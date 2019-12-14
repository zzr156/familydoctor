package com.ylz.bizDo.jtapp.ysChangeEntity;

import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packaccede.util.CardUtil;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.util.AgeUtil;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zzl on 2017/9/7.
 */
public class YsChangePatientEntity  {
    private String id;//患者id
    private String name;//姓名
    private String sex;//性别
    private String age;//年龄
    private String image;//头像
    private String label;//标签

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

    public void setAge(String age) {
        try {
            if(StringUtils.isNotBlank(this.getId())){
                SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
                AppPatientUser user = (AppPatientUser)dao.getServiceDo().find(AppPatientUser.class,this.getId());
                if(user!=null){
                    Map<String,Object> idNos = new HashMap<String,Object>();
                    if(user.getPatientIdno().length() == 18){
                         idNos = CardUtil.getCarInfo(user.getPatientIdno());
                    }else{
                        idNos = CardUtil.getCarInfo15W(user.getPatientIdno());
                    }
                    String birthday = idNos.get("birthday").toString();
                    age = AgeUtil.getAgeYear(ExtendDate.getCalendar(birthday));
                }
            }
            this.age = age;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
