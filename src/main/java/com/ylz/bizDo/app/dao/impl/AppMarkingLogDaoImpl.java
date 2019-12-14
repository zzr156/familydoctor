package com.ylz.bizDo.app.dao.impl;

import com.ylz.bizDo.app.dao.AppMarkingLogDao;
import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.bizDo.app.po.AppMarkingLog;
import com.ylz.bizDo.app.po.AppMarkingLogItem;
import com.ylz.bizDo.cd.po.CdUser;
import com.ylz.bizDo.smjq.smEntity.AppModifyLogEntity;
import com.ylz.bizDo.smjq.smEntity.AppModifyPeopleListEntity;
import com.ylz.bizDo.smjq.smVo.AppSmPeopleBasicVo;
import com.ylz.bizDo.sys.vo.SysLogVo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2018/8/15.
 */
@Service("appMarkingLogDao")
@Transactional(rollbackForClassName={"Exception"})
public class AppMarkingLogDaoImpl implements AppMarkingLogDao {
    @Autowired
    private SysDao sysDao;

    @Override
    public void saveMarkingLog(String userId, String businessName, List<AppMarkingLogItem> ls, String hospId,String drName,String drId) throws Exception{
        AppMarkingLog log = new AppMarkingLog();
        log.setBusinessSignId(userId);
        log.setOrgId(hospId);
        log.setUserName(drName);
        log.setUserId(drId);
        log.setBusinessName(businessName);
        if(StringUtils.isNotBlank(hospId)){
            AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,hospId);
            if(dept != null){
                log.setAreaCode(dept.getHospAreaCode());
            }
        }
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        log.setYear(String.valueOf(year));
        log.setYearMonth(ExtendDate.getYM(Calendar.getInstance()));
        sysDao.getServiceDo().add(log);
        for(AppMarkingLogItem ll:ls){
            ll.setLogId(log.getId());
            sysDao.getServiceDo().add(ll);
        }
    }

    @Override
    public List<AppModifyLogEntity> findMarkingLogList(AppSmPeopleBasicVo qvo) throws Exception {
        Map<String,Object> map = new HashMap<>();
        String sql = "SELECT " +
                " a.BUSINESS_SIGN_ID patientId," +
                " a.USER_ID drId," +
                " a.USER_NAME drName," +
                " a.ORG_ID orgId," +
                " '' orgName," +
                " '' patientName," +
                " '' patientGender," +
                "(SELECT OLD_VALUE FROM app_marking_log_item WHERE 1=1 AND TYPE ='201' AND LOG_ID = a.ID ) oldHbpValue," +
                "(SELECT NEW_VALUE FROM app_marking_log_item WHERE 1=1 AND TYPE ='201' AND LOG_ID = a.ID ) newHbpValue," +
                "(SELECT OLD_VALUE FROM app_marking_log_item WHERE 1=1 AND TYPE ='202' AND LOG_ID = a.ID ) oldDmValue," +
                "(SELECT NEW_VALUE FROM app_marking_log_item WHERE 1=1 AND TYPE ='202' AND LOG_ID = a.ID ) newDmValue,"+
                " HS_CREATE_DATE upTime " +
                "FROM APP_MARKING_LOG a WHERE 1=1 ";

        if(StringUtils.isNotBlank(qvo.getPatientId())){
            map.put("userId",qvo.getPatientId());
            sql += " AND a.BUSINESS_SIGN_ID =:userId ";
        }
        sql += " GROUP BY HS_CREATE_DATE DESC ";
        List<AppModifyLogEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppModifyLogEntity.class,qvo);
        return list;
    }

    @Override
    public List<AppModifyPeopleListEntity> findModifyPeopleList(AppSmPeopleBasicVo qvo) throws Exception {
        Map<String,Object> map = new HashMap<>();
        String sql = "SELECT\n" +
                "\tb.SIGN_PATIENT_ID patientId,\n" +
                "\tb.SIGN_HOSP_ID orgId,\n" +
                "\tb.SIGN_DR_ID drId,\n" +
                "\tb.SIGN_TEAM_ID teamId,\n" +
                "\t'' teamName," +
                "\t'' orgName,\n" +
                "\t'' drName,\n " +
                "\tc.PATIENT_NAME patientName,\n" +
                "\tc.PATIENT_AGE patientAge,\n" +
                "\tc.PATIENT_GENDER patientGender\n" +
                "FROM\n" +
                "\tapp_marking_log a\n" +
                "INNER JOIN app_sign_form b ON a.BUSINESS_SIGN_ID = b.SIGN_PATIENT_ID " +
                "INNER JOIN APP_PATIENT_USER c ON b.SIGN_PATIENT_ID = c.ID \n" +
                "WHERE 1=1 ";
        if(StringUtils.isNotBlank(qvo.getOrgId())){
            map.put("orgId",qvo.getOrgId());
            sql += " AND b.SIGN_HOSP_ID =:orgId ";
        }
        if(StringUtils.isNotBlank(qvo.getTeamId())){
            map.put("teamId",qvo.getTeamId());
            sql += " AND b.SIGN_TEAM_ID =:teamId ";
        }
        if(StringUtils.isNotBlank(qvo.getDrId())){
            map.put("drId",qvo.getDrId());
            sql += " AND b.SIGN_DR_ID =:drId ";
        }
        if(StringUtils.isNotBlank(qvo.getSex())){
            map.put("sex",qvo.getSex());
            sql += " AND c.PATIENT_GENDER =:sex ";
        }

        if(StringUtils.isNotBlank(qvo.getAge())){
            map.put("age",qvo.getAge());
            sql += " AND c.PATIENT_AGE =:age ";
        }

        if(StringUtils.isNotBlank(qvo.getAgeOne())){
            map.put("ageOne",qvo.getAgeOne());
            sql += " AND c.PATIENT_AGE >= :ageOne ";
        }
        if(StringUtils.isNotBlank(qvo.getAgeTwo())){
            map.put("ageTwo",qvo.getAgeTwo());
            sql += " AND c.PATIENT_AGE <= :ageTwo ";
        }

        if(StringUtils.isNotBlank(qvo.getPatientName())){
            map.put("name","%"+qvo.getPatientName()+"%");
            sql += " AND c.PATIENT_NAME LIKE :name ";
        }
        if(StringUtils.isNotBlank(qvo.getStartTime())){
            map.put("startTime",qvo.getStartTime());
            sql += " AND a.HS_CREATE_DATE>=:startTime ";
        }
        if(StringUtils.isNotBlank(qvo.getEndTime())){
            map.put("endTime",qvo.getEndTime());
            sql += " AND a.HS_CREATE_DATE <=:endTime ";
        }

        sql += " GROUP BY b.SIGN_PATIENT_ID ";
        List<AppModifyPeopleListEntity> list = sysDao.getServiceDo().findSqlMapRVo(sql,map,AppModifyPeopleListEntity.class,qvo);
        return list;
    }
}
