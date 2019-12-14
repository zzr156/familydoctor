package com.ylz.packcommon.common.comEnum;

public enum CommonLcState {
	/**
	 * 检测结果
	 */
	MR("1"),//默认
	FJ("1"),//复检
	QZ("2");//确证
	private String value;
	private CommonLcState(String value){
		this.value = value;
	}
	
	public String getValue(){
		return this.value;
	}

}
