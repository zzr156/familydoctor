package com.ylz.bizDo.jtapp.commonEntity;

import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;

/**
 * Created by zzl on 2017/8/10.
 */
public class AppTcmGuideEntity {
    private String id;//主键
    private String title;//标题
    private String content;//内容
    private String imageUrl;//图片
    private String createTime;//创建时间
    private String tzlx;//体质类型
    private String bjzdlx;//中医药保健指导类型
    private String createId;//创建人id

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTzlx() {
        return tzlx;
    }

    public void setTzlx(String tzlx) {
        this.tzlx = tzlx;
    }

    public String getBjzdlx() {
        return bjzdlx;
    }

    public void setBjzdlx(String bjzdlx) {
        this.bjzdlx = bjzdlx;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
    }

    /**
     * 获取体质类型名称
     * @return
     */
    public String getTzlxName() throws Exception{
        if(StringUtils.isNotBlank(this.getTzlx())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_TZLX, this.getTzlx());
            if(value!=null) {
                return value.getCodeTitle();
            }
        }
        return "";
    }

    /**
     * 中医药体质辨识保健指导类型名称
     * @return
     */
    public String getBjzdlxName() throws Exception{
        if(StringUtils.isNotBlank(this.getBjzdlx())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_ZYYBJZD, this.getBjzdlx());
            if(value!=null) {
                return value.getCodeTitle();
            }
        }
        return "";
    }

    /**
     * 获取医生名称
     * @return
     */
    public String getDrName(){
        if(StringUtils.isNotBlank(this.getCreateId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            if("0".equals(this.getCreateId())){
                return "系统";
            }
            AppDrUser user = (AppDrUser)dao.getServiceDo().find(AppDrUser.class,this.getCreateId());
            if(user!=null){
                return  user.getDrName();
            }else{
                AppHospDept dept = (AppHospDept)dao.getServiceDo().find(AppHospDept.class,this.getCreateId());
                if(dept!=null){
                    return dept.getHospName();
                }
            }
        }
        return "";
    }
}
