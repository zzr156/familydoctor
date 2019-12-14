package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 居家养老
 */
@Entity
@Table(name = "APP_OLD_CARE")
public class AppOldCare extends BasePO {

	// Fields
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
	private String id;//主键
	@Column(name = "OC_MANAGE_CLASS", length = 10)
	private String ocManageClass;//管理等级 红黄绿
	@Column(name = "OC_MANAGE_WAY", length = 10)
	private String ocManageWay;//管理方式
	@Column(name = "OC_PERIOD", length = 10)
	private String ocPeriod;//周期
	@Column(name = "OC_ALERT_DAY", length = 10)
	private String ocAlertDay ;//预警时间
	@Column(name = "OC_DR_ID", length = 36)
	private String ocDrId;//医生主键

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOcManageClass() {
		return ocManageClass;
	}

	public void setOcManageClass(String ocManageClass) {
		this.ocManageClass = ocManageClass;
	}

	public String getOcManageWay() {
		return ocManageWay;
	}

	public void setOcManageWay(String ocManageWay) {
		this.ocManageWay = ocManageWay;
	}

	public String getOcPeriod() {
		return ocPeriod;
	}

	public void setOcPeriod(String ocPeriod) {
		this.ocPeriod = ocPeriod;
	}

	public String getOcAlertDay() {
		return ocAlertDay;
	}

	public void setOcAlertDay(String ocAlertDay) {
		this.ocAlertDay = ocAlertDay;
	}

	public String getOcDrId() {
		return ocDrId;
	}

	public void setOcDrId(String ocDrId) {
		this.ocDrId = ocDrId;
	}
}