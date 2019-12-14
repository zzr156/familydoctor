package com.ylz.bizDo.jtapp.drEntity;

import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.app.po.AppSignsWarningSetting;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packaccede.util.CardUtil;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.util.AgeUtil;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**体征居民列表返回参数
 * Created by zzl on 2017/11/7.
 */
public class AppDrTzEntity {
    private String userId;//患者id
    private String userName;//患者姓名
    private String imageUrl;//患者头像
    private String disType;//疾病类型
    private String disTypeName;//疾病名称
    private String disColor;//颜色
    private String sex;//性别
    private String age;//年龄
    private String dayNum;//预警天数
    private String drId;//医生id

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDisType() {
        return disType;
    }

    public void setDisType(String disType) {
        this.disType = disType;
    }

    public String getDisTypeName() {
        return disTypeName;
    }

    public void setDisTypeName(String disTypeName) {
        this.disTypeName = disTypeName;
    }

    public String getDisColor() {
        return disColor;
    }

    public void setDisColor(String disColor) {
        this.disColor = disColor;
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
        if(StringUtils.isNotBlank(this.getUserId())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            AppPatientUser user = (AppPatientUser)dao.getServiceDo().find(AppPatientUser.class,this.getUserId());
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

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getDayNum() {
        return dayNum;
    }

    public void setDayNum(String dayNum) {
        if(StringUtils.isNotBlank(this.getDrId()) && StringUtils.isNotBlank(this.getDisType())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("drId",drId);
            map.put("disType",disType);
            String sql = "SELECT * FROM APP_SIGNS_WARNING_SETTING " +
                    "WHERE 1=1 AND SWS_CREATE_ID=:drId" +
                    " AND SWS_DIS_TYPE=:disType";
            map.put("userId",userId);
            String sql1=sql +" AND SWS_USER_ID=:userId";
//                sql +=" AND SWS_TYPE ='1'";
            List<AppSignsWarningSetting> list = dao.getServiceDo().findSqlMap(sql1,map,AppSignsWarningSetting.class);
            if(list!=null && list.size()>0){
                AppSignsWarningSetting vo = list.get(0);
                if("red".equals(this.getDisColor())){
                    dayNum = vo.getSwsRedSet();
                }else if("yellow".equals(this.getDisColor())){
                    dayNum = vo.getSwsYellowSet();
                }else if("green".equals(this.getDisColor())){
                    dayNum = vo.getSwsGreenSet();
                }
            }
        }
        this.dayNum = dayNum;
    }
}
