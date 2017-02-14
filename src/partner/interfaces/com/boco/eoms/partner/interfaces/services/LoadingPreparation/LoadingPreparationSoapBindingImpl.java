/**
 * LoadingPreparationSoapBindingImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2.1 Jun 14, 2005 (09:15:57 EDT) WSDL2Java emitter.
 */

package com.boco.eoms.partner.interfaces.services.LoadingPreparation;
import java.io.File;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.boco.eoms.partner.interfaces.common.init.RequestConfig;
import com.boco.eoms.partner.interfaces.common.init.StaticContext;
import com.res.soa.proxy.ServiceProxy;

public class LoadingPreparationSoapBindingImpl
		implements
		com.boco.eoms.partner.interfaces.services.LoadingPreparation.LoadingPreparationPortType {
	private static Log log = LogFactory
			.getLog(LoadingPreparationSoapBindingImpl.class);
    public void loadingPreparationRequest(
			javax.xml.rpc.holders.StringHolder eventID,
			java.lang.String systemID, java.util.Calendar sendTime,
			int suggestWorkMode, int suggestFileFormat, int suggestCharSet,
			javax.xml.rpc.holders.IntHolder workMode,
			javax.xml.rpc.holders.IntHolder charSet,
			javax.xml.rpc.holders.StringHolder connectionString,
			javax.xml.rpc.holders.StringHolder path,
			javax.xml.rpc.holders.BooleanHolder isCompressed)
			throws java.rmi.RemoteException {
		// querest:唯一事件标识（EventID）系统标识（SystemID）发送时间（SendTime）建议工作方式（SuggestWorkMode）
		// 建议文件格式（SuggestFileFormat）字符编码集（SuggestCharSet）
		// response:唯一事件标识（EventID）工作方式（WorkMode）文件格式（FileFormat）
		// 字符编码集（CharSet）连接字符串（ConnectionString）路径（Path）是否压缩（IsCompressed）
		if (suggestWorkMode != 0) {
			workMode.value = 1;
		} else {
			workMode.value = 0;
		}
		charSet.value = 2;
		connectionString.value = new java.lang.String();
		path.value = new java.lang.String();
		isCompressed.value = true;
		log.info("收到专业网管发送的装载准备");
		log.error("接口平台接收到专业网管传递的原始参数为：[唯一事件标识:" + eventID.value + "] [系统标识:"
				+ systemID + "] [发送时间:" + sendTime.getTime().toString() + "] [建议工作方式:"
				+ suggestWorkMode + "] [建议文件格式:" + suggestFileFormat
				+ "] [字符编码集:" + suggestCharSet);
		String classdir = StaticContext.getServletContext().getRealPath("WEB-INF/classes/com/boco/eoms/partner/interfaces/resources/partner.properties");
		File file=new File(classdir);
		try {
			RequestConfig.init(file);
			//暂时先屏蔽ESB
//			String esbfile = StaticContext.getServletContext().getRealPath("WEB-INF/classes/com/boco/eoms/partner/interfaces/resources/resesb/serviceproxy.ini");
//			ServiceProxy.getInstance().initialize(esbfile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		String connet = "ftp://" + RequestConfig.getftpusername()+ ":" 
		                         + RequestConfig.getftppassword() + "@" 
		                         + RequestConfig.getftphost() + ":" 
		                         + RequestConfig.getftpport();
		connectionString.value = connet;
		path.value = RequestConfig.getftpdir();
		log.info("接口平台返回专业网管数据为：[数据文件和校验文件存放位置：" + connet + File.separatorChar + path.value);
	}
}
