package com.ylz.bizDo.jtapp.drVo;

/**
 * Created by hzk on 2017/6/24.
 * 居民 列表
 */
public class AppJmList {
    private String count;//条数
    private String signPersGroup;//服务人群
    private String signHealthGroup;//健康情况
    private String labelGruops;//疾病类型
    private String name;//名称


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSignPersGroup() {
        return signPersGroup;
    }

    public void setSignPersGroup(String signPersGroup) {
        this.signPersGroup = signPersGroup;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getSignHealthGroup() {
        return signHealthGroup;
    }

    public void setSignHealthGroup(String signHealthGroup) {
        this.signHealthGroup = signHealthGroup;
    }

    public String getLabelGruops() {
        return labelGruops;
    }

    public void setLabelGruops(String labelGruops) {
        this.labelGruops = labelGruops;
    }
}
