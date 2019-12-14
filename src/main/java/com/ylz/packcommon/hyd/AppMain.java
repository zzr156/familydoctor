package com.ylz.packcommon.hyd;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * demo
 */
public class AppMain extends Base {

/*    private static String appid = "117588a63134444a83c15379c21xxaex";
    private static String encodingAesKey = "11CAkiNxfkHQcK26nCTBNw==";
    private static String token = "ylz_test";*/

    public static void main(String[] args) {
        String url="/api/user/async";
        //  url=HEAD_URL_SERVER+url; // 正式库
           url=HEAD_URL_TEST+url; //   测试库
        //url=HEAD_URL_LOCAL+url; // 本机测试
        String content = "";
        Map<String,String> testParam=new HashMap<String, String>();
        //openId 标识
//        testParam.put("openId","oMRyi0XoIv2z07P12qeFWeox515U");
//        testParam.put("password","123456");
        //username 手机号 标识
       /* testUser.put("openId","18250868281");
        testParam.put("username","18250868281");
        testParam.put("password","123456");
        testParam.put("nickname","测试hello");*/

       // identity 身份证
        testParam.put("openId","31863680-2062-415e-9107-f8ff679e0ca1 ");
        testParam.put("identity","350205199110170013");
        testParam.put("username","15980990371");
        testParam.put("name","丁伟森");
        content= JSON.toJSONString(testParam);
        excute(url,content);

    }


}

