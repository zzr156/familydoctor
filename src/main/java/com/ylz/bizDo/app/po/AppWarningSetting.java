package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**预警设置表
 * AppWorkdaySetting entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "APP_WARNING_SETTING")
public class AppWarningSetting extends BasePO {

	// Fields
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
	private String id;
	@Column(name = "WS_USER_ID", length = 36)
	private String wsUserId;//用户id
	@Column(name = "WS_STATE", length = 6)
	private String wsState;//预警开启状态 0禁用 1启用
	@Column(name = "WS_TYPE", length = 50)
	private String wsType;//设置类型 1体检提醒(p) 2健康监测异常提醒(p) 3健康咨询(p) 4用药短缺提醒(p) 5血压闹钟设置 6药品存量预警设置(d) 7儿童接种预约提醒 8儿童计划免疫提醒 9随访提前提醒 10随访8点提醒 11随访15点提醒 12随访方案二 13健康咨询群聊设置
	@Column(name = "WS_NUM" ,length = 10)
	private String wsNum;//提前提醒天数

	// Constructors


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getWsState() {
		return wsState;
	}

	public void setWsState(String wsState) {
		this.wsState = wsState;
	}

	public String getWsType() {
		return wsType;
	}

	public void setWsType(String wsType) {
		this.wsType = wsType;
	}

	public String getWsUserId() {
		return wsUserId;
	}

	public void setWsUserId(String wsUserId) {
		this.wsUserId = wsUserId;
	}

	public String getWsNum() {
		return wsNum;
	}

	public void setWsNum(String wsNum) {
		this.wsNum = wsNum;
	}

}