package com.example.kulinerkreasi;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class PenggunaFragment extends Fragment {
    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    Button btn_out;
    TextView  nama_admin, namaPengguna_admin, ttl_admin, alamat_admin,tlp_admin, jk_admin, email_admin;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pengguna, container, false);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        nama_admin = view.findViewById(R.id.nama_lk);
        namaPengguna_admin = view.findViewById(R.id.nama_pe);
        alamat_admin = view.findViewById(R.id.alamat_admin);
        tlp_admin = view.findViewById(R.id.tlp_admin);
        ttl_admin = view.findViewById(R.id.ttl);
        email_admin = view.findViewById(R.id.email_admin);
        btn_out = view.findViewById(R.id.buttonOut);


        btn_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.getInstance().signOut();
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        String userId = mAuth.getCurrentUser().getUid();

        db.getInstance().collection("Users").document(userId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if(documentSnapshot.exists()) {
                            String alamat = documentSnapshot.getString("alamat");
                            String email = documentSnapshot.getString("email");
                            String nama_lengkap = documentSnapshot.getString("nama_lengkap");
                            String nama_pengguna = documentSnapshot.getString("nama_pengguna");
                            String no_telp = documentSnapshot.getString("no_telp");
                            String tgl = documentSnapshot.getString("tgl_lahir");

                            nama_admin.setText(nama_lengkap);
                            namaPengguna_admin.setText(nama_pengguna);
                            ttl_admin.setText(tgl);
                            alamat_admin.setText(alamat);
                            tlp_admin.setText(no_telp);
                            email_admin.setText(email);


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

    }
}