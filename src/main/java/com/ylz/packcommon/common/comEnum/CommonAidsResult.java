package com.ylz.packcommon.common.comEnum;

/**
 * 表码组KEY常量
 *
 */
public enum CommonAidsResult{

	/**
	 * 阳性
	 */
	AC("1"),
	/**
	 * 阴性
	 */
	NE("2");
	private String value;
	private CommonAidsResult(String value){
		this.value = value;
	}
	
	public String getValue(){
		return this.value;
	}
	
}
