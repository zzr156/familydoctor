package com.ylz.bizDo.jtapp.drVo;

import com.ylz.packcommon.common.CommConditionVo;

/**
 * Created by zzl on 2017/11/4.
 */
public class AppSignsWarningRecordQvo extends CommConditionVo {
    private String id;//主键id
    private String patientId;//患者id
    private String code;//编号
    private String drId;//医生id
    private String type;//类型（0群发 1选人发）

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
}
