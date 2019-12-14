package com.ylz.bizDo.jtapp.commonVo;

import com.ylz.packcommon.common.CommConditionVo;

/**
 * Created by zzl on 2017/6/17.
 */
public class AppUserBloodgluVo extends CommConditionVo {

    private String userId;//患者id
    private String bgstate;//时段
    private String testtime;//测量时间
    private String bloodglu;//血糖值
    private String temptur;//温度值
    private String devid;//设备id
    private String symptom;//症状
    private String note;//备注
    private String period;//1周 2月 3三月
    private String sourceType;//数据来源(1app 2智能设备 3随访)

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBgstate() {
        return bgstate;
    }

    public void setBgstate(String bgstate) {
        this.bgstate = bgstate;
    }

    public String getTesttime() {
        return testtime;
    }

    public void setTesttime(String testtime) {
        this.testtime = testtime;
    }

    public String getBloodglu() {
        return bloodglu;
    }

    public void setBloodglu(String bloodglu) {
        this.bloodglu = bloodglu;
    }

    public String getTemptur() {
        return temptur;
    }

    public void setTemptur(String temptur) {
        this.temptur = temptur;
    }

    public String getDevid() {
        return devid;
    }

    public void setDevid(String devid) {
        this.devid = devid;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }
}
