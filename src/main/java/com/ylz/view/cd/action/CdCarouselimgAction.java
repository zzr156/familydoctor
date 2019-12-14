package com.ylz.view.cd.action;


/**
 * 轮播图片管理
 */

import com.ylz.bizDo.cd.po.CdCarouselimg;
import com.ylz.bizDo.cd.vo.CdCarouselimgQvo;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.exception.ActionException;
import com.ylz.packcommon.common.exception.DaoException;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Action(
		value="carousel",
		results={
				@Result(name="list", location="/intercept/cd/carousel/img_list.jsp"),
				@Result(name="edit", location="/intercept/cd/carousel/img_edit.jsp"),
				@Result(name = "json", type = "json", params = { "root", "jsons",
						"excludeProperties", "rows.*\\.user" ,"contentType", "text/html"}),
				@Result(name = "jsontreelist", type = "json",params={"root","jsonList","contentType", "text/html"})
		}
)
public class CdCarouselimgAction extends CommonAction {
	

	private static final long serialVersionUID = 1L;
	private List<CdCarouselimg> list;
	private CdCarouselimg vo;
    private CdCarouselimgQvo qvo;
    private File uploadImg; //上传的文件
    private String uploadImgFileName; //文件名称
    private String uploadImgContentType; //文件类型


	/**
	 * 查询全部
	 * @return
	 */
	public String list() {
		try{
			CdCarouselimgQvo qvo = (CdCarouselimgQvo)getQvoJson(CdCarouselimgQvo.class);
			List<CdCarouselimg> ls = sysDao.getCdCarouselimgDao().findEmperorCarouselimgList(qvo);
			jsons.setRowsQvo(ls,qvo);
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
		try {
			String id = this.getRequest().getParameter("id");
			sysDao.getServiceDo().delete(CdCarouselimg.class,id);
		} catch (DaoException e) {
			e.printStackTrace();
			new ActionException(this.getClass(), e.getMessage());
			this.newMsgJson(finalErrorMsg);
			return "json";
		}
		this.newMsgJson(finalSuccessrMsg);
		return "json";
	}
	
	/**
	 * 查询全部
	 * @return
	 */
	public String forList() {
		try {
//			list=sysDao.getCodeDao().findAll();
		} catch (Exception e) {
			e.printStackTrace();
			new ActionException(this.getClass(), e.getMessage());
			this.newMsgJson(finalErrorMsg);
			return "json";
		}
		return "list";
	}
	/**
	 * 准备新增
	 */
	public String forAddOrEdit(){
		return "edit";
	}
	/**
	 * 添加
	 * @return
	 */
	public String add()throws Exception{
		try {
			if(isvers("addTime")) {
                return this.list();
            }
			CdCarouselimg vo = (CdCarouselimg)getVoJson(CdCarouselimg.class);
			Calendar calendar=Calendar.getInstance();
			calendar.setTime(new Date());
			vo.setCjsj(calendar);
           // String carpath = sysDao.getIoUtils().UpLoadFile(this.getUploadImg(), PropertiesUtil.getConfValue("upfile"), getUploadImgFileName());
           // if (uploadImg!=null){
           //     vo.setImgUrl(carpath);
           //}
			sysDao.getServiceDo().add(vo);
		} catch (DaoException e) {
			e.printStackTrace();
			new ActionException(this.getClass(), e.getMessage());
			this.newMsgJson(finalErrorMsg);
			return "json";
		}
		this.newMsgJson(finalSuccessrMsg);
		return "json";
	}


    public List<CdCarouselimg> getList() {
        return list;
    }

    public void setList(List<CdCarouselimg> list) {
        this.list = list;
    }

    public CdCarouselimg getVo() {
        return vo;
    }

    public void setVo(CdCarouselimg vo) {
        this.vo = vo;
    }

    public CdCarouselimgQvo getQvo() {
        return qvo;
    }

    public void setQvo(CdCarouselimgQvo qvo) {
        this.qvo = qvo;
    }

    public File getUploadImg() {
        return uploadImg;
    }

    public void setUploadImg(File uploadImg) {
        this.uploadImg = uploadImg;
    }

    public String getUploadImgFileName() {
        return uploadImgFileName;
    }

    public void setUploadImgFileName(String uploadImgFileName) {
        this.uploadImgFileName = uploadImgFileName;
    }

    public String getUploadImgContentType() {
        return uploadImgContentType;
    }

    public void setUploadImgContentType(String uploadImgContentType) {
        this.uploadImgContentType = uploadImgContentType;
    }
}