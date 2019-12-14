package com.ylz.bizDo.flow.dao;

import com.ylz.bizDo.flow.po.FlowDb;
import com.ylz.bizDo.flow.vo.DeplQvo;
import com.ylz.bizDo.flow.vo.FlowDeployment;
import com.ylz.packaccede.common.bizDo.ServiceDo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * Created by PC on 2015/8/26.
 */
@Component("flowDao")
public class FlowDao {
    @Autowired
    private ServiceDo serviceDo;
    //查询部署流程列表
    public List<FlowDeployment> findDeployment(DeplQvo qvo)
    {
        HashMap map=new HashMap();
        StringBuffer sql=new StringBuffer("SELECT a.ID_ id, a.NAME_ name, a.CATEGORY_ category,a.DEPLOY_TIME_ dtime,b.VERSION_ ves,b.KEY_ actkey FROM act_re_deployment a LEFT JOIN act_re_procdef b on a.ID_=b.DEPLOYMENT_ID_ where 1=1");
        if(StringUtils.isNotBlank(qvo.getKey())) {
            sql.append(" and b.KEY_=:key");
            map.put("key",qvo.getKey());
        }
        if(StringUtils.isNotBlank(qvo.getName())) {
            sql.append(" and a.NAME_=:name");
            map.put("name",qvo.getName());
        }
        sql.append(" ORDER BY a.DEPLOY_TIME_ desc");
        List<FlowDeployment> ls=serviceDo.findSqlMapRVo(sql.toString(), map, FlowDeployment.class,qvo);
        if(ls!=null && ls.size()>0) {
            return ls;
        }
        return null;
    }

    /**
     * 流程待办查询
     * @param userid
     * @return
     */
    public List<FlowDb> findDb(String userid){
        if(userid == null){
            return null;
        }else {
            HashMap map=new HashMap();
            StringBuffer sql=new StringBuffer("SELECT DISTINCT\n" +
                    "\tt.ID_ tid,t.EXECUTION_ID_ eid,t.PROC_INST_ID_ pnid,t.PROC_DEF_ID_ pid,t.NAME_ flowname,d.NAME_ typename\n" +
                    "FROM\n" +
                    "\tact_ru_task t,act_re_procdef p,act_re_deployment d\n" +
                    "WHERE\n" +
                    "\tt.PROC_DEF_ID_=p.ID_ and p.DEPLOYMENT_ID_=d.ID_ and\n" +
                    "\tt.ASSIGNEE_ = :userid\n" +
                    "ORDER BY\n" +
                    "\tt.ID_ ASC");
            map.put("userid",userid);
            List<FlowDb> ls=serviceDo.findSqlMapRVo(sql.toString(), map, FlowDb.class);
            if(ls!=null && ls.size()>0) {
                return ls;
            }
            return null;
        }

    }



}
