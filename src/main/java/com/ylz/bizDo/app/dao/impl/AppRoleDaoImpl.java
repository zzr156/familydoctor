package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppRoleDao;
import com.ylz.bizDo.app.po.AppRole;
import com.ylz.bizDo.app.vo.AppRoleQvo;
import com.ylz.packaccede.allDo.SysDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2017/6/14.
 */
@Service("appRoleDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppRoleDaoImpl implements AppRoleDao {
    @Autowired
    private SysDao sysDao;


    @Override
    public List<AppRole> findListQvo(AppRoleQvo qvo) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT * FROM APP_ROLE  as a WHERE 1=1 ";
        if(StringUtils.isNotBlank(qvo.getAppRoleName())){
            map.put("appRoleName", "%"+qvo.getAppRoleName()+"%");
            sql += " AND a.ROLE_NAME like :appRoleName";
        }
        if(StringUtils.isNotBlank(qvo.getAppRoleState())){
            map.put("appRoleState", qvo.getAppRoleState());
            sql += " AND a.ROLE_STATE = :appRoleState";
        }
        return sysDao.getServiceDo().findSqlMap(sql, map, AppRole.class, qvo);
    }
}
