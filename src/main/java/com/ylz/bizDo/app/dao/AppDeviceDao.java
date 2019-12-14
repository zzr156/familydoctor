package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppDevice;
import com.ylz.bizDo.app.vo.AppDeviceQvo;
import com.ylz.bizDo.jtapp.commonEntity.AppDeviceEntity;
import com.ylz.bizDo.jtapp.commonEntity.DeviceEntity;
import com.ylz.bizDo.jtapp.commonVo.AppCommQvo;
import com.ylz.bizDo.jtapp.commonVo.AppDeviceVo;
import com.ylz.packcommon.common.exception.DaoException;

import java.util.List;

/**
 * Created by zzl on 2017/6/17.
 */
public interface AppDeviceDao {
    //分页查询
    public List<AppDevice> findListQvo(AppDeviceQvo qvo) throws Exception;
    //无分页查询全部
    public List<AppDeviceEntity> findByType(AppDeviceVo qvo) throws Exception;

    public List<DeviceEntity> findLike(AppCommQvo vo) throws Exception;
    //验证设备
    public boolean verification(String devCode,String type) throws Exception;
}
