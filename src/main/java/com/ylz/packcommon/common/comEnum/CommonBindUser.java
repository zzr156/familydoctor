package com.ylz.packcommon.common.comEnum;

/**
 * 表码组KEY常量
 *
 */
public enum CommonBindUser {

	/**
	 * 爸爸用户
	 */
	BB("1"),
	/**
	 * 妈妈用户
	 */
	MM("2");
	private String value;
	CommonBindUser(String value){
		this.value = value;
	}
	
	public String getValue(){
		return this.value;
	}
	
}
