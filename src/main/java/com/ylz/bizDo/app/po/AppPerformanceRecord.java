package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**履约记录表
 * Created by zzl on 2017/11/14.
 */
@Entity
@Table(name = "APP_PERFORMANCE_RECORD")
public class AppPerformanceRecord extends BasePO{
    @Id
    @Column(name="ID", unique=true, nullable=false, length=36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name = "APR_PATIENT_ID",length = 36)
    private String aprPatientId;//患者id
    @Column(name = "APR_PATIENT_NAME",length = 50)
    private String aprPatientName;//患者姓名
    @Column(name = "APR_PC_NUM",length = 100)
    private String aprPcNum;//批次号
    @Column(name = "APR_CREATE_TIME")
    private Calendar aprCreateTime;//创建时间
    @Column(name = "APR_DR_ID",length = 36)
    private String aprDrId;//医生id
    @Column(name = "APR_TYPE",length = 10)
    private String aprType;//类型
    @Column(name = "APR_SIGN_ID",length = 36)
    private String aprSignId;//签约单id
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAprPatientId() {
        return aprPatientId;
    }

    public void setAprPatientId(String aprPatientId) {
        this.aprPatientId = aprPatientId;
    }

    public String getAprPatientName() {
        return aprPatientName;
    }

    public void setAprPatientName(String aprPatientName) {
        this.aprPatientName = aprPatientName;
    }

    public String getAprPcNum() {
        return aprPcNum;
    }

    public void setAprPcNum(String aprPcNum) {
        this.aprPcNum = aprPcNum;
    }

    public Calendar getAprCreateTime() {
        return aprCreateTime;
    }

    public void setAprCreateTime(Calendar aprCreateTime) {
        this.aprCreateTime = aprCreateTime;
    }

    public String getAprDrId() {
        return aprDrId;
    }

    public void setAprDrId(String aprDrId) {
        this.aprDrId = aprDrId;
    }

    public String getAprType() {
        return aprType;
    }

    public void setAprType(String aprType) {
        this.aprType = aprType;
    }

    public String getAprSignId() {
        return aprSignId;
    }

    public void setAprSignId(String aprSignId) {
        this.aprSignId = aprSignId;
    }
}
