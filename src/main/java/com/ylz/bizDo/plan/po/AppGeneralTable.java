package com.ylz.bizDo.plan.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/** 通用随访表
 * Created by zzl on 2017/11/28.
 */
@Entity
@Table(name = "APP_GENERAL_TABLE")
public class AppGeneralTable extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;
    @Column(name = "GEN_PATIENT_ID",length = 36)
    private String genPatientId;//患者id
    @Column(name = "GEN_DR_ID",length = 36)
    private String genDrId;//医生id
    @Column(name = "GEN_FOLLOW_DATE")
    private Calendar genFollowDate;//随访日期
    @Column(name = "GEN_VISIT_ID",length = 36)
    private String genVisitId;//随访外键
    @Column(name = "GEN_VISIT_WAY",length = 2)
    private String genVisitWay;//随访方式
    @Column(name = "GEN_NAME",length = 50)
    private String genName;//姓名
    @Column(name = "GEN_CODE",length = 50)
    private String genCode;//编号
    @Column(name = "GEN_GENDER",length = 2)
    private String genGender;//性别 1男 2女 9未说明的性别 0未知的性别
    @Column(name = "GEN_BIRTHDATE")
    private Calendar genBirthDate;//出生日期
    @Column(name = "GEN_IDNO",length = 18)
    private String genIdno;//身份证
    @Column(name = "GEN_WORK_DEPT")
    private String genWorkDept;//工作单位
    @Column(name = "GEN_PHONE_NUM",length = 11)
    private String genPhoneNum;//本人电话
    @Column(name = "GEN_LXR_NAME",length = 50)
    private String genLxrName;//联系人姓名
    @Column(name = "GEN_LXR_PHONE",length = 11)
    private String genLxrPhone;//联系人电话
    @Column(name = "GEN_PERMANENT_TYPE",length = 2)
    private String genPermanentType;//常住类型 1户籍 2非户籍
    @Column(name = "GEN_NATIONAL",length = 4)
    private String genNational;//民族 01汉族 99少数民族
    @Column(name = "GEN_NATIONAL_NAME",length = 100)
    private String getGenNationalName;//民族名称(少数民族)
    @Column(name = "GEN_BLOOD_TYPE",length = 2)
    private String genBloodType;//血型 1.A型 2.B型 3.O型 4.AB型 5不详
    @Column(name = "GEN_RH_VALUE",length = 2)
    private String genRhValue;//RH值 1阴性 2阳性 3不详
    @Column(name = "GEN_EDUCATION_LEVEL",length = 2)
    private String genEducationLevel;//文化程度 1研究生 2大学本科 3大学专科和专科学校 4中等专业学校 5技工学校 6高中 7初中 8小学 9文盲或半文盲 10不详
    @Column(name = "GEN_PROFESSIONAL",length = 2)
    private String genProfessional;//职业 0国家机关、党群组织、企业、事业单位负责人 1专业技术人员 2办事人员和有关人员 3商业、服务业人员 4农、林、牧、渔、水利业生产人员 5生产、运输设备操作人员及有关人员 6军人 7不便分类的其他从业人员 8无职业
    @Column(name = "GEN_MARITAL_STATUS",length = 2)
    private String genMaritalStatus;//婚姻状况 1未婚 2已婚 3丧偶 4离婚 5未说明的婚姻状况
    @Column(name = "GEN_MEDICAL_PAY_TYPE",length = 10)
    private String genMedicalPayType;//医疗费用支付方式 1城镇职工基本医疗保险 2城镇居民基本医疗保险 3新型农村合作医疗 4贫困救助 5商业医疗保险 6全公费 7全自费 8其他
    @Column(name = "GEN_MEDICAL_OTHER_PAY_TYPE",length = 100)
    private String genMedicalOtherPayType;//其他医疗费用支付方式
    @Column(name = "GEN_DRUG_ALLERGY_HISTORY",length = 10)
    private String genDrugAllergyHistory;//药物过敏史 1无 2青霉素 3磺胺 4链霉素 5其他
    @Column(name = "GEN_DRUG_ALLERGY_OTHER_HISTORY",length = 100)
    private String genDrugAllergyOtherHistory;//其他药物过敏史
    @Column(name = "GEN_EXPOSED_HISTORY",length = 2)
    private String genExposedHistory;//暴露史 1无 2化学品 3毒物 4射线
    //既往史 从表信息
    @Column(name = "GEN_SURGERY",length = 2)
    private String genSurgery;//手术 1无 2有
    @Column(name = "GEN_TRAUMA",length = 2)
    private String genTrauma;//外伤 1无 2有
    @Column(name = "GEN_BLOOD_TRANSFUSION",length = 2)
    private String genBloodTransfusion;//输血 1无 2有
    //家族史 1无 2高血压 3糖尿病 4冠心病 5慢性阻塞性肺疾病 6恶性肿瘤 7脑卒中 8严重精神障碍 9结核病 10肝炎 11先天畸形 12其他
    @Column(name = "GEN_FATHER_HISTORY",length = 10)
    private String genFatherHistory;//父亲家族史
    @Column(name = "GEN_FATHER_OTHER_HISTORY",length = 100)
    private String genFatherOtherHistory;//父亲其他家族史
    @Column(name = "GEN_MOTHER_HISTORY",length = 10)
    private String genMotherHistory;//母亲家族史
    @Column(name = "GEN_MOTHER_OTHER_HISTORY",length = 100)
    private String genMotherOtherHistory;//母亲其他家族史
    @Column(name = "GEN_PEERS_HISTORY",length = 10)
    private String genPeersHistory;//兄弟姐妹家族史
    @Column(name = "GEN_PEERS_OTHER_HISTORY",length = 100)
    private String genPeersOtherHistory;//兄弟姐妹其他家族史
    @Column(name = "GEN_CHILDREN_HISTORY",length = 10)
    private String genChildrenHisTory;//子女家族史
    @Column(name = "GEN_CHILDREN_OTHER_HISTORY",length = 100)
    private String genChildrenOtherHistory;//子女其他家族史
    @Column(name = "GEN_GENETIC_DISORDERS_HISTORY",length = 10)
    private String genGeneticDisordersHistory;//遗传病史 1无 2有
    @Column(name = "GEN_GENETIC_DISORDERS_NAME",length = 100)
    private String genGeneticDisordersName;//遗传病名称
    @Column(name = "GEN_DISABILITY",length = 10)
    private String genDisability;//残疾情况 1无残疾 2视力残疾 3听力残疾 4语言残疾 5肢体残疾 6智力残疾 7精神残疾 8其他残疾
    //生活环境
    @Column(name = "GEN_KITCHEN_SITUATION",length = 2)
    private String genKitchenSituation;//厨房排风设施 1无 2油烟机 3换气扇 4烟囱
    @Column(name = "GEN_FUEL_TYPE",length = 2)
    private String genFuelType;//燃料类型 1液化气 2煤 3天然气 4沼气 5柴火 6其他
    @Column(name = "GEN_TOILET",length = 2)
    private String genToilet;//厕所 1卫生厕所 2一格或二格粪池式 3马桶 4露天粪坑 5简易棚厕
    @Column(name = "GEN_LIVESTOCK_BAR",length = 2)
    private String genLivestockBar;//禽畜栏 1无 2单设 3室内 4室外

    public String getGenVisitWay() {
        return genVisitWay;
    }

    public void setGenVisitWay(String genVisitWay) {
        this.genVisitWay = genVisitWay;
    }

    public String getGenDrId() {
        return genDrId;
    }

    public void setGenDrId(String genDrId) {
        this.genDrId = genDrId;
    }

    public Calendar getGenFollowDate() {
        return genFollowDate;
    }

    public void setGenFollowDate(Calendar genFollowDate) {
        this.genFollowDate = genFollowDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGenPatientId() {
        return genPatientId;
    }

    public void setGenPatientId(String genPatientId) {
        this.genPatientId = genPatientId;
    }

    public String getGenVisitId() {
        return genVisitId;
    }

    public void setGenVisitId(String genVisitId) {
        this.genVisitId = genVisitId;
    }

    public String getGenName() {
        return genName;
    }

    public void setGenName(String genName) {
        this.genName = genName;
    }

    public String getGenCode() {
        return genCode;
    }

    public void setGenCode(String genCode) {
        this.genCode = genCode;
    }

    public String getGenGender() {
        return genGender;
    }

    public void setGenGender(String genGender) {
        this.genGender = genGender;
    }

    public Calendar getGenBirthDate() {
        return genBirthDate;
    }

    public void setGenBirthDate(Calendar genBirthDate) {
        this.genBirthDate = genBirthDate;
    }

    public String getGenIdno() {
        return genIdno;
    }

    public void setGenIdno(String genIdno) {
        this.genIdno = genIdno;
    }

    public String getGenWorkDept() {
        return genWorkDept;
    }

    public void setGenWorkDept(String genWorkDept) {
        this.genWorkDept = genWorkDept;
    }

    public String getGenPhoneNum() {
        return genPhoneNum;
    }

    public void setGenPhoneNum(String genPhoneNum) {
        this.genPhoneNum = genPhoneNum;
    }

    public String getGenLxrName() {
        return genLxrName;
    }

    public void setGenLxrName(String genLxrName) {
        this.genLxrName = genLxrName;
    }

    public String getGenLxrPhone() {
        return genLxrPhone;
    }

    public void setGenLxrPhone(String genLxrPhone) {
        this.genLxrPhone = genLxrPhone;
    }

    public String getGenPermanentType() {
        return genPermanentType;
    }

    public void setGenPermanentType(String genPermanentType) {
        this.genPermanentType = genPermanentType;
    }

    public String getGenNational() {
        return genNational;
    }

    public void setGenNational(String genNational) {
        this.genNational = genNational;
    }

    public String getGetGenNationalName() {
        return getGenNationalName;
    }

    public void setGetGenNationalName(String getGenNationalName) {
        this.getGenNationalName = getGenNationalName;
    }

    public String getGenBloodType() {
        return genBloodType;
    }

    public void setGenBloodType(String genBloodType) {
        this.genBloodType = genBloodType;
    }

    public String getGenRhValue() {
        return genRhValue;
    }

    public void setGenRhValue(String genRhValue) {
        this.genRhValue = genRhValue;
    }

    public String getGenEducationLevel() {
        return genEducationLevel;
    }

    public void setGenEducationLevel(String genEducationLevel) {
        this.genEducationLevel = genEducationLevel;
    }

    public String getGenProfessional() {
        return genProfessional;
    }

    public void setGenProfessional(String genProfessional) {
        this.genProfessional = genProfessional;
    }

    public String getGenMaritalStatus() {
        return genMaritalStatus;
    }

    public void setGenMaritalStatus(String genMaritalStatus) {
        this.genMaritalStatus = genMaritalStatus;
    }

    public String getGenMedicalPayType() {
        return genMedicalPayType;
    }

    public void setGenMedicalPayType(String genMedicalPayType) {
        this.genMedicalPayType = genMedicalPayType;
    }

    public String getGenMedicalOtherPayType() {
        return genMedicalOtherPayType;
    }

    public void setGenMedicalOtherPayType(String genMedicalOtherPayType) {
        this.genMedicalOtherPayType = genMedicalOtherPayType;
    }

    public String getGenDrugAllergyHistory() {
        return genDrugAllergyHistory;
    }

    public void setGenDrugAllergyHistory(String genDrugAllergyHistory) {
        this.genDrugAllergyHistory = genDrugAllergyHistory;
    }

    public String getGenDrugAllergyOtherHistory() {
        return genDrugAllergyOtherHistory;
    }

    public void setGenDrugAllergyOtherHistory(String genDrugAllergyOtherHistory) {
        this.genDrugAllergyOtherHistory = genDrugAllergyOtherHistory;
    }

    public String getGenExposedHistory() {
        return genExposedHistory;
    }

    public void setGenExposedHistory(String genExposedHistory) {
        this.genExposedHistory = genExposedHistory;
    }

    public String getGenSurgery() {
        return genSurgery;
    }

    public void setGenSurgery(String genSurgery) {
        this.genSurgery = genSurgery;
    }

    public String getGenTrauma() {
        return genTrauma;
    }

    public void setGenTrauma(String genTrauma) {
        this.genTrauma = genTrauma;
    }

    public String getGenBloodTransfusion() {
        return genBloodTransfusion;
    }

    public void setGenBloodTransfusion(String genBloodTransfusion) {
        this.genBloodTransfusion = genBloodTransfusion;
    }

    public String getGenFatherHistory() {
        return genFatherHistory;
    }

    public void setGenFatherHistory(String genFatherHistory) {
        this.genFatherHistory = genFatherHistory;
    }

    public String getGenFatherOtherHistory() {
        return genFatherOtherHistory;
    }

    public void setGenFatherOtherHistory(String genFatherOtherHistory) {
        this.genFatherOtherHistory = genFatherOtherHistory;
    }

    public String getGenMotherHistory() {
        return genMotherHistory;
    }

    public void setGenMotherHistory(String genMotherHistory) {
        this.genMotherHistory = genMotherHistory;
    }

    public String getGenMotherOtherHistory() {
        return genMotherOtherHistory;
    }

    public void setGenMotherOtherHistory(String genMotherOtherHistory) {
        this.genMotherOtherHistory = genMotherOtherHistory;
    }

    public String getGenPeersHistory() {
        return genPeersHistory;
    }

    public void setGenPeersHistory(String genPeersHistory) {
        this.genPeersHistory = genPeersHistory;
    }

    public String getGenPeersOtherHistory() {
        return genPeersOtherHistory;
    }

    public void setGenPeersOtherHistory(String genPeersOtherHistory) {
        this.genPeersOtherHistory = genPeersOtherHistory;
    }

    public String getGenChildrenHisTory() {
        return genChildrenHisTory;
    }

    public void setGenChildrenHisTory(String genChildrenHisTory) {
        this.genChildrenHisTory = genChildrenHisTory;
    }

    public String getGenChildrenOtherHistory() {
        return genChildrenOtherHistory;
    }

    public void setGenChildrenOtherHistory(String genChildrenOtherHistory) {
        this.genChildrenOtherHistory = genChildrenOtherHistory;
    }

    public String getGenDisability() {
        return genDisability;
    }

    public void setGenDisability(String genDisability) {
        this.genDisability = genDisability;
    }

    public String getGenKitchenSituation() {
        return genKitchenSituation;
    }

    public void setGenKitchenSituation(String genKitchenSituation) {
        this.genKitchenSituation = genKitchenSituation;
    }

    public String getGenFuelType() {
        return genFuelType;
    }

    public void setGenFuelType(String genFuelType) {
        this.genFuelType = genFuelType;
    }

    public String getGenToilet() {
        return genToilet;
    }

    public void setGenToilet(String genToilet) {
        this.genToilet = genToilet;
    }

    public String getGenLivestockBar() {
        return genLivestockBar;
    }

    public void setGenLivestockBar(String genLivestockBar) {
        this.genLivestockBar = genLivestockBar;
    }

    public String getGenGeneticDisordersHistory() {
        return genGeneticDisordersHistory;
    }

    public void setGenGeneticDisordersHistory(String genGeneticDisordersHistory) {
        this.genGeneticDisordersHistory = genGeneticDisordersHistory;
    }

    public String getGenGeneticDisordersName() {
        return genGeneticDisordersName;
    }

    public void setGenGeneticDisordersName(String genGeneticDisordersName) {
        this.genGeneticDisordersName = genGeneticDisordersName;
    }
}
