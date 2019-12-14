package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppBloodgluDao;
import com.ylz.bizDo.app.po.AppBloodglu;
import com.ylz.bizDo.jtapp.drVo.DeviceQvo;
import com.ylz.packaccede.allDo.SysDao;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service("appBloodgluDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppBloodgluDaoImpl implements AppBloodgluDao {
    @Autowired
    private SysDao sysDao;

    @Override
    public AppBloodglu findByDevId(String bg) throws Exception {
        return (AppBloodglu) sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(AppBloodglu.class)
                .add(Restrictions.eq("bgDevId", bg))
                .uniqueResult();
    }

    @Override
    public List<AppBloodglu> findPage(DeviceQvo vo) throws Exception{
        HashMap<String,Object> map = new HashMap<>();
        String sql = "select * from APP_BLOODGLU where 1=1 ";
        if(vo.getPatientIds().length>0){
            map.put("userId",vo.getPatientIds());
            sql +=" AND BG_PAIENT_ID in (:userId) ";
        }
        sql +=" order by BG_CREATE_TIME DESC";
        return this.sysDao.getServiceDo().findSqlMap(sql,map,AppBloodglu.class,vo);
    }
}
