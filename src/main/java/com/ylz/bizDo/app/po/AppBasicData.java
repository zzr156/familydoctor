package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by zzl on 2018/4/12.
 */
@Entity
@Table(name = "APP_BASIC_DATA")
public class AppBasicData extends BasePO{
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name = "USER_ID",length = 36)
    private String userId;//请求人主键
    @Column(name = "USER_NAME",length = 50)
    private String userName;//请求人姓名
    @Column(name = "REQUEST_DATA")
    private String requestData;//请求参数
    @Column(name = "DATA")
    private String data;//返回数据
    @Column(name = "PATIENT_ID",length = 36)
    private String patientId;//居民主键
    @Column(name = "METHODS_TYPE",length = 200)
    private String methodsType;//请求方法名
    @Column(name = "STATE",length = 10)
    private String state;//状态

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

    public String getRequestData() {
        return requestData;
    }

    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getMethodsType() {
        return methodsType;
    }

    public void setMethodsType(String methodsType) {
        this.methodsType = methodsType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
