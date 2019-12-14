package com.ylz.view.app.action;

import com.ylz.bizDo.app.po.AppRole;
import com.ylz.bizDo.app.vo.AppRoleQvo;
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
        value="approle",
        results={
                @Result(name="list", location="/intercept/app/role/appRole_list.jsp"),
                @Result(name="edit", location="/intercept/app/role/appRole_edit.jsp"),
                @Result(name = "json", type = "json", params = { "root", "jsons","contentType", "text/html"}),
                @Result(name = "jsontreelist", type = "json",params={"root","jsonList","contentType", "text/html"}),
                @Result(name = "jsonVo", type = "json", params = { "root", "jsonVo","contentType", "text/html"}),
        }
)
public class AppRoleAction extends CommonAction {

    private static final long serialVersionUID = 1L;

    private AppRole vo;

    /**
     * 查询全部
     * @return
     */
    public String forList() {
        return "list";
    }

    /**
     * 查询全部
     * @return
     */
    public String list() {
        try {
            AppRoleQvo qvo = (AppRoleQvo)getQvoJson(AppRoleQvo.class);
            List<AppRole> ls = sysDao.getAppRoleDao().findListQvo(qvo);
            jsons.setRowsQvo(ls,qvo);
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
        return "json";
    }

    /**
     * 准备新增或者修改
     * @return
     */
    public String forAddOrEdit(){
        return "edit";
    }

    /**
     * 查询单条记录
     * @return
     */
    public String jsonByOne(){
        String id = this.getRequest().getParameter("id");
        this.setJsonVo((AppRole) sysDao.getServiceDo().find(AppRole.class, id));
        return "jsonVo";
    }

    /**
     * 添加
     * @return
     */
    public String add() {
        try {
            AppRole vo = (AppRole)getVoJson(AppRole.class);
            if (vo != null) {
                sysDao.getServiceDo().add(vo);
                this.newMsgJson(finalSuccessrMsg);
            }else{
                this.newMsgJson(finalErrorMsg);
            }
        } catch (DaoException e) {
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(finalErrorMsg);
            return "json";
        }
        return "json";
    }

    /**
     * 更新
     * @return
     */
    public String modify() {
        try {
            AppRole vo = (AppRole)getVoJson(AppRole.class);
            if (vo != null) {
                AppRole role = (AppRole) sysDao.getServiceDo().find(AppRole.class,vo.getId());
                role.setRoleName(vo.getRoleName());
                role.setRoleValue(vo.getRoleValue());
                role.setRoleState(vo.getRoleState());
                this.sysDao.getServiceDo().removePoFormSession(role);
                this.sysDao.getServiceDo().modify(role);
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
     * 批量删除
     * @return
     */
    public String delete() {
        try {
            String id = this.getRequest().getParameter("id");
            String[] ids = id.split(";");
            if(ids != null && ids.length >0){
                for(String s : ids){
                    sysDao.getServiceDo().delete(AppRole.class,s);
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
    public String findCmmInit(){
        try{
            Map<String, Object> map = new HashMap<String, Object>();
            //状态
            List<CdCode> lsEnable = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_ENABLE, CommonEnable.QIYONG.getValue());
            map.put("enable", lsEnable);
            this.getJsons().setMap(map);
            return "json";
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(finalErrorMsg);
            return "json";
        }

    }


    public AppRole getVo() {
        return vo;
    }

    public void setVo(AppRole vo) {
        this.vo = vo;
    }
}
