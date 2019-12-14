package com.ylz.bizDo.app.vo;

import java.util.Calendar;

/**
 * 求助表
 */
public class SeekHelpVo {

    private String seekDevId;//设备号
    private String seekPatientId;//患者主键
    private String seekHelpDate;//求助时间
    private String seekDrId;//医生主键
    private String seekDrName;//医生主键
    private String seekTeamId;//团队主键
    private String seekTeamName;//团队主键
    private String seekHospId;//医院主键
    private String seekHospName;//医院主键
    private String seekAreaCode;//区划
    private String seekAreaName;//区划
    private String seekRemark;//备注
    private String seekDrTel;//医生电话

    public String getSeekDevId() {
        return seekDevId;
    }

    public void setSeekDevId(String seekDevId) {
        this.seekDevId = seekDevId;
    }

    public String getSeekPatientId() {
        return seekPatientId;
    }

    public void setSeekPatientId(String seekPatientId) {
        this.seekPatientId = seekPatientId;
    }

    public String getSeekHelpDate() {
        return seekHelpDate;
    }

    public void setSeekHelpDate(String seekHelpDate) {
        this.seekHelpDate = seekHelpDate;
    }

    public String getSeekDrId() {
        return seekDrId;
    }

    public void setSeekDrId(String seekDrId) {
        this.seekDrId = seekDrId;
    }

    public String getSeekDrName() {
        return seekDrName;
    }

    public void setSeekDrName(String seekDrName) {
        this.seekDrName = seekDrName;
    }

    public String getSeekTeamId() {
        return seekTeamId;
    }

    public void setSeekTeamId(String seekTeamId) {
        this.seekTeamId = seekTeamId;
    }

    public String getSeekTeamName() {
        return seekTeamName;
    }

    public void setSeekTeamName(String seekTeamName) {
        this.seekTeamName = seekTeamName;
    }

    public String getSeekHospId() {
        return seekHospId;
    }

    public void setSeekHospId(String seekHospId) {
        this.seekHospId = seekHospId;
    }

    public String getSeekHospName() {
        return seekHospName;
    }

    public void setSeekHospName(String seekHospName) {
        this.seekHospName = seekHospName;
    }

    public String getSeekAreaCode() {
        return seekAreaCode;
    }

    public void setSeekAreaCode(String seekAreaCode) {
        this.seekAreaCode = seekAreaCode;
    }

    public String getSeekAreaName() {
        return seekAreaName;
    }

    public void setSeekAreaName(String seekAreaName) {
        this.seekAreaName = seekAreaName;
    }

    public String getSeekRemark() {
        return seekRemark;
    }

    public void setSeekRemark(String seekRemark) {
        this.seekRemark = seekRemark;
    }

    public String getSeekDrTel() {
        return seekDrTel;
    }

    public void setSeekDrTel(String seekDrTel) {
        this.seekDrTel = seekDrTel;
    }
}
