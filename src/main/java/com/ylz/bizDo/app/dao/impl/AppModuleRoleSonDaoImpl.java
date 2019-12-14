package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppModuleRoleSonDao;
import com.ylz.bizDo.app.entity.AppModuleRoleSonEntity;
import com.ylz.packaccede.allDo.SysDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by asus on 2017/08/03.
 */
@Service("appModuleRoleSonDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppModuleRoleSonDaoImpl implements AppModuleRoleSonDao {

    @Autowired
    private SysDao sysDao;

    @Override
    public void delForm(String id) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("SON_MENU_ID",id);
        String sql = "DELETE FROM APP_MODULE_ROLE_SON WHERE SON_MENU_ID = :SON_MENU_ID";
        this.sysDao.getServiceDo().update(sql,map);
    }

    @Override
    public List<AppModuleRoleSonEntity> ModuleRoleMenu(String id) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("SON_MENU_ID",id);
        String sql = "SELECT a.SON_MODULE_ROLE_ID sonModuleRoleId  FROM APP_MODULE_ROLE_SON a WHERE a.SON_MENU_ID = :SON_MENU_ID";
        return this.sysDao.getServiceDo().findSqlMapRVo(sql,map,AppModuleRoleSonEntity.class);
    }

    @Override
    public String findBySonMenuId(String id) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("SON_MENU_ID",id);
        String sql = "SELECT SON_MODULE_ROLE_ID FROM APP_MODULE_ROLE_SON where SON_MENU_ID = :SON_MENU_ID ";
        List<Map> ls = this.sysDao.getServiceDo().findSqlMap(sql,map);
        if(ls != null && ls.size() >0){
            String result = null;
            for(Map m : ls){
                if(StringUtils.isNotBlank(result)){
                    result += ";" +m.get("SON_MODULE_ROLE_ID").toString();
                }else{
                    result = m.get("SON_MODULE_ROLE_ID").toString();
                }
            }
            return result;
        }
        return null;
    }
}
