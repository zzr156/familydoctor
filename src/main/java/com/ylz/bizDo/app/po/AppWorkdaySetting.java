package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**工作日设置表
 * AppWorkdaySetting entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "APP_WORKDAY_SETTING")
public class AppWorkdaySetting extends BasePO {

	// Fields
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
	private String id;
	@Column(name = "WS_DR_ID", length = 36)
	private String wsDrId;//所属医生主键
	@Column(name = "WS_WORK_SHIFT", length = 50)
	private String wsWorkShift;//上班时间
	@Column(name = "WS_CLOSING_TIME", length = 50)
	private String wsClosingTime;//下班时间
	@Column(name = "WS_NIGHT_TIME", length = 50)
	private String wsNightTime;//晚上时间
	@Column(name = "WS_MON", length = 20)
	private String wsMon;//周一
	@Column(name = "WS_TUES", length = 20)
	private String wsTues;//周二
	@Column(name = "WS_WED", length = 20)
	private String wsWed;//周三
	@Column(name = "WS_THUR", length = 20)
	private String wsThur;//周四
	@Column(name = "WS_FRI", length = 20)
	private String wsFri;//周五
	@Column(name = "WS_SAT", length = 20)
	private String wsSat;//周六
	@Column(name = "WS_SUN", length = 20)
	private String wsSun;//周日
	@Column(name = "WS_CREATETIME", length = 19)
	private Calendar wsCreatetime;//创建时间

	// Constructors

	/** default constructor */
	public AppWorkdaySetting() {
	}

	/** minimal constructor */
	public AppWorkdaySetting(String id) {
		this.id = id;
	}

	/** full constructor */
	public AppWorkdaySetting(String id, String wsDrId, String wsWorkShift,
			String wsClosingTime, String wsNightTime, String wsMon,
			String wsTues, String wsWed, String wsThur, String wsFri,
			String wsSat, String wsSun, Calendar wsCreatetime) {
		this.id = id;
		this.wsDrId = wsDrId;
		this.wsWorkShift = wsWorkShift;
		this.wsClosingTime = wsClosingTime;
		this.wsNightTime = wsNightTime;
		this.wsMon = wsMon;
		this.wsTues = wsTues;
		this.wsWed = wsWed;
		this.wsThur = wsThur;
		this.wsFri = wsFri;
		this.wsSat = wsSat;
		this.wsSun = wsSun;
		this.wsCreatetime = wsCreatetime;
	}

	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}


	public String getWsDrId() {
		return this.wsDrId;
	}

	public void setWsDrId(String wsDrId) {
		this.wsDrId = wsDrId;
	}


	public String getWsWorkShift() {
		return this.wsWorkShift;
	}

	public void setWsWorkShift(String wsWorkShift) {
		this.wsWorkShift = wsWorkShift;
	}


	public String getWsClosingTime() {
		return this.wsClosingTime;
	}

	public void setWsClosingTime(String wsClosingTime) {
		this.wsClosingTime = wsClosingTime;
	}


	public String getWsNightTime() {
		return this.wsNightTime;
	}

	public void setWsNightTime(String wsNightTime) {
		this.wsNightTime = wsNightTime;
	}


	public String getWsMon() {
		return this.wsMon;
	}

	public void setWsMon(String wsMon) {
		this.wsMon = wsMon;
	}


	public String getWsTues() {
		return this.wsTues;
	}

	public void setWsTues(String wsTues) {
		this.wsTues = wsTues;
	}


	public String getWsWed() {
		return this.wsWed;
	}

	public void setWsWed(String wsWed) {
		this.wsWed = wsWed;
	}


	public String getWsThur() {
		return this.wsThur;
	}

	public void setWsThur(String wsThur) {
		this.wsThur = wsThur;
	}


	public String getWsFri() {
		return this.wsFri;
	}

	public void setWsFri(String wsFri) {
		this.wsFri = wsFri;
	}


	public String getWsSat() {
		return this.wsSat;
	}

	public void setWsSat(String wsSat) {
		this.wsSat = wsSat;
	}


	public String getWsSun() {
		return this.wsSun;
	}

	public void setWsSun(String wsSun) {
		this.wsSun = wsSun;
	}


	public Calendar getWsCreatetime() {
		return this.wsCreatetime;
	}

	public void setWsCreatetime(Calendar wsCreatetime) {
		this.wsCreatetime = wsCreatetime;
	}

}