package com.ylz.bizDo.jtapp.drEntity;

import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;

/**
 * Created by asus on 2017/7/6.
 */
public class AppManageCardEntity {
    private String id;//主键
    private String homeMangeLevel;//预警等级
    private String homeManageStyle;//预警方式
    private String homeManageCycle;//预警周期
    private String homeManageFrequency;//频次
    private String homeMangeLevelName;//预警等级
    private String homeManageStyleName;//预警方式
    private String homeManageCycleName;//预警周期
    private String homeManageFrequencyName;//频次
    private String homeManagePeminderDays;//预警天数

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHomeMangeLevel() {
        return homeMangeLevel;
    }

    public void setHomeMangeLevel(String homeMangeLevel) {
       this.homeMangeLevel = homeMangeLevel;
    }

    public String getHomeManageStyle() {
        return homeManageStyle;
    }

    public void setHomeManageStyle(String homeManageStyle) {
        this.homeManageStyle = homeManageStyle;
    }

    public String getHomeManageCycle() {
        return homeManageCycle;
    }

    public void setHomeManageCycle(String homeManageCycle) {
       this.homeManageCycle = homeManageCycle;
    }

    public String getHomeManagePeminderDays() {
        return homeManagePeminderDays;
    }

    public void setHomeManagePeminderDays(String homeManagePeminderDays) {
        this.homeManagePeminderDays = homeManagePeminderDays;
    }

    public String getHomeManageFrequency() {
        return homeManageFrequency;
    }

    public void setHomeManageFrequency(String homeManageFrequency) {
       this.homeManageFrequency = homeManageFrequency;
    }


    public String getHomeMangeLevelName() {
        return homeMangeLevelName;
    }

    public void setHomeMangeLevelName(String homeMangeLevelName) throws Exception {
        SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
        CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_COLOR,homeMangeLevelName);
        if(value!=null) {
            this.homeMangeLevelName = value.getCodeTitle();
        }
    }

    public String getHomeManageStyleName() {
        return homeManageStyleName;
    }

    public void setHomeManageStyleName(String homeManageStyleName) throws Exception {
        SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
        CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_HOMECAREMANGE,homeManageStyleName);
        if(value!=null) {
            this.homeManageStyleName = value.getCodeTitle();
        }
    }

    public String getHomeManageCycleName() {
        return homeManageCycleName;
    }

    public void setHomeManageCycleName(String homeManageCycleName) throws Exception {
        SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
        CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_HOMECARECYCLE,homeManageCycleName);
        if(value!=null) {
            this.homeManageCycleName = value.getCodeTitle();
        }
    }

    public String getHomeManageFrequencyName() {
        return homeManageFrequencyName;
    }

    public void setHomeManageFrequencyName(String homeManageFrequencyName) throws Exception {
        SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
        CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_HOMECAREFREQUENCY,homeManageFrequencyName);
        if(value!=null) {
            this.homeManageFrequencyName = value.getCodeTitle();
        }
    }
}
