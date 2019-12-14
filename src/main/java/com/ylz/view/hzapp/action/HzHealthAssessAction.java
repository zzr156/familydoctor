package com.ylz.view.hzapp.action;

import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.jtapp.patientVo.AppPatientHealthQvo;
import com.ylz.packaccede.common.action.CommonAction;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

/**健康评估
 * Created by zzl on 2017/6/26.
 */
@SuppressWarnings("all")
@Action(
        value="hzAss",
        results={
                @Result(name = "ajson", type = "json",params={"root","ajson","contentType", "application/json"})
        }
)
public class HzHealthAssessAction extends CommonAction {
    /**
     * 查询健康评估
     * @return
     */
    public String findList(){
        try{
            

        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 个人收藏健康教育
     * @return
     */
    public String enshrineHD(){
        try{
            AppPatientHealthQvo qvo = (AppPatientHealthQvo)this.getAppJson(AppPatientHealthQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                AppPatientUser user = this.getAppPatientUser();
                if(user!=null){
                    qvo.setUserId(user.getId());
                    qvo.setUserName(user.getPatientName());
                    this.sysDao.getAppHealthEnshrineDao().enshrineHDP(qvo);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }



}
