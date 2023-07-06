package com.example.kulinerkreasi;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.annotation.Nullable;

public class DetailResepFragmentAdmin extends Fragment {
    TextView judul, jumlahOrang, harga, estimasi, bahan, langkah;
    ImageView image;
    String bJudulResep;
    String bMinimal;
    String bMaksimal;
    String bEstimasi;
    String bImage;
    String bBahan;
    String bLangkah;
    String id;

    LinearLayout btnDelete;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Bundle args = getArguments();
        if (args != null) {
            bJudulResep = args.getString("judul_resep");
            bMinimal = args.getString("minimal");
            bMaksimal = args.getString("maksimal");
            bEstimasi = args.getString("estimasi");
            bImage = args.getString("image");
            bBahan = args.getString("bahan");
            bLangkah = args.getString("langkah");
            id = args.getString("id");

        }
        Log.i("Data", bMinimal);
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail_resep_admin, container, false);
        judul = rootView.findViewById(R.id.judul_resep);
        btnDelete = rootView.findViewById(R.id.btnDelete);
        jumlahOrang = rootView.findViewById(R.id.minimal);
        harga = rootView.findViewById(R.id.maksimal);
        estimasi = rootView.findViewById(R.id.estimasi);
        image = rootView.findViewById(R.id.image_makanan);
        bahan = rootView.findViewById(R.id.bahan);
        langkah = rootView.findViewById(R.id.langkah);

        judul.setText(bJudulResep);
        jumlahOrang.setText(bMinimal);
        harga.setText(bMaksimal);
        estimasi.setText(bEstimasi);
        bahan.setText(bBahan);
        langkah.setText(bLangkah);
        Glide.with(getContext())
                .load(bImage)
                .into(image);

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                CollectionReference collectionRef = db.collection("Reseps");
                collectionRef.document(id)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Document successfully deleted
                                ResepFragment fragmentDetail = new ResepFragment();
                                FragmentManager fragmentManager = getParentFragmentManager();
                                FragmentTransaction transaction = fragmentManager.beginTransaction();
                                transaction.replace(R.id.fragment_container, fragmentDetail);
                                transaction.addToBackStack(null);
                                transaction.commit();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Error deleting document
                                Toast.makeText(getContext(), "Failed to delete data", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
        return rootView;
    }
}
