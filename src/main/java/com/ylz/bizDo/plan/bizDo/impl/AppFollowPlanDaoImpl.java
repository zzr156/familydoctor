package com.ylz.bizDo.plan.bizDo.impl;

import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.app.vo.AppThreeBloodPressureDataVo;
import com.ylz.bizDo.app.vo.PerformanceDataQvo;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.jtapp.commonVo.AppUserBloodpressureVo;
import com.ylz.bizDo.jtapp.commonVo.DevUserBloodgluVo;
import com.ylz.bizDo.jtapp.patientEntity.AppPatientEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppPatientUserEntity;
import com.ylz.bizDo.jtapp.patientVo.AppPatientQvo;
import com.ylz.bizDo.plan.Entity.AppFollowPlanEntity;
import com.ylz.bizDo.plan.Entity.AppFollowPlanTjEntjty;
import com.ylz.bizDo.plan.Entity.AppFollowPlanTxEntity;
import com.ylz.bizDo.plan.bizDo.AppFollowPlanDao;
import com.ylz.bizDo.plan.po.*;
import com.ylz.bizDo.plan.vo.AppBasicDiseaseQvo;
import com.ylz.bizDo.plan.vo.AppBasicDrugQvo;
import com.ylz.bizDo.plan.vo.AppBasicHdBlooQvo;
import com.ylz.bizDo.plan.vo.AppFollowPlanQvo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.*;
import com.ylz.packcommon.common.exception.DaoException;
import com.ylz.packcommon.common.util.AreaUtils;
import com.ylz.packcommon.common.util.ExtendDate;
import com.ylz.packcommon.common.util.ExtendDateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017-05-12.
 */
@Service("appFollowPlanDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppFollowPlanDaoImpl implements AppFollowPlanDao {

    @Autowired
    public SysDao sysDao;


    @Override
    public List<AppFollowPlanTjEntjty> findTjPlan(AppFollowPlanQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT SF_FOLLOW_DATE planDate,SF_FOLLOW_NUM FROM APP_FOLLOW_PLAN where 1=1 ";
        if(StringUtils.isNotBlank(qvo.getPlanStart())){
            map.put("planStart",qvo.getPlanStart());
            sql += " AND SF_FOLLOW_DATE >= :planStart";
        }
        if(StringUtils.isNotBlank(qvo.getPlanEnd())){
            map.put("planEnd",qvo.getPlanEnd());
            sql += " AND SF_FOLLOW_DATE <= :planEnd";

        }
        if(StringUtils.isNotBlank(qvo.getPlanPatientId())){
            map.put("planPatientId",qvo.getPlanPatientId());
            sql += " AND SF_FOLLOW_PATIENTID = :planPatientId";

        }
        if(StringUtils.isNotBlank(qvo.getPlanDoctorId())){
            map.put("planDoctorId",qvo.getPlanDoctorId());
            sql += " AND SF_FOLLOW_DOCTORID = :planDoctorId";
        }
        sql += " GROUP BY SF_FOLLOW_NUM ";
        sql = "SELECT cc.planDate,COUNT(1) planCount FROM ("+sql+") cc group by cc.planDate ";
        return this.sysDao.getServiceDo().findSqlMapRVo(sql,map,AppFollowPlanTjEntjty.class);
    }

    @Override
    public List<AppFollowPlan> findPlan(AppFollowPlanQvo qvo)  throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT *  FROM APP_FOLLOW_PLAN where 1=1 ";
        if(StringUtils.isNotBlank(qvo.getPlanToDay())){
            map.put("date",qvo.getPlanToDay());
            sql += " AND SF_FOLLOW_DATE = :date";
        }
        if(StringUtils.isNotBlank(qvo.getPlanPatientId())){
            map.put("planPatientId",qvo.getPlanPatientId());
            sql += " AND SF_FOLLOW_PATIENTID = :planPatientId";
        }
        if(StringUtils.isNotBlank(qvo.getPlanDoctorId())){
            map.put("planDoctorId",qvo.getPlanDoctorId());
            sql += " AND SF_FOLLOW_DOCTORID = :planDoctorId";
        }
        sql += " group by SF_FOLLOW_NUM ";
        return this.sysDao.getServiceDo().findSqlMap(sql,map,AppFollowPlan.class);
    }

    @Override
    public AppFollowPlan findByType(String type,String patientId) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT *  FROM APP_FOLLOW_PLAN where 1=1 ";
        if(StringUtils.isNotBlank(patientId)){
            map.put("patientId",patientId);
            sql +=" AND SF_FOLLOW_PATIENTID = :patientId";
        }
        if(StringUtils.isNotBlank(type)){
            map.put("type",type);
            map.put("start","0");
            sql += " AND SF_FOLLOW_TYPE = :type AND SF_FOLLOW_STATE = :start order by SF_FOLLOW_DATE desc";
        }
        List<AppFollowPlan> ls = this.sysDao.getServiceDo().findSqlMap(sql,map,AppFollowPlan.class);
        if(ls != null && ls.size() >0) {
            return ls.get(0);
        }
        return null;
    }

    @Override
    public List<AppFollowPlan> findByTxState(String state)  throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT *  FROM APP_FOLLOW_PLAN where 1=1 ";
        if(StringUtils.isNotBlank(state)){
            map.put("state",state);
            map.put("start","0");
            map.put("sftx","0");
            sql += "  AND SF_FOLLOW_DAYTX = :state AND SF_FOLLOW_STATE = :start AND SF_FOLLOW_SFTX = :sftx order by SF_FOLLOW_DATE desc";
        }
        List<AppFollowPlan> ls = this.sysDao.getServiceDo().findSqlMap(sql,map,AppFollowPlan.class);
        return ls;
    }

    @Override
    public AppFollowPlan findById(String id) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT *  FROM APP_FOLLOW_PLAN where 1=1 ";
        if(StringUtils.isNotBlank(id)){
            map.put("id",id);
            map.put("start","1");
            sql += " AND ID = :id AND SF_FOLLOW_STATE = :start";
        }
        List<AppFollowPlan> ls = this.sysDao.getServiceDo().findSqlMap(sql,map,AppFollowPlan.class);
        if(ls != null && ls.size() >0) {
            return ls.get(0);
        }
        return null;
    }

    @Override
    public boolean findByIdAndTime(String patientId, String time,String type) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT *  FROM APP_FOLLOW_PLAN where 1=1 ";
        map.put("patientId",patientId);
        map.put("time",time);
        map.put("type",type);
        sql += " AND SF_FOLLOW_PATIENTID = :patientId AND SF_FOLLOW_TYPE=:type AND SF_FOLLOW_DATE = :time";
        List<AppFollowPlan> ls = this.sysDao.getServiceDo().findSqlMap(sql,map,AppFollowPlan.class);
        if(ls != null && ls.size() >0) {
            return true;
        }
        return false;
    }

    @Override
    public List<AppFollowPlan> findByIdNo(String userIdNo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT *  FROM APP_FOLLOW_PLAN where 1=1 ";
        if(StringUtils.isNotBlank(userIdNo)){
            map.put("userIdNo",userIdNo);
            map.put("start","1");
            sql += "  AND SF_FOLLOW_PATIENTID = :userIdNo AND SF_FOLLOW_STATE = :start order by SF_FOLLOW_DATE desc";
        }
        List<AppFollowPlan> ls = this.sysDao.getServiceDo().findSqlMap(sql,map,AppFollowPlan.class);
        return ls;
    }
    @Override
    public List<AppFollowPlan> findByDoctorId(String doctorId) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT *  FROM APP_FOLLOW_PLAN where 1=1 ";
        if(StringUtils.isNotBlank(doctorId)){
            map.put("doctorId",doctorId);
            map.put("start","1");
            map.put("isOrNot","0");
            sql += "  AND SF_FOLLOW_DOCTORID = :doctorId AND SF_FOLLOW_STATE = :start AND SF_ISORNOT = :isOrNot order by SF_FOLLOW_DATE desc";
        }
        List<AppFollowPlan> ls = this.sysDao.getServiceDo().findSqlMap(sql,map,AppFollowPlan.class);
        return ls;
    }

    @Override
    public AppFollowPlan findByIdCard(String type, String patientId) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT *  FROM APP_FOLLOW_PLAN where 1=1 ";
        if(StringUtils.isNotBlank(patientId)&&StringUtils.isNotBlank(type)){
            map.put("patientId",patientId);
            sql +=" AND SF_FOLLOW_PATIENTID = :patientId";
            map.put("type",type);
            map.put("start","0");
            sql += " AND SF_FOLLOW_TYPE = :type AND SF_FOLLOW_STATE = :start order by SF_FOLLOW_DATE desc";
            List<AppFollowPlan> ls = this.sysDao.getServiceDo().findSqlMap(sql,map,AppFollowPlan.class);
            if(ls != null && ls.size() >0) {
                return ls.get(0);
            }
        }else{
            return null;
        }
        return null;
    }

    @Override
    public boolean saveFollowPlan(AppFollowPlanEntity vo) throws Exception {
        /*try{
            HttpClient client = HttpClients.createDefault();
            String[] patientIds = vo.getSfFollowPatientid().split(",");
            String[] patientNames = vo.getSfFollowPatientName().split(",");
            boolean fl = false;
            String result = null;
            AppOpenRemind gor = null;
            List<AppOpenRemind> gors=this.sysDao.getServiceDo().loadByPk(AppOpenRemind.class,"doctorId",vo.getSfFollowDoctorid());
            if(gors!=null&&gors.size()>0){
                gor = gors.get(0);
            }else{
                gor = new AppOpenRemind();
                gor.setDoctorId(vo.getSfFollowDoctorid());
                gor.setDayNum("0");
                gor.setState("0");
                sysDao.getServiceDo().add(gor);
            }
            for(int i=0;i<patientIds.length;i++){
                //根据身份证号和当前时间查询记录
                boolean flag = sysDao.getAppFollowPlanDao().findByIdAndTime(patientIds[i],vo.getSfFollowDate(),vo.getSfFollowType());
                if(flag){
                    fl = true;
                    if(StringUtils.isBlank(result)){
                        result = patientNames[i]+"已随访，不能重复随访！";
                    }else{
                        result += patientNames[i]+"已随访，不能重复随访！";
                    }
                    continue;
                }

                AppFollowPlan p = new AppFollowPlan();
                p.setSfFollowPatientid(patientIds[i]);
                AppPatientUser patient = (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class, patientIds[i]);
                p.setSfYaxis(vo.getSfY());//纵坐标
                p.setSfXaxis(vo.getSfX());//横坐标
                p.setSfFollowPatientName(patientNames[i]);
                p.setSfFollowDate(ExtendDate.getCalendar(vo.getSfFollowDate()));
                p.setSfFollowMode(vo.getSfFollowMode());
                p.setSfFollowType(vo.getSfFollowType());
                if(gor!=null){
                    p.setSfFollowDay(gor.getDayNum());
                    p.setSfFollowDayTx(gor.getState());
                }
                p.setSfFollowDoctorid(vo.getSfFollowDoctorid());
                String num =sysDao.getManageMentCodePolicy().createAlipayPch("01", ExtendDate.getYMD(Calendar.getInstance()));
                p.setSfFollowNum(num);
                p.setSfFollowState("0");
                p.setSfFollowSftx("0");
                CdCode codeResult = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_SFDKJWSJ);
                if(codeResult != null){
                    if(codeResult.getCodeValue().equals("0")){
                        JSONObject jsonParam = new JSONObject();
                        jsonParam.put("name",vo.getSfFollowPatientName());
                        jsonParam.put("idno",vo.getSfFollowPatientid());
                        jsonParam.put("city","FZ");
                        String urlLogin =  PropertiesUtil.getConfValue("appYlkUrl") + "/thirdAPI/patient/archive";
                        String str1 = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam,"utf-8");
                        JSONObject jsonAll = JSONObject.fromObject(str1);
                        String jsonString = jsonAll.get("entity").toString();
                        if(!("400").equals(jsonString)){
                            JSONObject entity = JSONObject.fromObject(jsonString);
                            String jmdah = entity.get("jmdah").toString();
                            p.setSfHealthNum(jmdah);//健康档案号
                        }

                    }
                }
                sysDao.getServiceDo().add(p);
                AppWorkingPlan plan = new AppWorkingPlan();//工作计划
                plan.setPlanDrId(p.getSfFollowDoctorid());
                plan.setPlanDate(Calendar.getInstance());
                plan.setPlanType(CommonWorkPlanType.SFJH.getValue());
                plan.setPlanState(CommonWorkPlanState.WWC.getValue());
                plan.setPlanForeignId(p.getId());
                AppDrUser drUser = (AppDrUser) this.sysDao.getServiceDo().find(AppDrUser.class,p.getSfFollowDoctorid());
                if(drUser!=null){
                    plan.setPlanHospId(drUser.getDrHospId());
                    AppHospDept hosp = (AppHospDept) this.sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                    if(hosp!=null){
                        plan.setPlanAreaCode(hosp.getHospAreaCode());
                    }
                }
                List<AppSignForm> vv = this.sysDao.getServiceDo().loadByPk(AppSignForm.class,"signPatientId",p.getSfFollowPatientid());
                if(vv!=null && vv.size()>0){
                    plan.setPlanTeamId(vv.get(0).getSignTeamId());
                }
                this.sysDao.getServiceDo().add(plan);

            }
            return fl;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }*/
    return false;

    }

    @Override
    public List<AppPatientEntity> findList(AppPatientQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("value",qvo.getValue());
        map.put("teamId",qvo.getTeamId());
        String sql = "SELECT a.ID id,a.PATIENT_NAME name,a.PATIENT_GENDER sex,a.PATIENT_IMAGEURL imageUrl FROM APP_PATIENT_USER a,APP_SIGN_FORM b\n" +
                " WHERE 1=1 AND b.SIGN_PATIENT_ID = a.ID\n" +
                " AND b.SIGN_PERS_GROUP = :value AND b.SIGN_TEAM_ID =:teamId ";
        if(StringUtils.isNotBlank(qvo.getPatientName())){
            sql+=" AND a.PATIENT_NAME LIKE :patientName ";
            map.put("patientName","%"+qvo.getPatientName()+"%");
        }
        List<AppPatientEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppPatientEntity.class,qvo);
        return ls;
    }

    @Override
    public List<AppFollowPlan> findPlanYearList(AppFollowPlanQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String yearStart = ExtendDate.getYMD(ExtendDate.getCurrYearFirst());
        String yearEnd = ExtendDate.getYMD(ExtendDate.getCurrYearLast());
        String sql = "SELECT *  FROM APP_FOLLOW_PLAN where 1=1 ";
        if(StringUtils.isNotBlank(qvo.getPlanDoctorId())){
            map.put("planDoctorId",qvo.getPlanDoctorId());
            sql += " AND SF_FOLLOW_DOCTORID = :planDoctorId";
        }

        if(StringUtils.isNotBlank(yearStart)){
            map.put("yearStart",yearStart);
            sql += " AND SF_FOLLOW_DATE >= :yearStart";
        }
        if(StringUtils.isNotBlank(yearEnd)){
            map.put("yearEnd",yearEnd);
            sql += " AND SF_FOLLOW_DATE <= :yearEnd";

        }
        return this.sysDao.getServiceDo().findSqlMap(sql,map,AppFollowPlan.class,qvo);
    }

    /**
     * 随访提醒
     * @return
     * @throws Exception
     */
    @Override
    public List<AppFollowPlan> findAllPlan() throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("stateOne", CommonEnable.QIYONG.getValue());
        map.put("type", CommonWarnSet.SFTX.getValue());
        map.put("stateTwo", FollowPlanState.WEIWANCHENG.getValue());
        String sql = "SELECT a.* " +
                "FROM APP_FOLLOW_PLAN a INNER JOIN APP_WARNING_SETTING b ON a.SF_FOLLOW_DOCTORID = b.WS_USER_ID " +
                "WHERE b.WS_TYPE =:type AND b.WS_STATE =:stateOne AND a.SF_FOLLOW_DATE = DATE_ADD(DATE(NOW()),INTERVAL b.WS_NUM DAY) " +
                "AND a.SF_FOLLOW_STATE =:stateTwo GROUP BY a.SF_FOLLOW_DOCTORID";
        List<AppFollowPlan> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppFollowPlan.class);
        return ls;
    }

    /**
     * 查询当天随访情况
     * @return
     */
    @Override
    public List<AppFollowPlanTxEntity> findAllDayPlan(String type) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("openState",CommonEnable.QIYONG.getValue());
        map.put("followState",FollowPlanState.WEIWANCHENG.getValue());
        map.put("followStateT",FollowPlanState.YIWANCHENG.getValue());
        map.put("stateTwo", CommonEnable.QIYONG.getValue());
        map.put("type",type);
        String sql = "SELECT a.ID id," +
                "a.SF_FOLLOW_DOCTORID drId," +
                //统计目标量，当前日期之前状态为0的随访计划
                "(SELECT count(1) FROM APP_FOLLOW_PLAN WHERE SF_FOLLOW_DATE = date(now()) AND SF_FOLLOW_DOCTORID = a.SF_FOLLOW_DOCTORID) zsfs," +
                "(SELECT count(1) FROM APP_FOLLOW_PLAN WHERE date(SF_END_DATE) = date(now()) AND SF_FOLLOW_STATE =:followStateT AND SF_FOLLOW_DOCTORID = a.SF_FOLLOW_DOCTORID) ysfs," +
                "(SELECT count(1) FROM APP_FOLLOW_PLAN WHERE SF_FOLLOW_DATE = date(now()) AND SF_FOLLOW_STATE =:followState AND SF_FOLLOW_DOCTORID = a.SF_FOLLOW_DOCTORID) wsfs " +
                "FROM APP_FOLLOW_PLAN a INNER JOIN APP_WARNING_SETTING b ON a.SF_FOLLOW_DOCTORID = b.WS_USER_ID where b.WS_STATE=:stateTwo AND b.WS_TYPE=:type" +
                " AND a.SF_FOLLOW_DATE = DATE(NOW()) GROUP BY a.SF_FOLLOW_DOCTORID" ;
        List<AppFollowPlanTxEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppFollowPlanTxEntity.class);
        return ls;
    }

    /**
     * 随访消息提醒
     * @param userId
     * @return
     */
    @Override
    public List<AppFollowPlan> findAllPlan(String userId)  throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("userId",userId);
        map.put("stateOne", CommonEnable.QIYONG.getValue());
        map.put("type", CommonWarnSet.SFTX.getValue());
        map.put("stateTwo", FollowPlanState.WEIWANCHENG.getValue());
        String sql = "SELECT a.* " +
                "FROM APP_FOLLOW_PLAN a INNER JOIN APP_WARNING_SETTING b ON a.SF_FOLLOW_DOCTORID = b.WS_USER_ID " +
                "WHERE b.WS_TYPE =:type AND b.WS_STATE =:stateOne AND a.SF_FOLLOW_DATE = DATE_ADD(DATE(NOW()),INTERVAL b.WS_NUM DAY) " +
                "AND a.SF_FOLLOW_STATE =:stateTwo GROUP BY a.SF_FOLLOW_DOCTORID AND b.WS_USER_ID=:userId";
        List<AppFollowPlan> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppFollowPlan.class);
        return ls;
    }

    @Override
    public List<AppFollowPlanTxEntity> findAllDayPlan(String type, String userId) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("openState",CommonEnable.QIYONG.getValue());
        map.put("followState",FollowPlanState.WEIWANCHENG.getValue());
        map.put("followStateT",FollowPlanState.YIWANCHENG.getValue());
        map.put("stateTwo", CommonEnable.QIYONG.getValue());
        map.put("type",type);
        map.put("userId",userId);
        String sql = "SELECT a.ID id," +
                "a.SF_FOLLOW_DOCTORID drId," +
                //统计目标量，当前日期之前状态为0的随访计划
                "(SELECT count(1) FROM APP_FOLLOW_PLAN WHERE SF_FOLLOW_DATE = date(now()) AND SF_FOLLOW_DOCTORID = a.SF_FOLLOW_DOCTORID) zsfs," +
                "(SELECT count(1) FROM APP_FOLLOW_PLAN WHERE date(SF_END_DATE) = date(now()) AND SF_FOLLOW_STATE =:followStateT AND SF_FOLLOW_DOCTORID = a.SF_FOLLOW_DOCTORID) ysfs," +
                "(SELECT count(1) FROM APP_FOLLOW_PLAN WHERE SF_FOLLOW_DATE = date(now()) AND SF_FOLLOW_STATE =:followState AND SF_FOLLOW_DOCTORID = a.SF_FOLLOW_DOCTORID) wsfs " +
                "FROM APP_FOLLOW_PLAN a INNER JOIN APP_WARNING_SETTING b ON a.SF_FOLLOW_DOCTORID = b.WS_USER_ID where b.WS_STATE=:stateTwo AND b.WS_TYPE=:type" +
                " AND a.SF_FOLLOW_DATE = DATE(NOW()) AND b.WS_USER_ID =:userId GROUP BY a.SF_FOLLOW_DOCTORID" ;
        List<AppFollowPlanTxEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppFollowPlanTxEntity.class);
        return ls;
    }

    /**
     * 保存基卫糖尿病随访数据
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public String addBasicDiabetes(AppBasicDiseaseQvo qvo) throws Exception {
        if(StringUtils.isNotBlank(qvo.getPatientIdno())){

            if(StringUtils.isBlank(qvo.getFollowVisitTime())){
                return "本次随访时间不能为空";
            }
            if(StringUtils.isBlank(qvo.getNextVisiTime())){
                return "下次随访时间不能为空";
            }
            AppPatientUser user = null;
            List<AppPatientUser> lisUser = sysDao.getServiceDo().loadByPk(AppPatientUser.class,"patientIdno",qvo.getPatientIdno());
            if(lisUser!=null && lisUser.size()>0){
                user = lisUser.get(0);
            }
            if(user==null){
                return "该用户未注册";
            }else{
                AppSignForm form = sysDao.getAppSignformDao().getSignFormUserId(user.getId());
                if(form==null){
                    return "该用户未签约";
                }else{
                    /*if(StringUtils.isNotBlank(form.getSignAreaCode())){
                        String result = "";
                        String cityCode = AreaUtils.getAreaCode(form.getSignAreaCode(), "2");
                        CdCode code = this.sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, cityCode);
                        if (code != null) {
                            if (code.getCodeTitle().equals(AddressType.FZ.getValue())) {

                            } else if (code.getCodeTitle().equals(AddressType.QZ.getValue())) {
                                result = "qz_";
                            } else if (code.getCodeTitle().equals(AddressType.ZZ.getValue())) {
                                result = "zz_";
                            } else if (code.getCodeTitle().equals(AddressType.PT.getValue())) {
                                result = "pt_";
                            } else if (code.getCodeTitle().equals(AddressType.NP.getValue())) {
                                result = "np_";
                            } else if (code.getCodeTitle().equals(AddressType.SM.getValue())) {
                                result = "sm_";
                            } else if (code.getCodeTitle().equals(AddressType.LY.getValue())) {
                                result = "ly_";
                            } else if (code.getCodeTitle().equals(AddressType.ND.getValue())) {
                                result = "nd_";
                            } else if (code.getCodeTitle().equals(AddressType.XM.getValue())) {
                                result = "xm_";
                            } else if (code.getCodeTitle().equals(AddressType.PINGT.getValue())) {
                                result = "pg_";
                            }
                        }
                        if(StringUtils.isNotBlank(qvo.getVisitDoctorId())){
                            if(qvo.getVisitDoctorId().length()<=33){
                                qvo.setVisitDoctorId(result+qvo.getVisitDoctorId());
                            }
                        }
                    }*/
                    AppDrUser drUser = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,qvo.getVisitDoctorId());
                    if(drUser == null){
                        return "查不到相应医生信息";
                    }else{
                        //根据随访编号查询随访信息
                        AppDiabetesTable disTable = sysDao.getAppFollowPlanDao().findDisByCode(qvo.getCode());
                        if(disTable!=null){
                            if("1".equals(qvo.getBasicState())){//修改
                                List<AppMedicationTable> lis = sysDao.getServiceDo().loadByPk(AppMedicationTable.class,"visitId",disTable.getId());
                                if(lis!=null && lis.size()>0){
                                    for(AppMedicationTable lls:lis){
                                        sysDao.getServiceDo().delete(lls);
                                    }
                                }
                                String visitId = disTable.getVisitId();
                                sysDao.getServiceDo().delete(disTable);

                                AppDiabetesTable table = new AppDiabetesTable();
                                table.setVisitId(visitId);
                                table.setName(qvo.getName());
                                table.setCode(qvo.getCode());
                                table.setSymptoms(qvo.getSymptoms());
                                table.setSymptomsOther(qvo.getSymptomsOther());
                                table.setBloodPressureOne(qvo.getBloodPressureOne());
                                table.setWeight(qvo.getWeight());
                                table.setBmi(qvo.getBmi());
                                table.setDorsalisPedisPulse(qvo.getDorsalisPedisPulse());
                                table.setSignsOther(qvo.getSignsOther());
                                table.setSmokingToDay(qvo.getSmokingToDay());
                                table.setDrinkingToDay(qvo.getDrinkingToDay());
                                table.setActivityToWeek(qvo.getActivityToWeek());
                                table.setActivityToTime(qvo.getActivityToTime());
                                table.setFood(qvo.getFood());
                                table.setMentalityAdjust(qvo.getMentalityAdjust());
                                table.setFollowingBehavior(qvo.getFollowingBehavior());
                                table.setFastingBloodSugar(qvo.getFastingBloodSugar());
                                table.setOtherCheck(qvo.getOtherCheck());
                                table.setThHemoglobin(qvo.getThHemoglobin());
                                table.setFzCheckDate(getFollowTime(qvo.getFzCheckDate()));
                                table.setMedicationAdherence(qvo.getMedicationAdherence());
                                table.setDrugReaction(qvo.getDrugReaction());
                                table.setLowBloodGlucose(qvo.getLowBloodGlucose());
                                table.setVisitThisType(qvo.getVisitThisType());
                                table.setInsulin(qvo.getInsulin());
                                table.setUserInsulin(qvo.getUserInsulin());
                                table.setReferralReason(qvo.getReferralReason());
                                table.setReferralOrg(qvo.getReferralOrg());
                                table.setNextVisiTime(getFollowTime(qvo.getNextVisiTime()));
                                table.setVisitDoctorName(drUser.getId());
                                table.setBloodPressureTwo(qvo.getBloodPressureTwo());
                                table.setNextWeight(qvo.getNextWeight());
                                table.setHeight(qvo.getHeight());
                                table.setSmokingNextToDay(qvo.getSmokingNextToDay());
                                table.setDrinkingNextToDay(qvo.getDrinkingNextToDay());
                                table.setActivityNextToWeek(qvo.getActivityNextToWeek());
                                table.setActivityNextToTime(qvo.getActivityNextToTime());
                                table.setNextFood(qvo.getNextFood());
                                table.setDorsalisPedisValue(qvo.getDorsalisPedisValue());
                                table.setReferralDept(qvo.getReferralDept());
                                table.setReferral(qvo.getReferral());
                                table.setVisitSituation(qvo.getVisitSituation());
                                table.setVisitReason(qvo.getVisitReason());
                                table.setDieDate(getFollowTime(qvo.getDieDate()));
                                table.setName(qvo.getName());
                                table.setFollowVisitTime(getFollowTime(qvo.getFollowVisitTime()));
                                table.setFollowVisitWay(qvo.getFollowVisitWay());
                                table.setPatientId(user.getId());
                                table.setIsBasic("1");
                                sysDao.getServiceDo().add(table);
                                if(qvo.getDrugList()!=null && qvo.getDrugList().size()>0){
                                    List<AppBasicDrugQvo> lisDrug = qvo.getDrugList();
                                    for(AppBasicDrugQvo lls:lisDrug){
                                        AppMedicationTable vo = new AppMedicationTable();
                                        vo.setVisitId(table.getId());
                                        vo.setUserToTime(lls.getUserToTime());
                                        vo.setUserToDay(lls.getUserToDay());
                                        vo.setMedicineName(lls.getMedicineName());
                                        sysDao.getServiceDo().add(vo);
                                    }
                                }

                                return "true";
                            }else if("2".equals(qvo.getBasicState())){//删除
                                List<AppMedicationTable> lis = sysDao.getServiceDo().loadByPk(AppMedicationTable.class,"visitId",disTable.getId());
                                if(lis!=null && lis.size()>0){
                                    for(AppMedicationTable ll:lis){
                                        sysDao.getServiceDo().delete(ll);
                                    }
                                }
                                sysDao.getServiceDo().delete(disTable);
                                return "true";
                            }
                        }
                        //给患者添加糖尿病疾病标签
                       /* List<AppLabelDisease> lisDis = sysDao.getServiceDo().loadByPk(AppLabelDisease.class,"labelSignId",form.getId());
                        boolean flag = true;
                        if(lisDis!=null && lisDis.size()>0){
                            for(AppLabelDisease ll:lisDis){
                                if("202".equals(ll.getLabelValue())){
                                    flag = false;
                                }
                            }
                        }
                        if(flag){
                            AppLabelManage manage = sysDao.getAppLabelManageDao().findLabelByValue(LabelManageType.JBLX.getValue(),"202");
                            AppLabelDisease alg = new AppLabelDisease();
                            alg.setLabelId(manage.getId());
                            alg.setLabelSignId(form.getId());
                            alg.setLabelTeamId(form.getSignTeamId());
                            alg.setLabelTitle(manage.getLabelTitle());
                            alg.setLabelValue(manage.getLabelValue());
                            alg.setLabelType(manage.getLabelType());
                            alg.setLabelAreaCode(form.getSignAreaCode());
                            if("2".equals(manage.getLabelType())){
                                alg.setLabelColor("gray");
                            }
                            sysDao.getServiceDo().add(alg);
                        }*/
                        //给患者添加糖尿病服务人群标签
                        boolean flagg = true;
                        List<AppLabelGroup> lisGr = sysDao.getServiceDo().loadByPk(AppLabelGroup.class,"labelSignId",form.getId());
                        if(lisGr !=null && lisGr.size()>0){
                            for(AppLabelGroup ll:lisGr){
                                if(ResidentMangeType.TNB.getValue().equals(ll.getLabelValue())){
                                    flagg = false;
                                }
                            }
                        }
                        if(flagg){
                            /*AppLabelManage manage = sysDao.getAppLabelManageDao().findLabelByValue(LabelManageType.FWRQ.getValue(),ResidentMangeType.TNB.getValue());
                            AppLabelGroup group = new AppLabelGroup();
                            group.setLabelId(manage.getId());
                            group.setLabelSignId(form.getId());
                            group.setLabelTeamId(form.getSignTeamId());
                            group.setLabelTitle(manage.getLabelTitle());
                            group.setLabelValue(manage.getLabelValue());
                            group.setLabelType(manage.getLabelType());
                            group.setLabelAreaCode(form.getSignAreaCode());
                            sysDao.getServiceDo().add(group);*/
                            sysDao.getAppNoticeDao().addOnlyNotices("系统消息",
                                    "居民"+qvo.getName()+"，已确诊糖尿病，请前往修改服务人群",NoticesType.XTXX.getValue()+",2", "系统",
                                    form.getSignDrId(), form.getSignPatientId(),DrPatientType.DR.getValue());
                        }
                        //基卫过来的随访暂时不再添加计划
                        //添加随访计划
                        AppFollowPlan plan = new AppFollowPlan();
                        plan.setSfFollowPatientid(user.getId());
                        plan.setSfFollowPatientName(user.getPatientName());
                        plan.setSfFollowDate(ExtendDate.getCalendar(getFollowTime(qvo.getFollowVisitTime())));
                        plan.setSfFollowMode(qvo.getFollowVisitWay());
                        plan.setSfFollowType(ResidentMangeType.TNB.getValue());
//                    plan.setSfOrgId(form.getSignHospId());
                        plan.setSfHosAreaCode(form.getSignAreaCode());
                        plan.setSfTeamId(form.getSignTeamId());
                        plan.setSfFollowDoctorid(drUser.getId());
                        plan.setSfHosId(form.getSignHospId());
                        plan.setSfFollowNum(qvo.getCode());
                        plan.setSfFollowState(FollowPlanState.WEIWANCHENG.getValue());
                        plan.setSfHealthNum(user.getPatientjmda());
                        plan.setSfIsOrNot("1");
                        sysDao.getServiceDo().add(plan);

                        //工作计划
                        AppWorkingPlan plan1 = new AppWorkingPlan();
                        plan1.setPlanDrId(plan.getSfFollowDoctorid());
                        plan1.setPlanDate(Calendar.getInstance());
                        plan1.setPlanType(CommonWorkPlanType.SFJH.getValue());
                        plan1.setPlanState(CommonWorkPlanState.YWC.getValue());
                        plan1.setPlanForeignId(plan.getId());
                        if(drUser!=null){
                            plan1.setPlanHospId(drUser.getDrHospId());
                            AppHospDept hosp = (AppHospDept) this.sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                            if(hosp!=null){
                                plan1.setPlanAreaCode(hosp.getHospAreaCode());
                            }
                        }
                        plan1.setPlanTeamId(form.getSignTeamId());
                        sysDao.getServiceDo().add(plan1);

                        if(FollowPlanState.SHIFANG.getValue().equals(qvo.getVisitSituation())){//失访
                            plan.setSfFollowState(qvo.getVisitSituation());
                            plan.setSfMissReason(qvo.getVisitReason());
                            plan.setSfXaxis(qvo.getSfXaxis());
                            plan.setSfYaxis(qvo.getSfYaxis());
                            plan.setNextTime(ExtendDate.getCalendar(getFollowTime(qvo.getNextVisiTime())));
                            sysDao.getServiceDo().modify(plan);
                            //添加下一次随访计划
                            /*AppFollowPlan newPlan = new AppFollowPlan();
                            newPlan.setSfFollowState(FollowPlanState.WEIWANCHENG.getValue());
                            newPlan.setSfFollowDoctorid(plan.getSfFollowDoctorid());
                            newPlan.setSfFollowPatientid(plan.getSfFollowPatientid());
                            newPlan.setSfFollowMode(plan.getSfFollowMode());
                            newPlan.setSfFollowPatientName(plan.getSfFollowPatientName());
                            newPlan.setSfCreateDate(Calendar.getInstance());
                            newPlan.setSfFollowDate(ExtendDate.getCalendar(getFollowTime(qvo.getNextVisiTime())));
                            newPlan.setSfPid(plan.getSfFollowNum());
                            newPlan.setSfHosId(plan.getSfHosId());
                            newPlan.setSfHosAreaCode(plan.getSfHosAreaCode());
                            newPlan.setSfTeamId(plan.getSfTeamId());
                            newPlan.setSfFollowType(plan.getSfFollowType());
                            String nextNum = "";
                            AppHospDept dept= (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,plan.getSfHosId());
                            if(dept!=null && dept.getHospAreaCode()!=null) {
                                AppSerial serial = sysDao.getAppSignformDao().getAppSerial(dept.getHospAreaCode().substring(0, 4), "follow");
                                if(serial!=null) {
                                    Map<String,Object> bcnum = sysDao.getAppSignformDao().getNum(serial.getSerialNo(),SignFormType.FOLLOWSTATE.getValue());
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

                            AppWorkingPlan plan2 = new AppWorkingPlan();
                            plan2.setPlanDrId(newPlan.getSfFollowDoctorid());
                            plan2.setPlanDate(newPlan.getSfFollowDate());
                            plan2.setPlanType(CommonWorkPlanType.SFJH.getValue());
                            plan2.setPlanState(CommonWorkPlanState.WWC.getValue());
                            plan2.setPlanForeignId(newPlan.getId());
                            if(drUser!=null){
                                plan2.setPlanHospId(drUser.getDrHospId());
                                AppHospDept hosp = (AppHospDept) this.sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                                if(hosp!=null){
                                    plan2.setPlanAreaCode(hosp.getHospAreaCode());
                                }
                            }
                            plan2.setPlanTeamId(form.getSignTeamId());
                            sysDao.getServiceDo().add(plan2);
*/
                        }else if(FollowPlanState.SIWANG.getValue().equals(qvo.getVisitSituation())) {//死亡
                            plan.setSfFollowState(qvo.getVisitSituation());
                            plan.setSfMissReason(qvo.getVisitReason());
                            plan.setSfXaxis(qvo.getSfXaxis());
                            plan.setSfYaxis(qvo.getSfYaxis());
                            if(StringUtils.isNotBlank(qvo.getDieDate())){
                                plan.setSfDeadTime(ExtendDate.getCalendar(qvo.getDieDate()));
                            }
                            sysDao.getServiceDo().modify(plan);

                        }else{//正常随访下添加下一次随访计划
                            plan.setSfFollowState(FollowPlanState.YIWANCHENG.getValue());
                            plan.setSfEndDate(ExtendDate.getCalendar(getFollowTime(qvo.getVisitDate())));
                            plan.setSfReferral(qvo.getReferral());
                            plan.setSfReferralDept(qvo.getReferralDept());
                            plan.setSfReferralOrg(qvo.getReferralOrg());
                            plan.setSfReferralReason(qvo.getReferralReason());
                            plan.setSfXaxis(qvo.getSfXaxis());
                            plan.setSfYaxis(qvo.getSfYaxis());
                            Map<String,Object> map = new HashMap<String,Object>();
                            map.put("patientId",plan.getSfFollowPatientid());
                            map.put("type",ResidentMangeType.GXY.getValue());
                            map.put("state",FollowPlanState.YIWANCHENG.getValue());
                            String sql = "SELECT count(1) FROM APP_FOLLOW_PLAN WHERE SF_FOLLOW_STATE =:state AND SF_FOLLOW_PATIENTID=:patientId AND SF_FOLLOW_TYPE =:type";
                            int num11 = sysDao.getServiceDo().gteSqlCount(sql,map);
                            plan.setSfTypeNum(String.valueOf(num11+1));
                            plan.setNextTime(ExtendDate.getCalendar(getFollowTime(qvo.getNextVisiTime())));
                            sysDao.getServiceDo().modify(plan);

                            //新增下一次随访计划
                           /* AppFollowPlan pl = new AppFollowPlan();
                            if(org.apache.commons.lang.StringUtils.isNotBlank(plan.getSfFollowPatientid())) {
                                pl.setSfFollowPatientid(user.getId());
                                pl.setSfFollowPatientName(user.getPatientName());
                            }
                            pl.setSfFollowType(ResidentMangeType.GXY.getValue());
                            pl.setSfFollowDoctorid(plan.getSfFollowDoctorid());
                            pl.setSfFollowMode(plan.getSfFollowMode());
                            pl.setSfFollowState(FollowPlanState.WEIWANCHENG.getValue());//随访状态未开始
                            if("1".equals(qvo.getReferral())||FollowThisType.KZBMY.getValue().equals(qvo.getVisitThisType())||DrugState.YOU.getValue().equals(qvo.getDrugReaction())){
                                Calendar cal = Calendar.getInstance();
                                cal.add(Calendar.DATE,14);
                                pl.setSfFollowDate(cal);
                            }else{
                                //后台计算正常情况下一次随访计划时间
                                if(org.apache.commons.lang.StringUtils.isNotBlank(qvo.getNextVisiTime())){
                                    pl.setSfFollowDate(ExtendDate.getCalendar(getFollowTime(qvo.getNextVisiTime())));
                                }else{
                                    Calendar ss = Calendar.getInstance();
                                    ss.add(Calendar.MONTH,3);
                                    pl.setSfFollowDate(ss);
                                }
                            }
                            pl.setSfCreateDate(Calendar.getInstance());
                            String nextNum = "";
                            String deptId = "";
                            String hospAreaCode = "";
                            AppHospDept dept= (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                            if(dept!=null && dept.getHospAreaCode()!=null) {
                                AppSerial serial = sysDao.getAppSignformDao().getAppSerial(dept.getHospAreaCode().substring(0, 4), "follow");
                                if(serial!=null) {
                                    Map<String,Object> bcnum = sysDao.getAppSignformDao().getNum(serial.getSerialNo(),SignFormType.FOLLOWSTATE.getValue());
                                    serial.setSerialNo(bcnum.get("old").toString());
                                    sysDao.getServiceDo().modify(serial);
                                    nextNum = bcnum.get("new").toString();
                                }
                                deptId = dept.getId();
                                hospAreaCode = dept.getHospAreaCode();
                            }
                            //查询下次随访计划的编号
                            List<AppFollowPlan> listP = sysDao.getServiceDo().loadByPk(AppFollowPlan.class,"sfPid",plan.getSfFollowNum());
                            if(listP!=null&&listP.size()>0){
                                pl.setSfFollowNum(listP.get(0).getSfFollowNum());
                            }else{
                                pl.setSfFollowNum(nextNum);//批次号
                            }*/
                            /*pl.setSfHosId(deptId);
                            pl.setSfHosAreaCode(hospAreaCode);
                            pl.setSfTeamId(plan.getSfTeamId());
                            pl.setSfPid(plan.getSfFollowNum());
                            pl.setSfReferral(qvo.getReferral());
                            pl.setSfReferralDept(qvo.getReferralDept());
                            pl.setSfReferralOrg(qvo.getReferralOrg());
                            pl.setSfReferralReason(qvo.getReferralReason());
                            sysDao.getServiceDo().add(pl);
                            AppWorkingPlan plan2 = new AppWorkingPlan();
                            plan2.setPlanDrId(pl.getSfFollowDoctorid());
                            plan2.setPlanDate(pl.getSfFollowDate());
                            plan2.setPlanType(CommonWorkPlanType.SFJH.getValue());
                            plan2.setPlanState(CommonWorkPlanState.WWC.getValue());
                            plan2.setPlanForeignId(pl.getId());
                            if(drUser!=null){
                                plan2.setPlanHospId(drUser.getDrHospId());
                                AppHospDept hosp = (AppHospDept) this.sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                                if(hosp!=null){
                                    plan2.setPlanAreaCode(hosp.getHospAreaCode());
                                }
                            }
                            plan2.setPlanTeamId(form.getSignTeamId());
                            sysDao.getServiceDo().add(plan2);*/

                            AppDiabetesTable table = new AppDiabetesTable();
                            table.setVisitId(plan.getId());
                            table.setName(qvo.getName());
                            table.setCode(qvo.getCode());
                            table.setSymptoms(qvo.getSymptoms());
                            table.setSymptomsOther(qvo.getSymptomsOther());
                            table.setBloodPressureOne(qvo.getBloodPressureOne());
                            table.setWeight(qvo.getWeight());
                            table.setBmi(qvo.getBmi());
                            table.setDorsalisPedisPulse(qvo.getDorsalisPedisPulse());
                            table.setSignsOther(qvo.getSignsOther());
                            table.setSmokingToDay(qvo.getSmokingToDay());
                            table.setDrinkingToDay(qvo.getDrinkingToDay());
                            table.setActivityToWeek(qvo.getActivityToWeek());
                            table.setActivityToTime(qvo.getActivityToTime());
                            table.setFood(qvo.getFood());
                            table.setMentalityAdjust(qvo.getMentalityAdjust());
                            table.setFollowingBehavior(qvo.getFollowingBehavior());
                            table.setFastingBloodSugar(qvo.getFastingBloodSugar());
                            table.setOtherCheck(qvo.getOtherCheck());
                            table.setThHemoglobin(qvo.getThHemoglobin());
                            table.setFzCheckDate(getFollowTime(qvo.getFzCheckDate()));
                            table.setMedicationAdherence(qvo.getMedicationAdherence());
                            table.setDrugReaction(qvo.getDrugReaction());
                            table.setLowBloodGlucose(qvo.getLowBloodGlucose());
                            table.setVisitThisType(qvo.getVisitThisType());
                            table.setInsulin(qvo.getInsulin());
                            table.setUserInsulin(qvo.getUserInsulin());
                            table.setReferralReason(qvo.getReferralReason());
                            table.setReferralOrg(qvo.getReferralOrg());
                            if(StringUtils.isNotBlank(qvo.getNextVisiTime())){
                                table.setNextVisiTime(ExtendDate.getYMD(ExtendDate.getCalendar(getFollowTime(qvo.getNextVisiTime()))));
                            }
                            table.setVisitDoctorName(drUser.getId());
                            table.setBloodPressureTwo(qvo.getBloodPressureTwo());
                            table.setNextWeight(qvo.getNextWeight());
                            table.setHeight(qvo.getHeight());
                            table.setSmokingNextToDay(qvo.getSmokingNextToDay());
                            table.setDrinkingNextToDay(qvo.getDrinkingNextToDay());
                            table.setActivityNextToWeek(qvo.getActivityNextToWeek());
                            table.setActivityNextToTime(getFollowTime(qvo.getActivityNextToTime()));
                            table.setNextFood(qvo.getNextFood());
                            table.setDorsalisPedisValue(qvo.getDorsalisPedisValue());
                            table.setReferralDept(qvo.getReferralDept());
                            table.setReferral(qvo.getReferral());
                            table.setVisitSituation(qvo.getVisitSituation());
                            table.setVisitReason(qvo.getVisitReason());
                            table.setDieDate(getFollowTime(qvo.getDieDate()));
                            table.setName(user.getPatientName());
                            table.setFollowVisitTime(ExtendDate.getYMD(ExtendDate.getCalendar(getFollowTime(qvo.getFollowVisitTime()))));
                            table.setFollowVisitWay(qvo.getFollowVisitWay());
                            table.setPatientId(user.getId());
                            table.setIsBasic("1");
                            sysDao.getServiceDo().add(table);
                            if(qvo.getDrugList()!=null && qvo.getDrugList().size()>0){
                                List<AppBasicDrugQvo> lisDrug = qvo.getDrugList();
                                for(AppBasicDrugQvo lls:lisDrug){
                                    AppMedicationTable vo = new AppMedicationTable();
                                    vo.setVisitId(table.getId());
                                    vo.setUserToTime(lls.getUserToTime());
                                    vo.setUserToDay(lls.getUserToDay());
                                    vo.setMedicineName(lls.getMedicineName());
                                    sysDao.getServiceDo().add(vo);
                                }
                            }
                            
                            PerformanceDataQvo qqvo = new PerformanceDataQvo();
                            String fwType = "";
                            String sermeal = "";//服务包id
                            fwType = sysDao.getAppLabelGroupDao().findFwValue(form.getId());
                            sermeal = form.getSignpackageid();
                            qqvo.setPerName(user.getPatientName());
                            qqvo.setPerIdno(user.getPatientIdno());
                            qqvo.setPerArea(form.getSignAreaCode());
                            qqvo.setServeDate(getFollowTime(qvo.getVisitDate()));
                            qqvo.setPerCreateDate(getFollowTime(qvo.getVisitDate()));
                            qqvo.setHospId(form.getSignHospId());
                            qqvo.setDrId(form.getSignDrId());
                            qqvo.setTeamId(form.getSignTeamId());
                            qqvo.setPerSource("1");
                            qqvo.setPerType(PerformanceType.DQSF.getValue());
                            qqvo.setPerFollowType(ResidentMangeType.TNB.getValue());
                            qqvo.setPerForeign(table.getId());
                            sysDao.getAppPerformanceStatisticsDao().addPerformanceData(qqvo,sermeal,fwType);

                            //基卫糖尿病随访提取血糖数据到用户血糖信息表
                            DevUserBloodgluVo xt = new DevUserBloodgluVo();
                            xt.setIdno(user.getPatientIdno());
                            xt.setName(user.getPatientName());
                            //随访时间只有年月日，所以加上时分秒
                            xt.setTestTime(qvo.getVisitDate()+"000000");
                            xt.setSourceType("4");
                            xt.setBloodGlu(table.getFastingBloodSugar());
                            sysDao.getAppUserBloodgluDao().addlfBlu(xt);
                        }
                    }
                    return "true";
                }
            }
        }else{
            return "患者身份证号不能为空";
        }
    }

    /**
     * 根据随访编号查询糖尿病随访信息
     * @param code
     * @return
     */
    @Override
    public AppDiabetesTable findDisByCode(String code) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("code",code);
        String sql = " SELECT * FROM APP_DIABETES_TABLE WHERE CODE=:code";
        List<AppDiabetesTable> list = sysDao.getServiceDo().findSqlMap(sql,map,AppDiabetesTable.class);
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    public String getFollowTime(String time) throws Exception{
        String str = null;
        if(StringUtils.isNotBlank(time)){
            if(time.length()>=8){
                str = time.substring(0,4)+"-"+time.substring(4,6)+"-"+time.substring(6,8);
            }
        }
        return str;
    }

    @Override
    public List<AppFollowPlan> findPlanById(String userId) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        String sql = " SELECT * FROM APP_FOLLOW_PLAN WHERE SF_FOLLOW_PATIENTID=:userId AND SF_FOLLOW_STATE='0' AND SF_FOLLOW_DATE>SYSDATE() ";
        List<AppFollowPlan> list = sysDao.getServiceDo().findSqlMap(sql,map,AppFollowPlan.class);
        return list;
    }

    @Override
    public AppHdBlooPressureTable findHdByCode(String code) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("code",code);
        String sql = " SELECT * FROM APP_HDBLOOPRESSURE_TABLE WHERE CODE=:code";
        List<AppHdBlooPressureTable> list = sysDao.getServiceDo().findSqlMap(sql,map,AppHdBlooPressureTable.class);
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    /**
     * 保存基卫高血压随访数据
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public String addBasicHdBloo(AppBasicHdBlooQvo qvo) throws Exception {
        if(StringUtils.isNotBlank(qvo.getPatientIdno())) {
            if (StringUtils.isBlank(qvo.getFollowVisitTime())) {
                return "随访时间不能为空!";
            }
//            if (StringUtils.isBlank(qvo.getNextVisiTime())) {
//                return "下次随访时间不能为空";
//            }
            AppPatientUser user = null;
            List<AppPatientUser> lisUser = sysDao.getServiceDo().loadByPk(AppPatientUser.class, "patientIdno", qvo.getPatientIdno());
            if (lisUser != null && lisUser.size() > 0) {
                user = lisUser.get(0);
            }
            if (user == null) {
                return "该用户未注册";
            } else {
                AppSignForm form = sysDao.getAppSignformDao().getSignFormUserId(user.getId());
                if (form == null) {
                    return "该用户未签约";
                } else {
                    String result = "";
                    String cityCode = AreaUtils.getAreaCode(qvo.getAreacode(), "2");
                    CdCode code = this.sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, cityCode);
//                    if (code != null) {
//                        if (code.getCodeTitle().equals(AddressType.FZ.getValue())) {
//
//                        } else if (code.getCodeTitle().equals(AddressType.QZ.getValue())) {
//                            result = "qz_";
//                        } else if (code.getCodeTitle().equals(AddressType.ZZ.getValue())) {
//                            result = "zz_";
//                        } else if (code.getCodeTitle().equals(AddressType.PT.getValue())) {
//                            result = "pt_";
//                        } else if (code.getCodeTitle().equals(AddressType.NP.getValue())) {
//                            result = "np_";
//                        } else if (code.getCodeTitle().equals(AddressType.SM.getValue())) {
//                            result = "sm_";
//                        } else if (code.getCodeTitle().equals(AddressType.LY.getValue())) {
//                            result = "ly_";
//                        } else if (code.getCodeTitle().equals(AddressType.ND.getValue())) {
////                            result = "nd_";
//                        } else if (code.getCodeTitle().equals(AddressType.XM.getValue())) {
//                            result = "xm_";
//                        } else if (code.getCodeTitle().equals(AddressType.PINGT.getValue())) {
//                            result = "pg_";
//                        }
//                    }
//                    if(StringUtils.isNotBlank(qvo.getVisitDoctorId())){
//                        if(qvo.getVisitDoctorId().length()<=33){
//                            qvo.setVisitDoctorId(result+qvo.getVisitDoctorId());
//                        }
//                    }
                    AppDrUser drUser = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class, qvo.getVisitDoctorId());
                    if (drUser == null) {
                        return "查不到相应医生信息";
                    } else {
                        //根据随访编号查询随访信息
                        AppHdBlooPressureTable disTable = sysDao.getAppFollowPlanDao().findHdByCode(qvo.getCode());
                        if(disTable!=null){
                            if("1".equals(qvo.getBasicState())){//修改
                                List<AppMedicationTable> lis = sysDao.getServiceDo().loadByPk(AppMedicationTable.class,"visitId",disTable.getId());
                                if(lis!=null && lis.size()>0){
                                    for(AppMedicationTable lls:lis){
                                        sysDao.getServiceDo().delete(lls);
                                    }
                                }
                                String visitId = disTable.getVisitId();
                                sysDao.getServiceDo().delete(disTable);
                                AppHdBlooPressureTable table = new AppHdBlooPressureTable();
                                table.setVisitId(visitId);
                                table.setName(qvo.getName());
                                table.setCode(qvo.getCode());
                                table.setFollowVisitTime(getFollowTime(qvo.getFollowVisitTime()));
                                table.setFollowVisitWay(qvo.getFollowVisitWay());
                                table.setSymptoms(qvo.getSymptoms());
                                table.setSymptomsOther(qvo.getSymptomsOther());
                                table.setBloodPressureOne(qvo.getBloodPressureOne());
                                table.setWeight(qvo.getWeight());
                                table.setNextWeight(qvo.getNextWeight());
                                table.setHeight(qvo.getHeight());
                                table.setBmi(qvo.getBmi());
                                table.setHeartRate(qvo.getHeartRate());
                                table.setSignsOther(qvo.getSignsOther());
                                table.setSmokingToDay(qvo.getSmokingToDay());
                                table.setSmokingNextToDay(qvo.getSmokingNextToDay());
                                table.setDrinkingToDay(qvo.getDrinkingToDay());
                                table.setDrinkingNextToDay(qvo.getDrinkingNextToDay());
                                table.setActivityToWeek(qvo.getActivityToWeek());
                                table.setActivityToTime(qvo.getActivityToTime());
                                table.setActivityNextToWeek(qvo.getActivityNextToWeek());
                                table.setActivityNextToTime(qvo.getActivityNextToTime());
                                table.setSaltSituation(qvo.getSaltSituation());
                                table.setSaltNextSituation(qvo.getSaltNextSituation());
                                table.setMentalityAdjust(qvo.getMentalityAdjust());
                                table.setFollowingBehavior(qvo.getFollowingBehavior());
                                table.setFzCheck(qvo.getFzCheck());
                                table.setMedicationAdherence(qvo.getMedicationAdherence());
                                table.setDrugReaction(qvo.getDrugReaction());
                                table.setDrugReactionContent(qvo.getDrugReactionContent());
                                table.setVisitThisType(qvo.getVisitThisType());
                                table.setReferral(qvo.getReferral());
                                table.setReferralReason(qvo.getReferralReason());
                                table.setNextVisiTime(getFollowTime(qvo.getNextVisiTime()));
                                table.setVisitDoctorName(qvo.getVisitDoctorName());
                                table.setBloodPressureTwo(qvo.getBloodPressureTwo());
                                table.setVisitSituation(qvo.getVisitSituation());
//                                table.setVisitReason(qvo.getVisitReason());
//                                table.setDieDate(qvo.getDieDate());
                                table.setReferralOrg(qvo.getReferralOrg());
                                table.setReferralDept(qvo.getReferralDept());
                                table.setPatientId(user.getId());
                                table.setIsBasic("1");
                                sysDao.getServiceDo().add(table);
                                if(qvo.getDrugList()!=null && qvo.getDrugList().size()>0){
                                    List<AppBasicDrugQvo> lisDrug = qvo.getDrugList();
                                    for(AppBasicDrugQvo lls:lisDrug){
                                        AppMedicationTable vo = new AppMedicationTable();
                                        vo.setVisitId(table.getId());
                                        vo.setUserToTime(lls.getUserToTime());
                                        vo.setUserToDay(lls.getUserToDay());
                                        vo.setMedicineName(lls.getMedicineName());
                                        sysDao.getServiceDo().add(vo);
                                    }
                                }
                                return "true";
                            }else if("2".equals(qvo.getBasicState())){//删除
                                List<AppMedicationTable> lis = sysDao.getServiceDo().loadByPk(AppMedicationTable.class,"visitId",disTable.getId());
                                if(lis!=null && lis.size()>0){
                                    for(AppMedicationTable ll:lis){
                                        sysDao.getServiceDo().delete(ll);
                                    }
                                }
                                sysDao.getServiceDo().delete(disTable);
                                return "true";
                            }
                        }
                        //给患者添加糖尿病服务人群标签
                        boolean flagg = true;
                        List<AppLabelGroup> lisGr = sysDao.getServiceDo().loadByPk(AppLabelGroup.class,"labelSignId",form.getId());
                        if(lisGr !=null && lisGr.size()>0){
                            for(AppLabelGroup ll:lisGr){
                                if(ResidentMangeType.TNB.getValue().equals(ll.getLabelValue())){
                                    flagg = false;
                                }
                            }
                        }
                        if(flagg){
                            sysDao.getAppNoticeDao().addOnlyNotices("系统消息",
                                    "居民"+qvo.getName()+"，已确诊高血压，请前往修改服务人群",NoticesType.XTXX.getValue()+",2", "系统",
                                    form.getSignDrId(), form.getSignPatientId(),DrPatientType.DR.getValue());
                        }

                        //添加随访计划
                        AppFollowPlan plan = new AppFollowPlan();
                        plan.setSfFollowPatientid(user.getId());
                        plan.setSfFollowPatientName(user.getPatientName());
                        plan.setSfFollowDate(ExtendDate.getCalendar(getFollowTime(qvo.getFollowVisitTime())));
                        plan.setSfFollowMode(qvo.getFollowVisitWay());
                        plan.setSfFollowType(ResidentMangeType.GXY.getValue());
//                    plan.setSfOrgId(form.getSignHospId());
                        plan.setSfHosAreaCode(form.getSignAreaCode());
                        plan.setSfTeamId(form.getSignTeamId());
                        plan.setSfFollowDoctorid(drUser.getId());
                        plan.setSfHosId(form.getSignHospId());
                        plan.setSfFollowNum(qvo.getCode());
                        plan.setSfFollowState(FollowPlanState.WEIWANCHENG.getValue());
                        plan.setSfHealthNum(user.getPatientjmda());
                        plan.setSfIsOrNot("1");
                        sysDao.getServiceDo().add(plan);
                        //工作计划
                        AppWorkingPlan plan1 = new AppWorkingPlan();
                        plan1.setPlanDrId(plan.getSfFollowDoctorid());
                        plan1.setPlanDate(Calendar.getInstance());
                        plan1.setPlanType(CommonWorkPlanType.SFJH.getValue());
                        plan1.setPlanState(CommonWorkPlanState.YWC.getValue());
                        plan1.setPlanForeignId(plan.getId());
                        if(drUser!=null){
                            plan1.setPlanHospId(drUser.getDrHospId());
                            AppHospDept hosp = (AppHospDept) this.sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                            if(hosp!=null){
                                plan1.setPlanAreaCode(hosp.getHospAreaCode());
                            }
                        }
                        plan1.setPlanTeamId(form.getSignTeamId());
                        sysDao.getServiceDo().add(plan1);

                        if(FollowPlanState.SHIFANG.getValue().equals(qvo.getVisitSituation())){//失访
                            plan.setSfFollowState(qvo.getVisitSituation());
                            plan.setSfMissReason(qvo.getVisitReason());
                            plan.setSfXaxis(qvo.getSfXaxis());
                            plan.setSfYaxis(qvo.getSfYaxis());
                            plan.setNextTime(ExtendDate.getCalendar(getFollowTime(qvo.getNextVisiTime())));
                            sysDao.getServiceDo().modify(plan);
                            //添加下一次随访计划
                            /*AppFollowPlan newPlan = new AppFollowPlan();
                            newPlan.setSfFollowState(FollowPlanState.WEIWANCHENG.getValue());
                            newPlan.setSfFollowDoctorid(plan.getSfFollowDoctorid());
                            newPlan.setSfFollowPatientid(plan.getSfFollowPatientid());
                            newPlan.setSfFollowMode(plan.getSfFollowMode());
                            newPlan.setSfFollowPatientName(plan.getSfFollowPatientName());
                            newPlan.setSfCreateDate(Calendar.getInstance());
                            newPlan.setSfFollowDate(ExtendDate.getCalendar(getFollowTime(qvo.getNextVisiTime())));
                            newPlan.setSfPid(plan.getSfFollowNum());
                            newPlan.setSfHosId(plan.getSfHosId());
                            newPlan.setSfHosAreaCode(plan.getSfHosAreaCode());
                            newPlan.setSfTeamId(plan.getSfTeamId());
                            newPlan.setSfFollowType(plan.getSfFollowType());
                            String nextNum = "";
                            AppHospDept dept= (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,plan.getSfHosId());
                            if(dept!=null && dept.getHospAreaCode()!=null) {
                                AppSerial serial = sysDao.getAppSignformDao().getAppSerial(dept.getHospAreaCode().substring(0, 4), "follow");
                                if(serial!=null) {
                                    Map<String,Object> bcnum = sysDao.getAppSignformDao().getNum(serial.getSerialNo(),SignFormType.FOLLOWSTATE.getValue());
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

                            AppWorkingPlan plan2 = new AppWorkingPlan();
                            plan2.setPlanDrId(newPlan.getSfFollowDoctorid());
                            plan2.setPlanDate(newPlan.getSfFollowDate());
                            plan2.setPlanType(CommonWorkPlanType.SFJH.getValue());
                            plan2.setPlanState(CommonWorkPlanState.WWC.getValue());
                            plan2.setPlanForeignId(newPlan.getId());
                            if(drUser!=null){
                                plan2.setPlanHospId(drUser.getDrHospId());
                                AppHospDept hosp = (AppHospDept) this.sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                                if(hosp!=null){
                                    plan2.setPlanAreaCode(hosp.getHospAreaCode());
                                }
                            }
                            plan2.setPlanTeamId(form.getSignTeamId());
                            sysDao.getServiceDo().add(plan2);*/

                        }else if(FollowPlanState.SIWANG.getValue().equals(qvo.getVisitSituation())) {//死亡
                            plan.setSfFollowState(qvo.getVisitSituation());
                            plan.setSfMissReason(qvo.getVisitReason());
                            plan.setSfXaxis(qvo.getSfXaxis());
                            plan.setSfYaxis(qvo.getSfYaxis());
                            if(StringUtils.isNotBlank(qvo.getDieDate())){
                                plan.setSfDeadTime(ExtendDate.getCalendar(qvo.getDieDate()));
                            }
                            sysDao.getServiceDo().modify(plan);

                        }else{//正常随访下添加下一次随访计划
                            plan.setSfFollowState(FollowPlanState.YIWANCHENG.getValue());
                            plan.setSfEndDate(ExtendDate.getCalendar(getFollowTime(qvo.getVisitDate())));
                            plan.setSfReferral(qvo.getReferral());
                            plan.setSfReferralDept(qvo.getReferralDept());
                            plan.setSfReferralOrg(qvo.getReferralOrg());
                            plan.setSfReferralReason(qvo.getReferralReason());
                            plan.setSfXaxis(qvo.getSfXaxis());
                            plan.setSfYaxis(qvo.getSfYaxis());
                            Map<String,Object> map = new HashMap<String,Object>();
                            map.put("patientId",plan.getSfFollowPatientid());
                            map.put("type",ResidentMangeType.GXY.getValue());
                            map.put("state",FollowPlanState.YIWANCHENG.getValue());
                            String sql = "SELECT count(1) FROM APP_FOLLOW_PLAN WHERE SF_FOLLOW_STATE =:state AND SF_FOLLOW_PATIENTID=:patientId AND SF_FOLLOW_TYPE =:type";
                            int num11 = sysDao.getServiceDo().gteSqlCount(sql,map);
                            plan.setSfTypeNum(String.valueOf(num11+1));
                            plan.setNextTime(ExtendDate.getCalendar(getFollowTime(qvo.getNextVisiTime())));
                            sysDao.getServiceDo().modify(plan);

                            //新增下一次随访计划
                            /*AppFollowPlan pl = new AppFollowPlan();
                            if(org.apache.commons.lang.StringUtils.isNotBlank(plan.getSfFollowPatientid())) {
                                pl.setSfFollowPatientid(user.getId());
                                pl.setSfFollowPatientName(user.getPatientName());
                            }
                            pl.setSfFollowType(ResidentMangeType.GXY.getValue());
                            pl.setSfFollowDoctorid(plan.getSfFollowDoctorid());
                            pl.setSfFollowMode(plan.getSfFollowMode());
                            pl.setSfFollowState(FollowPlanState.WEIWANCHENG.getValue());//随访状态未开始
                            if("1".equals(qvo.getReferral())||FollowThisType.KZBMY.getValue().equals(qvo.getVisitThisType())||DrugState.YOU.getValue().equals(qvo.getDrugReaction())){
                                Calendar cal = Calendar.getInstance();
                                cal.add(Calendar.DATE,14);
                                pl.setSfFollowDate(cal);
                            }else{
                                //后台计算正常情况下一次随访计划时间
                                if(org.apache.commons.lang.StringUtils.isNotBlank(qvo.getNextVisiTime())){
                                    pl.setSfFollowDate(ExtendDate.getCalendar(getFollowTime(qvo.getNextVisiTime())));
                                }else{
                                    Calendar ss = Calendar.getInstance();
                                    ss.add(Calendar.MONTH,3);
                                    pl.setSfFollowDate(ss);
                                }
                            }
                            pl.setSfCreateDate(Calendar.getInstance());
                            String nextNum = "";
                            String deptId = "";
                            String hospAreaCode = "";
                            AppHospDept dept= (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                            if(dept!=null && dept.getHospAreaCode()!=null) {
                                AppSerial serial = sysDao.getAppSignformDao().getAppSerial(dept.getHospAreaCode().substring(0, 4), "follow");
                                if(serial!=null) {
                                    Map<String,Object> bcnum = sysDao.getAppSignformDao().getNum(serial.getSerialNo(),SignFormType.FOLLOWSTATE.getValue());
                                    serial.setSerialNo(bcnum.get("old").toString());
                                    sysDao.getServiceDo().modify(serial);
                                    nextNum = bcnum.get("new").toString();
                                }
                                deptId = dept.getId();
                                hospAreaCode = dept.getHospAreaCode();
                            }
                            //查询下次随访计划的编号
                            List<AppFollowPlan> listP = sysDao.getServiceDo().loadByPk(AppFollowPlan.class,"sfPid",plan.getSfFollowNum());
                            if(listP!=null&&listP.size()>0){
                                pl.setSfFollowNum(listP.get(0).getSfFollowNum());
                            }else{
                                pl.setSfFollowNum(nextNum);//批次号
                            }
                            pl.setSfHosId(deptId);
                            pl.setSfHosAreaCode(hospAreaCode);
                            pl.setSfTeamId(plan.getSfTeamId());
                            pl.setSfPid(plan.getSfFollowNum());
                            pl.setSfReferral(qvo.getReferral());
                            pl.setSfReferralDept(qvo.getReferralDept());
                            pl.setSfReferralOrg(qvo.getReferralOrg());
                            pl.setSfReferralReason(qvo.getReferralReason());
                            sysDao.getServiceDo().add(pl);
                            AppWorkingPlan plan2 = new AppWorkingPlan();
                            plan2.setPlanDrId(pl.getSfFollowDoctorid());
                            plan2.setPlanDate(pl.getSfFollowDate());
                            plan2.setPlanType(CommonWorkPlanType.SFJH.getValue());
                            plan2.setPlanState(CommonWorkPlanState.WWC.getValue());
                            plan2.setPlanForeignId(pl.getId());
                            if(drUser!=null){
                                plan2.setPlanHospId(drUser.getDrHospId());
                                AppHospDept hosp = (AppHospDept) this.sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                                if(hosp!=null){
                                    plan2.setPlanAreaCode(hosp.getHospAreaCode());
                                }
                            }
                            plan2.setPlanTeamId(form.getSignTeamId());
                            sysDao.getServiceDo().add(plan2);*/

                            AppHdBlooPressureTable table = new AppHdBlooPressureTable();
                            table.setVisitId(plan.getId());
                            table.setName(qvo.getName());
                            table.setCode(qvo.getCode());
                            table.setFollowVisitTime(ExtendDate.getYMD(ExtendDate.getCalendar(getFollowTime(qvo.getFollowVisitTime()))));
                            table.setFollowVisitWay(qvo.getFollowVisitWay());
                            table.setSymptoms(qvo.getSymptoms());
                            table.setSymptomsOther(qvo.getSymptomsOther());
                            table.setBloodPressureOne(qvo.getBloodPressureOne());
                            table.setWeight(qvo.getWeight());
                            table.setNextWeight(qvo.getNextWeight());
                            table.setHeight(qvo.getHeight());
                            table.setBmi(qvo.getBmi());
                            table.setHeartRate(qvo.getHeartRate());
                            table.setSignsOther(qvo.getSignsOther());
                            table.setSmokingToDay(qvo.getSmokingToDay());
                            table.setSmokingNextToDay(qvo.getSmokingNextToDay());
                            table.setDrinkingToDay(qvo.getDrinkingToDay());
                            table.setDrinkingNextToDay(qvo.getDrinkingNextToDay());
                            table.setActivityToWeek(qvo.getActivityToWeek());
                            table.setActivityToTime(qvo.getActivityToTime());
                            table.setActivityNextToWeek(qvo.getActivityNextToWeek());
                            table.setActivityNextToTime(qvo.getActivityNextToTime());
                            table.setSaltSituation(qvo.getSaltSituation());
                            table.setSaltNextSituation(qvo.getSaltNextSituation());
                            table.setMentalityAdjust(qvo.getMentalityAdjust());
                            table.setFollowingBehavior(qvo.getFollowingBehavior());
                            table.setFzCheck(qvo.getFzCheck());
                            table.setMedicationAdherence(qvo.getMedicationAdherence());
                            table.setDrugReaction(qvo.getDrugReaction());
                            table.setDrugReactionContent(qvo.getDrugReactionContent());
                            table.setVisitThisType(qvo.getVisitThisType());
                            table.setReferral(qvo.getReferral());
                            table.setReferralReason(qvo.getReferralReason());
                            if(StringUtils.isNotBlank(qvo.getNextVisiTime())){
                                table.setNextVisiTime(ExtendDate.getYMD(ExtendDate.getCalendar(getFollowTime(qvo.getNextVisiTime()))));
                            }
                            table.setVisitDoctorName(drUser.getId());
                            table.setBloodPressureTwo(qvo.getBloodPressureTwo());
                            table.setVisitSituation(qvo.getVisitSituation());
//                                table.setVisitReason(qvo.getVisitReason());
//                                table.setDieDate(qvo.getDieDate());
                            table.setReferralOrg(qvo.getReferralOrg());
                            table.setReferralDept(qvo.getReferralDept());
                            table.setPatientId(user.getId());
                            table.setIsBasic("1");

                            sysDao.getServiceDo().add(table);
                            if(qvo.getDrugList()!=null && qvo.getDrugList().size()>0){
                                List<AppBasicDrugQvo> lisDrug = qvo.getDrugList();
                                for(AppBasicDrugQvo lls:lisDrug){
                                    AppMedicationTable vo = new AppMedicationTable();
                                    vo.setVisitId(table.getId());
                                    vo.setUserToTime(lls.getUserToTime());
                                    vo.setUserToDay(lls.getUserToDay());
                                    vo.setMedicineName(lls.getMedicineName());
                                    sysDao.getServiceDo().add(vo);
                                }
                            }
                            PerformanceDataQvo qqvo = new PerformanceDataQvo();
                            String fwType = "";
                            String sermeal = "";//服务包id
                            fwType = sysDao.getAppLabelGroupDao().findFwValue(form.getId());
                            sermeal = form.getSignpackageid();
                            qqvo.setPerName(user.getPatientName());
                            qqvo.setPerIdno(user.getPatientIdno());
                            qqvo.setPerArea(form.getSignAreaCode());
                            qqvo.setServeDate(getFollowTime(qvo.getVisitDate()));
                            qqvo.setPerCreateDate(getFollowTime(qvo.getVisitDate()));
                            qqvo.setHospId(form.getSignHospId());
                            qqvo.setDrId(form.getSignDrId());
                            qqvo.setTeamId(form.getSignTeamId());
                            qqvo.setPerSource("1");
                            qqvo.setPerType(PerformanceType.DQSF.getValue());
//                            sysDao.getAppPerformanceTableDao().addPerformanceOne(qqvo,sermeal,fwType,PerformanceType.DQSF.getValue());
                            qqvo.setPerFollowType(ResidentMangeType.GXY.getValue());
                            qqvo.setPerForeign(table.getId());
                            sysDao.getAppPerformanceStatisticsDao().addPerformanceData(qqvo,sermeal,fwType);
                            //基卫高血压随访提取血压数据到用户血压信息表
                            AppThreeBloodPressureDataVo gxy = new AppThreeBloodPressureDataVo();
                            gxy.setIdno(user.getPatientIdno());
                            gxy.setName(user.getPatientName());
                            gxy.setHighPressure(table.getBloodPressureOne());
                            gxy.setLowVoltage(table.getBloodPressureTwo());
                            gxy.setPulse(table.getHeartRate());
                            //随访时间只有年月日，所以加上时分秒
                            gxy.setUploadTime(qvo.getVisitDate()+"000000");
                            gxy.setSourceType("4");
                            sysDao.getAppUserBloodpressureDao().addThreeBloodPressureData(gxy);

                        }

                    }
                }
            }
        }
        return "true";
    }
}
