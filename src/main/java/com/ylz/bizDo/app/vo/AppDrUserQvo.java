package com.ylz.bizDo.app.vo;

import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CommConditionVo;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by zzl on 2017/6/15.
 */
public class AppDrUserQvo extends CommConditionVo {
    private String appDrAccount;//账号
    private String appDrName;//姓名
    private String drHospId;//所属医院
    private String appDrState;//医生状态
    private String pro;//省
    private String city;//市
    private String area;//区

    public String getAppDrAccount() {
        return appDrAccount;
    }

    public void setAppDrAccount(String appDrAccount) {
        this.appDrAccount = appDrAccount;
    }

    public String getAppDrName() {
        return appDrName;
    }

    public void setAppDrName(String appDrName) {
        this.appDrName = appDrName;
    }

    public String getAppDrState() {
        return appDrState;
    }

    public void setAppDrState(String appDrState) {
        this.appDrState = appDrState;
    }

    public String getDrHospId() {
        return drHospId;
    }

    public void setDrHospId(String drHospId) {
        this.drHospId = drHospId;
    }
    //医院名称
    public String getDrHospName(){
        SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
        if(StringUtils.isNotBlank(this.getDrHospId())){
            AppHospDept hosp = (AppHospDept) dao.getServiceDo().find(AppHospDept.class,this.getDrHospId());
            if(hosp!=null){
                if(StringUtils.isNotBlank(hosp.getHospName())){
                    return hosp.getHospName();
                }
            }
        }
        return  "";
    }

    public String getPro() {
        return pro;
    }

    public void setPro(String pro) {
        this.pro = pro;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
