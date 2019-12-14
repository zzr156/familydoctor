package com.ylz.bizDo.plan.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 2型糖尿病患者随访服务记录表
 * Created by admin on 2017-05-12.
 */
@Entity
@Table(name = "APP_DIABETES_TABLE")
public class AppDiabetesTable extends BasePO {

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
    private String bloodPressureOne;//收缩压（mmHg）
    @Column(name = "WEIGHT", length = 50)
    private String weight;//体重（kg）
    @Column(name = "BMI", length = 50)
    private String bmi;//体质指数（kg/m^2）
    @Column(name = "DORSALIS_PEDIS_PULSE", length = 50)
    private String dorsalisPedisPulse;//足背动脉搏动（触及正常 减弱 消失）
    @Column(name = "SIGNS_OTHER", length = 200)
    private String signsOther;//其他体征
    @Column(name = "SOMKING_TO_DAY", length = 20)
    private String smokingToDay;//日吸烟量（支）
    @Column(name = "DRINKING_TO_DAY", length = 20)
    private String drinkingToDay;//日饮酒量（两）
    @Column(name = "ACTIVITY_TO_WEEK", length = 20)
    private String activityToWeek;//运动（次/周）
    @Column(name = "ACTIVITY_TO_TIME", length = 20)
    private String activityToTime;//运动（分钟/次）
    @Column(name = "FOOD", length = 20)
    private String food;//主食（克/天）
    @Column(name = "MENTALITY_ADJUST", length = 200)
    private String mentalityAdjust;//心理调整
    @Column(name = "FOLLOWING_BEHAVIOR", length = 50)
    private String followingBehavior;//遵医行为
    @Column(name = "FASTING_BLOOD_SUGAR", length = 50)
    private String fastingBloodSugar;//空腹血糖值
    @Column(name = "OTHER_CHECK", length = 50)
    private String otherCheck;//其他检查备注
    @Column(name = "TH_HEMOGLOBIN", length = 50)
    private String thHemoglobin;//糖化血红蛋白
    @Column(name = "FZ_CHECK_DATE", length = 20)
    private String fzCheckDate;//检查日期
    @Column(name = "MEDICATION_ADHERENCE", length = 50)
    private String medicationAdherence;//服药依从性
    @Column(name = "DRUG_REACTION", length = 50)
    private String drugReaction;//药物不良反应
    @Column(name = "LOW_BLOOD_BLUCOSE", length = 50)
    private String lowBloodGlucose;//低血糖反应
    @Column(name = "VISIT_THIS_TYPE", length = 50)
    private String visitThisType;//此次随访分类（控制满意 控制不满意 不良反应 并发症）
    @Column(name = "INSULIN", length = 50)
    private String insulin;//胰岛素种类
    @Column(name = "USE_INSULIN", length = 200)
    private String userInsulin;//胰岛素用法和用量
    @Column(name = "REFERRAL_REASON", length = 200)
    private String referralReason;//转诊建议原因
    @Column(name = "REFERRAL_ORG", length = 100)
    private String referralOrg;//转诊机构
    @Column(name = "NEXT_VISIT_TIME", length = 20)
    private String nextVisiTime;//下次随访日期
    @Column(name = "VISIT_DOCTOR_NAME", length = 50)
    private String visitDoctorName;//随访医生签名
    @Column(name = "VISIT_DOCTOR_ID",length = 36)
    private String visitDoctorId;//随访医生id
    @Column(name = "BLOOD_PRESSURE_TWO", length = 50)
    private String bloodPressureTwo;//（mmHg）舒张压
    @Column(name = "NEXT_WEIGHT",length = 50)
    private String nextWeight;//下次目标体重
    @Column(name = "HEIGHT", length = 10)
    private String height;//身高
    @Column(name = "SOMKING_NEXTTO_DAY", length = 20)
    private String smokingNextToDay;//下次目标日吸烟量（支）
    @Column(name = "DRINKING_NEXTTO_DAY", length = 20)
    private String drinkingNextToDay;//下次目标日饮酒量（两）
    @Column(name = "ACTIVITY_NEXTTO_WEEK", length = 20)
    private String activityNextToWeek;//下次目标运动（次/周）
    @Column(name = "ACTIVITY_NEXTTO_TIME", length = 20)
    private String activityNextToTime;//下次目标运动（分钟/次）
    @Column(name = "NEXT_FOOD", length = 20)
    private String nextFood;//下次目标主食（克/天）
    @Column(name = "DORSALIS_PEDIS_VALUE", length = 50)
    private String dorsalisPedisValue;//足背动脉搏动(1双侧，2左侧，3右侧)
    @Column(name = "REFERRAL_DEPT", length = 100)
    private String referralDept;//转诊科室
    @Column(name = "REFERRAL",length = 10)
    private String referral;//转诊
    @Column(name = "VISIT_SITUATION",length = 10)
    private String visitSituation;//随访情况(正常 失访 死亡)
    @Column(name = "VISIT_REASON")
    private String visitReason;//失访原因或死亡原因
    @Column(name = "DIE_DATE",length = 50)
    private String dieDate;//死亡日期
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

    public String getReferralOrg() {
        return referralOrg;
    }

    public void setReferralOrg(String referralOrg) {
        this.referralOrg = referralOrg;
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

    public String getIsBasic() {
        return isBasic;
    }

    public void setIsBasic(String isBasic) {
        this.isBasic = isBasic;
    }

    public String getVisitDoctorId() {
        return visitDoctorId;
    }

    public void setVisitDoctorId(String visitDoctorId) {
        this.visitDoctorId = visitDoctorId;
    }
}
