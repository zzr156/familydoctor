package com.ylz.bizDo.smjq.smEntity;

/**
 * Created by zzl on 2018/8/2.
 */
public class AppSmHbpTwoEntity {
    private Integer dbp;//舒张压
    private Integer sbp;//收缩压
    private Integer pulse;//脉搏
    private String testTime;//测量时间
    private String sourceType;//来源
    private String ycState;//异常状态（0无异常 1异常）
    private String drId;//医生id
    private String drName;//医生姓名
    private String drTel;//医生电话
    private String HbpRemark;//备注

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

    public void setTestTime(String testTime) {
        this.testTime = testTime;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getYcState() {
        return ycState;
    }

    public void setYcState(String ycState) {
        this.ycState = ycState;
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

    public String getDrTel() {
        return drTel;
    }

    public void setDrTel(String drTel) {
        this.drTel = drTel;
    }

    public String getHbpRemark() {
        return HbpRemark;
    }

    public void setHbpRemark(String hbpRemark) {
        HbpRemark = hbpRemark;
    }
}
