import com.ylz.bizDo.app.po.*;
import com.ylz.bizDo.app.vo.AppTeamQvo;
import com.ylz.bizDo.dd.po.DdJlqkQch;
import com.ylz.bizDo.web.po.WebPatientUser;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packaccede.util.CardUtil;
import com.ylz.packcommon.common.CommConditionVo;
import com.ylz.packcommon.common.Constrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.comEnum.UserUpHpisType;
import com.ylz.packcommon.common.util.ExtendDate;
import com.ylz.packcommon.common.util.Md5Util;
import com.ylz.packcommon.common.util.PinyinUtil;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.scheduling.annotation.Async;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SmTest {
	private ApplicationContext context;

	@Before
	public void before() {
		context = new ClassPathXmlApplicationContext("/spring/applicationContext.xml");
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
//		SysDao sysDao=(SysDao) SpringHelper.getBean("sysDao");
		try{
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
//			List<AppFamilyInfo> result = sysDao.getSecurityCardAsyncBean().getFetchFamily("350402198305040021","林金霞");
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
//			String sql = "SELECT * FROM APP_TEAM where TEAM_EASE_GROUP_ID IS NULL ";
//			List<AppTeam> ls = sysDao.getServiceDo().findSqlMap(sql,map,AppTeam.class);
//			if(ls != null && ls.size() >0){
//				for(AppTeam team : ls){
//					sysDao.getSecurityCardAsyncBean().addGroupTemp(team);
//				}
//			}

//			Map<String,Object> map = new HashMap<String,Object>();
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
//			String sql = "SELECT * FROM CP_CITY_AREA where LEVEL_NAME <= '3' and CITY_AREA_ID like '14%'";
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
//			}


//			List<AppTeam> ls = sysDao.getAppTeamDao().findAll(null);
//			Map<String,Object> map = new HashMap<String, Object>();
//			String sql = "SELECT * FROM APP_TEAM  as a WHERE 1=1 ";
//			List<AppTeam> ls =sysDao.getServiceDo().findSqlMap(sql, map, AppTeam.class);
//			if(ls != null && ls.size() >0){
//				int i = 1;
//				for(AppTeam team : ls){
//					map = new HashMap<>();
//					map.put("MEM_TEAMID",team.getTeamCode());
//					map.put("MEM_DR_ID",team.getTeamDrId());
//					sql = "SELECT * FROM APP_TEAM_MEMBER  as a WHERE 1=1 ";
//					sql += " AND MEM_TEAMID= :MEM_TEAMID AND MEM_DR_ID= :MEM_DR_ID ORDER BY MEM_DR_ID ";
//					List<AppTeamMember> lsMem =sysDao.getServiceDo().findSqlMap(sql, map, AppTeamMember.class);
//
//					if(lsMem != null && lsMem.size() >0){
//
//					}else{
//						AppTeamMember men = new AppTeamMember();
//						men.setMemDrId(team.getTeamDrId());
//						men.setMemDrName(team.getTeamDrName());
//						men.setMemState("0");
//						men.setMemWorkType("3");
//						men.setMemTeamid(team.getTeamCode());
//						men.setMemTeamName(team.getTeamName());
//						sysDao.getServiceDo().add(men);
//						i++;
//					}
//				}
//				System.out.println(i);
//			}
//			Map<String,Object> map = new HashMap<String, Object>();
//			map.put("STATE","0");
//			String sql = "SELECT * from APP_TEST_SM WHERE STATE = :STATE";
//			List<Map> ls = sysDao.getServiceDo().findSqlMap(sql, map);
//			if(ls != null && ls.size() >0){
//				for(Map p : ls){
//					String id = p.get("ID").toString();
//					String hospId = p.get("HOSP_ID").toString();
//					String drId = p.get("DR_ID").toString();
//					String teamId = null;
//					if(p.get("TEAM_ID") != null){
//						teamId = p.get("TEAM_ID").toString();
//					}
//					String areaCode = p.get("AREA_CODE").toString();
//					String idNo = p.get("IDNO").toString();
//					String tel = null;
//					if(p.get("TEL") != null){
//						tel = p.get("TEL").toString();
//					}
//					String signDate = p.get("SIGN_DATE").toString();
//					String signType = p.get("SIGN_TYPE").toString();
//					String state = p.get("STATE").toString();
//					String name = p.get("NAMES").toString();
//					String age = null;
//					String gender = null;
//					String patientId = null;
//					AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,hospId);
//					AppDrUser user = (AppDrUser)sysDao.getServiceDo().find(AppDrUser.class,drId);
//					AppTeam team = null;
//					if(StringUtils.isNotBlank(teamId)){
//						team = (AppTeam)sysDao.getServiceDo().find(AppTeam.class,teamId);
//					}else {
//						team = sysDao.getAppTeamDao().findHospDrId(hospId,drId);
//						if(team != null)
//							teamId = team.getId();
//					}
//					if(team == null){
//						team = new AppTeam();
//						team.setTeamDrId(drId);
//						team.setTeamCode(user.getDrCode());
//						team.setTeamCreateTime(Calendar.getInstance());
//						team.setTeamHospId(hospId);
//						team.setTeamDrName(user.getDrName());
//						team.setTeamHospName(dept.getHospName());
//						team.setTeamName(user.getDrName()+"服务团队");
//						team.setTeamState("1");
//						team.setTeamTel(user.getDrTel());
//						team.setTeamType("0");
//						sysDao.getServiceDo().add(team);
//						AppTeamMember men = new AppTeamMember();
//						men.setMemDrId(team.getTeamDrId());
//						men.setMemDrName(team.getTeamDrName());
//						men.setMemState("1");
//						men.setMemWorkType("3");
//						men.setMemTeamid(team.getId());
//						men.setMemTeamName(team.getTeamName());
//						sysDao.getServiceDo().add(men);
//						teamId = team.getId();
//					}
//
//					List<AppPatientUser> puser= sysDao.getServiceDo().loadByPk(AppPatientUser.class,"patientIdno",idNo);
//					if(puser!=null && !puser.isEmpty()){
//						patientId = puser.get(0).getId();//用户存在 替换成当前用户
//						name = puser.get(0).getPatientName();
//						gender = puser.get(0).getPatientGender();
//						age=puser.get(0).getPatientAge();
//					}else{
//						AppPatientUser wpuser=new AppPatientUser();
//						wpuser.setPatientName(name);
//						Map<String,Object> idNos = new HashMap<String,Object>();
//						if(idNo.trim().length() == 18){
//							idNos = CardUtil.getCarInfo(idNo.trim());
//							wpuser.setPatientIdno(idNo.toUpperCase());
//							wpuser.setPatientPwd(Md5Util.MD5(idNo.substring(idNo.length()-6,idNo.length())));
//						}else if(idNo.trim().length() == 15){
//							idNos = CardUtil.getCarInfo15W(idNo.trim());
//							wpuser.setPatientIdno(idNo.toUpperCase());
//							wpuser.setPatientPwd(Md5Util.MD5(idNo.substring(idNo.length()-6,idNo.length())));
//						}
//						if(StringUtils.isNotBlank(wpuser.getPatientName()))
//							wpuser.setPatientBopomofo(PinyinUtil.getPinYinHeadChar(wpuser.getPatientName()));
//						if(idNos.get("age") != null){
//							age =idNos.get("age").toString();
//							wpuser.setPatientAge(idNos.get("age").toString());
//						}
//						if(idNos.get("sex") != null) {
//							gender = idNos.get("sex").toString();
//							wpuser.setPatientGender(idNos.get("sex").toString());
//						}
//						if(idNos.get("birthday") != null) {
//							wpuser.setPatientBirthday(ExtendDate.getCalendar(idNos.get("birthday").toString()));
//						}
//						wpuser.setPatientUpHpis(UserUpHpisType.WEIJIHUO.getValue());//用户未激活
//						sysDao.getServiceDo().add(wpuser);
//						patientId = wpuser.getId();
//					}
//
//					//签约----------------------------------------------
//					System.out.println("patientId--"+patientId+"--teamid--"+teamId+"--drId--"+drId+"---hospId---"+hospId);
//					AppSignBatch batch=new AppSignBatch();//批次
//					AppSignForm form=new AppSignForm();//签约单
//					batch.setBatchCreateDate(Calendar.getInstance());
//					batch.setBatchTeamId(teamId);
//					batch.setBatchTeamName(team.getTeamName());
//					batch.setBatchCreatePersid(patientId);
//					batch.setBatchPatientName(name);
//					//组织批次号
//					if(dept!=null && StringUtils.isNotBlank(dept.getHospAreaCode())) {
//						AppSerial serial = sysDao.getAppSignformDao().getAppSerial(dept.getHospAreaCode().substring(0, 4), "batch");
//						if(serial!=null) {
//							String bcnum = sysDao.getAppSignformDao().getNum(serial.getSerialNo());
//							serial.setSerialNo(bcnum);
//							sysDao.getServiceDo().modify(serial);
//							batch.setBatchNum(bcnum);//批次号
//						}
//					}
//					//
//					sysDao.getServiceDo().add(batch);
//					form.setSignBatchId(batch.getId());
//					//组织编码
//					if(dept!=null && dept.getHospAreaCode()!=null) {
//						form.setSignHospId(dept.getId());
//						form.setSignAreaCode(dept.getHospAreaCode());
//						AppSerial serialSign = sysDao.getAppSignformDao().getAppSerial(dept.getHospAreaCode().substring(0, 4), "sign");
//						if (serialSign != null) {
//							String sinum = sysDao.getAppSignformDao().getNum(serialSign.getSerialNo());
//							serialSign.setSerialNo(sinum);
//							sysDao.getServiceDo().modify(serialSign);
//							form.setSignNum(sinum);//签约编码
//						}
//					}
//					//
//					form.setSignPatientId(patientId);
//					form.setSignDate(ExtendDate.getCalendar(signDate));
//					form.setSignPayState("0");//0：未缴费
//					form.setSignType("1");//1家庭签约
//					form.setSignTeamId(teamId);
//					form.setSignTeamName(team.getTeamName());
//					form.setSignWay("2");//医生代签
//					form.setSignState("2");//已签约
//					form.setSignFromDate(form.getSignDate());
//					form.setSignPatientGender(gender);
//					if(StringUtils.isNotBlank(age))
//						form.setSignPatientAge(Integer.parseInt(age));
//					Calendar end= Calendar.getInstance();
//					end.setTime(form.getSignDate().getTime());
//					end.add(Calendar.YEAR,1);
//					end.add(Calendar.DATE,-1);
//					form.setSignToDate(end);
//					form.setSignDrId(drId);
//					form.setSignContractState("0");//1是 0否
//					form.setSignGreenState("0");//1是 0否
//					form.setSignYellowState("0");//1是 0否
//					form.setSignRedState("0");//1是 0否
////					form.setSignsJjType(vo.getSignsJjType());
//					form.setUpHpis("2");//数据来源web
//					form.setSignHealthGroup("199");
//					sysDao.getServiceDo().add(form);
//
//					//改服务人群为多选
//					if(StringUtils.isNotBlank(signType)){
//						String[] groups = signType.split(",");
//						if (groups != null && groups.length > 0) {
//							form.setSignPersGroup(groups[0]);//支持之前版
//							sysDao.getServiceDo().modify(form);
//							for (String g : groups) {
//								List<AppLabelManage> manage = sysDao.getServiceDo().loadByPk(AppLabelManage.class, "labelValue", g);
//								if (manage != null && !manage.isEmpty()) {
//									AppLabelGroup alg = new AppLabelGroup();
//									alg.setLabelId(manage.get(0).getId());
//									alg.setLabelSignId(form.getId());
//									alg.setLabelTeamId(form.getSignTeamId());
//									alg.setLabelTitle(manage.get(0).getLabelTitle());
//									alg.setLabelValue(manage.get(0).getLabelValue());
//									alg.setLabelType(manage.get(0).getLabelType());
//									sysDao.getServiceDo().add(alg);
//								}
//							}
//						}
//					}
//					map = new HashMap<>();
//					map.put("STATE","1");
//					map.put("ID",id);
//					sql = "UPDATE APP_TEST_SM t SET t.STATE = :STATE WHERE ID = :ID";
//					sysDao.getServiceDo().update(sql,map);
//				}
//			}
//			Map<String,Object> map = new HashMap<String, Object>();
//			map.put("STATE","0");
//			if(allPage == 0){
//				String sqlCount = "SELECT count(1) from APP_TEST_SM WHERE STATE = :STATE";
//				int allCount = sysDao.getServiceDo().findCount(sqlCount,map);
//				allPage  =(allCount+500-1)/500;
//			}
//			if(allPage != 0){
//				for (int i = 0; i < allPage; i++) {
//					page++;
//					System.out.println("循环======" + page);
//					sysDao.getSecurityCardAsyncBean().upDateSm(page);
//				}
//			}

//			SysDao sysDao=(SysDao) SpringHelper.getBean("sysDao");
//			String sql = "SELECT\n" +
//					"\tGROUP_CONCAT(id) TEAM_ID,\n" +
//					"\tTEAM_DR_ID,\n" +
//					"\tTEAM_NAME,\n" +
//					"\tcount(1)\n" +
//					"FROM\n" +
//					"\tAPP_TEAM\n" +
//					"GROUP BY\n" +
//					"\tTEAM_DR_ID,\n" +
//					"\tTEAM_NAME\n" +
//					"HAVING\n" +
//					"\tcount(1) > 1";
//			List<Map> ls = sysDao.getServiceDo().findSql(sql);
//			if(ls != null && ls.size()>0){
//				for(Map m : ls) {
//					String id = m.get("TEAM_ID").toString();
//					String teamName = m.get("TEAM_NAME").toString();
//					String teamDrId = m.get("TEAM_DR_ID").toString();
//					Map<String, Object> maps = new HashMap<String, Object>();
//					maps.put("TEAM_DR_ID", teamDrId);
//					maps.put("TEAM_NAME", teamName);
//					sql = "SELECT * FROM APP_TEAM T where t.TEAM_NAME = :TEAM_NAME AND t.TEAM_DR_ID = :TEAM_DR_ID";
//					List<AppTeam> lsTeam = sysDao.getServiceDo().findSqlMap(sql, maps, AppTeam.class);
//					int i = 0;
//					for (AppTeam s : lsTeam) {
//						if (i != 0) {
//							sql = " DELETE FROM APP_TEAM WHERE ID = :ID";
//							maps = new HashMap<String, Object>();
//							maps.put("ID",s.getId());
//							sysDao.getServiceDo().update(sql, maps);
//						}
//						i++;
//					}
//				}
//			}
//			String sql =" SELECT t.ID,t.SIGN_TEAM_NAME FROM APP_SIGN_FORM t where t.SIGN_TEAM_ID NOT in (\n" +
//					"SELECT ID FROM APP_TEAM\n" +
//					")";
//			List<Map> ls = sysDao.getServiceDo().findSql(sql);
//			if(ls != null && ls.size()>0){
//				for(Map m : ls) {
//					String id = m.get("ID").toString();
//					String teamName = m.get("SIGN_TEAM_NAME").toString();
//					sql = "SELECT * FROM APP_TEAM where TEAM_NAME = :TEAM_NAME";
//					Map<String, Object> maps = new HashMap<String, Object>();
//					maps.put("TEAM_NAME",teamName);
//					List<AppTeam> lsTeam = sysDao.getServiceDo().findSqlMap(sql,maps,AppTeam.class);
//					AppSignForm form = (AppSignForm) sysDao.getServiceDo().find(AppSignForm.class,id);
//					if(form != null){
//						form.setSignTeamId(lsTeam.get(0).getId());
//						sysDao.getServiceDo().modify(form);
//					}
//				}
//			}
//			String sql ="SELECT SIGN_PATIENT_ID,count(1) FROM APP_SIGN_FORM where UP_HPIS ='2' AND SIGN_STATE ='2' AND SIGN_PATIENT_ID='350182198508252927' GROUP BY SIGN_PATIENT_ID HAVING count(1) >1";
//			List<Map> ls = sysDao.getServiceDo().findSql(sql);
//			if(ls != null && ls.size()>0){
//				for(Map m : ls) {
//					String id = m.get("SIGN_PATIENT_ID").toString();
//					sql = "SELECT * FROM APP_SIGN_FORM where SIGN_PATIENT_ID = :SIGN_PATIENT_ID";
//					Map<String, Object> maps = new HashMap<String, Object>();
//					maps.put("SIGN_PATIENT_ID",id);
//					List<AppSignForm> lsForm = sysDao.getServiceDo().findSqlMap(sql,maps,AppSignForm.class);
//					if(lsForm != null && lsForm.size() >0){
//						int i = 0;
//						for (AppSignForm s : lsForm) {
//							if (i != 0) {
//								sql = " DELETE FROM APP_SIGN_FORM WHERE ID = :ID";
//								maps = new HashMap<String, Object>();
//								maps.put("ID",s.getId());
//								sysDao.getServiceDo().update(sql, maps);
//							}
//							i++;
//						}
//					}
//
//				}
//			}
		}catch(Exception e){

			e.printStackTrace();
		}
		
	}


	private static int allPage = 0;
	private static int page = 0;



}
