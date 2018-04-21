package com.lmnml.group.util;

import com.google.gson.Gson;

/**
 * Created by daitian on 2018/4/21.
 */
public class JsonUtil {
    public static final Gson gson = new Gson();

    public static final String toJson(Object o) {
        return gson.toJson(o);
    }

    public static <T> T fromJson(String json, Class<T> tClass) {
        return gson.fromJson(json, tClass);
    }
}
