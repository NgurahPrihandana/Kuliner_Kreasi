package com.example.kulinerkreasi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class CariFragment extends Fragment implements ResepDataAdapter.OnItemClickListener{
    LinearLayout keHalamanSimpan, keHalamanRecook;
    EditText edt_search;
    private List<Resep> datalist;
    private ResepDataAdapter adapter;
    private List<Resep> filteredData;

    private RecyclerView recyclerView;

    Bundle bundle = new Bundle();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        datalist = new ArrayList<>();


        View rootView = inflater.inflate(R.layout.fragment_cari, container, false);

        edt_search = rootView.findViewById(R.id.edt_search);

        recyclerView = rootView.findViewById(R.id.recycler_view);

        adapter = new ResepDataAdapter(new ArrayList<>(), this);
        adapter.setOnItemClickListener(this);

        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        recyclerView.setAdapter(adapter);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference collectionRef = db.collection("Reseps");

        collectionRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
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
                        setupRecyclerView();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });



        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String searchQuery = charSequence.toString().trim();
                Toast.makeText(getContext(), searchQuery, Toast.LENGTH_SHORT).show();
                filterData(searchQuery.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return rootView;

    }

    private void setupRecyclerView() {
        // Create a separate list to store the filtered data
        filteredData = new ArrayList<>(datalist);

        // Set up the RecyclerView and adapter with the filtered data
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        adapter = new ResepDataAdapter(filteredData, this);
        recyclerView.setAdapter(adapter);
    }

    private void filterData(String query) {
        filteredData = new ArrayList<>();
        if (query.isEmpty()) {
            filteredData.addAll(datalist);
        } else {
            String lowerCaseQuery = query.toLowerCase(Locale.getDefault());
            for (Resep item : datalist) {
                if (item.getJudul_resep().toLowerCase(Locale.getDefault()).contains(lowerCaseQuery)) {
                    filteredData.add(item);
                }
            }
        }
    }

    @Override
    public void onItemClick(int position) {
        if (datalist != null && position >= 0 && position < datalist.size()) {
            Resep resep_item = datalist.get(position);
            bundle.putString("judul_resep", resep_item.getJudul_resep());
            bundle.putString("minimal", resep_item.getMinimal());
            bundle.putString("maksimal", resep_item.getMaksimal());
            bundle.putString("estimasi", resep_item.getEstimasi());
            bundle.putString("image", resep_item.getImageUrl());
            bundle.putString("bahan", resep_item.getBahan());
            bundle.putString("langkah", resep_item.getLangkah());
            DetailResepFragment fragmentDetail = new DetailResepFragment();
            fragmentDetail.setArguments(bundle);
            FragmentManager fragmentManager = getParentFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.fragment_container, fragmentDetail);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

}