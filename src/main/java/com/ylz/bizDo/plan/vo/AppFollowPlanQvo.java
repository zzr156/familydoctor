package com.ylz.bizDo.plan.vo;

import com.ylz.packcommon.common.CommConditionVo;

/**
 * Created by dws on 2017-05-15.
 */
public class AppFollowPlanQvo extends CommConditionVo{
    private String planStart;//开始时间
    private String planEnd;//结束时间
    private String planPatientId;//随访患者ID
    private String planDoctorId;//随访医生ID
    private String planToDay;//当天
    private String ukey;

    public String getPlanStart() {
        return planStart;
    }

    public void setPlanStart(String planStart) {
        this.planStart = planStart;
    }

    public String getPlanEnd() {
        return planEnd;
    }

    public void setPlanEnd(String planEnd) {
        this.planEnd = planEnd;
    }

    public String getPlanPatientId() {
        return planPatientId;
    }

    public void setPlanPatientId(String planPatientId) {
        this.planPatientId = planPatientId;
    }

    public String getPlanDoctorId() {
        return planDoctorId;
    }

    public void setPlanDoctorId(String planDoctorId) {
        this.planDoctorId = planDoctorId;
    }

    public String getPlanToDay() {
        return planToDay;
    }

    public void setPlanToDay(String planToDay) {
        this.planToDay = planToDay;
    }

    public String getUkey() {
        return ukey;
    }

    public void setUkey(String ukey) {
        this.ukey = ukey;
    }
}
