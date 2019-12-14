package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.entity.AppModuleRoleSonEntity;

import java.util.List;

/**
 * Created by asus on 2017/08/03.
 */
public interface AppModuleRoleSonDao {
    public void delForm(String id) throws Exception;

    public List<AppModuleRoleSonEntity> ModuleRoleMenu(String id) throws Exception;

    public String findBySonMenuId(String id) throws Exception;
}
