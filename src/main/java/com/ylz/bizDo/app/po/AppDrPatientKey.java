package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**
 * 医生患者账号TOKEN
 */
@Entity
@Table(name = "APP_DR_PATIENT_KEY")
//@org.hibernate.annotations.Cache(usage= CacheConcurrencyStrategy.READ_WRITE)//要使用hibernate的二级缓存Cache的注释
public class AppDrPatientKey extends BasePO {

	// Fields
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
	private String id;//主键
	@Column(name = "DR_TOKEN", length = 100)
	private String drToken;//token
	@Column(name = "DR_PATIENT_ID", length = 36)
	private String drPatientId;//医生患者主键
	@Column(name = "DR_PATIENT_TOKEN_EFFECTIVE_TIME", length = 10)
	private Calendar drPatientTokenEffectiveTime;//token有效时间
	@Column(name = "DR_PATIENT_DEVICE", length = 100)
	private String drPatientDevice;//手机设备号
	@Column(name = "DR_PATIENT_TYPE", length = 10)
	private String drPatientType;//1:患者,2医生
	@Column(name = "DR_TEMP_TOKEN", length = 100)
	private String drTempToken;//临时医生token
	@Column(name = "DR_TEMP_ID", length = 36)
	private String drTempId;//临时医生主键
	@Column(name = "DR_WECHAT_TOKEN", length = 100)
	private String drWechatToken;//微信token
	@Column(name = "DR_TV_TOKEN", length = 100)
	private String drTvToken;//智能客厅
	@Column(name = "DR_EXTERNAL_TOKEN", length = 100)
	private String drExternalToken;//对外医生token
	@Column(name = "DR_EXTERNAL_WECHAT_TOKEN", length = 100)
	private String drExternalWechatToken;//对外微信token
	@Column(name = "DR_PATIENT_LAST_DATE", length = 100)
	private Calendar drPatientLastDate;//上一次登录时间

	// Constructors

	/** default constructor */
	public AppDrPatientKey() {
	}

	/** minimal constructor */
	public AppDrPatientKey(String id) {
		this.id = id;
	}

	/** full constructor */
	public AppDrPatientKey(String id, String drToken, String drPatientId,
			Calendar drPatientTokenEffectiveTime, String drPatientDevice) {
		this.id = id;
		this.drToken = drToken;
		this.drPatientId = drPatientId;
		this.drPatientTokenEffectiveTime = drPatientTokenEffectiveTime;
		this.drPatientDevice = drPatientDevice;
	}

	// Property accessors
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDrToken() {
		return this.drToken;
	}

	public void setDrToken(String drToken) {
		this.drToken = drToken;
	}

	public String getDrPatientId() {
		return this.drPatientId;
	}

	public void setDrPatientId(String drPatientId) {
		this.drPatientId = drPatientId;
	}

	public Calendar getDrPatientTokenEffectiveTime() {
		return this.drPatientTokenEffectiveTime;
	}

	public void setDrPatientTokenEffectiveTime(Calendar drPatientTokenEffectiveTime) {
		this.drPatientTokenEffectiveTime = drPatientTokenEffectiveTime;
	}

	public String getDrPatientDevice() {
		return this.drPatientDevice;
	}

	public void setDrPatientDevice(String drPatientDevice) {
		this.drPatientDevice = drPatientDevice;
	}

	public String getDrPatientType() {
		return drPatientType;
	}

	public void setDrPatientType(String drPatientType) {
		this.drPatientType = drPatientType;
	}

	public String getDrTempToken() {
		return drTempToken;
	}

	public void setDrTempToken(String drTempToken) {
		this.drTempToken = drTempToken;
	}

	public String getDrTempId() {
		return drTempId;
	}

	public void setDrTempId(String drTempId) {
		this.drTempId = drTempId;
	}

	public String getDrWechatToken() {
		return drWechatToken;
	}

	public void setDrWechatToken(String drWechatToken) {
		this.drWechatToken = drWechatToken;
	}

	public Calendar getDrPatientLastDate() {
		return drPatientLastDate;
	}

	public void setDrPatientLastDate(Calendar drPatientLastDate) {
		this.drPatientLastDate = drPatientLastDate;
	}

	public String getDrTvToken() {
		return drTvToken;
	}

	public void setDrTvToken(String drTvToken) {
		this.drTvToken = drTvToken;
	}

	public String getDrExternalToken() {
		return drExternalToken;
	}

	public void setDrExternalToken(String drExternalToken) {
		this.drExternalToken = drExternalToken;
	}

	public String getDrExternalWechatToken() {
		return drExternalWechatToken;
	}

	public void setDrExternalWechatToken(String drExternalWechatToken) {
		this.drExternalWechatToken = drExternalWechatToken;
	}
}