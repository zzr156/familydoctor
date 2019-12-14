package com.ylz.bizDo.jtapp.commonEntity;

import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.comEnum.CommTcmBs;
import com.ylz.packcommon.common.comEnum.CommTcmTz;
import org.apache.commons.lang.StringUtils;

/** 中医体质辨识问题保存返回结果
 * Created by zzl on 2017/8/7.
 */
public class AppTcmEntity {
    private String Id;//主键id
    private String tzType;//体质类型
    private String tzbs;//体质辨识
    private String df;//得分
    private String qzts;//情志调摄
    private String ysty;//饮食调养
    private String qjts;//起居调摄
    private String ydbj;//运动保健
    private String xwbj;//穴位保健
    private String other;//其他
    private String jlId;//答题记录Id

    public String getTzType() {
        return tzType;
    }

    public void setTzType(String tzType) {
        this.tzType = tzType;
    }

    public String getTzbs() {
        return tzbs;
    }

    public void setTzbs(String tzbs) {
        this.tzbs = tzbs;
    }

    public String getDf() {
        return df;
    }

    public void setDf(String df) {
        this.df = df;
    }

    public String getJlId() {
        return jlId;
    }

    public void setJlId(String jlId) {
        this.jlId = jlId;
    }

    /**
     * 获取体质类别名称
     * @return
     */
   public String getTzName() throws Exception{
        if(StringUtils.isNotBlank(this.getTzType())){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_TZLX, this.getTzType());
            if(value!=null) {
                return value.getCodeTitle();
            }
        }
        return "";
    }

    /**
     * 获取体质辨识名称
     * @return
     */
    public String getTzbsName() throws Exception{
       if(StringUtils.isNotBlank(this.getTzbs())){
           SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
           CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_TZBS, this.getTzbs());
           if(value!=null) {
               if(CommTcmBs.QXS.getValue().equals(this.getTzbs())){
                   if(CommTcmTz.PHZ.getValue().equals(this.getTzType())){
                        return "基本是";
                   }
               }
           }
               return value.getCodeTitle();
       }
       return "";
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
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

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

}
