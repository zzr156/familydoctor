package com.ylz.bizDo.jtapp.commonEntity;

import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.CommConditionVo;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;

/**
 * Created by zzl on 2018/8/20.
 */
public class AppPossEntity{
    private String id;//数据id
    private String content;//服务内容
    private String time;//服务时间(精确到分钟)
    private String drId;//服务医生主键
    private String drName;//服务医生姓名

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) throws Exception {
        String cc = "";
        if(StringUtils.isNotBlank(content)){
            SysDao dao = (SysDao) SpringHelper.getBean("sysDao");
            CdCode value = dao.getCodeDao().findCodeGroupValue(CodeGroupConstrats.GROUP_SERVICECONTENT,content);
            if(value != null){
                cc = value.getCodeTitle();
            }
        }
        this.content = cc;
    }

    public String getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        if(time != null){
            this.time = ExtendDate.getYMD_h_m(time);
        }else{
            this.time = "";
        }
    }

    public String getDrName() {
        return drName;
    }

    public void setDrName(String drName) {
        if(StringUtils.isBlank(drName)){
            drName = "";
        }
        this.drName = drName;
    }

    public String getDrId() {
        return drId;
    }

    public void setDrId(String drId) {
        if(StringUtils.isBlank(drId)){
            drId = "";
        }
        this.drId = drId;
    }
}
