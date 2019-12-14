package com.ylz.bizDo.jtapp.patientEntity;

import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.app.po.AppTeamMember;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packaccede.util.CardUtil;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.util.AgeUtil;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.util.Map;

/**
 * Created by zzl on 2017/6/17.
 */
public class AppDrugGuideBatchEntity {
    private String doctorId;//指导医生
    private String guideTime;//指导日期
    private String drBatchNum;//批次号
    private String teamId;//团队主键
    private String hospId;//医院主键
    private String patientId;//患者主键

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getGuideTime() {
        return guideTime;
    }

    public void setGuideTime(Timestamp guideTime) {
        if(guideTime != null) {
            this.guideTime = ExtendDate.getYMD(guideTime);
        }
    }

    public String getDrBatchNum() {
        return drBatchNum;
    }

    public void setDrBatchNum(String drBatchNum) {
        this.drBatchNum = drBatchNum;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getHospId() {
        return hospId;
    }

    public void setHospId(String hospId) {
        this.hospId = hospId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getHospName(){
        if(StringUtils.isNotBlank(this.getHospId())){
            SysDao sysDao = (SysDao)SpringHelper.getBean("sysDao");
            AppHospDept dept = (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,this.getHospId());
            if(dept != null) {
                return dept.getHospName();
            }
        }
        return "";
    }

    public String getDoctorName(){
        if(StringUtils.isNotBlank(this.getDoctorId())){
            SysDao sysDao = (SysDao)SpringHelper.getBean("sysDao");
            AppDrUser dept = (AppDrUser) sysDao.getServiceDo().find(AppDrUser.class,this.getDoctorId());
            if(dept != null) {
                return dept.getDrName();
            }
        }
        return "";
    }

    public String getDoctorType() throws Exception {
        if(StringUtils.isNotBlank(this.getDoctorId()) && StringUtils.isNotBlank(this.getTeamId())){
            SysDao sysDao = (SysDao)SpringHelper.getBean("sysDao");
            AppTeamMember member =sysDao.getAppTeamMemberDao().findMemByDrId(this.getDoctorId(),this.getTeamId());
            if(member != null) {
                return member.getMemWorkTypeName();
            }
        }
        return "";
    }

    public String getPatientName(){
        if(StringUtils.isNotBlank(this.getPatientId())){
            SysDao sysDao = (SysDao)SpringHelper.getBean("sysDao");
            AppPatientUser user = (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class,this.getPatientId());
            if(user != null) {
                return user.getPatientName();
            }
        }
        return "";
    }


    public String getPatientGender() throws Exception{
        if(StringUtils.isNotBlank(this.getPatientId())){
            SysDao sysDao = (SysDao)SpringHelper.getBean("sysDao");
            AppPatientUser user = (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class,this.getPatientId());
            if(user != null) {
                return user.getStrPatientGender();
            }
        }
        return "";
    }


    public String getPatientAge(){
        if(StringUtils.isNotBlank(this.getPatientId())){
            try{
                SysDao sysDao = (SysDao)SpringHelper.getBean("sysDao");
                AppPatientUser user = (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class,this.getPatientId());
                if(user != null){
                    Map<String,Object> idNos;
                    if(user.getPatientIdno().length() == 18){
                        idNos = CardUtil.getCarInfo(user.getPatientIdno());
                    }else{
                        idNos = CardUtil.getCarInfo15W(user.getPatientIdno());
                    }
                    String birthday = idNos.get("birthday").toString();
                    String age = AgeUtil.getAgeYear(ExtendDate.getCalendar(birthday));
                    return age;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return "";
    }
}
