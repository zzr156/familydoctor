package com.ylz.bizDo.app.po;

import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**
 *高血压设备
 */
@Entity
@Table(name = "APP_BLOODPRESSURE")
public class AppBloodpressure extends BasePO{

	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
	private String id;//主键
	@Column(name = "BP_DEV_ID", length = 36)
	private String bpDevId;// 设备ID
	@Column(name = "BP_ICCID", length = 36)
	private String bpIccid;//设备编码
	@Column(name = "BP_TYPE", length = 50)
	private String bpType;//型号
	@Column(name = "BP_USER_ONE", length = 36)
	private String bpUserOne;//爸爸用户
	@Column(name = "BP_USER_TWO", length = 36)
	private String bpUserTwo;//妈妈用户
	@Column(name = "BP_DEV_TIME_ONE", length = 19)
	private Calendar bpDevTimeOne;//绑定设备的时间
	@Column(name = "BP_DEV_TIME_TWO", length = 19)
	private Calendar bpDevTimeTwo;//绑定设备的时间
	@Column(name = "BP_DR_ID", length = 36)
	private String bpDrId;//医生
	@Column(name = "BP_CLOCK_ONE", length = 15)
	private String bpClockOne;// 闹钟1
	@Column(name = "BP_CLOCK_TWO", length = 15)
	private String bpClockTwo;// 闹钟2
	@Column(name = "BP_CLOCK_THR", length = 15)
	private String bpClockThr; //闹钟3
	@Column(name = "BP_CLOCK_FOUR", length = 15)
	private String bpClockFour;// 闹钟4
	@Column(name = "BP_CREATE_TIME", length = 19)
	private Calendar bpCreateTime;//创建时间
	@Column(name = "BP_PHONE", length = 15)
	private String bpPhone;// 电话
	@Column(name = "BP_TEAM_ID", length = 45)
	private String bpTeamId;//团队
	@Column(name = "BP_HOSP_ID",length = 36)
	private String bpHospId;//医院id
	@Column(name = "BP_AREA_CODE",length = 50)
	private String bpAreaCode;//医院区域编码


	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBpDevId() {
		return this.bpDevId;
	}

	public void setBpDevId(String bpDevId) {
		this.bpDevId = bpDevId;
	}

	public String getBpIccid() {
		return this.bpIccid;
	}

	public void setBpIccid(String bpIccid) {
		this.bpIccid = bpIccid;
	}

	public String getBpType() {
		return this.bpType;
	}

	public void setBpType(String bpType) {
		this.bpType = bpType;
	}

	public String getBpUserOne() {
		return this.bpUserOne;
	}

	public void setBpUserOne(String bpUserOne) {
		this.bpUserOne = bpUserOne;
	}

	public String getBpUserTwo() {
		return this.bpUserTwo;
	}

	public void setBpUserTwo(String bpUserTwo) {
		this.bpUserTwo = bpUserTwo;
	}

	public Calendar getBpDevTimeOne() {
		return this.bpDevTimeOne;
	}

	public void setBpDevTimeOne(Calendar bpDevTimeOne) {
		this.bpDevTimeOne = bpDevTimeOne;
	}

	public Calendar getBpDevTimeTwo() {
		return this.bpDevTimeTwo;
	}

	public void setBpDevTimeTwo(Calendar bpDevTimeTwo) {
		this.bpDevTimeTwo = bpDevTimeTwo;
	}

	public String getBpDrId() {
		return bpDrId;
	}

	public void setBpDrId(String bpDrId) {
		this.bpDrId = bpDrId;
	}

	public String getBpClockOne() {
		return this.bpClockOne;
	}

	public void setBpClockOne(String bpClockOne) {
		this.bpClockOne = bpClockOne;
	}

	public String getBpClockTwo() {
		return this.bpClockTwo;
	}

	public void setBpClockTwo(String bpClockTwo) {
		this.bpClockTwo = bpClockTwo;
	}

	public String getBpClockThr() {
		return this.bpClockThr;
	}

	public void setBpClockThr(String bpClockThr) {
		this.bpClockThr = bpClockThr;
	}

	public String getBpClockFour() {
		return this.bpClockFour;
	}

	public void setBpClockFour(String bpClockFour) {
		this.bpClockFour = bpClockFour;
	}

	public Calendar getBpCreateTime() {
		return this.bpCreateTime;
	}

	public void setBpCreateTime(Calendar bpCreateTime) {
		this.bpCreateTime = bpCreateTime;
	}

	public String getBpPhone() {
		return this.bpPhone;
	}

	public void setBpPhone(String bpPhone) {
		this.bpPhone = bpPhone;
	}

	public String getBpTeamId() {
		return this.bpTeamId;
	}

	public void setBpTeamId(String bpTeamId) {
		this.bpTeamId = bpTeamId;
	}

	public String getUserOneName(){
		if(StringUtils.isNotBlank(this.bpUserOne)){
			SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
			AppPatientUser user = (AppPatientUser) dao.getServiceDo().find(AppPatientUser.class,this.bpUserOne);
			if(user!=null){
				return user.getPatientName();
			}
		}
		return "";
	}
	public String getUserTwoName(){
		if(StringUtils.isNotBlank(this.bpUserTwo)){
			SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
			AppPatientUser user = (AppPatientUser) dao.getServiceDo().find(AppPatientUser.class,this.bpUserTwo);
			if(user!=null){
				return user.getPatientName();
			}
		}
		return "";
	}

	public String getBindTimeOne(){
		if(this.bpDevTimeOne!=null){
			return ExtendDate.getYMD_h_m_s(this.bpDevTimeOne);
		}
		return "";
	}

	public String getBindTimeTwo(){
		if(this.bpDevTimeTwo!=null){
			return ExtendDate.getYMD_h_m_s(this.bpDevTimeTwo);
		}
		return "";
	}

	public String getBpHospId() {
		return bpHospId;
	}

	public void setBpHospId(String bpHospId) {
		this.bpHospId = bpHospId;
	}

	public String getBpAreaCode() {
		return bpAreaCode;
	}

	public void setBpAreaCode(String bpAreaCode) {
		this.bpAreaCode = bpAreaCode;
	}

	public String getStrBpCreateTime(){
		if(this.bpCreateTime!=null){
			return ExtendDate.getYMD_h_m_s(this.bpCreateTime);
		}
		return "";
	}
}