package com.example.kulinerkreasi;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kulinerkreasi.entities.Resep;

import java.util.List;

public class ResepDataAdapter extends RecyclerView.Adapter<DataResepViewHolder>{
    private List<Resep> dataList;
    private OnItemClickListener clickListener;


    public ResepDataAdapter(List<Resep> dataList, OnItemClickListener listener) {
        this.dataList = dataList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.clickListener = listener;
    }

    @NonNull
    @Override
    public DataResepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.resep_item, parent, false);
        return new DataResepViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DataResepViewHolder holder, int position) {
        Resep resep = dataList.get(position);
        holder.bindData(resep);
        holder.loadImage(resep.getImageUrl());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setData(List<Resep> newDataList) {
        dataList.clear();
        dataList.addAll(newDataList);
        notifyDataSetChanged();
    }
}
