package com.ylz.bizDo.jtapp.commonEntity;

import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;

import java.math.BigInteger;
import java.util.*;

/**0-6岁儿童健康检查履约人员列表
 * Created by zzl on 2017/11/16.
 */
public class AppChildLyEntity {
    private String countTj;//需体检次数
    private String count;//完成次数
    private String patientId;
    private String patientIdno;
    private String drId;
    private String signId;

    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) {
        this.signId = signId;
    }
    //    private List<AppChildTjEntity> tjList;//今年体检时间

    public String getCountTj() {
        return countTj;
    }

    public void setCountTj(BigInteger countTj) {
        if(countTj!=null){
            this.countTj = String.valueOf(countTj);
        }
        this.countTj = "0";
    }

    public String getCount() {
        return count;
    }

    public void setCount(BigInteger count) {
        if(count!=null){
            this.count = String.valueOf(count);
        }
        this.count = "0";
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getPatientIdno() {
        return patientIdno;
    }

    public void setPatientIdno(String patientIdno) {
        this.patientIdno = patientIdno;
    }

   /* public List<AppChildTjEntity> getTjList() {
        return tjList;
    }

    public void setTjList(String tjList) {
        if(StringUtils.isNotBlank(this.getPatientId())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            List<AppChildTjEntity> tjLists = new ArrayList<AppChildTjEntity>();
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("patientId",this.getPatientId());
            map.put("patientIdno",this.getPatientIdno());
            map.put("drId",this.getDrId());
            Calendar cal = Calendar.getInstance();
            map.put("year",cal.get(Calendar.YEAR));
            String sql = "SELECT " +
                    "a.CHP_PLAN_DATE tjDate," +
                    "(SELECT COUNT(1) FROM APP_PERFORMANCE_REGULAR_FOLLOWUP b" +
                    " WHERE b.PER_IDNO=:patientIdno " +
                    "AND DATE_FORMAT(b.PER_CREATE_DATE,'%Y-%m-%d')=DATE_FORMAT(a.CHP_PLAN_DATE,'%Y-%m-%d') " +
                    "AND b.PER_FOLLOW_TYPE='2'" +
                    "AND b.PER_DR_ID=:drId) state\n" +
                    "FROM APP_CHILD_HEALTH_PLAN a\n" +
                    "WHERE a.CHP_CHILD_USER_ID=:patientId\n" +
                    "AND DATE_FORMAT(a.CHP_PLAN_DATE,'%Y') =:year\n" +
                    "AND a.CHP_PLAN_DATE<=NOW()";
            tjLists = dao.getServiceDo().findSqlMapRVo(sql,map,AppChildTjEntity.class);
            this.tjList = tjLists;
        }

    }*/
}
