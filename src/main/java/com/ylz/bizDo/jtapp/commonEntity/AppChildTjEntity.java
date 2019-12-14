package com.ylz.bizDo.jtapp.commonEntity;

import com.ylz.packcommon.common.util.ExtendDate;

import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * Created by zzl on 2017/11/16.
 */
public class AppChildTjEntity {
    private String tjDate;//体检日期
    private String state;//是否及时完成（0否 1是）

    public String getTjDate() {
        return tjDate;
    }

    public void setTjDate(Timestamp tjDate) {
        if(tjDate!=null){
            this.tjDate = ExtendDate.getYMD(tjDate);
        }
    }

    public String getState() {
        return state;
    }

    public void setState(BigInteger state) {
        if(state !=null){
            this.state = String.valueOf(state);
        }else{
            this.state="0";
        }
    }
}
