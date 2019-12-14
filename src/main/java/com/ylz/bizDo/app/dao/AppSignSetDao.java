package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppSignSetting;
import com.ylz.bizDo.app.vo.AppSignSetQvo;

import java.util.List;

/**
 * Created by zzl on 2018/1/15.
 */
public interface AppSignSetDao {
    /**
     * 查询
     * @param qvo
     * @return
     */
    public List<AppSignSetting> findList(AppSignSetQvo qvo) throws Exception;

}
