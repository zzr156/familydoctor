package com.ylz.bizDo.app.entity;

import java.util.List;

/**工作日设置表
 * AppWorkdaySetting entity. @author MyEclipse Persistence Tools
 */
public class AppWorkdaySettingEntity {

	// Fields
	private String id;
	private String wsDrId;//所属医生主键
	private String wsWorkShift;//上班时间
	private String wsClosingTime;//下班时间
	private String wsNightTime;//晚上时间
	private List<String> wsWeek;//周

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWsDrId() {
		return wsDrId;
	}

	public void setWsDrId(String wsDrId) {
		this.wsDrId = wsDrId;
	}

	public String getWsWorkShift() {
		return wsWorkShift;
	}

	public void setWsWorkShift(String wsWorkShift) {
		this.wsWorkShift = wsWorkShift;
	}

	public String getWsClosingTime() {
		return wsClosingTime;
	}

	public void setWsClosingTime(String wsClosingTime) {
		this.wsClosingTime = wsClosingTime;
	}

	public String getWsNightTime() {
		return wsNightTime;
	}

	public void setWsNightTime(String wsNightTime) {
		this.wsNightTime = wsNightTime;
	}

	public List<String> getWsWeek() {
		return wsWeek;
	}

	public void setWsWeek(List<String> wsWeek) {
		this.wsWeek = wsWeek;
	}
}