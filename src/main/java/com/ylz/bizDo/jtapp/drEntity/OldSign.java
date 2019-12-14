package com.ylz.bizDo.jtapp.drEntity;

import com.ylz.bizDo.app.po.AppLabelManage;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;

/**签约单信息
 * Created by zzl on 2017/10/24.
 */
public class OldSign {
    private String patientId;//患者id
    private String fwName;//服务人群名称
    private String fwValue;//服务人群
    private String illName;//疾病类型名称
    private String illValue;//疾病类型值
    private String healthName;//健康情况名称
    private String healthValue;//健康情况值
    private String signId;//签约单id
    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getFwName() {
        return fwName;
    }

    public void setFwName(String fwName) {
        this.fwName = fwName;
    }

    public String getFwValue() {
        return fwValue;
    }

    public void setFwValue(String fwValue) {
        this.fwValue = fwValue;
    }

    public String getIllName() {
        return illName;
    }

    public void setIllName(String illName) {
        this.illName = illName;
    }

    public String getIllValue() {
        return illValue;
    }

    public void setIllValue(String illValue) {
        this.illValue = illValue;
    }

    public String getHealthName() {
        return healthName;
    }

    public void setHealthName(String healthName) throws Exception {
        if(StringUtils.isNotBlank(this.getHealthValue())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            AppLabelManage vo= dao.getAppLabelManageDao().findLabelByValue("1",this.getHealthValue());
            if(vo != null){
                healthName = vo.getLabelTitle();
            }
        }
        this.healthName = healthName;
    }

    public String getHealthValue() {
        return healthValue;
    }

    public void setHealthValue(String healthValue) {
        this.healthValue = healthValue;
    }

    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) {
        this.signId = signId;
    }
}
