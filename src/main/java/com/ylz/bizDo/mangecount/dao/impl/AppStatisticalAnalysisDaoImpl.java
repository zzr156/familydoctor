package com.ylz.bizDo.mangecount.dao.impl;

import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.bizDo.cd.entity.CdAddressEntity;
import com.ylz.bizDo.jtapp.commonEntity.*;
import com.ylz.bizDo.jtapp.drEntity.JmInfo;
import com.ylz.bizDo.jtapp.patientEntity.AppHealthListEntity;
import com.ylz.bizDo.mangecount.dao.AppStatisticalAnalysisDao;
import com.ylz.bizDo.mangecount.vo.PerformanceVo;
import com.ylz.bizDo.mangecount.vo.ResidentVo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.comEnum.FollowPlanState;
import com.ylz.packcommon.common.comEnum.PayType;
import com.ylz.packcommon.common.comEnum.ResidentMangeType;
import com.ylz.packcommon.common.comEnum.SignFormType;
import com.ylz.packcommon.common.util.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by zzl on 2017/7/19.
 */
@Service("appStatisticalAnalysisDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppStatisticalAnalysisDaoImpl implements AppStatisticalAnalysisDao {
    @Autowired
    private SysDao sysDao;

    /**
     * 查询随访量、健康指导、健康教育、求助量、未缴费人数
     * @param qvo areaId 区域编码
     * @param qvo hospId 医院id
     * @param qvo teamId 团队id
     * @param qvo yearStart 开始时间（yyyy-MM)
     * @param qvo yearEnd 结束时间(yyyy-MM)
     * @return
     */
    @Override
    public Map<String,Object> findCount(ResidentVo qvo) throws Exception {
        Map<String,Object> mapCount = new HashMap<String,Object>();
        Map<String,Object> map = new HashMap<String,Object>();
        Calendar cal = Calendar.getInstance();
        String end = ExtendDate.getYMD(cal)+" 23:59:59";
        cal.add(Calendar.DATE,-6);
        String start = ExtendDate.getYMD(cal)+" 00:00:00";
        map.put("start",start);
        map.put("end",end);
        String[] followState = new String[]{FollowPlanState.YIWANCHENG.getValue(),FollowPlanState.SIWANG.getValue(),FollowPlanState.SHIFANG.getValue()};
        map.put("state", followState);
        //查询随访量
        String sqlSf = " SELECT count(1) FROM APP_FOLLOW_PLAN WHERE 1=1";
        sqlSf +=" AND SF_FOLLOW_DATE>=:start AND SF_FOLLOW_DATE <=:end";
        sqlSf +=" AND SF_FOLLOW_STATE IN :state";
        if(StringUtils.isNotBlank(qvo.getAreaId())){
            String areaCode = AreaUtils.getAreaCode(qvo.getAreaId());
            map.put("areaCode",areaCode+"%");
            sqlSf +=" AND SF_HOS_AREA_CODE LIKE :areaCode";
        }else if(StringUtils.isNotBlank(qvo.getHospId())){
            map.put("hospId",qvo.getHospId());
            sqlSf += " AND SF_HOS_ID =:hospId ";
        }

        int sfCount = sysDao.getServiceReadDo().findCount(sqlSf,map);
        mapCount.put("sfCount",sfCount);
        //健康指导,
        String sqlGuide = "SELECT count(1) FROM APP_HEALTH_MEDDLE WHERE 1=1";
        sqlGuide += " AND HM_CREATE_TIME >=:start AND HM_CREATE_TIME <=:end";
        if(StringUtils.isNotBlank(qvo.getAreaId())){
            String areaCode = AreaUtils.getAreaCode(qvo.getAreaId());
            map.put("areaCode",areaCode+"%");
            sqlGuide += " AND HM_AREA_CODE LIKE :areaCode";
        }else if(StringUtils.isNotBlank(qvo.getHospId())){
            map.put("hospId",qvo.getHospId());
            sqlGuide += " AND HM_HOSP_ID = :hospId";
        }
        int guideCount = sysDao.getServiceReadDo().findCount(sqlGuide,map);
        mapCount.put("guideCount",guideCount);
        //健康教育,教育表
        String sqlHD = "SELECT count(1) FROM APP_USER_HEALTHED WHERE 1=1";
        sqlHD +=" AND HED_CREATE_TIME >=:start AND HED_CREATE_TIME <=:end";
        if(StringUtils.isNotBlank(qvo.getAreaId())){
            String areaCode = AreaUtils.getAreaCode(qvo.getAreaId());
            map.put("areaCode",areaCode+"%");
            sqlHD +=" AND HED_AREA_CODE LIKE :areaCode";
        }else if(StringUtils.isNotBlank(qvo.getHospId())){
            map.put("hospId",qvo.getHospId());
            sqlHD +=" AND HED_HOSP_ID = :hospId";
        }
        int hDCount = sysDao.getServiceReadDo().findCount(sqlHD,map);
        mapCount.put("hDCount",hDCount);
        //求助量,求助表
        String sqlHelp = "SELECT count(1) FROM APP_SEEK_HELP WHERE 1=1";
        sqlHelp += " AND SEEK_HELP_DATE >=:start AND SEEK_HELP_DATE <=:end";
        if(StringUtils.isNotBlank(qvo.getAreaId())){
            String areaCode = AreaUtils.getAreaCode(qvo.getAreaId());
            map.put("areaCode",areaCode+"%");
            sqlHelp += " AND SEEK_AREA_CODE LIKE :areaCode";
        }else if(StringUtils.isNotBlank(qvo.getHospId())){
            map.put("hospId",qvo.getHospId());
            sqlHelp += " AND SEEK_HOSP_ID = :hospId";
        }
        int helpCount = sysDao.getServiceReadDo().findCount(sqlHelp,map);
        mapCount.put("helpCount",helpCount);
        //未缴费人数
        String[] signStates = new String[]{SignFormType.YUQY.getValue(),SignFormType.YQY.getValue()};
        map.put("SIGNSTATE", signStates);
        map.put("payState", PayType.WEIJIAOFEI.getValue());
        String sqlWjf = "SELECT count(1) FROM APP_SIGN_FORM t WHERE 1=1 AND t.SIGN_STATE IN (:SIGNSTATE) AND t.SIGN_PAY_STATE = :payState ";
        sqlWjf += " AND t.SIGN_FROM_DATE >=:start AND t.SIGN_FROM_DATE <=:end";
        if(StringUtils.isNotBlank(qvo.getAreaId())){
            String areaCode = AreaUtils.getAreaCode(qvo.getAreaId());
            map.put("areaCode",areaCode+"%");
            sqlWjf += " AND t.SIGN_AREA_CODE LIKE :areaCode";
        }else if(StringUtils.isNotBlank(qvo.getHospId())){
            map.put("hospId",qvo.getHospId());
            sqlWjf += " AND t.SIGN_HOSP_ID = :hospId";
        }
        int wjfCount = sysDao.getServiceReadDo().findCount(sqlWjf,map);
        mapCount.put("wjfCount",wjfCount);
        return mapCount;
    }
    /**
     * 随访工作量和任务量
     * @param qvo areaId 区域编码
     * @param qvo hospId 医院id
     * @param qvo teamId 团队id
     * @param qvo yearStart 开始时间（yyyy-MM)
     * @param qvo yearEnd 结束时间(yyyy-MM)
     * @return
     */
    @Override
    public Map<String, Object> findSfWorkPlan(ResidentVo qvo) throws Exception {
        Map<String,Object> mapCount = new HashMap<String,Object>();
        Map<String,Object> map = new HashMap<String,Object>();
        List<Map<String,Object>> lsss = new ArrayList<Map<String,Object>>();

        String sql = "SELECT count(1) FROM APP_FOLLOW_PLAN WHERE 1=1";
        map.put("wcState",FollowPlanState.YIWANCHENG.getValue());
        if(StringUtils.isNotBlank(qvo.getYearStart())){
            map.put("startDate", ExtendDate.getFirstDayOfMonth(qvo.getYearStart())+" 00:00:00");
        }
        if(StringUtils.isNotBlank(qvo.getYearEnd())){
            map.put("endDate",ExtendDate.getLastDayOfMonth(qvo.getYearEnd())+" 23:59:59");
        }
        //随访工作量(这段时间的总随访量、每月的总随访量、近期的随访总量)
        //这段日期随访总量
        String sqlZsfl = sql+ " AND SF_FOLLOW_DATE >=:startDate AND SF_FOLLOW_DATE<=:endDate";
        if(StringUtils.isNotBlank(qvo.getAreaId())){
            map.put("areaCode",AreaUtils.getAreaCode(qvo.getAreaId())+"%");
            sqlZsfl += " AND SF_HOS_AREA_CODE LIKE :areaCode";
        }
        if(StringUtils.isNotBlank(qvo.getHospId())){
            map.put("hospId",qvo.getHospId());
            sqlZsfl += " AND SF_HOS_ID = :hospId";
        }
        if(StringUtils.isNotBlank(qvo.getTeamId())){
            map.put("teamId",qvo.getTeamId());
            sqlZsfl += " AND SF_TEAM_ID = :teamId";
        }
        int zsfl = sysDao.getServiceReadDo().findCount(sqlZsfl,map);
        mapCount.put("zsfl",zsfl);

        //血压总数量
        map.put("xyState", ResidentMangeType.GXY.getValue());
        String sfzlxy = sqlZsfl +" AND SF_FOLLOW_TYPE = :xyState";
        int xysfzl = sysDao.getServiceReadDo().findCount(sfzlxy,map);
        mapCount.put("xyzsl",xysfzl);
        //血压完成量
        String sfwclxy = sqlZsfl +" AND SF_FOLLOW_TYPE = :xyState AND SF_FOLLOW_STATE =:wcState";
        int xysfwcl = sysDao.getServiceReadDo().findCount(sfwclxy,map);
        mapCount.put("xysfwcl",xysfwcl);
        //血压完成率
        double xywcl = 0;
        if(xysfzl!=0){
            xywcl=MyMathUtil.div(Double.valueOf(xysfwcl),Double.valueOf(xysfzl),6)*100;
            xywcl = MyMathUtil.round(xywcl,2);
        }
        mapCount.put("xywcl",String.valueOf(xywcl));
        //血糖总数量
        map.put("xtState",ResidentMangeType.TNB.getValue());
        String sfzlxt = sqlZsfl + " AND SF_FOLLOW_TYPE = :xtState";
        int xtsfzl = sysDao.getServiceReadDo().findCount(sfzlxt,map);
        mapCount.put("xtzsl",xtsfzl);
        //血糖完成量
        String sfwclxt = sqlZsfl +" AND SF_FOLLOW_TYPE = :xtState AND SF_FOLLOW_STATE =:wcState";
        int xtsfwcl = sysDao.getServiceReadDo().findCount(sfwclxt,map);
        mapCount.put("xtsfwcl",xtsfwcl);
        //血糖完成率
        double xtwcl = 0;
        if(xtsfzl!=0){
            xtwcl=MyMathUtil.div(Double.valueOf(xtsfwcl),Double.valueOf(xtsfzl),6)*100;
            xtwcl=MyMathUtil.round(xtwcl,2);
        }
        mapCount.put("xtwcl",String.valueOf(xtwcl));
        //新生儿随访总数量
        map.put("xsrState",ResidentMangeType.ETLZLS.getValue());
        String sfzlxsr = sqlZsfl + " AND SF_FOLLOW_TYPE = :xsrState";
        int xsrsfzl = sysDao.getServiceReadDo().findCount(sfzlxsr,map);
        mapCount.put("xsrsfzl",xsrsfzl);
        //新生儿随访完成量
        String sfwclxsr = sqlZsfl +" AND SF_FOLLOW_TYPE = :xsrState AND SF_FOLLOW_STATE =:wcState";
        int xsrsfwcl = sysDao.getServiceReadDo().findCount(sfwclxsr,map);
        mapCount.put("xsrsfwcl",xsrsfwcl);
        //新生儿随访完成率
        double xsrwcl = 0;
        if(xsrsfzl!=0){
            xsrwcl=MyMathUtil.div(Double.valueOf(xsrsfwcl),Double.valueOf(xsrsfzl),6)*100;
            xsrwcl=MyMathUtil.round(xsrwcl,2);
        }
        mapCount.put("xsrwcl",String.valueOf(xsrwcl));
        //孕产妇随访总数量
        map.put("ycfState",ResidentMangeType.YCF.getValue());
        String sfzlycf = sqlZsfl + " AND SF_FOLLOW_TYPE = :ycfState";
        int ycfsfzl = sysDao.getServiceReadDo().findCount(sfzlycf,map);
        mapCount.put("ycfsfzl",ycfsfzl);
        //孕产妇随访完成量
        String sfwclycf = sqlZsfl +" AND SF_FOLLOW_TYPE = :ycfState AND SF_FOLLOW_STATE =:wcState";
        int ycfsfwcl = sysDao.getServiceReadDo().findCount(sfwclycf,map);
        mapCount.put("ycfsfwcl",ycfsfwcl);
        //孕产妇随访完成率
        double ycfwcl = 0;
        if(ycfsfzl!=0){
            ycfwcl=MyMathUtil.div(Double.valueOf(ycfsfwcl),Double.valueOf(ycfsfzl),6)*100;
            ycfwcl=MyMathUtil.round(ycfwcl,2);
        }
        mapCount.put("ycfwcl",String.valueOf(ycfwcl));
        //通用随访总数量
        map.put("tyState",ResidentMangeType.TY.getValue());
        String sfzlty = sqlZsfl + " AND SF_FOLLOW_TYPE = :tyState";
        int tysfzl = sysDao.getServiceReadDo().findCount(sfzlty,map);
        mapCount.put("tysfzl",tysfzl);
        //通用随访完成量
        String sfwclty = sqlZsfl +" AND SF_FOLLOW_TYPE = :tyState AND SF_FOLLOW_STATE =:wcState";
        int tysfwcl = sysDao.getServiceReadDo().findCount(sfwclty,map);
        mapCount.put("tysfwcl",tysfwcl);
        //通用随访完成率
        double tywcl = 0;
        if(tysfzl!=0){
            tywcl=MyMathUtil.div(Double.valueOf(tysfwcl),Double.valueOf(tysfzl),6)*100;
            tywcl=MyMathUtil.round(tywcl,2);
        }
        mapCount.put("tywcl",String.valueOf(tywcl));


        //近期随访量
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,-6);
        map.put("jqStart",ExtendDate.getYMD(cal)+" 00:00:00");
        map.put("jqEnd",ExtendDate.getYMD(Calendar.getInstance())+" 23:59:59");
        String jqSql =  sql + " AND SF_FOLLOW_DATE >=:jqStart AND SF_FOLLOW_DATE<=:jqEnd";
        if(StringUtils.isNotBlank(qvo.getAreaId())){
            jqSql += " AND SF_HOS_AREA_CODE LIKE :areaCode";
        }
        if(StringUtils.isNotBlank(qvo.getHospId())){
            jqSql += " AND SF_HOS_ID = :hospId";
        }
        if(StringUtils.isNotBlank(qvo.getTeamId())){
            jqSql += " AND SF_TEAM_ID = :teamId";
        }
        int jqsfl = sysDao.getServiceReadDo().findCount(jqSql,map);
        mapCount.put("jqsfl",jqsfl);

        //这段日期完成的随访量

        String sqlWcl = sqlZsfl + " AND SF_FOLLOW_STATE =:wcState";
        int wcsfl = sysDao.getServiceReadDo().findCount(sqlWcl,map);
        mapCount.put("wcsfl",wcsfl);

        //这段时间随访完成率
        double wclv = 0;
        if(jqsfl!=0){
            wclv=MyMathUtil.div(Double.valueOf(wcsfl),Double.valueOf(zsfl),6)*100;
            wclv=MyMathUtil.round(wclv,2);
        }
        mapCount.put("sfwcl",String.valueOf(wclv));

        //每月的随访信息
        List<Map<String,String>> list = ExtendDateUtil.getListBetweenMonthMap(qvo.getYearStart(),qvo.getYearEnd());
        for(Map<String,String> ls:list){
            map.put("mStart",ls.get("monthStart"));
            map.put("mEnd",ls.get("monthEnd"));
            Map<String,Object> mapMounth = new HashMap<String,Object>();
            //日期
            mapMounth.put("mounthDate",ls.get("monthStart").substring(0,7));
            //每月随访总量
            String sqlM = sql + " AND SF_FOLLOW_DATE >=:mStart AND SF_FOLLOW_DATE<=:mEnd";
            if(StringUtils.isNotBlank(qvo.getAreaId())){
                sqlM += " AND SF_HOS_AREA_CODE LIKE :areaCode";
            }
            if(StringUtils.isNotBlank(qvo.getHospId())){
                sqlM += " AND SF_HOS_ID = :hospId";
            }
            if(StringUtils.isNotBlank(qvo.getTeamId())){
                sqlM += " AND SF_TEAM_ID = :teamId";
            }
            int msfzl = sysDao.getServiceReadDo().findCount(sqlM,map);
            mapMounth.put("msfzl",msfzl);
            //每月的血压随访总量
            String sqlMxy = sqlM + " AND SF_FOLLOW_TYPE = :xyState";
            int xyMzl = sysDao.getServiceReadDo().findCount(sqlMxy,map);
            mapMounth.put("xyMzl",xyMzl);
            //每月的血糖随访总量
            String sqlMxt = sqlM + " AND SF_FOLLOW_TYPE = :xtState";
            int xtMzl = sysDao.getServiceReadDo().findCount(sqlMxt,map);
            mapMounth.put("xtMzl",xtMzl);
            //每月的完成量
            String sqlMwc= sqlM+ " AND SF_FOLLOW_STATE =:wcState";
            int msfwcl = sysDao.getServiceReadDo().findCount(sqlMwc,map);
            mapMounth.put("msfwcl",msfwcl);
            //每月完成的血压量
            String sqlMwcxy = sqlMwc + " AND SF_FOLLOW_TYPE = :xyState";
            int mwcxy = sysDao.getServiceReadDo().findCount(sqlMwcxy,map);
            mapMounth.put("mwcxy",mwcxy);
            //每月完成的血糖量
            String sqlMwcxt = sqlMwc + " AND SF_FOLLOW_TYPE = :xtState";
            int mwcxt =sysDao.getServiceReadDo().findCount(sqlMwcxt,map);
            mapMounth.put("mwcxt",mwcxt);
            //每月完成率
            double mwclv =0;
            if(msfzl!=0){
                mwclv = MyMathUtil.div(Double.valueOf(msfwcl),Double.valueOf(msfzl),6)*100;
                mwclv = MyMathUtil.round(mwclv,2);
            }
            mapMounth.put("mwclv",String.valueOf(mwclv));
            //每月完成血压率
            double mwcxylv =0;
            if(xyMzl!=0){
                mwcxylv=MyMathUtil.div(Double.valueOf(mwcxy),Double.valueOf(xyMzl),6)*100;
                mwcxylv=MyMathUtil.round(mwcxylv,2);
            }
            mapMounth.put("mwcxylv",String.valueOf(mwcxylv));
            //每月完成血糖率
            double mwcxtlv =0;
            if(xtMzl!=0){
                mwcxtlv = MyMathUtil.div(Double.valueOf(mwcxt),Double.valueOf(xtMzl),6)*100;
                mwcxtlv = MyMathUtil.round(mwcxtlv,2);
            }
            mapMounth.put("mwcxtlv",String.valueOf(mwcxtlv));
            lsss.add(mapMounth);
        }
        mapCount.put("msf",lsss);

        return mapCount;
    }

    /**
     * 遵从医嘱情况
     * @param qvo areaId 区域编码
     * @param qvo hospId 医院id
     * @param qvo teamId 团队id
     * @param qvo yearStart 开始时间（yyyy-MM)
     * @param qvo yearEnd 结束时间(yyyy-MM)
     * @return
     */
    @Override
    public Map<String, Object> findFollowDoctor(ResidentVo qvo) throws Exception {
        Map<String,Object> returnMap = new HashMap<>();
        List<Map<String, Object>> chartList = new ArrayList<>();
        String startDate = ExtendDate.getFirstDayOfMonth(qvo.getYearStart());
        String endDate = ExtendDate.getLastDayOfMonth(qvo.getYearEnd());
        List<Map<String,String>> list = ExtendDate.fWeek(startDate,endDate);
        //每月遵从医嘱情况
        for (Map<String,String> m:list) {
            Map<String, Object> map = new HashMap<>();
            Map<String,Object> mapCount =  new HashMap<>();
            map.put("startDate", m.get("startDate"));
            map.put("endDate", m.get("endDate"));
            String sql1 = "SELECT count(1) FROM APP_DRUG_GUIDE WHERE DG_END_TIME BETWEEN :startDate AND :endDate ";
            String sql2 = "SELECT count(1) FROM APP_DRUG_GUIDE WHERE DG_END_TIME BETWEEN :startDate AND :endDate " +
                    "AND (DG_FOLLOW_GUIDE IS NULL OR DG_FOLLOW_GUIDE = '0') ";
            if(StringUtils.isNotBlank(qvo.getAreaId())){
                map.put("areaId",AreaUtils.getAreaCode(qvo.getAreaId())+"%");
                sql1 += " AND DG_AREA_CODE LIKE :areaId ";
                sql2 += " AND DG_AREA_CODE LIKE :areaId ";
            }else if(StringUtils.isNotBlank(qvo.getHospId())){
                map.put("hospId",qvo.getHospId());
                sql1 +=" AND DG_HOSP_ID = :hospId ";
                sql2 +=" AND DG_HOSP_ID = :hospId ";
            }else if(StringUtils.isNotBlank(qvo.getTeamId())){
                map.put("teamId",qvo.getTeamId());
                sql1 +=" AND DG_TEAM_ID = :teamId ";
                sql2 +=" AND DG_TEAM_ID = :teamId ";
            }
            int count = sysDao.getServiceReadDo().gteSqlCount(sql1,map);
            int unFollowCount = sysDao.getServiceReadDo().gteSqlCount(sql2,map);
            mapCount.put("count",count);
            mapCount.put("unFollowCount",unFollowCount);
            mapCount.put("week1",m.get("startDate").substring(0,10));
            mapCount.put("week2",m.get("endDate").substring(0,10));
            chartList.add(mapCount);
        }

        String sql1 = "SELECT count(1) FROM APP_DRUG_GUIDE WHERE DG_END_TIME BETWEEN :startDate AND :endDate ";
        String sql2 = "SELECT count(1) FROM APP_DRUG_GUIDE WHERE DG_END_TIME BETWEEN :startDate AND :endDate " +
                "AND (DG_FOLLOW_GUIDE IS NULL OR DG_FOLLOW_GUIDE = '0') ";
        HashMap<String,Object> map1 = new HashMap<>();
        map1.put("startDate", ExtendDate.getFirstDayOfMonth(qvo.getYearStart())+" 00:00:00");
        map1.put("endDate",ExtendDate.getLastDayOfMonth(qvo.getYearEnd())+" 23:59:59");

        HashMap<String,Object> map2 = new HashMap<>();
        map2.put("startDate", ExtendDate.add(Calendar.getInstance(),-1,Calendar.WEEK_OF_YEAR));
        map2.put("endDate",Calendar.getInstance());
        if(StringUtils.isNotBlank(qvo.getAreaId())){
            map1.put("areaId",AreaUtils.getAreaCode(qvo.getAreaId())+"%");
            map2.put("areaId",AreaUtils.getAreaCode(qvo.getAreaId())+"%");
            sql1 += " AND DG_AREA_CODE LIKE :areaId ";
            sql2 += " AND DG_AREA_CODE LIKE :areaId ";
        }else if(StringUtils.isNotBlank(qvo.getHospId())){
            map1.put("hospId",qvo.getHospId());
            map2.put("hospId",qvo.getHospId());
            sql1 +=" AND DG_HOSP_ID = :hospId ";
            sql2 +=" AND DG_HOSP_ID = :hospId ";
        }else if(StringUtils.isNotBlank(qvo.getTeamId())){
            map1.put("teamId",qvo.getTeamId());
            map2.put("teamId",qvo.getTeamId());
            sql1 +=" AND DG_TEAM_ID = :teamId ";
            sql2 +=" AND DG_TEAM_ID = :teamId ";
        }
        //总时间情况
        int totalCount = sysDao.getServiceReadDo().gteSqlCount(sql1,map1);
        int totalUnFollowCount = sysDao.getServiceReadDo().gteSqlCount(sql2,map1);
        //近一周情况
        int weekCount = sysDao.getServiceReadDo().gteSqlCount(sql1,map2);
        int weekUnFollowCount = sysDao.getServiceReadDo().gteSqlCount(sql2,map2);

        returnMap.put("chartList",chartList);
        returnMap.put("totalCount",totalCount);
        returnMap.put("totalUnFollowCount",totalUnFollowCount);
        returnMap.put("weekCount",weekCount);
        returnMap.put("weekUnFollowCount",weekUnFollowCount);
        return returnMap;
    }

    /**
     * 拒转签情况
     * @param qvo areaId 区域编码
     * @param qvo hospId 医院id
     * @param qvo teamId 团队id
     * @param qvo yearStart 开始时间（yyyy-MM)
     * @param qvo yearEnd 结束时间(yyyy-MM)
     * @return
     */
    @Override
    public Map<String, Object> findRefuseToSign(ResidentVo qvo)  throws Exception{
        Map<String,Object> returnMap = new HashMap<>();
        List<Map<String, Object>> chartList = new ArrayList<>();
        List<Map<String,String>> list = ExtendDateUtil.getListBetweenMonthMap(qvo.getYearStart(),qvo.getYearEnd());
        //每月拒转签情况
        for (Map<String,String> m:list) {
            Map<String, Object> map = new HashMap<>();
            Map<String,Object> mapCount =  new HashMap<>();
            map.put("startDate", m.get("monthStart"));
            map.put("endDate", m.get("monthEnd"));
            String sqlRefuse="SELECT count(1) FROM APP_REFUSE_SIGN  WHERE RS_REFUSE_TIME BETWEEN :startDate AND :endDate ";
            String sqlSign = "SELECT count(1) FROM APP_REFUSE_SIGN  WHERE RS_SIGN_TIME IS NOT NULL AND RS_SIGN_TIME BETWEEN :startDate AND :endDate ";
            if(StringUtils.isNotBlank(qvo.getAreaId())){
                map.put("areaId",AreaUtils.getAreaCode(qvo.getAreaId())+"%");
                sqlRefuse += " AND RS_REFUSE_HOSP_AREA_CODE like :areaId ";
                sqlSign +=" AND RS_SIGN_HOSP_AREA_CODE LIKE :areaId ";
            }else if(StringUtils.isNotBlank(qvo.getHospId())){
                map.put("hospId",qvo.getHospId());
                sqlRefuse += " AND RS_REFUSE_HOSP_ID = :hospId ";
                sqlSign +=" AND RS_SIGN_HOSP_ID = :hospId ";
            }else if(StringUtils.isNotBlank(qvo.getTeamId())){
                map.put("teamId",qvo.getTeamId());
                sqlRefuse += " AND RS_REFUSE_TEAM_ID = :teamId ";
                sqlSign +=" AND RS_SIGN_TEAM_ID = :teamId ";
            }
            int refuseCount = sysDao.getServiceReadDo().gteSqlCount(sqlRefuse,map);
            int signCount = sysDao.getServiceReadDo().gteSqlCount(sqlSign,map);
            mapCount.put("refuseCount",refuseCount);
            mapCount.put("signCount",signCount);
            mapCount.put("date",m.get("monthStart").substring(0,7));
            chartList.add(mapCount);
        }

        String sqlRefuse="SELECT count(1) FROM APP_REFUSE_SIGN  WHERE RS_REFUSE_TIME BETWEEN :startDate AND :endDate ";
        String sqlSign = "SELECT count(1) FROM APP_REFUSE_SIGN  WHERE RS_SIGN_TIME IS NOT NULL AND RS_SIGN_TIME BETWEEN :startDate AND :endDate ";
        HashMap<String,Object> map1 = new HashMap<>();
        map1.put("startDate", ExtendDate.getFirstDayOfMonth(qvo.getYearStart())+" 00:00:00");
        map1.put("endDate",ExtendDate.getLastDayOfMonth(qvo.getYearEnd())+" 23:59:59");

        HashMap<String,Object> map2 = new HashMap<>();
        map2.put("startDate", ExtendDate.add(Calendar.getInstance(),-1,Calendar.WEEK_OF_YEAR));
        map2.put("endDate",Calendar.getInstance());
        if(StringUtils.isNotBlank(qvo.getAreaId())){
            map1.put("areaId",AreaUtils.getAreaCode(qvo.getAreaId())+"%");
            map2.put("areaId",AreaUtils.getAreaCode(qvo.getAreaId())+"%");
            sqlRefuse += " AND RS_REFUSE_HOSP_AREA_CODE like :areaId ";
            sqlSign +=" AND RS_SIGN_HOSP_AREA_CODE LIKE :areaId ";
        }else if(StringUtils.isNotBlank(qvo.getHospId())){
            map1.put("hospId",qvo.getHospId());
            map2.put("hospId",qvo.getHospId());
            sqlRefuse += " AND RS_REFUSE_HOSP_ID = :hospId ";
            sqlSign +=" AND RS_SIGN_HOSP_ID = :hospId ";
        }else if(StringUtils.isNotBlank(qvo.getTeamId())){
            map1.put("teamId",qvo.getTeamId());
            map2.put("teamId",qvo.getTeamId());
            sqlRefuse += " AND RS_REFUSE_TEAM_ID = :teamId ";
            sqlSign +=" AND RS_SIGN_TEAM_ID = :teamId ";
        }
        //总时间情况
        int totalRefuse = sysDao.getServiceReadDo().gteSqlCount(sqlRefuse,map1);
        int totalSign = sysDao.getServiceReadDo().gteSqlCount(sqlSign,map1);
        //近一周情况
        int weekRefuse = sysDao.getServiceReadDo().gteSqlCount(sqlRefuse,map2);
        int weekSign = sysDao.getServiceReadDo().gteSqlCount(sqlSign,map2);

        returnMap.put("chartList",chartList);
        returnMap.put("totalRefuse",totalRefuse);
        returnMap.put("totalSign",totalSign);
        returnMap.put("weekRefuse",weekRefuse);
        returnMap.put("weekSign",weekSign);
        return returnMap;

    }

    /**
     * 健康干预
     * @param qvo areaId 区域编码
     * @param qvo hospId 医院id
     * @param qvo teamId 团队id
     * @param qvo yearStart 开始时间（yyyy-MM)
     * @param qvo yearEnd 结束时间(yyyy-MM)
     * @return
     */
    @Override
    public Map<String, Object> findGuideWork(ResidentVo qvo)  throws Exception{
        Map<String,Object> mapCount = new HashMap<String,Object>();
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT COUNT(1) FROM APP_HEALTH_MEDDLE WHERE 1=1 ";
        String sqlzs = "SELECT COUNT(1) FROM APP_HEALTH_MEDDLE WHERE 1=1 ";
        if(StringUtils.isNotBlank(qvo.getYearStart())){
            map.put("startDate", ExtendDate.getFirstDayOfMonth(qvo.getYearStart())+" 00:00:00");
            sqlzs += " AND HM_CREATE_TIME >=:startDate";
        }
        if(StringUtils.isNotBlank(qvo.getYearEnd())){
            map.put("endDate",ExtendDate.getLastDayOfMonth(qvo.getYearEnd())+" 23:59:59");
            sqlzs += " AND HM_CREATE_TIME <=:endDate";
        }
        if(StringUtils.isNotBlank(qvo.getAreaId())){
            map.put("areaId",AreaUtils.getAreaCode(qvo.getAreaId())+"%");
            sqlzs += " AND HM_AREA_CODE LIKE :areaId";
            sql += " AND HM_AREA_CODE LIKE :areaId";
        }
        if(StringUtils.isNotBlank(qvo.getHospId())){
            map.put("hospId",qvo.getHospId());
            sqlzs += " AND HM_HOSP_ID = :hospId";
            sql += " AND HM_HOSP_ID = :hospId";
        }
        if(StringUtils.isNotBlank(qvo.getTeamId())){
            map.put("teamId",qvo.getTeamId());
            sqlzs += " AND HM_TEAM_ID = :teamId";
            sql += " AND HM_TEAM_ID = :teamId";
        }
        //总干预人数
        int gyzs = sysDao.getServiceReadDo().findCount(sqlzs,map);
        mapCount.put("gyzs",gyzs);
        List<Map<String,Object>> liss = new ArrayList<Map<String, Object>>();
        //每月的干预总数
        List<Map<String,String>> list = ExtendDateUtil.getListBetweenMonthMap(qvo.getYearStart(),qvo.getYearEnd());
        for(Map<String,String> ls:list) {
            map.put("mStart", ls.get("monthStart"));
            map.put("mEnd", ls.get("monthEnd"));
            Map<String,Object> mapMounth = new HashMap<String,Object>();
            String mgyzs = sql + " AND HM_CREATE_TIME >:mStart AND HM_CREATE_TIME<=:mEnd";
            //每月干预数
            int mgys = sysDao.getServiceReadDo().findCount(mgyzs,map);
            mapMounth.put("mgys",mgys);
            mapMounth.put("mounthDate",ls.get("monthStart").substring(0,7));
            liss.add(mapMounth);
        }
        mapCount.put("mgytj",liss);
        return mapCount;
    }

    /**
     * 随访量统计
     * @param qvo areaId 区域编码
     * @param qvo hospId 医院id
     * @param qvo teamId 团队id
     * @param qvo yearStart 开始时间（yyyy-MM)
     * @param qvo yearEnd 结束时间(yyyy-MM)
     * @return
     */
    @Override
    public Map<String, Object> findIndexFollowPlan(ResidentVo qvo) throws Exception {
        Map<String, Object> map = new HashMap<String,Object>();
        Map<String, Object> returnMap = new HashMap<String,Object>();
        List<AppManagePublicEntity> lsEntity = new ArrayList<AppManagePublicEntity>();
        String sql = " SELECT count(1) FROM APP_FOLLOW_PLAN WHERE 1=1";
        sql +=" AND SF_FOLLOW_DATE>=:start AND SF_FOLLOW_DATE <=:end";
        sql +=" AND SF_FOLLOW_STATE IN :state";
        if(StringUtils.isNotBlank(qvo.getYearStart())){
            map.put("start", qvo.getYearStart()+" 00:00:00");
        }
        if(StringUtils.isNotBlank(qvo.getYearEnd())){
            map.put("end",qvo.getYearEnd()+" 23:59:59");
        }
        String[] followState = new String[]{FollowPlanState.YIWANCHENG.getValue(),FollowPlanState.SIWANG.getValue(),FollowPlanState.SHIFANG.getValue()};
        map.put("state", followState);
        if(StringUtils.isNotBlank(qvo.getAreaId())){
            if (AreaUtils.getAreaCode(qvo.getAreaId()).length() == 6) {//区级行政，往下查看医院的排名
                //区下的医院集合
                List<AppHospDept> hospDepts = sysDao.getServiceReadDo().loadByPk(AppHospDept.class, "hospUpcityareaId", qvo.getAreaId());
                if(hospDepts != null && hospDepts.size() >0){
                    for (AppHospDept hosp : hospDepts) {
                        map.put("HOSP_ID",hosp.getId());
                        sql += " AND SF_HOS_ID = :HOSP_ID";
                        int count = this.sysDao.getServiceReadDo().findCount(sql,map);
                        AppManagePublicEntity entity = new AppManagePublicEntity();
                        entity.setName(hosp.getHospName());
                        entity.setValue(String.valueOf(count));
                        lsEntity.add(entity);
                    }
                }
            }else{
                List<CdAddressEntity> ls = sysDao.getCdAddressDao().findByUpIdOrNot(qvo.getAreaId());
                if(ls != null && ls.size() >0){
                    for(CdAddressEntity p : ls){
                        map.put("AREA_CODE",AreaUtils.getAreaCode(p.getId())+"%");
                        sql += " AND SF_HOS_AREA_CODE LIKE :AREA_CODE";
                        int count = this.sysDao.getServiceReadDo().findCount(sql,map);
                        AppManagePublicEntity entity = new AppManagePublicEntity();
                        entity.setName(p.getAreaSname());
                        entity.setValue(String.valueOf(count));
                        lsEntity.add(entity);
                    }
                }
            }
            returnMap.put("ls", JsonUtil.toJson(lsEntity));
        }else if(StringUtils.isNotBlank(qvo.getHospId())){
            AppHospDept dept = (AppHospDept) this.sysDao.getServiceReadDo().find(AppHospDept.class,qvo.getHospId());
            map.put("HOSP_ID",qvo.getHospId());
            sql += " AND SF_HOS_ID = :HOSP_ID";
            int count = this.sysDao.getServiceReadDo().findCount(sql,map);
            AppManagePublicEntity entity = new AppManagePublicEntity();
            entity.setName(dept.getHospName());
            entity.setValue(String.valueOf(count));
            returnMap.put("count",JsonUtil.toJson(entity));
            sql = " SELECT\n" +
                    "\tc.PATIENT_NAME name,\n" +
                    "\tc.PATIENT_TEL tel,\n" +
                    "\tc.PATIENT_ADDRESS address,\n" +
                    "\tc.PATIENT_IDNO idno,\n" +
                    "\tc.PATIENT_GENDER gender,\n" +
                    "\t'' age,\n" +
                    "\ta.SF_CREATE_DATE date,\n" +
                    "\ta.SF_TYPE_NUM num,\n" +
                    "\ta.SF_FOLLOW_TYPE type,\n" +
                    "\ta.ID followId \n"+
                    "FROM\n" +
                    "\tAPP_FOLLOW_PLAN a\n" +
                    "LEFT JOIN APP_PATIENT_USER c ON a.SF_FOLLOW_PATIENTID = c.ID\n" +
                    "WHERE 1=1";
            sql +=" AND SF_FOLLOW_DATE>=:start AND SF_FOLLOW_DATE <=:end";
            sql +=" AND SF_FOLLOW_STATE IN :state";
            sql += " AND SF_HOS_ID = :HOSP_ID";
            sql += " ORDER BY a.SF_CREATE_DATE DESC ";
            List<AppFollowPlanManageEntity> ls = sysDao.getServiceReadDo().findSqlMapRVo(sql,map, AppFollowPlanManageEntity.class,qvo);
            returnMap.put("ls", JsonUtil.toJson(ls));

        }
        return returnMap;
    }

    /**
     * 健康指导统计
     * @param qvo areaId 区域编码
     * @param qvo hospId 医院id
     * @param qvo teamId 团队id
     * @param qvo yearStart 开始时间（yyyy-MM)
     * @param qvo yearEnd 结束时间(yyyy-MM)
     * @return
     */
    @Override
    public Map<String, Object> findIndexGuide(ResidentVo qvo) throws Exception {
        Map<String, Object> map = new HashMap<String,Object>();
        Map<String, Object> returnMap = new HashMap<String,Object>();
        List<AppManagePublicEntity> lsEntity = new ArrayList<AppManagePublicEntity>();
        String sql = " SELECT count(1) FROM APP_HEALTH_MEDDLE WHERE 1=1";
        sql +=" AND HM_CREATE_TIME >=:start AND HM_CREATE_TIME <=:end ";
        if(StringUtils.isNotBlank(qvo.getYearStart())){
            map.put("start", qvo.getYearStart()+" 00:00:00");
        }
        if(StringUtils.isNotBlank(qvo.getYearEnd())){
            map.put("end",qvo.getYearEnd()+" 23:59:59");
        }
        if(StringUtils.isNotBlank(qvo.getAreaId())){
            if (AreaUtils.getAreaCode(qvo.getAreaId()).length() == 6) {//区级行政，往下查看医院的排名
                //区下的医院集合
                List<AppHospDept> hospDepts = sysDao.getServiceReadDo().loadByPk(AppHospDept.class, "hospUpcityareaId", qvo.getAreaId());
                if(hospDepts != null && hospDepts.size() >0){
                    for (AppHospDept hosp : hospDepts) {
                        map.put("HOSP_ID",hosp.getId());
                        sql += " AND HM_HOSP_ID = :HOSP_ID";
                        int count = this.sysDao.getServiceReadDo().findCount(sql,map);
                        AppManagePublicEntity entity = new AppManagePublicEntity();
                        entity.setName(hosp.getHospName());
                        entity.setValue(String.valueOf(count));
                        lsEntity.add(entity);
                    }
                }
            }else{
                List<CdAddressEntity> ls = sysDao.getCdAddressDao().findByUpIdOrNot(qvo.getAreaId());
                if(ls != null && ls.size() >0){
                    for(CdAddressEntity p : ls){
                        map.put("AREA_CODE",AreaUtils.getAreaCode(p.getId())+"%");
                        sql += " AND HM_AREA_CODE LIKE :AREA_CODE";
                        int count = this.sysDao.getServiceReadDo().findCount(sql,map);
                        AppManagePublicEntity entity = new AppManagePublicEntity();
                        entity.setName(p.getAreaSname());
                        entity.setValue(String.valueOf(count));
                        lsEntity.add(entity);
                    }
                }
            }
            returnMap.put("ls", JsonUtil.toJson(lsEntity));
        }else if(StringUtils.isNotBlank(qvo.getHospId())){
            AppHospDept dept = (AppHospDept) this.sysDao.getServiceReadDo().find(AppHospDept.class,qvo.getHospId());
            map.put("HOSP_ID",qvo.getHospId());
            sql += " AND HM_HOSP_ID = :HOSP_ID";
            int count = this.sysDao.getServiceReadDo().findCount(sql,map);
            AppManagePublicEntity entity = new AppManagePublicEntity();
            entity.setName(dept.getHospName());
            entity.setValue(String.valueOf(count));
            returnMap.put("count",JsonUtil.toJson(entity));
            sql = " SELECT a.ID id,a.HM_TITLE title,a.HM_IMAGE_URL imageUrl,a.HM_PATIENT_ID toUserId,'' toUserName,a.HM_CONTENT content," +
                    "a.HM_DR_ID drId,a.HM_MED_TYPE medTypeName,a.HM_MED_TYPE medType,a.HM_DIS_TYPE disTypeName,a.HM_DIS_TYPE disType," +
                    "date_format(a.HM_CREATE_TIME,'%Y-%c-%d %H:%i:%s') time,'' drName,'' drType FROM APP_HEALTH_MEDDLE a WHERE 1=1";
            sql +=" AND HM_CREATE_TIME >=:start AND HM_CREATE_TIME <=:end ";
            sql += " AND a.HM_HOSP_ID = :HOSP_ID ";
            sql += " ORDER BY a.HM_CREATE_TIME DESC";
            List<AppHealthGuideEntity> ls = sysDao.getServiceReadDo().findSqlMapRVo(sql,map, AppHealthGuideEntity.class,qvo);
            if(ls!=null && ls.size()>0){
                returnMap.put("ls", JsonUtil.toJson(ls));
            }else{
                ls = new ArrayList<>();
                returnMap.put("ls", JsonUtil.toJson(ls));
            }
        }
        return returnMap;
    }

    /**
     * 健康教育统计
     * @param qvo areaId 区域编码
     * @param qvo hospId 医院id
     * @param qvo teamId 团队id
     * @param qvo yearStart 开始时间（yyyy-MM)
     * @param qvo yearEnd 结束时间(yyyy-MM)
     * @return
     */
    @Override
    public Map<String, Object> findIndexHealth(ResidentVo qvo) throws Exception {
        Map<String, Object> map = new HashMap<String,Object>();
        Map<String, Object> returnMap = new HashMap<String,Object>();
        List<AppManagePublicEntity> lsEntity = new ArrayList<AppManagePublicEntity>();
        String sql = " SELECT count(1) FROM APP_USER_HEALTHED WHERE 1=1";
        sql +=" AND HED_CREATE_TIME >=:start AND HED_CREATE_TIME <=:end ";
        if(StringUtils.isNotBlank(qvo.getYearStart())){
            map.put("start", qvo.getYearStart()+" 00:00:00");
        }
        if(StringUtils.isNotBlank(qvo.getYearEnd())){
            map.put("end",qvo.getYearEnd()+" 23:59:59");
        }
        if(StringUtils.isNotBlank(qvo.getAreaId())){
            if (AreaUtils.getAreaCode(qvo.getAreaId()).length() == 6) {//区级行政，往下查看医院的排名
                //区下的医院集合
                List<AppHospDept> hospDepts = sysDao.getServiceReadDo().loadByPk(AppHospDept.class, "hospUpcityareaId", qvo.getAreaId());
                if(hospDepts != null && hospDepts.size() >0){
                    for (AppHospDept hosp : hospDepts) {
                        map.put("HOSP_ID",hosp.getId());
                        sql += " AND HED_HOSP_ID = :HOSP_ID";
                        int count = this.sysDao.getServiceReadDo().findCount(sql,map);
                        AppManagePublicEntity entity = new AppManagePublicEntity();
                        entity.setName(hosp.getHospName());
                        entity.setValue(String.valueOf(count));
                        lsEntity.add(entity);
                    }
                }
            }else{
                List<CdAddressEntity> ls = sysDao.getCdAddressDao().findByUpIdOrNot(qvo.getAreaId());
                if(ls != null && ls.size() >0){
                    for(CdAddressEntity p : ls){
                        map.put("AREA_CODE",AreaUtils.getAreaCode(p.getId())+"%");
                        sql += " AND HED_AREA_CODE LIKE :AREA_CODE";
                        int count = this.sysDao.getServiceReadDo().findCount(sql,map);
                        AppManagePublicEntity entity = new AppManagePublicEntity();
                        entity.setName(p.getAreaSname());
                        entity.setValue(String.valueOf(count));
                        lsEntity.add(entity);
                    }
                }
            }
            returnMap.put("ls", JsonUtil.toJson(lsEntity));
        }else if(StringUtils.isNotBlank(qvo.getHospId())){
            AppHospDept dept = (AppHospDept) this.sysDao.getServiceReadDo().find(AppHospDept.class,qvo.getHospId());
            map.put("HOSP_ID",qvo.getHospId());
            sql += " AND HED_HOSP_ID = :HOSP_ID";
            int count = this.sysDao.getServiceReadDo().findCount(sql,map);
            AppManagePublicEntity entity = new AppManagePublicEntity();
            entity.setName(dept.getHospName());
            entity.setValue(String.valueOf(count));
            returnMap.put("count",JsonUtil.toJson(entity));
            sql = "SELECT\n" +
                    "\ta.HED_TITLE title,\n" +
                    "\ta.HED_IMAGE_URL imageUrl,\n" +
                    "\ta.HED_DR_ID drId,\n" +
                    "a.HED_ID hedId,\n" +
                    "'' browse," +
                    "'' transmit," +
                    "'' enshrine," +
                    "\ta.HED_DR_NAME drName,\n" +
                    "\tb.DR_TYPE drType," +
                    "'' drTypeName,\n" +
                    "\ta.HED_CREATE_TIME time,\n" +
                    "\ta.ID id," +
                    "a.HED_CONTENT content," +
                    "\ta.HED_TYPE type," +
                    "a.HED_USER_ID toUserId," +
                    "a.HED_USER_NAME toUserName," +
                    "\t'' typeName\n" +
                    "FROM\n" +
                    "\tAPP_USER_HEALTHED a\n" +
                    "JOIN APP_DR_USER b ON a.HED_DR_ID = b.ID WHERE 1=1 ";
            sql +=" AND HED_CREATE_TIME >=:start AND HED_CREATE_TIME <=:end AND a.HED_HOSP_ID = :HOSP_ID ";
            sql += " ORDER BY a.HED_CREATE_TIME DESC ";
            List<AppHealthListEntity> ls = sysDao.getServiceReadDo().findSqlMapRVo(sql,map, AppHealthListEntity.class,qvo);
            if(ls!=null && ls.size()>0){
                returnMap.put("ls", JsonUtil.toJson(ls));
            }else{
                ls = new ArrayList<>();
                returnMap.put("ls", JsonUtil.toJson(ls));
            }
        }
        return returnMap;
    }

    /**
     * 求助量统计
     * @param qvo areaId 区域编码
     * @param qvo hospId 医院id
     * @param qvo teamId 团队id
     * @param qvo yearStart 开始时间（yyyy-MM)
     * @param qvo yearEnd 结束时间(yyyy-MM)
     * @return
     */
    @Override
    public Map<String, Object> findIndexHelp(ResidentVo qvo) throws Exception {
        Map<String, Object> map = new HashMap<String,Object>();
        Map<String, Object> returnMap = new HashMap<String,Object>();
        List<AppManagePublicEntity> lsEntity = new ArrayList<AppManagePublicEntity>();

        String sql = " SELECT count(1) FROM APP_SEEK_HELP WHERE 1=1";
        sql +=" AND SEEK_HELP_DATE >=:start AND SEEK_HELP_DATE <=:end ";
        if(StringUtils.isNotBlank(qvo.getYearStart())){
            map.put("start", qvo.getYearStart()+" 00:00:00");
        }
        if(StringUtils.isNotBlank(qvo.getYearEnd())){
            map.put("end",qvo.getYearEnd()+" 23:59:59");
        }
        if(StringUtils.isNotBlank(qvo.getAreaId())){
            if (AreaUtils.getAreaCode(qvo.getAreaId()).length() == 6) {//区级行政，往下查看医院的排名
                //区下的医院集合
                List<AppHospDept> hospDepts = sysDao.getServiceReadDo().loadByPk(AppHospDept.class, "hospUpcityareaId", qvo.getAreaId());
                if(hospDepts != null && hospDepts.size() >0){
                    for (AppHospDept hosp : hospDepts) {
                        map.put("HOSP_ID",hosp.getId());
                        sql += " AND SEEK_HOSP_ID = :HOSP_ID";
                        int count = this.sysDao.getServiceReadDo().findCount(sql,map);
                        AppManagePublicEntity entity = new AppManagePublicEntity();
                        entity.setName(hosp.getHospName());
                        entity.setValue(String.valueOf(count));
                        lsEntity.add(entity);
                    }
                }
            }else{
                List<CdAddressEntity> ls = sysDao.getCdAddressDao().findByUpIdOrNot(qvo.getAreaId());
                if(ls != null && ls.size() >0){
                    for(CdAddressEntity p : ls){
                        map.put("AREA_CODE",AreaUtils.getAreaCode(p.getId())+"%");
                        sql += " AND SEEK_AREA_CODE LIKE :AREA_CODE";
                        int count = this.sysDao.getServiceReadDo().findCount(sql,map);
                        AppManagePublicEntity entity = new AppManagePublicEntity();
                        entity.setName(p.getAreaSname());
                        entity.setValue(String.valueOf(count));
                        lsEntity.add(entity);
                    }
                }
            }
            returnMap.put("ls", JsonUtil.toJson(lsEntity));
        }else if(StringUtils.isNotBlank(qvo.getHospId())){
            AppHospDept dept = (AppHospDept) this.sysDao.getServiceReadDo().find(AppHospDept.class,qvo.getHospId());
            map.put("HOSP_ID",qvo.getHospId());
            sql += " AND SEEK_HOSP_ID = :HOSP_ID";
            int count = this.sysDao.getServiceReadDo().findCount(sql,map);
            AppManagePublicEntity entity = new AppManagePublicEntity();
            entity.setName(dept.getHospName());
            entity.setValue(String.valueOf(count));
            returnMap.put("count",JsonUtil.toJson(entity));
            sql = " SELECT\n" +
                    "\tc.PATIENT_NAME name,\n" +
                    "\tc.PATIENT_TEL tel,\n" +
                    "\tc.PATIENT_ADDRESS address,\n" +
                    "\tc.PATIENT_IDNO idno,\n" +
                    "\tc.PATIENT_GENDER gender,\n" +
                    "\t'' age,\n" +
                    "\ta.SEEK_HELP_DATE date\n" +
                    "FROM\n" +
                    "\tAPP_SEEK_HELP a\n" +
                    "LEFT JOIN APP_PATIENT_USER c ON a.SEEK_PATIENT_ID = c.ID\n" +
                    "WHERE 1=1 ";
            sql +=" AND SEEK_HELP_DATE >=:start AND SEEK_HELP_DATE <=:end ";
            sql += " AND SEEK_HOSP_ID = :HOSP_ID ORDER BY a.SEEK_HELP_DATE DESC";
            List<AppSeekHelpEntity> ls=sysDao.getServiceReadDo().findSqlMapRVo(sql,map, AppSeekHelpEntity.class,qvo);
            returnMap.put("ls", JsonUtil.toJson(ls));
        }
        return returnMap;
    }



    /**
     * 未缴费统计
     * @param qvo areaId 区域编码
     * @param qvo hospId 医院id
     * @param qvo teamId 团队id
     * @param qvo yearStart 开始时间（yyyy-MM)
     * @param qvo yearEnd 结束时间(yyyy-MM)
     * @return
     */
    @Override
    public Map<String, Object> findIndexUnpaid(ResidentVo qvo) throws Exception {
        Map<String, Object> map = new HashMap<String,Object>();
        Map<String, Object> returnMap = new HashMap<String,Object>();
        List<AppManagePublicEntity> lsEntity = new ArrayList<AppManagePublicEntity>();
        String sql = " SELECT count(1) FROM APP_SIGN_FORM t WHERE 1=1 AND t.SIGN_STATE IN (:SIGN_STATE) AND t.SIGN_PAY_STATE = :SIGN_PAY_STATE ";
        sql +="  AND t.SIGN_FROM_DATE >=:start AND t.SIGN_FROM_DATE <=:end ";
        String[] signStates = new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
        map.put("SIGN_STATE", signStates);
        map.put("SIGN_PAY_STATE", PayType.WEIJIAOFEI.getValue());
        if(StringUtils.isNotBlank(qvo.getYearStart())){
            map.put("start", qvo.getYearStart()+" 00:00:00");
        }
        if(StringUtils.isNotBlank(qvo.getYearEnd())){
            map.put("end",qvo.getYearEnd()+" 23:59:59");
        }
        if(StringUtils.isNotBlank(qvo.getAreaId())){
            if (AreaUtils.getAreaCode(qvo.getAreaId()).length() == 6) {//区级行政，往下查看医院的排名
                //区下的医院集合
                List<AppHospDept> hospDepts = sysDao.getServiceReadDo().loadByPk(AppHospDept.class, "hospUpcityareaId", qvo.getAreaId());
                if(hospDepts != null && hospDepts.size() >0){
                    for (AppHospDept hosp : hospDepts) {
                        map.put("HOSP_ID",hosp.getId());
                        sql += " AND SIGN_HOSP_ID = :HOSP_ID";
                        int count = this.sysDao.getServiceReadDo().findCount(sql,map);
                        AppManagePublicEntity entity = new AppManagePublicEntity();
                        entity.setName(hosp.getHospName());
                        entity.setValue(String.valueOf(count));
                        lsEntity.add(entity);
                    }
                }
            }else{
                List<CdAddressEntity> ls = sysDao.getCdAddressDao().findByUpIdOrNot(qvo.getAreaId());
                if(ls != null && ls.size() >0){
                    for(CdAddressEntity p : ls){
                        map.put("AREA_CODE",AreaUtils.getAreaCode(p.getId())+"%");
                        sql += " AND SIGN_AREA_CODE LIKE :AREA_CODE";
                        int count = this.sysDao.getServiceReadDo().findCount(sql,map);
                        AppManagePublicEntity entity = new AppManagePublicEntity();
                        entity.setName(p.getAreaSname());
                        entity.setValue(String.valueOf(count));
                        lsEntity.add(entity);
                    }
                }
            }
            returnMap.put("ls", JsonUtil.toJson(lsEntity));
        }else if(StringUtils.isNotBlank(qvo.getHospId())){
            AppHospDept dept = (AppHospDept) this.sysDao.getServiceReadDo().find(AppHospDept.class,qvo.getHospId());
            map.put("HOSP_ID",qvo.getHospId());
            sql += " AND SIGN_HOSP_ID = :HOSP_ID";
            int count = this.sysDao.getServiceReadDo().findCount(sql,map);
            AppManagePublicEntity entity = new AppManagePublicEntity();
            entity.setName(dept.getHospName());
            entity.setValue(String.valueOf(count));
            returnMap.put("count",JsonUtil.toJson(entity));
            sql = "SELECT\n" +
                    "\tc.ID id,\n" +
                    "\tc.PATIENT_NAME name,\n" +
                    "\tc.PATIENT_GENDER sex,\n" +
                    //"\tc.PATIENT_AGE age,\n" +
                    "\tc.PATIENT_TEL tel,\n" +
                    "\tc.PATIENT_IDNO patientIdno,\n" +
                    "'' age," +
                    "\tc.PATIENT_CARD patientCard,\n" +
                    "\tc.PATIENT_IMAGEURL imgurl,\n" +
                    "\ta.SIGN_SERVICE_A ssgg,\n" +
                    "\ta.SIGN_SERVICE_B jjyl,\n" +
                    "\ta.SIGN_TYPE signType,\n" +
                    "\ta.SIGN_URRENDER_REASON signUrrenderReason,\n" +
                    "\ta.SIGN_URRENDER_REASON_PATIENT signUrrenderReasonPatient,\n" +
                    "\ta.SIGN_OTHNER_REASON signOthnerReason,\n" +
                    "\t(SELECT GROUP_CONCAT(LABEL_TITLE) from APP_LABEL_GROUP g where g.LABEL_TYPE=3 and g.LABEL_SIGN_ID=a.ID) signPersGroup,\n" +
                    "\ta.SIGN_SERVICE_B_COLOR jjylcolor,\n" +
                    "\t(SELECT GROUP_CONCAT(LABEL_TITLE) from APP_LABEL_DISEASE g where g.LABEL_TYPE=2 and g.LABEL_SIGN_ID=a.ID) labtitle,\n" +
                    "\t(SELECT GROUP_CONCAT(LABEL_COLOR) from APP_LABEL_DISEASE g where g.LABEL_TYPE=2 and g.LABEL_SIGN_ID=a.ID) labcolor,\n" +
                    "\tDATE_FORMAT(a.SIGN_DATE,'%Y-%m-%d') signDate,\n" +
                    "\ta.SIGN_PAY_STATE signPayState\n" +
                    "FROM\n" +
                    "\tAPP_SIGN_FORM a\n" +
                    "LEFT JOIN APP_LABEL_GROUP b ON a.ID = b.LABEL_SIGN_ID\n" +
                    "LEFT JOIN APP_PATIENT_USER c ON a.SIGN_PATIENT_ID = c.ID\n" +
                    "where 1=1 AND a.SIGN_STATE IN (:SIGN_STATE) AND a.SIGN_PAY_STATE = :SIGN_PAY_STATE ";
            sql += "  AND a.SIGN_FROM_DATE >=:start AND a.SIGN_FROM_DATE <=:end ";
            sql += " AND a.SIGN_HOSP_ID = :HOSP_ID";
            sql += " GROUP BY c.ID";
            List<JmInfo> ls=sysDao.getServiceReadDo().findSqlMapRVo(sql,map, JmInfo.class,qvo);
            if(ls!=null && ls.size()>0){
                returnMap.put("ls", JsonUtil.toJson(ls));
            }else{
                ls = new ArrayList<>();
                returnMap.put("ls", JsonUtil.toJson(ls));
            }
        }
        return returnMap;
    }

    /**
     * 咨询统计(本月， 本年度 ，历史)
     * @param qvo
     * @return
     */
    @Override
    public Map<String, Object> findConsult(PerformanceVo qvo) throws Exception {
        Map<String,Object> returnMap = new HashMap<String,Object>();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("drId",qvo.getDrId());
        map.put("patientId",qvo.getPatientId());
        String sql = "SELECT COUNT(1) FROM APP_CONSULT_RECORD WHERE 1=1 ";
        sql += "AND CONREC_DR_ID =:drId AND CONREC_PATIENT_ID =:patientId ";
        //本月
        Calendar cal = Calendar.getInstance();
        String time = ExtendDate.getYM(cal);
        map.put("startTime",ExtendDate.getFirstDayOfMonth(time)+" 00:00:00");
        map.put("endTime",ExtendDate.getLastDayOfMonth(time)+ " 23:59:59");
        String sql1 = sql + " AND CONREC_INITIATE_TIME>=:startTime AND CONREC_INITIATE_TIME <=:endTime";
        int monthCount = sysDao.getServiceReadDo().findCount(sql1,map);
        returnMap.put("monthCount",monthCount);
        //本年度
        map.put("signFormDate",qvo.getSignFormDate());
        map.put("signToDate",qvo.getSignToDate());
        String sql2 = sql + " AND CONREC_INITIATE_TIME>=:signFormDate AND CONREC_INITIATE_TIME <=:signToDate";
        int yearCount = sysDao.getServiceReadDo().findCount(sql2,map);
        returnMap.put("yearCount",yearCount);
        //历史
        String sql3 = sql + " AND CONREC_INITIATE_TIME<:signFormDate OR CONREC_INITIATE_TIME>:signToDate";
        int hisCount = sysDao.getServiceReadDo().findCount(sql3,map);
        returnMap.put("hisCount",hisCount);
        return returnMap;
    }

    /**
     * 随访完成量/计划量
     * @param qvo
     * @return
     */
    @Override
    public Map<String, Object> findVisit(PerformanceVo qvo) throws Exception {
        Map<String,Object> returnMap = new HashMap<String,Object>();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("drId",qvo.getDrId());
        map.put("patientId",qvo.getPatientId());
        String sql = "SELECT COUNT(1) FROM APP_FOLLOW_PLAN WHERE 1=1 ";
        sql += " AND SF_FOLLOW_DOCTORID=:drId AND SF_FOLLOW_PATIENTID =:patientId ";
        //本月随访
        Calendar cal = Calendar.getInstance();
        String time = ExtendDate.getYM(cal);
        map.put("startTime",ExtendDate.getFirstDayOfMonth(time)+" 00:00:00");
        map.put("endTime",ExtendDate.getLastDayOfMonth(time)+ " 23:59:59");
        map.put("wcState",FollowPlanState.YIWANCHENG.getValue());
        String sql1 =  sql +" AND SF_FOLLOW_DATE >=:startTime AND SF_FOLLOW_DATE<=:endTime AND SF_FOLLOW_STATE=:wcState GROUP BY SF_FOLLOW_NUM";
        int wcMonth = sysDao.getServiceReadDo().findCount(sql1,map);
        returnMap.put("wcMonth",wcMonth);
        String sql2 = sql + " AND SF_FOLLOW_DATE >=:startTime AND SF_FOLLOW_DATE<=:endTime GROUP BY SF_FOLLOW_NUM";
        int weicMonth = sysDao.getServiceReadDo().findCount(sql2,map);
        returnMap.put("weicMonth",weicMonth);
        //本年度
        map.put("signFormDate",qvo.getSignFormDate());
        map.put("signToDate",qvo.getSignToDate());
        String sql3 =  sql +" AND SF_FOLLOW_DATE >=:signFormDate AND SF_FOLLOW_DATE<=:signToDate AND SF_FOLLOW_STATE=:wcState GROUP BY SF_FOLLOW_NUM";
        int wcYear = sysDao.getServiceReadDo().findCount(sql3,map);
        returnMap.put("wcYear",wcYear);
        String sql4 =  sql +" AND SF_FOLLOW_DATE >=:signFormDate AND SF_FOLLOW_DATE<=:signToDate  GROUP BY SF_FOLLOW_NUM";
        int weicYear = sysDao.getServiceReadDo().findCount(sql4,map);
        returnMap.put("weicYear",weicYear);
        //历史
        String sql5 =  sql +" AND SF_FOLLOW_DATE <:signFormDate OR SF_FOLLOW_DATE>:signToDate AND SF_FOLLOW_STATE=:wcState GROUP BY SF_FOLLOW_NUM";
        int wcHis = sysDao.getServiceReadDo().findCount(sql5,map);
        returnMap.put("wcHis",wcHis);
        String sql6 =  sql +" AND SF_FOLLOW_DATE <:signFormDate OR SF_FOLLOW_DATE>:signToDate  GROUP BY SF_FOLLOW_NUM";
        int weicHis = sysDao.getServiceReadDo().findCount(sql6,map);
        returnMap.put("weicHis",weicHis);
        return returnMap;
    }

    /**
     * 健康指导统计
     * @param qvo
     * @return
     */
    @Override
    public Map<String, Object> findGuide(PerformanceVo qvo) throws Exception {
        Map<String,Object> returnMap = new HashMap<String,Object>();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("drId",qvo.getDrId());
        map.put("patientId",qvo.getPatientId());
        String sql = "SELECT COUNT(1) FROM APP_HEALTH_MEDDLE WHERE 1=1 ";
        sql += " AND HM_DR_ID=:drId AND HM_PATIENT_ID=:patientId ";
        //本月
        Calendar cal = Calendar.getInstance();
        String time = ExtendDate.getYM(cal);
        map.put("startTime",ExtendDate.getFirstDayOfMonth(time)+" 00:00:00");
        map.put("endTime",ExtendDate.getLastDayOfMonth(time)+ " 23:59:59");
        String sql1 = sql + " AND HM_CREATE_TIME>=:startTime AND HM_CREATE_TIME<=:endTime";
        int guideMonth = sysDao.getServiceReadDo().findCount(sql1,map);
        returnMap.put("guideMonth",guideMonth);
        //本年度
        map.put("signFormDate",qvo.getSignFormDate());
        map.put("signToDate",qvo.getSignToDate());
        String sql2 = sql + " AND HM_CREATE_TIME>=:signFormDate AND HM_CREATE_TIME<=:signToDate";
        int guideYear = sysDao.getServiceReadDo().findCount(sql2,map);
        returnMap.put("guideYear",guideYear);
        //历史
        String sql3 = sql + " AND HM_CREATE_TIME<:signFormDate OR HM_CREATE_TIME>:signToDate";
        int guideHis = sysDao.getServiceReadDo().findCount(sql3,map);
        returnMap.put("guideHis",guideHis);

        return returnMap;
    }

    /**
     * 健康教育统计
     * @param qvo
     * @return
     */
    @Override
    public Map<String, Object> findHealth(PerformanceVo qvo) throws Exception {
        Map<String,Object> returnMap = new HashMap<String,Object>();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("drId",qvo.getDrId());
        map.put("patientId",qvo.getPatientId());
        String sql = "SELECT COUNT(1) FROM APP_USER_HEALTHED WHERE 1=1 ";
        sql += " AND HED_DR_ID =:drId AND HED_USER_ID=:patientId ";

        //本月
        Calendar cal = Calendar.getInstance();
        String time = ExtendDate.getYM(cal);
        map.put("startTime",ExtendDate.getFirstDayOfMonth(time)+" 00:00:00");
        map.put("endTime",ExtendDate.getLastDayOfMonth(time)+ " 23:59:59");
        String sql1 = sql + " AND HED_CREATE_TIME >=:startTime AND HED_CREATE_TIME <=:endTime";
        int healthMonth = sysDao.getServiceReadDo().findCount(sql1,map);
        returnMap.put("healthMonth",healthMonth);
        //本年度
        map.put("signFormDate",qvo.getSignFormDate());
        map.put("signToDate",qvo.getSignToDate());
        String sql2 = sql + " AND HED_CREATE_TIME>=:signFormDate AND HED_CREATE_TIME<=:signToDate";
        int healthYear = sysDao.getServiceReadDo().findCount(sql2,map);
        returnMap.put("healthYear",healthYear);
        //历史
        String sql3 = sql + " AND HED_CREATE_TIME<:signFormDate OR HED_CREATE_TIME>:signToDate";
        int healthHis = sysDao.getServiceReadDo().findCount(sql3,map);
        returnMap.put("healthHis",healthHis);

        return returnMap;
    }
}
