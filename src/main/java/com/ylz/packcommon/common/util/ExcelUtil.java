package com.ylz.packcommon.common.util;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 利用开源组件POI3.0.2动态导出EXCEL文档
 * 转载时请保留以下信息，注明出处！
 * @param <T> 应用泛型，代表任意一个符合javabean风格的类
 * 注意这里为了简单起见，boolean型的属性xxx的get器方式为getXxx(),而不是isXxx()
 * byte[]表jpg格式的图片数据
 */
public class ExcelUtil<T> {
    /**
     * 这是一个通用的方法，利用了JAVA的反射机制，可以将放置在JAVA集合中并且符号一定条件的数据以EXCEL 的形式输出到指定IO设备上
     * @param title
     *            表格标题名
     * @param headers
     *            表格属性列名数组
     * @param dataset
     *            需要显示的数据集合,集合中一定要放置符合javabean风格的类的对象。此方法支持的
     *            javabean属性的数据类型有基本数据类型及String,Date,byte[](图片数据)
     * @param out
     *            与输出设备关联的流对象，可以将EXCEL文档导出到本地文件或者网络中
     * @param pattern
     *            如果有时间数据，设定输出格式。默认为"yyy-MM-dd"
     */
    @SuppressWarnings("unchecked")
    public void exportExcel(String title, String[] headers, Collection<T> dataset, OutputStream out, String pattern) {
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 15);
        // 生成一个样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        style.setFont(font);
        // 生成并设置另一个样式
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成另一个字体
        HSSFFont font2 = workbook.createFont();
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        // 把字体应用到当前的样式
        style2.setFont(font2);

        // 声明一个画图的顶级管理器
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        // 定义注释的大小和位置,详见文档
        HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
        // 设置注释内容
        comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
        // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
        comment.setAuthor("leno");

        //产生表格标题行
        HSSFRow row = sheet.createRow(0);
        for (short i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        //遍历集合数据，产生数据行
        Iterator<T> it = dataset.iterator();
        int index = 0;
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            T t = (T) it.next();
            //利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
            Field[] fields = t.getClass().getDeclaredFields();
            for (short i = 0; i < fields.length; i++) {
                HSSFCell cell = row.createCell(i);
                cell.setCellStyle(style2);
                Field field = fields[i];
                String fieldName = field.getName();
                String getMethodName = "get"
                        + fieldName.substring(0, 1).toUpperCase()
                        + fieldName.substring(1);
                try {
                    Class tCls = t.getClass();
                    Method getMethod = tCls.getMethod(getMethodName,
                            new Class[] {});
                    Object value = getMethod.invoke(t, new Object[] {});
                    //判断值的类型后进行强制类型转换
                    String textValue = null;
//              if (value instanceof Integer) {
//                 int intValue = (Integer) value;
//                 cell.setCellValue(intValue);
//              } else if (value instanceof Float) {
//                 float fValue = (Float) value;
//                 textValue = new HSSFRichTextString(
//                       String.valueOf(fValue));
//                 cell.setCellValue(textValue);
//              } else if (value instanceof Double) {
//                 double dValue = (Double) value;
//                 textValue = new HSSFRichTextString(
//                       String.valueOf(dValue));
//                 cell.setCellValue(textValue);
//              } else if (value instanceof Long) {
//                 long longValue = (Long) value;
//                 cell.setCellValue(longValue);
//              }
                    if (value instanceof Boolean) {
                        boolean bValue = (Boolean) value;
                        textValue = "男";
                        if (!bValue) {
                            textValue ="女";
                        }
                    } else if (value instanceof Date) {
                        Date date = (Date) value;
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        textValue = sdf.format(date);
                    }  else if (value instanceof byte[]) {
                        // 有图片时，设置行高为60px;
                        row.setHeightInPoints(60);
                        // 设置图片所在列宽度为80px,注意这里单位的一个换算
                        sheet.setColumnWidth(i, (short) (35.7 * 80));
                        // sheet.autoSizeColumn(i);
                        byte[] bsValue = (byte[]) value;
                        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
                                1023, 255, (short) 6, index, (short) 6, index);
                        anchor.setAnchorType(2);
                        patriarch.createPicture(anchor, workbook.addPicture(
                                bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
                    } else{
                        //其它数据类型都当作字符串简单处理
                        textValue = value.toString();
                    }
                    //如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
                    if(textValue!=null){
                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                        Matcher matcher = p.matcher(textValue);
                        if(matcher.matches()){
                            //是数字当作double处理
                            cell.setCellValue(Double.parseDouble(textValue));
                        }else{
                            HSSFRichTextString richString = new HSSFRichTextString(textValue);
                            HSSFFont font3 = workbook.createFont();
                            font3.setColor(HSSFColor.BLUE.index);
                            richString.applyFont(font3);
                            cell.setCellValue(richString);
                        }
                    }
                } catch (SecurityException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                    //清理资源
                }
            }

        }
        try {
            workbook.write(out);
//            workbook.c
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void exportExcel(String title, String[] headers,String[] datasetName, Collection<T> dataset, OutputStream out, String pattern) {
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 15);
        // 生成一个样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        style.setFont(font);
        // 生成并设置另一个样式
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(HSSFColor.LIGHT_YELLOW.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        // 生成另一个字体
        HSSFFont font2 = workbook.createFont();
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        // 把字体应用到当前的样式
        style2.setFont(font2);
        /**
         * cjw
         * 画重点！
         *  workbook.createFont(); 生成字体
         *  代码357行 在for循环里面调用 每次循环都调用生成一次字体数据量大就会爆炸，所以创建字体方外面。
         */
        HSSFFont font3 = workbook.createFont();
        // 声明一个画图的顶级管理器
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        // 定义注释的大小和位置,详见文档
        HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
        // 设置注释内容
        comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
        // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
        comment.setAuthor("leno");

        //产生表格标题行
        HSSFRow row = sheet.createRow(0);
        for (short i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        //遍历集合数据，产生数据行
        Iterator<T> it = dataset.iterator();
        int index = 0;
        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            T t = (T) it.next();
            //利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
//            Field[] fields = t.getClass().getDeclaredFields();
            for (short i = 0; i < datasetName.length; i++) {
                HSSFCell cell = row.createCell(i);
                cell.setCellStyle(style2);
                String fieldName = datasetName[i];
//                String fieldName = field.getName();
                String getMethodName = "get"
                        + fieldName.substring(0, 1).toUpperCase()
                        + fieldName.substring(1);
                try {
                    Class tCls = t.getClass();
                    Method getMethod = tCls.getMethod(getMethodName,
                            new Class[] {});
                    Object value = getMethod.invoke(t, new Object[] {});
                    //判断值的类型后进行强制类型转换
                    String textValue = null;
//              if (value instanceof Integer) {
//                 int intValue = (Integer) value;
//                 cell.setCellValue(intValue);
//              } else if (value instanceof Float) {
//                 float fValue = (Float) value;
//                 textValue = new HSSFRichTextString(
//                       String.valueOf(fValue));
//                 cell.setCellValue(textValue);
//              } else if (value instanceof Double) {
//                 double dValue = (Double) value;
//                 textValue = new HSSFRichTextString(
//                       String.valueOf(dValue));
//                 cell.setCellValue(textValue);
//              } else if (value instanceof Long) {
//                 long longValue = (Long) value;
//                 cell.setCellValue(longValue);
//              }
                    if (value instanceof Boolean) {
                        boolean bValue = (Boolean) value;
                        textValue = "男";
                        if (!bValue) {
                            textValue ="女";
                        }
                    } else if (value instanceof Date) {
                        Date date = (Date) value;
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        textValue = sdf.format(date);
                    }  else if (value instanceof byte[]) {
                        // 有图片时，设置行高为60px;
                        row.setHeightInPoints(60);
                        // 设置图片所在列宽度为80px,注意这里单位的一个换算
                        sheet.setColumnWidth(i, (short) (35.7 * 80));
                        // sheet.autoSizeColumn(i);
                        byte[] bsValue = (byte[]) value;
                        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
                                1023, 255, (short) 6, index, (short) 6, index);
                        anchor.setAnchorType(2);
                        patriarch.createPicture(anchor, workbook.addPicture(
                                bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
                    } else{
                        //其它数据类型都当作字符串简单处理
                        if(value!=null) {
                            textValue = value.toString();
                        }else {
                            textValue="";
                        }
                    }
                    //如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
                    if(textValue!=null){
                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                        Matcher matcher = p.matcher(textValue);
                        if(matcher.matches()){
                            //是数字当作double处理
                            cell.setCellValue(Double.parseDouble(textValue));
                        }else{
                            HSSFRichTextString richString = new HSSFRichTextString(textValue);
                            font3.setColor(HSSFColor.BLUE.index);
                            richString.applyFont(font3);
                            cell.setCellValue(richString);
                        }
                    }
                } catch (SecurityException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                    //清理资源
                }
            }

        }
        try {
            workbook.write(out);
//            workbook.close();
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void exportExcell(String title, String[] headers,String[] datasetName, Collection<T> dataset, OutputStream out, String pattern,String titles,String titless) {
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(title);
        // 设置表格默认列宽度为15个字节
        sheet.setDefaultColumnWidth((short) 10);
        // 生成一个样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.WHITE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.VIOLET.index);
        font.setFontHeightInPoints((short) 10);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        style.setFont(font);
        // 生成并设置另一个样式
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(HSSFColor.WHITE.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        // 生成另一个字体
        HSSFFont font2 = workbook.createFont();
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        // 把字体应用到当前的样式
        style2.setFont(font2);

        HSSFCellStyle style3 = workbook.createCellStyle();
        // 设置这些样式
        style3.setFillForegroundColor(HSSFColor.WHITE.index);
        style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style3.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style3.setBorderTop(HSSFCellStyle.BORDER_THIN);
//        style3.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 生成一个字体
        HSSFFont font4 = workbook.createFont();
        font4.setColor(HSSFColor.VIOLET.index);
        font4.setFontHeightInPoints((short) 10);
        font4.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        style3.setFont(font4);
        /**
         * cjw
         * 画重点！
         *  workbook.createFont(); 生成字体
         *  代码357行 在for循环里面调用 每次循环都调用生成一次字体数据量大就会爆炸，所以创建字体方外面。
         */
        HSSFFont font3 = workbook.createFont();
        // 声明一个画图的顶级管理器
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        // 定义注释的大小和位置,详见文档
        HSSFComment comment = patriarch.createComment(new HSSFClientAnchor(0, 0, 0, 0, (short) 4, 2, (short) 6, 5));
        // 设置注释内容
        comment.setString(new HSSFRichTextString("可以在POI中添加注释！"));
        // 设置注释作者，当鼠标移动到单元格上是可以在状态栏中看到该内容.
        comment.setAuthor("leno");

        CellRangeAddress cra =new CellRangeAddress(0, 0, 0, headers.length-1); // 起始行, 终止行, 起始列, 终止列
        sheet.addMergedRegion(cra);


        int index = 1;
        String[] tt = titles.split(";;;");
        if(tt.length>1){
            //添加首行标题
            HSSFRow r = sheet.createRow(0);
            HSSFCell c = r.createCell(0);
            c.setCellStyle(style);
            c.setCellValue(tt[0]);

            index = 2;
            if(tt[1].indexOf("填报机构")!=-1){
                HSSFRow rr = sheet.createRow(1);
                HSSFCell cc = rr.createCell(0);
                cc.setCellStyle(style3);
                cc.setCellValue(tt[1]);
            }else{
                HSSFRow rr = sheet.createRow(1);
                HSSFCell cc = rr.createCell(0);
                cc.setCellStyle(style);
                cc.setCellValue(tt[1]);
            }


//            HSSFRow rrss = sheet.createRow(1);
//            HSSFCell ccs = rrss.createCell(1);
//            ccs.setCellStyle(style);
//            ccs.setCellValue(ttt[1]);
            CellRangeAddress crat =new CellRangeAddress(1, 1, 0, headers.length-1); // 起始行, 终止行, 起始列, 终止列
            sheet.addMergedRegion(crat);

            if(StringUtils.isNotBlank(titless)){
                index = 3;
                //添加第二行统计数据
                HSSFRow rrs = sheet.createRow(2);//第几行
                HSSFCell ccc = rrs.createCell(0);//第几列
                ccc.setCellStyle(style);//样式
                ccc.setCellValue(titless);//内容
                CellRangeAddress cratt =new CellRangeAddress(2, 2, 0, headers.length-1); // 起始行, 终止行, 起始列, 终止列
                sheet.addMergedRegion(cratt);
            }
        }else if(StringUtils.isNotBlank(titless)){
            //添加首行标题
            HSSFRow r = sheet.createRow(0);
            HSSFCell c = r.createCell(0);
            c.setCellStyle(style);
            c.setCellValue(titles);
            index = 2;
            //添加第二行统计数据
            HSSFRow rr = sheet.createRow(1);
            HSSFCell cc = rr.createCell(0);
            cc.setCellStyle(style);
            cc.setCellValue(titless);
            CellRangeAddress crat =new CellRangeAddress(1, 1, 0, headers.length-1); // 起始行, 终止行, 起始列, 终止列
            sheet.addMergedRegion(crat);
        }else{
            //添加首行标题
            HSSFRow r = sheet.createRow(0);
            HSSFCell c = r.createCell(0);
            c.setCellStyle(style);
            c.setCellValue(titles);
        }
        //产生表格标题行
        HSSFRow row = sheet.createRow(index);
        for (short i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }

        //遍历集合数据，产生数据行
        Iterator<T> it = dataset.iterator();

        while (it.hasNext()) {
            index++;
            row = sheet.createRow(index);
            T t = (T) it.next();
            //利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
//            Field[] fields = t.getClass().getDeclaredFields();
            for (short i = 0; i < datasetName.length; i++) {
                HSSFCell cell = row.createCell(i);
                cell.setCellStyle(style2);
                String fieldName = datasetName[i];
//                int numm = sheet.getColumnWidth(i);
//                sheet.setColumnWidth(numm, 100 * 256);
//                String fieldName = field.getName();
                String getMethodName = "get"
                        + fieldName.substring(0, 1).toUpperCase()
                        + fieldName.substring(1);
                try {
                    Class tCls = t.getClass();
                    Method getMethod = tCls.getMethod(getMethodName,
                            new Class[] {});
                    Object value = getMethod.invoke(t, new Object[] {});
                    //判断值的类型后进行强制类型转换
                    String textValue = null;
//              if (value instanceof Integer) {
//                 int intValue = (Integer) value;
//                 cell.setCellValue(intValue);
//              } else if (value instanceof Float) {
//                 float fValue = (Float) value;
//                 textValue = new HSSFRichTextString(
//                       String.valueOf(fValue));
//                 cell.setCellValue(textValue);
//              } else if (value instanceof Double) {
//                 double dValue = (Double) value;
//                 textValue = new HSSFRichTextString(
//                       String.valueOf(dValue));
//                 cell.setCellValue(textValue);
//              } else if (value instanceof Long) {
//                 long longValue = (Long) value;
//                 cell.setCellValue(longValue);
//              }
                    if (value instanceof Boolean) {
                        boolean bValue = (Boolean) value;
                        textValue = "男";
                        if (!bValue) {
                            textValue ="女";
                        }
                    } else if (value instanceof Date) {
                        Date date = (Date) value;
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        textValue = sdf.format(date);
                    }  else if (value instanceof byte[]) {
                        // 有图片时，设置行高为60px;
                        row.setHeightInPoints(60);
                        // 设置图片所在列宽度为80px,注意这里单位的一个换算
                        sheet.setColumnWidth(i, (short) (35.7 * 80));
                        // sheet.autoSizeColumn(i);
                        byte[] bsValue = (byte[]) value;
                        HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0,
                                1023, 255, (short) 6, index, (short) 6, index);
                        anchor.setAnchorType(2);
                        patriarch.createPicture(anchor, workbook.addPicture(
                                bsValue, HSSFWorkbook.PICTURE_TYPE_JPEG));
                    } else{
                        //其它数据类型都当作字符串简单处理
                        if(value!=null) {
                            textValue = value.toString();
                        }else {
                            textValue="";
                        }
                    }
                    //如果不是图片数据，就利用正则表达式判断textValue是否全部由数字组成
                    if(textValue!=null){
                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                        Matcher matcher = p.matcher(textValue);
                        if(matcher.matches()){
                            //是数字当作double处理
                            cell.setCellValue(Double.parseDouble(textValue));
                        }else{
                            HSSFRichTextString richString = new HSSFRichTextString(textValue);
                            font3.setColor(HSSFColor.BLUE.index);
                            richString.applyFont(font3);
                            cell.setCellValue(richString);
                        }
                    }
                } catch (SecurityException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                    //清理资源
                }
            }

        }
        try {
            workbook.write(out);
//            workbook.close();
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /**
     * 导出Excel - 样式一
     * @param sheetName
     * @param mainTitle
     * @param subTitle
     * @param headers
     * @param datasetNames
     * @param dataset
     * @param out
     * @param pattern
     */
    public void exportExcelStyleOne(String sheetName, String mainTitle, String subTitle, String[] headers, String[] datasetNames, Collection<T> dataset, OutputStream out, String pattern) {
        // 创建一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 新建一个Sheet表格
        HSSFSheet sheet = workbook.createSheet(sheetName);
        // 设置Sheet表格默认列宽度为10个字节
        sheet.setDefaultColumnWidth((short) 10);

        // 标题样式设计
        HSSFCellStyle style1 = workbook.createCellStyle();
        style1.setFillForegroundColor(HSSFColor.WHITE.index);
        style1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //style1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style1.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style1.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //style1.setBorderTop(HSSFCellStyle.BORDER_THIN);
        // 标题字体设计
        HSSFFont font1 = workbook.createFont();
        font1.setColor(HSSFColor.VIOLET.index);
        font1.setFontHeightInPoints((short) 12);
        font1.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把标题字体应用到标题样式
        style1.setFont(font1);

        // 子标题样式设计
        HSSFCellStyle style2 = workbook.createCellStyle();
        style2.setFillForegroundColor(HSSFColor.WHITE.index);
        style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style2.setAlignment(HSSFCellStyle.ALIGN_RIGHT);
        //style2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style2.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style2.setBorderRight(HSSFCellStyle.BORDER_THIN);
        //style2.setBorderTop(HSSFCellStyle.BORDER_THIN);
        // 子标题字体设计
        HSSFFont font2 = workbook.createFont();
        font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);
        // 子标题字体应用到子标题样式
        style2.setFont(font2);

        // 列名样式设计
        HSSFCellStyle style3 = workbook.createCellStyle();
        style3.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        style3.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style3.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style3.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style3.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style3.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style3.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 列名字体设计
        HSSFFont font3 = workbook.createFont();
        font3.setColor(HSSFColor.VIOLET.index);
        font3.setFontHeightInPoints((short) 10);
        font3.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 列名字体设计应用到列名样式
        style3.setFont(font3);

        // 数据行样式设计
        HSSFCellStyle style4 = workbook.createCellStyle();
        style4.setFillForegroundColor(HSSFColor.WHITE.index);
        style4.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style4.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style4.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style4.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style4.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style4.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 数据行字体设计
        HSSFFont font4 = workbook.createFont();
        font4.setColor(HSSFColor.VIOLET.index);
        font4.setFontHeightInPoints((short) 10);
        font4.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 数据行字体应用到数据行样式
        //style4.setFont(font4);


        // 创建第一行并填入主标题
        HSSFRow firstRow = sheet.createRow(0);
        firstRow.setHeightInPoints((short) 20);
        for (int i = 0; i < headers.length; i++) {
            HSSFCell firstRowCell = firstRow.createCell(i);
            if (i == 0) {
                firstRowCell.setCellStyle(style1);
                firstRowCell.setCellValue(mainTitle);
            } else {
                firstRowCell.setCellStyle(style1);
            }
        }

        CellRangeAddress firstRowRange = new CellRangeAddress(0, 0, 0, headers.length - 1);// 起始行，终止行，起始列，终止列
        sheet.addMergedRegion(firstRowRange);

        // 创建第二行并填入副标题
        HSSFRow secondRow = sheet.createRow(1);
        for (int i = 0; i < headers.length; i++) {
            HSSFCell secondRowCell = secondRow.createCell(i);
            if (i == 0) {
                secondRowCell.setCellStyle(style2);
                secondRowCell.setCellValue(subTitle);
            } else {
                secondRowCell.setCellStyle(style2);
            }
        }
        CellRangeAddress secondRowRange = new CellRangeAddress(1, 1, 0, headers.length - 1);// 起始行，终止行，起始列，终止列
        sheet.addMergedRegion(secondRowRange);


        // 创建第三行并循环填入列名
        HSSFRow thirdRow = sheet.createRow(2);
        for (short i = 0; i < headers.length; i++) {
            HSSFCell thirdRowCell = thirdRow.createCell(i);
            thirdRowCell.setCellStyle(style3);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            thirdRowCell.setCellValue(text);
        }


        // 遍历集合数据，产生数据行
        int index = 3;// 数据行从第四行开始填入
        int orderNo = 1;// 序号从1开始
        HSSFRow dataRow = null;
        for (T t : dataset) {
            dataRow = sheet.createRow(index);
            index++;
            for (short i = 0; i < datasetNames.length; i++) {
                if (i == 0) {// 填入序号
                    HSSFCell cell = dataRow.createCell(0);
                    cell.setCellStyle(style4);
                    cell.setCellValue(orderNo);
                    orderNo++;
                }
                HSSFCell cell = dataRow.createCell(i + 1);
                //cell.setCellStyle(style2);
                String fieldName = datasetNames[i];
                String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                try {
                    Class tCls = t.getClass();
                    Method getMethod = tCls.getMethod(getMethodName, new Class[]{});
                    Object value = getMethod.invoke(t, new Object[]{});
                    //判断值的类型后进行强制类型转换
                    String textValue = null;
                    if (value instanceof Date) {
                        Date date = (Date) value;
                        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
                        textValue = sdf.format(date);
                    } else {
                        //其它数据类型都当作字符串简单处理
                        if (value != null) {
                            textValue = value.toString();
                        } else {
                            textValue = "";
                        }
                    }
                    if (textValue != null) {
                        Pattern p = Pattern.compile("^//d+(//.//d+)?$");
                        Matcher matcher = p.matcher(textValue);
                        if (matcher.matches()) {
                            cell.setCellStyle(style4);
                            cell.setCellValue(Double.parseDouble(textValue));// 数字用double进行处理
                        } else {
                            HSSFRichTextString richString = new HSSFRichTextString(textValue);
                            //font3.setColor(HSSFColor.BLUE.index);
                            //richString.applyFont(font3);
                            cell.setCellStyle(style4);
                            cell.setCellValue(richString);
                        }
                    }
                } catch (SecurityException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } finally {
                    //清理资源
                }
            }
        }
        try {
            workbook.write(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception{
//        ExcelUtil<UserListVo> ex = new ExcelUtil<UserListVo>();
//        String[] headers = { "学号","q231"};
//        String[] datasetName={ "userId","userName" };
//        List<UserListVo> dataset = new ArrayList<UserListVo>();
//        UserListVo v1=new UserListVo();
//        v1.setUserId("123");
//        v1.setUserName("阿达");
//        UserListVo v2=new UserListVo();
//        v2.setUserId("12333");
//        v2.setUserName("ds打四大");
//        dataset.add(v1);
//        dataset.add(v2);
//        OutputStream out = new FileOutputStream("E://a.xls");
//        ex.exportExcel("测试1",headers,datasetName, dataset, out,"");
//        out.close();
    }
}
