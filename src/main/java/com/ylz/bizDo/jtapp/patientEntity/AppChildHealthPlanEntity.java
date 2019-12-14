package com.ylz.bizDo.jtapp.patientEntity;

import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;

/**
 * Created by zzl on 2017/6/22.
 */
public class AppChildHealthPlanEntity  {
    private String id;
    private String birthDay;//出生日期
    private String planDate;//体检日期
    private String title;//体检标题
    private String state;//状态
    private String userId;//用户id
    private String userName;//用户姓名

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Timestamp birthDay) {
        this.birthDay = ExtendDate.getYMD(birthDay);
    }

    public String getPlanDate() {
        return planDate;
    }

    public void setPlanDate(Timestamp planDate) {
        if(planDate != null) {
            this.planDate = ExtendDate.getYMD(planDate);
        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        if(StringUtils.isBlank(userName)){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            AppPatientUser patientUser = (AppPatientUser) dao.getServiceDo().find(AppPatientUser.class,this.getUserId());
            if(patientUser!=null){
                userName = patientUser.getPatientName();
            }
        }
        this.userName = userName;
    }
}
