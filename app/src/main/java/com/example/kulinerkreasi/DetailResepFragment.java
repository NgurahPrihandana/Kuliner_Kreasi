package com.example.kulinerkreasi;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import javax.annotation.Nullable;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailResepFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailResepFragment extends Fragment {
    TextView judul, jumlahOrang, harga, estimasi, bahan, langkah;
    ImageView image;
    String bJudulResep;
    String bMinimal;
    String bMaksimal;
    String bEstimasi;
    String bImage;
    String bBahan;
    String bLangkah;


    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        Bundle args = getArguments();
        if (args != null) {
            bJudulResep = args.getString("judul_resep");
            bMinimal = args.getString("minimal");
            bMaksimal = args.getString("maksimal");
            bEstimasi = args.getString("estimasi");
            bImage = args.getString("image");
            bBahan = args.getString("bahan");
            bLangkah = args.getString("langkah");

        }
        Log.i("Data", bMinimal);
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_detail_resep, container, false);
        judul = rootView.findViewById(R.id.judul_resep);
        jumlahOrang = rootView.findViewById(R.id.minimal);
        harga = rootView.findViewById(R.id.maksimal);
        estimasi = rootView.findViewById(R.id.estimasi);
        image = rootView.findViewById(R.id.image_makanan);
        bahan = rootView.findViewById(R.id.bahan);
        langkah = rootView.findViewById(R.id.langkah);

        judul.setText(bJudulResep);
        jumlahOrang.setText(bMinimal);
        harga.setText(bMaksimal);
        estimasi.setText(bEstimasi);
        bahan.setText(bBahan);
        langkah.setText(bLangkah);
        Glide.with(getContext())
                .load(bImage)
                .into(image);
        return rootView;
    }
}