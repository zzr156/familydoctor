package com.ylz.bizDo.assessment.vo;

import com.ylz.packcommon.common.util.ExtendDate;

public class interfaceQvo {

    private String idno;// 居民身份证号
    private String patientName;// 居民姓名
    private String birthday;// 居民出生日期
    private String signTimeStart;// 协议开始日期
    private String signTimeEnd;// 协议截止日期
    private String urlType;// （1-福州，2-泉州，3-漳州，4-莆田，5-南平，6-三明，7-测试）
    private String act;// 方法名
    private String type;// 所属人群（1-健康人群，2-老年人，3-慢性病）


    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(java.sql.Date birthday) {
        this.birthday = ExtendDate.getYMD(birthday);
    }

    public String getSignTimeStart() {
        return signTimeStart;
    }

    public void setSignTimeStart(java.sql.Timestamp signTimeStart) {
        this.signTimeStart = ExtendDate.getYMD(signTimeStart).replace("-", "");
    }

    public String getSignTimeEnd() {
        return signTimeEnd;
    }

    public void setSignTimeEnd(java.sql.Timestamp signTimeEnd) {
        this.signTimeEnd = ExtendDate.getYMD(signTimeEnd).replace("-", "");
    }

    public String getUrlType() {
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
