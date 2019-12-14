package com.ylz.bizDo.register.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="APP_HEALTHCARE_PARAMETER")
public class HealthCareParameter extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    private String id;

    @Column(name = "HP_HOSP_ID", length = 36)
    private String hospId;

    @Column(name = "HP_PARAM_NAME", length = 36)
    private String parameterName;

    @Column(name = "HP_PARAM_VALUE", length = 36)
    private String parameterValue;

    @Column(name = "HP_PARAM_REMARK", length = 255)
    private String parameterRemark;

    public HealthCareParameter() {
    }

    public HealthCareParameter(String hospId, String parameterName, String parameterValue, String parameterRemark) {
        this.hospId = hospId;
        this.parameterName = parameterName;
        this.parameterValue = parameterValue;
        this.parameterRemark = parameterRemark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHospId() {
        return hospId;
    }

    public void setHospId(String hospId) {
        this.hospId = hospId;
    }

    public String getParameterName() {
        return parameterName;
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }

    public String getParameterRemark() {
        return parameterRemark;
    }

    public void setParameterRemark(String parameterRemark) {
        this.parameterRemark = parameterRemark;
    }
}
