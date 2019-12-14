package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppMunicipalAuthorityDao;
import com.ylz.bizDo.app.po.AppMunicipalAuthority;
import com.ylz.packaccede.allDo.SysDao;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by asus on 2017/08/14.
 */
@Service("appMunicipalAuthorityDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppMunicipalAuthorityDaoImpl implements AppMunicipalAuthorityDao {

    @Autowired
    private SysDao sysDao;

    @Override
    public AppMunicipalAuthority findByAreaCode(String cityCode) throws Exception {
        return (AppMunicipalAuthority) sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(AppMunicipalAuthority.class)
                .add(Restrictions.eq("areaCode", cityCode))
                .uniqueResult();
    }
}
