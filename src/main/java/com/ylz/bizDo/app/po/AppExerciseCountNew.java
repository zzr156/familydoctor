package com.ylz.bizDo.app.po;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.ylz.packcommon.common.bean.BasePO;

/**
 * 履约统计(新)
 */
@Entity
@Table(name = "APP_EXERCISE_COUNT_NEW")
public class AppExerciseCountNew extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** ID */
	@Id
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "ID")
	private String id;

	/** 服务包ID,多个用,隔开 */
	@Column(name = "SIGN_PACKAGEID")
	private String signPackageId;

	/** 服务对象属性,多个用,隔开 */
	@Column(name = "LABEL_VALUE")
	private String labelValue;

	/** 签约数量 */
	@Column(name = "SIGN_NUMBER")
	private String signNumber;

	/** 签约地区 */
	@Column(name = "SIGN_AREA_CODE")
	private String signAreaCode;

	/** 年份 */
	@Column(name = "SIGN_YEAR")
	private String signYear;

	/** 签约医生ID */
	@Column(name = "SIGN_DR_ID")
	private String signDrId;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSignPackageId() {
		return signPackageId;
	}

	public void setSignPackageId(String signPackageId) {
		this.signPackageId = signPackageId;
	}

	public String getLabelValue() {
		return labelValue;
	}

	public void setLabelValue(String labelValue) {
		this.labelValue = labelValue;
	}

	public String getSignNumber() {
		return signNumber;
	}

	public void setSignNumber(String signNumber) {
		this.signNumber = signNumber;
	}

	public String getSignAreaCode() {
		return signAreaCode;
	}

	public void setSignAreaCode(String signAreaCode) {
		this.signAreaCode = signAreaCode;
	}

	public String getSignYear() {
		return signYear;
	}

	public void setSignYear(String signYear) {
		this.signYear = signYear;
	}

	public String getSignDrId() {
		return signDrId;
	}

	public void setSignDrId(String signDrId) {
		this.signDrId = signDrId;
	}

}