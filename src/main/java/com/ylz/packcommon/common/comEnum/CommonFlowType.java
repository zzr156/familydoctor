package com.ylz.packcommon.common.comEnum;

public enum CommonFlowType {
	//流程状态
	SJ("1"),//送检
	SH("2"),//审核
	JY("3"),//接样
	FJJC("4"),//复检检测
	FJSH("5"),//复检审核
	QZJC("6"),//确证检测
	QZFH("7"),//确证复核
	QZQF("8"),//确证签发
	SHTH("9"),//审核退回修改
	TY("10"),//退样
	FJJS("98"),//复检流程结束
	QZJS("99");//确证流程结束
	private String value;
	private CommonFlowType(String value){
		this.value = value;
	}
	
	public String getValue(){
		return this.value;
	}
}
