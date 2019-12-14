package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 医院科室表
 * Created by zzl on 2017/12/12.
 */
@Entity
@Table(name = "APP_HOSPITAL_DEPARTMENTS")
public class AppHospitalDepartments extends BasePO{
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name = "HD_HOSP_ID",length = 36)
    private String hdHospId;//医院id
    @Column(name = "HD_SECTION_NUMBER",length = 36)
    private String hdSectionNumber;//科室编号
    @Column(name = "HD_SECTION_NAME",length = 36)
    private String hdSectionName;//科室名称

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHdHospId() {
        return hdHospId;
    }

    public void setHdHospId(String hdHospId) {
        this.hdHospId = hdHospId;
    }

    public String getHdSectionNumber() {
        return hdSectionNumber;
    }

    public void setHdSectionNumber(String hdSectionNumber) {
        this.hdSectionNumber = hdSectionNumber;
    }

    public String getHdSectionName() {
        return hdSectionName;
    }

    public void setHdSectionName(String hdSectionName) {
        this.hdSectionName = hdSectionName;
    }
}
