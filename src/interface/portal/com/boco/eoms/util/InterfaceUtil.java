package com.boco.eoms.util;

import java.io.StringReader;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.rpc.ServiceException;
import javax.xml.transform.TransformerException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.xpath.XPathAPI;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.FileDownLoad;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.common.log.BocoLog;
import com.boco.eoms.commons.system.role.model.TawSystemSubRole;
import com.boco.eoms.commons.util.xml.XmlManage;
import com.boco.eoms.workbench.infopub.mgr.IThreadManager;
import com.boco.eoms.workbench.infopub.model.ThreadHistory;

public class InterfaceUtil {
	public String getFilterRoles(java.util.List list) {
		String str = "0";

		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				TawSystemSubRole tawSystemSubRole = new TawSystemSubRole();
				tawSystemSubRole = (TawSystemSubRole) list.get(i);
				str = tawSystemSubRole.getId() + ",";
			}
			str = str.substring(0, str.length() - 1);
		}

		return str;

	}

	public HashMap xmlElements(String xmlDoc, HashMap map,
			String opDetailFieldName,String xpath) throws Exception {

		NodeList UDSObjectList = null;
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			if (xmlDoc.indexOf("?xml") <= 0)
				xmlDoc = "<?xml version=\"1.0\" encoding=\"gb2312\"?>" + xmlDoc;
			DocumentBuilder dc = dbf.newDocumentBuilder();
			Document doc = dc.parse(new InputSource(new StringReader(xmlDoc)));

//			String xpath = "//msg/data/opDetail/recordInfo";

			UDSObjectList = XPathAPI.selectNodeList(doc, xpath);
			if (UDSObjectList.getLength() > 1) {
				if (UDSObjectList.getLength() > 0) {
					if (UDSObjectList.getLength() == 1) {
						map = this.getMap(xpath, map, doc, opDetailFieldName);

					} else {
//						xpath = "//msg/data/opDetail/recordInfo/fieldInfo";
						map = this.getMap(xpath, map, doc, opDetailFieldName);

					}
				}
			} else {
//				xpath = "//msg/data/opDetail/recordInfo/fieldInfo";
				map = this.getMap(xpath, map, doc, opDetailFieldName);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return map;
	}

	public String getFieldValue(String key, HashMap map,
			String OpDetailFieldName) {
		String fieldValue = "";
		OpDetail opDetail = new OpDetail();
		opDetail = (OpDetail) map.get(key);

		System.out.println("FieldChName:" + opDetail.getFieldChName());
		System.out.println("FieldContent:" + opDetail.getFieldContent());
		System.out.println("FieldEnName:" + opDetail.getFieldEnName());
		if (OpDetailFieldName.equals("FieldChName")) {
			fieldValue = opDetail.getFieldChName();
		} else if (OpDetailFieldName.equals("FieldContent")) {
			fieldValue = opDetail.getFieldContent();
		} else if (OpDetailFieldName.equals("FieldEnName")) {
			fieldValue = opDetail.getFieldEnName();
		}
		return fieldValue;
	}

	public HashMap getMap(String xpath, HashMap map, Document doc,
			String opDetailFieldName) {
		NodeList UDSObjectList = null;
		Node UDSObject = null;
		String fieldValue = "";
		// xpath = "//opDetail/recordInfo/fieldInfo";
		try {
			UDSObjectList = XPathAPI.selectNodeList(doc, xpath);
		} catch (TransformerException e1) {
			e1.printStackTrace();
		}
		if (UDSObjectList.getLength() > 0) {
			for (int i = 0; i < UDSObjectList.getLength(); i++) {
				UDSObject = UDSObjectList.item(i);
				OpDetail opDetail = new OpDetail();
				xpath = "fieldChName";
				try {
					if (XPathAPI.selectSingleNode(UDSObject, xpath)
							.getFirstChild() != null) {
						System.out.println("fieldChName:"
								+ XPathAPI.selectSingleNode(UDSObject, xpath)
										.getFirstChild().getNodeValue());

						opDetail.setFieldChName(XPathAPI.selectSingleNode(
								UDSObject, xpath).getFirstChild()
								.getNodeValue());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				xpath = "fieldEnName";
				try {
					if (XPathAPI.selectSingleNode(UDSObject, xpath)
							.getFirstChild() != null) {
						System.out.println("fieldEnName:"
								+ XPathAPI.selectSingleNode(UDSObject, xpath)
										.getFirstChild().getNodeValue());

						opDetail.setFieldEnName(XPathAPI.selectSingleNode(
								UDSObject, xpath).getFirstChild()
								.getNodeValue());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				xpath = "fieldContent";
				try {
					if (XPathAPI.selectSingleNode(UDSObject, xpath)
							.getFirstChild() != null) {
						System.out.println("fieldContent:"
								+ XPathAPI.selectSingleNode(UDSObject, xpath)
										.getFirstChild().getNodeValue());
						opDetail.setFieldContent(XPathAPI.selectSingleNode(
								UDSObject, xpath).getFirstChild()
								.getNodeValue());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (opDetailFieldName.equals("FieldChName")) {
					fieldValue = opDetail.getFieldChName();
				} else if (opDetailFieldName.equals("FieldContent")) {
					fieldValue = opDetail.getFieldContent();
				} else if (opDetailFieldName.equals("FieldEnName")) {
					fieldValue = opDetail.getFieldEnName();
				}
				map.put(opDetail.getFieldEnName(), fieldValue);
			}

		}
		return map;
	}

	/**
	 * @param xmlDoc
	 * @return AttachRef
	 */
	public List getAttachRefFromXml(String xmlDoc) {
		if (xmlDoc == null || xmlDoc.length() == 0)
			return null;
		List resultlist = new ArrayList();
		NodeList UDSObjectList = null;
		Node UDSObject = null;
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			if (xmlDoc.indexOf("?xml") <= 0)
				xmlDoc = "<?xml version=\"1.0\" encoding=\"gb2312\"?>" + xmlDoc;
			DocumentBuilder dc = dbf.newDocumentBuilder();
			Document doc = dc.parse(new InputSource(new StringReader(xmlDoc)));
			String xpath = "//msg/attachRef/attachInfo";

			UDSObjectList = XPathAPI.selectNodeList(doc, xpath);
			if (UDSObjectList.getLength() > 0) {
				for (int i = 0; i < UDSObjectList.getLength(); i++) {
					UDSObject = UDSObjectList.item(i);

					AttachRef attach = new AttachRef();
					xpath = "attachName";

					attach.setAttachName(XPathAPI.selectSingleNode(UDSObject,
							xpath).getFirstChild().getNodeValue());
					System.out.println(XPathAPI.selectSingleNode(UDSObject,
							xpath).getFirstChild().getNodeValue());

					xpath = "attachURL";
					attach.setAttachURL(XPathAPI.selectSingleNode(UDSObject,
							xpath).getFirstChild().getNodeValue());

					xpath = "attachLength";
					attach.setAttachLength(XPathAPI.selectSingleNode(UDSObject,
							xpath).getFirstChild().getNodeValue());
					resultlist.add(attach);

				}

			}

		} catch (Exception e) {

			e.printStackTrace();
		}
		return resultlist;
	}

	public static String changeCharater(String xml) {
		if (xml.indexOf("&amp") < 0)
			xml = xml.replaceAll("&", "&amp");
		xml = xml.replaceAll("\"", "&quot");
		xml = xml.replaceAll("\"", "&quot");
		xml = xml.replaceAll("'", "&apos");
		xml = xml.replaceAll("<", "&lt");
		xml = xml.replaceAll(">", "&gt");

		return xml;
	}
	public static String changeCharaterOpposite(String xml) {
		if (xml.indexOf("&amp") > 0)
			xml = xml.replaceAll("&amp", "&");
		xml = xml.replaceAll("&quot", "\"");
		xml = xml.replaceAll("&quot", "\"");
		xml = xml.replaceAll("&apos", "'");
		xml = xml.replaceAll("&lt;", "<");
		xml = xml.replaceAll("&gt;", ">");
		
		return xml;
	}

	public String mapToXml(HashMap map) {
		String xml = "<opDetail>";
		Set filterSet = map.keySet();
		Iterator filterIt = filterSet.iterator();
		while (filterIt.hasNext()) {
			String key = StaticMethod.nullObject2String(filterIt.next());
			String value = StaticMethod.nullObject2String(map.get(key));
			xml += "<recordInfo><fieldInfo><fieldChName></fieldChName><fieldEnName>"
					+ key
					+ "</fieldEnName><fieldContent>"
					+ value
					+ "</fieldContent></fieldInfo></recordInfo>";
		}
		xml += "</opDetail>";
		return xml;
	}

	public void fileDownLoad(String url) {
		Thread downThread = new Thread(new FileDownLoad(url, BulletinMgrLocator
				.getAttributes().getBulletinRootPath()), "nThread");
		downThread.start();
		System.out.println("thread have gone,we will go on");
	}

	public String getLocalString() {
		java.util.Date currentDate = new java.util.Date();
		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
				"yyMMdd");
		String date = dateFormat.format(currentDate);

		return date;
	}

	/*
	 * <attachRef> <attachInfo> <attachName>a?|</attachName> <attachURL>a?|</attachURL>
	 * <attachLength>a?|</attachLength> </attachInfo> </attachRef>
	 */

	public String getAttachRef(String accessories) {
		String attachRef = "<attachRef>";
		String contentPath = StaticMethod.nullObject2String(XmlManage.getFile(
				"/config/accessoriesServer.xml").getProperty("rootpath.http"))
				+ "accessories/uploadfile/infopubFiles/";

		if (accessories != null && !"".equals(accessories)) {
			if (accessories.indexOf(",") > 0) {
				String[] accessorieses = accessories.split(",");
				if (accessorieses.length > 0) {
					for (int i = 0; i < accessorieses.length; i++) {
						if (accessorieses[i] != null
								&& !"".equals(accessorieses[i])) {
							accessorieses[i] = accessorieses[i].substring(1,
									accessorieses[i].length() - 1);
						}
						attachRef += "<attachInfo>" + "<attachName>"
								+ accessorieses[i] + "</attachName>"
								+ "<attachURL>" + contentPath
								+ accessorieses[i] + "</attachURL>"
								+ "<attachLength>2</attachLength>"
								+ "</attachInfo>";

					}
				}
			} else {

				if (accessories != null && !"".equals(accessories)) {
					accessories = accessories.substring(1,
							accessories.length() - 1);
				}
				attachRef += "<attachInfo>" + "<attachName>" + accessories
						+ "</attachName>" + "<attachURL>" + contentPath
						+ accessories + "</attachURL>"
						+ "<attachLength>2</attachLength>" + "</attachInfo>";
			}
		}

		attachRef += "</attachRef>";

		System.out.println("attachRef====>" + attachRef);
		return attachRef;

	}

	public String getOpDetail(
			com.boco.eoms.workbench.infopub.model.Thread thread) {
		String opDetail = "<opDetail><recordInfo>";// 详细信息
		try {
			if (thread != null) {
				opDetail += "<fieldInfo>" // 字段内容
						+ "<fieldChName>主题</fieldChName>" // 字段中文名
						+ "<fieldEnName>title</fieldEnName>" // 字段英文名
						+ "<fieldContent>"
						+ thread.getTitle()
						+ "</fieldContent>" // 字段内容
						+ "</fieldInfo>"
						+ "<fieldInfo>" // 字段内容
						+ "<fieldChName>紧急程度</fieldChName>" // 字段中文名
						+ "<fieldEnName>urgentDegree</fieldEnName>" // 字段英文名
						+ "<fieldContent>"
						+ thread.getThreadTypeName()
						+ "</fieldContent>" // 字段内容
						+ "</fieldInfo>"
						+ "<fieldInfo>" // 字段内容
						+ "<fieldChName>有效期</fieldChName>" // 字段中文名
						+ "<fieldEnName>availTime</fieldEnName>" // 字段英文名
						+ "<fieldContent>"
						+ this.getLocalString(StaticMethod.null2int(thread
								.getValidity()))
						+ "</fieldContent>" // 字段内容
						+ "</fieldInfo>"
						+ "<fieldInfo>"
						+ "<fieldChName>公告内容</fieldChName>" // 字段中文名
						+ "<fieldEnName>noticeDesc</fieldEnName>" // 字段英文名
						+ "<fieldContent>"
						+ this.getString(thread.getContent())
						+ "</fieldContent>" // 字段内容
						+ "</fieldInfo>"
						+ "<fieldInfo>"
						+ "<fieldChName>是否需要回复</fieldChName>" // 字段中文名
						+ "<fieldEnName>isNeedReploy</fieldEnName>" // 字段英文名
						+ "<fieldContent>" + thread.getReplyName()
						+ "</fieldContent>" // 字段内容
						+ "</fieldInfo></recordInfo>";

			} else {
				opDetail = "</recordInfo><opDetail>";
			}
			opDetail += "</opDetail>";
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("opDetail:" + opDetail);
		return opDetail;
	}

	public String getOpDetail(ThreadHistory thread) {
		String opDetail = "<opDetail><recordInfo>";// ÏêÏ¸ÐÅÏ¢

		if (thread != null) {
			opDetail += "<fieldInfo>" // ×Ö¶ÎÄÚÈÝ
					+ "<fieldChName>回复结果</fieldChName>" // 字段中文名
					+ "<fieldEnName>reployResult</fieldEnName>" // ×Ö¶ÎÓ¢ÎÄÃû
					+ "<fieldContent>"
					+ thread.getReplyresultName()
					+ "</fieldContent>" // ×Ö¶ÎÄÚÈÝ
					+ "</fieldInfo>"
					+ "<fieldInfo>" // ×Ö¶ÎÄÚÈÝ
					+ "<fieldChName>回复意见</fieldChName>" // ×Ö¶ÎÖÐÎÄÃû
					+ "<fieldEnName>reployDesc</fieldEnName>" // ×Ö¶ÎÓ¢ÎÄÃû
					+ "<fieldContent>"
					+ thread.getComments()
					+ "</fieldContent>" // ×Ö¶ÎÄÚÈÝ
					+ "</fieldInfo>" + "</recordInfo></opDetail>";

		} else {
			opDetail += "</opDetail>";
		}

		System.out.println("opDetail:" + opDetail);
		return opDetail;
	}

	// SC-501-081111-10002
	public String getSerialNo() {
		String serialNo = BulletinMgrLocator.getAttributes()
				.getBulletinSerSupplier()
				+ "-"
				+ BulletinMgrLocator.getAttributes().getBulletinSerialNoType()
				+ "-" + this.getLocalString() + "-";// 
		IThreadManager msg = (IThreadManager) ApplicationContextHolder
				.getInstance().getBean("IthreadManager");
		String max = msg.getMaxSerialNo(serialNo);
		if (max != null && !"".equals(max)) {
			max.subSequence(max.length() - 5, max.length());
			int count = java.lang.Integer.parseInt(String.valueOf(max
					.subSequence(max.length() - 5, max.length())));
			serialNo += String.valueOf(count + 1);
		}
		return serialNo;
	}

	public static int dateDiff(String fromDate, String toDate)
			throws ParseException {
		int days = 0;

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		Date from = df.parse(fromDate);
		Date to = df.parse(toDate);
		if (to.getTime() > from.getTime()) {
			days = (int) Math.abs((to.getTime() - from.getTime())
					/ (24 * 60 * 60 * 1000));
		} else {
			days = 1;
		}

		return days;
	}

	public static int dateDifftmp(String fromDate, String toDate)
			throws ParseException {
		int days = 0;
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		Date from = df.parse(fromDate);
		Date to = df.parse(toDate);
		if (to.getTime() > from.getTime())
			days = (int) Math.abs((to.getTime() - from.getTime()) / 0x5265c00L);
		else
			days = 1;
		return days + 1;
	}

	public String getLocalString(String dete) {
		java.util.Date currentDate = new java.util.Date();
		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
				dete);
		String date = dateFormat.format(currentDate);

		return date;
	}

	public static String getLocalString(int day) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, day);

		java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
				"yyyyMMdd HH:mm:ss");
		String date = dateFormat.format(c.getTime());

		return date;
	}

	public static String getString(String str) {
		if (!"".equals(str)) {

			str = str.replace("<p>", "");
			str = str.replace("</p>", "");
		}

		return str;
	}

	public static String dayToDate(String dayCount) {
		String date = "";
		Calendar calendar = Calendar.getInstance();
		int day = calendar.get(6);
		int i = Integer.parseInt(dayCount);
		calendar.set(6, day + i);
		DateFormat format = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		date = format.format(calendar.getTime());
		return date;
	}
	/**
	 * 调用公客系统接口的公共调用方法！
	 * @param url
	 * @param methodName
	 * @param msgStr
	 * @return
	 */
	public static String gkAgencyMethod(String url,String methodName,String msgStr){
		String retrunStr="";
		Service service = new Service();
		Call call =null;
		
		try{
			call = (Call)service.createCall();
			call.setTargetEndpointAddress(url);
			call.setOperationName(new QName(url,methodName));
			retrunStr = (String)call.invoke(new Object[]{msgStr});
//			BocoLog.info("调用公客系统接口-com.boco.eoms.util.InterfaceUtil", 531, retrunStr);
		}catch (ServiceException e) {
			// TODO: handle exception
			BocoLog.error("调用公客系统接口-com.boco.eoms.util.InterfaceUtil", 534,"统一接口:"+methodName+";访问地址:"+url+"亿阳传送的字符串："+msgStr+"--错误原因："+e.getMessage());
			e.printStackTrace();
			return retrunStr;
			
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			BocoLog.error("调用公客系统接口-com.boco.eoms.util.InterfaceUtil", 540,"统一接口:"+methodName+";访问地址:"+url+"亿阳传送的字符串："+msgStr+"--错误原因："+e.getMessage());
			e.printStackTrace();
			return retrunStr;
		}
		/* try {
    	     //创建一个端点，直接引用远程的wsdl文件： 目前引用山东服务器地址。
    	     String endpoint = url;
             
    	     //创建一个Service实例
             Service service = new Service();
             
             //创建Call实例，由service创建call（call就是要进行调用的对象）
             Call call = (Call) service.createCall();
             
             //为Call设置服务的位置，call对象的属性targetEndpointAddress(读取目标地址)
             call.setTargetEndpointAddress(endpoint);
             
             //operationName（操作方法就是要调用的方法)startInvoke为服务器端的接口名！
             call.setOperationName(methodName);
             
    
             //参数设定： userName为参数名；XSD_DATE设置参数类型:String； 参数模式：'IN' or 'OUT'
             call.addParameter("userName",org.apache.axis.encoding.XMLType.XSD_DATE,
                  javax.xml.rpc.ParameterMode.IN);
             
             //接口的参数,返回值类型为：String                      
             call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
             
             //设置返回类型  ，如果调用成功则返回【调用成功】
             String temp = msgStr;
             
             retrunStr = (String)call.invoke(new Object[]{temp});
             
      }
      catch (Exception e) {
    	  
			BocoLog.error("调用公客系统接口-com.boco.eoms.util.InterfaceUtil", 576,"统一接口:"+methodName+";访问地址:"+url+"传送的字符串："+msgStr+"--错误原因："+e.getMessage());
			e.printStackTrace();
      }*/
		
		return retrunStr;
	}
	
	public static void main(String[] args) {
		System.out
				.println(StaticMethod.getCurrentDateTime("yyyyMMdd HH:mm:ss"));
		// try {
		// int i=dateDiff(StaticMethod.getCurrentDateTime("yyyyMMdd
		// HH:mm:ss"),"20090612 21:30:05");
		//			
		// System.out.println(i);
		// } catch (ParseException e) {
		// e.printStackTrace();
		// }
		// dayToDate("4");
		//
		// String xml = "<opDetail>" +
		// "<recordInfo>" +
		// "<fieldInfo>"
		// + "<fieldChName>zcx1</fieldChName>"
		// + "<fieldEnName>wangwei</fieldEnName>"
		// + "<fieldContent>1001???¡è</fieldContent>" +
		// "</fieldInfo>"
		// + "<fieldInfo>" +
		// "<fieldChName>zcx</fieldChName>"
		// + "<fieldEnName>80</fieldEnName>"
		// + "<fieldContent></fieldContent>" +
		// "</fieldInfo>"
		// + "</recordInfo>" +
		// "</opDetail>";
		//		
		//		
		//		
		// xml =
		// "<opDetail><recordInfo><fieldInfo><fieldChName>???????</fieldChName><fieldEnName>alarmId</fieldEnName>"+
		// "<fieldContent>BOCO_WNMS_892717772_640202506_3081174526_2329122724</fieldContent></fieldInfo>"+
		// "<fieldInfo><fieldChName>????ID</fieldChName><fieldEnName>alarmStaId</fieldEnName><fieldContent>001-011-00-800064</fieldContent></fieldInfo>"+
		// "<fieldInfo><fieldChName>?????</fieldChName><fieldEnName>oriAlarmId</fieldEnName><fieldContent>1097868</fieldContent></fieldInfo>"+
		// "<fieldInfo><fieldChName>????</fieldChName><fieldEnName>alarmTitle</fieldEnName><fieldContent>DIGITAL
		// PATH QUALITY SUPERVISION</fieldContent></fieldInfo>"+
		// "<fieldInfo><fieldChName>??????</fieldChName><fieldEnName>alarmCreateTime</fieldEnName><fieldContent>2008-09-22
		// 18:23:00</fieldContent></fieldInfo>"+
		// "<fieldInfo><fieldChName>????</fieldChName><fieldEnName>NeType</fieldEnName><fieldContent>10101</fieldContent></fieldInfo>"+
		// "<fieldInfo><fieldChName>????</fieldChName><fieldEnName>alarmVendor</fieldEnName><fieldContent>???</fieldContent></fieldInfo>"+
		// "<fieldInfo><fieldChName>????</fieldChName><fieldEnName>neName</fieldEnName><fieldContent>??BSCE1</fieldContent></fieldInfo>"+
		// "<fieldInfo><fieldChName>????</fieldChName><fieldEnName>alarmLevel</fieldEnName><fieldContent>8</fieldContent></fieldInfo>"+
		// "<fieldInfo><fieldChName>??????</fieldChName><fieldEnName>alarmType</fieldEnName><fieldContent>?????</fieldContent></fieldInfo>"+
		// "<fieldInfo><fieldChName>??????</fieldChName><fieldEnName>alarmSubType</fieldEnName><fieldContent>??????</fieldContent></fieldInfo>"+
		// "<fieldInfo><fieldChName>????</fieldChName><fieldEnName>alarmProvince</fieldEnName><fieldContent>???</fieldContent></fieldInfo>"+
		// "<fieldInfo><fieldChName>????</fieldChName><fieldEnName>alarmRegion</fieldEnName><fieldContent>???</fieldContent></fieldInfo>"+
		// "<fieldInfo><fieldChName>????</fieldChName><fieldEnName>alarmLocation</fieldEnName><fieldContent>90RBL2</fieldContent></fieldInfo>"+
		// "<fieldInfo><fieldChName>????</fieldChName><fieldEnName>alarmDetail</fieldEnName><fieldContent>????????:6"+
		// "????????:7"+
		// "????:"+
		// "%a"+
		// "-RecordId=1097868"+
		// "-SystemType=AXE"+
		// "-EventTime=20080922182300"+
		// "-ETtext=Quality of service"+
		// "-EventType=2"+
		// "-ObjectOfReference=SubNetwork=ONRM_RootMo,SubNetwork=AXE,ManagedElement=WHBSCE1"+
		// "-ProposedRepairAction=-2"+
		// "-ProblemText=*** ALARM 642 A3/APT \"WHBSCE1R12/GAP/\"U 080922 1823"+
		// ":DIGITAL PATH QUALITY SUPERVISION"+
		// ":SES"+
		// ":DIP DIPPART SESL2 QSV SECTION DATE TIME"+
		// ":90RBL2 1 11 080922 143303"+
		// ":END"+
		// "-ProblemData=PRCA=42"+
		// "-LoggingTime=20080922182419"+
		// "-PreviousSeverity=-1"+
		// "-FMXFlag=1"+
		// "-FMXGenerated=1"+
		// "-FmAlarmId=1097868"+
		// "%A</fieldContent></fieldInfo><fieldInfo><fieldChName>??????</fieldChName><fieldEnName>createType</fieldEnName><fieldContent>0</fieldContent></fieldInfo><fieldInfo><fieldChName>???</fieldChName><fieldEnName>sender</fieldEnName><fieldContent></fieldContent></fieldInfo></recordInfo></opDetail>";
		//		
		//		
		//		
		// System.out.println("xml:" +"<?xml version=\"1.0\"
		// encoding=\"gb2312\"?>"+xml);
		// HashMap sheetMap = new HashMap();
		// sheetMap = doc.xmlElements(xml, sheetMap, "FieldContent");
		// System.out.println("zcx:" + sheetMap.get("80"));
		// System.out.println("zcx1:" + sheetMap.get("wangwei"));
		//		
		// xml = "<opDetail>"+
		// "<recordInfo>"+
		// "<fieldInfo>" +
		// "<fieldChName>EOMS???¡ì???¡è?????¨¬??</fieldChName>" +
		// "<fieldEnName>userName</fieldEnName>" +
		// "<fieldContent>test11</fieldContent>" +
		// "</fieldInfo>" +
		//
		// "</recordInfo>" +
		// "<recordInfo>" +
		// "<fieldInfo>" +
		// "<fieldChName>EOMS???¡ì???¡è??£¤????</fieldChName>" +
		// "<fieldEnName>userPassword</fieldEnName>" +
		// "<fieldContent>111</fieldContent>" +
		// "</fieldInfo>" +
		// "</recordInfo>" +
		// "</opDetail>";
		//		
		// sheetMap = new HashMap();
		// sheetMap=doc.xmlElements(xml,sheetMap,"FieldContent");
		// System.out.println("userName:"+sheetMap.get("userName"));
		//
		// System.out.println("userPassword:"+sheetMap.get("userPassword"));
		//		
		// InterfaceUtil doc = new InterfaceUtil();
		// String xml ="<attachRef>"
		// + "<attachInfo>"
		// +"<attachName>¡§|?????</attachName>"
		// + "<attachURL>http://1</attachURL>"
		// + "<attachLength>1001</attachLength>" +
		// "</attachInfo>"
		// + "<attachInfo>"
		// +"<attachName>¡§|?????</attachName>"
		// + "<attachURL>http://2</attachURL>"
		// + "<attachLength>1001</attachLength>" +
		// "</attachInfo>"+
		// "</attachRef>";
		// List list = doc.getAttachRefFromXml(xml);
		// System.out.println(list.get(0).getClass().getName());
		//		
		// try{
		// CrmLoader cd = new CrmLoader();
		// cd.load();
		//			
		// EomsLoader loader = new EomsLoader();
		// loader.getCrmService("031", "001");
		//			
		// boolean result=UserMgrLocator.getTawSystemUserMgr().getUser("test11",
		// "111");
		// System.out.println(""+result);
		// }catch(Exception err){
		// err.printStackTrace();
		// }
	}
	/**
	 * @param xmlDoc
	 * @return AttachRef
	 */
	public List<String> getReturnXmlMsg(String xmlDoc) {
		if (xmlDoc == null || xmlDoc.length() == 0)
			return null;
		NodeList UDSObjectList = null;
		Node UDSObject = null;
		List<String> array = new ArrayList<String>();
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			dbf.setNamespaceAware(true);
			if (xmlDoc.indexOf("?xml") <= 0)
				xmlDoc = "<?xml version=\"1.0\" encoding=\"gb2312\"?>" + xmlDoc;
			DocumentBuilder dc = dbf.newDocumentBuilder();
			Document doc = dc.parse(new InputSource(new StringReader(xmlDoc)));
			String xpath = "//msg/data";

			UDSObjectList = XPathAPI.selectNodeList(doc, xpath);
			if (UDSObjectList.getLength() > 0) {
				for (int i = 0; i < UDSObjectList.getLength(); i++) {
					UDSObject = UDSObjectList.item(i);
					
					xpath = "Return_Code";

					array.add(XPathAPI.selectSingleNode(UDSObject,
							xpath).getFirstChild().getNodeValue());

					xpath = "Return_Msg";
					array.add(XPathAPI.selectSingleNode(UDSObject,
							xpath).getFirstChild().getNodeValue());
				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}
		return array;
	}
	
	/**
	 * 	 判断字符串是否为纯数字
	 	 * @author WANGJUN
	 	 * @title: isNumeric
	 	 * @date Jun 30, 2016 10:10:13 AM
	 	 * @param str
	 	 * @return boolean
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}
}
