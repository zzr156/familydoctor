package com.ylz.bizDo.jtapp.commonEntity;

/**
 * Created by asus on 2017/6/22.
 */
public class AppMessageEntity {
    private String mobiles;//手机号码
    private String content;//内容
    private String resultCode;//结果
    private String resultBody;//内容

    public String getMobiles() {
        return mobiles;
    }

    public void setMobiles(String mobiles) {
        this.mobiles = mobiles;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultBody() {
        return resultBody;
    }

    public void setResultBody(String resultBody) {
        this.resultBody = resultBody;
    }
}
