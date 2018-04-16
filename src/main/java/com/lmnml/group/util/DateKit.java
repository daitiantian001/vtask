package com.lmnml.group.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by daitian on 2018/4/16.
 */
public class DateKit {
    //获取当前时间后几分钟
    public static Date getMin() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -30);
        return calendar.getTime();
    }
}
