package com.ylz.bizDo.jtapp.commonEntity;

import com.ylz.packcommon.common.Constrats;
import com.ylz.packcommon.common.util.ExtendDate;

import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * Created by zzl on 2017/6/19.
 */
public class AppPressureEntity {
    private String id;
    private Timestamp time;//测量时间
    private Integer sys;//高压
    private Integer dia;//低压
    private Integer pul;//心率
    private String state = "";
    private String note;//备注


    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Integer getSys() {
        return sys;
    }

    public void setSys(Object sys) {
        if(sys instanceof Integer){
            this.sys = (Integer) sys;
        }else if(sys instanceof BigInteger){
            this.sys = ((BigInteger)sys).intValue();
        }

    }

    public Integer getDia() {
        return dia;
    }

    public void setDia(Object dia) {
        if(dia instanceof Integer){
            this.dia = (Integer) dia;
        }else if(dia instanceof BigInteger){
            this.dia = ((BigInteger)dia).intValue();
        }
    }

    public Integer getPul() {
        return pul;
    }

    public void setPul(Object pul) {
        if(pul instanceof Integer){
            this.pul = (Integer) pul;
        }else if(pul instanceof BigInteger){
            this.pul = ((BigInteger)pul).intValue();
        }
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
        if(this.getSys()!=null && this.getSys()> Constrats.TZ_SSYSX){
            state+="收缩压过高";
        }else if(this.getSys()!=null && this.getSys()< Constrats.TZ_SSYXX){
            state+="收缩压过低";
        }
        if(this.getDia()!=null && this.getDia()<Constrats.TZ_SZYXX){
            state+=" 舒张压过低";
        }else if(this.getDia()!=null &&  this.getDia()>Constrats.TZ_SZYSX){
            state+=" 舒张压过高";
        }
        if("".equals(state)){
            state="正常";
        }
        return state;
    }

    public String getSysState(){
        if(this.getSys()!=null && this.getSys()> Constrats.TZ_SSYSX){
            return "2";
        }else if(this.getSys()!=null && this.getSys()< Constrats.TZ_SSYXX){
            return "0";
        }else{
            return "1";
        }

    }
    public String getDiaState(){
        if(this.getDia()!=null && this.getDia()<Constrats.TZ_SZYXX){
            return "0";
        }else if(this.getDia()!=null && this.getDia()>Constrats.TZ_SZYSX){
            return "2";
        }else{
            return "1";
        }
    }

    public String getPulState(){
        if(this.getPul()!=null && this.getPul()<60){
            return "0";
        }else if(this.getPul()!=null && this.getPul()>100){
            return "2";
        }else{
            return "1";
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
