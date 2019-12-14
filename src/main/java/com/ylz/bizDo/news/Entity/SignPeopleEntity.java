package com.ylz.bizDo.news.Entity;

import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packaccede.util.CardUtil;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.util.AgeUtil;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zzl on 2017/9/14.
 */
public class SignPeopleEntity {
    private String id;//患者id
    private String patientName;//患者姓名
    private String sex;//性别
    private String age;//年龄
    private String signOrg;//签约机构
    private String signDr;//签约医生
    private String signTime;//签约时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        if(StringUtils.isNotBlank(this.getId())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            AppPatientUser user = (AppPatientUser) dao.getServiceDo().find(AppPatientUser.class,this.getId());
            if(user!=null){
                sex = user.getPatientGender();
            }
        }
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) throws Exception {
        if(StringUtils.isNotBlank(this.getId())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            AppPatientUser user = (AppPatientUser) dao.getServiceDo().find(AppPatientUser.class,this.getId());
            if(user!=null){
                Map<String,Object> idNos = new HashMap<String,Object>();
                if(StringUtils.isNotBlank(user.getPatientIdno())){
                    if(user.getPatientIdno().length() == 18){
                        idNos = CardUtil.getCarInfo(user.getPatientIdno());
                    }else{
                        idNos = CardUtil.getCarInfo15W(user.getPatientIdno());
                    }
                    String birthday = idNos.get("birthday").toString();
                    age = AgeUtil.getAgeYear(ExtendDate.getCalendar(birthday));
                }
            }
        }
        this.age = age;
    }

    public String getSignOrg() {
        return signOrg;
    }

    public void setSignOrg(String signOrg) {
        if(StringUtils.isNotBlank(signOrg)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            AppHospDept dept = (AppHospDept)dao.getServiceDo().find(AppHospDept.class,signOrg);
            if(dept!=null){
                signOrg = dept.getHospName();
            }
        }
        this.signOrg = signOrg;
    }

    public String getSignDr() {
        return signDr;
    }

    public void setSignDr(String signDr) {
        if(StringUtils.isNotBlank(signDr)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            AppDrUser drUser = (AppDrUser)dao.getServiceDo().find(AppDrUser.class,signDr);
            if(drUser!=null){
                signDr = drUser.getDrName();
            }
        }
        this.signDr = signDr;
    }

    public String getSignTime() {
        return signTime;
    }

    public void setSignTime(String signTime) {
        this.signTime = signTime;
    }
}
