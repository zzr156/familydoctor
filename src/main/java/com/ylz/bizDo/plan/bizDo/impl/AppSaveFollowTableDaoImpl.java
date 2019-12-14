package com.ylz.bizDo.plan.bizDo.impl;


import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.app.vo.PerformanceDataQvo;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.plan.Entity.*;
import com.ylz.bizDo.plan.bizDo.AppSaveFollowTableDao;
import com.ylz.bizDo.plan.po.*;
import com.ylz.bizDo.plan.vo.*;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.*;
import com.ylz.packcommon.common.exception.DaoException;
import com.ylz.packcommon.common.util.AreaUtils;
import com.ylz.packcommon.common.util.CopyUtils;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by zzl on 2017/7/24.
 */
@Service("appSaveFollowTableDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppSaveFollowTableDaoImpl implements AppSaveFollowTableDao {
    @Autowired
    public SysDao sysDao;

    /**
     * 保存高血压随访记录
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public AppSaveFollowQvo saveHdBloo(AppSaveFollowQvo qvo) throws Exception {
        AppHdBlooPressureTable table = new AppHdBlooPressureTable();
        CopyUtils.Copy(qvo.getSaveHdBloo(),table);
        AppFollowPlan plan = (AppFollowPlan)sysDao.getServiceDo().find(AppFollowPlan.class,table.getVisitId());
        if(plan!=null){
            table.setName(plan.getSfFollowPatientName());
            table.setCode(plan.getSfFollowNum());
            table.setFollowVisitTime(ExtendDate.getYMD(plan.getSfFollowDate()));
            if(StringUtils.isBlank(table.getFollowVisitWay())){
                table.setFollowVisitWay(plan.getSfFollowMode());
            }else{
                plan.setSfFollowMode(table.getFollowVisitWay());
            }
            table.setPatientId(plan.getSfFollowPatientid());
            //查询第几次随访
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("patientId",plan.getSfFollowPatientid());
            map.put("type",ResidentMangeType.GXY.getValue());
            map.put("state",FollowPlanState.YIWANCHENG.getValue());

            String sql = "SELECT count(1) FROM APP_FOLLOW_PLAN WHERE SF_FOLLOW_STATE = :state AND SF_FOLLOW_PATIENTID=:patientId AND SF_FOLLOW_TYPE =:type ";
            int num = sysDao.getServiceDo().gteSqlCount(sql,map);
            //判断随访计划的随访类型是否为空，不为空新增一条此类型的随访计划
           /* if(StringUtils.isNotBlank(plan.getSfFollowType())){
                AppFollowPlan pp = new AppFollowPlan();
                CopyUtils.Copy(plan,pp);
                pp.setId(null);
                pp.setSfFollowType(ResidentMangeType.GXY.getValue());
                pp.setSfEndDate(Calendar.getInstance());
                pp.setSfFollowState(FollowPlanState.YIWANCHENG.getValue());
                pp.setSfReferral(table.getReferral());
                pp.setSfReferralDept(table.getReferralDept());
                pp.setSfReferralOrg(table.getReferralOrg());
                pp.setSfReferralReason(table.getReferralReason());
                pp.setSfYaxis(qvo.getSfYaxis());
                pp.setSfXaxis(qvo.getSfXaxis());
                pp.setSfTypeNum(String.valueOf(num+1));
                pp.setNextTime(ExtendDate.getCalendar(table.getNextVisiTime()));
                sysDao.getServiceDo().add(pp);
                table.setVisitId(pp.getId());
            }else{//判断随访计划的随访类型是否为空，为空修改类型和转诊情况*/
            if(ResidentMangeType.GXY.getValue().equals(plan.getSfFollowType())){
                plan.setSfFollowType(ResidentMangeType.GXY.getValue());
                plan.setSfEndDate(Calendar.getInstance());
                plan.setSfFollowState(FollowPlanState.YIWANCHENG.getValue());
                plan.setSfReferral(table.getReferral());
                plan.setSfReferralDept(table.getReferralDept());
                plan.setSfReferralOrg(table.getReferralOrg());
                plan.setSfReferralReason(table.getReferralReason());
                plan.setSfXaxis(qvo.getSfXaxis());
                plan.setSfYaxis(qvo.getSfYaxis());
                plan.setSfTypeNum(String.valueOf(num+1));
                plan.setNextTime(ExtendDate.getCalendar(table.getNextVisiTime()));
                sysDao.getServiceDo().modify(plan);
            }
            //新增下一次随访计划情况
            //判断下一次随访计划是否新增
//            Map<String,Object> mapO = new HashMap<String,Object>();
//            mapO.put("patientId",plan.getSfFollowPatientid());
//            String sqll = "SELECT COUNT(1) FROM APP_FOLLOW_PLAN WHERE SF_FOLLOW_PATIENTID=:patientId AND SF_FOLLOW_DATE> NOW()";
//            int numm = sysDao.getServiceDo().findCount(sqll,mapO);
            int numm =0;
            if(numm == 0){//没有下一次随访计划，新增下一次随访计划
                AppFollowPlan pl = new AppFollowPlan();
                if(StringUtils.isNotBlank(plan.getSfFollowPatientid())) {
                    AppPatientUser patientUser = (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class, plan.getSfFollowPatientid());
                    pl.setSfFollowPatientid(patientUser.getId());//患者id
                    pl.setSfFollowPatientName(patientUser.getPatientName());//患者姓名
                }
                pl.setSfFollowType(ResidentMangeType.GXY.getValue());
                pl.setSfFollowDoctorid(plan.getSfFollowDoctorid());
                pl.setSfFollowMode(plan.getSfFollowMode());
                pl.setSfFollowState(FollowPlanState.WEIWANCHENG.getValue());//随访状态未开始
                //有转诊情况、控制不满意、药物不良反应
                if("1".equals(table.getReferral())||FollowThisType.KZBMY.getValue().equals(table.getVisitThisType())||DrugState.YOU.getValue().equals(table.getDrugReaction())){
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DATE,14);
                    pl.setSfFollowDate(cal);
                }else{
                    //正常情况下新增下一次随访计划日期
//                    Calendar cal = Calendar.getInstance();
//                    cal.add(Calendar.MONTH,Integer.parseInt(PropertiesUtil.getConfValue("month")));
//                    pl.setSfFollowDate(cal);
                    if(StringUtils.isNotBlank(table.getNextVisiTime())){
                        pl.setSfFollowDate(ExtendDate.getCalendar(table.getNextVisiTime()));
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
                AppDrUser drUser = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class,plan.getSfFollowDoctorid());
                AppHospDept dept= (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                if(dept!=null && dept.getHospAreaCode()!=null) {
                    AppSerial serial = sysDao.getAppSignformDao().getAppSerial(dept.getHospAreaCode().substring(0, 4), "follow");
                    if(serial!=null) {
                        Map<String,Object> bcnum = sysDao.getAppSignformDao().getNum(serial.getSerialNo(),SignFormType.APPSTATE.getValue());
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
                pl.setSfReferral(table.getReferral());
                pl.setSfReferralDept(table.getReferralDept());
                pl.setSfReferralOrg(table.getReferralOrg());
                pl.setSfReferralReason(table.getReferralReason());
                sysDao.getServiceDo().add(pl);
                table.setNextVisiTime(ExtendDate.getYMD(pl.getSfFollowDate()));
            }
            sysDao.getServiceDo().add(table);

            //修改工作计划的完成状态
            List<AppWorkingPlan> lisWp = sysDao.getServiceDo().loadByPk(AppWorkingPlan.class,"planForeignId",table.getVisitId());
            if(lisWp!=null && lisWp.size()>0){
                AppWorkingPlan wp = lisWp.get(0);
                wp.setPlanFinishDate(Calendar.getInstance());
                wp.setPlanState(CommonWorkPlanState.YWC.getValue());
                this.sysDao.getServiceDo().modify(plan);
            }

            //履约数据
            PerformanceDataQvo qqvo = new PerformanceDataQvo();
            AppPatientUser user = (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,table.getPatientId());
            AppDrUser drUser = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class,plan.getSfFollowDoctorid());
            if(user!=null){
                qqvo.setPerName(user.getPatientName());
                qqvo.setPerIdno(user.getPatientIdno());
                qqvo.setPerType(PerformanceType.DQSF.getValue());
                qqvo.setPerFollowType(ResidentMangeType.GXY.getValue());//高血压
                qqvo.setPerFollowNextDate(table.getNextVisiTime());
                qqvo.setPerForeign(table.getId());
                qqvo.setPerSource("2");
                if(StringUtils.isNotBlank(user.getPatientCity())){
                    CdAddress p = sysDao.getCdAddressDao().findByCode(user.getPatientCity());
                    if(p != null){
                        String code = AreaUtils.getAreaCode(p.getCtcode(),p.getLevel());
                        CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE,code);
                        if(value != null) {
                            qqvo.setPerArea(value.getCodeTitle());
                        }
                    }
                }
            }
            if(drUser != null){
                qqvo.setDrName(drUser.getDrName());
                qqvo.setDrAccount(drUser.getDrAccount());
                qqvo.setDrPwd(drUser.getDrPwd());
                qqvo.setDrGender(drUser.getDrGender());
                qqvo.setDrTel(drUser.getDrTel());
                qqvo.setDrId(drUser.getId());
                AppHospDept dept= (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                if(dept!=null){
                    qqvo.setHospId(dept.getId());
                    qqvo.setHospName(dept.getHospName());
                    qqvo.setHospAreaCode(dept.getHospAreaCode());
                    qqvo.setHospAddress(dept.getHospAddress());
                    qqvo.setHospTel(dept.getHospTel());
                }
            }
            if(StringUtils.isNotBlank(qqvo.getPerArea())){
                if(StringUtils.isNotBlank(qqvo.getPerType())){
                   /* String result = null;
                    if (qqvo.getPerArea().equals(AddressType.FZ.getValue())) {

                    } else if (qqvo.getPerArea().equals(AddressType.QZ.getValue())) {
                        result = "qz_";
                    } else if (qqvo.getPerArea().equals(AddressType.ZZ.getValue())) {
                        result = "zz_";
                    } else if (qqvo.getPerArea().equals(AddressType.PT.getValue())) {
                        result = "pt_";
                    } else if (qqvo.getPerArea().equals(AddressType.NP.getValue())) {
                        result = "np_";
                    } else if (qqvo.getPerArea().equals(AddressType.SM.getValue())) {
                        result = "sm_";
                    }
                    if(StringUtils.isNotBlank(result)){
                        qqvo.setDrId(result+qqvo.getDrId());
                        qqvo.setHospId(result+qqvo.getHospId());
                    }*/
                    String fwType = "";
                    String sermeal = "";//服务包id
                    AppSignForm form = sysDao.getAppSignformDao().findSignFormByUserState(table.getPatientId());
                    if(form != null){
                        fwType = sysDao.getAppLabelGroupDao().findFwValue(form.getId());
                        sermeal = form.getSignpackageid();
//                        sysDao.getAppPerformanceTableDao().addPerformanceOne(qqvo,sermeal,fwType,PerformanceType.DQSF.getValue());
                        sysDao.getAppPerformanceStatisticsDao().addPerformanceData(qqvo,sermeal,fwType);
                    }

                }
            }

            List<AppFollowPublic> lst = sysDao.getServiceDo().loadByPk(AppFollowPublic.class,"followNum",table.getCode());
            if(lst!=null&&lst.size()>0){
                AppFollowPublic tp = lst.get(0);
                tp.setActivityNextToTime(table.getActivityNextToTime());
                tp.setActivityNextToWeek(table.getActivityNextToWeek());
                tp.setActivityToTime(table.getActivityToTime());
                tp.setActivityToWeek(table.getActivityToWeek());
                tp.setBloodPressureOne(table.getBloodPressureOne());
                tp.setBloodPressureTwo(table.getBloodPressureTwo());
                tp.setBmi(table.getBmi());
                tp.setCreateTime(Calendar.getInstance());
                tp.setDrinkingNextToDay(table.getDrinkingNextToDay());
                tp.setDrinkingToDay(table.getDrinkingToDay());
                tp.setFollowNum(table.getCode());
                tp.setHeight(table.getHeight());
                tp.setSmokingNextToDay(table.getSmokingNextToDay());
                tp.setSmokingToDay(table.getSmokingToDay());
                tp.setWeight(table.getWeight());
                tp.setNextWeight(table.getNextWeight());
                tp.setHeartRate(table.getHeartRate());
                sysDao.getServiceDo().modify(tp);
            }else{
                AppFollowPublic tp = new AppFollowPublic();
                tp.setActivityNextToTime(table.getActivityNextToTime());
                tp.setActivityNextToWeek(table.getActivityNextToWeek());
                tp.setActivityToTime(table.getActivityToTime());
                tp.setActivityToWeek(table.getActivityToWeek());
                tp.setBloodPressureOne(table.getBloodPressureOne());
                tp.setBloodPressureTwo(table.getBloodPressureTwo());
                tp.setBmi(table.getBmi());
                tp.setCreateTime(Calendar.getInstance());
                tp.setDrinkingNextToDay(table.getDrinkingNextToDay());
                tp.setDrinkingToDay(table.getDrinkingToDay());
                tp.setFollowNum(table.getCode());
                tp.setHeight(table.getHeight());
                tp.setSmokingNextToDay(table.getSmokingNextToDay());
                tp.setSmokingToDay(table.getSmokingToDay());
                tp.setWeight(table.getWeight());
                tp.setHeartRate(table.getHeartRate());
                tp.setNextWeight(table.getNextWeight());
                sysDao.getServiceDo().add(tp);

            }

            List<AppMedicationTable> list = qvo.getUserMedicine();
            List<AppMedicationTable> lss = new ArrayList<AppMedicationTable>();
            if(list!=null&&list.size()>0){
                for(AppMedicationTable ls:list){
                    ls.setVisitId(table.getId());
                    sysDao.getServiceDo().add(ls);
                    lss.add(ls);
                }
            }
            qvo.setSaveHdBloo(table);
            qvo.setUserMedicine(lss);
        }
        return qvo;
    }

    /**
     * 个人基本信息 根据随访id 获取个人基本信息 包括随访次数
     * @param qvo id 随访id
     * @return
     * @throws Exception
     */
    @Override
    public AppFollowPatienBasic findFollBasic(AppFollwCommQvo qvo) throws Exception {
        AppFollowPatienBasic basic=null;
        Map<String,Object> map = new HashMap<>();
        if(StringUtils.isNotBlank(qvo.getId())){
                AppFollowPlan foll=(AppFollowPlan)sysDao.getServiceDo().find(AppFollowPlan.class,qvo.getId());
                if(foll!=null && StringUtils.isNotBlank(foll.getSfFollowPatientid())){
                    String sql="SELECT\n" +
                            "\ta.ID patientId,\n" +
                            "\tb.SF_FOLLOW_NUM sfFollowNum,\n" +
                            "\ta.PATIENT_NAME patientName,\n" +
                            "\ta.PATIENT_GENDER patientGender,\n" +
                            "\tDATE_FORMAT(a.PATIENT_BIRTHDAY,'%Y-%m-%d') patientBirthday,\n" +
                            "\ta.PATIENT_TEL patientTel,\n" +
                            "\ta.PATIENT_ADDRESS patientAddress,\n" +
                            "\ta.PATIENT_IDNO patientIdno,\n" +
                            "\ta.PATIENT_AGE patientAge,\n" +
                            "\tb.SF_FOLLOW_MODE sfFollowMode,\n" +
                            "\tb.ID id," +
                            "b.SF_FOLLOW_DATE sfFollowDate,\n" +
                            "b.SF_FOLLOW_DOCTORID drId," +
                            "\t'' persGroup\n" +
                            "FROM\n" +
                            "\tAPP_PATIENT_USER a LEFT JOIN APP_FOLLOW_PLAN b ON a.ID=b.SF_FOLLOW_PATIENTID\n" +
                            "WHERE\n" +
                            "  a.ID=:patientId and b.ID=:ID ";
                    map.put("patientId",foll.getSfFollowPatientid());
                    map.put("ID",qvo.getId());
                    List<AppFollowPatienBasic> ls=sysDao.getServiceDo().findSqlMapRVo(sql,map,AppFollowPatienBasic.class);
                    if(ls!=null && !ls.isEmpty()){
                        basic=ls.get(0);
                    }
                }

        }
        return basic;
    }

    /**
     * 根据随访id或患者id 查询随访记录
     * @param qvo id 随访id patientId 患者id
     * @return
     */
    public List<AppFollowPlan> findFollLosg(AppFollwCommQvo qvo) throws Exception{
        List<AppFollowPlan> ls=null;
        Map<String,Object> map = new HashMap<>();
        if(StringUtils.isNotBlank(qvo.getId())){
            AppFollowPlan foll=(AppFollowPlan)sysDao.getServiceDo().find(AppFollowPlan.class,qvo.getId());
            String sql="SELECT * from APP_FOLLOW_PLAN a where a.SF_FOLLOW_STATE=1 and a.SF_FOLLOW_PATIENTID=:SF_FOLLOW_PATIENTID ORDER BY a.SF_FOLLOW_DATE desc";
            map.put("SF_FOLLOW_PATIENTID",foll.getSfFollowPatientid());
            ls=sysDao.getServiceDo().findSqlMap(sql,map,AppFollowPlan.class);
            return ls;
        }
        if(StringUtils.isNotBlank(qvo.getPatientId())){
            String sql="SELECT * from APP_FOLLOW_PLAN a where a.SF_FOLLOW_STATE=1 and a.SF_FOLLOW_PATIENTID=:SF_FOLLOW_PATIENTID ORDER BY a.SF_FOLLOW_DATE desc";
            map.put("SF_FOLLOW_PATIENTID",qvo.getPatientId());
            ls=sysDao.getServiceDo().findSqlMap(sql,map,AppFollowPlan.class);
            return ls;
        }
        return ls;
    }



    /**
     * 最后随访诊断单获取 根据患者id和服务人群类型 LableValue
     * @param qvo id patientId 患者id persGroup 服务人群类型
     * @return
     * @throws Exception
     */
    public Object findMaxPersGroup(AppFollwCommQvo qvo) throws Exception{
        qvo.setPageCount(1);
        Map<String,Object> map = new HashMap<>();
        String sql="SELECT * from APP_FOLLOW_PLAN a where a.SF_FOLLOW_PATIENTID=:SF_FOLLOW_PATIENTID and a.SF_FOLLOW_TYPE=:SF_FOLLOW_TYPE and a.SF_FOLLOW_STATE=1 ORDER BY a.SF_END_DATE desc";
        map.put("SF_FOLLOW_PATIENTID",qvo.getPatientId());
        map.put("SF_FOLLOW_TYPE",qvo.getPersGroup());
        List<AppFollowPlan> ls=sysDao.getServiceDo().findSqlMap(sql,map,AppFollowPlan.class,qvo);//查询该类型最后一条随访
        if(ls!=null && !ls.isEmpty()){
            //高血压
            if(ResidentMangeType.GXY.getValue().equals(qvo.getPersGroup())){
                List<AppHdBlooPressureTable> bls=sysDao.getServiceDo().loadByPk(AppHdBlooPressureTable.class,"visitId",ls.get(0).getId());
                if(bls!=null && !bls.isEmpty()) {
                    return bls.get(0);
                }
            }
            //糖尿病
            if(ResidentMangeType.TNB.getValue().equals(qvo.getPersGroup())){
                List<AppDiabetesTable> bls=sysDao.getServiceDo().loadByPk(AppDiabetesTable.class,"visitId",ls.get(0).getId());
                if(bls!=null && !bls.isEmpty()) {
                    return bls.get(0);
                }
            }
            //新生儿家庭访视
            if(ResidentMangeType.ETLZLS.getValue().equals(qvo.getPersGroup())){
                List<AppChild> child = sysDao.getServiceDo().loadByPk(AppChild.class,"childVisitId",ls.get(0).getId());
                if(child!=null&&child.size()>0){
                    return child.get(0);
                }
            }
            //产后访视
            if(ResidentMangeType.YCF.getValue().equals(qvo.getPersGroup())){
                List<AppPostpartumVisit> postPar = sysDao.getServiceDo().loadByPk(AppPostpartumVisit.class,"postVisitId",ls.get(0).getId());
                if(postPar!=null&&postPar.size()>0){
                    return postPar.get(0);
                }
            }
            if(ResidentMangeType.TY.getValue().equals(qvo.getPersGroup())){
                List<AppGeneralTable> gen = sysDao.getServiceDo().loadByPk(AppGeneralTable.class,"genVisitId",ls.get(0).getId());
                if(gen != null){
                    return gen.get(0);
                }
            }
        }
        return null;
    }

    /**
     * 保存糖尿病随访记录
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public AppSaveFollowQvo saveDiabetes(AppSaveFollowQvo qvo) throws Exception {
        AppDiabetesTable table = new AppDiabetesTable();
        CopyUtils.Copy(qvo.getSaveDiabetes(),table);
        AppFollowPlan plan = (AppFollowPlan)sysDao.getServiceDo().find(AppFollowPlan.class,table.getVisitId());
        //判断随访计划的随访类型是否为空，不为空新增一条此类型的随访计划
        if(plan!=null){
            table.setName(plan.getSfFollowPatientName());
            table.setCode(plan.getSfFollowNum());
            table.setFollowVisitTime(ExtendDate.getYMD(plan.getSfFollowDate()));
            if(StringUtils.isBlank(table.getFollowVisitWay())){
                table.setFollowVisitWay(plan.getSfFollowMode());
            }else {
                plan.setSfFollowMode(table.getFollowVisitWay());
            }
            table.setPatientId(plan.getSfFollowPatientid());
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("patientId",plan.getSfFollowPatientid());
            map.put("type",ResidentMangeType.TNB.getValue());
            map.put("state",FollowPlanState.YIWANCHENG.getValue());
            String sql = "SELECT count(1) FROM APP_FOLLOW_PLAN WHERE SF_FOLLOW_STATE =:state AND SF_FOLLOW_PATIENTID=:patientId AND SF_FOLLOW_TYPE =:type";
            int num = sysDao.getServiceDo().gteSqlCount(sql,map);
            /*if(StringUtils.isNotBlank(plan.getSfFollowType())){
                AppFollowPlan pp = new AppFollowPlan();
                CopyUtils.Copy(plan,pp);
                pp.setId(null);
                pp.setSfFollowType(ResidentMangeType.TNB.getValue());
                pp.setSfEndDate(Calendar.getInstance());
                pp.setSfFollowState(FollowPlanState.YIWANCHENG.getValue());
                pp.setSfReferral(table.getReferral());
                pp.setSfReferralDept(table.getReferralDept());
                pp.setSfReferralOrg(table.getReferralOrg());
                pp.setSfReferralReason(table.getReferralReason());
                pp.setSfYaxis(qvo.getSfYaxis());
                pp.setSfXaxis(qvo.getSfXaxis());
                pp.setSfTypeNum(String.valueOf(num+1));
                pp.setNextTime(ExtendDate.getCalendar(table.getNextVisiTime()));
                sysDao.getServiceDo().add(pp);
                table.setVisitId(pp.getId());
            }else{//判断随访计划的随访类型是否为空，为空修改类型和转诊情况*/
            if(ResidentMangeType.TNB.getValue().equals(plan.getSfFollowType())){
                plan.setSfEndDate(Calendar.getInstance());
                plan.setSfFollowState(FollowPlanState.YIWANCHENG.getValue());
                plan.setSfReferral(table.getReferral());
                plan.setSfReferralDept(table.getReferralDept());
                plan.setSfReferralOrg(table.getReferralOrg());
                plan.setSfReferralReason(table.getReferralReason());
                plan.setSfXaxis(qvo.getSfXaxis());
                plan.setSfYaxis(qvo.getSfYaxis());
                plan.setSfTypeNum(String.valueOf(num+1));
                plan.setNextTime(ExtendDate.getCalendar(table.getNextVisiTime()));
                sysDao.getServiceDo().modify(plan);
            }
            //新增下一次随访计划情况
            //判断下一次随访计划是否新增
            /*Map<String,Object> mapO = new HashMap<String,Object>();
            mapO.put("patientId",plan.getSfFollowPatientid());
            String sqll = "SELECT COUNT(1) FROM APP_FOLLOW_PLAN WHERE SF_FOLLOW_PATIENTID=:patientId AND SF_FOLLOW_DATE> NOW()";
            int numm = sysDao.getServiceDo().findCount(sqll,mapO);*/
            int numm = 0;
            if(numm == 0){//没有下一次随访计划
                AppFollowPlan pl = new AppFollowPlan();
                if(StringUtils.isNotBlank(plan.getSfFollowPatientid())) {
                    AppPatientUser patientUser = (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class, plan.getSfFollowPatientid());
                    pl.setSfFollowPatientid(patientUser.getId());
                    pl.setSfFollowPatientName(patientUser.getPatientName());
                }
                pl.setSfFollowType(ResidentMangeType.TNB.getValue());
                pl.setSfFollowDoctorid(plan.getSfFollowDoctorid());
                pl.setSfFollowMode(plan.getSfFollowMode());
                pl.setSfFollowState(FollowPlanState.WEIWANCHENG.getValue());//随访状态未开始
                if("1".equals(table.getReferral())||FollowThisType.KZBMY.getValue().equals(table.getVisitThisType())||DrugState.YOU.getValue().equals(table.getDrugReaction())){
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DATE,14);
                    pl.setSfFollowDate(cal);
                }else{
                    //后台计算正常情况下一次随访计划时间
//                    Calendar cal = Calendar.getInstance();
//                    cal.add(Calendar.MONTH,Integer.parseInt(PropertiesUtil.getConfValue("month")));
//                    pl.setSfFollowDate(cal);
                    if(StringUtils.isNotBlank(table.getNextVisiTime())){
                        pl.setSfFollowDate(ExtendDate.getCalendar(table.getNextVisiTime()));
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
                AppDrUser drUser = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class,plan.getSfFollowDoctorid());
                AppHospDept dept= (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                if(dept!=null && dept.getHospAreaCode()!=null) {
                    AppSerial serial = sysDao.getAppSignformDao().getAppSerial(dept.getHospAreaCode().substring(0, 4), "follow");
                    if(serial!=null) {
                        Map<String,Object> bcnum = sysDao.getAppSignformDao().getNum(serial.getSerialNo(),SignFormType.APPSTATE.getValue());
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
                pl.setSfReferral(table.getReferral());
                pl.setSfReferralDept(table.getReferralDept());
                pl.setSfReferralOrg(table.getReferralOrg());
                pl.setSfReferralReason(table.getReferralReason());
                sysDao.getServiceDo().add(pl);
                table.setNextVisiTime(ExtendDate.getYMD(pl.getSfFollowDate()));
            }
//        签名图片
//        if(StringUtils.isNotBlank(table.getVisitDoctorName())){
//            String path = sysDao.getIoUtils().pathUrl(PropertiesUtil.getConfValue("filePicture"),PropertiesUtil.getConfValue("filePictureYz"),vo.getPatientImageName());
//            FileUtils.decoderBase64File(table.getVisitDoctorName(),PropertiesUtil.getConfValue("filePicture")+path);
//            table.setVisitDoctorName(path);
//
//        }
            sysDao.getServiceDo().add(table);
            //修改工作计划的完成状态
            List<AppWorkingPlan> lisWp = sysDao.getServiceDo().loadByPk(AppWorkingPlan.class,"planForeignId",table.getVisitId());
            if(lisWp!=null && lisWp.size()>0){
                AppWorkingPlan wp = lisWp.get(0);
                wp.setPlanFinishDate(Calendar.getInstance());
                wp.setPlanState(CommonWorkPlanState.YWC.getValue());
                this.sysDao.getServiceDo().modify(plan);
            }

            //履约数据
            PerformanceDataQvo qqvo = new PerformanceDataQvo();
            AppPatientUser user = (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,table.getPatientId());
            AppDrUser drUser = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class,plan.getSfFollowDoctorid());
            if(user!=null){
                qqvo.setPerName(user.getPatientName());
                qqvo.setPerIdno(user.getPatientIdno());
                qqvo.setPerType(PerformanceType.DQSF.getValue());
                qqvo.setPerFollowType(ResidentMangeType.TNB.getValue());//糖尿病
                qqvo.setPerFollowNextDate(table.getNextVisiTime());
                qqvo.setPerForeign(table.getId());
                qqvo.setPerSource("2");
                if(StringUtils.isNotBlank(user.getPatientCity())){
                    CdAddress p = sysDao.getCdAddressDao().findByCode(user.getPatientCity());
                    if(p != null){
                        String code = AreaUtils.getAreaCode(p.getCtcode(),p.getLevel());
                        CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE,code);
                        if(value != null) {
                            qqvo.setPerArea(value.getCodeTitle());
                        }
                    }
                }
            }
            if(drUser != null){
                qqvo.setDrName(drUser.getDrName());
                qqvo.setDrAccount(drUser.getDrAccount());
                qqvo.setDrPwd(drUser.getDrPwd());
                qqvo.setDrGender(drUser.getDrGender());
                qqvo.setDrTel(drUser.getDrTel());
                qqvo.setDrId(drUser.getId());
                AppHospDept dept= (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                if(dept!=null){
                    qqvo.setHospId(dept.getId());
                    qqvo.setHospName(dept.getHospName());
                    qqvo.setHospAreaCode(dept.getHospAreaCode());
                    qqvo.setHospAddress(dept.getHospAddress());
                    qqvo.setHospTel(dept.getHospTel());
                }
            }
            if(StringUtils.isNotBlank(qqvo.getPerArea())){
                if(StringUtils.isNotBlank(qqvo.getPerType())){
//                    String result = null;
//                    if (qqvo.getPerArea().equals(AddressType.FZ.getValue())) {
//result = "";
//                    } else if (qqvo.getPerArea().equals(AddressType.QZ.getValue())) {
//                        result = "qz_";
//                    } else if (qqvo.getPerArea().equals(AddressType.ZZ.getValue())) {
//                        result = "zz_";
//                    } else if (qqvo.getPerArea().equals(AddressType.PT.getValue())) {
//                        result = "pt_";
//                    } else if (qqvo.getPerArea().equals(AddressType.NP.getValue())) {
//                        result = "np_";
//                    } else if (qqvo.getPerArea().equals(AddressType.SM.getValue())) {
//                        result = "sm_";
//                    }
//                    if(StringUtils.isNotBlank(result)){
//                        qqvo.setDrId(result+qqvo.getDrId());
//                        qqvo.setHospId(result+qqvo.getHospId());
//                    }
                    String fwType = "";
                    String sermeal = "";//服务包id
                    AppSignForm form = sysDao.getAppSignformDao().findSignFormByUserState(table.getPatientId());
                    if(form != null){
                        fwType = sysDao.getAppLabelGroupDao().findFwValue(form.getId());
                        sermeal = form.getSignpackageid();
//                        sysDao.getAppPerformanceTableDao().addPerformanceOne(qqvo,sermeal,fwType,PerformanceType.DQSF.getValue());
                        qqvo.setPerType(PerformanceType.DQSF.getValue());
                        qqvo.setPerFollowType(ResidentMangeType.TNB.getValue());
                        sysDao.getAppPerformanceStatisticsDao().addPerformanceData(qqvo,sermeal,fwType);
                    }
                }
            }


            List<AppFollowPublic> lst = sysDao.getServiceDo().loadByPk(AppFollowPublic.class,"followNum",table.getCode());
            if(lst!=null&&lst.size()>0){
                AppFollowPublic tp = lst.get(0);
                tp.setActivityNextToTime(table.getActivityNextToTime());
                tp.setActivityNextToWeek(table.getActivityNextToWeek());
                tp.setActivityToTime(table.getActivityToTime());
                tp.setActivityToWeek(table.getActivityToWeek());
                tp.setBloodPressureOne(table.getBloodPressureOne());
                tp.setBloodPressureTwo(table.getBloodPressureTwo());
                tp.setBmi(table.getBmi());
                tp.setCreateTime(Calendar.getInstance());
                tp.setDrinkingNextToDay(table.getDrinkingNextToDay());
                tp.setDrinkingToDay(table.getDrinkingToDay());
                tp.setFollowNum(table.getCode());
                tp.setHeight(table.getHeight());
                tp.setSmokingNextToDay(table.getSmokingNextToDay());
                tp.setSmokingToDay(table.getSmokingToDay());
                tp.setWeight(table.getWeight());
                tp.setNextWeight(table.getNextWeight());
                sysDao.getServiceDo().modify(tp);
            }else{
                AppFollowPublic tp = new AppFollowPublic();
                tp.setActivityNextToTime(table.getActivityNextToTime());
                tp.setActivityNextToWeek(table.getActivityNextToWeek());
                tp.setActivityToTime(table.getActivityToTime());
                tp.setActivityToWeek(table.getActivityToWeek());
                tp.setBloodPressureOne(table.getBloodPressureOne());
                tp.setBloodPressureTwo(table.getBloodPressureTwo());
                tp.setBmi(table.getBmi());
                tp.setCreateTime(Calendar.getInstance());
                tp.setDrinkingNextToDay(table.getDrinkingNextToDay());
                tp.setDrinkingToDay(table.getDrinkingToDay());
                tp.setFollowNum(table.getCode());
                tp.setHeight(table.getHeight());
                tp.setSmokingNextToDay(table.getSmokingNextToDay());
                tp.setSmokingToDay(table.getSmokingToDay());
                tp.setWeight(table.getWeight());
                tp.setNextWeight(table.getNextWeight());
                sysDao.getServiceDo().add(tp);

            }
            List<AppMedicationTable> list = qvo.getUserMedicine();
            List<AppMedicationTable> lss = new ArrayList<AppMedicationTable>();
            if(list!=null&&list.size()>0){
                for(AppMedicationTable ls:list){
                    ls.setVisitId(table.getId());
                    sysDao.getServiceDo().add(ls);
                    lss.add(ls);
                }
            }
            qvo.setSaveDiabetes(table);
            qvo.setUserMedicine(lss);
        }
        return qvo;
    }


    /**
     * 保存新生儿随访记录
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public AppSaveFollowQvo saveNewChild(AppSaveFollowQvo qvo) throws Exception {
        if(qvo.getSaveNewChild()!=null){
            AppChild table = new AppChild();
            CopyUtils.Copy(qvo.getSaveNewChild(),table);
            AppFollowPlan plan = (AppFollowPlan)sysDao.getServiceDo().find(AppFollowPlan.class,table.getChildVisitId());

            //判断随访计划的随访类型是否为空，不为空新增一条此类型的随访计划
            if(plan!=null){
//                table.setChildName(plan.getSfFollowPatientName());
                table.setChildCode(plan.getSfFollowNum());
                table.setChildVisitTime(ExtendDate.getYMD(plan.getSfFollowDate()));
                table.setChildVisitWay(plan.getSfFollowMode());
                table.setChildPatientId(plan.getSfFollowPatientid());
                Map<String,Object> map = new HashMap<String,Object>();
                map.put("patientId",plan.getSfFollowPatientid());
                map.put("type",ResidentMangeType.ETLZLS.getValue());
                map.put("state",FollowPlanState.YIWANCHENG.getValue());
                String sql = "SELECT count(1) FROM APP_FOLLOW_PLAN WHERE SF_FOLLOW_STATE =:state AND SF_FOLLOW_PATIENTID=:patientId AND SF_FOLLOW_TYPE =:type";
                int num = sysDao.getServiceDo().gteSqlCount(sql,map);
               /* if(StringUtils.isNotBlank(plan.getSfFollowType())){
                    AppFollowPlan pp = new AppFollowPlan();
                    CopyUtils.Copy(plan,pp);
                    pp.setId(null);
                    pp.setSfFollowType(ResidentMangeType.ETLZLS.getValue());
                    pp.setSfEndDate(Calendar.getInstance());
                    pp.setSfFollowState(FollowPlanState.YIWANCHENG.getValue());
                    pp.setSfReferral(table.getChildReferral());
                    pp.setSfReferralDept(table.getChildReferralDept());
                    pp.setSfReferralOrg(table.getChildReferralOrg());
                    pp.setSfReferralReason(table.getChildReferralReason());
                    pp.setSfYaxis(qvo.getSfYaxis());
                    pp.setSfXaxis(qvo.getSfXaxis());
                    pp.setSfTypeNum(String.valueOf(num+1));
                    pp.setNextTime(ExtendDate.getCalendar(table.getChildNextVisiTime()));
                    sysDao.getServiceDo().add(pp);
                    table.setChildVisitId(pp.getId());
                }else{//判断随访计划的随访类型是否为空，为空修改类型和转诊情况*/
               if(ResidentMangeType.ETLZLS.getValue().equals(plan.getSfFollowType())){
//                    plan.setSfFollowType(ResidentMangeType.ETLZLS.getValue());
                    plan.setSfEndDate(Calendar.getInstance());
                    plan.setSfFollowState(FollowPlanState.YIWANCHENG.getValue());
                    plan.setSfReferral(table.getChildReferral());
                    plan.setSfReferralDept(table.getChildReferralDept());
                    plan.setSfReferralOrg(table.getChildReferralOrg());
                    plan.setSfReferralReason(table.getChildReferralReason());
                    plan.setSfXaxis(qvo.getSfXaxis());
                    plan.setSfYaxis(qvo.getSfYaxis());
                    plan.setSfTypeNum(String.valueOf(num+1));
                    plan.setNextTime(ExtendDate.getCalendar(table.getChildNextVisiTime()));
                    sysDao.getServiceDo().modify(plan);
                }
                //新增下一次随访计划情况
                //判断下一次随访计划是否新增
                /*Map<String,Object> mapO = new HashMap<String,Object>();
                mapO.put("patientId",plan.getSfFollowPatientid());
                String sqll = "SELECT COUNT(1) FROM APP_FOLLOW_PLAN WHERE SF_FOLLOW_PATIENTID=:patientId AND SF_FOLLOW_DATE> NOW()";
                int numm = sysDao.getServiceDo().findCount(sqll,mapO);*/
                int numm = 0;
                if(numm == 0){//没有下一次随访计划
                    AppFollowPlan pl = new AppFollowPlan();
                    if(StringUtils.isNotBlank(plan.getSfFollowPatientid())) {
                        AppPatientUser patientUser = (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class, plan.getSfFollowPatientid());
                        pl.setSfFollowPatientid(patientUser.getId());
                        pl.setSfFollowPatientName(patientUser.getPatientName());
                    }
                    pl.setSfFollowType(ResidentMangeType.ETLZLS.getValue());
                    pl.setSfFollowDoctorid(plan.getSfFollowDoctorid());
                    pl.setSfFollowMode(plan.getSfFollowMode());
                    pl.setSfFollowState(FollowPlanState.WEIWANCHENG.getValue());//随访状态未开始
                    if("1".equals(table.getChildReferral())){
                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.DATE,14);
                        pl.setSfFollowDate(cal);
                    }else{
                        //后台计算正常情况下一次随访计划时间
//                    Calendar cal = Calendar.getInstance();
//                    cal.add(Calendar.MONTH,Integer.parseInt(PropertiesUtil.getConfValue("month")));
//                    pl.setSfFollowDate(cal);
                        if(StringUtils.isNotBlank(table.getChildNextVisiTime())){
                            pl.setSfFollowDate(ExtendDate.getCalendar(table.getChildNextVisiTime()));
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
                    AppDrUser drUser = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class,plan.getSfFollowDoctorid());
                    AppHospDept dept= (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                    if(dept!=null && dept.getHospAreaCode()!=null) {
                        AppSerial serial = sysDao.getAppSignformDao().getAppSerial(dept.getHospAreaCode().substring(0, 4), "follow");
                        if(serial!=null) {
                            Map<String,Object> bcnum = sysDao.getAppSignformDao().getNum(serial.getSerialNo(),SignFormType.APPSTATE.getValue());
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
                    pl.setSfReferral(table.getChildReferral());
                    pl.setSfReferralDept(table.getChildReferralDept());
                    pl.setSfReferralOrg(table.getChildReferralOrg());
                    pl.setSfReferralReason(table.getChildReferralReason());
                    sysDao.getServiceDo().add(pl);
                    table.setChildNextVisiTime(ExtendDate.getYMD(pl.getSfFollowDate()));
                }
//        签名图片
//        if(StringUtils.isNotBlank(table.getVisitDoctorName())){
//            String path = sysDao.getIoUtils().pathUrl(PropertiesUtil.getConfValue("filePicture"),PropertiesUtil.getConfValue("filePictureYz"),vo.getPatientImageName());
//            FileUtils.decoderBase64File(table.getVisitDoctorName(),PropertiesUtil.getConfValue("filePicture")+path);
//            table.setVisitDoctorName(path);
//
//        }
                sysDao.getServiceDo().add(table);

                //修改工作计划的完成状态
                List<AppWorkingPlan> lisWp = sysDao.getServiceDo().loadByPk(AppWorkingPlan.class,"planForeignId",table.getChildVisitId());
                if(lisWp!=null && lisWp.size()>0){
                    AppWorkingPlan wp = lisWp.get(0);
                    wp.setPlanFinishDate(Calendar.getInstance());
                    wp.setPlanState(CommonWorkPlanState.YWC.getValue());
                    this.sysDao.getServiceDo().modify(plan);
                }

                //履约数据
                PerformanceDataQvo qqvo = new PerformanceDataQvo();
                AppPatientUser user = (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,table.getChildPatientId());
                AppDrUser drUser = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class,plan.getSfFollowDoctorid());
                if(user!=null){
                    AppSignForm form= sysDao.getAppSignformDao().getSignFormUserId(user.getId());
                    if(form != null){
                        qqvo.setPerName(user.getPatientName());
                        qqvo.setPerIdno(user.getPatientIdno());
                        qqvo.setPerType(PerformanceType.DQSF.getValue());
//                    qqvo.setPerFollowType(ResidentMangeType.ETLZLS.getValue());//儿童
                        qqvo.setPerFollowType("1");//新生儿访视
                        qqvo.setPerFollowNextDate(table.getChildNextVisiTime());
                        qqvo.setPerForeign(table.getId());
                        qqvo.setPerSource("2");
                        if(StringUtils.isNotBlank(user.getPatientCity())){
                            CdAddress p = sysDao.getCdAddressDao().findByCode(user.getPatientCity());
                            if(p != null){
                                String code = AreaUtils.getAreaCode(p.getCtcode(),p.getLevel());
                                CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE,code);
                                if(value != null) {
                                    qqvo.setPerArea(value.getCodeTitle());
                                }
                            }
                        }
                        if(drUser != null){
                            qqvo.setDrName(drUser.getDrName());
                            qqvo.setDrAccount(drUser.getDrAccount());
                            qqvo.setDrPwd(drUser.getDrPwd());
                            qqvo.setDrGender(drUser.getDrGender());
                            qqvo.setDrTel(drUser.getDrTel());
                            qqvo.setDrId(drUser.getId());
                            AppHospDept dept= (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                            if(dept!=null){
                                qqvo.setHospId(dept.getId());
                                qqvo.setHospName(dept.getHospName());
                                qqvo.setHospAreaCode(dept.getHospAreaCode());
                                qqvo.setHospAddress(dept.getHospAddress());
                                qqvo.setHospTel(dept.getHospTel());
                            }
                        }
                        if(StringUtils.isNotBlank(qqvo.getPerArea())){
                            if(StringUtils.isNotBlank(qqvo.getPerType())){
//                        String result = null;
//                        if (qqvo.getPerArea().equals(AddressType.FZ.getValue())) {
//
//                        } else if (qqvo.getPerArea().equals(AddressType.QZ.getValue())) {
//                            result = "qz_";
//                        } else if (qqvo.getPerArea().equals(AddressType.ZZ.getValue())) {
//                            result = "zz_";
//                        } else if (qqvo.getPerArea().equals(AddressType.PT.getValue())) {
//                            result = "pt_";
//                        } else if (qqvo.getPerArea().equals(AddressType.NP.getValue())) {
//                            result = "np_";
//                        } else if (qqvo.getPerArea().equals(AddressType.SM.getValue())) {
//                            result = "sm_";
//                        }
//                        if(StringUtils.isNotBlank(result)){
//                            qqvo.setDrId(result+qqvo.getDrId());
//                            qqvo.setHospId(result+qqvo.getHospId());
//                        }
                                String fwType = "";
                                String sermeal = "";//服务包id
                                fwType = sysDao.getAppLabelGroupDao().findFwValue(form.getId());
                                sermeal = form.getSignpackageid();
                                sysDao.getAppPerformanceStatisticsDao().addPerformanceData(qqvo,sermeal,fwType);

                            }
                        }

                    }

                }

                List<AppMedicationTable> list = qvo.getUserMedicine();
                List<AppMedicationTable> lss = new ArrayList<AppMedicationTable>();
                if(list!=null&&list.size()>0){
                    for(AppMedicationTable ls:list){
                        ls.setVisitId(table.getId());
                        sysDao.getServiceDo().add(ls);
                        lss.add(ls);
                    }
                }
                qvo.setSaveNewChild(table);
            }else {
                return null;
            }
            return qvo;


        }
        return null;
    }

    /**
     * 查询高血压记录数据
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public AppHdBlooPressureTableEntity findHd(String id) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("id",id);
        String sql = "SELECT " +
                "ID id,\n" +
                "VISIT_ID visitId,\n" +
                "NAME name,\n" +
                "CODE code,\n" +
                "FOLLOW_VISIT_TIME followVisitTime,\n" +
                "FOLLOW_VISIT_WAY followVisitWay,\n" +
                "SYMPTOMS symptoms,\n" +
                "SYMPTOMS_OTHER symptomsOther,\n" +
                "BLOOD_PRESSURE_ONE bloodPressureOne,\n" +
                "WEIGHT weight,\n" +
                "NEXT_WEIGHT nextWeight,\n" +
                "HEIGHT height,\n" +
                "BMI bmi,\n" +
                "HEART_RATE heartRate,\n" +
                "SIGNS_OTHER signsOther,\n" +
                "SOMKING_TO_DAY smokingToDay,\n" +
                "SOMKING_NEXTTO_DAY smokingNextToDay,\n" +
                "DRINKING_TO_DAY drinkingToDay,\n" +
                "DRINKING_NEXTTO_DAY drinkingNextToDay,\n" +
                "ACTIVITY_TO_WEEK activityToWeek,\n" +
                "ACTIVITY_TO_TIME activityToTime,\n" +
                "ACTIVITY_NEXTTO_WEEK activityNextToWeek,\n" +
                "ACTIVITY_NEXTTO_TIME activityNextToTime,\n" +
                "SALT_SITUATION saltSituation,\n" +
                "SALT_NEXT_SITUATION saltNextSituation,\n" +
                "FOLLOWING_BEHAVIOR followingBehavior,\n" +
                "FZ_CHECK fzCheck,\n" +
                "MEDICATION_ADHERENCE medicationAdherence,\n" +
                "DRUG_REACTION drugReaction,\n" +
                "DRUG_REACTION_CONTENT drugReactionContent,\n" +
                "VISIT_THIS_TYPE visitThisType,\n" +
                "REFERRAL referral,\n" +
                "REFERRAL_REASON referralReason,\n" +
                "NEXT_VISI_TIME nextVisiTime,\n" +
                "VISIT_DOCTOR_NAME visitDoctorName,\n" +
                "BLOOD_PRESSURE_TWO bloodPressureTwo,\n" +
                "VISIT_SITUATION visitSituation,\n" +
                "VISIT_REASON visitReason,\n" +
                "DIE_DATE dieDate,\n" +
                "REFERRAL_DEPT referralDept,\n" +
                "REFERRAL_ORG referralOrg," +
                "PATIENT_ID patientId," +
                "'' sfrq," +
                "'' csrq," +
                "'' sex," +
                "'' tel," +
                "'' addr," +
                "MENTALITY_ADJUST mentalityAdjust,\n" +
                "'' userMedicine \n" +
                "FROM APP_HDBLOOPRESSURE_TABLE " +
                "WHERE 1=1 AND ID =:id";
        List<AppHdBlooPressureTableEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppHdBlooPressureTableEntity.class);
        if(ls!=null&&ls.size()>0){
            return ls.get(0);
        }
        return null;
    }

    /**
     * 查询糖尿病记录数据
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public AppDiabetesTableEntity findDiabetes(String id) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("id",id);
        String sql = "SELECT " +
                "ID id,\n" +
                "VISIT_ID visitId,\n" +
                "NAME name,\n" +
                "CODE code,\n" +
                "FOLLOW_VISIT_TIME followVisitTime,\n" +
                "FOLLOW_VISIT_WAY followVisitWay,\n" +
                "SYMPTOMS symptoms,\n" +
                "SYMPTOMS_OTHER symptomsOther,\n" +
                "BLOOD_PRESSURE_ONE bloodPressureOne,\n" +
                "WEIGHT weight,\n" +
                "BMI bmi,\n" +
                "DORSALIS_PEDIS_PULSE dorsalisPedisPulse,\n" +
                "SIGNS_OTHER signsOther,\n" +
                "SOMKING_TO_DAY smokingToDay,\n" +
                "DRINKING_TO_DAY drinkingToDay,\n" +
                "ACTIVITY_TO_WEEK activityToWeek,\n" +
                "ACTIVITY_TO_TIME activityToTime,\n" +
                "FOOD food,\n" +
                "MENTALITY_ADJUST mentalityAdjust,\n" +
                "FOLLOWING_BEHAVIOR followingBehavior,\n" +
                "FASTING_BLOOD_SUGAR fastingBloodSugar,\n" +
                "OTHER_CHECK otherCheck,\n" +
                "TH_HEMOGLOBIN thHemoglobin,\n" +
                "FZ_CHECK_DATE fzCheckDate,\n" +
                "MEDICATION_ADHERENCE medicationAdherence,\n" +
                "DRUG_REACTION drugReaction,\n" +
                "LOW_BLOOD_BLUCOSE lowBloodGlucose,\n" +
                "VISIT_THIS_TYPE visitThisType,\n" +
                "INSULIN insulin,\n" +
                "USE_INSULIN userInsulin,\n" +
                "REFERRAL_REASON referralReason,\n" +
                "REFERRAL_ORG referralOrg,\n" +
                "NEXT_VISIT_TIME nextVisiTime,\n" +
                "VISIT_DOCTOR_NAME visitDoctorName,\n" +
                "BLOOD_PRESSURE_TWO bloodPressureTwo,\n" +
                "ACTIVITY_NEXTTO_TIME activityNextToTime,\n" +
                "ACTIVITY_NEXTTO_WEEK activityNextToWeek,\n" +
                "DORSALIS_PEDIS_VALUE dorsalisPedisValue,\n" +
                "DRINKING_NEXTTO_DAY drinkingNextToDay,\n" +
                "HEIGHT height,\n" +
                "NEXT_FOOD nextFood,\n" +
                "NEXT_WEIGHT nextWeight,\n" +
                "REFERRAL referral,\n" +
                "REFERRAL_DEPT referralDept,\n" +
                "SOMKING_NEXTTO_DAY smokingNextToDay,\n" +
                "DIE_DATE dieDate,\n" +
                "VISIT_REASON visitReason,\n" +
                "VISIT_SITUATION visitSituation," +
                "PATIENT_ID patientId," +
                "'' sfrq," +
                "'' csrq," +
                "'' sex," +
                "'' tel," +
                "'' addr," +
                "'' userMedicine  " +
                "FROM APP_DIABETES_TABLE " +
                "WHERE ID = :id";
        List<AppDiabetesTableEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppDiabetesTableEntity.class);
        if(ls!=null&&ls.size()>0){
            return ls.get(0);
        }
        return null;
    }

    /**
     * 保存产后访视记录表
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public AppSaveFollowQvo savePostPar(AppSaveFollowQvo qvo) throws Exception {
        if(qvo.getPostPar()!=null){
            AppPostpartumVisit table = new AppPostpartumVisit();
            CopyUtils.Copy(qvo.getPostPar(),table);
            AppFollowPlan plan = (AppFollowPlan)sysDao.getServiceDo().find(AppFollowPlan.class,table.getPostVisitId());
            //判断随访计划的随访类型是否为空，不为空新增一条此类型的随访计划
            if(plan!=null){
//              table.setChildName(plan.getSfFollowPatientName());
                table.setPostCode(plan.getSfFollowNum());
                table.setPostVistDate(ExtendDate.getYMD(plan.getSfFollowDate()));
                table.setPostVisitWay(plan.getSfFollowMode());
                table.setPostPatientId(plan.getSfFollowPatientid());
                if(StringUtils.isBlank(table.getPostName())){
                    table.setPostName(plan.getSfFollowPatientName());
                }

                Map<String,Object> map = new HashMap<String,Object>();
                map.put("patientId",plan.getSfFollowPatientid());
                map.put("type",ResidentMangeType.YCF.getValue());
                map.put("state",FollowPlanState.YIWANCHENG.getValue());
                String sql = "SELECT count(1) FROM APP_FOLLOW_PLAN WHERE SF_FOLLOW_STATE =:state AND SF_FOLLOW_PATIENTID=:patientId AND SF_FOLLOW_TYPE =:type";
                int num = sysDao.getServiceDo().gteSqlCount(sql,map);
                /*if(StringUtils.isNotBlank(plan.getSfFollowType())){
                    AppFollowPlan pp = new AppFollowPlan();
                    CopyUtils.Copy(plan,pp);
                    pp.setId(null);
                    pp.setSfFollowType(ResidentMangeType.YCF.getValue());
                    pp.setSfEndDate(Calendar.getInstance());
                    pp.setSfFollowState(FollowPlanState.YIWANCHENG.getValue());
                    pp.setSfReferral(table.getPostReferral());
                    pp.setSfReferralDept(table.getPostReferralDept());
                    pp.setSfReferralOrg(table.getPostReferralOrg());
                    pp.setSfReferralReason(table.getPostReferralReason());
                    pp.setSfYaxis(qvo.getSfYaxis());
                    pp.setSfXaxis(qvo.getSfXaxis());
                    pp.setSfTypeNum(String.valueOf(num+1));
                    pp.setNextTime(ExtendDate.getCalendar(table.getPostNextVisitDate()));
                    sysDao.getServiceDo().add(pp);
                    table.setPostVisitId(pp.getId());
                }else{//判断随访计划的随访类型是否为空，为空修改类型和转诊情况*/
                if(ResidentMangeType.YCF.getValue().equals(plan.getSfFollowType())){
//                    plan.setSfFollowType(ResidentMangeType.YCF.getValue());
                    plan.setSfEndDate(Calendar.getInstance());
                    plan.setSfFollowState(FollowPlanState.YIWANCHENG.getValue());
                    plan.setSfReferral(table.getPostReferral());
                    plan.setSfReferralDept(table.getPostReferralDept());
                    plan.setSfReferralOrg(table.getPostReferralOrg());
                    plan.setSfReferralReason(table.getPostReferralReason());
                    plan.setSfXaxis(qvo.getSfXaxis());
                    plan.setSfYaxis(qvo.getSfYaxis());
                    plan.setSfTypeNum(String.valueOf(num+1));
                    plan.setNextTime(ExtendDate.getCalendar(table.getPostNextVisitDate()));
                    sysDao.getServiceDo().modify(plan);
                }
                //新增下一次随访计划情况
                //判断下一次随访计划是否新增
               /* Map<String,Object> mapO = new HashMap<String,Object>();
                mapO.put("patientId",plan.getSfFollowPatientid());
                String sqll = "SELECT COUNT(1) FROM APP_FOLLOW_PLAN WHERE SF_FOLLOW_PATIENTID=:patientId AND SF_FOLLOW_DATE> NOW()";
                int numm = sysDao.getServiceDo().findCount(sqll,mapO);*/
               int numm = 0;
                if(numm == 0){//没有下一次随访计划
                    AppFollowPlan pl = new AppFollowPlan();
                    if(StringUtils.isNotBlank(plan.getSfFollowPatientid())) {
                        AppPatientUser patientUser = (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class, plan.getSfFollowPatientid());
                        pl.setSfFollowPatientid(patientUser.getId());
                        pl.setSfFollowPatientName(patientUser.getPatientName());
                    }
                    pl.setSfFollowType(ResidentMangeType.YCF.getValue());
                    pl.setSfFollowDoctorid(plan.getSfFollowDoctorid());
                    pl.setSfFollowMode(plan.getSfFollowMode());
                    pl.setSfFollowState(FollowPlanState.WEIWANCHENG.getValue());//随访状态未开始
                    if("1".equals(table.getPostReferral())){
                        Calendar cal = Calendar.getInstance();
                        cal.add(Calendar.DATE,14);
                        pl.setSfFollowDate(cal);
                    }else{
                        //后台计算正常情况下一次随访计划时间
//                    Calendar cal = Calendar.getInstance();
//                    cal.add(Calendar.MONTH,Integer.parseInt(PropertiesUtil.getConfValue("month")));
//                    pl.setSfFollowDate(cal);
                        if(StringUtils.isNotBlank(table.getPostNextVisitDate())){
                            pl.setSfFollowDate(ExtendDate.getCalendar(table.getPostNextVisitDate()));
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
                    AppDrUser drUser = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class,plan.getSfFollowDoctorid());
                    AppHospDept dept= (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                    if(dept!=null && dept.getHospAreaCode()!=null) {
                        AppSerial serial = sysDao.getAppSignformDao().getAppSerial(dept.getHospAreaCode().substring(0, 4), "follow");
                        if(serial!=null) {
                            Map<String,Object> bcnum = sysDao.getAppSignformDao().getNum(serial.getSerialNo(),SignFormType.APPSTATE.getValue());
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
                    pl.setSfReferral(table.getPostReferral());
                    pl.setSfReferralDept(table.getPostReferralDept());
                    pl.setSfReferralOrg(table.getPostReferralOrg());
                    pl.setSfReferralReason(table.getPostReferralReason());
                    sysDao.getServiceDo().add(pl);
                    table.setPostNextVisitDate(ExtendDate.getYMD(pl.getSfFollowDate()));
                }
//        签名图片
//        if(StringUtils.isNotBlank(table.getVisitDoctorName())){
//            String path = sysDao.getIoUtils().pathUrl(PropertiesUtil.getConfValue("filePicture"),PropertiesUtil.getConfValue("filePictureYz"),vo.getPatientImageName());
//            FileUtils.decoderBase64File(table.getVisitDoctorName(),PropertiesUtil.getConfValue("filePicture")+path);
//            table.setVisitDoctorName(path);
//
//        }
                sysDao.getServiceDo().add(table);

                //修改工作计划的完成状态
                List<AppWorkingPlan> lisWp = sysDao.getServiceDo().loadByPk(AppWorkingPlan.class,"planForeignId",table.getPostVisitId());
                if(lisWp!=null && lisWp.size()>0){
                    AppWorkingPlan wp = lisWp.get(0);
                    wp.setPlanFinishDate(Calendar.getInstance());
                    wp.setPlanState(CommonWorkPlanState.YWC.getValue());
                    this.sysDao.getServiceDo().modify(plan);
                }

                //履约数据
                PerformanceDataQvo qqvo = new PerformanceDataQvo();
                AppPatientUser user = (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,table.getPostPatientId());
                AppDrUser drUser = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class,plan.getSfFollowDoctorid());
                if(user!=null){
                    AppSignForm form = sysDao.getAppSignformDao().getSignFormUserId(plan.getSfFollowPatientid());
                    if(form != null){
                        qqvo.setPerName(user.getPatientName());
                        qqvo.setPerIdno(user.getPatientIdno());
                        qqvo.setPerType(PerformanceType.DQSF.getValue());
                        qqvo.setPerFollowType(ResidentMangeType.YCF.getValue());//孕产妇
                        qqvo.setPerFollowNextDate(table.getPostNextVisitDate());
                        qqvo.setPerForeign(table.getId());
                        qqvo.setPerSource("2");
                        if(StringUtils.isNotBlank(user.getPatientCity())){
                            CdAddress p = sysDao.getCdAddressDao().findByCode(user.getPatientCity());
                            if(p != null){
                                String code = AreaUtils.getAreaCode(p.getCtcode(),p.getLevel());
                                CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE,code);
                                if(value != null) {
                                    qqvo.setPerArea(value.getCodeTitle());
                                }
                            }
                        }
                        if(drUser != null){
                            qqvo.setDrName(drUser.getDrName());
                            qqvo.setDrAccount(drUser.getDrAccount());
                            qqvo.setDrPwd(drUser.getDrPwd());
                            qqvo.setDrGender(drUser.getDrGender());
                            qqvo.setDrTel(drUser.getDrTel());
                            qqvo.setDrId(drUser.getId());
                            AppHospDept dept= (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                            if(dept!=null){
                                qqvo.setHospId(dept.getId());
                                qqvo.setHospName(dept.getHospName());
                                qqvo.setHospAreaCode(dept.getHospAreaCode());
                                qqvo.setHospAddress(dept.getHospAddress());
                                qqvo.setHospTel(dept.getHospTel());
                            }
                        }
                        if(StringUtils.isNotBlank(qqvo.getPerArea())){
                            if(StringUtils.isNotBlank(qqvo.getPerType())){
//                        String result = null;
//                        if (qqvo.getPerArea().equals(AddressType.FZ.getValue())) {
//
//                        } else if (qqvo.getPerArea().equals(AddressType.QZ.getValue())) {
//                            result = "qz_";
//                        } else if (qqvo.getPerArea().equals(AddressType.ZZ.getValue())) {
//                            result = "zz_";
//                        } else if (qqvo.getPerArea().equals(AddressType.PT.getValue())) {
//                            result = "pt_";
//                        } else if (qqvo.getPerArea().equals(AddressType.NP.getValue())) {
//                            result = "np_";
//                        } else if (qqvo.getPerArea().equals(AddressType.SM.getValue())) {
//                            result = "sm_";
//                        }
//                        if(StringUtils.isNotBlank(result)){
//                            qqvo.setDrId(result+qqvo.getDrId());
//                            qqvo.setHospId(result+qqvo.getHospId());
//                        }
                                String fwType = "";
                                String sermeal = "";//服务包id
                                fwType = sysDao.getAppLabelGroupDao().findFwValue(form.getId());
                                sermeal = form.getSignpackageid();
                                sysDao.getAppPerformanceStatisticsDao().addPerformanceData(qqvo,sermeal,fwType);
                            }
                        }

                    }

                }


                qvo.setPostPar(table);
            }
            return qvo;
        }
        return null;

    }

    /**
     * 查询新生儿家庭访视记录
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public AppNewChildrenTableEntity findNewChild(String id) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT ID id," +
                "CHILD_VISIT_ID childVisitId,"+
                "CHILD_AGE_GROUP childAgeGroup,"+
                "CHILD_PATIENT_ID childPatientId,"+
                "CHILD_NAME childName,"+
                "CHILD_CODE childCode,"+
                "CHILD_GENDER childGender,"+
                "CHILD_BIRTH_DAY childBirthDay,"+
                "CHILD_IDNO childIdNo,"+
                "CHILD_ADDRESS childAddress,"+
                "CHILD_FATHER_NAME childFatherName,"+
                "CHILD_FATHER_OCCUPATION childFatherOccupation,"+
                "CHILD_FATHER_TEL childFatherTel,"+
                "CHILD_FATHER_BIRTH childFatherBirth,"+
                "CHILD_MOTHER_NAME childMotherName,"+
                "CHILD_MOTHER_OCCUPATION childMotherOccupation,"+
                "CHILD_MOTHER_TEL childMotherTel,"+
                "CHILD_MOTHER_BIRTH childMotherBirth,"+
                "CHILD_GESTATIONAL_WEEKS childGestationalWeeks,"+
                "CHILD_MOTHER_SITUATION childMotherSituation,"+
                "CHILD_ACCOUCHE_ORG_ID childAccoucheOrgId,"+
                "CHILD_ACCOUCHE_ORG childAccoucheOrg,"+
                "CHILD_BIRTH_SITUATION childBirthSituation,"+
                "CHILD_BIRTH_SITUATION_OTHER childBirthSituationOther,"+
                "CHILD_ASPHYXIA childAsphyxia,"+
                "CHILD_DEFORMITY childDeformity,"+
                "CHILD_DEFORMITY_CONTENT childDeformityContent,"+
                "CHILD_HEARING_SCREENING childHearingScreening,"+
                "CHILD_DISEASE_SCREENING childDiseaseScreening,"+
                "CHILD_BIRTH_WEIGHT childBirthWeight,"+
                "CHILD_WEIGHT childWeight,"+
                "CHILD_BIRTH_HEIGHT childBirthHeight,"+
                "CHILD_FEEDING_WAY childFeedingWay,"+
                "CHILD_EAT_MILK childEatMilk,"+
                "CHILD_EAT_MILK_NUM childEatMilkNum,"+
                "CHILD_VOMITING childVomiting,"+
                "CHILD_SHIT childShit,"+
                "CHILD_SHIT_NUM childShitNum,"+
                "CHILD_ANIMAL_HEAT childAnimalHeat,"+
                "CHILD_HEART_RATE childHeartRate,"+
                "CHILD_BREATHING_RATE childBreathingRate,"+
                "CHILD_FACE childFace,"+
                "CHILD_FACE_OTHER childFaceOther,"+
                "CHILD_JAUNDICE_PARTS childJaundiceParts,"+
                "CHILD_BREGMA_ONE childBregmaONE,"+
                "CHILD_BREGMA_TWO childBregmaTWO,"+
                "CHILD_BREGMA childBregma,"+
                "CHILD_BREGMA_OTHER childBregmaOther,"+
                "CHILD_EYES childEyes,"+
                "CHILD_LIMBS_ACTIVITY childLimbsActivity,"+
                "CHILD_EAR_APPEARANCE childEarAppearance,"+
                "CHILD_NECK_BAG_PIECE childNeckBagPiece,"+
                "CHILD_NOSE childNose,"+
                "CHILD_SKIN childSkin,"+
                "CHILD_ORAL childOral,"+
                "CHILD_ANUS childAnus,"+
                "CHILD_LUNG_AUSCULTATION childLungAuscultation,"+
                "CHILD_CHEST childChest,"+
                "CHILD_ABDOMINAL_TOUCH childAbdominalTouch,"+
                "CHILD_SPINE childSpine,"+
                "CHILD_GENITALS childGenitals,"+
                "CHILD_UMBILICAL_CORD childUmbilicalCord,"+
                "CHILD_UMBILICAL_CORD_OTHER childUmbilicalCordOther,"+
                "CHILD_REFERRAL childReferral,"+
                "CHILD_REFERRAL_REASON childReferralReason,"+
                "CHILD_REFERRAL_ORG childReferralOrg,"+
                "CHILD_GUIDE childGuide,"+
                "CHILD_GUIDE_OTHER childGuideOther,"+
                "CHILD_VISIT_TIME childVisitTime,"+
                "CHILD_NEXT_VISIT_ADDRESS childNextVisitAddress,"+
                "CHILD_NEXT_VISTI_TIME childNextVisiTime,"+
                "CHILD_VISIT_DOCTOR_Id childVisitDoctorId,"+
                "CHILD_REFERRAL_DEPT childReferralDept,"+
                "CHILD_VISIT_DOCTOR_IMAGE childVisitDoctorImage,"+
                "CHILD_VISIT_WAY childVisitWay "+
                "FROM APP_CHILD WHERE 1=1";
        map.put("id",id);
        sql +=" AND ID=:id";
        List<AppNewChildrenTableEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppNewChildrenTableEntity.class);
        if(ls!=null&&ls.size()>0){
            return ls.get(0);
        }
        return null;
    }

    /**
     * 查询产后访视记录
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public AppPostpartumVisitEntity findPostPar(String id) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT ID id," +
                "POST_VISIT_ID postVisitId,"+
                "POST_PATIENT_ID postPatientId,"+
                "POST_NAME postName,"+
                "POST_CODE postCode,"+
                "POST_VISIT_DATE postVistDate,"+
                "POST_CHILDBIRTH_DATE postChildbirthDate,"+
                "POST_LEAVE_HOSPITAL_DATE postLeveHospitalDate,"+
                "POST_ANIMAL_HEAT postAnimalHeat,"+
                "POST_HEALTH_SITUATION postHealthSituation,"+
                "POST_PSYCHOLOGIC_STATUS postpSychologicStatus,"+
                "POST_BLOOD_PRESSURE_ONE postBloodPressureOne,"+
                "POST_BLOOD_PRESSURE_TWO postBloodPressureTwo,"+
                "POST_BREAST postBreast,"+
                "POST_BREAST_CONTENT postBreastContent,"+
                "POST_LOCHIA postLochia,"+
                "POST_LOCHIA_CONTENT postLochiaContent,"+
                "POST_UTERUS postUterus,"+
                "POST_UTERUS_CONTENT postUterusContent,"+
                "POST_WOUND postWound,"+
                "POST_WOUND_CONTENT postWoundContent,"+
                "POST_OTHER_CONTENT postOtherContent,"+
                "POST_CLASSIFY postClassify,"+
                "POST_CLASSIFY_CONTENT postClassifyContent,"+
                "POST_GUIDE postGuide,"+
                "POST_GUIDE_CONTENT postGuideContent,"+
                "POST_REFERRAL postReferral,"+
                "POST_REFERRAL_REASON postReferralReason,"+
                "POST_REFERRAL_ORG postReferralOrg,"+
                "POST_REFERRAL_DEPT postReferralDept,"+
                "POST_NEXT_VISIT_DATE postNextVisitDate,"+
                "POST_DOCTOR_ID postDoctorId,"+
                "POST_DOCTOR_IMAGE postDoctorImage,"+
                "POST_VISIT_WAY postVisitWay "+
                "FROM APP_POSTPARTUM_VISIT WHERE 1=1";
        map.put("id",id);
        sql += " AND ID=:id";
        List<AppPostpartumVisitEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppPostpartumVisitEntity.class);
        if(ls!=null&&ls.size()>0){
            return ls.get(0);
        }
        return null;
    }

    /**
     * 保存通用随访信息
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public AppGeneralQvo saveGeneral(AppGeneralQvo qvo) throws Exception {
        AppGeneralTable table = new AppGeneralTable();
        AppFollowPlan plan = (AppFollowPlan)sysDao.getServiceDo().find(AppFollowPlan.class,qvo.getGenVisitId());
        PerformanceDataQvo qqvo = new PerformanceDataQvo();
        if(plan!=null){
            table.setGenPatientId(plan.getSfFollowPatientid());//患者id
            qvo.setGenPatientId(plan.getSfFollowPatientid());
            table.setGenVisitId(qvo.getGenVisitId());//随访计划外键
            table.setGenDrId(qvo.getGenDrId());//随访医生id
            table.setGenName(plan.getSfFollowPatientName());//患者姓名
            qqvo.setPerName(plan.getSfFollowPatientName());
            qvo.setGenName(plan.getSfFollowPatientName());
            table.setGenCode(plan.getSfFollowNum());//编号
            qvo.setGenCode(plan.getSfFollowNum());
            table.setGenFollowDate(plan.getSfFollowDate());//随访日期
            qvo.setGenFollowDate(ExtendDate.getYMD(plan.getSfFollowDate()));
            table.setGenVisitWay(plan.getSfFollowMode());//随访方式
            qvo.setGenVisitWay(plan.getSfFollowMode());

            if(StringUtils.isNotBlank(qvo.getGenDrId())){
                AppDrUser drUser = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,qvo.getGenDrId());
                if(drUser != null){
                    qqvo.setDrId(drUser.getId());
                    qqvo.setDrName(drUser.getDrName());
                    if(StringUtils.isNotBlank(drUser.getDrHospId())){
                        AppHospDept hospDept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                        if(hospDept != null){
                            qqvo.setHospId(hospDept.getId());
                            qqvo.setHospName(hospDept.getHospName());
                            qqvo.setHospAreaCode(hospDept.getHospAreaCode());
                        }
                    }
                }
            }
            //添加随访计划数据
            plan.setSfEndDate(Calendar.getInstance());//完成日期
            plan.setSfFollowState(FollowPlanState.YIWANCHENG.getValue());//随访状态
            plan.setSfXaxis(qvo.getSfXaxis());
            plan.setSfYaxis(qvo.getSfYaxis());
            //查询第几次随访
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("patientId",plan.getSfFollowPatientid());
            map.put("type",ResidentMangeType.TY.getValue());
            map.put("state",FollowPlanState.YIWANCHENG.getValue());
            String sql = "SELECT count(1) FROM APP_FOLLOW_PLAN WHERE SF_FOLLOW_STATE = :state AND SF_FOLLOW_PATIENTID=:patientId AND SF_FOLLOW_TYPE =:type ";
            int num = sysDao.getServiceDo().gteSqlCount(sql,map);
            plan.setSfTypeNum(String.valueOf(num+1));
            plan.setSfFollowType(ResidentMangeType.TY.getValue());//随访类型
            //查询患者信息
            AppPatientUser user = sysDao.getAppPatientUserDao().findByUserId(plan.getSfFollowPatientid());
            if(user!=null){
                table.setGenBirthDate(user.getPatientBirthday());//出生日期
                qvo.setGenBirthDate(ExtendDate.getYMD(user.getPatientBirthday()));
                table.setGenGender(user.getPatientGender());//性别
                qvo.setGenGender(user.getPatientGender());
                table.setGenIdno(user.getPatientIdno());//身份证号
                qvo.setGenIdno(user.getPatientIdno());
                qqvo.setPerIdno(user.getPatientIdno());
            }else{
                throw new RuntimeException("查无人员信息");
            }
            table.setGenWorkDept(qvo.getGenWorkDept());//工作岗位
            table.setGenPhoneNum(qvo.getGenPhoneNum());//本人电话
            table.setGenLxrName(qvo.getGenLxrName());//联系人姓名
            table.setGenLxrPhone(qvo.getGenLxrPhone());//联系人电话
            table.setGenPermanentType(qvo.getGenPermanentType());//常住类型
            table.setGenNational(qvo.getGenNational());//民族
            table.setGetGenNationalName(qvo.getGetGenNationalName());//民族名称(少数民族)
            table.setGenBloodType(qvo.getGenBloodType());//血型
            table.setGenRhValue(qvo.getGenRhValue());//RH值
            table.setGenEducationLevel(qvo.getGenEducationLevel());//文化程度
            table.setGenProfessional(qvo.getGenProfessional());//职业
            table.setGenMaritalStatus(qvo.getGenMaritalStatus());//婚姻状况
            table.setGenMedicalPayType(qvo.getGenMedicalPayType());//医疗费用支付方式
            table.setGenMedicalOtherPayType(qvo.getGenMedicalOtherPayType());//其他医疗费用支付方式
            table.setGenDrugAllergyHistory(qvo.getGenDrugAllergyHistory());//药物过敏史
            table.setGenDrugAllergyOtherHistory(qvo.getGenDrugAllergyOtherHistory());//其他药物过敏史
            table.setGenExposedHistory(qvo.getGenExposedHistory());//暴露史
            table.setGenSurgery(qvo.getGenSurgery());//手术
            table.setGenTrauma(qvo.getGenTrauma());//外伤
            table.setGenBloodTransfusion(qvo.getGenBloodTransfusion());//输血
            table.setGenFatherHistory(qvo.getGenFatherHistory());//父亲家族史
            table.setGenFatherOtherHistory(qvo.getGenFatherOtherHistory());//父亲其他家族史
            table.setGenMotherHistory(qvo.getGenMotherHistory());//母亲家族史
            table.setGenMotherOtherHistory(qvo.getGenMotherOtherHistory());////母亲其他家族史
            table.setGenPeersHistory(qvo.getGenPeersHistory());//兄弟姐妹家族史
            table.setGenPeersOtherHistory(qvo.getGenPeersOtherHistory());//兄弟姐妹其他家族史
            table.setGenChildrenHisTory(qvo.getGenChildrenHisTory());//子女家族史
            table.setGenChildrenOtherHistory(qvo.getGenChildrenOtherHistory());//子女其他家族史
            table.setGenGeneticDisordersHistory(qvo.getGenGeneticDisordersHistory());//遗传病史
            table.setGenGeneticDisordersName(qvo.getGenGeneticDisordersName());//有遗传病史的疾病名称
            table.setGenDisability(qvo.getGenDisability());//残疾情况
            table.setGenKitchenSituation(qvo.getGenKitchenSituation());//厨房排风设施
            table.setGenFuelType(qvo.getGenFuelType());//燃料类型
            table.setGenToilet(qvo.getGenToilet());//厕所
            table.setGenLivestockBar(qvo.getGenLivestockBar());//禽畜栏
            sysDao.getServiceDo().modify(plan);
            sysDao.getServiceDo().add(table);
            //保存履约数据
            AppPerformanceTable per = new AppPerformanceTable();
            if(StringUtils.isBlank(qqvo.getServeDate())){
                qqvo.setServeDate(ExtendDate.getYMD(Calendar.getInstance()));
                per.setPerServeDate(Calendar.getInstance());
                per.setPerCreateDate(Calendar.getInstance());
            }else{
                per.setPerServeDate(ExtendDate.getCalendar(qqvo.getServeDate()));
                per.setPerCreateDate(ExtendDate.getCalendar(qqvo.getServeDate()));
            }
            String nowString = qqvo.getServeDate();
            Calendar now = ExtendDate.getCalendar(qqvo.getServeDate());

            String year = nowString.split("-")[0];
            String month = nowString.split("-")[1];
            String yearMonth = year + "-" + month;
            per.setPerYearMonth(yearMonth);
            per.setPerYear(year);
            per.setPerMonth(month);
            per.setPerAreaCode(qqvo.getHospAreaCode());
            per.setPerDrId(qqvo.getDrId());
            per.setPerDrName(qqvo.getDrName());
            per.setPerHospId(qqvo.getHospId());
            per.setPerPatientIdNo(qqvo.getPerIdno());
            per.setPerHospName(qqvo.getHospName());
            per.setPerPatientName(qqvo.getPerName());
            per.setPerSergValue(qqvo.getSergValue());
            per.setPerSermContent(qqvo.getSermContent());
            per.setPerSermValue(qqvo.getSermValue());
            per.setPerSerpkValue(qqvo.getSerpkValue());
            per.setPerServeNum(qqvo.getServeNum());
            per.setPerSource(qqvo.getPerSource());
            per.setPerTeamId(qqvo.getTeamId());
            per.setPerType(PerformanceType.GGFW.getValue());
            sysDao.getServiceDo().add(per);


            qvo.setId(table.getId());
            if(qvo.getDisHisList()!=null && qvo.getDisHisList().size()>0){
                for(AppGeneralDiseaseHisQvo ll:qvo.getDisHisList()){
                    AppGeneralDiseaseHisTable hisTable = new AppGeneralDiseaseHisTable();
                    hisTable.setGdhtGenId(table.getId());
                    hisTable.setGdhtType(ll.getGdhtType());
                    hisTable.setGdhtNameOrReason(ll.getGdhtNameOrReason());
                    hisTable.setGdhtTime(ExtendDate.getCalendar(ll.getGdhtTime()));
                    sysDao.getServiceDo().add(hisTable);
                    ll.setId(hisTable.getId());
                    ll.setGdhtGenId(table.getId());
                }
            }
            if(qvo.getDisList()!=null && qvo.getDisList().size()>0){
                for(AppGeneralDiseaseQvo ll:qvo.getDisList()){
                    AppGeneralDiseaseTable disTable = new AppGeneralDiseaseTable();
                    disTable.setGdtDiseaseTitle(ll.getGdtDiseaseTitle());
                    disTable.setGdtDiseaseValue(ll.getGdtDiseaseValue());
                    disTable.setGdtGenId(table.getId());
                    disTable.setGdtConfirmedDate(ExtendDate.getCalendar(ll.getGdtConfirmedDate()));
                    sysDao.getServiceDo().add(disTable);
                    ll.setId(disTable.getId());
                    ll.setGdtGenId(table.getId());
                }
            }
        }else{
            return null;
        }
        return qvo;
    }

    /**
     * 查询通用随访记录表
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public AppGeneralEntity findTy(String id) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("id",id);
        String sql = "SELECT " +
                "ID id," +
                "GEN_PATIENT_ID genPatientId,\n" +
                "GEN_DR_ID genDrId,\n" +
                "GEN_FOLLOW_DATE genFollowDate,\n" +
                "GEN_VISIT_ID genVisitId,\n" +
                "GEN_VISIT_WAY genVisitWay,\n" +
                "GEN_NAME genName,\n" +
                "GEN_CODE genCode,\n" +
                "GEN_GENDER genGender,\n" +
                "GEN_BIRTHDATE genBirthDate,\n" +
                "GEN_IDNO genIdno,\n" +
                "GEN_WORK_DEPT genWorkDept,\n" +
                "GEN_PHONE_NUM genPhoneNum,\n" +
                "GEN_LXR_NAME genLxrName,\n" +
                "GEN_LXR_PHONE genLxrPhone,\n" +
                "GEN_PERMANENT_TYPE genPermanentType,\n" +
                "GEN_NATIONAL genNational,\n" +
                "GEN_NATIONAL_NAME getGenNationalName,\n" +
                "GEN_BLOOD_TYPE genBloodType,\n" +
                "GEN_RH_VALUE genRhValue,\n" +
                "GEN_EDUCATION_LEVEL genEducationLevel,\n" +
                "GEN_PROFESSIONAL genProfessional,\n" +
                "GEN_MARITAL_STATUS genMaritalStatus,\n" +
                "GEN_MEDICAL_PAY_TYPE genMedicalPayType,\n" +
                "GEN_MEDICAL_OTHER_PAY_TYPE genMedicalOtherPayType,\n" +
                "GEN_DRUG_ALLERGY_HISTORY genDrugAllergyHistory,\n" +
                "GEN_DRUG_ALLERGY_OTHER_HISTORY genDrugAllergyOtherHistory,\n" +
                "GEN_EXPOSED_HISTORY genExposedHistory,\n" +
                "GEN_SURGERY genSurgery,\n" +
                "GEN_TRAUMA genTrauma,\n" +
                "GEN_BLOOD_TRANSFUSION genBloodTransfusion,\n" +
                "GEN_FATHER_HISTORY genFatherHistory,\n" +
                "GEN_FATHER_OTHER_HISTORY genFatherOtherHistory,\n" +
                "GEN_MOTHER_HISTORY genMotherHistory,\n" +
                "GEN_MOTHER_OTHER_HISTORY genMotherOtherHistory,\n" +
                "GEN_PEERS_HISTORY genPeersHistory,\n" +
                "GEN_PEERS_OTHER_HISTORY genPeersOtherHistory,\n" +
                "GEN_CHILDREN_HISTORY genChildrenHisTory,\n" +
                "GEN_CHILDREN_OTHER_HISTORY genChildrenOtherHistory,\n" +
                "GEN_GENETIC_DISORDERS_HISTORY genGeneticDisordersHistory,\n" +
                "GEN_GENETIC_DISORDERS_NAME genGeneticDisordersName,\n" +
                "GEN_DISABILITY genDisability,\n" +
                "GEN_KITCHEN_SITUATION genKitchenSituation,\n" +
                "GEN_FUEL_TYPE genFuelType,\n" +
                "GEN_TOILET genToilet,\n" +
                "GEN_LIVESTOCK_BAR genLivestockBar," +
                "'' disHisList," +
                "'' disList " +
                "FROM APP_GENERAL_TABLE " +
                "WHERE ID=:id";
        List<AppGeneralEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppGeneralEntity.class);
        if(ls!=null && ls.size()>0){
            return ls.get(0);
        }
        return null;
    }

    /**
     * 保存精神病随访数据
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public AppSaveFollowQvo saveMentalVisit(AppSaveFollowQvo qvo) throws Exception {
        AppMentalVisitTable table = new AppMentalVisitTable();
        CopyUtils.Copy(qvo.getSaveMentalVisit(),table);
        AppFollowPlan plan = (AppFollowPlan)sysDao.getServiceDo().find(AppFollowPlan.class,table.getVisitId());
        if(plan!=null){
            table.setName(plan.getSfFollowPatientName());
            table.setCode(plan.getSfFollowNum());
            table.setFollowVisitTime(ExtendDate.getYMD(plan.getSfFollowDate()));
            if(StringUtils.isBlank(table.getFollowVisitWay())){
                table.setFollowVisitWay(plan.getSfFollowMode());
            }else{
                plan.setSfFollowMode(table.getFollowVisitWay());
            }
            table.setPatientId(plan.getSfFollowPatientid());
            //查询第几次随访
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("patientId",plan.getSfFollowPatientid());
            map.put("type",ResidentMangeType.YZJSZY.getValue());
            map.put("state",FollowPlanState.YIWANCHENG.getValue());

            String sql = "SELECT count(1) FROM APP_FOLLOW_PLAN WHERE SF_FOLLOW_STATE = :state AND SF_FOLLOW_PATIENTID=:patientId AND SF_FOLLOW_TYPE =:type ";
            int num = sysDao.getServiceDo().gteSqlCount(sql,map);
            if(ResidentMangeType.YZJSZY.getValue().equals(plan.getSfFollowType())){
                plan.setSfFollowType(ResidentMangeType.YZJSZY.getValue());
                plan.setSfEndDate(Calendar.getInstance());
                plan.setSfFollowState(FollowPlanState.YIWANCHENG.getValue());
                plan.setSfReferral(table.getReferral());
                plan.setSfReferralDept(table.getReferralDept());
                plan.setSfReferralOrg(table.getReferralOrg());
                plan.setSfReferralReason(table.getReferralReason());
                plan.setSfXaxis(qvo.getSfXaxis());
                plan.setSfYaxis(qvo.getSfYaxis());
                plan.setSfTypeNum(String.valueOf(num+1));
                plan.setNextTime(ExtendDate.getCalendar(table.getNextVisiTime()));
                sysDao.getServiceDo().modify(plan);
            }
            //新增下一次随访计划情况
            //判断下一次随访计划是否新增
//            Map<String,Object> mapO = new HashMap<String,Object>();
//            mapO.put("patientId",plan.getSfFollowPatientid());
//            String sqll = "SELECT COUNT(1) FROM APP_FOLLOW_PLAN WHERE SF_FOLLOW_PATIENTID=:patientId AND SF_FOLLOW_DATE> NOW()";
//            int numm = sysDao.getServiceDo().findCount(sqll,mapO);
            int numm =0;
            if(numm == 0){//没有下一次随访计划，新增下一次随访计划
                AppFollowPlan pl = new AppFollowPlan();
                if(StringUtils.isNotBlank(plan.getSfFollowPatientid())) {
                    AppPatientUser patientUser = (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class, plan.getSfFollowPatientid());
                    pl.setSfFollowPatientid(patientUser.getId());//患者id
                    pl.setSfFollowPatientName(patientUser.getPatientName());//患者姓名
                }
                pl.setSfFollowType(ResidentMangeType.YZJSZY.getValue());
                pl.setSfFollowDoctorid(table.getVisitDoctorId());//下一次随访计划已此次随访医生建立
                pl.setSfFollowMode(plan.getSfFollowMode());
                pl.setSfFollowState(FollowPlanState.WEIWANCHENG.getValue());//随访状态未开始
                //有转诊情况、本次随访不稳定、药物不良反应
                if("1".equals(table.getReferral())||FollowThisType.BWD.getValue().equals(table.getFollowVisitType())||DrugState.YOU.getValue().equals(table.getDrugReaction())){
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DATE,14);
                    pl.setSfFollowDate(cal);
                }else{
                    //正常情况下新增下一次随访计划日期
//                    Calendar cal = Calendar.getInstance();
//                    cal.add(Calendar.MONTH,Integer.parseInt(PropertiesUtil.getConfValue("month")));
//                    pl.setSfFollowDate(cal);
                    if(StringUtils.isNotBlank(table.getNextVisiTime())){
                        pl.setSfFollowDate(ExtendDate.getCalendar(table.getNextVisiTime()));
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
                AppDrUser drUser = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class,plan.getSfFollowDoctorid());
                AppHospDept dept= (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                if(dept!=null && dept.getHospAreaCode()!=null) {
                    AppSerial serial = sysDao.getAppSignformDao().getAppSerial(dept.getHospAreaCode().substring(0, 4), "follow");
                    if(serial!=null) {
                        Map<String,Object> bcnum = sysDao.getAppSignformDao().getNum(serial.getSerialNo(),SignFormType.APPSTATE.getValue());
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
                pl.setSfReferral(table.getReferral());
                pl.setSfReferralDept(table.getReferralDept());
                pl.setSfReferralOrg(table.getReferralOrg());
                pl.setSfReferralReason(table.getReferralReason());
                sysDao.getServiceDo().add(pl);
                table.setNextVisiTime(ExtendDate.getYMD(pl.getSfFollowDate()));
            }
            sysDao.getServiceDo().add(table);

            //修改工作计划的完成状态
            List<AppWorkingPlan> lisWp = sysDao.getServiceDo().loadByPk(AppWorkingPlan.class,"planForeignId",table.getVisitId());
            if(lisWp!=null && lisWp.size()>0){
                AppWorkingPlan wp = lisWp.get(0);
                wp.setPlanFinishDate(Calendar.getInstance());
                wp.setPlanState(CommonWorkPlanState.YWC.getValue());
                this.sysDao.getServiceDo().modify(plan);
            }

            //履约数据
            PerformanceDataQvo qqvo = new PerformanceDataQvo();
            AppPatientUser user = (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,table.getPatientId());
            AppDrUser drUser = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class,plan.getSfFollowDoctorid());
            if(user!=null){
                qqvo.setPerName(user.getPatientName());
                qqvo.setPerIdno(user.getPatientIdno());
                qqvo.setPerType(PerformanceType.DQSF.getValue());
                qqvo.setPerFollowType(ResidentMangeType.YZJSZY.getValue());//严重精神病
                qqvo.setPerFollowNextDate(table.getNextVisiTime());
                qqvo.setPerForeign(table.getId());
                qqvo.setPerSource("2");
                if(StringUtils.isNotBlank(user.getPatientCity())){
                    CdAddress p = sysDao.getCdAddressDao().findByCode(user.getPatientCity());
                    if(p != null){
                        String code = AreaUtils.getAreaCode(p.getCtcode(),p.getLevel());
                        CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE,code);
                        if(value != null) {
                            qqvo.setPerArea(value.getCodeTitle());
                        }
                    }
                }
            }
            if(drUser != null){
                qqvo.setDrName(drUser.getDrName());
                qqvo.setDrAccount(drUser.getDrAccount());
                qqvo.setDrPwd(drUser.getDrPwd());
                qqvo.setDrGender(drUser.getDrGender());
                qqvo.setDrTel(drUser.getDrTel());
                qqvo.setDrId(drUser.getId());
                AppHospDept dept= (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                if(dept!=null){
                    qqvo.setHospId(dept.getId());
                    qqvo.setHospName(dept.getHospName());
                    qqvo.setHospAreaCode(dept.getHospAreaCode());
                    qqvo.setHospAddress(dept.getHospAddress());
                    qqvo.setHospTel(dept.getHospTel());
                }
            }
            if(StringUtils.isNotBlank(qqvo.getPerArea())){
                if(StringUtils.isNotBlank(qqvo.getPerType())){
                   /* String result = null;
                    if (qqvo.getPerArea().equals(AddressType.FZ.getValue())) {

                    } else if (qqvo.getPerArea().equals(AddressType.QZ.getValue())) {
                        result = "qz_";
                    } else if (qqvo.getPerArea().equals(AddressType.ZZ.getValue())) {
                        result = "zz_";
                    } else if (qqvo.getPerArea().equals(AddressType.PT.getValue())) {
                        result = "pt_";
                    } else if (qqvo.getPerArea().equals(AddressType.NP.getValue())) {
                        result = "np_";
                    } else if (qqvo.getPerArea().equals(AddressType.SM.getValue())) {
                        result = "sm_";
                    }
                    if(StringUtils.isNotBlank(result)){
                        qqvo.setDrId(result+qqvo.getDrId());
                        qqvo.setHospId(result+qqvo.getHospId());
                    }*/
                    String fwType = "";
                    String sermeal = "";//服务包id
                    AppSignForm form = sysDao.getAppSignformDao().findSignFormByUserState(table.getPatientId());
                    if(form != null){
                        fwType = sysDao.getAppLabelGroupDao().findFwValue(form.getId());
                        sermeal = form.getSignpackageid();
                        sysDao.getAppPerformanceStatisticsDao().addPerformanceData(qqvo,sermeal,fwType);
                    }

                }
            }

            List<AppMedicationTable> list = qvo.getUserMedicine();
            List<AppMedicationTable> lss = new ArrayList<AppMedicationTable>();
            if(list!=null&&list.size()>0){
                for(AppMedicationTable ls:list){
                    ls.setVisitId(table.getId());
                    sysDao.getServiceDo().add(ls);
                    lss.add(ls);
                }
            }
            qvo.setSaveMentalVisit(table);
            qvo.setUserMedicine(lss);
        }
        return qvo;
    }

    /**
     * 查询严重精神病随访信息
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public AppMentalVisitEntity findMentalVisit(String id) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        String sql = "SELECT " +
                "ID id\n" +
                "VISIT_ID visitId\n" +
                "NAME name\n" +
                "CODE code\n" +
                "FOLLOW_VISIT_TIME followVisitTime\n" +
                "PHONE_TEL phoneTel\n" +
                "PHONE_NAME phoneName\n" +
                "RELATIONSHIP_TO_VISIT relationshipToVisit\n" +
                "FOLLOW_VISIT_WAY followVisitWay\n" +
                "LOAT_VISIT loatVisit\n" +
                "DEATH_DATE deathDate\n" +
                "DEATH_REASON deathReason \n" +
                "DEATH_DISEASE_TYPE deathDiseaseType\n" +
                "DANGEROUS dangerous\n" +
                "NOW_SYMPTOMS nowSymptoms\n" +
                "NOW_SYMPTOMS_OTHER nowSymptomsOther\n" +
                "SELF_KNOWLEDGE selfKnowledge\n" +
                "SLEEP sleep\n" +
                "DIET diet\n" +
                "PERSONAL_LIFE personalLife\n" +
                "HOUSE_WORK housework\n" +
                "WORK work\n" +
                "LEARN_ABILITY learnAbility\n" +
                "INTERPERSONAL interpersonal\n" +
                "DANGEROUS_BEHAVIOR dangerousBehavior\n" +
                "TROUBLE_NUM troubleNum\n" +
                "CAUSE_TROUBLE_NUM causeTroubleNum\n" +
                "CAUSE_TROUBLE_NUM1 causeTroubleNum1\n" +
                "OTHER_DANGER_NUM otherDangerNum\n" +
                "AUTOLESION_NUM autolesionNum\n" +
                "SUICIDE_NUM suicideNum\n" +
                "FOLLOW_VISIT_SHUT followVisitShut\n" +
                "FOLLOW_VISIT_ZY followVisitZy\n" +
                "LAST_LEAVE_HOSPITAL_DATE lastLeaveHospitalDate\n" +
                "LABORATORY_CHECK laboratoryCheck\n" +
                "LABORATORY_CHECK_CONTENT laboratoryCheckContent\n" +
                "MEDICATION_ADHERENCE medicationAdherence\n" +
                "DRUG_REACTION drugReaction\n" +
                "DRUG_REACTION_CONTENT drugReactionContent\n" +
                "TREATMENT_RESULT treatmentResult\n" +
                "REFERRAL referral\n" +
                "REFERRAL_REASON referralReason\n" +
                "REFERRAL_ORG referralOrg\n" +
                "REFERRAL_DEPT referralDept\n" +
                "REHABILITATION_MEASURES rehabilitationMeasures\n" +
                "REHABILITATION_MEASURES_OTHER rehabilitationMeasuresOther\n" +
                "FOLLOW_VISIT_TYPE followVisitType\n" +
                "NEXT_VISIT_TIME nextVisiTime\n" +
                "VISIT_DOCTOR_NAME visitDoctorName\n" +
                "VISIT_DOCTOR_ID visitDoctorId\n" +
                "RESPONSIBLE_DOCTOR_ID responsibleDoctorId\n" +
                "RESPONSIBLE_DOCTOR_Name responsibleDoctorName\n" +
                "INPUT_DOCTOR_ID inputDoctorId\n" +
                "INPUT_DOCTOR_NAME inputDoctorName\n" +
                "PATIENT_ID patientId " +
                "FROM APP_MENTALVISIT_TABLE WHERE 1=1 ";
        sql += " AND ID = :id ";
        List<AppMentalVisitEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppMentalVisitEntity.class);
        if(list != null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    /**
     * 保存肺结核第一次入户随访信息
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public AppSaveFollowQvo saveFTBVisit(AppSaveFollowQvo qvo) throws Exception {
        AppFirstTBFollowVisitTable table = new AppFirstTBFollowVisitTable();
        CopyUtils.Copy(qvo.getSaveFTB(),table);
        AppFollowPlan plan = (AppFollowPlan)sysDao.getServiceDo().find(AppFollowPlan.class,table.getFtbVisitId());
        if(plan!=null){
            table.setFtbPatientName(plan.getSfFollowPatientName());
            table.setFtbCode(plan.getSfFollowNum());
            table.setFtbVisitTime(ExtendDate.getYMD(plan.getSfFollowDate()));
            if(StringUtils.isBlank(table.getFtbPatientId())){
                table.setFtbPatientId(plan.getSfFollowPatientid());
            }
            //查询第几次随访
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("patientId",plan.getSfFollowPatientid());
            map.put("type",ResidentMangeType.JHB.getValue());
            map.put("state",FollowPlanState.YIWANCHENG.getValue());

            String sql = "SELECT count(1) FROM APP_FOLLOW_PLAN WHERE SF_FOLLOW_STATE = :state AND SF_FOLLOW_PATIENTID=:patientId AND SF_FOLLOW_TYPE =:type ";
            int num = sysDao.getServiceDo().gteSqlCount(sql,map);
            //判断随访计划的随访类型是否为空，不为空新增一条此类型的随访计划
            //判断随访计划的随访类型是否为空，为空修改类型和转诊情况
            if(ResidentMangeType.JHB.getValue().equals(plan.getSfFollowType())){
                plan.setSfFollowType(ResidentMangeType.JHB.getValue());
                plan.setSfEndDate(Calendar.getInstance());
                plan.setSfFollowState(FollowPlanState.YIWANCHENG.getValue());
                plan.setSfXaxis(qvo.getSfXaxis());
                plan.setSfYaxis(qvo.getSfYaxis());
                plan.setSfTypeNum(String.valueOf(num+1));
                plan.setNextTime(ExtendDate.getCalendar(table.getFtbNextTime()));
                sysDao.getServiceDo().modify(plan);
            }

            //新增下一次随访计划情况
            //判断下一次随访计划是否新增
//            Map<String,Object> mapO = new HashMap<String,Object>();
//            mapO.put("patientId",plan.getSfFollowPatientid());
//            String sqll = "SELECT COUNT(1) FROM APP_FOLLOW_PLAN WHERE SF_FOLLOW_PATIENTID=:patientId AND SF_FOLLOW_DATE> NOW()";
//            int numm = sysDao.getServiceDo().findCount(sqll,mapO);
            int numm =0;
            if(numm == 0){//没有下一次随访计划，新增下一次随访计划
                AppFollowPlan pl = new AppFollowPlan();
                if(StringUtils.isNotBlank(plan.getSfFollowPatientid())) {
                    AppPatientUser patientUser = (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class, plan.getSfFollowPatientid());
                    pl.setSfFollowPatientid(patientUser.getId());//患者id
                    pl.setSfFollowPatientName(patientUser.getPatientName());//患者姓名
                }
                pl.setSfFollowType(ResidentMangeType.YZJSZY.getValue());
                pl.setSfFollowDoctorid(table.getFtbDoctorId());//下一次随访计划已此次随访医生建立
                pl.setSfFollowMode(plan.getSfFollowMode());
                pl.setSfFollowState(FollowPlanState.WEIWANCHENG.getValue());//随访状态未开始
                if(StringUtils.isNotBlank(table.getFtbNextTime())){
                    pl.setSfFollowDate(ExtendDate.getCalendar(table.getFtbNextTime()));
                }else{
                    Calendar ss = Calendar.getInstance();
                    ss.add(Calendar.MONTH,3);
                    pl.setSfFollowDate(ss);
                }
                pl.setSfCreateDate(Calendar.getInstance());
                String nextNum = "";
                String deptId = "";
                String hospAreaCode = "";
                AppDrUser drUser = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class,plan.getSfFollowDoctorid());
                AppHospDept dept= (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                if(dept!=null && dept.getHospAreaCode()!=null) {
                    AppSerial serial = sysDao.getAppSignformDao().getAppSerial(dept.getHospAreaCode().substring(0, 4), "follow");
                    if(serial!=null) {
                        Map<String,Object> bcnum = sysDao.getAppSignformDao().getNum(serial.getSerialNo(),SignFormType.APPSTATE.getValue());
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
                sysDao.getServiceDo().add(pl);
                if(StringUtils.isBlank(table.getFtbNextTime())){
                    table.setFtbNextTime(ExtendDate.getYMD(pl.getSfFollowDate()));
                }
            }
            sysDao.getServiceDo().add(table);
            qvo.setSaveFTB(table);
            //修改工作计划的完成状态
            List<AppWorkingPlan> lisWp = sysDao.getServiceDo().loadByPk(AppWorkingPlan.class,"planForeignId",table.getFtbVisitId());
            if(lisWp!=null && lisWp.size()>0){
                AppWorkingPlan wp = lisWp.get(0);
                wp.setPlanFinishDate(Calendar.getInstance());
                wp.setPlanState(CommonWorkPlanState.YWC.getValue());
                this.sysDao.getServiceDo().modify(plan);
            }

            //履约数据
            PerformanceDataQvo qqvo = new PerformanceDataQvo();
            AppPatientUser user = (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,table.getFtbPatientId());
            AppDrUser drUser = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class,plan.getSfFollowDoctorid());
            if(user!=null){
                qqvo.setPerName(user.getPatientName());
                qqvo.setPerIdno(user.getPatientIdno());
                qqvo.setPerType(PerformanceType.DQSF.getValue());
                qqvo.setPerFollowType(ResidentMangeType.JHB.getValue());//结核病
                qqvo.setPerFollowNextDate(table.getFtbNextTime());
                qqvo.setPerForeign(table.getId());
                qqvo.setPerSource("2");
                if(StringUtils.isNotBlank(user.getPatientCity())){
                    CdAddress p = sysDao.getCdAddressDao().findByCode(user.getPatientCity());
                    if(p != null){
                        String code = AreaUtils.getAreaCode(p.getCtcode(),p.getLevel());
                        CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE,code);
                        if(value != null) {
                            qqvo.setPerArea(value.getCodeTitle());
                        }
                    }
                }
            }
            if(drUser != null){
                qqvo.setDrName(drUser.getDrName());
                qqvo.setDrAccount(drUser.getDrAccount());
                qqvo.setDrPwd(drUser.getDrPwd());
                qqvo.setDrGender(drUser.getDrGender());
                qqvo.setDrTel(drUser.getDrTel());
                qqvo.setDrId(drUser.getId());
                AppHospDept dept= (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                if(dept!=null){
                    qqvo.setHospId(dept.getId());
                    qqvo.setHospName(dept.getHospName());
                    qqvo.setHospAreaCode(dept.getHospAreaCode());
                    qqvo.setHospAddress(dept.getHospAddress());
                    qqvo.setHospTel(dept.getHospTel());
                }
            }
            if(StringUtils.isNotBlank(qqvo.getPerArea())){
                if(StringUtils.isNotBlank(qqvo.getPerType())){
                    /*String result = null;
                    if (qqvo.getPerArea().equals(AddressType.FZ.getValue())) {
                        result = "";
                    } else if (qqvo.getPerArea().equals(AddressType.QZ.getValue())) {
                        result = "qz_";
                    } else if (qqvo.getPerArea().equals(AddressType.ZZ.getValue())) {
                        result = "zz_";
                    } else if (qqvo.getPerArea().equals(AddressType.PT.getValue())) {
                        result = "pt_";
                    } else if (qqvo.getPerArea().equals(AddressType.NP.getValue())) {
                        result = "np_";
                    } else if (qqvo.getPerArea().equals(AddressType.SM.getValue())) {
                        result = "sm_";
                    }
                    if(StringUtils.isNotBlank(result)){
                        qqvo.setDrId(result+qqvo.getDrId());
                        qqvo.setHospId(result+qqvo.getHospId());
                    }*/
                    String fwType = "";
                    String sermeal = "";//服务包id
                    AppSignForm form = sysDao.getAppSignformDao().findSignFormByUserState(table.getFtbPatientId());
                    if(form != null){
                        fwType = sysDao.getAppLabelGroupDao().findFwValue(form.getId());
                        sermeal = form.getSignpackageid();
                        sysDao.getAppPerformanceStatisticsDao().addPerformanceData(qqvo,sermeal,fwType);
                    }
                }
            }
        }
        return qvo;
    }

    /**
     * 查询结核病第一次入户随访信息
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public AppFirstTBFollowVisitEntity findFtbVisit(String id) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        String sql = "SELECT " +
                "ID id,\n" +
                "FTB_VISIT_ID ftbVisitId,\n" +
                "FTB_PATIENT_ID ftbPatientId,\n" +
                "FTB_PATIENT_NAME ftbPatientName,\n" +
                "FTB_CODE ftbCode,\n" +
                "FTB_VISIT_TIME ftbVisitTime,\n" +
                "FTB_VISIT_WAY ftbVisitWay,\n" +
                "FTB_PATIENT_TYPE ftbPatientType,\n" +
                "FTB_SPUTUM_CONDITION ftbSputumCondition,\n" +
                "FTB_DRUG_RESISTANCE ftbDrugResistance,\n" +
                "FTB_SYMPTOMS_AND_SIGNS ftbSymptomsAndSigns,\n" +
                "FTB_SYMPTOMS_AND_SIGNS_OTHER ftbSymptomsAndSignsOther,\n" +
                "FTB_CHEMOTHERAPY_REGIMEN ftbChemotherapyRegimen,\n" +
                "FTB_USAGE ftbUsage,\n" +
                "FTB_DRUGS_DOSAGE_TYPE ftbDrugsDosageType,\n" +
                "FTB_SUPERVISORY_STAFF ftbSupervisoryStaff,\n" +
                "FTB_ALONE_ROOM ftbAloneRoom,\n" +
                "FTB_VENTILATION_CONDITION ftbVentilationCondition,\n" +
                "FTB_SMOKE_CIGARETTE ftbSmokeCigarette,\n" +
                "FTB_SMOKE_DAY ftbSmokeDay,\n" +
                "FTB_DRINK_WINE ftbDrinkWine,\n" +
                "FTB_DRINK_WINE_DAY ftbDrinkWineDay,\n" +
                "FTB_TAKE_MEDICINE_ADDR ftbTakeMedicineAddr,\n" +
                "FTB_TAKE_MEDICINE_TIME ftbTakeMedicineTime,\n" +
                "FTB_MEDICATION_RECORD_CARD ftbMedicationRecordCard,\n" +
                "FTB_TAKING_MEDICINE ftbTakingMedicine,\n" +
                "FTB_TREATMENT_COURSE ftbTreatmentCourse,\n" +
                "FTB_TAKING_MEDICINE_HARM ftbTakingMedicineHarm,\n" +
                "FTB_HANDLING_ADVERSE_REACTIONS ftbHandlingAdverseReactions,\n" +
                "FTB_FURTHER_CONSULTATION ftbFurtherConsultation,\n" +
                "FTB_GOOUT_TAKE_MEDICINE ftbGoOutTakeMedicine,\n" +
                "FTB_LIFE_MATTERS ftbLifeMatters,\n" +
                "FTB_CONTACT_INSPECTION ftbContactInspection,\n" +
                "FTB_NEXT_TIME ftbNextTime,\n" +
                "FTB_DOCTOR_ID ftbDoctorId,\n" +
                "FTB_DOCTOR_NAME ftbDoctorName " +
                "FROM APP_FIRST_TB_FOLLOW_VISIT WHERE 1=1 ";
        sql += " AND ID = :id ";
        List<AppFirstTBFollowVisitEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppFirstTBFollowVisitEntity.class);
        if(list != null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    /**
     * 保存结核病随访信息
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public AppSaveFollowQvo saveTBVisit(AppSaveFollowQvo qvo) throws Exception {
        AppTBFollowVisitTable table = new AppTBFollowVisitTable();
        CopyUtils.Copy(qvo.getSaveTB(),table);
        AppFollowPlan plan = (AppFollowPlan)sysDao.getServiceDo().find(AppFollowPlan.class,table.getVisitId());
        if(plan!=null){
            table.setName(plan.getSfFollowPatientName());
            table.setCode(plan.getSfFollowNum());
            table.setFollowVisitDate(ExtendDate.getYMD(plan.getSfFollowDate()));
            if(StringUtils.isBlank(table.getFollowVisitType())){
                table.setFollowVisitType(plan.getSfFollowMode());
            }else{
                plan.setSfFollowMode(table.getFollowVisitType());
            }
            table.setPatientId(plan.getSfFollowPatientid());
            //查询第几次随访
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("patientId",plan.getSfFollowPatientid());
            map.put("type",ResidentMangeType.JHB.getValue());
            map.put("state",FollowPlanState.YIWANCHENG.getValue());

            String sql = "SELECT count(1) FROM APP_FOLLOW_PLAN WHERE SF_FOLLOW_STATE = :state AND SF_FOLLOW_PATIENTID=:patientId AND SF_FOLLOW_TYPE =:type ";
            int num = sysDao.getServiceDo().gteSqlCount(sql,map);
            if(ResidentMangeType.JHB.getValue().equals(plan.getSfFollowType())){
                plan.setSfFollowType(ResidentMangeType.JHB.getValue());
                plan.setSfEndDate(Calendar.getInstance());
                plan.setSfFollowState(FollowPlanState.YIWANCHENG.getValue());
                plan.setSfReferralDept(table.getReferralDeptType());
//                plan.setSfReferralOrg(table.get());
                plan.setSfReferralReason(table.getReferralReason());
                plan.setSfXaxis(qvo.getSfXaxis());
                plan.setSfYaxis(qvo.getSfYaxis());
                plan.setSfTypeNum(String.valueOf(num+1));
                plan.setNextTime(ExtendDate.getCalendar(table.getNextVisiTime()));
                sysDao.getServiceDo().modify(plan);
            }
            //新增下一次随访计划情况
            //判断下一次随访计划是否新增
//            Map<String,Object> mapO = new HashMap<String,Object>();
//            mapO.put("patientId",plan.getSfFollowPatientid());
//            String sqll = "SELECT COUNT(1) FROM APP_FOLLOW_PLAN WHERE SF_FOLLOW_PATIENTID=:patientId AND SF_FOLLOW_DATE> NOW()";
//            int numm = sysDao.getServiceDo().findCount(sqll,mapO);
            int numm =0;
            if(numm == 0){//没有下一次随访计划，新增下一次随访计划
                AppFollowPlan pl = new AppFollowPlan();
                if(StringUtils.isNotBlank(plan.getSfFollowPatientid())) {
                    AppPatientUser patientUser = (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class, plan.getSfFollowPatientid());
                    pl.setSfFollowPatientid(patientUser.getId());//患者id
                    pl.setSfFollowPatientName(patientUser.getPatientName());//患者姓名
                }
                pl.setSfFollowType(ResidentMangeType.JHB.getValue());
                pl.setSfFollowDoctorid(table.getVisitDoctorId());//下一次随访计划已此次随访医生建立
                pl.setSfFollowMode(plan.getSfFollowMode());
                pl.setSfFollowState(FollowPlanState.WEIWANCHENG.getValue());//随访状态未开始
                //有转诊情况、本次随访不稳定、药物不良反应
                if(StringUtils.isNotBlank(table.getReferralReason())||DrugState.YOU.getValue().equals(table.getDrugReaction())){
                    Calendar cal = Calendar.getInstance();
                    cal.add(Calendar.DATE,14);
                    pl.setSfFollowDate(cal);
                }else{
                    //正常情况下新增下一次随访计划日期
//                    Calendar cal = Calendar.getInstance();
//                    cal.add(Calendar.MONTH,Integer.parseInt(PropertiesUtil.getConfValue("month")));
//                    pl.setSfFollowDate(cal);
                    if(StringUtils.isNotBlank(table.getNextVisiTime())){
                        pl.setSfFollowDate(ExtendDate.getCalendar(table.getNextVisiTime()));
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
                AppDrUser drUser = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class,plan.getSfFollowDoctorid());
                AppHospDept dept= (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                if(dept!=null && dept.getHospAreaCode()!=null) {
                    AppSerial serial = sysDao.getAppSignformDao().getAppSerial(dept.getHospAreaCode().substring(0, 4), "follow");
                    if(serial!=null) {
                        Map<String,Object> bcnum = sysDao.getAppSignformDao().getNum(serial.getSerialNo(),SignFormType.APPSTATE.getValue());
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
//                pl.setSfReferral(table.getReferral());
                pl.setSfReferralDept(table.getReferralDeptType());
//                pl.setSfReferralOrg(table.getReferralOrg());
                pl.setSfReferralReason(table.getReferralReason());
                sysDao.getServiceDo().add(pl);
                table.setNextVisiTime(ExtendDate.getYMD(pl.getSfFollowDate()));
            }
            sysDao.getServiceDo().add(table);

            //修改工作计划的完成状态
            List<AppWorkingPlan> lisWp = sysDao.getServiceDo().loadByPk(AppWorkingPlan.class,"planForeignId",table.getVisitId());
            if(lisWp!=null && lisWp.size()>0){
                AppWorkingPlan wp = lisWp.get(0);
                wp.setPlanFinishDate(Calendar.getInstance());
                wp.setPlanState(CommonWorkPlanState.YWC.getValue());
                this.sysDao.getServiceDo().modify(plan);
            }

            //履约数据
            PerformanceDataQvo qqvo = new PerformanceDataQvo();
            AppPatientUser user = (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,table.getPatientId());
            AppDrUser drUser = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class,plan.getSfFollowDoctorid());
            if(user!=null){
                qqvo.setPerName(user.getPatientName());
                qqvo.setPerIdno(user.getPatientIdno());
                qqvo.setPerType(PerformanceType.DQSF.getValue());
                qqvo.setPerFollowType(ResidentMangeType.JHB.getValue());//严重精神病
                qqvo.setPerFollowNextDate(table.getNextVisiTime());
                qqvo.setPerForeign(table.getId());
                qqvo.setPerSource("2");
                if(StringUtils.isNotBlank(user.getPatientCity())){
                    CdAddress p = sysDao.getCdAddressDao().findByCode(user.getPatientCity());
                    if(p != null){
                        String code = AreaUtils.getAreaCode(p.getCtcode(),p.getLevel());
                        CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE,code);
                        if(value != null) {
                            qqvo.setPerArea(value.getCodeTitle());
                        }
                    }
                }
            }
            if(drUser != null){
                qqvo.setDrName(drUser.getDrName());
                qqvo.setDrAccount(drUser.getDrAccount());
                qqvo.setDrPwd(drUser.getDrPwd());
                qqvo.setDrGender(drUser.getDrGender());
                qqvo.setDrTel(drUser.getDrTel());
                qqvo.setDrId(drUser.getId());
                AppHospDept dept= (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                if(dept!=null){
                    qqvo.setHospId(dept.getId());
                    qqvo.setHospName(dept.getHospName());
                    qqvo.setHospAreaCode(dept.getHospAreaCode());
                    qqvo.setHospAddress(dept.getHospAddress());
                    qqvo.setHospTel(dept.getHospTel());
                }
            }
            if(StringUtils.isNotBlank(qqvo.getPerArea())){
                if(StringUtils.isNotBlank(qqvo.getPerType())){
                   /* String result = null;
                    if (qqvo.getPerArea().equals(AddressType.FZ.getValue())) {

                    } else if (qqvo.getPerArea().equals(AddressType.QZ.getValue())) {
                        result = "qz_";
                    } else if (qqvo.getPerArea().equals(AddressType.ZZ.getValue())) {
                        result = "zz_";
                    } else if (qqvo.getPerArea().equals(AddressType.PT.getValue())) {
                        result = "pt_";
                    } else if (qqvo.getPerArea().equals(AddressType.NP.getValue())) {
                        result = "np_";
                    } else if (qqvo.getPerArea().equals(AddressType.SM.getValue())) {
                        result = "sm_";
                    }
                    if(StringUtils.isNotBlank(result)){
                        qqvo.setDrId(result+qqvo.getDrId());
                        qqvo.setHospId(result+qqvo.getHospId());
                    }*/
                    String fwType = "";
                    String sermeal = "";//服务包id
                    AppSignForm form = sysDao.getAppSignformDao().findSignFormByUserState(table.getPatientId());
                    if(form != null){
                        fwType = sysDao.getAppLabelGroupDao().findFwValue(form.getId());
                        sermeal = form.getSignpackageid();
                        sysDao.getAppPerformanceStatisticsDao().addPerformanceData(qqvo,sermeal,fwType);
                    }
                }
            }
            qvo.setSaveTB(table);
        }
        return qvo;
    }

    /**
     * 查询结核病随访信息
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public AppTBFollowVisitEntity findTbVisit(String id) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        String sql = "SELECT " +
                "ID id,\n" +
                "VISIT_ID visitId,\n" +
                "NAME name,\n" +
                "CODE code,\n" +
                "FOLLOW_VISIT_DATE followVisitDate,\n" +
                "MONTH_NUM monthNum,\n" +
                "SUPERVISOR supervisor,\n" +
                "FOLLOW_VISIT_TYPE followVisitType,\n" +
                "SYMPTOMS symptoms,\n" +
                "SYMPTOMS_OTHER symptomsOther,\n" +
                "SMOKING_TO_NUM smokingToNum,\n" +
                "SMOKING_TO_DAY smokingToDay,\n" +
                "DRINKING_TO_NUM drinkingToNum,\n" +
                "DRINKING_TO_DAY drinkingToDay,\n" +
                "HL_METHODS hlMethods,\n" +
                "USER_METHOD userMethod,\n" +
                "DRUG_TYPE drugType,\n" +
                "NOT_EAT_PILLS_NUM notEatPillsNum,\n" +
                "DRUG_REACTION drugReaction,\n" +
                "DRUG_REACTION_CONTENT drugReactionContent,\n" +
                "COMPLICATION complication,\n" +
                "COMPLICATION_CONTENT complicationContent,\n" +
                "REFERRAL_DEPT_TYPE referralDeptType,\n" +
                "REFERRAL_REASON referralReason,\n" +
                "REFERRAL_RESULT referralResult,\n" +
                "OPINION opinion,\n" +
                "NEXT_VISIT_TIME nextVisiTime,\n" +
                "VISIT_DOCTOR_NAME visitDoctorName,\n" +
                "VISIT_DOCTOR_ID visitDoctorId,\n" +
                "STOP_DATE stopDate,\n" +
                "STOP_REASON stopReason,\n" +
                "SHOULD_VISIT_NUM shouldVisitNum,\n" +
                "NOW_VISIT_NUM nowVisitNum,\n" +
                "SHOULD_TAKE_NUM shouldTakeNum,\n" +
                "NOW_TAKE_NUM nowTakeNum,\n" +
                "TAKE_RATE takeRate,\n" +
                "ASSESS_DOCTOR_NAME assessDoctorName,\n" +
                "ASSESS_DOCTOR_ID assessDoctorId,\n" +
                "PATIENT_ID patientId,\n" +
                "IS_BASIC isBasic " +
                "FROM APP_TB_FOLLOW_VISIT WHERE 1=1 ";
        sql += " AND ID = :id ";
        List<AppTBFollowVisitEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppTBFollowVisitEntity.class);
        if(list != null && list.size()>0){
            return list.get(0);
        }
        return null;
    }
}
