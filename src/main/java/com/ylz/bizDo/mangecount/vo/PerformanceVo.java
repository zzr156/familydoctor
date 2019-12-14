package com.ylz.bizDo.mangecount.vo;

/**履约统计请求参数
 * Created by zzl on 2017/10/25.
 */
public class PerformanceVo {
    private String drId;
    private String patientId;
    private String signFormDate;
    private String signToDate;

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getSignToDate() {
        return signToDate;
    }

    public void setSignToDate(String signToDate) {
        this.signToDate = signToDate;
    }

    public String getSignFormDate() {
        return signFormDate;
    }

    public void setSignFormDate(String signFormDate) {
        this.signFormDate = signFormDate;
    }
}
