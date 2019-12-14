package com.ylz.bizDo.app.po;

import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**签约单
 * AppSignform entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "APP_SIGN_FORM")
public class
AppSignForm extends BasePO {

	// Fields
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
	private String id;
	@Column(name = "SIGN_NUM", nullable = false, length = 50)
	private String signNum;//签约编号
	@Column(name = "SIGN_PATIENT_ID", length = 36)
	private String signPatientId;//签约成员主键
	@Column(name = "SIGN_PATIENT_AGE", length = 20)
	private int signPatientAge;//签约成员年龄
	@Column(name = "SIGN_PATIENT_GENDER", length = 20)
	private String signPatientGender;//签约成员性别
	@Column(name = "SIGN_PATIENT_IDNO", length = 20)
	private String signPatientIdNo;//签约成员身份证
	@Column(name = "SIGN_DATE", length = 19)
	private Calendar signDate;//签约时间
	@Column(name = "SIGN_FROM_DATE", length = 10)
	private Calendar signFromDate;//有效开始时间
	@Column(name = "SIGN_TO_DATE", length = 10)
	private Calendar signToDate;//有效结束时间
	@Column(name = "SIGN_STATE", length = 6)
	private String signState;//签约状态 0:预签约 1:待签约 2:已签约 3:解约中 4:已解约 5:改签解约中 6:改签申请中 7:已退约 8拒签;9删除 10变更 11转签 12撤回 98续签 99预转签
	@Column(name = "SIGN_PAY_STATE", length = 6)
	private String signPayState;//缴费状态 1已缴费 0：未缴费,2已登记
	@Column(name = "SIGN_SURRENDER_DATE", length = 19)
	private Calendar signSurrenderDate;//解约时间
	@Column(name = "SIGN_URRENDER_REASON", length = 100)
	private String signUrrenderReason;//解约原因
	@Column(name = "SIGN_URRENDER_REASON_PATIENT", length = 100)
	private String signUrrenderReasonPatient;//患者解约原因
	@Column(name = "SIGN_OTHNER_REASON", length = 100)
	private String signOthnerReason;//拒签原因或撤销原因
	@Column(name = "SIGN_TYPE", length = 10)
	private String signType;//签约类型 //1家庭签约 2 vip
	@Column(name = "SIGN_PERS_GROUP", length = 36)
	private String signPersGroup;//居民分组 服务人群
	@Column(name = "SIGN_HEALTH_GROUP", length = 36)
	private String signHealthGroup;//健康分布
	@Column(name = "SIGN_BATCH_ID", length = 36)
	private String signBatchId;//签约批次主键
	@Column(name = "SIGN_DR_ID", length = 36)
	private String signDrId;//签约医生主键
	@Column(name = "SIGN_TEAM_ID", length = 36)
	private String signTeamId;//签约团队主键
	@Column(name = "SIGN_TEAM_NAME", length = 200)
	private String signTeamName;//签约团队名称
	@Column(name = "SIGN_HOSP_ID", length = 36)
	private String signHospId;//医院主键
	@Column(name = "SIGN_AREA_CODE", length = 200)
	private String signAreaCode;//行政编码
	@Column(name = "SIGN_WAY", length = 6)
	private String signWay; //签约方式 0代表自己(主动发起) 1代表家人代签 2代表医生代签 3 代表自己(二维码扫描)

	//增值服务包 分为 三师共管 和 居家养老
	@Column(name = "SIGN_SERVICE_A", length =6)
	private String signServiceA;//三师共管 0:预签约 1:待签约 2:已签约 3:解约中 4:已解约 5:改签解约中 6:改签申请中 7:已退约 8拒签 9签约到期
	@Column(name = "SIGN_SERVICE_A_DATE", length = 19)
	private Calendar signServiceADate;//三师共管签约时间
	@Column(name = "SIGN_SERVICE_A_PAY_STATE", length = 6)
	private String signServiceAPayState;//三师共管缴费状态 1已缴费 0：未缴费
	//三师共管缴费订单号，暂未设置

	@Column(name = "SIGN_SERVICE_B", length =6)
	private String signServiceB;//居家养老 1:待签约 2:已签约 3:解约中 4:已解约 5:改签解约中 6:改签申请中 7:已退约 8拒签
	@Column(name = "SIGN_SERVICE_B_DATE", length = 19)
	private Calendar signServiceBDate;//居家养老签约时间
	@Column(name = "SIGN_SERVICE_B_PAY_STATE", length = 6)
	private String signServiceBPayState;//居家养老缴费状态 1已缴费 0：未缴费
	@Column(name = "SIGN_SERVICE_B_COLOR", length = 36)
	private String signServiceBColor;//居家养老 颜色
	//居家养老缴费订单号，暂未设置
	@Column(name = "SIGN_CONTRACT_STATE",length = 10)
	private String signContractState="0";//是否已续约 1是 0否
	@Column(name = "SIGN_GREEN_STATE",length = 10)
	private String signGreenState="0";//是否发送过绿级提醒 1是 0否
	@Column(name = "SIGN_YELLOW_STATE",length = 10)
	private String signYellowState="0";//是否发送过黄级提醒 1是0否
	@Column(name = "SIGN_RED_STATE",length = 10)
	private String signRedState="0";//是否发送过红级提醒 1是0否
	@Column(name = "SIGN_PAY_TYPE",length = 10)
	private String signPayType;//支付类型(1,全部贴,2未补贴,3社医保,4.统筹补贴)
	@Column(name = "SIGN_FEE",length = 50)
	private String signFee;//签约费用
	@Column(name = "SIGN_JJ_TYPE",length = 30)
	private String signsJjType;//经济类型；
	@Column(name = "UP_HPIS",length = 10)
	private String upHpis;//1APP 2web 3微信 4一体机 5pos机 6导入

	//删除原因
	@Column(name = "SIGN_DEL_TYPE",length = 10)
	private String signDelType;//删除类型 1,死亡,2其他
	@Column(name = "SIGN_DEL_REASON",length = 10)
	private String signDelReason;//删除原因/死亡原因
	@Column(name = "SIGN_DIE_DATE")
	private Calendar signDieDate;//死亡时间
	@Column(name = "SIGN_DEL_DATE")
	private Calendar signDelDate;//删除时间
	@Column(name = "SIGN_CHANGE_STATE",length = 10)
	private String signChangeState = "0";//变更状态
	@Column(name = "SIGN_CHANGE_DR",length = 36)
	private String signChangeDr;//变更医生id
	@Column(name = "SIGN_CHANGE_TEAM",length = 36)
	private String signChangeTeam;//变更团队.
	@Column(name = "SIGN_CHANGE_DATE")
	private Calendar signChangeDate;//变更时间

	@Column(name = "SIGN_RENEW_STATE",length = 10)
	private String signRenewState="0";//是否有发送过医生续约提醒（0无 1有）
	@Column(name = "SIGN_GOTO_SIGN_STATE",length = 10)
	private String signGoToSignState="0";//(1转签 2续签)
	@Column(name = "SIGN_GOTO_SIGN_TYPE",length = 10)
	private String signGotoSignType;//转签类型（1医生转签，2团队以上类型转签）
	@Column(name = "SIGNATURE_IMAGE_URL",length = 150)
	private String signatureImageUrl;//签名版
	@Column(name = "SIGN_PHOTO_IMAGE_URL",length = 150)
	private String signPhotoImageUrl;//摄像头

	/**
	 * web家签新增字段Dissolution
	 * @return
	 */

	@Column(name = "SIGN_WEB_STATE", length = 6)
	private String signWebState;//签约状态 1:预签约，数据保存到数据库 2：签约成功，数据传输到医保，3：签约失败，数据传输到医保失败
	@Column(name = "SIGN_DR_ASSISTANT_ID", length = 36)
	private String signDrAssistantId;//签约助理医生主键
	@Column(name = "SIGN_LX", length = 2)
	private String signlx;//签约类型
	@Column(name = "SIGN_CZ_PAY", length = 10)
	private String signczpay;//财政补贴
	@Column(name = "SIGN_ZF_PAY", length = 10)
	private String signzfpay;//自费
	@Column(name = "SIGN_TEXT")
	private String signtext;//补充协议内容
	@Column(name = "SIGN_PACKAGEID", length = 255)
	private String signpackageid;//特色服务包id
	@Column(name = "SIGN_RENEWORGOTOSIGN_DATE")
	private Calendar signRenewOrGoToSignDate;//续签或转签时间
	@Column(name = "SIGN_PRINT_FLAG", length = 6)
	private String signPrintFlag = "0"; //协议状态 打印判断 0 未打 1 已打  2已再打
	@Column(name = "SIGN_OPERATOR_NAME", length = 100)
	private String signOperatorName ; //基卫死亡解约 操作人
	@Column(name = "ASSESS_STATE", length = 6)
	private String signAssesState ; //绩效考核状态 0未考核 1已考核
	@Column(name = "SIGN_HEALTH_REPORT_STATE", length = 2)
	private String signHealthReportState = "0";//是否生成健康报告（0未生成，1生成）
	@Column(name = "SIGN_MOBILE_TYPE",length = 2)
	private String signMobileType;//签约移动端类型（1安卓 2ios）
//	@Column(name = "SIGN_DISEASE", length = 10)
//	private Integer signDisease;//疾病类型
//	@Column(name = "SIGN_ECONOMICS", length = 10)
//	private Integer signEconomics;//经济类型
//	@Column(name = "SIGN_GROUP", length = 10)
//	private Integer signGroup;//服务人群


//	public Integer getSignDisease() {
//		return signDisease;
//	}
//
//	public void setSignDisease(Integer signDisease) {
//		this.signDisease = signDisease;
//	}
//
//	public Integer getSignEconomics() {
//		return signEconomics;
//	}
//
//	public void setSignEconomics(Integer signEconomics) {
//		this.signEconomics = signEconomics;
//	}
//
//	public Integer getSignGroup() {
//		return signGroup;
//	}
//
//	public void setSignGroup(Integer signGroup) {
//		this.signGroup = signGroup;
//	}

	public String getSignHealthReportState() {
		return signHealthReportState;
	}

	public void setSignHealthReportState(String signHealthReportState) {
		this.signHealthReportState = signHealthReportState;
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

	public String getSignDrAssistantId() {
		return signDrAssistantId;
	}

	public void setSignDrAssistantId(String signDrAssistantId) {
		this.signDrAssistantId = signDrAssistantId;
	}

	public String getUpHpis() {
		return upHpis;
	}

	public void setUpHpis(String upHpis) {
		this.upHpis = upHpis;
	}

	public String getSignsJjType() {
		return signsJjType;
	}

	public void setSignsJjType(String signsJjType) {
		this.signsJjType = signsJjType;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getSignNum() {
		return this.signNum;
	}

	public void setSignNum(String signNum) {
		this.signNum = signNum;
	}


	public String getSignPatientId() {
		return this.signPatientId;
	}

	public void setSignPatientId(String signPatientId) {
		this.signPatientId = signPatientId;
	}


	public Calendar getSignDate() {
		return this.signDate;
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
		return this.signState;
	}

	public void setSignState(String signState) {
		this.signState = signState;
	}


	public String getSignPayState() {
		return this.signPayState;
	}

	public void setSignPayState(String signPayState) {
		this.signPayState = signPayState;
	}


	public Calendar getSignSurrenderDate() {
		return this.signSurrenderDate;
	}

	public void setSignSurrenderDate(Calendar signSurrenderDate) {
		this.signSurrenderDate = signSurrenderDate;
	}


	public String getSignUrrenderReason() {
		return this.signUrrenderReason;
	}

	public void setSignUrrenderReason(String signUrrenderReason) {
		this.signUrrenderReason = signUrrenderReason;
	}


	public String getSignOthnerReason() {
		return this.signOthnerReason;
	}

	public void setSignOthnerReason(String signOthnerReason) {
		this.signOthnerReason = signOthnerReason;
	}


	public String getSignType() {
		return this.signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}


	public String getSignPersGroup() {
		return this.signPersGroup;
	}

	public void setSignPersGroup(String signPersGroup) {
		this.signPersGroup = signPersGroup;
	}


	public String getSignHealthGroup() {
		return this.signHealthGroup;
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

	public String getSignUrrenderReasonPatient() {
		return signUrrenderReasonPatient;
	}

	public void setSignUrrenderReasonPatient(String signUrrenderReasonPatient) {
		this.signUrrenderReasonPatient = signUrrenderReasonPatient;
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

	public String getSignWay() {
		return signWay;
	}

	public void setSignWay(String signWay) {
		this.signWay = signWay;
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

	public String getSignDrId() {
		return signDrId;
	}

	public void setSignDrId(String signDrId) {
		this.signDrId = signDrId;
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

	public String getSignGotoSignType() {
		return signGotoSignType;
	}

	public void setSignGotoSignType(String signGotoSignType) {
		this.signGotoSignType = signGotoSignType;
	}

	/**
	 * 签约成员名称
	 * @return
	 */
	public String getPatientName(){
		if(StringUtils.isNoneBlank(this.getSignPatientId())){
			SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
			AppPatientUser user = (AppPatientUser) dao.getServiceDo().find(AppPatientUser.class,this.getSignPatientId());
			if(user!=null){
				return user.getPatientName();
			}
		}
		return "";
	}

	/**
	 * 签约成员头像
	 * @return
	 */
	public String getImageUrl(){
		if(StringUtils.isNoneBlank(this.getSignPatientId())){
			SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
			AppPatientUser user = (AppPatientUser) dao.getServiceDo().find(AppPatientUser.class,this.getSignPatientId());
			if(user!=null){
				return user.getPatientImageurl();
			}
		}
		return "";
	}

	/**
	 * 医院名称
	 * @return
	 */
	public String getHosptName(){
		if(StringUtils.isNoneBlank(this.getSignHospId())){
			SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
			AppHospDept dept = (AppHospDept) dao.getServiceDo().find(AppHospDept.class,this.getSignHospId());
			if(dept!=null){
				return dept.getHospName();
			}
		}
		return "";
	}

	public String getSignFee() {
		return signFee;
	}

	public void setSignFee(String signFee) {
		this.signFee = signFee;
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


	public String getSignWebState() {
		return signWebState;
	}

	public void setSignWebState(String signWebState) {
		this.signWebState = signWebState;
	}

	public String getSignlx() {
		return signlx;
	}

	public void setSignlx(String signlx) {
		this.signlx = signlx;
	}

	public String getSignPatientIdNo() {
		return signPatientIdNo;
	}

	public void setSignPatientIdNo(String signPatientIdNo) {
		this.signPatientIdNo = signPatientIdNo;
	}

	public String getSignGoToSignState() {
		return signGoToSignState;
	}

	public void setSignGoToSignState(String signGoToSignState) {
		this.signGoToSignState = signGoToSignState;
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

	public Calendar getSignRenewOrGoToSignDate() {
		return signRenewOrGoToSignDate;
	}

	public void setSignRenewOrGoToSignDate(Calendar signRenewOrGoToSignDate) {
		this.signRenewOrGoToSignDate = signRenewOrGoToSignDate;
	}

	public String getSignPrintFlag() {
		return signPrintFlag;
	}

	public void setSignPrintFlag(String signPrintFlag) {
		this.signPrintFlag = signPrintFlag;
	}

	public String getSignatureImageUrl() {
		return signatureImageUrl;
	}

	public void setSignatureImageUrl(String signatureImageUrl) {
		this.signatureImageUrl = signatureImageUrl;
	}

	public String getSignOperatorName() {
		return signOperatorName;
	}

	public void setSignOperatorName(String signOperatorName) {
		this.signOperatorName = signOperatorName;
	}

	public String getSignPhotoImageUrl() {
		return signPhotoImageUrl;
	}

	public void setSignPhotoImageUrl(String signPhotoImageUrl) {
		this.signPhotoImageUrl = signPhotoImageUrl;
	}

	public String getSignAssesState() {
		return signAssesState;
	}

	public void setSignAssesState(String signAssesState) {
		this.signAssesState = signAssesState;
	}

	public String getSignMobileType() {
		return signMobileType;
	}

	public void setSignMobileType(String signMobileType) {
		this.signMobileType = signMobileType;
	}
}