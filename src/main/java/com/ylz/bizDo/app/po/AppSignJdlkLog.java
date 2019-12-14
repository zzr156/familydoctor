package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 建档立卡贫困人口签约记录表（包括增加和修改）
 * WangCheng
 */
@Entity
@Table(name = "APP_SIGN_JDLK_LOG")
public class AppSignJdlkLog extends BasePO {

	// Fields
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
	private String id;
	@Column(name = "SIGN_FORM_ID", nullable = false, length = 36)
	private String signFormId;//关联签约表主键
	@Column(name = "SIGN_CREATER", length = 36)
	private String signCreater;//创建人
	@Column(name = "SIGN_MODIFIER", length = 36)
	private String signModifier;//修改人


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSignFormId() {
		return signFormId;
	}

	public void setSignFormId(String signFormId) {
		this.signFormId = signFormId;
	}

	public String getSignCreater() {
		return signCreater;
	}

	public void setSignCreater(String signCreater) {
		this.signCreater = signCreater;
	}

	public String getSignModifier() {
		return signModifier;
	}

	public void setSignModifier(String signModifier) {
		this.signModifier = signModifier;
	}
}