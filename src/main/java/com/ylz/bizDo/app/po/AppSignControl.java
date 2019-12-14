package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 调度创建环信表
 */
@Entity
@Table(name = "APP_SIGN_CONTROL")
public class AppSignControl extends BasePO{

    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;
    @Column(name = "SIGN_ID", length = 36)
    private String signId;//签约主键
    @Column(name = "SIGN_PATIENT_ID", length = 36)
    private String signPatientId;//签约成员主键
    @Column(name = "SIGN_DR_ID", length = 36)
    private String signDrId;//签约医生主键
    @Column(name = "SIGN_TEAM_ID", length = 36)
    private String signTeamId;//签约团队主键
    @Column(name = "SIGN_STATE", length = 36)
    private String signState;//状态

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) {
        this.signId = signId;
    }

    public String getSignPatientId() {
        return signPatientId;
    }

    public void setSignPatientId(String signPatientId) {
        this.signPatientId = signPatientId;
    }

    public String getSignDrId() {
        return signDrId;
    }

    public void setSignDrId(String signDrId) {
        this.signDrId = signDrId;
    }

    public String getSignTeamId() {
        return signTeamId;
    }

    public void setSignTeamId(String signTeamId) {
        this.signTeamId = signTeamId;
    }

    public String getSignState() {
        return signState;
    }

    public void setSignState(String signState) {
        this.signState = signState;
    }
}
