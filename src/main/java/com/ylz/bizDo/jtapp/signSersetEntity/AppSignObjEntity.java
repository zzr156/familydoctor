package com.ylz.bizDo.jtapp.signSersetEntity;

/**服务人群层
 * Created by zzl on 2017/8/23.
 */
public class AppSignObjEntity {
    private String oId;//服务人群id
    private String oName;//服务人群名称
    private String oValue;//服务人群值
    private String oState;//是否是基础服务人群
    private String oStateName;


    public String getoId() {
        return oId;
    }

    public void setoId(String oId) {
        this.oId = oId;
    }

    public String getoName() {
        return oName;
    }

    public void setoName(String oName) {
        this.oName = oName;
    }

    public String getoValue() {
        return oValue;
    }

    public void setoValue(String oValue) {
        this.oValue = oValue;
    }

    public String getoState() {
        return oState;
    }

    public void setoState(String oState) {
        this.oState = oState;
    }

    public String getoStateName() {
        return oStateName;
    }

    public void setoStateName(String oStateName) {
        this.oStateName = oStateName;
    }
}
