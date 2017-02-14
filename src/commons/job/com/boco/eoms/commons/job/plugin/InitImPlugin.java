package com.boco.eoms.commons.job.plugin;

import javax.servlet.ServletException;

import org.apache.struts.action.ActionServlet;
import org.apache.struts.action.PlugIn;
import org.apache.struts.config.ModuleConfig;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.log4j.Logger;
import com.boco.eoms.pq.facade.IPQFacade;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Date:Aug 1, 2008 5:04:05 PM
 * </p>
 * 
 * @author 曲静波
 * @version 3.5.1
 * 
 */
public class InitImPlugin implements PlugIn {
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts.action.PlugIn#destroy()
	 */
	public void destroy() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts.action.PlugIn#init(org.apache.struts.action.ActionServlet,
	 *      org.apache.struts.config.ModuleConfig)
	 */
	public void init(ActionServlet arg0, ModuleConfig arg1)
			throws ServletException {

		IPQFacade ipQFacade = (IPQFacade) ApplicationContextHolder
				.getInstance().getBean("pqFacade");
		ipQFacade.doPQs();

	}

}
