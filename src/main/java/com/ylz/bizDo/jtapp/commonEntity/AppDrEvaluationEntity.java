package com.ylz.bizDo.jtapp.commonEntity;

import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppTeam;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.util.MyMathUtil;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by asus on 2017/07/23.
 */
public class AppDrEvaluationEntity {
    private String drId;//医生主键
    private String drName;//医生姓名
    private String drGender;//医生性别
    private String teamId;//团队主键
    private String countAvgResult;//结果
    private String patientId;//患者主键


    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        this.drName = drName;
    }

    public String getDrGender() {
        return drGender;
    }

    public void setDrGender(String drGender) {
        this.drGender = drGender;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getCountAvgResult() {
        return countAvgResult;
    }

    public void setCountAvgResult(String countAvgResult) {
        if(StringUtils.isNotBlank(this.drId) && StringUtils.isNotBlank(this.patientId)){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("EVALUATION_DR_ID",this.drId);
            map.put("EVALUATION_PATIENT_ID",this.patientId);
            String sql = "SELECT\n" +
                    "\tsum(EVALUATION_AVERAGE) average,count(1) size\n" +
                    "FROM\n" +
                    "\tAPP_DR_EVALUATION\n" +
                    "WHERE\n" +
                    "\tEVALUATION_PATIENT_ID = :EVALUATION_PATIENT_ID \n" +
                    "AND EVALUATION_DR_ID= :EVALUATION_DR_ID ";
            List<Map> ls = dao.getServiceDo().findSqlMap(sql,map);
            if(ls != null && ls.size() >0){
                String average = ls.get(0).get("average").toString();
                String size = ls.get(0).get("size").toString();
                double result = MyMathUtil.div(Double.parseDouble(average),Double.parseDouble(size),2);
                if(result > 4){
                    countAvgResult = "非常好";
                }else if(result <=4 && result >3){
                    countAvgResult = "好";
                }else if(result <=3 && result >2){
                    countAvgResult = "一般";
                }else if(result <=2 && result >1){
                    countAvgResult = "差";
                }else{
                    countAvgResult = "非常差";
                }
            }
        }
        this.countAvgResult = countAvgResult;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getStrDrGender() throws Exception{
        if(StringUtils.isNotBlank(this.getDrGender())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CODESEX, this.getDrGender());
            if(value!=null) {
                return value.getCodeTitle();
            }
        }
        return "";
    }

    public String getStrTeamName(){
        if(StringUtils.isNotBlank(this.getTeamId())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            AppTeam value = (AppTeam)dao.getServiceDo().find(AppTeam.class,this.getTeamId());
            if(value!=null) {
                return value.getTeamName();
            }
        }
        return "";
    }

    public String getStrDrImageUrl(){
        if(StringUtils.isNotBlank(this.getDrId())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            AppDrUser value = (AppDrUser)dao.getServiceDo().find(AppDrUser.class,this.getDrId());
            if(value!=null) {
                return value.getDrImageurl();
            }
        }
        return "";
    }

    public static void main(String[] args) {
        double result = MyMathUtil.div(Double.parseDouble("9.25"),Double.parseDouble("10"),1);
        System.out.println(result);
        if(result > 4){
            System.out.println("非常好");
        }else if(result <=4 && result >3){
            System.out.println("好");
        }else if(result <=3 && result >2){
            System.out.println("一般");
        }else if(result <=2 && result >1){
            System.out.println("差");
        }else{
            System.out.println("非常差");
        }
    }
}
