package com.example.dengeliyasam.Fragments;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dengeliyasam.Models.KullaniciBilgileri;
import com.example.dengeliyasam.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profil extends Fragment {

    String img_url;
    Button guncelle;
    RadioButton cinsiyetk;
    RadioButton cinsiyete;
    ImageButton selectDate;
    CircleImageView profile_image;
    TextView ad_soyad;
    EditText hakkimda;
    EditText email;
    EditText sifre;
    DatePickerDialog datePickerDialog;
    TextView dateTxt;
    Calendar calendar;
    int year, month, dayOfMonth;
    View view;
    StorageReference storageReference;
    FirebaseStorage firebaseStorage;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_profil, container, false);
        define();
        datePicker();
        guncelleButton();
        bilgiGetir();
        profilDegisme();
        return view;
    }


    void define() {
        guncelle = view.findViewById(R.id.guncelle);
        selectDate = view.findViewById(R.id.date_picker);
        dateTxt = view.findViewById(R.id.dateTxt);
        cinsiyetk = view.findViewById(R.id.cinsiyetk);
        cinsiyete = view.findViewById(R.id.cinsiyete);
        hakkimda = view.findViewById(R.id.hakkimda);
        profile_image = view.findViewById(R.id.profile_image);
        ad_soyad = view.findViewById(R.id.ad_soyad);
        email = view.findViewById(R.id.email);
        sifre = view.findViewById(R.id.sifre);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("Kullanicilar").child(user.getUid());
    }

    void profilDegisme() {

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galeriAc();
            }
        });

    }

    void galeriAc() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 5);

    }

    public void onActivityResult(int requestcode, int resultcode, Intent data) {

        if (requestcode == 5 && resultcode == Activity.RESULT_OK) {
            Uri filepath = data.getData();
            StorageReference ref = storageReference.child("resim").child("bilmemne.jpg");
            ref.putFile(filepath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    final Task<Uri> firebaseuri = taskSnapshot.getStorage().getDownloadUrl();
                    firebaseuri.addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            final String downloadurl = uri.toString();
                            reference.child("resim").setValue(downloadurl);
                        }
                    });
                }
            });


        }
    }

    void datePicker() {
        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog = new DatePickerDialog(view.getContext(),
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

    void bilgiGetir() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                KullaniciBilgileri kb = snapshot.getValue(KullaniciBilgileri.class);
                ad_soyad.setText(String.format("%s %s", kb.getAd(), kb.getSoyad()));
                email.setText(kb.getEmail());
                dateTxt.setText(kb.getDate());
                hakkimda.setText(kb.getHakkimda());
                img_url = kb.getResim();

                if (img_url != null) {
                    Picasso.get().load(kb.getResim()).into(profile_image);
                }


                if (kb.getCinsiyet().equals(cinsiyetk.getText().toString())) {
                    cinsiyetk.setChecked(true);
                } else if (kb.getCinsiyet().equals(cinsiyete.getText().toString())) {
                    cinsiyete.setChecked(true);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    void bilgiGuncelle() {
        reference = database.getReference().child("Kullanicilar").child(auth.getUid());
        String bemail, bsifre, bdate, bhakkinda, bcinsiyetk, bcinsiyete;
        bemail = email.getText().toString();


        if (!sifre.getText().toString().equals("")) {
            bsifre = sifre.getText().toString();
            reference.child("sifre").setValue(bsifre);
        }

        bdate = dateTxt.getText().toString();
        bhakkinda = hakkimda.getText().toString();
        bcinsiyete = "elma";
        bcinsiyetk = "elma";

        if (img_url == null) {
            reference.child("resim").setValue("null");
        } else {
            reference.child("resim").setValue(img_url);
        }

        if (cinsiyetk.isChecked()) {
            bcinsiyetk = cinsiyetk.getText().toString();
        } else if (cinsiyete.isChecked()) {
            bcinsiyete = cinsiyete.getText().toString();
        }

        reference.child("email").setValue(bemail);
        reference.child("date").setValue(bdate);
        reference.child("hakkimda").setValue(bhakkinda);

        if (bcinsiyetk.equals("elma")) {
            reference.child("cinsiyet").setValue(bcinsiyete);
        } else if (bcinsiyete.equals("elma")) {
            reference.child("cinsiyet").setValue(bcinsiyetk);
        }

    }

    void guncelleButton() {
        guncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bilgiGuncelle();
                Toast.makeText(getContext(), "Bilgileriniz güncellenmiştir.",
                        Toast.LENGTH_LONG).show();
            }
        });
    }


}