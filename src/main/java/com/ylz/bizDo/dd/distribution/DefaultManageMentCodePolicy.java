package com.ylz.bizDo.dd.distribution;

import com.ylz.bizDo.dd.po.DdJlqkQch;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.Constrats;
import com.ylz.packcommon.common.bizDo.SpringHelper;
import com.ylz.packcommon.common.util.PropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


@Service("manageMentCodePolicy")
@Transactional
public class DefaultManageMentCodePolicy implements ManageMentCodePolicy {

//	public String create(String departmentId,String departmentCode,String getTime) throws IOException {
////		ApplicationContext context = new ClassPathXmlApplicationContext("/config/spring/applicationContext.xml");
////		SysDao dao = (SysDao) context.getBean("sysDao");
//		SysDao dao  =  (SysDao) SpringHelper.getBean("sysDao");
//		DateTime dt = new DateTime(getTime);
//		//年
//		int year = dt.getYear();
//		//月
//		int month = dt.getMonthOfYear();
//		//日
//		int day = dt.getDayOfMonth();
//		DdJlqkBm ddJlqkBm = dao.getDdJlqkBmDao().findByDwid(departmentId,year);
//		//如果存在社区订单流水号
//		if(ddJlqkBm == null){
//			ddJlqkBm = new DdJlqkBm();
//			ddJlqkBm.setDdYear(year);
//			ddJlqkBm.setDdGldwid(departmentId);
//			String maxChildCode = dao.getDdJlqkbDao().findMaxManageMentNo(departmentId,departmentCode,year);
//			//如果存在最大流水编码，则取最大流水编码后6位，否则取1作为编码
//			if(maxChildCode!=null){
//				//如果是新编码(20位)则截取后5位
//				maxChildCode = maxChildCode.substring(maxChildCode.length()-6);
//
//				Integer childSequence = Integer.parseInt(maxChildCode)+1;
//				ddJlqkBm.setDdCode(childSequence);
//				dao.getDdJlqkBmDao().saveUpdate(ddJlqkBm);
//				return formatDepartmentCode(departmentCode,year,month,day,childSequence);
//			}  else  {
//				ddJlqkBm.setDdCode(Constrats.DEAAULT_NO);
//				dao.getDdJlqkBmDao().saveUpdate(ddJlqkBm);
//				return formatDepartmentCode(departmentCode,year,month,day,Constrats.DEAAULT_NO);
//			}
//		} else if(ddJlqkBm.getDdYear()!=year){
//			ddJlqkBm.setDdCode(Constrats.DEAAULT_NO);
//			ddJlqkBm.setDdYear(year);
//			dao.getDdJlqkBmDao().modify(ddJlqkBm);
//			return formatDepartmentCode(departmentCode,year,month,day,Constrats.DEAAULT_NO);
//		} else {
//			ddJlqkBm.setDdCode(ddJlqkBm.getDdCode()+1);
//			dao.getDdJlqkBmDao().modify(ddJlqkBm);
//			return formatDepartmentCode(departmentCode,year,month,day,ddJlqkBm.getDdCode());
//		}
//	}
	
	//格式化部门编码
	private String formatDepartmentCode(String departmentCode,int year,int month,int day,Integer childSequence) throws IOException{
		//return departmentCode + year + String.format("1%05d",childSequence);
//		return departmentCode + year + String.format("%04d",10000-childSequence);
		String mon = String.valueOf(month);
		if(mon.length() == 1){
			mon = "0"+mon;
		}
		String days = String.valueOf(day);
		if(days.length() == 1){
			days = "0"+days;
		}
		return PropertiesUtil.getConfValue("ddnum")+departmentCode + year + mon + days + String.format("%06d",childSequence);
	}
	
	public static void main(String[] args) {
		try {
			DefaultManageMentCodePolicy p = new DefaultManageMentCodePolicy();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			String num = p.createAlipayPch("PL", format.format(Calendar.getInstance().getTime()));
			System.out.println(num);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		try {
//			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//			String num = p.create("703daa04-2790-4cdd-8dc4-cb62e4f397b6", "3502030701", format.format(Calendar.getInstance().getTime()));
//			System.out.println(num);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	/**
	 * 生成批次号
	 */
	public String createAlipayPch(String type,String getTime) throws Exception {
		SysDao dao  =  (SysDao) SpringHelper.getBean("sysDao");
		DateTime dt = new DateTime(getTime);
		//年  
		int year = dt.getYear();
		//月  
		int month = dt.getMonthOfYear();
		//日  
		int day = dt.getDayOfMonth();
		DdJlqkQch ddJlqkQch = dao.getDdJlqkQchDao().findByYear(year,month,day);
		//如果存在流水号
		if(ddJlqkQch == null){
			ddJlqkQch = new DdJlqkQch();
			ddJlqkQch.setDdYear(year);
			ddJlqkQch.setDdMonth(month);
			ddJlqkQch.setDdDay(day);
			ddJlqkQch.setDdCode(Constrats.DEAAULT_NO);
			ddJlqkQch.setDdType(type);
			dao.getServiceDo().saveUpdate(ddJlqkQch);
			return formatQpcCode(year,month,day,Constrats.DEAAULT_NO);
		} else if(ddJlqkQch.getDdYear()!=year && ddJlqkQch.getDdMonth()!=month && ddJlqkQch.getDdDay()!=day){
			ddJlqkQch.setDdCode(Constrats.DEAAULT_NO);
			ddJlqkQch.setDdYear(year);
			ddJlqkQch.setDdMonth(month);
			ddJlqkQch.setDdDay(day);
			dao.getServiceDo().modify(ddJlqkQch);
			return formatQpcCode(year,month,day,Constrats.DEAAULT_NO);
		} else {
			ddJlqkQch.setDdCode(ddJlqkQch.getDdCode()+1);
			dao.getServiceDo().modify(ddJlqkQch);
			return formatQpcCode(year,month,day,ddJlqkQch.getDdCode());
		}
	}
	
	private String formatQpcCode(int year,int month,int day,Integer childSequence) throws IOException{
		//return departmentCode + year + String.format("1%05d",childSequence);
//		return departmentCode + year + String.format("%04d",10000-childSequence);
		String mon = String.valueOf(month);
		if(mon.length() == 1){
			mon = "0"+mon;
		}
		String days = String.valueOf(day);
		if(days.length() == 1){
			days = "0"+days;
		}
		return year + mon + days + String.format("%08d",childSequence);
	}
	
	private String formatQpcCode(String type,int year,Integer childSequence) throws IOException{
		return type +"("+year +")" + String.format("%04d",childSequence)+"号";
	}
	

}
