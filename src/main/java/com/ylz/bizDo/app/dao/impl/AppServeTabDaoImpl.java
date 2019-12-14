package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppServeTabDao;
import com.ylz.bizDo.app.po.AppServeTab;
import com.ylz.packaccede.allDo.SysDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2017/8/22.
 */
@Service("appServeTabDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppServeTabDaoImpl implements AppServeTabDao {
    @Autowired
    private SysDao sysDao;
    @Override
    public AppServeTab findByDept(String deptId, String type) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT * FROM APP_SERVE_TAB WHERE 1=1";
        map.put("deptId",deptId);
        map.put("type",type);
        sql += " AND SERTAB_DEPT_ID =:deptId";
        sql += " AND SERTAB_TYPE = :type";
        List<AppServeTab> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppServeTab.class);
        if(ls!=null&&ls.size()>0){
            return ls.get(0);
        }
        return null;
    }
}
