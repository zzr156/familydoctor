package com.ylz.bizDo.smjq.smEntity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * 血压数据
 * Created by zzl on 2018/7/25.
 */
public class AppSmHbpEntity {
    private Integer dbp;//舒张压
    private Integer sbp;//收缩压
    private Integer pulse;//脉搏
    private String testTime;//测量时间
    private String sourceType;//来源
    private String abnormalState;//异常状态 1异常


    public Integer getDbp() {
        return dbp;
    }

    public void setDbp(Integer dbp) {
        this.dbp = dbp;
    }

    public Integer getSbp() {
        return sbp;
    }

    public void setSbp(Integer sbp) {
        this.sbp = sbp;
    }

    public Integer getPulse() {
        return pulse;
    }

    public void setPulse(Integer pulse) {
        this.pulse = pulse;
    }

    public String getTestTime() {
        return testTime;
    }

    public void setTestTime(Timestamp testTime) {
        if(testTime != null){
            this.testTime = String.valueOf(testTime);
        }else{
            this.testTime = "";
        }
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getAbnormalState() {
        return abnormalState;
    }

    public void setAbnormalState(String abnormalState) {
        this.abnormalState = abnormalState;
    }
}
