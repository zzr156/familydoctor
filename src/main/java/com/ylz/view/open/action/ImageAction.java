package com.ylz.view.open.action;

import com.ylz.bizDo.news.po.NewsTable;
import com.ylz.bizDo.news.po.NewsType;
import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.comEnum.CommonShortType;
import com.ylz.packcommon.common.exception.ActionException;
import com.ylz.packcommon.common.util.FileUtils;
import com.ylz.packcommon.common.util.PropertiesUtil;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import org.apache.commons.collections.map.HashedMap;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import javax.servlet.ServletOutputStream;
import java.io.*;
import java.util.*;

//import org.jbarcode.util.ImageUtil;
/**
 * 图片控制器
 * @author Administrator
 *
 */
@SuppressWarnings("all")
@Action(
		value="image", 
				results = {
					@Result(name = "index", location = "/open/index.jsp"),
					@Result(name = "invalid.token", location = "/open/login.jsp"),
					@Result(name = "json", type = "json",params={"root","jsons","contentType", "text/html"}),
					@Result(name = "jsonVo", type = "json",params={"root","jsonVo","contentType", "text/html"}),
					@Result(name = "pringOneBarCode", location = "/open/oneDimensioalCode/oneCode.jsp"),
					@Result(name = "resetPwd", location = "/open/resetUserPwd.jsp")
				}
)
public class ImageAction extends CommonAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private File image; //上传的文件
	private String imageFileName; //文件名称
	private String imageContentType; //文件类型
	private String imgSrc;

	public String forListOneCode(){
		return "pringOneBarCode";
	}
	
	/**
	 * 一维码生成规则
	 * @return
	 */
	public String getOneBarcode(){
		
        
		try {
			long time = Calendar.getInstance().getTime().getTime();
			//String encode = BarcodeUtil.generateBarCode128(String.valueOf(time),"0.5","10");
			Map parameter = new HashedMap();
	        parameter.put("ONE_CODE",String.valueOf(time));
	        List<JasperPrint> ls = new ArrayList<JasperPrint>();
			JasperDesign design = JRXmlLoader.load("D:\\card.jrxml");
			//编译报表模板
	        JasperReport jasperReport = JasperCompileManager.compileReport(design);
	        //填充报表
	        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, new JREmptyDataSource());
	        ls.add(jasperPrint);
	        byte[] bytes = new byte[500000];
            ServletOutputStream ouputStream = getResponse().getOutputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, ls);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
            exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
            exporter.exportReport();
            bytes = baos.toByteArray();
            getResponse().setContentType("application/pdf");
            baos.close();
            getResponse().setContentLength(bytes.length);
            ouputStream.write(bytes, 0, bytes.length);
            ouputStream.flush();
            ouputStream.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
		//this.setJsonVo(encode);
		return  null;
	}
	
	/**
	 * 图片添加
	 * @return
	 */
	public String addImage(){
		Map<String, String> map = new HashMap<String, String>();
		try {
			if (image != null) {
				String path = sysDao.getIoUtils().UpLoadFile(this.image,PropertiesUtil.getConfValue("filePicture")+ PropertiesUtil.getConfValue("filePictureImage"), getImageFileName());
				//String path = this.getSysDao().getIoUtils().pathUrl(PropertiesUtil.getConfValue("filePictureImage"),getImageFileName());
				map.put("filePath", path);
				map.put("fileName", this.getImageFileName());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		this.jsons.setVo(map);
		return "json";
	}


	/**
	 * 图片添加
	 * @return
	 */
	public String addImageBase64(){
		Map<String, String> map = new HashMap<String, String>();
		try {
			if (image != null) {
				String result = FileUtils.encodeBase64File(this.image);
//				String path = sysDao.getIoUtils().UpLoadFile(this.image,PropertiesUtil.getConfValue("filePicture")+ PropertiesUtil.getConfValue("filePictureImage"), getImageFileName());
				//String path = this.getSysDao().getIoUtils().pathUrl(PropertiesUtil.getConfValue("filePictureImage"),getImageFileName());
				Map<String,Object> mapImage = sysDao.getIoUtils().getCtyunOosSample(result, CommonShortType.YISHENG.getValue());
				map.put("filePath", mapImage.get("objectUrl").toString());
				map.put("fileName",mapImage.get("objectName").toString());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.jsons.setVo(map);
		return "json";
	}
	
	public String deltest(){
		try {
			String path = PropertiesUtil.getConfValue("deletePath");
			this.deleteFile(new File(path));
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public void deleteFile(File oldPath) {
		if (oldPath.isDirectory()) {
			System.out.println(oldPath + "是文件夹--");
			File[] files = oldPath.listFiles();
			for (File file : files) {
				deleteFile(file);
			}
		}else{
			oldPath.delete();
		}
	}

	public String appImage(){
		try {
			String imageUrl=this.getRequest().getParameter("imageUrl");
        	String gc = getRequest().getContextPath();
    		String ll=getRequest().getSession().getServletContext().getRealPath("").split(gc.substring(1, gc.length()))[0];//tomcat路径
			FileInputStream hFile = new FileInputStream(ll+imageUrl); // 以byte流的方式打开文件 d:\1.gif   
			int i = hFile.available(); //得到文件大小   
			byte data[] = new byte[i];
			hFile.read(data); //读数据   
			this.getResponse().setContentType("image/*"); //设置返回的文件类型   
			OutputStream toClient = this.getResponse().getOutputStream(); //得到向客户端输出二进制数据的对象   
			toClient.write(data); //输出数据             
			toClient.flush();
			toClient.close();
			hFile.close();
		} catch (Exception e) {
			e.printStackTrace();
			new ActionException(this.getClass(), e.getMessage());
		}
		return null;
	}


	public String imageNews() {
		try {
			NewsTable po = (NewsTable) sysDao.getServiceDo().find(NewsTable.class,this.getRequest().getParameter("id"));
	        if (po!=null&&po.getTableImageUrl()!=null) {
				FileInputStream hFile = new FileInputStream(po.getTableImageUrl()); // 以byte流的方式打开文件 d:\1.gif   
				int i = hFile.available(); //得到文件大小   
				byte data[] = new byte[i];
				hFile.read(data); //读数据   
				this.getResponse().setContentType("image/*"); //设置返回的文件类型   
				OutputStream toClient = this.getResponse().getOutputStream(); //得到向客户端输出二进制数据的对象   
				toClient.write(data); //输出数据             
				toClient.flush();
				toClient.close();
				hFile.close();
			} 
		} catch (Exception e) {
			e.printStackTrace();
			new ActionException(this.getClass(), e.getMessage());
		}
		return null;
	}
	public String typeImageNews() {
		try {
			NewsType po = (NewsType) sysDao.getServiceDo().find(NewsType.class,this.getRequest().getParameter("id"));
			if (po!=null&&po.getTypeImageUrl()!=null) {
				FileInputStream hFile = new FileInputStream(po.getTypeImageUrl()); // 以byte流的方式打开文件 d:\1.gif   
				int i = hFile.available(); //得到文件大小   
				byte data[] = new byte[i];
				hFile.read(data); //读数据   
				this.getResponse().setContentType("image/*"); //设置返回的文件类型   
				OutputStream toClient = this.getResponse().getOutputStream(); //得到向客户端输出二进制数据的对象   
				toClient.write(data); //输出数据             
				toClient.flush();
				toClient.close();
				hFile.close();
			} 
		} catch (Exception e) {
			e.printStackTrace();
			new ActionException(this.getClass(), e.getMessage());
		}
		return null;
	}
	
    public String getUrlImage()  {
        try {
            String url = getRequest().getParameter("url");
            FileInputStream hFile = new FileInputStream(url); // 以byte流的方式打开文件 d:\1.gif
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
    
    
}
