package com.ylz.bizDo.plan.Entity;

import com.ylz.bizDo.app.po.AppLabelGroup;
import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.app.po.AppSignForm;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packaccede.util.CardUtil;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.util.AgeUtil;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 随访
 * Created by zzl on 2017/6/30.
 */
public class AppFollowGroupEntity {
    private String followId;//随访主表id
    private String patientId;//患者id
    private String patientName;//姓名
    private String patientGender;//性别
    private String patientAge;//年龄
    private String imgurl;//头像
    private String signPersGroup;//服务类型名称 多个分号分割
    private String followDate;//计划随访时间
    private List<Map> labelType;//疾病类型
    private String referral;//转诊情况 1转诊
    private String followNum;//随访bianh


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

    public String getPatientGender() {
        return patientGender;
    }

    public void setPatientGender(String patientGender) {
        this.patientGender = patientGender;
    }

    public String getPatientAge() {
        return patientAge;
    }

    public void setPatientAge(String patientAge) throws Exception {
        if(StringUtils.isNotBlank(this.getPatientId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            AppPatientUser user = (AppPatientUser)dao.getServiceDo().find(AppPatientUser.class,this.getPatientId());
            if(user!=null){
                Map<String,Object> idNos = new HashMap<String,Object>();
                if(StringUtils.isNotBlank(user.getPatientIdno())){
                    if(user.getPatientIdno().length() == 18){
                        idNos = CardUtil.getCarInfo(user.getPatientIdno());
                    }else{
                        idNos = CardUtil.getCarInfo15W(user.getPatientIdno());
                    }
                    String birthday = idNos.get("birthday").toString();
                    String age = AgeUtil.getAgeYear(ExtendDate.getCalendar(birthday));
                    patientAge = age;
                }

            }
        }

        this.patientAge = patientAge;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    public String getSignPersGroup() {
        return signPersGroup;
    }

    public void setSignPersGroup(String signPersGroup) {
        this.signPersGroup = signPersGroup;
    }

    public String getFollowDate() {
        return followDate;
    }

    public void setFollowDate(String followDate) {
        this.followDate = followDate;
    }

    public List<Map> getLabelType() {
        return labelType;
    }


    public void setLabelType(String labelType2) {
        labelType = new ArrayList<>();
        if(StringUtils.isNotBlank(this.getPatientId())) {
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            List<AppSignForm> ls=dao.getServiceDo().loadByPk(AppSignForm.class,"signPatientId",this.getPatientId());
            if(ls!=null && !ls.isEmpty()) {
                Map<String,Object> map=new HashedMap();
                String sql="SELECT * from APP_LABEL_DISEASE a where a.LABEL_TYPE=2 and a.LABEL_SIGN_ID=:LABEL_SIGN_ID";
                map.put("LABEL_SIGN_ID",ls.get(0).getId());
                List<AppLabelGroup> labs=dao.getServiceDo().findSqlMap(sql,map,AppLabelGroup.class);
                if(labs!=null && !labs.isEmpty()){
                    for(AppLabelGroup la:labs) {
                        PersGroupLsit p = new PersGroupLsit();
                        Map<String,Object> map2 = new HashMap<>();
                        map2.put("labelTitle", la.getLabelTitle());
                        map2.put("labelColor", la.getLabelColor());
                        labelType.add(map2);
                    }
                }
            }
        }
    }

    public String getReferral() {
        return referral;
    }

    public void setReferral(String referral) {
        this.referral = referral;
    }

    public String getFollowId() {
        return followId;
    }

    public void setFollowId(String followId) {
        this.followId = followId;
    }

    public String getFollowNum() {
        return followNum;
    }

    public void setFollowNum(String followNum) {
        this.followNum = followNum;
    }
}
