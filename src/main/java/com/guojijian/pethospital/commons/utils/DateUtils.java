package com.guojijian.pethospital.commons.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

//时间工具类
public class DateUtils {
    //转换格式：yyyy-MM-dd HH-mm-ss
    public static String dateFormatDateTime(Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        return sdf.format(date);
    }

    //转换格式：yyyy-MM-dd
    public static String dateFormatDate(Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }
}
