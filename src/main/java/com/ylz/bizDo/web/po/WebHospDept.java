package com.ylz.bizDo.web.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 医院表
 */
@Entity
@Table(name = "APP_HOSP_DEPT")
public class WebHospDept extends BasePO{

	// Fields
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned")
	private String id;//主键
	@Column(name = "HOSP_NAME", length = 15)
	private String hospName;//医院名称
	@Column(name = "HOSP_CODE", length = 20)
	private String hospCode;//医院编码
	@Column(name = "HOSP_CITYAREA_ID", length = 36)
	private String hospCityareaId;//区域主键
	@Column(name = "HOSP_UPCITYAREA_ID", length = 36)
	private String hospUpcityareaId;//区域上级主键
	@Column(name = "HOSP_AREA_CODE", length = 20)
	private String hospAreaCode;//区域编码
	@Column(name = "HOSP_ADDRESS", length = 50)
	private String hospAddress;//医院地址
	@Column(name = "HOSP_TEL", length = 15)
	private String hospTel;//医院电话
	@Column(name = "HOSP_INTRO", length = 65535)
	private String hospIntro;//医院简介
	@Column(name = "HOSP_IMAGEURL", length = 100)
	private String hospImageurl;//医院图片
	@Column(name = "HOSP_LEVEL", length = 10)
	private String hospLevel;//等级 1省,2市,3区,4..社区
	@Column(name = "HOSP_STATE", length = 10)
	private String hospState;// 状态
	@Column(name = "HOSP_PAGE_STYLE", length = 6)
	private String hospPageStyle;//页面风格
	@Column(name = "HOSP_LEVEL_TYPE",length = 10)
	private String hospLevelType="7";//上级医院：0综合医院 1专科医院 2中医医院 3中西医结合医院 4民族医医院 5康复医院 6妇幼保健院  基层医院：7社区卫生服务中心（站） 8中心卫生院 9乡镇卫生院

	// Constructors

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHospName() {
		return this.hospName;
	}

	public void setHospName(String hospName) {
		this.hospName = hospName;
	}

	public String getHospCode() {
		return this.hospCode;
	}

	public void setHospCode(String hospCode) {
		this.hospCode = hospCode;
	}

	public String getHospCityareaId() {
		return this.hospCityareaId;
	}

	public void setHospCityareaId(String hospCityareaId) {
		this.hospCityareaId = hospCityareaId;
	}

	public String getHospUpcityareaId() {
		return this.hospUpcityareaId;
	}

	public void setHospUpcityareaId(String hospUpcityareaId) {
		this.hospUpcityareaId = hospUpcityareaId;
	}

	public String getHospAreaCode() {
		return this.hospAreaCode;
	}

	public void setHospAreaCode(String hospAreaCode) {
		this.hospAreaCode = hospAreaCode;
	}

	public String getHospAddress() {
		return this.hospAddress;
	}

	public void setHospAddress(String hospAddress) {
		this.hospAddress = hospAddress;
	}

	public String getHospTel() {
		return this.hospTel;
	}

	public void setHospTel(String hospTel) {
		this.hospTel = hospTel;
	}

	public String getHospIntro() {
		return this.hospIntro;
	}

	public void setHospIntro(String hospIntro) {
		this.hospIntro = hospIntro;
	}

	public String getHospImageurl() {
		return this.hospImageurl;
	}

	public void setHospImageurl(String hospImageurl) {
		this.hospImageurl = hospImageurl;
	}

	public String getHospLevel() {
		return this.hospLevel;
	}

	public void setHospLevel(String hospLevel) {
		this.hospLevel = hospLevel;
	}

	public String getHospState() {
		return this.hospState;
	}

	public void setHospState(String hospState) {
		this.hospState = hospState;
	}

	public String getHospPageStyle() {
		return this.hospPageStyle;
	}

	public void setHospPageStyle(String hospPageStyle) {
		this.hospPageStyle = hospPageStyle;
	}

	public String getHospLevelType() {
		return hospLevelType;
	}

	public void setHospLevelType(String hospLevelType) {
		this.hospLevelType = hospLevelType;
	}
}