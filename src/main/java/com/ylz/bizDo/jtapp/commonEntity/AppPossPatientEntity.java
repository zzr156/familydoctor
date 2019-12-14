package com.ylz.bizDo.jtapp.commonEntity;

import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.cd.po.CdAddressConfiguration;
import com.ylz.bizDo.jtapp.drEntity.AppServeEntity;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang3.StringUtils;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 漳浦手持机服务记录 查询居民信息返回对象
 * Created by zzl on 2018/10/30.
 */
public class AppPossPatientEntity {
    private String patientId;//居民主键
    private String patientName;//居民姓名
    private String patientSex;//性别
    private String patientIdno;//居民身份证
    private String patientBirthDay;//出生日期
    private String patientAddr;//地址
    private String patientTel;//电话
    private String signId;//签约单id
    private String fwValue;//服务类型值
    private String fwTitle;//服务类型名称
    private String jjValue;//经济类型值
    private String jjTitle;//经济类型名称
    private String signFromDate;//签约开始时间
    private String signToDate;//签约到期时间
    private String emergencyContactName;//紧急联系人姓名
    private String emergencyContactTel;//紧急联系人电话
    private String signDrId;//团队医生主键
    private String signDrName;//团队医生姓名
    private String signDrTel;//团队医生电话
    private String signDrAssistantId;//助理医生主键
    private String signDrAssistantName;//助理医生姓名
    private String signDrAssistantTel;//助理医生电话



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

    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) {
        this.signId = signId;
    }

    public String getFwValue() {
        return fwValue;
    }

    public void setFwValue(String fwValue) {
        this.fwValue = fwValue;
    }

    public String getFwTitle() {
        return fwTitle;
    }

    public void setFwTitle(String fwTitle) {
        this.fwTitle = fwTitle;
    }

    public String getPatientIdno() {
        return patientIdno;
    }

    public void setPatientIdno(String patientIdno) {
        this.patientIdno = patientIdno;
    }

    public String getSignFromDate() {
        return signFromDate;
    }

    public void setSignFromDate(Timestamp signFromDate) {
        if(signFromDate != null){
            this.signFromDate = ExtendDate.getChineseYMD(signFromDate);
        }else{
            this.signFromDate = "";
        }

    }

    public String getSignToDate() {
        return signToDate;
    }

    public void setSignToDate(Timestamp signToDate) {
        if(signToDate != null){
            this.signToDate = ExtendDate.getChineseYMD(signToDate);
        }else{
            this.signToDate = "";
        }
    }

    public String getPatientSex() {
        return patientSex;
    }

    public void setPatientSex(String patientSex) {
        this.patientSex = patientSex;
    }

    public String getPatientBirthDay() {
        return patientBirthDay;
    }

    public void setPatientBirthDay(Date patientBirthDay) {
        if(patientBirthDay != null){
            this.patientBirthDay = ExtendDate.getYMD(patientBirthDay);
        }else{
            this.patientBirthDay = "";
        }
    }

    public String getPatientAddr() {
        return patientAddr;
    }

    public void setPatientAddr(String patientAddr) throws Exception {
        String pro = "";
        String city = "";
        String area = "";
        String street = "";
        String cun = "";
        if(StringUtils.isNotBlank(this.getPatientId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            AppPatientUser user = (AppPatientUser)dao.getServiceDo().find(AppPatientUser.class,this.getPatientId());
            if(user != null){//查询地址信息
                if(StringUtils.isNotBlank(user.getPatientNeighborhoodCommittee())){
                    CdAddress address = dao.getCdAddressDao().findByCacheCode(user.getPatientNeighborhoodCommittee());
                    if(address != null){
                        cun = address.getAreaSname();
                    }else{
                        CdAddressConfiguration cAddress = dao.getCdAddressDao().findByCodeJw(user.getPatientNeighborhoodCommittee());
                        if(cAddress != null){
                            cun = cAddress.getAreaName();
                        }
                    }
                }
                if(StringUtils.isNotBlank(user.getPatientStreet())){
                    CdAddress address = dao.getCdAddressDao().findByCacheCode(user.getPatientStreet());
                    if(address != null){
                        street = address.getAreaSname();
                    }else{
                        CdAddressConfiguration cAddress = dao.getCdAddressDao().findByCodeJw(user.getPatientStreet());
                        if(cAddress != null){
                            street = cAddress.getAreaName();
                        }
                    }
                }
                if(StringUtils.isNotBlank(user.getPatientArea())){
                    CdAddress address = dao.getCdAddressDao().findByCacheCode(user.getPatientArea());
                    if(address != null){
                        area = address.getAreaSname();
                    }else{
                        CdAddressConfiguration cAddress = dao.getCdAddressDao().findByCodeJw(user.getPatientArea());
                        if(cAddress != null){
                            area = cAddress.getAreaName();
                        }
                    }
                }
                if(StringUtils.isNotBlank(user.getPatientCity())){
                    CdAddress address = dao.getCdAddressDao().findByCacheCode(user.getPatientCity());
                    if(address != null){
                        city = address.getAreaSname();
                    }else{
                        CdAddressConfiguration cAddress = dao.getCdAddressDao().findByCodeJw(user.getPatientCity());
                        if(cAddress != null){
                            city = cAddress.getAreaName();
                        }
                    }
                }
                if(StringUtils.isNotBlank(user.getPatientProvince())){
                    CdAddress address = dao.getCdAddressDao().findByCacheCode(user.getPatientProvince());
                    if(address != null){
                        pro = address.getAreaSname();
                    }
                }
            }
        }
        this.patientAddr = pro+city+area+street+cun+patientAddr;
    }

    public String getPatientTel() {
        return patientTel;
    }

    public void setPatientTel(String patientTel) {
        this.patientTel = patientTel;
    }

    public String getJjValue() {
        return jjValue;
    }

    public void setJjValue(String jjValue) {
        this.jjValue = jjValue;
    }

    public String getJjTitle() {
        return jjTitle;
    }

    public void setJjTitle(String jjTitle) {
        this.jjTitle = jjTitle;
    }

    public String getEmergencyContactName() {
        return emergencyContactName;
    }

    public void setEmergencyContactName(String emergencyContactName) {
        this.emergencyContactName = emergencyContactName;
    }

    public String getEmergencyContactTel() {
        return emergencyContactTel;
    }

    public void setEmergencyContactTel(String emergencyContactTel) {
        this.emergencyContactTel = emergencyContactTel;
    }

    public String getSignDrId() {
        return signDrId;
    }

    public void setSignDrId(String signDrId) {
        this.signDrId = signDrId;
    }

    public String getSignDrName() {
        return signDrName;
    }

    public void setSignDrName(String signDrName) {
        this.signDrName = signDrName;
    }

    public String getSignDrTel() {
        return signDrTel;
    }

    public void setSignDrTel(String signDrTel) {
        this.signDrTel = signDrTel;
    }

    public String getSignDrAssistantId() {
        return signDrAssistantId;
    }

    public void setSignDrAssistantId(String signDrAssistantId) {
        this.signDrAssistantId = signDrAssistantId;
    }

    public String getSignDrAssistantName() {
        return signDrAssistantName;
    }

    public void setSignDrAssistantName(String signDrAssistantName) {
        this.signDrAssistantName = signDrAssistantName;
    }

    public String getSignDrAssistantTel() {
        return signDrAssistantTel;
    }

    public void setSignDrAssistantTel(String signDrAssistantTel) {
        this.signDrAssistantTel = signDrAssistantTel;
    }
}
