package com.ylz.bizDo.jtapp.drVo;


/**
 * Created by zzl on 2017/6/20.
 */
public class AppWarningSettingQvo {
    private String userId;
    private String type;//设置类型 1体检提醒(p) 2 健康监测异常提醒(p) 3健康咨询(p) 4用药短缺提醒(p) 5血压闹钟设置(p) 6药品存量预警设置(d)
    private String state;// 0禁止 1开启
    private String num;//提前提醒天数
    private String clock1;//血压闹钟
    private String clock2;
    private String clock3;
    private String clock4;



    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getClock1() {
        return clock1;
    }

    public void setClock1(String clock1) {
        this.clock1 = clock1;
    }

    public String getClock2() {
        return clock2;
    }

    public void setClock2(String clock2) {
        this.clock2 = clock2;
    }

    public String getClock3() {
        return clock3;
    }

    public void setClock3(String clock3) {
        this.clock3 = clock3;
    }

    public String getClock4() {
        return clock4;
    }

    public void setClock4(String clock4) {
        this.clock4 = clock4;
    }
}
