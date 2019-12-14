package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppServeTab;

/**
 * Created by zzl on 2017/8/22.
 */
public interface AppServeTabDao {

    public AppServeTab findByDept(String deptId,String type) throws Exception;
}
