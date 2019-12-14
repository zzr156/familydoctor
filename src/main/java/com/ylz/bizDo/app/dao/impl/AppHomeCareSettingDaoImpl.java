package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppHomeCareSettingDao;
import com.ylz.bizDo.jtapp.drEntity.AppManageCardEntity;
import com.ylz.bizDo.jtapp.drVo.AppManageCardQvo;
import com.ylz.packaccede.allDo.SysDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by asus on 2017/7/6.
 */
@Service("appHomeCareSettingDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppHomeCareSettingDaoImpl implements AppHomeCareSettingDao {
    @Autowired
    private SysDao sysDao;

    @Override
    public List<AppManageCardEntity> findByTeamId(AppManageCardQvo qvo) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT\n" +
                "\tt.ID id,\n" +
                "\tt.HOME_MANAGE_LEVEL homeMangeLevel,\n" +
                "\tt.HOME_MANAGE_STYLE homeManageStyle,\n" +
                "\tt.HOME_MANAGE_CYCLE homeManageCycle,\n" +
                "\tt.HOME_MANAGE_FREQUENCY homeManageFrequency,\n" +
                "\tt.HOME_MANAGE_LEVEL homeMangeLevelName,\n" +
                "\tt.HOME_MANAGE_STYLE homeManageStyleName,\n" +
                "\tt.HOME_MANAGE_CYCLE homeManageCycleName,\n" +
                "\tt.HOME_MANAGE_FREQUENCY homeManageFrequencyName,\n" +
                "\tt.HOME_MANAGE_PEMINDER_DAYS homeManagePeminderDays\n" +
                "FROM\n" +
                "\tAPP_HOME_CARE_SETTING t \n" +
                "WHERE 1=1 ";
        if(StringUtils.isNotBlank(qvo.getTeamId())){
            map.put("teamId",qvo.getTeamId());
            sql += " AND t.HOME_TEAM_ID = :teamId";
        }
        sql += " ORDER BY t.HOME_CREATE_TIME";
        List<AppManageCardEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppManageCardEntity.class);
        return ls;
    }
}
