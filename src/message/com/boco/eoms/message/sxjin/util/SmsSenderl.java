package com.boco.eoms.message.sxjin.util;

import java.util.Map;

import org.apache.log4j.Logger;

import com.commerceceware.boco.util.MTThread;
import com.commerceware.cmpp.CMPP;
import com.commerceware.cmpp.CMPPESubmit;
import com.commerceware.cmpp.LoginInfo;
import com.commerceware.cmpp.SubmitInfo;
import com.commerceware.cmpp.thread.SenderThread;
import com.commerceware.cmpp.tools.WriteLog;



public class SmsSenderl {
	
	private static final Logger logger = Logger.getLogger(SmsSenderl.class);
	
	private static Map<String, SenderThread> sender=null;
	private static MTThread mt =null;
	private static boolean LoginFlag =false;
	private LoginInfo loginInfo;
	private String mobile; // �ֻ����
	private String msg; // ��������
	private boolean ifLong = true; // �Ƿ񳤶���,trueΪ��,falseΪ��
	private String ip; // ����IP��ַ
	private int port; // ���Ŷ˿�
	private String spId; // ���ź���
	private String spServiceNumber; // ������ҵ��
	private String spPass; // �������
	private int soTimeOut = 30000; // �������ؽ�bl�ӳ�ʱ��ʱ��(�Ժ���Ϊ��λ)
	private int activeTimeOut = 20000; // ��l��ʱ����l�ӵ�t·�����͵ļ��(�Ժ���Ϊ��λ)

	public SmsSenderl() {

	}

	public SmsSenderl(String mobile,String msg,boolean ifLong) {
		this.mobile = mobile;
		this.msg = msg;
		this.ifLong = ifLong;
	}

	public String sendMsg() {
		//WriteLog log = new WriteLog(".\\debug.txt", true); // ��д��־�ļ�
        WriteLog log = null;
        String ret = "OK";

        CMPP cmpp = new CMPP();
        try {
        	this.init();
        	logger.info("LoginFlag=========="+LoginFlag);
//        	if(LoginFlag==false || sender.isEmpty()){
//        		sender = cmpp.createSenderThread("s", 1, loginInfo, log);
//        		mt = new MTThread(sender.get("s[0]"));
//        		LoginFlag=true;
//        	}
            SubmitInfo submitInfo = new SubmitInfo();
            submitInfo.setSpId(loginInfo.getSpId());
            submitInfo.setServiceNumber("02007");
            submitInfo.setMessageMode(CMPPESubmit.MESSAGE_MODE_NOT_REPORT);
            submitInfo.setServiceType("svc");
            submitInfo.setMobile(this.getMobile());
            submitInfo.setMsgFormat(CMPPESubmit.MESSAGE_FORMAT_GB2312);
            submitInfo.setProtocolId(1);//���
            submitInfo.setFullSplit("\n");
            if(ifLong) { // �Ƿ񳤶��ŷ���
            	submitInfo.setUnite(true);
            }
            submitInfo.setMsgContent(this.getMsg(), "GBK");
            
            mt.add(submitInfo);

        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            logger.info("---���Ͷ��ų��--mobile--"+mobile);
            ret = e.getMessage();
        }
        return ret;
	}

	protected void init() {
		loginInfo = new LoginInfo();
        loginInfo.setSoTimeOut(this.getSoTimeOut());
        loginInfo.setActiveTimeOut(this.getActiveTimeOut());
        loginInfo.setIp(this.getIp());
        loginInfo.setPort(this.getPort());
        loginInfo.setSpId(this.getSpId());
        loginInfo.setSpServiceNumber(this.getSpServiceNumber());
        loginInfo.setSpPass(this.getSpPass());
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean getIfLong() {
		return ifLong;
	}

	public void setIfLong(boolean ifLong) {
		this.ifLong = ifLong;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getSpId() {
		return spId;
	}

	public void setSpId(String spId) {
		this.spId = spId;
	}

	public String getSpServiceNumber() {
		return spServiceNumber;
	}

	public void setSpServiceNumber(String spServiceNumber) {
		this.spServiceNumber = spServiceNumber;
	}

	public String getSpPass() {
		return spPass;
	}

	public void setSpPass(String spPass) {
		this.spPass = spPass;
	}

	public LoginInfo getLoginInfo() {
		return loginInfo;
	}

	public void setLoginInfo(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
	}

	public int getSoTimeOut() {
		return soTimeOut;
	}

	public void setSoTimeOut(int soTimeOut) {
		this.soTimeOut = soTimeOut;
	}

	public int getActiveTimeOut() {
		return activeTimeOut;
	}

	public void setActiveTimeOut(int activeTimeOut) {
		this.activeTimeOut = activeTimeOut;
	}
}
