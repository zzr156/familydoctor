package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**用户血压信息表
 * AppUserBloodpressure entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "APP_USER_BLOODPRESSURE")
public class AppUserBloodpressure extends BasePO {

	// Fields
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
	private String id;
	@Column(name = "UP_TEL", length = 11)
	private String upTel;//电话
	@Column(name = "UP_IMEI", length = 50)
	private String upImei;//设备编号
	@Column(name = "UP_USER", length = 2)
	private String upUser;//用户 1爸爸 2妈妈
	@Column(name = "UP_SYS", length = 3)
	private Integer upSys;//高压
	@Column(name = "UP_DIA", length = 3)
	private Integer upDia;//低压
	@Column(name = "UP_PUL", length = 3)
	private Integer upPul;//心跳次数/分钟
	@Column(name = "UP_ANO", length = 1)
	private String upAno;//心率 0齐 1不齐
	@Column(name = "UP_TIME", length = 19)
	private Calendar upTime;//时间
	@Column(name = "UP_STATE", length = 2)
	private String upState;//状态
	@Column(name = "UP_USER_ID", length = 36)
	private String upUserId;//血压用户id
	@Column(name = "UP_WARNING_STATE", length = 1)
	private String upWarningState;//预警消息
	@Column(name = "UP_SYMPTOM", length = 6)
	private String upSymptom;//症状
	@Column(name = "UP_NOTE", length = 1000)
	private String upNote;//备注
	@Column(name = "UP_ICCID", length = 50)
	private String upIccid;//设备过来的数据
	@Column(name = "UP_IMSI", length = 50)
	private String upImsi;//设备过来的数据
	@Column(name = "SOURCE_TYPE",length = 10)
	private String sourceType;//来源类型（1app 2智能设备 3随访 4门诊）
	@Column(name = "ABNORMAL_STATE",length = 10)
	private String abnormalState = "0";//异常状态 1异常

	// Constructors


	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUpTel() {
		return this.upTel;
	}

	public void setUpTel(String upTel) {
		this.upTel = upTel;
	}


	public String getUpImei() {
		return upImei;
	}

	public void setUpImei(String upImei) {
		this.upImei = upImei;
	}

	public String getUpUser() {
		return this.upUser;
	}

	public void setUpUser(String upUser) {
		this.upUser = upUser;
	}


	public Integer getUpSys() {
		return this.upSys;
	}

	public void setUpSys(Integer upSys) {
		this.upSys = upSys;
	}


	public Integer getUpDia() {
		return this.upDia;
	}

	public void setUpDia(Integer upDia) {
		this.upDia = upDia;
	}


	public Integer getUpPul() {
		return this.upPul;
	}

	public void setUpPul(Integer upPul) {
		this.upPul = upPul;
	}


	public String getUpAno() {
		return this.upAno;
	}

	public void setUpAno(String upAno) {
		this.upAno = upAno;
	}


	public Calendar getUpTime() {
		return this.upTime;
	}

	public void setUpTime(Calendar upTime) {
		this.upTime = upTime;
	}


	public String getUpState() {
		return this.upState;
	}

	public void setUpState(String upState) {
		this.upState = upState;
	}


	public String getUpUserId() {
		return this.upUserId;
	}

	public void setUpUserId(String upUserId) {
		this.upUserId = upUserId;
	}


	public String getUpWarningState() {
		return this.upWarningState;
	}

	public void setUpWarningState(String upWarningState) {
		this.upWarningState = upWarningState;
	}

	public String getUpSymptom() {
		return upSymptom;
	}

	public void setUpSymptom(String upSymptom) {
		this.upSymptom = upSymptom;
	}

	public String getUpNote() {
		return upNote;
	}

	public void setUpNote(String upNote) {
		this.upNote = upNote;
	}

	public String getUpIccid() {
		return upIccid;
	}

	public void setUpIccid(String upIccid) {
		this.upIccid = upIccid;
	}

	public String getUpImsi() {
		return upImsi;
	}

	public void setUpImsi(String upImsi) {
		this.upImsi = upImsi;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getAbnormalState() {
		return abnormalState;
	}

	public void setAbnormalState(String abnormalState) {
		this.abnormalState = abnormalState;
	}
}