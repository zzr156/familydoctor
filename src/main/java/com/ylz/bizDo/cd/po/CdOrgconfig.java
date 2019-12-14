package com.ylz.bizDo.cd.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by Administrator on 2018/1/26.
 * 机构配置表（用于机构查询是否签约或建卡立民）
 */
@Entity
@Table(name="CD_ORGCONFIG")
//@org.hibernate.annotations.Cache(usage= CacheConcurrencyStrategy.READ_WRITE)//要使用hibernate的二级缓存Cache的注释一定要添加上
public class CdOrgconfig extends BasePO{
    @Id
    @Column(name = "ORGID", unique = true, nullable = false, length = 36)
    private String orgId;//机构id
    @Column(name = "START_TYPE",length = 2)
    private String startType;//开启状态 0 未开启 1 开启

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getStartType() {
        return startType;
    }

    public void setStartType(String startType) {
        this.startType = startType;
    }

}
