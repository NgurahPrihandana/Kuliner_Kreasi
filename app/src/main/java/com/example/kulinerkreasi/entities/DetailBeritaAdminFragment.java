package com.example.kulinerkreasi.entities;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kulinerkreasi.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailBeritaAdminFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailBeritaAdminFragment extends Fragment {
    TextView judul_berita, desc_berita;
    ImageView image;
    String id;

    String b_judul_berita,b_desc_berita,bimage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail_berita_admin, container, false);

        Bundle args = getArguments();
        if (args != null) {
            b_judul_berita = args.getString("judul_berita");
            bimage = args.getString("image");
            b_desc_berita = args.getString("desc_berita");
            id = args.getString("id");

        }

        judul_berita = rootView.findViewById(R.id.judul_berita);
        desc_berita = rootView.findViewById(R.id.isiBerita);
        image = rootView.findViewById(R.id.image_makanan);

        judul_berita.setText(b_judul_berita);
        desc_berita.setText(b_desc_berita);
        Glide.with(getContext())
                .load(bimage)
                .into(image);

        return rootView;
    }
}