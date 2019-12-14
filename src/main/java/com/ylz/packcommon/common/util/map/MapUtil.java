package com.ylz.packcommon.common.util.map;


import com.ylz.packcommon.common.util.HtmlUtils;
import com.ylz.packcommon.common.util.JsonUtil;
import org.apache.http.NameValuePair;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapUtil {
	public static MapVo getCoordinateByAddr(String keywords) throws Exception {
		try {
			Map<String,Object> map = new HashMap<>();
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("keywords",keywords));
			params.add(new BasicNameValuePair("key","8325164e247e15eea68b59e89200988b"));
			CloseableHttpClient client = HttpClients.createDefault();
			String result = HtmlUtils.getInstance().executeResponse(client, "post", "https://restapi.amap.com/v3/place/text", params,"utf-8");
			MapList mapList = JsonUtil.fromJson(result, MapList.class);
			if(mapList!=null&&mapList.getPois()!=null&&mapList.getPois().size()>0){
				MapVo vo= mapList.getPois().get(0);
				MapVo mapVo=new MapVo();
				mapVo.setName(vo.getName());
				mapVo.setLocation(vo.getLocation());
				mapVo.setxAxis(vo.getLocation().split(",")[0]);
				mapVo.setyAxis(vo.getLocation().split(",")[1]);
				return mapVo;
			}else{
				return null;
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String args[]) throws Exception {
		MapUtil util=new MapUtil();
		String addr="福建省厦门市软件园二期观日路16号";
		MapVo vo=util.getCoordinateByAddr(addr);
		System.out.println("X轴:"+vo.getxAxis());
		System.out.println("Y轴:"+vo.getyAxis());
	}

}
