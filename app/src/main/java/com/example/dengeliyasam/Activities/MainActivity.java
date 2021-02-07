package com.example.dengeliyasam.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.dengeliyasam.R;

public class MainActivity extends AppCompatActivity {

    Button kullaniciButton, egitmenbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        define();
        kullaniciClick();
        egitmenClick();
    }

    void define(){
        kullaniciButton = findViewById(R.id.kullaniciButton);
        egitmenbutton = findViewById(R.id.egitmenButton);
    }

    void kullaniciClick(){
        kullaniciButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent kullaniciIntent = new Intent(getApplicationContext() , KullaniciGirisActivity.class);
                startActivity(kullaniciIntent);
            }
        });
    }

    void egitmenClick(){
        egitmenbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent egitmenIntent = new Intent(getApplicationContext() , EgitmenGirisActivity.class);
                startActivity(egitmenIntent);
            }
        });
    }



}