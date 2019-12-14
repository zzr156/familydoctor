package com.ylz.packcommon.common.comEnum;

/**
 * 用药指导来源
 *
 */
public enum AppDrugGuideFrom {

	/**
	 * 新增
	 */
	XZ("1"),
	/**
	 * 随访
	 */
	SF("2"),
	/**
	 * 健康指导
	 */
	JKZD("3");
	private String value;
	private AppDrugGuideFrom(String value){
		this.value = value;
	}
	
	public String getValue(){
		return this.value;
	}
	
}
