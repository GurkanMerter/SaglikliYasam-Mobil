package com.example.dengeliyasam.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dengeliyasam.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class KullaniciUyelik extends AppCompatActivity {

    EditText ad, soyad, email, sifre, boy, kilo;
    RadioButton cinsiyetk;
    RadioButton cinsiyete;
    Button submit;
    RatingBar aktiflik;
    ImageButton selectDate;
    DatePickerDialog datePickerDialog;
    TextView dateTxt;
    Calendar calendar;
    int year, month, dayOfMonth;
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kullanici_uyelik);
        define();
        submitButton();
        datePicker();
    }


    void define() {
        ad = findViewById(R.id.ad);
        soyad = findViewById(R.id.soyad);
        email = findViewById(R.id.email);
        sifre = findViewById(R.id.sifre);
        cinsiyetk = findViewById(R.id.cinsiyetk);
        cinsiyete = findViewById(R.id.cinsiyete);
        submit = findViewById(R.id.submit_button);
        aktiflik = findViewById(R.id.aktiflik);
        selectDate = findViewById(R.id.date_picker);
        dateTxt = findViewById(R.id.dateTxt);
        boy = findViewById(R.id.boy);
        kilo = findViewById(R.id.kilo);
        mAuth = FirebaseAuth.getInstance();
    }

    void datePicker() {
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(KullaniciUyelik.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                dateTxt.setText(day + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, dayOfMonth);
                datePickerDialog.show();
            }
        });
    }

    void submitButton() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String semail, spassword;
                semail = email.getText().toString();
                spassword = sifre.getText().toString();
                // TODO Rating bar

                if (!semail.trim().equals("") && !spassword.trim().equals("")) {
                    signUp(semail, spassword);
                } else {
                    Toast.makeText(getApplicationContext(), "Bilgileri eksiksiz doldurunuz.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void signUp(String semail, String password) {
        mAuth.createUserWithEmailAndPassword(semail, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            database = FirebaseDatabase.getInstance();
                            reference = database.getReference().child("Kullanicilar").child(mAuth.getUid());
                            Map map = new HashMap();
                            map.put("ad", ad.getText().toString());
                            map.put("soyad", soyad.getText().toString());

                            // TODO image ataması

                            map.put("email", email.getText().toString());
                            map.put("sifre", sifre.getText().toString());
                            map.put("date", dateTxt.getText().toString());
                            map.put("boy", boy.getText().toString());
                            map.put("kilo", kilo.getText().toString());
                            map.put("egitim_seviyesi", "");
                            map.put("hakkimda", "");
                            map.put("resim","null");

                            if (cinsiyete.isChecked()) {
                                map.put("cinsiyet", cinsiyete.getText().toString());
                            } else if (cinsiyetk.isChecked()) {
                                map.put("cinsiyet", cinsiyetk.getText().toString());
                            }
                            reference.setValue(map);

                            Intent intent = new Intent(getApplicationContext(), KullaniciGirisActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}