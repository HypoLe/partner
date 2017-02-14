package com.boco.eoms.message.dao.hibernate;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.List;
import java.util.Random;

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
import com.boco.eoms.message.util.MsgHelp;
import com.boco.eoms.message.util.SendConfig;
import com.cmcc.mm7.vasp.common.MMConstants;
import com.cmcc.mm7.vasp.common.MMContent;
import com.cmcc.mm7.vasp.conf.MM7Config;
import com.cmcc.mm7.vasp.message.MM7RSRes;
import com.cmcc.mm7.vasp.message.MM7SubmitReq;
import com.cmcc.mm7.vasp.service.MM7Sender;
import com.huawei.insa2.comm.cmpp.message.CMPPSubmitMessage;
import com.huawei.insa2.comm.cmpp.message.CMPPSubmitRepMessage;
import com.huawei.insa2.util.Args;
import com.huawei.insa2.util.Cfg;
import com.huawei.smproxy.SMProxy;

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
public class SchedulerDaoHibernate_ShanXi_Qin extends BaseDaoHibernate implements
		ISchedulerDao {
	static public SMProxy myProxy = null;
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
		System.out.println("i'm in ShanXi's smsOuterConfigImpl's sendSms method");
		boolean returnStr  = true;
		String ret="OK";
		content = content.trim();
	    //移动手机短信不能超过140，否则发送短信出错！
//	    mobiles=StaticMethod.FixedStr(mobiles,135,140,".");
	    //移动手机短信不能超过140，否则发送短信出错！

	    try {
	      if (! (StaticMethod.nullObject2String(mobiles).equals("") ||
	             StaticMethod.nullObject2String(content).equals(""))) {
	        if (myProxy == null) {
	          Args args = initConfig();
	          if (args == null) {
	            ret = "配置文件不存在";
	            returnStr=false; 
	          }
	          myProxy = new SMProxy(args);
	        }
	        else if (myProxy.getConnState() != null) {
	          Args args = initConfig();
	          if (args == null) {
	            ret = "配置文件不存在";
	            returnStr=false; 
	          }
	          myProxy = new SMProxy(args);
	        }
	        if (myProxy != null) {
	          //发送短信
	          synchronized (myProxy) {
	        	  CMPPSubmitRepMessage submitRepMsg = initCMPP(mobiles, content);
	           System.out.println("submit start");
//	           CMPPSubmitRepMessage submitRepMsg = (CMPPSubmitRepMessage) myProxy.
//	                send(
//	                submitMsg);
	            System.out.println("submit end");
	            if (submitRepMsg!=null&&submitRepMsg.getResult() != 0) {
	              ret = "短信发送失败:error result=" + submitRepMsg.getResult();
	              returnStr=false; 
	              //短信派发失败后的预防处理！,我们系统原有的失败处理方式，在三期上不知如何处理，暂时注释
	             /**
	              String[] dest_Terminal_Id = this.checkTel(mobiles).split(",");
	              for(int i =0;i<dest_Terminal_Id.length ;i++){
	                  if(dest_Terminal_Id[i].length()>0){//如果电话号码不为空
	                  RecordSet rs = new RecordSet();
	                  String sql ="insert into taw_sms_newadd (tel_no,hie_content) values('"+
	                        dest_Terminal_Id[i]+"','Re:"+content+"')";
	                  try{
	                    rs.execute(StaticMethod.strFromPageToDB(sql));
	                  }catch(Exception  e){}
	                  returnStr=false; 
	                  BocoLog.info(this,1000,"Send_Msg_Error:"+sql);
	                  }
	              }
	              ////短信派发失败后的预防处理！
					*/
	            }
	            else if(submitRepMsg==null){
	            	ret = "短信发送失败";
	  	          	returnStr=false; 
	            }
	          }
	        }
	        else {
	          ret = "连接短信网关失败";
	          returnStr=false; 
	        }
	      }
	      else {
	        ret = "手机号或短信内容为空";
	        returnStr=false; 
	      }
	    }
	    catch (Exception e) {
	      ret = e.getMessage();
	      e.printStackTrace(); //异常处理
	    }
	    BocoLog.info(this,mobiles+"手机号发送短信内容为："+content+"时:"+ret);
	    return returnStr;
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
	
	private CMPPSubmitRepMessage initCMPP(String tel, String msg) {
	    CMPPSubmitMessage submitMsg = null;
	    CMPPSubmitRepMessage submitRepMsg = null;
	    try {
	      if (StaticMethod.nullObject2String(tel) == "" ||
	          StaticMethod.nullObject2String(msg) == "") {
	        return null;
	      }
	      else {
	        int pk_Total = 1;
	        int pk_Number = 1;
	        int registered_Delivery = 1;
	        int msg_Level = 3;
	        String service_Id = MsgHelp.getSingleProperty("//message/msg/sms_icp_code");
	        int fee_UserType = 2;
	        String fee_Terminal_Id = "";
	        int tp_Pid = 0;
	        int tp_Udhi = 0;
	        int msg_Fmt = 15;
	        String msg_Src = MsgHelp.getSingleProperty("//message/msg/sms_icp_id");
	        String fee_Type = "01";
	        String fee_Code = "10";
	        Date valid_Time = null;
	        Date at_Time = null;
	        String src_Terminal_Id = MsgHelp.getSingleProperty("//message/msg/sms_icp_code");
	        tel = this.checkTel(tel);//手机号码合法性检查
	        String[] dest_Terminal_Id = tel.split(",");

	        //移动手机号不能超过11，否则发送短信出错！
	        for(int i =0;i<dest_Terminal_Id.length ;i++){
	          if(dest_Terminal_Id[i].length()>11){
	            dest_Terminal_Id[i]=dest_Terminal_Id[i].substring(0,11);
	           }
	        }
	        //移动手机号不能超过11，否则发送短信出错！
           ;
		        byte[] msg_Content = msg.getBytes("UnicodeBigUnmarked");
	        String reserve = "";
            int messageUCS2Len=msg_Content.length;//长短信长度
            int maxMessageLen=140;
            System.out.println("+++++++++++++++++++messageUCS2Len+++++++++++++++++++="+messageUCS2Len);
//          start
            if(messageUCS2Len>maxMessageLen){//长短信发送
            	System.out.println("begin send long sms!");
                tp_Udhi=0x01;
                msg_Fmt=0x08;
                int messageUCS2Count=messageUCS2Len/(maxMessageLen-6)+1;//长短信分为多少条发送
                System.out.println("++++++++++messageUCS2Count+++++++++++++="+messageUCS2Count);
                Random random=new Random();//创建random对象
                int intNumber=random.nextInt();//获取一个整型数
                byte[] tp_udhiHead=new byte[6];
                tp_udhiHead[0]=0x05;
                tp_udhiHead[1]=0x00;
                tp_udhiHead[2]=0x03;
                tp_udhiHead[3]=(byte)intNumber;
                tp_udhiHead[4]=(byte)messageUCS2Count;
                tp_udhiHead[5]=0x01;//默认为第一条
                for(int i=0;i<messageUCS2Count;i++){
                    tp_udhiHead[5]=(byte)(i+1);
                    byte[] msgContent;
                    if(i!=messageUCS2Count-1){//不为最后一条
                        msgContent=byteAdd(tp_udhiHead, msg_Content, i*(maxMessageLen-6), (i+1)*(maxMessageLen-6));
                        System.out.println("+++++++++++++msgContentend="+msgContent);
                    }else{
                        msgContent=byteAdd(tp_udhiHead, msg_Content, i*(maxMessageLen-6), messageUCS2Len);
                        System.out.println("+++++++++++++++msgContent="+msgContent);
                    }
    		        submitMsg =
    		            new CMPPSubmitMessage(
    		            pk_Total,
    		            pk_Number,
    		            registered_Delivery,
    		            msg_Level,
    		            service_Id,
    		            fee_UserType,
    		            fee_Terminal_Id,
    		            tp_Pid,
    		            tp_Udhi,
    		            msg_Fmt,
    		            msg_Src,
    		            fee_Type,
    		            fee_Code,
    		            valid_Time,
    		            at_Time,
    		            src_Terminal_Id,
    		            dest_Terminal_Id,
    		            msgContent,
    		            reserve
    		            );
    	            System.out.println("submit start");
    	            submitRepMsg = (CMPPSubmitRepMessage) myProxy.send(submitMsg);
    	            System.out.println("submit end");	    		        
                }
            }	
//end	   
            else{
            	msg_Content=msg.getBytes();
            	submitMsg =
	            new CMPPSubmitMessage(
	            pk_Total,
	            pk_Number,
	            registered_Delivery,
	            msg_Level,
	            service_Id,
	            fee_UserType,
	            fee_Terminal_Id,
	            tp_Pid,
	            tp_Udhi,
	            msg_Fmt,
	            msg_Src,
	            fee_Type,
	            fee_Code,
	            valid_Time,
	            at_Time,
	            src_Terminal_Id,
	            dest_Terminal_Id,
	            msg_Content,
	            reserve
	            );
            System.out.println("submit start");
            submitRepMsg = (CMPPSubmitRepMessage) myProxy.send(submitMsg);
            System.out.println("submit end");		        
            }
	      }
            
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	    System.out.println("+++++++++++++submitMsg+++++++++++++++="+submitMsg);
	    return submitRepMsg;
	  }

 	static public Args initConfig() {
	    Args args = null;
	    PropertyFile prop = PropertyFile.getInstance();
	    String configfile = "";
	    if(prop.getFilePath()!=null){
	    configfile = prop.getFilePath() + File.separator + "SMProxy.xml";
	    }
	    else{
	    	configfile = "/export/home/sms/tomcat_schedule/webapps/eoms/WEB-INF/configfiles/SMProxy.xml";
	    }
	    System.out.println("配置文件：" + configfile);
	    File file = new File(configfile);
	    try {
	      if (file.exists()) {
	        args = new Cfg(configfile, false).getArgs("ismg");
	        System.out.println("args:" + args.toString());
	      }
	      file = null;
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	    return args;
	  }
	  
	  
	  public static byte[] byteAdd(byte[] src,byte[] add,int start,int end){ 
	        byte[] dst=new byte[src.length+end-start]; 
	        for(int i=0;i<src.length;i++){ 
	            dst[i]=src[i]; 
	        } 
	        for(int i=0;i<end-start;i++){ 
	            dst[src.length+i]=add[start+i]; 
	        } 
	        return dst; 
	    } 
	  
	 
	  
	  
	 /**
	  * 手机合法性校验
	  * @param tel 手机号
	  * @return
	  */ 
	  public String checkTel(String tel) {
		    String mobeil = "";
		    boolean flag = false;
		    int length = tel.length();
		    for (int i = 0; i < length; i++) {
		      int v = (int) tel.charAt(i);

		      if (v == 44 && flag) {
		        mobeil += tel.charAt(i);
		        flag = false;
		      }
		      else if (v >= 48 && v <= 57) {
		        mobeil += tel.charAt(i);
		        flag = true;
		      }
		    }
		    return mobeil;
	  }
	
}