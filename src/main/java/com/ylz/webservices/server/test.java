package com.ylz.webservices.server;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by WangCheng on 2018/07/25.
 */
public class test {

    /**
     * 当前第几季度
     * @param date
     * @return
     */
    public static int getSeason(Date date) {

        int season = 0;

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        switch (month) {
            case Calendar.JANUARY:
            case Calendar.FEBRUARY:
            case Calendar.MARCH:
                season = 1;
                break;
            case Calendar.APRIL:
            case Calendar.MAY:
            case Calendar.JUNE:
                season = 2;
                break;
            case Calendar.JULY:
            case Calendar.AUGUST:
            case Calendar.SEPTEMBER:
                season = 3;
                break;
            case Calendar.OCTOBER:
            case Calendar.NOVEMBER:
            case Calendar.DECEMBER:
                season = 4;
                break;
            default:
                break;
        }
        return season;
    }

    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        int result = getSeason(calendar.getTime());
        System.out.println(result);
    }
}
