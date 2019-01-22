package com.mr.liu.modulebase.utils;

import android.content.Context;

import com.mr.liu.modulebase.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by mrliu on 2018/12/27.
 * 此类用于:日期工具类
 */

public class DateUtil {


    public static final String FMT_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public static String format(Date date, String format) {
        return new SimpleDateFormat(format).format(date);

    }

    /**
     * 判断是否为今天
     *
     * @param time 毫秒
     * @return true今天 false不是
     */
    public static boolean isToday(long time) {
        Calendar pre = Calendar.getInstance();
        Date predate = new Date(System.currentTimeMillis());
        pre.setTime(predate);
        Calendar cal = Calendar.getInstance();
        Date date = new Date(time);
        cal.setTime(date);
        if (cal.get(Calendar.YEAR) == (pre.get(Calendar.YEAR))) {
            int diffDay = cal.get(Calendar.DAY_OF_YEAR)
                    - pre.get(Calendar.DAY_OF_YEAR);

            if (diffDay == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * 将崩溃日志写道本地文件时使用
     * @param date
     * @return
     */
    public static String formatDateTime(Date date) {
        return format(date, FMT_DATETIME);
    }

    /*获取系统时间 格式为："yyyy/MM/dd "*/
    public static String getCurrentDate() {
        Date d = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sf.format(d);
    }
    /*时间戳转换成字符窜*/
    public static String getDateToString(String time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        long lcc_time = Long.valueOf(time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }

    /*时间戳转换成字符窜*/
    public static String getDateToStringLong(String time) {

        String re_StrTime = null;
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lcc_time = Long.valueOf(time);
        re_StrTime = sdf.format(new Date(lcc_time));
        return re_StrTime;
    }

    /*将字符串转为时间戳*/
    public static long getStringToDate(String time) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date();
        try{
            date = sf.parse(time);
        } catch(ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date.getTime();
    }

    static SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 将时间戳转换为"距离现在多久"的字符串
     */
    public static String getDateToToday(String timeServer) {

        long timeStart = 0;
        try {
            timeStart = sdf.parse(timeServer).getTime();
            //获取当前时间与获取时间的差值
            long newTime = System.currentTimeMillis() - (timeStart);
            //获取天数
            long day = newTime / 24 / 60 / 60 / 1000;
            //获取小时值
            long hour = (newTime - day * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            //获取分值
            long minute = (newTime - day * (1000 * 60 * 60 * 24) - hour * (1000 * 60 * 60)) / (1000 * 60);
            if (day >= 1) {
                return day + "天" + hour + "小时" + minute + "分钟前";
            } else {
                if (hour >= 1) {
                    return hour + "小时" + minute + "分钟前";
                } else {
                    if (minute >= 1) {
                        return minute + "分钟前";
                    } else {
                        return "刚刚";
                    }
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取星座工具类
     */
    public static String getConstellation(Context context, int mouth, int day){
        String starSeat = null;
        if ((mouth == 3 && day >= 21) || (mouth == 4 && day <= 19)) {
            starSeat = context.getString(R.string.aries);
        } else if ((mouth == 4 && day >= 20) || (mouth == 5 && day <= 20)) {
            starSeat = context.getString(R.string.taurus);
        } else if ((mouth == 5 && day >= 21) || (mouth == 6 && day <= 21)) {
            starSeat = context.getString(R.string.gemini);
        } else if ((mouth == 6 && day >= 22) || (mouth == 7 && day <= 22)) {
            starSeat = context.getString(R.string.cancer);
        } else if ((mouth == 7 && day >= 23) || (mouth == 8 && day <= 22)) {
            starSeat = context.getString(R.string.leo);
        } else if ((mouth == 8 && day >= 23) || (mouth == 9 && day <= 22)) {
            starSeat = context.getString(R.string.virgo);
        } else if ((mouth == 9 && day >= 23) || (mouth == 10 && day <= 23)) {
            starSeat = context.getString(R.string.libra);
        } else if ((mouth == 10 && day >= 24) || (mouth == 11 && day <= 22)) {
            starSeat = context.getString(R.string.scorpio);
        } else if ((mouth == 11 && day >= 23) || (mouth == 12 && day <= 21)) {
            starSeat = context.getString(R.string.sagittarius);
        } else if ((mouth == 12 && day >= 22) || (mouth == 1 && day <= 19)) {
            starSeat = context.getString(R.string.capricornus);
        } else if ((mouth == 1 && day >= 20) || (mouth == 2 && day <= 18)) {
            starSeat = context.getString(R.string.aquarius);
        } else {
            starSeat = context.getString(R.string.pisces);
        }
        return starSeat;
    }
}
