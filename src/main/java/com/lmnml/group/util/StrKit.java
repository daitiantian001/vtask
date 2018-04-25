package com.lmnml.group.util;

import java.util.UUID;

/**
 * Created by daitian on 2018/4/16.
 */
public class StrKit {
    /**
     * 字符串为 null 或者内部字符全部为 ' ' '\t' '\n' '\r' 这四类字符时返回 true
     */
    public static boolean isBlank(String str) {
        if (str == null) {
            return true;
        }
        int len = str.length();
        if (len == 0) {
            return true;
        }
        for (int i = 0; i < len; i++) {
            switch (str.charAt(i)) {
                case ' ':
                case '\t':
                case '\n':
                case '\r':
                    // case '\b':
                    // case '\f':
                    break;
                default:
                    return false;
            }
        }
        return true;
    }

    public static boolean notBlank(String str) {
        return !isBlank(str);
    }

    public static boolean notBlank(String... strings) {
        if (strings == null || strings.length == 0) {
            return false;
        }
        for (String str : strings) {
            if (isBlank(str)) {
                return false;
            }
        }
        return true;
    }

    public static boolean notNull(Object... paras) {
        if (paras == null) {
            return false;
        }
        for (Object obj : paras) {
            if (obj == null) {
                return false;
            }
        }
        return true;
    }

    public static String ID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String addPoint(String money){
        StringBuilder  sb = new StringBuilder (money);
        int length = sb.length();
        if(length==0){
            sb.append("0.00");
        }else if(length==2){
            sb.insert(0, "0.");
        }else if(length==1){
            sb.insert(0, "0.0");
        }else{
            sb.insert(length-2,".");
        }
        return sb.toString();
    }
}
