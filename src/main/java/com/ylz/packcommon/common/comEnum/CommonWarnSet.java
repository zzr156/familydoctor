package com.ylz.packcommon.common.comEnum;

public enum CommonWarnSet {
	TJXT("1"),//体检提醒
	JKJC("2"),//健康监测异常提醒
	JKZX("3"),//健康咨询
	YYDQ("4"),//用药短缺提醒
	XYNZ("5"),//血压闹钟设置(
	YPCL("6"),//药品存量预警设置
	JZYJ("7"),//儿童接种提醒
	JHMY("8"),//儿童计划免疫提醒
	SFTX("9"),//随访提前提醒
	SFBD("10"),//随访8点提醒
	SFSWD("11"),//随访16点提醒
	BGQX("22"),//变更过期时间期限
	JTYCJCTX("13");//健康咨询群聊提醒
	private String value;
	private CommonWarnSet(String value){
		this.value = value;
	}
	
	public String getValue(){
		return this.value;
	}
}
