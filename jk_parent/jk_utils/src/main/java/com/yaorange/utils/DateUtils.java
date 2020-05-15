package com.yaorange.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static String getDateString(Date date,String pattern){
        if(pattern != null){
            format = new SimpleDateFormat(pattern);
        }
        return format.format(date);
    }
}
