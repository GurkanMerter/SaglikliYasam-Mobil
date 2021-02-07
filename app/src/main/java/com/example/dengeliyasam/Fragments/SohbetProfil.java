package com.example.dengeliyasam.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.dengeliyasam.Activities.SohbetActivity;
import com.example.dengeliyasam.Models.KullaniciBilgileri;
import com.example.dengeliyasam.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class SohbetProfil extends Fragment {

    View view;
    TextView adsoyad;
    String userid, otherid;
    CircleImageView resim;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Button mesajat;
    FirebaseAuth auth;
    FirebaseUser user;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sohbet_profil, container, false);
        define();
        aksiyon();
        return view;
    }

    void define() {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        userid = user.getUid();
        otherid = getArguments().getString("userid");
        adsoyad = view.findViewById(R.id.ad_soyad);
        resim = view.findViewById(R.id.profile_görünüm);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        mesajat = view.findViewById(R.id.mesajat);
    }

    public void aksiyon() {
        databaseReference.child("Kullanicilar").child(otherid).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                KullaniciBilgileri kb = snapshot.getValue(KullaniciBilgileri.class);
                adsoyad.setText(String.format("%s %s", kb.getAd(), kb.getSoyad()));
                if (!kb.getResim().equals("null")) {
                    Picasso.get().load(kb.getResim()).into(resim);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mesajat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SohbetActivity.class);
                intent.putExtra("kullanicisim", adsoyad.getText().toString());
                intent.putExtra("id", otherid);
                startActivity(intent);
            }
        });


    }
}