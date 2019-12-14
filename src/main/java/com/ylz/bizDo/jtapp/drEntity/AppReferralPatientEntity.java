package com.ylz.bizDo.jtapp.drEntity;

import com.ylz.packaccede.util.CardUtil;
import com.ylz.packcommon.common.util.AgeUtil;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zzl on 2017/12/18.
 */
public class AppReferralPatientEntity {
    private String id;//患者主键
    private String name;//患者姓名
    private String labelTitle;//疾病标签
    private String labelValue;//疾病值
    private String labelColor;//疾病颜色
    private String signId;//签约单id
    private String idno;//身份证号
    private String sex;//性别
    private String age;//年龄
    private String tel;//手机号
    private String address;//居住地
    private String card;//医保卡号
    private String imageUrl;//头像

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabelTitle() {
        return labelTitle;
    }

    public void setLabelTitle(String labelTitle) {
        this.labelTitle = labelTitle;
    }

    public String getLabelValue() {
        return labelValue;
    }

    public void setLabelValue(String labelValue) {
        this.labelValue = labelValue;
    }

    public String getLabelColor() {
        return labelColor;
    }

    public void setLabelColor(String labelColor) {
        this.labelColor = labelColor;
    }

    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) {
        this.signId = signId;
    }

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) throws Exception{
        Map<String,Object> idNos = new HashMap<String,Object>();
        if(StringUtils.isNotBlank(this.getIdno())){
            if(this.getIdno().length() == 18){
                idNos = CardUtil.getCarInfo(this.getIdno());
            }else{
                idNos = CardUtil.getCarInfo15W(this.getIdno());
            }
            String birthday = idNos.get("birthday").toString();
            String patientAge = AgeUtil.getAgeYear(ExtendDate.getCalendar(birthday));
            age = patientAge;
        }
        this.age = age;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
