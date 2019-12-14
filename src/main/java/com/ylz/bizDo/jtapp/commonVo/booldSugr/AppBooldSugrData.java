package com.ylz.bizDo.jtapp.commonVo.booldSugr;

/**
 * Created by asus on 2018/05/10.
 */
public class AppBooldSugrData {
    private AppHead head;//头部数据
    private AppMsgdata msgdata;//数据消息主体
    private String reservedcode;//接口命令（01-血糖上传命令）
    private String sourceType;//来源（1app 2智能设备 3随访 4门诊 5poss 6一体机）
    private String name;//姓名
    private String idno;//身份证

    public AppHead getHead() {
        return head;
    }

    public void setHead(AppHead head) {
        this.head = head;
    }

    public AppMsgdata getMsgdata() {
        return msgdata;
    }

    public void setMsgdata(AppMsgdata msgdata) {
        this.msgdata = msgdata;
    }

    public String getReservedcode() {
        return reservedcode;
    }

    public void setReservedcode(String reservedcode) {
        this.reservedcode = reservedcode;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }
}
