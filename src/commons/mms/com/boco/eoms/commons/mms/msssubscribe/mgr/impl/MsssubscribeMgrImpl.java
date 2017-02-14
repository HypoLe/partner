package com.boco.eoms.commons.mms.msssubscribe.mgr.impl;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;

import com.boco.eoms.commons.mms.base.config.Reports;
import com.boco.eoms.commons.mms.base.util.MMSConstants;
import com.boco.eoms.commons.mms.mmsreport.model.Mmsreport;
import com.boco.eoms.commons.mms.mmsreporttemplate.mgr.MmsreportTemplateMgr;
import com.boco.eoms.commons.mms.mmsreporttemplate.model.MmsreportTemplate;
import com.boco.eoms.commons.mms.msssubscribe.dao.MsssubscribeDao;
import com.boco.eoms.commons.mms.msssubscribe.mgr.MsssubscribeMgr;
import com.boco.eoms.commons.mms.msssubscribe.model.Msssubscribe;
import com.boco.eoms.commons.mms.statreport.mgr.StatreportMgr;
import com.boco.eoms.commons.mms.statreport.model.Statreport;
import com.boco.eoms.commons.statistic.base.reference.ApplicationContextHolder;
import com.boco.eoms.commons.statistic.base.reference.ParseXmlService;
import com.boco.eoms.commons.statistic.base.reference.StaticMethod;
import com.boco.eoms.message.model.MmsContent;
import com.boco.eoms.message.service.impl.MsgServiceImpl;
import com.boco.eoms.message.util.MsgConstants;

/**
 * <p>
 * Title:彩信报订阅
 * </p>
 * <p>
 * Description:彩信报系统
 * </p>
 * <p>
 * Fri Feb 20 11:32:20 CST 2009
 * </p>
 * 
 * @author 李志刚
 * @version 3.5
 * 
 */
public class MsssubscribeMgrImpl implements MsssubscribeMgr {
 
	public List getMmsreport(Mmsreport mmsreoprt) {
		String mmsreport_template_id = mmsreoprt.getMmsreport_template_id();
		return msssubscribeDao.getMsssubscribeForMmsreportTemplateId(mmsreport_template_id);
	}

	public String sendMmsreport(Msssubscribe msssubscribe,Mmsreport mmsreport) throws Exception {
		String status = mmsreport.getSendStatus();
		if(msssubscribe == null)
		{
			return status;
		}
		
		//MSG 建立发送内容
//		MmsreportTemplateMgr mmsreportTemplateMgr = (MmsreportTemplateMgr)ApplicationContextHolder.getInstance().getBean("mmsreportTemplateMgr");
//	    MmsreportTemplate  mmsreportTemplate  = mmsreportTemplateMgr.getMmsreportTemplate(msssubscribe.getMmsreport_templateId());
	    StatreportMgr statreportMgr = (StatreportMgr)ApplicationContextHolder.getInstance().getBean("statreportMgr");
	    List statlist = statreportMgr.getStatreportForMmsreportId(mmsreport.getId());
	    
	    List mmsContentList = new ArrayList();
	    int mms_index = 1;
	    //彩信报首页图片
//	    String titlePath = StaticMethod.getFilePathForUrl(MMSConstants.FIST_PAGE_GIF);
//	    MmsContent title_pic_mmsContent = new MmsContent();
//	    title_pic_mmsContent.setContent(titlePath);
//	    title_pic_mmsContent.setContentType(MsgConstants.MMS_TYPE_GIF);
//	    title_pic_mmsContent.setPosition(String.valueOf(mms_index));
//	    title_pic_mmsContent.setDeleted("0");
//	    mmsContentList.add(title_pic_mmsContent);
//	    mms_index++;
	    
	    //加入彩信说明文字
	    MmsreportTemplateMgr mmsreportTemplateMgr = (MmsreportTemplateMgr)ApplicationContextHolder.getInstance().getBean("mmsreportTemplateMgr");
	    MmsreportTemplate  mmsreportTemplate  = mmsreportTemplateMgr.getMmsreportTemplate(msssubscribe.getMmsreport_templateId());
	    MmsContent titile_desc_mmsContent1 = new MmsContent();
	    titile_desc_mmsContent1.setContent(mmsreportTemplate.getMmsReportDesc());
	    titile_desc_mmsContent1.setContentType(MsgConstants.MMS_TYPE_TEXT);
	    titile_desc_mmsContent1.setPosition(String.valueOf(mms_index));
	    titile_desc_mmsContent1.setDeleted("0");
	    mmsContentList.add(titile_desc_mmsContent1);
	    mms_index++;
		
	    for(int i=0;i<statlist.size();i++)
	    {
	    	Statreport statreport = (Statreport)statlist.get(i);
	    	String picUrl = statreport.getPicID();
	    	String footInfo = statreport.getFootInfo();
	    	
	    	if(picUrl != null && !picUrl.equalsIgnoreCase(""))
	    	{
			    MmsContent mmsContent = new MmsContent();
			    String path = MMSConstants.MMSREPORT_FILE_RELATIVEPATH + picUrl;
			    if(!path.startsWith("/"))
			    {
			    	path = "/" + path;
			    }
			    mmsContent.setContent(path);//绝对路径
			    mmsContent.setContentType(MsgConstants.MMS_TYPE_GIF);
			    mmsContent.setPosition(String.valueOf(mms_index));
			    mmsContent.setDeleted("0");
			    mmsContentList.add(mmsContent);
			    mms_index++;
	    	}
	    	
	    	if(footInfo != null && !footInfo.trim().equalsIgnoreCase(""))
	    	{
				MmsContent mmsContent = new MmsContent();
			    mmsContent.setContent(footInfo);
			    mmsContent.setContentType(MsgConstants.MMS_TYPE_TEXT);
			    mmsContent.setPosition(String.valueOf(mms_index));
			    mmsContent.setDeleted("0");
			    mmsContentList.add(mmsContent);
				mms_index++;
	    	}
	    }
	    
	    //发送彩信
	    //mod by lizhenyou begin 2009-6-30
	    MsgServiceImpl   msgService = new MsgServiceImpl();
	    String sendUserStr = "";
	    // 接收人可以有多个接收人
		String[] sendUsers = null;
		if( msssubscribe.getReceivePerson()!=null&& !msssubscribe.getReceivePerson().equals("")){
			sendUsers = msssubscribe.getReceivePerson().split(","); 
			for(int i=0;i<sendUsers.length;i++)
		    {
		    	sendUserStr += "1,"  + sendUsers[i];
		    	
		    	if(i != sendUsers.length-1)
		    	{
		    		sendUserStr +="#";
		    	}
		    }
		}
	    
	    //读取配置文件
	    String configpath = MMSConstants.REPORT_CONFIG;
		Reports r = (Reports) ParseXmlService.create().xml2object(
				Reports.class, StaticMethod.getFilePathForUrl(configpath));
		//消息平台为彩信分配的32位id
		String serviceId = r.getServiceId();//8aa081d61ff852bf011ff857e92b0004
		//接收时间
		String receiveTime = msssubscribe.getReceiveTime();
		Calendar rightNow = Calendar.getInstance();
		int year = rightNow.get(Calendar.YEAR);
		int month = rightNow.get(Calendar.MONTH) + 1;
		int date = rightNow.get(Calendar.DAY_OF_MONTH);
		receiveTime = year + "-" + month + "-" + date + " " + receiveTime;
		// 唯一标识
		String buizId = msssubscribe.getId();
		System.out.println("serviceId :" + serviceId);
		System.out.println("buizId :" + buizId);
		System.out.println("sendUserStr :" + sendUserStr);
		System.out.println("mmreportName :" + msssubscribe.getMmreportName());
		System.out.println("receiveTime :" + receiveTime);
//		System.out.println("mmsContentList  :" + mmsContentList);
		//发送彩信（调用消息平台）
		status = msgService.sendMms(serviceId, 
	    		buizId, 
	    		sendUserStr, //orgIds 格式：1,admin#1,sunshengtai#2,151#3,185(其中1代表人，2代表部门,3代表角色ID
	    		msssubscribe.getMmreportName(),
	    		receiveTime,mmsContentList);
	  //mod end
	    
		return status;
	}
	
	/**
	 * 邮件发送，根据服务订阅信息发送邮件
	 * 
	 * @param serviceId
	 *            消息系统中订阅的服务id
	 * @param subject 
	 * 			  邮件主题
	 * @param content
	 *            邮件内容
	 * @param buizId
	 *            具体调用消息服务的业务流水号，如：作业计划id（主键），主要为取消消息发送
	 * @param addresser
	 *            邮件的发件人 email格式 
	 * @param orgIds 
	 * 			  发送人员或者部门的串 格式：1,admin#1,sunshengtai#2,254 （其中1代表人员  2代表部门 254代表部门id）             
	 * @param dispatchTime 
	 * 			  业务发送时间点
	 * @param accessoriesUrl
	 * 			  附件url地址
	 * @return success,fail（成功与否）
	 */
    public String sendEmail(String serviceId, String subject, String content, String buizId, String addresser, String orgIds, String dispatchTime, String accessoriesUrl)
    {
    	return "发送彩信";
    }

	private MsssubscribeDao  msssubscribeDao;
 	
	public MsssubscribeDao getMsssubscribeDao() {
		return this.msssubscribeDao;
	}
 	
	public void setMsssubscribeDao(MsssubscribeDao msssubscribeDao) {
		this.msssubscribeDao = msssubscribeDao;
	}
 	
    public List getMsssubscribes() {
    	return msssubscribeDao.getMsssubscribes();
    }
    
    public Msssubscribe getMsssubscribe(final String id) {
    	return msssubscribeDao.getMsssubscribe(id);
    }
    
    public void saveMsssubscribe(Msssubscribe msssubscribe) {
    	msssubscribeDao.saveMsssubscribe(msssubscribe);
    }
    
    public void removeMsssubscribe(final String id) {
    	msssubscribeDao.removeMsssubscribe(id);
    }
    
    public Map getMsssubscribes(final Integer curPage, final Integer pageSize,
			final String whereStr) {
		return msssubscribeDao.getMsssubscribes(curPage, pageSize, whereStr);
	}
    
    public static void main(String[] args) throws FileNotFoundException, DocumentException
    {
//    	String fistPagePath = StaticMethod.getFilePathForUrl(MMSConstants.FIST_PAGE_GIF);
//    	System.out.print(fistPagePath);
//    	String path="E:/SendConfig.xml";
//    	SendConfig.getSendConfig(path);
    	
//    	String[] sendUsers = {"admin","woli","ddd","mku"};
//    	String sendUserStr = "";
//	    for(int i=0;i<sendUsers.length;i++)
//	    {
//	    	sendUserStr += "1,"  + sendUsers[i];
//	    	
//	    	if(i != sendUsers.length-1)
//	    	{
//	    		sendUserStr +="#";
//	    	}
//	    	                                 
//	    }
//	    System.out.println(sendUserStr);
	    
	    Calendar rightNow = Calendar.getInstance();
		int year = rightNow.get(Calendar.YEAR);
		int month = rightNow.get(Calendar.MONTH) + 1;
		int date = rightNow.get(Calendar.DAY_OF_MONTH);
		
		String receiveTime = year + "-" + month + "-" + date + " " + "10:05:00";
		System.out.println(receiveTime);
		
    }
}
