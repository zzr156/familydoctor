package com.ylz.packcommon.common.util;

import java.util.Calendar;

/**
 * Created by zzl on 2017/6/22.
 */
public class ChildHealthPlanUtil {
    public String plan(String strDate,Integer num){
        Calendar cal = ExtendDate.getCalendar(strDate);
        cal.add(Calendar.MONTH,num);
        return ExtendDate.getYMD(cal);
    }
    public Calendar planTime(String strDate,Integer num){
        Calendar cal = ExtendDate.getCalendar(strDate);
        cal.add(Calendar.MONTH,num);
        return cal;
    }
}
