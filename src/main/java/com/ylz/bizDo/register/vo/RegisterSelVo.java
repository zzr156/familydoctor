package com.ylz.bizDo.register.vo;

import com.ylz.packaccede.util.CardUtil;
import com.ylz.packcommon.common.CommConditionVo;
import com.ylz.packcommon.common.util.AgeUtil;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class RegisterSelVo {
    private String id;//
    private String signNum;//签约编号
    private String name;//姓名
    private String age;//年龄
    private String patientIdno;//身份证
    private String tel;//联系电话
    private String signsJjType;//人口经济性质
    private String signlx;//医保类型 1医保 2 农合
    private String signDate;//签约时间
    private String signState;//签约状态
    private String signczpay;//财政补贴
    private String signzfpay;//自费
    private String signDrId;//签约医生主键
    private String signDrName;//签约医生
    private String signDrCode;//医生编号

    private String sex;//性别 1男 2女
    private String patientCard;//社保卡
    private String signType;//1家庭签约 2 vip
    private String signPersGroup;//服务人群
    private String signPayState;//缴费状态 1已缴费 0：未缴费
    private String batchOperatorName;
    private String signTeamName;
    private String patientAddress;
    private String signgwpay;//公卫
    private String signybpay;//医保

    private String patientId;//患者主键
    private String teamId;//团队主键

    private String signPers1;//服务人群筛查
    private String signPers2;
    private String signPers3;
    private String signPers4;
    private String signPers5;
    private String signPers6;
    private String signPers7;
    private String signPers8;
    private String signPers9;

    private String operatorId;//操作员id
    private String operatorName;//操作员姓名
    private String operationDepart;//操作科室
    private String hospitalId;//医院ID
    private String batchId;//批次ID
    private String setMealId;//套餐ID
    private String signDateStart;//签约有效开始时间
    private String signDateEnd;//签约有效结束时间
    private String signWebState;//web的签约状态

    public String getSignWebState() {
        return signWebState;
    }

    public void setSignWebState(String signWebState) {
        this.signWebState = signWebState;
    }

    public String getSignDrCode() {
        return signDrCode;
    }

    public void setSignDrCode(String signDrCode) {
        this.signDrCode = signDrCode;
    }

    public String getSignDateStart() {
        return signDateStart;
    }

    public void setSignDateStart(String signDateStart) {
        this.signDateStart = signDateStart;
    }

    public String getSignDateEnd() {
        return signDateEnd;
    }

    public void setSignDateEnd(String signDateEnd) {
        this.signDateEnd = signDateEnd;
    }

    public String getSetMealId() {
        return setMealId;
    }

    public void setSetMealId(String setMealId) {
        this.setMealId = setMealId;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public String getSignTeamName() {
        return signTeamName;
    }

    public void setSignTeamName(String signTeamName) {
        this.signTeamName = signTeamName;
    }

    public String getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(String patientAddress) {
        this.patientAddress = patientAddress;
    }

    public String getBatchOperatorName() {
        return batchOperatorName;
    }

    public void setBatchOperatorName(String batchOperatorName) {
        this.batchOperatorName = batchOperatorName;
    }

    public String getSigngwpay() {
        return signgwpay;
    }

    public void setSigngwpay(String signgwpay) {
        this.signgwpay = signgwpay;
    }

    public String getSignybpay() {
        return signybpay;
    }

    public void setSignybpay(String signybpay) {
        this.signybpay = signybpay;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getAge() {
        return age;
    }

    public void setAge(String age) throws Exception{
        Map<String,Object> idNos = new HashMap<String,Object>();
        if(StringUtils.isNotBlank(this.getPatientIdno())){
            if(this.getPatientIdno().length() == 18){
                idNos = CardUtil.getCarInfo(this.getPatientIdno());
            }else{
                idNos = CardUtil.getCarInfo15W(this.getPatientIdno());
            }
            String birthday = idNos.get("birthday").toString();
            age = AgeUtil.getAgeYear(ExtendDate.getCalendar(birthday));
        }
        this.age = age;
    }



    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getSignsJjType() {
        return signsJjType;
    }

    public void setSignsJjType(String signsJjType) {
        if(StringUtils.isNotBlank(signsJjType)){
            if(signsJjType.equals("1")){
                this.signsJjType = "一般人口";
            }
            if(signsJjType.equals("2")){
                this.signsJjType = "建档立卡贫困人口";
            }
            if(signsJjType.equals("3")){
                this.signsJjType = "低保户";
            }
            if(signsJjType.equals("4")){
                this.signsJjType = "特困户（五保户）";
            }
            if(signsJjType.equals("5")){
                this.signsJjType = "计生特殊家庭";
            }
        }else{
            this.signsJjType = "";
        }

    }

    public String getSignlx() {
        return signlx;
    }

    public void setSignlx(String signlx) {
        if(StringUtils.isNotBlank(signlx)){
            if(signlx.equals("1")) {
                this.signlx = "医保";
            }
            if(signlx.equals("2")) {
                this.signlx = "农合";
            }
            if(signlx.equals("3")) {
                this.signlx = "未参保";
            }
        }else {
            this.signlx = "";
        }
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(Timestamp signDate) {
        if(signDate != null) {
            this.signDate = ExtendDate.getYMD(signDate);
        }
    }

    public String getSignState() {
        return signState;
    }

    public void setSignState(String signState) {
        if(StringUtils.isNotBlank(signState)) {
            if(signState.equals("0")){
                this.signState = "预签约";
            }
            if(signState.equals("1")){
                this.signState = "待签约";
            }
            if(signState.equals("2")){
                this.signState = "已签约";
            }
            if(signState.equals("3")){
                this.signState = "解约中";
            }
            if(signState.equals("4")){
                this.signState = "已解约";
            }
            if(signState.equals("5")){
                this.signState = "改签解约中";
            }
            if(signState.equals("6")){
                this.signState = "改签申请中";
            }
            if(signState.equals("7")){
                this.signState = "已退约";
            }
            if(signState.equals("8")){
                this.signState = "拒签";
            }
            if(signState.equals("9")){
                this.signState = "删除";
            }
        }else{
            this.signState = "";
        }
    }

    public String getSignczpay() {
        return signczpay;
    }

    public void setSignczpay(String signczpay) {
        this.signczpay = signczpay;
    }

    public String getSignzfpay() {
        return signzfpay;
    }

    public void setSignzfpay(String signzfpay) {
        this.signzfpay = signzfpay;
    }

    public String getSignDrId() {
        return signDrId;
    }

    public void setSignDrId(String signDrId) {
        this.signDrId = signDrId;
    }

    public String getSignDrName() {
        return signDrName;
    }

    public void setSignDrName(String signDrName) {
        this.signDrName = signDrName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        if(StringUtils.isNotBlank(sex)){
            if(sex.equals("1")) {
                this.sex = "男";
            }
            if(sex.equals("2")) {
                this.sex = "女";
            }
        }else {
            this.sex = "";
        }

    }

    public String getPatientCard() {
        return patientCard;
    }

    public void setPatientCard(String patientCard) {
        this.patientCard = patientCard;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSignPersGroup() {
        return signPersGroup;
    }

    public void setSignPersGroup(String signPersGroup) {
        if(StringUtils.isNotBlank(signPersGroup)){
            String[] value = signPersGroup.split(",");
            String signvalue ="";
            for(int i=0;i<value.length;i++){
                if(value[i].equals("5")){
                    signvalue =signvalue+"001;";
                }else if(value[i].equals("6")){
                    signvalue = signvalue+"002;";
                }
            }
            if(StringUtils.isBlank(signvalue)){
                this.signPersGroup = "999;";
            }else{
                this.signPersGroup =signvalue;
            }
        }else{
            this.signPersGroup = "";
        }

    }

    public String getSignPayState() {
        return signPayState;
    }

    public void setSignPayState(String signPayState) {
        this.signPayState = signPayState;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getSignPers1() {
        return signPers1;
    }

    public void setSignPers1(String signPers1) {
        this.signPers1 = signPers1;
    }

    public String getSignPers2() {
        return signPers2;
    }

    public void setSignPers2(String signPers2) {
        this.signPers2 = signPers2;
    }

    public String getSignPers3() {
        return signPers3;
    }

    public void setSignPers3(String signPers3) {
        this.signPers3 = signPers3;
    }

    public String getSignPers4() {
        return signPers4;
    }

    public void setSignPers4(String signPers4) {
        this.signPers4 = signPers4;
    }

    public String getSignPers5() {
        return signPers5;
    }

    public void setSignPers5(String signPers5) {
        this.signPers5 = signPers5;
    }

    public String getSignPers6() {
        return signPers6;
    }

    public void setSignPers6(String signPers6) {
        this.signPers6 = signPers6;
    }

    public String getSignPers7() {
        return signPers7;
    }

    public void setSignPers7(String signPers7) {
        this.signPers7 = signPers7;
    }

    public String getSignPers8() {
        return signPers8;
    }

    public void setSignPers8(String signPers8) {
        this.signPers8 = signPers8;
    }

    public String getSignPers9() {
        return signPers9;
    }

    public void setSignPers9(String signPers9) {
        this.signPers9 = signPers9;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperationDepart() {
        return operationDepart;
    }

    public void setOperationDepart(String operationDepart) {
        this.operationDepart = operationDepart;
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }
}
