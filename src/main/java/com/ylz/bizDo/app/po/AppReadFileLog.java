package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 档案调阅记录表
 */
@Entity
@Table(name = "APP_READ_FILE_LOG")
@DynamicInsert
public class AppReadFileLog extends BasePO {

    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;// 主键ID

    @Column(name = "PATIENT_NAME")
    private String patientName;// 居民姓名

    @Column(name = "PATIENT_IDNO")
    private String patientIdno;// 居民身份证号

    @Column(name = "PATIENT_DFID")
    private String patientDfid;// 居民健康档案号

    @Column(name = "READ_USER_ID")
    private String readUserId;// 调阅人ID

    @Column(name = "READ_USER_NAME")
    private String readUserName;// 调阅人名称

    @Column(name = "READ_WAY")
    private String readWay;// 调阅方式（1-居民签约信息，2-其他）


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientIdno() {
        return patientIdno;
    }

    public void setPatientIdno(String patientIdno) {
        this.patientIdno = patientIdno;
    }

    public String getPatientDfid() {
        return patientDfid;
    }

    public void setPatientDfid(String patientDfid) {
        this.patientDfid = patientDfid;
    }

    public String getReadUserId() {
        return readUserId;
    }

    public void setReadUserId(String readUserId) {
        this.readUserId = readUserId;
    }

    public String getReadUserName() {
        return readUserName;
    }

    public void setReadUserName(String readUserName) {
        this.readUserName = readUserName;
    }

    public String getReadWay() {
        return readWay;
    }

    public void setReadWay(String readWay) {
        this.readWay = readWay;
    }
}
