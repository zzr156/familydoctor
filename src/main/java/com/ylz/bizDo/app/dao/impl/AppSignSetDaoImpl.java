package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppSignSetDao;
import com.ylz.bizDo.app.po.AppSignSetting;
import com.ylz.bizDo.app.vo.AppSignSetQvo;
import com.ylz.packaccede.allDo.SysDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2018/1/15.
 */
@Service("appSignSetDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppSignSetDaoImpl implements AppSignSetDao {
    @Autowired
    private SysDao sysDao;

    @Override
    public List<AppSignSetting> findList(AppSignSetQvo qvo) throws Exception{
        Map<String,Object> map = new HashMap<>();
        String sql = "SELECT * FROM APP_SIGN_SETTING WHERE 1=1 ";
        if(StringUtils.isNotBlank(qvo.getAreaCode())){
            map.put("areaCode",qvo.getAreaCode());
            sql += " SIGNS_AREA_CODE=:areaCode ";
        }
        sql += " ORDER BY SIGNS_AREA_CODE ASC";
        List<AppSignSetting> list = sysDao.getServiceDo().findSqlMap(sql,map,AppSignSetting.class,qvo);
        return list;
    }
}
