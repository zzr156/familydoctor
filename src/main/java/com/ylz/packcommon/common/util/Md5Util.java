package com.ylz.packcommon.common.util;

import com.ylz.packaccede.util.CardUtil;
import com.ylzinfo.ehc.portal.sdk.EhcPortalClient;
import org.apache.commons.collections.map.HashedMap;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public class Md5Util
{
     /* 下面这些S11-S44实际上是一个4*4的矩阵，在原始的C实现中是用#define 实现的，
     这里把它们实现成为static final是表示了只读，切能在同一个进程空间内的多个


     Instance间共享*/
        static final int S11 = 7;
        static final int S12 = 12;
        static final int S13 = 17;
        static final int S14 = 22;

        static final int S21 = 5;
        static final int S22 = 9;
        static final int S23 = 14;
        static final int S24 = 20;

        static final int S31 = 4;
        static final int S32 = 11;
        static final int S33 = 16;
        static final int S34 = 23;

        static final int S41 = 6;
        static final int S42 = 10;
        static final int S43 = 15;
        static final int S44 = 21;

        static final byte[] PADDING = { -128, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
        /* 下面的三个成员是MD5计算过程中用到的3个核心数据，在原始的C实现中


           被定义到MD5_CTX结构中



         */
        private long[] state = new long[4];  // state (ABCD)
        private long[] count = new long[2];  // number of bits, modulo 2^64 (lsb first)
        private byte[] buffer = new byte[64]; // input buffer

     /* digestHexStr是MD5的唯一一个公共成员，是最新一次计算结果的
     　 16进制ASCII表示.
     */
        public String digestHexStr;

        /* digest,是最新一次计算结果的2进制内部表示，表示128bit的MD5值.
     */
        private byte[] digest = new byte[16];

     /*
       getMD5ofStr是类MD5最主要的公共方法，入口参数是你想要进行MD5变换的字符串
       返回的是变换完的结果，这个结果是从公共成员digestHexStr取得的．
     */
        public String getMD5ofStr(String inbuf) {
                md5Init();
                md5Update(inbuf.getBytes(), inbuf.length());
                md5Final();
                digestHexStr = "";
                for (int i = 0; i < 16; i++) {
                        digestHexStr += byteHEX(digest[i]);
                }
                return digestHexStr;

        }
        // 这是MD5这个类的标准构造函数，JavaBean要求有一个public的并且没有参数的构造函数


        public Md5Util() {
                md5Init();

                return;
        }



        /* md5Init是一个初始化函数，初始化核心变量，装入标准的幻数 */
        private void md5Init() {
                count[0] = 0L;
                count[1] = 0L;
                ///* Load magic initialization constants.

                state[0] = 0x67452301L;
                state[1] = 0xefcdab89L;
                state[2] = 0x98badcfeL;
                state[3] = 0x10325476L;

                return;
        }
        /* F, G, H ,I 是4个基本的MD5函数，在原始的MD5的C实现中，由于它们是


        简单的位运算，可能出于效率的考虑把它们实现成了宏，在java中，我们把它们


     　　实现成了private方法，名字保持了原来C中的。 */

        private long F(long x, long y, long z) {
                return (x & y) | ((~x) & z);

        }
        private long G(long x, long y, long z) {
                return (x & z) | (y & (~z));

        }
        private long H(long x, long y, long z) {
                return x ^ y ^ z;
        }

        private long I(long x, long y, long z) {
                return y ^ (x | (~z));
        }

       /*
          FF,GG,HH和II将调用F,G,H,I进行近一步变换


          FF, GG, HH, and II transformations for rounds 1, 2, 3, and 4.
          Rotation is separate from addition to prevent recomputation.
       */

        private long FF(long a, long b, long c, long d, long x, long s,
                long ac) {
                a += F (b, c, d) + x + ac;
                a = ((int) a << s) | ((int) a >>> (32 - s));
                a += b;
                return a;
        }

        private long GG(long a, long b, long c, long d, long x, long s,
                long ac) {
                a += G (b, c, d) + x + ac;
                a = ((int) a << s) | ((int) a >>> (32 - s));
                a += b;
                return a;
        }
        private long HH(long a, long b, long c, long d, long x, long s,
                long ac) {
                a += H (b, c, d) + x + ac;
                a = ((int) a << s) | ((int) a >>> (32 - s));
                a += b;
                return a;
        }
        private long II(long a, long b, long c, long d, long x, long s,
                long ac) {
                a += I (b, c, d) + x + ac;
                a = ((int) a << s) | ((int) a >>> (32 - s));
                a += b;
                return a;
        }
        /*
         md5Update是MD5的主计算过程，inbuf是要变换的字节串，inputlen是长度，这个
         函数由getMD5ofStr调用，调用之前需要调用md5init，因此把它设计成private的


        */
        private void md5Update(byte[] inbuf, int inputLen) {

                int i, index, partLen;
                byte[] block = new byte[64];
                index = (int)(count[0] >>> 3) & 0x3F;
                // /* Update number of bits */
                if ((count[0] += (inputLen << 3)) < (inputLen << 3)) {
                    count[1]++;
                }
                count[1] += (inputLen >>> 29);

                partLen = 64 - index;

                // Transform as many times as possible.
                if (inputLen >= partLen) {
                        md5Memcpy(buffer, inbuf, index, 0, partLen);
                        md5Transform(buffer);

                        for (i = partLen; i + 63 < inputLen; i += 64) {

                                md5Memcpy(block, inbuf, 0, i, 64);
                                md5Transform (block);
                        }
                        index = 0;

                } else

                {
                    i = 0;
                }

                ///* Buffer remaining input */
                md5Memcpy(buffer, inbuf, index, i, inputLen - i);

        }

        /*
          md5Final整理和填写输出结果


        */
        private void md5Final () {
                byte[] bits = new byte[8];
                int index, padLen;

                ///* Save number of bits */
                Encode (bits, count, 8);

                ///* Pad out to 56 mod 64.
                index = (int)(count[0] >>> 3) & 0x3f;
                padLen = (index < 56) ? (56 - index) : (120 - index);
                md5Update (PADDING, padLen);

                ///* Append length (before padding) */
                md5Update(bits, 8);

                ///* Store state in digest */
                Encode (digest, state, 16);

        }

        /* md5Memcpy是一个内部使用的byte数组的块拷贝函数，从input的inpos开始把len长度的


　　　　　 字节拷贝到output的outpos位置开始


        */

        private void md5Memcpy (byte[] output, byte[] input,
                int outpos, int inpos, int len)
        {
                int i;

                for (i = 0; i < len; i++) {
                    output[outpos + i] = input[inpos + i];
                }
        }

        /*
           md5Transform是MD5核心变换程序，有md5Update调用，block是分块的原始字节
        */
        private void md5Transform (byte block[]) {
                long a = state[0], b = state[1], c = state[2], d = state[3];
                long[] x = new long[16];

                Decode (x, block, 64);

                /* Round 1 */
                a = FF (a, b, c, d, x[0], S11, 0xd76aa478L); /* 1 */
                d = FF (d, a, b, c, x[1], S12, 0xe8c7b756L); /* 2 */
                c = FF (c, d, a, b, x[2], S13, 0x242070dbL); /* 3 */
                b = FF (b, c, d, a, x[3], S14, 0xc1bdceeeL); /* 4 */
                a = FF (a, b, c, d, x[4], S11, 0xf57c0fafL); /* 5 */
                d = FF (d, a, b, c, x[5], S12, 0x4787c62aL); /* 6 */
                c = FF (c, d, a, b, x[6], S13, 0xa8304613L); /* 7 */
                b = FF (b, c, d, a, x[7], S14, 0xfd469501L); /* 8 */
                a = FF (a, b, c, d, x[8], S11, 0x698098d8L); /* 9 */
                d = FF (d, a, b, c, x[9], S12, 0x8b44f7afL); /* 10 */
                c = FF (c, d, a, b, x[10], S13, 0xffff5bb1L); /* 11 */
                b = FF (b, c, d, a, x[11], S14, 0x895cd7beL); /* 12 */
                a = FF (a, b, c, d, x[12], S11, 0x6b901122L); /* 13 */
                d = FF (d, a, b, c, x[13], S12, 0xfd987193L); /* 14 */
                c = FF (c, d, a, b, x[14], S13, 0xa679438eL); /* 15 */
                b = FF (b, c, d, a, x[15], S14, 0x49b40821L); /* 16 */

                /* Round 2 */
                a = GG (a, b, c, d, x[1], S21, 0xf61e2562L); /* 17 */
                d = GG (d, a, b, c, x[6], S22, 0xc040b340L); /* 18 */
                c = GG (c, d, a, b, x[11], S23, 0x265e5a51L); /* 19 */
                b = GG (b, c, d, a, x[0], S24, 0xe9b6c7aaL); /* 20 */
                a = GG (a, b, c, d, x[5], S21, 0xd62f105dL); /* 21 */
                d = GG (d, a, b, c, x[10], S22, 0x2441453L); /* 22 */
                c = GG (c, d, a, b, x[15], S23, 0xd8a1e681L); /* 23 */
                b = GG (b, c, d, a, x[4], S24, 0xe7d3fbc8L); /* 24 */
                a = GG (a, b, c, d, x[9], S21, 0x21e1cde6L); /* 25 */
                d = GG (d, a, b, c, x[14], S22, 0xc33707d6L); /* 26 */
                c = GG (c, d, a, b, x[3], S23, 0xf4d50d87L); /* 27 */
                b = GG (b, c, d, a, x[8], S24, 0x455a14edL); /* 28 */
                a = GG (a, b, c, d, x[13], S21, 0xa9e3e905L); /* 29 */
                d = GG (d, a, b, c, x[2], S22, 0xfcefa3f8L); /* 30 */
                c = GG (c, d, a, b, x[7], S23, 0x676f02d9L); /* 31 */
                b = GG (b, c, d, a, x[12], S24, 0x8d2a4c8aL); /* 32 */

                /* Round 3 */
                a = HH (a, b, c, d, x[5], S31, 0xfffa3942L); /* 33 */
                d = HH (d, a, b, c, x[8], S32, 0x8771f681L); /* 34 */
                c = HH (c, d, a, b, x[11], S33, 0x6d9d6122L); /* 35 */
                b = HH (b, c, d, a, x[14], S34, 0xfde5380cL); /* 36 */
                a = HH (a, b, c, d, x[1], S31, 0xa4beea44L); /* 37 */
                d = HH (d, a, b, c, x[4], S32, 0x4bdecfa9L); /* 38 */
                c = HH (c, d, a, b, x[7], S33, 0xf6bb4b60L); /* 39 */
                b = HH (b, c, d, a, x[10], S34, 0xbebfbc70L); /* 40 */
                a = HH (a, b, c, d, x[13], S31, 0x289b7ec6L); /* 41 */
                d = HH (d, a, b, c, x[0], S32, 0xeaa127faL); /* 42 */
                c = HH (c, d, a, b, x[3], S33, 0xd4ef3085L); /* 43 */
                b = HH (b, c, d, a, x[6], S34, 0x4881d05L); /* 44 */
                a = HH (a, b, c, d, x[9], S31, 0xd9d4d039L); /* 45 */
                d = HH (d, a, b, c, x[12], S32, 0xe6db99e5L); /* 46 */
                c = HH (c, d, a, b, x[15], S33, 0x1fa27cf8L); /* 47 */
                b = HH (b, c, d, a, x[2], S34, 0xc4ac5665L); /* 48 */

                /* Round 4 */
                a = II (a, b, c, d, x[0], S41, 0xf4292244L); /* 49 */
                d = II (d, a, b, c, x[7], S42, 0x432aff97L); /* 50 */
                c = II (c, d, a, b, x[14], S43, 0xab9423a7L); /* 51 */
                b = II (b, c, d, a, x[5], S44, 0xfc93a039L); /* 52 */
                a = II (a, b, c, d, x[12], S41, 0x655b59c3L); /* 53 */
                d = II (d, a, b, c, x[3], S42, 0x8f0ccc92L); /* 54 */
                c = II (c, d, a, b, x[10], S43, 0xffeff47dL); /* 55 */
                b = II (b, c, d, a, x[1], S44, 0x85845dd1L); /* 56 */
                a = II (a, b, c, d, x[8], S41, 0x6fa87e4fL); /* 57 */
                d = II (d, a, b, c, x[15], S42, 0xfe2ce6e0L); /* 58 */
                c = II (c, d, a, b, x[6], S43, 0xa3014314L); /* 59 */
                b = II (b, c, d, a, x[13], S44, 0x4e0811a1L); /* 60 */
                a = II (a, b, c, d, x[4], S41, 0xf7537e82L); /* 61 */
                d = II (d, a, b, c, x[11], S42, 0xbd3af235L); /* 62 */
                c = II (c, d, a, b, x[2], S43, 0x2ad7d2bbL); /* 63 */
                b = II (b, c, d, a, x[9], S44, 0xeb86d391L); /* 64 */

                state[0] += a;
                state[1] += b;
                state[2] += c;
                state[3] += d;

        }

        /*Encode把long数组按顺序拆成byte数组，因为java的long类型是64bit的，
          只拆低32bit，以适应原始C实现的用途


        */
        private void Encode (byte[] output, long[] input, int len) {
                int i, j;

                for (i = 0, j = 0; j < len; i++, j += 4) {
                        output[j] = (byte)(input[i] & 0xffL);
                        output[j + 1] = (byte)((input[i] >>> 8) & 0xffL);
                        output[j + 2] = (byte)((input[i] >>> 16) & 0xffL);
                        output[j + 3] = (byte)((input[i] >>> 24) & 0xffL);
                }
        }

        /*Decode把byte数组按顺序合成成long数组，因为java的long类型是64bit的，
          只合成低32bit，高32bit清零，以适应原始C实现的用途


        */
        private void Decode (long[] output, byte[] input, int len) {
                int i, j;


                for (i = 0, j = 0; j < len; i++, j += 4) {
                    output[i] = b2iu(input[j]) |
                            (b2iu(input[j + 1]) << 8) |
                            (b2iu(input[j + 2]) << 16) |
                            (b2iu(input[j + 3]) << 24);
                }

                return;
        }

        /*
          b2iu是我写的一个把byte按照不考虑正负号的原则的＂升位＂程序，因为java没有unsigned运算
        */
        public static long b2iu(byte b) {
                return b < 0 ? b & 0x7F + 128 : b;
        }

     /*byteHEX()，用来把一个byte类型的数转换成十六进制的ASCII表示，


     　因为java中的byte的toString无法实现这一点，我们又没有C语言中的
       sprintf(outbuf,"%02X",ib)
     */
        public static String byteHEX(byte ib) {
                char[] Digit = { '0','1','2','3','4','5','6','7','8','9',
                'A','B','C','D','E','F' };
                char [] ob = new char[2];
                ob[0] = Digit[(ib >>> 4) & 0X0F];
                ob[1] = Digit[ib & 0X0F];
                String s = new String(ob);
                return s;
        }

        public static void main(String args[]) {

//        String result = "666;123&456;132&6936;785";
//        String[] s = result.split("&");
//        for(String v : s){
//            System.out.println(v);
//        }
//                Md5Util m = new Md5Util();
//            System.out.println(Md5Util.MD5("543744"));
//            System.out.println(m.getMD5ofStr("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"));
//                if (Array.getLength(args) == 0) {   //如果没有参数，执行标准的Test Suite
//
//                            System.out.println("MD5 Test suite:");
//                     System.out.println("MD5(\"\"):"+m.getMD5ofStr(""));
//                     System.out.println("MD5(\"a\"):"+m.getMD5ofStr("a"));
//                     System.out.println("MD5(\"abc\"):"+m.getMD5ofStr("abc"));
//                     System.out.println("MD5(\"message digest\"):"+m.getMD5ofStr("message digest"));
//                     System.out.println("MD5(\"abcdefghijklmnopqrstuvwxyz\"):"+
//                        m.getMD5ofStr("abcdefghijklmnopqrstuvwxyz"));
//                     System.out.println("MD5(\"robin\"):");
//                          System.out.println( m.getMD5ofStr("厦门市疾病预防控制中心http://www.ntko.com"));
//                          System.out.println( m.getMD5ofStr("sinian"));
//                          System.out.println( m.getMD5ofStr("12345678987456321"));
//                }
//                else
//                           System.out.println("MD5(" + args[0] + ")=" + m.getMD5ofStr(args[0]));
//            try {
//                Map<String,Object> idNos =   CardUtil.getCarInfo("350124193811011695");
//                System.out.println(JsonUtil.toJson(idNos));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            for(int g =0;g<100;g++){
//                int i = (int)(Math.random()*8888)+4000;
//                int q = (int)(Math.random()*7777)+5000;
//                String first = String.valueOf(i).substring(0,2);
//                String last = String.valueOf(i).substring(2,4);
//                int result = Integer.parseInt(last) % Integer.parseInt(first) / 7;
//                if(result >0 ) {
//                    System.out.println("int g===" + g + "====" + result+q);
//                }
//            }
//            System.out.println(Md5Util.MD5("zzylz"));
//            try {
//                String urlData = "%7B%22appId%22%3A%221CVFAAL6D00317560A0A0000C3C8B12C%22%2C%22encryptData%22%3A%22ED268C1B9DA525BA12C8B3F71804C78C2FBC0D34254DC2B301A4A3EA7ACCE9410D16FF916971F5AA937BE9D6EB9D1934874A1DFB25AA8335EC14C8C8FC9036AF7A40A44725333813B08ECD6DD11472E5E20A428C0C6ED8DC1EF344068DB5C48A3CD8EB42317E9851B8DE22095D4636D345CF5818815A973E7E2A85DCD5AD61D4859FB7585545257B71B4E6595ABCE08C%22%2C%22encryptType%22%3A%22AES%22%2C%22isEncrypt%22%3A0%2C%22sign%22%3A%22825A3C730CDDF8C42E6F8D8EF4C284EB%22%2C%22signType%22%3A%22MD5%22%2C%22timestamp%22%3A%2220181224132317%22%7D";
//                String result= URLDecoder.decode(urlData, "UTF-8");
//                System.out.println(result);
//
//                String appId = "2BTDC1OPF0041518A8C000000F004DF7";
//                String appSecret = "2BTDC1OPR0001518A8C0000090385091";
//                Map<String, Object> reqParam = new HashMap();
//                reqParam.put("extUserId", "wwww");
//                reqParam.put("mobilePhone", "18559216386");
//                reqParam.put("userName", "测试");
//                reqParam.put("idNo", "360101198912014115");
//                String serverUrl = "http://alilive.ylzpay.cn/ehc-portal-web/ext/index";
//                String url = EhcPortalClient.getUrl(serverUrl, appId, appSecret, reqParam);
//                System.out.println(url);
//
//            }catch (Exception e){
//                e.printStackTrace();
//            }
            String st = "appId=F2CDBF13683C3DF4C99D5177E43CAC80&param={\"ehcCardNo\":\"55\",\"ehcId\":\"44\",\"patientCard\":\"G03088032\",\"patientIdNo\":\"350420195106220088\",\"patientName\":\"童桂清\",\"patientNeighborhoodCommittee\":\"350481003002\",\"patientTel\":\"13666982393\",\"timestamp\":\"20190131144652\"}&timestamp=20190131144652&type=QYSQ&key=4BBCF52CCC65D6C83036D34DC4CF7EE6";
            System.out.println(Md5Util.MD516(st).toUpperCase());
//            System.out.println(i.length());

        }


    public final static String MD5(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public final static String MD516(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str).substring(8,24);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

