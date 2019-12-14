package com.ylz.packcommon.common.util;

import java.util.Calendar;
import java.util.Date;

public class AgeUtil {

	/**
	 * 计算年龄
	 */
	public static String getAge(Date birthday){
		Calendar calendar = Calendar.getInstance();
		Calendar birth = Calendar.getInstance();
		birth.setTime(birthday);
		int nowYear = calendar.get(Calendar.YEAR);
		int nowMonth  = calendar.get(Calendar.MONTH);
		int nowDay = calendar.get(Calendar.DAY_OF_MONTH);
		int birthYear = birth.get(Calendar.YEAR);
		int birthMonth = birth.get(Calendar.MONTH);
		int birthDay = birth.get(Calendar.DAY_OF_MONTH);
		
		int ageYear;
		int ageMonth;

		ageMonth = nowMonth-birthMonth;
		ageYear = nowYear-birthYear;
		
		if(nowDay<birthDay){
			ageMonth = ageMonth-1;
		}
		
		if(ageMonth<0){
			ageMonth = ageMonth+12;
			ageYear = ageYear-1;
		}
		

		
		return ageYear + "岁"+ ageMonth +"月龄";
	}
	public static String getAgeYear(Date birthday){
		Calendar calendar = Calendar.getInstance();
		Calendar birth = Calendar.getInstance();
		birth.setTime(birthday);
		int nowYear = calendar.get(Calendar.YEAR);
		int nowMonth  = calendar.get(Calendar.MONTH);
		int nowDay = calendar.get(Calendar.DAY_OF_MONTH);
		int birthYear = birth.get(Calendar.YEAR);
		int birthMonth = birth.get(Calendar.MONTH);
		int birthDay = birth.get(Calendar.DAY_OF_MONTH);
		
		int ageYear;
		int ageMonth;

		ageMonth = nowMonth-birthMonth;
		ageYear = nowYear-birthYear;
		
		if(nowDay<birthDay){
			ageMonth = ageMonth-1;
		}
		
		if(ageMonth<0){
			ageMonth = ageMonth+12;
			ageYear = ageYear-1;
		}
		
		
		return ageYear + "岁";
	}

	public static String getAgeYear(Calendar birthday){
		Calendar calendar = Calendar.getInstance();
		Calendar birth = Calendar.getInstance();
		birth.setTime(birthday.getTime());
		int nowYear = calendar.get(Calendar.YEAR);
		int nowMonth  = calendar.get(Calendar.MONTH);
		int nowDay = calendar.get(Calendar.DAY_OF_MONTH);
		int birthYear = birth.get(Calendar.YEAR);
		int birthMonth = birth.get(Calendar.MONTH);
		int birthDay = birth.get(Calendar.DAY_OF_MONTH);

		int ageYear;
		int ageMonth;

		ageMonth = nowMonth-birthMonth;
		ageYear = nowYear-birthYear;

		if(nowDay<birthDay){
			ageMonth = ageMonth-1;
		}

		if(ageMonth<0){
			ageMonth = ageMonth+12;
			ageYear = ageYear-1;
		}


		return String.valueOf(ageYear);
	}

	public static int getMonthAge(Date date){
		Calendar calendar = Calendar.getInstance();
		Calendar birth = Calendar.getInstance();
		birth.setTime(date);
		int nowYear = calendar.get(Calendar.YEAR);
		int nowMonth  = calendar.get(Calendar.MONTH);
		int nowDay = calendar.get(Calendar.DAY_OF_MONTH);
		int birthYear = birth.get(Calendar.YEAR);
		int birthMonth = birth.get(Calendar.MONTH);
		int birthDay = birth.get(Calendar.DAY_OF_MONTH);
		
		int ageYear;
		int ageMonth;

		ageMonth = nowMonth-birthMonth;
		ageYear = nowYear-birthYear;
		
		if(nowDay<birthDay){
			ageMonth = ageMonth-1;
		}
		
		if(ageMonth<0){
			ageMonth = ageMonth+12;
			ageYear = ageYear-1;
		}
		return ageYear * 12 + ageMonth;
	}

	public static int getMonthAge(Date startDate, Date endDate) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(endDate);
		Calendar birth = Calendar.getInstance();
		birth.setTime(startDate);
		int nowYear = calendar.get(Calendar.YEAR);
		int nowMonth  = calendar.get(Calendar.MONTH);
		int nowDay = calendar.get(Calendar.DAY_OF_MONTH);
		int birthYear = birth.get(Calendar.YEAR);
		int birthMonth = birth.get(Calendar.MONTH);
		int birthDay = birth.get(Calendar.DAY_OF_MONTH);
		
		int ageYear;
		int ageMonth;

		ageMonth = nowMonth-birthMonth;
		ageYear = nowYear-birthYear;
		
		if(nowDay<birthDay){
			ageMonth = ageMonth-1;
		}
		
		if(ageMonth<0){
			ageMonth = ageMonth+12;
			ageYear = ageYear-1;
		}
		return ageYear * 12 + ageMonth;
	}

	public static void main(String[] args) {
		System.out.println(AgeUtil.getAgeYear(ExtendDate.getCalendar("2014-08-07")));
		System.out.println(AgeUtil.getMonthAge(ExtendDate.getCalendar("2013-02-22").getTime()));
		System.out.println(AgeUtil.getMonthAge(ExtendDate.getCalendar("2017-11-09").getTime(),ExtendDate.getCalendar("2017-12-09").getTime()));
		System.out.println(ExtendDate.getLastYearMonth());
	}

}
