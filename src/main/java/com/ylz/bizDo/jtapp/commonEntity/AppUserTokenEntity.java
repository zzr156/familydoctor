package com.ylz.bizDo.jtapp.commonEntity;

import com.ylz.bizDo.jtapp.drEntity.AppDrUserEntity;
import com.ylz.bizDo.jtapp.drEntity.AppDrUserPossEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppPatientUserEntity;

/**
 * Created by asus on 2017-7-13.
 */
public class AppUserTokenEntity {
    private String commonEntity;
    private AppDrUserPossEntity poss;
    private AppDrUserEntity druser;
    private AppPatientUserEntity patientuser;

    public String getCommonEntity() {
        return commonEntity;
    }

    public void setCommonEntity(String commonEntity) {
        this.commonEntity = commonEntity;
    }

    public AppDrUserPossEntity getPoss() {
        return poss;
    }

    public void setPoss(AppDrUserPossEntity poss) {
        this.poss = poss;
    }

    public AppDrUserEntity getDruser() {
        return druser;
    }

    public void setDruser(AppDrUserEntity druser) {
        this.druser = druser;
    }

    public AppPatientUserEntity getPatientuser() {
        return patientuser;
    }

    public void setPatientuser(AppPatientUserEntity patientuser) {
        this.patientuser = patientuser;
    }
}
