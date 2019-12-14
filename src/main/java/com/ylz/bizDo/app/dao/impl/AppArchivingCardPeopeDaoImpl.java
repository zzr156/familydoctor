package com.ylz.bizDo.app.dao.impl;

import com.alibaba.fastjson.JSON;
import com.ylz.bizDo.app.dao.AppArchivingCardPeopeDao;
import com.ylz.bizDo.app.entity.AppArchivingCardAddrEntity;
import com.ylz.bizDo.app.entity.AppArchivingCardPeopeEntity;
import com.ylz.bizDo.app.entity.AppArchivingPatientEntity;
import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.app.vo.AppArchivingCardPeopleQvo;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.cd.po.CdUser;
import com.ylz.bizDo.jtapp.basicHealthVo.HomeServiceItemsQvo;
import com.ylz.bizDo.jtapp.drEntity.AppDrPatientFwEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppPatientUserEntity;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packaccede.util.CardUtil;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.AddressType;
import com.ylz.packcommon.common.comEnum.AppRoleType;
import com.ylz.packcommon.common.comEnum.EconomicType;
import com.ylz.packcommon.common.comEnum.LabelManageType;
import com.ylz.packcommon.common.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by zzl on 2018/7/17.
 */
@Service("appArchivingCardPeopeDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppArchivingCardPeopeDaoImpl implements AppArchivingCardPeopeDao {
    @Autowired
    private SysDao sysDao;

    @Override
    public List<AppArchivingCardPeopeEntity> findPeopleList(AppArchivingCardPeopleQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("DEL_STATE","0");
        String sql = "SELECT " +
                "t.ID id," +
                "t.RHF_ID rhfId,\n" +
                "t.ADDR_COUNTY_CODE addrCountyCode,\n" +
                "t.ADDR_COUNTY_NAME addrCountyName,\n" +
                "t.ADDR_RURAL_CODE addrRuralCode,\n" +
                "t.ADDR_RURAL_NAME addrRuralName,\n" +
                "t.ADDR_VILLAGE_CODE addrVillageCode,\n" +
                "t.ADDR_VILLAGE_NAME addrVillageName,\n" +
                "t.ARCHIVING_PATIENT_NAME patientName,\n" +
                "t.ARCHIVING_PATIENT_IDNO patientIdno,\n" +
                "t.SIGN_STATE signState,\n" +
                "t.SOURCE_TYPE sourceType," +
                "t.IS_NOT_POVERTY isNotPoverty,\n" +
                "t.NOT_SIGN_REASON notSignReason," +
                "t.PATIENT_TEL patientTel," +
                "t.LOW_INSURED lowInsured," +
                "t.POOR_HOUSEHOLDS poorHouseholds," +
                "t.TEAM_ID teamId," +
                "t.DEL_STATE delState," +
                "t.DR_ID drId," +
                "t.PROVINCIAL_INSURANCE provincialInsurance," +
                "t.SIGN_FROM_DATE signFromDate," +
                "t.HOSP_ID hospId," +
                "t.ADDR_HOSP_ID addrHospId " +
                "FROM APP_ARCHIVINGCARD_PEOPLE t  WHERE 1=1  ";//AND SOURCE_TYPE ='1'
        if("0".equals(qvo.getJdSourceType())){
            map.put("SOURCE_TYPE","3");
        }else{
            map.put("SOURCE_TYPE","1");
        }
        sql += " AND t.SOURCE_TYPE =:SOURCE_TYPE ";
        //姓名模糊查询
        if(StringUtils.isNotBlank(qvo.getPatientName())){
            map.put("PATIENT_NAME","%"+qvo.getPatientName()+"%");
            sql += " AND t.ARCHIVING_PATIENT_NAME LIKE :PATIENT_NAME ";

        }

        //身份证查询
        if(StringUtils.isNotBlank(qvo.getPatientIdno())){
            map.put("PATIENT_IDNO",qvo.getPatientIdno());
            sql += " AND t.ARCHIVING_PATIENT_IDNO =:PATIENT_IDNO ";

        }

        if(StringUtils.isNotBlank(qvo.getPatientNeighborhoodCommittee())){
            map.put("areaCode",qvo.getPatientNeighborhoodCommittee());
            if("0".equals(qvo.getJdSourceType())){
                map.put("streetCode",qvo.getPatientStreet());
                sql += " AND t.ADDR_RURAL_CODE = :streetCode ";
                sql += " AND t.ADDR_VILLAGE_NAME = :areaCode ";
            }else{
                sql += " AND t.ADDR_VILLAGE_CODE = :areaCode ";
            }

        }else if(StringUtils.isNotBlank(qvo.getPatientStreet())){
            map.put("areaCode",qvo.getPatientStreet());
//                    map.put("areaCode",AreaUtils.getAreaCode(qvo.getPatientStreet(),AppRoleType.SHEQU.getValue())+"%");
            sql += " AND t.ADDR_RURAL_CODE = :areaCode ";
        }else if(StringUtils.isNotBlank(qvo.getPatientArea())){
            map.put("areaCode",qvo.getPatientArea());
//                    map.put("areaCode",AreaUtils.getAreaCode(qvo.getPatientArea(),AppRoleType.QU.getValue())+"%");
            sql += " AND t.ADDR_COUNTY_CODE = :areaCode ";
        }else if(StringUtils.isNotBlank(qvo.getPatientCity())){
            map.put("areaCode",AreaUtils.getAreaCode(qvo.getPatientCity())+"%");
            sql += " AND t.ADDR_COUNTY_CODE LIKE :areaCode ";
        }else{
            map.put("areaCode","35%");
            sql += " AND t.ADDR_COUNTY_CODE LIKE :areaCode ";
        }

        if(StringUtils.isNotBlank(qvo.getJdState())){
            if("0".equals(qvo.getJdState())){
                sql += " AND t.RHF_ID IS NULL ";
            }else{
                sql += " AND t.RHF_ID IS NOT NULL ";
            }
        }
        String startTime = ExtendDate.getYMD(Calendar.getInstance())+" 00:00:00";
        String endTime = ExtendDate.getYMD(Calendar.getInstance())+ " 23:59:59";
        map.put("startTime",startTime);
        map.put("endTime",endTime);

        if(StringUtils.isNotBlank(qvo.getRole()) && AppRoleType.YISHENG.getValue().equals(qvo.getRole())){//医生权限
            if(StringUtils.isNotBlank(qvo.getTeamIds())){
                String[] teamIds = qvo.getTeamIds().split(",");
                map.put("teamIds",teamIds);
                sql +=" AND t.TEAM_ID IN (:teamIds) ";
            }else{
                map.put("drId",qvo.getDrId());
                sql += " AND t.DR_ID =:drId ";
            }
            if(StringUtils.isNotBlank(qvo.getSignState())){
                if("1".equals(qvo.getSignState())){
                    sql += " AND t.SIGN_STATE IN ('0', '2')";
                    if(qvo.getToDayState().length>0){//查询当天签约信息
                        if("1".equals(qvo.getToDayState()[0])){
                            sql += " AND t.SIGN_FROM_DATE >= :startTime ";
                            sql += " AND t.SIGN_FROM_DATE <= :endTime ";
//                            sql += " AND f.HS_CREATE_DATE >= :startTime ";
//                            sql += " AND f.HS_CREATE_DATE <= :endTime ";
//                            sql += " ";
                            //
                        }
                        /*else{
                            sql += " AND SIGN_FROM_DATE <= :startTime ";
                        }*/
                    }
                    /*else{
                        sql += " AND SIGN_FROM_DATE <= :startTime ";
                    }*/
                }else{
                    sql += " AND t.SIGN_STATE is NULL";
                }
            }
        }else{
            if(StringUtils.isNotBlank(qvo.getSignState())){
                if("1".equals(qvo.getSignState())){
                    sql += " AND t.SIGN_STATE IN ('0', '2')";
                    if(qvo.getToDayState().length>0){//查询当天签约信息
                        if("1".equals(qvo.getToDayState()[0])){
                            sql += " AND t.SIGN_FROM_DATE >= :startTime ";
                            sql += " AND t.SIGN_FROM_DATE <= :endTime ";
                        }
                        /*else{
                            sql += " AND SIGN_FROM_DATE <= :startTime ";
                        }*/
                    }
                    /*else{
                        sql += " AND SIGN_FROM_DATE <= :startTime ";
                    }*/
                }else{
                    sql += " AND t.SIGN_STATE is NULL";
                }
            }
        }

        if(StringUtils.isNotBlank(qvo.getPovertyState())){
            map.put("povertyState",qvo.getPovertyState());
            sql += " AND t.IS_NOT_POVERTY =:povertyState ";
        }
        if(StringUtils.isNotBlank(qvo.getNotConfirm())){//未确认
            sql += " AND t.ADDR_RURAL_CODE IS NULL ";
        }

        if(qvo.getCancelState() != null){
            if(qvo.getCancelState().length == 1){//查询注销数据
                map.put("DEL_STATE",new String[]{"3","4"});
                sql += " AND t.DEL_STATE IN (:DEL_STATE) ";
            }
        }

        if(StringUtils.isNotBlank(qvo.getProvincialInsurance())){//对象类型
            map.put("PROVINCIAL_INSURANCE",qvo.getProvincialInsurance());
            sql += " AND t.PROVINCIAL_INSURANCE =:PROVINCIAL_INSURANCE";

        }
        List<AppArchivingCardPeopeEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppArchivingCardPeopeEntity.class,qvo);
        return list;
    }
    /**
     * 查询建档立卡人数、未建档人数、未建档已签约人数、已建档未签约人数、
     * 脱贫人数
     * @param qvo
     * @return
     */
    @Override
    public Map<String, Object> findMap(AppArchivingCardPeopleQvo qvo) throws Exception{
        Map<String,Object> returnMap = new HashMap<>();
        Map<String,Object> map = new HashMap<>();
        int totalCount = 0;//建档立卡人数
        int totalCountS = 0;//建档立卡人数实
        int wjd = 0;//未建档人数
        int wjdS = 0;
        int wjdyqy = 0;//未建档已签约
        int wjdyqyS = 0;
        int yjdwqy = 0;//已建档未签约
        int yjdwqyS = 0;
        int tp = 0;//脱贫
        int tpS = 0;
        int ycs = 0;//异常数
        int ycsS = 0;
        int cfs = 0;//重复数
        String startTime = ExtendDate.getYMD(Calendar.getInstance())+" 00:00:00";
        String endTime = ExtendDate.getYMD(Calendar.getInstance())+ " 23:59:59";
        map.put("startTime",startTime);
        map.put("endTime",endTime);
        String sql = " SELECT COUNT(1) FROM APP_ARCHIVINGCARD_PEOPLE WHERE 1=1  ";//AND SOURCE_TYPE ='1'
        if("0".equals(qvo.getJdSourceType())){
            map.put("SOURCE_TYPE","3");
        }else{
            map.put("SOURCE_TYPE","1");
        }
        sql += " AND SOURCE_TYPE =:SOURCE_TYPE ";

        //姓名模糊查询
        if(StringUtils.isNotBlank(qvo.getPatientName())){
            map.put("PATIENT_NAME","%"+qvo.getPatientName()+"%");
            sql += " AND ARCHIVING_PATIENT_NAME LIKE :PATIENT_NAME ";

        }

        //身份证查询
        if(StringUtils.isNotBlank(qvo.getPatientIdno())){
            map.put("PATIENT_IDNO",qvo.getPatientIdno());
            sql += " AND ARCHIVING_PATIENT_IDNO =:PATIENT_IDNO ";

        }


        if(StringUtils.isNotBlank(qvo.getPatientNeighborhoodCommittee())){
            map.put("areaCode",qvo.getPatientNeighborhoodCommittee());
            if("0".equals(qvo.getJdSourceType())){
                map.put("streetCode",qvo.getPatientStreet());
                sql += " AND ADDR_RURAL_CODE = :streetCode ";
                sql += " AND ADDR_VILLAGE_NAME = :areaCode ";
            }else{
                sql += " AND ADDR_VILLAGE_CODE = :areaCode ";
            }
        }else if(StringUtils.isNotBlank(qvo.getPatientStreet())){
            map.put("areaCode",qvo.getPatientStreet());
//                    map.put("areaCode",AreaUtils.getAreaCode(qvo.getPatientStreet(),AppRoleType.SHEQU.getValue())+"%");
            sql += " AND ADDR_RURAL_CODE = :areaCode ";
        }else if(StringUtils.isNotBlank(qvo.getPatientArea())){
            map.put("areaCode",qvo.getPatientArea());
//                    map.put("areaCode",AreaUtils.getAreaCode(qvo.getPatientArea(),AppRoleType.QU.getValue())+"%");
            sql += " AND ADDR_COUNTY_CODE = :areaCode ";
        }else if(StringUtils.isNotBlank(qvo.getPatientCity())){
            map.put("areaCode",AreaUtils.getAreaCode(qvo.getPatientCity())+"%");
            sql += " AND ADDR_COUNTY_CODE LIKE :areaCode ";
        }else{
            map.put("areaCode","35%");
            sql += " AND ADDR_COUNTY_CODE LIKE :areaCode ";
        }

        if(StringUtils.isNotBlank(qvo.getNotConfirm())){
            sql += " AND ADDR_RURAL_CODE IS NULL ";
        }

        if(StringUtils.isNotBlank(qvo.getJdState())){
            if("0".equals(qvo.getJdState())){
                sql += " AND RHF_ID IS NULL ";
            }else{
                sql += " AND RHF_ID IS NOT NULL ";
            }
        }
        if(StringUtils.isNotBlank(qvo.getRole()) && AppRoleType.YISHENG.getValue().equals(qvo.getRole())){//医生权限
            if(StringUtils.isNotBlank(qvo.getTeamIds())){
                String[] teamIds = qvo.getTeamIds().split(",");
                map.put("teamIds",teamIds);
                sql +=" AND TEAM_ID IN (:teamIds) ";
            }else{
                map.put("drId",qvo.getDrId());
                sql += " AND DR_ID =:drId ";
            }
            if(StringUtils.isNotBlank(qvo.getSignState())){
                if("1".equals(qvo.getSignState())){
                    sql += " AND SIGN_STATE IN ('0', '2')";
                    if(qvo.getToDayState().length>0){//查询当天签约信息
                        if("1".equals(qvo.getToDayState()[0])){
                            sql += " AND SIGN_FROM_DATE >= :startTime ";
                            sql += " AND SIGN_FROM_DATE <= :endTime ";
                        }
                        /*else{
                            sql += " AND SIGN_FROM_DATE <= :startTime ";
                        }*/
                    }
                    /*else{
                        sql += " AND SIGN_FROM_DATE <= :startTime ";
                    }*/
                }else{
                    sql += " AND SIGN_STATE is NULL";
                }
            }
        }else{
            if(StringUtils.isNotBlank(qvo.getSignState())){
                if("1".equals(qvo.getSignState())){
                    sql += " AND SIGN_STATE IN ('0', '2')";
                    if(qvo.getToDayState().length>0){//查询当天签约信息
                        if("1".equals(qvo.getToDayState()[0])){
                            sql += " AND SIGN_FROM_DATE >= :startTime ";
                            sql += " AND SIGN_FROM_DATE <= :endTime ";
                        }
                        /*else{
                            sql += " AND SIGN_FROM_DATE <= :startTime ";
                        }*/
                    }
                    /*else{
                        sql += " AND SIGN_FROM_DATE <= :startTime ";
                    }*/
                }else{
                    sql += " AND SIGN_STATE is NULL";
                }
            }
        }
        if(StringUtils.isNotBlank(qvo.getPovertyState())){
            /*if("1".equals(qvo.getPovertyState())){
                map.put("povertyState",qvo.getPovertyState());
                sql += " AND IS_NOT_POVERTY IS NOT NULL ";
            }else{
                sql += " AND IS_NOT_POVERTY IS NULL ";
            }*/
            map.put("povertyState",qvo.getPovertyState());
            sql += " AND IS_NOT_POVERTY =:povertyState ";
        }

        if(StringUtils.isNotBlank(qvo.getNotConfirm())){//未确认
            sql += " AND ADDR_RURAL_CODE IS NULL ";
        }

        if(qvo.getCancelState() != null){
            if(qvo.getCancelState().length == 1){//查询注销数据
                map.put("DEL_STATE",new String[]{"3","4"});
                sql += " AND DEL_STATE IN (:DEL_STATE) ";
            }
        }

        if(StringUtils.isNotBlank(qvo.getProvincialInsurance())){//对象类型
            map.put("PROVINCIAL_INSURANCE",qvo.getProvincialInsurance());
            sql += " AND PROVINCIAL_INSURANCE =:PROVINCIAL_INSURANCE ";

        }

        totalCount = sysDao.getServiceDo().findCount(sql,map);
        String sqls ="SELECT COUNT(1) FROM ("+ sql +" GROUP BY ARCHIVING_PATIENT_IDNO ) aa";
//        totalCountS = sysDao.getServiceDo().findCount(sqls,map);

        String sqlO = sql + " AND RHF_ID IS NULL ";
        String sqlOS ="SELECT COUNT(1) FROM ("+  sqlO + " GROUP BY ARCHIVING_PATIENT_IDNO ) aa";
        wjd = sysDao.getServiceDo().findCount(sqlO,map);
//        wjdS = sysDao.getServiceDo().findCount(sqlOS,map);

        String sqlT = sql + " AND RHF_ID IS NULL AND SIGN_STATE IN ('0', '2') ";
        String sqlTS ="SELECT COUNT(1) FROM ("+  sqlT + " GROUP BY ARCHIVING_PATIENT_IDNO ) aa";
        wjdyqy = sysDao.getServiceDo().findCount(sqlT,map);
//        wjdyqyS = sysDao.getServiceDo().findCount(sqlTS,map);

        String sqlTT = sql + " AND RHF_ID IS NOT NULL AND SIGN_STATE is NULL ";
        String sqlTTS ="SELECT COUNT(1) FROM ("+  sqlTT + " GROUP BY ARCHIVING_PATIENT_IDNO ) aa";
        yjdwqy = sysDao.getServiceDo().findCount(sqlTT,map);
//        yjdwqyS = sysDao.getServiceDo().findCount(sqlTTS,map);

        String sqlF = sql + " AND IS_NOT_POVERTY = '1' ";
        String sqlFS ="SELECT COUNT(1) FROM ("+  sqlF + " GROUP BY ARCHIVING_PATIENT_IDNO ) aa";
        tp = sysDao.getServiceDo().findCount(sqlF,map);
//        tpS = sysDao.getServiceDo().findCount(sqlFS,map);

        String sqlYc = sql + " AND ADDR_RURAL_CODE IS NULL ";
        String sqlYcS ="SELECT COUNT(1) FROM ("+  sqlYc + "  GROUP BY ARCHIVING_PATIENT_IDNO ) aa";
        ycs = sysDao.getServiceDo().findCount(sqlYc,map);
//        ycsS = sysDao.getServiceDo().findCount(sqlYcS,map);
        String sqlCf = "SELECT COUNT(1) FROM ("+sql+ " GROUP BY ARCHIVING_PATIENT_IDNO HAVING count(1)>1) aa";
//        cfs = sysDao.getServiceDo().findCount(sqlCf,map);

        returnMap.put("totalCount",totalCount);
//        returnMap.put("totalCountS",totalCountS);

        returnMap.put("wjd",wjd);
//        returnMap.put("wjdS",wjdS);

        returnMap.put("wjdyqy",wjdyqy);
//        returnMap.put("wjdyqyS",wjdyqyS);

        returnMap.put("yjdwqy",yjdwqy);
//        returnMap.put("yjdwqyS",yjdwqyS);

        returnMap.put("tp",tp);
//        returnMap.put("tpS",tpS);

        returnMap.put("ycs",ycs);
//        returnMap.put("ycsS",ycsS);
//        returnMap.put("cfs",cfs);
        return returnMap;
    }

    @Override
    public AppArchivingPatientEntity archivingLook(AppArchivingCardPeopleQvo qvo) throws Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("id",qvo.getId());
        String sql = "SELECT " +
                "ID id," +
                "RHF_ID rhfId,\n" +
                "ADDR_COUNTY_CODE addrCountyCode,\n" +
                "ADDR_COUNTY_NAME addrCountyName,\n" +
                "ADDR_RURAL_CODE addrRuralCode,\n" +
                "ADDR_RURAL_NAME addrRuralName,\n" +
                "ADDR_VILLAGE_CODE addrVillageCode,\n" +
                "ADDR_VILLAGE_NAME addrVillageName,\n" +
                "ARCHIVING_PATIENT_NAME patientName,\n" +
                "ARCHIVING_PATIENT_IDNO patientIdno,\n" +
                "SIGN_STATE signState,\n" +
                "IS_NOT_POVERTY isNotPoverty,\n" +
                "NOT_SIGN_REASON notSignReason," +
                "PATIENT_TEL patientTel," +
                "LOW_INSURED lowInsured," +
                "POOR_HOUSEHOLDS poorHouseholds," +
                "TEAM_ID teamId," +
                "DR_ID drId," +
                "DEL_REASON delReason," +
                "DEL_STATE delState," +
                "OTHER_REASON otherReason," +
                "REVOKE_REASON revokeReason," +
                "REVOKE_DATE revokeDate," +
                "SEX sex," +
                "REMARKS remarks " +
                "FROM APP_ARCHIVINGCARD_PEOPLE WHERE 1=1 ";
        sql += " AND ID = :id ";
        List<AppArchivingPatientEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppArchivingPatientEntity.class);
        if(list != null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<AppArchivingCardPeopeEntity> findPeopleListT(AppArchivingCardPeopleQvo qvo) throws Exception{
        Map<String, Object> map = new HashMap<>();
        String sql = "SELECT " +
                "ID id," +
                "RHF_ID rhfId,\n" +
                "ADDR_COUNTY_CODE addrCountyCode,\n" +
                "ADDR_COUNTY_NAME addrCountyName,\n" +
                "ADDR_RURAL_CODE addrRuralCode,\n" +
                "ADDR_RURAL_NAME addrRuralName,\n" +
                "ADDR_VILLAGE_CODE addrVillageCode,\n" +
                "ADDR_VILLAGE_NAME addrVillageName,\n" +
                "ARCHIVING_PATIENT_NAME patientName,\n" +
                "ARCHIVING_PATIENT_IDNO patientIdno,\n" +
                "SIGN_STATE signState,\n" +
                "IS_NOT_POVERTY isNotPoverty,\n" +
                "NOT_SIGN_REASON notSignReason," +
                "PATIENT_TEL patientTel," +
                "LOW_INSURED lowInsured," +
                "POOR_HOUSEHOLDS poorHouseholds," +
                "TEAM_ID teamId," +
                "DR_ID drId," +
                "PROVINCIAL_INSURANCE provincialInsurance," +
                "SIGN_FROM_DATE signFromDate " +
                "FROM APP_ARCHIVINGCARD_PEOPLE WHERE 1=1  ";//AND SOURCE_TYPE ='1'
        if("0".equals(qvo.getJdSourceType())){
            map.put("SOURCE_TYPE","3");
        }else{
            map.put("SOURCE_TYPE","1");
            sql += " AND DEL_STATE ='0' ";
        }
        sql += " AND SOURCE_TYPE =:SOURCE_TYPE ";

        //姓名模糊查询
        if(StringUtils.isNotBlank(qvo.getPatientName())){
            map.put("PATIENT_NAME","%"+qvo.getPatientName()+"%");
            sql += " AND ARCHIVING_PATIENT_NAME LIKE :PATIENT_NAME ";

        }

        //身份证查询
        if(StringUtils.isNotBlank(qvo.getPatientIdno())){
            map.put("PATIENT_IDNO",qvo.getPatientIdno());
            sql += " AND ARCHIVING_PATIENT_IDNO =:PATIENT_IDNO ";

        }

        if (StringUtils.isNotBlank(qvo.getPatientNeighborhoodCommittee())) {
            map.put("areaCode",qvo.getPatientNeighborhoodCommittee());
            if("0".equals(qvo.getJdSourceType())){
                map.put("streetCode",qvo.getPatientStreet());
                sql += " AND ADDR_RURAL_CODE = :streetCode ";
                sql += " AND ADDR_VILLAGE_NAME = :areaCode ";
            }else{
                sql += " AND ADDR_VILLAGE_CODE = :areaCode ";
            }
        } else if (StringUtils.isNotBlank(qvo.getPatientStreet())) {
            map.put("areaCode", qvo.getPatientStreet());
//                    map.put("areaCode",AreaUtils.getAreaCode(qvo.getPatientStreet(),AppRoleType.SHEQU.getValue())+"%");
            sql += " AND ADDR_RURAL_CODE = :areaCode ";
        } else if (StringUtils.isNotBlank(qvo.getPatientArea())) {
            map.put("areaCode", qvo.getPatientArea());
//                    map.put("areaCode",AreaUtils.getAreaCode(qvo.getPatientArea(),AppRoleType.QU.getValue())+"%");
            sql += " AND ADDR_COUNTY_CODE = :areaCode ";
        } else if (StringUtils.isNotBlank(qvo.getPatientCity())) {
            map.put("areaCode", AreaUtils.getAreaCode(qvo.getPatientCity()) + "%");
            sql += " AND ADDR_COUNTY_CODE LIKE :areaCode ";
        } else {
            map.put("areaCode", "35%");
            sql += " AND ADDR_COUNTY_CODE LIKE :areaCode ";
        }

        if (StringUtils.isNotBlank(qvo.getJdState())) {
            if ("0".equals(qvo.getJdState())) {
                sql += " AND RHF_ID IS NULL ";
            } else {
                sql += " AND RHF_ID IS NOT NULL ";
            }
        }
        if (StringUtils.isNotBlank(qvo.getRole()) && AppRoleType.YISHENG.getValue().equals(qvo.getRole())) {//医生权限
            if (StringUtils.isNotBlank(qvo.getTeamIds())) {
                String[] teamIds = qvo.getTeamIds().split(",");
                map.put("teamIds", teamIds);
                sql += " AND TEAM_ID IN (:teamIds) ";
            } else {
                map.put("drId", qvo.getDrId());
                sql += " AND DR_ID =:drId ";
            }
            if (StringUtils.isNotBlank(qvo.getSignState())) {
                if ("1".equals(qvo.getSignState())) {
                    sql += " AND SIGN_STATE IN ('0', '2')";
                } else {
                    sql += " AND SIGN_STATE is NULL";
                }
            }
        } else {
            if (StringUtils.isNotBlank(qvo.getSignState())) {
                if ("1".equals(qvo.getSignState())) {
                    sql += " AND SIGN_STATE IN ('0', '2')";
                } else {
                    sql += " AND SIGN_STATE is NULL";
                }
            }
        }
        if (StringUtils.isNotBlank(qvo.getPovertyState())) {
            map.put("povertyState", qvo.getPovertyState());
            sql += " AND IS_NOT_POVERTY =:povertyState ";
        }
        //去重
        if (StringUtils.isNotBlank(qvo.getRemovalState())) {
            String ssql = sql + " AND ARCHIVING_PATIENT_IDNO IN (SELECT aa.ARCHIVING_PATIENT_IDNO FROM (SELECT ARCHIVING_PATIENT_IDNO,count(1) count FROM app_archivingcard_people a WHERE ARCHIVING_PATIENT_IDNO IS NOT NULL AND DEL_STATE ='0' GROUP BY ARCHIVING_PATIENT_IDNO HAVING count>1) aa) order by ARCHIVING_PATIENT_IDNO ";
            List<AppArchivingCardPeopeEntity> list = sysDao.getServiceDo().findSqlMapRVo(ssql, map, AppArchivingCardPeopeEntity.class, qvo);
            return list;
        }else if(qvo.getIdnoState().length==1){//身份证异常
            if("0".equals(qvo.getJdSourceType())){
                map.put("REMARKS","1");
                sql += " AND REMARKS =:REMARKS ";
            }else{
                sql += " AND (SEX IS NULL OR length(ARCHIVING_PATIENT_IDNO)!=18 OR (length(ARCHIVING_PATIENT_IDNO) =19 AND ARCHIVING_PATIENT_IDNO not LIKE '%x' ) ) ";
            }
        }else{
            if(StringUtils.isNotBlank(qvo.getNotConfirm())){//未确认
                if(qvo.getVillageState().length==1){//居委会异常
                    sql += " AND ADDR_VILLAGE_CODE IS NULL ";
                }else{
                    sql += " AND ADDR_RURAL_CODE IS NULL ";
                }
            }
        }
        if(StringUtils.isNotBlank(qvo.getProvincialInsurance())){
            map.put("PROVINCIAL_INSURANCE",qvo.getProvincialInsurance());
            sql += " AND PROVINCIAL_INSURANCE =:PROVINCIAL_INSURANCE ";
        }
        List<AppArchivingCardPeopeEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppArchivingCardPeopeEntity.class,qvo);
        return list;
    }

    @Override
    public Map<String, Object> findMapT(AppArchivingCardPeopleQvo qvo) throws Exception{
        Map<String,Object> returnMap = new HashMap<>();
        Map<String,Object> map = new HashMap<>();
        int totalCount = 0;//建档立卡人数
        int totalCountS = 0;//建档立卡人数实
        int wjd = 0;//未建档人数
        int wjdS = 0;
        int wjdyqy = 0;//未建档已签约
        int wjdyqyS = 0;
        int yjdwqy = 0;//已建档未签约
        int yjdwqyS = 0;
        int tp = 0;//脱贫
        int tpS = 0;
        int ycs = 0;//异常数
        int ycsS = 0;
        int cfs = 0;//重复数
        String sql = "SELECT " +
                "count(1)  " +
                "FROM APP_ARCHIVINGCARD_PEOPLE WHERE 1=1  ";//AND SOURCE_TYPE ='1'
        if("0".equals(qvo.getJdSourceType())){
            map.put("SOURCE_TYPE","3");
        }else{
            map.put("SOURCE_TYPE","1");
            sql += " AND DEL_STATE ='0' ";
        }
        sql += " AND SOURCE_TYPE =:SOURCE_TYPE ";

        //姓名模糊查询
        if(StringUtils.isNotBlank(qvo.getPatientName())){
            map.put("PATIENT_NAME","%"+qvo.getPatientName()+"%");
            sql += " AND ARCHIVING_PATIENT_NAME LIKE :PATIENT_NAME ";

        }

        //身份证查询
        if(StringUtils.isNotBlank(qvo.getPatientIdno())){
            map.put("PATIENT_IDNO",qvo.getPatientIdno());
            sql += " AND ARCHIVING_PATIENT_IDNO =:PATIENT_IDNO ";

        }

        if(StringUtils.isNotBlank(qvo.getPatientNeighborhoodCommittee())){
            map.put("areaCode",qvo.getPatientNeighborhoodCommittee());
            if("0".equals(qvo.getJdSourceType())){
                map.put("streetCode",qvo.getPatientStreet());
                sql += " AND ADDR_RURAL_CODE = :streetCode ";
                sql += " AND ADDR_VILLAGE_NAME = :areaCode ";
            }else{
                sql += " AND ADDR_VILLAGE_CODE = :areaCode ";
            }
        }else if(StringUtils.isNotBlank(qvo.getPatientStreet())){
            map.put("areaCode",qvo.getPatientStreet());
//                    map.put("areaCode",AreaUtils.getAreaCode(qvo.getPatientStreet(),AppRoleType.SHEQU.getValue())+"%");
            sql += " AND ADDR_RURAL_CODE = :areaCode ";
        }else if(StringUtils.isNotBlank(qvo.getPatientArea())){
            map.put("areaCode",qvo.getPatientArea());
//                    map.put("areaCode",AreaUtils.getAreaCode(qvo.getPatientArea(),AppRoleType.QU.getValue())+"%");
            sql += " AND ADDR_COUNTY_CODE = :areaCode ";
        }else if(StringUtils.isNotBlank(qvo.getPatientCity())){
            map.put("areaCode",AreaUtils.getAreaCode(qvo.getPatientCity())+"%");
            sql += " AND ADDR_COUNTY_CODE LIKE :areaCode ";
        }else{
            map.put("areaCode","35%");
            sql += " AND ADDR_COUNTY_CODE LIKE :areaCode ";
        }

        if(StringUtils.isNotBlank(qvo.getJdState())){
            if("0".equals(qvo.getJdState())){
                sql += " AND RHF_ID IS NULL ";
            }else{
                sql += " AND RHF_ID IS NOT NULL ";
            }
        }
        if(StringUtils.isNotBlank(qvo.getRole()) && AppRoleType.YISHENG.getValue().equals(qvo.getRole())){//医生权限
            if(StringUtils.isNotBlank(qvo.getTeamIds())){
                String[] teamIds = qvo.getTeamIds().split(",");
                map.put("teamIds",teamIds);
                sql +=" AND TEAM_ID IN (:teamIds) ";
            }else{
                map.put("drId",qvo.getDrId());
                sql += " AND DR_ID =:drId ";
            }
            sql += " AND SIGN_STATE IN ('0', '2')";
        }else{
            if(StringUtils.isNotBlank(qvo.getSignState())){
                if("1".equals(qvo.getSignState())){
                    sql += " AND SIGN_STATE IN ('0', '2')";
                }else{
                    sql += " AND SIGN_STATE is NULL";
                }
            }
        }
        if(StringUtils.isNotBlank(qvo.getPovertyState())){
            map.put("povertyState",qvo.getPovertyState());
            sql += " AND IS_NOT_POVERTY =:povertyState ";
        }
        //去重
        if(StringUtils.isNotBlank(qvo.getRemovalState())){
            sql = sql + " AND ARCHIVING_PATIENT_IDNO IN (SELECT aa.ARCHIVING_PATIENT_IDNO FROM (SELECT ARCHIVING_PATIENT_IDNO,count(1) count FROM app_archivingcard_people a WHERE ARCHIVING_PATIENT_IDNO IS NOT NULL AND DEL_STATE ='0' GROUP BY ARCHIVING_PATIENT_IDNO HAVING count>1) aa)  ";
        }else if(qvo.getIdnoState().length==1){//身份证异常
            if("0".equals(qvo.getJdSourceType())){
                map.put("REMARKS","1");
                sql += " AND REMARKS =:REMARKS ";
            }else{
                sql += " AND (SEX IS NULL OR length(ARCHIVING_PATIENT_IDNO)!=18 OR (length(ARCHIVING_PATIENT_IDNO) =19 AND ARCHIVING_PATIENT_IDNO not LIKE '%x' ) ) ";
            }
        }else{
            if(StringUtils.isNotBlank(qvo.getNotConfirm())){//未确认
                if(qvo.getVillageState().length==1){//居委会异常
                    sql += " AND ADDR_VILLAGE_CODE IS NULL ";
                }else{
                    sql += " AND ADDR_RURAL_CODE IS NULL ";
                }
            }
        }
        if(StringUtils.isNotBlank(qvo.getProvincialInsurance())){
            map.put("PROVINCIAL_INSURANCE",qvo.getProvincialInsurance());
            sql += " AND PROVINCIAL_INSURANCE =:PROVINCIAL_INSURANCE ";
        }

        String sqlll = " order by ARCHIVING_PATIENT_IDNO ";
        totalCount = sysDao.getServiceDo().findCount(sql+sqlll,map);
        String sqls ="SELECT COUNT(1) FROM ("+ sql +" GROUP BY ARCHIVING_PATIENT_IDNO ) aa";
//        totalCountS = sysDao.getServiceDo().findCount(sqls,map);

        String sqlO = sql + " AND RHF_ID IS NULL ";
        String sqlOS ="SELECT COUNT(1) FROM ("+  sqlO + " GROUP BY ARCHIVING_PATIENT_IDNO ) aa";
        wjd = sysDao.getServiceDo().findCount(sqlO,map);
//        wjdS = sysDao.getServiceDo().findCount(sqlOS,map);

        String sqlT = sql + " AND RHF_ID IS NULL AND SIGN_STATE IN ('0', '2') ";
        String sqlTS ="SELECT COUNT(1) FROM ("+  sqlT + " GROUP BY ARCHIVING_PATIENT_IDNO ) aa";
        wjdyqy = sysDao.getServiceDo().findCount(sqlT,map);
//        wjdyqyS = sysDao.getServiceDo().findCount(sqlTS,map);

        String sqlTT = sql + " AND RHF_ID IS NOT NULL AND SIGN_STATE is NULL ";
        String sqlTTS ="SELECT COUNT(1) FROM ("+  sqlTT + " GROUP BY ARCHIVING_PATIENT_IDNO ) aa";
        yjdwqy = sysDao.getServiceDo().findCount(sqlTT,map);
//        yjdwqyS = sysDao.getServiceDo().findCount(sqlTTS,map);

        String sqlF = sql + " AND IS_NOT_POVERTY = '1' ";
        String sqlFS ="SELECT COUNT(1) FROM ("+  sqlF + " GROUP BY ARCHIVING_PATIENT_IDNO ) aa";
        tp = sysDao.getServiceDo().findCount(sqlF,map);
//        tpS = sysDao.getServiceDo().findCount(sqlFS,map);

        String sqlYc = sql + " AND ADDR_RURAL_CODE IS NULL ";
        String sqlYcS ="SELECT COUNT(1) FROM ("+  sqlYc + "  GROUP BY ARCHIVING_PATIENT_IDNO ) aa";
        ycs = sysDao.getServiceDo().findCount(sqlYc,map);
//        ycsS = sysDao.getServiceDo().findCount(sqlYcS,map);
        String sqlCf = "SELECT COUNT(1) FROM ("+sql+ " GROUP BY ARCHIVING_PATIENT_IDNO HAVING count(1)>1) aa";
//        cfs = sysDao.getServiceDo().findCount(sqlCf,map);

        returnMap.put("totalCount",totalCount);
//        returnMap.put("totalCountS",totalCountS);

        returnMap.put("wjd",wjd);
//        returnMap.put("wjdS",wjdS);

        returnMap.put("wjdyqy",wjdyqy);
//        returnMap.put("wjdyqyS",wjdyqyS);

        returnMap.put("yjdwqy",yjdwqy);
//        returnMap.put("yjdwqyS",yjdwqyS);

        returnMap.put("tp",tp);
//        returnMap.put("tpS",tpS);

        returnMap.put("ycs",ycs);
//        returnMap.put("ycsS",ycsS);
//        returnMap.put("cfs",cfs);
        return returnMap;
    }

    @Override
    public AppArchivingCardPeople findPeopleByIdno(String patientIdno,String jdSourceType) throws Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("patientIdno",patientIdno);
        String sql = "SELECT * FROM app_archivingcard_people " +
                "WHERE 1=1 and ARCHIVING_PATIENT_IDNO = :patientIdno ";
        map.put("SOURCE_TYPE",jdSourceType);
        sql += " AND SOURCE_TYPE =:SOURCE_TYPE ";
        List<AppArchivingCardPeople> list = sysDao.getServiceDo().findSqlMap(sql,map,AppArchivingCardPeople.class);
        if(list != null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<AppArchivingCardPeople> findNotJd() throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("SOURCE_TYPE","1");
        String areaCode = PropertiesUtil.getConfValue("cityJdCode");
        CdCode cityCode = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JDSOURCETYPE, areaCode);
        if(cityCode != null){
            if("0".equals(cityCode.getCodeTitle())){
                map.put("SOURCE_TYPE","3");
            }
        }
        String sql = "SELECT\n" +
                "\t*\n" +
                "FROM\n" +
                "\tAPP_ARCHIVINGCARD_PEOPLE\n" +
                "WHERE\n" +
                "\t1 = 1\n" +
                "AND ( RHF_ID IS NULL OR RHF_ID ='' ) AND SOURCE_TYPE=:SOURCE_TYPE ";
        List<AppArchivingCardPeople> list = sysDao.getServiceDo().findSqlMap(sql,map,AppArchivingCardPeople.class);
        return list;
    }

    @Override
    public void changeArchiSignState(String patientId,String notSignReason) throws Exception{
        AppPatientUser user = (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,patientId);
        if(user != null){
            if(StringUtils.isNotBlank(user.getPatientIdno())){
                List<AppArchivingCardPeople> listP = sysDao.getServiceDo().loadByPk(AppArchivingCardPeople.class,"archivingPatientIdno",user.getPatientIdno());
                if(listP != null && listP.size()>0){
                    for (AppArchivingCardPeople llp:listP){
                        llp.setSignState(null);
                        llp.setSignId(null);
                        llp.setDrId(null);
                        llp.setTeamId(null);
                        llp.setHospId(null);
                        llp.setSignFromDate(null);
                        if(StringUtils.isNotBlank(notSignReason)){
                            llp.setNotSignReason(notSignReason);
                        }else{
                            llp.setNotSignReason("15");
                        }
                        sysDao.getServiceDo().modify(llp);
                    }
                }
            }
        }
    }

    @Override
    public List<AppArchivingCardPeople> findBySignId() throws Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("SOURCE_TYPE","1");
        String areaCode = PropertiesUtil.getConfValue("cityJdCode");
        CdCode cityCode = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JDSOURCETYPE, areaCode);
        if(cityCode != null){
            if("0".equals(cityCode.getCodeTitle())){
                map.put("SOURCE_TYPE","3");
            }
        }
        String sql = "SELECT * FROM app_archivingcard_people WHERE SOURCE_TYPE =:SOURCE_TYPE\n" +
                "AND (SIGN_ID IS NULL OR SIGN_ID = '' )";
        List<AppArchivingCardPeople> list = sysDao.getServiceDo().findSqlMap(sql,map,AppArchivingCardPeople.class);
        return list;
    }

    @Override
    public List<AppArchivingCardAddrEntity> findJnameList(String upId) throws Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("upId",upId);
        map.put("SOURCE_TYPE","1");
        String sql = "SELECT\n" +
                "\tADDR_VILLAGE_NAME jname,\n" +
                "\tADDR_VILLAGE_CODE jcode\n" +
                "FROM\n" +
                "\tapp_archivingcard_people\n" +
                "WHERE\n" +
                "\tADDR_RURAL_CODE = :upId\n" +
                "AND ADDR_VILLAGE_NAME IS NOT NULL\n";
        if(StringUtils.isNotBlank(upId)){
            CdCode cdCode = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JDSOURCETYPE,AreaUtils.getAreaCode(upId,"2"));
            if(cdCode != null && "0".equals(cdCode.getCodeTitle())){
                map.put("SOURCE_TYPE","3");
            }
        }
        sql += " AND SOURCE_TYPE =:SOURCE_TYPE ";
        sql += " GROUP BY ADDR_VILLAGE_NAME ";
        List<AppArchivingCardAddrEntity> listM = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppArchivingCardAddrEntity.class);
        return listM;
    }

    @Override
    public int findCountBySourceType(String sourceType, String orgId,String areaCode) throws Exception{
        String sql = "SELECT count(1) FROM app_archivingcard_people where SOURCE_TYPE =:SOURCE_TYPE ";
        Map<String,Object> map = new HashMap<>();
        map.put("SOURCE_TYPE",sourceType);
        if(StringUtils.isNotBlank(orgId)){
            map.put("ADDR_HOSP_ID",orgId);
            sql += " and ADDR_HOSP_ID=:ADDR_HOSP_ID ";
        }
        if(StringUtils.isNotBlank(areaCode)){
            map.put("ADDR_RURAL_CODE",areaCode+"%");
            sql += " and ADDR_RURAL_CODE LIKE :ADDR_RURAL_CODE ";
        }

        int count = sysDao.getServiceDo().findCount(sql,map);
        return count;
    }

    @Override
    public String addImportExcel(Map<Integer, String> map,String areaCode)  throws Exception{
        String result1 = "成功导入"+map.size()+"条";
        System.out.println("总行数："+map.size());
        for (int i = 0; i < map.size(); i++) {//循环每条记录数据
            int num = i + 1;
            System.out.println("第几行数："+num);
            String[] ss = map.get(i).split("\\|");//取每条记录的每一个字段
            AppArchivingCardPeople people = this.getPeopleByIdno(ss[7],areaCode);
            if(people != null){
                throw new Exception("导入失败:第"+num+"行,身份证:"+ss[7]+"已存在,不能重复导入!");
            }
            people = new AppArchivingCardPeople();
            people.setAddrCountyName(ss[1]);//区名
            people.setAddrRuralName(ss[2]);//街道名称
            people.setAddrVillageName(ss[3]);//社区名称
            people.setArchivingPatientName(ss[4]);
            if ("未建档".equals(ss[6])|| StringUtils.isBlank(ss[6])) {//档案号
                people.setRhfId(null);
            } else {
                people.setRhfId(ss[6]);
            }
            //通过身份证获取性别
            people.setArchivingPatientIdno(ss[7]);//身份证号
            Map<String, Object> idNos = new HashMap<>();
            if (ss[7].length() == 18) {
                idNos = CardUtil.getCarInfo(ss[7]);
            } else {
                idNos = CardUtil.getCarInfo15W(ss[7]);
            }
            String sex = idNos.get("sex").toString();
            String birthday = idNos.get("birthday").toString();
            people.setBirthday(ExtendDate.getCalendar(birthday));//出生日期
            people.setSex(sex);//性别
            if (!"0".equals(ss[8]) && StringUtils.isNotBlank(ss[8])) {
                people.setPatientTel(ss[8]);//电话号码
            }
            if ("是".equals(ss[9])) {//是否脱贫
                people.setIsNotPoverty("1");
            } else {
                people.setIsNotPoverty("0");
            }
            people.setProvincialInsurance(ss[10]);
            if (AddressType.FZS.getValue().equals(areaCode)) {
                people.setAddrHospId(ss[12]);
            } else if (AddressType.PTS.getValue().equals(areaCode)) {
                people.setAddrHospId("pt_" + ss[12]);
            } else if (AddressType.SMS.getValue().equals(areaCode)) {
                people.setAddrHospId("sm_" + ss[12]);
            } else if (AddressType.QZS.getValue().equals(areaCode)) {
                people.setAddrHospId("qz_" + ss[12]);
            } else if (AddressType.ZZS.getValue().equals(areaCode)) {
                people.setAddrHospId("zz_" + ss[12]);
            } else if (AddressType.NPS.getValue().equals(areaCode)) {
                people.setAddrHospId("np_" + ss[12]);
            }

            String jdSourceType = "1";
            if (StringUtils.isNotBlank(areaCode)) {
                CdCode cityCode = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JDSOURCETYPE, areaCode);
                if (cityCode != null) {
                    if ("0".equals(cityCode.getCodeTitle())) {
                        jdSourceType = "3";
                    }
                }
            }
            people.setSourceType(jdSourceType);//导入数据
            Map<String, Object> map1 = new HashMap<String, Object>();
            String sqlArea = "SELECT * FROM cp_city_area t where t.AREA_NAME LIKE :NAME AND LEVEL_NAME = :LEVEL_NAME AND t.AREA_CODE like '" + areaCode + "%' ";
            map1.put("NAME", "%" + people.getAddrCountyName() + "%");
            map1.put("LEVEL_NAME", "3");
            List<CdAddress> lsArea = sysDao.getServiceDo().findSqlMap(sqlArea, map1, CdAddress.class);
            if (lsArea != null && lsArea.size() > 0) {
                people.setAddrCountyCode(lsArea.get(0).getCtcode());
//                    s.setAddrCountyName(lsArea.get(0).getAreaSname());
            }else{
                throw new Exception("导入失败:第"+num+"行,身份证:"+ss[7]+"的县(区）:"+people.getAddrCountyName()+"查询不到对应编码,请核对后重新导入!");
            }
            map1 = new HashMap<String, Object>();
            map1.put("NAME", "%" + people.getAddrRuralName() + "%");
            map1.put("LEVEL_NAME", "4");
            lsArea = sysDao.getServiceDo().findSqlMap(sqlArea, map1, CdAddress.class);
            if (lsArea != null && lsArea.size() > 0) {
                people.setAddrRuralCode(lsArea.get(0).getCtcode());
            }else{
                throw new Exception("导入失败:第"+num+"行,身份证:"+ss[7]+"的乡、镇（居）:"+people.getAddrRuralName()+"查询不到对应编码,请核对后重新导入!");
            }
            if (StringUtils.isNotBlank(people.getAddrVillageName())) {
                map1 = new HashMap<String, Object>();
                String re = null;
                if (people.getAddrVillageName().length() > 2) {
                    re = people.getAddrVillageName().substring(0, 2);
                } else {
                    re = people.getAddrVillageName();
                }
                if (lsArea != null && lsArea.size() > 0) {
                    map1.put("NAME", "%" + lsArea.get(0).getAreaSname() + re + "%");
                } else {
                    map1.put("NAME", "%" + people.getAddrRuralName() + re + "%");
                }
//                    map.put("NAME","%"+s.getAddrVillageName()+"%");
                map1.put("LEVEL_NAME", "5");
                lsArea = sysDao.getServiceDo().findSqlMap(sqlArea, map1, CdAddress.class);
                if (lsArea != null && lsArea.size() > 0) {
                    people.setAddrVillageCode(lsArea.get(0).getCtcode());
//                        s.setAddrVillageName(lsArea.get(0).getAreaSname());
                }
            }
            String signAreaCode = null;
            //根据身份证查询居民信息，在查询签约信息 如果此处要开的话，还要建档立卡标签表数据也要插入
            AppPatientUser patientUser = sysDao.getAppPatientUserDao().findByIdnoAndName(people.getArchivingPatientIdno(), people.getArchivingPatientName());
            if (patientUser != null) {
                AppSignForm form = sysDao.getAppSignformDao().getSignFormUserId(patientUser.getId());
                if (form != null) {
                    if (StringUtils.isBlank(people.getPatientTel())) {
                        if (StringUtils.isNotBlank(patientUser.getPatientTel())) {
                            people.setPatientTel(patientUser.getPatientTel());
                        }
                    } else {
                        if (people.getPatientTel().equals("无")) {
                            if (StringUtils.isNotBlank(patientUser.getPatientTel())) {
                                people.setPatientTel(patientUser.getPatientTel());
                            }
                        }
                    }
                    //只有签约在本市的才算已经签约
                    if(AreaUtils.getAreaCode(form.getSignAreaCode(),"2").equals(AreaUtils.getAreaCode(people.getAddrCountyCode(),"2"))){
                        people.setSignId(form.getId());
                        people.setTeamId(form.getSignTeamId());
                        people.setHospId(form.getSignHospId());
                        people.setSignFromDate(form.getSignFromDate());
                        people.setDrId(form.getSignDrId());
                        people.setSignState(form.getSignState());
                        signAreaCode = form.getSignAreaCode();
                    }
                }
            }
            //不管是否已经签约，未签约原因都初始默认值15暂时未签约
            people.setNotSignReason("15");
            sysDao.getServiceDo().add(people);
            //处理label数据
            if(StringUtils.isNotBlank(people.getSignId())){
                String result = null;
                List<AppLabelGroup> lsGroup = sysDao.getAppLabelGroupDao().findBySignGroup(people.getSignId(),"3");
                if(lsGroup != null && lsGroup.size()>0){
                    for(AppLabelGroup group : lsGroup){
                        if(result != null){
                            result = result+ ";"+group.getLabelValue();
                        }else{
                            result = group.getLabelValue();
                        }
                    }
                }
                if(StringUtils.isNotBlank(result)){
                    String[] res = result.split(";");
                    sysDao.getAppLabelGroupDao().addLabel(res,people.getId(),null,signAreaCode, LabelManageType.JDLK.getValue());
                }
            }else{
                HomeServiceItemsQvo qqvo = new HomeServiceItemsQvo();
                qqvo.setIdno(people.getArchivingPatientIdno());
                qqvo.setType("2");
                //获取居民服务类型
                String cityCode = "";
                CdCode value = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE, people.getAddrCountyCode().substring(0,4));
                if(value!=null){
                    cityCode = value.getCodeTitle();
                }
                String state = PropertiesUtil.getConfValue("openTheInterface");
                CloseableHttpClient client = HttpClients.createDefault();
                JSONObject jsonParam = new JSONObject();
                jsonParam.put("idNo", people.getArchivingPatientIdno());
                jsonParam.put("urlType", cityCode);
                String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=findFwType";
                String str = null;
                str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
                if(StringUtils.isNotBlank(str)){
                    JSONObject jsonall = JSONObject.fromObject(str);
                    if (jsonall.get("vo") != null && jsonall.get("msgCode").equals("800")) {
                        AppDrPatientFwEntity entity = JSON.parseObject(jsonall.get("vo").toString(), AppDrPatientFwEntity.class);
                        if(entity != null){
                            String resu  = null;
                            if(entity.getIs06child().equals("1")){//儿童
                                if(resu != null){
                                    resu  += ";2";
                                }else{
                                    resu  = "2";
                                }
                            }
                            if(entity.getIslnr().equals("1")){//老年人
                                if(resu != null){
                                    resu  += ";4";
                                }else{
                                    resu  = "4";
                                }
                            }
                            if(entity.getIsycf().equals("1")){//孕产妇
                                if(resu != null){
                                    resu  += ";3";
                                }else{
                                    resu  = "3";
                                }
                            }
                            if(entity.getIsgxy().equals("1")){//高血压
                                if(resu != null){
                                    resu  += ";5";
                                }else{
                                    resu  = "5";
                                }
                            }
                            if(entity.getIstnb().equals("1")){//糖尿病
                                if(resu != null){
                                    resu  += ";6";
                                }else{
                                    resu  = "6";
                                }
                            }
                            if(entity.getIsjhb().equals("1")){//及合并
                                if(resu != null){
                                    resu  += ";8";
                                }else{
                                    resu  = "8";
                                }
                            }
                            if(entity.getIscjr().equals("1")){//残疾人
                                if(resu != null){
                                    resu  += ";9";
                                }else{
                                    resu  = "9";
                                }
                            }
                            if(entity.getIszxjsb().equals("1")){//精神病
                                if(resu != null){
                                    resu  += ";7";
                                }else{
                                    resu  = "7";
                                }
                            }
                            if(entity.getComservice().equals("1")){//普通服务
                                if(resu != null){
                                    resu  += ";1";
                                }else{
                                    resu  = "1";
                                }
                            }
                            if(StringUtils.isNotBlank(resu)){
                                String[] res = resu.split(";");
                                sysDao.getAppLabelGroupDao().addLabel(res,people.getId(),null,signAreaCode, LabelManageType.JDLK.getValue());
                            }else{
                                String[] res = "1".split(";");
                                sysDao.getAppLabelGroupDao().addLabel(res,people.getId(),null,signAreaCode, LabelManageType.JDLK.getValue());
                            }


                        }else{
                            if(StringUtils.isNotBlank(people.getAge())){
                                int age = Integer.parseInt(people.getAge());
                                if(age >= 65){
                                    String  resu  = "4";
                                    String[] res = resu.split(";");
                                    sysDao.getAppLabelGroupDao().addLabel(res,people.getId(),null,signAreaCode, LabelManageType.JDLK.getValue());
                                }else if(age <=6){
                                    String  resu  = "2";
                                    String[] res = resu.split(";");
                                    sysDao.getAppLabelGroupDao().addLabel(res,people.getId(),null,signAreaCode, LabelManageType.JDLK.getValue());
                                }else{
                                    String resu  = "1";
                                    String[] res = resu.split(";");
                                    sysDao.getAppLabelGroupDao().addLabel(res,people.getId(),null,signAreaCode, LabelManageType.JDLK.getValue());
                                }
                            }else{
                                String resu  = "1";
                                String[] res = resu.split(";");
                                sysDao.getAppLabelGroupDao().addLabel(res,people.getId(),null,signAreaCode, LabelManageType.JDLK.getValue());
                            }
                        }
                    }else{
                        if(StringUtils.isNotBlank(people.getAge())){
                            int age = Integer.parseInt(people.getAge());
                            if(age >= 65){
                                String  resu  = "4";
                                String[] res = resu.split(";");
                                sysDao.getAppLabelGroupDao().addLabel(res,people.getId(),null,signAreaCode, LabelManageType.JDLK.getValue());
                            }else if(age <=6){
                                String  resu  = "2";
                                String[] res = resu.split(";");
                                sysDao.getAppLabelGroupDao().addLabel(res,people.getId(),null,signAreaCode, LabelManageType.JDLK.getValue());
                            }else{
                                String resu  = "1";
                                String[] res = resu.split(";");
                                sysDao.getAppLabelGroupDao().addLabel(res,people.getId(),null,signAreaCode, LabelManageType.JDLK.getValue());
                            }
                        }else{
                            String resu  = "1";
                            String[] res = resu.split(";");
                            sysDao.getAppLabelGroupDao().addLabel(res,people.getId(),null,signAreaCode, LabelManageType.JDLK.getValue());
                        }
                    }
                }else{
                    if(StringUtils.isNotBlank(people.getAge())){
                        int age = Integer.parseInt(people.getAge());
                        if(age >= 65){
                            String  resu  = "4";
                            String[] res = resu.split(";");
                            sysDao.getAppLabelGroupDao().addLabel(res,people.getId(),null,signAreaCode, LabelManageType.JDLK.getValue());
                        }else if(age <=6){
                            String  resu  = "2";
                            String[] res = resu.split(";");
                            sysDao.getAppLabelGroupDao().addLabel(res,people.getId(),null,signAreaCode, LabelManageType.JDLK.getValue());
                        }else{
                            String resu  = "1";
                            String[] res = resu.split(";");
                            sysDao.getAppLabelGroupDao().addLabel(res,people.getId(),null,signAreaCode, LabelManageType.JDLK.getValue());
                        }
                    }else{
                        String resu  = "1";
                        String[] res = resu.split(";");
                        sysDao.getAppLabelGroupDao().addLabel(res,people.getId(),null,signAreaCode, LabelManageType.JDLK.getValue());
                    }

                }
            }
        }
        return result1;
    }

    /**
     * 修改建档立卡数据
     * @param map
     * @param areaCode
     * @return
     */
    @Override
    public String modifyImportExcel(Map<Integer, String> map, String areaCode) throws Exception {
        String result = "成功修改"+map.size()+"条数据";
        for (int i = 0; i < map.size(); i++) {//循环每条记录数据
            int num = i + 1;
            String[] ss = map.get(i).split("\\|");//取每条记录的每一个字段
            //根据身份证查询数据
            String idno = ss[0];//身份证号
            AppArchivingCardPeople people = this.getPeopleByIdno(idno,areaCode);
            if(people != null){
                if(ss.length >1){
                    if(StringUtils.isNotBlank(ss[1])){
                        people.setArchivingPatientName(ss[1]);//姓名
                    }
                }
                if(ss.length > 2){
                    if(StringUtils.isNotBlank(ss[2])){
                        people.setArchivingPatientIdno(ss[2]);//身份证
                        Map<String,Object> idNos = new HashMap<>();
                        if(ss[2].length() == 18){
                            idNos = CardUtil.getCarInfo(ss[2]);
                        }else{
                            idNos = CardUtil.getCarInfo15W(ss[2]);
                        }
                        String sex = idNos.get("sex").toString();
                        String birthday = idNos.get("birthday").toString();
                        people.setBirthday(ExtendDate.getCalendar(birthday));//出生日期
                        people.setSex(sex);//性别
                    }
                }
                if(ss.length > 3 ){
                    if(StringUtils.isNotBlank(ss[3])){
                        people.setProvincialInsurance(ss[3]);//对象类型
                    }
                }
                if(ss.length > 5){
                    if(StringUtils.isNotBlank(ss[5])){
                        if(AddressType.FZS.getValue().equals(areaCode)){//归管机构ID
                            people.setAddrHospId(ss[5]);
                        }else if(AddressType.PTS.getValue().equals(areaCode)){
                            people.setAddrHospId("pt_"+ss[5]);
                        }else if(AddressType.SMS.getValue().equals(areaCode)){
                            people.setAddrHospId("sm_"+ss[5]);
                        }else if(AddressType.QZS.getValue().equals(areaCode)){
                            people.setAddrHospId("qz_"+ss[5]);
                        }else if(AddressType.ZZS.getValue().equals(areaCode)){
                            people.setAddrHospId("zz_"+ss[5]);
                        }else if(AddressType.NPS.getValue().equals(areaCode)){
                            people.setAddrHospId("np_"+ss[5]);
                        }
                    }
                }

                if(ss.length>6){
                    if(StringUtils.isNotBlank(ss[6])){
                        people.setRhfId(ss[6]);
                    }
                }

                if(ss.length > 7){
                    if(StringUtils.isNotBlank(ss[7])){
                        people.setPatientTel(ss[7]);
                    }
                }
                if(ss.length > 8){
                    if(StringUtils.isNotBlank(ss[8])){
                        people.setIsNotPoverty(ss[8]);
                    }
                }
                if(ss.length>9){
                    if(StringUtils.isNotBlank(ss[9])){
                        //如果区县不为空要修改，则乡镇、村和归管单位就不能为空
                        people.setAddrCountyName(ss[9]);
                        if(StringUtils.isBlank(ss[5])){
                            throw new Exception("导入失败:第"+num+"行,修改区县则归管单位不能为空!");
                        }
                        if(ss.length<12){

                            throw new Exception("导入失败:第"+num+"行,修改区县则乡镇、村不能为空!");
                        }
                        people.setAddrRuralName(ss[10]);
                        people.setAddrVillageName(ss[11]);
                        Map<String, Object> map1 = new HashMap<String, Object>();
                        String sqlArea = "SELECT * FROM cp_city_area t where t.AREA_NAME LIKE :NAME AND LEVEL_NAME = :LEVEL_NAME AND t.AREA_CODE like '" + areaCode + "%' ";
                        map1.put("NAME", "%" + people.getAddrCountyName() + "%");
                        map1.put("LEVEL_NAME", "3");
                        List<CdAddress> lsArea = sysDao.getServiceDo().findSqlMap(sqlArea, map1, CdAddress.class);
                        if (lsArea != null && lsArea.size() > 0) {
                            people.setAddrCountyCode(lsArea.get(0).getCtcode());
                        }else{
                            throw new Exception("导入失败:第"+num+"行,身份证:"+ss[0]+"的县(区）:"+people.getAddrCountyName()+"查询不到对应编码,请核对后重新导入!");
                        }
                        map1 = new HashMap<String, Object>();
                        map1.put("NAME", "%" + people.getAddrRuralName() + "%");
                        map1.put("LEVEL_NAME", "4");
                        lsArea = sysDao.getServiceDo().findSqlMap(sqlArea, map1, CdAddress.class);
                        if (lsArea != null && lsArea.size() > 0) {
                            people.setAddrRuralCode(lsArea.get(0).getCtcode());
                        }else{
                            throw new Exception("导入失败:第"+num+"行,身份证:"+ss[0]+"的乡、镇（居）:"+people.getAddrRuralName()+"查询不到对应编码,请核对后重新导入!");
                        }
                        if (StringUtils.isNotBlank(people.getAddrVillageName())) {
                            map1 = new HashMap<String, Object>();
                            String re = null;
                            if (people.getAddrVillageName().length() > 2) {
                                re = people.getAddrVillageName().substring(0, 2);
                            } else {
                                re = people.getAddrVillageName();
                            }
                            if (lsArea != null && lsArea.size() > 0) {
                                map1.put("NAME", "%" + lsArea.get(0).getAreaSname() + re + "%");
                            } else {
                                map1.put("NAME", "%" + people.getAddrRuralName() + re + "%");
                            }
                            map1.put("LEVEL_NAME", "5");
                            lsArea = sysDao.getServiceDo().findSqlMap(sqlArea, map1, CdAddress.class);
                            if (lsArea != null && lsArea.size() > 0) {
                                people.setAddrVillageCode(lsArea.get(0).getCtcode());
                            }
                        }
                    }
                }
                sysDao.getServiceDo().modify(people);
            }else{
                throw new Exception("导入失败:第"+num+"行,身份证:"+ss[0]+"修改失败,查询不到对应的信息,请核对后重新导入!");
            }
        }
        return result;
    }

    /**
     * 删除建档立卡数据
     * @param map
     * @param areaCode
     * @return
     */
    @Override
    public String delImportExcel(Map<Integer, String> map, String areaCode) throws Exception{
        String result = "成功删除"+map.size()+"条数据";
        for (int i = 0; i < map.size(); i++) {//循环每条记录数据
            int num = i + 1;
            String[] ss = map.get(i).split("\\|");//取每条记录的每一个字段
            //根据身份证号和医保来源查询建档立卡数据，改来源字段值为999
            String idno = ss[7];//身份证号
            AppArchivingCardPeople people = this.getPeopleByIdno(idno,areaCode);
            if(people != null){
                people.setSourceType("999");
                sysDao.getServiceDo().modify(people);
                //修改签约选择的经济类型,只有建档立卡
                AppPatientUser user = sysDao.getAppPatientUserDao().findPatientIdNo(people.getArchivingPatientIdno());
                if(user != null){
                    AppSignForm form = sysDao.getAppSignformDao().findSignFormByUserState(user.getId());
                    if(form != null){
                        List<AppLabelEconomics> list = sysDao.getAppLabelGroupDao().listLabelEconomics(people.getSignId());
                        if(list != null && list.size()>0){
                            for(AppLabelEconomics le:list){
                                if(list.size()== 1 && EconomicType.JDLKPKRK.getValue().equals(le.getLabelValue())){//有且只有一个建档立卡经济类型，则修改为一般人群
                                    AppLabelManage manage = sysDao.getAppLabelManageDao().findLabelByValue("4",EconomicType.YBRK.getValue());
                                    if(manage != null){
                                        le.setLabelId(manage.getId());
                                        le.setLabelValue(manage.getLabelValue());
                                        le.setLabelTitle(manage.getLabelTitle());
                                        sysDao.getServiceDo().modify(le);
                                    }
                                }else{//不是只有建档立卡经济类型，则删除含有建档立卡经济类型的数据
                                    if(EconomicType.JDLKPKRK.getValue().equals(le.getLabelValue())){
                                        sysDao.getServiceDo().delete(le);
                                    }
                                }
                            }
                        }
                    }
                }

            }else{
                throw new Exception("导入失败:第"+num+"行,身份证:"+ss[7]+"删除失败,查询不到对应的信息,请核对后重新导入!");
            }
        }
        return result;
    }

    public AppArchivingCardPeople getPeopleByIdno(String idno,String areaCode) throws Exception{
        String jdSourceType = "1";
        if (StringUtils.isNotBlank(areaCode)) {
            CdCode cityCode = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JDSOURCETYPE, areaCode);
            if (cityCode != null) {
                if ("0".equals(cityCode.getCodeTitle())) {
                    jdSourceType = "3";
                }
            }
        }
        Map<String,Object> map = new HashMap<>();
        map.put("idno",idno);
        map.put("sourceType",jdSourceType);
        String sql = "SELECT * FROM APP_ARCHIVINGCARD_PEOPLE WHERE 1=1 "
                + " AND ARCHIVING_PATIENT_IDNO =:idno "
                + " AND SOURCE_TYPE =:sourceType ";
        List<AppArchivingCardPeople> list = sysDao.getServiceDo().findSqlMap(sql,map,AppArchivingCardPeople.class);
        if(list != null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<AppArchivingCardPeople> findByNotSignId() throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("SOURCE_TYPE","1");
        String areaCode = PropertiesUtil.getConfValue("cityJdCode");
        CdCode cityCode = sysDao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_JDSOURCETYPE, areaCode);
        if(cityCode != null){
            if("0".equals(cityCode.getCodeTitle())){
                map.put("SOURCE_TYPE","3");
            }
        }
        String sql = "SELECT * FROM app_archivingcard_people WHERE SOURCE_TYPE =:SOURCE_TYPE\n" +
                "AND SIGN_ID IS NOT NULL AND SIGN_STATE IN ('0','2') ";
//        sql += " AND id = '00010592-fac2-41f7-a44a-5e5a2c359523' ";
        List<AppArchivingCardPeople> list = sysDao.getServiceDo().findSqlMap(sql,map,AppArchivingCardPeople.class);
        return list;
    }
}
