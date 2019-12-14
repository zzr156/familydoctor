package com.ylz.bizDo.jtapp.commonEntity;

import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;

/**
 * Created by lintingjie on 2017/6/21.
 */
public class AppDrugEntity {

    private String id;//药品id
    private String drugName;//药品名称
    private String drugType;//药品类型
    private String drugPeriod;//服用周期
    private String drugFrequency;//服用频次
    private String drugUsage;//服用方法
    private String drugTaking;//服用时段
    private String drugNote;//备注
    private String drugPeriodOther;//周期其他
    private String drugFrequencyOther;//频次其他
    private String drugPharmAcology;//药理
    private String drugSpec;//规格
    private String drugBatch;//批号
    private String drugUseLevel;//用量
    private String drugDosageUnit;//用量单位

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getDrugType() {
        return drugType;
    }

    public void setDrugType(String drugType) {
        this.drugType = drugType;
    }

    public String getDrugPeriod() {
        return drugPeriod;
    }

    public void setDrugPeriod(String drugPeriod) {
        this.drugPeriod = drugPeriod;
    }

    public String getDrugFrequency() {
        return drugFrequency;
    }

    public void setDrugFrequency(String drugFrequency) {
        this.drugFrequency = drugFrequency;
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

    public String getDrugNote() {
        return drugNote;
    }

    public void setDrugNote(String drugNote) {
        this.drugNote = drugNote;
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

    public String getDrugPharmAcology() {
        return drugPharmAcology;
    }

    public void setDrugPharmAcology(String drugPharmAcology) throws Exception {
        if(StringUtils.isNotBlank(drugPharmAcology)){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_PHARMACOLOGY,drugPharmAcology);
            if(value != null) {
                this.drugPharmAcology = value.getCodeTitle();
            }
        }
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

    /**
     * 服用周期名称
     * @return
     */
    public String getStrDrugPeriod() throws Exception{
        if(StringUtils.isNotBlank(this.getDrugPeriod())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_PERIOD,this.getDrugPeriod());
            if(value != null) {
                return value.getCodeTitle();
            }
        }
        return "";
    }

    /**
     * 服用频次名称
     * @return
     */
    public String getStrDrugFrequency() throws Exception{
        if(StringUtils.isNotBlank(this.getDrugFrequency())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_FREQUENCY,this.getDrugFrequency());
            if(value != null) {
                return value.getCodeTitle();
            }
        }
        return "";
    }

    /**
     * 服用方法名称
     * @return
     */
    public String getStrDrugUsage() throws Exception{
        if(StringUtils.isNotBlank(this.getDrugUsage())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_USAGE,this.getDrugUsage());
            if(value != null) {
                return value.getCodeTitle();
            }
        }
        return "";
    }

    /**
     * 服用时段名称
     * @return
     */
    public String getStrDrugTaking() throws Exception{
        if(StringUtils.isNotBlank(this.getDrugTaking())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_TAKING,this.getDrugTaking());
            if(value != null) {
                return value.getCodeTitle();
            }
        }
        return "";
    }

    /**
     * 用量单位名称
     * @return
     */
    public String getStrDrugDosageUnit() throws Exception{
        if(StringUtils.isNotBlank(this.getDrugDosageUnit())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_DRUGDOSAGEUNIT,this.getDrugDosageUnit());
            if(value != null) {
                return value.getCodeTitle();
            }
        }
        return "";
    }

    /**
     * 用药类型名称
     * @return
     */
    public String getStrDrugType() throws Exception{
        if(StringUtils.isNotBlank(this.getDrugType())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_DRUGTYPE,this.getDrugType());
            if(value != null) {
                return value.getCodeTitle();
            }
        }
        return "";
    }
}
