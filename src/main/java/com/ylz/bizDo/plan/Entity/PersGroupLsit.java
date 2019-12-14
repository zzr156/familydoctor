package com.ylz.bizDo.plan.Entity;

/**
 * Created by hzk on 2017/7/24.
 */
public class PersGroupLsit {
    private String labelValue;//类型值
    private String labelTitle;//类型名
    private String count;//总次数
    private String state;//儿童是否超过一月龄（0未超过 1超过）
    private String visitId;//随访计划主键
    private String wcState;//完成状态

    public String getLabelValue() {
        return labelValue;
    }

    public void setLabelValue(String labelValue) {
        this.labelValue = labelValue;
    }

    public String getLabelTitle() {
        return labelTitle;
    }

    public void setLabelTitle(String labelTitle) {
        this.labelTitle = labelTitle;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }

    public String getWcState() {
        return wcState;
    }

    public void setWcState(String wcState) {
        this.wcState = wcState;
    }
}
