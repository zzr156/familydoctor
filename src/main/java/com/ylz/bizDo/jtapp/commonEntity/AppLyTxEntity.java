package com.ylz.bizDo.jtapp.commonEntity;

import java.math.BigInteger;

/**
 * 履约提醒
 * Created by zzl on 2017/11/9.
 */
public class AppLyTxEntity {
    private String count;
    private String patientId;
    private String drId;
    private String patientIdno;
    private String signId;

    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) {
        this.signId = signId;
    }

    public String getCount() {
        return count;
    }

    public void setCount(BigInteger count) {
        if(count!=null){
            this.count = String.valueOf(count);
        }else{
            this.count = "0";
        }

    }

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

    public String getPatientIdno() {
        return patientIdno;
    }

    public void setPatientIdno(String patientIdno) {
        this.patientIdno = patientIdno;
    }
}
