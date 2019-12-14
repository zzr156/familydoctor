package com.ylz.packcommon.common.comEnum;

/**
 * 表码组KEY常量
 *
 */
public enum CommonDeviceType {

	/**
	 * 血压计
	 */
	XY("1"),
	/**
	 * 血糖仪
	 */
	XT("2");
	private String value;
	CommonDeviceType(String value){
		this.value = value;
	}
	
	public String getValue(){
		return this.value;
	}
	
}
