package com.ylz.view.common.action;

import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppHealthEnshrine;
import com.ylz.bizDo.jtapp.commonEntity.AppEnshrineHealthEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppPeopleEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppPeopleHealthEntity;
import com.ylz.bizDo.jtapp.commonVo.AppHealthEducationQvo;
import com.ylz.bizDo.jtapp.commonVo.AppHealthQvo;
import com.ylz.bizDo.jtapp.commonVo.AppPeopleTypeQvo;
import com.ylz.bizDo.jtapp.drVo.AppGuideTemplateQvo;
import com.ylz.bizDo.news.po.NewsTable;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.comEnum.AppRoleType;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import javax.management.relation.Role;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2017/7/11.
 */
@SuppressWarnings("all")
@Action(
        value="appHealth",
        results={
                @Result(name = "ajson", type = "json",params={"root","ajson","contentType", "application/json"})
        }
)
public class AppHealthAction extends CommonAction {
    /**
     * 添加公共健康教育模板
     * @return
     */
    public String addHealthEd(){
        try{
            AppHealthEducationQvo qvo = (AppHealthEducationQvo)getAppJson(AppHealthEducationQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                NewsTable news = this.sysDao.getNewsTableDao().addHealth(qvo);
                this.getAjson().setVo(news);
                this.getAjson().setMsg("保存成功");
                this.getAjson().setMsgCode("800");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     *公共添加健康指导
     * @return
     */
    public String addHealthGuide(){
        try{
            AppGuideTemplateQvo qvo = (AppGuideTemplateQvo)getAppJson(AppGuideTemplateQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                AppDrUser user = this.getAppDrUser();
                if(user!=null){
                    if(user.getDrRole().indexOf(AppRoleType.SHEQU.getValue())!=-1){//有医院权限添加模板为医院模板
                        qvo.setHospId(user.getDrHospId());
                    }else{
                        qvo.setUserId(user.getId());
                    }
                }
                this.sysDao.getAppGuideTemplateDao().addHealthGuide(qvo);
                this.getAjson().setMsg("保存成功");
                this.getAjson().setMsgCode("800");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 增加浏览量
     * @return
     */
    public String addBrowseNum(){
        try{
            AppHealthQvo qvo = (AppHealthQvo)getAppJson(AppHealthQvo.class);
            if(qvo == null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                NewsTable table = (NewsTable)sysDao.getServiceDo().find(NewsTable.class,qvo.getId());
                if(table!=null){
                    table.setTableBrowse(String.valueOf(Integer.parseInt(table.getTableBrowse())+1));
                    sysDao.getServiceDo().modify(table);
                    this.getAjson().setMsgCode("800");
                }else{
                    this.getAjson().setMsg("没有此教育模板");
                    this.getAjson().setMsgCode("900");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 收藏健康教育
     * @return
     */
    public String addEnshrineNum(){
        try{
            AppHealthQvo qvo = (AppHealthQvo)getAppJson(AppHealthQvo.class);
            if(qvo == null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                AppHealthEnshrine vv = sysDao.getAppHealthEnshrineDao().find(qvo.getId(),qvo.getUserId());
                if(vv!=null){
                    this.getAjson().setMsg("已收藏，不能重复收藏");
                    this.getAjson().setMsgCode("800");
                    return "ajson";
                }else{
                    AppHealthEnshrine vo =sysDao.getAppHealthEnshrineDao().saveHealth(qvo);
                    if(vo!=null){
                        this.getAjson().setVo(vo);
                        this.getAjson().setMsgCode("800");
                    }else{
                        this.getAjson().setMsg("收藏失败");
                        this.getAjson().setMsgCode("900");
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 查询收藏健康教育
     * @return
     */
    public String findEnshrine(){
        try {
            AppHealthQvo qvo = (AppHealthQvo)getAppJson(AppHealthQvo.class);
            if(qvo == null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                List<AppEnshrineHealthEntity> ls = sysDao.getAppHealthEnshrineDao().findEnshrineList(qvo);
                this.getAjson().setMsgCode("800");
                this.getAjson().setRows(ls);
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 查看收藏教育具体内容
     * @return
     */
    public String findEnshContent(){
        try{
            AppHealthQvo qvo = (AppHealthQvo)getAppJson(AppHealthQvo.class);
            if(qvo==null){
                this.getAjson().setMsgCode("900");
                this.getAjson().setMsg("参数格式错误");
            }else{
                AppEnshrineHealthEntity vo= sysDao.getAppHealthEnshrineDao().findEnshrine(qvo.getId());
                this.getAjson().setVo(vo);
                this.getAjson().setMsgCode("800");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsg(e.getMessage());
            this.getAjson().setMsgCode("900");
        }
        return "ajson";
    }

    /**
     * 取消收藏的健康教育
     * @return
     */
    public String delEnsh(){
        try{
            AppHealthQvo qvo = (AppHealthQvo)getAppJson(AppHealthQvo.class);
            if(qvo == null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                AppHealthEnshrine table = (AppHealthEnshrine)sysDao.getServiceDo().find(AppHealthEnshrine.class,qvo.getId());
                if(table!=null){
                    sysDao.getServiceDo().delete(table);
                    this.getAjson().setMsgCode("800");
                    this.getAjson().setMsg("取消成功");
                }else {
                    AppHealthEnshrine table1 = sysDao.getAppHealthEnshrineDao().findByQvo(qvo);
                    if(table1!=null){
                        sysDao.getServiceDo().delete(table1);
                        this.getAjson().setMsgCode("800");
                        this.getAjson().setMsg("取消成功");
                    }else{
                        this.getAjson().setMsgCode("900");
                        this.getAjson().setMsg("查无数据");
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

    /**
     * 查询居民统计数(根据服务类型、健康情况、疾病类型)
     * @return
     */
    public String findPeopleCount(){
        try{
            AppPeopleTypeQvo qvo = (AppPeopleTypeQvo)getAppJson(AppPeopleTypeQvo.class);
            if(qvo==null){
                this.getAjson().setMsg("参数格式错误");
                this.getAjson().setMsgCode("900");
            }else{
                Map<String,Object> map = new HashMap<String,Object>();
                List<AppPeopleHealthEntity> lsCount = sysDao.getAppPatientUserDao().findTypeCount(qvo);
                List<AppPeopleEntity> ls =sysDao.getAppPatientUserDao().findByType(qvo);
                map.put("count",lsCount);
                this.getAjson().setRows(ls);
                this.getAjson().setMap(map);
                this.getAjson().setMsgCode("800");
            }
        }catch (Exception e){
            e.printStackTrace();
            this.getAjson().setMsgCode("900");
            this.getAjson().setMsg(e.getMessage());
        }
        return "ajson";
    }

}
