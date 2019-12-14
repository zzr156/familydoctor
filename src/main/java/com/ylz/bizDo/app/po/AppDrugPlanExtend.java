package com.ylz.bizDo.app.po;


import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 用药提醒拓展表
 */
@Entity
@Table(name = "APP_DRUG_PLAN_EXTEND")
public class AppDrugPlanExtend extends BasePO {
    // Fields
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name = "DRUG_ID", length = 36)
    private String drugId;//用药指导id
    @Column(name = "DRUG_PLAN_ID", length = 36)
    private String drugPlanId;//用药提醒id

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDrugId() {
        return drugId;
    }

    public void setDrugId(String drugId) {
        this.drugId = drugId;
    }

    public String getDrugPlanId() {
        return drugPlanId;
    }

    public void setDrugPlanId(String drugPlanId) {
        this.drugPlanId = drugPlanId;
    }
}


