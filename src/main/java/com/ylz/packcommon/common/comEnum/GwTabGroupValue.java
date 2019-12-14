package com.ylz.packcommon.common.comEnum;

public enum GwTabGroupValue {

	/**
	 * 健康
	 */
	VALUE_JK("0"),
	/**
	 * 疾病
	 */
	TYPE_JB("1"),
	/**
	 * 自定义
	 */
	TYPE_ZDY("2");
	private String value;
	private GwTabGroupValue(String value){
		this.value = value;
	}

	public String getValue(){
		return this.value;
	}
}
