package com.example.kulinerkreasi;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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

            }
        });

        tambahBerita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return rootView;

    }



}