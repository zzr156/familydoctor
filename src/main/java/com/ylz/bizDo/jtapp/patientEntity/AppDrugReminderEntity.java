package com.ylz.bizDo.jtapp.patientEntity;


/**
 * Created by zzl on 2017/6/17.
 */
public class AppDrugReminderEntity {
    private String drugName;//药品名称
    private String drugId;//用药指导id

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getDrugId() {
        return drugId;
    }

    public void setDrugId(String drugId) {
        this.drugId = drugId;
    }
}
