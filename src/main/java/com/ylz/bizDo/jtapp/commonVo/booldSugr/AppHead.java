package com.ylz.bizDo.jtapp.commonVo.booldSugr;

/**
 * Created by asus on 2018/05/10.
 */
public class AppHead {
    private String signature;//头部签名
    private String timestamp;//时间戳
    private String randomnumber;//随机数

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getRandomnumber() {
        return randomnumber;
    }

    public void setRandomnumber(String randomnumber) {
        this.randomnumber = randomnumber;
    }
}
