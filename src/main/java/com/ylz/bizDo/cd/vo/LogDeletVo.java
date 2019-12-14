package com.ylz.bizDo.cd.vo;

import org.apache.struts2.json.annotations.JSON;

import java.util.Calendar;

/**
 * Created by hzk on 2017/8/24.
 * 删除日志实体
 */
public class LogDeletVo {
    private String id;
    private String userId;//用户id
    private String userName;//用户名
    private String className;//Class
    private String businessId;//业务主键
    private Calendar createDate;

    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Calendar getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Calendar createDate) {
        this.createDate = createDate;
    }

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }
}
