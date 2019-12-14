package com.ylz.bizDo.plan.po;


import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 第一次产前检查服务记录表
 * Created by admin on 2017-05-13.
 */
@Entity
@Table(name = "APP_FIRSTPRENATAL_TABLE")
public class AppFirstPrenatalTable extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;
    @Column(name = "VISIT_ID", length = 36)
    private String visitId;//随访外键
    @Column(name = "NAME", length = 50)
    private String name;//姓名
    @Column(name = "CODE",length = 100)
    private String code;//编号
    @Column(name = "CREATION_TIME", length = 20)
    private String creationTime;//填表日期
    @Column(name = "WEEK_OF_BIRTH", length = 20)
    private String weekOfBirth;//孕周
    @Column(name = "PREGNANT_WOMAN_AGE", length = 20)
    private String pregnantWomanAge;//孕妇年龄
    @Column(name = "HUSBAND_NAME", length = 50)
    private String husbandName;//丈夫姓名
    @Column(name = "HUSBAND_AGE", length = 20)
    private String husbandAge;//丈夫年龄
    @Column(name = "HUSBAND_PHONE", length = 20)
    private String husbandPhone;//丈夫电话
    @Column(name = "PREGNANT_NUM", length = 20)
    private String pregnantNum;//孕次
    @Column(name = "PARITY_NUM1", length = 20)
    private String parityNum1;//产次阴道分娩次数
    @Column(name = "PARITY_NUM2", length = 20)
    private String parityNum2;//剖宫产次数
    @Column(name = "LAST_MENSTRUATION", length = 20)
    private String lastMenstruation;//末次月经
    @Column(name = "DUE_DATE", length = 20)
    private String dueDate;//预产期
    @Column(name = "HISTORY", length = 50)
    private String history;//既往史
    @Column(name = "HISTORY_OTHER", length = 200)
    private String historyOther;//既往史其他选项内容
    @Column(name = "FAMILY_HISTORY", length = 50)
    private String familyHistory;//家族史
    @Column(name = "FAMILY_HISTORY_OTHER", length = 200)
    private String familyHistoryOther;// 家族史其他选项内容
    @Column(name = "PERSONAL_HISTORY", length = 50)
    private String personalHistory;//个人史
    @Column(name = "PERSONAL_HISTORY_OTHER", length = 200)
    private String personalHistoryOther;//个人史其他选项内容
    @Column(name = "MATERNITY_HISTORY", length = 200)
    private String maternityHistory;//妇产科手术史
    @Column(name = "MATERNITY_HISTORY_CONTENT", length = 200)
    private String maternityHistoryContent;//妇产科手术史内容
    @Column(name = "MATERNAL_ZRLC",length = 10)
    private String maternalZrlc;//自然流产次数
    @Column(name = "MATERNAL_RGLC",length = 10)
    private String maternalRglc;//人工流产
    @Column(name = "MATERNAL_ST",length = 10)
    private String maternalSt;//死胎次数
    @Column(name = "MATERNAL_SC",length = 10)
    private String matermalSc;//死产
    @Column(name = "MATERNAL_NEW_DIE",length = 10)
    private String maternalNewDie;//新生儿死亡次数
    @Column(name = "MATERNAL_DEFECTS",length = 10)
    private String maternalDefects;//出生缺陷儿
    @Column(name = "WEIGHT", length = 50)
    private String weight;//体重
    @Column(name = "HEIGHT", length = 50)
    private String height;//身高
    @Column(name = "BMI", length = 50)
    private String bmi;//体质指数（kg/m^2）
    @Column(name = "BLOOD_PRESSURE_ONE", length = 50)
    private String bloodPressureOne;//舒张压（mmHg）
    @Column(name = "AUSCULTATION_HEART", length = 20)
    private String auscultationHeart;//心脏听诊
    @Column(name = "AUSCULTATION_HEART_CONTENT", length = 200)
    private String auscultationHeartContent;//心脏听诊异常内容
    @Column(name = "AUSCULTATION_LUNGS", length = 20)
    private String auscultationLungs;//肺部听诊
    @Column(name = "AUSCULTATION_LUNGS_CONTENT", length = 200)
    private String auscultationLungsContent;//肺部听诊异常内容
    @Column(name = "VULVA", length = 20)
    private String vulva;//外阴检查
    @Column(name = "VULVA_CONTENT", length = 200)
    private String vulvaContent;//外阴异常内容
    @Column(name = "VAGINA", length = 20)
    private String vagina;//阴道检查
    @Column(name = "VAGINA_CONTENT", length = 200)
    private String vaginaContent;//阴道异常内容
    @Column(name = "CERVICAL", length = 20)
    private String cervical;//宫颈检查
    @Column(name = "CERVICAL_CONTENT", length = 200)
    private String cervicalContent;//宫颈异常内容
    @Column(name = "UTERUS", length = 20)
    private String uterus;//子宫
    @Column(name = "UTERUS_CONTENT", length = 200)
    private String uterusContent;//子宫异常内容
    @Column(name = "FJ", length = 20)
    private String fj;//附件
    @Column(name = "FJ_CONTENT", length = 200)
    private String fjContent;//附件异常内容
    @Column(name = "HEMOGLOBIN", length = 20)
    private String hemoglobin;//血红蛋白值
    @Column(name = "HEMAMEBA_NUM", length = 20)
    private String hemamebaNum;//白细胞计数值
    @Column(name = "PLATELET_NUM", length = 20)
    private String plateletNum;//血小板计数值
    @Column(name = "ROUTINE_BLOOD_OTHER", length = 200)
    private String routineBloodOther;//其他血常规内容
    @Column(name = "URINE_PROTEIN", length = 20)
    private String urineProtein;//尿蛋白
    @Column(name = "URINE_SUGAR", length = 20)
    private String urineSugar;//尿糖
    @Column(name = "URINE_KETONE_BODY", length = 20)
    private String urineKetoneBody;//尿酮体
    @Column(name = "ERY", length = 20)
    private String ery;//尿潜血
    @Column(name = "URINE_OTHER", length = 200)
    private String urineOther;//其他尿常规检查
    @Column(name = "BLOOD_TYPE", length = 20)
    private String bloodType;//血型
    @Column(name = "BLOOD_SUGAR", length = 20)
    private String bloodSugar;//血糖
    @Column(name = "SERUM_ALT", length = 20)
    private String serumAlt;//血清谷丙转氨酶
    @Column(name = "SERUM_CZ", length = 20)
    private String serumCz;//血清谷草转氨酶
    @Column(name = "ALBUMIN", length = 20)
    private String albumin;//白蛋白
    @Column(name = "TOTAL_BILIRUBIN", length = 20)
    private String totalBilirubin;//总胆红素
    @Column(name = "DIRECT_BILIRUBIN", length = 20)
    private String directBilirubin;//结合胆红素
    @Column(name = "SERUM_CREATININE", length = 20)
    private String serumCreatinine;//血清肌酐
    @Column(name = "BLOOD_UREA", length = 20)
    private String bloodUrea;//血尿素
    @Column(name = "VAGINAL_DISCHARGE", length = 20)
    private String vaginalDischarge;//阴道分泌物
    @Column(name = "VAGINAL_DISCHARGE_OTHER", length = 200)
    private String vaginalDischargeOther;//其他阴道分泌物内容
    @Column(name = "VAGINAL_CLEANNESS", length = 20)
    private String vaginalCleanness;//阴道清洁度
    @Column(name = "HBS_AG", length = 20)
    private String hbsAg;//乙型肝炎表面抗原
    @Column(name = "HBS_AB", length = 20)
    private String hbsAb;//乙型肝炎表面抗体
    @Column(name = "HBE_AG", length = 20)
    private String hbeAg;//乙型肝炎e抗原
    @Column(name = "HBE_AB", length = 20)
    private String hbeAb;//乙型肝炎e抗体
    @Column(name = "HBC_AB", length = 20)
    private String hbcAb;//乙型肝炎核心抗体
    @Column(name = "SYPHILIS_TEST_RESULT", length = 20)
    private String syphilisTestResult;//梅毒血清学实验结果
    @Column(name = "HIV_RESULT", length = 20)
    private String hivResult;//HIV抗体检测
    @Column(name = "ULTRASOUND_TO_B", length = 200)
    private String ultrasoundToB;//B超
    @Column(name = "FZ_CHECK_OTHER", length = 200)
    private String fzCheckOther;//其他辅助检查
    @Column(name = "TOTAL_ASSESS", length = 20)
    private String totalAssess;//总体评估
    @Column(name = "TOTAL_ASSESS_OTHER", length = 200)
    private String totalAssessOther;//总体评估异常内容
    @Column(name = "HEALTH_GUIDELINES", length = 200)
    private String healthGuidelines;//保健指导
    @Column(name = "HEALTH_GUIDELINES_OTHER", length = 200)
    private String healthGuidelinesOther;//其他保健指导
    @Column(name = "REFERRAL", length = 20)
    private String referral;//转诊情况
    @Column(name = "REFERRAL_REASON", length = 200)
    private String referralReason;//转诊建议原因
    @Column(name = "REFERRAL_ORG", length = 50)
    private String referralOrg;//转诊机构
    @Column(name = "NEXT_VISIT_TIME", length = 20)
    private String nextVisiTime;//下次随访日期
    @Column(name = "VISIT_DOCTOR_NAME", length = 50)
    private String visitDoctorName;//随访医生签名
    @Column(name = "BLOOD_PRESSURE_TWO", length = 50)
    private String bloodPressureTwo;//舒张压（mmHg）
    @Column(name = "REFERRAL_DEPT",length = 50)
    private String referralDept;//转诊科室



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getWeekOfBirth() {
        return weekOfBirth;
    }

    public void setWeekOfBirth(String weekOfBirth) {
        this.weekOfBirth = weekOfBirth;
    }

    public String getPregnantWomanAge() {
        return pregnantWomanAge;
    }

    public void setPregnantWomanAge(String pregnantWomanAge) {
        this.pregnantWomanAge = pregnantWomanAge;
    }

    public String getHusbandName() {
        return husbandName;
    }

    public void setHusbandName(String husbandName) {
        this.husbandName = husbandName;
    }

    public String getHusbandAge() {
        return husbandAge;
    }

    public void setHusbandAge(String husbandAge) {
        this.husbandAge = husbandAge;
    }

    public String getHusbandPhone() {
        return husbandPhone;
    }

    public void setHusbandPhone(String husbandPhone) {
        this.husbandPhone = husbandPhone;
    }

    public String getPregnantNum() {
        return pregnantNum;
    }

    public void setPregnantNum(String pregnantNum) {
        this.pregnantNum = pregnantNum;
    }

    public String getParityNum1() {
        return parityNum1;
    }

    public void setParityNum1(String parityNum1) {
        this.parityNum1 = parityNum1;
    }

    public String getParityNum2() {
        return parityNum2;
    }

    public void setParityNum2(String parityNum2) {
        this.parityNum2 = parityNum2;
    }

    public String getLastMenstruation() {
        return lastMenstruation;
    }

    public void setLastMenstruation(String lastMenstruation) {
        this.lastMenstruation = lastMenstruation;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getHistoryOther() {
        return historyOther;
    }

    public void setHistoryOther(String historyOther) {
        this.historyOther = historyOther;
    }

    public String getFamilyHistory() {
        return familyHistory;
    }

    public void setFamilyHistory(String familyHistory) {
        this.familyHistory = familyHistory;
    }

    public String getFamilyHistoryOther() {
        return familyHistoryOther;
    }

    public void setFamilyHistoryOther(String familyHistoryOther) {
        this.familyHistoryOther = familyHistoryOther;
    }

    public String getPersonalHistory() {
        return personalHistory;
    }

    public void setPersonalHistory(String personalHistory) {
        this.personalHistory = personalHistory;
    }

    public String getPersonalHistoryOther() {
        return personalHistoryOther;
    }

    public void setPersonalHistoryOther(String personalHistoryOther) {
        this.personalHistoryOther = personalHistoryOther;
    }

    public String getMaternityHistory() {
        return maternityHistory;
    }

    public void setMaternityHistory(String maternityHistory) {
        this.maternityHistory = maternityHistory;
    }

    public String getMaternityHistoryContent() {
        return maternityHistoryContent;
    }

    public void setMaternityHistoryContent(String maternityHistoryContent) {
        this.maternityHistoryContent = maternityHistoryContent;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
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

    public String getBloodPressureOne() {
        return bloodPressureOne;
    }

    public void setBloodPressureOne(String bloodPressureOne) {
        this.bloodPressureOne = bloodPressureOne;
    }

    public String getAuscultationHeart() {
        return auscultationHeart;
    }

    public void setAuscultationHeart(String auscultationHeart) {
        this.auscultationHeart = auscultationHeart;
    }

    public String getAuscultationHeartContent() {
        return auscultationHeartContent;
    }

    public void setAuscultationHeartContent(String auscultationHeartContent) {
        this.auscultationHeartContent = auscultationHeartContent;
    }

    public String getAuscultationLungs() {
        return auscultationLungs;
    }

    public void setAuscultationLungs(String auscultationLungs) {
        this.auscultationLungs = auscultationLungs;
    }

    public String getAuscultationLungsContent() {
        return auscultationLungsContent;
    }

    public void setAuscultationLungsContent(String auscultationLungsContent) {
        this.auscultationLungsContent = auscultationLungsContent;
    }

    public String getVulva() {
        return vulva;
    }

    public void setVulva(String vulva) {
        this.vulva = vulva;
    }

    public String getVulvaContent() {
        return vulvaContent;
    }

    public void setVulvaContent(String vulvaContent) {
        this.vulvaContent = vulvaContent;
    }

    public String getVagina() {
        return vagina;
    }

    public void setVagina(String vagina) {
        this.vagina = vagina;
    }

    public String getVaginaContent() {
        return vaginaContent;
    }

    public void setVaginaContent(String vaginaContent) {
        this.vaginaContent = vaginaContent;
    }

    public String getCervical() {
        return cervical;
    }

    public void setCervical(String cervical) {
        this.cervical = cervical;
    }

    public String getCervicalContent() {
        return cervicalContent;
    }

    public void setCervicalContent(String cervicalContent) {
        this.cervicalContent = cervicalContent;
    }

    public String getUterus() {
        return uterus;
    }

    public void setUterus(String uterus) {
        this.uterus = uterus;
    }

    public String getUterusContent() {
        return uterusContent;
    }

    public void setUterusContent(String uterusContent) {
        this.uterusContent = uterusContent;
    }

    public String getFj() {
        return fj;
    }

    public void setFj(String fj) {
        this.fj = fj;
    }

    public String getFjContent() {
        return fjContent;
    }

    public void setFjContent(String fjContent) {
        this.fjContent = fjContent;
    }

    public String getHemoglobin() {
        return hemoglobin;
    }

    public void setHemoglobin(String hemoglobin) {
        this.hemoglobin = hemoglobin;
    }

    public String getHemamebaNum() {
        return hemamebaNum;
    }

    public void setHemamebaNum(String hemamebaNum) {
        this.hemamebaNum = hemamebaNum;
    }

    public String getPlateletNum() {
        return plateletNum;
    }

    public void setPlateletNum(String plateletNum) {
        this.plateletNum = plateletNum;
    }

    public String getRoutineBloodOther() {
        return routineBloodOther;
    }

    public void setRoutineBloodOther(String routineBloodOther) {
        this.routineBloodOther = routineBloodOther;
    }

    public String getUrineProtein() {
        return urineProtein;
    }

    public void setUrineProtein(String urineProtein) {
        this.urineProtein = urineProtein;
    }

    public String getUrineSugar() {
        return urineSugar;
    }

    public void setUrineSugar(String urineSugar) {
        this.urineSugar = urineSugar;
    }

    public String getUrineKetoneBody() {
        return urineKetoneBody;
    }

    public void setUrineKetoneBody(String urineKetoneBody) {
        this.urineKetoneBody = urineKetoneBody;
    }

    public String getEry() {
        return ery;
    }

    public void setEry(String ery) {
        this.ery = ery;
    }

    public String getUrineOther() {
        return urineOther;
    }

    public void setUrineOther(String urineOther) {
        this.urineOther = urineOther;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getBloodSugar() {
        return bloodSugar;
    }

    public void setBloodSugar(String bloodSugar) {
        this.bloodSugar = bloodSugar;
    }

    public String getSerumAlt() {
        return serumAlt;
    }

    public void setSerumAlt(String serumAlt) {
        this.serumAlt = serumAlt;
    }

    public String getSerumCz() {
        return serumCz;
    }

    public void setSerumCz(String serumCz) {
        this.serumCz = serumCz;
    }

    public String getAlbumin() {
        return albumin;
    }

    public void setAlbumin(String albumin) {
        this.albumin = albumin;
    }

    public String getTotalBilirubin() {
        return totalBilirubin;
    }

    public void setTotalBilirubin(String totalBilirubin) {
        this.totalBilirubin = totalBilirubin;
    }

    public String getDirectBilirubin() {
        return directBilirubin;
    }

    public void setDirectBilirubin(String directBilirubin) {
        this.directBilirubin = directBilirubin;
    }

    public String getSerumCreatinine() {
        return serumCreatinine;
    }

    public void setSerumCreatinine(String serumCreatinine) {
        this.serumCreatinine = serumCreatinine;
    }

    public String getBloodUrea() {
        return bloodUrea;
    }

    public void setBloodUrea(String bloodUrea) {
        this.bloodUrea = bloodUrea;
    }

    public String getVaginalDischarge() {
        return vaginalDischarge;
    }

    public void setVaginalDischarge(String vaginalDischarge) {
        this.vaginalDischarge = vaginalDischarge;
    }

    public String getVaginalDischargeOther() {
        return vaginalDischargeOther;
    }

    public void setVaginalDischargeOther(String vaginalDischargeOther) {
        this.vaginalDischargeOther = vaginalDischargeOther;
    }

    public String getVaginalCleanness() {
        return vaginalCleanness;
    }

    public void setVaginalCleanness(String vaginalCleanness) {
        this.vaginalCleanness = vaginalCleanness;
    }

    public String getHbsAg() {
        return hbsAg;
    }

    public void setHbsAg(String hbsAg) {
        this.hbsAg = hbsAg;
    }

    public String getHbsAb() {
        return hbsAb;
    }

    public void setHbsAb(String hbsAb) {
        this.hbsAb = hbsAb;
    }

    public String getHbeAg() {
        return hbeAg;
    }

    public void setHbeAg(String hbeAg) {
        this.hbeAg = hbeAg;
    }

    public String getHbeAb() {
        return hbeAb;
    }

    public void setHbeAb(String hbeAb) {
        this.hbeAb = hbeAb;
    }

    public String getHbcAb() {
        return hbcAb;
    }

    public void setHbcAb(String hbcAb) {
        this.hbcAb = hbcAb;
    }

    public String getSyphilisTestResult() {
        return syphilisTestResult;
    }

    public void setSyphilisTestResult(String syphilisTestResult) {
        this.syphilisTestResult = syphilisTestResult;
    }

    public String getHivResult() {
        return hivResult;
    }

    public void setHivResult(String hivResult) {
        this.hivResult = hivResult;
    }

    public String getUltrasoundToB() {
        return ultrasoundToB;
    }

    public void setUltrasoundToB(String ultrasoundToB) {
        this.ultrasoundToB = ultrasoundToB;
    }

    public String getFzCheckOther() {
        return fzCheckOther;
    }

    public void setFzCheckOther(String fzCheckOther) {
        this.fzCheckOther = fzCheckOther;
    }

    public String getTotalAssess() {
        return totalAssess;
    }

    public void setTotalAssess(String totalAssess) {
        this.totalAssess = totalAssess;
    }

    public String getTotalAssessOther() {
        return totalAssessOther;
    }

    public void setTotalAssessOther(String totalAssessOther) {
        this.totalAssessOther = totalAssessOther;
    }

    public String getHealthGuidelines() {
        return healthGuidelines;
    }

    public void setHealthGuidelines(String healthGuidelines) {
        this.healthGuidelines = healthGuidelines;
    }

    public String getHealthGuidelinesOther() {
        return healthGuidelinesOther;
    }

    public void setHealthGuidelinesOther(String healthGuidelinesOther) {
        this.healthGuidelinesOther = healthGuidelinesOther;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMaternalZrlc() {
        return maternalZrlc;
    }

    public void setMaternalZrlc(String maternalZrlc) {
        this.maternalZrlc = maternalZrlc;
    }

    public String getMaternalRglc() {
        return maternalRglc;
    }

    public void setMaternalRglc(String maternalRglc) {
        this.maternalRglc = maternalRglc;
    }

    public String getMaternalSt() {
        return maternalSt;
    }

    public void setMaternalSt(String maternalSt) {
        this.maternalSt = maternalSt;
    }

    public String getMatermalSc() {
        return matermalSc;
    }

    public void setMatermalSc(String matermalSc) {
        this.matermalSc = matermalSc;
    }

    public String getMaternalNewDie() {
        return maternalNewDie;
    }

    public void setMaternalNewDie(String maternalNewDie) {
        this.maternalNewDie = maternalNewDie;
    }

    public String getMaternalDefects() {
        return maternalDefects;
    }

    public void setMaternalDefects(String maternalDefects) {
        this.maternalDefects = maternalDefects;
    }

    public String getReferralDept() {
        return referralDept;
    }

    public void setReferralDept(String referralDept) {
        this.referralDept = referralDept;
    }
}
