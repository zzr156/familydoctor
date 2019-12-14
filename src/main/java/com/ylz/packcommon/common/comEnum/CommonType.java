package com.ylz.packcommon.common.comEnum;

/**
 * 表码组KEY常量
 *
 */
public enum CommonType{

	/**
	 * 银行卡管理
	 */
	TYPE_BANK("bankcode"),
	/**
	 * 金额管理
	 */
	TYPE_MONEY("moneycode"),
	/**
	 * 基础数据管理表
	 */
	TYPE_CODE("cdcode");
	private String value;
	private CommonType(String value){
		this.value = value;
	}
	
	public String getValue(){
		return this.value;
	}
	
}
