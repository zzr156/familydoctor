package com.ylz.bizDo.plan.Entity;

import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by zzl on 2019/4/4.
 */
public class AppMentalVisitEntity {
    private String id;
    private String visitId;//随访外键
    private String name;//姓名
    private String code;//编号
    private String followVisitTime;//随访日期
    private String phoneTel;//联系电话
    private String phoneName;//联系人姓名
    private String relationshipToVisit;//与被访者关系
    private String followVisitWay;//随访方式
    private String loatVisit;//失访原因（1外出打工 2迁居他处 3走失  4连续3次未访到  5其他）
    private String deathDate;//死亡日期
    private String deathReason;//死亡原因 1躯体疾病①染病和寄生虫病 ②肿瘤 ③心脏病  ④脑血管病 ⑤呼吸系统疾病  ⑥消化系统疾病  ⑦其他疾病  ⑨不详  2自杀   3他杀   4意外   5精神疾病相关并发症    6其他
    private String deathDiseaseType;//死于什么躯体疾病①染病和寄生虫病 ②肿瘤 ③心脏病  ④脑血管病 ⑤呼吸系统疾病  ⑥消化系统疾病  ⑦其他疾病  ⑨不详
    private String dangerous;//危险性评估0 （0级） 1（1级） 2(2级)  3(3级)  4(4级)  5（5级）
    private String nowSymptoms;//目前症状（多选） 1幻觉 2交流困难 3猜疑 4喜怒无常  5行为怪异  6兴奋话多 7伤人毁物8悲观厌世  9无故外走 10自语自笑  11孤僻懒散 12其他
    private String nowSymptomsOther;//其他症状内容
    private String selfKnowledge;//自知力 1自知力完全    2自知力不全    3自知力缺失
    private String sleep;//睡眠情况 1良好    2一般    3较差
    private String diet;//饮食情况 1良好    2一般    3较差
    private String personalLife;//个人生活料理 1良好    2一般    3较差
    private String housework;//家务劳动 1良好    2一般    3较差
    private String work;//生产劳动及工作 1良好    2一般    3较差  9此项不适用
    private String learnAbility;//学习能力 1良好    2一般    3较差
    private String interpersonal;//社会人际交往 1良好    2一般    3较差
    private String dangerousBehavior;//危险行为 1轻度滋事 2肇事 3肇祸 4其他危害行为 5自伤 6自杀未遂 7无
    private String troubleNum;//轻度滋事次数
    private String causeTroubleNum;//肇事次数
    private String causeTroubleNum1;//肇祸次数
    private String otherDangerNum;//其他危险行为次数
    private String autolesionNum;//自伤次数
    private String suicideNum;//自杀未遂次数
    private String followVisitShut;//两次随访期间关锁情况 1期间无关锁   2关锁  3期间关锁已解除
    private String followVisitZy;//两次随访期间住院情况 0期间未住院  1目前正在住院  2期间曾住院，现未住院
    private String lastLeaveHospitalDate;//末次出院时间
    private String laboratoryCheck;//实验室检查 1无    2有
    private String laboratoryCheckContent;//有实验室检查内容
    private String medicationAdherence;//用药依从性 1按医嘱规律服药  2间断服药  3不服药  4医嘱勿需服药
    private String drugReaction;//药物不良反应 1无    2有
    private String drugReactionContent;//有药物不良反应内容
    private String treatmentResult;//治疗效果 1痊愈  2 好转   3 无变化   4 加重
    private String referral;//是否转诊 1否  2是
    private String referralReason;//转诊原因
    private String referralOrg;//转诊机构id
    private String referralDept;//转诊科室
    private String rehabilitationMeasures;//康复措施(多选) 1生活劳动能力2职业训练3学习能力4社会交往5其他
    private String rehabilitationMeasuresOther;//其他康复措施
    private String followVisitType;//本次随访分类 1不稳定  2基本稳定  3稳定
    private String nextVisiTime;//下次随访日期
    private String visitDoctorName;//随访医生签名
    private String visitDoctorId;//随访医生id
    private String responsibleDoctorId;//责任医生id
    private String responsibleDoctorName;//责任医生签名
    private String inputDoctorId;//录入人员id
    private String inputDoctorName;//录入人员签名
    private String patientId;//患者id

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

    public String getPhoneTel() {
        return phoneTel;
    }

    public void setPhoneTel(String phoneTel) {
        this.phoneTel = phoneTel;
    }

    public String getPhoneName() {
        return phoneName;
    }

    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }

    public String getRelationshipToVisit() {
        return relationshipToVisit;
    }

    public void setRelationshipToVisit(String relationshipToVisit) {
        this.relationshipToVisit = relationshipToVisit;
    }

    public String getFollowVisitWay() {
        return followVisitWay;
    }

    public void setFollowVisitWay(String followVisitWay) {
        this.followVisitWay = followVisitWay;
    }

    public String getLoatVisit() {
        return loatVisit;
    }

    public void setLoatVisit(String loatVisit) throws Exception{
        if(StringUtils.isNotBlank(loatVisit)){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_SFYYJSB, loatVisit);
            if(value!=null){
                loatVisit = value.getCodeTitle();
            }
        }
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

    public void setDeathReason(String deathReason) throws Exception {
        if(StringUtils.isNotBlank(deathReason)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_SYYYSJB, deathReason);
            if(value!=null){
                deathReason = value.getCodeTitle();
            }
        }
        this.deathReason = deathReason;
    }

    public String getDeathDiseaseType() {
        return deathDiseaseType;
    }

    public void setDeathDiseaseType(String deathDiseaseType) throws Exception{
        if(StringUtils.isNotBlank(deathDiseaseType)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_PHYSICALDIS, deathDiseaseType);
            if(value!=null){
                deathDiseaseType = value.getCodeTitle();
            }
        }
        this.deathDiseaseType = deathDiseaseType;
    }

    public String getDangerous() {
        return dangerous;
    }

    public void setDangerous(String dangerous) throws Exception{
        if(StringUtils.isNotBlank(dangerous)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_WXXPGJSB, dangerous);
            if(value!=null){
                dangerous = value.getCodeTitle();
            }
        }
        this.dangerous = dangerous;
    }

    public String getNowSymptoms() {
        return nowSymptoms;
    }

    public void setNowSymptoms(String nowSymptoms) throws Exception {
        String str = "";
        if(StringUtils.isNotBlank(nowSymptoms)){
            String[] nowSymptomss = nowSymptoms.split(";");
            for(String sy:nowSymptomss){
                SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
                CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_MQZZJSB, sy);
                if(value!=null){
                    if(StringUtils.isBlank(str)){
                        str = value.getCodeTitle();
                    }else{
                        str += ";"+value.getCodeTitle();
                    }
                }
            }
        }
        this.nowSymptoms = str;
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

    public void setSelfKnowledge(String selfKnowledge) throws Exception{
        if(StringUtils.isNotBlank(selfKnowledge)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_ZZLJSB, selfKnowledge);
            if(value!=null){
                selfKnowledge = value.getCodeTitle();
            }
        }
        this.selfKnowledge = selfKnowledge;
    }

    public String getSleep() {
        return sleep;
    }

    public void setSleep(String sleep) throws Exception{
        if(StringUtils.isNotBlank(sleep)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JSBNYC, sleep);
            if(value!=null){
                sleep = value.getCodeTitle();
            }
        }
        this.sleep = sleep;
    }

    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) throws Exception{
        if(StringUtils.isNotBlank(diet)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JSBNYC, diet);
            if(value!=null){
                diet = value.getCodeTitle();
            }
        }
        this.diet = diet;
    }

    public String getPersonalLife() {
        return personalLife;
    }

    public void setPersonalLife(String personalLife) throws Exception{
        if(StringUtils.isNotBlank(personalLife)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JSBNYC, personalLife);
            if(value!=null){
                personalLife = value.getCodeTitle();
            }
        }
        this.personalLife = personalLife;
    }

    public String getHousework() {
        return housework;
    }

    public void setHousework(String housework) throws Exception{
        if(StringUtils.isNotBlank(housework)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JSBNYC, housework);
            if(value!=null){
                housework = value.getCodeTitle();
            }
        }
        this.housework = housework;
    }

    public String getWork() {
        return work;
    }

    public void setWork(String work) throws Exception{
        if(StringUtils.isNotBlank(work)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JSBNYC, work);
            if(value!=null){
                work = value.getCodeTitle();
            }
        }
        this.work = work;
    }

    public String getLearnAbility() {
        return learnAbility;
    }

    public void setLearnAbility(String learnAbility) throws Exception{
        if(StringUtils.isNotBlank(learnAbility)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JSBNYC, learnAbility);
            if(value!=null){
                learnAbility = value.getCodeTitle();
            }
        }
        this.learnAbility = learnAbility;
    }

    public String getInterpersonal() {
        return interpersonal;
    }

    public void setInterpersonal(String interpersonal) throws Exception{
        if(StringUtils.isNotBlank(interpersonal)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JSBNYC, interpersonal);
            if(value!=null){
                interpersonal = value.getCodeTitle();
            }
        }
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

    public void setFollowVisitShut(String followVisitShut) throws Exception {
        if(StringUtils.isNotBlank(followVisitShut)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_GSQKJSB, followVisitShut);
            if(value!=null){
                followVisitShut = value.getCodeTitle();
            }
        }
        this.followVisitShut = followVisitShut;
    }

    public String getFollowVisitZy() {
        return followVisitZy;
    }

    public void setFollowVisitZy(String followVisitZy) throws Exception{
        if(StringUtils.isNotBlank(followVisitZy)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_ZYQKJSB, followVisitZy);
            if(value!=null){
                followVisitZy = value.getCodeTitle();
            }
        }
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

    public void setLaboratoryCheck(String laboratoryCheck) throws Exception{
        if(StringUtils.isNotBlank(laboratoryCheck)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_SFYW, laboratoryCheck);
            if(value!=null){
                laboratoryCheck = value.getCodeTitle();
            }
        }
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

    public void setDrugReaction(String drugReaction) throws Exception{
        if(StringUtils.isNotBlank(drugReaction)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_SFYW, drugReaction);
            if(value!=null){
                drugReaction = value.getCodeTitle();
            }
        }
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

    public void setTreatmentResult(String treatmentResult) throws Exception{
        if(StringUtils.isNotBlank(treatmentResult)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_ZLXGJSB, treatmentResult);
            if(value!=null){
                treatmentResult = value.getCodeTitle();
            }
        }
        this.treatmentResult = treatmentResult;
    }

    public String getReferral() {
        return referral;
    }

    public void setReferral(String referral) throws Exception{
        if(StringUtils.isNotBlank(referral)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_ISORNOT, referral);
            if(value!=null){
                referral = value.getCodeTitle();
            }
        }
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

    public String getReferralDept() {
        return referralDept;
    }

    public void setReferralDept(String referralDept) {
        this.referralDept = referralDept;
    }

    public String getRehabilitationMeasures() {
        return rehabilitationMeasures;
    }

    public void setRehabilitationMeasures(String rehabilitationMeasures) throws Exception{
        String str = "";
        if(StringUtils.isNotBlank(rehabilitationMeasures)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            String[] rehabilitationMeasuress = rehabilitationMeasures.split(";");
            for(String sy:rehabilitationMeasuress){
                CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_KFCSJSB, sy);
                if(value!=null){
                    if (StringUtils.isBlank(str)) {
                        str = value.getCodeTitle();
                    }else{
                        str += ";"+value.getCodeTitle();
                    }
                }
            }
        }
        this.rehabilitationMeasures = str;
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

    public void setFollowVisitType(String followVisitType) throws Exception{
        if(StringUtils.isNotBlank(followVisitType)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_THISVISITTYPE, followVisitType);
            if(value!=null){
                followVisitType = value.getCodeTitle();
            }
        }
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

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
}
