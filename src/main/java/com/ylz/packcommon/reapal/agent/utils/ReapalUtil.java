package com.ylz.packcommon.reapal.agent.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.*;

public class ReapalUtil {

	protected static ResourceBundle resourceBundle = ResourceBundle.getBundle("application");
	protected static String privateKey = resourceBundle.getString("privateKey");// 私钥
	protected static String password = resourceBundle.getString("password");// 密码
	protected static String key = resourceBundle.getString("user_key");// 用户key
	protected static String merchant_id = resourceBundle.getString("merchant_ID");// 商户ID
	protected static String pubKeyUrl = resourceBundle.getString("pubKeyUrl");// 公钥
	protected static String url = resourceBundle.getString("url");
	protected static String notify_url = resourceBundle.getString("notify_url");// 回调地址
	protected static String version = resourceBundle.getString("version");// 版本
	protected static String charset = resourceBundle.getString("charset");// 编码
	protected static String sign_type = resourceBundle.getString("sign_type");// 签名方式，暂时仅支持MD5
	

	public static String getKey() {

		return key;
	}
	public static String getUrl(){
		return url;
	}
	public static String getNotify_url(){
		return notify_url;
	}
	
	 public static String getPrivateKey() {
		return privateKey;
	}
	public static void setPrivateKey(String privateKey) {
		ReapalUtil.privateKey = privateKey;
	}
	public static String getPassword() {
		return password;
	}
	public static void setPassword(String password) {
		ReapalUtil.password = password;
	}
	public static String getMerchant_id() {
		return merchant_id;
	}
	public static void setMerchant_id(String merchant_id) {
		ReapalUtil.merchant_id = merchant_id;
	}
	public static String getPubKeyUrl() {
		return pubKeyUrl;
	}
	public static void setPubKeyUrl(String pubKeyUrl) {
		ReapalUtil.pubKeyUrl = pubKeyUrl;
	}
	public static void setUrl(String url) {
		ReapalUtil.url = url;
	}
	public static void setNotify_url(String notify_url) {
		ReapalUtil.notify_url = notify_url;
	}
	
	public static String getVersion() {
		return version;
	}
	public static void setVersion(String version) {
		ReapalUtil.version = version;
	}
	public static String getCharset() {
		return charset;
	}
	public static void setCharset(String charset) {
		ReapalUtil.charset = charset;
	}
	
	public static String getSign_type() {
		return sign_type;
	}
	public static void setSign_type(String sign_type) {
		ReapalUtil.sign_type = sign_type;
	}
	/**
     * 参数加密
     * @return
     */
    public static Map<String, String> addkey(String json)throws Exception{

        System.out.println("数据=============>" + json);

        //商户获取融宝公钥
        PublicKey pubKeyFromCrt = RSA.getPubKeyFromCRT(pubKeyUrl);
        //随机生成16数字
        String key = RandomUtil.getRandom(16);

        System.out.println("key=============>" + key);
        //对随机数进行加密
        String encryptkey = RSA.encrypt(key, pubKeyFromCrt);
        String encryData = AES.encryptToBase64(json, key);

        System.out.println("密文key============>" + encryptkey);
        System.out.println("密文数据===========>" + encryData);

        Map<String, String> map = new HashMap<String, String>();
        map.put("data", encryData);
		map.put("encryptkey", encryptkey);

        return map;
    }
    


    /**
     * 解密
     * @param post
     * @return
     * @throws Exception
     */
    public static String pubkey(String post)throws Exception{

        System.out.println("密文================>" + post);
        // 将返回的json串转换为map

        TreeMap<String, String> map = JSON.parseObject(post,
                new TypeReference<TreeMap<String, String>>() {
                });
        String encryptkey = map.get("encryptkey");
        String data = map.get("data");
        
        System.out.println("输出data  = ="+data);

        //获取自己私钥解密
        PrivateKey pvkformPfx = RSA.getPvkformPfx(privateKey, ReapalUtil.getPassword());
        String decryptData = RSA.decrypt(encryptkey, pvkformPfx);

        post = AES.decryptFromBase64(data, decryptData);

        System.out.println("明文================>" + post);


        return post;
    }
    /**
     * 解密
     * @param post
     * @return
     * @throws Exception
     */
    public static String pubkey(String encryptkey,String data)throws Exception{
    	String post;
    	System.out.println("encryptkey================>" + encryptkey);
    	System.out.println("密文================>" + data);
        //获取自己私钥解密
        PrivateKey pvkformPfx = RSA.getPvkformPfx(privateKey, ReapalUtil.getPassword());
        String decryptData = RSA.decrypt(encryptkey, pvkformPfx);

        post = AES.decryptFromBase64(data, decryptData);

        System.out.println("明文================>" + post);


        return post;
    }
    
    public static String getMapOrderStr(Map<String,Object> request){
        List<String> fieldNames = new ArrayList<String>(request.keySet());
        Collections.sort(fieldNames);
        StringBuffer buf = new StringBuffer();
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = String.valueOf(request.get(fieldName));
            if ((fieldValue != null) && (fieldValue.length() > 0)){
                buf.append(fieldName+"="+fieldValue+"&");
            }
        }
        if(buf.length()>1) {
            buf.setLength(buf.length()-1);
        }
        return buf.toString(); //去掉最后&

    }



    /**
     * 生成订单号
     * @return
     */
    public static String no(){
        String code = "10" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + "01" ;
        return code;
    }

    /**
     *
     * @param sArray
     * @param key
     * @return
     */
    public static String BuildMysign(Map sArray, String key) {
        if(sArray!=null && sArray.size()>0){
            StringBuilder prestr = CreateLinkString(sArray);
            System.out.println("prestr====>" + prestr);
            //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
            return Md5Encrypt.md5(prestr.toString()+key,ReapalUtil.getCharset());
        }
        return null;
    }

    /**
     * 拼装参数
     * @param params
     * @return
     */
    public static StringBuilder CreateLinkString(Map params){
        List keys = new ArrayList(params.keySet());
        Collections.sort(keys);
        StringBuilder prestr = new StringBuilder();
        String key="";
        String value="";
        for (int i = 0; i < keys.size(); i++) {
            key=(String) keys.get(i);
            value = (String) params.get(key);
            if("".equals(value) || value == null ||
                    key.equalsIgnoreCase("sign") || key.equalsIgnoreCase("sign_type")){
                continue;
            }
            prestr.append(key).append("=").append(value).append("&");
        }
        return prestr.deleteCharAt(prestr.length()-1);
    }
    
    public static void main(String[] arg0) throws Exception{
    	ReapalUtil ru = new ReapalUtil();
    	Map<String, String> map = ru.addkey("wo ai beijing tian an men");
    	String anwer = ru.pubkey(JSON.toJSONString(map));
    }
}
