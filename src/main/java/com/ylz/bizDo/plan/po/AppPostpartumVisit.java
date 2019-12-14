package com.ylz.bizDo.plan.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**产后访视记录表 有用copy工具
 * Created by zzl on 2017/8/29.
 */
@Entity
@Table(name = "APP_POSTPARTUM_VISIT")
public class AppPostpartumVisit  extends BasePO{
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;
    @Column(name = "POST_VISIT_ID",length = 36)
    private String postVisitId;//随访外键
    @Column(name = "POST_PATIENT_ID",length = 36)
    private String postPatientId;//随访患者id
    @Column(name = "POST_NAME",length = 50)
    private String postName;//姓名
    @Column(name = "POST_CODE",length = 20)
    private String postCode;//编号
    @Column(name = "POST_VISIT_DATE",length = 20)
    private String postVistDate;//随访日期
    @Column(name = "POST_CHILDBIRTH_DATE",length = 20)
    private String postChildbirthDate;//分娩日期
    @Column(name = "POST_LEAVE_HOSPITAL_DATE",length = 20)
    private String postLeveHospitalDate;//出院日期
    @Column(name = "POST_ANIMAL_HEAT",length = 10)
    private String postAnimalHeat;//体温
    @Column(name = "POST_HEALTH_SITUATION")
    private String postHealthSituation;//一般健康情况
    @Column(name = "POST_PSYCHOLOGIC_STATUS")
    private String postpSychologicStatus;//一般心理状况
    @Column(name = "POST_BLOOD_PRESSURE_ONE",length = 10)
    private String postBloodPressureOne;//血压收缩压
    @Column(name = "POST_BLOOD_PRESSURE_TWO",length = 10)
    private String postBloodPressureTwo;//血压舒张压
    @Column(name = "POST_BREAST",length = 10)
    private String postBreast;//乳房（1未见异常 2异常）
    @Column(name = "POST_BREAST_CONTENT")
    private String postBreastContent;//乳房异常内容
    @Column(name = "POST_LOCHIA",length = 10)
    private String postLochia;//恶露（1未见异常 2异常）
    @Column(name = "POST_LOCHIA_CONTENT")
    private String postLochiaContent;//恶露异常内容
    @Column(name = "POST_UTERUS",length = 10)
    private String postUterus;//子宫（1未见异常 2异常）
    @Column(name = "POST_UTERUS_CONTENT")
    private String postUterusContent;//子宫异常内容
    @Column(name = "POST_WOUND",length = 10)
    private String postWound;//伤口(1未见异常 2异常)
    @Column(name = "POST_WOUND_CONTENT")
    private String postWoundContent;//伤口异常内容
    @Column(name = "POST_OTHER_CONTENT")
    private String postOtherContent;//其他内容
    @Column(name = "POST_CLASSIFY",length = 10)
    private String postClassify;//分类（1未见异常 2异常）
    @Column(name = "POST_CLASSIFY_CONTENT")
    private String postClassifyContent;//分类异常内容
    @Column(name = "POST_GUIDE",length = 10)
    private String postGuide;//指导（1个人卫生 2心理 3营养 4母乳喂养 5新生儿护理与喂养 6其他）
    @Column(name = "POST_GUIDE_CONTENT")
    private String postGuideContent;//其他指导内容
    @Column(name = "POST_REFERRAL",length = 10)
    private String postReferral;//转诊（0无 1有）
    @Column(name = "POST_REFERRAL_REASON")
    private String postReferralReason;//转诊原因
    @Column(name = "POST_REFERRAL_ORG",length = 36)
    private String postReferralOrg;//转诊机构
    @Column(name = "POST_REFERRAL_DEPT",length = 36)
    private String postReferralDept;//转诊科室
    @Column(name = "POST_NEXT_VISIT_DATE",length = 20)
    private String postNextVisitDate;//下次随访日期
    @Column(name = "POST_DOCTOR_ID",length = 36)
    private String postDoctorId;//随访医生id
    @Column(name = "POST_DOCTOR_IMAGE",length = 100)
    private String postDoctorImage;//随访医生签名
    @Column(name = "POST_VISIT_WAY",length = 10)
    private String postVisitWay;//随访方式

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPostName() {
        return postName;
    }

    public void setPostName(String postName) {
        this.postName = postName;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getPostVistDate() {
        return postVistDate;
    }

    public void setPostVistDate(String postVistDate) {
        this.postVistDate = postVistDate;
    }

    public String getPostChildbirthDate() {
        return postChildbirthDate;
    }

    public void setPostChildbirthDate(String postChildbirthDate) {
        this.postChildbirthDate = postChildbirthDate;
    }

    public String getPostLeveHospitalDate() {
        return postLeveHospitalDate;
    }

    public void setPostLeveHospitalDate(String postLeveHospitalDate) {
        this.postLeveHospitalDate = postLeveHospitalDate;
    }

    public String getPostAnimalHeat() {
        return postAnimalHeat;
    }

    public void setPostAnimalHeat(String postAnimalHeat) {
        this.postAnimalHeat = postAnimalHeat;
    }

    public String getPostHealthSituation() {
        return postHealthSituation;
    }

    public void setPostHealthSituation(String postHealthSituation) {
        this.postHealthSituation = postHealthSituation;
    }

    public String getPostpSychologicStatus() {
        return postpSychologicStatus;
    }

    public void setPostpSychologicStatus(String postpSychologicStatus) {
        this.postpSychologicStatus = postpSychologicStatus;
    }

    public String getPostBloodPressureOne() {
        return postBloodPressureOne;
    }

    public void setPostBloodPressureOne(String postBloodPressureOne) {
        this.postBloodPressureOne = postBloodPressureOne;
    }

    public String getPostBloodPressureTwo() {
        return postBloodPressureTwo;
    }

    public void setPostBloodPressureTwo(String postBloodPressureTwo) {
        this.postBloodPressureTwo = postBloodPressureTwo;
    }

    public String getPostBreast() {
        return postBreast;
    }

    public void setPostBreast(String postBreast) {
        this.postBreast = postBreast;
    }

    public String getPostBreastContent() {
        return postBreastContent;
    }

    public void setPostBreastContent(String postBreastContent) {
        this.postBreastContent = postBreastContent;
    }

    public String getPostLochia() {
        return postLochia;
    }

    public void setPostLochia(String postLochia) {
        this.postLochia = postLochia;
    }

    public String getPostLochiaContent() {
        return postLochiaContent;
    }

    public void setPostLochiaContent(String postLochiaContent) {
        this.postLochiaContent = postLochiaContent;
    }

    public String getPostUterus() {
        return postUterus;
    }

    public void setPostUterus(String postUterus) {
        this.postUterus = postUterus;
    }

    public String getPostUterusContent() {
        return postUterusContent;
    }

    public void setPostUterusContent(String postUterusContent) {
        this.postUterusContent = postUterusContent;
    }

    public String getPostWound() {
        return postWound;
    }

    public void setPostWound(String postWound) {
        this.postWound = postWound;
    }

    public String getPostWoundContent() {
        return postWoundContent;
    }

    public void setPostWoundContent(String postWoundContent) {
        this.postWoundContent = postWoundContent;
    }

    public String getPostOtherContent() {
        return postOtherContent;
    }

    public void setPostOtherContent(String postOtherContent) {
        this.postOtherContent = postOtherContent;
    }

    public String getPostClassify() {
        return postClassify;
    }

    public void setPostClassify(String postClassify) {
        this.postClassify = postClassify;
    }

    public String getPostClassifyContent() {
        return postClassifyContent;
    }

    public void setPostClassifyContent(String postClassifyContent) {
        this.postClassifyContent = postClassifyContent;
    }

    public String getPostGuide() {
        return postGuide;
    }

    public void setPostGuide(String postGuide) {
        this.postGuide = postGuide;
    }

    public String getPostGuideContent() {
        return postGuideContent;
    }

    public void setPostGuideContent(String postGuideContent) {
        this.postGuideContent = postGuideContent;
    }

    public String getPostReferral() {
        return postReferral;
    }

    public void setPostReferral(String postReferral) {
        this.postReferral = postReferral;
    }

    public String getPostReferralReason() {
        return postReferralReason;
    }

    public void setPostReferralReason(String postReferralReason) {
        this.postReferralReason = postReferralReason;
    }

    public String getPostReferralOrg() {
        return postReferralOrg;
    }

    public void setPostReferralOrg(String postReferralOrg) {
        this.postReferralOrg = postReferralOrg;
    }

    public String getPostReferralDept() {
        return postReferralDept;
    }

    public void setPostReferralDept(String postReferralDept) {
        this.postReferralDept = postReferralDept;
    }

    public String getPostVisitId() {
        return postVisitId;
    }

    public void setPostVisitId(String postVisitId) {
        this.postVisitId = postVisitId;
    }

    public String getPostNextVisitDate() {
        return postNextVisitDate;
    }

    public void setPostNextVisitDate(String postNextVisitDate) {
        this.postNextVisitDate = postNextVisitDate;
    }

    public String getPostDoctorId() {
        return postDoctorId;
    }

    public void setPostDoctorId(String postDoctorId) {
        this.postDoctorId = postDoctorId;
    }

    public String getPostDoctorImage() {
        return postDoctorImage;
    }

    public void setPostDoctorImage(String postDoctorImage) {
        this.postDoctorImage = postDoctorImage;
    }

    public String getPostVisitWay() {
        return postVisitWay;
    }

    public void setPostVisitWay(String postVisitWay) {
        this.postVisitWay = postVisitWay;
    }

    public String getPostPatientId() {
        return postPatientId;
    }

    public void setPostPatientId(String postPatientId) {
        this.postPatientId = postPatientId;
    }
}
