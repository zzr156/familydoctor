package com.ylz.bizDo.jtapp.commonEntity;

import java.util.List;

/**
 * Created by zzl on 2017/8/7.
 */
public class AppTcmLsEntity {
    private String id;//主键id
    private String createTime;//时间
    private String drId;//医生id
    private String drName;//医生姓名
    private List<AppTcmEntity> resulList;//体质辨识结果
    private String type;//记录类型（1患者自测 2医生检测）

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public List<AppTcmEntity> getResulList() {
        return resulList;
    }

    public void setResulList(List<AppTcmEntity> resulList) {
        this.resulList = resulList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
