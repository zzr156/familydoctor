package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 市级权限
 */
@Entity
@Table(name="APP_MUNICIPAL_AUTHORITY")
public class AppMunicipalAuthority extends BasePO {

    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name = "AREA_CODE",length = 50)
    private String areaCode;//市编码
    @Column(name = "AREA_NAME",length = 50)
    private String areaName;//地址全称
    @Column(name = "AREA_SNAME",length = 50)
    private String areaSname;//地址简称
    @Column(name = "AREA_STATE",length = 50)
    private String atreState;//是否开启区名称


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaSname() {
        return areaSname;
    }

    public void setAreaSname(String areaSname) {
        this.areaSname = areaSname;
    }

    public String getAtreState() {
        return atreState;
    }

    public void setAtreState(String atreState) {
        this.atreState = atreState;
    }
}
