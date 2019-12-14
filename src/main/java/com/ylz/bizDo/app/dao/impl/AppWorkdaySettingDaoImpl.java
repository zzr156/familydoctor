package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppWorkdaySettingDao;
import com.ylz.bizDo.app.po.AppWorkdaySetting;
import com.ylz.packaccede.allDo.SysDao;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zzl on 2017/6/14.
 */
@Service("appWorkdaySettingDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppWorkdaySettingDaoImpl implements AppWorkdaySettingDao {
    @Autowired
    private SysDao sysDao;

    @Override
    public AppWorkdaySetting findByDoctorId(String drId) throws Exception{
        return (AppWorkdaySetting)sysDao.getServiceDo()
                .getSessionFactory().getCurrentSession()
                .createCriteria(AppWorkdaySetting.class)
                .add(Restrictions.eq("wsDrId", drId))
                .uniqueResult();
    }
}
