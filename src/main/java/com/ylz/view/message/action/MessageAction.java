package com.ylz.view.message.action;

import com.ylz.bizDo.message.vo.MessageDrInfo;
import com.ylz.bizDo.message.vo.MessagePatientInfo;
import com.ylz.bizDo.message.vo.MessageQvo;
import com.ylz.packaccede.common.action.CommonAction;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.List;

/**
 * Created by hzk on 2017/7/4.
 * 消息
 */
@SuppressWarnings("all")
@Action(
        value="messageDo",
        results={
                @Result(name = "ajson", type = "json",params={"root","ajson","contentType", "application/json"})
        }
)
public class MessageAction extends CommonAction {
    /**
     * 医生列表 患者使用id获取医生列表
     * @param qvo patientId 患者id name 姓名 模糊查询
     * @return
     */
    public String findDrListByPatientId(){
        try{
            MessageQvo qvo = (MessageQvo)this.getAppJson(MessageQvo.class);
            if(qvo != null){
                List<MessageDrInfo> ls=this.getSysDao().getMessageDao().findDrListByPatientId(qvo);
                this.getAjson().setRows(ls);
                this.getAjson().setQvo(qvo);
                this.getAjson().setMsgCode("800");
            }else{
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("系统错误,请联系管理员");
        }
        return "ajson";
    }

    /**
     * 医生 用团队id查询 患者列表
     * @param qvo teamId 团队id name 姓名 模糊查询
     * @return
     */
    public String findPatientListByTeamId(){
        try{
            MessageQvo qvo = (MessageQvo)this.getAppJson(MessageQvo.class);
            if(qvo != null){
                //qvo.setDrId(this.getAppDrUser().getId());
                List<MessagePatientInfo> ls=this.getSysDao().getMessageDao().findPatientListByTeamId(qvo);
                this.getAjson().setRows(ls);
                this.getAjson().setQvo(qvo);
                this.getAjson().setMsgCode("800");
            }else{
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数错误");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg("系统错误,请联系管理员");
        }
        return "ajson";
    }

}
