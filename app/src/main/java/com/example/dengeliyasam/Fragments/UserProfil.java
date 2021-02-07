package com.example.dengeliyasam.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.MenuView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dengeliyasam.Activities.SohbetActivity;
import com.example.dengeliyasam.Models.KullaniciBilgileri;
import com.example.dengeliyasam.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class UserProfil extends Fragment {

    View view;
    TextView tecrübe, hakkimda, adsoyad, likesayı;
    String userid, otherid, begenikontrol = "null";
    CircleImageView resim;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Button like, unlike, üyeol;
    FirebaseAuth auth;
    FirebaseUser user;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_profil, container, false);
        define();
        aksiyon();
        getBegenitxt();
        return view;
    }

    void define() {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        userid = user.getUid();
        otherid = getArguments().getString("userid");
        adsoyad = view.findViewById(R.id.ad_soyad);
        resim = view.findViewById(R.id.profile_görünüm);
        tecrübe = view.findViewById(R.id.tecrübe);
        hakkimda = view.findViewById(R.id.hakkimda);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        like = view.findViewById(R.id.likebutton);
        unlike = view.findViewById(R.id.dislikebutton);
        likesayı = view.findViewById(R.id.likesayı);
        üyeol = view.findViewById(R.id.üyeol);


        databaseReference.child("Begeniler").child(otherid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(userid)) {
                    begenikontrol = "beğendi";
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    void getBegenitxt() {

        databaseReference.child("Begeniler").child(otherid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                likesayı.setText(snapshot.getChildrenCount() + "");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void begen(String userid, String otherid) {
        databaseReference.child("Begeniler").child(otherid).child(userid).child("durum").setValue("Like attım").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getContext(), "Eğitmeni Puanladınız", Toast.LENGTH_LONG).show();
                    begenikontrol = "beğendi";
                    getBegenitxt();

                }
            }
        });


    }

    void begeniptal(String userid, String otherid) {

        databaseReference.child("Begeniler").child(otherid).child(userid).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                begenikontrol = "null";
                Toast.makeText(getContext(), "Eğitmenden Puanınızı Çektiniz", Toast.LENGTH_LONG).show();
                getBegenitxt();

            }
        });
    }

    public void aksiyon() {
        databaseReference.child("Kullanicilar").child(otherid).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                KullaniciBilgileri kb = snapshot.getValue(KullaniciBilgileri.class);
                adsoyad.setText(String.format("%s %s", kb.getAd(), kb.getSoyad()));
                tecrübe.setText(kb.getEgitim_seviyesi());
                hakkimda.setText(kb.getHakkimda());
                unlike.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (begenikontrol.equals("beğendi")) {
                            begeniptal(userid, otherid);
                        }
                    }

                });
                like.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!begenikontrol.equals("beğendi")) {
                            begen(userid, otherid);
                        }
                    }
                });

                if (!kb.getResim().equals("null")) {
                    Picasso.get().load(kb.getResim()).into(resim);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        üyeol.setOnClickListener(new View.OnClickListener() {
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