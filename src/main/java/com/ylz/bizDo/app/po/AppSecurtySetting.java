package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 安全设置
 * Created by asus on 2017/08/11.
 */
@Entity
@Table(name = "APP_SECURTY_SETTING")
public class AppSecurtySetting extends BasePO{
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;
    @Column(name = "SECURTY_DR_PATIENT_ID", length = 36)
    private String securtyDrPatientId;//用户id
    @Column(name = "SECURTY_STATE", length = 6)
    private String securtyState;// 开启状态 0禁用 1启用
    @Column(name = "SECURTY_TYPE", length = 50)
    private String securtyType;//安全类型 1:家人,2医生

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSecurtyDrPatientId() {
        return securtyDrPatientId;
    }

    public void setSecurtyDrPatientId(String securtyDrPatientId) {
        this.securtyDrPatientId = securtyDrPatientId;
    }

    public String getSecurtyState() {
        return securtyState;
    }

    public void setSecurtyState(String securtyState) {
        this.securtyState = securtyState;
    }

    public String getSecurtyType() {
        return securtyType;
    }

    public void setSecurtyType(String securtyType) {
        this.securtyType = securtyType;
    }
}
