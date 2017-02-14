package com.boco.eoms.util;

import java.util.*;
import java.io.*;
import java.text.*;

import com.boco.activiti.partner.process.model.OperationShopConfigModel;
import com.boco.eoms.base.util.ApplicationContextHolder;

import bsh.This;

/*
 * 使用该类注意事项：功能：封装对目录和文件的相关操作。
 * 2：必须先调用setUrl()方法设置文件和目录操作的绝对路径，然后调用createHtml(String content,String id)
 * 创建指定名称和内容的文件。创建的文件保存在如下格式的路径下：根目录/n/year/monthday/*.html
 * 如：在2009-10-31 创建的文件位于：/n/2009/1031/id.html
 *
 *
 * */
public class JspToHtml {
	private OperationShopConfigModel xmlConfigModel=(OperationShopConfigModel)ApplicationContextHolder.getInstance().getBean("operationShopConfigModel");
	public  String baseUrl = xmlConfigModel.getPlaceHtmlPathPrefix();
	//windows下
//	public final String baseUrl = "D:\\emostomcat\\apache-tomcat-6.0.18\\webapps\\worksheet\\boco";
//	public static String baseUrl = "/usr/boco/tomcat/apache-tomcat-6.0.24/webapps/worksheet/boco";
	//windows下

//	private String prefixFold = "\\n";
	private String prefixFold = File.separatorChar+"n";
	private String foldUrl;

	/*
	 * 类的功能：封装对目录和文件的操作
	 */
	public JspToHtml() {
	}

	/*
	 * name:setUrl function:设置目录和文件操作的时使用的根路径(web应用程序的根文件夹的绝对路径)
	 */
	public void setUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	private String getBaseUrl() {
		return baseUrl;
	};

	/*
	 * name:setFoldUrl function:设置文件目录结构字符串
	 */
	private void setFoldUrl(String fileUrl) {
		this.foldUrl = fileUrl;
	}

	private String getFoldUrl() {
		return foldUrl;
	}

	private String getPrefixFold() {
		return prefixFold;
	}

	/*
	 * name;createHtml function:创建html文件，内容是content，名称是id.html
	 * 
	 */
	public String  createHtml(String content, String id) {
		boolean bool = false;
	    String pathUrl= "";
		String cont = content;
		String id1 = generateFileNameStr();
		String fileName= id + ".html";
		if("".equals(id)){			
			fileName=id1+".html";
		}
		FileWriter toFile;
		BufferedWriter out;
		try {
			if (formatUrl()) {
				toFile = new FileWriter(getFoldUrl() + File.separatorChar+ fileName);
				out = new BufferedWriter(toFile);
				out.write(cont, 0, cont.length());
				out.close();
				toFile.close();
				bool = true;
				pathUrl=getFoldUrl() + File.separatorChar + fileName;
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		return pathUrl;
	}// createHtml
	/*
	 * name:deleteHtml function:删除指定id(文件名称去除后缀)的html文件
	 * 
	 */

	public boolean deleteHtml(String htmlId) {
		boolean bool = false;
		String name = htmlId;
		if (htmlId == null) {
			return bool;
		}
		String yearStr = htmlId.substring(0, 4);
		String monthDayStr = htmlId.substring(4, 8);
		String fileUrl = getBaseUrl() + getPrefixFold() + File.separatorChar + yearStr + File.separatorChar
				+ monthDayStr + File.separatorChar + htmlId + ".html";
		File file_del = new File(fileUrl);
		if (file_del.exists()) {
			file_del.delete();
			bool = true;// 删除成功标志
		}
		return bool;
	}

	/*
	 * name:formatUrl function:检查当前日期的文件目录结构是否存在，若不存在，则创建对应的目录结构，并且若目录结构存在，
	 * 则保存该目录结构字符串 保存
	 * 
	 */
	private boolean formatUrl() {
		/*
		 * 如果存放当前日期文件的目录结构不存在，则创建对应的目录结构
		 */
		boolean bool = false;
		File file = new File(getBaseUrl());

		if (file.exists() && file.isDirectory()) {
//			System.out.println("--sdfsd-");
			String foldStr = getTodayFilePath();

			File filePath = new File(foldStr);
			if (!filePath.exists()) {
				filePath.mkdirs();
			}
			if (filePath.isDirectory()) {
				bool = true;
				setFoldUrl(foldStr);
			}
		}
		return bool;
	}// formatUrl()
	// 存放*.html的目录结构： .../.../根目录/年/月日/*.html
	/*
	 * name:getTodayFilePath function:该方法返回文件名之前的部分呢
	 */

	private String getTodayFilePath() {
		String tPath = "";
		String dateStr[];
		dateStr = new String[2];
		String patternStr[] = { "yyyy", "MMdd" };
		dateStr[0] = new SimpleDateFormat(patternStr[0]).format(new Date());
		dateStr[1] = new SimpleDateFormat(patternStr[1]).format(new Date());
		tPath = getBaseUrl() + getPrefixFold() + File.separatorChar + dateStr[0] + File.separatorChar 
				+ dateStr[1];
//		System.out.println(tPath);
		
		return tPath;
	}

	private String generateFileNameStr() {
		String fileName = "";
		fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		return fileName;
	}
	/*
	 * main 方法测试类是否正常工作
	 * 
	 */
	public static void main(String args[]) {
		JspToHtml jspToHtml = new JspToHtml();
		String b =jspToHtml.createHtml("d", "11");
		/* b=b.replace(baseUrl, "http://localhost:8080\\examples\\boco");
		System.out.println("------b-"+b);
		System.out.println("------baseUrl-"+baseUrl);*/
		
		
	}
}