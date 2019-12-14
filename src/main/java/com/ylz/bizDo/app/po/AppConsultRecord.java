package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**咨询记录表
 * Created by zzl on 2017/10/25.
 */
@Entity
@Table(name = "APP_CONSULT_RECORD")
public class AppConsultRecord extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name = "CONREC_DR_ID",length = 36)
    private String conrecDrId;//医生id
    @Column(name = "CONREC_PATIENT_ID",length = 36)
    private String conrecPatientId;//患者id
    @Column(name = "CONREC_INITIATE_TIME")
    private Calendar conrecInitiateTime;//发起时间
    @Column(name = "CONREC_REPLY_TIME")
    private Calendar conrecReplyTime;//回复时间
    @Column(name = "CONREC_TEAM_ID",length = 36)
    private String conrecTeamId;//团队主键
    @Column(name = "CONREC_HOSP_ID",length = 36)
    private String conrecHospId;//医院主键
    @Column(name = "CONREC_AREA_CODE",length = 20)
    private String conrecAreaCode;//行政区划

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConrecDrId() {
        return conrecDrId;
    }

    public void setConrecDrId(String conrecDrId) {
        this.conrecDrId = conrecDrId;
    }

    public String getConrecPatientId() {
        return conrecPatientId;
    }

    public void setConrecPatientId(String conrecPatientId) {
        this.conrecPatientId = conrecPatientId;
    }

    public Calendar getConrecInitiateTime() {
        return conrecInitiateTime;
    }

    public void setConrecInitiateTime(Calendar conrecInitiateTime) {
        this.conrecInitiateTime = conrecInitiateTime;
    }

    public Calendar getConrecReplyTime() {
        return conrecReplyTime;
    }

    public void setConrecReplyTime(Calendar conrecReplyTime) {
        this.conrecReplyTime = conrecReplyTime;
    }

    public String getConrecTeamId() {
        return conrecTeamId;
    }

    public void setConrecTeamId(String conrecTeamId) {
        this.conrecTeamId = conrecTeamId;
    }

    public String getConrecHospId() {
        return conrecHospId;
    }

    public void setConrecHospId(String conrecHospId) {
        this.conrecHospId = conrecHospId;
    }

    public String getConrecAreaCode() {
        return conrecAreaCode;
    }

    public void setConrecAreaCode(String conrecAreaCode) {
        this.conrecAreaCode = conrecAreaCode;
    }
}
