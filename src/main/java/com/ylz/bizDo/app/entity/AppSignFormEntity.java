package com.ylz.bizDo.app.entity;



import javax.persistence.*;
import java.util.Calendar;

/**签约单
 * AppSignform entity. @author MyEclipse Persistence Tools
 */

public class AppSignFormEntity  {


	private String id;
	private String signNum;//签约编号
	private String signPatientId;//签约成员主键
	private int signPatientAge;//签约成员年龄
	private String signPatientGender;//签约成员性别
	private String signPatientIdNo;//签约成员身份证
	private Calendar signDate;//签约时间
	private Calendar signFromDate;//有效开始时间
	private Calendar signToDate;//有效结束时间
	private String signState;//签约状态 0:预签约 1:待签约 2:已签约 3:解约中 4:已解约 5:改签解约中 6:改签申请中 7:已退约 8拒签;9删除
	private String signPayState;//缴费状态 1已缴费 0：未缴费
	private Calendar signSurrenderDate;//解约时间
	private String signUrrenderReason;//解约原因
	private String signUrrenderReasonPatient;//患者解约原因
	private String signOthnerReason;//拒签原因
	private String signType;//签约类型 //1家庭签约 2 vip
	private String signPersGroup;//居民分组 服务人群
	private String signHealthGroup;//健康分布
	private String signBatchId;//签约批次主键
	private String signDrId;//签约医生主键
	private String signTeamId;//签约团队主键
	private String signTeamName;//签约团队名称
	private String signHospId;//医院主键
	private String signAreaCode;//行政编码
	private String signWay; //签约方式 0代表自己 1代表家人代签 2代表医生代签

	//增值服务包 分为 三师共管 和 居家养老
	private String signServiceA;//三师共管 0:预签约 1:待签约 2:已签约 3:解约中 4:已解约 5:改签解约中 6:改签申请中 7:已退约 8拒签 9签约到期
	private Calendar signServiceADate;//三师共管签约时间
	private String signServiceAPayState;//三师共管缴费状态 1已缴费 0：未缴费
	//三师共管缴费订单号，暂未设置

	private String signServiceB;//居家养老 1:待签约 2:已签约 3:解约中 4:已解约 5:改签解约中 6:改签申请中 7:已退约 8拒签
	private Calendar signServiceBDate;//居家养老签约时间
	private String signServiceBPayState;//居家养老缴费状态 1已缴费 0：未缴费
	private String signServiceBColor;//居家养老 颜色
	//居家养老缴费订单号，暂未设置
	private String signContractState="0";//是否已续约 1是 0否
	private String signGreenState="0";//是否发送过绿级提醒 1是 0否
	private String signYellowState="0";//是否发送过黄级提醒 1是0否
	private String signRedState="0";//是否发送过红级提醒 1是0否
	private String signPayType;//支付类型(1,全部贴,2未补贴,3社医保,4.统筹补贴)
	private String signFee;//签约费用
	private String signsJjType;//经济类型；
	private String upHpis;//是否上传基卫 /1已上传 2数据来源web

	//删除原因
	private String signDelType;//删除类型 1,死亡,2其他
	private String signDelReason;//删除原因
	private Calendar signDieDate;//死亡时间
	private Calendar signDelDate;//删除时间
	private String signChangeState = "0";//变更状态
	private String signChangeDr;//变更医生id
	private String signChangeTeam;//变更团队.
	private Calendar signChangeDate;//变更时间

	private String signRenewState="0";//是否发送医生续约提醒
	private String signGoToSignState="0";//(1转签 2续签)
	private String signGotoSignType;//转签类型（1医生转签，2团队以上类型转签）

	/**
	 * web家签新增字段
	 * @return
	 */

	private String signWebState;//签约状态 1:预签约，数据保存到数据库 2：签约成功，数据传输到医保，3：签约失败，数据传输到医保失败
		private String signDrAssistantId;//签约助理医生主键
	private String signlx;//签约类型
	private String signczpay;//财政补贴
	private String signzfpay;//自费
	private String signtext;//补充协议内容
	private String signpackageid;//特色服务包id
	private String yuser; // 医保账号
	private String ypaw; //医保密码

	private String code;
	private String msg;

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

	public String getSignPatientId() {
		return signPatientId;
	}

	public void setSignPatientId(String signPatientId) {
		this.signPatientId = signPatientId;
	}

	public int getSignPatientAge() {
		return signPatientAge;
	}

	public void setSignPatientAge(int signPatientAge) {
		this.signPatientAge = signPatientAge;
	}

	public String getSignPatientGender() {
		return signPatientGender;
	}

	public void setSignPatientGender(String signPatientGender) {
		this.signPatientGender = signPatientGender;
	}

	public String getSignPatientIdNo() {
		return signPatientIdNo;
	}

	public void setSignPatientIdNo(String signPatientIdNo) {
		this.signPatientIdNo = signPatientIdNo;
	}

	public Calendar getSignDate() {
		return signDate;
	}

	public void setSignDate(Calendar signDate) {
		this.signDate = signDate;
	}

	public Calendar getSignFromDate() {
		return signFromDate;
	}

	public void setSignFromDate(Calendar signFromDate) {
		this.signFromDate = signFromDate;
	}

	public Calendar getSignToDate() {
		return signToDate;
	}

	public void setSignToDate(Calendar signToDate) {
		this.signToDate = signToDate;
	}

	public String getSignState() {
		return signState;
	}

	public void setSignState(String signState) {
		this.signState = signState;
	}

	public String getSignPayState() {
		return signPayState;
	}

	public void setSignPayState(String signPayState) {
		this.signPayState = signPayState;
	}

	public Calendar getSignSurrenderDate() {
		return signSurrenderDate;
	}

	public void setSignSurrenderDate(Calendar signSurrenderDate) {
		this.signSurrenderDate = signSurrenderDate;
	}

	public String getSignUrrenderReason() {
		return signUrrenderReason;
	}

	public void setSignUrrenderReason(String signUrrenderReason) {
		this.signUrrenderReason = signUrrenderReason;
	}

	public String getSignUrrenderReasonPatient() {
		return signUrrenderReasonPatient;
	}

	public void setSignUrrenderReasonPatient(String signUrrenderReasonPatient) {
		this.signUrrenderReasonPatient = signUrrenderReasonPatient;
	}

	public String getSignOthnerReason() {
		return signOthnerReason;
	}

	public void setSignOthnerReason(String signOthnerReason) {
		this.signOthnerReason = signOthnerReason;
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

	public String getSignHealthGroup() {
		return signHealthGroup;
	}

	public void setSignHealthGroup(String signHealthGroup) {
		this.signHealthGroup = signHealthGroup;
	}

	public String getSignBatchId() {
		return signBatchId;
	}

	public void setSignBatchId(String signBatchId) {
		this.signBatchId = signBatchId;
	}

	public String getSignDrId() {
		return signDrId;
	}

	public void setSignDrId(String signDrId) {
		this.signDrId = signDrId;
	}

	public String getSignTeamId() {
		return signTeamId;
	}

	public void setSignTeamId(String signTeamId) {
		this.signTeamId = signTeamId;
	}

	public String getSignTeamName() {
		return signTeamName;
	}

	public void setSignTeamName(String signTeamName) {
		this.signTeamName = signTeamName;
	}

	public String getSignHospId() {
		return signHospId;
	}

	public void setSignHospId(String signHospId) {
		this.signHospId = signHospId;
	}

	public String getSignAreaCode() {
		return signAreaCode;
	}

	public void setSignAreaCode(String signAreaCode) {
		this.signAreaCode = signAreaCode;
	}

	public String getSignWay() {
		return signWay;
	}

	public void setSignWay(String signWay) {
		this.signWay = signWay;
	}

	public String getSignServiceA() {
		return signServiceA;
	}

	public void setSignServiceA(String signServiceA) {
		this.signServiceA = signServiceA;
	}

	public Calendar getSignServiceADate() {
		return signServiceADate;
	}

	public void setSignServiceADate(Calendar signServiceADate) {
		this.signServiceADate = signServiceADate;
	}

	public String getSignServiceAPayState() {
		return signServiceAPayState;
	}

	public void setSignServiceAPayState(String signServiceAPayState) {
		this.signServiceAPayState = signServiceAPayState;
	}

	public String getSignServiceB() {
		return signServiceB;
	}

	public void setSignServiceB(String signServiceB) {
		this.signServiceB = signServiceB;
	}

	public Calendar getSignServiceBDate() {
		return signServiceBDate;
	}

	public void setSignServiceBDate(Calendar signServiceBDate) {
		this.signServiceBDate = signServiceBDate;
	}

	public String getSignServiceBPayState() {
		return signServiceBPayState;
	}

	public void setSignServiceBPayState(String signServiceBPayState) {
		this.signServiceBPayState = signServiceBPayState;
	}

	public String getSignServiceBColor() {
		return signServiceBColor;
	}

	public void setSignServiceBColor(String signServiceBColor) {
		this.signServiceBColor = signServiceBColor;
	}

	public String getSignContractState() {
		return signContractState;
	}

	public void setSignContractState(String signContractState) {
		this.signContractState = signContractState;
	}

	public String getSignGreenState() {
		return signGreenState;
	}

	public void setSignGreenState(String signGreenState) {
		this.signGreenState = signGreenState;
	}

	public String getSignYellowState() {
		return signYellowState;
	}

	public void setSignYellowState(String signYellowState) {
		this.signYellowState = signYellowState;
	}

	public String getSignRedState() {
		return signRedState;
	}

	public void setSignRedState(String signRedState) {
		this.signRedState = signRedState;
	}

	public String getSignPayType() {
		return signPayType;
	}

	public void setSignPayType(String signPayType) {
		this.signPayType = signPayType;
	}

	public String getSignFee() {
		return signFee;
	}

	public void setSignFee(String signFee) {
		this.signFee = signFee;
	}

	public String getSignsJjType() {
		return signsJjType;
	}

	public void setSignsJjType(String signsJjType) {
		this.signsJjType = signsJjType;
	}

	public String getUpHpis() {
		return upHpis;
	}

	public void setUpHpis(String upHpis) {
		this.upHpis = upHpis;
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

	public Calendar getSignDieDate() {
		return signDieDate;
	}

	public void setSignDieDate(Calendar signDieDate) {
		this.signDieDate = signDieDate;
	}

	public Calendar getSignDelDate() {
		return signDelDate;
	}

	public void setSignDelDate(Calendar signDelDate) {
		this.signDelDate = signDelDate;
	}

	public String getSignChangeState() {
		return signChangeState;
	}

	public void setSignChangeState(String signChangeState) {
		this.signChangeState = signChangeState;
	}

	public String getSignChangeDr() {
		return signChangeDr;
	}

	public void setSignChangeDr(String signChangeDr) {
		this.signChangeDr = signChangeDr;
	}

	public String getSignChangeTeam() {
		return signChangeTeam;
	}

	public void setSignChangeTeam(String signChangeTeam) {
		this.signChangeTeam = signChangeTeam;
	}

	public Calendar getSignChangeDate() {
		return signChangeDate;
	}

	public void setSignChangeDate(Calendar signChangeDate) {
		this.signChangeDate = signChangeDate;
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

	public String getSignGotoSignType() {
		return signGotoSignType;
	}

	public void setSignGotoSignType(String signGotoSignType) {
		this.signGotoSignType = signGotoSignType;
	}

	public String getSignWebState() {
		return signWebState;
	}

	public void setSignWebState(String signWebState) {
		this.signWebState = signWebState;
	}

	public String getSignDrAssistantId() {
		return signDrAssistantId;
	}

	public void setSignDrAssistantId(String signDrAssistantId) {
		this.signDrAssistantId = signDrAssistantId;
	}

	public String getSignlx() {
		return signlx;
	}

	public void setSignlx(String signlx) {
		this.signlx = signlx;
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

	public String getSigntext() {
		return signtext;
	}

	public void setSigntext(String signtext) {
		this.signtext = signtext;
	}

	public String getSignpackageid() {
		return signpackageid;
	}

	public void setSignpackageid(String signpackageid) {
		this.signpackageid = signpackageid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getYuser() {
		return yuser;
	}

	public void setYuser(String yuser) {
		this.yuser = yuser;
	}

	public String getYpaw() {
		return ypaw;
	}

	public void setYpaw(String ypaw) {
		this.ypaw = ypaw;
	}
}