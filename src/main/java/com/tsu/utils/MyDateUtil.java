package com.tsu.utils;

import cn.hutool.core.date.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author ZZZ
 * @create 2020/10/11/15:36
 */

public class MyDateUtil {

    public static Date stringToDate(String s, String pattern) {
        return DateUtil.parse(s, pattern).toJdkDate();
    }

    public static Date stringToDate(String s) {
        return stringToDate(s, "yyyy-MM-dd hh:mm:ss");
    }


    public static Date getBeforeHourTime(int hour) throws ParseException {
        String dateStr = "";
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - hour);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
        dateStr = df.format(calendar.getTime());
        return df.parse(dateStr);
    }

}
