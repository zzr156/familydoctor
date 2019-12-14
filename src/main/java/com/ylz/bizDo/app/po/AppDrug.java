package com.ylz.bizDo.app.po;

import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**药品表
 * Created by zzl on 2017/6/17.
 */
@Entity
@Table(name = "APP_DRUG")
public class AppDrug extends BasePO {
    // Fields
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name = "DRUG_TYPE", length =6 )
    private String drugType;//药品类别
    @Column(name = "DRUG_NAME", length =50 )
    private String drugName;//药品名称
    @Column(name = "DRUG_PERIOD", length =50 )
    private String drugPeriod;//用药周期
    @Column(name = "DRUG_USAGE", length =6 )
    private String drugUsage;//服用方法
    @Column(name = "DRUG_TAKING", length = 6)
    private String drugTaking;//服用时间 饭前饭后等
    @Column(name = "DRUG_FREQUENCY", length = 50)
    private String drugFrequency;//频次
    @Column(name = "DRUG_NOTE", length = 2000)
    private String drugNote;//备注
    @Column(name = "DRUG_PERIOD_OTHER", length = 50)
    private String drugPeriodOther;//周期其他
    @Column(name = "DRUG_FREQUENCY_OTHER", length = 50)
    private String drugFrequencyOther;//频次其他
    @Column(name = "DRUG_ADD_TIME")
    private Calendar drugAddTime;//添加时间
    @Column(name = "DRUG_PHARM_ACOLOGY")
    private String drugPharmAcology;//药理
    @Column(name = "DRUG_SPEC")
    private String drugSpec;//规格
    @Column(name = "DRUG_BATCH")
    private String drugBatch;//批号
    @Column(name = "DRUG_USE_LEVEL")
    private String drugUseLevel;//用量
    @Column(name = "DRUG_DOSAGE_UNIT")
    private String drugDosageUnit;//用量单位
    @Column(name = "DRUG_DR_ID")
    private String drugDrId;//创建医生
    @Column(name = "DRUG_HOSP_ID")
    private String drugHospId;//创建单位

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDrugType() {
        return drugType;
    }

    public void setDrugType(String drugType) {
        this.drugType = drugType;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getDrugPeriod() {
        return drugPeriod;
    }

    public void setDrugPeriod(String drugPeriod) {
        this.drugPeriod = drugPeriod;
    }

    public String getDrugUsage() {
        return drugUsage;
    }

    public void setDrugUsage(String drugUsage) {
        this.drugUsage = drugUsage;
    }

    public String getDrugTaking() {
        return drugTaking;
    }

    public void setDrugTaking(String drugTaking) {
        this.drugTaking = drugTaking;
    }

    public String getDrugFrequency() {
        return drugFrequency;
    }

    public void setDrugFrequency(String drugFrequency) {
        this.drugFrequency = drugFrequency;
    }

    public String getDrugNote() {
        return drugNote;
    }

    public void setDrugNote(String drugNote) {
        this.drugNote = drugNote;
    }

    public String getDrugPharmAcology() {
        return drugPharmAcology;
    }

    public void setDrugPharmAcology(String drugPharmAcology) {
        this.drugPharmAcology = drugPharmAcology;
    }

    public String getDrugSpec() {
        return drugSpec;
    }

    public void setDrugSpec(String drugSpec) {
        this.drugSpec = drugSpec;
    }

    public String getDrugBatch() {
        return drugBatch;
    }

    public void setDrugBatch(String drugBatch) {
        this.drugBatch = drugBatch;
    }

    public String getDrugUseLevel() {
        return drugUseLevel;
    }

    public void setDrugUseLevel(String drugUseLevel) {
        this.drugUseLevel = drugUseLevel;
    }

    public String getDrugDosageUnit() {
        return drugDosageUnit;
    }

    public void setDrugDosageUnit(String drugDosageUnit) {
        this.drugDosageUnit = drugDosageUnit;
    }

    public String getDrugDrId() {
        return drugDrId;
    }

    public void setDrugDrId(String drugDrId) {
        this.drugDrId = drugDrId;
    }

    public String getDrugHospId() {
        return drugHospId;
    }

    public void setDrugHospId(String drugHospId) {
        this.drugHospId = drugHospId;
    }

    public String getStrDrugType() throws Exception{
        if(StringUtils.isNotBlank(this.getDrugType())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_DRUGTYPE, this.getDrugType());
            if(value!=null) {
                return value.getCodeTitle();
            }
        }
        return "";
    }

    public String getStrPeriod() throws Exception{
        if(StringUtils.isNotBlank(this.getDrugPeriod())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_PERIOD, this.getDrugPeriod());
            if(value!=null) {
                return value.getCodeTitle();
            }
        }
        return "";
    }

    public String getStrTakeTime() throws Exception{
        if(StringUtils.isNotBlank(this.getDrugTaking())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_TAKING, this.getDrugTaking());
            if(value!=null) {
                return value.getCodeTitle();
            }
        }
        return "";
    }

    public String getStrUsage() throws Exception{
        if(StringUtils.isNotBlank(this.getDrugUsage())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_USAGE, this.getDrugUsage());
            if(value!=null) {
                return value.getCodeTitle();
            }
        }
        return "";
    }

    public String getStrFrequency() throws Exception{
        if(StringUtils.isNotBlank(this.getDrugFrequency())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_FREQUENCY, this.getDrugFrequency());
            if(value!=null) {
                return value.getCodeTitle();
            }
        }
        return "";
    }

    public String getStrDrugPharmAcology() throws Exception{
        if(StringUtils.isNotBlank(this.getDrugPharmAcology())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_PHARMACOLOGY, this.getDrugPharmAcology());
            if(value!=null) {
                return value.getCodeTitle();
            }
        }
        return "";
    }

    public String getStrDrugDosageUnit() throws Exception{
        if(StringUtils.isNotBlank(this.getDrugDosageUnit())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_DRUGDOSAGEUNIT, this.getDrugDosageUnit());
            if(value!=null) {
                return value.getCodeTitle();
            }
        }
        return "";
    }

    public String getStrDrugDrUserName(){
        if(StringUtils.isNotBlank(this.getDrugDrId())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            AppDrUser drUser = (AppDrUser) dao.getServiceDo().find(AppDrUser.class,this.getDrugDrId());
            if(drUser != null){
                return  drUser.getDrName();
            }
        }
        return "";
    }

    public String getDrugPeriodOther() {
        return drugPeriodOther;
    }

    public void setDrugPeriodOther(String drugPeriodOther) {
        this.drugPeriodOther = drugPeriodOther;
    }

    public String getDrugFrequencyOther() {
        return drugFrequencyOther;
    }

    public void setDrugFrequencyOther(String drugFrequencyOther) {
        this.drugFrequencyOther = drugFrequencyOther;
    }

    public Calendar getDrugAddTime() {
        return drugAddTime;
    }

    public void setDrugAddTime(Calendar drugAddTime) {
        this.drugAddTime = drugAddTime;
    }

    /**
     * 获取时间
     * @return
     */
    public String getStrDrugAddTime(){
        if(this.getDrugAddTime()!=null){
            return ExtendDate.getYMD_h_m_s(this.getDrugAddTime());
        }
        return "";
    }
}
