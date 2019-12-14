package com.ylz.view.app.action;


import com.ylz.bizDo.app.po.AppMenu;
import com.ylz.bizDo.app.vo.AppMenuQvo;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.exception.ActionException;
import com.ylz.packcommon.common.exception.DaoException;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Action(
        value="appmenu",
        results={
                @Result(name="list", location="/intercept/app/menu/menu_list.jsp"),
                @Result(name="edit", location="/intercept/app/menu/menu_edit.jsp"),
                @Result(name = "json", type = "json", params = { "root", "jsons","contentType", "text/html"}),
                @Result(name = "jsontreelist", type = "json",params={"root","jsonList","contentType", "text/html"}),
                @Result(name = "jsonVo", type = "json", params = { "root", "jsonVo","contentType", "text/html"}),
        }
)
public class AppMenuAction extends CommonAction {

    private static final long serialVersionUID = 1L;
    private AppMenu vo;

    /**
     * 准备查询
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
            AppMenuQvo qvo = (AppMenuQvo)getQvoJson(AppMenuQvo.class);
            List<AppMenu> ls = sysDao.getAppMenuDao().findListQvo(qvo);
            jsons.setRowsQvo(ls,qvo);
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
        return "json";
    }

    /**
     * 查询手机菜单
     * @return
     */
    public String findMenulist(){
        try{
            AppMenuQvo qvo = (AppMenuQvo)getQvoJson(AppMenuQvo.class);
            List<AppMenu> ls = sysDao.getAppMenuDao().findMenulist(qvo);
            jsons.setRowsQvo(ls,qvo);
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
        return "json";
    }

    /**
     * 根据id查询
     * @return
     */
    public String jsonByOne(){
        try{
            String id = this.getRequest().getParameter("id");
            this.setJsonVo((AppMenu) sysDao.getServiceDo().find(AppMenu.class, id));
            return "jsonVo";
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
    }

    /**
     * 新增
     * @return
     */
    public String add(){
        try{
            AppMenu vo = (AppMenu) getVoJson(AppMenu.class);
            if (vo != null) {
                this.sysDao.getServiceDo().add(vo);
                this.newMsgJson(this.finalSuccessrMsg);
            }else{
                this.newMsgJson(finalErrorMsg);
            }
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
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
        try {
            AppMenu vo = (AppMenu)getVoJson(AppMenu.class);
            if (vo != null) {
                this.sysDao.getServiceDo().removePoFormSession(vo);
                this.sysDao.getServiceDo().modify(vo);
                this.newMsgJson(finalSuccessrMsg);
            }else{
                this.newMsgJson(finalErrorMsg);
            }
        }catch (DaoException e) {
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(finalErrorMsg);
            return "json";
        }
        return "json";
    }

    /**
     * 删除
     * @return
     */
    public String delete(){
        try {
            String id = this.getRequest().getParameter("id");
            String[] ids = id.split(";");
            if(ids != null && ids.length >0){
                for(String s : ids){
                    sysDao.getServiceDo().delete(AppMenu.class,s);
                }
            }
        } catch (DaoException e) {
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(finalErrorMsg);
            return "json";
        }
        this.newMsgJson(finalSuccessrMsg);
        return "json";
    }

    /**
     * 页面初始化
     * @return
     */
    public String findCmmInit() {
        try{
            Map<String, Object> map = new HashMap<String, Object>();
            List<CdCode> menuType = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_APP_MENU_TYPE, CommonEnable.QIYONG.getValue());
            List<CdCode> menuModule = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_APP_MENU_MODULE, CommonEnable.QIYONG.getValue());
            List<CdCode> menuState = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_ENABLE, CommonEnable.QIYONG.getValue());
            map.put("menuState",menuState);
            map.put("menuType",menuType);
            map.put("menuModule",menuModule);
            this.getJsons().setMap(map);
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(finalErrorMsg);
        }
        return "json";
    }

    public AppMenu getVo() {
        return vo;
    }

    public void setVo(AppMenu vo) {
        this.vo = vo;
    }
}

