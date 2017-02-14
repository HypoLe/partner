package com.boco.eoms.message.dao.hibernate;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
public class SchedulerDaoHibernate_JiangXi extends BaseDaoHibernate implements
		ISchedulerDao {
	static SMProxy myProxy = null;
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
			BocoLog.debug(this, "statusCode=" + statusCode + ";statusText=" + statusText);
			code = statusCode;
	    }
	    if(code == 1000) {
	    	status = true;
	    }
		return status;
	}

	public boolean smsMonitorScheduler(String mobiles, String content) {
		content = content.trim();
        boolean returnStr = true;
        String ret = "OK";
        try
        {
            if(!StaticMethod.nullObject2String(mobiles).equals("") && !StaticMethod.nullObject2String(mobiles).equals(""))
            {
                if(myProxy == null)
                {
                	BocoLog.debug(this, "------------i'm in 39----------------");
                    Args args = initConfig();
                    if(args == null)
                    {
                        ret = "configfile is not exist";
                        BocoLog.debug(this, "------------i'm in 43----------------" + ret + "====");
                        returnStr = false;
                    }
                    BocoLog.debug(this, "args:ssssssssssssssssssssssss" + args.toString());
                    myProxy = new SMProxy(args);
                    BocoLog.debug(this, "+++++++++++++++++++++++++++++++++++++++++" + myProxy + "====");
                } else
                if(myProxy.getConnState() != null)
                {
                	BocoLog.debug(this, "------------i'm in 49----------------" + ret + "====");
                    Args args = initConfig();
                    if(args == null)
                    {
                        ret = "configfile is not exist";
                        BocoLog.debug(this, "------------i'm in 53----------------" + ret + "====");
                        returnStr = false;
                    }
                    myProxy = new SMProxy(args);
                }
                if(myProxy != null)
                {
                	BocoLog.debug(this, "into cycle");
                    List list = subStrings(content, 60);
                    BocoLog.debug(this, "list size=" + list.size());
                    for(int i = 0; i < list.size(); i++)
                    {
                    	content = (String)list.get(i);
                    	BocoLog.debug(this, "sms chaifen num i=" + i + ":message=" + content);
                        synchronized(myProxy)
                        {
                            CMPPSubmitMessage submitMsg = initCMPP(mobiles, content);
                            CMPPSubmitRepMessage submitRepMsg = (CMPPSubmitRepMessage)myProxy.send(submitMsg);
                            if(submitRepMsg.getResult() != 0)
                            {
                                ret = "send fail error result=" + submitRepMsg.getResult();
                                returnStr = false;
                            }
                        }
                    }

                } else
                {
                	BocoLog.debug(this, "出错啦!!!");
                    ret = "connect message gate fail";
                    returnStr = false;
                }
            } else
            {
                ret = "tel or content is null";
                returnStr = false;
            }
        }
        catch(Exception e)
        {
            ret = e.getMessage();
            e.printStackTrace();
        }
        BocoLog.debug(this, "+++++++++++++++++++++++++++++++++++++++++" + myProxy + "====");
        BocoLog.info(this, mobiles + "send content is" + content + ":" + ret);
        return returnStr;
    }

    public List subStrings(String res, int length)
    {
        int size = res.length() / length;
        if(res.length() % length > 0)
            size++;
        List list = new ArrayList();
        for(int i = 0; i < size; i++)
        {
            String prexStr = "";
            if(res.length() > length)
            {
                prexStr = res.substring(0, length);
                res = res.substring(length);
            } else
            {
                prexStr = res;
            }
            list.add(prexStr);
        }

        return list;
    }

    private CMPPSubmitMessage initCMPP(String tel, String msg)
    {
        CMPPSubmitMessage submitMsg = null;
        if(StaticMethod.nullObject2String(tel) == "" || StaticMethod.nullObject2String(msg) == "")
            return null;
        try
        {
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
            java.util.Date valid_Time = null;
            java.util.Date at_Time = null;
            String src_Terminal_Id = MsgHelp.getSingleProperty("//message/msg/sms_icp_code");
            tel = checkTel(tel);
            String dest_Terminal_Id[] = tel.split(",");
            for(int i = 0; i < dest_Terminal_Id.length; i++)
                if(dest_Terminal_Id[i].length() > 11)
                    dest_Terminal_Id[i] = dest_Terminal_Id[i].substring(0, 11);

            byte msg_Content[] = msg.getBytes();
            String reserve = "";
            submitMsg = new CMPPSubmitMessage(pk_Total, pk_Number, registered_Delivery, msg_Level, service_Id, fee_UserType, fee_Terminal_Id, tp_Pid, tp_Udhi, msg_Fmt, msg_Src, fee_Type, fee_Code, valid_Time, at_Time, src_Terminal_Id, dest_Terminal_Id, msg_Content, reserve);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return submitMsg;
    }

    public Args initConfig()
    {
        Args args = null;
        PropertyFile prop = PropertyFile.getInstance();
        String configfile = prop.getFilePath() + File.separator + "SMProxy.xml";
        //String configfile = "E:\\eomsb4\\eoms35\\WebContent\\WEB-INF\\configfiles\\SMProxy.xml";
       // String configfile = "E:\\IBM\\WebSphere\\ProcServer\\profiles\\Custom01\\installedApps\\wqCell01\\eoms35EAR.ear\\eoms35.war\\WEB-INF\\configfiles\\SMProxy.xml";
        BocoLog.debug(this, "\351\205\215\347\275\256\346\226\207\344\273\266\357\274\232" + configfile);
        File file = new File(configfile);
        try
        {
            if(file.exists())
            {
                args = (new Cfg(configfile, false)).getArgs("ismg");
                BocoLog.debug(this, "args:" + args.toString());
            }
            file = null;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return args;
    }

    public String checkTel(String tel)
    {
        String mobeil = "";
        boolean flag = false;
        int length = tel.length();
        for(int i = 0; i < length; i++)
        {
            int v = tel.charAt(i);
            if(v == 44 && flag)
            {
                mobeil = mobeil + tel.charAt(i);
                flag = false;
            } else
            if(v >= 48 && v <= 57)
            {
                mobeil = mobeil + tel.charAt(i);
                flag = true;
            }
        }

        return mobeil;
    }
    public boolean sendVoice(String t_no, String t_alloc_time, String t_finish_time, String t_content, String t_tel_num, String t_tel_num2, String dispatch_tel) {
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
//				BocoLog.error(VoiceMonitorManagerImpl.class, e.getMessage());
			}
		}
		return status;
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