package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**用药指导
 * Created by zzl on 2017/6/17.
 */
@Entity
@Table(name = "APP_DRUG_GUIDE")
public class AppDrugGuide extends BasePO {
    // Fields
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name = "DG_DRUG_ID", length =36 )
    private String dgDrugId;//药品外键
    @Column(name = "DG_DRUG_NAME", length =50 )
    private String dgDrugName;//药品名称
    @Column(name = "DG_DRUG_TYPE", length =50 )
    private String dgDrugType;//药品类型
    @Column(name = "DG_PERIOD", length =50 )
    private String dgPeriod;//用药周期
    @Column(name = "DG_USAGE", length =10 )
    private String dgUsage;//服用方法
    @Column(name = "DG_TAKING", length = 10)
    private String dgTaking;//服用时间 饭前饭后
    @Column(name = "DG_FREQUENCY", length = 50)
    private String dgFrequency;//用药频次
    @Column(name = "DG_DOC_ID", length = 36)
    private String dgDocId;//指导医生
    @Column(name = "DG_GUIDE_TIME")
    private Calendar dgGuideTime;//指导时间
    @Column(name = "DG_PATIENT_ID", length =36)
    private String dgPatientId;//患者id
    @Column(name = "DG_END_TIME")
    private Calendar dgEndTime;//周期结束时间 1-7 指导时间在 周期之内
    @Column(name = "DG_WARN_NUM", length = 10)
    private String dgWarnNum;//用药提醒
    @Column(name = "DG_COMMON_WARN_NUM", length = 10)
    private String dgCommonWarnNum;//通用用药提醒
    @Column(name = "DG_ENABLE_WARN", length = 10)
    private String dgEnableWarn;//开启提醒 1是 0否
    @Column(name = "DG_DRUG_BEGIN_TIME")
    private Calendar dgDrugBeginTime;//用药开始时间
    @Column(name = "DG_GUIDE_FROM", length = 10)
    private String dgGuideFrom;//用药指导来源 1新增 2随访 3健康干预
    @Column(name = "DG_GUIDE_NOTE", length = 2000)
    private String dgGuideNote;//备注
    @Column(name = "DG_FOLLOW_GUIDE", length = 10)
    private String dgFollowGuide;//是否遵从医嘱，1是 0否
    @Column(name = "DG_FREQUENCY_OTHER", length = 50)
    private String dgFrequencyOther;//频次其他
    @Column(name = "DG_PERIOD_OTHER", length = 50)
    private String dgPeriodOther;//周期其他
    @Column(name = "DG_USE_LEVEL", length = 50)
    private String dgUseLevel;//用量
    @Column(name = "DG_DOSAGE_UNIT", length = 50)
    private String dgDosageUnit;//用量单位
    @Column(name = "DG_ALL_USE_LEVEL", length = 50)
    private String dgAllUseLevel;//总量
    @Column(name = "DG_ALL_DOSAGE_UNIT", length = 50)
    private String dgAllDosageUnit;//总量单位
    @Column(name = "DG_SPEC")
    private String dgSpec;//规格
    @Column(name = "DG_BATCH")
    private String dgBatch;//批号
    @Column(name = "DG_BATCH_NUM", length = 50)
    private String dgBatchNum;//批号
    @Column(name = "DG_HOSP_ID", length = 36)
    private String dgHospId;//医院id
    @Column(name = "DG_TEAM_ID", length = 36)
    private String dgTeamId;//团队id
    @Column(name = "DG_AREA_CODE", length = 50)
    private String dgAreaCode;//区域编码
    @Column(name = "DG_SEND_STATE", length = 50)
    private String dgSendState = "0";//发送状态1为已发送

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDgDrugId() {
        return dgDrugId;
    }

    public void setDgDrugId(String dgDrugId) {
        this.dgDrugId = dgDrugId;
    }

    public String getDgPeriod() {
        return dgPeriod;
    }

    public void setDgPeriod(String dgPeriod) {
        this.dgPeriod = dgPeriod;
    }

    public String getDgUsage() {
        return dgUsage;
    }

    public void setDgUsage(String dgUsage) {
        this.dgUsage = dgUsage;
    }

    public String getDgTaking() {
        return dgTaking;
    }

    public void setDgTaking(String dgTaking) {
        this.dgTaking = dgTaking;
    }

    public String getDgFrequency() {
        return dgFrequency;
    }

    public void setDgFrequency(String dgFrequency) {
        this.dgFrequency = dgFrequency;
    }

    public String getDgDocId() {
        return dgDocId;
    }

    public void setDgDocId(String dgDocId) {
        this.dgDocId = dgDocId;
    }

    public Calendar getDgGuideTime() {
        return dgGuideTime;
    }

    public void setDgGuideTime(Calendar dgGuideTime) {
        this.dgGuideTime = dgGuideTime;
    }

    public String getDgPatientId() {
        return dgPatientId;
    }

    public void setDgPatientId(String dgPatientId) {
        this.dgPatientId = dgPatientId;
    }

    public Calendar getDgEndTime() {
        return dgEndTime;
    }

    public void setDgEndTime(Calendar dgEndTime) {
        this.dgEndTime = dgEndTime;
    }

    public String getDgDrugName() {
        return dgDrugName;
    }

    public void setDgDrugName(String dgDrugName) {
        this.dgDrugName = dgDrugName;
    }

    public String getDgDrugType() {
        return dgDrugType;
    }

    public void setDgDrugType(String dgDrugType) {
        this.dgDrugType = dgDrugType;
    }

    public String getDgWarnNum() {
        return dgWarnNum;
    }

    public void setDgWarnNum(String dgWarnNum) {
        this.dgWarnNum = dgWarnNum;
    }

    public String getDgCommonWarnNum() {
        return dgCommonWarnNum;
    }

    public void setDgCommonWarnNum(String dgCommonWarnNum) {
        this.dgCommonWarnNum = dgCommonWarnNum;
    }

    public String getDgEnableWarn() {
        return dgEnableWarn;
    }

    public void setDgEnableWarn(String dgEnableWarn) {
        this.dgEnableWarn = dgEnableWarn;
    }

    public Calendar getDgDrugBeginTime() {
        return dgDrugBeginTime;
    }

    public void setDgDrugBeginTime(Calendar dgDrugBeginTime) {
        this.dgDrugBeginTime = dgDrugBeginTime;
    }

    public String getDgGuideFrom() {
        return dgGuideFrom;
    }

    public void setDgGuideFrom(String dgGuideFrom) {
        this.dgGuideFrom = dgGuideFrom;
    }

    public String getDgGuideNote() {
        return dgGuideNote;
    }

    public void setDgGuideNote(String dgGuideNote) {
        this.dgGuideNote = dgGuideNote;
    }

    public String getDgFollowGuide() {
        return dgFollowGuide;
    }

    public void setDgFollowGuide(String dgFollowGuide) {
        this.dgFollowGuide = dgFollowGuide;
    }

    public String getDgFrequencyOther() {
        return dgFrequencyOther;
    }

    public void setDgFrequencyOther(String dgFrequencyOther) {
        this.dgFrequencyOther = dgFrequencyOther;
    }

    public String getDgPeriodOther() {
        return dgPeriodOther;
    }

    public void setDgPeriodOther(String dgPeriodOther) {
        this.dgPeriodOther = dgPeriodOther;
    }

    public String getDgHospId() {
        return dgHospId;
    }

    public void setDgHospId(String dgHospId) {
        this.dgHospId = dgHospId;
    }

    public String getDgTeamId() {
        return dgTeamId;
    }

    public void setDgTeamId(String dgTeamId) {
        this.dgTeamId = dgTeamId;
    }

    public String getDgAreaCode() {
        return dgAreaCode;
    }

    public void setDgAreaCode(String dgAreaCode) {
        this.dgAreaCode = dgAreaCode;
    }

    public String getDgSendState() {
        return dgSendState;
    }

    public void setDgSendState(String dgSendState) {
        this.dgSendState = dgSendState;
    }

    public String getDgUseLevel() {
        return dgUseLevel;
    }

    public void setDgUseLevel(String dgUseLevel) {
        this.dgUseLevel = dgUseLevel;
    }

    public String getDgDosageUnit() {
        return dgDosageUnit;
    }

    public void setDgDosageUnit(String dgDosageUnit) {
        this.dgDosageUnit = dgDosageUnit;
    }

    public String getDgAllUseLevel() {
        return dgAllUseLevel;
    }

    public void setDgAllUseLevel(String dgAllUseLevel) {
        this.dgAllUseLevel = dgAllUseLevel;
    }

    public String getDgAllDosageUnit() {
        return dgAllDosageUnit;
    }

    public void setDgAllDosageUnit(String dgAllDosageUnit) {
        this.dgAllDosageUnit = dgAllDosageUnit;
    }

    public String getDgBatchNum() {
        return dgBatchNum;
    }

    public void setDgBatchNum(String dgBatchNum) {
        this.dgBatchNum = dgBatchNum;
    }

    public String getDgSpec() {
        return dgSpec;
    }

    public void setDgSpec(String dgSpec) {
        this.dgSpec = dgSpec;
    }

    public String getDgBatch() {
        return dgBatch;
    }

    public void setDgBatch(String dgBatch) {
        this.dgBatch = dgBatch;
    }
}
