package com.ylz.bizDo.app.entity;

import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;

/**
 * Created by asus on 2017/09/05.
 */
public class AppSystemVersionEntity {
    private String systemForce;
    private String systemUpdate;
    private String downLoadUrl;
    private String updateTime;
    private String changeLog;
    private String vsersionCode;
    private String vsersionName;

    public String getSystemForce() {
        return systemForce;
    }

    public void setSystemForce(String systemForce) {
        this.systemForce = systemForce;
    }

    public String getSystemUpdate() {
        return systemUpdate;
    }

    public void setSystemUpdate(String systemUpdate) {
        this.systemUpdate = systemUpdate;
    }

    public String getDownLoadUrl() {
        return downLoadUrl;
    }

    public void setDownLoadUrl(String downLoadUrl) {
        this.downLoadUrl = downLoadUrl;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Timestamp updateTime) {
        if(updateTime != null){
            this.updateTime = ExtendDate.getYMD_h_m(updateTime);
        }
    }

    public String getChangeLog() {
        return changeLog;
    }

    public void setChangeLog(String changeLog) {
        this.changeLog = changeLog;
    }

    public String getVsersionCode() {
        return vsersionCode;
    }

    public void setVsersionCode(String vsersionCode) {
        this.vsersionCode = vsersionCode;
    }

    public String getVsersionName() {
        return vsersionName;
    }

    public void setVsersionName(String vsersionName) {
        this.vsersionName = vsersionName;
    }

    public boolean getIsUpdate(){
        boolean result = false;
        if(StringUtils.isNotBlank(this.getSystemUpdate())){
            if(this.getSystemUpdate().equals("1")){
                result = true;
            }
        }
        return result;
    }

    public boolean getIsForce(){
        boolean result = false;
        if(StringUtils.isNotBlank(this.getSystemForce())){
            if(this.getSystemForce().equals("1")){
                result = true;
            }
        }
        return result;
    }
}
