package com.example.kulinerkreasi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kulinerkreasi.entities.Berita;
import com.example.kulinerkreasi.entities.Resep;

import java.util.List;

public class BeritaDataAdapter extends RecyclerView.Adapter<DataBeritaViewHolder>{
    private List<Berita> dataList;

    public BeritaDataAdapter(List<Berita> dataList) {
        this.dataList = dataList;
    }
    @NonNull
    @Override
    public DataBeritaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.berita_item, parent, false);
        return new DataBeritaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DataBeritaViewHolder holder, int position) {
        Berita berita = dataList.get(position);
        holder.bindData(berita);
        holder.loadImage(berita.getImageUrl());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setData(List<Berita> newDataList) {
        dataList.clear();
        dataList.addAll(newDataList);
        notifyDataSetChanged();
    }
}
