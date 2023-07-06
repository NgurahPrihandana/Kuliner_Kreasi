package com.example.kulinerkreasi;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
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

import com.example.kulinerkreasi.entities.Berita;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TambahBeritaFragment extends Fragment {
    private EditText edt_judul_berita, edt_desc_berita;
    private Button btn_submit, btn_select_image, btn_back;

    private ImageView imageViewPreview;

    private Uri selectedImageUri;
    FirebaseFirestore db;
    @SuppressLint("MissingInflatedId")
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        db = FirebaseFirestore.getInstance();
        View rootView = inflater.inflate(R.layout.fragment_tambah_berita, container, false);

        edt_judul_berita = rootView.findViewById(R.id.edt_judul_berita);
        edt_desc_berita = rootView.findViewById(R.id.edt_desc_berita);
        btn_submit = rootView.findViewById(R.id.btn_simpan);
        btn_select_image = rootView.findViewById(R.id.btnSelectImage);
        imageViewPreview = rootView.findViewById(R.id.imageViewPreview);
        btn_back = rootView.findViewById(R.id.btn_back_toTambah);
        btn_select_image.setOnClickListener(new View.OnClickListener() {
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
                String judul_berita = edt_judul_berita.getText().toString();
                String desc_berita = edt_desc_berita.getText().toString();

                saveBeritaData(judul_berita, desc_berita);
            }
        });

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


        return rootView;
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

    private void saveBeritaData(String judul_berita, String desc_berita) {
        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("judul_berita", judul_berita);
        userDetails.put("desc_berita", desc_berita);


        db.collection("Beritas").add(userDetails)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        String beritaId = documentReference.getId();
                        Toast.makeText(getContext(), "Berita added successfully", Toast.LENGTH_SHORT).show();
                        saveImageToFirebaseStorage(beritaId);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error when adding data", e);
                        Toast.makeText(getContext(), "Failed to Add Berita", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void saveImageToFirebaseStorage(String beritaId) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        String imageName = UUID.randomUUID().toString() + ".png";
        StorageReference imageRef = storageRef.child("berita_images/"+imageName);

        imageRef.putFile(selectedImageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String imageUrl = uri.toString();

                                updateBeritaImageUrl(beritaId, imageUrl);
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

    private void updateBeritaImageUrl(String beritaId, String imgUrl) {
        DocumentReference beritaRef = db.collection("Beritas").document(beritaId);

        beritaRef.update("imageBerita", imgUrl)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getContext(), "Berita Image Berhasil di Update", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Berita Image Gagal di Update", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
