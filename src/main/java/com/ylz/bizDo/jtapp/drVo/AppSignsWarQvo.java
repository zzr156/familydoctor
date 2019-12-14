package com.ylz.bizDo.jtapp.drVo;

/**医生设置所管居民未更新体征数据的预警时间请求参数
 * Created by zzl on 2017/11/2.
 */
public class AppSignsWarQvo {
    private String drId;//医生id
    private String red;//红级预警设置
    private String yellow;//黄级预警设置
    private String green;//绿级预警设置
    private String gray;//灰级预警设置
    private String openState;//开启状态
    private String patientId;//患者id（多个用;隔开）
    private String allState;//是否选择全部人员（1是，0否）
    private String disType;//疾病类型
    private String firstDay;//初始天数设置
    private String redSwitch;//红标开关（0关闭 1开启）
    private String yellowSwitch;//黄标开关
    private String greenSwitch;//绿标开关
    private String graySwitch;//灰标开关

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getRed() {
        return red;
    }

    public void setRed(String red) {
        this.red = red;
    }

    public String getYellow() {
        return yellow;
    }

    public void setYellow(String yellow) {
        this.yellow = yellow;
    }

    public String getGreen() {
        return green;
    }

    public void setGreen(String green) {
        this.green = green;
    }

    public String getOpenState() {
        return openState;
    }

    public void setOpenState(String openState) {
        this.openState = openState;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getAllState() {
        return allState;
    }

    public void setAllState(String allState) {
        this.allState = allState;
    }

    public String getDisType() {
        return disType;
    }

    public void setDisType(String disType) {
        this.disType = disType;
    }

    public String getFirstDay() {
        return firstDay;
    }

    public void setFirstDay(String firstDay) {
        this.firstDay = firstDay;
    }

    public String getRedSwitch() {
        return redSwitch;
    }

    public void setRedSwitch(String redSwitch) {
        this.redSwitch = redSwitch;
    }

    public String getYellowSwitch() {
        return yellowSwitch;
    }

    public void setYellowSwitch(String yellowSwitch) {
        this.yellowSwitch = yellowSwitch;
    }

    public String getGreenSwitch() {
        return greenSwitch;
    }

    public void setGreenSwitch(String greenSwitch) {
        this.greenSwitch = greenSwitch;
    }

    public String getGray() {
        return gray;
    }

    public void setGray(String gray) {
        this.gray = gray;
    }

    public String getGraySwitch() {
        return graySwitch;
    }

    public void setGraySwitch(String graySwitch) {
        this.graySwitch = graySwitch;
    }
}
