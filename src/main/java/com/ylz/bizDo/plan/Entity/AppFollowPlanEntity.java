package com.ylz.bizDo.plan.Entity;

/**
 * 添加随访计划
 * GwFollowPlan entity. @author MyEclipse Persistence Tools
 */

public class AppFollowPlanEntity {


	private String sfFollowDoctorid;//随访医生id
	private String sfFollowPatientid;//随访患者id
	private String sfFollowPatientName;//随访患者姓名
	private String sfFollowMode;//随访方式
	private String sfFollowDay;//随访提醒天数
	private String sfFollowDayTx;//0未开启,1开启
	private String sfFollowType;//随访类型
	private String sfFollowDate;//随访日期
	private String sfX;//随访x轴
	private String sfY;//随访y轴
	private String sfTeamId;//团队主键


	public String getSfFollowDoctorid() {
		return sfFollowDoctorid;
	}

	public void setSfFollowDoctorid(String sfFollowDoctorid) {
		this.sfFollowDoctorid = sfFollowDoctorid;
	}

	public String getSfFollowPatientid() {
		return sfFollowPatientid;
	}

	public void setSfFollowPatientid(String sfFollowPatientid) {
		this.sfFollowPatientid = sfFollowPatientid;
	}

	public String getSfFollowPatientName() {
		return sfFollowPatientName;
	}

	public void setSfFollowPatientName(String sfFollowPatientName) {
		this.sfFollowPatientName = sfFollowPatientName;
	}

	public String getSfFollowMode() {
		return sfFollowMode;
	}

	public void setSfFollowMode(String sfFollowMode) {
		this.sfFollowMode = sfFollowMode;
	}

	public String getSfFollowDayTx() {
		return sfFollowDayTx;
	}

	public void setSfFollowDayTx(String sfFollowDayTx) {
		this.sfFollowDayTx = sfFollowDayTx;
	}

	public String getSfFollowType() {
		return sfFollowType;
	}

	public void setSfFollowType(String sfFollowType) {
		this.sfFollowType = sfFollowType;
	}

	public String getSfFollowDate() {
		return sfFollowDate;
	}

	public void setSfFollowDate(String sfFollowDate) {
		this.sfFollowDate = sfFollowDate;
	}

	public String getSfFollowDay() {
		return sfFollowDay;
	}

	public void setSfFollowDay(String sfFollowDay) {
		this.sfFollowDay = sfFollowDay;
	}

	public String getSfX() {
		return sfX;
	}

	public void setSfX(String sfX) {
		this.sfX = sfX;
	}

	public String getSfY() {
		return sfY;
	}

	public void setSfY(String sfY) {
		this.sfY = sfY;
	}

	public String getSfTeamId() {
		return sfTeamId;
	}

	public void setSfTeamId(String sfTeamId) {
		this.sfTeamId = sfTeamId;
	}
}