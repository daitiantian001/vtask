package com.lmnml.group.util;

import com.google.gson.Gson;
import com.lmnml.group.common.excel2.ExcelNode;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

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

    public static List<ExcelNode> toExcel(String s) {
        List<ExcelNode> nodes=new ArrayList<>();
        gson.fromJson(s,List.class).forEach(k->nodes.add(gson.fromJson(k.toString(), ExcelNode.class)));
        return nodes;
    }
}
