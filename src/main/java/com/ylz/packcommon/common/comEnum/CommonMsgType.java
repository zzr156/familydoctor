package com.ylz.packcommon.common.comEnum;

/**
 * 表码组KEY常量
 *
 */
public enum CommonMsgType{

	/**
	 * 普通消息
	 */
	PT("1"),
	/**
	 * 预警消息
	 */
	YJ("2");
	private String value;
	private CommonMsgType(String value){
		this.value = value;
	}
	
	public String getValue(){
		return this.value;
	}
	
}
