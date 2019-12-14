package com.ylz.bizDo.jtapp.basicHealthEntity.InfectiousReport;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * 传染病报告表
 * @author hp
 *
 */
public class T_im_refDTO {
	@JsonView({IFindGroup.class})
    private String imRefId;//编号

    private String brickh;//IC卡号

    private String brid00;//病人id

    @JsonView({IFindGroup.class})
    private String ghh000;//挂号号

    @JsonView({IFindGroup.class})
    private String brzjbh;//病人证件编号

    @JsonView({IFindGroup.class})
    private String brdh00;//病人电话

    @JsonView({IFindGroup.class})
    private String dfId;//居民档案号

    private String icd100;//ICD_10码

    @JsonView({IFindGroup.class})
    private String jbmc00;//疾病名称

    @JsonView({IFindGroup.class})
    private String refIpName;//传染病名称

    @JsonView({IFindGroup.class})
    private String imCrblb;//传染病类别（1--甲类，2--乙类，3--丙类）

    @JsonView({IFindGroup.class})
    private String imSickdate;//发病日期(格式：yyyymmdd)

    @JsonView({IFindGroup.class})
    private String imDiagnosedate;//诊断日期(格式：yyyymmdd)

    @JsonView({IFindGroup.class})
    private String imCureresult;//治疗结果

    @JsonView({IFindGroup.class})
    private String imReportco;//报告单位

    @JsonView({IFindGroup.class})
    private String imCrbzdzt;//传染病诊断状态（1--疑似病例，2--临床诊断病例，3--实验室确诊病例，4--病原携带者）

    @JsonView({IFindGroup.class})
    private String imCrbfblb;//传染病发病类别（1--急性，2--慢性）

    @JsonView({IFindGroup.class})
    private String imDoctor;//报告医生员工编号

    @JsonView({IFindGroup.class})
    private String imBeizhu;//备注

    @JsonView({IFindGroup.class})
    private String imLogdate;//报卡日期(格式：yyyymmdd)

    public String getImRefId() {
        return imRefId;
    }

    public void setImRefId(String imRefId) {
        this.imRefId = imRefId == null ? null : imRefId.trim();
    }

    public String getBrickh() {
        return brickh;
    }

    public void setBrickh(String brickh) {
        this.brickh = brickh == null ? null : brickh.trim();
    }

    public String getBrid00() {
        return brid00;
    }

    public void setBrid00(String brid00) {
        this.brid00 = brid00;
    }

    public String getGhh000() {
        return ghh000;
    }

    public void setGhh000(String ghh000) {
        this.ghh000 = ghh000 == null ? null : ghh000.trim();
    }

    public String getBrzjbh() {
        return brzjbh;
    }

    public void setBrzjbh(String brzjbh) {
        this.brzjbh = brzjbh == null ? null : brzjbh.trim();
    }

    public String getBrdh00() {
        return brdh00;
    }

    public void setBrdh00(String brdh00) {
        this.brdh00 = brdh00 == null ? null : brdh00.trim();
    }

    public String getDfId() {
        return dfId;
    }

    public void setDfId(String dfId) {
        this.dfId = dfId == null ? null : dfId.trim();
    }

    public String getIcd100() {
        return icd100;
    }

    public void setIcd100(String icd100) {
        this.icd100 = icd100 == null ? null : icd100.trim();
    }

    public String getJbmc00() {
        return jbmc00;
    }

    public void setJbmc00(String jbmc00) {
        this.jbmc00 = jbmc00 == null ? null : jbmc00.trim();
    }

    public String getRefIpName() {
        return refIpName;
    }

    public void setRefIpName(String refIpName) {
        this.refIpName = refIpName == null ? null : refIpName.trim();
    }

    public String getImCrblb() {
        return imCrblb;
    }

    public void setImCrblb(String imCrblb) {
        this.imCrblb = imCrblb == null ? null : imCrblb.trim();
    }

    public String getImSickdate() {
        return imSickdate;
    }

    public void setImSickdate(String imSickdate) {
        this.imSickdate = imSickdate == null ? null : imSickdate.trim();
    }

    public String getImDiagnosedate() {
        return imDiagnosedate;
    }

    public void setImDiagnosedate(String imDiagnosedate) {
        this.imDiagnosedate = imDiagnosedate == null ? null : imDiagnosedate.trim();
    }

    public String getImCureresult() {
        return imCureresult;
    }

    public void setImCureresult(String imCureresult) {
        this.imCureresult = imCureresult == null ? null : imCureresult.trim();
    }

    public String getImReportco() {
        return imReportco;
    }

    public void setImReportco(String imReportco) {
        this.imReportco = imReportco == null ? null : imReportco.trim();
    }

    public String getImCrbzdzt() {
        return imCrbzdzt;
    }

    public void setImCrbzdzt(String imCrbzdzt) {
        this.imCrbzdzt = imCrbzdzt == null ? null : imCrbzdzt.trim();
    }

    public String getImCrbfblb() {
        return imCrbfblb;
    }

    public void setImCrbfblb(String imCrbfblb) {
        this.imCrbfblb = imCrbfblb == null ? null : imCrbfblb.trim();
    }

    public String getImDoctor() {
        return imDoctor;
    }

    public void setImDoctor(String imDoctor) {
        this.imDoctor = imDoctor;
    }

    public String getImBeizhu() {
        return imBeizhu;
    }

    public void setImBeizhu(String imBeizhu) {
        this.imBeizhu = imBeizhu == null ? null : imBeizhu.trim();
    }

    public String getImLogdate() {
        return imLogdate;
    }

    public void setImLogdate(String imLogdate) {
        this.imLogdate = imLogdate == null ? null : imLogdate.trim();
    }

    public static interface IFindGroup{}
}