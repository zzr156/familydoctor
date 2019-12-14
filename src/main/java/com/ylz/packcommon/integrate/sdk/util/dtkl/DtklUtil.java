package com.ylz.packcommon.integrate.sdk.util.dtkl;





import com.ylz.packcommon.integrate.sdk.util.dtkl.key.OCRA;
import com.ylz.packcommon.integrate.sdk.util.dtkl.tel.Itel;
import com.ylz.packcommon.integrate.sdk.util.dtkl.tel.TelUtil;
import com.ylz.packcommon.integrate.sdk.util.dtkl.util.HexUtil;
import com.ylz.packcommon.integrate.sdk.util.dtkl.util.TimeUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DtklUtil {
	
	private static String ocra = "OCRA-1:HOTP-SHA1-6:QN08-T1MS128";

	private static String SEED = "3132333435363738393031323334353637383930";

	private static String question = "xmgrid!@#$%^";

	public static String checkok = "1";
	private static String checkerr = "0";

	/**
	 * @param website
	 * @param username
	 * @param telno
	 * @return 返回1000表示发送成功,返回2000表示发送失败(失败原因未知)，返回2001表示发送失败（失败原因：电话号码有问题）
	 */
	public static String sendPwd(String website, String username, String telno) {
		Itel tel = TelUtil.iniTel();
		String msg = encrypt(website, username, telno);
		String isSend = tel.sendMsg(telno, msg);
		return "1000";
	}

	/**
	 * 加密
	 * @param website  web站点编号
	 * @param username 用户名
	 * @param tel 用户接收令牌电话
	 * @return 令牌
	 */
	public static String encrypt(String website, String username, String tel) {
		DateFormat t1 = new SimpleDateFormat("yyyyMMddHHmm");
		String timeStamp = t1.format(new Date());
		String kl = encrypt(website, username, tel, timeStamp);
		return kl;
	}

	/**
	 * 加密(时间由外部设定)
	 * @param website web站点编号
	 * @param username 用户名
	 * @param tel 用户接收令牌电话
	 * @param stime 时间
	 * @return
	 */
	private static String encrypt(String website, String username, String tel,
                                  String stime) {
		String seed = SEED;
		String ocraSuite = ocra;
		String counter = "";
		String password = "";
		String sessionInformation = HexUtil.toHexString(website + username + tel);
		String qHex = HexUtil.asHex(question.getBytes());
		String timeStamp = stime;
		String kl = OCRA.generateOCRA(ocraSuite, seed, counter, qHex, password,
				sessionInformation, timeStamp);
		return kl;
	}

	/**
	 * 解密校验
	 * @param website
	 * @param username
	 * @param tel
	 * @param answer 令牌
	 * @return
	 */
	public static String check(String website, String username, String tel,
                               String answer) {
		if (answer == null) {
			return "0";
		}
		DateFormat t1 = new SimpleDateFormat("yyyyMMddHHmm");
		Date d = new Date();
		String time = t1.format(d);
		// 往前推1分钟
		String time1 = t1.format(TimeUtil.minusMin(d, 1));
		// 往前推2分钟
		String time2 = t1.format(TimeUtil.minusMin(d, 2));
		// 往前推3分钟
		String time3 = t1.format(TimeUtil.minusMin(d, 3));
		// 往前推4分钟
		String time4 = t1.format(TimeUtil.minusMin(d, 4));
		// 往前推5分钟
		String time5 = t1.format(TimeUtil.minusMin(d, 5));

		String kl1 = encrypt(website, username, tel, time);
		System.out.println("kl1:"+kl1);
		if (answer.equals(kl1)) {
			return DtklUtil.checkok;
		}
		String kl2 = encrypt(website, username, tel, time1);
		System.out.println("kl2:"+kl2);
		if (answer.equals(kl2)) {
			return DtklUtil.checkok;
		}
		String kl3 = encrypt(website, username, tel, time2);
		System.out.println("kl3:"+kl3);
		if (answer.equals(kl3)) {
			return DtklUtil.checkok;
		}
		String kl4 = encrypt(website, username, tel, time3);
		System.out.println("kl4:"+kl4);
		if (answer.equals(kl4)) {
			return DtklUtil.checkok;
		}
		String kl5 = encrypt(website, username, tel, time4);
		System.out.println("kl5:"+kl5);
		if (answer.equals(kl5)) {
			return DtklUtil.checkok;
		}
		String kl6 = encrypt(website, username, tel, time5);
		System.out.println("kl6:"+kl6);
		if (answer.equals(kl6)) {
			return DtklUtil.checkok;
		}

		return DtklUtil.checkerr;
	}
	
	public static void main(String[] args) {
		String website = "xmcdc";
		String username = "zyd";
		String tel = "13000012346";
		String pass = check(website, username, tel, "800609");
		System.out.println("pass is :" + pass);
	}
}
