package com.ylz.view.app.action;

import com.ylz.bizDo.app.po.AppModuleRole;
import com.ylz.bizDo.app.po.AppModuleRoleSon;
import com.ylz.bizDo.app.vo.AppModuleRoleQvo;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.exception.ActionException;
import com.ylz.packcommon.common.exception.DaoException;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.*;

@Action(
        value="appmodulerole",
        results={
                @Result(name="list", location="/intercept/app/module/module_list.jsp"),
                @Result(name="edit", location="/intercept/app/module/module_edit.jsp"),
                @Result(name="roleEdit", location="/intercept/app/module/module_son_edit.jsp"),
                @Result(name = "json", type = "json", params = { "root", "jsons","contentType", "text/html"}),
                @Result(name = "jsontreelist", type = "json",params={"root","jsonList","contentType", "text/html"}),
                @Result(name = "jsonVo", type = "json", params = { "root", "jsonVo","contentType", "text/html"}),
        }
)
public class AppModuleRoleAction extends CommonAction{

    private static final long serialVersionUID = 1L;
    private static final String areaCode = "2";
    private AppModuleRole vo;


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

    public String forAddOrEditRole(){return  "roleEdit";}
    /**
     * 分页查询
     * @return
     */
    public String list(){
        try{
            AppModuleRoleQvo qvo = (AppModuleRoleQvo)getQvoJson(AppModuleRoleQvo.class);
            List<AppModuleRole> ls = sysDao.getAppModuleRoleDao().findListQvo(qvo);
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
            this.setJsonVo((AppModuleRole) sysDao.getServiceDo().find(AppModuleRole.class, id));
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
            AppModuleRole vo = (AppModuleRole) getVoJson(AppModuleRole.class);
            if (vo != null) {
                if(StringUtils.isBlank(vo.getModuleRoleAreaCode())){
                    vo.setModuleRoleAreaCode(null);
                }
                if(StringUtils.isBlank(vo.getModuleRoleHospId())){
                    vo.setModuleRoleHospId(null);
                }
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
            AppModuleRole vo = (AppModuleRole)getVoJson(AppModuleRole.class);
            if (vo != null) {
                if(StringUtils.isBlank(vo.getModuleRoleAreaCode())){
                    vo.setModuleRoleAreaCode(null);
                }
                if(StringUtils.isBlank(vo.getModuleRoleHospId())){
                    vo.setModuleRoleHospId(null);
                }
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
                    sysDao.getServiceDo().delete(AppModuleRole.class,s);
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
            List<CdCode> moduleState = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_ENABLE, CommonEnable.QIYONG.getValue());
            List<CdCode> moduleMenuType = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_APP_MENU_TYPE, CommonEnable.QIYONG.getValue());
            List<CdCode> moduleType = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_MODULE_ROLE_TYPE, CommonEnable.QIYONG.getValue());
            List<CdCode> moduleManySign = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_MODULE_MANY_SIGN, CommonEnable.QIYONG.getValue());
            List<CdAddress> ls = this.getSysDao().getCdAddressDao().findUpCasheAreaLevel(areaCode);
            map.put("moduleState",moduleState);
            map.put("moduleMenuType",moduleMenuType);
            map.put("moduleRoleAreaCode",ls);
            map.put("moduleType",moduleType);
            map.put("moduleManySign",moduleManySign);
            this.getJsons().setMap(map);
            return "json";
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(finalErrorMsg);
            return "json";
        }

    }

    /**
     * 查询
     * @return
     */
    public String findCmmModuleRoleMenu(){
        try {
            String id = this.getRequest().getParameter("id");
            this.getJsons().setRows(this.getSysDao().getAppModuleRoleSonDao().ModuleRoleMenu(id));
        } catch (Exception e) {
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(finalErrorMsg);
            return "json";
        }
        this.newMsgJson(finalSuccessrMsg);
        return "json";
    }
    /**
     * 权限添加
     * @return
     */
    public  String sonModify(){
        try {
            String id = this.getRequest().getParameter("id");
            String ids = this.getRequest().getParameter("ids");
            String[] idsString = ids.split(";");
            this.getSysDao().getAppModuleRoleSonDao().delForm(id);
            if(idsString != null){
                for(String s : idsString){
                    AppModuleRoleSon son = new AppModuleRoleSon();
                    son.setSonMenuId(id);
                    son.setSonModuleRoleId(s);
                    this.sysDao.getServiceDo().add(son);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(finalErrorMsg);
            return "json";
        }
        this.newMsgJson(finalSuccessrMsg);
        return "json";
    }
    public AppModuleRole getVo() {
        return vo;
    }

    public void setVo(AppModuleRole vo) {
        this.vo = vo;
    }
}

