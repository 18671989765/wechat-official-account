package com.wechat.official.account.wechatofficcialaccount.util;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Administrator
 * @version 1.0
 * @date 2020/9/20 15:39
 */
public class SimpleFormatterUtil {

    public static SimpleDateFormat simpleDateFormat;

    public static  String simpleFormatter() {
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       return  simpleDateFormat.format(new Date());
    }
}
