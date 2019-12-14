package com.ylz.view.app.action;

import com.ylz.bizDo.app.po.AppWorkType;
import com.ylz.bizDo.app.vo.AppWorkTypeQvo;
import com.ylz.bizDo.cd.po.CdUser;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.exception.ActionException;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.Calendar;
import java.util.List;

/**
 * Created by zzl on 2017/8/17.
 */
@Action(
        value="appWork",
        results={
                @Result(name="list", location="/intercept/app/workType/appWorkType_list.jsp"),
                @Result(name="edit", location="/intercept/app/workType/appWorkType_edit.jsp"),
                @Result(name = "json", type = "json", params = { "root", "jsons","contentType", "text/html"}),
                @Result(name = "jsontreelist", type = "json",params={"root","jsonList","contentType", "text/html"}),
                @Result(name = "jsonVo", type = "json", params = { "root", "jsonVo","contentType", "text/html"}),
        }
)
public class AppWorkTypeAction extends CommonAction {
    private static final long serialVersionUID = 1L;
    private AppWorkType vo;
    private AppWorkTypeQvo qvo;

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
            AppWorkTypeQvo qvo = (AppWorkTypeQvo)getQvoJson(AppWorkTypeQvo.class);
            List<AppWorkType> ls = sysDao.getAppWorkTypeDao().findList(qvo);
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
     * 查询单个数据
     * @return
     */
    public String jsonByOne(){
        try{
            String id = this.getRequest().getParameter("id");
            this.setJsonVo((AppWorkType) sysDao.getServiceDo().find(AppWorkType.class, id));
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
            AppWorkType vo =(AppWorkType)getVoJson(AppWorkType.class);
            if (vo != null) {
                CdUser user = this.getSessionUser();
                if(user!=null){
                    vo.setWorkCreateId(user.getUserId());
                }
                vo.setWorkCreateTime(Calendar.getInstance());
                sysDao.getServiceDo().add(vo);
                this.newMsgJson(this.finalSuccessrMsg);
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
            AppWorkType vo =(AppWorkType)getVoJson(AppWorkType.class);
            if (vo != null) {
                AppWorkType table = (AppWorkType)sysDao.getServiceDo().find(AppWorkType.class,vo.getId());
                if(table!=null){
                    table.setWorkTitle(vo.getWorkTitle());
                    table.setWorkValue(vo.getWorkValue());
                    //session出现实体重复时的处理
                    this.sysDao.getServiceDo().removePoFormSession(vo);
                    this.sysDao.getServiceDo().modify(table);
                    this.newMsgJson(this.finalSuccessrMsg);
                }
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
     * 删除
     * @return
     */
    public String delete(){
        try{
            String id = this.getRequest().getParameter("id");
            if(StringUtils.isNotBlank(id)){
                String[] ids = id.split(";");//批量删除
                for(String s:ids){
                    sysDao.getServiceDo().delete(AppWorkType.class,s);
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
     * 查询编号是否存在
     * @return
     */
    public String findCmmValue(){
        try{
            String value = this.getRequest().getParameter("value");
            List<AppWorkType> ls = sysDao.getServiceDo().loadByPk(AppWorkType.class,"workValue",value);
            if(ls!=null&&ls.size()>0){
                this.newMsgJson(this.finalSuccessrMsg);
                return "json";
            }
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
        return "json";
    }

    public AppWorkType getVo() {
        return vo;
    }

    public void setVo(AppWorkType vo) {
        this.vo = vo;
    }

    public AppWorkTypeQvo getQvo() {
        return qvo;
    }

    public void setQvo(AppWorkTypeQvo qvo) {
        this.qvo = qvo;
    }
}
