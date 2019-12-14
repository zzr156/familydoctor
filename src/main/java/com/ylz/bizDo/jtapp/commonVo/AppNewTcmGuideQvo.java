package com.ylz.bizDo.jtapp.commonVo;

import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.comEnum.CommonTcmType;
import org.apache.commons.lang.StringUtils;

/**
 * Created by zzl on 2017/8/29.
 */
public class AppNewTcmGuideQvo {
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
    private String type;//1医生 2医院 2系统

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取体质类型名称
     * @return
     */
    public String getStrTzlx() throws Exception{
        if(StringUtils.isNotBlank(this.getTzlx())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_TZLX,this.getTzlx());
            if(value != null) {
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
            if(CommonTcmType.YS.getValue().equals(this.getType())){
                AppDrUser drUser = (AppDrUser)dao.getServiceDo().find(AppDrUser.class,this.getCreateId());
                if(drUser!=null){
                    return drUser.getDrName();
                }
            }else if(CommonTcmType.YY.getValue().equals(this.getType())){
                AppHospDept dept = (AppHospDept)dao.getServiceDo().find(AppHospDept.class,this.getCreateId());
                if(dept!=null){
                    return dept.getHospName();
                }
            }else if(CommonTcmType.XT.getValue().equals(this.getType())){
                return "系统";
            }

        }
        return "";
    }
}
