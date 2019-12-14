package com.ylz.bizDo.jtapp.basicHealthVo;

/**
 * 接收基卫中医体质辨识数据
 * Created by zzl on 2018/8/13.
 */
public class AppBasicTcmAllQvo {
    private String cityCode;//城市编码
    private String userIdno;//患者id
    private String userName;//患者姓名
    private String createTime;//填表时间
    private String drId;//医生id
    private String drName;//医生姓名
    private AppBasicTcmJlQvo tcmJl;//中医体质辨识答题记录
    private AppBasicTcmResultQvo tcmResult;//中医体质辨识结果
    private AppBasicTcmGuideQvo guideVo;//指导

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getUserIdno() {
        return userIdno;
    }

    public void setUserIdno(String userIdno) {
        this.userIdno = userIdno;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public AppBasicTcmJlQvo getTcmJl() {
        return tcmJl;
    }

    public void setTcmJl(AppBasicTcmJlQvo tcmJl) {
        this.tcmJl = tcmJl;
    }

    public AppBasicTcmResultQvo getTcmResult() {
        return tcmResult;
    }

    public void setTcmResult(AppBasicTcmResultQvo tcmResult) {
        this.tcmResult = tcmResult;
    }

    public AppBasicTcmGuideQvo getGuideVo() {
        return guideVo;
    }

    public void setGuideVo(AppBasicTcmGuideQvo guideVo) {
        this.guideVo = guideVo;
    }
}
