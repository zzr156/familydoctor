package com.ylz.packcommon.common.util;

import com.thoughtworks.xstream.XStream;
import com.ylz.bizDo.app.vo.AppAgreementQvo;
import com.ylz.bizDo.jtapp.commonEntity.AppMessageEntityXml;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by asus on 2018/11/07.
 */
public class MessageXmlUtil {
    /**
     * xml转为Map集合
     * @author Administrator
     *
     */
    public static Map<String,String> strXmlToMap(String strXml) throws Exception{
        Map<String,String> map =new HashMap<String,String>();
        Document docment = DocumentHelper.parseText(strXml);
        Element root=docment.getRootElement();
        List<Element> list =root.elements();
        for(Element ele:list) {
            if(ele.elements().size()>=1){
                List<Element> ls = ele.elements();
                if(ls != null && ls.size() >0){
                    for(Element eles:ls) {
                        map.put(eles.getName(), eles.getText());
                    }
                }
            }else{
                map.put(ele.getName(), ele.getText());
            }
        }

        return map;
    }


    public static void main(String[] args) {
        try{
//            AppMessageEntityXml
            String str1 = "<?xml version=\"1.0\" encoding=\"gbk\" ?><response><code>03</code><message><desmobile>15980990371</desmobile><msgid>f803a8ea1588611a7100</msgid></message></response>\n";
            System.out.println(str1);
//            Map<String,String> map2 = MessageXmlUtil.strXmlToMap(str1);
//            System.out.println(JsonUtil.toJson(map2));
            XStream xstream =new XStream();
            xstream.alias("response", AppMessageEntityXml.class);
            AppMessageEntityXml xml = (AppMessageEntityXml) xstream.fromXML(str1);
            System.out.println(JsonUtil.toJson(xml));

        }catch (Exception e){
            e.printStackTrace();
        }
    }



}
