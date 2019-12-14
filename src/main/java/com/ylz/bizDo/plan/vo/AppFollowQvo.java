package com.ylz.bizDo.plan.vo;

import com.ylz.bizDo.plan.Entity.AppMedicationTableEntity;

import java.util.List;

/**
 * Created by zzl on 2017/7/24.
 */
public class AppFollowQvo {

    private String id;//主键id
    private String visitId;//随访计划主键
    private String name;//名字
    private String code;//编号
    private String visitDate;//随访日期
    private String visitSituation;//随访情况（1失访 2死亡）
    private String dieDate;//死亡日期
    private String visitReason;//若失访则是失访原因，若死亡则是死亡原因
    private String symptoms;//症状
    private String symptomsOther;//其他症状内容
    private String bloodPressureOne;//血压（mmHg）舒张压
    private String bloodPressureTwo;//缩收压
    private String weight;//体重（kg）
    private String nextWeight;//下次目标体重
    private String height;//身高
    private String bmi;//体质指数（kg/m^2）
    private String heartRate;//心率（次/分钟）
    private String signsOther;//其他体征
    private String smokingToDay;//日吸烟量（支）
    private String nextSmokingToDay;//下次目标日吸烟量（支）
    private String drinkingToDay;//日饮酒量（两）
    private String nextDrinkingToDay;//下次目标日饮酒量（两）
    private String activityToWeek;//运动（次/周）
    private String nextActivityToWeek;//下次目标运动（次/周）
    private String activityToTime;//运动（分钟/次）
    private String nextActivityToTime;//下次目标运动（分钟/次）
    private String saltSituation;//摄盐情况
    private String nextSaltSituation;//下次目标摄盐情况
    private String mentalityAdjust;//心理调整
    private String followingBehavior;//遵医行为
    private String fzCheck;//辅助检查
    private String medicationAdherence;//服药依从性
    private String drugReaction;//药物不良反应
    private String drugReactionContent;//有药物不良反应内容
    private String visitThisType;//此次随访分类
    private List<AppMedicationTableEntity> userMedicine;//用药情况
    private String referral;//转诊情况
    private String referralReason;//转诊建议原因
    private String orgAndDept;//机构及科室
    private String nextVisiTime;//下次随访日期
    private String visitDoctorName;//随访医生签名
    private String dorsalisPedisPulse;//足背动脉搏动

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
    }

    public String getVisitSituation() {
        return visitSituation;
    }

    public void setVisitSituation(String visitSituation) {
        this.visitSituation = visitSituation;
    }

    public String getDieDate() {
        return dieDate;
    }

    public void setDieDate(String dieDate) {
        this.dieDate = dieDate;
    }

    public String getVisitReason() {
        return visitReason;
    }

    public void setVisitReason(String visitReason) {
        this.visitReason = visitReason;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getSymptomsOther() {
        return symptomsOther;
    }

    public void setSymptomsOther(String symptomsOther) {
        this.symptomsOther = symptomsOther;
    }

    public String getBloodPressureOne() {
        return bloodPressureOne;
    }

    public void setBloodPressureOne(String bloodPressureOne) {
        this.bloodPressureOne = bloodPressureOne;
    }

    public String getBloodPressureTwo() {
        return bloodPressureTwo;
    }

    public void setBloodPressureTwo(String bloodPressureTwo) {
        this.bloodPressureTwo = bloodPressureTwo;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getNextWeight() {
        return nextWeight;
    }

    public void setNextWeight(String nextWeight) {
        this.nextWeight = nextWeight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getBmi() {
        return bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }

    public String getSignsOther() {
        return signsOther;
    }

    public void setSignsOther(String signsOther) {
        this.signsOther = signsOther;
    }

    public String getSmokingToDay() {
        return smokingToDay;
    }

    public void setSmokingToDay(String smokingToDay) {
        this.smokingToDay = smokingToDay;
    }

    public String getNextSmokingToDay() {
        return nextSmokingToDay;
    }

    public void setNextSmokingToDay(String nextSmokingToDay) {
        this.nextSmokingToDay = nextSmokingToDay;
    }

    public String getDrinkingToDay() {
        return drinkingToDay;
    }

    public void setDrinkingToDay(String drinkingToDay) {
        this.drinkingToDay = drinkingToDay;
    }

    public String getNextDrinkingToDay() {
        return nextDrinkingToDay;
    }

    public void setNextDrinkingToDay(String nextDrinkingToDay) {
        this.nextDrinkingToDay = nextDrinkingToDay;
    }

    public String getActivityToWeek() {
        return activityToWeek;
    }

    public void setActivityToWeek(String activityToWeek) {
        this.activityToWeek = activityToWeek;
    }

    public String getNextActivityToWeek() {
        return nextActivityToWeek;
    }

    public void setNextActivityToWeek(String nextActivityToWeek) {
        this.nextActivityToWeek = nextActivityToWeek;
    }

    public String getActivityToTime() {
        return activityToTime;
    }

    public void setActivityToTime(String activityToTime) {
        this.activityToTime = activityToTime;
    }

    public String getNextActivityToTime() {
        return nextActivityToTime;
    }

    public void setNextActivityToTime(String nextActivityToTime) {
        this.nextActivityToTime = nextActivityToTime;
    }

    public String getSaltSituation() {
        return saltSituation;
    }

    public void setSaltSituation(String saltSituation) {
        this.saltSituation = saltSituation;
    }

    public String getNextSaltSituation() {
        return nextSaltSituation;
    }

    public void setNextSaltSituation(String nextSaltSituation) {
        this.nextSaltSituation = nextSaltSituation;
    }

    public String getMentalityAdjust() {
        return mentalityAdjust;
    }

    public void setMentalityAdjust(String mentalityAdjust) {
        this.mentalityAdjust = mentalityAdjust;
    }

    public String getFollowingBehavior() {
        return followingBehavior;
    }

    public void setFollowingBehavior(String followingBehavior) {
        this.followingBehavior = followingBehavior;
    }

    public String getFzCheck() {
        return fzCheck;
    }

    public void setFzCheck(String fzCheck) {
        this.fzCheck = fzCheck;
    }

    public String getMedicationAdherence() {
        return medicationAdherence;
    }

    public void setMedicationAdherence(String medicationAdherence) {
        this.medicationAdherence = medicationAdherence;
    }

    public String getDrugReaction() {
        return drugReaction;
    }

    public void setDrugReaction(String drugReaction) {
        this.drugReaction = drugReaction;
    }

    public String getDrugReactionContent() {
        return drugReactionContent;
    }

    public void setDrugReactionContent(String drugReactionContent) {
        this.drugReactionContent = drugReactionContent;
    }

    public String getVisitThisType() {
        return visitThisType;
    }

    public void setVisitThisType(String visitThisType) {
        this.visitThisType = visitThisType;
    }

    public List<AppMedicationTableEntity> getUserMedicine() {
        return userMedicine;
    }

    public void setUserMedicine(List<AppMedicationTableEntity> userMedicine) {
        this.userMedicine = userMedicine;
    }

    public String getReferral() {
        return referral;
    }

    public void setReferral(String referral) {
        this.referral = referral;
    }

    public String getReferralReason() {
        return referralReason;
    }

    public void setReferralReason(String referralReason) {
        this.referralReason = referralReason;
    }

    public String getOrgAndDept() {
        return orgAndDept;
    }

    public void setOrgAndDept(String orgAndDept) {
        this.orgAndDept = orgAndDept;
    }

    public String getNextVisiTime() {
        return nextVisiTime;
    }

    public void setNextVisiTime(String nextVisiTime) {
        this.nextVisiTime = nextVisiTime;
    }

    public String getVisitDoctorName() {
        return visitDoctorName;
    }

    public void setVisitDoctorName(String visitDoctorName) {
        this.visitDoctorName = visitDoctorName;
    }
}
