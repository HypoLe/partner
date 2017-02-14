package com.boco.eoms.base.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;


import com.boco.eoms.commons.log.service.logSave;
import com.boco.eoms.commons.loging.BocoLog;
import com.boco.eoms.commons.system.priv.bo.TawSystemPrivAssignOut;
import com.boco.eoms.commons.system.priv.util.PrivConstants;
import com.boco.eoms.commons.system.priv.util.PrivMgrLocator;
import com.boco.eoms.commons.system.session.bo.TawSystemSessionBo;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.ui.bo.TawSystemTreeBo;

public class SetCharacterEncodingFilter implements Filter {

	/**
	 * The default character encoding to set for requests that pass through this
	 * filter.
	 */
	protected String encoding = null;

	/**
	 * The filter configuration object we are associated with. If this value is
	 * null, this filter instance is not currently configured.
	 */
	protected FilterConfig filterConfig = null;

	/**
	 * Should a character encoding specified by the client be ignored?
	 */
	protected boolean ignore = true;

	// 没有权限时，跳转的页面
	protected String privpage = "";

	// session超时时，跳转的页面
	protected String timeoutpage = "";

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		try {
			if (ignore || (request.getCharacterEncoding() == null)) {
				String encoding = selectEncoding(request);
				if (encoding != null)
					request.setCharacterEncoding(encoding);

			}
			HttpServletRequest req = (HttpServletRequest) request;
			String url = req.getRequestURI();

			if (url.indexOf("wap") > 0) {
				timeoutpage = "waptimeout.jsp";
			}
			boolean isTure = true;
			// Pass control on to the next filter
			if (((HttpServletRequest) request).getSession().getAttribute(
					"sessionform") != null && true) {
				chain.doFilter(request, response);
				isTure = false;
			}
			// 获取用户session信息
			if (request.getParameter("type") != null 
					&& request.getParameter("type").equalsIgnoreCase("interface")) {

				String userId = request.getParameter("userName");
				if (userId != null) {
					TawSystemSessionForm sessionform = new TawSystemSessionForm();
					sessionform.setUserid(userId);
					sessionform = TawSystemSessionBo.getSessionForm(userId);

					TawSystemPrivAssignOut privassimgr = TawSystemPrivAssignOut.getInstance();
					String modeName = privassimgr.getNameBycode(request.getParameter("id"));

					logSave log = logSave.getInstance(modeName, userId, "0001",
							request.getRemoteAddr(), userId + " 于:"
									+ StaticMethod.getCurrentDateTime() + " 登录系统.", "111");
					log.info();
					sessionform.setRomteaddr(request.getRemoteAddr());

					TawSystemTreeBo usertree = TawSystemTreeBo.getInstance();

					// 添加项目所在物理路径
					try {
						sessionform.setRealPath(StaticMethod.getWebPath());
					} catch (FileNotFoundException e) {

					}

					if ("admin".equals(sessionform.getUserid())) {
						((HttpServletRequest) request).getSession().setAttribute("menu",usertree.getPrivAdminMenu(userId));
					} else {
						((HttpServletRequest) request).getSession().setAttribute("menu",
							PrivMgrLocator.getPrivMgr().operations2json(PrivMgrLocator.getPrivMgr()
								.listOpertion(sessionform.getUserid(),sessionform.getDeptid(),sessionform.getRolelist(),
									PrivConstants.LIST_OPERATION_TYPE_MOUDLE_FUNCTION,StaticVariable.ROOT_NODE)));
					}

					((HttpServletRequest) request).getSession().setMaxInactiveInterval(-1);
					((HttpServletRequest) request).getSession().setAttribute("sessionform", sessionform);
					((HttpServletRequest) request).getSession().setAttribute("type", "interface");
					if (isTure) {
						try {
							chain.doFilter(request, response);
							isTure = false;
						} catch (Exception e) {

						}
					}
				}
			}
			if (isTure) {
				chain.doFilter(request, response);
			}

		} catch (Exception ee) {
			ee.printStackTrace();
			BocoLog.error(this, ee.getMessage());
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		this.filterConfig = arg0;
		this.privpage = filterConfig.getInitParameter("privpage");
		this.timeoutpage = filterConfig.getInitParameter("timeoutpage");

		this.encoding = filterConfig.getInitParameter("encoding");
		String value = filterConfig.getInitParameter("ignore");

		if (value == null)
			this.ignore = true;
		else if (value.equalsIgnoreCase("true"))
			this.ignore = true;
		else if (value.equalsIgnoreCase("yes"))
			this.ignore = true;
		else
			this.ignore = false;
	}

	protected String selectEncoding(ServletRequest request) {

		return (this.encoding);

	}

}
