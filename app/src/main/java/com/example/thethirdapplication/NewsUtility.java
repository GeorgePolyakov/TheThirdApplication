package com.example.thethirdapplication;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class NewsUtility {

    private static Calendar c;
    private static String currentDate;

    public static String getSpecificDate() {
        c = new GregorianCalendar();
        c.add(Calendar.MONTH, -1);
        Date date = c.getTime();
        DateFormat df = new SimpleDateFormat("YYYY-MM-dd");
        currentDate = df.format(date);
        return currentDate;
    }
}
