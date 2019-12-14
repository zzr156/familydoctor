package com.ylz.bizDo.jtapp.basicHealthVo;

/**
 * Created by zzl on 2017/7/21.
 */
public class HealthDiagnosisBaseQvo {
    private String card;//社保卡
    private String organizationCode;//机构代码
    private String ghh000;//事件号
    private String patientId;//病人id
    private String areaCode;//城市编码

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getGhh000() {
        return ghh000;
    }

    public void setGhh000(String ghh000) {
        this.ghh000 = ghh000;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
}
