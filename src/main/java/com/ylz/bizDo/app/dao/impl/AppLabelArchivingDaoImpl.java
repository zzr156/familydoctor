package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppLabelArchivingDao;
import com.ylz.bizDo.app.po.AppLabelArchiving;
import com.ylz.packaccede.allDo.SysDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2019/3/27.
 */
@Service("appLabelArchivingDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppLabelArchivingDaoImpl implements AppLabelArchivingDao {
    @Autowired
    private SysDao sysDao;

    @Override
    public AppLabelArchiving findByIdAndValue(String value, String id) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("value",value);
        map.put("labelId",id);
        String sql = "SELECT * FROM APP_LABEL_ARCHIVING WHERE 1=1 AND LABEL_VALUE =:value AND LABEL_ARCHIVING_ID =:labelId ";
        List<AppLabelArchiving> list = sysDao.getServiceDo().findSqlMap(sql,map,AppLabelArchiving.class);
        if(list != null && list.size()>0){
            return list.get(0);
        }
        return null;
    }
}
