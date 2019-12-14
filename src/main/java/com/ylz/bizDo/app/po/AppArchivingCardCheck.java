package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**
 * 建档立卡贫困人口健康状况核查表
 * Created by zzl on 2018/11/6.
 */
@Entity
@Table(name = "APP_ARCHIVING_CARD_CHECK")
public class AppArchivingCardCheck extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name = "ACC_ARCHIVING_ID",length = 36)
    private String accArchivingId;//建档立卡外键
    @Column(name = "ACC_PATIENT_NO",length = 20)
    private String accPatientNo;// 用户编号
    @Column(name = "ACC_FAMILY_SIZE",length = 10)
    private String accFamilySize;// 家庭人口数
    @Column(name = "ACC_POVERTY_STATE",length = 10)
    private String accPovertyState;// 是否脱贫(0否 1是)
    @Column(name = "ACC_PATIENT_NAME",length = 50)
    private String accPatientName;// 姓名
    @Column(name = "ACC_PATIENT_SEX",length = 10)
    private String accPatientSex;// 性别
    @Column(name = "ACC_PATIENT_BIRTHDAY")
    private Calendar accPatientBirthDay;// 出生日期
    @Column(name = "ACC_PATIENT_IDNO",length = 20)
    private String accPatientIdno;// 身份证号
    @Column(name = "ACC_HOUSEHOLD_RELATIONSHIP",length = 100)
    private String accHouseholdRelationship;// 与户主的关系
    @Column(name = "ACC_RESIDENCE_STATE",length = 10)
    private String accResidenceState;// 有无户籍（0无 1有）
    @Column(name = "ACC_FPA_STATE",length = 10)
    private String accFpaState;// 有无纳入计生管理（0无 1有）
    @Column(name = "ACC_ARCHIVING_CARD_STATE",length = 10)
    private String accArchivingCardState;// 有无纳入建档立卡人口（0无 1有）
    @Column(name = "ACC_MARITAL_STATUS",length = 10)
    private String accMaritalStatus;// 婚姻状况（0未 1初 2再 3离 4丧偶）
    @Column(name = "ACC_FAMILY_TYPE",length = 10)
    private String accFamilyType;// 计生家庭户类型（1无孩 2一男 3一女 4二女 5一女一男 6一男一女 7三孩 8四孩 9五孩以上）
    @Column(name = "ACC_PATIENT_CARD",length = 20)
    private String accPatientCard;// 社会保障号
    @Column(name = "ACC_PATIENT_JMDA",length = 20)
    private String accPatientJmda;// 居民健康档案号
    @Column(name = "ACC_DOMICILE_PLACE_COUNTY",length = 12)
    private String accDomicilePlaceCounty;// 户籍地（县）
    @Column(name = "ACC_DOMICILE_PLACE_TOWN",length = 12)
    private String accDomicilePlaceTown;// 户籍地乡
    @Column(name = "ACC_DOMICILE_PLACE_VILLAGE",length = 12)
    private String accDomicilePlaceVillage;// 户籍地村
    @Column(name = "ACC_DOMICILE_PLACE",length = 255)
    private String accDonicilePlace;//户籍地(县乡村）

    @Column(name = "ACC_RESIDENCE_PLACE_COUNTY",length = 12)
    private String accResidencePlaceCounty;//  居住地县
    @Column(name = "ACC_RESIDENCE_PLACE_TOWN",length = 12)
    private String accResidencePlaceTown;// 居住地乡
    @Column(name = "ACC_RESIDENCE_PLACE_VILLAGE",length = 12)
    private String accResidencePlaceVillage;// 居住地村
    @Column(name = "ACC_RESIDENCE_PLACE",length = 255)
    private String accResidencePlace;// 居住地(县乡村）

    @Column(name = "ACC_RESIDENCE_PLACE_ADDR",length = 200)
    private String accResidencePlaceAddr;// 居住地详细地址（门牌号）
    @Column(name = "ACC_PATIENT_TEL",length = 20)
    private String accPatientTel;// 联系电话
    @Column(name = "ACC_SERVICE_TYPE",length = 10)
    private String accServiceType;// 服务类型（1一般人群 2.0-6岁儿童 3孕产妇 4.65岁以上老年人 5高血压患者 6糖尿病患者 7严重精神障碍患者 8肺结核患者 9残疾人 ）

    @Column(name = "ACC_SPECIAL_FAMILY",length = 10)
    private String accSpecialFamily;//是否计生特殊家庭（0否 1是）
    @Column(name = "ACC_TOTAL_FEE",length = 10)
    private String accTotalFee;//诊疗费用
    @Column(name = "ACC_ILLNESS_NAME",length = 10)
    private String accIllnessName;//大病名称
    @Column(name = "ACC_PAPER_SIGN_STATE",length = 10)
    private String accPaperSignState;//纸质签约（0未签约 1已签约）
    @Column(name = "ACC_SIGN_AGREEMENT_STATE",length = 10)
    private String accSignAgreementState;//有无签约协议（0无 1有）
    @Column(name = "ACC_SERVICE_HANDBOOK_STATE",length = 10)
    private String accServiceHandbookState;// 有无服务手册(0无 1有)
    @Column(name = "ACC_CONTACT_CARD_STATE",length = 10)
    private String accContactCardState;// 有无爱心服务卡（联络卡0无 1有）
    @Column(name = "ACC_SIGN_FROM_DATE")
    private Calendar accSignFromDate;// 签约服务开始时间
    @Column(name = "ACC_SIGN_TO_DATE")
    private Calendar accSignToDate;// 签约服务结束时间
    @Column(name = "ACC_NOT_SIGN_REASON",length = 10)
    private String accNotSignReason;// 未落实签约原因
    @Column(name = "ACC_NOT_SIGN_REASON_OTHER",length = 200)
    private String accNotSignReasonOther;// 未落实签约其它原因
    @Column(name = "ACC_JMDA_TIME")
    private Calendar accJmdaTime;// 居民健康档案时间
    @Column(name = "ACC_JMDA_LAST_UPDATE_TIME")
    private Calendar accJmdaLastUpdateTime;// 居民健康档案最后更新时间
    @Column(name = "ACC_SIGN_PE_NUM",length = 10)
    private String accSignPeNum;// 签约后健康体检次数
    @Column(name = "ACC_LAST_PE_TIME")
    private Calendar accLastPeTime;// 最后一次健康体检时间
    @Column(name = "ACC_ET_FOLLOW_NUM",length = 10)
    private String accEtFollowNum;// 儿童随访次数
    @Column(name = "ACC_ET_LAST_FOLLOW_TIME")
    private Calendar accEtLastFollowTime;// 儿童最后一次随访时间
    @Column(name = "ACC_YCF_FOLLOW_NUM",length = 10)
    private String accYcfFollowNum;// 孕产妇随访次数
    @Column(name = "ACC_YCF_LAST_FOLLOW_TIME")
    private Calendar accYcfLastFollowTime;// 孕产妇最后一次随访时间
    @Column(name = "ACC_GXY_NUM",length = 10)
    private String accGxyNum;// 高血压患者测血压次数
    @Column(name = "ACC_GXY_LAST_TIME")
    private Calendar accGxyLastTime;// 高血压患者最后一次测血压时间
    @Column(name = "ACC_GXY_FOLLOW_NUM",length = 10)
    private String accGxyFollowNum;// 高血压患者随访次数
    @Column(name = "ACC_GXY_LAST_FOLLOW_TIME")
    private Calendar accGxyLastFollowTime;// 高血压最后一次随访时间
    @Column(name = "ACC_TNB_NUM",length = 10)
    private String accTnbNum;// 糖尿病测血糖次数
    @Column(name = "ACC_TNB_LAST_TIME")
    private Calendar accTnbLastTime;// 糖尿病患者最后一次测血糖时间
    @Column(name = "ACC_TNB_FOLLOW_NUM",length = 10)
    private String accTnbFollowNum;// 糖尿病患者随访次数
    @Column(name = "ACC_TNB_LAST_FOLLOW_TIME")
    private Calendar accTnbLastFollowTime;// 糖尿病患者最后一次随访时间
    @Column(name = "ACC_JSB_FOLLOW_NUM",length = 10)
    private String accJsbFollowNum;// 严重精神障碍患者随访次数
    @Column(name = "ACC_JSB_LAST_FOLLOW_TIME")
    private Calendar accJsbLastFollowTime;// 严重精神障碍患者最后一次随访时间
    @Column(name = "ACC_KNOW_HELP_POOR_POLICY",length = 10)
    private String accKnowHelpPoorPolicy;// 是否知晓健康扶贫政策（0不知道 1知道 2有听说，但具体内容不了解）
    @Column(name = "ACC_SATISFIED_STATE",length = 10)
    private String accSatisfiedState;// 对签约服务是否满意（1满意 2基本满意 3不满意）
    @Column(name = "ACC_INSPECTOR_ONE_URL",length = 200)
    private String accInspectorOneUrl;// 核查员签名（计生管理员）
    @Column(name = "ACC_INSPECTOR_TWO_URL",length = 200)
    private String accInspectorTwoUrl;// 核查员签名(医务人员)
    @Column(name = "ACC_CHECK_DATE")
    private Calendar accCheckDate;// 核查日期(计生管理员)
    @Column(name = "ACC_CREATE_ID",length = 36)
    private String accCreateId;//创建人员
    @Column(name = "ACC_CREATE_NAME",length = 50)
    private String accCreateName;//创建人员姓名
    @Column(name = "ACC_CHECK_YW_DATE")
    private Calendar accCheckYwDate;//核查日期（医务人员）
    @Column(name = "ACC_SIGN_ID",length = 36)
    private String accSignId;//签约单主键
    @Column(name = "ACC_YEAR",length = 10)
    private String accYear;//签约年
    @Column(name = "ACC_YEAR_MONTH",length = 20)
    private String accYearMonth;//签约年月

    @Column(name = "ACC_OBJECT_TYPE",length = 10)
    private String accObjectType;//建档立卡所属对象
//    本年度在基层医疗机构救治资金构成情况
//    总费用、自付金额、新农合报销、民政补助、扶贫叠加保障补偿、其他
    @Column(name = "ACC_TOTAL_COST",length = 20)
    private String accTotalCost;//总费用
    @Column(name = "ACC_ZF_FEE",length = 20)
    private String accZfFee;//自付金额
    @Column(name = "ACC_NCMS_FEE",length = 20)
    private String accNcmsFee;//新农合报销
    @Column(name = "ACC_CIVIL_ASSISTANCE",length = 20)
    private String accCivilAssistance;//民政补助
    @Column(name = "ACC_SGCPA",length = 20)
    private String accSgcpa;//扶贫叠加保障补偿
    @Column(name = "ACC_OTHER_FUND")
    private String accOtherFund;//其他


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccArchivingId() {
        return accArchivingId;
    }

    public void setAccArchivingId(String accArchivingId) {
        this.accArchivingId = accArchivingId;
    }

    public String getAccPatientNo() {
        return accPatientNo;
    }

    public void setAccPatientNo(String accPatientNo) {
        this.accPatientNo = accPatientNo;
    }

    public String getAccFamilySize() {
        return accFamilySize;
    }

    public void setAccFamilySize(String accFamilySize) {
        this.accFamilySize = accFamilySize;
    }

    public String getAccPovertyState() {
        return accPovertyState;
    }

    public void setAccPovertyState(String accPovertyState) {
        this.accPovertyState = accPovertyState;
    }

    public String getAccPatientName() {
        return accPatientName;
    }

    public void setAccPatientName(String accPatientName) {
        this.accPatientName = accPatientName;
    }

    public String getAccPatientSex() {
        return accPatientSex;
    }

    public void setAccPatientSex(String accPatientSex) {
        this.accPatientSex = accPatientSex;
    }

    public Calendar getAccPatientBirthDay() {
        return accPatientBirthDay;
    }

    public void setAccPatientBirthDay(Calendar accPatientBirthDay) {
        this.accPatientBirthDay = accPatientBirthDay;
    }

    public String getAccPatientIdno() {
        return accPatientIdno;
    }

    public void setAccPatientIdno(String accPatientIdno) {
        this.accPatientIdno = accPatientIdno;
    }

    public String getAccHouseholdRelationship() {
        return accHouseholdRelationship;
    }

    public void setAccHouseholdRelationship(String accHouseholdRelationship) {
        this.accHouseholdRelationship = accHouseholdRelationship;
    }

    public String getAccResidenceState() {
        return accResidenceState;
    }

    public void setAccResidenceState(String accResidenceState) {
        this.accResidenceState = accResidenceState;
    }

    public String getAccFpaState() {
        return accFpaState;
    }

    public void setAccFpaState(String accFpaState) {
        this.accFpaState = accFpaState;
    }

    public String getAccArchivingCardState() {
        return accArchivingCardState;
    }

    public void setAccArchivingCardState(String accArchivingCardState) {
        this.accArchivingCardState = accArchivingCardState;
    }

    public String getAccMaritalStatus() {
        return accMaritalStatus;
    }

    public void setAccMaritalStatus(String accMaritalStatus) {
        this.accMaritalStatus = accMaritalStatus;
    }

    public String getAccFamilyType() {
        return accFamilyType;
    }

    public void setAccFamilyType(String accFamilyType) {
        this.accFamilyType = accFamilyType;
    }

    public String getAccPatientCard() {
        return accPatientCard;
    }

    public void setAccPatientCard(String accPatientCard) {
        this.accPatientCard = accPatientCard;
    }

    public String getAccPatientJmda() {
        return accPatientJmda;
    }

    public void setAccPatientJmda(String accPatientJmda) {
        this.accPatientJmda = accPatientJmda;
    }

    public String getAccDomicilePlaceCounty() {
        return accDomicilePlaceCounty;
    }

    public void setAccDomicilePlaceCounty(String accDomicilePlaceCounty) {
        this.accDomicilePlaceCounty = accDomicilePlaceCounty;
    }

    public String getAccDomicilePlaceTown() {
        return accDomicilePlaceTown;
    }

    public void setAccDomicilePlaceTown(String accDomicilePlaceTown) {
        this.accDomicilePlaceTown = accDomicilePlaceTown;
    }

    public String getAccDomicilePlaceVillage() {
        return accDomicilePlaceVillage;
    }

    public void setAccDomicilePlaceVillage(String accDomicilePlaceVillage) {
        this.accDomicilePlaceVillage = accDomicilePlaceVillage;
    }

    public String getAccResidencePlaceCounty() {
        return accResidencePlaceCounty;
    }

    public void setAccResidencePlaceCounty(String accResidencePlaceCounty) {
        this.accResidencePlaceCounty = accResidencePlaceCounty;
    }

    public String getAccResidencePlaceTown() {
        return accResidencePlaceTown;
    }

    public void setAccResidencePlaceTown(String accResidencePlaceTown) {
        this.accResidencePlaceTown = accResidencePlaceTown;
    }

    public String getAccResidencePlaceVillage() {
        return accResidencePlaceVillage;
    }

    public void setAccResidencePlaceVillage(String accResidencePlaceVillage) {
        this.accResidencePlaceVillage = accResidencePlaceVillage;
    }

    public String getAccResidencePlaceAddr() {
        return accResidencePlaceAddr;
    }

    public void setAccResidencePlaceAddr(String accResidencePlaceAddr) {
        this.accResidencePlaceAddr = accResidencePlaceAddr;
    }

    public String getAccPatientTel() {
        return accPatientTel;
    }

    public void setAccPatientTel(String accPatientTel) {
        this.accPatientTel = accPatientTel;
    }

    public String getAccServiceType() {
        return accServiceType;
    }

    public void setAccServiceType(String accServiceType) {
        this.accServiceType = accServiceType;
    }

    public String getAccSpecialFamily() {
        return accSpecialFamily;
    }

    public void setAccSpecialFamily(String accSpecialFamily) {
        this.accSpecialFamily = accSpecialFamily;
    }

    public String getAccTotalFee() {
        return accTotalFee;
    }

    public void setAccTotalFee(String accTotalFee) {
        this.accTotalFee = accTotalFee;
    }

    public String getAccIllnessName() {
        return accIllnessName;
    }

    public void setAccIllnessName(String accIllnessName) {
        this.accIllnessName = accIllnessName;
    }

    public String getAccPaperSignState() {
        return accPaperSignState;
    }

    public void setAccPaperSignState(String accPaperSignState) {
        this.accPaperSignState = accPaperSignState;
    }

    public String getAccSignAgreementState() {
        return accSignAgreementState;
    }

    public void setAccSignAgreementState(String accSignAgreementState) {
        this.accSignAgreementState = accSignAgreementState;
    }

    public String getAccServiceHandbookState() {
        return accServiceHandbookState;
    }

    public void setAccServiceHandbookState(String accServiceHandbookState) {
        this.accServiceHandbookState = accServiceHandbookState;
    }

    public String getAccContactCardState() {
        return accContactCardState;
    }

    public void setAccContactCardState(String accContactCardState) {
        this.accContactCardState = accContactCardState;
    }

    public Calendar getAccSignFromDate() {
        return accSignFromDate;
    }

    public void setAccSignFromDate(Calendar accSignFromDate) {
        this.accSignFromDate = accSignFromDate;
    }

    public Calendar getAccSignToDate() {
        return accSignToDate;
    }

    public void setAccSignToDate(Calendar accSignToDate) {
        this.accSignToDate = accSignToDate;
    }

    public String getAccNotSignReason() {
        return accNotSignReason;
    }

    public void setAccNotSignReason(String accNotSignReason) {
        this.accNotSignReason = accNotSignReason;
    }

    public String getAccNotSignReasonOther() {
        return accNotSignReasonOther;
    }

    public void setAccNotSignReasonOther(String accNotSignReasonOther) {
        this.accNotSignReasonOther = accNotSignReasonOther;
    }

    public Calendar getAccJmdaTime() {
        return accJmdaTime;
    }

    public void setAccJmdaTime(Calendar accJmdaTime) {
        this.accJmdaTime = accJmdaTime;
    }

    public Calendar getAccJmdaLastUpdateTime() {
        return accJmdaLastUpdateTime;
    }

    public void setAccJmdaLastUpdateTime(Calendar accJmdaLastUpdateTime) {
        this.accJmdaLastUpdateTime = accJmdaLastUpdateTime;
    }

    public String getAccSignPeNum() {
        return accSignPeNum;
    }

    public void setAccSignPeNum(String accSignPeNum) {
        this.accSignPeNum = accSignPeNum;
    }

    public Calendar getAccLastPeTime() {
        return accLastPeTime;
    }

    public void setAccLastPeTime(Calendar accLastPeTime) {
        this.accLastPeTime = accLastPeTime;
    }

    public String getAccEtFollowNum() {
        return accEtFollowNum;
    }

    public void setAccEtFollowNum(String accEtFollowNum) {
        this.accEtFollowNum = accEtFollowNum;
    }

    public Calendar getAccEtLastFollowTime() {
        return accEtLastFollowTime;
    }

    public void setAccEtLastFollowTime(Calendar accEtLastFollowTime) {
        this.accEtLastFollowTime = accEtLastFollowTime;
    }

    public String getAccYcfFollowNum() {
        return accYcfFollowNum;
    }

    public void setAccYcfFollowNum(String accYcfFollowNum) {
        this.accYcfFollowNum = accYcfFollowNum;
    }

    public Calendar getAccYcfLastFollowTime() {
        return accYcfLastFollowTime;
    }

    public void setAccYcfLastFollowTime(Calendar accYcfLastFollowTime) {
        this.accYcfLastFollowTime = accYcfLastFollowTime;
    }

    public String getAccGxyNum() {
        return accGxyNum;
    }

    public void setAccGxyNum(String accGxyNum) {
        this.accGxyNum = accGxyNum;
    }

    public Calendar getAccGxyLastTime() {
        return accGxyLastTime;
    }

    public void setAccGxyLastTime(Calendar accGxyLastTime) {
        this.accGxyLastTime = accGxyLastTime;
    }

    public String getAccGxyFollowNum() {
        return accGxyFollowNum;
    }

    public void setAccGxyFollowNum(String accGxyFollowNum) {
        this.accGxyFollowNum = accGxyFollowNum;
    }

    public Calendar getAccGxyLastFollowTime() {
        return accGxyLastFollowTime;
    }

    public void setAccGxyLastFollowTime(Calendar accGxyLastFollowTime) {
        this.accGxyLastFollowTime = accGxyLastFollowTime;
    }

    public String getAccTnbNum() {
        return accTnbNum;
    }

    public void setAccTnbNum(String accTnbNum) {
        this.accTnbNum = accTnbNum;
    }

    public Calendar getAccTnbLastTime() {
        return accTnbLastTime;
    }

    public void setAccTnbLastTime(Calendar accTnbLastTime) {
        this.accTnbLastTime = accTnbLastTime;
    }

    public String getAccTnbFollowNum() {
        return accTnbFollowNum;
    }

    public void setAccTnbFollowNum(String accTnbFollowNum) {
        this.accTnbFollowNum = accTnbFollowNum;
    }

    public Calendar getAccTnbLastFollowTime() {
        return accTnbLastFollowTime;
    }

    public void setAccTnbLastFollowTime(Calendar accTnbLastFollowTime) {
        this.accTnbLastFollowTime = accTnbLastFollowTime;
    }

    public String getAccJsbFollowNum() {
        return accJsbFollowNum;
    }

    public void setAccJsbFollowNum(String accJsbFollowNum) {
        this.accJsbFollowNum = accJsbFollowNum;
    }

    public Calendar getAccJsbLastFollowTime() {
        return accJsbLastFollowTime;
    }

    public void setAccJsbLastFollowTime(Calendar accJsbLastFollowTime) {
        this.accJsbLastFollowTime = accJsbLastFollowTime;
    }

    public String getAccKnowHelpPoorPolicy() {
        return accKnowHelpPoorPolicy;
    }

    public void setAccKnowHelpPoorPolicy(String accKnowHelpPoorPolicy) {
        this.accKnowHelpPoorPolicy = accKnowHelpPoorPolicy;
    }

    public String getAccSatisfiedState() {
        return accSatisfiedState;
    }

    public void setAccSatisfiedState(String accSatisfiedState) {
        this.accSatisfiedState = accSatisfiedState;
    }

    public String getAccInspectorOneUrl() {
        return accInspectorOneUrl;
    }

    public void setAccInspectorOneUrl(String accInspectorOneUrl) {
        this.accInspectorOneUrl = accInspectorOneUrl;
    }

    public String getAccInspectorTwoUrl() {
        return accInspectorTwoUrl;
    }

    public void setAccInspectorTwoUrl(String accInspectorTwoUrl) {
        this.accInspectorTwoUrl = accInspectorTwoUrl;
    }

    public Calendar getAccCheckDate() {
        return accCheckDate;
    }

    public void setAccCheckDate(Calendar accCheckDate) {
        this.accCheckDate = accCheckDate;
    }

    public String getAccDonicilePlace() {
        return accDonicilePlace;
    }

    public void setAccDonicilePlace(String accDonicilePlace) {
        this.accDonicilePlace = accDonicilePlace;
    }

    public String getAccResidencePlace() {
        return accResidencePlace;
    }

    public void setAccResidencePlace(String accResidencePlace) {
        this.accResidencePlace = accResidencePlace;
    }

    public String getAccCreateId() {
        return accCreateId;
    }

    public void setAccCreateId(String accCreateId) {
        this.accCreateId = accCreateId;
    }

    public String getAccCreateName() {
        return accCreateName;
    }

    public void setAccCreateName(String accCreateName) {
        this.accCreateName = accCreateName;
    }

    public Calendar getAccCheckYwDate() {
        return accCheckYwDate;
    }

    public void setAccCheckYwDate(Calendar accCheckYwDate) {
        this.accCheckYwDate = accCheckYwDate;
    }

    public String getAccSignId() {
        return accSignId;
    }

    public void setAccSignId(String accSignId) {
        this.accSignId = accSignId;
    }

    public String getAccYear() {
        return accYear;
    }

    public void setAccYear(String accYear) {
        this.accYear = accYear;
    }

    public String getAccYearMonth() {
        return accYearMonth;
    }

    public void setAccYearMonth(String accYearMonth) {
        this.accYearMonth = accYearMonth;
    }

    public String getAccObjectType() {
        return accObjectType;
    }

    public void setAccObjectType(String accObjectType) {
        this.accObjectType = accObjectType;
    }

    public String getAccTotalCost() {
        return accTotalCost;
    }

    public void setAccTotalCost(String accTotalCost) {
        this.accTotalCost = accTotalCost;
    }

    public String getAccZfFee() {
        return accZfFee;
    }

    public void setAccZfFee(String accZfFee) {
        this.accZfFee = accZfFee;
    }

    public String getAccNcmsFee() {
        return accNcmsFee;
    }

    public void setAccNcmsFee(String accNcmsFee) {
        this.accNcmsFee = accNcmsFee;
    }

    public String getAccCivilAssistance() {
        return accCivilAssistance;
    }

    public void setAccCivilAssistance(String accCivilAssistance) {
        this.accCivilAssistance = accCivilAssistance;
    }

    public String getAccSgcpa() {
        return accSgcpa;
    }

    public void setAccSgcpa(String accSgcpa) {
        this.accSgcpa = accSgcpa;
    }

    public String getAccOtherFund() {
        return accOtherFund;
    }

    public void setAccOtherFund(String accOtherFund) {
        this.accOtherFund = accOtherFund;
    }
}
