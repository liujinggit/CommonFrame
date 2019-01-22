package com.mr.liu.modulebase.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Iverson on 2016/12/28 下午4:30
 * 此类用于：关于数字的工具类
 */

public class NumberUtils {

    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^1[3|4|5|7|8]\\d{9}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    public static boolean isChinese(String text) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(text.charAt(0) + "");
        return m.matches();
    }
}
