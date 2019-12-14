package com.ylz.bizDo.jtapp.drVo;

import com.ylz.packcommon.common.CommConditionVo;

/**
 * 提交转诊参数
 * Created by zzl on 2017/12/11.
 */
public class AppDrReferralQvo extends CommConditionVo {
    private String signId;//签约单id
    private String id;//主键id
    private String patientId;//患者id
    private String name;//患者姓名
    private String sex;//患者性别
    private String age;//年龄
    private String idNo;//身份证
    private String card;//医保卡号
    private String phone;//手机号
    private String address;//居住地
    //转诊信息
    private String cbyx;//初步印象
    private String zcyy;//主要现病史（转出原因）
    private String jws;//主要既往史
    private String zljg;//治疗经过
    //转诊机构信息
    private String outOrgId;//转诊发起机构
    private String outOrgName;//转诊发起机构名称
    private String outDrId;//转诊发起医生
    private String outDrName;//转诊发起医生姓名
    private String lxfs;//联系方式
    private String inOrgId;//转诊接收机构
    private String inOrgName;//转诊接收机构名称
    private String inDeptId;//转诊接收科室
    private String inDeptName;//转诊接收科室名称
    private String yyDate;//预约转诊日期
    private String sqTime;//申请转诊日期
    private String teamId;//团队id
    private String state;//转诊状态（1同意 2过期 3拒绝 4终止 5康复回转）

    private String jjReason;//拒绝理由
    private String zzReason;//终止原因
    private String zdResult;//诊断结果
    private String zyjcResult;//主要检查结果
    private String nextAdvice;//下一步建议
    private String inDrId;//转诊接收医生id
    private String refCode;//转诊编号
    private String refAcceptsTime;//转诊接收时间（同意时间2018-01-01 01:01:01）
    private String refRejectTime;//转诊拒绝时间
    private String refTerminationTime;//终止日期
    private String refRehabilitationTime;//康复回转日期

    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) {
        this.signId = signId;
    }

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCbyx() {
        return cbyx;
    }

    public void setCbyx(String cbyx) {
        this.cbyx = cbyx;
    }

    public String getZcyy() {
        return zcyy;
    }

    public void setZcyy(String zcyy) {
        this.zcyy = zcyy;
    }

    public String getJws() {
        return jws;
    }

    public void setJws(String jws) {
        this.jws = jws;
    }

    public String getZljg() {
        return zljg;
    }

    public void setZljg(String zljg) {
        this.zljg = zljg;
    }

    public String getOutOrgId() {
        return outOrgId;
    }

    public void setOutOrgId(String outOrgId) {
        this.outOrgId = outOrgId;
    }

    public String getOutDrId() {
        return outDrId;
    }

    public void setOutDrId(String outDrId) {
        this.outDrId = outDrId;
    }

    public String getLxfs() {
        return lxfs;
    }

    public void setLxfs(String lxfs) {
        this.lxfs = lxfs;
    }

    public String getInOrgId() {
        return inOrgId;
    }

    public void setInOrgId(String inOrgId) {
        this.inOrgId = inOrgId;
    }

    public String getInDeptId() {
        return inDeptId;
    }

    public void setInDeptId(String inDeptId) {
        this.inDeptId = inDeptId;
    }

    public String getYyDate() {
        return yyDate;
    }

    public void setYyDate(String yyDate) {
        this.yyDate = yyDate;
    }

    public String getSqTime() {
        return sqTime;
    }

    public void setSqTime(String sqTime) {
        this.sqTime = sqTime;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getJjReason() {
        return jjReason;
    }

    public void setJjReason(String jjReason) {
        this.jjReason = jjReason;
    }

    public String getZzReason() {
        return zzReason;
    }

    public void setZzReason(String zzReason) {
        this.zzReason = zzReason;
    }

    public String getZdResult() {
        return zdResult;
    }

    public void setZdResult(String zdResult) {
        this.zdResult = zdResult;
    }

    public String getZyjcResult() {
        return zyjcResult;
    }

    public void setZyjcResult(String zyjcResult) {
        this.zyjcResult = zyjcResult;
    }

    public String getNextAdvice() {
        return nextAdvice;
    }

    public void setNextAdvice(String nextAdvice) {
        this.nextAdvice = nextAdvice;
    }

    public String getInDrId() {
        return inDrId;
    }

    public void setInDrId(String inDrId) {
        this.inDrId = inDrId;
    }

    public String getOutOrgName() {
        return outOrgName;
    }

    public void setOutOrgName(String outOrgName) {
        this.outOrgName = outOrgName;
    }

    public String getOutDrName() {
        return outDrName;
    }

    public void setOutDrName(String outDrName) {
        this.outDrName = outDrName;
    }

    public String getInOrgName() {
        return inOrgName;
    }

    public void setInOrgName(String inOrgName) {
        this.inOrgName = inOrgName;
    }

    public String getInDeptName() {
        return inDeptName;
    }

    public void setInDeptName(String inDeptName) {
        this.inDeptName = inDeptName;
    }

    public String getRefCode() {
        return refCode;
    }

    public void setRefCode(String refCode) {
        this.refCode = refCode;
    }

    public String getRefAcceptsTime() {
        return refAcceptsTime;
    }

    public void setRefAcceptsTime(String refAcceptsTime) {
        this.refAcceptsTime = refAcceptsTime;
    }

    public String getRefRejectTime() {
        return refRejectTime;
    }

    public void setRefRejectTime(String refRejectTime) {
        this.refRejectTime = refRejectTime;
    }

    public String getRefTerminationTime() {
        return refTerminationTime;
    }

    public void setRefTerminationTime(String refTerminationTime) {
        this.refTerminationTime = refTerminationTime;
    }

    public String getRefRehabilitationTime() {
        return refRehabilitationTime;
    }

    public void setRefRehabilitationTime(String refRehabilitationTime) {
        this.refRehabilitationTime = refRehabilitationTime;
    }
}
