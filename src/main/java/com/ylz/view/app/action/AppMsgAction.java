package com.ylz.view.app.action;

import com.ylz.bizDo.app.po.AppMsg;
import com.ylz.bizDo.app.vo.AppMsgQvo;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.exception.ActionException;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2019/1/28.
 */
@SuppressWarnings("all")
@Action(
        value="appMsg",
        results={
                @Result(name="list", location="/intercept/app/appMsg/appMsg_list.jsp"),
                @Result(name="edit", location="/intercept/app/appMsg/appMsg_edit.jsp"),
                @Result(name = "json", type = "json", params = { "root", "jsons","contentType", "text/html"}),
                @Result(name = "jsontreelist", type = "json",params={"root","jsonList","contentType", "text/html"}),
                @Result(name = "jsonVo", type = "json", params = { "root", "jsonVo","contentType", "text/html"}),
                @Result(name = "ajson", type = "json",params={"root","ajson","contentType", "application/json"})
        }
)
public class AppMsgAction extends CommonAction {
        private AppMsgQvo qvo;
        private AppMsg vo;

        /**
         * 菜单链接
         * @return
         */
        public String forList(){
                return "list";
        }

        /**
         * 准备新增或修改
         * @return
         */
        public String forAddOrEdit(){
                return "edit";
        }

        /**
         * 分页查询
         * @return
         */
        public String list(){
                try{
                        AppMsgQvo qvo = (AppMsgQvo)getQvoJson(AppMsgQvo.class);
                        List<AppMsg> list = sysDao.getAppMsgDao().findMsg(qvo);
                        jsons.setRowsQvo(list,qvo);
                }catch (Exception e){
                        e.printStackTrace();
                        new ActionException(this.getClass(), e.getMessage());
                        this.newMsgJson(this.finalErrorMsg);
                        return "json";
                }
                return "json";
        }

        /**
         * 新增
         * @return
         */
        public String add(){
                try{
                        AppMsgQvo vo =(AppMsgQvo)getVoJson(AppMsgQvo.class);
                        sysDao.getAppMsgDao().addMsg(vo);
                        this.newMsgJson(this.finalSuccessrMsg);
                }catch (Exception e){
                        e.printStackTrace();
                        new ActionException(this.getClass(), e.getMessage());
                        this.newMsgJson(this.finalErrorMsg);
                        return "json";
                }
                return "json";
        }

        /**
         * 修改
         * @return
         */
        public String modify(){
                try{
                        AppMsgQvo vo =(AppMsgQvo)getVoJson(AppMsgQvo.class);
                        AppMsg msg =  sysDao.getAppMsgDao().modifyMsg(vo);
                        if(msg != null){
                                this.newMsgJson(this.finalSuccessrMsg);
                        }else{
                                this.newMsgJson(this.finalErrorMsg);
                        }

                }catch (Exception e){
                        e.printStackTrace();
                        new ActionException(this.getClass(), e.getMessage());
                        this.newMsgJson(this.finalErrorMsg);
                }
                return "json";
        }

        /**
         * 删除
         * @return
         */
        public String delete(){
                try{
                        String id = this.getRequest().getParameter("id");
                        if(StringUtils.isNotBlank(id)){
                                AppMsg msg = (AppMsg)sysDao.getServiceDo().find(AppMsg.class,id);
                                if(msg != null){
                                        sysDao.getServiceDo().delete(msg);
                                        this.newMsgJson(this.finalSuccessrMsg);
                                }
                        }else{
                                this.newMsgJson("参数主键不能为空");
                        }

                }catch (Exception e){
                        e.printStackTrace();
                        new ActionException(this.getClass(), e.getMessage());
                        this.newMsgJson(this.finalErrorMsg);
                }
                return "json";
        }

        /**
         * 查询单个
         * @return
         */
        public String jsonByOne(){
                try{
                        String id = this.getRequest().getParameter("id");
                        if(StringUtils.isNotBlank(id)){
                                this.setJsonVo((AppMsg) sysDao.getServiceDo().find(AppMsg.class, id));
                                return "jsonVo";
                        }else{
                                this.newMsgJson("参数不能为空");
                        }

                }catch (Exception e){
                        e.printStackTrace();
                        new ActionException(this.getClass(), e.getMessage());
                        this.newMsgJson(this.finalErrorMsg);
                        return "json";
                }
                return "json";
        }

        public AppMsgQvo getQvo() {
                return qvo;
        }

        public void setQvo(AppMsgQvo qvo) {
                this.qvo = qvo;
        }

        public AppMsg getVo() {
                return vo;
        }

        public void setVo(AppMsg vo) {
                this.vo = vo;
        }
}
