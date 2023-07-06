package com.example.kulinerkreasi;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class TambahFragment extends Fragment {
    LinearLayout tambahResep;
    LinearLayout tambahBerita;

    private FirebaseAuth mAuth;
    TextView nama_admin;
    FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tambah, container, false);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        nama_admin = rootView.findViewById(R.id.nama_admin);

        tambahResep = rootView.findViewById(R.id.opsi_tambah_resep);
        tambahBerita = rootView.findViewById(R.id.opsi_tambah_berita);

        tambahResep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TambahResepFragment fragmentTambah = new TambahResepFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_container, fragmentTambah);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        tambahBerita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TambahBeritaFragment fragmentTambahBerita = new TambahBeritaFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_container, fragmentTambahBerita);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        FirebaseUser currentUser = mAuth.getCurrentUser();

        String userId = mAuth.getCurrentUser().getUid();

        db.getInstance().collection("Users").document(userId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()) {
                            String tampil_nama = documentSnapshot.getString("nama_pengguna");
                            nama_admin.setText(tampil_nama);
                        } else {
                            Toast.makeText(getContext(), "User document does not exist.", Toast.LENGTH_SHORT).show();

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Error occurred while retrieving user role
                        Log.w(TAG, "Error when fetching data", e);
                        Toast.makeText(getContext(), "Error occurred. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });


        return rootView;

    }



}