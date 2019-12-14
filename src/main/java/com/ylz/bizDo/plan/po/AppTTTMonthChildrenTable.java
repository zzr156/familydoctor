package com.ylz.bizDo.plan.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 12-30月龄儿童健康检查记录表
 * Created by admin on 2017-05-13.
 */
@Entity
@Table(name = "APP_TTT_CHILDREN_TABLE")
public class AppTTTMonthChildrenTable extends BasePO {
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
    @Column(name = "MONTH", length = 20)
    private String month;//月龄
    @Column(name = "FOLLOW_VISIT_TIME", length = 20)
    private String followVisitTime;//随访日期
    @Column(name = "WEIGHT", length = 10)
    private String weight;//体重
    @Column(name = "WEIGHT_STANDARD", length = 10)
    private String weightStandard;//体重标准
    @Column(name = "HEIGHT", length = 10)
    private String height;//身高
    @Column(name = "HEIGHT_STANDARD", length = 10)
    private String heightStandard;//身高标准
    @Column(name = "FACE", length = 10)
    private String face;//面色
    @Column(name = "SKIN", length = 10)
    private String skin;//皮肤
    @Column(name = "BREGMA", length = 10)
    private String bregma;//前囟
    @Column(name = "BREGMA_NUM1", length = 10)
    private String bregmaNum1;//前囟数据1
    @Column(name = "BREGMA_NUM2", length = 10)
    private String bregmaNum2;//前囟数据2
    @Column(name = "EYES", length = 10)
    private String eyes;//眼睛
    @Column(name = "EAR_APPEARANCE", length = 10)
    private String earAppearance;//耳外观
    @Column(name = "CHILDREN_EAR", length = 10)
    private String childrenEar;//听力
    @Column(name = "TOOTH_NUM", length = 10)
    private String toothNum;//出牙/龋齿数
    @Column(name = "CHEST", length = 10)
    private String chest;//胸部
    @Column(name = "ABDOMINAL_TOUCH", length = 10)
    private String abdominalTouch;//腹部
    @Column(name = "LIMBS", length = 10)
    private String limbs;//四肢
    @Column(name = "GAIT", length = 10)
    private String gait;//步态
    @Column(name = "RICKETS_SIGNS", length = 10)
    private String ricketsSigns;//可疑佝偻病体征
    @Column(name = "HEMOGLOBIN", length = 10)
    private String hemoglobin;//血红蛋白值
    @Column(name = "OUTDOOR_ACTIVITY", length = 10)
    private String outdoorActivity;//户外活动
    @Column(name = "VD", length = 10)
    private String vd;//维生素D
    @Column(name = "GROWTH_PG", length = 100)
    private String growthPg;//发育评估
    @Column(name = "TWO_VISIT_SITUATION", length = 10)
    private String twoVisitSituation;// 两次随访间患病情况
    @Column(name = "PNEUMONIA_NUM", length = 20)
    private String pneumoniaNum;//肺炎次数
    @Column(name = "DIARRHEA_NUM", length = 20)
    private String diarrheaNum;//腹泻次数
    @Column(name = "TRAUMA_NUM", length = 20)
    private String traumaNum;//外伤次数
    @Column(name = "OTHER_TWO_VISIT", length = 50)
    private String otherTwoVisit;//其他两次随访患病情况内容
    @Column(name = "REFERRAL", length = 50)
    private String referral;//转诊情况（0无 1有）
    @Column(name = "REFERRAL_REASON", length = 50)
    private String referralReason;//转诊建议原因
    @Column(name = "REFERRAL_ORG", length = 50)
    private String referralOrg;//转诊机构
    @Column(name = "GUIDE", length = 10)
    private String guide;//指导
    @Column(name = "GUIDE_OTHER", length = 50)
    private String guideOther;//指导其他选项内容
    @Column(name = "NEXT_VISIT_TIME", length = 20)
    private String nextVisiTime;//下次随访日期
    @Column(name = "VISIT_DOCTOR_NAME", length = 50)
    private String visitDoctorName;//随访医生签名
    @Column(name = "REFERRAL_DEPT",length = 50)
    private String referralDept;//转诊科室


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

    public String getPneumoniaNum() {
        return pneumoniaNum;
    }

    public void setPneumoniaNum(String pneumoniaNum) {
        this.pneumoniaNum = pneumoniaNum;
    }

    public String getDiarrheaNum() {
        return diarrheaNum;
    }

    public void setDiarrheaNum(String diarrheaNum) {
        this.diarrheaNum = diarrheaNum;
    }

    public String getTraumaNum() {
        return traumaNum;
    }

    public void setTraumaNum(String traumaNum) {
        this.traumaNum = traumaNum;
    }

    public String getOtherTwoVisit() {
        return otherTwoVisit;
    }

    public void setOtherTwoVisit(String otherTwoVisit) {
        this.otherTwoVisit = otherTwoVisit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getFollowVisitTime() {
        return followVisitTime;
    }

    public void setFollowVisitTime(String followVisitTime) {
        this.followVisitTime = followVisitTime;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getWeightStandard() {
        return weightStandard;
    }

    public void setWeightStandard(String weightStandard) {
        this.weightStandard = weightStandard;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getHeightStandard() {
        return heightStandard;
    }

    public void setHeightStandard(String heightStandard) {
        this.heightStandard = heightStandard;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getSkin() {
        return skin;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }

    public String getBregma() {
        return bregma;
    }

    public void setBregma(String bregma) {
        this.bregma = bregma;
    }

    public String getBregmaNum1() {
        return bregmaNum1;
    }

    public void setBregmaNum1(String bregmaNum1) {
        this.bregmaNum1 = bregmaNum1;
    }

    public String getBregmaNum2() {
        return bregmaNum2;
    }

    public void setBregmaNum2(String bregmaNum2) {
        this.bregmaNum2 = bregmaNum2;
    }

    public String getEyes() {
        return eyes;
    }

    public void setEyes(String eyes) {
        this.eyes = eyes;
    }

    public String getEarAppearance() {
        return earAppearance;
    }

    public void setEarAppearance(String earAppearance) {
        this.earAppearance = earAppearance;
    }

    public String getChildrenEar() {
        return childrenEar;
    }

    public void setChildrenEar(String childrenEar) {
        this.childrenEar = childrenEar;
    }

    public String getToothNum() {
        return toothNum;
    }

    public void setToothNum(String toothNum) {
        this.toothNum = toothNum;
    }

    public String getChest() {
        return chest;
    }

    public void setChest(String chest) {
        this.chest = chest;
    }

    public String getAbdominalTouch() {
        return abdominalTouch;
    }

    public void setAbdominalTouch(String abdominalTouch) {
        this.abdominalTouch = abdominalTouch;
    }

    public String getLimbs() {
        return limbs;
    }

    public void setLimbs(String limbs) {
        this.limbs = limbs;
    }

    public String getGait() {
        return gait;
    }

    public void setGait(String gait) {
        this.gait = gait;
    }

    public String getRicketsSigns() {
        return ricketsSigns;
    }

    public void setRicketsSigns(String ricketsSigns) {
        this.ricketsSigns = ricketsSigns;
    }

    public String getHemoglobin() {
        return hemoglobin;
    }

    public void setHemoglobin(String hemoglobin) {
        this.hemoglobin = hemoglobin;
    }

    public String getOutdoorActivity() {
        return outdoorActivity;
    }

    public void setOutdoorActivity(String outdoorActivity) {
        this.outdoorActivity = outdoorActivity;
    }

    public String getVd() {
        return vd;
    }

    public void setVd(String vd) {
        this.vd = vd;
    }

    public String getGrowthPg() {
        return growthPg;
    }

    public void setGrowthPg(String growthPg) {
        this.growthPg = growthPg;
    }

    public String getTwoVisitSituation() {
        return twoVisitSituation;
    }

    public void setTwoVisitSituation(String twoVisitSituation) {
        this.twoVisitSituation = twoVisitSituation;
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
