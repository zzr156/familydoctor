package com.ylz.bizDo.plan.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/** 通用随访既往史（1手术 2外伤 3输血）
 * Created by zzl on 2017/11/28.
 */
@Entity
@Table(name = "APP_GENERAL_DISEASE_HIS_TABLE")
public class AppGeneralDiseaseHisTable extends BasePO{
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;
    @Column(name = "GDHT_GEN_ID",length = 36)
    private String gdhtGenId;//通用随访外键
    @Column(name = "GDHT_NAMEORREASON",length = 100)
    private String gdhtNameOrReason;//名称或原因
    @Column(name = "GDHT_TIME")
    private Calendar gdhtTime;//时间
    @Column(name = "GDHT_TYPE",length = 2)
    private String gdhtType;//类型（1手术 2外伤 3输血）

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGdhtGenId() {
        return gdhtGenId;
    }

    public void setGdhtGenId(String gdhtGenId) {
        this.gdhtGenId = gdhtGenId;
    }

    public String getGdhtNameOrReason() {
        return gdhtNameOrReason;
    }

    public void setGdhtNameOrReason(String gdhtNameOrReason) {
        this.gdhtNameOrReason = gdhtNameOrReason;
    }

    public Calendar getGdhtTime() {
        return gdhtTime;
    }

    public void setGdhtTime(Calendar gdhtTime) {
        this.gdhtTime = gdhtTime;
    }

    public String getGdhtType() {
        return gdhtType;
    }

    public void setGdhtType(String gdhtType) {
        this.gdhtType = gdhtType;
    }
}
