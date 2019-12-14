package com.ylz.view.app.action;

import com.ylz.bizDo.app.po.AppHepVideo;
import com.ylz.bizDo.app.vo.AppHepVideoQvo;
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

/**
 * HepVideo Entity 健康教育宣传视频
 *
 * @author dws 2018-11-14
 *
 */
@Action(
        value="videoAction",
        results={
                @Result(name="list", location="/intercept/app/video/video_list.jsp"),
                @Result(name="edit", location="/intercept/app/video/video_edit.jsp"),
                @Result(name = "json", type = "json", params = { "root", "jsons","contentType", "text/html"}),
                @Result(name = "jsonVo", type = "json", params = { "root", "jsonVo","contentType", "text/html"}),
        }
)
public class AppHepVideoAction extends CommonAction {

    private static final long serialVersionUID = 1L;
    private AppHepVideo vo;

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
            AppHepVideoQvo qvo = (AppHepVideoQvo)getQvoJson(AppHepVideoQvo.class);
            List<AppHepVideo> ls = sysDao.getAppHepVideoDao().findListQvo(qvo);
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
            this.setJsonVo((AppHepVideo) sysDao.getServiceDo().find(AppHepVideo.class, id));
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
            AppHepVideo vo = (AppHepVideo) getVoJson(AppHepVideo.class);
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
            AppHepVideo vo = (AppHepVideo)getVoJson(AppHepVideo.class);
            if (vo != null) {
                AppHepVideo p = (AppHepVideo) sysDao.getServiceDo().find(AppHepVideo.class,vo.getId());
                if(p != null){
                    p.setVideoAddress(vo.getVideoAddress());
                    p.setVideoContent(vo.getVideoContent());
                    p.setVideoName(vo.getVideoName());
                    p.setVideoState(vo.getVideoState());
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
                    sysDao.getServiceDo().delete(AppHepVideo.class,s);
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
            List<CdCode> systemCode = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_ENABLE, CommonEnable.QIYONG.getValue());
            map.put("systemEnable",systemCode);
            this.getJsons().setMap(map);
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(this.finalErrorMsg);
        }

        return "json";
    }

    public AppHepVideo getVo() {
        return vo;
    }

    public void setVo(AppHepVideo vo) {
        this.vo = vo;
    }
}
