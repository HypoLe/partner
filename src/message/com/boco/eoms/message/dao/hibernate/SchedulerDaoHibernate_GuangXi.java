package com.boco.eoms.message.dao.hibernate;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.dom4j.DocumentException;

import com.boco.eoms.base.dao.hibernate.BaseDaoHibernate;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.loging.BocoLog;
import com.boco.eoms.message.dao.ISchedulerDao;
import com.boco.eoms.message.mgr.impl.VoiceMonitorManagerImpl;
import com.boco.eoms.message.model.MmsContent;
import com.boco.eoms.message.util.LongSms;
import com.boco.eoms.message.util.MsgConstants;
import com.boco.eoms.message.util.SendConfig;
import com.cmcc.mm7.vasp.common.MMConstants;
import com.cmcc.mm7.vasp.common.MMContent;
import com.cmcc.mm7.vasp.conf.MM7Config;
import com.cmcc.mm7.vasp.message.MM7RSRes;
import com.cmcc.mm7.vasp.message.MM7SubmitReq;
import com.cmcc.mm7.vasp.service.MM7Sender;

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
public class SchedulerDaoHibernate_GuangXi extends BaseDaoHibernate implements
		ISchedulerDao {

	public boolean mmsMonitorScheduler(String mobiles, String subject, List contentList) {
		int code = 0;
		boolean status = false;
		String[] mobileArr = null;	    
	    //发送初始化发送地址，服务地址等信息
	    String mm7c = "";
	    String connc = "";
		try {
			mm7c = StaticMethod.getFilePathForUrl("classpath:com/boco/eoms/message/config/mm7Config.xml");
			connc = StaticMethod.getFilePathForUrl("classpath:com/boco/eoms/message/config/ConnConfig.xml");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		MM7Config mm7Config = new MM7Config(mm7c);
		mm7Config. setConnConfigName(connc);
		MM7Sender mm7Sender = null;
		try {
			mm7Sender = new MM7Sender(mm7Config);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    MM7SubmitReq submit = new MM7SubmitReq();
	    try {
			SendConfig sendConfig = new SendConfig();
			sendConfig = SendConfig.getSendConfig("classpath:com/boco/eoms/message/config/SendConfig.xml");
			submit.setTransactionID(sendConfig.transactionID);//设置MM7_submit.REQ/MM7_submit.RES对的标识，必备
		    submit.setVASID(sendConfig.VASID);//设置SP代码，必备
		    submit.setVASPID(sendConfig.VASPID);//设置服务代码，必备
		    submit.setSenderAddress(sendConfig.SenderAddress);//"MM始发方的地址 必备
		    submit.setServiceCode(sendConfig.serviceCode);//设置业务代码，必备
		    submit.setChargedPartyID("1");//设置付费方的手机号码，必备
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	    
	    submit.setChargedParty((byte) 1);//VASP所提交MM的付费方  设置VASP所提交MM的付费方，例如，发送方、接收方、发送方和接收方或两方均不付费，可选，0：Sender、1：Recipients、2：Both、3：Neither、4：ThirdParty
		submit.setDeliveryReport(true); //设置是否需要发送报告的请求（boolean值）,可选
		
		//彩信报的名称
	    submit.setSubject(subject);
	    //接收人手机号：15829550058,13915002000 可以设置发送多个人
	    if(mobiles != null && !mobiles.equals("")) {
	    	mobileArr = mobiles.split(",");
	    	for(int i=0; i<mobileArr.length; i++) {
	    		submit.addTo(mobileArr[i]);
	    	}
	    }	    
	    
	    //建立彩信内容
	    MMContent content = new MMContent();
	    content.setContentType(MMConstants.ContentType. MULTIPART_MIXED);
	    	    
	    //加入彩信说明文字
	    	    
	    for(int i=0;i<contentList.size();i++)
	    {
	    	MmsContent mmsContent = (MmsContent)contentList.get(i);
	    	String contentUrl = mmsContent.getContent().trim();
	    	String contentType = mmsContent.getContentType();
	    	
	    	if(contentType != null && !contentType.equalsIgnoreCase("")) {
	    		if(contentType.equals(MsgConstants.MMS_TYPE_TEXT)) {
	    			MMContent sub = MMContent.createFromString(contentUrl);
				    sub.setContentID(i+".txt");
				    sub.setContentType(MMConstants.ContentType.TEXT);
				    content.addSubContent(sub);
	    		} else if(contentType.equals(MsgConstants.MMS_TYPE_GIF)) {
	    			MMContent sub = MMContent.createFromFile(contentUrl);
				    sub.setContentID(i+".gif");
				    sub.setContentType(MMConstants.ContentType.GIF);
				    content.addSubContent(sub);
	    		} else if(contentType.equals(MsgConstants.MMS_TYPE_JPEG)) {
	    			MMContent sub = MMContent.createFromFile(contentUrl);
				    sub.setContentID(i+".jpeg");
				    sub.setContentType(MMConstants.ContentType.JPEG);
				    content.addSubContent(sub);
	    		}
	    	}
	    }
	    submit.setContent(content);
	    
	    //发送彩信
	    if(mm7Sender != null)
	    {
	    	MM7RSRes res = mm7Sender.send(submit);
			//发送时间和状态
			int statusCode = res.getStatusCode();
			String statusText = res.getStatusText();
			System.out.println("statusCode=" + statusCode + ";statusText=" + statusText);
			code = statusCode;
	    }
	    if(code == 1000) {
	    	status = true;
	    }
		return status;
	}

	public boolean smsMonitorScheduler(String mobiles, String content) {
		String nResult = LongSms.TestSms(mobiles, content);
	    return nResult .equals("0")? true :false;
	}

	public boolean voiceMonitorScheduler(String t_no, String t_alloc_time, String t_finish_time, String t_content, String t_tel_num, String t_tel_num2, String dispatch_tel) {
		DataSource db = (DataSource)ApplicationContextHolder.getInstance().getBean("voiceSource");
		Statement stmt = null;
		Connection conn = null;
		boolean status = true;
		try {			
			conn = db.getConnection();
			stmt = conn.createStatement();
			String sql = "insert into t_info (t_no,t_alloc_time,t_finish_time,t_content,t_tel_num,t_tel_num2,dispatch_tel,t_allocer_no, t_dealer_no) values ('"+ t_no + "','" + t_alloc_time + "','" + t_finish_time + "','" + t_content + "','" + t_tel_num + "','" + t_tel_num2 + "','" + dispatch_tel + "',0,0)";
			
			stmt.execute(sql);
			
		} catch (Exception e) {
			status = false;			
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
				status = false;
				BocoLog.error(VoiceMonitorManagerImpl.class, e.getMessage());
			}
		}
		return status;
	}
	
}