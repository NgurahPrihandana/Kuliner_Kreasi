package com.example.kulinerkreasi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kulinerkreasi.entities.Resep;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class ResepUserFragment extends Fragment implements ResepDataAdapter.OnItemClickListener{
    LinearLayout keHalamanSimpan, keHalamanRecook;
    List<Resep> datalist;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_resep_user, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));

        ResepDataAdapter adapter = new ResepDataAdapter(new ArrayList<>());

        recyclerView.setAdapter(adapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionRef = db.collection("Reseps");

        collectionRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        datalist = new ArrayList<>();
                        for(QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            String judul_resep = documentSnapshot.getString("judul_resep");
                            String minimal = documentSnapshot.getString("minimal");
                            String maksimal = documentSnapshot.getString("maksimal");
                            String estimasi = documentSnapshot.getString("estimasi");
                            String image = documentSnapshot.getString("imageResep");
                            String bahan = documentSnapshot.getString("bahan");
                            String langkah = documentSnapshot.getString("langkah");
                            Resep resep = new Resep(judul_resep, minimal, maksimal, bahan, langkah, estimasi, image);
                            datalist.add(resep);
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
        Resep resep_item = datalist.get(position);
        Toast.makeText(getContext(), "Clicked: " + resep_item.getJudul_resep(), Toast.LENGTH_SHORT).show();
    }

//    @Override
//    public void onItemClick(int position) {
//        Resep resep = datalist.get(position);
//    }
}