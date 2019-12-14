package com.ylz.bizDo.jtapp.drEntity;

import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packaccede.util.CardUtil;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.util.AgeUtil;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zzl on 2017/10/20.
 */
public class AppSignFormEntity {
    private String signFormId;//签约单
    private String signState;//签约状态 1:待签约 2:已签约 3:解约中 4:已解约 5:改签解约中 6:改签申请中 7:已退约
    private String patientName;//签约人员姓名
    private String patientGender;//性别
    private String patientAge;//年龄
    private String patientId;//患者主键
    private String patientImageurl;//头像
    private String teamHospName;//医院名称 签约机构
    private String teamName;//团队名称
    private String teamId;//团队id 查看协议单使用
    private String signFromDate;//有效开始时间
    private String signToDate;//有效结束时间
    private String signWay;//签约方式 0代表自己 1代表家人代签 2代表医生代签
    //医生端
    private String signDate;////签约时间
    private String patientIdno;//身份证号
    private String patientCard;//社保卡
    private String patientTel;//电话
    private String drName;//医生名称
    private String fwImageName;//服务图片名称
    private String fwb;//服务包
    private String signRenewState;//是否发送医生续约提醒（0否 1是）
    private String signGoToSignState;//0未续签或转签 1转签 2续签

    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public String getPatientIdno() {
        return patientIdno;
    }

    public void setPatientIdno(String patientIdno) {
        this.patientIdno = patientIdno;
    }

    public String getPatientCard() {
        return patientCard;
    }

    public void setPatientCard(String patientCard) {
        this.patientCard = patientCard;
    }

    public String getPatientTel() {
        return patientTel;
    }

    public void setPatientTel(String patientTel) {
        this.patientTel = patientTel;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(Timestamp signDate) {
        if(signDate!=null) {
            this.signDate = ExtendDate.getYMD(signDate);
        }
    }

    public String getSignFromDate() {
        return signFromDate;
    }

    public void setSignFromDate(Timestamp signFromDate) {
        if(signFromDate!=null) {
            this.signFromDate = ExtendDate.getYMD(signFromDate);
        }
    }

    public String getSignToDate() {
        return signToDate;
    }

    public void setSignToDate(Timestamp signToDate) {
        if(signToDate!=null) {
            this.signToDate = ExtendDate.getYMD(signToDate);
        }
    }

    public String getSignFormId() {
        return signFormId;
    }

    public void setSignFormId(String signFormId) {
        this.signFormId = signFormId;
    }

    public String getSignState() {
        return signState;
    }

    public void setSignState(String signState) {
        this.signState = signState;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientImageurl() {
        return patientImageurl;
    }

    public void setPatientImageurl(String patientImageurl) {
        this.patientImageurl = patientImageurl;
    }

    public String getTeamHospName() {
        return teamHospName;
    }

    public void setTeamHospName(String teamHospName) {
        this.teamHospName = teamHospName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getSignWay() {
        return signWay;
    }

    public void setSignWay(String signWay) {
        this.signWay = signWay;
    }

    public String getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(String patientGender) throws Exception {
        if(StringUtils.isNotBlank(patientGender)){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CODESEX, patientGender);
            if(value!=null) {
                this.patientGender = value.getCodeTitle();
            }

        }

    }

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(String patientAge) throws Exception{
        Map<String,Object> idNos = new HashMap<String,Object>();
        if(StringUtils.isNotBlank(this.getPatientIdno())){
            if(this.getPatientIdno().length() == 18){
                idNos = CardUtil.getCarInfo(this.getPatientIdno());
            }else{
                idNos = CardUtil.getCarInfo15W(this.getPatientIdno());
            }
            String birthday = idNos.get("birthday").toString();
            String age = AgeUtil.getAgeYear(ExtendDate.getCalendar(birthday));
            patientAge = age;
        }
        this.patientAge = patientAge;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getFwImageName() {
        return fwImageName;
    }

    public void setFwImageName(String fwImageName) {
        this.fwImageName = fwImageName;
    }

    public String getFwb() {
        return fwb;
    }

    public void setFwb(String fwb) {
        this.fwb = fwb;
    }

    public String getSignRenewState() {
        return signRenewState;
    }

    public void setSignRenewState(String signRenewState) {
        this.signRenewState = signRenewState;
    }

    public String getSignGoToSignState() {
        return signGoToSignState;
    }

    public void setSignGoToSignState(String signGoToSignState) {
        this.signGoToSignState = signGoToSignState;
    }
}
