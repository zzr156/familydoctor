package com.ylz.bizDo.app.entity;

import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packaccede.util.CardUtil;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.comEnum.SignFormType;
import com.ylz.packcommon.common.util.AgeUtil;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by asus on 2017/11/22.
 */
public class AppPerformanceRecordEntity {
    private String patientId;//主键
    private String patientName;//姓名
    private String patientIdNo;//身份证
    private String patientAge;//年龄
    private String patientGender;//性别
    private String patientImageUrl;//头像
    private String patientImageName;//头像名称
    private String signId;//签约单id
    private String fwType;//服务类型

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

    public String getPatientIdNo() {
        return patientIdNo;
    }

    public void setPatientIdNo(String patientIdNo) {
        this.patientIdNo = patientIdNo;
    }

    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) {
        this.signId = signId;
    }

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(String patientAge) {
        Map<String,Object> idNos = new HashMap<String,Object>();
        try {
            if(StringUtils.isNotBlank(this.getPatientIdNo())){
                if(this.getPatientIdNo().length() == 18){
                    idNos = CardUtil.getCarInfo(this.getPatientIdNo());
                }else{
                    idNos = CardUtil.getCarInfo15W(this.getPatientIdNo());
                }
                String birthday = idNos.get("birthday").toString();
                patientAge = AgeUtil.getAgeYear(ExtendDate.getCalendar(birthday));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        this.patientAge = patientAge;
    }

    public String getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(String patientGender) {
        this.patientGender = patientGender;
    }

    public String getPatientImageUrl() {
        return patientImageUrl;
    }

    public void setPatientImageUrl(String patientImageUrl) {
        this.patientImageUrl = patientImageUrl;
    }

    public String getPatientImageName() {
        return patientImageName;
    }

    public void setPatientImageName(String patientImageName) {
        this.patientImageName = patientImageName;
    }

    public String getFwType() {
        return fwType;
    }

    public void setFwType(String fwType) {
        if(StringUtils.isNotBlank(this.getSignId())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            Map<String,Object> map = new HashMap<>();
            map.put("signId",this.getSignId());
            String sql = "SELECT GROUP_CONCAT(a.LABEL_VALUE) fwType FROM APP_LABEL_GROUP a\n" +
                    "WHERE \n" +
                    "a.LABEL_SIGN_ID=:signId\n" +
                    "AND a.LABEL_TYPE='3'";
            List<Map> maps = dao.getServiceDo().findSqlMap(sql,map);
            if(maps !=null && maps.size()>0){
                if(maps.get(0)!=null){
                    fwType = maps.get(0).get("fwType").toString();
                }

            }
        }
        this.fwType = fwType;
    }
}
