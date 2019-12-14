package com.ylz.bizDo.cd.po;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ylz.packaccede.util.JackCalender;
import com.ylz.packcommon.common.bean.BasePO;
import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**
 * 表删除日志
 */
@Entity
@Table(name = "SYS_LOG_DELET")
public class SysLogDelet extends BasePO {

    @GenericGenerator(name = "generator", strategy = "uuid.hex")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "ID", unique = true, nullable = false, length = 40)
    private String id;
    @Column(name = "USER_ID",length = 40)
    private String userId;//用户id
    @Column(name = "USER_NAME",length = 50)
    private String userName;//用户名
    @Column(name = "CLASS_NAME",length = 200)
    private String className;//Class
    @Column(name = "BUSINESS_ID",length = 60)
    private String businessId;//业务主键
    @Column(name = "BUSINESS_JSON")
    private String businessJson;//业务内容
    @Column(name = "CREATE_DATE", nullable = true)
    private Calendar createDate;
    @Column(name = "CLASS_ACT")
    private String classAct;//方法名


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

    public String getBusinessJson() {
        return businessJson;
    }

    public void setBusinessJson(String businessJson) {
        this.businessJson = businessJson;
    }

    @JSON(format="yyyy-MM-dd HH:mm:ss")
    public Calendar getCreateDate() {
        return createDate;
    }

    @JsonDeserialize(using= JackCalender.class)
    public void setCreateDate(Calendar createDate) {
        this.createDate = createDate;
    }

    public String getClassAct() {
        return classAct;
    }

    public void setClassAct(String classAct) {
        this.classAct = classAct;
    }
}
