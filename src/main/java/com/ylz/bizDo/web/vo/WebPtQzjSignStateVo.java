package com.ylz.bizDo.web.vo;

public class WebPtQzjSignStateVo
{
	private String lsh000;//主键 流水号

	private String grbh00;//个人编号

	private String xm0000;//姓名

	private String shzf00;//身份证号

	private String ylzh00;//医疗证号

	private String sbkh00;//社保卡号

	private String kssj00;//签约开始时间

	private String jzsj00;//签约截止时间

	private String qysj00;//签约时间

	private String czry00;//操作人员

	private String sjcrsj;//签约数据插入前置机时间(基卫系统更新)  -- 按照sysdate更新

	private String hgbz00;//合格标志 -- 1 合格，0不合格，默认空值

	private String nhbzgx;//合格标志更新(农合系统更新)  -- 1 代表农合已经取走合格标志数据

	private String jwbzgx;//合格标志更新(基卫系统更新)  -- 1 代表基卫更新合格标志

	private String qybzgx="0";//签约标志更新(农合和基卫系统更新) -- 农合更新为1，基卫更新为0 默认0

	private String nhgxsj;//签约更新时间(农合系统更新)  -- 农合取走签约数据更新时间

	private String jwgxsj;//签约更新时间(基卫系统更新)  -- 基卫插入签约数据更新时间

	private String qysbyy;//签约失败原因(农合系统更新)  -- 农合取走签约数据更新失败原因

	public String getLsh000() {
		return lsh000;
	}

	public void setLsh000(String lsh000) {
		this.lsh000 = lsh000;
	}

	public String getGrbh00() {
		return grbh00;
	}

	public void setGrbh00(String grbh00) {
		this.grbh00 = grbh00;
	}

	public String getXm0000() {
		return xm0000;
	}

	public void setXm0000(String xm0000) {
		this.xm0000 = xm0000;
	}

	public String getShzf00() {
		return shzf00;
	}

	public void setShzf00(String shzf00) {
		this.shzf00 = shzf00;
	}

	public String getYlzh00() {
		return ylzh00;
	}

	public void setYlzh00(String ylzh00) {
		this.ylzh00 = ylzh00;
	}

	public String getSbkh00() {
		return sbkh00;
	}

	public void setSbkh00(String sbkh00) {
		this.sbkh00 = sbkh00;
	}

	public String getCzry00() {
		return czry00;
	}

	public void setCzry00(String czry00) {
		this.czry00 = czry00;
	}

	public String getHgbz00() {
		return hgbz00;
	}

	public void setHgbz00(String hgbz00) {
		this.hgbz00 = hgbz00;
	}

	public String getNhbzgx() {
		return nhbzgx;
	}

	public void setNhbzgx(String nhbzgx) {
		this.nhbzgx = nhbzgx;
	}

	public String getJwbzgx() {
		return jwbzgx;
	}

	public void setJwbzgx(String jwbzgx) {
		this.jwbzgx = jwbzgx;
	}

	public String getQybzgx() {
		return qybzgx;
	}

	public void setQybzgx(String qybzgx) {
		this.qybzgx = qybzgx;
	}


	public String getQysbyy() {
		return qysbyy;
	}

	public void setQysbyy(String qysbyy) {
		this.qysbyy = qysbyy;
	}

	public String getKssj00() {
		return kssj00;
	}

	public void setKssj00(String kssj00) {
		this.kssj00 = kssj00;
	}

	public String getJzsj00() {
		return jzsj00;
	}

	public void setJzsj00(String jzsj00) {
		this.jzsj00 = jzsj00;
	}

	public String getQysj00() {
		return qysj00;
	}

	public void setQysj00(String qysj00) {
		this.qysj00 = qysj00;
	}

	public String getSjcrsj() {
		return sjcrsj;
	}

	public void setSjcrsj(String sjcrsj) {
		this.sjcrsj = sjcrsj;
	}

	public String getNhgxsj() {
		return nhgxsj;
	}

	public void setNhgxsj(String nhgxsj) {
		this.nhgxsj = nhgxsj;
	}

	public String getJwgxsj() {
		return jwgxsj;
	}

	public void setJwgxsj(String jwgxsj) {
		this.jwgxsj = jwgxsj;
	}

}
