package com.ylz.bizDo.web.vo;



/**
 * 签约接口
 */
public class WebSignUpVo {
    private String areaCodeProvince;//行政区划(省)
    private String areaCodeCity;//行政区划（市）
    //医院信息 根据医院id查询是否存在，没有自动创建医院
    private String hospId;//医院主键
    private String hospName;//医院名称
    private String hospAreaCode;//区域编码
    private String hospAddress;//医院地址
    private String hospTel;//医院电话
    //医生信息 根据医生id查询是否存在，没有自动创建医生
    private String drId;//医生主键
    private String drName;//医生名称
    private String drAccount;//医生账号
    private String drPwd;//医生密码
    private String drGender;//医生性别
    private String drTel;//医生电话
    private String drIdNo;//身份证
    private String drOperatorId;//操作人医生主键
    private String drOperatorName;//操作人医生名称
    private String drOperatorAccount;//操作人医生账号
    private String drOperatorPwd;//操作人医生密码
    private String drOperatorGender;//操作人医生性别
    private String drOperatorTel;//操作人医生电话
    private String drOperatorIdNo;//操作人身份证
    private String hospOperatorId;//医院主键

    private String drAssistantId;//助理医生主键
    private String drAssistantName;//助理医生名称
    private String drAssistantAccount;//助理医生账号
    private String drAssistantPwd;//助理医生密码
    private String drAssistantGender;//助理医生性别
    private String drAssistantTel;//助理医生电话
    private String drAssistantIdNo;//操作人身份证
    private String hospAssistantId;//助理医院主键


    private String memState;// 团队角色 0：队长 1：成员
    private String signDrAssistantId;
    private String signDrAssistantTel;
    private  String signDrAssistantName;

    //团队 医生id查询是否已有团队 没有就根据上传的团队名称自动创建团队
    private String teamId;//团队id
    private String teamName;//团队名称
    //患者 根据患者身份证查询是否存在，没有自动创建患者 患者
    private String patientId;//患者id
    private String patientName;//患者名字
    private String patientGender;//患者性别
    private String patientIdno;//患者身份证号
    private String patientCard;//患者社保卡
    private String patientTel;//患者电话
    private String patientAddress;//患者地址
    private String patientPwd;//患者密码
    private String patientAge;//年纪
    private String signlx;//签约医保类型
    private String patientjtda;//居民家庭档案
    private String patientjmda;//居民建康档案
    private String ptsignPk;
    private String patientProvince;//省
    private String patientCity;//市
    private String patientArea;//区
    private String patientStreet;//街道
    private String patientNeighborhoodCommittee;//居委会

    //签约
    private String signDate;//签约时间
    private String signFromDate;//有效开始时间
    private String signToDate;//有效结束时间
    private String signPayState;//缴费状态 1已缴费 0：未缴费
    private String signType;//签约类型 //1家庭签约 2 vip
    private String signPersGroup;//服务人群 1普通人群 2儿童(0-6岁) 3孕产妇 4老年人 5高血压 6糖尿病 7严重精神障碍 8结核病 99未知
    private String signsJjType;//经济类型
    private String signHealth;//健康情况
    private String signDiseaseGroup;//疾病类型
    private String signczpay;//财政补贴
    private String signzfpay;//自费
    private String signState;//签约状态

    private String signId;//签约单id
    private String teamMerType;//添加或删除成员类型（1添加 2删除）
    private String signDelType;//删除类型 1,死亡,2其他
    private String signDelReason;//删除原因
    private String signDelDate;//删除时间
    private String changeDate;//变更时间
    private String signFormId;//签约单id
    private String reason;//转签原因
    private String sersmValue;//服务包值

    public String getAreaCodeProvince() {
        return areaCodeProvince;
    }

    public void setAreaCodeProvince(String areaCodeProvince) {
        this.areaCodeProvince = areaCodeProvince;
    }

    public String getAreaCodeCity() {
        return areaCodeCity;
    }

    public void setAreaCodeCity(String areaCodeCity) {
        this.areaCodeCity = areaCodeCity;
    }

    public String getHospId() {
        return hospId;
    }

    public void setHospId(String hospId) {
        this.hospId = hospId;
    }

    public String getHospName() {
        return hospName;
    }

    public void setHospName(String hospName) {
        this.hospName = hospName;
    }

    public String getHospAreaCode() {
        return hospAreaCode;
    }

    public void setHospAreaCode(String hospAreaCode) {
        this.hospAreaCode = hospAreaCode;
    }

    public String getHospAddress() {
        return hospAddress;
    }

    public void setHospAddress(String hospAddress) {
        this.hospAddress = hospAddress;
    }

    public String getHospTel() {
        return hospTel;
    }

    public void setHospTel(String hospTel) {
        this.hospTel = hospTel;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public String getDrAccount() {
        return drAccount;
    }

    public void setDrAccount(String drAccount) {
        this.drAccount = drAccount;
    }

    public String getDrPwd() {
        return drPwd;
    }

    public void setDrPwd(String drPwd) {
        this.drPwd = drPwd;
    }

    public String getDrGender() {
        return drGender;
    }

    public void setDrGender(String drGender) {
        this.drGender = drGender;
    }

    public String getDrTel() {
        return drTel;
    }

    public void setDrTel(String drTel) {
        this.drTel = drTel;
    }

    public String getDrOperatorId() {
        return drOperatorId;
    }

    public void setDrOperatorId(String drOperatorId) {
        this.drOperatorId = drOperatorId;
    }

    public String getDrOperatorName() {
        return drOperatorName;
    }

    public void setDrOperatorName(String drOperatorName) {
        this.drOperatorName = drOperatorName;
    }

    public String getDrOperatorAccount() {
        return drOperatorAccount;
    }

    public void setDrOperatorAccount(String drOperatorAccount) {
        this.drOperatorAccount = drOperatorAccount;
    }

    public String getDrOperatorPwd() {
        return drOperatorPwd;
    }

    public void setDrOperatorPwd(String drOperatorPwd) {
        this.drOperatorPwd = drOperatorPwd;
    }

    public String getDrOperatorGender() {
        return drOperatorGender;
    }

    public void setDrOperatorGender(String drOperatorGender) {
        this.drOperatorGender = drOperatorGender;
    }

    public String getDrOperatorTel() {
        return drOperatorTel;
    }

    public void setDrOperatorTel(String drOperatorTel) {
        this.drOperatorTel = drOperatorTel;
    }

    public String getHospOperatorId() {
        return hospOperatorId;
    }

    public void setHospOperatorId(String hospOperatorId) {
        this.hospOperatorId = hospOperatorId;
    }

    public String getDrAssistantId() {
        return drAssistantId;
    }

    public void setDrAssistantId(String drAssistantId) {
        this.drAssistantId = drAssistantId;
    }

    public String getDrAssistantName() {
        return drAssistantName;
    }

    public void setDrAssistantName(String drAssistantName) {
        this.drAssistantName = drAssistantName;
    }

    public String getDrAssistantAccount() {
        return drAssistantAccount;
    }

    public void setDrAssistantAccount(String drAssistantAccount) {
        this.drAssistantAccount = drAssistantAccount;
    }

    public String getDrAssistantPwd() {
        return drAssistantPwd;
    }

    public void setDrAssistantPwd(String drAssistantPwd) {
        this.drAssistantPwd = drAssistantPwd;
    }

    public String getDrAssistantGender() {
        return drAssistantGender;
    }

    public void setDrAssistantGender(String drAssistantGender) {
        this.drAssistantGender = drAssistantGender;
    }

    public String getDrAssistantTel() {
        return drAssistantTel;
    }

    public void setDrAssistantTel(String drAssistantTel) {
        this.drAssistantTel = drAssistantTel;
    }

    public String getHospAssistantId() {
        return hospAssistantId;
    }

    public void setHospAssistantId(String hospAssistantId) {
        this.hospAssistantId = hospAssistantId;
    }

    public String getMemState() {
        return memState;
    }

    public void setMemState(String memState) {
        this.memState = memState;
    }

    public String getSignDrAssistantId() {
        return signDrAssistantId;
    }

    public void setSignDrAssistantId(String signDrAssistantId) {
        this.signDrAssistantId = signDrAssistantId;
    }

    public String getSignDrAssistantTel() {
        return signDrAssistantTel;
    }

    public void setSignDrAssistantTel(String signDrAssistantTel) {
        this.signDrAssistantTel = signDrAssistantTel;
    }

    public String getSignDrAssistantName() {
        return signDrAssistantName;
    }

    public void setSignDrAssistantName(String signDrAssistantName) {
        this.signDrAssistantName = signDrAssistantName;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(String patientGender) {
        this.patientGender = patientGender;
    }

    public String getPatientIdno() {
        return patientIdno;
    }

    public void setPatientIdno(String patientIdno) {
        this.patientIdno = patientIdno;
    }

    public String getPatientCard() {
        return patientCard;
    }

    public void setPatientCard(String patientCard) {
        this.patientCard = patientCard;
    }

    public String getPatientTel() {
        return patientTel;
    }

    public void setPatientTel(String patientTel) {
        this.patientTel = patientTel;
    }

    public String getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(String patientAddress) {
        this.patientAddress = patientAddress;
    }

    public String getPatientPwd() {
        return patientPwd;
    }

    public void setPatientPwd(String patientPwd) {
        this.patientPwd = patientPwd;
    }

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(String patientAge) {
        this.patientAge = patientAge;
    }

    public String getSignlx() {
        return signlx;
    }

    public void setSignlx(String signlx) {
        this.signlx = signlx;
    }

    public String getPatientjtda() {
        return patientjtda;
    }

    public void setPatientjtda(String patientjtda) {
        this.patientjtda = patientjtda;
    }

    public String getPatientjmda() {
        return patientjmda;
    }

    public void setPatientjmda(String patientjmda) {
        this.patientjmda = patientjmda;
    }

    public String getPtsignPk() {
        return ptsignPk;
    }

    public void setPtsignPk(String ptsignPk) {
        this.ptsignPk = ptsignPk;
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

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public String getSignFromDate() {
        return signFromDate;
    }

    public void setSignFromDate(String signFromDate) {
        this.signFromDate = signFromDate;
    }

    public String getSignToDate() {
        return signToDate;
    }

    public void setSignToDate(String signToDate) {
        this.signToDate = signToDate;
    }

    public String getSignPayState() {
        return signPayState;
    }

    public void setSignPayState(String signPayState) {
        this.signPayState = signPayState;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSignPersGroup() {
        return signPersGroup;
    }

    public void setSignPersGroup(String signPersGroup) {
        this.signPersGroup = signPersGroup;
    }

    public String getSignsJjType() {
        return signsJjType;
    }

    public void setSignsJjType(String signsJjType) {
        this.signsJjType = signsJjType;
    }

    public String getSignczpay() {
        return signczpay;
    }

    public void setSignczpay(String signczpay) {
        this.signczpay = signczpay;
    }

    public String getSignzfpay() {
        return signzfpay;
    }

    public void setSignzfpay(String signzfpay) {
        this.signzfpay = signzfpay;
    }

    public String getSignState() {
        return signState;
    }

    public void setSignState(String signState) {
        this.signState = signState;
    }

    public String getSignHealth() {
        return signHealth;
    }

    public void setSignHealth(String signHealth) {
        this.signHealth = signHealth;
    }

    public String getSignDiseaseGroup() {
        return signDiseaseGroup;
    }

    public void setSignDiseaseGroup(String signDiseaseGroup) {
        this.signDiseaseGroup = signDiseaseGroup;
    }

    public String getDrIdNo() {
        return drIdNo;
    }

    public void setDrIdNo(String drIdNo) {
        this.drIdNo = drIdNo;
    }

    public String getDrOperatorIdNo() {
        return drOperatorIdNo;
    }

    public void setDrOperatorIdNo(String drOperatorIdNo) {
        this.drOperatorIdNo = drOperatorIdNo;
    }

    public String getDrAssistantIdNo() {
        return drAssistantIdNo;
    }

    public void setDrAssistantIdNo(String drAssistantIdNo) {
        this.drAssistantIdNo = drAssistantIdNo;
    }

    public String getTeamMerType() {
        return teamMerType;
    }

    public void setTeamMerType(String teamMerType) {
        this.teamMerType = teamMerType;
    }

    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) {
        this.signId = signId;
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

    public String getSignDelDate() {
        return signDelDate;
    }

    public void setSignDelDate(String signDelDate) {
        this.signDelDate = signDelDate;
    }

    public String getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(String changeDate) {
        this.changeDate = changeDate;
    }

    public String getSignFormId() {
        return signFormId;
    }

    public void setSignFormId(String signFormId) {
        this.signFormId = signFormId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSersmValue() {
        return sersmValue;
    }

    public void setSersmValue(String sersmValue) {
        this.sersmValue = sersmValue;
    }
}
