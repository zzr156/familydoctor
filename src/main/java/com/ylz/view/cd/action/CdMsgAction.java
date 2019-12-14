package com.ylz.view.cd.action;

import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.cd.po.CdMsg;
import com.ylz.bizDo.cd.vo.CdMsgQvo;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.exception.ActionException;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by PC on 2016/2/15.
 * 消息
 */
@Action(
        value="cdmsg",
        results={
                @Result(name="list", location="/intercept/cd/msg/msg_list.jsp"),
                @Result(name="edit", location="/intercept/cd/msg/msg_edit.jsp"),
                @Result(name="view", location="/intercept/cd/msg/msg_view.jsp"),
                @Result(name = "jsonVo", type = "json",params={"root","jsonVo","contentType", "text/html"}),
                @Result(name = "json", type = "json", params = { "root", "jsons","contentType", "text/html"}),
                @Result(name = "jsontreelist", type = "json",params={"root","jsonList","contentType", "text/html"})
        }
)
public class CdMsgAction extends CommonAction  {
    private CdMsg vo;
    private CdMsgQvo qvo;


    //消息菜单链接
    public String forList() {
        try {
        } catch (Exception e) {
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(finalErrorMsg);
            return "json";
        }
        return "list";
    }
    public String jsonByOne(){
        String id = this.getRequest().getParameter("id");
        this.setJsonVo((CdMsg) sysDao.getServiceDo().find(CdMsg.class, id));
        return "jsonVo";
    }
    //消息查询
    public String list() {
        /*try {*/
        try{
            CdMsgQvo qvo = (CdMsgQvo)getQvoJson(CdMsgQvo.class);
            List<CdMsg> ls = sysDao.getCdMsgDao().findList(qvo);
            jsons.setRowsQvo(ls,qvo);
            return "json";
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
    }
    /**
     * 统一初始化
     * @return
     */
    public String findCmmInit(){
        try{
            Map<String, Object> map = new HashMap<String, Object>();
            //消息类别
            List<CdCode> msgType = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_MSGTYPE, CommonEnable.QIYONG.getValue());
            map.put("msgType", msgType);
            this.getJsons().setMap(map);
            return "json";
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
    }
    //准备添加
    public String forAddOrEdit(){
        return "edit";
    }

    //发送消息
    public String add(){
        try {
            CdMsg vo = (CdMsg)getVoJson(CdMsg.class);
            String userCodes=getRequest().getParameter("userCodes");
            String userNames2=getRequest().getParameter("userNames");
            //判断是否公开消息
            if(vo.getMsgType().equals("3")){
                sysDao.getServiceDo().add(vo);
            }else {
                sysDao.getCdMsgDao().addMsg(vo, userCodes, userNames2);
            }
            this.newMsgJson(this.finalSuccessrMsg);
        } catch (Exception e) {
            e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
        }
        return "json";
    }

    //查看消息
    public String view(){
        try {
            String id=getRequest().getParameter("id");
            CdMsg msg= (CdMsg) sysDao.getServiceDo().find(CdMsg.class,id);
            sysDao.getCdMsgDao().readMsg(msg.getId(),this.getSessionUser().getUserId());
            setVo(msg);
        } catch (Exception e) {
            e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(finalErrorMsg);
        }
        return "view";
    }

    public String delete() {
        try
        {
            String id = this.getRequest().getParameter("id");
            String[] ids = id.split(";");
            if(ids != null && ids.length >0){
                for(String s : ids){
                    sysDao.getCdMsgDao().deleteMsg(s);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson("false","删除失败");
            return "json";
        }
        this.newMsgJson("true","删除成功");
        return "json";
    }

    /**
     * 取个人消息
     * @return
     */
    public String userMsgCmm(){
        try{
            CdMsgQvo qvo=new CdMsgQvo();
            qvo.setIsread("1");
            qvo.setPageSize(5);
            qvo.setAccepteruserid(getSessionUser().getUserId());
            this.getJsons().setRows(sysDao.getCdMsgDao().findList(qvo));
            this.getJsons().setQvo(qvo);
            return "json";
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }

    }
    /**
     * 取公共消息
     * @return
     */
    public String ggMsgCmm(){
        try{
            CdMsgQvo qvo = new CdMsgQvo();
            qvo.setPageSize(5);
            this.getJsons().setRows(sysDao.getCdMsgDao().findList(qvo));
            this.getJsons().setQvo(qvo);
            return "json";
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }

    }

    public CdMsg getVo() {
        return vo;
    }

    public void setVo(CdMsg vo) {
        this.vo = vo;
    }

    public CdMsgQvo getQvo() {
        return qvo;
    }

    public void setQvo(CdMsgQvo qvo) {
        this.qvo = qvo;
    }
}
