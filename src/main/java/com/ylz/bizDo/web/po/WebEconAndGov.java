package com.ylz.bizDo.web.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**
 * 经济类型和政府补贴类型
 * Created by zzl on 2018/3/14.
 */
@Entity
@Table(name = "APP_ECON_AND_GOV")
public class WebEconAndGov extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "assigned")
    private String id;//主键
    @Column(name = "EAG_ECON_VALUE",length = 10)
    private String eagEconValue;//经济类型值
    @Column(name = "EAG_ECON_TITLE")
    private String eagEconTitle;//经济类型名称
    @Column(name = "EAG_GOV_VALUE")
    private String eagGovValue;//政府补贴类型值
    @Column(name = "EAG_GOV_TITLE")
    private String eagGovTitle;//政府补贴类型名称
    @Column(name = "EAG_OPEN_STATE",length = 10)
    private String eagOpenState;//开启状态
    @Column(name = "EAG_CREATE_ID",length = 36)
    private String eagCreateId;//创建人id
    @Column(name = "EAG_DEPT_ID",length = 36)
    private String eagDeptId;//单位id
    @Column(name = "EAG_AREA_CODE",length = 100)
    private String eagAreaCode;//区域
    @Column(name = "EAG_LEVEL",length = 10)
    private String eagLevel;//经济类型级别
    @Column(name = "EAG_CREATE_TIME")
    private Calendar eagCreateTime;//创建时间
    @Column(name = "EAG_ECON_ID",length = 36)
    private String eagEconId;//经济类型id
    @Column(name = "EAG_GOV_ID")//长度类型text
    private String eagGovId;//政府补贴id

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEagEconValue() {
        return eagEconValue;
    }

    public void setEagEconValue(String eagEconValue) {
        this.eagEconValue = eagEconValue;
    }

    public String getEagEconTitle() {
        return eagEconTitle;
    }

    public void setEagEconTitle(String eagEconTitle) {
        this.eagEconTitle = eagEconTitle;
    }

    public String getEagGovValue() {
        return eagGovValue;
    }

    public void setEagGovValue(String eagGovValue) {
        this.eagGovValue = eagGovValue;
    }

    public String getEagGovTitle() {
        return eagGovTitle;
    }

    public void setEagGovTitle(String eagGovTitle) {
        this.eagGovTitle = eagGovTitle;
    }

    public String getEagOpenState() {
        return eagOpenState;
    }

    public void setEagOpenState(String eagOpenState) {
        this.eagOpenState = eagOpenState;
    }

    public String getEagCreateId() {
        return eagCreateId;
    }

    public void setEagCreateId(String eagCreateId) {
        this.eagCreateId = eagCreateId;
    }

    public String getEagDeptId() {
        return eagDeptId;
    }

    public void setEagDeptId(String eagDeptId) {
        this.eagDeptId = eagDeptId;
    }

    public String getEagAreaCode() {
        return eagAreaCode;
    }

    public void setEagAreaCode(String eagAreaCode) {
        this.eagAreaCode = eagAreaCode;
    }

    public String getEagLevel() {
        return eagLevel;
    }

    public void setEagLevel(String eagLevel) {
        this.eagLevel = eagLevel;
    }

    public Calendar getEagCreateTime() {
        return eagCreateTime;
    }

    public void setEagCreateTime(Calendar eagCreateTime) {
        this.eagCreateTime = eagCreateTime;
    }

    public String getEagEconId() {
        return eagEconId;
    }

    public void setEagEconId(String eagEconId) {
        this.eagEconId = eagEconId;
    }

    public String getEagGovId() {
        return eagGovId;
    }

    public void setEagGovId(String eagGovId) {
        this.eagGovId = eagGovId;
    }
}
