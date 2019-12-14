package com.ylz.packcommon.common.wechat.menu;

import com.ylz.packcommon.common.wechat.menu.button.ClickButton;
import com.ylz.packcommon.common.wechat.menu.button.ComplexButton;
import com.ylz.packcommon.common.wechat.menu.button.ViewButton;
import com.ylz.packcommon.common.wechat.menu.button.base.Button;
import com.ylz.packcommon.common.wechat.pojo.Token;
import com.ylz.packcommon.common.wechatUtil.CommonUtil;
import com.ylz.packcommon.common.wechatUtil.MenuUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * 菜单管理器类
 * 
 * @author xyy
 * @date 2014-10-14
 * 
 */
public class MenuManager {
	private static Logger log = LoggerFactory.getLogger(MenuManager.class);

	/**
	 * 定义菜单结构
	 * 
	 * @return
	 */
	private static Menu getMenu() {
		
		Menu menu = new Menu();
		Properties p = new Properties();
		try {
			//加载菜单配置文件
			p.load(MenuManager.class.getResourceAsStream("/menu.properties"));
			List<Object> clb = new ArrayList<Object>();
			//3*5菜单
			for(int i=1;i<=3;i++){
				String km = "km"+i;
				//只有父菜单
				if(p.getProperty(km)!=null){
					String type=km+"type";
					if(p.getProperty(type)!=null&&p.getProperty(km)!=null&&p.getProperty(km+"key")!=null&&"click".equals(p.getProperty(type))){
						ClickButton btn = new ClickButton();
						btn.setName(p.getProperty(km));
						btn.setType("click");
						btn.setKey(p.getProperty(km+"key"));
						clb.add(btn);
					}else if(p.getProperty(type)!=null&&p.getProperty(km)!=null&&p.getProperty(km+"url")!=null&&"view".equals(p.getProperty(type))){
						ViewButton btn = new ViewButton();
						btn.setName(p.getProperty(km));
						btn.setType("view");
						btn.setUrl(p.getProperty(km+"url"));
						clb.add(btn);
					}
				}
				//有子菜单
				else{
					ComplexButton mainBtn = null;
					List<Button> lb = new ArrayList<Button>();
					for(int j=1;j<=5;j++){
						String m="m"+i+j;
						String type=m+"type";
						if(p.getProperty(type)!=null&&p.getProperty(m)!=null&&p.getProperty(m+"key")!=null&&"click".equals(p.getProperty(type))){
							ClickButton btn = new ClickButton();
							btn.setName(p.getProperty(m));
							btn.setType("click");
							btn.setKey(p.getProperty(m+"key"));
							lb.add(btn);
						}else if(p.getProperty(type)!=null&&p.getProperty(m)!=null&&p.getProperty(m+"url")!=null&&"view".equals(p.getProperty(type))){
							ViewButton btn = new ViewButton();
							btn.setName(p.getProperty(m));
							btn.setType("view");
							btn.setUrl(p.getProperty(m+"url"));
							lb.add(btn);
						}
					}
					if(lb!=null&&lb.size()>0&&p.getProperty("m"+i)!=null){
						mainBtn = new ComplexButton();
						mainBtn.setName(p.getProperty("m"+i));
						mainBtn.setSub_button(lb);
						clb.add(mainBtn);
					}
				}
			}
			if(clb!=null&&clb.size()>0){
				menu.setButton(clb);
			}
			return menu;
		} catch (IOException e) {
			log.error(new Date()+"获取菜单配置文件出错！");
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args) {
		// 第三方用户唯一凭证,第三方用户唯一凭证密钥
		String appId = "wx37113c70cb08cbe4";//APPID
		String appSecret = "31679cf412ab5a337e96a8c65cb4eeec";//APPSECRET
//		String appId = "wx2098dd437b6c296b";//APPID 
//		String appSecret = "e6554a9f7b16a2628d5dc3b51c482665";//APPSECRET
		// 调用接口获取凭证
		Token token = CommonUtil.getToken(appId, appSecret);
		
		if (null != token) {
			// 创建菜单
			boolean result = MenuUtil.createMenu(getMenu(), token.getAccessToken());

			// 判断菜单创建结果
			if (result) {
                log.warn(new Date()+"-菜单创建成功！");
            } else {
                log.warn(new Date()+"-菜单创建失败！");
            }
		}
	}
}
