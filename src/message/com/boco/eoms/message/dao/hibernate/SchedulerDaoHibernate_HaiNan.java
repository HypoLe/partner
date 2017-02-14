package com.boco.eoms.message.dao.hibernate;

import java.io.FileInputStream;
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
import com.boco.eoms.common.util.PropertyFile;
import com.boco.eoms.commons.loging.BocoLog;
import com.boco.eoms.message.dao.ISchedulerDao;
import com.boco.eoms.message.mgr.impl.VoiceMonitorManagerImpl;
import com.boco.eoms.message.model.MmsContent;
import com.boco.eoms.message.util.MsgConstants;
import com.boco.eoms.message.util.SendConfig;
import com.cmcc.mm7.vasp.common.MMConstants;
import com.cmcc.mm7.vasp.common.MMContent;
import com.cmcc.mm7.vasp.conf.MM7Config;
import com.cmcc.mm7.vasp.message.MM7RSRes;
import com.cmcc.mm7.vasp.message.MM7SubmitReq;
import com.cmcc.mm7.vasp.service.MM7Sender;
import com.sxit.cmpp.CMPP;
import com.sxit.cmpp.CMPPSocket;
import com.sxit.cmpp.CMPPSubmitResp;
import com.sxit.cmpp.SubmitBody;

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
public class SchedulerDaoHibernate_HaiNan extends BaseDaoHibernate implements
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

	public boolean smsMonitorScheduler(String tel, final String msg) {
		System.out.println("i'm in HaiNan's smsOuterConfigImpl's sendSms method");
		/**
		    String  host=StaticMethod.nullObject2String(StaticMethod.getNodeName("sms_host_ip"));
		    int  port=StaticMethod.nullObject2int(StaticMethod.getNodeName("sms_port"));
		    String  spId=StaticMethod.nullObject2String(StaticMethod.getNodeName("sms_user"));
		    String  pass=StaticMethod.nullObject2String(StaticMethod.getNodeName("sms_pass"));
		**/
		  String  host="172.28.1.90";
		  int  port=7890;
		  String  spId="920958";
		  String  pass="test421";


//		    int port = Integer.parseInt(sms.getProperty("sms_port"));
//		   String pass = sms.getProperty("sms_pass");
		    boolean ret = false;
		    CMPPSocket socket = new CMPPSocket(host,port);
		    try {
		      socket.initialSock();
		    }
		    catch (Exception e) {}
		    CMPP cmpp = new CMPP(socket);
		    int staus = cmpp.cmppConnect(spId, pass, 2);

		  //    检测通不通
		  //System.out.print("passs"+staus);

		    SubmitBody sub = new SubmitBody();

		    //因为用户所填的手机号码可能有多个，所以截取第一个手机号码
		    if(tel.length()>11){
		      tel = tel.substring(0, 11);
		    }
		    sub = initCMPP(tel,msg);
		    int i = 0;
		    while (true) {
		      long begin = System.currentTimeMillis();
		      CMPPSubmitResp resp = new CMPPSubmitResp();
		      cmpp.cmppSubmit(sub, resp);
		      System.out.println(resp.msg_Id + "->messageid  " + resp.sequenceID +
		                         "->seqid  " + resp.result + "->result");
		      long now = System.currentTimeMillis();
		       if (now - begin <= 400) {
		        System.out.println("发这条短信花了" + (now - begin) + "秒的时间");
		        try {
		          Thread.currentThread().sleep(400 - now + begin);
		        }
		        catch (Exception e) {}
		        ;
		      }
		      if (i++ == 0) {
		        break;
		      }

		    }

		    //发送链路检测包

		    int test = cmpp.cmppActiveTest();
		    System.out.println("发送链路检测包成功=" + test);
		    if (test == 0) {
		      ret = true;
		      return ret;
		    }
		    return ret;
	}
	public boolean sendSms(String tel, String msg,CMPP cmpp) {

	 //   String  host=StaticMethod.nullObject2String(StaticMethod.getNodeName("Interface.sms_host_ip"));
	  //  int  port=StaticMethod.nullObject2int(StaticMethod.getNodeName("Interface.sms_host_port"));
//	    int port = Integer.parseInt(sms.getProperty("sms_port"));
//	    String  user=StaticMethod.nullObject2String(StaticMethod.getNodeName("Interface.sms_host_user"));
//	    String user = sms.getProperty("sms_user");
//	    String  pass=StaticMethod.nullObject2String(StaticMethod.getNodeName("Interface.sms_host_pass"));
//	   String pass = sms.getProperty("sms_pass");
	    boolean ret = false;

	    SubmitBody sub = new SubmitBody();

	    //因为用户所填的手机号码可能有多个，所以截取第一个手机号码
	    if(tel.length()>11){
	      tel = tel.substring(0, 11);
	    }
	    sub = initCMPP(tel,msg);
	    int i = 0;
	    while (true) {
	      long begin = System.currentTimeMillis();
	      CMPPSubmitResp resp = new CMPPSubmitResp();
	      cmpp.cmppSubmit(sub, resp);
	      System.out.println(resp.msg_Id + "->messageid  " + resp.sequenceID +
	                         "->seqid  " + resp.result + "->result");
	      long now = System.currentTimeMillis();
	       if (now - begin <= 400) {
	        System.out.println("发这条短信花了" + (now - begin) + "秒的时间");
	        try {
	          Thread.currentThread().sleep(400 - now + begin);
	        }
	        catch (Exception e) {}
	        ;
	      }
	      if (i++ == 0) {
	        break;
	      }

	    }

	    //发送链路检测包

	    int test = cmpp.cmppActiveTest();
	    System.out.println("==========发送链路检测包成功=============结果：" + test);
	    if (test == 0) {
	      ret = true;
	      return ret;
	    }
	    return ret; /*
	        if (ret.equals("0")){
	          return true;
	        }
	        else
	        {
	          return false;
	        }*/

	  }
//	初始化socket链接。
	  public static CMPP cmppInit() {
	/**
	    String  host=StaticMethod.nullObject2String(StaticMethod.getNodeName("sms_host_ip"));
	    int  port=StaticMethod.nullObject2int(StaticMethod.getNodeName("sms_port"));
	    String  spId=StaticMethod.nullObject2String(StaticMethod.getNodeName("sms_user"));
	    String  pass=StaticMethod.nullObject2String(StaticMethod.getNodeName("sms_pass"));
	**/
	    String  host="172.28.1.90";
	    int  port=7890;
	    String  spId="920958";
	    String  pass="test421";

	    CMPPSocket socket = new CMPPSocket(host, port);
	    try {
	      socket.initialSock();
	    }
	    catch (Exception e) {
	      System.out.print(e.getMessage());
	    }
	    CMPP cmppObj = new CMPP(socket);
	    int staus = cmppObj.cmppConnect(spId, pass, 2);
	    System.out.println("*************检测初始化链路情况************结果：" + staus);
	    return cmppObj;
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
	
	private byte[] StrToByte(String str) {
	    byte[] tmp = str.getBytes();
	    byte[] ret = new byte[tmp.length + 1];
	    for (int i = 0; i < tmp.length; i++) {
	      ret[i] = tmp[i];
	    }
	    ret[tmp.length] = 0x0;
	    return ret;
	  }
	
	public String getProperty(String key) {
	    FileInputStream conf = null;
	    PropertyFile prop = PropertyFile.getInstance();
	    String ret = "";
	    try {
	      ret = prop.getProperty(key).toString();
	    }
	    catch (Exception e) {
	      //System.out.println("配置文件：" + prop.getFilePath() + "没有发现");
	    }

	    if (ret == null) {
	      ret = "";
	    }
	    return ret;

	  }
	private SubmitBody initCMPP(String tel, String msg) {
		/**
		    String  serviceId=StaticMethod.nullObject2String(StaticMethod.getNodeName("sms_icp_id"));
		    String  spId=StaticMethod.nullObject2String(StaticMethod.getNodeName("sms_user"));
		**/

		    String  serviceId="10658217";
		    String  spId="920958";

		         //submit消息,封装submit包,各字段意义请查阅doc文档
		         SubmitBody submit = new SubmitBody(); // submit.msgID = 12;
		         ///信息总条数
		         submit.ucPkTotal = 1;

		         //submit.ucPkNumber = 1;
		         /// 是否要求返回状态确认报告： 0：不需要 1：需要 2：产生SMC话单 （该类型短信仅供网关计费使用，不发送给目的终端)
		           submit.ucRegister = 1;
		         ///信息级别
		         submit.ucMsgLevel = 1;
		        ///业务类型，是数字、字母和符号的组合
		         submit.sServiceId = serviceId;
		         /// 计费用户类型字段  0：对目的终端MSISDN计费  1：对源终端MSISDN计费  2：对SP计费  3：表示本字段无效，对谁计费参见Fee_terminal_Id字段。
		         submit.ucFeeUserType = 3;

		         submit.sFeeTermId = tel;
		       /// GSM协议类型 详细是解释请参考GSM03.40中的9.2.3.9
		         submit.ucTpPid = 0;
		      /// GSM协议类型 详细是解释请参考GSM03.40中的9.2.3.23,仅使用1位，右对齐
		         submit.ucTpUdhi = 0;
		         ///信息格式    0：ASCII串    3：短信写卡操作    4：二进制信息    8：UCS2编码    15：含GB汉字
		         submit.ucMsgFmt = 15;
		        ///信息内容来源(SP_Id)
		         submit.sMsgSrc = spId;
		        /// 资费类别          01：对“计费用户号码”免费          02：对“计费用户号码”按条计信息费          03：对“计费用户号码”按包月收取信息费
		        ///        04：对“计费用户号码”的信息费封顶          05：对“计费用户号码”的收费是由SP实现
		         submit.sFeeType = "05";
		       /// 资费代码（以分为单位）
		         submit.sFeeCode = "10";
		      /// 存活有效期，格式遵循SMPP3.3协议
		         submit.sValidTime = "";
		       ///定时发送时间，格式遵循SMPP3.3协议
		         submit.sAtTime = "";
		        ///源号码 SP的服务代码或前缀为服务代码的长号码, 网关将该号码完整的填到SMPP协议Submit_SM消息
		        ///相应的source_addr字段，该号码最终在用户手机上显示为短消息的主叫号码
		         submit.sSrcTermId = serviceId;
		         ///接收短信的MSISDN号码,以英文逗号","分隔开
		         ///因为2.0协议短信群发的问题，建立此处只填入一个手机号码,如果有多个目的号码,请分条发送
		         submit.sDstTermId = tel;
		         ///信息内容
		         submit.usMsgContent = msg;
		         return submit;

		  }
	
	
}