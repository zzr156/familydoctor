package com.ylz.packaccede.common.action;

import com.fasterxml.jackson.databind.JavaType;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.ylz.bizDo.app.po.AppDrPatientKey;
import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppPatientUser;
import com.ylz.bizDo.app.vo.AppJson;
import com.ylz.bizDo.app.vo.SNJson;
import com.ylz.bizDo.app.vo.ThreeJson;
import com.ylz.bizDo.cd.po.CdUser;
import com.ylz.bizDo.sys.vo.SysLogVo;
import com.ylz.bizDo.web.vo.WebJson;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packaccede.common.bean.JsonLayui;
import com.ylz.packcommon.common.CommConditionVo;
import com.ylz.packcommon.common.Constant;
import com.ylz.packcommon.common.Constrats;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.comEnum.CommonType;
import com.ylz.packcommon.common.exception.ActionException;
import com.ylz.packcommon.common.util.*;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.ParentPackage;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import static com.ylz.packcommon.common.util.JacksonUtils.getCollectionType;
import static com.ylz.packcommon.common.util.JacksonUtils.mapper;


@ParentPackage(value = "all")
public class CommonAction extends ActionSupport
{
	
	private static final long serialVersionUID = 1L;
	private static final String keydoctor = "doctorylz(*&^%";
	private static final String projectName = "ylzywfamilydoctor";
	public static final String finalErrorMsg = "系统错误,请联系管理员!";
	public static final String finalSuccessrMsg = "true";
	private List jsonList;
	private Object jsonVo;
	//动作
	private String act;
	//消息框
	private String msg;
	//错误消息框
	private String errorMsg;
	//业务集合
	public SysDao sysDao;
	//layui表格
	public JsonLayui jsonLayui;
	public String limit;

	public String vers;
    public JsonList jsons;
	private AppJson ajson;
	private WebJson upjson;
    private int page=1;
    public int rows=12;
    public String sort;
    public  String order;
    public String handle;
    public String isBack;
    public String loginMenuId;
    private String token;
    private AppPatientUser appPatientUser;
    private AppDrUser appDrUser;
    private AppDrPatientKey appDrPatientKey;
    ActionContext context = ActionContext.getContext();
    private HttpServletRequest request ;
    private HttpServletResponse response ;
    private SNJson snJson;
    private ThreeJson threeJson;

    public HttpServletRequest getRequest() {
        return (HttpServletRequest) context.get(ServletActionContext.HTTP_REQUEST);
    }
    public HttpServletResponse getResponse() {
        return (HttpServletResponse) context.get(ServletActionContext.HTTP_RESPONSE);
    }

	@SuppressWarnings("static-access")
	public String execute(){
		try {
			if (jsons == null) {
				jsons = new JsonList();
			}
			if (ajson == null) {
				ajson = new AppJson();
			}
			if (jsonLayui == null) {
				jsonLayui = new JsonLayui();
			}
			if(upjson == null) {
				upjson = new WebJson();
			}
			if(snJson == null) {
				snJson = new SNJson();
			}
			if(threeJson == null) {
				threeJson = new ThreeJson();
			}
			String act=this.getAct();
			System.out.println(act);
			if(act.equals("webSignUp")){
				return (String)this.getClass().getMethod(act, new Class[]{}).invoke(this,null) ;
			}

//			if(!act.equals("appLogin") && !act.equals("appRegister") && !act.equals("appTelShort")
//					&& !act.equals("appBaseImageUrl")
//					&& !act.equals("applfxt")&&!act.equals("applfgxy") && !act.equals("appPressureSOS")
//					&& !act.equals("apForgetPwd")
//					&& !act.equals("getAppCommonAjson")
//					&& !act.equals("getAppReceiveNotify")&& act.indexOf("Sm")==-1
//					&& !act.equals("getAppReceiveNotify")
//					&& !act.equals("getAppReceiveNotify")
//					&& !act.equals("appJksmRegister")
//					&& !act.equals("appPatientEase")
//					&& !act.equals("getSystemUpdate")
//					&& !act.equals("checktInterface")
//					&& !act.equals("getWarnNotice")
//					&& !act.equals("appPerformanceData")
//					&& !act.equals("addBasicReferral")
//					&& !act.equals("wechatLogin")
//					&& !act.equals("wechatOauthToken")
//					&& !act.equals("upWebSign")
//					&& !act.equals("addBasicDiabetes")
//					&& !act.equals("addBasicHdBloo")
//					&& !act.equals("appAddressResultAio")
//					&& !act.equals("appLoginRegisterAio")
//					&& !act.equals("appBooldSugrData")
//					&& !act.equals("addThreeBloodPressureData")
//					&& !act.equals("manageSignData")
//					&& !act.equals("manageSignPerData")
//					&& !act.equals("manageSignAgeData")
//					&& !act.equals("manageSignGenderData")
//					&& !act.equals("manageSignTotalData")
//					&& !act.equals("manageSignEconomicData")
//					&& !act.equals("manageTeamCount")
//					&& !act.equals("manageChronicDisease")
//					&& !act.equals("citySignManageData")
//					&& !act.equals("appServeAioList")
//					&& !act.equals("wechatVouche")
//					&& !act.equals("mbLogin")&& !act.equals("archivingCardCount") && !act.equals("archivingCardServeCount")
//					&& !act.equals("ceArc")
//					&& !act.equals("findTeamCount") && !act.equals("savaTcmFromJw") && !act.equals("oneButtonSos") && !act.equals("appManageIndexCountNew")
//					&& !act.equals("appLoginPoss")&& !act.equals("tvRegister")&& !act.equals("tvLogin")
//					&& !act.equals("appFindPatientInfo")
//					&& !act.equals("appExternalLogin")
//					&& !act.equals("appExternalHtmlLogin")&& !act.equals("appAccredit")
//					){
//				if(this.getToken()!=null){
////					Map<String, String> map = new HashMap<String, String>();
////					Enumeration headerNames = this.getRequest().getHeaderNames();
////					while (headerNames.hasMoreElements()) {
////						String key = (String) headerNames.nextElement();
////						String value = this.getRequest().getHeader(key);
////						map.put(key, value);
////					}
////					System.out.println(JsonUtil.toJson(map));
//
//					String rule = PropertiesUtil.getConfValue("ruleAuthorization");
//					StringBuilder sb = new StringBuilder(this.getToken());
//					for(int i = rule.length();i>0;i--){
//						sb.deleteCharAt(Integer.parseInt(rule.substring(i-1,i)));
//					}
//					this.setToken(sb.toString());
//					appDrPatientKey=sysDao.getAppDrPatientKeyDao().findDrPatientKeyByToken(this.getToken());
//					if(appDrPatientKey==null){//查询不到用户
//						appDrPatientKey=sysDao.getAppDrPatientKeyDao().findDrTempKeyByToken(this.getToken());//查询是否临时医生
//						if(appDrPatientKey!=null){//临时医生
//							appDrUser = sysDao.getAppDrUserDao().findByUserId(appDrPatientKey.getDrTempId());//获取临时医生信息
//							if(appDrUser != null){//临时用户
//								if(Constrats.ACT_LIST.indexOf(act)>-1){//是临时医生，判断观察模式需排除的功能
//									this.getAjson().setMsg("在观察模式无法操作!");
//									this.getAjson().setMsgCode("2000");
//									return "ajson";
//								}else {
//									if(StringUtils.isNotBlank(act)) {
//										return (String)this.getClass().getMethod(act, new Class[]{}).invoke(this,null) ;
//									}
//								}
//							}else {
//								this.getAjson().setMsg("临时用户信息错误!");
//								this.getAjson().setMsgCode("1000");
//								return "ajson";
//							}
//						}else {//没登入 也不是临时医生
//							this.getAjson().setMsg("请重新登入系统!");
//							this.getAjson().setMsgCode("1000");
//							return "ajson";
//						}
//					}else if(appDrPatientKey!=null){//正常用户
//
//						int stratr =  Integer.parseInt(ExtendDate.getYYYYMMD(Calendar.getInstance()));
//						int end = Integer.parseInt(ExtendDate.getYYYYMMD(appDrPatientKey.getDrPatientTokenEffectiveTime()));
////						if(end - stratr <0){
////							this.getAjson().setMsg("自动登录时间超时,请重新登入系统!");
////							this.getAjson().setMsgCode("2000");
////							return "ajson";
////						}else{
//						//登入状态正常 是否获取用户信息
//						if(StringUtils.isNotBlank(appDrPatientKey.getDrPatientType())){
//							if(appDrPatientKey.getDrPatientType().equals("1")){//患者
//								appPatientUser = sysDao.getAppPatientUserDao().findByUserId(appDrPatientKey.getDrPatientId());
//								if(appPatientUser == null){
//									this.getAjson().setMsg("用户未登入!");
//									this.getAjson().setMsgCode("1000");
//									return "ajson";
//								}
//							}else{
//								appDrUser = sysDao.getAppDrUserDao().findByUserId(appDrPatientKey.getDrPatientId());
//								if(appDrUser == null){
//									this.getAjson().setMsg("用户未登入!");
//									this.getAjson().setMsgCode("1000");
//									return "ajson";
//								}
//							}
//						}
////						}
//					}else {
//						this.getAjson().setMsg("请重新登入系统!");
//						this.getAjson().setMsgCode("1000");
//						return "ajson";
//					}
//				}else{
//					this.getAjson().setMsg("用户未登入!");
//					this.getAjson().setMsgCode("1000");
//					return "ajson";
//
//				}
//			}
			if(StringUtils.isNotBlank(act)) {
				return (String)this.getClass().getMethod(act, new Class[]{}).invoke(this,null) ;
			}
			return "role";
		}catch (Exception e)
		{
			e.printStackTrace();
			new ActionException(this.getClass(),e.getMessage());
			this.getAjson().setMsg("系统异常,请联系管理员!");
			this.getAjson().setMsgCode("1000");
			return "ajson";
		}


//		return super.SUCCESS;
	}
	
	public SysDao getSysDao() {
		return sysDao;
	}
	public void setSysDao(SysDao sysDao) {
		this.sysDao = sysDao;
	}
	
	/**
	 * 文件下载
	 */
	@SuppressWarnings("deprecation")
	public String DownLoad(String fileName,String filePath) throws Exception {
		java.io.InputStream stream=null;
		org.apache.commons.io.output.ByteArrayOutputStream bao=null;
		try
		{
			HttpServletResponse response = ServletActionContext.getResponse(); 
			fileName = new String(fileName.getBytes("GBK"), "iso8859-1");
			response.setContentType("APPLICATION");
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
			
			java.io.OutputStream bos = response.getOutputStream();
			stream = new java.io.FileInputStream(new File(filePath));
			bao = new org.apache.commons.io.output.ByteArrayOutputStream(1024000);
			org.apache.commons.io.CopyUtils.copy(stream, bos);
			bao.writeTo(bos);
			bos.close();
			stream.close();
		}
		catch (Exception ex)
		{
            throw new ActionException(this.getClass(), ex);
		}finally {
			bao.close();
			stream.close();
		}
		return null;
	}
	//添加或修改 判断重复提交
	public boolean isvers(String m)
	{
		String addTime=(String) this.getRequest().getSession().getAttribute(m);
		if(StringUtils.isBlank(addTime)){//第一次请求添加
			this.getRequest().getSession(false).setAttribute(m, this.getRequest().getParameter("vers"));
		}else{
			if(addTime.equals(this.getRequest().getParameter("vers"))){//相同则重复提交
				return true;
			}
			else{
				this.getRequest().getSession().setAttribute(m, this.getRequest().getParameter("vers"));
			}
		}
		return false;
	}
	
	//删除 判断重复提交
	public boolean delisvers(String m,String del)
	{
		String addTime=(String) this.getRequest().getSession().getAttribute(m);
		if(addTime==null){//第一次请求添加
			this.getRequest().getSession().setAttribute(m, del);
		}else{
			if(addTime.equals(del)){//相同则重复提交
				return true;
			}
			else{
				this.getRequest().getSession().setAttribute(m, del);
			}
		}
		return false;
	}

	/**
	 * 取body
	 * @param ctx
	 * @return
	 */
	public static String getRequestBody(ActionContext ctx){
		try {
			HttpServletRequest request = (HttpServletRequest)ctx.get(ServletActionContext.HTTP_REQUEST);
			InputStream inputStream = request.getInputStream();
			String strMessage = "";
			StringBuffer buff = new StringBuffer();
			BufferedReader bufferReader = new BufferedReader(new InputStreamReader(inputStream,"utf-8"));
			while((strMessage = bufferReader.readLine()) != null){
				buff.append(strMessage);
			}
			bufferReader.close();
			inputStream.close();
			return buff.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	protected Object getAppJson(Class t){
		ActionContext ctx = ActionContext.getContext();
		String strJson = getRequestBody(ctx);
		if (StringUtils.isBlank(strJson)) {
			return null;
		}
		return JacksonUtils.getObject(strJson, t);
	}

	protected String getAppStrJson(){
		ActionContext ctx = ActionContext.getContext();
		String strJson = getRequestBody(ctx);
		return strJson;
	}

	protected Object getQvoJson(Class t){
		String strJson = getRequest().getParameter("qvoJson");
		if (StringUtils.isBlank(strJson)) {
			return null;
		}
		return JacksonUtils.getObject(strJson, t);
	}
	protected Object getVoJson(Class t){
		String strJson = getRequest().getParameter("strJson");
		if (StringUtils.isBlank(strJson)) {
			return null;
		}
		return JacksonUtils.getObject(strJson, t);
	}
	/**
	 * 初始化分页json
	 * @param ls
	 * @param qvo
	 */
	public void newPageJson(List ls,CommConditionVo qvo){
        if(ls==null) {
			this.getJsons().setRows(new ArrayList());
		} else {
			this.getJsons().setRows(ls);//放入List
		}
        this.getJsons().setTotal(qvo.getItemCount());//放入总条数
	}
    public void newNotPageJson(List ls){
        if(ls==null) {
			this.getJsons().setRows(new ArrayList());
		} else {
			this.getJsons().setRows(ls);//放入List
		}
    }

    public void newMsgJson(String msg){
        this.getJsons().setMsg(msg);
    }
    
    public void newMsgJson(String msg,String result){
        this.getJsons().setMsg(msg);
        this.getJsons().setResult(result);
    }
    
    public void newVoJson(Object vo){
        this.getJsons().setVo(vo);
    }
    
    public void newMsgVoJson(String msg,Object vo){
        this.getJsons().setMsg(msg);
        this.getJsons().setVo(vo);
    }
    
    public CdUser getSessionUser(){
    	return (CdUser)this.getRequest().getSession()
		.getAttribute(Constant.SESSION_ATTRIBUTE_LOGIN_STAFF);
    }

	protected Object getJsonLay(Class t){
		String strJson = getRequest().getParameter("strJson");
		if (StringUtils.isBlank(strJson)) {
			return null;
		}
		CommConditionVo qvo=((CommConditionVo)JacksonUtils.getObject(strJson, t));
		qvo.setPageStartNo(Integer.valueOf(this.getPage()));
		if(StringUtils.isNotBlank(this.getLimit())){
			qvo.setPageSize(Integer.valueOf(this.getLimit()));
		}
		return qvo;
	}
	protected Object getJson(Class t){
		String strJson = getRequest().getParameter("strJson");
		if (StringUtils.isBlank(strJson)) {
			return null;
		}
		return JacksonUtils.getObject(strJson, t);
	}

	protected List<SysLogVo> getLogVoList() {
		List<SysLogVo> lst = null;
		try {
			String strJson = getRequest().getParameter("strJson");
			if (StringUtils.isNotBlank(strJson)) {
				Map<String, String> m = (Map<String, String>) JacksonUtils.getObject(strJson, Map.class);
				JavaType javaType = getCollectionType(ArrayList.class, SysLogVo.class);
				lst = (List<SysLogVo>) mapper.readValue(JacksonUtils.objToStr(m.get("logVo")), javaType);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return lst;
	}

	public void dels(String m)
	{
		this.getRequest().getSession().setAttribute(m, null);
	}


	public String getAct()
	{
		return act;
	}

    public void setAct(String act) {
		this.act = act;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getVers() {
		return vers;
	}
	public void setVers(String vers) {
		this.vers = vers;
	}
	public JsonList getJsons() {
		return jsons;
	}
	public void setJsons(JsonList jsons) {
		this.jsons = jsons;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public List getJsonList() {
		return jsonList;
	}
	public void setJsonList(List jsonList) {
		this.jsonList = jsonList;
	}
	

	/**
	 * 该方法不做拦截公用查询List 
	 * type查询不同类型:1:基础数据
	 * group 为code的分组名字
	 * @param group
	 * @param type
	 * @return
	 */
	public List findCommonList(String type,String group) {
		try{
			if(type.equals(CommonType.TYPE_CODE.getValue())){
				return this.getSysDao().getCodeDao().findGroupList(group, CommonEnable.QIYONG.getValue());
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

    public Object getJsonVo() {
		return jsonVo;
	}
	public void setJsonVo(Object jsonVo) {
		this.jsonVo = jsonVo;
	}
	public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
	public String getHandle() {
		return handle;
	}
	public void setHandle(String handle) {
		this.handle = handle;
	}
	public String getLoginMenuId() {
		return loginMenuId;
	}
	public void setLoginMenuId(String loginMenuId) {
		this.loginMenuId = loginMenuId;
	}
	public String getIsBack() {
		return isBack;
	}
	public void setIsBack(String isBack) {
		this.isBack = isBack;
	}

	public AppJson getAjson() {
		return ajson;
	}

	public void setAjson(AppJson ajson) {
		this.ajson = ajson;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public AppPatientUser getAppPatientUser() {

		return appPatientUser;
	}

	public void setAppPatientUser(AppPatientUser appPatientUser) {
		this.appPatientUser = appPatientUser;
	}

	public AppDrUser getAppDrUser() {
		return appDrUser;
	}

	public void setAppDrUser(AppDrUser appDrUser) {
		this.appDrUser = appDrUser;
	}

	public AppDrPatientKey getAppDrPatientKey() {
		return appDrPatientKey;
	}

	public void setAppDrPatientKey(AppDrPatientKey appDrPatientKey) {
		this.appDrPatientKey = appDrPatientKey;
	}

	public JsonLayui getJsonLayui() {
		return jsonLayui;
	}

	public void setJsonLayui(JsonLayui jsonLayui) {
		this.jsonLayui = jsonLayui;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public WebJson getUpjson() {
		return upjson;
	}

	public void setUpjson(WebJson upjson) {
		this.upjson = upjson;
	}

	public SNJson getSnJson() {
		return snJson;
	}

	public void setSnJson(SNJson snJson) {
		this.snJson = snJson;
	}

	public ThreeJson getThreeJson() {
		return threeJson;
	}

	public void setThreeJson(ThreeJson threeJson) {
		this.threeJson = threeJson;
	}

}
