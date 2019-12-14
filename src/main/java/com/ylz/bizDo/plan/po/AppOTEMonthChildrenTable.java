package com.ylz.bizDo.plan.po;


import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 1-8月龄儿童健康检查记录表
 * Created by admin on 2017-05-13.
 */
@Entity
@Table(name = "APP_OTEM_CHILDREN_TABLE")
public class AppOTEMonthChildrenTable extends BasePO {
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
    private String code;//编码
    @Column(name = "MONTH", length = 36)
    private String month;//月龄
    @Column(name = "FOLLOW_VISIT_TIME", length = 36)
    private String followVisitTime;//随访日期
    @Column(name = "WEIGHT", length = 36)
    private String weight;//体重
    @Column(name = "WEIGHT_STANDARD", length = 36)
    private String weightStandard;//体重标准
    @Column(name = "HEIGHT", length = 36)
    private String height;//身高
    @Column(name = "HEIGHT_STANDARD", length = 36)
    private String heightStandard;//身高标准
    @Column(name = "HEAD_CIRCUMFERENCE", length = 36)
    private String headCircumference;//头围
    @Column(name = "FACE", length = 10)
    private String face;//面色
    @Column(name = "SKIN", length = 10)
    private String skin;//皮肤
    @Column(name = "BREGMA", length = 10)
    private String bregma;//前囟
    @Column(name = "BREGMA_NUM1", length = 36)
    private String bregmaNum1;//前囟数据1
    @Column(name = "BREGMA_NUM2", length = 36)
    private String bregmaNum2;//前囟数据2
    @Column(name = "NECT_BAG_PIECE", length = 10)
    private String neckBagPiece;//颈部包块
    @Column(name = "EYES", length = 10)
    private String eyes;//眼睛
    @Column(name = "EAR_APPEARANCE", length = 10)
    private String earAppearance;//耳外观
    @Column(name = "CHILDREN_EAR", length = 10)
    private String childrenEar;//听力
    @Column(name = "ORAL", length = 10)
    private String oral;//口腔
    @Column(name = "CHEST", length = 10)
    private String chest;//胸部
    @Column(name = "ABDOMINAL_TOUCH", length = 10)
    private String abdominalTouch;//腹部
    @Column(name = "UMBILICAL_CORD", length = 10)
    private String umbilicalCord;//脐部
    @Column(name = "LIMBS", length = 10)
    private String limbs;//四肢
    @Column(name = "RICKETS_SYMPTOMS", length = 10)
    private String ricketsSymptoms;//佝偻病症状
    @Column(name = "RICKETS_SIGNS", length = 10)
    private String ricketsSigns;//佝偻病体征
    @Column(name = "ANUS", length = 10)
    private String anus;//肛门/外生殖器
    @Column(name = "HEMOGLOBIN", length = 10)
    private String hemoglobin;//血红蛋白值
    @Column(name = "OUTDOOR_ACTIVITY", length = 10)
    private String outdoorActivity;//户外活动
    @Column(name = "VD", length = 10)
    private String vd;//维生素D
    @Column(name = "GROWTH_PG", length = 100)
    private String growthPg;//发育评估
    @Column(name = "TWO_VISIT_SITUATION",length = 10)
    private String twoVisitSituation;//两次随访间患病情况
    @Column(name = "TWO_VISIT_SITUATION_LUNG", length = 36)
    private String twoVisitSituationLung;// 两次随访间患病情况肺炎
    @Column(name = "TWO_VISIT_SITUATION_DIARRHEA", length = 36)
    private String twoVisitSituationDiarrhea;// 两次随访间患病情况腹泻
    @Column(name = "TWO_VISIT_SITUATION_INJURY", length = 36)
    private String twoVisitSituationInjury;// 两次随访间患病情况外伤
    @Column(name = "TWO_VISIT_SITUATION_OTHER", length = 36)
    private String twoVisitSituationOther;// 两次随访间患病情况其他
    @Column(name = "REFERRAL", length = 10)
    private String referral;//转诊情况
    @Column(name = "REFERRAL_REASON", length = 36)
    private String referralReason;//转诊建议原因
    @Column(name = "REFERRAL_ORG", length = 50)
    private String referralOrg;//转诊机构
    @Column(name = "GUIDE", length = 10)
    private String guide;//指导
    @Column(name = "GUIDE_OTHER", length = 36)
    private String guideOther;//指导其他选项内容
    @Column(name = "NEXT_VISIT_TIME", length = 36)
    private String nextVisiTime;//下次随访日期
    @Column(name = "VISIT_DOCTOR_NAME", length = 36)
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

    public String getHeadCircumference() {
        return headCircumference;
    }

    public void setHeadCircumference(String headCircumference) {
        this.headCircumference = headCircumference;
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

    public String getNeckBagPiece() {
        return neckBagPiece;
    }

    public void setNeckBagPiece(String neckBagPiece) {
        this.neckBagPiece = neckBagPiece;
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

    public String getOral() {
        return oral;
    }

    public void setOral(String oral) {
        this.oral = oral;
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

    public String getUmbilicalCord() {
        return umbilicalCord;
    }

    public void setUmbilicalCord(String umbilicalCord) {
        this.umbilicalCord = umbilicalCord;
    }

    public String getLimbs() {
        return limbs;
    }

    public void setLimbs(String limbs) {
        this.limbs = limbs;
    }

    public String getRicketsSymptoms() {
        return ricketsSymptoms;
    }

    public void setRicketsSymptoms(String ricketsSymptoms) {
        this.ricketsSymptoms = ricketsSymptoms;
    }

    public String getRicketsSigns() {
        return ricketsSigns;
    }

    public void setRicketsSigns(String ricketsSigns) {
        this.ricketsSigns = ricketsSigns;
    }

    public String getAnus() {
        return anus;
    }

    public void setAnus(String anus) {
        this.anus = anus;
    }

    public String getHemoglobin() {
        return hemoglobin;
    }

    public void setHemoglobin(String hemoglobin) {
        this.hemoglobin = hemoglobin;
    }

    public String getTwoVisitSituationLung() {
        return twoVisitSituationLung;
    }

    public void setTwoVisitSituationLung(String twoVisitSituationLung) {
        this.twoVisitSituationLung = twoVisitSituationLung;
    }

    public String getTwoVisitSituationDiarrhea() {
        return twoVisitSituationDiarrhea;
    }

    public void setTwoVisitSituationDiarrhea(String twoVisitSituationDiarrhea) {
        this.twoVisitSituationDiarrhea = twoVisitSituationDiarrhea;
    }

    public String getTwoVisitSituationInjury() {
        return twoVisitSituationInjury;
    }

    public void setTwoVisitSituationInjury(String twoVisitSituationInjury) {
        this.twoVisitSituationInjury = twoVisitSituationInjury;
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

    public String getTwoVisitSituationOther() {
        return twoVisitSituationOther;
    }

    public void setTwoVisitSituationOther(String twoVisitSituationOther) {
        this.twoVisitSituationOther = twoVisitSituationOther;
    }

    public String getReferralDept() {
        return referralDept;
    }

    public void setReferralDept(String referralDept) {
        this.referralDept = referralDept;
    }
}
