package com.devil.sell.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author Hanyanjiao
 * @date 2020/6/15
 */
public class JsonUtil {

    public static String toJson(Object obj){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(obj);
    }
}
