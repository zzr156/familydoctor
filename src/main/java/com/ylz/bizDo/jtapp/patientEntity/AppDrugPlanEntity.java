package com.ylz.bizDo.jtapp.patientEntity;

import com.ylz.bizDo.app.po.AppDrugGuide;
import com.ylz.bizDo.app.po.AppDrugPlanExtend;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lintingjie on 2017/8/4.
 */
public class AppDrugPlanEntity {

    private String id;
    private List<String> drugName;//药品名称
    private List<String> drugId;//用药指导id
    private String time;//提醒时间
    private String state;//状态
    private String monday;
    private String tuesday;
    private String wednesday;
    private String thursday;
    private String friday;
    private String saturday;
    private String sunday;
    private String onlyOne;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getDrugName() {
        return drugName;
    }

    public void setDrugName(String xxx) {
        List<String> drugName = new ArrayList<>();
        if(StringUtils.isNotBlank(this.getId())) {
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            List<AppDrugPlanExtend> extendList = dao.getServiceDo().loadByPk(AppDrugPlanExtend.class, "drugPlanId", this.getId());
            if(extendList!=null && !extendList.isEmpty()){
                for(AppDrugPlanExtend extend:extendList){
                    AppDrugGuide guide = (AppDrugGuide) dao.getServiceDo().find(AppDrugGuide.class,extend.getDrugId());
                    if(guide != null) {
                        drugName.add(guide.getDgDrugName());
                    }
                }
            }
        }
        this.drugName = drugName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMonday() {
        return monday;
    }

    public void setMonday(String monday) {
        this.monday = monday;
    }

    public String getTuesday() {
        return tuesday;
    }

    public void setTuesday(String tuesday) {
        this.tuesday = tuesday;
    }

    public String getWednesday() {
        return wednesday;
    }

    public void setWednesday(String wednesday) {
        this.wednesday = wednesday;
    }

    public String getThursday() {
        return thursday;
    }

    public void setThursday(String thursday) {
        this.thursday = thursday;
    }

    public String getFriday() {
        return friday;
    }

    public void setFriday(String friday) {
        this.friday = friday;
    }

    public String getSaturday() {
        return saturday;
    }

    public void setSaturday(String saturday) {
        this.saturday = saturday;
    }

    public String getSunday() {
        return sunday;
    }

    public void setSunday(String sunday) {
        this.sunday = sunday;
    }

    public String getOnlyOne() {
        return onlyOne;
    }

    public void setOnlyOne(String onlyOne) {
        this.onlyOne = onlyOne;
    }

    public List<String> getDrugId() {
        return drugId;
    }

    public void setDrugId(String yy) {
        List<String> drugId = new ArrayList<>();
        if(StringUtils.isNotBlank(this.getId())) {
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            List<AppDrugPlanExtend> extendList = dao.getServiceDo().loadByPk(AppDrugPlanExtend.class, "drugPlanId", this.getId());
            if(extendList!=null && !extendList.isEmpty()){
                for(AppDrugPlanExtend extend:extendList){
                    drugId.add(extend.getDrugId());
                }
            }
        }
        this.drugId = drugId;
    }
}
