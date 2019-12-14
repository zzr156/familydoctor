package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;


/**
 * 用药指导
 */
@Entity
@Table(name="APP_PERFORMANCE_MEDICATION_GUIDANCE")
public class AppPerformanceMedicationGuidance  extends BasePO {


    // Fields
    @Id
    @Column(name="ID", unique=true, nullable=false, length=36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name="PER_NAME", length=30)
    private String perName;//姓名
    @Column(name="PER_IDNO", length=20)
    private String perIdno;//身份证
    @Column(name="PER_DR_ID", length=36)
    private String perDrId;//医生主键
    @Column(name="PER_DR_NAME", length=25)
    private String perDrName;//医生名字
    @Column(name="PER_HOSP_ID", length=36)
    private String perHospId;//医院主键
    @Column(name="PER_HOSP_NAME", length=45)
    private String perHospName;//医院名字
    @Column(name="PER_AREA_CODE", length=20)
    private String perAreaCode;//行政区划
    @Column(name="PER_CREATE_DATE", length=19)
    private Calendar perCreateDate;//创建时间
    @Column(name="PER_YEAR", length=10)
    private String perYear;//年
    @Column(name="PER_MONTH", length=10)
    private String perMonth;//月
    @Column(name="PER_YEAR_MONTH", length=10)
    private String perYearMonth;//年月
    @Column(name="PER_SOURCE", length=10)
    private String perSource;//来源
    @Column(name="PER_FOREIGN", length=36)
    private String perForeign;//主键


    // Constructors

    /** default constructor */
    public AppPerformanceMedicationGuidance() {
    }

    /** minimal constructor */
    public AppPerformanceMedicationGuidance(String id) {
        this.id = id;
    }

    /** full constructor */
    public AppPerformanceMedicationGuidance(String id, String perName, String perIdno,
                                         String perDrId, String perDrName, String perHospId,
                                         String perHospName, String perAreaCode, Calendar perCreateDate,
                                         String perYear, String perMonth, String perYearMonth,String perSource) {
        this.id = id;
        this.perName = perName;
        this.perIdno = perIdno;
        this.perDrId = perDrId;
        this.perDrName = perDrName;
        this.perHospId = perHospId;
        this.perHospName = perHospName;
        this.perAreaCode = perAreaCode;
        this.perCreateDate = perCreateDate;
        this.perYear = perYear;
        this.perMonth = perMonth;
        this.perYearMonth = perYearMonth;
        this.perSource = perSource;
    }


    // Property accessors

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPerName() {
        return this.perName;
    }

    public void setPerName(String perName) {
        this.perName = perName;
    }

    public String getPerIdno() {
        return this.perIdno;
    }

    public void setPerIdno(String perIdno) {
        this.perIdno = perIdno;
    }

    public String getPerDrId() {
        return this.perDrId;
    }

    public void setPerDrId(String perDrId) {
        this.perDrId = perDrId;
    }

    public String getPerDrName() {
        return this.perDrName;
    }

    public void setPerDrName(String perDrName) {
        this.perDrName = perDrName;
    }

    public String getPerHospId() {
        return this.perHospId;
    }

    public void setPerHospId(String perHospId) {
        this.perHospId = perHospId;
    }

    public String getPerHospName() {
        return this.perHospName;
    }

    public void setPerHospName(String perHospName) {
        this.perHospName = perHospName;
    }

    public String getPerAreaCode() {
        return this.perAreaCode;
    }

    public void setPerAreaCode(String perAreaCode) {
        this.perAreaCode = perAreaCode;
    }

    public Calendar getPerCreateDate() {
        return this.perCreateDate;
    }

    public void setPerCreateDate(Calendar perCreateDate) {
        this.perCreateDate = perCreateDate;
    }

    public String getPerYear() {
        return this.perYear;
    }

    public void setPerYear(String perYear) {
        this.perYear = perYear;
    }

    public String getPerMonth() {
        return this.perMonth;
    }

    public void setPerMonth(String perMonth) {
        this.perMonth = perMonth;
    }

    public String getPerYearMonth() {
        return this.perYearMonth;
    }

    public void setPerYearMonth(String perYearMonth) {
        this.perYearMonth = perYearMonth;
    }

    public String getPerSource() {
        return perSource;
    }

    public void setPerSource(String perSource) {
        this.perSource = perSource;
    }

    public String getPerForeign() {
        return perForeign;
    }

    public void setPerForeign(String perForeign) {
        this.perForeign = perForeign;
    }
}