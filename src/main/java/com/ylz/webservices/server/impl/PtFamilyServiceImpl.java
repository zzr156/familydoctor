package com.ylz.webservices.server.impl;

import com.ylz.bizDo.app.po.AppLabelEconomics;
import com.ylz.bizDo.app.po.AppSignForm;
import com.ylz.bizDo.app.vo.AppJson;
import com.ylz.bizDo.cd.po.CdOrgconfig;
import com.ylz.bizDo.web.vo.WebUpVo;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.util.AesEncrypt;
import com.ylz.packcommon.common.util.JacksonUtils;
import com.ylz.webservices.server.PtFamilyService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class PtFamilyServiceImpl implements PtFamilyService {

	private static final String signkey = "ylzdocortest@)!*)!";
	@Autowired
	public SysDao sysDao=(SysDao) SpringHelper.getBean("sysDao");

	public String sayHello(String ttt) {
		return "Hello, "+ttt;
	}

	public String getSignInfoByCard(String json){
		AesEncrypt aesEncrypt = new AesEncrypt(signkey);
		AppJson Ajson = new AppJson();
		String str="";
		try{
			WebUpVo p = (WebUpVo) JacksonUtils.getObject(json, WebUpVo.class);;
			String ajson = aesEncrypt.decrypt(p.getStrJson());
			JSONObject fromobject = JSONObject.fromObject(ajson);
			String orgId = fromobject.getString("orgId");
			String idCard = fromobject.getString("idCard");
			CdOrgconfig orgconfig =(CdOrgconfig) sysDao.getServiceDo().find(CdOrgconfig.class,orgId);
			if(orgconfig!=null){
				if(!"1".equals(orgconfig.getStartType())){
					Ajson.setMsgCode("900");
					Ajson.setMsg("该机构没有权限访问！！");
					JSONObject jsonstring = JSONObject.fromObject(Ajson);
					str = jsonstring.toString();
					return str;
				}
			}else{
				Ajson.setMsgCode("900");
				Ajson.setMsg("未找到该机构，请联系管理员");
				JSONObject jsonstring = JSONObject.fromObject(Ajson);
				str = jsonstring.toString();
				return str;
			}
			AppSignForm appSignForm = sysDao.getAppSignformDao().findpatientIdNo(idCard);
			if(appSignForm!=null){
				List<AppLabelEconomics> ls = sysDao.getAppLabelGroupDao().findBySignEcon(appSignForm.getId(),"2");
				if(ls!=null && ls.size()>0){
					Ajson.setMsgCode("800");
					Ajson.setMsg("该居民属于建档立卡");
				}else{
					Ajson.setMsgCode("900");
					Ajson.setMsg("该居民已签约，不属于建档立卡");
				}
			}else {
				Ajson.setMsgCode("900");
				Ajson.setMsg("该居民未签约");
			}
		}catch (Exception e){
			Ajson.setMsgCode("900");
			Ajson.setMsg("接口异常");
			e.printStackTrace();
		}
		JSONObject jsonstring = JSONObject.fromObject(Ajson);
		str = jsonstring.toString();
		return str;
	}

}
