package com.ylz.bizDo.jtapp.patientVo;

/**
 * Created by zzl on 2018/3/9.
 */
public class AppMaternalPlanQvo {
    private String userIdno;//用户id
//    private String type;//1预产日期， 2末次月经日期
    private String type;//1孕产妇 2老年人
    private String userDate;//用户输入日期
    private String urlType;//0591.福州,0595泉州,0596漳州,0594.莆田,0599.南平,0598.三明,7.测试

    public String getUserIdno() {
        return userIdno;
    }

    public void setUserIdno(String userIdno) {
        this.userIdno = userIdno;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserDate() {
        return userDate;
    }

    public void setUserDate(String userDate) {
        this.userDate = userDate;
    }

    public String getUrlType() {
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }
}
