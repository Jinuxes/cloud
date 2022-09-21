package com.jinuxes.cloud.utils;

import java.util.UUID;

public class UUIDUtils {
    /**
     * 获取UUID值
     * @return
     */
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
