package com.ylz.packcommon.common.comEnum;

/**
 * 出生间隔单位
 * @author dws
 *
 */
public enum ExtendDateType {
	
	/**
	 * 年
	 */
	YEARS("5"),
	/**
	 * 月
	 */
	MONTHS("3"),
	/**
	 * 日
	 */
	DAYS("1"),
	/**
	 * 周
	 */
	WEEKS("7"),
	/**
	 * 季度
	 */
	quarter("9");
	private String value;
	private ExtendDateType(String value){
		this.value = value;
	}
	
	public String getValue(){
		return this.value;
	}
}
