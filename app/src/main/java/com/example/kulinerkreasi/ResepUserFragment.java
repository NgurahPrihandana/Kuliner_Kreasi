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


public class ResepUserFragment extends Fragment {
    LinearLayout keHalamanSimpan, keHalamanRecook;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_resep_user, container, false);

        keHalamanSimpan = rootView.findViewById(R.id.keDisimpan);
        keHalamanRecook = rootView.findViewById(R.id.keDiRecook);


        keHalamanSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DisimpanFragment fragmentsimpan = new DisimpanFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.fragment_container, fragmentsimpan);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        keHalamanRecook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Tambah Berita", Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;

    }



}