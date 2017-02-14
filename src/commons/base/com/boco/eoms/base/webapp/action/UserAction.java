package com.boco.eoms.base.webapp.action;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.Authentication;
import org.acegisecurity.AuthenticationTrustResolver;
import org.acegisecurity.AuthenticationTrustResolverImpl;
import org.acegisecurity.BadCredentialsException;
import org.acegisecurity.context.SecurityContext;
import org.acegisecurity.context.SecurityContextHolder;
import org.acegisecurity.providers.encoding.Md5PasswordEncoder;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.util.MessageResources;
import org.springframework.mail.SimpleMailMessage;

import com.boco.eoms.base.Constants;
import com.boco.eoms.base.model.Role;
import com.boco.eoms.base.model.User;
import com.boco.eoms.base.service.MailEngine;
import com.boco.eoms.base.service.RoleManager;
import com.boco.eoms.base.service.UserExistsException;
import com.boco.eoms.base.service.UserManager;
import com.boco.eoms.base.util.DateUtil;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.StringUtil;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.form.UserForm;
import com.boco.eoms.base.webapp.util.RequestUtil;
import com.boco.eoms.commons.loging.BocoLog;
import com.boco.eoms.commons.system.session.action.TawSystemSessionAction;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.commons.system.user.service.ITawSystemUserManager;
import com.boco.eoms.commons.system.user.util.UserMgrLocator;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerUser;
import com.boco.eoms.wap.util.WAPConstants;
import com.boco.eoms.wap.util.WapUtil;
import com.ultrapower.casp.client.LoginUtil;
import com.ultrapower.casp.common.code.ResultCode;
import com.ultrapower.casp.common.datatran.data.ticket.TransferTicket;
import com.ultrapower.casp.common.datatran.data.user.UserInfo;
import com.ultrapower.casp.common.util.CodeUtil;

/**
 * Implementation of <strong>Action</strong> that interacts with the
 * {@link UserForm} and retrieves values. It interacts with the
 * {@link UserManager} to retrieve/persist values to the database.
 * 
 * <p>
 * <a href="UserAction.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a> Modified by
 *         <a href="mailto:dan@getrolling.com">Dan Kibler</a>
 * 
 */
public final class UserAction extends BaseAction {

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// if (log.isDebugEnabled()) {
		BocoLog.debug(this, "Entering 'add' method");
		// }

		User user = new User();
		user.addRole(new Role(Constants.USER_ROLE));
		UserForm userForm = (UserForm) convert(user);
		updateFormBean(mapping, request, userForm);

		checkForRememberMeLogin(request);

		return mapping.findForward("edit");
	}

	public ActionForward cancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// if (log.isDebugEnabled()) {
		BocoLog.debug(this, "Entering 'cancel' method");
		// }

		if (!StringUtils.equals(request.getParameter("from"), "list")) {
			return mapping.findForward("mainMenu");
		} else {
			return mapping.findForward("viewUsers");
		}
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// if (log.isDebugEnabled()) {
		BocoLog.debug(this, "Entering 'delete' method");
		// }

		// Extract attributes and parameters we will need
		ActionMessages messages = new ActionMessages();
		UserForm userForm = (UserForm) form;

		// Exceptions are caught by ActionExceptionHandler
		UserManager mgr = (UserManager) getBean("userManager");
		mgr.removeUser(userForm.getId());

		messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
				"user.deleted", userForm.getFirstName() + ' '
						+ userForm.getLastName()));

		saveMessages(request.getSession(), messages);

		// return a forward to searching users
		return mapping.findForward("viewUsers");
	}

	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// if (log.isDebugEnabled()) {
		BocoLog.debug(this, "Entering 'edit' method");
		// }

		UserForm userForm = (UserForm) form;

		// if URL is "editProfile" - make sure it's the current user
		if (request.getRequestURI().indexOf("editProfile") > -1) {
			// reject if username passed in or "list" parameter passed in
			// someone that is trying this probably knows the AppFuse code
			// but it's a legitimate bug, so I'll fix it. ;-)
			if ((request.getParameter("username") != null)
					|| (request.getParameter("from") != null)) {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
				BocoLog.warn(this, "User '" + request.getRemoteUser()
						+ "' is trying to edit user '"
						+ request.getParameter("username") + "'");

				return null;
			}
		}

		// Exceptions are caught by ActionExceptionHandler
		UserManager mgr = (UserManager) getBean("userManager");
		User user = null;

		// if a user's username is passed in
		if (request.getParameter("username") != null) {
			// lookup the user using that id
			user = mgr.getUserByUsername(userForm.getUsername());
		} else {
			// look it up based on the current user's id
			user = mgr.getUserByUsername(request.getRemoteUser());
		}

		BeanUtils.copyProperties(userForm, convert(user));
		userForm.setConfirmPassword(userForm.getPassword());
		updateFormBean(mapping, request, userForm);

		checkForRememberMeLogin(request);

		// return a forward to edit forward
		return mapping.findForward("edit");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// if (log.isDebugEnabled()) {
		BocoLog.debug(this, "Entering 'save' method");
		// }

		// run validation rules on this form
		// See https://appfuse.dev.java.net/issues/show_bug.cgi?id=128
		ActionMessages errors = form.validate(mapping, request);

		if (!errors.isEmpty()) {
			// saveErrors(request, errors);
			BocoLog.error(this, errors.toString());
			return mapping.findForward("edit");
		}

		// Extract attributes and parameters we will need
		ActionMessages messages = new ActionMessages();
		UserForm userForm = (UserForm) form;
		User user = new User();

		// Exceptions are caught by ActionExceptionHandler
		// all we need to persist is the parent object
		BeanUtils.copyProperties(user, userForm);

		Boolean encrypt = (Boolean) getConfiguration().get(
				Constants.ENCRYPT_PASSWORD);

		if (StringUtils.equals(request.getParameter("encryptPass"), "true")
				&& (encrypt != null && encrypt.booleanValue())) {
			String algorithm = (String) getConfiguration().get(
					Constants.ENC_ALGORITHM);

			if (algorithm == null) { // should only happen for test case
				BocoLog.debug(this,
						"assuming testcase, setting algorithm to 'SHA'");
				algorithm = "SHA";
			}

			user.setPassword(StringUtil.encodePassword(user.getPassword(),
					algorithm));
		}

		UserManager mgr = (UserManager) getBean("userManager");
		RoleManager roleMgr = (RoleManager) getBean("roleManager");
		String[] userRoles = request.getParameterValues("userRoles");

		for (int i = 0; userRoles != null && i < userRoles.length; i++) {
			String roleName = userRoles[i];
			user.addRole(roleMgr.getRole(roleName));
		}

		try {
			mgr.saveUser(user);
		} catch (UserExistsException e) {
			// errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
			// "errors.existing.user", userForm.getUsername(), userForm
			// .getEmail()));
			// saveErrors(request, errors);
			BocoLog.error(this, "errors.existing.user "
					+ userForm.getUsername());

			BeanUtils.copyProperties(userForm, convert(user));
			userForm.setConfirmPassword(userForm.getPassword());
			// reset the version # to what was passed in
			userForm.setVersion(request.getParameter("version"));
			updateFormBean(mapping, request, userForm);

			return mapping.findForward("edit");
		}

		BeanUtils.copyProperties(userForm, convert(user));
		userForm.setConfirmPassword(userForm.getPassword());
		updateFormBean(mapping, request, userForm);

		if (!StringUtils.equals(request.getParameter("from"), "list")) {
			// add success messages
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"user.saved"));
			saveMessages(request.getSession(), messages);

			// return a forward to main Menu
			return mapping.findForward("mainMenu");
		} else {
			// add success messages
			if ("".equals(request.getParameter("version"))) {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"user.added", user.getFullName()));
				saveMessages(request.getSession(), messages);
				sendNewUserEmail(request, userForm);

				return mapping.findForward("addUser");
			} else {
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"user.updated.byAdmin", user.getFullName()));
				saveMessages(request, messages);

				return mapping.findForward("edit");
			}
		}
	}

	@SuppressWarnings("unchecked")
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BocoLog.debug(this, "Entering 'search' method");

		UserForm userForm = (UserForm) form;

		// Exceptions are caught by ActionExceptionHandler
		UserManager mgr = (UserManager) getBean("userManager");
		User user = (User) convert(userForm);
		List users = mgr.getUsers(user);
		request.setAttribute(Constants.USER_LIST, users);

		// return a forward to the user list definition
		return mapping.findForward("list");
	}

	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		return search(mapping, form, request, response);
	}

	private void sendNewUserEmail(HttpServletRequest request, UserForm userForm)
			throws Exception {
		MessageResources resources = getResources(request);

		// Send user an e-mail
		// if (log.isDebugEnabled()) {
		BocoLog.debug(this, "Sending user '" + userForm.getUsername()
				+ "' an account information e-mail");
		// }

		SimpleMailMessage message = (SimpleMailMessage) getBean("mailMessage");
		message.setTo(userForm.getFullName() + "<" + userForm.getEmail() + ">");

		StringBuffer msg = new StringBuffer();
		msg.append(resources.getMessage("newuser.email.message", userForm
				.getFullName()));
		msg.append("\n\n" + resources.getMessage("userForm.username"));
		msg.append(": " + userForm.getUsername() + "\n");
		msg.append(resources.getMessage("userForm.password") + ": ");
		msg.append(userForm.getPassword());
		msg.append("\n\nLogin at: " + RequestUtil.getAppURL(request));
		message.setText(msg.toString());

		message.setSubject(resources.getMessage("signup.email.subject"));

		MailEngine engine = (MailEngine) getBean("mailEngine");
		engine.send(message);
	}

	private void checkForRememberMeLogin(HttpServletRequest request) {
		// if user logged in with remember me, display a warning that they can't
		// change passwords
		BocoLog.debug(this, "checking for remember me login...");

		AuthenticationTrustResolver resolver = new AuthenticationTrustResolverImpl();
		SecurityContext ctx = SecurityContextHolder.getContext();

		if (ctx != null) {
			Authentication auth = ctx.getAuthentication();

			if (resolver.isRememberMe(auth)) {
				request.getSession().setAttribute("cookieLogin", "true");

				// add warning message
				ActionMessages messages = new ActionMessages();
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"userProfile.cookieLogin"));
				saveMessages(request, messages);
			}
		}
	}

	public ActionForward saveSession(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String userId = null;
		ActionForward forword = null;
		ITawSystemUserManager userManager = (ITawSystemUserManager) getBean("ItawSystemUserSaveManagerFlush");

		// wap登陆方式
		if (request.getParameter("wapLogin") != null) {
			String j_username = request.getParameter("j_username");
			String j_password = request.getParameter("j_password");
			// cookie中保存用户名
			WapUtil.saveValue4Cookie(WAPConstants.WAP_COOKIE_LOGIN_NAME,
					j_username, response);
			// 用户名/手机号 校验
			String returnValue = "";
			if (j_username != null) {

				returnValue = userManager.getUserByUserIdOrMobile(j_username,
						j_password);
				if ("".equalsIgnoreCase(returnValue)) {
					return new ActionForward("/wap/login.jsp?error=error");

				}
			}
			userId = returnValue;
		}
		// 单点登陆方式
		else if (Constants.LOGIN_SSO.equals(UtilMgrLocator.getEOMSAttributes()
				.getLoginType())) {
			userId = (String) request.getSession().getAttribute(
					"edu.yale.its.tp.cas.client.filter.user");
		}
		// 以acegi方式登陆
		else if (Constants.LOGIN_ACEGI.equals(UtilMgrLocator
				.getEOMSAttributes().getLoginType())) {
			SecurityContext securityContext = SecurityContextHolder
					.getContext();
			TawSystemUser acegiUser = (TawSystemUser) securityContext
					.getAuthentication().getPrincipal();
			userId = acegiUser.getUserid();
		}
		// 以eoms自带方式登陆
		else if (Constants.LOGIN_EOMS.equals(UtilMgrLocator.getEOMSAttributes()
				.getLoginType())) {
			userId = request.getParameter("j_username");
			String userPWD = request.getParameter("j_password");

			TawSystemUser user = null;

			try {
/*				// 4A 验证用户
				System.out.println("4A认证用户开始！！");
				String loginBy4aRtn = loginBy4a(request, response, userId,
						userPWD);
				log.info("loginBy4aRtn:" + loginBy4aRtn);
				if ("".equals(loginBy4aRtn)) {
					// 4A认证错误
					// return mapping.findForward("relogin");
					// 使用本地认证
					user = (TawSystemUser) userManager.getUserByuserid(userId);
				} else if ("localAuthentication".equals(loginBy4aRtn)) {
*/					// 使用本地认证
					user = (TawSystemUser) userManager.getUserByuserid(userId);

					if (!user.getPassword().equals(// 密码错误，抛异常
							new Md5PasswordEncoder().encodePassword(userPWD,
									new String()))) {

						throw new BadCredentialsException(messages.getMessage(
								"UserAction.badCredentials", "Bad credentials"));
					}

/*				} else {
					// 4A认证成功
					user = (TawSystemUser) userManager
							.getUserByuserid(loginBy4aRtn);
				}
*/
				if (user.getUserid() == null) {
					// 用户不存在，抛异常
					loginErrorReturn(request, response,
							"BadCredentialsException");
					return mapping.findForward("relogin");
				}
			} catch (RuntimeException e1) {
				loginErrorReturn(request, response, "BadCredentialsException");
				return mapping.findForward("relogin");
			}

			// SOX，连续登陆n（6）次将锁定用户不得登陆
			try {
				user.setEnabled(true);
			
				
				Date systemdate=new Date();
				Date tempupdatetime = user.getUpdatetime()==null?user.getSavetime():sdf.parse(user.getUpdatetime());				
				long daynum=systemdate.getTime()-tempupdatetime.getTime();
				daynum = daynum / 1000 / 60 / 60 / 24;
				
				// TODO ldap中验证通过后,不再验证EOMS系统
				if (!user.getPassword().equals(// 密码错误，抛异常
						new Md5PasswordEncoder().encodePassword(userPWD,
								new String()))) {
					// lzj add 2010-5-7 不进行密码验证
					// throw new BadCredentialsException(messages.getMessage(
					// "UserAction.badCredentials", "Bad credentials"));
				} else if (user.isAccountLocked()) {// 用户被锁定
					loginErrorReturn(request, response, "LockedException");
					return mapping.findForward("relogin");
				} else if (!user.isEnabled()) {// 用户不可用
					loginErrorReturn(request, response, "DisabledException");
					return mapping.findForward("relogin");
				}else if(!userManager.checkPasswd(userPWD)){
					loginErrorReturn(request, response, "DisabledException");
					return mapping.findForward("relogin");
				}else if(daynum >= 90){	//超过90天未修改密码就锁定
					user.setAccountLocked(true);
					userManager.saveTawSystemUser(user);
					loginErrorReturn(request, response, "LockedException");
					return mapping.findForward("relogin");
				}
			} catch (Exception e) {
				// 若登陆失败，则将失败次数累加，到达一定次数则锁住用户
				user.setFailCount(user.getFailCount() != null ? new Integer(
						user.getFailCount().intValue() + 1) : new Integer(0));
				if (user.getFailCount().intValue() >= UserMgrLocator
						.getUserAttributes().getPasswdRepeatNum().intValue()) {
					// 锁定用户
					user.setAccountLocked(true);
					userManager.saveTawSystemUser(user);
					loginErrorReturn(request, response, "LockedException");
					return mapping.findForward("relogin");
				}
				userManager.saveTawSystemUser(user);
				loginErrorReturn(request, response, "BadCredentialsException");
				return mapping.findForward("relogin");
			}

			/*
			// SOX，判断用户有效期为n(90)天
			if (new Date().compareTo(DateUtil.addDate(user.getSavetime(),
					UserMgrLocator.getUserAttributes().getPasswdAvailableDay()
							.intValue())) > 0) {
				// 设置user用户不可用
				user.setEnabled(false);
				userManager.saveTawSystemUser(user);
				loginErrorReturn(request, response, "DisabledException");
				return mapping.findForward("relogin");
			}
			*/

			// 登陆成功则置零
			if (user.getFailCount() != null
					&& user.getFailCount().intValue() != 0) {
				user.setFailCount(new Integer(0));
				userManager.saveTawSystemUser(user);
			}
		}
		// 密码到期提示
		TawSystemUser tsu = userManager.getUserByuserid(userId);
		timeOutRemind(request, response, tsu.getUpdatetime()==null?tsu.getSavetime():sdf.parse(tsu.getUpdatetime()));

		TawSystemSessionForm sessionform = new TawSystemSessionForm();
		sessionform.setUserid(userId);

		 TawSystemSessionAction sessionaction = new TawSystemSessionAction();
		forword = sessionaction.performInit(mapping, sessionform, request,response);
//		e10adc3949ba59abbe56e057f20f883e----对应123456

		// 若添写密码则验证并修改密码，否则不修改、不修改。
		// 密码至少大于8位并数字加字母组合
		/*boolean isNormal = true;
		String passwordString = request.getParameter("j_password");
		
//		passwordString="Aa0000";
		isNormal= userManager.checkPasswd(passwordString);
//		留了后门，先是对密码为123456的进行初始化，再对不符合校验条件的用户。	
		String isreStr= tsu.getIsrest()==null?"":tsu.getIsrest();
		if(tsu.getPassword().equals("c82b6299c82bdd5244180bd067501f3a")||(!isNormal && !isreStr.equals("5"))){
			return mapping.findForward("loginModify");
		}*/
//		------end 
		return forword;
	}

	/**
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return 分离wap登陆方式
	 */
	public ActionForward wapSaveSession(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String userId = null;
		ActionForward forword = null;
		ITawSystemUserManager userManager = (ITawSystemUserManager) getBean("ItawSystemUserSaveManagerFlush");

		// wap登陆方式
		if (request.getParameter("wapLogin") != null) {
			String j_username = request.getParameter("j_username");
			String j_password = request.getParameter("j_password");
			// cookie中保存用户名
			WapUtil.saveValue4Cookie(WAPConstants.WAP_COOKIE_LOGIN_NAME,
					j_username, response);
			// 用户名/手机号 校验
			String returnValue = "";
			if (j_username != null) {

				returnValue = userManager.getUserByUserIdOrMobile(j_username,
						j_password);
				if ("".equalsIgnoreCase(returnValue)) {
					return new ActionForward("/wap/login.jsp?error=error");

				}
			}
			userId = returnValue;

			TawSystemUser user = null;

			// SOX，连续登陆n（6）次将锁定用户不得登陆
			try {
				// 验证用户
				user = (TawSystemUser) userManager.getUserByuserid(userId);

				if (user == null)// 用户不存在，抛异常
					throw new BadCredentialsException(messages.getMessage(
							"UserAction.badCredentials", "Bad credentials"));
				// TODO ldap中验证通过后,不再验证EOMS系统
				// else if (!user.getPassword().equals(// 密码错误，抛异常
				// new Md5PasswordEncoder().encodePassword(userPWD,
				// new String()))) {
				// throw new BadCredentialsException(messages.getMessage(
				// "UserAction.badCredentials", "Bad credentials"));
				// }
				else if (user.isAccountLocked()) {// 用户被锁定
					loginErrorReturn(request, response, "LockedException");
					return mapping.findForward("relogin");
				} else if (!user.isEnabled()) {// 用户不可用
					loginErrorReturn(request, response, "DisabledException");
					return mapping.findForward("relogin");
				}
			} catch (Exception e) {
				// 若登陆失败，则将失败次数累加，到达一定次数则锁住用户
				user.setFailCount(user.getFailCount() != null ? new Integer(
						user.getFailCount().intValue() + 1) : new Integer(0));
				if (user.getFailCount().intValue() >= UserMgrLocator
						.getUserAttributes().getPasswdRepeatNum().intValue()) {
					// 锁定用户
					user.setAccountLocked(true);
					userManager.saveTawSystemUser(user);
					loginErrorReturn(request, response, "LockedException");
					return mapping.findForward("relogin");
				}
				userManager.saveTawSystemUser(user);
				loginErrorReturn(request, response, "BadCredentialsException");
				return mapping.findForward("relogin");
			}

			// SOX，判断用户有效期为n(90)天
			if (new Date().compareTo(DateUtil.addDate(user.getSavetime(),
					UserMgrLocator.getUserAttributes().getPasswdAvailableDay()
							.intValue())) > 0) {
				// 设置user用户不可用
				user.setEnabled(false);
				userManager.saveTawSystemUser(user);
				loginErrorReturn(request, response, "DisabledException");
				return mapping.findForward("relogin");
			}

			// 登陆成功则置零
			user.setFailCount(new Integer(0));
			userManager.saveTawSystemUser(user);

		}
		// 密码到期提示
		TawSystemUser tsu = userManager.getTawSystemUserByuserid(userId);
		timeOutRemind(request, response, tsu.getSavetime());

		TawSystemSessionForm sessionform = new TawSystemSessionForm();
		sessionform.setUserid(userId);

		TawSystemSessionAction sessionaction = new TawSystemSessionAction();
		/**作业计划代理查询，合作伙伴中不需要
		 * modify by 陈元蜀 2012-08-30 begin
		 
		ITawwpStubUserMgr tawwpStubUserMgr = (ITawwpStubUserMgr) getBean("tawwpStubUserMgr");
		request.setAttribute("tawwpStubUserMgr", tawwpStubUserMgr);
		* modify by 陈元蜀 2012-08-30 end
		 */
		request.setAttribute("wapLogin", "wap");
		forword = sessionaction.performLogin(mapping, sessionform, request,
				response);

		return forword;
	}

	private void loginErrorReturn(HttpServletRequest request,
			HttpServletResponse response, String strError) {
		request.setAttribute(Constants.EOMS_SECURITY_EXCEPTION_KEY, strError);
	}

	// 密码即将无效提示
	private void timeOutRemind(HttpServletRequest request,
			HttpServletResponse response, Date saveTime) {
		int timeOut = UserMgrLocator.getUserAttributes()
				.getPasswdAvailableDay().intValue()
				- UserMgrLocator.getUserAttributes()
						.getPasswdUnavailableRemindDay().intValue();
		String temp = "";
		if (new Date().compareTo(DateUtil.addDate(saveTime, timeOut)) > 0) {
			temp = "密码即将无效，请尽快更新密码!\\n上次修改密码时间：" + saveTime
					+ "\\n(\"确定\"修改密码,\"取消\"以后再说)";
			request.setAttribute("remind", temp);
		} else {
			request.setAttribute("remind", temp);
		}
	}

	/**
	 * 
	 * 云南4A认证改造
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	private String loginBy4a(HttpServletRequest request,
			HttpServletResponse response, String userId, String userPassword) {
		// 判断是否启动4A初始化
		if (request.getSession().getServletContext().getAttribute("4AInit") == null) {
			String configPath = request.getSession().getServletContext()
					.getRealPath("/")
					+ File.separator
					+ "WEB-INF"
					+ File.separator
					+ "casp_client_config.properties";
			boolean initTrue = LoginUtil.getInstance().init(configPath);
			request.getSession().getServletContext().setAttribute("4AInit",
					initTrue);
			log.info("init4A:" + initTrue);
		}

		if (LoginUtil.getInstance().isEnable()) {

			if (LoginUtil.getInstance().hasAliveServer()) {
				TransferTicket rootTicket = null;

				// 获取认证信息，调用相应的认证接口到4A系统认证，下面为主帐号静态密码认证，从帐号静态密码认证、短信认证见规范接口
				// accStaticPwdAuth(String,String,String,String)，参数1：主帐号id，如果不是主帐号认证为null；参数2：从帐号id，如果不是从帐号认证为null；参数3：密码；参数4：客户端IP，访问控制时使用；
				rootTicket = LoginUtil.getInstance().accStaticPwdAuth(userId,
						null, userPassword, request.getRemoteAddr());

				// 将返回值赋给 rootTicket
				request.getSession().setAttribute(CodeUtil.ROOT_TICKET_KEY,
						rootTicket.getRootTicket());

				if (rootTicket != null && rootTicket.getRetCode() != null
						&& ResultCode.RESULT_OK.equals(rootTicket.getRetCode())) {
					UserInfo userInfo = LoginUtil.getInstance()
							.qryUserByTicket(rootTicket);
					System.out.println("userId:" + userInfo.getAccountID()
							+ " | retCode:" + userInfo.getRetCode());
					if (!ResultCode.RESULT_OK.equals(userInfo.getRetCode())) {
						// 跳转到错误页面，显示错误码；
						loginErrorReturn(request, response, "4A认证获得用户信息错误:"
								+ userInfo.getRetCode());
						return "";
					} else {
						// 应用资源根据帐号信息做登录后业务处理；
						return userInfo.getAccountID();
					}
				} else {
					// 跳转到错误页面，显示错误码；
					loginErrorReturn(request, response, "4A认证错误 :"
							+ rootTicket.getRetCode());
					return "";
				}

			} else {
				// 使用应用资源本地认证；

				return "localAuthentication";
			}
		} else {
			// 使用应用资源本地认证；
			return "localAuthentication";
		}

	}
	
	/**
	 * 快捷修改个人信息---修改密码而生
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editPersonal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String dateString = StaticMethod.date2String(new Date());
		ITawSystemUserManager mgr = (ITawSystemUserManager) getBean("ItawSystemUserSaveManagerFlush");
		
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");

		String operuserid = sessionform.getUserid();
		
        String newPassword = request.getParameter("newPassword");
        TawSystemUser tawSystemUser = mgr.getTawSystemUserByuserid(operuserid);
	
			// md5加密
		tawSystemUser.setPassword(new Md5PasswordEncoder().encodePassword(
				newPassword, new String()));
		tawSystemUser.setUpdatetime(dateString);
		mgr.saveTawSystemUser(tawSystemUser);
		
		
		PartnerUserMgr partnerUserMgr = (PartnerUserMgr) getBean("partnerUserMgr");
		
		PartnerUser partnerUser = partnerUserMgr.getPartnerUserByUserId(operuserid);
		
		if(partnerUser !=null){
			
			partnerUser.setUpdateTime(dateString);
			
//			String prefix = StaticMethod.getRandomCharAndNumr(2);
//			String suffix = StaticMethod.getRandomCharAndNumr(3);
//			
//			newPassword =prefix+newPassword+suffix;
			partnerUser.setUserPassword(newPassword);
			
			partnerUserMgr.save(partnerUser);
		}
		
		return mapping.findForward("relogin");
	}
}
