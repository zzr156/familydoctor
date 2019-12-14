package com.ylz.packcommon.common.bean;

//import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.ylz.packaccede.util.JackCalender;
import org.apache.struts2.json.annotations.JSON;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.util.Calendar;


/**
 * POJO基类
 * @date 2012/04/07
 */
//@MappedSuperclass 用在父类上面。当这个类肯定是父类时，加此标注。如果改成@Entity，则继承后，多个类继承，只会生成一个表，而不是多个继承，生成多个表
@MappedSuperclass
public class BasePO  implements java.io.Serializable {

	private static final long serialVersionUID = -5972679257248081155L;

	@Column(name = "HS_CREATE_DATE", nullable = true)
	private Calendar HsCreateDate;
	@Column(name = "HS_UPDATE_TIME", nullable = true)
	private Calendar HsUpdateTime;

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Calendar getHsCreateDate() {
		return HsCreateDate;
	}

	@JsonDeserialize(using= JackCalender.class)
	public void setHsCreateDate(Calendar hsCreateDate) {
		HsCreateDate = hsCreateDate;
	}

	@JSON(format="yyyy-MM-dd HH:mm:ss")
	public Calendar getHsUpdateTime() {
		return HsUpdateTime;
	}

	@JsonDeserialize(using= JackCalender.class)
	public void setHsUpdateTime(Calendar hsUpdateTime) {
		HsUpdateTime = hsUpdateTime;
	}

}
