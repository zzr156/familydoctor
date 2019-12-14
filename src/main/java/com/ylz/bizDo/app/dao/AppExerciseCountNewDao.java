package com.ylz.bizDo.app.dao;

import java.util.List;
import java.util.Map;

/**
 * 新履约管理
 * 
 * @author lyy
 */
public interface AppExerciseCountNewDao {

	/**
	 * 调度方法
	 * 
	 * @param year 年
	 * @param num 第几月 0：不算月，算一整年
	 */
	public List<Map> dispatchExerciseCount(int year, int num) throws Exception;
	
	/**
	 * 由年、地区获取数量
	 */
	public List<Map> getExerciseCountByYearAndAreaCode(String year, String areaCode) throws Exception;

	/**
	 * 由年、医生ID获取数量
	 */
	public List<Map> getExerciseCountByYearAndDrId(String year, String drId) throws Exception;
}
