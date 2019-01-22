package com.mr.liu.modulebase.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by mrliu on 2018/12/27.
 * 此类用于:数据的工具类
 */

public class DataUtil {

    /**
     * 默认String-->int
     *
     * @param data
     * @return
     */
    public static int parseInt(String data) {
        int result = 0;
        try {
            result = Integer.parseInt(data);
        } catch (Exception e) {
        }
        return result;
    }

    public static Long parseLong(String data) {
        Long result = null;
        try {
            result = Long.parseLong(data);
        } catch (Exception e) {
        }
        return result;
    }

    public static float parseFloat(String data, float defaultValue) {
        float result = defaultValue;
        try {
            result = Float.parseFloat(data);
        } catch (Exception e) {
        }
        return result;
    }

    public static double parseDouble(String data) {
        double result = 0f;
        try {
            result = Double.parseDouble(data);
        } catch (Exception e) {
        }
        return result;
    }
    /**
     * 转换数据格式,有小数就显示一位小数,如果小数为.00显示整数
     * @param d
     * @return
     */
    public static String formatDouble(double d) {
        BigDecimal bg = new BigDecimal(d).setScale(1, RoundingMode.UP);
        double num = bg.doubleValue();
        if (Math.round(num) - num == 0) {
            return String.valueOf((long) num);
        }
        return String.valueOf(num);
    }

    // 单位换算
    public static String conversionNumber(long number){
        if(number/100000000>0){
            return number/100000000+"亿";
        }else if(number/10000>0){
            return number/10000+"万";
        }else{
            return Long.toString(number);
        }
    }


    //单位换算,保留两位小数
    public static String conversionNumberByDouble(double number) {
        String num;
        BigDecimal bigDecimal;
        if (number / 100000000 > 1) {
            bigDecimal = new BigDecimal((number / 100000000)).setScale(2, BigDecimal.ROUND_HALF_UP);
            num = bigDecimal + "亿";
        } else if (number / 10000 > 1) {
            bigDecimal = new BigDecimal((number / 100000000)).setScale(2, BigDecimal.ROUND_HALF_UP);
            num = bigDecimal + "万";
        } else {
            num = String.valueOf(number);
        }

        return String.valueOf(num);
    }
    //保留一位小数
    public static String conversionintByDouble(double number){
        if(number/100000000>1){
            return ((int)number/100000000)+"亿";
        }else if(number/10000>1){
            return ((int)number/10000)+"万";
        }else{
            String num = String.valueOf(number);
            BigDecimal bigDecimal = new BigDecimal(num);
            bigDecimal = bigDecimal.setScale(0, BigDecimal.ROUND_HALF_UP);
            return bigDecimal+"";
        }
    }

    /**
     * 格式化联系人手机号
     * @param number
     * @return
     */
    public static String fomatContactNumber(String number) {
        String str1 = number.replace(" ", "");
        String str2 = str1.replace("-", "");

        if (str2.startsWith("+86")) {
            return str2.substring(3);
        }
        return str2;
    }

    /**
     * 隐藏手机中间4位号码
     * 130****0000
     *
     * @param mobile_phone 手机号码
     * @return 130****0000
     */
    public static String hideMobilePhone4(String mobile_phone) {
        if (mobile_phone.length() != 11) {
            return "手机号码不正确";
        }
        return mobile_phone.substring(0, 3) + "****" + mobile_phone.substring(7, 11);
    }

    /**
     * 格式化银行卡 加*
     * 3749 **** **** 330
     *
     * @param cardNo 银行卡
     * @return 3749 **** **** 330
     */
    public static String formatCard(String cardNo) {
        if (cardNo.length() < 8) {
            return "银行卡号有误";
        }
        String card = "";
        card = cardNo.substring(0, 4) + " **** **** ";
        card += cardNo.substring(cardNo.length() - 4);
        return card;
    }
}
