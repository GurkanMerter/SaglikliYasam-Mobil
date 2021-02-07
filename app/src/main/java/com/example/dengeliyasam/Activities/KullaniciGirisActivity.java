package com.example.dengeliyasam.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dengeliyasam.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class KullaniciGirisActivity extends AppCompatActivity {

    Button giris;
    TextView uyeOl;
    EditText email, password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kullanici_giris);
        define();
        girisClick();
        uyeOlClick();
    }

    void define() {
        giris = findViewById(R.id.giris);
        uyeOl = findViewById(R.id.uyeOl);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();
    }

    void girisClick() {
        giris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String semail, spassword;
                semail = email.getText().toString();
                spassword = password.getText().toString();
                if (!semail.trim().equals("") && !spassword.trim().equals("")) {
                    signIn(semail, spassword);
                } else {
                    Toast.makeText(getApplicationContext(), "Bilgileri eksiksiz doldurunuz.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent kullaniciIntent = new Intent(getApplicationContext(), KullaniciEkran.class);
                            startActivity(kullaniciIntent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    void uyeOlClick() {
        uyeOl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent uyeIntent = new Intent(getApplicationContext(), KullaniciUyelik.class);
                startActivity(uyeIntent);
            }
        });
    }

}