package com.example.dengeliyasam.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dengeliyasam.Activities.SohbetActivity;
import com.example.dengeliyasam.Fragments.Sohbet;
import com.example.dengeliyasam.Fragments.SohbetProfil;
import com.example.dengeliyasam.Fragments.UserProfil;
import com.example.dengeliyasam.Models.KullaniciBilgileri;
import com.example.dengeliyasam.Models.MesajModel;
import com.example.dengeliyasam.R;
import com.example.dengeliyasam.Utils.ChangeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SohbetAdapter extends RecyclerView.Adapter<SohbetAdapter.ViewHolder> {

    List<String> userkeylist;
    Activity activity;
    Context context;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    FirebaseUser user;


    public SohbetAdapter(List<String> userkeylist, Activity activity, Context context) {
        this.userkeylist = userkeylist;
        this.activity = activity;
        this.context = context;
        this.firebaseDatabase = FirebaseDatabase.getInstance();
        this.databaseReference = firebaseDatabase.getReference();
        this.auth = FirebaseAuth.getInstance();
        this.user = auth.getCurrentUser();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.userlayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        databaseReference.child("Mesajlar").child(userkeylist.get(position)).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                holder.isim.setText("User Name");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        holder.mainlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ChangeFragment cf = new ChangeFragment(context);
                cf.changeWparametre(new SohbetProfil(), userkeylist.get(position));

            }
        });

    }

    @Override
    public int getItemCount() {
        return userkeylist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView isim;
        CircleImageView resim;
        LinearLayout mainlayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mainlayout = itemView.findViewById(R.id.usermainlayout);
            isim = itemView.findViewById(R.id.userisim);
            resim = itemView.findViewById(R.id.profile_foto);

        }
    }


}
