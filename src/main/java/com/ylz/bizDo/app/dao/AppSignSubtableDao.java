package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppSignSubtable;

import java.util.List;

/**
 * Created by zzl on 2018/7/17.
 */
public interface AppSignSubtableDao {
    /**
     * 根据签约主键和类型查询图片
     * @param signId
     * @param type
     * @return
     * @throws Exception
     */
    public List<AppSignSubtable> findBySign(String signId, String type,String cType) throws Exception;

}
