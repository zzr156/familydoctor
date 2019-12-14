package com.ylz.packcommon.common.comEnum;

/**
 * 表码组KEY常量
 *
 */
public enum CommonUgState {

	/**
	 * 默认
	 */
	MR("1"),
	/**
	 * 归档
	 */
	GD("0"),

	/**
	 * 空腹
	 */
	KF("1"),
	/**
	 * 早餐后
	 */
	ZCH("2"),
	WCQ("3"),//午餐前
	WCH("4"),//午餐后
	DCQ("5"),//晚餐前
	DCH("6"),//晚餐后
	SQ("7"),//睡前
	LC("8"),//凌晨
	SJ("9");//随机

	private String value;
	CommonUgState(String value){
		this.value = value;
	}
	
	public String getValue(){
		return this.value;
	}
	
}
