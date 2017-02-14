package com.boco.eoms.message.dao.hibernate;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.apache.axis.AxisFault;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.commons.loging.BocoLog;
import com.boco.eoms.message.dao.ISchedulerDao;
import com.boco.eoms.message.interfacesclient.ReceiveMmsSoapBindingStub;
import com.boco.eoms.message.mgr.ISmsLogManager;
import com.boco.eoms.message.mgr.impl.SmsMonitorManagerImpl;
import com.boco.eoms.message.model.MmsContent;
import com.boco.eoms.message.model.SmsLog;
import com.boco.eoms.message.util.Base64Util;
import com.boco.eoms.message.util.MsgConstants;
import com.boco.eoms.message.util.MsgHelp;

/**
 * 
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Date:2009-7-21 下午03:40:02
 * </p>
 * 
 * @author 孙圣泰
 * @version 3.5.1
 * 
 */
public class SchedulerDaoHibernate_GuiZhou extends BaseDaoHibernate implements
		ISchedulerDao {

	public boolean mmsMonitorScheduler(String mobiles, String subject, List contentList) {
		mobiles = mobiles.trim();
		subject = subject.trim();
		String logSwitch= MsgHelp.logSwitch;
		ISmsLogManager lMgr = (ISmsLogManager) ApplicationContextHolder
		.getInstance().getBean("IsmsLogManager");
		String result = "";
		boolean status = false;
		String[] mobileArr = null;	    
		ReceiveMmsSoapBindingStub bindingStub = null;
		try {
			bindingStub = new ReceiveMmsSoapBindingStub(new URL("http://10.194.2.23:8180/MMS_SEND/services/receiveMms"),null);
		} catch (AxisFault e1) {
			e1.printStackTrace();
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
	    //接收人手机号：15829550058,13915002000 可以设置发送多个人
	    if(mobiles != null && !mobiles.equals("")) {
	    	SmsLog log = new SmsLog();
	    	mobileArr = mobiles.split(",");
	    	for(int i=0; i<mobileArr.length; i++) {
		    	StringBuffer mmsXML = new StringBuffer("");
		    	StringBuffer contentBuffer = new StringBuffer("");
		    	StringBuffer filesBuffer = new StringBuffer("");
	    		mmsXML = mmsXML
	    		.append("<?xml version=\"1.0\" encoding=\"GB2312\"?>")
	    		.append("<message>")
	    		.append("<receiver_number>")
	    		.append(mobileArr[i])
	    		.append("</receiver_number>")
	    		.append("<subject>")
	    		.append(subject)
	    		.append("</subject>");
	    		for(int j=0;j<contentList.size();j++) {
	    			String base64Str = "";
	    	    	MmsContent mmsContent = (MmsContent)contentList.get(j);
	    	    	String contentUrl = mmsContent.getContent().trim();
	    	    	String contentType = mmsContent.getContentType();
	    	    	
	    	    	if(contentType != null && !contentType.equalsIgnoreCase("")) {
	    	    		if(contentType.equals(MsgConstants.MMS_TYPE_TEXT)) {
	    	    			contentBuffer.append(operString(contentBuffer.toString(),contentUrl.toString()));	    	    			
	    	    		} else if(contentType.equals(MsgConstants.MMS_TYPE_GIF) || contentType.equals(MsgConstants.MMS_TYPE_JPEG)) {
	    	    			int pos = (contentUrl.lastIndexOf("/") == -1) ? (contentUrl.lastIndexOf("\\")) : (contentUrl.lastIndexOf("/")); 
	    	    			String fileName = contentUrl.substring(pos+1);
	    	    			base64Str = Base64Util.encode(contentUrl);
	    	    			filesBuffer.append("<files imgname=\""+fileName+"\">"+base64Str+"</files>");
	    	    			contentBuffer.append(operString(contentBuffer.toString(),fileName));
	    	    		} 
	    	    	}
	    	    }	    		
	    		mmsXML.append("<content>")
	    		.append(contentBuffer.toString())
	    		.append("</content>")
	    		.append(filesBuffer.toString())
	    		.append("</message>");
	    		System.out.println(mmsXML.toString());
	    		//发送彩信
	    		try {
					result = bindingStub.receiveMms(mmsXML.toString());
				} catch (RemoteException e) {
					e.printStackTrace();
				}
	    		if(result.equals("1000")) {
	    			status = true;
	    			
	    		}
//	    		根据messageConfig.xml中配置是否记录日志
				if(logSwitch.equals(MsgConstants.LOG_ON)) {
					log.setMobile(mobiles);
					log.setContent(subject);
					log.setStatus(result);
					log.setMsgType(MsgConstants.MSGTYPE_MMS);
					log.setStatus(result);
					log.setReason("网关返回代码："+result+".请分析！");
					lMgr.saveSmsLog(log);
				}
	    	}
	    }		
		return status;
	}

	public boolean smsMonitorScheduler(String mobiles, String content) {
		System.out.println("-------------------------Enter GuiZhou Scheduler-----------------------------");
		DataSource db = (DataSource)ApplicationContextHolder.getInstance().getBean("msgdatasource");
		boolean returnStr = true;
		Statement stmt = null;
		Connection conn = null;
		try {			
			conn = db.getConnection();
			stmt = conn.createStatement();
			content = new String(content.getBytes("GB2312"),"ISO-8859-1");
			String sql = "insert into sp_submit (desttermid,msgcontent) values ('"+ mobiles + "','" + content + "')";
			
			stmt.execute(sql);
			
		} catch (Exception e) {
			returnStr = false;
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				BocoLog.error(SmsMonitorManagerImpl.class, e.getMessage());
			}
		}
		return returnStr;
	}

	public boolean voiceMonitorScheduler(String t_no, String t_alloc_time, String t_finish_time, String t_content, String t_tel_num, String t_tel_num2, String dispatch_tel) {
		return false;
	}
	public String operString(String start,String in) {
		if(start.length() == 0) {
			return in;
		} else {
			return "#@#"+in;
		}
		
	}
	
}