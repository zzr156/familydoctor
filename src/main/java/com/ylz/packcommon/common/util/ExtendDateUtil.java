package com.ylz.packcommon.common.util;

import com.ylz.packcommon.common.comEnum.ExtendDateType;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;




public class ExtendDateUtil {
	
	
	
	public static String minusDate(String month,String type,int i){
		if(ExtendDateType.MONTHS.getValue().equals(type)){
			DateTime begin = new DateTime(month);
			return begin.minusMonths(i).toString("yyyy-MM-dd");
		}else  if (ExtendDateType.DAYS.getValue().equals(type)){
			DateTime begin = new DateTime(month);
			Calendar date = Calendar.getInstance();
			date.setTime(begin.toDate());
			date.set(Calendar.DATE, date.get(Calendar.DATE) - i);
			return ExtendDate.getYMD(date);
		}else{
			throw new RuntimeException("暂不支持其他类型");
		}

	}
	
	/**
	 * 需要增加一个获取月龄的方法,6.x的话,返回7
	 * @param beginStr
	 * @return
	 * @throws ParseException 
	 */
	public static String getMyl(String endStr,String beginStr) throws ParseException{
		 int yf = 0;
		 DateTime begin = new DateTime(beginStr);  
		 DateTime end = new DateTime(endStr);
		//相差天数
		 int days = new Period(begin, end, PeriodType.days()).getDays();
		 //相差月份
		 int months = new Period(begin, end, PeriodType.months()).getMonths();
		//获取endStr的上N个月的日期
		 LocalDate nowMonth = end.toLocalDate();
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		 String  lastdayMonth = format.format(nowMonth.minusMonths(months).dayOfMonth().getLocalDate().toDate());
		 DateTime str = new DateTime(format.parse(lastdayMonth));
		 int day = Days.daysBetween(str, end).getDays();
		 if(days > day){
			yf = months + 1;
		 }else{
			yf= months;
		 }
		return String.valueOf(yf);
	}
	
//	public static void main(String[] args){
//		String s=ExtendDateUtil.getMyl("2014-09-01", "2009-02-02");
//		System.out.println("jg:::::::::::"+s);
//	}
	
	/**
	 * 需要增加一个获取月龄的方法,6.x的话,返回6
	 * @param beginStr
	 * @return
	 */
	public static String getNotMyl(String endStr,String beginStr){
		 DateTime begin = new DateTime(beginStr);  
		 DateTime end = new DateTime(endStr);
		 //相差月份
		 int months = new Period(begin, end, PeriodType.months()).getMonths();
		
		return String.valueOf(months);
	}
	
	/**
	 * 把传入的时间和今天比较,相等就
	 * @return
	 */
	public static boolean equalsToday(String date){
		boolean flag = false;
		DateTime begin = new DateTime(date);  
		DateTime end = new DateTime();
		String beginStr = begin.toString("yyyy-MM-dd");  
		String endStr = end.toString("yyyy-MM-dd");  
		if(beginStr.equals(endStr)){
			flag = true;
			return flag;
		}
		return flag;
	}
	/**
	 * 计算日期相差天数,月份,月份
	 * @param beginStr
	 * @param endStr
	 * @param type : years,months,days
	 * @return
	 */
	public static int differDate(String endStr,String beginStr,String type){
		
		 DateTime begin = new DateTime(beginStr);  
		 DateTime end = new DateTime(endStr);  
		 int sj = 0;
		 
		 //计算年区间
		 if(type.equals(ExtendDateType.YEARS.getValue())){
			 Period pyear = new Period(begin,end,PeriodType.years());
			 sj = pyear.getYears();
		 }else if(type.equals(ExtendDateType.MONTHS.getValue())){//计算月份区间
			 Period pmonth = new Period(begin, end, PeriodType.months());  
			 sj = pmonth.getMonths(); 
		 }else if(type.equals(ExtendDateType.DAYS.getValue())){//计算天数区间
			 Period pday = new Period(begin, end, PeriodType.days());  
			 sj = pday.getDays(); 
		 }else if(type.equals(ExtendDateType.WEEKS.getValue())){//计算周区间
			 Period pweek = new Period(begin, end, PeriodType.weeks());  
			 sj = pweek.getWeeks();
		 }		
		 
		return sj;
	}
	
	/**
	 * 计算日期添加
	 * @param sTime
	 * @param type
	 * @param sj
	 * @return
	 */
	public static String addDate(String sTime,String type,int sj){
		SimpleDateFormat imFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if(StringUtils.isNotBlank(sTime)){
				sTime = imFormat.format(imFormat.parse(sTime));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DateTime begin = new DateTime(sTime); 
		
		String rq = null;
		//当前DateTime对象的时间的加上年,计算出日期
		 if(type.equals(ExtendDateType.YEARS.getValue())){
			 rq = begin.plus(Period.years(sj)).toString(DateTimeFormat.forPattern("yyyy-MM-dd"));
		 }else if(type.equals(ExtendDateType.MONTHS.getValue())){//当前DateTime对象的时间的加上月份,计算出日期
			 rq = begin.plus(Period.months(sj)).toString(DateTimeFormat.forPattern("yyyy-MM-dd"));
		 }else if(type.equals(ExtendDateType.DAYS.getValue())){//当前DateTime对象的时间的加上天数,计算出日期
			 rq = begin.plus(Period.days(sj)).toString(DateTimeFormat.forPattern("yyyy-MM-dd"));
		 }else if(type.equals(ExtendDateType.WEEKS.getValue())){//当前DateTime对象的时间的加上周,计算出日期
			 rq = begin.plus(Period.weeks(sj)).toString(DateTimeFormat.forPattern("yyyy-MM-dd"));
		 }
		 
		 return rq;
		 
	}
	
	public static String minusDate(int sj){
		SimpleDateFormat dft = new SimpleDateFormat("yyyy-MM-dd");
		Date beginDate = new Date();
		Calendar date = Calendar.getInstance();
		date.setTime(beginDate);
		date.set(Calendar.DATE, date.get(Calendar.DATE) - sj);
		return dft.format(date.getTime());
	}

	public static Date minusDate(Date beginDate,int sj){
		Calendar date = Calendar.getInstance();
		date.setTime(beginDate);
		date.set(Calendar.MINUTE, date.get(Calendar.MINUTE) - sj);
		return date.getTime();
	}
	

	/**
	 * 根据当前日期算出,当前月份天数
	 * @param sTime
	 * @return
	 */
	public static int getMonthLastDay(String sTime){
		 DateTime begin = new DateTime(sTime);  
		 LocalDate nowMonth = begin.toLocalDate();		 
		 return nowMonth.dayOfMonth().getMaximumValue();
	}
	
	/**
	 * 根据当前时间算出,当前时间星期几
	 * @param sTime
	 * @return
	 */
	public static int getWeekOfDate(String sTime){		
		DateTime dt = new DateTime(sTime);
		return dt.getDayOfWeek();
	} 
	
	
	/**
	 * 根据当前时间算出,当前时间星期几
	 * @param sTime
	 * @return
	 */
	public static String getWeekOfDateXq(String sTime){		
		DateTime dt = new DateTime(sTime);  
		String xq = null;
		//星期  
		switch(dt.getDayOfWeek()) {  
		case DateTimeConstants.SUNDAY:  
			xq = "星期日"; 
		    break;  
		case DateTimeConstants.MONDAY:  
			xq = "星期一"; 
		    break;  
		case DateTimeConstants.TUESDAY:  
			xq = "星期二"; 
		    break;  
		case DateTimeConstants.WEDNESDAY: 
			xq = "星期三"; 
		    break;  
		case DateTimeConstants.THURSDAY: 
			xq = "星期四"; 
		    break;  
		case DateTimeConstants.FRIDAY:  
			xq = "星期五"; 
		    break;  
		case DateTimeConstants.SATURDAY:  
			xq = "星期六"; 
		    break;  
		}  
		
		return xq;
	} 
	/**
	 * 判断两个日期是否在同个月份
	 * @param strDate
	 * @param strOtherDate
	 * @return
	 */
	public static boolean isInSameMonth(String strDate,String strOtherDate){
		String format = "yyyy-MM-dd";
		DateFormat df = new SimpleDateFormat(format);
		try {
			Date date = df.parse(strDate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
		
			Date otherDate = df.parse(strOtherDate);
			Calendar otherCalendar = Calendar.getInstance();
			otherCalendar.setTime(otherDate);
			return calendar.get(Calendar.YEAR)==otherCalendar.get(Calendar.YEAR)&&calendar.get(Calendar.MONTH)==otherCalendar.get(Calendar.MONTH);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 获取传入时间月份第一天
	 * @param strDate
	 * @return
	 */
	public static Date getFirstDateOfMonth(String strDate){
		String format = "yyyy-MM-dd";
		DateFormat df = new SimpleDateFormat(format);
		Date date;
		try {
			date = df.parse(strDate);
			return DateUtils.truncate(date, Calendar.MONTH);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		
	}

	/**
	 * 判断两个日期是否同一天
	 * @param dt1
	 * @param dt2
	 * @return
	 */
	public static Boolean sameDate(Date dt1 , Date dt2 ){
		LocalDate ld1 = new LocalDate(new DateTime(dt1));
		LocalDate ld2 = new LocalDate(new DateTime(dt2));
		return ld1.equals( ld2 );
	}

	/**
	 * 计算两个时间直接的月份
	 * 返回格式("2017-01")
	 * @return
	 */
	public static List<String> getListBetweenMonthYear(String year){
		String start = ExtendDate.getYMD(ExtendDate.getYearFirst(Integer.parseInt(year)));
		String end = ExtendDate.getYMD(ExtendDate.getYearLast(Integer.parseInt(year)));
		List<String> ls = ExtendDateUtil.getListBetweenMonth(start,end);
		return ls;
	}

	/**
	 * 计算两个时间直接的月份
	 * @param begDate
	 * @param endDate
	 * @return
	 */
	public static List<String> getListBetweenMonth(String begDate,String endDate){
		List<String> ls = new ArrayList<String>();
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			Calendar bef = Calendar.getInstance();
			Calendar aft = Calendar.getInstance();
			bef.setTime(sdf.parse(begDate));
			aft.setTime(sdf.parse(endDate));
			int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH) + 1;
			int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
			int jg = Math.abs(month + result);
			for(int i =0;i<jg;i++){
				DateTime dateTime = new DateTime(begDate);
				ls.add(dateTime.toString(DateTimeFormat.forPattern("yyyy-MM")));
				begDate = dateTime.plus(Period.months(1)).toString(DateTimeFormat.forPattern("yyyy-MM-dd"));
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return ls;
	}

	/**
	 * 计算两个时间直接的年份
	 * @param begDate
	 * @param endDate
	 * @return
	 */
	public static List<String> getListBetweenYear(String begDate,String endDate){
		List<String> ls = new ArrayList<String>();
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
			Calendar bef = Calendar.getInstance();
			Calendar aft = Calendar.getInstance();
			bef.setTime(sdf.parse(begDate));
			aft.setTime(sdf.parse(endDate));
			int year = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR))+1;
			for(int i =0;i<year;i++){
				DateTime dateTime = new DateTime(begDate);
				ls.add(dateTime.toString(DateTimeFormat.forPattern("yyyy")));
				begDate = dateTime.plus(Period.years(1)).toString(DateTimeFormat.forPattern("yyyy-MM-dd"));
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return ls;
	}


	public static List<Map<String,String>> getListBetweenMonthMap(String begDate, String endDate){
		List<Map<String,String>> ls = new ArrayList<Map<String,String>>();
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			Calendar bef = Calendar.getInstance();
			Calendar aft = Calendar.getInstance();
			bef.setTime(sdf.parse(begDate));
			aft.setTime(sdf.parse(endDate));
			int result = aft.get(Calendar.MONTH) - bef.get(Calendar.MONTH) + 1;
			int month = (aft.get(Calendar.YEAR) - bef.get(Calendar.YEAR)) * 12;
			int jg = Math.abs(month + result);
			for(int i =0;i<jg;i++){
				DateTime dateTime = new DateTime(begDate);
				HashMap<String,String> map = new HashMap<>();
				String date = dateTime.toString(DateTimeFormat.forPattern("yyyy-MM"))+"-01";
				Calendar startt=ExtendDate.getCalendar(date);
				map.put("monthStart",ExtendDate.getYMD(startt)+" 00:00:00");
				Calendar endd=ExtendDate.getCalendar(date);
				endd.set(Calendar.DAY_OF_MONTH, endd.getActualMaximum(Calendar.DAY_OF_MONTH));
				map.put("monthEnd",ExtendDate.getYMD(endd)+" 23:59:59");
				map.put("month", endd.get(Calendar.MONTH)+1+"月");
				ls.add(map);
				begDate = dateTime.plus(Period.months(1)).toString(DateTimeFormat.forPattern("yyyy-MM-dd"));
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return ls;
	}


	/**
	 * 时间是否满足一年
	 * @param date
	 * @return
	 */
	public static boolean getIsDateSatisfied(String date){
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);    //获取年
		int month = calendar.get(Calendar.MONTH) + 1;   //获取月份，0表示1月份
		String start = null;
		if(month < 10){
			start = year+"-0"+month+"-01";
		}else{
			start = year+"-"+month+"-01";
		}
		date = date + "-01";
		int day = ExtendDateUtil.differDate(start,date,ExtendDateType.DAYS.getValue());
		if(day < 365){
			return false;
		}else{
			return true;
		}

	}

	public static void main(String[] args){
		String endDate = ExtendDateUtil.minusDate(ExtendDate.getYMD(Calendar.getInstance()), ExtendDateType.DAYS.getValue(),1);
		System.out.println(endDate);
//		String s="2015-04-02";
//		String s2="2015-05-08";
//		int s3=ExtendDateUtil.differDate(s2, s, ExtendDateType.DAYS.getValue());
//		System.out.println("time :::::::::::"+s3);
//		Calendar ca = Calendar.getInstance();
//		System.out.println(ExtendDate.getHHMM(ca));

//		List<String> ls = getListBetweenYear("2017","2017");
//		System.out.println(JsonUtil.toJson(ls));
	}

}
