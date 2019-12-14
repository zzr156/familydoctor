package com.ylz.view.app.action;

import com.ylz.bizDo.app.po.AppLabelManage;
import com.ylz.bizDo.app.vo.AppLabelManageQvo;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.cd.po.CdUser;
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

/**
 * Created by zzl on 2017/6/17.
 */
@Action(
        value="applabel",
        results={
                @Result(name="list", location="/intercept/app/label/label_list.jsp"),
                @Result(name="edit", location="/intercept/app/label/label_edit.jsp"),
                @Result(name = "json", type = "json", params = { "root", "jsons","contentType", "text/html"}),
                @Result(name = "jsontreelist", type = "json",params={"root","jsonList","contentType", "text/html"}),
                @Result(name = "jsonVo", type = "json", params = { "root", "jsonVo","contentType", "text/html"}),
        }
)
public class AppLabelManageAction extends CommonAction {
    private static final long serialVersionUID = 1L;
    private AppLabelManage vo;

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
            AppLabelManageQvo qvo = (AppLabelManageQvo)getQvoJson(AppLabelManageQvo.class);
            List<AppLabelManage> ls = sysDao.getAppLabelManageDao().findListQvo(qvo);
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
            this.setJsonVo((AppLabelManage) sysDao.getServiceDo().find(AppLabelManage.class, id));
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
            AppLabelManage vo = (AppLabelManage) getVoJson(AppLabelManage.class);
            if (vo != null) {
                CdUser user = this.getSessionUser();
                if(user!=null){
                    vo.setLabelPatientId(user.getUserId());
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
            AppLabelManage vo = (AppLabelManage)getVoJson(AppLabelManage.class);
            if (vo != null) {
                //AppTeam role = (AppTeam) sysDao.getServiceDo().find(AppTeam.class,vo.getId());
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
                    sysDao.getServiceDo().delete(AppLabelManage.class,s);
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
            //标签类别
            List<CdCode> labelType = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_LABELTYPE, CommonEnable.QIYONG.getValue());
            map.put("labelType",labelType);
            this.getJsons().setMap(map);
            return "json";
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(finalErrorMsg);
            return "json";
        }
    }

    /**
     * 手动签约标签
     * @return
     */
    public String findList(){
        try{
            List<AppLabelManage> ls = sysDao.getAppLabelManageDao().findList();
            if(ls!=null) {
                jsons.setRows(ls);
            }
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
        return "json";
    }


    public AppLabelManage getVo() {
        return vo;
    }

    public void setVo(AppLabelManage vo) {
        this.vo = vo;
    }
}
