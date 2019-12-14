package com.ylz.view.ysapp.action;

import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.jtapp.drEntity.AppDrTargetEntity;
import com.ylz.bizDo.jtapp.drVo.AppDrTargetQvo;
import com.ylz.packaccede.common.action.CommonAction;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.Calendar;

/**
 * 管理指标设置
 * Created by zzl on 2017/7/16.
 */
@SuppressWarnings("all")
@Action(
        value="ysTarget",
        results={
                @Result(name = "ajson", type = "json",params={"root","ajson","contentType", "application/json"})
        }
)
public class YsTargetAction extends CommonAction {
    /**
     * 查询指标
     * @return
     */
    public String findList(){
        try{
            AppDrTargetQvo qvo = (AppDrTargetQvo)getAppJson(AppDrTargetQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                Calendar cal = Calendar.getInstance();
                qvo.setYear(String.valueOf(cal.get(Calendar.YEAR)));
                AppDrTargetEntity vo = this.sysDao.getCdAddressPeopleDao().findByQvo(qvo);
                this.getAjson().setVo(vo);
                this.getAjson().setMsgCode("800");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }
    /**
     * 修改指标
     * @return
     */
    public String modify(){
        try{
            AppDrTargetQvo qvo = (AppDrTargetQvo)getAppJson(AppDrTargetQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                AppDrUser drUser = this.getAppDrUser();
                if(drUser != null){
                    if(StringUtils.isBlank(qvo.getOrgid())){
                        qvo.setOrgid(drUser.getDrHospId());
                    }
                }
                AppDrTargetEntity vo=this.sysDao.getCdAddressPeopleDao().modifyTarget(qvo);
                this.getAjson().setVo(vo);
                this.getAjson().setMsgCode("800");
                this.getAjson().setMsg("修改成功");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }
}
