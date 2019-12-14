package com.ylz.bizDo.web.dao;

import com.ylz.bizDo.app.po.AppLabelManage;
import com.ylz.bizDo.mangecount.dao.AppResidentAnalysisDao;
import com.ylz.bizDo.mangecount.entity.ManageCountEntity;
import com.ylz.bizDo.mangecount.vo.ResidentVo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.comEnum.SignFormType;
import com.ylz.packcommon.common.util.AreaUtils;
import com.ylz.packcommon.common.util.ExtendDate;
import com.ylz.packcommon.common.util.JsonUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 居民分析
 */
@Service("webResidentAnalysisDao")
@Transactional(rollbackForClassName={"Exception"})
public class WebResidentAnalysisDaoImpl implements WebResidentAnalysisDao {

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
//            List<Map> maps = this.sysDao.getServiceDo().findSqlMap(sql,map);
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
//            int yjf=sysDao.getServiceDo().findCount(sqlyjf,map);
//            int wjf=sysDao.getServiceDo().findCount(sqlwjf,map);
//            rmap.put("已缴费人数",yjf);
//            rmap.put("未缴费人数",wjf);
//            return rmap;
//        }

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
        List<Map> maps = this.sysDao.getServiceDo().findSqlMap(sql,map);
        if(maps != null && maps.size() >0){
            if(maps.get(0).get("unPaidCount") != null) {
                rmap.put("未缴费人数",(int)Double.parseDouble(maps.get(0).get("unPaidCount").toString()));
            }
            if(maps.get(0).get("aleradyPaidCount") != null) {
                rmap.put("已缴费人数",(int)Double.parseDouble(maps.get(0).get("aleradyPaidCount").toString()));
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
        List<AppLabelManage> ls=sysDao.getServiceDo().loadByPk(AppLabelManage.class,"labelType","1");
        if(ls!=null && !ls.isEmpty()) {
            HashMap map = new HashMap();
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
//                List<Map> maps = this.sysDao.getServiceDo().findSqlMap(sql,map);
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
//                    int  count= sysDao.getServiceDo().findCount(sql, map);
//                    rmap.put(l.getLabelTitle(), count);
//                }
//                return rmap;
//            }
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
            List<Map> maps = this.sysDao.getServiceDo().findSqlMap(sql,map);
            if(maps != null && maps.size() >0){
                for(AppLabelManage l:ls) {
                    if(l.getLabelValue().equals("101")){//患病人群
                        if(maps.get(0).get("manageBeillCount") != null) {
                            rmap.put(l.getLabelTitle(),(int)Double.parseDouble(maps.get(0).get("manageBeillCount").toString()));
                        }
                    }else if(l.getLabelValue().equals("102")){//健康人群
                        if(maps.get(0).get("manageHealthCount") != null) {
                            rmap.put(l.getLabelTitle(),(int)Double.parseDouble(maps.get(0).get("manageHealthCount").toString()));
                        }
                    }else if(l.getLabelValue().equals("103")){//高危人群
                        if(maps.get(0).get("manageHighRiskCount") != null) {
                            rmap.put(l.getLabelTitle(),(int)Double.parseDouble(maps.get(0).get("manageHighRiskCount").toString()));
                        }
                    }else if(l.getLabelValue().equals("104")){//恢复期人群
                        if(maps.get(0).get("manageConvalescenceCount") != null) {
                            rmap.put(l.getLabelTitle(),(int)Double.parseDouble(maps.get(0).get("manageConvalescenceCount").toString()));
                        }
                    }else if(l.getLabelValue().equals("199")){//未知
                        if(maps.get(0).get("manageHealthUnknownCount") != null) {
                            rmap.put(l.getLabelTitle(),(int)Double.parseDouble(maps.get(0).get("manageHealthUnknownCount").toString()));
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
//                List<Map> maps = this.sysDao.getServiceDo().findSqlMap(sql,map);
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
//                    int  count= sysDao.getServiceDo().findCount(sql, map);
//                    rmap.put(l.getLabelTitle(), count);
//                }
//                return rmap;
//            }

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
            List<Map> maps = this.sysDao.getServiceDo().findSqlMap(sql,map);
            if(maps != null && maps.size() >0){
                for(AppLabelManage l:ls) {
                    if(l.getLabelValue().equals("1")){//普通人群
                        if(maps.get(0).get("managePlainCount") != null) {
                            rmap.put(l.getLabelTitle(),(int)Double.parseDouble(maps.get(0).get("managePlainCount").toString()));
                        }
                    }else if(l.getLabelValue().equals("2")){//儿童(0-6岁)
                        if(maps.get(0).get("manageChildCount") != null) {
                            rmap.put(l.getLabelTitle(),(int)Double.parseDouble(maps.get(0).get("manageChildCount").toString()));
                        }
                    }else if(l.getLabelValue().equals("3")){//孕产妇
                        if(maps.get(0).get("manageMaternalCount") != null) {
                            rmap.put(l.getLabelTitle(),(int)Double.parseDouble(maps.get(0).get("manageMaternalCount").toString()));
                        }
                    }else if(l.getLabelValue().equals("4")){//老年人
                        if(maps.get(0).get("manageOldPeopleCount") != null) {
                            rmap.put(l.getLabelTitle(),(int)Double.parseDouble(maps.get(0).get("manageOldPeopleCount").toString()));
                        }
                    }else if(l.getLabelValue().equals("5")){//高血压
                        if(maps.get(0).get("manageBloodCount") != null) {
                            rmap.put(l.getLabelTitle(),(int)Double.parseDouble(maps.get(0).get("manageBloodCount").toString()));
                        }
                    }else if(l.getLabelValue().equals("6")){//糖尿病
                        if(maps.get(0).get("manageDiabetesCount") != null) {
                            rmap.put(l.getLabelTitle(),(int)Double.parseDouble(maps.get(0).get("manageDiabetesCount").toString()));
                        }
                    }else if(l.getLabelValue().equals("7")){//严重精神障碍
                        if(maps.get(0).get("managePsychosisCount") != null) {
                            rmap.put(l.getLabelTitle(),(int)Double.parseDouble(maps.get(0).get("managePsychosisCount").toString()));
                        }
                    }else if(l.getLabelValue().equals("8")){//结核病
                        if(maps.get(0).get("managePhthisisCount") != null) {
                            rmap.put(l.getLabelTitle(),(int)Double.parseDouble(maps.get(0).get("managePhthisisCount").toString()));
                        }
                    }else if(l.getLabelValue().equals("9")){//残疾人
                        if(maps.get(0).get("manageDisabledPeopleCount") != null) {
                            rmap.put(l.getLabelTitle(),(int)Double.parseDouble(maps.get(0).get("manageDisabledPeopleCount").toString()));
                        }
                    }else if(l.getLabelValue().equals("99")){//未知
                        if(maps.get(0).get("manageServiceUnknownCount") != null) {
                            rmap.put(l.getLabelTitle(),(int)Double.parseDouble(maps.get(0).get("manageServiceUnknownCount").toString()));
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
//            if (StringUtils.isNotBlank(qvo.getTeamId())) {
                map.put("yearStart", ExtendDate.getFirstDayOfMonth(qvo.getYearStart())+" 00:00:00");
                map.put("yearEnd",ExtendDate.getLastDayOfMonth(qvo.getYearEnd())+" 23:59:59");
                String[] state = {SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};//状态
                map.put("SIGN_STATE",state);
                String sql = "SELECT COUNT(1) FROM APP_SIGN_FORM a LEFT JOIN APP_LABEL_GROUP b on a.ID=b.LABEL_SIGN_ID WHERE a.SIGN_STATE IN (:SIGN_STATE) ";
                sql += " AND a.SIGN_FROM_DATE >= :yearStart AND a.SIGN_FROM_DATE <= :yearEnd ";
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
                sql += " AND b.LABEL_VALUE=:SIGN_PERS_GROUP";
                for(AppLabelManage l:ls) {
                    ManageCountEntity v = new ManageCountEntity();
                    if(l.getLabelValue().equals("1")){//普通人群
                    }else if(l.getLabelValue().equals("2")){//儿童(0-6岁)
                        map.put("SIGN_PERS_GROUP",l.getLabelValue());
                        int  count= sysDao.getServiceDo().findCount(sql, map);
                        v.setTitle("manageChildCount");
                        v.setValue(String.valueOf(count));
                        v.setName(l.getLabelTitle());
                        lsEntity.add(v);
                    }else if(l.getLabelValue().equals("3")){//孕产妇
                        map.put("SIGN_PERS_GROUP",l.getLabelValue());
                        int  count= sysDao.getServiceDo().findCount(sql, map);
                        v.setTitle("manageMaternalCount");
                        v.setValue(String.valueOf(count));
                        v.setName(l.getLabelTitle());
                        lsEntity.add(v);
                    }else if(l.getLabelValue().equals("4")){//老年人
                        map.put("SIGN_PERS_GROUP",l.getLabelValue());
                        int  count= sysDao.getServiceDo().findCount(sql, map);
                        v.setTitle("manageOldPeopleCount");
                        v.setValue(String.valueOf(count));
                        v.setName(l.getLabelTitle());
                        lsEntity.add(v);
                    }else if(l.getLabelValue().equals("5")){//高血压
                        map.put("SIGN_PERS_GROUP",l.getLabelValue());
                        int  count= sysDao.getServiceDo().findCount(sql, map);
                        v.setTitle("manageBloodCount");
                        v.setValue(String.valueOf(count));
                        v.setName(l.getLabelTitle());
                        lsEntity.add(v);
                    }else if(l.getLabelValue().equals("6")){//糖尿病
                        map.put("SIGN_PERS_GROUP",l.getLabelValue());
                        int  count= sysDao.getServiceDo().findCount(sql, map);
                        v.setTitle("manageDiabetesCount");
                        v.setValue(String.valueOf(count));
                        v.setName(l.getLabelTitle());
                        lsEntity.add(v);
                    }else if(l.getLabelValue().equals("7")){//严重精神障碍
                        map.put("SIGN_PERS_GROUP",l.getLabelValue());
                        int  count= sysDao.getServiceDo().findCount(sql, map);
                        v.setTitle("managePsychosisCount");
                        v.setValue(String.valueOf(count));
                        v.setName(l.getLabelTitle());
                        lsEntity.add(v);
                    }else if(l.getLabelValue().equals("8")){//结核病
                        map.put("SIGN_PERS_GROUP",l.getLabelValue());
                        int  count= sysDao.getServiceDo().findCount(sql, map);
                        v.setTitle("managePhthisisCount");
                        v.setValue(String.valueOf(count));
                        v.setName(l.getLabelTitle());
                        lsEntity.add(v);
                    }else if(l.getLabelValue().equals("9")){//残疾人
                        map.put("SIGN_PERS_GROUP",l.getLabelValue());
                        int  count= sysDao.getServiceDo().findCount(sql, map);
                        v.setTitle("manageDisabledPeopleCount");
                        v.setValue(String.valueOf(count));
                        v.setName(l.getLabelTitle());
                        lsEntity.add(v);
                    }else if(l.getLabelValue().equals("99")){//未知
                    }
                }
//            }else{
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
//                    for(AppLabelManage l:ls) {
//                        ManageCountEntity v = new ManageCountEntity();
//                        if(l.getLabelValue().equals("1")){//普通人群
//                        }else if(l.getLabelValue().equals("2")){//儿童(0-6岁)
//                            if(maps.get(0).get("manageChildCount") != null){
//                                v.setTitle("manageChildCount");
//                                v.setName(l.getLabelTitle());
//                                v.setValue(String.valueOf((int)Double.parseDouble(maps.get(0).get("manageChildCount").toString())));
//                                lsEntity.add(v);
//                            }
//                        }else if(l.getLabelValue().equals("3")){//孕产妇
//                            if(maps.get(0).get("manageMaternalCount") != null){
//                                v.setTitle("manageMaternalCount");
//                                v.setName(l.getLabelTitle());
//                                v.setValue(String.valueOf((int)Double.parseDouble(maps.get(0).get("manageMaternalCount").toString())));
//                                lsEntity.add(v);
//                            }
//                        }else if(l.getLabelValue().equals("4")){//老年人
//                            if(maps.get(0).get("manageOldPeopleCount") != null){
//                                v.setTitle("manageOldPeopleCount");
//                                v.setName(l.getLabelTitle());
//                                v.setValue(String.valueOf((int)Double.parseDouble(maps.get(0).get("manageOldPeopleCount").toString())));
//                                lsEntity.add(v);
//                            }
//                        }else if(l.getLabelValue().equals("5")){//高血压
//                            if(maps.get(0).get("manageBloodCount") != null){
//                                v.setTitle("manageBloodCount");
//                                v.setName(l.getLabelTitle());
//                                v.setValue(String.valueOf((int)Double.parseDouble(maps.get(0).get("manageBloodCount").toString())));
//                                lsEntity.add(v);
//                            }
//                        }else if(l.getLabelValue().equals("6")){//糖尿病
//                            if(maps.get(0).get("manageDiabetesCount") != null){
//                                v.setTitle("manageDiabetesCount");
//                                v.setName(l.getLabelTitle());
//                                v.setValue(String.valueOf((int)Double.parseDouble(maps.get(0).get("manageDiabetesCount").toString())));
//                                lsEntity.add(v);
//                            }
//                        }else if(l.getLabelValue().equals("7")){//严重精神障碍
//                            if(maps.get(0).get("managePsychosisCount") != null){
//                                v.setTitle("managePsychosisCount");
//                                v.setName(l.getLabelTitle());
//                                v.setValue(String.valueOf((int)Double.parseDouble(maps.get(0).get("managePsychosisCount").toString())));
//                                lsEntity.add(v);
//                            }
//                        }else if(l.getLabelValue().equals("8")){//结核病
//                            if(maps.get(0).get("managePhthisisCount") != null){
//                                v.setTitle("managePhthisisCount");
//                                v.setName(l.getLabelTitle());
//                                v.setValue(String.valueOf((int)Double.parseDouble(maps.get(0).get("managePhthisisCount").toString())));
//                                lsEntity.add(v);
//                            }
//                        }else if(l.getLabelValue().equals("9")){//残疾人
//                            if(maps.get(0).get("manageDisabledPeopleCount") != null){
//                                v.setTitle("manageDisabledPeopleCount");
//                                v.setName(l.getLabelTitle());
//                                v.setValue(String.valueOf((int)Double.parseDouble(maps.get(0).get("manageDisabledPeopleCount").toString())));
//                                lsEntity.add(v);
//                            }
//                        }else if(l.getLabelValue().equals("99")){//未知
//                        }
//                    }
//                }
//            }
        }
        return JsonUtil.toJson(lsEntity);
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
//        if(StringUtils.isNotBlank(qvo.getTeamId())){
//            map.put("STARTDATE",qvo.getYearStart());
//            map.put("ENDDATE",qvo.getYearEnd());
//            String sqlDate = " AND t.MANAGE_YEAR_MONTH >= :STARTDATE AND t.MANAGE_YEAR_MONTH <= :ENDDATE ";
//            String sql = "SELECT " +
//                    " SUM(t.MANAGE_MALE_COUNT) maleCount,SUM(t.MANAGE_FEMALE_COUNT) feMaleCount,SUM(t.MANAGE_GENDER_TOTAL_COUNT) totalCount "+
//                    " FROM APP_MANAGE_TEAM_COUNT t where 1=1 "+sqlDate;
//            map.put("TEAM_ID",qvo.getTeamId());
//            sql += " AND t.MANAGE_TEAM_ID = :TEAM_ID ";
//            List<Map> maps = this.sysDao.getServiceDo().findSqlMap(sql,map);
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
//            int man = this.sysDao.getServiceDo().findCount(sqlMan,map);
//            int woMan = this.sysDao.getServiceDo().findCount(sqlWoMan,map);
//            mapCount.put("男性",man);
//            mapCount.put("女性",woMan);
//            mapCount.put("总数",man+woMan);
//            return mapCount;
//        }
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
        List<Map> maps = this.sysDao.getServiceDo().findSqlMap(sql,map);
        if(maps != null && maps.size() >0){
            if(maps.get(0).get("maleCount") != null) {
                mapCount.put("男性",(int)Double.parseDouble(maps.get(0).get("maleCount").toString()));
            }
            if(maps.get(0).get("feMaleCount") != null) {
                mapCount.put("女性",(int)Double.parseDouble(maps.get(0).get("feMaleCount").toString()));
            }
            if(maps.get(0).get("totalCount") != null) {
                mapCount.put("总数",(int)Double.parseDouble(maps.get(0).get("totalCount").toString()));
            }
        }
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
//                List<Map> maps = this.sysDao.getServiceDo().findSqlMap(sql,map);
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
//                    int count = this.sysDao.getServiceDo().findCount(sql,map);
//                    lsTitle.add(resultTitle[i]);
//                    lsValue.add(count);
//                }
//                mapCount.put("lsTitle",lsTitle);
//                mapCount.put("lsValue",lsValue);
//                return mapCount;
//            }
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
            List<Map> maps = this.sysDao.getServiceDo().findSqlMap(sql,map);
            if(maps != null && maps.size() >0){
                if(maps.get(0).get("06Count") != null) {
                    lsValue.add((int)Double.parseDouble(maps.get(0).get("06Count").toString()));
                }
                if(maps.get(0).get("718Count") != null) {
                    lsValue.add((int)Double.parseDouble(maps.get(0).get("718Count").toString()));
                }
                if(maps.get(0).get("1930Count") != null) {
                    lsValue.add((int)Double.parseDouble(maps.get(0).get("1930Count").toString()));
                }
                if(maps.get(0).get("3150Count") != null) {
                    lsValue.add((int)Double.parseDouble(maps.get(0).get("3150Count").toString()));
                }
                if(maps.get(0).get("5165Count") != null) {
                    lsValue.add((int)Double.parseDouble(maps.get(0).get("5165Count").toString()));
                }
                if(maps.get(0).get("greater65Count") != null) {
                    lsValue.add((int)Double.parseDouble(maps.get(0).get("greater65Count").toString()));
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
        String sql = " AND a.SIGN_FROM_DATE >= :yearStart AND a.SIGN_FROM_DATE <= :yearEnd ";
//            String[] qys = {SignFormType.YQY.getValue(),SignFormType.YJY.getValue()};
//            map.put("yqy", qys);
        String[] signStates = new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
        String qys = SignFormType.YQY.getValue();
        map.put("yqy", signStates);
        //已缴费人数
        String sqlyjf="SELECT COUNT(1) FROM APP_SIGN_FORM a WHERE a.SIGN_PAY_STATE = '1' AND a.SIGN_STATE IN (:yqy) "+sql;
        String sqlwjf="SELECT COUNT(1) FROM APP_SIGN_FORM a WHERE a.SIGN_PAY_STATE = '0' AND a.SIGN_STATE IN (:yqy) "+sql;
        if(StringUtils.isNotBlank(qvo.getHospId())){
            sqlyjf +=" AND a.SIGN_HOSP_ID =:SIGN_HOSP_ID";
            sqlwjf +=" AND a.SIGN_HOSP_ID =:SIGN_HOSP_ID";
            map.put("SIGN_HOSP_ID",qvo.getHospId());
        }
        if(StringUtils.isNotBlank(qvo.getTeamId())){
            sqlyjf +=" AND a.SIGN_TEAM_ID =:SIGN_TEAM_ID";
            sqlwjf +=" AND a.SIGN_TEAM_ID =:SIGN_TEAM_ID";
            map.put("SIGN_TEAM_ID",qvo.getTeamId());

        }

        int yjf=sysDao.getServiceDo().findCount(sqlyjf,map);
        int wjf=sysDao.getServiceDo().findCount(sqlwjf,map);
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
        List<AppLabelManage> ls=sysDao.getServiceDo().loadByPk(AppLabelManage.class,"labelType","1");
        if(ls!=null && !ls.isEmpty()) {
            HashMap map = new HashMap();
            map.put("yearStart", ExtendDate.getFirstDayOfMonth(qvo.getYearStart())+" 00:00:00");
            map.put("yearEnd",ExtendDate.getLastDayOfMonth(qvo.getYearEnd())+" 23:59:59");
            String sql = "SELECT COUNT(1) FROM APP_SIGN_FORM a WHERE 1=1  ";
//            String[] qys = {SignFormType.YQY.getValue(),SignFormType.YJY.getValue()};
//            map.put("yqy", qys);
            String[] signStates = new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
//            String qys = SignFormType.YQY.getValue();
            map.put("yqy", signStates);
            sql += " AND a.SIGN_STATE IN (:yqy) ";
            sql += "AND a.SIGN_FROM_DATE >= :yearStart AND a.SIGN_FROM_DATE <= :yearEnd ";
            if (StringUtils.isNotBlank(qvo.getHospId())) {
                sql += " AND a.SIGN_HOSP_ID =:SIGN_HOSP_ID";
                map.put("SIGN_HOSP_ID", qvo.getHospId());
                sql += " AND a.SIGN_HEALTH_GROUP=:SIGN_HEALTH_GROUP";
            }

            if (StringUtils.isNotBlank(qvo.getTeamId())) {
                sql += " AND a.SIGN_TEAM_ID =:SIGN_TEAM_ID";
                map.put("SIGN_TEAM_ID",qvo.getTeamId());
                sql += " AND a.SIGN_HEALTH_GROUP=:SIGN_HEALTH_GROUP";
            }

            for(AppLabelManage l:ls) {
                map.put("SIGN_HEALTH_GROUP",l.getLabelValue());
                int  count= sysDao.getServiceDo().findCount(sql, map);
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
            String sql = "SELECT COUNT(1) FROM APP_SIGN_FORM a LEFT JOIN APP_LABEL_GROUP b on a.ID=b.LABEL_SIGN_ID WHERE 1=1 ";
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
                sql += " AND b.LABEL_VALUE=:SIGN_PERS_GROUP";
            }
            if (StringUtils.isNotBlank(qvo.getTeamId())) {
                sql += " AND a.SIGN_TEAM_ID =:SIGN_TEAM_ID";
                map.put("SIGN_TEAM_ID",qvo.getTeamId());
                sql += " AND b.LABEL_VALUE=:SIGN_PERS_GROUP";
            }


            for(AppLabelManage l:ls) {
                map.put("SIGN_PERS_GROUP",l.getLabelValue());
                int  count= sysDao.getServiceDo().findCount(sql, map);
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
                }else if(l.getLabelValue().equals("99")){//未知
                    rmap.put("manageServiceUnknownCount", count);
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
    public Map<String,Object> findCountGenderInitialise(ResidentVo qvo)  throws Exception{
        Map<String,Object> map = new HashMap<String,Object> ();
        Map<String,Object> mapCount = new HashMap<String,Object> ();
        map.put("yearStart", ExtendDate.getFirstDayOfMonth(qvo.getYearStart())+" 00:00:00");
        map.put("yearEnd",ExtendDate.getLastDayOfMonth(qvo.getYearEnd())+" 23:59:59");
        String sqlMan = "SELECT count(1) FROM APP_SIGN_FORM a where a.SIGN_PATIENT_GENDER = '1'  AND a.SIGN_FROM_DATE >= :yearStart AND a.SIGN_FROM_DATE <= :yearEnd ";//统计男
        String sqlWoMan = "SELECT count(1) FROM APP_SIGN_FORM a where a.SIGN_PATIENT_GENDER = '2' AND a.SIGN_FROM_DATE >= :yearStart AND a.SIGN_FROM_DATE <= :yearEnd ";//统计女
//        String[] qys = {SignFormType.YQY.getValue(),SignFormType.YJY.getValue()};
//        map.put("yqy", qys);
        String[] signStates = new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
//        String qys = SignFormType.YQY.getValue();
        map.put("yqy", signStates);
        sqlMan += " AND a.SIGN_STATE IN (:yqy) ";
        sqlWoMan += " AND a.SIGN_STATE IN (:yqy) ";
        if(StringUtils.isNotBlank(qvo.getHospId())){
            map.put("SIGN_HOSP_ID",qvo.getHospId());
            sqlMan += " AND a.SIGN_HOSP_ID =:SIGN_HOSP_ID";
            sqlWoMan += " AND a.SIGN_HOSP_ID =:SIGN_HOSP_ID";
            int man = this.sysDao.getServiceDo().findCount(sqlMan,map);
            int woMan = this.sysDao.getServiceDo().findCount(sqlWoMan,map);
            mapCount.put("manageMaleCount",man);
            mapCount.put("manageFemaleCount",woMan);
            mapCount.put("manageGenderTotalCount",man+woMan);
            return mapCount;
        }
        if(StringUtils.isNotBlank(qvo.getTeamId())){
            map.put("SIGN_TEAM_ID",qvo.getTeamId());
            sqlMan += " AND a.SIGN_TEAM_ID = :SIGN_TEAM_ID";
            sqlWoMan += " AND a.SIGN_TEAM_ID = :SIGN_TEAM_ID";
            int man = this.sysDao.getServiceDo().findCount(sqlMan,map);
            int woMan = this.sysDao.getServiceDo().findCount(sqlWoMan,map);
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
        if(result != null){
            map.put("yearStart", ExtendDate.getFirstDayOfMonth(qvo.getYearStart())+" 00:00:00");
            map.put("yearEnd",ExtendDate.getLastDayOfMonth(qvo.getYearEnd())+" 23:59:59");
//            String[] qys = {SignFormType.YQY.getValue(),SignFormType.YJY.getValue()};
//            map.put("yqy", qys);
            String[] signStates = new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
//            String qys = SignFormType.YQY.getValue();
            map.put("yqy", signStates);
            String sql = "select count(*) from APP_SIGN_FORM a WHERE 1=1 AND a.SIGN_FROM_DATE >= :yearStart AND a.SIGN_FROM_DATE <= :yearEnd AND a.SIGN_STATE IN (:yqy) ";
            if(StringUtils.isNotBlank(qvo.getHospId())){
                map.put("SIGN_HOSP_ID",qvo.getHospId());
                for(int i=0;i<result.length;i++){
                    String[] age = result[i].split(",");
                    map.put("startAge",age[0]);
                    map.put("endAge",age[1]);
                    sql += " AND a.SIGN_PATIENT_AGE  >= :startAge AND a.SIGN_PATIENT_AGE  <= :endAge ";
                    sql += " AND a.SIGN_HOSP_ID =:SIGN_HOSP_ID ";
                    int count = this.sysDao.getServiceDo().findCount(sql,map);
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
                    int count = this.sysDao.getServiceDo().findCount(sql,map);
                    mapCount.put(resultTitle[i],count);
                }
                return mapCount;
            }
        }
        return null;
    }

}
