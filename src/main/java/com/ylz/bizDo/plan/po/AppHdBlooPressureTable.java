package com.ylz.bizDo.plan.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 高血压患者随访服务记录表
 * Created by admin on 2017-05-12.
 */
@Entity
@Table(name = "APP_HDBLOOPRESSURE_TABLE")
public class AppHdBlooPressureTable extends BasePO {

    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;
    @Column(name = "VISIT_ID", length = 36)
    private String visitId;//随访外键
    @Column(name = "NAME",length = 50)
    private String name;//姓名
    @Column(name = "CODE",length = 100)
    private String code;//编号
    @Column(name = "FOLLOW_VISIT_TIME", length = 36)
    private String followVisitTime;//随访日期
    @Column(name = "FOLLOW_VISIT_WAY",length = 10)
    private String followVisitWay;//随访方式
    @Column(name = "SYMPTOMS", length = 50)
    private String symptoms;//症状
    @Column(name = "SYMPTOMS_OTHER",length = 100)
    private String symptomsOther;//其他症状
    @Column(name = "BLOOD_PRESSURE_ONE", length = 50)
    private String bloodPressureOne;//血压（mmHg）收缩压
    @Column(name = "WEIGHT", length = 50)
    private String weight;//体重（kg）
    @Column(name = "NEXT_WEIGHT",length = 50)
    private String nextWeight;//下一次目标体重
    @Column(name = "HEIGHT", length = 10)
    private String height;//身高
    @Column(name = "BMI", length = 50)
    private String bmi;//体质指数（kg/m^2）
    @Column(name = "HEART_RATE", length = 50)
    private String heartRate;//心率（次/分钟）
    @Column(name = "SIGNS_OTHER", length = 200)
    private String signsOther;//其他体征
    @Column(name = "SOMKING_TO_DAY", length = 20)
    private String smokingToDay;//日吸烟量（支）
    @Column(name = "SOMKING_NEXTTO_DAY", length = 20)
    private String smokingNextToDay;//下次目标日吸烟量（支）
    @Column(name = "DRINKING_TO_DAY", length = 20)
    private String drinkingToDay;//日饮酒量（两）
    @Column(name = "DRINKING_NEXTTO_DAY", length = 20)
    private String drinkingNextToDay;//下次目标日饮酒量（两）
    @Column(name = "ACTIVITY_TO_WEEK", length = 20)
    private String activityToWeek;//运动（次/周）
    @Column(name = "ACTIVITY_TO_TIME", length = 20)
    private String activityToTime;//运动（分钟/次）
    @Column(name = "ACTIVITY_NEXTTO_WEEK", length = 20)
    private String activityNextToWeek;//下次目标运动（次/周）
    @Column(name = "ACTIVITY_NEXTTO_TIME", length = 20)
    private String activityNextToTime;//下次目标运动（分钟/次）
    @Column(name = "SALT_SITUATION", length = 20)
    private String saltSituation;//摄盐情况
    @Column(name = "SALT_NEXT_SITUATION", length = 20)
    private String saltNextSituation;//下次目标摄盐情况
    @Column(name = "MENTALITY_ADJUST", length = 200)
    private String mentalityAdjust;//心理调整
    @Column(name = "FOLLOWING_BEHAVIOR", length = 50)
    private String followingBehavior;//遵医行为
    @Column(name = "FZ_CHECK", length = 50)
    private String fzCheck;//辅助检查
    @Column(name = "MEDICATION_ADHERENCE", length = 50)
    private String medicationAdherence;//服药依从性
    @Column(name = "DRUG_REACTION", length = 50)
    private String drugReaction;//药物不良反应
    @Column(name = "DRUG_REACTION_CONTENT", length = 200)
    private String drugReactionContent;//有药物不良反应内容
    @Column(name = "VISIT_THIS_TYPE", length = 50)
    private String visitThisType;//此次随访分类
    @Column(name = "REFERRAL",length = 10)
    private String referral;//转诊情况
    @Column(name = "REFERRAL_REASON", length = 50)
    private String referralReason;//转诊建议原因
    @Column(name = "NEXT_VISI_TIME", length = 50)
    private String nextVisiTime;//下次随访日期
    @Column(name = "VISIT_DOCTOR_NAME", length = 50)
    private String visitDoctorName;//随访医生签名
    @Column(name = "BLOOD_PRESSURE_TWO", length = 50)
    private String bloodPressureTwo;//血压（mmHg）舒张压
    @Column(name = "VISIT_SITUATION",length = 10)
    private String visitSituation;//随访情况(正常 失访 死亡)
    @Column(name = "VISIT_REASON")
    private String visitReason;//失访原因或死亡原因
    @Column(name = "DIE_DATE",length = 50)
    private String dieDate;//死亡日期
    @Column(name = "REFERRAL_ORG",length = 36)
    private String referralOrg;//转诊机构
    @Column(name = "REFERRAL_DEPT",length = 100)
    private String referralDept;//转诊科室
    @Column(name = "PATIENT_ID",length = 36)
    private String patientId;//患者id
    @Column(name = "IS_BASIC",length = 10)
    private String isBasic="0";//是基卫数据(0否 1是)

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
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

    public String getSaltSituation() {
        return saltSituation;
    }

    public void setSaltSituation(String saltSituation) {
        this.saltSituation = saltSituation;
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

    public String getReferralReason() {
        return referralReason;
    }

    public void setReferralReason(String referralReason) {
        this.referralReason = referralReason;
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

    public String getBloodPressureTwo() {
        return bloodPressureTwo;
    }

    public void setBloodPressureTwo(String bloodPressureTwo) {
        this.bloodPressureTwo = bloodPressureTwo;
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

    public String getSymptomsOther() {
        return symptomsOther;
    }

    public void setSymptomsOther(String symptomsOther) {
        this.symptomsOther = symptomsOther;
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

    public String getSaltNextSituation() {
        return saltNextSituation;
    }

    public void setSaltNextSituation(String saltNextSituation) {
        this.saltNextSituation = saltNextSituation;
    }

    public String getDieDate() {
        return dieDate;
    }

    public void setDieDate(String dieDate) {
        this.dieDate = dieDate;
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

    public String getReferral() {
        return referral;
    }

    public void setReferral(String referral) {
        this.referral = referral;
    }

    public String getReferralOrg() {
        return referralOrg;
    }

    public void setReferralOrg(String referralOrg) {
        this.referralOrg = referralOrg;
    }

    public String getReferralDept() {
        return referralDept;
    }

    public void setReferralDept(String referralDept) {
        this.referralDept = referralDept;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getIsBasic() {
        return isBasic;
    }

    public void setIsBasic(String isBasic) {
        this.isBasic = isBasic;
    }
}
