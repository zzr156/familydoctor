import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.alibaba.fastjson.JSON;
import com.google.gson.reflect.TypeToken;
import com.ylz.bizDo.app.entity.AppGroup;
import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.app.vo.AppSignVo;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.cd.po.CdAddressPeople;
import com.ylz.bizDo.jtapp.basicHealthEntity.HomeServiceItemsEntity;
import com.ylz.bizDo.jtapp.basicHealthEntity.jktj.JmjktjDTO;
import com.ylz.bizDo.jtapp.basicHealthVo.FollowUpQVo;
import com.ylz.bizDo.jtapp.basicHealthVo.HomeServiceItemsQvo;
import com.ylz.bizDo.jtapp.basicHealthVo.JmjktjQvo;
import com.ylz.bizDo.jtapp.commonEntity.AppFamilyInfo;
import com.ylz.bizDo.jtapp.commonEntity.AppPlannedPeopleEntity;
import com.ylz.bizDo.jtapp.commonEntity.AppRksEntity;
import com.ylz.bizDo.jtapp.commonVo.AppCommQvo;
import com.ylz.bizDo.jtapp.drEntity.AppDrPatientFwEntity;
import com.ylz.bizDo.jtapp.drEntity.AppDrPatientSignEntity;
import com.ylz.bizDo.jtapp.patientEntity.AppTeamManagEntity;
import com.ylz.bizDo.web.po.WebTeam;
import com.ylz.easemob.server.example.api.impl.EasemobChatGroup;
import com.ylz.easemob.server.example.api.impl.EasemobIMUsers;
import com.ylz.packaccede.util.CardUtil;
import com.ylz.packaccede.util.SecUtil;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.comEnum.LabelManageType;
import com.ylz.packcommon.common.comEnum.NoticesType;
import com.ylz.packcommon.common.comEnum.SignFormType;
import com.ylz.packcommon.common.util.*;
import com.ylz.task.async.IdCard.IdCardEntity;
import io.swagger.client.model.RegisterUsers;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ylz.bizDo.dd.po.DdJlqkQch;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.Constrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;


public class JunitTest { private ApplicationContext context;

	@Before
	public void before() {
		context = new ClassPathXmlApplicationContext("/spring/app*.xml");
//		context = new ClassPathXmlApplicationContext("/config/spring/appCxfClient.xml");
	}

	/**
	 * 生成批次号
	 */
	public String createAlipayPch(String type,String getTime) throws Exception {
		SysDao dao =  (SysDao) context.getBean("sysDao");
		DateTime dt = new DateTime(getTime);  
		//年  
		int year = dt.getYear();  
		//月  
		int month = dt.getMonthOfYear();  
		//日  
		int day = dt.getDayOfMonth();  
		DdJlqkQch ddJlqkQch = dao.getDdJlqkQchDao().findByYear(year,type);
		//如果存在流水号
		if(ddJlqkQch == null){
			ddJlqkQch = new DdJlqkQch();
			ddJlqkQch.setDdYear(year);
			ddJlqkQch.setDdMonth(month);
			ddJlqkQch.setDdDay(day);
			ddJlqkQch.setDdCode(Constrats.DEAAULT_NO);
			ddJlqkQch.setDdType(type);
			dao.getServiceDo().saveUpdate(ddJlqkQch);
			return formatQpcCode(type,year,Constrats.DEAAULT_NO);
//		} else if(ddJlqkQch.getDdYear()!=year && ddJlqkQch.getDdMonth()!=month && ddJlqkQch.getDdDay()!=day){
//			ddJlqkQch.setDdCode(Constrats.DEAAULT_NO);
//			ddJlqkQch.setDdYear(year);
//			ddJlqkQch.setDdMonth(month);
//			ddJlqkQch.setDdDay(day);
//			dao.getServiceDo().modify(ddJlqkQch);
//			return formatQpcCode(type,year,month,day,Constrats.DEAAULT_NO);
		} else {
			ddJlqkQch.setDdCode(ddJlqkQch.getDdCode()+1);
			dao.getServiceDo().modify(ddJlqkQch);
			return formatQpcCode(type,year,ddJlqkQch.getDdCode());
		}
	}
	
	private String formatQpcCode(int year,int month,int day,Integer childSequence) throws IOException{
		//return departmentCode + year + String.format("1%05d",childSequence);
//		return departmentCode + year + String.format("%04d",10000-childSequence);
		String mon = String.valueOf(month);
		if(mon.length() == 1){
			mon = "0"+mon;
		}
		String days = String.valueOf(day);
		if(days.length() == 1){
			days = "0"+days;
		}
		return year + mon + days + String.format("%08d",childSequence);
	}
	
	private String formatQpcCode(String type,int year,Integer childSequence) throws IOException{
		return type +"("+year +")" + String.format("%04d",childSequence)+"号";
	}

	@Test
	public void test() {
		SysDao sysDao=(SysDao) SpringHelper.getBean("sysDao");
//		sysDao.getSecurityCardAsyncBean().registeredEasemobTemp("29fe7bf7-27c4-4376-8dd7-db7962a01311");
//		sysDao.getAppMsgCodeDao().sendMessage("15980990371", "小胖是帅哥!");
		try{
			String name = "c44b26492a8a74fcbd5780a411b885a7";
			String password = "8622f0f77516e26e350ab549071ac99a";

			String decrypt = SecUtil.decrypt(name);
			String decrypt1 = SecUtil.decrypt(password);
			System.out.println("数据库账户："+decrypt);
			System.out.println("数据库密码："+decrypt1);

			//签约
//			String sql="SELECT * from (\n" +
//					"SELECT\n" +
//					"\tx.hidno,\n" +
//					"\tx.jwDoctorId,\n" +
//					"\tb.ID patientId,\n" +
//					"  c.MEM_TEAMID teamid,\n" +
//					" \td.ID drId,\n" +
//					" \th.HOSP_AREA_CODE\n" +
//					"FROM\n" +
//					"\t(SELECT\n" +
//					"\ta.hidno,\n" +
//					"\ta.jwDoctorId\n" +
//					"FROM\n" +
//					"\tuser4 a  GROUP BY a.hidno,a.jwDoctorId) x\n" +
//					"LEFT JOIN APP_PATIENT_USER b ON x.hidno = b.PATIENT_IDNO\n" +
//					"LEFT JOIN APP_DR_USER d on x.jwDoctorId=d.DR_CODE\n" +
//					"LEFT JOIN APP_TEAM_MEMBER c on d.ID=c.MEM_DR_ID\n" +
//					"LEFT JOIN APP_HOSP_DEPT h on d.DR_HOSP_ID=h.ID  \n" +
//					") f where f.teamid is not null and f.HOSP_AREA_CODE is not null GROUP BY f.patientId";
//			String sql="SELECT * from (\n" +
//					"SELECT\n" +
//					"\tx.hidno,\n" +
//					"\tx.jwDoctorId,\n" +
//					"\tb.ID patientId,\n" +
//					"  c.MEM_TEAMID teamid,\n" +
//					" \td.ID drId,\n" +
//					" \th.HOSP_AREA_CODE\n" +
//					"FROM\n" +
//					"\t(SELECT\n" +
//					"\ta.hidno,\n" +
//					"\ta.jwDoctorId\n" +
//					"FROM\n" +
//					"\tuser4 a  GROUP BY a.hidno,a.jwDoctorId) x\n" +
//					"LEFT JOIN APP_PATIENT_USER b ON x.hidno = b.PATIENT_IDNO\n" +
//					"LEFT JOIN APP_DR_USER d on x.jwDoctorId=d.DR_CODE\n" +
//					"LEFT JOIN APP_TEAM_MEMBER c on d.ID=c.MEM_DR_ID\n" +
//					"LEFT JOIN APP_HOSP_DEPT h on d.DR_HOSP_ID=h.ID  \n" +
//					") f where f.teamid is not null and f.HOSP_AREA_CODE is not null GROUP BY f.patientId\n";
//			List<Map> ls=sysDao.getServiceDo().findSql(sql);
//			for(Map l:ls){
//				String patientId=l.get("patientId").toString();
//				String teamid=l.get("teamid").toString();
//				String drId=l.get("drId").toString();
//				System.out.println("patientId--"+patientId+"--teamid--"+teamid+"--drId--"+drId);
//				AppSignBatch batch=new AppSignBatch();//批次
//				AppSignForm form=new AppSignForm();//签约单
//				AppTeam teamvo= (AppTeam) sysDao.getServiceDo().find(AppTeam.class,teamid);//团队
//				AppPatientUser uservo= (AppPatientUser) sysDao.getServiceDo().find(AppPatientUser.class,patientId);//患者
//				batch.setBatchCreateDate(Calendar.getInstance());
//				batch.setBatchTeamId(teamvo.getId());
//				batch.setBatchTeamName(teamvo.getTeamName());
//				batch.setBatchCreatePersid(uservo.getId());
//				batch.setBatchPatientName(uservo.getPatientName());
//				//组织批次号
//				AppHospDept dept= (AppHospDept) sysDao.getServiceDo().find(AppHospDept.class,teamvo.getTeamHospId());
//				if(dept!=null && dept.getHospAreaCode()!=null) {
//					AppSerial serial = sysDao.getAppSignformDao().getAppSerial(dept.getHospAreaCode().substring(0, 4), "batch");
//					if(serial!=null) {
//						String bcnum = sysDao.getAppSignformDao().getNum(serial.getSerialNo());
//						serial.setSerialNo(bcnum);
//						sysDao.getServiceDo().modify(serial);
//						batch.setBatchNum(bcnum);//批次号
//					}
//				}
//				//
//				sysDao.getServiceDo().add(batch);
//				form.setSignBatchId(batch.getId());
//				//组织编码
//				if(dept!=null && dept.getHospAreaCode()!=null) {
//					form.setSignHospId(dept.getId());
//					form.setSignAreaCode(dept.getHospAreaCode());
//					AppSerial serialSign = sysDao.getAppSignformDao().getAppSerial(dept.getHospAreaCode().substring(0, 4), "sign");
//					if (serialSign != null) {
//						String sinum = sysDao.getAppSignformDao().getNum(serialSign.getSerialNo());
//						serialSign.setSerialNo(sinum);
//						sysDao.getServiceDo().modify(serialSign);
//						form.setSignNum(sinum);//签约编码
//					}
//				}
//				//
//				form.setSignPatientId(uservo.getId());
//				form.setSignDate(Calendar.getInstance());
//				form.setSignPayState("0");//0：未缴费
//				form.setSignType("1");//1家庭签约
//				form.setSignTeamId(teamvo.getId());
//				form.setSignTeamName(teamvo.getTeamName());
//				form.setSignWay("2");//医生代签
//				form.setSignState("2");//已签约
//				form.setSignFromDate(Calendar.getInstance());
//				form.setSignPatientGender(uservo.getPatientGender());
//				form.setSignPatientAge(Integer.parseInt(uservo.getPatientAge()));
//				Calendar end=Calendar.getInstance();
//				end.add(Calendar.YEAR,1);
//				end.add(Calendar.DATE,-1);
//				form.setSignToDate(end);
//				form.setSignDrId(drId);
//				form.setSignContractState("0");//1是 0否
//				form.setSignGreenState("0");//1是 0否
//				form.setSignYellowState("0");//1是 0否
//				form.setSignRedState("0");//1是 0否
//				sysDao.getServiceDo().add(form);
//			}

//			AesEncrypt aesEncrypt = new AesEncrypt("jtyssf123");
//			String msg="{\"org_code\":\"350102000000\"}";
//			String dept=aesEncrypt.encrypt(msg);
//			String m="{\"message\":\""+dept+"\"}";
//			Dbcon httpc=new Dbcon();
//			String s=httpc.dbcon("http://10.10.10.57:8080/sqyljk/rest/jwjtysqy/hqgtlrstj",m);
//			String val=aesEncrypt.decrypt(s.replace("\"",""));
//			JSONObject jsonAll = JSONObject.fromObject(val);
//			System.out.println(val);
//			if((Boolean) jsonAll.get("success")){
//				JSONObject jsons = JSONObject.fromObject(jsonAll.get("entity"));
//				System.out.println(jsons.get("gxyrs0"));
//				System.out.println(jsons.get("tnbrs0"));
//				System.out.println(jsons.get("lnrrs0"));
//			}
//			List<CdAddress> ls = sysDao.getCdAddressDao().findByState(CommonEnable.QIYONG.getValue());
//			if(ls != null && ls.size()>0){
//				for(CdAddress v : ls){
//					HttpClient client = HttpClients.createDefault();
//					List<NameValuePair> params = new ArrayList<NameValuePair>();
//					params.add(new BasicNameValuePair("year","2017"));
//					params.add(new BasicNameValuePair("month","05"));
//					params.add(new BasicNameValuePair("addr",v.getId()));
//					String urlLogin = "http://10.120.0.82:8080/jwbbrktj/api/count";
//					String str1 = HtmlUtils.getInstance().executeResponse(client, "get", urlLogin, params,"utf-8");
////				System.out.println(str1);
//					JSONObject jsonAll = JSONObject.fromObject(str1);
//					if(jsonAll.get("msg")!= null){
//						String str2 = jsonAll.get("msg").toString();
////					MsgVoRks p = JsonUtil.fromJson(str2,MsgVoRks.class);
////						int i = Integer.parseInt(p.getResident())+Integer.parseInt(p.getIn())-Integer.parseInt(p.getOut());
////					int i = Integer.parseInt(p.getResident());
//						AppRksEntity vo = JsonUtil.fromJson(str2, AppRksEntity.class);
//						int zrs = Integer.parseInt(vo.getReg())+Integer.parseInt(vo.getResident());
//						double mbs = zrs*0.3;
//						long mbss = Math.round(mbs);
//						System.out.println(v.getAreaSname()+":总人数:==="+zrs);
//						System.out.println(v.getAreaSname()+":目标数==="+mbss);
//						sysDao.getServiceDo().modify(v);
//					}
//				}
//			}
//
//			sysDao.getAppPatientUserDao().addTest();
//			sysDao.getAppPatientUserDao().addTestDevice();
			//sysDao.getAppPatientUserDao().addTestData();
//			List<AppPatientUser> ls = sysDao.getAppPatientUserDao().findAll();
//			if(ls != null && ls.size() >0){
//				for(AppPatientUser p : ls){
//					String idNo = p.getPatientIdno().toUpperCase();
//					p.setPatientIdno(idNo);
//					p.setPatientPwd(Md5Util.MD5(idNo.substring(idNo.length()-6,idNo.length())));
//					sysDao.getServiceDo().modify(p);
//				}
//			}
//			List<AppPatientUser> ls = sysDao.getAppPatientUserDao().findAll();
//			if(ls != null && ls.size() >0){
//				for(AppPatientUser user : ls){
//					String result = PinyinUtil.getQuanPin(user.getPatientName()).toString().toLowerCase();
//					user.setPatientBopomofo(result);
//					sysDao.getServiceDo().modify(user);
//
//				}
//			}
//			List<AppDrUser> ls = sysDao.getAppDrUserDao().findAll(null);
//			if(ls != null && ls.size()>0){
//				for(AppDrUser user : ls){
//					if(StringUtils.isNotBlank(user.getDrTel())){
//						if(user.getDrTel().length() >=11){
//							user.setDrPwd(Md5Util.MD5(user.getDrTel().substring(user.getDrTel().length()-6,user.getDrTel().length())));
//							sysDao.getServiceDo().modify(user);
//						}
//					}
////					String result = PinyinUtil.getQuanPin(user.getDrName()).toString().toLowerCase();
////					user.setDrBopomofo(result);
////					sysDao.getServiceDo().modify(user);
//				}
//			}
//			List<AppSignForm> ls = sysDao.getAppSignformDao().findAll();
//			if(ls != null && ls.size() >0){
//				for(AppSignForm v : ls){
//					List<AppTeamMember> menber = sysDao.getAppTeamMemberDao().findTeamId(v.getSignTeamId());
//					if(menber != null && menber.size()>0){
//						for(AppTeamMember p : menber){
//							hxloginutil.addFridenSignl(v.getSignPatientId(),p.getMemDrId());
//						}
//					}
//				}
//			}
//			SericuryUtil p = new SericuryUtil();
//			List<AppPatientUser> lsV = sysDao.getAppPatientUserDao().findAll();
//			if(lsV != null && lsV.size() >0){
//				for(AppPatientUser user : lsV){
//					String result = "";
//					CloseableHttpClient client = HttpClients.createDefault();
//					try{
//						JSONObject jsonParam = new JSONObject();
////            String urlLogin = PropertiesUtil.getConfValueUrl("idCardHealthUrl")+"/yiKaTongMaintain/sscard/getCardInfo.htm";
//						String urlLogin = "http://192.168.52.14:10007/sscard/getCardInfo.htm";
//						jsonParam.put("idCard",user.getPatientIdno());
//						if(StringUtils.isNotBlank(user.getPatientName()))
//							jsonParam.put("userName",user.getPatientName());
//						String str1 = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam,"utf-8");
//						if(StringUtils.isNotBlank(str1)){
//							JSONObject jsonAll = JSONObject.fromObject(str1);
//							if(jsonAll.get("entity") != null){
//								List<IdCardEntity> ls = JsonUtil.fromJson(jsonAll.get("entity").toString(), new TypeToken<List<IdCardEntity>>() {}.getType());
//								if(ls != null && ls.size() >0){
//									String temp  = "";
//									for(int i =0;i<ls.size();i++){
//										IdCardEntity v = ls.get(i);
//										if(StringUtils.isBlank(result)){
//											result = v.getSsCard();
//											temp = v.getSsCard();
//										}else {
//											if(temp.equals(v.getSsCard())){
//
//											}else{
//												result += ","+v.getSsCard();
//											}
//										}
//									}
//								}
//							}
//						}
//						if(StringUtils.isNotBlank(user.getId())){
//							AppPatientUser v =  (AppPatientUser)sysDao.getServiceDo().find(AppPatientUser.class,user.getId());
//							if(v != null){
//								String[] sz = result.split(",");
//								if(sz.length > 1){
//									v.setPatientIdCardTemp(result);
//								}else{
//									v.setPatientCard(result);
//								}
//								sysDao.getServiceDo().modify(v);
//							}
//						}
//					}catch (Exception e){
//						e.printStackTrace();
//					}finally {
//						try {
//							client.close();
//						}catch (Exception e){
//							e.printStackTrace();
//						}
//					}
//				}
//			}
//
//			List<AppDrUser> ls = sysDao.getAppDrUserDao().findByEase();
//			if(ls != null && ls.size()>0){
//				for(AppDrUser user : ls){
//					HxLoginUtil.registeredEasemob(user.getId());
//					user.setDrEaseState("1");
//					sysDao.getServiceDo().modify(user);
//
//				}
//			}

//			List<AppPatientUser> ls = sysDao.getAppPatientUserDao().findAll();
//			if(ls != null && ls.size() >0){
//				for(AppPatientUser user : ls){
//					String result = PinyinUtil.getPinYinHeadChar(user.getPatientName());
//					user.setPatientBopomofo(result);
//					sysDao.getServiceDo().modify(user);
//
//				}
//			}
//			List<AppDrUser> ls = sysDao.getAppDrUserDao().findAll(null);
//			if(ls != null && ls.size()>0){
//				for(AppDrUser user : ls){
//					String result = PinyinUtil.getPinYinHeadChar(user.getDrName());
//					user.setDrBopomofo(result);
//					sysDao.getServiceDo().modify(user);
//				}
//			}
//			sysDao.getAppCityAreaDao().getCityAreaPeopleInit();

			//出生年龄男女
//			List<AppPatientUser> ls  = sysDao.getAppPatientUserDao().findAll();
//			if( ls != null && ls.size() >0){
//				for(AppPatientUser user : ls){
//					Map<String,Object> idNos;
//					if(org.apache.commons.lang3.StringUtils.isNotBlank(user.getPatientIdno())){
//						System.out.println(user.getPatientName()+"==="+user.getPatientIdno());
//						if(user.getPatientIdno().trim().length() == 18){
//							idNos = CardUtil.getCarInfo(user.getPatientIdno().trim());
//						}else{
//							idNos = CardUtil.getCarInfo15W(user.getPatientIdno().trim());
//						}
//
//						user.setPatientAge(idNos.get("age").toString());
//						user.setPatientGender(idNos.get("sex").toString());
//						user.setPatientBirthday(ExtendDate.getCalendar(idNos.get("birthday").toString()));
//						sysDao.getSecurityCardAsyncBean().getSecurityCard(user.getPatientIdno(),user.getPatientName(),user.getId());
//						sysDao.getServiceDo().modify(user);
//					}
//
//				}
//			}
			//环信患者用户
//			List<AppPatientUser> ls = sysDao.getAppPatientUserDao().findAll();
//			if(ls != null && ls.size() >0){
//				for(AppPatientUser user : ls){
//					String result = PinyinUtil.getQuanPin(user.getPatientName()).toString().toLowerCase();
//					user.setPatientBopomofo(result);
//					HxLoginUtil.registeredEasemob(user.getId());
//					user.setPatientEaseState("1");
//					sysDao.getServiceDo().modify(user);
//
//				}
//			}
//			//环信医生用户
//			List<AppDrUser> ls = sysDao.getAppDrUserDao().findAll(null);
//			Map map = new HashMap();
//			String sql = "SELECT * FROM APP_DR_USER where DR_EASE_STATE IS NULL";
//			List<AppDrUser> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppDrUser.class);
//			if(ls != null && ls.size()>0){
//				for(AppDrUser user : ls){
//					String result = PinyinUtil.getPinYinHeadChar(user.getDrName());
//					user.setDrBopomofo(result);
//					HxLoginUtil.registeredEasemob(user.getId());
//					user.setDrEaseState("1");
//					sysDao.getServiceDo().modify(user);
//				}
//			}
			//加好友
//			List<AppSignForm> ls = sysDao.getAppSignformDao().findAll();
//			if(ls != null && ls.size() >0){
//				for(AppSignForm v : ls){
//					List<AppTeamMember> menber = sysDao.getAppTeamMemberDao().findTeamId(v.getSignTeamId());
//					if(menber != null && menber.size()>0){
//						for(AppTeamMember p : menber){
//							HxLoginUtil.addFridenSignl(v.getSignPatientId(),p.getMemDrId());
//						}
//					}
//				}
//			}

			 //服务人群初始化
//			String sql="SELECT * from APP_SIGN_FORM a where a.SIGN_PERS_GROUP is null";
//			List<AppSignForm> ls =sysDao.getServiceDo().findSqlMap(sql,new HashedMap(),AppSignForm.class);
//			List<AppSignForm> ls = sysDao.getAppSignformDao().findAll();
//			for(AppSignForm l:ls){
//				if(StringUtils.isNotBlank(l.getSignPersGroup())) {
//					System.out.println(l.getSignPersGroup());
//					List<AppLabelManage> manage = sysDao.getServiceDo().loadByPk(AppLabelManage.class, "labelValue", l.getSignPersGroup());
//						if (manage != null && !manage.isEmpty()) {
//							String gsql = "SELECT * from APP_LABEL_GROUP a where a.LABEL_SIGN_ID=:LABEL_SIGN_ID and a.LABEL_VALUE=:LABEL_VALUE";
//							Map gmap = new HashedMap();
//							gmap.put("LABEL_SIGN_ID", manage.get(0).getId());
//							gmap.put("LABEL_VALUE", manage.get(0).getLabelValue());
//							List<AppLabelGroup> lgr = sysDao.getServiceDo().findSqlMap(gsql,gmap,AppLabelGroup.class);
//							if(lgr!=null && !lgr.isEmpty()){
//
//							}else {
//								AppLabelGroup alg = new AppLabelGroup();
//								alg.setLabelId(manage.get(0).getId());
//								alg.setLabelSignId(l.getId());
//								alg.setLabelTeamId(l.getSignTeamId());
//								alg.setLabelTitle(manage.get(0).getLabelTitle());
//								alg.setLabelValue(manage.get(0).getLabelValue());
//								alg.setLabelType(manage.get(0).getLabelType());
//								sysDao.getServiceDo().add(alg);
//							}
//
//					}
//				}
//			}

//			String sql="SELECT * from APP_SIGN_FORM a where a.SIGN_PERS_GROUP is null";
//			List<AppSignForm> ls =sysDao.getServiceDo().findSqlMap(sql,new HashedMap(),AppSignForm.class);
////			List<AppSignForm> ls = sysDao.getAppSignformDao().findAll();
//			for(AppSignForm l:ls){
////				if(StringUtils.isNotBlank(l.getSignPersGroup())){
////					System.out.println(l.getSignPersGroup());
//				//List<AppLabelManage> manage = sysDao.getServiceDo().loadByPk(AppLabelManage.class, "labelValue", l.getSignPersGroup());
//				AppLabelManage manage1= (AppLabelManage) sysDao.getServiceDo().find(AppLabelManage.class,"cdd660dd-6fa5-11e7-9cfa-d4bed9dda673");
//				if (manage1 != null ) {
//					AppLabelGroup alg = new AppLabelGroup();
//					alg.setLabelId(manage1.getId());
//					alg.setLabelSignId(l.getId());
//					alg.setLabelTeamId(l.getSignTeamId());
//					alg.setLabelTitle(manage1.getLabelTitle());
//					alg.setLabelValue(manage1.getLabelValue());
//					alg.setLabelType(manage1.getLabelType());
//					sysDao.getServiceDo().add(alg);
//				}
////				}
//			}

			//删除好友 患者
//			List<AppPatientUser> ls = sysDao.getAppPatientUserDao().findAll();
//			for(AppPatientUser l:ls){
//				HxLoginUtil.delEasemob(l.getId());
//			}
			//删除好友 医生
//			List<AppDrUser> ls = sysDao.getAppDrUserDao().findAll(null);
//			for(AppDrUser l:ls){
//				String id=null;
//				if(l.getId().indexOf("sm_")==0){
//					id=l.getId().substring(3);
//				}else {
//					id=l.getId();
//				}
//				HxLoginUtil.delEasemob(id);
//			}


			//加好友
//			List<AppSignForm> ls = sysDao.getAppSignformDao().findAll();
//			if(ls != null && ls.size() >0){
//				for(AppSignForm v : ls){
//					List<AppTeamMember> menber = sysDao.getAppTeamMemberDao().findTeamId(v.getSignTeamId());
//					if(menber != null && menber.size()>0){
//						for(AppTeamMember p : menber){
//							HxLoginUtil.addFridenSignl(v.getSignPatientId(),p.getMemDrId());
//						}
//					}
//				}
//			}

//			WebTeam team=new WebTeam();
//			team.setId("zzzz");
//			sysDao.getServiceDo().add(team);
//			sysDao.getUniformPayMentBean().payCreateCashier("dfd96144-1426-4d70-ad51-c3a39c816443","1");
//			List<AppFamilyInfo> result = sysDao.getSecurityCardAsyncBean().getFetchFamily("350212199101033109",null);
//			if(result != null && result.size() >0){
//				for(AppFamilyInfo p : result){
//					System.out.println(p.getName()+"====="+p.getTel()+"===="+p.getRelation());
//				}
//			}
//			System.out.println(result);
			//协议书
//			List<AppHospDept> ls = sysDao.getAppHospDeptDao().findAll();
//			String sql = "SELECT * FROM APP_TEST_AGREEMENT";
//			List<Map> lsMap = sysDao.getServiceDo().findSql(sql);
//			if(lsMap != null){
//				Map map = lsMap.get(0);
//				String title = map.get("MENT_TITLE").toString();
//				String content = map.get("MENT_CONTENT").toString();
//				String type = map.get("MENT_TYPE").toString();
//				String state = map.get("MENT_STATE").toString();
//				String useType = map.get("MENT_USE_TYPE").toString();
//				if(ls != null && ls.size() >0){
//					for(AppHospDept dept : ls){
//						AppAgreement p = new AppAgreement();
//						p.setMentTitle(title);
//						p.setMentHospId(dept.getId());
//						p.setMentType(type);
//						p.setMentState(state);
//						p.setMentContent(content);
//						if(StringUtils.isNotBlank(dept.getHospAreaCode()))
//							p.setMentCityId(AreaUtils.getAreaCode(dept.getHospAreaCode(),"2"));
//						p.setMentUseType(useType);
//						sysDao.getServiceDo().add(p);
//					}
//				}
//
//			}
//			Map map = new HashMap();
//			String sql = "SELECT * FROM APP_PATIENT_USER where PATIENT_TEL IS NOT NULL ";
////			String sql = "SELECT * FROM APP_PATIENT_USER where PATIENT_CARD='D77755457'";
//			List<AppPatientUser> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppPatientUser.class);
//			if(ls != null && ls.size()>0){
//				for(AppPatientUser u : ls){
//					if(u.getPatientTel().length() == 11){
//						u.setPatientPwd(Md5Util.MD5(u.getPatientTel().substring(u.getPatientTel().length()-6,u.getPatientTel().length())));
//						sysDao.getServiceDo().modify(u);
//					}
////					sysDao.getSecurityCardAsyncBean().getSecurityCard(u.getPatientIdno(),null,u.getId());
//				}
//			}
//			String result = sysDao.getSecurityCardAsyncBean().getSecurityCard("350302199106211624",null,null);
//			System.out.println(result);
//			sysDao.getAppNoticeDao().addNotices("测试","内容厦门" ,NoticesType.JKZX.getValue(),"350211000000","1","2",null);
//			AppSignForm l=null;
//			String url=null;
//			String rr=sysDao.getWebSignFormDao().signUpWeb(l,url);
//			System.out.println(rr);
//			WebTeam wteam= (WebTeam) sysDao.getServiceDo().find(WebTeam.class,"0a648384-a8bc-4ed2-961d-bef18daefbbe");
//			System.out.println(wteam.getTeamName());

//			String result = sysDao.getSecurityCardAsyncBean().getSecurityCardNotAsync("350119199003124000",null);
//			System.out.println(result);

//			Map<String,Object> map = new HashMap<String,Object>();
//			String sql = "SELECT * FROM APP_TEAM where TEAM_EASE_GROUP_ID IS NULL AND TEAM_CREATE_TIME IS NOT NULL ";
//			List<AppTeam> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppTeam.class);
//			if(ls != null && ls.size() >0){
//				for(AppTeam team : ls){
//					sysDao.getSecurityCardAsyncBean().addGroupTemp(team);
//				}
//			}

//			Map<String,Object> map = new HashMap<String,Object>();
//			String sql = "SELECT * FROM APP_DR_USER WHERE DR_EASE_STATE IS NULL ";
//			List<AppDrUser> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppDrUser.class);=-			if(ls != null && ls.size()>0){
//				for(AppDrUser user : ls){
//					String reult = HxLoginUtil.registeredEasemobTemp(user.getId());
//					if(reult != null){
//						String result = PinyinUtil.getPinYinHeadChar(user.getDrName());
//						user.setDrBopomofo(result);
//						user.setDrEaseState("1");
//						sysDao.getServiceDo().modify(user);
//					}
//				}
//			}

//			Map<String,Object> map = new HashMap<String,Object>();
//			String sql = "SELECT * FROM APP_PATIENT_USER where PATIENT_EASE_STATE IS NULL";
//			List<AppPatientUser> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppPatientUser.class);
//			if(ls != null && ls.size() >0){
//				for(AppPatientUser user : ls){
//					if(StringUtils.isNotBlank(user.getPatientName())){
//						String result = PinyinUtil.getQuanPin(user.getPatientName()).toString().toLowerCase();
//						user.setPatientBopomofo(result);
//					}
//					HxLoginUtil.registeredEasemob(user.getId());
//					user.setPatientEaseState("1");
//					sysDao.getServiceDo().modify(user);
//					AppDrPatientSignEntity from = sysDao.getAppSignformDao().findPatient(user.getId());
//					if(from != null){
//						List<AppTeamMember> lsTeam = sysDao.getAppTeamMemberDao().findTeamId(from.getSignTeamId());
//						if(lsTeam != null && lsTeam.size() >0){
//							for(AppTeamMember v : lsTeam){
//								sysDao.getSecurityCardAsyncBean().addFridenSignl(user.getId(),v.getMemDrId());
//							}
//						}
//					}
//			Map<String,Object> map = new HashMap<String,Object>();
//			String sql = "SELECT * FROM APP_PATIENT_USER where PATIENT_EASE_STATE IS NULL";
//			List<AppPatientUser> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppPatientUser.class);
//			if(ls != null && ls.size() >0){
//				for(AppPatientUser user : ls){
//					if(StringUtils.isNotBlank(user.getPatientName())){
//						String result = PinyinUtil.getQuanPin(user.getPatientName()).toString().toLowerCase();
//						user.setPatientBopomofo(result);
//					}
//					HxLoginUtil.registeredEasemob(user.getId());
//					user.setPatientEaseState("1");
//					sysDao.getServiceDo().modify(user);
//					AppDrPatientSignEntity from = sysDao.getAppSignformDao().findPatient(user.getId());
//					if(from != null){
//						List<AppTeamMember> lsTeam = sysDao.getAppTeamMemberDao().findTeamId(from.getSignTeamId());
//						if(lsTeam != null && lsTeam.size() >0){
//							for(AppTeamMember v : lsTeam){
//								sysDao.getSecurityCardAsyncBean().addFridenSignl(user.getId(),v.getMemDrId());
//							}
//						}
//					}
//
//				}
//			}

//				}
//			}

//			Map<String,Object> map = new HashMap<String,Object>();
//			String sql = "SELECT * FROM APP_TEAM_MEMBER where MEM_DR_ID in('ab9567d7-5ff0-4cec-a203-e4b9554d65a9','b9ba0a70-044e-42b2-bc13-78555b2cf372')";
//			List<AppTeamMember> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppTeamMember.class);
//			if(ls != null && ls.size() >0){
//			for(AppTeamMember user : ls){
////
//					sysDao.getSecurityCardAsyncBean().addGroupMembers("23542370467842",user.getMemDrId());
//				}
//			}
//			List<IdCardEntity> ls = sysDao.getSecurityCardAsyncBean().getSecurityCardListAsync("350681199011106225",null);
//			for(IdCardEntity p : ls){
//				System.out.println(p.getSsCard()+"=="+p.getStatus());
//			}

//			List<AppFamilyInfo> ls = sysDao.getSecurityCardAsyncBean().getFetchFamily("350426198910075530","林景洲");
//			for(AppFamilyInfo info : ls){
//				System.out.println(info.getName()+"======="+info.getRelation());
//			}
//			String sql = "SELECT c.TEL,c.CID FROM APP_TEST_GLY c where LENGTH(c.TEL) = 11";
//			List<Map> ls = sysDao.getServiceDo().findSqlMap(sql,map);
//			if(ls != null && ls.size() >0){
//				for(Map<String,String> s : ls){
//					if(s.get("CID") != null){
//						AppPatientUser user = sysDao.getAppPatientUserDao().findPatientIdNo(s.get("CID"));
//						if(user != null){
//							user.setPatientTel(s.get("TEL"));
//							user.setPatientPwd(Md5Util.MD5(s.get("TEL").substring(s.get("TEL").length()-6,s.get("TEL").length())));
//							sysDao.getServiceDo().modify(user);
//						}
//					}
//				}
//			}

//			Map<String,Object> map = new HashMap<String,Object>();
//			String sql = "SELECT * FROM APP_PATIENT_USER t where t.PATIENT_UP_HPIS ='1' AND t.PATIENT_BIRTHDAY IS NULL ";
//			List<AppPatientUser> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppPatientUser.class);
//			if(ls != null && ls.size() >0){
//				for(AppPatientUser p : ls){
//					if(StringUtils.isNotBlank(p.getPatientIdno())){
//						Map<String,Object> idNos;
//						if(p.getPatientIdno().trim().length() == 18){
//							idNos = CardUtil.getCarInfo(p.getPatientIdno().trim());
//						}else{
//							idNos = CardUtil.getCarInfo15W(p.getPatientIdno().trim());
//						}
//						if(idNos.get("age") != null){
//							p.setPatientAge(idNos.get("age").toString());
//						}
//						if(idNos.get("age") != null) {
//							p.setPatientGender(idNos.get("sex").toString());
//						}
//						if(idNos.get("birthday") != null) {
//							p.setPatientBirthday(ExtendDate.getCalendar(idNos.get("birthday").toString()));
//						}
//						sysDao.getServiceDo().modify(p);
//					}
//				}
//			}
//			Map<String,Object> map = new HashMap<String,Object>();
//			String sql = "SELECT * FROM CP_CITY_AREA where LEVEL_NAME <= '3' and CITY_AREA_ID like '43%'";
//			List<CdAddress> ls = sysDao.getServiceDo().findSqlMap(sql,map,CdAddress.class);
//			if(ls != null && ls.size() >0){
//				for(CdAddress v : ls){
//					if(v.getAreaSname().length() <= 3){
//						v.setAreaAlias("健康"+v.getAreaSname().substring(0,2));
//					}else {
//						v.setAreaAlias("健康"+v.getAreaSname());
//					}
//					sysDao.getServiceDo().modify(v);
//				}
//			}
//			Map<String,Object> map = new HashMap<String,Object>();
//			String sql = "SELECT * FROM APP_SIGN_FORM where UP_HPIS='2' ";
////			List<AppTeam> ls = this
//			List<AppSignForm> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppSignForm.class);
//			int i =0;
//			int j =0;
//			if(ls != null && ls.size()>0){
//				for(AppSignForm p : ls){
//					System.out.println(i);
//					map.put("id",p.getSignDrId());
//					sql = "SELECT * FROM HFS_DR where id = :id ";
//					List list = sysDao.getServiceDo().findSqlMap(sql,map);
//					if(list != null && list.size() >0){
//						Map<String,Object> mapTeam = (Map<String, Object>) list.get(0);
//						if(mapTeam.get("USER_ID") != null){
//							String drId = mapTeam.get("USER_ID").toString();
//							p.setSignDrId(drId);
//							sysDao.getServiceDo().modify(p);
//							System.out.println(i);
//							i++;
//						}
//					}
//					j++;
//				}
//			}

//			String sql = "select * from db_b_area";db_b_area

//			sysDao.getAppCityAreaDao().getCityAreaInit();
//			List<AppFamilyInfo> ls = sysDao.getSecurityCardAsyncBean().getFetchFamily("350427198908200028","乐张欣");
//			if(ls != null && ls.size()>0){
//				for(AppFamilyInfo info : ls){
//					System.out.println(info.getName()+"=========="+info.getRelation());
//				}
//			}d
//			sysDao.getSecurityCardAsyncBean().appInitialiseTeamTotal("1","2016-01-01");


//			Map<String,Object> map = new HashMap<String,Object>();
//			String sql = "SELECT * FROM APP_DR_USER where DR_EASE_STATE IS NULL AND DR_HOSP_ID IS NOT NULL";
//			List<AppDrUser> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppDrUser.class);
//			if(ls != null && ls.size()>0){
//				for(AppDrUser user : ls){
//					String result = PinyinUtil.getPinYinHeadChar(user.getDrName());
//					user.setDrBopomofo(result);
//					user.setDrPwd(Md5Util.MD5(user.getDrTel().substring(user.getDrTel().length()-6,user.getDrTel().length())));
//					HxLoginUtil.registeredEasemob(user.getId());
//					user.setDrEaseState("1");
//					sysDao.getServiceDo().modify(user);
//				}
//			}

//			String sql = "select * FROM APP_TEAM  where TEAM_EASE_GROUP_ID IS NULL ";
//			Map<String,Object> map = new HashMap<String,Object>();
//			List<AppTeam> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppTeam.class);
//			if(ls != null && ls.size()>0){
//				for(AppTeam team : ls){
//					sysDao.getSecurityCardAsyncBean().addGroupTemp(team);
//				}
//			}
//			String sql = "select * FROM APP_TEAM  where TEAM_EASE_ROOM_ID IS NULL ";
//			Map<String,Object> map = new HashMap<String,Object>();
//			List<AppTeam> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppTeam.class);
//			if(ls != null && ls.size()>0){
//				for(AppTeam team : ls){
//					sysDao.getSecurityCardAsyncBean().addRoomTeamp(team);
//				}
//			}
			//讨论组
//			Map<String,Object> map = new HashMap<String,Object>();
//			String sql = "SELECT t.* FROM APP_SIGN_FORM c INNER JOIN APP_PATIENT_USER t ON c.SIGN_PATIENT_ID = t.ID where t.PATIENT_EASE_STATE IS NOT NULL AND t.PATIENT_UP_HPIS ='0' ";
//			List<AppPatientUser> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppPatientUser.class);
//			if(ls != null && ls.size() >0){
//				SericuryUtil p = new SericuryUtil();
//				for(AppPatientUser user : ls){
//					AppSignForm form = sysDao.getAppSignformDao().findSignFormByUserState(user.getId());
//					if(form != null){
//						AppTeam team = (AppTeam) sysDao.getServiceDo().find(AppTeam.class,form.getSignTeamId());
//						if(team != null){
//							sysDao.getSecurityCardAsyncBean().addRoomMembersTemp(team.getTeamEaseRoomId(),p.encrypt(user.getId()));
//						}
//					}
//				}
//			}
//			//删除群
//			EasemobChatGroup group = new EasemobChatGroup();
//			Object result = group.getChatGroups((long) 3000,null);
//			JSONObject jsonAll = JSONObject.fromObject(result.toString());
//			String data = jsonAll.get("data").toString();
//			List<AppGroup> ls = JsonUtil.fromJson(data, new TypeToken<List<AppGroup>>() {}.getType());
//			if(ls != null && ls.size() >0){
//				for(AppGroup p : ls){
//					group.deleteChatGroup(p.getGroupid());
//				}
//			}
//			System.out.println(result);
//			AppAgreement v = (AppAgreement) sysDao.getServiceDo().find(AppAgreement.class,"00029bf7-5d0c-4165-9d81-8161c6e45b77");
//			String sql = " SELECT * FROM APP_HOSP_DEPT t ";
//			Map<String,Object> map = new HashMap<String,Object>();
//			List<AppHospDept> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppHospDept.class);
//			if(ls != null && ls.size()>0){
//				for(AppHospDept p : ls){
//					AppAgreement agreement = new AppAgreement();
//					agreement.setMentUseType("2");
//					agreement.setMentCityId(AreaUtils.getAreaCode(p.getHospAreaCode(),"2"));
//					agreement.setMentContent(v.getMentContent());
//					agreement.setMentState("1");
//					agreement.setMentType("1");
//					agreement.setMentHospId(p.getId());
//					agreement.setMentTitle(v.getMentTitle());
//					sysDao.getServiceDo().add(agreement);
//				}
//			}

//			SysDao sysDao = (SysDao) SpringHelper.getBean("sysDao");
//			Map<String, Object> map = new HashedMap();
//			String url = PropertiesUtil.getConfValue("weburl");
//			String sql = "SELECT * from APP_SIGN_FORM a where a.SIGN_STATE='2' and a.UP_HPIS is null";//查询未上传数据
//			List<AppSignForm> ls = sysDao.getServiceDo().findSqlMap(sql, map, AppSignForm.class);
//			if (ls != null && !ls.isEmpty()) {
//				for (AppSignForm l : ls) {
//					String rt = sysDao.getWebSignFormDao().signUpWeb(l, url);
//					Map m = (Map) JacksonUtils.getObject(rt, Map.class);
//					if (m.get("errCode").toString().equals("900")) {
//						System.out.println("单号" + l.getId() + "上传失败:" + rt);
//					} else if (m.get("errCode").toString().equals("800")) {
//						l.setUpHpis("1");//上传web成功
//						sysDao.getServiceDo().modify(l);
//					}
//				}
//			}
//			HomeServiceItemsQvo qvo = new HomeServiceItemsQvo();
//			qvo.setIdno("350102198906253632");
//			qvo.setUrlType("7");
//			HomeServiceItemsEntity entity = sysDao.getSecurityCardAsyncBean().findHomeServiceItems(qvo);
//			System.out.println(JsonUtil.toJson(entity));
//			JmjktjQvo jmjktjQvo = new JmjktjQvo();
//			jmjktjQvo.setDf_id(entity.getJmdah());
//			jmjktjQvo.setUrlType("7");
//			List<JmjktjDTO> ls = sysDao.getSecurityCardAsyncBean().findJmjktjList(jmjktjQvo);
//			System.out.println(JsonUtil.toJson(ls));

//			FollowUpQVo qvo = new FollowUpQVo();
//			qvo.setDf_id("35010200800500707");
//			qvo.setMxjbbz("1");
//			qvo.setUrlType("7");
//       String sql = "SELECT * FROM APP_SIGN_CONTROL WHERE SIGN_PATIENT_ID in (SELECT c.SIGN_PATIENT_ID from (SELECT SIGN_PATIENT_ID,count(1) FROM APP_SIGN_CONTROL GROUP BY SIGN_PATIENT_ID HAVING count(1) >1 ORDER BY count(1) DESC ) c) ORDER BY SIGN_PATIENT_ID";
//       List ls = sysDao.getServiceDo().findSql(sql);
//       String result = null;
//       String re = "";
//       if(ls != null && ls.size() >0){
//       	  for(int i=0;i<ls.size();i++){
//       	  	Map<String,Object> map = (Map<String, Object>) ls.get(i);
//       	  	if(StringUtils.isNotBlank(result)){
//					if(result.equals(map.get("SIGN_PATIENT_ID").toString())){
////						sysDao.getServiceDo().delete(AppSignControl.class,map.get("ID").toString());
//						re += "'"+map.get("SIGN_ID").toString()+"',";
//					}else{
//						result = map.get("SIGN_PATIENT_ID").toString();
//					}
//			}else{
//       	  		result = map.get("SIGN_PATIENT_ID").toString();
//			}
//		  }
//		   System.out.println(re);
//	   }
//			Map<String,Object> map = new HashMap<String,Object>();
//			String sql = "SELECT * from APP_DR_USER where id in(" +
//					"'466e2d84-f5af-46dc-bb2f-775fd6a586d8',\n" +
//					"'acd821d5-b18a-46fd-a8ed-a5552e6bf64c',\n" +
//					"'f4539ae0-2f97-4900-9b04-38d10037148c')";
//			List<AppDrUser> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppDrUser.class);
//			if(ls != null && ls.size()>0){
//				for(AppDrUser user : ls){
//					String result = PinyinUtil.getPinYinHeadChar(user.getDrName());
//					user.setDrBopomofo(result);
//					user.setDrPwd(Md5Util.MD5(user.getDrTel().substring(user.getDrTel().length()-6,user.getDrTel().length())));
//					HxLoginUtil.registeredEasemob(user.getId());
//					user.setDrEaseState("1");
//					sysDao.getServiceDo().modify(user);
//				}
//			}
//			Map<String,Object> map = new HashMap<String,Object>();
//			String sql = "SELECT * FROM APP_TEAM WHERE TEAM_EASE_ROOM_ID is NULL OR TEAM_EASE_GROUP_ID IS NUll ";
//			List<AppTeam> lsTeam = sysDao.getServiceDo().findSqlMap(sql,map,AppTeam.class);
//			if(lsTeam != null && lsTeam.size()>0){
//				for(AppTeam team : lsTeam){
//					if(team != null && StringUtils.isBlank(team.getTeamEaseGroupId())){
//						sysDao.getSecurityCardAsyncBean().addGroupTemp(team);
//					}
//					if(team != null && StringUtils.isBlank(team.getTeamEaseRoomId())){
//						sysDao.getSecurityCardAsyncBean().addRoomTeamp(team);
//					}
//				}
//			}
//			Map<String,Object> map = new HashMap<String,Object>();
//			String sql = "SELECT * FROM CP_CITY_AREA where LEVEL_NAME ='1'";
//			List<CdAddress> ls = sysDao.getServiceDo().findSqlMap(sql,map,CdAddress.class);
//			if(ls != null && ls.size() >0){
//				for(CdAddress p : ls){
//					AppServeRole role = new AppServeRole();
//					role.setServeRoleAreaCode(p.getId());
//					role.setServeName(p.getAreaSname());
//					role.setServeState("1");
//					sysDao.getServiceDo().add(role);
//				}
//			}


//			Map<String,Object> map = new HashMap<String,Object>();
//			String sql = "SELECT * FROM `APP_SERVE_ROLE_SON` where SON_SERVE_ROLE_ID ='5c8d2f2d-2f20-46a9-899e-715994ca1959'";
//			List<AppServeRoleSon> lsSon = sysDao.getServiceDo().findSqlMap(sql,map,AppServeRoleSon.class);
////			sql  = "SELECT * FROM `APP_SERVE_ROLE` WHERE 1=1 AND SERVE_ROLE_AREA_CODE <> '350200000000'  AND SERVE_ROLE_AREA_CODE <> '350400000000'  AND SERVE_ROLE_AREA_CODE <> '350100000000'";
//			sql = " SELECT * FROM `APP_SERVE_ROLE` WHERE 1=1 AND (SERVE_ROLE_AREA_CODE = '140000000000'  or SERVE_ROLE_AREA_CODE = '350000000000'  or SERVE_ROLE_AREA_CODE = '430000000000')";
//			List<AppServeRole> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppServeRole.class);
//			if(ls != null && ls.size() >0){
//				for(AppServeRole p : ls){
//					if(lsSon != null && lsSon.size()>0){
//						for(AppServeRoleSon v : lsSon){
//							AppServeRoleSon vo = new AppServeRoleSon();
//							vo.setSonServeSetSpaceType(v.getSonServeSetSpaceType());
//							vo.setSonServeRoleId(p.getId());
//							vo.setSonServeId(v.getSonServeId());
//							vo.setSonServeSetSpace(v.getSonServeSetSpace());
//							vo.setSonServeSetNum(v.getSonServeSetNum());
//							sysDao.getServiceDo().add(vo);
//						}
//					}
//				}
//			}


			Map<String,Object> map = new HashMap<String,Object>();
//			String sql = " SELECT * FROM app_sign_form where SIGN_JJ_TYPE IS NOT NULL AND SIGN_JJ_TYPE LIKE '%,%'";
//			List<AppSignForm> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppSignForm.class);
//			if(ls != null && ls.size() >0){
//				for(AppSignForm sign : ls){
//					String signsJjType = sign.getSignsJjType();
//					System.out.println(signsJjType);
////					if(StringUtils.isNotBlank(signsJjType)){
////						String[] groups = signsJjType.split(",");
////						if (groups != null && groups.length > 0) {
////							sysDao.getAppLabelGroupDao().addLabel(groups,sign.getId(),sign.getSignTeamId(),sign.getSignAreaCode(), LabelManageType.JJLX.getValue());
////						}
////					}
//				}
//			}
//
//			CloseableHttpClient client = HttpClients.createDefault();
//			JSONObject jsonParam = new JSONObject();
//			jsonParam.put("idNo", "350102195401040478");
//			jsonParam.put("urlType", "0591");
//			String url = PropertiesUtil.getConfValue("idCardHealthUrl") + "/appCommon.action?act=findFwType";
//			String str = HtmlUtils.getInstance().executeResponseJson(client, "post", url, jsonParam, "utf-8");
//			JSONObject jsonall = JSONObject.fromObject(str);
//			if (jsonall.get("vo") != null && jsonall.get("msgCode").equals("800")) {
//				AppDrPatientFwEntity ls = JSON.parseObject(jsonall.get("vo").toString(), AppDrPatientFwEntity.class);
//				System.out.println(JsonUtil.toJson(ls));
//			}
//			JSONObject jsonParam = new JSONObject();
//			String urlLogin = PropertiesUtil.getConfValue("idCardHealthUrl")+"/appCommon.action?act=getFetchFamily";
//			jsonParam.put("idNo","350212199101033109");
////			if(StringUtils.isNotBlank(idName))
//				jsonParam.put("name","纪丽速");
//			String str1 = HtmlUtils.getInstance().executeResponseJson(client, "post", urlLogin, jsonParam,"utf-8");
//			if(StringUtils.isNotBlank(str1)){
//				JSONObject jsonAll = JSONObject.fromObject(str1);
//				if(jsonAll.get("rows") != null){
//					List<AppFamilyInfo> ls = JsonUtil.fromJson(jsonAll.get("rows").toString(), new TypeToken<List<AppFamilyInfo>>() {}.getType());
//					System.out.println(JsonUtil.toJson(ls));
//				}
//			}

//			AppPlannedPeopleEntity v  = sysDao.getSecurityCardAsyncBean().getPlannedPeople("2017","11","350102801000");
//			System.out.println(JsonUtil.toJson(v));
//			String result = sysDao.getSecurityCardAsyncBean().getSecurityCard("DC5012000",null,null);
//			System.out.println(result);

//			SericuryUtil p = new SericuryUtil();
//			String  userId = p.encrypt("31010419541216288X");
//			RegisterUsers users = new RegisterUsers();
//			io.swagger.client.model.User payload = new io.swagger.client.model.User()
//					.username(userId)
//					.password(userId);
//			users.add(payload);
//			EasemobIMUsers ease = new EasemobIMUsers();
//			Object userNme =ease.getIMUserByUserName(userId);
//			if(userNme != null){
//
//			}else {
//
//			}

//			sysDao.getSecurityCardAsyncBean().registeredEasemobTemp("31010419541216288X");

//			List<AppHospDept> hospDepts = sysDao.getServiceReadDo().loadByPk(AppHospDept.class, "hospUpcityareaId", "350100000000");
//			System.out.println(hospDepts.size());




		//套餐初始化
//		String sql = "SELECT * FROM `APP_SERVE_GROUP` where SERG_PK_ID IS NULL";
//		List<AppServeGroup> ls =sysDao.getServiceDo().findSqlMap(sql,map,AppServeGroup.class);
//		if(ls != null && ls.size()>0){
//			for(AppServeGroup p : ls){
//				String[] s = p.getSergPkTitle().split(",");
//				if(s != null){
//					String result = null;
//					for(String g : s){
//						map.put("SERPK_NAME",g);
//						String sqlv = " SELECT * FROM APP_SERVE_PACKAGE where SERPK_NAME = :SERPK_NAME ";
//						List<AppServePackage> lv = sysDao.getServiceDo().findSqlMap(sqlv,map,AppServePackage.class);
//						if(lv != null && lv.size()>0){
//							AppServePackage v = lv.get(0);
//							if(result == null){
//								result = v.getId();
//							}else{
//								result += ";"+v.getId();
//							}
//						}
//
//					}
//					p.setSergPkId(result);
//					sysDao.getServiceDo().modify(p);
//				}
//
//			}
//		}

//			String sql = "SELECT * FROM `APP_SERVE_GROUP` where SERG_OBJECT_ID IS NULL or SERG_OBJECT_ID =''";
//			List<AppServeGroup> ls =sysDao.getServiceDo().findSqlMap(sql,map,AppServeGroup.class);
//			if(ls != null && ls.size()>0){
//				for(AppServeGroup p : ls){
//						String result = null;
//						map.put("SERO_NAME",p.getSergObjectTitle());
//						String sqlv = " SELECT * FROM app_serve_object where SERO_NAME = :SERO_NAME ";
//						List<AppServeObject> lv = sysDao.getServiceDo().findSqlMap(sqlv,map,AppServeObject.class);
//						if(lv != null && lv.size()>0){
//							AppServeObject v = lv.get(0);
//							if(result == null){
//								result = v.getId();
//							}
//						}
//						p.setSergObjectId(result);
//						sysDao.getServiceDo().modify(p);
//					}
//			}
//			String sql = "SELECT * FROM `APP_SERVE_SETMEAL` where SERSM_GROUP_ID IS NULL OR SERSM_GROUP_ID = ''";
//			List<AppServeSetmeal> ls =sysDao.getServiceDo().findSqlMap(sql,map,AppServeSetmeal.class);
//			if(ls != null && ls.size()>0){
//				for(AppServeSetmeal p : ls){
//					String result = null;
//					String[] results = p.getSersmGroupValue().split(";");
//					map.put("SERG_VALUE",results);
//					String sqlv = " SELECT * FROM APP_SERVE_GROUP where SERG_VALUE IN (:SERG_VALUE) ";
//					List<AppServeGroup> lv = sysDao.getServiceDo().findSqlMap(sqlv,map,AppServeGroup.class);
//					if(lv != null && lv.size()>0){
//						for(AppServeGroup v : lv){
//							if(result == null){
//								result = v.getId();
//							}else{
//								result += ";"+v.getId();
//							}
//						}
//					}
//					p.setSersmGroupId(result);
//					sysDao.getServiceDo().modify(p);
//				}
//			}
//			String startDate = "2016-01-01";
//			String endDate = ExtendDate.getYMD(Calendar.getInstance());
//			List<String> ls = ExtendDateUtil.getListBetweenMonth(startDate,endDate);
//			if(ls != null){
//				for(String s: ls){
//					System.out.println(s);
//				}
//			}
//			System.out.println(ExtendDate.getFirstDayOfMonth("2018-05"));
//			System.out.println(ExtendDate.getLastDayOfMonth("2018-05"));

//			String state = PropertiesUtil.getConfValue("manageState");
//			List<String> ls = new ArrayList<String>();
//			if(state.equals(CommonEnable.QIYONG.getValue())){
//				String startDate = "2016-01-01";
//				String endDate = ExtendDate.getYMD(Calendar.getInstance());
//				ls = ExtendDateUtil.getListBetweenMonth(startDate,endDate);
//			}else{
//				String date = ExtendDate.getYMD(Calendar.getInstance());
//				ls = ExtendDateUtil.getListBetweenMonth(date,date);
//			}
//			List<AppTeamManagEntity> lsTeam = sysDao.getAppTeamDao().findByManageCont();
//			if(ls != null && ls.size() >0){
//				for(String s : ls){
//					if(s.equals("2018-05")){
//						break;
//					}
//					sysDao.getSecurityCardAsyncBean().totalManageCount(s,lsTeam);
//				}
//			}
//			String sql = "SELECT * FROM APP_SIGN_FORM t where t.SIGN_STATE in(:SIGN_STATE) and t.HS_UPDATE_TIME < '2018-05-29 15:00:00'";
//			String[] qys = {SignFormType.YQY.getValue(),SignFormType.YUQY.getValue()};
//			map.put("SIGN_STATE",qys);
//			List<AppSignForm> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppSignForm.class);
//			if(ls != null && ls.size() > 0){
//				for(AppSignForm form : ls){
//					Calendar calendar = Calendar.getInstance();
//					calendar.setTime(form.getSignDate().getTime());
//					calendar.add(Calendar.YEAR, +1); // 年份减1
//					calendar.add(Calendar.DATE, -1); // 日期加1
//
//					Date result = calendar.getTime();
//					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//					System.out.println(sdf.format(result));
//					form.setSignToDate(calendar);
//					form.setSignFromDate(form.getSignDate());
//					sysDao.getServiceDo().modify(form);
//				}
//			}
//			String date = "2019-02-13 08:00:00";
//			System.out.println(date);
//			Calendar signEnd = ExtendDate.getCalendar(date);
//			signEnd.add(Calendar.YEAR, -1); // 年份减1
//			signEnd.add(Calendar.DATE, +1); // 年份减1
//			System.out.println(ExtendDate.getYMD_h_m_s(signEnd));

//			sysDao.getAppMsgCodeDao().sendMessageNew("15980990371","测试");
		}catch(Exception e){

			e.printStackTrace();
		}
		
	}


}
