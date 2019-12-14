package com.ylz.bizDo.plan.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**用药情况和用药指导数据表
 * Created by admin on 2017-05-14.
 */
@Entity
@Table(name = "APP_MEDICATION_TABLE")
public class AppMedicationTable extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;
    @Column(name = "VISIT_ID", length = 36)
    private String visitId;//随访外键
    @Column(name = "MEDICINE_NAME",length =50)
    private String medicineName;//药物名称
    @Column(name = "USE_TO_DAY",length =20)
    private String userToDay;//每日（月）次
    @Column(name = "USE_TO_TIME",length =20)
    private String userToTime;//每次剂量


    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getUserToDay() {
        return userToDay;
    }

    public void setUserToDay(String userToDay) {
        this.userToDay = userToDay;
    }

    public String getUserToTime() {
        return userToTime;
    }

    public void setUserToTime(String userToTime) {
        this.userToTime = userToTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVisitId() {
        return visitId;
    }

    public void setVisitId(String visitId) {
        this.visitId = visitId;
    }
}
