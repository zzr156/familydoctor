package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppServeEcon;
import com.ylz.bizDo.app.vo.AppServeEconQvo;
import com.ylz.bizDo.jtapp.commonEntity.AppSubsidyEntity;

import java.util.List;

/**
 * Created by zzl on 2017/8/16.
 */
public interface AppServeEconDao {
    /**
     * 初始查询
     * @param qvo
     * @return
     */
    public List<AppServeEcon> findList(AppServeEconQvo qvo) throws Exception;

    public List<AppServeEcon> findAllList(String roleType,String areaCode,String hospId) throws Exception;

    /**
     * 查询最新编号
     * @return
     */
    public String findCode() throws Exception;

    public Boolean isReferencedByEG(AppServeEcon pk) throws Exception;

    public AppServeEcon findByValue(String value) throws Exception;

    public List<AppSubsidyEntity> findByJjType(String jjId, String mealId) throws Exception;
}
