package com.ylz.bizDo.jtapp.patientVo;

/**
 * Created by zzl on 2017/12/29.
 */
public class AppFamilySignSonQvo {
    private String patientId;//患者id
    private String serverIds;//服务套餐（多个用“;”隔开）

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getServerIds() {
        return serverIds;
    }

    public void setServerIds(String serverIds) {
        this.serverIds = serverIds;
    }
}
