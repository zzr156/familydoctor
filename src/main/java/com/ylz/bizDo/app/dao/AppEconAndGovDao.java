package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.po.AppEconAndGov;
import com.ylz.bizDo.app.vo.AppEconAndGovQvo;

import java.util.List;

/**
 * Created by zzl on 2017/8/21.
 */
public interface AppEconAndGovDao {
    /**
     * 初始查询
     * @param qvo
     * @return
     */
    public List<AppEconAndGov> findList(AppEconAndGovQvo qvo) throws Exception;

    public List<AppEconAndGov> findByStr(String roleType,String areaCode,String hospId) throws Exception;

    public Boolean isReferencedByMeal(AppEconAndGov pk) throws Exception;

}
