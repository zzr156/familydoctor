package com.ylz.packcommon.common.comEnum;

public enum CommonEnable {
	
	/**
	 * 禁用
	 */
	JINYONG("0"),
	
	/**
	 * 启用
	 */
	QIYONG("1");
	private String value;
	private CommonEnable(String value){
		this.value = value;
	}
	
	public String getValue(){
		return this.value;
	}
}
