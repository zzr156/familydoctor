package com.ylz.bizDo.plan.Entity;

import com.ylz.packcommon.common.util.ExtendDate;

import java.sql.Time;
import java.sql.Timestamp;

/**
 * Created by zzl on 2017/11/28.
 */
public class AppGeneralDiseaseHisEntity {
    private String id;
    private String gdhtGenId;//通用随访外键
    private String gdhtNameOrReason;//名称或原因
    private String gdhtTime;//时间
    private String gdhtType;//类型（1手术 2外伤 3输血）

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGdhtGenId() {
        return gdhtGenId;
    }

    public void setGdhtGenId(String gdhtGenId) {
        this.gdhtGenId = gdhtGenId;
    }

    public String getGdhtNameOrReason() {
        return gdhtNameOrReason;
    }

    public void setGdhtNameOrReason(String gdhtNameOrReason) {
        this.gdhtNameOrReason = gdhtNameOrReason;
    }

    public String getGdhtTime() {
        return gdhtTime;
    }

    public void setGdhtTime(Timestamp gdhtTime) {
        if(gdhtTime != null){
            this.gdhtTime = ExtendDate.getYMD(gdhtTime);
        }else{
            this.gdhtTime = "";
        }
    }

    public String getGdhtType() {
        return gdhtType;
    }

    public void setGdhtType(String gdhtType) {
        this.gdhtType = gdhtType;
    }
}
