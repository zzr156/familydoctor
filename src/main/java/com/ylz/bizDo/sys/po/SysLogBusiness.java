package com.ylz.bizDo.sys.po;

import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.common.util.ExtendDate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 业务修改日志
 */
@Entity
@Table(name = "SYS_LOG_BUSINESS")
public class SysLogBusiness extends BasePO {

    @GenericGenerator(name = "generator", strategy = "uuid.hex")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "ID", unique = true, nullable = false, length = 40)
    private String id;
    @Column(name = "BUSINESS_NAME",length = 60)
    private String businessName;//业务名称
    @Column(name = "BUSINESS_TABLE",length = 40)
    private String businessTable;//业务表名
    @Column(name = "BUSINESS_ID",length = 200)
    private String businessId;//业务主键
    @Column(name = "USER_NAME",length = 50)
    private String userName;//修改者
    @Column(name = "USER_ID",length = 40)
    private String userId;//修改者id
    @Column(name = "ORG_ID",length = 40)
    private String orgId;//机构id
    @Column(name = "BUSINESS_DESC")
    private String businessDesc;//修改描述

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBusinessDesc() {
        return businessDesc;
    }

    public void setBusinessDesc(String businessDesc) {
        this.businessDesc = businessDesc;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getBusinessTable() {
        return businessTable;
    }

    public void setBusinessTable(String businessTable) {
        this.businessTable = businessTable;
    }

    public String getFormatTime(){
        if(this.getHsCreateDate()!=null) {
            return ExtendDate.getYMD_h_m_s(this.getHsCreateDate());
        }
        return "";
    }
}
