package com.ylz.bizDo.jtapp.drEntity;

import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 体征预警
 * Created by zzl on 2017/11/3.
 */
public class AppDrTzyjEntity  {
    private String drId;//医生id
    private String patientId;//患者id
    private String disType;//疾病类型
    private String color;//颜色
    private String dayNum;//天数
    private String teamId;//团队id

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDisType() {
        return disType;
    }

    public void setDisType(String disType) {
        this.disType = disType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDayNum() {
        return dayNum;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public void setDayNum(String dayNum) {
        if(StringUtils.isNotBlank(this.getPatientId())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("userId",this.getPatientId());
            String sql = "";
            //高血压
            if("201".equals(this.getDisType())){
                sql = "SELECT DATEDIFF(NOW(),c.UP_TIME) dyNum FROM APP_USER_BLOODPRESSURE c WHERE c.UP_USER_ID =:userId" +
                        " ORDER BY c.UP_TIME DESC LIMIT 1";
            }
            //糖尿病
            if("202".equals(this.getDisType())){
                sql = "SELECT  DATEDIFF(NOW(),c.UG_TEST_TIME) dyNum FROM APP_USER_BLOODGLU c WHERE c.UG_USER_ID =:userId" +
                        " ORDER BY c.UG_TEST_TIME DESC LIMIT 1";
            }
            List<Map> map1 = dao.getServiceDo().findSqlMap(sql,map);
            if(map1!=null && map1.size()>0 ){
                if(map1.get(0).get("dyNum") != null){
                    dayNum = map1.get(0).get("dyNum").toString();
                }
            }
        }
        this.dayNum = dayNum;
    }


}
