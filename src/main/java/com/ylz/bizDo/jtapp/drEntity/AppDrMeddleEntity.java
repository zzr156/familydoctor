package com.ylz.bizDo.jtapp.drEntity;

import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppLabelManage;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;

/**指导内容返回实体数据
 * Created by zzl on 2017/7/1.
 */
public class AppDrMeddleEntity  {
    private String id;//主键id
    private String title;//标题
    private String content;//内容
    private String time;//时间
    private String imageUrl;//图片
    private String meddleType;//指导类型名称
    private String disType;//疾病类型名称
    private String drId;//指导医生id
    private String drName;//指导医生
    private String illType;//疾病类型
    private String medType;//指导类型

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

    public void setTime(Timestamp time) {
        if(time != null) {
            this.time = ExtendDate.getYMD_h_m(time);
        }
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMeddleType() {
        return meddleType;
    }

    public void setMeddleType(String meddleType) throws Exception {
        if(StringUtils.isNotBlank(meddleType)){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_MEDDLETYPE, meddleType);
            if(value!=null){
                meddleType = value.getCodeTitle();
            }
        }

        this.meddleType = meddleType;
    }

    public String getDisType() {
        return disType;
    }

    public void setDisType(String disType) throws Exception {
        if(StringUtils.isNotBlank(disType)){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            AppLabelManage value = dao.getAppLabelManageDao().findLabelByValue("2",disType);
            if(value!=null){
                disType = value.getLabelTitle();
            }
        }
        this.disType = disType;
    }

    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        if(StringUtils.isNotBlank(this.getDrId())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            AppDrUser drUser = (AppDrUser) dao.getServiceDo().find(AppDrUser.class,this.getDrId());
            if(drUser!=null){
                drName = drUser.getDrName();
            }

        }

        this.drName = drName;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        this.drId = drId;
    }

    public String getIllType() {
        return illType;
    }

    public void setIllType(String illType) {
        this.illType = illType;
    }

    public String getMedType() {
        return medType;
    }

    public void setMedType(String medType) {
        this.medType = medType;
    }
}
