package com.ylz.view.app.action;

import com.ylz.bizDo.app.entity.AppServeRoleEntity;
import com.ylz.bizDo.app.po.AppServeSetting;
import com.ylz.bizDo.app.vo.AppServeSettingQvo;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.cd.po.CdUser;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.exception.ActionException;
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
        value="appServe",
        results={
                @Result(name="list", location="/intercept/app/serveSetting/appServe_list.jsp"),
                @Result(name="edit", location="/intercept/app/serveSetting/appServe_edit.jsp"),
                @Result(name = "json", type = "json", params = { "root", "jsons","contentType", "text/html"}),
                @Result(name = "jsontreelist", type = "json",params={"root","jsonList","contentType", "text/html"}),
                @Result(name = "jsonVo", type = "json", params = { "root", "jsonVo","contentType", "text/html"}),
        }
)
public class AppServeAction extends CommonAction {

    private AppServeSetting vo;
    private AppServeSettingQvo qvo;

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
     * 查询
     * @return
     */
    public String list(){
        try{
            AppServeSettingQvo qvo = (AppServeSettingQvo)getQvoJson(AppServeSettingQvo.class);
            List<AppServeSetting> ls = sysDao.getAppServeSettingDao().findList(qvo);
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
     * 查询
     * @return
     */
    public String findCmmServe(){
        try{
            String serveId = this.getRequest().getParameter("serveId");
            List<AppServeRoleEntity> ls = sysDao.getAppServeSettingDao().findCmmServe(serveId);
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
            this.setJsonVo((AppServeSetting) sysDao.getServiceDo().find(AppServeSetting.class, id));
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
            AppServeSetting vo = (AppServeSetting)getVoJson(AppServeSetting.class);
            if (vo != null) {
                //判断数据是否存在
                AppServeSetting vos = this.sysDao.getAppServeSettingDao().findBySer(vo.getSerObjectValue(),vo.getSerValue());
                if(vos!=null){
                    this.newMsgJson("该数据已存在");
                    return "json";
                }
                if(StringUtils.isBlank(vo.getSerSetNum())){
                    vo.setSerSetNum(null);
                }
                CdUser user = this.getSessionUser();
                if(user!=null){
                    vo.setSerCreateUser(user.getUserId());
                    if(user.getUserName().equals("管理员")){
                        vo.setSerCreateState("0");
                    }else{
                        vo.setSerCreateState("1");
                    }
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
        try{
            AppServeSetting vo =(AppServeSetting) getVoJson(AppServeSetting.class);
            if(vo!=null){
                AppServeSetting vos = (AppServeSetting) this.getSysDao().getServiceDo().find(AppServeSetting.class,vo.getId());
                if(StringUtils.isNotBlank(vo.getSerSetNum())){
                    vos.setSerSetNum(vo.getSerSetNum());
                }
                vos.setSerImageName(vo.getSerImageName());
                vos.setSerObjectTitle(vo.getSerObjectTitle());
                vos.setSerObjectValue(vo.getSerObjectValue());
                vos.setSerStartState(vo.getSerStartState());
                vos.setSerTitle(vo.getSerTitle());
                vos.setSerValue(vo.getSerValue());
                vos.setSerImageTitle(vo.getSerImageTitle());
                this.sysDao.getServiceDo().removePoFormSession(vos);
                this.sysDao.getServiceDo().modify(vos);
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
     * 删除
     * @return
     */
    public String delete(){
        try{
            String id = this.getRequest().getParameter("id");
            if(StringUtils.isNotBlank(id)){
                String[] ids = id.split(";");//批量删除
                for(String s:ids){
                    sysDao.getServiceDo().delete(AppServeSetting.class,s);
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
            //服务人群
            List<CdCode> servePeople = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_RESIDENTMANGE, CommonEnable.QIYONG.getValue());
            //服务类型
            List<CdCode> serverType = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_SERVICETYPE, CommonEnable.QIYONG.getValue());
            //开启状态
            List<CdCode> state = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_SFCOMMON,CommonEnable.QIYONG.getValue());

            map.put("servePeople",servePeople);
            map.put("serverType",serverType);
            map.put("state",state);
            this.getJsons().setMap(map);
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
        }

        return "json";
    }



    public AppServeSetting getVo() {
        return vo;
    }

    public void setVo(AppServeSetting vo) {
        this.vo = vo;
    }

    public AppServeSettingQvo getQvo() {
        return qvo;
    }

    public void setQvo(AppServeSettingQvo qvo) {
        this.qvo = qvo;
    }
}
