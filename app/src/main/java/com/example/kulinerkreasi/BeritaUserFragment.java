package com.example.kulinerkreasi;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.kulinerkreasi.entities.Berita;
import com.example.kulinerkreasi.entities.Resep;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class BeritaUserFragment extends Fragment implements BeritaDataAdapter.OnItemClickListener {
    List<Berita> datalist;
    @Nullable

    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        datalist = new ArrayList<>();
        View rootView = inflater.inflate(R.layout.fragment_berita_user, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        BeritaDataAdapter adapter = new BeritaDataAdapter(new ArrayList<>(), this);
        adapter.setOnItemClickListener(this);

        recyclerView.setAdapter(adapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionRef = db.collection("Beritas");

        collectionRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            String judul_berita = documentSnapshot.getString("judul_berita");
                            String deskripsi = documentSnapshot.getString("desc_berita");
                            String image = documentSnapshot.getString("imageBerita");
                            Berita berita= new Berita(judul_berita, deskripsi, image);
                            datalist.add(berita);
                        }

                        adapter.setData(datalist);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Failed to fetch data", Toast.LENGTH_SHORT).show();
                    }
                });

        return rootView;
    }

    @Override
    public void onItemClick(int position) {
        Berita berita_item = datalist.get(position);
        Toast.makeText(getContext(), "Clicked: " + berita_item.getJudul_berita(), Toast.LENGTH_SHORT).show();
    }
}