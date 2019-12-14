package com.ylz.bizDo.jtapp.basicHealthEntity.jktj;

/**妇幼出院日期信息
 * Created by zzl on 2017/11/20.
 */
public class Fy_message {
  //  private String type;//1为新生儿，需要传入母亲身份证号，2为孕产妇
    private String cyrq00;//出院日期
    private String y13rq0;//孕13周日期
    private String mcyj00;//末次月经(为空表示“不详”)

    public String getCyrq00() {
        return cyrq00;
    }

    public void setCyrq00(String cyrq00) {
        this.cyrq00 = cyrq00;
    }

    public String getY13rq0() {
        return y13rq0;
    }

    public void setY13rq0(String y13rq0) {
        this.y13rq0 = y13rq0;
    }

    public String getMcyj00() {
        return mcyj00;
    }

    public void setMcyj00(String mcyj00) {
        this.mcyj00 = mcyj00;
    }
}
