package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**血糖数据
 * AppUserBloodglu entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "APP_USER_BLOODGLU")
public class AppUserBloodglu extends BasePO {

	// Fields
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
	private String id;
	@Column(name = "UG_BLOODGLU", length = 10)
	private Double ugBloodglu;//血糖值
	@Column(name = "UG_BGSTATE", length = 4)
	private String ugBgstate;//时间段 1：饭后 2饭前 （1:空腹2:早餐后3:午餐前4:午餐后5:晚餐前6:晚餐后7:睡前8:凌晨9随机）
	@Column(name = "UG_TEST_TIME", length = 19)
	private Calendar ugTestTime;//测量时间
	@Column(name = "UG_TEMPTUR", length = 5)
	private String ugTemptur;//温度值
	@Column(name = "UG_CODE", length = 10)
	private String ugCode;//设备号
	@Column(name = "UG_STATE", length = 10)
	private String ugState;//状态 1默认 0归档
	@Column(name = "UG_USER_ID", length = 36)
	private String ugUserId;//用户主键
	@Column(name = "UG_SYMPTOM", length = 6)
	private String ugSymptom;//症状
	@Column(name = "UG_NOTE", length = 500)
	private String ugNote;//备注
	@Column(name = "UG_CODE_NUM", length = 50)
	private String ugCodeNum;//血糖仪code码
	@Column(name = "SOURCE_TYPE",length = 10)
	private String sourceType;//数据来源（1app 2智能设备 3随访 4门诊 5poss 6一体机）
	@Column(name = "ABNORMAL_STATE",length = 10)
	private String abnormalState = "0";//异常状态 1异常

	// Constructors

	/** default constructor */
	public AppUserBloodglu() {
	}

	/** minimal constructor */
	public AppUserBloodglu(String id) {
		this.id = id;
	}

	/** full constructor */
	public AppUserBloodglu(String id, Double ugBloodglu,
			String ugBgstate, Calendar ugTestTime, String ugTemptur,
			String ugCode, String ugState, String ugUserId) {
		this.id = id;
		this.ugBloodglu = ugBloodglu;
		this.ugBgstate = ugBgstate;
		this.ugTestTime = ugTestTime;
		this.ugTemptur = ugTemptur;
		this.ugCode = ugCode;
		this.ugState = ugState;
		this.ugUserId = ugUserId;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Double getUgBloodglu() {
		return this.ugBloodglu;
	}

	public void setUgBloodglu(Double ugBloodglu) {
		this.ugBloodglu = ugBloodglu;
	}


	public String getUgBgstate() {
		return this.ugBgstate;
	}

	public void setUgBgstate(String ugBgstate) {
		this.ugBgstate = ugBgstate;
	}


	public Calendar getUgTestTime() {
		return this.ugTestTime;
	}

	public void setUgTestTime(Calendar ugTestTime) {
		this.ugTestTime = ugTestTime;
	}


	public String getUgTemptur() {
		return this.ugTemptur;
	}

	public void setUgTemptur(String ugTemptur) {
		this.ugTemptur = ugTemptur;
	}


	public String getUgCode() {
		return this.ugCode;
	}

	public void setUgCode(String ugCode) {
		this.ugCode = ugCode;
	}


	public String getUgState() {
		return this.ugState;
	}

	public void setUgState(String ugState) {
		this.ugState = ugState;
	}


	public String getUgUserId() {
		return this.ugUserId;
	}

	public void setUgUserId(String ugUserId) {
		this.ugUserId = ugUserId;
	}

	public String getUgSymptom() {
		return ugSymptom;
	}

	public void setUgSymptom(String ugSymptom) {
		this.ugSymptom = ugSymptom;
	}

	public String getUgNote() {
		return ugNote;
	}

	public void setUgNote(String ugNote) {
		this.ugNote = ugNote;
	}

	public String getUgCodeNum() {
		return ugCodeNum;
	}

	public void setUgCodeNum(String ugCodeNum) {
		this.ugCodeNum = ugCodeNum;
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