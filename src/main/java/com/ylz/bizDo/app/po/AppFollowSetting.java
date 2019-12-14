package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**随访当天预警
 * AppWorkdaySetting entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "APP_FOLLOW_SETTING")
public class AppFollowSetting extends BasePO {

	// Fields
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
	private String id;
	@Column(name = "FS_DOCTOR_ID", length = 36)
	private String fsDoctorId;//医生id
	@Column(name = "FS_STATE", length = 6)
	private String fsState;//预警开启状态 0禁用 1启用
	@Column(name = "FS_MORNING", length = 50)
	private String fsMorning;//早上提醒时间
	@Column(name = "FS_NOON" ,length = 10)
	private String fsNoon;//中午提醒时间
	@Column(name = "FS_NIGHT" ,length = 10)
	private String fsNight;//晚上提醒时间

	// Constructors


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFsDoctorId() {
		return fsDoctorId;
	}

	public void setFsDoctorId(String fsDoctorId) {
		this.fsDoctorId = fsDoctorId;
	}

	public String getFsState() {
		return fsState;
	}

	public void setFsState(String fsState) {
		this.fsState = fsState;
	}

	public String getFsMorning() {
		return fsMorning;
	}

	public void setFsMorning(String fsMorning) {
		this.fsMorning = fsMorning;
	}

	public String getFsNoon() {
		return fsNoon;
	}

	public void setFsNoon(String fsNoon) {
		this.fsNoon = fsNoon;
	}

	public String getFsNight() {
		return fsNight;
	}

	public void setFsNight(String fsNight) {
		this.fsNight = fsNight;
	}
}