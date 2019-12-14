package com.ylz.bizDo.plan.po;


import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 肺结核患者随访服务记录表
 * Created by admin on 2017-05-13.
 */
@Entity
@Table(name = "APP_TB_FOLLOW_VISIT")
public class AppTBFollowVisitTable extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;
    @Column(name = "VISIT_ID", length = 36)
    private String visitId;//随访主表id
    @Column(name = "NAME", length = 36)
    private String name;//姓名
    @Column(name = "CODE",length = 100)
    private String code;//编号
    @Column(name = "FOLLOW_VISIT_DATE", length = 36)
    private String followVisitDate;//随时间
    @Column(name = "MONTH_NUM", length = 10)
    private String monthNum;//治疗月序
    @Column(name = "SUPERVISOR", length = 10)
    private String supervisor;//督导人员(1医生 2家属 3 自服药 4 其他)
    @Column(name = "FOLLOW_VISIT_TYPE", length = 10)
    private String followVisitType;//随访方式 1门诊2家庭3电话
    @Column(name = "SYMPTOMS", length = 10)
    private String symptoms;//症状及体征 0.没有症状1.咳嗽咳痰2.低热盗汗3.咯血或血痰4.胸痛消瘦5.恶心纳差6.关节疼痛7.头痛失眠8.视物模糊9.皮肤瘙痒、皮疹10.耳鸣、听力下降11.其他
    @Column(name = "SYMPTOMS_OTHER", length = 100)
    private String symptomsOther;//其他症状及体征
    @Column(name = "SMOKING_TO_NUM", length = 10)
    private String smokingToNum;//吸烟量（支）
    @Column(name = "SMOKING_TO_DAY",length = 10)
    private String smokingToDay;//吸烟量（日）
    @Column(name = "DRINKING_TO_NUM",length = 10)
    private String drinkingToNum;//饮酒量（两）
    @Column(name = "DRINKING_TO_DAY", length = 10)
    private String drinkingToDay;//饮酒量（日）
    @Column(name = "HL_METHODS", length = 36)
    private String hlMethods;//化疗方案
    @Column(name = "USER_METHOD", length = 10)
    private String userMethod;//用法1每日2 间歇□
    @Column(name = "DRUG_TYPE", length = 100)
    private String drugType;//药品剂型1 固定剂量复合制剂2 散装药3 板式组合药4 注射剂
    @Column(name = "NOT_EAT_PILLS_NUM", length = 10)
    private String notEatPillsNum;//漏服药次数
    @Column(name = "DRUG_REACTION", length = 10)
    private String drugReaction;//药物不良反应 1无 2有
    @Column(name = "DRUG_REACTION_CONTENT", length = 50)
    private String drugReactionContent;//有药物不良反应内容
    @Column(name = "COMPLICATION", length = 10)
    private String complication;//并发症或合并症 1无 2有
    @Column(name = "COMPLICATION_CONTENT", length = 10)
    private String complicationContent;//有并发症或合并症内容
    @Column(name = "REFERRAL_DEPT_TYPE", length = 36)
    private String referralDeptType;//转诊科别
    @Column(name = "REFERRAL_REASON", length = 50)
    private String referralReason;//转诊原因
    @Column(name = "REFERRAL_RESULT", length = 100)
    private String referralResult;//2周内随访，随访结果
    @Column(name = "OPINION", length = 100)
    private String opinion;//处理意见
    @Column(name = "NEXT_VISIT_TIME", length = 36)
    private String nextVisiTime;//下次随访日期
    @Column(name = "VISIT_DOCTOR_NAME", length = 20)
    private String visitDoctorName;//随访医生签名
    @Column(name = "VISIT_DOCTOR_ID",length = 36)
    private String visitDoctorId;//随访医生主键
    @Column(name = "STOP_DATE", length = 36)
    private String stopDate;//停止治疗时间
    @Column(name = "STOP_REASON", length = 10)
    private String stopReason;//停止治疗原因 1完成疗程 2死亡 3丢失 4转入耐多药治疗
    @Column(name = "SHOULD_VISIT_NUM", length = 10)
    private String shouldVisitNum;//应访视次数
    @Column(name = "NOW_VISIT_NUM", length = 10)
    private String nowVisitNum;//实际访视次数
    @Column(name = "SHOULD_TAKE_NUM", length = 10)
    private String shouldTakeNum;//应服药次数
    @Column(name = "NOW_TAKE_NUM", length = 10)
    private String nowTakeNum;//实际服药次数
    @Column(name = "TAKE_RATE", length = 10)
    private String takeRate;//服药率
    @Column(name = "ASSESS_DOCTOR_NAME", length = 20)
    private String assessDoctorName;//评估医生签名
    @Column(name = "ASSESS_DOCTOR_ID", length = 36)
    private String assessDoctorId;//评估医生id
    @Column(name = "PATIENT_ID",length = 36)
    private String patientId;//患者id
    @Column(name = "IS_BASIC",length = 10)
    private String isBasic="0";//是基卫数据(0否 1是)

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


    public String getFollowVisitDate() {
        return followVisitDate;
    }

    public void setFollowVisitDate(String followVisitDate) {
        this.followVisitDate = followVisitDate;
    }

    public String getMonthNum() {
        return monthNum;
    }

    public void setMonthNum(String monthNum) {
        this.monthNum = monthNum;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getFollowVisitType() {
        return followVisitType;
    }

    public void setFollowVisitType(String followVisitType) {
        this.followVisitType = followVisitType;
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

    public String getHlMethods() {
        return hlMethods;
    }

    public void setHlMethods(String hlMethods) {
        this.hlMethods = hlMethods;
    }

    public String getUserMethod() {
        return userMethod;
    }

    public void setUserMethod(String userMethod) {
        this.userMethod = userMethod;
    }

    public String getDrugType() {
        return drugType;
    }

    public void setDrugType(String drugType) {
        this.drugType = drugType;
    }

    public String getNotEatPillsNum() {
        return notEatPillsNum;
    }

    public void setNotEatPillsNum(String notEatPillsNum) {
        this.notEatPillsNum = notEatPillsNum;
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

    public String getComplication() {
        return complication;
    }

    public void setComplication(String complication) {
        this.complication = complication;
    }

    public String getComplicationContent() {
        return complicationContent;
    }

    public void setComplicationContent(String complicationContent) {
        this.complicationContent = complicationContent;
    }

    public String getReferralDeptType() {
        return referralDeptType;
    }

    public void setReferralDeptType(String referralDeptType) {
        this.referralDeptType = referralDeptType;
    }

    public String getReferralReason() {
        return referralReason;
    }

    public void setReferralReason(String referralReason) {
        this.referralReason = referralReason;
    }

    public String getReferralResult() {
        return referralResult;
    }

    public void setReferralResult(String referralResult) {
        this.referralResult = referralResult;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
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

    public String getStopDate() {
        return stopDate;
    }

    public void setStopDate(String stopDate) {
        this.stopDate = stopDate;
    }

    public String getStopReason() {
        return stopReason;
    }

    public void setStopReason(String stopReason) {
        this.stopReason = stopReason;
    }

    public String getShouldVisitNum() {
        return shouldVisitNum;
    }

    public void setShouldVisitNum(String shouldVisitNum) {
        this.shouldVisitNum = shouldVisitNum;
    }

    public String getNowVisitNum() {
        return nowVisitNum;
    }

    public void setNowVisitNum(String nowVisitNum) {
        this.nowVisitNum = nowVisitNum;
    }

    public String getShouldTakeNum() {
        return shouldTakeNum;
    }

    public void setShouldTakeNum(String shouldTakeNum) {
        this.shouldTakeNum = shouldTakeNum;
    }

    public String getNowTakeNum() {
        return nowTakeNum;
    }

    public void setNowTakeNum(String nowTakeNum) {
        this.nowTakeNum = nowTakeNum;
    }

    public String getTakeRate() {
        return takeRate;
    }

    public void setTakeRate(String takeRate) {
        this.takeRate = takeRate;
    }

    public String getAssessDoctorName() {
        return assessDoctorName;
    }

    public void setAssessDoctorName(String assessDoctorName) {
        this.assessDoctorName = assessDoctorName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSmokingToNum() {
        return smokingToNum;
    }

    public void setSmokingToNum(String smokingToNum) {
        this.smokingToNum = smokingToNum;
    }

    public String getDrinkingToNum() {
        return drinkingToNum;
    }

    public void setDrinkingToNum(String drinkingToNum) {
        this.drinkingToNum = drinkingToNum;
    }

    public String getVisitDoctorId() {
        return visitDoctorId;
    }

    public void setVisitDoctorId(String visitDoctorId) {
        this.visitDoctorId = visitDoctorId;
    }

    public String getAssessDoctorId() {
        return assessDoctorId;
    }

    public void setAssessDoctorId(String assessDoctorId) {
        this.assessDoctorId = assessDoctorId;
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
