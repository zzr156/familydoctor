package com.ylz.bizDo.jtapp.basicHealthEntity.jzYy;

import java.util.List;

/**
 * 预约时间段
 * @author Administrator
 *
 */
public class YuyueSjD {
	private String yuyue;//预约的时间
	private List<Sjd> am;//上午时间段
	private List<Sjd> pm;//下午时间段
	private String jzYyxx;//接种预约信息 :1:全天,2:上午,3:下午
	private String entity;
	public String getYuyue() {
		return yuyue;
	}
	public void setYuyue(String yuyue) {
		this.yuyue = yuyue;
	}
	public List<Sjd> getAm() {
		return am;
	}
	public void setAm(List<Sjd> am) {
		this.am = am;
	}
	public List<Sjd> getPm() {
		return pm;
	}
	public void setPm(List<Sjd> pm) {
		this.pm = pm;
	}
	public String getJzYyxx() {
		return jzYyxx;
	}
	public void setJzYyxx(String jzYyxx) {
		this.jzYyxx = jzYyxx;
	}

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}
}
