package com.ylz.bizDo.jtapp.basicHealthVo;

/**
 * Created by zzl on 2018/8/13.
 */
public class AppBasicTcmGuideQvo {
    private String tzlx00; // 体质类型(1气虚质 2阳虚质 3阴虚质 4痰湿质 5湿热质 6血瘀质 7气郁质 8特禀质 9平和质)
    private String tzbm00; // 体质名称（气虚质 阳虚质 阴虚质 痰湿质 湿热质 血瘀质 气郁质 特禀质 平和质）
    private String qzts00; // 情志调摄
    private String ysty00; // 饮食调养
    private String qjts00; // 起居调摄
    private String ydbj00; // 运动保健
    private String xwbj00; // 穴位保健
    private String other;//906:其他
    private String createTime;//创建时间（格式 2018-08-08 00:00:00）

    public String getTzlx00() {
        return tzlx00;
    }

    public void setTzlx00(String tzlx00) {
        this.tzlx00 = tzlx00;
    }

    public String getTzbm00() {
        return tzbm00;
    }

    public void setTzbm00(String tzbm00) {
        this.tzbm00 = tzbm00;
    }

    public String getQzts00() {
        return qzts00;
    }

    public void setQzts00(String qzts00) {
        this.qzts00 = qzts00;
    }

    public String getYsty00() {
        return ysty00;
    }

    public void setYsty00(String ysty00) {
        this.ysty00 = ysty00;
    }

    public String getQjts00() {
        return qjts00;
    }

    public void setQjts00(String qjts00) {
        this.qjts00 = qjts00;
    }

    public String getYdbj00() {
        return ydbj00;
    }

    public void setYdbj00(String ydbj00) {
        this.ydbj00 = ydbj00;
    }

    public String getXwbj00() {
        return xwbj00;
    }

    public void setXwbj00(String xwbj00) {
        this.xwbj00 = xwbj00;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
