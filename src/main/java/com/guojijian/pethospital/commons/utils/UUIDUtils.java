package com.guojijian.pethospital.commons.utils;

import java.util.UUID;

//UUID的工具类
public class UUIDUtils {

    //获取UUID值
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
