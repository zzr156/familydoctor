package com.ylz.view.cd.action;

import com.ylz.bizDo.cd.entity.AddressTreeEntity;
import com.ylz.bizDo.cd.entity.AddressTreeHospEntity;
import com.ylz.bizDo.cd.po.CdAddress;
import com.ylz.bizDo.cd.vo.CdAddressSvo;
import com.ylz.bizDo.cd.vo.CdAddressVo;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.exception.ActionException;
import com.ylz.packcommon.common.util.AreaUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.util.List;

/**
 * 行政区
 */
@SuppressWarnings("all")
@Action(
		value="address",
		results={
				@Result(name="list", location="/intercept/cd/address/address_list.jsp"),
				@Result(name="edit", location="/intercept/cd/address/address_edit.jsp"),
				@Result(name = "jsonVo", type = "json",params={"root","jsonVo","contentType", "text/html"}),
			    @Result(name = "json", type = "json",params={"root","jsons","contentType", "text/html"}),
                @Result(name = "jsontreelist", type = "json",params={"root","jsonList","contentType", "text/html"}),
				}
)
public class CdAddressAction extends CommonAction {

	
	private List<CdAddress> list;
	private CdAddressVo qvo;
	private CdAddress vo;
	private String addrAddzi;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * 查询部门树形结构
	 * @return
	 */
	public String jsonTreelist() {
		try {
			//String pid = this.getRequest().getParameter("addrId");
			String id = this.getRequest().getParameter("id");
			List<AddressTreeEntity> ls = sysDao.getCdAddressDao().findTreeSetting(id);
			//CdAddressVo qvo = (CdAddressVo)getQvoJson(CdAddressVo.class);
			//List<CdAddressSvo> ls = sysDao.getCdAddressDao().findList(qvo);
			this.setJsonList(ls);
		} catch (Exception e) {
			e.printStackTrace();
			new ActionException(this.getClass(), e.getMessage());
		}
		return "jsontreelist";
	}


	/**
	 * 查询服务套餐部门树形结构
	 * @return
	 */
	public String jsonServicePackage() {
		try {
			//String pid = this.getRequest().getParameter("addrId");
			String id = this.getRequest().getParameter("id");
			if(StringUtils.isNotBlank(id)){
				if(AreaUtils.getAreaCode(id).length() == 6){
					List<AddressTreeHospEntity> ls = sysDao.getAppHospDeptDao().findTreeSetting(id);
					this.setJsonList(ls);
				}else{
					List<AddressTreeEntity> ls = sysDao.getCdAddressDao().findTreeSetting(id);
					this.setJsonList(ls);
				}
			}else{
				List<AddressTreeEntity> ls = sysDao.getCdAddressDao().findTreeSetting(id);
				this.setJsonList(ls);
			}


			//CdAddressVo qvo = (CdAddressVo)getQvoJson(CdAddressVo.class);
			//List<CdAddressSvo> ls = sysDao.getCdAddressDao().findList(qvo);

		} catch (Exception e) {
			e.printStackTrace();
			new ActionException(this.getClass(), e.getMessage());
		}
		return "jsontreelist";
	}

	/**
	 * 查询数据
	 * @return
	 */
	public String findCmmTreelist() {
		try {
			CdAddressVo qvo = (CdAddressVo)getQvoJson(CdAddressVo.class);
			List<CdAddressSvo> ls = sysDao.getCdAddressDao().findList(qvo);
			this.setJsonList(ls);
		} catch (Exception e) {
			e.printStackTrace();
			new ActionException(this.getClass(), e.getMessage());
		}
		return "jsontreelist";
	}

	/**
	 * 查询各级行政区划数据
	 * @return
	 */
	public String jsonList(){
		try{
			String num = this.getRequest().getParameter("num");
			this.setJsonList(this.getSysDao().getCdAddressDao().findGroupList(num));
			return "jsontreelist";
		}catch (Exception e){
			e.printStackTrace();
			new ActionException(this.getClass(),e.getMessage());
			this.newMsgJson(finalErrorMsg);
			return "json";
		}
	}
	/**
	 * 根据父id查找子类数据
	 * @return
	 */
	public String findCmmByPid(){
		try {
			String pid = this.getRequest().getParameter("pid");
			String id = this.getRequest().getParameter("id");
			if(StringUtils.isNotBlank(id)){
				if(id.equals("drHospId")){
					if(StringUtils.isNotBlank(pid)) {
						this.setJsonList(this.getSysDao().getAppHospDeptDao().findByAreaCodeLike(pid));
					}
				}else {
					this.setJsonList(this.getSysDao().getCdAddressDao().findByPidList(pid));
				}
			}else{
				this.setJsonList(this.getSysDao().getCdAddressDao().findByPidList(pid));
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return "jsontreelist";
	}
	/**
	 * 根据条件查询
	 * @return
	 */
	public String forList() {
		return "list";
	}
	
	/**
	 * 准备新增或修改
	 * @return
	 */
	public String forAddOrEdit(){
		return "edit";
	}
	/**
	 * 修改数据查询
	 */
	public String jsonByOne(){
		   String id = this.getRequest().getParameter("id");
		   this.setJsonVo((CdAddress) sysDao.getServiceDo().find(CdAddress.class, id));
	       return "jsonVo";
	}
	/**
	 * 添加
	 * @return
	 */
	public String addOrEdit(){
		 try {
			 CdAddress vo = (CdAddress)getVoJson(CdAddress.class);
			// vo.setBaseid((String) this.getRequest().getSession().getAttribute(Constant.ORG_ID));
			 if(vo!=null){
				 CdAddress cdAddress = (CdAddress) this.sysDao.getServiceDo().find(CdAddress.class,vo.getId());
				 if(cdAddress!=null){
					 //session出现实体重复时的处理
					 cdAddress.setAreaAlias(vo.getAreaAlias());
					 cdAddress.setAreaName(vo.getAreaName());
					 cdAddress.setAreaSname(vo.getAreaSname());
					 cdAddress.setCtcode(vo.getCtcode());
					 this.sysDao.getServiceDo().removePoFormSession(vo);
					 this.sysDao.getServiceDo().modify(cdAddress);
					 this.newMsgJson(this.finalSuccessrMsg);
				 }else{
					 CdAddress findIsSector = this.sysDao.getCdAddressDao().findIsCdAddress(vo.getCtcode(), vo.getId());
					 if(findIsSector!=null){
						 this.newMsgJson("此编号已经存在,请重新输入!");
						 return "json";
					 }
					 if(StringUtils.isBlank(vo.getPid())){
						 vo.setPid(null);
					 }
					 this.sysDao.getServiceDo().add(vo);
					 this.newMsgJson(this.finalSuccessrMsg);
				 }



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
	 * 删除
	 * @return
	 */
	public String delete() {
		try {
			String id = this.getRequest().getParameter("id");
			List<CdAddressSvo> ls = sysDao.getCdAddressDao().findById(id);
			if(ls != null && ls.size() >0){
				this.newMsgJson("请先删除子目录类型!");
			}else{
				sysDao.getServiceDo().delete(CdAddress.class,id);
				this.newMsgJson(finalSuccessrMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(finalErrorMsg);
		}
        return "json";
	}
	
	public CdAddress getVo() {
		return vo;
	}
	public void setVo(CdAddress vo) {
		this.vo = vo;
	}

	public CdAddressVo getQvo() {
		return qvo;
	}

	public void setQvo(CdAddressVo qvo) {
		this.qvo = qvo;
	}

	public String getAddrAddzi() {
		return addrAddzi;
	}

	public void setAddrAddzi(String addrAddzi) {
		this.addrAddzi = addrAddzi;
	}
	
	
}
