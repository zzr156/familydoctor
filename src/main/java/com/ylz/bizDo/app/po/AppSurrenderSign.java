package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**
 * 解约请求参数记录表
 * Created by zzl on 2018/5/21.
 */
@Entity
@Table(name = "APP_SURRENDER_SIGN")
public class AppSurrenderSign extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;
    @Column(name = "PATIENT_IDNO",length = 18)
    private String patientIdNo;//居民身份证号
    @Column(name = "PATIENT_CAUSE_DEATH",length = 200)
    private String patientCauseDeath;//死亡原因
    @Column(name = "PATIENT_TIME_DEATH")
    private Calendar patientTimeDeath;//死亡时间
    @Column(name = "PATIENT_STATE",length = 10)
    private String patientState="0";//数据状态（0未解约 1已解约）
    @Column(name = "DR_ID",length = 36)
    private String drId;//医生主键
    @Column(name = "ORG_ID",length = 36)
    private String orgId;//机构主键

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientIdNo() {
        return patientIdNo;
    }

    public void setPatientIdNo(String patientIdNo) {
        this.patientIdNo = patientIdNo;
    }

    public String getPatientCauseDeath() {
        return patientCauseDeath;
    }

    public void setPatientCauseDeath(String patientCauseDeath) {
        this.patientCauseDeath = patientCauseDeath;
    }

    public Calendar getPatientTimeDeath() {
        return patientTimeDeath;
    }

    public void setPatientTimeDeath(Calendar patientTimeDeath) {
        this.patientTimeDeath = patientTimeDeath;
    }

    public String getPatientState() {
        return patientState;
    }

    public void setPatientState(String patientState) {
        this.patientState = patientState;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
}
