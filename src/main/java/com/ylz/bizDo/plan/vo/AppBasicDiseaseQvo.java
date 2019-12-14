package com.ylz.bizDo.plan.vo;

import java.util.List;

/**
 * 基卫糖尿病随访请求参数
 * Created by zzl on 2018/1/25.
 */
public class AppBasicDiseaseQvo {
    private String id;
    private String visitId;//随访外键
    private String name;//姓名
    private String code;//编号
    private String followVisitTime;//随访日期
    private String followVisitWay;//随访方式
    private String symptoms;//症状
    private String symptomsOther;//其他症状
    private String bloodPressureOne;//收缩压（mmHg）
    private String weight;//体重（kg）
    private String bmi;//体质指数（kg/m^2）
    private String dorsalisPedisPulse;//足背动脉搏动（触及正常 减弱 消失）
    private String signsOther;//其他体征
    private String smokingToDay;//日吸烟量（支）
    private String drinkingToDay;//日饮酒量（两）
    private String activityToWeek;//运动（次/周）
    private String activityToTime;//运动（分钟/次）
    private String food;//主食（克/天）
    private String mentalityAdjust;//心理调整
    private String followingBehavior;//遵医行为
    private String fastingBloodSugar;//空腹血糖值
    private String otherCheck;//其他检查备注
    private String thHemoglobin;//糖化血红蛋白
    private String fzCheckDate;//检查日期
    private String medicationAdherence;//服药依从性
    private String drugReaction;//药物不良反应
    private String lowBloodGlucose;//低血糖反应
    private String visitThisType;//此次随访分类（控制满意 控制不满意 不良反应 并发症）
    private String insulin;//胰岛素种类
    private String userInsulin;//胰岛素用法和用量
    private String referralReason;//转诊建议原因
    private String referralOrg;//转诊机构
    private String nextVisiTime;//下次随访日期
    private String visitDoctorId;//随访医生Id
    private String bloodPressureTwo;//（mmHg）舒张压
    private String nextWeight;//下次目标体重
    private String height;//身高
    private String smokingNextToDay;//下次目标日吸烟量（支）
    private String drinkingNextToDay;//下次目标日饮酒量（两）
    private String activityNextToWeek;//下次目标运动（次/周）
    private String activityNextToTime;//下次目标运动（分钟/次）
    private String nextFood;//下次目标主食（克/天）
    private String dorsalisPedisValue;//足背动脉搏动(1双侧，2左侧，3右侧)
    private String referralDept;//转诊科室
    private String referral;//转诊
    private String visitSituation;//随访情况(正常 失访 死亡)
    private String visitReason;//失访原因或死亡原因
    private String dieDate;//死亡日期
    private String patientId;//患者id
    private String patientIdno;//患者身份证
    private String visitDate;//随访完成时间
    private String sfXaxis;//x轴
    private String sfYaxis;//y轴
    private String basicState;//0新增 1修改 2删除
    private List<AppBasicDrugQvo> drugList;//用药情况
    private String num;//第几次随访

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

    public String getFollowVisitTime() {
        return followVisitTime;
    }

    public void setFollowVisitTime(String followVisitTime) {
        this.followVisitTime = followVisitTime;
    }

    public String getFollowVisitWay() {
        return followVisitWay;
    }

    public void setFollowVisitWay(String followVisitWay) {
        this.followVisitWay = followVisitWay;
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

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getBmi() {
        return bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }

    public String getDorsalisPedisPulse() {
        return dorsalisPedisPulse;
    }

    public void setDorsalisPedisPulse(String dorsalisPedisPulse) {
        this.dorsalisPedisPulse = dorsalisPedisPulse;
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

    public String getDrinkingToDay() {
        return drinkingToDay;
    }

    public void setDrinkingToDay(String drinkingToDay) {
        this.drinkingToDay = drinkingToDay;
    }

    public String getActivityToWeek() {
        return activityToWeek;
    }

    public void setActivityToWeek(String activityToWeek) {
        this.activityToWeek = activityToWeek;
    }

    public String getActivityToTime() {
        return activityToTime;
    }

    public void setActivityToTime(String activityToTime) {
        this.activityToTime = activityToTime;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
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

    public String getFastingBloodSugar() {
        return fastingBloodSugar;
    }

    public void setFastingBloodSugar(String fastingBloodSugar) {
        this.fastingBloodSugar = fastingBloodSugar;
    }

    public String getOtherCheck() {
        return otherCheck;
    }

    public void setOtherCheck(String otherCheck) {
        this.otherCheck = otherCheck;
    }

    public String getThHemoglobin() {
        return thHemoglobin;
    }

    public void setThHemoglobin(String thHemoglobin) {
        this.thHemoglobin = thHemoglobin;
    }

    public String getFzCheckDate() {
        return fzCheckDate;
    }

    public void setFzCheckDate(String fzCheckDate) {
        this.fzCheckDate = fzCheckDate;
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

    public String getLowBloodGlucose() {
        return lowBloodGlucose;
    }

    public void setLowBloodGlucose(String lowBloodGlucose) {
        this.lowBloodGlucose = lowBloodGlucose;
    }

    public String getVisitThisType() {
        return visitThisType;
    }

    public void setVisitThisType(String visitThisType) {
        this.visitThisType = visitThisType;
    }

    public String getInsulin() {
        return insulin;
    }

    public void setInsulin(String insulin) {
        this.insulin = insulin;
    }

    public String getUserInsulin() {
        return userInsulin;
    }

    public void setUserInsulin(String userInsulin) {
        this.userInsulin = userInsulin;
    }

    public String getReferralReason() {
        return referralReason;
    }

    public void setReferralReason(String referralReason) {
        this.referralReason = referralReason;
    }

    public String getReferralOrg() {
        return referralOrg;
    }

    public void setReferralOrg(String referralOrg) {
        this.referralOrg = referralOrg;
    }

    public String getNextVisiTime() {
        return nextVisiTime;
    }

    public void setNextVisiTime(String nextVisiTime) {
        this.nextVisiTime = nextVisiTime;
    }

    public String getVisitDoctorId() {
        return visitDoctorId;
    }

    public void setVisitDoctorId(String visitDoctorId) {
        this.visitDoctorId = visitDoctorId;
    }

    public String getBloodPressureTwo() {
        return bloodPressureTwo;
    }

    public void setBloodPressureTwo(String bloodPressureTwo) {
        this.bloodPressureTwo = bloodPressureTwo;
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

    public String getSmokingNextToDay() {
        return smokingNextToDay;
    }

    public void setSmokingNextToDay(String smokingNextToDay) {
        this.smokingNextToDay = smokingNextToDay;
    }

    public String getDrinkingNextToDay() {
        return drinkingNextToDay;
    }

    public void setDrinkingNextToDay(String drinkingNextToDay) {
        this.drinkingNextToDay = drinkingNextToDay;
    }

    public String getActivityNextToWeek() {
        return activityNextToWeek;
    }

    public void setActivityNextToWeek(String activityNextToWeek) {
        this.activityNextToWeek = activityNextToWeek;
    }

    public String getActivityNextToTime() {
        return activityNextToTime;
    }

    public void setActivityNextToTime(String activityNextToTime) {
        this.activityNextToTime = activityNextToTime;
    }

    public String getNextFood() {
        return nextFood;
    }

    public void setNextFood(String nextFood) {
        this.nextFood = nextFood;
    }

    public String getDorsalisPedisValue() {
        return dorsalisPedisValue;
    }

    public void setDorsalisPedisValue(String dorsalisPedisValue) {
        this.dorsalisPedisValue = dorsalisPedisValue;
    }

    public String getReferralDept() {
        return referralDept;
    }

    public void setReferralDept(String referralDept) {
        this.referralDept = referralDept;
    }

    public String getReferral() {
        return referral;
    }

    public void setReferral(String referral) {
        this.referral = referral;
    }

    public String getVisitSituation() {
        return visitSituation;
    }

    public void setVisitSituation(String visitSituation) {
        this.visitSituation = visitSituation;
    }

    public String getVisitReason() {
        return visitReason;
    }

    public void setVisitReason(String visitReason) {
        this.visitReason = visitReason;
    }

    public String getDieDate() {
        return dieDate;
    }

    public void setDieDate(String dieDate) {
        this.dieDate = dieDate;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientIdno() {
        return patientIdno;
    }

    public void setPatientIdno(String patientIdno) {
        this.patientIdno = patientIdno;
    }

    public String getVisitDate() {
        return visitDate;
    }

    public void setVisitDate(String visitDate) {
        this.visitDate = visitDate;
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

    public String getBasicState() {
        return basicState;
    }

    public void setBasicState(String basicState) {
        this.basicState = basicState;
    }

    public List<AppBasicDrugQvo> getDrugList() {
        return drugList;
    }

    public void setDrugList(List<AppBasicDrugQvo> drugList) {
        this.drugList = drugList;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
