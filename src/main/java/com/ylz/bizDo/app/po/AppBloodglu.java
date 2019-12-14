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
 * 血糖设备
 */
@Entity
@Table(name = "APP_BLOODGLU")
public class AppBloodglu extends BasePO {

	// Fields
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
	private String id;//主键
	@Column(name = "BG_DEV_ID", length = 50)
	private String bgDevId;//设备id
	@Column(name = "BG_PAIENT_ID", length = 36)
	private String bgPaientId;//患者主键
	@Column(name = "BG_DEV_TIME")
	private Calendar bgDevTime;//绑定设备的时间
	@Column(name = "BG_CREATE_TIME")
	private Calendar bgCreateTime;//创建时间
	@Column(name = "BG_DR_ID", length = 36)
	private String bgDrId;//医生主键
	@Column(name = "BG_TEAM_ID", length = 36)
	private String bgTeamId;//团队主键
	@Column(name = "BG_TYPE", length = 50)
	private String bgType;//设备型号
	@Column(name = "BG_HOSP_ID", length = 36)
	private String bgHospId;//医院id
	@Column(name = "BG_AREA_CODE", length = 50)
	private String bgAreaCode;//医院区域编码


	// Property accessors

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBgDevId() {
		return this.bgDevId;
	}

	public void setBgDevId(String bgDevId) {
		this.bgDevId = bgDevId;
	}

	public String getBgPaientId() {
		return this.bgPaientId;
	}

	public void setBgPaientId(String bgPaientId) {
		this.bgPaientId = bgPaientId;
	}

	public Calendar getBgDevTime() {
		return this.bgDevTime;
	}

	public void setBgDevTime(Calendar bgDevTime) {
		this.bgDevTime = bgDevTime;
	}

	public Calendar getBgCreateTime() {
		return this.bgCreateTime;
	}

	public void setBgCreateTime(Calendar bgCreateTime) {
		this.bgCreateTime = bgCreateTime;
	}

	public String getBgDrId() {
		return this.bgDrId;
	}

	public void setBgDrId(String bgDrId) {
		this.bgDrId = bgDrId;
	}

	public String getBgTeamId() {
		return this.bgTeamId;
	}

	public void setBgTeamId(String bgTeamId) {
		this.bgTeamId = bgTeamId;
	}

	public String getBgType() {
		return bgType;
	}

	public void setBgType(String bgType) {
		this.bgType = bgType;
	}

	public String getUserOneName(){
		if(StringUtils.isNotBlank(this.bgPaientId)){
			SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
			AppPatientUser user = (AppPatientUser) dao.getServiceDo().find(AppPatientUser.class,this.bgPaientId);
			if(user!=null){
				return user.getPatientName();
			}
		}
		return "";
	}

	public String getBindTime(){
		if(this.bgDevTime!=null){
			return ExtendDate.getYMD_h_m_s(this.bgDevTime);
		}
		return "";
	}

	public String getBgHospId() {
		return bgHospId;
	}

	public void setBgHospId(String bgHospId) {
		this.bgHospId = bgHospId;
	}

	public String getBgAreaCode() {
		return bgAreaCode;
	}

	public void setBgAreaCode(String bgAreaCode) {
		this.bgAreaCode = bgAreaCode;
	}

	public String getStrBgCreateTime(){
		if(this.bgCreateTime!=null){
			return ExtendDate.getYMD_h_m_s(this.bgCreateTime);
		}
		return "";
	}
}