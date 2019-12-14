package com.ylz.view.cd.action;

import com.ylz.bizDo.sys.po.SysLogBusiness;
import com.ylz.bizDo.sys.po.SysLogBusinessItem;
import com.ylz.bizDo.sys.vo.LogQvo;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.exception.ActionException;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 日志管理
 */
@SuppressWarnings("all")
@Action(
        value="logAction",
        results={
                @Result(name = "json", type = "json",params={"root","jsons","contentType", "application/json"}),
                @Result(name = "jsonLay", type = "json",params={"root","jsonLayui","contentType", "application/json"})
        }
)
public class LogAction extends CommonAction {

    /**
     * 初始数据
     * @return
     */
    public String commList(){
        try {
        }catch (Exception e){
            new ActionException(getClass(), getAct(), getJsons(), e);
        }
        return "json";
    }

    /**
     * 日志列表
     * @return
     */
    public String findList(){
        try{
            LogQvo qvo= (LogQvo) getJsonLay(LogQvo.class);
            if(qvo == null){
                qvo = new LogQvo();
            }
            List<SysLogBusiness> ls = sysDao.getSysLogBusinessDo().findLogList(qvo);
            getJsonLayui().setData(ls);
            getJsonLayui().setCount(qvo.getItemCount());
        }catch (Exception e){
            new ActionException(getClass(), getAct(), getJsons(), e);
        }
        return "jsonLay";
    }

    /**
     *删除日志，单个或是批量删除
     * @return
     */
    public String deleteLog(){
        try
        {
            String id = this.getRequest().getParameter("id");
            String[] ids = id.split(";");
            if(ids != null && ids.length >0) {
                for (String s : ids) {
                    List<SysLogBusinessItem> ls = sysDao.getServiceDo().loadByPk(SysLogBusinessItem.class,"logId",id);
                    for (SysLogBusinessItem item:ls){
                        sysDao.getServiceDo().delete(item);
                    }
                    sysDao.getServiceDo().delete(SysLogBusiness.class,id);
                }
            }
            this.getJsons().setMsg("true");
        }catch (Exception e){
            new ActionException(getClass(), getAct(), getJsons(), e);
        }
        return "json";
    }

    /**
     * 查看单条日志
     * @return
     */
    public String lookOneLogs(){
        try
        {
            String id = this.getRequest().getParameter("id");
            if(StringUtils.isNotBlank(id)){
                Map<String,Object> map=new HashMap<String,Object>();
                map.put("logId",id);
                List<SysLogBusinessItem> ls = sysDao.getServiceDo().loadByPk(SysLogBusinessItem.class,"logId",id);
                getJsons().setRows(ls);
            }


        }catch (Exception e){
            new ActionException(getClass(), getAct(), getJsons(), e);
        }
        return "json";
    }

}
