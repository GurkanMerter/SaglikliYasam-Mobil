package com.example.dengeliyasam.Models;

public class MesajModel {
    String tip, zaman, mesaj, from;
    Boolean seen;

    public MesajModel() {
    }

    public MesajModel(String from, String mesaj, Boolean seen, String tip, String zaman) {
        this.from = from;
        this.mesaj = mesaj;
        this.seen = seen;
        this.tip = tip;
        this.zaman = zaman;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }

    public String getZaman() {
        return zaman;
    }

    public void setZaman(String zaman) {
        this.zaman = zaman;
    }

    public String getMesaj() {
        return mesaj;
    }

    public void setMesaj(String mesaj) {
        this.mesaj = mesaj;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Boolean getSeen() {
        return seen;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }
}
