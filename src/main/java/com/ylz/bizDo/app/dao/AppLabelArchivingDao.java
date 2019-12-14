package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppLabelArchiving;

/**
 * Created by zzl on 2019/3/27.
 */
public interface AppLabelArchivingDao {
    /**
     * 根据服务类型值和健康立卡主键查询建档立卡标签
     * @param value
     * @param id
     * @return
     * @throws Exception
     */
    public AppLabelArchiving findByIdAndValue(String value,String id) throws Exception;
}
