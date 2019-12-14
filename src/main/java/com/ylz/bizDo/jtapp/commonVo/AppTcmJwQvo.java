package com.ylz.bizDo.jtapp.commonVo;

import com.ylz.bizDo.jtapp.commonEntity.AppTcmEntity;

import java.util.List;

/**
 * Created by zzl on 2018/8/10.
 */
public class AppTcmJwQvo {
    private String drId;//医生id
    private String orgId;//机构id
    private String urlType;//区域
    private String df_id;//居民档案号
    private List<AppTcmEntity> listTcm;//体质结果

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getUrlType() {
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }

    public List<AppTcmEntity> getListTcm() {
        return listTcm;
    }

    public void setListTcm(List<AppTcmEntity> listTcm) {
        this.listTcm = listTcm;
    }

    public String getDf_id() {
        return df_id;
    }

    public void setDf_id(String df_id) {
        this.df_id = df_id;
    }
}
