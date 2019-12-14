package com.ylz.bizDo.jtapp.drVo;

import com.ylz.packcommon.common.CommConditionVo;

/** 健康请求参数
 * Created by zzl on 2017/7/1.
 */
public class AppDrQvo  extends CommConditionVo {
    private String id;//数据Id
    private String userId;//用户id
    private String disType;//疾病类型
    private String medType;//干预类型
    private String content;//查询教育条件
    private String healthType;//健康教育分类
    private String enshrineId;//收藏用户
    private String xtmb;//系统模板
    private String hospId;//机构id
    private String startTime;//开始时间 格式（2017-01-01 00:00:00）
    private String endTime;//结束时间

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDisType() {
        return disType;
    }

    public void setDisType(String disType) {
        this.disType = disType;
    }

    public String getMedType() {
        return medType;
    }

    public void setMedType(String medType) {
        this.medType = medType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHealthType() {
        return healthType;
    }

    public void setHealthType(String healthType) {
        this.healthType = healthType;
    }

    public String getEnshrineId() {
        return enshrineId;
    }

    public void setEnshrineId(String enshrineId) {
        this.enshrineId = enshrineId;
    }

    public String getXtmb() {
        return xtmb;
    }

    public void setXtmb(String xtmb) {
        this.xtmb = xtmb;
    }

    public String getHospId() {
        return hospId;
    }

    public void setHospId(String hospId) {
        this.hospId = hospId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
