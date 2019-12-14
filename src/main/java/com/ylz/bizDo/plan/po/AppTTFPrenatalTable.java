package com.ylz.bizDo.plan.po;


import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 2-5次产前随访服务记录表
 * Created by admin on 2017-05-13.
 */
@Entity
@Table(name = "APP_TTF_PRENATAL_TABLE")
public class AppTTFPrenatalTable extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;
    @Column(name = "VISIT_ID", length = 36)
    private String visitId;//随访主表id
    @Column(name = "NAME", length = 20)
    private String name;//姓名
    @Column(name = "CODE",length = 100)
    private String code;//编号
    @Column(name = "XM", length = 10)
    private String xm;//项目
    @Column(name = "FOLLOW_VISIT_DATE", length = 20)
    private String followVisitDate;//（随访/督促）日期
    @Column(name = "WEEK_OF_BIRTH", length = 20)
    private String weekOfBirth;//孕周
    @Column(name = "MAIN_SUIT", length = 1000)
    private String mainSuit;//主诉
    @Column(name = "WEIGHT", length = 20)
    private String weigth;//体重
    @Column(name = "FUNDUS_HEIGHT", length = 20)
    private String fundusHeight;//宫底高度
    @Column(name = "ABDOMINAL_GIRTH", length = 20)
    private String abdominalGirth;//腹围
    @Column(name = "POSITION", length = 20)
    private String position;//胎位
    @Column(name = "HEART_RATE", length = 20)
    private String heartRate;//胎心率(次/分钟)
    @Column(name = "BLOOD_PRESSURE_ONE", length = 20)
    private String bloodPressureOne;//舒张压（mmHg）
    @Column(name = "HEMOGLOBIN", length = 20)
    private String hemoglobin;//血红蛋白
    @Column(name = "URINE_PROTEIN", length = 20)
    private String urineProtein;//尿蛋白
    @Column(name = "FZ_CHECK_OTHER", length = 100)
    private String fzCheckOther;//其他辅助检查
    @Column(name = "CLASSIFY", length = 10)
    private String classify;//分类
    @Column(name = "CLASSIFY_OTHER", length = 50)
    private String classifyOther;//分类异常内容
    @Column(name = "GUIDE", length = 10)
    private String guide;//指导
    @Column(name = "GUIDE_OTHER", length = 30)
    private String guideOther;//其他指导
    @Column(name = "REFERRAL", length = 10)
    private String referral;//转诊情况（0无 1有）
    @Column(name = "REFERRAL_REASON", length = 30)
    private String referralReason;//转诊建议原因
    @Column(name = "REFERRAL_ORG", length = 30)
    private String referralOrg;//转诊机构
    @Column(name = "NEXT_VISIT_TIME", length = 36)
    private String nextVisiTime;//下次随访日期
    @Column(name = "VISIT_DOCTOR_NAME", length = 30)
    private String visitDoctorName;//随访医生签名
    @Column(name = "BLOOD_PRESSURE_TWO", length = 20)
    private String bloodPressureTwo;//舒张压（mmHg）
    @Column(name = "REFERRAL_DEPT",length = 50)
    private String referralDept;//转诊科室

    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getFollowVisitDate() {
        return followVisitDate;
    }

    public void setFollowVisitDate(String followVisitDate) {
        this.followVisitDate = followVisitDate;
    }

    public String getWeekOfBirth() {
        return weekOfBirth;
    }

    public void setWeekOfBirth(String weekOfBirth) {
        this.weekOfBirth = weekOfBirth;
    }

    public String getMainSuit() {
        return mainSuit;
    }

    public void setMainSuit(String mainSuit) {
        this.mainSuit = mainSuit;
    }

    public String getWeigth() {
        return weigth;
    }

    public void setWeigth(String weigth) {
        this.weigth = weigth;
    }

    public String getFundusHeight() {
        return fundusHeight;
    }

    public void setFundusHeight(String fundusHeight) {
        this.fundusHeight = fundusHeight;
    }

    public String getAbdominalGirth() {
        return abdominalGirth;
    }

    public void setAbdominalGirth(String abdominalGirth) {
        this.abdominalGirth = abdominalGirth;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }

    public String getBloodPressureOne() {
        return bloodPressureOne;
    }

    public void setBloodPressureOne(String bloodPressureOne) {
        this.bloodPressureOne = bloodPressureOne;
    }

    public String getHemoglobin() {
        return hemoglobin;
    }

    public void setHemoglobin(String hemoglobin) {
        this.hemoglobin = hemoglobin;
    }

    public String getUrineProtein() {
        return urineProtein;
    }

    public void setUrineProtein(String urineProtein) {
        this.urineProtein = urineProtein;
    }

    public String getFzCheckOther() {
        return fzCheckOther;
    }

    public void setFzCheckOther(String fzCheckOther) {
        this.fzCheckOther = fzCheckOther;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getClassifyOther() {
        return classifyOther;
    }

    public void setClassifyOther(String classifyOther) {
        this.classifyOther = classifyOther;
    }

    public String getGuide() {
        return guide;
    }

    public void setGuide(String guide) {
        this.guide = guide;
    }

    public String getGuideOther() {
        return guideOther;
    }

    public void setGuideOther(String guideOther) {
        this.guideOther = guideOther;
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

    public String getReferralDept() {
        return referralDept;
    }

    public void setReferralDept(String referralDept) {
        this.referralDept = referralDept;
    }
}
