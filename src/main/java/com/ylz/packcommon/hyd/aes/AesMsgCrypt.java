package com.ylz.packcommon.hyd.aes;

import com.ylz.packcommon.hyd.exception.BusinessException;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Random;

/**
 * 使用微信的AES开发包，进行消息体的加解密
 */
public class AesMsgCrypt implements IMsgCrypt {

    static Charset CHARSET = Charset.forName("utf-8");

    private final byte[] aesKey;

    private final String appId;

    public AesMsgCrypt(String appId, String encodingAesKey) {
        this.aesKey = Base64.decodeBase64(encodingAesKey);
        this.appId = appId;
    }

    @Override
    public String encrypt(String msg) {
        String base64Encrypted = null;
        String randomStr = getRandomStr();
        ByteGroup byteCollector = new ByteGroup();
        byte[] randomStrBytes = randomStr.getBytes(CHARSET);
        byte[] textBytes = msg.getBytes(CHARSET);
        byte[] networkBytesOrder = getNetworkBytesOrder(textBytes.length);
        byte[] appidBytes = appId.getBytes(CHARSET);

        byteCollector.addBytes(randomStrBytes);
        byteCollector.addBytes(networkBytesOrder);
        byteCollector.addBytes(textBytes);
        byteCollector.addBytes(appidBytes);

        // ... + pad: 使用自定义的填充方式对明文进行补位填充
        byte[] padBytes = PKCS7Encoder.encode(byteCollector.size());
        byteCollector.addBytes(padBytes);

        // 获得最终的字节流, 未加密
        byte[] unencrypted = byteCollector.toBytes();

        try {
            // 设置加密模式为AES的CBC模式
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec keySpec = new SecretKeySpec(aesKey, "AES");
            IvParameterSpec iv = new IvParameterSpec(aesKey, 0, 16);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);

            // 加密
            byte[] encrypted = cipher.doFinal(unencrypted);

            // 使用BASE64对加密后的字符串进行编码
            base64Encrypted = Base64.encodeBase64String(encrypted);
        } catch (Exception e) {
         e.printStackTrace();
        }
        return base64Encrypted;
    }

    @Override
    public String decrypt(String encryptMsg) {
        byte[] original;
        String content, from_appid;
        try {
            // 设置解密模式为AES的CBC模式
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
            SecretKeySpec key_spec = new SecretKeySpec(aesKey, "AES");
            IvParameterSpec iv = new IvParameterSpec(Arrays.copyOfRange(aesKey, 0, 16));
            cipher.init(Cipher.DECRYPT_MODE, key_spec, iv);

            // 使用BASE64对密文进行解码
            byte[] encrypted = Base64.decodeBase64(encryptMsg);
            // 解密
            original = cipher.doFinal(encrypted);

            // 去除补位字符
            byte[] bytes = PKCS7Encoder.decode(original);

            // 分离16位随机字符串,网络字节序和AppId
            byte[] networkOrder = Arrays.copyOfRange(bytes, 16, 20);

            int contentLength = recoverNetworkBytesOrder(networkOrder);
            if(contentLength > bytes.length){
                throw new BusinessException("数据格式有误，丢失数据标识位");
            }
            content = new String(Arrays.copyOfRange(bytes, 20, 20 + contentLength), CHARSET);
            from_appid = new String(Arrays.copyOfRange(bytes, 20 + contentLength, bytes.length),
                    CHARSET);
        } catch (Exception e) {
            if(e instanceof BusinessException){
                throw (BusinessException)e;
            }
            throw new BusinessException("数据格式有误，解密失败");
        }
        // appid不相同的情况
        if (!from_appid.equals(appId)) {
            throw new BusinessException("无效appId");
        }
        return content;
    }

    // 随机生成16位字符串
    private String getRandomStr() {
        String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 16; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    // 生成4个字节的网络字节序
    private byte[] getNetworkBytesOrder(int sourceNumber) {
        byte[] orderBytes = new byte[4];
        orderBytes[3] = (byte) (sourceNumber & 0xFF);
        orderBytes[2] = (byte) (sourceNumber >> 8 & 0xFF);
        orderBytes[1] = (byte) (sourceNumber >> 16 & 0xFF);
        orderBytes[0] = (byte) (sourceNumber >> 24 & 0xFF);
        return orderBytes;
    }

    // 还原4个字节的网络字节序
    int recoverNetworkBytesOrder(byte[] orderBytes) {
        int sourceNumber = 0;
        for (int i = 0; i < 4; i++) {
            sourceNumber <<= 8;
            sourceNumber |= orderBytes[i] & 0xff;
        }
        return sourceNumber;
    }
}
