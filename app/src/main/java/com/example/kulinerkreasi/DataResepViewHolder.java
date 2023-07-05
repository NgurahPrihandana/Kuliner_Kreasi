package com.example.kulinerkreasi;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.kulinerkreasi.entities.Berita;
import com.example.kulinerkreasi.entities.Resep;

public class DataResepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    private ImageView imageView;
    private TextView judul;
    private TextView rating;
    private AdapterView.OnItemClickListener clickListener;

    public DataResepViewHolder(@NonNull View itemView, AdapterView.OnItemClickListener listener) {
        super(itemView);
        clickListener = listener;

        itemView.setOnClickListener(this);
        imageView = itemView.findViewById(R.id.gambar_resep);
        judul = itemView.findViewById(R.id.judul_resep);
        rating = itemView.findViewById(R.id.rating);
    }

    public void bindData(Resep resep) {
        judul.setText(resep.getJudul_resep());
        rating.setText("5");
    }


    public void loadImage(String imageUrl) {
        Glide.with(itemView.getContext())
                .load(imageUrl)
                .into(imageView);
    }

    @Override
    public void onClick(View view) {
        int position = getAdapterPosition();
        if(position != RecyclerView.NO_POSITION) {
//            Resep resep = data
        }
    }
}
