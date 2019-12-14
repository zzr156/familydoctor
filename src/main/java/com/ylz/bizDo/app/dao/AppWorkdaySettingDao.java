package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppWorkdaySetting;

/**
 * Created by zzl on 2017/6/14.
 */
public interface AppWorkdaySettingDao  {

    /**
     * 根据医生ID查询工作台设置
     * @param id
     * @return
     */
    public AppWorkdaySetting findByDoctorId(String drId) throws Exception;
}
