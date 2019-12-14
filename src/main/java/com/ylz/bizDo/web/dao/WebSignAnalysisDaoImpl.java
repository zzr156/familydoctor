package com.ylz.bizDo.web.dao;

import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.bizDo.app.po.AppLabelManage;
import com.ylz.bizDo.app.po.AppTeam;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.cd.po.CdAddressPeople;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.jtapp.commonEntity.AppGoToEntity;
import com.ylz.bizDo.mangecount.dao.AppSignAnalysisDao;
import com.ylz.bizDo.mangecount.entity.ManageCountEntity;
import com.ylz.bizDo.mangecount.vo.ResidentVo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.*;
import com.ylz.packcommon.common.util.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 签约分析
 */
@Service("webSignAnalysisDao")
@Transactional(rollbackForClassName={"Exception"})
public class WebSignAnalysisDaoImpl implements WebSignAnalysisDao {

    @Autowired
    private SysDao sysDao;

    /**
     * 签约首页统计
     * @param qvo areaId 区域主键 hospId 社区主键 teamId 团队主键
     * @return
     */
    @Override
    public Map<String,Object> findSignAnalysisIndex(ResidentVo qvo)  throws Exception{
        Map<String, Object> map = new HashMap<String,Object>();
        if(StringUtils.isNotBlank(qvo.getAreaId())){
            CdAddress address = this.sysDao.getCdAddressDao().findByCacheCode(qvo.getAreaId());
            map = getSianCount(address, null,qvo.getYearStart(),qvo.getYearEnd());
        }else if(StringUtils.isNotBlank(qvo.getHospId())){
            AppHospDept hospDept = (AppHospDept)this.sysDao.getServiceDo().find(AppHospDept.class,qvo.getHospId());
            if(hospDept != null){
                CdAddress address = this.sysDao.getCdAddressDao().findByCacheCode(hospDept.getHospAreaCode());
                map = getSianCount(address, hospDept,qvo.getYearStart(),qvo.getYearEnd());
            }
        }
        return map;
    }



    /**
     * 签约转取统计
     * @param qvo
     * @return
     */
    @Override
    public Map<String, Object> findSignAnalysisList(ResidentVo qvo)  throws Exception{
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();//签约
        String result = null;
        if(StringUtils.isNotBlank(qvo.getAreaId())){
            if (AreaUtils.getAreaCode(qvo.getAreaId()).length() == 6 || AreaUtils.getAreaCode(qvo.getAreaId()).length() == 5) {//区级行政，往下查看医院的排名
                //区下的医院集合
                List<AppHospDept> hospDepts = sysDao.getServiceDo().loadByPk(AppHospDept.class, "hospUpcityareaId", qvo.getAreaId());
                if(hospDepts != null && hospDepts.size() >0){
                    for (AppHospDept hosp : hospDepts) {
                        CdAddress address = this.sysDao.getCdAddressDao().findByCacheCode(hosp.getHospAreaCode());
                        Map<String, Object> map = getSianCount(address, hosp,qvo.getYearStart(),qvo.getYearEnd());
                        list.add(map);
                    }
                }
            }else{
                List<CdAddress> lsCdAddress = sysDao.getCdAddressDao().findUpCasheAreaCode(qvo.getAreaId());
                if(lsCdAddress != null && lsCdAddress.size()>0){
                    for (CdAddress address : lsCdAddress) {
                        Map<String, Object> map = getSianCount(address, null,qvo.getYearStart(),qvo.getYearEnd());
                        list.add(map);
                    }
                }
            }
            result = "1";
        }else if(StringUtils.isNotBlank(qvo.getHospId())){
            List<AppTeam> lsTeam = sysDao.getAppTeamDao().findAll(qvo.getHospId());
            if(lsTeam != null && lsTeam.size()>0){
                for(AppTeam team : lsTeam){
                    Map<String, Object> map = getSianCountTeam(team,qvo.getYearStart(),qvo.getYearEnd());
                    list.add(map);
                }
            }
            result = "2";
        }
        Collections.sort(list, ComparatorUtils.getComparator());
        HashMap<String,Object> returnMap = new HashMap<>();
        returnMap.put("total",list);
        returnMap.put("state",result);
        return returnMap;
    }



    /**
     * 签约量统计（省，市,社区）
     * @return
     */
    private static Map<String,Map<String,Object>> m1=new HashMap<>();
    public Map<String,Object> getSianCount(CdAddress address,AppHospDept hosp,String startDate,String endDate) throws Exception{
        int areaReg = 0;//户籍人数
        int areaRegTarget = 0;//户籍目标数
        int areaFoucs = 0;//重点人群数
        int areaFoucsTarget = 0;//重点人群目标数
        int areaEconomic = 0;//经济人口性质人口数
        int areaEconomicTarget = 0;//经济人口性质目标数


        Map<String,Object> map = new HashMap<String,Object>();
        Map<String,Object> returnMap = new HashMap<String,Object>();
        String[] year = endDate.split("-");
        String addressCtCode = address.getCtcode();
        //漳州“台商投资区”，“招商局漳州开发区”，“常山农场经济开发区”这三个区要特别判断
        if("350698000000".equals(address.getCtcode())){//台商投资区
            addressCtCode = "350681102000";
        }else if("350699000000".equals(address.getCtcode())){//招商局漳州开发区
            addressCtCode = "350681501000";
        }else if("350697000000".equals(address.getCtcode())) {//常山农场经济开发区
            addressCtCode = "350622450000";
        }
        List<CdAddressPeople> lsPeople = this.sysDao.getCdAddressPeopleDao().findByCacheList(addressCtCode,year[0]);
        if(lsPeople != null && lsPeople.size() >0){
            areaReg = Integer.parseInt(StringUtils.isNotBlank(lsPeople.get(0).getAreaPopulation()) ? lsPeople.get(0).getAreaPopulation():"0");
            areaRegTarget = Integer.parseInt(StringUtils.isNotBlank(lsPeople.get(0).getAreaTarget()) ? lsPeople.get(0).getAreaTarget():"0");
            areaFoucs = Integer.parseInt(StringUtils.isNotBlank(lsPeople.get(0).getAreaFocus()) ? lsPeople.get(0).getAreaFocus():"0");
            areaFoucsTarget = Integer.parseInt(StringUtils.isNotBlank(lsPeople.get(0).getAreaFocusTarget()) ? lsPeople.get(0).getAreaFocusTarget():"0");
            areaEconomic = Integer.parseInt(StringUtils.isNotBlank(lsPeople.get(0).getAreaEconomic()) ? lsPeople.get(0).getAreaEconomic():"0");
            areaEconomicTarget = Integer.parseInt(StringUtils.isNotBlank(lsPeople.get(0).getAreaEconomicTarget()) ? lsPeople.get(0).getAreaEconomicTarget():"0");
        }
        int yqy = 0;
        int zdrqyqy = 0;
        int renew = 0;
        int totalRenew = 0;
        int economic = 0;//人口性质签约人数

        map.put("STARTDATE", ExtendDate.getFirstDayOfMonth(startDate)+" 00:00:00");
        map.put("ENDDATE",ExtendDate.getLastDayOfMonth(endDate)+" 23:59:59");
        String sqlDate = " AND t.SIGN_FROM_DATE >= :STARTDATE AND t.SIGN_FROM_DATE <= :ENDDATE ";
        String sqlYqy = "SELECT count(1) FROM APP_SIGN_FORM t where 1=1 AND t.SIGN_STATE IN (:SIGN_STATE) "+sqlDate;
        String sqlZdrqqy = "SELECT count(1) FROM APP_SIGN_FORM t LEFT JOIN APP_LABEL_GROUP b ON t.ID = b.LABEL_SIGN_ID where 1=1 and b.LABEL_TYPE='3' AND t.SIGN_STATE IN (:SIGN_STATE) AND b.LABEL_VALUE NOT IN (:SIGN_PERS_GROUP) "+sqlDate;
        String[] state = {SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};//状态
        String[] fwState ={ResidentMangeType.PTRQ.getValue(),ResidentMangeType.WEIZHI.getValue()};
        map.put("SIGN_STATE",state);
        map.put("SIGN_PERS_GROUP",fwState);
        if(hosp != null){
            map.put("HOSP_ID",hosp.getId());
            sqlYqy += " AND t.SIGN_HOSP_ID = :HOSP_ID ";
            yqy = this.sysDao.getServiceDo().findCount(sqlYqy,map);//已签约
            sqlZdrqqy += " AND t.SIGN_HOSP_ID = :HOSP_ID ";
            zdrqyqy = this.sysDao.getServiceDo().findCount(sqlZdrqqy,map);//重點人群已簽約
        }else {
            map.put("AREA_CODE",AreaUtils.getAreaCode(address.getCtcode())+"%");
            sqlYqy += " AND t.SIGN_AREA_CODE LIKE :AREA_CODE ";
            yqy = this.sysDao.getServiceDo().findCount(sqlYqy,map);//已签约
            sqlZdrqqy += " AND t.SIGN_AREA_CODE LIKE :AREA_CODE ";
            zdrqyqy = this.sysDao.getServiceDo().findCount(sqlZdrqqy,map);//重點人群已簽約
        }
//        map.put("STARTDATE",startDate);
//        map.put("ENDDATE",endDate);
//        String sqlDate = " AND t.MANAGE_YEAR_MONTH >= :STARTDATE AND t.MANAGE_YEAR_MONTH <= :ENDDATE ";
//        String sql = "SELECT SUM(t.MANAGE_SIGN_COUNT) signCount,SUM(t.MANAGE_KEY_SIGN_COUNT) keySingCount,SUM(t.MANAGE_RENEW) renew," +
//                "SUM(t.MANAGE_LOW_FAMILY_COUNT) manageLowFamilyCount," +
//                "SUM(t.MANAGE_DESTITUTE_FAMILY_COUNT) manageDestituteFamilyCount," +
//                "SUM(t.MANAGE_SPECIAL_PLAN_FAMILY_COUNT) manageSpecialPlanFamilyCount," +
//                "SUM(t.MANAGE_POOR_FAMILY_COUNT) managePoorFamilyCount " +
//                " FROM APP_MANAGE_COUNT t where 1=1 "+sqlDate;
//        //续签
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.YEAR,-1);
//        String years = ExtendDate.getYYYY(cal);
//        map.put("years",years);
//        String sqll = "SELECT SUM(t.MANAGE_TOTAL_RENEW) totalRenew  " +
//                "FROM APP_MANAGE_COUNT t where 1=1 AND t.MANAGE_YEAR=:years";
//
//        if(hosp != null){
//            map.put("HOSP_ID",hosp.getId());
//            sql += " AND t.MANAGE_HOSP_ID = :HOSP_ID ";
//            sqll +="  AND t.MANAGE_HOSP_ID = :HOSP_ID ";
//        }else {
//            map.put("AREA_CODE",AreaUtils.getAreaCode(address.getCtcode())+"%");
//            sql += " AND t.MANAGE_AREA_CODE LIKE :AREA_CODE ";
//            sqll += " AND t.MANAGE_AREA_CODE LIKE :AREA_CODE ";
//        }
//        List<Map> maps = this.sysDao.getServiceDo().findSqlMap(sql,map);
//        List<Map> mapss = this.sysDao.getServiceDo().findSqlMap(sqll,map);
//        if(maps != null && maps.size() >0){
//            if(maps.get(0).get("signCount") != null)
//                yqy = (int)Double.parseDouble(maps.get(0).get("signCount").toString());//已签约
//            if(maps.get(0).get("keySingCount") != null)
//                zdrqyqy = (int)Double.parseDouble(maps.get(0).get("keySingCount").toString());//重點人群已簽約
//            if(maps.get(0).get("renew") !=null){
//                renew = (int)Double.parseDouble(maps.get(0).get("renew").toString());//续签数
//            }
//            if(maps.get(0).get("manageLowFamilyCount")!=null){
//                economic+=(int)Double.parseDouble(maps.get(0).get("manageLowFamilyCount").toString());
//            }
//            if(maps.get(0).get("manageDestituteFamilyCount")!=null){
//                economic+=(int)Double.parseDouble(maps.get(0).get("manageDestituteFamilyCount").toString());
//            }
//            if(maps.get(0).get("manageSpecialPlanFamilyCount")!=null){
//                economic+=(int)Double.parseDouble(maps.get(0).get("manageSpecialPlanFamilyCount").toString());
//            }
//            if(maps.get(0).get("managePoorFamilyCount")!=null){
//                economic+=(int)Double.parseDouble(maps.get(0).get("managePoorFamilyCount").toString());
//            }
//
//        }
//        if(mapss !=null && maps.size()>0){
//            if(mapss.get(0).get("totalRenew")!=null){
//                totalRenew = (int)Double.parseDouble(mapss.get(0).get("totalRenew").toString());//总签约数
//            }
//        }
        //续签率
//        double xql = 0;
//        if(totalRenew>0){
//            xql = MyMathUtil.div(Double.valueOf(renew),Double.valueOf(totalRenew),4)*100;//续签率
//        }
        //签约率
        double qywcl = 0;
        if(areaReg >0){
            qywcl = MyMathUtil.div(Double.valueOf(yqy),Double.valueOf(areaReg),4)*100;//签约率
        }
        //目标率
        double mbwcl = 0;
        if(areaRegTarget >0){
            mbwcl = MyMathUtil.div(Double.valueOf(yqy),Double.valueOf(areaRegTarget),4)*100;//目标率
        }
        //重点人群签约率
        double zdrqwcl = 0;
        if(areaFoucs >0){
            zdrqwcl = MyMathUtil.div(Double.valueOf(zdrqyqy),Double.valueOf(areaFoucs),4)*100;//重点人群签约率
        }
        double zdrqmbwcl = 0;
        if(areaFoucsTarget >0){
            zdrqmbwcl = MyMathUtil.div(Double.valueOf(zdrqyqy),Double.valueOf(areaFoucsTarget),4)*100;//重点人群目标率
        }
//        //人口经济性质签约率
//        double economicRate =0;
//        if(areaEconomic>0){
//            economicRate = MyMathUtil.div(Double.valueOf(economic),Double.valueOf(areaEconomic),4)*100;//人口性质签约率
//        }
//        //人口性质目标率
//        double economicTargetRate = 0;
//        if(areaEconomicTarget>0){
//            economicTargetRate = MyMathUtil.div(Double.valueOf(economic),Double.valueOf(areaEconomicTarget),4)*100;//人口性质目标率
//        }
        //未签约数
        int wqys = (int) MyMathUtil.sub(Double.valueOf(areaReg),Double.valueOf(yqy));
        if(hosp != null){
            returnMap.put("hospId",hosp.getId());//区域编码
            returnMap.put("hospName",hosp.getHospName());//名称
            returnMap.put("areaCode",null);//区域编码
            returnMap.put("areaName",null);//区域名称
        }else{
            returnMap.put("areaCode",address.getCtcode());//区域编码
            returnMap.put("areaName",address.getAreaSname());//区域名称
            returnMap.put("hospId",null);//区域编码
            returnMap.put("hospName",null);//名称
        }
        returnMap.put("areaReg",areaReg);//户籍人数
        returnMap.put("areaRegTarget",areaRegTarget);//户籍目标数
        returnMap.put("yqy",yqy);//签约数
        returnMap.put("wqys",wqys);//签约数
        returnMap.put("qywcl",String.valueOf(MyMathUtil.round(qywcl,2)));//签约率
        returnMap.put("mbwcl",String.valueOf(MyMathUtil.round(mbwcl,2)));//目标率
        returnMap.put("areaFoucs",areaFoucs);//重点人群数
        returnMap.put("zdrqyqy",zdrqyqy);//重点人群签约数
        returnMap.put("areaFoucsTarget",areaFoucsTarget);//重点人群目标数
        returnMap.put("zdrqwcl",String.valueOf(MyMathUtil.round(zdrqwcl,2)));//重点人群签约率
        returnMap.put("zdrqmbwcl",String.valueOf(MyMathUtil.round(zdrqmbwcl,2)));//重点人群目标率
//        returnMap.put("xql",String.valueOf(MyMathUtil.round(xql,2)));//续签率
//        returnMap.put("xqs",renew);//续签数
//        returnMap.put("synqys",totalRenew);//上一年签约数
//
//        returnMap.put("economic",economic);//人口性质签约数
//        returnMap.put("economicRate",economicRate);//人口性质签约率
//        returnMap.put("economicTargetRate",economicTargetRate);//人口性质目标率
//        returnMap.put("areaEconomic",areaEconomic);//经济人口性质人口数
//        returnMap.put("areaEconomicTarget",areaEconomicTarget);//经济人口性质目标数

        return returnMap;
    }


    /**
     * 签约量统计（团队）
     * @param team
     * @return
     */
    public Map<String,Object> getSianCountTeam(AppTeam team,String startDate,String endDate) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        Map<String,Object> returnMap = new HashMap<String,Object>();
        int yqy = 0;
        int zdrqyqy = 0;
        int renew = 0;
        int totalRenew = 0;
        map.put("STARTDATE", ExtendDate.getFirstDayOfMonth(startDate)+" 00:00:00");
        map.put("ENDDATE",ExtendDate.getLastDayOfMonth(endDate)+" 23:59:59");
        String sqlDate = " AND t.SIGN_FROM_DATE >= :STARTDATE AND t.SIGN_FROM_DATE <= :ENDDATE ";
        String sqlYqy = "SELECT count(1) FROM APP_SIGN_FORM t where 1=1 AND t.SIGN_STATE IN (:SIGN_STATE) "+sqlDate;
        String sqlZdrqqy = "SELECT count(1) FROM APP_SIGN_FORM t LEFT JOIN APP_LABEL_GROUP b ON t.ID = b.LABEL_SIGN_ID where 1=1 and b.LABEL_TYPE=3 AND t.SIGN_STATE IN (:SIGN_STATE) AND b.LABEL_VALUE NOT IN (:SIGN_PERS_GROUP) "+sqlDate;
        String[] signStates = new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
//        String state = SignFormType.YQY.getValue();//状态
        String[] fwState ={ResidentMangeType.PTRQ.getValue(),ResidentMangeType.WEIZHI.getValue()};
        map.put("SIGN_STATE",signStates);
        map.put("SIGN_PERS_GROUP",fwState);
        map.put("TEAM_ID",team.getId());
        sqlYqy += " AND t.SIGN_TEAM_ID = :TEAM_ID ";
        yqy = this.sysDao.getServiceDo().findCount(sqlYqy,map);//已签约
        sqlZdrqqy += " AND t.SIGN_TEAM_ID = :TEAM_ID ";
        zdrqyqy = this.sysDao.getServiceDo().findCount(sqlZdrqqy,map);//重點人群已簽約
//        map.put("STARTDATE",startDate);
//        map.put("ENDDATE",endDate);
//        String sqlDate = " AND t.MANAGE_YEAR_MONTH >= :STARTDATE AND t.MANAGE_YEAR_MONTH <= :ENDDATE ";
//        String sql = "SELECT SUM(t.MANAGE_SIGN_COUNT) signCount,SUM(t.MANAGE_KEY_SIGN_COUNT) keySingCount," +
//                "SUM(t.MANAGE_RENEW) renew " +
//                " FROM APP_MANAGE_COUNT t where 1=1 "+sqlDate;
//        map.put("TEAM_ID",team.getId());
//        //续签
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.YEAR,-1);
//        String years = ExtendDate.getYYYY(cal);
//        map.put("years",years);
//        String sqll = "SELECT SUM(t.MANAGE_TOTAL_RENEW) totalRenew  " +
//                "FROM APP_MANAGE_COUNT t where 1=1 AND t.MANAGE_YEAR=:years";
//
//        sql += " AND t.MANAGE_TEAM_ID = :TEAM_ID ";
//        sqll += " AND t.MANAGE_TEAM_ID = :TEAM_ID ";
//
//
//
//        List<Map> mapss = this.sysDao.getServiceDo().findSqlMap(sqll,map);
//        List<Map> maps = this.sysDao.getServiceDo().findSqlMap(sql,map);
//        if(maps != null && maps.size() >0){
//            if(maps.get(0).get("signCount") != null)
//                yqy = (int)Double.parseDouble(maps.get(0).get("signCount").toString());//已签约
//            if(maps.get(0).get("keySingCount") != null)
//                zdrqyqy = (int)Double.parseDouble(maps.get(0).get("keySingCount").toString());//重點人群已簽約
//            if(maps.get(0).get("renew") !=null){
//                renew = (int)Double.parseDouble(maps.get(0).get("renew").toString());//续签数
//            }
//
//        }
//        if(mapss!=null && mapss.size()>0){
//            if(mapss.get(0).get("totalRenew")!=null){
//                totalRenew = (int)Double.parseDouble(mapss.get(0).get("totalRenew").toString());//总签约数
//            }
//        }
//        //续签率
//        double xql = 0;
//        if(totalRenew>0){
//            xql = MyMathUtil.div(Double.valueOf(renew),Double.valueOf(totalRenew),4)*100;//续签率
//        }
//        returnMap.put("xql",xql);//续签率
        returnMap.put("yqy",yqy);//签约数
        returnMap.put("zdrqyqy",zdrqyqy);//重点人群签约数
        returnMap.put("teamName",team.getTeamName());//团队名称
        returnMap.put("teamDrName",team.getTeamDrName());//团队队长名称
        returnMap.put("xqs",renew);//续签数
        returnMap.put("synqys",totalRenew);//上一年签约数
        return returnMap;
    }

    /**
     * 签约转取统计
     * @param qvo
     * @return
     */
    @Override
    public Map<String, Object> findSignAnalysisListInitialise(ResidentVo qvo)  throws Exception{
        AppHospDept hosp = null;
        AppTeam team = null;
        if(StringUtils.isNotBlank(qvo.getHospId())){
            hosp = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,qvo.getHospId());
        }
        if(StringUtils.isNotBlank(qvo.getTeamId())){
            team = (AppTeam) sysDao.getServiceDo().find(AppTeam.class,qvo.getTeamId());
        }
        Map<String, Object> map = getSianCountInitialise(hosp,team,qvo.getYearStart(),qvo.getYearEnd());
        return map;
    }



    /**
     * 签约量统计（省，市,社区）
     * @return
     */
    public Map<String,Object> getSianCountInitialise(AppHospDept hosp,AppTeam team,String startDate,String endDate) throws Exception{

        Map<String,Object> map = new HashMap<String,Object>();
        Map<String,Object> returnMap = new HashMap<String,Object>();
        int yqy = 0;
        int zdrqyqy = 0;
        map.put("STARTDATE", ExtendDate.getFirstDayOfMonth(startDate)+" 00:00:00");
        map.put("ENDDATE",ExtendDate.getLastDayOfMonth(endDate)+" 23:59:59");
        String sqlDate = " AND t.SIGN_FROM_DATE >= :STARTDATE AND t.SIGN_FROM_DATE <= :ENDDATE ";
        String sqlYqy = "SELECT count(1) FROM APP_SIGN_FORM t where 1=1 AND t.SIGN_STATE in (:SIGN_STATE) "+sqlDate;
        String sqlZdrqqy = "SELECT count(1) FROM APP_SIGN_FORM t LEFT JOIN APP_LABEL_GROUP b ON t.ID = b.LABEL_SIGN_ID where 1=1 and b.LABEL_TYPE='3' AND b.LABEL_VALUE NOT IN (:SIGN_PERS_GROUP) AND t.SIGN_STATE in (:SIGN_STATE) "+sqlDate;
        String[] fwState ={ResidentMangeType.PTRQ.getValue(),ResidentMangeType.WEIZHI.getValue()};
        String[] qys = {SignFormType.YUQY.getValue(),SignFormType.YQY.getValue(),SignFormType.YJY.getValue()};
        map.put("SIGN_STATE", qys);
//        String qys = SignFormType.YQY.getValue();
//        map.put("SIGN_STATE", qys);
        map.put("SIGN_PERS_GROUP",fwState);
        if(hosp != null){
            map.put("HOSP_ID",hosp.getId());
            sqlYqy += " AND t.SIGN_HOSP_ID = :HOSP_ID ";
            sqlZdrqqy += " AND t.SIGN_HOSP_ID = :HOSP_ID ";
        }

        if(team != null){
            map.put("SIGN_TEAM_ID",team.getId());
            sqlYqy += " AND t.SIGN_TEAM_ID = :SIGN_TEAM_ID ";
            sqlZdrqqy += " AND t.SIGN_TEAM_ID = :SIGN_TEAM_ID ";
        }
        yqy = this.sysDao.getServiceDo().findCount(sqlYqy,map);//已签约
        zdrqyqy = this.sysDao.getServiceDo().findCount(sqlZdrqqy,map);//重點人群已簽約
        returnMap.put("manageSignCount",yqy);//签约数
        returnMap.put("manageKeySignCount",zdrqyqy);//重点人群签约数
        return returnMap;
    }

    /**
     * 经济类型统计
     * @param qvo
     * @return
     */
    public String findSignEconomicTypeList(ResidentVo qvo) throws Exception{
        HashMap rmap = new HashMap();
        List<ManageCountEntity> lsEntity = new ArrayList<ManageCountEntity>();
//        List<CdCode> ls = this.sysDao.getCodeDao().findGroupList(CodeGroupConstrats.GROUP_JJLX, CommonEnable.QIYONG.getValue());
        List<AppLabelManage> ls = this.sysDao.getAppLabelManageDao().findByType(LabelManageType.JJLX.getValue());
        if(ls!=null && !ls.isEmpty()) {
            HashMap map = new HashMap();
//            if (StringUtils.isNotBlank(qvo.getTeamId())) {
                map.put("yearStart", ExtendDate.getFirstDayOfMonth(qvo.getYearStart())+" 00:00:00");
                map.put("yearEnd",ExtendDate.getLastDayOfMonth(qvo.getYearEnd())+" 23:59:59");
                String[] state = {SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};//状态
                map.put("SIGN_STATE",state);
                String sql = "SELECT COUNT(1) FROM APP_SIGN_FORM a WHERE a.SIGN_STATE IN (:SIGN_STATE) ";
                sql += "AND a.SIGN_FROM_DATE >= :yearStart AND a.SIGN_FROM_DATE <= :yearEnd ";
//                sql += " AND a.SIGN_TEAM_ID =:SIGN_TEAM_ID";
//                map.put("SIGN_TEAM_ID", qvo.getTeamId());
                if(StringUtils.isNotBlank(qvo.getAreaId())){
                    sql += " AND a.SIGN_AREA_CODE LIKE :SIGN_AREA_CODE";
                    map.put("SIGN_AREA_CODE",AreaUtils.getAreaCode( qvo.getAreaId())+"%");
                }
                if(StringUtils.isNotBlank(qvo.getHospId())){
                    sql += " AND a.SIGN_HOSP_ID =:SIGN_HOSP_ID";
                    map.put("SIGN_HOSP_ID", qvo.getHospId());
                }
                if(StringUtils.isNotBlank(qvo.getTeamId())){
                    sql += " AND a.SIGN_TEAM_ID =:SIGN_TEAM_ID";
                    map.put("SIGN_TEAM_ID", qvo.getTeamId());
                }
                sql += " AND a.SIGN_JJ_TYPE=:SIGN_JJ_TYPE";
                for(AppLabelManage l:ls) {
                    ManageCountEntity v = new ManageCountEntity();
                    if(l.getLabelValue().equals("1")){//一般人口
                        map.put("SIGN_JJ_TYPE",l.getLabelValue());
                        int  count= sysDao.getServiceDo().findCount(sql, map);
                        v.setTitle("manageGeneralPopulationCount");
                        v.setValue(String.valueOf(count));
                        v.setName(l.getLabelTitle());
                        lsEntity.add(v);
                    }else if(l.getLabelValue().equals("2")){//建档立卡贫困人口
                        map.put("SIGN_JJ_TYPE",l.getLabelValue());
                        int  count= sysDao.getServiceDo().findCount(sql, map);
                        v.setTitle("managePoorFamilyCount");
                        v.setValue(String.valueOf(count));
                        v.setName(l.getLabelTitle());
                        lsEntity.add(v);
                    }else if(l.getLabelValue().equals("3")){//低保户
                        map.put("SIGN_JJ_TYPE",l.getLabelValue());
                        int  count= sysDao.getServiceDo().findCount(sql, map);
                        v.setTitle("manageLowFamilyCount");
                        v.setValue(String.valueOf(count));
                        v.setName(l.getLabelTitle());
                        lsEntity.add(v);
                    }else if(l.getLabelValue().equals("4")){//特困户（五保户）
                        map.put("SIGN_JJ_TYPE",l.getLabelValue());
                        int  count= sysDao.getServiceDo().findCount(sql, map);
                        v.setTitle("manageDestituteFamilyCount");
                        v.setValue(String.valueOf(count));
                        v.setName(l.getLabelTitle());
                        lsEntity.add(v);
                    }else if(l.getLabelValue().equals("5")){//计生特殊家庭
                        map.put("SIGN_JJ_TYPE",l.getLabelValue());
                        int  count= sysDao.getServiceDo().findCount(sql, map);
                        v.setTitle("manageSpecialPlanFamilyCount");
                        v.setValue(String.valueOf(count));
                        v.setName(l.getLabelTitle());
                        lsEntity.add(v);
                    }
                }
//            }else{
//                map.put("STARTDATE",qvo.getYearStart());
//                map.put("ENDDATE",qvo.getYearEnd());
//                String sqlDate = " AND t.MANAGE_YEAR_MONTH >= :STARTDATE AND t.MANAGE_YEAR_MONTH <= :ENDDATE ";
//                String sql = "SELECT " +
//                        "SUM(t.MANAGE_LOW_FAMILY_COUNT) manageLowFamilyCount," +
//                        "SUM(t.MANAGE_DESTITUTE_FAMILY_COUNT) manageDestituteFamilyCount," +
//                        "SUM(t.MANAGE_SPECIAL_PLAN_FAMILY_COUNT) manageSpecialPlanFamilyCount," +
//                        "SUM(t.MANAGE_GENERAL_POPULATION_COUNT) manageGeneralPopulationCount," +
//                        "SUM(t.MANAGE_POOR_FAMILY_COUNT) managePoorFamilyCount " +
//                        " FROM APP_MANAGE_COUNT t where 1=1 "+sqlDate;
//                if(StringUtils.isNotBlank(qvo.getTeamId())){
//                    map.put("TEAM_ID",qvo.getHospId());
//                    sql += " AND t.MANAGE_TEAM_ID = :TEAM_ID ";
//                }
//                if(StringUtils.isNotBlank(qvo.getHospId())){
//                    map.put("HOSP_ID",qvo.getHospId());
//                    sql += " AND t.MANAGE_HOSP_ID = :HOSP_ID ";
//                }
//                if(StringUtils.isNotBlank(qvo.getAreaId())){
//                    String areaCode = AreaUtils.getAreaCode(qvo.getAreaId());
//                    map.put("AREA_CODE",areaCode+"%");
//                    sql += " AND t.MANAGE_AREA_CODE LIKE :AREA_CODE ";
//                }
//                List<Map> maps = this.sysDao.getServiceDo().findSqlMap(sql,map);
//                if(maps != null && maps.size() >0){
//                    for(CdCode l:ls) {
//                        ManageCountEntity v = new ManageCountEntity();
//                        if(l.getCodeValue().equals("1")){//一般人口
//                            if(maps.get(0).get("manageGeneralPopulationCount") != null){
//                                v.setTitle("manageGeneralPopulationCount");
//                                v.setName(l.getCodeTitle());
//                                v.setValue(String.valueOf((int)Double.parseDouble(maps.get(0).get("manageGeneralPopulationCount").toString())));
//                                lsEntity.add(v);
//                            }
//                        }else if(l.getCodeValue().equals("2")){//建档立卡贫困人口
//                            if(maps.get(0).get("managePoorFamilyCount") != null){
//                                v.setTitle("managePoorFamilyCount");
//                                v.setName(l.getCodeTitle());
//                                v.setValue(String.valueOf((int)Double.parseDouble(maps.get(0).get("managePoorFamilyCount").toString())));
//                                lsEntity.add(v);
//                            }
//                        }else if(l.getCodeValue().equals("3")){//低保户
//                            if(maps.get(0).get("manageLowFamilyCount") != null){
//                                v.setTitle("manageLowFamilyCount");
//                                v.setName(l.getCodeTitle());
//                                v.setValue(String.valueOf((int)Double.parseDouble(maps.get(0).get("manageLowFamilyCount").toString())));
//                                lsEntity.add(v);
//                            }
//                        }else if(l.getCodeValue().equals("4")){//特困户（五保户）
//                            if(maps.get(0).get("manageDestituteFamilyCount") != null){
//                                v.setTitle("manageDestituteFamilyCount");
//                                v.setName(l.getCodeTitle());
//                                v.setValue(String.valueOf((int)Double.parseDouble(maps.get(0).get("manageDestituteFamilyCount").toString())));
//                                lsEntity.add(v);
//                            }
//                        }else if(l.getCodeValue().equals("5")){//计生特殊家庭
//                            if(maps.get(0).get("manageSpecialPlanFamilyCount") != null){
//                                v.setTitle("manageSpecialPlanFamilyCount");
//                                v.setName(l.getCodeTitle());
//                                v.setValue(String.valueOf((int)Double.parseDouble(maps.get(0).get("manageSpecialPlanFamilyCount").toString())));
//                                lsEntity.add(v);
//                            }
//                        }
//                    }
//                }
//            }
        }
        return JsonUtil.toJson(lsEntity);
    }

    /**
     * 经济类型统计
     * @param qvo
     * @return
     */
    @Override
    public Map<String, Object> findSignEconomicTypeListInitialise(ResidentVo qvo) throws Exception {
        HashMap rmap = new HashMap();
//        List<CdCode> ls = this.sysDao.getCodeDao().findGroupList(CodeGroupConstrats.GROUP_JJLX, CommonEnable.QIYONG.getValue());
        List<AppLabelManage> ls = this.sysDao.getAppLabelManageDao().findByType(LabelManageType.JJLX.getValue());
        if(ls!=null && !ls.isEmpty()) {
            HashMap map = new HashMap();
            map.put("yearStart", ExtendDate.getFirstDayOfMonth(qvo.getYearStart())+" 00:00:00");
            map.put("yearEnd",ExtendDate.getLastDayOfMonth(qvo.getYearEnd())+" 23:59:59");
            String sql = "SELECT COUNT(1) FROM APP_SIGN_FORM a WHERE 1=1 ";
//            String[] qys = {SignFormType.YQY.getValue(),SignFormType.YJY.getValue()};
//            map.put("yqy", qys);
            String[] signStates = new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
//            String qys = SignFormType.YQY.getValue();
            map.put("yqy", signStates);
            sql += " AND a.SIGN_STATE IN (:yqy) ";
            sql += " AND a.SIGN_FROM_DATE >= :yearStart AND a.SIGN_FROM_DATE <= :yearEnd ";
            if (StringUtils.isNotBlank(qvo.getHospId())) {
                sql += " AND a.SIGN_HOSP_ID =:SIGN_HOSP_ID";
                map.put("SIGN_HOSP_ID", qvo.getHospId());
            }
            if (StringUtils.isNotBlank(qvo.getTeamId())) {
                sql += " AND a.SIGN_TEAM_ID =:SIGN_TEAM_ID";
                map.put("SIGN_TEAM_ID",qvo.getTeamId());
            }


            for(AppLabelManage l:ls) {
                if(l.getLabelValue().equals("1")){//一般人口
                    sql += " AND a.SIGN_JJ_TYPE =:SIGN_JJ_TYPE ";
                    map.put("SIGN_JJ_TYPE",l.getLabelValue());
                    int  count= sysDao.getServiceDo().findCount(sql, map);
                    rmap.put("manageGeneralPopulationCount", count);
                }else if(l.getLabelValue().equals("2")){//建档立卡贫困人口
                    sql += " AND a.SIGN_JJ_TYPE =:SIGN_JJ_TYPE ";
                    map.put("SIGN_JJ_TYPE",l.getLabelValue());
                    int  count= sysDao.getServiceDo().findCount(sql, map);
                    rmap.put("managePoorFamilyCount", count);
                }else if(l.getLabelValue().equals("3")){//低保户
                    sql += " AND a.SIGN_JJ_TYPE =:SIGN_JJ_TYPE ";
                    map.put("SIGN_JJ_TYPE",l.getLabelValue());
                    int  count= sysDao.getServiceDo().findCount(sql, map);
                    rmap.put("manageLowFamilyCount", count);
                }else if(l.getLabelValue().equals("4")){//特困户（五保户）
                    sql += " AND a.SIGN_JJ_TYPE =:SIGN_JJ_TYPE ";
                    map.put("SIGN_JJ_TYPE",l.getLabelValue());
                    int  count= sysDao.getServiceDo().findCount(sql, map);
                    rmap.put("manageDestituteFamilyCount", count);
                }else if(l.getLabelValue().equals("5")){//计生特殊家庭
                    sql += " AND a.SIGN_JJ_TYPE =:SIGN_JJ_TYPE ";
                    map.put("SIGN_JJ_TYPE",l.getLabelValue());
                    int  count= sysDao.getServiceDo().findCount(sql, map);
                    rmap.put("manageSpecialPlanFamilyCount", count);
                }
            }
            return rmap;
        }
        return rmap;
    }

    /**
     * 续签数
     * @param qvo
     * @return
     */
    @Override
    public Map<String, Object> findSignRenew(ResidentVo qvo)  throws Exception{
        HashMap rmap = new HashMap();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("yearStart", ExtendDate.getFirstDayOfMonth(qvo.getYearStart())+" 00:00:00");
        map.put("yearEnd",ExtendDate.getLastDayOfMonth(qvo.getYearEnd())+" 23:59:59");
        String[] signStates = new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
        map.put("signState",signStates);
        map.put("contractState", CommSF.YES.getValue());
        String sql = "SELECT\n" +
                "\tCOUNT(1)\n" +
                "FROM\n" +
                "\tAPP_SIGN_FORM\n" +
                "WHERE 1=1\n" +
                "\t AND SIGN_FROM_DATE >= :yearStart\n" +
                "AND SIGN_FROM_DATE <= :yearEnd\n" +
                "AND SIGN_GOTO_SIGN_STATE='2'";
//                "AND SIGN_STATE IN (:signState)\n" +
//                "AND SIGN_CONTRACT_STATE = :contractState";
        if(StringUtils.isNotBlank(qvo.getAreaId())){
            map.put("areaCode",qvo.getAreaId()+"%");
            sql += " AND SIGN_AREA_CODE LIKE :areaCode ";
        }
        if(StringUtils.isNotBlank(qvo.getHospId())){
            map.put("hospId",qvo.getHospId());
            sql += " AND SIGN_HOSP_ID =:hospId";
        }
        if(StringUtils.isNotBlank(qvo.getTeamId())){
            map.put("teamId",qvo.getTeamId());
            sql += " AND SIGN_TEAM_ID =:teamId";
        }
        int renew = this.sysDao.getServiceDo().findCount(sql,map);
        rmap.put("manageRenew",renew);
        return rmap;
    }

    /**
     * 签约数
     * @param qvo
     * @return
     */
    @Override
    public Map<String, Object> findSignTotalRenew(ResidentVo qvo)  throws Exception{
        HashMap rmap = new HashMap();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("yearStart", ExtendDate.getFirstDayOfMonth(qvo.getYearStart())+" 00:00:00");
        map.put("yearEnd",ExtendDate.getLastDayOfMonth(qvo.getYearEnd())+" 23:59:59");
        String[] signStates = new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
        map.put("signState",signStates);
        map.put("contractState", CommSF.NOT.getValue());
        String sql = "SELECT\n" +
                "\tCOUNT(1)\n" +
                "FROM\n" +
                "\tAPP_SIGN_FORM\n" +
                "WHERE 1=1\n" +
                "\t AND SIGN_FROM_DATE >= :yearStart\n" +
                "AND SIGN_FROM_DATE <= :yearEnd\n" +
                "AND SIGN_STATE IN (:signState)\n" +
                "AND SIGN_CONTRACT_STATE = :contractState";
        if(StringUtils.isNotBlank(qvo.getAreaId())){
            map.put("areaCode",qvo.getAreaId()+"%");
            sql += " AND SIGN_AREA_CODE LIKE :areaCode ";
        }
        if(StringUtils.isNotBlank(qvo.getHospId())){
            map.put("hospId",qvo.getHospId());
            sql += " AND SIGN_HOSP_ID =:hospId";
        }
        if(StringUtils.isNotBlank(qvo.getTeamId())){
            map.put("teamId",qvo.getTeamId());
            sql += " AND SIGN_TEAM_ID =:teamId";
        }
        int totlaRenew = this.sysDao.getServiceDo().findCount(sql,map);
        rmap.put("manageTotalRenew",totlaRenew);
        return rmap;
    }

    /**
     * 评价统计
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> findAssess(ResidentVo qvo) throws Exception {
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        List<Map<String,Object>> assessList = new ArrayList<Map<String,Object>>();
        Map<String,Object> map = new HashMap<String,Object>();
        List<Map<String,String>> mapList = ExtendDateUtil.getListBetweenMonthMap(qvo.getYearStart(),qvo.getYearEnd());
        String sql = "SELECT avg(EVALUATION_AVERAGE) AS scout " +
                "FROM APP_DR_EVALUATION WHERE 1=1 ";
        Double dd = 0.0;
        int num1 =0;
        //第一部分
        if(mapList!=null&&mapList.size()>0){
            num1 = mapList.size();
            for(Map<String,String> mm:mapList){//遍历时间 获取每月数据
                Map<String,Object> firstMap = new HashMap<String,Object>();
                map.put("startTime",mm.get("monthStart"));
                map.put("endTime",mm.get("monthEnd"));
                String sqlO = sql + " AND EVALUATION_DATE>=:startTime AND EVALUATION_DATE <=:endTime ";
                if( StringUtils.isNotBlank(qvo.getHospId())){
                    map.put("HOSP_ID",qvo.getHospId());
                    sqlO += " AND EVALUATION_HOSP_ID = :HOSP_ID ";
                }
                if(StringUtils.isNotBlank(qvo.getAreaId())){
                    map.put("AREA_CODE",AreaUtils.getAreaCode(qvo.getAreaId())+"%");
                    sqlO += " AND EVALUATION_AREA_CODE LIKE :AREA_CODE ";
                }
                if(StringUtils.isNotBlank(qvo.getTeamId())){
                    map.put("TEAM_ID",qvo.getTeamId());
                    sqlO += " AND EVALUATION_TEAM_ID=:TEAM_ID";
                }
                List<Map> maps = this.sysDao.getServiceDo().findSqlMap(sqlO,map);
                firstMap.put("name",mm.get("monthStart").substring(5,7));
                if(maps!=null&&maps.size()>0){
                    if(maps.get(0).get("scout")!=null){
                        String num = maps.get(0).get("scout").toString();
                        dd +=MyMathUtil.round(Double.parseDouble(num),2);
                        firstMap.put("value",MyMathUtil.round(Double.parseDouble(num),2));
                    }else{
                        firstMap.put("value",0);
                    }

                }
                assessList.add(firstMap);
            }
        }

        //第二部分查询评价
        String result = null;
        if(StringUtils.isNotBlank(qvo.getAreaId())){
            if (AreaUtils.getAreaCode(qvo.getAreaId()).length() == 6) {//区级行政，往下查看医院的排名
                //区下的医院集合
                List<AppHospDept> hospDepts = sysDao.getServiceDo().loadByPk(AppHospDept.class, "hospUpcityareaId", qvo.getAreaId());
                if(hospDepts != null && hospDepts.size() >0){
                    for (AppHospDept hosp : hospDepts) {
                        CdAddress address = this.sysDao.getCdAddressDao().findByCacheCode(hosp.getHospAreaCode());
                        Map<String, Object> map1 = getAssess(address, hosp,qvo.getYearStart(),qvo.getYearEnd());
                        list.add(map1);
                    }
                }
            }else{
                List<CdAddress> lsCdAddress = sysDao.getCdAddressDao().findUpCasheAreaCode(qvo.getAreaId());
                if(lsCdAddress != null && lsCdAddress.size()>0){
                    for (CdAddress address : lsCdAddress) {
                        Map<String, Object> map1 = getAssess(address, null,qvo.getYearStart(),qvo.getYearEnd());
                        list.add(map1);
                    }
                }
            }
            result = "1";
        }else if(StringUtils.isNotBlank(qvo.getHospId())){
            List<AppTeam> lsTeam = sysDao.getAppTeamDao().findAll(qvo.getHospId());
            if(lsTeam != null && lsTeam.size()>0){
                for(AppTeam team : lsTeam){
                    Map<String, Object> map1 = getAssessTeam(team,qvo.getYearStart(),qvo.getYearEnd());
                    list.add(map1);
                }
            }
            result = "2";
        }else if(StringUtils.isNotBlank(qvo.getTeamId())){
            List<AppDrUser> lsDr = sysDao.getAppTeamMemberDao().findAll(qvo.getTeamId());
            if(lsDr !=null && lsDr.size()>0){
                for(AppDrUser drUser:lsDr){
                    Map<String, Object> map1 = getAssessDr(drUser,qvo.getYearStart(),qvo.getYearEnd());
                    list.add(map1);
                }
            }
            result = "3";
        }
        Collections.sort(list, ComparatorUtils.getComparator());
        HashMap<String,Object> returnMap = new HashMap<>();
        returnMap.put("total",list);
        returnMap.put("state",result);
        returnMap.put("assessList",assessList);
        double scount = 0;
        if(num1!=0){
            scount = MyMathUtil.div(Double.valueOf(dd),Double.valueOf(num1),4);
        }
        double resultDouble = MyMathUtil.round(Double.parseDouble(String.valueOf(scount)),2);
        returnMap.put("result",String.valueOf(resultDouble));
        return returnMap;
    }


    /**
     * 查询评价
     * @param address
     * @param hosp
     * @param startDate
     * @param endDate
     * @return
     */
    public Map<String,Object> getAssess(CdAddress address,AppHospDept hosp,String startDate,String endDate) throws Exception{
        //第二部分
        Map<String,Object> returnMap = new HashMap<String,Object>();
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT avg(EVALUATION_AVERAGE) AS scout " +
                "FROM APP_DR_EVALUATION WHERE 1=1 ";
        map.put("startDate",ExtendDate.getFirstDayOfMonth(startDate)+" 00:00:00");
        map.put("endDate",ExtendDate.getLastDayOfMonth(endDate)+" 23:59:59");
        sql += " AND EVALUATION_DATE>=:startDate AND EVALUATION_DATE <=:endDate";
        if(hosp != null){
            map.put("HOSP_ID",hosp.getId());
            sql += " AND EVALUATION_HOSP_ID = :HOSP_ID ";
            returnMap.put("hospId",hosp.getId());//区域编码
            returnMap.put("hospName",hosp.getHospName());//名称
            returnMap.put("areaCode",null);//区域编码
            returnMap.put("areaName",null);//区域名称
        }else {
            map.put("AREA_CODE",AreaUtils.getAreaCode(address.getCtcode())+"%");
            sql += " AND EVALUATION_AREA_CODE LIKE :AREA_CODE ";
            returnMap.put("areaCode",address.getCtcode());//区域编码
            returnMap.put("areaName",address.getAreaSname());//区域名称
            returnMap.put("hospId",null);//区域编码
            returnMap.put("hospName",null);//名称
        }
        List<Map> maps = this.sysDao.getServiceDo().findSqlMap(sql,map);

        if(maps!=null&&maps.size()>0){
            if(maps.get(0).get("scout")!=null){
                returnMap.put("scout",MyMathUtil.round(Double.parseDouble(maps.get(0).get("scout").toString()),2));
            }else{
                returnMap.put("scout",0);
            }
        }
        return returnMap;
    }
    public Map<String,Object> getAssessTeam(AppTeam team,String startDate,String endDate) throws Exception{
        Map<String,Object> returnMap = new HashMap<String,Object>();
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT avg(EVALUATION_AVERAGE) AS scout " +
                "FROM APP_DR_EVALUATION WHERE 1=1 ";
        map.put("startDate",ExtendDate.getFirstDayOfMonth(startDate)+" 00:00:00");
        map.put("endDate",ExtendDate.getLastDayOfMonth(endDate)+" 23:59:59");
        sql += " AND EVALUATION_DATE>=:startDate AND EVALUATION_DATE <=:endDate";
        if(team!=null){
            map.put("teamId",team.getId());
            sql += " AND EVALUATION_TEAM_ID =:teamId";
        }
        List<Map> maps = this.sysDao.getServiceDo().findSqlMap(sql,map);

        if(maps!=null&&maps.size()>0){
            if(maps.get(0).get("scout")!=null){
                returnMap.put("scout",MyMathUtil.round(Double.parseDouble(maps.get(0).get("scout").toString()),2));
            }else{
                returnMap.put("scout",0);
            }
        }
        returnMap.put("teamName",team.getTeamName());//团队名称
        returnMap.put("teamDrName",team.getTeamDrName());//团队队长名称
        returnMap.put("teamId",team.getId());
        return returnMap;
    }
    public Map<String,Object> getAssessDr(AppDrUser drUser,String startDate,String endDate) throws Exception{
        Map<String,Object> returnMap = new HashMap<String,Object>();
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "SELECT avg(EVALUATION_AVERAGE) AS scout " +
                "FROM APP_DR_EVALUATION WHERE 1=1 ";
        map.put("startDate",ExtendDate.getFirstDayOfMonth(startDate)+" 00:00:00");
        map.put("endDate",ExtendDate.getLastDayOfMonth(endDate)+" 23:59:59");
        sql += " AND EVALUATION_DATE>=:startDate AND EVALUATION_DATE <=:endDate";
        if(drUser!=null){
            map.put("drId",drUser.getId());
            sql += " AND EVALUATION_DR_ID =:drId";
        }
        List<Map> maps = this.sysDao.getServiceDo().findSqlMap(sql,map);

        if(maps!=null&&maps.size()>0){
            if(maps.get(0).get("scout")!=null){
                returnMap.put("scout",MyMathUtil.round(Double.parseDouble(maps.get(0).get("scout").toString()),2));
            }else{
                returnMap.put("scout",0);
            }
        }
        returnMap.put("drName",drUser.getDrName());//医生姓名
        returnMap.put("drId",drUser.getId());
        return returnMap;
    }

    @Override
    public Map<String, Object> findSignEconomicTypeCount(ResidentVo qvo)  throws Exception{
        HashMap rmap = new HashMap();
//        List<CdCode> ls = this.sysDao.getCodeDao().findGroupList(CodeGroupConstrats.GROUP_JJLX, CommonEnable.QIYONG.getValue());
        List<AppLabelManage> ls = this.sysDao.getAppLabelManageDao().findByType(LabelManageType.JJLX.getValue());
        if(ls!=null && !ls.isEmpty()) {
            HashMap map = new HashMap();
            map.put("STARTDATE",qvo.getYearStart());
            map.put("ENDDATE",qvo.getYearEnd());
            String sqlDate = " AND t.MANAGE_YEAR_MONTH >= :STARTDATE AND t.MANAGE_YEAR_MONTH <= :ENDDATE ";
            String sql = "SELECT " +
                    "SUM(t.MANAGE_LOW_FAMILY_COUNT) manageLowFamilyCount," +
                    "SUM(t.MANAGE_DESTITUTE_FAMILY_COUNT) manageDestituteFamilyCount," +
                    "SUM(t.MANAGE_SPECIAL_PLAN_FAMILY_COUNT) manageSpecialPlanFamilyCount," +
                    "SUM(t.MANAGE_GENERAL_POPULATION_COUNT) manageGeneralPopulationCount," +
                    "SUM(t.MANAGE_POOR_FAMILY_COUNT) managePoorFamilyCount " +
                    " FROM APP_MANAGE_COUNT t where 1=1 "+sqlDate;
            if(StringUtils.isNotBlank(qvo.getTeamId())){
                map.put("TEAM_ID",qvo.getHospId());
                sql += " AND t.MANAGE_TEAM_ID = :TEAM_ID ";
            }
            if(StringUtils.isNotBlank(qvo.getHospId())){
                map.put("HOSP_ID",qvo.getHospId());
                sql += " AND t.MANAGE_HOSP_ID = :HOSP_ID ";
            }
            if(StringUtils.isNotBlank(qvo.getAreaId())){
                String areaCode = AreaUtils.getAreaCode(qvo.getAreaId());
                map.put("AREA_CODE",areaCode+"%");
                sql += " AND t.MANAGE_AREA_CODE LIKE :AREA_CODE ";
            }
            List<Map> maps = this.sysDao.getServiceDo().findSqlMap(sql,map);
            if(maps != null && maps.size() >0){
                for(AppLabelManage l:ls) {
                    ManageCountEntity v = new ManageCountEntity();
                    if(l.getLabelValue().equals("1")){//一般人口
                        if(maps.get(0).get("manageGeneralPopulationCount") != null){
                            rmap.put(l.getLabelTitle(),(int)Double.parseDouble(maps.get(0).get("manageGeneralPopulationCount").toString()));
                        }
                    }else if(l.getLabelValue().equals("2")){//建档立卡贫困人口
                        if(maps.get(0).get("managePoorFamilyCount") != null){
                            rmap.put(l.getLabelTitle(),(int)Double.parseDouble(maps.get(0).get("managePoorFamilyCount").toString()));
                        }
                    }else if(l.getLabelValue().equals("3")){//低保户
                        if(maps.get(0).get("manageLowFamilyCount") != null){
                            rmap.put(l.getLabelTitle(),(int)Double.parseDouble(maps.get(0).get("manageLowFamilyCount").toString()));
                        }
                    }else if(l.getLabelValue().equals("4")){//特困户（五保户）
                        if(maps.get(0).get("manageDestituteFamilyCount") != null){
                            rmap.put(l.getLabelTitle(),(int)Double.parseDouble(maps.get(0).get("manageDestituteFamilyCount").toString()));
                        }
                    }else if(l.getLabelValue().equals("5")){//计生特殊家庭
                        if(maps.get(0).get("manageSpecialPlanFamilyCount") != null){
                            rmap.put(l.getLabelTitle(),(int)Double.parseDouble(maps.get(0).get("manageSpecialPlanFamilyCount").toString()));
                        }
                    }
                }
                return rmap;
            }
        }
        return rmap;
    }

    /**
     * 转签统计
     * @param qvo
     * @return
     */
    @Override
    public Map<String, Object> findGotoSign(ResidentVo qvo)  throws Exception{

        Map<String,Object> returnMap = new HashMap<String,Object>();
        Map<String,Object> map = new HashMap<String,Object>();
        //每项转签原因人数和比例
        String sqlxx ="SELECT\n" +
                "\tCOUNT(1) count,\n" +
                "\tb.GS_REASON_TYPE reasonType\n" +
                "FROM\n" +
                "\tAPP_GOTOSIGN_RECORD a\n" +
                "INNER JOIN APP_GOTO_SIGN_FB b ON a.ID = b.GS_ID\n" ;
        String sqlxxcount = "SELECT count(1) FROM APP_GOTOSIGN_RECORD a INNER JOIN APP_GOTO_SIGN_FB b ON a.ID = b.GS_ID ";

        //查询续签数+转签数(包含本区域/本医院/本团队转签数)
        String sql = "SELECT count(1) FROM APP_SIGN_FORM a WHERE 1=1";

        //查询转入数
        String sqll= "SELECT count(1) FROM APP_GOTOSIGN_RECORD a WHERE 1=1 ";
        //查询转出数
        String sqlzc = "SELECT count(1) FROM APP_GOTOSIGN_RECORD a WHERE 1=1 ";

        //跨区人数
        String sqlkq = "SELECT count(1) FROM APP_GOTOSIGN_RECORD a WHERE 1=1 ";
        //跨医院人数
        String sqlkyy = "SELECT count(1) FROM APP_GOTOSIGN_RECORD a WHERE 1=1 ";
        //跨团队人数
        String sqlktd = "SELECT count(1) FROM APP_GOTOSIGN_RECORD a WHERE 1=1 ";

        //时间条件（当前年份）
        Calendar cal = Calendar.getInstance();
        map.put("year",cal.get(Calendar.YEAR));
        sql+= " AND DATE_FORMAT(a.SIGN_TO_DATE,'%Y') =:year";
        sqll += " AND DATE_FORMAT(a.GTS_CREATE_TIME,'%Y')=:year";
        sqlzc +=" AND DATE_FORMAT(a.GTS_CREATE_TIME,'%Y')=:year";
        sqlxx +=" AND DATE_FORMAT(a.GTS_CREATE_TIME,'%Y')=:year";
        sqlkq +=" AND DATE_FORMAT(a.GTS_CREATE_TIME,'%Y')=:year";
        sqlkyy+=" AND DATE_FORMAT(a.GTS_CREATE_TIME,'%Y')=:year";
        sqlktd+=" AND DATE_FORMAT(a.GTS_CREATE_TIME,'%Y')=:year";
        sqlxxcount+=" AND DATE_FORMAT(a.GTS_CREATE_TIME,'%Y')=:year";

        sqlkq += " AND a.GTS_OLD_AREA_CODE != a.GTS_AREA_CODE";
        sqlkyy+= " AND a.GTS_OLD_HOSP_ID != a.GTS_HOSP_ID";
        sqlktd+= " AND a.GTS_OLD_TEAM_ID != a.GTS_TEAM_ID";
        int kqcount = sysDao.getServiceDo().findCount(sqlkq,map);
        int kyycount = sysDao.getServiceDo().findCount(sqlkyy,map);
        int ktdcount = sysDao.getServiceDo().findCount(sqlktd,map);
        returnMap.put("kqcount",kqcount);
        returnMap.put("kyycount",kyycount);
        returnMap.put("ktdcount",ktdcount);

        //区域编号条件
        if(StringUtils.isNotBlank(qvo.getAreaId())){
            map.put("areaId",AreaUtils.getAreaCode(qvo.getAreaId())+"%");
            sql +=" AND a.SIGN_AREA_CODE LIKE:areaId";
            sqll+=" AND a.GTS_AREA_CODE LIKE:areaId AND a.GTS_OLD_AREA_CODE NOT LIKE:areaId";
            sqlzc+=" AND a.GTS_OLD_AREA_CODE LIKE:areaId AND a.GTS_AREA_CODE NOT LIKE:areaId";
            sqlxx+=" AND a.GTS_OLD_AREA_CODE LIKE:areaId OR a.GTS_AREA_CODE LIKE:areaId";
            sqlxxcount+=" AND a.GTS_OLD_AREA_CODE LIKE:areaId OR a.GTS_AREA_CODE LIKE:areaId";
        }
        //医院条件
        if(StringUtils.isNotBlank(qvo.getHospId())){
            map.put("hospId",qvo.getHospId());
            sql +=" AND a.SIGN_HOSP_ID =:hospId";
            sqll +=" AND a.GTS_HOSP_ID =:hospId AND a.GTS_OLD_HOSP_ID !=:hospId";
            sqlzc+=" AND a.GTS_OLD_HOSP_ID=:hospId AND a.GTS_HOSP_ID !=:hospId";
            sqlxx+=" AND a.GTS_OLD_HOSP_ID=:hospId OR a.GTS_HOSP_ID =:hospId";
            sqlxxcount+=" AND a.GTS_OLD_HOSP_ID=:hospId OR a.GTS_HOSP_ID =:hospId";
        }

        //团队条件
        if(StringUtils.isNotBlank(qvo.getTeamId())){
            map.put("teamId",qvo.getTeamId());
            sql += " AND a.SIGN_TEAM_ID=:teamId";
            sqll += " AND a.GTS_TEAM_ID=:teamId AND a.GTS_OLD_TEAM_ID!=:teamId";
            sqlzc+= " AND a.GTS_OLD_TEAM_ID=:teamId AND a.GTS_TEAM_ID!=:teamId";
            sqlxx+=" AND a.GTS_OLD_TEAM_ID=:teamId OR a.GTS_TEAM_ID=:teamId";
            sqlxxcount+=" AND a.GTS_OLD_TEAM_ID=:teamId OR a.GTS_TEAM_ID=:teamId";
        }

        //已续签和已转签状态条件(1转签 2续签)
        String sql1 = sql+" AND a.SIGN_GOTO_SIGN_STATE IN('1','2')";
        //已续签人数
        String sql2 = sql + " AND a.SIGN_GOTO_SIGN_STATE='1'";
        int xqcount = sysDao.getServiceDo().findCount(sql2,map);
        //非转入续签居民数
        int fzrcount = sysDao.getServiceDo().findCount(sql1,map);
        //转入居民数
        int zrcount = sysDao.getServiceDo().findCount(sqll,map);
        returnMap.put("zrcount",zrcount);
        //续签居民总数(非转入续签居民数+转入居民数)
        int xqzs = fzrcount+zrcount;
        returnMap.put("xqzs",xqzs);
        //转签数
        int zqcount = xqzs-xqcount;
        returnMap.put("zqcount",zqcount);
        //转签率(转签数/续签居民总数)
        double zql=0;
        if(xqzs!=0){
            zql =MyMathUtil.div(Double.valueOf(zqcount),Double.valueOf(xqzs),4)*100;//续签率
        }
        returnMap.put("zql",zql);
        //转出数
        int zccount = sysDao.getServiceDo().findCount(sqlzc,map);
        returnMap.put("zccount",zccount);
        sqlxx +=   " GROUP BY\n" +
                "\tb.GS_REASON_TYPE";
        List<AppGoToEntity> lgt =sysDao.getServiceDo().findSqlMapRVo(sqlxx,map,AppGoToEntity.class);
        returnMap.put("reansonList",lgt);
        int blzs = sysDao.getServiceDo().findCount(sqlxxcount,map);
        returnMap.put("blzs",blzs);

        //钻取数据
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();//签约
        String result = null;
        if(StringUtils.isNotBlank(qvo.getAreaId())){
            if (AreaUtils.getAreaCode(qvo.getAreaId()).length() == 6){//区级行政，往下查看医院的排名
                //区下的医院集合
                List<AppHospDept> hospDepts = sysDao.getServiceDo().loadByPk(AppHospDept.class, "hospUpcityareaId", qvo.getAreaId());
                if(hospDepts != null && hospDepts.size() >0){
                    for (AppHospDept hosp : hospDepts){
                        CdAddress address = this.sysDao.getCdAddressDao().findByCacheCode(hosp.getHospAreaCode());
                        Map<String, Object> mapp = getGotoSianCount(address, hosp,null);
                        list.add(mapp);
                    }
                }
            }else{
                List<CdAddress> lsCdAddress = sysDao.getCdAddressDao().findUpCasheAreaCode(qvo.getAreaId());
                if(lsCdAddress != null && lsCdAddress.size()>0){
                    for (CdAddress address : lsCdAddress) {
                        Map<String, Object> mapp = getGotoSianCount(address, null,null);
                        list.add(mapp);
                    }
                }
            }
            result = "1";
        }else if(StringUtils.isNotBlank(qvo.getHospId())){
            List<AppTeam> lsTeam = sysDao.getAppTeamDao().findAll(qvo.getHospId());
            if(lsTeam != null && lsTeam.size()>0){
                for(AppTeam team : lsTeam){
                    Map<String, Object> mapp = getGotoSianCount(null,null,team);
                    list.add(mapp);
                }
            }
            result = "2";
        }
        Collections.sort(list, ComparatorUtils.getComparator());
        returnMap.put("zqList",list);
        returnMap.put("result",result);
        return returnMap;
    }

    public Map<String,Object> getGotoSianCount(CdAddress address,AppHospDept hosp,AppTeam team) throws Exception{

        int zqs = 0;//转签数
        int zrs = 0;//转入数
        int zcs = 0;//转出数

        Map<String,Object> map = new HashMap<>();
        Map<String,Object> returnMap = new HashMap<>();
        Calendar cal = Calendar.getInstance();
        map.put("year",cal.get(Calendar.YEAR));
        //转入数
        String zrSql = "SELECT count(1) FROM APP_GOTOSIGN_RECORD a WHERE 1=1 AND DATE_FORMAT(a.GTS_CREATE_TIME,'%Y')=:year";

        //转出数
        String zcSql = "SELECT count(1) FROM APP_GOTOSIGN_RECORD a WHERE 1=1 AND DATE_FORMAT(a.GTS_CREATE_TIME,'%Y')=:year";

        //转签数
        String zqSql = "SELECT count(1) FROM APP_SIGN_FORM a WHERE 1=1 AND DATE_FORMAT(a.SIGN_TO_DATE,'%Y')=:year ";

        if(hosp!=null){
            map.put("hospId",hosp.getId());
            zrSql += " AND a.GTS_HOSP_ID=:hospId AND a.GTS_OLD_HOSP_ID!=:hospId";
            zcSql += " AND a.GTS_HOSP_ID!=:hospId AND a.GTS_OLD_HOSP_ID=:hospId";
            zqSql += " AND a.SIGN_HOSP_ID=:hospId";
        }if(team!=null){
            map.put("teamId",team.getId());
            zrSql += " AND a.GTS_TEAM_ID=:teamId AND a.GTS_OLD_TEAM_ID!=:teamId";
            zcSql += " AND a.GTS_TEAM_ID!=:teamId AND a.GTS_OLD_TEAM_ID=:teamId";
            zqSql += " AND a.SIGN_TEAM_ID=:teamId";

        }else{
            map.put("areaCode",AreaUtils.getAreaCode(address.getCtcode())+"%");
            zrSql += " AND a.GTS_AREA_CODE LIKE :areaCode AND a.GTS_OLD_AREA_CODE NOT LIKE:areaCode";
            zcSql += " AND a.GTS_AREA_CODE NOT LIKE :areaCode AND a.GTS_OLD_AREA_CODE LIKE:areaCode";
            zqSql += " AND a.SIGN_AREA_CODE LIKE:areaCode";
        }
        zqSql+=" AND a.SIGN_GOTO_SIGN_STATE='2'";
        zrs = sysDao.getServiceDo().findCount(zrSql,map);
        zcs = sysDao.getServiceDo().findCount(zcSql,map);
        zqs = sysDao.getServiceDo().findCount(zqSql,map);
        zqs+=zrs;
        if(hosp != null){
            returnMap.put("hospId",hosp.getId());//区域编码
            returnMap.put("hospName",hosp.getHospName());//名称
            returnMap.put("areaCode",null);//区域编码
            returnMap.put("areaName",null);//区域名称
        }else if(team != null){
            returnMap.put("teamName",team.getTeamName());//团队名称
            returnMap.put("teamDrName",team.getTeamDrName());//团队队长名称
        }else{
            returnMap.put("areaCode",address.getCtcode());//区域编码
            returnMap.put("areaName",address.getAreaSname());//区域名称
            returnMap.put("hospId",null);//区域编码
            returnMap.put("hospName",null);//名称
        }
        returnMap.put("zqs",zqs);//转签数
        returnMap.put("zrs",zrs);//转人数
        returnMap.put("zcs",zcs);//转出数
        return returnMap;
    }
}
