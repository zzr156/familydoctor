package com.ylz.packcommon.common.util;
/**
 * 订单状态枚举
 */
public enum OrderPayState{
	/**
	 * 待付款
	 */
	ORDER_DFK("0"),
	/**
	 * 已付款
	 */
	ORDER_YFK("1"),
	/**
	 * 退款中
	 */
	ORDER_TKZ("2"),
	/**
	 * 已退款
	 */
	ORDER_YTK("3"),
	/**
	 * 已取消
	 */
	ORDER_YQX("4"),
	/**
	 * 交易完成
	 */
	ORDER_JYWC("5"),
	/**
	 * 申请退款
	 */
	ORDER_SQTK("6");
	
	private String state;//订单状态
	
	private OrderPayState(String state){
		this.state = state;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	
}
