package com.ylz.bizDo.jtapp.basicHealthEntity.jktj;

/**孕产妇检查时间及检查时孕周
 * Created by zzl on 2018/3/12.
 */
public class Jktj_maternal {
    private String type;//1孕产妇 2老年人
    private String xm0000;//类型(1首次产前检查 2二到五次产前随访 3产后随访)
    private String cs0000;//次数（首次产前检查为空，二到五次产前随访：次别标记（2~5次、追加），产后随访：项目（1为产后，2为产后42天））
    private String yz0000;//填表孕周
    private String yzts00;//填表孕周天数
    private String sfrq00;//填表日期
    private String xcsfrq;//下次随访日期
    private String scxtbh;//系统手册编号
    private String njsj00;//年检时间

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getXm0000() {
        return xm0000;
    }

    public void setXm0000(String xm0000) {
        this.xm0000 = xm0000;
    }

    public String getCs0000() {
        return cs0000;
    }

    public void setCs0000(String cs0000) {
        this.cs0000 = cs0000;
    }

    public String getYz0000() {
        return yz0000;
    }

    public void setYz0000(String yz0000) {
        this.yz0000 = yz0000;
    }

    public String getYzts00() {
        return yzts00;
    }

    public void setYzts00(String yzts00) {
        this.yzts00 = yzts00;
    }

    public String getSfrq00() {
        return sfrq00;
    }

    public void setSfrq00(String sfrq00) {
        this.sfrq00 = sfrq00;
    }

    public String getXcsfrq() {
        return xcsfrq;
    }

    public void setXcsfrq(String xcsfrq) {
        this.xcsfrq = xcsfrq;
    }

    public String getScxtbh() {
        return scxtbh;
    }

    public void setScxtbh(String scxtbh) {
        this.scxtbh = scxtbh;
    }

    public String getNjsj00() {
        return njsj00;
    }

    public void setNjsj00(String njsj00) {
        this.njsj00 = njsj00;
    }
}
