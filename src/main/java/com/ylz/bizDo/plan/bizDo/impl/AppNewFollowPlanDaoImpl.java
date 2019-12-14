package com.ylz.bizDo.plan.bizDo.impl;

import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.app.po.AppSerial;
import com.ylz.bizDo.plan.Entity.AppFollowGroupEntity;
import com.ylz.bizDo.plan.Entity.AppFollowListEntity;
import com.ylz.bizDo.plan.Entity.AppFollowPlanXYEntity;
import com.ylz.bizDo.plan.Entity.AppFollowRecordEntity;
import com.ylz.bizDo.plan.bizDo.AppNewFollowPlanDao;
import com.ylz.bizDo.plan.po.AppFollowPlan;
import com.ylz.bizDo.plan.vo.AppFollowAddQvo;
import com.ylz.bizDo.plan.vo.AppFollowGroupQvo;
import com.ylz.bizDo.plan.vo.AppFollowPlanQvo;
import com.ylz.bizDo.plan.vo.AppFollowStateQvo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.comEnum.*;
import com.ylz.packcommon.common.util.ExtendDate;
import com.ylz.packcommon.common.util.ExtendDateUtil;
import com.ylz.packcommon.common.util.PropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by Administrator on 2017-05-12.
 */
@Service("appNewFollowPlanDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppNewFollowPlanDaoImpl implements AppNewFollowPlanDao {

    @Autowired
    public SysDao sysDao;

    /**
     * 根据服务人群筛选
     * @param qvo group
     * @return
     */
    @Override
    public List<AppFollowGroupEntity> findFollowByGroup(AppFollowGroupQvo qvo) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        String sql = "SELECT\n" +
                "   d.ID followId,"+
                "	c.ID patientId,\n" +
                "	c.PATIENT_NAME patientName,\n" +
                "	c.PATIENT_GENDER patientGender,\n" +
                "	'' patientAge,\n" +
                "	c.PATIENT_IMAGEURL imgurl,\n" +
                "	(SELECT GROUP_CONCAT(LABEL_TITLE) FROM APP_LABEL_GROUP g WHERE g.LABEL_TYPE = 3	AND g.LABEL_SIGN_ID = a.ID) signPersGroup,\n" +
                "   '' labelType,"+
                "    d.SF_REFERRAL referral,"+
                "	(SELECT DATE_FORMAT(max(k.SF_FOLLOW_DATE), '%Y-%m-%d') FROM APP_FOLLOW_PLAN k	WHERE k.SF_FOLLOW_STATE = '0' AND c.ID = k.SF_FOLLOW_PATIENTID) followDate\n" +
                "FROM\n" +
                "	APP_SIGN_FORM a\n" +
                "LEFT JOIN APP_LABEL_GROUP b ON a.ID = b.LABEL_SIGN_ID\n" +
                "LEFT JOIN APP_PATIENT_USER c ON a.SIGN_PATIENT_ID = c.ID\n" +
                "LEFT JOIN APP_FOLLOW_PLAN d ON d.SF_FOLLOW_PATIENTID = c.ID\n" +
                "WHERE a.SIGN_STATE IN (:signState) AND b.LABEL_VALUE != :labelValue ";//已签约的，不包含普通人群
        String[] signStates = new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
        map.put("signState", signStates);//已签约
        map.put("labelValue", ResidentMangeType.PTRQ.getValue());//普通人群
        //原方式查询签约医生为当前医生的人
        if(StringUtils.isNotBlank(qvo.getDrId())) {
            sql += " AND a.SIGN_DR_ID = :drId ";
            map.put("drId", qvo.getDrId());
        }
        //现改为查询当前团队的人
        if(StringUtils.isNotBlank(qvo.getTeamId())){
            map.put("teamId",qvo.getTeamId());
            sql += " AND a.SIGN_TEAM_ID = :teamId ";
        }
        if(StringUtils.isNotBlank(qvo.getGroup())){
           String[] groups = qvo.getGroup().split(";");
           for(String group:groups){
               map.put("group",group);
               sql +=" AND b.LABEL_VALUE = :group ";
           }
        }
        if(StringUtils.isNotBlank(qvo.getPatientName())){
            sql += " AND c.PATIENT_NAME like :patientName ";
            map.put("patientName", "%"+qvo.getPatientName()+"%");
        }
        sql +=" GROUP BY c.ID ORDER BY d.SF_FOLLOW_DATE  ";
        List<AppFollowGroupEntity> ls=sysDao.getServiceDo().findSqlMapRVo(sql,map, AppFollowGroupEntity.class,qvo);
        return ls;
    }

    /**
     * 新增随访计划(制定随访计划)
     * @param qvo patientId;//多个用分割隔开
     * @param qvo followWay;//随访方式
     * @param qvo teamId;
     * @param qvo followDate;//随访计划时间
     * @return
     */
    @Override
    public AppFollowPlan addFollowPlan(AppFollowAddQvo qvo, AppDrUser drUser) throws Exception {
        String num = "";
        String deptId = "";
        String hospAreaCode = "";
        AppFollowPlan returnPlan = new AppFollowPlan();
        AppHospDept dept= (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
        if(dept!=null && dept.getHospAreaCode()!=null) {
            AppSerial serial = sysDao.getAppSignformDao().getAppSerial(dept.getHospAreaCode().substring(0, 4), "batch");
            if(serial!=null) {
                Map<String,Object> bcnum = sysDao.getAppSignformDao().getNum(serial.getSerialNo(),SignFormType.APPSTATE.getValue());
                serial.setSerialNo(bcnum.get("old").toString());
                sysDao.getServiceDo().modify(serial);
                num = bcnum.get("new").toString();
//                plan.setSfFollowNum(bcnum);//批次号
            }
            deptId=dept.getId();
            hospAreaCode = dept.getHospAreaCode();
        }
        //查询服务类型
        String labelValue = getLabelVaue(drUser.getId(),qvo.getPatientId());
        if(StringUtils.isNotBlank(labelValue)){
            String[] labelValues = labelValue.split(",");
            for(String value:labelValues){
//                if(ResidentMangeType.ETLZLS.getValue().equals(value)||ResidentMangeType.YCF.getValue().equals(value)
//                        ||ResidentMangeType.GXY.getValue().equals(value)||ResidentMangeType.TNB.getValue().equals(value)){
                    AppFollowPlan plan = new AppFollowPlan();
                    List<AppFollowPlan> planList = findFollowPlan(drUser.getId(),qvo.getPatientId(),FollowPlanState.WEIWANCHENG.getValue(),value);
                    if(planList!=null && planList.size()>0){
                        if(planList.get(0).getSfFollowDate()!=null && Calendar.getInstance().equals(planList.get(0).getSfFollowDate()) && Calendar.getInstance().before(ExtendDate.getCalendar(qvo.getFollowDate()))){//延期随访
                            planList.get(0).setSfFollowState(FollowPlanState.YANQI.getValue());
                        }else if(planList.get(0).getSfFollowDate()!=null && planList.get(0).getSfFollowDate().before(Calendar.getInstance())){//过期随访
                            planList.get(0).setSfFollowState(FollowPlanState.GUOQI.getValue());
                        } else {
                            plan = planList.get(0);
                        }
                    }
                    if(StringUtils.isNotBlank(qvo.getPatientId())) {
                        AppPatientUser patientUser = (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class, qvo.getPatientId());
                        plan.setSfFollowPatientid(qvo.getPatientId());
                        plan.setSfFollowPatientName(patientUser.getPatientName());
                    }
                    plan.setSfFollowType(value);
                    plan.setSfFollowNum(num);//批次号
                    plan.setSfHosId(deptId);
                    plan.setSfHosAreaCode(hospAreaCode);
                    plan.setSfFollowDoctorid(drUser.getId());
                    plan.setSfFollowMode(qvo.getFollowWay());
                    plan.setSfFollowState(FollowPlanState.WEIWANCHENG.getValue());//随访状态未开始
                    plan.setSfTeamId(qvo.getTeamId());
                    plan.setSfFollowDate(ExtendDate.getCalendar(qvo.getFollowDate()));
                    plan.setSfIsTemporary(qvo.getIsTemporary());//是否是临时随访（0否 1是）
                    //plan.setSfRemindPlan(qvo.getSfRemindPlan());//随访提醒方案
                    List<AppFollowPlan> followPlanList = findFollowPlan(null,qvo.getPatientId(),FollowPlanState.YIWANCHENG.getValue(),value);
                    if(followPlanList!=null && followPlanList.size()>0){
                        plan.setSfPid(followPlanList.get(0).getSfFollowNum());
                    }
                    if(plan.getId()==null){
                        plan.setSfCreateDate(Calendar.getInstance());
                        sysDao.getServiceDo().add(plan);
                    }else{
                        sysDao.getServiceDo().modify(plan);
                    }
                    returnPlan = plan;
//                }
            }
        }
        return returnPlan;
    }

    /**
     * 查询随访计划
     * @param qvo group
     * @param qvo day
     * @param qvo patientName
     * @param qvo drId
     * @return
     */
    @Override
    public List<AppFollowGroupEntity> findFollowPlan(AppFollowGroupQvo qvo) throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("state",FollowPlanState.WEIWANCHENG.getValue());
        String sql = "SELECT cc.* FROM (SELECT aa.* FROM (SELECT\n" +
                "   d.ID followId,"+
                "	c.ID patientId," +
                "   d.SF_FOLLOW_NUM followNum,\n" +
                "	c.PATIENT_NAME patientName,\n" +
                "	c.PATIENT_GENDER patientGender,\n" +
                "	c.PATIENT_AGE patientAge,\n" +
                "	c.PATIENT_IMAGEURL imgurl,\n" +
                "	(SELECT GROUP_CONCAT(LABEL_TITLE) FROM APP_LABEL_GROUP g WHERE g.LABEL_TYPE = 3	AND g.LABEL_SIGN_ID = a.ID) signPersGroup,\n" +
                "   null labelType,"+
                "	DATE_FORMAT(d.SF_FOLLOW_DATE, '%Y-%m-%d') followDate\n" +
                "FROM\n" +
                "	APP_SIGN_FORM a\n" +
                "LEFT JOIN APP_LABEL_GROUP b ON a.ID = b.LABEL_SIGN_ID\n" +
                "LEFT JOIN APP_PATIENT_USER c ON a.SIGN_PATIENT_ID = c.ID\n" +
                "LEFT JOIN APP_FOLLOW_PLAN d ON d.SF_FOLLOW_PATIENTID = c.ID\n" +
                "WHERE  d.SF_FOLLOW_STATE = :state ";
        //原方式默认token获取医生id（签约医生添加的随访计划）带入
       /* if(StringUtils.isNotBlank(qvo.getDrId())) {
            sql += " AND d.SF_FOLLOW_DOCTORID = :drId ";
            map.put("drId", qvo.getDrId());
        }*/
       //现方式是带团队查询
        if(StringUtils.isNotBlank(qvo.getTeamId())){
            map.put("teamId",qvo.getTeamId());
            sql += " AND a.SIGN_TEAM_ID = :teamId ";
        }

        if(StringUtils.isNotBlank(qvo.getGroup())){
            String[] groups = qvo.getGroup().split(";");
            for(String group:groups){
                map.put("group",group);
                sql +=" AND b.LABEL_VALUE = :group ";
            }
        }
        if(StringUtils.isNotBlank(qvo.getPatientName())){
            sql += " AND c.PATIENT_NAME like :patientName ";
            map.put("patientName", "%"+qvo.getPatientName()+"%");
        }
        if(StringUtils.isNotBlank(qvo.getDay())){
            if("-1".equals(qvo.getDay())){//未完成
                sql +=" AND d.SF_FOLLOW_DATE < CURDATE() ";
            }else if("0".equals(qvo.getDay())){//当日
                sql +=" AND d.SF_FOLLOW_DATE = CURDATE() ";
            }else if("1".equals(qvo.getDay())){//未来三天
                sql += " AND DATE_ADD(CURDATE(), INTERVAL 3 DAY) >= d.SF_FOLLOW_DATE AND d.SF_FOLLOW_DATE > CURDATE() ";
            }else if("2".equals(qvo.getDay())){//未来七天
                sql += " AND DATE_ADD(CURDATE(), INTERVAL 7 DAY) >= d.SF_FOLLOW_DATE AND d.SF_FOLLOW_DATE > CURDATE() ";
            }else if("3".equals(qvo.getDay())){//未来一个月
                sql += " AND DATE_ADD(CURDATE(), INTERVAL 1 MONTH) >= d.SF_FOLLOW_DATE AND d.SF_FOLLOW_DATE > CURDATE() ";
            }else if("4".equals(qvo.getDay())){//未来三个月
                sql += " AND DATE_ADD(CURDATE(), INTERVAL 3 MONTH) >= d.SF_FOLLOW_DATE AND d.SF_FOLLOW_DATE > CURDATE() ";
            }
        }
        sql +="  GROUP BY d.ID ORDER BY d.SF_FOLLOW_DATE ) aa GROUP BY aa.followNum ) cc";
        List<AppFollowGroupEntity> ls=sysDao.getServiceDo().findSqlMapRVo(sql,map, AppFollowGroupEntity.class,qvo);
        return ls;
    }

    /**
     * 查询医生的随访记录
     * @param qvo group
     * @param qvo startDate
     * @param qvo endDate
     * @param qvo patientName
     * @return
     */
    @Override
    public List<AppFollowGroupEntity> findFollowRecord(AppFollowGroupQvo qvo)  throws Exception{
        HashMap<String, Object> map = new HashMap<>();
        map.put("state",FollowPlanState.YIWANCHENG.getValue());
        String sql = "SELECT\n" +
                "	c.ID patientId,\n" +
                "	c.PATIENT_NAME patientName,\n" +
                "	c.PATIENT_GENDER patientGender,\n" +
                "	c.PATIENT_AGE patientAge,\n" +
                "	c.PATIENT_IMAGEURL imgurl,\n" +
                "	(SELECT GROUP_CONCAT(LABEL_TITLE) FROM APP_LABEL_GROUP g WHERE g.LABEL_TYPE = 3	AND g.LABEL_SIGN_ID = a.ID) signPersGroup,\n" +
                "   '' labelType "+
                "FROM\n" +
                "	APP_SIGN_FORM a\n" +
                "LEFT JOIN APP_LABEL_GROUP b ON a.ID = b.LABEL_SIGN_ID\n" +
                "LEFT JOIN APP_PATIENT_USER c ON a.SIGN_PATIENT_ID = c.ID\n" +
                "LEFT JOIN APP_FOLLOW_PLAN d ON d.SF_FOLLOW_PATIENTID = c.ID\n" +
                "WHERE  d.SF_FOLLOW_STATE = :state ";
        if(StringUtils.isNotBlank(qvo.getDrId())) {
            sql += " AND d.SF_FOLLOW_DOCTORID = :drId ";
            map.put("drId", qvo.getDrId());
        }
        if(StringUtils.isNotBlank(qvo.getGroup())){
            String[] groups = qvo.getGroup().split(";");
            map.put("groups",groups);
            sql +=" AND d.SF_FOLLOW_TYPE in (:groups) ";
        }
        if(StringUtils.isNotBlank(qvo.getPatientName())){
            sql += " AND c.PATIENT_NAME like :patientName ";
            map.put("patientName", "%"+qvo.getPatientName()+"%");
        }
        if(StringUtils.isNotBlank(qvo.getStartDate())&&StringUtils.isNotBlank(qvo.getEndDate())){
            map.put("startDate",ExtendDate.getFirstDayOfMonth(qvo.getStartDate()));
            map.put("endDate",ExtendDate.getLastDayOfMonth(qvo.getEndDate()));
            sql += " AND  d.SF_FOLLOW_DATE BETWEEN :startDate AND :endDate ";
        }
        if(StringUtils.isNotBlank(qvo.getDate())){
            map.put("startDate",qvo.getDate()+"-01-01 00:00:00");
            map.put("endDate",qvo.getDate()+"-12-31 23:59:59");
            sql += " AND  d.SF_FOLLOW_DATE BETWEEN :startDate AND :endDate ";
        }
        sql +=" GROUP BY c.ID ";
        if(StringUtils.isNotBlank(qvo.getGroup())){
            String[] groups = qvo.getGroup().split(";");
            map.put("groupNum",groups.length);
            sql += " HAVING COUNT(DISTINCT d.SF_FOLLOW_TYPE) = :groupNum ";
        }
        List<AppFollowGroupEntity> ls=sysDao.getServiceDo().findSqlMapRVo(sql,map, AppFollowGroupEntity.class,qvo);
        return ls;
    }

    /**
     * 查询患者的随访记录
     * @param qvo patientId
     * @return
     */
    @Override
    public List<AppFollowRecordEntity> findPersonFollowRecord(AppFollowGroupQvo qvo) throws Exception {
        HashMap<String,Object> map = new HashMap<>();
        String[] strs = new String[]{FollowPlanState.YIWANCHENG.getValue(),FollowPlanState.SHIFANG.getValue(),FollowPlanState.SIWANG.getValue()};
        map.put("state",strs);
        String[] signStates = new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
        map.put("qyState", signStates);
        String workType = "";
        if(StringUtils.isNotBlank(qvo.getPatientId())){
            workType =  "   (SELECT\n" +
                    "\tc.MEM_WORK_TYPE\n" +
                    "FROM\n" +
                    "\tAPP_TEAM_MEMBER c\n" +
                    "INNER JOIN APP_SIGN_FORM d ON c.MEM_TEAMID = d.SIGN_TEAM_ID\n" +
                    "WHERE\n" +
                    "\td.SIGN_PATIENT_ID =:patientId " +
                    "AND d.SIGN_STATE IN (:qyState) " +
                    "AND c.MEM_DR_ID = b.ID) workType, ";
        }else{
            workType = " (SELECT MEM_WORK_TYPE FROM APP_TEAM_MEMBER WHERE MEM_TEAMID=:teamId and MEM_DR_ID =:drId) workType, ";
        }
        String sql = "SELECT\n" +
                "	a.SF_FOLLOW_NUM batch,\n" +
                "	DATE_FORMAT(a.SF_END_DATE,'%Y-%m-%d %T') followDate,\n" +
                "	a.SF_FOLLOW_DOCTORID drId,\n" +
                "   b.DR_NAME drName," +
                "   a.SF_MISS_REASON reason," +
                "   a.SF_FOLLOW_STATE followState," +
                "   a.SF_DEAD_TIME date," +
                workType+
                "'' workTypeName,"+
                "	'' followInfo\n" +
                "FROM\n" +
                "	APP_FOLLOW_PLAN a LEFT JOIN APP_DR_USER b on a.SF_FOLLOW_DOCTORID = b.ID" +
                " WHERE 1=1 ";
//        a.SF_FOLLOW_STATE = :state
        sql += " AND a.SF_FOLLOW_STATE IN :state ";
        if(StringUtils.isNotBlank(qvo.getPatientId())){
            map.put("patientId",qvo.getPatientId());
            sql += " AND a.SF_FOLLOW_PATIENTID = :patientId ";
        }
        if(StringUtils.isNotBlank(qvo.getDate())){
            map.put("startDate",qvo.getDate()+"-01-01 00:00:00");
            map.put("endDate",qvo.getDate()+"-12-31 23:59:59");
            sql += " AND  a.SF_FOLLOW_DATE BETWEEN :startDate AND :endDate ";
        }
        if(StringUtils.isNotBlank(qvo.getDrId())) {
            sql += " AND a.SF_FOLLOW_DOCTORID = :drId ";
            map.put("drId", qvo.getDrId());
        }
        if(StringUtils.isNotBlank(qvo.getTeamId())) {
            sql += " AND a.SF_TEAM_ID = :teamId ";
            map.put("teamId", qvo.getTeamId());
        }
        sql +=" GROUP BY a.SF_FOLLOW_NUM ORDER BY a.SF_END_DATE DESC ";
        List<AppFollowRecordEntity> ls=sysDao.getServiceDo().findSqlMapRVo(sql,map, AppFollowRecordEntity.class,qvo);
        return ls;
    }

    /**
     *  随访失访或死亡
     *  @param qvo followId;//随访主表id
     *  @param qvo state;//失访或死亡
     *  @param qvo reason;//失访或死亡原因
     *  @param qvo date;//失访下次随访日期或死亡日期
     *  @param qvo xAxis;
     *  @param qvo yAxis;
     * @return
     */
    @Override
    public AppFollowPlan saveFollowPlanState(AppFollowStateQvo qvo) throws Exception {
        AppFollowPlan plan = (AppFollowPlan) sysDao.getServiceDo().find(AppFollowPlan.class,qvo.getFollowId());
        AppFollowPlan plans = new AppFollowPlan();
        Map<String,Object> map = new HashMap<>();
        map.put("SF_FOLLOW_DATE",ExtendDate.getYMD(plan.getSfFollowDate()));
        map.put("SF_FOLLOW_NUM",plan.getSfFollowNum());
        String sql = "SELECT * FROM APP_FOLLOW_PLAN WHERE SF_FOLLOW_NUM=:SF_FOLLOW_NUM AND SF_FOLLOW_DATE=:SF_FOLLOW_DATE";
        List<AppFollowPlan> list = sysDao.getServiceDo().findSqlMap(sql,map,AppFollowPlan.class);
        if(list!=null && list.size()>0){
            for(AppFollowPlan ll:list){
                if(FollowPlanState.SIWANG.getValue().equals(qvo.getState())){
                    ll.setSfFollowState(FollowPlanState.SIWANG.getValue());
                    ll.setSfMissReason(qvo.getReason());
                    ll.setSfDeadTime(ExtendDate.getCalendar(qvo.getDate()));
                    ll.setSfEndDate(Calendar.getInstance());
                    ll.setSfXaxis(qvo.getxAxis());
                    ll.setSfYaxis(qvo.getyAxis());
                    sysDao.getServiceDo().modify(ll);
                }else if(FollowPlanState.SHIFANG.getValue().equals(qvo.getState())){
                    ll.setSfFollowState(FollowPlanState.SHIFANG.getValue());
                    ll.setSfMissReason(qvo.getReason());
                    ll.setSfEndDate(Calendar.getInstance());
                    ll.setSfXaxis(qvo.getxAxis());
                    ll.setSfYaxis(qvo.getyAxis());
                    sysDao.getServiceDo().modify(ll);
                    AppFollowPlan newPlan = new AppFollowPlan();
                    newPlan.setSfFollowState(FollowPlanState.WEIWANCHENG.getValue());
                    newPlan.setSfFollowDoctorid(ll.getSfFollowDoctorid());
                    newPlan.setSfFollowPatientid(ll.getSfFollowPatientid());
                    newPlan.setSfFollowMode(ll.getSfFollowMode());
                    newPlan.setSfFollowPatientName(ll.getSfFollowPatientName());
                    newPlan.setSfCreateDate(Calendar.getInstance());
                    newPlan.setSfFollowDate(ExtendDate.getCalendar(qvo.getDate()));
                    newPlan.setSfPid(ll.getSfFollowNum());
                    newPlan.setSfHosId(ll.getSfHosId());
                    newPlan.setSfHosAreaCode(ll.getSfHosAreaCode());
                    newPlan.setSfTeamId(ll.getSfTeamId());
                    newPlan.setSfFollowType(ll.getSfFollowType());
                    String nextNum = "";
                    AppHospDept dept= (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,plan.getSfHosId());
                    if(dept!=null && dept.getHospAreaCode()!=null) {
                        AppSerial serial = sysDao.getAppSignformDao().getAppSerial(dept.getHospAreaCode().substring(0, 4), "batch");
                        if(serial!=null) {
                            Map<String,Object> bcnum = sysDao.getAppSignformDao().getNum(serial.getSerialNo(),SignFormType.APPSTATE.getValue());
                            serial.setSerialNo(bcnum.get("old").toString());
                            sysDao.getServiceDo().modify(serial);
                            nextNum = bcnum.get("new").toString();
                        }
                    }
                    //newPlan.setSfFollowNum(bcnum);//批次号
                    //查询下次随访计划
                    List<AppFollowPlan> listP = sysDao.getServiceDo().loadByPk(AppFollowPlan.class,"sfPid",plan.getSfFollowNum());
                    if(listP!=null&&listP.size()>0){
                        newPlan.setSfFollowNum(listP.get(0).getSfFollowNum());
                    }else{
                        newPlan.setSfFollowNum(nextNum);//批次号
                    }
                    sysDao.getServiceDo().add(newPlan);
                }
                if(ll.getId().equals(plan.getId())){
                    plans = plan;
                }
            }
        }
        /*if(FollowPlanState.SIWANG.getValue().equals(qvo.getState())){
            plan.setSfFollowState(FollowPlanState.SIWANG.getValue());
            plan.setSfMissReason(qvo.getReason());
            plan.setSfDeadTime(ExtendDate.getCalendar(qvo.getDate()));
            plan.setSfEndDate(Calendar.getInstance());
            plan.setSfXaxis(qvo.getxAxis());
            plan.setSfYaxis(qvo.getyAxis());
            sysDao.getServiceDo().modify(plan);
        }else if(FollowPlanState.SHIFANG.getValue().equals(qvo.getState())){
            plan.setSfFollowState(FollowPlanState.SHIFANG.getValue());
            plan.setSfMissReason(qvo.getReason());
            plan.setSfEndDate(Calendar.getInstance());
            plan.setSfXaxis(qvo.getxAxis());
            plan.setSfYaxis(qvo.getyAxis());
            sysDao.getServiceDo().modify(plan);
            AppFollowPlan newPlan = new AppFollowPlan();
            newPlan.setSfFollowState(FollowPlanState.WEIWANCHENG.getValue());
            newPlan.setSfFollowDoctorid(plan.getSfFollowDoctorid());
            newPlan.setSfFollowPatientid(plan.getSfFollowPatientid());
            newPlan.setSfFollowMode(plan.getSfFollowMode());
            newPlan.setSfFollowPatientName(plan.getSfFollowPatientName());
            newPlan.setSfCreateDate(Calendar.getInstance());
            newPlan.setSfFollowDate(ExtendDate.getCalendar(qvo.getDate()));
            newPlan.setSfPid(plan.getSfFollowNum());
            newPlan.setSfHosId(plan.getSfHosId());
            newPlan.setSfHosAreaCode(plan.getSfHosAreaCode());
            newPlan.setSfTeamId(plan.getSfTeamId());
            AppHospDept dept= (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,plan.getSfHosId());
            if(dept!=null && dept.getHospAreaCode()!=null) {
                AppSerial serial = sysDao.getAppSignformDao().getAppSerial(dept.getHospAreaCode().substring(0, 4), "batch");
                if(serial!=null) {
                    String bcnum = sysDao.getAppSignformDao().getNum(serial.getSerialNo());
                    serial.setSerialNo(bcnum);
                    sysDao.getServiceDo().modify(serial);
                    newPlan.setSfFollowNum(bcnum);//批次号
                }
            }
            sysDao.getServiceDo().add(newPlan);
        }*/
        return plans;
    }

    @Override
    public List<AppFollowPlan> findDoneByType(String type) throws Exception {
        HashMap<String,Object> map = new HashMap<>();
        map.put("type",type);
        map.put("followState",FollowPlanState.YIWANCHENG.getValue());
        map.put("isState", CommSF.NOT.getValue());
        String sql = "select * from APP_FOLLOW_PLAN where SF_FOLLOW_STATE = :followState and SF_FOLLOW_TYPE = :type AND SF_ISORNOT=:isState " +
                " and SF_HOS_AREA_CODE not like '3502%' and SF_HOS_AREA_CODE not like '3509%' and SF_HOS_AREA_CODE not like '3508%' ";
        //山西
//        sql += " AND SF_HOS_AREA_CODE like '14%' ";
        List<AppFollowPlan> list = sysDao.getServiceDo().findSqlMap(sql,map,AppFollowPlan.class);
        return list;
    }

    /**
     *  随访的坐标
     *  @param drId
     *  @param followDate
     * @return
     */
    @Override
    public List<AppFollowPlanXYEntity> findDrFollowLocation(String drId,String followDate) throws Exception {
        HashMap<String,Object> map = new HashMap<>();
        map.put("drId",drId);
        map.put("state",FollowPlanState.YIWANCHENG.getValue());
        String sql = "SELECT ID id," +
                "           SF_FOLLOW_DOCTORID drId," +
                "           SF_FOLLOW_PATIENTID patientId," +
                "           SF_FOLLOW_PATIENTNAME patientName," +
                "           DATE_FORMAT(SF_END_DATE,'%Y-%m-%d') followTime," +
                "           SF_FOLLOW_TYPE followType," +
                "           SF_X_AXIS sfX," +
                "           SF_Y_AXIS sfY  " +
                " FROM APP_FOLLOW_PLAN " +
                " WHERE  SF_FOLLOW_DOCTORID = :drId AND SF_FOLLOW_STATE = :state AND SF_END_DATE BETWEEN :beginTime AND :endTime ORDER BY SF_END_DATE DESC";
        map.put("beginTime",followDate+" 00:00:00");
        map.put("endTime",followDate+" 23:59:59");
        List<AppFollowPlanXYEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppFollowPlanXYEntity.class);
        return list;
    }

    /**
     *  随访列表(时间次数)
     *  @param qvo drId
     * @return
     */
    @Override
    public List<AppFollowListEntity> findDrFollowLocationList(AppFollowPlanQvo qvo) throws Exception {
        HashMap<String,Object> map = new HashMap<>();
        map.put("drId",qvo.getPlanDoctorId());
        map.put("state",FollowPlanState.YIWANCHENG.getValue());
        String sql = "SELECT DATE_FORMAT(SF_END_DATE,'%Y-%m-%d') followDate,count(DATE_FORMAT(SF_END_DATE, '%Y-%m-%d')) followCount FROM APP_FOLLOW_PLAN " +
                " WHERE SF_FOLLOW_DOCTORID = :drId AND SF_FOLLOW_STATE = :state " ;
        if(StringUtils.isNotBlank(qvo.getPlanStart())){
            map.put("planStart",qvo.getPlanStart());
            sql += " AND SF_FOLLOW_DATE >= :planStart ";
        }

        if(StringUtils.isNotBlank(qvo.getPlanEnd())){
            map.put("planend",ExtendDateUtil.addDate(qvo.getPlanEnd(),ExtendDateType.DAYS.getValue(),1));
            sql += " AND SF_FOLLOW_DATE < :planend ";
        }

        sql += " GROUP BY DATE_FORMAT(SF_END_DATE,'%Y-%m-%d') ORDER BY SF_END_DATE DESC";
        List<AppFollowListEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppFollowListEntity.class,qvo);
        return list;
    }


    /**
     * 查询随访计划
     * @param drId
     * @param patientId
     * @param followState
     * @return
     */
    public List<AppFollowPlan> findFollowPlan(String drId,String patientId, String followState,String value) throws Exception{
        HashMap<String,Object> map = new HashMap<>();
        map.put("drId",drId);
        map.put("patientId",patientId);
        map.put("value",value);
        map.put("followState",followState);
        String sql = "select * from APP_FOLLOW_PLAN where SF_FOLLOW_DOCTORID = :drId and SF_FOLLOW_PATIENTID = :patientId and SF_FOLLOW_STATE = :followState AND SF_FOLLOW_TYPE =:value "+
                " order by SF_FOLLOW_DATE desc ";
        List<AppFollowPlan> list = sysDao.getServiceDo().findSqlMap(sql,map,AppFollowPlan.class);
        return list;
    }

    /**
     * 随访日程统计
     * @param qvo
     * @return
     */
    @Override
    public Map<String, Object> findDrFollowLocationCount(AppFollowPlanQvo qvo) throws Exception{
        Map<String,Object> map = new LinkedHashMap<String, Object>();
        int daysBetween = ExtendDate.daysBetween(qvo.getPlanStart(),qvo.getPlanEnd());
        if(daysBetween >0){
            String end = null;
            String title = null;
            for(int i=0;i<=daysBetween;i++){
                if(StringUtils.isBlank(end)){
                    end = qvo.getPlanStart();
                    title = ExtendDate.getChineseMD(end);
                }else{
                    end = ExtendDateUtil.addDate(end, ExtendDateType.DAYS.getValue(),1);
                    title = ExtendDate.getChineseMD(end);
                }
                List<AppFollowPlanXYEntity> ls = this.findDrFollowLocation(qvo.getPlanDoctorId(),end);
                map.put(title,ls);
            }
        }else {
            String end = qvo.getPlanStart();
            String title = ExtendDate.getChineseMD(end);
            List<AppFollowPlanXYEntity> ls = this.findDrFollowLocation(qvo.getPlanDoctorId(),end);
            map.put(title,ls);
        }
        return map;
    }

    /**
     * 查询患者服务类型
     * @param drId
     * @param patientId
     * @return
     */
    public String getLabelVaue(String drId,String patientId) throws Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("drId",drId);
        map.put("patientId",patientId);
        String[] singStates = new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
        map.put("signState",singStates);
        String sql = " SELECT\n" +
                "\tGROUP_CONCAT(a.LABEL_VALUE) labelValue\n" +
                "FROM\n" +
                "\tAPP_LABEL_GROUP a\n" +
                "INNER JOIN APP_SIGN_FORM b ON a.LABEL_SIGN_ID = b.ID\n" +
                "WHERE\n" +
                "\tb.SIGN_PATIENT_ID = :patientId\n" +
                "AND b.SIGN_DR_ID = :drId\n" +
                "AND b.SIGN_STATE IN :signState\n" +
//                "AND b.SIGN_FROM_DATE <= SYSDATE()\n" +
//                "AND b.SIGN_TO_DATE > SYSDATE() " +
                "AND a.LABEL_TYPE='3'\n";
        List<Map> list = sysDao.getServiceDo().findSqlMap(sql,map);
        if(list!=null && list.size()>0){
            if(list.get(0).get("labelValue")!=null){
                return list.get(0).get("labelValue").toString();
            }
        }
        return null;
    }
}
