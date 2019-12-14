package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.entity.AppMenuEntity;
import com.ylz.bizDo.app.po.AppModuleRole;
import com.ylz.bizDo.app.vo.AppMenuRoleQvo;
import com.ylz.bizDo.app.vo.AppModuleRoleQvo;

import java.util.List;

/**
 * Created by asus on 2017/08/03.
 */
public interface AppModuleRoleDao {
    public List<AppModuleRole> findListQvo(AppModuleRoleQvo qvo);

    public List<AppMenuEntity> findMenuRole(AppMenuRoleQvo qvo);

    public AppModuleRole findAppMouduleRole(AppMenuRoleQvo qvo);

    public String findMenuRoleString(AppMenuRoleQvo qvo);
}
