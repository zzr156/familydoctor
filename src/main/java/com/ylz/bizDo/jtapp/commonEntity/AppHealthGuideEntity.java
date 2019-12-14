package com.ylz.bizDo.jtapp.commonEntity;

import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppLabelManage;
import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by zzl on 2017/6/20.
 */
public class AppHealthGuideEntity  {
    private String id;//主键
    private String title;//标题
    private String content;//内容
    private String time;//时间
    private String type;//类型
    private String disTypeName;//疾病类型
    private String medTypeName;//干预类型
    private String drId;//医生id
    private String drName;//医生姓名
    private String drType;//医生类别
    private String toUserId;//接收人id
    private String toUserName;//接收人姓名
    private String imageUrl;//图片
    private String disType;//疾病类型
    private String medType;//干预类型
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDisTypeName() {
        return disTypeName;
    }

    public void setDisTypeName(String disTypeName) throws Exception {
        if(StringUtils.isNotBlank(disTypeName)){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            AppLabelManage value =dao.getAppLabelManageDao().findLabelByValue("2",disTypeName);
            if(value!=null) {
                disTypeName= value.getLabelTitle();
            }
        }
        this.disTypeName = disTypeName;
    }

    public String getMedTypeName() {
        return medTypeName;
    }

    public void setMedTypeName(String medTypeName) throws Exception {
        if(StringUtils.isNotBlank(medTypeName)){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_MEDDLETYPE, medTypeName);
            if(value!=null) {
                medTypeName= value.getCodeTitle();
            }
        }
        this.medTypeName = medTypeName;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        if(StringUtils.isNotBlank(this.getDrId())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            AppDrUser drUser = (AppDrUser)dao.getServiceDo().find(AppDrUser.class,this.getDrId());
            if(drUser!=null){
                drName = drUser.getDrName();
            }
        }
        this.drName = drName;
    }

    public String getDrType() {
        return drType;
    }

    public void setDrType(String drType) throws Exception {
        if(StringUtils.isNotBlank(drType)){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_WORKTYPE, drType);
            if(value!=null) {
                drType= value.getCodeTitle();
            }
        }
        this.drType = drType;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        if(StringUtils.isNotBlank(this.getToUserId())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            AppPatientUser patientUser = (AppPatientUser)dao.getServiceDo().find(AppPatientUser.class,this.getToUserId());
            if(patientUser!=null){
                toUserName = patientUser.getPatientName();
            }
        }
        this.toUserName = toUserName;
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

    public String getMedType() {
        return medType;
    }

    public void setMedType(String medType) {
        this.medType = medType;
    }
}
