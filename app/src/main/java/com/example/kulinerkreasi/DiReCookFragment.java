package com.example.kulinerkreasi;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DiReCookFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,    @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_di_re_cook, container, false);
    }
}