package com.guojijian.pethospital.commons.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

//时间工具类
public class DateUtils {
    //转换格式：yyyy-MM-dd HH-mm-ss
    public static String dateFormatDateTime(Date date){
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        String dateTime=format.format(date);
        return dateTime;
    }
}
