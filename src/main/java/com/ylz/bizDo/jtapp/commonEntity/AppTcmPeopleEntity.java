package com.ylz.bizDo.jtapp.commonEntity;

import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packaccede.util.CardUtil;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.util.AgeUtil;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2017/8/9.
 */
public class AppTcmPeopleEntity {
    private String id;//主键id
    private String name;//姓名
    private String age;//年龄
    private String gender;//性别
    private String imageUrl;//居民头像
    private String signId;//签约单id
    private List<AppTcmServeEntiry> fwrq;//服务人群信息

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

    public String getAge() {
        return age;
    }

    public void setAge(String age) throws Exception {
        if(StringUtils.isNotBlank(this.getId())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            AppPatientUser user = (AppPatientUser)dao.getServiceDo().find(AppPatientUser.class,this.getId());
            if(user!=null){
                Map<String,Object> idNos = new HashMap<String,Object>();
                if(StringUtils.isNotBlank(user.getPatientIdno())){
                    if(user.getPatientIdno().length() == 18){
                        idNos = CardUtil.getCarInfo(user.getPatientIdno());
                    }else{
                        idNos = CardUtil.getCarInfo15W(user.getPatientIdno());
                    }
                    String birthday = idNos.get("birthday").toString();
                    age = AgeUtil.getAgeYear(ExtendDate.getCalendar(birthday));
                }
            }
        }
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSignId() {
        return signId;
    }

    public void setSignId(String signId) {
        this.signId = signId;
    }

    public List<AppTcmServeEntiry> getFwrq() {
        return fwrq;
    }

    public void setFwrq(String fwrq) {
        if(StringUtils.isNotBlank(this.getSignId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("signId",this.getSignId());
            map.put("type","3");
            String sql = "SELECT LABEL_TITLE fwTitle,LABEL_VALUE fwValue,LABEL_COLOR fwColor FROM APP_LABEL_GROUP WHERE LABEL_SIGN_ID =:signId AND LABEL_TYPE =:type";
            List<AppTcmServeEntiry> lss = dao.getServiceDo().findSqlMapRVo(sql,map,AppTcmServeEntiry.class);
            this.fwrq = lss;
        }
    }
}
