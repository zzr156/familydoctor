package com.ylz.packcommon.integrate.sdk.util.dtkl.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {

	public static String minusMin(String sTime, int minus) {
		DateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
		Date date = null;
		try {
			date = format.parse(sTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Date b = new Date(date.getYear(), date.getMonth(), date.getDate(),
				date.getHours(), date.getMinutes(), date.getSeconds());
		b.setMinutes(b.getMinutes() - minus);
		return format.format(b);
	}

	public static Date minusMin(Date time, int minus) {
		Date b = new Date(time.getYear(), time.getMonth(), time.getDate(),
				time.getHours(), time.getMinutes(), time.getSeconds());
		b.setMinutes(b.getMinutes() - minus);
		return b;
	}
}
