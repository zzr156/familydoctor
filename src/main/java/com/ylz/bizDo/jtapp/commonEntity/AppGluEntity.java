package com.ylz.bizDo.jtapp.commonEntity;

import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.Constrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;

/**
 * 血糖实体
 */
public class AppGluEntity {
    private String id;
    private Timestamp time;//测量时间
    private String bgState;//时段
    private Double glu;//血糖值
    private String note;//备注信息

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public String getBgState() {
        return bgState;
    }

    public void setBgState(String bgState) {
        this.bgState = bgState;
    }

    public Double getGlu() {
        return glu;
    }

    public void setGlu(Double glu) {
        this.glu = glu;
    }

    public String getStrBgstate() throws Exception{
        if(StringUtils.isNotBlank(this.getBgState())){
            SysDao dao =  (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_BGSTATE, this.getBgState());
            if(value!=null) {
                return value.getCodeTitle();
            }
        }
        return "随机";
    }


    public String getYMD(){
        if(this.getTime()!=null){
            return ExtendDate.getYMD(this.getTime());
        }
        return "";
    }
    public String getMD(){
        if(this.getTime()!=null){
            return ExtendDate.getMD(this.getTime());
        }
        return "";
    }
    public String getHM(){
        if(this.getTime()!=null){
            return ExtendDate.getHHMM(this.getTime());
        }
        return "";
    }

    public String getState(){
        if(StringUtils.isNotBlank(bgState) && glu != null){
            if(bgState.equals("2")||bgState.equals("4")||bgState.equals("6")){
                if(glu>Constrats.TZ_CHXTSX){//11.1
                    return "高";
                }else if(glu>Constrats.TZ_CHXTZX){//7.8
                    return "偏高";
                } else if(glu<Constrats.TZ_CHXTXX){//3.9
                    return "低";
                }
            }else{//餐前
                if(glu>Constrats.TZ_KFXTSX){//7
                    return "高";
                }else if(glu>Constrats.TZ_KFXTZX){//6.1
                    return "偏高";
                }else if(glu<Constrats.TZ_KFXTXX){//3.9
                    return "低";
                }
            }
            return "正常";
        }
        return "";

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStrGlu(){
        if(this.getGlu() != null){
            return String.valueOf(this.getGlu());
        }
        return  "";
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
