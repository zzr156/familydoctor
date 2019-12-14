package com.ylz.bizDo.cd.po;

import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bean.BasePO;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Calendar;

/**
 * 发送短信内容表
 */
@Entity
@Table(name = "CD_SHORT_MESSAGE")
public class CdShortMessage extends BasePO{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
	private String id;//主键
	
	@Column(name = "MSG_PHONE")
	private String msgPhone;//手机
	
	@Column(name = "MSG_CONTENT")
	private String msgContent;//内容
	
	@Column(name = "MSG_CZ_ID")
	private String msgCzId;//回执ID
	
	@Column(name = "CJSJ")
	private Calendar cjsj;//创建时间
	
	@Column(name = "CJR")
	private String cjr;//创建人

	/** default constructor */
	public CdShortMessage() {
	}

	/** minimal constructor */
	public CdShortMessage(String id) {
		this.id = id;
	}

	/** full constructor */
	public CdShortMessage(String id, String msgPhone, String msgContent,
			String msgCzId, Calendar cjsj,String cjr) {
		this.id = id;
		this.msgPhone = msgPhone;
		this.msgContent = msgContent;
		this.msgCzId = msgCzId;
		this.cjsj = cjsj;
		this.cjr = cjr;
	}
	
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMsgPhone() {
		return msgPhone;
	}

	public void setMsgPhone(String msgPhone) {
		this.msgPhone = msgPhone;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public String getMsgCzId() {
		return msgCzId;
	}

	public void setMsgCzId(String msgCzId) {
		this.msgCzId = msgCzId;
	}

	public Calendar getCjsj() {
		return cjsj;
	}

	public void setCjsj(Calendar cjsj) {
		this.cjsj = cjsj;
	}

	public String getCjr() {
		return cjr;
	}

	public void setCjr(String cjr) {
		this.cjr = cjr;
	}
	
	//创建时间
	public String getStrCjsj(){
		return ExtendDate.getYMD_h_m_s(this.getCjsj());
	}
	//创建人中文名
	public String getCjrName(){
		if(StringUtils.isNotBlank(this.getCjr())){
			SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
			CdUser value = (CdUser)dao.getServiceDo().find(CdUser.class,this.getCjr());
			if(value!=null) {
                return value.getUserName();
            }
		}

		return "";
	}
}
