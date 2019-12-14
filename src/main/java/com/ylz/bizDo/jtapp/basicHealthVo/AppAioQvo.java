package com.ylz.bizDo.jtapp.basicHealthVo;

/**
 * Created by zzl on 2018/9/20.
 */
public class AppAioQvo {
    private String idno;//身份证
    private String dataSource;//数据来源 不传时获取所有（多个用“，”隔开，例：”yituo,mindray”）
    private String startDate;//查询开始时间	格式:yyyymmdd hh24:mi:ss
    private String endDate;//查询结束时间	格式:yyyymmdd hh24:mi:ss
    private String uploadOrgId;//机构ID带区域编码，例：SM_6943 不传时获取所有
    private String urlType;

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getUploadOrgId() {
        return uploadOrgId;
    }

    public void setUploadOrgId(String uploadOrgId) {
        this.uploadOrgId = uploadOrgId;
    }

    public String getUrlType() {
        return urlType;
    }

    public void setUrlType(String urlType) {
        this.urlType = urlType;
    }
}
