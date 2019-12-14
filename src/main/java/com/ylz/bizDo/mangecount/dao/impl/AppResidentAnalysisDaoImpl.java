package com.ylz.bizDo.mangecount.dao.impl;

import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.bizDo.app.po.AppLabelManage;
import com.ylz.bizDo.app.po.AppTeam;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.mangecount.dao.AppResidentAnalysisDao;
import com.ylz.bizDo.mangecount.entity.ManageCountEntity;
import com.ylz.bizDo.mangecount.entity.ManageTeamCountEntity;
import com.ylz.bizDo.mangecount.vo.ResidentVo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.*;
import com.ylz.packcommon.common.util.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * 居民分析
 */
@Service("appResidentAnalysisDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppResidentAnalysisDaoImpl implements AppResidentAnalysisDao {

    @Autowired
    private SysDao sysDao;

    private static Map<String,Map<String,Object>> m1=new HashMap<>();

    /**
     * 缴费情况
     * @param qvo areaId 区域主键 hospId 社区主键 teamId 团队主键
     * @return
     */
    @Override
    public Map findPayStateCount(ResidentVo qvo) throws Exception{
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
//        if(StringUtils.isNotBlank(qvo.getTeamId())){
//            map.put("STARTDATE",qvo.getYearStart());
//            map.put("ENDDATE",qvo.getYearEnd());
//            String sqlDate = " AND t.MANAGE_YEAR_MONTH >= :STARTDATE AND t.MANAGE_YEAR_MONTH <= :ENDDATE ";
//            String sql = "SELECT " +
//                    "SUM(t.MANAGE_UNPAID_COUNT) unPaidCount," +
//                    "SUM(t.MANAGE_ALREADY_PAID_COUNT) aleradyPaidCount " +
//                    " FROM APP_MANAGE_TEAM_COUNT t where 1=1 "+sqlDate;
//            map.put("TEAM_ID",qvo.getTeamId());
//            sql += " AND t.MANAGE_TEAM_ID = :TEAM_ID ";
//            List<Map> maps = this.sysDao.getServiceReadDo().findSqlMap(sql,map);
//            if(maps != null && maps.size() >0){
//                if(maps.get(0).get("unPaidCount") != null)
//                    rmap.put("未缴费人数",(int)Double.parseDouble(maps.get(0).get("unPaidCount").toString()));
//                if(maps.get(0).get("aleradyPaidCount") != null)
//                    rmap.put("已缴费人数",(int)Double.parseDouble(maps.get(0).get("aleradyPaidCount").toString()));
//            }
//            map.put("yearStart", ExtendDate.getFirstDayOfMonth(qvo.getYearStart())+" 00:00:00");
//            map.put("yearEnd",ExtendDate.getLastDayOfMonth(qvo.getYearEnd())+" 23:59:59");
//            map.put("SIGN_STATE", SignFormType.YQY.getValue());
//            String sql = " AND a.SIGN_FROM_DATE >= :yearStart AND a.SIGN_FROM_DATE <= :yearEnd ";
//            //已缴费人数
//            String sqlyjf="SELECT COUNT(1) FROM APP_SIGN_FORM a WHERE a.SIGN_PAY_STATE = '1' and a.SIGN_STATE =:SIGN_STATE "+sql;
//            String sqlwjf="SELECT COUNT(1) FROM APP_SIGN_FORM a WHERE a.SIGN_PAY_STATE = '0' and a.SIGN_STATE =:SIGN_STATE  "+sql;
//            sqlyjf +=" AND a.SIGN_TEAM_ID =:SIGN_TEAM_ID";
//            sqlwjf +=" AND a.SIGN_TEAM_ID =:SIGN_TEAM_ID";
//            map.put("SIGN_TEAM_ID",qvo.getTeamId());
//            int yjf=sysDao.getServiceReadDo().findCount(sqlyjf,map);
//            int wjf=sysDao.getServiceReadDo().findCount(sqlwjf,map);
//            rmap.put("已缴费人数",yjf);
//            rmap.put("未缴费人数",wjf);
//            return rmap;
//        }
        String sqlyjf = null;
        String sqlwjf = null;
        if(end >= now){
            String[] state = {SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};//状态
            map.put("yearStart", ExtendDate.getYMD(Calendar.getInstance())+" 00:00:00");
            map.put("yearEnd",ExtendDate.getYMD(Calendar.getInstance())+" 23:59:59");
            map.put("SIGN_STATE", state);
            String sql = " AND t.SIGN_FROM_DATE >= :yearStart AND t.SIGN_FROM_DATE <= :yearEnd ";
            //已缴费人数
            sqlyjf="SELECT COUNT(1) FROM APP_SIGN_FORM t WHERE t.SIGN_PAY_STATE = '1' and t.SIGN_STATE IN (:SIGN_STATE) "+sql;
            sqlwjf="SELECT COUNT(1) FROM APP_SIGN_FORM t WHERE t.SIGN_PAY_STATE = '0' and t.SIGN_STATE IN (:SIGN_STATE)  "+sql;

            if(StringUtils.isNotBlank(qvo.getTeamId())){
                sqlyjf += " AND t.SIGN_TEAM_ID =:SIGN_TEAM_ID";
                sqlwjf += " AND t.SIGN_TEAM_ID =:SIGN_TEAM_ID";
                map.put("SIGN_TEAM_ID", qvo.getTeamId());
            }
            if(StringUtils.isNotBlank(qvo.getHospId())){
                map.put("SIGN_HOSP_ID",qvo.getHospId());
                sqlyjf += " AND t.SIGN_HOSP_ID = :SIGN_HOSP_ID ";
                sqlwjf += " AND t.SIGN_HOSP_ID = :SIGN_HOSP_ID ";
            }
            if(StringUtils.isNotBlank(qvo.getAreaId())){
                String areaCode = AreaUtils.getAreaCode(qvo.getAreaId());
                map.put("SIGN_AREA_CODE",areaCode+"%");
                sqlyjf += " AND t.SIGN_AREA_CODE LIKE :SIGN_AREA_CODE ";
                sqlwjf += " AND t.SIGN_AREA_CODE LIKE :SIGN_AREA_CODE ";
            }
        }
        map.put("STARTDATE",qvo.getYearStart());
        map.put("ENDDATE",qvo.getYearEnd());
        String sqlDate = " AND t.MANAGE_YEAR_MONTH >= :STARTDATE AND t.MANAGE_YEAR_MONTH <= :ENDDATE ";
        String sql = "SELECT " +
                "SUM(t.MANAGE_UNPAID_COUNT) unPaidCount," +
                "SUM(t.MANAGE_ALREADY_PAID_COUNT) aleradyPaidCount " +
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
            if(maps.get(0).get("unPaidCount") != null) {
                int count =  (int) Double.parseDouble(maps.get(0).get("unPaidCount").toString());
                if(StringUtils.isNotBlank(sqlwjf)){
                    count += sysDao.getServiceReadDo().findCount(sqlwjf,map);
                }
                rmap.put("未缴费人数",count);
            }
            if(maps.get(0).get("aleradyPaidCount") != null) {
                int count =  (int) Double.parseDouble(maps.get(0).get("aleradyPaidCount").toString());
                if(StringUtils.isNotBlank(sqlyjf)){
                    count += sysDao.getServiceReadDo().findCount(sqlyjf,map);
                }
                rmap.put("已缴费人数", count);
            }
        }
        return rmap;
    }

    /**
     * 健康分布
     * @param qvo areaId 区域主键 hospId 社区主键 teamId 团队主键
     * @return
     */
    @Override
    public Map findHealthGroupCount(ResidentVo qvo) throws Exception{
        HashMap rmap = new HashMap();
        //健康分布
        List<AppLabelManage> ls=sysDao.getServiceReadDo().loadByPk(AppLabelManage.class,"labelType","1");
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
//                map.put("STARTDATE",qvo.getYearStart());
//                map.put("ENDDATE",qvo.getYearEnd());
//                String sqlDate = " AND t.MANAGE_YEAR_MONTH >= :STARTDATE AND t.MANAGE_YEAR_MONTH <= :ENDDATE ";
//                String sql = "SELECT " +
//                        "SUM(t.MANAGE_HEALTH_COUNT) manageHealthCount," +
//                        "SUM(t.MANAGE_BEILL_COUNT) manageBeillCount," +
//                        "SUM(t.MANAGE_HIGH_RISK_COUNT) manageHighRiskCount," +
//                        "SUM(t.MANAGE_CONVALESCENCE_COUNT) manageConvalescenceCount," +
//                        "SUM(t.MANAGE_HEALTH_UNKNOWN_COUNT) manageHealthUnknownCount " +
//                        " FROM APP_MANAGE_TEAM_COUNT t where 1=1 "+sqlDate;
//                map.put("TEAM_ID",qvo.getTeamId());
//                sql += " AND t.MANAGE_TEAM_ID = :TEAM_ID ";
//                List<Map> maps = this.sysDao.getServiceReadDo().findSqlMap(sql,map);
//                if(maps != null && maps.size() >0){
//                    for(AppLabelManage l:ls) {
//                        if(l.getLabelValue().equals("101")){//患病人群
//                            if(maps.get(0).get("manageBeillCount") != null)
//                                rmap.put(l.getLabelTitle(),(int)Double.parseDouble(maps.get(0).get("manageBeillCount").toString()));
//                        }else if(l.getLabelValue().equals("102")){//健康人群
//                            if(maps.get(0).get("manageHealthCount") != null)
//                                rmap.put(l.getLabelTitle(),(int)Double.parseDouble(maps.get(0).get("manageHealthCount").toString()));
//                        }else if(l.getLabelValue().equals("103")){//高危人群
//                            if(maps.get(0).get("manageHighRiskCount") != null)
//                                rmap.put(l.getLabelTitle(),(int)Double.parseDouble(maps.get(0).get("manageHighRiskCount").toString()));
//                        }else if(l.getLabelValue().equals("104")){//恢复期人群
//                            if(maps.get(0).get("manageConvalescenceCount") != null)
//                                rmap.put(l.getLabelTitle(),(int)Double.parseDouble(maps.get(0).get("manageConvalescenceCount").toString()));
//                        }else if(l.getLabelValue().equals("199")){//未知
//                            if(maps.get(0).get("manageHealthUnknownCount") != null)
//                                rmap.put(l.getLabelTitle(),(int)Double.parseDouble(maps.get(0).get("manageHealthUnknownCount").toString()));
//                        }
//                    }
//                }
//                map.put("yearStart", ExtendDate.getFirstDayOfMonth(qvo.getYearStart())+" 00:00:00");
//                map.put("yearEnd",ExtendDate.getLastDayOfMonth(qvo.getYearEnd())+" 23:59:59");
//                String sql = "SELECT COUNT(1) FROM APP_SIGN_FORM a WHERE a.SIGN_STATE='2'   ";
//                sql += "AND a.SIGN_FROM_DATE >= :yearStart AND a.SIGN_FROM_DATE <= :yearEnd ";
//                sql += " AND a.SIGN_TEAM_ID =:SIGN_TEAM_ID";
//                map.put("SIGN_TEAM_ID", qvo.getTeamId());
//                sql += " AND a.SIGN_HEALTH_GROUP=:SIGN_HEALTH_GROUP";
//                for(AppLabelManage l:ls) {
//                    map.put("SIGN_HEALTH_GROUP",l.getLabelValue());
//                    int  count= sysDao.getServiceReadDo().findCount(sql, map);
//                    rmap.put(l.getLabelTitle(), count);
//                }
//                return rmap;
//            }
            String sqlNow = null;
            if(end >= now){
                String[] state = {SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};//状态
                map.put("yearStart", ExtendDate.getYMD(Calendar.getInstance())+" 00:00:00");
                map.put("yearEnd",ExtendDate.getYMD(Calendar.getInstance())+" 23:59:59");
                map.put("SIGN_STATE", state);
                String sql = " AND t.SIGN_FROM_DATE >= :yearStart AND t.SIGN_FROM_DATE <= :yearEnd ";
                sqlNow = "SELECT COUNT(1) FROM APP_SIGN_FORM t WHERE t.SIGN_PAY_STATE = '1' and t.SIGN_STATE IN (:SIGN_STATE) "+sql;
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
            map.put("STARTDATE",qvo.getYearStart());
            map.put("ENDDATE",qvo.getYearEnd());
            String sqlDate = " AND t.MANAGE_YEAR_MONTH >= :STARTDATE AND t.MANAGE_YEAR_MONTH <= :ENDDATE ";
            String sql = "SELECT " +
                    "SUM(t.MANAGE_HEALTH_COUNT) manageHealthCount," +
                    "SUM(t.MANAGE_BEILL_COUNT) manageBeillCount," +
                    "SUM(t.MANAGE_HIGH_RISK_COUNT) manageHighRiskCount," +
                    "SUM(t.MANAGE_CONVALESCENCE_COUNT) manageConvalescenceCount," +
                    "SUM(t.MANAGE_HEALTH_UNKNOWN_COUNT) manageHealthUnknownCount " +
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
                    if(l.getLabelValue().equals("101")){//患病人群
                        if(maps.get(0).get("manageBeillCount") != null){
                            int count = (int)Double.parseDouble(maps.get(0).get("manageBeillCount").toString());
                            if(StringUtils.isNotBlank(sqlNow)) {
                              count += this.sysDao.getServiceReadDo().findCount(sqlNow,map);
                            }
                            rmap.put(l.getLabelTitle(),count);
                        }
                    }else if(l.getLabelValue().equals("102")){//健康人群
                        if(maps.get(0).get("manageHealthCount") != null){
                            int count = (int)Double.parseDouble(maps.get(0).get("manageHealthCount").toString());
                            if(StringUtils.isNotBlank(sqlNow)) {
                                count += this.sysDao.getServiceReadDo().findCount(sqlNow,map);
                            }
                            rmap.put(l.getLabelTitle(),count);
                        }
                    }else if(l.getLabelValue().equals("103")){//高危人群
                        if(maps.get(0).get("manageHighRiskCount") != null){
                            int count = (int)Double.parseDouble(maps.get(0).get("manageHighRiskCount").toString());
                            if(StringUtils.isNotBlank(sqlNow)) {
                                count += this.sysDao.getServiceReadDo().findCount(sqlNow,map);
                            }
                            rmap.put(l.getLabelTitle(),count);
                        }
                    }else if(l.getLabelValue().equals("104")){//恢复期人群
                        if(maps.get(0).get("manageConvalescenceCount") != null){
                            int count = (int)Double.parseDouble(maps.get(0).get("manageConvalescenceCount").toString());
                            if(StringUtils.isNotBlank(sqlNow)) {
                                count += this.sysDao.getServiceReadDo().findCount(sqlNow,map);
                            }
                            rmap.put(l.getLabelTitle(),count);
                        }
                    }else if(l.getLabelValue().equals("199")){//未知
                        if(maps.get(0).get("manageHealthUnknownCount") != null){
                            if(maps.get(0).get("manageConvalescenceCount") != null){
                                int count = (int)Double.parseDouble(maps.get(0).get("manageHealthUnknownCount").toString());
                                if(StringUtils.isNotBlank(sqlNow)) {
                                    count += this.sysDao.getServiceReadDo().findCount(sqlNow,map);
                                }
                                rmap.put(l.getLabelTitle(),count);
                            }
                        }
                    }
                }
            }
            return rmap;
        }

        return rmap;

    }

    /**
     * 服务分布
     * @param qvo areaId 区域主键 hospId 社区主键 teamId 团队主键
     * @return
     */
    @Override
    public Map findPersGroupCount(ResidentVo qvo) throws Exception{
        HashMap rmap = new HashMap();
        //List<CdCode> ls = sysDao.getCodeDao().findGroupList(CodeGroupConstrats.GROUP_RESIDENTMANGE, CommonEnable.QIYONG.getValue());
        List<AppLabelManage> ls = this.sysDao.getAppLabelManageDao().findByType("3");
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
//                map.put("STARTDATE",qvo.getYearStart());
//                map.put("ENDDATE",qvo.getYearEnd());
//                String sqlDate = " AND t.MANAGE_YEAR_MONTH >= :STARTDATE AND t.MANAGE_YEAR_MONTH <= :ENDDATE ";
//                String sql = "SELECT " +
//                        "SUM(t.MANAGE_PLAIN_COUNT) managePlainCount," +
//                        "SUM(t.MANAGE_CHILD_COUNT) manageChildCount," +
//                        "SUM(t.MANAGE_MATERNAL_COUNT) manageMaternalCount," +
//                        "SUM(t.MANAGE_OLD_PEOPLE_COUNT) manageOldPeopleCount," +
//                        "SUM(t.MANAGE_BLOOD_COUNT) manageBloodCount," +
//                        "SUM(t.MANAGE_DIABETES_COUNT) manageDiabetesCount," +
//                        "SUM(t.MANAGE_PSYCHOSIS_COUNT) managePsychosisCount," +
//                        "SUM(t.MANAGE_PHTHISIS_COUNT) managePhthisisCount," +
//                        "SUM(t.MANAGE_DISABLED_PEOPLE_COUNT) manageDisabledPeopleCount," +
//                        "SUM(t.MANAGE_SERVICE_UNKNOWN_COUNT) manageServiceUnknownCount " +
//                        " FROM APP_MANAGE_TEAM_COUNT t where 1=1 "+sqlDate;
//                map.put("TEAM_ID",qvo.getTeamId());
//                sql += " AND t.MANAGE_TEAM_ID = :TEAM_ID ";
//                List<Map> maps = this.sysDao.getServiceReadDo().findSqlMap(sql,map);
//                if(maps != null && maps.size() >0){
//                    for(AppLabelManage l:ls) {
//                        if(l.getLabelValue().equals("1")){//普通人群
//                            if(maps.get(0).get("managePlainCount") != null)
//                                rmap.put(l.getLabelTitle(),(int)Double.parseDouble(maps.get(0).get("managePlainCount").toString()));
//                        }else if(l.getLabelValue().equals("2")){//儿童(0-6岁)
//                            if(maps.get(0).get("manageChildCount") != null)
//                                rmap.put(l.getLabelTitle(),(int)Double.parseDouble(maps.get(0).get("manageChildCount").toString()));
//                        }else if(l.getLabelValue().equals("3")){//孕产妇
//                            if(maps.get(0).get("manageMaternalCount") != null)
//                                rmap.put(l.getLabelTitle(),(int)Double.parseDouble(maps.get(0).get("manageMaternalCount").toString()));
//                        }else if(l.getLabelValue().equals("4")){//老年人
//                            if(maps.get(0).get("manageOldPeopleCount") != null)
//                                rmap.put(l.getLabelTitle(),(int)Double.parseDouble(maps.get(0).get("manageOldPeopleCount").toString()));
//                        }else if(l.getLabelValue().equals("5")){//高血压
//                            if(maps.get(0).get("manageBloodCount") != null)
//                                rmap.put(l.getLabelTitle(),(int)Double.parseDouble(maps.get(0).get("manageBloodCount").toString()));
//                        }else if(l.getLabelValue().equals("6")){//糖尿病
//                            if(maps.get(0).get("manageDiabetesCount") != null)
//                                rmap.put(l.getLabelTitle(),(int)Double.parseDouble(maps.get(0).get("manageDiabetesCount").toString()));
//                        }else if(l.getLabelValue().equals("7")){//严重精神障碍
//                            if(maps.get(0).get("managePsychosisCount") != null)
//                                rmap.put(l.getLabelTitle(),(int)Double.parseDouble(maps.get(0).get("managePsychosisCount").toString()));
//                        }else if(l.getLabelValue().equals("8")){//结核病
//                            if(maps.get(0).get("managePhthisisCount") != null)
//                                rmap.put(l.getLabelTitle(),(int)Double.parseDouble(maps.get(0).get("managePhthisisCount").toString()));
//                        }else if(l.getLabelValue().equals("9")){//残疾人
//                            if(maps.get(0).get("manageDisabledPeopleCount") != null)
//                                rmap.put(l.getLabelTitle(),(int)Double.parseDouble(maps.get(0).get("manageDisabledPeopleCount").toString()));
//                        }else if(l.getLabelValue().equals("99")){//未知
//                            if(maps.get(0).get("manageServiceUnknownCount") != null)
//                                rmap.put(l.getLabelTitle(),(int)Double.parseDouble(maps.get(0).get("manageServiceUnknownCount").toString()));
//                        }
//                    }
//                }
//                map.put("yearStart", ExtendDate.getFirstDayOfMonth(qvo.getYearStart())+" 00:00:00");
//                map.put("yearEnd",ExtendDate.getLastDayOfMonth(qvo.getYearEnd())+" 23:59:59");
//                String sql = "SELECT COUNT(1) FROM APP_SIGN_FORM a LEFT JOIN APP_LABEL_GROUP b on a.ID=b.LABEL_SIGN_ID WHERE a.SIGN_STATE='2' ";
//                sql += " AND a.SIGN_FROM_DATE >= :yearStart AND a.SIGN_FROM_DATE <= :yearEnd ";
//                sql += " AND a.SIGN_TEAM_ID =:SIGN_TEAM_ID";
//                map.put("SIGN_TEAM_ID", qvo.getTeamId());
//                sql += " AND b.LABEL_VALUE=:SIGN_PERS_GROUP";
//                for(AppLabelManage l:ls) {
//                    map.put("SIGN_PERS_GROUP",l.getLabelValue());
//                    int  count= sysDao.getServiceReadDo().findCount(sql, map);
//                    rmap.put(l.getLabelTitle(), count);
//                }
//                return rmap;
//            }

            String sqlNow = null;
            if(end >= now){
                String[] state = {SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};//状态
                map.put("yearStart", ExtendDate.getYMD(Calendar.getInstance())+" 00:00:00");
                map.put("yearEnd",ExtendDate.getYMD(Calendar.getInstance())+" 23:59:59");
                map.put("SIGN_STATE", state);
                String sql = " AND t.SIGN_FROM_DATE >= :yearStart AND t.SIGN_FROM_DATE <= :yearEnd ";
                sqlNow = "SELECT COUNT(1) FROM APP_SIGN_FORM t LEFT JOIN APP_LABEL_GROUP b on t.ID=b.LABEL_SIGN_ID WHERE t.SIGN_STATE IN (:SIGN_STATE) "+sql;
                sqlNow += " AND b.LABEL_VALUE = :SIGN_PERS_GROUP ";
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
            map.put("STARTDATE",qvo.getYearStart());
            map.put("ENDDATE",qvo.getYearEnd());
            String sqlDate = " AND t.MANAGE_YEAR_MONTH >= :STARTDATE AND t.MANAGE_YEAR_MONTH <= :ENDDATE ";
            String sql = "SELECT " +
                    "SUM(t.MANAGE_PLAIN_COUNT) managePlainCount," +
                    "SUM(t.MANAGE_CHILD_COUNT) manageChildCount," +
                    "SUM(t.MANAGE_MATERNAL_COUNT) manageMaternalCount," +
                    "SUM(t.MANAGE_OLD_PEOPLE_COUNT) manageOldPeopleCount," +
                    "SUM(t.MANAGE_BLOOD_COUNT) manageBloodCount," +
                    "SUM(t.MANAGE_DIABETES_COUNT) manageDiabetesCount," +
                    "SUM(t.MANAGE_PSYCHOSIS_COUNT) managePsychosisCount," +
                    "SUM(t.MANAGE_PHTHISIS_COUNT) managePhthisisCount," +
                    "SUM(t.MANAGE_DISABLED_PEOPLE_COUNT) manageDisabledPeopleCount," +
                    "SUM(t.MANAGE_SERVICE_UNKNOWN_COUNT) manageServiceUnknownCount " +
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
                    if(l.getLabelValue().equals("1")){//普通人群
                        if(maps.get(0).get("managePlainCount") != null){
                            int count = (int)Double.parseDouble(maps.get(0).get("managePlainCount").toString());
                            if(StringUtils.isNotBlank(sqlNow)){
                                map.put("SIGN_PERS_GROUP",l.getLabelValue());
                                count += sysDao.getServiceReadDo().findCount(sqlNow, map);
                            }
                            rmap.put(l.getLabelTitle(),count);
                        }

                    }else if(l.getLabelValue().equals("2")){//儿童(0-6岁)
                        if(maps.get(0).get("manageChildCount") != null){
                            int count = (int)Double.parseDouble(maps.get(0).get("manageChildCount").toString());
                            if(StringUtils.isNotBlank(sqlNow)){
                                map.put("SIGN_PERS_GROUP",l.getLabelValue());
                                count += sysDao.getServiceReadDo().findCount(sqlNow, map);
                            }
                            rmap.put(l.getLabelTitle(),count);
                        }
                    }else if(l.getLabelValue().equals("3")){//孕产妇
                        if(maps.get(0).get("manageMaternalCount") != null){
                            int count = (int)Double.parseDouble(maps.get(0).get("manageMaternalCount").toString());
                            if(StringUtils.isNotBlank(sqlNow)){
                                map.put("SIGN_PERS_GROUP",l.getLabelValue());
                                count += sysDao.getServiceReadDo().findCount(sqlNow, map);
                            }
                            rmap.put(l.getLabelTitle(),count);
                        }
                    }else if(l.getLabelValue().equals("4")){//老年人
                        if(maps.get(0).get("manageOldPeopleCount") != null){
                            int count = (int)Double.parseDouble(maps.get(0).get("manageOldPeopleCount").toString());
                            if(StringUtils.isNotBlank(sqlNow)){
                                map.put("SIGN_PERS_GROUP",l.getLabelValue());
                                count += sysDao.getServiceReadDo().findCount(sqlNow, map);
                            }
                            rmap.put(l.getLabelTitle(),count);
                        }
                    }else if(l.getLabelValue().equals("5")){//高血压
                        if(maps.get(0).get("manageBloodCount") != null){
                            int count = (int)Double.parseDouble(maps.get(0).get("manageBloodCount").toString());
                            if(StringUtils.isNotBlank(sqlNow)){
                                map.put("SIGN_PERS_GROUP",l.getLabelValue());
                                count += sysDao.getServiceReadDo().findCount(sqlNow, map);
                            }
                            rmap.put(l.getLabelTitle(),count);
                        }
                    }else if(l.getLabelValue().equals("6")){//糖尿病
                        if(maps.get(0).get("manageDiabetesCount") != null){
                            int count = (int)Double.parseDouble(maps.get(0).get("manageDiabetesCount").toString());
                            if(StringUtils.isNotBlank(sqlNow)){
                                map.put("SIGN_PERS_GROUP",l.getLabelValue());
                                count += sysDao.getServiceReadDo().findCount(sqlNow, map);
                            }
                            rmap.put(l.getLabelTitle(),count);
                        }
                    }else if(l.getLabelValue().equals("7")){//严重精神障碍
                        if(maps.get(0).get("managePsychosisCount") != null){
                            int count = (int)Double.parseDouble(maps.get(0).get("managePsychosisCount").toString());
                            if(StringUtils.isNotBlank(sqlNow)){
                                map.put("SIGN_PERS_GROUP",l.getLabelValue());
                                count += sysDao.getServiceReadDo().findCount(sqlNow, map);
                            }
                            rmap.put(l.getLabelTitle(),count);
                        }
                    }else if(l.getLabelValue().equals("8")){//结核病
                        if(maps.get(0).get("managePhthisisCount") != null){
                            int count = (int)Double.parseDouble(maps.get(0).get("managePhthisisCount").toString());
                            if(StringUtils.isNotBlank(sqlNow)){
                                map.put("SIGN_PERS_GROUP",l.getLabelValue());
                                count += sysDao.getServiceReadDo().findCount(sqlNow, map);
                            }
                            rmap.put(l.getLabelTitle(),count);
                        }
                    }else if(l.getLabelValue().equals("9")){//残疾人
                        if(maps.get(0).get("manageDisabledPeopleCount") != null){
                            int count = (int)Double.parseDouble(maps.get(0).get("manageDisabledPeopleCount").toString());
                            if(StringUtils.isNotBlank(sqlNow)){
                                map.put("SIGN_PERS_GROUP",l.getLabelValue());
                                count += sysDao.getServiceReadDo().findCount(sqlNow, map);
                            }
                            rmap.put(l.getLabelTitle(),count);
                        }
                    }else if(l.getLabelValue().equals("99")){//未知
                        if(maps.get(0).get("manageServiceUnknownCount") != null){
                            int count = (int)Double.parseDouble(maps.get(0).get("manageServiceUnknownCount").toString());
                            if(StringUtils.isNotBlank(sqlNow)){
                                map.put("SIGN_PERS_GROUP",l.getLabelValue());
                                count += sysDao.getServiceReadDo().findCount(sqlNow, map);
                            }
                            rmap.put(l.getLabelTitle(),count);
                        }
                    }
                }
            }
            return rmap;
        }
        return rmap;
    }


    /**
     * 服务分布重点人群
     * @param qvo areaId 区域主键 hospId 社区主键 teamId 团队主键
     * @return
     */
    @Override
    public String findPersGroupCountFocusGroups(ResidentVo qvo) throws Exception{
        HashMap rmap = new HashMap();
        List<ManageCountEntity> lsEntity = new ArrayList<ManageCountEntity>();
        //List<CdCode> ls = sysDao.getCodeDao().findGroupList(CodeGroupConstrats.GROUP_RESIDENTMANGE, CommonEnable.QIYONG.getValue());
        List<AppLabelManage> ls = this.sysDao.getAppLabelManageDao().findByType("3");
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
//                String sql = "SELECT COUNT(1) FROM APP_SIGN_FORM a LEFT JOIN APP_LABEL_GROUP b on a.ID=b.LABEL_SIGN_ID WHERE a.SIGN_STATE='2' ";
//                sql += " AND a.SIGN_FROM_DATE >= :yearStart AND a.SIGN_FROM_DATE <= :yearEnd ";
//                sql += " AND a.SIGN_TEAM_ID =:SIGN_TEAM_ID";
//                map.put("SIGN_TEAM_ID", qvo.getTeamId());
//                sql += " AND b.LABEL_VALUE=:SIGN_PERS_GROUP";
//                for(AppLabelManage l:ls) {
//                    ManageCountEntity v = new ManageCountEntity();
//                    if(l.getLabelValue().equals("1")){//普通人群
//                    }else if(l.getLabelValue().equals("2")){//儿童(0-6岁)
//                        map.put("SIGN_PERS_GROUP",l.getLabelValue());
//                        int  count= sysDao.getServiceReadDo().findCount(sql, map);
//                        v.setTitle("manageChildCount");
//                        v.setValue(String.valueOf(count));
//                        lsEntity.add(v);
//                    }else if(l.getLabelValue().equals("3")){//孕产妇
//                        map.put("SIGN_PERS_GROUP",l.getLabelValue());
//                        int  count= sysDao.getServiceReadDo().findCount(sql, map);
//                        v.setTitle("manageMaternalCount");
//                        v.setValue(String.valueOf(count));
//                        lsEntity.add(v);
//                    }else if(l.getLabelValue().equals("4")){//老年人
//                        map.put("SIGN_PERS_GROUP",l.getLabelValue());
//                        int  count= sysDao.getServiceReadDo().findCount(sql, map);
//                        v.setTitle("manageOldPeopleCount");
//                        v.setValue(String.valueOf(count));
//                        lsEntity.add(v);
//                    }else if(l.getLabelValue().equals("5")){//高血压
//                        map.put("SIGN_PERS_GROUP",l.getLabelValue());
//                        int  count= sysDao.getServiceReadDo().findCount(sql, map);
//                        v.setTitle("manageBloodCount");
//                        v.setValue(String.valueOf(count));
//                        lsEntity.add(v);
//                    }else if(l.getLabelValue().equals("6")){//糖尿病
//                        map.put("SIGN_PERS_GROUP",l.getLabelValue());
//                        int  count= sysDao.getServiceReadDo().findCount(sql, map);
//                        v.setTitle("manageDiabetesCount");
//                        v.setValue(String.valueOf(count));
//                        lsEntity.add(v);
//                    }else if(l.getLabelValue().equals("7")){//严重精神障碍
//                        map.put("SIGN_PERS_GROUP",l.getLabelValue());
//                        int  count= sysDao.getServiceReadDo().findCount(sql, map);
//                        v.setTitle("managePsychosisCount");
//                        v.setValue(String.valueOf(count));
//                        lsEntity.add(v);
//                    }else if(l.getLabelValue().equals("8")){//结核病
//                        map.put("SIGN_PERS_GROUP",l.getLabelValue());
//                        int  count= sysDao.getServiceReadDo().findCount(sql, map);
//                        v.setTitle("managePhthisisCount");
//                        v.setValue(String.valueOf(count));
//                        lsEntity.add(v);
//                    }else if(l.getLabelValue().equals("9")){//残疾人
//                        map.put("SIGN_PERS_GROUP",l.getLabelValue());
//                        int  count= sysDao.getServiceReadDo().findCount(sql, map);
//                        v.setTitle("manageDisabledPeopleCount");
//                        v.setValue(String.valueOf(count));
//                        lsEntity.add(v);
//                    }else if(l.getLabelValue().equals("99")){//未知
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
                    sqlNow = "SELECT COUNT(1) FROM APP_SIGN_FORM t LEFT JOIN APP_LABEL_GROUP b on t.ID=b.LABEL_SIGN_ID WHERE t.SIGN_STATE IN (:SIGN_STATE) ";
                    sqlNow += " AND t.SIGN_FROM_DATE >= :yearStart AND t.SIGN_FROM_DATE <= :yearEnd ";
                    sqlNow += " AND b.LABEL_VALUE=:SIGN_PERS_GROUP";
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
                        //漳州“台商投资区”，“招商局漳州开发区”，“常山农场经济开发区”这三个区要特别判断
                        if("350698000000".equals(qvo.getAreaId())){//台商投资区
                            map.put("SIGN_AREA_CODE","350681102%");
                        }else if("350699000000".equals(qvo.getAreaId())){//招商局漳州开发区
                            map.put("SIGN_AREA_CODE","350681501%");
                        }else if("350697000000".equals(qvo.getAreaId())){//常山农场经济开发区
                            map.put("SIGN_AREA_CODE","350622450%");
                        }else if("350681000000".equals(qvo.getAreaId())){
                            map.put("notAreaCode",new String[]{"350681102000","350681501000","350622450000"});
                            sqlNow += " AND t.SIGN_AREA_CODE NOT IN (:notAreaCode) ";
                        }else if("350622000000".equals(qvo.getAreaId())){
                            map.put("notAreaCode",new String[]{"350681102000","350681501000","350622450000"});
                            sqlNow += " AND t.SIGN_AREA_CODE NOT IN (:notAreaCode) ";
                        }
                        sqlNow += " AND t.SIGN_AREA_CODE LIKE :SIGN_AREA_CODE ";
                    }
                }else{
                    sqlTeam = "SELECT aa.ID FROM APP_TEAM aa WHERE aa.TEAM_DEL_STATE = '1' " +
                            "AND aa.TEAM_DEL_TIME >=:stratTime\n" +
                            "AND aa.TEAM_DEL_TIME <=:endTime ";
                }
                map.put("STARTDATE",qvo.getYearStart());
                map.put("ENDDATE",qvo.getYearEnd());
                map.put("startTime",ExtendDate.getFirstDayOfMonth(qvo.getYearStart()) + " 00:00:00");
                map.put("endTime",ExtendDate.getLastDayOfMonth(qvo.getYearEnd()) + " 23:59:59");
                String sqlDate = " AND t.MANAGE_YEAR_MONTH >= :STARTDATE AND t.MANAGE_YEAR_MONTH <= :ENDDATE ";
                String sql = "SELECT " +
                        "SUM(t.MANAGE_PLAIN_COUNT) managePlainCount," +
                        "SUM(t.MANAGE_CHILD_COUNT) manageChildCount," +
                        "SUM(t.MANAGE_MATERNAL_COUNT) manageMaternalCount," +
                        "SUM(t.MANAGE_OLD_PEOPLE_COUNT) manageOldPeopleCount," +
                        "SUM(t.MANAGE_BLOOD_COUNT) manageBloodCount," +
                        "SUM(t.MANAGE_DIABETES_COUNT) manageDiabetesCount," +
                        "SUM(t.MANAGE_PSYCHOSIS_COUNT) managePsychosisCount," +
                        "SUM(t.MANAGE_PHTHISIS_COUNT) managePhthisisCount," +
                        "SUM(t.MANAGE_DISABLED_PEOPLE_COUNT) manageDisabledPeopleCount," +
                        "SUM(t.MANAGE_SERVICE_UNKNOWN_COUNT) manageServiceUnknownCount," +
                        "SUM(t.MANAGE_NXG_COUNT) manageNxgCount," +
                        "SUM(t.MANAGE_GXB_COUNT) manageGxbCount," +
                        "SUM(t.MANAGE_AZ_COUNT) manageAzCount  " +
                        " FROM APP_MANAGE_COUNT t where 1=1 "+sqlDate;
                if(StringUtils.isNotBlank(qvo.getTeamId())){
                    map.put("TEAM_ID",qvo.getTeamId());
                    sql += " AND t.MANAGE_TEAM_ID = :TEAM_ID ";
                }
                if(StringUtils.isNotBlank(qvo.getHospId())){
                    map.put("HOSP_ID",qvo.getHospId());
                    sql += " AND t.MANAGE_HOSP_ID = :HOSP_ID ";
                    sqlTeam += " AND aa.TEAM_HOSP_ID =:HOSP_ID ";
                }
                if(StringUtils.isNotBlank(qvo.getAreaId())){
                    String areaCode = AreaUtils.getAreaCode(qvo.getAreaId());
                    map.put("AREA_CODE",areaCode+"%");
                    sql += " AND t.MANAGE_AREA_CODE LIKE :AREA_CODE ";
                }

                //去除已删团队的签约统计数
                sql += " AND t.MANAGE_TEAM_ID NOT IN ("+sqlTeam+") ";

                List<Map> maps = this.sysDao.getServiceReadDo().findSqlMap(sql,map);
                if(maps != null && maps.size() >0){
                    for(AppLabelManage l:ls) {
                        ManageCountEntity v = new ManageCountEntity();
                        if(l.getLabelValue().equals("1")){//普通人群
                            if(maps.get(0).get("managePlainCount") != null){
                                v.setTitle("managePlainCount");
                                v.setName(l.getLabelTitle());
                                int count = (int)Double.parseDouble(maps.get(0).get("managePlainCount").toString());
                                if(StringUtils.isNotBlank(sqlNow)){
                                    map.put("SIGN_PERS_GROUP",l.getLabelValue());
                                    count += sysDao.getServiceReadDo().findCount(sqlNow, map);
                                }
                                v.setValue(String.valueOf(count));
                                lsEntity.add(v);
                            }
                        }else if(l.getLabelValue().equals("2")){//儿童(0-6岁)
                            if(maps.get(0).get("manageChildCount") != null){
                                v.setTitle("manageChildCount");
                                v.setName(l.getLabelTitle());
                                int count = (int)Double.parseDouble(maps.get(0).get("manageChildCount").toString());
                                if(StringUtils.isNotBlank(sqlNow)){
                                    map.put("SIGN_PERS_GROUP",l.getLabelValue());
                                    count += sysDao.getServiceReadDo().findCount(sqlNow, map);
                                }
                                v.setValue(String.valueOf(count));
                                lsEntity.add(v);
                            }
                        }else if(l.getLabelValue().equals("3")){//孕产妇
                            if(maps.get(0).get("manageMaternalCount") != null){
                                v.setTitle("manageMaternalCount");
                                v.setName(l.getLabelTitle());
                                int count = (int)Double.parseDouble(maps.get(0).get("manageMaternalCount").toString());
                                if(StringUtils.isNotBlank(sqlNow)){
                                    map.put("SIGN_PERS_GROUP",l.getLabelValue());
                                    count += sysDao.getServiceReadDo().findCount(sqlNow, map);
                                }
                                v.setValue(String.valueOf(count));
                                lsEntity.add(v);
                            }
                        }else if(l.getLabelValue().equals("4")){//老年人
                            if(maps.get(0).get("manageOldPeopleCount") != null){
                                v.setTitle("manageOldPeopleCount");
                                v.setName(l.getLabelTitle());
                                int count = (int)Double.parseDouble(maps.get(0).get("manageOldPeopleCount").toString());
                                if(StringUtils.isNotBlank(sqlNow)){
                                    map.put("SIGN_PERS_GROUP",l.getLabelValue());
                                    count += sysDao.getServiceReadDo().findCount(sqlNow, map);
                                }
                                v.setValue(String.valueOf(count));
                                lsEntity.add(v);
                            }
                        }else if(l.getLabelValue().equals("5")){//高血压
                            if(maps.get(0).get("manageBloodCount") != null){
                                v.setTitle("manageBloodCount");
                                v.setName(l.getLabelTitle());
                                int count = (int)Double.parseDouble(maps.get(0).get("manageBloodCount").toString());
                                if(StringUtils.isNotBlank(sqlNow)){
                                    map.put("SIGN_PERS_GROUP",l.getLabelValue());
                                    count += sysDao.getServiceReadDo().findCount(sqlNow, map);
                                }
                                v.setValue(String.valueOf(count));
                                lsEntity.add(v);
                            }
                        }else if(l.getLabelValue().equals("6")){//糖尿病
                            if(maps.get(0).get("manageDiabetesCount") != null){
                                v.setTitle("manageDiabetesCount");
                                v.setName(l.getLabelTitle());
                                int count = (int)Double.parseDouble(maps.get(0).get("manageDiabetesCount").toString());
                                if(StringUtils.isNotBlank(sqlNow)){
                                    map.put("SIGN_PERS_GROUP",l.getLabelValue());
                                    count += sysDao.getServiceReadDo().findCount(sqlNow, map);
                                }
                                v.setValue(String.valueOf(count));
                                lsEntity.add(v);
                            }
                        }else if(l.getLabelValue().equals("7")){//严重精神障碍
                            if(maps.get(0).get("managePsychosisCount") != null){
                                v.setTitle("managePsychosisCount");
                                v.setName(l.getLabelTitle());
                                int count = (int)Double.parseDouble(maps.get(0).get("managePsychosisCount").toString());
                                if(StringUtils.isNotBlank(sqlNow)){
                                    map.put("SIGN_PERS_GROUP",l.getLabelValue());
                                    count += sysDao.getServiceReadDo().findCount(sqlNow, map);
                                }
                                v.setValue(String.valueOf(count));
                                lsEntity.add(v);
                            }
                        }else if(l.getLabelValue().equals("8")){//结核病
                            if(maps.get(0).get("managePhthisisCount") != null){
                                v.setTitle("managePhthisisCount");
                                v.setName(l.getLabelTitle());
                                int count = (int)Double.parseDouble(maps.get(0).get("managePhthisisCount").toString());
                                if(StringUtils.isNotBlank(sqlNow)){
                                    map.put("SIGN_PERS_GROUP",l.getLabelValue());
                                    count += sysDao.getServiceReadDo().findCount(sqlNow, map);
                                }
                                v.setValue(String.valueOf(count));
                                lsEntity.add(v);
                            }
                        }else if(l.getLabelValue().equals("9")){//残疾人
                            if(maps.get(0).get("manageDisabledPeopleCount") != null){
                                v.setTitle("manageDisabledPeopleCount");
                                v.setName(l.getLabelTitle());
                                int count = (int)Double.parseDouble(maps.get(0).get("manageDisabledPeopleCount").toString());
                                if(StringUtils.isNotBlank(sqlNow)){
                                    map.put("SIGN_PERS_GROUP",l.getLabelValue());
                                    count += sysDao.getServiceReadDo().findCount(sqlNow, map);
                                }
                                v.setValue(String.valueOf(count));
                                lsEntity.add(v);
                            }
                        }else if(l.getLabelValue().equals("10")){//脑血管
                            if(maps.get(0).get("manageNxgCount") != null){
                                v.setTitle("manageNxgCount");
                                v.setName(l.getLabelTitle());
                                int count = (int)Double.parseDouble(maps.get(0).get("manageNxgCount").toString());
                                if(StringUtils.isNotBlank(sqlNow)){
                                    map.put("SIGN_PERS_GROUP",l.getLabelValue());
                                    count += sysDao.getServiceReadDo().findCount(sqlNow, map);
                                }
                                v.setValue(String.valueOf(count));
                                lsEntity.add(v);
                            }
                        }else if(l.getLabelValue().equals("11")){//冠心病
                            if(maps.get(0).get("manageGxbCount") != null){
                                v.setTitle("manageGxbCount");
                                v.setName(l.getLabelTitle());
                                int count = (int)Double.parseDouble(maps.get(0).get("manageGxbCount").toString());
                                if(StringUtils.isNotBlank(sqlNow)){
                                    map.put("SIGN_PERS_GROUP",l.getLabelValue());
                                    count += sysDao.getServiceReadDo().findCount(sqlNow, map);
                                }
                                v.setValue(String.valueOf(count));
                                lsEntity.add(v);
                            }
                        }else if(l.getLabelValue().equals("12")){//癌症
                            if(maps.get(0).get("manageAzCount") != null){
                                v.setTitle("manageAzCount");
                                v.setName(l.getLabelTitle());
                                int count = (int)Double.parseDouble(maps.get(0).get("manageAzCount").toString());
                                if(StringUtils.isNotBlank(sqlNow)){
                                    map.put("SIGN_PERS_GROUP",l.getLabelValue());
                                    count += sysDao.getServiceReadDo().findCount(sqlNow, map);
                                }
                                v.setValue(String.valueOf(count));
                                lsEntity.add(v);
                            }
                        }else if(l.getLabelValue().equals("99")){//其他
                            if(maps.get(0).get("manageServiceUnknownCount") != null){
                                v.setTitle("manageServiceUnknownCount");
                                v.setName(l.getLabelTitle());
                                int count = (int)Double.parseDouble(maps.get(0).get("manageServiceUnknownCount").toString());
                                if(StringUtils.isNotBlank(sqlNow)){
                                    map.put("SIGN_PERS_GROUP",l.getLabelValue());
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
     * 服务分布(返回字段英文)
     * @param qvo areaId 区域主键 hospId 社区主键 teamId 团队主键
     * @return
     */
    @Override
    public Map findPersGroupCountEnglish(ResidentVo qvo) throws Exception{
        HashMap rmap = new HashMap();
        //List<CdCode> ls = sysDao.getCodeDao().findGroupList(CodeGroupConstrats.GROUP_RESIDENTMANGE, CommonEnable.QIYONG.getValue());
        List<AppLabelManage> ls = this.sysDao.getAppLabelManageDao().findByType("3");
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
            String sqlNow = null;
            if(end >= now){
                String[] state = {SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};//状态
                map.put("yearStart", ExtendDate.getYMD(Calendar.getInstance())+" 00:00:00");
                map.put("yearEnd",ExtendDate.getYMD(Calendar.getInstance())+" 23:59:59");
                map.put("SIGN_STATE", state);
                String sql = " AND t.SIGN_FROM_DATE >= :yearStart AND t.SIGN_FROM_DATE <= :yearEnd ";
                sqlNow = "SELECT COUNT(1) FROM APP_SIGN_FORM t LEFT JOIN APP_LABEL_GROUP b on t.ID=b.LABEL_SIGN_ID WHERE t.SIGN_STATE IN (:SIGN_STATE) "+sql;
                sqlNow += " AND b.LABEL_VALUE = :SIGN_PERS_GROUP ";
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
            map.put("STARTDATE",qvo.getYearStart());
            map.put("ENDDATE",qvo.getYearEnd());
            String sqlDate = " AND t.MANAGE_YEAR_MONTH >= :STARTDATE AND t.MANAGE_YEAR_MONTH <= :ENDDATE ";
            String sql = "SELECT " +
                    "SUM(t.MANAGE_PLAIN_COUNT) managePlainCount," +
                    "SUM(t.MANAGE_CHILD_COUNT) manageChildCount," +
                    "SUM(t.MANAGE_MATERNAL_COUNT) manageMaternalCount," +
                    "SUM(t.MANAGE_OLD_PEOPLE_COUNT) manageOldPeopleCount," +
                    "SUM(t.MANAGE_BLOOD_COUNT) manageBloodCount," +
                    "SUM(t.MANAGE_DIABETES_COUNT) manageDiabetesCount," +
                    "SUM(t.MANAGE_PSYCHOSIS_COUNT) managePsychosisCount," +
                    "SUM(t.MANAGE_PHTHISIS_COUNT) managePhthisisCount," +
                    "SUM(t.MANAGE_DISABLED_PEOPLE_COUNT) manageDisabledPeopleCount," +
                    "SUM(t.MANAGE_SERVICE_UNKNOWN_COUNT) manageServiceUnknownCount " +
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
                    if(l.getLabelValue().equals("1")){//普通人群
                        if(maps.get(0).get("managePlainCount") != null){
                            int count = (int)Double.parseDouble(maps.get(0).get("managePlainCount").toString());
                            if(StringUtils.isNotBlank(sqlNow)){
                                map.put("SIGN_PERS_GROUP",l.getLabelValue());
                                count += sysDao.getServiceReadDo().findCount(sqlNow, map);
                            }
                            rmap.put("managePlainCount",count);
                        }

                    }else if(l.getLabelValue().equals("2")){//儿童(0-6岁)
                        if(maps.get(0).get("manageChildCount") != null){
                            int count = (int)Double.parseDouble(maps.get(0).get("manageChildCount").toString());
                            if(StringUtils.isNotBlank(sqlNow)){
                                map.put("SIGN_PERS_GROUP",l.getLabelValue());
                                count += sysDao.getServiceReadDo().findCount(sqlNow, map);
                            }
                            rmap.put("manageChildCount",count);
                        }
                    }else if(l.getLabelValue().equals("3")){//孕产妇
                        if(maps.get(0).get("manageMaternalCount") != null){
                            int count = (int)Double.parseDouble(maps.get(0).get("manageMaternalCount").toString());
                            if(StringUtils.isNotBlank(sqlNow)){
                                map.put("SIGN_PERS_GROUP",l.getLabelValue());
                                count += sysDao.getServiceReadDo().findCount(sqlNow, map);
                            }
                            rmap.put("manageMaternalCount",count);
                        }
                    }else if(l.getLabelValue().equals("4")){//老年人
                        if(maps.get(0).get("manageOldPeopleCount") != null){
                            int count = (int)Double.parseDouble(maps.get(0).get("manageOldPeopleCount").toString());
                            if(StringUtils.isNotBlank(sqlNow)){
                                map.put("SIGN_PERS_GROUP",l.getLabelValue());
                                count += sysDao.getServiceReadDo().findCount(sqlNow, map);
                            }
                            rmap.put("manageOldPeopleCount",count);
                        }
                    }else if(l.getLabelValue().equals("5")){//高血压
                        if(maps.get(0).get("manageBloodCount") != null){
                            int count = (int)Double.parseDouble(maps.get(0).get("manageBloodCount").toString());
                            if(StringUtils.isNotBlank(sqlNow)){
                                map.put("SIGN_PERS_GROUP",l.getLabelValue());
                                count += sysDao.getServiceReadDo().findCount(sqlNow, map);
                            }
                            rmap.put("manageBloodCount",count);
                        }
                    }else if(l.getLabelValue().equals("6")){//糖尿病
                        if(maps.get(0).get("manageDiabetesCount") != null){
                            int count = (int)Double.parseDouble(maps.get(0).get("manageDiabetesCount").toString());
                            if(StringUtils.isNotBlank(sqlNow)){
                                map.put("SIGN_PERS_GROUP",l.getLabelValue());
                                count += sysDao.getServiceReadDo().findCount(sqlNow, map);
                            }
                            rmap.put("manageDiabetesCount",count);
                        }
                    }else if(l.getLabelValue().equals("7")){//严重精神障碍
                        if(maps.get(0).get("managePsychosisCount") != null){
                            int count = (int)Double.parseDouble(maps.get(0).get("managePsychosisCount").toString());
                            if(StringUtils.isNotBlank(sqlNow)){
                                map.put("SIGN_PERS_GROUP",l.getLabelValue());
                                count += sysDao.getServiceReadDo().findCount(sqlNow, map);
                            }
                            rmap.put("managePsychosisCount",count);
                        }
                    }else if(l.getLabelValue().equals("8")){//结核病
                        if(maps.get(0).get("managePhthisisCount") != null){
                            int count = (int)Double.parseDouble(maps.get(0).get("managePhthisisCount").toString());
                            if(StringUtils.isNotBlank(sqlNow)){
                                map.put("SIGN_PERS_GROUP",l.getLabelValue());
                                count += sysDao.getServiceReadDo().findCount(sqlNow, map);
                            }
                            rmap.put("managePhthisisCount",count);
                        }
                    }else if(l.getLabelValue().equals("9")){//残疾人
                        if(maps.get(0).get("manageDisabledPeopleCount") != null){
                            int count = (int)Double.parseDouble(maps.get(0).get("manageDisabledPeopleCount").toString());
                            if(StringUtils.isNotBlank(sqlNow)){
                                map.put("SIGN_PERS_GROUP",l.getLabelValue());
                                count += sysDao.getServiceReadDo().findCount(sqlNow, map);
                            }
                            rmap.put("manageDisabledPeopleCount",count);
                        }
                    }else if(l.getLabelValue().equals("99")){//未知
                        if(maps.get(0).get("manageServiceUnknownCount") != null){
                            int count = (int)Double.parseDouble(maps.get(0).get("manageServiceUnknownCount").toString());
                            if(StringUtils.isNotBlank(sqlNow)){
                                map.put("SIGN_PERS_GROUP",l.getLabelValue());
                                count += sysDao.getServiceReadDo().findCount(sqlNow, map);
                            }
                            rmap.put("manageServiceUnknownCount",count);
                        }
                    }
                }
            }
            return rmap;
        }
        return rmap;
    }

    /**
     * 查询性别 男 女
     * @param  qvo areaId 区域主键 hospId 社区主键 teamId 团队主键
     * @return
     */
    @Override
    public Map<String,Object> findCountGender(ResidentVo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object> ();
        Map<String,Object> mapCount = new HashMap<String,Object> ();
        Calendar calendar = Calendar.getInstance();
        int nowYear = calendar.get(Calendar.YEAR);
        int nowMonth = calendar.get(Calendar.MONTH) + 1;
        int now = Integer.parseInt(String.valueOf(nowYear)+ String.valueOf(nowMonth));
        String[] year = qvo.getYearEnd().split("-");
        int endYear = Integer.parseInt(year[0]);
        int endMonth = Integer.parseInt(year[1]);
        int end = Integer.parseInt(String.valueOf(endYear)+ String.valueOf(endMonth));
//        if(StringUtils.isNotBlank(qvo.getTeamId())){
//            map.put("STARTDATE",qvo.getYearStart());
//            map.put("ENDDATE",qvo.getYearEnd());
//            String sqlDate = " AND t.MANAGE_YEAR_MONTH >= :STARTDATE AND t.MANAGE_YEAR_MONTH <= :ENDDATE ";
//            String sql = "SELECT " +
//                    " SUM(t.MANAGE_MALE_COUNT) maleCount,SUM(t.MANAGE_FEMALE_COUNT) feMaleCount,SUM(t.MANAGE_GENDER_TOTAL_COUNT) totalCount "+
//                    " FROM APP_MANAGE_TEAM_COUNT t where 1=1 "+sqlDate;
//            map.put("TEAM_ID",qvo.getTeamId());
//            sql += " AND t.MANAGE_TEAM_ID = :TEAM_ID ";
//            List<Map> maps = this.sysDao.getServiceReadDo().findSqlMap(sql,map);
//            if(maps != null && maps.size() >0){
//                if(maps.get(0).get("maleCount") != null)
//                    mapCount.put("男性",(int)Double.parseDouble(maps.get(0).get("maleCount").toString()));
//                if(maps.get(0).get("feMaleCount") != null)
//                    mapCount.put("女性",(int)Double.parseDouble(maps.get(0).get("feMaleCount").toString()));
//                if(maps.get(0).get("totalCount") != null)
//                    mapCount.put("总数",(int)Double.parseDouble(maps.get(0).get("totalCount").toString()));
//            }
//            map.put("yqy", SignFormType.YQY.getValue());
//            map.put("yearStart", ExtendDate.getFirstDayOfMonth(qvo.getYearStart())+" 00:00:00");
//            map.put("yearEnd",ExtendDate.getLastDayOfMonth(qvo.getYearEnd())+" 23:59:59");
//            String sqlMan = "SELECT count(1) FROM APP_SIGN_FORM a where a.SIGN_PATIENT_GENDER = '1' AND a.SIGN_STATE = :yqy AND a.SIGN_FROM_DATE >= :yearStart AND a.SIGN_FROM_DATE <= :yearEnd ";//统计男
//            String sqlWoMan = "SELECT count(1) FROM APP_SIGN_FORM a where a.SIGN_PATIENT_GENDER = '2'  AND a.SIGN_STATE = :yqy AND a.SIGN_FROM_DATE >= :yearStart AND a.SIGN_FROM_DATE <= :yearEnd ";//统计女
//            map.put("teamId",qvo.getTeamId());
//            sqlMan += " AND a.SIGN_TEAM_ID =:teamId";
//            sqlWoMan += " AND a.SIGN_TEAM_ID =:teamId";
//            int man = this.sysDao.getServiceReadDo().findCount(sqlMan,map);
//            int woMan = this.sysDao.getServiceReadDo().findCount(sqlWoMan,map);
//            mapCount.put("男性",man);
//            mapCount.put("女性",woMan);
//            mapCount.put("总数",man+woMan);
//            return mapCount;
//        }
        String sqlMan = null;
        String sqlWoMan = null;
        int allCount = 0;
        //如果结束时间大于等于当前时间
        if(end >= now){
//            String state = SignFormType.YQY.getValue();//状态
            String[] state = {SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};//状态
            map.put("SIGN_STATE",state);
            map.put("yearStart", ExtendDate.getYMD(Calendar.getInstance())+" 00:00:00");
            map.put("yearEnd",ExtendDate.getYMD(Calendar.getInstance())+" 23:59:59");
            sqlMan = "SELECT count(1) FROM APP_SIGN_FORM t where t.SIGN_PATIENT_GENDER = '1' AND t.SIGN_STATE IN (:SIGN_STATE) AND t.SIGN_FROM_DATE >= :yearStart AND t.SIGN_FROM_DATE <= :yearEnd ";//统计男
            sqlWoMan = "SELECT count(1) FROM APP_SIGN_FORM t where t.SIGN_PATIENT_GENDER = '2'  AND t.SIGN_STATE IN (:SIGN_STATE) AND t.SIGN_FROM_DATE >= :yearStart AND t.SIGN_FROM_DATE <= :yearEnd ";//统计女
            if(StringUtils.isNotBlank(qvo.getTeamId())){
                sqlMan += " AND t.SIGN_TEAM_ID =:SIGN_TEAM_ID";
                sqlWoMan += " AND t.SIGN_TEAM_ID =:SIGN_TEAM_ID";
                map.put("SIGN_TEAM_ID", qvo.getTeamId());
            }
            if(StringUtils.isNotBlank(qvo.getHospId())){
                map.put("SIGN_HOSP_ID",qvo.getHospId());
                sqlMan += " AND t.SIGN_HOSP_ID = :SIGN_HOSP_ID ";
                sqlWoMan += " AND t.SIGN_HOSP_ID = :SIGN_HOSP_ID ";
            }
            if(StringUtils.isNotBlank(qvo.getAreaId())){
                String areaCode = AreaUtils.getAreaCode(qvo.getAreaId());
                map.put("SIGN_AREA_CODE",areaCode+"%");
                sqlMan += " AND t.SIGN_AREA_CODE LIKE :SIGN_AREA_CODE ";
                sqlWoMan += " AND t.SIGN_AREA_CODE LIKE :SIGN_AREA_CODE ";
            }
        }
        map.put("STARTDATE",qvo.getYearStart());
        map.put("ENDDATE",qvo.getYearEnd());
        String sqlDate = " AND t.MANAGE_YEAR_MONTH >= :STARTDATE AND t.MANAGE_YEAR_MONTH <= :ENDDATE ";
        String sql = "SELECT SUM(t.MANAGE_MALE_COUNT) maleCount,SUM(t.MANAGE_FEMALE_COUNT) feMaleCount,SUM(t.MANAGE_GENDER_TOTAL_COUNT) totalCount FROM APP_MANAGE_COUNT t where 1=1 "+sqlDate;
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
            sql += "  AND t.MANAGE_AREA_CODE LIKE :AREA_CODE ";
        }
        List<Map> maps = this.sysDao.getServiceReadDo().findSqlMap(sql,map);
        if(maps != null && maps.size() >0){
            if(maps.get(0).get("maleCount") != null){
                int count = (int)Double.parseDouble(maps.get(0).get("maleCount").toString());
                if(StringUtils.isNotBlank(sqlMan)){
                    int man = this.sysDao.getServiceReadDo().findCount(sqlMan,map);
                    allCount += man;
                    count += man;
                }
                mapCount.put("男性",count);
            }

            if(maps.get(0).get("feMaleCount") != null){
                int count = (int)Double.parseDouble(maps.get(0).get("feMaleCount").toString());
                if(StringUtils.isNotBlank(sqlMan)){
                    int woMan = this.sysDao.getServiceReadDo().findCount(sqlWoMan,map);
                    allCount += woMan;
                    count += woMan;
                }
                mapCount.put("女性",count);
            }

            if(maps.get(0).get("totalCount") != null) {
                int count = (int) Double.parseDouble(maps.get(0).get("totalCount").toString()) + allCount;
                mapCount.put("总数", count);
            }
        }
         return mapCount;
    }

    /**
     * 查询性别男女(返回字段英文)
     * @param  qvo areaId 区域主键 hospId 社区主键 teamId 团队主键
     * @return
     */
    public Map<String,Object> findCountGenderEnglish(ResidentVo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object> ();
        Map<String,Object> mapCount = new HashMap<String,Object> ();
        Calendar calendar = Calendar.getInstance();
        int nowYear = calendar.get(Calendar.YEAR);
        int nowMonth = calendar.get(Calendar.MONTH) + 1;
        int now = Integer.parseInt(String.valueOf(nowYear)+ String.valueOf(nowMonth));
        String[] year = qvo.getYearEnd().split("-");
        int endYear = Integer.parseInt(year[0]);
        int endMonth = Integer.parseInt(year[1]);
        int end = Integer.parseInt(String.valueOf(endYear)+ String.valueOf(endMonth));
        String sqlMan = null;
        String sqlWoMan = null;
        int allCount = 0;
        //如果结束时间大于等于当前时间
        if(end >= now){
            String[] state = {SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};//状态
            map.put("SIGN_STATE",state);
            map.put("yearStart", ExtendDate.getYMD(Calendar.getInstance())+" 00:00:00");
            map.put("yearEnd",ExtendDate.getYMD(Calendar.getInstance())+" 23:59:59");
            sqlMan = "SELECT count(1) FROM APP_SIGN_FORM t where t.SIGN_PATIENT_GENDER = '1' AND t.SIGN_STATE IN (:SIGN_STATE) AND t.SIGN_FROM_DATE >= :yearStart AND t.SIGN_FROM_DATE <= :yearEnd ";//统计男
            sqlWoMan = "SELECT count(1) FROM APP_SIGN_FORM t where t.SIGN_PATIENT_GENDER = '2'  AND t.SIGN_STATE IN (:SIGN_STATE) AND t.SIGN_FROM_DATE >= :yearStart AND t.SIGN_FROM_DATE <= :yearEnd ";//统计女
            if(StringUtils.isNotBlank(qvo.getTeamId())){
                sqlMan += " AND t.SIGN_TEAM_ID =:SIGN_TEAM_ID";
                sqlWoMan += " AND t.SIGN_TEAM_ID =:SIGN_TEAM_ID";
                map.put("SIGN_TEAM_ID", qvo.getTeamId());
            }
            if(StringUtils.isNotBlank(qvo.getHospId())){
                map.put("SIGN_HOSP_ID",qvo.getHospId());
                sqlMan += " AND t.SIGN_HOSP_ID = :SIGN_HOSP_ID ";
                sqlWoMan += " AND t.SIGN_HOSP_ID = :SIGN_HOSP_ID ";
            }
            if(StringUtils.isNotBlank(qvo.getAreaId())){
                String areaCode = AreaUtils.getAreaCode(qvo.getAreaId());
                map.put("SIGN_AREA_CODE",areaCode+"%");
                sqlMan += " AND t.SIGN_AREA_CODE LIKE :SIGN_AREA_CODE ";
                sqlWoMan += " AND t.SIGN_AREA_CODE LIKE :SIGN_AREA_CODE ";
            }
        }
        map.put("STARTDATE",qvo.getYearStart());
        map.put("ENDDATE",qvo.getYearEnd());
        String sqlDate = " AND t.MANAGE_YEAR_MONTH >= :STARTDATE AND t.MANAGE_YEAR_MONTH <= :ENDDATE ";
        String sql = "SELECT SUM(t.MANAGE_MALE_COUNT) maleCount,SUM(t.MANAGE_FEMALE_COUNT) feMaleCount,SUM(t.MANAGE_GENDER_TOTAL_COUNT) totalCount FROM APP_MANAGE_COUNT t where 1=1 "+sqlDate;
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
            sql += "  AND t.MANAGE_AREA_CODE LIKE :AREA_CODE ";
        }
        List<Map> maps = this.sysDao.getServiceReadDo().findSqlMap(sql,map);
        int maleCount = 0;
        int feMaleCount = 0;
        int totalCount = 0;
        if(maps != null && maps.size() >0){
            if(maps.get(0).get("maleCount") != null){
                int count = (int)Double.parseDouble(maps.get(0).get("maleCount").toString());
                if(StringUtils.isNotBlank(sqlMan)){
                    int man = this.sysDao.getServiceReadDo().findCount(sqlMan,map);
                    allCount += man;
                    count += man;
                }
                maleCount = count;
//                mapCount.put("maleCount",count);
            }

            if(maps.get(0).get("feMaleCount") != null){
                int count = (int)Double.parseDouble(maps.get(0).get("feMaleCount").toString());
                if(StringUtils.isNotBlank(sqlMan)){
                    int woMan = this.sysDao.getServiceReadDo().findCount(sqlWoMan,map);
                    allCount += woMan;
                    count += woMan;
                }
                feMaleCount =count;
//                mapCount.put("feMaleCount",count);
            }

            if(maps.get(0).get("totalCount") != null) {
                int count = (int) Double.parseDouble(maps.get(0).get("totalCount").toString()) + allCount;
                totalCount = count;
//                mapCount.put("totalCount", count);
            }
        }
        mapCount.put("maleCount",maleCount);
        mapCount.put("feMaleCount",feMaleCount);
        mapCount.put("totalCount", totalCount);
        return mapCount;
    }

    /**
     * 年龄分布
     * @param  qvo areaId 区域主键 hospId 社区主键 teamId 团队主键
     * @return
     */
    @Override
    public Map<String, Object> findCountAge(ResidentVo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object> ();
        Map<String,Object> mapCount = new HashMap<String,Object> ();
        String  tjRsult = "0,6;7,18;19,30;31,50;51,65;66,200";
        String  tjRsultTitle = "0~6;7~18;19~30;31~50;51~65;>65";
        String[] result = tjRsult.split(";");
        String[] resultTitle = tjRsultTitle.split(";");
        List<String> lsTitle = new ArrayList<String>();
        List<Integer> lsValue = new ArrayList<Integer>();
        if(result != null){
            Calendar calendar = Calendar.getInstance();
            int nowYear = calendar.get(Calendar.YEAR);
            int nowMonth = calendar.get(Calendar.MONTH) + 1;
            int now = Integer.parseInt(String.valueOf(nowYear)+ String.valueOf(nowMonth));
            String[] year = qvo.getYearEnd().split("-");
            int endYear = Integer.parseInt(year[0]);
            int endMonth = Integer.parseInt(year[1]);
            int end = Integer.parseInt(String.valueOf(endYear)+ String.valueOf(endMonth));
//            if(StringUtils.isNotBlank(qvo.getTeamId())){
//                map.put("STARTDATE",qvo.getYearStart());
//                map.put("ENDDATE",qvo.getYearEnd());
//                String sqlDate = " AND t.MANAGE_YEAR_MONTH >= :STARTDATE AND t.MANAGE_YEAR_MONTH <= :ENDDATE ";
//                String sql = "SELECT " +
//                        "SUM(t.MANAGE_0_6_COUNT) 06Count," +
//                        "SUM(t.MANAGE_7_18_COUNT) 718Count," +
//                        "SUM(t.MANAGE_19_30_COUNT) 1930Count," +
//                        "SUM(t.MANAGE_31_50_COUNT) 3150Count, " +
//                        "SUM(t.MANAGE_51_65_COUNT) 5165Count, " +
//                        "SUM(t.MANAGE_GREATER_65_COUNT) greater65Count " +
//                        " FROM APP_MANAGE_TEAM_COUNT t where 1=1 "+sqlDate;
//                map.put("TEAM_ID",qvo.getTeamId());
//                sql += " AND t.MANAGE_TEAM_ID = :TEAM_ID ";
//                List<Map> maps = this.sysDao.getServiceReadDo().findSqlMap(sql,map);
//                if(maps != null && maps.size() >0){
//                    if(maps.get(0).get("06Count") != null)
//                        lsValue.add((int)Double.parseDouble(maps.get(0).get("06Count").toString()));
//                    if(maps.get(0).get("718Count") != null)
//                        lsValue.add((int)Double.parseDouble(maps.get(0).get("718Count").toString()));
//                    if(maps.get(0).get("1930Count") != null)
//                        lsValue.add((int)Double.parseDouble(maps.get(0).get("1930Count").toString()));
//                    if(maps.get(0).get("3150Count") != null)
//                        lsValue.add((int)Double.parseDouble(maps.get(0).get("3150Count").toString()));
//                    if(maps.get(0).get("5165Count") != null)
//                        lsValue.add((int)Double.parseDouble(maps.get(0).get("5165Count").toString()));
//                    if(maps.get(0).get("greater65Count") != null)
//                        lsValue.add((int)Double.parseDouble(maps.get(0).get("greater65Count").toString()));
//                }
//                for(int i=0;i<resultTitle.length;i++){
//                    lsTitle.add(resultTitle[i]);
//                }
//                mapCount.put("lsTitle",lsTitle);
//                mapCount.put("lsValue",lsValue);
//                map.put("yearStart", ExtendDate.getFirstDayOfMonth(qvo.getYearStart())+" 00:00:00");
//                map.put("yearEnd",ExtendDate.getLastDayOfMonth(qvo.getYearEnd())+" 23:59:59");
//                map.put("SIGN_STATE", SignFormType.YQY.getValue());
//                String sql = "select count(*) from APP_SIGN_FORM a WHERE 1=1 AND a.SIGN_FROM_DATE >= :yearStart AND a.SIGN_FROM_DATE <= :yearEnd and a.SIGN_STATE =:SIGN_STATE ";
//                map.put("teamId",qvo.getTeamId());
//                for(int i=0;i<result.length;i++){
//                    String[] age = result[i].split(",");
//                    map.put("startAge",age[0]);
//                    map.put("endAge",age[1]);
//                    sql += " AND a.SIGN_PATIENT_AGE  >= :startAge AND a.SIGN_PATIENT_AGE  <= :endAge ";
//                    sql += " AND a.SIGN_TEAM_ID =:teamId ";
//                    int count = this.sysDao.getServiceReadDo().findCount(sql,map);
//                    lsTitle.add(resultTitle[i]);
//                    lsValue.add(count);
//                }
//                mapCount.put("lsTitle",lsTitle);
//                mapCount.put("lsValue",lsValue);
//                return mapCount;
//            }

            String sqlNow = null;
            //如果结束时间大于等于当前时间
            if(end >= now){
//                String state = SignFormType.YQY.getValue();//状态
                String[] state = {SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};//状态
                map.put("SIGN_STATE",state);
                map.put("yearStart", ExtendDate.getYMD(Calendar.getInstance())+" 00:00:00");
                map.put("yearEnd",ExtendDate.getYMD(Calendar.getInstance())+" 23:59:59");
                sqlNow = "select count(*) from APP_SIGN_FORM t WHERE 1=1 AND t.SIGN_FROM_DATE >= :yearStart AND t.SIGN_FROM_DATE <= :yearEnd and t.SIGN_STATE IN (:SIGN_STATE) ";
                sqlNow += " AND t.SIGN_PATIENT_AGE  >= :startAge AND t.SIGN_PATIENT_AGE  <= :endAge ";
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


            map.put("STARTDATE",qvo.getYearStart());
            map.put("ENDDATE",qvo.getYearEnd());
            String sqlDate = " AND t.MANAGE_YEAR_MONTH >= :STARTDATE AND t.MANAGE_YEAR_MONTH <= :ENDDATE ";
            String sql = "SELECT " +
                    "SUM(t.MANAGE_0_6_COUNT) 06Count," +
                    "SUM(t.MANAGE_7_18_COUNT) 718Count," +
                    "SUM(t.MANAGE_19_30_COUNT) 1930Count," +
                    "SUM(t.MANAGE_31_50_COUNT) 3150Count, " +
                    "SUM(t.MANAGE_51_65_COUNT) 5165Count, " +
                    "SUM(t.MANAGE_GREATER_65_COUNT) greater65Count " +
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
                if(maps.get(0).get("06Count") != null){
                    int count = (int)Double.parseDouble(maps.get(0).get("06Count").toString());
                    if(StringUtils.isNotBlank(sqlNow)){
                        map.put("startAge","0");
                        map.put("endAge","6");
                        count += this.sysDao.getServiceReadDo().findCount(sqlNow,map);
                    }
                    lsValue.add(count);

                }

                if(maps.get(0).get("718Count") != null){
                    int count = (int)Double.parseDouble(maps.get(0).get("718Count").toString());
                    if(StringUtils.isNotBlank(sqlNow)){
                        map.put("startAge","7");
                        map.put("endAge","18");
                        count += this.sysDao.getServiceReadDo().findCount(sqlNow,map);
                    }
                    lsValue.add(count);
                }

                if(maps.get(0).get("1930Count") != null){
                    int count = (int)Double.parseDouble(maps.get(0).get("718Count").toString());
                    if(StringUtils.isNotBlank(sqlNow)){
                        map.put("startAge","19");
                        map.put("endAge","30");
                        count += this.sysDao.getServiceReadDo().findCount(sqlNow,map);
                    }
                    lsValue.add(count);
                }

                if(maps.get(0).get("3150Count") != null){
                    int count = (int)Double.parseDouble(maps.get(0).get("3150Count").toString());
                    if(StringUtils.isNotBlank(sqlNow)){
                        map.put("startAge","31");
                        map.put("endAge","50");
                        count += this.sysDao.getServiceReadDo().findCount(sqlNow,map);
                    }
                    lsValue.add(count);
                }

                if(maps.get(0).get("5165Count") != null){
                    int count = (int)Double.parseDouble(maps.get(0).get("5165Count").toString());
                    if(StringUtils.isNotBlank(sqlNow)){
                        map.put("startAge","51");
                        map.put("endAge","65");
                        count += this.sysDao.getServiceReadDo().findCount(sqlNow,map);
                    }
                    lsValue.add(count);
                }

                if(maps.get(0).get("greater65Count") != null){
                    int count = (int)Double.parseDouble(maps.get(0).get("greater65Count").toString());
                    if(StringUtils.isNotBlank(sqlNow)){
                        map.put("startAge","66");
                        map.put("endAge","200");
                        count += this.sysDao.getServiceReadDo().findCount(sqlNow,map);
                    }
                    lsValue.add(count);
                }

            }
            for(int i=0;i<resultTitle.length;i++){
                lsTitle.add(resultTitle[i]);
            }
            mapCount.put("lsTitle",lsTitle);
            mapCount.put("lsValue",lsValue);
            return mapCount;
        }
        return null;
    }


    /**
     * 缴费情况
     * @param qvo areaId 区域主键 hospId 社区主键 teamId 团队主键
     * @return
     */
    @Override
    public Map findPayStateCountInitialise(ResidentVo qvo) throws Exception{
        HashMap rmap = new HashMap();
        HashMap map = new HashMap();
        map.put("yearStart", ExtendDate.getFirstDayOfMonth(qvo.getYearStart())+" 00:00:00");
        map.put("yearEnd",ExtendDate.getLastDayOfMonth(qvo.getYearEnd())+" 23:59:59");
//        map.put("yearEnd","2018-10-18 23:59:59");
        String sql = " AND a.SIGN_FROM_DATE >= :yearStart AND a.SIGN_FROM_DATE <= :yearEnd ";
//            String[] qys = {SignFormType.YQY.getValue(),SignFormType.YJY.getValue()};
//            map.put("yqy", qys);
//        String qys = SignFormType.YQY.getValue();
        String[] qys = {SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
        map.put("yqy", qys);

        map.put("yjy",SignFormType.YJY.getValue());
        map.put("tsnr","签约到期，自动解约");

        boolean flag = ExtendDateUtil.getIsDateSatisfied(qvo.getYearEnd());
        String sqlyjff = "SELECT COUNT(1) FROM APP_SIGN_FORM a WHERE a.SIGN_PAY_STATE = '1' AND a.SIGN_STATE =:yjy AND a.SIGN_URRENDER_REASON =:tsnr  " +sql;
        String sqlwjff= "SELECT COUNT(1) FROM APP_SIGN_FORM a WHERE a.SIGN_PAY_STATE = '0' AND a.SIGN_STATE =:yjy AND a.SIGN_URRENDER_REASON =:tsnr  "+sql;

        //已缴费人数
        String sqlyjf="SELECT COUNT(1) FROM APP_SIGN_FORM a WHERE a.SIGN_PAY_STATE = '1' AND a.SIGN_STATE IN (:yqy) "+sql;
        String sqlwjf="SELECT COUNT(1) FROM APP_SIGN_FORM a WHERE a.SIGN_PAY_STATE = '0' AND a.SIGN_STATE IN (:yqy) "+sql;
        if(StringUtils.isNotBlank(qvo.getHospId())){
            sqlyjf +=" AND a.SIGN_HOSP_ID =:SIGN_HOSP_ID";
            sqlwjf +=" AND a.SIGN_HOSP_ID =:SIGN_HOSP_ID";
            sqlyjff +=" AND a.SIGN_HOSP_ID =:SIGN_HOSP_ID";
            sqlwjff +=" AND a.SIGN_HOSP_ID =:SIGN_HOSP_ID";
            map.put("SIGN_HOSP_ID",qvo.getHospId());
        }

        if(StringUtils.isNotBlank(qvo.getTeamId())){
            sqlyjf +=" AND a.SIGN_TEAM_ID =:SIGN_TEAM_ID";
            sqlwjf +=" AND a.SIGN_TEAM_ID =:SIGN_TEAM_ID";
            sqlyjff +=" AND a.SIGN_TEAM_ID =:SIGN_TEAM_ID";
            sqlwjff +=" AND a.SIGN_TEAM_ID =:SIGN_TEAM_ID";
            map.put("SIGN_TEAM_ID",qvo.getTeamId());
        }

        int yjf=sysDao.getServiceReadDo().findCount(sqlyjf,map);
        int wjf=sysDao.getServiceReadDo().findCount(sqlwjf,map);
        if(flag){
            int yjff=sysDao.getServiceReadDo().findCount(sqlyjff,map);
            int wjff=sysDao.getServiceReadDo().findCount(sqlwjff,map);
            yjf+=yjff;
            wjf+=wjff;
        }
        rmap.put("manageAlreadyPaidCount",yjf);
        rmap.put("manageUnpaidCount",wjf);
        return rmap;
    }

    /**
     * 健康分布
     * @param qvo areaId 区域主键 hospId 社区主键 teamId 团队主键
     * @return
     */
    @Override
    public Map findHealthGroupCountInitialise(ResidentVo qvo) throws Exception{
        HashMap rmap = new HashMap();
        //健康分布
        List<AppLabelManage> ls=sysDao.getServiceReadDo().loadByPk(AppLabelManage.class,"labelType","1");
        if(ls!=null && !ls.isEmpty()) {
            HashMap map = new HashMap();
            map.put("yearStart", ExtendDate.getFirstDayOfMonth(qvo.getYearStart())+" 00:00:00");
            map.put("yearEnd",ExtendDate.getLastDayOfMonth(qvo.getYearEnd())+" 23:59:59");
//            map.put("yearEnd","2018-10-18 23:59:59");
            String sql = "SELECT COUNT(1) FROM APP_SIGN_FORM a WHERE 1=1  ";
//            String[] qys = {SignFormType.YQY.getValue(),SignFormType.YJY.getValue()};
//            map.put("yqy", qys);
//            String qys = SignFormType.YQY.getValue();
            String[] qys = {SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
            map.put("yqy", qys);

            map.put("yjy",SignFormType.YJY.getValue());
            map.put("tsnr","签约到期，自动解约");
            boolean flag = ExtendDateUtil.getIsDateSatisfied(qvo.getYearEnd());
            String sqll = "SELECT COUNT(1) FROM APP_SIGN_FORM a WHERE 1=1  AND a.SIGN_STATE =:yjy " +
                    "AND a.SIGN_URRENDER_REASON =:tsnr AND a.SIGN_FROM_DATE >= :yearStart AND a.SIGN_FROM_DATE <= :yearEnd ";

            sql += " AND a.SIGN_STATE IN (:yqy) ";
            sql += "AND a.SIGN_FROM_DATE >= :yearStart AND a.SIGN_FROM_DATE <= :yearEnd ";
            if (StringUtils.isNotBlank(qvo.getHospId())) {
                map.put("SIGN_HOSP_ID", qvo.getHospId());
                sql += " AND a.SIGN_HOSP_ID =:SIGN_HOSP_ID";
                sql += " AND a.SIGN_HEALTH_GROUP=:SIGN_HEALTH_GROUP";
                sqll += " AND a.SIGN_HOSP_ID =:SIGN_HOSP_ID";
                sqll += " AND a.SIGN_HEALTH_GROUP=:SIGN_HEALTH_GROUP";
            }

            if (StringUtils.isNotBlank(qvo.getTeamId())) {
                map.put("SIGN_TEAM_ID",qvo.getTeamId());
                sql += " AND a.SIGN_TEAM_ID =:SIGN_TEAM_ID";
                sql += " AND a.SIGN_HEALTH_GROUP=:SIGN_HEALTH_GROUP";
                sqll += " AND a.SIGN_TEAM_ID =:SIGN_TEAM_ID";
                sqll += " AND a.SIGN_HEALTH_GROUP=:SIGN_HEALTH_GROUP";
            }

            for(AppLabelManage l:ls) {
                map.put("SIGN_HEALTH_GROUP",l.getLabelValue());
                int  count= sysDao.getServiceReadDo().findCount(sql, map);
                if(flag){
                    int ccount = sysDao.getServiceReadDo().findCount(sqll,map);
                    count += ccount;
                }
                if(l.getLabelValue().equals("101")){//患病人群
                    rmap.put("manageBeillCount", count);
                }else if(l.getLabelValue().equals("102")){//健康人群
                    rmap.put("manageHealthCount", count);
                }else if(l.getLabelValue().equals("103")){//高危人群
                    rmap.put("manageHighRiskCount", count);
                }else if(l.getLabelValue().equals("104")){//恢复期人群
                    rmap.put("manageConvalescenceCount", count);
                }else if(l.getLabelValue().equals("199")){//未知
                    rmap.put("manageHealthUnknownCount", count);
                }
            }
            return rmap;
        }
        return rmap;

    }

    /**
     * 服务分布
     * @param qvo areaId 区域主键 hospId 社区主键 teamId 团队主键
     * @return
     */
    @Override
    public Map findPersGroupCountInitialise(ResidentVo qvo) throws Exception{
        HashMap rmap = new HashMap();
        List<AppLabelManage> ls = this.sysDao.getAppLabelManageDao().findByType("3");
        if(ls!=null && !ls.isEmpty()) {
            HashMap map = new HashMap();
            map.put("yearStart", ExtendDate.getFirstDayOfMonth(qvo.getYearStart())+" 00:00:00");
            map.put("yearEnd",ExtendDate.getLastDayOfMonth(qvo.getYearEnd())+" 23:59:59");
//            map.put("yearEnd","2018-10-18 23:59:59");
            String sql = "SELECT a.* FROM APP_SIGN_FORM a LEFT JOIN APP_LABEL_GROUP b on a.ID=b.LABEL_SIGN_ID WHERE 1=1 ";
//            String[] qys = {SignFormType.YQY.getValue(),SignFormType.YJY.getValue()};
//            map.put("yqy", qys);
//            String qys = SignFormType.YQY.getValue();
            String[] qys = {SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
            map.put("yqy", qys);
            map.put("yjy",SignFormType.YJY.getValue());
            map.put("tsnr","签约到期，自动解约");
            boolean flag = ExtendDateUtil.getIsDateSatisfied(qvo.getYearEnd());
            String sqll = "SELECT a.* FROM APP_SIGN_FORM a LEFT JOIN APP_LABEL_GROUP b on a.ID=b.LABEL_SIGN_ID WHERE 1=1 " +
                    " AND a.SIGN_STATE =:yjy AND a.SIGN_URRENDER_REASON =:tsnr AND a.SIGN_FROM_DATE >= :yearStart AND a.SIGN_FROM_DATE <= :yearEnd ";

            sql += " AND a.SIGN_STATE IN (:yqy) ";
            sql += " AND a.SIGN_FROM_DATE >= :yearStart AND a.SIGN_FROM_DATE <= :yearEnd ";
            if (StringUtils.isNotBlank(qvo.getHospId())) {
                sql += " AND a.SIGN_HOSP_ID =:SIGN_HOSP_ID";
                sqll += " AND a.SIGN_HOSP_ID =:SIGN_HOSP_ID";
                map.put("SIGN_HOSP_ID", qvo.getHospId());
            }
            if (StringUtils.isNotBlank(qvo.getTeamId())) {
                sql += " AND a.SIGN_TEAM_ID =:SIGN_TEAM_ID";
                sqll += " AND a.SIGN_TEAM_ID =:SIGN_TEAM_ID";
                map.put("SIGN_TEAM_ID",qvo.getTeamId());

            }
            if (StringUtils.isNotBlank(qvo.getDrId())) {
                sql += " AND a.SIGN_DR_ID =:SIGN_DR_ID";
                sqll += " AND a.SIGN_DR_ID =:SIGN_DR_ID";
                map.put("SIGN_DR_ID",qvo.getDrId());
            }

            sql += " AND b.LABEL_VALUE=:SIGN_PERS_GROUP";
            sqll += " AND b.LABEL_VALUE=:SIGN_PERS_GROUP";
            String sqlAll = " SELECT count(1) FROM ("+sql+"  GROUP BY a.SIGN_PATIENT_ID ) c";
            String sqlAlll = " SELECT count(1) FROM ("+sqll+"  GROUP BY a.SIGN_PATIENT_ID ) c";
            for(AppLabelManage l:ls) {
                map.put("SIGN_PERS_GROUP",l.getLabelValue());
                int  count= sysDao.getServiceReadDo().findCount(sqlAll, map);
                if(flag){
                    int ccount = sysDao.getServiceReadDo().findCount(sqlAlll, map);
                    count += ccount;
                }
                if(l.getLabelValue().equals("1")){//普通人群
                    rmap.put("managePlainCount", count);
                }else if(l.getLabelValue().equals("2")){//儿童(0-6岁)
                    rmap.put("manageChildCount", count);
                }else if(l.getLabelValue().equals("3")){//孕产妇
                    rmap.put("manageMaternalCount", count);
                }else if(l.getLabelValue().equals("4")){//老年人
                    rmap.put("manageOldPeopleCount", count);
                }else if(l.getLabelValue().equals("5")){//高血压
                    rmap.put("manageBloodCount", count);
                }else if(l.getLabelValue().equals("6")){//糖尿病
                    rmap.put("manageDiabetesCount", count);
                }else if(l.getLabelValue().equals("7")){//严重精神障碍
                    rmap.put("managePsychosisCount", count);
                }else if(l.getLabelValue().equals("8")){//结核病
                    rmap.put("managePhthisisCount", count);
                }else if(l.getLabelValue().equals("9")){//残疾人
                    rmap.put("manageDisabledPeopleCount", count);
                }else if(l.getLabelValue().equals("99")){//其他
                    rmap.put("manageServiceUnknownCount", count);
                }else if(l.getLabelValue().equals("10")){//脑血管
                    rmap.put("manageNxgCount",count);
                }else if(l.getLabelValue().equals("11")){//冠心病
                    rmap.put("manageGxbCount",count);
                }else if(l.getLabelValue().equals("12")){//癌症
                    rmap.put("manageAzCount",count);
                }
            }
            return rmap;
        }
        return rmap;
    }

    /**
     * 查询性别 男 女
     * @param  qvo areaId 区域主键 hospId 社区主键 teamId 团队主键
     * @return
     */
    @Override
    public Map<String,Object> findCountGenderInitialise(ResidentVo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object> ();
        Map<String,Object> mapCount = new HashMap<String,Object> ();
        map.put("yearStart", ExtendDate.getFirstDayOfMonth(qvo.getYearStart())+" 00:00:00");
        map.put("yearEnd",ExtendDate.getLastDayOfMonth(qvo.getYearEnd())+" 23:59:59");
//        map.put("yearEnd","2018-10-18 23:59:59");
        String sqlMan = "SELECT count(1) FROM APP_SIGN_FORM a where a.SIGN_PATIENT_GENDER = '1'  AND a.SIGN_FROM_DATE >= :yearStart AND a.SIGN_FROM_DATE <= :yearEnd ";//统计男
        String sqlWoMan = "SELECT count(1) FROM APP_SIGN_FORM a where a.SIGN_PATIENT_GENDER = '2' AND a.SIGN_FROM_DATE >= :yearStart AND a.SIGN_FROM_DATE <= :yearEnd ";//统计女
        String[] qys = {SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
        boolean flag = ExtendDateUtil.getIsDateSatisfied(qvo.getYearEnd());
        map.put("yqy", qys);
        int manCc = 0;
        int woManCc = 0;
        String sqlManCc = sqlMan;
        String sqlWoManCc = sqlWoMan;
        map.put("yjy",SignFormType.YJY.getValue());
        map.put("tsnr","签约到期，自动解约");
        sqlManCc += " AND a.SIGN_STATE  =:yjy  AND a.SIGN_URRENDER_REASON =:tsnr ";
        sqlWoManCc += " AND a.SIGN_STATE  =:yjy  AND a.SIGN_URRENDER_REASON =:tsnr ";
//        map.put("yqy", qys);
//        String qys = SignFormType.YQY.getValue();

        sqlMan += " AND a.SIGN_STATE IN (:yqy) ";
        sqlWoMan += " AND a.SIGN_STATE IN (:yqy) ";
        if(StringUtils.isNotBlank(qvo.getHospId())){
            map.put("SIGN_HOSP_ID",qvo.getHospId());
            sqlMan += " AND a.SIGN_HOSP_ID =:SIGN_HOSP_ID";
            sqlWoMan += " AND a.SIGN_HOSP_ID =:SIGN_HOSP_ID";

            sqlManCc += " AND a.SIGN_HOSP_ID =:SIGN_HOSP_ID";
            sqlWoManCc += " AND a.SIGN_HOSP_ID =:SIGN_HOSP_ID";

            int man = this.sysDao.getServiceReadDo().findCount(sqlMan,map);
            int woMan = this.sysDao.getServiceReadDo().findCount(sqlWoMan,map);
            if(flag) {//加解约数据
                manCc = this.sysDao.getServiceReadDo().findCount(sqlManCc,map);
                woManCc = this.sysDao.getServiceReadDo().findCount(sqlWoManCc,map);
                man += manCc;
                woMan += woManCc;
            }
            mapCount.put("manageMaleCount",man);
            mapCount.put("manageFemaleCount",woMan);
            mapCount.put("manageGenderTotalCount",man+woMan);
            return mapCount;
        }
        if(StringUtils.isNotBlank(qvo.getTeamId())){
            map.put("SIGN_TEAM_ID",qvo.getTeamId());
            sqlMan += " AND a.SIGN_TEAM_ID = :SIGN_TEAM_ID";
            sqlWoMan += " AND a.SIGN_TEAM_ID = :SIGN_TEAM_ID";

            sqlManCc += " AND a.SIGN_TEAM_ID =:SIGN_TEAM_ID";
            sqlWoManCc += " AND a.SIGN_TEAM_ID =:SIGN_TEAM_ID";
            int man = this.sysDao.getServiceReadDo().findCount(sqlMan,map);
            int woMan = this.sysDao.getServiceReadDo().findCount(sqlWoMan,map);
            if(flag) {//加解约数据
                manCc = this.sysDao.getServiceReadDo().findCount(sqlManCc,map);
                woManCc = this.sysDao.getServiceReadDo().findCount(sqlWoManCc,map);
                man += manCc;
                woMan += woManCc;
            }
            mapCount.put("manageMaleCount",man);
            mapCount.put("manageFemaleCount",woMan);
            mapCount.put("manageGenderTotalCount",man+woMan);
            return mapCount;
        }
        return mapCount;
    }

    /**
     * 年龄分布
     * @param  qvo areaId 区域主键 hospId 社区主键 teamId 团队主键
     * @return
     */
    @Override
    public Map<String, Object> findCountAgeInitialise(ResidentVo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object> ();
        Map<String,Object> mapCount = new HashMap<String,Object> ();
        String  tjRsult = "0,6;7,18;19,30;31,50;51,65;66,200";
        String  tjRsultTitle = "manage06Count;manage718Count;manage1930Count;manage3150Count;manage5165Count;manageGreater65Count";
        String[] result = tjRsult.split(";");
        String[] resultTitle = tjRsultTitle.split(";");
        boolean flag = ExtendDateUtil.getIsDateSatisfied(qvo.getYearEnd());
        if(result != null){
            map.put("yearStart", ExtendDate.getFirstDayOfMonth(qvo.getYearStart())+" 00:00:00");
            map.put("yearEnd",ExtendDate.getLastDayOfMonth(qvo.getYearEnd())+" 23:59:59");
//            map.put("yearEnd","2018-10-18 23:59:59");
//            String[] qys = {SignFormType.YQY.getValue(),SignFormType.YJY.getValue()};
//            map.put("yqy", qys);
//            String qys = SignFormType.YQY.getValue();
            String[] qys = {SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
            map.put("yqy", qys);
            String sql = "select count(*) from APP_SIGN_FORM a WHERE 1=1 AND a.SIGN_FROM_DATE >= :yearStart AND a.SIGN_FROM_DATE <= :yearEnd AND a.SIGN_STATE IN (:yqy) ";
            map.put("yjy",SignFormType.YJY.getValue());
            map.put("tsnr","签约到期，自动解约");
            String ssql = "select count(*) from APP_SIGN_FORM a WHERE 1=1 AND a.SIGN_FROM_DATE >= :yearStart AND a.SIGN_FROM_DATE <= :yearEnd ";
            ssql += " AND a.SIGN_STATE  =:yjy  AND a.SIGN_URRENDER_REASON =:tsnr  ";
            if(StringUtils.isNotBlank(qvo.getHospId())){
                map.put("SIGN_HOSP_ID",qvo.getHospId());
                for(int i=0;i<result.length;i++){
                    String[] age = result[i].split(",");
                    map.put("startAge",age[0]);
                    map.put("endAge",age[1]);
                    sql += " AND a.SIGN_PATIENT_AGE  >= :startAge AND a.SIGN_PATIENT_AGE  <= :endAge ";
                    sql += " AND a.SIGN_HOSP_ID =:SIGN_HOSP_ID ";
                    ssql += " AND a.SIGN_PATIENT_AGE  >= :startAge AND a.SIGN_PATIENT_AGE  <= :endAge ";
                    ssql += " AND a.SIGN_HOSP_ID =:SIGN_HOSP_ID ";
                    int count = this.sysDao.getServiceReadDo().findCount(sql,map);
                    if(flag){
                        int scount = this.sysDao.getServiceReadDo().findCount(ssql,map);
                        count += scount;
                    }
                    mapCount.put(resultTitle[i],count);
                }
                return mapCount;
            }
            if(StringUtils.isNotBlank(qvo.getTeamId())){
                map.put("SIGN_TEAM_ID",qvo.getTeamId());
                for(int i=0;i<result.length;i++){
                    String[] age = result[i].split(",");
                    map.put("startAge",age[0]);
                    map.put("endAge",age[1]);
                    sql += " AND a.SIGN_PATIENT_AGE  >= :startAge AND a.SIGN_PATIENT_AGE  <= :endAge ";
                    sql += " AND a.SIGN_TEAM_ID = :SIGN_TEAM_ID ";
                    ssql += " AND a.SIGN_PATIENT_AGE  >= :startAge AND a.SIGN_PATIENT_AGE  <= :endAge ";
                    ssql += " AND a.SIGN_TEAM_ID = :SIGN_TEAM_ID ";
                    int count = this.sysDao.getServiceReadDo().findCount(sql,map);
                    if(flag){
                        int scount = this.sysDao.getServiceReadDo().findCount(ssql,map);
                        count += scount;
                    }
                    mapCount.put(resultTitle[i],count);
                }
                return mapCount;
            }
        }
        return null;
    }

    @Override
    public Map<String, Object> findCountByMin(ResidentVo qvo) throws Exception {
        Map<String,Object> returnMap = new HashMap<>();
        Map<String,Object> map = new HashMap<>();
        map.put("startTime",qvo.getYearStart());
        map.put("endTime",qvo.getYearEnd());
        map.put("areaCode",AreaUtils.getAreaCode(qvo.getAreaId())+"%");
        map.put("signState",new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()});
        String qySql = "SELECT COUNT(1) FROM APP_SIGN_FORM WHERE SIGN_FROM_DATE >=:startTime" +
                " AND SIGN_FROM_DATE<=:endTime AND SIGN_STATE IN (:signState) " +
                "AND SIGN_AREA_CODE LIKE :areaCode";
        int qyCount = sysDao.getServiceReadDo().findCount(qySql,map);
        returnMap.put("totalCount",qyCount);
        List<CdAddress> lsCdAddress = sysDao.getCdAddressDao().findUpCasheAreaCode(qvo.getAreaId());
        if(lsCdAddress!=null && !lsCdAddress.isEmpty()) {
            List<Map<String,Object>> listMap = new ArrayList<>();
            int[] strs = new int[]{0,1,2,3,4,5,6,7,8,9};
            int totalcount = 0;
            int totalZdrqCount = 0;
            for (CdAddress address : lsCdAddress) {
                Map<String,Object> mmp = new HashMap<>();
                mmp.put("areaCode",address.getCtcode().substring(0,4));
                mmp.put("areaName",address.getAreaSname());
                int count = getFindSignByMin(address,qvo);
                int zdrqCount = getFindSignByMinZdrq(address,qvo);
              /*  mmp.put("count",count);
                mmp.put("zdrqCount",zdrqCount);
                if(address.getCtcode().startsWith("3501") || address.getCtcode().startsWith("3507")){
                    mmp.put("count",0);
                    mmp.put("zdrqCount",0);
                }else{
                    mmp.put("count",3);
                    mmp.put("zdrqCount",3);
                    totalcount += 3;
                }*/

                /*if(count==0){//随机数
                    Random rand = new Random();
                    Random rand1 = new Random();
                    int xx = rand.nextInt(10);
                    int yy = rand1.nextInt(10);
                    count = strs[xx];
                    mmp.put("count",strs[xx]);
                    int xy = strs[yy];
                    totalcount +=strs[xx];
                    if(xy>=strs[xx]){
                        mmp.put("zdrqCount",0);
                    }else{
                        mmp.put("zdrqCount",strs[xx]-xy);
                        totalZdrqCount +=strs[xx]-xy;
                    }
                }else{
                    mmp.put("count",count);
                    totalcount += count;
                    mmp.put("zdrqCount",zdrqCount);
                    totalZdrqCount += zdrqCount;
                }*/
                mmp.put("count",count);
                totalcount += count;
                mmp.put("zdrqCount",zdrqCount);
                totalZdrqCount += zdrqCount;
               listMap.add(mmp);
            }
            returnMap.put("listMap",listMap);
            returnMap.put("totalCount",totalcount);
            returnMap.put("totalZdrqCount",totalZdrqCount);
        }
        return returnMap;
    }
    public int getFindSignByMin(CdAddress address,ResidentVo qvo){
        int count = 0;
        int zdrqCount = 0;
        Map<String,Object> map = new HashMap<>();
        map.put("startTime",qvo.getYearStart());
        map.put("endTime",qvo.getYearEnd());
        map.put("areaCode",AreaUtils.getAreaCode(address.getCtcode())+"%");
        String[] fwState ={ResidentMangeType.PTRQ.getValue(),ResidentMangeType.WEIZHI.getValue()};
        map.put("SIGN_PERS_GROUP",fwState);
        map.put("signState",new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()});
        String sql = "SELECT COUNT(1) FROM APP_SIGN_FORM WHERE SIGN_FROM_DATE >=:startTime" +
                " AND SIGN_FROM_DATE<=:endTime AND SIGN_STATE IN (:signState) " +
                "AND SIGN_AREA_CODE LIKE :areaCode";
        count = sysDao.getServiceReadDo().findCount(sql,map);
        return count;
    }

    @Override
    public Map<String, Object> findChronicDisease(ResidentVo qvo) throws Exception {
        Map<String,Object> returnMap = new HashMap<>();
        List<Map<String,Object>> list1 = new ArrayList<>();
        List<Map<String,Object>> list2 = new ArrayList<>();
        List<Map<String,Object>> list3 = new ArrayList<>();
        if(StringUtils.isNotBlank(qvo.getAreaId())) {
            List<CdAddress> lsCdAddress = sysDao.getCdAddressDao().findUpCasheAreaCode(qvo.getAreaId());
            if(lsCdAddress!=null && !lsCdAddress.isEmpty()) {
                for (CdAddress address : lsCdAddress) {
                    if(address.getId().indexOf("3501")!=-1||address.getId().indexOf("3504")!=-1
                            ||address.getId().indexOf("3503")!=-1||address.getId().indexOf("3507")!=-1||address.getId().indexOf("3506")!=-1
                            ||address.getId().indexOf("3505")!=-1) {
                        Map<String,Object> gxyMap = new HashMap<>();
                        Map<String,Object> tnbMap = new HashMap<>();
                        Map<String,Object> zhMap = new HashMap<>();
                        Map<String,Object> mmp = getFindChronicDisease(address,null,qvo);
                        gxyMap.put("codeId",address.getId());
                        gxyMap.put("hospId",null);
                        gxyMap.put("areaName",address.getAreaSname());
                        gxyMap.put("areaRank",mmp.get("gxyRate"));
                        list1.add(gxyMap);
                        tnbMap.put("codeId",address.getId());
                        tnbMap.put("hospId",null);
                        tnbMap.put("areaName",address.getAreaSname());
                        tnbMap.put("areaRank",mmp.get("tnbRate"));
                        list2.add(tnbMap);
                        zhMap.put("codeId",address.getId());
                        zhMap.put("hospId",null);
                        zhMap.put("areaName",address.getAreaSname());
                        zhMap.put("areaRank",mmp.get("zhRate"));
                        list3.add(zhMap);
                    }

                }
            }
        }
        Collections.sort(list1, ComparatorUtils.getComparator());
        Collections.sort(list2, ComparatorUtils.getComparator());
        Collections.sort(list3, ComparatorUtils.getComparator());
        returnMap.put("gxyMap",list1);
        returnMap.put("tnbMap",list2);
        returnMap.put("zhMap",list3);
        return returnMap;
    }

    public Map<String,Object> getFindChronicDisease(CdAddress address, AppTeam team, ResidentVo qvo){
        Map<String,Object> returnMap = new HashMap<>();
        int gxyCount = 0;//高血压随访数
        int tnbCount = 0;//糖尿病随访数
        int gxyPCount = 0;//高血压签约人数
        int tnbPCount = 0;//糖尿病签约人数
        Map<String,Object> map = new HashMap<>();
        map.put("startTime",qvo.getYearStart());
        map.put("endTime",qvo.getYearEnd());
        String sql = "SELECT " +
                "SUM(PRC_DIABETES_COUNT) prcDiabetesCount," +
                "SUM(PRC_HYPERTENSION_COUNT) prcHypertensionCount," +
                "SUM(PRC_TOTAL_COUNT) prcTotalCount," +
                "SUM(PRC_DIABETES_PATIENT_COUNT) prcDiabetesPatientCount," +
                "SUM(PRC_HYPERTENSION_PATIENT_COUNT) prcHypertensionPatientCount," +
                "SUM(PRC_TOTAL_PATIENT_COUNT) prcTotalPatientCount " +
                "FROM APP_PERFORME_RANK_COUNT " +
                "WHERE 1=1 AND PRC_YEAR_MONTH>=:startTime AND PRC_YEAR_MONTH<=:endTime";
        if(address != null){
            map.put("areaCode",AreaUtils.getAreaCode(address.getCtcode())+"%");
            sql += " AND PRC_AREA_CODE LIKE :areaCode";
        }
        if(team != null){
            map.put("teamId",team.getId());
            sql += " AND PRC_TEAM_ID =:teamId";
        }
        List<Map> maps = this.sysDao.getServiceReadDo().findSqlMap(sql,map);
        if(maps != null && maps.size() >0){
            if(maps.get(0).get("prcDiabetesCount") != null) {
                tnbCount = (int) Double.parseDouble(maps.get(0).get("prcDiabetesCount").toString());//糖尿病随访数
            }
            if(maps.get(0).get("prcHypertensionCount") != null){
                gxyCount = (int)Double.parseDouble(maps.get(0).get("prcHypertensionCount").toString());//高血压随访数
            }
            if(maps.get(0).get("prcDiabetesPatientCount") != null){
                tnbPCount = (int)Double.parseDouble(maps.get(0).get("prcDiabetesPatientCount").toString());//糖尿病签约人数
            }
            if(maps.get(0).get("prcHypertensionPatientCount") != null){
                gxyPCount = (int)Double.parseDouble(maps.get(0).get("prcHypertensionPatientCount").toString());//高血压签约人数
            }
        }
        //高血压完成率
        BigDecimal gxyRate = new BigDecimal(0);
        if(gxyCount > 0 ){
            gxyRate = new BigDecimal(gxyPCount*100).divide(new BigDecimal(gxyCount),0,BigDecimal.ROUND_HALF_UP);
        }
        //糖尿病完成率
        BigDecimal tnbRate = new BigDecimal(0);
        if(tnbCount > 0 ){
            tnbRate = new BigDecimal(tnbPCount*100).divide(new BigDecimal(tnbCount),0,BigDecimal.ROUND_HALF_UP);
        }
        String gxyRates = String.valueOf(gxyRate);
        if(gxyRates.length()>2){
            gxyRates = gxyRates.substring(0,2);
        }
        String tnbRates = String.valueOf(tnbRate);
        if(tnbRates.length()>2){
            tnbRates = tnbRates.substring(0,2);
        }
        returnMap.put("gxyCount",gxyCount);
        returnMap.put("tnbCount",tnbCount);
        returnMap.put("gxyPCount",gxyPCount);
        returnMap.put("tnbPCount",tnbPCount);
        returnMap.put("gxyRate",new BigDecimal(Integer.parseInt(gxyRates)));
        returnMap.put("tnbRate",new BigDecimal(Integer.parseInt(tnbRates)));
        //综合完成率
        BigDecimal zhRate = new BigDecimal(0);
        BigDecimal gxyRatee = new BigDecimal(Integer.parseInt(gxyRates));
        BigDecimal tnbRatee = new BigDecimal(Integer.parseInt(tnbRates));
        zhRate = (gxyRatee.add(tnbRatee)).divide(new BigDecimal(2),0,BigDecimal.ROUND_HALF_UP);
        returnMap.put("zhRate",zhRate);
        return returnMap;
    }

    @Override
    public List<ManageTeamCountEntity> findTeamCount(ResidentVo qvo) throws Exception {
        Map<String,Object> returnMap = new HashMap<>();
        Map<String,Object> map = new HashMap<>();
        map.put("startTime",qvo.getYearStart());
        map.put("endTime",qvo.getYearEnd());
        map.put("areaCode",AreaUtils.getAreaCode(qvo.getAreaId())+"%");
        String sql = "SELECT \n" +
                "a.rate rate,\n" +
                "b.TEAM_NAME teamName,\n" +
                "b.TEAM_HOSP_NAME hospName,\n" +
                "a.PRC_AREA_CODE address\n" +
                " FROM (\n" +
                "SELECT\n" +
                "\tavg(PRC_RATE) rate,\n" +
                "\tPRC_TEAM_ID,\n" +
                "\tPRC_AREA_CODE\n" +
                "FROM\n" +
                "\tapp_performe_rank_count\n" +
                "WHERE\n" +
                "\tPRC_AREA_CODE LIKE :areaCode\n" +
                "AND PRC_YEAR_MONTH >= :startTime\n" +
                "AND PRC_YEAR_MONTH <= :endTime\n" +
                "GROUP BY PRC_TEAM_ID) a INNER JOIN app_team b ON a.PRC_TEAM_ID = b.ID WHERE 1=1 AND b.TEAM_DEL_STATE = '0' ORDER BY a.rate+0 DESC LIMIT 20";
        List<ManageTeamCountEntity> list = this.sysDao.getServiceReadDo().findSqlMapRVo(sql,map,ManageTeamCountEntity.class);
        Collections.sort(list, ComparatorUtils.getComparatorList());
        return list;
    }

    /**
     * 重点人群签约数
     * @param address
     * @param qvo
     * @return
     */
    public int getFindSignByMinZdrq(CdAddress address,ResidentVo qvo){
        int count = 0;
        Map<String,Object> map = new HashMap<>();
        map.put("startTime",qvo.getYearStart());
        map.put("endTime",qvo.getYearEnd());
        map.put("areaCode",AreaUtils.getAreaCode(address.getCtcode())+"%");
        String[] fwState ={ResidentMangeType.PTRQ.getValue(),ResidentMangeType.WEIZHI.getValue()};
        map.put("SIGN_PERS_GROUP",fwState);
        map.put("signState",new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()});
        String sqlZdrq = "SELECT\n" +
                "\tcount(1)\n" +
                "FROM\n" +
                "\t(\n" +
                "\t\tSELECT\n" +
                "\t\t\tt.*\n" +
                "\t\tFROM\n" +
                "\t\t\tAPP_SIGN_FORM t\n" +
                "\t\tLEFT JOIN APP_LABEL_GROUP b ON t.ID = b.LABEL_SIGN_ID\n" +
                "\t\tWHERE\n" +
                "\t\t\t1 = 1\n" +
                "\t\tAND b.LABEL_TYPE = '3'\n" +
                "\t\tAND b.LABEL_VALUE NOT IN (:SIGN_PERS_GROUP)\n" +
                "\t\tAND t.SIGN_STATE IN (:signState)\n" +
                "\t\tAND t.SIGN_FROM_DATE >= :startTime\n" +
                "\t\tAND t.SIGN_FROM_DATE <= :endTime\n" +
                "\t\tAND t.SIGN_AREA_CODE LIKE :areaCode\n" +
                "\t\tGROUP BY\n" +
                "\t\t\tt.SIGN_PATIENT_ID\n" +
                "\t) c";
        count = sysDao.getServiceReadDo().findCount(sqlZdrq,map);
        return count;
    }

    /**
     * 慢病统计
     * @param qvo
     * @return
     */
    @Override
    public Map<String, Object> findChronic(ResidentVo qvo) throws Exception {

        int totalSign = 0;//总签约数
        int mbCount = 0;//慢病人数
        int gxyCount = 0;//高血压人数
        int tnbCount = 0;//糖尿病人数
        int gtCount = 0;//既是高血压又是糖尿病人数

        int gxyRedCount = 0;//高血压人数（红）
        int gxyYellowCount = 0;//高血压人数（黄）
        int gxyGreenCount = 0;//高血压人数（绿）
        int gxyGrayCount = 0;//高血压人数（灰）

        int tnbRedCount = 0;//糖尿病人数（红）
        int tnbYellowCount = 0;//糖尿病人数（黄）
        int tnbGreenCount = 0;//糖尿病人数（绿）
        int tnbGrayCount = 0;//糖尿病人数（灰）

        int ybCount = 0;//慢病一般人口
        int jdCount = 0;//慢病建档立卡人口
        int dbCount = 0;//慢病低保户人口
        int tkCount = 0;//慢病特困户人口
        int jsCount = 0;//慢病计生人口
        HashMap rmap = new HashMap();
        HashMap map = new HashMap();
        map.put("yearStart", ExtendDate.getFirstDayOfMonth(qvo.getYearStart())+" 00:00:00");
        map.put("yearEnd",ExtendDate.getLastDayOfMonth(qvo.getYearEnd())+" 23:59:59");
        String[] signStates = {SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
        map.put("SIGN_STATE", signStates);
        map.put("fwValue",new String[]{ResidentMangeType.GXY.getValue(),ResidentMangeType.TNB.getValue()});
        map.put("disValue",new String[]{DiseaseType.TNB.getValue(),DiseaseType.GXY.getValue()});
        map.put("gxyValue",ResidentMangeType.GXY.getValue());
        map.put("tnbValue",ResidentMangeType.TNB.getValue());
        map.put("gxyDisValue",DiseaseType.GXY.getValue());
        map.put("tnbDisValue",DiseaseType.TNB.getValue());
        map.put("redValue","red");
        map.put("yellowValue","yellow");
        map.put("greenValue","green");
        map.put("grayValue","gray");

//        String
        String ss = " AND a.SIGN_STATE IN (:SIGN_STATE) AND a.SIGN_FROM_DATE >= :yearStart AND a.SIGN_FROM_DATE <= :yearEnd ";
        if (StringUtils.isNotBlank(qvo.getHospId())) {
            ss += " AND a.SIGN_HOSP_ID =:SIGN_HOSP_ID";
            map.put("SIGN_HOSP_ID", qvo.getHospId());
        }
        if (StringUtils.isNotBlank(qvo.getTeamId())) {
            ss += " AND a.SIGN_TEAM_ID =:SIGN_TEAM_ID";
            map.put("SIGN_TEAM_ID",qvo.getTeamId());

        }
        if (StringUtils.isNotBlank(qvo.getDrId())) {
            ss += " AND a.SIGN_DR_ID =:SIGN_DR_ID ";
            map.put("SIGN_DR_ID",qvo.getDrId());
        }
        //总签约
        String allSql = " SELECT a.* FROM APP_SIGN_FORM a WHERE 1=1 " +ss;
        String sss = " SELECT COUNT(1) FROM ("+allSql+" GROUP BY a.ID ) cc ";
        totalSign = sysDao.getServiceDo().findCount(sss,map);
        rmap.put("manageSignCount",totalSign);

        //慢病人数
        String mbSql = "SELECT a.* FROM APP_SIGN_FORM a INNER JOIN APP_LABEL_GROUP b ON a.ID = b.LABEL_SIGN_ID WHERE 1=1 AND b.LABEL_TYPE = '3' AND b.LABEL_VALUE IN (:fwValue) "+ ss;
        String mbSqls = " SELECT COUNT(1) FROM ("+mbSql+" GROUP BY a.ID ) cc ";
        mbCount = sysDao.getServiceDo().findCount(mbSqls,map);
        rmap.put("manageNcdCount",mbCount);
        //高血压或糖尿病人数
        String oneSql = "SELECT a.* , " +
                "(SELECT GROUP_CONCAT(LABEL_VALUE) FROM app_label_group WHERE LABEL_TYPE = '3' AND LABEL_SIGN_ID = a.ID AND LABEL_VALUE IN (:fwValue)) labelValue," +
                "(SELECT GROUP_CONCAT(LABEL_COLOR) FROM app_label_disease WHERE LABEL_TYPE = '2' AND LABEL_SIGN_ID = a.ID AND LABEL_VALUE IN (:disValue)) labelColor " +
                " FROM app_sign_form a WHERE 1=1 "+ss;

        //只有高血压人群
        String hbpSql = "SELECT COUNT(1) FROM ("+oneSql+" group By a.Id) cc WHERE cc.labelValue = :gxyValue ";
        gxyCount = sysDao.getServiceDo().findCount(hbpSql,map);
        rmap.put("manageBloodCount",gxyCount);
        //只有糖尿病人群
        String bmSql = "SELECT COUNT(1) FROM ("+oneSql+" group By a.Id) cc WHERE cc.labelValue = :tnbValue ";
        tnbCount = sysDao.getServiceDo().findCount(bmSql,map);
        rmap.put("manageDiabetesCount",tnbCount);
        //既有高血压又有糖尿病人群
        String gtSql = "SELECT COUNT(1) FROM ("+oneSql+" group by a.ID) cc WHERE find_in_set(:gxyValue,cc.labelValue) AND find_in_set(:tnbValue,cc.labelValue) ";
        gtCount = sysDao.getServiceDo().findCount(gtSql,map);
        rmap.put("manageDisBloodCount",gtCount);
        //有高血压
        String hGxySql =  "SELECT a.* , " +
                "(SELECT GROUP_CONCAT(LABEL_VALUE) FROM app_label_group WHERE LABEL_TYPE = '3' AND LABEL_SIGN_ID = a.ID AND LABEL_VALUE =:gxyValue) labelValue," +
                "(SELECT GROUP_CONCAT(LABEL_COLOR) FROM app_label_disease WHERE LABEL_TYPE = '2' AND LABEL_SIGN_ID = a.ID AND LABEL_VALUE =:gxyDisValue) labelColor " +
                " FROM app_sign_form a WHERE 1=1 "+ss;
        //高血压红标数
        String gxyRSql = "SELECT COUNT(1) FROM ("+hGxySql+" group by a.ID) cc where cc.labelValue =:gxyValue AND cc.labelColor = :redValue";
        gxyRedCount = sysDao.getServiceDo().findCount(gxyRSql,map);
        rmap.put("manageBloodRedCount",gxyRedCount);
        //高血压黄标数
        String gxyYSql = "SELECT COUNT(1) FROM ("+hGxySql+" group by a.ID) cc where cc.labelValue =:gxyValue AND cc.labelColor = :yellowValue";
        gxyYellowCount = sysDao.getServiceDo().findCount(gxyYSql,map);
        rmap.put("manageBloodYellowCount",gxyYellowCount);
        //高血压绿标数
        String gxyGSql = "SELECT COUNT(1) FROM ("+hGxySql+" group by a.ID) cc where cc.labelValue =:gxyValue AND cc.labelColor = :greenValue";
        gxyGreenCount = sysDao.getServiceDo().findCount(gxyGSql,map);
        rmap.put("manageBloodGreenCount",gxyGreenCount);
        //高血压灰标数
        String gxyGrSql = "SELECT COUNT(1) FROM ("+hGxySql+" group by a.ID) cc where cc.labelValue =:gxyValue AND (cc.labelColor =:grayValue OR cc.labelColor is null )";
        gxyGrayCount = sysDao.getServiceDo().findCount(gxyGrSql,map);
        rmap.put("manageBloodGrayCount",gxyGrayCount);
        //有糖尿病
        String hTnbSql =  "SELECT a.* , " +
                "(SELECT GROUP_CONCAT(LABEL_VALUE) FROM app_label_group WHERE LABEL_TYPE = '3' AND LABEL_SIGN_ID = a.ID AND LABEL_VALUE =:tnbValue) labelValue," +
                "(SELECT GROUP_CONCAT(LABEL_COLOR) FROM app_label_disease WHERE LABEL_TYPE = '2' AND LABEL_SIGN_ID = a.ID AND LABEL_VALUE =:tnbDisValue) labelColor " +
                " FROM app_sign_form a WHERE 1=1 "+ss;
        //糖尿病红标数
        String tnbRSql = "SELECT COUNT(1) FROM ("+hTnbSql+" group by a.ID) cc where cc.labelValue =:tnbValue AND cc.labelColor =:redValue";
        tnbRedCount = sysDao.getServiceDo().findCount(tnbRSql,map);
        rmap.put("manageDiabetesRedCount",tnbRedCount);
        //糖尿病黄标
        String tnbYSql = "SELECT COUNT(1) FROM ("+hTnbSql+" group by a.ID) cc where cc.labelValue =:tnbValue and cc.labelColor =:yellowValue";
        tnbYellowCount = sysDao.getServiceDo().findCount(tnbYSql,map);
        rmap.put("manageDiabetesYellowCount",tnbYellowCount);
        //糖尿病绿标
        String tnbGSql = "select count(1) from ("+hTnbSql+" group by a.id) cc where cc.labelValue =:tnbValue and cc.labelColor =:greenValue";
        tnbGreenCount = sysDao.getServiceDo().findCount(tnbGSql,map);
        rmap.put("manageDiabetesGreenCount",tnbGreenCount);
        //糖尿病灰标
        String tnbGrSql = "SELECT COUNT(1) FROM ("+hTnbSql+" group by a.ID) cc where cc.labelValue =:tnbValue AND (cc.labelColor =:grayValue OR cc.labelColor is null )";
        tnbGrayCount = sysDao.getServiceDo().findCount(tnbGrSql,map);
        rmap.put("manageDiabetesGrayCount",tnbGrayCount);

        //慢病经济类型
        String jjSql = "SELECT\n" +
                "\ta.*\n" +
                "FROM\n" +
                "\tAPP_SIGN_FORM a\n" +
                "INNER JOIN APP_LABEL_GROUP b ON a.ID = b.LABEL_SIGN_ID\n" +
                "INNER JOIN app_label_economics c ON a.ID = c.LABEL_SIGN_ID\n" +
                "WHERE\n" +
                "\t1 = 1\n" +
                "AND b.LABEL_TYPE = '3'\n" +
                "AND b.LABEL_VALUE IN (:fwValue)\n" +
                "AND c.LABEL_VALUE =:jjValue "+ ss;
        List<AppLabelManage> lisM = sysDao.getAppLabelManageDao().findByType("4");
        if(lisM != null){
            String jjSignSql = "SELECT COUNT(1) FROM ("+jjSql+" GROUP by a.ID) cc";
            for (AppLabelManage lm:lisM){
                map.put("jjValue",lm.getLabelValue());
                int count = sysDao.getServiceDo().findCount(jjSignSql,map);
                if(EconomicType.YBRK.getValue().equals(lm.getLabelValue())){
                    rmap.put("manageGeneralPopulationCount",count);
                }else if(EconomicType.JDLKPKRK.getValue().equals(lm.getLabelValue())){
                    rmap.put("managePoorFamilyCount",count);
                }else if(EconomicType.DBH.getValue().equals(lm.getLabelValue())){
                    rmap.put("manageLowFamilyCount",count);
                }else if(EconomicType.TKH.getValue().equals(lm.getLabelValue())){
                    rmap.put("manageDestituteFamilyCount",count);
                }else if(EconomicType.JSTSJT.getValue().equals(lm.getLabelValue())){
                    rmap.put("manageSpecialPlanFamilyCount",count);
                }
            }
        }
        return rmap;
    }

    @Override
    public Map<String, Object> findManageTeam(ResidentVo qvo) throws Exception {
        //取团队id，查询该团队是否是签约团队、成员数、签约成员数
        Map<String,Object> returnMap = new HashMap<>();
        Map<String,Object> map = new HashMap<>();
        map.put("teamId",qvo.getTeamId());
        map.put("SIGN_STATE",new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()});
        map.put("STATE","1");
        String sqlSign = "SELECT count(1) FROM app_sign_form WHERE SIGN_STATE IN (:SIGN_STATE) AND SIGN_TEAM_ID = :teamId";
        int signCount = sysDao.getServiceReadDo().findCount(sqlSign,map);
        if(signCount >0){
            returnMap.put("manageSignState","1");
        }else{
            returnMap.put("manageSignState","0");
        }
        //团队成员数量
        String sqlMem = "SELECT a.ID," +
                "(select count(1) FROM APP_SIGN_FORM where SIGN_STATE IN (:SIGN_STATE) and SIGN_DR_ID = a.ID ) signCount " +
                " FROM app_dr_user a INNER JOIN app_team_member b ON a.ID = b.MEM_DR_ID\n" +
                "WHERE b.MEM_TEAMID = :teamId\n" +
                "AND a.DR_STATE =:STATE";
        String sqlMemCount = "SELECT COUNT(1) FROM ("+sqlMem+" group by a.id) cc ";
        int memCount = sysDao.getServiceReadDo().findCount(sqlMemCount,map);
        returnMap.put("manageMemberCount",memCount);
        String sqlSignDr = "SELECT COUNT(1) FROM ("+sqlMem+" group by a.id) cc where cc.signCount >0 ";
        int signDrCount =  sysDao.getServiceReadDo().findCount(sqlSignDr,map);
        returnMap.put("manageSignMemberCount",signDrCount);
        return returnMap;
    }

    /**
     * 按行政区划级别各级展示 签约总数，一般人群签约数，高血压签约数，
     * 糖尿病签约数、老年人签约数、孕产妇人群签约数，儿童签约数、
     * 严重精神障碍签约数、肺结核签约数；
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> findSignManage(ResidentVo qvo) throws Exception {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<String,Object>();
        if(StringUtils.isNotBlank(qvo.getAreaId())){
            if (AreaUtils.getAreaCode(qvo.getAreaId()).length() == 6 || AreaUtils.getAreaCode(qvo.getAreaId()).length() == 5) { //区级行政，往下查看医院的排名
                //区下的医院集合
                List<AppHospDept> hospDepts = sysDao.getAppHospDeptDao().findUpHospListRead(qvo.getAreaId());
                if (hospDepts != null && hospDepts.size() > 0) {
                    for (AppHospDept hosp : hospDepts) {
                        if (hosp != null) {
                            if (hosp.getHospState().equals("1")) {
                                map = getSignCount(null, hosp,qvo.getYearStart(),qvo.getYearEnd());
                                list.add(map);
                            }
                        }
                    }
                }
            }else{
                List<CdAddress> lsCdAddress = sysDao.getCdAddressDao().findUpCasheAreaCode(qvo.getAreaId());
                if(lsCdAddress != null && lsCdAddress.size()>0) {
                    for (CdAddress address : lsCdAddress) {
                        String code = address.getCtcode().substring(0, 4);
                        if (AddressType.FZS.getValue().equals(code) || AddressType.SMS.getValue().equals(code) || AddressType.ZZS.getValue().equals(code) ||
                                AddressType.QZS.getValue().equals(code) || AddressType.PTS.getValue().equals(code) || AddressType.NPS.getValue().equals(code)) {
                            map = getSignCount(address, null,qvo.getYearStart(),qvo.getYearEnd());
                            list.add(map);
                        }
                    }
                }
            }
        }
        /*else if(StringUtils.isNotBlank(qvo.getHospId())){
            AppHospDept hosp = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,qvo.getHospId());
            if(hosp != null){
                map = getSignCount(null, hosp,qvo.getYearStart(),qvo.getYearEnd());
                list.add(map);
            }
        }*/
        return list;
    }

    /**
     * 按行政区划级别各级展示 签约总数，一般人群签约数，
     * 高血压签约数，
     * 糖尿病签约数、老年人签约数、孕产妇人群签约数，儿童签约数、
     * 严重精神障碍签约数、肺结核签约数；
     * @param address
     * @param hosp
     * @param yearStart
     * @param yearEnd
     * @return
     */
    public Map<String,Object> getSignCount(CdAddress address,AppHospDept hosp,String yearStart,String yearEnd) throws Exception{
        Map<String,Object> rMap = new HashMap<>();
        Map<String,Object> map = new HashMap<>();
        int signCount = 0;//签约总数
        int signPtCount = 0;//一般人群签约数
        int signGxyCount = 0;//高血压签约数
        int signTnbCount = 0;//糖尿病签约数
        int signLnrCount = 0;//老年人签约数
        int signYcfCount = 0;//孕产妇签约数
        int signEtCount = 0;//儿童签约数
        int signJsbCount = 0;//严重精神障碍签约数
        int signFjhCount = 0;//肺结核签约数
        int signCjrCount = 0;//残疾人签约数

        String sqlTeam = " ";
        String sql = "SELECT " +
                "SUM(t.MANAGE_SIGN_COUNT) manageSignCount," +
                "SUM(t.MANAGE_PLAIN_COUNT) managePlainCount," +
                "SUM(t.MANAGE_CHILD_COUNT) manageChildCount," +
                "SUM(t.MANAGE_MATERNAL_COUNT) manageMaternalCount," +
                "SUM(t.MANAGE_OLD_PEOPLE_COUNT) manageOldPeopleCount," +
                "SUM(t.MANAGE_BLOOD_COUNT) manageBloodCount," +
                "SUM(t.MANAGE_DIABETES_COUNT) manageDiabetesCount," +
                "SUM(t.MANAGE_PSYCHOSIS_COUNT) managePsychosisCount," +
                "SUM(t.MANAGE_PHTHISIS_COUNT) managePhthisisCount," +
                "SUM(t.MANAGE_DISABLED_PEOPLE_COUNT) manageDisabledPeopleCount," +
                "SUM(t.MANAGE_SERVICE_UNKNOWN_COUNT) manageServiceUnknownCount " +
                " FROM APP_MANAGE_COUNT t where 1=1 ";
        if(StringUtils.isNotBlank(yearStart)){
            map.put("STARTDATE",yearStart);
//            map.put("startTime",ExtendDate.getFirstDayOfMonth(yearStart) + " 00:00:00");
            sql += " AND t.MANAGE_YEAR_MONTH >= :STARTDATE ";

        }
        boolean flag = false;
        if(StringUtils.isNotBlank(yearEnd)){
            map.put("ENDDATE",yearEnd);
//            map.put("endTime",ExtendDate.getLastDayOfMonth(yearEnd) + " 23:59:59");
            sql += "  AND t.MANAGE_YEAR_MONTH <= :ENDDATE ";
            Calendar calendar = Calendar.getInstance();
            int nowYear = calendar.get(Calendar.YEAR);
            int nowMonth = calendar.get(Calendar.MONTH) + 1;
            int now = Integer.parseInt(String.valueOf(nowYear)+ String.valueOf(nowMonth));
            String[] year = yearEnd.split("-");
            int endYear = Integer.parseInt(year[0]);
            int endMonth = Integer.parseInt(year[1]);
            int end = Integer.parseInt(String.valueOf(endYear)+ String.valueOf(endMonth));
            //判断是否统计已删团队数据如果查询截止年月份包含当前年月则不统计已删的团队签约数，如果不包含则不统计这个时间段已删的团队统计数

            if(end >= now){//大于即包含,去除已删团队统计数
                flag = true;
            }
        }
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
            sqlTeam += " AND aa.TEAM_HOSP_ID =:HOSP_ID ";
            rMap.put("hospId",hosp.getId());
            rMap.put("hospName",hosp.getHospName());
        }
        if(address != null){
            if("1".equals(address.getLevel())){
                map.put("AREA_CODE",AreaUtils.getAreaCode(address.getCtcode(),"1")+"%");
            }else if("2".equals(address.getLevel())){
                map.put("AREA_CODE",AreaUtils.getAreaCode(address.getCtcode(),"2")+"%");
            }else if("3".equals(address.getLevel())){
                map.put("AREA_CODE",AreaUtils.getAreaCode(address.getCtcode(),"3")+"%");
            }else if("4".equals(address.getLevel())){
                map.put("AREA_CODE",AreaUtils.getAreaCode(address.getCtcode(),"4")+"%");
            }

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
            rMap.put("areaCode",address.getCtcode());
            rMap.put("areaName",address.getAreaSname());
        }

        //是否去除已删团队签约统计数
        sql += " AND t.MANAGE_TEAM_ID NOT IN ("+sqlTeam+") ";
        List<Map> maps = this.sysDao.getServiceReadDo().findSqlMap(sql,map);
        if(maps != null && maps.size() >0){
            if(maps.get(0).get("manageSignCount") != null){
                signCount = (int) Double.parseDouble(maps.get(0).get("manageSignCount").toString());//签约总数
            }
            if(maps.get(0).get("managePlainCount") != null){
                signPtCount = (int) Double.parseDouble(maps.get(0).get("managePlainCount").toString());
            }
            if(maps.get(0).get("manageChildCount") != null){
                signEtCount = (int) Double.parseDouble(maps.get(0).get("manageChildCount").toString());
            }
            if(maps.get(0).get("manageMaternalCount") != null){
                signYcfCount = (int) Double.parseDouble(maps.get(0).get("manageMaternalCount").toString());
            }
            if(maps.get(0).get("manageOldPeopleCount") != null){
                signLnrCount = (int) Double.parseDouble(maps.get(0).get("manageOldPeopleCount").toString());
            }
            if(maps.get(0).get("manageBloodCount") != null){
                signGxyCount = (int) Double.parseDouble(maps.get(0).get("manageBloodCount").toString());
            }
            if(maps.get(0).get("manageDiabetesCount") != null){
                signTnbCount = (int) Double.parseDouble(maps.get(0).get("manageDiabetesCount").toString());
            }
            if(maps.get(0).get("managePsychosisCount") != null){
                signJsbCount = (int) Double.parseDouble(maps.get(0).get("managePsychosisCount").toString());
            }
            if(maps.get(0).get("managePhthisisCount") != null){
                signFjhCount = (int) Double.parseDouble(maps.get(0).get("managePhthisisCount").toString());
            }
            if(maps.get(0).get("manageDisabledPeopleCount") != null){
                signCjrCount = (int) Double.parseDouble(maps.get(0).get("manageDisabledPeopleCount").toString());
            }
//            if(maps.get(0).get("manageServiceUnknownCount") != null){
//                signWzCount = (int) Double.parseDouble(maps.get(0).get("managePlainCount").toString());
//            }
        }
        //判断是否要加上今天数据
        if(flag){
            map.put("STARTDATE", ExtendDate.getYMD(Calendar.getInstance())+" 00:00:00");
            map.put("ENDDATE",ExtendDate.getYMD(Calendar.getInstance())+" 23:59:59");
            String sqlDate = " AND t.SIGN_FROM_DATE >= :STARTDATE AND t.SIGN_FROM_DATE <= :ENDDATE ";
            //签约数
            String sqlYqy = "SELECT count(1) FROM APP_SIGN_FORM t where 1=1 AND t.SIGN_STATE IN (:SIGN_STATE) "+sqlDate;
            //重点人群数
            String sqlZdrqqy = "SELECT t.* FROM APP_SIGN_FORM t LEFT JOIN APP_LABEL_GROUP b ON t.ID = b.LABEL_SIGN_ID " +
                    "where 1=1 AND t.SIGN_STATE IN (:SIGN_STATE) AND b.LABEL_TYPE='3' AND b.LABEL_VALUE =:value "+sqlDate;

            if(hosp != null){
                map.put("HOSP_ID",hosp.getId());
                sqlYqy += " AND t.SIGN_HOSP_ID = :HOSP_ID ";
                sqlZdrqqy += " AND t.SIGN_HOSP_ID = :HOSP_ID ";
            }
            if(address != null){
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
                    sqlZdrqqy += " AND t.SIGN_AREA_CODE NOT IN (:notAreaCode) ";
                }else if("350622000000".equals(address.getCtcode())){
                    map.put("notAreaCode",new String[]{"350681102000","350681501000","350622450000"});
                    sqlYqy += " AND t.SIGN_AREA_CODE NOT IN (:notAreaCode) ";
                    sqlZdrqqy += " AND t.SIGN_AREA_CODE NOT IN (:notAreaCode) ";
                }
                sqlYqy += " AND t.SIGN_AREA_CODE LIKE :AREA_CODE ";
                sqlZdrqqy += " AND t.SIGN_AREA_CODE LIKE :AREA_CODE ";

            }
            signCount += sysDao.getServiceDo().findCount(sqlYqy,map);
            List<AppLabelManage> list = sysDao.getAppLabelManageDao().findByType("3");
            if(list != null && list.size()>0){
                for(AppLabelManage manage:list){
                    map.put("value",manage.getLabelValue());
                    if(ResidentMangeType.PTRQ.getValue().equals(manage.getLabelValue())){
                        signPtCount += sysDao.getServiceDo().findCount(sqlZdrqqy,map);
                    }else if(ResidentMangeType.ETLZLS.getValue().equals(manage.getLabelValue())){
                        signEtCount += sysDao.getServiceDo().findCount(sqlZdrqqy,map);
                    }else if(ResidentMangeType.YCF.getValue().equals(manage.getLabelValue())){
                        signYcfCount += sysDao.getServiceDo().findCount(sqlZdrqqy,map);
                    }else if(ResidentMangeType.LNR.getValue().equals(manage.getLabelValue())){
                        signLnrCount += sysDao.getServiceDo().findCount(sqlZdrqqy,map);
                    }else if(ResidentMangeType.GXY.getValue().equals(manage.getLabelValue())){
                        signGxyCount += sysDao.getServiceDo().findCount(sqlZdrqqy,map);
                    }else if(ResidentMangeType.TNB.getValue().equals(manage.getLabelValue())){
                        signTnbCount += sysDao.getServiceDo().findCount(sqlZdrqqy,map);
                    }else if(ResidentMangeType.YZJSZY.getValue().equals(manage.getLabelValue())){
                        signJsbCount += sysDao.getServiceDo().findCount(sqlZdrqqy,map);
                    }else if(ResidentMangeType.JHB.getValue().equals(manage.getLabelValue())){
                        signFjhCount += sysDao.getServiceDo().findCount(sqlZdrqqy,map);
                    }else if(ResidentMangeType.CJR.getValue().equals(manage.getLabelValue())){
                        signCjrCount += sysDao.getServiceDo().findCount(sqlZdrqqy,map);
                    }
                }
            }
        }
        rMap.put("signCount",signCount);
        rMap.put("signPtCount",signPtCount);
        rMap.put("signGxyCount",signGxyCount);
        rMap.put("signTnbCount",signTnbCount);
        rMap.put("signLnrCount",signLnrCount);
        rMap.put("signYcfCount",signYcfCount);
        rMap.put("signEtCount",signEtCount);
        rMap.put("signJsbCount",signJsbCount);
        rMap.put("signFjhCount",signFjhCount);
        rMap.put("signCjrCount",signCjrCount);

        return rMap;
    }
}

