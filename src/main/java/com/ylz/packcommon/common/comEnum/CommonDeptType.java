package com.ylz.packcommon.common.comEnum;

/**
 * 表码组KEY常量
 *
 */
public enum CommonDeptType{

	/**
	 * 机构
	 */
	JG("0"),
	/**
	 * 部门
	 */
	BM("1");
	private String value;
	private CommonDeptType(String value){
		this.value = value;
	}
	
	public String getValue(){
		return this.value;
	}
	
}
