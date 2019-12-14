package com.ylz.bizDo.smjq.smEntity;

import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 居民基础信息
 * Created by zzl on 2018/7/25.
 */
public class AppSmPeopleBasicEntity {
    private String patientId;//用户id
    private String patientName;//姓名
    private String patientIdno;//身份证
    private String paitentTel;//联系电话
    private String teamId;//团队id
    private String teamName;//团队名称
    private String orgId;//机构id
    private String orgName;//机构名称
    private String province;//省份
    private String city;//城市
    private String area;//区县
    private String street;//街道
    private String village;//居委会
    private String addr;//地址
    private String xaxis;//
    private String yaxis;//
    private String drId;//医生id
    private String drName;//医生姓名
    private String drTel;//医生电话
    private List<AppSmHbpEntity> listHbp;//血压数据
    private List<AppBloodSugarEntity> listBS;//血糖数据

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
        String address = "";
        address = this.getProvince()+this.getCity()+this.getArea()+this.getStreet()+addr;
        this.addr = address;
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

    public List<AppSmHbpEntity> getListHbp() {
        return listHbp;
    }

    public void setListHbp(List<AppSmHbpEntity> listHbp) {
        this.listHbp = listHbp;
    }

    public List<AppBloodSugarEntity> getListBS() {
        return listBS;
    }

    public void setListBS(List<AppBloodSugarEntity> listBS) {
        this.listBS = listBS;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        if(StringUtils.isNotBlank(village)){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdAddress address = (CdAddress) dao.getServiceDo().find(CdAddress.class, village);
            if(address != null) {
                village = address.getAreaSname();
            }
        }
        this.village = village;
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
}
