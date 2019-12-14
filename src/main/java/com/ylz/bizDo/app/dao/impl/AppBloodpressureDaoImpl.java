package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppBloodpressureDao;
import com.ylz.bizDo.app.po.AppBloodpressure;
import com.ylz.bizDo.jtapp.drVo.DeviceQvo;
import com.ylz.packaccede.allDo.SysDao;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service("appBloodpressureDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppBloodpressureDaoImpl implements AppBloodpressureDao {
    @Autowired
    private SysDao sysDao;


    @Override
    public List<AppBloodpressure> findByUserId(String userId) throws Exception{
        HashMap<String,Object> map = new HashMap<String,Object>();
        String sql = "select * from APP_BLOODPRESSURE where 1=1 ";
        if(StringUtils.isNotBlank(userId)){
            map.put("userId",userId);
            sql +=" AND BP_USER_ONE = :userId or BP_USER_TWO = :userId";
        }
        return this.sysDao.getServiceDo().findSqlMap(sql,map,AppBloodpressure.class);
    }


    @Override
    public AppBloodpressure findByDevId(String bp) throws Exception{
        return (AppBloodpressure) sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(AppBloodpressure.class)
                .add(Restrictions.eq("bpDevId", bp))
                .uniqueResult();
    }

    @Override
    public List<AppBloodpressure> findPage(DeviceQvo vo) throws Exception{
        HashMap<String,Object> map = new HashMap<>();
        String sql = "select * from APP_BLOODPRESSURE where 1=1 ";
        if(vo.getPatientIds().length>0){
            map.put("userId",vo.getPatientIds());
            sql +=" AND BP_USER_ONE in (:userId) or BP_USER_TWO in (:userId)";
        }
        sql +=" order by BP_CREATE_TIME DESC";
        return this.sysDao.getServiceDo().findSqlMap(sql,map,AppBloodpressure.class,vo);
    }
}
