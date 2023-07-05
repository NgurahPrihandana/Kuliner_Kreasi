package com.example.kulinerkreasi;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class TambahBeritaFragment extends Fragment {
    private EditText edt_judul_berita;
    private Button btn_submit, btn_select_image;
    FirebaseFirestore db;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        db = FirebaseFirestore.getInstance();
        View rootView = inflater.inflate(R.layout.fragment_tambah_berita, container, false);

        edt_judul_berita = rootView.findViewById(R.id.edt_judul_berita);
        btn_submit = rootView.findViewById(R.id.btn_simpan);
        btn_select_image = rootView.findViewById(R.id.btnSelectImage);

        btn_select_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                openImagePicker();
            }
        });
        btn_submit.setOnClickListener(new View.OnClickListener() {
            String judul_berita = edt_judul_berita.getText().toString();
            @Override
            public void onClick(View view) {
                saveBeritaData(judul_berita);
            }
        });
        return rootView;
    }
    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

    }

    public String getPathFromURI(Uri contentUri) {
        String res = null;

        String[] proj = {MediaStore.Images.Media.DATA};
        
    }

    private void saveBeritaData(String judul_berita) {
        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("judul_berita", judul_berita);

        //db.collection("Beritas").document()
    }
}
