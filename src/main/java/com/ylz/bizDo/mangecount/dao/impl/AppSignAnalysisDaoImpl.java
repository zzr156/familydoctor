package com.ylz.bizDo.mangecount.dao.impl;

import com.ylz.bizDo.app.entity.AppManageArchivingCountEntity;
import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.app.vo.AppManageArchivingCountQvo;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.cd.po.CdAddressConfiguration;
import com.ylz.bizDo.cd.po.CdAddressPeople;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.jtapp.commonEntity.AppArchivintPeopleEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppArchivintPeopleTTEntity;
import com.ylz.bizDo.jtapp.drEntity.ReferralInfo;
import com.ylz.bizDo.jtapp.patientEntity.AppTeamMemberEntity;
import com.ylz.bizDo.mangecount.dao.AppSignAnalysisDao;
import com.ylz.bizDo.mangecount.entity.ManageCountEntity;
import com.ylz.bizDo.mangecount.vo.ResidentVo;
import com.ylz.bizDo.smjq.smVo.AppSmMnanageQvo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.*;
import com.ylz.packcommon.common.util.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.util.*;

/**
 * 签约分析
 */
@Service("appSignAnalysisDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppSignAnalysisDaoImpl implements AppSignAnalysisDao {

    @Autowired
    private SysDao sysDao;

    /**
     * 签约首页统计
     * @param qvo areaId 区域主键 hospId 社区主键 teamId 团队主键
     * @return
     */
    @Override
    public Map<String,Object> findSignAnalysisIndex(ResidentVo qvo) throws Exception {
        Map<String, Object> map = new HashMap<String,Object>();
        if(StringUtils.isNotBlank(qvo.getAreaId())){
            CdAddress address = this.sysDao.getCdAddressDao().findByCacheCode(qvo.getAreaId());
            map = getSianCount(address, null,qvo.getYearStart(),qvo.getYearEnd());
        }else if(StringUtils.isNotBlank(qvo.getHospId())){
            AppHospDept hospDept = (AppHospDept)this.sysDao.getServiceReadDo().find(AppHospDept.class,qvo.getHospId());
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
    public Map<String, Object> findSignAnalysisList(ResidentVo qvo) throws Exception {
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();//签约
        String result = null;
        if(StringUtils.isNotBlank(qvo.getAreaId())){
            if (AreaUtils.getAreaCode(qvo.getAreaId()).length() == 6 || AreaUtils.getAreaCode(qvo.getAreaId()).length() == 5) { //区级行政，往下查看医院的排名
                //区下的医院集合
                List<AppHospDept> hospDepts = sysDao.getAppHospDeptDao().findUpHospListRead(qvo.getAreaId());
//                List<AppHospDept> hospDepts = sysDao.getServiceReadDo().loadByPk(AppHospDept.class, "hospUpcityareaId", qvo.getAreaId());
                if(hospDepts != null && hospDepts.size() >0){
                    for (AppHospDept hosp : hospDepts) {
                        if(hosp.getHospState().equals("1")){
                            CdAddress address = this.sysDao.getCdAddressDao().findByCacheCode(hosp.getHospAreaCode());
                            Map<String, Object> map = getSianCount(address, hosp,qvo.getYearStart(),qvo.getYearEnd());
                            list.add(map);
                        }
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
            //根据查询时间判断是否要查出已删团队
            List<AppTeam> lsTeam = sysDao.getAppTeamDao().findTeamByTime(qvo.getHospId(),qvo.getYearStart(),qvo.getYearEnd());
//            List<AppTeam> lsTeam = sysDao.getAppTeamDao().findAll(qvo.getHospId());
            if(lsTeam != null && lsTeam.size()>0){
                for(AppTeam team : lsTeam){
                    Map<String, Object> map = getSianCountTeam(team,qvo.getYearStart(),qvo.getYearEnd());
                    list.add(map);
                }
            }
            result = "2";
        }
        else if(StringUtils.isNotBlank(qvo.getTeamId())){
            List<AppDrUser> lsDr = sysDao.getAppTeamMemberDao().findDrListByTeamId(qvo.getTeamId());
            if(lsDr != null && lsDr.size()>0){
                for(AppDrUser drUser:lsDr){
                    Map<String,Object> map = getSignCountDr(drUser,qvo.getYearStart(),qvo.getYearEnd());
                    list.add(map);
                }
            }
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
    public Map<String,Object> getSianCount(CdAddress address,AppHospDept hosp,String startDate,String endDate) throws Exception{
        Calendar calendar = Calendar.getInstance();
        int nowYear = calendar.get(Calendar.YEAR);
        int nowMonth = calendar.get(Calendar.MONTH) + 1;
        int now = Integer.parseInt(String.valueOf(nowYear)+ String.valueOf(nowMonth));
        String[] year = endDate.split("-");
        int endYear = Integer.parseInt(year[0]);
        int endMonth = Integer.parseInt(year[1]);
        int end = Integer.parseInt(String.valueOf(endYear)+ String.valueOf(endMonth));
        int areaReg = 0;//户籍人数
        int areaRegTarget = 0;//户籍目标数
        int areaFoucs = 0;//重点人群数
        int areaFoucsTarget = 0;//重点人群目标数
        int areaEconomic = 0;//经济人口性质人口数
        int areaEconomicTarget = 0;//经济人口性质目标数
        int areaSignTop = 0; //医生 团队目标上限数
        //判断是否统计已删团队数据如果查询截止年月份包含当前年月则不统计已删的团队签约数，如果不包含则不统计这个时间段已删的团队统计数
        boolean flag = false;
        if(end >= now){//大于即包含,去除已删团队统计数
            flag = true;
        }

        Map<String,Object> map = new HashMap<String,Object>();
        Map<String,Object> returnMap = new HashMap<String,Object>();
        Map<String,Object> returnSignCountMap = new HashMap<String,Object>();
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
           /* if(AreaUtils.getAreaCode(address.getCtcode()).length() == 9 || AreaUtils.getAreaCode(address.getCtcode()).length() == 8){
                // 社区进入
                areaSignTop = Integer.parseInt(StringUtils.isNotBlank(lsPeople.get(0).getAreaDisSignTop()) ? lsPeople.get(0).getAreaDisSignTop():"0");
            }else{*/
            areaSignTop = Integer.parseInt(StringUtils.isNotBlank(lsPeople.get(0).getAreaPopulation()) ? lsPeople.get(0).getAreaPopulation():"0");
            // }
            areaReg = Integer.parseInt(StringUtils.isNotBlank(lsPeople.get(0).getAreaPopulation()) ? lsPeople.get(0).getAreaPopulation():"0");
            areaRegTarget = Integer.parseInt(StringUtils.isNotBlank(lsPeople.get(0).getAreaTarget()) ? lsPeople.get(0).getAreaTarget():"0");
            areaFoucs = Integer.parseInt(StringUtils.isNotBlank(lsPeople.get(0).getAreaFocus()) ? lsPeople.get(0).getAreaFocus():"0");
            areaFoucsTarget = Integer.parseInt(StringUtils.isNotBlank(lsPeople.get(0).getAreaFocusTarget()) ? lsPeople.get(0).getAreaFocusTarget():"0");
            areaEconomic = Integer.parseInt(StringUtils.isNotBlank(lsPeople.get(0).getAreaEconomic()) ? lsPeople.get(0).getAreaEconomic():"0");
            areaEconomicTarget = Integer.parseInt(StringUtils.isNotBlank(lsPeople.get(0).getAreaEconomicTarget()) ? lsPeople.get(0).getAreaEconomicTarget():"0");
        }
        int yqy = 0;
        int ycqy = 0;//有偿签约数(有偿签给数=已签约-（特困+低保+建档立卡+计生特殊家庭）)
        int zdrqyqy = 0;
        int webyqy = 0;//web已签约
        int appyqy= 0;//app已签约
        int wechatyqy = 0;//微信已签约
        int aioyqy=0;//一体机已签约
        int renew = 0;
        int totalRenew = 0;
        int economic = 0;//人口性质签约人数

        map.put("STARTDATE",startDate);
        map.put("ENDDATE",endDate);
        map.put("stratTime",ExtendDate.getFirstDayOfMonth(startDate) + " 00:00:00");
        map.put("endTime",ExtendDate.getLastDayOfMonth(endDate) + " 23:59:59");
        String sqlDate = " AND t.MANAGE_YEAR_MONTH >= :STARTDATE AND t.MANAGE_YEAR_MONTH <= :ENDDATE ";
        String sql = "SELECT SUM(t.MANAGE_SIGN_COUNT) signCount,SUM(t.MANAGE_KEY_SIGN_COUNT) keySingCount," +
                "SUM(t.MANAGE_SIGN_WEB_COUNT) signWebCount,SUM(t.MANAGE_SIGN_APP_COUNT) singAppCount," +
                "SUM(t.MANAGE_SIGN_WECHAT_COUNT) singWechatCount,SUM(t.MANAGE_SIGN_AIO_COUNT) signAioCount,"+
                "SUM(t.MANAGE_RENEW) renew," +
                "SUM(t.MANAGE_ECONOMIC_SIGN_COUNT) manageEconomicSignCount," +
                "SUM(t.MANAGE_LOW_FAMILY_COUNT) manageLowFamilyCount," +
                "SUM(t.MANAGE_DESTITUTE_FAMILY_COUNT) manageDestituteFamilyCount," +
                "SUM(t.MANAGE_SPECIAL_PLAN_FAMILY_COUNT) manageSpecialPlanFamilyCount," +
                "SUM(t.MANAGE_POOR_FAMILY_COUNT) managePoorFamilyCount, " +
                "SUM(t.MANAGE_TOTAL_RENEW) totalRenew "+
                " FROM APP_MANAGE_COUNT t where 1=1 "+sqlDate;

        String sqlTeam = "";
        if(flag){
            sqlTeam = "SELECT aa.ID FROM APP_TEAM aa WHERE aa.TEAM_DEL_STATE = '1' ";
        }else{
            sqlTeam = "SELECT aa.ID FROM APP_TEAM aa WHERE aa.TEAM_DEL_STATE = '1' " +
                    "AND aa.TEAM_DEL_TIME >=:stratTime\n" +
                    "AND aa.TEAM_DEL_TIME <=:endTime ";
        }
        if(hosp != null){
            map.put("HOSP_ID",hosp.getId());
            sql += " AND t.MANAGE_HOSP_ID = :HOSP_ID ";

         /*   //机构不为空，判断查询结束时间是否是当前月
            if(ExtendDate.getYM(Calendar.getInstance()).equals(endDate)){
                //是，排除已删除团队的签约数
                String teamStr = sysDao.getAppTeamDao().findTeamIdsByHospId(hosp.getId());
                if(StringUtils.isNotBlank(teamStr)){
                    map.put("teamIds",teamStr.split(","));
                    sql += " AND t.MANAGE_TEAM_ID NOT IN (:teamIds) ";
                }
            }*/
            sqlTeam += " AND aa.TEAM_HOSP_ID =:HOSP_ID ";

        }else {
            map.put("AREA_CODE",AreaUtils.getAreaCode(address.getCtcode())+"%");
            //漳州“台商投资区”，“招商局漳州开发区”，“常山农场经济开发区”这三个区要特别判断
            if("350698000000".equals(address.getCtcode())){//台商投资区
                map.put("AREA_CODE","350681102%");
            }else if("350699000000".equals(address.getCtcode())){//招商局漳州开发区
                map.put("AREA_CODE","350681501%");
            }else if("350697000000".equals(address.getCtcode())){//常山农场经济开发区
                map.put("AREA_CODE","350622450%");
            }else if("350681000000".equals(address.getCtcode())){
                map.put("notAreaCode",new String[]{"350681102000","350681501000","350622450000"});
                sql += " AND t.MANAGE_AREA_CODE NOT IN (:notAreaCode) ";
            }else if("350622000000".equals(address.getCtcode())){
                map.put("notAreaCode",new String[]{"350681102000","350681501000","350622450000"});
                sql += " AND t.MANAGE_AREA_CODE NOT IN (:notAreaCode) ";
            }
            //区域不为空，
            if(ExtendDate.getYM(Calendar.getInstance()).equals(endDate)){

            }
            sql += " AND t.MANAGE_AREA_CODE LIKE :AREA_CODE ";
        }

        //是否去除已删团队签约统计数
        sql += " AND t.MANAGE_TEAM_ID NOT IN ("+sqlTeam+") ";

        List<Map> maps = this.sysDao.getServiceReadDo().findSqlMap(sql,map);
        if(maps != null && maps.size() >0){
            if(maps.get(0).get("signCount") != null){
                yqy = (int) Double.parseDouble(maps.get(0).get("signCount").toString());//已签约
            }
            if(maps.get(0).get("keySingCount") != null){
                zdrqyqy = (int) Double.parseDouble(maps.get(0).get("keySingCount").toString());//重點人群已签约
            }
            if(maps.get(0).get("signWebCount") != null){
                webyqy = (int) Double.parseDouble(maps.get(0).get("signWebCount").toString());//web已签约
            }
            if(maps.get(0).get("singAppCount") != null){
                appyqy = (int) Double.parseDouble(maps.get(0).get("singAppCount").toString());//app已签约
            }
            if(maps.get(0).get("singWechatCount") != null) {
                wechatyqy = (int) Double.parseDouble(maps.get(0).get("singWechatCount").toString());//微信已签约
            }
            if(maps.get(0).get("signAioCount") != null){
                aioyqy = (int) Double.parseDouble(maps.get(0).get("signAioCount").toString());//一体机已签约数
            }
            if(maps.get(0).get("renew") !=null){
                renew = (int)Double.parseDouble(maps.get(0).get("renew").toString());//续签数
            }
            if(maps.get(0).get("manageEconomicSignCount")!=null){
                economic+=(int)Double.parseDouble(maps.get(0).get("manageEconomicSignCount").toString());//经济类型
            }
            /*if(maps.get(0).get("manageLowFamilyCount")!=null){
                economic+ =(int)Double.parseDouble(maps.get(0).get("manageLowFamilyCount").toString());//低保户
            }
            if(maps.get(0).get("manageDestituteFamilyCount")!=null){
                economic+ =(int)Double.parseDouble(maps.get(0).get("manageDestituteFamilyCount").toString());//特困户（五保户）
            }
            if(maps.get(0).get("manageSpecialPlanFamilyCount")!=null){
                economic+=(int)Double.parseDouble(maps.get(0).get("manageSpecialPlanFamilyCount").toString());//计生特殊家庭
            }
            if(maps.get(0).get("managePoorFamilyCount")!=null){
                economic+=(int)Double.parseDouble(maps.get(0).get("managePoorFamilyCount").toString());//建档立卡贫困人口
            }*/
            if(maps.get(0).get("totalRenew")!=null){
                totalRenew = (int)Double.parseDouble(maps.get(0).get("totalRenew").toString());//总签约数
            }
        }

        //如果结束时间大于等于当前时间
        if(end >= now){
//            List<CdCode> ls = this.sysDao.getCodeDao().findGroupList(CodeGroupConstrats.GROUP_JJLX, CommonEnable.QIYONG.getValue());
//            List<AppLabelManage> ls = this.sysDao.getAppLabelManageDao().findByType(LabelManageType.JJLX.getValue());
            map.put("STARTDATE", ExtendDate.getYMD(Calendar.getInstance())+" 00:00:00");
            map.put("ENDDATE",ExtendDate.getYMD(Calendar.getInstance())+" 23:59:59");
            sqlDate = " AND t.SIGN_FROM_DATE >= :STARTDATE AND t.SIGN_FROM_DATE <= :ENDDATE ";
            //签约数
            String sqlYqy = "SELECT count(1) FROM APP_SIGN_FORM t where 1=1 AND t.SIGN_STATE IN (:SIGN_STATE) "+sqlDate;
            //重点人群数
            String sqlZdrqqy = "SELECT t.* FROM APP_SIGN_FORM t LEFT JOIN APP_LABEL_GROUP b ON t.ID = b.LABEL_SIGN_ID where 1=1 AND t.SIGN_STATE IN (:SIGN_STATE) AND b.LABEL_VALUE NOT IN (:SIGN_PERS_GROUP) AND b.LABEL_TYPE='3'  "+sqlDate;
            //web签约数
            String sqlWebYqy = "SELECT count(1) FROM APP_SIGN_FORM t where 1=1 AND t.SIGN_STATE IN (:SIGN_STATE) AND t.UP_HPIS = :WEB_STATE  "+sqlDate;
            //app签约数
            String sqlAppYqy = "SELECT count(1) FROM APP_SIGN_FORM t where 1=1 AND t.SIGN_STATE IN (:SIGN_STATE) AND t.UP_HPIS = :APP_STATE "+sqlDate;
            //微信签约数
            String sqlWechatYqy = "SELECT count(1) FROM APP_SIGN_FORM t where 1=1 AND t.SIGN_STATE IN (:SIGN_STATE) AND t.UP_HPIS = :WECHAT_STATE "+sqlDate;
            //经济类型数
//            String sqlJjTypeV = "SELECT COUNT(1) FROM APP_SIGN_FORM t  LEFT JOIN APP_LABEL_ECONOMICS b ON t.ID = b.LABEL_SIGN_ID WHERE 1=1 AND  t.SIGN_STATE IN (:SIGN_STATE) "+sqlDate;
            String sqlJjType = "SELECT t.* FROM APP_SIGN_FORM t LEFT JOIN APP_LABEL_ECONOMICS b ON t.ID = b.LABEL_SIGN_ID where 1=1 AND t.SIGN_STATE IN (:SIGN_STATE) AND b.LABEL_VALUE NOT IN (:SIGN_JJ_TYPE) AND b.LABEL_TYPE='4'  "+sqlDate;
            //续签数
            String sqlRenew = "SELECT count(1) FROM APP_SIGN_FORM t where 1=1 AND t.SIGN_GOTO_SIGN_STATE = :SIGN_GOTO_SIGN_STATE "+sqlDate;
            //一体机签约数
            String sqlAioYqy = "SELECT count(1) FROM APP_SIGN_FORM t where 1=1 AND t.SIGN_STATE IN (:SIGN_STATE) AND t.UP_HPIS = :AIO_STATE "+sqlDate;
            String gotoSignState = SignFormType.YQY.getValue();//状态
            String[] state = {SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};//状态
            String[] fwState ={ResidentMangeType.PTRQ.getValue(),ResidentMangeType.WEIZHI.getValue()};
//            String[] jjState = {EconomicType.YBRK.getValue(),EconomicType.JDLKPKRK.getValue(),EconomicType.DBH.getValue(),EconomicType.TKH.getValue(),EconomicType.JSTSJT.getValue()};
            map.put("SIGN_GOTO_SIGN_STATE","2");
            map.put("SIGN_STATE",state);
            map.put("SIGN_PERS_GROUP",fwState);
            map.put("SIGN_JJ_TYPE",EconomicType.YBRK.getValue());
            map.put("WEB_STATE",SignFormType.WEBSTATE.getValue());
            map.put("APP_STATE",SignFormType.APPSTATE.getValue());
            map.put("WECHAT_STATE",SignFormType.WECHATSTATE.getValue());
            map.put("AIO_STATE",SignFormType.YITIJISTATE.getValue());
            if(hosp != null){
                map.put("HOSP_ID",hosp.getId());
                sqlYqy += " AND t.SIGN_HOSP_ID = :HOSP_ID ";
                yqy += this.sysDao.getServiceReadDo().findCount(sqlYqy,map);//已签约
                sqlWebYqy += " AND t.SIGN_HOSP_ID = :HOSP_ID ";
                webyqy += this.sysDao.getServiceReadDo().findCount(sqlWebYqy,map);//web已签约
                sqlAppYqy += " AND t.SIGN_HOSP_ID = :HOSP_ID ";
                appyqy += this.sysDao.getServiceReadDo().findCount(sqlAppYqy,map);//app已签约
                sqlWechatYqy += " AND t.SIGN_HOSP_ID = :HOSP_ID ";
                wechatyqy += this.sysDao.getServiceReadDo().findCount(sqlWechatYqy,map);//微信已签约
                sqlZdrqqy += " AND t.SIGN_HOSP_ID = :HOSP_ID ";
                String sqlZdrq = "SELECT COUNT(1) FROM ( "+sqlZdrqqy +" GROUP BY t.SIGN_PATIENT_ID) c";
                zdrqyqy += this.sysDao.getServiceReadDo().findCount(sqlZdrq,map);//重點人群已簽約
                sqlJjType += " AND t.SIGN_HOSP_ID = :HOSP_ID ";
                String sqlJjrq = "SELECT COUNT(1) FROM ( "+sqlJjType +" GROUP BY t.SIGN_PATIENT_ID) c";
                economic += this.sysDao.getServiceReadDo().findCount(sqlJjrq,map);//重點人群经济已簽約
                sqlRenew += " AND t.SIGN_HOSP_ID = :HOSP_ID ";
                renew += this.sysDao.getServiceReadDo().findCount(sqlRenew,map);//续签
                sqlAioYqy += " AND t.SIGN_HOSP_ID = :HOSP_ID ";
                aioyqy += this.sysDao.getServiceReadDo().findCount(sqlAioYqy,map);//一体机已签约
            }else {
                map.put("AREA_CODE",AreaUtils.getAreaCode(address.getCtcode())+"%");
                //漳州“台商投资区”，“招商局漳州开发区”，“常山农场经济开发区”这三个区要特别判断
                if("350698000000".equals(address.getCtcode())){//台商投资区
                    map.put("AREA_CODE","350681102%");
                }else if("350699000000".equals(address.getCtcode())){//招商局漳州开发区
                    map.put("AREA_CODE","350681501%");
                }else if("350697000000".equals(address.getCtcode())){//常山农场经济开发区
                    map.put("AREA_CODE","350622450%");
                }else if("350681000000".equals(address.getCtcode())){
                    map.put("notAreaCode",new String[]{"350681102000","350681501000","350622450000"});
                    sqlYqy += " AND t.SIGN_AREA_CODE NOT IN (:notAreaCode) ";
                    sqlWebYqy += " AND t.SIGN_AREA_CODE NOT IN (:notAreaCode) ";
                    sqlAppYqy += " AND t.SIGN_AREA_CODE NOT IN (:notAreaCode) ";
                    sqlWechatYqy += " AND t.SIGN_AREA_CODE NOT IN (:notAreaCode) ";
                    sqlZdrqqy += " AND t.SIGN_AREA_CODE NOT IN (:notAreaCode) ";
                    sqlJjType += " AND t.SIGN_AREA_CODE NOT IN (:notAreaCode) ";
                    sqlRenew += " AND t.SIGN_AREA_CODE NOT IN (:notAreaCode) ";
                    sqlAioYqy += " AND t.SIGN_AREA_CODE NOT IN (:notAreaCode) ";
                }else if("350622000000".equals(address.getCtcode())){
                    map.put("notAreaCode",new String[]{"350681102000","350681501000","350622450000"});
                    sqlYqy += " AND t.SIGN_AREA_CODE NOT IN (:notAreaCode) ";
                    sqlWebYqy += " AND t.SIGN_AREA_CODE NOT IN (:notAreaCode) ";
                    sqlAppYqy += " AND t.SIGN_AREA_CODE NOT IN (:notAreaCode) ";
                    sqlWechatYqy += " AND t.SIGN_AREA_CODE NOT IN (:notAreaCode) ";
                    sqlZdrqqy += " AND t.SIGN_AREA_CODE NOT IN (:notAreaCode) ";
                    sqlJjType += " AND t.SIGN_AREA_CODE NOT IN (:notAreaCode) ";
                    sqlRenew += " AND t.SIGN_AREA_CODE NOT IN (:notAreaCode) ";
                    sqlAioYqy += " AND t.SIGN_AREA_CODE NOT IN (:notAreaCode) ";
                }
                sqlYqy += " AND t.SIGN_AREA_CODE LIKE :AREA_CODE ";
                yqy += this.sysDao.getServiceReadDo().findCount(sqlYqy,map);//已签约
                sqlWebYqy += " AND t.SIGN_AREA_CODE LIKE :AREA_CODE ";
                webyqy += this.sysDao.getServiceReadDo().findCount(sqlWebYqy,map);//web已签约
                sqlAppYqy += " AND t.SIGN_AREA_CODE LIKE :AREA_CODE ";
                appyqy += this.sysDao.getServiceReadDo().findCount(sqlAppYqy,map);//app已签约
                sqlWechatYqy += " AND t.SIGN_AREA_CODE LIKE :AREA_CODE ";
                wechatyqy += this.sysDao.getServiceReadDo().findCount(sqlWechatYqy,map);//微信已签约
                sqlZdrqqy += " AND t.SIGN_AREA_CODE LIKE :AREA_CODE ";
                String sqlZdrq = "SELECT COUNT(1) FROM ( "+sqlZdrqqy +" GROUP BY t.SIGN_PATIENT_ID) c";
                zdrqyqy += this.sysDao.getServiceReadDo().findCount(sqlZdrq,map);//重點人群已簽約
                sqlJjType += " AND t.SIGN_AREA_CODE LIKE :AREA_CODE ";
                String sqlJjrq = "SELECT COUNT(1) FROM ( "+sqlJjType +" GROUP BY t.SIGN_PATIENT_ID) c";
                economic += this.sysDao.getServiceReadDo().findCount(sqlJjrq,map);//重點人群经济已簽約
                sqlRenew += " AND t.SIGN_AREA_CODE LIKE :AREA_CODE ";
                renew += this.sysDao.getServiceReadDo().findCount(sqlRenew,map);//续签
                sqlAioYqy += "  AND t.SIGN_AREA_CODE LIKE :AREA_CODE ";
                aioyqy += this.sysDao.getServiceReadDo().findCount(sqlAioYqy,map);//一体机已签约
            }


            /*if(ls != null && !ls.isEmpty()){
                sqlJjTypeV += " AND b.LABEL_VALUE =:LABEL_VALUE  AND b.LABEL_TYPE='4' ";
                for(AppLabelManage l:ls) {
                    if(l.getLabelValue().equals("2")){//建档立卡贫困人口
                        map.put("LABEL_VALUE",l.getLabelValue());
                        jdCount += sysDao.getServiceReadDo().findCount(sqlJjType, map);
                    }else if(l.getLabelValue().equals("3")){//低保户
                        map.put("LABEL_VALUE",l.getLabelValue());
                        dbCount += sysDao.getServiceReadDo().findCount(sqlJjType, map);
                    }else if(l.getLabelValue().equals("4")){//特困户（五保户）
                        map.put("LABEL_VALUE",l.getLabelValue());
                        tkCount += sysDao.getServiceReadDo().findCount(sqlJjType, map);
                    }else if(l.getLabelValue().equals("5")){//计生特殊家庭
                        map.put("LABEL_VALUE",l.getLabelValue());
                        jsCount += sysDao.getServiceReadDo().findCount(sqlJjType, map);
                    }
                }
            }*/
        }


        //当前查询 底下各机构 签约率 福州
        double fzqyl = 0;
        if(areaSignTop>0){
            fzqyl = MyMathUtil.div(Double.valueOf(yqy),Double.valueOf(areaSignTop),4)*100;//各社区、区县签约率
        }
        //续签率
        double xql = 0;
        if(totalRenew>0){
            xql = MyMathUtil.div(Double.valueOf(renew),Double.valueOf(totalRenew),4)*100;//续签率
        }
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
            zdrqwcl = MyMathUtil.div(Double.valueOf(zdrqyqy),Double.valueOf(areaFoucs),6)*100;//重点人群签约率
            zdrqwcl = MyMathUtil.round(zdrqwcl,2);
        }
        double zdrqmbwcl = 0;
        if(areaFoucsTarget >0){
            zdrqmbwcl = MyMathUtil.div(Double.valueOf(zdrqyqy),Double.valueOf(areaFoucsTarget),6)*100;//重点人群目标率
            zdrqmbwcl = MyMathUtil.round(zdrqmbwcl,2);
        }
        //人口经济性质签约率
        double economicRate =0;
        if(areaEconomic>0){
            economicRate = MyMathUtil.div(Double.valueOf(economic),Double.valueOf(areaEconomic),6)*100;//人口性质签约率
            economicRate = MyMathUtil.round(economicRate,2);
        }
        //人口性质目标率
        double economicTargetRate = 0;
        if(areaEconomicTarget>0){
            economicTargetRate = MyMathUtil.div(Double.valueOf(economic),Double.valueOf(areaEconomicTarget),4)*100;//人口性质目标率
        }
        //未签约数
        int wqys = (int) MyMathUtil.sub(Double.valueOf(areaReg),Double.valueOf(yqy));
        if(hosp != null){
            returnMap.put("hospId",hosp.getId());//区域编码
            returnMap.put("hospName",hosp.getHospName());//名称
            CdAddress cdAddress = this.sysDao.getCdAddressDao().findByCacheCode(hosp.getHospAreaCode());
            if(cdAddress != null ) {
                returnMap.put("upWebAreaCode", cdAddress.getPid());
                returnMap.put("hospLevel",cdAddress.getLevel());
            }
            returnMap.put("areaCode",null);//区域编码
            returnMap.put("areaName",null);//区域名称
        }else{
            returnMap.put("areaCode",address.getCtcode());//区域编码
            returnMap.put("areaName",address.getAreaSname());//区域名称
            returnMap.put("upWebAreaCode",address.getPid());
            returnMap.put("hospLevel",address.getLevel());
            returnMap.put("hospId",null);//区域编码
            returnMap.put("hospName",null);//名称
        }
        ycqy = yqy -economic;
        returnMap.put("areaReg",areaReg);//户籍人数
        returnMap.put("areaRegTarget",areaRegTarget);//户籍目标数
        returnMap.put("yqy",yqy);//签约数
        returnMap.put("ycqy",ycqy);//有偿签约数
        returnMap.put("appyqy",appyqy);//app签约数
        returnMap.put("webyqy",webyqy);//web签约数
        returnMap.put("wechatyqy",wechatyqy);//wechat签约数
        returnMap.put("wqys",wqys);//签约数
        returnMap.put("qywcl",String.valueOf(MyMathUtil.round(qywcl,2)));//签约率
        returnMap.put("mbwcl",String.valueOf(MyMathUtil.round(mbwcl,2)));//目标率
        returnMap.put("areaFoucs",areaFoucs);//重点人群数
        returnMap.put("zdrqyqy",zdrqyqy);//重点人群签约数
        returnMap.put("areaFoucsTarget",areaFoucsTarget);//重点人群目标数
        returnMap.put("zdrqwcl",String.valueOf(MyMathUtil.round(zdrqwcl,2)));//重点人群签约率
        returnMap.put("zdrqmbwcl",String.valueOf(MyMathUtil.round(zdrqmbwcl,2)));//重点人群目标率
        returnMap.put("xql",String.valueOf(MyMathUtil.round(xql,2)));//续签率
        returnMap.put("xqs",renew);//续签数
        returnMap.put("synqys",totalRenew);//上一年签约数
        returnMap.put("economic",economic);//人口经济性质签约数
        returnMap.put("economicRate",economicRate);//人口经济性质签约率
        returnMap.put("economicTargetRate",economicTargetRate);//人口经济性质目标率
        returnMap.put("areaEconomic",areaEconomic);//人口经济性质人口数
        returnMap.put("areaEconomicTarget",areaEconomicTarget);//人口经济性质目标数
        returnMap.put("fzqyl",fzqyl);// 各社区，区县签约率
        returnMap.put("aioyqy",aioyqy);//一体机签约数
     /*   returnSignCountMap.put("web",webyqy);
        returnSignCountMap.put("app",appyqy);
        returnMap.put("signTypeCount",returnSignCountMap);*/
        return returnMap;
    }


    /**
     * 签约量统计（团队）
     * @param team
     * @return
     */
    public Map<String,Object> getSianCountTeam(AppTeam team,String startDate,String endDate) throws Exception{
        Map<String, Object> hmap = new HashMap<String, Object>();
        Map<String,Object> map = new HashMap<String,Object>();
        Map<String,Object> returnMap = new HashMap<String,Object>();
        Map<String,Object> returnSignCountMap = new HashMap<String,Object>();
        Calendar calendar = Calendar.getInstance();
        int nowYear = calendar.get(Calendar.YEAR);
        int nowMonth = calendar.get(Calendar.MONTH) + 1;
        int now = Integer.parseInt(String.valueOf(nowYear)+ String.valueOf(nowMonth));
        String[] year = endDate.split("-");
        int endYear = Integer.parseInt(year[0]);
        int endMonth = Integer.parseInt(year[1]);
        int end = Integer.parseInt(String.valueOf(endYear)+ String.valueOf(endMonth));
        int yqy = 0;
        int zdrqyqy = 0;
        int webyqy = 0;//web已签约
        int appyqy= 0;//app已签约
        int wechatyqy = 0;//微信已签约
        int renew = 0;
        int totalRenew = 0;
        int economic = 0;//人口经济性质签约数
        int areaSignTop = 0; //各团队 签约率
        double mul = 0;
        int intmul = 0;
        String hsql = " SELECT d.* from app_team t INNER JOIN app_hosp_dept d on t.TEAM_HOSP_ID = d.ID where t.ID =:teamid " ;
//                "AND t.TEAM_DEL_STATE = '0'  ";
        hmap.put("teamid",team.getId());
        List<AppHospDept> hosp = this.sysDao.getServiceDo().findSqlMap(hsql,hmap,AppHospDept.class);
        //CdAddressPeople cdAdd = sysDao.getCdAddressPeopleDao().findByCacheCode(hosp.get(0).getHospAreaCode(), ExtendDate.getYYYY(Calendar.getInstance()));
        List<CdAddressPeople> lsPeople = this.sysDao.getCdAddressPeopleDao().findByCacheList(hosp.get(0).getHospAreaCode(),year[0]);
        if(lsPeople != null && lsPeople.size()>0){
            if("0".equals(lsPeople.get(0).getAreaDisSignWay())){ //0医生 1团队
                Integer ger =sysDao.getAppTeamMemberDao().findTeamPeopleCount(team.getId());
                mul = MyMathUtil.mul(Double.valueOf(lsPeople.get(0).getAreaSignTop()),Double.valueOf(ger));// 乘法
            }else{
                mul =Double.valueOf(lsPeople.get(0).getAreaSignTop());
            }
            DecimalFormat df = new DecimalFormat("######0"); //四色五入转换成整数
            intmul = Integer.parseInt(df.format(mul));
            areaSignTop = Integer.parseInt(StringUtils.isNotBlank(String.valueOf(mul)) ? String.valueOf(intmul):"0");
        }
//        map.put("STARTDATE", ExtendDate.getFirstDayOfMonth(startDate)+" 00:00:00");
//        map.put("ENDDATE",ExtendDate.getLastDayOfMonth(endDate)+" 23:59:59");
//        String sqlDate = " AND t.SIGN_FROM_DATE >= :STARTDATE AND t.SIGN_FROM_DATE <= :ENDDATE ";
//        String sqlYqy = "SELECT count(1) FROM APP_SIGN_FORM t where 1=1 AND t.SIGN_STATE = :SIGN_STATE "+sqlDate;
//        String sqlZdrqqy = "SELECT count(1) FROM APP_SIGN_FORM t LEFT JOIN APP_LABEL_GROUP b ON t.ID = b.LABEL_SIGN_ID where 1=1 and b.LABEL_TYPE=3 AND t.SIGN_STATE = :SIGN_STATE AND b.LABEL_VALUE NOT IN (:SIGN_PERS_GROUP) "+sqlDate;
//        String state = SignFormType.YQY.getValue();//状态
//        String[] fwState ={ResidentMangeType.PTRQ.getValue(),ResidentMangeType.WEIZHI.getValue()};
//        map.put("SIGN_STATE",state);
//        map.put("SIGN_PERS_GROUP",fwState);
//        map.put("TEAM_ID",team.getId());
//        sqlYqy += " AND t.SIGN_TEAM_ID = :TEAM_ID ";
//        yqy = this.sysDao.getServiceReadDo().findCount(sqlYqy,map);//已签约
//        sqlZdrqqy += " AND t.SIGN_TEAM_ID = :TEAM_ID ";
//        zdrqyqy = this.sysDao.getServiceReadDo().findCount(sqlZdrqqy,map);//重點人群已簽約
        map.put("STARTDATE",startDate);
        map.put("ENDDATE",endDate);
        String sqlDate = " AND t.MANAGE_YEAR_MONTH >= :STARTDATE AND t.MANAGE_YEAR_MONTH <= :ENDDATE ";
        String sql = "SELECT SUM(t.MANAGE_SIGN_COUNT) signCount," +
                "SUM(t.MANAGE_KEY_SIGN_COUNT) keySingCount," +
                "SUM(t.MANAGE_SIGN_WEB_COUNT) signWebCount," +
                "SUM(t.MANAGE_SIGN_APP_COUNT) singAppCount," +
                "SUM(t.MANAGE_SIGN_WECHAT_COUNT) singWechatCount,"+
                "SUM(t.MANAGE_RENEW) renew," +
                "SUM(t.MANAGE_ECONOMIC_SIGN_COUNT) manageEconomicSignCount," +
                "SUM(t.MANAGE_LOW_FAMILY_COUNT) manageLowFamilyCount," +
                "SUM(t.MANAGE_DESTITUTE_FAMILY_COUNT) manageDestituteFamilyCount," +
                "SUM(t.MANAGE_SPECIAL_PLAN_FAMILY_COUNT) manageSpecialPlanFamilyCount," +
                "SUM(t.MANAGE_POOR_FAMILY_COUNT) managePoorFamilyCount, " +
                "SUM(t.MANAGE_TOTAL_RENEW) totalRenew "+
                " FROM APP_MANAGE_COUNT t where 1=1 "+sqlDate;
        map.put("TEAM_ID",team.getId());
        sql += " AND t.MANAGE_TEAM_ID = :TEAM_ID ";

        List<Map> maps = this.sysDao.getServiceReadDo().findSqlMap(sql,map);
        if(maps != null && maps.size() >0){
            if(maps.get(0).get("signCount") != null) {
                yqy = (int) Double.parseDouble(maps.get(0).get("signCount").toString());//已签约
            }
            if(maps.get(0).get("keySingCount") != null) {
                zdrqyqy = (int) Double.parseDouble(maps.get(0).get("keySingCount").toString());//重點人群已簽約
            }
            if(maps.get(0).get("signWebCount") != null){
                webyqy = (int) Double.parseDouble(maps.get(0).get("signWebCount").toString());//web已签约
            }
            if(maps.get(0).get("singAppCount") != null){
                appyqy = (int) Double.parseDouble(maps.get(0).get("singAppCount").toString());//app已签约
            }
            if(maps.get(0).get("singWechatCount") != null) {
                wechatyqy = (int) Double.parseDouble(maps.get(0).get("singWechatCount").toString());//微信已签约
            }
            if(maps.get(0).get("renew") !=null){
                renew = (int)Double.parseDouble(maps.get(0).get("renew").toString());//续签数
            }
            if(maps.get(0).get("totalRenew")!=null){
                totalRenew = (int)Double.parseDouble(maps.get(0).get("totalRenew").toString());//总签约数
            }
            if(maps.get(0).get("manageEconomicSignCount")!=null){
                economic+=(int)Double.parseDouble(maps.get(0).get("manageEconomicSignCount").toString());//经济类型
            }
//            if(maps.get(0).get("manageLowFamilyCount")!=null){
//                economic+=(int)Double.parseDouble(maps.get(0).get("manageLowFamilyCount").toString());//低保户
//            }
//            if(maps.get(0).get("manageDestituteFamilyCount")!=null){
//                economic+=(int)Double.parseDouble(maps.get(0).get("manageDestituteFamilyCount").toString());//特困户（五保户）
//            }
//            if(maps.get(0).get("manageSpecialPlanFamilyCount")!=null){
//                economic+=(int)Double.parseDouble(maps.get(0).get("manageSpecialPlanFamilyCount").toString());//计生特殊家庭
//            }
//            if(maps.get(0).get("managePoorFamilyCount")!=null){
//                economic+=(int)Double.parseDouble(maps.get(0).get("managePoorFamilyCount").toString());//建档立卡贫困人口
//            }
        }

        //如果结束时间大于等于当前时间
        if(end >= now){
//            List<CdCode> ls = this.sysDao.getCodeDao().findGroupList(CodeGroupConstrats.GROUP_JJLX, CommonEnable.QIYONG.getValue());
//            List<AppLabelManage> ls = this.sysDao.getAppLabelManageDao().findByType(LabelManageType.JJLX.getValue());
            map.put("STARTDATE", ExtendDate.getYMD(Calendar.getInstance())+" 00:00:00");
            map.put("ENDDATE",ExtendDate.getYMD(Calendar.getInstance())+" 23:59:59");
            sqlDate = " AND t.SIGN_FROM_DATE >= :STARTDATE AND t.SIGN_FROM_DATE <= :ENDDATE ";
            //签约数
            String sqlYqy = "SELECT count(1) FROM APP_SIGN_FORM t where 1=1 AND t.SIGN_STATE IN (:SIGN_STATE) "+sqlDate;
            //重点人群数
            String sqlZdrqqy = "SELECT t.* FROM APP_SIGN_FORM t LEFT JOIN APP_LABEL_GROUP b ON t.ID = b.LABEL_SIGN_ID where 1=1 AND t.SIGN_STATE IN (:SIGN_STATE) AND b.LABEL_VALUE NOT IN (:SIGN_PERS_GROUP) AND b.LABEL_TYPE='3'  "+sqlDate;
//            //重点人群数
//            String sqlZdrqqy = "SELECT count(1) FROM APP_SIGN_FORM t LEFT JOIN APP_LABEL_GROUP b ON t.ID = b.LABEL_SIGN_ID where 1=1 and b.LABEL_TYPE='3' AND t.SIGN_STATE IN (:SIGN_STATE) AND b.LABEL_VALUE NOT IN (:SIGN_PERS_GROUP) "+sqlDate;
            //web签约数
            String sqlWebYqy = "SELECT count(1) FROM APP_SIGN_FORM t where 1=1 AND t.SIGN_STATE IN (:SIGN_STATE) AND t.UP_HPIS = :WEB_STATE  "+sqlDate;
            //app签约数
            String sqlAppYqy = "SELECT count(1) FROM APP_SIGN_FORM t where 1=1 AND t.SIGN_STATE IN (:SIGN_STATE) AND t.UP_HPIS = :APP_STATE "+sqlDate;
            //微信签约数
            String sqlWechatYqy = "SELECT count(1) FROM APP_SIGN_FORM t where 1=1 AND t.SIGN_STATE IN (:SIGN_STATE) AND t.UP_HPIS = :WECHAT_STATE "+sqlDate;
            //续签数
            String sqlRenew = "SELECT count(1) FROM APP_SIGN_FORM t where 1=1 AND t.SIGN_GOTO_SIGN_STATE = :SIGN_GOTO_SIGN_STATE "+sqlDate;
            //经济类型数
//            String sqlJjType = "SELECT COUNT(1) FROM APP_SIGN_FORM t  LEFT JOIN APP_LABEL_ECONOMICS b ON t.ID = b.LABEL_SIGN_ID WHERE 1=1 AND  t.SIGN_STATE IN (:SIGN_STATE) "+sqlDate;
            String sqlJjType = "SELECT t.* FROM APP_SIGN_FORM t LEFT JOIN APP_LABEL_ECONOMICS b ON t.ID = b.LABEL_SIGN_ID where 1=1 AND t.SIGN_STATE IN (:SIGN_STATE) AND b.LABEL_VALUE NOT IN (:SIGN_JJ_TYPE) AND b.LABEL_TYPE='4'  "+sqlDate;

            String gotoSignState = SignFormType.YQY.getValue();//状态
            String[] state = {SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};//状态
            String[] fwState ={ResidentMangeType.PTRQ.getValue(),ResidentMangeType.WEIZHI.getValue()};
//            String[] jjState = {EconomicType.YBRK.getValue(),EconomicType.JDLKPKRK.getValue(),EconomicType.DBH.getValue(),EconomicType.TKH.getValue(),EconomicType.JSTSJT.getValue()};
            map.put("SIGN_JJ_TYPE",EconomicType.YBRK.getValue());
            map.put("SIGN_GOTO_SIGN_STATE",gotoSignState);
            map.put("SIGN_STATE",state);
            map.put("SIGN_PERS_GROUP",fwState);
            map.put("TEAM_ID",team.getId());
            sqlYqy += " AND t.SIGN_TEAM_ID = :TEAM_ID ";
            yqy += this.sysDao.getServiceReadDo().findCount(sqlYqy,map);//已签约
            sqlWebYqy += " AND t.SIGN_TEAM_ID = :TEAM_ID ";
            webyqy += this.sysDao.getServiceReadDo().findCount(sqlWebYqy,map);//web已签约
            sqlAppYqy += " AND t.SIGN_TEAM_ID = :TEAM_ID ";
            appyqy += this.sysDao.getServiceReadDo().findCount(sqlAppYqy,map);//app已签约
            sqlWechatYqy += " AND t.SIGN_TEAM_ID = :TEAM_ID ";
            wechatyqy += this.sysDao.getServiceReadDo().findCount(sqlWechatYqy,map);//微信已签约
            sqlZdrqqy += " AND t.SIGN_TEAM_ID = :TEAM_ID ";
            String sqlZdrq = "SELECT COUNT(1) FROM ( "+sqlZdrqqy +" GROUP BY t.SIGN_PATIENT_ID) c";
            zdrqyqy += this.sysDao.getServiceReadDo().findCount(sqlZdrq,map);//重點人群已簽約
            sqlJjType += " AND t.SIGN_TEAM_ID = :TEAM_ID ";
            String sqlJjrq = "SELECT COUNT(1) FROM ( "+sqlJjType +" GROUP BY t.SIGN_PATIENT_ID) c";
            economic += this.sysDao.getServiceReadDo().findCount(sqlJjrq,map);//重點人群经济已簽約
            sqlRenew += " AND t.SIGN_TEAM_ID = :TEAM_ID ";
            renew += this.sysDao.getServiceReadDo().findCount(sqlRenew,map);//续签
        }


        //续签率
        double xql = 0;
        if(totalRenew>0){
            xql = MyMathUtil.div(Double.valueOf(renew),Double.valueOf(totalRenew),4)*100;//续签率
        }
        // 各团队签约率
        double fzqyl = 0;
        if(areaSignTop>0){
            fzqyl = MyMathUtil.div(Double.valueOf(yqy),Double.valueOf(areaSignTop),4)*100;//签约率
        }
        returnMap.put("xql",xql);//续签率
        returnMap.put("yqy",yqy);//签约数
        returnMap.put("appyqy",appyqy);//app签约数
        returnMap.put("webyqy",webyqy);//web签约数
        returnMap.put("wechatyqy",wechatyqy);//wechat签约数
        returnMap.put("zdrqyqy",zdrqyqy);//重点人群签约数
        returnMap.put("teamId",team.getId());//团队Id主键
        returnMap.put("teamName",team.getTeamName());//团队名称
        returnMap.put("teamDrName",team.getTeamDrName());//团队队长名称
        returnMap.put("xqs",renew);//续签数
        returnMap.put("synqys",totalRenew);//上一年签约数
        returnMap.put("economic",economic);//人口经济性质签约数
        returnMap.put("fzqyl",fzqyl);
        returnSignCountMap.put("web",webyqy);
        returnSignCountMap.put("app",appyqy);
        returnMap.put("signTypeCount",returnSignCountMap);
        return returnMap;
    }

    /**
     * 签约转取统计
     * @param qvo
     * @return
     */
    @Override
    public Map<String, Object> findSignAnalysisListInitialise(ResidentVo qvo) throws Exception {
        AppHospDept hosp = null;
        AppTeam team = null;
        AppDrUser drUser = null;
        if(StringUtils.isNotBlank(qvo.getHospId())){
            hosp = (AppHospDept)sysDao.getServiceReadDo().find(AppHospDept.class,qvo.getHospId());
        }
        if(StringUtils.isNotBlank(qvo.getTeamId())){
            team = (AppTeam) sysDao.getServiceReadDo().find(AppTeam.class,qvo.getTeamId());
        }
        if(StringUtils.isNotBlank(qvo.getDrId())){
            drUser = (AppDrUser) sysDao.getServiceReadDo().find(AppDrUser.class,qvo.getDrId());
        }
        Map<String, Object> map = getSianCountInitialise(hosp,team,drUser,qvo.getYearStart(),qvo.getYearEnd());
        return map;
    }

    /**
     * 签约量统计（省，市,社区）
     * @return
     */
    public Map<String,Object> getSianCountInitialise(AppHospDept hosp,AppTeam team,AppDrUser drUser,String startDate,String endDate) throws Exception{

        Map<String,Object> map = new HashMap<String,Object>();
        Map<String,Object> returnMap = new HashMap<String,Object>();
        int yqy = 0;
        int zdrqyqy = 0;
        int webyqy = 0;//web已签约
        int appyqy= 0;//app已签约
        int wechatyqy = 0;//微信已签约
        int aioyqy = 0;//一体机已签约
        int posyqy = 0;//pos机已签约
        map.put("STARTDATE", ExtendDate.getFirstDayOfMonth(startDate)+" 00:00:00");
        map.put("ENDDATE",ExtendDate.getLastDayOfMonth(endDate)+" 23:59:59");
//        map.put("ENDDATE","2018-10-18 23:59:59");
        String sqlDate = " AND t.SIGN_FROM_DATE >= :STARTDATE AND t.SIGN_FROM_DATE <= :ENDDATE ";
        String sqlYqy = "SELECT count(1) FROM APP_SIGN_FORM t where 1=1 AND t.SIGN_STATE IN (:SIGN_STATE) "+sqlDate;
        String sqlWebYqy = "SELECT count(1) FROM APP_SIGN_FORM t where 1=1 AND t.SIGN_STATE IN (:SIGN_STATE) AND t.UP_HPIS = :WEB_STATE  "+sqlDate;
        String sqlAppYqy = "SELECT count(1) FROM APP_SIGN_FORM t where 1=1 AND t.SIGN_STATE IN (:SIGN_STATE) AND t.UP_HPIS = :APP_STATE "+sqlDate;
        String sqlWechatYqy = "SELECT count(1) FROM APP_SIGN_FORM t where 1=1 AND t.SIGN_STATE IN (:SIGN_STATE) AND t.UP_HPIS = :WECHAT_STATE "+sqlDate;
        String sqlZdrqqy = "SELECT t.* FROM APP_SIGN_FORM t LEFT JOIN APP_LABEL_GROUP b ON t.ID = b.LABEL_SIGN_ID where 1=1 and b.LABEL_TYPE='3' AND b.LABEL_VALUE NOT IN (:SIGN_PERS_GROUP) AND t.SIGN_STATE IN (:SIGN_STATE) "+sqlDate ;
        String sqlAioYqy = "SELECT count(1) FROM APP_SIGN_FORM t where 1=1 AND t.SIGN_STATE IN (:SIGN_STATE) AND t.UP_HPIS = :AIO_STATE "+sqlDate;
        String sqlPosYqy = "SELECT count(1) FROM APP_SIGN_FORM t where 1=1 AND t.SIGN_STATE IN (:SIGN_STATE) AND t.UP_HPIS = :POS_STATE "+sqlDate;
        String[] fwState ={ResidentMangeType.PTRQ.getValue(),ResidentMangeType.WEIZHI.getValue()};
//        String[] qys = {SignFormType.YQY.getValue(),SignFormType.YJY.getValue()};
        String[] qys = {SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
        map.put("SIGN_STATE", qys);

        map.put("yjy",SignFormType.YJY.getValue());
        map.put("tsnr","签约到期，自动解约");
        boolean flag = ExtendDateUtil.getIsDateSatisfied(endDate);
        String sqlYqyl = "SELECT count(1) FROM APP_SIGN_FORM t where 1=1 AND t.SIGN_STATE =:yjy AND t.SIGN_URRENDER_REASON =:tsnr "+sqlDate;
        String sqlWebYqyl = "SELECT count(1) FROM APP_SIGN_FORM t where 1=1 AND t.SIGN_STATE =:yjy AND t.SIGN_URRENDER_REASON =:tsnr AND t.UP_HPIS = :WEB_STATE  "+sqlDate;
        String sqlAppYqyl = "SELECT count(1) FROM APP_SIGN_FORM t where 1=1 AND t.SIGN_STATE =:yjy AND t.SIGN_URRENDER_REASON =:tsnr AND t.UP_HPIS = :APP_STATE "+sqlDate;
        String sqlWechatYqyl = "SELECT count(1) FROM APP_SIGN_FORM t where 1=1 AND t.SIGN_STATE =:yjy AND t.SIGN_URRENDER_REASON =:tsnr AND t.UP_HPIS = :WECHAT_STATE "+sqlDate;
        String sqlZdrqqyl = "SELECT t.* FROM APP_SIGN_FORM t LEFT JOIN APP_LABEL_GROUP b ON t.ID = b.LABEL_SIGN_ID where 1=1 and b.LABEL_TYPE='3' AND b.LABEL_VALUE NOT IN (:SIGN_PERS_GROUP) AND t.SIGN_STATE =:yjy AND t.SIGN_URRENDER_REASON =:tsnr "+sqlDate ;
        String sqlAioYqyl = "SELECT count(1) FROM APP_SIGN_FORM t where 1=1 AND t.SIGN_STATE =:yjy AND t.SIGN_URRENDER_REASON =:tsnr AND t.UP_HPIS = :AIO_STATE "+sqlDate;
        String sqlPosYqyl = "SELECT count(1) FROM APP_SIGN_FORM t where 1=1 AND t.SIGN_STATE =:yjy AND t.SIGN_URRENDER_REASON =:tsnr AND t.UP_HPIS = :POS_STATE "+sqlDate;

//        String qys = SignFormType.YQY.getValue();
//        map.put("SIGN_STATE", qys);
        map.put("SIGN_PERS_GROUP",fwState);
        map.put("WEB_STATE",SignFormType.WEBSTATE.getValue());
        map.put("APP_STATE",SignFormType.APPSTATE.getValue());
        map.put("WECHAT_STATE",SignFormType.WECHATSTATE.getValue());
        map.put("AIO_STATE",SignFormType.YITIJISTATE.getValue());
        map.put("POS_STATE",SignFormType.POSSTATE.getValue());
        if(hosp != null){
            map.put("HOSP_ID",hosp.getId());
            sqlYqy += " AND t.SIGN_HOSP_ID = :HOSP_ID ";
            sqlWebYqy += " AND t.SIGN_HOSP_ID = :HOSP_ID ";
            sqlAppYqy += " AND t.SIGN_HOSP_ID = :HOSP_ID ";
            sqlWechatYqy += " AND t.SIGN_HOSP_ID = :HOSP_ID ";
            sqlZdrqqy += " AND t.SIGN_HOSP_ID = :HOSP_ID ";
            sqlAioYqy += " AND t.SIGN_HOSP_ID = :HOSP_ID ";
            sqlPosYqy += " AND t.SIGN_HOSP_ID = :HOSP_ID ";

            sqlYqyl += " AND t.SIGN_HOSP_ID = :HOSP_ID ";
            sqlWebYqyl += " AND t.SIGN_HOSP_ID = :HOSP_ID ";
            sqlAppYqyl += " AND t.SIGN_HOSP_ID = :HOSP_ID ";
            sqlWechatYqyl += " AND t.SIGN_HOSP_ID = :HOSP_ID ";
            sqlZdrqqyl += " AND t.SIGN_HOSP_ID = :HOSP_ID ";
            sqlAioYqyl += " AND t.SIGN_HOSP_ID = :HOSP_ID ";
            sqlPosYqyl += " AND t.SIGN_HOSP_ID = :HOSP_ID ";
        }

        if(team != null){
            map.put("SIGN_TEAM_ID",team.getId());
            sqlYqy += " AND t.SIGN_TEAM_ID = :SIGN_TEAM_ID ";
            sqlWebYqy += " AND t.SIGN_TEAM_ID = :SIGN_TEAM_ID ";
            sqlAppYqy += " AND t.SIGN_TEAM_ID = :SIGN_TEAM_ID ";
            sqlWechatYqy += " AND t.SIGN_TEAM_ID = :SIGN_TEAM_ID ";
            sqlZdrqqy += " AND t.SIGN_TEAM_ID = :SIGN_TEAM_ID ";
            sqlAioYqy += " AND t.SIGN_TEAM_ID = :SIGN_TEAM_ID ";
            sqlPosYqy += " AND t.SIGN_TEAM_ID = :SIGN_TEAM_ID ";

            sqlYqyl += " AND t.SIGN_TEAM_ID = :SIGN_TEAM_ID ";
            sqlWebYqyl += " AND t.SIGN_TEAM_ID = :SIGN_TEAM_ID ";
            sqlAppYqyl += " AND t.SIGN_TEAM_ID = :SIGN_TEAM_ID ";
            sqlWechatYqyl += " AND t.SIGN_TEAM_ID = :SIGN_TEAM_ID ";
            sqlZdrqqyl += " AND t.SIGN_TEAM_ID = :SIGN_TEAM_ID ";
            sqlAioYqyl += " AND t.SIGN_TEAM_ID = :SIGN_TEAM_ID ";
            sqlPosYqyl += " AND t.SIGN_TEAM_ID = :SIGN_TEAM_ID ";
        }

        if(drUser != null){
            map.put("SIGN_DR_ID",drUser.getId());
            sqlYqy += " AND t.SIGN_DR_ID = :SIGN_DR_ID ";
            sqlWebYqy += " AND t.SIGN_DR_ID = :SIGN_DR_ID ";
            sqlAppYqy += " AND t.SIGN_DR_ID = :SIGN_DR_ID ";
            sqlWechatYqy += " AND t.SIGN_DR_ID = :SIGN_DR_ID ";
            sqlZdrqqy += " AND t.SIGN_DR_ID = :SIGN_DR_ID ";
            sqlAioYqy += " AND t.SIGN_DR_ID = :SIGN_DR_ID ";
            sqlPosYqy += " AND t.SIGN_DR_ID = :SIGN_DR_ID ";

            sqlYqyl += " AND t.SIGN_DR_ID = :SIGN_DR_ID ";
            sqlWebYqyl += " AND t.SIGN_DR_ID = :SIGN_DR_ID ";
            sqlAppYqyl += " AND t.SIGN_DR_ID = :SIGN_DR_ID ";
            sqlWechatYqyl += " AND t.SIGN_DR_ID = :SIGN_DR_ID ";
            sqlZdrqqyl += " AND t.SIGN_DR_ID = :SIGN_DR_ID ";
            sqlAioYqyl += " AND t.SIGN_DR_ID = :SIGN_DR_ID ";
            sqlPosYqyl += " AND t.SIGN_DR_ID = :SIGN_DR_ID ";
        }
        yqy = this.sysDao.getServiceReadDo().findCount(sqlYqy,map);//已签约
        String sql = " SELECT count(1) FROM ("+sqlZdrqqy+"  GROUP BY t.SIGN_PATIENT_ID ) c";
        zdrqyqy = this.sysDao.getServiceReadDo().findCount(sql,map);//重點人群已簽約
        appyqy = this.sysDao.getServiceReadDo().findCount(sqlAppYqy,map);//APP已簽約
        webyqy = this.sysDao.getServiceReadDo().findCount(sqlWebYqy,map);//WEB已簽約
        wechatyqy = this.sysDao.getServiceReadDo().findCount(sqlWechatYqy,map);//微信已簽約
        aioyqy = this.sysDao.getServiceReadDo().findCount(sqlAioYqy,map);//一体机已签约
        posyqy = this.sysDao.getServiceReadDo().findCount(sqlPosYqy,map);//pos机已签约

        if(flag){
            yqy += this.sysDao.getServiceReadDo().findCount(sqlYqyl,map);//已签约
            String sqll = " SELECT count(1) FROM ("+sqlZdrqqyl+"  GROUP BY t.SIGN_PATIENT_ID ) c";
            zdrqyqy += this.sysDao.getServiceReadDo().findCount(sqll,map);//重點人群已簽約
            appyqy += this.sysDao.getServiceReadDo().findCount(sqlAppYqyl,map);//APP已簽約
            webyqy += this.sysDao.getServiceReadDo().findCount(sqlWebYqyl,map);//WEB已簽約
            wechatyqy += this.sysDao.getServiceReadDo().findCount(sqlWechatYqyl,map);//微信已簽約
            aioyqy += this.sysDao.getServiceReadDo().findCount(sqlAioYqyl,map);//一体机已签约
            posyqy += this.sysDao.getServiceReadDo().findCount(sqlPosYqyl,map);//pos机已签约
        }

        returnMap.put("manageSignCount",yqy);//签约数
        returnMap.put("manageKeySignCount",zdrqyqy);//重点人群签约数
        returnMap.put("manageSignWebCount",webyqy);//WEB已簽約
        returnMap.put("manageSignAppCount",appyqy);//APP已簽約
        returnMap.put("manageSignWechatCount",wechatyqy);//微信已簽約
        returnMap.put("manageSignAioCount",aioyqy);//一体机已签约
        returnMap.put("manageSignPosCount",posyqy);//pos机已签约
        return returnMap;
    }


    /**
     * 签约转取统计(建档立卡)
     * @param qvo
     * @return
     */
    public Map<String,Object> findSignAnalysisArchivingListInitialise(ResidentVo qvo) throws Exception{
//        AppHospDept hosp = null;
//        AppTeam team = null;
//        AppDrUser drUser = null;
//        if(StringUtils.isNotBlank(qvo.getHospId())){
//            hosp = (AppHospDept)sysDao.getServiceReadDo().find(AppHospDept.class,qvo.getHospId());
//        }
//        if(StringUtils.isNotBlank(qvo.getTeamId())){
//            team = (AppTeam) sysDao.getServiceReadDo().find(AppTeam.class,qvo.getTeamId());
//        }
//        if(StringUtils.isNotBlank(qvo.getDrId())){
//            drUser = (AppDrUser) sysDao.getServiceReadDo().find(AppDrUser.class,qvo.getDrId());
//        }
        Map<String, Object> map = getSianCountArchivingInitialise(qvo.getHospId(),qvo.getTeamId(),qvo.getDrId(),qvo.getYearStart(),qvo.getYearEnd(),qvo.getJdAreaCode(),qvo.getJdSourceType());
        return map;
    }


    /**
     * 签约量统计（省，市,社区）建档立卡
     * @return
     */
    public Map<String,Object> getSianCountArchivingInitialise(String hosp,String team,String drUser,String startDate,String endDate,String jdAreaCode,String sourceType) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        Map<String,Object> returnMap = new HashMap<String,Object>();
        List<AppLabelManage> ls = this.sysDao.getAppLabelManageDao().findByType("3");
        if(ls!=null && !ls.isEmpty()) {
            int yqy = 0;
            int key = 0;
            int js = 0;
            map.put("STARTDATE", ExtendDate.getFirstDayOfMonth(startDate)+" 00:00:00");
//            map.put("ENDDATE",ExtendDate.getLastDayOfMonth(endDate)+" 23:59:59");
            map.put("ENDDATE",ExtendDate.getLastDayOfMonthNew(endDate)+" 23:59:59");
            map.put("SOURCE_TYPE",sourceType);
            map.put("LABEL_VALUE_ALL","2");
            map.put("jdAreaCode",jdAreaCode);
            String sqlDate = " AND t.SIGN_FROM_DATE >= :STARTDATE AND t.SIGN_FROM_DATE <= :ENDDATE  AND t.ADDR_RURAL_CODE=:jdAreaCode AND t.SOURCE_TYPE = :SOURCE_TYPE ";//
            String sqlDateJj = " AND t.SIGN_FROM_DATE >= :STARTDATE AND t.SIGN_FROM_DATE <= :ENDDATE AND t.ID in (SELECT s.SIGN_ID FROM APP_ARCHIVINGCARD_PEOPLE s WHERE s.SIGN_ID IS NOT NULL AND s.ADDR_RURAL_CODE=:jdAreaCode AND s.SOURCE_TYPE = :SOURCE_TYPE  )";//AND s.SOURCE_TYPE = :SOURCE_TYPE
            if(StringUtils.isNotBlank(hosp)){
                sqlDateJj = " AND t.SIGN_FROM_DATE >= :STARTDATE AND t.SIGN_FROM_DATE <= :ENDDATE AND t.ID in (SELECT s.SIGN_ID FROM APP_ARCHIVINGCARD_PEOPLE s WHERE s.SIGN_ID IS NOT NULL AND s.ADDR_RURAL_CODE=:jdAreaCode AND s.SOURCE_TYPE = :SOURCE_TYPE AND s.ADDR_HOSP_ID =:HOSP_ID  )";//AND s.SOURCE_TYPE = :SOURCE_TYPE
            }
            String sql = "SELECT t.* FROM app_archivingcard_people t INNER JOIN app_label_archiving b on t.ID = b.LABEL_ARCHIVING_ID WHERE 1=1 AND t.SIGN_STATE IS NOT NULL  "+sqlDate;
//            sql += " AND b.LABEL_TYPE = '4' AND b.LABEL_VALUE = :LABEL_VALUE_ALL ";
            String sqlGroup = "SELECT t.* FROM app_archivingcard_people t INNER JOIN app_label_archiving b on t.ID = b.LABEL_ARCHIVING_ID WHERE 1=1 AND t.SIGN_STATE IS NOT NULL  "+sqlDate;
//            sqlGroup += " AND c.LABEL_TYPE = '4' AND c.LABEL_VALUE = :LABEL_VALUE_ALL ";
            String sqlGroupJs = "SELECT t.* FROM APP_SIGN_FORM t LEFT JOIN APP_LABEL_GROUP b on t.ID=b.LABEL_SIGN_ID LEFT JOIN APP_LABEL_ECONOMICS c on t.ID = c.LABEL_SIGN_ID WHERE 1=1 AND t.SIGN_STATE IN (:SIGN_STATE) "+sqlDateJj;
            sqlGroupJs += " AND c.LABEL_TYPE = '4' AND c.LABEL_VALUE = '5' ";
            String[] fwState ={ResidentMangeType.PTRQ.getValue(),ResidentMangeType.WEIZHI.getValue()};
            String[] qys = {SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
            String[] jjtype = {"2","5"};
            map.put("LABEL_VALUE_ALLS",jjtype);
//            String[] qys = {SignFormType.YQY.getValue(),SignFormType.YUQY.getValue(),SignFormType.YJY.getValue()};
            map.put("SIGN_STATE", qys);
            if(StringUtils.isNotBlank(hosp)){
                map.put("HOSP_ID",hosp);
                sql += " AND t.ADDR_HOSP_ID = :HOSP_ID ";
                sqlGroup += " AND t.ADDR_HOSP_ID = :HOSP_ID ";
//                sqlGroupJs += " AND t.SIGN_HOSP_ID = :SIGN_HOSP_ID ";
            }

            if(StringUtils.isNotBlank(team)){
                map.put("SIGN_TEAM_ID",team);
                sql += " AND t.TEAM_ID = :SIGN_TEAM_ID ";
                sqlGroup += " AND t.TEAM_ID = :SIGN_TEAM_ID ";
                sqlGroupJs += " AND t.SIGN_TEAM_ID = :SIGN_TEAM_ID ";

            }

            if(StringUtils.isNotBlank(drUser)){
                map.put("SIGN_DR_ID",drUser);
                sql += " AND t.DR_ID = :SIGN_DR_ID ";
                sqlGroup += " AND t.DR_ID = :SIGN_TEAM_ID ";
                sqlGroupJs += " AND t.SIGN_TEAM_ID = :SIGN_TEAM_ID ";
            }
            String sqlAll = " SELECT count(1) FROM ("+sql+"  GROUP BY  b.LABEL_ARCHIVING_ID ) c";
            yqy = this.sysDao.getServiceReadDo().findCount(sqlAll,map);//已签约
            returnMap.put("manageSignCount",yqy);//签约数

            String sqlKeyGroup = sqlGroup + " AND b.LABEL_VALUE NOT IN (:LABEL_VALUE_NOT_IN) ";
            String sqlKeyGroupAll = " SELECT count(1) FROM ("+sqlKeyGroup+"  GROUP BY  b.LABEL_ARCHIVING_ID ) c";
            map.put("LABEL_VALUE_NOT_IN",fwState);
            key = this.sysDao.getServiceReadDo().findCount(sqlKeyGroupAll,map);//重点已签约
            returnMap.put("manageKeySignCount",key);//重点签约数

            String sqlGroupJsAll = " SELECT count(1) FROM ("+sqlGroupJs+"  GROUP BY t.SIGN_PATIENT_ID  ) c";
            js = this.sysDao.getServiceReadDo().findCount(sqlGroupJsAll,map);//计生
            returnMap.put("manageSpecialPlanFamilyCount",js);

            sqlGroup += " AND b.LABEL_VALUE=:SIGN_PERS_GROUP";
            String sqlGroupAll = " SELECT count(1) FROM ("+sqlGroup+"  GROUP BY  b.LABEL_ARCHIVING_ID ) c";
            for(AppLabelManage l:ls) {
                map.put("SIGN_PERS_GROUP",l.getLabelValue());
                int  count= sysDao.getServiceReadDo().findCount(sqlGroupAll, map);
                if(l.getLabelValue().equals("1")){//普通人群
                    returnMap.put("managePlainCount", count);
                }else if(l.getLabelValue().equals("2")){//儿童(0-6岁)
                    returnMap.put("manageChildCount", count);
                }else if(l.getLabelValue().equals("3")){//孕产妇
                    returnMap.put("manageMaternalCount", count);
                }else if(l.getLabelValue().equals("4")){//老年人
                    returnMap.put("manageOldPeopleCount", count);
                }else if(l.getLabelValue().equals("5")){//高血压
                    returnMap.put("manageBloodCount", count);
                }else if(l.getLabelValue().equals("6")){//糖尿病
                    returnMap.put("manageDiabetesCount", count);
                }else if(l.getLabelValue().equals("7")){//严重精神障碍
                    returnMap.put("managePsychosisCount", count);
                }else if(l.getLabelValue().equals("8")){//结核病
                    returnMap.put("managePhthisisCount", count);
                }else if(l.getLabelValue().equals("9")){//残疾人
                    returnMap.put("manageDisabledPeopleCount", count);
                }else if(l.getLabelValue().equals("99")){//未知
                    returnMap.put("manageServiceUnknownCount", count);
                }
            }
        }
        return  returnMap;
    }



    /**
     * 签约转取统计(建档立卡)
     * @param qvo
     * @return
     */
    public Map<String,Object> findSignAnalysisArchivingAllListInitialise(ResidentVo qvo) throws Exception{
        AppHospDept hosp = null;
        AppTeam team = null;
        AppDrUser drUser = null;
        CdAddress cdAddress = null;
        /*if(StringUtils.isNotBlank(qvo.getHospId())){
            hosp = (AppHospDept)sysDao.getServiceReadDo().find(AppHospDept.class,qvo.getHospId());
        }
        if(StringUtils.isNotBlank(qvo.getTeamId())){
            team = (AppTeam) sysDao.getServiceReadDo().find(AppTeam.class,qvo.getTeamId());
        }
        if(StringUtils.isNotBlank(qvo.getDrId())){
            drUser = (AppDrUser) sysDao.getServiceReadDo().find(AppDrUser.class,qvo.getDrId());
        }*/
        if(StringUtils.isNotBlank(qvo.getAreaId())){
            cdAddress = (CdAddress) sysDao.getServiceReadDo().find(CdAddress.class,qvo.getAreaId());
            if(cdAddress == null){
                CdAddressConfiguration cp = sysDao.getCdAddressDao().findByCodeJw(qvo.getAreaId());
                if(cp != null){
                    cdAddress = new CdAddress();
                    cdAddress.setId(cp.getAreaCodeJw());
                }
            }
        }
        Map<String, Object> map = getSianCountArchivingAllInitialise(cdAddress,qvo.getYearStart(),qvo.getYearEnd(),qvo.getJdSourceType(),qvo.getHospId());
        return map;
    }


    /**
     * 签约量统计（省，市,社区）建档立卡
     * @return
     */
    public Map<String,Object> getSianCountArchivingAllInitialise(CdAddress cdAddress,String startDate,String endDate,String sourceType,String addrHospId) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        Map<String,Object> returnMap = new HashMap<String,Object>();
        List<AppLabelManage> ls = this.sysDao.getAppLabelManageDao().findByType("3");
        if(ls!=null && !ls.isEmpty()) {
            int yqy = 0;
            int key = 0;
            int wyqy =0;
            map.put("STARTDATE", ExtendDate.getFirstDayOfMonth(startDate)+" 00:00:00");
//            map.put("ENDDATE",ExtendDate.getLastDayOfMonth(endDate)+" 23:59:59");
            map.put("ENDDATE",ExtendDate.getLastDayOfMonthNew(endDate)+" 23:59:59");
            map.put("LABEL_VALUE_ALL","2");
            map.put("SOURCE_TYPE",sourceType);
            String sqlDate = " AND t.HS_CREATE_DATE >= :STARTDATE AND t.HS_CREATE_DATE <= :ENDDATE AND t.SOURCE_TYPE = :SOURCE_TYPE  ";//AND t.SOURCE_TYPE = :SOURCE_TYPE
            String sql = "SELECT t.* FROM app_archivingcard_people t INNER JOIN app_label_archiving b ON t.ID = b.LABEL_ARCHIVING_ID WHERE 1=1 AND t.SIGN_ID IS NOT NULL  "+sqlDate;
            String sqlWqy = "SELECT t.ARCHIVING_PATIENT_IDNO FROM app_archivingcard_people t WHERE 1=1 AND t.SIGN_ID IS NULL  "+sqlDate;
            String sqlGroup = "SELECT b.LABEL_ARCHIVING_ID FROM app_archivingcard_people t INNER JOIN app_label_archiving b ON t.ID = b.LABEL_ARCHIVING_ID WHERE 1=1  "+sqlDate;
            String[] fwState ={ResidentMangeType.PTRQ.getValue(),ResidentMangeType.WEIZHI.getValue()};
//            String[] qys = {SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
//            String[] qys = {SignFormType.YQY.getValue(),SignFormType.YUQY.getValue(),SignFormType.YJY.getValue()};
//            map.put("SIGN_STATE", qys);
            if(cdAddress != null){
                map.put("ADDR_RURAL_CODE",cdAddress.getId());
                sql += " AND t.ADDR_RURAL_CODE = :ADDR_RURAL_CODE ";
                sqlGroup += " AND t.ADDR_RURAL_CODE = :ADDR_RURAL_CODE ";
                sqlWqy += " AND t.ADDR_RURAL_CODE = :ADDR_RURAL_CODE ";
                if(StringUtils.isNotBlank(addrHospId)){
                    map.put("ADDR_HOSP_ID",addrHospId);
                    sql += " AND t.ADDR_HOSP_ID =:ADDR_HOSP_ID ";
                    sqlGroup += " AND t.ADDR_HOSP_ID =:ADDR_HOSP_ID ";
                    sqlWqy += " AND t.ADDR_HOSP_ID =:ADDR_HOSP_ID ";
                }
            }
            String sqlAll = " SELECT count(1) FROM ("+sql+"  GROUP BY b.LABEL_ARCHIVING_ID ) c";
            yqy = this.sysDao.getServiceReadDo().findCount(sqlAll,map);//已签约
            returnMap.put("manageSignCount",yqy);//签约数
            String sqlKeyGroup = sqlGroup + " AND b.LABEL_VALUE NOT IN (:LABEL_VALUE_NOT_IN) ";
            String sqlKeyGroupAll = " SELECT count(1) FROM ("+sqlKeyGroup+"  GROUP BY b.LABEL_ARCHIVING_ID ) c";
            map.put("LABEL_VALUE_NOT_IN",fwState);
            key = this.sysDao.getServiceReadDo().findCount(sqlKeyGroupAll,map);//重点已签约
            returnMap.put("manageKeySignCount",key);//重点签约数
            String sqlWqyAll = " SELECT count(1) FROM ("+sqlWqy+"  ) c";
            wyqy = this.sysDao.getServiceReadDo().findCount(sqlWqyAll,map);//未签约 GROUP BY t.ARCHIVING_PATIENT_IDNO
            returnMap.put("manageNotSignCount",wyqy);//未签约
            sqlGroup += " AND b.LABEL_VALUE=:SIGN_PERS_GROUP";
            String sqlGroupAll = " SELECT count(1) FROM ("+sqlGroup+"  GROUP BY  b.LABEL_ARCHIVING_ID ) c";
            for(AppLabelManage l:ls) {
                map.put("SIGN_PERS_GROUP",l.getLabelValue());
                int  count= sysDao.getServiceReadDo().findCount(sqlGroupAll, map);
                if(l.getLabelValue().equals("1")){//普通人群
                    returnMap.put("managePlainCount", count);
                }else if(l.getLabelValue().equals("2")){//儿童(0-6岁)
                    returnMap.put("manageChildCount", count);
                }else if(l.getLabelValue().equals("3")){//孕产妇
                    returnMap.put("manageMaternalCount", count);
                }else if(l.getLabelValue().equals("4")){//老年人
                    returnMap.put("manageOldPeopleCount", count);
                }else if(l.getLabelValue().equals("5")){//高血压
                    returnMap.put("manageBloodCount", count);
                }else if(l.getLabelValue().equals("6")){//糖尿病
                    returnMap.put("manageDiabetesCount", count);
                }else if(l.getLabelValue().equals("7")){//严重精神障碍
                    returnMap.put("managePsychosisCount", count);
                }else if(l.getLabelValue().equals("8")){//结核病
                    returnMap.put("managePhthisisCount", count);
                }else if(l.getLabelValue().equals("9")){//残疾人
                    returnMap.put("manageDisabledPeopleCount", count);
                }else if(l.getLabelValue().equals("99")){//未知
                    returnMap.put("manageServiceUnknownCount", count);
                }
            }
        }
        return  returnMap;
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
            Calendar calendar = Calendar.getInstance();
            int nowYear = calendar.get(Calendar.YEAR);
            int nowMonth = calendar.get(Calendar.MONTH) + 1;
            int now = Integer.parseInt(String.valueOf(nowYear)+ String.valueOf(nowMonth));
            String[] year = qvo.getYearEnd().split("-");
            int endYear = Integer.parseInt(year[0]);
            int endMonth = Integer.parseInt(year[1]);
            int end = Integer.parseInt(String.valueOf(endYear)+ String.valueOf(endMonth));
//            if (StringUtils.isNotBlank(qvo.getTeamId())) {
//                map.put("yearStart", ExtendDate.getFirstDayOfMonth(qvo.getYearStart())+" 00:00:00");
//                map.put("yearEnd",ExtendDate.getLastDayOfMonth(qvo.getYearEnd())+" 23:59:59");
//                String sql = "SELECT COUNT(1) FROM APP_SIGN_FORM a WHERE a.SIGN_STATE='2'   ";
//                sql += "AND a.SIGN_FROM_DATE >= :yearStart AND a.SIGN_FROM_DATE <= :yearEnd ";
//                sql += " AND a.SIGN_TEAM_ID =:SIGN_TEAM_ID";
//                map.put("SIGN_TEAM_ID", qvo.getTeamId());
//                sql += " AND a.SIGN_HEALTH_GROUP=:SIGN_HEALTH_GROUP";
//                for(CdCode l:ls) {
//                    ManageCountEntity v = new ManageCountEntity();
//                    if(l.getCodeValue().equals("1")){//一般人口
//                        map.put("SIGN_JJ_TYPE",l.getCodeValue());
//                        int  count= sysDao.getServiceReadDo().findCount(sql, map);
//                        v.setTitle("manageGeneralPopulationCount");
//                        v.setValue(String.valueOf(count));
//                        lsEntity.add(v);
//                    }else if(l.getCodeValue().equals("2")){//建档立卡贫困人口
//                        map.put("SIGN_JJ_TYPE",l.getCodeValue());
//                        int  count= sysDao.getServiceReadDo().findCount(sql, map);
//                        v.setTitle("managePoorFamilyCount");
//                        v.setValue(String.valueOf(count));
//                        lsEntity.add(v);
//                    }else if(l.getCodeValue().equals("3")){//低保户
//                        map.put("SIGN_JJ_TYPE",l.getCodeValue());
//                        int  count= sysDao.getServiceReadDo().findCount(sql, map);
//                        v.setTitle("manageLowFamilyCount");
//                        v.setValue(String.valueOf(count));
//                        lsEntity.add(v);
//                    }else if(l.getCodeValue().equals("4")){//特困户（五保户）
//                        map.put("SIGN_JJ_TYPE",l.getCodeValue());
//                        int  count= sysDao.getServiceReadDo().findCount(sql, map);
//                        v.setTitle("manageDestituteFamilyCount");
//                        v.setValue(String.valueOf(count));
//                        lsEntity.add(v);
//                    }else if(l.getCodeValue().equals("5")){//计生特殊家庭
//                        map.put("SIGN_JJ_TYPE",l.getCodeValue());
//                        int  count= sysDao.getServiceReadDo().findCount(sql, map);
//                        v.setTitle("manageSpecialPlanFamilyCount");
//                        v.setValue(String.valueOf(count));
//                        lsEntity.add(v);
//                    }
//                }
//            }else{
            String sqlNow = null;
            String sqlTeam = "";
            //如果结束时间大于等于当前时间
            if(end >= now){
                sqlTeam = " SELECT aa.ID FROM APP_TEAM aa WHERE aa.TEAM_DEL_STATE = '1' ";
//                    String state = SignFormType.YQY.getValue();//状态
                String[] state = {SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};//状态
                map.put("SIGN_STATE",state);
                map.put("yearStart", ExtendDate.getYMD(Calendar.getInstance())+" 00:00:00");
                map.put("yearEnd",ExtendDate.getYMD(Calendar.getInstance())+" 23:59:59");
                sqlNow = "SELECT COUNT(1) FROM APP_SIGN_FORM t LEFT JOIN APP_LABEL_ECONOMICS b ON t.ID = b.LABEL_SIGN_ID  WHERE t.SIGN_STATE IN (:SIGN_STATE) ";
                sqlNow += "AND t.SIGN_FROM_DATE >= :yearStart AND t.SIGN_FROM_DATE <= :yearEnd ";
                sqlNow += " AND b.LABEL_VALUE =:LABEL_VALUE  AND b.LABEL_TYPE='4' ";
                if(StringUtils.isNotBlank(qvo.getTeamId())){
                    sqlNow += " AND t.SIGN_TEAM_ID =:SIGN_TEAM_ID";
                    map.put("SIGN_TEAM_ID", qvo.getTeamId());
                }
                if(StringUtils.isNotBlank(qvo.getHospId())){
                    map.put("SIGN_HOSP_ID",qvo.getHospId());
                    sqlNow += " AND t.SIGN_HOSP_ID = :SIGN_HOSP_ID ";
                }
                if(StringUtils.isNotBlank(qvo.getAreaId())){
                    String areaCode = AreaUtils.getAreaCode(qvo.getAreaId());
                    map.put("SIGN_AREA_CODE",areaCode+"%");
                    sqlNow += " AND t.SIGN_AREA_CODE LIKE :SIGN_AREA_CODE ";
                }
            }else{
                sqlTeam = "SELECT aa.ID FROM APP_TEAM aa WHERE aa.TEAM_DEL_STATE = '1' " +
                        "AND aa.TEAM_DEL_TIME >=:stratTime\n" +
                        "AND aa.TEAM_DEL_TIME <=:endTime ";
            }
            map.put("STARTDATE",qvo.getYearStart());
            map.put("ENDDATE",qvo.getYearEnd());
            map.put("startTime",ExtendDate.getFirstDayOfMonth(qvo.getYearStart()));
            map.put("endTime",ExtendDate.getLastDayOfMonth(qvo.getYearEnd()));
            String sqlDate = " AND t.MANAGE_YEAR_MONTH >= :STARTDATE AND t.MANAGE_YEAR_MONTH <= :ENDDATE ";
            String sql = "SELECT " +
                    "SUM(t.MANAGE_LOW_FAMILY_COUNT) manageLowFamilyCount," +
                    "SUM(t.MANAGE_DESTITUTE_FAMILY_COUNT) manageDestituteFamilyCount," +
                    "SUM(t.MANAGE_SPECIAL_PLAN_FAMILY_COUNT) manageSpecialPlanFamilyCount," +
                    "SUM(t.MANAGE_GENERAL_POPULATION_COUNT) manageGeneralPopulationCount," +
                    "SUM(t.MANAGE_POOR_FAMILY_COUNT) managePoorFamilyCount," +
                    "SUM(t.MANAGE_JSDZN_COUNT) manageJsdznCount," +
                    "SUM(t.MANAGE_JSSN_COUNT) manageJssnCount," +
                    "SUM(t.MANAGE_PKH_COUNT) managePkhCount," +
                    "SUM(t.MANAGE_QTJJ_COUNT) manageQtJjCount " +
                    " FROM APP_MANAGE_COUNT t where 1=1 "+sqlDate;
            if(StringUtils.isNotBlank(qvo.getTeamId())){
                map.put("TEAM_ID",qvo.getTeamId());
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

            //是否去除已删团队签约统计数
            sql += " AND t.MANAGE_TEAM_ID NOT IN ("+sqlTeam+") ";

            List<Map> maps = this.sysDao.getServiceReadDo().findSqlMap(sql,map);
            if(maps != null && maps.size() >0){
                for(AppLabelManage l:ls) {
                    ManageCountEntity v = new ManageCountEntity();
                    if(l.getLabelValue().equals("1")){//一般人口
                        if(maps.get(0).get("manageGeneralPopulationCount") != null){
                            v.setTitle("manageGeneralPopulationCount");
                            v.setName(l.getLabelTitle());
                            int count = (int)Double.parseDouble(maps.get(0).get("manageGeneralPopulationCount").toString());
                            if(StringUtils.isNotBlank(sqlNow)){
                                map.put("LABEL_VALUE",l.getLabelValue());
                                count += sysDao.getServiceReadDo().findCount(sqlNow, map);
                            }
                            v.setValue(String.valueOf(count));
                            lsEntity.add(v);
                        }
                    }else if(l.getLabelValue().equals("2")){//建档立卡贫困人口
                        if(maps.get(0).get("managePoorFamilyCount") != null){
                            v.setTitle("managePoorFamilyCount");
                            v.setName(l.getLabelTitle());
                            int count = (int)Double.parseDouble(maps.get(0).get("managePoorFamilyCount").toString());
                            if(StringUtils.isNotBlank(sqlNow)){
                                map.put("LABEL_VALUE",l.getLabelValue());
                                count += sysDao.getServiceReadDo().findCount(sqlNow, map);
                            }
                            v.setValue(String.valueOf(count));
                            lsEntity.add(v);
                        }
                    }else if(l.getLabelValue().equals("3")){//低保户
                        if(maps.get(0).get("manageLowFamilyCount") != null){
                            v.setTitle("manageLowFamilyCount");
                            v.setName(l.getLabelTitle());
                            int count = (int)Double.parseDouble(maps.get(0).get("manageLowFamilyCount").toString());
                            if(StringUtils.isNotBlank(sqlNow)){
                                map.put("LABEL_VALUE",l.getLabelValue());
                                count += sysDao.getServiceReadDo().findCount(sqlNow, map);
                            }
                            v.setValue(String.valueOf(count));
                            lsEntity.add(v);
                        }
                    }else if(l.getLabelValue().equals("4")){//特困户（五保户）
                        if(maps.get(0).get("manageDestituteFamilyCount") != null){
                            v.setTitle("manageDestituteFamilyCount");
                            v.setName(l.getLabelTitle());
                            int count = (int)Double.parseDouble(maps.get(0).get("manageDestituteFamilyCount").toString());
                            if(StringUtils.isNotBlank(sqlNow)){
                                map.put("LABEL_VALUE",l.getLabelValue());
                                count += sysDao.getServiceReadDo().findCount(sqlNow, map);
                            }
                            v.setValue(String.valueOf(count));
                            lsEntity.add(v);
                        }
                    }else if(l.getLabelValue().equals("5")){//计生特殊家庭
                        if(maps.get(0).get("manageSpecialPlanFamilyCount") != null){
                            v.setTitle("manageSpecialPlanFamilyCount");
                            v.setName(l.getLabelTitle());
                            int count = (int)Double.parseDouble(maps.get(0).get("manageSpecialPlanFamilyCount").toString());
                            if(StringUtils.isNotBlank(sqlNow)){
                                map.put("LABEL_VALUE",l.getLabelValue());
                                count += sysDao.getServiceReadDo().findCount(sqlNow, map);
                            }
                            v.setValue(String.valueOf(count));
                            lsEntity.add(v);
                        }
                    }else if(l.getLabelValue().equals("7")){//计生独子女户
                        if(maps.get(0).get("manageJsdznCount") != null){
                            v.setTitle("manageJsdznCount");
                            v.setName(l.getLabelTitle());
                            int count = (int)Double.parseDouble(maps.get(0).get("manageJsdznCount").toString());
                            if(StringUtils.isNotBlank(sqlNow)){
                                map.put("LABEL_VALUE",l.getLabelValue());
                                count += sysDao.getServiceReadDo().findCount(sqlNow, map);
                            }
                            v.setValue(String.valueOf(count));
                            lsEntity.add(v);
                        }
                    }else if(l.getLabelValue().equals("8")){//计生双女户
                        if(maps.get(0).get("manageJssnCount") != null){
                            v.setTitle("manageJssnCount");
                            v.setName(l.getLabelTitle());
                            int count = (int)Double.parseDouble(maps.get(0).get("manageJssnCount").toString());
                            if(StringUtils.isNotBlank(sqlNow)){
                                map.put("LABEL_VALUE",l.getLabelValue());
                                count += sysDao.getServiceReadDo().findCount(sqlNow, map);
                            }
                            v.setValue(String.valueOf(count));
                            lsEntity.add(v);
                        }
                    }else if(l.getLabelValue().equals("9")){//贫困户
                        if(maps.get(0).get("managePkhCount") != null){
                            v.setTitle("managePkhCount");
                            v.setName(l.getLabelTitle());
                            int count = (int)Double.parseDouble(maps.get(0).get("managePkhCount").toString());
                            if(StringUtils.isNotBlank(sqlNow)){
                                map.put("LABEL_VALUE",l.getLabelValue());
                                count += sysDao.getServiceReadDo().findCount(sqlNow, map);
                            }
                            v.setValue(String.valueOf(count));
                            lsEntity.add(v);
                        }
                    }else if(l.getLabelValue().equals("10")){//其他经济
                        if(maps.get(0).get("manageQtJjCount") != null){
                            v.setTitle("manageQtJjCount");
                            v.setName(l.getLabelTitle());
                            int count = (int)Double.parseDouble(maps.get(0).get("manageQtJjCount").toString());
                            if(StringUtils.isNotBlank(sqlNow)){
                                map.put("LABEL_VALUE",l.getLabelValue());
                                count += sysDao.getServiceReadDo().findCount(sqlNow, map);
                            }
                            v.setValue(String.valueOf(count));
                            lsEntity.add(v);
                        }
                    }
                }
            }
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
//            map.put("yearEnd","2018-10-18 23:59:59");
            String sqlZdrqqy = "SELECT a.* FROM APP_SIGN_FORM a LEFT JOIN APP_LABEL_ECONOMICS b ON a.ID = b.LABEL_SIGN_ID where 1=1 and b.LABEL_TYPE='4' AND b.LABEL_VALUE NOT IN (:SIGN_PERS_GROUP) " ;
            String sql = "SELECT a.* FROM APP_SIGN_FORM a LEFT JOIN APP_LABEL_ECONOMICS b on a.ID = b.LABEL_SIGN_ID WHERE 1=1 ";
            //医保建档立卡计算
            String sqlJdlk = "SELECT a.* FROM app_archivingcard_people a INNER JOIN app_label_archiving b ON a.ID = b.LABEL_ARCHIVING_ID \n" +
                    "WHERE a.SOURCE_TYPE = '3' AND a.SIGN_ID IS NOT NULL AND a.SIGN_FROM_DATE >= :yearStart AND a.SIGN_FROM_DATE <= :yearEnd ";


//            String[] qys = {SignFormType.YQY.getValue(),SignFormType.YJY.getValue()};
//            map.put("yqy", qys);
//            String qys = SignFormType.YQY.getValue();
            String[] qys = {SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
            map.put("yqy", qys);

            map.put("yjy",SignFormType.YJY.getValue());
            map.put("tsnr","签约到期，自动解约");
            boolean flag = ExtendDateUtil.getIsDateSatisfied(qvo.getYearEnd());
            String sqlZdrqqyl = "SELECT a.* FROM APP_SIGN_FORM a LEFT JOIN APP_LABEL_ECONOMICS b ON a.ID = b.LABEL_SIGN_ID " +
                    "where 1=1 and b.LABEL_TYPE='4' AND b.LABEL_VALUE NOT IN (:SIGN_PERS_GROUP) AND a.SIGN_STATE =:yjy AND a.SIGN_URRENDER_REASON =:tsnr AND a.SIGN_FROM_DATE >= :yearStart AND a.SIGN_FROM_DATE <= :yearEnd " ;
            String sqll = "SELECT a.* FROM APP_SIGN_FORM a LEFT JOIN APP_LABEL_ECONOMICS b on a.ID = b.LABEL_SIGN_ID WHERE 1=1 AND a.SIGN_STATE =:yjy AND a.SIGN_URRENDER_REASON =:tsnr  AND a.SIGN_FROM_DATE >= :yearStart AND a.SIGN_FROM_DATE <= :yearEnd ";


            map.put("SIGN_PERS_GROUP","1");
            sql += " AND a.SIGN_STATE IN (:yqy) ";
            sql += " AND a.SIGN_FROM_DATE >= :yearStart AND a.SIGN_FROM_DATE <= :yearEnd ";
            sqlZdrqqy += " AND a.SIGN_STATE IN (:yqy) ";
            sqlZdrqqy += " AND a.SIGN_FROM_DATE >= :yearStart AND a.SIGN_FROM_DATE <= :yearEnd ";
            if (StringUtils.isNotBlank(qvo.getAreaId())) {
                sql += " AND a.SIGN_AREA_CODE =:SIGN_AREA_CODE";
                sqlZdrqqy += " AND a.SIGN_AREA_CODE =:SIGN_AREA_CODE";

                sqll += " AND a.SIGN_AREA_CODE =:SIGN_AREA_CODE";
                sqlZdrqqyl += " AND a.SIGN_AREA_CODE =:SIGN_AREA_CODE";
                map.put("SIGN_AREA_CODE", qvo.getAreaId()+"%");
            }
            if (StringUtils.isNotBlank(qvo.getHospId())) {
                sql += " AND a.SIGN_HOSP_ID =:SIGN_HOSP_ID";
                sqlZdrqqy += " AND a.SIGN_HOSP_ID =:SIGN_HOSP_ID";
                map.put("SIGN_HOSP_ID", qvo.getHospId());

                sqll += " AND a.SIGN_HOSP_ID =:SIGN_HOSP_ID";
                sqlZdrqqyl += " AND a.SIGN_HOSP_ID =:SIGN_HOSP_ID";
            }
            if (StringUtils.isNotBlank(qvo.getTeamId())) {
                sql += " AND a.SIGN_TEAM_ID =:SIGN_TEAM_ID";
                sqlZdrqqy += " AND a.SIGN_TEAM_ID =:SIGN_TEAM_ID";
                map.put("SIGN_TEAM_ID",qvo.getTeamId());

                sqll += " AND a.SIGN_TEAM_ID =:SIGN_TEAM_ID";
                sqlZdrqqyl += " AND a.SIGN_TEAM_ID =:SIGN_TEAM_ID";
                sqlJdlk += " AND a.TEAM_ID =:SIGN_TEAM_ID ";
//                AppTeam teamm = (AppTeam)sysDao.getServiceDo().find(AppTeam.class,qvo.getTeamId());
//                if(teamm != null){
//                    map.put("teamHospId",teamm.getTeamHospId());
//                    sqlJdlk += " AND a.ADDR_HOSP_ID =:teamHospId ";
//                }


            }

            String sqlZdjjlx = " SELECT count(1) FROM ("+sqlZdrqqy+"  GROUP BY a.SIGN_PATIENT_ID ) c";
            int zdrqyqy = this.sysDao.getServiceReadDo().findCount(sqlZdjjlx,map);//重點人群经济类型已簽約
            if(flag){
                String sqlZdjjlxl = " SELECT count(1) FROM ("+sqlZdrqqyl+"  GROUP BY a.SIGN_PATIENT_ID ) c";
                zdrqyqy  += this.sysDao.getServiceReadDo().findCount(sqlZdjjlxl,map);//重點人群经济类型已簽約
            }

            rmap.put("manageEconomicSignCount", zdrqyqy);
            sql += " AND b.LABEL_TYPE = '4' AND b.LABEL_VALUE = :LABEL_VALUE ";
            sqll += " AND b.LABEL_TYPE = '4' AND b.LABEL_VALUE = :LABEL_VALUE ";
            String sqlAll = " SELECT count(1) FROM ("+sql+"  GROUP BY a.SIGN_PATIENT_ID ) c";
            String sqlAlll = " SELECT count(1) FROM ("+sqll+"  GROUP BY a.SIGN_PATIENT_ID ) c";
            for(AppLabelManage l:ls) {
                map.put("LABEL_VALUE",l.getLabelValue());
                int  count= sysDao.getServiceReadDo().findCount(sqlAll, map);
                if(flag){
                    count +=  sysDao.getServiceReadDo().findCount(sqlAlll, map);
                }
                if(l.getLabelValue().equals("1")){//一般人口
                    rmap.put("manageGeneralPopulationCount", count);
                }else if(l.getLabelValue().equals("2")){//建档立卡贫困人口
                    if("0".equals(qvo.getJdSourceType())){
                        String sqlJdlks = "SELECT COUNT(1) FROM ("+sqlJdlk+" GROUP BY a.ID ) cc";
                        int ccount = sysDao.getServiceDo().findCount(sqlJdlks,map);
                        rmap.put("managePoorFamilyCount", ccount);
                    }else{
                        rmap.put("managePoorFamilyCount", count);
                    }
                }else if(l.getLabelValue().equals("3")){//低保户
                    rmap.put("manageLowFamilyCount", count);
                }else if(l.getLabelValue().equals("4")){//特困户（五保户）
                    rmap.put("manageDestituteFamilyCount", count);
                }else if(l.getLabelValue().equals("5")){//计生独伤残家庭
//                    map.put("LABEL_VALUE",l.getLabelValue());
//                    int  count= sysDao.getServiceReadDo().findCount(sqlAll, map);
                    rmap.put("manageSpecialPlanFamilyCount", count);
                }else if(l.getLabelValue().equals("7")){//计生独子女户
                    rmap.put("manageJsdznCount", count);
                }else if(l.getLabelValue().equals("8")){//计生双女户
                    rmap.put("manageJssnCount", count);
                }else if(l.getLabelValue().equals("9")){//因病致贫
                    rmap.put("managePkhCount", count);
                }else if(l.getLabelValue().equals("10")){//其他
                    rmap.put("manageQtJjCount", count);
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
    public Map<String, Object> findSignRenew(ResidentVo qvo) throws Exception {
        HashMap rmap = new HashMap();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("yearStart", ExtendDate.getFirstDayOfMonth(qvo.getYearStart())+" 00:00:00");
        map.put("yearEnd",ExtendDate.getLastDayOfMonth(qvo.getYearEnd())+" 23:59:59");
//        map.put("yearEnd","2018-10-18 23:59:59");
        String[] qys = {SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
        map.put("signState",qys);
        map.put("contractState", CommSF.YES.getValue());
        String sql = "SELECT\n" +
                "\tCOUNT(1)\n" +
                "FROM\n" +
                "\tAPP_SIGN_FORM\n" +
                "WHERE 1=1\n" +
                "\t AND SIGN_RENEWORGOTOSIGN_DATE >= :yearStart\n" +
                "AND SIGN_RENEWORGOTOSIGN_DATE <= :yearEnd\n" +
                "AND SIGN_GOTO_SIGN_STATE='2'";
//                "AND SIGN_STATE = :signState\n" +
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
        int renew = this.sysDao.getServiceReadDo().findCount(sql,map);
        rmap.put("manageRenew",renew);
        return rmap;
    }

    /**
     * 签约数
     * @param qvo
     * @return
     */
    @Override
    public Map<String, Object> findSignTotalRenew(ResidentVo qvo) throws Exception {
        HashMap rmap = new HashMap();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("yearStart", ExtendDate.getFirstDayOfMonth(qvo.getYearStart())+" 00:00:00");
        map.put("yearEnd",ExtendDate.getLastDayOfMonth(qvo.getYearEnd())+" 23:59:59");
//        map.put("yearEnd","2018-10-18 23:59:59");
        String[] qys = {SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
        map.put("signState",qys);
        map.put("contractState", CommSF.NOT.getValue());
        map.put("yjy",SignFormType.YJY.getValue());
        map.put("tsnr","签约到期，自动解约");
        boolean flag = ExtendDateUtil.getIsDateSatisfied(qvo.getYearEnd());

        String sql = "SELECT\n" +
                "\tCOUNT(1)\n" +
                "FROM\n" +
                "\tAPP_SIGN_FORM\n" +
                "WHERE 1=1\n" +
                "\t AND SIGN_FROM_DATE >= :yearStart\n" +
                "AND SIGN_FROM_DATE <= :yearEnd\n" +
                "AND SIGN_STATE IN (:signState)\n" +
                "AND SIGN_CONTRACT_STATE = :contractState";

        String sqll = "SELECT\n" +
                "\tCOUNT(1)\n" +
                "FROM\n" +
                "\tAPP_SIGN_FORM\n" +
                "WHERE 1=1\n" +
                "\t AND SIGN_FROM_DATE >= :yearStart\n" +
                "AND SIGN_FROM_DATE <= :yearEnd\n" +
                "AND SIGN_STATE =:yjy AND SIGN_URRENDER_REASON =:tsnr\n" +
                "AND SIGN_CONTRACT_STATE = :contractState";

        if(StringUtils.isNotBlank(qvo.getAreaId())){
            map.put("areaCode",qvo.getAreaId()+"%");
            sql += " AND SIGN_AREA_CODE LIKE :areaCode ";
            sqll += " AND SIGN_AREA_CODE LIKE :areaCode ";
        }
        if(StringUtils.isNotBlank(qvo.getHospId())){
            map.put("hospId",qvo.getHospId());
            sql += " AND SIGN_HOSP_ID =:hospId";
            sqll += " AND SIGN_HOSP_ID =:hospId";
        }
        if(StringUtils.isNotBlank(qvo.getTeamId())){
            map.put("teamId",qvo.getTeamId());
            sql += " AND SIGN_TEAM_ID =:teamId";
            sqll += " AND SIGN_TEAM_ID =:teamId";
        }
        int totlaRenew = this.sysDao.getServiceReadDo().findCount(sql,map);
        if(flag){
            totlaRenew += this.sysDao.getServiceReadDo().findCount(sqll,map);
        }
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
                List<Map> maps = this.sysDao.getServiceReadDo().findSqlMap(sqlO,map);
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
                List<AppHospDept> hospDepts = sysDao.getServiceReadDo().loadByPk(AppHospDept.class, "hospUpcityareaId", qvo.getAreaId());
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
        if(list!=null && list.size()>0){
            num1=list.size();
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
        List<Map> maps = this.sysDao.getServiceReadDo().findSqlMap(sql,map);

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
        List<Map> maps = this.sysDao.getServiceReadDo().findSqlMap(sql,map);

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
        List<Map> maps = this.sysDao.getServiceReadDo().findSqlMap(sql,map);

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
    public Map<String, Object> findSignEconomicTypeCount(ResidentVo qvo) throws Exception {
        HashMap rmap = new HashMap();
        HashMap map = new HashMap();
        Calendar calendar = Calendar.getInstance();
        int nowYear = calendar.get(Calendar.YEAR);
        int nowMonth = calendar.get(Calendar.MONTH) + 1;
        int now = Integer.parseInt(String.valueOf(nowYear)+ String.valueOf(nowMonth));
        String[] year = qvo.getYearEnd().split("-");
        int endYear = Integer.parseInt(year[0]);
        int endMonth = Integer.parseInt(year[1]);
        int end = Integer.parseInt(String.valueOf(endYear)+ String.valueOf(endMonth));

        String sqlNow = null;
        //如果结束时间大于等于当前时间
        if(end >= now){
//            String state = SignFormType.YQY.getValue();//状态
            String[] state = {SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};//状态
            map.put("SIGN_STATE",state);
            map.put("yearStart", ExtendDate.getYMD(Calendar.getInstance())+" 00:00:00");
            map.put("yearEnd",ExtendDate.getYMD(Calendar.getInstance())+" 23:59:59");
            sqlNow = "SELECT COUNT(1) FROM APP_SIGN_FORM t  LEFT JOIN APP_LABEL_ECONOMICS b ON t.ID = b.LABEL_SIGN_ID WHERE t.SIGN_STATE IN (:SIGN_STATE)  ";
            sqlNow += "AND t.SIGN_FROM_DATE >= :yearStart AND t.SIGN_FROM_DATE <= :yearEnd ";
            sqlNow += " AND b.LABEL_VALUE =:LABEL_VALUE  AND b.LABEL_TYPE='4' ";
            if(StringUtils.isNotBlank(qvo.getTeamId())){
                sqlNow += " AND t.SIGN_TEAM_ID =:SIGN_TEAM_ID";
                map.put("SIGN_TEAM_ID", qvo.getTeamId());
            }
            if(StringUtils.isNotBlank(qvo.getHospId())){
                map.put("SIGN_HOSP_ID",qvo.getHospId());
                sqlNow += " AND t.SIGN_HOSP_ID = :SIGN_HOSP_ID ";
            }
            if(StringUtils.isNotBlank(qvo.getAreaId())){
                String areaCode = AreaUtils.getAreaCode(qvo.getAreaId());
                map.put("SIGN_AREA_CODE",areaCode+"%");
                sqlNow += " AND t.SIGN_AREA_CODE LIKE :SIGN_AREA_CODE ";
            }

        }
//        List<CdCode> ls = this.sysDao.getCodeDao().findGroupList(CodeGroupConstrats.GROUP_JJLX, CommonEnable.QIYONG.getValue());
        List<AppLabelManage> ls = this.sysDao.getAppLabelManageDao().findByType(LabelManageType.JJLX.getValue());
        if(ls!=null && !ls.isEmpty()) {

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
            List<Map> maps = this.sysDao.getServiceReadDo().findSqlMap(sql,map);
            if(maps != null && maps.size() >0){
                for(AppLabelManage l:ls) {
                    ManageCountEntity v = new ManageCountEntity();
                    if(l.getLabelValue().equals("1")){//一般人口
                        if(maps.get(0).get("manageGeneralPopulationCount") != null){
                            int count = (int)Double.parseDouble(maps.get(0).get("manageGeneralPopulationCount").toString());
                            if(StringUtils.isNotBlank(sqlNow)){
                                map.put("LABEL_VALUE",l.getLabelValue());
                                count += sysDao.getServiceReadDo().findCount(sqlNow, map);
                            }
                            rmap.put("manageGeneralPopulationCount",count);
                        }
                    }else if(l.getLabelValue().equals("2")){//建档立卡贫困人口
                        if(maps.get(0).get("managePoorFamilyCount") != null){
                            int count = (int)Double.parseDouble(maps.get(0).get("managePoorFamilyCount").toString());
                            if(StringUtils.isNotBlank(sqlNow)){
                                map.put("LABEL_VALUE",l.getLabelValue());
                                count += sysDao.getServiceReadDo().findCount(sqlNow, map);
                            }
                            rmap.put("managePoorFamilyCount",count);
                        }
                    }else if(l.getLabelValue().equals("3")){//低保户
                        if(maps.get(0).get("manageLowFamilyCount") != null){
                            int count = (int)Double.parseDouble(maps.get(0).get("manageLowFamilyCount").toString());
                            if(StringUtils.isNotBlank(sqlNow)){
                                map.put("LABEL_VALUE",l.getLabelValue());
                                count += sysDao.getServiceReadDo().findCount(sqlNow, map);
                            }
                            rmap.put("manageLowFamilyCount",count);
                        }
                    }else if(l.getLabelValue().equals("4")){//特困户（五保户）
                        if(maps.get(0).get("manageDestituteFamilyCount") != null){
                            int count = (int)Double.parseDouble(maps.get(0).get("manageDestituteFamilyCount").toString());
                            if(StringUtils.isNotBlank(sqlNow)){
                                map.put("LABEL_VALUE",l.getLabelValue());
                                count += sysDao.getServiceReadDo().findCount(sqlNow, map);
                            }
                            rmap.put("manageDestituteFamilyCount",count);
                        }
                    }else if(l.getLabelValue().equals("5")){//计生特殊家庭
                        if(maps.get(0).get("manageSpecialPlanFamilyCount") != null){
                            int count = (int)Double.parseDouble(maps.get(0).get("manageSpecialPlanFamilyCount").toString());
                            if(StringUtils.isNotBlank(sqlNow)){
                                map.put("LABEL_VALUE",l.getLabelValue());
                                count += sysDao.getServiceReadDo().findCount(sqlNow, map);
                            }
                            rmap.put("manageSpecialPlanFamilyCount",count);
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
    public Map<String, Object> findGotoSign(ResidentVo qvo) throws Exception {

        Map<String,Object> returnMap = new HashMap<String,Object>();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("year",qvo.getYearStart());
        //本年度转签数
        int goToCount = 0;
        //本年度续签数
        int renewCount = 0;
        //今年跨区转签数
        int nowArea = 0;
        //今年跨社区转签数
        int nowHosp = 0;
        //今年跨团队转签数
        int nowTeam = 0;
        //去年跨区转签数
        int oldArea = 0;
        //去年跨社区转签数
        int oldHosp = 0;
        //去年跨团队转签数
        int oldTeam = 0;

        String sql = "SELECT sum(MANAGE_GOTOSIGN_COUNT) goToCount," +
                "sum(MANAGE_RENEW) renewCount," +
                "sum(MANAGE_GOTOSIGN_AREA) MANAGE_GOTOSIGN_AREA," +
                "sum(MANAGE_GOTOSIGN_HOSP) MANAGE_GOTOSIGN_HOSP," +
                "sum(MANAGE_GOTOSIGN_TEAM) MANAGE_GOTOSIGN_TEAM FROM APP_MANAGE_COUNT WHERE 1=1";
        sql += " AND MANAGE_YEAR=:year ";
        int oldYear = Integer.parseInt(qvo.getYearStart())-1;
        map.put("oldYear",oldYear);
        String sqlold = "SELECT "+
                "sum(MANAGE_GOTOSIGN_AREA) MANAGE_GOTOSIGN_AREA," +
                "sum(MANAGE_GOTOSIGN_HOSP) MANAGE_GOTOSIGN_HOSP," +
                "sum(MANAGE_GOTOSIGN_TEAM) MANAGE_GOTOSIGN_TEAM FROM APP_MANAGE_COUNT WHERE 1=1";
        sqlold += " AND MANAGE_YEAR=:oldYear";
        String msql = "";
        //区域续签统计
        if(StringUtils.isNotBlank(qvo.getAreaId())){
            map.put("areaId",AreaUtils.getAreaCode(qvo.getAreaId())+"%");
            sql += " AND MANAGE_AREA_CODE LIKE :areaId";
            msql +=" AND a.SIGN_AREA_CODE LIKE :areaId";
            sqlold += " AND MANAGE_AREA_CODE LIKE :areaId";
        }
        //医院续签统计
        if(StringUtils.isNotBlank(qvo.getHospId())){
            map.put("hospId",qvo.getHospId());
            sql += " AND MANAGE_HOSP_ID = :hospId";
            sqlold += " AND MANAGE_HOSP_ID = :hospId";
            msql += " AND a.SIGN_HOSP_ID =:hospId ";
        }
        //团队续签统计
        if(StringUtils.isNotBlank(qvo.getTeamId())){
            map.put("teamId",qvo.getTeamId());
            sql += " AND MANAGE_TEAM_ID = :teamId";
            sqlold += " AND MANAGE_TEAM_ID =:teamId";
            msql += " AND a.SIGN_TEAM_ID = :teamId ";
        }
        List<Map> maps = this.sysDao.getServiceReadDo().findSqlMap(sql,map);
        if(maps!=null && maps.size()>0){
            if(maps.get(0).get("goToCount")!=null){
                goToCount = (int)Double.parseDouble(maps.get(0).get("goToCount").toString());
            }
            if(maps.get(0).get("renewCount")!=null){
                renewCount = (int)Double.parseDouble(maps.get(0).get("renewCount").toString());
            }
            //今年跨区转签数
            if(maps.get(0).get("MANAGE_GOTOSIGN_AREA") != null){
                nowArea = (int)Double.parseDouble(maps.get(0).get("MANAGE_GOTOSIGN_AREA").toString());
            }
            //今年跨社区转签数
            if(maps.get(0).get("MANAGE_GOTOSIGN_HOSP") != null){
                nowHosp = (int)Double.parseDouble(maps.get(0).get("MANAGE_GOTOSIGN_HOSP").toString());
            }
            //今年跨团队转签数
            if(maps.get(0).get("MANAGE_GOTOSIGN_TEAM") != null){
                nowTeam = (int)Double.parseDouble(maps.get(0).get("MANAGE_GOTOSIGN_TEAM").toString());
            }
        }
        String time = ExtendDate.getYMD(Calendar.getInstance());
        String nowYear = time.substring(0,4);
        int today = 0;
        if(qvo.getYearStart().equals(nowYear)) {
            time += " 00:00:00";
            map.put("today", time);
            String toDaySql = " SELECT COUNT(1) FROM APP_SIGN_FORM a WHERE a.SIGN_GOTO_SIGN_STATE = '2' " +
                    "AND a.SIGN_RENEWORGOTOSIGN_DATE<=SYSDATE() " +
                    " AND a.SIGN_RENEWORGOTOSIGN_DATE>=:today ";
            String tozqSql = "SELECT COUNT(1)\n" +
                    "FROM\n" +
                    "\tAPP_SIGN_FORM a INNER JOIN APP_GOTOSIGN_RECORD b ON a.ID = b.GTS_OLD_SIGN_ID\n" +
                    "WHERE 1=1\n" +
                    "\t AND a.SIGN_RENEWORGOTOSIGN_DATE >= :today\n" +
                    "AND a.SIGN_RENEWORGOTOSIGN_DATE <= SYSDATE()\n" +
                    "AND a.SIGN_GOTO_SIGN_STATE='1'\n" +
                    "AND b.GTS_SIGN_STATE='1' ";
            tozqSql+=msql;
            int todayZq = sysDao.getServiceReadDo().findCount(tozqSql,map);
            goToCount+=todayZq;
            //今天之前的续签人数加上今天的人数
            toDaySql += msql;
            today = sysDao.getServiceReadDo().findCount(toDaySql, map);
            renewCount += today;

            String ksql = "SELECT\n" +
                    "\tCOUNT(1)\n" +
                    "FROM\n" +
                    "\tAPP_SIGN_FORM a INNER JOIN APP_GOTOSIGN_RECORD b ON a.ID = b.GTS_OLD_SIGN_ID\n" +
                    "WHERE 1=1\n" +
                    "\t AND a.SIGN_RENEWORGOTOSIGN_DATE >= :today\n" +
                    "AND a.SIGN_RENEWORGOTOSIGN_DATE <= SYSDATE()\n" +
                    "AND a.SIGN_GOTO_SIGN_STATE='1'\n" +
                    "AND b.GTS_SIGN_STATE='1' " ;

            //跨区域
            String kqySql = ksql+" AND SUBSTRING(a.SIGN_AREA_CODE,1,6) != SUBSTRING(b.GTS_AREA_CODE,1,6) " +msql;
            int kqy = sysDao.getServiceReadDo().findCount(kqySql,map);
            nowArea+=kqy;
            //跨医院
            String kyySql = ksql+" AND a.SIGN_HOSP_ID != b.GTS_HOSP_ID "+msql;
            int kyy = sysDao.getServiceReadDo().findCount(kyySql,map);
            nowHosp+=kyy;
            //跨团队
            String ktdSql = ksql+" AND a.SIGN_TEAM_ID != b.GTS_TEAM_ID "+msql;
            int ktd = sysDao.getServiceReadDo().findCount(ktdSql,map);
            nowTeam +=ktd;

        }

        List<Map> oldMaps = this.sysDao.getServiceReadDo().findSqlMap(sqlold,map);
        if(oldMaps != null && oldMaps.size()>0){
            if(oldMaps.get(0).get("MANAGE_GOTOSIGN_AREA")!=null){
                oldArea = (int)Double.parseDouble(oldMaps.get(0).get("MANAGE_GOTOSIGN_AREA").toString());
            }
            if(oldMaps.get(0).get("MANAGE_GOTOSIGN_HOSP")!=null){
                oldHosp = (int)Double.parseDouble(oldMaps.get(0).get("MANAGE_GOTOSIGN_HOSP").toString());
            }
            if(oldMaps.get(0).get("MANAGE_GOTOSIGN_TEAM")!=null){
                oldTeam = (int)Double.parseDouble(oldMaps.get(0).get("MANAGE_GOTOSIGN_TEAM").toString());
            }

        }
        returnMap.put("goToCount",goToCount);
        returnMap.put("renewCount",renewCount);
        returnMap.put("nowArea",nowArea);
        returnMap.put("nowHosp",nowHosp);
        returnMap.put("nowTeam",nowTeam);
        returnMap.put("oldArea",oldArea);
        returnMap.put("oldHosp",oldHosp);
        returnMap.put("oldTeam",oldTeam);
        //转签率
        double goToSignRate = 0;
        if(renewCount!=0){
            goToSignRate = MyMathUtil.div(Double.valueOf(goToCount),Double.valueOf(renewCount),4)*100;
        }
        returnMap.put("goToSignRate",goToSignRate);
        //各转签原因占比
        List<CdCode> lsCode = sysDao.getCodeDao().findGroupList("zzReason","1");
        if(lsCode!=null && lsCode.size()>0){
            for(CdCode ll:lsCode){
                int num =0;
                map.put("reason",ll.getCodeValue());
                String sqlReason = "SELECT COUNT(1) reasonCount FROM APP_GOTOSIGN_RECORD a INNER JOIN APP_GOTO_SIGN_FB b ON a.ID = b.GS_ID " +
                        "WHERE DATE_FORMAT(a.GTS_CREATE_TIME,'%Y')=:year " +
                        "AND a.GTS_SIGN_STATE ='1' " +
                        "AND b.GS_REASON_TYPE=:reason ";
                String rmSql = " ";
                if(StringUtils.isNotBlank(qvo.getAreaId())){
                    sqlReason += " AND a.GTS_OLD_AREA_CODE LIKE :areaId ";
                    rmSql += " AND a.GTS_OLD_AREA_CODE LIKE :areaId ";
                }
                if(StringUtils.isNotBlank(qvo.getHospId())){
                    sqlReason += " AND a.GTS_OLD_HOSP_ID =:hospId ";
                    rmSql += " AND a.GTS_OLD_HOSP_ID =:hospId ";
                }
                if(StringUtils.isNotBlank(qvo.getTeamId())){
                    sqlReason += " AND a.GTS_OLD_TEAM_ID =:teamId ";
                    rmSql += " AND a.GTS_OLD_TEAM_ID =:teamId ";
                }
                List<Map> reaSongMap = this.sysDao.getServiceReadDo().findSqlMap(sqlReason,map);
                if(reaSongMap!=null && reaSongMap.size()>0){
                    if(reaSongMap.get(0).get("reasonCount")!=null){
                        num = (int)Double.parseDouble(reaSongMap.get(0).get("reasonCount").toString());
                    }
                }
                if(qvo.getYearStart().equals(nowYear)){
                    String reaSql = "SELECT COUNT(1) reasonCount FROM APP_GOTOSIGN_RECORD a INNER JOIN APP_GOTO_SIGN_FB b ON a.ID = b.GS_ID " +
                            "WHERE a.GTS_CREATE_TIME<=SYSDATE() AND a.GTS_CREATE_TIME>=:today " +
                            "AND a.GTS_SIGN_STATE ='1' " +
                            "AND b.GS_REASON_TYPE=:reason ";
                    reaSql+=rmSql;
                    int ss = sysDao.getServiceReadDo().findCount(reaSql,map);
                    num+=ss;
                }


//                1	常住地变更
//                2	医生专业度不够
//                3	咨询回复慢
//                4	无获得感
//                5	医生服务态度差
//                6	其他
                String title = "";
                if(ll.getCodeValue().equals("1")){//常住地变更
                    title = "czdbg";
                }else if(ll.getCodeValue().equals("2")){//医生专业度不够
                    title = "zydbg";
                }else if(ll.getCodeValue().equals("3")){//咨询回复慢
                    title = "zxhfm";
                }else if(ll.getCodeValue().equals("4")){//无获得感
                    title = "whdg";
                }else if(ll.getCodeValue().equals("5")){//医生服务态度差
                    title = "fwtdc";
                }else {
                    title = "qt";
                }
                returnMap.put(title,num);
            }
        }

        //钻取数据
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();//签约
        String result = null;
        if(StringUtils.isNotBlank(qvo.getAreaId())){
            if (AreaUtils.getAreaCode(qvo.getAreaId()).length() == 6){//区级行政，往下查看医院的排名
                //区下的医院集合
                List<AppHospDept> hospDepts = sysDao.getServiceReadDo().loadByPk(AppHospDept.class, "hospUpcityareaId", qvo.getAreaId());
                if(hospDepts != null && hospDepts.size() >0){
                    for (AppHospDept hosp : hospDepts){
                        CdAddress address = this.sysDao.getCdAddressDao().findByCacheCode(hosp.getHospAreaCode());
                        Map<String, Object> mapp = getNewGotoSianCount(address, hosp,null,qvo);
                        list.add(mapp);
                    }
                }
            }else{
                List<CdAddress> lsCdAddress = sysDao.getCdAddressDao().findUpCasheAreaCode(qvo.getAreaId());
                if(lsCdAddress != null && lsCdAddress.size()>0){
                    for (CdAddress address : lsCdAddress) {
                        Map<String, Object> mapp = getNewGotoSianCount(address, null,null,qvo);
                        list.add(mapp);
                    }
                }
            }
            result = "1";
        }else if(StringUtils.isNotBlank(qvo.getHospId())){
            List<AppTeam> lsTeam = sysDao.getAppTeamDao().findAll(qvo.getHospId());
            if(lsTeam != null && lsTeam.size()>0){
                for(AppTeam team : lsTeam){
                    Map<String, Object> mapp = getNewGotoSianCount(null,null,team,qvo);
                    list.add(mapp);
                }
            }
            result = "2";
        }
//        Collections.sort(list, ComparatorUtils.getComparator());
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
        zrs = sysDao.getServiceReadDo().findCount(zrSql,map);
        zcs = sysDao.getServiceReadDo().findCount(zcSql,map);
        zqs = sysDao.getServiceReadDo().findCount(zqSql,map);
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

    /**
     * 转诊统计
     * @param qvo
     * @return
     */
    @Override
    public Map<String, Object> findReferral(ResidentVo qvo) throws Exception {

        Map<String,Object> returnMap = new HashMap<>();
        Map<String,Object> map = new HashMap<>();
        map.put("startTime",ExtendDate.getFirstDayOfMonth(qvo.getYearStart())+" 00:00:00");
        map.put("endTime",ExtendDate.getLastDayOfMonth(qvo.getYearEnd())+" 23:59:59");
        map.put("REF_TYPE", ReferralType.ZR.getValue());
        map.put("REF_STATE",ReferralState.KFHZ.getValue());
        String sql = "SELECT COUNT(1) FROM APP_REFERRAL_TABLE a WHERE 1=1 " +
                "AND a.REF_APPLY_TIME>=:startTime AND a.REF_APPLY_TIME<=:endTime ";
        //转诊待通过、转诊已拒绝、转诊已过期
        String[] strs = new String[]{ReferralState.ZZDTG.getValue(),ReferralState.ZZYJJ.getValue(),ReferralState.ZZYGQ.getValue()};
        map.put("refState",strs);

        if(StringUtils.isNotBlank(qvo.getAreaId())){
            map.put("areaCode",AreaUtils.getAreaCode(qvo.getAreaId())+"%");

            int zzrl = 0;//总转诊转入量
            int zzcl = 0;//总转诊转出量
            int kfzcl = 0;//康复转出量
            int zzhl = 0;//总转回量
            double kfzb = 0;//康复转出占比
            //转诊转入量
            String sqlZzrl = "";
            //转诊转出量
            String sqlZcl = "";
            //康复转出量
            String sqlKfzcl = "";
            String sqlZzh = "";
            sqlZcl = "SELECT count(1) FROM APP_REFERRAL_TABLE a INNER JOIN APP_HOSP_DEPT b ON a.REF_OUT_ORG_ID = b.ID " +
                    "WHERE b.HOSP_AREA_CODE LIKE :areaCode AND a.REF_STATE NOT IN (:refState) " +
                    "AND a.REF_APPLY_TIME>=:startTime AND a.REF_APPLY_TIME<=:endTime";
            sqlZzrl = "SELECT count(1) FROM APP_REFERRAL_TABLE a INNER JOIN APP_HOSP_DEPT b ON a.REF_IN_ORG_ID = b.ID " +
                    "WHERE b.HOSP_AREA_CODE LIKE :areaCode AND a.REF_STATE NOT IN (:refState) " +
                    "AND a.REF_APPLY_TIME>=:startTime AND a.REF_APPLY_TIME<=:endTime";
            sqlKfzcl ="SELECT count(1) FROM APP_REFERRAL_TABLE a INNER JOIN APP_HOSP_DEPT b ON a.REF_IN_ORG_ID = b.ID " +
                    "WHERE b.HOSP_AREA_CODE LIKE :areaCode AND a.REF_STATE=:REF_STATE " +
                    "AND a.REF_APPLY_TIME>=:startTime AND a.REF_APPLY_TIME<=:endTime";
            sqlZzh = "SELECT count(1) FROM APP_REFERRAL_TABLE a INNER JOIN APP_HOSP_DEPT b ON a.REF_IN_ORG_ID = b.ID " +
                    "WHERE b.HOSP_AREA_CODE LIKE :areaCode AND a.REF_TYPE=:REF_TYPE " +
                    "AND a.REF_APPLY_TIME>=:startTime AND a.REF_APPLY_TIME<=:endTime";

            zzrl = sysDao.getServiceReadDo().findCount(sqlZzrl,map);
            zzcl = sysDao.getServiceReadDo().findCount(sqlZcl,map);
            kfzcl = sysDao.getServiceReadDo().findCount(sqlKfzcl,map);
            zzhl = sysDao.getServiceReadDo().findCount(sqlZzh,map);
            if(zzhl!=0){
                kfzb = MyMathUtil.div(Double.valueOf(kfzcl),Double.valueOf(zzhl),6)*100;//康复转出占比
                kfzb = MyMathUtil.round(kfzb,2);
            }
            returnMap.put("kfzb",String.valueOf(kfzb));//康复占比
            returnMap.put("zzrl",zzrl);//总转诊转入量
            returnMap.put("zzcl",zzcl);//总转诊转出量
            returnMap.put("kfzcl",kfzcl);//康复转出量

//            if(AreaUtils.getAreaCode(qvo.getAreaId()).length() == 6){//区级才要查询基层医院数和上级医院数
            //基层医院个数
            String sqlJc = "";
            //上级医院个数
            String sqlSj = "";

            int jcCount = 0;//基层医院数
            int sjCount = 0;//上级医院数
            sqlJc = "SELECT count(1) FROM APP_HOSP_DEPT WHERE HOSP_AREA_CODE LIKE :areaCode AND HOSP_LEVEL_TYPE IN ('7','8','9') ";
            sqlSj = "SELECT count(1) FROM APP_HOSP_DEPT WHERE HOSP_AREA_CODE LIKE :areaCode AND HOSP_LEVEL_TYPE IN ('0','1','2','3','4','5','6') ";
            jcCount = sysDao.getServiceReadDo().findCount(sqlJc,map);
            sjCount = sysDao.getServiceReadDo().findCount(sqlSj,map);
            returnMap.put("jcCount",jcCount);
            returnMap.put("sjCount",sjCount);

            //基层医院
            //本年度签约居民数
            int qyzs = 0;
            Calendar cal = Calendar.getInstance();
            map.put("yearsM",qvo.getYearStart());
            map.put("yeareM",qvo.getYearEnd());
            String sqlQyzs = "SELECT SUM(MANAGE_TOTAL_RENEW) MANAGE_TOTAL_RENEW FROM APP_MANAGE_COUNT WHERE MANAGE_AREA_CODE LIKE :areaCode AND MANAGE_YEAR_MONTH>=:yearsM AND" +
                    " MANAGE_YEAR_MONTH<=:yeareM ";
            List<Map> listQy = sysDao.getServiceReadDo().findSqlMap(sqlQyzs,map);
            if(listQy!=null && listQy.size()>0){
                qyzs = (int) Double.parseDouble(listQy.get(0).get("MANAGE_TOTAL_RENEW").toString());
            }
            returnMap.put("qyzs",qyzs);

            //转出总量 上转到上级医院的患者数量（人次）。被接收的数量
            int jczczs = 0;
            String sqlZczs ="SELECT COUNT(1) FROM APP_REFERRAL_TABLE a INNER JOIN APP_HOSP_DEPT b ON a.REF_OUT_ORG_ID = b.ID " +
                    "WHERE  b.HOSP_AREA_CODE LIKE :areaCode AND a.REF_STATE NOT IN (:refState) " +
                    "AND a.REF_APPLY_TIME>=:startTime AND a.REF_APPLY_TIME<=:endTime";

            //转出人数 上转到上级医院的患者人数。被接收的人数
            int jczcrs = 0;
            String sqlZcrs = "SELECT COUNT(1) FROM (SELECT a.* FROM APP_REFERRAL_TABLE a INNER JOIN APP_HOSP_DEPT b ON a.REF_OUT_ORG_ID = b.ID\n" +
                    "WHERE b.HOSP_AREA_CODE LIKE :areaCode AND a.REF_STATE NOT IN (:refState) " +
                    "AND a.REF_APPLY_TIME>=:startTime AND a.REF_APPLY_TIME<=:endTime GROUP BY a.REF_PATIENT_ID,a.REF_OUT_ORG_ID) aa";

            //转回量 上级医院转回的患者数量
            int jczhl = 0;
            String sqlZhl = "SELECT COUNT(1) FROM APP_REFERRAL_TABLE a INNER JOIN APP_HOSP_DEPT b ON a.REF_OUT_ORG_ID = b.ID " +
                    "WHERE b.HOSP_AREA_CODE LIKE :areaCode AND a.REF_TYPE=:REF_TYPE" +
                    " AND a.REF_APPLY_TIME>=:startTime AND a.REF_APPLY_TIME<=:endTime";

            //康复转回人数 上级医院转回的“康复转回”人数
            int jckfzhrs = 0;
            String sqlKfzhrs = "SELECT COUNT(1) FROM (SELECT a.* FROM APP_REFERRAL_TABLE a INNER JOIN APP_HOSP_DEPT b ON a.REF_OUT_ORG_ID = b.ID " +
                    "WHERE  b.HOSP_AREA_CODE LIKE :areaCode AND a.REF_STATE=:REF_STATE" +
                    " AND a.REF_APPLY_TIME>=:startTime AND a.REF_APPLY_TIME<=:endTime " +
                    "GROUP BY a.REF_PATIENT_ID,a.REF_OUT_ORG_ID) aa";

            //上级医院转诊分析
            //转入总量 接收下级医院上转的患者数量（人次）。同意的数量
            int sjzrl = 0;//上级转入总量
            String sqlZrl = "SELECT COUNT(1) FROM APP_REFERRAL_TABLE a INNER JOIN APP_HOSP_DEPT b ON a.REF_IN_ORG_ID = b.ID " +
                    "WHERE b.HOSP_AREA_CODE LIKE :areaCode AND a.REF_STATE NOT IN (:refState) " +
                    "AND a.REF_APPLY_TIME>=:startTime AND a.REF_APPLY_TIME<=:endTime";

            //转入人数 接收下级医院上转的患者人数（人次）。同意的人数
            int sjzrrs = 0;//上级转入人数
            String sqlZrrs = "SELECT COUNT(1) FROM (SELECT a.* FROM APP_REFERRAL_TABLE a INNER JOIN APP_HOSP_DEPT b ON a.REF_IN_ORG_ID = b.ID " +
                    "WHERE b.HOSP_AREA_CODE LIKE:areaCode AND a.REF_STATE NOT IN (:refState) AND a.REF_APPLY_TIME>=:startTime AND a.REF_APPLY_TIME<=:endTime" +
                    " GROUP BY a.REF_PATIENT_ID,a.REF_IN_ORG_ID) aa";

            //康复转出总量 康复转回给下级医疗机构的患者数量
            int sjkfzczl = 0;//上级康复转出总量
            String sqlKfzczl = "SELECT count(1) FROM APP_REFERRAL_TABLE a INNER JOIN APP_HOSP_DEPT b ON a.REF_IN_ORG_ID=b.ID " +
                    "WHERE b.HOSP_AREA_CODE LIKE:areaCode AND a.REF_STATE=:REF_STATE " +
                    "AND a.REF_APPLY_TIME>=:startTime AND a.REF_APPLY_TIME<=:endTime";

            //康复转出人数 康复转回给下级医疗机构的患者人数
            int sjkfzcrs = 0;//上级康复转出人数
            String sqlKfzcrs = "SELECT count(1) FROM (SELECT a.* FROM APP_REFERRAL_TABLE a INNER JOIN APP_HOSP_DEPT b ON a.REF_IN_ORG_ID=b.ID " +
                    "WHERE b.HOSP_AREA_CODE LIKE:areaCode AND a.REF_STATE=:REF_STATE " +
                    "AND a.REF_APPLY_TIME>=:startTime AND a.REF_APPLY_TIME<=:endTime GROUP BY a.REF_PATIENT_ID,a.REF_IN_ORG_ID) aa";

            //其他原因转出量 其他原因转回给下级医疗机构的患者数量
            int sjqtzcl = 0;//上级其他转出量
            String sqlQtzcl = "SELECT count(1) FROM APP_REFERRAL_TABLE a INNER JOIN APP_HOSP_DEPT b ON a.REF_IN_ORG_ID=b.ID " +
                    "WHERE a.REF_TYPE=:REF_TYPE AND b.HOSP_AREA_CODE LIKE:areaCode AND a.REF_STATE!=:REF_STATE " +
                    "AND a.REF_APPLY_TIME>=:startTime AND a.REF_APPLY_TIME<=:endTime";

            //其他原因转出数 其他原因转回给下级医疗机构的患者人数
            int sjqtzcrs = 0;
            String sqlQtzcs = "SELECT count(1) FROM (SELECT a.* FROM APP_REFERRAL_TABLE a INNER JOIN APP_HOSP_DEPT b ON a.REF_IN_ORG_ID=b.ID " +
                    "WHERE a.REF_TYPE=:REF_TYPE AND b.HOSP_AREA_CODE LIKE:areaCode AND a.REF_STATE!=:REF_STATE " +
                    "AND a.REF_APPLY_TIME>=:startTime AND a.REF_APPLY_TIME<=:endTime GROUP BY a.REF_PATIENT_ID,a.REF_IN_ORG_ID) aa";

            jczcrs = sysDao.getServiceReadDo().findCount(sqlZcrs,map);
            jczczs = sysDao.getServiceReadDo().findCount(sqlZczs,map);
            jczhl = sysDao.getServiceReadDo().findCount(sqlZhl,map);
            jckfzhrs = sysDao.getServiceReadDo().findCount(sqlKfzhrs,map);
            returnMap.put("jczczs",jczczs);//基层医院转出量
            returnMap.put("jczcrs",jczcrs);//基层医院转出人数
            returnMap.put("jczhl",jczhl);//基层医院转回量
            returnMap.put("jckfzhrs",jckfzhrs);//基层医院康复转回人数
            //转出率 转出数/统计年度签约的居民数
            double jczcl = 0;
            if(qyzs!=0){
                jczcl = MyMathUtil.div(Double.valueOf(jczcrs),Double.valueOf(qyzs),6)*100;
                jczcl = MyMathUtil.round(jczcl,2);
            }
            returnMap.put("jczcl",String.valueOf(jczcl));//基层转出率

            //康复转回率 康复转回人数/转回总人数
            double jckfzhl = 0;
            if(jczhl!=0){
                jckfzhl = MyMathUtil.div(Double.valueOf(jckfzhrs),Double.valueOf(jczhl),6)*100;
                jckfzhl = MyMathUtil.round(jckfzhl,2);
            }
            returnMap.put("jckfzhl",String.valueOf(jckfzhl));


            sjzrl = sysDao.getServiceReadDo().findCount(sqlZrl,map);
            returnMap.put("sjzrl",sjzrl);

            sjzrrs = sysDao.getServiceReadDo().findCount(sqlZrrs,map);
            returnMap.put("sjzrrs",sjzrrs);


            sjkfzczl = sysDao.getServiceReadDo().findCount(sqlKfzczl,map);
            returnMap.put("sjkfzczl",sjkfzczl);


            sjkfzcrs = sysDao.getServiceReadDo().findCount(sqlKfzcrs,map);
            returnMap.put("sjkfzcrs",sjkfzcrs);

            sjqtzcl = sysDao.getServiceReadDo().findCount(sqlQtzcl,map);
            returnMap.put("sjqtzcl",sjqtzcl);


            sjqtzcrs = sysDao.getServiceReadDo().findCount(sqlQtzcs,map);
            returnMap.put("sjqtzcrs",sjqtzcrs);

            //上级康复转回率
            int sjzhl = sjqtzcrs+sjkfzcrs;
            double sjkfzhl = 0;
            //上级终结转回率
            double sjqtzhl = 0;
            if(sjzhl!=0){
                sjkfzhl = MyMathUtil.div(Double.valueOf(sjkfzcrs),Double.valueOf(sjzhl),6)*100;
                sjqtzhl = MyMathUtil.div(Double.valueOf(sjqtzcrs),Double.valueOf(sjzhl),6)*100;
                sjkfzhl = MyMathUtil.round(sjkfzhl,2);
                sjqtzhl = MyMathUtil.round(sjqtzhl,2);
            }
            returnMap.put("sjkfzhl",String.valueOf(sjkfzhl));
            returnMap.put("sjqtzhl",String.valueOf(sjqtzhl));


//            }else{

//            }
        }
        if(StringUtils.isNotBlank(qvo.getHospId())){//医院转诊统计
            AppHospDept dept = (AppHospDept)sysDao.getServiceReadDo().find(AppHospDept.class,qvo.getHospId());
            map.put("hospId",qvo.getHospId());
            if(dept!=null){
                if("7".equals(dept.getHospLevelType())||"8".equals(dept.getHospLevelType())||"9".equals(dept.getHospLevelType())){//基层医院统计
                    //转出总量 上转到上级医院的患者数量（人次）。被接收的数量
                    int zczl = 0;
                    String sqlZczl =sql+ " AND a.REF_OUT_ORG_ID=:hospId and a.REF_STATE NOT IN (:refState) ";
                    zczl = sysDao.getServiceReadDo().findCount(sqlZczl,map);
                    returnMap.put("jczczs",zczl);

                    //转出人数	上转到上级医院的患者人数。被接收的人数
                    int zcrs = 0;
                    String sqlZcrs = "SELECT count(1) FROM ("+sql + " AND a.REF_OUT_ORG_ID=:hospId and a.REF_STATE NOT IN (:refState)" +
                            " GROUP BY a.REF_PATIENT_ID,a.REF_OUT_ORG_ID) c";
                    zcrs = sysDao.getServiceReadDo().findCount(sqlZcrs,map);
                    returnMap.put("jczcrs",zcrs);

                    //本年度签约居民数
                    int qyzs = 0;
                    Calendar cal = Calendar.getInstance();
                    map.put("year",cal.get(Calendar.YEAR));
                    String sqlQyzs = "SELECT SUM(MANAGE_TOTAL_RENEW) MANAGE_TOTAL_RENEW FROM APP_MANAGE_COUNT WHERE MANAGE_HOSP_ID = :hospId AND MANAGE_YEAR=:year";
                    List<Map> listQy = sysDao.getServiceReadDo().findSqlMap(sqlQyzs,map);
                    if(listQy!=null && listQy.size()>0){
                        if(listQy.get(0).get("MANAGE_TOTAL_RENEW")!=null) {
                            qyzs = (int)Double.parseDouble(listQy.get(0).get("MANAGE_TOTAL_RENEW").toString());
                        }
                    }
                    returnMap.put("qyzs",qyzs);

                    //转出率	转出数/统计年度签约的居民数
                    double zcl = 0;
                    if(qyzs!=0){
                        zcl = MyMathUtil.div(Double.valueOf(zcrs),Double.valueOf(qyzs),6)*100;
                        zcl = MyMathUtil.round(zcl,2);
                    }
                    returnMap.put("jczcl",String.valueOf(zcl));

                    //转回量	上级医院转回的患者数量
                    int zhl = 0;
                    String sqlZhl = sql + " AND a.REF_OUT_ORG_ID=:hospId AND a.REF_TYPE=:REF_TYPE ";
                    zhl = sysDao.getServiceReadDo().findCount(sqlZhl,map);
                    returnMap.put("jczhl",zhl);

                    //康复转回人数	上级医院转回的“康复转回”人数
                    int kfzhrs = 0;
                    String sqlKfzhrs = "SELECT count(1) FROM ("+sql + " AND a.REF_OUT_ORG_ID=:hospId AND a.REF_TYPE=:REF_TYPE AND a.REF_STATE=:REF_STATE" +
                            " GROUP BY a.REF_PATIENT_ID,a.REF_OUT_ORG_ID) c";
                    kfzhrs = sysDao.getServiceReadDo().findCount(sqlKfzhrs,map);
                    returnMap.put("jckfzhrs",kfzhrs);

                    //转回总人数
                    int zhzrs = 0;
                    String sqlZhzrs = "SELECT count(1) FROM ("+sql + " AND a.REF_OUT_ORG_ID=:hospId AND a.REF_TYPE=:REF_TYPE GROUP BY a.REF_PATIENT_ID,a.REF_OUT_ORG_ID) c";
                    zhzrs = sysDao.getServiceReadDo().findCount(sqlZhzrs,map);
                    //康复转回率	康复转回人数/转回总人数
                    double kfzhl= 0;
                    if(zhzrs!=0){
                        kfzhl = MyMathUtil.div(Double.valueOf(kfzhrs),Double.valueOf(zhzrs),6)*100;
                        kfzhl = MyMathUtil.round(kfzhl,2);
                    }
                    returnMap.put("jckfzhl",String.valueOf(kfzhl));
                }
                if("0".equals(dept.getHospLevelType())||"1".equals(dept.getHospLevelType())||"2".equals(dept.getHospLevelType())||
                        "3".equals(dept.getHospLevelType())||"4".equals(dept.getHospLevelType())||"5".equals(dept.getHospLevelType())||"6".equals(dept.getHospLevelType())){//上级医院统计
                    //转入总量	接收下级医院上转的患者数量（人次）。同意的数量
                    int zrzl = 0;
                    String sqlZrzl = sql + " AND a.REF_IN_ORG_ID=:hospId AND a.REF_STATE NOT IN (:refState) ";
                    zrzl = sysDao.getServiceReadDo().findCount(sqlZrzl,map);
                    returnMap.put("sjzrl",zrzl);

                    //转入人数	接收下级医院上转的患者人数（人次）。同意的人数
                    int zrrs = 0;
                    String sqlZrrs ="SELECT count(1) FROM ("+ sql + " AND a.REF_IN_ORG_ID=:hospId AND a.REF_STATE NOT IN (:refState)" +
                            " GROUP BY a.REF_PATIENT_ID,a.REF_IN_ORG_ID) c";
                    zrrs = sysDao.getServiceReadDo().findCount(sqlZrrs,map);
                    returnMap.put("sjzrrs",zrrs);

                    //康复转出总量	康复转回给下级医疗机构的患者数量
                    int kfzczl = 0;
                    String sqlKfzczl = sql + " AND a.REF_IN_ORG_ID=:hospId AND a.REF_TYPE=:REF_TYPE AND a.REF_STATE=:REF_STATE ";
                    kfzczl = sysDao.getServiceReadDo().findCount(sqlKfzczl,map);
                    returnMap.put("kfzzzl",kfzczl);

                    //康复转出人数	康复转回给下级医疗机构的患者人数
                    int kfzcrs = 0;
                    String sqlKfzcrs ="SELECT count(1) FROM ("+ sql + " AND a.REF_IN_ORG_ID=:hospId AND a.REF_TYPE=:REF_TYPE AND a.REF_STATE=:REF_STATE " +
                            "GROUP BY a.REF_PATIENT_ID,a.REF_IN_ORG_ID) c";
                    kfzcrs = sysDao.getServiceReadDo().findCount(sqlKfzcrs,map);
                    returnMap.put("sjkfzcrs",kfzcrs);

                    //其他原因转出量	其他原因转回给下级医疗机构的患者数量
                    int qtzcl = 0;
                    String sqlQtzcl = sql + " AND a.REF_IN_ORG_ID=:hospId AND a.REF_TYPE=:REF_TYPE AND a.REF_STATE !=:REF_STATE";
                    qtzcl = sysDao.getServiceReadDo().findCount(sqlQtzcl,map);
                    returnMap.put("qtzcl",qtzcl);

                    //其他原因转出数	其他原因转回给下级医疗机构的患者人数
                    int qtzcs = 0;
                    String sqlQtzcs = "SELECT count(1) FROM ("+sql +" AND a.REF_IN_ORG_ID=:hospId AND a.REF_TYPE=:REF_TYPE AND a.REF_STATE !=:REF_STATE " +
                            "GROUP BY a.REF_PATIENT_ID,a.REF_IN_ORG_ID) c";
                    qtzcs = sysDao.getServiceReadDo().findCount(sqlQtzcs,map);
                    returnMap.put("sjqtzcrs",qtzcs);
                    //上级康复转回率
                    int sjzhl = qtzcs+kfzcrs;
                    double sjkfzhl = 0;
                    //上级终结转回率
                    double sjqtzhl = 0;
                    if(sjzhl!=0){
                        sjkfzhl = MyMathUtil.div(Double.valueOf(kfzcrs),Double.valueOf(sjzhl),6)*100;
                        sjqtzhl = MyMathUtil.div(Double.valueOf(qtzcs),Double.valueOf(sjzhl),6)*100;
                        sjkfzhl = MyMathUtil.round(sjkfzhl,2);
                        sjqtzhl = MyMathUtil.round(sjqtzhl,2);
                    }
                    returnMap.put("sjkfzhl",String.valueOf(sjkfzhl));
                    returnMap.put("sjqtzhl",String.valueOf(sjqtzhl));
                }
            }
        }
        //钻取数据
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();//签约
        String result = null;
        if(StringUtils.isNotBlank(qvo.getAreaId())){
            if(AreaUtils.getAreaCode(qvo.getAreaId()).length()==6){
                //区下的医院集合
                List<AppHospDept> hospDepts = sysDao.getServiceReadDo().loadByPk(AppHospDept.class, "hospUpcityareaId", qvo.getAreaId());
                if(hospDepts != null && hospDepts.size() >0){
                    for (AppHospDept hosp : hospDepts){
                        CdAddress address = this.sysDao.getCdAddressDao().findByCacheCode(hosp.getHospAreaCode());
                        Map<String, Object> mapp = getReferralCount(null,hosp, qvo);
                       /* Map<String, Object> mapp = new HashMap<>();
                        mapp.put("hospId",hosp.getId());//区域编码
                        mapp.put("hospName",hosp.getHospName());//名称
                        mapp.put("level",hosp.getHospLevelType());*/
                        list.add(mapp);
                    }
                }
                result = "1";
            }else{
                List<CdAddress> lsCdAddress = sysDao.getCdAddressDao().findUpCasheAreaCode(qvo.getAreaId());
                if(lsCdAddress != null && lsCdAddress.size()>0){
                    for (CdAddress address : lsCdAddress) {
                        Map<String, Object> mapp = getReferralCount(address,null, qvo);
                        list.add(mapp);
                    }
                }
                result = "1";
            }
        }else if(StringUtils.isNotBlank(qvo.getHospId())){
            //机构查询转诊居民列表
            List<ReferralInfo> lsRef = sysDao.getAppRefarralTableDao().findByHospId(qvo);
            returnMap.put("zzlist",lsRef);
            result = "2";
        }
        Collections.sort(list, ComparatorUtils.getComparator());
        returnMap.put("zqList",list);
        returnMap.put("result",result);
        return returnMap;
    }



    /**
     * 对基卫签约首页统计接口
     * @param qvo areaId 区域主键 hospId 社区主键 teamId 团队主键
     * @return
     */
    @Override
    public Map<String,Object> findSignAnalysisIndexMotoe(ResidentVo qvo) throws Exception {
        Map<String, Object> map = new HashMap<String,Object>();
        if(StringUtils.isNotBlank(qvo.getAreaId())){
            CdAddress address = this.sysDao.getCdAddressDao().findByCacheCode(qvo.getAreaId());
            map = getSianCount(address, null,qvo.getYearStart(),qvo.getYearEnd());
        }else if(StringUtils.isNotBlank(qvo.getHospId())){
            AppHospDept hospDept = (AppHospDept)this.sysDao.getServiceReadDo().find(AppHospDept.class,qvo.getHospId());
            if(hospDept != null){
                CdAddress address = this.sysDao.getCdAddressDao().findByCacheCode(hospDept.getHospAreaCode());
                map = getSianCount(address, hospDept,qvo.getYearStart(),qvo.getYearEnd());
            }
        }
        return map;
    }






    public Map<String,Object> getReferralCount(CdAddress address, AppHospDept hosp,ResidentVo qvo) throws Exception{
        Map<String,Object> returnMap = new HashMap<>();
        Map<String,Object> map = new HashMap<>();
        map.put("startTime",ExtendDate.getFirstDayOfMonth(qvo.getYearStart())+" 00:00:00");
        map.put("endTime",ExtendDate.getLastDayOfMonth(qvo.getYearEnd())+" 23:59:59");

        int zrl = 0;//总转诊转入量
        int zcl = 0;//总转诊转出量
        int kfzcl = 0;//康复转出量
        int zzhl = 0;//总转回量
        double kfzb = 0;//康复转出占比
        map.put("REF_TYPE", ReferralType.ZR.getValue());
        map.put("REF_STATE",ReferralState.KFHZ.getValue());
        String[] strs = new String[]{ReferralState.ZZDTG.getValue(),ReferralState.ZZYJJ.getValue(),ReferralState.ZZYGQ.getValue()};
        map.put("refState",strs);
        if(address!=null){
            map.put("areaCode",AreaUtils.getAreaCode(address.getCtcode())+"%");
            //转出量 outOrgId
            String sqlZcl =  "SELECT count(1) FROM APP_REFERRAL_TABLE a INNER JOIN APP_HOSP_DEPT b ON a.REF_OUT_ORG_ID=b.ID " +
                    "WHERE b.HOSP_AREA_CODE LIKE :areaCode AND REF_STATE NOT IN (:refState) AND" +
                    " a.REF_APPLY_TIME>=:startTime AND a.REF_APPLY_TIME<=:endTime";
            //转入量 inOrgId
            String sqlZrl = "SELECT count(1) FROM APP_REFERRAL_TABLE a INNER JOIN APP_HOSP_DEPT b ON a.REF_IN_ORG_ID=b.ID " +
                    "WHERE b.HOSP_AREA_CODE LIKE :areaCode AND REF_STATE NOT IN (:refState) " +
                    "AND a.REF_APPLY_TIME>=:startTime AND a.REF_APPLY_TIME<=:endTime";
            //康复转出量 inOrgId refState=5
            String sqlKfzcl ="SELECT count(1) FROM APP_REFERRAL_TABLE a INNER JOIN APP_HOSP_DEPT b ON a.REF_IN_ORG_ID=b.ID " +
                    "WHERE b.HOSP_AREA_CODE LIKE :areaCode AND REF_STATE=:REF_STATE " +
                    "AND a.REF_APPLY_TIME>=:startTime AND a.REF_APPLY_TIME<=:endTime";
            String sqlZzhl = " SELECT count(1) FROM APP_REFERRAL_TABLE a INNER JOIN APP_HOSP_DEPT b ON a.REF_IN_ORG_ID=b.ID " +
                    "WHERE b.HOSP_AREA_CODE LIKE :areaCode AND REF_TYPE=:REF_TYPE " +
                    "AND a.REF_APPLY_TIME>=:startTime AND a.REF_APPLY_TIME<=:endTime";
            zrl = sysDao.getServiceReadDo().findCount(sqlZrl,map);
            zcl = sysDao.getServiceReadDo().findCount(sqlZcl,map);
            kfzcl = sysDao.getServiceReadDo().findCount(sqlKfzcl,map);
            zzhl = sysDao.getServiceReadDo().findCount(sqlZzhl,map);
            if(zzhl!=0){
                kfzb =MyMathUtil.div(Double.valueOf(kfzcl),Double.valueOf(zzhl),6)*100;//康复转出占比
                kfzb = MyMathUtil.round(kfzb,2);
            }
            returnMap.put("areaName",address.getAreaSname());
            returnMap.put("areaCode",address.getCtcode());

        }else if(hosp != null){
            map.put("hospId",hosp.getId());
            //转出量 outOrgId
            String sqlZcl =  "SELECT count(1) FROM APP_REFERRAL_TABLE a INNER JOIN APP_HOSP_DEPT b ON a.REF_OUT_ORG_ID=b.ID " +
                    "WHERE b.ID = :hospId AND REF_STATE NOT IN (:refState) AND" +
                    " a.REF_APPLY_TIME>=:startTime AND a.REF_APPLY_TIME<=:endTime";
            //转入量 inOrgId
            String sqlZrl = "SELECT count(1) FROM APP_REFERRAL_TABLE a INNER JOIN APP_HOSP_DEPT b ON a.REF_IN_ORG_ID=b.ID " +
                    "WHERE b.ID = :hospId AND REF_STATE NOT IN (:refState) " +
                    "AND a.REF_APPLY_TIME>=:startTime AND a.REF_APPLY_TIME<=:endTime";
            //康复转出量 inOrgId refState=5
            String sqlKfzcl ="SELECT count(1) FROM APP_REFERRAL_TABLE a INNER JOIN APP_HOSP_DEPT b ON a.REF_IN_ORG_ID=b.ID " +
                    "WHERE b.ID = :hospId AND REF_STATE=:REF_STATE " +
                    "AND a.REF_APPLY_TIME>=:startTime AND a.REF_APPLY_TIME<=:endTime";
            String sqlZzhl = " SELECT count(1) FROM APP_REFERRAL_TABLE a INNER JOIN APP_HOSP_DEPT b ON a.REF_IN_ORG_ID=b.ID " +
                    "WHERE b.ID = :hospId LIKE :areaCode AND REF_TYPE=:REF_TYPE " +
                    "AND a.REF_APPLY_TIME>=:startTime AND a.REF_APPLY_TIME<=:endTime";
            zrl = sysDao.getServiceReadDo().findCount(sqlZrl,map);
            zcl = sysDao.getServiceReadDo().findCount(sqlZcl,map);
            kfzcl = sysDao.getServiceReadDo().findCount(sqlKfzcl,map);
            zzhl = sysDao.getServiceReadDo().findCount(sqlZzhl,map);
            if(zzhl!=0){
                kfzb =MyMathUtil.div(Double.valueOf(kfzcl),Double.valueOf(zzhl),4)*100;//康复转出占比
                kfzb = MyMathUtil.round(kfzb,2);
            }
            returnMap.put("hospId",hosp.getId());//区域编码
            returnMap.put("hospName",hosp.getHospName());//名称
            returnMap.put("level",hosp.getHospLevelType());
        }

        returnMap.put("kfzb",String.valueOf(kfzb));
        returnMap.put("zzrl",zrl);
        returnMap.put("zzcl",zcl);
        returnMap.put("kfzcl",kfzcl);
        return returnMap;
    }

    /**
     * 服务续签人群
     * @param qvo
     * @return
     */
    @Override
    public Map<String, Object> findServeRenew(ResidentVo qvo) throws Exception {
        HashMap rmap = new HashMap();
        List<AppLabelManage> ls = this.sysDao.getAppLabelManageDao().findByType("3");
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("yearStart", ExtendDate.getFirstDayOfMonth(qvo.getYearStart())+" 00:00:00");
        map.put("yearEnd",ExtendDate.getLastDayOfMonth(qvo.getYearEnd())+" 23:59:59");
//        map.put("yearEnd","2018-10-18 23:59:59");
        String[] qys = {SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
        map.put("signState",qys);
        map.put("contractState", CommSF.YES.getValue());
        String sql = "SELECT\n" +
                "\tCOUNT(1)\n" +
                "FROM\n" +
                "\tAPP_SIGN_FORM a LEFT JOIN APP_LABEL_GROUP b on a.ID=b.LABEL_SIGN_ID \n" +
                "WHERE 1=1\n" +
                "\t AND a.SIGN_RENEWORGOTOSIGN_DATE >= :yearStart\n" +
                "AND a.SIGN_RENEWORGOTOSIGN_DATE <= :yearEnd\n" +
                "AND a.SIGN_GOTO_SIGN_STATE='2'";
//                "AND SIGN_STATE = :signState\n" +
//                "AND SIGN_CONTRACT_STATE = :contractState";
        if(StringUtils.isNotBlank(qvo.getAreaId())){
            map.put("areaCode",qvo.getAreaId()+"%");
            sql += " AND a.SIGN_AREA_CODE LIKE :areaCode ";
            sql += " AND b.LABEL_VALUE=:SIGN_PERS_GROUP ";
        }
        if(StringUtils.isNotBlank(qvo.getHospId())){
            map.put("hospId",qvo.getHospId());
            sql += " AND a.SIGN_HOSP_ID =:hospId";
            sql += " AND b.LABEL_VALUE=:SIGN_PERS_GROUP";
        }
        if(StringUtils.isNotBlank(qvo.getTeamId())){
            map.put("teamId",qvo.getTeamId());
            sql += " AND a.SIGN_TEAM_ID =:teamId";
            sql += " AND b.LABEL_VALUE=:SIGN_PERS_GROUP";
        }
        for(AppLabelManage l:ls) {
            map.put("SIGN_PERS_GROUP",l.getLabelValue());
            int  count= sysDao.getServiceReadDo().findCount(sql, map);
            if(l.getLabelValue().equals("1")){//普通人群
                rmap.put("manageRenewPlainCount", count);
            }else if(l.getLabelValue().equals("2")){//儿童(0-6岁)
                rmap.put("manageRenewChildCount", count);
            }else if(l.getLabelValue().equals("3")){//孕产妇
                rmap.put("manageRenewMaternalCount", count);
            }else if(l.getLabelValue().equals("4")){//老年人
                rmap.put("manageRenewOldPeopleCount", count);
            }else if(l.getLabelValue().equals("5")){//高血压
                rmap.put("manageRenewBloodCount", count);
            }else if(l.getLabelValue().equals("6")){//糖尿病
                rmap.put("manageRenewDiabetesCount", count);
            }else if(l.getLabelValue().equals("7")){//严重精神障碍
                rmap.put("manageRenewPsychosisCount", count);
            }else if(l.getLabelValue().equals("8")){//结核病
                rmap.put("manageRenewPhthisisCount", count);
            }else if(l.getLabelValue().equals("9")){//残疾人
                rmap.put("manageRenewDisabledPeopleCount", count);
            }else if(l.getLabelValue().equals("99")){//其他
                rmap.put("manageRenewServiceUnknownCount", count);
            }else if(l.getLabelValue().equals("10")){//脑血管
                rmap.put("manageRenewNxgCount", count);
            }else if(l.getLabelValue().equals("11")){//冠心病
                rmap.put("manageRenewGxbCount", count);
            }else if(l.getLabelValue().equals("12")){//癌症
                rmap.put("manageRenewAzCount", count);
            }

        }
        return rmap;
    }

    /**
     * 续签统计
     * @param qvo
     * @return
     */
    @Override
    public Map<String, Object> findRenewSign(ResidentVo qvo) throws Exception {

        Map<String,Object> returnMap = new HashMap<>();
        //续签人数
        int renewCount = 0;
        //上一年度签约人数
        int upSignCount = 0;
        //续签率
        double renewRate = 0;
        //普通人续签人数
        int ptRenewCount = 0;
        //儿童续签人数
        int etRenewCount = 0;
        //老年人续签人数
        int lnrRenewCount = 0;
        //高血压续签人数
        int gxyRenewCount = 0;
        //结核病续签人数
        int jhbRenewCount = 0;
        //糖尿病续签人数
        int tnbRenewCount = 0;
        //精神病续签人数
        int jsbRenewCount = 0;
        //残疾人续签人数
        int cjRenewCount = 0;
        //孕产妇续签人数
        int ycfRenewCount = 0;
        //未知服务人群续签人数
        int wzRenewCount = 0;

        Map<String,Object> map = new HashMap<>();
        map.put("year",qvo.getYearStart());
        int upYear = Integer.parseInt(qvo.getYearStart())-1;
        map.put("upYear",upYear);
        String sqlMonth = "SELECT sum(MANAGE_RENEW) renewCount FROM APP_MANAGE_COUNT WHERE 1=1";
        String sql ="SELECT " +
                "SUM(t.MANAGE_RENEW) manageRenew," +
                "SUM(t.MANAGE_RENEW_PLAIN_COUNT) manageRenewPlainCount," +
                "SUM(t.MANAGE_RENEW_CHILD_COUNT) manageRenewChildCount," +
                "SUM(t.MANAGE_RENEW_MATERNAL_COUNT) manageRenewMaternalCount," +
                "SUM(t.MANAGE_RENEW_OLD_PEOPLE_COUNT) manageRenewOldPeopleCount," +
                "SUM(t.MANAGE_RENEW_BLOOD_COUNT) manageRenewBloodCount," +
                "SUM(t.MANAGE_RENEW_DIABETES_COUNT) manageRenewDiabetesCount," +
                "SUM(t.MANAGE_RENEW_PSYCHOSIS_COUNT) manageRenewPsychosisCount," +
                "SUM(t.MANAGE_RENEW_PHTHISIS_COUNT) manageRenewPhthisisCount," +
                "SUM(t.MANAGE_RENEW_DISABLED_PEOPLE_COUNT) manageRenewDisabledPeopleCount," +
                "SUM(t.MANAGE_RENEW_SERVICE_UNKNOWN_COUNT) manageRenewServiceUnknownCount," +
                "SUM(t.MANAGE_TOTAL_RENEW) manageTotalRenew "+
                " FROM APP_MANAGE_COUNT t where 1=1 ";
        sql += " AND t.MANAGE_YEAR =:year";
        String upSql = " SELECT SUM(t.MANAGE_TOTAL_RENEW) manageTotalRenew FROM APP_MANAGE_COUNT t where 1=1 and t.MANAGE_YEAR =:upYear ";
        String msql = "";
        //区域续签统计
        if(StringUtils.isNotBlank(qvo.getAreaId())){
            map.put("areaId",AreaUtils.getAreaCode(qvo.getAreaId())+"%");
            sql += " AND t.MANAGE_AREA_CODE LIKE :areaId";
            sqlMonth += " AND MANAGE_AREA_CODE LIKE :areaId";
            upSql += " AND t.MANAGE_AREA_CODE LIKE :areaId ";
            msql += " AND a.SIGN_AREA_CODE LIKE :areaId ";
        }
        //医院续签统计
        if(StringUtils.isNotBlank(qvo.getHospId())){
            map.put("hospId",qvo.getHospId());
            sql += " AND t.MANAGE_HOSP_ID = :hospId";
            sqlMonth += " AND MANAGE_HOSP_ID = :hospId";
            msql += " AND a.SIGN_HOSP_ID = :hospId";
            upSql += " AND t.MANAGE_HOSP_ID = :hospId ";
        }
        //团队续签统计
        if(StringUtils.isNotBlank(qvo.getTeamId())){
            map.put("teamId",qvo.getTeamId());
            sql += " AND t.MANAGE_TEAM_ID = :teamId";
            upSql += " AND t.MANAGE_TEAM_ID =:teamId ";
            sqlMonth += " AND MANAGE_TEAM_ID = :teamId";
            msql += " AND a.SIGN_TEAM_ID = :teamId ";
        }
        List<Map> maps = this.sysDao.getServiceReadDo().findSqlMap(sql,map);
        List<Map> upMaps = this.sysDao.getServiceReadDo().findSqlMap(upSql,map);
        if(upMaps!=null && upMaps.size()>0){
            if (upMaps.get(0).get("manageTotalRenew") != null) {
                upSignCount = (int) Double.parseDouble(upMaps.get(0).get("manageTotalRenew").toString());//上一年度签约人数
            }
        }
        if(maps != null && maps.size() >0) {
            if (maps.get(0).get("manageRenew") != null){
                renewCount = (int) Double.parseDouble(maps.get(0).get("manageRenew").toString());//续签人数
            }

            if (maps.get(0).get("manageRenewPlainCount") != null) {
                ptRenewCount = (int) Double.parseDouble(maps.get(0).get("manageRenewPlainCount").toString());//普通人群续签人数
            }

            if(maps.get(0).get("manageRenewChildCount") != null) {
                etRenewCount = (int) Double.parseDouble(maps.get(0).get("manageRenewChildCount").toString());//儿童人群续签人数
            }

            if(maps.get(0).get("manageRenewMaternalCount") != null) {
                ycfRenewCount = (int) Double.parseDouble(maps.get(0).get("manageRenewMaternalCount").toString());//孕产妇续签数人数
            }

            if(maps.get(0).get("manageRenewOldPeopleCount") != null) {
                lnrRenewCount = (int) Double.parseDouble(maps.get(0).get("manageRenewOldPeopleCount").toString());//老年人续签人数
            }

            if(maps.get(0).get("manageRenewBloodCount") != null) {
                gxyRenewCount = (int) Double.parseDouble(maps.get(0).get("manageRenewBloodCount").toString());//高血压续签人数
            }

            if(maps.get(0).get("manageRenewDiabetesCount") != null) {
                tnbRenewCount = (int) Double.parseDouble(maps.get(0).get("manageRenewDiabetesCount").toString());//糖尿病续签人数
            }

            if(maps.get(0).get("manageRenewPsychosisCount") != null) {
                jsbRenewCount = (int) Double.parseDouble(maps.get(0).get("manageRenewPsychosisCount").toString());//严重精神病患者续签人数
            }

            if(maps.get(0).get("manageRenewPhthisisCount") != null) {
                jhbRenewCount = (int) Double.parseDouble(maps.get(0).get("manageRenewPhthisisCount").toString());//结核病续签人数
            }

            if(maps.get(0).get("manageRenewDisabledPeopleCount") != null) {
                cjRenewCount = (int) Double.parseDouble(maps.get(0).get("manageRenewDisabledPeopleCount").toString());//残疾续签人数
            }

            if(maps.get(0).get("manageRenewServiceUnknownCount") != null) {
                wzRenewCount = (int) Double.parseDouble(maps.get(0).get("manageRenewServiceUnknownCount").toString());//未知人群续签人数
            }
        }
        String time = ExtendDate.getYMD(Calendar.getInstance());
        String nowYear = time.substring(0,4);
        int today = 0;
        if(qvo.getYearStart().equals(nowYear)){
            time+= " 00:00:00";
            map.put("today",time);
            String toDaySql = " SELECT COUNT(1) FROM APP_SIGN_FORM a WHERE a.SIGN_GOTO_SIGN_STATE = '2' " +
                    "AND a.SIGN_RENEWORGOTOSIGN_DATE<=SYSDATE() " +
                    " AND a.SIGN_RENEWORGOTOSIGN_DATE>=:today ";
            String fwSql = "SELECT\n" +
                    "\tCOUNT(1)\n" +
                    "FROM\n" +
                    "\tAPP_SIGN_FORM a LEFT JOIN APP_LABEL_GROUP b on a.ID = b.LABEL_SIGN_ID\n" +
                    "WHERE\n" +
                    "\ta.SIGN_GOTO_SIGN_STATE = '2' \n" +
                    "AND a.SIGN_RENEWORGOTOSIGN_DATE <= SYSDATE()\n" +
                    "AND a.SIGN_RENEWORGOTOSIGN_DATE >=:today\n" +
                    "AND b.LABEL_VALUE = :fwValue AND b.LABEL_TYPE = '3'";
            //今天之前的续签人数加上今天的人数
            toDaySql+=msql;
            today = sysDao.getServiceReadDo().findCount(toDaySql,map);
            renewCount+= today;
            fwSql+=msql;
            map.put("fwValue",ResidentMangeType.PTRQ.getValue());
            int ptRenew = sysDao.getServiceReadDo().findCount(fwSql,map);
            ptRenewCount+=ptRenew;

            map.put("fwValue",ResidentMangeType.ETLZLS.getValue());
            int etRenew = sysDao.getServiceReadDo().findCount(fwSql,map);
            etRenewCount += etRenew;

            map.put("fwValue",ResidentMangeType.YCF.getValue());
            int ycfRenew = sysDao.getServiceReadDo().findCount(fwSql,map);
            ycfRenewCount += ycfRenew;

            map.put("fwValue",ResidentMangeType.LNR.getValue());
            int lnrRenew = sysDao.getServiceReadDo().findCount(fwSql,map);
            lnrRenewCount +=lnrRenew;

            map.put("fwValue",ResidentMangeType.GXY.getValue());
            int gxyRenew = sysDao.getServiceReadDo().findCount(fwSql,map);
            gxyRenewCount+=gxyRenew;

            map.put("fwValue",ResidentMangeType.TNB.getValue());
            int tnbRenew = sysDao.getServiceReadDo().findCount(fwSql,map);
            tnbRenewCount += tnbRenew;

            map.put("fwValue",ResidentMangeType.YZJSZY.getValue());
            int jsbRenew = sysDao.getServiceReadDo().findCount(fwSql,map);
            jsbRenewCount += jsbRenew;

            map.put("fwValue",ResidentMangeType.JHB.getValue());
            int jhbRenew = sysDao.getServiceReadDo().findCount(fwSql,map);
            jhbRenewCount += jhbRenew;

            map.put("fwValue",ResidentMangeType.CJR.getValue());
            int cjrRenew = sysDao.getServiceReadDo().findCount(fwSql,map);
            cjRenewCount += cjrRenew;

            map.put("fwValue",ResidentMangeType.WEIZHI.getValue());
            int wzRenew = sysDao.getServiceReadDo().findCount(fwSql,map);
            wzRenewCount += wzRenew;
        }

        returnMap.put("renewCount",renewCount);
        returnMap.put("upSignCount",upSignCount);
        returnMap.put("ptRenewCount",ptRenewCount);
        returnMap.put("etRenewCount",etRenewCount);
        returnMap.put("ycfRenewCount",ycfRenewCount);
        returnMap.put("lnrRenewCount",lnrRenewCount);
        returnMap.put("gxyRenewCount",gxyRenewCount);
        returnMap.put("tnbRenewCount",tnbRenewCount);
        returnMap.put("jsbRenewCount",jsbRenewCount);
        returnMap.put("jhbRenewCount",jhbRenewCount);
        returnMap.put("cjRenewCount",cjRenewCount);
        returnMap.put("wzRenewCount",wzRenewCount);
        //续签率
        if(upSignCount!=0){
            renewRate = MyMathUtil.div(Double.valueOf(renewCount),Double.valueOf(upSignCount),4)*100;//续签率
        }
        //每月的续签数
        List<String> listMonth = ExtendDateUtil.getListBetweenMonthYear(qvo.getYearStart());
        if(listMonth!=null && listMonth.size()>0){
            Map<String,Object> monthMap = new LinkedHashMap<>();
            for(String lm:listMonth){
                map.put("month",lm);
                String ss = sqlMonth+" AND MANAGE_YEAR_MONTH=:month ";
                List<Map> monthMaps = this.sysDao.getServiceReadDo().findSqlMap(ss,map);
                int renewCountM = 0;
                if(monthMaps!=null && monthMaps.size()>0){
                    if (monthMaps.get(0).get("renewCount") != null){
                        renewCountM = (int) Double.parseDouble(monthMaps.get(0).get("renewCount").toString());
                    }
                }
                if(qvo.getYearStart().equals(nowYear)){//当前年份
                    String ym = time.substring(0,7);
                    if(lm.equals(ym)){
                        renewCountM +=today;
                    }
                }
                monthMap.put("month"+lm.substring(lm.length()-2,lm.length()),renewCountM);
            }
            returnMap.put("mList",monthMap);
        }

        //钻取数据
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();//签约
        String result = null;
        if(StringUtils.isNotBlank(qvo.getAreaId())){
            if (AreaUtils.getAreaCode(qvo.getAreaId()).length() == 6){//区级行政，往下查看医院的排名
                //区下的医院集合
                List<AppHospDept> hospDepts = sysDao.getServiceReadDo().loadByPk(AppHospDept.class, "hospUpcityareaId", qvo.getAreaId());
                if(hospDepts != null && hospDepts.size() >0){
                    for (AppHospDept hosp : hospDepts){
                        CdAddress address = this.sysDao.getCdAddressDao().findByCacheCode(hosp.getHospAreaCode());
                        Map<String, Object> mapp = getRenewSianCount(address, hosp,null,qvo);
                        list.add(mapp);
                    }
                }
            }else{
                List<CdAddress> lsCdAddress = sysDao.getCdAddressDao().findUpCasheAreaCode(qvo.getAreaId());
                if(lsCdAddress != null && lsCdAddress.size()>0){
                    for (CdAddress address : lsCdAddress) {
                        Map<String, Object> mapp = getRenewSianCount(address, null,null,qvo);
                        list.add(mapp);
                    }
                }
            }
            result = "1";
        }else if(StringUtils.isNotBlank(qvo.getHospId())){
            List<AppTeam> lsTeam = sysDao.getAppTeamDao().findAll(qvo.getHospId());
            if(lsTeam != null && lsTeam.size()>0){
                for(AppTeam team : lsTeam){
                    Map<String, Object> mapp = getRenewSianCount(null,null,team,qvo);
                    list.add(mapp);
                }
            }
            result = "2";
        }
        returnMap.put("xqLists",list);

        return returnMap;
    }
    public Map<String,Object> getRenewSianCount(CdAddress address,AppHospDept hosp,AppTeam team,ResidentVo qvo) throws Exception{
        Map<String,Object> returnMap = new HashMap<>();
        //续签人数
        int renewCount = 0;
        //上一年度签约人数
        int upSignCount = 0;
        //续签率
        double renewRate = 0;
        Map<String,Object> map = new HashMap<>();
        map.put("year",qvo.getYearStart());
        int upYear = Integer.parseInt(qvo.getYearStart())-1;
        map.put("upYear",upYear);
        String sql ="SELECT " +
                "SUM(t.MANAGE_RENEW) manageRenew," +
                "SUM(t.MANAGE_TOTAL_RENEW) manageTotalRenew "+
                " FROM APP_MANAGE_COUNT t where 1=1 ";
        sql += " AND t.MANAGE_YEAR =:year";
        String upSql = " SELECT SUM(t.MANAGE_TOTAL_RENEW) manageTotalRenew FROM APP_MANAGE_COUNT t where 1=1 and t.MANAGE_YEAR =:upYear ";
        String msql = "";
        //区域续签统计
        if(address!=null){
            map.put("areaId",AreaUtils.getAreaCode(address.getCtcode())+"%");
            sql += " AND t.MANAGE_AREA_CODE LIKE :areaId";
            upSql += " AND t.MANAGE_AREA_CODE LIKE :areaId";
            msql += " AND a.SIGN_AREA_CODE LIKE :areaId ";
            returnMap.put("areaCode",address.getCtcode());
            returnMap.put("areaName",address.getAreaSname());
        }
        //医院续签统计
        if(hosp!=null){
            map.put("hospId",hosp.getId());
            sql += " AND t.MANAGE_HOSP_ID = :hospId";
            upSql+= " AND t.MANAGE_HOSP_ID = :hospId";
            msql += " AND a.SIGN_HOSP_ID = :hospId ";
            returnMap.put("hospId",hosp.getId());
            returnMap.put("hospName",hosp.getHospName());
        }
        //团队续签统计
        if(team!=null){
            map.put("teamId",team.getId());
            sql += " AND t.MANAGE_TEAM_ID = :teamId";
            upSql+= " AND t.MANAGE_TEAM_ID = :teamId";
            msql += " AND a.SIGN_TEAM_ID = :teamId ";
            returnMap.put("teamId",team.getId());
            returnMap.put("teamName",team.getTeamName());
        }
        List<Map> maps = this.sysDao.getServiceReadDo().findSqlMap(sql,map);
        List<Map> upMaps = this.sysDao.getServiceReadDo().findSqlMap(upSql,map);
        if(upMaps!=null && upMaps.size()>0){
            if(upMaps.get(0).get("manageTotalRenew")!=null){
                upSignCount = (int) Double.parseDouble(upMaps.get(0).get("manageTotalRenew").toString());
            }
        }
        if(maps!=null && maps.size()>0){
            if(maps.get(0).get("manageRenew")!=null){
                renewCount = (int) Double.parseDouble(maps.get(0).get("manageRenew").toString());
            }

        }
        String time = ExtendDate.getYMD(Calendar.getInstance());
        String nowYear = time.substring(0,4);
        int today = 0;
        if(qvo.getYearStart().equals(nowYear)) {
            time += " 00:00:00";
            map.put("today", time);
            String toDaySql = " SELECT COUNT(1) FROM APP_SIGN_FORM a WHERE a.SIGN_GOTO_SIGN_STATE = '2' " +
                    "AND a.SIGN_RENEWORGOTOSIGN_DATE<=SYSDATE() " +
                    " AND a.SIGN_RENEWORGOTOSIGN_DATE>=:today ";
            //今天之前的续签人数加上今天的人数
            toDaySql += msql;
            today = sysDao.getServiceReadDo().findCount(toDaySql, map);
            renewCount += today;
        }
        //续签率
        if(upSignCount!=0){
            renewRate = MyMathUtil.div(Double.valueOf(renewCount),Double.valueOf(upSignCount),4)*100;//康复转出占比
        }
        returnMap.put("renewCount",renewCount);
        returnMap.put("upSignCount",upSignCount);
        returnMap.put("renewRate",renewRate);
        return returnMap;
    }

    /**
     * 转签数
     * @param qvo
     * @return
     */
    @Override
    public Map<String, Object> findGoToSignT(ResidentVo qvo) throws Exception {
        HashMap rmap = new HashMap();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("yearStart", ExtendDate.getFirstDayOfMonth(qvo.getYearStart())+" 00:00:00");
        map.put("yearEnd",ExtendDate.getLastDayOfMonth(qvo.getYearEnd())+" 23:59:59");
//        map.put("yearEnd","2018-10-18 23:59:59");
        map.put("contractState", CommSF.YES.getValue());
        String sql = "SELECT\n" +
                "\tCOUNT(1)\n" +
                "FROM\n" +
                "\tAPP_SIGN_FORM a INNER JOIN APP_GOTOSIGN_RECORD b ON a.ID = b.GTS_OLD_SIGN_ID\n" +
                "WHERE 1=1\n" +
                "\t AND a.SIGN_RENEWORGOTOSIGN_DATE >= :yearStart\n" +
                "AND a.SIGN_RENEWORGOTOSIGN_DATE <= :yearEnd\n" +
                "AND a.SIGN_GOTO_SIGN_STATE='1'\n" +
                "AND b.GTS_SIGN_STATE='1' ";
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
        int manageGoToSignCount = this.sysDao.getServiceReadDo().findCount(sql,map);
        rmap.put("manageGoToSignCount",manageGoToSignCount);
        return rmap;
    }

    /**
     * ]跨区转签数
     * @param qvo
     * @return
     */
    @Override
    public Map<String, Object> findGoToSignArea(ResidentVo qvo) throws Exception {
        HashMap rmap = new HashMap();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("yearStart", ExtendDate.getFirstDayOfMonth(qvo.getYearStart())+" 00:00:00");
        map.put("yearEnd",ExtendDate.getLastDayOfMonth(qvo.getYearEnd())+" 23:59:59");
//        map.put("yearEnd","2018-10-18 23:59:59");
        map.put("contractState", CommSF.YES.getValue());
        String sql = "SELECT\n" +
                "\tCOUNT(1)\n" +
                "FROM\n" +
                "\tAPP_SIGN_FORM a INNER JOIN APP_GOTOSIGN_RECORD b ON a.ID = b.GTS_OLD_SIGN_ID\n" +
                "WHERE 1=1\n" +
                "\t AND a.SIGN_RENEWORGOTOSIGN_DATE >= :yearStart\n" +
                "AND a.SIGN_RENEWORGOTOSIGN_DATE <= :yearEnd\n" +
                "AND a.SIGN_GOTO_SIGN_STATE='1'\n" +
                "AND b.GTS_SIGN_STATE='1' " +
                "AND SUBSTRING(a.SIGN_AREA_CODE,1,6) != SUBSTRING(b.GTS_AREA_CODE,1,6) ";
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
        int manageGoToSignArea = this.sysDao.getServiceReadDo().findCount(sql,map);
        rmap.put("manageGoToSignArea",manageGoToSignArea);
        return rmap;
    }

    /**
     * 跨社区转签数
     * @param qvo
     * @return
     */
    @Override
    public Map<String, Object> findGoToSignHosp(ResidentVo qvo)  throws Exception{
        HashMap rmap = new HashMap();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("yearStart", ExtendDate.getFirstDayOfMonth(qvo.getYearStart())+" 00:00:00");
        map.put("yearEnd",ExtendDate.getLastDayOfMonth(qvo.getYearEnd())+" 23:59:59");
//        map.put("yearEnd","2018-10-18 23:59:59");
        map.put("contractState", CommSF.YES.getValue());
        String sql = "SELECT\n" +
                "\tCOUNT(1)\n" +
                "FROM\n" +
                "\tAPP_SIGN_FORM a INNER JOIN APP_GOTOSIGN_RECORD b ON a.ID = b.GTS_OLD_SIGN_ID\n" +
                "WHERE 1=1\n" +
                "\t AND a.SIGN_RENEWORGOTOSIGN_DATE >= :yearStart\n" +
                "AND a.SIGN_RENEWORGOTOSIGN_DATE <= :yearEnd\n" +
                "AND a.SIGN_GOTO_SIGN_STATE='1'\n" +
                "AND b.GTS_SIGN_STATE='1' " +
                "AND a.SIGN_HOSP_ID != b.GTS_HOSP_ID ";
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
        int manageGoToSignHosp = this.sysDao.getServiceReadDo().findCount(sql,map);
        rmap.put("manageGoToSignHosp",manageGoToSignHosp);
        return rmap;
    }

    /**
     * 跨团队转签数
     * @param qvo
     * @return
     */
    @Override
    public Map<String, Object> findGoToSignTeam(ResidentVo qvo) throws Exception {
        HashMap rmap = new HashMap();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("yearStart", ExtendDate.getFirstDayOfMonth(qvo.getYearStart())+" 00:00:00");
        map.put("yearEnd",ExtendDate.getLastDayOfMonth(qvo.getYearEnd())+" 23:59:59");
//        map.put("yearEnd","2018-10-18 23:59:59");
        map.put("contractState", CommSF.YES.getValue());
        String sql = "SELECT\n" +
                "\tCOUNT(1)\n" +
                "FROM\n" +
                "\tAPP_SIGN_FORM a INNER JOIN APP_GOTOSIGN_RECORD b ON a.ID = b.GTS_OLD_SIGN_ID\n" +
                "WHERE 1=1\n" +
                "\t AND a.SIGN_RENEWORGOTOSIGN_DATE >= :yearStart\n" +
                "AND a.SIGN_RENEWORGOTOSIGN_DATE <= :yearEnd\n" +
                "AND a.SIGN_GOTO_SIGN_STATE='1'\n" +
                "AND b.GTS_SIGN_STATE='1' " +
                "AND a.SIGN_TEAM_ID != b.GTS_TEAM_ID ";
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
        int manageGoToSignTeam = this.sysDao.getServiceReadDo().findCount(sql,map);
        rmap.put("manageGoToSignTeam",manageGoToSignTeam);
        return rmap;
    }

    public Map<String,Object> getNewGotoSianCount(CdAddress address,AppHospDept hosp,AppTeam team,ResidentVo qvo) throws Exception{
        int goToCount = 0;//转签数
        int renewCount = 0;//续签数

        Map<String,Object> map = new HashMap<>();
        Map<String,Object> returnMap = new HashMap<>();
        Calendar cal = Calendar.getInstance();
        map.put("year",qvo.getYearStart());
        String sql = "SELECT sum(MANAGE_GOTOSIGN_COUNT) goToCount," +
                "sum(MANAGE_RENEW) renewCount " +
                " FROM APP_MANAGE_COUNT WHERE 1=1";
        sql += " AND MANAGE_YEAR=:year";
        String msql = " ";
        if(hosp!=null){
            map.put("hospId",hosp.getId());
            sql += " AND MANAGE_HOSP_ID = :hospId";
            msql += " AND a.SIGN_HOSP_ID = :hospId";
        }else if(team!=null){
            map.put("teamId",team.getId());
            sql += " AND MANAGE_TEAM_ID = :teamId ";
            msql += " AND a.SIGN_TEAM_ID = :teamId ";
        }else{
            map.put("areaCode",AreaUtils.getAreaCode(address.getCtcode())+"%");
            sql += " AND MANAGE_AREA_CODE LIKE :areaCode ";
            msql+= " AND a.SIGN_AREA_CODE LIKE :areaCode ";
        }

        List<Map> maps = sysDao.getServiceReadDo().findSqlMap(sql,map);
        if(maps!=null && maps.size()>0){
            if(maps.get(0).get("goToCount")!=null){
                goToCount = (int)Double.parseDouble(maps.get(0).get("goToCount").toString());
            }
            if(maps.get(0).get("renewCount")!=null){
                renewCount = (int)Double.parseDouble(maps.get(0).get("renewCount").toString());
            }
        }
        String time = ExtendDate.getYMD(Calendar.getInstance());
        String nowYear = time.substring(0,4);
        int today = 0;
        if(qvo.getYearStart().equals(nowYear)) {
            time += " 00:00:00";
            map.put("today", time);
            String toDaySql = " SELECT COUNT(1) FROM APP_SIGN_FORM a WHERE a.SIGN_GOTO_SIGN_STATE = '1' " +
                    "AND a.SIGN_RENEWORGOTOSIGN_DATE<=SYSDATE() " +
                    " AND a.SIGN_RENEWORGOTOSIGN_DATE>=:today ";
            //今天之前的续签人数加上今天的人数
            toDaySql += msql;
            today = sysDao.getServiceReadDo().findCount(toDaySql, map);
            renewCount += today;
            String tozqSql = "SELECT COUNT(1)\n" +
                    "FROM\n" +
                    "\tAPP_SIGN_FORM a INNER JOIN APP_GOTOSIGN_RECORD b ON a.ID = b.GTS_OLD_SIGN_ID\n" +
                    "WHERE 1=1\n" +
                    "\t AND a.SIGN_RENEWORGOTOSIGN_DATE >= :today\n" +
                    "AND a.SIGN_RENEWORGOTOSIGN_DATE <= SYSDATE()\n" +
                    "AND a.SIGN_GOTO_SIGN_STATE='1'\n" +
                    "AND b.GTS_SIGN_STATE='1' ";
            tozqSql+=msql;
            int todayZq = sysDao.getServiceReadDo().findCount(tozqSql,map);
            goToCount+=todayZq;


        }


        returnMap.put("goToCount",goToCount);
        returnMap.put("renewCount",renewCount);
        double goToSignRate = 0;
        if(renewCount!=0){
            goToSignRate = MyMathUtil.div(Double.valueOf(goToCount),Double.valueOf(renewCount),4)*100;
        }
        returnMap.put("goToSignRate",goToSignRate);
        if(hosp != null){
            returnMap.put("hospId",hosp.getId());//医院id
            returnMap.put("hospName",hosp.getHospName());//医院名称
        }else if(team != null){
            returnMap.put("teamId",team.getId());//团队id
            returnMap.put("teamName",team.getTeamName());//团队名称
            returnMap.put("teamDrName",team.getTeamDrName());//团队队长名称
        }else{
            returnMap.put("areaCode",address.getCtcode());//区域编码
            returnMap.put("areaName",address.getAreaSname());//区域名称
        }
        return returnMap;
    }

    /**
     * 查询上年度签约量 本年度签约量 续签量
     * @param qvo
     * @return
     */
    @Override
    public Map<String, Object> signAndRenew(ResidentVo qvo)  throws Exception{
        Map<String,Object> returnMap = new HashMap<>();
        int oldSignCount = 0;//上一年度签约量
        int signCount = 0;//本年度签约量
        int renewCount = 0;//续签量
        Map<String,Object> map = new HashMap<>();
        map.put("year",qvo.getYearStart());
        int old = Integer.parseInt(qvo.getYearStart())-1;
        map.put("oldYear",old);
        String sql = "SELECT \n" +
                "sum(a.MANAGE_SIGN_COUNT) signCount,\n" +
                "sum(a.MANAGE_RENEW) renewCount \n" +
                "FROM APP_MANAGE_COUNT a WHERE 1=1 ";
        String oldSql = sql + " AND a.MANAGE_YEAR = :oldYear ";
        String nowSql = sql + " AND a.MANAGE_YEAR = :year ";
        String mSql = "";
        if(StringUtils.isNotBlank(qvo.getAreaId())){
            map.put("areaId",AreaUtils.getAreaCode(qvo.getAreaId())+"%");
            oldSql += " AND a.MANAGE_AREA_CODE LIKE :areaId ";
            nowSql += " AND a.MANAGE_AREA_CODE LIKE :areaId ";
            mSql += " AND a.SIGN_AREA_CODE LIKE :areaId ";
        }else if(StringUtils.isNotBlank(qvo.getHospId())){
            map.put("hospId",qvo.getHospId());
            oldSql += " AND a.MANAGE_HOSP_ID =:hospId ";
            nowSql += " AND a.MANAGE_HOSP_ID =:hospId ";
            mSql += " AND a.SIGN_HOSP_ID = :hospId ";
        }else if(StringUtils.isNotBlank(qvo.getTeamId())){
            map.put("teamId",qvo.getTeamId());
            oldSql += " AND a.MANAGE_TEAM_ID =:teamId ";
            nowSql += " AND a.MANAGE_TEAM_ID =:teamId ";
            mSql += " AND a.SIGN_TEAM_ID = :teamId ";
        }
        List<Map> oldList = sysDao.getServiceReadDo().findSqlMap(oldSql,map);
        if(oldList!=null && oldList.size()>0){
            if(oldList.get(0).get("signCount")!=null){
                oldSignCount = (int)Double.parseDouble(oldList.get(0).get("signCount").toString());
            }
        }
        List<Map> nowList = sysDao.getServiceReadDo().findSqlMap(nowSql,map);
        if(nowList!=null && nowList.size()>0){
            if(nowList.get(0).get("signCount")!=null){
                signCount = (int)Double.parseDouble(nowList.get(0).get("signCount").toString());
            }
            if(nowList.get(0).get("renewCount")!=null){
                renewCount = (int)Double.parseDouble(nowList.get(0).get("renewCount").toString());
            }
        }
        //今年的签约数加上今天的签约数
        Calendar calY = Calendar.getInstance();
        int yy = calY.get(Calendar.YEAR);
        if(yy==Integer.parseInt(qvo.getYearStart())){
            String today = ExtendDate.getYMD(calY);
            map.put("toDay",today+" 00:00:00");
            String[] signState = new String[]{SignFormType.YUQY.getValue(),SignFormType.YQY.getValue()};
            map.put("signState",signState);
            String toDaysql ="SELECT COUNT(1) FROM APP_SIGN_FORM a WHERE a.SIGN_STATE IN (:signState) AND a.SIGN_FROM_DATE>=:toDay " +
                    "AND a.SIGN_FROM_DATE <=SYSDATE() ";
            toDaysql+= mSql;
            int toDayN = sysDao.getServiceReadDo().findCount(toDaysql,map);
            signCount+=toDayN;
            String toDayRSql = "SELECT COUNT(1) FROM APP_SIGN_FORM a WHERE a.SIGN_STATE IN (:signState) AND a.SIGN_RENEWORGOTOSIGN_DATE>=:toDay " +
                    "AND a.SIGN_RENEWORGOTOSIGN_DATE <=SYSDATE() AND a.SIGN_GOTO_SIGN_STATE='2' ";
            toDayRSql+=mSql;
            int toDayRenew = sysDao.getServiceReadDo().findCount(toDayRSql,map);
            renewCount+=toDayRenew;
        }


        returnMap.put("oldSignCount",oldSignCount);
        returnMap.put("signCount",signCount);
        returnMap.put("renewCount",renewCount);

        //钻取数据
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();//签约
        String result = null;
        if(StringUtils.isNotBlank(qvo.getAreaId())){
            if (AreaUtils.getAreaCode(qvo.getAreaId()).length() == 6){//区级行政，往下查看医院的排名
                //区下的医院集合
                List<AppHospDept> hospDepts = sysDao.getServiceReadDo().loadByPk(AppHospDept.class, "hospUpcityareaId", qvo.getAreaId());
                if(hospDepts != null && hospDepts.size() >0){
                    for (AppHospDept hosp : hospDepts){
                        CdAddress address = this.sysDao.getCdAddressDao().findByCacheCode(hosp.getHospAreaCode());
                        Map<String, Object> mapp = getSignAndRenewCount(address, hosp,null,qvo);
                        list.add(mapp);
                    }
                }
            }else{
                List<CdAddress> lsCdAddress = sysDao.getCdAddressDao().findUpCasheAreaCode(qvo.getAreaId());
                if(lsCdAddress != null && lsCdAddress.size()>0){
                    for (CdAddress address : lsCdAddress) {
                        Map<String, Object> mapp = getSignAndRenewCount(address, null,null,qvo);
                        list.add(mapp);
                    }
                }
            }
            result = "1";
        }else if(StringUtils.isNotBlank(qvo.getHospId())){
            List<AppTeam> lsTeam = sysDao.getAppTeamDao().findAll(qvo.getHospId());
            if(lsTeam != null && lsTeam.size()>0){
                for(AppTeam team : lsTeam){
                    Map<String, Object> mapp = getSignAndRenewCount(null,null,team,qvo);
                    list.add(mapp);
                }
            }
            result = "1";
        }
        returnMap.put("srList",list);
        return returnMap;
    }

    public Map<String,Object> getSignAndRenewCount(CdAddress address,AppHospDept hosp,AppTeam team,ResidentVo qvo) throws Exception{
        Map<String,Object> returnMap = new HashMap<>();
        int oldSignCount = 0;//上一年度签约量
        int signCount = 0;//本年度签约量
        int renewCount = 0;//续签量
        Map<String,Object> map = new HashMap<>();
        map.put("year",qvo.getYearStart());
        int old = Integer.parseInt(qvo.getYearStart())-1;
        map.put("oldYear",old);
        String sql = "SELECT \n" +
                "sum(a.MANAGE_SIGN_COUNT) signCount,\n" +
                "sum(a.MANAGE_RENEW) renewCount \n" +
                "FROM APP_MANAGE_COUNT a WHERE 1=1 ";
        String oldSql = sql + " AND a.MANAGE_YEAR = :oldYear ";
        String nowSql = sql + " AND a.MANAGE_YEAR = :year ";
        if(address!=null){
            map.put("areaId",AreaUtils.getAreaCode(address.getCtcode())+"%");
            oldSql += " AND a.MANAGE_AREA_CODE LIKE :areaId ";
            nowSql += " AND a.MANAGE_AREA_CODE LIKE :areaId ";
        }else if(hosp!=null){
            map.put("hospId",hosp.getId());
            oldSql += " AND a.MANAGE_HOSP_ID =:hospId ";
            nowSql += " AND a.MANAGE_HOSP_ID =:hospId ";
        }else if(team!=null){
            map.put("teamId",team.getId());
            oldSql += " AND a.MANAGE_TEAM_ID =:teamId ";
            nowSql += " AND a.MANAGE_TEAM_ID =:teamId ";
        }
        List<Map> oldList = sysDao.getServiceReadDo().findSqlMap(oldSql,map);
        if(oldList!=null && oldList.size()>0){
            if(oldList.get(0).get("signCount")!=null){
                oldSignCount = (int)Double.parseDouble(oldList.get(0).get("signCount").toString());
            }
        }
        List<Map> nowList = sysDao.getServiceReadDo().findSqlMap(nowSql,map);
        if(nowList!=null && nowList.size()>0){
            if(nowList.get(0).get("signCount")!=null){
                signCount = (int)Double.parseDouble(nowList.get(0).get("signCount").toString());
            }
            if(nowList.get(0).get("renewCount")!=null){
                renewCount = (int)Double.parseDouble(nowList.get(0).get("renewCount").toString());
            }
        }
        returnMap.put("oldSignCount",oldSignCount);
        returnMap.put("signCount",signCount);
        returnMap.put("renewCount",renewCount);

        if(hosp != null){
            returnMap.put("hospId",hosp.getId());//医院id
            returnMap.put("hospName",hosp.getHospName());//医院名称
        }else if(team != null){
            returnMap.put("teamId",team.getId());//团队id
            returnMap.put("teamName",team.getTeamName());//团队名称
            returnMap.put("teamDrName",team.getTeamDrName());//团队队长名称
        }else{
            returnMap.put("areaCode",address.getCtcode());//区域编码
            returnMap.put("areaName",address.getAreaSname());//区域名称
        }
        return returnMap;
    }

    @Override
    public Map<String, Object> findPerFollowPlan(ResidentVo qvo) throws Exception {
        HashMap rmap = new HashMap();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("yearStart", ExtendDate.getFirstDayOfMonth(qvo.getYearStart())+" 00:00:00");
        map.put("yearEnd",ExtendDate.getLastDayOfMonth(qvo.getYearEnd())+" 23:59:59");
        map.put("contractState", CommSF.YES.getValue());
        String[] types = new String[]{ResidentMangeType.TNB.getValue(),ResidentMangeType.GXY.getValue()};
        String sql = "SELECT\n" +
                "\tCOUNT(1)\n" +
                "FROM\n" +
                "\tapp_follow_plan a \n" +
                "WHERE 1=1\n" +
                "\t AND a.SF_END_DATE >= :yearStart\n" +
                "AND a.SF_END_DATE <= :yearEnd\n" +
                "AND a.SF_FOLLOW_STATE=:contractState " ;
        if(StringUtils.isNotBlank(qvo.getAreaId())){
            map.put("areaCode",qvo.getAreaId()+"%");
            sql += " AND a.SF_HOS_AREA_CODE LIKE :areaCode ";
        }
        if(StringUtils.isNotBlank(qvo.getHospId())){
            map.put("hospId",qvo.getHospId());
            sql += " AND a.SF_HOS_ID =:hospId";
        }
        if(StringUtils.isNotBlank(qvo.getTeamId())){
            map.put("teamId",qvo.getTeamId());
            sql += " AND a.SF_TEAM_ID =:teamId";
        }
        sql +=" AND a.SF_FOLLOW_TYPE=:type ";
        if(types.length>0){
            for (String type:types){
                map.put("type",type);
                int count = this.sysDao.getServiceReadDo().findCount(sql,map);
                if(ResidentMangeType.GXY.getValue().equals(type)){
                    rmap.put("prcHypertensionCount",count);
                }
                if(ResidentMangeType.TNB.getValue().equals(type)){
                    rmap.put("prcDiabetesCount",count);
                }
            }
        }
        return rmap;
    }

    @Override
    public List<AppManageArchivingCountEntity> findCountList(AppManageArchivingCountQvo qvo) throws Exception {
        List<AppManageArchivingCountEntity> mapList = new ArrayList<>();
        int totalS = 0;//应签约数
        int totalYqy = 0;//已签约数
        int totalYbrq = 0;
        int totalEt = 0;
        int totalLnr = 0;
        int totalGxy = 0;
        int totalTnb = 0;
        int totalYcf = 0;
        int totalJsb = 0;
        int totalJhb = 0;
        int totalCjr = 0;
        int totalWzrq = 0;
        if(StringUtils.isNotBlank(qvo.getHospId())){
            String jdSourceType = "1";
            AppHospDept deptt = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,qvo.getHospId());
            if(deptt != null){
                if(StringUtils.isNotBlank(deptt.getHospAreaCode())){
                    CdCode ccode = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JDSOURCETYPE, AreaUtils.getAreaCode(deptt.getHospAreaCode(),"2"));
                    if(ccode != null){
                        if("0".equals(ccode.getCodeTitle())){
                            jdSourceType = "3";
                        }
                    }
                }
            }
            qvo.setJdSourceType(jdSourceType);
            //查询机构下各团队统计数
            List<AppTeam> listTeam = sysDao.getAppTeamDao().findAll(qvo.getHospId());
            if(listTeam != null && listTeam.size()>0){
                for(int i =0;i<listTeam.size();i++){
                    AppManageArchivingCountEntity returnMap = getFindCountList(null,null,listTeam.get(i),qvo);
                    returnMap.setIndex(String.valueOf(i+1));
                    mapList.add(returnMap);
                    totalS += Integer.parseInt(returnMap.getShouldSignCount());
                    totalYqy += Integer.parseInt(returnMap.getSignCount());
                    totalYbrq += Integer.parseInt(returnMap.getPtr());
                    totalEt += Integer.parseInt(returnMap.getEt());
                    totalLnr += Integer.parseInt(returnMap.getLnr());
                    totalGxy += Integer.parseInt(returnMap.getGxy());
                    totalTnb += Integer.parseInt(returnMap.getTnb());
                    totalYcf += Integer.parseInt(returnMap.getYcf());
                    totalJsb += Integer.parseInt(returnMap.getJsb());
                    totalJhb += Integer.parseInt(returnMap.getJhb());
                    totalCjr += Integer.parseInt(returnMap.getCjr());
                    totalWzrq += Integer.parseInt(returnMap.getWzrq());
                }
            }
        }else{
            if(StringUtils.isNotBlank(qvo.getPatientStreet())){
                String jdSourceType = "1";
                CdCode ccode = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JDSOURCETYPE, AreaUtils.getAreaCode(qvo.getPatientStreet(),"2"));
                if(ccode != null){
                    if("0".equals(ccode.getCodeTitle())){
                        jdSourceType = "3";
                    }
                }
                qvo.setJdSourceType(jdSourceType);
                List<AppHospDept> listHosp = sysDao.getAppHospDeptDao().findHospByStreet(qvo.getPatientStreet(),qvo.getIsFindState());
                if(listHosp != null && listHosp.size()>0){
                    qvo.setItemCount(listHosp.size());
                    for(int i =0;i<listHosp.size();i++){
                        AppManageArchivingCountEntity returnMap = getFindCountList(null,listHosp.get(i),null,qvo);
                        returnMap.setIndex(String.valueOf(i+1));
                        mapList.add(returnMap);
                        totalS += Integer.parseInt(returnMap.getShouldSignCount());
                        totalYqy += Integer.parseInt(returnMap.getSignCount());
                        totalYbrq += Integer.parseInt(returnMap.getPtr());
                        totalEt += Integer.parseInt(returnMap.getEt());
                        totalLnr += Integer.parseInt(returnMap.getLnr());
                        totalGxy += Integer.parseInt(returnMap.getGxy());
                        totalTnb += Integer.parseInt(returnMap.getTnb());
                        totalYcf += Integer.parseInt(returnMap.getYcf());
                        totalJsb += Integer.parseInt(returnMap.getJsb());
                        totalJhb += Integer.parseInt(returnMap.getJhb());
                        totalCjr += Integer.parseInt(returnMap.getCjr());
                        totalWzrq += Integer.parseInt(returnMap.getWzrq());
                    }
                }
            }else{
                String areCode = "";
                if(StringUtils.isNotBlank(qvo.getPatientArea())){
                    areCode = qvo.getPatientArea();
                }else if(StringUtils.isNotBlank(qvo.getPatientCity())){
                    areCode = qvo.getPatientCity();
                }else{
                    areCode = "350000000000";
                }

                List<CdAddress> lsCdAddress = sysDao.getCdAddressDao().findUpCasheAreaCode(areCode);
                if(lsCdAddress != null && lsCdAddress.size()>0){
                    int index = 1;
                    for(int i =0;i<lsCdAddress.size();i++){
                        if(StringUtils.isNotBlank(lsCdAddress.get(i).getCtcode())){
                            String areaCodeee = lsCdAddress.get(i).getCtcode().substring(0,4);
                            if(!AddressType.XMS.getValue().equals(areaCodeee) && !AddressType.LYS.getValue().equals(areaCodeee)
                                    && !AddressType.PTZHSYQ.getValue().equals(areaCodeee) && !AddressType.NDS.getValue().equals(areaCodeee)){
                                String jdSourceType = "1";
                                CdCode ccode = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JDSOURCETYPE, AreaUtils.getAreaCode(lsCdAddress.get(i).getCtcode(),"2"));
                                if(ccode != null){
                                    if("0".equals(ccode.getCodeTitle())){
                                        jdSourceType = "3";
                                    }
                                }
                                qvo.setJdSourceType(jdSourceType);

                                AppManageArchivingCountEntity returnMap = getFindCountList(lsCdAddress.get(i), null,null,qvo);
                                returnMap.setIndex(String.valueOf(index));
                                index ++;
                                mapList.add(returnMap);
                                totalS += Integer.parseInt(returnMap.getShouldSignCount());
                                totalYqy += Integer.parseInt(returnMap.getSignCount());
                                totalYbrq += Integer.parseInt(returnMap.getPtr());
                                totalEt += Integer.parseInt(returnMap.getEt());
                                totalLnr += Integer.parseInt(returnMap.getLnr());
                                totalGxy += Integer.parseInt(returnMap.getGxy());
                                totalTnb += Integer.parseInt(returnMap.getTnb());
                                totalYcf += Integer.parseInt(returnMap.getYcf());
                                totalJsb += Integer.parseInt(returnMap.getJsb());
                                totalJhb += Integer.parseInt(returnMap.getJhb());
                                totalCjr += Integer.parseInt(returnMap.getCjr());
                                totalWzrq += Integer.parseInt(returnMap.getWzrq());

                            }
                        }
                    }
                }
            }
        }
        AppManageArchivingCountEntity mpp = new AppManageArchivingCountEntity();
        mpp.setIndex("");
        mpp.setName("合计：");
        mpp.setDyzrr("");
        mpp.setTdcy("");
        mpp.setShouldSignCount(String.valueOf(totalS));
        mpp.setSignCount(String.valueOf(totalYqy));
        mpp.setPtr(String.valueOf(totalYbrq));
        mpp.setEt(String.valueOf(totalEt));
        mpp.setLnr(String.valueOf(totalLnr));
        mpp.setYcf(String.valueOf(totalYcf));
        mpp.setGxy(String.valueOf(totalGxy));
        mpp.setTnb(String.valueOf(totalTnb));
        mpp.setJsb(String.valueOf(totalJsb));
        mpp.setJhb(String.valueOf(totalJhb));
        mpp.setCjr(String.valueOf(totalCjr));
        mpp.setWzrq(String.valueOf(totalWzrq));
        mapList.add(mpp);
        return mapList;
    }
    public AppManageArchivingCountEntity getFindCountList(CdAddress address,AppHospDept hospDept,AppTeam team,AppManageArchivingCountQvo qvo) throws Exception{
        Map<String,Object> returnMap = new HashMap<>();
        AppManageArchivingCountEntity vv = new AppManageArchivingCountEntity();
        String name = "";
        String dyzrr = "";
        String tdcy = "";
        int shouldSignCount = 0;
        int signCount = 0;
        int ptr = 0;
        int et = 0;
        int ycf = 0;
        int lnr = 0;
        int gxy = 0;
        int tnb = 0;
        int jsb = 0;
        int jhb = 0;
        int cjr = 0;
        int wzrq = 0;
        returnMap.put("shouldSignCount","0");
        Map<String,Object> map = new HashMap<>();
        map.put("SOURCE_TYPE",qvo.getJdSourceType());
        String shouldSql = "SELECT COUNT(1) FROM APP_ARCHIVINGCARD_PEOPLE WHERE 1=1 AND SOURCE_TYPE =:SOURCE_TYPE ";//AND SOURCE_TYPE ='1'
        map.put("signState",new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()});
        map.put("toDayStart",ExtendDate.getYMD(Calendar.getInstance())+" 00:00:00");
        map.put("toDayEnd",ExtendDate.getYMD(Calendar.getInstance())+" 23:59:59");
        String nowSql = "SELECT count(1) FROM app_sign_form a " +
                "INNER JOIN app_label_economics b ON a.ID = b.LABEL_SIGN_ID " +
                "INNER JOIN app_label_group c ON a.ID = c.LABEL_SIGN_ID " +
                "WHERE b.LABEL_VALUE = '2' AND c.LABEL_TYPE = '3' AND c.LABEL_VALUE=:value " +
                " AND a.SIGN_STATE IN (:signState) AND a.SIGN_FROM_DATE >=:toDayStart AND a.SIGN_FROM_DATE<=:toDayEnd ";
        String sql = "SELECT " +
                "SUM(MANAGE_SIGN_COUNT) signCount,\n" +
                "SUM(MANAGE_PLAIN_COUNT) ptr,\n" +
                "SUM(MANAGE_CHILD_COUNT) et,\n" +
                "SUM(MANAGE_MATERNAL_COUNT) ycf,\n" +
                "SUM(MANAGE_OLD_PEOPLE_COUNT) lnr,\n" +
                "SUM(MANAGE_BLOOD_COUNT) gxy,\n" +
                "SUM(MANAGE_DIABETES_COUNT) tnb,\n" +
                "SUM(MANAGE_PSYCHOSIS_COUNT) jsb,\n" +
                "SUM(MANAGE_PHTHISIS_COUNT) jhb,\n" +
                "SUM(MANAGE_DISABLED_PEOPLE_COUNT) cjr,\n" +
                "SUM(MANAGE_SERVICE_UNKNOWN_COUNT) wzrq \n" +
                " FROM APP_MANAGE_ARCHIVING_COUNT WHERE 1=1  ";
        if(StringUtils.isNotBlank(qvo.getStartDate())){
            map.put("startDate",qvo.getStartDate());
            sql+=" AND MANAGE_YEAR_MONTH >=:startDate";
        }
        if(StringUtils.isNotBlank(qvo.getEndDate())){
            map.put("endDate",qvo.getEndDate());
            sql += " AND MANAGE_YEAR_MONTH <=:endDate";
        }
        if(team != null){
            map.put("teamId",team.getId());
            sql += " AND MANAGE_TEAM_ID =:teamId ";
            nowSql += " AND a.SIGN_TEAM_ID =:teamId ";
            name = team.getTeamName();
            List<AppTeamMemberEntity> teamMs = sysDao.getAppTeamMemberDao().findMemByTeamId(team.getId());
            if(teamMs != null && teamMs.size()>0){
                for(AppTeamMemberEntity ll:teamMs){
                    if("0".equals(ll.getMemState())){
                        dyzrr=ll.getMemDrName()+"("+ll.getMemWorkName()+")";
                    }else{
                        if(StringUtils.isNotBlank(ll.getMemWorkName())){
                            if(StringUtils.isBlank(tdcy)){
                                tdcy = ll.getMemDrName()+"("+ll.getMemWorkName()+")";
                            }else{
                                tdcy +=","+ll.getMemDrName()+"("+ll.getMemWorkName()+")";
                            }
                        }else{
                            if(StringUtils.isBlank(tdcy)){
                                tdcy = ll.getMemDrName();
                            }else{
                                tdcy +=","+ll.getMemDrName();
                            }
                        }
                    }
                }
            }
        }
        if(hospDept != null){
            map.put("hospId",hospDept.getId());
            map.put("hospAreaCode",AreaUtils.getAreaCode(hospDept.getHospAreaCode(),"3")+"%");
            sql += " AND MANAGE_HOSP_ID =:hospId AND MANAGE_JD_AREA_CODE LIKE :hospAreaCode ";
            nowSql += " AND a.SIGN_HOSP_ID =:hospId ";
            name = hospDept.getHospName();
        }
        if(address != null){
            if("4".equals(address.getLevel())){//街道
                map.put("areaCode",address.getCtcode().substring(0,9)+"%");
            }else{
                map.put("areaCode",AreaUtils.getAreaCode(address.getCtcode())+"%");
            }
            sql += " AND MANAGE_JD_AREA_CODE LIKE:areaCode ";
//            map.put("areaCode",AreaUtils.getAreaCode(address.getCtcode())+"%");
            if(StringUtils.isNotBlank(qvo.getPatientArea())){
                map.put("codee",address.getCtcode());
                shouldSql += " AND ADDR_RURAL_CODE =:codee ";
            }else if(StringUtils.isNotBlank(qvo.getPatientCity())){
                map.put("codee",AreaUtils.getAreaCode(address.getCtcode(),"3")+"%");
                //漳州“台商投资区”，“招商局漳州开发区”，“常山农场经济开发区”这三个区要特别判断
                if("350698000000".equals(address.getCtcode())){//台商投资区
                    map.put("codee","350681102%");
                    map.put("areaCode","350681102%");
                }else if("350699000000".equals(address.getCtcode())){//招商局漳州开发区
                    map.put("codee","350681501%");
                    map.put("areaCode","350681501%");
                }else if("350697000000".equals(address.getCtcode())){//常山农场经济开发区
                    map.put("codee","350622450%");
                    map.put("areaCode","350622450%");
                }else if("350681000000".equals(address.getCtcode())){
                    map.put("notAreaCode",new String[]{"350681102000","350681501000","350622450000"});
                    shouldSql +=" AND ADDR_RURAL_CODE NOT IN (:notAreaCode) ";
                    sql += " AND MANAGE_JD_AREA_CODE NOT IN (:notAreaCode) ";
                    nowSql += " AND a.SIGN_AREA_CODE NOT IN (:notAreaCode) ";
                }else if("350622000000".equals(address.getCtcode())){
                    map.put("notAreaCode",new String[]{"350681102000","350681501000","350622450000"});
                    shouldSql +=" AND ADDR_RURAL_CODE NOT IN (:notAreaCode) ";
                    sql += " AND MANAGE_JD_AREA_CODE NOT IN (:notAreaCode) ";
                    nowSql += " AND a.SIGN_AREA_CODE NOT IN (:notAreaCode) ";
                }

                shouldSql +=" AND ADDR_RURAL_CODE LIKE :codee ";
            }else{
                map.put("codee",AreaUtils.getAreaCode(address.getCtcode())+"%");
                shouldSql +=" AND ADDR_RURAL_CODE LIKE :codee ";
            }

            nowSql += " AND a.SIGN_AREA_CODE LIKE :areaCode ";
            name = address.getAreaSname();
            shouldSignCount = sysDao.getServiceDo().findCount(shouldSql,map);
        }

        List<Map> listMap = sysDao.getServiceReadDo().findSqlMap(sql,map);
        if(listMap != null && listMap.size()>0){
            if(listMap.get(0).get("signCount")!=null){
                signCount = (int)Double.parseDouble(listMap.get(0).get("signCount").toString());
            }
            if(listMap.get(0).get("ptr")!=null){
                ptr = (int)Double.parseDouble(listMap.get(0).get("ptr").toString());
            }
            if(listMap.get(0).get("et")!=null){
                et = (int)Double.parseDouble(listMap.get(0).get("et").toString());
            }
            if(listMap.get(0).get("ycf")!=null){
                ycf = (int)Double.parseDouble(listMap.get(0).get("ycf").toString());
            }
            if(listMap.get(0).get("lnr")!=null){
                lnr = (int)Double.parseDouble(listMap.get(0).get("lnr").toString());
            }
            if(listMap.get(0).get("jsb")!=null){
                jsb = (int)Double.parseDouble(listMap.get(0).get("jsb").toString());
            }
            if(listMap.get(0).get("gxy")!=null){
                gxy = (int)Double.parseDouble(listMap.get(0).get("gxy").toString());
            }
            if(listMap.get(0).get("tnb")!=null){
                tnb = (int)Double.parseDouble(listMap.get(0).get("tnb").toString());
            }
            if(listMap.get(0).get("jhb")!=null){
                jhb = (int)Double.parseDouble(listMap.get(0).get("jhb").toString());
            }
            if(listMap.get(0).get("cjr")!=null){
                cjr = (int)Double.parseDouble(listMap.get(0).get("cjr").toString());
            }
            if(listMap.get(0).get("wzrq")!=null){
                wzrq = (int)Double.parseDouble(listMap.get(0).get("wzrq").toString());
            }
        }
        if(StringUtils.isBlank(qvo.getEndDate()) || ExtendDate.getYM(Calendar.getInstance()).equals(qvo.getEndDate())){
           List<AppLabelManage> ls = this.sysDao.getAppLabelManageDao().findByType("3");
            for(AppLabelManage l:ls) {
                map.put("value",l.getLabelValue());
//                String ssql = "SELECT count(1) from ("+nowSql+" GROUP BY a.SIGN_PATIENT_ID) c ";
//                int  count= sysDao.getServiceReadDo().findCount(ssql, map);
                int count =0;
                if(l.getLabelValue().equals("1")){//普通人群
                    ptr+= count;
                }else if(l.getLabelValue().equals("2")){//儿童(0-6岁)
                    et+= count;
                }else if(l.getLabelValue().equals("3")){//孕产妇
                    ycf+= count;
                }else if(l.getLabelValue().equals("4")){//老年人
                    lnr+= count;
                }else if(l.getLabelValue().equals("5")){//高血压
                    gxy+= count;
                }else if(l.getLabelValue().equals("6")){//糖尿病
                    tnb+= count;
                }else if(l.getLabelValue().equals("7")){//严重精神障碍
                    jsb+= count;
                }else if(l.getLabelValue().equals("8")){//结核病
                    jhb+= count;
                }else if(l.getLabelValue().equals("9")){//残疾人
                    cjr+= count;
                }else if(l.getLabelValue().equals("99")){//未知
                    wzrq+= count;
                }
            }
        }
        if(address != null){
            if(shouldSignCount==0){
                signCount = 0;
                ptr = 0;
                et = 0;
                ycf = 0;
                lnr = 0;
                gxy = 0;
                tnb = 0;
                jsb = 0;
                jhb = 0;
                cjr = 0;
                wzrq = 0;
            }
        }
        vv.setName(name);
        vv.setDyzrr(dyzrr);
        vv.setTdcy(tdcy);
        vv.setShouldSignCount(String.valueOf(shouldSignCount));
        vv.setSignCount(String.valueOf(signCount));
        vv.setPtr(String.valueOf(ptr));
        vv.setEt(String.valueOf(et));
        vv.setLnr(String.valueOf(lnr));
        vv.setYcf(String.valueOf(ycf));
        vv.setGxy(String.valueOf(gxy));
        vv.setTnb(String.valueOf(tnb));
        vv.setJsb(String.valueOf(jsb));
        vv.setJhb(String.valueOf(jhb));
        vv.setCjr(String.valueOf(cjr));
        vv.setWzrq(String.valueOf(wzrq));
        return  vv;
    }

    @Override
    public List<Map<String, Object>> archivingCardCount(ResidentVo qvo) throws Exception {//钻取到街道
        List<Map<String,Object>> list = new ArrayList<>();
        if(StringUtils.isNotBlank(qvo.getAreaId())){
            //qvo.getAreaId = 35
            int totalArchivingCount = 0;//建档立卡人员数
            int totalSignCount = 0;//累计已签约人数
            int total = 0;//合计数
            int dieCount = 0;//未签约死亡数
            int missCount = 0;//失联失踪人数
            int moveOutCount = 0;//迁出人数
            int longTimeOutCount = 0;//长期外出人数
            int refuseSignCount = 0;//拒签人数
            int beExecotedCount = 0;//服刑人数
            int nameRepeatCount = 0;//名单重复人数（信息重复）
            int mentalPatientCount = 0;//精神病人住康复医院，家属外出人数
            int performMilitaryService = 0;//服兵役人数
            int foreignNationality = 0;//外籍人数
            int marryOutCount = 0;//外嫁人数（未入户）
            int newPersonnelCount = 0;//新增人员
            int noConnectionCount = 0;//联系不上人数
            int idnoDiscrepancyCount = 0;//身份名字不符人数（身份证信息错误）
            int notSignGoOut = 0;//暂时外出无法签约
            int fieldArchivingCount = 0;//外地建档人数
            int quitCount = 0;//退出人数（异地签约）
            int notCardCount = 0;//无社保卡人数
            int goAbroadCount = 0;//出国人数
            int otherCount = 0;//其它人数
            int unfilledCount = 0;//未填写人数
            int lowInsuredCount = 0;//低保户人数
            int signLowInsuredCount = 0;//已签约低保户人数
            int totalYbArchivingCount = 0;//医保总建档立卡数合计
            int totalYbSignArchivingCount = 0;//医保已签约总建档立卡数合计
            String ccode = qvo.getAreaId();
            if(qvo.getAreaId().length() == 2){//省 区划
                qvo.setAreaId(qvo.getAreaId()+"0000000000");
            }else if(qvo.getAreaId().length() == 4){//市 区划
                qvo.setAreaId(qvo.getAreaId()+"00000000");
            }else if(qvo.getAreaId().length() == 6){//区 区划
                qvo.setAreaId(qvo.getAreaId()+"000000");
            }else if(qvo.getAreaId().length() == 9){//街道 区划
                qvo.setAreaId(qvo.getAreaId()+"000");
            }
            //先查对照表
            CdAddressConfiguration ccf = sysDao.getCdAddressDao().findByCodeJw(qvo.getAreaId());
            if(ccf != null){
                qvo.setAreaId(ccf.getId());
            }
            if (AreaUtils.getAreaCode(qvo.getAreaId()).length() == 6 || AreaUtils.getAreaCode(qvo.getAreaId()).length() == 5) {//街道级行政，往下查看医院的排名
                //区下的医院集合
                List<AppHospDept> hospDepts = sysDao.getAppHospDeptDao().findUpHospListRead(qvo.getAreaId());
                if(hospDepts != null && hospDepts.size()>0) {
                    for (AppHospDept hosp : hospDepts) {
                        if (hosp.getHospState().equals("1")) {
                            Map<String, Object> map = getArchivingCardCount(null, hosp,qvo);
                            list.add(map);
                            totalArchivingCount +=Integer.parseInt(map.get("totalArchivingCount").toString());//建档立卡人员数
                            totalSignCount +=Integer.parseInt(map.get("totalSignCount").toString());//累计已签约人数
                            total +=Integer.parseInt(map.get("total").toString());//合计数
                            dieCount +=Integer.parseInt(map.get("dieCount").toString());//未签约死亡数
                            missCount +=Integer.parseInt(map.get("missCount").toString());//失联失踪人数
                            moveOutCount +=Integer.parseInt(map.get("moveOutCount").toString());//迁出人数
                            longTimeOutCount +=Integer.parseInt(map.get("longTimeOutCount").toString());//长期外出人数
                            refuseSignCount +=Integer.parseInt(map.get("refuseSignCount").toString());//拒签人数
                            beExecotedCount +=Integer.parseInt(map.get("beExecotedCount").toString());//服刑人数
                            nameRepeatCount +=Integer.parseInt(map.get("nameRepeatCount").toString());//名单重复人数
                            mentalPatientCount +=Integer.parseInt(map.get("mentalPatientCount").toString());//精神病人住康复医院，家属外出人数
                            performMilitaryService +=Integer.parseInt(map.get("performMilitaryService").toString());//服兵役人数
                            foreignNationality +=Integer.parseInt(map.get("foreignNationality").toString());//外籍人数
                            marryOutCount +=Integer.parseInt(map.get("marryOutCount").toString());//外嫁人数
                            newPersonnelCount +=Integer.parseInt(map.get("newPersonnelCount").toString());//新增人员
                            noConnectionCount +=Integer.parseInt(map.get("noConnectionCount").toString());//联系不上人数
                            idnoDiscrepancyCount +=Integer.parseInt(map.get("idnoDiscrepancyCount").toString());//身份名字不符人数
                            notSignGoOut +=Integer.parseInt(map.get("notSignGoOut").toString());//暂时外出无法签约
                            fieldArchivingCount +=Integer.parseInt(map.get("fieldArchivingCount").toString());//外地建档人数
                            quitCount +=Integer.parseInt(map.get("quitCount").toString());//退出人数
                            notCardCount +=Integer.parseInt(map.get("notCardCount").toString());//无社保卡人数
                            goAbroadCount +=Integer.parseInt(map.get("goAbroadCount").toString());//出国人数
                            otherCount +=Integer.parseInt(map.get("otherCount").toString());//其它人数
                            unfilledCount +=Integer.parseInt(map.get("unfilledCount").toString());//未填写人数
                            lowInsuredCount += Integer.parseInt(map.get("lowInsuredCount").toString());//低保户人数
                            signLowInsuredCount += Integer.parseInt(map.get("signLowInsuredCount").toString());//已签约低保户人数
                            totalYbArchivingCount += Integer.parseInt(map.get("totalYbArchivingCount").toString());//医保总建档立卡数合计
                            totalYbSignArchivingCount += Integer.parseInt(map.get("totalYbSignArchivingCount").toString());//医保已签约总建档立卡数合计
                        }
                    }
                    Map<String,Object> map = new HashMap<>();
                    map.put("name","合计");
                    map.put("code","");
                    map.put("totalArchivingCount",totalArchivingCount);//建档立卡人员数
                    map.put("totalSignCount",totalSignCount);//累计已签约人数
                    map.put("total",total);//合计
                    map.put("dieCount",dieCount);//未签约死亡数
                    map.put("missCount",missCount);//失联失踪人数
                    map.put("moveOutCount",moveOutCount);//迁出人数
                    map.put("longTimeOutCount",longTimeOutCount);//长期外出人数
                    map.put("refuseSignCount",refuseSignCount);//拒签人数
                    map.put("beExecotedCount",beExecotedCount);//服刑人数
                    map.put("nameRepeatCount",nameRepeatCount);//名单重复人数
                    map.put("mentalPatientCount",mentalPatientCount);//精神病人住康复医院，家属外出人数
                    map.put("performMilitaryService",performMilitaryService);//服兵役人数
                    map.put("foreignNationality",foreignNationality);//外籍人数
                    map.put("marryOutCount",marryOutCount);//外嫁人数
                    map.put("newPersonnelCount",newPersonnelCount);//新增人员
                    map.put("noConnectionCount",noConnectionCount);//联系不上人数
                    map.put("idnoDiscrepancyCount",idnoDiscrepancyCount);//身份名字不符人数
                    map.put("notSignGoOut",notSignGoOut);//暂时外出无法签约
                    map.put("fieldArchivingCount",fieldArchivingCount);//外地建档人数
                    map.put("quitCount",quitCount);//退出人数
                    map.put("notCardCount",notCardCount);//无社保卡人数
                    map.put("goAbroadCount",goAbroadCount);//出国人数
                    map.put("otherCount",otherCount);//其它人数
                    map.put("unfilledCount",unfilledCount);//未填写人数
                    map.put("lowInsuredCount",lowInsuredCount);
                    map.put("signLowInsuredCount",signLowInsuredCount);
                    map.put("totalYbArchivingCount",totalYbArchivingCount);//医保总建档立卡数合计
                    map.put("totalYbSignArchivingCount",totalYbSignArchivingCount);//医保已签约总建档立卡数合计
                    list.add(map);
                }
            }else{
                List<CdAddress> lsCdAddress = sysDao.getCdAddressDao().findUpCasheAreaCode(qvo.getAreaId());
                if(lsCdAddress != null && lsCdAddress.size()>0){
                    for (CdAddress address : lsCdAddress) {
                        String code = address.getCtcode().substring(0,4);
                        if(AddressType.FZS.getValue().equals(code)||AddressType.SMS.getValue().equals(code)||AddressType.ZZS.getValue().equals(code)||
                                AddressType.QZS.getValue().equals(code)||AddressType.PTS.getValue().equals(code)||AddressType.NPS.getValue().equals(code)){
                            Map<String, Object> map = getArchivingCardCount(address, null,qvo);
                            totalArchivingCount +=Integer.parseInt(map.get("totalArchivingCount").toString());//建档立卡人员数
                            totalSignCount +=Integer.parseInt(map.get("totalSignCount").toString());//累计已签约人数
                            total +=Integer.parseInt(map.get("total").toString());//合计数
                            dieCount +=Integer.parseInt(map.get("dieCount").toString());//未签约死亡数
                            missCount +=Integer.parseInt(map.get("missCount").toString());//失联失踪人数
                            moveOutCount +=Integer.parseInt(map.get("moveOutCount").toString());//迁出人数
                            longTimeOutCount +=Integer.parseInt(map.get("longTimeOutCount").toString());//长期外出人数
                            refuseSignCount +=Integer.parseInt(map.get("refuseSignCount").toString());//拒签人数
                            beExecotedCount +=Integer.parseInt(map.get("beExecotedCount").toString());//服刑人数
                            nameRepeatCount +=Integer.parseInt(map.get("nameRepeatCount").toString());//名单重复人数
                            mentalPatientCount +=Integer.parseInt(map.get("mentalPatientCount").toString());//精神病人住康复医院，家属外出人数
                            performMilitaryService +=Integer.parseInt(map.get("performMilitaryService").toString());//服兵役人数
                            foreignNationality +=Integer.parseInt(map.get("foreignNationality").toString());//外籍人数
                            marryOutCount +=Integer.parseInt(map.get("marryOutCount").toString());//外嫁人数
                            newPersonnelCount +=Integer.parseInt(map.get("newPersonnelCount").toString());//新增人员
                            noConnectionCount +=Integer.parseInt(map.get("noConnectionCount").toString());//联系不上人数
                            idnoDiscrepancyCount +=Integer.parseInt(map.get("idnoDiscrepancyCount").toString());//身份名字不符人数
                            notSignGoOut +=Integer.parseInt(map.get("notSignGoOut").toString());//暂时外出无法签约
                            fieldArchivingCount +=Integer.parseInt(map.get("fieldArchivingCount").toString());//外地建档人数
                            quitCount +=Integer.parseInt(map.get("quitCount").toString());//退出人数
                            notCardCount +=Integer.parseInt(map.get("notCardCount").toString());//无社保卡人数
                            goAbroadCount +=Integer.parseInt(map.get("goAbroadCount").toString());//出国人数
                            otherCount +=Integer.parseInt(map.get("otherCount").toString());//其它人数
                            unfilledCount +=Integer.parseInt(map.get("unfilledCount").toString());//未填写人数
                            lowInsuredCount +=Integer.parseInt(map.get("lowInsuredCount").toString());//低保户人数
                            signLowInsuredCount += Integer.parseInt(map.get("signLowInsuredCount").toString());//已签约低保户人数
                            totalYbArchivingCount += Integer.parseInt(map.get("totalYbArchivingCount").toString());//医保总建档立卡数合计
                            totalYbSignArchivingCount += Integer.parseInt(map.get("totalYbSignArchivingCount").toString());//医保已签约总建档立卡数合计
                            list.add(map);
                        }
                    }
                }
                Map<String,Object> map = new HashMap<>();
                map.put("name","合计");
                map.put("code","");
                map.put("totalArchivingCount",totalArchivingCount);//建档立卡人员数
                map.put("totalSignCount",totalSignCount);//累计已签约人数
                map.put("total",total);//合计
                map.put("dieCount",dieCount);//未签约死亡数
                map.put("missCount",missCount);//失联失踪人数
                map.put("moveOutCount",moveOutCount);//迁出人数
                map.put("longTimeOutCount",longTimeOutCount);//长期外出人数
                map.put("refuseSignCount",refuseSignCount);//拒签人数
                map.put("beExecotedCount",beExecotedCount);//服刑人数
                map.put("nameRepeatCount",nameRepeatCount);//名单重复人数
                map.put("mentalPatientCount",mentalPatientCount);//精神病人住康复医院，家属外出人数
                map.put("performMilitaryService",performMilitaryService);//服兵役人数
                map.put("foreignNationality",foreignNationality);//外籍人数
                map.put("marryOutCount",marryOutCount);//外嫁人数
                map.put("newPersonnelCount",newPersonnelCount);//新增人员
                map.put("noConnectionCount",noConnectionCount);//联系不上人数
                map.put("idnoDiscrepancyCount",idnoDiscrepancyCount);//身份名字不符人数
                map.put("notSignGoOut",notSignGoOut);//暂时外出无法签约
                map.put("fieldArchivingCount",fieldArchivingCount);//外地建档人数
                map.put("quitCount",quitCount);//退出人数
                map.put("notCardCount",notCardCount);//无社保卡人数
                map.put("goAbroadCount",goAbroadCount);//出国人数
                map.put("otherCount",otherCount);//其它人数
                map.put("unfilledCount",unfilledCount);//未填写人数
                map.put("lowInsuredCount",lowInsuredCount);
                map.put("signLowInsuredCount",signLowInsuredCount);
                map.put("totalYbArchivingCount",totalYbArchivingCount);//医保总建档立卡数合计
                map.put("totalYbSignArchivingCount",totalYbSignArchivingCount);//医保已签约总建档立卡数合计
                list.add(map);
            }

        }
            return list;
    }

    public Map<String,Object> getArchivingCardCount(CdAddress address,AppHospDept hosp,ResidentVo qvo) throws Exception{
        Map<String,Object> returnMap = new HashMap<>();
        Map<String,Object> map = new HashMap<>();
        int totalArchivingCount = 0;//建档立卡人员数
        int totalSignCount = 0;//累计已签约人数
        int total = 0;//合计数
        int dieCount = 0;//未签约死亡数
        int missCount = 0;//失联失踪人数
        int moveOutCount = 0;//迁出人数
        int longTimeOutCount = 0;//长期外出人数
        int refuseSignCount = 0;//拒签人数
        int beExecotedCount = 0;//服刑人数
        int nameRepeatCount = 0;//名单重复人数
        int mentalPatientCount = 0;//精神病人住康复医院，家属外出人数
        int performMilitaryService = 0;//服兵役人数
        int foreignNationality = 0;//外籍人数
        int marryOutCount = 0;//外嫁人数
        int newPersonnelCount = 0;//新增人员
        int noConnectionCount = 0;//联系不上人数
        int idnoDiscrepancyCount = 0;//身份名字不符人数
        int notSignGoOut = 0;//暂时外出无法签约
        int fieldArchivingCount = 0;//外地建档人数
        int quitCount = 0;//退出人数
        int notCardCount = 0;//无社保卡人数
        int goAbroadCount = 0;//出国人数
        int otherCount = 0;//其它人数
        int unfilledCount = 0;//未填写人数
        int lowInsuredCount = 0;//低保户人数
        int signLowInsuredCount = 0;//已签约的低保户人数
        int totalYbArchivingCount = 0;//医保总建档立卡数合计
        int totalYbSignArchivingCount = 0;//医保已签约总建档立卡数合计
        map.put("LOW_INSURED","2");
        map.put("SOURCE_TYPE",qvo.getJdSourceType());
        map.put("JD_INSURED","1");
        String sqlLI = "SELECT COUNT(1) FROM app_archivingcard_people WHERE 1=1 AND SOURCE_TYPE =:SOURCE_TYPE AND PROVINCIAL_INSURANCE = :LOW_INSURED ";
        String sqlyb = "SELECT COUNT(1) FROM app_archivingcard_people WHERE 1=1 AND SOURCE_TYPE =:SOURCE_TYPE AND PROVINCIAL_INSURANCE = :JD_INSURED ";
        String sql = "SELECT * FROM app_archivingcard_people WHERE 1=1 AND SOURCE_TYPE =:SOURCE_TYPE  ";//建档立卡总人数（按区域编号查）
        String sqlSign = "SELECT * FROM app_archivingcard_people WHERE 1=1  AND SIGN_ID IS NOT NULL AND SOURCE_TYPE =:SOURCE_TYPE  ";//签约数(省市区按区域编号查询签约数，街道（即机构）按机构主键查询);
        String sqlWqy = " SELECT SUM(TOTAL_SIGN_COUNT) totalSignCount FROM app_manage_archiving_people WHERE 1=1 ";
        //查询区域建档立卡人员未签约原因
        int totalArCountYb = 0;//医保下的总的建档立卡数

        String sqlCount = " SELECT\n" +
                "\tSUM(TOTAL_ARCHIVING_CONT) totalArchivingCount," +
                "\tSUM(MANAGE_TOTAL) manageTotal,\n" +
                "\tSUM(TOTAL_SIGN_COUNT) totalSignCount,\n" +
                "\tSUM(DIE_COUNT) dieCount,\n" +
                "\tSUM(MISS_COUNT) missCount,\n" +
                "\tSUM(MOVE_OUT_COUNT) moveOutCount,\n" +
                "\tSUM(LONG_TIME_OUT_COUNT) longTimeOutCount,\n" +
                "\tSUM(REFUSE_SIGN_COUNT) refuseSignCount,\n" +
                "\tSUM(BE_EXECUTED_COUNT) beExecotedCount,\n" +
                "\tSUM(NAME_REPEAT_COUNT) nameRepeatCount,\n" +
                "\tSUM(MENTAL_PATIENT_COUNT) mentalPatientCount,\n" +
                "\tSUM(PERFORM_MILITARY_SERVICE) performMilitaryService,\n" +
                "\tSUM(FOREIGN_NATIONALITY) foreignNationality,\n" +
                "\tSUM(MARRY_OUT_COUNT) marryOutCount,\n" +
                "\tSUM(NEW_PERSONNEL_COUNT) newPersonnelCount,\n" +
                "\tSUM(NO_CONNECTION_COUNT) noConnectionCount,\n" +
                "\tSUM(IDNO_DISCREPANCY_COUNT) idnoDiscrepancyCount,\n" +
                "\tSUM(NOT_SIGN_GO_OUT) notSignGoOut,\n" +
                "\tSUM(FIELD_ARCHIVING_COUNT) fieldArchivingCount,\n" +
                "\tSUM(QUIT_COUNT) quitCount,\n" +
                "\tSUM(NOT_CARD_COUNT) notCardCount,\n" +
                "\tSUM(GO_ABROAD_COUNT) goAbroadCount,\n" +
                "\tSUM(OTHER_COUNT) otherCount,\n" +
                "\tSUM(UNFILLED_COUNT) unfilledCount,\n" +
                "\tSUM(SIGN_LOW_INSURED_COUNT) signLowInsuredCount\n" +
                "FROM\n" +
                "\tapp_manage_archiving_people\n" +
                "WHERE 1=1\n";
        if(address != null){
            if("2".equals(address.getLevel())){//各市数据统计
                map.put("areaCode",address.getCtcode().substring(0,4)+"%");
                sql +=" AND ADDR_COUNTY_CODE LIKE :areaCode";
                sqlCount += " AND STREET_CODE LIKE :areaCode";
                sqlSign += " AND ADDR_COUNTY_CODE LIKE :areaCode";
                sqlWqy += " AND STREET_CODE LIKE :areaCode";
                sqlLI += " AND ADDR_RURAL_CODE LIKE :areaCode ";
                sqlyb += " AND ADDR_RURAL_CODE LIKE :areaCode ";
                returnMap.put("code",AreaUtils.getAreaCode(address.getCtcode(),"2"));
            }else if("3".equals(address.getLevel())){//各区数据
                map.put("areaCode",address.getCtcode());
                sql +=" AND ADDR_COUNTY_CODE = :areaCode";
                sqlSign += " AND ADDR_COUNTY_CODE = :areaCode";
                sqlWqy += " AND STREET_CODE LIKE :ccodee ";
                map.put("ccodee",address.getCtcode().substring(0,6)+"%");
                if(AddressType.ZZS.getValue().equals(address.getCtcode().substring(0,4))){//漳州特别判断
                    //漳州“台商投资区”，“招商局漳州开发区”，“常山农场经济开发区”这三个区要特别判断
                    if("350698000000".equals(address.getCtcode())){//台商投资区
                        map.put("ccodee","350681102%");
                    }else if("350699000000".equals(address.getCtcode())){//招商局漳州开发区
                        map.put("ccodee","350681501%");
                    }else if("350697000000".equals(address.getCtcode())){//常山农场经济开发区
                        map.put("ccodee","350622450%");
                    }else if("350681000000".equals(address.getCtcode())){
                        map.put("notAreaCode",new String[]{"350681102000","350681501000","350622450000"});
                        sqlWqy += " AND STREET_CODE NOT IN (:notAreaCode) ";
                        sqlCount += " AND STREET_CODE NOT IN (:notAreaCode) ";
                        sqlLI += " AND ADDR_RURAL_CODE NOT IN (:notAreaCode) ";
                        sqlyb += " AND ADDR_RURAL_CODE NOT IN (:notAreaCode) ";
                    }else if("350622000000".equals(address.getCtcode())){
                        map.put("notAreaCode",new String[]{"350681102000","350681501000","350622450000"});
                        sqlWqy += " AND STREET_CODE NOT IN (:notAreaCode) ";
                        sqlCount += " AND STREET_CODE NOT IN (:notAreaCode) ";
                        sqlLI += " AND ADDR_RURAL_CODE NOT IN (:notAreaCode) ";
                        sqlyb += " AND ADDR_RURAL_CODE NOT IN (:notAreaCode) ";
                    }
                }
                sqlCount += " AND STREET_CODE LIKE :ccodee";
                sqlLI += " AND ADDR_RURAL_CODE LIKE :ccodee ";
                sqlyb += " AND ADDR_RURAL_CODE LIKE :ccodee ";
                returnMap.put("code",AreaUtils.getAreaCode(address.getCtcode(),"3"));
            }else if("4".equals(address.getLevel())){//各街道数据
                map.put("areaCode",address.getCtcode());
                sql +=" AND ADDR_RURAL_CODE = :areaCode";
                sqlSign += " AND ADDR_RURAL_CODE = :areaCode";
                sqlWqy += " AND STREET_CODE = :areaCode";
                sqlCount += " AND STREET_CODE = :areaCode";
                sqlLI += " AND ADDR_RURAL_CODE = :areaCode ";
                sqlyb += " AND ADDR_RURAL_CODE = :areaCode ";
                returnMap.put("code",AreaUtils.getAreaCode(address.getCtcode(),"4"));
            }else if("5".equals(address.getLevel())){//各居委会数据
                map.put("areaCode",address.getCtcode());
                sql +=" AND ADDR_VILLAGE_CODE = :areaCode";
//                sqlWqy += " AND ADDR_VILLAGE_CODE = :areaCode";
                sqlSign += " AND ADDR_VILLAGE_CODE = :areaCode";
                returnMap.put("code",address.getCtcode());
            }
            returnMap.put("name",address.getAreaSname());

        }
        if(hosp != null){
            map.put("hospId",hosp.getId());
            sql += " AND HOSP_ID =:hospId ";
            map.put("areaCode",hosp.getHospAreaCode());
            map.put("areaT",AreaUtils.getAreaCode(hosp.getHospAreaCode(),"3")+"000000");
            map.put("likeStreet",AreaUtils.getAreaCode(hosp.getHospAreaCode(),"4")+"%");
            sqlSign += " AND HOSP_ID =:hospId AND ADDR_COUNTY_CODE=:areaT ";
            if("3".equals(qvo.getJdSourceType())){
                sqlLI += " AND ADDR_HOSP_ID = :hospId ";
                sqlyb += " AND ADDR_HOSP_ID = :hospId ";
                sqlWqy += " AND ADDR_HOSP_ID = :hospId ";
                sqlCount += " AND ADDR_HOSP_ID = :hospId ";//机构未签数据
            }else{
                sqlCount += " AND STREET_CODE = :areaCode ";//机构未签数据
                sqlWqy += " AND STREET_CODE = :areaCode ";
                sqlLI += " AND ADDR_RURAL_CODE LIKE :likeStreet ";
                sqlyb += " AND ADDR_RURAL_CODE LIKE :likeStreet ";
            }
            returnMap.put("name",hosp.getHospName());
            returnMap.put("hospId",hosp.getId());
        }

//        String ql = "SELECT COUNT(1) FROM ("+sql + " GROUP BY ARCHIVING_PATIENT_IDNO ) cc";
//        totalArchivingCount = sysDao.getServiceDo().findCount(ql,map);

//        String sqlsign = sql;
        if(StringUtils.isNotBlank(qvo.getYearStart())){//统计年月
            map.put("startTime",qvo.getYearStart()+ " 00:00:00");
            map.put("startDate",ExtendDate.getFirstDayOfMonth(qvo.getYearStart().substring(0,7))+" 00:00:00");
            sqlSign += " AND SIGN_FROM_DATE>=:startDate ";
            map.put("ksTime",qvo.getYearStart().substring(0,7));
            sqlWqy += " AND MANAGE_YEAR_MONTH >=:ksTime ";
            sqlCount += " AND MANAGE_YEAR_MONTH >=:ksTime ";
            sqlLI += " AND HS_CREATE_DATE >=:startDate ";
            sqlyb += " AND HS_CREATE_DATE >=:startDate ";
            sql += " AND HS_CREATE_DATE >=:startDate ";
//            sqlsign += " AND SIGN_FROM_DATE>=:startDate";
        }
        if(StringUtils.isNotBlank(qvo.getYearEnd())){
            map.put("endTime",qvo.getYearEnd()+" 23:59:59");
            map.put("endDate",ExtendDate.getLastDayOfMonth(qvo.getYearEnd().substring(0,7))+" 23:59:59");
            sqlSign += " AND SIGN_FROM_DATE<=:endDate ";
            map.put("jsTime",qvo.getYearEnd().substring(0,7));
            sqlWqy += " AND MANAGE_YEAR_MONTH <=:jsTime ";
            sqlCount += " AND MANAGE_YEAR_MONTH <=:jsTime ";
            sqlLI += " AND HS_CREATE_DATE <=:endDate ";
            sqlyb += " AND HS_CREATE_DATE <=:endDate ";
            sql += " AND HS_CREATE_DATE <=:endDate ";
//            sqlsign += " AND SIGN_FROM_DATE<=:endDate";
        }

        //计算低保户人数
        lowInsuredCount = sysDao.getServiceDo().findCount(sqlLI,map);
        //计算医保建档立卡人数
        totalArCountYb = sysDao.getServiceDo().findCount(sqlyb,map);
//
        List<Map> notSignlist = sysDao.getServiceDo().findSqlMap(sqlCount,map);
        List<Map> signList = sysDao.getServiceDo().findSqlMap(sqlWqy,map);
        if(signList != null && signList.size()>0){
            if(signList.get(0).get("totalSignCount") != null){//累计签约数
                totalSignCount = (int)Double.parseDouble(signList.get(0).get("totalSignCount").toString());
            }
        }
        if(notSignlist != null && notSignlist.size()>0){
            if(notSignlist.get(0).get("dieCount") != null){//未签约死亡数
                dieCount = (int)Double.parseDouble(notSignlist.get(0).get("dieCount").toString());
            }
            if(notSignlist.get(0).get("missCount") != null){//失联失踪人数
                missCount = (int)Double.parseDouble(notSignlist.get(0).get("missCount").toString());
            }
            if(notSignlist.get(0).get("moveOutCount") != null){//迁出人数
                moveOutCount = (int)Double.parseDouble(notSignlist.get(0).get("moveOutCount").toString());
            }
            if(notSignlist.get(0).get("longTimeOutCount") != null){//长期外出人数
                longTimeOutCount = (int)Double.parseDouble(notSignlist.get(0).get("longTimeOutCount").toString());
            }
            if(notSignlist.get(0).get("refuseSignCount") != null){//拒签人数
                refuseSignCount = (int)Double.parseDouble(notSignlist.get(0).get("refuseSignCount").toString());
            }
            if(notSignlist.get(0).get("beExecotedCount") != null){//服刑人数
                beExecotedCount = (int)Double.parseDouble(notSignlist.get(0).get("beExecotedCount").toString());
            }
            if(notSignlist.get(0).get("nameRepeatCount") != null){//名单重复人数
                nameRepeatCount = (int)Double.parseDouble(notSignlist.get(0).get("nameRepeatCount").toString());
            }
            if(notSignlist.get(0).get("mentalPatientCount") != null){//精神病人住康复医院，家属外出人数
                mentalPatientCount = (int)Double.parseDouble(notSignlist.get(0).get("mentalPatientCount").toString());
            }
            if(notSignlist.get(0).get("performMilitaryService") != null){//服兵役人数
                performMilitaryService = (int)Double.parseDouble(notSignlist.get(0).get("performMilitaryService").toString());
            }
            if(notSignlist.get(0).get("foreignNationality") != null){//外籍人数
                foreignNationality = (int)Double.parseDouble(notSignlist.get(0).get("foreignNationality").toString());
            }
            if(notSignlist.get(0).get("marryOutCount") != null){//外嫁人数（未入户）
                marryOutCount = (int)Double.parseDouble(notSignlist.get(0).get("marryOutCount").toString());
            }
            if(notSignlist.get(0).get("newPersonnelCount") != null){//新增人员
                newPersonnelCount = (int)Double.parseDouble(notSignlist.get(0).get("newPersonnelCount").toString());
            }
            if(notSignlist.get(0).get("noConnectionCount") != null){//联系不上人数
                noConnectionCount = (int)Double.parseDouble(notSignlist.get(0).get("noConnectionCount").toString());
            }
            if(notSignlist.get(0).get("idnoDiscrepancyCount") != null){//身份名字不符人数
                idnoDiscrepancyCount = (int)Double.parseDouble(notSignlist.get(0).get("idnoDiscrepancyCount").toString());
            }
            if(notSignlist.get(0).get("notSignGoOut") != null){//暂时外出无法签约
                notSignGoOut = (int)Double.parseDouble(notSignlist.get(0).get("notSignGoOut").toString());
            }
            if(notSignlist.get(0).get("fieldArchivingCount") != null){//外地建档人数
                fieldArchivingCount = (int)Double.parseDouble(notSignlist.get(0).get("fieldArchivingCount").toString());
            }
            if(notSignlist.get(0).get("quitCount") != null){//退出人数（异地签约）
                quitCount = (int)Double.parseDouble(notSignlist.get(0).get("quitCount").toString());
            }
            if(notSignlist.get(0).get("notCardCount") != null){//无社保卡人数
                notCardCount = (int)Double.parseDouble(notSignlist.get(0).get("notCardCount").toString());
            }
            if(notSignlist.get(0).get("goAbroadCount") != null){//出国人数
                goAbroadCount = (int)Double.parseDouble(notSignlist.get(0).get("goAbroadCount").toString());
            }
            if(notSignlist.get(0).get("otherCount") != null){//其它人数
                otherCount = (int)Double.parseDouble(notSignlist.get(0).get("otherCount").toString());
            }
            if(notSignlist.get(0).get("unfilledCount") != null){//未填写人数
                unfilledCount = (int)Double.parseDouble(notSignlist.get(0).get("unfilledCount").toString());
            }
            if(notSignlist.get(0).get("manageTotal") != null){//合计
                total = (int)Double.parseDouble(notSignlist.get(0).get("manageTotal").toString());
            }
            if(notSignlist.get(0).get("signLowInsuredCount") != null){//累计签约人数中低保户人数
                signLowInsuredCount = (int)Double.parseDouble(notSignlist.get(0).get("signLowInsuredCount").toString());
            }
        }
//        if(hosp != null){
//            String sss ="SELECT COUNT(1) FROM (" + sqlSign + "  GROUP BY ARCHIVING_PATIENT_IDNO ) cc";
//            totalSignCount = sysDao.getServiceDo().findCount(sss,map);//累计签约数
//        }

//        String wqySql = "SELECT COUNT(1) from ("+sqlWqy+" GROUP BY ARCHIVING_PATIENT_IDNO ) cc";
//        total = sysDao.getServiceDo().findCount(wqySql,map);
        totalArchivingCount = total+totalSignCount;
        if("3".equals(qvo.getJdSourceType())){
            totalArchivingCount = totalArCountYb;
        }

        returnMap.put("totalArchivingCount",totalArchivingCount);//建档立卡人员数
        returnMap.put("totalSignCount",totalSignCount);//累计已签约人数
        returnMap.put("total",total);//合计
        returnMap.put("dieCount",dieCount);//未签约死亡数
        returnMap.put("missCount",missCount);//失联失踪人数
        returnMap.put("moveOutCount",moveOutCount);//迁出人数
        returnMap.put("longTimeOutCount",longTimeOutCount);//长期外出人数
        returnMap.put("refuseSignCount",refuseSignCount);//拒签人数
        returnMap.put("beExecotedCount",beExecotedCount);//服刑人数
        returnMap.put("nameRepeatCount",nameRepeatCount);//名单重复人数
        returnMap.put("mentalPatientCount",mentalPatientCount);//精神病人住康复医院，家属外出人数
        returnMap.put("performMilitaryService",performMilitaryService);//服兵役人数
        returnMap.put("foreignNationality",foreignNationality);//外籍人数
        returnMap.put("marryOutCount",marryOutCount);//外嫁人数
        returnMap.put("newPersonnelCount",newPersonnelCount);//新增人员
        returnMap.put("noConnectionCount",noConnectionCount);//联系不上人数
        returnMap.put("idnoDiscrepancyCount",idnoDiscrepancyCount);//身份名字不符人数
        returnMap.put("notSignGoOut",notSignGoOut);//暂时外出无法签约
        returnMap.put("fieldArchivingCount",fieldArchivingCount);//外地建档人数
        returnMap.put("quitCount",quitCount);//退出人数
        returnMap.put("notCardCount",notCardCount);//无社保卡人数
        returnMap.put("goAbroadCount",goAbroadCount);//出国人数
        returnMap.put("otherCount",otherCount);//其它人数
        returnMap.put("unfilledCount",unfilledCount);//未填写人数
        returnMap.put("lowInsuredCount",lowInsuredCount);//低保户人数
        returnMap.put("signLowInsuredCount",signLowInsuredCount);//已签约低保户人数
        totalYbArchivingCount = totalArchivingCount +lowInsuredCount;//医保总建档立卡数合计
        totalYbSignArchivingCount = totalSignCount + signLowInsuredCount;//医保已签约总建档立卡数合计
        returnMap.put("totalYbArchivingCount",totalYbArchivingCount);//医保总建档立卡数合计
        returnMap.put("totalYbSignArchivingCount",totalYbSignArchivingCount);//医保已签约总建档立卡数合计
        return returnMap;
    }

    public Map<String,Object> getArchivingCardCountByCity(CdAddress address,ResidentVo qvo) throws Exception{
        int totalArchivingCount = 0;//建档立卡人员数
        int totalSignCount = 0;//累计已签约人数
        int total = 0;//合计数
        int dieCount = 0;//未签约死亡数
        int missCount = 0;//失联失踪人数
        int moveOutCount = 0;//迁出人数
        int longTimeOutCount = 0;//长期外出人数
        int refuseSignCount = 0;//拒签人数
        int beExecotedCount = 0;//服刑人数
        int nameRepeatCount = 0;//名单重复人数
        int mentalPatientCount = 0;//精神病人住康复医院，家属外出人数
        int performMilitaryService = 0;//服兵役人数
        int foreignNationality = 0;//外籍人数
        int marryOutCount = 0;//外嫁人数
        int newPersonnelCount = 0;//新增人员
        int noConnectionCount = 0;//联系不上人数
        int idnoDiscrepancyCount = 0;//身份名字不符人数
        int notSignGoOut = 0;//暂时外出无法签约
        int fieldArchivingCount = 0;//外地建档人数
        int quitCount = 0;//退出人数
        int notCardCount = 0;//无社保卡人数
        int goAbroadCount = 0;//出国人数
        int otherCount = 0;//其它人数
        int unfilledCount = 0;//未填写人数
        Map<String,Object> returnMap = new HashMap<>();
        Map<String,Object> map = new HashMap<>();
        map.put("cityCode",address.getCtcode());
        String sql = "SELECT * from app_manage_archiving_people WHERE CITY_CODE =:cityCode";
        List<AppManageArchivingPeople> list = sysDao.getServiceDo().findSqlMap(sql,map,AppArchivingCardPeople.class);
        if(list != null && list.size()>0){
            AppManageArchivingPeople vv = list.get(0);
            if(StringUtils.isNotBlank(vv.getTotalArchivingCount())){
                totalArchivingCount = Integer.parseInt(vv.getTotalArchivingCount());
            }
            if(StringUtils.isNotBlank(vv.getTotalSignCount())){
                totalSignCount = Integer.parseInt(vv.getTotalSignCount());
            }
            if(StringUtils.isNotBlank(vv.getDieCount())){
                dieCount = Integer.parseInt(vv.getDieCount());
            }
            if(StringUtils.isNotBlank(vv.getMissCount())){
                missCount = Integer.parseInt(vv.getMissCount());
            }
            if(StringUtils.isNotBlank(vv.getMoveOutCount())){
                moveOutCount = Integer.parseInt(vv.getMoveOutCount());
            }
            if(StringUtils.isNotBlank(vv.getLongTimeOutCount())){
                longTimeOutCount = Integer.parseInt(vv.getLongTimeOutCount());
            }
            if(StringUtils.isNotBlank(vv.getRefuseSignCount())){
                refuseSignCount = Integer.parseInt(vv.getRefuseSignCount());
            }
            if(StringUtils.isNotBlank(vv.getBeExecotedCount())){
                beExecotedCount = Integer.parseInt(vv.getBeExecotedCount());
            }
            if(StringUtils.isNotBlank(vv.getNameRepeatCount())){
                nameRepeatCount = Integer.parseInt(vv.getNameRepeatCount());
            }
            if(StringUtils.isNotBlank(vv.getMentalPatientCount())){
                mentalPatientCount = Integer.parseInt(vv.getMentalPatientCount());
            }
            if(StringUtils.isNotBlank(vv.getPerformMilitaryService())){
                performMilitaryService = Integer.parseInt(vv.getPerformMilitaryService());
            }
            if(StringUtils.isNotBlank(vv.getForeignNationality())){
                foreignNationality = Integer.parseInt(vv.getForeignNationality());
            }
            if(StringUtils.isNotBlank(vv.getMarryOutCount())){
                marryOutCount = Integer.parseInt(vv.getMarryOutCount());
            }
            if(StringUtils.isNotBlank(vv.getNewPersonnelCount())){
                newPersonnelCount = Integer.parseInt(vv.getNewPersonnelCount());
            }
            if(StringUtils.isNotBlank(vv.getNoConnectionCount())){
                noConnectionCount = Integer.parseInt(vv.getNoConnectionCount());
            }
            if(StringUtils.isNotBlank(vv.getIdnoDiscrepancyCount())){
                idnoDiscrepancyCount = Integer.parseInt(vv.getIdnoDiscrepancyCount());
            }
            if(StringUtils.isNotBlank(vv.getNotSignGoOut())){
                notSignGoOut = Integer.parseInt(vv.getNotSignGoOut());
            }
            if(StringUtils.isNotBlank(vv.getFieldArchivingCount())){
                fieldArchivingCount = Integer.parseInt(vv.getFieldArchivingCount());
            }
            if(StringUtils.isNotBlank(vv.getQuitCount())){
                quitCount = Integer.parseInt(vv.getQuitCount());
            }
            if(StringUtils.isNotBlank(vv.getNotCardCount())){
                notCardCount = Integer.parseInt(vv.getNotCardCount());
            }
            if(StringUtils.isNotBlank(vv.getGoAbroadCount())){
                goAbroadCount = Integer.parseInt(vv.getGoAbroadCount());
            }
            if(StringUtils.isNotBlank(vv.getOtherCount())){
                otherCount = Integer.parseInt(vv.getOtherCount());
            }
            if(StringUtils.isNotBlank(vv.getUnfilledCount())){
                unfilledCount = Integer.parseInt(vv.getUnfilledCount());
            }
        }
        total = dieCount+missCount+moveOutCount+longTimeOutCount+refuseSignCount+
                beExecotedCount+nameRepeatCount+mentalPatientCount+performMilitaryService+foreignNationality+
                marryOutCount+newPersonnelCount+noConnectionCount+idnoDiscrepancyCount+notSignGoOut+fieldArchivingCount+
                quitCount+notCardCount+goAbroadCount+otherCount;
        returnMap.put("name",address.getAreaSname());
        returnMap.put("code",AreaUtils.getAreaCode(address.getCtcode()));
        returnMap.put("totalArchivingCount",totalArchivingCount);//建档立卡人员数
        returnMap.put("totalSignCount",totalSignCount);//累计已签约人数
        returnMap.put("total",total);//合计
        returnMap.put("dieCount",dieCount);//未签约死亡数
        returnMap.put("missCount",missCount);//失联失踪人数
        returnMap.put("moveOutCount",moveOutCount);//迁出人数
        returnMap.put("longTimeOutCount",longTimeOutCount);//长期外出人数
        returnMap.put("refuseSignCount",refuseSignCount);//拒签人数
        returnMap.put("beExecotedCount",beExecotedCount);//服刑人数
        returnMap.put("nameRepeatCount",nameRepeatCount);//名单重复人数
        returnMap.put("mentalPatientCount",mentalPatientCount);//精神病人住康复医院，家属外出人数
        returnMap.put("performMilitaryService",performMilitaryService);//服兵役人数
        returnMap.put("foreignNationality",foreignNationality);//外籍人数
        returnMap.put("marryOutCount",marryOutCount);//外嫁人数
        returnMap.put("newPersonnelCount",newPersonnelCount);//新增人员
        returnMap.put("noConnectionCount",noConnectionCount);//联系不上人数
        returnMap.put("idnoDiscrepancyCount",idnoDiscrepancyCount);//身份名字不符人数
        returnMap.put("notSignGoOut",notSignGoOut);//暂时外出无法签约
        returnMap.put("fieldArchivingCount",fieldArchivingCount);//外地建档人数
        returnMap.put("quitCount",quitCount);//退出人数
        returnMap.put("notCardCount",notCardCount);//无社保卡人数
        returnMap.put("goAbroadCount",goAbroadCount);//出国人数
        returnMap.put("otherCount",otherCount);//其它人数
        returnMap.put("unfilledCount",unfilledCount);//未填写人数
        return returnMap;
    }

    @Override
    public List<Map<String, Object>> archivingCardServeCount(ResidentVo qvo) throws Exception {
        List<Map<String,Object>> list = new ArrayList<>();
        if(StringUtils.isNotBlank(qvo.getAreaId())){
            if(qvo.getAreaId().length() == 2){//省 区划
                qvo.setAreaId(qvo.getAreaId()+"0000000000");
            }else if(qvo.getAreaId().length() == 4){//市 区划
                qvo.setAreaId(qvo.getAreaId()+"00000000");
            }else if(qvo.getAreaId().length() == 6){//区 区划
                qvo.setAreaId(qvo.getAreaId()+"000000");
            }else if(qvo.getAreaId().length() == 9){//街道 区划
                qvo.setAreaId(qvo.getAreaId()+"000");
            }
            int totalArchivingCount = 0;//建档立卡人员数
            int plainCount = 0;//一般人群人数
            int keySignCount = 0;//重点人群人数
            int notSignCount = 0;//未落实签约人数(人)
            int signCount = 0;//已签约总人数(人)
            int signPlainCount = 0;//一般人群人数(签约人数)
            int signKeySignCount = 0;//重点人群人数(签约人数)
            int childCount = 0;//0-6岁儿童数(人)
            int signChildCount = 0;//0-6岁儿童签约人数
            int oldPeopleCount = 0;//65岁及以上老年人人数
            int signOldPeopleCount = 0;//65岁及以上老年人签约人数
            int maternalCount = 0;//孕产妇人数
            int signMaternalCount = 0;//孕产妇签约人数
            int bloodCount = 0;//已管理的高血压患者人数
            int signBloodCount = 0;//已管理的高血压患者签约人数
            int diabetesCount = 0;//已管理的糖尿病患者人数
            int signDiabetesCount = 0;//已管理的糖尿病患者签约人数
            int phthisisCount = 0;//已管理的肺结核患者人数
            int signPhthisisCount = 0;//已管理的肺结核患者签约人数
            int psychosisCount = 0;//已管理的严重精神障碍患者人数
            int signPsychosisCount = 0;//已管理的严重精神障碍患者签约人数
            int disabledPeopleCount = 0;//残疾人患者人数
            int signDisabledPeopleCount = 0;//残疾人患者签约人数
            int specialPlanFamilyCount = 0;//计划生育特殊家庭人数
            int signSpecialPlanFamilyCount = 0;//计划生育特殊家庭自愿签约人数
            int povertyCount = 0;//脱贫人数
            //先查对照表
            CdAddressConfiguration ccf = sysDao.getCdAddressDao().findByCodeJw(qvo.getAreaId());
            if(ccf != null){
                qvo.setAreaId(ccf.getId());
            }
            if (AreaUtils.getAreaCode(qvo.getAreaId()).length() == 6 || AreaUtils.getAreaCode(qvo.getAreaId()).length() == 5) { //区级行政，往下查看医院的排名
                //区下的医院集合
                List<AppHospDept> hospDepts = sysDao.getAppHospDeptDao().findUpHospListRead(qvo.getAreaId());
                if(hospDepts != null && hospDepts.size()>0){
                    for(AppHospDept hosp:hospDepts){
                        if(hosp != null ){
                            if(hosp.getHospState().equals("1")){
                                Map<String, Object> map = getArchivingCardServeCount(null,hosp,qvo);
                                list.add(map);
                                totalArchivingCount +=  Integer.parseInt(map.get("totalArchivingCount").toString());//建档立卡人员数
                                plainCount +=  Integer.parseInt(map.get("plainCount").toString());//一般人群人数
                                keySignCount +=  Integer.parseInt(map.get("keySignCount").toString());//重点人群人数
                                notSignCount +=  Integer.parseInt(map.get("notSignCount").toString());//未落实签约人数(人).toString());
                                signCount +=  Integer.parseInt(map.get("signCount").toString());//已签约总人数(人).toString());
                                signPlainCount +=  Integer.parseInt(map.get("signPlainCount").toString());//一般人群人数(签约人数).toString());
                                signKeySignCount +=  Integer.parseInt(map.get("signKeySignCount").toString());//重点人群人数(签约人数).toString());
                                childCount +=  Integer.parseInt(map.get("childCount").toString());//0-6岁儿童数(人).toString());
                                signChildCount +=  Integer.parseInt(map.get("signChildCount").toString());//0-6岁儿童签约人数
                                oldPeopleCount +=  Integer.parseInt(map.get("oldPeopleCount").toString());//65岁及以上老年人人数
                                signOldPeopleCount +=  Integer.parseInt(map.get("signOldPeopleCount").toString());//65岁及以上老年人签约人数
                                maternalCount +=  Integer.parseInt(map.get("maternalCount").toString());//孕产妇人数
                                signMaternalCount +=  Integer.parseInt(map.get("signMaternalCount").toString());//孕产妇签约人数
                                bloodCount +=  Integer.parseInt(map.get("bloodCount").toString());//已管理的高血压患者人数
                                signBloodCount +=  Integer.parseInt(map.get("signBloodCount").toString());//已管理的高血压患者签约人数
                                diabetesCount +=  Integer.parseInt(map.get("diabetesCount").toString());//已管理的糖尿病患者人数
                                signDiabetesCount +=  Integer.parseInt(map.get("signDiabetesCount").toString());//已管理的糖尿病患者签约人数
                                phthisisCount +=  Integer.parseInt(map.get("phthisisCount").toString());//已管理的肺结核患者人数
                                signPhthisisCount +=  Integer.parseInt(map.get("signPhthisisCount").toString());//已管理的肺结核患者签约人数
                                psychosisCount +=  Integer.parseInt(map.get("psychosisCount").toString());//已管理的严重精神障碍患者人数
                                signPsychosisCount +=  Integer.parseInt(map.get("signPsychosisCount").toString());//已管理的严重精神障碍患者签约人数
                                disabledPeopleCount +=  Integer.parseInt(map.get("disabledPeopleCount").toString());//残疾人患者人数
                                signDisabledPeopleCount +=  Integer.parseInt(map.get("signDisabledPeopleCount").toString());//残疾人患者签约人数
                                specialPlanFamilyCount += Integer.parseInt(map.get("specialPlanFamilyCount").toString());//计划生育特殊家庭人数
                                signSpecialPlanFamilyCount +=  Integer.parseInt(map.get("signSpecialPlanFamilyCount").toString());//计划生育特殊家庭自愿签约人数
                                povertyCount += Integer.parseInt(map.get("povertyCount").toString());
                            }
                        }
                    }
                    Map<String,Object> returnMap = new HashMap<>();
                    returnMap.put("name","合计");
                    returnMap.put("code","");
                    returnMap.put("totalArchivingCount",totalArchivingCount);//建档立卡人员数
                    returnMap.put("plainCount",plainCount);//一般人群人数
                    returnMap.put("keySignCount",keySignCount);//重点人群人数
                    returnMap.put("notSignCount",notSignCount);//未落实签约人数(人)
                    returnMap.put("signCount",signCount);//已签约总人数(人)
                    returnMap.put("signPlainCount",signPlainCount);//一般人群人数(签约人数)
                    returnMap.put("signKeySignCount",signKeySignCount);//重点人群人数(签约人数)
                    returnMap.put("childCount",childCount);//0-6岁儿童数(人)
                    returnMap.put("signChildCount",signChildCount);//0-6岁儿童签约人数
                    returnMap.put("oldPeopleCount",oldPeopleCount);//65岁及以上老年人人数
                    returnMap.put("signOldPeopleCount",signOldPeopleCount);//65岁及以上老年人签约人数
                    returnMap.put("maternalCount",maternalCount);//孕产妇人数
                    returnMap.put("signMaternalCount",signMaternalCount);//孕产妇签约人数
                    returnMap.put("bloodCount",bloodCount);//已管理的高血压患者人数
                    returnMap.put("signBloodCount",signBloodCount);//已管理的高血压患者签约人数
                    returnMap.put("diabetesCount",diabetesCount);//已管理的糖尿病患者人数
                    returnMap.put("signDiabetesCount",signDiabetesCount);//已管理的糖尿病患者签约人数
                    returnMap.put("phthisisCount",phthisisCount);//已管理的肺结核患者人数
                    returnMap.put("signPhthisisCount",signPhthisisCount);//已管理的肺结核患者签约人数
                    returnMap.put("psychosisCount",psychosisCount);//已管理的严重精神障碍患者人数
                    returnMap.put("signPsychosisCount",signPsychosisCount);//已管理的严重精神障碍患者签约人数
                    returnMap.put("disabledPeopleCount",disabledPeopleCount);//残疾人患者人数
                    returnMap.put("signDisabledPeopleCount",signDisabledPeopleCount);//残疾人患者签约人数
                    returnMap.put("specialPlanFamilyCount",specialPlanFamilyCount);//计划生育特殊家庭人数)
                    returnMap.put("signSpecialPlanFamilyCount",signSpecialPlanFamilyCount);//计划生育特殊家庭自愿签约人数
                    returnMap.put("povertyCount",povertyCount);
                    list.add(returnMap);
                }
            }else{
                List<CdAddress> lsCdAddress = sysDao.getCdAddressDao().findUpCasheAreaCode(qvo.getAreaId());
                if(lsCdAddress != null && lsCdAddress.size()>0) {
                    for (CdAddress address : lsCdAddress) {
                        String code = address.getCtcode().substring(0, 4);
                        if (AddressType.FZS.getValue().equals(code) || AddressType.SMS.getValue().equals(code) || AddressType.ZZS.getValue().equals(code) ||
                                AddressType.QZS.getValue().equals(code) || AddressType.PTS.getValue().equals(code) || AddressType.NPS.getValue().equals(code)) {
                            Map<String, Object> map = getArchivingCardServeCount(address,null,qvo);
                            list.add(map);
                            totalArchivingCount +=  Integer.parseInt(map.get("totalArchivingCount").toString());//建档立卡人员数
                            plainCount +=  Integer.parseInt(map.get("plainCount").toString());//一般人群人数
                            keySignCount +=  Integer.parseInt(map.get("keySignCount").toString());//重点人群人数
                            notSignCount +=  Integer.parseInt(map.get("notSignCount").toString());//未落实签约人数(人).toString());
                            signCount +=  Integer.parseInt(map.get("signCount").toString());//已签约总人数(人).toString());
                            signPlainCount +=  Integer.parseInt(map.get("signPlainCount").toString());//一般人群人数(签约人数).toString());
                            signKeySignCount +=  Integer.parseInt(map.get("signKeySignCount").toString());//重点人群人数(签约人数).toString());
                            childCount +=  Integer.parseInt(map.get("childCount").toString());//0-6岁儿童数(人).toString());
                            signChildCount +=  Integer.parseInt(map.get("signChildCount").toString());//0-6岁儿童签约人数
                            oldPeopleCount +=  Integer.parseInt(map.get("oldPeopleCount").toString());//65岁及以上老年人人数
                            signOldPeopleCount +=  Integer.parseInt(map.get("signOldPeopleCount").toString());//65岁及以上老年人签约人数
                            maternalCount +=  Integer.parseInt(map.get("maternalCount").toString());//孕产妇人数
                            signMaternalCount +=  Integer.parseInt(map.get("signMaternalCount").toString());//孕产妇签约人数
                            bloodCount +=  Integer.parseInt(map.get("bloodCount").toString());//已管理的高血压患者人数
                            signBloodCount +=  Integer.parseInt(map.get("signBloodCount").toString());//已管理的高血压患者签约人数
                            diabetesCount +=  Integer.parseInt(map.get("diabetesCount").toString());//已管理的糖尿病患者人数
                            signDiabetesCount +=  Integer.parseInt(map.get("signDiabetesCount").toString());//已管理的糖尿病患者签约人数
                            phthisisCount +=  Integer.parseInt(map.get("phthisisCount").toString());//已管理的肺结核患者人数
                            signPhthisisCount +=  Integer.parseInt(map.get("signPhthisisCount").toString());//已管理的肺结核患者签约人数
                            psychosisCount +=  Integer.parseInt(map.get("psychosisCount").toString());//已管理的严重精神障碍患者人数
                            signPsychosisCount +=  Integer.parseInt(map.get("signPsychosisCount").toString());//已管理的严重精神障碍患者签约人数
                            disabledPeopleCount +=  Integer.parseInt(map.get("disabledPeopleCount").toString());//残疾人患者人数
                            signDisabledPeopleCount +=  Integer.parseInt(map.get("signDisabledPeopleCount").toString());//残疾人患者签约人数
                            specialPlanFamilyCount += Integer.parseInt(map.get("specialPlanFamilyCount").toString());//计划生育特殊家庭人数
                            signSpecialPlanFamilyCount +=  Integer.parseInt(map.get("signSpecialPlanFamilyCount").toString());//计划生育特殊家庭自愿签约人数
                            povertyCount += Integer.parseInt(map.get("povertyCount").toString());
                        }
                    }
                    Map<String,Object> returnMap = new HashMap<>();
                    returnMap.put("name","合计");
                    returnMap.put("code","");
                    returnMap.put("totalArchivingCount",totalArchivingCount);//建档立卡人员数
                    returnMap.put("plainCount",plainCount);//一般人群人数
                    returnMap.put("keySignCount",keySignCount);//重点人群人数
                    returnMap.put("notSignCount",notSignCount);//未落实签约人数(人)
                    returnMap.put("signCount",signCount);//已签约总人数(人)
                    returnMap.put("signPlainCount",signPlainCount);//一般人群人数(签约人数)
                    returnMap.put("signKeySignCount",signKeySignCount);//重点人群人数(签约人数)
                    returnMap.put("childCount",childCount);//0-6岁儿童数(人)
                    returnMap.put("signChildCount",signChildCount);//0-6岁儿童签约人数
                    returnMap.put("oldPeopleCount",oldPeopleCount);//65岁及以上老年人人数
                    returnMap.put("signOldPeopleCount",signOldPeopleCount);//65岁及以上老年人签约人数
                    returnMap.put("maternalCount",maternalCount);//孕产妇人数
                    returnMap.put("signMaternalCount",signMaternalCount);//孕产妇签约人数
                    returnMap.put("bloodCount",bloodCount);//已管理的高血压患者人数
                    returnMap.put("signBloodCount",signBloodCount);//已管理的高血压患者签约人数
                    returnMap.put("diabetesCount",diabetesCount);//已管理的糖尿病患者人数
                    returnMap.put("signDiabetesCount",signDiabetesCount);//已管理的糖尿病患者签约人数
                    returnMap.put("phthisisCount",phthisisCount);//已管理的肺结核患者人数
                    returnMap.put("signPhthisisCount",signPhthisisCount);//已管理的肺结核患者签约人数
                    returnMap.put("psychosisCount",psychosisCount);//已管理的严重精神障碍患者人数
                    returnMap.put("signPsychosisCount",signPsychosisCount);//已管理的严重精神障碍患者签约人数
                    returnMap.put("disabledPeopleCount",disabledPeopleCount);//残疾人患者人数
                    returnMap.put("signDisabledPeopleCount",signDisabledPeopleCount);//残疾人患者签约人数
                    returnMap.put("specialPlanFamilyCount",specialPlanFamilyCount);//计划生育特殊家庭人数)
                    returnMap.put("signSpecialPlanFamilyCount",signSpecialPlanFamilyCount);//计划生育特殊家庭自愿签约人数
                    returnMap.put("povertyCount",povertyCount);
                    list.add(returnMap);
                }
            }
        }
        return list;
    }

    public Map<String,Object> getArchivingCardServeCount(CdAddress address,AppHospDept hosp,ResidentVo qvo) throws Exception{
        Map<String,Object> returnMap = new HashMap<>();
        Map<String,Object> map = new HashMap<>();
        int totalArchivingCount = 0;//建档立卡人员数
        int plainCount = 0;//一般人群人数
        int keySignCount = 0;//重点人群人数
        int notSignCount = 0;//未落实签约人数(人)
        int signCount = 0;//已签约总人数(人)
        int signPlainCount = 0;//一般人群人数(签约人数)
        int signKeySignCount = 0;//重点人群人数(签约人数)
        int childCount = 0;//0-6岁儿童数(人)
        int signChildCount = 0;//0-6岁儿童签约人数
        int oldPeopleCount = 0;//65岁及以上老年人人数
        int signOldPeopleCount = 0;//65岁及以上老年人签约人数
        int maternalCount = 0;//孕产妇人数
        int signMaternalCount = 0;//孕产妇签约人数
        int bloodCount = 0;//已管理的高血压患者人数
        int signBloodCount = 0;//已管理的高血压患者签约人数
        int diabetesCount = 0;//已管理的糖尿病患者人数
        int signDiabetesCount = 0;//已管理的糖尿病患者签约人数
        int phthisisCount = 0;//已管理的肺结核患者人数
        int signPhthisisCount = 0;//已管理的肺结核患者签约人数
        int psychosisCount = 0;//已管理的严重精神障碍患者人数
        int signPsychosisCount = 0;//已管理的严重精神障碍患者签约人数
        int disabledPeopleCount = 0;//残疾人患者人数
        int signDisabledPeopleCount = 0;//残疾人患者签约人数
        int specialPlanFamilyCount =0;//计划生育特殊家庭人数
        int signSpecialPlanFamilyCount = 0;//计划生育特殊家庭自愿签约人数
        int povertyCount = 0;//脱贫人数
        map.put("SOURCE_TYPE",qvo.getJdSourceType());
        String poverSql = " SELECT * FROM app_archivingcard_people WHERE 1=1 AND IS_NOT_POVERTY ='1' AND SOURCE_TYPE =:SOURCE_TYPE ";

//        String arcSql = "SELECT * FROM app_archivingcard_people WHERE 1=1 and SIGN_ID IS NULL AND SOURCE_TYPE ='1' ";
        String sql = "SELECT " +
                "SUM(MANAGE_NOT_SIGN_COUNT) manageNotSignCount,\n" +
                "SUM(MANAGE_SIGN_COUNT) manageSignCount, \n" +
                "SUM(MANAGE_KEY_SIGN_COUNT) manageKeySignCount, \n" +
                "SUM(MANAGE_PLAIN_COUNT) managePlainCount, \n" +
                "SUM(MANAGE_CHILD_COUNT) manageChildCount, \n" +
                "SUM(MANAGE_MATERNAL_COUNT) manageMaternalCount, \n" +
                "SUM(MANAGE_OLD_PEOPLE_COUNT) manageOldPeopleCount, \n" +
                "SUM(MANAGE_BLOOD_COUNT) manageBloodCount, \n" +
                "SUM(MANAGE_DIABETES_COUNT) manageDiabetesCount, \n" +
                "SUM(MANAGE_PSYCHOSIS_COUNT) managePsychosisCount, \n" +
                "SUM(MANAGE_PHTHISIS_COUNT) managePhthisisCount, \n" +
                "SUM(MANAGE_DISABLED_PEOPLE_COUNT) manageDisabledPeopleCount, \n" +
                "SUM(MANAGE_SERVICE_UNKNOWN_COUNT) manageServiceUnknownCount \n" +
                "FROM APP_MANAGE_ARCHIVING_ALL_COUNT WHERE 1=1 ";
        String signSql = " SELECT " +
                "SUM(MANAGE_SIGN_COUNT) manageSignCount,\n" +
                "SUM(MANAGE_KEY_SIGN_COUNT) manageKeySignCount,\n" +
                "SUM(MANAGE_PLAIN_COUNT) managePlainCount,\n" +
                "SUM(MANAGE_CHILD_COUNT) manageChildCount,\n" +
                "SUM(MANAGE_MATERNAL_COUNT) manageMaternalCount,\n" +
                "SUM(MANAGE_OLD_PEOPLE_COUNT) manageOldPeopleCount,\n" +
                "SUM(MANAGE_BLOOD_COUNT) manageBloodCount,\n" +
                "SUM(MANAGE_DIABETES_COUNT) manageDiabetesCount,\n" +
                "SUM(MANAGE_PSYCHOSIS_COUNT) managePsychosisCount,\n" +
                "SUM(MANAGE_PHTHISIS_COUNT) managePhthisisCount,\n" +
                "SUM(MANAGE_DISABLED_PEOPLE_COUNT) manageDisabledPeopleCount,\n" +
                "SUM(MANAGE_SERVICE_UNKNOWN_COUNT) manageServiceUnknownCount,\n" +
                "SUM(MANAGE_SPECIAL_PLAN_FAMILY_COUNT) manageSpecialPlanFamilyCount " +
                " FROM APP_MANAGE_ARCHIVING_COUNT WHERE 1=1 ";
        if(StringUtils.isNotBlank(qvo.getYearStart())){
            map.put("startTime",qvo.getYearStart().substring(0,7));
            signSql += " AND MANAGE_YEAR_MONTH >=:startTime ";
        }
        if(StringUtils.isNotBlank(qvo.getYearEnd())){
            map.put("endTime",qvo.getYearEnd().substring(0,7));
            signSql += " AND MANAGE_YEAR_MONTH <=:endTime ";
        }
        if(address != null){
            if("2".equals(address.getLevel())){//各市数据统计
                map.put("areaCode",address.getCtcode().substring(0,4)+"%");
                returnMap.put("code",AreaUtils.getAreaCode(address.getCtcode(),"2"));
                sql +=" AND MANAGE_AREA_CODE LIKE :areaCode";
                signSql +=" AND MANAGE_JD_AREA_CODE LIKE :areaCode";
//                arcSql += " AND ADDR_COUNTY_CODE LIKE :areaCode";
                poverSql += " AND ADDR_COUNTY_CODE LIKE :areaCode";
            }else if("3".equals(address.getLevel())){//各区数据
                map.put("areaCode",address.getCtcode().substring(0,6)+"%");
                returnMap.put("code",AreaUtils.getAreaCode(address.getCtcode(),"3"));

                //漳州“台商投资区”，“招商局漳州开发区”，“常山农场经济开发区”这三个区要特别判断
                if("350698000000".equals(address.getCtcode())){//台商投资区
                    map.put("areaCode","350681102%");
                }else if("350699000000".equals(address.getCtcode())){//招商局漳州开发区
                    map.put("areaCode","350681501%");
                }else if("350697000000".equals(address.getCtcode())){//常山农场经济开发区
                    map.put("areaCode","350622450%");
                }else if("350681000000".equals(address.getCtcode())){
                    map.put("notAreaCode",new String[]{"350681102000","350681501000","350622450000"});
                    sql +=" AND MANAGE_AREA_CODE NOT IN (:notAreaCode) ";
                    signSql +=" AND MANAGE_JD_AREA_CODE NOT IN (:notAreaCode) ";
                }else if("350622000000".equals(address.getCtcode())){
                    map.put("notAreaCode",new String[]{"350681102000","350681501000","350622450000"});
                    sql +=" AND MANAGE_AREA_CODE NOT IN (:notAreaCode) ";
                    signSql +=" AND MANAGE_JD_AREA_CODE NOT IN (:notAreaCode) ";
                }

                sql +=" AND MANAGE_AREA_CODE LIKE :areaCode";
                signSql +=" AND MANAGE_JD_AREA_CODE LIKE :areaCode";
                map.put("ccdd",address.getCtcode());
//                arcSql += " AND ADDR_COUNTY_CODE = :ccdd";
                poverSql += " AND ADDR_COUNTY_CODE = :ccdd";
            }else if("4".equals(address.getLevel())){//各街道数据
                map.put("areaCode",address.getCtcode().substring(0,9)+"%");
                returnMap.put("code",AreaUtils.getAreaCode(address.getCtcode(),"4"));
                sql +=" AND MANAGE_AREA_CODE LIKE :areaCode";
                signSql +=" AND MANAGE_JD_AREA_CODE LIKE :areaCode";
                map.put("ccdd",address.getCtcode());
//                arcSql += " AND ADDR_RURAL_CODE = :ccdd";
                poverSql += " AND ADDR_RURAL_CODE = :ccdd";
            }else if("5".equals(address.getLevel())){//各居委会数据
                map.put("areaCode",address.getCtcode());
                returnMap.put("code",address.getCtcode());
                sql +=" AND MANAGE_AREA_CODE = :areaCode";
                signSql +=" AND MANAGE_JD_AREA_CODE = :areaCode";
                map.put("ccdd",address.getCtcode());
//                arcSql += " AND ADDR_VILLAGE_CODE = :ccdd";
                poverSql += " AND ADDR_VILLAGE_CODE = :ccdd";
            }
            returnMap.put("name",address.getAreaSname());
        }
        if(hosp != null){
            map.put("hospId",hosp.getId());
            map.put("hospAreaCode",AreaUtils.getAreaCode(hosp.getHospAreaCode(),"3")+"%");
            signSql += " AND ADDR_HOSP_ID =:hospId AND MANAGE_JD_AREA_CODE  = :areaCode ";
            map.put("areaCode",hosp.getHospAreaCode());
//            sql += " AND MANAGE_AREA_CODE = :areaCode ";
//            sql += " AND ADDR_HOSP_ID =:hospId ";
//            arcSql += " AND ADDR_RURAL_CODE LIKE :areaCode";
//            poverSql += " AND ADDR_RURAL_CODE LIKE :areaCode";
//            poverSql += " AND ADDR_HOSP_ID =:hospId ";
            returnMap.put("name",hosp.getHospName());
            returnMap.put("hospId",hosp.getId());
            if("3".equals(qvo.getJdSourceType())){
                poverSql += " AND ADDR_HOSP_ID =:hospId ";
                sql += " AND ADDR_HOSP_ID =:hospId ";
            }else{
                poverSql += " AND ADDR_RURAL_CODE LIKE :areaCode ";
                sql += " AND MANAGE_AREA_CODE = :areaCode ";
            }
        }

        List<Map> listAll = sysDao.getServiceDo().findSqlMap(sql,map);
        List<Map> listSign = sysDao.getServiceDo().findSqlMap(signSql,map);
        if(listAll != null && listAll.size()>0){//所有数
            if(listAll.get(0).get("manageNotSignCount")!= null){//未签约人数
                notSignCount = (int)Double.parseDouble(listAll.get(0).get("manageNotSignCount").toString());
            }
            /*if(listAll.get(0).get("manageSignCount") != null){//已签约人数
                signCount = (int)Double.parseDouble(listAll.get(0).get("manageSignCount").toString());
            }*/
            //一般人群人数 = 一般人群签约数 =普通人群+未知人群
//            if(listAll.get(0).get("managePlainCount") != null){//普通人群数
//                plainCount = (int)Double.parseDouble(listAll.get(0).get("managePlainCount").toString());
//            }
//            if(listAll.get(0).get("manageServiceUnknownCount") != null){//未知人群数
//                plainCount += (int)Double.parseDouble(listAll.get(0).get("manageServiceUnknownCount").toString());
//            }
//            if(listAll.get(0).get("manageKeySignCount") != null){//重点人群数
//                keySignCount = (int)Double.parseDouble(listAll.get(0).get("manageKeySignCount").toString());
//            }
            if(listAll.get(0).get("manageChildCount") != null){//0-6岁儿童数(人)
                childCount = (int)Double.parseDouble(listAll.get(0).get("manageChildCount").toString());
            }
            if(listAll.get(0).get("manageMaternalCount") != null){//孕产妇
                maternalCount = (int)Double.parseDouble(listAll.get(0).get("manageMaternalCount").toString());
            }
            if(listAll.get(0).get("manageOldPeopleCount") != null){//老年人
                oldPeopleCount = (int)Double.parseDouble(listAll.get(0).get("manageOldPeopleCount").toString());
            }
            if(listAll.get(0).get("manageBloodCount") != null){//高血压
                bloodCount = (int)Double.parseDouble(listAll.get(0).get("manageBloodCount").toString());
            }
            if(listAll.get(0).get("manageDiabetesCount") != null){//糖尿病
                diabetesCount = (int)Double.parseDouble(listAll.get(0).get("manageDiabetesCount").toString());
            }
            if(listAll.get(0).get("managePsychosisCount") != null){//严重精神病患者
                psychosisCount = (int)Double.parseDouble(listAll.get(0).get("managePsychosisCount").toString());
            }
            if(listAll.get(0).get("managePhthisisCount") != null){//结核病
                phthisisCount = (int)Double.parseDouble(listAll.get(0).get("managePhthisisCount").toString());
            }
            if(listAll.get(0).get("manageDisabledPeopleCount") != null){//残疾人
                disabledPeopleCount = (int)Double.parseDouble(listAll.get(0).get("manageDisabledPeopleCount").toString());
            }

        }
        //已签约人数
        if(listSign != null && listSign.size()>0){
            if(listSign.get(0).get("manageKeySignCount") != null){//重点人群数
                keySignCount = (int)Double.parseDouble(listSign.get(0).get("manageKeySignCount").toString());
                signKeySignCount = (int)Double.parseDouble(listSign.get(0).get("manageKeySignCount").toString());
            }
            if(listSign.get(0).get("manageSignCount") != null){//已签约人数
                signCount = (int)Double.parseDouble(listSign.get(0).get("manageSignCount").toString());
            }
            if(listSign.get(0).get("managePlainCount") != null){//普通人群
                plainCount = (int)Double.parseDouble(listSign.get(0).get("managePlainCount").toString());
                signPlainCount = (int)Double.parseDouble(listSign.get(0).get("managePlainCount").toString());
            }
            if(listSign.get(0).get("manageChildCount") != null){//0-6岁儿童
                signChildCount = (int)Double.parseDouble(listSign.get(0).get("manageChildCount").toString());
            }
            if(listSign.get(0).get("manageMaternalCount") != null){//孕产妇
                signMaternalCount = (int)Double.parseDouble(listSign.get(0).get("manageMaternalCount").toString());
            }
            if(listSign.get(0).get("manageOldPeopleCount") != null){//老年人
                signOldPeopleCount = (int)Double.parseDouble(listSign.get(0).get("manageOldPeopleCount").toString());
            }
            if(listSign.get(0).get("manageBloodCount") != null){//高血压
                signBloodCount = (int)Double.parseDouble(listSign.get(0).get("manageBloodCount").toString());
            }
            if(listSign.get(0).get("manageDiabetesCount") != null){//糖尿病
                signDiabetesCount = (int)Double.parseDouble(listSign.get(0).get("manageDiabetesCount").toString());
            }
            if(listSign.get(0).get("managePsychosisCount") != null){//严重精神病患者
                signPsychosisCount = (int)Double.parseDouble(listSign.get(0).get("managePsychosisCount").toString());
            }
            if(listSign.get(0).get("managePhthisisCount") != null){//结核病
                signPhthisisCount = (int)Double.parseDouble(listSign.get(0).get("managePhthisisCount").toString());
            }
            if(listSign.get(0).get("manageDisabledPeopleCount") != null){//残疾人
                signDisabledPeopleCount = (int)Double.parseDouble(listSign.get(0).get("manageDisabledPeopleCount").toString());
            }
            if(listSign.get(0).get("manageServiceUnknownCount") != null){//服务人群未知
                plainCount += (int)Double.parseDouble(listSign.get(0).get("manageServiceUnknownCount").toString());
                signPlainCount += (int)Double.parseDouble(listSign.get(0).get("manageServiceUnknownCount").toString());
            }
            if(listSign.get(0).get("manageSpecialPlanFamilyCount") != null){//计生特殊家庭
                signSpecialPlanFamilyCount = (int)Double.parseDouble(listSign.get(0).get("manageSpecialPlanFamilyCount").toString());
                specialPlanFamilyCount = (int)Double.parseDouble(listSign.get(0).get("manageSpecialPlanFamilyCount").toString());
            }
        }
        //建档立卡贫困人口数 = 未签约数 + 签约数
        String sy = "SELECT COUNT(1) FROM ("+poverSql+" GROUP BY ARCHIVING_PATIENT_IDNO) cc";
        povertyCount = sysDao.getServiceDo().findCount(sy,map);
//        String sss = "SELECT COUNT(1) FROM ("+arcSql + " GROUP BY ARCHIVING_PATIENT_IDNO) cc";
//        notSignCount = sysDao.getServiceDo().findCount(sss,map);
        totalArchivingCount = notSignCount+signCount;
        returnMap.put("totalArchivingCount",totalArchivingCount);//建档立卡人员数
        returnMap.put("plainCount",plainCount);//一般人群人数
        returnMap.put("keySignCount",keySignCount);//重点人群人数
        returnMap.put("notSignCount",notSignCount);//未落实签约人数(人)
        returnMap.put("signCount",signCount);//已签约总人数(人)
        returnMap.put("signPlainCount",signPlainCount);//一般人群人数(签约人数)
        returnMap.put("signKeySignCount",signKeySignCount);//重点人群人数(签约人数)
        returnMap.put("childCount",childCount);//0-6岁儿童数(人)
        returnMap.put("signChildCount",signChildCount);//0-6岁儿童签约人数
        returnMap.put("oldPeopleCount",oldPeopleCount);//65岁及以上老年人人数
        returnMap.put("signOldPeopleCount",signOldPeopleCount);//65岁及以上老年人签约人数
        returnMap.put("maternalCount",maternalCount);//孕产妇人数
        returnMap.put("signMaternalCount",signMaternalCount);//孕产妇签约人数
        returnMap.put("bloodCount",bloodCount);//已管理的高血压患者人数
        returnMap.put("signBloodCount",signBloodCount);//已管理的高血压患者签约人数
        returnMap.put("diabetesCount",diabetesCount);//已管理的糖尿病患者人数
        returnMap.put("signDiabetesCount",signDiabetesCount);//已管理的糖尿病患者签约人数
        returnMap.put("phthisisCount",phthisisCount);//已管理的肺结核患者人数
        returnMap.put("signPhthisisCount",signPhthisisCount);//已管理的肺结核患者签约人数
        returnMap.put("psychosisCount",psychosisCount);//已管理的严重精神障碍患者人数
        returnMap.put("signPsychosisCount",signPsychosisCount);//已管理的严重精神障碍患者签约人数
        returnMap.put("disabledPeopleCount",disabledPeopleCount);//残疾人患者人数
        returnMap.put("signDisabledPeopleCount",signDisabledPeopleCount);//残疾人患者签约人数
        returnMap.put("specialPlanFamilyCount",specialPlanFamilyCount);//计划生育特殊家庭人数)
        returnMap.put("signSpecialPlanFamilyCount",signSpecialPlanFamilyCount);//计划生育特殊家庭自愿签约人数
        returnMap.put("povertyCount",povertyCount);
        return returnMap;
    }

    /**
     * 根据机构查询统计数
     * @param qvo
     * @return
     */
    @Override
    public Map<String, Object> findArchivingCountByHosp(ResidentVo qvo) throws Exception {
        Map<String,Object> returnMap = new HashMap<>();
        Map<String,Object> map = new HashMap<>();
        map.put("hospId",qvo.getHospId());
        int totalSignCount = 0;//签约总数
        int keySignCount = 0;//重点人群签约总数
        int paidSignCount = 0;//有偿签约数
        String sql = "SELECT\n" +
                "\tsum(MANAGE_SIGN_COUNT) manageSignCount,\n" +
                "\tSUM(MANAGE_KEY_SIGN_COUNT) manageKeySignCount\n" +
                "FROM\n" +
                "\tapp_manage_archiving_count\n" +
                "WHERE\n" +
                "\tMANAGE_HOSP_ID = :hospId\n";
        if(StringUtils.isNotBlank(qvo.getYearStart())){
            map.put("startTime",qvo.getYearStart().substring(0,7));
            sql += " AND MANAGE_YEAR_MONTH >=:startTime ";
        }
        if(StringUtils.isNotBlank(qvo.getYearEnd())){
            map.put("endTime",qvo.getYearEnd().substring(0,7));
            sql += " AND MANAGE_YEAR_MONTH <=:endTime ";
        }
        List<Map> list = sysDao.getServiceDo().findSqlMap(sql,map);
        if(list != null && list.size()>0){
            if(list.get(0).get("manageSignCount") != null){//签约总数
                totalSignCount = (int)Double.parseDouble(list.get(0).get("manageSignCount").toString());
                paidSignCount = (int)Double.parseDouble(list.get(0).get("manageSignCount").toString());
            }
            if(list.get(0).get("manageKeySignCount") != null){//重点人群签约总数
                keySignCount = (int)Double.parseDouble(list.get(0).get("manageKeySignCount").toString());
            }
        }
        returnMap.put("totalSignCount",totalSignCount);
        returnMap.put("paidSignCount",paidSignCount);
        returnMap.put("keySignCount",keySignCount);
        return returnMap;
    }

    @Override
    public List<AppArchivintPeopleEntity> findArchivingPeopleByHosp(ResidentVo qvo) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("hospId",qvo.getHospId());
        map.put("SOURCE_TYPE",qvo.getJdSourceType());
        String[] fwState ={ResidentMangeType.PTRQ.getValue(),ResidentMangeType.WEIZHI.getValue()};
        map.put("LABEL_VALUE",fwState);
        String ss = "";
        if(StringUtils.isNotBlank(qvo.getPersGroup())){//服务人群查询
            String[] persGroups = qvo.getPersGroup().split(";");
            if(persGroups != null && persGroups.length>0){
                for(String persGroup:persGroups){
                    if(StringUtils.isNotBlank(persGroup)){
                        if(StringUtils.isBlank(ss)){
                            ss = " find_in_set("+persGroup+",cc.keyValue)";
                        }else{
                            ss += " OR find_in_set("+persGroup+",cc.keyValue) ";
                        }
                    }
                }
            }
        }
        String sql = "SELECT\n" +
                "\ta.ARCHIVING_PATIENT_NAME name,\n" +
                "\ta.ARCHIVING_PATIENT_IDNO idno,\n" +
                "\t(SELECT TEAM_NAME FROM app_team WHERE ID = a.TEAM_ID) teamName,\n" +
                "\ta.TEAM_ID teamId,\n" +
                "\t(SELECT DR_NAME FROM app_dr_user WHERE ID = a.DR_ID) drName,\n" +
                "\t(SELECT DR_TEL FROM app_dr_user WHERE ID = a.DR_ID) drTel,\n" +
                "\t(SELECT GROUP_CONCAT(LABEL_VALUE) FROM app_label_archiving WHERE LABEL_ARCHIVING_ID = a.ID AND LABEL_VALUE NOT IN (:LABEL_VALUE) ) keyValue,\n" +
                "\t(SELECT GROUP_CONCAT(LABEL_TITLE) FROM app_label_archiving WHERE LABEL_ARCHIVING_ID = a.ID AND LABEL_VALUE NOT IN (:LABEL_VALUE) ) keyCrowd,\n" +
                "a.SIGN_FROM_DATE signDate," +
                "null agreement," +
                "a.IS_NOT_POVERTY povertyState,\n" +
                "null other\n" +
                "FROM\n" +
                "\tapp_archivingcard_people a\n" +
                "WHERE\n" +
                "\ta.HOSP_ID = :hospId\n" +
                "AND a.SIGN_ID IS NOT NULL\n" +
                " AND a.SOURCE_TYPE =:SOURCE_TYPE " ;
        AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,qvo.getHospId());
        if(dept != null){
            map.put("areaCode",AreaUtils.getAreaCode(dept.getHospAreaCode(),"3")+"000000");
            sql += " AND a.ADDR_COUNTY_CODE =:areaCode ";
        }

//                "AND a.SOURCE_TYPE = :SOURCE_TYPE\n";
        if(StringUtils.isNotBlank(qvo.getPovertyState())){
            map.put("povertyState",qvo.getPovertyState());
            sql += " AND a.IS_NOT_POVERTY =:povertyState ";
        }
        if(StringUtils.isNotBlank(qvo.getYearStart())){
            map.put("startTime",qvo.getYearStart() + " 00:00:00");
            sql += " AND a.SIGN_FROM_DATE >=:startTime ";
        }
        if(StringUtils.isNotBlank(qvo.getYearEnd())){
            map.put("endTime",qvo.getYearEnd() + " 23:59:59");
            sql += " AND a.SIGN_FROM_DATE <=:endTime ";
        }

        sql +=  " GROUP BY a.ARCHIVING_PATIENT_IDNO";
        String ssql = "SELECT cc.* FROM ("+sql+") cc WHERE 1=1 ";
        if(StringUtils.isNotBlank(ss)){
            ssql += " AND ("+ss+")";
        }
        List<AppArchivintPeopleEntity> list = sysDao.getServiceDo().findSqlMapRVo(ssql,map,AppArchivintPeopleEntity.class,qvo);
        return list;
    }

    /**
     * 建档立卡各未签原因调度统计
     * @param qvo
     * @return
     */
    @Override
    public Map<String, Object> findManageArchiving(ResidentVo qvo) throws Exception {
        AppHospDept hosp = null;
        AppTeam team = null;
        AppDrUser drUser = null;
        CdAddress cdAddress = null;
        /*if(StringUtils.isNotBlank(qvo.getHospId())){
            hosp = (AppHospDept)sysDao.getServiceReadDo().find(AppHospDept.class,qvo.getHospId());
        }
        if(StringUtils.isNotBlank(qvo.getTeamId())){
            team = (AppTeam) sysDao.getServiceReadDo().find(AppTeam.class,qvo.getTeamId());
        }
        if(StringUtils.isNotBlank(qvo.getDrId())){
            drUser = (AppDrUser) sysDao.getServiceReadDo().find(AppDrUser.class,qvo.getDrId());
        }*/
        if(StringUtils.isNotBlank(qvo.getAreaId())){
            cdAddress = (CdAddress) sysDao.getServiceReadDo().find(CdAddress.class,qvo.getAreaId());
            if(cdAddress == null){
                CdAddressConfiguration cp = sysDao.getCdAddressDao().findByCodeJw(qvo.getAreaId());
                if(cp != null){
                    cdAddress = new CdAddress();
                    cdAddress.setId(cp.getAreaCodeJw());
                }
            }
        }
        Map<String, Object> map = getSianCountArchivingPeople(cdAddress,qvo.getYearStart(),qvo.getYearEnd(),qvo.getJdSourceType(),qvo.getHospId());
        return map;
    }

    public Map<String,Object> getSianCountArchivingPeople(CdAddress cdAddress,String startDate,String endDate,String sourceType,String addrHospId ) throws Exception{
        Map<String,Object> returnMap = new HashMap<>();
        Map<String,Object> map = new HashMap<>();
        int totalArchivingCount = 0;//建档立卡人员数
        int signCount = 0;//累计签约人数
        int notSignCount = 0;//合计(未签约数)
        int signLowInsuredCount = 0;//签约低保户人数
        map.put("STARTDATE", ExtendDate.getFirstDayOfMonth(startDate)+" 00:00:00");
        map.put("ENDDATE",ExtendDate.getLastDayOfMonthNew(endDate)+" 23:59:59");
        map.put("SOURCE_TYPE",sourceType);
        map.put("LOW_INSURED","2");
        map.put("JDLK","1");

        String sqlDateSign = " AND t.SIGN_FROM_DATE >= :STARTDATE AND t.SIGN_FROM_DATE <= :ENDDATE  AND t.SOURCE_TYPE = :SOURCE_TYPE ";//AND t.SOURCE_TYPE = :SOURCE_TYPE
        String sqlDate = " AND t.HS_CREATE_DATE >= :STARTDATE AND t.HS_CREATE_DATE <= :ENDDATE  AND t.SOURCE_TYPE = :SOURCE_TYPE ";//AND t.SOURCE_TYPE = :SOURCE_TYPE
        String sql = "SELECT t.ARCHIVING_PATIENT_IDNO FROM app_archivingcard_people t WHERE 1=1 "+sqlDate;
        String sqlSign = "SELECT t.* FROM app_archivingcard_people t INNER JOIN app_label_archiving b on t.ID = b.LABEL_ARCHIVING_ID WHERE 1=1 AND t.SIGN_STATE IS NOT NULL "+sqlDateSign;
        String sqlSignLI = "SELECT t.* FROM app_archivingcard_people t INNER JOIN app_label_archiving b on t.ID = b.LABEL_ARCHIVING_ID WHERE 1=1 AND t.SIGN_STATE IS NOT NULL AND t.PROVINCIAL_INSURANCE =:LOW_INSURED "+sqlDateSign;
        String sqlWqy = "SELECT t.ARCHIVING_PATIENT_IDNO FROM app_archivingcard_people t WHERE 1=1 AND t.SIGN_ID IS NULL "+sqlDate;
        if("3".equals(sourceType)){
            sqlSign += " AND t.PROVINCIAL_INSURANCE =:JDLK ";
            sql += " AND t.PROVINCIAL_INSURANCE =:JDLK ";
        }
        //查询该街道建档立卡人数
        if(cdAddress != null){
            map.put("ADDR_RURAL_CODE",cdAddress.getId());
            sql += " AND t.ADDR_RURAL_CODE = :ADDR_RURAL_CODE  ";
            sqlSign += " AND t.ADDR_RURAL_CODE =:ADDR_RURAL_CODE ";
            sqlSignLI += " AND t.ADDR_RURAL_CODE =:ADDR_RURAL_CODE ";
            sqlWqy += " AND t.ADDR_RURAL_CODE = :ADDR_RURAL_CODE ";
            if(StringUtils.isNotBlank(addrHospId)){
                map.put("ADDR_HOSP_ID",addrHospId);
                sql += " AND t.ADDR_HOSP_ID =:ADDR_HOSP_ID ";
                sqlSign += " AND t.ADDR_HOSP_ID =:ADDR_HOSP_ID ";
                sqlSignLI += " AND t.ADDR_HOSP_ID =:ADDR_HOSP_ID ";
                sqlWqy += " AND t.ADDR_HOSP_ID =:ADDR_HOSP_ID ";
            }
        }

        String ssql = "SELECT COUNT(1) FROM ("+sql+" ) cc";
        totalArchivingCount = sysDao.getServiceDo().findCount(ssql,map);//建档立卡人数  GROUP BY t.ARCHIVING_PATIENT_IDNO
        returnMap.put("totalArchivingCount",totalArchivingCount);
        String signSql = " SELECT COUNT(1) FROM ("+sqlSign + " GROUP BY b.LABEL_ARCHIVING_ID ) cc";
        signCount = sysDao.getServiceDo().findCount(signSql,map);//累计签约数  GROUP BY t.ARCHIVING_PATIENT_IDNO
        returnMap.put("totalSignCount",signCount);
        String signLISql = "SELECT COUNT(1) FROM ("+sqlSignLI+" GROUP BY b.LABEL_ARCHIVING_ID ) cc";
        signLowInsuredCount = sysDao.getServiceDo().findCount(signLISql,map);//累计签约人数中低保户人数
        returnMap.put("signLowInsuredCount",signLowInsuredCount);
        //查询未签约原因
        List<CdCode> lisCd = sysDao.getCodeDao().findGroupList(CodeGroupConstrats.GROUP_NOTSIGNREASON, CommonEnable.QIYONG.getValue());
        if(lisCd != null && lisCd.size()>0){
            sqlWqy += " AND t.NOT_SIGN_REASON =:value ";
            String notSignSql = "SELECT COUNT(1) FROM ("+sqlWqy+"  ) cc";//GROUP BY t.ARCHIVING_PATIENT_IDNO
            for(CdCode cd:lisCd){
                map.put("value",cd.getCodeValue());
                int count = sysDao.getServiceDo().findCount(notSignSql,map);
                notSignCount += count;
                if("1".equals(cd.getCodeValue())){//死亡
                    returnMap.put("dieCount",count);
                }else if ("2".equals(cd.getCodeValue())){//失联失踪
                    returnMap.put("missCount",count);
                }else if("3".equals(cd.getCodeValue())){//迁出
                    returnMap.put("moveOutCount",count);
                }else if("4".equals(cd.getCodeValue())){//长期外出
                    returnMap.put("longTimeOutCount",count);
                }else if("5".equals(cd.getCodeValue())){//拒签
                    returnMap.put("refuseSignCount",count);
                }else if("6".equals(cd.getCodeValue())){//服刑
                    returnMap.put("beExecotedCount",count);
                }else if("7".equals(cd.getCodeValue())){//名单重复
                    returnMap.put("nameRepeatCount",count);
                }else if("8".equals(cd.getCodeValue())){//精神病人住康复医院，家属外出
                    returnMap.put("mentalPatientCount",count);
                }else if("9".equals(cd.getCodeValue())){//服兵役
                    returnMap.put("performMilitaryService",count);
                }else if("10".equals(cd.getCodeValue())){//外籍
                    returnMap.put("foreignNationality",count);
                }else if("11".equals(cd.getCodeValue())){//外嫁
                    returnMap.put("marryOutCount",count);
                }else if("12".equals(cd.getCodeValue())){//新增人员
                    returnMap.put("newPersonnelCount",count);
                }else if("13".equals(cd.getCodeValue())){//联系不上
                    returnMap.put("noConnectionCount",count);
                }else if("14".equals(cd.getCodeValue())){//身份名字不符
                    returnMap.put("idnoDiscrepancyCount",count);
                }else if("15".equals(cd.getCodeValue())){//暂时外出无法签约
                    returnMap.put("notSignGoOut",count);
                }else if("16".equals(cd.getCodeValue())){//外地建档
                    returnMap.put("fieldArchivingCount",count);
                }else if("17".equals(cd.getCodeValue())){//退出
                    returnMap.put("quitCount",count);
                }else if("18".equals(cd.getCodeValue())){//无社保卡
                    returnMap.put("notCardCount",count);
                }else if("19".equals(cd.getCodeValue())){//出国
                    returnMap.put("goAbroadCount",count);
                }else if("20".equals(cd.getCodeValue())){//其他
                    returnMap.put("otherCount",count);
                }else if("21".equals(cd.getCodeValue())){//未填写
                    returnMap.put("unfilledCount",count);
                }

            }
            returnMap.put("manageTotal",notSignCount);
        }
        return returnMap;
    }

    public Map<String,Object> getSignCountDr(AppDrUser drUser,String startTime,String endTime) throws Exception{
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("drName",drUser.getDrName());
        returnMap.put("drId",drUser.getId());

        int yqy = 0;//总签约数
        int zdrqyqy = 0 ;//重点人群签约数
        int jjrqyqy = 0;//重点经济类型签约数
        Map<String,Object> map = new HashMap<>();
        map.put("drId",drUser.getId());
        map.put("SIGN_STATE",new String[]{SignFormType.YUQY.getValue(),SignFormType.YQY.getValue()});
        map.put("fwValue",new String[]{ResidentMangeType.PTRQ.getValue(),ResidentMangeType.WEIZHI.getValue()});
        String sql = "SELECT\n" +
                "\tcount(1)\n" +
                "FROM\n" +
                "\tapp_sign_form\n" +
                "WHERE\n" +
                "\tSIGN_STATE IN (:SIGN_STATE)\n" +
                "AND SIGN_DR_ID = :drId ";

        String sqlzd = "SELECT\n" +
                "\ta.*\n" +
                "FROM\n" +
                "\tapp_sign_form a\n" +
                "INNER JOIN app_label_group b ON a.ID = b.LABEL_SIGN_ID\n" +
                "WHERE\n" +
                "\t1 = 1\n" +
                "AND a.SIGN_STATE IN (:SIGN_STATE)\n" +
                "AND a.SIGN_DR_ID = :drId\n" +
                "AND b.LABEL_VALUE NOT IN (:fwValue) AND b.LABEL_TYPE = '3'";

        String sqljj = "SELECT\n" +
                "\ta.*\n" +
                "FROM\n" +
                "\tapp_sign_form a\n" +
                "INNER JOIN app_label_economics b ON a.ID = b.LABEL_SIGN_ID\n" +
                "WHERE\n" +
                "\t1 = 1\n" +
                "AND a.SIGN_STATE IN (:SIGN_STATE)\n" +
                "AND a.SIGN_DR_ID = :drId\n" +
                "AND b.LABEL_VALUE != '1'\n" +
                "AND b.LABEL_TYPE = '4'";

        if(StringUtils.isNotBlank(startTime)){
            map.put("startTime",ExtendDate.getFirstDayOfMonth(startTime)+" 00:00:00");
            sql += " AND SIGN_FROM_DATE >= :startTime ";
            sqlzd += " AND a.SIGN_FROM_DATE >= :startTime ";
            sqljj += " AND a.SIGN_FROM_DATE >= :startTime ";
        }
        if(StringUtils.isNotBlank(endTime)){
            map.put("endTime",ExtendDate.getLastDayOfMonth(endTime)+ " 23:59:59");
            sql += " AND SIGN_FROM_DATE <= :endTime ";
            sqlzd += " AND a.SIGN_FROM_DATE <= :endTime ";
            sqljj += " AND a.SIGN_FROM_DATE <= :endTime ";
        }

        yqy = sysDao.getServiceDo().findCount(sql,map);

        String zdSql = "SELECT COUNT(1) FROM ("+sqlzd+" GROUP BY a.SIGN_PATIENT_ID ) cc";
        zdrqyqy = sysDao.getServiceDo().findCount(zdSql,map);

        String jjSql = "SELECT COUNT(1) FROM ("+sqljj+" GROUP BY a.SIGN_PATIENT_ID ) cc";
        jjrqyqy = sysDao.getServiceDo().findCount(jjSql,map);
        returnMap.put("yqy",yqy);
        returnMap.put("keyCount",zdrqyqy);
        returnMap.put("jjCount",jjrqyqy);
        return returnMap;

    }

    @Override
    public List<AppArchivintPeopleTTEntity> findArchivingTTPeopleByHosp(ResidentVo qvo) throws Exception {
        Map<String,Object> map = new HashMap<>();
        AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,qvo.getHospId());
        map.put("ADDR_HOSP_ID",qvo.getHospId());
        map.put("areaCode",dept.getHospAreaCode());
        map.put("SOURCE_TYPE",qvo.getJdSourceType());
        String sql = "SELECT\n" +
                "\ta.RHF_ID rhfId,\n" +
                "\ta.ADDR_VILLAGE_NAME villageName,\n" +
                "\ta.ARCHIVING_PATIENT_NAME name,\n" +
                "\ta.ARCHIVING_PATIENT_IDNO idno,\n" +
                "\t(SELECT PATIENT_TEL  FROM app_patient_user WHERE PATIENT_IDNO = a.ARCHIVING_PATIENT_IDNO) tel,\n" +
                "\ta.SIGN_ID isSign,\n" +
                "\ta.IS_NOT_POVERTY isNotPoverty,\n" +
                "\ta.NOT_SIGN_REASON notSignReason,\n" +
                "\ta.SOURCE_TYPE sourceType\n" +
                "FROM\n" +
                "\tapp_archivingcard_people a WHERE 1=1 " +
                "AND a.SIGN_ID is null AND a.SOURCE_TYPE =:SOURCE_TYPE   ";

        map.put("hospId",qvo.getHospId());
        String ssql = "SELECT\n" +
                "\ta.RHF_ID rhfId,\n" +
                "\ta.ADDR_VILLAGE_NAME villageName,\n" +
                "\ta.ARCHIVING_PATIENT_NAME name,\n" +
                "\ta.ARCHIVING_PATIENT_IDNO idno,\n" +
                "\t(SELECT PATIENT_TEL  FROM app_patient_user WHERE PATIENT_IDNO = a.ARCHIVING_PATIENT_IDNO) tel,\n" +
                "\ta.SIGN_STATE isSign,\n" +
                "\ta.IS_NOT_POVERTY isNotPoverty,\n" +
                "\ta.NOT_SIGN_REASON notSignReason,\n" +
                "\ta.SOURCE_TYPE sourceType\n" +
                "FROM\n" +
                "\tapp_archivingcard_people a WHERE a.ADDR_RURAL_CODE = :areaCode AND a.HOSP_ID =:hospId AND a.SIGN_ID is NOT NULL AND a.SOURCE_TYPE =:SOURCE_TYPE   ";

        if("3".equals(qvo.getJdSourceType())){
            sql +=" AND a.ADDR_HOSP_ID =:ADDR_HOSP_ID ";
        }else{
            sql+=" AND a.ADDR_RURAL_CODE = :areaCode ";
        }
        sql += " GROUP BY a.ARCHIVING_PATIENT_IDNO ";

        if(org.apache.commons.lang.StringUtils.isNotBlank(qvo.getYearStart())){
            map.put("startTime",qvo.getYearStart() + " 00:00:00");
            ssql += " AND a.SIGN_FROM_DATE >=:startTime ";
        }
        if(org.apache.commons.lang.StringUtils.isNotBlank(qvo.getYearEnd())){
            map.put("endTime",qvo.getYearEnd() + " 23:59:59");
            ssql += " AND a.SIGN_FROM_DATE <=:endTime ";
        }
        ssql +=" GROUP BY a.ARCHIVING_PATIENT_IDNO ";
        String allSql = sql +" UNION "+ssql;
        List<AppArchivintPeopleTTEntity> list = sysDao.getServiceDo().findSqlMapRVo(allSql,map,AppArchivintPeopleTTEntity.class,qvo);
        return list;
    }

    @Override
    public Map<String, Object> findPovCount(ResidentVo qvo) throws Exception {
        Map<String,Object> returnMap = new HashMap<>();
        int totalArchivingCount =0;//建档立卡总人数
        int povertyCount = 0;//脱贫人数
        int signCount= 0;//已签约人数
        int notSignCount = 0;//未签约人数
        Map<String,Object> map = new HashMap<>();
        AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,qvo.getHospId());
        map.put("areaCode",dept.getHospAreaCode());//a.IS_NOT_POVERTY isNotPoverty,
        map.put("hospId",qvo.getHospId());
        map.put("SOURCE_TYPE",qvo.getJdSourceType());
        String sql = "SELECT * FROM app_archivingcard_people a WHERE a.ADDR_RURAL_CODE = :areaCode and a.SIGN_ID is null AND a.SOURCE_TYPE =:SOURCE_TYPE  GROUP BY a.ARCHIVING_PATIENT_IDNO ";
        String signSql = "SELECT * FROM app_archivingcard_people a WHERE a.ADDR_RURAL_CODE = :areaCode and a.SIGN_ID is not null AND a.HOSP_ID =:hospId AND a.SOURCE_TYPE =:SOURCE_TYPE  ";

        String sqlSign = "SELECT COUNT(1) FROM app_archivingcard_people a WHERE a.SIGN_ID is not null AND a.HOSP_ID =:hospId AND a.SOURCE_TYPE =:SOURCE_TYPE ";//已签约
        String sqlNotSign = "SELECT COUNT(1) FROM app_archivingcard_people a WHERE  a.SIGN_ID is null AND a.ADDR_HOSP_ID =:hospId AND a.SOURCE_TYPE =:SOURCE_TYPE ";//未签约

        if(org.apache.commons.lang.StringUtils.isNotBlank(qvo.getYearStart())){
            map.put("startTime",qvo.getYearStart() + " 00:00:00");
            signSql += " AND a.SIGN_FROM_DATE >=:startTime ";
        }
        if(org.apache.commons.lang.StringUtils.isNotBlank(qvo.getYearEnd())){
            map.put("endTime",qvo.getYearEnd() + " 23:59:59");
            signSql += " AND a.SIGN_FROM_DATE <=:endTime ";
        }
        signSql +=" GROUP BY a.ARCHIVING_PATIENT_IDNO ";

        String allSql = "SELECT COUNT(1) FROM ( "+sql+" UNION "+signSql+" ) cc";
        totalArchivingCount = sysDao.getServiceDo().findCount(allSql,map);

        String sqlP = "SELECT * FROM app_archivingcard_people a WHERE a.ADDR_RURAL_CODE = :areaCode and a.SIGN_ID is null AND a.IS_NOT_POVERTY ='1' AND a.SOURCE_TYPE =:SOURCE_TYPE  GROUP BY a.ARCHIVING_PATIENT_IDNO";
        String signSqlP = "SELECT * FROM app_archivingcard_people a WHERE a.ADDR_RURAL_CODE = :areaCode and a.SIGN_ID is not null AND a.HOSP_ID =:hospId AND a.IS_NOT_POVERTY ='1' AND a.SOURCE_TYPE =:SOURCE_TYPE  ";

        if(org.apache.commons.lang.StringUtils.isNotBlank(qvo.getYearStart())){
            map.put("startTime",qvo.getYearStart() + " 00:00:00");
            signSqlP += " AND a.SIGN_FROM_DATE >=:startTime ";
        }
        if(org.apache.commons.lang.StringUtils.isNotBlank(qvo.getYearEnd())){
            map.put("endTime",qvo.getYearEnd() + " 23:59:59");
            signSqlP += " AND a.SIGN_FROM_DATE <=:endTime ";
        }
        signSqlP +=" GROUP BY a.ARCHIVING_PATIENT_IDNO ";

        String ssSql = "SELECT COUNT(1) FROM ( "+sqlP+" UNION "+signSqlP+" ) cc";
        povertyCount = sysDao.getServiceDo().findCount(ssSql,map);

        signCount = sysDao.getServiceDo().findCount(sqlSign,map);
        notSignCount = sysDao.getServiceDo().findCount(sqlNotSign,map);
        returnMap.put("totalArchivingCount",totalArchivingCount);
        returnMap.put("povertyCount",povertyCount);
        returnMap.put("signCount",signCount);
        returnMap.put("notSignCount",notSignCount);
        return returnMap;
    }

    @Override
    public Map<String, Object> findManageSmNCD(AppSmMnanageQvo qvo) throws Exception {
        Map<String,Object> returnMap = new HashMap<>();
        int totalPopulation = 0;//县总人口
        int signCount = 0;//总签约人数
        int signNcdCount = 0;//慢病人群数
        int manageBloodCount = 0;//只有高血压人数
        int manageDiabetesCount = 0;//只有糖尿病人数
        int manageDisBloodCount = 0;//既是高血压又是糖尿病人数
        int manageBloodRedCount = 0;//高血压红标人数
        int manageBloodYellowCount = 0;//高血压黄标人数
        int manageBloodGreenCount = 0;//高血压绿标人数
        int manageBloodGrayCount = 0;//高血压灰标人数
        int manageDiabetesRedCount = 0;//糖尿病红标人数
        int manageDiabetesYellowCount = 0;//糖尿病黄标人数
        int manageDiabetesGreenCount = 0;//糖尿病绿标人数
        int manageDiabetesGrayCount = 0;//糖尿病灰标人数
        int manageLowFamilyCount = 0;//低保户(慢病)
        int manageDestituteFamilyCount = 0;//特困户（五保户）(慢病)
        int manageSpecialPlanFamilyCount = 0;//计生特殊家庭(慢病)
        int manageGeneralPopulationCount = 0;//一般人口(慢病)
        int managePoorFamilyCount = 0;//建档立卡贫困人口(慢病)
        int hbpCount = 0;//高血压数
        int dmCount = 0;//糖尿病数
        boolean flag = true;//判断时间是否大于当前时间
        String startDate = "";
        String endDate = "";
        String[] year = qvo.getEndTime().split("-");
        String sAreaCode = "";
        if(StringUtils.isNotBlank(qvo.getAreaCode())){
            sAreaCode = qvo.getAreaCode();
        }else if(StringUtils.isNotBlank(qvo.getHospId())){
            AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,qvo.getHospId());
            if(dept != null){
                sAreaCode = dept.getHospAreaCode();
            }
        }
        List<CdAddressPeople> lsPeople = this.sysDao.getCdAddressPeopleDao().findByCacheList(sAreaCode,year[0]);
        if(lsPeople != null && lsPeople.size()>0){
            totalPopulation = Integer.parseInt(StringUtils.isNotBlank(lsPeople.get(0).getAreaPopulation()) ? lsPeople.get(0).getAreaPopulation():"0");
        }
        //县户籍人口数
        returnMap.put("totalPopulation",totalPopulation);

        Map<String,Object> map = new HashMap<>();
        String sql = "SELECT\n" +
                "\tSUM(MANAGE_SIGN_COUNT) manageSignCount,\n" +
                "\tSUM(MANAGE_NCD_COUNT) manageNcdCount,\n" +
                "\tSUM(MANAGE_BLOOD_COUNT) manageBloodCount,\n" +
                "\tSUM(MANAGE_DIABETES_COUNT) manageDiabetesCount,\n" +
                "\tSUM(MANAGE_DIS_BLOOD_COUNT) manageDisBloodCount,\n" +
                "\tSUM(MANAGE_BLOOD_RED_COUNT) manageBloodRedCount,\n" +
                "\tSUM(MANAGE_BLOOD_YELLOW_COUNT) manageBloodYellowCount,\n" +
                "\tSUM(MANAGE_BLOOD_GREEN_COUNT) manageBloodGreenCount,\n" +
                "\tSUM(MANAGE_BLOOD_GRAY_COUNT) manageBloodGrayCount,\n" +
                "\tSUM(MANAGE_DIABETES_RED_COUNT) manageDiabetesRedCount,\n" +
                "\tSUM(MANAGE_DIABETES_YELLOW_COUNT) manageDiabetesYellowCount,\n" +
                "\tSUM(MANAGE_DIABETES_GREEN_COUNT) manageDiabetesGreenCount,\n" +
                "\tSUM(MANAGE_DIABETES_GRAY_COUNT) manageDiabetesGrayCount,\n" +
                "\tSUM(MANAGE_LOW_FAMILY_COUNT) manageLowFamilyCount,\n" +
                "\tSUM(MANAGE_DESTITUTE_FAMILY_COUNT) manageDestituteFamilyCount,\n" +
                "\tSUM(MANAGE_SPECIAL_PLAN_FAMILY_COUNT) manageSpecialPlanFamilyCount,\n" +
                "\tSUM(MANAGE_GENERAL_POPULATION_COUNT) manageGeneralPopulationCount,\n" +
                "\tSUM(MANAGE_POOR_FAMILY_COUNT) managePoorFamilyCount\n" +
                "FROM\n" +
                "\tapp_manage_chronic_disease\n" +
                "WHERE 1=1 ";
        String sqlMonth = "SELECT\n" +
                "\tSUM(MANAGE_BLOOD_RED_COUNT) manageBloodRedCount,\n" +
                "\tSUM(MANAGE_BLOOD_YELLOW_COUNT) manageBloodYellowCount,\n" +
                "\tSUM(MANAGE_BLOOD_GREEN_COUNT) manageBloodGreenCount,\n" +
                "\tSUM(MANAGE_BLOOD_GRAY_COUNT) manageBloodGrayCount,\n" +
                "\tSUM(MANAGE_DIABETES_RED_COUNT) manageDiabetesRedCount,\n" +
                "\tSUM(MANAGE_DIABETES_YELLOW_COUNT) manageDiabetesYellowCount,\n" +
                "\tSUM(MANAGE_DIABETES_GREEN_COUNT) manageDiabetesGreenCount,\n" +
                "\tSUM(MANAGE_DIABETES_GRAY_COUNT) manageDiabetesGrayCount\n" +
                "FROM\n" +
                "\tapp_manage_chronic_disease\n" +
                "WHERE 1=1 ";
        String sqlTeam = "SELECT\n" +
                "\tSUM(MANAGE_NCD_COUNT) manageNcdCount,\n" +
                "(SELECT t.HOSP_NAME FROM app_hosp_dept t WHERE t.ID = MANAGE_HOSP_ID ) hospName," +
                "(SELECT t.TEAM_NAME FROM app_team t WHERE t.ID = MANAGE_TEAM_ID ) teamName," +
                "MANAGE_TEAM_ID teamId " +
                "FROM\n" +
                "\tapp_manage_chronic_disease\n" +
                "WHERE 1=1 ";
        String ss = "";
        if(StringUtils.isNotBlank(qvo.getAreaCode())){
            String areaCode = AreaUtils.getAreaCode(qvo.getAreaCode());
            if(areaCode.length() == 5){
                areaCode +="0";
            }else if(areaCode.length() ==7){
                areaCode +="00";
            }else if(areaCode.length() ==8){
                areaCode += "0";
            }
            map.put("areaCode",areaCode+"%");
            sql += " AND MANAGE_AREA_CODE LIKE :areaCode";
            ss += " AND a.SIGN_AREA_CODE LIKE :areaCode";
            sqlMonth += " AND MANAGE_AREA_CODE LIKE :areaCode ";
            sqlTeam += " AND MANAGE_AREA_CODE LIKE :areaCode ";
        }else if(StringUtils.isNotBlank(qvo.getHospId())){
            map.put("hospId",qvo.getHospId());
            sql += " AND MANAGE_HOSP_ID =:hospId ";
            ss += " AND a.SIGN_HOSP_ID = :hospId";
            sqlMonth += " AND MANAGE_HOSP_ID = :hospId ";
            sqlTeam += " AND MANAGE_HOSP_ID = :hospId ";
        }
        if(StringUtils.isNotBlank(qvo.getStartTime())){
            map.put("startTime",qvo.getStartTime());
            startDate = qvo.getStartTime()+"-01";
            sql += " AND MANAGE_YEAR_MONTH>=:startTime ";
            sqlMonth += " AND MANAGE_YEAR_MONTH>=:startTime ";
            sqlTeam += " AND MANAGE_YEAR_MONTH>=:startTime ";
        }
        if(StringUtils.isNotBlank(qvo.getEndTime())){
            map.put("endTime",qvo.getEndTime());
            endDate = qvo.getEndTime()+"-01";
            sql += " AND MANAGE_YEAR_MONTH<=:endTime ";
            sqlMonth += " AND MANAGE_YEAR_MONTH<=:endTime ";
            sqlTeam += " AND MANAGE_YEAR_MONTH<=:endTime ";
            String time = ExtendDate.getLastDayOfMonthNew(qvo.getEndTime())+" 23:59:59";
            Calendar cal = Calendar.getInstance();
            if(ExtendDate.getCalendar(time).compareTo(cal)<0){//小于系统时间
                flag = false;
            }
        }
        List<Map> maps = sysDao.getServiceDo().findSqlMap(sql,map);
        if(maps != null && maps.size() >0) {
            if (maps.get(0).get("manageSignCount") != null) {
                signCount = (int) Double.parseDouble(maps.get(0).get("manageSignCount").toString());//已签约
            }
            if(maps.get(0).get("manageNcdCount") != null){
                signNcdCount = (int) Double.parseDouble(maps.get(0).get("manageNcdCount").toString());//慢病签约数
            }
            if(maps.get(0).get("manageBloodCount") != null){
                manageBloodCount = (int) Double.parseDouble(maps.get(0).get("manageBloodCount").toString());//只有高血压人数
            }
            if(maps.get(0).get("manageDiabetesCount") != null){
                manageDiabetesCount = (int) Double.parseDouble(maps.get(0).get("manageDiabetesCount").toString());//只有糖尿病人数
            }

            if(maps.get(0).get("manageDisBloodCount") != null){
                manageDisBloodCount = (int) Double.parseDouble(maps.get(0).get("manageDisBloodCount").toString());//既是高血压又是糖尿病人数
            }

            if(maps.get(0).get("manageBloodRedCount") != null){
                manageBloodRedCount = (int) Double.parseDouble(maps.get(0).get("manageBloodRedCount").toString());//高血压红标人数
            }
            if(maps.get(0).get("manageBloodYellowCount") != null){
                manageBloodYellowCount = (int) Double.parseDouble(maps.get(0).get("manageBloodYellowCount").toString());//高血压黄标人数
            }
            if(maps.get(0).get("manageBloodGreenCount") != null){
                manageBloodGreenCount = (int) Double.parseDouble(maps.get(0).get("manageBloodGreenCount").toString());//高血压绿标人数
            }
            if(maps.get(0).get("manageBloodGrayCount") != null){
                manageBloodGrayCount = (int) Double.parseDouble(maps.get(0).get("manageBloodGrayCount").toString());//高血压灰标人数
            }
            if(maps.get(0).get("manageDiabetesRedCount") != null){
                manageDiabetesRedCount = (int) Double.parseDouble(maps.get(0).get("manageDiabetesRedCount").toString());//糖尿病红标人数
            }
            if(maps.get(0).get("manageDiabetesYellowCount") != null){
                manageDiabetesYellowCount = (int) Double.parseDouble(maps.get(0).get("manageDiabetesYellowCount").toString());//糖尿病黄标人数
            }
            if(maps.get(0).get("manageDiabetesGreenCount") != null){
                manageDiabetesGreenCount = (int) Double.parseDouble(maps.get(0).get("manageDiabetesGreenCount").toString());//糖尿病绿标人数
            }
            if(maps.get(0).get("manageDiabetesGrayCount") != null){
                manageDiabetesGrayCount = (int) Double.parseDouble(maps.get(0).get("manageDiabetesGrayCount").toString());//糖尿病灰标人数
            }
            if(maps.get(0).get("manageLowFamilyCount") != null){
                manageLowFamilyCount = (int) Double.parseDouble(maps.get(0).get("manageLowFamilyCount").toString());//低保户(慢病)
            }
            if(maps.get(0).get("manageDestituteFamilyCount") != null){
                manageDestituteFamilyCount = (int) Double.parseDouble(maps.get(0).get("manageDestituteFamilyCount").toString());//特困户（五保户）(慢病)
            }
            if(maps.get(0).get("manageSpecialPlanFamilyCount") != null){
                manageSpecialPlanFamilyCount = (int) Double.parseDouble(maps.get(0).get("manageSpecialPlanFamilyCount").toString());//计生特殊家庭(慢病)
            }
            if(maps.get(0).get("manageGeneralPopulationCount") != null){
                manageGeneralPopulationCount = (int) Double.parseDouble(maps.get(0).get("manageGeneralPopulationCount").toString());//一般人口(慢病)
            }
            if(maps.get(0).get("managePoorFamilyCount") != null){
                managePoorFamilyCount = (int) Double.parseDouble(maps.get(0).get("managePoorFamilyCount").toString());//建档立卡贫困人口(慢病)
            }
        }
        //加上当天签约情况
        int tSignCount = 0;
        int tSignNcdCount = 0;
        int tManageBloodCount = 0;//只有高血压人数
        int tManageDiabetesCount = 0;//只有糖尿病人数
        int tManageDisBloodCount = 0;//既是高血压又是糖尿病人数
        int tManageBloodRedCount = 0;//高血压红标人数
        int tManageBloodYellowCount = 0;//高血压黄标人数
        int tManageBloodGreenCount = 0;//高血压绿标人数
        int tManageBloodGrayCount = 0;//高血压灰标人数
        int tManageDiabetesRedCount = 0;//糖尿病红标人数
        int tManageDiabetesYellowCount = 0;//糖尿病黄标人数
        int tManageDiabetesGreenCount = 0;//糖尿病绿标人数
        int tManageDiabetesGrayCount = 0;//糖尿病灰标人数
        int tManageLowFamilyCount = 0;//低保户(慢病)
        int tManageDestituteFamilyCount = 0;//特困户（五保户）(慢病)
        int tManageSpecialPlanFamilyCount = 0;//计生特殊家庭(慢病)
        int tManageGeneralPopulationCount = 0;//一般人口(慢病)
        int tManagePoorFamilyCount = 0;//建档立卡贫困人口(慢病)
        String teamMb = "";
        if(flag){
            map.put("sign_state",new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()});
            map.put("fwValue",new String[]{ResidentMangeType.TNB.getValue(),ResidentMangeType.GXY.getValue()});
            map.put("tstartTime",ExtendDate.getYMD(Calendar.getInstance())+" 00:00:00");
            map.put("tendTime",ExtendDate.getYMD(Calendar.getInstance())+" 23:59:59");
            ss += " AND a.SIGN_STATE IN(:sign_state) AND a.SIGN_FROM_DATE>=:tstartTime AND a.SIGN_FROM_DATE<=:tendTime ";
            String todaySql = " SELECT a.* FROM app_sign_form a " +
                    " INNER JOIN app_hosp_dept b ON a.sign_hosp_id = b.id " +
                    " INNER JOIN app_team c on a.sign_team_id = c.id " +
                    " WHERE 1=1 AND c.TEAM_DEL_STATE = '0' ";
            String ttodaySql = "SELECT COUNT(1) FROM (" +todaySql+ss+" group by a.id) cc";
            tSignCount = sysDao.getServiceDo().findCount(ttodaySql,map);
            signCount += tSignCount;

            String todayMbSql = "SELECT\n" +
                    "\ta.*\n" +
                    "FROM\n" +
                    "\tapp_sign_form a INNER JOIN app_label_group b ON a.ID = b.LABEL_SIGN_ID\n" +
                    " INNER JOIN app_hosp_dept c ON a.sign_hosp_id = c.id " +
                    " INNER JOIN app_team d on a.sign_team_id = d.id " +
                    "WHERE\n" +
                    "\t1 = 1\n" +
                    "AND b.LABEL_VALUE IN (:fwValue) AND b.LABEL_TYPE = '3' AND d.TEAM_DEL_STATE = '0' ";

            teamMb ="SELECT\n" +
                    "\ta.*\n" +
                    "FROM\n" +
                    "\tapp_sign_form a INNER JOIN app_label_group b ON a.ID = b.LABEL_SIGN_ID\n" +
                    " INNER JOIN app_hosp_dept c ON a.sign_hosp_id = c.id " +
                    " INNER JOIN app_team d on a.sign_team_id = d.id " +
                    "WHERE\n" +
                    "\t1 = 1 "+ss+" \n" +
                    "AND b.LABEL_VALUE IN (:fwValue) AND b.LABEL_TYPE = '3' AND d.TEAM_DEL_STATE = '0' ";

            String sss = "SELECT COUNT(1) from ("+todayMbSql+ss+" group by a.id) cc ";
            tSignNcdCount =sysDao.getServiceDo().findCount(sss,map);
            signNcdCount +=tSignNcdCount;
            //既是高血压又是糖尿病
            String hdSql ="SELECT COUNT(1) FROM (\n" +
                    "SELECT \n" +
                    "a.*,\n" +
                    "(SELECT GROUP_CONCAT(LABEL_VALUE) FROM app_label_group WHERE LABEL_TYPE = '3' AND LABEL_SIGN_ID = a.ID AND LABEL_VALUE IN ('5','6') ) labelValue,\n" +
                    "(SELECT GROUP_CONCAT(LABEL_COLOR) FROM app_label_disease WHERE LABEL_TYPE = '2' AND LABEL_SIGN_ID = a.ID AND LABEL_VALUE IN ('201','202') ) labelColor\n" +
                    "FROM app_sign_form a \n" +
                    " INNER JOIN app_hosp_dept c ON a.sign_hosp_id = c.id " +
                    " INNER JOIN app_team d on a.sign_team_id = d.id " +
                    "WHERE 1=1 AND d.TEAM_DEL_STATE = '0' "+ss+" group by a.id) cc \n" +
                    "WHERE 1=1\n" +
                    "AND FIND_IN_SET('5',cc.labelValue) \n" +
                    "AND FIND_IN_SET('6',cc.labelValue)  ";
            tManageDisBloodCount = sysDao.getServiceDo().findCount(hdSql,map);
            manageDisBloodCount += tManageDisBloodCount;
            //只有高血压
            String hSql = "SELECT COUNT(1) FROM (\n" +
                    "SELECT \n" +
                    "a.*,\n" +
                    "(SELECT GROUP_CONCAT(LABEL_VALUE) FROM app_label_group WHERE LABEL_TYPE = '3' AND LABEL_SIGN_ID = a.ID AND LABEL_VALUE IN ('5','6') ) labelValue,\n" +
                    "(SELECT GROUP_CONCAT(LABEL_COLOR) FROM app_label_disease WHERE LABEL_TYPE = '2' AND LABEL_SIGN_ID = a.ID AND LABEL_VALUE IN ('201','202') ) labelColor\n" +
                    "FROM app_sign_form a \n" +
                    " INNER JOIN app_hosp_dept c ON a.sign_hosp_id = c.id " +
                    " INNER JOIN app_team d on a.sign_team_id = d.id " +
                    "WHERE 1=1 AND d.TEAM_DEL_STATE = '0' "+ss+" group by a.id) cc \n" +
                    "WHERE 1=1\n" +
                    "AND cc.labelValue ='5'  ";
            tManageBloodCount = sysDao.getServiceDo().findCount(hSql,map);
            manageBloodCount += tManageBloodCount;
            //只有糖尿病数
            String dSql = "SELECT COUNT(1) FROM (\n" +
                    "SELECT \n" +
                    "a.*,\n" +
                    "(SELECT GROUP_CONCAT(LABEL_VALUE) FROM app_label_group WHERE LABEL_TYPE = '3' AND LABEL_SIGN_ID = a.ID AND LABEL_VALUE IN ('5','6') ) labelValue,\n" +
                    "(SELECT GROUP_CONCAT(LABEL_COLOR) FROM app_label_disease WHERE LABEL_TYPE = '2' AND LABEL_SIGN_ID = a.ID AND LABEL_VALUE IN ('201','202') ) labelColor\n" +
                    "FROM app_sign_form a \n" +
                    " INNER JOIN app_hosp_dept c ON a.sign_hosp_id = c.id " +
                    " INNER JOIN app_team d on a.sign_team_id = d.id " +
                    "WHERE 1=1 AND d.TEAM_DEL_STATE = '0' "+ss+" group by a.id) cc \n" +
                    "WHERE 1=1\n" +
                    "AND cc.labelValue ='6'  ";
            tManageDiabetesCount = sysDao.getServiceDo().findCount(dSql,map);
            manageDiabetesCount += tManageDiabetesCount;
            //高血压红、黄、绿、灰标
            String hRedSql = "SELECT COUNT(1) FROM (\n" +
                    "SELECT \n" +
                    "a.*,\n" +
                    "(SELECT GROUP_CONCAT(LABEL_VALUE) FROM app_label_group WHERE LABEL_TYPE = '3' AND LABEL_SIGN_ID = a.ID AND LABEL_VALUE = '5' ) labelValue,\n" +
                    "(SELECT GROUP_CONCAT(LABEL_COLOR) FROM app_label_disease WHERE LABEL_TYPE = '2' AND LABEL_SIGN_ID = a.ID AND LABEL_VALUE = '201' ) labelColor\n" +
                    "FROM app_sign_form a \n" +
                    " INNER JOIN app_hosp_dept c ON a.sign_hosp_id = c.id " +
                    " INNER JOIN app_team d on a.sign_team_id = d.id " +
                    "WHERE 1=1 AND d.TEAM_DEL_STATE = '0' "+ss+" group by a.id) cc \n" +
                    "WHERE 1=1\n" +
                    "AND cc.labelColor ='red'  \n";
            tManageBloodRedCount = sysDao.getServiceDo().findCount(hRedSql,map);
            manageBloodRedCount += tManageBloodRedCount;
            String hYellowSql = "SELECT COUNT(1) FROM (\n" +
                    "SELECT \n" +
                    "a.*,\n" +
                    "(SELECT GROUP_CONCAT(LABEL_VALUE) FROM app_label_group WHERE LABEL_TYPE = '3' AND LABEL_SIGN_ID = a.ID AND LABEL_VALUE = '5' ) labelValue,\n" +
                    "(SELECT GROUP_CONCAT(LABEL_COLOR) FROM app_label_disease WHERE LABEL_TYPE = '2' AND LABEL_SIGN_ID = a.ID AND LABEL_VALUE = '201' ) labelColor\n" +
                    "FROM app_sign_form a \n" +
                    " INNER JOIN app_hosp_dept c ON a.sign_hosp_id = c.id " +
                    " INNER JOIN app_team d on a.sign_team_id = d.id " +
                    "WHERE 1=1 AND d.TEAM_DEL_STATE = '0' "+ss+" group by a.id) cc \n" +
                    "WHERE 1=1\n" +
                    "AND cc.labelColor ='yellow'  \n";
            tManageBloodYellowCount = sysDao.getServiceDo().findCount(hYellowSql,map);
            manageBloodYellowCount += tManageBloodYellowCount;
            String hGreenSql = "SELECT COUNT(1) FROM (\n" +
                    "SELECT \n" +
                    "a.*,\n" +
                    "(SELECT GROUP_CONCAT(LABEL_VALUE) FROM app_label_group WHERE LABEL_TYPE = '3' AND LABEL_SIGN_ID = a.ID AND LABEL_VALUE = '5' ) labelValue,\n" +
                    "(SELECT GROUP_CONCAT(LABEL_COLOR) FROM app_label_disease WHERE LABEL_TYPE = '2' AND LABEL_SIGN_ID = a.ID AND LABEL_VALUE = '201' ) labelColor\n" +
                    "FROM app_sign_form a \n" +
                    " INNER JOIN app_hosp_dept c ON a.sign_hosp_id = c.id " +
                    " INNER JOIN app_team d on a.sign_team_id = d.id " +
                    "WHERE 1=1 AND d.TEAM_DEL_STATE = '0' "+ss+" group by a.id ) cc \n" +
                    "WHERE 1=1\n" +
                    "AND cc.labelColor ='green'\n";
            tManageBloodGreenCount = sysDao.getServiceDo().findCount(hGreenSql,map);
            manageBloodGreenCount += tManageBloodGreenCount;
            String hGraySql = "SELECT COUNT(1) FROM (\n" +
                    "SELECT \n" +
                    "a.*,\n" +
                    "(SELECT GROUP_CONCAT(LABEL_VALUE) FROM app_label_group WHERE LABEL_TYPE = '3' AND LABEL_SIGN_ID = a.ID AND LABEL_VALUE = '5' ) labelValue,\n" +
                    "(SELECT GROUP_CONCAT(LABEL_COLOR) FROM app_label_disease WHERE LABEL_TYPE = '2' AND LABEL_SIGN_ID = a.ID AND LABEL_VALUE = '201' ) labelColor\n" +
                    "FROM app_sign_form a \n" +
                    " INNER JOIN app_hosp_dept c ON a.sign_hosp_id = c.id " +
                    " INNER JOIN app_team d on a.sign_team_id = d.id " +
                    "WHERE 1=1 AND d.TEAM_DEL_STATE = '0' "+ss+" group by a.id) cc \n" +
                    "WHERE 1=1\n" +
                    "AND (cc.labelColor ='gray' OR cc.labelColor IS NULL )";
            tManageBloodGrayCount = sysDao.getServiceDo().findCount(hGraySql,map);
            manageBloodGrayCount += tManageBloodGrayCount;

            //糖尿病红、黄、绿、灰标
            String dRedSql = "SELECT COUNT(1) FROM (\n" +
                    "SELECT \n" +
                    "a.*,\n" +
                    "(SELECT GROUP_CONCAT(LABEL_VALUE) FROM app_label_group WHERE LABEL_TYPE = '3' AND LABEL_SIGN_ID = a.ID AND LABEL_VALUE = '6' ) labelValue,\n" +
                    "(SELECT GROUP_CONCAT(LABEL_COLOR) FROM app_label_disease WHERE LABEL_TYPE = '2' AND LABEL_SIGN_ID = a.ID AND LABEL_VALUE = '202' ) labelColor\n" +
                    "FROM app_sign_form a \n" +
                    " INNER JOIN app_hosp_dept c ON a.sign_hosp_id = c.id " +
                    " INNER JOIN app_team d on a.sign_team_id = d.id " +
                    "WHERE 1=1 AND d.TEAM_DEL_STATE = '0' "+ss+" group by a.id) cc \n" +
                    "WHERE 1=1\n" +
                    "AND cc.labelColor ='red'\n";
            tManageDiabetesRedCount = sysDao.getServiceDo().findCount(dRedSql,map);
            manageDiabetesRedCount += tManageDiabetesRedCount;
            String dYellowSql = "SELECT COUNT(1) FROM (\n" +
                    "SELECT \n" +
                    "a.*,\n" +
                    "(SELECT GROUP_CONCAT(LABEL_VALUE) FROM app_label_group WHERE LABEL_TYPE = '3' AND LABEL_SIGN_ID = a.ID AND LABEL_VALUE = '6' ) labelValue,\n" +
                    "(SELECT GROUP_CONCAT(LABEL_COLOR) FROM app_label_disease WHERE LABEL_TYPE = '2' AND LABEL_SIGN_ID = a.ID AND LABEL_VALUE = '202' ) labelColor\n" +
                    "FROM app_sign_form a \n" +
                    " INNER JOIN app_hosp_dept c ON a.sign_hosp_id = c.id " +
                    " INNER JOIN app_team d on a.sign_team_id = d.id " +
                    "WHERE 1=1 AND d.TEAM_DEL_STATE = '0' "+ss+" group by a.id) cc \n" +
                    "WHERE 1=1\n" +
                    "AND cc.labelColor ='yellow'\n";
            tManageDiabetesYellowCount = sysDao.getServiceDo().findCount(dYellowSql,map);
            manageDiabetesYellowCount += tManageDiabetesYellowCount;
            String dGreenSql = "SELECT COUNT(1) FROM (\n" +
                    "SELECT \n" +
                    "a.*,\n" +
                    "(SELECT GROUP_CONCAT(LABEL_VALUE) FROM app_label_group WHERE LABEL_TYPE = '3' AND LABEL_SIGN_ID = a.ID AND LABEL_VALUE = '6' ) labelValue,\n" +
                    "(SELECT GROUP_CONCAT(LABEL_COLOR) FROM app_label_disease WHERE LABEL_TYPE = '2' AND LABEL_SIGN_ID = a.ID AND LABEL_VALUE = '202' ) labelColor\n" +
                    "FROM app_sign_form a \n" +
                    " INNER JOIN app_hosp_dept c ON a.sign_hosp_id = c.id " +
                    " INNER JOIN app_team d on a.sign_team_id = d.id " +
                    "WHERE 1=1 AND d.TEAM_DEL_STATE = '0' "+ss+" group by a.id) cc \n" +
                    "WHERE 1=1\n" +
                    "AND cc.labelColor ='green'\n";
            tManageDiabetesGreenCount = sysDao.getServiceDo().findCount(dGreenSql,map);
            manageDiabetesGreenCount += tManageDiabetesGreenCount;
            String dGraySql = "SELECT COUNT(1) FROM (\n" +
                    "SELECT \n" +
                    "a.*,\n" +
                    "(SELECT GROUP_CONCAT(LABEL_VALUE) FROM app_label_group WHERE LABEL_TYPE = '3' AND LABEL_SIGN_ID = a.ID AND LABEL_VALUE = '6' ) labelValue,\n" +
                    "(SELECT GROUP_CONCAT(LABEL_COLOR) FROM app_label_disease WHERE LABEL_TYPE = '2' AND LABEL_SIGN_ID = a.ID AND LABEL_VALUE = '202' ) labelColor\n" +
                    "FROM app_sign_form a \n" +
                    " INNER JOIN app_hosp_dept c ON a.sign_hosp_id = c.id " +
                    " INNER JOIN app_team d on a.sign_team_id = d.id " +
                    "WHERE 1=1 AND d.TEAM_DEL_STATE = '0' "+ss+" group by a.id) cc \n" +
                    "WHERE 1=1\n" +
                    "AND (cc.labelColor ='gray' OR cc.labelColor IS NULL )";
            tManageDiabetesGrayCount = sysDao.getServiceDo().findCount(dGraySql,map);
            manageDiabetesGrayCount += tManageDiabetesGrayCount;


        }
        //慢病人群占县人口比例
        double mbl = 0;
        if(totalPopulation>0){
            mbl = MyMathUtil.div(Double.valueOf(signNcdCount),Double.valueOf(totalPopulation),6)*100;
            mbl = MyMathUtil.round(mbl,2);
        }
        //慢病签约率
        double mbqyl = 0;
        if(signCount>0){
            mbqyl = MyMathUtil.div(Double.valueOf(signNcdCount),Double.valueOf(signCount),6)*100;
            mbqyl = MyMathUtil.round(mbqyl,2);
        }
        hbpCount = manageBloodRedCount+manageBloodYellowCount+manageBloodGreenCount+manageBloodGrayCount;
        dmCount = manageDiabetesRedCount+manageDiabetesYellowCount+manageDiabetesGreenCount+manageDiabetesGrayCount;
        //高血压占比
        double hbpl = 0;
        if(signCount>0){
            hbpl = MyMathUtil.div(Double.valueOf(hbpCount),Double.valueOf(signCount),6)*100;
            hbpl = MyMathUtil.round(hbpl,2);
        }
        //糖尿病占比
        double dml = 0;
        if(signCount>0){
            dml = MyMathUtil.div(Double.valueOf(dmCount),Double.valueOf(signCount),6)*100;
            dml = MyMathUtil.round(dml,2);
        }
        //高血压红、黄、绿、灰标占比
        double hRedl = 0;
        double hYellowl = 0;
        double hGreenl = 0;
        double hGrayl = 0;
        if(hbpCount>0){
            hRedl = MyMathUtil.div(Double.valueOf(manageBloodRedCount),Double.valueOf(hbpCount),6)*100;
            hRedl = MyMathUtil.round(hRedl,2);
            hYellowl = MyMathUtil.div(Double.valueOf(manageBloodYellowCount),Double.valueOf(hbpCount),6)*100;
            hYellowl = MyMathUtil.round(hYellowl,2);
            hGreenl = MyMathUtil.div(Double.valueOf(manageBloodGreenCount),Double.valueOf(hbpCount),6)*100;
            hGreenl = MyMathUtil.round(hGreenl,2);
            hGrayl = MyMathUtil.div(Double.valueOf(manageBloodGrayCount),Double.valueOf(hbpCount),6)*100;
            hGrayl = MyMathUtil.round(hGrayl,2);
        }
        //糖尿病红、黄、绿、灰标占比
        double dRedl = 0;
        double dYellowl = 0;
        double dGreenl = 0;
        double dGrayl = 0;
        if(dmCount>0){
            dRedl = MyMathUtil.div(Double.valueOf(manageDiabetesRedCount),Double.valueOf(dmCount),6)*100;
            dRedl = MyMathUtil.round(dRedl,2);
            dYellowl = MyMathUtil.div(Double.valueOf(manageDiabetesYellowCount),Double.valueOf(dmCount),6)*100;
            dYellowl = MyMathUtil.round(dYellowl,2);
            dGreenl = MyMathUtil.div(Double.valueOf(manageDiabetesGreenCount),Double.valueOf(dmCount),6)*100;
            dGreenl = MyMathUtil.round(dGreenl,2);
            dGrayl = MyMathUtil.div(Double.valueOf(manageDiabetesGrayCount),Double.valueOf(dmCount),6)*100;
            dGrayl = MyMathUtil.round(dGrayl,2);
        }
        returnMap.put("hRedl",hRedl);//高血压红标占比
        returnMap.put("hYellowl",hYellowl);//高血压黄标占比
        returnMap.put("hGreenl",hGreenl);//高血压绿标占比
        returnMap.put("hGrayl",hGrayl);//高血压灰标占比

        returnMap.put("dRedl",dRedl);//糖尿病红标占比
        returnMap.put("dYellowl",dYellowl);//糖尿病黄标占比
        returnMap.put("dGreenl",dGreenl);//糖尿病绿标占比
        returnMap.put("dGrayl",dGrayl);//糖尿病灰标占比

        returnMap.put("mbl",mbl);//慢病人群占县人口比例
        returnMap.put("mbqyl",mbqyl);//慢病签约率
        returnMap.put("hbpCount",hbpCount);
        returnMap.put("dmCount",dmCount);
        returnMap.put("hbpl",hbpl);
        returnMap.put("dml",dml);
        returnMap.put("totalPopulation",totalPopulation);//县总人口
        returnMap.put("signCount",signCount);//总签约人数
        returnMap.put("signNcdCount",signNcdCount);//慢病人群数
        returnMap.put("manageBloodCount",manageBloodCount);//只有高血压人数
        returnMap.put("manageDiabetesCount",manageDiabetesCount);//只有糖尿病人数
        returnMap.put("manageDisBloodCount",manageDisBloodCount);//既是高血压又是糖尿病人数
        returnMap.put("manageBloodRedCount",manageBloodRedCount);//高血压红标人数
        returnMap.put("manageBloodYellowCount",manageBloodYellowCount);//高血压黄标人数
        returnMap.put("manageBloodGreenCount",manageBloodGreenCount);//高血压绿标人数
        returnMap.put("manageBloodGrayCount",manageBloodGrayCount);//高血压灰标人数
        returnMap.put("manageDiabetesRedCount",manageDiabetesRedCount);//糖尿病红标人数
        returnMap.put("manageDiabetesYellowCount",manageDiabetesYellowCount);//糖尿病黄标人数
        returnMap.put("manageDiabetesGreenCount",manageDiabetesGreenCount);//糖尿病绿标人数
        returnMap.put("manageDiabetesGrayCount",manageDiabetesGrayCount);//糖尿病灰标人数
        returnMap.put("manageLowFamilyCount",manageLowFamilyCount);//低保户(慢病)
        returnMap.put("manageDestituteFamilyCount",manageDestituteFamilyCount);//特困户（五保户）(慢病)
        returnMap.put("manageSpecialPlanFamilyCount",manageSpecialPlanFamilyCount);//计生特殊家庭(慢病)
        returnMap.put("manageGeneralPopulationCount",manageGeneralPopulationCount);//一般人口(慢病)
        returnMap.put("managePoorFamilyCount",managePoorFamilyCount);//建档立卡贫困人口(慢病)

        List<Map<String,Object>> listMbb = new ArrayList<>();
        List<String> ls = ExtendDateUtil.getListBetweenMonth(startDate,endDate);
        if(ls!=null && ls.size()>0){
            String tDate = ExtendDate.getYM(Calendar.getInstance());
            for(String s:ls){
                Map<String,Object> mbMap = new HashMap<>();
                int hCount = 0;//每个月的高血压数
                int dCount = 0;//每个月的糖尿病数
                int hRedCount = 0;
                int hYellowCount = 0;
                int hGreenCount = 0;
                int hGrayCount = 0;
                int dRedCount = 0;
                int dYellowCount = 0;
                int dGreenCount = 0;
                int dGrayCount = 0;
                map.put("monthValue",s);
                sqlMonth += "  AND MANAGE_YEAR_MONTH =:monthValue ";
                List<Map> listMb = sysDao.getServiceDo().findSqlMap(sqlMonth,map);
                if(listMb != null && listMb.size()>0){
                    Map ll = listMb.get(0);
                    if(ll.get("manageBloodRedCount") != null){
                        hRedCount = (int) Double.parseDouble(ll.get("manageBloodRedCount").toString());//高血压红标人数
                    }
                    if(ll.get("manageBloodYellowCount") != null){
                        hYellowCount = (int) Double.parseDouble(ll.get("manageBloodYellowCount").toString());//高血压黄标人数
                    }
                    if(ll.get("manageBloodGreenCount") != null){
                        hGreenCount = (int) Double.parseDouble(ll.get("manageBloodGreenCount").toString());//高血压绿标人数
                    }
                    if(ll.get("manageBloodGrayCount") != null){
                        hGrayCount = (int) Double.parseDouble(ll.get("manageBloodGrayCount").toString());//高血压灰标人数
                    }
                    if(ll.get("manageDiabetesRedCount") != null){
                        dRedCount = (int) Double.parseDouble(ll.get("manageDiabetesRedCount").toString());//糖尿病红标人数
                    }
                    if(ll.get("manageDiabetesYellowCount") != null){
                        dYellowCount = (int) Double.parseDouble(ll.get("manageDiabetesYellowCount").toString());//糖尿病黄标人数
                    }
                    if(ll.get("manageDiabetesGreenCount") != null){
                        dGreenCount = (int) Double.parseDouble(ll.get("manageDiabetesGreenCount").toString());//糖尿病绿标人数
                    }
                    if(ll.get("manageDiabetesGrayCount") != null){
                        dGrayCount = (int) Double.parseDouble(ll.get("manageDiabetesGrayCount").toString());//糖尿病灰标人数
                    }
                    if(tDate.equals(s)){
                        hRedCount += tManageBloodRedCount;
                        hYellowCount +=tManageBloodYellowCount;
                        hGreenCount +=tManageBloodGreenCount;
                        hGrayCount +=tManageBloodGrayCount;
                        dRedCount +=tManageDiabetesRedCount;
                        dYellowCount +=tManageDiabetesYellowCount;
                        dGreenCount +=tManageDiabetesGreenCount;
                        dGrayCount +=tManageDiabetesGrayCount;
                    }
                    hCount = hRedCount+hYellowCount+hGrayCount+hGreenCount;
                    dCount = dRedCount+dYellowCount+dGrayCount+dGreenCount;
                    mbMap.put("monthDate",s);
                    mbMap.put("hCount",hCount);//每个月的高血压数
                    mbMap.put("dCount",dCount);//每个月的糖尿病数
                    mbMap.put("hRedCount",hRedCount);
                    mbMap.put("hYellowCount",hYellowCount);
                    mbMap.put("hGreenCount",hGreenCount);
                    mbMap.put("hGrayCount",hGrayCount);
                    mbMap.put("dRedCount",dRedCount);
                    mbMap.put("dYellowCount",dYellowCount);
                    mbMap.put("dGreenCount",dGreenCount);
                    mbMap.put("dGrayCount",dGrayCount);
                    listMbb.add(mbMap);
                }
            }
        }
        returnMap.put("listMb",listMbb);
        sqlTeam += " group by MANAGE_TEAM_ID ORDER BY manageNcdCount DESC\n" +
                "LIMIT 0,\n" +
                " 10";
        List<Map> listTeam = sysDao.getServiceDo().findSqlMap(sqlTeam,map);
        List<Map<String,Object>> listTeambb = new ArrayList<>();
        if(listTeam != null && listTeam.size()>0){
            for(Map ll:listTeam){
                Map<String,Object> teamMap = new HashMap<>();
                int ncdCount = 0;
                String hospName = "";
                String teamName = "";
                String teamId = "";
                if(ll.get("manageNcdCount") != null){
                    ncdCount = (int) Double.parseDouble(ll.get("manageNcdCount").toString());//慢病人数
                }
                if(ll.get("hospName") != null){
                    hospName = ll.get("hospName").toString();
                }
                if(ll.get("teamName") != null){
                    teamName = ll.get("teamName").toString();
                }
                if(ll.get("teamId") != null){
                    teamId = ll.get("teamId").toString();
                }
                if(StringUtils.isNotBlank(teamMb) && StringUtils.isNotBlank(teamId)){//查询当天团队签约数
                    map.put("teamId",teamId);
                    teamMb += " AND a.SIGN_TEAM_ID =:teamId ";
                    String teamMbSql = "SELECT COUNT(1) from ("+teamMb+" group by a.id) cc";
                    ncdCount += sysDao.getServiceDo().findCount(teamMbSql,map);
                }
                teamMap.put("ncdCount",ncdCount);
                teamMap.put("hospName",hospName);
                teamMap.put("teamName",teamName);
                listTeambb.add(teamMap);
            }
        }
        returnMap.put("listTeambb",listTeambb);
        return returnMap;
    }

    /**
     * 随访量、健康指导、健康教育、求助量、未缴费人数统计
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> findOtherCount(ResidentVo qvo) throws Exception {
        Map<String,Object> rMap = new HashMap<>();
        Map<String,Object> map = new HashMap<>();
        //时间为当前时间到前6天这个时间段
        Calendar nowCal = Calendar.getInstance();//获取当前时间
        nowCal.add(Calendar.DATE,-6);
        String yearStart = ExtendDate.getYMD(nowCal)+" 00:00:00";
        map.put("yearStart", yearStart);
        map.put("yearEnd",ExtendDate.getLastDayOfMonth(qvo.getYearEnd())+" 23:59:59");
        String[] followState = new String[]{FollowPlanState.YIWANCHENG.getValue(),FollowPlanState.SIWANG.getValue(),FollowPlanState.SHIFANG.getValue()};
        map.put("state", followState);
        //查询随访量
        String sqlSf = " SELECT count(1) FROM APP_FOLLOW_PLAN WHERE 1=1";
        sqlSf +=" AND SF_FOLLOW_DATE>=:yearStart AND SF_FOLLOW_DATE <=:yearEnd";
        sqlSf +=" AND SF_FOLLOW_STATE IN :state";
        //健康指导,
        String sqlGuide = "SELECT count(1) FROM APP_HEALTH_MEDDLE WHERE 1=1";
        sqlGuide += " AND HM_CREATE_TIME >=:yearStart AND HM_CREATE_TIME <=:yearEnd";
        //健康教育,教育表
        String sqlHD = "SELECT count(1) FROM APP_USER_HEALTHED WHERE 1=1";
        sqlHD +=" AND HED_CREATE_TIME >=:yearStart AND HED_CREATE_TIME <=:yearEnd";
        //求助量,求助表
        String sqlHelp = "SELECT count(1) FROM APP_SEEK_HELP WHERE 1=1";
        sqlHelp += " AND SEEK_HELP_DATE >=:yearStart AND SEEK_HELP_DATE <=:yearEnd";
        //未缴费人数
        String[] signStates = new String[]{SignFormType.YUQY.getValue(),SignFormType.YQY.getValue()};
        map.put("SIGNSTATE", signStates);
        map.put("payState", PayType.WEIJIAOFEI.getValue());
        String sqlWjf = "SELECT count(1) FROM APP_SIGN_FORM t WHERE 1=1 AND t.SIGN_STATE IN (:SIGNSTATE) AND t.SIGN_PAY_STATE = :payState ";
        sqlWjf += " AND t.SIGN_FROM_DATE >=:yearStart AND t.SIGN_FROM_DATE <=:yearEnd";
        if(StringUtils.isNotBlank(qvo.getAreaId())){
            String areaCode = AreaUtils.getAreaCode(qvo.getAreaId());
            map.put("areaCode",areaCode+"%");
            sqlSf +=" AND SF_HOS_AREA_CODE LIKE :areaCode";
            sqlGuide += " AND HM_AREA_CODE LIKE :areaCode";
            sqlHD +=" AND HED_AREA_CODE LIKE :areaCode";
            sqlHelp += " AND SEEK_AREA_CODE LIKE :areaCode";
            sqlWjf += " AND t.SIGN_AREA_CODE LIKE :areaCode";

        }else if(StringUtils.isNotBlank(qvo.getHospId())){
            map.put("hospId",qvo.getHospId());
            sqlSf += " AND SF_HOS_ID =:hospId ";
            sqlGuide += " AND HM_HOSP_ID = :hospId";
            sqlHD +=" AND HED_HOSP_ID = :hospId";
            sqlHelp += " AND SEEK_HOSP_ID = :hospId";
            sqlWjf += " AND t.SIGN_HOSP_ID = :hospId";

        }else if(StringUtils.isNotBlank(qvo.getTeamId())){
            map.put("teamId",qvo.getTeamId());
            sqlSf += " AND SF_TEAM_ID =:teamId ";
            sqlGuide += " AND HM_TEAM_ID =:teamId ";
            sqlHD += " AND HED_TEAM_ID =:teamId ";
            sqlHelp += " AND SEEK_TEAM_ID =:teamId ";
            sqlWjf += " AND t.SIGN_TEAM_ID =:teamId ";
        }

        int sfCount = sysDao.getServiceReadDo().findCount(sqlSf,map);
        int guideCount = sysDao.getServiceReadDo().findCount(sqlGuide,map);
        int hdCount = sysDao.getServiceReadDo().findCount(sqlHD,map);
        int helpCount = sysDao.getServiceReadDo().findCount(sqlHelp,map);
        int wjfCount = sysDao.getServiceReadDo().findCount(sqlWjf,map);
        rMap.put("manageSfCount",sfCount);
        rMap.put("manageGuideCount",guideCount);
        rMap.put("manageHdCount",hdCount);
        rMap.put("manageHelpCount",helpCount);
        rMap.put("manageNotPayCount",wjfCount);
        return rMap;
    }

    @Override
    public Map<String, Object> findMangeOtherCount(ResidentVo qvo) throws Exception {
        Map<String, Object> map = new HashMap<String,Object>();
        if(StringUtils.isNotBlank(qvo.getAreaId())){
            CdAddress address = this.sysDao.getCdAddressDao().findByCacheCode(qvo.getAreaId());
            map = getOtherCount(address, null,qvo.getYearStart(),qvo.getYearEnd());
        }else if(StringUtils.isNotBlank(qvo.getHospId())){
            AppHospDept hospDept = (AppHospDept)this.sysDao.getServiceReadDo().find(AppHospDept.class,qvo.getHospId());
            if(hospDept != null){
                CdAddress address = this.sysDao.getCdAddressDao().findByCacheCode(hospDept.getHospAreaCode());
                map = getOtherCount(address, hospDept,qvo.getYearStart(),qvo.getYearEnd());
            }
        }
        return map;
    }

    /**
     * 签约量统计（省，市,社区）
     * @return
     */
    public Map<String,Object> getOtherCount(CdAddress address,AppHospDept hosp,String startDate,String endDate) throws Exception{
        Calendar calendar = Calendar.getInstance();
        int nowYear = calendar.get(Calendar.YEAR);
        int nowMonth = calendar.get(Calendar.MONTH) + 1;
        int now = Integer.parseInt(String.valueOf(nowYear)+ String.valueOf(nowMonth));
        String[] year = endDate.split("-");
        int endYear = Integer.parseInt(year[0]);
        int endMonth = Integer.parseInt(year[1]);
        int end = Integer.parseInt(String.valueOf(endYear)+ String.valueOf(endMonth));
        int sfCount = 0;//随访数
        int guideCount = 0;//健康指导数
        int hdCount = 0 ;//健康教育数
        int helpCount = 0;//求助数
        int notPayCount = 0;//未缴费数

        Map<String,Object> map = new HashMap<String,Object>();
        Map<String,Object> returnMap = new HashMap<String,Object>();
        Map<String,Object> returnSignCountMap = new HashMap<String,Object>();
        map.put("STARTDATE",startDate);
        map.put("ENDDATE",endDate);
        map.put("stratTime",ExtendDate.getFirstDayOfMonth(startDate) + " 00:00:00");
        map.put("endTime",ExtendDate.getLastDayOfMonth(endDate) + " 23:59:59");
//        String sqlDate = " AND t.MANAGE_YEAR_MONTH >= :STARTDATE AND t.MANAGE_YEAR_MONTH <= :ENDDATE ";
        String sql = "SELECT\n" +
                "\tSUM(t.MANAGE_SF_COUNT) manageSfCount,\n" +
                "\tSUM(t.MANAGE_GUIDE_COUNT) manageGuideCount,\n" +
                "\tSUM(t.MANAGE_HD_COUNT) manageHdCount,\n" +
                "\tSUM(t.MANAGE_HELP_COUNT) manageHelpCount,\n" +
                "\tSUM(t.MANAGE_NOT_PAY_COUNT) manageNotPayCount\n" +
                "FROM\n" +
                "\tapp_manage_other_count t where 1=1 ";
        if(hosp != null){
            map.put("HOSP_ID",hosp.getId());
            sql += " AND t.MANAGE_HOSP_ID = :HOSP_ID ";

        }else {
            map.put("AREA_CODE",AreaUtils.getAreaCode(address.getCtcode())+"%");
            //漳州“台商投资区”，“招商局漳州开发区”，“常山农场经济开发区”这三个区要特别判断
            if("350698000000".equals(address.getCtcode())){//台商投资区
                map.put("AREA_CODE","350681102%");
            }else if("350699000000".equals(address.getCtcode())){//招商局漳州开发区
                map.put("AREA_CODE","350681501%");
            }else if("350697000000".equals(address.getCtcode())){//常山农场经济开发区
                map.put("AREA_CODE","350622450%");
            }else if("350681000000".equals(address.getCtcode())){
                map.put("notAreaCode",new String[]{"350681102000","350681501000","350622450000"});
                sql += " AND t.MANAGE_AREA_CODE NOT IN (:notAreaCode) ";
            }else if("350622000000".equals(address.getCtcode())){
                map.put("notAreaCode",new String[]{"350681102000","350681501000","350622450000"});
                sql += " AND t.MANAGE_AREA_CODE NOT IN (:notAreaCode) ";
            }
            sql += " AND t.MANAGE_AREA_CODE LIKE :AREA_CODE ";
        }
        List<Map> maps = this.sysDao.getServiceReadDo().findSqlMap(sql,map);
        if(maps != null && maps.size()>0){
            if(maps.get(0).get("manageSfCount") != null){
                sfCount = (int) Double.parseDouble(maps.get(0).get("manageSfCount").toString());//随访统计量
            }
            if(maps.get(0).get("manageGuideCount") != null){
                guideCount = (int) Double.parseDouble(maps.get(0).get("manageGuideCount").toString());//健康指导统计量
            }
            if(maps.get(0).get("manageHdCount") != null){
                hdCount = (int) Double.parseDouble(maps.get(0).get("manageHdCount").toString());//健康教育统计量
            }
            if(maps.get(0).get("manageHelpCount") != null){
                helpCount = (int) Double.parseDouble(maps.get(0).get("manageHelpCount").toString());//求助统计量
            }
            if(maps.get(0).get("manageNotPayCount") != null){
                notPayCount = (int) Double.parseDouble(maps.get(0).get("manageNotPayCount").toString());//未缴费统计量
            }
        }

        map.put("STARTDATE", ExtendDate.getYMD(Calendar.getInstance())+" 00:00:00");
        map.put("ENDDATE",ExtendDate.getYMD(Calendar.getInstance())+" 23:59:59");
        //查询随访量
        String[] followState = new String[]{FollowPlanState.YIWANCHENG.getValue(),FollowPlanState.SIWANG.getValue(),FollowPlanState.SHIFANG.getValue()};
        map.put("state", followState);
        String sqlSf = " SELECT count(1) FROM APP_FOLLOW_PLAN WHERE 1=1";
        sqlSf +=" AND SF_FOLLOW_DATE>=:STARTDATE AND SF_FOLLOW_DATE <=:ENDDATE";
        sqlSf +=" AND SF_FOLLOW_STATE IN :state";
        //查询健康指导
        String sqlGuide = "SELECT count(1) FROM APP_HEALTH_MEDDLE WHERE 1=1";
        sqlGuide += " AND HM_CREATE_TIME >=:STARTDATE AND HM_CREATE_TIME <=:ENDDATE";
        //健康教育,教育表
        String sqlHD = "SELECT count(1) FROM APP_USER_HEALTHED WHERE 1=1";
        sqlHD +=" AND HED_CREATE_TIME >=:STARTDATE AND HED_CREATE_TIME <=:ENDDATE";
        //求助量,求助表
        String sqlHelp = "SELECT count(1) FROM APP_SEEK_HELP WHERE 1=1";
        sqlHelp += " AND SEEK_HELP_DATE >=:STARTDATE AND SEEK_HELP_DATE <=:ENDDATE";
        //未缴费人数
        String[] signStates = new String[]{SignFormType.YUQY.getValue(),SignFormType.YQY.getValue()};
        map.put("SIGNSTATE", signStates);
        map.put("payState", PayType.WEIJIAOFEI.getValue());
        String sqlWjf = "SELECT count(1) FROM APP_SIGN_FORM t WHERE 1=1 AND t.SIGN_STATE IN (:SIGNSTATE) AND t.SIGN_PAY_STATE = :payState ";
        sqlWjf += " AND t.SIGN_FROM_DATE >=:STARTDATE AND t.SIGN_FROM_DATE <=:ENDDATE";

        if(hosp != null){
            map.put("HOSP_ID",hosp.getId());
            sqlSf += " AND SF_HOS_ID =:HOSP_ID ";
            sqlGuide += " AND HM_HOSP_ID = :HOSP_ID";
            sqlHD +=" AND HED_HOSP_ID = :HOSP_ID";
            sqlHelp += " AND SEEK_HOSP_ID = :HOSP_ID";
            sqlWjf += " AND t.SIGN_HOSP_ID = :HOSP_ID";
        }else {
            map.put("AREA_CODE",AreaUtils.getAreaCode(address.getCtcode())+"%");
            //漳州“台商投资区”，“招商局漳州开发区”，“常山农场经济开发区”这三个区要特别判断
            if("350698000000".equals(address.getCtcode())){//台商投资区
                map.put("AREA_CODE","350681102%");
            }else if("350699000000".equals(address.getCtcode())){//招商局漳州开发区
                map.put("AREA_CODE","350681501%");
            }else if("350697000000".equals(address.getCtcode())){//常山农场经济开发区
                map.put("AREA_CODE","350622450%");
            }else if("350681000000".equals(address.getCtcode())){
                map.put("notAreaCode",new String[]{"350681102000","350681501000","350622450000"});
                sqlSf += " AND SF_HOS_AREA_CODE NOT IN (:notAreaCode) ";
                sqlGuide += " AND HM_AREA_CODE NOT IN (:notAreaCode) ";
                sqlHD += " AND HED_AREA_CODE NOT IN (:notAreaCode) ";
                sqlHelp += " AND SEEK_AREA_CODE NOT IN (:notAreaCode) ";
                sqlWjf += " AND t.SIGN_AREA_CODE NOT IN (:notAreaCode) ";
            }else if("350622000000".equals(address.getCtcode())){
                map.put("notAreaCode",new String[]{"350681102000","350681501000","350622450000"});
                sqlSf += " AND SF_HOS_AREA_CODE NOT IN (:notAreaCode) ";
                sqlGuide += " AND HM_AREA_CODE NOT IN (:notAreaCode) ";
                sqlHD += " AND HED_AREA_CODE NOT IN (:notAreaCode) ";
                sqlHelp += " AND SEEK_AREA_CODE NOT IN (:notAreaCode) ";
                sqlWjf += " AND t.SIGN_AREA_CODE NOT IN (:notAreaCode) ";
            }
            sqlSf +=" AND SF_HOS_AREA_CODE LIKE :AREA_CODE";
            sqlGuide += " AND HM_AREA_CODE LIKE :AREA_CODE";
            sqlHD +=" AND HED_AREA_CODE LIKE :AREA_CODE";
            sqlHelp += " AND SEEK_AREA_CODE LIKE :AREA_CODE";
            sqlWjf += " AND t.SIGN_AREA_CODE LIKE :AREA_CODE";
        }

        sfCount += sysDao.getServiceDo().findCount(sqlSf,map);
        guideCount += sysDao.getServiceDo().findCount(sqlGuide,map);
        hdCount += sysDao.getServiceDo().findCount(sqlHD,map);
        helpCount += sysDao.getServiceDo().findCount(sqlHelp,map);
        notPayCount += sysDao.getServiceDo().findCount(sqlWjf,map);

        returnMap.put("sfCount",sfCount);
        returnMap.put("guideCount",guideCount);
        returnMap.put("hDCount",hdCount);
        returnMap.put("helpCount",helpCount);
        returnMap.put("wjfCount",notPayCount);
        return returnMap;
    }

    @Override
    public Map<String, Object> findRankCount(ResidentVo qvo) throws Exception {
        Map<String,Object> rMap = new HashMap<>();
        Map<String,Object> map = new HashMap<>();
        int totalSfCount = 0;//总随访数
        int completeSfCount = 0;//完成随访数
        int totalHealth = 0;//总健康干预
        int completeHealth = 0;//完成健康干预
        int totalWork = 0;//总工作量
        int completeWork = 0;//完成工作量
        int refuseSign = 0;//拒签
        int gotoSign = 0;//转签
        map.put("yearStart", ExtendDate.getFirstDayOfMonth(qvo.getYearStart())+" 00:00:00");
        map.put("yearEnd",ExtendDate.getLastDayOfMonth(qvo.getYearEnd())+" 23:59:59");
        String[] followState = new String[]{FollowPlanState.YIWANCHENG.getValue(),FollowPlanState.SIWANG.getValue(),FollowPlanState.SHIFANG.getValue()};
        map.put("state", followState);
        //总随访
        String sqlsf = "SELECT count(1) FROM APP_FOLLOW_PLAN t WHERE 1=1 AND t.SF_FOLLOW_DATE BETWEEN :yearStart AND :yearEnd ";
        //总工作量
        String sqlwork =  "SELECT count(1) FROM APP_WORKING_PLAN WHERE 1=1  AND PLAN_DATE BETWEEN :yearStart AND :yearEnd ";
        //总健康干预
        String sqlwork2 =  "SELECT count(1) FROM APP_WORKING_PLAN WHERE 1=1 and PLAN_TYPE='2' AND PLAN_DATE BETWEEN :yearStart AND :yearEnd ";
        //完成健康干预
        String gsql="SELECT COUNT(1) FROM APP_HEALTH_MEDDLE a WHERE 1=1 AND a.HM_GUIDE_TYPE = 2 AND HM_CREATE_TIME BETWEEN :yearStart AND :yearEnd ";
        //拒签
        String sqlRefuse="SELECT count(1) FROM APP_REFUSE_SIGN  WHERE 1=1 AND RS_REFUSE_TIME BETWEEN :yearStart AND :yearEnd ";
        //转签
        String sqlSign = sqlRefuse + " AND RS_SIGN_TIME IS NOT NULL AND RS_SIGN_TIME BETWEEN :yearStart AND :yearEnd ";

        if(StringUtils.isNotBlank(qvo.getTeamId())){
            map.put("teamId",qvo.getTeamId());
            sqlsf +=" AND t.SF_TEAM_ID = :teamId ";
            sqlwork +=" AND PLAN_TEAM_ID = :teamId ";
            sqlwork2 +=" AND PLAN_TEAM_ID = :teamId ";
            gsql += " AND a.HM_TEAM_ID = :teamId ";
            sqlRefuse += " AND RS_REFUSE_TEAM_ID = :teamId ";
            sqlSign +=" AND RS_SIGN_TEAM_ID = :teamId ";

        }

        totalSfCount = sysDao.getServiceDo().findCount(sqlsf,map);
        //完成随访量
        String sqlsfW = sqlsf + " AND t.SF_FOLLOW_STATE IN (:state) ";
        completeSfCount = sysDao.getServiceReadDo().findCount(sqlsfW,map);
        totalWork = sysDao.getServiceReadDo().findCount(sqlwork,map);
        //已完成
        String sqlYwcl = sqlwork + " AND PLAN_STATE ='2' ";
        completeWork = sysDao.getServiceReadDo().findCount(sqlYwcl,map);
        //总数
        totalHealth=this.sysDao.getServiceReadDo().findCount(sqlwork2,map);
        //完成数
        completeHealth=this.sysDao.getServiceReadDo().findCount(gsql,map);
        //总拒签数
        refuseSign = this.sysDao.getServiceReadDo().findCount(sqlRefuse,map);
        //转签约数
        gotoSign = this.sysDao.getServiceReadDo().findCount(sqlSign,map);

        rMap.put("manageTotalFollow",totalSfCount);
        rMap.put("manageCompleteFollow",completeSfCount);
        rMap.put("manageTotalWork",totalWork);
        rMap.put("manageCompleteWork",completeWork);
        rMap.put("manageTotalHealth",totalHealth);
        rMap.put("manageCompleteHealth",completeHealth);
        rMap.put("manageRefuseSign",refuseSign);
        rMap.put("manageGoToSign",gotoSign);
        return rMap;
    }

    /**
     * 排名统计
     * @param address
     * @param hosp
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    public Map<String,Object> getRankCount(CdAddress address,AppHospDept hosp,String startDate,String endDate) throws Exception{
        Calendar calendar = Calendar.getInstance();
        int nowYear = calendar.get(Calendar.YEAR);
        int nowMonth = calendar.get(Calendar.MONTH) + 1;
        int now = Integer.parseInt(String.valueOf(nowYear)+ String.valueOf(nowMonth));
        String[] year = endDate.split("-");
        int endYear = Integer.parseInt(year[0]);
        int endMonth = Integer.parseInt(year[1]);
        int end = Integer.parseInt(String.valueOf(endYear)+ String.valueOf(endMonth));
        int totalSfCount = 0;//总随访数
        int completeSfCount = 0;//完成随访数
        int totalHealth = 0;//总健康干预
        int completeHealth = 0;//完成健康干预
        int totalWork = 0;//总工作量
        int completeWork = 0;//完成工作量
        int refuseSign = 0;//拒签
        int gotoSign = 0;//转签


        Map<String,Object> map = new HashMap<String,Object>();
        Map<String,Object> returnMap = new HashMap<String,Object>();
        Map<String,Object> returnSignCountMap = new HashMap<String,Object>();
        map.put("STARTDATE",startDate);
        map.put("ENDDATE",endDate);
        map.put("stratTime",ExtendDate.getFirstDayOfMonth(startDate) + " 00:00:00");
        map.put("endTime",ExtendDate.getLastDayOfMonth(endDate) + " 23:59:59");
//        String sqlDate = " AND t.MANAGE_YEAR_MONTH >= :STARTDATE AND t.MANAGE_YEAR_MONTH <= :ENDDATE ";
        String sql = "SELECT\n" +
                "\tSUM(t.MANAGE_SF_COUNT) manageSfCount,\n" +
                "\tSUM(t.MANAGE_GUIDE_COUNT) manageGuideCount,\n" +
                "\tSUM(t.MANAGE_HD_COUNT) manageHdCount,\n" +
                "\tSUM(t.MANAGE_HELP_COUNT) manageHelpCount,\n" +
                "\tSUM(t.MANAGE_NOT_PAY_COUNT) manageNotPayCount\n" +
                "FROM\n" +
                "\tapp_manage_other_count t where 1=1 ";
        if(hosp != null){
            map.put("HOSP_ID",hosp.getId());
            sql += " AND t.MANAGE_HOSP_ID = :HOSP_ID ";

        }else {
            map.put("AREA_CODE",AreaUtils.getAreaCode(address.getCtcode())+"%");
            //漳州“台商投资区”，“招商局漳州开发区”，“常山农场经济开发区”这三个区要特别判断
            if("350698000000".equals(address.getCtcode())){//台商投资区
                map.put("AREA_CODE","350681102%");
            }else if("350699000000".equals(address.getCtcode())){//招商局漳州开发区
                map.put("AREA_CODE","350681501%");
            }else if("350697000000".equals(address.getCtcode())){//常山农场经济开发区
                map.put("AREA_CODE","350622450%");
            }else if("350681000000".equals(address.getCtcode())){
                map.put("notAreaCode",new String[]{"350681102000","350681501000","350622450000"});
                sql += " AND t.MANAGE_AREA_CODE NOT IN (:notAreaCode) ";
            }else if("350622000000".equals(address.getCtcode())){
                map.put("notAreaCode",new String[]{"350681102000","350681501000","350622450000"});
                sql += " AND t.MANAGE_AREA_CODE NOT IN (:notAreaCode) ";
            }
            sql += " AND t.MANAGE_AREA_CODE LIKE :AREA_CODE ";
        }
        List<Map> maps = this.sysDao.getServiceReadDo().findSqlMap(sql,map);
        if(maps != null && maps.size()>0){
//            if(maps.get(0).get("manageSfCount") != null){
//                sfCount = (int) Double.parseDouble(maps.get(0).get("manageSfCount").toString());//随访统计量
//            }
//            if(maps.get(0).get("manageGuideCount") != null){
//                guideCount = (int) Double.parseDouble(maps.get(0).get("manageGuideCount").toString());//健康指导统计量
//            }
//            if(maps.get(0).get("manageHdCount") != null){
//                hdCount = (int) Double.parseDouble(maps.get(0).get("manageHdCount").toString());//健康教育统计量
//            }
//            if(maps.get(0).get("manageHelpCount") != null){
//                helpCount = (int) Double.parseDouble(maps.get(0).get("manageHelpCount").toString());//求助统计量
//            }
//            if(maps.get(0).get("manageNotPayCount") != null){
//                notPayCount = (int) Double.parseDouble(maps.get(0).get("manageNotPayCount").toString());//未缴费统计量
//            }
        }

        map.put("STARTDATE", ExtendDate.getYMD(Calendar.getInstance())+" 00:00:00");
        map.put("ENDDATE",ExtendDate.getYMD(Calendar.getInstance())+" 23:59:59");
        //查询随访量
        String[] followState = new String[]{FollowPlanState.YIWANCHENG.getValue(),FollowPlanState.SIWANG.getValue(),FollowPlanState.SHIFANG.getValue()};
        map.put("state", followState);
        String sqlSf = " SELECT count(1) FROM APP_FOLLOW_PLAN WHERE 1=1";
        sqlSf +=" AND SF_FOLLOW_DATE>=:STARTDATE AND SF_FOLLOW_DATE <=:ENDDATE";
        sqlSf +=" AND SF_FOLLOW_STATE IN :state";
        //查询健康指导
        String sqlGuide = "SELECT count(1) FROM APP_HEALTH_MEDDLE WHERE 1=1";
        sqlGuide += " AND HM_CREATE_TIME >=:STARTDATE AND HM_CREATE_TIME <=:ENDDATE";
        //健康教育,教育表
        String sqlHD = "SELECT count(1) FROM APP_USER_HEALTHED WHERE 1=1";
        sqlHD +=" AND HED_CREATE_TIME >=:STARTDATE AND HED_CREATE_TIME <=:ENDDATE";
        //求助量,求助表
        String sqlHelp = "SELECT count(1) FROM APP_SEEK_HELP WHERE 1=1";
        sqlHelp += " AND SEEK_HELP_DATE >=:STARTDATE AND SEEK_HELP_DATE <=:ENDDATE";
        //未缴费人数
        String[] signStates = new String[]{SignFormType.YUQY.getValue(),SignFormType.YQY.getValue()};
        map.put("SIGNSTATE", signStates);
        map.put("payState", PayType.WEIJIAOFEI.getValue());
        String sqlWjf = "SELECT count(1) FROM APP_SIGN_FORM t WHERE 1=1 AND t.SIGN_STATE IN (:SIGNSTATE) AND t.SIGN_PAY_STATE = :payState ";
        sqlWjf += " AND t.SIGN_FROM_DATE >=:STARTDATE AND t.SIGN_FROM_DATE <=:ENDDATE";

        if(hosp != null){
            map.put("HOSP_ID",hosp.getId());
            sqlSf += " AND SF_HOS_ID =:HOSP_ID ";
            sqlGuide += " AND HM_HOSP_ID = :HOSP_ID";
            sqlHD +=" AND HED_HOSP_ID = :HOSP_ID";
            sqlHelp += " AND SEEK_HOSP_ID = :HOSP_ID";
            sqlWjf += " AND t.SIGN_HOSP_ID = :HOSP_ID";
        }else {
            map.put("AREA_CODE",AreaUtils.getAreaCode(address.getCtcode())+"%");
            //漳州“台商投资区”，“招商局漳州开发区”，“常山农场经济开发区”这三个区要特别判断
            if("350698000000".equals(address.getCtcode())){//台商投资区
                map.put("AREA_CODE","350681102%");
            }else if("350699000000".equals(address.getCtcode())){//招商局漳州开发区
                map.put("AREA_CODE","350681501%");
            }else if("350697000000".equals(address.getCtcode())){//常山农场经济开发区
                map.put("AREA_CODE","350622450%");
            }else if("350681000000".equals(address.getCtcode())){
                map.put("notAreaCode",new String[]{"350681102000","350681501000","350622450000"});
                sqlSf += " AND SF_HOS_AREA_CODE NOT IN (:notAreaCode) ";
                sqlGuide += " AND HM_AREA_CODE NOT IN (:notAreaCode) ";
                sqlHD += " AND HED_AREA_CODE NOT IN (:notAreaCode) ";
                sqlHelp += " AND SEEK_AREA_CODE NOT IN (:notAreaCode) ";
                sqlWjf += " AND t.SIGN_AREA_CODE NOT IN (:notAreaCode) ";
            }else if("350622000000".equals(address.getCtcode())){
                map.put("notAreaCode",new String[]{"350681102000","350681501000","350622450000"});
                sqlSf += " AND SF_HOS_AREA_CODE NOT IN (:notAreaCode) ";
                sqlGuide += " AND HM_AREA_CODE NOT IN (:notAreaCode) ";
                sqlHD += " AND HED_AREA_CODE NOT IN (:notAreaCode) ";
                sqlHelp += " AND SEEK_AREA_CODE NOT IN (:notAreaCode) ";
                sqlWjf += " AND t.SIGN_AREA_CODE NOT IN (:notAreaCode) ";
            }
            sqlSf +=" AND SF_HOS_AREA_CODE LIKE :AREA_CODE";
            sqlGuide += " AND HM_AREA_CODE LIKE :AREA_CODE";
            sqlHD +=" AND HED_AREA_CODE LIKE :AREA_CODE";
            sqlHelp += " AND SEEK_AREA_CODE LIKE :AREA_CODE";
            sqlWjf += " AND t.SIGN_AREA_CODE LIKE :AREA_CODE";
        }

//        sfCount += sysDao.getServiceDo().findCount(sqlSf,map);
//        guideCount += sysDao.getServiceDo().findCount(sqlGuide,map);
//        hdCount += sysDao.getServiceDo().findCount(sqlHD,map);
//        helpCount += sysDao.getServiceDo().findCount(sqlHelp,map);
//        notPayCount += sysDao.getServiceDo().findCount(sqlWjf,map);

//        returnMap.put("sfCount",sfCount);
//        returnMap.put("guideCount",guideCount);
//        returnMap.put("hDCount",hdCount);
//        returnMap.put("helpCount",helpCount);
//        returnMap.put("wjfCount",notPayCount);
        return returnMap;
    }
}
