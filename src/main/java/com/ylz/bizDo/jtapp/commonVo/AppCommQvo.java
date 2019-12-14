package com.ylz.bizDo.jtapp.commonVo;

import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.app.po.AppSignForm;
import com.ylz.bizDo.jtapp.patientEntity.AppPatientEntity;
import com.ylz.packcommon.common.CommConditionVo;

import java.util.List;

/**
 * Created by hzk on 2017/6/17.
 */
public class AppCommQvo extends CommConditionVo {
    private String drId;//医生id
    private String teamId;//团队id

    private String patientId;//患者id
    private String signPersGroup;//居民分组
    private String signHealthGroup;//健康分布
    private String labelGruops;//疾病类型接口 多分号隔开

    private String signFormId;//签约单id
    private String batchCreatePersid;//创建者主键
    private String signOthnerReason;//其他原因
    private String signUrrenderReason;//解约原因
    private String signUrrenderReasonPatient;//患者解约原因

    private String patientIds;//多患者


    private String signState;//签约状态 1:待签约 2:已签约 3:解约中 4:已解约 5:改签解约中 6:改签申请中 7:已退约 8拒签

    private String serviceA;//三师共管 1勾选
    private String serviceAred;//三师共管 红色 1勾选
    private String serviceAyellow;//三师共管 黄色 1勾选
    private String serviceAgreen;//三师共管 绿 1勾选
    private String serviceB;//居家养老 1勾选
    private String serviceBred;//居家养老 红色 1勾选
    private String serviceByellow;//居家养老 黄色 1勾选
    private String serviceBgreen;//居家养老 绿色 1勾选
    private String vip;
    private String expire;//到期
    private String patientName;//患者姓名

    private String signServiceBColor;//居家养老颜色
    private String serachValue;//模糊搜索
    private String renew;//是否续签 1是 0否
    private String payType;//政府补贴类型
    private String fee;//费用
    private String patientDrType;//类型

    private String signDelType;//删除类型
    private String signDelReason;//删除原因
    private String signDieDate;//死亡时间

    private String patientAddress;//地址
    private String patientProvince;//省
    private String patientCity;//市
    private String patientArea;//区
    private String patientStreet;//街道
    private String patientNeighborhoodCommittee;//居委会
    private String signsJjType;//经济类型
    private String[] signsJjTypes;//经济类型
    private String patientIdno;//身份证
    private String patientjmda;//居民健康档案
    private String patientjtda;//居民家庭档案
    private String signzfpay;//自费金额
    private String signDate;//签约时间
    private String signDateStart;//签约开始
    private String signDateEnd;//签约结束
    private String signlx;//医保类型 1医保 2 农合
    private String signDrAssistantId;//助理姓名
    private String signHospId;//医院主键
    private String signAreaCode;//行政区划
    private String signYear;//年度
    private String signgwpay;//公卫
    private String signybpay;//医保
    private String batchOperatorName;//操作人姓名
    private String[] persGroup;
    private String signpackageid;
    private String batchId;//签约批次id
    private String familysubpage;
    private String upHpis;//是否上传基卫 /1已上传 2数据来源web
    private String signPrintFlag; //协议状态 打印判断 0 未打 1 已打  2已再打
    private String signType;
    private String isReferral;//是否转诊
    private String signFromDateStart;//协议开始日期开始
    private String signFromDateEnd;//协议开始日期结束
    private String signToDateStart;//协议结束日期开始
    private String signToDateEnd;//协议结束日期结束

    private String signrenew;// 续签状态
    private String signUpHpis;//签约来源
    private String signatureImageUrl;//签名版
    private String signPhotoImageUrl;//摄像头
    private String openState;//开关 1打开查询团队签约 其他查询医生个人签约
    private String areaName;//区域全称
    private String areaSname;//区域简称
    private String payState;//缴费状态（1已缴费 0：未缴费,2已登记）

    private String dissolutionType;// 查询时判断 解约查询
    private String signWay; // //签约方式 0代表自己 1代表家人代签 2代表医生代签

    private List<AppPatientEntity> patientList;

    private String teamName;//团队名称
    private String economicType;//专门接收贫困人口经济类型
    private String signQyCount; // 续签
    private String signImageUrl;//图片路径
    private String sex;//性别
    private String birthday;//出生日期
    private String patientTel;//电话
    private String flagFileUp;//是否已上传附件(0否 1是)

    private String orgId;//机构id
    private String urlType;//1.福州,2泉州,3漳州,4.莆田,5.南平,6.三明,7.测试
    private String findLevel;//查询级别 1:省，2：市，3：区县，4：社区

    private String showHistory;// 是否显示历史（解约）签约单（1-是，0-否）
    private String showRenew;// 是否显示续签后未生效的签约单（1-是，0-否）

    private String warningDay;
    private String numberCount;
    private String batchOperatorId;//操作人id

    private String signHealthReportState;
    private String signUrrenderState;//是否过期
    private String signMobileType;//签约移动端类型（1安卓 2ios）
    private String signImageType;//是否开启上传签名(0不用,1需要)

    private String reason;// 解约原因（1-签约到期，自动解约，2-死亡，3-其他）

    private String patientCard;//医社保卡号
    private String signRegister;//医院对接表示、1表示跟医保对接用的

    public String getPatientCard() {
        return patientCard;
    }

    public void setPatientCard(String patientCard) {
        this.patientCard = patientCard;
    }

    public String getSignRegister() {
        return signRegister;
    }

    public void setSignRegister(String signRegister) {
        this.signRegister = signRegister;
    }

    public String getSignUrrenderState() {
        return signUrrenderState;
    }

    public void setSignUrrenderState(String signUrrenderState) {
        this.signUrrenderState = signUrrenderState;
    }

    public String getSignHealthReportState() {
        return signHealthReportState;
    }

    public void setSignHealthReportState(String signHealthReportState) {
        this.signHealthReportState = signHealthReportState;
    }

    public String getNumberCount() {
        return numberCount;
    }

    public void setNumberCount(String numberCount) {
        this.numberCount = numberCount;
    }

    public String getWarningDay() {
        return warningDay;
    }

    public void setWarningDay(String warningDay) {
        this.warningDay = warningDay;
    }

    public String getUrlType() {
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getEconomicType() {
        return economicType;
    }

    public void setEconomicType(String economicType) {
        this.economicType = economicType;
    }

    public List<AppPatientEntity> getPatientList() {
        return patientList;
    }

    public void setPatientList(List<AppPatientEntity> patientList) {
        this.patientList = patientList;
    }

    public String getBatchOperatorName() {
        return batchOperatorName;
    }

    public void setBatchOperatorName(String batchOperatorName) {
        this.batchOperatorName = batchOperatorName;
    }

    public String[] getPersGroup() {
        return persGroup;
    }

    public void setPersGroup(String[] persGroup) {
        this.persGroup = persGroup;
    }

    public String getSignHospId() {
        return signHospId;
    }

    public void setSignHospId(String signHospId) {
        this.signHospId = signHospId;
    }

    public String getSignDrAssistantId() {
        return signDrAssistantId;
    }

    public void setSignDrAssistantId(String signDrAssistantId) {
        this.signDrAssistantId = signDrAssistantId;
    }

    public String getSignlx() {
        return signlx;
    }

    public void setSignlx(String signlx) {
        this.signlx = signlx;
    }

    public String getSignDateStart() {
        return signDateStart;
    }

    public void setSignDateStart(String signDateStart) {
        this.signDateStart = signDateStart;
    }

    public String getSignDateEnd() {
        return signDateEnd;
    }

    public void setSignDateEnd(String signDateEnd) {
        this.signDateEnd = signDateEnd;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public String getSignzfpay() {
        return signzfpay;
    }

    public void setSignzfpay(String signzfpay) {
        this.signzfpay = signzfpay;
    }

    public String getPatientjmda() {
        return patientjmda;
    }

    public void setPatientjmda(String patientjmda) {
        this.patientjmda = patientjmda;
    }

    public String getPatientIdno() {
        return patientIdno;
    }

    public void setPatientIdno(String patientIdno) {
        this.patientIdno = patientIdno;
    }

    public String getSignServiceBColor() {
        return signServiceBColor;
    }

    public void setSignServiceBColor(String signServiceBColor) {
        this.signServiceBColor = signServiceBColor;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public String getSignState() {
        return signState;
    }

    public void setSignState(String signState) {
        this.signState = signState;
    }

    public String getBatchCreatePersid() {
        return batchCreatePersid;
    }

    public void setBatchCreatePersid(String batchCreatePersid) {
        this.batchCreatePersid = batchCreatePersid;
    }

    public String getSignUrrenderReasonPatient() {
        return signUrrenderReasonPatient;
    }

    public void setSignUrrenderReasonPatient(String signUrrenderReasonPatient) {
        this.signUrrenderReasonPatient = signUrrenderReasonPatient;
    }

    public String getSignOthnerReason() {
        return signOthnerReason;
    }

    public void setSignOthnerReason(String signOthnerReason) {
        this.signOthnerReason = signOthnerReason;
    }

    public String getSignUrrenderReason() {
        return signUrrenderReason;
    }

    public void setSignUrrenderReason(String signUrrenderReason) {
        this.signUrrenderReason = signUrrenderReason;
    }

    public String getLabelGruops() {
        return labelGruops;
    }

    public void setLabelGruops(String labelGruops) {
        this.labelGruops = labelGruops;
    }

    public String getSignFormId() {
        return signFormId;
    }

    public void setSignFormId(String signFormId) {
        this.signFormId = signFormId;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getSignPersGroup() {
        return signPersGroup;
    }

    public void setSignPersGroup(String signPersGroup) {
        this.signPersGroup = signPersGroup;
    }

    public String getSignHealthGroup() {
        return signHealthGroup;
    }

    public void setSignHealthGroup(String signHealthGroup) {
        this.signHealthGroup = signHealthGroup;
    }

    public String getPatientIds() {
        return patientIds;
    }

    public void setPatientIds(String patientIds) {
        this.patientIds = patientIds;
    }

    public String getServiceA() {
        return serviceA;
    }

    public void setServiceA(String serviceA) {
        this.serviceA = serviceA;
    }

    public String getServiceAred() {
        return serviceAred;
    }

    public void setServiceAred(String serviceAred) {
        this.serviceAred = serviceAred;
    }

    public String getServiceAyellow() {
        return serviceAyellow;
    }

    public void setServiceAyellow(String serviceAyellow) {
        this.serviceAyellow = serviceAyellow;
    }

    public String getServiceAgreen() {
        return serviceAgreen;
    }

    public void setServiceAgreen(String serviceAgreen) {
        this.serviceAgreen = serviceAgreen;
    }

    public String getServiceB() {
        return serviceB;
    }

    public void setServiceB(String serviceB) {
        this.serviceB = serviceB;
    }

    public String getServiceBred() {
        return serviceBred;
    }

    public void setServiceBred(String serviceBred) {
        this.serviceBred = serviceBred;
    }

    public String getServiceByellow() {
        return serviceByellow;
    }

    public void setServiceByellow(String serviceByellow) {
        this.serviceByellow = serviceByellow;
    }

    public String getServiceBgreen() {
        return serviceBgreen;
    }

    public void setServiceBgreen(String serviceBgreen) {
        this.serviceBgreen = serviceBgreen;
    }

    public String getSerachValue() {
        return serachValue;
    }

    public void setSerachValue(String serachValue) {
        this.serachValue = serachValue;
    }

    public String getRenew() {
        return renew;
    }

    public void setRenew(String renew) {
        this.renew = renew;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getPatientDrType() {
        return patientDrType;
    }

    public void setPatientDrType(String patientDrType) {
        this.patientDrType = patientDrType;
    }

    public String getSignDelType() {
        return signDelType;
    }

    public void setSignDelType(String signDelType) {
        this.signDelType = signDelType;
    }

    public String getSignDelReason() {
        return signDelReason;
    }

    public void setSignDelReason(String signDelReason) {
        this.signDelReason = signDelReason;
    }

    public String getSignDieDate() {
        return signDieDate;
    }

    public void setSignDieDate(String signDieDate) {
        this.signDieDate = signDieDate;
    }

    public String getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(String patientAddress) {
        this.patientAddress = patientAddress;
    }

    public String getPatientProvince() {
        return patientProvince;
    }

    public void setPatientProvince(String patientProvince) {
        this.patientProvince = patientProvince;
    }

    public String getPatientCity() {
        return patientCity;
    }

    public void setPatientCity(String patientCity) {
        this.patientCity = patientCity;
    }

    public String getPatientArea() {
        return patientArea;
    }

    public void setPatientArea(String patientArea) {
        this.patientArea = patientArea;
    }

    public String getPatientStreet() {
        return patientStreet;
    }

    public void setPatientStreet(String patientStreet) {
        this.patientStreet = patientStreet;
    }

    public String getPatientNeighborhoodCommittee() {
        return patientNeighborhoodCommittee;
    }

    public void setPatientNeighborhoodCommittee(String patientNeighborhoodCommittee) {
        this.patientNeighborhoodCommittee = patientNeighborhoodCommittee;
    }

    public String getSignsJjType() {
        return signsJjType;
    }

    public void setSignsJjType(String signsJjType) {
        this.signsJjType = signsJjType;
    }

    public String getSignAreaCode() {
        return signAreaCode;
    }

    public void setSignAreaCode(String signAreaCode) {
        this.signAreaCode = signAreaCode;
    }

    public String getSignYear() {
        return signYear;
    }

    public void setSignYear(String signYear) {
        this.signYear = signYear;
    }
    public String[] getSignsJjTypes() {
        return signsJjTypes;
    }

    public void setSignsJjTypes(String[] signsJjTypes) {
        this.signsJjTypes = signsJjTypes;
    }

    public String getSigngwpay() {
        return signgwpay;
    }

    public void setSigngwpay(String signgwpay) {
        this.signgwpay = signgwpay;
    }

    public String getSignybpay() {
        return signybpay;
    }

    public void setSignybpay(String signybpay) {
        this.signybpay = signybpay;
    }

    public String getSignpackageid() {
        return signpackageid;
    }

    public void setSignpackageid(String signpackageid) {
        this.signpackageid = signpackageid;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getFamilysubpage() {
        return familysubpage;
    }

    public void setFamilysubpage(String familysubpage) {
        this.familysubpage = familysubpage;
    }

    public String getUpHpis() {
        return upHpis;
    }

    public void setUpHpis(String upHpis) {
        this.upHpis = upHpis;
    }

    public String getSignPrintFlag() {
        return signPrintFlag;
    }

    public void setSignPrintFlag(String signPrintFlag) {
        this.signPrintFlag = signPrintFlag;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getIsReferral() {
        return isReferral;
    }

    public void setIsReferral(String isReferral) {
        this.isReferral = isReferral;
    }

    public String getSignFromDateStart() {
        return signFromDateStart;
    }

    public void setSignFromDateStart(String signFromDateStart) {
        this.signFromDateStart = signFromDateStart;
    }

    public String getSignFromDateEnd() {
        return signFromDateEnd;
    }

    public void setSignFromDateEnd(String signFromDateEnd) {
        this.signFromDateEnd = signFromDateEnd;
    }

    public String getSignToDateStart() {
        return signToDateStart;
    }

    public void setSignToDateStart(String signToDateStart) {
        this.signToDateStart = signToDateStart;
    }

    public String getSignToDateEnd() {
        return signToDateEnd;
    }

    public void setSignToDateEnd(String signToDateEnd) {
        this.signToDateEnd = signToDateEnd;
    }

    public String getPatientjtda() {
        return patientjtda;
    }

    public void setPatientjtda(String patientjtda) {
        this.patientjtda = patientjtda;
    }

    public String getSignrenew() {
        return signrenew;
    }

    public void setSignrenew(String signrenew) {
        this.signrenew = signrenew;
    }

    public String getSignUpHpis() {
        return signUpHpis;
    }

    public void setSignUpHpis(String signUpHpis) {
        this.signUpHpis = signUpHpis;
    }

    public String getSignatureImageUrl() {
        return signatureImageUrl;
    }

    public void setSignatureImageUrl(String signatureImageUrl) {
        this.signatureImageUrl = signatureImageUrl;
    }

    public String getOpenState() {
        return openState;
    }

    public void setOpenState(String openState) {
        this.openState = openState;
    }

    public String getAreaSname() {
        return areaSname;
    }

    public void setAreaSname(String areaSname) {
        this.areaSname = areaSname;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getPayState() {
        return payState;
    }

    public void setPayState(String payState) {
        this.payState = payState;
    }

    public String getDissolutionType() {
        return dissolutionType;
    }

    public void setDissolutionType(String dissolutionType) {
        this.dissolutionType = dissolutionType;
    }

    public String getSignPhotoImageUrl() {
        return signPhotoImageUrl;
    }

    public void setSignPhotoImageUrl(String signPhotoImageUrl) {
        this.signPhotoImageUrl = signPhotoImageUrl;
    }

    public String getSignWay() {
        return signWay;
    }

    public void setSignWay(String signWay) {
        this.signWay = signWay;
    }

    public String getSignQyCount() {
        return signQyCount;
    }

    public void setSignQyCount(String signQyCount) {
        this.signQyCount = signQyCount;
    }

    public String getSignImageUrl() {
        return signImageUrl;
    }

    public void setSignImageUrl(String signImageUrl) {
        this.signImageUrl = signImageUrl;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPatientTel() {
        return patientTel;
    }

    public void setPatientTel(String patientTel) {
        this.patientTel = patientTel;
    }

    public String getFlagFileUp() {
        return flagFileUp;
    }

    public void setFlagFileUp(String flagFileUp) {
        this.flagFileUp = flagFileUp;
    }

    public String getFindLevel() {
        return findLevel;
    }

    public void setFindLevel(String findLevel) {
        this.findLevel = findLevel;
    }

    public String getShowHistory() {
        return showHistory;
    }

    public void setShowHistory(String showHistory) {
        this.showHistory = showHistory;
    }

    public String getShowRenew() {
        return showRenew;
    }

    public void setShowRenew(String showRenew) {
        this.showRenew = showRenew;
    }

    public String getBatchOperatorId() {
        return batchOperatorId;
    }

    public void setBatchOperatorId(String batchOperatorId) {
        this.batchOperatorId = batchOperatorId;
    }

    public String getSignMobileType() {
        return signMobileType;
    }

    public void setSignMobileType(String signMobileType) {
        this.signMobileType = signMobileType;
    }

    public String getSignImageType() {
        return signImageType;
    }

    public void setSignImageType(String signImageType) {
        this.signImageType = signImageType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
