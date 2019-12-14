package com.ylz.bizDo.mangecount.dao;

import com.ylz.bizDo.mangecount.vo.ResidentVo;

import java.util.Map;

/**
 * Created by asus on 2017/07/19.
 */
public interface AppWorkPlanDao {

    /**
     * 工作计划统计
     * @param qvo
     * @return
     */
    public Map<String,Object> findWorkPlan(ResidentVo qvo) throws Exception;

}
