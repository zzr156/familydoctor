package com.ylz.packcommon.common.comEnum;

public enum CommonSuccessFail {

	/**
	 * 失败
	 */
	FAIL("2"),

	/**
	 * 成功
	 */
	SUCCESS("1");
	private String value;
	private CommonSuccessFail(String value){
		this.value = value;
	}
	
	public String getValue(){
		return this.value;
	}
}
