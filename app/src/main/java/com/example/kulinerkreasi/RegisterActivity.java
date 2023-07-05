package com.example.kulinerkreasi;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseFirestore db;

    private EditText edt_nama_lengkap, edt_nama_pengguna, edt_tgl_lahir, edt_alamat, edt_no_telp,
            edt_email, edt_pass, edt_pass_conf;
    private Button registerButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.regis1);
        edt_nama_lengkap = findViewById(R.id.edt_nama_lengkap);
        edt_nama_pengguna = findViewById(R.id.edt_nama_pengguna);
        edt_tgl_lahir = findViewById(R.id.edt_tgl_lahir);
        edt_alamat = findViewById(R.id.edt_alamat);
        edt_no_telp = findViewById(R.id.edt_no_telp);
        edt_email = findViewById(R.id.edt_email);
        edt_pass = findViewById(R.id.edt_pass);
        edt_pass_conf = findViewById(R.id.edt_pass_conf);
        registerButton = findViewById(R.id.button);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        String nama_lengkap = edt_nama_lengkap.getText().toString();
        String nama_pengguna = edt_nama_pengguna.getText().toString();
        String tgl_lahir = edt_tgl_lahir.getText().toString();
        String alamat = edt_alamat.getText().toString();
        String no_telp = edt_no_telp.getText().toString();
        String email = edt_email.getText().toString();
        String pass = edt_pass.getText().toString();
        String pass_conf = edt_pass_conf.getText().toString();


            mAuth.createUserWithEmailAndPassword(email,pass)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Authentication Success.",
                                        Toast.LENGTH_SHORT).show();
                                saveUsersDetails(email, nama_lengkap, nama_pengguna, tgl_lahir, alamat, no_telp);

                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                finish();
                            } else {
                                Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

    }

    private void saveUsersDetails(String email, String nama_lengkap, String nama_pengguna, String tgl_lahir,
                                  String alamat, String no_telp) {
        String userId = mAuth.getCurrentUser().getUid();

        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("nama_lengkap", nama_lengkap);
        userDetails.put("nama_pengguna", nama_pengguna);
        userDetails.put("email", email);
        userDetails.put("tgl_lahir", tgl_lahir);
        userDetails.put("alamat", alamat);
        userDetails.put("no_telp", no_telp);
        userDetails.put("role","user");

        db.collection("Users").document(userId)
                .set(userDetails)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d(TAG, "DocumentSnapshot successfully written!");
                        Toast.makeText(RegisterActivity.this, "Registration successful!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error writing document", e);
                        Toast.makeText(RegisterActivity.this, "Error occurred while saving user details.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
