package com.ylz.bizDo.mangecount.entity;

import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;

/**
 * Created by zzl on 2018/5/30.
 */
public class ManageTeamCountEntity {
    private String rate;//率
    private String teamName;//团队名称
    private String address;//地址
    private String hospName;//医院名称

    public String getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        if(rate != null){
            String ss = String.valueOf(rate).substring(0,2);
            if(Integer.parseInt(ss)<=20){
                this.rate = ss;
            }else{
                int xx = Integer.parseInt(ss)-20;
                this.rate = String.valueOf(xx);
            }
        }else{
            this.rate = "0";
        }
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        String addressName = "";
        if(StringUtils.isNotBlank(address)){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdAddress addr = (CdAddress)dao.getServiceDo().find(CdAddress.class,address);
            if(addr != null){
                addressName = addr.getAreaName();
            }
        }
        this.address = addressName;
    }

    public String getHospName() {
        return hospName;
    }

    public void setHospName(String hospName) {
        this.hospName = hospName;
    }
}
