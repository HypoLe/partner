package com.boco.eoms.message.sxjin.util;

import org.apache.log4j.Logger;

import com.boco.eoms.common.log.BocoLog;
import com.boco.eoms.message.util.MsgHelp;
public class shortMsg {

	private static final Logger logger = Logger.getLogger(shortMsg.class);

	private static String smsIp = MsgHelp.getSingleProperty("sms_host_ip");
	private static int port = Integer.valueOf(MsgHelp.getSingleProperty("sms_port"));
	private static String spUser = MsgHelp.getSingleProperty("sms_user");
	private static String spPass = MsgHelp.getSingleProperty("sms_pass");
	private static String spServiceNumber = MsgHelp.getSingleProperty("sms_icp_code");
	
	
	
	/**
	 * 
	 * @param tel
	 * @param msg
	 * @return
	 */
	public static String sendLongSms(int monitorId,String tel,String msg) {
	     String ret = "OK";
	     
	     try {
	       if (! (tel).equals("") ||
	              (msg).equals("")) {
	         boolean ifLong = true;	         
	         String ifLongStr ="true";
	         if (ifLongStr != null && !ifLongStr.equals("true")) {
					ifLong = false;
				}

	         int soTimeOut = 30000;
	         int activeTimeOut = 20000;
	         
	         logger.info("msg====================================="+msg);
	         
	         SmsSenderl smsSender= new SmsSenderl(tel,msg,ifLong);
	         smsSender.setIp(smsIp);
	         smsSender.setPort(port);
	         smsSender.setSpId(spUser);
	         smsSender.setSpServiceNumber(spServiceNumber);
	         smsSender.setSpPass(spPass);
	         smsSender.setSoTimeOut(soTimeOut);
	         smsSender.setActiveTimeOut(activeTimeOut);
	         
	         ret = smsSender.sendMsg();
	       } else {
				ret = "";
			}
	       
		} catch (Exception e) {
	         ret = e.getMessage();
	         e.printStackTrace();
	         BocoLog.info(shortMsg.class, 1002, tel + "短信发送出错，请检查原因：" + msg + "时:" + ret);
	       }
	       BocoLog.info(shortMsg.class, 1002, tel + "手机号发送短信内容为：" + msg + "时:" + ret);
	       return ret;
	     }
	
}
