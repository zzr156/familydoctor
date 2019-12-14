package com.ylz.view.news.action;

import com.opensymphony.xwork2.ModelDriven;
import com.ylz.bizDo.cd.po.CdCode;
import com.ylz.bizDo.news.po.NewsType;
import com.ylz.bizDo.news.vo.NewsTypePoTop;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.CodeGroupConstrats;
import com.ylz.packcommon.common.comEnum.CommonEnable;
import com.ylz.packcommon.common.exception.ActionException;
import com.ylz.packcommon.common.util.PropertiesUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@SuppressWarnings("all")
@Action(
		value="newsType",
		results={
				@Result(name="list", location="/intercept/news/newsType/news_type_list.jsp"),
				@Result(name="edit", location="/intercept/news/newsType/news_type_edit.jsp"),
			    @Result(name = "json", type = "json",params={"root","jsons","contentType", "text/html"}),
				@Result(name = "jsontreelist", type = "json",params={"root","jsonList","contentType", "text/html"}),
				@Result(name = "jsonVo", type = "json",params={"root","jsonVo","contentType", "text/html"})
				}
)
public class NewsTypeAction extends CommonAction implements ModelDriven<NewsType> {

	@Override
	public NewsType getModel() {
		 if (null == vo) {
	            return vo = new NewsType();
	        }
	        return vo;
	}

	private NewsType vo;
	private List<NewsType> list;
	private String newsTypeAddzi;
	private File image; //上传的文件
	private String imageFileName; //文件名称
	private String imageContentType; //文件类型
	
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
			this.newNotPageJson(sysDao.getNewsTypeDao().findAllNewsType());
		}catch (Exception e){
			e.printStackTrace();
		}
		return "json";
	}
	/**
	 * 统一初始化
	 * @return
	 */
	public String findCmmInit(){
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			//状态
			List<CdCode> newType = this.getSysDao().getCodeDao().findGroupList(CodeGroupConstrats.GROUP_ENABLE, CommonEnable.QIYONG.getValue());
			map.put("newType", newType);
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
	 * 准备新增或者修改
	 * @return
	 */
	public String forAddOrEdit(){
		return "edit";
	}
	
	/**
	 * 查询单个类别
	 * @return
	 */
	public String jsonByOne(){
		String id = this.getRequest().getParameter("id");
		this.setJsonVo((NewsType) sysDao.getServiceDo().find(NewsType.class, id));
		return "jsonVo";
	}
	
	/**
	 * 查询树
	 * @return
	 */
	public String jsonTreelist() {
		try {
			List<NewsTypePoTop> ls = sysDao.getNewsTypeDao().findAllNewsTypeTop("1");
			this.setJsonList(ls);
		} catch (Exception e) {
			e.printStackTrace();
			new ActionException(this.getClass(), e.getMessage());
		}
		return "jsontreelist";
	}
	
	/**
	 * 添加
	 * @return
	 */
	public String add() {
		try {
			NewsType vo = (NewsType)getVoJson(NewsType.class);
			if (vo != null) {
	    		String dqNum = null;
	    		vo.setId(null);
	    		if (StringUtils.isNotBlank(vo.getTypePid())){
	    			dqNum = sysDao.getNewsTypeDao().findByIdTypeNum(vo.getTypePid());
	    		}else{
	    			vo.setTypePid(null);
	    		}
	    		//查询最大值
	    		String maxNum = sysDao.getNewsTypeDao().findMaxNum(vo.getTypePid());
	    		Integer num = null;
	    		//类型编码
	    		if(StringUtils.isBlank(maxNum)){
	    			num = Integer.parseInt(PropertiesUtil.getConfValue("typeNum"));
	    		}else{
	    			if(StringUtils.isBlank(vo.getTypePid())) {
                        num = Integer.parseInt(maxNum.substring(0,maxNum.length()))+1;
                    } else {
                        num = Integer.parseInt(maxNum.substring(maxNum.length()-2,maxNum.length()))+1;
                    }
	    		}
	    		if(StringUtils.isNotBlank(dqNum)){
	    			vo.setTypeNum(dqNum+String.format("%02d",num));
	    		}else{
	    			vo.setTypeNum(String.format("%02d",num));
	    		}
	    		sysDao.getServiceDo().add(vo);
	    		this.newMsgJson(finalSuccessrMsg);
			}else{
				this.newMsgJson(finalErrorMsg);
			}
		}  catch (Exception e) {
			e.printStackTrace();
			new ActionException(this.getClass(), e.getMessage());
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
			NewsType vo = (NewsType)getVoJson(NewsType.class);
			if (vo != null) {
				NewsType p = (NewsType)sysDao.getServiceDo().find(NewsType.class, vo.getId());
				vo.setTypeNum(p.getTypeNum());
				sysDao.getServiceDo().removePoFormSession(p);
			    sysDao.getServiceDo().modify(vo);
			    this.newMsgJson(finalSuccessrMsg);
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
			List<NewsTypePoTop> ls = sysDao.getNewsTypeDao().findTopToNewsType(id);
			if(ls != null && ls.size() >0){
				this.newMsgJson("请先删除子目录类型!");
			}else{
				sysDao.getServiceDo().delete(NewsType.class,id);
				this.newMsgJson(finalSuccessrMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
            new ActionException(this.getClass(),e.getMessage());
            this.newMsgJson(finalErrorMsg);
		}
        return "json";
	}
	
	public NewsType getVo() {
		return vo;
	}
	public void setVo(NewsType vo) {
		this.vo = vo;
	}
	public List<NewsType> getList() {
		return list;
	}
	public void setList(List<NewsType> list) {
		this.list = list;
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

	public String getNewsTypeAddzi() {
		return newsTypeAddzi;
	}

	public void setNewsTypeAddzi(String newsTypeAddzi) {
		this.newsTypeAddzi = newsTypeAddzi;
	}
	

}
