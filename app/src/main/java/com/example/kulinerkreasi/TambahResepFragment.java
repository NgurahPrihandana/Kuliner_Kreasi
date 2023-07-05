package com.example.kulinerkreasi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.viewmodel.CreationExtras;

public class TambahResepFragment extends Fragment {
    private LinearLayout containerBahan;
    private LinearLayout containerSteps;
    private Button btnAddBahan;
    private Button btnAddSteps;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tambah_resep, container, false);

        containerBahan = rootView.findViewById(R.id.containerBahan);
        containerSteps = rootView.findViewById(R.id.containerStep);
        btnAddBahan = rootView.findViewById(R.id.addMoreBahan);
        btnAddSteps = rootView.findViewById(R.id.addMoreSteps);

        btnAddBahan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBahanItem();
            }
        });

        btnAddSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addStepsItem();
            }
        });
        return rootView;
    }

    private void addBahanItem() {
        LayoutInflater inflater = getLayoutInflater();
        View itemBahan = inflater.inflate(R.layout.item_bahan,containerBahan, false);

        Button btnRemove = itemBahan.findViewById(R.id.btnRemove);
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeItemBahan(itemBahan);
            }
        });

        containerBahan.addView(itemBahan);
    }

    private void addStepsItem() {
        LayoutInflater inflater = getLayoutInflater();
        View itemSteps = inflater.inflate(R.layout.item_steps,containerSteps, false);

        Button btnRemove = itemSteps.findViewById(R.id.btnRemove);

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeItemSteps(itemSteps);
            }
        });

        containerSteps.addView(itemSteps);
    }

    private void removeItemBahan(View itemBahan) {
        containerBahan.removeView(itemBahan);
    }

    private void removeItemSteps(View itemSteps) {
        containerSteps.removeView(itemSteps);
    }


}
