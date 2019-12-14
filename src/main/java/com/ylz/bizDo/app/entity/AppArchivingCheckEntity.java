package com.ylz.bizDo.app.entity;

import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;

/**
 * Created by zzl on 2018/11/8.
 */
public class AppArchivingCheckEntity {
    private String id;//主键
    private String archivingId;//外键
    private String signId;//签约单主键
    private String patientNo;// 用户编号
    private String familySize;// 家庭人口数
    private String povertyState;// 是否脱贫(0否 1是)
    private String patientName;// 姓名
    private String patientSex;// 性别
    private String patientBirthDay;// 出生日期
    private String patientIdno;// 身份证号
    private String householdRelationship;// 与户主的关系
    private String residenceState;// 有无户籍（0无 1有）
    private String fpaState;// 有无纳入计生管理（0无 1有）
    private String archivingCardState;// 有无纳入建档立卡人口（0无 1有）
    private String maritalStatus;// 婚姻状况（0未 1初 2再 3离 4丧偶）
    private String familyType;// 计生家庭户类型（1无孩 2一男 3一女 4二女 5一女一男 6一男一女 7三孩 8四孩 9五孩以上）
    private String patientCard;// 社会保障号
    private String patientJmda;// 居民健康档案号
    private String domicilePlaceCounty;// 户籍地（县）
    private String domicilePlaceTown;// 户籍地乡
    private String domicilePlaceVillage;// 户籍地村
    private String accDonicilePlace;//户籍地(县乡村）
    private String residencePlaceCounty;//  居住地县
    private String residencePlaceTown;// 居住地乡
    private String residencePlaceVillage;// 居住地村
    private String residencePlace;// 居住地(县乡村）
    private String residencePlaceAddr;// 居住地详细地址（门牌号）
    private String patientTel;// 联系电话
    private String serviceType;// 服务类型（1一般人群 2.0-6岁儿童 3孕产妇 4.65岁以上老年人 5高血压患者 6糖尿病患者 7严重精神障碍患者 8肺结核患者 9残疾）
    private String specialFamily;//是否计生特殊家庭（0否 1是）
    private String totalFee;//诊疗费用
    private String illnessName;//大病名称
    private String paperSignState;//纸质签约（0未签约 1已签约）
    private String signAgreementState;//有无签约协议（0无 1有）
    private String serviceHandbookState;// 有无服务手册(0无 1有)
    private String contactCardState;// 有无爱心服务卡（联络卡0无 1有）
    private String signFromDate;// 签约服务开始时间
    private String signToDate;// 签约服务结束时间
    private String notSignReason;// 未落实签约原因
    private String notSignReasonOther;// 未落实签约其它原因
    private String jmdaTime;// 居民健康档案时间
    private String jmdaLastUpdateTime;// 居民健康档案最后更新时间
    private String signPeNum;// 签约后健康体检次数
    private String lastPeTime;// 最后一次健康体检时间
    private String etFollowNum;// 儿童随访次数
    private String etLastFollowTime;// 儿童最后一次随访时间
    private String ycfFollowNum;// 孕产妇随访次数
    private String ycfLastFollowTime;// 孕产妇最后一次随访时间
    private String gxyNum;// 高血压患者测血压次数
    private String gxyLastTime;// 高血压患者最后一次测血压时间
    private String gxyFollowNum;// 高血压患者随访次数
    private String gxyLastFollowTime;// 高血压最后一次随访时间
    private String tnbNum;// 糖尿病测血糖次数
    private String tnbLastTime;// 糖尿病患者最后一次测血糖时间
    private String tnbFollowNum;// 糖尿病患者随访次数
    private String tnbLastFollowTime;// 糖尿病患者最后一次随访时间
    private String jsbFollowNum;// 严重精神障碍患者随访次数
    private String jsbLastFollowTime;// 严重精神障碍患者最后一次随访时间
    private String knowHelpPoorPolicy;// 是否知晓健康扶贫政策（0不知道 1知道 2有听说，但具体内容不了解）
    private String satisfiedState;// 对签约服务是否满意（1满意 2基本满意 3不满意）
    private String inspectorOneUrl;// 核查员签名（计生管理员）
    private String inspectorTwoUrl;// 核查员签名(医务人员)
    private String checkDate;// 核查日期
    private String fwType;//服务人群
    private String areaName;//建档立卡区县名字
    private String streetName;//建档立卡镇名字
    private String cunName;
    private String areaCode;//行政区划
    private String createId;//录入人主键
    private String createName;//录入人姓名
    private String checkYwDate;//核查日期（医务人员）
    private String objectType;//建档立卡所属对象

    private String totalCost;//总费用
    private String zfFee;//自付金额
    private String ncmsFee;//新农合报销
    private String civilAssistance;//民政补助
    private String sgcpa;//扶贫叠加保障补偿
    private String otherFund;//其他

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getArchivingId() {
        return archivingId;
    }

    public void setArchivingId(String archivingId) {
        this.archivingId = archivingId;
    }

    public String getPatientNo() {
        return patientNo;
    }

    public void setPatientNo(String patientNo) {
        this.patientNo = patientNo;
    }

    public String getFamilySize() {
        return familySize;
    }

    public void setFamilySize(String familySize) {
        this.familySize = familySize;
    }

    public String getPovertyState() {
        return povertyState;
    }

    public void setPovertyState(String povertyState) {
        this.povertyState = povertyState;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientSex() {
        return patientSex;
    }

    public void setPatientSex(String patientSex) {
        this.patientSex = patientSex;
    }

    public String getPatientBirthDay() {
        return patientBirthDay;
    }

    public void setPatientBirthDay(Timestamp patientBirthDay) {
        if(patientBirthDay != null){
            this.patientBirthDay = ExtendDate.getYMD(patientBirthDay);
        }else{
            this.patientBirthDay = "";
        }
    }

    public String getPatientIdno() {
        return patientIdno;
    }

    public void setPatientIdno(String patientIdno) {
        this.patientIdno = patientIdno;
    }

    public String getHouseholdRelationship() {
        return householdRelationship;
    }

    public void setHouseholdRelationship(String householdRelationship) {
        this.householdRelationship = householdRelationship;
    }

    public String getResidenceState() {
        return residenceState;
    }

    public void setResidenceState(String residenceState) {
        this.residenceState = residenceState;
    }

    public String getFpaState() {
        return fpaState;
    }

    public void setFpaState(String fpaState) {
        this.fpaState = fpaState;
    }

    public String getArchivingCardState() {
        return archivingCardState;
    }

    public void setArchivingCardState(String archivingCardState) {
        this.archivingCardState = archivingCardState;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getFamilyType() {
        return familyType;
    }

    public void setFamilyType(String familyType) {
        this.familyType = familyType;
    }

    public String getPatientCard() {
        return patientCard;
    }

    public void setPatientCard(String patientCard) {
        this.patientCard = patientCard;
    }

    public String getPatientJmda() {
        return patientJmda;
    }

    public void setPatientJmda(String patientJmda) {
        this.patientJmda = patientJmda;
    }

    public String getDomicilePlaceCounty() {
        return domicilePlaceCounty;
    }

    public void setDomicilePlaceCounty(String domicilePlaceCounty) {
        this.domicilePlaceCounty = domicilePlaceCounty;
    }

    public String getDomicilePlaceTown() {
        return domicilePlaceTown;
    }

    public void setDomicilePlaceTown(String domicilePlaceTown) {
        this.domicilePlaceTown = domicilePlaceTown;
    }

    public String getDomicilePlaceVillage() {
        return domicilePlaceVillage;
    }

    public void setDomicilePlaceVillage(String domicilePlaceVillage) {
        this.domicilePlaceVillage = domicilePlaceVillage;
    }

    public String getAccDonicilePlace() {
        return accDonicilePlace;
    }

    public void setAccDonicilePlace(String accDonicilePlace) {
        this.accDonicilePlace = accDonicilePlace;
    }

    public String getResidencePlaceCounty() {
        return residencePlaceCounty;
    }

    public void setResidencePlaceCounty(String residencePlaceCounty) {
        this.residencePlaceCounty = residencePlaceCounty;
    }

    public String getResidencePlaceTown() {
        return residencePlaceTown;
    }

    public void setResidencePlaceTown(String residencePlaceTown) {
        this.residencePlaceTown = residencePlaceTown;
    }

    public String getResidencePlaceVillage() {
        return residencePlaceVillage;
    }

    public void setResidencePlaceVillage(String residencePlaceVillage) {
        this.residencePlaceVillage = residencePlaceVillage;
    }

    public String getResidencePlace() {
        return residencePlace;
    }

    public void setResidencePlace(String residencePlace) {
        this.residencePlace = residencePlace;
    }

    public String getResidencePlaceAddr() {
        return residencePlaceAddr;
    }

    public void setResidencePlaceAddr(String residencePlaceAddr) {
        this.residencePlaceAddr = residencePlaceAddr;
    }

    public String getPatientTel() {
        return patientTel;
    }

    public void setPatientTel(String patientTel) {
        this.patientTel = patientTel;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getSpecialFamily() {
        return specialFamily;
    }

    public void setSpecialFamily(String specialFamily) {
        this.specialFamily = specialFamily;
    }

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public String getIllnessName() {
        return illnessName;
    }

    public void setIllnessName(String illnessName) {
        this.illnessName = illnessName;
    }

    public String getPaperSignState() {
        return paperSignState;
    }

    public void setPaperSignState(String paperSignState) {
        this.paperSignState = paperSignState;
    }

    public String getSignAgreementState() {
        return signAgreementState;
    }

    public void setSignAgreementState(String signAgreementState) {
        this.signAgreementState = signAgreementState;
    }

    public String getServiceHandbookState() {
        return serviceHandbookState;
    }

    public void setServiceHandbookState(String serviceHandbookState) {
        this.serviceHandbookState = serviceHandbookState;
    }

    public String getContactCardState() {
        return contactCardState;
    }

    public void setContactCardState(String contactCardState) {
        this.contactCardState = contactCardState;
    }

    public String getSignFromDate() {
        return signFromDate;
    }

    public void setSignFromDate(Timestamp signFromDate) {
        if(signFromDate != null){
            this.signFromDate = ExtendDate.getChineseYMD(signFromDate);
        }else{
            this.signFromDate = "";
        }
    }

    public String getSignToDate() {
        return signToDate;
    }

    public void setSignToDate(Timestamp signToDate) {
        if(signToDate != null){
            this.signToDate = ExtendDate.getChineseYMD(signToDate);
        }else {
            this.signToDate = "";
        }

    }

    public String getNotSignReason() {
        return notSignReason;
    }

    public void setNotSignReason(String notSignReason) {
        this.notSignReason = notSignReason;
    }

    public String getNotSignReasonOther() {
        return notSignReasonOther;
    }

    public void setNotSignReasonOther(String notSignReasonOther) {
        this.notSignReasonOther = notSignReasonOther;
    }

    public String getJmdaTime() {
        return jmdaTime;
    }

    public void setJmdaTime(Timestamp jmdaTime) {
        if(jmdaTime != null){
            this.jmdaTime = ExtendDate.getYMD_h_m_s(jmdaTime);
        }else{
            this.jmdaTime = "";
        }
    }

    public String getJmdaLastUpdateTime() {
        return jmdaLastUpdateTime;
    }

    public void setJmdaLastUpdateTime(Timestamp jmdaLastUpdateTime) {
        if(jmdaLastUpdateTime != null){
            this.jmdaLastUpdateTime = ExtendDate.getYMD_h_m_s(jmdaLastUpdateTime);
        }else{
            this.jmdaLastUpdateTime = "";
        }

    }

    public String getSignPeNum() {
        return signPeNum;
    }

    public void setSignPeNum(String signPeNum) {
        this.signPeNum = signPeNum;
    }

    public String getLastPeTime() {
        return lastPeTime;
    }

    public void setLastPeTime(Timestamp lastPeTime) {
        if(lastPeTime != null){
            this.lastPeTime = ExtendDate.getYMD_h_m_s(lastPeTime);
        }else{
            this.lastPeTime = "";
        }
    }

    public String getEtFollowNum() {
        return etFollowNum;
    }

    public void setEtFollowNum(String etFollowNum) {
        this.etFollowNum = etFollowNum;
    }

    public String getEtLastFollowTime() {
        return etLastFollowTime;
    }

    public void setEtLastFollowTime(Timestamp etLastFollowTime) {
        if(etLastFollowTime != null){
            this.etLastFollowTime = ExtendDate.getYMD_h_m_s(etLastFollowTime);
        }else{
            this.etLastFollowTime = "";
        }
    }

    public String getYcfFollowNum() {
        return ycfFollowNum;
    }

    public void setYcfFollowNum(String ycfFollowNum) {
        this.ycfFollowNum = ycfFollowNum;
    }

    public String getYcfLastFollowTime() {
        return ycfLastFollowTime;
    }

    public void setYcfLastFollowTime(Timestamp ycfLastFollowTime) {
        if(ycfLastFollowTime != null){
            this.ycfLastFollowTime = ExtendDate.getYMD_h_m_s(ycfLastFollowTime);
        }else{
            this.ycfLastFollowTime = "";
        }
    }

    public String getGxyNum() {
        return gxyNum;
    }

    public void setGxyNum(String gxyNum) {
        this.gxyNum = gxyNum;
    }

    public String getGxyLastTime() {
        return gxyLastTime;
    }

    public void setGxyLastTime(Timestamp gxyLastTime) {
        if(gxyLastTime != null){
            this.gxyLastTime = ExtendDate.getYMD_h_m_s(gxyLastTime);
        }else{
            this.gxyLastTime = "";
        }
    }

    public String getGxyFollowNum() {
        return gxyFollowNum;
    }

    public void setGxyFollowNum(String gxyFollowNum) {
        this.gxyFollowNum = gxyFollowNum;
    }

    public String getGxyLastFollowTime() {
        return gxyLastFollowTime;
    }

    public void setGxyLastFollowTime(Timestamp gxyLastFollowTime) {
        if(gxyLastFollowTime != null){
            this.gxyLastFollowTime = ExtendDate.getYMD_h_m_s(gxyLastFollowTime);
        }else{
            this.gxyLastFollowTime = "";
        }
    }

    public String getTnbNum() {
        return tnbNum;
    }

    public void setTnbNum(String tnbNum) {
        this.tnbNum = tnbNum;
    }

    public String getTnbLastTime() {
        return tnbLastTime;
    }

    public void setTnbLastTime(Timestamp tnbLastTime) {
        if(tnbLastTime != null){
            this.tnbLastTime = ExtendDate.getYMD_h_m_s(tnbLastTime);
        }else{
            this.tnbLastTime = "";
        }
    }

    public String getTnbFollowNum() {
        return tnbFollowNum;
    }

    public void setTnbFollowNum(String tnbFollowNum) {
        this.tnbFollowNum = tnbFollowNum;
    }

    public String getTnbLastFollowTime() {
        return tnbLastFollowTime;
    }

    public void setTnbLastFollowTime(Timestamp tnbLastFollowTime) {
        if(tnbLastFollowTime != null){
            this.tnbLastFollowTime = ExtendDate.getYMD_h_m_s(tnbLastFollowTime);
        }else{
            this.tnbLastFollowTime = "";
        }
    }

    public String getJsbFollowNum() {
        return jsbFollowNum;
    }

    public void setJsbFollowNum(String jsbFollowNum) {
        this.jsbFollowNum = jsbFollowNum;
    }

    public String getJsbLastFollowTime() {
        return jsbLastFollowTime;
    }

    public void setJsbLastFollowTime(Timestamp jsbLastFollowTime) {
        if(jsbLastFollowTime != null){
            this.jsbLastFollowTime = ExtendDate.getYMD_h_m_s(jsbLastFollowTime);
        }else{
            this.jsbLastFollowTime = "";
        }
    }

    public String getKnowHelpPoorPolicy() {
        return knowHelpPoorPolicy;
    }

    public void setKnowHelpPoorPolicy(String knowHelpPoorPolicy) {
        this.knowHelpPoorPolicy = knowHelpPoorPolicy;
    }

    public String getSatisfiedState() {
        return satisfiedState;
    }

    public void setSatisfiedState(String satisfiedState) {
        this.satisfiedState = satisfiedState;
    }

    public String getInspectorOneUrl() {
        return inspectorOneUrl;
    }

    public void setInspectorOneUrl(String inspectorOneUrl) {
        this.inspectorOneUrl = inspectorOneUrl;
    }

    public String getInspectorTwoUrl() {
        return inspectorTwoUrl;
    }

    public void setInspectorTwoUrl(String inspectorTwoUrl) {
        this.inspectorTwoUrl = inspectorTwoUrl;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Timestamp checkDate) {
        if(checkDate != null){
            this.checkDate = ExtendDate.getYMD(checkDate);
        }else{
            this.checkDate = "";
        }
    }

    public String getFwType() {
        return fwType;
    }

    public void setFwType(String fwType) {
        this.fwType = fwType;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCunName() {
        return cunName;
    }

    public void setCunName(String cunName) {
        this.cunName = cunName;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    public String getInspectorOneName() throws Exception{
        if(StringUtils.isNotBlank(this.getInspectorOneUrl())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode cdCode = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JSADMIN,this.getInspectorOneUrl());
            if(cdCode != null){
                return cdCode.getCodeTitle();
            }
        }
        return "";
    }
    public String getInspectorTwoName(){
        if(StringUtils.isNotBlank(this.getInspectorTwoUrl())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            AppDrUser drUser = (AppDrUser)dao.getServiceDo().find(AppDrUser.class,this.getInspectorTwoUrl());
            if(drUser != null){
                return drUser.getDrName();
            }
        }
        return "";
    }

    public String getCheckYwDate() {
        return checkYwDate;
    }

    public void setCheckYwDate(Timestamp checkYwDate) {
        if(checkYwDate != null){
            this.checkYwDate = ExtendDate.getYMD(checkYwDate);
        }else{
            this.checkYwDate = "";
        }
    }

    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) {
        this.signId = signId;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(String totalCost) {
        this.totalCost = totalCost;
    }

    public String getZfFee() {
        return zfFee;
    }

    public void setZfFee(String zfFee) {
        this.zfFee = zfFee;
    }

    public String getNcmsFee() {
        return ncmsFee;
    }

    public void setNcmsFee(String ncmsFee) {
        this.ncmsFee = ncmsFee;
    }

    public String getCivilAssistance() {
        return civilAssistance;
    }

    public void setCivilAssistance(String civilAssistance) {
        this.civilAssistance = civilAssistance;
    }

    public String getSgcpa() {
        return sgcpa;
    }

    public void setSgcpa(String sgcpa) {
        this.sgcpa = sgcpa;
    }

    public String getOtherFund() {
        return otherFund;
    }

    public void setOtherFund(String otherFund) {
        this.otherFund = otherFund;
    }
}
