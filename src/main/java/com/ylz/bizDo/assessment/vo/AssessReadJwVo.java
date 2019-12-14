package com.ylz.bizDo.assessment.vo;

//调取基卫页面VO
public class AssessReadJwVo {

    private String idcardno;

    private String childName;

    private String birthday;

    private String signStartTime;

    private String signEndTime;

    private String urlType;//1.福州,2泉州,3漳州,4.莆田,5.南平,6.三明,7.测试

    private String act;//方法名

    private String type;//// 1:健康人群 2:老年人 3:慢性病

    private String areaCode;


    public String getIdcardno() {
        return idcardno;
    }

    public void setIdcardno(String idcardno) {
        this.idcardno = idcardno;
    }

    public String getSignStartTime() {
        return signStartTime;
    }

    public void setSignStartTime(String signStartTime) {
        this.signStartTime = signStartTime;
    }

    public String getSignEndTime() {
        return signEndTime;
    }

    public void setSignEndTime(String signEndTime) {
        this.signEndTime = signEndTime;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getUrlType() {
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
}
