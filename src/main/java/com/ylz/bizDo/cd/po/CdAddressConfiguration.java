package com.ylz.bizDo.cd.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by PC on 2017/2/28.
 * 地址信息
 */
@Entity
@Table(name = "CP_CITY_AREA_CONFIGURATION")
public class CdAddressConfiguration extends BasePO {

    @Id
    @Column(name = "CITY_AREA_ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键

    @Column(name = "UP_CITY_AREA_ID", length = 50)
    private String upCityAreaId;//上级地址ID

    @Column(name = "AREA_NAME", length = 50)
    private String areaName;// 地址全称

    @Column(name = "AREA_CODE_JW", length = 32)
    private String areaCodeJw;// 基卫编码

    @Column(name = "UP_CITY_AREA_ID_JW", length = 50)
    private String upCityAreaIdJw;// 上级地址ID

    @Column(name = "AREA_NAME_JW", length = 50)
    private String areaNameJw;// 基卫编码

    @Column(name = "AREA_TYPE", length = 30)
    private String areaType;// 类型 1为基卫 2为农合参保编码


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUpCityAreaId() {
        return upCityAreaId;
    }

    public void setUpCityAreaId(String upCityAreaId) {
        this.upCityAreaId = upCityAreaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaCodeJw() {
        return areaCodeJw;
    }

    public void setAreaCodeJw(String areaCodeJw) {
        this.areaCodeJw = areaCodeJw;
    }

    public String getUpCityAreaIdJw() {
        return upCityAreaIdJw;
    }

    public void setUpCityAreaIdJw(String upCityAreaIdJw) {
        this.upCityAreaIdJw = upCityAreaIdJw;
    }

    public String getAreaNameJw() {
        return areaNameJw;
    }

    public void setAreaNameJw(String areaNameJw) {
        this.areaNameJw = areaNameJw;
    }

    public String getAreaType() {
        return areaType;
    }

    public void setAreaType(String areaType) {
        this.areaType = areaType;
    }
}
