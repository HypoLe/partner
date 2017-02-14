package com.boco.eoms.workbench.infopub.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import com.boco.eoms.base.util.StaticMethod;

/**
 * <p>
 * Title:信息发布的工具类
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Date:May 22, 2008 2:01:41 PM
 * </p>
 * 
 * @author 曲静波
 * @version 3.5.1
 * 
 */
public class InfopubUtil {
	/**
	 * 判断字符串是否为"true"
	 * 
	 * @param bool
	 *            字符串
	 * @return 字符串是否为true的boolean值
	 */
	
	
	private String defaultForums;
	
	public static String newInfopub = getSingleProperty("//infopub/message/newInfopub");
	
	public static String audit = getSingleProperty("//infopub/message/audit");
	
	
	
	public static boolean isTrue(String bool) {
		return InfopubConstants.TRUE_STR.equals(bool);
	}

	/**
	 * 为一个数字加1后返回
	 * 
	 * @param number
	 *            整型对象
	 * @return 加1后的结果
	 */
	public synchronized static Integer add1(Integer number) {
		if (null == number) {
			number = new Integer(0);
		}
		return new Integer(number.intValue() + 1);
	}

	public String getDefaultForums() {
		return defaultForums;
	}

	public void setDefaultForums(String defaultForums) {
		this.defaultForums = defaultForums;
	}
	public static String getSingleProperty(String path) {
		SAXReader reader = new SAXReader();
		String filePath = "";
		try {
			filePath = StaticMethod
					.getFilePathForUrl("classpath:com/boco/eoms/workbench/infopub/config/infopubConfig.xml");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		Document doc = null;
		try {
			doc = reader.read(new FileInputStream(filePath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		Node n = doc.selectSingleNode(path);
		String xmlValue = n.getText();

		return xmlValue;
	}
}
