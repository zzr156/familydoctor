package com.ylz.bizDo.app.dao;


import com.ylz.bizDo.app.po.AppPregnantPlan;
import com.ylz.bizDo.jtapp.patientEntity.AppPregnantEntity;
import com.ylz.bizDo.jtapp.patientVo.AppPregnantPlanQvo;

import java.util.List;

public interface AppPregnantPlanDao {
    public List<AppPregnantEntity> findPlan(String patientId,String type,String userDate) throws Exception;

    public List<AppPregnantPlan> findPlanAlert() throws Exception;

    public void setHealthCare(AppPregnantPlanQvo vo) throws Exception;

    /**
     * 孕妇保健计划提醒
     * @param userId
     * @return
     */
    public List<AppPregnantPlan> findPlanAlert(String userId) throws Exception;
}
