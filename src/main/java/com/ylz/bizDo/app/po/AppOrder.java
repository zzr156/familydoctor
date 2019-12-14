package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Calendar;

/**
 * AppOrder entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "APP_ORDER")
public class AppOrder extends BasePO {

	// Fields
	@Id
	@Column(name = "ID", unique = true, nullable = false, length = 36)
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
	private String id;//主键
	@Column(name = "ORDER_NO", length = 100)
	private String orderNo;//订单号
	@Column(name = "ORDER_TITLE", length = 100)
	private String orderTitle;//订单标题
	@Column(name = "ORDER_SIGN_ID", length = 36)
	private String orderSignId;//签约单
	@Column(name = "ORDER_TYPE", length = 6)
	private String orderType;//订单类型(1家庭签约 2三师共管 3居家养老)
	@Column(name = "ORDER_PAY_MEN", precision = 16, scale=2)
	private BigDecimal orderPayMen;//支付金额
	@Column(name = "ORDER_PAY_MEN_ACTUAL", precision = 16, scale=2)
	private BigDecimal orderPayMenActual;// 实际支付金额
	@Column(name = "ORDER_STATE", length = 10)
	private String orderState;//订单状态(0待付款, 1已付款, 2退款中, 3已退款, 4已取消, 5交易完成,6申请退款)
	@Column(name = "ORDER_CREATE_ID", length = 36)
	private String orderCreateId;//订单创建主键
	@Column(name = "ORDER_CREATE_NAME", length = 20)
	private String orderCreateName;//订单创建人
	@Column(name = "ORDER_CREATE_DATE", length = 19)
	private Calendar orderCreateDate;//订单创建时间
	@Column(name = "ORDER_DATE", length = 19)
	private Calendar orderDate;//订单日期
	@Column(name = "ORDER_DUE_DATE", length = 19)
	private Calendar orderDueDate;//支付时间
	@Column(name = "ORDER_SOURCE_SYSTEM", length = 10)
	private String orderSourceSystem;//订单系统来源(1.app,2web)
	@Column(name = "ORDER_PAY_MENT_MODE", length = 15)
	private String orderPayMentMode;//订单付款方式(1.支付宝,2微信)
	@Column(name = "ORDER_CHARGE_NO", length = 100)
	private String orderChargeNo;//平台订单号
	@Column(name = "ORDER_CHARGE_DATA", length = 1000)
	private String orderChargeData;//平台订单数据

	// Constructors

	/** default constructor */
	public AppOrder() {
	}

	/** minimal constructor */
	public AppOrder(String id) {
		this.id = id;
	}

	/** full constructor */
	public AppOrder(String id, String orderNo, String orderSignId,
			String orderType, BigDecimal orderPayMen, BigDecimal orderPayMenActual,
			String orderState, String orderCreateId, String orderCreateName,
			Calendar orderCreateDate, Calendar orderDate,
		    Calendar orderDueDate, String orderSourceSystem,
			String orderPayMentMode,String orderTitle,String orderChargeNo,
			String orderChargeData) {
		this.id = id;
		this.orderNo = orderNo;
		this.orderSignId = orderSignId;
		this.orderType = orderType;
		this.orderPayMen = orderPayMen;
		this.orderPayMenActual = orderPayMenActual;
		this.orderState = orderState;
		this.orderCreateId = orderCreateId;
		this.orderCreateName = orderCreateName;
		this.orderCreateDate = orderCreateDate;
		this.orderDate = orderDate;
		this.orderDueDate = orderDueDate;
		this.orderSourceSystem = orderSourceSystem;
		this.orderPayMentMode = orderPayMentMode;
		this.orderTitle = orderTitle;
		this.orderChargeNo = orderChargeNo;
		this.orderChargeData = orderChargeData;
	}

	// Property accessors


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOrderSignId() {
		return orderSignId;
	}

	public void setOrderSignId(String orderSignId) {
		this.orderSignId = orderSignId;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public BigDecimal getOrderPayMen() {
		return orderPayMen;
	}

	public void setOrderPayMen(BigDecimal orderPayMen) {
		this.orderPayMen = orderPayMen;
	}

	public BigDecimal getOrderPayMenActual() {
		return orderPayMenActual;
	}

	public void setOrderPayMenActual(BigDecimal orderPayMenActual) {
		this.orderPayMenActual = orderPayMenActual;
	}

	public String getOrderState() {
		return orderState;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}

	public String getOrderCreateId() {
		return orderCreateId;
	}

	public void setOrderCreateId(String orderCreateId) {
		this.orderCreateId = orderCreateId;
	}

	public String getOrderCreateName() {
		return orderCreateName;
	}

	public void setOrderCreateName(String orderCreateName) {
		this.orderCreateName = orderCreateName;
	}

	public Calendar getOrderCreateDate() {
		return orderCreateDate;
	}

	public void setOrderCreateDate(Calendar orderCreateDate) {
		this.orderCreateDate = orderCreateDate;
	}

	public Calendar getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(Calendar orderDate) {
		this.orderDate = orderDate;
	}

	public Calendar getOrderDueDate() {
		return orderDueDate;
	}

	public void setOrderDueDate(Calendar orderDueDate) {
		this.orderDueDate = orderDueDate;
	}

	public String getOrderSourceSystem() {
		return orderSourceSystem;
	}

	public void setOrderSourceSystem(String orderSourceSystem) {
		this.orderSourceSystem = orderSourceSystem;
	}

	public String getOrderPayMentMode() {
		return orderPayMentMode;
	}

	public void setOrderPayMentMode(String orderPayMentMode) {
		this.orderPayMentMode = orderPayMentMode;
	}

	public String getOrderTitle() {
		return orderTitle;
	}

	public void setOrderTitle(String orderTitle) {
		this.orderTitle = orderTitle;
	}

	public String getOrderChargeNo() {
		return orderChargeNo;
	}

	public void setOrderChargeNo(String orderChargeNo) {
		this.orderChargeNo = orderChargeNo;
	}

	public String getOrderChargeData() {
		return orderChargeData;
	}

	public void setOrderChargeData(String orderChargeData) {
		this.orderChargeData = orderChargeData;
	}
}