package com.ylz.bizDo.assessment.vo;

import com.ylz.packcommon.common.CommConditionVo;

/**
 * Created by zms on 2018/6/13.
 */
public class AssessListQvo extends CommConditionVo {

    private String areaLevel;// 区域级别（1-省，2-市，3-区、县，4-乡、镇，5-村、居委会）
    private String areaCode;// 区域编码
    private String hospId;// 机构ID
    private String teamId;// 团队ID
    private String drId;// 医生ID
    private String drCode;// 医生编号
    private String drName;// 医生姓名
    private String patientName;// 居民姓名
    private String patientIdNo;// 居民身份证号
    private String signNum;// 签约编号
    private String group;// 服务人群
    private String economics;// 经济类型
    private String signDateStart;// 签约日期开始
    private String signDateEnd;// 签约日期结束
    private String signFromDateStart;// 协议开始日期开始
    private String signFromDateEnd;// 协议开始日期结束
    private String signToDateStart;// 协议截止日期开始
    private String signToDateEnd;// 协议截止日期结束
    private String isAssess;// 是否考核
    private String isReview;// 是否审核
    private String state;// 是否考核完成
    private int totalScore;// 总考核成绩
    private String rating;// 考核评级
    private String random;// 随机抽查
    private String history;// 是否显示历史记录
    private String committee;// 村、居委会编码


    public String getAreaLevel() {
        return areaLevel;
    }

    public void setAreaLevel(String areaLevel) {
        this.areaLevel = areaLevel;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getHospId() {
        return hospId;
    }

    public void setHospId(String hospId) {
        this.hospId = hospId;
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

    public String getDrCode() {
        return drCode;
    }

    public void setDrCode(String drCode) {
        this.drCode = drCode;
    }

    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientIdNo() {
        return patientIdNo;
    }

    public void setPatientIdNo(String patientIdNo) {
        this.patientIdNo = patientIdNo;
    }

    public String getSignNum() {
        return signNum;
    }

    public void setSignNum(String signNum) {
        this.signNum = signNum;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getEconomics() {
        return economics;
    }

    public void setEconomics(String economics) {
        this.economics = economics;
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

    public String getIsAssess() {
        return isAssess;
    }

    public void setIsAssess(String isAssess) {
        this.isAssess = isAssess;
    }

    public String getIsReview() {
        return isReview;
    }

    public void setIsReview(String isReview) {
        this.isReview = isReview;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getRandom() {
        return random;
    }

    public void setRandom(String random) {
        this.random = random;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getCommittee() {
        return committee;
    }

    public void setCommittee(String committee) {
        this.committee = committee;
    }
}
