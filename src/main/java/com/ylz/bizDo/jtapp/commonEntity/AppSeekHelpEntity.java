package com.ylz.bizDo.jtapp.commonEntity;

import com.ylz.packaccede.util.CardUtil;
import com.ylz.packcommon.common.util.AgeUtil;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by asus on 2017/09/04.
 */
public class AppSeekHelpEntity {
    private String name;
    private String tel;
    private String idno;
    private String address;
    private String gender;
    private String age;
    private String date;

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
            this.date = ExtendDate.getYMD_h_m_s(date);
        }
    }

    public String getTitle(){
        String result = "居民:";
        if(StringUtils.isNotBlank(this.getName())){
            result += this.getName()+",";
        }else {
            result += ",";
        }

        if(StringUtils.isNotBlank(this.getTel())){
            result += "电话:"+ this.getTel()+",";
        }else {
            result += "电话:,";
        }

        if(StringUtils.isNotBlank(this.getAddress())){
            result += "地址:"+ this.getAddress()+",";
        }else {
            result += "地址:,";
        }
        result += " 请求帮助!";
        return result;
    }
}
