package com.ylz.bizDo.smjq.smEntity;

import com.ylz.packcommon.common.util.ExtendDate;

import java.sql.Timestamp;

/**
 * 血糖数据
 * Created by zzl on 2018/7/25.
 */
public class AppBloodSugarEntity {
    private Double bloodSugarValue;//血糖值
    private String measureTime;//测量时间
    private String ugBgstate;//时段（1饭前）
    private String sourceType;//来源
    private String abnormalState;//异常状态 1异常

    public Double getBloodSugarValue() {
        return bloodSugarValue;
    }

    public void setBloodSugarValue(Double bloodSugarValue) {
        this.bloodSugarValue = bloodSugarValue;
    }

    public String getMeasureTime() {
        return measureTime;
    }

    public void setMeasureTime(Timestamp measureTime) {
        if(measureTime!=null){
            this.measureTime = ExtendDate.getYMD_h_m_s(measureTime);
        }else{
            this.measureTime = "";
        }
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getUgBgstate() {
        return ugBgstate;
    }

    public void setUgBgstate(String ugBgstate) {
        this.ugBgstate = ugBgstate;
    }

    public String getAbnormalState() {
        return abnormalState;
    }

    public void setAbnormalState(String abnormalState) {
        this.abnormalState = abnormalState;
    }
}
