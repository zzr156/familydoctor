package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppServeGov;
import com.ylz.bizDo.app.vo.AppServeGovQvo;

import java.util.List;

/**
 * Created by zzl on 2017/8/16.
 */
public interface AppServeGovDao {
    /**
     * 初始查询
     * @param qvo
     * @return
     * @throws Exception
     */
    public List<AppServeGov> findList(AppServeGovQvo qvo) throws Exception;

    public List<AppServeGov> findAllList(String roleType,String areaCode,String hospId) throws Exception;

    public List<AppServeGov> findByEcon(String[] strs) throws Exception;

    /**
     * 查询编号最新
     * @return
     */
    public String findCode() throws Exception;

    public Boolean isReferencedByEG(AppServeGov pk) throws Exception;

    public AppServeGov findByValue(String value) throws Exception;
}
