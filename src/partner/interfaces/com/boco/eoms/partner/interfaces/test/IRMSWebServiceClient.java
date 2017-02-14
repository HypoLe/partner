package com.boco.eoms.partner.interfaces.test;

import java.io.FileNotFoundException;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import javax.xml.rpc.*;
import javax.xml.rpc.holders.StringHolder;
import org.apache.commons.logging.*;
import com.boco.eoms.partner.interfaces.fj.chinamobile.proxy.DeliveryRequestProxy;
import com.boco.eoms.partner.interfaces.services.DeliveryRequest.DeliveryRequestLocator;
import com.boco.eoms.partner.interfaces.services.DeliveryRequest.DeliveryRequestPortType;
import com.res.soa.message.SOAException;
import com.res.soa.proxy.ServiceProxy;

public class IRMSWebServiceClient {
	/*
	private LoadingFeedbackPortType port;
	private static Log log = LogFactory.getLog(OEMSWebServiceClient.class);
	
	public void getPort() {
		LoadingFeedbackLocator server = new LoadingFeedbackLocator();
		try {
			port = server.getLoadingFeedbackSOAPport_http(new URL(
						"http:10.210.17.11:8080/irmsrtu/services/LoadingFeedbackSOAPport_http"));
		} catch (MalformedURLException e) {
			log.error(LogUtil.getExceptionStr(e));
		} catch (ServiceException e) {
			log.error(LogUtil.getExceptionStr(e));
		}
	}
	
	public void replyWorkSheet() {
		String result = "";
		getPort();
		try {
			System.out.println("test breakpoint");
			int arg1 = 0;
	    	String arg2 = "not null";
			Calendar arg3 = new GregorianCalendar(2008, 9, 1, 10, 10, 10);
			StringHolder arg0 = new StringHolder();
			arg0.value = "test";
			result = port.loadingFeedbackRequest(arg0, arg1, arg2, arg3);
		} catch (Exception e) {
			log.debug(e.toString());
			e.printStackTrace();
		}
		System.out.println("print content: " + result );
	}
	
	private LoadingPreparationPortType port;
	private static Log log = LogFactory.getLog(OEMSWebServiceClient.class);

	public void getPort() {
		LoadingPreparationLocator server = new LoadingPreparationLocator();
		try {
			port = server
					.getLoadingPreparationSOAPport_http(new URL(
							"http:10.210.17.11:8080/irmsrtu/services/LoadingPreparation"));
		} catch (MalformedURLException e) {
			log.error(LogUtil.getExceptionStr(e));
		} catch (ServiceException e) {
			log.error(LogUtil.getExceptionStr(e));
		}
	}

	public void replyWorkSheet() {
		String result = "";
		getPort();
		try {
			System.out.println("test breakpoint");
			int suggestFileFormat = 2;
			StringHolder eventID = new StringHolder("IRMS2008090416342300");
			int suggestWorkMode = 1;
			int suggestCharSet = 0;
			String systemID = "IRMS";
			IntHolder workMode = new IntHolder(1);
			IntHolder charSet = new IntHolder(1);
			StringHolder path = new StringHolder();
			path.value = "";
			StringHolder connectionString = new StringHolder();
			connectionString.value = "";
			BooleanHolder isCompressed = new BooleanHolder(false);
			Calendar sendTime = new GregorianCalendar(2008, 9, 1, 10, 10, 10);
			port.loadingPreparationRequest(eventID, systemID, sendTime,
					suggestWorkMode, suggestFileFormat, suggestCharSet,
					workMode, charSet, connectionString, path, isCompressed);
			log.info("רҵ��ܴ���װ�ط�!����ֵΪ��" + workMode.value + " " + eventID.value + " "
					+ charSet.value + " " + connectionString.value + " " + path.value + " "
					+ String.valueOf(isCompressed.value));
		} catch (Exception e) {
			log.debug(e.toString());
			e.printStackTrace();
		}
		System.out.println("print content: " + result );
	}
	*/
	
	private DeliveryRequestPortType port;
	private static Log log = LogFactory.getLog(IRMSWebServiceClient.class);

	public void getPort() {
		DeliveryRequestLocator server = new DeliveryRequestLocator();
		try {
			port = server
					.getDeliveryRequestSOAPport_http(new URL(
							"http://10.210.17.18:8080/eoms/services/DeliveryRequestSOAPport_http"));
		} catch (MalformedURLException e) {
			log.error(e.toString());
		} catch (ServiceException e) {
			log.error(e.toString());
		}
	}

	public void replyWorkSheet() {
		String result = "";
		getPort();
		try {
			System.out.println("test breakpoint");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH24MMSS",Locale.SIMPLIFIED_CHINESE);
			Date day = new Date();
			String strdate = sdf.format(day);
			log.info("<YYYYMMDDHH24MMSS>:" + strdate);
			StringHolder eventID = new StringHolder("IRMS"+strdate);
			String systemID = "IRMS";
			Calendar sendTime = new GregorianCalendar();
			log.info("eventID:" + eventID);
			String dataReadyRequestUri = "http://10.210.17.18:8080/eoms/services/DeliveryReadySOAPport_http";
			//生成文件 多个网元用";"分隔符，网元第一个属性用"." 后面的属性是and 
			String filter = "GRID.null";
			//生成字符串
			port.deliveryRequestRequest(eventID, systemID, sendTime, filter, dataReadyRequestUri);
//			DeliveryRequestProxy.doDeliveryRequest("http://10.210.17.49:8080/eomsMain/services/DeliveryRequestSOAPport_http",eventID, systemID, sendTime, filter, dataReadyRequestUri);
			log.info("专业网管处理装载反馈返回值为：" + dataReadyRequestUri);
		} catch (Exception e) {
			log.debug(e.toString());
			e.printStackTrace();
		}
		System.out.println("print content: " + result );
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	//	IRMSWebServiceClient owsc = new IRMSWebServiceClient();
//		try {
//			ServiceProxy.getInstance().initialize("D:\\resesb\\serviceproxy.ini");
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (SOAException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		//owsc.replyWorkSheet();
		String a="qqq";
		String s = String.valueOf(a);
		
	//	a.substring(0,a.indexOf("."));
		System.out.println(a.indexOf("."));
		
	//	System.out.println(a.substring(0,a.indexOf(".")));
		//System.out.println(owsc.convert());
	}
	
	private String convert(){
		String src = "YA916黄陵上官&#26";
		if (src == null)
			return null;
		char c[] = src.toCharArray();
		if (c.length == 0)
			return "";

		StringBuffer sbuf = new StringBuffer(c.length);
		System.out.println(c.length);
		for (int i = 0; i < c.length; i++) {
			switch (c[i]) {
			case '>':
				sbuf.append("&gt;");
				break;
			case '<':
				sbuf.append("&lt;");
				break;
			case '"':
				sbuf.append("&quot;");
				break;
			case '\'':
				sbuf.append("&apos;");
				break;
			case '&':
				sbuf.append("&amp;");
				break;
			default:
				sbuf.append(c[i]);
			}
		}
		return sbuf.toString();
	}
}
