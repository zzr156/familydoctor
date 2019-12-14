package com.ylz.view.dwr.admin.web;
//package com.xmgrid.view.dwr.admin.web;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.Resource;
//import javax.servlet.http.HttpServletRequest;
//
//import org.apache.commons.beanutils.BeanUtils;
//import org.directwebremoting.ScriptBuffer;
//import org.directwebremoting.annotations.RemoteMethod;
//import org.directwebremoting.annotations.RemoteProxy;
//
//import com.xmgrid.allDo.SysDao;
//import com.xmgrid.bizDo.news.po.NewsRight;
//import com.xmgrid.bizDo.news.po.NewsUser;
//import com.xmgrid.common.Constant;
//import com.xmgrid.view.dwr.admin.po.Login;
//import com.xmgrid.view.dwr.common.DwrCommonServer;
//
///**
// * Created with IntelliJ IDEA.
// * User: hzk
// * Date: 12-11-11
// * Time: 上午9:11
// * To change this template use File | Settings | File Templates.
// */
//@RemoteProxy
//public class LoginDwr extends DwrCommonServer{
//    public SysDao sysDao;
//    public SysDao getSysDao() {
//        return sysDao;
//    }
//    @Resource(name="sysDao")
//    public void setSysDao(SysDao sysDao) {
//        this.sysDao = sysDao;
//    }
//
//    @RemoteMethod
//    public void logins(Map vo,HttpServletRequest request) throws Exception
//    {
//        initDu();
//        Login login=new Login();
//        BeanUtils.populate(login, vo);
//        if(!login.getYzm().equals(request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY)))
//        {
//            du.addScript(new ScriptBuffer("alert('验证码输入错误')"));
//            return;
//        }
//        if(login.getUserName().length()==0)
//        {
//            du.addScript(new ScriptBuffer("alert('用户名不能为空')"));
//            return;
//        }
//        if(login.getUserPassword().length()==0)
//        {
//            du.addScript(new ScriptBuffer("alert('密码不能为空')"));
//            return;
//        }
//        NewsUser nu=sysDao.getNewsUserDo().findByAccountAndPwd(login.getUserName(),login.getUserPassword());
//        if(nu==null)
//        {
//            du.addScript(new ScriptBuffer("alert('找不到此用户或用户密码输入错误')"));
//            return;
//        }
//        else
//        {
//            request.getSession(false).setAttribute(Constant.SESSION_ATTRIBUTE_LOGIN_STAFF,nu);
//            List<NewsRight> rg=new ArrayList<NewsRight>();
//            if(nu.getId().equalsIgnoreCase("admin"))
//            {
//                rg=sysDao.getNewsRightDo().findadminfa(nu.getId(),true);
//                request.getSession(false).setAttribute(Constant.SESSION_FB_TYPE,rg);
//            }else
//            {
//                rg=sysDao.getNewsRightDo().findadminfa(nu.getId(),false);
//                request.getSession(false).setAttribute(Constant.SESSION_FB_TYPE,rg);
//            }
//            du.addScript(new ScriptBuffer("window.location.href ='"+request.getContextPath()+"/news/index.jsp'"));
//        }
//    }
//
//    @RemoteMethod
//    public String logins2(String vo,HttpServletRequest request) throws Exception
//    {
//      initDu();
//      du.addScript(new ScriptBuffer("window.location.href ='http://192.168.10.135:8087/cjoacdc/sys/logon.spr?password=273a0c7bd3c679ba9a6f5d99078e36e85d02b952&userName=admin&skin=default&action=logon&&isWeakPassword=false'"));
//      return null;
//    }
//
//}
