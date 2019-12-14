package com.ylz.bizDo.jtapp.commonEntity;

import com.ylz.packcommon.common.util.ExtendDate;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Created by zzl on 2019/3/11.
 */
public class AppYbSignEntity {
    private String id;//主键
    private String funid;//交易码
    private String xm0000;//姓名
    private String sfzh00;//身份证
    private String id0000;//社保卡
    private String qyysId;//签约医生主键
    private String qyysxm;//签约医生姓名
    private String qyjg00;//签约机构
    private String qyjgName;//签约机构名称
    private String kssj00;//开始时间
    private String jzsj00;//截止时间
    private String je0000;//签约服务费
    private String jjzfe0;//基金支付金额
    private String grzfe0;//个人自付金额
    private String zhzfe0;//账户支付金额
    private String gwzfe0;//公卫支付金额

    public String getFunid() {
        return funid;
    }

    public void setFunid(String funid) {
        this.funid = funid;
    }

    public String getXm0000() {
        return xm0000;
    }

    public void setXm0000(String xm0000) {
        this.xm0000 = xm0000;
    }

    public String getSfzh00() {
        return sfzh00;
    }

    public void setSfzh00(String sfzh00) {
        this.sfzh00 = sfzh00;
    }

    public String getId0000() {
        return id0000;
    }

    public void setId0000(String id0000) {
        this.id0000 = id0000;
    }

    public String getQyysxm() {
        return qyysxm;
    }

    public void setQyysxm(String qyysxm) {
        this.qyysxm = qyysxm;
    }

    public String getQyjg00() {
        return qyjg00;
    }

    public void setQyjg00(String qyjg00) {
        this.qyjg00 = qyjg00;
    }

    public String getKssj00() {
        return kssj00;
    }

    public void setKssj00(Timestamp kssj00) {
        if(kssj00 != null){
            this.kssj00 = ExtendDate.getYMD_h_m_s(kssj00);
        }else{
            this.kssj00 = "";
        }
    }

    public String getJzsj00() {
        return jzsj00;
    }

    public void setJzsj00(Timestamp jzsj00) {
        if(jzsj00 != null){
            this.jzsj00 = ExtendDate.getYMD_h_m_s(jzsj00);
        }else{
            this.jzsj00 = "";
        }
    }

    public String getJe0000() {
        return je0000;
    }

    public void setJe0000(String je0000) {
        this.je0000 = je0000;
    }

    public String getJjzfe0() {
        return jjzfe0;
    }

    public void setJjzfe0(BigDecimal jjzfe0) {
        if(jjzfe0 != null){
            this.jjzfe0 = String.valueOf(jjzfe0);
        }else{
            this.jjzfe0 = "0";
        }
    }

    public String getGrzfe0() {
        return grzfe0;
    }

    public void setGrzfe0(BigDecimal grzfe0) {
        if(grzfe0 != null){
            this.grzfe0 = String.valueOf(grzfe0);
        }else{
            this.grzfe0 = "0";
        }
    }

    public String getZhzfe0() {
        return zhzfe0;
    }

    public void setZhzfe0(BigDecimal zhzfe0) {
        if(zhzfe0 != null){
            this.zhzfe0 = String.valueOf(zhzfe0);
        }else{
            this.zhzfe0 = "0";
        }
    }

    public String getGwzfe0() {
        return gwzfe0;
    }

    public void setGwzfe0(BigDecimal gwzfe0) {
        if(gwzfe0 != null){
            this.gwzfe0 = String.valueOf(gwzfe0);
        }else{
            this.gwzfe0 = "0";
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQyysId() {
        return qyysId;
    }

    public void setQyysId(String qyysId) {
        this.qyysId = qyysId;
    }

    public String getQyjgName() {
        return qyjgName;
    }

    public void setQyjgName(String qyjgName) {
        this.qyjgName = qyjgName;
    }
}
