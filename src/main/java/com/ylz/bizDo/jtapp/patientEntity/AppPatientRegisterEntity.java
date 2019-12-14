package com.ylz.bizDo.jtapp.patientEntity;

import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.cd.po.CdAddressConfiguration;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.util.AreaUtils;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang3.StringUtils;

import java.sql.Date;

/**
 * 登录
 */
public class AppPatientRegisterEntity {
    private String id;
    private String patientName;//名字
    private String patientGender;//性别
    private String patientBirthday;//出生日期
    private String patientIdno;//身份证号
    private String patientCard;//社保卡
    private String patientAge;//年龄
    private String patientTel;//电话
    private String patientAddress;//地址
    private String patientImageurl;//头像
    private String patientImageName;//头像图片后缀
    private String patientProvince;//省
    private String patientCity;//市
    private String patientArea;//区
    private String patientStreet;//街道
    private String patientNeighborhoodCommittee;//居委会
    private String patientCommunity;//社区
    private String patientProvinceName;//省
    private String patientCityName;//市
    private String patientAreaName;//区
    private String patientStreetName;//街道
    private String patientNeighborhoodCommitteeName;//居委会
    private String cityCode;//城市代码


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(String patientGender) {
            this.patientGender = patientGender;

    }

    public String getPatientBirthday() {
        return patientBirthday;
    }

    public void setPatientBirthday(Date patientBirthday) {
        if(patientBirthday != null) {
            this.patientBirthday = ExtendDate.getYMD(patientBirthday);
        }else{
            this.patientBirthday = "";
        }
    }

    public String getPatientIdno() {
        return patientIdno;
    }

    public void setPatientIdno(String patientIdno) {
        this.patientIdno = patientIdno;
    }

    public String getPatientCard() {
        return patientCard;
    }

    public void setPatientCard(String patientCard) {
        this.patientCard = patientCard;
    }

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(String patientAge) {
        this.patientAge = patientAge;
    }

    public String getPatientTel() {
        return patientTel;
    }

    public void setPatientTel(String patientTel) {
        this.patientTel = patientTel;
    }

    public String getPatientAddress() {
        return patientAddress;
    }

    public void setPatientAddress(String patientAddress) {
        this.patientAddress = patientAddress;
    }

    public String getPatientImageurl() {
        return patientImageurl;
    }

    public void setPatientImageurl(String patientImageurl) {
        this.patientImageurl = patientImageurl;
    }

    public String getPatientProvince() {
        return patientProvince;
    }

    public void setPatientProvince(String patientProvince) {
        this.patientProvince = patientProvince;
    }

    public String getPatientCity() {
        return patientCity;
    }

    public void setPatientCity(String patientCity) {
        this.patientCity = patientCity;
    }

    public String getPatientArea() {
        return patientArea;
    }

    public void setPatientArea(String patientArea) {
        this.patientArea = patientArea;
    }

    public String getPatientStreet() {
        return patientStreet;
    }

    public void setPatientStreet(String patientStreet) {
        this.patientStreet = patientStreet;
    }

    public String getPatientCommunity() {
        return patientCommunity;
    }

    public void setPatientCommunity(String patientCommunity) {
        this.patientCommunity = patientCommunity;
    }

    public String getPatientImageName() {
        return patientImageName;
    }

    public void setPatientImageName(String patientImageName) {
        this.patientImageName = patientImageName;
    }

    public String getPatientNeighborhoodCommittee() {
        return patientNeighborhoodCommittee;
    }

    public void setPatientNeighborhoodCommittee(String patientNeighborhoodCommittee) {
        this.patientNeighborhoodCommittee = patientNeighborhoodCommittee;
    }

    public String getPatientProvinceName() {
        return patientProvinceName;
    }

    public void setPatientProvinceName(String patientProvinceName) throws Exception {
        SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
        CdAddress address = dao.getCdAddressDao().findByCode(patientProvinceName);
        if(address!=null) {
            this.patientProvinceName = address.getAreaSname();
        }else{
            CdAddressConfiguration addressConfiguration = dao.getCdAddressDao().findByCodeJw(patientProvinceName);
            if(addressConfiguration != null){
                this.patientProvinceName = addressConfiguration.getAreaNameJw();
            }
        }
    }

    public String getPatientCityName() {
        return patientCityName;
    }

    public void setPatientCityName(String patientCityName) throws Exception {
        SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
        CdAddress address = dao.getCdAddressDao().findByCode(patientCityName);
        if(address!=null) {
            this.patientCityName = address.getAreaSname();
        }else{
            CdAddressConfiguration addressConfiguration = dao.getCdAddressDao().findByCodeJw(patientCityName);
            if(addressConfiguration != null){
                this.patientCityName = addressConfiguration.getAreaNameJw();
            }
        }
    }

    public String getPatientAreaName() {
        return patientAreaName;
    }

    public void setPatientAreaName(String patientAreaName) throws Exception {
        SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
        CdAddress address = dao.getCdAddressDao().findByCode(patientAreaName);
        if(address!=null) {
            this.patientAreaName = address.getAreaSname();
        }else{
            CdAddressConfiguration addressConfiguration = dao.getCdAddressDao().findByCodeJw(patientAreaName);
            if(addressConfiguration != null){
                this.patientAreaName = addressConfiguration.getAreaNameJw();
            }
        }
    }

    public String getPatientStreetName() {
        return patientStreetName;
    }

    public void setPatientStreetName(String patientStreetName) throws Exception {
        SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
        CdAddress address = dao.getCdAddressDao().findByCode(patientStreetName);
        if(address!=null) {
            this.patientStreetName = address.getAreaSname();
        }else{
            CdAddressConfiguration addressConfiguration = dao.getCdAddressDao().findByCodeJw(patientStreetName);
            if(addressConfiguration != null){
                this.patientStreetName = addressConfiguration.getAreaNameJw();
            }
        }
    }

    public String getPatientNeighborhoodCommitteeName() {
        return patientNeighborhoodCommitteeName;
    }

    public void setPatientNeighborhoodCommitteeName(String patientNeighborhoodCommitteeName) throws Exception {
        SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
        if(StringUtils.isNotBlank(patientNeighborhoodCommitteeName)){
            CdAddress address = dao.getCdAddressDao().findByCode(patientNeighborhoodCommitteeName);
            if(address!=null) {
                this.patientNeighborhoodCommitteeName = address.getAreaSname();
            }else{
                CdAddressConfiguration addressConfiguration = dao.getCdAddressDao().findByCodeJw(patientNeighborhoodCommitteeName);
                if(addressConfiguration != null){
                    this.patientNeighborhoodCommitteeName = addressConfiguration.getAreaNameJw();
                }
            }
        }else {
            this.patientNeighborhoodCommitteeName = "";
        }
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode)  throws Exception{
        SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
        CdAddress p = dao.getCdAddressDao().findByCode(cityCode);
        if(p != null){
            String code = AreaUtils.getAreaCode(p.getCtcode(),p.getLevel());
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CITYCODE,code);
            if(value != null) {
                this.cityCode = value.getCodeTitle();
            }
        }else{
            this.cityCode = cityCode;
        }
    }

}
