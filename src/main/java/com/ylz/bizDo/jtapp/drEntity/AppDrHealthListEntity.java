package com.ylz.bizDo.jtapp.drEntity;

import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;

/**医生返回健康教育列表
 * Created by zzl on 2017/6/22.
 */
public class AppDrHealthListEntity  {
    private String id;//健康教育主键
    private String title;//标题
    private String imageUrl;//图片
    private String type;//类型
    private String browse;//浏览次数
    private String transmit;//发布次数
    private String enshrine;//收藏次数
    private String time;//时间
    private String drId;//医生id
    private String drName;//医生姓名
    private String drTypeName;//医生类型名称

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBrowse() {
        return browse;
    }

    public void setBrowse(String browse) {
        this.browse = browse;
    }

    public String getTransmit() {
        return transmit;
    }

    public void setTransmit(String transmit) {
        this.transmit = transmit;
    }

    public String getEnshrine() {
        return enshrine;
    }

    public void setEnshrine(String enshrine) {
        this.enshrine = enshrine;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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
            AppDrUser drUser = (AppDrUser) dao.getServiceDo().find(AppDrUser.class,this.getDrId());
            if(drUser!=null){
                drName = drUser.getDrName();
            }
        }
        this.drName = drName;
    }

    public String getDrTypeName() {
        return drTypeName;
    }

    public void setDrTypeName(String drTypeName) throws Exception {
        if(StringUtils.isNotBlank(this.getDrId())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            AppDrUser drUser = (AppDrUser) dao.getServiceDo().find(AppDrUser.class,this.getDrId());
            if(drUser!=null){
                CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_YSTYPE, drUser.getDrType());
                if(value!=null){
                    drTypeName = value.getCodeTitle();
                }
            }
        }
        this.drTypeName = drTypeName;
    }
}
