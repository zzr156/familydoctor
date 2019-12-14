package com.ylz.bizDo.sys.vo;


import com.ylz.packcommon.common.CommConditionVo;

/**
 * Created by jokeThinkPad on 2017/8/1.
 */
public class LogQvo extends CommConditionVo {

    private String id;
    private String startTime;   //开始时间
    private String endTime;     //结束时间
    private String businessTable; //表名
    private String userName;      //修改人
    private String businessName;//业务名

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

    public String getBusinessTable() {
        return businessTable;
    }

    public void setBusinessTable(String businessTable) {
        this.businessTable = businessTable;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }
}
