package com.ylz.bizDo.jtapp.drEntity;

import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppLabelManage;
import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;

/**
 * Created by zzl on 2017/11/4.
 */
public class AppSignsWarningRecordEntity {
    private String id;
    private String drId;//医生id
    private String userId;//患者id
    private String color;//颜色
    private String disType;//疾病类型
    private String dayNum;//天数
    private String createTime;//创建时间
    private String content;//内容

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDisType() {
        return disType;
    }

    public void setDisType(String disType) {
        this.disType = disType;
    }

    public String getDayNum() {
        return dayNum;
    }

    public void setDayNum(String dayNum) {
        this.dayNum = dayNum;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        if(createTime!=null){
            this.createTime = ExtendDate.getYMD(createTime);
        }else{
            this.createTime = "";
        }
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取医生姓名
     * @return
     */
    public String getDrName(){
        if(StringUtils.isNotBlank(this.getDrId())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            AppDrUser drUser = (AppDrUser)dao.getServiceDo().find(AppDrUser.class,this.getDrId());
            if(drUser!=null){
                return drUser.getDrName();
            }
        }
        return "";
    }

    /**
     * 获取患者姓名
     * @return
     */
    public String getPatientName(){
        if(StringUtils.isNotBlank(this.getUserId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            AppPatientUser user = (AppPatientUser)dao.getServiceDo().find(AppPatientUser.class,this.getUserId());
            if(user!=null){
                return user.getPatientName();
            }
        }
        return "";
    }

    /**
     * 获取颜色中文名称
     * @return
     */
    public String getColorName(){
        if(StringUtils.isNotBlank(this.getColor())){
            if("red".equalsIgnoreCase(this.getColor())){
                return "红标";
            }else if("yellow".equalsIgnoreCase(this.getColor())){
                return "黄标";
            }else if("green".equalsIgnoreCase(this.getColor())){
                return "绿标";
            }
        }
        return "";
    }

    /**
     * 获取患者手机号码
     * @return
     */
    public String getPatientTel(){
        if(StringUtils.isNotBlank(this.getUserId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            AppPatientUser user = (AppPatientUser)dao.getServiceDo().find(AppPatientUser.class,this.getUserId());
            if(user!=null){
                return user.getPatientTel();
            }
        }
        return "";
    }

    /**
     * 获取疾病名称
     * @return
     */
    public String getDisTypeName() throws Exception {
        if(StringUtils.isNotBlank(this.getDisType())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            //CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_WORKTYPE, this.getMemWorkType());
            AppLabelManage labelManage = dao.getAppLabelManageDao().findLabelByValue("2",this.getDisType());
            if(labelManage!=null){
                return labelManage.getLabelTitle();
            }
        }
        return "";
    }

}
