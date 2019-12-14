package com.ylz.bizDo.jtapp.patientVo;

import com.ylz.packcommon.common.CommConditionVo;

/**个人条件查询健康教育列表
 * Created by zzl on 2017/6/22.
 */
public class AppPatientHealthListQvo extends CommConditionVo {
    private String patientId;//个人id
    private String drId;//医生id
    private String type;//健康教育类别
    private String date;//年份

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
