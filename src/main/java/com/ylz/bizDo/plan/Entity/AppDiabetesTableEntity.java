package com.ylz.bizDo.plan.Entity;

import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.plan.po.AppFollowPlan;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 2型糖尿病患者随访服务记录表
 * Created by admin on 2017-05-12.
 */
public class AppDiabetesTableEntity implements java.io.Serializable {
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
    private String visitDoctorName;//随访医生签名
    private String bloodPressureTwo;//舒张压（mmHg）
    private String nextWeight;//下次目标体重
    private String height;//身高
    private String smokingNextToDay;//下次目标日吸烟量（支）
    private String drinkingNextToDay;//下次目标日饮酒量（两）
    private String activityNextToWeek;//下次目标运动（次/周）
    private String activityNextToTime;//下次目标运动（分钟/次）
    private String nextFood;//下次目标主食（克/天）
    private String dorsalisPedisValue;//足背动脉搏动(双侧，左侧，右侧)
    private String referralDept;//转诊科室
    private String referral;//转诊
    private String visitSituation;//随访情况(正常 失访 死亡)
    private String visitReason;//失访原因或死亡原因
    private String dieDate;//死亡日期
    private List<AppMedicationTableEntity> userMedicine;//用药情况
    private String patientId;//患者id
    private String sfrq;
    private String csrq;
    private String sex;
    private String tel;
    private String addr;

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

    public void setFollowVisitWay(String followVisitWay) throws Exception {
        String str = "";
        if(StringUtils.isNotBlank(followVisitWay)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_FOLLOWWAY, followVisitWay);
            if(value!=null){
                str = value.getCodeTitle();
            }
        }
        this.followVisitWay = str;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) throws Exception {
        String str = "";
        if(StringUtils.isNotBlank(symptoms)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            String[] syms = symptoms.split(";");
            for(String sym:syms){
                CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_XTSYMPTOM, sym);
                if(value!=null){
                    if(StringUtils.isBlank(str)){
                        str = value.getCodeTitle();
                    }else{
                        str += ";"+value.getCodeTitle();
                    }
                }

            }
        }
        this.symptoms = str;
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

    public void setDorsalisPedisPulse(String dorsalisPedisPulse) throws Exception {
        String str = "";
        if(StringUtils.isNotBlank(dorsalisPedisPulse)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_ZBDMBD, dorsalisPedisPulse);
            if(value!=null){
                str = value.getCodeTitle();
            }
        }

        this.dorsalisPedisPulse = str;
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

    public void setMentalityAdjust(String mentalityAdjust) throws Exception {
        String str = "";
        if(StringUtils.isNotBlank(mentalityAdjust)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_LHC, mentalityAdjust);
            if(value!=null){
                str = value.getCodeTitle();
            }
        }
        this.mentalityAdjust = str;
    }

    public String getFollowingBehavior() {
        return followingBehavior;
    }

    public void setFollowingBehavior(String followingBehavior) throws Exception {
        String str = "";
        if(StringUtils.isNotBlank(followingBehavior)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_LHC, followingBehavior);
            if(value!=null){
                str = value.getCodeTitle();
            }
        }

        this.followingBehavior = str;
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

    public void setMedicationAdherence(String medicationAdherence) throws Exception {
        String str = "";
        if(StringUtils.isNotBlank(medicationAdherence)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_FYYCX, medicationAdherence);
            if(value!=null){
                str = value.getCodeTitle();
            }
        }
        this.medicationAdherence = str;
    }

    public String getDrugReaction() {
        return drugReaction;
    }

    public void setDrugReaction(String drugReaction) throws Exception {
        String str = "";
        if(StringUtils.isNotBlank(drugReaction)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_DRUGREACTION, drugReaction);
            if(value!=null){
                str = value.getCodeTitle();
            }
        }

        this.drugReaction = str;
    }

    public String getLowBloodGlucose() {
        return lowBloodGlucose;
    }

    public void setLowBloodGlucose(String lowBloodGlucose) throws Exception {
        String str = "";
        if(StringUtils.isNotBlank(lowBloodGlucose)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_DXTYY, lowBloodGlucose);
            if(value!=null){
                str = value.getCodeTitle();
            }
        }
        this.lowBloodGlucose = str;
    }

    public String getVisitThisType() {
        return visitThisType;
    }

    public void setVisitThisType(String visitThisType) throws Exception {
        String str = "";
        if(StringUtils.isNotBlank(visitThisType)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CCFFFL, visitThisType);
            if(value!=null){
                str = value.getCodeTitle();
            }
        }
        this.visitThisType = str;
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

    public String getVisitDoctorName() {
        return visitDoctorName;
    }

    public void setVisitDoctorName(String visitDoctorName) {
        String str = "";
        if(StringUtils.isNotBlank(visitDoctorName)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            AppDrUser user = (AppDrUser)dao.getServiceDo().find(AppDrUser.class,visitDoctorName);
            if(user!=null){
                str = user.getDrName();
            }
        }
        this.visitDoctorName = str;
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

    public void setDorsalisPedisValue(String dorsalisPedisValue) throws Exception {
        String str = "";
        if(StringUtils.isNotBlank(dorsalisPedisValue)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_ZBDMBDT, dorsalisPedisValue);
            if(value!=null){
                str = value.getCodeTitle();
            }

        }
        this.dorsalisPedisValue = str;
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

    public void setReferral(String referral) throws Exception {
        String str = "";
        if(StringUtils.isNotBlank(referral)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_SFCOMMON, referral);
            if(value!=null){
                str = value.getCodeTitle();
            }
        }
        this.referral = str;
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

    public List<AppMedicationTableEntity> getUserMedicine() {
        return userMedicine;
    }

    public void setUserMedicine(String userMedicine) {
        List<AppMedicationTableEntity> ls = new ArrayList<AppMedicationTableEntity>();
        if(StringUtils.isNotBlank(this.getId())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("visitId",this.getId());
            String sql = "SELECT " +
                    "ID id,\n" +
                    "MEDICINE_NAME medicineName,\n" +
                    "USE_TO_DAY userToDay,\n" +
                    "USE_TO_TIME userToTime \n" +
                    "FROM APP_MEDICATION_TABLE WHERE VISIT_ID=:visitId";
            ls = dao.getServiceDo().findSqlMapRVo(sql,map,AppMedicationTableEntity.class);
        }
        this.userMedicine = ls;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getSfrq() {
        return sfrq;
    }

    public void setSfrq(String sfrq) {
        if(StringUtils.isNotBlank(this.getVisitId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            AppFollowPlan plan = (AppFollowPlan) dao.getServiceDo().find(AppFollowPlan.class,this.getVisitId());
            if(plan!=null){
                sfrq = ExtendDate.getYMD(plan.getSfEndDate());
            }
        }
        this.sfrq = sfrq;
    }

    public String getCsrq() {
        return csrq;
    }

    public void setCsrq(String csrq) {
        if(StringUtils.isNotBlank(this.getPatientId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            AppPatientUser user = (AppPatientUser)dao.getServiceDo().find(AppPatientUser.class,this.getPatientId());
            if(user!=null){
                csrq = ExtendDate.getYMD(user.getPatientBirthday());
            }
        }
        this.csrq = csrq;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        if(StringUtils.isNotBlank(this.getPatientId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            AppPatientUser user = (AppPatientUser)dao.getServiceDo().find(AppPatientUser.class,this.getPatientId());
            if(user!=null){
                sex = user.getPatientGender();
            }
        }
        this.sex = sex;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        if(StringUtils.isNotBlank(this.getPatientId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            AppPatientUser user = (AppPatientUser)dao.getServiceDo().find(AppPatientUser.class,this.getPatientId());
            if(user!=null){
                tel = user.getPatientTel();
            }
        }
        this.tel = tel;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        if(StringUtils.isNotBlank(this.getPatientId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            AppPatientUser user = (AppPatientUser)dao.getServiceDo().find(AppPatientUser.class,this.getPatientId());
            if(user!=null){
              addr = user.getPatientIdno();
            }
        }
        this.addr = addr;
    }
}
