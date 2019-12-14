package com.ylz.bizDo.performance.bizDo.impl;

import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.jtapp.basicHealthEntity.findHealthInfo.entity.In;
import com.ylz.bizDo.jtapp.commonEntity.AppFamilyInfo;
import com.ylz.bizDo.jtapp.commonEntity.AppLyTxEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppTcmLyEntity;
import com.ylz.bizDo.jtapp.commonVo.AppCommQvo;
import com.ylz.bizDo.jtapp.drVo.AppLyQvo;
import com.ylz.bizDo.mangecount.vo.ResidentVo;
import com.ylz.bizDo.performance.bizDo.AppPerFormanceDao;
import com.ylz.bizDo.performance.vo.HealthGroupVo;
import com.ylz.bizDo.performance.vo.PerformanceVo;
import com.ylz.bizDo.performance.vo.TeamLsitVo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.comEnum.*;
import com.ylz.packcommon.common.exception.DaoException;
import com.ylz.packcommon.common.util.ExtendDate;
import com.ylz.packcommon.common.util.ExtendDateUtil;
import com.ylz.packcommon.common.util.MyMathUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 绩效
 */
@Service("appPerFormanceDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppPerFormanceDaoImpl implements AppPerFormanceDao {

    @Autowired
    private SysDao sysDao;

    /**
     * 首页统计(签约人员,未缴费人群,咨询量,求助量)
     * @param qvo areaId 区域 hospId 医院主键 teamId 团队主键 drId 医生主键
     * @return
     */
    public Map<String,Object> findIndexCount(PerformanceVo qvo) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        Map<String,Object> mapCount = new HashMap<String,Object>();
        //签约人数
        String[] signStates = new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
        map.put("SIGNSTATE", signStates);
        String sqlQyrs = "SELECT count(1) FROM APP_SIGN_FORM t WHERE 1=1 AND t.SIGN_STATE IN (:SIGNSTATE) ";
        //未缴费
        map.put("payState", PayType.WEIJIAOFEI.getValue());
        String sqlWjf = "SELECT count(1) FROM APP_SIGN_FORM t WHERE 1=1 AND t.SIGN_STATE IN (:SIGNSTATE) AND t.SIGN_PAY_STATE = :payState ";
        //咨询数
        String sqlZxs = "SELECT count(1) FROM APP_CONSULT t WHERE 1=1 ";
        //求助量
        String sqlQzl = "SELECT count(1) FROM APP_SEEK_HELP t WHERE 1=1 ";
//        //区域统计
//        if(qvo.getRoleId().equals(AppRoleType.SHENG.getValue()) ||
//                qvo.getRoleId().equals(AppRoleType.SHI.getValue()) ||
//                qvo.getRoleId().equals(AppRoleType.QU.getValue())){
//            map.put("areaId", AreaUtils.getAreaCode(qvo.getAreaId())+"%");
//            sqlQyrs += " AND t.SIGN_AREA_CODE LIKE :areaId ";
//            sqlWjf += "  AND t.SIGN_AREA_CODE LIKE :areaId ";
//            sqlZxs += "  AND t.CON_AREA_CODE LIKE :areaId ";
//            sqlQzl += "  AND t.SEEK_AREA_CODE LIKE :areaId";
//        }
//
//        //医院统计
//        if(qvo.getRoleId().equals(AppRoleType.SHEQU.getValue())){
//            map.put("hospId",qvo.getHospId());
//            sqlQyrs += " AND t.SIGN_HOSP_ID = :hospId ";
//            sqlWjf += "  AND t.SIGN_HOSP_ID = :hospId ";
//            sqlZxs += "  AND t.CON_HOSP_ID = :hospId ";
//            sqlQzl += "  AND t.SEEK_HOSP_ID = :hospId";
//        }

        //医生统计
//        if(qvo.getRoleId().equals(AppRoleType.YISHENG.getValue())){
            //团队统计
//            if(StringUtils.isNotBlank(qvo.getTeamId()) && StringUtils.isBlank(qvo.getDrId())){
//                map.put("teamId",qvo.getTeamId());
//                sqlQyrs += " AND t.SIGN_TEAM_ID = :teamId ";
//                sqlWjf += "  AND t.SIGN_TEAM_ID = :teamId ";
//                sqlZxs += "  AND t.CON_TEAM_ID = :teamId ";
//                sqlQzl += "  AND t.SEEK_TEAM_ID = :teamId ";
//            }
//
//            //医生统计
//            if(StringUtils.isNotBlank(qvo.getDrId()) && StringUtils.isNotBlank(qvo.getTeamId())){
                map.put("drId",qvo.getDrId());
                map.put("teamId",qvo.getTeamId());
                sqlQyrs += " AND t.SIGN_DR_ID = :drId AND t.SIGN_TEAM_ID = :teamId ";
                sqlWjf += "  AND t.SIGN_DR_ID = :drId AND t.SIGN_TEAM_ID = :teamId ";
                sqlZxs += "  AND t.CON_REPLIER_ID = :drId ";
                sqlQzl += "  AND t.SEEK_DR_ID = :drId ";
//            }
//        }
        //签约人数
        int qyrs = this.sysDao.getServiceReadDo().findCount(sqlQyrs,map);
        mapCount.put("qysr",qyrs);
        //未缴费
        int wjf = this.sysDao.getServiceReadDo().findCount(sqlWjf,map);
        mapCount.put("wjf",wjf);
        //咨询数
        int zxs = this.sysDao.getServiceReadDo().findCount(sqlZxs,map);
        mapCount.put("zxs",zxs);
        //求助量
        int qzl = this.sysDao.getServiceReadDo().findCount(sqlQzl,map);
        mapCount.put("qzl",qzl);
        return mapCount;
    }

    /**
     * 工作进度统计(总计划,未完成,完成,完成率,周,月数据)
     * @param qvo areaId 区域 hospId 医院主键 teamId 团队主键 drId 医生主键
     * @return
     */
    @Override
    public Map<String, Object> findWorkPlanCount(PerformanceVo qvo)  throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        Map<String,Object> mapCount = new HashMap<String,Object>();
        String sql =  "SELECT count(1) FROM APP_WORKING_PLAN WHERE 1=1  AND PLAN_DATE >= :startDate  AND PLAN_DATE <= :endDate " ;
//        //区域统计
//        if(qvo.getRoleId().equals(AppRoleType.SHENG.getValue()) ||
//                qvo.getRoleId().equals(AppRoleType.SHI.getValue()) ||
//                qvo.getRoleId().equals(AppRoleType.QU.getValue())){
//            map.put("areaCode", AreaUtils.getAreaCode(qvo.getAreaId())+"%");
//            sql += " AND PLAN_AREA_CODE LIKE :areaCode ";
//        }
//
//        //医院统计
//        if(qvo.getRoleId().equals(AppRoleType.SHEQU.getValue())){
//            map.put("hospId",qvo.getHospId());
//            sql += " AND PLAN_HOSP_ID = :hospId ";
//        }

//        if(qvo.getRoleId().equals(AppRoleType.YISHENG.getValue())){
            //团队统计
//            if(StringUtils.isNotBlank(qvo.getTeamId())){
//                map.put("teamId",qvo.getTeamId());
//                sql += " AND PLAN_TEAM_ID = :teamId ";
//            }

            //医生统计
            if(StringUtils.isNotBlank(qvo.getDrId())){
                map.put("drId",qvo.getDrId());
                sql += " AND PLAN_DR_ID = :drId ";
            }
//        }

        map.put("startDate",qvo.getStartDate()+" 00:00:00");
        map.put("endDate",qvo.getEndDate()+" 23:59:59");
        //总工作量
        String sqlZgzl = sql;
        int zgzl = this.sysDao.getServiceReadDo().findCount(sqlZgzl,map);
        mapCount.put("zgzl",zgzl);

        map.put("planState","1");//未完成状态
        //未完成量
        String sqlWwcl = sql + " AND PLAN_STATE = :planState";
        int wwcl = this.sysDao.getServiceReadDo().findCount(sqlWwcl,map);
        mapCount.put("wwcl",wwcl);

        map.put("planState","2");//已完成状态
        //已完成
        String sqlYwcl = sql + " AND PLAN_STATE = :planState";
        int ywcl = this.sysDao.getServiceReadDo().findCount(sqlYwcl,map);
        mapCount.put("ywcl",ywcl);

        double wcl = 0;
        //完成率
        if(zgzl > 0 ){
            wcl = MyMathUtil.div(Double.valueOf(ywcl),Double.valueOf(zgzl),2)*100;//完成率
        }

        mapCount.put("wcl",wcl);

        int daysBetween = ExtendDate.daysBetween(qvo.getStartDate(),qvo.getEndDate());
        if(daysBetween >0){
            String end = null;
            String title = null;
            List<String> lsString = new ArrayList<String>();
            List<Integer> lsValueZjh = new ArrayList<Integer>();//总计划量
            List<Integer> lsValuewcl= new ArrayList<Integer>();//完成量
            for(int i=0;i<=daysBetween;i++){
                if(StringUtils.isBlank(end)){
                    end = qvo.getStartDate();
                    title = ExtendDate.getChineseMD(end);
                }else{
                    end = ExtendDateUtil.addDate(end,ExtendDateType.DAYS.getValue(),1);
                    title = ExtendDate.getChineseMD(end);
                }
                map.put("startDate",end+" 00:00:00");
                map.put("endDate",end+" 23:59:59");
                //每天总计划量
                String sqlTodayZjh = sql;
                int toZjh = this.sysDao.getServiceReadDo().findCount(sqlTodayZjh,map);
                map.put("planState","2");//未完成状态
                //每天完成量
                String sqlTodayWcl =  sql + " AND PLAN_STATE = :planState";
                int toWcl = this.sysDao.getServiceReadDo().findCount(sqlTodayWcl,map);
                lsString.add(title);
                lsValueZjh.add(toZjh);
                lsValuewcl.add(toWcl);
            }
            mapCount.put("lsString",lsString);
            mapCount.put("lsValueZjh",lsValueZjh);
            mapCount.put("lsValuewcl",lsValuewcl);
        }
        return mapCount;
    }


    /**
     * 签约居民健康分布情况
     * @param qvo hospId 医院主键 drId 医生主键 labelGruops 疾病类型 多使用分号隔开 labelGruopsColor 疾病类型颜色 多使用分号隔开 teamId 团队 多使用分号隔开
     * @return
     */
    public List<HealthGroupVo> findHealthGroup(PerformanceVo qvo) throws Exception{
        List<HealthGroupVo> ls;
        HashMap map = new HashMap();
        String sql="SELECT\n" +
                "\tb.ID patientId,\n" +
                "\tb.PATIENT_NAME patientName,\n" +
                "\tb.PATIENT_ADDRESS patientAddress,\n" +
                "\tb.PATIENT_TEL patientTel,\n" +
                "\tb.PATIENT_ORDINATE patientOrdinate,\n" +
                "\tb.PATIENT_ABSCISSA patientAbscissa,\n" +
                "\tb.PATIENT_AGE patientAge,\n" +
                "\tc.LABEL_TITLE labelTitle,\n" +
                "\tc.LABEL_COLOR labelColor\n" +
//                "\t(SELECT GROUP_CONCAT(LABEL_TITLE) from APP_LABEL_GROUP g where g.LABEL_SIGN_ID=a.ID) labelTitle,\n" +
//                "\t(SELECT GROUP_CONCAT(LABEL_COLOR) from APP_LABEL_GROUP g where g.LABEL_SIGN_ID=a.ID) labelColor\n" +
                "FROM\n" +
                "\tAPP_SIGN_FORM a\n" +
                "LEFT JOIN APP_PATIENT_USER b ON a.SIGN_PATIENT_ID = b.ID\n" +
                "LEFT JOIN APP_LABEL_DISEASE c ON a.ID = c.LABEL_SIGN_ID\n" +
                "where 1=1";
//        if(StringUtils.isNotBlank(qvo.getDrId())){
//            sql +=" AND a.SIGN_DR_ID=:SIGN_DR_ID";
//            map.put("SIGN_DR_ID",qvo.getDrId());
//        }
        if(StringUtils.isNotBlank(qvo.getHospId())){
            sql +=" AND a.SIGN_HOSP_ID=:SIGN_HOSP_ID";
            map.put("SIGN_HOSP_ID",qvo.getHospId());
        }
        if(StringUtils.isNotBlank(qvo.getLabelGruops())){
            String[] m=qvo.getLabelGruops().split(";");
            sql +=" AND c.LABEL_VALUE in (:LABEL_VALUE)";
            map.put("LABEL_VALUE",m);
        }
        if(StringUtils.isNotBlank(qvo.getLabelGruopsColor())){
            String[] m=qvo.getLabelGruopsColor().split(";");
            sql +=" and c.LABEL_COLOR in (:LABEL_COLOR)";
            map.put("LABEL_COLOR",m);
        }
        if(StringUtils.isNotBlank(qvo.getTeamId())){
            String[] m=qvo.getTeamId().split(";");
            sql +=" AND a.SIGN_TEAM_ID in (:SIGN_TEAM_ID)";
            map.put("SIGN_TEAM_ID",m);
        }
        ls=sysDao.getServiceReadDo().findSqlMapRVo(sql,map,HealthGroupVo.class);
        return ls;
    }


    /**
     * 健康干预工作量统计
     * @param qvo hospId 医院主键 drId 医生主键
     * @return
     */
    public Map findHealthMeddle(PerformanceVo qvo) throws Exception
    {
        HashMap rmap = new HashMap();
        String sql="SELECT COUNT(1) FROM\tAPP_HEALTH_MEDDLE a WHERE 1=1 ";
        HashMap map = new HashMap();
        //
//        if(qvo.getRoleId().equals(AppRoleType.SHENG.getValue()) ||
//                qvo.getRoleId().equals(AppRoleType.SHI.getValue()) ||
//                qvo.getRoleId().equals(AppRoleType.QU.getValue())){
//            sql +=" AND a.HM_AREA_CODE=:HM_AREA_CODE";
//            map.put("HM_AREA_CODE",qvo.getAreaId());
//        }

        if(StringUtils.isNotBlank(qvo.getDrId())){
            sql +=" AND a.HM_DR_ID=:HM_DR_ID";
            map.put("HM_DR_ID",qvo.getDrId());
        }
        if(StringUtils.isNotBlank(qvo.getHospId())){
            sql +=" AND a.HM_HOSP_ID=:HM_HOSP_ID";
            map.put("HM_HOSP_ID",qvo.getHospId());
        }
        //前七日已干预人次
        Calendar ca=Calendar.getInstance();
        ca.add(Calendar.DATE,-7);
        String sqla=sql+" AND a.HM_CREATE_TIME>:HM_CREATE_TIME";
        map.put("HM_CREATE_TIME",ExtendDate.getYMD_h_m_s(ca));
        int ia=sysDao.getServiceReadDo().findCount(sqla,map);
        rmap.put("前七日已干预人次",ia);
        //近一月
        Calendar cb=Calendar.getInstance();
        cb.add(Calendar.MONTH,-1);
        String sqlb=sql+" AND a.HM_CREATE_TIME>:HM_CREATE_TIMEb";
        map.put("HM_CREATE_TIMEb",ExtendDate.getYMD_h_m_s(cb));
        int ib=sysDao.getServiceReadDo().findCount(sqlb,map);
        rmap.put("近一月",ib);
        //近一季度
        Calendar cc=Calendar.getInstance();
        cc.add(Calendar.MONTH,-3);
        String sqlc=sql+" AND a.HM_CREATE_TIME>:HM_CREATE_TIMEc";
        map.put("HM_CREATE_TIMEc",ExtendDate.getYMD_h_m_s(cc));
        int ic=sysDao.getServiceReadDo().findCount(sqlc,map);
        rmap.put("近一季度",ic);
        //近半年
        Calendar cg=Calendar.getInstance();
        cg.add(Calendar.MONTH,-6);
        String sqlg=sql+" AND a.HM_CREATE_TIME>:HM_CREATE_TIMEg";
        map.put("HM_CREATE_TIMEg",ExtendDate.getYMD_h_m_s(cg));
        int icg=sysDao.getServiceReadDo().findCount(sqlg,map);
        rmap.put("近半年",icg);
        //近一年
        Calendar cd=Calendar.getInstance();
        cd.add(Calendar.MONTH,-12);
        String sqld=sql+" AND a.HM_CREATE_TIME>:HM_CREATE_TIMEd";
        map.put("HM_CREATE_TIMEd",ExtendDate.getYMD_h_m_s(cd));
        int id=sysDao.getServiceReadDo().findCount(sqld,map);
        rmap.put("近一年",id);
        //当年每月干预人次
        List<String> lsTitle = new ArrayList<String>();
        List<Integer> lsValue = new ArrayList<Integer>();
        Calendar one= ExtendDate.getCalendar(ExtendDate.getYYYY(Calendar.getInstance())+"-01-01");
        for (int i=0;i<12;i++) {
            Calendar end=ExtendDate.getCalendar(ExtendDate.getYYYY(Calendar.getInstance())+"-01-01");
            end.set(Calendar.DAY_OF_MONTH, one.getActualMaximum(Calendar.DAY_OF_MONTH));
            end.add(Calendar.MONTH,i);
            String sqlf=sql+" AND a.HM_CREATE_TIME>:HM_CREATE_TIME_FROM";
            map.put("HM_CREATE_TIME_FROM",ExtendDate.getYMD_h_m_s(one));
            sqlf +=" AND a.HM_CREATE_TIME<:HM_CREATE_TIME_TO";
            map.put("HM_CREATE_TIME_TO",ExtendDate.getYMD(end)+" 23:59:59");
            int ff=sysDao.getServiceReadDo().findCount(sqlf,map);
            int m=i+1;
            lsTitle.add(m+"月");
            lsValue.add(ff);
        }
        rmap.put("lsTitle",lsTitle);
        rmap.put("lsValue",lsValue);
        return rmap;
    }

    /**
     * 拒管居民动态
     * @param qvo hospId 医院主键 drId 医生主键 startYyyyMm 开始月份 endYyyyMm 结束月份
     */
    public Map findRefuseSign(PerformanceVo qvo) throws Exception{
        HashMap rmap = new HashMap();
        //拒管人数
        String jsql="SELECT COUNT(1) FROM APP_REFUSE_SIGN a WHERE 1=1 AND a.RS_SIGN_TIME IS NULL ";
        //拒转至签人数
        String qsql="SELECT COUNT(1) FROM APP_REFUSE_SIGN a WHERE 1=1 AND a.RS_SIGN_TIME IS not NULL ";
        HashMap map = new HashMap();
        if(StringUtils.isNotBlank(qvo.getDrId())){
            jsql +=" AND a.RS_REFUSE_DR_ID =:RS_REFUSE_DR_ID";
            map.put("RS_REFUSE_DR_ID",qvo.getDrId());
            qsql +=" AND a.RS_SIGN_DR_ID = :RS_SIGN_DR_ID";
            map.put("RS_SIGN_DR_ID",qvo.getDrId());
        }
        if(StringUtils.isNotBlank(qvo.getHospId())){
            jsql +=" AND a.RS_REFUSE_HOSP_ID =:RS_REFUSE_HOSP_ID";
            map.put("RS_REFUSE_HOSP_ID",qvo.getHospId());
            qsql +=" AND a.RS_SIGN_HOSP_ID =:RS_SIGN_HOSP_ID";
            map.put("RS_SIGN_HOSP_ID",qvo.getHospId());
        }
        //
        if(StringUtils.isNotBlank(qvo.getStartYyyyMm())){
            jsql +=" AND a.RS_REFUSE_TIME>:RS_REFUSE_TIME_FROM";
            map.put("RS_REFUSE_TIME_FROM",qvo.getStartYyyyMm()+"-01 00:00:00");
            qsql +=" AND a.RS_SIGN_TIME>:RS_SIGN_TIME_FROM";
            map.put("RS_SIGN_TIME_FROM",qvo.getStartYyyyMm()+"-01 00:00:00");
        }
        if(StringUtils.isNotBlank(qvo.getEndYyyyMm())){
            Calendar end=ExtendDate.getCalendar(qvo.getEndYyyyMm()+"-01");
            end.set(Calendar.DAY_OF_MONTH, end.getActualMaximum(Calendar.DAY_OF_MONTH));
            jsql +=" AND a.RS_REFUSE_TIME<:RS_REFUSE_TIME_TO";
            map.put("RS_REFUSE_TIME_TO",ExtendDate.getYMD(end)+" 23:59:59");
            qsql +=" AND a.RS_SIGN_TIME<:RS_SIGN_TIME_TO";
            map.put("RS_SIGN_TIME_TO",ExtendDate.getYMD(end)+" 23:59:59");
        }
        int jsqli=sysDao.getServiceReadDo().findCount(jsql,map);
        rmap.put("拒管人数",jsqli);
        int qsqli=sysDao.getServiceReadDo().findCount(qsql,map);
        rmap.put("拒转至签人数",qsqli);
        List<String> jlsTitle = new ArrayList<String>();
        List<Integer> jlsValue = new ArrayList<Integer>();
        List<String> zlsTitle = new ArrayList<String>();
        List<Integer> zlsValue = new ArrayList<Integer>();
        if(StringUtils.isNotBlank(qvo.getStartYyyyMm()) && StringUtils.isNotBlank(qvo.getEndYyyyMm())) {
            List<Map<String,String>> lsMap = ExtendDateUtil.getListBetweenMonthMap(qvo.getStartYyyyMm(),qvo.getEndYyyyMm());
            for (int i=0;i<lsMap.size();i++) {
                Map<String,String> mapString = lsMap.get(i);
                map.put("RS_REFUSE_TIME_FROM",mapString.get("monthStart"));
                map.put("RS_SIGN_TIME_FROM",mapString.get("monthStart"));
                map.put("RS_REFUSE_TIME_TO",mapString.get("monthEnd"));
                map.put("RS_SIGN_TIME_TO",mapString.get("monthEnd"));
                int jsqlif=sysDao.getServiceReadDo().findCount(jsql,map);
                int qsqlif=sysDao.getServiceReadDo().findCount(qsql,map);
                jlsTitle.add(mapString.get("month"));
                jlsValue.add(jsqlif);
                zlsTitle.add(mapString.get("month"));
                zlsValue.add(qsqlif);
            }
        }
        rmap.put("jlsTitle",jlsTitle);
        rmap.put("jlsValue",jlsValue);
        rmap.put("zlsTitle",zlsTitle);
        rmap.put("zlsValue",zlsValue);
        return rmap;
    }

    /**
     * 随访工作量统计
     * @param qvo hospId 医院主键 drId 医生主键
     * @return
     */
    @Override
    public Map<String, Object> findFollowWorkPlanCount(PerformanceVo qvo) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> mapCount = new HashMap<String, Object>();
        //区域统计
//        if (StringUtils.isNotBlank(qvo.getAreaId())) {
//            return mapCount;
//        }
            //医院统计
//        if (StringUtils.isNotBlank(qvo.getHospId())) {
//            map.put("hospId", qvo.getHospId());
//            String toDay = ExtendDate.getYMD(Calendar.getInstance());
//            //一周前已随访人次
//            String beforeDay = ExtendDateUtil.minusDate(toDay, ExtendDateType.DAYS.getValue(), 7);
//            map.put("beforeDateStart", beforeDay + " 00:00:00");
//            map.put("beforeDateEnd", toDay + " 23:59:59");
//            map.put("ywcState", FollowPlanState.YIWANCHENG.getValue());
//            String sqlYsfrc = "SELECT\n" +
//                    "\tcount(1)\n" +
//                    "FROM\n" +
//                    "\tAPP_FOLLOW_PLAN t\n" +
//                    "WHERE\n" +
//                    "\tt.SF_FOLLOW_STATE = :ywcState\n" +
//                    "AND t.SF_FOLLOW_DATE >= :beforeDateStart\n" +
//                    "AND t.SF_FOLLOW_DATE <= :beforeDateEnd\n" +
//                    "AND t.SF_HOS_ID = :hospId";
//            int ysfrc = this.sysDao.getServiceReadDo().findCount(sqlYsfrc, map);
//            mapCount.put("一周前已随访", ysfrc);
//
//            //一周后应随访人次
//            String afterDay = ExtendDateUtil.addDate(toDay, ExtendDateType.DAYS.getValue(), 7);
//            map.put("afterDateStart", toDay + " 00:00:00");
//            map.put("afterDateEnd", afterDay + " 23:59:59");
//            map.put("wwcState", FollowPlanState.WEIWANCHENG.getValue());
//            String sqlWwc = "SELECT\n" +
//                    "\tcount(1)\n" +
//                    "FROM\n" +
//                    "\tAPP_FOLLOW_PLAN t\n" +
//                    "WHERE\n" +
//                    "\tt.SF_FOLLOW_STATE = :wwcState\n" +
//                    "AND t.SF_FOLLOW_DATE >= :afterDateStart\n" +
//                    "AND t.SF_FOLLOW_DATE <= :afterDateEnd\n" +
//                    "AND t.SF_HOS_ID = :hospId";
//            int wwcrc = this.sysDao.getServiceReadDo().findCount(sqlWwc, map);
//            mapCount.put("一周后应随访", wwcrc);
//
//            //一个月
//            String beforeDayMonth = ExtendDateUtil.minusDate(toDay, ExtendDateType.DAYS.getValue(), 30);
//            map.put("beforeDayMonthStart", beforeDayMonth + " 00:00:00");
//            map.put("beforeDayMonthEnd", toDay + " 23:59:59");
//            map.put("ywcState", FollowPlanState.YIWANCHENG.getValue());
//            String sqlMonth = "SELECT\n" +
//                    "\tcount(1)\n" +
//                    "FROM\n" +
//                    "\tAPP_FOLLOW_PLAN t\n" +
//                    "WHERE\n" +
//                    "\tt.SF_FOLLOW_STATE = :ywcState\n" +
//                    "AND t.SF_FOLLOW_DATE >= :beforeDayMonthStart\n" +
//                    "AND t.SF_FOLLOW_DATE <= :beforeDayMonthEnd\n" +
//                    "AND t.SF_HOS_ID = :hospId";
//            int month = this.sysDao.getServiceReadDo().findCount(sqlMonth, map);
//            mapCount.put("近一个月", month);
//
//            //一个季度
//            String beforeDaySeason = ExtendDateUtil.minusDate(toDay, ExtendDateType.DAYS.getValue(), 90);
//            map.put("beforeDaySeasonStart", beforeDaySeason + " 00:00:00");
//            map.put("beforeDaySeasonEnd", toDay + " 23:59:59");
//            map.put("ywcState", FollowPlanState.YIWANCHENG.getValue());
//            String sqlSeason = "SELECT\n" +
//                    "\tcount(1)\n" +
//                    "FROM\n" +
//                    "\tAPP_FOLLOW_PLAN t\n" +
//                    "WHERE\n" +
//                    "\tt.SF_FOLLOW_STATE = :ywcState\n" +
//                    "AND t.SF_FOLLOW_DATE >= :beforeDaySeasonStart\n" +
//                    "AND t.SF_FOLLOW_DATE <= :beforeDaySeasonEnd\n" +
//                    "AND t.SF_HOS_ID = :hospId";
//            int season = this.sysDao.getServiceReadDo().findCount(sqlSeason, map);
//            mapCount.put("近一季度", season);
//
//            //半年
//            String beforeDayHalfYear = ExtendDateUtil.minusDate(toDay, ExtendDateType.DAYS.getValue(), 180);
//            map.put("beforeDayHalfYearStart", beforeDayHalfYear + " 00:00:00");
//            map.put("beforeDayHalfYearEnd", toDay + " 23:59:59");
//            map.put("ywcState", FollowPlanState.YIWANCHENG.getValue());
//            String sqlHalfYear = "SELECT\n" +
//                    "\tcount(1)\n" +
//                    "FROM\n" +
//                    "\tAPP_FOLLOW_PLAN t\n" +
//                    "WHERE\n" +
//                    "\tt.SF_FOLLOW_STATE = :ywcState\n" +
//                    "AND t.SF_FOLLOW_DATE >= :beforeDayHalfYearStart\n" +
//                    "AND t.SF_FOLLOW_DATE <= :beforeDayHalfYearEnd\n" +
//                    "AND t.SF_HOS_ID = :hospId";
//            int halfYear = this.sysDao.getServiceReadDo().findCount(sqlHalfYear, map);
//            mapCount.put("近半年", halfYear);
//
//            //近一年
//            String beforeDayYear = ExtendDateUtil.minusDate(toDay, ExtendDateType.DAYS.getValue(), 365);
//            map.put("beforeDayYearStart", beforeDayYear + " 00:00:00");
//            map.put("beforeDayYearEnd", toDay + " 23:59:59");
//            map.put("ywcState", FollowPlanState.YIWANCHENG.getValue());
//            String sqlYear = "SELECT\n" +
//                    "\tcount(1)\n" +
//                    "FROM\n" +
//                    "\tAPP_FOLLOW_PLAN t\n" +
//                    "WHERE\n" +
//                    "\tt.SF_FOLLOW_STATE = :ywcState\n" +
//                    "AND t.SF_FOLLOW_DATE >= :beforeDayYearStart\n" +
//                    "AND t.SF_FOLLOW_DATE <= :beforeDayYearEnd\n" +
//                    "AND t.SF_HOS_ID = :hospId";
//            int year = this.sysDao.getServiceReadDo().findCount(sqlYear, map);
//            mapCount.put("近一年", year);
//
//            //一年统计
//            Calendar one = ExtendDate.getCalendar(ExtendDate.getYYYY(Calendar.getInstance()) + "-01-01");
//            map.put("startYear", ExtendDate.getYMD_h_m_s(one));
//            List<String> lsTitle = new ArrayList<String>();
//            List<Integer> lsValue = new ArrayList<Integer>();
//            for (int i = 1; i <= 12; i++) {
//                Calendar end = ExtendDate.getCalendar(ExtendDate.getYYYY(Calendar.getInstance()) + "-01-01");
//                end.set(Calendar.DAY_OF_MONTH, one.getActualMaximum(Calendar.DAY_OF_MONTH));
//                end.add(Calendar.MONTH, i);
//                map.put("endYear", ExtendDate.getYMD(end) + " 23:59:59");
//                String sqlOne = "SELECT\n" +
//                        "\tcount(1)\n" +
//                        "FROM\n" +
//                        "\tAPP_FOLLOW_PLAN t\n" +
//                        "WHERE\n" +
//                        "\tt.SF_FOLLOW_STATE = :ywcState\n" +
//                        "AND t.SF_FOLLOW_DATE >= :startYear\n" +
//                        "AND t.SF_FOLLOW_DATE <= :endYear\n" +
//                        "AND t.SF_HOS_ID = :hospId";
//                int oneValue = this.sysDao.getServiceReadDo().findCount(sqlOne, map);
//                lsTitle.add(i + "月");
//                lsValue.add(oneValue);
//            }
//            mapCount.put("lsTitle", lsTitle);
//            mapCount.put("lsValue", lsValue);
//            return mapCount;
//        }
//
//        //团队统计
//        if (StringUtils.isNotBlank(qvo.getTeamId())) {
//
//            return mapCount;
//        }

        //医生统计
//        if (StringUtils.isNotBlank(qvo.getDrId())) {
            map.put("drId", qvo.getDrId());
            String toDay = ExtendDate.getYMD(Calendar.getInstance());
            //一周前已随访人次
            String beforeDay = ExtendDateUtil.minusDate(toDay, ExtendDateType.DAYS.getValue(), 7);
            map.put("beforeDateStart", beforeDay + " 00:00:00");
            map.put("beforeDateEnd", toDay + " 23:59:59");
            map.put("ywcState", FollowPlanState.YIWANCHENG.getValue());
            String sqlYsfrc = "SELECT\n" +
                    "\tcount(1)\n" +
                    "FROM\n" +
                    "\tAPP_FOLLOW_PLAN t\n" +
                    "WHERE\n" +
                    "\tt.SF_FOLLOW_STATE = :ywcState\n" +
                    "AND t.SF_FOLLOW_DATE >= :beforeDateStart\n" +
                    "AND t.SF_FOLLOW_DATE <= :beforeDateEnd\n" +
                    "AND t.SF_FOLLOW_DOCTORID = :drId";
            int ysfrc = this.sysDao.getServiceReadDo().findCount(sqlYsfrc, map);
            mapCount.put("一周前已随访", ysfrc);

            //一周后应随访人次
            String afterDay = ExtendDateUtil.addDate(toDay, ExtendDateType.DAYS.getValue(), 7);
            map.put("afterDateStart", toDay + " 00:00:00");
            map.put("afterDateEnd", afterDay + " 23:59:59");
            map.put("wwcState", FollowPlanState.WEIWANCHENG.getValue());
            String sqlWwc = "SELECT\n" +
                    "\tcount(1)\n" +
                    "FROM\n" +
                    "\tAPP_FOLLOW_PLAN t\n" +
                    "WHERE\n" +
                    "\tt.SF_FOLLOW_STATE = :wwcState\n" +
                    "AND t.SF_FOLLOW_DATE >= :afterDateStart\n" +
                    "AND t.SF_FOLLOW_DATE <= :afterDateEnd\n" +
                    "AND t.SF_FOLLOW_DOCTORID = :drId";
            int wwcrc = this.sysDao.getServiceReadDo().findCount(sqlWwc, map);
            mapCount.put("一周后应随访", wwcrc);

            //一个月
            String beforeDayMonth = ExtendDateUtil.minusDate(toDay, ExtendDateType.DAYS.getValue(), 30);
            map.put("beforeDayMonthStart", beforeDayMonth + " 00:00:00");
            map.put("beforeDayMonthEnd", toDay + " 23:59:59");
            map.put("ywcState", FollowPlanState.YIWANCHENG.getValue());
            String sqlMonth = "SELECT\n" +
                    "\tcount(1)\n" +
                    "FROM\n" +
                    "\tAPP_FOLLOW_PLAN t\n" +
                    "WHERE\n" +
                    "\tt.SF_FOLLOW_STATE = :ywcState\n" +
                    "AND t.SF_FOLLOW_DATE >= :beforeDayMonthStart\n" +
                    "AND t.SF_FOLLOW_DATE <= :beforeDayMonthEnd\n" +
                    "AND t.SF_FOLLOW_DOCTORID = :drId";
            int month = this.sysDao.getServiceReadDo().findCount(sqlMonth, map);
            mapCount.put("近一个月", month);

            //一个季度
            String beforeDaySeason = ExtendDateUtil.minusDate(toDay, ExtendDateType.DAYS.getValue(), 90);
            map.put("beforeDaySeasonStart", beforeDaySeason + " 00:00:00");
            map.put("beforeDaySeasonEnd", toDay + " 23:59:59");
            map.put("ywcState", FollowPlanState.YIWANCHENG.getValue());
            String sqlSeason = "SELECT\n" +
                    "\tcount(1)\n" +
                    "FROM\n" +
                    "\tAPP_FOLLOW_PLAN t\n" +
                    "WHERE\n" +
                    "\tt.SF_FOLLOW_STATE = :ywcState\n" +
                    "AND t.SF_FOLLOW_DATE >= :beforeDaySeasonStart\n" +
                    "AND t.SF_FOLLOW_DATE <= :beforeDaySeasonEnd\n" +
                    "AND t.SF_FOLLOW_DOCTORID = :drId";
            int season = this.sysDao.getServiceReadDo().findCount(sqlSeason, map);
            mapCount.put("近一季度", season);

            //半年
            String beforeDayHalfYear = ExtendDateUtil.minusDate(toDay, ExtendDateType.DAYS.getValue(), 180);
            map.put("beforeDayHalfYearStart", beforeDayHalfYear + " 00:00:00");
            map.put("beforeDayHalfYearEnd", toDay + " 23:59:59");
            map.put("ywcState", FollowPlanState.YIWANCHENG.getValue());
            String sqlHalfYear = "SELECT\n" +
                    "\tcount(1)\n" +
                    "FROM\n" +
                    "\tAPP_FOLLOW_PLAN t\n" +
                    "WHERE\n" +
                    "\tt.SF_FOLLOW_STATE = :ywcState\n" +
                    "AND t.SF_FOLLOW_DATE >= :beforeDayHalfYearStart\n" +
                    "AND t.SF_FOLLOW_DATE <= :beforeDayHalfYearEnd\n" +
                    "AND t.SF_FOLLOW_DOCTORID = :drId";
            int halfYear = this.sysDao.getServiceReadDo().findCount(sqlHalfYear, map);
            mapCount.put("近半年", halfYear);

            //近一年
            String beforeDayYear = ExtendDateUtil.minusDate(toDay, ExtendDateType.DAYS.getValue(), 365);
            map.put("beforeDayYearStart", beforeDayYear + " 00:00:00");
            map.put("beforeDayYearEnd", toDay + " 23:59:59");
            map.put("ywcState", FollowPlanState.YIWANCHENG.getValue());
            String sqlYear = "SELECT\n" +
                    "\tcount(1)\n" +
                    "FROM\n" +
                    "\tAPP_FOLLOW_PLAN t\n" +
                    "WHERE\n" +
                    "\tt.SF_FOLLOW_STATE = :ywcState\n" +
                    "AND t.SF_FOLLOW_DATE >= :beforeDayYearStart\n" +
                    "AND t.SF_FOLLOW_DATE <= :beforeDayYearEnd\n" +
                    "AND t.SF_FOLLOW_DOCTORID = :drId";
            int year = this.sysDao.getServiceReadDo().findCount(sqlYear, map);
            mapCount.put("近一年", year);

            //一年统计
            Calendar one = ExtendDate.getCalendar(ExtendDate.getYYYY(Calendar.getInstance()) + "-01-01");
            map.put("startYear", ExtendDate.getYMD_h_m_s(one));
            List<String> lsTitle = new ArrayList<String>();
            List<Integer> lsValue = new ArrayList<Integer>();
            for (int i = 1; i <= 12; i++) {
                Calendar end = ExtendDate.getCalendar(ExtendDate.getYYYY(Calendar.getInstance()) + "-01-01");
                end.set(Calendar.DAY_OF_MONTH, one.getActualMaximum(Calendar.DAY_OF_MONTH));
                end.add(Calendar.MONTH, i);
                map.put("endYear", ExtendDate.getYMD(end) + " 23:59:59");
                String sqlOne = "SELECT\n" +
                        "\tcount(1)\n" +
                        "FROM\n" +
                        "\tAPP_FOLLOW_PLAN t\n" +
                        "WHERE\n" +
                        "\tt.SF_FOLLOW_STATE = :ywcState\n" +
                        "AND t.SF_FOLLOW_DATE >= :startYear\n" +
                        "AND t.SF_FOLLOW_DATE <= :endYear\n" +
                        "AND t.SF_FOLLOW_DOCTORID = :drId";
                int oneValue = this.sysDao.getServiceReadDo().findCount(sqlOne, map);
                lsTitle.add(i + "月");
                lsValue.add(oneValue);
            }
            mapCount.put("lsTitle", lsTitle);
            mapCount.put("lsValue", lsValue);
            return mapCount;
//        }
//        return mapCount;
    }


    /**
     * 团队排名 工作进度，随访，健康干预
     * @param qvo hospId 社区主键
     * @return
     */
    public Map findTeamTop(PerformanceVo qvo) throws Exception{
        HashMap rmap = new HashMap();
        HashMap map2 = new HashMap();
        List<TeamLsitVo> ls=new ArrayList<TeamLsitVo>();//综合
        List<TeamLsitVo> ls2=new ArrayList<TeamLsitVo>();//工作进度完成率
        List<TeamLsitVo> ls3=new ArrayList<TeamLsitVo>();//随访完成率
        List<TeamLsitVo> ls4=new ArrayList<TeamLsitVo>();//健康干预率

        //查询医院团队
        String sqlteam="SELECT * from APP_TEAM a where a.TEAM_HOSP_ID=:TEAM_HOSP_ID AND a.TEAM_DEL_STATE = '0' ";
        map2.put("TEAM_HOSP_ID",qvo.getHospId());
        List<AppTeam> lsteam=sysDao.getServiceReadDo().findSqlMap(sqlteam,map2, AppTeam.class);
        if(lsteam!=null && !lsteam.isEmpty()){
            for(AppTeam team:lsteam){
                HashMap map = new HashMap();
                map.put("teamId",team.getId());
                TeamLsitVo v=new TeamLsitVo();//综合
                TeamLsitVo v2=new TeamLsitVo();//工作进度完成率
                TeamLsitVo v3=new TeamLsitVo();//随访完成率
                TeamLsitVo v4=new TeamLsitVo();//健康干预率
                TeamLsitVo v5=new TeamLsitVo();//健康干预率
                //团队名称
                v.setTeamName(team.getTeamName());
                v2.setTeamName(team.getTeamName());//团队名称
                v3.setTeamName(team.getTeamName());//团队名称
                v4.setTeamName(team.getTeamName());//团队名称
                int zgzl=0;
                int ywcl=0;
                int zsf=0;
                int wsf=0;
                int wgsql=0;
                int zssql=0;
                //工作进度----开始
                String sqlwork =  "SELECT count(1) FROM APP_WORKING_PLAN WHERE 1=1 and PLAN_TYPE='1'" ;
                sqlwork += " AND PLAN_TEAM_ID = :teamId ";
                //总工作量
                zgzl = this.sysDao.getServiceReadDo().findCount(sqlwork,map);
                //已完成
                String sqlYwcl = sqlwork + " AND PLAN_STATE ='2' ";
                ywcl = this.sysDao.getServiceReadDo().findCount(sqlYwcl,map);
                double wcl = 0;
                //完成率
                if(zgzl > 0 ){
//                    wcl = MyMathUtil.div(Double.valueOf(ywcl),Double.valueOf(zgzl),2)*100;//完成率
                    wcl = new BigDecimal(ywcl*100).divide(new BigDecimal(zgzl),0,BigDecimal.ROUND_HALF_UP).doubleValue();
                }
                v2.setTeamYCount(String.valueOf(wcl));
                //工作进度----结束

                //随访----开始
                String sqlFoll="SELECT count(1) FROM APP_FOLLOW_PLAN t WHERE 1=1 ";
                //总工作量
                sqlFoll += " AND t.SF_TEAM_ID = :teamId ";
                zsf = this.sysDao.getServiceReadDo().findCount(sqlFoll,map);
                //完成数
                String[] followState = new String[]{FollowPlanState.YIWANCHENG.getValue(),FollowPlanState.SIWANG.getValue(),FollowPlanState.SHIFANG.getValue()};
                map.put("followState",followState);
                String sqlFollw=sqlFoll+" AND t.SF_FOLLOW_STATE IN :followState ";
                wsf = this.sysDao.getServiceReadDo().findCount(sqlFollw,map);
                //完成率
                double swcl = 0;
                if(zsf > 0 ){
//                    swcl = MyMathUtil.div(Double.valueOf(wsf),Double.valueOf(zsf),2)*100;//完成率
                    swcl = new BigDecimal(wsf*100).divide(new BigDecimal(zsf),0,BigDecimal.ROUND_HALF_UP).doubleValue();
                }
                v3.setTeamYCount(String.valueOf(swcl));
                //随访----结束

                //健康干预----开始
                String sqlwork2 =  "SELECT count(1) FROM APP_WORKING_PLAN WHERE 1=1 and PLAN_TYPE='2'" ;
                sqlwork2 += " AND PLAN_TEAM_ID = :teamId ";
                zssql=this.sysDao.getServiceReadDo().findCount(sqlwork2,map);
                //完成数
                String gsql="SELECT COUNT(1) FROM APP_HEALTH_MEDDLE a WHERE 1=1 AND a.HM_GUIDE_TYPE = '2' ";
                gsql+=" AND a.HM_TEAM_ID=:teamId ";
                wgsql=this.sysDao.getServiceReadDo().findCount(gsql,map);
                //完成率
                double gwcl = 0;
                if(zssql > 0 && wgsql >0){
                    gwcl = new BigDecimal(wgsql*100).divide(new BigDecimal(zssql),0,BigDecimal.ROUND_HALF_UP).doubleValue();
                }
                v4.setTeamYCount(String.valueOf(gwcl));
                //健康干预----结束


                double zywcl=MyMathUtil.div((wcl+swcl+gwcl),3,2);//已完成率
                v.setTeamYCount(String.valueOf(zywcl));


                ls.add(v);
                ls2.add(v2);
                ls3.add(v3);
                ls4.add(v4);
            }
        }
        Collections.sort(ls, new Comparator<TeamLsitVo>() {
            public int compare(TeamLsitVo o1, TeamLsitVo o2) {
                if (Double.valueOf(o1.getTeamYCount()) < Double.valueOf(o2.getTeamYCount())) {
                    return 1;
                }
                if (o1.getTeamYCount() == o2.getTeamYCount()) {
                    return 0;
                }
                return -1;
            }});
        rmap.put("sum",ls);
        Collections.sort(ls2, new Comparator<TeamLsitVo>() {
            public int compare(TeamLsitVo o1, TeamLsitVo o2) {
                if (Double.valueOf(o1.getTeamYCount()) < Double.valueOf(o2.getTeamYCount())) {
                    return 1;
                }
                if (o1.getTeamYCount() == o2.getTeamYCount()) {
                    return 0;
                }
                return -1;
            }});
        rmap.put("working",ls2);
        Collections.sort(ls3, new Comparator<TeamLsitVo>() {
            public int compare(TeamLsitVo o1, TeamLsitVo o2) {
                if (Double.valueOf(o1.getTeamYCount()) < Double.valueOf(o2.getTeamYCount())) {
                    return 1;
                }
                if (o1.getTeamYCount() == o2.getTeamYCount()) {
                    return 0;
                }
                return -1;
            }});
        rmap.put("follow",ls3);
        Collections.sort(ls4, new Comparator<TeamLsitVo>() {
            public int compare(TeamLsitVo o1, TeamLsitVo o2) {
                if (Double.valueOf(o1.getTeamYCount()) < Double.valueOf(o2.getTeamYCount())) {
                    return 1;
                }
                if (o1.getTeamYCount() == o2.getTeamYCount()) {
                    return 0;
                }
                return -1;
            }});
        rmap.put("healthMeddle",ls4);

        return rmap;
    }



    public Map findTeamNo(PerformanceVo qvo) throws Exception{
        HashMap rmap = new HashMap();
        HashMap map = new HashMap();
        List<TeamLsitVo> ls=new ArrayList<TeamLsitVo>();//

        //查询医院团队
        String sqlteam="SELECT * from APP_TEAM a where a.TEAM_HOSP_ID=:TEAM_HOSP_ID AND a.TEAM_DEL_STATE = '0' ";
        map.put("TEAM_HOSP_ID",qvo.getHospId());
        List<AppTeam> lsteam=sysDao.getServiceReadDo().findSqlMap(sqlteam,map, AppTeam.class);
        String endDate=ExtendDate.getYMD(Calendar.getInstance())+" 23:59:59";
        map.put("endDate",endDate);
        if(lsteam!=null && !lsteam.isEmpty()){
            for(AppTeam team:lsteam){
                map.put("teamId",team.getId());
                TeamLsitVo v=new TeamLsitVo();
                v.setTeamName(team.getTeamName());//团队名称
                //统计七天 工作进度，随访，健康干预
                List<String> teamDate=new ArrayList<String>();//日期
                List<String> teamWork=new ArrayList<String>();//工作进度完成率
                List<String> teamFollow=new ArrayList<String>();//随访完成率
                List<String> teamMeddle=new ArrayList<String>();//健康干预率
                for (int i=7;i>0;i--) {

                    Calendar c=Calendar.getInstance();
                    c.add(Calendar.DATE,-i);
                    teamDate.add(ExtendDate.getYMD(c));//日期
                    String startDate=ExtendDate.getYMD(c)+" 00:00:00";
                    //工作进度----开始
                    String sqlwork =  "SELECT count(1) FROM APP_WORKING_PLAN WHERE 1=1  AND PLAN_DATE>= :startDate  AND PLAN_DATE <= :endDate " ;
                    map.put("startDate",startDate);
                    sqlwork += " AND PLAN_TEAM_ID = :teamId ";
                    //总工作量
                    int zgzl = this.sysDao.getServiceReadDo().findCount(sqlwork,map);
                    //已完成
                    String sqlYwcl = sqlwork + " AND PLAN_STATE ='2' ";
                    int ywcl = this.sysDao.getServiceReadDo().findCount(sqlYwcl,map);
                    double wcl = 0;
                    //完成率
                    if(zgzl > 0 ){
//                        wcl = MyMathUtil.div(Double.valueOf(ywcl),Double.valueOf(zgzl),2)*100;//完成率
                        wcl = new BigDecimal(ywcl*100).divide(new BigDecimal(zgzl),0,BigDecimal.ROUND_HALF_UP).doubleValue();
                    }
                    teamWork.add(String.valueOf(wcl));
                    //工作进度----结束

                    //随访----开始
                    String sqlFoll="SELECT count(1) FROM APP_FOLLOW_PLAN t WHERE 1=1 AND t.SF_FOLLOW_DATE >= :startDate AND t.SF_FOLLOW_DATE < :endDate";
                    //总工作量
                    sqlFoll += " AND t.SF_TEAM_ID = :teamId ";
                    int zsf = this.sysDao.getServiceReadDo().findCount(sqlFoll,map);
                    //完成数
                    String sqlFollw=sqlFoll+" AND t.SF_FOLLOW_STATE = 1";
                    int wsf = this.sysDao.getServiceReadDo().findCount(sqlFollw,map);
                    //完成率
                    double swcl = 0;
                    if(zsf > 0 ){
//                        swcl = MyMathUtil.div(Double.valueOf(wsf),Double.valueOf(zsf),2)*100;//完成率
                        swcl = new BigDecimal(wsf*100).divide(new BigDecimal(zsf),0,BigDecimal.ROUND_HALF_UP).doubleValue();
                    }
                    teamFollow.add(String.valueOf(swcl));
                    //随访----结束

                    //健康干预----开始
                    //完成数
                    String gsql="SELECT COUNT(1) FROM APP_HEALTH_MEDDLE a WHERE 1=1 AND a.HM_CREATE_TIME>:startDate AND a.HM_CREATE_TIME<:endDate";
                    gsql+=" AND a.HM_TEAM_ID=:teamId ";
                    int wgsql=this.sysDao.getServiceReadDo().findCount(gsql,map);
                    //完成率
                    double gwcl = 0;
                    if(zsf > 0 ){
//                        gwcl = MyMathUtil.div(Double.valueOf(gwcl),Double.valueOf(zsf),2)*100;//完成率
                        gwcl = new BigDecimal(gwcl*100).divide(new BigDecimal(zsf),0,BigDecimal.ROUND_HALF_UP).doubleValue();
                    }
                    teamMeddle.add(String.valueOf(gwcl));
                    //健康干预----结束

                    if(i==1){
                        double zywcl=(wcl+swcl+gwcl)/3*100;//已完成率
                        double wwcl=0;
                        double wswcl=0;
                        double wgwcl=0;
                        if(wcl>0){
                            wwcl=100-wcl;
                        }
                        if(swcl>0){
                            wswcl=100-swcl;
                        }
                        if(gwcl>0){
                            wgwcl=100-gwcl;
                        }
                        double zwwcl=(wwcl+wswcl+wgwcl)/3*100;//未完成率
                        v.setTeamYCount(String.valueOf(zywcl));
                        v.setTeamWcount(String.valueOf(zwwcl));
                    }
                }
                v.setTeamDate(teamDate);
                v.setTeamWork(teamWork);
                v.setTeamFollow(teamFollow);
                v.setTeamMeddle(teamMeddle);
                ls.add(v);
            }
        }

        rmap.put("teamls",ls);

        return rmap;
    }

    /**
     * 随访任务量统计
     * @param qvo hospId 医院主键 drId 医生主键
     * @return
     * @throws DaoException
     */
    @Override
    public Map<String, Object> findByTime(PerformanceVo qvo) throws Exception  {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> mapXy = new HashMap<String, Object>();
        Map<String, Object> mapXt = new HashMap<String, Object>();
        Map<String, Object> mapCount = new HashMap<String, Object>();
        map.put("startDate", qvo.getStartDate()+ " 00:00:00");
        map.put("endDate", qvo.getEndDate()+ " 23:59:59");
        map.put("ywcState", FollowPlanState.YIWANCHENG.getValue());
        //已随访
        String sqlYjsf = "SELECT\n" +
                "\tcount(1)\n" +
                "FROM\n" +
                "\tAPP_FOLLOW_PLAN t\n" +
                "WHERE\n" +
                "\tt.SF_FOLLOW_STATE = :ywcState\n" +
                "AND t.SF_FOLLOW_DATE >= :startDate\n" +
                "AND t.SF_FOLLOW_DATE <= :endDate " ;
        //应随访
        String sqlYsf= "SELECT\n" +
                "\tcount(1)\n" +
                "FROM\n" +
                "\tAPP_FOLLOW_PLAN t\n" +
                "WHERE\n" +
                " t.SF_FOLLOW_DATE >= :startDate\n" +
                "AND t.SF_FOLLOW_DATE <= :endDate " ;


        //区域统计
//        if (StringUtils.isNotBlank(qvo.getAreaId())) {
//            return mapCount;
//        }
        //医院统计
//        if (StringUtils.isNotBlank(qvo.getHospId())) {
//            map.put("hospId", qvo.getHospId());
//            //血压统计
//            String sqlXyYjsf = sqlYjsf + " AND t.SF_FOLLOW_TYPE = '0' AND t.SF_HOS_ID = :hospId";//已随访
//            String sqlXyYsf = sqlYsf+ " AND t.SF_FOLLOW_TYPE = '0' AND t.SF_HOS_ID = :hospId";//应随访
//
//            int xyYjsf = this.sysDao.getServiceReadDo().findCount(sqlXyYjsf,map);//血压已随访
//            int xyYsf = this.sysDao.getServiceReadDo().findCount(sqlXyYsf,map);//血压应随访
//            double xyWcl = 0;
//            if(xyYsf > 0 ){
//                xyWcl = MyMathUtil.div(Double.valueOf(xyYjsf),Double.valueOf(xyYsf),2)*100;//完成率
//            }
//            mapXy.put("xyYjsf",xyYjsf);//已随访
//            mapXy.put("xyYsf",xyYsf);//已应随访
//            mapXy.put("xyWcl",xyWcl);//完成率
//
//            int daysBetween = ExtendDate.daysBetween(qvo.getStartDate(),qvo.getEndDate());
//            Map<String,Object> xy = this.getMapSfRw(daysBetween,sqlXyYjsf,sqlXyYsf,qvo.getStartDate(),null,null,qvo.getHospId());
//            mapXy.putAll(xy);
//
//            //血糖
//            String sqlXtYjsf = sqlYjsf + " AND t.SF_FOLLOW_TYPE = '1' AND t.SF_HOS_ID = :hospId";//已随访
//            String sqlXtYsf = sqlYsf+ " AND t.SF_FOLLOW_TYPE = '1' AND t.SF_HOS_ID = :hospId";//应随访
//
//            int xtYjsf = this.sysDao.getServiceReadDo().findCount(sqlXtYjsf,map);//血压已随访
//            int xtYsf = this.sysDao.getServiceReadDo().findCount(sqlXtYsf,map);//血压应随访
//            double xtWcl = 0;
//            if(xtYsf > 0 ){
//                xtWcl = MyMathUtil.div(Double.valueOf(xtYjsf),Double.valueOf(xtYsf),2)*100;//完成率
//            }
//            mapXt.put("xtYjsf",xtYjsf);//已随访
//            mapXt.put("xtYsf",xtYsf);//已应随访
//            mapXt.put("xtWcl",xtWcl);//完成率
//
//            Map<String,Object> xt = this.getMapSfRw(daysBetween,sqlXtYjsf,sqlXtYsf,qvo.getStartDate(),null,null,qvo.getHospId());
//            mapXt.putAll(xt);
//
//            mapCount.put("xy",mapXy);
//            mapCount.put("xt",mapXt);
//            return mapCount;
//        }
//
//        //团队统计
//        if (StringUtils.isNotBlank(qvo.getTeamId())) {
//            map.put("teamId",qvo.getTeamId());
//            //血压统计
//            String sqlXyYjsf = sqlYjsf + " AND t.SF_FOLLOW_TYPE = '0' AND t.SF_TEAM_ID = :teamId";//已随访
//            String sqlXyYsf = sqlYsf+ " AND t.SF_FOLLOW_TYPE = '0' AND t.SF_TEAM_ID = :teamId";//应随访
//
//            int xyYjsf = this.sysDao.getServiceReadDo().findCount(sqlXyYjsf,map);//血压已随访
//            int xyYsf = this.sysDao.getServiceReadDo().findCount(sqlXyYsf,map);//血压应随访
//            double xyWcl = 0;
//            if(xyYsf > 0 ){
//                xyWcl = MyMathUtil.div(Double.valueOf(xyYjsf),Double.valueOf(xyYsf),2)*100;//完成率
//            }
//            mapXy.put("xyYjsf",xyYjsf);//已随访
//            mapXy.put("xyYsf",xyYsf);//已应随访
//            mapXy.put("xyWcl",xyWcl);//完成率
//
//            int daysBetween = ExtendDate.daysBetween(qvo.getStartDate(),qvo.getEndDate());
//            Map<String,Object> xy = this.getMapSfRw(daysBetween,sqlXyYjsf,sqlXyYsf,qvo.getStartDate(),null,qvo.getTeamId(),null);
//            mapXy.putAll(xy);
//
//            //血糖
//            String sqlXtYjsf = sqlYjsf + " AND t.SF_FOLLOW_TYPE = '1' AND t.SF_TEAM_ID = :teamId";//已随访
//            String sqlXtYsf = sqlYsf+ " AND t.SF_FOLLOW_TYPE = '1' AND t.SF_TEAM_ID = :teamId";//应随访
//
//            int xtYjsf = this.sysDao.getServiceReadDo().findCount(sqlXtYjsf,map);//血压已随访
//            int xtYsf = this.sysDao.getServiceReadDo().findCount(sqlXtYsf,map);//血压应随访
//            double xtWcl = 0;
//            if(xtYsf > 0 ){
//                xtWcl = MyMathUtil.div(Double.valueOf(xtYjsf),Double.valueOf(xtYsf),2)*100;//完成率
//            }
//            mapXt.put("xtYjsf",xtYjsf);//已随访
//            mapXt.put("xtYsf",xtYsf);//已应随访
//            mapXt.put("xtWcl",xtWcl);//完成率
//
//            Map<String,Object> xt = this.getMapSfRw(daysBetween,sqlXtYjsf,sqlXtYsf,qvo.getStartDate(),null,qvo.getTeamId(),null);
//            mapXt.putAll(xt);
//
//            mapCount.put("xy",mapXy);
//            mapCount.put("xt",mapXt);
//            return mapCount;
//        }

        //医生统计
//        if (StringUtils.isNotBlank(qvo.getDrId())) {
            map.put("drId", qvo.getDrId());
            //血压统计
            map.put("xyState",ResidentMangeType.GXY.getValue());
            String sqlXyYjsf = sqlYjsf + " AND t.SF_FOLLOW_TYPE = :xyState AND t.SF_FOLLOW_DOCTORID = :drId";//已随访
            String sqlXyYsf = sqlYsf+ " AND t.SF_FOLLOW_TYPE = :xyState AND t.SF_FOLLOW_DOCTORID = :drId";//应随访

            int xyYjsf = this.sysDao.getServiceReadDo().findCount(sqlXyYjsf,map);//血压已随访
            int xyYsf = this.sysDao.getServiceReadDo().findCount(sqlXyYsf,map);//血压应随访
            double xyWcl = 0;
            if(xyYsf > 0 ){
                xyWcl = MyMathUtil.div(Double.valueOf(xyYjsf),Double.valueOf(xyYsf),2)*100;//完成率
            }
            mapXy.put("xyYjsf",xyYjsf);//已随访
            mapXy.put("xyYsf",xyYsf);//已应随访
            mapXy.put("xyWcl",xyWcl);//完成率

            int daysBetween = ExtendDate.daysBetween(qvo.getStartDate(),qvo.getEndDate());
            Map<String,Object> xy = this.getMapSfRw(daysBetween,sqlXyYjsf,sqlXyYsf,qvo.getStartDate(),qvo.getDrId(),null,null);
            mapXy.putAll(xy);

            //血糖
            map.put("xtState",ResidentMangeType.TNB.getValue());
            String sqlXtYjsf = sqlYjsf + " AND t.SF_FOLLOW_TYPE = :xtState AND t.SF_FOLLOW_DOCTORID = :drId";//已随访
            String sqlXtYsf = sqlYsf+ " AND t.SF_FOLLOW_TYPE = :xtState AND t.SF_FOLLOW_DOCTORID = :drId";//应随访

            int xtYjsf = this.sysDao.getServiceReadDo().findCount(sqlXtYjsf,map);//血压已随访
            int xtYsf = this.sysDao.getServiceReadDo().findCount(sqlXtYsf,map);//血压应随访
            double xtWcl = 0;
            if(xtYsf > 0 ){
                xtWcl = MyMathUtil.div(Double.valueOf(xtYjsf),Double.valueOf(xtYsf),2)*100;//完成率
            }
            mapXt.put("xtYjsf",xtYjsf);//已随访
            mapXt.put("xtYsf",xtYsf);//已应随访
            mapXt.put("xtWcl",xtWcl);//完成率

            Map<String,Object> xt = this.getMapSfRw(daysBetween,sqlXtYjsf,sqlXtYsf,qvo.getStartDate(),qvo.getDrId(),null,null);
            mapXt.putAll(xt);

            mapCount.put("xy",mapXy);
            mapCount.put("xt",mapXt);
            return mapCount;
//        }
//        return mapCount;
    }


    /**
     * 随访任务量公用方法
     * @param daysBetween
     * @param sqlYjsf
     * @param sqlYsf
     * @param startDate
     * @return
     */
    private Map<String,Object> getMapSfRw(int daysBetween,String sqlYjsf,String sqlYsf,String startDate,
                                          String drId,String teamId,String hospId){
        Map<String,Object> map =  new HashMap<String,Object>();
        Map<String,Object> mapCount =  new HashMap<String,Object>();
        if(daysBetween >0){
            String end = null;
            String title = null;
            List<String> lsString = new ArrayList<String>();
            List<Integer> lsValueYjsf = new ArrayList<Integer>();//已随访
            List<Integer> lsValueYsf= new ArrayList<Integer>();//应随访
            List<Double> lsValueWcl= new ArrayList<Double>();//完成率
            for(int i=0;i<=daysBetween;i++){
                if(StringUtils.isBlank(end)){
                    end = startDate;
                    title = ExtendDate.getChineseMD(end);
                }else{
                    end = ExtendDateUtil.addDate(end,ExtendDateType.DAYS.getValue(),1);
                    title = ExtendDate.getChineseMD(end);
                }
                map =  new HashMap<String,Object>();
                map.put("startDate",end+" 00:00:00");
                map.put("endDate",end+" 23:59:59");
                map.put("ywcState", FollowPlanState.YIWANCHENG.getValue());
                if(StringUtils.isNotBlank(drId)){
                    map.put("drId",drId);
                }
                if(StringUtils.isNotBlank(teamId)){
                    map.put("teamId",teamId);
                }
                if(StringUtils.isNotBlank(hospId)){
                    map.put("hospId",hospId);
                }

                int yjsf = this.sysDao.getServiceReadDo().findCount(sqlYjsf,map);//已随访
                int ysf = this.sysDao.getServiceReadDo().findCount(sqlYsf,map);//应随访
                double wcl = 0;
                if(wcl > 0 ){
                    wcl = MyMathUtil.div(Double.valueOf(yjsf),Double.valueOf(ysf),2)*100;//完成率
                }
                lsString.add(title);
                lsValueYjsf.add(yjsf);
                lsValueYsf.add(ysf);
                lsValueWcl.add(wcl);
            }
            mapCount.put("lsString",lsString);
            mapCount.put("lsValueYjsf",lsValueYjsf);
            mapCount.put("lsValueYsf",lsValueYsf);
            mapCount.put("lsValueWcl",lsValueWcl);
        }
        return  mapCount;
    }

    /**
     * 健康分布
     * @param qvo areaId 区域主键 hospId 社区主键 teamId 团队主键
     * @return
     */
    @Override
    public Map findHealthGroupCount(ResidentVo qvo) throws Exception {
        HashMap rmap = new HashMap();
        //健康分布
        List<AppLabelManage> ls = sysDao.getServiceDo().loadByPk(AppLabelManage.class, "labelType", "1");
        if (ls != null && !ls.isEmpty()) {
            String sql = "SELECT COUNT(1) FROM APP_SIGN_FORM a WHERE a.SIGN_STATE IN ('0','2')   ";
            if (StringUtils.isNotBlank(qvo.getDrId())) {
                HashMap map = new HashMap();
                sql += " AND a.SIGN_DR_ID =:SIGN_DR_ID";
                map.put("SIGN_DR_ID", qvo.getDrId());
                sql += " AND a.SIGN_HEALTH_GROUP=:SIGN_HEALTH_GROUP";
                for (AppLabelManage l : ls) {
                    map.put("SIGN_HEALTH_GROUP", l.getLabelValue());
                    int count = sysDao.getServiceReadDo().findCount(sql, map);
                    rmap.put(l.getLabelTitle(), count);
                }
                return rmap;
            }
        }
        return rmap;
    }

//    public Map findTeamNo(PerformanceVo qvo){
//
//    }

    /**
     * 统计遵从医嘱
     * @param qvo drId 医生主键
     * @param qvo startDate 开始日期
     * @param qvo endDate 结束日期
     * @return
     */
    @Override
    public List<Map<String, Object>> findFollowDoctor(PerformanceVo qvo) throws Exception {
        List<Map<String, Object>> returnList = new ArrayList<>();
        List<Map<String,String>> list = ExtendDate.fWeek(qvo.getStartDate(),qvo.getEndDate());
        for (Map<String,String> m:list) {
            Map<String, Object> map = new HashMap<>();
            Map<String,Object> mapCount =  new HashMap<>();
            map.put("startDate", m.get("startDate"));
            map.put("endDate", m.get("endDate"));
            String sql1 = "SELECT count(*) FROM APP_DRUG_GUIDE WHERE DG_END_TIME BETWEEN :startDate AND :endDate";
            String sql2 = "SELECT count(*) FROM APP_DRUG_GUIDE WHERE DG_END_TIME BETWEEN :startDate AND :endDate " +
                    "AND (DG_FOLLOW_GUIDE IS NULL OR DG_FOLLOW_GUIDE = '0')";
            int count = sysDao.getServiceReadDo().gteSqlCount(sql1,map);
            int unFollowCount = sysDao.getServiceReadDo().gteSqlCount(sql2,map);
            mapCount.put("count",count);
            mapCount.put("unFollowCount",unFollowCount);
            String aDate = ExtendDate.getChineseMD(m.get("startDate"));
            String bDay = ExtendDate.getChineseDD(m.get("endDate"));
            mapCount.put("date",aDate+"-"+bDay);
            returnList.add(mapCount);
        }
        return returnList;
    }


    /**
     * 服务分布
     * @param qvo areaId 区域主键 hospId 社区主键 teamId 团队主键
     * @return
     */
    @Override
    public Map findPersGroupCount(ResidentVo qvo) throws Exception{
        HashMap rmap = new HashMap();
        List<AppLabelManage> ls = this.sysDao.getAppLabelManageDao().findByType("3");
        //List<CdCode> ls = sysDao.getCodeDao().findGroupList(CodeGroupConstrats.GROUP_RESIDENTMANGE, CommonEnable.QIYONG.getValue());
        if(ls!=null && !ls.isEmpty()) {
            String sql = "SELECT COUNT(1) FROM APP_SIGN_FORM a LEFT JOIN APP_LABEL_GROUP b on a.ID=b.LABEL_SIGN_ID WHERE a.SIGN_STATE IN ('0','2') ";
            if (StringUtils.isNotBlank(qvo.getDrId())) {
                HashMap map = new HashMap();
                sql += " AND a.SIGN_DR_ID =:SIGN_DR_ID";
                map.put("SIGN_DR_ID", qvo.getDrId());
                sql += " AND b.LABEL_VALUE=:SIGN_PERS_GROUP";
                for(AppLabelManage l:ls) {
                    map.put("SIGN_PERS_GROUP",l.getLabelValue());
                    int  count= sysDao.getServiceReadDo().findCount(sql, map);
                    rmap.put(l.getLabelTitle().replace("(0-6岁)",""), count);
                }
                return rmap;
            }
        }
        return rmap;

    }

    /**
     * 医生完成健康教育任务信息
     * @param qvo
     * @return
     */
    @Override
    public AppLyTxEntity findHealthEducation(AppCommQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("signId",qvo.getSignFormId());
        Calendar cal = Calendar.getInstance();
        map.put("year",cal.get(Calendar.YEAR));
        String sql = "SELECT\n" +
                "\t(\n" +
                "\t\tSELECT\n" +
                "\t\t\tcount(1)\n" +
                "\t\tFROM\n" +
                "\t\t\tAPP_PERFORMANCE_HEALTH_EDUCATION b\n" +
                "\t\tWHERE\n" +
                "\t\t\tb.PER_DR_ID = a.SIGN_DR_ID\n" +
                "\t\tAND b.PER_IDNO = a.SIGN_PATIENT_IDNO\n" +
                "\t\tAND b.PER_YEAR =:year\n" +
                "\t) count,\n" +
                "a.SIGN_PATIENT_ID patientId,\n" +
                "a.SIGN_DR_ID drId,\n" +
                "a.SIGN_PATIENT_IDNO patientIdno \n" +
                "FROM\n" +
                "\tAPP_SIGN_FORM a\n" +
                "WHERE\n" +
                "\ta.ID =:signId";
        List<AppLyTxEntity> ls = sysDao.getServiceReadDo().findSqlMapRVo(sql,map,AppLyTxEntity.class);
        if(ls!=null && ls.size()>0){
            return ls.get(0);
        }
        return null;
    }

    /**
     * 医生完成健康指导记录
     * @param qvo
     * @return
     */
    @Override
    public AppLyTxEntity findHealthGuide(AppCommQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("signId",qvo.getSignFormId());
        Calendar cal = Calendar.getInstance();
        map.put("year",cal.get(Calendar.YEAR));
        String sql = "SELECT\n" +
                "\t(\n" +
                "\t\tSELECT\n" +
                "\t\t\tcount(1)\n" +
                "\t\tFROM\n" +
                "\t\t\tAPP_PERFORMANCE_HEALTH_GUIDANCE b\n" +
                "\t\tWHERE\n" +
                "\t\t\tb.PER_DR_ID = a.SIGN_DR_ID\n" +
                "\t\tAND b.PER_IDNO = a.SIGN_PATIENT_IDNO\n" +
                "\t\tAND b.PER_YEAR =:year\n" +
                "\t) count,\n" +
                "a.SIGN_PATIENT_ID patientId,\n" +
                "a.SIGN_DR_ID drId,\n" +
                "a.SIGN_PATIENT_IDNO patientIdno \n" +
                "FROM\n" +
                "\tAPP_SIGN_FORM a\n" +
                "WHERE\n" +
                "\ta.ID = :signId";
        List<AppLyTxEntity> list = sysDao.getServiceReadDo().findSqlMapRVo(sql,map,AppLyTxEntity.class);
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    /**
     * 医生完成用药指导记录
     * @param qvo
     * @return
     */
    @Override
    public AppLyTxEntity findGuidelines(AppCommQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("signId",qvo.getSignFormId());
        Calendar cal = Calendar.getInstance();
        map.put("year",cal.get(Calendar.YEAR));
        String sql = "SELECT\n" +
                "\t(\n" +
                "\t\tSELECT\n" +
                "\t\t\tcount(1)\n" +
                "\t\tFROM\n" +
                "\t\t\tAPP_PERFORMANCE_MEDICATION_GUIDANCE b\n" +
                "\t\tWHERE\n" +
                "\t\t\tb.PER_DR_ID = a.SIGN_DR_ID\n" +
                "\t\tAND b.PER_IDNO = a.SIGN_PATIENT_IDNO\n" +
                "\t\tAND b.PER_YEAR =:year\n" +
                "\t) count,\n" +
                "a.SIGN_PATIENT_ID patientId,\n" +
                "a.SIGN_DR_ID drId," +
                "a.SIGN_PATIENT_IDNO patientIdno \n" +
                "FROM\n" +
                "\tAPP_SIGN_FORM a\n" +
                "WHERE\n" +
                "\ta.ID = :signId";
        List<AppLyTxEntity> list = sysDao.getServiceReadDo().findSqlMapRVo(sql,map,AppLyTxEntity.class);
        if(list!=null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    /**
     * 查询定期随访（高血压、糖尿病、重性精神病、肺结核）
     * @param qvo
     * @return
     */
    @Override
    public List<AppLyTxEntity> findVisit(AppCommQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        Calendar cal = Calendar.getInstance();
        map.put("year",cal.get(Calendar.YEAR));
        map.put("drId",qvo.getDrId());
        map.put("type",qvo.getSignlx());
        String[] signStates = new String[]{SignFormType.YUQY.getValue(),SignFormType.YQY.getValue()};
        map.put("signState",signStates);
        String sql ="SELECT\n" +
                "\t(\n" +
                "\t\tSELECT\n" +
                "\t\t\tcount(1)\n" +
                "\t\tFROM\n" +
                "\t\t\tAPP_PERFORMANCE_REGULAR_FOLLOWUP c\n" +
                "\t\tWHERE\n" +
                "\t\t\tc.PER_DR_ID = a.SIGN_DR_ID\n" +
                "\t\tAND c.PER_IDNO = a.SIGN_PATIENT_IDNO\n" +
                "\t\tAND c.PER_YEAR = :year\n" +
                "\t\tAND c.PER_FOLLOW_TYPE = :type\n" +
                "\t) count,\n" +
                "\ta.SIGN_PATIENT_ID patientId,\n" +
                "\ta.SIGN_DR_ID drId,\n" +
                "\ta.ID signId,\n" +
                "\ta.SIGN_PATIENT_IDNO patientIdno\n" +
                "FROM\n" +
                "\tAPP_SIGN_FORM a\n" +
                "INNER JOIN APP_LABEL_GROUP b ON a.ID = b.LABEL_SIGN_ID\n" +
                "WHERE\n" +
                "\ta.SIGN_STATE IN (:signState)\n" +
                "AND b.LABEL_TYPE = '3'\n" +
                "AND b.LABEL_VALUE = :type\n" +
                "AND a.SIGN_DR_ID =:drId\n" +
                "\n";
        List<AppLyTxEntity> list = sysDao.getServiceReadDo().findSqlMapRVo(sql,map,AppLyTxEntity.class);
        return list;
    }

    /**
     * 中医药健康指导履约记录
     * @param qvo
     * @return
     */
    @Override
    public List<AppTcmLyEntity> findTcmLy(AppCommQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("drId",qvo.getDrId());
        String sql = "SELECT\n" +
                "\t(SELECT\n" +
                "\tcount(1)\n" +
                "FROM\n" +
                "\tAPP_CHILD_HEALTH_PLAN d\n" +
                "WHERE\n" +
                "\td.CHP_CHILD_USER_ID =a.SIGN_PATIENT_ID\n" +
                "AND d.CHP_TITLE IN (\n" +
                "\t'6月龄',\n" +
                "\t'12月龄',\n" +
                "\t'18月龄',\n" +
                "\t'24月龄',\n" +
                "\t'30月龄',\n" +
                "\t'3岁龄'\n" +
                ")\n" +
                "AND d.CHP_PLAN_DATE <= NOW()) countTj,\n" +
                "(SELECT\n" +
                "\tcount(1)\n" +
                "FROM\n" +
                "\tAPP_PERFORMANCE_CHINESE_GUIDANCE cc \n" +
                "WHERE cc.PER_DR_ID=a.SIGN_DR_ID\n" +
                "AND cc.PER_IDNO =a.SIGN_PATIENT_IDNO) countGuide," +
                "\ta.SIGN_PATIENT_ID patientId,\n" +
                "\ta.SIGN_PATIENT_IDNO patientIdNo,\n" +
                "\ta.ID signId,\n" +
                "\ta.SIGN_DR_ID drId\n" +
                "FROM\n" +
                "\tAPP_SIGN_FORM a\n" +
                "INNER JOIN APP_LABEL_GROUP b ON a.ID = b.LABEL_SIGN_ID\n" +
                "WHERE\n" +
                "\ta.SIGN_STATE IN ('0','2')\n" +
                "AND a.SIGN_CONTRACT_STATE = '0'\n" +
                "AND b.LABEL_TYPE = '3'\n" +
                "AND b.LABEL_VALUE = '2'\n" +
                "AND a.SIGN_DR_ID = :drId";
        List<AppTcmLyEntity> list = sysDao.getServiceReadDo().findSqlMapRVo(sql,map,AppTcmLyEntity.class);
        return list;
    }

    /**
     * 孕期保健服务履约记录
     * @param qvo
     * @return
     */
    @Override
    public List<AppLyTxEntity> findPrenatalCare(AppCommQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("drId",qvo.getDrId());
        String[] signStates = new String[]{SignFormType.YUQY.getValue(),SignFormType.YQY.getValue()};
        map.put("signState",signStates);
        map.put("type", ResidentMangeType.YCF.getValue());
        Calendar cal = Calendar.getInstance();
        map.put("year",cal.get(Calendar.YEAR));
        String sql = "SELECT\n" +
                "\t(\n" +
                "\t\tSELECT\n" +
                "\t\t\tcount(1)\n" +
                "\t\tFROM\n" +
                "\t\t\tAPP_PERFORMANCE_PRENATAL_CARE c\n" +
                "\t\tWHERE\n" +
                "\t\t\tc.PER_IDNO = a.SIGN_PATIENT_IDNO\n" +
                "\t\tAND c.PER_DR_ID = a.SIGN_DR_ID\n" +
                "\tAND c.PER_YEAR =:year\n" +
                "\t) count,\n" +
                "\ta.SIGN_PATIENT_ID patientId,\n" +
                "\ta.SIGN_PATIENT_IDNO patientIdno,\n" +
                "\ta.SIGN_DR_ID drId," +
                "\ta.ID signId \n" +
                "FROM\n" +
                "\tAPP_SIGN_FORM a\n" +
                "INNER JOIN APP_LABEL_GROUP b ON a.ID = b.LABEL_SIGN_ID\n" +
                "WHERE\n" +
                "\ta.SIGN_STATE IN (:signState)\n" +
                "AND a.SIGN_CONTRACT_STATE = '0'\n" +
                "AND a.SIGN_DR_ID = :drId\n" +
                "AND b.LABEL_TYPE = '3'\n" +
                "AND b.LABEL_VALUE = :type";
        List<AppLyTxEntity> list = sysDao.getServiceReadDo().findSqlMapRVo(sql,map,AppLyTxEntity.class);
        return list;
    }

    /**
     * 产后42天健康检查记录
     * @param qvo
     * @return
     */
    @Override
    public List<AppLyTxEntity> findPostPartum(AppCommQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("drId",qvo.getDrId());
        String[] signStates = new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
        map.put("signState",signStates);
        map.put("type", ResidentMangeType.YCF.getValue());
        Calendar cal = Calendar.getInstance();
        map.put("year",cal.get(Calendar.YEAR));
        String sql = "SELECT\n" +
                "\t(\n" +
                "\t\tSELECT\n" +
                "\t\t\tcount(1)\n" +
                "\t\tFROM\n" +
                "\t\t\tAPP_PERFORMANCE_POSTPARTUM_VISIT c\n" +
                "\t\tWHERE\n" +
                "\t\t\tc.PER_IDNO = a.SIGN_PATIENT_IDNO\n" +
                "\t\tAND c.PER_DR_ID = a.SIGN_DR_ID\n" +
                "\tAND c.PER_YEAR =:year\n" +
                "\t) count,\n" +
                "\ta.SIGN_PATIENT_ID patientId,\n" +
                "\ta.SIGN_PATIENT_IDNO patientIdno,\n" +
                "\ta.SIGN_DR_ID drId,\n" +
                "\ta.ID signId " +
                "FROM\n" +
                "\tAPP_SIGN_FORM a\n" +
                "INNER JOIN APP_LABEL_GROUP b ON a.ID = b.LABEL_SIGN_ID\n" +
                "WHERE\n" +
                "\ta.SIGN_STATE IN (:signState)\n" +
                "AND a.SIGN_CONTRACT_STATE = '0'\n" +
                "AND a.SIGN_DR_ID = :drId\n" +
                "AND b.LABEL_TYPE = '3'\n" +
                "AND b.LABEL_VALUE = :type";
        List<AppLyTxEntity> list = sysDao.getServiceReadDo().findSqlMapRVo(sql,map,AppLyTxEntity.class);
        return list;
    }

    /**
     * 健康体检履约记录
     * @param qvo
     * @return
     */
    @Override
    public List<AppPerformancePhysicalExamination> findJktjList(AppCommQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("drId",qvo.getDrId());
        map.put("patientIdno",qvo.getPatientIdno());
        Calendar cal = Calendar.getInstance();
        map.put("year",cal.get(Calendar.YEAR));
        String sql = "SELECT * FROM APP_PERFORMANCE_PHYSICAL_EXAMINATION a " +
                "WHERE a.PER_IDNO=:patientIdno AND a.PER_DR_ID=:drId " +
                "AND a.PER_YEAR =:year";
        List<AppPerformancePhysicalExamination> list = sysDao.getServiceReadDo().findSqlMap(sql,map,AppPerformancePhysicalExamination.class);
        return list;
    }

    /**
     * 履约通知
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public String performanceNotice(AppLyQvo qvo) throws Exception {
        AppDrUser drUser = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,qvo.getDrId());
        if(drUser!=null){
            if(StringUtils.isNotBlank(drUser.getDrHospId())){
                AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
                if(dept!=null){
                    if(StringUtils.isNotBlank(qvo.getPcNum()) && !"1".equals(qvo.getType())){//批量通知
                        List<AppPerformanceRecord> list = sysDao.getServiceDo().loadByPk(AppPerformanceRecord.class,"aprPcNum",qvo.getPcNum());
                        if(list!=null && list.size()>0){
                            for(AppPerformanceRecord ll:list){
                                if(PerType.ETJKTJ.getValue().equals(qvo.getPeyType())){
                                    sysDao.getAppNoticeDao().addNotices("履约通知",
                                            "您好，请您及时至社区医院进行健康检查。检查月龄：满月、3月龄、6月龄、8月龄、" +
                                                    "12月龄、24月龄、30月龄、3岁、4岁、5岁、6岁。"+dept.getHospName(),NoticesType.XTXX.getValue(),
                                            qvo.getDrId(),ll.getAprPatientId(),null,DrPatientType.PATIENT.getValue());
                                }else if(PerType.ETZYYJKZD.getValue().equals(qvo.getPeyType())){
                                    sysDao.getAppNoticeDao().addNotices("履约通知",
                                            "您好，请您及时至社区医院进行0-36个月儿童中医药健康管理服务。服务月龄：6月龄、" +
                                                    "12月龄、18月龄、24月龄、30月龄、36月龄。"+dept.getHospName(),NoticesType.XTXX.getValue(),
                                            qvo.getDrId(),ll.getAprPatientId(),null,DrPatientType.PATIENT.getValue());
                                }else if(PerType.YQBJFW.getValue().equals(qvo.getPeyType())){
                                    sysDao.getAppNoticeDao().addNotices("履约通知",
                                            "您好，请您及时至社区医院进行孕期健康指导服务。"+dept.getHospName(),NoticesType.XTXX.getValue(),
                                            qvo.getDrId(),ll.getAprPatientId(),null,DrPatientType.PATIENT.getValue());
                                }else if(PerType.CHJKJC.getValue().equals(qvo.getPeyType())){
                                    sysDao.getAppNoticeDao().addNotices("履约通知",
                                            "您好，请您及时至社区医院进行产后42天健康检查。"+dept.getHospName(),NoticesType.XTXX.getValue(),
                                            qvo.getDrId(),ll.getAprPatientId(),null,DrPatientType.PATIENT.getValue());
                                }else if(PerType.JKTJ.getValue().equals(qvo.getPeyType())){
                                    sysDao.getAppNoticeDao().addNotices("履约通知","您好，请您及时进行免费健康体检。"+dept.getHospName(),
                                            NoticesType.XTXX.getValue(),qvo.getDrId(),ll.getAprPatientId(),null,DrPatientType.PATIENT.getValue());
                                }
                            }
                        }
                    }else {//选择人发送通知
                        if(StringUtils.isNotBlank(qvo.getPatientId())){
                            String[] patientIds = qvo.getPatientId().split(";");
                            for(String patientId:patientIds){
                                if(PerType.ETJKTJ.getValue().equals(qvo.getPeyType())){
                                    sysDao.getAppNoticeDao().addNotices("履约通知",
                                            "您好，请您及时至社区医院进行健康检查。检查月龄：满月、3月龄、6月龄、8月龄、" +
                                                    "12月龄、24月龄、30月龄、3岁、4岁、5岁、6岁。"+dept.getHospName(),NoticesType.XTXX.getValue(),
                                            qvo.getDrId(),patientId,null,DrPatientType.PATIENT.getValue());
                                }else if(PerType.ETZYYJKZD.getValue().equals(qvo.getPeyType())){
                                    sysDao.getAppNoticeDao().addNotices("履约通知",
                                            "您好，请您及时至社区医院进行0-36个月儿童中医药健康管理服务。服务月龄：6月龄、" +
                                                    "12月龄、18月龄、24月龄、30月龄、36月龄。"+dept.getHospName(),NoticesType.XTXX.getValue(),
                                            qvo.getDrId(),patientId,null,DrPatientType.PATIENT.getValue());
                                }else if(PerType.YQBJFW.getValue().equals(qvo.getPeyType())){
                                    sysDao.getAppNoticeDao().addNotices("履约通知",
                                            "您好，请您及时至社区医院进行孕期健康指导服务。"+dept.getHospName(),NoticesType.XTXX.getValue(),
                                            qvo.getDrId(),patientId,null,DrPatientType.PATIENT.getValue());
                                }else if(PerType.CHJKJC.getValue().equals(qvo.getPeyType())){
                                    sysDao.getAppNoticeDao().addNotices("履约通知",
                                            "您好，请您及时至社区医院进行产后42天健康检查。"+dept.getHospName(),NoticesType.XTXX.getValue(),
                                            qvo.getDrId(),patientId,null,DrPatientType.PATIENT.getValue());
                                }else if(PerType.JKTJ.getValue().equals(qvo.getPeyType())){
                                    sysDao.getAppNoticeDao().addNotices("履约通知","您好，请您及时进行免费健康体检。"+dept.getHospName(),
                                            NoticesType.XTXX.getValue(),qvo.getDrId(),patientId,null,DrPatientType.PATIENT.getValue());
                                }
                            }
                        }
                    }
                }
            }
        }else{
            return "查询不到医生信息";
        }
        return "true";
    }

    @Override
    public Map findAgeCount(ResidentVo qvo) throws Exception {
        Map<String,Object> ageMap = new HashMap<>();
        //查询年龄分布
        String  tjRsult = "0,6;7,18;19,30;31,50;51,65;66,200";
        String  tjRsultTitle = "0~6;7~18;19~30;31~50;51~65;>65";
        String[] result = tjRsult.split(";");
        String[] resultTitle = tjRsultTitle.split(";");
        List<String> lsTitle = new ArrayList<String>();
        List<Integer> lsValue = new ArrayList<Integer>();
        if(result != null){
            Map<String,Object> map = new HashMap<>();
            String[] qys = {SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
            map.put("yqy", qys);
            String sql = "select count(*) from APP_SIGN_FORM a WHERE 1=1 AND a.SIGN_STATE IN (:yqy) ";
            map.put("SIGN_DR_ID",qvo.getDrId());
            for(int i=0;i<result.length;i++){
                String[] age = result[i].split(",");
                map.put("startAge",age[0]);
                map.put("endAge",age[1]);
                sql += " AND a.SIGN_PATIENT_AGE >= :startAge AND a.SIGN_PATIENT_AGE <= :endAge ";
                sql += " AND a.SIGN_DR_ID =:SIGN_DR_ID ";
                int count = this.sysDao.getServiceDo().findCount(sql,map);
                lsValue.add(count);
            }
            for(int i=0;i<resultTitle.length;i++){
                lsTitle.add(resultTitle[i]);
            }
            ageMap.put("lsTitle",lsTitle);
            ageMap.put("lsValue",lsValue);
        }
        return ageMap;
    }

    @Override
    public Map findEconomicCount(ResidentVo qvo) throws Exception {
        Map<String, Object> returnMap = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        String[] state = {SignFormType.YQY.getValue(), SignFormType.YUQY.getValue()};//状态
        map.put("SIGN_STATE", state);
        String sql = "SELECT COUNT(1) FROM APP_SIGN_FORM t  LEFT JOIN APP_LABEL_ECONOMICS b ON t.ID = b.LABEL_SIGN_ID " +
                "WHERE t.SIGN_STATE IN (:SIGN_STATE)  ";
        sql += " AND b.LABEL_VALUE =:LABEL_VALUE  AND b.LABEL_TYPE='4' ";
        sql += " AND t.SIGN_DR_ID =:SIGN_DR_ID";
        map.put("SIGN_DR_ID", qvo.getDrId());

        List<AppLabelManage> ls = this.sysDao.getAppLabelManageDao().findByType(LabelManageType.JJLX.getValue());
        if (ls != null && !ls.isEmpty()) {
            for (AppLabelManage l : ls) {
                if (l.getLabelValue().equals("1")) {//一般人口
                    map.put("LABEL_VALUE", l.getLabelValue());
                    int count = sysDao.getServiceDo().findCount(sql, map);
//                    returnMap.put("manageGeneralPopulationCount", count);
                    returnMap.put("一般人口", count);
                } else if (l.getLabelValue().equals("2")) {//建档立卡贫困人口
                    map.put("LABEL_VALUE", l.getLabelValue());
                    int count = sysDao.getServiceDo().findCount(sql, map);
                    returnMap.put("建档立卡贫困人口", count);
                } else if (l.getLabelValue().equals("3")) {//低保户
                    map.put("LABEL_VALUE", l.getLabelValue());
                    int count = sysDao.getServiceDo().findCount(sql, map);
                    returnMap.put("低保户", count);
                } else if (l.getLabelValue().equals("4")) {//特困户（五保户）
                    map.put("LABEL_VALUE", l.getLabelValue());
                    int count = sysDao.getServiceDo().findCount(sql, map);
                    returnMap.put("特困户（五保户）", count);
                } else if (l.getLabelValue().equals("5")) {//计生特殊家庭
                    map.put("LABEL_VALUE", l.getLabelValue());
                    int count = sysDao.getServiceDo().findCount(sql, map);
                    returnMap.put("计生特殊家庭", count);
                }
            }
        }
        return returnMap;
    }

    @Override
    public Map findGenderCount(ResidentVo qvo) throws Exception {
        Map<String,Object> returnMap = new HashMap<>();
        Map<String,Object> map = new HashMap<>();
        String[] state = {SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};//状态
        map.put("SIGN_STATE",state);
        String sqlMan = "SELECT count(1) FROM APP_SIGN_FORM t where t.SIGN_PATIENT_GENDER = '1' AND t.SIGN_STATE IN (:SIGN_STATE) ";//统计男
        String sqlWoMan = "SELECT count(1) FROM APP_SIGN_FORM t where t.SIGN_PATIENT_GENDER = '2'  AND t.SIGN_STATE IN (:SIGN_STATE) ";//统计女
        String sql = "SELECT count(1) FROM APP_SIGN_FORM t where t.SIGN_STATE IN (:SIGN_STATE) ";//统计总数
        sqlMan += " AND t.SIGN_DR_ID =:SIGN_DR_ID";
        sqlWoMan += " AND t.SIGN_DR_ID =:SIGN_DR_ID";
        sql += " AND t.SIGN_DR_ID =:SIGN_DR_ID ";
        map.put("SIGN_DR_ID", qvo.getDrId());
        int man = this.sysDao.getServiceDo().findCount(sqlMan,map);
        returnMap.put("男性",man);

        int woMan = this.sysDao.getServiceDo().findCount(sqlWoMan,map);
        returnMap.put("女性",woMan);

        int allCount = this.sysDao.getServiceDo().findCount(sql,map);
        returnMap.put("总数",allCount);

        return returnMap;
    }
}
