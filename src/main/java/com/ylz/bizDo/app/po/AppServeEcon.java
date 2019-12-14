package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**
 * 经济类型表
 * Created by zzl on 2017/8/16.
 */
@Entity
@Table(name="APP_SERVE_ECON")
public class AppServeEcon extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name = "ECON_VALUE",length = 100)
    private String econValue;//经济类型值
    @Column(name = "ECON_TITLE",length = 100)
    private String econTitle;//经济类型名称
    @Column(name = "ECON_AREA_CODE",length = 50)
    private String econAreaCode;//区域编号
    @Column(name = "ECON_STATE",length = 10)
    private String econState;//标记状态
    @Column(name = "ECON_DEPT_ID",length = 36)
    private String econDeptId;//单位id
    @Column(name = "ECON_JC_TYPE",length = 10)
    private String econJcType="0";//是否是系统经济类型
    @Column(name = "ECON_CREATE_ID",length = 36)
    private String econCreateId;//创建人id
    @Column(name = "ECON_CREATE_TIME")
    private Calendar econCreateTime;//创建时间
    @Column(name = "ECON_OPEN_STATE",length = 10)
    private String econOpenState;//开启状态
    @Column(name = "ECON_LEVEL",length = 10)
    private String econLevel;//等级
    @Column(name = "ECON_TAB_STATE",length = 10)
    private String econTabState;//标记
    @Column(name = "ECON_LABEL_TYPE",length = 10)
    private String econLabelType;//所属服务类型1
    @Column(name = "ECON_FW_TYPE",length = 10)
    private String econFwType;//所属服务类型2


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEconValue() {
        return econValue;
    }

    public void setEconValue(String econValue) {
        this.econValue = econValue;
    }

    public String getEconTitle() {
        return econTitle;
    }

    public void setEconTitle(String econTitle) {
        this.econTitle = econTitle;
    }

    public String getEconAreaCode() {
        return econAreaCode;
    }

    public void setEconAreaCode(String econAreaCode) {
        this.econAreaCode = econAreaCode;
    }

    public String getEconState() {
        return econState;
    }

    public void setEconState(String econState) {
        this.econState = econState;
    }

    public String getEconDeptId() {
        return econDeptId;
    }

    public void setEconDeptId(String econDeptId) {
        this.econDeptId = econDeptId;
    }

    public String getEconJcType() {
        return econJcType;
    }

    public void setEconJcType(String econJcType) {
        this.econJcType = econJcType;
    }

    public String getEconCreateId() {
        return econCreateId;
    }

    public void setEconCreateId(String econCreateId) {
        this.econCreateId = econCreateId;
    }

    public Calendar getEconCreateTime() {
        return econCreateTime;
    }

    public void setEconCreateTime(Calendar econCreateTime) {
        this.econCreateTime = econCreateTime;
    }

    public String getEconOpenState() {
        return econOpenState;
    }

    public void setEconOpenState(String econOpenState) {
        this.econOpenState = econOpenState;
    }

    public String getEconLevel() {
        return econLevel;
    }

    public void setEconLevel(String econLevel) {
        this.econLevel = econLevel;
    }

    public String getEconTabState() {
        return econTabState;
    }

    public void setEconTabState(String econTabState) {
        this.econTabState = econTabState;
    }

    public String getEconLabelType() {
        return econLabelType;
    }

    public void setEconLabelType(String econLabelType) {
        this.econLabelType = econLabelType;
    }

    public String getEconFwType() {
        return econFwType;
    }

    public void setEconFwType(String econFwType) {
        this.econFwType = econFwType;
    }
}
