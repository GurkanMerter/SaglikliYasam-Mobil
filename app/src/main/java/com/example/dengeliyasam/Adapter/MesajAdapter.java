package com.example.dengeliyasam.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dengeliyasam.Models.MesajModel;
import com.example.dengeliyasam.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MesajAdapter extends RecyclerView.Adapter<MesajAdapter.ViewHolder> {

    List<String> userkeylist;
    Activity activity;
    Context context;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth auth;
    FirebaseUser user;
    String userid;
    List<MesajModel> mesajModelList;
    boolean durum;
    int viewtypegönderildi = 1;
    int viewtypealındı = 2;

    public MesajAdapter(List<String> userkeylist, Activity activity, Context context, List<MesajModel> mesajModelList) {
        this.userkeylist = userkeylist;
        this.activity = activity;
        this.context = context;
        this.mesajModelList = mesajModelList;
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        userid = user.getUid();
        durum = false;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == viewtypegönderildi) {
            view = LayoutInflater.from(context).inflate(R.layout.mesaj_gonderildi, parent, false);
            return new ViewHolder(view);
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.mesaj_alindi, parent, false);
            return new ViewHolder(view);
        }

    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.mesajtext.setText(mesajModelList.get(position).getMesaj());


    }

    @Override
    public int getItemCount() {
        return mesajModelList.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (mesajModelList.get(position).getFrom().equals(userid)) {
            durum = true;
            return viewtypegönderildi;
        } else {
            durum = false;
            return viewtypealındı;
        }

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView mesajtext;

        public ViewHolder(View view) {
            super(view);
            if (durum) {
                mesajtext = view.findViewById(R.id.gönderildianmesaj);
            } else {
                mesajtext = view.findViewById(R.id.alinanmesaj);
            }
        }
    }


}