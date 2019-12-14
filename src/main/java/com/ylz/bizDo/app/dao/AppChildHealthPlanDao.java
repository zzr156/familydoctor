package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppChildHealthPlan;
import com.ylz.bizDo.jtapp.patientEntity.AppChildHealthPlanEntity;
import com.ylz.bizDo.jtapp.patientVo.AppChildHealthQvo;
import com.ylz.packcommon.common.exception.DaoException;

import java.util.Calendar;
import java.util.List;

/**
 * Created by zzl on 2017/6/22.
 */
public interface AppChildHealthPlanDao {
    //保存儿童体检计划
    public List<AppChildHealthPlanEntity> savePlan(AppChildHealthQvo qvo) throws Exception;
    //查询当前时间加预警时间等于记录
    public List<AppChildHealthPlan> findByYj(Calendar cal) throws Exception;

    public List<AppChildHealthPlan> findByChildUserId(String chpUserId,String childUserId) throws Exception;
    public void saveChildPlan(AppChildHealthQvo qvo) throws Exception;
    //查询用户计划信息
    public List<AppChildHealthPlanEntity> findById(String userId,String childUserId) throws Exception;

    /**
     * 体检提醒
     * @param cal
     * @param userId
     * @return
     * @throws DaoException
     */
    public List<AppChildHealthPlan> findByYj(Calendar cal,String userId) throws Exception;

}
