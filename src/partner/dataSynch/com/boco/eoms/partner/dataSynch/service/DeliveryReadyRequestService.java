package com.boco.eoms.partner.dataSynch.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.EOMSAttributes;
import com.boco.eoms.base.util.InterfaceDataMonitor;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.dataSynch.mgr.AbstractDataSynchMgr;
import com.boco.eoms.partner.dataSynch.model.CheckFile;
import com.boco.eoms.partner.dataSynch.model.EventMapping;
import com.boco.eoms.partner.dataSynch.util.DataParserUtils;
import com.boco.eoms.partner.dataSynch.util.DataSynchConfig;
import com.boco.eoms.partner.dataSynch.util.DataSynchConstant;
import com.boco.eoms.sequence.ISequenceFacade;
import com.boco.eoms.sequence.Sequence;
import com.boco.eoms.sequence.exception.SequenceNotFoundException;
import com.boco.eoms.sequence.util.SequenceLocator;

/** 
 * Description: 数据反馈接口(综合资源<客户端>调用属地化运维<服务端>)
 * Copyright:   Copyright (c)2012
 * Company:     BOCO
 * @author:     LiuChang
 * @version:    1.0
 * Create at:   Apr 23, 2012 4:00:58 PM
 */
public class DeliveryReadyRequestService {
	
	/**
	 * 有效参数
	 * @param eventID 事件ID，格式如：SDH_jk_ag20120509010101
	 * @param deliveryTime
	 * @param readyStatusCode
	 * @param readyStatusDescription
	 * @param workMode
	 * @param fileFormat
	 * @param charSet
	 * @param lineSeparator
	 * @param fieldSeparator
	 * @param fieldNameList
	 * @param xmlSchema
	 * @param dataInfo
	 * @param connectionString 连接字符串，如ftp://usr:pwd@192.168.0.123:21,ftp://irmsftp:Irmsftp.2k10@10.209.2.150:21
	 * @param path  FTP服务器CHK校验文件的放置目录，如/irmsftp/iface-data/irmsfile2/SDH_jk_ag20120509.CHK
	 * @param isCompressed
	 */
	public void deliveryReadyRequest(String eventID,Calendar deliveryTime,int readyStatusCode,
			String readyStatusDescription,int workMode,int fileFormat,int charSet,
			int lineSeparator,int fieldSeparator,String fieldNameList,String xmlSchema,
			String dataInfo,String connectionString,String path,boolean isCompressed){
		//创建接口日志
		InterfaceDataMonitor monitor = new InterfaceDataMonitor();
		String result = "0";
		Map<String,Object> monitorMap = new HashMap<String,Object> ();
		monitorMap.put("eventID", eventID);monitorMap.put("deliveryTime", deliveryTime);
		monitorMap.put("readyStatusCode", readyStatusCode);monitorMap.put("readyStatusDescription", readyStatusDescription);
		monitorMap.put("workMode", workMode);monitorMap.put("fileFormat", fileFormat);
		monitorMap.put("charSet", charSet);monitorMap.put("lineSeparator", lineSeparator);
		monitorMap.put("fieldSeparator", fieldSeparator);monitorMap.put("fieldNameList", fieldNameList);
		monitorMap.put("xmlSchema", xmlSchema);monitorMap.put("dataInfo", dataInfo);
		monitorMap.put("connectionString", connectionString);monitorMap.put("path", path);
		monitorMap.put("isCompressed", isCompressed);
		monitorMap.put("sheetkey", eventID);
		//获取配置信息
		DataSynchConfig dataSynchConfig = (DataSynchConfig)ApplicationContextHolder
						.getInstance().getBean("dataSynchConfig");
		
		//分割FTP连接字符串
		connectionString = connectionString.trim().substring(6);
		String[] str = connectionString.split("@");
		String userLogin = str[0].split(":")[0];
		String pwdLogin = str[0].split(":")[1];
		String ip = str[1].split(":")[0]; 
//		String port = str[1].split(":")[1]; //端口
		
		int last = path.lastIndexOf("/");
		String serverPath = path.substring(0,last + 1); //FTP服务器上文件的路径
		String checkFileName = path.substring(last + 1);  //CHK文件文件名
		//将CHK核查文件从综合资源ftp服务器下载到本地
		String ftpCheckFileResult = DataParserUtils.downloadFileByFtp(ip, userLogin, pwdLogin, 
				checkFileName, dataSynchConfig.getFilePath(), path);
		try {
			if(!"".endsWith(ftpCheckFileResult)){
				throw new Exception(ftpCheckFileResult);
			}
			//下载完成后CHK核查文件在本地的全路径
			String checkFilePath = dataSynchConfig.getFilePath() + checkFileName;
		
			List<CheckFile> checkFileList = DataParserUtils.parseCheckFile(checkFilePath);
			if(checkFileList.isEmpty()){
				result = checkFileName + "核查文件为空";
				System.out.println("------------"+result+"------------");
				return;
			}
			List<String> dataFileNameList = new ArrayList<String>();
			List<String> serverPathList = new ArrayList<String>();
			for (CheckFile cf : checkFileList) {
				dataFileNameList.add(cf.getDataFileName());
				serverPathList.add(serverPath+cf.getDataFileName());
			}
			//将ZIP数据文件从综合资源ftp服务器下载到本地
			String ftpDataFileResult = DataParserUtils.downloadFileByFtp(ip, userLogin, pwdLogin, 
					dataFileNameList, dataSynchConfig.getFilePath(), serverPathList);
			if(!"".endsWith(ftpDataFileResult)){
				throw new Exception(ftpDataFileResult);
			}
		
			//不包含日期的eventID
			String eventIDNoDate = eventID.substring(0,eventID.length()- 14);
			//根据EventID获取对应的beanName
			//String beanName = DataSynchConstant.eventMap.get(eventIDNoDate);
			String beanName = EventMapping.eventID2MgrNameMap.get(eventIDNoDate);
			if(StringUtils.isEmpty(beanName)){
				throw new Exception("事件eventID:"+eventIDNoDate+"无法找到对应的业务层实现类名称，请在DataSynchConstant类中配置");
			}
			AbstractDataSynchMgr dataSynchMgr = (AbstractDataSynchMgr) ApplicationContextHolder
						.getInstance().getBean(beanName);
			String sequenceOpen = StaticMethod.null2String(((EOMSAttributes) ApplicationContextHolder
							.getInstance().getBean("eomsAttributes")).getSequenceOpen());
			if ("true".equals(sequenceOpen)) {
				// 初始化队列
				ISequenceFacade sequenceFacade = SequenceLocator.getSequenceFacade();
				Sequence dataSynchSequence = null;
				try {
					String sequenceKey = eventIDNoDate;
					dataSynchSequence = sequenceFacade.getSequence(sequenceKey);
				} catch (SequenceNotFoundException e) {
					System.err.println("没有找到sequenceKey="+eventIDNoDate+"，请在applicationContext-sequence.xml中配置");
					e.printStackTrace();
				}			
				// 把mgr撇队列里
				sequenceFacade.put(dataSynchMgr, "dataSynch", 
						new Class[] {List.class},
						new Object[] {checkFileList}, null,dataSynchSequence);
				dataSynchSequence.setChanged();
				sequenceFacade.doJob(dataSynchSequence);
			} else {
				throw new Exception("sequence未开启，请在applicationContext-attributes.xml将sequenceOpen配置为true");
			}
		} catch (Exception err) {
			err.printStackTrace();
			result =err.getMessage();
		}finally{
			monitor.saveMonitorBySeq(monitorMap, result,  "综合资源调用合作伙伴", "deliveryReadyRequest");
		}
	}
}
