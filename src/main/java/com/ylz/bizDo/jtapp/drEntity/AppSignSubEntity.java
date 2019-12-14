package com.ylz.bizDo.jtapp.drEntity;

import com.ylz.packcommon.common.util.PropertiesUtil;
import org.apache.commons.lang.StringUtils;

/**
 * Created by zzl on 2018/7/16.
 */
public class AppSignSubEntity {
    private String id;
    private String signId;//签约单主键
    private String imgUrl;//图片路径
    private String intranetIp;//内网ip
    private String extranetIp;//外网ip
    private String type;//类型

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) {
        this.signId = signId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getIntranetIp() {
        return intranetIp;
    }

    public void setIntranetIp(String intranetIp) throws Exception {
        if(StringUtils.isNotBlank(this.getImgUrl())){
            intranetIp = PropertiesUtil.getConfValue("intranetIp")+this.getImgUrl();
        }
        this.intranetIp = intranetIp;
    }

    public String getExtranetIp() {
        return extranetIp;
    }

    public void setExtranetIp(String extranetIp) throws Exception{
        if(StringUtils.isNotBlank(this.getImgUrl())){
            extranetIp = PropertiesUtil.getConfValue("extranetIp")+this.getImgUrl();
        }
        this.extranetIp = extranetIp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
