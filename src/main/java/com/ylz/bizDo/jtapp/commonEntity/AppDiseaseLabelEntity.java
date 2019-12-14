package com.ylz.bizDo.jtapp.commonEntity;

import org.apache.commons.lang.StringUtils;

/**
 * 高血压、糖尿病标签管理页面查询返回参数
 * Created by zzl on 2018/7/30.
 */
public class AppDiseaseLabelEntity {
    private String id;//签约主键
    private String patientId;//居民主键
    private String patientName;//居民姓名
    private String patientIdno;//居民身份证
    private String patientAddr;//居民地址
    private String hbpLabel;//高血压标签
    private String hbpLabelColor;//高血压标签颜色
    private String dmLabel;//糖尿病标签
    private String dmLabelColor;//糖尿病标签颜色
    private String pmPLabel;//严重精神障碍
    private String pmPLabelColor;//严重精神障碍标签颜色
    private String tbLabel;//结核病
    private String tbLabelColor;//结核病标签颜色
    private String xCoordinate;//X轴
    private String yCoordinate;//y轴

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPatientIdno() {
        return patientIdno;
    }

    public void setPatientIdno(String patientIdno) {
        this.patientIdno = patientIdno;
    }

    public String getPatientAddr() {
        return patientAddr;
    }

    public void setPatientAddr(String patientAddr) {
        this.patientAddr = patientAddr;
    }

    public String getHbpLabel() {
        return hbpLabel;
    }

    public void setHbpLabel(String hbpLabel) {
        if(StringUtils.isBlank(hbpLabel)){
            hbpLabel = "";
        }
        this.hbpLabel = hbpLabel;
    }

    public String getDmLabel() {
        return dmLabel;
    }

    public void setDmLabel(String dmLabel) {
        if(StringUtils.isBlank(dmLabel)){
            dmLabel = "";
        }
        this.dmLabel = dmLabel;
    }

    public String getHbpLabelColor() {
        return hbpLabelColor;
    }

    public void setHbpLabelColor(String hbpLabelColor) {
        if(StringUtils.isBlank(hbpLabelColor)){
            hbpLabelColor = "gray";
        }
        this.hbpLabelColor = hbpLabelColor;
    }

    public String getDmLabelColor() {
        return dmLabelColor;
    }

    public void setDmLabelColor(String dmLabelColor) {
        if(StringUtils.isBlank(dmLabelColor)){
            dmLabelColor = "gray";
        }
        this.dmLabelColor = dmLabelColor;
    }

    public String getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(String xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public String getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(String yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public String getPmPLabel() {
        return pmPLabel;
    }

    public void setPmPLabel(String pmPLabel) {
        if(StringUtils.isBlank(pmPLabel)){
            pmPLabel = "";
        }
        this.pmPLabel = pmPLabel;
    }

    public String getPmPLabelColor() {
        return pmPLabelColor;
    }

    public void setPmPLabelColor(String pmPLabelColor) {
        if(StringUtils.isBlank(pmPLabelColor)){
            pmPLabelColor = "gray";
        }
        this.pmPLabelColor = pmPLabelColor;
    }

    public String getTbLabel() {
        return tbLabel;
    }

    public void setTbLabel(String tbLabel) {
        if(StringUtils.isBlank(tbLabel)){
            tbLabel = "";
        }
        this.tbLabel = tbLabel;
    }

    public String getTbLabelColor() {
        return tbLabelColor;
    }

    public void setTbLabelColor(String tbLabelColor) {
        if(StringUtils.isBlank(tbLabelColor)){
            tbLabelColor = "";
        }
        this.tbLabelColor = tbLabelColor;
    }
}
