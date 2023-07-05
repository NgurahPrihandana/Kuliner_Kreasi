package com.example.kulinerkreasi;

import android.content.Intent;
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

public class TambahFragment extends Fragment {
    LinearLayout tambahResep;
    LinearLayout tambahBerita;

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tambah, container, false);

        tambahResep = rootView.findViewById(R.id.opsi_tambah_resep);
        tambahBerita = rootView.findViewById(R.id.opsi_tambah_berita);

        tambahResep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getActivity(), "Tambah Resep", Toast.LENGTH_SHORT).show();
                // Create new fragment and transaction
                FragmentManager fragmentManager = getChildFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setReorderingAllowed(true);
                // Replace whatever is in the fragment_container view with this fragment
                transaction.replace(R.id.fragment_container, ExampleFragment.class, null);

// Commit the transaction
                transaction.commit();
            }
        });

        tambahBerita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Tambah Berita", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;

    }



}