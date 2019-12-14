package com.ylz.bizDo.smjq.smVo;

import com.ylz.packcommon.common.CommConditionVo;

/**
 * 居民用户基础信息
 * Created by zzl on 2018/7/25.
 */
public class AppSmPeopleBasicVo extends CommConditionVo {
//    医院id、高血压、糖尿病、红标、黄标、绿标、团队id、患者姓名、区域编码
    private String patientId;//用户主键
    private String patientName;//用户姓名
    private String type;//类型 0全部（或） 1查询血压数据 2查询血糖数据

    private String orgId;//机构id
    private String colorType;//标类型（0全部或 1红标(red) 2黄标（yellow） 3绿标（green） 4灰标（gray））
    private String teamId;//团队id

    private String diagnosisDate;//确诊日期
    private String drId;//医生id
    private String patientIdno;//身份证
    private String sex;//性别
    private String patientTel;//联系电话
    private String age;//年龄

    private String startTime;//开始时间
    private String endTime;//结束时间
    private String ageOne;//年龄小
    private String ageTwo;//年龄大

    private String areaId;//区域id
    private String addr;//详细地址

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getColorType() {
        return colorType;
    }

    public void setColorType(String colorType) {
        this.colorType = colorType;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getPatientIdno() {
        return patientIdno;
    }

    public void setPatientIdno(String patientIdno) {
        this.patientIdno = patientIdno;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getDiagnosisDate() {
        return diagnosisDate;
    }

    public void setDiagnosisDate(String diagnosisDate) {
        this.diagnosisDate = diagnosisDate;
    }

    public String getPatientTel() {
        return patientTel;
    }

    public void setPatientTel(String patientTel) {
        this.patientTel = patientTel;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getAgeOne() {
        return ageOne;
    }

    public void setAgeOne(String ageOne) {
        this.ageOne = ageOne;
    }

    public String getAgeTwo() {
        return ageTwo;
    }

    public void setAgeTwo(String ageTwo) {
        this.ageTwo = ageTwo;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }
}
