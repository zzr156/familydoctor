package com.ylz.bizDo.jtapp.patientEntity;

import com.ylz.packcommon.common.util.ExtendDate;

import java.util.Date;


/**
 * 返回孕产妇保健计划
 */
public class AppPregnantEntity {

    private String id;
    private Date planDate;//保健计划日期
    private String planTitle;//周次
    private String state;//0未体检 1已体检

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Date planDate) {
        this.planDate = planDate;
    }

    public String getPlanTitle() {
        return planTitle;
    }

    public void setPlanTitle(String planTitle) {
        this.planTitle = planTitle;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStrPlanDate(){
        return ExtendDate.getChineseYMD(planDate);
    }
}
