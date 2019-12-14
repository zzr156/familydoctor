package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppDrPatientKeyDao;
import com.ylz.bizDo.app.po.AppDrPatientKey;
import com.ylz.packaccede.allDo.SysDao;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("appDrPatientKeyDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppDrPatientKeyDaoImpl implements AppDrPatientKeyDao {

    @Autowired
    private SysDao sysDao;


    @Override
    public AppDrPatientKey findByDoctorOrPatientId(String id) throws Exception{
        return (AppDrPatientKey) sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(AppDrPatientKey.class)
                .add(Restrictions.eq("drPatientId", id))
//                .setCacheable(true)
                .uniqueResult();
    }

    public AppDrPatientKey findDrPatientKeyByToken(String drToken) throws Exception{
        return (AppDrPatientKey) sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(AppDrPatientKey.class)
                .add(Restrictions.or(Restrictions.eq("drToken",drToken),
                        Restrictions.eq("drWechatToken",drToken),
                        Restrictions.eq("drTvToken",drToken),
                        Restrictions.eq("drExternalToken",drToken),
                        Restrictions.eq("drExternalWechatToken",drToken)))
//                .setCacheable(true)
                .uniqueResult();
    }

    public AppDrPatientKey findDrTempKeyByToken(String drToken) throws Exception{
        return (AppDrPatientKey) sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(AppDrPatientKey.class)
                .add(Restrictions.eq("drTempToken", drToken))
//                .setCacheable(true)
                .uniqueResult();
    }

    public Map findDrPatientKeyByTokenSql(String drToken) throws Exception{
        try{
            Map<String,Object> map = new HashMap<String,Object>();
            String sql = "SELECT * FROM APP_DR_PATIENT_KEY WHERE 1=1 ";
            if(StringUtils.isNotBlank(drToken)){
                map.put("DR_TOKEN",drToken);
                sql += " AND (DR_TOKEN =:DR_TOKEN OR DR_WECHAT_TOKEN  =:DR_TOKEN)";
            }
            List<Map> ls = sysDao.getServiceReadDo().findSqlMap(sql,map);
            if(ls != null && ls.size() >0){
                return ls.get(0);
            }
            return null;
        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }

    @Override
    public AppDrPatientKey findByDrOrPatient(String userId) throws Exception{
        if(StringUtils.isNotBlank(userId)){
            Map<String,Object> map = new HashMap<>();
            map.put("userId",userId);
            String sql = "SELECT * FROM app_dr_patient_key where DR_PATIENT_ID =:userId ";
            List<AppDrPatientKey> list = sysDao.getServiceDo().findSqlMap(sql,map,AppDrPatientKey.class);
            if(list != null && list.size()>0){
                return list.get(0);
            }
        }
        return null;
    }
}
