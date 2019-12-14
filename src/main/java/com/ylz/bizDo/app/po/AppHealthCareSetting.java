package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 *保健提醒天数设置表
 */
@Entity
@Table(name = "APP_HAELTHCARE_SETTING")
public class AppHealthCareSetting extends BasePO {

	// Fields
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
	private String id;
	@Column(name = "HC_PATIENT_ID", length = 36)
	private String hcPatientId;//用户id
	@Column(name = "HC_REMINGD_DAY", length = 6)
	private String hcRemindDay;//提前提醒天数
	@Column(name = "HC_ENABLE", length = 6)
	private String hcEnable;//是否启用。1启用，0禁止

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHcPatientId() {
		return hcPatientId;
	}

	public void setHcPatientId(String hcPatientId) {
		this.hcPatientId = hcPatientId;
	}

	public String getHcRemindDay() {
		return hcRemindDay;
	}

	public void setHcRemindDay(String hcRemindDay) {
		this.hcRemindDay = hcRemindDay;
	}

	public String getHcEnable() {
		return hcEnable;
	}

	public void setHcEnable(String hcEnable) {
		this.hcEnable = hcEnable;
	}
}