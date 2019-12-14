package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**标记表
 * Created by zzl on 2017/8/22.
 */
@Entity
@Table(name = "APP_SERVE_TAB")
public class AppServeTab extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;
    @Column(name = "SERTAB_DEPT_ID",length = 36)
    private String serTabDeptId;//单位id
    @Column(name = "SERTAB_SER_ID",length = 36)
    private String serTabSerId;//服务id
    @Column(name = "SERTAB_TYPE",length = 10)
    private String serTabType;//类别(1套餐 2经济类型 3政府补贴方式,4服务内容，5服务人群，6组合)

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSerTabDeptId() {
        return serTabDeptId;
    }

    public void setSerTabDeptId(String serTabDeptId) {
        this.serTabDeptId = serTabDeptId;
    }

    public String getSerTabSerId() {
        return serTabSerId;
    }

    public void setSerTabSerId(String serTabSerId) {
        this.serTabSerId = serTabSerId;
    }

    public String getSerTabType() {
        return serTabType;
    }

    public void setSerTabType(String serTabType) {
        this.serTabType = serTabType;
    }
}
