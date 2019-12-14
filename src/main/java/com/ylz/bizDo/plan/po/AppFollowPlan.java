package com.ylz.bizDo.plan.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**
 * 随访计划表
 * GwFollowPlan entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "APP_FOLLOW_PLAN")
public class AppFollowPlan extends BasePO {

	// Fields
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
	private String id;
	@Column(name = "SF_FOLLOW_NUM", length = 20)
	private String sfFollowNum;//随访编号
	@Column(name = "SF_FOLLOW_DOCTORID", length = 36)
	private String sfFollowDoctorid;//医生id
	@Column(name = "SF_FOLLOW_PATIENTID", length = 36)
	private String sfFollowPatientid;//患者id
	@Column(name = "SF_FOLLOW_PATIENTNAME", length = 36)
	private String sfFollowPatientName;//患者姓名
	@Column(name = "SF_FOLLOW_MODE", length = 15)
	private String sfFollowMode;//面访
	@Column(name = "SF_FOLLOW_STATE", length = 10)
	private String sfFollowState;//0未开始,1已完成,2过期,3取消,4延期,5死亡,6失访
	@Column(name = "SF_FOLLOW_TYPE", length = 10)
	private String sfFollowType;//随访类型 5高血压 6糖尿病
	@Column(name = "SF_FOLLOW_DATE", length = 19)
	private Calendar sfFollowDate;//随访计划时间
	@Column(name = "SF_CREATE_DATE", length = 19)
	private Calendar sfCreateDate;//创建时间
	@Column(name = "SF_ORG_ID",length = 36)
	private String sfOrgId;//机构
	@Column(name = "SF_HEALTH_NUM",length = 50)//健康档案编号
	private String sfHealthNum;
	@Column(name = "SF_ISORNOT",length = 10)
	private String sfIsOrNot="0";//是否上传到基卫默认0未上传，1为上传
	@Column(name = "SF_END_DATE", length = 19)
	private Calendar sfEndDate;//随访结束时间
	@Column(name = "SF_HOS_ID",length = 40)
	private String sfHosId;//医院ID
	@Column(name = "SF_HOS_AREA_CODE",length = 50)
	private String sfHosAreaCode;//医院code
	@Column(name = "SF_X_AXIS", length = 50)
	private String sfXaxis;//横坐标
	@Column(name = "SF_Y_AXIS", length = 50)
	private String sfYaxis;//纵坐标
	@Column(name = "SF_TEAM_ID",length = 36)
	private String sfTeamId;//团队id
	@Column(name = "SF_PID",length = 36)
	private String sfPid;//上一次批号
	@Column(name = "SF_REFERRAL",length = 10)
	private String sfReferral;//转诊情况//0无，1有
	@Column(name = "SF_REFERRAL_ORG",length = 36)
	private String sfReferralOrg;//转诊机构
	@Column(name = "SF_REFERRAL_DEPT",length = 100)
	private String sfReferralDept;//转诊科室
	@Column(name = "SF_REFERRAL_REASON",length = 255)
	private String sfReferralReason;//转诊原因
	@Column(name = "SF_IS_TEMPORARY",length = 10)
	private String sfIsTemporary;//是否临时随访
	@Column(name = "SF_REMIND_PLAN",length = 10)
	private String sfRemindPlan;//提醒方案
	@Column(name = "SF_TYPE_NUM",length = 10)
	private String sfTypeNum;//随访对应类型次数
	@Column(name = "SF_DEAD_TIME")
	private Calendar sfDeadTime;//死亡日期
	@Column(name = "SF_MISS_REASON",length = 255)
	private String sfMissReason;//失访或死亡原因
	@Column(name = "NEXT_TIME")
	private Calendar nextTime;//下次随访日期
	@Column(name = "NOT_SUCCESS_REASON")
	private String notSuccessReason;//上传基卫失败原因

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSfFollowNum() {
		return sfFollowNum;
	}

	public void setSfFollowNum(String sfFollowNum) {
		this.sfFollowNum = sfFollowNum;
	}

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

	public String getSfFollowState() {
		return sfFollowState;
	}

	public void setSfFollowState(String sfFollowState) {
		this.sfFollowState = sfFollowState;
	}

	public String getSfFollowType() {
		return sfFollowType;
	}

	public void setSfFollowType(String sfFollowType) {
		this.sfFollowType = sfFollowType;
	}

	public Calendar getSfFollowDate() {
		return sfFollowDate;
	}

	public void setSfFollowDate(Calendar sfFollowDate) {
		this.sfFollowDate = sfFollowDate;
	}

	public Calendar getSfCreateDate() {
		return sfCreateDate;
	}

	public void setSfCreateDate(Calendar sfCreateDate) {
		this.sfCreateDate = sfCreateDate;
	}

	public String getSfOrgId() {
		return sfOrgId;
	}

	public void setSfOrgId(String sfOrgId) {
		this.sfOrgId = sfOrgId;
	}

	public String getSfHealthNum() {
		return sfHealthNum;
	}

	public void setSfHealthNum(String sfHealthNum) {
		this.sfHealthNum = sfHealthNum;
	}

	public String getSfIsOrNot() {
		return sfIsOrNot;
	}

	public void setSfIsOrNot(String sfIsOrNot) {
		this.sfIsOrNot = sfIsOrNot;
	}

	public Calendar getSfEndDate() {
		return sfEndDate;
	}

	public void setSfEndDate(Calendar sfEndDate) {
		this.sfEndDate = sfEndDate;
	}

	public String getSfHosId() {
		return sfHosId;
	}

	public void setSfHosId(String sfHosId) {
		this.sfHosId = sfHosId;
	}

	public String getSfHosAreaCode() {
		return sfHosAreaCode;
	}

	public void setSfHosAreaCode(String sfHosAreaCode) {
		this.sfHosAreaCode = sfHosAreaCode;
	}

	public String getSfXaxis() {
		return sfXaxis;
	}

	public void setSfXaxis(String sfXaxis) {
		this.sfXaxis = sfXaxis;
	}

	public String getSfYaxis() {
		return sfYaxis;
	}

	public void setSfYaxis(String sfYaxis) {
		this.sfYaxis = sfYaxis;
	}

	public String getSfTeamId() {
		return sfTeamId;
	}

	public void setSfTeamId(String sfTeamId) {
		this.sfTeamId = sfTeamId;
	}

	public String getSfPid() {
		return sfPid;
	}

	public void setSfPid(String sfPid) {
		this.sfPid = sfPid;
	}

	public String getSfReferral() {
		return sfReferral;
	}

	public void setSfReferral(String sfReferral) {
		this.sfReferral = sfReferral;
	}

	public String getSfReferralOrg() {
		return sfReferralOrg;
	}

	public void setSfReferralOrg(String sfReferralOrg) {
		this.sfReferralOrg = sfReferralOrg;
	}

	public String getSfReferralDept() {
		return sfReferralDept;
	}

	public void setSfReferralDept(String sfReferralDept) {
		this.sfReferralDept = sfReferralDept;
	}

	public String getSfReferralReason() {
		return sfReferralReason;
	}

	public void setSfReferralReason(String sfReferralReason) {
		this.sfReferralReason = sfReferralReason;
	}

	public String getSfIsTemporary() {
		return sfIsTemporary;
	}

	public void setSfIsTemporary(String sfIsTemporary) {
		this.sfIsTemporary = sfIsTemporary;
	}

	public String getSfRemindPlan() {
		return sfRemindPlan;
	}

	public void setSfRemindPlan(String sfRemindPlan) {
		this.sfRemindPlan = sfRemindPlan;
	}

	public String getSfTypeNum() {
		return sfTypeNum;
	}

	public void setSfTypeNum(String sfTypeNum) {
		this.sfTypeNum = sfTypeNum;
	}

	public Calendar getSfDeadTime() {
		return sfDeadTime;
	}

	public void setSfDeadTime(Calendar sfDeadTime) {
		this.sfDeadTime = sfDeadTime;
	}

	public String getSfMissReason() {
		return sfMissReason;
	}

	public void setSfMissReason(String sfMissReason) {
		this.sfMissReason = sfMissReason;
	}

	public Calendar getNextTime() {
		return nextTime;
	}

	public void setNextTime(Calendar nextTime) {
		this.nextTime = nextTime;
	}

	public String getNotSuccessReason() {
		return notSuccessReason;
	}

	public void setNotSuccessReason(String notSuccessReason) {
		this.notSuccessReason = notSuccessReason;
	}
}