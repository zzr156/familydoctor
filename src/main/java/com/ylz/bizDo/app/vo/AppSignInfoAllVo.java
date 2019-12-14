package com.ylz.bizDo.app.vo;

import com.ylz.packcommon.common.util.ExtendDate;

import java.sql.Timestamp;

/**
 * Created by TroubleMan-Cxw on 2017-12-07.
 */
public class AppSignInfoAllVo {
    private String signNum;//签约编号
    private String name;//姓名
    private String patientIdno;//身份证
    private String signDate;//签约时间
    private String ptsscno; //医疗证号
    private String patientCard;//社保卡
    private String signFromDate;//签约开始时间
    private String signToDate;// 签约截止时间
    private String operatorName;// 签约操作人

    public String getSignNum() {
        return signNum;
    }

    public void setSignNum(String signNum) {
        this.signNum = signNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatientIdno() {
        return patientIdno;
    }

    public void setPatientIdno(String patientIdno) {
        this.patientIdno = patientIdno;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(Timestamp signDate) {
        if(signDate != null) {
            this.signDate = ExtendDate.getYMD(signDate);
        }
    }

    public String getPatientCard() {
        return patientCard;
    }

    public void setPatientCard(String patientCard) {
        this.patientCard = patientCard;
    }

    public String getSignFromDate() {
        return signFromDate;
    }

    public void setSignFromDate(Timestamp signFromDate) {
        if(signFromDate != null) {
            this.signFromDate = ExtendDate.getYMD(signFromDate);
        }
    }

    public String getSignToDate() {
        return signToDate;
    }

    public void setSignToDate(Timestamp signToDate) {
        if(signToDate != null) {
            this.signToDate = ExtendDate.getYMD(signToDate);
        }
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getPtsscno() {
        return ptsscno;
    }

    public void setPtsscno(String ptsscno) {
        this.ptsscno = ptsscno;
    }
}
