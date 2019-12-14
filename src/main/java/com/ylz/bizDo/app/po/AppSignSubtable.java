package com.ylz.bizDo.app.po;

import com.ylz.packcommon.common.bean.BasePO;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * 签约单图片子表
 * Created by zzl on 2018/7/13.
 */
@Entity
@Table(name = "APP_SIGN_SUBTABLE")
public class AppSignSubtable extends BasePO{
    @Id
    @Column(name = "ID", unique = true, nullable = false, length = 36)
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid2")
    private String id;
    @Column(name = "SIGN_ID",length = 36)
    private String signId;//签约单主键
    @Column(name = "IMG_URL",length = 200)
    private String imgUrl;//图片路径
    @Column(name = "TYPE",length = 10)
    private String type;//类型 1APP 2web 3微信 4一体机 5pos机,6评价
    @Column(name = "IMG_BASE64",length = 200)
    private String imgBase64;//base64数据
    @Column(name = "IS_AUTOGRAPH",length = 10)
    private String isAutograph="0";//是否是签名  1是 0不是
    @Column(name = "SIGN_AREA_CODE",length = 100)
    private String signAreaCode;//区域编码

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImgBase64() {
        return imgBase64;
    }

    public void setImgBase64(String imgBase64) {
        this.imgBase64 = imgBase64;
    }

    public String getIsAutograph() {
        return isAutograph;
    }

    public void setIsAutograph(String isAutograph) {
        this.isAutograph = isAutograph;
    }

    public String getSignAreaCode() {
        return signAreaCode;
    }

    public void setSignAreaCode(String signAreaCode) {
        this.signAreaCode = signAreaCode;
    }
}
