package com.ylz.view.app.action;

import com.ylz.bizDo.app.po.AppResidentManage;
import com.ylz.bizDo.app.vo.AppResidentManageQvo;
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
 * Created by zzl on 2017/6/17.
 */
@Action(
        value="apprm",
        results={
                @Result(name="list", location="/intercept/app/resident/resident_list.jsp"),
                @Result(name="edit", location="/intercept/app/resident/resident_edit.jsp"),
                @Result(name = "json", type = "json", params = { "root", "jsons","contentType", "text/html"}),
                @Result(name = "jsontreelist", type = "json",params={"root","jsonList","contentType", "text/html"}),
                @Result(name = "jsonVo", type = "json", params = { "root", "jsonVo","contentType", "text/html"}),
        }
)
public class AppResidentManageAction extends CommonAction {

    private static final long serialVersionUID = 1L;
    private AppResidentManage vo;

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
            AppResidentManageQvo qvo = (AppResidentManageQvo)getQvoJson(AppResidentManageQvo.class);
            List<AppResidentManage> ls = this.sysDao.getAppResidentManageDao().findAll(qvo);
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
     * 查询个人
     * @return
     */
    public String jsonByOne(){
        try{
            String id = this.getRequest().getParameter("id");
            AppResidentManage vo = (AppResidentManage) this.sysDao.getServiceDo().find(AppResidentManage.class,id);
            this.setJsonVo(vo);
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
        try{
            AppResidentManage vo = (AppResidentManage) getVoJson(AppResidentManage.class);
            if(vo!=null){
                CdUser user = this.getSessionUser();
                if(user!=null){
                    vo.setRmCreateId(user.getUserId());
                }
                this.sysDao.getServiceDo().add(vo);
                this.newMsgJson(this.finalSuccessrMsg);
            }else{
                this.newMsgJson(this.finalErrorMsg);
            }
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
            AppResidentManage vo = (AppResidentManage) getVoJson(AppResidentManage.class);
            if(vo!=null){
                this.sysDao.getServiceDo().removePoFormSession(vo);
                this.sysDao.getServiceDo().modify(vo);
                this.newMsgJson(finalSuccessrMsg);
            }else{
                this.newMsgJson(this.finalErrorMsg);
            }
        }catch (Exception e){
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
                String[] ids=id.split(";");
                for(String s:ids){
                    this.sysDao.getServiceDo().delete(AppResidentManage.class,s);
                }
                this.newMsgJson(this.finalSuccessrMsg);
            }else{
                this.newMsgJson(this.finalErrorMsg);
            }
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
            return "json";
        }
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
            List<CdCode> labelType = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_HOUSESIGNING, CommonEnable.QIYONG.getValue());
            map.put("rmSignType",labelType);
            this.getJsons().setMap(map);
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
        }
        return "json";
    }

    public AppResidentManage getVo() {
        return vo;
    }

    public void setVo(AppResidentManage vo) {
        this.vo = vo;
    }
}
