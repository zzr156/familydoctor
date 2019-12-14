package com.ylz.bizDo.smjq.smEntity;

import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;

/**
 * Created by zzl on 2018/8/14.
 */
public class AppSmyxPatientEntity {
    private String patientId;//居民主键
    private String patientName;//居民姓名
    private String addr;//地址

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

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        String address = "";
        if(StringUtils.isNotBlank(this.getPatientId())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            AppPatientUser user = (AppPatientUser)dao.getServiceDo().find(AppPatientUser.class,this.getPatientId());
            if(user != null){
                if(StringUtils.isNotBlank(user.getPatientProvince())){
                    CdAddress provience = (CdAddress) dao.getServiceDo().find(CdAddress.class, user.getPatientProvince());
                    if(provience != null) {
                        address += provience.getAreaSname();
                    }
                }
                if(StringUtils.isNotBlank(user.getPatientCity())){
                    CdAddress city = (CdAddress) dao.getServiceDo().find(CdAddress.class, user.getPatientCity());
                    if(city != null) {
                        address += city.getAreaSname();
                    }
                }
                if(StringUtils.isNotBlank(user.getPatientArea())){
                    CdAddress area = (CdAddress) dao.getServiceDo().find(CdAddress.class, user.getPatientArea());
                    if(area != null) {
                        address += area.getAreaSname();
                    }
                }
                if(StringUtils.isNotBlank(user.getPatientStreet())){
                    CdAddress street = (CdAddress) dao.getServiceDo().find(CdAddress.class, user.getPatientStreet());
                    if(street != null) {
                        address += street.getAreaSname();
                    }
                }
                if(StringUtils.isNotBlank(user.getPatientNeighborhoodCommittee())){
                    CdAddress committee = (CdAddress) dao.getServiceDo().find(CdAddress.class, user.getPatientNeighborhoodCommittee());
                    if(committee != null) {
                        address += committee.getAreaSname();
                    }
                }
                if(StringUtils.isNotBlank(user.getPatientAddress())){
                    address += user.getPatientAddress();
                }
            }
        }
        this.addr = address;
    }
}
