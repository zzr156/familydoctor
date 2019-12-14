package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**健康指导表
 * Created by zzl on 2017/6/20.
 */
@Entity
@Table(name = "APP_HEALTH_GUIDE")
public class AppHealthGuide extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name = "HG_DR_ID", length = 36)
    private String hgDrId;//指导医生
    @Column(name = "HG_PATIENT_ID", length = 36)
    private String hgPatientId;//所指导的患者
    @Column(name = "HG_TITLE", length = 200)
    private String hgTitle;//指导标题
    @Column(name = "HG_CONTENT",length = 200)
    private String hgContent;//指导内容
    @Column(name = "HG_TIME")
    private Calendar hgTime;//指导时间
    @Column(name = "HG_TYPE",length = 10)
    private String hgType;//指导类型
    @Column(name = "HG_MOD_ID", length = 36)
    private String hgModId;//指导模板id
    @Column(name = "HG_HOSP_ID",length = 36)
    private String hgHospId;//医院id
    @Column(name = "HG_AREA_CODE",length = 50)
    private String hgAreaCode;//区域编号

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHgDrId() {
        return hgDrId;
    }

    public void setHgDrId(String hgDrId) {
        this.hgDrId = hgDrId;
    }

    public String getHgPatientId() {
        return hgPatientId;
    }

    public void setHgPatientId(String hgPatientId) {
        this.hgPatientId = hgPatientId;
    }

    public String getHgTitle() {
        return hgTitle;
    }

    public void setHgTitle(String hgTitle) {
        this.hgTitle = hgTitle;
    }

    public String getHgContent() {
        return hgContent;
    }

    public void setHgContent(String hgContent) {
        this.hgContent = hgContent;
    }

    public Calendar getHgTime() {
        return hgTime;
    }

    public void setHgTime(Calendar hgTime) {
        this.hgTime = hgTime;
    }

    public String getHgType() {
        return hgType;
    }

    public void setHgType(String hgType) {
        this.hgType = hgType;
    }

    public String getHgModId() {
        return hgModId;
    }

    public void setHgModId(String hgModId) {
        this.hgModId = hgModId;
    }

    public String getHgHospId() {
        return hgHospId;
    }

    public void setHgHospId(String hgHospId) {
        this.hgHospId = hgHospId;
    }

    public String getHgAreaCode() {
        return hgAreaCode;
    }

    public void setHgAreaCode(String hgAreaCode) {
        this.hgAreaCode = hgAreaCode;
    }
}
