package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppBasicDataTempDao;
import com.ylz.bizDo.app.po.AppBasicDataTemp;
import com.ylz.packaccede.allDo.SysDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by zzl on 2018/4/13.
 */
@Service("appBasicDataTempDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppBasicDataDaoTempImpl implements AppBasicDataTempDao {

    @Autowired
    private SysDao sysDao;

    @Override
    public AppBasicDataTemp getBasicDataTemp(String basicId) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("BASICE_DATA_ID",basicId);
        String sql = "SELECT * FROM APP_BASIC_DATA_TEMP WHERE 1=1 AND BASICE_DATA_ID = :BASICE_DATA_ID ";
        List<AppBasicDataTemp> list = sysDao.getServiceDo().findSqlMap(sql,map,AppBasicDataTemp.class);
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }
}
