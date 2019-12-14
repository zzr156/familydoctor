package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.common.util.ExtendDate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**
 * 应用信息表
 * Created by zzl on 2019/1/28.
 */
@Entity
@Table(name = "APP_MSG")
public class AppMsg extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name = "APP_NAME",length = 50)
    private String appName;//应用名
    @Column(name = "APP_ID",length = 50)
    private String appId;//应用id
    @Column(name = "APP_KEY",length = 50)
    private String appKey;//应用key
    @Column(name = "APP_MASTER_SECRET",length = 100)
    private String appMasterSecret;//应用主密钥
    @Column(name = "APP_CREATE_TIME")
    private Calendar appCreateTime;//应用创建时间
    @Column(name = "APP_PG_NAME",length = 50)
    private String appPgName;//应用包名
    @Column(name = "APP_STATE",length = 10)
    private String appState="0";//是否验证状态（0未验证 1已验证）默认0
    @Column(name = "APP_AES_KEY",length = 50)
    private String appAesKey;
    @Column(name = "APP_AREA_CODE",length = 4)
    private String appAreaCode;//地市区划

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppMasterSecret() {
        return appMasterSecret;
    }

    public void setAppMasterSecret(String appMasterSecret) {
        this.appMasterSecret = appMasterSecret;
    }

    public Calendar getAppCreateTime() {
        return appCreateTime;
    }

    public void setAppCreateTime(Calendar appCreateTime) {
        this.appCreateTime = appCreateTime;
    }

    public String getAppPgName() {
        return appPgName;
    }

    public void setAppPgName(String appPgName) {
        this.appPgName = appPgName;
    }

    public String getAppState() {
        return appState;
    }

    public void setAppState(String appState) {
        this.appState = appState;
    }

    public String getAppAesKey() {
        return appAesKey;
    }

    public void setAppAesKey(String appAesKey) {
        this.appAesKey = appAesKey;
    }

    public String getStrCreateTime(){
        if(this.getAppCreateTime()!=null){
            return ExtendDate.getYMD_h_m_s(this.getAppCreateTime());
        }else {
            return "";
        }
    }

    public String getAppAreaCode() {
        return appAreaCode;
    }

    public void setAppAreaCode(String appAreaCode) {
        this.appAreaCode = appAreaCode;
    }
}
