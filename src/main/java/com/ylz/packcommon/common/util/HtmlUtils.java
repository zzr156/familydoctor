
package com.ylz.packcommon.common.util;

import com.ylz.bizDo.jtapp.commonEntity.AppMessageEntity;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.List;


/**
 * <p>Title: HTML文件的通用操作</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author 陈亚琛
 * @version 1.0
 */
public class HtmlUtils
{
//	private static HtmlUtils instance = null;
	
	public static HtmlUtils getInstance()
	{
//		if(instance == null) {
		HtmlUtils instance = new HtmlUtils();
//        }
		return instance;
	}
	
	private HtmlUtils()
	{
	}
	
	/**
	 * 取得目标网页的HTML
	 * @param strUrl
	 * @return
	 */
	public String loadURL(String strUrl)
	{
		java.net.URL url;
		java.io.InputStream is = null;
		String contentBuffer = null;
		try
		{
			url = new java.net.URL(strUrl);
			java.net.URLConnection conn = url.openConnection();   
			conn.connect();
			is = conn.getInputStream();
			contentBuffer = getResponseString(is, "utf-8");
			is.close();
		}
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			if(is != null) {
                try
                {
                    is.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
		}
		return contentBuffer.toString();
	}
	
	
	
	/**
	 * 模拟IE浏览器,执行post或get方法 返回httpclient
	 * @param httpClient 为空则创建新的http客户端
	 * @param method 提交方式(post/get)
	 * @param url URL地址
	 * @param params 参数列表(get方式不需要此项,如带入也无效)
	 * @return 
	 */
	public HttpClient execute(HttpClient httpClient,String method, String url, List<NameValuePair> params)
	{
		if(httpClient == null) {
            httpClient = HttpClients.createDefault();
        }
		if(StringUtils.isEmpty(method)) {
            method= "post";
        }
		try
		{
			if(method.equalsIgnoreCase("post"))
			{
				HttpPost httpPost = new HttpPost(url);
				if(params != null) {
                    httpPost.setEntity(new UrlEncodedFormEntity(params,Consts.UTF_8));
                }
				System.out.println(httpClient.execute(httpPost));
			}
			if(method.equalsIgnoreCase("get"))
			{
				HttpGet httpGet = null;
				if(params != null){
					httpGet = new HttpGet(url +"?"+EntityUtils.toString(new UrlEncodedFormEntity(params, Consts.UTF_8)));
				}else{
					httpGet = new HttpGet(url);
				}
				httpClient.execute(httpGet);
			}
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		return httpClient;
	}
	
	/**
	 * 模拟IE浏览器,执行post或get方法 返回页面结果内容
	 * @param httpClient 为空则创建新的http客户端
	 * @param method 提交方式(post/get)
	 * @param url URL地址
	 * @param params 参数列表(get方式不需要此项,如带入也无效)
	 * @param codename 网页的字符编码
	 * @return 网页内容
	 */
	public String executeResponse(HttpClient httpClient,String method, String url, List<NameValuePair> params,String codename)
	{
		if(httpClient == null) {
            httpClient =  HttpClients.createDefault();
        }
		if(StringUtils.isEmpty(method)) {
            method= "post";
        }
		try
		{
			if(method.equalsIgnoreCase("post"))
			{
				HttpPost httpPost = new HttpPost(url);
				if(params != null) {
                    httpPost.setEntity(new UrlEncodedFormEntity(params, Consts.UTF_8));
                }
				HttpResponse response = httpClient.execute(httpPost);
				if(response.getEntity() == null) {
                    return null;
                }
				InputStream is = response.getEntity().getContent();
				return getResponseString(is, codename);
			}
			if(method.equalsIgnoreCase("get"))
			{
				HttpGet httpGet = null;
				if(params != null){
					httpGet = new HttpGet(url +"?"+EntityUtils.toString(new UrlEncodedFormEntity(params, Consts.UTF_8)));
				}else{
					httpGet = new HttpGet(url);
				}
				httpGet.setHeader("Accept","application/json");
				HttpResponse response = httpClient.execute(httpGet);
				if(response.getEntity() == null) {
                    return null;
                }
				InputStream is = response.getEntity().getContent();
				return getResponseString(is, codename);
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		return null;
	}

	/**
	 * 模拟IE浏览器,执行post或get方法 返回页面结果内容
	 * @param httpClient 为空则创建新的http客户端
	 * @param method 提交方式(post/get)
	 * @param url URL地址
	 * @param params 参数列表(get方式不需要此项,如带入也无效)
	 * @param codename 网页的字符编码
	 * @return 网页内容
	 */
	public String executeResponseJsonPost(HttpClient httpClient,String method, String url, String params,String codename)
	{

		if(httpClient == null) {
            httpClient = HttpClients.createDefault();
        }
		if(StringUtils.isEmpty(method)) {
            method= "post";
        }
		try
		{
			if(method.equalsIgnoreCase("post")){
				HttpPost httpPost = new HttpPost(url);
				if(params != null){
					StringEntity entity = new StringEntity(params,Consts.UTF_8);//解决中文乱码问题
//					entity.setContentEncoding("UTF-8");
					entity.setContentType("application/json");
					httpPost.setEntity(entity);
				}
				HttpResponse response = httpClient.execute(httpPost);
				if(response.getEntity() == null) {
                    return null;
                }
				InputStream is = response.getEntity().getContent();
				return getResponseString(is, codename);
			}

		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		return null;
	}



	public String executeResponseJson(HttpClient httpClient,String method, String url, JSONObject jsonParam,String codename)
	{
		if(httpClient == null) {
            httpClient = HttpClients.createDefault();
        }
		if(StringUtils.isEmpty(method)) {
            method= "post";
        }
		try
		{
			if(method.equalsIgnoreCase("post")){
				HttpPost httpPost = new HttpPost(url);
				if(jsonParam != null){
					StringEntity entity = new StringEntity(jsonParam.toString(),Consts.UTF_8);//解决中文乱码问题
//					entity.setContentEncoding("UTF-8");
		            entity.setContentType("application/json");
		            httpPost.setEntity(entity);
				}
				HttpResponse response = httpClient.execute(httpPost);
				if(response.getEntity() == null) {
                    return null;
                }
				InputStream is = response.getEntity().getContent();
				return getResponseString(is, codename);
			}

		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return null;
		}
		return null;
	}
	/**
	 * 取得字符流的字符串,用指定编码
	 * @param is 数据流
	 * @param codename 编码格式
	 * @return
	 */
	public String getResponseString(InputStream is,String codename)
	{
		StringBuffer contentBuffer = new StringBuffer();
		try
		{
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,codename));   
			String inputLine = null;   
			while ((inputLine = reader.readLine()) != null) 
			{   
				contentBuffer.append(inputLine);   
			}
			return contentBuffer.toString();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return null;
		}
		
	}


	
	
	
	public static void main(String[] args) throws Exception
	{
//		HttpClient client = HttpClients.createDefault();

//		HttpClient client = HttpClients.createDefault();
//		JSONObject jsonParam = new JSONObject();
//		String urlLogin = "http://192.168.44.123:8081/yiKaTongMaintain/sscard/getCardInfo.htm";
// 		jsonParam.put("idCard","350205199110170013");
//		//jsonParam.put("userName","丁伟森");
////		String str1 = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam,"utf-8");
////		System.out.println(str1);
//		//urlLogin = "http://apptest.ncdhm.com:3000/family/thirdAPI/treatment/records";
//		String str1 = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam,"utf-8");
//		System.out.println(str1);

//		jsonParam.put("ssnumber","D26570890");
//		jsonParam.put("ghh000","fc5ff4ee-9e06-49de-99bf-96fd6c16ef03");
//		jsonParam.put("areaCode","350200");
//		jsonParam.put("jzType","1");
//		jsonParam.put("patientId","fcc4783e-0f00-46b3-ba4a-43ecce317875");
//		jsonParam.put("organizationCode","");
//		String str1 = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam,"utf-8");
//		System.out.println(str1);
//		urlLogin = "http://fdapp.ncdhm.com:3000/family/hr/health/diagnosisBase";
//		str1 = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam,"utf-8");
//		System.out.println(str1);
// 		String str1 = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam,"utf-8");
//		List<NameValuePair> params = new ArrayList<NameValuePair>();
//		params.add(new BasicNameValuePair("year","2017"));
//		params.add(new BasicNameValuePair("month","05"));
//		params.add(new BasicNameValuePair("addr","350203009000"));
//		String urlLogin = "http://10.120.0.82:8080/jwbbrktj/api/count";
//		String str1 = HtmlUtils.getInstance().executeResponse(client, "get", urlLogin, params,"utf-8");
////				System.out.println(str1);
//		JSONObject jsonAll = JSONObject.fromObject(str1);
//		if(jsonAll.get("msg")!= null){
//			String str2 = jsonAll.get("msg").toString();
////					MsgVoRks p = JsonUtil.fromJson(str2,MsgVoRks.class);
////						int i = Integer.parseInt(p.getResident())+Integer.parseInt(p.getIn())-Integer.parseInt(p.getOut());
////					int i = Integer.parseInt(p.getResident());
//			AppRksEntity vo = JsonUtil.fromJson(str2, AppRksEntity.class);
//			System.out.println(Integer.parseInt(vo.getReg())+Integer.parseInt(vo.getResident()));
//		}

//		
//		JSONObject jsonParam = new JSONObject();
// 		jsonParam.put("idCard","350205199110170013");
// 		String str1 = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam,"utf-8");
		//测试 添加标签
//		GwTabGroupEntity tab = new GwTabGroupEntity();
//		tab.setTabPatientid("214092");//医生
//		tab.setTabType("1");
//		tab.setTabTitle("嘿嘿");
//		tab.setTabValue("2");
//		tab.setTabSubdValue("5");
//		List<NameValuePair> params = new ArrayList<NameValuePair>();
//		params.add(new BasicNameValuePair("act","addTabGroup"));
//		String json = JsonUtil.toJson(tab);
//		System.out.println(json);
//		params.add(new BasicNameValuePair("strJson",json));
//		String urlLogin = "http://localhost:7077/family-doctor/jtapp.action";
//		String str1 = HtmlUtils.getInstance().executeResponse(client, "post", urlLogin, params,"utf-8");
//		System.out.println(str1);

//      查询个人和公共标签
//		GwTabGroupQvo tab = new GwTabGroupQvo();
//		tab.setTabPatientid("123");//医生
//		短信验证码
//		AppShrotQvo qvo = new AppShrotQvo();
//		qvo.setIdNo("350205199110170013");
//		qvo.setTel("15980990371");
//		qvo.setType("3");
//		List<NameValuePair> params = new ArrayList<NameValuePair>();
//		params.add(new BasicNameValuePair("act","appTelShort"));
//		String json = JsonUtil.toJson(qvo);
//		System.out.println(json);
//		params.add(new BasicNameValuePair("qvoJson",json));
//		String urlLogin = "http://localhost:7077/family-doctor/appCommon.action";
//		String str1 = HtmlUtils.getInstance().executeResponse(client, "post", urlLogin, params,"utf-8");
//		System.out.println(str1);

//		个人注册
//		AppPatientResgisterQvo qvo = new AppPatientResgisterQvo();
//		qvo.setUserCrad("D45215835");
//		qvo.setUserShort("730310");
//		qvo.setUserTel("15980990371");
//		qvo.setUserIdNo("350205199110170013");
//		qvo.setUserName("丁伟森");
//		List<NameValuePair> params = new ArrayList<NameValuePair>();
//		params.add(new BasicNameValuePair("act","appRegister"));
//		String json = JsonUtil.toJson(qvo);
//		System.out.println(json);
//		params.add(new BasicNameValuePair("qvoJson",json));
//		String urlLogin = "http://localhost:7077/family-doctor/hzlogin.action";
//		String str1 = HtmlUtils.getInstance().executeResponse(client, "post", urlLogin, params,"utf-8");
//		System.out.println(str1);


//		个人登录
//		AppPatientUserQvo qvo = new AppPatientUserQvo();
//		qvo.setSelectType("0");
//		qvo.setUserAccount("350205199110170013");
//		qvo.setUserPass("58b8643ebed29bf12fba5b6fceb55ac9");
//		qvo.setUserShort("164877");
//		List<NameValuePair> params = new ArrayList<NameValuePair>();
//		params.add(new BasicNameValuePair("act","appLogin"));
//		String json = JsonUtil.toJson(qvo);
//		System.out.println(json);
//		params.add(new BasicNameValuePair("qvoJson",json));
//		String urlLogin = "http://localhost:7077/family-doctor/hzlogin.action";
//		String str1 = HtmlUtils.getInstance().executeResponse(client, "post", urlLogin, params,"utf-8");
//		System.out.println(str1);
//		base64图片转换
//		List<NameValuePair> params = new ArrayList<NameValuePair>();
//		params.add(new BasicNameValuePair("act","appBaseImageUrl"));
//		params.add(new BasicNameValuePair("qvoJson","{'imageUrl':'d://system//file////2017/04/28////5918120170428.jpg'}"));
//		String urlLogin = "http://localhost:7077/family-doctor/appCommon.action";
//		String str1 = HtmlUtils.getInstance().executeResponse(client, "post", urlLogin, params,"utf-8");
//		System.out.println(str1);
		//查询省市区街道
//		AppAddressQvo qvo = new AppAddressQvo();
//		List<NameValuePair> params = new ArrayList<NameValuePair>();
//		params.add(new BasicNameValuePair("act","appAddressResult"));
//		String json = JsonUtil.toJson(qvo);
//		System.out.println(json);
//		params.add(new BasicNameValuePair("qvoJson",json));
//		params.add(new BasicNameValuePair("token","2756fdc700d7a441"));
//		String urlLogin = "http://localhost:7077/family-doctor/appCommon.action";
//		String str1 = HtmlUtils.getInstance().executeResponse(client, "post", urlLogin, params,"utf-8");
//		System.out.println(str1);
//		查询社区
//		AppAddressQvo qvo = new AppAddressQvo();
//		qvo.setUpId("4CE00843-1D1C-4E85-9ED8-8969D1D42CB9");
//		List<NameValuePair> params = new ArrayList<NameValuePair>();
//		params.add(new BasicNameValuePair("act","appHospResult"));
//		String json = JsonUtil.toJson(qvo);
//		System.out.println(json);
//		params.add(new BasicNameValuePair("qvoJson",json));
//		params.add(new BasicNameValuePair("token","2756fdc700d7a441"));
//		String urlLogin = "http://localhost:7077/family-doctor/appCommon.action";
//		String str1 = HtmlUtils.getInstance().executeResponse(client, "post", urlLogin, params,"utf-8");
//		System.out.println(str1);
//        AppAgreeMentQvo vo = new AppAgreeMentQvo();
//		vo.setType("2");
//		vo.setHospId("dfd96144-1426-4d70-ad51-c3a39c816443");
//		vo.setQyType("1");
//		vo.setTeamId("ab2dcc9a-4cbf-4de6-b0f5-385226156ce1");
//		List<NameValuePair> params = new ArrayList<NameValuePair>();
//		params.add(new BasicNameValuePair("act","appAgreeMent"));
//		String json = JsonUtil.toJson(vo);
//		System.out.println(json);
//		params.add(new BasicNameValuePair("qvoJson",json));
//		params.add(new BasicNameValuePair("token","2756fdc700d7a441"));
//		String urlLogin = "http://localhost:7077/family-doctor/appCommon.action";
//		String str1 = HtmlUtils.getInstance().executeResponse(client, "post", urlLogin, params,"utf-8");
//		System.out.println(str1);
//        String start = ExtendDate.getChineseYMD(Calendar.getInstance());
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
//        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);
//        String end = ExtendDate.getChineseYMD(calendar);
//        System.out.println(start+"==="+end);
//		String key = "c4d34493fd22f4c7";
//		String str1 = "{\"map\":null,\"msg\":null,\"msgCode\":\"800\",\"qvo\":null,\"rows\":null,\"ukey\":\"c4d34493fd22f4c7\",\"vo\":\"NE\\/BSDmeBzsCuJEnNtcznxsHUApxnbtOutVYkZ3aRvIxvS2wyeQDOxSHiDohJzuqN3HTsCcrQkN8STZVyRiJ2lGlBKGcNVQBOgc8sYhthEebQ6CGBwB8Y0\\/RbPMgBuL0iT9i\\/hHhym5kcu3FbmuqUXenDv1Avymuc9oUHjXHnnKLTieA0EuKd3PojS7yXTErfSkyzveWYl\\/6zEeexBnnTr\\/uJBJ7PQrI4xJdDmvH8P6qyMWselliBYruoKIK3BRo9aLmPoPRWXD87MOZ4veSP4QuXaQJir1qofKsOBo8qkRwK07rC2jEz7D0W31N0y+7JKsvf6o\\/f0Ihd3uLUYS3h63dhqtHOSfNcXo7NByaD\\/dhn4YgjmnTqoIkZreFmE+ieHdrrF0RX8VJbeAQeeRAvbqJkg6CaecoWltjfybJDacoc9fmdZlk+sYhMEgpR25SDYUQc07OCUBJxPS4udGAb0WqJuEybXDU0bSzek4FpBI=\"}";
//		JSONObject jsonAll = JSONObject.fromObject(str1);
//		if(jsonAll.get("vo") != null){
//			String result = EncryptUtils.aesDecrypt(jsonAll.get("vo").toString(),key);
//			System.out.println(result);
//		}
//		String fileName = "d://system//file////2017/04/28////5918120170428.jpg";
//		int pos = fileName.lastIndexOf(".");
//		String hz = fileName.substring(pos).replace(".","");
//		System.out.println(hz);
		//查询签约列表
//		GwSignListQvo tab = new GwSignListQvo();
//		tab.setDoctorId("400");
//		tab.setContractState("02");
//		List<NameValuePair> params = new ArrayList<NameValuePair>();
//		params.add(new BasicNameValuePair("act","findSignList"));
//		String json = JsonUtil.toJson(tab);
//		System.out.println(json);
//		params.add(new BasicNameValuePair("qvoJson",json));
//		String urlLogin = "http://localhost:7077/family-doctor/jtapp.action";
//		String str1 = HtmlUtils.getInstance().executeResponse(client, "post", urlLogin, params,"utf-8");
//		System.out.println(str1);

		//签约
//		?year=2016&month=10&addr=350102001
//		JSONObject jsonParam = new JSONObject();
//				List<NameValuePair> params = new ArrayList<NameValuePair>();
////		params.add(new BasicNameValuePair("ssnumber","D91803259"));
////		params.add(new BasicNameValuePair("act","findLvCount"));
//		params.add(new BasicNameValuePair("act","findZtfxCount"));
////		params.add(new BasicNameValuePair("qvoJson","{'tjType':'1'}"));
//		String urlLogin = "http://localhost:7077/family-doctor/jtapp.action";
//		String str1 = HtmlUtils.getInstance().executeResponse(client, "post", urlLogin, params,"utf-8");
//		System.out.println(str1);
//		String s = "350212001000,350212002000,350212106000,350212110000,350212109000,350212105000,350212108000,350212107000,350203003000,350203001000,350203005000,350203006000,350203007000,350203008000,350203009000,350203010000,350203011000,350203012000,350213104000,350213102000,350213111000,350213103000,350213404000,350211001000,350211002000,350211102000,350211103000,350211003000,350211004000,350205001000,350205003000,350205004000,350205002000,350206001000,350206004000,350206003000,350206002000,350206005000";
//		String s = "350212,350203,350213,350211,350205,350206";
//		String[] ss = s.split(",");
//		if(ss != null){
//			for(String b : ss){
//				List<NameValuePair> params = new ArrayList<NameValuePair>();
//				params.add(new BasicNameValuePair("year","2017"));
//				params.add(new BasicNameValuePair("month","04"));
//				params.add(new BasicNameValuePair("addr",b));
//				String urlLogin = "http://10.120.0.82:8080/jwbbrktj/api/count";
//				String str1 = HtmlUtils.getInstance().executeResponse(client, "get", urlLogin, params,"utf-8");
////				System.out.println(str1);
//				JSONObject jsonAll = JSONObject.fromObject(str1);
//				if(jsonAll.get("msg")!= null){
//					String str2 = jsonAll.get("msg").toString();
////					MsgVoRks p = JsonUtil.fromJson(str2,MsgVoRks.class);
////						int i = Integer.parseInt(p.getResident())+Integer.parseInt(p.getIn())-Integer.parseInt(p.getOut());
////					int i = Integer.parseInt(p.getResident());
//			List<MsgVoRks>ls = JsonUtil.fromJson(str2, new TypeToken<List<MsgVoRks>>() {}.getType());
////			int i = Integer.parseInt(ls.get(0).getResident())+Integer.parseInt(ls.get(0).getIn())-Integer.parseInt(ls.get(0).getOut());
//						int i = Integer.parseInt(ls.get(0).getResident());
//					System.out.println(b+"======"+i);
//				}
//			}
//		}



//		//添加患者对应的标签
//		String ids = "615a350c-1431-4775-9786-899e02ec3c35;b7afbe9a-339c-4687-a7a1-2f85b37b4698;";
//		UserBloodgluVo vo = new UserBloodgluVo();
//		vo.setUserid("123");
//		vo.setBgstate("1");
//		vo.setBloodglu("99");
//		vo.setTemptur("22");
//		vo.setTesttime("2017-01-01 10:00:00");
//		List<NameValuePair> params = new ArrayList<NameValuePair>();
//		params.add(new BasicNameValuePair("act","findDoctorByName"));
//		String json = JsonUtil.toJson(vo);
//		System.out.println(json);
//		params.add(new BasicNameValuePair("doctorName","林"));
//		String urlLogin = "http://localhost:7077/family-doctor/jtapp.action";
//		String str1 = HtmlUtils.getInstance().executeResponse(client, "post", urlLogin, params,"utf-8");
//		System.out.println(str1);

//改签约状态
//		GwSignContractStateQvo tab = new GwSignContractStateQvo();
//		tab.setContractState("02");
//		tab.setContractId("2017051309375401110333");
//		List<NameValuePair> params = new ArrayList<NameValuePair>();
//		params.add(new BasicNameValuePair("act","updateSignContractState"));
//		String json = JsonUtil.toJson(tab);
//		System.out.println(json);
//		params.add(new BasicNameValuePair("qvoJson",json));
//		String urlLogin = "http://localhost:7077/family-doctor/jtapp.action";
//		String str1 = HtmlUtils.getInstance().executeResponse(client, "post", urlLogin, params,"utf-8");
//		System.out.println(str1);

//		List<NameValuePair> params = new ArrayList<NameValuePair>();
//		params.add(new BasicNameValuePair("loginName","linmy"));
//		params.add(new BasicNameValuePair("loginPwd","myu123456"));
//		JSONObject jsonParam = new JSONObject();
//		jsonParam.put("loginName","linmy");
//		jsonParam.put("loginPwd","myu123456");
//		String urlLogin = "http://www.msyos.com/platform-ws-cxf/api/login";
//		String str1 = HtmlUtils.getInstance().executeResponseJsonPost(client, "post", urlLogin, jsonParam.toString(),"utf-8");
//		System.out.println(str1);
//		AppMessageEntity v = JsonUtil.fromJson(str1,AppMessageEntity.class);
//		jsonParam = new JSONObject();
//		JSONObject jsonParam1 = new JSONObject();
//		jsonParam.put("checkno",v.getResultBody());
//		jsonParam.put("serviceId","000000");
//		v = new AppMessageEntity();
//		v.setContent("短信验证码为118182,有效期两分钟!");
//		v.setMobiles("15980990371");
//		List<AppMessageEntity> ls = new ArrayList<AppMessageEntity>();
//		ls.add(v);
//		String result = JsonUtil.toJson(v);
//		System.out.println(result);
//		result = result.replace("\"","'");
//		jsonParam1.put("content","短信验证码为:118182,有效期两分钟!");
//		jsonParam1.put("mobiles","15980990371");
//		jsonParam.put("requestContext","\""+result+"\"");
//		System.out.println(jsonParam.toString());
//		urlLogin = "http://www.msyos.com/platform-ws-cxf/api/query";
//		str1 = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam,"utf-8");
//		System.out.println(str1);


	//查询随访
//		GwFollowPlanQvo qvo = new GwFollowPlanQvo();
//		qvo.setPlanStart("2017-05-08");
//		qvo.setPlanEnd("2017-05-15");
//		qvo.setPlanPatientId("11111111");
//		qvo.setPlanDoctorId("400");

//		test vo = new test();
//		vo.setSfFollowPatientid("11111111");
//		vo.setSfFollowState("0");
//		vo.setSfFollowMode("2");
//		vo.setSfFollowDoctorid("400");
//		vo.setSfFollowDate("2017-05-12");
//		vo.setSfFollowDay("3");
//		vo.setSfFollowDayTx("0");
//		vo.setSfFollowType("4");

//		GwTabGroupQvo qvo = new GwTabGroupQvo();
//		qvo.setTabPatientid("394834");
//		List<NameValuePair> params = new ArrayList<NameValuePair>();
//		params.add(new BasicNameValuePair("act","appEarlyWarning"));
////		String json = JsonUtil.toJson(qvo);
////		System.out.println(json);
//		params.add(new BasicNameValuePair("qvoJson","{'doctorId':'1221','ukey':'a2b977d99e94f7f6327516f81f45b0f5132b204237d8ef9b','type':'3'}"));
//		String urlLogin = "http://localhost:7077/family-doctor/jtapp.action";
//		String str1 = HtmlUtils.getInstance().executeResponse(client, "post", urlLogin, params,"utf-8");
//		System.out.println(str1);


//		GwTabGroupQvo tab = new GwTabGroupQvo();
//		tab.setTabPatientid("123");//医生
//		AppUserQvo qvo = new AppUserQvo();
//		qvo.setUserAccount("admin");
//		qvo.setUserPass("a");

//		List<NameValuePair> params = new ArrayList<NameValuePair>();
//		JSONObject jsonParam = new JSONObject();
//		jsonParam.put("name","石小亮");
//		jsonParam.put("idno","350104197903263622");
//		jsonParam.put("city","FZ");
//		String urlLogin = "http://fdapp.ncdhm.com:3000/family/thirdAPI/patient/archive";
//		String str1 = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam,"utf-8");
//		System.out.println(str1);
//		GwMainFamilyDoctor doctor = new GwMainFamilyDoctor();

//		HtmlUtils.msgMessage("70c28cffc23b45ed830bb1676a053aa8","15980990371","小胖是帅哥!");
//		System.out.println(Md5Util.MD5("888888"));
//		SericuryUtil p = new SericuryUtil();
//		System.out.println(p.encrypt("749ef29e-59b9-49e8-8896-871fde55f13c"));
//		String keydoctor = "doctorylz(*&^%";
//		String token = "936fa5bb10f74fd7";
//		AesEncrypt aesEncrypt = new AesEncrypt(keydoctor);
//		String resultProject = aesEncrypt.encrypt("ylzywfamilydoctor");
//		String resultToken = token.substring(0,16);
//		String resultProject = token.substring(16,token.length());
//		System.out.println(token);
//		System.out.println(resultToken);
//		System.out.println(resultProject);
//		System.out.println(aesEncrypt.decrypt(resultProject));
//		System.out.println(result);
//		System.out.println(results);
//		String rule = "569823147";
//		String token = "936fa5bb10f74fd7";
//		StringBuilder sb =new StringBuilder(token);
//		System.out.println(token);
//		int v = 1;
//		for(int i=0;i<rule.length();i++){
//				String jg = token.substring(token.length()-v,token.length()-v+1);
//				sb.insert(Integer.parseInt(rule.substring(i,i+1)),jg);
//				System.out.println(sb);
//				v++;
//		}
//		System.out.println(sb);
//		for(int i = rule.length();i>0;i--){
//			sb.deleteCharAt(Integer.parseInt(rule.substring(i-1,i)));
//			System.out.println(sb);
//		}
//		System.out.println(sb);


	}
	
	/**
	 * 加密函数
	 * @param src
	 * @return
	 */
	public static String encrypt(String src) {
		if (src == null) {
            return null;
        }
		StringBuilder codeSB = new StringBuilder();
		for (int i = 0; i < src.length(); i++) {
			int ascii = src.charAt(i);
			if (ascii < 10) {
				codeSB.append("00");
			}
			if (ascii < 100) {
				codeSB.append("0");
			}
			codeSB.append(ascii);
		}
		return jodh(codeSB.toString());
	}
	
	public static String jodh(String src) {
		if (src == null) {
            return null;
        }
		int size = src.length();
		StringBuilder codeSB = new StringBuilder();
		for (int i = 0; i < size; i++) {
			if (i + 1 < size) {
				codeSB.append(src.charAt(i + 1)).append(src.charAt(i));
				i++;
			} else {
				codeSB.append(src.charAt(i));
			}
		}
		return codeSB.toString();
	}

	/**
	 * 解密函数
	 * @param src
	 * @return
	 */
	public static String decrypt(String src) {
		if (src == null) {
            return null;
        }
		StringBuilder codeSB = new StringBuilder();
		try {
			String deSrc = jodh(src);
			int size = deSrc.length();
			for (int i = 0; i < size;) {
				if (i + 3 <= size) {
					String str = "" + deSrc.charAt(i) + deSrc.charAt(i + 1)
							+ deSrc.charAt(i + 2);
					str = str.replaceAll("^(0*)", "");
					codeSB.append((char) Integer.parseInt(str));
					i += 3;
				} else {
					break;
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return codeSB.toString();
	}

	/**
	 * 短信登录
	 * @return
	 */
	public static String msgLogin(){
		String result = "";
		try {
			HttpClient client = HttpClients.createDefault();
			JSONObject jsonParam = new JSONObject();
			jsonParam.put("loginName", PropertiesUtil.getConfValue("msgUserName"));
			jsonParam.put("loginPwd",PropertiesUtil.getConfValue("msgPwd"));
			String urlLogin = PropertiesUtil.getConfValue("msgUrl")+"/login";
			result = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam,"utf-8");
		}catch (Exception e){
			e.printStackTrace();
		}
		return  result;
	}

	/**
	 * 短信发送
	 * @return
	 */
	public static String msgMessage(String resultBody,String tel,String content){
		String result = "";
		try {
			HttpClient client = HttpClients.createDefault();
			JSONObject jsonParam = new JSONObject();
			jsonParam.put("checkno",resultBody);
			jsonParam.put("serviceId",PropertiesUtil.getConfValue("msgServiceId"));
			AppMessageEntity v = new AppMessageEntity();
			v.setContent(content);
			v.setMobiles(tel);
			String results = JsonUtil.toJson(v);
			jsonParam.put("requestContext","\""+results+"\"");
			String urlLogin = PropertiesUtil.getConfValue("msgUrl")+"/query";
			result = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam,"utf-8");
		}catch (Exception e){
			e.printStackTrace();
		}
		return  result;
	}

}
