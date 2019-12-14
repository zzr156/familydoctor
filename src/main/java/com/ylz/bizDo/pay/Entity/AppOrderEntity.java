package com.ylz.bizDo.pay.Entity;

import com.ylz.packcommon.common.util.ExtendDate;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by asus on 2017/07/25.
 */
public class AppOrderEntity {
    private String orderTitle;//订单标题
    private String orderPayMenActual;// 实际支付金额
    private String orderState;//订单状态(0待付款, 1已付款, 2退款中, 3已退款, 4已取消, 5交易完成,6申请退款)
    private String orderCreateName;//订单创建人
    private String orderDate;//订单日期
    private String orderDueDate;//支付时间
    private String orderPayMentMode;//订单付款方式(1.支付宝,2微信)
    private String orderChargeNo;//平台订单号

    public String getOrderTitle() {
        return orderTitle;
    }

    public void setOrderTitle(String orderTitle) {
        this.orderTitle = orderTitle;
    }

    public String getOrderPayMenActual() {
        return orderPayMenActual;
    }

    public void setOrderPayMenActual(BigDecimal orderPayMenActual) {
        this.orderPayMenActual = orderPayMenActual.toString();
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getOrderCreateName() {
        return orderCreateName;
    }

    public void setOrderCreateName(String orderCreateName) {
        this.orderCreateName = orderCreateName;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        if(orderDate != null){
            this.orderDate = ExtendDate.getYMD_h_m(orderDate);
        }else {
            this.orderDate = "";
        }
    }

    public String getOrderDueDate() {
        return orderDueDate;
    }

    public void setOrderDueDate(Timestamp orderDueDate) {
        if(orderDueDate != null){
            this.orderDueDate = ExtendDate.getYMD_h_m(orderDueDate);
        }else {
            this.orderDueDate = "";
        }
    }

    public String getOrderPayMentMode() {
        return orderPayMentMode;
    }

    public void setOrderPayMentMode(String orderPayMentMode) {
        this.orderPayMentMode = orderPayMentMode;
    }

    public String getOrderChargeNo() {
        return orderChargeNo;
    }

    public void setOrderChargeNo(String orderChargeNo) {
        this.orderChargeNo = orderChargeNo;
    }
}
