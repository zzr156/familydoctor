package com.ylz.bizDo.mangecount.dao.impl;

import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.bizDo.app.po.AppTeam;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.cd.po.CdAddressPeople;
import com.ylz.bizDo.mangecount.dao.AppRankAnalysisDao;
import com.ylz.bizDo.mangecount.vo.ResidentVo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.comEnum.SignFormType;
import com.ylz.packcommon.common.util.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 排名分析
 */
@Service("appRankAnalysisDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppRankAnalysisDaoImpl implements AppRankAnalysisDao {
    @Autowired
    private SysDao sysDao;

    /**
     * 综合排名
     * @param qvo areaId 区域主键 hospId 社区主键 teamId 团队主键
     * @return
     */
    @Override
    public Map<String,Object> findRanking(ResidentVo qvo) throws Exception {
        List<Map<String,Object>> list = new ArrayList<>();
        List<Map<String,Object>> list1 = new ArrayList<>();
        List<Map<String,Object>> list2 = new ArrayList<>();
        List<Map<String,Object>> list3 = new ArrayList<>();
        List<Map<String,Object>> list4 = new ArrayList<>();
        List<Map<String,Object>> list5 = new ArrayList<>();
        List<Map<String,Object>> list6 = new ArrayList<>();
        List<Map<String,Object>> list7 = new ArrayList<>();
        List<Map<String,Object>> list8 = new ArrayList<>();
        List<Map<String,Object>> list9 = new ArrayList<>();
        List<Map<String,Object>> list10 = new ArrayList<>();
        List<Map<String,Object>> list11 = new ArrayList<>();
        List<Map<String,Object>> list12 = new ArrayList<>();
        List<Map<String,Object>> list13 = new ArrayList<>();
        List<Map<String,Object>> list14 = new ArrayList<>();
        HashMap<String,List> listMap = new HashMap<>();
        listMap.put("list",list);
        listMap.put("list1",list1);
        listMap.put("list2",list2);
        listMap.put("list3",list3);
        listMap.put("list4",list4);
        listMap.put("list5",list5);
        listMap.put("list6",list6);
        listMap.put("list7",list7);
        listMap.put("list8",list8);
        listMap.put("list9",list9);
        listMap.put("list10",list10);
        listMap.put("list11",list11);
        listMap.put("list12",list12);
        listMap.put("list13",list13);
        listMap.put("list14",list14);
        HashMap<String,Object> map = new HashMap<>();
        if(StringUtils.isNotBlank(qvo.getAreaId())) {
            if (AreaUtils.getAreaCode(qvo.getAreaId()).length() == 6) {//区级行政，往下查看医院的排名
                //区下的医院集合
                List<AppHospDept> hospDepts = sysDao.getServiceReadDo().loadByPk(AppHospDept.class, "hospUpcityareaId", qvo.getAreaId());
                if(hospDepts!=null && !hospDepts.isEmpty()){
                    for (AppHospDept hosp : hospDepts) {
                        if(!"0".equals(hosp.getHospState())) {
                            //实时查询
                            BigDecimal work = getWorkRank(null, hosp.getId(), null, qvo.getYearStart(), qvo.getYearEnd());
                            BigDecimal follow = getFollowRank(null, hosp.getId(), null, qvo.getYearStart(), qvo.getYearEnd());
                            BigDecimal health = getHealthRank(null, hosp.getId(), null, qvo.getYearStart(), qvo.getYearEnd());
                            BigDecimal refuse = getRefuseToSignRank(null, hosp.getId(), null, qvo.getYearStart(), qvo.getYearEnd());
                            //查询调度排名
//                            BigDecimal work = getManageWorkRank(null,hosp.getId(),null,qvo.getYearStart(),qvo.getYearEnd());
//                            BigDecimal follow = getManageFollowRank(null,hosp.getId(),null,qvo.getYearStart(),qvo.getYearEnd());
//                            BigDecimal health = getManageHealthRank(null,hosp.getId(),null,qvo.getYearStart(),qvo.getYearEnd());
//                            BigDecimal refuse = getManageRefuseToSignRank(null,hosp.getId(),null,qvo.getYearStart(),qvo.getYearEnd());

                            myCount(hosp.getHospName(), null, hosp.getId(), work, follow, health, refuse, listMap);
                        }
                    }
                }

            }else{
                List<CdAddress> lsCdAddress = sysDao.getCdAddressDao().findUpCasheAreaCode(qvo.getAreaId());
                if(lsCdAddress!=null && !lsCdAddress.isEmpty()) {
                    for (CdAddress address : lsCdAddress) {
                        BigDecimal work = getWorkRank(address.getId(), null, null,qvo.getYearStart(),qvo.getYearEnd());
                        BigDecimal follow = getFollowRank(address.getId(), null, null,qvo.getYearStart(),qvo.getYearEnd());
                        BigDecimal health = getHealthRank(address.getId(), null, null,qvo.getYearStart(),qvo.getYearEnd());
                        BigDecimal refuse = getRefuseToSignRank(address.getId(), null, null,qvo.getYearStart(),qvo.getYearEnd());

                        //调度查询
//                        BigDecimal work = getManageWorkRank(address.getId(),null,null,qvo.getYearStart(),qvo.getYearEnd());
//                        BigDecimal follow = getManageFollowRank(address.getId(),null,null,qvo.getYearStart(),qvo.getYearEnd());
//                        BigDecimal health = getManageHealthRank(address.getId(),null,null,qvo.getYearStart(),qvo.getYearEnd());
//                        BigDecimal refuse = getManageRefuseToSignRank(address.getId(),null,null,qvo.getYearStart(),qvo.getYearEnd());

                        myCount(address.getAreaSname(),address.getId(),null,work,follow,health,refuse,listMap);
                    }
                }
            }
        //如果传入的是hospId,则返回该hospId下团队数据
        }else if(StringUtils.isNotBlank(qvo.getHospId())){
//            List<AppTeam> teamList = sysDao.getServiceReadDo().loadByPk(AppTeam.class,"teamHospId",qvo.getHospId());
            List<AppTeam> teamList = sysDao.getAppTeamDao().findAll(qvo.getHospId());
            if(teamList!=null && !teamList.isEmpty()) {
                for (AppTeam team : teamList) {
                    BigDecimal work = getWorkRank(null, null, team.getId(),qvo.getYearStart(),qvo.getYearEnd());
                    BigDecimal follow = getFollowRank(null, null, team.getId(),qvo.getYearStart(),qvo.getYearEnd());
                    BigDecimal health = getHealthRank(null, null, team.getId(),qvo.getYearStart(),qvo.getYearEnd());
                    BigDecimal refuse = getRefuseToSignRank(null, null, team.getId(),qvo.getYearStart(),qvo.getYearEnd());

                    //调度查询
//                    BigDecimal work = getManageWorkRank(null,null,team.getId(),qvo.getYearStart(),qvo.getYearEnd());
//                    BigDecimal follow = getManageFollowRank(null,null,team.getId(),qvo.getYearStart(),qvo.getYearEnd());
//                    BigDecimal health = getManageHealthRank(null,null,team.getId(),qvo.getYearStart(),qvo.getYearEnd());
//                    BigDecimal refuse = getManageRefuseToSignRank(null,null,team.getId(),qvo.getYearStart(),qvo.getYearEnd());

                    myCount(team.getTeamName(),null,null,work,follow,health,refuse,listMap);
                }
            }
        }
        Collections.sort(list, ComparatorUtils.getComparator());
        Collections.sort(list1, ComparatorUtils.getComparator());
        Collections.sort(list2, ComparatorUtils.getComparator());
        Collections.sort(list3, ComparatorUtils.getComparator());
        Collections.sort(list4, ComparatorUtils.getComparator());
        Collections.sort(list5, ComparatorUtils.getComparator());
        Collections.sort(list6, ComparatorUtils.getComparator());
        Collections.sort(list7, ComparatorUtils.getComparator());
        Collections.sort(list8, ComparatorUtils.getComparator());
        Collections.sort(list9, ComparatorUtils.getComparator());
        Collections.sort(list10, ComparatorUtils.getComparator());
        Collections.sort(list11, ComparatorUtils.getComparator());
        Collections.sort(list12, ComparatorUtils.getComparator());
        Collections.sort(list13, ComparatorUtils.getComparator());
        Collections.sort(list14, ComparatorUtils.getComparator());
        map.put("total",list);
        map.put("work",list1);
        map.put("follow",list2);
        map.put("health",list3);
        map.put("refuse",list4);
        map.put("workFollow",list5);
        map.put("workHealth",list6);
        map.put("workRefuse",list7);
        map.put("followHealth",list8);
        map.put("followRefuse",list9);
        map.put("healthRefuse",list10);
        map.put("workFollowHealth",list11);
        map.put("workFollowRefuse",list12);
        map.put("workHealthRefuse",list13);
        map.put("followHealthRefuse",list14);
        return map;
    }

    /**
     * 工作进度 完成率
     * @param addressId
     * @param hospId
     * @param teamId
     * @return
     */
    public BigDecimal getWorkRank(String addressId,String hospId, String teamId,String startDate,String endDate) throws Exception{
        int zgzl=0;
        int ywcl=0;
        HashMap map = new HashMap();
        String sqlwork =  "SELECT count(1) FROM APP_WORKING_PLAN WHERE 1=1 ";
        if(StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)){
            map.put("STARTDATE", ExtendDate.getFirstDayOfMonth(startDate)+" 00:00:00");
            map.put("ENDDATE",ExtendDate.getLastDayOfMonth(endDate)+" 23:59:59");
            sqlwork += " AND PLAN_DATE BETWEEN :STARTDATE AND :ENDDATE ";
        }
        if(addressId!=null){
            map.put("areaId", AreaUtils.getAreaCode(addressId)+"%");
            sqlwork += " AND PLAN_AREA_CODE like :areaId ";
        }
        if(hospId!=null){
            map.put("hospId", hospId);
            sqlwork += " AND PLAN_HOSP_ID = :hospId ";
        }
        if(teamId!=null){
            map.put("teamId", teamId);
            sqlwork +=" AND PLAN_TEAM_ID = :teamId ";
        }

        //总工作量
        zgzl = this.sysDao.getServiceReadDo().findCount(sqlwork,map);
        //已完成
        String sqlYwcl = sqlwork + " AND PLAN_STATE ='2' ";
        ywcl = this.sysDao.getServiceReadDo().findCount(sqlYwcl,map);
        BigDecimal wcl = new BigDecimal(0);
        //完成率
        if(zgzl > 0 ){
            wcl = new BigDecimal(ywcl*100).divide(new BigDecimal(zgzl),0,BigDecimal.ROUND_HALF_UP);
        }
        return wcl;
    }

    /**
     * 随访 完成率
     * @param addressId
     * @param hospId
     * @param teamId
     * @return
     */
    public BigDecimal getFollowRank(String addressId,String hospId, String teamId, String startDate, String endDate) throws Exception{
        int zsf=0;
        int wsf=0;
        HashMap map = new HashMap();
        String sqlFoll="SELECT count(1) FROM APP_FOLLOW_PLAN t WHERE 1=1 ";
        if(StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)){
            map.put("STARTDATE", ExtendDate.getFirstDayOfMonth(startDate)+" 00:00:00");
            map.put("ENDDATE",ExtendDate.getLastDayOfMonth(endDate)+" 23:59:59");
            sqlFoll += " AND t.SF_FOLLOW_DATE BETWEEN :STARTDATE AND :ENDDATE ";
        }
        if(StringUtils.isNotBlank(addressId)){
            map.put("areaId", AreaUtils.getAreaCode(addressId)+"%");
            sqlFoll += " AND t.SF_HOS_AREA_CODE like :areaId ";
        }
        if(StringUtils.isNotBlank(hospId)){
            map.put("hospId",hospId);
            sqlFoll += " AND t.SF_HOS_ID = :hospId ";
        }
        if(StringUtils.isNotBlank(teamId)){
            map.put("teamId",teamId);
            sqlFoll +=" AND t.SF_TEAM_ID = :teamId ";
        }
        //总数
        zsf = this.sysDao.getServiceReadDo().findCount(sqlFoll,map);
        //完成数
        String sqlFollw=sqlFoll+" AND t.SF_FOLLOW_STATE = 1";
        wsf = this.sysDao.getServiceReadDo().findCount(sqlFollw,map);
        //完成率
        BigDecimal swcl = new BigDecimal(0);
        if(zsf > 0 ){
            swcl = new BigDecimal(wsf*100).divide(new BigDecimal(zsf),0,BigDecimal.ROUND_HALF_UP);
        }
        return swcl;
    }

    /**
     * 健康干预 完成率
     * @param addressId
     * @param hospId
     * @param teamId
     * @return
     */
    public BigDecimal getHealthRank(String addressId,String hospId, String teamId,String startDate,String endDate) throws Exception{
        int wgsql=0;
        int zssql=0;
        HashMap map = new HashMap();
        String sqlwork2 =  "SELECT count(1) FROM APP_WORKING_PLAN WHERE 1=1 and PLAN_TYPE='2'";
        String gsql="SELECT COUNT(1) FROM APP_HEALTH_MEDDLE a WHERE 1=1 AND a.HM_GUIDE_TYPE = 2 ";
        if(StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)){
            map.put("STARTDATE", ExtendDate.getFirstDayOfMonth(startDate)+" 00:00:00");
            map.put("ENDDATE",ExtendDate.getLastDayOfMonth(endDate)+" 23:59:59");
            sqlwork2 += " AND PLAN_DATE BETWEEN :STARTDATE AND :ENDDATE ";
            gsql += " AND HM_CREATE_TIME BETWEEN :STARTDATE AND :ENDDATE ";
        }
        if(StringUtils.isNotBlank(addressId)){
            map.put("areaId", AreaUtils.getAreaCode(addressId)+"%");
            sqlwork2 += " AND PLAN_AREA_CODE like :areaId ";
            gsql+=" AND a.HM_AREA_CODE like :areaId ";
        }
        if(StringUtils.isNotBlank(hospId)){
            map.put("hospId", hospId);
            sqlwork2 += " AND PLAN_HOSP_ID = :hospId ";
            gsql += " AND a.HM_HOSP_ID = :hospId ";
        }
        if(StringUtils.isNotBlank(teamId)){
            map.put("teamId",teamId);
            sqlwork2 +=" AND PLAN_TEAM_ID = :teamId ";
            gsql += " AND a.HM_TEAM_ID = :teamId ";
        }
        //总数
        zssql=this.sysDao.getServiceReadDo().findCount(sqlwork2,map);
        //完成数
        wgsql=this.sysDao.getServiceReadDo().findCount(gsql,map);
        //完成率
        BigDecimal gwcl = new BigDecimal(0);
        if(zssql > 0 ){
            gwcl = new BigDecimal(wgsql*100).divide(new BigDecimal(zssql),0,BigDecimal.ROUND_HALF_UP);
        }
        return gwcl;
    }

    /**
     * 拒转签 完成率
     * @param addressId
     * @param hospId
     * @param teamId
     * @return
     */
    public BigDecimal getRefuseToSignRank(String addressId,String hospId, String teamId,String startDate,String endDate) throws Exception{
        int zjq=0;
        int wqy=0;
        HashMap map = new HashMap();
        String sqlRefuse="SELECT count(1) FROM APP_REFUSE_SIGN  WHERE 1=1 ";
        String sqlSign = sqlRefuse + " AND RS_SIGN_TIME IS NOT NULL ";
        if(StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)){
            map.put("STARTDATE", ExtendDate.getFirstDayOfMonth(startDate)+" 00:00:00");
            map.put("ENDDATE",ExtendDate.getLastDayOfMonth(endDate)+" 23:59:59");
            sqlRefuse += " AND RS_REFUSE_TIME BETWEEN :STARTDATE AND :ENDDATE ";
            sqlSign += " AND RS_SIGN_TIME BETWEEN :STARTDATE AND :ENDDATE ";
        }
        if(StringUtils.isNotBlank(addressId)){
            map.put("areaId", AreaUtils.getAreaCode(addressId)+"%");
            sqlRefuse += " AND RS_REFUSE_HOSP_AREA_CODE like :areaId ";
            sqlSign +=" AND RS_SIGN_HOSP_AREA_CODE LIKE :areaId ";
        }
        if(StringUtils.isNotBlank(hospId)){
            map.put("hospId",hospId);
            sqlRefuse += " AND RS_REFUSE_HOSP_ID = :hospId ";
            sqlSign +=" AND RS_SIGN_HOSP_ID = :hospId ";
        }
        if(StringUtils.isNotBlank(teamId)){
            map.put("teamId",teamId);
            sqlRefuse += " AND RS_REFUSE_TEAM_ID = :teamId ";
            sqlSign +=" AND RS_SIGN_TEAM_ID = :teamId ";
        }
        //总拒签数
        zjq = this.sysDao.getServiceReadDo().findCount(sqlRefuse,map);
        //转签约数
        wqy = this.sysDao.getServiceReadDo().findCount(sqlSign,map);
        //完成率
        BigDecimal swcl = new BigDecimal(0);
        if(zjq > 0 ){
            swcl = new BigDecimal(wqy*100).divide(new BigDecimal(zjq),0,BigDecimal.ROUND_HALF_UP);
        }
        return swcl;
    }

    public void myCount(String areaName,String codeId,String hospId,BigDecimal work,BigDecimal follow,BigDecimal health,BigDecimal refuse,Map<String,List> listMap) throws Exception{
        HashMap<String,Object> mapCount = new HashMap<>();
        HashMap<String,Object> map1 = new HashMap<>();
        HashMap<String,Object> map2 = new HashMap<>();
        HashMap<String,Object> map3 = new HashMap<>();
        HashMap<String,Object> map4 = new HashMap<>();
        HashMap<String,Object> map5 = new HashMap<>();
        HashMap<String,Object> map6 = new HashMap<>();
        HashMap<String,Object> map7 = new HashMap<>();
        HashMap<String,Object> map8 = new HashMap<>();
        HashMap<String,Object> map9 = new HashMap<>();
        HashMap<String,Object> map10 = new HashMap<>();
        HashMap<String,Object> map11 = new HashMap<>();
        HashMap<String,Object> map12 = new HashMap<>();
        HashMap<String,Object> map13 = new HashMap<>();
        HashMap<String,Object> map14 = new HashMap<>();

        mapCount.put("areaName", areaName);
        mapCount.put("codeId", codeId);
        mapCount.put("hospId", hospId);
        mapCount.put("areaRank", (work.add(follow).add(health).add(refuse)).divide(new BigDecimal(4),0,BigDecimal.ROUND_HALF_UP));//综合完成率
        map1.put("areaRank",work);//工作计划
        map1.put("areaName", areaName);
        map1.put("codeId", codeId);
        map1.put("hospId", hospId);
        map2.put("areaRank",follow);//随访
        map2.put("areaName", areaName);
        map2.put("codeId", codeId);
        map2.put("hospId", hospId);
        map3.put("areaRank",health);//健康干预
        map3.put("areaName", areaName);
        map3.put("codeId", codeId);
        map3.put("hospId", hospId);
        map4.put("areaRank",refuse);//拒签
        map4.put("areaName", areaName);
        map4.put("codeId", codeId);
        map5.put("hospId", hospId);
        map5.put("areaRank",(work.add(follow)).divide(new BigDecimal(2),0,BigDecimal.ROUND_HALF_UP));//工作计划+随访平均完成率
        map5.put("areaName", areaName);
        map5.put("codeId", codeId);
        map5.put("hospId", hospId);
        map6.put("areaRank",(work.add(health)).divide(new BigDecimal(2),0,BigDecimal.ROUND_HALF_UP));//工作计划+健康干预平均完成率
        map6.put("areaName", areaName);
        map6.put("codeId", codeId);
        map6.put("hospId", hospId);
        map7.put("areaRank",(work.add(refuse)).divide(new BigDecimal(2),0,BigDecimal.ROUND_HALF_UP));//工作计划+拒转签平均完成率
        map7.put("areaName", areaName);
        map7.put("codeId", codeId);
        map7.put("hospId", hospId);
        map8.put("areaRank",(follow.add(health)).divide(new BigDecimal(2),0,BigDecimal.ROUND_HALF_UP));//随访+健康干预平均完成率
        map8.put("areaName", areaName);
        map8.put("codeId", codeId);
        map8.put("hospId", hospId);
        map9.put("areaRank",(follow.add(refuse)).divide(new BigDecimal(2),0,BigDecimal.ROUND_HALF_UP));//随访+拒转签平均完成率
        map9.put("areaName", areaName);
        map9.put("codeId", codeId);
        map9.put("hospId", hospId);
        map10.put("areaRank",(health.add(refuse)).divide(new BigDecimal(2),0,BigDecimal.ROUND_HALF_UP));//健康干预+拒转签平均完成率
        map10.put("areaName", areaName);
        map10.put("codeId", codeId);
        map10.put("hospId", hospId);
        map11.put("areaRank",(work.add(follow).add(health)).divide(new BigDecimal(3),0,BigDecimal.ROUND_HALF_UP));//工作计划+随访+健康干预
        map11.put("areaName", areaName);
        map11.put("codeId", codeId);
        map11.put("hospId", hospId);
        map12.put("areaRank",(work.add(follow).add(refuse)).divide(new BigDecimal(3),0,BigDecimal.ROUND_HALF_UP));//工作计划+随访+拒转签
        map12.put("areaName", areaName);
        map12.put("codeId", codeId);
        map12.put("hospId", hospId);
        map13.put("areaRank",(work.add(health).add(refuse)).divide(new BigDecimal(3),0,BigDecimal.ROUND_HALF_UP));//工作计划+健康干预+拒转签
        map13.put("areaName", areaName);
        map13.put("codeId", codeId);
        map13.put("hospId", hospId);
        map14.put("areaRank",(follow.add(health).add(refuse)).divide(new BigDecimal(3),0,BigDecimal.ROUND_HALF_UP));//随访+健康干预+拒转签
        map14.put("areaName", areaName);
        map14.put("codeId", codeId);
        map14.put("hospId", hospId);
        listMap.get("list").add(mapCount);
        listMap.get("list1").add(map1);
        listMap.get("list2").add(map2);
        listMap.get("list3").add(map3);
        listMap.get("list4").add(map4);
        listMap.get("list5").add(map5);
        listMap.get("list6").add(map6);
        listMap.get("list7").add(map7);
        listMap.get("list8").add(map8);
        listMap.get("list9").add(map9);
        listMap.get("list10").add(map10);
        listMap.get("list11").add(map11);
        listMap.get("list12").add(map12);
        listMap.get("list13").add(map13);
        listMap.get("list14").add(map14);
    }


    /**
     * 综合排名
     * @param qvo
     * @return
     */
    @Override
    public Map<String, Object> findNewRanking(ResidentVo qvo) throws Exception{
        Map<String,Object> returnMap = new HashMap<>();
        List<Map<String,Object>> list1 = new ArrayList<>();
        List<Map<String,Object>> list2 = new ArrayList<>();
        List<Map<String,Object>> list3 = new ArrayList<>();

        BigDecimal bd1 = new BigDecimal(PropertiesUtil.getConfValue("signWeight"));
        BigDecimal bd2 = new BigDecimal(PropertiesUtil.getConfValue("renewWeight"));
        if(StringUtils.isNotBlank(qvo.getAreaId())) {
            if (AreaUtils.getAreaCode(qvo.getAreaId()).length() == 6) {//区级行政，往下查看医院的排名
                //区下的医院集合
                List<AppHospDept> hospDepts = sysDao.getServiceReadDo().loadByPk(AppHospDept.class, "hospUpcityareaId", qvo.getAreaId());
                if(hospDepts!=null && !hospDepts.isEmpty()){
                    for (AppHospDept hosp : hospDepts) {
                        if(!"0".equals(hosp.getHospState())) {
                            Map<String,Object> signMap = new HashMap<>();
                            Map<String,Object> renewMap = new HashMap<>();
                            Map<String,Object> signRenewMap = new HashMap<>();
                            //工作进度完成率、签约完成率、续签率
//                            BigDecimal workRate =
                            BigDecimal signRate = getSignRate(null,hosp,null,qvo.getYearStart(),qvo.getYearEnd());
                            BigDecimal renewRate = getRenewRate(null,hosp,null,qvo.getYearStart(),qvo.getYearEnd());
                            signMap.put("codeId",null);
                            signMap.put("hospId",hosp.getId());
                            signMap.put("areaName",hosp.getHospName());
                            signMap.put("areaRank",signRate);
                            list1.add(signMap);
                            renewMap.put("codeId",null);
                            renewMap.put("hospId",hosp.getId());
                            renewMap.put("areaName",hosp.getHospName());
                            renewMap.put("areaRank",renewRate);
                            list2.add(renewMap);
                            BigDecimal signRenewRate = signRate.multiply(bd1).add(renewRate.multiply(bd2));
                            signRenewMap.put("codeId",null);
                            signRenewMap.put("hospId",hosp.getId());
                            signRenewMap.put("areaName",hosp.getHospName());
                            signRenewMap.put("areaRank",signRenewRate);
                            list3.add(signMap);
                        }
                    }
                }

            }else{
                List<CdAddress> lsCdAddress = sysDao.getCdAddressDao().findUpCasheAreaCode(qvo.getAreaId());
                if(lsCdAddress!=null && !lsCdAddress.isEmpty()) {
                    for (CdAddress address : lsCdAddress) {
                        Map<String,Object> signMap = new HashMap<>();
                        Map<String,Object> renewMap = new HashMap<>();
                        Map<String,Object> signRenewMap = new HashMap<>();
                        BigDecimal signRate = getSignRate(address,null,null,qvo.getYearStart(),qvo.getYearEnd());
                        BigDecimal renewRate = getRenewRate(address,null,null,qvo.getYearStart(),qvo.getYearEnd());
//                        myCount(address.getAreaSname(),address.getId(),null,work,follow,health,refuse,listMap);
                        signMap.put("codeId",address.getId());
                        signMap.put("hospId",null);
                        signMap.put("areaName",address.getAreaSname());
                        signMap.put("areaRank",signRate);
                        list1.add(signMap);
                        renewMap.put("codeId",address.getId());
                        renewMap.put("hospId",null);
                        renewMap.put("areaName",address.getAreaSname());
                        renewMap.put("areaRank",renewRate);
                        list2.add(renewMap);
                        BigDecimal signRenewRate = signRate.multiply(bd1).add(renewRate.multiply(bd2));
                        signRenewMap.put("codeId",address.getId());
                        signRenewMap.put("hospId",null);
                        signRenewMap.put("areaName",address.getAreaSname());
                        signRenewMap.put("areaRank",signRenewRate);
                        list3.add(signRenewMap);
                    }
                }
            }
            //如果传入的是hospId,则返回该hospId下团队数据
        }else if(StringUtils.isNotBlank(qvo.getHospId())){
//            List<AppTeam> teamList = sysDao.getServiceReadDo().loadByPk(AppTeam.class,"teamHospId",qvo.getHospId());
            List<AppTeam> teamList = sysDao.getAppTeamDao().findAll(qvo.getHospId());
            if(teamList!=null && !teamList.isEmpty()) {
                for (AppTeam team : teamList) {
                    Map<String,Object> signMap = new HashMap<>();
                    Map<String,Object> renewMap = new HashMap<>();
                    Map<String,Object> signRenewMap = new HashMap<>();
                    BigDecimal signRate = getSignRate(null,null,team,qvo.getYearStart(),qvo.getYearEnd());
                    BigDecimal renewRate = getRenewRate(null,null,team,qvo.getYearStart(),qvo.getYearEnd());
//                    myCount(team.getTeamName(),null,null,work,follow,health,refuse,listMap);
                    signMap.put("codeId",null);
                    signMap.put("hospId",null);
                    signMap.put("areaName",team.getTeamName());
                    signMap.put("areaRank",signRate);
                    list1.add(signMap);
                    renewMap.put("codeId",null);
                    renewMap.put("hospId",null);
                    renewMap.put("areaName",team.getTeamName());
                    renewMap.put("areaRank",renewRate);
                    list2.add(renewMap);
                    BigDecimal signRenewRate = signRate.multiply(bd1).add(renewRate.multiply(bd2));
                    signRenewMap.put("codeId",null);
                    signRenewMap.put("hospId",null);
                    signRenewMap.put("areaName",team.getTeamName());
                    signRenewMap.put("areaRank",signRenewRate);
                    list3.add(signRenewMap);
                }
            }
        }
        Collections.sort(list1, ComparatorUtils.getComparator());
        Collections.sort(list2, ComparatorUtils.getComparator());
        Collections.sort(list3, ComparatorUtils.getComparator());
        returnMap.put("signMap",list1);
        returnMap.put("renewMap",list2);
        returnMap.put("signRenewMap",list3);
        return returnMap;
    }

    /**
     * 签约 完成率
     * @param address
     * @param hosp
     * @param team
     * @param startTime
     * @param endTime
     * @return
     */
    public BigDecimal getSignRate(CdAddress address,AppHospDept hosp,AppTeam team,String startTime,String endTime) throws Exception{
        int signCount = 0;//签约数
        int signTopCount = 0;//签约目标数
        Map<String,Object> map = new HashMap<>();
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        String sql = "SELECT SUM(MANAGE_SIGN_COUNT) signCount FROM APP_MANAGE_COUNT WHERE 1=1 ";
        sql += " AND MANAGE_YEAR_MONTH >=:startTime AND MANAGE_YEAR_MONTH <=:endTime ";
        if(address != null){//区域排名
            map.put("areaCode",AreaUtils.getAreaCode(address.getId())+"%");
            sql += " AND MANAGE_AREA_CODE LIKE :areaCode ";
            String[] endTimes = endTime.split("-");
            map.put("year",endTimes[0]);
            //计算区域的签约目标数
            String sqlArea = "SELECT SUM(AREA_TARGET) signTopCount FROM CP_CITY_AREA_PEOPLE WHERE 1=1 " +
                    "AND AREA_YEAR =:year AND AREA_CODE LIKE :areaCode";
            List<Map> maps = this.sysDao.getServiceReadDo().findSqlMap(sqlArea,map);
            if(maps != null && maps.size() >0){
                if(maps.get(0).get("signTopCount")!=null){
                    signTopCount = (int) Double.parseDouble(maps.get(0).get("signTopCount").toString());
                }
            }
        }
        if(hosp != null){//医院排名
            map.put("hospId",hosp.getId());
            sql += " AND MANAGE_HOSP_ID =:hospId ";
            //计算医院签约上限数(医院的目标数)
            String[] endTimes = endTime.split("-");
            map.put("year",endTimes[0]);
            map.put("hospAreaCode",hosp.getHospAreaCode());
            //计算区域的签约目标数
            String sqlArea = "SELECT AREA_TARGET signTopCount FROM CP_CITY_AREA_PEOPLE WHERE 1=1 " +
                    "AND AREA_YEAR =:year AND AREA_CODE = :hospAreaCode";
            List<Map> maps = this.sysDao.getServiceReadDo().findSqlMap(sqlArea,map);
            if(maps != null && maps.size() >0){
                if(maps.get(0).get("signTopCount")!=null){
                    signTopCount = (int) Double.parseDouble(maps.get(0).get("signTopCount").toString());
                }
            }
        }
        if(team != null){//团队排名
            map.put("teamId",team.getId());
            sql += " AND MANAGE_TEAM_ID =:teamId ";
            if(StringUtils.isBlank(team.getTeamHospId())){
                team.setTeamHospId("");
            }
            AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,team.getTeamHospId());
            if(dept != null){
                if(StringUtils.isNotBlank(endTime)){
                    String[] endTimes = endTime.split("-");
                    CdAddressPeople cdAdd = sysDao.getCdAddressPeopleDao().findByCacheCode(dept.getHospAreaCode(),endTimes[0]);
                    if(cdAdd != null){
                        double mul = 0;
                        int intmul = 0;
                        if(cdAdd.getAreaDisSignWay().equals("0")){ //0医生 1团队
                            Integer ger =sysDao.getAppTeamMemberDao().findTeamPeopleCount(team.getId());
                            mul = MyMathUtil.mul(Double.valueOf(cdAdd.getAreaSignTop()),Double.valueOf(ger));// 乘法
                        }else{
                            mul =Double.valueOf(cdAdd.getAreaSignTop());
                        }
                        DecimalFormat df = new DecimalFormat("######0"); //四色五入转换成整数
                        intmul = Integer.parseInt(df.format(mul));
                        signTopCount = Integer.parseInt(StringUtils.isNotBlank(String.valueOf(mul)) ? String.valueOf(intmul):"0");
                    }
                }
            }
        }
        List<Map> mapss = this.sysDao.getServiceReadDo().findSqlMap(sql,map);
        if(mapss != null && mapss.size()>0){
            if(mapss.get(0).get("signCount") != null){
                signCount = (int) Double.parseDouble(mapss.get(0).get("signCount").toString());
            }
        }
        //判断是否需要加上当天签约数
        int yearM = Integer.parseInt(endTime.replaceAll("-",""));//2018-01-->201801
        int nowM = Integer.parseInt(ExtendDate.getYM(Calendar.getInstance()).replaceAll("-",""));
        if(yearM >= nowM){
            map.put("STARTDATE", ExtendDate.getYMD(Calendar.getInstance())+" 00:00:00");
            map.put("ENDDATE",ExtendDate.getYMD(Calendar.getInstance())+" 23:59:59");
            String[] states = new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
            map.put("states",states);
            String sqlDate = "SELECT COUNT(1) FROM APP_SIGN_FORM WHERE 1=1 " +
                    "AND SIGN_STATE IN (:states) " +
                    "AND SIGN_FROM_DATE >= :STARTDATE AND SIGN_FROM_DATE <= :ENDDATE ";
            if(address != null){
                sqlDate += " AND SIGN_AREA_CODE LIKE :areaCode ";
            }
            if(hosp != null){
                sqlDate += " AND SIGN_HOSP_ID =:hospId ";
            }
            if(team != null){
                sqlDate += " AND SIGN_TEAM_ID =:teamId ";
            }
            signCount += sysDao.getServiceReadDo().findCount(sqlDate,map);
        }
        //完成率
        BigDecimal swcl = new BigDecimal(0);
        if(signTopCount > 0 ){
            swcl = new BigDecimal(signCount*100).divide(new BigDecimal(signTopCount),0,BigDecimal.ROUND_HALF_UP);
        }
        return swcl;
    }

    /**
     * 续签 完成率
     * @param address
     * @param hosp
     * @param team
     * @param startTime
     * @param endTime
     * @return
     */
    public BigDecimal getRenewRate(CdAddress address,AppHospDept hosp,AppTeam team,String startTime,String endTime) throws Exception{
        int renewCount = 0;//续签数
        int upSignCount = 0;//上一年度签约数
        Map<String,Object> map = new HashMap<>();
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        String sql = "SELECT SUM(MANAGE_RENEW) renewCount FROM APP_MANAGE_COUNT WHERE 1=1 ";
        sql += " AND MANAGE_YEAR_MONTH >=:startTime AND MANAGE_YEAR_MONTH <=:endTime ";
        String[] endsTime = endTime.split("-");
        int upYear =  Integer.parseInt(endsTime[0])-1;
        map.put("upYear",upYear);
        String upSql = "SELECT SUM(MANAGE_SIGN_COUNT) upSignCount FROM APP_MANAGE_COUNT WHERE 1=1 ";
        upSql += " AND MANAGE_YEAR =:upYear ";
        if(address != null){
            map.put("areaCode",AreaUtils.getAreaCode(address.getId())+"%");
            sql += " AND MANAGE_AREA_CODE LIKE :areaCode ";
            upSql += " AND MANAGE_AREA_CODE LIKE :areaCode ";
        }
        if(hosp != null){
            map.put("hospId",hosp.getId());
            sql += " AND MANAGE_HOSP_ID =:hospId ";
            upSql += " AND MANAGE_HOSP_ID =:hospId ";
        }
        if(team != null){
            map.put("teamId",team.getId());
            sql += " AND MANAGE_TEAM_ID =:teamId ";
            upSql += " AND MANAGE_TEAM_ID =:teamId ";
        }
        List<Map> maps = sysDao.getServiceReadDo().findSqlMap(sql,map);
        List<Map> upMaps = sysDao.getServiceReadDo().findSqlMap(upSql,map);
        if(maps != null && maps.size()>0){
            if(maps.get(0).get("renewCount") != null){
                renewCount = (int) Double.parseDouble(maps.get(0).get("renewCount").toString());
            }
        }
        if(upMaps != null && upMaps.size()>0){
            if(upMaps.get(0).get("upSignCount") != null){
                upSignCount = (int) Double.parseDouble(upMaps.get(0).get("upSignCount").toString());
            }
        }
        //判断是否需加上当天的续签数
        int yearM = Integer.parseInt(endTime.replaceAll("-",""));//2018-01-->201801
        int nowM = Integer.parseInt(ExtendDate.getYM(Calendar.getInstance()).replaceAll("-",""));
        if(yearM >= nowM){
            map.put("STARTDATE", ExtendDate.getYMD(Calendar.getInstance())+" 00:00:00");
            map.put("ENDDATE",ExtendDate.getYMD(Calendar.getInstance())+" 23:59:59");
            String sqlDate = "SELECT COUNT(1) " +
                    "FROM APP_SIGN_FORM WHERE 1=1" +
                    " AND SIGN_RENEWORGOTOSIGN_DATE >= :STARTDATE" +
                    " AND SIGN_RENEWORGOTOSIGN_DATE <= :ENDDATE" +
                    " AND SIGN_GOTO_SIGN_STATE='2'";

            if(address != null){
                sqlDate += " AND SIGN_AREA_CODE LIKE :areaCode ";
            }
            if(hosp != null){
                sqlDate += " AND SIGN_HOSP_ID =:hospId ";
            }
            if(team != null){
                sqlDate += " AND SIGN_TEAM_ID =:teamId ";
            }
            renewCount += sysDao.getServiceReadDo().findCount(sqlDate,map);
        }
        //完成率
        BigDecimal swcl = new BigDecimal(0);
        if(upSignCount > 0 ){
            swcl = new BigDecimal(renewCount*100).divide(new BigDecimal(upSignCount),0,BigDecimal.ROUND_HALF_UP);
        }
        return swcl;
    }

    /**
     * 工作进度 完成率
     * @param addressId
     * @param hospId
     * @param teamId
     * @return
     */
    public BigDecimal getManageWorkRank(String addressId,String hospId, String teamId,String startDate,String endDate) throws Exception{
        int zgzl=0;
        int ywcl=0;
        Calendar calendar = Calendar.getInstance();
        int nowYear = calendar.get(Calendar.YEAR);
        int nowMonth = calendar.get(Calendar.MONTH) + 1;
        int now = Integer.parseInt(String.valueOf(nowYear)+ String.valueOf(nowMonth));
        String[] year = endDate.split("-");
        int endYear = Integer.parseInt(year[0]);
        int endMonth = Integer.parseInt(year[1]);
        int end = Integer.parseInt(String.valueOf(endYear)+ String.valueOf(endMonth));

        HashMap map = new HashMap();
        String sql = "SELECT " +
                "SUM(MANAGE_TOTAL_WORK) manageTotalWork," +
                "SUM(MANAGE_COMPLETE_WORK) manageCompleteWork " +
                "FROM APP_MANAGE_RANK_COUNT WHERE 1=1 ";

        String sqlwork =  "SELECT count(1) FROM APP_WORKING_PLAN WHERE 1=1 ";
        if(StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)){
            map.put("yearStart",startDate);
            map.put("yearEnd",endDate);
            sql += " AND MANAGE_YEAR_MONTH BETWEEN :yearStart AND :yearEnd ";

        }
        if(addressId!=null){
            map.put("areaId", AreaUtils.getAreaCode(addressId)+"%");
            sqlwork += " AND PLAN_AREA_CODE like :areaId ";
            sql += " AND MANAGE_AREA_CODE LIKE :areaId ";
        }
        if(hospId!=null){
            map.put("hospId", hospId);
            sqlwork += " AND PLAN_HOSP_ID = :hospId ";
            sql += " AND MANAGE_HOSP_ID = :hospId ";
        }
        if(teamId!=null){
            map.put("teamId", teamId);
            sqlwork +=" AND PLAN_TEAM_ID = :teamId ";
            sql += " AND MANAGE_TEAM_ID = :teamId ";
        }

        //总工作量
        List<Map> list = sysDao.getServiceReadDo().findSqlMap(sql,map);
        if(list != null && list.size()>0){
            if(list.get(0).get("manageTotalWork") != null){
                zgzl =  (int) Double.parseDouble(list.get(0).get("manageTotalWork").toString());//总工作量
            }
            if(list.get(0).get("manageCompleteWork") != null){
                ywcl =  (int) Double.parseDouble(list.get(0).get("manageCompleteWork").toString());//完成工作量
            }
        }
        if(end >= now){//查询时间大于现在时间加上当天
            map.put("STARTDATE", ExtendDate.getYMD(Calendar.getInstance())+" 00:00:00");
            map.put("ENDDATE",ExtendDate.getYMD(Calendar.getInstance())+" 23:59:59");
            sqlwork += " AND PLAN_DATE BETWEEN :STARTDATE AND :ENDDATE ";

            zgzl += this.sysDao.getServiceReadDo().findCount(sqlwork,map);
            //已完成
            String sqlYwcl = sqlwork + " AND PLAN_STATE ='2' ";
            ywcl += this.sysDao.getServiceReadDo().findCount(sqlYwcl,map);
        }


        BigDecimal wcl = new BigDecimal(0);
        //完成率
        if(zgzl > 0 ){
            wcl = new BigDecimal(ywcl*100).divide(new BigDecimal(zgzl),0,BigDecimal.ROUND_HALF_UP);
        }
        return wcl;
    }

    /**
     * 随访 完成率
     * @param addressId
     * @param hospId
     * @param teamId
     * @return
     */
    public BigDecimal getManageFollowRank(String addressId,String hospId, String teamId, String startDate, String endDate) throws Exception{
        int zsf=0;
        int wsf=0;
        Calendar calendar = Calendar.getInstance();
        int nowYear = calendar.get(Calendar.YEAR);
        int nowMonth = calendar.get(Calendar.MONTH) + 1;
        int now = Integer.parseInt(String.valueOf(nowYear)+ String.valueOf(nowMonth));
        String[] year = endDate.split("-");
        int endYear = Integer.parseInt(year[0]);
        int endMonth = Integer.parseInt(year[1]);
        int end = Integer.parseInt(String.valueOf(endYear)+ String.valueOf(endMonth));

        HashMap map = new HashMap();
        String sql = "SELECT SUM(MANAGE_TOTAL_FOLLOW) manageTotalFollow," +
                "SUM(MANAGE_COMPLETE_FOLLOW) manageCompleteFollow " +
                "FROM APP_MANAGE_RANK_COUNT WHERE 1=1 ";

        String sqlFoll="SELECT count(1) FROM APP_FOLLOW_PLAN t WHERE 1=1 ";
        if(StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)){
            map.put("yearStart",startDate);
            map.put("yearEnd",endDate);
            sql += " AND MANAGE_YEAR_MONTH BETWEEN :yearStart AND :yearEnd ";

        }
        if(StringUtils.isNotBlank(addressId)){
            map.put("areaId", AreaUtils.getAreaCode(addressId)+"%");
            sqlFoll += " AND t.SF_HOS_AREA_CODE like :areaId ";
            sql += " AND MANAGE_AREA_CODE LIKE :areaId ";
        }
        if(StringUtils.isNotBlank(hospId)){
            map.put("hospId",hospId);
            sqlFoll += " AND t.SF_HOS_ID = :hospId ";
            sql += " AND MANAGE_HOSP_ID = :hospId ";
        }
        if(StringUtils.isNotBlank(teamId)){
            map.put("teamId",teamId);
            sqlFoll +=" AND t.SF_TEAM_ID = :teamId ";
            sql += " AND MANAGE_TEAM_ID = :teamId ";
        }

        List<Map> list = sysDao.getServiceReadDo().findSqlMap(sql,map);
        if(list != null && list.size()>0){
            if(list.get(0).get("manageTotalFollow") != null){
                zsf = (int) Double.parseDouble(list.get(0).get("manageTotalFollow").toString());//总随访量
            }
            if(list.get(0).get("manageCompleteFollow") != null){
                wsf = (int) Double.parseDouble(list.get(0).get("manageCompleteFollow").toString());//完成随访
            }
        }
        if(end >= now){
            map.put("STARTDATE", ExtendDate.getYMD(Calendar.getInstance())+" 00:00:00");
            map.put("ENDDATE",ExtendDate.getYMD(Calendar.getInstance())+" 23:59:59");
            sqlFoll += " AND t.SF_FOLLOW_DATE BETWEEN :STARTDATE AND :ENDDATE ";

            //总数
            zsf += this.sysDao.getServiceReadDo().findCount(sqlFoll,map);
            //完成数
            String sqlFollw=sqlFoll+" AND t.SF_FOLLOW_STATE = 1";
            wsf += this.sysDao.getServiceReadDo().findCount(sqlFollw,map);
        }
        //完成率
        BigDecimal swcl = new BigDecimal(0);
        if(zsf > 0 ){
            swcl = new BigDecimal(wsf*100).divide(new BigDecimal(zsf),0,BigDecimal.ROUND_HALF_UP);
        }
        return swcl;
    }

    /**
     * 健康干预 完成率
     * @param addressId
     * @param hospId
     * @param teamId
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    public BigDecimal getManageHealthRank(String addressId,String hospId, String teamId,String startDate,String endDate) throws Exception{
        int wgsql=0;
        int zssql=0;
        Calendar calendar = Calendar.getInstance();
        int nowYear = calendar.get(Calendar.YEAR);
        int nowMonth = calendar.get(Calendar.MONTH) + 1;
        int now = Integer.parseInt(String.valueOf(nowYear)+ String.valueOf(nowMonth));
        String[] year = endDate.split("-");
        int endYear = Integer.parseInt(year[0]);
        int endMonth = Integer.parseInt(year[1]);
        int end = Integer.parseInt(String.valueOf(endYear)+ String.valueOf(endMonth));

        HashMap map = new HashMap();
        String sql = "SELECT SUM(MANAGE_TOTAL_HEALTH) manageTotalHealth," +
                "SUM(MANAGE_COMPLETE_HEALTH) manageCompleteHealth " +
                "FROM APP_MANAGE_RANK_COUNT WHERE 1=1 ";

        String sqlwork2 =  "SELECT count(1) FROM APP_WORKING_PLAN WHERE 1=1 and PLAN_TYPE='2'";
        String gsql="SELECT COUNT(1) FROM APP_HEALTH_MEDDLE a WHERE 1=1 AND a.HM_GUIDE_TYPE = 2 ";
        if(StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)){

            map.put("yearStart",startDate);
            map.put("yearEnd",endDate);
            sql += " AND MANAGE_YEAR_MONTH BETWEEN :yearStart AND :yearEnd ";

        }
        if(StringUtils.isNotBlank(addressId)){
            map.put("areaId", AreaUtils.getAreaCode(addressId)+"%");
            sqlwork2 += " AND PLAN_AREA_CODE like :areaId ";
            gsql+=" AND a.HM_AREA_CODE like :areaId ";

            sql += " AND MANAGE_AREA_CODE LIKE :areaId ";
        }
        if(StringUtils.isNotBlank(hospId)){
            map.put("hospId", hospId);
            sqlwork2 += " AND PLAN_HOSP_ID = :hospId ";
            gsql += " AND a.HM_HOSP_ID = :hospId ";
            sql += " AND MANAGE_HOSP_ID = :hospId ";
        }
        if(StringUtils.isNotBlank(teamId)){
            map.put("teamId",teamId);
            sqlwork2 +=" AND PLAN_TEAM_ID = :teamId ";
            gsql += " AND a.HM_TEAM_ID = :teamId ";
            sql += " AND MANAGE_TEAM_ID = :teamId ";
        }
        List<Map> list = sysDao.getServiceReadDo().findSqlMap(sql,map);
        if(list != null && list.size()>0){
            if(list.get(0).get("manageTotalHealth") != null){
                zssql = (int) Double.parseDouble(list.get(0).get("manageTotalHealth").toString());//总干预量
            }
            if(list.get(0).get("manageCompleteHealth") != null){
                wgsql = (int) Double.parseDouble(list.get(0).get("manageCompleteHealth").toString());//完成干预量
            }
        }
        if(end >= now){
            map.put("STARTDATE", ExtendDate.getYMD(Calendar.getInstance())+" 00:00:00");
            map.put("ENDDATE",ExtendDate.getYMD(Calendar.getInstance())+" 23:59:59");
            sqlwork2 += " AND PLAN_DATE BETWEEN :STARTDATE AND :ENDDATE ";
            gsql += " AND HM_CREATE_TIME BETWEEN :STARTDATE AND :ENDDATE ";

            //总数
            zssql +=this.sysDao.getServiceReadDo().findCount(sqlwork2,map);
            //完成数
            wgsql +=this.sysDao.getServiceReadDo().findCount(gsql,map);
        }

        //完成率
        BigDecimal gwcl = new BigDecimal(0);
        if(zssql > 0 ){
            gwcl = new BigDecimal(wgsql*100).divide(new BigDecimal(zssql),0,BigDecimal.ROUND_HALF_UP);
        }
        return gwcl;
    }

    /**
     * 拒转签 完成率
     * @param addressId
     * @param hospId
     * @param teamId
     * @return
     */
    public BigDecimal getManageRefuseToSignRank(String addressId,String hospId, String teamId,String startDate,String endDate) throws Exception{
        int zjq=0;
        int wqy=0;

        Calendar calendar = Calendar.getInstance();
        int nowYear = calendar.get(Calendar.YEAR);
        int nowMonth = calendar.get(Calendar.MONTH) + 1;
        int now = Integer.parseInt(String.valueOf(nowYear)+ String.valueOf(nowMonth));
        String[] year = endDate.split("-");
        int endYear = Integer.parseInt(year[0]);
        int endMonth = Integer.parseInt(year[1]);
        int end = Integer.parseInt(String.valueOf(endYear)+ String.valueOf(endMonth));

        HashMap map = new HashMap();
        String sql = "SELECT SUM(MANAGE_REFUSE_SIGN) manageRefuseSign," +
                "SUM(MANAGE_GOTO_SIGN) manageGotoSign " +
                "FROM APP_MANAGE_RANK_COUNT WHERE 1=1 ";

        String sqlRefuse="SELECT count(1) FROM APP_REFUSE_SIGN  WHERE 1=1 ";
        String sqlSign = sqlRefuse + " AND RS_SIGN_TIME IS NOT NULL ";
        if(StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate)){
            map.put("yearStart",startDate);
            map.put("yearEnd",endDate);
            sql += " AND MANAGE_YEAR_MONTH BETWEEN :yearStart AND :yearEnd ";

        }
        if(StringUtils.isNotBlank(addressId)){
            map.put("areaId", AreaUtils.getAreaCode(addressId)+"%");
            sqlRefuse += " AND RS_REFUSE_HOSP_AREA_CODE like :areaId ";
            sqlSign +=" AND RS_SIGN_HOSP_AREA_CODE LIKE :areaId ";
            sql += " AND MANAGE_AREA_CODE LIKE :areaId ";
        }
        if(StringUtils.isNotBlank(hospId)){
            map.put("hospId",hospId);
            sqlRefuse += " AND RS_REFUSE_HOSP_ID = :hospId ";
            sqlSign +=" AND RS_SIGN_HOSP_ID = :hospId ";
            sql += " AND MANAGE_HOSP_ID =:hospId ";
        }
        if(StringUtils.isNotBlank(teamId)){
            map.put("teamId",teamId);
            sqlRefuse += " AND RS_REFUSE_TEAM_ID = :teamId ";
            sqlSign +=" AND RS_SIGN_TEAM_ID = :teamId ";
            sql += " AND MANAGE_TEAM_ID =:teamId ";
        }

        List<Map> list = sysDao.getServiceReadDo().findSqlMap(sql,map);
        if(list != null && list.size()>0){
            if(list.get(0).get("manageRefuseSign") != null){
                zjq = (int) Double.parseDouble(list.get(0).get("manageRefuseSign").toString());//总拒签数
            }
            if(list.get(0).get("manageGotoSign") != null){
                wqy = (int) Double.parseDouble(list.get(0).get("manageGotoSign").toString());//转签数
            }
        }
        if(end >= now){
            map.put("STARTDATE", ExtendDate.getYMD(Calendar.getInstance())+" 00:00:00");
            map.put("ENDDATE",ExtendDate.getYMD(Calendar.getInstance())+" 23:59:59");
            sqlRefuse += " AND RS_REFUSE_TIME BETWEEN :STARTDATE AND :ENDDATE ";
            sqlSign += " AND RS_SIGN_TIME BETWEEN :STARTDATE AND :ENDDATE ";
            //总拒签数
            zjq += this.sysDao.getServiceReadDo().findCount(sqlRefuse,map);
            //转签约数
            wqy += this.sysDao.getServiceReadDo().findCount(sqlSign,map);
        }
        //完成率
        BigDecimal swcl = new BigDecimal(0);
        if(zjq > 0 ){
            swcl = new BigDecimal(wqy*100).divide(new BigDecimal(zjq),0,BigDecimal.ROUND_HALF_UP);
        }
        return swcl;
    }
}
