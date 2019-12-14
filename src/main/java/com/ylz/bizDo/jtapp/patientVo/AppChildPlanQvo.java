package com.ylz.bizDo.jtapp.patientVo;

/**
 * Created by zzl on 2018/3/9.
 */
public class AppChildPlanQvo {
    private String userIdno;//用户身份证
    private String childerIdno;//儿童身份证号
    private String birthDay;//出生日期
    private String childName;//儿童姓名
    private String urlType;//1.福州,2泉州,3漳州,4.莆田,5.南平,6.三明,7.测试

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getUserIdno() {
        return userIdno;
    }

    public void setUserIdno(String userIdno) {
        this.userIdno = userIdno;
    }

    public String getChilderIdno() {
        return childerIdno;
    }

    public void setChilderIdno(String childerIdno) {
        this.childerIdno = childerIdno;
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        this.birthDay = birthDay;
    }

    public String getUrlType() {
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }
}
