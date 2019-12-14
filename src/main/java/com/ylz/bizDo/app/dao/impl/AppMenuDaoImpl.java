package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppMenuDao;
import com.ylz.bizDo.app.entity.AppMenuEntity;
import com.ylz.bizDo.app.po.AppMenu;
import com.ylz.bizDo.app.vo.AppMenuQvo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.comEnum.CommonEnable;
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
@Service("appMenuDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppMenuDaoImpl implements AppMenuDao {

    @Autowired
    private SysDao sysDao;

    @Override
    public List<AppMenu> findListQvo(AppMenuQvo qvo) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT * FROM APP_MENU  as a WHERE 1=1 ORDER BY a.MENU_TYPE ,a.MENU_SORT ";
        return sysDao.getServiceDo().findSqlMap(sql, map, AppMenu.class, qvo);
    }

    @Override
    public List<AppMenu> findMenulist(AppMenuQvo qvo) {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT * FROM APP_MENU  as a WHERE 1=1  ";
        if(StringUtils.isNotBlank(qvo.getType())){
            map.put("MENU_TYPE",qvo.getType());
            sql += " AND a.MENU_TYPE = :MENU_TYPE";
        }
        map.put("MENU_STATE", CommonEnable.QIYONG.getValue());
        sql += " AND a.MENU_STATE = :MENU_STATE";
        sql += " ORDER BY a.MENU_TYPE ,a.MENU_SORT ";
        return sysDao.getServiceDo().findSqlMap(sql, map, AppMenu.class);
    }


    @Override
    public List<AppMenuEntity> findMenuEntity(AppMenuQvo qvo) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT\n" +
                "\tM.ID menuId,M.MENU_MODULE menuModule,M.MENU_NAME menuName,M.MENU_VALUE menuValue\n" +
                "FROM\n" +
                "\tAPP_MENU M WHERE 1=1 ";
        if(StringUtils.isNotBlank(qvo.getType())){
            map.put("MENU_TYPE",qvo.getType());
            sql += " AND M.MENU_TYPE = :MENU_TYPE";
        }
        if(StringUtils.isNotBlank(qvo.getMenuCode())){
            sql += " AND M.MENU_SORT in ('01','03','06','19','20','21','22','23','24','26','27','46','48')";
        }
        map.put("MENU_STATE","1");
        sql += "AND MENU_STATE = :MENU_STATE ";
        sql += " ORDER BY M.MENU_MODULE,M.MENU_SORT ";
        return this.sysDao.getServiceDo().findSqlMapRVo(sql,map,AppMenuEntity.class);
    }
}


