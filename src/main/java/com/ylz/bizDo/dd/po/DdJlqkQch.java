package com.ylz.bizDo.dd.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


/**
 * 订单号生成表
 * @author dws
 *
 */
@Entity
@Table(name = "DD_JLQKQCH")
public class DdJlqkQch extends BasePO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
	private String id;//主键
	
	@Column(name = "DD_CODE")
	private Integer ddCode;//流水号
	
	@Column(name = "DD_YEAR")
	private Integer ddYear;//年份
	
	@Column(name = "DD_MONTH")
	private Integer ddMonth;//月
	
	@Column(name = "DD_DAY")
	private Integer ddDay;//日
	
	@Column(name = "DD_TYPE")
	private String ddType;//类别(AVI,PL,TB,BY)
	

	/** default constructor */
	public DdJlqkQch() {
	}

	/** minimal constructor */
	public DdJlqkQch(String id) {
		this.id = id;
	}

	/** full constructor */
	public DdJlqkQch(String id, Integer ddCode, Integer ddYear,Integer ddMonth,Integer ddDay,String ddType) {
		this.id = id;
		this.ddCode = ddCode;
		this.ddYear = ddYear;
		this.ddMonth = ddMonth;
		this.ddDay = ddDay;
		this.ddType = ddType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getDdCode() {
		return ddCode;
	}

	public void setDdCode(Integer ddCode) {
		this.ddCode = ddCode;
	}

	public Integer getDdYear() {
		return ddYear;
	}

	public void setDdYear(Integer ddYear) {
		this.ddYear = ddYear;
	}

	public Integer getDdMonth() {
		return ddMonth;
	}

	public void setDdMonth(Integer ddMonth) {
		this.ddMonth = ddMonth;
	}

	public Integer getDdDay() {
		return ddDay;
	}

	public void setDdDay(Integer ddDay) {
		this.ddDay = ddDay;
	}

	public String getDdType() {
		return ddType;
	}

	public void setDdType(String ddType) {
		this.ddType = ddType;
	}
}
