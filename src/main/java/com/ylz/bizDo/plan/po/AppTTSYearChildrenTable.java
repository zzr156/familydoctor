package com.ylz.bizDo.plan.po;


import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 3-6岁儿童健康检查记录表
 * Created by admin on 2017-05-13.
 */
@Entity
@Table(name = "APP_TTS_CHILDREN_TABLE")
public class AppTTSYearChildrenTable extends BasePO {
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
    @Column(name = "MONTH", length = 10)
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
    @Column(name = "WTH_RATIO", length = 10)
    private String wTHRatio;//体重身高比
    @Column(name = "WTH_RATIO_STANDARD", length = 10)
    private String wTHRatioStandard;//体重身高比标准
    @Column(name = "PHYSICAL_GROWTH", length = 10)
    private String physicalGrowth;//体格发育评价
    @Column(name = "VISION", length = 10)
    private String vision;//视力
    @Column(name = "HEARING", length = 10)
    private String hearing;//听力
    @Column(name = "TOOTH_NUM", length = 10)
    private String toothNum;//牙数/龋齿数
    @Column(name = "CHEST", length = 10)
    private String chest;//胸部
    @Column(name = "ABDOMINAL_TOUCH", length = 10)
    private String abdominalTouch;//腹部
    @Column(name = "HEMOGLOBIN", length = 10)
    private String hemoglobin;//血红蛋白值
    @Column(name = "OTHER_PHYSICAL", length = 50)
    private String otherPhysical;//其他体格检查
    @Column(name = "GROWTH_PG", length = 20)
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
    @Column(name = "GUIDE", length = 20)
    private String guide;//指导
    @Column(name = "GUIDE_OTHER", length = 50)
    private String guideOther;//指导其他选项内容
    @Column(name = "NEXT_VISIT_TIME", length = 20)
    private String nextVisiTime;//下次随访日期
    @Column(name = "VISIT_DOCTOR_NAME", length = 20)
    private String visitDoctorName;//随访医生签名
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

    public String getwTHRatio() {
        return wTHRatio;
    }

    public void setwTHRatio(String wTHRatio) {
        this.wTHRatio = wTHRatio;
    }

    public String getwTHRatioStandard() {
        return wTHRatioStandard;
    }

    public void setwTHRatioStandard(String wTHRatioStandard) {
        this.wTHRatioStandard = wTHRatioStandard;
    }

    public String getPhysicalGrowth() {
        return physicalGrowth;
    }

    public void setPhysicalGrowth(String physicalGrowth) {
        this.physicalGrowth = physicalGrowth;
    }

    public String getVision() {
        return vision;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }

    public String getHearing() {
        return hearing;
    }

    public void setHearing(String hearing) {
        this.hearing = hearing;
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

    public String getHemoglobin() {
        return hemoglobin;
    }

    public void setHemoglobin(String hemoglobin) {
        this.hemoglobin = hemoglobin;
    }

    public String getOtherPhysical() {
        return otherPhysical;
    }

    public void setOtherPhysical(String otherPhysical) {
        this.otherPhysical = otherPhysical;
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
