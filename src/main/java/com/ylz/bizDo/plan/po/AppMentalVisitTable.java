package com.ylz.bizDo.plan.po;


import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 严重精神障碍患者随访服务记录表
 * Created by admin on 2017-05-13.
 */
@Entity
@Table(name = "APP_MENTALVISIT_TABLE")
public class AppMentalVisitTable extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;
    @Column(name = "VISIT_ID", length = 36)
    private String visitId;//随访外键
    @Column(name = "NAME",length = 20)
    private String name;//姓名
    @Column(name = "CODE",length = 100)
    private String code;//编号
    @Column(name = "FOLLOW_VISIT_TIME", length = 36)
    private String followVisitTime;//随访日期
    @Column(name = "PHONE_TEL",length = 11)
    private String phoneTel;//联系电话
    @Column(name = "PHONE_NAME",length = 20)
    private String phoneName;//联系人姓名
    @Column(name = "RELATIONSHIP_TO_VISIT",length = 20)
    private String relationshipToVisit;//与被访者关系
    @Column(name = "FOLLOW_VISIT_WAY",length = 10)
    private String followVisitWay;//随访方式
    @Column(name = "LOAT_VISIT",length = 20)
    private String loatVisit;//失访原因（1外出打工 2迁居他处 3走失  4连续3次未访到  5其他）
    @Column(name = "DEATH_DATE",length = 20)
    private String deathDate;//死亡日期
    @Column(name = "DEATH_REASON",length = 20)
    private String deathReason;//死亡原因 1躯体疾病①染病和寄生虫病 ②肿瘤 ③心脏病  ④脑血管病 ⑤呼吸系统疾病  ⑥消化系统疾病  ⑦其他疾病  ⑨不详  2自杀   3他杀   4意外   5精神疾病相关并发症    6其他
    @Column(name = "DEATH_DISEASE_TYPE",length = 20)
    private String deathDiseaseType;//死于什么躯体疾病
    @Column(name = "DANGEROUS",length = 20)
    private String dangerous;//危险性评估0 （0级） 1（1级） 2(2级)  3(3级)  4(4级)  5（5级）
    @Column(name = "NOW_SYMPTOMS",length = 200)
    private String nowSymptoms;//目前症状（多选） 1幻觉 2交流困难 3猜疑 4喜怒无常  5行为怪异  6兴奋话多 7伤人毁物8悲观厌世  9无故外走 10自语自笑  11孤僻懒散 12其他
    @Column(name = "NOW_SYMPTOMS_OTHER",length = 20)
    private String nowSymptomsOther;//其他症状内容
    @Column(name = "SELF_KNOWLEDGE",length = 20)
    private String selfKnowledge;//自知力 1自知力完全    2自知力不全    3自知力缺失
    @Column(name = "SLEEP",length = 20)
    private String sleep;//睡眠情况 1良好    2一般    3较差
    @Column(name = "DIET",length = 20)
    private String diet;//饮食情况 1良好    2一般    3较差
    @Column(name = "PERSONAL_LIFE",length = 20)
    private String personalLife;//个人生活料理 1良好    2一般    3较差
    @Column(name = "HOUSE_WORK",length = 20)
    private String housework;//家务劳动 1良好    2一般    3较差
    @Column(name = "WORK",length = 20)
    private String work;//生产劳动及工作 1良好    2一般    3较差  9此项不适用
    @Column(name = "LEARN_ABILITY",length = 20)
    private String learnAbility;//学习能力 1良好    2一般    3较差
    @Column(name = "INTERPERSONAL",length = 20)
    private String interpersonal;//社会人际交往 1良好    2一般    3较差
    @Column(name = "DANGEROUS_BEHAVIOR",length = 20)
    private String dangerousBehavior;//危险行为 1轻度滋事 2肇事 3肇祸 4其他危害行为 5自伤 6自杀未遂 7无
    @Column(name = "TROUBLE_NUM",length = 20)
    private String troubleNum;//轻度滋事次数
    @Column(name = "CAUSE_TROUBLE_NUM",length = 20)
    private String causeTroubleNum;//肇事次数
    @Column(name = "CAUSE_TROUBLE_NUM1",length = 20)
    private String causeTroubleNum1;//肇祸次数
    @Column(name = "OTHER_DANGER_NUM",length = 20)
    private String otherDangerNum;//其他危险行为次数
    @Column(name = "AUTOLESION_NUM",length = 20)
    private String autolesionNum;//自伤次数
    @Column(name = "SUICIDE_NUM",length = 20)
    private String suicideNum;//自杀未遂次数
    @Column(name = "FOLLOW_VISIT_SHUT",length = 20)
    private String followVisitShut;//两次随访期间关锁情况 1期间无关锁   2关锁  3期间关锁已解除
    @Column(name = "FOLLOW_VISIT_ZY",length = 20)
    private String followVisitZy;//两次随访期间住院情况 0期间未住院  1目前正在住院  2期间曾住院，现未住院
    @Column(name = "LAST_LEAVE_HOSPITAL_DATE",length = 20)
    private String lastLeaveHospitalDate;//末次出院时间
    @Column(name = "LABORATORY_CHECK",length = 20)
    private String laboratoryCheck;//实验室检查 1无    2有
    @Column(name = "LABORATORY_CHECK_CONTENT",length = 20)
    private String laboratoryCheckContent;//有实验室检查内容
    @Column(name = "MEDICATION_ADHERENCE",length = 20)
    private String medicationAdherence;//用药依从性 1按医嘱规律服药  2间断服药  3不服药  4医嘱勿需服药
    @Column(name = "DRUG_REACTION",length = 20)
    private String drugReaction;//药物不良反应 1无    2有
    @Column(name = "DRUG_REACTION_CONTENT",length = 20)
    private String drugReactionContent;//有药物不良反应内容
    @Column(name = "TREATMENT_RESULT",length = 20)
    private String treatmentResult;//治疗效果 1痊愈  2 好转   3 无变化   4 加重
    @Column(name = "REFERRAL",length = 20)
    private String referral;//是否转诊 1否  2是
    @Column(name = "REFERRAL_REASON",length = 20)
    private String referralReason;//转诊原因
    @Column(name = "REFERRAL_ORG",length = 50)
    private String referralOrg;//转诊机构id
    @Column(name = "REFERRAL_DEPT",length = 50)
    private String referralDept;//转诊科室
    @Column(name = "REHABILITATION_MEASURES",length = 20)
    private String rehabilitationMeasures;//康复措施(多选) 1生活劳动能力2职业训练3学习能力4社会交往5其他
    @Column(name = "REHABILITATION_MEASURES_OTHER",length = 20)
    private String rehabilitationMeasuresOther;//其他康复措施
    @Column(name = "FOLLOW_VISIT_TYPE",length = 20)
    private String followVisitType;//本次随访分类 1不稳定  2基本稳定  3稳定
    @Column(name = "NEXT_VISIT_TIME",length = 20)
    private String nextVisiTime;//下次随访日期
    @Column(name = "VISIT_DOCTOR_NAME",length = 20)
    private String visitDoctorName;//随访医生签名
    @Column(name = "VISIT_DOCTOR_ID",length = 36)
    private String visitDoctorId;//随访医生id
    @Column(name = "RESPONSIBLE_DOCTOR_ID",length = 36)
    private String responsibleDoctorId;//责任医生id
    @Column(name = "RESPONSIBLE_DOCTOR_Name",length = 20)
    private String responsibleDoctorName;//责任医生签名
    @Column(name = "INPUT_DOCTOR_ID",length = 36)
    private String inputDoctorId;//录入人员id
    @Column(name = "INPUT_DOCTOR_NAME",length = 20)
    private String inputDoctorName;//录入人员签名
    @Column(name = "PATIENT_ID",length = 36)
    private String patientId;//患者id
    @Column(name = "IS_BASIC",length = 10)
    private String isBasic="0";//是基卫数据(0否 1是)


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoatVisit() {
        return loatVisit;
    }

    public void setLoatVisit(String loatVisit) {
        this.loatVisit = loatVisit;
    }

    public String getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(String deathDate) {
        this.deathDate = deathDate;
    }

    public String getDeathReason() {
        return deathReason;
    }

    public void setDeathReason(String deathReason) {
        this.deathReason = deathReason;
    }

    public String getDangerous() {
        return dangerous;
    }

    public void setDangerous(String dangerous) {
        this.dangerous = dangerous;
    }

    public String getNowSymptoms() {
        return nowSymptoms;
    }

    public void setNowSymptoms(String nowSymptoms) {
        this.nowSymptoms = nowSymptoms;
    }

    public String getNowSymptomsOther() {
        return nowSymptomsOther;
    }

    public void setNowSymptomsOther(String nowSymptomsOther) {
        this.nowSymptomsOther = nowSymptomsOther;
    }

    public String getSelfKnowledge() {
        return selfKnowledge;
    }

    public void setSelfKnowledge(String selfKnowledge) {
        this.selfKnowledge = selfKnowledge;
    }

    public String getSleep() {
        return sleep;
    }

    public void setSleep(String sleep) {
        this.sleep = sleep;
    }

    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    public String getPersonalLife() {
        return personalLife;
    }

    public void setPersonalLife(String personalLife) {
        this.personalLife = personalLife;
    }

    public String getHousework() {
        return housework;
    }

    public void setHousework(String housework) {
        this.housework = housework;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) {
        this.work = work;
    }

    public String getLearnAbility() {
        return learnAbility;
    }

    public void setLearnAbility(String learnAbility) {
        this.learnAbility = learnAbility;
    }

    public String getInterpersonal() {
        return interpersonal;
    }

    public void setInterpersonal(String interpersonal) {
        this.interpersonal = interpersonal;
    }

    public String getDangerousBehavior() {
        return dangerousBehavior;
    }

    public void setDangerousBehavior(String dangerousBehavior) {
        this.dangerousBehavior = dangerousBehavior;
    }

    public String getTroubleNum() {
        return troubleNum;
    }

    public void setTroubleNum(String troubleNum) {
        this.troubleNum = troubleNum;
    }

    public String getCauseTroubleNum() {
        return causeTroubleNum;
    }

    public void setCauseTroubleNum(String causeTroubleNum) {
        this.causeTroubleNum = causeTroubleNum;
    }

    public String getCauseTroubleNum1() {
        return causeTroubleNum1;
    }

    public void setCauseTroubleNum1(String causeTroubleNum1) {
        this.causeTroubleNum1 = causeTroubleNum1;
    }

    public String getOtherDangerNum() {
        return otherDangerNum;
    }

    public void setOtherDangerNum(String otherDangerNum) {
        this.otherDangerNum = otherDangerNum;
    }

    public String getAutolesionNum() {
        return autolesionNum;
    }

    public void setAutolesionNum(String autolesionNum) {
        this.autolesionNum = autolesionNum;
    }

    public String getSuicideNum() {
        return suicideNum;
    }

    public void setSuicideNum(String suicideNum) {
        this.suicideNum = suicideNum;
    }

    public String getFollowVisitShut() {
        return followVisitShut;
    }

    public void setFollowVisitShut(String followVisitShut) {
        this.followVisitShut = followVisitShut;
    }

    public String getFollowVisitZy() {
        return followVisitZy;
    }

    public void setFollowVisitZy(String followVisitZy) {
        this.followVisitZy = followVisitZy;
    }

    public String getLastLeaveHospitalDate() {
        return lastLeaveHospitalDate;
    }

    public void setLastLeaveHospitalDate(String lastLeaveHospitalDate) {
        this.lastLeaveHospitalDate = lastLeaveHospitalDate;
    }

    public String getLaboratoryCheck() {
        return laboratoryCheck;
    }

    public void setLaboratoryCheck(String laboratoryCheck) {
        this.laboratoryCheck = laboratoryCheck;
    }

    public String getLaboratoryCheckContent() {
        return laboratoryCheckContent;
    }

    public void setLaboratoryCheckContent(String laboratoryCheckContent) {
        this.laboratoryCheckContent = laboratoryCheckContent;
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

    public String getTreatmentResult() {
        return treatmentResult;
    }

    public void setTreatmentResult(String treatmentResult) {
        this.treatmentResult = treatmentResult;
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

    public String getReferralOrg() {
        return referralOrg;
    }

    public void setReferralOrg(String referralOrg) {
        this.referralOrg = referralOrg;
    }

    public String getRehabilitationMeasures() {
        return rehabilitationMeasures;
    }

    public void setRehabilitationMeasures(String rehabilitationMeasures) {
        this.rehabilitationMeasures = rehabilitationMeasures;
    }

    public String getRehabilitationMeasuresOther() {
        return rehabilitationMeasuresOther;
    }

    public void setRehabilitationMeasuresOther(String rehabilitationMeasuresOther) {
        this.rehabilitationMeasuresOther = rehabilitationMeasuresOther;
    }

    public String getFollowVisitType() {
        return followVisitType;
    }

    public void setFollowVisitType(String followVisitType) {
        this.followVisitType = followVisitType;
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

    public String getReferralDept() {
        return referralDept;
    }

    public void setReferralDept(String referralDept) {
        this.referralDept = referralDept;
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

    public String getDeathDiseaseType() {
        return deathDiseaseType;
    }

    public void setDeathDiseaseType(String deathDiseaseType) {
        this.deathDiseaseType = deathDiseaseType;
    }

    public String getVisitDoctorId() {
        return visitDoctorId;
    }

    public void setVisitDoctorId(String visitDoctorId) {
        this.visitDoctorId = visitDoctorId;
    }

    public String getResponsibleDoctorId() {
        return responsibleDoctorId;
    }

    public void setResponsibleDoctorId(String responsibleDoctorId) {
        this.responsibleDoctorId = responsibleDoctorId;
    }

    public String getResponsibleDoctorName() {
        return responsibleDoctorName;
    }

    public void setResponsibleDoctorName(String responsibleDoctorName) {
        this.responsibleDoctorName = responsibleDoctorName;
    }

    public String getInputDoctorId() {
        return inputDoctorId;
    }

    public void setInputDoctorId(String inputDoctorId) {
        this.inputDoctorId = inputDoctorId;
    }

    public String getInputDoctorName() {
        return inputDoctorName;
    }

    public void setInputDoctorName(String inputDoctorName) {
        this.inputDoctorName = inputDoctorName;
    }
}
