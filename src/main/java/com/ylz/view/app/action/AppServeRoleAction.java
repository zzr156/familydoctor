package com.ylz.view.app.action;

import com.ylz.bizDo.app.po.AppServeRole;
import com.ylz.bizDo.app.po.AppServeRoleSon;
import com.ylz.bizDo.app.vo.AppServeRoleQvo;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2017/7/21.
 */
@SuppressWarnings("all")
@Action(
        value="appserverole",
        results={
                @Result(name="list", location="/intercept/app/serveRole/serveRole_list.jsp"),
                @Result(name="edit", location="/intercept/app/serveRole/serveRole_edit.jsp"),
                @Result(name="roleEdit", location="/intercept/app/serveRole/serveRole_son_edit.jsp"),
                @Result(name = "json", type = "json", params = { "root", "jsons","contentType", "text/html"}),
                @Result(name = "jsontreelist", type = "json",params={"root","jsonList","contentType", "text/html"}),
                @Result(name = "jsonVo", type = "json", params = { "root", "jsonVo","contentType", "text/html"}),
        }
)
public class AppServeRoleAction extends CommonAction {

    private static final String areaCode = "2";
    private AppServeRole vo;

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
    public String forAddOrEditRole(){return  "roleEdit";}

    /**
     * 查询
     * @return
     */
    public String list(){
        try{
            AppServeRoleQvo qvo = (AppServeRoleQvo)getQvoJson(AppServeRoleQvo.class);
            List<AppServeRole> ls = sysDao.getAppServeRoleDao().findList(qvo);
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
     * 查询单条记录
     * @return
     */
    public String jsonByOne(){
        try{
            String id = this.getRequest().getParameter("id");
            this.setJsonVo((AppServeRole) sysDao.getServiceDo().find(AppServeRole.class, id));
            return "jsonVo";
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
    }

    /**
     * 新增
     * @return
     */
    public String add(){
        try {
            AppServeRole vo = (AppServeRole)getVoJson(AppServeRole.class);
            if (vo != null) {
                if(StringUtils.isBlank(vo.getServeRoleAreaCode())){
                    vo.setServeRoleAreaCode(null);
                }
                if(StringUtils.isBlank(vo.getServeRoleHospId())){
                    vo.setServeRoleHospId(null);
                }
                sysDao.getServiceDo().add(vo);
                this.newMsgJson(this.finalSuccessrMsg);
            }else{
                this.newMsgJson(finalErrorMsg);
            }
        } catch (Exception e) {
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
        try {
            AppServeRole vo = (AppServeRole)getVoJson(AppServeRole.class);
            if (vo != null) {
                if(StringUtils.isBlank(vo.getServeRoleAreaCode())){
                    vo.setServeRoleAreaCode(null);
                }
                if(StringUtils.isBlank(vo.getServeRoleHospId())){
                    vo.setServeRoleHospId(null);
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
        try{
            String id = this.getRequest().getParameter("id");
            if(StringUtils.isNotBlank(id)){
                String[] ids = id.split(";");//批量删除
                for(String s:ids){
                    sysDao.getServiceDo().delete(AppServeRole.class,s);
                }
            }
            this.newMsgJson(finalSuccessrMsg);
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
        return "json";

    }

    /**
     * 统一初始化
     * @return
     */
    public String findCmmInit(){
        try{
            Map<String, Object> map = new HashMap<String, Object>();
            List<CdCode> serveState = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_ENABLE, CommonEnable.QIYONG.getValue());
            List<CdCode> serveType = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_MODULE_ROLE_TYPE, CommonEnable.QIYONG.getValue());
            List<CdAddress> ls = this.getSysDao().getCdAddressDao().findUpCasheAreaLevel(areaCode);
            map.put("serveState",serveState);
            map.put("serveRoleAreaCode",ls);
            map.put("serveType",serveType);
            this.getJsons().setMap(map);
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
        }
        return "json";
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
            String settingIds = this.getRequest().getParameter("settingIds");
            String setNums = this.getRequest().getParameter("setNums");
            String setSpace = this.getRequest().getParameter("setSpace");
            String setSpaceType = this.getRequest().getParameter("setSpaceType");
            String[] idsString = ids.split(";");
            String[] setNumString = setNums.split(";");
            String[] settingIdString = settingIds.split(";");
            String[] setSpaceString = setSpace.split(";");
            String[] setSpaceTypeString = setSpaceType.split(";");
            this.getSysDao().getAppServeRoleDao().delForm(id);
            if(settingIdString != null && settingIdString.length >0){
                int i = 0;
                for(String s : settingIdString){
                    AppServeRoleSon son = new AppServeRoleSon();
                    son.setSonServeId(s);
                    son.setSonServeRoleId(id);
                    son.setSonServeSetNum(setNumString[i]);
                    son.setSonServeSetSpaceType(setSpaceTypeString[i]);
                    if(setSpaceTypeString[i].equals("0")){  //其他
                        son.setSonServeSetSpace("0");
                    }else{
                        son.setSonServeSetSpace(setSpaceString[i]);
                    }

                    this.sysDao.getServiceDo().add(son);
                    i++;
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

    public AppServeRole getVo() {
        return vo;
    }

    public void setVo(AppServeRole vo) {
        this.vo = vo;
    }


}
