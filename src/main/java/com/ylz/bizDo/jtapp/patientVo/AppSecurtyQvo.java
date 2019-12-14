package com.ylz.bizDo.jtapp.patientVo;

import com.ylz.packcommon.common.CommConditionVo;

/**
 * Created by asus on 2017/08/11.
 */
public class AppSecurtyQvo extends CommConditionVo {

    private String drPatientId;//用户主键
    private String state;//类型
    private String id;//主键
    private String type;//类型

    public String getDrPatientId() {
        return drPatientId;
    }

    public void setDrPatientId(String drPatientId) {
        this.drPatientId = drPatientId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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
