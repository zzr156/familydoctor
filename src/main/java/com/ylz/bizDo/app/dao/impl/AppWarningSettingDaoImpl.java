package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppWarningSettingDao;
import com.ylz.bizDo.app.po.AppWarningSetting;
import com.ylz.packaccede.allDo.SysDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lintingjie on 2017/6/21.
 */
@Service("appWarningSettingDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppWarningSettingDaoImpl implements AppWarningSettingDao {
    @Autowired
    private SysDao sysDao;

    @Override
    public List<AppWarningSetting> findSetting(String userId,String type) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("userId",userId);
        map.put("type",type);
        String sql = "select * from APP_WARNING_SETTING where WS_USER_ID = :userId and WS_TYPE = :type";
        return this.sysDao.getServiceDo().findSqlMap(sql,map,AppWarningSetting.class);

    }
}
