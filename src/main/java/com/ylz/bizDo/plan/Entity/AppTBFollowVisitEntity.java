package com.ylz.bizDo.plan.Entity;

import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by zzl on 2019/4/12.
 */
public class AppTBFollowVisitEntity {
    private String id;
    private String visitId;//随访主表id
    private String name;//姓名
    private String code;//编号
    private String followVisitDate;//随时间
    private String monthNum;//治疗月序
    private String supervisor;//督导人员(1医生 2家属 3 自服药 4 其他)
    private String followVisitType;//随访方式 1门诊2家庭3电话
    private String symptoms;//症状及体征 0.没有症状1.咳嗽咳痰2.低热盗汗3.咯血或血痰4.胸痛消瘦5.恶心纳差6.关节疼痛7.头痛失眠8.视物模糊9.皮肤瘙痒、皮疹10.耳鸣、听力下降11.其他
    private String symptomsOther;//其他症状及体征
    private String smokingToNum;//吸烟量（支）
    private String smokingToDay;//吸烟量（日）
    private String drinkingToNum;//饮酒量（两）
    private String drinkingToDay;//饮酒量（日）
    private String hlMethods;//化疗方案
    private String userMethod;//用法1每日2 间歇□
    private String drugType;//药品剂型1 固定剂量复合制剂2 散装药3 板式组合药4 注射剂
    private String notEatPillsNum;//漏服药次数
    private String drugReaction;//药物不良反应 1无 2有
    private String drugReactionContent;//有药物不良反应内容
    private String complication;//并发症或合并症 1无 2有
    private String complicationContent;//有并发症或合并症内容
    private String referralDeptType;//转诊科别
    private String referralReason;//转诊原因
    private String referralResult;//2周内随访，随访结果
    private String opinion;//处理意见
    private String nextVisiTime;//下次随访日期
    private String visitDoctorName;//随访医生签名
    private String visitDoctorId;//随访医生主键
    private String stopDate;//停止治疗时间
    private String stopReason;//停止治疗原因 1完成疗程 2死亡 3丢失 4转入耐多药治疗
    private String shouldVisitNum;//应访视次数
    private String nowVisitNum;//实际访视次数
    private String shouldTakeNum;//应服药次数
    private String nowTakeNum;//实际服药次数
    private String takeRate;//服药率
    private String assessDoctorName;//评估医生签名
    private String assessDoctorId;//评估医生id
    private String patientId;//患者id
    private String isBasic;//是基卫数据(0否 1是)

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

    public void setSupervisor(String supervisor) throws Exception{
        if(StringUtils.isNotBlank(supervisor)){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JHBDDRY, supervisor);
            if(value!=null){
                supervisor = value.getCodeTitle();
            }
        }
        this.supervisor = supervisor;
    }

    public String getFollowVisitType() {
        return followVisitType;
    }

    public void setFollowVisitType(String followVisitType) throws Exception{
        if(StringUtils.isNotBlank(followVisitType)){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_FOLLOWWAY, followVisitType);
            if(value!=null){
                followVisitType = value.getCodeTitle();
            }
        }
        this.followVisitType = followVisitType;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) throws Exception{
        String str = "";
        if(StringUtils.isNotBlank(symptoms)){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            String[] ss = symptoms.split(";");
            for(String s:ss){
                CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JHBZZANDTZ, s);
                if(value!=null){
                    if(StringUtils.isBlank(str)){
                        str = value.getCodeTitle();
                    }else{
                        str += ";"+value.getCodeTitle();
                    }
                }
            }
        }
        this.symptoms = symptoms;
    }

    public String getSymptomsOther() {
        return symptomsOther;
    }

    public void setSymptomsOther(String symptomsOther) {
        this.symptomsOther = symptomsOther;
    }

    public String getSmokingToNum() {
        return smokingToNum;
    }

    public void setSmokingToNum(String smokingToNum) {
        this.smokingToNum = smokingToNum;
    }

    public String getSmokingToDay() {
        return smokingToDay;
    }

    public void setSmokingToDay(String smokingToDay) {
        this.smokingToDay = smokingToDay;
    }

    public String getDrinkingToNum() {
        return drinkingToNum;
    }

    public void setDrinkingToNum(String drinkingToNum) {
        this.drinkingToNum = drinkingToNum;
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

    public void setUserMethod(String userMethod) throws Exception{
        if(StringUtils.isNotBlank(userMethod)){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JHBYF, userMethod);
            if(value!=null){
                userMethod = value.getCodeTitle();
            }
        }
        this.userMethod = userMethod;
    }

    public String getDrugType() {
        return drugType;
    }

    public void setDrugType(String drugType) throws Exception{
        if(StringUtils.isNotBlank(drugType)){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JHBYPJX, drugType);
            if(value!=null){
                drugType = value.getCodeTitle();
            }
        }
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

    public void setDrugReaction(String drugReaction) throws Exception{
        if(StringUtils.isNotBlank(drugReaction)){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
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

    public String getComplication() {
        return complication;
    }

    public void setComplication(String complication) throws Exception {
        if(StringUtils.isNotBlank(complication)){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_SFYW, complication);
            if(value!=null){
                complication = value.getCodeTitle();
            }
        }
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

    public String getVisitDoctorId() {
        return visitDoctorId;
    }

    public void setVisitDoctorId(String visitDoctorId) {
        this.visitDoctorId = visitDoctorId;
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

    public void setStopReason(String stopReason) throws Exception {
        if(StringUtils.isNotBlank(stopReason)){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JHBTZZLYY, stopReason);
            if(value!=null){
                stopReason = value.getCodeTitle();
            }
        }
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
