package com.ylz.bizDo.sys.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 业务修改日志详细
 */
@Entity
@Table(name = "SYS_LOG_BUSINESS_ITEM")
public class SysLogBusinessItem extends BasePO {

    @GenericGenerator(name = "generator", strategy = "uuid.hex")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "ID", unique = true, nullable = false, length = 40)
    private String id;
    @Column(name = "ITME_KEY",length = 60)
    private String key;//修改的字段名
    @Column(name = "OLD_VALUE")
    private String oldValue;//原来值
    @Column(name = "NEW_VALUE")
    private String newValue;//新值
    @Column(name = "LOG_ID",length = 40)
    private String logId;//修改日志id

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }
}
