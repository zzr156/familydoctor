package com.ylz.bizDo.Motoe.dao.vo;

import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2017/12/21.
 */
public class MotoeVo {

    private String ptName;
    private String ptsignDate;
    private String ptBirthday;
    private String ptIdNo;
    private String ptzdrq;
    private String ptGender;
    private String ptId;
    private String ptTeam;
    private String patientjmda;
    private String ptjjrk;
    private String type;
    private String signId;

    public String getPtId() {
        return ptId;
    }

    public void setPtId(String ptId) {
        this.ptId = ptId;
    }

    public String getPtTeam() {
        return ptTeam;
    }

    public void setPtTeam(String ptTeam) {
        this.ptTeam = ptTeam;
    }

    public String getPtName() {
        return ptName;
    }

    public void setPtName(String ptName) {
        this.ptName = ptName;
    }

    public String getPtsignDate() {
        return ptsignDate;
    }

    public void setPtsignDate(Timestamp ptsignDate) {
        if(ptsignDate != null) {
            this.ptsignDate = ExtendDate.getYMD(ptsignDate);
        }
    }

    public String getPtBirthday() {
        return ptBirthday;
    }

    public void setPtBirthday(Date ptBirthday) {
        if(ptBirthday != null) {
            this.ptBirthday = ExtendDate.getYMD(ptBirthday);
        }
    }

    public String getPtIdNo() {
        return ptIdNo;
    }

    public void setPtIdNo(String ptIdNo) {
        this.ptIdNo = ptIdNo;
    }

    public String getPtzdrq() {
        return ptzdrq;
    }

    public void setPtzdrq(String ptzdrq) {
        String result = ptzdrq;
        if(StringUtils.isBlank(this.getType())){
            if(StringUtils.isNotBlank(this.getSignId())){
                SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
                Map<String,Object> map = new HashMap<String,Object>();
                map.put("LABEL_SIGN_ID",this.getSignId());
                String sql = "SELECT\n" +
                        "\t\t\tGROUP_CONCAT(LABEL_TITLE) LABEL_TITLE\n" +
                        "\t\tFROM\n" +
                        "\t\t\tAPP_LABEL_GROUP g\n" +
                        "\t\tWHERE\n" +
                        "\t\t\tg.LABEL_TYPE = '3'\n" +
                        "\t\tAND g.LABEL_SIGN_ID = :LABEL_SIGN_ID ";
                if(StringUtils.isNotBlank(this.getType())){
                    if(this.getType().equals("1")){
                        sql +=" and g.LABEL_VALUE not in('1','99') ";
                    }else{
                        sql +=" and g.LABEL_VALUE  in('1') ";
                    }
                }
                List<Map> ls = dao.getServiceDo().findSqlMap(sql,map);
                if(ls != null && ls.size() >0){
                    if(ls.get(0).get("LABEL_TITLE") != null){
                        result = ls.get(0).get("LABEL_TITLE").toString();
                    }
                }
                this.ptzdrq = result;
            }
        }else {
            this.ptzdrq = result;
        }

    }

    public String getPtGender() {
        return ptGender;
    }

    public void setPtGender(String ptGender) {
        if(StringUtils.isNotBlank(ptGender)){
            if(ptGender.equals("1")){
                this.ptGender = "男";
            }
            if(ptGender.equals("2")){
                this.ptGender = "女";
            }
        }else{
            this.ptGender = "";
        }

    }

    public String getPatientjmda() {
        return patientjmda;
    }

    public void setPatientjmda(String patientjmda) {
        this.patientjmda = patientjmda;
    }

    public String getPtjjrk() {
        return ptjjrk;
    }

    public void setPtjjrk(String ptjjrk) {

        String result = ptjjrk;
        if(StringUtils.isBlank(this.type)){
            if(StringUtils.isNotBlank(this.getSignId())){
                SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
                Map<String,Object> map = new HashMap<String,Object>();
                map.put("LABEL_SIGN_ID",this.getSignId());
                String sql = "SELECT\n" +
                        "\t\t\tGROUP_CONCAT(LABEL_TITLE) LABEL_TITLE\n" +
                        "\t\tFROM\n" +
                        "\t\t\tAPP_LABEL_ECONOMICS g\n" +
                        "\t\tWHERE\n" +
                        "\t\t\tg.LABEL_TYPE = '4'\n" +
                        "\t\tAND g.LABEL_SIGN_ID = :LABEL_SIGN_ID ";
                List<Map> ls = dao.getServiceDo().findSqlMap(sql,map);
                if(ls != null && ls.size() >0){
                    if(ls.get(0).get("LABEL_TITLE") != null){
                        result = ls.get(0).get("LABEL_TITLE").toString();
                    }
                }
                this.ptjjrk = result;
            }
        }else{
            this.ptjjrk = result;
        }

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) {
        this.signId = signId;
    }

}
