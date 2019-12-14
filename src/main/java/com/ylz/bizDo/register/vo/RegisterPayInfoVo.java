package com.ylz.bizDo.register.vo;
import com.ylz.packcommon.common.CommConditionVo;

public class RegisterPayInfoVo {
    // Fields
    private String id;
    private String projectName;//项目名称
    private String unitPrice;//项目单价
    private String quantity;//数量
    private String totalPrice;//总金额
    private String operationDate;//操作时间
    private String isSign;//是否签约
    private String jjId;//经济类型ID
    private String govValue;//政府补贴类型值

    public String getGovValue() {
        return govValue;
    }

    public void setGovValue(String govValue) {
        this.govValue = govValue;
    }

    public String getJjId() {
        return jjId;
    }

    public void setJjId(String jjId) {
        this.jjId = jjId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getOperationDate() {
        return operationDate;
    }

    public void setOperationDate(String operationDate) {
        this.operationDate = operationDate;
    }

    public String getIsSign() {
        return isSign;
    }

    public void setIsSign(String isSign) {
        this.isSign = isSign;
    }
}
