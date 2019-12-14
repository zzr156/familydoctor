package com.ylz.bizDo.plan.vo;

import java.util.List;

/** 通用随访请求参数
 * Created by zzl on 2017/11/28.
 */
public class AppGeneralQvo {
    private String id;
    private String genPatientId;//患者id
    private String genVisitId;//随访外键
    private String genDrId;//医生id
    private String genName;//姓名
    private String genCode;//编号
    private String genVisitWay;//随访方式
    private String genFollowDate;//创建时间
    private String genGender;//性别 1男 2女 9未说明的性别 0未知的性别
    private String genBirthDate;//出生日期
    private String genIdno;//身份证
    private String genWorkDept;//工作单位
    private String genPhoneNum;//本人电话
    private String genLxrName;//联系人姓名
    private String genLxrPhone;//联系人电话
    private String genPermanentType;//常住类型 1户籍 2非户籍
    private String genNational;//民族 01汉族 99少数民族
    private String getGenNationalName;//民族名称(少数民族)
    private String genBloodType;//血型 1.A型 2.B型 3.O型 4.AB型 5不详
    private String genRhValue;//RH值 1阴性 2阳性 3不详
    private String genEducationLevel;//文化程度 1研究生 2大学本科 3大学专科和专科学校 4中等专业学校 5技工学校 6高中 7初中 8小学 9文盲或半文盲 10不详
    private String genProfessional;//职业 0国家机关、党群组织、企业、事业单位负责人 1专业技术人员 2办事人员和有关人员 3商业、服务业人员 4农、林、牧、渔、水利业生产人员 5生产、运输设备操作人员及有关人员 6军人 7不便分类的其他从业人员 8无职业
    private String genMaritalStatus;//婚姻状况 1未婚 2已婚 3丧偶 4离婚 5未说明的婚姻状况
    private String genMedicalPayType;//医疗费用支付方式 1城镇职工基本医疗保险 2城镇居民基本医疗保险 3新型农村合作医疗 4贫困救助 5商业医疗保险 6全公费 7全自费 8其他
    private String genMedicalOtherPayType;//其他医疗费用支付方式
    private String genDrugAllergyHistory;//药物过敏史 1无 2青霉素 3磺胺 4链霉素 5其他
    private String genDrugAllergyOtherHistory;//其他药物过敏史
    private String genExposedHistory;//暴露史 1无 2化学品 3毒物 4射线
    //既往史 从表信息
    private String genSurgery;//手术 1无 2有
    private String genTrauma;//外伤 1无 2有
    private String genBloodTransfusion;//输血 1无 2有
    //家族史 1无 2高血压 3糖尿病 4冠心病 5慢性阻塞性肺疾病 6恶性肿瘤 7脑卒中 8严重精神障碍 9结核病 10肝炎 11先天畸形 12其他
    private String genFatherHistory;//父亲家族史
    private String genFatherOtherHistory;//父亲其他家族史
    private String genMotherHistory;//母亲家族史
    private String genMotherOtherHistory;//母亲其他家族史
    private String genPeersHistory;//兄弟姐妹家族史
    private String genPeersOtherHistory;//兄弟姐妹其他家族史
    private String genChildrenHisTory;//子女家族史
    private String genChildrenOtherHistory;//子女其他家族史
    private String genGeneticDisordersHistory;//遗传病史 1无 2有
    private String genGeneticDisordersName;//有遗传病史的疾病名称
    private String genDisability;//残疾情况 1无残疾 2视力残疾 3听力残疾 4语言残疾 5肢体残疾 6智力残疾 7精神残疾 8其他残疾
    //生活环境
    private String genKitchenSituation;//厨房排风设施 1无 2油烟机 3换气扇 4烟囱
    private String genFuelType;//燃料类型 1液化气 2煤 3天然气 4沼气 5柴火 6其他
    private String genToilet;//厕所 1卫生厕所 2一格或二格粪池式 3马桶 4露天粪坑 5简易棚厕
    private String genLivestockBar;//禽畜栏 1无 2单设 3室内 4室外
    private List<AppGeneralDiseaseQvo> disList;//既往病史疾病从表
    private List<AppGeneralDiseaseHisQvo> disHisList;//既往病史
    private String sfXaxis;//x轴
    private String sfYaxis;//y轴

    public String getGenVisitWay() {
        return genVisitWay;
    }

    public void setGenVisitWay(String genVisitWay) {
        this.genVisitWay = genVisitWay;
    }

    public String getGenFollowDate() {
        return genFollowDate;
    }

    public void setGenFollowDate(String genFollowDate) {
        this.genFollowDate = genFollowDate;
    }

    public String getSfXaxis() {
        return sfXaxis;
    }

    public void setSfXaxis(String sfXaxis) {
        this.sfXaxis = sfXaxis;
    }

    public String getSfYaxis() {
        return sfYaxis;
    }

    public void setSfYaxis(String sfYaxis) {
        this.sfYaxis = sfYaxis;
    }

    public String getGenDrId() {
        return genDrId;
    }

    public void setGenDrId(String genDrId) {
        this.genDrId = genDrId;
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

    public String getGenBirthDate() {
        return genBirthDate;
    }

    public void setGenBirthDate(String genBirthDate) {
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

    public List<AppGeneralDiseaseQvo> getDisList() {
        return disList;
    }

    public void setDisList(List<AppGeneralDiseaseQvo> disList) {
        this.disList = disList;
    }

    public List<AppGeneralDiseaseHisQvo> getDisHisList() {
        return disHisList;
    }

    public void setDisHisList(List<AppGeneralDiseaseHisQvo> disHisList) {
        this.disHisList = disHisList;
    }
}
