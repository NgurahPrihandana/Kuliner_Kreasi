package com.example.kulinerkreasi;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kulinerkreasi.entities.Berita;
import com.example.kulinerkreasi.entities.Resep;

public class DataBeritaViewHolder extends RecyclerView.ViewHolder{
    private ImageView imageView;
    private TextView judul;
    private TextView desc;

    public DataBeritaViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.gambar_berita);
        judul = itemView.findViewById(R.id.judul_berita);
        desc = itemView.findViewById(R.id.desc_berita);
    }

    public void bindData(Berita berita) {
        judul.setText(berita.getJudul_berita());
        desc.setText(berita.getDesc_berita());
    }

    public void loadImage(String imageUrl) {
        Glide.with(itemView.getContext())
                .load(imageUrl)
                .into(imageView);
    }
}
