package com.ylz.bizDo.jtapp.commonEntity;

import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**中医健康指导履约记录
 * Created by zzl on 2017/11/16.
 */
public class AppTcmLyEntity {
    private String countTj;//统计当前时间之前有几次月龄体检
    private String countGuide;//统计指导次数
    private String patientId;
    private String patientIdNo;
    private String drId;
    private String signId;

    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) {
        this.signId = signId;
    }

    public String getCountGuide() {
        return countGuide;
    }

    public void setCountGuide(BigInteger countGuide) {
        if(countGuide!=null){
            this.countGuide = String.valueOf(countGuide);
        }
        this.countGuide = "0";
    }

    public String getCountTj() {
        return countTj;
    }

    public void setCountTj(BigInteger countTj) {
        if(countTj!=null){
            this.countTj = String.valueOf(countTj);
        }
        this.countTj = "0";
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientIdNo() {
        return patientIdNo;
    }

    public void setPatientIdNo(String patientIdNo) {
        this.patientIdNo = patientIdNo;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

}
