package com.ylz.bizDo.jtapp.ysChangeEntity;

/**
 * Created by zzl on 2017/9/4.
 */
public class YsChangePeopleEntity {
    private String id;//患者id
    private String name;//患者姓名
    private String imageUrl;//患者头像
    private String sex;//性别

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
