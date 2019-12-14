package com.ylz.bizDo.plan.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 新生儿家庭访视记录表
 * Created by zzl on 2017/8/29.
 */
@Entity
@Table(name = "APP_CHILD")
public class AppChild extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name = "CHILD_VISIT_ID",length = 36)
    private String childVisitId;//外键
    @Column(name = "CHILD_AGE_GROUP",length = 10)
    private String childAgeGroup;//年龄段
    @Column(name = "CHILD_PATIENT_ID",length = 36)
    private String childPatientId;//随访人员id
    @Column(name = "CHILD_NAME",length = 50)
    private String childName;//儿童姓名
    @Column(name = "CHILD_CODE",length = 100)
    private String childCode;//编号
    @Column(name = "CHILD_GENDER",length = 10)
    private String childGender;//性别
    @Column(name = "CHILD_BIRTH_DAY")
    private String childBirthDay;//出生日期
    @Column(name = "CHILD_IDNO",length = 50)
    private String childIdNo;//身份证号
    @Column(name = "CHILD_ADDRESS")
    private String childAddress;//家庭住址
    @Column(name = "CHILD_FATHER_NAME",length = 50)
    private String childFatherName;//父亲姓名
    @Column(name = "CHILD_FATHER_OCCUPATION",length = 100)
    private String childFatherOccupation;//职业
    @Column(name = "CHILD_FATHER_TEL",length = 11)
    private String childFatherTel;//联系电话
    @Column(name = "CHILD_FATHER_BIRTH")
    private String childFatherBirth;//父亲出生日期
    @Column(name = "CHILD_MOTHER_NAME",length = 50)
    private String childMotherName;//母亲姓名
    @Column(name = "CHILD_MOTHER_OCCUPATION",length = 100)
    private String childMotherOccupation;//母亲职业
    @Column(name = "CHILD_MOTHER_TEL",length = 11)
    private String childMotherTel;//母亲电话
    @Column(name = "CHILD_MOTHER_BIRTH")
    private String childMotherBirth;//母亲出生日期
    @Column(name = "CHILD_GESTATIONAL_WEEKS",length = 10)
    private String childGestationalWeeks;//孕周
    @Column(name = "CHILD_MOTHER_SITUATION",length = 10)
    private String childMotherSituation;//母亲妊娠期患病情况
    @Column(name = "CHILD_ACCOUCHE_ORG_ID",length = 36)
    private String childAccoucheOrgId;//助产机构id
    @Column(name = "CHILD_ACCOUCHE_ORG",length = 50)
    private String childAccoucheOrg;//助产机构名称
    @Column(name = "CHILD_BIRTH_SITUATION",length = 10)
    private String childBirthSituation;//出生情况（1顺产 2胎头吸引 3产钳 4剖宫 5双多胎 6臀位 7其他）
    @Column(name = "CHILD_BIRTH_SITUATION_OTHER")
    private String childBirthSituationOther;//出生其他情况内容
    @Column(name = "CHILD_ASPHYXIA",length = 10)
    private String childAsphyxia;//新生儿窒息情况(1min--1无 ，5min--2有)
    @Column(name = "CHILD_DEFORMITY",length = 10)
    private String childDeformity;//畸形情况(1无 2有)
    @Column(name = "CHILD_DEFORMITY_CONTENT")
    private String childDeformityContent;//有畸形情况内容
    @Column(name = "CHILD_HEARING_SCREENING",length = 10)
    private String childHearingScreening;//新生儿听力筛查（1通过 2未通过 3未筛查 4不详）
    @Column(name = "CHILD_DISEASE_SCREENING",length = 10)
    private String childDiseaseScreening;//新生儿疾病筛查（1未进行 2检查均阴性 3甲低 4苯丙酮尿症 5其他遗传代谢病）
    @Column(name = "CHILD_BIRTH_WEIGHT",length = 50)
    private String childBirthWeight;//新生儿出生体重
    @Column(name = "CHILD_WEIGHT",length = 50)
    private String childWeight;//目前体重
    @Column(name = "CHILD_BIRTH_HEIGHT",length = 50)
    private String childBirthHeight;//出生身长
    @Column(name = "CHILD_FEEDING_WAY",length = 10)
    private String childFeedingWay;//喂养方式（1纯母乳 2混合 3人工）
    @Column(name = "CHILD_EAT_MILK",length = 20)
    private String childEatMilk;//吃奶量 ml/次
    @Column(name = "CHILD_EAT_MILK_NUM",length = 10)
    private String childEatMilkNum;//吃奶次数 次/日
    @Column(name = "CHILD_VOMITING",length = 10)
    private String childVomiting;//呕吐（1无 2有）
    @Column(name = "CHILD_SHIT",length = 10)
    private String childShit;//大便 （1糊状 2稀 3其他）
    @Column(name = "CHILD_SHIT_NUM",length = 20)
    private String childShitNum;//大便次数 次/日
    @Column(name = "CHILD_ANIMAL_HEAT",length = 10)
    private String childAnimalHeat;//体温
    @Column(name = "CHILD_HEART_RATE",length = 10)
    private String childHeartRate;//心率
    @Column(name = "CHILD_BREATHING_RATE",length = 10)
    private String childBreathingRate;//呼吸频率
    @Column(name = "CHILD_FACE",length = 10)
    private String childFace;//面色（1红润 2黄染 3其他）
    @Column(name = "CHILD_FACE_OTHER")
    private String childFaceOther;//面色其他内容
    @Column(name = "CHILD_JAUNDICE_PARTS",length = 10)
    private String childJaundiceParts;//黄疸部位(1无 2面部 3躯干 4四肢 5手足)
    @Column(name = "CHILD_BREGMA_ONE",length = 10)
    private String childBregmaONE;//前囟数据1
    @Column(name = "CHILD_BREGMA_TWO",length = 10)
    private String childBregmaTWO;//前囟数据2
    @Column(name = "CHILD_BREGMA",length = 10)
    private String childBregma;//前囟(1正常 2膨隆 3凹陷 4其他)
    @Column(name = "CHILD_BREGMA_OTHER")
    private String childBregmaOther;//前囟其他内容
    @Column(name = "CHILD_EYES",length = 10)
    private String childEyes;//眼睛（1未见异常 2异常）
    @Column(name = "CHILD_LIMBS_ACTIVITY",length = 10)
    private String childLimbsActivity;//四肢活动度（1未见异常 2异常）
    @Column(name = "CHILD_EAR_APPEARANCE", length = 10)
    private String childEarAppearance;//耳外观（1未见异常 2异常）
    @Column(name = "CHILD_NECK_BAG_PIECE", length = 10)
    private String childNeckBagPiece;//颈部包块（1无 2有）
    @Column(name = "CHILD_NOSE", length = 10)
    private String childNose;//鼻（1未见异常 2异常）
    @Column(name = "CHILD_SKIN", length = 10)
    private String childSkin;//皮肤（1未见异常 2湿疹 3糜烂 4其他）
    @Column(name = "CHILD_ORAL", length = 10)
    private String childOral;//口腔（1未见异常 2异常）
    @Column(name = "CHILD_ANUS", length = 10)
    private String childAnus;//肛门（1未见异常 2异常）
    @Column(name = "CHILD_LUNG_AUSCULTATION", length = 10)
    private String childLungAuscultation;//心肺听诊（1未见异常 2异常）
    @Column(name = "CHILD_CHEST", length = 10)
    private String childChest;//胸部（1未见异常 2异常）
    @Column(name = "CHILD_ABDOMINAL_TOUCH", length = 10)
    private String childAbdominalTouch;//腹部触诊（1未见异常 2异常）
    @Column(name = "CHILD_SPINE", length = 10)
    private String childSpine;//脊柱（1未见异常 2异常）
    @Column(name = "CHILD_GENITALS", length = 10)
    private String childGenitals;//外生殖器（1未见异常 2异常）
    @Column(name = "CHILD_UMBILICAL_CORD", length = 10)
    private String childUmbilicalCord;//脐带（1未脱 2脱落 3脐部有渗出 4其他）
    @Column(name = "CHILD_UMBILICAL_CORD_OTHER")
    private String childUmbilicalCordOther;//脐带其他选项内容
    @Column(name = "CHILD_REFERRAL", length = 36)
    private String childReferral;//转诊情况（0无 1有）
    @Column(name = "CHILD_REFERRAL_REASON")
    private String childReferralReason;//转诊建议原因
    @Column(name = "CHILD_REFERRAL_ORG", length = 36)
    private String childReferralOrg;//转诊机构id
    @Column(name = "CHILD_GUIDE", length = 100)
    private String childGuide;//指导（1喂养指导 2发育指导 3防病指导 4预防伤害指导 5口腔保健指导 6其他
    @Column(name = "CHILD_GUIDE_OTHER")
    private String childGuideOther;//指导其他选项内容
    @Column(name = "CHILD_VISIT_TIME",length = 20)
    private String childVisitTime;//本次访视日期
    @Column(name = "CHILD_NEXT_VISIT_ADDRESS")
    private String childNextVisitAddress;//下次随访地点
    @Column(name = "CHILD_NEXT_VISTI_TIME", length = 36)
    private String childNextVisiTime;//下次随访日期
    @Column(name = "CHILD_VISIT_DOCTOR_ID", length = 36)
    private String childVisitDoctorId;//随访医生签名
    @Column(name = "CHILD_REFERRAL_DEPT",length = 50)
    private String childReferralDept;//转诊科室
    @Column(name = "CHILD_VISIT_DOCTOR_IMAGE",length = 100)
    private String childVisitDoctorImage;//随访医生签名
    @Column(name = "CHILD_VISIT_WAY",length = 10)
    private String childVisitWay;//本次随访方式

    public String getChildVisitId() {
        return childVisitId;
    }

    public void setChildVisitId(String childVisitId) {
        this.childVisitId = childVisitId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChildAgeGroup() {
        return childAgeGroup;
    }

    public void setChildAgeGroup(String childAgeGroup) {
        this.childAgeGroup = childAgeGroup;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getChildCode() {
        return childCode;
    }

    public void setChildCode(String childCode) {
        this.childCode = childCode;
    }

    public String getChildGender() {
        return childGender;
    }

    public void setChildGender(String childGender) {
        this.childGender = childGender;
    }

    public String getChildBirthDay() {
        return childBirthDay;
    }

    public void setChildBirthDay(String childBirthDay) {
        this.childBirthDay = childBirthDay;
    }

    public String getChildIdNo() {
        return childIdNo;
    }

    public void setChildIdNo(String childIdNo) {
        this.childIdNo = childIdNo;
    }

    public String getChildAddress() {
        return childAddress;
    }

    public void setChildAddress(String childAddress) {
        this.childAddress = childAddress;
    }

    public String getChildFatherName() {
        return childFatherName;
    }

    public void setChildFatherName(String childFatherName) {
        this.childFatherName = childFatherName;
    }

    public String getChildFatherOccupation() {
        return childFatherOccupation;
    }

    public void setChildFatherOccupation(String childFatherOccupation) {
        this.childFatherOccupation = childFatherOccupation;
    }

    public String getChildFatherTel() {
        return childFatherTel;
    }

    public void setChildFatherTel(String childFatherTel) {
        this.childFatherTel = childFatherTel;
    }

    public String getChildFatherBirth() {
        return childFatherBirth;
    }

    public void setChildFatherBirth(String childFatherBirth) {
        this.childFatherBirth = childFatherBirth;
    }

    public String getChildMotherName() {
        return childMotherName;
    }

    public void setChildMotherName(String childMotherName) {
        this.childMotherName = childMotherName;
    }

    public String getChildMotherOccupation() {
        return childMotherOccupation;
    }

    public void setChildMotherOccupation(String childMotherOccupation) {
        this.childMotherOccupation = childMotherOccupation;
    }

    public String getChildMotherTel() {
        return childMotherTel;
    }

    public void setChildMotherTel(String childMotherTel) {
        this.childMotherTel = childMotherTel;
    }

    public String getChildMotherBirth() {
        return childMotherBirth;
    }

    public void setChildMotherBirth(String childMotherBirth) {
        this.childMotherBirth = childMotherBirth;
    }

    public String getChildGestationalWeeks() {
        return childGestationalWeeks;
    }

    public void setChildGestationalWeeks(String childGestationalWeeks) {
        this.childGestationalWeeks = childGestationalWeeks;
    }

    public String getChildMotherSituation() {
        return childMotherSituation;
    }

    public void setChildMotherSituation(String childMotherSituation) {
        this.childMotherSituation = childMotherSituation;
    }

    public String getChildAccoucheOrgId() {
        return childAccoucheOrgId;
    }

    public void setChildAccoucheOrgId(String childAccoucheOrgId) {
        this.childAccoucheOrgId = childAccoucheOrgId;
    }

    public String getChildBirthSituation() {
        return childBirthSituation;
    }

    public void setChildBirthSituation(String childBirthSituation) {
        this.childBirthSituation = childBirthSituation;
    }

    public String getChildBirthSituationOther() {
        return childBirthSituationOther;
    }

    public void setChildBirthSituationOther(String childBirthSituationOther) {
        this.childBirthSituationOther = childBirthSituationOther;
    }

    public String getChildAsphyxia() {
        return childAsphyxia;
    }

    public void setChildAsphyxia(String childAsphyxia) {
        this.childAsphyxia = childAsphyxia;
    }

    public String getChildDeformity() {
        return childDeformity;
    }

    public void setChildDeformity(String childDeformity) {
        this.childDeformity = childDeformity;
    }

    public String getChildDeformityContent() {
        return childDeformityContent;
    }

    public void setChildDeformityContent(String childDeformityContent) {
        this.childDeformityContent = childDeformityContent;
    }

    public String getChildHearingScreening() {
        return childHearingScreening;
    }

    public void setChildHearingScreening(String childHearingScreening) {
        this.childHearingScreening = childHearingScreening;
    }

    public String getChildDiseaseScreening() {
        return childDiseaseScreening;
    }

    public void setChildDiseaseScreening(String childDiseaseScreening) {
        this.childDiseaseScreening = childDiseaseScreening;
    }

    public String getChildBirthWeight() {
        return childBirthWeight;
    }

    public void setChildBirthWeight(String childBirthWeight) {
        this.childBirthWeight = childBirthWeight;
    }

    public String getChildWeight() {
        return childWeight;
    }

    public void setChildWeight(String childWeight) {
        this.childWeight = childWeight;
    }

    public String getChildBirthHeight() {
        return childBirthHeight;
    }

    public void setChildBirthHeight(String childBirthHeight) {
        this.childBirthHeight = childBirthHeight;
    }

    public String getChildFeedingWay() {
        return childFeedingWay;
    }

    public void setChildFeedingWay(String childFeedingWay) {
        this.childFeedingWay = childFeedingWay;
    }

    public String getChildEatMilk() {
        return childEatMilk;
    }

    public void setChildEatMilk(String childEatMilk) {
        this.childEatMilk = childEatMilk;
    }

    public String getChildEatMilkNum() {
        return childEatMilkNum;
    }

    public void setChildEatMilkNum(String childEatMilkNum) {
        this.childEatMilkNum = childEatMilkNum;
    }

    public String getChildVomiting() {
        return childVomiting;
    }

    public void setChildVomiting(String childVomiting) {
        this.childVomiting = childVomiting;
    }

    public String getChildShit() {
        return childShit;
    }

    public void setChildShit(String childShit) {
        this.childShit = childShit;
    }

    public String getChildShitNum() {
        return childShitNum;
    }

    public void setChildShitNum(String childShitNum) {
        this.childShitNum = childShitNum;
    }

    public String getChildAnimalHeat() {
        return childAnimalHeat;
    }

    public void setChildAnimalHeat(String childAnimalHeat) {
        this.childAnimalHeat = childAnimalHeat;
    }

    public String getChildHeartRate() {
        return childHeartRate;
    }

    public void setChildHeartRate(String childHeartRate) {
        this.childHeartRate = childHeartRate;
    }

    public String getChildBreathingRate() {
        return childBreathingRate;
    }

    public void setChildBreathingRate(String childBreathingRate) {
        this.childBreathingRate = childBreathingRate;
    }

    public String getChildFace() {
        return childFace;
    }

    public void setChildFace(String childFace) {
        this.childFace = childFace;
    }

    public String getChildFaceOther() {
        return childFaceOther;
    }

    public void setChildFaceOther(String childFaceOther) {
        this.childFaceOther = childFaceOther;
    }

    public String getChildJaundiceParts() {
        return childJaundiceParts;
    }

    public void setChildJaundiceParts(String childJaundiceParts) {
        this.childJaundiceParts = childJaundiceParts;
    }

    public String getChildBregmaONE() {
        return childBregmaONE;
    }

    public void setChildBregmaONE(String childBregmaONE) {
        this.childBregmaONE = childBregmaONE;
    }

    public String getChildBregmaTWO() {
        return childBregmaTWO;
    }

    public void setChildBregmaTWO(String childBregmaTWO) {
        this.childBregmaTWO = childBregmaTWO;
    }

    public String getChildBregma() {
        return childBregma;
    }

    public void setChildBregma(String childBregma) {
        this.childBregma = childBregma;
    }

    public String getChildBregmaOther() {
        return childBregmaOther;
    }

    public void setChildBregmaOther(String childBregmaOther) {
        this.childBregmaOther = childBregmaOther;
    }

    public String getChildEyes() {
        return childEyes;
    }

    public void setChildEyes(String childEyes) {
        this.childEyes = childEyes;
    }

    public String getChildLimbsActivity() {
        return childLimbsActivity;
    }

    public void setChildLimbsActivity(String childLimbsActivity) {
        this.childLimbsActivity = childLimbsActivity;
    }

    public String getChildEarAppearance() {
        return childEarAppearance;
    }

    public void setChildEarAppearance(String childEarAppearance) {
        this.childEarAppearance = childEarAppearance;
    }

    public String getChildNeckBagPiece() {
        return childNeckBagPiece;
    }

    public void setChildNeckBagPiece(String childNeckBagPiece) {
        this.childNeckBagPiece = childNeckBagPiece;
    }

    public String getChildNose() {
        return childNose;
    }

    public void setChildNose(String childNose) {
        this.childNose = childNose;
    }

    public String getChildSkin() {
        return childSkin;
    }

    public void setChildSkin(String childSkin) {
        this.childSkin = childSkin;
    }

    public String getChildOral() {
        return childOral;
    }

    public void setChildOral(String childOral) {
        this.childOral = childOral;
    }

    public String getChildAnus() {
        return childAnus;
    }

    public void setChildAnus(String childAnus) {
        this.childAnus = childAnus;
    }

    public String getChildLungAuscultation() {
        return childLungAuscultation;
    }

    public void setChildLungAuscultation(String childLungAuscultation) {
        this.childLungAuscultation = childLungAuscultation;
    }

    public String getChildChest() {
        return childChest;
    }

    public void setChildChest(String childChest) {
        this.childChest = childChest;
    }

    public String getChildAbdominalTouch() {
        return childAbdominalTouch;
    }

    public void setChildAbdominalTouch(String childAbdominalTouch) {
        this.childAbdominalTouch = childAbdominalTouch;
    }

    public String getChildSpine() {
        return childSpine;
    }

    public void setChildSpine(String childSpine) {
        this.childSpine = childSpine;
    }

    public String getChildGenitals() {
        return childGenitals;
    }

    public void setChildGenitals(String childGenitals) {
        this.childGenitals = childGenitals;
    }

    public String getChildUmbilicalCord() {
        return childUmbilicalCord;
    }

    public void setChildUmbilicalCord(String childUmbilicalCord) {
        this.childUmbilicalCord = childUmbilicalCord;
    }

    public String getChildUmbilicalCordOther() {
        return childUmbilicalCordOther;
    }

    public void setChildUmbilicalCordOther(String childUmbilicalCordOther) {
        this.childUmbilicalCordOther = childUmbilicalCordOther;
    }

    public String getChildReferral() {
        return childReferral;
    }

    public void setChildReferral(String childReferral) {
        this.childReferral = childReferral;
    }

    public String getChildReferralReason() {
        return childReferralReason;
    }

    public void setChildReferralReason(String childReferralReason) {
        this.childReferralReason = childReferralReason;
    }

    public String getChildReferralOrg() {
        return childReferralOrg;
    }

    public void setChildReferralOrg(String childReferralOrg) {
        this.childReferralOrg = childReferralOrg;
    }

    public String getChildGuide() {
        return childGuide;
    }

    public void setChildGuide(String childGuide) {
        this.childGuide = childGuide;
    }

    public String getChildGuideOther() {
        return childGuideOther;
    }

    public void setChildGuideOther(String childGuideOther) {
        this.childGuideOther = childGuideOther;
    }

    public String getChildVisitTime() {
        return childVisitTime;
    }

    public void setChildVisitTime(String childVisitTime) {
        this.childVisitTime = childVisitTime;
    }

    public String getChildNextVisitAddress() {
        return childNextVisitAddress;
    }

    public void setChildNextVisitAddress(String childNextVisitAddress) {
        this.childNextVisitAddress = childNextVisitAddress;
    }

    public String getChildNextVisiTime() {
        return childNextVisiTime;
    }

    public void setChildNextVisiTime(String childNextVisiTime) {
        this.childNextVisiTime = childNextVisiTime;
    }

    public String getChildVisitDoctorId() {
        return childVisitDoctorId;
    }

    public void setChildVisitDoctorId(String childVisitDoctorId) {
        this.childVisitDoctorId = childVisitDoctorId;
    }

    public String getChildReferralDept() {
        return childReferralDept;
    }

    public void setChildReferralDept(String childReferralDept) {
        this.childReferralDept = childReferralDept;
    }

    public String getChildVisitDoctorImage() {
        return childVisitDoctorImage;
    }

    public void setChildVisitDoctorImage(String childVisitDoctorImage) {
        this.childVisitDoctorImage = childVisitDoctorImage;
    }

    public String getChildVisitWay() {
        return childVisitWay;
    }

    public void setChildVisitWay(String childVisitWay) {
        this.childVisitWay = childVisitWay;
    }

    public String getChildPatientId() {
        return childPatientId;
    }

    public void setChildPatientId(String childPatientId) {
        this.childPatientId = childPatientId;
    }

    public String getChildAccoucheOrg() {
        return childAccoucheOrg;
    }

    public void setChildAccoucheOrg(String childAccoucheOrg) {
        this.childAccoucheOrg = childAccoucheOrg;
    }

}
