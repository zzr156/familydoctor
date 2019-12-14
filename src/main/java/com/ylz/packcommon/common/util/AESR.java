package com.ylz.packcommon.common.util;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class AESR {

    public static void main(String args[]) throws Exception{
        System.out.println(System.getProperty("file.encoding"));

        //加密内容
        String content = "{\"idCard\": \"350422200202023432\",\"name\":\"张三}";
        //String content = "test123456";
        //为与Delphi编码统一，将字符转为UTF8编码（其他语言也相同）
        //String ss=new String(content.getBytes(),"UTF-8");
        //密钥
        String password = "ylzdocortest@)!*)!";
        System.out.println("加密前：" + content);
        byte[] encryptResult = encrypt(content, password,16); //16位密钥长度128位、24位密钥长度192、32位密钥长度256（在delphi中对应kb128、kb192、快播56）
        //System.out.println("加密后：" + parseByte2HexStr(encryptResult));//将加密后编码二进制转为16进制编码
        System.out.println(Base64Utils.encode(encryptResult));//二进制转Hbase64

        byte[] decryptResult = decrypt(encryptResult,password,16);
        System.out.println("解密后：" + new String(decryptResult));
    }


    /**
     * 加密
     *
     * @param content 需要加密的内容
     * @param password  加密密码
     * @param keySize 密钥长度16,24,32
     * @return
     * @throws UnsupportedEncodingException
     * @throws InvalidAlgorithmParameterException
     */

    public static byte[] encrypt(String content, String password, int keySize) throws UnsupportedEncodingException, InvalidAlgorithmParameterException {
        try {
            //密钥长度不够用0补齐。
            SecretKeySpec key = new SecretKeySpec(ZeroPadding(password.getBytes(), keySize), "AES");
            //定义加密算法AES、算法模式ECB、补码方式PKCS5Padding
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            //定义加密算法AES 算法模式CBC、补码方式PKCS5Padding
//            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            //CBC模式模式下初始向量 不足16位用0补齐
//            IvParameterSpec iv = new IvParameterSpec(ZeroPadding("1234567890123456".getBytes(),16));
            byte[] byteContent = content.getBytes("utf-8");
            //初始化加密
            //ECB
            cipher.init(Cipher.ENCRYPT_MODE, key);
            //CBC
//            cipher.init(Cipher.ENCRYPT_MODE, key,iv);
            byte[] result = cipher.doFinal(byteContent);
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**解密
     * @param content  待解密内容
     * @param password 解密密钥
     * @param keySize 密钥长度16,24,32
     * @return
     * @throws InvalidAlgorithmParameterException
     */
    public static byte[] decrypt(byte[] content, String password, int keySize) throws InvalidAlgorithmParameterException {
        try {
            //密钥长度不够用0补齐。
            SecretKeySpec key = new SecretKeySpec(ZeroPadding(password.getBytes(), keySize), "AES");
            //定义加密算法AES、算法模式ECB、补码方式PKCS5Padding
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            //定义加密算法AES 算法模式CBC、补码方式PKCS5Padding
//            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            //CBC模式模式下初始向量 不足16位用0补齐
//            IvParameterSpec iv = new IvParameterSpec(ZeroPadding("1234567890123456".getBytes(),16));
            // 初始化解密
            //ECB
            cipher.init(Cipher.DECRYPT_MODE, key);
            //CBC
//            cipher.init(Cipher.DECRYPT_MODE, key,iv);

            byte[] result = cipher.doFinal(content);
            return result;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 加密 base64
     * @param content
     * @param password
     * @param keySize
     * @return
     * @throws UnsupportedEncodingException
     * @throws InvalidAlgorithmParameterException
     */
    public static String base64Encrypt(String content, String password, int keySize) throws Exception {
        byte[] encryptResult = AESR.encrypt(content, password,keySize);
        return Base64Utils.encode(encryptResult);
    }

    /**
     * 解密 base64
     * @param content
     * @param password
     * @param keySize
     * @return
     * @throws Exception
     */
    public static String base64Decryp(String content, String password, int keySize) throws Exception {
        try {
            System.out.println(System.getProperty("file.encoding"));
            String c=new String(content.getBytes(),"UTF-8");
            byte[] decryptResult = AESR.decrypt(Base64Utils.decode(c), password,keySize);
            return new String(decryptResult,"UTF-8");
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }

    /**将二进制转换成16进制
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    /**将16进制转换为二进制
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        System.out.println();
        if(hexStr.length() < 1) {
            return null;
        }
        byte[] result = new byte[hexStr.length()/2];
        for (int i = 0;i< hexStr.length()/2; i++) {
            int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
            int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }

    public static byte[] ZeroPadding(byte[] in,Integer blockSize){
        Integer copyLen = in.length;
        if (copyLen > blockSize) {
            copyLen = blockSize;
        }
        byte[] out = new byte[blockSize];
        System.arraycopy(in, 0, out, 0, copyLen);
        return out;
    }

}