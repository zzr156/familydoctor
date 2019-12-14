package com.ylz.view.app.action;

import com.ylz.bizDo.app.po.AppDevice;
import com.ylz.bizDo.app.vo.AppDeviceQvo;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.cd.po.CdUser;
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
 * Created by zzl on 2017/6/17.
 */
@SuppressWarnings("all")
@Action(
        value="appdev",
        results={
                @Result(name="list", location="/intercept/app/device/device_list.jsp"),
                @Result(name="edit", location="/intercept/app/device/device_edit.jsp"),
                @Result(name = "json", type = "json", params = { "root", "jsons","contentType", "text/html"}),
                @Result(name = "jsontreelist", type = "json",params={"root","jsonList","contentType", "text/html"}),
                @Result(name = "jsonVo", type = "json", params = { "root", "jsonVo","contentType", "text/html"}),
        }
)
public class AppDeviceAction extends CommonAction {
    private static final long serialVersionUID = 1L;
    private AppDevice vo;

    /**
     * 准备查询
     * @return
     */
    public String forList() {
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
            AppDeviceQvo qvo = (AppDeviceQvo)getQvoJson(AppDeviceQvo.class);
            List<AppDevice> ls = sysDao.getAppDeviceDao().findListQvo(qvo);
            jsons.setRowsQvo(ls,qvo);
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(finalErrorMsg);
            return "json";
        }
        return "json";
    }
    /**
     * 查询单个记录
     * @return
     */
    public String jsonByOne(){
        try{
            String id = this.getRequest().getParameter("id");
            this.setJsonVo((AppDevice) sysDao.getServiceDo().find(AppDevice.class, id));
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
            AppDevice vo = (AppDevice) getVoJson(AppDevice.class);
            if (vo != null) {
                CdUser user = this.getSessionUser();
                if(user!=null){
                    vo.setDevCreateId(user.getUserId());
                }
                Calendar cal = Calendar.getInstance();
                vo.setDevCreateTime(cal);
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
            AppDevice vo = (AppDevice)getVoJson(AppDevice.class);
            if (vo != null) {
                AppDevice dev = (AppDevice)sysDao.getServiceDo().find(AppDevice.class,vo.getId());
                if(dev!=null){
                    dev.setDevName(vo.getDevName());
                    dev.setDevImageUrl(vo.getDevImageUrl());
                    dev.setDevType(vo.getDevType());
                }
                this.sysDao.getServiceDo().removePoFormSession(dev);
                this.sysDao.getServiceDo().modify(dev);
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
                    sysDao.getServiceDo().delete(AppDevice.class,s);
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

    public String findCmmInit(){
        try{
            Map<String,Object> map = new HashMap<String,Object>();
            //设备类型
            List<CdCode> devType = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_DEVTYPE, CommonEnable.QIYONG.getValue());
            map.put("devType",devType);
            this.getJsons().setMap(map);
            return "json";
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(), e.getMessage());
            this.newMsgJson(finalErrorMsg);
            return "json";
        }

    }

    public AppDevice getVo() {
        return vo;
    }

    public void setVo(AppDevice vo) {
        this.vo = vo;
    }
}
