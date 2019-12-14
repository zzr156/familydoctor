package com.ylz.task.async;

import com.ylz.bizDo.cd.po.CdShortMessage;
import com.ylz.bizDo.cd.po.CdUserXxx;
import com.ylz.packaccede.allDo.SysDao;
import com.ylz.packcommon.common.Constrats;
import com.ylz.packcommon.common.util.CaKeyUtil;
import com.ylz.packcommon.common.util.MessageDtklUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Calendar;


@Component("msgPhoneAsyncBean")
public class MsgPhoneAsyncBean {

	@Autowired
	private SysDao sysDao;
	
	/**
	 * 发送ca证书密码
	 * @param userId
	 * @param account
	 * @param phone
	 * @throws InterruptedException
	 */
	@Async()
	public void msgPhoneCa(String userId,String account,String phone) throws InterruptedException {
//		Thread.sleep(5 * 1000);//网络连接中 。。。消息发送中。。。
		CdUserXxx x = CaKeyUtil.CaCreateKey(account, userId);
		this.sysDao.getServiceDo().add(x);
		String content = String.format(Constrats.MSG_KEY, x.getUserPassRandom());
		//回执ID
		int hzId  = 0;
		//int hzId = MessageDtklUtil.send(phone, content);
		CdShortMessage msg = new CdShortMessage(null,phone,content,String.valueOf(hzId),Calendar.getInstance(),userId);
		this.sysDao.getServiceDo().add(msg);
	}  
	
	/**
	 * 登录验证码
	 * @param userId
	 * @param account
	 * @param phone
	 */
	public String msgPhoneYzm(String userId,String account,String phone){
//		生成短信随机六位数
		String code = MessageDtklUtil.encrypt(Constrats.MSG_WEBSITE, account, phone);
		String content = String.format(Constrats.MSG_LOGIN, code);

		return code;
	}
	
	/**
	 * 短信验证
	 * @param account
	 * @param phone
	 * @param code
	 * @return
	 */
	public String msgPhoneCheck(String account,String phone,String code){
		return MessageDtklUtil.check(Constrats.MSG_WEBSITE, account, phone,code);
	}


	
}
