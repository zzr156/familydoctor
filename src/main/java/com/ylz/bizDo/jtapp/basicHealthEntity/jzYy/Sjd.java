package com.ylz.bizDo.jtapp.basicHealthEntity.jzYy;
/**
 * 时间段
 * @author Administrator
 *
 */
public class Sjd {
	
	private String sjd;//时间段
	private String state;//状态 1是已约 0是未约
	private String yiyue="0";//已约
	private String keyue;//可约
	public String getSjd() {
		return sjd;
	}
	public void setSjd(String sjd) {
		this.sjd = sjd;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Sjd(String sjd, String state) {
		super();
		this.sjd = sjd;
		this.state = state;
	}
	
	public Sjd(String sjd, String state, String yiyue, String keyue) {
		super();
		this.sjd = sjd;
		this.state = state;
		this.yiyue = yiyue;
		this.keyue = keyue;
	}
	public Sjd() {
		super();
	}
	public String getYiyue() {
		return yiyue;
	}
	public void setYiyue(String yiyue) {
		this.yiyue = yiyue;
	}
	public String getKeyue() {
		return keyue;
	}
	public void setKeyue(String keyue) {
		this.keyue = keyue;
	}
	
	
}
