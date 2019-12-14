package com.ylz.bizDo.jtapp.basicHealthVo;

/**
 * Created by asus on 2017/10/30.
 */
public class NeonateQvo {
    private String idno;//身份证
    private String ageType;//年龄类型 0.新生儿，1.1岁以内儿童，2.1-2岁儿童，3.3岁以上儿童(查询体检时可为空)
    private String urlType;//类型

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getUrlType() {
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }

    public String getAgeType() {
        return ageType;
    }

    public void setAgeType(String ageType) {
        this.ageType = ageType;
    }
}
