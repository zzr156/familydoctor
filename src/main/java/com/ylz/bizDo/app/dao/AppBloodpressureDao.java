package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppBloodpressure;
import com.ylz.bizDo.jtapp.drVo.DeviceQvo;

import java.util.List;

/**
 * 高血压设备
 */
public interface AppBloodpressureDao {

    public List<AppBloodpressure> findByUserId(String userId) throws Exception;


    public AppBloodpressure findByDevId(String bp) throws Exception;
    //分页
    public List<AppBloodpressure> findPage(DeviceQvo vo) throws Exception;
}
