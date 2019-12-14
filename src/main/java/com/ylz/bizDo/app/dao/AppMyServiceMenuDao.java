package com.ylz.bizDo.app.dao;

import com.ylz.bizDo.app.entity.AppMenuEntity;
import com.ylz.bizDo.app.entity.AppMyServiceEntity;
import com.ylz.bizDo.app.vo.AppMenuRoleQvo;

import java.util.List;

/**
 * Created by asus on 2017/08/19.
 */
public interface AppMyServiceMenuDao {

    public List<AppMyServiceEntity> findMenuRole(AppMenuRoleQvo qvo) throws Exception;

    public void addMySerViceMenu(List<AppMenuEntity> strPatientMenuRole,String drPatientId) throws Exception;

    public void addMySerViceMenu(String drPatientId,String menuId) throws Exception;

    public void delServiceMenu(String drPatientId) throws Exception;
}
