package com.ylz.packcommon.common.wechat.entity;


public class JieZhongRi {
	private String jzrSj;//接种日
	private String jzYyxx;//接种预约信息 :1:全天,2:上午,3:下午
	private String yyrs;//当天预约人数

	public String getJzrSj() {
		return jzrSj;
	}

	public void setJzrSj(String jzrSj) {
		this.jzrSj = jzrSj;
	}

	public String getYyrs() {
		return yyrs;
	}

	public void setYyrs(String yyrs) {
		this.yyrs = yyrs;
	}

	public String getJzYyxx() {
		return jzYyxx;
	}

	public void setJzYyxx(String jzYyxx) {
		this.jzYyxx = jzYyxx;
	}
	
	
}
