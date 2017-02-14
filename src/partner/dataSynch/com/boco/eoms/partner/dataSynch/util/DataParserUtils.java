package com.boco.eoms.partner.dataSynch.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import sun.net.TelnetInputStream;
import sun.net.ftp.FtpClient;
import sun.net.ftp.FtpLoginException;

import com.boco.eoms.partner.dataSynch.model.CheckFile;
import com.boco.eoms.partner.dataSynch.service.DeliveryRequestRequestService;
import com.google.common.base.Splitter;
import com.google.common.io.Files;

/** 
 * Description: 工具类
 * Copyright:   Copyright (c)2012
 * Company:     BOCO
 * @author:     LiuChang
 * @version:    1.0
 * Create at:   Apr 1, 2012 11:32:27 AM
 */
public class DataParserUtils {
	public final static String CLASS_PATH_FLAG = "classpath:";             //classpath标识
	public final static URL CLASS_PATH_URL = DataParserUtils.class.getResource("/"); //classpath绝对URL
	private final String dataReadyRequestUri = "http://"+DataSynchConstant.getLocalIp()+":"+DataSynchConstant.getLocalPort()+"/"
				+DataSynchConstant.getLocalProject()+"/services/deliveryReadyRequestService?wsdl";
	/**
	 * 获取文件绝对路径
	 * @param filePath 形如classpath:config/person.xml的文件路径
	 * @return
	 */
	public static String getAbstractPath(String filePath) {
		if (filePath != null) {
			if (filePath.length() > CLASS_PATH_FLAG.length()) {
				if (CLASS_PATH_FLAG.equals(filePath.substring(0, CLASS_PATH_FLAG.length()))) {
					filePath = CLASS_PATH_URL.getFile() + filePath.substring(CLASS_PATH_FLAG.length());
				}
			}
		}
		return filePath;
	}
	
	/**
	 * FTP文件批量下载
	 * @param ftpserver FTP服务器IP地址
	 * @param userLogin FTP服务器登录用户名
	 * @param pwdLogin  FTP服务器登录密码
	 * @param fileNameList 保存为本地文件名集合
	 * @param filepath  本地文件的存储路径
	 * @param serverPathList FTP服务器文件的全路径集合
	 * @return
	 */
	public static String downloadFileByFtp(String ftpserver,String userLogin,String pwdLogin,
			List<String> fileNameList, String filepath,List<String> serverPathList) {
		String retMessage = "";
		FtpClient fc = new FtpClient();
		retMessage = connectToFtpServer(fc,ftpserver,userLogin,pwdLogin);
		if (!retMessage.equals("")) {
			return retMessage;
		}
		TelnetInputStream is = null;
		File dir = new File(filepath);
		/*创建目录 begin*/
		if(!dir.exists()) {
			dir.mkdirs();
		}
		/*创建目录 end*/
		try {
			for (int i=0;i<fileNameList.size();i++) {
				String serverPath = serverPathList.get(i);
				String fileName=fileNameList.get(i);
				is = fc.get(serverPath);
				File file_out = new File(dir,fileName);
				FileOutputStream os = new FileOutputStream(file_out);
				byte[] bytes = new byte[1024];
				int c;
				while ((c = is.read(bytes)) != -1) {
					os.write(bytes, 0, c);
				}
				is.close();
				os.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			retMessage = "FTP下载文件时发生文件读写错误：" + e.getMessage();
			System.out.println("FTP下载文件时发生文件读写错误：" + e.getMessage());
		}finally{
			try {
				fc.closeServer();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return retMessage;
	}
	
	/**
	 * FTP单个文件下载
	 * @param ftpserver FTP服务器IP地址
	 * @param userLogin FTP服务器登录用户名
	 * @param pwdLogin  FTP服务器登录密码
	 * @param fileName  下载保存后的本地文件名，如：aa.zip
	 * @param filepath  本地文件的存储路径
	 * @param serverPath FTP服务器文件的全路径，如：a/b/c/d.zip
	 * @return
	 */
	public static String downloadFileByFtp(String ftpserver,String userLogin,String pwdLogin,
			String fileName, String filepath,String serverPath) {
		String retMessage = "";
		FtpClient fc = new FtpClient();
		retMessage = connectToFtpServer(fc,ftpserver,userLogin,pwdLogin);
		if (!retMessage.equals("")) {
			return retMessage;
		}
		try {
			TelnetInputStream is = fc.get(serverPath);
			File dir = new File(filepath);
			/*创建目录 begin*/
			if(!dir.exists()) {
				dir.mkdirs();
			}
			/*创建目录 end*/
			File file_out = new File(dir,fileName);
			FileOutputStream os = new FileOutputStream(file_out);
			byte[] bytes = new byte[1024];
			int c;
			while ((c = is.read(bytes)) != -1) {
				os.write(bytes, 0, c);
			}
			is.close();
			os.close();
			fc.closeServer();
		} catch (Exception e) {
			e.printStackTrace();
			retMessage = "下载文件：" + fileName + "时发生文件读写错误：" + e.getMessage();
			System.out.println("获取文件时发生错误：" + e.getMessage());
		}
		return retMessage;
	} 

	
	/**
	 * 解析核查文件
	 * @param checkFilePath 文件路径
	 * @return
	 */
	public static List<CheckFile> parseCheckFile(String checkFilePath) throws Exception{
		CheckFile checkFile = null;
		File file = new File(checkFilePath);
		List<CheckFile> checkFileList = new ArrayList<CheckFile>();	
//		String checkFileContent = Files.readFirstLine(file,java.nio.charset.Charset.forName("UTF-8"));
//		String checkFileContent = new LineReader(new InputStreamReader(checkFileInputStream)).readLine();
		List<String> contentList = Files.readLines(file, java.nio.charset.Charset.forName("UTF-8"));
		for (String content : contentList) {
			checkFile = new CheckFile();
			content = content.trim();
			if(!StringUtils.isEmpty(content)){
				List<String> strList = new ArrayList<String>();
				for(String token : Splitter.on(" ")
						.trimResults()      //去除分隔出的字符的前后空格
						.omitEmptyStrings() //去除分隔出的空字符
						.split(content)){
					strList.add(token);
				}
				if(strList.size() == 4){
					checkFile.setDataFileName(strList.get(0));
					checkFile.setDataFileSize(Integer.parseInt(strList.get(1)));
					checkFile.setDataFileRecordCount(Integer.parseInt(strList.get(2)));
					checkFile.setMd5Code(strList.get(3));
				}else{
					throw new Exception("核查文件格式不规范，" + checkFilePath);
				}
				checkFileList.add(checkFile);
			}
		}
		return checkFileList;
	}
	
	/**
	 * 连接到FTP服务器
	 * @param fc
	 * @param ftpserver
	 * @param userLogin
	 * @param pwdLogin
	 * @return
	 */
	public static String connectToFtpServer(FtpClient fc,String ftpserver, String userLogin,
			String pwdLogin) {
		if ((ftpserver == null) || (ftpserver.equals("")))
			return "FTP服务器名设置不正确!";
		try {
			fc.openServer(ftpserver);
			fc.login(userLogin, pwdLogin);
			fc.binary();
		} catch (FtpLoginException e) {
			return "没有与FTP服务器连接的权限,或用户名密码设置不正确!";
		} catch (IOException e) {
			return "与FTP服务器连接失败!";
		} catch (SecurityException e) {
			return "没有权限与FTP服务器连接";
		}
		return "";
	}
	
	public void deliveryRequestRequest(String baseEvent){
		try{
			String time = new org.joda.time.DateTime().toString("yyyyMMddHHmmss");
			String eventID = baseEvent + time;
			String systemID = baseEvent;
			String filter = baseEvent + ".null";
			DeliveryRequestRequestService service = new DeliveryRequestRequestService();
			service.deliveryRequestRequest(eventID,systemID,"",filter,dataReadyRequestUri);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
