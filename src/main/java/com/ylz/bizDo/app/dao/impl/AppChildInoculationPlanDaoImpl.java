package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppChildInoculationPlanDao;
import com.ylz.bizDo.app.po.AppChildInoculationPlan;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.packaccede.allDo.SysDao;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2017/7/22.
 */
@Service("appChildInoculationPlanDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppChildInoculationPlanDaoImpl implements AppChildInoculationPlanDao {
    @Autowired
    private SysDao sysDao;

    @Override
    public List<AppChildInoculationPlan> findByMyKh(String id, String etMykh) throws Exception {
        return sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(CdAddress.class)
                .add(Restrictions.eq("id", id))
                .add(Restrictions.eq("inouclationMykh", etMykh))
                .list();
    }

    @Override
    public void deleteChildMykh(String etMykh,String id) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("INOCULATION_MYKH",etMykh);
        map.put("INOCULATION_USER_ID",id);
        String sqlDel = "DELETE FROM APP_CHILD_INOCULATION_PLAN WHERE INOCULATION_MYKH = :INOCULATION_MYKH AND INOCULATION_USER_ID = :INOCULATION_USER_ID";
        this.sysDao.getServiceDo().update(sqlDel,map);
    }

    @Override
    public List<AppChildInoculationPlan> findList() throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        //date_sub(CURDATE(),interval 1 day)
        map.put("type", "7");
        String sql = "SELECT a.* FROM APP_CHILD_INOCULATION_PLAN a INNER JOIN APP_WARNING_SETTING b ON a.INOCULATION_USER_ID = b.WS_USER_ID " +
                "WHERE a.INOCULATION_DATE = date_sub(CURDATE(),interval -b.WS_NUM+0 day) AND b.WS_TYPE=:type GROUP BY a.INOCULATION_CODE,a.INOCULATION_USER_ID,a.INOCULATION_DATE";
        List<AppChildInoculationPlan> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppChildInoculationPlan.class);
        return ls;
    }

    /**
     *儿童体检免疫提醒
     * @param userId
     * @return
     */
    @Override
    public List<AppChildInoculationPlan> findList(String userId)throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("userId",userId);
        map.put("type", "7");
//        String sql = "SELECT a.* FROM APP_CHILD_INOCULATION_PLAN a INNER JOIN APP_WARNING_SETTING b ON a.INOCULATION_USER_ID = b.WS_USER_ID " +
//                "WHERE a.INOCULATION_DATE = date_sub(CURDATE(),interval -b.WS_NUM+0 day) AND b.WS_TYPE=:type AND b.WS_USER_ID=:userId GROUP BY a.INOCULATION_CODE,a.INOCULATION_USER_ID,a.INOCULATION_DATE";
        String sql = "SELECT\n" +
                "\ta.*\n" +
                "FROM\n" +
                "\tAPP_CHILD_INOCULATION_PLAN a\n" +
                "INNER JOIN APP_WARNING_SETTING b ON a.INOCULATION_USER_ID = b.WS_USER_ID \n" +
                "                \n" +
                "WHERE\n" +
                "\ta.INOCULATION_DATE = date_add(\n" +
                "\t\tDATE(NOW()),\n" +
                "\t\tINTERVAL b.WS_NUM + 0 DAY\n" +
                "\t)\n" +
                "AND b.WS_TYPE =:type\n" +
                "AND b.WS_USER_ID =:userId\n" +
                "AND b.WS_STATE='1'\n" +
                "AND a.INOCULATION_TX_STATE !='1'\n" +
                "GROUP BY\n" +
                "\ta.INOCULATION_CODE,\n" +
                "\ta.INOCULATION_USER_ID,\n" +
                "\ta.INOCULATION_DATE";
        List<AppChildInoculationPlan> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppChildInoculationPlan.class);
        return ls;
    }
}
