package com.ylz.bizDo.app.vo;

/**
 * Created by asus on 2017/11/06.
 */
public class PerformanceDataQvo {
    private String perName;//姓名
    private String perIdno;//身份证
    private String perArea;//接收地市
    private String perType;//类型
    private String perSource;//来源 1基卫,2app
    private String perFollowType;// 随访类型 5 高血压,6 糖尿病,7精神病8结核病,1新生儿访视,2儿童体检
    private String perFollowNextDate;//下次随访时间
    private String perForeign;//主键
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
    private String perCreateDate;//创建时间

    private String sermValue;//服务包编码
    private String sergValue;//服务组合编码
    private String serpkValue;//服务内容编号
    private String sermContent;//服务内容
    private String serveDate;//服务时间
    private String serveNum;//服务次数

    private String teamId;//团队主键

    public String getPerName() {
        return perName;
    }

    public void setPerName(String perName) {
        this.perName = perName;
    }

    public String getPerIdno() {
        return perIdno;
    }

    public void setPerIdno(String perIdno) {
        this.perIdno = perIdno;
    }

    public String getPerArea() {
        return perArea;
    }

    public void setPerArea(String perArea) {
        this.perArea = perArea;
    }

    public String getPerType() {
        return perType;
    }

    public void setPerType(String perType) {
        this.perType = perType;
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

    public String getPerSource() {
        return perSource;
    }

    public void setPerSource(String perSource) {
        this.perSource = perSource;
    }

    public String getPerFollowType() {
        return perFollowType;
    }

    public void setPerFollowType(String perFollowType) {
        this.perFollowType = perFollowType;
    }

    public String getPerForeign() {
        return perForeign;
    }

    public void setPerForeign(String perForeign) {
        this.perForeign = perForeign;
    }

    public String getPerFollowNextDate() {
        return perFollowNextDate;
    }

    public void setPerFollowNextDate(String perFollowNextDate) {
        this.perFollowNextDate = perFollowNextDate;
    }

    public String getPerCreateDate() {
        return perCreateDate;
    }

    public void setPerCreateDate(String perCreateDate) {
        this.perCreateDate = perCreateDate;
    }

    public String getSermValue() {
        return sermValue;
    }

    public void setSermValue(String sermValue) {
        this.sermValue = sermValue;
    }

    public String getSergValue() {
        return sergValue;
    }

    public void setSergValue(String sergValue) {
        this.sergValue = sergValue;
    }

    public String getSerpkValue() {
        return serpkValue;
    }

    public void setSerpkValue(String serpkValue) {
        this.serpkValue = serpkValue;
    }

    public String getSermContent() {
        return sermContent;
    }

    public void setSermContent(String sermContent) {
        this.sermContent = sermContent;
    }

    public String getServeDate() {
        return serveDate;
    }

    public void setServeDate(String serveDate) {
        this.serveDate = serveDate;
    }

    public String getServeNum() {
        return serveNum;
    }

    public void setServeNum(String serveNum) {
        this.serveNum = serveNum;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }
}
