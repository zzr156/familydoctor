package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppChildInoculationPlan;

import java.util.List;

/**
 * Created by zzl on 2017/7/22.
 */
public interface AppChildInoculationPlanDao {
    public List<AppChildInoculationPlan> findByMyKh(String id, String etMykh) throws Exception;
    public void deleteChildMykh(String etMykh,String id) throws Exception;
    /**
     * 调度查询儿童体检免疫提醒
     * @return
     */
    public List<AppChildInoculationPlan> findList() throws Exception;
    /**
     * 调度查询儿童体检免疫提醒
     * @return
     */
    public List<AppChildInoculationPlan> findList(String userId) throws Exception;
}
