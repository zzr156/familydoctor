package com.ylz.view.open.action;


import com.ylz.packaccede.common.action.CommonAction;
import com.ylz.packcommon.common.util.PropertiesUtil;
import org.apache.struts2.convention.annotation.Action;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

@Action(value = "download"
)
public class DownloadAction extends CommonAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7921397834313851009L;
	
	private String path;//存储路径
	private String url;//访问url
	private String type;//访问类型
	private String id;//id
	
	public String execute(){
		if (this.getAct().equalsIgnoreCase("ueditor")) {
            return ueditor();// 准备添加
        }
		if (this.getAct().equalsIgnoreCase("display")) {
            return display();
        }
		return super.execute();
	}
	
	public String ueditor(){
		try {
			path = PropertiesUtil.getConfValue("ueditorPath");
			if(url!=null&&path!=null){
				path = path+url;
				File dir = new File(path);
				if(dir.exists()){
					FileInputStream hFile = new FileInputStream(path); // 以byte流的方式打开文件 d:\1.gif   
			        int i=hFile.available(); //得到文件大小   
			        byte data[]=new byte[i];   
			        hFile.read(data);  //读数据   
			       // getResponse().setContentType("image/*"); //设置返回的文件类型   
			        OutputStream toClient = getResponse().getOutputStream(); //得到向客户端输出二进制数据的对象   
			        toClient.write(data);  //输出数据             
			        toClient.flush();  
			        toClient.close();   
			        hFile.close(); 
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String display(){
		try {
			if(url!=null){
				String surl= new String(url.toString().getBytes("ISO-8859-1"), "utf-8");
				File dir = new File(surl);
				if(dir.exists()){
					FileInputStream hFile = new FileInputStream(surl); // 以byte流的方式打开文件 d:\1.gif   
			        int i=hFile.available(); //得到文件大小   
			        byte data[]=new byte[i];   
			        hFile.read(data);  //读数据   
			       // getResponse().setContentType("image/*"); //设置返回的文件类型   
			        OutputStream toClient = getResponse().getOutputStream(); //得到向客户端输出二进制数据的对象   
			        toClient.write(data);  //输出数据             
			        toClient.flush();  
			        toClient.close();   
			        hFile.close(); 
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
