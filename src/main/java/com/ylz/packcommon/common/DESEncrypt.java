package com.ylz.packcommon.common;

import org.springframework.util.Base64Utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

/**
 *
 * @author Administrator
 *
 */
public class DESEncrypt {

	/**
	 * 加密
	 *
	 * @param key
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String encode(String key, String data) {

		try {
			DESKeySpec dks = new DESKeySpec(key.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			// key的长度不能够小于8位字
			Key secretKey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec(key.getBytes());// 向量
			AlgorithmParameterSpec paramSpec = iv;
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, paramSpec);
			byte[] bytes = cipher.doFinal(data.getBytes("utf-8"));
			return Base64Utils.encodeToString(bytes);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 解密
	 *
	 * @param key
	 * @param xml
	 * @return
	 * @throws Exception
	 */
	public static byte[] decode(String key, byte[] data) throws Exception {
		try {
			DESKeySpec dks = new DESKeySpec(key.getBytes());
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			// key的长度不能够小于8位字�?
			Key secretKey = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec(key.getBytes());
			AlgorithmParameterSpec paramSpec = iv;
			cipher.init(Cipher.DECRYPT_MODE, secretKey, paramSpec);
			byte[] bs = cipher.doFinal(data);
			return bs;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}

	/**
	 * 获取编码后的�?utf-8)
	 *
	 * @param key
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String decodeValueUTf8(String key, String data) {
		byte[] datas;
		String value = null;
		try {
			data = data.replaceAll(" ", "+");
			datas = decode(key, Base64Utils.decodeFromString(data));
			value = new String(datas, "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return value;
	}

	public static void main(String[] args) throws Exception {
		String jiami = encode("12345678", "entName=深圳市腾讯计算机系统有限公司");

		System.out.println(jiami);
	}

}
