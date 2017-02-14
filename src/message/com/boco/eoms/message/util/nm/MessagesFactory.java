package com.boco.eoms.message.util.nm;

import java.io.IOException;

import com.sxit.cmpp.CMPP;
import com.sxit.cmpp.CMPPException;
import com.sxit.cmpp.CMPPSocket;
import com.sxit.cmpp.CMPPSubmitResp;
import com.sxit.cmpp.Common;
import com.sxit.cmpp.SubmitBody;
import com.boco.eoms.common.util.StaticMethod;
import com.boco.eoms.message.util.nm.model.SubmitResultBean;
import com.boco.eoms.common.log.BocoLog;
public class MessagesFactory {
	  private static CMPPSocket socket;
	  private static CMPP cmpp;
	  private static String hostIp;
	  private int port;
	  private String password;
	  private String icpCode;
	  private String icpId;
	  private int version;
	  private int sleepTime;

	  /**
	   * 读取初始配置并建立与网关的连接（会一直循环连接，直到连接成功为止）
	   */
	  public MessagesFactory() {
	    readConfig();
	    initConnect();
	  }

	  /**
	   * 建立与网关的socket及cmpp连接，如果不能连接则会一直循环连接，直到连接成功为止
	   */
	  private void initConnect() {
	    SmsUtil smsUtil = new SmsUtil();
	    while (cmpp == null) {
	      while (socket == null) {
	        try {
	          socket = smsUtil.createSocket(hostIp, port);
	        }
	        catch (IOException ce) {
	          socket = null;
	          try {
	            Thread.sleep(5000);
	          }
	          catch (InterruptedException e) {

	          }
	          ce.printStackTrace();
	        }
	      }
	      try {
	        cmpp = smsUtil.createConnect(icpId, password, version, socket);
	      }
	      catch (IOException ce) {
	        cmpp = null;
	        socket = null;
	        try {
	          Thread.sleep(5000);
	        }
	        catch (InterruptedException e) {

	        }
	        ce.printStackTrace();
	      }
	    }
	    BocoLog.debug(this,1000,"初始化连接－createConnect");
	  }

	  /**
	   * 读取相关配置信息
	   */
	  private void readConfig() {
	    ReadConf rc = new ReadConf();
	    port = rc.getSmsSendPort();
	    hostIp = rc.getSmsHostIp();
	    password = rc.getSmsPassword();
	    icpCode = rc.getSmsIcpCode();
	    icpId = rc.getSmsIcpId();
	    version = rc.getSmsVersion();
	    sleepTime = rc.getSmsSleepTime();
	  }

	  /**
	   * 配置短信发送体
	   * @param phoneNumber 接收手机号码
	   * @param messages 消息内容
	   * @return 配置完成后的消息体，消息体说明见SmsUtil的initSmsBody方法
	   */
	  private SubmitBody getSubmitBody(String phoneNumber, String messages) {
	    BocoLog.debug(this,1000,"初始化消息发送体－SmsUtil.initSmsBody");
	    return new SmsUtil().initSmsBody(icpId, icpCode, phoneNumber, messages);
	  }

	  /**
	   * 提交短信内容到网关
	   * @param phoneNumber 接收手机号码
	   * @param messages 消息内容
	   * @return 提交状态值，可通过SmsUtil的getSubmitResultInfo方法取得其含义
	   */
	  public synchronized SubmitResultBean submitSms(String phoneNumber, String messages) throws IOException {
	    try {
	      Thread.sleep(sleepTime * 1000);
	    }
	    catch (InterruptedException ie) {

	    }
	    CMPPSubmitResp resp = new CMPPSubmitResp();
	    SubmitBody sub = getSubmitBody(phoneNumber, messages);

	    int count=0;

	    int result=cmpp.cmppSubmit(sub, resp);
	    while(result!=0 && count<10){
	      if(cmpp.cmppActiveTest()!=0){
	        cmpp=null;
	        socket=null;
	        initConnect();
	      }
	      sub=getSubmitBody(phoneNumber, messages);
	      result=cmpp.cmppSubmit(sub, resp);
	      BocoLog.debug(this,1000,"第"+(count+1)+"次重发！");
	      count++;
	    }
	    return new SubmitResultBean(Common.bytes8ToLong(resp.msg_Id),StaticMethod.getLocalString(),
	                                phoneNumber, messages,
	                                SmsUtil
	                                .getSubmitResultInfo(result));


	  }
	}
