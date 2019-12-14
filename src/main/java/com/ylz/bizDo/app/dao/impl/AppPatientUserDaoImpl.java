package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppPatientUserDao;
import com.ylz.bizDo.app.entity.AppHealthCardRecodesVo;
import com.ylz.bizDo.app.entity.AppPatientDAHVo;
import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.jtapp.commonEntity.*;
import com.ylz.bizDo.jtapp.commonVo.AppCommLyQvo;
import com.ylz.bizDo.jtapp.commonVo.AppCommQvo;
import com.ylz.bizDo.jtapp.commonVo.AppPeopleTypeQvo;
import com.ylz.bizDo.jtapp.drEntity.AppDrTzEntity;
import com.ylz.bizDo.jtapp.drEntity.AppLyPeopleEntity;
import com.ylz.bizDo.jtapp.drEntity.JmInfo;
import com.ylz.bizDo.jtapp.drEntity.PatientInfo;
import com.ylz.bizDo.jtapp.drVo.AppDrCount;
import com.ylz.bizDo.jtapp.drVo.AppDrTzQvo;
import com.ylz.bizDo.jtapp.drVo.AppLyQvo;
import com.ylz.bizDo.jtapp.patientEntity.*;
import com.ylz.bizDo.jtapp.ysChangeEntity.YsChangeCountEntity;
import com.ylz.bizDo.jtapp.ysChangeEntity.YsChangePatientEntity;
import com.ylz.bizDo.jtapp.ysChangeEntity.YsChangePeopleEntity;
import com.ylz.bizDo.jtapp.ysChangeVo.YsChangeCountQvo;
import com.ylz.bizDo.jtapp.ysChangeVo.YsChangeSureQvo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packaccede.util.CardUtil;
import com.ylz.packcommon.common.CommConditionVo;
import com.ylz.packcommon.common.comEnum.*;
import com.ylz.packcommon.common.exception.DaoException;
import com.ylz.packcommon.common.util.*;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2017/6/14.
 */
@Service("appPatientUserDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppPatientUserDaoImpl implements AppPatientUserDao {
    @Autowired
    private SysDao sysDao;

    @Override
    public boolean findPatientByIdNo(String idNo,String type) throws Exception{
        boolean result = false;
        AppPatientUser user  = (AppPatientUser) sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(AppPatientUser.class)
                .add(Restrictions.eq("patientIdno", idNo))
                .add(Restrictions.eq("patientUpHpis",type))
//                .setCacheable(true)
                .uniqueResult();
        if(user != null){
            result = true;
        }
        return result;
    }

    @Override
    public AppPatientUser findPatientIdNo(String idNo) throws Exception{
        return (AppPatientUser) sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(AppPatientUser.class)
                .add(Restrictions.eq("patientIdno", idNo))
                .add(Restrictions.ne("patientUpHpis","998"))
//                .setCacheable(true)
                .uniqueResult();
    }

    @Override
    public AppPatientUser findPatientIdNo(String idNo, String type) throws Exception{
        return (AppPatientUser) sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(AppPatientUser.class)
                .add(Restrictions.eq("patientIdno", idNo))
                .add(Restrictions.eq("patientUpHpis",type))
                .uniqueResult();
    }

    @Override
    public boolean findPatientByCard(String card,String type) throws Exception{
        boolean result = false;
        AppPatientUser user  = (AppPatientUser) sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(AppPatientUser.class)
                .add(Restrictions.eq("patientCard", card))
                .add(Restrictions.eq("patientUpHpis",type))
//                .setCacheable(true)
                .uniqueResult();
        if(user != null){
            result = true;
        }
        return result;
    }

    @Override
    public boolean findPatientByCardNotUser(String card, String patientId) throws Exception{
        boolean result = false;
        AppPatientUser user  = (AppPatientUser) sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(AppPatientUser.class)
                .add(Restrictions.eq("patientCard", card))
                .add(Restrictions.ne("id", patientId))
//                .setCacheable(true)
                .uniqueResult();
        if(user != null){
            result = true;
        }
        return result;
    }

    @Override
    public boolean findPatientByTel(String tel,String type) throws Exception{
        boolean result = false;
        List<AppPatientUser> user  =  sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(AppPatientUser.class)
                .add(Restrictions.eq("patientTel", tel))
                .add(Restrictions.eq("patientUpHpis",type))
//                .setCacheable(true)
                .list();
        if(user != null && user.size() >0){
            result = true;
        }
        return result;
    }

    @Override
    public AppPatientUser findByTelPhone(String tel) throws Exception{
        return  (AppPatientUser) sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(AppPatientUser.class)
                .add(Restrictions.eq("patientTel", tel))
                .add(Restrictions.eq("patientUpHpis",UserUpHpisType.JIHUO.getValue()))
//                .setCacheable(true)
                .uniqueResult();
    }

    @Override
    public AppPatientUser findByUserId(String drPatientId) throws Exception{
        return  (AppPatientUser) sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(AppPatientUser.class)
                .add(Restrictions.eq("id", drPatientId))
//                .setCacheable(true)
                .uniqueResult();
    }

    @Override
    public AppPatientUserEntity findByUser(String userAccount, String md5UserPassword, String selectType) throws Exception{
        HashMap map = new HashMap();
        String sql="SELECT\n" +
                "\tID id,\n" +
                "\tID easeId,\n" +
                "\tPATIENT_NAME patientName,\n" +
                "\tPATIENT_GENDER patientGender,\n" +
                "\tPATIENT_BIRTHDAY patientBirthday,\n" +
                "\tPATIENT_IDNO patientIdno,\n" +
                "\tPATIENT_CARD patientCard,\n" +
                "\tPATIENT_ID_CARD_TEMP paitentIdCardTemp,\n" +
                "\tPATIENT_AGE patientAge,\n" +
                "\tPATIENT_TEL patientTel,\n" +
                "\tPATIENT_ADDRESS patientAddress,\n" +
                "\tPATIENT_IMAGEURL patientImageurl,\n" +
                "\tPATIENT_IMAGE_NAME patientImageName,\n" +
                "\tPATIENT_PROVINCE patientProvince,\n" +
                "\tPATIENT_CITY patientCity,\n" +
                "\tPATIENT_AREA patientArea,\n" +
                "\tPATIENT_STREET patientStreet,\n" +
                "\tPATIENT_NEIGHBORHOOD_COMMITTEE patientNeighborhoodCommittee,\n" +
                "\tPATIENT_PROVINCE patientProvinceName,\n" +
                "\tPATIENT_CITY patientCityName,\n" +
                "\tPATIENT_AREA patientAreaName,\n" +
                "\tPATIENT_STREET patientStreetName,\n" +
                "\tPATIENT_NEIGHBORHOOD_COMMITTEE patientNeighborhoodCommitteeName,\n" +
                "\t'' patientCommunity,\n" +
                "\tPATIENT_STATE patientState,\n" +
                "\tPATIENT_HEALTHY patientHealthy,\n" +
                "\t'1' patientFirstState,\n" +
                "\tPATIENT_CITY cityCode,\n" +
                "\tPATIENT_WEIGHT patientWeight,\n" +
                "\tPATIENT_HEIGHT patientHeight,\n" +
                "\tPATIENT_BMI patientBmi," +
                "\tPATIENT_PWD_STATE pwdState,\n" +
                "\tPATIENT_EHC_ID patientEHCId,\n" +
                "\tPATIENT_EHC_NO patientEHCNo,\n" +
                "\t'' openEHCState\n" +
                "FROM\n" +
                "\tAPP_PATIENT_USER\n" +
                "WHERE\n" +
                "\t1 = 1";
        if(selectType.equals(CommonLoginType.ZHANGHAO.getValue())){
            map.put("userAccount",userAccount);
//            map.put("md5UserPassword",md5UserPassword);
//            AND PATIENT_PWD = :md5UserPassword
            if(userAccount.length() == 11){
                sql+=" AND PATIENT_TEL = :userAccount ";
            }else{
                sql+=" AND PATIENT_IDNO = :userAccount ";
            }
        }else if(selectType.equals(CommonLoginType.JMZJ.getValue())){
            map.put("userAccount",userAccount);
            sql+=" AND ID = :userAccount ";
        }else if(selectType.equals(CommonLoginType.ZNKT.getValue())){
            map.put("userAccount",userAccount);
            sql+=" AND ID = :userAccount ";
        }else{
            map.put("userAccount",userAccount);
            sql+=" AND PATIENT_TEL = :userAccount ";
        }
        map.put("PATIENT_UP_HPIS","0");
        sql += " AND PATIENT_UP_HPIS = :PATIENT_UP_HPIS ";
        List<AppPatientUserEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql, map, AppPatientUserEntity.class);
        if(ls != null && ls.size() >0){
            return  ls.get(0);
        }
        return null;

    }

    @Override
    public AppPatientUserWechatEntity findByWechatUser(String userAccount, String selectType) throws Exception{
        HashMap map = new HashMap();
        String sql="SELECT\n" +
                "\tID id,\n" +
                "\tID easeId,\n" +
                "\tPATIENT_NAME patientName,\n" +
                "\tPATIENT_GENDER patientGender,\n" +
                "\tPATIENT_BIRTHDAY patientBirthday,\n" +
                "\tPATIENT_IDNO patientIdno,\n" +
                "\tPATIENT_CARD patientCard,\n" +
                "\tPATIENT_ID_CARD_TEMP paitentIdCardTemp,\n" +
                "\tPATIENT_AGE patientAge,\n" +
                "\tPATIENT_TEL patientTel,\n" +
                "\tPATIENT_ADDRESS patientAddress,\n" +
                "\tPATIENT_IMAGEURL patientImageurl,\n" +
                "\tPATIENT_IMAGE_NAME patientImageName,\n" +
                "\tPATIENT_PROVINCE patientProvince,\n" +
                "\tPATIENT_CITY patientCity,\n" +
                "\tPATIENT_AREA patientArea,\n" +
                "\tPATIENT_STREET patientStreet,\n" +
                "\tPATIENT_NEIGHBORHOOD_COMMITTEE patientNeighborhoodCommittee,\n" +
                "\tPATIENT_PROVINCE patientProvinceName,\n" +
                "\tPATIENT_CITY patientCityName,\n" +
                "\tPATIENT_AREA patientAreaName,\n" +
                "\tPATIENT_STREET patientStreetName,\n" +
                "\tPATIENT_NEIGHBORHOOD_COMMITTEE patientNeighborhoodCommitteeName,\n" +
                "\t'' patientCommunity,\n" +
                "\tPATIENT_STATE patientState,\n" +
                "\tPATIENT_HEALTHY patientHealthy,\n" +
                "\t'1' patientFirstState,\n" +
                "\tPATIENT_CITY cityCode,\n" +
                "\tPATIENT_WEIGHT patientWeight,\n" +
                "\tPATIENT_HEIGHT patientHeight,\n" +
                "\tPATIENT_BMI patientBmi," +
                "\tPATIENT_PWD_STATE pwdState\n" +
                "FROM\n" +
                "\tAPP_PATIENT_USER\n" +
                "WHERE\n" +
                "\t1 = 1";
        map.put("userAccount",userAccount);
        if(selectType.equals(CommonLoginType.ZHANGHAO.getValue())){
            if(userAccount.length() == 11){
                sql+=" AND PATIENT_TEL = :userAccount ";
            }else{
                sql+=" AND PATIENT_IDNO = :userAccount ";
            }
        }else if(selectType.equals(CommonLoginType.WEIXINOPEN.getValue())){
            sql+=" AND PATIENT_OPEN_ID = :userAccount ";
        }else{
            sql+=" AND PATIENT_TEL = :userAccount ";
        }
        map.put("PATIENT_UP_HPIS","0");
        sql += " AND PATIENT_UP_HPIS = :PATIENT_UP_HPIS ";
        List<AppPatientUserWechatEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql, map, AppPatientUserWechatEntity.class);
        if(ls != null && ls.size() >0){
            return  ls.get(0);
        }
        return null;

    }

    @Override
    public AppPatientUserEntity findUserId(String id) throws Exception{
        HashMap map = new HashMap();
        String sql="SELECT" +
                " ID id," +
                "\tID easeId,\n" +
                " PATIENT_NAME patientName," +
                " PATIENT_GENDER patientGender," +
                " PATIENT_BIRTHDAY patientBirthday," +
                " PATIENT_IDNO patientIdno," +
                " PATIENT_CARD patientCard," +
                "\tPATIENT_ID_CARD_TEMP paitentIdCardTemp,\n" +
                " PATIENT_AGE patientAge," +
                " PATIENT_TEL patientTel," +
                " PATIENT_ADDRESS patientAddress," +
                " PATIENT_IMAGEURL patientImageurl," +
                " PATIENT_IMAGE_NAME patientImageName," +
                " PATIENT_PROVINCE patientProvince," +
                " PATIENT_CITY patientCity," +
                " PATIENT_AREA patientArea," +
                " PATIENT_STREET patientStreet," +
                "\tPATIENT_NEIGHBORHOOD_COMMITTEE patientNeighborhoodCommittee,\n" +
                "\tPATIENT_PROVINCE patientProvinceName,\n" +
                "\tPATIENT_CITY patientCityName,\n" +
                "\tPATIENT_AREA patientAreaName,\n" +
                "\tPATIENT_STREET patientStreetName,\n" +
                "\tPATIENT_NEIGHBORHOOD_COMMITTEE patientNeighborhoodCommitteeName,\n" +
                "\t'' patientCommunity,\n" +
                " PATIENT_STATE patientState," +
                " PATIENT_HEALTHY patientHealthy," +
                " '1' patientFirstState," +
                "\tPATIENT_CITY cityCode,\n" +
                "\tPATIENT_WEIGHT patientWeight,\n" +
                "\tPATIENT_HEIGHT patientHeight,\n" +
                "\tPATIENT_BMI patientBmi,\n" +
                "\tPATIENT_EHC_ID patientEHCId,\n" +
                "\tPATIENT_EHC_NO patientEHCNo,\n" +
                "\t'' openEHCState\n" +
                " FROM APP_PATIENT_USER WHERE 1=1 ";
            map.put("id",id);
            sql+=" AND ID = :id ";
        List<AppPatientUserEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql, map, AppPatientUserEntity.class);
        if(ls != null && ls.size() >0){
            return  ls.get(0);
        }
        return null;
    }

    @Override
    public AppPatientUserEntity findByUserIdNo(String userIdNo) throws Exception{
        HashMap map = new HashMap();
        String sql="SELECT\n" +
                "\tID id,\n" +
                "\tID easeId,\n" +
                "\tPATIENT_NAME patientName,\n" +
                "\tPATIENT_GENDER patientGender,\n" +
                "\tPATIENT_BIRTHDAY patientBirthday,\n" +
                "\tPATIENT_IDNO patientIdno,\n" +
                "\tPATIENT_CARD patientCard,\n" +
                "\tPATIENT_ID_CARD_TEMP paitentIdCardTemp,\n" +
                "\tPATIENT_AGE patientAge,\n" +
                "\tPATIENT_TEL patientTel,\n" +
                "\tPATIENT_ADDRESS patientAddress,\n" +
                "\tPATIENT_IMAGEURL patientImageurl,\n" +
                "\tPATIENT_IMAGE_NAME patientImageName,\n" +
                "\tPATIENT_PROVINCE patientProvince,\n" +
                "\tPATIENT_CITY patientCity,\n" +
                "\tPATIENT_AREA patientArea,\n" +
                "\tPATIENT_STREET patientStreet,\n" +
                "\tPATIENT_NEIGHBORHOOD_COMMITTEE patientNeighborhoodCommittee,\n" +
                "\tPATIENT_PROVINCE patientProvinceName,\n" +
                "\tPATIENT_CITY patientCityName,\n" +
                "\tPATIENT_AREA patientAreaName,\n" +
                "\tPATIENT_STREET patientStreetName,\n" +
                "\tPATIENT_NEIGHBORHOOD_COMMITTEE patientNeighborhoodCommitteeName,\n" +
                "\t'' patientCommunity,\n" +
                "\tPATIENT_STATE patientState,\n" +
                "\tPATIENT_HEALTHY patientHealthy,\n" +
                "\t'1' patientFirstState\n," +
                "\tPATIENT_CITY cityCode,\n" +
                "\tPATIENT_WEIGHT patientWeight,\n" +
                "\tPATIENT_HEIGHT patientHeight,\n" +
                "\tPATIENT_BMI patientBmi\n" +
                " FROM\n" +
                "\tAPP_PATIENT_USER\n" +
                "WHERE\n" +
                "\t1 = 1";
        map.put("userAccount",userIdNo);
        sql+=" AND PATIENT_IDNO = :userAccount";
        List<AppPatientUserEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql, map, AppPatientUserEntity.class);
        if(ls != null && ls.size() >0){
            return  ls.get(0);
        }
        return null;
    }

    @Override
    public PatientInfo findUserInfo(String patientId,String state,String signId) throws Exception{
        HashMap map = new HashMap();
        String sql = "SELECT\n" +
                "\tc.ID id,\n" +
                "\ta.ID signFormId,\n" +
                "\tc.PATIENT_NAME name,\n" +
                "\tc.PATIENT_GENDER sex,\n" +
                //"\tc.PATIENT_AGE age,\n" +
                "\tc.PATIENT_IMAGEURL imgurl,\n" +
                "\tc.PATIENT_IDNO idno,\n" +
                "'' age," +
                "\tc.PATIENT_TEL phone,\n" +
                "\tc.PATIENT_CARD card,\n" +
                "\tc.PATIENT_PROVINCE province,\n" +
                "\tc.PATIENT_CITY city,\n" +
                "\tc.PATIENT_AREA area,\n" +
                "\tc.PATIENT_STREET street,\n" +
                "\tc.PATIENT_NEIGHBORHOOD_COMMITTEE committee,\n" +
                "\tc.PATIENT_ADDRESS address,\n" +
                "\ta.SIGN_STATE qyzt,\n" +
                "\ta.SIGN_TYPE qylx,\n" +
                "\ta.SIGN_TYPE qyType,\n" +
                "\ta.SIGN_SERVICE_A ssgg,\n" +
                "\ta.SIGN_SERVICE_B jjyl,\n" +
                "\ta.SIGN_SERVICE_B_COLOR jjylcolor,\n" +
                "\ta.SIGN_FROM_DATE fromDate,\n" +
                "\ta.SIGN_TO_DATE endDate,\n" +
                "\t(SELECT GROUP_CONCAT(LABEL_TITLE) from APP_LABEL_GROUP g where g.LABEL_TYPE=3 and g.LABEL_SIGN_ID=a.ID) fwrq,\n" +
                "\ta.SIGN_HEALTH_GROUP jkqk,\n" +
                "\t(SELECT GROUP_CONCAT(LABEL_TITLE) from APP_LABEL_DISEASE g where g.LABEL_TYPE=2 and g.LABEL_SIGN_ID=a.ID) labtitle,\n" +
                "\t(SELECT GROUP_CONCAT(LABEL_COLOR) from APP_LABEL_DISEASE g where g.LABEL_TYPE=2 and g.LABEL_SIGN_ID=a.ID) labcolor,\n" +
                "\t(SELECT k.ORDER_DATE from APP_ORDER k where k.ORDER_SIGN_ID=a.ID) payDate,\n" +
                "\ta.SIGN_PAY_STATE signPayState,\n" +
                "\ta.SIGN_TEAM_ID signTeamId," +
                "\tc.PATIENT_CITY cityCode,\n" +
                "\t(SELECT GROUP_CONCAT(LABEL_TITLE) from APP_LABEL_ECONOMICS g where g.LABEL_TYPE= 4 and g.LABEL_SIGN_ID=a.ID) signJjType,\n" +
                "\ta.SIGN_PACKAGEID serveList," +
                "a.SIGN_HOSP_ID signHoapId," +
                "a.SIGN_DR_ID signDrId," +
                "'' referralState," +
                "'' isFollow," +
                "'' isGuide," +
                "'' isEducate," +
                "'' imgUrls " +
                "FROM\n" +
                "\tAPP_SIGN_FORM a\n" +
//                "LEFT JOIN APP_LABEL_GROUP b ON a.ID = b.LABEL_SIGN_ID\n" +
                "LEFT JOIN APP_PATIENT_USER c ON a.SIGN_PATIENT_ID = c.ID\n" +
                "where 1=1";
        if(StringUtils.isNotBlank(patientId)){
            map.put("patientId", patientId);
            sql += " AND a.SIGN_PATIENT_ID = :patientId";
        }
        if(StringUtils.isNotBlank(state)){
            if(state.equals("1")){
                String[] signStates = new String[]{SignFormType.DQY.getValue(),SignFormType.YUZQ.getValue()};
                map.put("SIGN_STATE", signStates);
                sql += " AND a.SIGN_STATE IN (:SIGN_STATE) ";
            }else {
                /*if(StringUtils.isNotBlank(teamId)){
                    map.put("teamId",teamId);
                    sql += " AND a.ID =(SELECT bb.ID FROM APP_SIGN_FORM bb WHERE bb.SIGN_PATIENT_ID =:patientId AND bb.SIGN_TEAM_ID = :teamId  ORDER BY HS_UPDATE_TIME DESC LIMIT 0,1) ";
                }else{*/
                String[] signStates = new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
                map.put("SIGN_STATE", signStates);

                if(StringUtils.isNotBlank(signId)){
                    map.put("SIGN_ID",signId);
                    sql += " AND (a.SIGN_STATE IN (:SIGN_STATE) OR a.ID =:SIGN_ID )";
                }else{
                    sql += " AND a.SIGN_STATE IN (:SIGN_STATE) ";
                }


            }

        }

        sql=sql+" GROUP BY c.ID";
        List<PatientInfo> ls=sysDao.getServiceDo().findSqlMapRVo(sql,map, PatientInfo.class);
        if(ls!=null && !ls.isEmpty()){
            return ls.get(0);
        }
        return null;
    }

    @Override
    public void addTest() throws Exception {
        String sql = "select * from app_test_gly t";
        List<Map> ls = sysDao.getServiceDo().findSql(sql);
        if(ls != null && ls.size() >0){
            int i =0;
            for(Map<String,Object> s : ls){
                System.out.println(s.get("USER_NAME"));
                AppPatientUser user = new AppPatientUser();
                Map<String,Object> idNos;
                String userIds = s.get("CID").toString().toLowerCase();
                if(userIds.length() == 18){
                    idNos = CardUtil.getCarInfo(userIds);
                }else{
                    idNos = CardUtil.getCarInfo15W(userIds);
                }
                String birthday = idNos.get("birthday").toString();
                user.setPatientName(s.get("USER_NAME").toString());
                user.setPatientIdno(s.get("CID").toString());
                user.setPatientAge(AgeUtil.getAgeYear(ExtendDate.getCalendar(birthday)));
                user.setPatientGender(idNos.get("sex").toString());
                user.setPatientBirthday(ExtendDate.getCalendar(idNos.get("birthday").toString()));
                if(s.get("SBKH") != null){
                    user.setPatientCard(s.get("SBKH").toString());
                }
                if(s.get("TEL") != null){
                    if(s.get("TEL").toString().length()== 11){
                        user.setPatientTel(s.get("TEL").toString());
                    }
                }
                user.setPatientState(CommonEnable.QIYONG.getValue());
                user.setPatientHealthy(CommonEnable.JINYONG.getValue());
                user.setPatientPwd(Md5Util.MD5(userIds.substring(userIds.length()-6,userIds.length())));
                user.setPatientHealthy(CommonEnable.QIYONG.getValue());
                user.setPatientProvince("350000000000");
                user.setPatientCity("350200000000");
                user.setPatientArea("350211000000");
                user.setPatientStreet("350211003000");
                if(s.get("X") != null){
                    user.setPatientAbscissa(s.get("X").toString());
                }
                if(s.get("Y") != null){
                    user.setPatientOrdinate(s.get("Y").toString());
                }
                if(s.get("ADDRESS") != null){
                    user.setPatientAddress(s.get("ADDRESS").toString());
                }
                sysDao.getServiceDo().add(user);
                String residentMange = "99";
                String label = "";
                String labels = null;
                if( s.get("LABEL")  != null){
                    labels = s.get("LABEL").toString();
                }
                if(StringUtils.isNotBlank(labels)){
                    String[] sz = labels.split(";");
                    if(sz != null && sz.length >0){
                        for(String v : sz){
                            if(v.equals("1")){
                                if(StringUtils.isBlank(label)){
                                    label = "201|green";
                                }else{
                                    label += ";201|green";
                                }
                                residentMange = "5";
                            }else if(v.equals("3")){
                                if(StringUtils.isBlank(label)){
                                    label = "201|yellow";
                                }else{
                                    label += ";201|yellow";
                                }
                                residentMange = "5";
                            }else if(v.equals("4")){
                                if(StringUtils.isBlank(label)){
                                    label = "201|red";
                                }else{
                                    label += ";201|red";
                                }
                                residentMange = "5";
                            }else if(v.equals("5")){
                                if(StringUtils.isBlank(label)){
                                    label = "202|green";
                                }else{
                                    label += ";202|green";
                                }
                                residentMange = "6";
                            }else if(v.equals("2")){
                                if(StringUtils.isBlank(label)){
                                    label = "202|yellow";
                                }else{
                                    label += ";202|yellow";
                                }
                                residentMange = "6";
                            }else if(v.equals("6")){
                                if(StringUtils.isBlank(label)){
                                    label = "202|red";
                                }else{
                                    label += ";202|red";
                                }
                                residentMange = "6";
                            }
                        }
                    }
                }
//                sysDao.getAppSignformDao().drAgreeSignForm(user.getId(),s.get("TEAMID").toString(),residentMange,"199",label,s.get("DRID").toString(), qvo.getPatientAddress(), qvo.getPatientProvince(), qvo.getPatientCity(), qvo.getPatientArea(), qvo.getPatientStreet(), qvo.getPatientNeighborhoodCommittee());
            }
        }
    }

    @Override
    public List<AppConDrEntiry> findDrById(String id) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("id",id);
        String[] signStates = new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
        map.put("SIGN_STATE", signStates);
        String sql = "SELECT\n" +
                "\tb.MEM_DR_ID drId,\n" +
                "\tb.MEM_DR_NAME drName," +
                "b.MEM_WORK_TYPE workType," +
                "'' id,\n" +
                "'' drImageurl," +
                "'' drGender " +
                "FROM\n" +
                "\tAPP_SIGN_FORM a JOIN APP_TEAM_MEMBER b ON a.SIGN_TEAM_ID = b.MEM_TEAMID\n" +
                "WHERE\n" +
                "\t\n" +
                "a.SIGN_PATIENT_ID =:id AND a.SIGN_STATE IN (:SIGN_STATE) ";
        List<AppConDrEntiry> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppConDrEntiry.class);
        return ls;
    }


    @Override
    public void addTestDevice() throws Exception{
        //血压
//        String sql = "SELECT t.USER_NAME,t.CID,t.BP,c.ID FROM APP_TEST_GLY t INNER JOIN APP_PATIENT_USER c ON t.CID = c.PATIENT_IDNO  WHERE t.BP IS NOT NULL ORDER BY BP";
//        List<Map> ls = sysDao.getServiceDo().findSql(sql);
//        if(ls != null && ls.size() >0) {
//            for (Map<String, Object> s : ls) {
//                AppBloodpressure vo = this.sysDao.getAppBloodpressureDao().findByDevId(s.get("BP").toString());
//                if(vo != null){
//                    vo.setBpDevTimeTwo(Calendar.getInstance());
//                    vo.setBpUserTwo(s.get("ID").toString());
//                    this.sysDao.getServiceDo().modify(vo);
//                }else{
//                    vo = new AppBloodpressure();
//                    vo.setBpUserOne(s.get("ID").toString());
//                    vo.setBpDevTimeOne(Calendar.getInstance());
//                    vo.setBpCreateTime(Calendar.getInstance());
//                    vo.setBpDevId(s.get("BP").toString());
//                    vo.setBpType("1");
//                    this.sysDao.getServiceDo().add(vo);
//                }
//            }
//        }

        //血样
        String sql = "SELECT t.USER_NAME,t.CID,t.BG,c.ID FROM APP_TEST_GLY t INNER JOIN APP_PATIENT_USER c ON t.CID = c.PATIENT_IDNO  WHERE t.BG IS NOT NULL ORDER BY BG";
        List<Map> ls = sysDao.getServiceDo().findSql(sql);
        if(ls != null && ls.size() >0) {
            for (Map<String, Object> s : ls) {
                AppBloodglu vo = this.sysDao.getAppBloodgluDao().findByDevId(s.get("BG").toString());
                if(vo != null){

                }else{
                    vo = new AppBloodglu();
                   vo.setBgCreateTime(Calendar.getInstance());
                   vo.setBgDevId(s.get("BG").toString());
                   vo.setBgDevTime(Calendar.getInstance());
                   vo.setBgPaientId(s.get("ID").toString());
                   vo.setBgType("2");
                    this.sysDao.getServiceDo().add(vo);
                }
            }
        }
    }

    @Override
    public void addTestData() throws Exception{
        String sql = "SELECT\n" +
                "\tt.DEVID,\n" +
                "\tt.BLOODGLU,\n" +
                "\tt.BGSTATE,\n" +
                "\tt.TESTTIME,\n" +
                "\tt.TEMPTUR,\n" +
                "\tg.ID patientId\n" +
                "FROM\n" +
                "\thd_user_bloodglu t\n" +
                "INNER JOIN APP_TEST_GLY c ON t.USER_ID = c.USER_ID\n" +
                "INNER JOIN APP_PATIENT_USER g ON c.CID = g.PATIENT_IDNO\n" +
                "ORDER BY\n" +
                "\tDEVID DESC";
        List<Map> ls = sysDao.getServiceDo().findSql(sql);
        if(ls != null && ls.size() >0) {
            for (Map<String, Object> s : ls) {
                AppUserBloodglu v = new AppUserBloodglu();
                v.setUgBloodglu((double)s.get("BLOODGLU"));
            }
        }

    }

    @Override
    public List<AppPatientUser> findAll() throws Exception{
        return  sysDao.getServiceDo().getSessionFactory().getCurrentSession()
                .createCriteria(AppPatientUser.class)
                .list();
    }

    /**
     * 查询医生咨询列表
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<AppConDrEntiry> findDrByIdList(AppDrCount qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("id",qvo.getPatientId());
        String[] signStates = new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
        map.put("SIGN_STATE", signStates);
        String sql = "SELECT\n" +
                "\tb.MEM_DR_ID drId,\n" +
                "\tb.MEM_DR_NAME drName," +
                "b.MEM_WORK_TYPE workType," +
                "'' id,\n" +
                "'' drImageurl," +
                "'' drGender " +
                "FROM\n" +
                "\tAPP_SIGN_FORM a JOIN APP_TEAM_MEMBER b ON a.SIGN_TEAM_ID = b.MEM_TEAMID\n" +
                "WHERE\n" +
                "\t\n" +
                "a.SIGN_PATIENT_ID =:id AND a.SIGN_STATE IN (:SIGN_STATE) ";
        if(StringUtils.isNotBlank(qvo.getDrName())){
            map.put("drName","%"+qvo.getDrName()+"%");
            sql += " AND b.MEM_DR_NAME LIKE :drName";
        }
        List<AppConDrEntiry> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppConDrEntiry.class);
        return ls;
    }



    @Override
    public AppPatientUserEntity findByUserIdNoCardTel(String userIdNo, String userCard, String userTel) throws Exception{
        HashMap map = new HashMap();
        String sql="SELECT\n" +
                "\tID id,\n" +
                "\tID easeId,\n" +
                "\tPATIENT_NAME patientName,\n" +
                "\tPATIENT_GENDER patientGender,\n" +
                "\tPATIENT_BIRTHDAY patientBirthday,\n" +
                "\tPATIENT_IDNO patientIdno,\n" +
                "\tPATIENT_CARD patientCard,\n" +
                "\tPATIENT_ID_CARD_TEMP paitentIdCardTemp,\n" +
                "\tPATIENT_AGE patientAge,\n" +
                "\tPATIENT_TEL patientTel,\n" +
                "\tPATIENT_ADDRESS patientAddress,\n" +
                "\tPATIENT_IMAGEURL patientImageurl,\n" +
                "\tPATIENT_IMAGE_NAME patientImageName,\n" +
                "\tPATIENT_PROVINCE patientProvince,\n" +
                "\tPATIENT_CITY patientCity,\n" +
                "\tPATIENT_AREA patientArea,\n" +
                "\tPATIENT_STREET patientStreet,\n" +
                "\tPATIENT_NEIGHBORHOOD_COMMITTEE patientNeighborhoodCommittee,\n" +
                "\tPATIENT_PROVINCE patientProvinceName,\n" +
                "\tPATIENT_CITY patientCityName,\n" +
                "\tPATIENT_AREA patientAreaName,\n" +
                "\tPATIENT_STREET patientStreetName,\n" +
                "\tPATIENT_NEIGHBORHOOD_COMMITTEE patientNeighborhoodCommitteeName,\n" +
                "\t'' patientCommunity,\n" +
                "\tPATIENT_STATE patientState,\n" +
                "\tPATIENT_HEALTHY patientHealthy,\n" +
                "\t'1' patientFirstState\n," +
                "\tPATIENT_CITY cityCode,\n" +
                "\tPATIENT_WEIGHT patientWeight,\n" +
                "\tPATIENT_HEIGHT patientHeight,\n" +
                "\tPATIENT_BMI patientBmi\n" +
                " FROM\n" +
                "\tAPP_PATIENT_USER\n" +
                "WHERE\n" +
                "\t1 = 1";
        if(StringUtils.isNotBlank(userIdNo)) {
            map.put("userIdNo", userIdNo);
            sql+=" AND PATIENT_IDNO = :userIdNo";
        }
        if(StringUtils.isNotBlank(userCard)) {
            map.put("userCard", userCard);
            sql+=" AND PATIENT_CARD = :userCard";
        }
        if(StringUtils.isNotBlank(userTel)) {
            map.put("userTel", userTel);
            sql+=" AND PATIENT_TEL = :userTel";
        }

        List<AppPatientUserEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql, map, AppPatientUserEntity.class);
        if(ls != null && ls.size() >0){
            return  ls.get(0);
        }
        return null;
    }


    @Override
    public AppPatientRegisterEntity findByUserIdNoCardTelRegister(String userIdNo, String userCard, String userTel)throws Exception {
        HashMap map = new HashMap();
        String sql="SELECT\n" +
                "\tID id,\n" +
                "\tPATIENT_NAME patientName,\n" +
                "\tPATIENT_GENDER patientGender,\n" +
                "\tPATIENT_BIRTHDAY patientBirthday,\n" +
                "\tPATIENT_IDNO patientIdno,\n" +
                "\tPATIENT_CARD patientCard,\n" +
                "\tPATIENT_AGE patientAge,\n" +
                "\tPATIENT_TEL patientTel,\n" +
                "\tPATIENT_ADDRESS patientAddress,\n" +
                "\tPATIENT_IMAGEURL patientImageurl,\n" +
                "\tPATIENT_IMAGE_NAME patientImageName,\n" +
                "\tPATIENT_PROVINCE patientProvince,\n" +
                "\tPATIENT_CITY patientCity,\n" +
                "\tPATIENT_AREA patientArea,\n" +
                "\tPATIENT_STREET patientStreet,\n" +
                "\tPATIENT_NEIGHBORHOOD_COMMITTEE patientNeighborhoodCommittee,\n" +
                "\tPATIENT_PROVINCE patientProvinceName,\n" +
                "\tPATIENT_CITY patientCityName,\n" +
                "\tPATIENT_AREA patientAreaName,\n" +
                "\tPATIENT_STREET patientStreetName,\n" +
                "\tPATIENT_NEIGHBORHOOD_COMMITTEE patientNeighborhoodCommitteeName,\n" +
                "\t'' patientCommunity,\n" +
                "\tPATIENT_CITY cityCode\n" +
                " FROM\n" +
                "\tAPP_PATIENT_USER\n" +
                "WHERE\n" +
                "\t1 = 1";
        if(StringUtils.isNotBlank(userIdNo)) {
            map.put("userIdNo", userIdNo);
            sql+=" AND PATIENT_IDNO = :userIdNo";
        }
        if(StringUtils.isNotBlank(userCard)) {
            map.put("userCard", userCard);
            sql+=" AND PATIENT_CARD = :userCard";
        }
        if(StringUtils.isNotBlank(userTel)) {
            map.put("userTel", userTel);
            sql+=" AND PATIENT_TEL = :userTel";
        }

        List<AppPatientRegisterEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql, map, AppPatientRegisterEntity.class);
        if(ls != null && ls.size() >0){
            return  ls.get(0);
        }
        return null;
    }


    /**
     * 根据服务人群、健康情况、疾病类型查询
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<AppPeopleEntity> findByType(AppPeopleTypeQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("type",qvo.getFindType());
        String[] signStates = new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
        map.put("state",signStates);
        map.put("teamId",qvo.getTeamId());
        String sqlLabel = null;
        if(LabelManageType.JKQK.getValue().equals(qvo.getFindType())){
            sqlLabel = "\tSELECT\n" +
                    "\tcount(1)\n" +
                    "\tFROM\n" +
                    "\t\tAPP_SIGN_FORM a\n" +
                    "\tWHERE\n" +
                    "\ta.SIGN_STATE IN (:state)\n" +
                    "\tAND a.SIGN_HEALTH_GROUP = c.LABEL_VALUE\n" +
                    "\tAND a.SIGN_TEAM_ID = :teamId\n" +
                    "\tGROUP BY\n" +
                    "\ta.SIGN_HEALTH_GROUP";
        }else if(LabelManageType.FWRQ.getValue().equals(qvo.getFindType())){
            sqlLabel = "\tSELECT\n" +
                    "\tcount(1)\n" +
                    "\tFROM\n" +
                    "\tAPP_SIGN_FORM a\n" +
                    "\tINNER JOIN APP_LABEL_GROUP b ON a.ID = b.LABEL_SIGN_ID\n" +
                    "\tWHERE\n" +
                    "\ta.SIGN_STATE IN (:state)\n" +
                    "\tAND b.LABEL_VALUE = c.LABEL_VALUE\n" +
                    "\tAND a.SIGN_TEAM_ID = :teamId\n" +
                    "\tAND b.LABEL_TYPE = :type\n" +
                    "\tGROUP BY\n" +
                    "\tb.LABEL_VALUE";
        }else if(LabelManageType.JBLX.getValue().equals(qvo.getFindType())){
            sqlLabel = "\tSELECT\n" +
                    "\tcount(1)\n" +
                    "\tFROM\n" +
                    "\tAPP_SIGN_FORM a\n" +
                    "\tINNER JOIN APP_LABEL_DISEASE b ON a.ID = b.LABEL_SIGN_ID\n" +
                    "\tWHERE\n" +
                    "\ta.SIGN_STATE IN (:state)\n" +
                    "\tAND b.LABEL_VALUE = c.LABEL_VALUE\n" +
                    "\tAND a.SIGN_TEAM_ID = :teamId\n"+
                   "\tGROUP BY\n" +
                    "\tb.LABEL_VALUE";
        }else if(LabelManageType.JJLX.getValue().equals(qvo.getFindType())){
            sqlLabel = "\tSELECT\n" +
                    "\tcount(1)\n" +
                    "\tFROM\n" +
                    "\tAPP_SIGN_FORM a\n" +
                    "\tINNER JOIN APP_LABEL_ECONOMICS b ON a.ID = b.LABEL_SIGN_ID\n" +
                    "\tWHERE\n" +
                    "\ta.SIGN_STATE IN (:state)\n" +
                    "\tAND b.LABEL_VALUE = c.LABEL_VALUE\n" +
                    "\tAND a.SIGN_TEAM_ID = :teamId\n" +
                    "\tGROUP BY\n" +
                    "\tb.LABEL_VALUE";
        }
        String sql = "SELECT ("+sqlLabel+") count," +
                "c.LABEL_TITLE typeName,c.LABEL_VALUE typeValue  FROM APP_LABEL_MANAGE c WHERE c.LABEL_TYPE = :type" +
                " ORDER BY c.LABEL_VALUE";
        List<AppPeopleEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppPeopleEntity.class);
        return ls;
    }


    @Override
    public String addPerfectData(AppPatientPerfectDataEntity vo) throws Exception {
        AppPatientUser user = (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,vo.getId());
        Map<String,Object> idNos;
        if(vo.getPatientIdNo().length() == 18){
            idNos = CardUtil.getCarInfo(vo.getPatientIdNo());
        }else{
            idNos = CardUtil.getCarInfo15W(vo.getPatientIdNo());
        }
        user.setPatientBirthday(ExtendDate.getCalendar(idNos.get("birthday").toString()));//出生日期
        user.setPatientAge(AgeUtil.getAgeYear(user.getPatientBirthday()));//年龄
        user.setPatientGender(idNos.get("sex").toString());//性别
        user.setPatientIdno(vo.getPatientIdNo().toUpperCase());//身份证
        //
        if(StringUtils.isBlank(vo.getPatientCard())){
            //添加社保卡号
            String idCard = sysDao.getSecurityCardAsyncBean().getSecurityCardNotAsync(user.getPatientIdno(),user.getPatientName(),user.getId(),user.getPatientName(),DrPatientType.PATIENT.getValue());
            user.setPatientCard(idCard);
        }else {
            user.setPatientCard(vo.getPatientCard());
        }
        user.setPatientName(vo.getPatientName());
        user.setPatientBopomofo(PinyinUtil.getPinYinHeadChar(user.getPatientName()));
        user.setPatientHealthy(vo.getPatientHealthy());
        user.setPatientProvince(vo.getPatientProvince());
        user.setPatientCity(vo.getPatientCity());
        user.setPatientNeighborhoodCommittee(vo.getPatientNeighborhoodCommittee());
        user.setPatientArea(vo.getPatientArea());
        user.setPatientStreet(vo.getPatientStreet());
        user.setPatientAbscissa(vo.getPatientAbscissa());
        user.setPatientOrdinate(vo.getPatientOrdinate());
        if(StringUtils.isNotBlank(vo.getPatientImageurl())){
            Map<String,Object> map = sysDao.getIoUtils().getCtyunOosSample(vo.getPatientImageurl(),CommonShortType.HUANGZHE.getValue());
            user.setPatientImageurl(map.get("objectUrl").toString());
            user.setPatientImageName(map.get("objectName").toString());
//            String path = sysDao.getIoUtils().pathUrl(PropertiesUtil.getConfValue("filePicture"),PropertiesUtil.getConfValue("filePictureHz"),vo.getPatientImageName());
//            FileUtils.decoderBase64File(vo.getPatientImageurl(),PropertiesUtil.getConfValue("filePicture")+path);
//            user.setPatientImageurl(path);
//            user.setPatientImageName(vo.getPatientImageName());
        }
        user.setPatientAddress(vo.getPatientAddress());
        user.setPatientAbscissa(vo.getPatientAbscissa());
        user.setPatientOrdinate(vo.getPatientOrdinate());

        if(StringUtils.isNotBlank(vo.getPatientArea())){
            user.setPatientArea(vo.getPatientArea());
            if(vo.getPatientArea().startsWith("3504")){
                sysDao.getSecurityCardAsyncBean().activeJksm(user.getPatientTel(),user.getPatientPwd(),user.getPatientCard(),user.getPatientName(),user.getPatientIdno(),user.getPatientImageurl(),user.getPatientGender(),user.getPatientAge());
            }
        }
        //判断是有未激活用户
        String userId;
        AppPatientUser wjhUser = sysDao.getAppPatientUserDao().findPatientIdNo(user.getPatientIdno(), UserUpHpisType.WEIJIHUO.getValue());
        if(wjhUser != null){
            String wjhUserId = wjhUser.getId();
            CopyUtils.Copy(user,wjhUser);
            wjhUser.setId(wjhUserId);
            wjhUser.setPatientUpHpis(UserUpHpisType.JIHUO.getValue());
            sysDao.getServiceDo().modify(wjhUser);
//            this.sysDao.getServiceDo().removePoFormSession(wjhUser);
            AppDrPatientKey key = this.sysDao.getAppDrPatientKeyDao().findByDoctorOrPatientId(user.getId());
            key.setDrPatientId(wjhUserId);
            sysDao.getServiceDo().modify(key);
            sysDao.getServiceDo().delete(AppPatientUser.class,user.getId());
            userId = wjhUser.getId();
            String result = PropertiesUtil.getConfValue("messageCode");
            if(result.equals("0")){
                if(StringUtils.isBlank(wjhUser.getPatientEaseState())){
                    //添加环新账号
                    sysDao.getSecurityCardAsyncBean().registeredEasemob(userId);
                }
            }
        }else{
            userId = user.getId();
            sysDao.getServiceDo().modify(user);
            this.sysDao.getServiceDo().removePoFormSession(user);
            String result = PropertiesUtil.getConfValue("messageCode");
            if(result.equals("0")){
                //添加环新账号
                sysDao.getSecurityCardAsyncBean().registeredEasemob(user.getId());
            }
        }
        return userId;
    }

    @Override
    public List<AppPeopleHealthEntity> findTypeCount(AppPeopleTypeQvo qvo) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("type",qvo.getFindType());
        String[] signStates = new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
        map.put("state",signStates);
        map.put("teamId",qvo.getTeamId());
        String sqlOne = "SELECT\n" +
                "\tb.LABEL_TYPE labelType,\n" +
                "\tcount(1) count\n" +
                "FROM\n" +
                "\tAPP_SIGN_FORM a\n" +
                "INNER JOIN APP_LABEL_GROUP b ON a.ID = b.LABEL_SIGN_ID\n" +
                "WHERE\n" +
                "\ta.SIGN_STATE IN (:state)\n" +
                "AND a.SIGN_TEAM_ID = :teamId\n" +
                "AND b.LABEL_TYPE='3' "+
                "GROUP BY\n" +
                "\tb.LABEL_TYPE";

        String sqlTwo = "SELECT\n" +
                "\t'1' labelType,\n" +
                "\tcount(1)\n" +
                "FROM\n" +
                "\tAPP_SIGN_FORM a\n" +
                "\tWHERE\n" +
                "\t\t\ta.SIGN_STATE IN (:state)\n" +
                "\t\tAND a.SIGN_TEAM_ID = :teamId\n" +
                "\t\tAND a.SIGN_HEALTH_GROUP IS NOT NULL ";

        String sqlThree =  "SELECT\n" +
                "\tb.LABEL_TYPE labelType,\n" +
                "\tcount(1) count\n" +
                "FROM\n" +
                "\tAPP_SIGN_FORM a\n" +
                "INNER JOIN APP_LABEL_DISEASE b ON a.ID = b.LABEL_SIGN_ID\n" +
                "WHERE\n" +
                "\ta.SIGN_STATE IN (:state)\n" +
                "AND a.SIGN_TEAM_ID = :teamId\n"+
                "GROUP BY\n" +
                "\tb.LABEL_TYPE";
        String sql = sqlOne +" UNION "+sqlTwo+" UNION "+sqlThree;
        List<AppPeopleHealthEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppPeopleHealthEntity.class);
        return ls;
    }

    @Override
    public List<AppPeopleHealthEntity> findTypeCountChange(YsChangeCountQvo qvo) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String[] signStates = new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
        map.put("state",signStates);
        String[] strs = new String[]{ChangeState.SQBG.getValue(),ChangeState.TYBG.getValue()};
        map.put("str",strs);
        String sqlTime = " AND a.SIGN_FROM_DATE <=SYSDATE() AND a.SIGN_TO_DATE>=SYSDATE() ";
        String sqlOne = "SELECT\n" +
                "a.*,\n" +
                "'3' LABEL_TYPE\n"+
                "FROM\n" +
                "\tAPP_SIGN_FORM a\n" +
                "INNER JOIN APP_LABEL_GROUP b ON a.ID = b.LABEL_SIGN_ID\n" +
                "WHERE\n" +
                "\ta.SIGN_STATE IN (:state)\n" +
                "AND b.LABEL_TYPE ='3' ";

        String sqlThree = "SELECT\n" +
                "a.*,\n" +
                "'2' LABEL_TYPE\n"+
                "FROM\n" +
                "\tAPP_SIGN_FORM a\n" +
                "INNER JOIN APP_LABEL_DISEASE b ON a.ID = b.LABEL_SIGN_ID\n" +
                "WHERE\n" +
                "\ta.SIGN_STATE IN (:state)\n" +
                "AND b.LABEL_TYPE ='2' ";

        String sqlTwo = "SELECT\n" +
                "\ta.*,\n" +
                "\t'1' LABEL_TYPE\n" +
                "FROM\n" +
                "\tAPP_SIGN_FORM a\n" +
                "\tWHERE\n" +
                "a.SIGN_STATE IN (:state)\n" +
                "AND a.SIGN_HEALTH_GROUP IS NOT NULL ";
        sqlOne += " AND a.SIGN_CHANGE_STATE != '2'";
        sqlTwo +=sqlTime+ " AND a.SIGN_CHANGE_STATE != '2' ";
        sqlThree+= " AND a.SIGN_CHANGE_STATE != '2' ";
        if(StringUtils.isNotBlank(qvo.getTeamId())){
            map.put("teamId",qvo.getTeamId());
            sqlOne += " AND a.SIGN_TEAM_ID = :teamId   ";
            sqlTwo += " AND a.SIGN_TEAM_ID = :teamId ";
            sqlThree +=" AND a.SIGN_TEAM_ID = :teamId ";
            AppTeam team = (AppTeam)sysDao.getServiceDo().find(AppTeam.class,qvo.getTeamId());
            if(team!=null){
                if(!qvo.getDrId().equals(team.getTeamDrId())){//不是团队长代医生id限制查询
                    map.put("drId",qvo.getDrId());
                    sqlOne+=" AND a.SIGN_DR_ID =:drId ";
                    sqlTwo+=" AND a.SIGN_DR_ID =:drId ";
                    sqlThree+=" AND a.SIGN_DR_ID =:drId ";
                }
            }
        }

        sqlOne += sqlTime+" GROUP BY a.SIGN_PATIENT_ID,b.LABEL_TYPE ";
        sqlThree+=sqlTime+" GROUP BY a.SIGN_PATIENT_ID,b.LABEL_TYPE ";
        String sql = "SELECT '3' labelType,COUNT(1) count FROM ("+sqlOne +") c UNION "+
                "SELECT '2' labelType,COUNT(1) count FROM ("+sqlThree+") c UNION "+
                "SELECT '1' labelType,COUNT(1) count FROM ("+sqlTwo+") c";
        List<AppPeopleHealthEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppPeopleHealthEntity.class);
        return ls;
    }

    @Override
    public List<YsChangeCountEntity> findByTypeChange(YsChangeCountQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        String resultTeamId = "";
        map.put("type",qvo.getType());
        String[] signStates = new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
        map.put("state",signStates);
        map.put("drId",qvo.getDrId());
        String[] strs = new String[]{ChangeState.SQBG.getValue(),ChangeState.TYBG.getValue()};
        map.put("str",strs);
        String sqlTime = " AND a.SIGN_FROM_DATE <=SYSDATE() AND a.SIGN_TO_DATE>SYSDATE() ";
        String sqlLabel = null;
        if(LabelManageType.JKQK.getValue().equals(qvo.getType())){
            sqlLabel = "\tSELECT\n" +
                    "\tcount(1)\n" +
                    "\tFROM\n" +
                    "\tAPP_SIGN_FORM a\n" +
                    "\tWHERE\n" +
                    "\ta.SIGN_STATE IN (:state)\n" +
                    "\tAND a.SIGN_HEALTH_GROUP = c.LABEL_VALUE\n" +
                    "AND a.SIGN_CHANGE_STATE != '2' ";
            if(StringUtils.isNotBlank(qvo.getTeamId())){
                map.put("teamId",qvo.getTeamId());
                resultTeamId = qvo.getTeamId();
                sqlLabel +=" AND a.SIGN_TEAM_ID =:teamId ";
                AppTeam team = (AppTeam)sysDao.getServiceDo().find(AppTeam.class,qvo.getTeamId());
                if(team!=null){
                    if(!qvo.getDrId().equals(team.getTeamDrId())){//非本团队团长带医生限制条件查询
                        sqlLabel +=  " AND a.SIGN_DR_ID =:drId ";
                    }
                }
            }
            sqlLabel += sqlTime;
            sqlLabel += "\tGROUP BY\n" +
                    "\ta.SIGN_HEALTH_GROUP";
        }else if(LabelManageType.FWRQ.getValue().equals(qvo.getType())){
            sqlLabel = "\tSELECT\n" +
                    "\tcount(1)\n" +
                    "\tFROM\n" +
                    "\tAPP_SIGN_FORM a\n" +
                    "\tINNER JOIN APP_LABEL_GROUP b ON a.ID = b.LABEL_SIGN_ID\n" +
                    "\tWHERE\n" +
                    "\ta.SIGN_STATE IN (:state)\n" +
                    "\tAND b.LABEL_VALUE = c.LABEL_VALUE\n" +
                    "AND a.SIGN_CHANGE_STATE != '2' ";
            if(StringUtils.isNotBlank(qvo.getTeamId())){
                map.put("teamId",qvo.getTeamId());
                resultTeamId = qvo.getTeamId();
                sqlLabel +=" AND a.SIGN_TEAM_ID =:teamId ";
                AppTeam team = (AppTeam)sysDao.getServiceDo().find(AppTeam.class,qvo.getTeamId());
                if(team!=null){
                    if(!qvo.getDrId().equals(team.getTeamDrId())){//非本团队团长带医生限制条件查询
                        sqlLabel +=  " AND a.SIGN_DR_ID =:drId ";
                    }
                }
            }
            sqlLabel += " AND b.LABEL_TYPE ='3'";
            sqlLabel += sqlTime;
            sqlLabel += "\tGROUP BY\n" +
                    "\tb.LABEL_VALUE";
        }else if(LabelManageType.JBLX.getValue().equals(qvo.getType())){
            sqlLabel = "\tSELECT\n" +
                    "\tcount(1)\n" +
                    "\tFROM\n" +
                    "\tAPP_SIGN_FORM a\n" +
                    "\tINNER JOIN APP_LABEL_DISEASE b ON a.ID = b.LABEL_SIGN_ID\n" +
                    "\tWHERE\n" +
                    "\ta.SIGN_STATE IN (:state)\n" +
                    "\tAND b.LABEL_VALUE = c.LABEL_VALUE\n" +
                    "AND a.SIGN_CHANGE_STATE != '2' ";
            if(StringUtils.isNotBlank(qvo.getTeamId())){
                map.put("teamId",qvo.getTeamId());
                resultTeamId = qvo.getTeamId();
                sqlLabel +=" AND a.SIGN_TEAM_ID =:teamId ";
                AppTeam team = (AppTeam)sysDao.getServiceDo().find(AppTeam.class,qvo.getTeamId());
                if(team!=null){
                    if(!qvo.getDrId().equals(team.getTeamDrId())){//非本团队团长带医生限制条件查询
                        sqlLabel +=  " AND a.SIGN_DR_ID =:drId ";
                    }
                }
            }
            sqlLabel += sqlTime;
            sqlLabel += " AND b.LABEL_TYPE ='2'";
            sqlLabel += "\tGROUP BY\n" +
                    "\tb.LABEL_VALUE";
        }else if(LabelManageType.JJLX.getValue().equals(qvo.getType())){
            sqlLabel = "\tSELECT\n" +
                    "\tcount(1)\n" +
                    "\tFROM\n" +
                    "\tAPP_SIGN_FORM a\n" +
                    "\tINNER JOIN APP_LABEL_ECONOMICS b ON a.ID = b.LABEL_SIGN_ID\n" +
                    "\tWHERE\n" +
                    "\ta.SIGN_STATE IN (:state)\n" +
                    "\tAND b.LABEL_VALUE = c.LABEL_VALUE\n" +
                    "AND a.SIGN_CHANGE_STATE != '2' ";
            if(StringUtils.isNotBlank(qvo.getTeamId())){
                map.put("teamId",qvo.getTeamId());
                resultTeamId = qvo.getTeamId();
                sqlLabel +=" AND a.SIGN_TEAM_ID =:teamId ";
                AppTeam team = (AppTeam)sysDao.getServiceDo().find(AppTeam.class,qvo.getTeamId());
                if(team!=null){
                    if(!qvo.getDrId().equals(team.getTeamDrId())){//非本团队团长带医生限制条件查询
                        sqlLabel +=  " AND a.SIGN_DR_ID =:drId ";
                    }
                }
            }
            sqlLabel += sqlTime;
            sqlLabel += " AND b.LABEL_TYPE ='4'";
            sqlLabel += "\tGROUP BY\n" +
                    "\tb.LABEL_VALUE";
        }
        String sql = "SELECT ("+sqlLabel+") count," +
                " '"+qvo.getDrId()+"' drId," +
                " '"+resultTeamId+"' teamId," +
                "c.LABEL_TITLE typeName," +
                "c.LABEL_VALUE typeValue," +
                "c.LABEL_TYPE type," +
                "'' peopleList " +
                "FROM APP_LABEL_MANAGE c " +
                "WHERE c.LABEL_TYPE = :type" +
                " ORDER BY c.LABEL_VALUE";
        List<YsChangeCountEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,YsChangeCountEntity.class);
        return ls;
    }

    @Override
    public List<YsChangePeopleEntity> findPeople(YsChangeCountQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("drId",qvo.getDrId());
        map.put("teamId",qvo.getTeamId());
        String[] signStates = new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
        map.put("state",signStates);
        map.put("changeState", ChangeState.SQBG.getValue());
        String sql ="SELECT e.ID id," +
                "e.PATIENT_NAME name," +
                "e.PATIENT_IMAGEURL imageUrl," +
                "e.PATIENT_GENDER sex  " +
                "FROM " +
                "APP_PATIENT_USER e " +
                "INNER JOIN APP_SIGN_FORM f ON e.ID = f.SIGN_PATIENT_ID " +
                "WHERE " +
                "f.SIGN_STATE IN (:state) " +
                "AND f.SIGN_DR_ID =:drId " +
                "AND f.SIGN_TEAM_ID =:teamId " +
                "AND f.SIGN_CHANGE_STATE != '2' ";
        if(StringUtils.isNotBlank(qvo.getPatientName())){
            map.put("patientName","%"+qvo.getPatientName()+"%");
            sql += "AND e.PATIENT_NAME LIKE :patientName " ;
        }
        sql += " AND f.SIGN_FROM_DATE <=SYSDATE() AND f.SIGN_TO_DATE>SYSDATE() ";
        List<YsChangePeopleEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,YsChangePeopleEntity.class);
        return ls;
    }

    /**
     * 查询变更人员列表信息
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<YsChangePatientEntity> findChangePatient(YsChangeSureQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        List<AppSignChange> lsc = sysDao.getServiceDo().loadByPk(AppSignChange.class,"changeNum",qvo.getNum());
        String signId = "";
        if(lsc!=null&&lsc.size()>0){
            for(AppSignChange sc:lsc){
                if(StringUtils.isNotBlank(sc.getChangeSignId())){
                    signId += sc.getChangeSignId()+";";
                }
            }
        }
        if(StringUtils.isNotBlank(signId)){
            map.put("signIds",signId.split(";"));
            String sql = "SELECT " +
                    "e.ID id," +
                    "e.PATIENT_NAME name," +
                    "e.PATIENT_IMAGEURL image," +
                    "e.PATIENT_GENDER sex," +
                    "'' age," +
                    "(SELECT GROUP_CONCAT(LABEL_TITLE) FROM APP_LABEL_GROUP WHERE LABEL_SIGN_ID = f.ID AND LABEL_TYPE ='3' ) label " +
                    "FROM " +
                    "APP_PATIENT_USER e " +
                    "INNER JOIN APP_SIGN_FORM f ON e.ID = f.SIGN_PATIENT_ID " +
                    "WHERE " +
                    "f.ID IN :signIds";
            List<YsChangePatientEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,YsChangePatientEntity.class);
            return ls;
        }
        return null;
    }


    /**
     * 体征居民列表查询
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<AppDrTzEntity> findTzJmList(AppDrTzQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("drId",qvo.getDrId());
        String[] signStates = new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
        map.put("SIGN_STATE",signStates);
        String sql = "SELECT c.ID userId," +
                "c.PATIENT_NAME userName," +
                "c.PATIENT_IMAGEURL imageUrl," +
                "c.PATIENT_GENDER sex," +
                "a.SIGN_DR_ID drId," +
                "(SELECT GROUP_CONCAT(LABEL_COLOR) from APP_LABEL_DISEASE g where g.LABEL_TYPE=2 and g.LABEL_SIGN_ID=a.ID and g.LABEL_VALUE =b.LABEL_VALUE) disColor," +
                "(SELECT GROUP_CONCAT(LABEL_VALUE) from APP_LABEL_DISEASE g where g.LABEL_TYPE=2 and g.LABEL_SIGN_ID=a.ID and g.LABEL_VALUE =b.LABEL_VALUE) disType," +
                "(SELECT GROUP_CONCAT(LABEL_TITLE) from APP_LABEL_DISEASE g where g.LABEL_TYPE=2 and g.LABEL_SIGN_ID=a.ID and g.LABEL_VALUE =b.LABEL_VALUE) disTypeName, " +
                "'' age," +
                "'' dayNum " +
                "FROM " +
                "APP_SIGN_FORM a " +
                "LEFT JOIN APP_LABEL_DISEASE b ON a.ID = b.LABEL_SIGN_ID " +
                "LEFT JOIN APP_PATIENT_USER c ON a.SIGN_PATIENT_ID = c.ID " +
                "where 1=1 and a.SIGN_STATE IN (:SIGN_STATE) AND b.LABEL_VALUE IN ('201','202') AND a.SIGN_CONTRACT_STATE='0' AND a.SIGN_DR_ID=:drId";
        if("1".equals(qvo.getType())){//模糊查询
            map.put("userName","%"+qvo.getPatientName()+"%");
            sql += " AND c.PATIENT_NAME LIKE :userName";
        }
        List<AppDrTzEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppDrTzEntity.class);
        return list;
    }

    /**
     * 查询履约人员列表
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<AppLyPeopleEntity> findLyPeopleList(AppLyQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("pcNum",qvo.getPcNum());
        String sql = "SELECT " +
                "b.ID patientId," +
                "b.PATIENT_NAME patientName," +
                "b.PATIENT_IMAGEURL imgUrl," +
                "b.PATIENT_GENDER sex," +
                "b.PATIENT_IDNO patientIdno," +
                "'' age " +
                "FROM APP_PERFORMANCE_RECORD a " +
                "INNER JOIN APP_PATIENT_USER b ON a.APR_PATIENT_ID=b.ID " +
                "WHERE 1=1 ";
        sql += " AND a.APR_PC_NUM =:pcNum";
        List<AppLyPeopleEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppLyPeopleEntity.class);
        return list;
    }

    /**
     * 健康教育和健康指导履约人员列表
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<AppPeopleHealthEntity> findLyCount(AppPeopleTypeQvo qvo) throws Exception {

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("type",qvo.getFindType());
        map.put("lyNum",qvo.getLyNum());
        String sqlOne = "SELECT\n" +
                "\tb.LABEL_TYPE labelType,\n" +
                "\tcount(1) count\n" +
                "FROM\n" +
                "\tAPP_PERFORMANCE_RECORD a\n" +
                "INNER JOIN APP_LABEL_GROUP b ON a.APR_SIGN_ID = b.LABEL_SIGN_ID\n" +
                "WHERE\n" +
                "\ta.APR_PC_NUM = :lyNum\n" +
                "AND b.LABEL_TYPE ='3' " +
                "GROUP BY\n" +
                "\tb.LABEL_TYPE";

        String sqlTwo = "SELECT\n" +
                "\t'1' labelType,\n" +
                "\tcount(1)\n" +
                "FROM\n" +
                "\tAPP_SIGN_FORM a INNER JOIN APP_PERFORMANCE_RECORD b ON a.ID = b.APR_SIGN_ID\n" +
                "\tWHERE\n" +
                "b.APR_PC_NUM=:lyNum " +
                "\t\tAND a.SIGN_HEALTH_GROUP IS NOT NULL ";

        String sqlThree =  "SELECT\n" +
                "\tb.LABEL_TYPE labelType,\n" +
                "\tcount(1) count\n" +
                "FROM\n" +
                "\tAPP_PERFORMANCE_RECORD a\n" +
                "INNER JOIN APP_LABEL_DISEASE b ON a.APR_SIGN_ID = b.LABEL_SIGN_ID\n" +
                "WHERE\n" +
                "a.APR_PC_NUM=:lyNum " +
                "AND b.LABEL_TYPE ='2' " +
                "GROUP BY\n" +
                "\tb.LABEL_TYPE";
        String sql = sqlOne +" UNION "+sqlThree+" UNION "+sqlTwo;
        List<AppPeopleHealthEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppPeopleHealthEntity.class);
        return ls;
    }

    @Override
    public List<AppComLyPeopleEntity> findByLyType(AppPeopleTypeQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("type",qvo.getFindType());
        map.put("lyNum",qvo.getLyNum());
        String sqlLabel = null;
        if(LabelManageType.JKQK.getValue().equals(qvo.getFindType())){
            sqlLabel = "\tSELECT\n" +
                    "\tcount(1)\n" +
                    "\tFROM\n" +
                    "\t\tAPP_SIGN_FORM a INNER JOIN APP_PERFORMANCE_RECORD b ON a.ID=b.APR_SIGN_ID\n" +
                    "\tWHERE\n" +
                    "\tb.APR_PC_NUM = :lyNum\n" +
                    "\tAND a.SIGN_HEALTH_GROUP = c.LABEL_VALUE\n" +
                    "\tGROUP BY\n" +
                    "\ta.SIGN_HEALTH_GROUP";
        }else if(LabelManageType.FWRQ.getValue().equals(qvo.getFindType())){
            sqlLabel = "\tSELECT\n" +
                    "\tcount(1)\n" +
                    "\tFROM\n" +
                    "\tAPP_PERFORMANCE_RECORD a\n" +
                    "\tINNER JOIN APP_LABEL_GROUP b ON a.APR_SIGN_ID = b.LABEL_SIGN_ID\n" +
                    "\tWHERE\n" +
                    "\tb.LABEL_VALUE = c.LABEL_VALUE\n" +
                    "\tAND a.APR_PC_NUM = :lyNum\n" +
                    "\tAND b.LABEL_TYPE = :type\n" +
                    "\tGROUP BY\n" +
                    "\tb.LABEL_VALUE";
        }else if(LabelManageType.JBLX.getValue().equals(qvo.getFindType())){
            sqlLabel = "\tSELECT\n" +
                    "\tcount(1)\n" +
                    "\tFROM\n" +
                    "\tAPP_PERFORMANCE_RECORD a\n" +
                    "\tINNER JOIN APP_LABEL_DISEASE b ON a.APR_SIGN_ID = b.LABEL_SIGN_ID\n" +
                    "\tWHERE\n" +
                    "\ta.APR_PC_NUM = :lyNum\n" +
                    "\tAND b.LABEL_VALUE = c.LABEL_VALUE\n" +
                    "\tGROUP BY\n" +
                    "\tb.LABEL_VALUE";
        }
        String sql = "SELECT ("+sqlLabel+") count," +
                "c.LABEL_TITLE typeName," +
                "c.LABEL_VALUE typeValue," +
                "c.LABEL_TYPE type," +
                "'"+qvo.getLyNum()+"' pcNum, " +
                "'' lyList " +
                "FROM APP_LABEL_MANAGE c WHERE c.LABEL_TYPE = :type" +
                " ORDER BY c.LABEL_VALUE";
        List<AppComLyPeopleEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppComLyPeopleEntity.class);
        return ls;
    }

    /**
     * 履约人员列表
     * @param qvo
     * @return
     * @throws Exception
     */
    @Override
    public List<AppLyPatientEntity> findLyPatient(AppPeopleTypeQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("lyNum",qvo.getLyNum());
        String sql = "SELECT " +
                "a.ID patientId," +
                "a.PATIENT_NAME patientName," +
                "a.PATIENT_GENDER patientSex," +
                "a.PATIENT_IDNO patientIdno," +
                "a.PATIENT_TEL patientTel," +
                "a.PATIENT_BIRTHDAY patientBirthday," +
                "(SELECT GROUP_CONCAT(LABEL_COLOR) from APP_LABEL_DISEASE g where g.LABEL_TYPE='2' and g.LABEL_SIGN_ID=b.APR_SIGN_ID ) labelColor," +
                "(SELECT GROUP_CONCAT(LABEL_TITLE) from APP_LABEL_DISEASE g where g.LABEL_TYPE='2' and g.LABEL_SIGN_ID=b.APR_SIGN_ID ) labelTitle," +
                "b.APR_TYPE perType," +
                "'' patientAge " +
                "FROM APP_PATIENT_USER a INNER JOIN APP_PERFORMANCE_RECORD b ON a.ID = b.APR_PATIENT_ID " +
                "WHERE b.APR_PC_NUM =:lyNum ";
        List<AppLyPatientEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppLyPatientEntity.class);
        return list;
    }

    @Override
    public List<JmInfo> findByEconomicType(AppCommQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("value",qvo.getSignsJjType());
        String sql = "SELECT\n" +
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
                "\tc.PATIENT_ADDRESS address,\n" +
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
                "LEFT JOIN APP_LABEL_ECONOMICS b ON a.ID = b.LABEL_SIGN_ID\n" +
                "LEFT JOIN APP_PATIENT_USER c ON a.SIGN_PATIENT_ID = c.ID\n" +
                "WHERE 1=1 AND a.SIGN_FROM_DATE<=SYSDATE() AND a.SIGN_TO_DATE>SYSDATE() AND b.LABEL_VALUE = :value\n";
        map.put("SIGN_TEAM_ID",qvo.getTeamId());
        sql += " AND a.SIGN_TEAM_ID=:SIGN_TEAM_ID ";
        String[] signStates = new String[]{SignFormType.YUQY.getValue(),SignFormType.YQY.getValue()};
        map.put("SIGN_STATE",signStates);
        sql += " AND a.SIGN_STATE IN (:SIGN_STATE) ";
        if(StringUtils.isNotBlank(qvo.getPatientName())){
            sql = sql + " AND ( c.PATIENT_NAME LIKE :searchValue OR c.PATIENT_IDNO LIKE :searchValue OR c.PATIENT_BOPOMOFO LIKE :searchValue ) ";
            map.put("searchValue", "%"+qvo.getPatientName()+"%");
        }
        sql += " GROUP BY c.ID ";
        List<JmInfo> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,JmInfo.class,qvo);
        return list;
    }

    @Override
    public PatientInfo findUserInfoO(String patientId, String state, String type,String teamId) throws Exception{
        HashMap map = new HashMap();
        String sql = "SELECT\n" +
                "\tc.ID id,\n" +
                "\ta.ID signFormId,\n" +
                "\tc.PATIENT_NAME name,\n" +
                "\tc.PATIENT_GENDER sex,\n" +
                "\tc.PATIENT_IMAGEURL imgurl,\n" +
                "\tc.PATIENT_IDNO idno,\n" +
                "'' age," +
                "\tc.PATIENT_TEL phone,\n" +
                "\tc.PATIENT_CARD card,\n" +
                "\tc.PATIENT_PROVINCE province,\n" +
                "\tc.PATIENT_CITY city,\n" +
                "\tc.PATIENT_AREA area,\n" +
                "\tc.PATIENT_STREET street,\n" +
                "\tc.PATIENT_NEIGHBORHOOD_COMMITTEE committee,\n" +
                "\tc.PATIENT_ADDRESS address,\n" +
                "\ta.SIGN_STATE qyzt,\n" +
                "\ta.SIGN_TYPE qylx,\n" +
                "\ta.SIGN_TYPE qyType,\n" +
                "\ta.SIGN_SERVICE_A ssgg,\n" +
                "\ta.SIGN_SERVICE_B jjyl,\n" +
                "\ta.SIGN_SERVICE_B_COLOR jjylcolor,\n" +
                "\ta.SIGN_FROM_DATE fromDate,\n" +
                "\ta.SIGN_TO_DATE endDate,\n" +
                "\t(SELECT GROUP_CONCAT(LABEL_TITLE) from APP_LABEL_GROUP g where g.LABEL_TYPE=3 and g.LABEL_SIGN_ID=a.ID) fwrq,\n" +
                "\ta.SIGN_HEALTH_GROUP jkqk,\n" +
                "\t(SELECT GROUP_CONCAT(LABEL_TITLE) from APP_LABEL_DISEASE g where g.LABEL_TYPE=2 and g.LABEL_SIGN_ID=a.ID) labtitle,\n" +
                "\t(SELECT GROUP_CONCAT(LABEL_COLOR) from APP_LABEL_DISEASE g where g.LABEL_TYPE=2 and g.LABEL_SIGN_ID=a.ID) labcolor,\n" +
                "\t(SELECT k.ORDER_DATE from APP_ORDER k where k.ORDER_SIGN_ID=a.ID) payDate,\n" +
                "\ta.SIGN_PAY_STATE signPayState,\n" +
                "\ta.SIGN_TEAM_ID signTeamId," +
                "\tc.PATIENT_CITY cityCode,\n" +
                "\t(SELECT GROUP_CONCAT(LABEL_TITLE) from APP_LABEL_ECONOMICS g where g.LABEL_TYPE= 4 and g.LABEL_SIGN_ID=a.ID) signJjType,\n" +
                "\ta.SIGN_PACKAGEID serveList," +
                "a.SIGN_HOSP_ID signHoapId," +
                "a.SIGN_DR_ID signDrId," +
                "'' referralState " +
                "FROM\n" +
                "\tAPP_SIGN_FORM a\n" +
                "LEFT JOIN APP_PATIENT_USER c ON a.SIGN_PATIENT_ID = c.ID\n" +
                "where 1=1";
        if(StringUtils.isNotBlank(patientId)){
            map.put("patientId", patientId);
            sql += " AND a.SIGN_PATIENT_ID = :patientId";
        }
        if(StringUtils.isNotBlank(state)){
            if(state.equals("1")){
                String[] signStates = new String[]{SignFormType.DQY.getValue(),SignFormType.YUZQ.getValue()};
                map.put("SIGN_STATE", signStates);
                sql += " AND a.SIGN_STATE IN (:SIGN_STATE) ";
            }else {
                if("1".equals(type)){//查询有效签约状态的人员信息
                    String[] signStates = new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
                    map.put("SIGN_STATE", signStates);
                    sql += " AND a.SIGN_STATE IN (:SIGN_STATE) ";
                }else if("2".equals(type)){//查询过期数据
                    map.put("SIGN_STATE",SignFormType.YJY.getValue());
                    map.put("SIGN_TEAM_ID",teamId);
                    sql += " AND a.SIGN_STATE =:SIGN_STATE AND a.SIGN_TEAM_ID =:SIGN_TEAM_ID ";
                }else if("3".equals(type)){//删除数据
                    map.put("SIGN_STATE",SignFormType.SC.getValue());
                    map.put("SIGN_TEAM_ID",teamId);
                    sql += " AND a.SIGN_STATE =:SIGN_STATE AND a.SIGN_TEAM_ID =:SIGN_TEAM_ID ";
                }


            }

        }

        sql=sql+" GROUP BY c.ID";
        List<PatientInfo> ls=sysDao.getServiceDo().findSqlMapRVo(sql,map, PatientInfo.class);
        if(ls!=null && !ls.isEmpty()){
            return ls.get(0);
        }
        return null;
    }

    /**
     * 居民健康档案签约状态
     * @param patientIdNo
     * @param patientJmda
     * @return
     */
    @Override
    public List<AppSignForm> getAppSignState(String patientIdNo,String patientJmda)throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        String sql = "select b.* from app_patient_user a INNER JOIN app_sign_form b ON a.ID=b.SIGN_PATIENT_ID where b.SIGN_STATE in('0','2')";
        if(StringUtils.isNotEmpty(patientIdNo)){
            map.put("patientIdNo",patientIdNo);
            sql += " and a.PATIENT_IDNO =:patientIdNo";
        }
        if(StringUtils.isNotEmpty(patientJmda) && !"0".equals(patientJmda)){
            map.put("patientJmda",patientJmda);
            sql += " and a.PATIENT_JMDA =:patientJmda";
        }
        String[] state = {SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};//状态
        map.put("SIGN_STATE",state);
        sql += " and b.SIGN_STATE IN (:SIGN_STATE) ";
        List<AppSignForm> list = sysDao.getServiceDo().findSqlMap(sql,map,AppSignForm.class);
        if(list != null){
            return list;
        }
        return  null;
    }


    public int Apppatientlist()throws Exception
    {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql =" SELECT COUNT(*) from app_patient_user where PATIENT_JMDA is null or PATIENT_JMDA =''  ";
        int count =sysDao.getServiceDo().gteSqlCount(sql,map);


        return count;

    }


    public List<AppPatientUser> AppPatientFind(int pageStartNo,int pageSize)throws  Exception
    {
        Map<String,Object> map = new HashMap<String,Object>();
        CommConditionVo qvo  = new CommConditionVo();
        qvo.setPageStartNo(pageStartNo);
        qvo.setPageSize(pageSize);
        String sql =" SELECT * from app_patient_user where PATIENT_JMDA is null or PATIENT_JMDA =''  ";
        List<AppPatientUser> list = sysDao.getServiceDo().findSqlMap(sql,map,AppPatientUser.class,qvo);
        System.out.print("");
        return list ;
    }

    public AppHealthCardRecodesVo AppDeptCode(String id)throws  Exception
    {
        Map<String,Object> map = new HashMap<String,Object>();
        String sql =" SELECT left(d.HOSP_AREA_CODE,4)urlType, d.ID orgId  from app_patient_user u INNER JOIN app_sign_form f on u.ID = f.SIGN_PATIENT_ID INNER JOIN app_hosp_dept d on f.SIGN_HOSP_ID = d.ID where f.sign_state in ('0','2') and u.ID =:id ";
        map.put("id",id);
        List<AppHealthCardRecodesVo> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppHealthCardRecodesVo.class);
        if(list !=null && list.size()>0){
            return list.get(0);
        }
        return null ;
    }

    public void AppPatientUpdate(AppPatientDAHVo vo)throws Exception
    {
        if(StringUtils.isNotBlank(vo.getId())){
            AppPatientUser user = (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,vo.getId());
            if(user != null){
                user.setPatientjmda(vo.getPatientjmda());
                user.setPatientjtda(vo.getPatientjtda());
                sysDao.getServiceDo().modify(user);
            }
        }
//        Map<String,Object> map = new HashMap<String,Object>();
//        String sql =" UPDATE app_patient_user u set u.PATIENT_JMDA =:jmda ,u.PATIENT_JTDA =:jtda where u.ID =:id ";
//        map.put("jmda",vo.getPatientjmda());
//        map.put("jtda",vo.getPatientjtda());
//        map.put("id",vo.getId());
//        sysDao.getServiceDo().update(sql,map);
    }



    /**
     * 根据身份证和社保卡查询居民数据
     * @param userIdNo
     * @param userCard
     * @return
     */
    @Override
    public AppPatientUserEntity findByUserIdNoAndCard(String userIdNo, String userCard) throws Exception{
        HashMap map = new HashMap();
        String sql="SELECT\n" +
                "\tID id,\n" +
                "\tID easeId,\n" +
                "\tPATIENT_NAME patientName,\n" +
                "\tPATIENT_GENDER patientGender,\n" +
                "\tPATIENT_BIRTHDAY patientBirthday,\n" +
                "\tPATIENT_IDNO patientIdno,\n" +
                "\tPATIENT_CARD patientCard,\n" +
                "\tPATIENT_ID_CARD_TEMP paitentIdCardTemp,\n" +
                "\tPATIENT_AGE patientAge,\n" +
                "\tPATIENT_TEL patientTel,\n" +
                "\tPATIENT_ADDRESS patientAddress,\n" +
                "\tPATIENT_IMAGEURL patientImageurl,\n" +
                "\tPATIENT_IMAGE_NAME patientImageName,\n" +
                "\tPATIENT_PROVINCE patientProvince,\n" +
                "\tPATIENT_CITY patientCity,\n" +
                "\tPATIENT_AREA patientArea,\n" +
                "\tPATIENT_STREET patientStreet,\n" +
                "\tPATIENT_NEIGHBORHOOD_COMMITTEE patientNeighborhoodCommittee,\n" +
                "\tPATIENT_PROVINCE patientProvinceName,\n" +
                "\tPATIENT_CITY patientCityName,\n" +
                "\tPATIENT_AREA patientAreaName,\n" +
                "\tPATIENT_STREET patientStreetName,\n" +
                "\tPATIENT_NEIGHBORHOOD_COMMITTEE patientNeighborhoodCommitteeName,\n" +
                "\t'' patientCommunity,\n" +
                "\tPATIENT_STATE patientState,\n" +
                "\tPATIENT_HEALTHY patientHealthy,\n" +
                "\t'1' patientFirstState,\n" +
                "\tPATIENT_CITY cityCode,\n" +
                "\tPATIENT_WEIGHT patientWeight,\n" +
                "\tPATIENT_HEIGHT patientHeight,\n" +
                "\tPATIENT_BMI patientBmi," +
                "\tPATIENT_PWD_STATE pwdState\n" +
                "FROM\n" +
                "\tAPP_PATIENT_USER\n" +
                "WHERE\n" +
                "\t1 = 1";
            if(StringUtils.isNotBlank(userIdNo)){
                map.put("patientIdNo",userIdNo);
                sql+=" AND PATIENT_IDNO = :patientIdNo ";
            }
//            if(StringUtils.isNotBlank(userCard)){
//                map.put("patientCard",userCard);
//                sql+=" AND PATIENT_CARD = :patientCard ";
//            }
        List<AppPatientUserEntity> ls = sysDao.getServiceDo().findSqlMapRVo(sql, map, AppPatientUserEntity.class);
        if(ls != null && ls.size() >0){
            return  ls.get(0);
        }
        return null;
    }

    @Override
    public PatientInfo findUserInfooo(String patientIdno,String drId) throws Exception {
        HashMap map = new HashMap();
        String sql = "SELECT\n" +
                "\tc.ID id,\n" +
                "\ta.ID signFormId,\n" +
                "\tc.PATIENT_NAME name,\n" +
                "\tc.PATIENT_GENDER sex,\n" +
                //"\tc.PATIENT_AGE age,\n" +
                "\tc.PATIENT_IMAGEURL imgurl,\n" +
                "\tc.PATIENT_IDNO idno,\n" +
                "'' age," +
                "\tc.PATIENT_TEL phone,\n" +
                "\tc.PATIENT_CARD card,\n" +
                "\tc.PATIENT_PROVINCE province,\n" +
                "\tc.PATIENT_CITY city,\n" +
                "\tc.PATIENT_AREA area,\n" +
                "\tc.PATIENT_STREET street,\n" +
                "\tc.PATIENT_NEIGHBORHOOD_COMMITTEE committee,\n" +
                "\tc.PATIENT_ADDRESS address,\n" +
                "\ta.SIGN_STATE qyzt,\n" +
                "\ta.SIGN_TYPE qylx,\n" +
                "\ta.SIGN_TYPE qyType,\n" +
                "\ta.SIGN_SERVICE_A ssgg,\n" +
                "\ta.SIGN_SERVICE_B jjyl,\n" +
                "\ta.SIGN_SERVICE_B_COLOR jjylcolor,\n" +
                "\ta.SIGN_FROM_DATE fromDate,\n" +
                "\ta.SIGN_TO_DATE endDate,\n" +
                "\t(SELECT GROUP_CONCAT(LABEL_TITLE) from APP_LABEL_GROUP g where g.LABEL_TYPE=3 and g.LABEL_SIGN_ID=a.ID) fwrq,\n" +
                "\ta.SIGN_HEALTH_GROUP jkqk,\n" +
                "\t(SELECT GROUP_CONCAT(LABEL_TITLE) from APP_LABEL_DISEASE g where g.LABEL_TYPE=2 and g.LABEL_SIGN_ID=a.ID) labtitle,\n" +
                "\t(SELECT GROUP_CONCAT(LABEL_COLOR) from APP_LABEL_DISEASE g where g.LABEL_TYPE=2 and g.LABEL_SIGN_ID=a.ID) labcolor,\n" +
                "\t(SELECT k.ORDER_DATE from APP_ORDER k where k.ORDER_SIGN_ID=a.ID) payDate,\n" +
                "\ta.SIGN_PAY_STATE signPayState,\n" +
                "\ta.SIGN_TEAM_ID signTeamId," +
                "\tc.PATIENT_CITY cityCode,\n" +
                "\t(SELECT GROUP_CONCAT(LABEL_TITLE) from APP_LABEL_ECONOMICS g where g.LABEL_TYPE= 4 and g.LABEL_SIGN_ID=a.ID) signJjType,\n" +
                "\ta.SIGN_PACKAGEID serveList," +
                "a.SIGN_HOSP_ID signHoapId," +
                "a.SIGN_DR_ID signDrId," +
                "'' referralState," +
                "'' imgUrls," +
                "a.SIGN_TO_DATE renewState," +
                "a.SIGN_GOTO_SIGN_STATE signGoToSignState " +
                "FROM\n" +
                "\tAPP_SIGN_FORM a\n" +
//                "LEFT JOIN APP_LABEL_GROUP b ON a.ID = b.LABEL_SIGN_ID\n" +
                "LEFT JOIN APP_PATIENT_USER c ON a.SIGN_PATIENT_ID = c.ID\n" +
                "where 1=1";
        if(StringUtils.isBlank(patientIdno)){
            throw new Exception("请求参数异常!");
        }else{
            if(StringUtils.isNotBlank(patientIdno)){
                map.put("patientIdno", patientIdno);
                sql += " AND c.PATIENT_IDNO = :patientIdno";
            }
            if(StringUtils.isNotBlank(drId)){
                map.put("drId",drId);
                sql += " AND a.SIGN_DR_ID =:drId";
            }
            String[] signStates = new String[]{SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
            map.put("SIGN_STATE", signStates);
            sql += " AND a.SIGN_STATE IN (:SIGN_STATE) ";
            sql=sql+" GROUP BY c.ID";
            List<PatientInfo> ls=sysDao.getServiceDo().findSqlMapRVo(sql,map, PatientInfo.class);
            if(ls!=null && !ls.isEmpty()){
                return ls.get(0);
            }
        }

        return null;
    }

    @Override
    public Map<String, Object> findPatentUserName(String drPatientId) throws Exception{
        HashMap map = new HashMap();
        String sql = "SELECT * FROM APP_PATIENT_USER a where 1=1";
        if(StringUtils.isNotBlank(drPatientId)){
            map.put("ID", drPatientId);
            sql += " AND a.ID = :ID";
        }
        List<Map> ls=sysDao.getServiceReadDo().findSqlMap(sql,map);
        if(ls!=null && !ls.isEmpty()){
            return ls.get(0);
        }
        return null;
    }

    @Override
    public List<AppPatientSignAndJdEntity> findSignAndJdByQvo(AppCommQvo qvo) throws Exception {
        Map<String,Object> map = new HashMap<>();
        String sql = "SELECT ID id," +
                "PATIENT_NAME name," +
                "PATIENT_GENDER sex," +
                "PATIENT_BIRTHDAY birthDay," +
                "PATIENT_IDNO idno," +
                "'' signId," +
                "'' jmdah " +
                "FROM APP_PATIENT_USER WHERE 1=1";
        //根据姓名搜索
        if(StringUtils.isNotBlank(qvo.getPatientName())){
            map.put("name","%"+qvo.getPatientName()+"%");
            sql += " AND PATIENT_NAME LIKE :name";
        }
        //根据性别搜索
        if(StringUtils.isNotBlank(qvo.getSex())){
            map.put("sex",qvo.getSex());
            sql += " AND PATIENT_GENDER =:sex";
        }
        //根据出生日期搜索
        if(StringUtils.isNotBlank(qvo.getBirthday())){
            map.put("birthDay",qvo.getBirthday());
            sql += " AND PATIENT_BIRTHDAY =:birthDay";
        }
//        //根据身份证搜索
//        if(StringUtils.isNotBlank(qvo.getPatientIdno())){
//            map.put("idno",qvo.getPatientIdno());
//            sql +=" AND PATIENT_IDNO =:idno";
//        }
        List<AppPatientSignAndJdEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppPatientSignAndJdEntity.class);
        return list;
    }

    /**
     * 通过身份证和姓名获取居民信息
     * WangCheng
     * @param idno
     * @param name
     * @return
     */
    @Override
    public AppPatientUser findByIdnoAndName(String idno, String name)throws Exception {
        Map<String,Object> map = new HashMap<>();
        map.put("idno",idno);
        map.put("name",name);
        String sql = "SELECT * FROM app_patient_user WHERE 1=1";
        if(StringUtils.isNotEmpty(idno)){
            map.put("idno",idno);
            sql += " AND PATIENT_IDNO = :idno";
        }
        if(StringUtils.isNotEmpty(name)){
            map.put("name",name);
            sql += " AND PATIENT_NAME = :name";
        }
        List<AppPatientUser> list = sysDao.getServiceDo().findSqlMap(sql,map,AppPatientUser.class);
        if(list != null && list.size()>0){
            return list.get(0);
        }
        return null;
    }

    public List<AppPatientUser> listPatient(String streetCode) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append(" select a.* from app_patient_user a where 1 = 1 ");
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotBlank(streetCode)) {
            sql.append(" and a.patient_street = :patientStreet ");
            map.put("patientStreet", streetCode);
        }
        List<AppPatientUser> list = sysDao.getServiceDo().findSqlMap(sql.toString(), map, AppPatientUser.class);
        return list;
    }

    @Override
    public boolean findByIdNoAndJh(String userId,String idNo, String jhState) throws Exception{
        Map<String,Object> map = new HashMap<>();
        String sql = "SELECT * FROM APP_PATIENT_USER WHERE 1=1 ";
        map.put("idNo",idNo);
        map.put("jhState",jhState);
        map.put("patientId",userId);
        sql += " AND PATIENT_IDNO =:idNo ";
        sql += " AND PATIENT_UP_HPIS =:jhState ";
        sql += " AND ID =:patientId ";
        List<AppPatientUser> list = sysDao.getServiceDo().findSqlMap(sql,map,AppPatientUser.class);
        if(list != null && list.size()>0){
            return true;
        }
        return false;
    }

    @Override
    public List<AppTestPatientEntity> findRepeatPatient() throws Exception{
        Map<String,Object> map = new HashedMap();
        String sql = "SELECT\n" +
                "\tPATIENT_IDNO patientIdno,\n" +
                "\tCOUNT(1) num\n" +
                "FROM\n" +
                "\tapp_patient_user \n" +
                "WHERE\n" +
                "\tPATIENT_IDNO IS NOT NULL\n" +
                "AND PATIENT_UP_HPIS != '998'\n" +
                "GROUP BY\n" +
                "\tPATIENT_IDNO\n" +
                "HAVING\n" +
                "\tnum > 1\n" +
                "ORDER BY num DESC";
        List<AppTestPatientEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppTestPatientEntity.class);
        if(list != null && list.size()>0){
            return list;
        }
        return null;
    }

    @Override
    public List<AppTestPatientEntity> findRepeatPatientByIdno(String idno) throws Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("patientIdno",idno);
        String sqlCount = "SELECT a.ID patientIdno,\n" +
                "(SELECT count(1) FROM app_sign_form WHERE SIGN_PATIENT_ID = a.ID ) num \n" +
                "FROM app_patient_user a\n" +
                "WHERE a.PATIENT_IDNO = :patientIdno\n" +
                "ORDER BY num DESC ";
        List<AppTestPatientEntity> list = sysDao.getServiceDo().findSqlMapRVo(sqlCount,map,AppTestPatientEntity.class);
        if(list != null && list.size()>0){
            return list;
        }
        return null;
    }

    @Override
    public List<AppPatientUser> findByUpHpis() throws Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("up_hpis","998");
        String sql = "SELECT * FROM APP_PATIENT_USER WHERE 1=1 " +
                "AND PATIENT_UP_HPIS =:up_hpis ";
        List<AppPatientUser> list = sysDao.getServiceDo().findSqlMap(sql,map,AppPatientUser.class);
        if(list != null && list.size()>0){
            return list;
        }
        return null;
    }

    @Override
    public Map<String,Object> getPatientInfoLogin(AppPatientUserEntity patient,AppPatientPerfectDataEntity vo,String type) throws Exception{
        Map<String,Object> map = new HashMap<String,Object>();
        AppPatientUser user = (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,patient.getId());
        if(StringUtils.isNotBlank(vo.getEhcId()) && StringUtils.isNotBlank(vo.getEhcCardNo())){
            if(StringUtils.isNotBlank(user.getPatientEHCId()) && StringUtils.isNotBlank(user.getPatientEHCNo())){
                if(!user.getPatientEHCId().equals(vo.getEhcId())){
                    user.setPatientEHCId(vo.getEhcId());
                }
                if(!user.getPatientEHCNo().equals(vo.getEhcCardNo())){
                    user.setPatientEHCNo(vo.getEhcCardNo());
                }
            }else{
                user.setPatientEHCId(vo.getEhcId());
                user.setPatientEHCNo(vo.getEhcCardNo());
            }
            sysDao.getServiceDo().modify(user);
        }

        if(StringUtils.isNotBlank(patient.getPatientNeighborhoodCommittee())){
            AppHospDept dept = (AppHospDept)sysDao.getAppHospDeptDao().findByAreaCodeByOne(patient.getPatientNeighborhoodCommittee());
            if(dept != null){
                patient.setPatientCommunity(dept.getId());
            }else{
                dept = (AppHospDept)sysDao.getAppHospDeptDao().findByAreaCodeByOne(patient.getPatientStreet());
                if(dept != null) {
                    patient.setPatientCommunity(dept.getId());
                }
            }
        }else if(StringUtils.isNotBlank(patient.getPatientStreet())){
            AppHospDept dept = (AppHospDept)sysDao.getAppHospDeptDao().findByAreaCodeByOne(patient.getPatientStreet());
            if(dept != null) {
                patient.setPatientCommunity(dept.getId());
            }
        }
        Md5Util util = new Md5Util();
        String key = patient.getPatientIdno()+ ExtendDate.getYMD_h_m_s(Calendar.getInstance());
        key = util.MD516(key);
        AppDrPatientKey uKey = this.sysDao.getAppDrPatientKeyDao().findByDoctorOrPatientId(patient.getId());
        String day = ExtendDateUtil.addDate(ExtendDate.getYMD(Calendar.getInstance()), ExtendDateType.DAYS.getValue(),15);
        if(uKey != null){
            if(type.equals("1")) {
                uKey.setDrExternalToken(key);
            }else{
                uKey.setDrExternalWechatToken(key);
            }
            uKey.setDrPatientTokenEffectiveTime(ExtendDate.getCalendar(day));
            uKey.setDrPatientType(CommonDrPartientType.huanzhe.getValue());
            patient.setPatientFirstState(CommonEnable.QIYONG.getValue());
            uKey.setDrPatientLastDate(uKey.getHsUpdateTime());
            this.sysDao.getServiceDo().modify(uKey);
        }else{
            uKey = new AppDrPatientKey();
            if(type.equals("1")) {
                uKey.setDrExternalToken(key);
            }else{
                uKey.setDrExternalWechatToken(key);
            }
            uKey.setDrPatientId(patient.getId());
            uKey.setDrPatientTokenEffectiveTime(ExtendDate.getCalendar(day));
            uKey.setDrPatientType(CommonDrPartientType.huanzhe.getValue());
            uKey.setDrPatientLastDate(Calendar.getInstance());
            if(StringUtils.isBlank(patient.getPatientIdno())){
                patient.setPatientFirstState(CommonEnable.JINYONG.getValue());
            }else{
                patient.setPatientFirstState(CommonEnable.QIYONG.getValue());
            }
            sysDao.getServiceDo().add(uKey);
        }
        user.setPatientJgState(UserJgType.YISHEZHI.getValue());
        this.sysDao.getServiceDo().modify(user);
        PatientInfo info = sysDao.getAppPatientUserDao().findUserInfo(patient.getId(),"0",null);
        if(info!=null){
            patient.setSignType(info.getJQ());
            patient.setRetireHome(info.getJjyl());
            patient.setRetireHomeColor(info.getJjylcolor());
            patient.setLabTitle(info.getLabtitle());
            patient.setLabColor(info.getLabcolor());
        }
        if(type.equals("1")) {

        }else{
            String rule = PropertiesUtil.getConfValue("ruleAuthorization");;
            String token = key;
            StringBuilder sb =new StringBuilder(token);
//                                                        System.out.println(token);
            int v = 1;
            for(int i=0;i<rule.length();i++){
                String jg = token.substring(token.length()-v,token.length()-v+1);
                sb.insert(Integer.parseInt(rule.substring(i,i+1)),jg);
                v++;
            }
            key = String.valueOf(sb);
        }
        map.put("key",key);
        map.put("patient",JsonUtil.toJson(patient));
        return  map;
    }


    @Override
    public String addExternalPerfectData(AppPatientPerfectDataEntity vo) throws Exception {
        AppPatientUser user = new AppPatientUser();
        if(StringUtils.isNotBlank(vo.getPatientIdNo())){
            Map<String,Object> idNos;
            if(vo.getPatientIdNo().length() == 18){
                idNos = CardUtil.getCarInfo(vo.getPatientIdNo());
            }else{
                idNos = CardUtil.getCarInfo15W(vo.getPatientIdNo());
            }
            user.setPatientBirthday(ExtendDate.getCalendar(idNos.get("birthday").toString()));//出生日期
            user.setPatientAge(AgeUtil.getAgeYear(user.getPatientBirthday()));//年龄
            user.setPatientGender(idNos.get("sex").toString());//性别
            user.setPatientIdno(vo.getPatientIdNo().toUpperCase());//身份证
        }
        if(StringUtils.isNotBlank(vo.getPatientName())){
            user.setPatientName(vo.getPatientName());
            user.setPatientBopomofo(PinyinUtil.getPinYinHeadChar(user.getPatientName()));
        }
        if(StringUtils.isNotBlank(vo.getPatientCard())){
            user.setPatientCard(vo.getPatientCard());
        }else{
            //添加社保卡号
            String idCard = sysDao.getSecurityCardAsyncBean().getSecurityCardNotAsync(user.getPatientIdno(),user.getPatientName(),user.getId(),user.getPatientName(),DrPatientType.PATIENT.getValue());
            user.setPatientCard(idCard);
        }
        user.setPatientTel(vo.getPatientTel());
        if(StringUtils.isNotBlank(vo.getPatientNeighborhoodCommittee()) && vo.getPatientNeighborhoodCommittee().length() == 12){
            user.setPatientNeighborhoodCommittee(vo.getPatientNeighborhoodCommittee());
            user.setPatientStreet(AreaUtils.getAreaCodeAll(vo.getPatientNeighborhoodCommittee(),"4"));
            user.setPatientArea(AreaUtils.getAreaCodeAll(vo.getPatientNeighborhoodCommittee(),"3"));
            user.setPatientCity(AreaUtils.getAreaCodeAll(vo.getPatientNeighborhoodCommittee(),"2"));
            user.setPatientProvince(AreaUtils.getAreaCodeAll(vo.getPatientNeighborhoodCommittee(),"1"));
        }
        if(StringUtils.isNotBlank(vo.getPatientAddress())) {
            user.setPatientAddress(vo.getPatientAddress());
        }
//        user.setPatientEHCId(vo.getEhcId());
//        user.setPatientEHCNo(vo.getEhcCardNo());
        user.setPatientState(CommonEnable.QIYONG.getValue());
        user.setPatientHealthy(CommonEnable.JINYONG.getValue());
        user.setPatientJgState(UserJgType.WEISHEZHI.getValue());
        user.setPatientEaseState(UserJgType.WEISHEZHI.getValue());
        user.setPatientPwd(Md5Util.MD5(vo.getPatientTel().substring(vo.getPatientTel().length()-6,vo.getPatientTel().length())));
        user.setPatientUpHpis(UserUpHpisType.JIHUO.getValue());//用户激活
        //判断是有未激活用户
        String userId;
        AppPatientUser wjhUser = sysDao.getAppPatientUserDao().findPatientIdNo(user.getPatientIdno(), UserUpHpisType.WEIJIHUO.getValue());
        if(wjhUser != null){
            String wjhUserId = wjhUser.getId();
            CopyUtils.Copy(user,wjhUser);
            wjhUser.setId(wjhUserId);
            wjhUser.setPatientUpHpis(UserUpHpisType.JIHUO.getValue());
            sysDao.getServiceDo().modify(wjhUser);
//            this.sysDao.getServiceDo().removePoFormSession(wjhUser);
            AppDrPatientKey key = this.sysDao.getAppDrPatientKeyDao().findByDoctorOrPatientId(user.getId());
            if(key != null){
                key.setDrPatientId(wjhUserId);
                sysDao.getServiceDo().modify(key);
            }
            userId = wjhUser.getId();
            String result = PropertiesUtil.getConfValue("messageCode");
            if(result.equals("0")){
                if(StringUtils.isBlank(wjhUser.getPatientEaseState())){
                    //添加环新账号
                    sysDao.getSecurityCardAsyncBean().registeredEasemob(userId);
                }
            }
        }else{
            sysDao.getServiceDo().add(user);
            userId = user.getId();
//            this.sysDao.getServiceDo().removePoFormSession(user);
            String result = PropertiesUtil.getConfValue("messageCode");
            if(result.equals("0")){
                //添加环新账号
                sysDao.getSecurityCardAsyncBean().registeredEasemob(user.getId());
            }
        }
        return userId;
    }
}
