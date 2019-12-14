package com.ylz.bizDo.jtapp.drEntity;

import com.ylz.packaccede.util.CardUtil;
import com.ylz.packcommon.common.util.AgeUtil;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 转诊居民列表
 * Created by zzl on 2018/1/24.
 */
public class ReferralInfo {
    private String id;//
    private String name;//姓名
    private String sex;//性别 1男 2女
    private String age;//年龄
    private String tel;//电话
    private String imgurl;//头像
    private String ssgg;//三师工管 2代表签约
    private String jjyl;//居家养老 2代表签约
    private String jjylcolor;//居家养老颜色
    private String labtitle;//疾病类型 多逗号隔开 例如 肝炎,恶性肿瘤,糖尿病
    private String labcolor;//疾病类型颜色 多逗号隔开 例如 red,yellow
    private String patientIdno;//身份证
    private String patientCard;//社保卡
    private String signType;//1家庭签约 2 vip
    private String signPersGroup;//服务人群
    private String signUrrenderReason;//解约原因
    private String signUrrenderReasonPatient;//患者解约原因
    private String signOthnerReason;//拒签原因
    private String signDate;//签约时间
    private String signPayState;//缴费状态 1已缴费 0：未缴费
    private String address;//地址
    private String signFormId;//签约单id
    private String referralState;//转诊状态

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) throws Exception {
        Map<String,Object> idNos = new HashMap<String,Object>();
        if(StringUtils.isNotBlank(this.getPatientIdno())){
            if(this.getPatientIdno().length() == 18){
                idNos = CardUtil.getCarInfo(this.getPatientIdno());
            }else{
                idNos = CardUtil.getCarInfo15W(this.getPatientIdno());
            }
            String birthday = idNos.get("birthday").toString();
            age = AgeUtil.getAgeYear(ExtendDate.getCalendar(birthday));
        }
        this.age = age;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getSsgg() {
        return ssgg;
    }

    public void setSsgg(String ssgg) {
        this.ssgg = ssgg;
    }

    public String getJjyl() {
        return jjyl;
    }

    public void setJjyl(String jjyl) {
        this.jjyl = jjyl;
    }

    public String getJjylcolor() {
        return jjylcolor;
    }

    public void setJjylcolor(String jjylcolor) {
        this.jjylcolor = jjylcolor;
    }

    public String getLabtitle() {
        return labtitle;
    }

    public void setLabtitle(String labtitle) {
        this.labtitle = labtitle;
    }

    public String getLabcolor() {
        return labcolor;
    }

    public void setLabcolor(String labcolor) {
        this.labcolor = labcolor;
    }

    public String getPatientIdno() {
        return patientIdno;
    }

    public void setPatientIdno(String patientIdno) {
        this.patientIdno = patientIdno;
    }

    public String getPatientCard() {
        return patientCard;
    }

    public void setPatientCard(String patientCard) {
        this.patientCard = patientCard;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getSignPersGroup() {
        return signPersGroup;
    }

    public void setSignPersGroup(String signPersGroup) {
        this.signPersGroup = signPersGroup;
    }

    public String getSignUrrenderReason() {
        return signUrrenderReason;
    }

    public void setSignUrrenderReason(String signUrrenderReason) {
        this.signUrrenderReason = signUrrenderReason;
    }

    public String getSignUrrenderReasonPatient() {
        return signUrrenderReasonPatient;
    }

    public void setSignUrrenderReasonPatient(String signUrrenderReasonPatient) {
        this.signUrrenderReasonPatient = signUrrenderReasonPatient;
    }

    public String getSignOthnerReason() {
        return signOthnerReason;
    }

    public void setSignOthnerReason(String signOthnerReason) {
        this.signOthnerReason = signOthnerReason;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public String getSignPayState() {
        return signPayState;
    }

    public void setSignPayState(String signPayState) {
        this.signPayState = signPayState;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSignFormId() {
        return signFormId;
    }

    public void setSignFormId(String signFormId) {
        this.signFormId = signFormId;
    }

    public String getReferralState() {
        return referralState;
    }

    public void setReferralState(String referralState) {
        this.referralState = referralState;
    }
}
