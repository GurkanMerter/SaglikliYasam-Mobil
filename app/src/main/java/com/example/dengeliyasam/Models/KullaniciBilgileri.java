package com.example.dengeliyasam.Models;

public class KullaniciBilgileri {
    private String ad, soyad, resim, email, sifre, date, cinsiyet, boy, kilo, egitim_seviyesi, hakkimda;

    public KullaniciBilgileri() {

    }

    public KullaniciBilgileri(String ad, String soyad, String resim, String email, String sifre, String date, String cinsiyet, String boy, String kilo, String egitim_seviyesi, String hakkimda) {
        this.ad = ad;
        this.soyad = soyad;
        this.resim = resim;
        this.email = email;
        this.sifre = sifre;
        this.date = date;
        this.cinsiyet = cinsiyet;
        this.boy = boy;
        this.kilo = kilo;
        this.egitim_seviyesi = egitim_seviyesi;
        this.hakkimda = hakkimda;

    }


    public String getAd() {
        return ad;
    }

    public String getResim() {
        return resim;
    }

    public void setResim(String resim) {
        this.resim = resim;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getSoyad() {
        return soyad;
    }

    public void setSoyad(String soyad) {
        this.soyad = soyad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSifre() {
        return sifre;
    }

    public void setSifre(String sifre) {
        this.sifre = sifre;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCinsiyet() {
        return cinsiyet;
    }

    public void setCinsiyet(String cinsiyet) {
        this.cinsiyet = cinsiyet;
    }

    public String getBoy() {
        return boy;
    }

    public void setBoy(String boy) {
        this.boy = boy;
    }

    public String getKilo() {
        return kilo;
    }

    public void setKilo(String kilo) {
        this.kilo = kilo;
    }

    public String getEgitim_seviyesi() {
        return egitim_seviyesi;
    }

    public void setEgitim_seviyesi(String egitim_seviyesi) {
        this.egitim_seviyesi = egitim_seviyesi;
    }

    public String getHakkimda() {
        return hakkimda;
    }

    public void setHakkimda(String hakkimda) {
        this.hakkimda = hakkimda;
    }
}
