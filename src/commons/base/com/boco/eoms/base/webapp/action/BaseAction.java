package com.boco.eoms.base.webapp.action;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.boco.eoms.base.Constants;
import com.boco.eoms.base.util.ConvertUtil;
import com.boco.eoms.base.util.CurrencyConverter;
import com.boco.eoms.base.util.DateConverter;
import com.boco.eoms.commons.loging.BocoLog;
import com.boco.eoms.commons.system.priv.util.PrivMgrLocator;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;

/**
 * Implementation of <strong>Action</strong> that contains base methods for
 * Actions as well as determines with methods to call in subclasses. This class
 * uses the name of the button to determine which method to execute on the
 * Action.
 * </p>
 * 
 * <p>
 * For example look at the following two buttons:
 * </p>
 * 
 * <pre>
 *                                        &lt;html:submit property=&quot;method.findName&quot;&gt;
 *                                           &lt;bean:message key=&quot;email.find&quot;/&gt;
 *                                        &lt;/html:submit&gt;
 *                                    
 *                                        &lt;html:submit property=&quot;method.findEmail&quot;&gt;
 *                                           &lt;bean:message key=&quot;email.find&quot;/&gt;
 *                                        &lt;/html:submit&gt;
 * </pre>
 * 
 * <p>
 * The name of the button is set with the property parameter, i.e., the name of
 * the first button is method.findName. The name of the second button is
 * method.findEmail.
 * </p>
 * 
 * <p>
 * As per HTML/HTTP, whatever submit button that is pushed causes only that
 * submit button's name to be sent as a request parameter to the action.
 * </p>
 * 
 * <p>
 * This action looks for the name by removing the prepender string "method.".
 * The remaining part of the string is the name of the method to execute, e.g.,
 * pushing the first button will execute the findName method and the second
 * button will execute the findEmail method.
 * </p>
 * 
 * <p>
 * This class extends DispatchAction and allows methods to be sent as regular
 * GETs as well, i.e., &lt;a href="emailAction.do?method=findEmail"/&gt; would
 * cause the findEmail method to be executed just as it would in a
 * DispatchAction. Thus, you configure a ButtonNameDispatchAction exactly the
 * way you configure a DispatchAction, i.e., set the mapping parameter to the
 * name of the request parameter that holds the mehtod name.
 * </p>
 * 
 * <p>
 * <a href="BaseAction.java.html"><i>View Source</i></a>
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 * @author Rick Hightower (based on his ButtonNameDispatchAction)
 */
public class BaseAction extends DispatchAction {
	// protected final Log log = LogFactory.getLog(getClass());

	private static final Long defaultLong = null;

	static {
		ConvertUtils.register(new CurrencyConverter(), Double.class);
		ConvertUtils.register(new DateConverter(), Date.class);
		ConvertUtils.register(new DateConverter(), String.class);
		ConvertUtils.register(new LongConverter(defaultLong), Long.class);
		ConvertUtils.register(new IntegerConverter(defaultLong), Integer.class);
	}

	/**
	 * Convenience method to get Spring-initialized beans
	 * 
	 * @param name
	 * @return Object bean from ApplicationContext
	 */
	public Object getBean(String name) {
		ApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(servlet.getServletContext());
		return ctx.getBean(name);
	}

	/**
	 * @see com.boco.eoms.base.util.ConvertUtil#convert(java.lang.Object)
	 */
	protected Object convert(Object o) throws Exception {
		return ConvertUtil.convert(o);
	}

	/**
	 * @see com.boco.eoms.base.util.ConvertUtil#convertLists(java.lang.Object)
	 */
	protected Object convertLists(Object o) throws Exception {
		return ConvertUtil.convertLists(o);
	}

	/**
	 * Convenience method to initialize messages in a subclass.
	 * 
	 * @param request
	 *            the current request
	 * @return the populated (or empty) messages
	 */
	public ActionMessages getMessages(HttpServletRequest request) {
		ActionMessages messages = null;
		HttpSession session = request.getSession();

		if (request.getAttribute(Globals.MESSAGE_KEY) != null) {
			messages = (ActionMessages) request
					.getAttribute(Globals.MESSAGE_KEY);
			saveMessages(request, messages);
		} else if (session.getAttribute(Globals.MESSAGE_KEY) != null) {
			messages = (ActionMessages) session
					.getAttribute(Globals.MESSAGE_KEY);
			saveMessages(request, messages);
			session.removeAttribute(Globals.MESSAGE_KEY);
		} else {
			messages = new ActionMessages();
		}

		return messages;
	}

	/**
	 * Gets the method name based on the mapping passed to it
	 */
	private String getActionMethodWithMapping(HttpServletRequest request,
			ActionMapping mapping) {
		return getActionMethod(request, mapping.getParameter());
	}

	/**
	 * Gets the method name based on the prepender passed to it.
	 */
	protected String getActionMethod(HttpServletRequest request, String prepend) {
		String name = null;

		// for backwards compatibility, try with no prepend first
		name = request.getParameter(prepend);
		if (name != null) {
			// trim any whitespace around - this might happen on buttons
			name = name.trim();
			// lowercase first letter
			return name.replace(name.charAt(0), Character.toLowerCase(name
					.charAt(0)));
		}

		Enumeration e = request.getParameterNames();

		while (e.hasMoreElements()) {
			String currentName = (String) e.nextElement();

			if (currentName.startsWith(prepend + ".")) {
				// if (log.isDebugEnabled()) {
				BocoLog.debug(this, "calling method: " + currentName);
				// }

				String[] parameterMethodNameAndArgs = StringUtils.split(
						currentName, ".");
				name = parameterMethodNameAndArgs[1];
				break;
			}
		}

		return name;
	}

	/**
	 * Override the execute method in DispatchAction to parse URLs and forward
	 * to methods without parameters.
	 * </p>
	 * <p>
	 * This is based on the following system: <p/>
	 * <ul>
	 * <li>edit*.html -> edit method</li>
	 * <li>save*.html -> save method</li>
	 * <li>view*.html -> search method</li>
	 * </ul>
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		if (isCancelled(request)) {
			try {
				getMethod("cancel");
				return dispatchMethod(mapping, form, request, response,
						"cancel");
			} catch (NoSuchMethodException n) {
				BocoLog.warn(this, "No 'cancel' method found, returning null");
				return cancelled(mapping, form, request, response);
			}
		}

		// Check to see if methodName indicated by request parameter
		String actionMethod = getActionMethodWithMapping(request, mapping);
		if (!"saveSession".equals(actionMethod)) {
			if(!"wapSaveSession".equals(actionMethod)){
			TawSystemSessionForm sessionform = (TawSystemSessionForm) request
					.getSession().getAttribute("sessionform");
			// String url = request.getServletPath();
			// boolean flag = TawSystemUserAssignBo.getInstance().isHaveUrl(url,
			// sessionform.getUserid());
			// if (flag == false && !sessionform.isIsadmin()
			// && !url.equals("/xtree.do")) {
			//
			//				
			// }
			// if (sessionform != null) {
			// if (!"/xtree.do".equals(url) && !hasPermission(url, request)) {
			// // return XtreeAction.noOperpriv(mapping, form, request,
			// // response);
			// ActionForward forward = new ActionForward("/nopriv.jsp",
			// false);
			// forward.setContextRelative(true);
			// return forward;
			// }
			//
			// } else
			if (sessionform == null) {
				// throw new Exception("登陆超时，请重新登陆");
				ActionForward forward = new ActionForward("/reload.jsp", false);
				forward.setContextRelative(true);
				return forward;
			}
		}
		}
		if (actionMethod != null) {
			return dispatchMethod(mapping, form, request, response,
					actionMethod);
		} else {
			String[] rules = { "edit", "save", "search", "view" };
			for (int i = 0; i < rules.length; i++) {
				// apply the rules for automatically appending the method name
				if (request.getServletPath().indexOf(rules[i]) > -1) {
					return dispatchMethod(mapping, form, request, response,
							rules[i]);
				}
			}
		}

		return super.execute(mapping, form, request, response);
	}

	/**
	 * Convenience method for getting an action form base on it's mapped scope.
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param request
	 *            The HTTP request we are processing
	 * @return ActionForm the form from the specifies scope, or null if nothing
	 *         found
	 */
	protected ActionForm getActionForm(ActionMapping mapping,
			HttpServletRequest request) {
		ActionForm actionForm = null;

		// Remove the obsolete form bean
		if (mapping.getAttribute() != null) {
			if ("request".equals(mapping.getScope())) {
				actionForm = (ActionForm) request.getAttribute(mapping
						.getAttribute());
			} else {
				HttpSession session = request.getSession();
				actionForm = (ActionForm) session.getAttribute(mapping
						.getAttribute());
			}
		}

		return actionForm;
	}

	/**
	 * Convenience method to get the Configuration HashMap from the servlet
	 * context.
	 * 
	 * @return the user's populated form from the session
	 */
	public Map getConfiguration() {
		Map config = (HashMap) getServlet().getServletContext().getAttribute(
				Constants.CONFIG);

		// so unit tests don't puke when nothing's been set
		if (config == null) {
			return new HashMap();
		}

		return config;
	}

	/**
	 * Convenience method for removing the obsolete form bean.
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param request
	 *            The HTTP request we are processing
	 */
	protected void removeFormBean(ActionMapping mapping,
			HttpServletRequest request) {
		// Remove the obsolete form bean
		if (mapping.getAttribute() != null) {
			if ("request".equals(mapping.getScope())) {
				request.removeAttribute(mapping.getAttribute());
			} else {
				HttpSession session = request.getSession();
				session.removeAttribute(mapping.getAttribute());
			}
		}
	}

	/**
	 * Convenience method to update a formBean in it's scope
	 * 
	 * @param mapping
	 *            The ActionMapping used to select this instance
	 * @param request
	 *            The HTTP request we are processing
	 * @param form
	 *            The ActionForm
	 */
	protected void updateFormBean(ActionMapping mapping,
			HttpServletRequest request, ActionForm form) {
		// Remove the obsolete form bean
		if (mapping.getAttribute() != null) {
			if ("request".equals(mapping.getScope())) {
				request.setAttribute(mapping.getAttribute(), form);
			} else {
				HttpSession session = request.getSession();
				session.setAttribute(mapping.getAttribute(), form);
			}
		}
	}

	/**
	 * 获取当前用户信息
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return 当前用户信息
	 */
	protected TawSystemSessionForm getUser(HttpServletRequest request) {
		return this.getUser(request.getSession());
	}

	/**
	 * 获取当前用户信息
	 * 
	 * @param session
	 *            当前会话信息
	 * @return 当前用户信息
	 */
	protected TawSystemSessionForm getUser(HttpSession session) {
		return (TawSystemSessionForm) session.getAttribute("sessionform");
	}

	/**
	 * 获取当前用户id
	 * 
	 * @param request
	 *            HttpServletRequest对象
	 * @return 当前用户id
	 */
	protected String getUserId(HttpServletRequest request) {
		TawSystemSessionForm user = this.getUser(request);
		return user.getUserid();
	}

	// /**
	// * 某人是否拥有某权限（不判断超级用户）
	// *
	// * @param url
	// * 权限
	// * @param userId
	// * 用户id
	// * @deprecated 效率低，请使用hasPermission(String url, HttpServletRequest
	// * request)方法
	// * @return 拥有权限否（不判断超级用户）
	// */
	// protected boolean hasPermission(String url, String userId) {
	// // return PrivMgrLocator.getTawSystemPrivUserAssignManager().isHaveUrl(
	// // url, userId);
	//
	// return PrivMgrLocator.getPrivMgr().hasPriv(userId, url);
	//
	// }

	/**
	 * 判断session登陆的人是否拥有某权限，或此人为超级用户也返回true
	 * 
	 * @param url
	 *            权限
	 * @param request
	 *            HttpServletRequest
	 * @return 拥有权限否(超级用户也可）
	 */
	protected boolean hasPermission(String url, HttpServletRequest request) {
		TawSystemSessionForm user = this.getUser(request);

		return PrivMgrLocator.getPrivMgr().hasPriv(user.getUserid(),
				user.getRolelist(), user.getDeptid(), url)
				|| Constants.ADMINISTRATOR.equals(this.getUser(request)
						.getUserid());
		// return (hasPermission(url, this.getUserId(request)) || this.getUser(
		// request).isAdmin());
	}
	
	/**
	 * 获取全局ServletContext
	 * @return
	 */
	public ServletContext getServletContext(){
		return this.getServlet().getServletContext();
	}
	
	/**
	 * 获取全局ServletContext的Attribute
	 * @return
	 */
	public Object getServletContextAttribute(String attributeName){
		return this.getServletContext().getAttribute(attributeName);
	}
}
