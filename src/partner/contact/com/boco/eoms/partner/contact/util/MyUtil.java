package com.boco.eoms.partner.contact.util;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import sun.misc.BASE64Decoder;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.read.biff.BiffException;
import jxl.write.Border;
import jxl.write.BorderLineStyle;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.common.properties.XMLProperties;
import com.boco.eoms.commons.statistic.base.config.report.Data;
import com.boco.eoms.commons.system.dict.service.ID2NameService;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.commons.system.user.service.ITawSystemUserManager;
import com.boco.eoms.commons.util.xml.XmlManage;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcService;
import com.boco.eoms.deviceManagement.common.utils.CommonSqlHelper;
import com.boco.eoms.message.mgr.ISmsMonitorManager;
import com.boco.eoms.message.mgr.IVoiceMonitorManager;
import com.boco.eoms.partner.contact.model.ContactMain;
import com.boco.eoms.partner.contact.model.ContactTask;
import com.boco.eoms.partner.contact.util.smsclient.SMSServiceLocator;
import com.boco.eoms.partner.contact.util.smsclient.SMSServiceSoap11BindingStub;
import com.boco.eoms.sheet.base.util.Constants;
import com.boco.eoms.sheet.base.util.SheetAttributes;
import com.boco.eoms.sheet.base.util.SheetStaticMethod;
	/**
 * <p>
 * Title:常用方法
 * </p>
 * <p>
 * Description:常用方法
 * </p>
 * <p>
 * Jul 25, 2012 2:33:41 PM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
public class MyUtil {
	 public static final String DEFAULT_workProcessName="ContactMsgProcess"; // 即时提醒
	 public static final String SMS_SERVICE_INSTANT="smsServiceId.instant"; // 即时提醒
	 public static final String SMS_SERVICE_PREOVERTIME_ACCEPT="smsServiceId.preOverTime.accept"; //接单超时前提醒
	 public static final String SMS_SERVICE_PREOVERTIME_DEAL="smsServiceId.preOverTime.deal"; //处理超时前提醒
	 public static final String SMS_SERVICE_POSTOVERTIME_ACCPT="smsServiceId.postOverTime.accept"; //处理超时后提醒
	 public static final String SMS_SERVICE_POSTOVERTIME_DEAL="smsServiceId.postOverTime.deal"; //处理超时后提醒	 
	 
	/**
	 * 判断字符串是否为空
	 * @param str
	 * @return 空：true
	 */
	public static boolean isEmpty(String str){
		   if("".equals(StaticMethod.null2String(str)))
			   return true;
		   else
			   return false;
	   }
	/**
	 * 字符串2字符串数组
	 * @param str
	 * @param regex 要分割字符
	 * @return
	 */
	public static String[] getStrings(String str,String regex){
		if(!isEmpty(str))
			return str.split(regex);
		else
			throw new RuntimeException("字符串为空");
	}
	/**
	 * 当前时间
	 * @param time
	 * @return
	 */
	 public static String getNowTime(){
		 Date time = new Date();
		 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 String timeStr ;
		 try {
			 timeStr = dateFormat.format(time);
		} catch (Exception e) {
			timeStr = "1900-01-01 00:00:00";
		}
		 return timeStr;
	 }
	 
	 public static boolean isNum(String str){
		 str = StaticMethod.null2String(str);
		 return str.matches("^\\d+(\\.{1}\\d+)?$");
	 }
	 /**
	  * 业务联系函 编码生成. modify lvzhongqian 20130321：按照编码规范 1.6 业务联系函编号
	  * @return
	 * @throws FileNotFoundException 
	  */
	 public static String getCode() throws FileNotFoundException{
		 
		/* String url = StaticMethod.CLASSPATH_FLAG+"com/boco/eoms/partner/contact/config/provinceCode.xml";
		 XMLProperties properties = new XMLProperties(StaticMethod.getFilePathForUrl(url));
		String temp = properties.getElement().getChildText("code");*/
		 
		 //modify lvzhongqian 地区的编码从src\partner\sheet\base\config\applicationContext-sheet-base-attributes.xml工单的基础属性取
		 SheetAttributes sa=
			 (SheetAttributes) ApplicationContextHolder.getInstance().getBean("SheetAttributes");
		 String headCode = sa.getRegionId()+"-网通-函-";
		 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		 String date = dateFormat.format(new Date()); 
		 String beanTime = date+" 00:00:00";
		 String endTime = date+" 23:59:59";		 
		 date = date.replace("-", "");
		 date=date.substring(2);//yymmdd

		 //现在采用sequence做，每天凌晨使用轮询自动重置序列为1
		/* String countSql = "select count(*) from contact_main where publishtime >= "+CommonSqlHelper.formatDateTime(beanTime)
		 +" and publishtime<="+CommonSqlHelper.formatDateTime(endTime)+"";  */ 
		 CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");
		  int  seq = jdbcService.queryForInt("select SEQ_CONTACT.nextval  FROM (select count(*) from pnr_dept)"); 
		  DecimalFormat format = new DecimalFormat("00000");
		  String seqStr = format.format(seq);
		  System.out.println(headCode+date+"-"+seqStr);
		 
		 return headCode+date+"-"+seqStr;
	 }
	 
	 
	  /**
	  * 
	  * @param taskOwnerIds
	  * @param taskOwnerNames
	  * @return  list
	  */
	 public static List<ContactTask> getTaskFromStr(ContactMain cMain,String taskOwnerIds,String taskOwnerNames){
		 ContactTask temp;
		 ArrayList<ContactTask> list = new ArrayList<ContactTask>();
		 String id[] = MyUtil.getStrings(taskOwnerIds, ",");
		 String name[] = MyUtil.getStrings(taskOwnerNames, ",");
		 
		 //阅知人  部门
		 //发送短信给的对象，可能有三种：人员、部门、角色，以下判断对象的类型. 此处现只有2种：人员、部门
		 ID2NameService service = (ID2NameService) ApplicationContextHolder.getInstance().getBean("ID2NameGetServiceCatch");
		 for (int i = 0 ,length = id.length; i < length; i++) {			 		
			 if(!"".equals((service.id2Name(id[i],"tawSystemUserDao")))){//用户
				 temp = new ContactTask();
				 temp.setTaskOwnerId(id[i]);
				 temp.setTaskOwnerName(name[i]);
				 temp.setTaskOwnerType(Type.USER);
			     temp.setTaskType(Type.PUBLISHING);
			     list.add(temp); 			  
			 }
			 else{//部门  //if(MyUtil.isNum(id[i])){//部门
				 //根据部门查找该部门下的所有用户
				 ITawSystemUserManager tawUserMgr = (ITawSystemUserManager)ApplicationContextHolder.getInstance().getBean( "itawSystemUserManager");
				 List listUsers = tawUserMgr.getUserBydeptids(id[i]);
				 if(listUsers!=null&&listUsers.size()>0){
					 for(int ii=0;ii<listUsers.size();ii++){
						 TawSystemUser user = (TawSystemUser)listUsers.get(ii);
						 temp = new ContactTask();
						 temp.setTaskOwnerType(Type.USER);
						 temp.setTaskType(Type.PUBLISHING);
						 temp.setTaskOwnerId(user.getUserid());
						 temp.setTaskOwnerName(user.getUsername());
						 list.add(temp); 					
					 }
				 }
			 }
		}
		 return list;
	 }
	 
	 public static void WriteExcel(List dataList,String sheetName ,OutputStream ops) 
	 throws BiffException, IOException,RowsExceededException, WriteException {

		    WritableWorkbook  wwb = Workbook.createWorkbook(ops);
		    WritableSheet sheet = wwb.createSheet(sheetName, 0);
		    
		    jxl.write.WritableFont head_wf = new jxl.write.WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD);
			head_wf.setColour(Colour.RED);
			jxl.write.WritableCellFormat head_wcf = new jxl.write.WritableCellFormat(head_wf);
			head_wcf.setAlignment(Alignment.CENTRE);
			
		    jxl.write.WritableCellFormat td_wcf = new jxl.write.WritableCellFormat();
			td_wcf.setAlignment(Alignment.CENTRE);
			td_wcf.setVerticalAlignment(VerticalAlignment.JUSTIFY);
			
			DateFormat df0 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			sheet.setColumnView(0, 30);
			sheet.addCell(new Label(0,0,"编号", head_wcf));
			sheet.setColumnView(1, 10);
			sheet.addCell(new Label(1,0,"发布人", head_wcf));
			sheet.setColumnView(2, 25);
			sheet.addCell(new Label(2,0,"派发时间", head_wcf));
			sheet.setColumnView(3, 50);
			sheet.addCell(new Label(3,0,"主题", head_wcf));
			sheet.setColumnView(4, 25);
		    sheet.addCell(new Label(4,0,"处理时限", head_wcf));
			for (int i = 0; i < dataList.size(); i++) {
				   ContactMain cmain = (ContactMain) dataList.get(i);
				   sheet.addCell(new Label(0, i+1, cmain.getCode(), td_wcf));
				   sheet.addCell(new Label(1, i+1, cmain.getPublisherName(), td_wcf));
				   sheet.addCell(new Label(2, i+1, df0.format(cmain.getPublishTime()), td_wcf));
				   if(cmain.getIsUrgent()==1){
					   sheet.addCell(new Label(3, i+1, "加急："+cmain.getSubject(), td_wcf));
				   }else{
					   sheet.addCell(new Label(3, i+1, cmain.getSubject(), td_wcf));
				   }				   
				   sheet.addCell(new Label(4, i+1, df0.format(cmain.getDeathTime()), td_wcf));
			}
			wwb.write();
			wwb.close();
			ops.flush();
			ops.close();
		}
	 
	public static void sendMessage(ContactMain main,String taskOwner){
		String code = StaticMethod.nullObject2String(main.getCode());
		String content =  "你好，你有编号:"+code+"的业务涵需要处理!";
		 TawSystemUser tawUser;
		 ITawSystemUserManager tawUserMgr = (ITawSystemUserManager)ApplicationContextHolder.getInstance().getBean( "itawSystemUserManager");
		 try{
		     tawUser =(TawSystemUser) tawUserMgr.getUserByuserid(taskOwner);
		
			 String mobile =StaticMethod.nullObject2String( tawUser.getMobile());
			 System.out.println(content+":---------mobile---------"+mobile);
			 System.out.println(content+":--------getPhone----------"+tawUser.getPhone());
			 if(mobile.equals("")){
				 mobile = StaticMethod.nullObject2String( tawUser.getPhone());
			 }
			 if(!mobile.equals("")){
				 System.out.println(content+":------------------"+mobile);
					String urlStr = StaticMethod.nullObject2String(XmlManage.getFile("/config/send-msg-config.xml").getProperty("ServiceUrl"));
					System.out.println("短信接口url：" + urlStr);
					URL url = new URL(urlStr);
					SMSServiceSoap11BindingStub binding = (SMSServiceSoap11BindingStub)new SMSServiceLocator().getSMSServiceHttpSoap11Endpoint(url);
					binding.setTimeout(60000);
					String username = StaticMethod.nullObject2String(XmlManage.getFile("/config/send-msg-config.xml").getProperty("base.username"));
					String password = StaticMethod.nullObject2String(XmlManage.getFile("/config/send-msg-config.xml").getProperty("base.password"));
					//base64加密
					byte[] bt = (byte[])null;
					try { BASE64Decoder decoder = new BASE64Decoder();
					bt = decoder.decodeBuffer(username);
					username =  new String (username);
					bt = decoder.decodeBuffer(password);
					password =  new String (password);
					} catch (IOException e) {
						e.printStackTrace();
					}
					String licence = StaticMethod.nullObject2String(XmlManage.getFile("/config/send-msg-config.xml").getProperty("base.licence"));
					
					String result = binding.smsSend(mobile, content, username, password, licence);
					int status = StaticMethod.nullObject2int(result);
					System.out.println("短信result：" + result);
			 }
		 
		 }catch(Exception e){
			 e.printStackTrace();
		 }
		
	}

	/**
	 * 获得此模块短信服务的serviceId
	 * @param workProcessName 配置文件的处理标签名
	 * @param sms_service_SIDpath 处理标签下的serviceId属性的路径
	 * @return
	 * */
	public static String getServiceId(String workProcessName,String sms_service_SIDpath)
	{
		String url = StaticMethod.CLASSPATH_FLAG+"com/boco/eoms/partner/contact/config/contact-sms-service-info.xml";//短信服务信息配置文件
		String filePath="";
		try {
			filePath=StaticMethod.getFilePathForUrl(url);			
		} catch (Exception e) {
			System.out.println("读取业务联系函的短信配置文件错误");
			e.printStackTrace();
			return "";
		}
		String nodeInstantName=workProcessName+"."+sms_service_SIDpath;
		String instantServiceId=SheetStaticMethod.getNodeName(filePath, nodeInstantName);
		System.out.println("====instantServiceId="+instantServiceId);
		return instantServiceId;
	}
	
}
