package com.ylz.bizDo.app.vo;

import com.ylz.packaccede.util.CardUtil;
import com.ylz.packcommon.common.comEnum.SignFormType;
import com.ylz.packcommon.common.util.AgeUtil;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class AppSignVo {
    private String id;//
    private String signNum;//签约编号
    private String name;//姓名
    private String age;//年龄
    private String patientIdno;//身份证
    private String patientJmda;//居民建康档案号
    private String tel;//联系电话
    private String signsJjType;//人口经济性质
    private String signlx;//医保类型 1医保 2 农合
    private String signDate;//签约时间
    private String signFromDate;//协议开始时间
    private String signToDateOther;//协议结束时间
    private String signState;//签约状态
    private String signczpay;//财政补贴
    private String signzfpay;//自费
    private String signDrId;//签约医生主键
    private String signDrName;//签约医生
    private String patientjmda;// 居民档案
    private String patientjtda;// 家庭档案

    private String sex;//性别 1男 2女
    private String patientCard;//社保卡
    private String signType;//1家庭签约 2 vip
    private String signPersGroup;//服务人群
    private String signPayState;//缴费状态 1已缴费 0：未缴费
    private String batchOperatorName;
    private String batchOperatorId;
    private String signTeamName;
    private String patientAddress;
    private String signgwpay;//公卫
    private String signybpay;//医保
    private BigInteger signQyCount;
    private String signToDate;// 时间区间
    private String signSurrenderDate ; // 解约时间
    private String signUrrenderReason ; // 解约原因
    private String signDelType ; // 删除 1 死亡 2 其他
    private String signDelReason ; // 删除原因
    private String signDieDate ; //  死亡时间
    private String signDelDate ;  // 删除时间
    private String signOperatorName;// 基卫操作人
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
    private String signareacode;
    private String contractCode;

    private String packageId;//套餐Id
    private String sersmName;//名称
    private String renewDate;//续签时间
    private String patientNeighborhoodCommittee;//村所
    private String signHealthReportState;

    private String signGoToSignState;//续签状态（2代表已经续签，0是未续签）
    private String batchChangeOperatorName;//变更操作人姓名
    private String batchChangeOperatorId;//变更操作人id

    public String getSignGoToSignState() {
        return signGoToSignState;
    }

    public void setSignGoToSignState(String signGoToSignState) {
        this.signGoToSignState = signGoToSignState;
    }

    public String getSignHealthReportState() {
        return signHealthReportState;
    }

    public void setSignHealthReportState(String signHealthReportState) {
        this.signHealthReportState = signHealthReportState;
    }

    public String getRenewDate() {
        return renewDate;
    }

    public void setRenewDate(String renewDate) {
        this.renewDate = renewDate;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getSersmName() {
        return sersmName;
    }

    public void setSersmName(String sersmName) {
        this.sersmName = sersmName;
    }

    public String getUpHpis() {
        return upHpis;
    }

    public void setUpHpis(String upHpis) {
        if(StringUtils.isNotBlank(upHpis)){
            if(SignFormType.APPSTATE.getValue().equals(upHpis)) {
                this.upHpis = "APP签约";
            }
            if(SignFormType.WEBSTATE.getValue().equals(upHpis)) {
                this.upHpis = "WEB签约";
            }
            if(SignFormType.WECHATSTATE.getValue().equals(upHpis)) {
                this.upHpis = "微信签约";
            }
            if(SignFormType.YITIJISTATE.getValue().equals(upHpis)){
                this.upHpis = "一体机签约";
            }
            if(SignFormType.POSSTATE.getValue().equals(upHpis)){
                this.upHpis = "POS机签约";
            }
        }else {
            this.upHpis = "";
        }
    }

    private String upHpis;//签约来源：1、WEB签约 2、WEB家签 3、微信签约

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

    public String getPatientJmda() {
        return patientJmda;
    }

    public void setPatientJmda(String patientJmda) {
        this.patientJmda = patientJmda;
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
                    boolean idno = isNumeric(this.getPatientIdno());
                    if(idno){
                    idNos = CardUtil.getCarInfo15W(this.getPatientIdno());
                    }else{
                        age = "";
                    }
                }
                if(idNos.get("birthday") != null){
                    String birthday = idNos.get("birthday").toString();
                    age = AgeUtil.getAgeYear(ExtendDate.getCalendar(birthday));
                }

        }
        this.age = age;
    }

    public  boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
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
       /* if(StringUtils.isNotBlank(signsJjType)){
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
        }*/
        this.signsJjType = signsJjType;
    }

    public String getSignlx() {
        return signlx;
    }

    public void setSignlx(String signlx) {
        if(StringUtils.isNotBlank(signlx)){
            if(signlx.equals("0")) {
                this.signlx = "省医保";
            }
            if(signlx.equals("1")) {
                this.signlx = "市医保";
            }
            if(signlx.equals("2")) {
                this.signlx = "农合";
            }
            if(signlx.equals("3")) {
                this.signlx = "未参保";
            }
            if(signlx.equals("4")) {
                this.signlx = "居民医保";
            }
            if(signlx.equals("5")) {
                this.signlx = "职工医保";
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
        }else{
            this.signDate = "";
        }
    }

    public String getSignFromDate() {
        return signFromDate;
    }

    public void setSignFromDate(Timestamp signFromDate) {
        if(signFromDate != null){
            this.signFromDate = ExtendDate.getYMD(signFromDate);
        }else{
            this.signFromDate = "";
        }
    }

    public String getSignToDateOther() {
        return signToDateOther;
    }

    public void setSignToDateOther(Timestamp signToDateOther) {
        if(signToDateOther != null){
            this.signToDateOther = ExtendDate.getYMD(signToDateOther);
        }else{
            this.signToDateOther = "";
        }

    }

    public String getSignState() {
        return signState;
    }

    public void setSignState(String signState) {
        String payState =this.signPayState;
        if(StringUtils.isNotEmpty(signareacode)){
            String code =this.signareacode.substring(0,4);
            if(StringUtils.isNotBlank(signState)) {
                if(signState.equals("0")){
                    if(code.equals("3503")){
                        this.signState ="未交互";
                    }else{
                        if(StringUtils.isNotBlank(payState)){
                            if(payState.equals("1")){
                                this.signState = "已签约已缴费"; // 还未与医保交互 但是服务包的钱给了
                            }else {
                                this.signState = "已签约未缴费"; // 还未与医保交互
                            }
                        }else{
                            this.signState = "已签约未缴费"; // 还未与医保交互
                        }

                    }

                }
                if(signState.equals("1")){
                    this.signState = "待签约";
                }
                if(signState.equals("2")){
                    if(code.equals("3503")){
                        this.signState ="已交互";
                    }else {
                        if (StringUtils.isNotBlank(payState)) {
                            if (payState.equals("1")) {
                                this.signState = "已签约已缴费 "; // 与医保交互完 但是服务包的钱给了
                            } else {
                                this.signState = "已签约未缴费 "; // 与医保交互完
                            }
                        } else {
                            this.signState = "已签约未缴费 "; // 与医保交互完
                        }
                    }
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
                if(signState.equals("98")){
                    this.signState = "预签约";
                }
            }else{
                this.signState = "";
            }
        }else {
            if(StringUtils.isNotEmpty(signState)){
                this.signState = signState;
            }else {
                this.signState = "";
            }
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
        this.signPersGroup = signPersGroup;
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

    public String getSignToDate() {
        return signToDate;
    }

    public void setSignToDate(String signToDate) {
        if(signToDate != null) {
            this.signToDate = signToDate;
        }
    }

    public String getSignareacode() {
        return signareacode;
    }

    public void setSignareacode(String signareacode) {
        this.signareacode = signareacode;
    }


    public String getPatientjmda() {
        return patientjmda;
    }

    public void setPatientjmda(String patientjmda) {
        this.patientjmda = patientjmda;
    }

    public String getPatientjtda() {
        return patientjtda;
    }

    public void setPatientjtda(String patientjtda) {
        this.patientjtda = patientjtda;
    }

    public String getSignSurrenderDate() {
        return signSurrenderDate;
    }

    public void setSignSurrenderDate(Timestamp signSurrenderDate) { // 当正常解约 时间为空 去取删除死亡解约
        if(signSurrenderDate != null) {
            this.signSurrenderDate = ExtendDate.getYMD(signSurrenderDate);
        }else {
            this.signSurrenderDate = this.signDelDate;
        }
        // this.signSurrenderDate = signSurrenderDate;
    }

    public String getSignUrrenderReason() {
        return signUrrenderReason;
    }

    public void setSignUrrenderReason(String signUrrenderReason) {
        if(StringUtils.isNotBlank(signUrrenderReason)){
            this.signUrrenderReason = signUrrenderReason;
        }else{
            this.signUrrenderReason= this.signDelReason;
        }

    }

    public String getSignDelType() {
        return signDelType;
    }

    public void setSignDelType(String signDelType) {
        this.signDelType = signDelType;
    }

    public String getSignDelReason() {
        return signDelReason;
    }

    public void setSignDelReason(String signDelReason) {
        this.signDelReason = signDelReason;
    }

    public String getSignDieDate() {
        return signDieDate;
    }

    public void setSignDieDate(Timestamp signDieDate) {
        if(signDieDate != null) {
            this.signDieDate = ExtendDate.getYMD(signDieDate);
        }
    }

    public String getSignDelDate() {
        return signDelDate;
    }

    public void setSignDelDate(Timestamp signDelDate) {
        if(signDelDate != null) {
            this.signDelDate = ExtendDate.getYMD(signDelDate);
        }
    }

    public String getSignOperatorName() {
        return signOperatorName;
    }

    public void setSignOperatorName(String signOperatorName) {
        this.signOperatorName = signOperatorName;
    }

    public BigInteger getSignQyCount() {
        return signQyCount;
    }

    public void setSignQyCount(BigInteger signQyCount) {
        this.signQyCount = signQyCount;
    }

	public String getContractCode() {
		return contractCode;
	}

	public void setContractCode(String contractCode) {
		this.contractCode = contractCode;
	}

    public String getPatientNeighborhoodCommittee() {
        return patientNeighborhoodCommittee;
    }

    public void setPatientNeighborhoodCommittee(String patientNeighborhoodCommittee) {
        this.patientNeighborhoodCommittee = patientNeighborhoodCommittee;
    }

    public String getBatchOperatorId() {
        return batchOperatorId;
    }

    public void setBatchOperatorId(String batchOperatorId) {
        this.batchOperatorId = batchOperatorId;
    }

    public String getBatchChangeOperatorName() {
        return batchChangeOperatorName;
    }

    public void setBatchChangeOperatorName(String batchChangeOperatorName) {
        this.batchChangeOperatorName = batchChangeOperatorName;
    }

    public String getBatchChangeOperatorId() {
        return batchChangeOperatorId;
    }

    public void setBatchChangeOperatorId(String batchChangeOperatorId) {
        this.batchChangeOperatorId = batchChangeOperatorId;
    }
}
