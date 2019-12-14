package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.vo.PerformanceDataQvo;
import com.ylz.bizDo.mangecount.vo.PerformanceVo;

/**
 * Created by zzl on 2018/5/25.
 */
public interface AppPerformanceTableDao {

    public int findCount(PerformanceDataQvo qvo) throws Exception;

    public void addPerformanceOne(PerformanceDataQvo qqvo,String sermeal,String fwType,String perType) throws Exception;

}
