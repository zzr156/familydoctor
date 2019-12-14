package com.ylz.view.cd.action;


import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.cd.po.CdDept;
import com.ylz.bizDo.cd.po.CdRole;
import com.ylz.bizDo.cd.po.CdUser;
import com.ylz.bizDo.cd.vo.CdAddressSvo;
import com.ylz.bizDo.cd.vo.DeptQvo;
import com.ylz.bizDo.cd.vo.DeptSvo;
import com.ylz.bizDo.cd.vo.RoleVo;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.exception.ActionException;
import com.ylz.packcommon.common.util.PropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("all")
@Action(
		value="dept",
		results={
				@Result(name="list", location="/intercept/cd/dept/dept_list.jsp"),
				@Result(name="edit", location="/intercept/cd/dept/dept_edit.jsp"),
				@Result(name = "jsonVo", type = "json",params={"root","jsonVo",
		        		"excludeProperties","sid,slist,roles,user","contentType", "text/html"}),
			    @Result(name = "json", type = "json",params={"root","jsons",
                        "excludeProperties","rows.*\\.slist,rows.*\\.roles,rows.*\\.user,rows.*\\.children,rows.*\\.sid","contentType", "text/html"}),
				@Result(name = "jsontreelist", type = "json",params={"root","jsonList",
						"excludeProperties",".*\\.slist,.*\\.roles,.*\\.user","contentType", "text/html"})
				}
)
public class CdDeptAction extends CommonAction {
	

	
	private List<CdDept> list;
	
	private CdDept vo;
	private DeptQvo qvo = new DeptQvo();
	private String delId;//获取页面传过来的Id;
	private List<CdRole> rolelist;
	private String deptAddzi;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public String jsonByOne(){
		   String id = this.getRequest().getParameter("id");
		   this.setJsonVo((CdDept) sysDao.getServiceDo().find(CdDept.class, id));
	       return "jsonVo";
	}
	/**
	 * 查询部门树形结构
	 * @return
	 */
	public String jsonTreelist() {
		try{
//	   this.setJsonList(this.sysDao.getCdDeptDao().findByState());
			this.setJsonList(this.sysDao.getCdDeptDao().findSByState());
		}catch (Exception e){
			new ActionException(getClass(),getAct(),getJsons(),e);
		}
       return "jsontreelist";
	}
	/**
	 * 查询机构
	 * @return
	 */
	public String jsonCmmList(){
		try{
			CdUser us = this.getSessionUser();
			if(us!=null){
				CdDept dept = (CdDept) sysDao.getServiceDo().find(CdDept.class, us.getCdDeptId());
				if(dept!=null){
					//当当前用户所在部门是医院时返回所在顶级机构id和名称
					if(dept.getUnitType().equals("1")){
						if(dept.getDeptType().equals("0")){
							List<DeptSvo> deptt = this.sysDao.getCdDeptDao().findById(us.getCdDeptId());
							this.setJsonVo(deptt);
							return "jsonVo";
						}else{
							//orgId(dept.getSid().getId());
							this.setJsonVo(orgId(dept.getSid().getId()));
							return "jsonVo";
						}
					}else{
						//当当前用户为疾控时返回所有单位类型为医院的机构
						this.setJsonList(this.sysDao.getCdDeptDao().findByType("0"));
						return "jsontreelist";
					}
				}else{
					this.newMsgJson("送检单位查询失败!");
					return "json";
				}
			}else{
				this.newMsgJson("送检单位查询失败!");
				return "json";
			}
		}catch (Exception e){
			e.printStackTrace();
			new ActionException(this.getClass(),e.getMessage());
			this.newMsgJson(this.finalErrorMsg);
			return "json";
		}

	}
  	public List<DeptSvo> orgId(String id){
		try{
			List<DeptSvo> depte = this.sysDao.getCdDeptDao().findById(id);
			if(depte!=null){
				if(depte.get(0).getDeptType().equals("0")){
					//List<DeptSvo> dept=(List<DeptSvo>) depte.get(0);
					return depte;
				}else{
					return orgId(depte.get(0).getId());
				}
			}else{
				return depte;
			}
		}catch (Exception e){
			new ActionException(getClass(),getAct(),getJsons(),e);
		}
		return null;
  	}
	
	/**
	 * 根据条件查询
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
	 	try{
			this.getJsons().setRows(this.getSysDao().getCdDeptDao().findAll());
		}catch (Exception e){
			e.printStackTrace();
			new ActionException(this.getClass(),e.getMessage());
			this.newMsgJson(this.finalErrorMsg);
		}
        return "json";
    }

    /**
	 * 批量删除
	 * @return
	 */
	public String delete() {
		 try
	        {
	            String id = this.getRequest().getParameter("id");
	            //如果角色在用,不能删除
	            CdDept find = (CdDept) sysDao.getServiceDo().find(CdDept.class,id);
	            if(find.getUser().size()<1){//角色没有使用此部门
	                List<CdDept> se = this.sysDao.getCdDeptDao().finRM(find.getId());
	                if(se!=null){
	                    this.newMsgJson("请先删除子部门");
	                    return "json";
	                }else{
	                    this.sysDao.getServiceDo().delete(CdDept.class,id);//删除
	                }
	            }else{
	                this.newMsgJson("该部门有人员,不允许删除!");
	                return "json";
	            }
	        }catch (Exception e){
	            e.printStackTrace();
	            new ActionException(this.getClass(),e.getMessage());
	            this.newMsgJson("删除失败");
	            return "json";
	        }
	        this.newMsgJson(finalSuccessrMsg);
	        return "json";
	}
	/**
	 * 准备添加或修改
	 * @return
	 */
	public String forAddOrEdit(){
		return "edit";
	}		
	/**
	 * 修改
	 * @return
	 */
	public String modify() {
		try {
			CdDept vo = (CdDept)getVoJson(CdDept.class);
			//编号为空 自动生成
			if(!StringUtils.isNotBlank(vo.getSnumber())){
				String dqNum = null;
				if(StringUtils.isNotBlank(vo.getSid().getId())){
					dqNum = sysDao.getCdDeptDao().findByIdTypeNum(vo.getSid().getId());
				}else{
					vo.setSid(null);
				}
				//查询最大值
				String maxNum = sysDao.getCdDeptDao().findMaxNum(vo.getSid().getId());
				Integer num = null;
				//类型编码
				if(StringUtils.isBlank(maxNum)){
					num = Integer.parseInt(PropertiesUtil.getConfValue("typeNum"));
				}else{
					if(StringUtils.isBlank(vo.getSid().toString())) {
						num = Integer.parseInt(maxNum.substring(0, maxNum.length())) + 1;
					} else {
						num = Integer.parseInt(maxNum.substring(maxNum.length() - 2, maxNum.length())) + 1;
					}
				}
				if(StringUtils.isNotBlank(dqNum)){
					vo.setSnumber(dqNum+String.format("%02d",num));
				}else{
					vo.setSnumber(String.format("%02d",num));
				}
			}
            /*CdDept findIsSector = this.sysDao.getCdDeptDao().findIsCdDept(vo.getSnumber(), vo.getId(),vo.getDeptType());
            if(findIsSector!=null){
                this.newMsgJson("此编号已经存在,请重新输入!");
                return "json";
            }*/
           /* if(vo.getDeptType().equals("1")){
            	CdDept depte = (CdDept) this.sysDao.getServiceDo().find(CdDept.class,vo.getSid().getId());
            	if(depte!=null){
            		vo.setArea(depte.getArea());
            		vo.setCity(depte.getCity());
            		vo.setUnitType(depte.getUnitType());
            	}else{
            		this.newMsgJson("没有找到所属机构或部门!");
	                return "json";
            	}

            }*/
            if(StringUtils.isBlank(vo.getSid().getId())){
	        	  vo.setSid(null);
	          }
            CdDept dept = (CdDept) this.sysDao.getServiceDo().find(CdDept.class,vo.getId());
            vo.setCjrId(dept.getCjrId());
            vo.setCjdwId(dept.getCjdwId());
            CdUser us= this.getSessionUser();
            if(us!=null){
	        	vo.setXgrId(us.getUserId());//修改人id
	        	if(us.getCdDept()!=null){
		        	vo.setXgdwId(us.getCdDept().getId());//修改人单位id
	        	}
	        }
            //session出现实体重复时的处理
			this.sysDao.getServiceDo().removePoFormSession(dept);
            this.sysDao.getServiceDo().modify(vo);
        }catch (Exception e){
            e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(finalErrorMsg);
            return "json";
        }
        this.newMsgJson(finalSuccessrMsg);
        return "json";
	}
	
	/**
	 * 添加
	 * @return
	 */
	public String add(){
		 try {
			 String handle = this.getRequest().getParameter("handle");
			 CdDept vo = (CdDept)getVoJson(CdDept.class);
			//System.out.println(vo.getSid().getId());
			 if(vo!=null){
			 	//编号为空 自动生成
			 	if(!StringUtils.isNotBlank(vo.getSnumber())){
					String dqNum = null;
					if(StringUtils.isNotBlank(vo.getSid().getId())){
						dqNum = sysDao.getCdDeptDao().findByIdTypeNum(vo.getSid().getId());
					}else{
						vo.setSid(null);
					}
					//查询最大值
					String maxNum=null;
					if(vo.getSid()!=null){
						maxNum = sysDao.getCdDeptDao().findMaxNum(vo.getSid().getId());
					}

					Integer num = null;
					//类型编码
					if(StringUtils.isBlank(maxNum)){
						num = Integer.parseInt(PropertiesUtil.getConfValue("typeNum"));
					}else{
						if(StringUtils.isBlank(vo.getSid().toString())) {
							num = Integer.parseInt(maxNum.substring(0, maxNum.length())) + 1;
						} else {
							num = Integer.parseInt(maxNum.substring(maxNum.length() - 2, maxNum.length())) + 1;
						}
					}
					if(StringUtils.isNotBlank(dqNum)){
						vo.setSnumber(dqNum+String.format("%02d",num));
					}else{
						vo.setSnumber(String.format("%02d",num));
					}
				}
                //查询编号
				 /*CdDept findIsSector = this.sysDao.getCdDeptDao().findIsCdDept(vo.getSnumber(), vo.getId(),vo.getDeptType());
		          if(findIsSector!=null){
	                 this.newMsgJson("此编号已经存在,请重新输入!");
	                 return "json";
	             }*/
		        /*  if(handle.equals("addzi")){
		        	 CdDept dept = (CdDept) this.sysDao.getServiceDo().find(CdDept.class,vo.getSid().getId());
		        	  if(dept!=null){
		        		 vo.setArea(dept.getArea());
		        		  vo.setCity(dept.getCity());
		        		 vo.setDeptType("1");
		        		  vo.setUnitType(dept.getUnitType());
		        	  }else{
		        		  this.newMsgJson("没有找到所属机构或部门!");
		                  return "json";
		        	  }
		         }*/
		          if(vo.getDeptType().equals("1")){
		        	  if(StringUtils.isBlank(vo.getParnentId())){
		        		  this.newMsgJson("部门要选择所属单位!");
		                  return "json";
		        	  }
		          }
		          if(StringUtils.isBlank(vo.getSid().getId())){
		        	  vo.setSid(null);
		          }
		          CdUser us= this.getSessionUser();
		          if(us!=null){
		        	  vo.setCjrId(us.getUserId());//创建人id
		        	  vo.setXgrId(us.getUserId());//修改人id
		        	  if(us.getCdDept()!=null){
		        		 vo.setCjdwId(us.getCdDept().getId());//创建人单位id
		        		 vo.setXgdwId(us.getCdDept().getId());//修改人单位id
		        	  }
		          }
	             this.sysDao.getServiceDo().add(vo);
	             this.newMsgJson(this.finalSuccessrMsg);
			 }else{
				 this.newMsgJson(finalErrorMsg);
			 }
			 
         }catch (Exception e){
             e.printStackTrace();
             new ActionException(this.getClass(),e.getMessage());
             this.newMsgJson(finalErrorMsg);
             return "json";
         }
         return "json";
	}
	

	/**
	 * 页面初始化
	 * @return
	 */
	public String findCmmInit(){
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			//权限
			List<RoleVo> rolelist = this.sysDao.getRoleDo().findRoleAll();
			//部门类别
			List<CdCode> lsDeptType = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_DEPTTYPE, CommonEnable.QIYONG.getValue());
			//单位类型
			List<CdCode> lsUnitType = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_UNITTYPE, CommonEnable.QIYONG.getValue());
			//状态
			List<CdCode> lsEnable = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_ENABLE, CommonEnable.QIYONG.getValue());
			//城市
			List<CdAddressSvo> lsAddress = sysDao.getCdAddressDao().findByNum();
			map.put("role", rolelist);
			map.put("deptType", lsDeptType);
			map.put("unitType", lsUnitType);
			map.put("enable", lsEnable);
			map.put("city", lsAddress);
			this.getJsons().setMap(map);
		}catch (Exception e){
			e.printStackTrace();
			new ActionException(this.getClass(),e.getMessage());
			this.newMsgJson(this.finalErrorMsg);
		}
		return "json";
	}
	
	public List<CdRole> getRolelist() {
		return rolelist;
	}

	public void setRolelist(List<CdRole> rolelist) {
		this.rolelist = rolelist;
	}

	public String getDelId() {
		return delId;
	}

	public void setDelId(String delId) {
		this.delId = delId;
	}

	public CdDept getVo() {
		return vo;
	}

	public void setVo(CdDept vo) {
		this.vo = vo;
	}

	public List<CdDept> getList() {
		return list;
	}

	public DeptQvo getQvo() {
		return qvo;
	}

	public void setQvo(DeptQvo qvo) {
		this.qvo = qvo;
	}

	public void setList(List<CdDept> list) {
		this.list = list;
	}
	public String getDeptAddzi() {
		return deptAddzi;
	}
	public void setDeptAddzi(String deptAddzi) {
		this.deptAddzi = deptAddzi;
	}
	
	
	
	
}
