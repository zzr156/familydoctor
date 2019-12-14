package com.ylz.bizDo.jtapp.commonEntity;

import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import org.apache.commons.lang.StringUtils;

/**中医药保健指导记录
 * Created by zzl on 2017/8/30.
 */
public class AppNewUserTcmGuideEntity {
    private String id;//主键
    private String createId;//创建者id
    private String createTime;//创建时间
    private String tzlx;//体质类型
    private String qzts;//情志调摄
    private String ysty;//饮食调养
    private String qjts;//起居调摄
    private String ydbj;//运动保健
    private String xwbj;//穴位保健
    private String qt;//其他
    private String userId;//用户id
    private String jlId;//记录id

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateId() {
        return createId;
    }

    public void setCreateId(String createId) {
        this.createId = createId;
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

    public String getQzts() {
        return qzts;
    }

    public void setQzts(String qzts) {
        this.qzts = qzts;
    }

    public String getYsty() {
        return ysty;
    }

    public void setYsty(String ysty) {
        this.ysty = ysty;
    }

    public String getQjts() {
        return qjts;
    }

    public void setQjts(String qjts) {
        this.qjts = qjts;
    }

    public String getYdbj() {
        return ydbj;
    }

    public void setYdbj(String ydbj) {
        this.ydbj = ydbj;
    }

    public String getXwbj() {
        return xwbj;
    }

    public void setXwbj(String xwbj) {
        this.xwbj = xwbj;
    }

    public String getQt() {
        return qt;
    }

    public void setQt(String qt) {
        this.qt = qt;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getJlId() {
        return jlId;
    }

    public void setJlId(String jlId) {
        this.jlId = jlId;
    }

    /**
     * 获取医生姓名
     * @return
     */
    public String getDrName(){
        if(StringUtils.isNotBlank(this.getCreateId())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            AppDrUser drUser = (AppDrUser)dao.getServiceDo().find(AppDrUser.class,this.getCreateId());
            if(drUser!=null){
                return drUser.getDrName();
            }else{
                AppPatientUser user = (AppPatientUser)dao.getServiceDo().find(AppPatientUser.class,this.getCreateId());
                if(user!=null){
                    return user.getPatientName();
                }
            }
        }
        return "";
    }

    /**
     * 获取体质类型名称
     * @return
     */
    public String getStrTzlx() throws Exception{
        if(StringUtils.isNotBlank(this.getTzlx())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_TZLX,this.getTzlx());
            if(value != null){
                return value.getCodeTitle();
            }
        }
        return "";
    }

    /**
     * 获取患者姓名
     * @return
     */
    public String getUserName(){
        if(StringUtils.isNotBlank(this.getUserId())){
            SysDao dao = (SysDao)SpringHelper.getBean("sysDao");
            AppPatientUser patientUser = (AppPatientUser)dao.getServiceDo().find(AppPatientUser.class,this.getUserId());
            if(patientUser!=null){
                return patientUser.getPatientName();
            }
        }
        return "";
    }
}
