package com.ylz.bizDo.plan.po;


import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**新生儿家庭访视记录表
 * Created by admin on 2017-05-13.
 */
@Entity
@Table(name = "APP_NEWCHILDREN_TABLE")
public class AppNewChildrenTable extends BasePO {
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
    private String code;//编号
    @Column(name = "SEX", length = 10)
    private String sex;//性别 (1男 2女 9未说明的性别 0未知的性别)
    @Column(name = "BIRTHDAY", length = 36)
    private String birthDay;//出生日期
    @Column(name = "ID_CARD", length = 36)
    private String idCard;//身份证号
    @Column(name = "ADDRESS", length = 100)
    private String address;//家庭住址
    @Column(name = "FATHER_NAME", length = 36)
    private String fatherName;//父亲姓名
    @Column(name = "FATHER_WORK", length = 36)
    private String fatherWork;//父亲职业
    @Column(name = "FATHER_PHONE", length = 36)
    private String fatherPhone;//父亲联系电话
    @Column(name = "FATHER_BIRTHDAY", length = 36)
    private String fatherBirthDay;//父亲出生日期
    @Column(name = "MOTHER_NAME", length = 36)
    private String motherName;//母亲姓名
    @Column(name = "MOTHER_WORK", length = 36)
    private String motherWork;//母亲职业
    @Column(name = "MOTHER_PHONE", length = 36)
    private String motherPhone;//母亲联系电话
    @Column(name = "MOTHER_BIRTHDAY", length = 36)
    private String motherBirthDay;//母亲出生日期
    @Column(name = "WEEK_OF_BIRTH", length = 36)
    private String weekOfBirth;//出生孕周
    @Column(name = "MOTHER_DISEASE", length = 36)
    private String motherDisease;//母亲妊娠期患病情况 （1无 2糖尿病 3妊娠期高血压 4其他）
    @Column(name = "BIRTH_ORG", length = 50)
    private String birthOrg;//助产机构名称
    @Column(name = "BIRTH_SITUATION", length = 36)
    private String birthSituation;//出生情况（1顺产 2胎头吸引 3产钳 4剖宫 5双多胎 6臀位 7其他
    @Column(name = "BIRTH_SITUATION_OTHER", length = 50)
    private String birthSituationOther;//出生情况其他
    @Column(name = "CHILDREN_CHOKING", length = 36)
    private String childrenChoking;//新生儿窒息
    @Column(name = "DEFORMITY", length = 36)
    private String deformity;//畸形情况 （1无 2 有）
    @Column(name = "DEFORMITY_DETIAL", length = 50)
    private String deformityDetial;//畸形情况详细
    @Column(name = "CHILDREN_EAR", length = 36)
    private String childrenEar;//CHILDREN_EARCHILDREN_EAR
    @Column(name = "CHILDREN_DISEASE", length = 36)
    private String childrenDisease;//新生儿疾病筛查 （1未进行 2检查均阴性 3甲低 4苯丙酮尿症 5 其他遗传代谢病）
    @Column(name = "BIRTH_WEIGHT", length = 36)
    private String birthWeight;//新生儿出生体重
    @Column(name = "NOW_WEIGHT", length = 36)
    private String nowWeight;//目前体重
    @Column(name = "BIRTH_HEIGHT", length = 36)
    private String birthHeight;//出生身高
    @Column(name = "FEEDING_WAY", length = 36)
    private String feedingWay;//喂养方式 （1纯母乳 2混合 3人工）
    @Column(name = "EAT_FOOD", length = 36)
    private String eatFood;//吃奶量
    @Column(name = "EAT_NUM", length = 36)
    private String eatNum;//吃奶次数
    @Column(name = "VOMITING", length = 36)
    private String vomiting;//呕吐情况 （1无 2有）
    @Column(name = "FECES", length = 36)
    private String feces;//大便（1糊状 2 稀 3其他）
    @Column(name = "FECES_NUM", length = 36)
    private String fecesNum;//大便次数
    @Column(name = "BODY_HEAT", length = 36)
    private String bodyHeat;//体温
    @Column(name = "HEART_RATE", length = 36)
    private String heartRate;//心率
    @Column(name = "BREATHING_RATE", length = 36)
    private String breathingRate;//呼吸频率
    @Column(name = "FACE", length = 36)
    private String face;//面色（1红润 2黄染 3其他）
    @Column(name = "FACE_OTHER", length = 36)
    private String faceOther;//面色其他
    @Column(name = "JAUNDICE_PARTS", length = 36)
    private String jaundiceParts;//黄疸部位（1无 2面部 3躯干 4四肢 5手足）
    @Column(name = "BREGMA_NUM1", length = 36)
    private String bregmaNum1;//前囟数据1
    @Column(name = "BREGMA_NUM2", length = 36)
    private String bregmaNum2;//前囟数据2
    @Column(name = "BREGMA", length = 36)
    private String bregma;//前囟选项（1正常 2膨隆 3凹陷 4其他）
    @Column(name = "BREGMA_OTHER", length = 36)
    private String bregmaOther;//前囟其他选项内容
    @Column(name = "EYES", length = 10)
    private String eyes;//眼睛（1未见异常 2异常）
    @Column(name = "LIMBS", length = 10)
    private String limbs;//四肢活动度（1未见异常 2异常）
    @Column(name = "EAR_APPEARANCE", length = 10)
    private String earAppearance;//耳外观（1未见异常 2异常）
    @Column(name = "NECK_BAG_PIECE", length = 10)
    private String neckBagPiece;//颈部包块（1无 2有）
    @Column(name = "NOSE", length = 10)
    private String nose;//鼻（1未见异常 2异常）
    @Column(name = "SKIN", length = 10)
    private String skin;//皮肤（1未见异常 2湿疹 3糜烂 4其他）
    @Column(name = "ORAL", length = 10)
    private String oral;//口腔（1未见异常 2异常）
    @Column(name = "ANUS", length = 10)
    private String anus;//肛门（1未见异常 2异常）
    @Column(name = "LUNG_AUSCULTATION", length = 10)
    private String lungAuscultation;//心肺听诊（1未见异常 2异常）
    @Column(name = "CHEST", length = 10)
    private String chest;//胸部（1未见异常 2异常）
    @Column(name = "ABDOMINAL_TOUCH", length = 10)
    private String abdominalTouch;//腹部触诊（1未见异常 2异常）
    @Column(name = "SPINE", length = 10)
    private String spine;//脊柱（1未见异常 2异常）
    @Column(name = "GENITALS", length = 10)
    private String genitals;//外生殖器（1未见异常 2异常）
    @Column(name = "UMBILICAL_CORD", length = 10)
    private String umbilicalCord;//脐带（1未脱 2脱落 3脐部有渗出 4其他）
    @Column(name = "UMBILICAL_CORD_OTHER", length = 36)
    private String umbilicalCordOther;//脐带其他选项内容
    @Column(name = "REFERRAL", length = 36)
    private String referral;//转诊情况（0无 1有）
    @Column(name = "REFERRAL_REASON", length = 100)
    private String referralReason;//转诊建议原因
    @Column(name = "REFERRAL_ORG", length = 50)
    private String referralOrg;//转诊机构
    @Column(name = "GUIDE", length = 36)
    private String guide;//指导（1喂养指导 2发育指导 3防病指导 4预防伤害指导 5口腔保健指导 6其他
    @Column(name = "GUIDE_OTHER", length = 50)
    private String guideOther;//指导其他选项内容
    @Column(name = "VISIT_TIME", length = 36)
    private String VisitTime;//本次访视日期
    @Column(name = "NEXT_VISIT_ADDRESS", length = 36)
    private String nextVisitAddress;//下次随访地点
    @Column(name = "NEXT_VISTI_TIME", length = 36)
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getFatherWork() {
        return fatherWork;
    }

    public void setFatherWork(String fatherWork) {
        this.fatherWork = fatherWork;
    }

    public String getFatherPhone() {
        return fatherPhone;
    }

    public void setFatherPhone(String fatherPhone) {
        this.fatherPhone = fatherPhone;
    }

    public String getFatherBirthDay() {
        return fatherBirthDay;
    }

    public void setFatherBirthDay(String fatherBirthDay) {
        this.fatherBirthDay = fatherBirthDay;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getMotherWork() {
        return motherWork;
    }

    public void setMotherWork(String motherWork) {
        this.motherWork = motherWork;
    }

    public String getMotherPhone() {
        return motherPhone;
    }

    public void setMotherPhone(String motherPhone) {
        this.motherPhone = motherPhone;
    }

    public String getMotherBirthDay() {
        return motherBirthDay;
    }

    public void setMotherBirthDay(String motherBirthDay) {
        this.motherBirthDay = motherBirthDay;
    }

    public String getWeekOfBirth() {
        return weekOfBirth;
    }

    public void setWeekOfBirth(String weekOfBirth) {
        this.weekOfBirth = weekOfBirth;
    }

    public String getMotherDisease() {
        return motherDisease;
    }

    public void setMotherDisease(String motherDisease) {
        this.motherDisease = motherDisease;
    }

    public String getBirthOrg() {
        return birthOrg;
    }

    public void setBirthOrg(String birthOrg) {
        this.birthOrg = birthOrg;
    }

    public String getBirthSituation() {
        return birthSituation;
    }

    public void setBirthSituation(String birthSituation) {
        this.birthSituation = birthSituation;
    }

    public String getBirthSituationOther() {
        return birthSituationOther;
    }

    public void setBirthSituationOther(String birthSituationOther) {
        this.birthSituationOther = birthSituationOther;
    }

    public String getChildrenChoking() {
        return childrenChoking;
    }

    public void setChildrenChoking(String childrenChoking) {
        this.childrenChoking = childrenChoking;
    }

    public String getDeformity() {
        return deformity;
    }

    public void setDeformity(String deformity) {
        this.deformity = deformity;
    }

    public String getDeformityDetial() {
        return deformityDetial;
    }

    public void setDeformityDetial(String deformityDetial) {
        this.deformityDetial = deformityDetial;
    }

    public String getChildrenEar() {
        return childrenEar;
    }

    public void setChildrenEar(String childrenEar) {
        this.childrenEar = childrenEar;
    }

    public String getChildrenDisease() {
        return childrenDisease;
    }

    public void setChildrenDisease(String childrenDisease) {
        this.childrenDisease = childrenDisease;
    }

    public String getBirthWeight() {
        return birthWeight;
    }

    public void setBirthWeight(String birthWeight) {
        this.birthWeight = birthWeight;
    }

    public String getNowWeight() {
        return nowWeight;
    }

    public void setNowWeight(String nowWeight) {
        this.nowWeight = nowWeight;
    }

    public String getBirthHeight() {
        return birthHeight;
    }

    public void setBirthHeight(String birthHeight) {
        this.birthHeight = birthHeight;
    }

    public String getFeedingWay() {
        return feedingWay;
    }

    public void setFeedingWay(String feedingWay) {
        this.feedingWay = feedingWay;
    }

    public String getEatFood() {
        return eatFood;
    }

    public void setEatFood(String eatFood) {
        this.eatFood = eatFood;
    }

    public String getEatNum() {
        return eatNum;
    }

    public void setEatNum(String eatNum) {
        this.eatNum = eatNum;
    }

    public String getVomiting() {
        return vomiting;
    }

    public void setVomiting(String vomiting) {
        this.vomiting = vomiting;
    }

    public String getFeces() {
        return feces;
    }

    public void setFeces(String feces) {
        this.feces = feces;
    }

    public String getFecesNum() {
        return fecesNum;
    }

    public void setFecesNum(String fecesNum) {
        this.fecesNum = fecesNum;
    }

    public String getBodyHeat() {
        return bodyHeat;
    }

    public void setBodyHeat(String bodyHeat) {
        this.bodyHeat = bodyHeat;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }

    public String getBreathingRate() {
        return breathingRate;
    }

    public void setBreathingRate(String breathingRate) {
        this.breathingRate = breathingRate;
    }

    public String getFace() {
        return face;
    }

    public void setFace(String face) {
        this.face = face;
    }

    public String getFaceOther() {
        return faceOther;
    }

    public void setFaceOther(String faceOther) {
        this.faceOther = faceOther;
    }

    public String getJaundiceParts() {
        return jaundiceParts;
    }

    public void setJaundiceParts(String jaundiceParts) {
        this.jaundiceParts = jaundiceParts;
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

    public String getBregma() {
        return bregma;
    }

    public void setBregma(String bregma) {
        this.bregma = bregma;
    }

    public String getBregmaOther() {
        return bregmaOther;
    }

    public void setBregmaOther(String bregmaOther) {
        this.bregmaOther = bregmaOther;
    }

    public String getEyes() {
        return eyes;
    }

    public void setEyes(String eyes) {
        this.eyes = eyes;
    }

    public String getLimbs() {
        return limbs;
    }

    public void setLimbs(String limbs) {
        this.limbs = limbs;
    }

    public String getEarAppearance() {
        return earAppearance;
    }

    public void setEarAppearance(String earAppearance) {
        this.earAppearance = earAppearance;
    }

    public String getNeckBagPiece() {
        return neckBagPiece;
    }

    public void setNeckBagPiece(String neckBagPiece) {
        this.neckBagPiece = neckBagPiece;
    }

    public String getNose() {
        return nose;
    }

    public void setNose(String nose) {
        this.nose = nose;
    }

    public String getSkin() {
        return skin;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }

    public String getOral() {
        return oral;
    }

    public void setOral(String oral) {
        this.oral = oral;
    }

    public String getAnus() {
        return anus;
    }

    public void setAnus(String anus) {
        this.anus = anus;
    }

    public String getLungAuscultation() {
        return lungAuscultation;
    }

    public void setLungAuscultation(String lungAuscultation) {
        this.lungAuscultation = lungAuscultation;
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

    public String getSpine() {
        return spine;
    }

    public void setSpine(String spine) {
        this.spine = spine;
    }

    public String getGenitals() {
        return genitals;
    }

    public void setGenitals(String genitals) {
        this.genitals = genitals;
    }

    public String getUmbilicalCord() {
        return umbilicalCord;
    }

    public void setUmbilicalCord(String umbilicalCord) {
        this.umbilicalCord = umbilicalCord;
    }

    public String getUmbilicalCordOther() {
        return umbilicalCordOther;
    }

    public void setUmbilicalCordOther(String umbilicalCordOther) {
        this.umbilicalCordOther = umbilicalCordOther;
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

    public String getVisitTime() {
        return VisitTime;
    }

    public void setVisitTime(String visitTime) {
        VisitTime = visitTime;
    }

    public String getNextVisitAddress() {
        return nextVisitAddress;
    }

    public void setNextVisitAddress(String nextVisitAddress) {
        this.nextVisitAddress = nextVisitAddress;
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

    public String getReferralDept() {
        return referralDept;
    }

    public void setReferralDept(String referralDept) {
        this.referralDept = referralDept;
    }
}
