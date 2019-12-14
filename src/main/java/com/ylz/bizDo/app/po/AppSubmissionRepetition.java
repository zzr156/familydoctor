package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * app医生代签约重复提交接口
 * Created by asus on 2018/05/31.
 */
@Entity
@Table(name = "APP_SUBMISSION_REPETITION")
public class AppSubmissionRepetition extends BasePO {

    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name = "PATIENT_ID",length =36 )
    private String patientId;//患者主键
    @Column(name = "SIGN_MD5_RESULT",length =150 )
    private String signMd5Result;//md5加密
    @Column(name = "SIGN_ID",length =36 )
    private String signId;//签约主键

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getSignMd5Result() {
        return signMd5Result;
    }

    public void setSignMd5Result(String signMd5Result) {
        this.signMd5Result = signMd5Result;
    }

    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) {
        this.signId = signId;
    }
}
