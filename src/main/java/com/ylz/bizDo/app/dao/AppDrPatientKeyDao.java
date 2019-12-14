package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppDrPatientKey;

import java.util.Map;

/**
 * 医生患者账号TOKEN
 */
public interface AppDrPatientKeyDao {

    public AppDrPatientKey findByDoctorOrPatientId(String id)throws Exception;

    public AppDrPatientKey findDrPatientKeyByToken(String drToken)throws Exception;

    public AppDrPatientKey findDrTempKeyByToken(String drToken)throws Exception;

    public Map findDrPatientKeyByTokenSql(String drToken)throws Exception;

    public AppDrPatientKey findByDrOrPatient(String userId)throws Exception;

}
