package com.ylz.bizDo.jtapp.drVo;

/**
 * 履约人员列表请求参数
 * Created by zzl on 2017/11/14.
 */
public class AppLyQvo {
    private String type;//类型 1个人
    private String drId;//医生id
    private String pcNum;//批次号
    private String patientId;//患者id
    private String peyType;//履约类型

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getPcNum() {
        return pcNum;
    }

    public void setPcNum(String pcNum) {
        this.pcNum = pcNum;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPeyType() {
        return peyType;
    }

    public void setPeyType(String peyType) {
        this.peyType = peyType;
    }
}
