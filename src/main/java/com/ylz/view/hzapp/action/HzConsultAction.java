package com.ylz.view.hzapp.action;

import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.jtapp.commonEntity.AppCousultEntity;
import com.ylz.bizDo.jtapp.commonVo.AppConsultQvo;
import com.ylz.packaccede.common.action.CommonAction;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.List;

/**个人咨询
 * Created by zzl on 2017/6/25.
 */
@SuppressWarnings("all")
@Action(
        value="hzCon",
        results={
                @Result(name = "ajson", type = "json",params={"root","ajson","contentType", "application/json"})
        }
)
public class HzConsultAction extends CommonAction {
    /**
     * 保存咨询记录
     * @return
     */
    public String saveCon(){
        try{
            AppConsultQvo qvo = (AppConsultQvo)getAppJson(AppConsultQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else {
                AppPatientUser v = this.getAppPatientUser();
                qvo.setCreateId(v.getId());
                this.sysDao.getAppConsultDao().saveCon(qvo);
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 查询个人端咨询 待回复、进行中、已完成
     * @return
     */
    public String findConlist(){
        try{
            AppConsultQvo qvo = (AppConsultQvo)getAppJson(AppConsultQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                List<AppCousultEntity> ls = this.sysDao.getAppConsultDao().findConList(qvo);
                this.getAjson().setMsgCode("800");
                this.getAjson().setRows(ls);
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

}
