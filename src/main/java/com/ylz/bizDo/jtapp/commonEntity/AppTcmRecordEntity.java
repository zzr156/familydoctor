package com.ylz.bizDo.jtapp.commonEntity;

import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2018/8/11.
 */
public class AppTcmRecordEntity {
    private String jlId;//记录id
    private String patientId;//居民id
    private String df_id;//档案号
    private String doctor;//医生id
    private String edate;//中医体质辨识日期
    private String yyid00;//机构id
    private String dtjg;//答题结果
    private List<AppTcmDtEntity> tzjl;//辨识记录

    public String getDtjg() {
        return dtjg;
    }

    public void setDtjg(String dtjg) {
        this.dtjg = dtjg;
    }

    public String getJlId() {
        return jlId;
    }

    public void setJlId(String jlId) {
        this.jlId = jlId;
    }

    public String getDf_id() {
        return df_id;
    }

    public void setDf_id(String df_id) {
        if(StringUtils.isNotBlank(this.getPatientId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            AppPatientUser user = (AppPatientUser)dao.getServiceDo().find(AppPatientUser.class,this.getPatientId());
            if(user != null){
                df_id = user.getPatientjmda();
            }
        }
        this.df_id = df_id;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getEdate() {
        return edate;
    }

    public void setEdate(Timestamp edate) {
        String time = "";
        if(edate != null){
            time = ExtendDate.getYMD(edate);
            if(StringUtils.isNotBlank(time)){
                time = time.replaceAll("-","");
            }
        }
        this.edate = time;
    }

    public String getYyid00() {
        return yyid00;
    }

    public void setYyid00(String yyid00) {
        this.yyid00 = yyid00;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public List<AppTcmDtEntity> getTzjl() {
        return tzjl;
    }

    public void setTzjl(String tzjl) {
        List<AppTcmDtEntity> tzList = null;
        if(StringUtils.isNotBlank(this.getJlId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            Map<String,Object> map = new HashMap<>();
            map.put("jlId",this.getJlId());
            String sql = "SELECT\n" +
                    "\ta.ID id,\n" +
                    "\ta.TCMR_ID tcmrId,\n" +
                    "\ta.TCMR_USER_ID tcmrUserId,\n" +
                    "\ta.TCMR_USER_NAME tcmrUserName,\n" +
                    "\ta.TCMR_DR_ID tcmrDrId,\n" +
                    "\ta.TCMR_DR_NAME tcmrDrName,\n" +
                    "\ta.TCMR_RESULT_TYPE tcmrResultType,\n" +
                    "\ta.TCMR_RESULT_VALUE tcmrResultValue,\n" +
                    "\ta.TCMR_SCORE tcmrScore,\n" +
                    "\t(SELECT GUIDE_CONTENT FROM APP_GUIDE_TEMPLATE WHERE GUIDE_DISEASE_TYPE =a.TCMR_RESULT_TYPE AND GUIDE_MEDDLE_TYPE = a.TCMR_MODERN_CULTIVATE) tcmrModernCultivate,\n" +
                    "\t(SELECT GUIDE_CONTENT FROM APP_GUIDE_TEMPLATE WHERE GUIDE_DISEASE_TYPE =a.TCMR_RESULT_TYPE AND GUIDE_MEDDLE_TYPE = a.TCMR_FOOD_NURSING) tcmrFoodNursing,\n" +
                    "\t(SELECT GUIDE_CONTENT FROM APP_GUIDE_TEMPLATE WHERE GUIDE_DISEASE_TYPE =a.TCMR_RESULT_TYPE AND GUIDE_MEDDLE_TYPE = a.TCMR_DAILY_LIFE_CULTIVATE) tcmrDailyLifeCultivate,\n" +
                    "\t(SELECT GUIDE_CONTENT FROM APP_GUIDE_TEMPLATE WHERE GUIDE_DISEASE_TYPE =a.TCMR_RESULT_TYPE AND GUIDE_MEDDLE_TYPE = a.TCMR_SPORTS_HEALTH_CARE) tcmrSportsHealthCare,\n" +
                    "\t(SELECT GUIDE_CONTENT FROM APP_GUIDE_TEMPLATE WHERE GUIDE_DISEASE_TYPE =a.TCMR_RESULT_TYPE AND GUIDE_MEDDLE_TYPE = a.TCMR_MERIDIAN_HEALTH) tcmrMeridianHealth,\n" +
                    "\ta.TCMR_OTHER tcmrOther\n" +
                    "FROM\n" +
                    "\tapp_tcm_result a WHERE a.TCMR_ID=:jlId AND a.TCMR_FS_STATE='1'";
            tzList = dao.getServiceDo().findSqlMapRVo(sql,map,AppTcmDtEntity.class);
        }
        this.tzjl = tzList;
    }
}
