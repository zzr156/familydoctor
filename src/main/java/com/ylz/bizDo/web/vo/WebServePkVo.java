package com.ylz.bizDo.web.vo;

/**
 * 服务内容vo
 * Created by zzl on 2018/3/13.
 */
public class WebServePkVo {
    private String serpkId;//服务内容id
    private String serpkValue;//服务编号
    private String serpkName;//服务内容
    private String serpkOpenState;//是否开启频次 0否 1是 2按需
    private String serpkTime;//频次
    private String serpkNum;//间隔值
    private String serpkIntervalType;//间隔单位 3月 0其他
    private String serpkBaseType;//是否是特色服务 0否 1是
    private String serpkRemark;//服务内容说明

    public String getSerpkValue() {
        return serpkValue;
    }

    public void setSerpkValue(String serpkValue) {
        this.serpkValue = serpkValue;
    }

    public String getSerpkName() {
        return serpkName;
    }

    public void setSerpkName(String serpkName) {
        this.serpkName = serpkName;
    }

    public String getSerpkOpenState() {
        return serpkOpenState;
    }

    public void setSerpkOpenState(String serpkOpenState) {
        this.serpkOpenState = serpkOpenState;
    }

    public String getSerpkTime() {
        return serpkTime;
    }

    public void setSerpkTime(String serpkTime) {
        this.serpkTime = serpkTime;
    }

    public String getSerpkNum() {
        return serpkNum;
    }

    public void setSerpkNum(String serpkNum) {
        this.serpkNum = serpkNum;
    }

    public String getSerpkIntervalType() {
        return serpkIntervalType;
    }

    public void setSerpkIntervalType(String serpkIntervalType) {
        this.serpkIntervalType = serpkIntervalType;
    }

    public String getSerpkBaseType() {
        return serpkBaseType;
    }

    public void setSerpkBaseType(String serpkBaseType) {
        this.serpkBaseType = serpkBaseType;
    }

    public String getSerpkRemark() {
        return serpkRemark;
    }

    public void setSerpkRemark(String serpkRemark) {
        this.serpkRemark = serpkRemark;
    }

    public String getSerpkId() {
        return serpkId;
    }

    public void setSerpkId(String serpkId) {
        this.serpkId = serpkId;
    }
}
