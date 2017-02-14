package com.boco.eoms.partner.process.util;

import java.io.Serializable;

/**
 * 发送短信的类
 * @author Administrator
 *
 */
public class SendMsgThread  implements Serializable,Runnable{

//	要发送信息
	private String msg;
//	接收人-接收人的电话可以是","分隔的字符串
	private String receiver;
	
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	
	
	public SendMsgThread(String msg, String receiver) {
		this.msg = msg;
		this.receiver = receiver;
	}
	
	public SendMsgThread(){}
	
	
	@Override
	public void run() {
		
		CommonUtils.sendMsg(this.msg, this.receiver);

		
	}
	
	
	
}
