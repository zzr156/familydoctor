package com.ylz.packcommon.common.util;

//import java.awt.image.BufferedImage;
//import java.io.ByteArrayOutputStream;
//import java.util.Calendar;
//
//import javax.imageio.ImageIO;
//
//import org.jbarcode.JBarcode;
//import org.jbarcode.encode.Code11Encoder;
//import org.jbarcode.encode.Code128Encoder;
//import org.jbarcode.encode.EAN13Encoder;
//import org.jbarcode.encode.InvalidAtributeException;
//import org.jbarcode.paint.BaseLineTextPainter;
//import org.jbarcode.paint.EAN13TextPainter;
//import org.jbarcode.paint.WidthCodedPainter;
//
//import sun.misc.BASE64Encoder;

public class BarcodeUtil {
	   
//	/**  
//     * 128条形码  
//     *  
//     * @param strBarCode  
//     *            条形码：0-100位  
//     * @param dimension  
//     *            商品条形码：尺寸  
//     * @param barheight  
//     *            商品条形码：高度  
//     * @return 图片(Base64编码)  
//     */  
//      public static String generateBarCode11(String strBarCode,String dimension,String barheight) {  
//              
//       
//            try {  
//                ByteArrayOutputStream outputStream = null;  
//                BufferedImage bi = null;  
//                int len = strBarCode.length();  
//                JBarcode productBarcode = new JBarcode(Code11Encoder.getInstance(),  
//                        WidthCodedPainter.getInstance(),  
//                        BaseLineTextPainter.getInstance());  
//       
//                // 尺寸，面积，大小 密集程度  
//                productBarcode.setXDimension(Double.valueOf(dimension).doubleValue());  
//                // 高度 10.0 = 1cm 默认1.5cm  
//                productBarcode.setBarHeight(Double.valueOf(barheight).doubleValue());  
//                // 宽度  
//                productBarcode.setWideRatio(Double.valueOf(30).doubleValue());  
////                  是否显示字体  
//                productBarcode.setShowText(true);  
////                 显示字体样式  
//                productBarcode.setTextPainter(BaseLineTextPainter.getInstance());   
//       
//                // 生成二维码  
//                bi = productBarcode.createBarcode(strBarCode);  
//                  
//                outputStream = new ByteArrayOutputStream();  
//                ImageIO.write(bi, "jpg", outputStream);  
//                BASE64Encoder encoder = new BASE64Encoder();  
////            System.err.println(encoder.encode(outputStream.toByteArray()));  
//  
//                return encoder.encode(outputStream.toByteArray());  
//            } catch (Exception e) {  
//                e.printStackTrace();  
//                return "encodeError";  
//            }  
//        }  
//      public static String generateBarCode128(String strBarCode,String dimension,String barheight) {  
//          
//          
//          try {  
//              ByteArrayOutputStream outputStream = null;  
//              BufferedImage bi = null;  
//              int len = strBarCode.length();  
//              JBarcode productBarcode = new JBarcode(Code128Encoder.getInstance(),  
//                      WidthCodedPainter.getInstance(),  
//                      EAN13TextPainter.getInstance());  
//     
//              // 尺寸，面积，大小 密集程度  
//              productBarcode.setXDimension(Double.valueOf(dimension).doubleValue());  
//              // 高度 10.0 = 1cm 默认1.5cm  
//              productBarcode.setBarHeight(Double.valueOf(barheight).doubleValue());  
//              // 宽度  
//              productBarcode.setWideRatio(Double.valueOf(10).doubleValue());  
////                是否显示字体  
//              productBarcode.setShowText(false);  
////               显示字体样式  
//              productBarcode.setTextPainter(BaseLineTextPainter.getInstance());   
//     
//              productBarcode.setCheckDigit(true);
//              productBarcode.setShowCheckDigit(false);
//              System.out.println(strBarCode);
//              // 生成二维码  
//              bi = productBarcode.createBarcode(strBarCode);  
//                
//              outputStream = new ByteArrayOutputStream();  
//              ImageIO.write(bi, "jpg", outputStream);  
//              BASE64Encoder encoder = new BASE64Encoder();  
////          System.err.println(encoder.encode(outputStream.toByteArray()));  
//
//              return encoder.encode(outputStream.toByteArray());  
//          } catch (Exception e) {  
//              e.printStackTrace();  
//              return "encodeError";  
//          }  
//      }  
//      
//      
//      
//   
//    /**  
//     * 商品条形码  
//     * @param strBarCode  
//     *            商品条形码：13位  
//     * @param dimension  
//     *            商品条形码：尺寸  
//     * @param barheight  
//     *            商品条形码：高度  
//     * @return 图片(Base64编码)  
//     */  
//    public static String generateBarCode(String strBarCode,String dimension,String barheight) {  
////      isNumeric 是否是数值  
////      校验。。。。。  
//          
//   
//        try {  
//            ByteArrayOutputStream outputStream = null;  
//            BufferedImage bi = null;  
//            int len = strBarCode.length();  
//            JBarcode productBarcode = new JBarcode(EAN13Encoder.getInstance(),  
//                    WidthCodedPainter.getInstance(),  
//                    EAN13TextPainter.getInstance());  
//              
//            String barCode = strBarCode.substring(0, len - 1);  
//            String code = strBarCode.substring(len - 1, len);  
//              
//            //校验13位  
//            String checkCode = productBarcode.calcCheckSum(barCode);  
//            if (!code.equals(checkCode)) {  
//                return "checkCodeError";  
//            }  
//   
//   
//            // 尺寸，面积，大小  
//            productBarcode.setXDimension(Double.valueOf(dimension).doubleValue());  
//            // 高度 10.0 = 1cm 默认1.5cm  
//            productBarcode.setBarHeight(Double.valueOf(barheight).doubleValue());  
//            // 宽度  
//            productBarcode.setWideRatio(Double.valueOf(25).doubleValue());  
//              
//            // 是否校验13位，默认false  
//            productBarcode.setShowCheckDigit(true);  
//              
//          //显示字符串内容中是否显示检查码内容  
////          productBarcode.setShowCheckDigit(true);  
//   
//            // 生成二维码  
//            bi = productBarcode.createBarcode(barCode);  
//              
//            outputStream = new ByteArrayOutputStream();  
//            ImageIO.write(bi, "jpg", outputStream);  
//            BASE64Encoder encoder = new BASE64Encoder();  
////          System.err.println(encoder.encode(outputStream.toByteArray()));  
//  
//            return encoder.encode(outputStream.toByteArray());  
//        } catch (Exception e) {  
//            e.printStackTrace();  
//            return "encodeError";  
//        }  
//    }  
//   
//    /**  
//     * @param args  
//     * @throws InvalidAtributeException  
//     */  
//    public static void main(String[] args) throws InvalidAtributeException {  
//    	long time = Calendar.getInstance().getTime().getTime();
//        String encode = BarcodeUtil.generateBarCode("6936983800013","0.5","30");  
//        String encode2 = BarcodeUtil.generateBarCode11("69369833450938430579753045230800013","0.5","30");  
//   
////        System.out.println(encode);  
//        System.out.println(encode2);
//    }  
    
}
