package com.boco.eoms.commons.system.user.webapp.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Sheet;
import jxl.Workbook;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.acegisecurity.providers.encoding.Md5PasswordEncoder;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.MessageResources;

import com.boco.eoms.base.Constants;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.EOMSAttributes;
import com.boco.eoms.base.util.SSOConfig;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.StaticVariable;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.base.webapp.listener.UserCounterListener;
import com.boco.eoms.commons.cache.application.ApplicationCacheMgr;
import com.boco.eoms.commons.log.service.logSave;
import com.boco.eoms.commons.loging.BocoLog;
import com.boco.eoms.commons.system.area.model.TawSystemArea;
import com.boco.eoms.commons.system.area.service.ITawSystemAreaManager;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.dept.service.bo.TawSystemDeptBo;
import com.boco.eoms.commons.system.dept.util.DeptConstants;
import com.boco.eoms.commons.system.dict.model.TawSystemDictType;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.commons.system.priv.bo.TawSystemPrivAssignOut;
import com.boco.eoms.commons.system.priv.service.ITawSystemPrivMenuManager;
import com.boco.eoms.commons.system.role.model.TawSystemRole;
import com.boco.eoms.commons.system.role.model.TawSystemSubRole;
import com.boco.eoms.commons.system.role.service.ITawSystemRoleManager;
import com.boco.eoms.commons.system.role.util.RoleConstants;
import com.boco.eoms.commons.system.role.util.RoleIdList;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.system.user.model.TawPartnersUser;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.commons.system.user.service.ITawPartnersUserManager;
import com.boco.eoms.commons.system.user.service.ITawSystemUserManager;
import com.boco.eoms.commons.system.user.service.ITawSystemUserRefRoleManager;
import com.boco.eoms.commons.system.user.service.IUserMgr;
import com.boco.eoms.commons.system.user.service.bo.impl.TawSystemUserRoleBo;
import com.boco.eoms.commons.system.user.sox.mgr.IUserPasswdHistoryMgr;
import com.boco.eoms.commons.system.user.util.UserMgrLocator;
import com.boco.eoms.commons.system.user.webapp.form.TawSystemUserForm;
import com.boco.eoms.commons.ui.util.JSONUtil;
import com.boco.eoms.commons.ui.util.UIConstants;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerUser;
import com.boco.eoms.sequence.ISequenceFacade;
import com.boco.eoms.sequence.Sequence;
import com.boco.eoms.sequence.exception.SequenceNotFoundException;
import com.boco.eoms.sequence.util.SequenceLocator;

/**
 * Action class to handle CRUD on a TawSystemUser object
 * 
 * @struts.action name="tawSystemUserForm" path="/tawSystemUsers"
 *                scope="request" validate="false" parameter="method"
 *                input="mainMenu"
 * @struts.action name="tawSystemUserForm" path="/editTawSystemUser"
 *                scope="request" validate="false" parameter="method"
 *                input="list"
 * @struts.action name="tawSystemUserForm" path="/saveTawSystemUser"
 *                scope="request" validate="true" parameter="method"
 *                input="edit"
 * @struts.action-set-property property="cancellable" value="true"
 * @struts.action-forward name="edit"
 *                        path="/WEB-INF/pages/tawSystemUser/tawSystemUserForm.jsp"
 * @struts.action-forward name="list"
 *                        path="/WEB-INF/pages/tawSystemUser/tawSystemUserList.jsp"
 */
public final class TawSystemUserAction extends BaseAction {

	/**
	 * servlet上下文，随应用启动初始化，在线用户信息保存在其中
	 */
	private static transient ServletContext servletContext;
	/*
	 * 用于保存用户信息
	 */
	public ActionForward xsave(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		MessageResources mr = this.getResources(request);
		TawSystemUserForm tawSystemUserForm = (TawSystemUserForm) form;
		ITawSystemUserManager mgr = (ITawSystemUserManager) getBean("ItawSystemUserManagerFlush");
		ITawPartnersUserManager pmgr = (ITawPartnersUserManager) getBean("itawPartnersUserManager");// 2008-11-18
		// liujinlong
		// 验证用户id，字母开头，允许5-16字节，允许字母数字下划线
		if (!mgr.checkUserId(tawSystemUserForm.getUserid())) {
			String msg = mr.getMessage("user.infomation.useridfailure");
			JSONUtil.fail(response, msg);
			return null;
		}

		// 密码至少大于6位并数字加字母(至少一位大写字母）组合
		if (!mgr.checkPasswd(tawSystemUserForm.getNewPassword())) {
			String msg = mr.getMessage("user.infomation.passwdfailure",
					UserMgrLocator.getUserAttributes().getPasswdLength());
			JSONUtil.fail(response, msg);
			return null;
		}
		// 若用户存在
		if (mgr.isUserExist(tawSystemUserForm.getUserid())) {
			String msg = mr.getMessage("user.infomation.useridExist");
			JSONUtil.fail(response, msg);
			return null;
		}
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String operuserid = sessionform.getUserid();
		tawSystemUserForm.setOperuserid(operuserid);

		TawSystemUser tawSystemUser = (TawSystemUser) convert(tawSystemUserForm);
		// md5加密
		tawSystemUser.setPassword(new Md5PasswordEncoder().encodePassword(
				tawSystemUserForm.getNewPassword(), new String()));
		tawSystemUser.setDeleted("0");
		tawSystemUser.setOperremotip(request.getRemoteAddr());
		tawSystemUser.setSavetime(new Date());
		tawSystemUser.setEnabled(true);
		tawSystemUser.setAccountLocked(false);
		tawSystemUser.setId("");
		tawSystemUser.setDeptname(TawSystemDeptBo.getInstance()
				.getDeptnameBydeptid(tawSystemUser.getDeptid()));

		// ---------若为代维公司，还得保存表TawPartnersDept 2008-11-10 liujinlong
		String isPartners = tawSystemUser.getIsPartners();
		if (isPartners == null || "".equals(isPartners))
			tawSystemUser.setIsPartners("0");
		if (isPartners != null && isPartners.equals("1")) {// isPartners=1表示代维公司
			TawPartnersUser tawPartnersUser = new TawPartnersUser();

			tawPartnersUser.setInCompany(tawSystemUserForm.getInCompany());
			tawPartnersUser.setBirthday(tawSystemUserForm.getBirthday());
			tawPartnersUser.setCertificationInfo(tawSystemUserForm
					.getCertificationInfo());
			tawPartnersUser.setEducation(tawSystemUserForm.getEducation());
			tawPartnersUser.setGraduateSchool(tawSystemUserForm
					.getGraduateSchool());
			tawPartnersUser.setIDNumber(tawSystemUserForm.getIDNumber());
			tawPartnersUser.setInCity(tawSystemUserForm.getInCity());
			tawPartnersUser.setInDistrict(tawSystemUserForm.getInDistrict());
			tawPartnersUser.setIsMarried(tawSystemUserForm.getIsMarried());
			tawPartnersUser.setJobType(tawSystemUserForm.getJobType());
			tawPartnersUser.setPersonDiscription(tawSystemUserForm
					.getPersonDiscription());
			tawPartnersUser.setPhotoInfo(tawSystemUserForm.getPhotoInfo());
			tawPartnersUser.setPoliticalStatus(tawSystemUserForm
					.getPoliticalStatus());
			tawPartnersUser.setPost(tawSystemUserForm.getPost());
			tawPartnersUser.setPostState(tawSystemUserForm.getPostState());
			tawPartnersUser.setSpecialties(tawSystemUserForm.getSpecialties());
			tawPartnersUser.setStaffLabel(tawSystemUserForm.getStaffLabel());

			tawPartnersUser.setStartWorkTime(tawSystemUserForm
					.getStartWorkTime());
			tawPartnersUser.setToDeptTime(tawSystemUserForm.getToDeptTime());
			// DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			// try{
			// Date date1 = format.parse(tawSystemUserForm.getStartWorkTime());
			// Date date2 = format.parse(tawSystemUserForm.getToDeptTime());
			// tawPartnersUser.setStartWorkTime(date1);
			// tawPartnersUser.setToDeptTime(date2);
			// }catch(Exception ex){
			// ex.printStackTrace();
			// }

			tawPartnersUser.setUserid(tawSystemUser.getUserid());// 用这个字段与TawSystemDept表关联
			pmgr.saveTawPartnersUser(tawPartnersUser);
		}
		// ------------------
		//将该用户放到缓存中
		/**
		 * 合作伙伴中没有作业计划，所以屏蔽
		 * modify by 陈元蜀 2012-09-04 begin		 
		TawWorkplanCacheBean tawWorkplanCacheBean = (TawWorkplanCacheBean) ApplicationContextHolder
		.getInstance().getBean("TawWorkplanCacheBean");
		Map workplanMap = tawWorkplanCacheBean.getWorkplanUser(); 
		Map userMap = (Map)workplanMap.get("userMap");
		userMap.put(tawSystemUser.getUserid(),tawSystemUser.getUsername());
		*modify by 陈元蜀 2012-09-04 end
		*/
		mgr.saveTawSystemUser(tawSystemUser, tawSystemUserForm.getOlduserid());
		return null;
	}

	/*
	 * 用于保存（修改）个人用户信息
	 */
	public ActionForward saveuserinfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemUserForm tawSystemUserForm = (TawSystemUserForm) form;
		ITawSystemUserManager mgr = (ITawSystemUserManager) getBean("ItawSystemUserSaveManagerFlush");
		// // 验证用户id，字母开头，允许5-16字节，允许字母数字下划线
		// if (!mgr.checkUserId(tawSystemUserForm.getUserid())) {
		// ActionMessages messages = new ActionMessages();
		// messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
		// "user.infomation.useridfailure"));
		// saveMessages(request, messages);
		// return mapping.findForward(Constants.FORWARD_FAILURE);
		// }

		// // 判断是否未修改用户id，若原用户id与要修改的用户id相同，则不检查数据库用户id是否存在
		// if (tawSystemUserForm.getOlduserid() != null
		// && !tawSystemUserForm.getOlduserid().equals(
		// tawSystemUserForm.getUserid())) {
		// // 若用户存在
		// if (mgr.isUserExist(tawSystemUserForm.getUserid())) {
		// ActionMessages messages = new ActionMessages();
		// messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
		// "user.infomation.useridExist", UserMgrLocator
		// .getUserAttributes().getPasswdLength()));
		// saveMessages(request, messages);
		// return mapping.findForward(Constants.FORWARD_FAILURE);
		// }
		// }
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");

		String operuserid = sessionform.getUserid();
		tawSystemUserForm.setOperuserid(operuserid);

		TawSystemUser tawSystemUser = (TawSystemUser) convert(tawSystemUserForm);
		tawSystemUser.setDeleted("0");

		IUserPasswdHistoryMgr userPasswdHistoryMgr = (IUserPasswdHistoryMgr) getBean("userPasswdHistoryMgr");
		// 若修改密码则验证，否则不验证
		if (null != tawSystemUserForm.getNewPassword()
				&& !"".equals(tawSystemUserForm.getNewPassword().trim())) {
			// 密码至少大于6位并数字加字母组合
			if (!mgr.checkPasswd(tawSystemUserForm.getNewPassword())) {
				MessageResources mr = this.getResources(request);
				String msg = mr.getMessage("user.infomation.passwdfailure",
						UserMgrLocator.getUserAttributes().getPasswdLength());
				JSONUtil.fail(response, msg);
				return null;
				// ActionMessages messages = new ActionMessages();
				// messages.add(ActionMessages.GLOBAL_MESSAGE, new
				// ActionMessage(
				// "user.infomation.passwdfailure", UserMgrLocator
				// .getUserAttributes().getPasswdLength()));
				//
				// saveMessages(request, messages);
				// return mapping.findForward(Constants.FORWARD_FAILURE);
			}
			// 判断新调置的密码是否在近几次修改的密码中
			if (userPasswdHistoryMgr.isRepeatPasswd(tawSystemUser.getId(),
					tawSystemUserForm.getNewPassword(), UserMgrLocator
							.getUserAttributes().getPasswdRepeatNum())) {
				// ActionMessages messages = new ActionMessages();
				// messages.add(ActionMessages.GLOBAL_MESSAGE, new
				// ActionMessage(
				// "user.infomation.repeatpasswd", UserMgrLocator
				// .getUserAttributes().getPasswdRepeatNum()));
				// saveMessages(request, messages);
				// return mapping.findForward(Constants.FORWARD_FAILURE);

				MessageResources mr = this.getResources(request);
				String msg = mr
						.getMessage("user.infomation.repeatpasswd",
								UserMgrLocator.getUserAttributes()
										.getPasswdRepeatNum());
				JSONUtil.fail(response, msg);
				return null;
			}
			// md5加密
			tawSystemUser.setPassword(new Md5PasswordEncoder().encodePassword(
					tawSystemUserForm.getNewPassword(), new String()));
		} else {
			tawSystemUser.setPassword(tawSystemUserForm.getPassword());
		}

		tawSystemUser.setOperremotip(request.getRemoteAddr());
		tawSystemUser.setSavetime(new Date());
		tawSystemUser.setEnabled(true);
		// tawSystemUser.setId("");
		tawSystemUser.setDeptname(TawSystemDeptBo.getInstance()
				.getDeptnameBydeptid(tawSystemUser.getDeptid()));
		if (tawSystemUser.getIsPartners() == null
				|| "".equals(tawSystemUser.getIsPartners()))
			tawSystemUser.setIsPartners("0");
		mgr.saveTawSystemUser(tawSystemUser, tawSystemUserForm.getOlduserid());

		return mapping.findForward("edituser");
	}

	/**
	 * 快捷修改个人信息
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
		
		TawSystemUserForm tawSystemUserForm = (TawSystemUserForm) form;
		ITawSystemUserManager mgr = (ITawSystemUserManager) getBean("ItawSystemUserSaveManagerFlush");
		// // 验证用户id，字母开头，允许5-16字节，允许字母数字下划线
		// if (!mgr.checkUserId(tawSystemUserForm.getUserid())) {
		// ActionMessages messages = new ActionMessages();
		// messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
		// "user.infomation.useridfailure"));
		// saveMessages(request, messages);
		// return mapping.findForward(Constants.FORWARD_FAILURE);
		// }

		// // 判断是否未修改用户id，若原用户id与要修改的用户id相同，则不检查数据库用户id是否存在
		// if (tawSystemUserForm.getOlduserid() != null
		// && !tawSystemUserForm.getOlduserid().equals(
		// tawSystemUserForm.getUserid())) {
		// // 若用户存在
		// if (mgr.isUserExist(tawSystemUserForm.getUserid())) {
		// ActionMessages messages = new ActionMessages();
		// messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
		// "user.infomation.useridExist", UserMgrLocator
		// .getUserAttributes().getPasswdLength()));
		// saveMessages(request, messages);
		// return mapping.findForward(Constants.FORWARD_FAILURE);
		// }
		// }
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");

		String operuserid = sessionform.getUserid();
		tawSystemUserForm.setOperuserid(operuserid);

		TawSystemUser tawSystemUser = (TawSystemUser) convert(tawSystemUserForm);
		tawSystemUser.setDeleted("0");

		IUserPasswdHistoryMgr userPasswdHistoryMgr = (IUserPasswdHistoryMgr) getBean("userPasswdHistoryMgr");

		// 若添写密码则验证并修改密码，否则不修改、不修改。
		if (null != tawSystemUserForm.getNewPassword()
				&& !"".equals(tawSystemUserForm.getNewPassword())) {
			// 大于等于8位并有大写字母、小写字母、数字及特殊字符的组合
			if (!mgr.checkPasswd(tawSystemUserForm.getNewPassword())) {
				ActionMessages messages = new ActionMessages();
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"user.infomation.passwdfailure", UserMgrLocator
								.getUserAttributes().getPasswdLength()));

				saveMessages(request, messages);
				return mapping.findForward(Constants.FORWARD_FAILURE);
			}
			// 判断新调置的密码是否在近几次修改的密码中
			if (userPasswdHistoryMgr.isRepeatPasswd(tawSystemUser.getId(),
					tawSystemUserForm.getNewPassword(), UserMgrLocator
							.getUserAttributes().getPasswdRepeatNum())) {
				ActionMessages messages = new ActionMessages();
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"user.infomation.repeatpasswd", UserMgrLocator
								.getUserAttributes().getPasswdRepeatNum()));
				saveMessages(request, messages);
				return mapping.findForward(Constants.FORWARD_FAILURE);
			}
			// md5加密
			tawSystemUser.setPassword(new Md5PasswordEncoder().encodePassword(
					tawSystemUserForm.getNewPassword(), new String()));
		} else {

			tawSystemUser.setPassword(tawSystemUserForm.getPassword());
		}
		tawSystemUser.setOperremotip(request.getRemoteAddr());
		tawSystemUser.setUpdatetime(StaticMethod.date2String(new Date()));
		tawSystemUser.setEnabled(true);
		// tawSystemUser.setId("");
		tawSystemUser.setDeptname(TawSystemDeptBo.getInstance()
				.getDeptnameBydeptid(tawSystemUser.getDeptid()));
		if (tawSystemUser.getIsPartners() == null
				|| "".equals(tawSystemUser.getIsPartners()))
			tawSystemUser.setIsPartners("0");
		mgr.saveTawSystemUser(tawSystemUser,tawSystemUserForm.getNewPassword(),tawSystemUserForm.getOlduserid());
		//同步到pnr_user表中
        PartnerUserMgr partnerUserMgr = (PartnerUserMgr) getBean("partnerUserMgr");		
		PartnerUser partnerUser = partnerUserMgr.getPartnerUserByUserId(operuserid);
		
		if(partnerUser !=null){
			String newPassword =tawSystemUserForm.getNewPassword();
			if(!"".equals(newPassword)){
				partnerUser.setUpdateTime(StaticMethod.date2String(new Date()));
				
				//String prefix = StaticMethod.getRandomCharAndNumr(2);
				//String suffix = StaticMethod.getRandomCharAndNumr(3);
				
			//	String newPassword =prefix+tawSystemUserForm.getNewPassword()+suffix;
				
				
				partnerUser.setUserPassword(tawSystemUser.getPassword());
				
				partnerUserMgr.save(partnerUser);
			}
			
		}
		//同步--end
		return mapping.findForward("success");
	}

	/**
	 * 用户管理界面布局用树图 默认模板：tpl-user-layoutTree
	 */
	public ActionForward getNodes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String node = StaticMethod.null2String(request.getParameter("node"),
				StaticVariable.ProvinceID + "");
		TawSystemDeptBo deptbo = TawSystemDeptBo.getInstance();
		TawSystemUserRoleBo userrolebo = TawSystemUserRoleBo.getInstance();
		String template = StaticMethod.null2String(request.getParameter("tpl"),
				"tpl-user-layoutTree");
		List userlist = new ArrayList();
		List deptlist = new ArrayList();
		try {
			ITawSystemUserManager user = (ITawSystemUserManager) ApplicationContextHolder
					.getInstance().getBean("itawSystemUserManager");

			ITawSystemDeptManager mgr = (ITawSystemDeptManager) ApplicationContextHolder
					.getInstance().getBean("ItawSystemDeptManager");
			deptlist = (ArrayList) deptbo.getNextLevecDepts(node, "0");
			if (deptlist.size() > 0) {
				List list = new ArrayList();
				for (int i = 0; i < deptlist.size(); i++) {
					TawSystemDept tawSystemDept = new TawSystemDept();
					tawSystemDept = (TawSystemDept) deptlist.get(i);
					String deptName = tawSystemDept.getDeptName();
					if (deptName != null && !"".equals(deptName)) {
						int count = 0;
						if (tawSystemDept.getDeptId().equals("1")) {
							List lists = new ArrayList();
							lists = mgr.getNextLevecDepts(tawSystemDept
									.getDeptId(), "0");
							int allCount = user.getAllMobileBydeptid("1")
									.size();
							if (lists.size() > 0) {
								for (int j = 0; j < lists.size(); j++) {
									TawSystemDept tawSystemDepttmp = new TawSystemDept();
									tawSystemDepttmp = (TawSystemDept) lists
											.get(j);
									count += user.getUserLike(
											tawSystemDepttmp.getDeptId())
											.size();
								}

							}
							count += allCount;
							System.out.println("count:" + count);

						} else {
							count = user.getUserLike(tawSystemDept.getDeptId())
									.size();
						}
						tawSystemDept.setDeptName(deptName + "("
								+ String.valueOf(count) + ")");

						list.add(tawSystemDept);
					}
				}
				request.setAttribute("deptlist", list);
			}
			userlist = (ArrayList) userrolebo.getUserBydeptids(node);
		} catch (Exception ex) {
			ex.printStackTrace();
			BocoLog.error(this, "生成用户管理模块树图时报错：" + ex);
		}
		
		request.setAttribute("deptlist", deptlist);
		request.setAttribute("userlist", userlist);
		ApplicationCacheMgr cacheMgr = (ApplicationCacheMgr) this
				.getBean("ApplicationCacheMgr");
		cacheMgr.flush("com.boco.eoms.commons.system.dept.DEPT_CATCH");
		return mapping.findForward(template);
	}

	/**
	 * 2008-12-2 wangsixuan 用户管理界面布局用树图,不显示代维的人员 默认模板：tpl-user-layoutTree
	 * NoPartner
	 */
	public ActionForward getNodesNoPartner(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String node = StaticMethod.null2String(request.getParameter("node"),
				StaticVariable.ProvinceID + "");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String operuserid = sessionform.getUserid();
		ITawSystemUserManager mgr1 = (ITawSystemUserManager) getBean("itawSystemUserManager");
		
		TawSystemUser tawSystemUser = mgr1.getUserByuserid(operuserid);
		//如果当前登录用户部门不是山东省联通巡检单位和中国联通山东分公司将只显示用户所属部门信息
		if(node.equals("-1")&&!tawSystemUser.getDeptid().equals("101")&&!tawSystemUser.getDeptid().equals("201")){
			node=tawSystemUser.getDeptid();
		}
		TawSystemDeptBo deptbo = TawSystemDeptBo.getInstance();
		TawSystemUserRoleBo userrolebo = TawSystemUserRoleBo.getInstance();
		String template = StaticMethod.null2String(request.getParameter("tpl"),
				"tpl-user-layoutTree");
		List userlist = new ArrayList();
		List deptlist = new ArrayList();
		try {
			deptlist = (ArrayList) deptbo.getNextLevecDeptsNoPartner(node, "0");
			userlist = (ArrayList) userrolebo.getUserBydeptids(node);
		} catch (Exception ex) {
			BocoLog.error(this, "生成用户管理模块树图时报错：" + ex);
		}

		//2009-5-26 新建类RoleIdList，定义角色属性
		ITawSystemDeptManager mgr = (ITawSystemDeptManager) ApplicationContextHolder
		.getInstance().getBean("ItawSystemDeptManager");
		RoleIdList roleIdList = (RoleIdList) getBean("roleIdInGroup");//读取角色组
		List roleList = sessionform.getRolelist();
		ITawSystemAreaManager areaManager = (ITawSystemAreaManager) getBean("ItawSystemAreaManager");
		String hasRight = (String)request.getParameter("hasRight");
		if(node.equals("-1"))hasRight = "0";//若是根节点
		request.setAttribute("hasRight", hasRight);
		request.setAttribute("areaManager", areaManager);
		request.setAttribute("roleIdList", roleIdList);
		request.setAttribute("userid", sessionform.getUserid());
		request.setAttribute("roleList", roleList);
		request.setAttribute("tawSystemDeptManager", mgr);
		
		request.setAttribute("deptlist", deptlist);
		request.setAttribute("userlist", userlist);
		return mapping.findForward(template);
	}

	/**
	 * 2008-12-2 wangsixuan 用户管理界面布局用树图,只显示代维的人员 默认模板：tpl-user-layoutTree
	 * NoPartner
	 */
	public ActionForward getNodesPartner(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String node = StaticMethod.null2String(request.getParameter("node"),
				StaticVariable.ProvinceID + "");
		TawSystemDeptBo deptbo = TawSystemDeptBo.getInstance();
		TawSystemUserRoleBo userrolebo = TawSystemUserRoleBo.getInstance();
		String template = StaticMethod.null2String(request.getParameter("tpl"),
				"tpl-user-layoutTree");
		List userlist = new ArrayList();
		List deptlist = new ArrayList();
		try {
			deptlist = (ArrayList) deptbo.getNextLevecDepts(node, "0");
			if (deptbo.isPartner(node))
				userlist = (ArrayList) userrolebo.getUserBydeptids(node);
		} catch (Exception ex) {
			BocoLog.error(this, "生成用户管理模块树图时报错：" + ex);
		}
		request.setAttribute("deptlist", deptlist);
		request.setAttribute("userlist", userlist);
		return mapping.findForward(template);
	}

	/**
	 * 根据模块或功能的编码，删除该对象。
	 * 页面访问：http://hostname:port/webappname/tawSystemUser.html?method=xdelete&id=inputvalue
	 */
	public String xdelete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String _strId = request.getParameter("id");

		ITawSystemUserManager mgrflush = (ITawSystemUserManager) getBean("ItawSystemUserManagerFlush");
		ITawPartnersUserManager pmgr = (ITawPartnersUserManager) getBean("itawPartnersUserManager");// 2008-11-18
		// liujinlong

		String userid = mgrflush.getTawSystemUser(_strId).getUserid();
		// ITawSystemPrivAssignManager mgr = (ITawSystemPrivAssignManager)
		// getBean("ItawSystemPrivAssignManager");

		// -----若该系统人员属于代维公司，则一并删除代维公司人员附加表 2008-11-12 liujinlong
		if (mgrflush.getTawSystemUser(_strId).getIsPartners().equals("1")) {
			TawPartnersUser tawPartnersUser = pmgr
					.getPartnersUserByUserId(userid);
			if (tawPartnersUser.getId() != null) {
				pmgr.removeTawPartnersUser(tawPartnersUser);
			}
		}
		// -----------------------------------

		// 假如用户存在权限则需要将用户拥有的权限一并删除
		// if (mgr.hasAssign(userid)) {
		//
		// mgrflush.removeTawSystemUser(_strId, userid);
		//
		// } else {
		//在缓存中删除改用户
		/**
		 * 合作伙伴中没有作业计划，所以屏蔽
		 * modify by 陈元蜀 2012-09-04 begin		 
		TawWorkplanCacheBean tawWorkplanCacheBean = (TawWorkplanCacheBean) ApplicationContextHolder
		.getInstance().getBean("TawWorkplanCacheBean");
		Map workplanMap = tawWorkplanCacheBean.getWorkplanUser(); 
		Map userMap = (Map)workplanMap.get("userMap");
		userMap.put(tawSystemUser.getUserid(),tawSystemUser.getUsername());
		*modify by 陈元蜀 2012-09-04 end
		*/
		// 从缓存里取deptName
		mgrflush.removeTawSystemUser(_strId);
		// }

		return null;
	}

	/**
	 * 用户删除恢复
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */

	public String xresume(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String _strId = request.getParameter("id");
		ITawSystemUserManager mgr = (ITawSystemUserManager) getBean("itawSystemUserManager");
		mgr.resumeTawSystemUser(_strId);
		return null;
	}

	public ActionForward resume(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String _strId = request.getParameter("id");
		ITawSystemUserManager mgr = (ITawSystemUserManager) getBean("itawSystemUserManager");
		mgr.resumeTawSystemUser(_strId);
		return mapping.findForward("searchdel");
	}

	/**
	 * ajax请求获取某节点的详细信息。 panlong
	 */
	public String xget(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String _strId = request.getParameter("id");
		TawSystemUserForm tawSystemUserForm = (TawSystemUserForm) form;
		ITawSystemUserManager mgr = (ITawSystemUserManager) getBean("itawSystemUserManager");
		ITawPartnersUserManager pmgr = (ITawPartnersUserManager) getBean("itawPartnersUserManager");// 2008-11-18
		// liujinlong
		TawSystemUser _objOneOpt = new TawSystemUser();
		_objOneOpt = mgr.getTawSystemUser(_strId);
		tawSystemUserForm = (TawSystemUserForm) convert(_objOneOpt);
		tawSystemUserForm.setOlduserid(_objOneOpt.getUserid());
		// tawSystemUserForm.setOldPassword(_objOneOpt.getPassword());
		tawSystemUserForm.setPassword(_objOneOpt.getPassword());

		if (_objOneOpt.getIsPartners() != null
				&& _objOneOpt.getIsPartners().equals("1")) {
			TawPartnersUser tawPartnersUser = pmgr
					.getPartnersUserByUserId(_objOneOpt.getUserid());
			tawSystemUserForm.setInCompany(tawPartnersUser.getInCompany());
			tawSystemUserForm.setInCity(tawPartnersUser.getInCity());
			tawSystemUserForm.setInDistrict(tawPartnersUser.getInDistrict());
			tawSystemUserForm.setEducation(tawPartnersUser.getEducation());
			tawSystemUserForm.setBirthday(tawPartnersUser.getBirthday());
			tawSystemUserForm.setTechTitle(tawPartnersUser.getTechTitle());
			tawSystemUserForm.setIDNumber(tawPartnersUser.getIDNumber());
			tawSystemUserForm.setPostState(tawPartnersUser.getPostState());
			tawSystemUserForm.setPhotoInfo(tawPartnersUser.getPhotoInfo());
			tawSystemUserForm.setStaffLabel(tawPartnersUser.getStaffLabel());
			tawSystemUserForm.setIsMarried(tawPartnersUser.getIsMarried());
			tawSystemUserForm.setJobType(tawPartnersUser.getJobType());
			tawSystemUserForm.setPost(tawPartnersUser.getPost());
			tawSystemUserForm.setGraduateSchool(tawPartnersUser
					.getGraduateSchool());
			tawSystemUserForm.setSpecialties(tawPartnersUser.getSpecialties());
			tawSystemUserForm.setPoliticalStatus(tawPartnersUser
					.getPersonDiscription());
			tawSystemUserForm.setWorkPeriod(tawPartnersUser.getWorkPeriod());
			tawSystemUserForm.setPersonDiscription(tawPartnersUser
					.getPersonDiscription());
			tawSystemUserForm.setStartWorkTime(tawPartnersUser
					.getStartWorkTime());
			tawSystemUserForm.setToDeptTime(tawPartnersUser.getToDeptTime());
			tawSystemUserForm.setCertificationInfo(tawPartnersUser
					.getCertificationInfo());
		}

		JSONObject jsonRoot = new JSONObject();
		jsonRoot = JSONObject.fromObject(tawSystemUserForm);
		JSONUtil.print(response, jsonRoot.toString());
		return null;
	}

	/**
	 * 修改用户信息 panlong
	 */
	public ActionForward editUserInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String operuserid = sessionform.getUserid();
		TawSystemUserForm tawSystemUserForm = (TawSystemUserForm) form;

		ITawSystemUserManager mgr = (ITawSystemUserManager) getBean("itawSystemUserManager");
		TawSystemUser tawSystemUser = mgr.getUserByuserid(operuserid);
		tawSystemUserForm = (TawSystemUserForm) convert(tawSystemUser);
		tawSystemUserForm.setUpdatetime(StaticMethod.getOperDay());
		updateFormBean(mapping, request, tawSystemUserForm);

		// 获得人员所属大角色和子角色
		ITawSystemUserRefRoleManager userRefRoleMgr = (ITawSystemUserRefRoleManager) getBean("itawSystemUserRefRoleManager");
		List subRoleList = userRefRoleMgr.getSubRoleByUserId(operuserid,
				RoleConstants.ROLETYPE_SUBROLE);
		List roleList = new ArrayList();
		HashMap hm = new HashMap();
		Iterator subRoleIt = subRoleList.iterator();
		while (subRoleIt.hasNext()) {
			TawSystemSubRole subRole = (TawSystemSubRole) subRoleIt.next();
			if (null == hm.get(String.valueOf(subRole.getRoleId()))) {
				ITawSystemRoleManager roleMgr = (ITawSystemRoleManager) getBean("ItawSystemRoleManager");
				TawSystemRole role = roleMgr.getTawSystemRole(subRole
						.getRoleId());
				roleList.add(role);
				hm.put(String.valueOf(role.getRoleId()), role);
			}
		}
		request.setAttribute("subRoleList", subRoleList);
		request.setAttribute("roleList", roleList);

		return mapping.findForward("edituser");
	}

	/**
	 * 修改用户信息 panlong
	 */
	public ActionForward saveUserLogOut(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String operuserid = sessionform.getUserid();
		request.getSession().removeAttribute("sessionform");
		request.getSession().removeAttribute(
				"edu.yale.its.tp.cas.client.filter.user");
		// TawCommonLog.saveLog(this, operuserid, request.getRemoteAddr(),
		// "0002",
		// operuserid + " 于 " + StaticMethod.getCurrentDateTime()
		// + " 登出系统!");
		TawSystemPrivAssignOut privassimgr = TawSystemPrivAssignOut.getInstance();
		String modeName = privassimgr.getNameBycode(request.getParameter("id"));
		
		logSave log = logSave.getInstance(modeName,
				operuserid, "0002", request.getRemoteAddr(), operuserid + " 于:"
						+ StaticMethod.getCurrentDateTime() + " 登出系统.", "111");
		
		String sequenceOpen = StaticMethod
		.null2String(((EOMSAttributes) ApplicationContextHolder
				.getInstance().getBean("eomsAttributes"))
				.getSequenceOpen());
		if ("true".equals(sequenceOpen)) {
			// 初始化队列
			ISequenceFacade sequenceFacade = SequenceLocator
					.getSequenceFacade();
			Sequence savelogSequence = null;
			try {
				savelogSequence = sequenceFacade.getSequence("savelog");
			} catch (SequenceNotFoundException e) {
				e.printStackTrace();
			}			
			// 把action撇队列里
			sequenceFacade.put(log, "info", 
					null,
					null, null,
					savelogSequence);
			savelogSequence.setChanged();
			sequenceFacade.doJob(savelogSequence);
		} else {
			log.info();
		}
		
		
		// 单点登陆方式
		if (Constants.LOGIN_SSO.equals(UtilMgrLocator.getEOMSAttributes()
				.getLoginType())) {
			String serverName = request.getServerName();
			// String casAddr = ((String) UtilMgrLocator.getEOMSAttributes()
			// .getRegister().get(serverName)).trim();
			// by leo 多IP退出
			SSOConfig ssoConfig = (SSOConfig) UtilMgrLocator
					.getEOMSAttributes().getRegister().get(serverName);
			String casAddr = ssoConfig.getCasLogin().trim();

			String eomsAddr = "http://" + ssoConfig.getEomsServerName().trim() + ":" + request.getServerPort()
					+ request.getContextPath();

			// String eomsAddr = "http://" + serverName + ":"
			// + request.getServerPort() + request.getContextPath();
			response.sendRedirect(casAddr + "/logout?eomsUrl=" + eomsAddr);
			return null;
		}
		// 以acegi方式登陆
		return mapping.findForward("relogin");
	}

	/**
	 * 修改用户信息 panlong
	 */
	public void saveUserLogOutClose(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String operuserid = sessionform.getUserid();
		request.getSession().removeAttribute("sessionform");
		// TawCommonLog.saveLog(this, operuserid, request.getRemoteAddr(),
		// "0002",
		// operuserid + " 于 " + StaticMethod.getCurrentDateTime()
		// + " 登出系统!");
		TawSystemPrivAssignOut privassimgr = TawSystemPrivAssignOut.getInstance();
		String modeName = privassimgr.getNameBycode(request.getParameter("id"));
		logSave log = logSave.getInstance(modeName,
				operuserid, "0002", request.getRemoteAddr(), operuserid + " 于:"
						+ StaticMethod.getCurrentDateTime() + " 登出系统.", "111");
		log.info();
	}

	/**
	 * 唯一性验证 panlong
	 */
	public String checkUnique(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String type = StaticMethod.null2String(request.getParameter("name"));
		String value = StaticMethod.null2String(request.getParameter("value"));
		boolean flag = false;
		if (type.equalsIgnoreCase("userid")) {
			ITawSystemUserManager mgr = (ITawSystemUserManager) getBean("itawSystemUserManager");
			String id = StaticMethod.null2String(mgr.getUserByuserid(value)
					.getId());
			String tawid = StaticMethod.null2String(mgr
					.getTawSystemUserByuserid(value).getId());
			if (!tawid.equals("")) {
				// 如果用户曾经存在，现已被删除则提示假删除，找管理员回复
				if ("".equals(id)) {
					JSONUtil.print(response, "falsedelete");
					return null;
				}
				flag = true;
			}
		} else if (type.equalsIgnoreCase("deptName")) {
			ITawSystemDeptManager mgr = (ITawSystemDeptManager) getBean("ItawSystemDeptManagerFlush");
			flag = mgr.getDeptnameIsExist(value, String
					.valueOf(StaticVariable.UNDELETED));
		} else if (type.equalsIgnoreCase("areaname")) {
			ITawSystemAreaManager mgr = (ITawSystemAreaManager) getBean("ItawSystemAreaManager");
			flag = mgr.isExitAreaName(value);
		} else if (type.equalsIgnoreCase("name")) {
			ITawSystemPrivMenuManager mgr = (ITawSystemPrivMenuManager) getBean("ItawSystemPrivMenuManager");
			flag = mgr.isExits(value);
		}
		JSONUtil.print(response, (new Boolean(flag)).toString());
		return null;
	}

	/**
	 * ajax请求修改某节点的详细信息。 mios 070724
	 */
	public ActionForward xedit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		MessageResources mr = this.getResources(request);
		TawSystemUserForm tawSystemUserForm = (TawSystemUserForm) form;
		ITawSystemUserManager mgr = (ITawSystemUserManager) getBean("ItawSystemUserManagerFlush");
		ITawPartnersUserManager pmgr = (ITawPartnersUserManager) getBean("itawPartnersUserManager");// 2008-11-18
		// liujinlong

		// 字母开头，允许5-16字节，允许字母数字下划线
		if (!mgr.checkUserId(tawSystemUserForm.getUserid())) {
			String msg = mr.getMessage("user.infomation.useridfailure");
			JSONUtil.fail(response, msg);
			return null;
		}

		// 判断是否未修改用户id，若原用户id与要修改的用户id相同，则不检查数据库用户id是否存在
		if (tawSystemUserForm.getOlduserid() != null
				&& !tawSystemUserForm.getOlduserid().equals(
						tawSystemUserForm.getUserid())) {
			// 若用户存在
			if (mgr.isUserExist(tawSystemUserForm.getUserid())) {
				String msg = mr.getMessage("user.infomation.useridExist");
				JSONUtil.fail(response, msg);
				return null;
			}
		}
		IUserPasswdHistoryMgr userPasswdHistoryMgr = (IUserPasswdHistoryMgr) getBean("userPasswdHistoryMgr");
		TawSystemUser tawSystemUser = (TawSystemUser) convert(tawSystemUserForm);
		// 若修改密码
		if (tawSystemUserForm.getNewPassword() != null
				&& !"".equals(tawSystemUserForm.getNewPassword().trim())) {
			// 密码至少大于6位并数字加字母组合
			if (!mgr.checkPasswd(tawSystemUserForm.getNewPassword())) {
				String msg = mr.getMessage("user.infomation.passwdfailure",
						UserMgrLocator.getUserAttributes().getPasswdLength());
				JSONUtil.fail(response, msg);
				return null;
			}
			// 判断新调置的密码是否在近几次修改的密码中
			if (userPasswdHistoryMgr.isRepeatPasswd(tawSystemUser.getId(),
					tawSystemUserForm.getNewPassword(), UserMgrLocator
							.getUserAttributes().getPasswdRepeatNum())) {

				String msg = mr
						.getMessage("user.infomation.repeatpasswd",
								UserMgrLocator.getUserAttributes()
										.getPasswdRepeatNum());
				JSONUtil.fail(response, msg);
				return null;
			}
			tawSystemUser.setPassword(new Md5PasswordEncoder().encodePassword(
					tawSystemUserForm.getNewPassword(), new String()));
		} else {
			tawSystemUser.setPassword(tawSystemUserForm.getPassword());
		}

		// 设置保存时间，90天后密码将失效
		tawSystemUser.setSavetime(new Date());

		// ---------若为代维公司，还得保存表TawPartnersDept 2008-11-12 liujinlong
		String isPartners = tawSystemUser.getIsPartners();
		if (tawSystemUser.getIsPartners() == null
				|| "".equals(tawSystemUser.getIsPartners()))
			tawSystemUser.setIsPartners("0");
		if (isPartners != null && isPartners.equals("1")) {// isPartners=1表示代维公司
			TawPartnersUser tawPartnersUser = pmgr
					.getPartnersUserByUserId(tawSystemUser.getUserid());

			tawPartnersUser.setInCompany(tawSystemUserForm.getInCompany());
			tawPartnersUser.setBirthday(tawSystemUserForm.getBirthday());
			tawPartnersUser.setCertificationInfo(tawSystemUserForm
					.getCertificationInfo());
			tawPartnersUser.setEducation(tawSystemUserForm.getEducation());
			tawPartnersUser.setGraduateSchool(tawSystemUserForm
					.getGraduateSchool());
			tawPartnersUser.setIDNumber(tawSystemUserForm.getIDNumber());
			tawPartnersUser.setInCity(tawSystemUserForm.getInCity());
			tawPartnersUser.setInDistrict(tawSystemUserForm.getInDistrict());
			tawPartnersUser.setIsMarried(tawSystemUserForm.getIsMarried());
			tawPartnersUser.setJobType(tawSystemUserForm.getJobType());
			tawPartnersUser.setPersonDiscription(tawSystemUserForm
					.getPersonDiscription());
			tawPartnersUser.setPhotoInfo(tawSystemUserForm.getPhotoInfo());
			tawPartnersUser.setPoliticalStatus(tawSystemUserForm
					.getPoliticalStatus());
			tawPartnersUser.setPost(tawSystemUserForm.getPost());
			tawPartnersUser.setPostState(tawSystemUserForm.getPostState());
			tawPartnersUser.setSpecialties(tawSystemUserForm.getSpecialties());
			tawPartnersUser.setStaffLabel(tawSystemUserForm.getStaffLabel());

			tawPartnersUser.setStartWorkTime(tawSystemUserForm
					.getStartWorkTime());
			tawPartnersUser.setToDeptTime(tawSystemUserForm.getToDeptTime());

			// DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			// try{
			// Date date1 = format.parse(tawSystemUserForm.getStartWorkTime());
			// Date date2 = format.parse(tawSystemUserForm.getToDeptTime());
			// tawPartnersUser.setStartWorkTime(date1);
			// tawPartnersUser.setToDeptTime(date2);
			// }catch(Exception ex){
			// ex.printStackTrace();
			// }

			pmgr.saveTawPartnersUser(tawPartnersUser);
		}
		// ------------------
//		将该修改过的用户放到缓存中
		/**
		 * 合作伙伴中没有作业计划，所以屏蔽
		 * modify by 陈元蜀 2012-09-04 begin		 
		TawWorkplanCacheBean tawWorkplanCacheBean = (TawWorkplanCacheBean) ApplicationContextHolder
		.getInstance().getBean("TawWorkplanCacheBean");
		Map workplanMap = tawWorkplanCacheBean.getWorkplanUser(); 
		Map userMap = (Map)workplanMap.get("userMap");
		userMap.put(tawSystemUser.getUserid(),tawSystemUser.getUsername());
		*modify by 陈元蜀 2012-09-04 end
		*/
		mgr.saveTawSystemUser(tawSystemUser, tawSystemUserForm.getOlduserid());

		return null;
	}

	/**
	 * 查询在线用户信息
	 */
	public ActionForward searchOnlineUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemUserForm userForm = (TawSystemUserForm) form;
		ITawSystemUserManager mgr = (ITawSystemUserManager) getBean("itawSystemUserManager");
		HashMap users = UserCounterListener.getOnlineUsersForSearch();
		HashMap result = mgr.getOnlineUsers(users,userForm);
		request.setAttribute("result",result);
		return mapping.findForward("success");
	}
	
	/**
	 * AJAX查询用户名称
	 */
	public ActionForward xquery(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String q = StaticMethod.null2String(request.getParameter("q"));
		IUserMgr mgr = (IUserMgr) getBean("UserMgrImpl");
		List list = mgr.listByNameQuery(q);
		request.setAttribute("list", list);
		return mapping.findForward(UIConstants.XTPL_USER);
	}
	
	// AJAX方式进行搜索请求时的数据处理
	public void xsearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'xsearch' method");
		}

		TawSystemUserForm searchForm = (TawSystemUserForm) form;
		String userName = searchForm.getUsername();

		ITawSystemUserManager mgr = (ITawSystemUserManager) getBean("itawSystemUserManager");

		List list = new ArrayList();
		list = mgr.getUsersByName(userName);

		// 创建JSON对象
		JSONObject jsonRoot = new JSONObject();

		// 将查询结果的行数放入JSON对象中
		jsonRoot.put("results", list.size());

		// 将查询记录转换为JSON数组放入JSON对象中
		jsonRoot.put("rows", JSONArray.fromObject(list));

		JSONUtil.print(response, jsonRoot.toString());

	}

	/**
	 * 根据关键字查询人员名称，得到人员列表，只是代维部门人员 2008-12-3 wangsixuan
	 * 
	 * @param word
	 *            人员名称关键字
	 * @return List
	 */
	public void xsearchPartner(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'xsearch' method");
		}

		TawSystemUserForm searchForm = (TawSystemUserForm) form;
		String userName = searchForm.getUsername();
		String cptroomname = searchForm.getFax();
		String deptname = searchForm.getPhone();
		String mobile = searchForm.getMobile();
		String email = searchForm.getEmail();

		ITawSystemUserManager mgr = (ITawSystemUserManager) getBean("itawSystemUserManager");

		List list = new ArrayList();
		list = mgr.getUsersByInfo(userName, cptroomname, deptname, mobile,
				email);
		List listForDel = new ArrayList();
		for (int i = 0; i < list.size(); i++) {
			TawSystemUser temp = (TawSystemUser) list.get(i);
			TawSystemDeptBo deptbo = TawSystemDeptBo.getInstance();
			if (!deptbo.isPartner(temp.getDeptid()))
				listForDel.add(i + "");
		}
		for (int j = (listForDel.size() - 1); j >= 0; j--) {
			String s = listForDel.get(j).toString();
			int jj = Integer.parseInt(s);
			list.remove(jj);
		}

		ITawSystemDictTypeManager dmgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");
		List list2 = new ArrayList();
		TawSystemUser sUser = new TawSystemUser();
		TawSystemDictType dict = new TawSystemDictType();
		for (int i = 0; i < list.size(); i++) {
			sUser = (TawSystemUser) list.get(i);
			sUser.setIsfullemploy("");
			sUser.setIsrest("");
			if (sUser.getSex() != null && !"".equals(sUser.getSex())
					&& sUser.getSex().length() > 4) {
				dict = dmgr.getDictTypeByDictid(sUser.getSex());
				sUser.setIsfullemploy(dict.getDictName());
			}
			if (sUser.getFamilyaddress() != null
					&& !"".equals(sUser.getFamilyaddress())
					&& sUser.getSex().length() > 4) {
				dict = dmgr.getDictTypeByDictid(sUser.getFamilyaddress());
				sUser.setIsrest(dict.getDictName());
			}
			list2.add(sUser);
		}

		// 创建JSON对象
		JSONObject jsonRoot = new JSONObject();

		// 将查询结果的行数放入JSON对象中
		jsonRoot.put("results", list2.size());

		// 将查询记录转换为JSON数组放入JSON对象中
		jsonRoot.put("rows", JSONArray.fromObject(list2));

		JSONUtil.print(response, jsonRoot.toString());

	}

	/**
	 * 根据关键字查询人员名称，得到人员列表，只是非代维部门人员 2008-12-3 wangsixuan
	 * 
	 * @param word
	 *            人员名称关键字
	 * @return List
	 */
	public void xsearchNoPartner(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'xsearch' method");
		}

		TawSystemUserForm searchForm = (TawSystemUserForm) form;
		String userName = searchForm.getUsername();
		String deptName = searchForm.getPost();
		String userId = searchForm.getUserid();
		ITawSystemUserManager mgr = (ITawSystemUserManager) getBean("itawSystemUserManager");

		List list = new ArrayList();
		list = mgr.getUsersByName(userName, deptName,userId);
		List listForDel = new ArrayList();
		TawSystemUser temp = null;
		TawSystemDeptBo deptbo = TawSystemDeptBo.getInstance();
		for (int i = 0; i < list.size(); i++) {
			temp = (TawSystemUser) list.get(i);
			if (deptbo.isPartner(temp.getDeptid()))
				listForDel.add(i + "");
		}
		for (int j = (listForDel.size() - 1); j >= 0; j--) {
			String s = listForDel.get(j).toString();
			int jj = Integer.parseInt(s);
			list.remove(jj);
		}
		
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");

        ITawSystemAreaManager areaManager = (ITawSystemAreaManager) getBean("ItawSystemAreaManager");
		String hasRight = "0";
        String operuserid = sessionform.getUserid();
    	if(operuserid.equals("admin")){
    		hasRight = "1";
    	}
		RoleIdList roleIdList = (RoleIdList) getBean("roleIdInGroup");
		List roleList = sessionform.getRolelist();
		JSONArray json = new JSONArray();
		if (list.size() > 0){
			TawSystemDept dept = null;
			TawSystemUser user = null;
			for (int i = 0; i < list.size(); i++) {	
				user = (TawSystemUser) list.get(i);
				dept = deptbo.getDeptinfobydeptid(user.getDeptid(), "0");
				String deptId = dept.getDeptId();
				TawSystemSubRole tempRole = null;
				TawSystemArea area = null;
				String areaId = null;//用于循环地域
				for(int k=0;k<roleList.size();k++){
					tempRole = (TawSystemSubRole)roleList.get(k);
					if(tempRole.getRoleId() == roleIdList.getUserAdminRoleId().intValue()){
					    if((tempRole.getDeptId()!=null&&!tempRole.getDeptId().equals(""))&&(tempRole.getArea()==null||tempRole.getArea().equals(""))){//子角色 只存了地市id，没存部门id
					        area = areaManager.getAreaByAreaId(tempRole.getDeptId());
					        if(tempRole.getDeptId().equals(dept.getAreaid())){//属于这个地市的部门都有权限；因为父部门没有权限，那么父部门所在地域应该包含子角色地域()
								hasRight = "1";
								break;
							}
							else if(area!=null&&area.getId()!=null&&!area.getLeaf().equals("1")){//如果地域有子地域（如广西下有南宁），那么得判断部门是不是在子地域，在父地域下有权限那么在子地域下肯定有权限
							    areaId = dept.getAreaid();
							    while(areaId!=null&&!areaId.equals("-1")){
							       area = areaManager.getAreaByAreaId(areaId);
							       if(area.getParentAreaid().equals(tempRole.getDeptId())){
							           	hasRight = "1";
								        break;
							       }
							       else {
							           areaId = area.getParentAreaid();
							           hasRight = "0";
							       }
							    }
							    if(hasRight.equals("1")) break;
							}
							else hasRight = "0";
						}
					    else if((tempRole.getArea()!=null&&!tempRole.getArea().equals(""))&&(tempRole.getDeptId()==null||tempRole.getDeptId().equals(""))){//子角色 只存了部门id，没存地市id
					        if(tempRole.getArea().equals(dept.getDeptId())){//部门是子角色所选部门，给其权限
				            	hasRight = "1";
						        break;
					        }
					        else { 
					            TawSystemDept tsd = null;
					            deptId = dept.getParentDeptid();
					            while(!deptId.equals(DeptConstants.ROOT_PARENTDEPT_ID)) {
					               tsd = deptbo.getDeptinfobydeptid(deptId, "0");
					               if(tsd.getId()!=null){
						               if(tempRole.getArea().equals(tsd.getDeptId())){
							              hasRight = "1";
								          break;
						               }
						               else {
						            	  deptId = tsd.getParentDeptid();
						                  hasRight = "0";
						               }
					               }
					               else {//第一个节点“省公司”会执行到这一步
					            	   deptId = DeptConstants.ROOT_PARENTDEPT_ID;
						               hasRight = "0";
					               }
					            }
					            if(hasRight.equals("1"))break;
					        }
					    }
					    else if((tempRole.getDeptId()!=null&&!tempRole.getDeptId().equals(""))&&(tempRole.getArea()!=null&&!tempRole.getArea().equals(""))){//子角色既存了部门id，又存了地市id
					        areaId = dept.getAreaid();
				            String isArea = "0";
				            while(areaId!=null&&!areaId.equals("-1")){//判断当前部门是否在子角色规定的地域和子地域里，如果在就判断部门是否是子角色规定的部门或子部门
						       area = areaManager.getAreaByAreaId(areaId);
						       if(area.getAreaid().equals(tempRole.getDeptId())){
						           	isArea = "1";
							        break;
						       }
						       else {
						           areaId = area.getParentAreaid();
						           isArea = "0";
						       }
					        }
					        if(isArea.equals("1")){//当前部门在子角色规定的地域或子地域里，那么判断当前部门是否是子角色规定的部门或子部门
					            if(tempRole.getArea().equals(dept.getDeptId())){
					            	hasRight = "1";
							        break;
						        }
					            else { 
						            TawSystemDept tsd = null;
						            deptId = dept.getParentDeptid();
						            while(!deptId.equals(DeptConstants.ROOT_PARENTDEPT_ID)) {
						               tsd = deptbo.getDeptinfobydeptid(deptId, "0");
						               if(tsd.getId()!=null){
							               if(tempRole.getArea().equals(tsd.getDeptId())){
								              hasRight = "1";
									          break;
							               }
							               else {
							            	  deptId = tsd.getParentDeptid();
							                  hasRight = "0";
							               }
						               }
						               else {//第一个节点“省公司”会执行到这一步
						            	   deptId = "-1";
							               hasRight = "0";
						               }
						            }
						            if(hasRight.equals("1"))break;
						        }
					        }
					        else hasRight = "0";//当前部门不在子角色规定的地域或子地域里
					    }
					    else hasRight = "0";
					}
			}
				JSONObject jitem = new JSONObject();
				jitem.put(UIConstants.JSON_ID, user.getId());
				jitem.put(UIConstants.JSON_TEXT, user.getUsername());
				jitem.put(UIConstants.JSON_NODETYPE, "user");
				jitem.put("mobile", user.getMobile());
				jitem.put("username", user.getUsername());
				jitem.put("phone", user.getPhone());
				jitem.put("deptname", dept.getDeptName());
				if(hasRight.equals("1")){
				    jitem.put("allowChild", false);
				    jitem.put("allowDelete", true);
				    jitem.put("allowEdit", true);
				    jitem.put("allowsubroleList-mi", true);
				    jitem.put("allowadjustsubrole-mi", true);
				}
				else {
					jitem.put("allowChild", false);
				    jitem.put("allowDelete", false);
				    jitem.put("allowEdit", false);
				    jitem.put("allowsubroleList-mi", false);
				    jitem.put("allowadjustsubrole-mi", false);
				}
				json.put(jitem);
		}
		}

		// 创建JSON对象
		JSONObject jsonRoot = new JSONObject();

		// 将查询结果的行数放入JSON对象中
		jsonRoot.put("results", list.size());

		// 将查询记录转换为JSON数组放入JSON对象中
		jsonRoot.put("rows", json);

		JSONUtil.print(response, jsonRoot.toString());

	}

	/**
	 * 搜索删除人员列表
	 * 
	 * @param word
	 *            人员名称关键字
	 * @return List
	 */
	public ActionForward xsearchdel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			TawSystemUserForm searchForm = (TawSystemUserForm) form;
			String userName = StaticMethod.nullObject2String(searchForm
					.getUsername());

			ITawSystemUserManager mgr = (ITawSystemUserManager) getBean("itawSystemUserManager");
			List list = new ArrayList();
			list = mgr.getUsersDelByName(userName);
			request.setAttribute("tawSystemUserList", list);
		} catch (Exception e) {
			e.printStackTrace();
			return mapping.findForward("failure");
		}
		return mapping.findForward("searchdel");

	}

	/*
	 * 附件批量录入
	 */
	public ActionForward xaccessory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		try {
			// 首先将文件从客户端上传到服务器
			String timeTag = StaticMethod
					.getCurrentDateTime("yyyy_MM_dd_HHmmss");
			String sysTemPaht = StaticMethod.getWebPath();
			TawSystemUserForm accessoryForm = (TawSystemUserForm) form;
			String uploadPath = "/WEB-INF/pages/tawSystemUser/upfiles";
			String filePath = sysTemPaht + uploadPath + "/" + timeTag + ".xls";
			File tempFile = new File(sysTemPaht + uploadPath);
			if (!tempFile.exists()) {
				tempFile.mkdir();
			}

			FormFile file = accessoryForm.getAccessoryName();
			try {
				InputStream stream = file.getInputStream(); // 把文件读入
				OutputStream outputStream = new FileOutputStream(filePath); // 建立一个上传文件的输出流

				int bytesRead = 0;
				byte[] buffer = new byte[8192];
				while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}
				outputStream.close();
				stream.close();
			} catch (Exception e) {
				e.printStackTrace();
				return mapping.findForward("failure");
			}

			// 然后把文件的每一条数据读入到form中
			Workbook workbook = null;
			ArrayList accessoryList = new ArrayList();
			try {
				// 构建Workbook对象, 只读Workbook对象
				// 直接从本地文件创建Workbook, 从输入流创建Workbook
				InputStream ins = new FileInputStream(filePath);
				workbook = Workbook.getWorkbook(ins);
				Sheet dataSheet = workbook.getSheet(0);

				// 读取数据
				for (int i = 1; i < dataSheet.getRows(); i++) {
					accessoryForm = new TawSystemUserForm();
					accessoryForm.setIsPartners("1");
					if (dataSheet.getCell(0, i).getContents() != null
							&& !""
									.equals(dataSheet.getCell(0, i)
											.getContents()))
						accessoryForm.setUsername(dataSheet.getCell(0, i)
								.getContents().trim());
					if (dataSheet.getCell(1, i).getContents() != null
							&& !""
									.equals(dataSheet.getCell(1, i)
											.getContents()))
						accessoryForm.setUserid(dataSheet.getCell(1, i)
								.getContents().trim());
					if (dataSheet.getCell(2, i).getContents() != null
							&& !""
									.equals(dataSheet.getCell(2, i)
											.getContents()))
						accessoryForm.setNewPassword(dataSheet.getCell(2, i)
								.getContents().trim());
					if (dataSheet.getCell(3, i).getContents() != null
							&& !""
									.equals(dataSheet.getCell(3, i)
											.getContents()))
						accessoryForm.setDeptid(dataSheet.getCell(3, i)
								.getContents().trim());
					if (dataSheet.getCell(4, i).getContents() != null
							&& !""
									.equals(dataSheet.getCell(4, i)
											.getContents()))
						accessoryForm.setDeptname(dataSheet.getCell(4, i)
								.getContents().trim());
					if (dataSheet.getCell(5, i).getContents() != null
							&& !""
									.equals(dataSheet.getCell(5, i)
											.getContents()))
						accessoryForm.setCptroomid(dataSheet.getCell(5, i)
								.getContents().trim());
					if (dataSheet.getCell(6, i).getContents() != null
							&& !""
									.equals(dataSheet.getCell(6, i)
											.getContents()))
						accessoryForm.setCptroomname(dataSheet.getCell(6, i)
								.getContents().trim());
					if (dataSheet.getCell(7, i).getContents() != null
							&& !""
									.equals(dataSheet.getCell(7, i)
											.getContents()))
						accessoryForm.setSex(dataSheet.getCell(7, i)
								.getContents().trim());
					if (dataSheet.getCell(8, i).getContents() != null
							&& !""
									.equals(dataSheet.getCell(8, i)
											.getContents()))
						accessoryForm.setPhotoInfo(dataSheet.getCell(8, i)
								.getContents().trim());
					if (dataSheet.getCell(9, i).getContents() != null
							&& !""
									.equals(dataSheet.getCell(9, i)
											.getContents()))
						accessoryForm.setBirthday(dataSheet.getCell(9, i)
								.getContents().trim());
					if (dataSheet.getCell(10, i).getContents() != null
							&& !"".equals(dataSheet.getCell(10, i)
									.getContents()))
						accessoryForm.setEducation(dataSheet.getCell(10, i)
								.getContents().trim());
					if (dataSheet.getCell(11, i).getContents() != null
							&& !"".equals(dataSheet.getCell(11, i)
									.getContents()))
						accessoryForm.setStartWorkTime(dataSheet.getCell(11, i)
								.getContents().trim());
					if (dataSheet.getCell(12, i).getContents() != null
							&& !"".equals(dataSheet.getCell(12, i)
									.getContents()))
						accessoryForm.setToDeptTime(dataSheet.getCell(12, i)
								.getContents().trim());
					if (dataSheet.getCell(13, i).getContents() != null
							&& !"".equals(dataSheet.getCell(13, i)
									.getContents()))
						accessoryForm.setCertificationInfo(dataSheet.getCell(
								13, i).getContents().trim());
					if (dataSheet.getCell(14, i).getContents() != null
							&& !"".equals(dataSheet.getCell(14, i)
									.getContents()))
						accessoryForm.setStaffLabel(dataSheet.getCell(14, i)
								.getContents().trim());
					if (dataSheet.getCell(15, i).getContents() != null
							&& !"".equals(dataSheet.getCell(15, i)
									.getContents()))
						accessoryForm.setFamilyaddress(dataSheet.getCell(15, i)
								.getContents().trim());
					if (dataSheet.getCell(16, i).getContents() != null
							&& !"".equals(dataSheet.getCell(16, i)
									.getContents()))
						accessoryForm.setPostState(dataSheet.getCell(16, i)
								.getContents().trim());
					if (dataSheet.getCell(17, i).getContents() != null
							&& !"".equals(dataSheet.getCell(17, i)
									.getContents()))
						accessoryForm.setMobile(dataSheet.getCell(17, i)
								.getContents().trim());
					if (dataSheet.getCell(18, i).getContents() != null
							&& !"".equals(dataSheet.getCell(18, i)
									.getContents()))
						accessoryForm.setEmail(dataSheet.getCell(18, i)
								.getContents().trim());
					if (dataSheet.getCell(19, i).getContents() != null
							&& !"".equals(dataSheet.getCell(19, i)
									.getContents()))
						accessoryForm.setRemark(dataSheet.getCell(19, i)
								.getContents().trim());

					accessoryList.add(accessoryForm);
				}

				// 保存该Form
				MessageResources mr = this.getResources(request);
				ITawSystemUserManager mgr = (ITawSystemUserManager) getBean("ItawSystemUserSaveManagerFlush");
				ITawPartnersUserManager pmgr = (ITawPartnersUserManager) getBean("itawPartnersUserManager");
				String isRight = "Y";

				for (int i = 0; i < accessoryList.size(); ++i) {
					accessoryForm = (TawSystemUserForm) accessoryList.get(i);
					// 验证用户id，字母开头，允许5-16字节，允许字母数字下划线
					if (!mgr.checkUserId(accessoryForm.getUserid())) {
						// String msg =
						// mr.getMessage("user.infomation.useridfailure");
						// JSONUtil.fail(response, msg);
						isRight = "N";
					}

					// 密码至少大于6位并数字加字母(至少一位大写字母）组合
					if (!mgr.checkPasswd(accessoryForm.getNewPassword())) {
						// String msg =
						// mr.getMessage("user.infomation.passwdfailure",
						// UserMgrLocator.getUserAttributes()
						// .getPasswdLength());
						// JSONUtil.fail(response, msg);
						isRight = "N";
					}
					// 若用户存在
					if (mgr.isUserExist(accessoryForm.getUserid())) {
						// String msg =
						// mr.getMessage("user.infomation.useridExist");
						// JSONUtil.fail(response, msg);
						isRight = "N";
					}
				}

				if (isRight.equals("Y")) {
					for (int i = 0; i < accessoryList.size(); ++i) {
						accessoryForm = (TawSystemUserForm) accessoryList
								.get(i);
						TawSystemSessionForm sessionform = (TawSystemSessionForm) request
								.getSession().getAttribute("sessionform");
						String operuserid = sessionform.getUserid();
						accessoryForm.setOperuserid(operuserid);

						TawSystemUser tawSystemUser = (TawSystemUser) convert(accessoryForm);
						// md5加密
						tawSystemUser.setPassword(new Md5PasswordEncoder()
								.encodePassword(accessoryForm.getNewPassword(),
										new String()));
						tawSystemUser.setDeleted("0");
						tawSystemUser.setOperremotip(request.getRemoteAddr());
						tawSystemUser.setSavetime(new Date());
						tawSystemUser.setEnabled(true);
						tawSystemUser.setAccountLocked(false);
						tawSystemUser.setId("");
						tawSystemUser
								.setDeptname(TawSystemDeptBo.getInstance()
										.getDeptnameBydeptid(
												tawSystemUser.getDeptid()));

						TawPartnersUser tawPartnersUser = new TawPartnersUser();

						tawPartnersUser.setInCompany(accessoryForm
								.getInCompany());
						tawPartnersUser
								.setBirthday(accessoryForm.getBirthday());
						tawPartnersUser.setCertificationInfo(accessoryForm
								.getCertificationInfo());
						tawPartnersUser.setEducation(accessoryForm
								.getEducation());
						tawPartnersUser.setGraduateSchool(accessoryForm
								.getGraduateSchool());
						tawPartnersUser
								.setIDNumber(accessoryForm.getIDNumber());
						tawPartnersUser.setInCity(accessoryForm.getInCity());
						tawPartnersUser.setInDistrict(accessoryForm
								.getInDistrict());
						tawPartnersUser.setIsMarried(accessoryForm
								.getIsMarried());
						tawPartnersUser.setJobType(accessoryForm.getJobType());
						tawPartnersUser.setPersonDiscription(accessoryForm
								.getPersonDiscription());
						tawPartnersUser.setPhotoInfo(accessoryForm
								.getPhotoInfo());
						tawPartnersUser.setPoliticalStatus(accessoryForm
								.getPoliticalStatus());
						tawPartnersUser.setPost(accessoryForm.getPost());
						tawPartnersUser.setPostState(accessoryForm
								.getPostState());
						tawPartnersUser.setSpecialties(accessoryForm
								.getSpecialties());
						tawPartnersUser.setStaffLabel(accessoryForm
								.getStaffLabel());

						tawPartnersUser.setStartWorkTime(accessoryForm
								.getStartWorkTime());
						tawPartnersUser.setToDeptTime(accessoryForm
								.getToDeptTime());

						tawPartnersUser.setUserid(tawSystemUser.getUserid());// 用这个字段与TawSystemDept表关联
						pmgr.saveTawPartnersUser(tawPartnersUser);

						mgr.saveTawSystemUser(tawSystemUser, accessoryForm
								.getOlduserid());
					}
					workbook.close();
					File fileDel = new File(filePath);
					if (fileDel.exists())
						fileDel.delete();
					return mapping.findForward("success");
				} else {
					workbook.close();
					File fileDel = new File(filePath);
					if (fileDel.exists())
						fileDel.delete();
					return mapping.findForward("failure");
				}
			} catch (Exception e) {
				workbook.close();
				File fileDel = new File(filePath);
				if (fileDel.exists())
					fileDel.delete();
				e.printStackTrace();
				return mapping.findForward("failure");
			} finally {
				workbook.close();
				File fileDel = new File(filePath);
				if (fileDel.exists())
					fileDel.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return mapping.findForward("failure");
		}
	}

	// 模板文件下载
	public ActionForward xmlOutPut(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String url = request.getParameter("url");
			url = StaticMethod.getWebPath() + url;
			File file = new File(url);
			// 读到流中
			InputStream inStream = new FileInputStream(file);// 文件的存放路径
			// 设置输出的格式
			response.reset();
			response.setContentType("application/x-msdownload;charset=GBK");
			response.setCharacterEncoding("UTF-8");
			String fileName = URLEncoder.encode(file.getName(), "UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ new String(fileName.getBytes("UTF-8"), "GBK"));

			// 循环取出流中的数据
			byte[] b = new byte[128];
			int len;
			while ((len = inStream.read(b)) > 0)
				response.getOutputStream().write(b, 0, len);
			inStream.close();
		} catch (Exception e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
			return mapping.findForward("failure");
		}
		return null;
	}
	
	//2009-6-22 ajax 调用 得到在线用户数
	public ActionForward showOnlineUserCounter(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		int count = UserCounterListener.getOnlineUserCounter();
		String aaa = String.valueOf(count);
		response.setContentType("text/html; charset=GBK");
		try {
			response.getWriter().print(aaa);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			response.getWriter().flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
