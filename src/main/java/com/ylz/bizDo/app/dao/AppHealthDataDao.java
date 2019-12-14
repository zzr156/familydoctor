package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppHealthData;
import net.sf.json.JSONObject;

/**
 * Created by asus on 2017/07/27.
 */
public interface AppHealthDataDao {
    public AppHealthData findByType(String type, String idNo) throws Exception;

    public AppHealthData findByPatientId(String patientId, String ghh000,String type) throws Exception;

    public void addHealthDataImplements(JSONObject jsonall,String idNo,String card,String type,String requestUserId,String requestUserName,String userType) throws Exception;
}
