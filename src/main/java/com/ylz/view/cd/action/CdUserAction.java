package com.ylz.view.cd.action;


import com.opensymphony.xwork2.ValidationAwareSupport;
import com.ylz.bizDo.cd.po.*;
import com.ylz.bizDo.cd.vo.PositionVo;
import com.ylz.bizDo.cd.vo.RoleVo;
import com.ylz.bizDo.cd.vo.UserVo;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.Constant;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.exception.ActionException;
import com.ylz.packcommon.common.util.Md5Util;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import javax.servlet.ServletException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * 用户登录 action
 * 
 * @author 513-02 //action中返回字符串是success时，返回工程主页
 */

@SuppressWarnings("all")
@Action(value = "users", results = {
		@Result(name = "list", location = "/intercept/cd/user/user_list.jsp"),
		@Result(name = "edit", location = "/intercept/cd/user/user_edit.jsp"),
        @Result(name = "resetPwd", location = "/intercept/cd/user/resetUserPwd.jsp"),
        @Result(name = "logou", location = "/open/login.jsp", type = "redirect"),
        @Result(name = "json", type = "json",params={"root","jsons","excludeProperties","rows.*\\.userPost,rows.*\\.role,rows.*\\.cdDept","contentType", "text/html"}),
        @Result(name = "jsonVo", type = "json",params={"root","jsonVo",
        		"excludeProperties","userPost,role,cdDept","contentType", "text/html"})
})
public class CdUserAction extends CommonAction {

	

	
	private static final long serialVersionUID = 7716035788719025037L;
	private String imageId;
	private File image; //上传的文件
	private String imageFileName; //文件名称
	private String imageContentType; //文件类型
	private String imgSrc;
	private CdUser vo;
	private UserVo qvo;// 查询条件
    private String userOldPassword;//旧密码
    private String userNewPassword;// 新密码
    private String userPwdOk;// 新确认密码
	private List<CdPosition> postlist;
	private List<CdDept> cdDeptlist;
	private List<CdRole> rolelist;
	

	private final ValidationAwareSupport validationAware = new ValidationAwareSupport();
	
	
	public String jsonByOne(){
		   String id = this.getRequest().getParameter("userId");
		   this.setJsonVo((CdUser) sysDao.getServiceDo().find(CdUser.class, id));
	       return "jsonVo";
	}

	
	
	/**
	 * 准备查询
	 * @return
	 */
	public String forList() {
		return "list";
	}

	/**
	 * 点击用户管理页面查询按钮<br>
	 * 按账号,用户名,部门,在职状态查询
	 * @return
	 * @throws ActionException
	 */
	public String list() {
		try{
			UserVo qvo = (UserVo)getQvoJson(UserVo.class);
			List<CdUser> ls = sysDao.getUserDo().findH(qvo);
			jsons.setRowsQvo(ls,qvo);
			return "json";
		}catch (Exception e){
			e.printStackTrace();
			new ActionException(this.getClass(), e.getMessage());
			this.newMsgJson(finalErrorMsg);
			return "json";
		}

	}
	
	/**
	 * 准备新增或者修改
	 * @return
	 */
	public String forAddOrEdit(){
		return "edit";
	}
	
	
	/**
	 * 在用户管理页面点击新增链接，先查询所有职位名称，部门名称，角色名称， 然后将信息添加到数据库中，添加成功返回用户管理页面,
	 * 无论成功或失败都返回用户管理页面
	 * 
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public String add() {
		try {
			CdUser vo = (CdUser)getVoJson(CdUser.class);
			if (vo != null) {
				CdDept dept = (CdDept) this.sysDao.getServiceDo().find(CdDept.class, vo.getCdDept().getId());
				vo.setCdDept(dept);
				// 验证此用户是否已经存在
				List<CdUser> us = this.sysDao.getServiceDo().loadByPk(
						CdUser.class, "account", vo.getAccount());
				if (us != null && us.size() > 0) {
					this.newMsgJson("此账号已存在");
					return "json";
				}
				// 加密
				String md5UserPassword = Md5Util.MD5(vo.getUserPassword());
				vo.setUserPassword(md5UserPassword);
				String md5UserSignaturePwd = Md5Util.MD5(vo.getUserSignaturePwd());
				vo.setUserSignaturePwd(md5UserSignaturePwd);
				CdUser uss= this.getSessionUser();
				if(uss!=null){
					vo.setCjrId(uss.getUserId());//创建人id
					vo.setXgrId(uss.getUserId());//修改人id
		        	if(uss.getCdDept()!=null){
		        		vo.setCjdwId(uss.getCdDept().getId());//创建人单位id
		        		vo.setXgdwId(uss.getCdDept().getId());//修改人单位id
		        	}
		        }
				
				this.sysDao.getServiceDo().add(vo);
				if(StringUtils.isNotBlank(vo.getUserId())){
					this.sysDao.getMsgPhoneAsyncBean().msgPhoneCa(vo.getUserId(), vo.getAccount(), vo.getMobilePhone());
				}
				this.newMsgJson(this.finalSuccessrMsg);
			}else{
				this.newMsgJson(finalErrorMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			new ActionException(this.getClass(), e.getMessage());
			this.newMsgJson(this.finalErrorMsg);
			return "json";
		}
		
		return "json";
	}
	
	/**
	 * 编辑(修改)用户
	 * 
	 * @return
	 */
	public String modify()  {
		try {
			CdUser vo = (CdUser)getVoJson(CdUser.class);
			if (vo != null) {
				CdDept dept = (CdDept) this.sysDao.getServiceDo().find(CdDept.class, vo.getCdDept().getId());
				vo.setCdDept(dept);
				CdUser us = this.sysDao.getUserDo().findIsUser(vo.getAccount(),
						vo.getUserId());
				if (us != null) {
					this.newMsgJson("此账号已存在");
					return "json";
				}
				us = (CdUser) this.sysDao.getServiceDo().find(CdUser.class,vo.getUserId());
				us.setAccount(vo.getAccount());
				us.setUserName(vo.getUserName());
				us.setUserPassword(vo.getUserPassword());
				us.setUserSignaturePwd(vo.getUserSignaturePwd());
				us.setCdDept(vo.getCdDept());
				us.setUserPost(vo.getUserPost());
				us.setUserSex(vo.getUserSex());
				us.setWorkState(vo.getWorkState());
				us.setCardNum(vo.getCardNum());
				us.setMobilePhone(vo.getMobilePhone());
				us.setHomeTelephone(vo.getHomeTelephone());
				us.setOfficePhone(vo.getOfficePhone());
				us.setEmail(vo.getEmail());
				us.setUserSort(vo.getUserSort());
				us.setUserNum(vo.getUserNum());
				us.setRole(vo.getRole());
				if(StringUtils.isNotBlank(vo.getUserFilePath())){
					us.setUserFilePath(vo.getUserFilePath());
					us.setUserFileName(vo.getUserFileName());
				}
				CdUser uss= this.getSessionUser();
	            if(uss!=null){
					us.setModifyDate(Calendar.getInstance());
					us.setXgrId(uss.getUserId());//修改人id
		        	if(uss.getCdDept()!=null){
						us.setXgdwId(uss.getCdDept().getId());//修改人单位id
		        	}
		        }
				//session出现实体重复时的处理
				this.sysDao.getServiceDo().removePoFormSession(vo);
//				us=new CdUser();
				this.sysDao.getServiceDo().modify(us);
				this.newMsgJson(this.finalSuccessrMsg);
			}else{
				this.newMsgJson(finalErrorMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
			new ActionException(this.getClass(), e.getMessage());
			this.newMsgJson(finalErrorMsg);
			return "json";
		}
		return "json";
	    
	}
	
	
	/**
	 * 用户点击人员移动链接，跳转到部门页面，获取部门名称 根据部门名称修改用户的信息，成功与失败都返回用户管理页面<br>
	 * 移动用户
	 * 
	 * @return
	 */
	public String move() {
		try{
			if(isvers("addTime")) {
				return this.list();
			}
			// 获取id
			String ids = this.getRequest().getParameter("paramId");
			if (StringUtils.isNotBlank(ids)) {
				// 获取用户的信息
				vo = sysDao.getUserDo().findUser(ids);
				// 部门
				String[] cdDeptid = this.getRequest()
						.getParameterValues("cdDeptid");
				// 部门(只有一个)
				if (cdDeptid != null) {
					CdDept find = (CdDept) this.sysDao.getServiceDo().find(CdDept.class,cdDeptid[0]);
					if(vo.getCdDept()==null) {
						vo.setCdDept(new CdDept());
					}
					vo.setCdDept(find);
				}

				sysDao.getServiceDo().modify(vo);

			}
			return this.list();
		}catch (Exception e){
			e.printStackTrace();
			new ActionException(this.getClass(), e.getMessage());
			this.newMsgJson(finalErrorMsg);
			return "json";
		}

	}
	
	/**
	 * 删除用户
	 * @return
	 */
	public String delete() {
		try {
			String id = this.getRequest().getParameter("id");
			String[] ids = id.split(";");
			if(ids != null && ids.length >0){
				for(String s : ids){
					CdUser po = (CdUser) sysDao.getServiceDo().find(CdUser.class,s);
					if(!po.getAccount().equals("admin")){
						sysDao.getServiceDo().delete(CdUser.class,s);
						if(StringUtils.isNotBlank(po.getUserFilePath())){
							deleteFile(po.getUserFilePath());
						}
					}else{
						this.newMsgJson("超级管理员不予许删除!");
						return "json";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			new ActionException(this.getClass(), e.getMessage());
			this.newMsgJson("删除失败");
			return "json";
		}
		this.newMsgJson(finalSuccessrMsg);
		return "json";
	}
	
	

	/**
	 * 用户点击重置密码按钮， 验证新密码与确认新密码正确后执行这个方法<br>
	 * 失败与成功都返回用户管理页面<br>
	 * 重置密码
	 * 
	 * @return
	 */
	public String resetPwd() {
		try{
			CdUser user = (CdUser) this.getRequest().getSession(true).getAttribute(Constant.SESSION_ATTRIBUTE_LOGIN_STAFF);
			String md5OldPwd = Md5Util.MD5(userOldPassword);
			if (!md5OldPwd.equalsIgnoreCase(user.getUserPassword())) {
				this.setMsg("旧密码输入错误");
				return "resetPwd";
			}

			if (!userNewPassword.equalsIgnoreCase(userPwdOk)) {
				this.setMsg("新密码与新确认密码不一致");
				return "resetPwd";
			}
			vo = sysDao.getUserDo().findUser(user.getUserId());
			if (vo!=null) {
				// 加密
				String md5NewPwd = Md5Util.MD5(this.getUserNewPassword());
				vo.setUserPassword(md5NewPwd);
				// 修改
				sysDao.getServiceDo().saveUpdate(vo);
				this.setMsg("修改密码成功!");
				return "resetPwd";

			} else {
				this.setMsg("修改密码失败!");
				return "resetPwd";
			}
		}catch (Exception e){
			e.printStackTrace();
			new ActionException(this.getClass(), e.getMessage());
			this.newMsgJson(finalErrorMsg);
			return "json";
		}

	}
	
	
	
	/**
	 * 用户退出
	 * @return
	 */
	public String exit(){
		CdUser user = (CdUser) this.getRequest().getSession(true)
				.getAttribute(Constant.SESSION_ATTRIBUTE_LOGIN_STAFF);
		if (user != null) {
			this.getRequest().getSession(true)
					.setAttribute(Constant.SESSION_ATTRIBUTE_LOGIN_STAFF, null);
			this.getRequest().getSession().setAttribute("HospName",null);
			this.getRequest().getSession().setAttribute("HospAreaCode",null);
			this.getRequest().getSession().setAttribute("CommHospAreaCode",null);
			this.getRequest().getSession().setAttribute("HospLevel",null);
			this.getRequest().getSession().setAttribute("UserId",null);
			this.getRequest().getSession().setAttribute("DrId",null);
			this.getRequest().getSession().setAttribute("UserName",null);
		
		}		
		return "logou";
	}
	
	/**
	 * 
	 * @throws ActionException
	 */
	public void command() throws ActionException{
		 try {	
			 //	查询所有部门名称
			List<CdDept> findAllCdDept = sysDao.getUserDo().findAllCdDept();
			for (CdDept s : findAllCdDept) {
				if(cdDeptlist == null) {
                    cdDeptlist = new ArrayList<CdDept>();
                }
				if(s.getState().equals("1")){
					cdDeptlist.add(s);
				}
			}
			//sectorlist = (List<Sector>) findAllSector;
			// 查询所有职位名称
			postlist = (List<CdPosition>) sysDao.getUserDo().findAllPosition();
			// 查询所有角色名称
			List<CdRole> rol = this.sysDao.getRoleDo().selectAll(null);//多对多表查询
			for (CdRole r : rol) {
				if(rolelist==null) {
                    rolelist=new ArrayList<CdRole>();
                }
				if(r.getState()==1){
					rolelist.add(r);
				}
			}
		 } catch (Exception e) {
	            throw new ActionException(this.getClass(), e);
	      }
	}
	
	public String image()  {
		try {
			String id = getRequest().getParameter("id");  
	        CdUser user = (CdUser) this.sysDao.getServiceDo().find(CdUser.class,id);
	        FileInputStream hFile = new FileInputStream(user.getUserFilePath()); // 以byte流的方式打开文件 d:\1.gif   
	        int i=hFile.available(); //得到文件大小   
	        byte data[]=new byte[i];   
	        hFile.read(data);  //读数据   
	        getResponse().setContentType("image/*"); //设置返回的文件类型   
	        OutputStream toClient = getResponse().getOutputStream(); //得到向客户端输出二进制数据的对象   
	        toClient.write(data);  //输出数据             
	        toClient.flush();  
	        toClient.close();   
	        hFile.close(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
		
	/**
	 * 删除文件
	 * @param sPath
	 * @return
	 */
	public boolean deleteFile(String sPath) {  
	    boolean flag = false;  
	    File file = new File(sPath);  
	    // 路径为文件且不为空则进行删除  
	    if (file.isFile() && file.exists()) {  
	        file.delete();  
	        flag = true;  
	    }  
	    return flag;  
	}  
	
	/**
	 * 查询当前登录用户信息
	 * @return
	 */
	public String findCmmUser(){
		CdUser us= this.getSessionUser();
        if(us!=null){
   		   this.setJsonVo((CdUser) sysDao.getServiceDo().find(CdUser.class, us.getUserId()));
   	       return "jsonVo";
        }else{
        	this.newMsgJson(finalErrorMsg);
            return "json";
        }
	}
	
	/**
	 * 页面初始化
	 * @return
	 */
	public String findCmmInit(){
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			//职位
			List<PositionVo> lsPosition = this.sysDao.getPositionDao().findPosition();
			//权限
			List<RoleVo> rolelist = this.sysDao.getRoleDo().findRoleAll();
			//工作状态
			List<CdCode> lsWorkState = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_WORKSTATE, CommonEnable.QIYONG.getValue());
			//性别
			List<CdCode> lsSex = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_CODESEX, CommonEnable.QIYONG.getValue());
			map.put("posittion", lsPosition);
			map.put("role", rolelist);
			map.put("workState", lsWorkState);
			map.put("sex", lsSex);
			this.getJsons().setMap(map);
			return "json";
		}catch (Exception e){
			e.printStackTrace();
			new ActionException(this.getClass(), e.getMessage());
			this.newMsgJson(finalErrorMsg);
			return "json";
		}

	}

	/**
	 * 根据userId获取用户信息
	 * WangCheng
	 * @return
	 */
	public String findUserByUserId(){
		try {
			String userId = this.getRequest().getParameter("userId");
			CdUser cdUser = sysDao.getUserDo().findUser(userId);
			this.setJsonVo(cdUser);
		}catch (Exception e){
			e.printStackTrace();
		}
		return "jsonVo";
	}
	
	public CdUser getVo() {
		return vo;
	}

	public void setVo(CdUser vo) {
		this.vo = vo;
	}
	
	public String getUserPwdOk() {
		return userPwdOk;
	}

	public void setUserPwdOk(String userPwdOk) {
		this.userPwdOk = userPwdOk;
	}

	public UserVo getQvo() {
		return qvo;
	}

	public void setQvo(UserVo qvo) {
		this.qvo = qvo;
	}

	public List<CdPosition> getPostlist() {
		return postlist;
	}

	public void setPostlist(List<CdPosition> postlist) {
		this.postlist = postlist;
	}

	public List<CdDept> getCdDeptlist() {
		return cdDeptlist;
	}

	public void setCdDeptlist(List<CdDept> cdDeptlist) {
		this.cdDeptlist = cdDeptlist;
	}

	public List<CdRole> getRolelist() {
		return rolelist;
	}

	public void setRolelist(List<CdRole> rolelist) {
		this.rolelist = rolelist;
	}

	public String getUserNewPassword() {
		return userNewPassword;
	}

	public void setUserNewPassword(String userNewPassword) {
		this.userNewPassword = userNewPassword;
	}	

	public File getImage() {
		return image;
	}

	public void setImage(File image) {
		this.image = image;
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

	public String getImgSrc() {
		return imgSrc;
	}

	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}	

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	public ValidationAwareSupport getValidationAware() {
		return validationAware;
	}

	public String getUserOldPassword() {
		return userOldPassword;
	}

	public void setUserOldPassword(String userOldPassword) {
		this.userOldPassword = userOldPassword;
	}
	
	

}
