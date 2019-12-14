package com.ylz.bizDo.plan.vo;

/**
 * Created by asus on 2017/6/21.
 */
public class AppFollowStateQvo {
    private String followId;//随访主表id
    private String state;//失访或死亡
    private String reason;//失访或死亡原因
    private String date;//失访下次随访日期或死亡日期
    private String xAxis;
    private String yAxis;

    public String getFollowId() {
        return followId;
    }

    public void setFollowId(String followId) {
        this.followId = followId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getxAxis() {
        return xAxis;
    }

    public void setxAxis(String xAxis) {
        this.xAxis = xAxis;
    }

    public String getyAxis() {
        return yAxis;
    }

    public void setyAxis(String yAxis) {
        this.yAxis = yAxis;
    }
}
