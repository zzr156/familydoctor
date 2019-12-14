package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 分标修改日志详细表
 * Created by zzl on 2018/8/15.
 */
@Entity
@Table(name = "APP_MARKING_LOG_ITEM")
public class AppMarkingLogItem extends BasePO {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    private String id;
    @Column(name = "LOG_ID",length = 40)
    private String logId;//修改日志id
    @Column(name = "BUSINESS_TABLE",length = 100)
    private String businessTable;//业务表名
    @Column(name = "BUSINESS_ID",length = 200)
    private String businessId;//业务主键
    @Column(name = "ITME_KEY",length = 60)
    private String key;//修改的字段名
    @Column(name = "OLD_VALUE")
    private String oldValue;//原来值
    @Column(name = "NEW_VALUE")
    private String newValue;//新值
    @Column(name = "TYPE",length = 10)
    private String type;//疾病类型(高血压201 糖尿病202)

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public String getBusinessTable() {
        return businessTable;
    }

    public void setBusinessTable(String businessTable) {
        this.businessTable = businessTable;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
