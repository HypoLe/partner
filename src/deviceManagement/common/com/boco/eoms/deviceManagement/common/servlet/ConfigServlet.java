package com.boco.eoms.deviceManagement.common.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.boco.eoms.commons.loging.BocoLog;
import com.boco.eoms.deviceManagement.common.utils.CommonConstants;
import com.boco.eoms.partner.deviceInspect.switchcfg.SwitchLoader;
import com.boco.eoms.partner.deviceInspect.util.SpecialtyMappingXMLUtil;
import com.boco.eoms.partner.process.util.PnrProcessCach;
import com.boco.eoms.partner.resourceInfo.switchcfg.SwitchLoaderSc;

/**
 * 系统总配置Servlet
 *
 */
public class ConfigServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 初始化总配置servlet--ConfigServlet
	 */
	public void init() throws ServletException {
		super.init();
		try {
			deviceInspectCfgRes();
			PnrProcessCach.loadAllOperation();
			ScCfgRes();
		} catch (Exception e) {
			BocoLog.error(e, "=============总配置servlet--ConfigServlet--错误=============");
			e.printStackTrace();
		}
	}
	
	/**
	 * 设备巡检相关的配置资源
	 * @throws Exception
	 */
	public void deviceInspectCfgRes() throws Exception{
		this.getServletContext().setAttribute(CommonConstants.INSPECT_SWITCH_CONFIG, SwitchLoader.loadSwitchCfg());
		this.getServletContext().setAttribute("Table2NetResourceMapping",SpecialtyMappingXMLUtil.Table2NetResourceMapping);
	}
	/**
	 * 四川版本开关配置
	 * @throws Exception
	 */
	public void ScCfgRes() throws Exception{
		this.getServletContext().setAttribute("pnrScSwitchConfig", SwitchLoaderSc.loadSwitchCfg());	
		}
}
