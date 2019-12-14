package com.ylz.bizDo.jtapp.commonEntity;

import java.math.BigInteger;

/**转签每项原因人数和比例
 * Created by zzl on 2017/11/21.
 */
public class AppGoToEntity {
    private String reasonType;//原因类型
    private String count;//人数
    private String bl;//比例

    public String getReasonType() {
        return reasonType;
    }

    public void setReasonType(String reasonType) {
        this.reasonType = reasonType;
    }

    public String getCount() {
        return count;
    }

    public void setCount(BigInteger count) {
        if(count!=null){
            this.count = String.valueOf(count);
        }else{
            this.count = "0";
        }
    }

    public String getBl() {
        return bl;
    }

    public void setBl(String bl) {
        this.bl = bl;
    }
}
