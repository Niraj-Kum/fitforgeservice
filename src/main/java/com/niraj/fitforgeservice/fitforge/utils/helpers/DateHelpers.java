package com.niraj.fitforgeservice.fitforge.utils.helpers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class DateHelpers {

    public static String getFormattedDateTime(Date date) {
        if(Objects.isNull(date)) {
            date = new Date();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("MMMM dd, yyyy, hh:mm a", Locale.ENGLISH);
        return formatter.format(date);
    }
}
