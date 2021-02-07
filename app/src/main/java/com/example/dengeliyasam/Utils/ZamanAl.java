package com.example.dengeliyasam.Utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ZamanAl {

    public static String getDate(){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date bugün = Calendar.getInstance().getTime();
        String zaman=df.format(bugün);

        return zaman;
    }


}
