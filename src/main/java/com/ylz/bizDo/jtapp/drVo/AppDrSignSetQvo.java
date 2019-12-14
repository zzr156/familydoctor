package com.ylz.bizDo.jtapp.drVo;

/**
 * Created by zzl on 2017/7/26.
 */
public class AppDrSignSetQvo {
    private String userId;//用户id
//    private String openWorkType;//是否开启选择工作类型(0否1是)
//    private String workType;//工作类型（1健康管理师，2专科医生，3全科医生，4	医技人员，5公卫医师，6社区护士）
//    private String subsidyType;//政府补贴方式
//    private String signFree;//签约费用
//    private String serveType;//居民服务类型
//    private String fwb;//服务包
    private String cityCode;//
    private String signState;//状态

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getSignState() {
        return signState;
    }

    public void setSignState(String signState) {
        this.signState = signState;
    }
}
