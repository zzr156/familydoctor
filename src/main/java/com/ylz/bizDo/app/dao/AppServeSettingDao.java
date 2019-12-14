package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.entity.AppServeRoleEntity;
import com.ylz.bizDo.app.po.AppServeSetting;
import com.ylz.bizDo.app.vo.AppServeSettingQvo;

import java.util.List;

/**
 * 服务类型
 * Created by zzl on 2017/7/21.
 */
public interface AppServeSettingDao  {
    /**
     * 查询
     * @param qvo
     * @return
     */
    public List<AppServeSetting> findList(AppServeSettingQvo qvo)  throws Exception;

    /**
     * 根据服务人群和服务类型查询
     * @param reValue
     * @param serValue
     * @return
     */
    public AppServeSetting findBySer(String reValue,String serValue)  throws Exception;

    /**
     * 查询
     * @return
     */
    public List<AppServeRoleEntity> findCmmServe(String serveId)  throws Exception;
}
