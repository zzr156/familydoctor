package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**
 * 求助表
 */
@Entity
@Table(name = "APP_SEEK_HELP")
public class AppSeekHelp extends BasePO{

    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;
    @Column(name = "SEEK_DEV_ID", length = 50)
    private String seekDevId;//设备号
    @Column(name = "SEEK_PATIENT_ID", length = 36)
    private String seekPatientId;//患者主键
    @Column(name = "SEEK_HELP_DATE", length = 10)
    private Calendar seekHelpDate;//求助时间
    @Column(name = "SEEK_DR_ID", length = 10)
    private String seekDrId;//医生主键
    @Column(name = "SEEK_TEAM_ID", length = 36)
    private String seekTeamId;//团队主键
    @Column(name = "SEEK_HOSP_ID", length = 36)
    private String seekHospId;//医院主键
    @Column(name = "SEEK_AREA_CODE", length = 50)
    private String seekAreaCode;//区划

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSeekDevId() {
        return seekDevId;
    }

    public void setSeekDevId(String seekDevId) {
        this.seekDevId = seekDevId;
    }

    public String getSeekPatientId() {
        return seekPatientId;
    }

    public void setSeekPatientId(String seekPatientId) {
        this.seekPatientId = seekPatientId;
    }

    public Calendar getSeekHelpDate() {
        return seekHelpDate;
    }

    public void setSeekHelpDate(Calendar seekHelpDate) {
        this.seekHelpDate = seekHelpDate;
    }

    public String getSeekDrId() {
        return seekDrId;
    }

    public void setSeekDrId(String seekDrId) {
        this.seekDrId = seekDrId;
    }

    public String getSeekTeamId() {
        return seekTeamId;
    }

    public void setSeekTeamId(String seekTeamId) {
        this.seekTeamId = seekTeamId;
    }

    public String getSeekHospId() {
        return seekHospId;
    }

    public void setSeekHospId(String seekHospId) {
        this.seekHospId = seekHospId;
    }

    public String getSeekAreaCode() {
        return seekAreaCode;
    }

    public void setSeekAreaCode(String seekAreaCode) {
        this.seekAreaCode = seekAreaCode;
    }
}
