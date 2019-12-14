package com.ylz.packcommon.common.util;

//import java.awt.Color;
//import java.awt.Graphics2D;
//import java.awt.image.BufferedImage;
//import java.io.File;
//
//import javax.imageio.ImageIO;
//
//import jp.sourceforge.qrcode.QRCodeDecoder;
//import jp.sourceforge.qrcode.data.QRCodeImage;
//import jp.sourceforge.qrcode.util.ContentConverter;
//
//import com.swetake.util.Qrcode;

/**
 * Created with IntelliJ IDEA.
 * User: zk
 * Date: 13-10-14
 * Time: 上午9:58
 * To change this template use File | Settings | File Templates.
 */
public class QrcodeUtils {
//    /**
//     * 生成二维码
//     * @param content 内容
//     * @param size 图片大小
//     * @return
//     * @throws Exception
//     */
//    public static BufferedImage enCode(String content, int size, String logoPath) throws Exception{
//        Qrcode qrcode=new Qrcode();
//        // 设置二维码排错率，可选L(7%)、M(15%)、Q(25%)、H(30%)，排错率越高可存储的信息越少，但对二维码清晰度的要求越小
//        qrcode.setQrcodeErrorCorrect('L');
//        qrcode.setQrcodeEncodeMode('B');
//        // 设置设置二维码尺寸，取值范围1-40，值越大尺寸越大，可存储的信息越大
//        qrcode.setQrcodeVersion(size);
//        // 获得内容的字节数组，设置编码格式
//        byte[] d =content.getBytes("UTF-8");
//        // 图片尺寸
//        int imgSize = 67 + 12 * (size - 1);
//        System.out.println(imgSize);
//        BufferedImage bi = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_RGB);
//        Graphics2D g = bi.createGraphics();
//        //设置背景颜色
//        g.setBackground(Color.WHITE);
//        g.clearRect(0, 0, imgSize, imgSize);
//        // 设定图像颜色> BLACK
//        g.setColor(Color.BLACK);
//        // 设置偏移量，不设置可能导致解析出错
//        int pixoff = 2;
//        // 输出内容> 二维码
//        if (d.length > 0 && d.length < 800) {
//            boolean[][] codeOut = qrcode.calQrcode(d);
//            for (int i = 0; i < codeOut.length; i++) {
//                for (int j = 0; j < codeOut.length; j++) {
//                    if (codeOut[j][i]) {
//                        g.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
//                    }
//                }
//            }
//        } else {
//            System.out.println("二维码字符串过长");
//            throw new Exception("QRCode content bytes length = " + d.length + " not in [0, 800].");
//        }
////        logoPath = ReaderFile.doCompress(logoPath, 25, 25, 1f, "001", false);
////        Thread.sleep(1000);
////        if(logoPath!=""){//添加logo
////            File f = new File(logoPath);
////            if(f.exists()){
////                Image img = ImageIO.read(f);//实例化一个Image对象。
////                g.drawImage(img, (imgSize-25)/2, (imgSize-25)/2, null);
////            }
////        }
//        g.dispose();
//        bi.flush();
//        return bi;
//    }
//
//    /**
//     * 二维码生成图片文件
//     * @param FilePath 文件路径
//     * @param FileType 文件类型
//     * @param content   内容
//     * @param size     图片大小
//     * @throws Exception
//     */
//    public void enFileCode(String FilePath,String FileType,String content,int size,String logoPath) throws Exception
//    {
//        BufferedImage bi= QrcodeUtils.enCode(content, size, logoPath);
//        File f = new File(FilePath);
//        ImageIO.write(bi, FileType, f);
//    }
//
//    /**
//     * qrcode解码
//     * @param filename 文件路径
//     * @return 解码内容
//     */
//    public String deCode(String filename)
//    {
//        BufferedImage sourceImage;
//        String decodedString = null;
//        try {
//            sourceImage = ImageIO.read(new File(filename));
//            QRCodeDecoder code = new QRCodeDecoder();
//            byte[] decodedBytes = code.decode(new J2SEImage(sourceImage));
//            decodedString = new String(decodedBytes);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        decodedString = ContentConverter.convert(decodedString);
//        System.out.println("解码内容:");
//        System.out.println(decodedString);
//        return decodedString;
//    }
//
//    public static void main(String[] args) throws Exception {
//        QrcodeUtils q=new QrcodeUtils();
////        String testString = "姓名: 过玉玲 \n性别: 女 \n民族: 汉族 \n身份证号：352102197511052024";//103/64 115/86   127/108
////        String testString ="012345678901234567890123456789012345678901234567890123456789";
////        String testString="厦门市卫生局卫生监督所 \nhttp://www.xmwsjd.com";
////        String testString="名称：厦门市职业卫生简讯 \n网址：http://www.xmwsjd.com/wsjd/stencil/zt_list/index.jsp?id=8a8ae68329399ebe0141e96907a2000b \n编辑部：厦门市卫生局卫生监督所放射与职业卫生监督科 \n联系邮箱：xmzyws@163.com \n编辑部联系电话：0592-2667625";
////        String testString="itms-services:///?action=download-manifest&url=http://115.29.39.158/wsjoa/xmgridoa.plist";
//        String FilePath="c:\\TestQRCode.png";
////        String testString="08654fc5b95e0bb76b5d676ed0fb4c30##张三|男|20130101|张三母亲|350203198004013021";
////        String testString="http://www.xmwsjd.com/wsjd/stencil/sszx_list2/disp.jsp?id=8a8ae6832e282fd5012e2c3db15c0002&bid=8a8ae6832e282fd5012e2d33709d0026";
////        String testString="机构名称:厦门湖里健民医院 \n法定代表人:陈瑞华 \n执业地址:厦门市湖里区殿前街道寨上2363号\n主要负责人:林国棣\n诊疗科目:急诊科、内科、外科、妇产科、儿科、中医科、口腔科、五官科、预防保健科、麻醉科、计划生育专业（人工流产、药物流产、输卵管绝育术、放、取宫内节育器）、医学检验科（临床体液、血液专业，临床化学检验专业，临床免疫、血清学专业）、医学影像科（X线诊断专业，超声诊断专业，心电诊断专业）、药房\n有效期限:2012年1月19日至2015年1月18日\n登记号:220616350206510125";
//        String testString="http://www.xmwsjd.com";
//        q.enFileCode(FilePath,"png",testString,18,"F:\\111.jpg");
//        q.deCode(FilePath);
//    }
//
//}
//class J2SEImage implements QRCodeImage {
//    BufferedImage image;
//
//    public J2SEImage(BufferedImage image) {
//        this.image = image;
//    }
//
//    public int getWidth() {
//        return image.getWidth();
//    }
//
//    public int getHeight() {
//        return image.getHeight();
//    }
//
//    public int getPixel(int x, int y) {
//        return image.getRGB(x, y);
//    }
}
