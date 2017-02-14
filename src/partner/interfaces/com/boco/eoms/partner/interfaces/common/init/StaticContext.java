package com.boco.eoms.partner.interfaces.common.init;
import javax.servlet.ServletContext;
public class StaticContext {
	private static ServletContext servletContext=null;

	public static ServletContext getServletContext() {
		return servletContext;
	}

	public static void setServletContext(ServletContext servletContext) {
		StaticContext.servletContext = servletContext;
	}
}
