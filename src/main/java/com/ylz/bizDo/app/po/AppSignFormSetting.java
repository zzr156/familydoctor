package com.ylz.bizDo.app.po;

import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**
 * 签约信息配置表
 * Wangcheng
 */
@Entity
@Table(name = "APP_SIGN_FORM_SETTING")
public class AppSignFormSetting extends BasePO {

	// Fields
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
	private String id;

	@Column(name = "HOSP_AREA_SHORT", nullable = false, length = 20)
	private String hospAreaShort;//行政编码前四位

	@Column(name = "FIND_STATE", length = 2)
	private String findState;//限制查询前一个月的状态（1开启、2未开启）

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHospAreaShort() {
		return hospAreaShort;
	}

	public void setHospAreaShort(String hospAreaShort) {
		this.hospAreaShort = hospAreaShort;
	}

	public String getFindState() {
		return findState;
	}

	public void setFindState(String findState) {
		this.findState = findState;
	}
}