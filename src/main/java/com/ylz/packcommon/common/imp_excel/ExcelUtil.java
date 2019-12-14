package com.ylz.packcommon.common.imp_excel;

import org.apache.poi.hssf.usermodel.*;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ExcelUtil {

	
	
	public static List<String> readExcelSchool(File form,String fileName ,int sheetNumber,int strRow,int strCel) throws Exception {
		InputStream is = getFileInputStream(form,fileName);
		
		// 创建HSSFWorkbook
		HSSFWorkbook wookbook = new HSSFWorkbook(is);
		// 获取第一张工作表
		HSSFSheet sheet = wookbook.getSheetAt(sheetNumber);

		// 获取到Excel文件中的所有行数­


		int rows = sheet.getPhysicalNumberOfRows();
		List<String> listValue = new ArrayList<String>();
		
		/*if(sheet.getRow(strRow-2).getPhysicalNumberOfCells()!=21 || !sheet.getRow(strRow-2).getCell((short)0).getStringCellValue().equals("所在地编码")){
			throw new Exception("您所需要导入的学校信息表格式不对，请先点击页面上的[格式表下载]下载模板！");
		}*/
		if(rows-strRow>0){
			// 遍历行

			for (int i = strRow; i < rows; i++) {//从第二行读起
				
				// 获取某一行


				HSSFRow row = sheet.getRow(i);
				if(row.getCell((short)0).getCellType()==HSSFCell.CELL_TYPE_STRING)
				{
					if(row.getCell((short)0).getStringCellValue().endsWith("end")) {
                        break;
                    }
				}
				if (row != null) {
					// 获取该行的列数


//					int cells = row.getPhysicalNumberOfCells();
					StringBuffer value = new StringBuffer("");
//					String value="";
					// 遍历单元格
				
					for (int j = strCel; j < sheet.getRow(strRow-1).getPhysicalNumberOfCells(); j++) {
						// 获取到列的值­

						
						HSSFCell cell = row.getCell((short) j);
						if (cell != null) {

							switch (cell.getCellType()) {
							case HSSFCell.CELL_TYPE_FORMULA:
								break;
							case HSSFCell.CELL_TYPE_NUMERIC:{
								if (HSSFDateUtil.isCellDateFormatted(cell)) {       
							        double d = cell.getNumericCellValue();       
							        Date date = HSSFDateUtil.getJavaDate(d); 
							        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
							        String sss = df.format(date);								        
							        value.append(sss+",");
							    }else{
							    	if(j==2){
							    		double d = cell.getNumericCellValue();       
								        Date date = HSSFDateUtil.getJavaDate(d); 
								        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
								        String bbb = df.format(date);
								        value.append(bbb+",");
						    		}else{
						    			value.append(((Double)cell.getNumericCellValue()).intValue()+",");
						    		}
							    }
									break;
							 }

							case HSSFCell.CELL_TYPE_STRING:
								value.append(cell.getStringCellValue()+",");
								break;
							default:
								value.append(" ,");

								break;
							}
						}else{
//							boolean flag=false;
//							for(int k = 0;k<mustCells.length;k++){
//								if(mustCells[k]==j){
//									flag=true;
//								}
//							}
//							if(flag)
//								throw new Exception("您所需要导入的信息中,["+sheet.getRow(strRow-2).getCell((short) j).getStringCellValue()+"]列为必填项,但您所提供的excel表中的第"+(i+1)+"行,第"+(j+1)+"列的值为空！");
//							else
								value.append(",");
						}

					}
					listValue.add(value.toString());
				}
			}
			
		}
		else {
            throw new Exception("您所导入的学校信息表中无一条数据信息，请填入信息后，才可导入到数据库！");
        }
		
		return listValue;
	}
	
	//获取文件输入流
	private static InputStream getFileInputStream(File form,String fileName) throws Exception,
			FileNotFoundException, IOException {
		System.out.println(fileName);
		if(!fileName.substring(fileName.lastIndexOf(".")).equals(".xls")){
			String message = fileName.substring(fileName.lastIndexOf("."))+"格式的数据不能被读取,请选择.xls的文件格式进行数据导入";
			throw new Exception(message);
		}
		FileInputStream is = new FileInputStream(form);
		return is;
	}
}
