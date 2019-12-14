package com.ylz.view.cd.action;


import com.ylz.bizDo.cd.po.SysLogDelet;
import com.ylz.bizDo.cd.vo.LogDeletQvo;
import com.ylz.bizDo.cd.vo.LogDeletVo;
import com.ylz.bizDo.cd.vo.SysCommQvo;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.exception.ActionException;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.List;

/**
 * Created by hzk on 2017/8/24.
 * 删除日志查询
 */
@SuppressWarnings("all")
@Action(
        value="sysLogDeletAction",
        results={
                @Result(name = "json", type = "json",params={"root","jsons","contentType", "application/json"}),
                @Result(name = "jsonLayui", type = "json",params={"root","jsonLayui","contentType", "application/json"})
        }
)
public class SysLogDeletAction extends CommonAction {

    /**
     * 综合查询
     * @return
     */
    public String list(){
        try{
            LogDeletQvo qvo= (LogDeletQvo) getJsonLay(LogDeletQvo.class);
            if(qvo == null){
                qvo = new LogDeletQvo();
            }
            List<LogDeletVo> ls = sysDao.getSysLogDeletDo().findLogDeletList(qvo);
            getJsonLayui().setData(ls);
            getJsonLayui().setCount(qvo.getItemCount());
        }catch (Exception e){
            new ActionException(getClass(), getAct(), getJsons(), e);
        }
        return "jsonLayui";
    }


    /**
     * 查询单条
     * @return
     */
    public String view(){
        try{
            SysCommQvo qvo= (SysCommQvo) getJson(SysCommQvo.class);
            getJsons().setVo(sysDao.getServiceDo().find(SysLogDelet.class,qvo.getId()));
        }catch (Exception e){
            new ActionException(getClass(), getAct(), getJsons(), e);
        }
        return "json";
    }

}
