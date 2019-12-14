package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 请求基卫日志表
 * Created by zzl on 2018/5/16.
 */
@Entity
@Table(name = "APP_BASIC_LOG")
public class AppBasicLog extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name = "REQUEST_DATA")
    private String requestData;//请求参数
    @Column(name = "REQUEST_USER_ID",length = 36)
    private String requestUserId;//请求人主键
    @Column(name = "REQUEST_USER_NAME",length = 50)
    private String requestUserName;//请求人姓名
    @Column(name = "RESULT_DATA")
    private String resultData;//请求结果
    @Column(name = "REQUEST_USER_TYPE",length = 10)
    private String requestUserType;//请求人类型（1患者 2医生）;
    @Column(name = "REQUEST_METHOD",length = 100)
    private String requestMethod;//请求方法名

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRequestData() {
        return requestData;
    }

    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }

    public String getRequestUserId() {
        return requestUserId;
    }

    public void setRequestUserId(String requestUserId) {
        this.requestUserId = requestUserId;
    }

    public String getRequestUserName() {
        return requestUserName;
    }

    public void setRequestUserName(String requestUserName) {
        this.requestUserName = requestUserName;
    }

    public String getResultData() {
        return resultData;
    }

    public void setResultData(String resultData) {
        this.resultData = resultData;
    }

    public String getRequestUserType() {
        return requestUserType;
    }

    public void setRequestUserType(String requestUserType) {
        this.requestUserType = requestUserType;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }
}
