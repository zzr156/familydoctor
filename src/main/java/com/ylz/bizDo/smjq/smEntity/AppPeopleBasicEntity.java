package com.ylz.bizDo.smjq.smEntity;

import com.ylz.bizDo.app.po.AppUserBloodglu;
import com.ylz.bizDo.app.po.AppUserBloodpressure;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by zzl on 2018/7/25.
 */
public class AppPeopleBasicEntity {
    private String patientId;//用户id
    private String patientName;//姓名
    private String patientIdno;//身份证
    private String patientGender;//性别
    private String paitentTel;//联系电话
    private String teamId;//团队id
    private String teamName;//团队名称
    private String orgId;//机构id
    private String orgName;//机构名称
    private String province;//省份
    private String city;//城市
    private String area;//区县
    private String street;//街道
    private String addr;//地址
    private String xaxis;//
    private String yaxis;//
    private String labelValue;//标签值
    private String labelTitle;//标签
    private String labelColor;//颜色
    private String patientJmdah;//居民档案号
    private String drId;//医生id
    private String drName;//医生名称
    private String drTel;//医生电话
    private String gluValue;//是否有血糖数据（1有 0没有）
    private String hbpValue;//是否有血压数据（1有 0没有）
    private BigInteger countT;
//    private String

    public String getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(String patientGender) {
        this.patientGender = patientGender;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientIdno() {
        return patientIdno;
    }

    public void setPatientIdno(String patientIdno) {
        this.patientIdno = patientIdno;
    }

    public String getPaitentTel() {
        return paitentTel;
    }

    public void setPaitentTel(String paitentTel) {
        this.paitentTel = paitentTel;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {

        if(StringUtils.isNotBlank(province)){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdAddress address = (CdAddress) dao.getServiceDo().find(CdAddress.class, province);
            if(address != null) {
                province = address.getAreaSname();
            }
        }
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        if(StringUtils.isNotBlank(city)){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdAddress address = (CdAddress) dao.getServiceDo().find(CdAddress.class, city);
            if(address != null) {
                city = address.getAreaSname();
            }
        }
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        if(StringUtils.isNotBlank(area)){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdAddress address = (CdAddress) dao.getServiceDo().find(CdAddress.class, area);
            if(address != null) {
                area = address.getAreaSname();
            }
        }
        this.area = area;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        if(StringUtils.isNotBlank(street)){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdAddress address = (CdAddress) dao.getServiceDo().find(CdAddress.class, street);
            if(address != null) {
                street = address.getAreaSname();
            }
        }
        this.street = street;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getXaxis() {
        return xaxis;
    }

    public void setXaxis(String xaxis) {
        this.xaxis = xaxis;
    }

    public String getYaxis() {
        return yaxis;
    }

    public void setYaxis(String yaxis) {
        this.yaxis = yaxis;
    }

    public String getLabelTitle() {
        return labelTitle;
    }

    public void setLabelTitle(String labelTitle) {
        this.labelTitle = labelTitle;
    }

    public String getLabelColor() {
        return labelColor;
    }

    public void setLabelColor(String labelColor) {
        this.labelColor = labelColor;
    }

    public String getLabelValue() {
        return labelValue;
    }

    public void setLabelValue(String labelValue) {
        this.labelValue = labelValue;
    }

    public String getPatientJmdah() {
        return patientJmdah;
    }

    public void setPatientJmdah(String patientJmdah) {
        this.patientJmdah = patientJmdah;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public String getDrTel() {
        return drTel;
    }

    public void setDrTel(String drTel) {
        this.drTel = drTel;
    }

    public String getGluValue() {
        return gluValue;
    }

    public void setGluValue(String gluValue) {
        gluValue = "0";
        if(StringUtils.isNotBlank(this.getPatientId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            List<AppUserBloodglu> list = dao.getServiceDo().loadByPk(AppUserBloodglu.class,"ugUserId",this.getPatientId());
            if(list != null && list.size()>0){
                gluValue = "1";
            }
        }
        this.gluValue = gluValue;
    }

    public String getHbpValue() {
        return hbpValue;
    }

    public void setHbpValue(String hbpValue) {
        hbpValue = "0";
        if(StringUtils.isNotBlank(this.getPatientId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            List<AppUserBloodpressure> list = dao.getServiceDo().loadByPk(AppUserBloodpressure.class,"upUserId",this.getPatientId());
            if(list != null && list.size()>0){
                hbpValue = "1";
            }
        }
        this.hbpValue = hbpValue;
    }

    public BigInteger getCountT() {
        return countT;
    }

    public void setCountT(BigInteger countT) {
        this.countT = countT;
    }
}
