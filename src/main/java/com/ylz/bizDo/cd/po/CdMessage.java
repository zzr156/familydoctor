package com.ylz.bizDo.cd.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by PC on 2016/2/15.
 * 消息管理
 */
@Entity
@Table(name="CD_MESSAGE")
public class CdMessage extends BasePO {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    @Column(name = "id", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;//主键
	@Column(name = "dataOut_Remind",length = 10)
	private String cqtx;//超期未送检提醒
	@Column(name = "qf_timeOut",length = 10)
	private String qfcs;//检验报告时间签发超时
	@Column(name = "result",length = 10)
	private String result;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCqtx() {
		return cqtx;
	}
	public void setCqtx(String cqtx) {
		this.cqtx = cqtx;
	}
	public String getQfcs() {
		return qfcs;
	}
	public void setQfcs(String qfcs) {
		this.qfcs = qfcs;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	
}
