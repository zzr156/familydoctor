package com.ylz.view.news.action;

import com.opensymphony.xwork2.ModelDriven;
import com.ylz.bizDo.app.po.AppDrUser;
import com.ylz.bizDo.app.po.AppHospDept;
import com.ylz.bizDo.app.po.AppLabelManage;
import com.ylz.bizDo.app.po.AppUserHealthED;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.cd.po.CdUser;
import com.ylz.bizDo.news.Entity.SignPeopleEntity;
import com.ylz.bizDo.news.po.NewsTable;
import com.ylz.bizDo.news.vo.NewsTablePo;
import com.ylz.bizDo.news.vo.NewsTableQqvo;
import com.ylz.bizDo.news.vo.NewsTableQvo;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.Constant;
import com.ylz.packcommon.common.comEnum.AppRoleType;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.comEnum.NoticesType;
import com.ylz.packcommon.common.comEnum.PushState;
import com.ylz.packcommon.common.exception.ActionException;
import com.ylz.packcommon.common.util.ExtendDate;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.io.File;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
@Action(
		value="newsTable",
		results={
				@Result(name="edit", location="/intercept/news/newsTable/news_table_edit.jsp"),
				@Result(name="list", location="/intercept/news/newsTable/news_table_list.jsp"),
				@Result(name="set", location="/intercept/news/newsTable/news_table_set.jsp"),
			    @Result(name = "json", type = "json",params={"root","jsons","contentType", "text/html"}),
				@Result(name = "jsontreelist", type = "json",params={"root","jsonList","contentType", "text/html"}),
				@Result(name = "jsonVo", type = "json",params={"root","jsonVo","contentType", "text/html"})
	   }
)
public class NewsTableAction extends CommonAction implements ModelDriven<NewsTable>{
	@Override
	public NewsTable getModel() {
		 if (null == vo) {
	            return vo = new NewsTable();
	        }
	        return vo;
	}
	private NewsTable vo;
	private NewsTableQvo qvo;
	private CdUser user;
	private File image; //上传的文件
	private String imageFileName; //文件名称
	private String imageContentType; //文件类型
	private NewsTableQqvo qqvo;
	
	/**
	 * 准备查询
	 * @return
	 */
	public String forList() {
		return "list";
	}
	
	/**
	 * 查询
	 * @return
	 */
	public String list() {
		try {
			NewsTableQvo qvo = (NewsTableQvo)getQvoJson(NewsTableQvo.class);
			List<NewsTablePo> ls = sysDao.getNewsTableDao().findAllNewsTable(qvo);
			jsons.setRowsQvo(ls,qvo);
		}catch (Exception e){
			e.printStackTrace();
			new ActionException(this.getClass(),e.getMessage());
			this.newMsgJson(finalErrorMsg);
		}
		return "json";
	}
	
	/**
	 * 准备新增或者修改
	 * @return
	 */
	public String forAddOrEdit(){
		user = (CdUser) this.getRequest().getSession(true).getAttribute(Constant.SESSION_ATTRIBUTE_LOGIN_STAFF);
		return "edit";
	}
	
	public String jsonByOne(){
		   String id = this.getRequest().getParameter("id");
		   if(StringUtils.isNotBlank(this.getHandle())){
		   		if(this.getHandle().equals("look")){
		   			NewsTable table = (NewsTable)this.getSysDao().getServiceDo().find(NewsTable.class,id);
		   			table.setTableBrowse(String.valueOf(Integer.parseInt(table.getTableBrowse())+1));
		   			this.getSysDao().getServiceDo().modify(table);
				}
		   }
		   this.setJsonVo((NewsTable) sysDao.getServiceDo().find(NewsTable.class, id));
	       return "jsonVo";
	}
	
	/**
	 * 添加
	 * @return
	 */
	public String add() {
		try {
			NewsTable vo = (NewsTable)getVoJson(NewsTable.class);
			if (vo != null) {
				vo.setTableBrowse("0");
				vo.setTableTransmit("0");
				vo.setTableEnshrine("0");
				CdUser user = this.getSessionUser();
				if(user!=null){
					if("admin".equals(user.getAccount()) || "smadmin".equals(user.getAccount())||
							"zzadmin".equals(user.getAccount())||
							"fzadmin".equals(user.getAccount())||
							"ptadmin".equals(user.getAccount())||
							"npadmin".equals(user.getAccount())||
							"qzadmin".equals(user.getAccount())){//管理员添加系统信息
						vo.setTableType("1");//系统
					}else{
						vo.setTableType("2");//医院模板
					}
					vo.setTableHospId(user.getHospId());
				}
				sysDao.getServiceDo().add(vo);
				this.newMsgJson(this.finalSuccessrMsg);
			}else{
				 this.newMsgJson(finalErrorMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(finalErrorMsg);
		}
		return "json";
	}
	
	/**
	 * 修改
	 * @return
	 */
	public String modify() {
		try {
			NewsTable vo = (NewsTable)getVoJson(NewsTable.class);
			if (vo != null) {
				sysDao.getServiceDo().modify(vo);
				this.newMsgJson(this.finalSuccessrMsg);
			}else{
				 this.newMsgJson(finalErrorMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(finalErrorMsg);
		}
		return "json";
	}
	
	/**
	 * 删除
	 * @return
	 */
	public String delete() {
		try {
			String id = this.getRequest().getParameter("id");
			String[] ids = id.split(";");
			if(ids != null && ids.length >0){
				for(String s : ids){
					sysDao.getServiceDo().delete(NewsTable.class,s);
				}
			}
			this.newMsgJson(finalSuccessrMsg);
		} catch (Exception e) {
			e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(finalErrorMsg);
		}
        return "json";
	}

	/**
	 * 页面初始化
	 * @return
	 */
	public String findCmmInit() {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			//公告类型
			//居民服务
			List<AppLabelManage> ls = this.sysDao.getAppLabelManageDao().findByType("3");
			map.put("newsType",ls);
			this.getJsons().setMap(map);
		}catch (Exception e){
			e.printStackTrace();
			new ActionException(this.getClass(),e.getMessage());
			this.newMsgJson(finalErrorMsg);
		}
		return "json";
	}

	/**
	 *	准备设置定期推送
	 * @return
	 */
	public String forSet(){
		return "set";
	}

	/**
	 * 推送页面初始化
	 * @return
	 */
	public String findCmmInitSet(){
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			//接收对象
			List<CdCode> jsObject = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_JSDX, CommonEnable.QIYONG.getValue());
			map.put("object",jsObject);
			this.getJsons().setMap(map);
		}catch (Exception e){
			e.printStackTrace();
			new ActionException(this.getClass(), e.getMessage());
			this.newMsgJson(this.finalErrorMsg);
		}
		return "json";
	}

	/**
	 * 设置定期推送
	 * @return
	 */
	public String set(){
		try {
			NewsTable vo = (NewsTable)getVoJson(NewsTable.class);
			if (vo != null) {
				NewsTable vv = (NewsTable)sysDao.getServiceDo().find(NewsTable.class,vo.getId());
				if(vv!=null){
					vv.setTablePushDate(ExtendDate.getCalendar(vo.getStrTablePushDate()));
					vv.setTablePushObject(vo.getTablePushObject());
					vv.setTablePushState("1");
					vv.setTableObject(vo.getTableObject());
					vv.setTablePeopleList(vo.getTablePeopleList());
					vv.setTablePeopleName(vo.getTablePeopleName());
					this.sysDao.getServiceDo().removePoFormSession(vo);
					sysDao.getServiceDo().modify(vv);
					CdUser user = this.getSessionUser();
					if(user!=null){
						List<AppDrUser> drUsers = sysDao.getAppDrUserDao().findByAccount(user.getAccount());
						AppDrUser drUser = new AppDrUser();
						AppHospDept dept = new AppHospDept();
						String areaCode = "";
						if(drUsers!=null && drUsers.size()>0){
							drUser = drUsers.get(0);
							if(StringUtils.isNotBlank(drUser.getDrHospId())){
								dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
							}
							if(dept!=null){
								if(drUser.getDrRole().indexOf(AppRoleType.SHENG.getValue())!=-1){
									areaCode = dept.getHospAreaCode().substring(0,2)+"0000000000";
								}else if(drUser.getDrRole().indexOf(AppRoleType.SHI.getValue())!=-1){
									areaCode = dept.getHospAreaCode().substring(0,4)+"00000000";
								}else if(drUser.getDrRole().indexOf(AppRoleType.QU.getValue())!=-1){
									areaCode = dept.getHospAreaCode().substring(0,6)+"000000";
								}else if(drUser.getDrRole().indexOf(AppRoleType.SHEQU.getValue())!=-1){
									areaCode = dept.getHospAreaCode().substring(0,9)+"000";
								}
							}
						}
						if(PushState.ZDRY.getValue().equals(vv.getTablePushObject())){//指定人员发送
							if(StringUtils.isNotBlank(vv.getTablePeopleList())){
								String[] ss = vv.getTablePeopleList().split(";");
								String[] ssn = vv.getTablePeopleName().split(",");
								for(int i=0;i<ss.length;i++){
									AppUserHealthED hed = new AppUserHealthED();
									hed.setHedContent(vv.getTableContent());
									hed.setHedCreateTime(Calendar.getInstance());
									hed.setHedImageUrl(vv.getTableImageUrl());
									hed.setHedTitle(vv.getTableTitle());
									hed.setHedUserId(ss[i]);
									hed.setHedType(vv.getTableHealthType());
									hed.setHedObject("4");
									hed.setHedId(vv.getId());
									hed.setHedDrId(drUser.getId());
									hed.setHedDrName(drUser.getDrName());
									hed.setHedHospId(drUser.getDrHospId());
									hed.setHedAreaCode(dept.getHospAreaCode());
									hed.setHedUserName(ssn[i]);
									hed.setHedPushState("1");//定期推送
									hed.setHedPushDate(vv.getTablePushDate());//定期推送时间
									sysDao.getServiceDo().add(hed);
								}
							}
						}else if(PushState.QBRY.getValue().equals(vv.getTablePushObject())){
							AppUserHealthED healthED = new AppUserHealthED();
							if(user!=null){
								healthED.setHedAreaCode(areaCode);
								healthED.setHedHospId(dept.getId());
								healthED.setHedUserId("1");
								healthED.setHedDrName(drUser.getDrName());
								healthED.setHedContent(vv.getTableContent());
								healthED.setHedType(vv.getTableHealthType());
								healthED.setHedCreateTime(Calendar.getInstance());
								healthED.setHedDrId(drUser.getId());
								healthED.setHedImageUrl(vv.getTableImageUrl());
								healthED.setHedTitle(vv.getTableTitle());
								healthED.setHedId(vv.getId());
								healthED.setHedObject(vv.getTablePushObject());
								healthED.setHedPushState("1");//定期推送
								healthED.setHedPushDate(vv.getTablePushDate());//定期推送时间
								sysDao.getServiceDo().add(healthED);
							}
						}else{
							AppUserHealthED healthED = new AppUserHealthED();
							healthED.setHedAreaCode(areaCode);
							healthED.setHedObject(vv.getTablePushObject());
							healthED.setHedId(vv.getId());
							healthED.setHedImageUrl(vv.getTableImageUrl());
							healthED.setHedTitle(vv.getTableTitle());
							healthED.setHedContent(vv.getTableContent());
							healthED.setHedType(vv.getTableHealthType());
							healthED.setHedUserId(vv.getTableObject());
							healthED.setHedCreateTime(Calendar.getInstance());
							healthED.setHedDrId(drUser.getId());
							healthED.setHedDrName(drUser.getDrName());
							healthED.setHedObjectXx(vv.getTableObject());
							healthED.setHedPushState("1");//定期推送
							healthED.setHedPushDate(vv.getTablePushDate());
							sysDao.getServiceDo().add(healthED);
						}
					}
				}
				this.newMsgJson(this.finalSuccessrMsg);
			}else{
				this.newMsgJson(finalErrorMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			new ActionException(this.getClass(),e.getMessage());
			this.newMsgJson(finalErrorMsg);
		}
		return "json";
	}

	/**
	 * 立即推送
	 * @return
	 */
	public String nowSet(){
		try {
			NewsTable vo = (NewsTable)getVoJson(NewsTable.class);
			if (vo != null) {
				NewsTable vv = (NewsTable)sysDao.getServiceDo().find(NewsTable.class,vo.getId());
				if(vv!=null){
					vv.setTablePushDate(ExtendDate.getCalendar(vo.getStrTablePushDate()));
					vv.setTablePushObject(vo.getTablePushObject());
					vv.setTablePushState("2");
					vv.setTableObject(vo.getTableObject());
					vv.setTablePeopleList(vo.getTablePeopleList());
					vv.setTablePeopleName(vo.getTablePeopleName());
					this.sysDao.getServiceDo().removePoFormSession(vo);
					sysDao.getServiceDo().modify(vv);
					CdUser user = this.getSessionUser();
					if(user!=null){
						List<AppDrUser> drUsers = sysDao.getServiceDo().loadByPk(AppDrUser.class,"drTel",user.getAccount());
						AppDrUser drUser = new AppDrUser();
						AppHospDept dept = new AppHospDept();
						String areaCode = "";
						if(drUsers!=null && drUsers.size()>0){
							drUser = drUsers.get(0);
							if(StringUtils.isNotBlank(drUser.getDrHospId())){
								dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,drUser.getDrHospId());
							}
							if(dept!=null){
								if(drUser.getDrRole().indexOf(AppRoleType.SHENG.getValue())!=-1){
									areaCode = dept.getHospAreaCode().substring(0,2)+"0000000000";
								}else if(drUser.getDrRole().indexOf(AppRoleType.SHI.getValue())!=-1){
									areaCode = dept.getHospAreaCode().substring(0,4)+"00000000";
								}else if(drUser.getDrRole().indexOf(AppRoleType.QU.getValue())!=-1){
									areaCode = dept.getHospAreaCode().substring(0,6)+"000000";
								}else if(drUser.getDrRole().indexOf(AppRoleType.SHEQU.getValue())!=-1){
									areaCode = dept.getHospAreaCode().substring(0,9)+"000";
								}
							}
						}
						if(PushState.ZDRY.getValue().equals(vv.getTablePushObject())){//指定人员发送
							if(StringUtils.isNotBlank(vv.getTablePeopleList())){
								String[] ss = vv.getTablePeopleList().split(";");
								String[] ssn = vv.getTablePeopleName().split(",");
								for(int i=0;i<ss.length;i++){
									AppUserHealthED hed = new AppUserHealthED();
									hed.setHedContent(vv.getTableContent());
									hed.setHedCreateTime(Calendar.getInstance());
									hed.setHedImageUrl(vv.getTableImageUrl());
									hed.setHedTitle(vv.getTableTitle());
									hed.setHedUserId(ss[i]);
									hed.setHedType(vv.getTableHealthType());
									hed.setHedObject("4");
									hed.setHedId(vv.getId());
									hed.setHedDrId(drUser.getId());
									hed.setHedDrName(drUser.getDrName());
									hed.setHedHospId(drUser.getDrHospId());
									hed.setHedAreaCode(dept.getHospAreaCode());
									hed.setHedUserName(ssn[i]);
									hed.setHedPushState("2");//立即发送
									hed.setHedPushDate(vv.getTablePushDate());
									sysDao.getServiceDo().add(hed);
									sysDao.getAppNoticeDao().addNotices("系统消息", "您新收到一笔系统消息，请注意查看。",
											NoticesType.XTXX.getValue(), "系统", hed.getHedUserId(), hed.getId(), null);
								}
							}
						}else if(PushState.QBRY.getValue().equals(vv.getTablePushObject())){
							AppUserHealthED healthED = new AppUserHealthED();
							if(user!=null){
								healthED.setHedAreaCode(areaCode);
								healthED.setHedHospId(dept.getId());
								healthED.setHedUserId("1");
								healthED.setHedDrName(drUser.getDrName());
								healthED.setHedContent(vv.getTableContent());
								healthED.setHedType(vv.getTableHealthType());
								healthED.setHedCreateTime(Calendar.getInstance());
								healthED.setHedDrId(drUser.getId());
								healthED.setHedImageUrl(vv.getTableImageUrl());
								healthED.setHedTitle(vv.getTableTitle());
								healthED.setHedId(vv.getId());
								healthED.setHedObject(vv.getTablePushObject());
								healthED.setHedPushState("2");//立即发送
								healthED.setHedPushDate(vv.getTablePushDate());
								sysDao.getServiceDo().add(healthED);
								sysDao.getAppNoticeDao().addNoticesAllTag(healthED.getHedTitle(),"您新收到一笔系统消息，请注意查看。",
										NoticesType.XTXX.getValue(),healthED.getHedAreaCode(),"1",
										healthED.getId(),"allpeople",healthED.getHedAreaCode(),true);
								healthED.setHedPushOstate("1");
								sysDao.getServiceDo().modify(healthED);
							}
						}else{
							AppUserHealthED healthED = new AppUserHealthED();
							healthED.setHedAreaCode(areaCode);
							healthED.setHedObject(vv.getTablePushObject());
							healthED.setHedId(vv.getId());
							healthED.setHedImageUrl(vv.getTableImageUrl());
							healthED.setHedTitle(vv.getTableTitle());
							healthED.setHedContent(vv.getTableContent());
							healthED.setHedType(vv.getTableHealthType());
							healthED.setHedUserId(vv.getTableObject());
							healthED.setHedCreateTime(Calendar.getInstance());
							healthED.setHedDrId(drUser.getId());
							healthED.setHedDrName(drUser.getDrName());
							healthED.setHedObjectXx(vv.getTableObject());
							healthED.setHedPushState("2");//立即发送
							healthED.setHedPushDate(vv.getTablePushDate());
							sysDao.getServiceDo().add(healthED);
							sysDao.getAppNoticeDao().addNoticesAllTag(healthED.getHedTitle(),"您新收到一笔系统消息，请注意查看。",
									NoticesType.XTXX.getValue(),healthED.getHedAreaCode(),healthED.getHedObjectXx(),
									healthED.getId(),healthED.getHedObject(),healthED.getHedAreaCode(),true);
							/*if(StringUtils.isNotBlank(vv.getTableObject())){
								String[] ss = vv.getTableObject().split(",");
								for(String s:ss){
									AppNoticeFb ff = new AppNoticeFb();
									ff.setNoticeContent("您新收到一笔系统消息，请注意查看。");
									ff.setNoticeTitle(healthED.getHedTitle());
									ff.setNoticeCreatePeople(healthED.getHedAreaCode());
									ff.setNoticeCreateTime(Calendar.getInstance());
									ff.setNoticeType(NoticesType.XTXX.getValue());
									ff.setNoticeReceivePeople(s);
									ff.setNoticeRead(NoticesReadType.WEIDU.getValue());
									ff.setNoticeForeign(healthED.getId());
									ff.setNoticeId();
									sysDao.getServiceDo().add(ff);
								}
							}*/
							healthED.setHedPushOstate("1");
							sysDao.getServiceDo().modify(healthED);

						}
					}
				}
				this.newMsgJson(this.finalSuccessrMsg);
			}else{
				this.newMsgJson(finalErrorMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			new ActionException(this.getClass(),e.getMessage());
			this.newMsgJson(finalErrorMsg);
		}
		return "json";
	}

	public String findLabelCmm(){
		try {
			Map<String,Object> map = new HashMap<String,Object>();
			String num = this.getRequest().getParameter("num");
			List<AppLabelManage> ls = this.sysDao.getAppLabelManageDao().findByType(num);
			map.put("newsType",ls);
			this.getJsons().setMap(map);
		}catch (Exception e){
			e.printStackTrace();
			new ActionException(this.getClass(),e.getMessage());
			this.newMsgJson(finalErrorMsg);
		}
		return "json";
	}

	/**
	 * 查询患者签约列表
	 * @return
	 */
	public String findPeopleCmm(){
		try{
			NewsTableQqvo qqvo = (NewsTableQqvo)getQvoJson(NewsTableQqvo.class);
			CdUser user = this.getSessionUser();
			if(user!=null){
				if("admin".equals(user.getAccount()) || "smadmin".equals(user.getAccount())||
						"zzadmin".equals(user.getAccount())||
						"fzadmin".equals(user.getAccount())||
						"ptadmin".equals(user.getAccount())||
						"npadmin".equals(user.getAccount())||
						"qzadmin".equals(user.getAccount())){
					qqvo.setType("1");
				}else{
					qqvo.setType("2");
					List<AppDrUser> drList = sysDao.getServiceDo().loadByPk(AppDrUser.class,"drTel",user.getAccount());
					if(drList!=null && drList.size()>0){
						AppDrUser drUser = drList.get(0);
						AppHospDept dept = (AppHospDept)sysDao.getServiceDo().find(AppHospDept.class,user.getHospId());
						if(drUser!=null){
							qqvo.setDrId(drUser.getId());
							if(StringUtils.isNotBlank(drUser.getDrRole())){
								if(drUser.getDrRole().indexOf(AppRoleType.SHENG.getValue())!=-1){
									qqvo.setAreaCode(dept.getHospAreaCode().substring(0,2));
								}else if(drUser.getDrRole().indexOf(AppRoleType.SHI.getValue())!=-1){
									qqvo.setAreaCode(dept.getHospAreaCode().substring(0,4));
								}else if(drUser.getDrRole().indexOf(AppRoleType.QU.getValue())!=-1){
									qqvo.setAreaCode(dept.getHospAreaCode().substring(0,6));
								}else if(drUser.getDrRole().indexOf(AppRoleType.SHEQU.getValue())!=-1){
									qqvo.setAreaCode(dept.getHospAreaCode().substring(0,9));
								}else if(drUser.getDrRole().indexOf(AppRoleType.TUANDUI.getValue())!=-1){//团队
									qqvo.setAreaCode(AppRoleType.TUANDUI.getValue());
								}else if(drUser.getDrRole().indexOf(AppRoleType.YISHENG.getValue())!=-1){//医生权限
									qqvo.setAreaCode(AppRoleType.YISHENG.getValue());
								}
							}
						}
					}
				}
			}
			List<SignPeopleEntity> ls = sysDao.getAppSignformDao().findPeople(qqvo);
			jsons.setRowsQvo(ls,qqvo);
		}catch (Exception e){
			e.printStackTrace();
			new ActionException(this.getClass(),e.getMessage());
			this.newMsgJson(finalErrorMsg);
		}
		return "json";
	}



	public NewsTable getVo() {
		return vo;
	}
	public void setVo(NewsTable vo) {
		this.vo = vo;
	}
	public NewsTableQvo getQvo() {
		return qvo;
	}
	public void setQvo(NewsTableQvo qvo) {
		this.qvo = qvo;
	}
	public CdUser getUser() {
		return user;
	}
	public void setUser(CdUser user) {
		this.user = user;
	}
	public String getImageFileName() {
		return imageFileName;
	}
	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}
	public String getImageContentType() {
		return imageContentType;
	}
	public void setImageContentType(String imageContentType) {
		this.imageContentType = imageContentType;
	}
	public File getImage() {
		return image;
	}
	public void setImage(File image) {
		this.image = image;
	}

	public NewsTableQqvo getQqvo() {
		return qqvo;
	}

	public void setQqvo(NewsTableQqvo qqvo) {
		this.qqvo = qqvo;
	}
}
