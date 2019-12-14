package com.ylz.packaccede.fiter;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.ylz.bizDo.cd.po.CdUser;
import com.ylz.packcommon.common.Constant;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpSession;

/**
 * 用户登录,权限拦截器
 * 
 * @author 513-02
 * 
 */
@SuppressWarnings("all")
public class LoginIntecepter extends AbstractInterceptor {

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {

		return invocation.invoke();
		/*boolean flag = false;
		if(invocation.getAction().getClass().getName().contains("open")
				|| invocation.getAction().getClass().getName().contains("HzAppLogin")
				|| invocation.getAction().getClass().getName().contains("HzSign")
				|| invocation.getAction().getClass().getName().contains("YsAppLogin")
				|| invocation.getAction().getClass().getName().contains("YsSign")
				|| invocation.getAction().getClass().getName().contains("AppCommon")
				|| invocation.getAction().getClass().getName().contains("MotoeAction")
				){
			return invocation.invoke();
		}else{
			 HttpSession session = ServletActionContext.getRequest().getSession();
		        CdUser userName = (CdUser)session.getAttribute(Constant.SESSION_ATTRIBUTE_LOGIN_STAFF);
		        if(userName==null){//错误,回到登录界面
		            return Action.LOGIN;
		        }else{
		        	//获取请求的action名称
		            String actionName = invocation.getInvocationContext().getName();
		            //获取请求action的act
//		            Object act = invocation.getInvocationContext().getParameters().get("act");
    	            Object [] objs=(Object[]) invocation.getInvocationContext().getParameters().get("act");
					if(objs==null)
						return "role";
		            String act = objs[0].toString();
		            String roleName = actionName+"_"+act;
		            //遇到下拉树统一不做拦截 和图片读取,下载方法
		            if(act.equals("jsonTreelist") || act.equals("image") || act.equals("jsonList") || act.equals("jsonByOne") ||
		            			act.equals("downLoading") || act.equals("exit") || act.equals("findCommonList")||
		            			act.contains("Cmm")){
		            	flag = true;
		            }else{
//		            	//判断用户在该方法上是否有权限
//			        	List<CdMenuSon> ls=(List<CdMenuSon>)session.getAttribute(Constant.SESSION_SON);
//			        	if(ls != null && ls.size() >0){
//			        		for(CdMenuSon p : ls){
//			        			if(p.getNature().equals(roleName)){
//			        				flag = true;
//			        				break;
//			        			}
//			        		}
//			        	}
		            	flag = true;
		            }

		        }
		        if(flag){
		        	return invocation.invoke();
		        }else{
		        	return "role";
		        }
		}*/

	}
	
}
