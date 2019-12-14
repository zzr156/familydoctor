package com.ylz.bizDo.jtapp.commonEntity;

import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packaccede.util.CardUtil;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.comEnum.ResidentMangeType;
import com.ylz.packcommon.common.util.AgeUtil;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by asus on 2017/09/04.
 */
public class AppFollowPlanManageEntity {

    private String name;
    private String tel;
    private String idno;
    private String address;
    private String gender;
    private String age;
    private String date;
    private String num;
    private String type;
    private String followId;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        Map<String,Object> idNos = new HashMap<String,Object>();
        try {
            if(StringUtils.isNotBlank(this.getIdno())){
                if(this.getIdno().length() == 18){
                    idNos = CardUtil.getCarInfo(this.getIdno());
                }else{
                    idNos = CardUtil.getCarInfo15W(this.getIdno());
                }
                String birthday = idNos.get("birthday").toString();
                age = AgeUtil.getAgeYear(ExtendDate.getCalendar(birthday));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        this.age = age;
    }

    public String getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        if(date != null){
            this.date = ExtendDate.getYMD_h_m(date);
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getFollowId() {
        return followId;
    }

    public void setFollowId(String followId) {
        this.followId = followId;
    }

    public String getStrResultId(){
        String result = "";
        if(StringUtils.isNotBlank(this.getType())){
            SysDao sysDao = (SysDao) SpringHelper.getBean("sysDao");
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("FOLLOWID",this.getFollowId());
            map.put("CCFFFL","ccfffl");
            String sql = "SELECT\n" ;
            if(ResidentMangeType.GXY.getValue().equals(this.getType())){//高血压
                sql +=  "\t(SELECT code_title FROM CD_CODE WHERE code_value = a.VISIT_THIS_TYPE AND code_group = :CCFFFL) visitName,\n" +
                        "\ta.ID id\n"+
                        "FROM\n"+
                        "\tAPP_HDBLOOPRESSURE_TABLE a\n"+
                        "WHERE\n" +
                        "\tVISIT_ID = :FOLLOWID";
            }
            if(ResidentMangeType.TNB.getValue().equals(this.getType())){//糖尿病
                sql +=  "\t(SELECT code_title FROM CD_CODE WHERE code_value = a.VISIT_THIS_TYPE AND code_group = :CCFFFL) visitName,\n" +
                        "\ta.ID id\n"+
                        "FROM\n"+
                        "\tAPP_DIABETES_TABLE a\n"+
                        "WHERE\n" +
                        "\tVISIT_ID = :FOLLOWID";
            }
            if(ResidentMangeType.ETLZLS.getValue().equals(this.getType())){//糖尿病
                sql +=  "\t'' visitName," +
                        "\ta.ID id\n"+
                        "FROM\n"+
                        "\tAPP_CHILD a\n"+
                        "WHERE\n" +
                        "\tCHILD_VISIT_ID = :FOLLOWID";
            }
            if(ResidentMangeType.YCF.getValue().equals(this.getType())){//糖尿病
                sql +=  "\t'' visitName," +
                        "\ta.ID id\n"+
                        "FROM\n";
                sql += "\tAPP_POSTPARTUM_VISIT a\n";
                sql += "WHERE\n" +
                        "\tPOST_VISIT_ID = :FOLLOWID";
            }

            List<Map> lsMap = sysDao.getServiceDo().findSqlMap(sql,map);
            if(lsMap != null && lsMap.size()>0){
                if(lsMap.get(0).get("visitName") != null && lsMap.get(0).get("id") != null) {
                    result = lsMap.get(0).get("visitName").toString() +";"+lsMap.get(0).get("id").toString();
                }
            }
        }
        return result;
    }

    public String getControlResult(){
        String result = "";
        if(StringUtils.isNotBlank(this.getStrResultId())){
            String[] results = this.getStrResultId().split(";");
            result = results[0];
        }
        return  result;
    }

    public String getSubFollowId(){
        String result = "";
        if(StringUtils.isNotBlank(this.getStrResultId())){
            String[] results = this.getStrResultId().split(";");
            result = results[1];
        }
        return  result;
    }


    public String getTitle() throws Exception{
        String result = "";
        if(StringUtils.isNotBlank(this.getName())){
            result += this.getName()+",";
        }
        if(StringUtils.isNotBlank(this.getGender())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_CODESEX, this.getGender());
            if(value!=null) {
                result += value.getCodeTitle()+",";
            }
        }
        if(StringUtils.isNotBlank(this.getAge())){
            result += this.getAge()+"岁。";
        }
        if(StringUtils.isNotBlank(this.getNum())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_RESIDENTMANGE, this.getType());
            result += "第"+this.getNum()+"次"+value.getCodeTitle()+"随访";
        }
        if(StringUtils.isNotBlank(this.getControlResult())){
            result += ","+this.getControlResult()+"。";
        }else{
            result += "。";
        }
        return result;
    }
}
