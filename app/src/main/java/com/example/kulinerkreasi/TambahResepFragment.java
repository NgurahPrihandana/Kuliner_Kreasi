package com.example.kulinerkreasi;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.viewmodel.CreationExtras;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TambahResepFragment extends Fragment {
    private LinearLayout containerBahan;
    protected int steps_counter = 0;
    protected int bahan_counter = 0;
    private LinearLayout containerSteps;
    private Button btnAddBahan;
    private Button btnAddSteps;
    private ImageView imageViewPreview;
    private Uri selectedImageUri;

    private EditText edt_judul_resep, edt_minimal, edt_maksimal, edt_estimasi, edt_bahan, edt_langkah;

    private Button btnSelectImage, btn_submit, btn_back;
    FirebaseFirestore db;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        db = FirebaseFirestore.getInstance();

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tambah_resep, container, false);

        edt_judul_resep = rootView.findViewById(R.id.edt_judul_resep);
        edt_minimal = rootView.findViewById(R.id.edt_minimal);
        edt_maksimal = rootView.findViewById(R.id.edt_maksimal);
        edt_estimasi = rootView.findViewById(R.id.edt_estimasi);
        edt_bahan = rootView.findViewById(R.id.edt_bahan);
        edt_langkah = rootView.findViewById(R.id.edt_langkah);
        btnSelectImage = rootView.findViewById(R.id.btnSelectImage);
        btn_submit = rootView.findViewById(R.id.submit_menu);
        imageViewPreview = rootView.findViewById(R.id.imageViewPreview);
        btn_back = rootView.findViewById(R.id.btn_back_toTambah);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  TambahFragment fragmentback = new TambahFragment();
                  FragmentManager fragmentManager = getParentFragmentManager();
                  FragmentTransaction transaction = fragmentManager.beginTransaction();
                  transaction.replace(R.id.fragment_container, fragmentback);
                  transaction.addToBackStack(null);
                  transaction.commit();
            }
            });

        btnSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                pickImageActivityResultLauncher.launch(intent);
            }
        });



        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String judul_resep = edt_judul_resep.getText().toString();
                String minimal = edt_minimal.getText().toString();
                String maksimal = edt_maksimal.getText().toString();
                String estimasi = edt_estimasi.getText().toString();
                String bahan = edt_bahan.getText().toString();
                String langkah = edt_langkah.getText().toString();

                saveResepData(judul_resep, minimal, maksimal, estimasi, bahan, langkah);
            }
        });



        return rootView;
    }

    ActivityResultLauncher<Intent> pickImageActivityResultLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new
                    ActivityResultCallback<ActivityResult>() {
                        @Override
                        public void onActivityResult(ActivityResult result) {
                            if(result.getResultCode()== Activity.RESULT_OK){
                                Intent data = result.getData();
                                selectedImageUri = data.getData();

                                final String path = getPathFromURI(selectedImageUri);
                                if(path!=null) {
                                    File f = new File(path);
                                    selectedImageUri = Uri.fromFile(f);
                                }
                                imageViewPreview.setImageURI(selectedImageUri);
                            }
                        }
                    });

    private void saveResepData(String judul_resep, String minimal, String maksimal, String estimasi,
                               String bahan, String langkah) {
        Map<String, Object> resepDetails = new HashMap<>();
        resepDetails.put("judul_resep", judul_resep);
        resepDetails.put("minimal", minimal);
        resepDetails.put("maksimal", maksimal);
        resepDetails.put("estimasi", estimasi);
        resepDetails.put("bahan", bahan);
        resepDetails.put("langkah", langkah);


        db.collection("Reseps").add(resepDetails)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        String resepId = documentReference.getId();
                        Toast.makeText(getContext(), "Resep added successfully", Toast.LENGTH_SHORT).show();
                        saveImageToFirebaseStorage(resepId);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error when adding data", e);
                        Toast.makeText(getContext(), "Failed to Add Resep", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public String getPathFromURI(Uri contentUri) {
        Context applicationContext = MainActivityAdmin.getContextOfApplication();
        String res = null;

        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = applicationContext.getContentResolver().query(contentUri, proj, null,
                null, null);
        if(cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();

        return res;
    }

    private void saveImageToFirebaseStorage(String resepId) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        String imageName = UUID.randomUUID().toString() + ".png";
        StorageReference imageRef = storageRef.child("resep_images/"+imageName);

        imageRef.putFile(selectedImageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String imageUrl = uri.toString();

                                updateResepImageUrl(resepId, imageUrl);
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error when updating image data", e);
                        Toast.makeText(getContext(), "Failed to upload image", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void updateResepImageUrl(String resepId, String imgUrl) {
        DocumentReference resepRef = db.collection("Reseps").document(resepId);

        resepRef.update("imageResep", imgUrl)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getContext(), "Resep Image Berhasil di Update", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Resep Image Gagal di Update", Toast.LENGTH_SHORT).show();
                    }
                });
    }



}
