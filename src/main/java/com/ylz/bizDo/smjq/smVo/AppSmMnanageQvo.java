package com.ylz.bizDo.smjq.smVo;

/**
 * 三明尤溪统计请求参数
 * Created by zzl on 2018/8/21.
 */
public class AppSmMnanageQvo {
    private String areaCode;//区域编码
    private String startTime;//开始时间
    private String endTime;//接收数据
    private String hospId;//机构id

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
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

    public String getHospId() {
        return hospId;
    }

    public void setHospId(String hospId) {
        this.hospId = hospId;
    }
}
