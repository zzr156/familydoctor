package com.ylz.packcommon.common.util;

import com.ylz.packcommon.common.comEnum.ExtendDateType;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExtendDate
{
	static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	static final SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");

	static final SimpleDateFormat formatter7 = new SimpleDateFormat("yyyyMMdd");

	static final SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm");

	static final SimpleDateFormat formatter3 = new SimpleDateFormat("yyyy-MM-dd HH:mm");

	static final SimpleDateFormat formatter4 = new SimpleDateFormat("yyyy年MM月dd日");

    static final SimpleDateFormat formatter5 = new SimpleDateFormat("MM-dd");
    static final SimpleDateFormat formatter9 = new SimpleDateFormat("MM-dd HH:mm");

    static final SimpleDateFormat formatter6 = new SimpleDateFormat("yyyy");
	static final SimpleDateFormat formatter8 = new SimpleDateFormat("MM月dd日");
	static final SimpleDateFormat formatter10 = new SimpleDateFormat("dd日");
	static final SimpleDateFormat formatter11 = new SimpleDateFormat("yyyyMMddhhmmss");
	static final SimpleDateFormat formatter12 = new SimpleDateFormat("yyyy-MM");
	static final SimpleDateFormat formatter13 = new SimpleDateFormat("yyyyMM");
	static final SimpleDateFormat formatter17 = new SimpleDateFormat("yyyyMMddHHmmss");

	public static String getTime(Calendar cal){
		if (cal == null) {
			return null;
		}
		return formatter17.format(cal.getTime());
	}

    public static String getYYYY(java.util.Calendar cal)
    {
        if (cal == null) {
            return null;
        }
        return formatter6.format(cal.getTime());

    }

	public ExtendDate()
	{
	}

	public static int compare(Calendar cal1, Calendar cal2)
	{
		
		if (cal1 == null || cal2 == null) {
            throw new RuntimeException("两个时间均不能为空");
        }
		if (cal1.getTimeInMillis() == cal2.getTimeInMillis()) {
            return 0;
        }
		if (cal1.getTimeInMillis() > cal2.getTimeInMillis()) {
            return 1;
        }
		if (cal1.getTimeInMillis() < cal2.getTimeInMillis()) {
            return 2;
        }
		throw new RuntimeException("不能比较");
	}

	public static Calendar getDayOfStart(Calendar cal)
	{
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal;
	}

	public static Calendar getDayOfEnd(Calendar cal)
	{
		cal.set(Calendar.HOUR, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal;
	}

	public static String getHHMM(java.sql.Time time)
	{
		if (time == null) {
            return null;
        }
		return formatter2.format(time);

	}
	public static String getHHMM(Timestamp time)
	{
		if (time == null) {
            return null;
        }
		return formatter2.format(time);

	}

	public static String getChineseYMD(Calendar cal)
	{
		if (cal == null) {
            return null;
        }
		return formatter4.format(cal.getTime());

	}
	public static String getChineseYMD(Timestamp cal)
	{
		if (cal == null) {
            return null;
        }
		return formatter4.format(cal.getTime());

	}
	public static String getChineseYMD(Date cal)
	{
		if (cal == null) {
            return null;
        }
		return formatter4.format(cal.getTime());

	}

	public static String getChineseMD(String cal){
		if (cal == null) {
            return null;
        }
		return formatter8.format(getCalendar(cal).getTime());

	}

	public static String getChineseDD(String cal) {
		if (cal == null) {
            return null;
        }
		return formatter10.format(getCalendar(cal).getTime());
	}

	public static String getHHMM(java.util.Date date)
	{
		if (date == null) {
            return null;
        }
		return formatter2.format(date);

	}

	public static String getHHMM(java.util.Calendar cal)
	{
		if (cal == null) {
            return null;
        }
		return formatter2.format(cal.getTime());

	}

	@SuppressWarnings("static-access")
	public static int getYear()
	{
		Calendar cal = Calendar.getInstance();
		return cal.get(cal.YEAR);
	}

	public static java.sql.Time getSqlTime(String strTime)
	{
		if (strTime == null) {
            return null;
        }
		String[] strs = StringUtils.split(strTime, ":");
		if (strs.length == 2)
		{
			if (Integer.parseInt(strs[0]) < 24 && Integer.parseInt(strs[1]) < 60) {
                return java.sql.Time.valueOf(strTime + ":0");
            }
			return null;
		}
		else
		{
			return null;
		}
	}
public  static  java.sql.Timestamp getSqlTimestamp(String strTimestamp){
	if (strTimestamp == null) {
        return null;
    }
	String[] strs = StringUtils.split(strTimestamp, ":");
	if (strs.length == 2)
	{
		if (Integer.parseInt(strs[0]) < 24 && Integer.parseInt(strs[1]) < 60) {
            return Timestamp.valueOf(strTimestamp + ":0");
        }
		return null;
	}
	else
	{
		return null;
	}
}
	public static String getYMD_h_m_s(Calendar cal)
	{
		if (cal == null) {
            return null;
        }
		return formatter.format(cal.getTime());
	}

	public static String getYMD_h_m_s(java.sql.Timestamp date)
	{
		Calendar cal = new java.util.GregorianCalendar();
		cal.setTime(date);
		return getYMD_h_m_s(cal);
	}

	public static String getYMD(java.sql.Timestamp date)
	{
		Calendar cal = new java.util.GregorianCalendar();
		cal.setTime(date);
		return getYMD(cal);
	}

	public static String getYMD_h_m(Calendar cal)
	{
		if (cal == null) {
            return null;
        }
		return formatter3.format(cal.getTime());

	}

	public static String getYMD_h_m(java.sql.Timestamp  date)
	{
		Calendar cal = new java.util.GregorianCalendar();
		cal.setTime(date);
		return getYMD_h_m(cal);

	}

	public static String getYMD(Calendar cal)
	{
		if (cal == null) {
            return null;
        }
		return formatter1.format(cal.getTime());

	}
	public static String getYM(Calendar cal)
	{
		if (cal == null) {
            return null;
        }
		return formatter12.format(cal.getTime());

	}
	public static String getYM(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return getYM(cal);
	}
	public static String getYYYYMMD(Calendar cal)
	{
		if (cal == null) {
            return null;
        }
		return formatter7.format(cal.getTime());

	}

	public static Calendar getYYYYMMD(String cal){
		if (cal == null) {
            return null;
        }
		Calendar calendar = Calendar.getInstance();
		try {
			Date date = formatter7.parse(cal);
			calendar.setTime(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return calendar;

	}
	public static String getYYYYMM(Calendar cal)
	{
		if (cal == null) {
            return null;
        }
		return formatter13.format(cal.getTime());

	}

    public static String getMD(Calendar cal)
    {
        if (cal == null) {
            return null;
        }
        return formatter5.format(cal.getTime());

    }
	public static String getMD(Timestamp cal)
	{
		if (cal == null) {
            return null;
        }
		return formatter5.format(cal.getTime());

	}

	public static String getMD_h_m(Timestamp cal)
	{
		if (cal == null) {
            return null;
        }
		return formatter9.format(cal.getTime());

	}

	public static String getYMD(java.sql.Date date)
	{
		Calendar cal = new java.util.GregorianCalendar();
		cal.setTime(date);
		return getYMD(cal);
	}

	public static String getYMD(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return getYMD(cal);
	}
	
	public static String getYMD_h_m_s(java.util.Date date)
	{
		Calendar cal = new java.util.GregorianCalendar();
		cal.setTime(date);
		return getYMD_h_m_s(cal);
	}

	public static Calendar getCalendar(String strDate)
	{
		if (strDate == null || strDate.trim().length() == 0) {
            return null;
        }
		try
		{
			
			strDate = strDate.replaceAll("-", "/");
			
			SimpleDateFormat _f = new SimpleDateFormat("yyyy/MM/dd");
			if(strDate.length()>10) {
                _f = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            }
			
		 
			java.util.Date date =  _f.parse(strDate);
			 
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			// calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
			return calendar;
		}
		catch (Exception ex)

		{
			ex.printStackTrace();
			return null;
		}

	}


	public static boolean getCalendarFlag(String strDate) {
		boolean flag = true;
		if (strDate != null && strDate.trim().length() != 0) {
			try {
				strDate = strDate.replaceAll("-", "/");
				SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd");
				if (strDate.length() > 10) {
					f = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				}

				Date date = f.parse(strDate);
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(date);
				return flag;
			} catch (Exception var4) {
				var4.printStackTrace();
				flag = false;
				return flag;
			}
		} else {
			return flag;
		}
	}

	public static Calendar getCalendarYmshms(String strDate){
		if (strDate == null || strDate.trim().length() == 0) {
            return null;
        }
		try
		{
			java.util.Date date =  formatter11.parse(strDate);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			return calendar;
		}
		catch (Exception ex)

		{
			ex.printStackTrace();
			return null;
		}

	}


	public static Calendar getCalendarms(String strDate)
	{
		if (strDate == null || strDate.trim().length() == 0) {
            return null;
        }
		try
		{

			strDate = strDate.replaceAll("-", "/");

			SimpleDateFormat _f = new SimpleDateFormat("yyyy/MM/dd");
			if(strDate.length()>10) {
                _f = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            }


			java.util.Date date =  _f.parse(strDate);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			// calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 1);
			return calendar;
		}
		catch (Exception ex)

		{
			ex.printStackTrace();
			return null;
		}

	}

	public static Calendar getCalendarzw(String strDate) {
		if (strDate == null || strDate.trim().length() == 0) {
            return null;
        }
		try {
			strDate = strDate.replaceAll("年", "/");
			strDate = strDate.replaceAll("月", "/");
			strDate = strDate.replaceAll("日", "");
			SimpleDateFormat _f = new SimpleDateFormat("yyyy/MM/dd");
			if (strDate.length() > 10) {
                _f = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            }
			java.util.Date date = _f.parse(strDate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			return calendar;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@SuppressWarnings("static-access")
	public static Calendar addDayNumNoRelaxDate(Calendar cal, int dayNum)
	{
		for (int i = 1; i <= dayNum; i++)
		{
			cal.add(cal.DATE, i);
			while (cal.get(cal.DAY_OF_WEEK) == 7 || cal.get(cal.DAY_OF_WEEK) == 1)
			{
				cal.add(cal.DATE, 1);
			}

		}
		return cal;

	}

	public static Calendar add(Calendar cal, int num, int addType)
	{
		Calendar _c=getCalendar(getYMD_h_m_s(cal));
		_c.add(addType, num);
		return _c;

	}

	/**
	 * 取得星期几，符合中国人的习惯
	 *
	 * @param cal Calendar
	 * @return int
	 */
	@SuppressWarnings("static-access")
	public static int getDayOfWeek(Calendar cal)
	{
		if (cal == null) {
            return -1;
        }
		int _i = cal.get(cal.DAY_OF_WEEK) - 1;
		if (_i == 0) {
            _i = 7;
        }

		return _i;
	}

	@SuppressWarnings("static-access")
	public static int getDayOfWeek(java.sql.Date date)
	{

		if (date == null) {
            return -1;
        }
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(date.getTime());
		int _i = cal.get(cal.DAY_OF_WEEK) - 1;
		if (_i == 0) {
            _i = 7;
        }

		return _i;
	}

	/**
	 * get the last date of param cal
	 *
	 * @param cal Calendar
	 * @return Calendar
	 */
	@SuppressWarnings("static-access")
	public static Calendar getLastDayOfMonth(Calendar cal)
	{
		int maxDate = -1;
		maxDate = cal.getActualMaximum(cal.DAY_OF_MONTH);
		System.out.println(maxDate);
		cal.set(cal.DAY_OF_MONTH, maxDate);
		System.out.println(getYMD(cal));
		return cal;
	}

	public static int getDayNumBetween2Date(Calendar startCal0, Calendar toCal0)
	{

		if (startCal0 == null || toCal0 == null) {
            return -1;
        }
		Calendar startCal = Calendar.getInstance();
		Calendar toCal = Calendar.getInstance();
		startCal.setTimeInMillis(startCal0.getTimeInMillis());
		toCal.setTimeInMillis(toCal0.getTimeInMillis());

		startCal.set(Calendar.HOUR, 00);
		startCal.set(Calendar.MINUTE, 00);
		startCal.set(Calendar.SECOND, 000);
		toCal.set(Calendar.HOUR, 00);
		toCal.set(Calendar.MINUTE, 00);
		toCal.set(Calendar.SECOND, 000);
		long dateRange = toCal.getTimeInMillis() - startCal.getTimeInMillis();
		long oneDay = 1000 * 3600 * 24;
		int diffdays = (int) (dateRange / oneDay);

		return diffdays;
	}

	/**
	 * 通过日期与时间相加返回日历型(Calendar)
	 *
	 * @param date Date 日期
	 * @param time Time 时间
	 * @return Calendar 日历
	 */
	@SuppressWarnings("static-access")
	public static Calendar getCalendar(java.sql.Date date, java.sql.Time time)
	{
		Calendar _cal1 = new java.util.GregorianCalendar();
		Calendar _cal2 = new java.util.GregorianCalendar();
		_cal1.setTimeInMillis(date.getTime());
		_cal2.setTimeInMillis(time.getTime());

		_cal1.set(_cal1.HOUR, _cal2.get(_cal2.HOUR));
		_cal1.set(_cal1.MINUTE, _cal2.get(_cal2.MINUTE));
		_cal1.set(_cal1.SECOND, _cal2.get(_cal2.SECOND));
		return _cal1;
	}

	public static Calendar getCalendar(Calendar cal, java.sql.Time time)
	{
		if (cal == null || time == null) {
            return null;
        }
		Calendar _calTime = new GregorianCalendar();
		Calendar _cal = new GregorianCalendar();
		_cal.setTimeInMillis(cal.getTimeInMillis());
		_calTime.setTimeInMillis(time.getTime());
		_cal.set(Calendar.HOUR_OF_DAY, _calTime.get(Calendar.HOUR_OF_DAY));
		_cal.set(Calendar.MINUTE, _calTime.get(Calendar.MINUTE));
		_cal.set(Calendar.SECOND, _calTime.get(Calendar.SECOND));
		return _cal;
	}

	public static String getStringInteger(char chr, String style)
	{
		String[] strValue = new String[]
		{ "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
		if (style.equalsIgnoreCase("lower")) {
            strValue = new String[]
            { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九" };
        }
		switch (chr)
		{
			case '0':
				return strValue[0];
			case '1':
				return strValue[1];
			case '2':
				return strValue[2];
			case '3':
				return strValue[3];
			case '4':
				return strValue[4];
			case '5':
				return strValue[5];
			case '6':
				return strValue[6];
			case '7':
				return strValue[7];
			case '8':
				return strValue[8];
			case '9':
				return strValue[9];
		}
		return "";
	}

	public static String getIntegerChinase(long lngValue)
	{
		if (lngValue == 0) {
            return "零";
        }
		String[] bit = new String[]
		{ "", "十", "百", "千", "万", "十万", "百万", "千万", "亿", "十亿", "百亿", "千亿", "万亿", "十万亿" };
		String strRtn = "";
		String value = String.valueOf(lngValue);
		char[] chars = value.toCharArray();
		for (int i = 0; i < chars.length; i++)
		{
			if (chars[i] == '0')
			{
				if (i != chars.length - 1)
				{
					if (chars[i - 1] != '0') {
                        strRtn += getStringInteger(chars[i], "lower");
                    }
				}
				else
				{
					if (chars[i] != '0') {
                        strRtn += getStringInteger(chars[i], "lower");
                    }
				}
			}
			else
			{
				strRtn += getStringInteger(chars[i], "lower");
				strRtn += bit[chars.length - 1 - i];
			}
		}
		while (strRtn.indexOf("亿") != -1)
		{
			String _string = strRtn;
			_string = _string.replaceFirst("亿", "");
			if (_string.indexOf("亿") == -1) {
                break;
            }
			strRtn = _string;
		}
		while (strRtn.indexOf("万") != -1)
		{
			String _string = strRtn;
			_string = _string.replaceFirst("万", "");
			if (_string.indexOf("万") == -1) {
                break;
            }
			strRtn = _string;
		}
		if ((strRtn.length() - 1) == strRtn.lastIndexOf("零")) {
            return strRtn.substring(0, strRtn.lastIndexOf("零"));
        }
		return strRtn;
	}

	private static String getStringChinase(String string)
	{
		char[] chars = string.toCharArray();
		String strRtn = "";
		for (int i = 0; i < chars.length; i++)
		{
			strRtn += getStringInteger(chars[i], "lower");
		}
		return strRtn;
	}

	public static String getCalendarToChinase(Calendar calendar, String format)
	{
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		int second = calendar.get(Calendar.SECOND);

		String yyyy = getStringChinase(String.valueOf(year));
		String y2 = String.valueOf(year % 100);
		y2 = (y2.length() < 2) ? "0" + y2 : y2;
		String yy = getStringChinase(y2);

		String M = getIntegerChinase(month);
		if (month >= 10 && month < 20) {
            M = M.substring(1);
        }
		String MM = M;
		if (month < 10) {
            MM = "零" + MM;
        }

		String d = getIntegerChinase(day);
		if (day >= 10 && day < 20) {
            d = d.substring(1);
        }
		String dd = d;
		if (day < 10) {
            dd = "零" + dd;
        }

		String H = getIntegerChinase(hour);
		if (hour >= 10 && hour < 20) {
            H = H.substring(1);
        }
		String HH = H;
		if (hour < 10) {
            HH = "零" + HH;
        }

		String m = getIntegerChinase(minute);
		if (minute >= 10 && minute < 20) {
            m = m.substring(1);
        }
		String mm = m;
		if (minute < 10) {
            mm = "零" + mm;
        }

		String S = getIntegerChinase(second);
		if (second >= 10 && second < 20) {
            S = S.substring(1);
        }
		String SS = S;
		if (second < 10) {
            SS = "零" + SS;
        }

		format = format.replaceAll("yyyy", yyyy);
		format = format.replaceAll("yy", yy);
		format = format.replaceAll("MM", MM);
		format = format.replaceAll("M", M);
		format = format.replaceAll("dd", dd);
		format = format.replaceAll("d", d);
		format = format.replaceAll("HH", HH);
		format = format.replaceAll("H", H);
		format = format.replaceAll("mm", mm);
		format = format.replaceAll("m", m);
		format = format.replaceAll("SS", SS);
		format = format.replaceAll("S", S);

		return format;
	}

	@SuppressWarnings("unused")
	public static float getDayNumBetween2DateTiem(Calendar startCal0, Calendar toCal0)
	{
		if (startCal0 == null || toCal0 == null) {
            return -1;
        }

		long dateRange = toCal0.getTimeInMillis() - startCal0.getTimeInMillis();
		long oneDay = 24 * 60 * 60 * 1000;
		int diffdays = (int) (dateRange / oneDay);

		return div(dateRange, oneDay, 2);
	}

	@SuppressWarnings("unused")
	public static float getDayNumBetween2DateTiem(Calendar ssCal, Calendar stCal, Calendar dsCal, Calendar dtCal)
	{
		if (ssCal == null || stCal == null) {
            return -1;
        }
		Calendar _sCal = ssCal, _tCal = stCal;

		if (dsCal != null && dsCal.getTimeInMillis() >= ssCal.getTimeInMillis() && dsCal.getTimeInMillis() <= stCal.getTimeInMillis())
		{
			_sCal = dsCal;
		}
		if (dtCal != null && dtCal.getTimeInMillis() >= ssCal.getTimeInMillis() && dtCal.getTimeInMillis() <= stCal.getTimeInMillis())
		{
			_tCal = dtCal;
		}
		if (dsCal != null && dsCal.getTimeInMillis() > stCal.getTimeInMillis() || dtCal != null && dtCal.getTimeInMillis() < ssCal.getTimeInMillis())
		{
			return -1;
		}

		long dateRange = _tCal.getTimeInMillis() - _sCal.getTimeInMillis();
		long oneDay = 24 * 60 * 60 * 1000;
		int diffdays = (int) (dateRange / oneDay);

		return div(dateRange, oneDay, 2);
	}

	private static float div(double v1, double v2, int scale)
	{
		if (v1 == v2 && v1 == 0) {
            return 0;
        }
		if (scale < 0)
		{
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		if (v2 == 0) {
            throw new IllegalArgumentException("v2 can not be zero");
        }
		if (v1 == 0) {
            return 0;
        }
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).floatValue();
	}


	public static Map<String,String> getDateVaild(Calendar calendar){
		Map<String,String > map = new HashMap<String,String>();
		String start = ExtendDate.getChineseYMD(calendar);
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);
		String end = ExtendDate.getChineseYMD(calendar);
		map.put("start",start);
		map.put("end",end);
		return  map;
	}

	/**
	 * 计算两个日期之间相差的天数
	 * @param smdate 较小的时间
	 * @param bdate  较大的时间
	 * @return 相差天数
	 */
	public static int daysBetween(Date smdate,Date bdate){
		try{
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			smdate=sdf.parse(sdf.format(smdate));
			bdate=sdf.parse(sdf.format(bdate));
			Calendar cal = Calendar.getInstance();
			cal.setTime(smdate);
			long time1 = cal.getTimeInMillis();
			cal.setTime(bdate);
			long time2 = cal.getTimeInMillis();
			long between_days=(time2-time1)/(1000*3600*24);

			return Integer.parseInt(String.valueOf(between_days));
		}catch (Exception e){
			e.printStackTrace();;
		}
		return  0;
	}

	/**
	 *字符串的日期格式的计算
	 */
	public static int daysBetween(String smdate,String bdate){
		try {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			Calendar cal = Calendar.getInstance();
			cal.setTime(sdf.parse(smdate));
			long time1 = cal.getTimeInMillis();
			cal.setTime(sdf.parse(bdate));
			long time2 = cal.getTimeInMillis();
			long between_days=(time2-time1)/(1000*3600*24);

			return Integer.parseInt(String.valueOf(between_days));
		}catch (Exception e){
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * 取月份之间周时间
	 * @param startYyyyMm
	 * @param endYyyyMm
	 * @return
	 */
	public static List<String> mWeek(String startYyyyMm,String endYyyyMm){
		List<String> ls=new ArrayList<>();
		Calendar startd=ExtendDate.getCalendar(startYyyyMm+"-01");
		Calendar endd=ExtendDate.getCalendar(endYyyyMm+"-01");
		endd.set(Calendar.DAY_OF_MONTH, startd.getActualMaximum(Calendar.DAY_OF_MONTH));
		startd.add(Calendar.DATE,8-startd.get(Calendar.DAY_OF_WEEK));
		ls.add(ExtendDate.getYMD(startd)+" 23:59:59");
		for (int i=0;i<999;i++) {
			if(endd.before(startd)){
				break;
			}
			startd.add(Calendar.WEEK_OF_YEAR,1);
			ls.add(ExtendDate.getYMD(startd)+" 23:59:59");
		}
		return ls;
	}

	/**
	 * 取两个时间之间周时间
	 * @param startYyyyMmDd
	 * @param endYyyyMmDd
	 * @return
	 */
	public static List<Map<String,String>> fWeek(String startYyyyMmDd,String endYyyyMmDd){
		List<Map<String,String>> ls=new ArrayList<>();
		Calendar startd=ExtendDate.getCalendar(startYyyyMmDd);
		Calendar endd=ExtendDate.getCalendar(endYyyyMmDd);
		if(startd.after(endd)){
			return ls;
		}
		endd.add(Calendar.WEEK_OF_YEAR,-1);
		while(startd.before(endd)){
			HashMap<String,String> map = new HashMap<>();
			map.put("startDate",ExtendDate.getYMD(startd)+" 00:00:00");
			startd.add(Calendar.DATE,6);
			map.put("endDate",ExtendDate.getYMD(startd)+" 23:59:59");
			startd.add(Calendar.DATE,1);
			ls.add(map);
		}
		endd.add(Calendar.WEEK_OF_YEAR,1);
		HashMap<String,String> map = new HashMap<>();
		map.put("startDate",ExtendDate.getYMD(startd)+" 00:00:00");
		map.put("endDate",ExtendDate.getYMD(endd)+" 23:59:59");
		ls.add(map);
		return ls;
	}

	/**
	 * 取月份之间月时间
	 * @param startYyyyMm
	 * @param endYyyyMm
	 * @return 一个月开始时间和结束时间
	 */
	public static List<Map<String,String>> fMonth(String startYyyyMm,String endYyyyMm){
		List<Map<String,String>> ls=new ArrayList<>();
		String start=startYyyyMm.substring(startYyyyMm.length()-2);
		String end=endYyyyMm.substring(endYyyyMm.length()-2);
		int sv=Integer.valueOf(endYyyyMm.substring(0,4)+end)-Integer.valueOf(startYyyyMm.substring(0,4)+start);
		for (int i=0;i<sv+1;i++) {
			HashMap<String,String> map = new HashMap<>();
			Calendar startt=ExtendDate.getCalendar(startYyyyMm+"-01");
			startt.add(Calendar.MONTH,i);
			map.put("monthStart",ExtendDate.getYMD(startt)+" 00:00:00");
            Calendar endd=ExtendDate.getCalendar(startYyyyMm+"-01");
            endd.set(Calendar.DAY_OF_MONTH, endd.getActualMaximum(Calendar.DAY_OF_MONTH));
            endd.add(Calendar.MONTH,i);
            map.put("monthEnd",ExtendDate.getYMD(endd)+" 23:59:59");
            ls.add(map);
        }
		return ls;
	}
	/**
	 * 取月份之间月时间
	 * @param startYyyyMm
	 * @param endYyyyMm
	 * @return
	 */
	public static List<String> mMonth(String startYyyyMm,String endYyyyMm){
		List<String> ls=new ArrayList<>();
		String start=startYyyyMm.substring(startYyyyMm.length()-2);
		String end=endYyyyMm.substring(endYyyyMm.length()-2);
		int sv=Integer.valueOf(endYyyyMm.substring(0,4)+end)-Integer.valueOf(startYyyyMm.substring(0,4)+start);
		for (int i=0;i<sv+1;i++) {
			Calendar endd=ExtendDate.getCalendar(startYyyyMm+"-01");
			endd.set(Calendar.DAY_OF_MONTH, endd.getActualMaximum(Calendar.DAY_OF_MONTH));
			endd.add(Calendar.MONTH,i);
			ls.add(ExtendDate.getYMD(endd)+" 23:59:59");
		}
		return ls;
	}

	/**
	 * 获取当年的第一天
	 * @return
	 */
	public static Date getCurrYearFirst(){
		Calendar currCal=Calendar.getInstance();
		int currentYear = currCal.get(Calendar.YEAR);
		return getYearFirst(currentYear);
	}

	/**
	 * 获取当年的最后一天
	 * @return
	 */
	public static Date getCurrYearLast(){
		Calendar currCal=Calendar.getInstance();
		int currentYear = currCal.get(Calendar.YEAR);
		return getYearLast(currentYear);
	}

	/**
	 * 获取某年第一天日期
	 * @param year 年份
	 * @return Date
	 */
	public static Date getYearFirst(int year){
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		Date currYearFirst = calendar.getTime();
		return currYearFirst;
	}

	/**
	 * 获取某年最后一天日期
	 * @param year 年份
	 * @return Date
	 */
	public static Date getYearLast(int year){
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		Date currYearLast = calendar.getTime();

		return currYearLast;
	}


	public static String getLastDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month-1);
		cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DATE));
		return  new   SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());
	}
	public static String getFirstDayOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month-1);
		cal.set(Calendar.DAY_OF_MONTH,cal.getMinimum(Calendar.DATE));
		return   new   SimpleDateFormat( "yyyy-MM-dd ").format(cal.getTime());
	}


	public static String getLastDayOfMonth(String date) {
		Calendar cal = ExtendDate.getCalendar(date+"-01");
		//获取当前系统年月
		Calendar nowCal = Calendar.getInstance();
		String nowYm = ExtendDate.getYM(nowCal);
		cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DATE));
		//获取参数的年月
		String strYm = ExtendDate.getYM(cal);
		if(nowYm.equals(strYm)){//两个数据判断是否是同年同月
			//取当前时间前一天
			Calendar call = Calendar.getInstance();
			call.add(Calendar.DATE,-1);
			return new SimpleDateFormat( "yyyy-MM-dd").format(call.getTime());
		}
//		return  new   SimpleDateFormat( "yyyy-MM-dd").format(cal.getTime());
//		Calendar cal = Calendar.getInstance();
//		cal.add(Calendar.DATE, -1);// 日期减1
		return   new   SimpleDateFormat( "yyyy-MM-dd").format(cal.getTime());
	}
	public static String getFirstDayOfMonth(String date) {
		Calendar cal = ExtendDate.getCalendar(date+"-01");
		cal.set(Calendar.DAY_OF_MONTH,cal.getMinimum(Calendar.DATE));
		return   new   SimpleDateFormat( "yyyy-MM-dd").format(cal.getTime());
	}

	public static Calendar getLastYearMonth(){
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, 11 );
		return calendar;
	}

	@SuppressWarnings("static-access")
	public static void main(String[] args)
	{
		String dateDate = "2019-06-15";
		Calendar start = ExtendDate.getCalendar(dateDate);
		Calendar cal = Calendar.getInstance();
		if(start.getTimeInMillis() > cal.getTimeInMillis()){
			System.out.println(1);
		}else{
			System.out.println(2);
		}
//		String ymda="2002/11/02 16:54";
//		String ymdb="2002/02/11";
//		Calendar a=ExtendDate.getCalendar(ymda);
//		Calendar b=ExtendDate.getCalendar(ymdb);
//
//		System.out.println(a.get(a.MONTH)+1);
//		System.out.println(b.get(b.MONTH)+1);
//
//
//		System.out.println(ExtendDate.getYMD_h_m(a));
//		System.out.println(ExtendDate.getYMD_h_m(b));
//		Map<String,String> test = ExtendDate.getDateVaild(Calendar.getInstance());
//		System.out.println(test.get("start"));
//		System.out.println(test.get("end"));

//		System.out.println(getFirstDayOfMonth("2016-02"));
//		System.out.println(getLastDayOfMonth("2016-02"));
//		String yearDate = "2016-01";
//		String yearDateEnd = "2017-06";
//		List<Map<String,String>> list = ExtendDateUtil.getListBetweenMonthMap(yearDate,yearDateEnd);
//		for (Map<String,String> s:list){
//			System.out.println(s.get("monthStart")+"||"+s.get("monthEnd"));
//		}
//		try{
//
//			String a = "12:00";
//			String b = "19:00";
//			String c = " 17:30";
//			if(isInZone(getLong(a),getLong(b),getLong(c))){
//				System.out.println(123);
//			}
//		}catch (Exception e){
//
//		}
		System.out.println(ExtendDateUtil.addDate("2017-01-01", ExtendDateType.WEEKS.getValue(),13));
		/*String result = ExtendDate.getFirstDayOfMonth(yearDate);
		String result2 = ExtendDate.getLastDayOfMonth(yearDateEnd);
		System.out.println(result);
		System.out.println(result2);*/
//		Calendar cal1 = getCalendar("2006-11-07 23:59:59");
//		Calendar cal2 = getCalendar("2006-11-07 12:00:00");
//
//		float f=-0.5f;
//System.out.println(new Float(f*1000 * 3600 * 24).intValue());
//		cal1.setTimeInMillis(cal1.getTimeInMillis()+new Float(f*1000 * 3600 * 24).intValue());
//		System.out.println(ExtendDate.getYMD_h_m_s(cal1));
	}


	public static boolean isInZone(long tStart,long tEnd,long t) {
		return tStart <= t && t <= tEnd;
	}
	public static long getLong(String timeStr) throws ParseException {
		return formatter2.parse(timeStr).getTime();
	}
	private static long getCurrentTime() throws ParseException {
		return getLong(formatter2.format(new Date()));
	}
	public static String getLastDayOfMonthNew(String date) {
		Calendar cal = ExtendDate.getCalendar(date+"-01");
		cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DATE));
		return   new   SimpleDateFormat( "yyyy-MM-dd").format(cal.getTime());


	}
}
