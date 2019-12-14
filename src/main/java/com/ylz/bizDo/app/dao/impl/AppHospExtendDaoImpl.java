package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppHospExtendDao;
import com.ylz.bizDo.app.po.AppHospExtend;
import com.ylz.packaccede.allDo.SysDao;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zzl on 2017/7/13.
 */
@Service("appHospExtendDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppHospExtendDaoImpl  implements AppHospExtendDao{
    @Autowired
    private SysDao sysDao;

    @Override
    public AppHospExtend findByHospId(String hospId) throws Exception{
        return(AppHospExtend)this.sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(AppHospExtend.class)
                .add(Restrictions.eq("extHospId", hospId))
                .uniqueResult();
    }
}
