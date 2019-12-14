package com.ylz.bizDo.jtapp.drVo;

import com.ylz.packcommon.common.CommConditionVo;

/**
 * Created by asus on 2017/6/24.
 */
public class DeviceQvo  extends CommConditionVo {
    private String teamId;//团队主键
    private String[] patientIds;//患者主键
    private String code;//
    private String id;//设备主键
    private String type;//设备类型 1血糖 2血压


    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String[] getPatientIds() {
        return patientIds;
    }

    public void setPatientIds(String[] patientIds) {
        this.patientIds = patientIds;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
