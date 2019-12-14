package com.ylz.bizDo.plan.Entity;

import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppLabelGroup;
import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.app.po.AppSignForm;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.plan.po.AppFollowPlan;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packaccede.util.CardUtil;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.comEnum.FollowPlanState;
import com.ylz.packcommon.common.util.AgeUtil;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;

import java.sql.*;
import java.util.*;

/**
 * 随访人员基本信息
 */
public class AppFollowPatienBasic implements java.io.Serializable {
    private String patientId;//患者id
    private String sfFollowNum;//随访编号
    private String patientName;//随访居民 患者姓名
    private String patientGender;//性别
    private String patientBirthday;//出生日期
    private String patientTel;//电话
    private String patientAddress;//家庭地址
    private String patientIdno;//身份证号
    private String patientAge;//年龄
    private String sfFollowMode;//随访方式
    private String id;//随访id
    private String sfFollowDate;//随访时间
    private String drId;//医生主键

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    //服务人群类型与随访次数
    private List<PersGroupLsit> persGroup;
    //随访记录
    public List<PersGroupLsit> getPersGroup() {
        return persGroup;
    }
    public void setPersGroup(String persGroup2) throws Exception {
        persGroup= new ArrayList<>();
        if(StringUtils.isNotBlank(this.getPatientId())) {
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            AppSignForm form = dao.getAppSignformDao().findSignFormByUserState(this.getPatientId());
//            List<AppSignForm> ls=dao.getServiceDo().loadByPk(AppSignForm.class,"signPatientId",this.getPatientId());
            if(form!=null) {
                Map<String,Object> map=new HashedMap();
                String sql="SELECT * from APP_LABEL_GROUP a where a.LABEL_TYPE=3 and a.LABEL_SIGN_ID=:LABEL_SIGN_ID and a.LABEL_VALUE in ('5','6','2','3') ";
                map.put("LABEL_SIGN_ID",form.getId());
                List<AppLabelGroup> labs=dao.getServiceDo().findSqlMap(sql,map,AppLabelGroup.class);
                if(labs!=null && !labs.isEmpty()){
                    for(AppLabelGroup la:labs) {
                        PersGroupLsit p = new PersGroupLsit();
                        Map<String, Object> map2 = new HashedMap();
                        String psql = "SELECT COUNT(1) from APP_FOLLOW_PLAN a where a.SF_FOLLOW_STATE=1 and a.SF_FOLLOW_PATIENTID=:SF_FOLLOW_PATIENTID and  a.SF_FOLLOW_TYPE=:SF_FOLLOW_TYPE";
                        map2.put("SF_FOLLOW_PATIENTID", this.getPatientId());
                        map2.put("SF_FOLLOW_TYPE", la.getLabelValue());
                        int coun=dao.getServiceDo().findCount(psql,map2);
                        map2.put("SF_FOLLOW_NUM",this.getSfFollowNum());
                        map2.put("SF_FOLLOW_DATE",this.getSfFollowDate());
                        String sqlnum = "SELECT a.* FROM APP_FOLLOW_PLAN a WHERE a.SF_FOLLOW_NUM=:SF_FOLLOW_NUM AND a.SF_FOLLOW_TYPE=:SF_FOLLOW_TYPE AND a.SF_FOLLOW_DATE=:SF_FOLLOW_DATE";
                        List<AppFollowPlan> listPlan = dao.getServiceDo().findSqlMap(sqlnum,map2,AppFollowPlan.class);
//                        p.setCount(String.valueOf(coun+1));
                        if(listPlan!=null && listPlan.size()>0){
                            p.setVisitId(listPlan.get(0).getId());
                            if(listPlan.get(0).getSfFollowState().equals(FollowPlanState.YIWANCHENG.getValue())){
                                p.setCount(String.valueOf(coun));
                            }else{
                                p.setCount(String.valueOf(coun+1));
                            }
                            p.setWcState(listPlan.get(0).getSfFollowState());
                        }
                        p.setLabelTitle(la.getLabelTitle());
                        p.setLabelValue(la.getLabelValue());

                        if("2".equals(la.getLabelValue())){//儿童随访判断是否超一个月龄
                            String birthDay = this.getPatientBirthday();
                            if(StringUtils.isNotBlank(birthDay)){
                                Calendar cal = ExtendDate.getCalendar(birthDay);
                                cal.add(Calendar.MONTH,1);
                                Calendar now = Calendar.getInstance();
                                if(ExtendDate.compare(cal,now)==2){//比较两个时间 一月龄<当前系统时间说明超过一个月龄
                                    p.setState("1");
                                }
                            }
                        }
                        persGroup.add(p);
                    }
                }
            }
        }
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getSfFollowNum() {
        return sfFollowNum;
    }

    public void setSfFollowNum(String sfFollowNum) {
        this.sfFollowNum = sfFollowNum;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(String patientGender) {
        this.patientGender = patientGender;
    }

    public String getPatientBirthday() {
        return patientBirthday;
    }

    public void setPatientBirthday(String patientBirthday) {
        this.patientBirthday = patientBirthday;
    }

    public String getPatientTel() {
        return patientTel;
    }

    public void setPatientTel(String patientTel) {
        this.patientTel = patientTel;
    }

    public String getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(String patientAddress) {
        String address = "";
        if(StringUtils.isNotBlank(this.getPatientId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            AppPatientUser user = (AppPatientUser)dao.getServiceDo().find(AppPatientUser.class,this.getPatientId());
            if(user != null){
                if(StringUtils.isNotBlank(user.getPatientProvince())){
                    CdAddress provience = (CdAddress) dao.getServiceDo().find(CdAddress.class, user.getPatientProvince());
                    if(provience != null) {
                        address += provience.getAreaSname();
                    }
                }
                if(StringUtils.isNotBlank(user.getPatientCity())){
                    CdAddress city = (CdAddress) dao.getServiceDo().find(CdAddress.class, user.getPatientCity());
                    if(city != null) {
                        address += city.getAreaSname();
                    }
                }
                if(StringUtils.isNotBlank(user.getPatientArea())){
                    CdAddress area = (CdAddress) dao.getServiceDo().find(CdAddress.class, user.getPatientArea());
                    if(area != null) {
                        address += area.getAreaSname();
                    }
                }
                if(StringUtils.isNotBlank(user.getPatientStreet())){
                    CdAddress street = (CdAddress) dao.getServiceDo().find(CdAddress.class, user.getPatientStreet());
                    if(street != null) {
                        address += street.getAreaSname();
                    }
                }
                if(StringUtils.isNotBlank(user.getPatientNeighborhoodCommittee())){
                    CdAddress committee = (CdAddress) dao.getServiceDo().find(CdAddress.class, user.getPatientNeighborhoodCommittee());
                    if(committee != null) {
                        address += committee.getAreaSname();
                    }
                }
                if(StringUtils.isNotBlank(user.getPatientAddress())){
                    address += user.getPatientAddress();
                }
            }
        }

        this.patientAddress = address;
    }

    public String getPatientIdno() {
        return patientIdno;
    }

    public void setPatientIdno(String patientIdno) {
        this.patientIdno = patientIdno;
    }

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(String patientAge) throws Exception{
        if(StringUtils.isNotBlank(this.getPatientId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            AppPatientUser user = (AppPatientUser)dao.getServiceDo().find(AppPatientUser.class,this.getPatientId());
            if(user!=null){
                Map<String,Object> idNos = new HashMap<String,Object>();
                if(StringUtils.isNotBlank(user.getPatientIdno())){
                    if(user.getPatientIdno().length() == 18){
                        idNos = CardUtil.getCarInfo(user.getPatientIdno());
                    }else{
                        idNos = CardUtil.getCarInfo15W(user.getPatientIdno());
                    }
                    String birthday = idNos.get("birthday").toString();
                    String age = AgeUtil.getAgeYear(ExtendDate.getCalendar(birthday));
                    patientAge = age;
                }

            }
        }
        this.patientAge = patientAge;
    }

    public String getSfFollowMode() {
        return sfFollowMode;
    }

    public void setSfFollowMode(String sfFollowMode) {
        this.sfFollowMode = sfFollowMode;
    }

    public String getSfFollowDate() {
        return sfFollowDate;
    }

    public void setSfFollowDate(java.sql.Date sfFollowDate) {
        if(sfFollowDate!=null){
            this.sfFollowDate = ExtendDate.getYMD(sfFollowDate);
        }else{
            this.sfFollowDate = "";
        }
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getDrName(){
        if(StringUtils.isNotBlank(this.getDrId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            AppDrUser drUser = (AppDrUser) dao.getServiceDo().find(AppDrUser.class,this.getDrId());
            if(drUser!=null){
                return drUser.getDrName();
            }
        }
        return "";
    }
}
