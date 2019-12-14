package com.ylz.bizDo.jtapp.commonEntity;

import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;

/**
 * Created by zzl on 2017/8/23.
 */
public class AppTcmResultEntity {
    private String id;//指导id
    private String bjlx;//中医药保健指导类型
    private String title;//标题
    private String content;//内容
    private String imageUrl;//图片
    private String createTime;//创建时间
    private String createId;//创建人id

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBjlx() {
        return bjlx;
    }

    public void setBjlx(String bjlx) {
        this.bjlx = bjlx;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = ExtendDate.getYMD_h_m(createTime);
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    /**
     * 获取保健指导名称
     * @return
     */
    public String getBjlxName() throws Exception{
        if(StringUtils.isNotBlank(this.getBjlx())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_ZYYBJZD, this.getBjlx());
            if(value!=null) {
                return value.getCodeTitle();
            }
        }
        return "";
    }

    /**
     * 获取医生姓名
     * @return
     */
    public String getCreateName(){
        if(StringUtils.isNotBlank(this.getCreateId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            AppDrUser drUser =(AppDrUser) dao.getServiceDo().find(AppDrUser.class,this.getCreateId());
            if(drUser!=null){
                return drUser.getDrName();
            }
        }
        return "";
    }
}
