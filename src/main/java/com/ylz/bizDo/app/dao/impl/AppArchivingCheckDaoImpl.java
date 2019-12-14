package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppArchivingCheckDao;
import com.ylz.bizDo.app.entity.AppArchivingCardCPEntiy;
import com.ylz.bizDo.app.entity.AppArchivingCardPeopeEntity;
import com.ylz.bizDo.app.entity.AppArchivingCheckEntity;
import com.ylz.bizDo.app.vo.AppArchivingCardPeopleQvo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.comEnum.AppRoleType;
import com.ylz.packcommon.common.util.AreaUtils;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2018/11/8.
 */
@Service("appArchivingCheckDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppArchivingCheckDaoImpl implements AppArchivingCheckDao {

    @Autowired
    private SysDao sysDao;

    @Override
    public AppArchivingCheckEntity findByArchivingId(String archivingId,String signId) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("archivingId",archivingId);
        String sql = "SELECT " +
                "ID id,\n" +
                "ACC_ARCHIVING_ID archivingId,\n" +
                "ACC_PATIENT_NO patientNo,\n" +
                "ACC_FAMILY_SIZE familySize,\n" +
                "ACC_POVERTY_STATE povertyState,\n" +
                "ACC_PATIENT_NAME patientName,\n" +
                "ACC_PATIENT_SEX patientSex,\n" +
                "ACC_PATIENT_BIRTHDAY patientBirthDay,\n" +
                "ACC_PATIENT_IDNO patientIdno,\n" +
                "ACC_HOUSEHOLD_RELATIONSHIP householdRelationship,\n" +
                "ACC_RESIDENCE_STATE residenceState,\n" +
                "ACC_FPA_STATE fpaState,\n" +
                "ACC_ARCHIVING_CARD_STATE archivingCardState,\n" +
                "ACC_MARITAL_STATUS maritalStatus,\n" +
                "ACC_FAMILY_TYPE familyType,\n" +
                "ACC_PATIENT_CARD patientCard,\n" +
                "ACC_PATIENT_JMDA patientJmda,\n" +
                "ACC_DOMICILE_PLACE_COUNTY domicilePlaceCounty,\n" +
                "ACC_DOMICILE_PLACE_TOWN domicilePlaceTown,\n" +
                "ACC_DOMICILE_PLACE_VILLAGE domicilePlaceVillage,\n" +
                "ACC_DOMICILE_PLACE accDonicilePlace,\n" +
                "ACC_RESIDENCE_PLACE_COUNTY residencePlaceCounty,\n" +
                "ACC_RESIDENCE_PLACE_TOWN residencePlaceTown,\n" +
                "ACC_RESIDENCE_PLACE_VILLAGE residencePlaceVillage,\n" +
                "ACC_RESIDENCE_PLACE residencePlace,\n" +
                "ACC_RESIDENCE_PLACE_ADDR residencePlaceAddr,\n" +
                "ACC_PATIENT_TEL patientTel,\n" +
                "ACC_SERVICE_TYPE serviceType,\n" +
                "ACC_SERVICE_TYPE fwType,\n" +
                "ACC_SPECIAL_FAMILY specialFamily,\n" +
                "ACC_TOTAL_FEE totalFee,\n" +
                "ACC_ILLNESS_NAME illnessName,\n" +
                "ACC_PAPER_SIGN_STATE paperSignState,\n" +
                "ACC_SIGN_AGREEMENT_STATE signAgreementState,\n" +
                "ACC_SERVICE_HANDBOOK_STATE serviceHandbookState,\n" +
                "ACC_CONTACT_CARD_STATE contactCardState,\n" +
                "ACC_SIGN_FROM_DATE signFromDate,\n" +
                "ACC_SIGN_TO_DATE signToDate,\n" +
                "ACC_NOT_SIGN_REASON notSignReason,\n" +
                "ACC_NOT_SIGN_REASON_OTHER notSignReasonOther,\n" +
                "ACC_JMDA_TIME jmdaTime,\n" +
                "ACC_JMDA_LAST_UPDATE_TIME jmdaLastUpdateTime,\n" +
                "ACC_SIGN_PE_NUM signPeNum,\n" +
                "ACC_LAST_PE_TIME lastPeTime,\n" +
                "ACC_ET_FOLLOW_NUM etFollowNum,\n" +
                "ACC_ET_LAST_FOLLOW_TIME etLastFollowTime,\n" +
                "ACC_YCF_FOLLOW_NUM ycfFollowNum,\n" +
                "ACC_YCF_LAST_FOLLOW_TIME ycfLastFollowTime,\n" +
                "ACC_GXY_NUM gxyNum,\n" +
                "ACC_GXY_LAST_TIME gxyLastTime,\n" +
                "ACC_GXY_FOLLOW_NUM gxyFollowNum,\n" +
                "ACC_GXY_LAST_FOLLOW_TIME gxyLastFollowTime,\n" +
                "ACC_TNB_NUM tnbNum,\n" +
                "ACC_TNB_LAST_TIME tnbLastTime,\n" +
                "ACC_TNB_FOLLOW_NUM tnbFollowNum,\n" +
                "ACC_TNB_LAST_FOLLOW_TIME tnbLastFollowTime,\n" +
                "ACC_JSB_FOLLOW_NUM jsbFollowNum,\n" +
                "ACC_JSB_LAST_FOLLOW_TIME jsbLastFollowTime,\n" +
                "ACC_KNOW_HELP_POOR_POLICY knowHelpPoorPolicy,\n" +
                "ACC_SATISFIED_STATE satisfiedState,\n" +
                "ACC_INSPECTOR_ONE_URL inspectorOneUrl,\n" +
                "ACC_INSPECTOR_TWO_URL inspectorTwoUrl,\n" +
                "ACC_CHECK_DATE checkDate,\n" +
                "ACC_CREATE_ID createId,\n" +
                "ACC_CREATE_NAME createName," +
                "ACC_SIGN_ID signId," +
                "ACC_CHECK_YW_DATE checkYwDate," +
                "ACC_OBJECT_TYPE objectType," +
                "ACC_TOTAL_COST totalCost," +
                "ACC_ZF_FEE zfFee," +
                "ACC_NCMS_FEE ncmsFee," +
                "ACC_CIVIL_ASSISTANCE civilAssistance," +
                "ACC_SGCPA sgcpa," +
                "ACC_OTHER_FUND otherFund " +
                "FROM APP_ARCHIVING_CARD_CHECK " +
                "WHERE 1=1 ";
        sql += " AND ACC_ARCHIVING_ID =:archivingId ";
        if(StringUtils.isNotBlank(signId)){
            map.put("signId",signId);
            sql += " AND ACC_SIGN_ID =:signId ";
        }

        List<AppArchivingCheckEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppArchivingCheckEntity.class);
        if(list != null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    @Override
    public  List<Map> findByQvo(AppArchivingCardPeopleQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("DEL_STATE","0");
        String sql = "SELECT " +
                "GROUP_CONCAT(id) archivingId " +
                "FROM APP_ARCHIVINGCARD_PEOPLE WHERE 1=1  ";//AND SOURCE_TYPE ='1'
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
            map.put("areaCode", AreaUtils.getAreaCode(qvo.getPatientCity())+"%");
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
        String startTime = ExtendDate.getYMD(Calendar.getInstance())+" 00:00:00";
        String endTime = ExtendDate.getYMD(Calendar.getInstance())+ " 23:59:59";
        map.put("startTime",startTime);
        map.put("endTime",endTime);

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
            sql += " AND PROVINCIAL_INSURANCE =:PROVINCIAL_INSURANCE";

        }
        List<Map> list = sysDao.getServiceDo().findSqlMap(sql,map,qvo);
        return list;
    }

    @Override
    public List<AppArchivingCardCPEntiy> findPeopleList(AppArchivingCardPeopleQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("DEL_STATE","0");
        String sql = "SELECT " +
                "a.ID id," +
                "a.RHF_ID rhfId,\n" +
                "a.ADDR_COUNTY_CODE addrCountyCode,\n" +
                "a.ADDR_COUNTY_NAME addrCountyName,\n" +
                "a.ADDR_RURAL_CODE addrRuralCode,\n" +
                "a.ADDR_RURAL_NAME addrRuralName,\n" +
                "a.ADDR_VILLAGE_CODE addrVillageCode,\n" +
                "a.ADDR_VILLAGE_NAME addrVillageName,\n" +
                "a.ARCHIVING_PATIENT_NAME patientName,\n" +
                "a.ARCHIVING_PATIENT_IDNO patientIdno,\n" +
                "a.SIGN_STATE signState,\n" +
                "a.SOURCE_TYPE sourceType," +
                "a.IS_NOT_POVERTY isNotPoverty,\n" +
                "a.NOT_SIGN_REASON notSignReason," +
                "a.PATIENT_TEL patientTel," +
                "a.LOW_INSURED lowInsured," +
                "a.POOR_HOUSEHOLDS poorHouseholds," +
                "a.TEAM_ID teamId," +
                "a.DEL_STATE delState," +
                "a.DR_ID drId," +
                "a.PROVINCIAL_INSURANCE provincialInsurance," +
                "a.SIGN_FROM_DATE signFromDate," +
                "a.SIGN_ID signId," +
                "'' addState " +
                "FROM APP_ARCHIVINGCARD_PEOPLE a WHERE 1=1  ";//AND SOURCE_TYPE ='1'
        boolean flag = false;
        if(qvo.getIsHaveState() != null){
            if(qvo.getIsHaveState().length == 1){
                flag = true;
            }
        }
        if (StringUtils.isNotBlank(qvo.getCheckStartDate())) {
            flag = true;
        }

        if(StringUtils.isNotBlank(qvo.getCheckEndDate())){
            flag = true;
        }

        if(flag ){//查询有核查表的
            sql = "SELECT " +
                    "a.ID id," +
                    "a.RHF_ID rhfId,\n" +
                    "a.ADDR_COUNTY_CODE addrCountyCode,\n" +
                    "a.ADDR_COUNTY_NAME addrCountyName,\n" +
                    "a.ADDR_RURAL_CODE addrRuralCode,\n" +
                    "a.ADDR_RURAL_NAME addrRuralName,\n" +
                    "a.ADDR_VILLAGE_CODE addrVillageCode,\n" +
                    "a.ADDR_VILLAGE_NAME addrVillageName,\n" +
                    "a.ARCHIVING_PATIENT_NAME patientName,\n" +
                    "a.ARCHIVING_PATIENT_IDNO patientIdno,\n" +
                    "a.SIGN_STATE signState,\n" +
                    "a.SOURCE_TYPE sourceType," +
                    "a.IS_NOT_POVERTY isNotPoverty,\n" +
                    "a.NOT_SIGN_REASON notSignReason," +
                    "a.PATIENT_TEL patientTel," +
                    "a.LOW_INSURED lowInsured," +
                    "a.POOR_HOUSEHOLDS poorHouseholds," +
                    "a.TEAM_ID teamId," +
                    "a.DEL_STATE delState," +
                    "a.DR_ID drId," +
                    "a.PROVINCIAL_INSURANCE provincialInsurance," +
                    "a.SIGN_FROM_DATE signFromDate," +
                    "a.SIGN_ID signId," +
                    "'1' addState " +
                    "FROM APP_ARCHIVINGCARD_PEOPLE a " +
                    " INNER JOIN app_archiving_card_check b ON a.ID = b.ACC_ARCHIVING_ID " +
                    "WHERE 1=1 " +
                    " AND a.SIGN_ID IS NOT NULL AND b.ACC_SIGN_ID = a.SIGN_ID ";

            if(StringUtils.isNotBlank(qvo.getCheckStartDate())){//核查开始日期
                map.put("checkStartDate",qvo.getCheckStartDate()+" 00:00:00");
                sql += " AND ( b.ACC_CHECK_DATE >=:checkStartDate OR b.ACC_CHECK_YW_DATE >=:checkStartDate ) ";
            }
            if(StringUtils.isNotBlank(qvo.getCheckEndDate())){//核查结束日期
                map.put("checkEndDate",qvo.getCheckEndDate()+" 23:59:59");
                sql += " AND ( b.ACC_CHECK_DATE <=:checkEndDate OR b.ACC_CHECK_YW_DATE <=:checkEndDate ) ";
            }
        }

        if("0".equals(qvo.getJdSourceType())){
            map.put("SOURCE_TYPE","3");
        }else{
            map.put("SOURCE_TYPE","1");
        }
        sql += " AND a.SOURCE_TYPE =:SOURCE_TYPE ";
        //姓名模糊查询
        if(StringUtils.isNotBlank(qvo.getPatientName())){
            map.put("PATIENT_NAME","%"+qvo.getPatientName()+"%");
            sql += " AND a.ARCHIVING_PATIENT_NAME LIKE :PATIENT_NAME ";

        }

        //身份证查询
        if(StringUtils.isNotBlank(qvo.getPatientIdno())){
            map.put("PATIENT_IDNO",qvo.getPatientIdno());
            sql += " AND a.ARCHIVING_PATIENT_IDNO =:PATIENT_IDNO ";

        }

        if(StringUtils.isNotBlank(qvo.getPatientNeighborhoodCommittee())){
            map.put("areaCode",qvo.getPatientNeighborhoodCommittee());
            if("0".equals(qvo.getJdSourceType())){
                map.put("streetCode",qvo.getPatientStreet());
                sql += " AND a.ADDR_RURAL_CODE = :streetCode ";
                sql += " AND a.ADDR_VILLAGE_NAME = :areaCode ";
            }else{
                sql += " AND a.ADDR_VILLAGE_CODE = :areaCode ";
            }

        }else if(StringUtils.isNotBlank(qvo.getPatientStreet())){
            map.put("areaCode",qvo.getPatientStreet());
//                    map.put("areaCode",AreaUtils.getAreaCode(qvo.getPatientStreet(),AppRoleType.SHEQU.getValue())+"%");
            sql += " AND a.ADDR_RURAL_CODE = :areaCode ";
        }else if(StringUtils.isNotBlank(qvo.getPatientArea())){
            map.put("areaCode",qvo.getPatientArea());
//                    map.put("areaCode",AreaUtils.getAreaCode(qvo.getPatientArea(),AppRoleType.QU.getValue())+"%");
            sql += " AND a.ADDR_COUNTY_CODE = :areaCode ";
        }else if(StringUtils.isNotBlank(qvo.getPatientCity())){
            map.put("areaCode",AreaUtils.getAreaCode(qvo.getPatientCity())+"%");
            sql += " AND a.ADDR_COUNTY_CODE LIKE :areaCode ";
        }else{
            map.put("areaCode","35%");
            sql += " AND a.ADDR_COUNTY_CODE LIKE :areaCode ";
        }

        if(StringUtils.isNotBlank(qvo.getJdState())){
            if("0".equals(qvo.getJdState())){
                sql += " AND a.RHF_ID IS NULL ";
            }else{
                sql += " AND a.RHF_ID IS NOT NULL ";
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
                sql +=" AND a.TEAM_ID IN (:teamIds) ";
            }else{
                map.put("drId",qvo.getDrId());
                sql += " AND a.DR_ID =:drId ";
            }
            if(StringUtils.isNotBlank(qvo.getSignState())){
                if("1".equals(qvo.getSignState())){
                    sql += " AND a.SIGN_STATE IN ('0', '2')";
                    if(qvo.getToDayState().length>0){//查询当天签约信息
                        if("1".equals(qvo.getToDayState()[0])){
                            sql += " AND a.SIGN_FROM_DATE >= :startTime ";
                            sql += " AND a.SIGN_FROM_DATE <= :endTime ";
                        }
                        /*else{
                            sql += " AND SIGN_FROM_DATE <= :startTime ";
                        }*/
                    }
                    /*else{
                        sql += " AND SIGN_FROM_DATE <= :startTime ";
                    }*/
                }else{
                    sql += " AND a.SIGN_STATE is NULL";
                }
            }
        }else{
            if(StringUtils.isNotBlank(qvo.getSignState())){
                if("1".equals(qvo.getSignState())){
                    sql += " AND a.SIGN_STATE IN ('0', '2')";
                    if(qvo.getToDayState().length>0){//查询当天签约信息
                        if("1".equals(qvo.getToDayState()[0])){
                            sql += " AND a.SIGN_FROM_DATE >= :startTime ";
                            sql += " AND a.SIGN_FROM_DATE <= :endTime ";
                        }
                        /*else{
                            sql += " AND SIGN_FROM_DATE <= :startTime ";
                        }*/
                    }
                    /*else{
                        sql += " AND SIGN_FROM_DATE <= :startTime ";
                    }*/
                }else{
                    sql += " AND a.SIGN_STATE is NULL";
                }
            }
        }

        if(StringUtils.isNotBlank(qvo.getPovertyState())){
            map.put("povertyState",qvo.getPovertyState());
            sql += " AND a.IS_NOT_POVERTY =:povertyState ";
        }
        if(StringUtils.isNotBlank(qvo.getNotConfirm())){//未确认
            sql += " AND a.ADDR_RURAL_CODE IS NULL ";
        }

        if(qvo.getCancelState() != null){
            if(qvo.getCancelState().length == 1){//查询注销数据
                map.put("DEL_STATE",new String[]{"3","4"});
                sql += " AND a.DEL_STATE IN (:DEL_STATE) ";
            }
        }

        if(StringUtils.isNotBlank(qvo.getProvincialInsurance())){//对象类型
            map.put("PROVINCIAL_INSURANCE",qvo.getProvincialInsurance());
            sql += " AND a.PROVINCIAL_INSURANCE =:PROVINCIAL_INSURANCE";

        }


        List<AppArchivingCardCPEntiy> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppArchivingCardCPEntiy.class,qvo);
        return list;
    }
}
