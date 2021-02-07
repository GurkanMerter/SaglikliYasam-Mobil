package com.example.dengeliyasam.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.dengeliyasam.Adapter.MesajAdapter;
import com.example.dengeliyasam.Models.MesajModel;
import com.example.dengeliyasam.R;
import com.example.dengeliyasam.Utils.ZamanAl;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SohbetActivity extends AppCompatActivity {

    TextView userisim;
    DatabaseReference reference;
    FirebaseDatabase database;
    FirebaseUser user;
    FirebaseAuth auth;
    ImageButton gönder;
    EditText mesajalanı;
    List<MesajModel> modelList;
    RecyclerView recyclerView;
    MesajAdapter mesajAdapter;
    List<String> keylist;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sohbet);
        define();
        aksiyon();
        mesajyükle();
    }

    void define() {
        userid = getIntent().getExtras().getString("userid");
        userisim = findViewById(R.id.mesajisim);
        userisim.setText(getUserName());
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        gönder = findViewById(R.id.mesajgönder);
        mesajalanı = findViewById(R.id.mesajalanı);
        modelList = new ArrayList<>();
        keylist = new ArrayList<>();
        recyclerView = findViewById(R.id.sohbetrcview);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(SohbetActivity.this, 1);
        recyclerView.setLayoutManager(layoutManager);
        mesajAdapter = new MesajAdapter(keylist, SohbetActivity.this, SohbetActivity.this, this.modelList);
        recyclerView.setAdapter(mesajAdapter);
    }

    void aksiyon() {
        gönder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mesaj = mesajalanı.getText().toString();
                mesajalanı.setText("");
                mesajgönder(user.getUid(), getId(), "text", ZamanAl.getDate(), false, mesaj);
            }
        });
    }


    public String getUserName() {
        String username = getIntent().getExtras().getString("kullanicisim");
        return username;
    }

    public String getId() {

        String id = getIntent().getExtras().getString("id");

        return id;
    }

    void mesajyükle() {

        reference.child("Mesajlar").child(user.getUid()).child(getId()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                MesajModel mesajModel = snapshot.getValue(MesajModel.class);
                modelList.add(mesajModel);
                mesajAdapter.notifyDataSetChanged();
                keylist.add(snapshot.getKey());
                recyclerView.scrollToPosition(modelList.size() - 1);
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


    public void mesajgönder(String userid, String otherid, String textype, String date, Boolean seen, String msjtxt) {

        String mesajid = reference.child("Mesajlar").child(userid).child(otherid).push().getKey();
        Map msjmap = new HashMap();
        msjmap.put("tip", textype);
        msjmap.put("seen", seen);
        msjmap.put("zaman", date);
        msjmap.put("mesaj", msjtxt);
        msjmap.put("from", userid);
        reference.child("Mesajlar").child(userid).child(otherid).child(mesajid).setValue(msjmap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                reference.child("Mesajlar").child(otherid).child(userid).child(mesajid).setValue(msjmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
            }
        });

    }
}