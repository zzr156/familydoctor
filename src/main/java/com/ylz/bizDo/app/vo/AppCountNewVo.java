package com.ylz.bizDo.app.vo;

import com.ylz.packcommon.common.util.ExtendDate;

import java.sql.Timestamp;

/**
 * Created by TroubleMan-Cxw on 2018-01-15.
 * 首页统计表格数据（莆田）
 */
public class AppCountNewVo {
    private String orgId;//机构ID
    private String orgName;//机构名
    private String hospAreaCode;//机构编码
    private String hospLevel;//机构级别
    private int rksum; //人口总数
    private int qysum; //签约数
    private String qyrkl; //签约率
    private int jdlksum;//建档立卡管理数
    private int qyjdlk; // 建档立卡签约数
    private String qyjdlkl;// 建档立卡签约率
    private int zdrqsum;// 重点人群管理数
    private int qyzdrq; // 重点人群签约数
    private String qyzdrql;// 重点人群签约率

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getHospAreaCode() {
        return hospAreaCode;
    }

    public void setHospAreaCode(String hospAreaCode) {
        this.hospAreaCode = hospAreaCode;
    }

    public String getHospLevel() {
        return hospLevel;
    }

    public void setHospLevel(String hospLevel) {
        this.hospLevel = hospLevel;
    }

    public int getRksum() {
        return rksum;
    }

    public void setRksum(int rksum) {
        this.rksum = rksum;
    }

    public int getQysum() {
        return qysum;
    }

    public void setQysum(int qysum) {
        this.qysum = qysum;
    }

    public String getQyrkl() {
        return qyrkl;
    }

    public void setQyrkl(String qyrkl) {
        this.qyrkl = qyrkl;
    }

    public int getJdlksum() {
        return jdlksum;
    }

    public void setJdlksum(int jdlksum) {
        this.jdlksum = jdlksum;
    }

    public int getQyjdlk() {
        return qyjdlk;
    }

    public void setQyjdlk(int qyjdlk) {
        this.qyjdlk = qyjdlk;
    }

    public String getQyjdlkl() {
        return qyjdlkl;
    }

    public void setQyjdlkl(String qyjdlkl) {
        this.qyjdlkl = qyjdlkl;
    }

    public int getZdrqsum() {
        return zdrqsum;
    }

    public void setZdrqsum(int zdrqsum) {
        this.zdrqsum = zdrqsum;
    }

    public int getQyzdrq() {
        return qyzdrq;
    }

    public void setQyzdrq(int qyzdrq) {
        this.qyzdrq = qyzdrq;
    }

    public String getQyzdrql() {
        return qyzdrql;
    }

    public void setQyzdrql(String qyzdrql) {
        this.qyzdrql = qyzdrql;
    }
}
