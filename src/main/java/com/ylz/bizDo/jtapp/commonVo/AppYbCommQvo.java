package com.ylz.bizDo.jtapp.commonVo;

/**
 * 医保对接
 * Created by WangCheng on 2019/03/07.
 */
public class AppYbCommQvo {
    private String id;//签约单主键
    private String name;//姓名
    private String patientIdno;//身份证
    private String signHospId;//医院ID
    private String patientCard;//社保卡号
    private String packageId;//服务包编号
    private String signDrName;//签约医生姓名
    private String signToDate;//协议开始至截止时间
    private String urlType;
    private String usr;//用户名
    private String pwd;//密码
    private String bke241; //1 窗口 2移动端

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatientIdno() {
        return patientIdno;
    }

    public void setPatientIdno(String patientIdno) {
        this.patientIdno = patientIdno;
    }

    public String getSignHospId() {
        return signHospId;
    }

    public void setSignHospId(String signHospId) {
        this.signHospId = signHospId;
    }

    public String getPatientCard() {
        return patientCard;
    }

    public void setPatientCard(String patientCard) {
        this.patientCard = patientCard;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getSignDrName() {
        return signDrName;
    }

    public void setSignDrName(String signDrName) {
        this.signDrName = signDrName;
    }

    public String getSignToDate() {
        return signToDate;
    }

    public void setSignToDate(String signToDate) {
        this.signToDate = signToDate;
    }

    public String getUrlType() {
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }

    public String getUsr() {
        return usr;
    }

    public void setUsr(String usr) {
        this.usr = usr;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getBke241() {
        return bke241;
    }

    public void setBke241(String bke241) {
        this.bke241 = bke241;
    }
}