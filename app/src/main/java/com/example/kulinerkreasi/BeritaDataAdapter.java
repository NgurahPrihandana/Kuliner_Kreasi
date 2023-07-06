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

    private BeritaDataAdapter.OnItemClickListener clickListener;

    public void setOnItemClickListener(BeritaDataAdapter.OnItemClickListener listener) {
        this.clickListener = listener;
    }

    public BeritaDataAdapter(List<Berita> dataList, BeritaDataAdapter.OnItemClickListener clickListener) {
        this.dataList = dataList;
        this.clickListener = clickListener;
    }
    @NonNull
    @Override
    public DataBeritaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.berita_item, parent, false);
        return new DataBeritaViewHolder(itemView, clickListener);
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

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setData(List<Berita> newDataList) {
        dataList.clear();
        dataList.addAll(newDataList);
        notifyDataSetChanged();
    }
}
