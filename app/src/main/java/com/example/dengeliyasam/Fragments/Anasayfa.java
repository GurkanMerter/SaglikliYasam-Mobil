package com.example.dengeliyasam.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dengeliyasam.Adapter.UserAdapter;
import com.example.dengeliyasam.Models.KullaniciBilgileri;
import com.example.dengeliyasam.R;
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

public class Anasayfa extends Fragment {

    List<String> userkeylist;
    View view;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    UserAdapter userAdapter;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_anasayfa, container, false);
        define();
        kullaniciGetir();
        return view;
    }

    void define() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        userkeylist = new ArrayList<>();
        recyclerView = view.findViewById(R.id.userlistview);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(layoutManager);
        userAdapter = new UserAdapter(userkeylist, getActivity(), getContext());
        recyclerView.setAdapter(userAdapter);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
    }

    void kullaniciGetir() {

        databaseReference.child("Kullanicilar").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                databaseReference.child("Kullanicilar").child(snapshot.getKey()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        KullaniciBilgileri kb = snapshot.getValue(KullaniciBilgileri.class);
                        if (!kb.getAd().equals("null") && !snapshot.getKey().equals(user.getUid())) {

                            userkeylist.add(snapshot.getKey());
                            userAdapter.notifyDataSetChanged();

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}