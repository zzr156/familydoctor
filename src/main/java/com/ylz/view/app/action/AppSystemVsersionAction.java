package com.ylz.view.app.action;

import com.ylz.bizDo.app.po.AppSystemVsersion;
import com.ylz.bizDo.app.vo.AppSystemVesionQvo;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.exception.ActionException;
import com.ylz.packcommon.common.exception.DaoException;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统版本更新
 */
@Action(
        value="appsystemversion",
        results={
                @Result(name="list", location="/intercept/app/system/system_list.jsp"),
                @Result(name="edit", location="/intercept/app/system/system_edit.jsp"),
                @Result(name = "json", type = "json", params = { "root", "jsons","contentType", "text/html"}),
                @Result(name = "jsonVo", type = "json", params = { "root", "jsonVo","contentType", "text/html"}),
        }
)
public class AppSystemVsersionAction extends CommonAction{

    private static final long serialVersionUID = 1L;
    private AppSystemVsersion vo;

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
            AppSystemVesionQvo qvo = (AppSystemVesionQvo)getQvoJson(AppSystemVesionQvo.class);
            List<AppSystemVsersion> ls = sysDao.getAppSystemVsersionDao().findListQvo(qvo);
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
            this.setJsonVo((AppSystemVsersion) sysDao.getServiceDo().find(AppSystemVsersion.class, id));
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
            AppSystemVsersion vo = (AppSystemVsersion) getVoJson(AppSystemVsersion.class);
            if (vo != null) {
                vo.setCreateDate(Calendar.getInstance());
                vo.setUpdateTime(Calendar.getInstance());
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
            AppSystemVsersion vo = (AppSystemVsersion)getVoJson(AppSystemVsersion.class);
            if (vo != null) {
                AppSystemVsersion p = (AppSystemVsersion) sysDao.getServiceDo().find(AppSystemVsersion.class,vo.getId());
                if(p != null){
                    p.setChangeLog(vo.getChangeLog());
                    p.setSystem(vo.getSystem());
                    p.setSystemForce(vo.getSystemForce());
                    p.setSystemUpdate(vo.getSystemUpdate());
                    p.setDownLoadUrl(vo.getDownLoadUrl());
                    p.setVsersionCode(vo.getVsersionCode());
                    p.setVsersionName(vo.getVsersionName());
                    p.setType(vo.getType());
                    this.sysDao.getServiceDo().removePoFormSession(p);
                    this.sysDao.getServiceDo().modify(p);
                }

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
                    sysDao.getServiceDo().delete(AppSystemVsersion.class,s);
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
            List<CdCode> systemCode = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_SYSTEM_CODE, CommonEnable.QIYONG.getValue());
            List<CdCode> systemTrueFalse= this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_SYSTEM_TRUE_FALSE, CommonEnable.QIYONG.getValue());
            map.put("systemCode",systemCode);
            map.put("systemTrueFalse",systemTrueFalse);
            this.getJsons().setMap(map);
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
        }
        return "json";
    }

    public AppSystemVsersion getVo() {
        return vo;
    }

    public void setVo(AppSystemVsersion vo) {
        this.vo = vo;
    }
}
