package com.ylz.packcommon.common.util;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;

/**
 * 编码工具类
 * 实现aes加密、解密
 */
public class EncryptUtils {

    /**
     * 密钥
     */
    private static final String KEY = "be365753edd6fb36";

    /**
     * 算法
     */
    private static final String ALGORITHMSTR = "AES/ECB/PKCS5Padding";

    public static void main(String[] args) throws Exception {
//        String content = "{\"id\":\"48d14f17-6392-4757-8ab6-fc96c2502520\",\"drName\":\"小小\",\"drImageurl\":\"d://system//file////2017/06/16////5550920170616.jpg\",\"teamState\":\"\",\"teamWorkType\":\"\",\"drHospName\":\"35f75957-1792-4697-817b-47d5613f45cd\",\"drGood\":\"看病\",\"drIntro\":\"的嘎嘎额\",\"zxCount\":\"0\",\"sfCount\":\"0\",\"dyyCount\":\"0\",\"jkjyCount\":\"0\",\"jkzdCount\":\"0\",\"drLabel\":\"\"}";
//        System.out.println("加密前：" + content);
//
//        System.out.println("加密密钥和解密密钥：" + KEY);
//
//        String encrypt = aesEncrypt(content, KEY);
//        System.out.println("加密后：" + encrypt);
        String s="q+6EENzF3Lj8mfaWNd9yDQHjM3ivsTwuYcrtTRXqb/qw/JR5cmiO50TpFj1mL/twUztSEwrxbgzb2p/OHNvoxO6dr7mbLJaQnKIZtKy+M0H374kFgCRqDecAEM/OslMQF6fImat772sJ6kpC9myyxmaany1P10n8hpvdbo74jWc3ZqkSMkiDzv3gs86HjTZDJbsvJWql+jbrd26PQ4I9pIeuu7VZ8y4GbbNPeB+3dTsz8gi4ylxTp5DqCeqR71sK0mU5gcdEksyqKI5MgEOs/hyNeZ/HvoOy8Ygfgp/xYkUz8oYTYofFl+jCVwYjDWbAnrGvux5LbXSIVKsGNlHNtO9MlMV5eX+823ACJHgRBEi4qvgXZ/vFYRIBUTgiad5WVx2YjPivtrwD1i5Oh0g87e+cXD8GvLVB0fWQtG21JuhFyEPDYRRF6wTN/WQKY37RKmjcWG+7uV0ci7iyED/LtA==";
        String e="q+6EENzF3Lj8mfaWNd9yDQHjM3ivsTwuYcrtTRXqb\\/qw\\/JR5cmiO50TpFj1mL\\/twUztSEwrxbgzb2p\\/OHNvoxO6dr7mbLJaQnKIZtKy+M0H374kFgCRqDecAEM\\/OslMQF6fImat772sJ6kpC9myyxmaany1P10n8hpvdbo74jWc3ZqkSMkiDzv3gs86HjTZDJbsvJWql+jbrd26PQ4I9pIeuu7VZ8y4GbbNPeB+3dTsz8gi4ylxTp5DqCeqR71sK0mU5gcdEksyqKI5MgEOs\\/hyNeZ\\/HvoOy8Ygfgp\\/xYkUz8oYTYofFl+jCVwYjDWbAnrGvux5LbXSIVKsGNlHNtO9MlMV5eX+823ACJHgRBEi4qvgXZ\\/vFYRIBUTgiad5WVx2YjPivtrwD1i5Oh0g87e+cXD8GvLVB0fWQtG21JuhFyEPDYRRF6wTN\\/WQKY37RKmjcWG+7uV0ci7iyED\\/LtA==";

        String decrypt = aesDecrypt(e.replace("\\",""), "be365753edd6fb36");
        System.out.println("解密后：" + decrypt);
// a;
//       String str = "admin" + ExtendDate.getYMD_h_m_s(Calendar.getInstance());
//        System.out.println( Md5Util.MD516(str));;
    }

    /**
     * aes解密
     * @param encrypt   内容
     * @return
     * @throws Exception
     */
    public static String aesDecrypt(String encrypt) throws Exception {
        return aesDecrypt(encrypt, KEY);
    }

    /**
     * aes加密
     * @param content
     * @return
     * @throws Exception
     */
    public static String aesEncrypt(String content) throws Exception {
        return aesEncrypt(content, KEY);
    }

    /**
     * 将byte[]转为各种进制的字符串
     * @param bytes byte[]
     * @param radix 可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制
     * @return 转换后的字符串
     */
    public static String binary(byte[] bytes, int radix){
        return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数
    }

    /**
     * base 64 encode
     * @param bytes 待编码的byte[]
     * @return 编码后的base 64 code
     */
    public static String base64Encode(byte[] bytes){
        return Base64.encodeBase64String(bytes);
    }

    /**
     * base 64 decode
     * @param base64Code 待解码的base 64 code
     * @return 解码后的byte[]
     * @throws Exception
     */
    public static byte[] base64Decode(String base64Code) throws Exception{
        return StringUtils.isEmpty(base64Code) ? null : new BASE64Decoder().decodeBuffer(base64Code);
    }


    /**
     * AES加密
     * @param content 待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的byte[]
     * @throws Exception
     */
    public static byte[] aesEncryptToBytes(String content, String encryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);
        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(encryptKey.getBytes(), "AES"));

        return cipher.doFinal(content.getBytes("utf-8"));
    }


    /**
     * AES加密为base 64 code
     * @param content 待加密的内容
     * @param encryptKey 加密密钥
     * @return 加密后的base 64 code
     * @throws Exception
     */
    public static String aesEncrypt(String content, String encryptKey) throws Exception {
        return base64Encode(aesEncryptToBytes(content, encryptKey));
    }

    /**
     * AES解密
     * @param encryptBytes 待解密的byte[]
     * @param decryptKey 解密密钥
     * @return 解密后的String
     * @throws Exception
     */
    public static String aesDecryptByBytes(byte[] encryptBytes, String decryptKey) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        kgen.init(128);

        Cipher cipher = Cipher.getInstance(ALGORITHMSTR);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(decryptKey.getBytes(), "AES"));
        byte[] decryptBytes = cipher.doFinal(encryptBytes);

        return new String(decryptBytes);
    }


    /**
     * 将base 64 code AES解密
     * @param encryptStr 待解密的base 64 code
     * @param decryptKey 解密密钥
     * @return 解密后的string
     * @throws Exception
     */
    public static String aesDecrypt(String encryptStr, String decryptKey) throws Exception {
        return StringUtils.isEmpty(encryptStr) ? null : aesDecryptByBytes(base64Decode(encryptStr), decryptKey);
    }

}