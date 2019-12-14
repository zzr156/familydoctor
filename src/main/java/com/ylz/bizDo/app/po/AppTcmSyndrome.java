package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/** 中医体质辨识记录表
 * Created by zzl on 2017/8/4.
 */
@Entity
@Table(name = "APP_TCM_SYNDROME")
public class AppTcmSyndrome extends BasePO {
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
    @Column(name = "TCM_QUESTION_VALUE")
    private String tcmQuestionValue;//问题
    @Column(name = "TCM_CODE",length = 100)
    private String tcmCode;//编号
    @Column(name = "TCM_CHOOSE_NUM")
    private String tcmChooseNum;//选择（A,B,C,D,E)
    @Column(name = "TCM_SCORE",length = 100)
    private String tcmScode;//得分
    @Column(name = "TCM_USER_ID",length = 36)
    private String tcmUserId;//患者id
    @Column(name = "TCM_USER_NAME",length = 50)
    private String tcmUserName;//患者姓名
    @Column(name = "TCM_CREATE_TIME")
    private Calendar tcmCreateTime;//填表时间
    @Column(name = "TCM_DR_ID",length = 36)
    private String tcmDrId;//医生id
    @Column(name = "TCM_DR_NAME",length = 50)
    private String tcmDrName;//医生姓名
    @Column(name = "TCM_TYPE",length = 10)
    private String tcmType;//类型（1患者自测 2医生检测）
    @Column(name = "UPLOAD_STATE",length = 10)
    private String uploadState="0";//是否已上传到基卫（0未上传,1已上传（上传成功）,2基卫数据）

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTcmQuestionValue() {
        return tcmQuestionValue;
    }

    public void setTcmQuestionValue(String tcmQuestionValue) {
        this.tcmQuestionValue = tcmQuestionValue;
    }

    public String getTcmCode() {
        return tcmCode;
    }

    public void setTcmCode(String tcmCode) {
        this.tcmCode = tcmCode;
    }

    public String getTcmChooseNum() {
        return tcmChooseNum;
    }

    public void setTcmChooseNum(String tcmChooseNum) {
        this.tcmChooseNum = tcmChooseNum;
    }

    public String getTcmScode() {
        return tcmScode;
    }

    public void setTcmScode(String tcmScode) {
        this.tcmScode = tcmScode;
    }

    public String getTcmUserId() {
        return tcmUserId;
    }

    public void setTcmUserId(String tcmUserId) {
        this.tcmUserId = tcmUserId;
    }

    public String getTcmUserName() {
        return tcmUserName;
    }

    public void setTcmUserName(String tcmUserName) {
        this.tcmUserName = tcmUserName;
    }

    public Calendar getTcmCreateTime() {
        return tcmCreateTime;
    }

    public void setTcmCreateTime(Calendar tcmCreateTime) {
        this.tcmCreateTime = tcmCreateTime;
    }

    public String getTcmDrId() {
        return tcmDrId;
    }

    public void setTcmDrId(String tcmDrId) {
        this.tcmDrId = tcmDrId;
    }

    public String getTcmDrName() {
        return tcmDrName;
    }

    public void setTcmDrName(String tcmDrName) {
        this.tcmDrName = tcmDrName;
    }

    public String getTcmType() {
        return tcmType;
    }

    public void setTcmType(String tcmType) {
        this.tcmType = tcmType;
    }

    public String getUploadState() {
        return uploadState;
    }

    public void setUploadState(String uploadState) {
        this.uploadState = uploadState;
    }
}
