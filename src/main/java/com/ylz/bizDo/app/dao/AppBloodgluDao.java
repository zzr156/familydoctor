package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppBloodglu;
import com.ylz.bizDo.jtapp.drVo.DeviceQvo;

import java.util.List;

/**
 * 血糖设备
 */
public interface AppBloodgluDao {


    public AppBloodglu findByDevId(String bg) throws Exception;

    public List<AppBloodglu> findPage(DeviceQvo vo) throws Exception;
}
