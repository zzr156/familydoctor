package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 个人药品库
 * Created by zzl on 2017/6/17.
 */
@Entity
@Table(name = "APP_PERSON_DRUG")
public class AppPersonDrug extends BasePO {
    // Fields
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name = "PD_PATIENT_ID", length = 36)
    private String pdPatientId;//患者id
    @Column(name = "PD_DRUG_ID", length = 36)
    private String pdDrugId;//公共药品库药品id

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPdDrugId() {
        return pdDrugId;
    }

    public void setPdDrugId(String pdDrugId) {
        this.pdDrugId = pdDrugId;
    }

    public String getPdPatientId() {
        return pdPatientId;
    }

    public void setPdPatientId(String pdPatientId) {
        this.pdPatientId = pdPatientId;
    }
}
