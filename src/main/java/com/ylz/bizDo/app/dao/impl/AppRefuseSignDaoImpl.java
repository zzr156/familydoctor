package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppRefuseSignDao;
import com.ylz.bizDo.app.po.AppRefuseSign;
import com.ylz.packaccede.allDo.SysDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

/**
 * Created by lintingjie on 2017/6/28.
 */
@Service("appRefuseSignDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppRefuseSignDaoImpl implements AppRefuseSignDao {
    @Autowired
    private SysDao sysDao;

    @Override
    public AppRefuseSign findByIdno(String patientIdno) throws Exception{
        HashMap<String ,Object> map = new HashMap<String ,Object>();
        map.put("idno", patientIdno);
        String sql = "SELECT * FROM APP_REFUSE_SIGN WHERE RS_PATIENT_IDNO = :idno";
        List<AppRefuseSign> list = sysDao.getServiceDo().findSqlMap(sql,map,AppRefuseSign.class);
        if(!list.isEmpty()){
            return list.get(0);
        }
        return null;
    }
}
