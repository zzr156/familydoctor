package com.ylz.packcommon.common.comEnum;

public enum LabelManageType {

	/**
	 * 健康情况
	 */
	JKQK("1"),

	/**
	 * 疾病类型
	 */
	JBLX("2"),

	/**
	 * 服务人群
	 */
	FWRQ("3"),

	/**
	 * 经济类型
	 */
	JJLX("4"),
	/**
	 * 建档立卡
	 */
	JDLK("5");
	private String value;
	private LabelManageType(String value){
		this.value = value;
	}
	
	public String getValue(){
		return this.value;
	}
}
