package com.boco.eoms.base.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.acegisecurity.providers.encoding.Md5PasswordEncoder;

import com.boco.activiti.partner.process.model.NetResInspectUser;
import com.boco.activiti.partner.process.service.INetResInspectUserService;
import com.boco.eoms.common.util.StaticMethod;
import com.boco.eoms.commons.system.dept.exception.TawSystemException;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.bo.TawSystemDeptBo;
import com.boco.eoms.commons.system.role.util.RoleConstants;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.commons.system.user.service.ITawSystemUserManager;
import com.boco.eoms.commons.system.user.service.bo.impl.TawSystemUserRoleBo;
import com.boco.eoms.mobile.util.AndroidPropertiesUtils;
import com.google.common.base.Strings;

/**
 * Description: Android手机过滤器 Copyright: Copyright (c)2011 Company: BOCO
 * 
 * @author: LEE
 * @version: 1.0 Create at: Dec 5, 2011 2:32:03 PM
 */
public class AndroidFilter implements Filter {
	public void destroy() {

	}
	protected FilterConfig filterConfig = null;
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
//		System.out.println("session id   "+ ((HttpServletRequest) request).getSession().getId());
		if (request.getParameter("type") != null) {
			if (request.getParameter("type").equalsIgnoreCase("android")) {
				String method = request.getParameter("method");
				if (ignore || (request.getCharacterEncoding() == null)) {
					if (encoding != null)
						request.setCharacterEncoding(encoding);
				}
				if (null == method || "".equals(method)) {
					response.setCharacterEncoding("UTF-8");
					response.setContentType("text/plain");
					response.getWriter().write("");
					return;
				}
				if (method.equals("login")) {
					ITawSystemUserManager userManager = (ITawSystemUserManager) ApplicationContextHolder.getInstance().getBean("ItawSystemUserSaveManagerFlush");
					String j_username = request.getParameter("j_username");
					TawSystemUser user = userManager
							.getTawSystemUserByuserid(j_username);
					response.setCharacterEncoding("UTF-8");
					response.setContentType("text/plain");
//					
					String j_password = request.getParameter("j_password");
					String isreStr= user.getIsrest()==null?"":user.getIsrest();
					/*PartnerUserMgr partnerUserMgr = (PartnerUserMgr)ApplicationContextHolder.getInstance().getBean("partnerUserMgr");
					
					PartnerUser partnerUser = partnerUserMgr.getPartnerUserByUserId(j_username);
					boolean isNormal = true;
					if(partnerUser!=null){
						isNormal = userManager.checkPasswd(partnerUser.getUserPassword()); 
					}*/

//					
					if (null == user) {
						response.getWriter().write("erroruser");
						return;
					} else if (null == user.getPassword()) {
						response.getWriter().write("erroruser");
						return;
					} else if (user.isAccountLocked()) {
						response.getWriter().write("lockuser");
						return;
					} else if(!(user.getPassword()+"").equals(StaticMethod.nullObject2String(j_password))){
						response.getWriter().write("errorPassword");
						return;
					}else if((user.getPassword()).equals("e10adc3949ba59abbe56e057f20f883e")||(isreStr.equals("5"))){
						//e10adc3949ba59abbe56e057f20f883e --- 123456
						response.getWriter().write("errorPassword");
						return;
					}else {
						TawSystemDept sysdept;
						try {
							sysdept = TawSystemDeptBo.getInstance()
									.getDeptinfobydeptid(user.getDeptid(), "0");
							TawSystemDeptBo deptbo = TawSystemDeptBo
									.getInstance();
							TawSystemUserRoleBo rolebo = TawSystemUserRoleBo
									.getInstance();
							ArrayList rolelist = (ArrayList) rolebo.getRoleByuserid(user.getId(),RoleConstants.ROLETYPE_SUBROLE);
							
							TawSystemSessionForm sessionform = new TawSystemSessionForm();
							sessionform.setId(user.getId());
							sessionform.setUsername(user.getUsername());
							sessionform.setUserid(user.getUserid());
							sessionform.setPassword(user.getPassword());
							sessionform.setDeptid(user.getDeptid());
							sessionform.setAreaId(Strings.nullToEmpty(user.getAreaId()));
							sessionform.setAreaName(Strings.nullToEmpty(user.getAreaName()));
							sessionform.setRealPath(com.boco.eoms.base.util.StaticMethod.getWebPath());
							// 机房
							String roomid = user.getCptroomid();
							sessionform.setRoomId(roomid);
							// FIXME 机房id需修改为动态获取

							sessionform.setRoomname(user.getCptroomname());
							sessionform.setDeptpriid(sysdept.getId());
							sessionform.setDeptname(deptbo
									.getDeptnameBydeptid(user.getDeptid()));
							sessionform.setPassword(user.getPassword());
							sessionform.setRolelist(rolelist);
							sessionform.setContactMobile(user.getMobile());
							String userdegree = "";
							userdegree = user.getUserdegree();
							// form.setHavePriv(TawSystemUserAssignBo.getInstance()
							// .isExitsUserassign(userid));
							if (userdegree != null) {
								if (userdegree.equals("1")) {
									sessionform.setIsadmin(true);
								} else {
									sessionform.setIsadmin(false);
								}
							}
							String userAgent = ((HttpServletRequest) request).getHeader("User-Agent");
							String developerSerialNumber = AndroidPropertiesUtils.getValue("developerSerialNumber");
							String validateApp = AndroidPropertiesUtils.getValue("validateApp");
							((HttpServletRequest) request).getSession().setAttribute("sessionform", sessionform);
							String returnJson = "[{\"success\":\"true\""
									+ ",\"password\":\"" + user.getPassword()+ "\"" 
									+ ",\"isAdmin\":\""+ sessionform.isAdmin()+ "\""
									+ ",\"username\":\"" + user.getUsername()+ "\"" 
									+ ",\"deptname\":\""+ sessionform.getDeptname()+"\""
									+ ",\"deptid\":\"" + user.getDeptid()+ "\"" 
									+ ",\"phone\":\"" + user.getPhone()+ "\"" 
									+ ",\"mobile\":\""+ user.getMobile() + "\"" 
									+ ",\"validateApp\":\""+ validateApp + "\"" 
									+ ",\"developerSerialNumber\":\""+ developerSerialNumber + "\""
									+ ",\"JID\":\""+ ((HttpServletRequest) request).getSession().getId() + "\""
									+ "}]";
							response.getWriter().write(returnJson);
							return;
						} catch (TawSystemException e) {
							e.printStackTrace();
							response.getWriter().write("erroruser");
							return;
						}
					}
				}
				if (method.equals("loginOut")) {
					((HttpServletRequest) request).getSession()
							.removeAttribute("sessionform");
					return;
				}
				if (method.equals("testNet")) {
					response.setCharacterEncoding("UTF-8");
					response.setContentType("text/plain");
					String currentTime = StaticMethod.getCurrentDateTime();
					String returnJson= "[{\"success\":\"true\",\"sysTime\":\""
							+ currentTime + "\"}]";
					System.out.println(returnJson);
					response.getWriter().write(returnJson);
					return;
				}
				
				if ("registerUser".equals(method)) { //注册用户
					response.setCharacterEncoding("UTF-8");
					response.setContentType("text/plain");
					System.out.println("---------------进来了吗");
					String userName = StaticMethod.nullObject2String(request.getParameter("userName"));
					System.out.println("------userName="+userName);
					String group = StaticMethod.nullObject2String(request.getParameter("group"));
					System.out.println("------group="+group);
					String phone = StaticMethod.nullObject2String(request.getParameter("phone"));
					System.out.println("------phone="+phone);
					String cityId = StaticMethod.nullObject2String(request.getParameter("cityId"));
					System.out.println("------cityId="+cityId);
					String jobAttributesId = StaticMethod.nullObject2String(request.getParameter("jobAttributesId"));
					System.out.println("------jobAttributesId="+jobAttributesId);
					String password = StaticMethod.nullObject2String(request.getParameter("password"));
					System.out.println("------password="+password);
					
					Map<String,String> param = new HashMap<String,String>();
					param.put("userName", userName);
					param.put("group", group);
					param.put("phone", phone);
					param.put("cityId", cityId);
					param.put("jobAttributesId", jobAttributesId);
					param.put("password", password);
					
					INetResInspectUserService netResInspectUserService = (INetResInspectUserService) ApplicationContextHolder.getInstance().getBean("netResInspectUserService");
					String returnJson = netResInspectUserService.saveNetResInspectUser(param);
					
					System.out.println(returnJson);
					response.getWriter().write(returnJson);
					return;
				}
				if ("noSession".equals(StaticMethod.nullObject2String(request
						.getParameter("sessionType")))) {
					TawSystemSessionForm sessionform = new TawSystemSessionForm();
					((HttpServletRequest) request).getSession().setAttribute(
							"sessionform", sessionform);
					((HttpServletRequest) request).getSession()
							.setMaxInactiveInterval(2 * 60);//2小时后清除该session
					chain.doFilter(request, response);
					return;
				} else if (null != ((HttpServletRequest) request).getSession().getAttribute("sessionform")){
						chain.doFilter(request, response);
						return;
					} else {
						response.getOutputStream().write("".getBytes("UTF-8"));
						return;
				}
			} else if (request.getParameter("type").equalsIgnoreCase("exam")) {
				TawSystemSessionForm sessionform = new TawSystemSessionForm();
				String personCardNo = request.getParameter("id");

				// 将考试账号（身份证号）放入session中
				sessionform.setUserid(personCardNo);
				((HttpServletRequest) request).getSession().setAttribute(
						"sessionform", sessionform);
				((HttpServletRequest) request).getSession().setAttribute(
						"type", "andorid");
				chain.doFilter(request, response);
			} else {
				chain.doFilter(request, response);
			}
		} else {
			chain.doFilter(request, response);
		}
	}
	protected String encoding = null;
	protected boolean ignore = true;
	public void init(FilterConfig arg0) throws ServletException {
		this.filterConfig = arg0;
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

}
