package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppHospExtend;

/**
 * Created by zzl on 2017/7/13.
 */
public interface AppHospExtendDao {

    public AppHospExtend findByHospId(String hospId) throws Exception;
}
