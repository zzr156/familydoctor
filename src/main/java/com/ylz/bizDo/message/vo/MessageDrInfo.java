package com.ylz.bizDo.message.vo;

import com.ylz.packcommon.common.util.SericuryUtil;
import org.apache.commons.lang.StringUtils;

/**
 * Created by hzk on 2017/7/4.
 * 消息用户列表
 */
public class MessageDrInfo {
    private String id;//医生id
    private String drName;//医生姓名
    private String workType;//工作类型
    private String drGood;//简介
    private String drImageurl;//头像
    private String drGender;//性别

    public String getDrGender() {
        return drGender;
    }

    public void setDrGender(String drGender) {
        this.drGender = drGender;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        if(StringUtils.isNotBlank(id)){
            try {
                SericuryUtil p = new SericuryUtil();
                this.id = p.encrypt(id);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }

    public String getDrGood() {
        return drGood;
    }

    public void setDrGood(String drGood) {
        this.drGood = drGood;
    }

    public String getDrImageurl() {
        return drImageurl;
    }

    public void setDrImageurl(String drImageurl) {
        this.drImageurl = drImageurl;
    }
}
