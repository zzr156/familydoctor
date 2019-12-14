package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 居民标签分组(服务人群)
 */
@Entity
@Table(name = "APP_LABEL_ARCHIVING")
public class AppLabelArchiving extends BasePO {

	// Fields

	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
	private String id;//主键
	@Column(name = "LABEL_ARCHIVING_ID", length = 36)
	private String labelArchivingId;//建档立卡主键
	@Column(name = "LABEL_TEAM_ID", length = 36)
	private String labelTeamId;//团队主键
	@Column(name = "LABEL_VALUE", length = 10)
	private String labelValue;//标签值
	@Column(name = "LABEL_ID", length = 36)
	private String labelId;//标签主键
	@Column(name = "LABEL_TITLE", length = 30)
	private String labelTitle;//标签名称
	@Column(name = "LABEL_TYPE", length = 10)
	private String labelType;//标签类型
	@Column(name = "LABEL_COLOR", length = 10)
	private String labelColor;//标签颜色
	@Column(name = "LABEL_AREA_CODE", length = 100)
	private String labelAreaCode;//行政区划

	public String getLabelColor() {
		return labelColor;
	}

	public void setLabelColor(String labelColor) {
		this.labelColor = labelColor;
	}

	// Constructors

	/** default constructor */
	public AppLabelArchiving() {
	}

	/** minimal constructor */
	public AppLabelArchiving(String id) {
		this.id = id;
	}

	/** full constructor */
	public AppLabelArchiving(String id, String labelArchivingId, String labelTeamId,
                             String labelValue, String labelId, String labelTitle,
                             String labelType) {
		this.id = id;
		this.labelArchivingId = labelArchivingId;
		this.labelTeamId = labelTeamId;
		this.labelValue = labelValue;
		this.labelId = labelId;
		this.labelTitle = labelTitle;
		this.labelType = labelType;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabelArchivingId() {
		return labelArchivingId;
	}

	public void setLabelArchivingId(String labelArchivingId) {
		this.labelArchivingId = labelArchivingId;
	}

	public String getLabelTeamId() {
		return this.labelTeamId;
	}

	public void setLabelTeamId(String labelTeamId) {
		this.labelTeamId = labelTeamId;
	}

	public String getLabelValue() {
		return this.labelValue;
	}

	public void setLabelValue(String labelValue) {
		this.labelValue = labelValue;
	}

	public String getLabelId() {
		return this.labelId;
	}

	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}

	public String getLabelTitle() {
		return this.labelTitle;
	}

	public void setLabelTitle(String labelTitle) {
		this.labelTitle = labelTitle;
	}

	public String getLabelType() {
		return this.labelType;
	}

	public void setLabelType(String labelType) {
		this.labelType = labelType;
	}

	public String getLabelAreaCode() {
		return labelAreaCode;
	}

	public void setLabelAreaCode(String labelAreaCode) {
		this.labelAreaCode = labelAreaCode;
	}
}