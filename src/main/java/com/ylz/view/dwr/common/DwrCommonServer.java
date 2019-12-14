package com.ylz.view.dwr.common;

import com.ylz.packcommon.common.CommConditionVo;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import org.directwebremoting.annotations.RemoteMethod;
import org.directwebremoting.proxy.dwr.Util;

/**
 * Created with IntelliJ IDEA.
 * User: hzk
 * Date: 13-1-15
 * Time: 上午1:51
 * To change this template use File | Settings | File Templates.
 */
public class DwrCommonServer {
    public Util du;
    public void initDu()
    {   WebContext webc = WebContextFactory.get();
        du = new Util(webc.getScriptSession());
    }
    // dwr分页，页面上显示...........paging
    @RemoteMethod
    public void dwrPaging(Util du, CommConditionVo qvo) {
        if (qvo.isNeedCount()) {
            du.setValue("pageStartNo", qvo.getPageStartNo() + "");
            du.setValue("pageSize", qvo.getPageSize() + "");
            du.setValue("pageCount", qvo.getPageCount() + "");
            du.setValue("flable", qvo.getDescs());
            String[] skipPage = new String[qvo.getPageCount()];
            for (int i = 0; i < qvo.getPageCount(); i++) {
                skipPage[i] = i + 1 + "";
            }
            du.removeAllOptions("skipPage");
            du.addOptions("skipPage", skipPage);
            du.setValue("skipPage", qvo.getPageStartNo() + "");
        }
    }

}
