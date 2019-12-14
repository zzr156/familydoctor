package com.ylz.bizDo.jtapp.commonVo;

import java.util.List;

/**
 * Created by zzl on 2018/8/11.
 */
public class AppTcmRecordQvo {
    private String urlType;
    private AppTcmYbQvo ybqk;
    private AppTcmBsQvo bsjl;
    private List<AppTcmZdQvo> jlvo;

    public String getUrlType() {
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }

    public AppTcmYbQvo getYbqk() {
        return ybqk;
    }

    public void setYbqk(AppTcmYbQvo ybqk) {
        this.ybqk = ybqk;
    }

    public AppTcmBsQvo getBsjl() {
        return bsjl;
    }

    public void setBsjl(AppTcmBsQvo bsjl) {
        this.bsjl = bsjl;
    }

    public List<AppTcmZdQvo> getJlvo() {
        return jlvo;
    }

    public void setJlvo(List<AppTcmZdQvo> jlvo) {
        this.jlvo = jlvo;
    }
}
