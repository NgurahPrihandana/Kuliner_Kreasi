package com.example.kulinerkreasi;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.ChildEventListener;
public class DashboardFragment extends Fragment {
    private FirebaseAuth mAuth;
    TextView nama_admin, countResep, countUser, countBerita;
    FirebaseFirestore db;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        nama_admin = view.findViewById(R.id.nama_admin);

        countResep = view.findViewById(R.id.count_resep);
        countUser = view.findViewById(R.id.countUser);
        countBerita = view.findViewById(R.id.count_berita);

        // Retrieve the count from the table
        FirebaseFirestore.getInstance().collection("Reseps")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    int entryCount = queryDocumentSnapshots.size();
                    // Update the UI with the entry count
                    countResep.setText(String.valueOf(entryCount));
                })
                .addOnFailureListener(e -> {
                    // Handle any errors
                });

        FirebaseFirestore.getInstance().collection("Beritas")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    int entryCount = queryDocumentSnapshots.size();
                    // Update the UI with the entry count
                    countBerita.setText(String.valueOf(entryCount));
                })
                .addOnFailureListener(e -> {
                    // Handle any errors
                });

        FirebaseFirestore.getInstance().collection("Users")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    int entryCount = queryDocumentSnapshots.size();
                    // Update the UI with the entry count
                    countUser.setText(String.valueOf(entryCount));
                })
                .addOnFailureListener(e -> {
                    // Handle any errors
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

    }

}