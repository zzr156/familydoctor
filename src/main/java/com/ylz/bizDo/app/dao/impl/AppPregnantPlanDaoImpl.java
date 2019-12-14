package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppPregnantPlanDao;
import com.ylz.bizDo.app.po.AppHealthCareSetting;
import com.ylz.bizDo.app.po.AppPregnantPlan;
import com.ylz.bizDo.jtapp.patientEntity.AppPregnantEntity;
import com.ylz.bizDo.jtapp.patientVo.AppPregnantPlanQvo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.comEnum.CommonWarnSet;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


@Service("appPregnantPlanDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppPregnantPlanDaoImpl implements AppPregnantPlanDao {
    @Autowired
    private SysDao sysDao;

    @Override
    public List<AppPregnantEntity> findPlan(String patientId,String type,String userDate) throws Exception{
        HashMap<String,Object> map = new HashMap<String,Object>();

        String sql = "SELECT ID id, PP_PLAN_DATE planDate, PP_PLAN_TITLE planTitle, PP_STATE state FROM APP_PREGNANT_PLAN " +
                "WHERE 1=1 ";
        if(StringUtils.isNotBlank(patientId)){
            map.put("userId",patientId);
            sql += " AND PP_USER_ID = :userId";
        }
        if(StringUtils.isNotBlank(type)){
            map.put("type",type);
            sql += " AND PP_DATE_TYPE = :type ";
        }
        sql += " ORDER BY PP_PLAN_DATE ";
        List<AppPregnantEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppPregnantEntity.class);
        if(list!=null&&list.size()>0){
            return list;
        }else{
            List<AppPregnantEntity> returnlist = new ArrayList<AppPregnantEntity>();
            List<AppHealthCareSetting> setList = sysDao.getServiceDo().loadByPk(AppHealthCareSetting.class,"hcPatientId",patientId);
            String[] num = {"第一次产检 怀孕12周","第二次产检  怀孕16周","第三次产检 怀孕20周","第四次产检 怀孕24周","第五次产检 怀孕28周",
                    "第六次产检  怀孕30周","第七次产检 怀孕32周","第八次产检 怀孕34周","第九次产检 怀孕36周","第十次产检 怀孕37周","第十一次产检 怀孕38周","第十二次产检 怀孕39周","第十三次产检 怀孕40周"};
            if("2".equals(type)){//末次月经日期
                int[] weeks = {12,16,20,24,28,30,32,34,36,37,38,39,40};
                for (int i = 0; i <weeks.length ; i++) {
                    AppPregnantPlan plan = new AppPregnantPlan();
                    plan.setPpUserId(patientId);
                    plan.setPpDateType(type);
                    plan.setPpUserDate(ExtendDate.getCalendar(userDate));
                    plan.setPpPlanTitle(num[i]);
                    Calendar cal = ExtendDate.getCalendar(userDate);
                    cal.add(Calendar.DATE, weeks[i] * 7);
                    plan.setPpPlanDate(cal);
                    plan.setPpState(CommonEnable.JINYONG.getValue());
                    if(!setList.isEmpty()){
                        plan.setPpRemindDay(setList.get(0).getHcRemindDay());
                        plan.setPpEnableAlert(setList.get(0).getHcEnable());
                    }
                    sysDao.getServiceDo().add(plan);
                    AppPregnantEntity entity = new AppPregnantEntity();
                    entity.setId(plan.getId());
                    entity.setPlanDate(plan.getPpPlanDate().getTime());
                    entity.setPlanTitle(plan.getPpPlanTitle());
                    entity.setState(CommonEnable.JINYONG.getValue());
                    returnlist.add(entity);
                }
            }else if("1".equals(type)){//预产日期
                int[] weeks = {28,24,20,16,12,10,8,6,4,3,2,1,0};
                for (int i = 0; i <weeks.length ; i++) {
                    AppPregnantPlan plan = new AppPregnantPlan();
                    plan.setPpUserId(patientId);
                    plan.setPpDateType(type);
                    plan.setPpUserDate(ExtendDate.getCalendar(userDate));
                    plan.setPpPlanTitle(num[i]);
                    Calendar cal = ExtendDate.getCalendar(userDate);
                    cal.add(Calendar.DATE, -(weeks[i] * 7));
                    plan.setPpPlanDate(cal);
                    plan.setPpState(CommonEnable.JINYONG.getValue());
                    if(!setList.isEmpty()){
                        plan.setPpRemindDay(setList.get(0).getHcRemindDay());
                        plan.setPpEnableAlert(setList.get(0).getHcEnable());
                    }
                    sysDao.getServiceDo().add(plan);
                    AppPregnantEntity entity = new AppPregnantEntity();
                    entity.setId(plan.getId());
                    entity.setPlanDate(plan.getPpPlanDate().getTime());
                    entity.setPlanTitle(plan.getPpPlanTitle());
                    entity.setState(CommonEnable.JINYONG.getValue());
                    returnlist.add(entity);
                }
            }
            return returnlist;
        }


    }

    @Override
    public List<AppPregnantPlan> findPlanAlert() throws Exception{
        HashMap<String,Object> map = new HashMap<String,Object>();
//        String sql = "select * from APP_PREGNANT_PLAN WHERE PP_ENABLE_ALERT = '1' AND PP_PLAN_DATE > NOW() AND DATEDIFF(PP_PLAN_DATE,NOW()) <= cast(PP_REMINGD_DAY as unsigned int)";
        String sql = "SELECT\n" +
                "\t*\n" +
                "FROM\n" +
                "\tAPP_PREGNANT_PLAN\n" +
                "WHERE\n" +
                "\tPP_ENABLE_ALERT = '1'\n" +
                "AND PP_PLAN_DATE >= DATE(NOW())\n" +
                "AND DATEDIFF(PP_PLAN_DATE, NOW()) <= cast(\n" +
                "\tPP_REMINGD_DAY AS UNSIGNED INT\n" +
                ")";
        return sysDao.getServiceDo().findSqlMap(sql,map,AppPregnantPlan.class);
    }

    @Override
    public void setHealthCare(AppPregnantPlanQvo vo) throws Exception{
        List<AppHealthCareSetting> setList = sysDao.getServiceDo().loadByPk(AppHealthCareSetting.class,"hcPatientId",vo.getPatientId());
        if(setList.isEmpty()){
            AppHealthCareSetting set = new AppHealthCareSetting();
            set.setHcEnable(vo.getEnable());
            set.setHcPatientId(vo.getPatientId());
            set.setHcRemindDay(vo.getDate());
            List<AppPregnantPlan> planList = sysDao.getServiceDo().loadByPk(AppPregnantPlan.class,"ppUserId",vo.getPatientId());
            if(!planList.isEmpty()){
                for(AppPregnantPlan plan : planList){
                    plan.setPpRemindDay(vo.getDate());
                    plan.setPpEnableAlert(vo.getEnable());
                }
            }
            sysDao.getServiceDo().add(set);
        }else{
            AppHealthCareSetting set = setList.get(0);
            set.setHcEnable(vo.getEnable());
            set.setHcPatientId(vo.getPatientId());
            set.setHcRemindDay(vo.getDate());
            List<AppPregnantPlan> planList = sysDao.getServiceDo().loadByPk(AppPregnantPlan.class,"ppUserId",vo.getPatientId());
            if(!planList.isEmpty()){
                for(AppPregnantPlan plan : planList){
                    plan.setPpRemindDay(vo.getDate());
                    plan.setPpEnableAlert(vo.getEnable());
                }
            }
            sysDao.getServiceDo().modify(set);
        }
    }

    /**
     * 孕妇保健计划提醒
     * @param userId
     * @return
     */
    @Override
    public List<AppPregnantPlan> findPlanAlert(String userId) throws Exception{
        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("userId",userId);
        /*String sql = "SELECT\n" +
                "\t*\n" +
                "FROM\n" +
                "\tAPP_PREGNANT_PLAN\n" +
                "WHERE\n" +
                "\tPP_ENABLE_ALERT = '1'\n" +
                "AND PP_PLAN_DATE >= DATE(NOW())\n" +
                "AND DATEDIFF(PP_PLAN_DATE, NOW()) <= cast(\n" +
                "\tPP_REMINGD_DAY AS UNSIGNED INT\n" +
                ")";
        sql += " AND PP_USER_ID=:userId";*/
        map.put("txType", CommonWarnSet.TJXT.getValue());
        String sql = "SELECT\n" +
                "\ta.*\n" +
                "FROM\n" +
                "\tAPP_PREGNANT_PLAN a\n" +
                "INNER JOIN APP_WARNING_SETTING b ON a.PP_USER_ID = b.WS_USER_ID\n" +
                "WHERE\n" +
                "\tb.WS_STATE = '1'\n" +
                "AND a.PP_PLAN_DATE >= DATE(NOW())\n" +
                "AND DATEDIFF(a.PP_PLAN_DATE, NOW()) <= cast(\n" +
                "\tb.WS_NUM AS UNSIGNED INT\n" +
                ")\n" +
                "AND a.PP_USER_ID = :userId AND a.PP_READ_STATE !='1' " +
                "AND b.WS_TYPE=:txType";
        return sysDao.getServiceDo().findSqlMap(sql,map,AppPregnantPlan.class);
    }
}
