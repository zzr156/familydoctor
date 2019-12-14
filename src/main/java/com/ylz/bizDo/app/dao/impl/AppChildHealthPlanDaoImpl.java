package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppChildHealthPlanDao;
import com.ylz.bizDo.app.po.AppChildHealthPlan;
import com.ylz.bizDo.jtapp.patientEntity.AppChildHealthPlanEntity;
import com.ylz.bizDo.jtapp.patientVo.AppChildHealthQvo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.exception.DaoException;
import com.ylz.packcommon.common.util.ChildHealthPlanUtil;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by zzl on 2017/6/22.
 */
@Service("appChildHealthPlanDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppChildHealthPlanDaoImpl implements AppChildHealthPlanDao {
    @Autowired
    private SysDao sysDao;
    @Override
    public List<AppChildHealthPlanEntity> savePlan(AppChildHealthQvo qvo) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("userId",qvo.getUserId());
        map.put("birthDay",ExtendDate.getCalendar(qvo.getBirthDay()));
        String sql = " SELECT * FROM APP_CHILD_HEALTH_PLAN WHERE 1=1";
        sql += " AND CHP_USER_ID =:userId AND CHP_BIRTHDAY =:birthDay";
        sql += " ORDER BY CHP_PLAN_DATE";
        List<AppChildHealthPlan> list = sysDao.getServiceDo().findSqlMap(sql,map,AppChildHealthPlan.class);
        List<AppChildHealthPlanEntity> tablels = new ArrayList<AppChildHealthPlanEntity>();
        if(list!=null && list.size()>0){
            for(AppChildHealthPlan ls:list){
                AppChildHealthPlanEntity table = new AppChildHealthPlanEntity();
                table.setId(ls.getId());
                table.setBirthDay(ExtendDate.getSqlTimestamp(ExtendDate.getYMD_h_m_s(ls.getChpBirthDay())));
                table.setPlanDate(ExtendDate.getSqlTimestamp(ExtendDate.getYMD_h_m_s(ls.getChpPlanDate())));
                table.setTitle(ls.getChpTitle());
                if(ls.getChpPlanDate().getTimeInMillis()<Calendar.getInstance().getTimeInMillis()){
                    table.setState("1");//时间已过
                }else{
                    table.setState(ls.getChpState());
                }
                tablels.add(table);
            }
            return tablels;
        }else{//无数据则保存数据
            Integer[] num= new Integer[]{1,3,6,8,12,18,24,30,36,48,60,72};
            String[] title = new String[]{
                    "满月龄","3月龄","6月龄","8月龄","12月龄","18月龄","24月龄",
                    "30月龄","3岁龄","4岁龄","5岁龄","6岁龄"
            };
            for(int i=0;i<12;i++){
                AppChildHealthPlan plan = new AppChildHealthPlan();
                plan.setChpBirthDay(ExtendDate.getCalendar(qvo.getBirthDay()));
                plan.setChpUserId(qvo.getUserId());
                plan.setChpPlanDate(new ChildHealthPlanUtil().planTime(qvo.getBirthDay(),num[i]));//体检日期
                plan.setChpTitle(title[i]);
                if(plan.getChpPlanDate().getTimeInMillis()<Calendar.getInstance().getTimeInMillis()){
                    plan.setChpState("1");
                }else{
                    plan.setChpState("0");
                }
                sysDao.getServiceDo().add(plan);
                AppChildHealthPlanEntity table = new AppChildHealthPlanEntity();
                table.setId(plan.getId());
                table.setBirthDay(ExtendDate.getSqlTimestamp(ExtendDate.getYMD_h_m_s(plan.getChpBirthDay())));
                table.setPlanDate(ExtendDate.getSqlTimestamp(ExtendDate.getYMD_h_m_s(plan.getChpPlanDate())));
                table.setTitle(plan.getChpTitle());
                table.setState(plan.getChpState());
                tablels.add(table);
            }
            return tablels;
        }
    }

    @Override
    public List<AppChildHealthPlan> findByYj(Calendar cal) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String ca=ExtendDate.getYMD(cal);
        map.put("planDate",ExtendDate.getCalendar(ca));
        String sql = "SELECT a.* FROM APP_CHILD_HEALTH_PLAN a JOIN APP_HAELTHCARE_SETTING b ON a.CHP_USER_ID = b.HC_PATIENT_ID"+
                " WHERE a.CHP_PLAN_DATE = DATE_ADD(:planDate,INTERVAL b.HC_REMINGD_DAY+0 DAY) AND a.CHP_TXSTATE !='1'";
        List<AppChildHealthPlan> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppChildHealthPlan.class);
        return ls;

    }

    /**
     * 根据当前用户
     * @param chpUserId
     * @param childUserId
     * @return
     */
    @Override
    public List<AppChildHealthPlan> findByChildUserId(String chpUserId,String childUserId)throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("CHP_USER_ID",chpUserId);
        String sql = "SELECT * FROM APP_CHILD_HEALTH_PLAN t WHERE t.CHP_USER_ID = :CHP_USER_ID  ";
        if(StringUtils.isNotBlank(childUserId)){
            map.put("CHP_CHILD_USER_ID",childUserId);
            sql += " AND t.CHP_CHILD_USER_ID = :CHP_CHILD_USER_ID ";
        }else{
            sql += " AND t.CHP_CHILD_USER_ID IS NULL ";
        }
        List<AppChildHealthPlan> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppChildHealthPlan.class);
        return ls;
    }

    @Override
    public void saveChildPlan(AppChildHealthQvo qvo) throws Exception{
        List<AppChildHealthPlan> lss = this.findByChildUserId(qvo.getUserId(),qvo.getChildUserId());
        if(lss!=null&&lss.size()>0){
            for(AppChildHealthPlan ls:lss){
                sysDao.getServiceDo().delete(ls);
            }
        }
        sysDao.getServiceDo().getSessionFactory().getCurrentSession().flush();
        String[] strs =new String[]{qvo.getJan(),qvo.getMar(),qvo.getJun(),qvo.getAug(),qvo.getDec(),qvo.getEtMounth(),qvo.getTfMounth(),qvo.getTtyMounth(),qvo.getThreeYear(),qvo.getFourYear(),qvo.getFiveYear(),qvo.getSexYear()};
        String[] title = new String[]{
                "满月龄","3月龄","6月龄","8月龄","12月龄","18月龄","24月龄",
                "30月龄","3岁龄","4岁龄","5岁龄","6岁龄"
        };
        for(int i=0;i<12;i++){
            AppChildHealthPlan table = new AppChildHealthPlan();
            if(StringUtils.isNotBlank(qvo.getChildUserId())){
                table.setChpChildUserId(qvo.getChildUserId());
            }
            if(StringUtils.isNotBlank(qvo.getChildUserName())){
                table.setChpChildName(qvo.getChildUserName());
            }
            table.setChpUserId(qvo.getUserId());
            table.setChpBirthDay(ExtendDate.getCalendar(qvo.getBirthDay()));
            table.setChpTitle(title[i]);
            table.setChpPlanDate(ExtendDate.getCalendar(strs[i]));
            table.setChpState("0");
            table.setChpTxState("0");
            sysDao.getServiceDo().add(table);
        }


    }

    @Override
    public List<AppChildHealthPlanEntity> findById(String userId,String childUserId) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("userId",userId);
        map.put("childUserId",childUserId);
        String sql = " SELECT a.ID id,a.CHP_BIRTHDAY birthDay,a.CHP_PLAN_DATE planDate," +
                "a.CHP_TITLE title,a.CHP_USER_ID userId,a.CHP_STATE state,a.CHP_CHILD_NAME userName FROM APP_CHILD_HEALTH_PLAN a" +
                " WHERE a.CHP_USER_ID =:userId ";

        if(StringUtils.isNotBlank(childUserId)){
            sql += " AND a.CHP_CHILD_USER_ID = :childUserId";
        }else{
            sql += " AND a.CHP_CHILD_USER_ID IS NULL";
        }
        sql += " ORDER BY a.CHP_PLAN_DATE ";
        List<AppChildHealthPlanEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppChildHealthPlanEntity.class);
        return ls;
    }

    /**
     * 体检提醒
     * @param cal
     * @param userId
     * @return
     * @throws DaoException
     */
    @Override
    public List<AppChildHealthPlan> findByYj(Calendar cal, String userId) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String ca=ExtendDate.getYMD(cal);
        map.put("userId",userId);
        map.put("planDate",ExtendDate.getCalendar(ca));
        String sql = "SELECT\n" +
                "\ta.*\n" +
                "FROM\n" +
                "\tAPP_CHILD_HEALTH_PLAN a\n" +
                "INNER JOIN APP_WARNING_SETTING b ON a.CHP_USER_ID = b.WS_USER_ID\n" +
                "WHERE\n" +
                "\ta.CHP_PLAN_DATE = DATE_ADD(\n" +
                "\t\tDATE(NOW()),\n" +
                "\t\tINTERVAL b.WS_NUM DAY\n" +
                "\t)\n" +
                "AND a.CHP_USER_ID = :userId\n" +
                "AND b.WS_TYPE = '1'\n" +
                "AND b.WS_STATE = '1' " +
                "AND a.CHP_TXSTATE != '1'";
        List<AppChildHealthPlan> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppChildHealthPlan.class);
        return ls;
    }
}
