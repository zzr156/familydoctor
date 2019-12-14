package com.ylz.bizDo.cd.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by WangCheng on 2018/09/25.
 */
@Entity
@Table(name="cd_dissolution_warning")
public class CdDissolutionWarning extends BasePO {

    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 40)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键

    @Column(name = "ORG_ID", length = 40)
    private String orgId;//机构主键

    @Column(name = "DR_ID", length = 40)
    private String drId;//医生主键

    @Column(name = "RED_WARNING_DAY", length = 20)
    private String redWarningDay;//红色预警天数

    @Column(name = "RED_WARNING_STATE", length = 2)
    private String redWarningState;//红色预警启用状态

    @Column(name = "YELLOW_WARNING_DAY", length = 20)
    private String yellowWarningDay;//黄色预警天数

    @Column(name = "YELLOW_WARNING_STATE", length = 2)
    private String yellowWarningState;//黄色预警启用状态

    @Column(name = "GREEN_WARNING_DAY", length = 20)
    private String greenWarningDay;//绿色预警天数

    @Column(name = "GREEN_WARNING_STATE", length = 2)
    private String greenWarningState;//绿色预警启用状态

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getRedWarningDay() {
        return redWarningDay;
    }

    public void setRedWarningDay(String redWarningDay) {
        this.redWarningDay = redWarningDay;
    }

    public String getRedWarningState() {
        return redWarningState;
    }

    public void setRedWarningState(String redWarningState) {
        this.redWarningState = redWarningState;
    }

    public String getYellowWarningDay() {
        return yellowWarningDay;
    }

    public void setYellowWarningDay(String yellowWarningDay) {
        this.yellowWarningDay = yellowWarningDay;
    }

    public String getYellowWarningState() {
        return yellowWarningState;
    }

    public void setYellowWarningState(String yellowWarningState) {
        this.yellowWarningState = yellowWarningState;
    }

    public String getGreenWarningDay() {
        return greenWarningDay;
    }

    public void setGreenWarningDay(String greenWarningDay) {
        this.greenWarningDay = greenWarningDay;
    }

    public String getGreenWarningState() {
        return greenWarningState;
    }

    public void setGreenWarningState(String greenWarningState) {
        this.greenWarningState = greenWarningState;
    }
}