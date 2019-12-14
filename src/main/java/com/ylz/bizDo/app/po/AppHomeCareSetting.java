package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**
 * 居家养老频率设置
 */
@Entity
@Table(name = "APP_HOME_CARE_SETTING")
public class AppHomeCareSetting extends BasePO {

	// Fields
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
	private String id;//主键
	@Column(name = "HOME_MANAGE_LEVEL", nullable = false, length = 10)
	private String homeManageLevel;//预警等级
	@Column(name = "HOME_MANAGE_STYLE", length = 10)
	private String homeManageStyle;//预警方式
	@Column(name = "HOME_MANAGE_CYCLE", length = 10)
	private String homeManageCycle;//预警周期
	@Column(name = "HOME_MANAGE_FREQUENCY", length = 10)
	private String homeManageFrequency;//频次
	@Column(name = "HOME_MANAGE_PEMINDER_DAYS", length = 10)
	private String homeManagePeminderDays;//预警天数
	@Column(name = "HOME_TEAM_ID", length = 36)
	private String homeTeamId;//团队主键
	@Column(name = "HOME_HOSP_ID", length = 36)
	private String homeHospId;//医院主键
	@Column(name = "HOME_CREATE_TIME")
	private Calendar homeCrateTime;//创建时间
	// Constructors

	/** default constructor */
	public AppHomeCareSetting() {
	}

	/** minimal constructor */
	public AppHomeCareSetting(String id, String homeManageLevel) {
		this.id = id;
		this.homeManageLevel = homeManageLevel;
	}

	/** full constructor */
	public AppHomeCareSetting(String id, String homeManageLevel,
			String homeManageStyle, String homeManageCycle,String homeManageFrequency,
			String homeManagePeminderDays, String homeTeamId, String homeHospId,Calendar homeCrateTime) {
		this.id = id;
		this.homeManageLevel = homeManageLevel;
		this.homeManageStyle = homeManageStyle;
		this.homeManageCycle = homeManageCycle;
		this.homeManageFrequency = homeManageFrequency;
		this.homeManagePeminderDays = homeManagePeminderDays;
		this.homeTeamId = homeTeamId;
		this.homeHospId = homeHospId;
		this.homeCrateTime = homeCrateTime;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHomeManageLevel() {
		return this.homeManageLevel;
	}

	public void setHomeManageLevel(String homeManageLevel) {
		this.homeManageLevel = homeManageLevel;
	}

	public String getHomeManageStyle() {
		return this.homeManageStyle;
	}

	public void setHomeManageStyle(String homeManageStyle) {
		this.homeManageStyle = homeManageStyle;
	}

	public String getHomeManageCycle() {
		return this.homeManageCycle;
	}

	public void setHomeManageCycle(String homeManageCycle) {
		this.homeManageCycle = homeManageCycle;
	}

	public String getHomeManagePeminderDays() {
		return this.homeManagePeminderDays;
	}

	public void setHomeManagePeminderDays(String homeManagePeminderDays) {
		this.homeManagePeminderDays = homeManagePeminderDays;
	}

	public String getHomeTeamId() {
		return this.homeTeamId;
	}

	public void setHomeTeamId(String homeTeamId) {
		this.homeTeamId = homeTeamId;
	}

	public String getHomeHospId() {
		return this.homeHospId;
	}

	public void setHomeHospId(String homeHospId) {
		this.homeHospId = homeHospId;
	}

	public String getHomeManageFrequency() {
		return homeManageFrequency;
	}

	public void setHomeManageFrequency(String homeManageFrequency) {
		this.homeManageFrequency = homeManageFrequency;
	}

	public Calendar getHomeCrateTime() {
		return homeCrateTime;
	}

	public void setHomeCrateTime(Calendar homeCrateTime) {
		this.homeCrateTime = homeCrateTime;
	}
}