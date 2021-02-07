package com.example.dengeliyasam.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.dengeliyasam.Fragments.Anasayfa;
import com.example.dengeliyasam.Fragments.Profil;
import com.example.dengeliyasam.R;
import com.example.dengeliyasam.Fragments.Sohbet;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class KullaniciEkran extends AppCompatActivity {

    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kullanici_ekran);
        define();
        bottomNavCreater();
    }

    void define() {
        bottomNav = findViewById(R.id.bottom_nav);
    }

    void bottomNavCreater() {
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_holder, new Anasayfa()).commit();

        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment fragment = null;

                int itemId = item.getItemId();
                if (itemId == R.id.action_anasayfa) {
                    fragment = new Anasayfa();
                } else if (itemId == R.id.action_chat) {
                    fragment = new Sohbet();
                } else if (itemId == R.id.action_profile) {
                    fragment = new Profil();
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_holder, fragment).commit();
                return true;
            }
        });
    }

}