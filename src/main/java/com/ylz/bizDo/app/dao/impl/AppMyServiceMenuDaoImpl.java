package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppMyServiceMenuDao;
import com.ylz.bizDo.app.entity.AppMenuEntity;
import com.ylz.bizDo.app.entity.AppMyServiceEntity;
import com.ylz.bizDo.app.po.AppMyServiceMenu;
import com.ylz.bizDo.app.vo.AppMenuRoleQvo;
import com.ylz.packaccede.allDo.SysDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by asus on 2017/08/19.
 */
@Service("appMyServiceMenuDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppMyServiceMenuDaoImpl implements AppMyServiceMenuDao {

    @Autowired
    private SysDao sysDao;



    @Override
    public List<AppMyServiceEntity> findMenuRole(AppMenuRoleQvo qvo) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT\n" +
                "\tM.ID menuId,\n" +
                "\tM.MENU_MODULE menuModule,\n" +
                "\tM.MENU_NAME menuName,\n" +
                "\tM.MENU_VALUE menuValue\n" +
                "FROM\n" +
                "\tAPP_MENU M \n" +
                "INNER JOIN APP_MY_SERVICE_MENU S ON M.ID = S.SERVICE_MENU_ID\n" +
                "WHERE\n" +
                "\t1 = 1 ";
        if(StringUtils.isNotBlank(qvo.getDrPaiteintId())){
            map.put("SERVICE_DR_PATIENT_ID",qvo.getDrPaiteintId());
            sql += " AND S.SERVICE_DR_PATIENT_ID = :SERVICE_DR_PATIENT_ID";
        }
        sql += " ORDER BY S.SERVICE_SORT ";
        List<AppMyServiceEntity> ls = this.sysDao.getServiceDo().findSqlMapRVo(sql,map,AppMyServiceEntity.class);
        if(ls != null && ls.size() >0){
            return  ls;
        }
        return null;
    }

    @Override
    public void addMySerViceMenu(List<AppMenuEntity> lsRole,String drPatientId) throws Exception{
        if(lsRole != null && lsRole.size() >0){
            int i = 1;
            for(AppMenuEntity v : lsRole){
                if(v.getMenuModule().equals("1")){//首页
                    AppMyServiceMenu menu = new AppMyServiceMenu();
                    menu.setServiceDrPatientId(drPatientId);
                    menu.setServiceMenuId(v.getMenuId());
                    menu.setServiceSort(i);
                    i++;
                    sysDao.getServiceDo().add(menu);
                }
            }
        }
    }


    @Override
    public void addMySerViceMenu(String drPatientId, String menuId) throws Exception{
        String[] menuIds = menuId.split(";");
        if(menuIds != null){
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("SERVICE_DR_PATIENT_ID",drPatientId);
            String sql = "DELETE FROM APP_MY_SERVICE_MENU WHERE SERVICE_DR_PATIENT_ID = :SERVICE_DR_PATIENT_ID";
            this.sysDao.getServiceDo().update(sql,map);
            int i = 1;
            for(String id : menuIds){
                AppMyServiceMenu menu = new AppMyServiceMenu();
                menu.setServiceDrPatientId(drPatientId);
                menu.setServiceMenuId(id);
                menu.setServiceSort(i);
                i++;
                sysDao.getServiceDo().add(menu);
            }
        }
    }

    @Override
    public void delServiceMenu(String drPatientId) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("SERVICE_DR_PATIENT_ID",drPatientId);
        String sql = "DELETE FROM APP_MY_SERVICE_MENU WHERE SERVICE_DR_PATIENT_ID = :SERVICE_DR_PATIENT_ID";
        this.sysDao.getServiceDo().update(sql,map);
    }


}
