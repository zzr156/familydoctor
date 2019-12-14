package com.ylz.packaccede.util;

import com.ylz.packcommon.common.util.AesEncrypt;
import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;

/**
 * Created by hzk on 17-3-15.
 */
public class SecUtil {
    private static byte[] keybytes = { 0x31, 0x32, 0x33, 0x34, 0x35, 0x50,0x37, 0x38, 0x39, 0x40, 0x41, 0x42, 0x43, 0x44, 0x45, 0x46 };
        public static void main(String[] args) throws Exception {

            String e1 = encrypt("ylz@hl#2019");
            System.out.println(e1);
            String e2 = decrypt("8622f0f77516e26e350ab549071ac99a");
            System.out.println(e2);
        }
        //四舍五入把double转化int整型，0.5进一，小于0.5不进一
        public static int getInt(double number){
            BigDecimal bd=new BigDecimal(number).setScale(0, BigDecimal.ROUND_HALF_UP);
            return Integer.parseInt(bd.toString());
        }

        //四舍五入把double转化为int类型整数,0.5也舍去,0.51进一
        public static int DoubleFormatInt(Double dou){
            DecimalFormat df = new DecimalFormat("######0"); //四色五入转换成整数
            return Integer.parseInt(df.format(dou));
        }

        //去掉小数凑整:不管小数是多少，都进一
        public static int ceilInt(double number){
            return (int) Math.ceil(number);
        }

        //测试
       /* public static void main(String[] args) {

            System.out.println("getInt============="+getInt(20.5));
            System.out.println("DoubleFormatInt=========="+DoubleFormatInt(20.5));
            System.out.println("ceilInt================="+ceilInt(20.01));

        }*/

        /**
         * 加密
         * @param value
         * @return
         */
        public static String encrypt(String value) {
            String s=null;
            int mode = Cipher.ENCRYPT_MODE;
            try {
            Cipher cipher = initCipher(mode);
            byte[] outBytes = cipher.doFinal(value.getBytes());
            s = String.valueOf(Hex.encodeHex(outBytes));
            } catch (Exception e) {
            e.printStackTrace();
            }
            return s;
        }
        /**
         * 解密
         * @param value
         * @return
         */
        public static String decrypt(String value) {
            String s = null;
            int mode = Cipher.DECRYPT_MODE;
            try {
                Cipher cipher = initCipher(mode);
                byte[] outBytes = cipher.doFinal(Hex.decodeHex(value.toCharArray()));
                s = new String(outBytes);
            } catch (Exception e) {
            e.printStackTrace();
            }
            return s;
        }

        private static Cipher initCipher(int mode) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            Key key = new SecretKeySpec(keybytes, "AES");
            cipher.init(mode, key);
            return cipher;
        }
}
