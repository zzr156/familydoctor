package com.ylz.bizDo.plan.Entity;

import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;

/**
 * Created by lintingjie on 2017/8/7.
 */
public class AppFollowPlanXYEntity {

    private String id;
    private String drId;//医生id
    private String patientId;
    private String patientName;
    private String followType;//随访类型
    private String followTime;//随访时间
    private String sfX;//随访横坐标
    private String sfY;//随访纵坐标

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getFollowType() {
        return followType;
    }

    public void setFollowType(String followType) throws Exception {
        if(StringUtils.isNotBlank(followType)){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_FOLLOWWAY, followType);
            if(value!=null){
                this.followType = value.getCodeTitle();
            }
        }
    }

    public String getFollowTime() {
        return followTime;
    }

    public void setFollowTime(String followTime) {
        this.followTime = followTime;
    }

    public String getSfX() {
        return sfX;
    }

    public void setSfX(String sfX) {
        this.sfX = sfX;
    }

    public String getSfY() {
        return sfY;
    }

    public void setSfY(String sfY) {
        this.sfY = sfY;
    }
}
