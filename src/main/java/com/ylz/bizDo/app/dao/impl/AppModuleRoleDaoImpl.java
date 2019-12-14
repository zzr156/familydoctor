package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppModuleRoleDao;
import com.ylz.bizDo.app.entity.AppMenuEntity;
import com.ylz.bizDo.app.po.AppModuleRole;
import com.ylz.bizDo.app.vo.AppMenuRoleQvo;
import com.ylz.bizDo.app.vo.AppModuleRoleQvo;
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
@Service("appModuleRoleDao")
@Transactional
public class AppModuleRoleDaoImpl implements AppModuleRoleDao {

    @Autowired
    private SysDao sysDao;

    @Override
    public List<AppModuleRole> findListQvo(AppModuleRoleQvo qvo) {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT * FROM APP_MODULE_ROLE  as a WHERE 1=1  ";
        if(StringUtils.isNotBlank(qvo.getType())){
            if(qvo.getType().equals("1")){//区域
                sql += " AND a.MODULE_ROLE_AREA_CODE IS NOT NULL ";
            }
            if(qvo.getType().equals("2")){//医院
                sql += " AND a.MODULE_ROLE_HOSP_ID IS NOT NULL ";
            }
        }
        sql += " ORDER BY a.MODULE_ROLE_AREA_CODE ,a.MODULE_MENU_TYPE ";
        return sysDao.getServiceDo().findSqlMap(sql, map, AppModuleRole.class, qvo);
    }


    @Override
    public List<AppMenuEntity> findMenuRole(AppMenuRoleQvo qvo) {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT\n" +
                "\tM.ID menuId,M.MENU_MODULE menuModule,M.MENU_NAME menuName,M.MENU_VALUE menuValue\n" +
                "FROM\n" +
                "\tAPP_MODULE_ROLE R\n" +
                "INNER JOIN APP_MODULE_ROLE_SON S ON R.ID = S.SON_MENU_ID \n" +
                "INNER JOIN APP_MENU M ON S.SON_MODULE_ROLE_ID = M.ID WHERE 1=1 ";
        if(StringUtils.isNotBlank(qvo.getAreaCode())){
            map.put("MODULE_ROLE_AREA_CODE",qvo.getAreaCode());
            sql += " AND R.MODULE_ROLE_AREA_CODE = :MODULE_ROLE_AREA_CODE";
        }
        if(StringUtils.isNotBlank(qvo.getHospId())){
            map.put("MODULE_ROLE_HOSP_ID",qvo.getHospId());
            sql += " AND R.MODULE_ROLE_HOSP_ID = :MODULE_ROLE_HOSP_ID";
        }

        if(StringUtils.isNotBlank(qvo.getMenuType())){
            map.put("MODULE_MENU_TYPE",qvo.getMenuType());
            sql += " AND R.MODULE_MENU_TYPE = :MODULE_MENU_TYPE";
        }
        sql += " ORDER BY M.MENU_MODULE,M.MENU_SORT ";
        List<AppMenuEntity> ls = this.sysDao.getServiceDo().findSqlMapRVo(sql,map,AppMenuEntity.class);
        if(ls != null && ls.size() >0){
            return  ls;
        }
        return null;
    }

    @Override
    public String findMenuRoleString(AppMenuRoleQvo qvo) {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT\n" +
                "\tGROUP_CONCAT(M.MENU_VALUE) menuValue\n" +
                "FROM\n" +
                "\tAPP_MODULE_ROLE R\n" +
                "INNER JOIN APP_MODULE_ROLE_SON S ON R.ID = S.SON_MENU_ID \n" +
                "INNER JOIN APP_MENU M ON S.SON_MODULE_ROLE_ID = M.ID WHERE 1=1 ";
        if(StringUtils.isNotBlank(qvo.getAreaCode())){
            map.put("MODULE_ROLE_AREA_CODE",qvo.getAreaCode());
            sql += " AND R.MODULE_ROLE_AREA_CODE = :MODULE_ROLE_AREA_CODE";
        }
        if(StringUtils.isNotBlank(qvo.getHospId())){
            map.put("MODULE_ROLE_HOSP_ID",qvo.getHospId());
            sql += " AND R.MODULE_ROLE_HOSP_ID = :MODULE_ROLE_HOSP_ID";
        }
        if(StringUtils.isNotBlank(qvo.getMenuType())){
            map.put("MODULE_MENU_TYPE",qvo.getMenuType());
            sql += " AND R.MODULE_MENU_TYPE = :MODULE_MENU_TYPE";
        }
        if(StringUtils.isNotBlank(qvo.getMenuModule())){
            map.put("MENU_MODULE",qvo.getMenuModule());
            sql += " AND M.MENU_MODULE = :MENU_MODULE";
        }
        sql += " ORDER BY M.MENU_MODULE,M.MENU_SORT ";
        List<Map> ls = this.sysDao.getServiceDo().findSqlMap(sql,map);
        if(ls != null && ls.size() >0){
            if(ls.get(0).get("menuValue")!=null){
                String result = ls.get(0).get("menuValue").toString();
                return result;
            }
        }
        return null;
    }

    @Override
    public AppModuleRole findAppMouduleRole(AppMenuRoleQvo qvo) {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT * FROM APP_MODULE_ROLE R WHERE 1=1 \n";
        if(StringUtils.isNotBlank(qvo.getAreaCode())){
            map.put("MODULE_ROLE_AREA_CODE",qvo.getAreaCode());
            sql += " AND R.MODULE_ROLE_AREA_CODE = :MODULE_ROLE_AREA_CODE";
        }
        if(StringUtils.isNotBlank(qvo.getMenuType())){
            map.put("MODULE_MENU_TYPE",qvo.getMenuType());
            sql += " AND R.MODULE_MENU_TYPE = :MODULE_MENU_TYPE";
        }
        List<AppModuleRole> ls = this.sysDao.getServiceDo().findSqlMap(sql,map,AppModuleRole.class);
        if(ls != null && ls.size() >0){
            return  ls.get(0);
        }
        return null;
    }
}
