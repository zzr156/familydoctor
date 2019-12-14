package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 分标修改日志表
 * Created by zzl on 2018/8/15.
 */
@Entity
@Table(name = "APP_MARKING_LOG")
public class AppMarkingLog extends BasePO {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    private String id;
    @Column(name = "BUSINESS_SIGN_ID",length = 36)
    private String businessSignId;//居民主键
    @Column(name = "BUSINESS_NAME",length = 60)
    private String businessName;//业务名称
    @Column(name = "USER_ID",length = 36)
    private String userId;//修改人id
    @Column(name = "USER_NAME",length = 50)
    private String userName;//修改人姓名
    @Column(name = "ORG_ID",length = 36)
    private String orgId;//机构id
    @Column(name = "MANAGE_YEAR",length = 10)
    private String year;//年
    @Column(name = "MANAGE_YEAR_MONTH",length = 20)
    private String yearMonth;//年月
    @Column(name = "AREA_CODE",length = 12)
    private String areaCode;//区域编码


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusinessSignId() {
        return businessSignId;
    }

    public void setBusinessSignId(String businessSignId) {
        this.businessSignId = businessSignId;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getYearMonth() {
        return yearMonth;
    }

    public void setYearMonth(String yearMonth) {
        this.yearMonth = yearMonth;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
}
