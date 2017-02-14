package com.boco.eoms.partner.interfaces.common.init;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.ultrapower.casp.client.LoginUtil;

public class ContextListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent arg0) {
		StaticContext.setServletContext(null);
	}

	public void contextInitialized(ServletContextEvent arg0) {
		StaticContext.setServletContext(arg0.getServletContext());
		
		// lzj add 2010-6-24 nop3 4A初始化
		// 判断是否启动4A初始化

//		String configPath = arg0.getServletContext().getRealPath("/")
//				+ File.separator + "WEB-INF" + File.separator
//				+ "casp_client_config.properties";
//		boolean initTrue = LoginUtil.getInstance().init(configPath);
//		arg0.getServletContext().setAttribute("4AInit", initTrue);
//		System.out.println("初始化4A:" + initTrue);

	}

}
