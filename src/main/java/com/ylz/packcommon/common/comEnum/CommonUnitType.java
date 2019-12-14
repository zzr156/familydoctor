package com.ylz.packcommon.common.comEnum;

/**
 * 表码组KEY常量
 *
 */
public enum CommonUnitType{

	/**
	 * 疾控
	 */
	JK("0"),
	/**
	 * 医院
	 */
	YY("1"),
	/**
	 * 艾滋病病种编号
	 */
	aids("HIV"),
	/**
	 * 市疾控科室
	 */
	KS("0303");
	private String value;
	private CommonUnitType(String value){
		this.value = value;
	}
	
	public String getValue(){
		return this.value;
	}
	
}
