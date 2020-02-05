package com.sergames.llistaarticles;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {
    public static String format(String input) {
        Date newDate = new Date();
        return new SimpleDateFormat("dd-MM-yyyy").format(newDate);
    }
}