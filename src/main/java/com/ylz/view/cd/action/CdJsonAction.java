package com.ylz.view.cd.action;

import com.ylz.packaccede.common.action.CommonAction;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

/**
 * Created by PC on 2016/2/16.
 * 基础json
 */
@SuppressWarnings("all")
@Action(
        value="cdjson",
        results={
                @Result(name = "json", type = "json",params={"root","jsons",
                        "excludeProperties","rows.*\\.slist,rows.*\\.roles,rows.*\\.user,rows.*\\.children,rows.*\\.sid","contentType", "text/html"}),
                @Result(name = "jsontreelist", type = "json",params={"root","jsonList",
                        "excludeProperties",".*\\.slist,.*\\.roles,.*\\.user","contentType", "text/html"})
        }
)
public class CdJsonAction extends CommonAction {
    /**
     * 基础数据json
     * @return
     */
    public String jsonTreelist() {
        try{
            String type=getRequest().getParameter("type");
            //部门树
            if(type.equals("depttree")) {
                this.setJsonList(this.sysDao.getCdDeptDao().findByState());
                return "jsontreelist";
            }
            //根据部门id查询用户
            if(type.equals("deptuser")) {
                String deptid=getRequest().getParameter("deptid");
                this.setJsonList(this.sysDao.getUserDo().findCdUserJson(deptid));
                return "jsontreelist";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
