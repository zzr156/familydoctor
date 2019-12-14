package com.ylz.bizDo.jtapp.drEntity;


import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.comEnum.PerformanceType;
import org.apache.commons.lang3.StringUtils;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**履约人员列表
 * Created by zzl on 2017/11/13.
 */
public class AppDrSignPeoleListEntity {
    private String patientId;//患者id
    private String fwbTitle;//服务包
    private String fwbValue;
    private String fwrqTitle;//服务人群
    private String fwrqValue;
    private String patientIdno;//身份证
    private String signId;//签约单id
    private String drId;//医生id
    private String signCount;//完成次数
    private String followType;//随访类型（定期随访）
    private String serSpace;//频次

    public String getSerSpace() {
        return serSpace;
    }

    public void setSerSpace(String serSpace) {
        this.serSpace = serSpace;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientIdno() {
        return patientIdno;
    }

    public void setPatientIdno(String patientIdno) {
        this.patientIdno = patientIdno;
    }

    public String getFwbTitle() {
        return fwbTitle;
    }

    public void setFwbTitle(String fwbTitle) {
        this.fwbTitle = fwbTitle;
    }

    public String getFwbValue() {
        return fwbValue;
    }

    public void setFwbValue(String fwbValue) {
        this.fwbValue = fwbValue;
    }

    public String getFwrqTitle() {
        return fwrqTitle;
    }

    public void setFwrqTitle(String fwrqTitle) {
        this.fwrqTitle = fwrqTitle;
    }

    public String getFwrqValue() {
        return fwrqValue;
    }

    public void setFwrqValue(String fwrqValue) {
        this.fwrqValue = fwrqValue;
    }

    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) {
        this.signId = signId;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getFollowType() {
        return followType;
    }

    public void setFollowType(String followType) {
        this.followType = followType;
    }

    public String getSignCount() {
        return signCount;
    }

    public void setSignCount(String signCount) {
        SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
        Map<String,Object> map = new HashMap<>();
        Calendar cal = Calendar.getInstance();
        map.put("year",cal.get(Calendar.YEAR));
        if(StringUtils.isNotBlank(this.getFwbValue())){
            map.put("idno",this.getPatientIdno());
            map.put("drId",this.getDrId());
            String sql = "SELECT COUNT(1) FROM ";
            if(PerformanceType.JKJY.getValue().equals(this.getFwbValue())){//健康教育
                sql +=" APP_PERFORMANCE_HEALTH_EDUCATION a WHERE 1=1";
            }else if(PerformanceType.JKZD.getValue().equals(this.getFwbValue())){//健康指导
                sql +=" APP_PERFORMANCE_HEALTH_GUIDANCE a WHERE 1=1";
            }else if(PerformanceType.YYZD.getValue().equals(this.getFwbValue())){//用药指导
                sql +=" APP_PERFORMANCE_MEDICATION_GUIDANCE a WHERE 1=1";
            }else if(PerformanceType.JKTJ.getValue().equals(this.getFwbValue())){//健康体检
                sql +=" APP_PERFORMANCE_PHYSICAL_EXAMINATION a WHERE 1=1";
            }
            sql +=" AND a.PER_YEAR=:year AND a.PER_IDNO=:idno AND PER_DR_ID=:drId";
            int count = dao.getServiceDo().findCount(sql,map);
            signCount=String.valueOf(count);
        }else if(StringUtils.isNotBlank(this.getFollowType())){
            map.put("followType",this.getFollowType());
            String sql = "";
            if("1".equals(this.getFollowType())){
                sql =  "SELECT\n" +
                        "\t\t\tcount(1)\n" +
                        "\t\tFROM\n" +
                        "\t\t\tAPP_PERFORMANCE_REGULAR_FOLLOWUP\n" +
                        "\t\tWHERE\n" +
                        "\t\t\tPER_IDNO = a.SIGN_PATIENT_IDNO\n" +
                        "\t\tAND PER_DR_ID = a.SIGN_DR_ID\n" +
                        "\t\tAND PER_FOLLOW_TYPE = :followType\n" +
                        "\t\tAND PER_YEAR = :year\n" +
                        "\t\tAND TIMESTAMPDIFF(\n" +
                        "\t\t\tDAY,\n" +
                        "\t\t\tb.SCOW_OUT_HOSPITAL_DATE,\n" +
                        "\t\t\tPER_CREATE_DATE\n" +
                        "\t\t) <= 7\n" ;
                int count = dao.getServiceDo().findCount(sql,map);
                signCount=String.valueOf(count);
            }
        }
        this.signCount = signCount;
    }
}
