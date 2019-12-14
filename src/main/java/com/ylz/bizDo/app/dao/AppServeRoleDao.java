package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.entity.AppServeManageEntity;
import com.ylz.bizDo.app.po.AppServeRole;
import com.ylz.bizDo.app.vo.AppServeManageQvo;
import com.ylz.bizDo.app.vo.AppServeRoleQvo;

import java.util.List;

/**
 * Created by asus on 2017/11/02.
 */
public interface AppServeRoleDao {
    /**
     * 分页查询
     * @param qvo
     * @return
     */
    public List<AppServeRole> findList(AppServeRoleQvo qvo) throws Exception;

    /**
     * 删除
     * @param id
     */
    public void delForm(String id) throws Exception;

    /**
     * 查询服务类型频次
     * @param qvo
     * @return
     */
    public List<AppServeManageEntity> findManageEntity(AppServeManageQvo qvo) throws Exception;

    public AppServeManageEntity findManageByOne(String type,String result,String hospId,String areaCode) throws Exception;
}
