package com.boco.eoms.workbench.infopub.webapp.action;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.abdera.Abdera;
import org.apache.abdera.factory.Factory;
import org.apache.abdera.model.Entry;
import org.apache.abdera.model.Feed;
import org.apache.abdera.model.Person;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;

import com.boco.eoms.Bulletin.mgr.BulletinMgr;
import com.boco.eoms.base.Constants;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.ConvertUtil;
import com.boco.eoms.base.util.StaticVariable;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.common.util.StaticMethod;
import com.boco.eoms.commons.loging.BocoLog;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.dept.service.bo.TawSystemDeptBo;
import com.boco.eoms.commons.system.dict.util.DictMgrLocator;
import com.boco.eoms.commons.system.role.service.ITawSystemSubRoleManager;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.commons.system.user.service.ITawSystemUserManager;
import com.boco.eoms.commons.system.user.service.bo.impl.TawSystemUserRoleBo;
import com.boco.eoms.commons.ui.util.UIConstants;
import com.boco.eoms.message.mgr.IContentManager;
import com.boco.eoms.message.service.impl.MsgServiceImpl;
import com.boco.eoms.util.BulletinMgrLocator;
import com.boco.eoms.util.InterfaceUtil;
import com.boco.eoms.util.InterfaceUtilVariable;
import com.boco.eoms.workbench.infopub.mgr.IForumsManager;
import com.boco.eoms.workbench.infopub.mgr.IThreadAuditHistoryManager;
import com.boco.eoms.workbench.infopub.mgr.IThreadFormManager;
import com.boco.eoms.workbench.infopub.mgr.IThreadHistoryManager;
import com.boco.eoms.workbench.infopub.mgr.IThreadManager;
import com.boco.eoms.workbench.infopub.mgr.IThreadPermimissionOrgManager;
import com.boco.eoms.workbench.infopub.model.Forums;
import com.boco.eoms.workbench.infopub.model.Thread;
import com.boco.eoms.workbench.infopub.model.ThreadAuditHistory;
import com.boco.eoms.workbench.infopub.model.ThreadHistory;
import com.boco.eoms.workbench.infopub.model.ThreadPermimissionOrg;
import com.boco.eoms.workbench.infopub.util.InfopubConstants;
import com.boco.eoms.workbench.infopub.util.InfopubUtil;
import com.boco.eoms.workbench.infopub.util.Infopublocator;
import com.boco.eoms.workbench.infopub.webapp.form.ThreadForm;

/**
 * 
 * <p>
 * Title:信息发布中的信息
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Date:May 24, 2008 4:57:45 PM
 * </p>
 * 
 * @author 曲静波
 * @version 3.5.1
 * 
 */
public final class ThreadAction extends BaseAction {

	/**
	 * 转向某版块的贴子列表
	 * 
	 * @param mapping
	 * @param threadForm
	 * @return
	 */
	private ActionForward forwardList4forumId(ActionMapping mapping,
			ThreadForm threadForm) {
		ActionForward forward = mapping.findForward("forwardlist");
		String path = forward.getPath() + "&forumsId="
				+ threadForm.getForumsId();// 加参数
		return new ActionForward(path, false);
	}

	/**
	 * 删除信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		if (log.isDebugEnabled()) {
			log.debug("Entering 'delete' method");
		}

		// ActionMessages messages = new ActionMessages();
		ThreadForm threadForm = (ThreadForm) form;

		// 既没信息管理员权限又不是本人创建的信息
		if (!hasPermission(
				InfopubConstants.WORKBENCH_INFOPUB_PERMISSION_THREAD_MGR,
				request)
				&& !this.getUserId(request).equals(threadForm.getCreaterId())) {
			return mapping.findForward("nopriv");
		}
		// Exceptions are caught by ActionExceptionHandler
		IThreadManager mgr = (IThreadManager) getBean("IthreadManager");
		// Thread thread = (Thread) convert(threadForm);
		mgr.removeThread(threadForm.getId());

		// messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
		// "thread.deleted"));

		// save messages in session, so they'll survive the redirect
		// saveMessages(request.getSession(), messages);

		return forwardList4forumId(mapping, threadForm);
	}

	/**
	 * 查看某版块未读信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listUnread(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Date currentTime = new Date();
		// 版块id
		String forumsId = request.getParameter("forumsId");
		// 版块id
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				InfopubConstants.THREAD_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		// 每页显示条数
		Integer pageSize = UtilMgrLocator.getEOMSAttributes().getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		IThreadManager mgr = (IThreadManager) getBean("IthreadManager");
		Map map = null;
		String deptStr = null;
		List parentDepts = null;
		// 若拥有信息管理员的权限，则可以查询所有部门（范围）
		if (this.hasPermission(
				InfopubConstants.WORKBENCH_INFOPUB_PERMISSION_THREAD_MGR,
				request)) {
			request.setAttribute("threadMgrPriv", "threadMgrPriv");
		} else {
			deptStr = getUser(request).getDeptid();// 2009-04-08
		}

		// --- 排序及导出 ---
		String order = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								InfopubConstants.THREAD_LIST)
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_ORDER)));
		String sort = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								InfopubConstants.THREAD_LIST)
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_SORT)));
		String whereStr = "";
		if (!order.equals("")) {
			if (order.equals("1")) {
				order = " asc";
			} else {
				order = " desc";
			}
		}
		String exportType = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								InfopubConstants.THREAD_LIST)
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_EXPORTTYPE)));
		if (!exportType.equals("")) {
			pageSize = new Integer(-1);
		}

		if (UIConstants.ROOT_NODE.equals(forumsId) || null == forumsId
				|| "".equals(forumsId.trim())) {
			whereStr = getPublishRangeQueryStr(deptStr, getUser(request)
					.getUserid());
			if (!sort.equals("")) {
				whereStr += " order by thread." + sort + order;
			} else {
				whereStr += " order by thread.threadTypeId,thread.editTime desc";
			}

			// 取所有版块未读记录
			map = (Map) mgr.getUnreadThreads(pageIndex, pageSize, this
					.getUserId(request), whereStr);
		} else {
			whereStr = " and (thread.forumsId='"
					+ forumsId
					+ "')"
					+ getPublishRangeQueryStr(deptStr, getUser(request)
							.getUserid());
			if (!sort.equals("")) {
				whereStr += " order by thread." + sort + order;
			} else {
				whereStr += " order by thread.threadTypeId,thread.editTime desc";
			}
			// 取某版块未阅读记录列表
			map = (Map) mgr.getUnreadThreads(pageIndex, pageSize, this
					.getUserId(request), whereStr);
		}

		List list = (List) map.get("result");
		request.setAttribute(InfopubConstants.THREAD_LIST, list);
		String resultSize = "0";
		if (list.size() != 0) {
			resultSize = map.get("total") + "";
		}
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("threadUnreadList");
	}

	/**
	 * 转向新增、修改页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'edit' method");
		}

		ThreadForm threadForm = (ThreadForm) form;
		String forumsId = StaticMethod.null2String(request
				.getParameter("forumsId"));

		// if an id is passed in, look up the user - otherwise
		// don't do anything - user is doing an add
		// 修改
		if (threadForm.getId() != null) {
			IThreadManager mgr = (IThreadManager) getBean("IthreadManager");
			Thread thread = (Thread) convert(threadForm);
			thread = (Thread) mgr.getThread(thread.getId());
			// thread.setReply(thread.getReply().trim());
			// 既没信息管理员权限又不是本人创建的信息
			if (!hasPermission(
					InfopubConstants.WORKBENCH_INFOPUB_PERMISSION_THREAD_MGR,
					request)
					&& !this.getUserId(request).equals(thread.getCreaterId())) {
				return mapping.findForward("nopriv");
			}

			// 若本人想编辑该贴，并该贴已审核通过则不可修改
			if (this.getUserId(request).equals(thread.getCreaterId())
					&& InfopubConstants.AUDIT_PASS.equals(thread.getStatus())) {
				// 已经审核通过，不可修改
				ActionMessages messages = new ActionMessages();
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"threadForm.tips.noEditWithOwner"));
				saveMessages(request, messages);
				return mapping.findForward("fail");
			}

			// 若本人想编辑该贴，并该贴正在审核中则不可修改
			if (this.getUserId(request).equals(thread.getCreaterId())
					&& InfopubConstants.ADUITING.equals(thread.getStatus())) {
				// 已经审核通过，不可修改
				ActionMessages messages = new ActionMessages();
				messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
						"threadForm.tips.noEdit4Auditing"));
				saveMessages(request, messages);
				return mapping.findForward("fail");
			}

			threadForm = (ThreadForm) convert(thread);
			updateFormBean(mapping, request, threadForm);
		}

		IThreadPermimissionOrgManager orgMgr = (IThreadPermimissionOrgManager) getBean("IthreadPermimissionOrgManager");
		List pOrgs = orgMgr.getThreadPermissionOrgsByThreadId(threadForm
				.getId());
		IThreadAuditHistoryManager auditMgr = (IThreadAuditHistoryManager) getBean("IthreadAuditHistoryManager");
		ThreadAuditHistory Audit = auditMgr
				.getCurrentThreadAuditHistory(threadForm.getId());
		// 信息权限列表转换成json的字符串
		String jsonOrgs = threadPermissionOrgs2jsonStr(pOrgs);
		String jsonAudit = threadAuditHistoryAuditer2jsonStr(Audit);
		request.setAttribute("jsonOrgs", jsonOrgs);
		request.setAttribute("jsonAudit", jsonAudit);

		String defaultId = Infopublocator.InfopubUtilInstance()
				.getDefaultForums();
		if (forumsId.equals(defaultId)) {
			return mapping.findForward("netEdit");
		}
		return mapping.findForward("edit");
	}
	
	/**
	 * 接口过来初始化新增界面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initThreadForm(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'initThreadForm' method");
		}

		ThreadForm threadForm = (ThreadForm) form;
		
		String manager = StaticMethod.null2String(request.getParameter("mgrName"));
		IThreadFormManager tMgr = (IThreadFormManager) getBean(manager);
		
		threadForm = tMgr.getThreadForm(request);
		updateFormBean(mapping, request, threadForm);
		String jsonOrgs = "[]";
		String jsonAudit = "[]";
		request.setAttribute("jsonOrgs", jsonOrgs);
		request.setAttribute("jsonAudit", jsonAudit);
		return mapping.findForward("edit");
	}

	/**
	 * 信息权限列表转换成json的字符串
	 * 
	 * @param threadPermimissionOrgs
	 *            信息权限列表
	 * @return 信息权限转换后的字符串
	 */
	private String threadPermissionOrgs2jsonStr(List threadPermimissionOrgs) {
		String jsonOrgs = "[]";
		// 构造信息发布范围json对象
		if (null != threadPermimissionOrgs && !threadPermimissionOrgs.isEmpty()) {
			JSONArray jsonRoot = new JSONArray();
			for (Iterator it = threadPermimissionOrgs.iterator(); it.hasNext();) {
				ThreadPermimissionOrg org = (ThreadPermimissionOrg) it.next();
				JSONObject item = new JSONObject();
				// 构造json对象
				item.put(UIConstants.JSON_ID, org.getOrgId());
				item.put(UIConstants.JSON_NAME, org.getName());
				// 判断发布对象类型
				int orgtype = StaticMethod.null2int(org.getOrgType());
				String orgtypestr = "";
				switch (orgtype) {
				case 1:
					orgtypestr = "dept";
					break;
				case 2:
					orgtypestr = "user";
					break;
				}
				item.put(UIConstants.JSON_NODETYPE, orgtypestr);
				jsonRoot.put(item);
			}
			jsonOrgs = jsonRoot.toString();
		}
		return jsonOrgs;
	}

	/**
	 * 信息权限列表转换成json的字符串
	 * 
	 * @param threadPermimissionOrgs
	 *            信息权限列表
	 * @return 信息权限转换后的字符串
	 */
	private String threadAuditHistoryAuditer2jsonStr(ThreadAuditHistory Audit) {
		String jsonOrgs = "[]";
		// 构造信息发布范围json对象
		if (null != Audit) {
			JSONArray jsonRoot = new JSONArray();
			JSONObject item = new JSONObject();
			// 构造json对象
			item.put(UIConstants.JSON_ID, Audit.getOrgId());
			if (!this.getOrgName(Audit.getOrgId(), Audit.getOrgType()).equals(
					"")) {
				item.put(UIConstants.JSON_NAME, this.getOrgName(Audit
						.getOrgId(), Audit.getOrgType()));
			}
			item.put(UIConstants.JSON_NODETYPE, Audit.getOrgType());
			jsonRoot.put(item);
			jsonOrgs = jsonRoot.toString();
		}
		return jsonOrgs;
	}

	/**
	 * 组织名称
	 * 
	 * @return 组织名称
	 */
	public String getOrgName(String orgId, String orgType) {
		String orgName = "";
		// 若为角色则显示角色名称
		if (StaticVariable.PRIV_ASSIGNTYPE_ROLE.equals(orgType)) {
			ITawSystemSubRoleManager roleMgr = (ITawSystemSubRoleManager) ApplicationContextHolder
					.getInstance().getBean("ItawSystemSubRoleManager");
			return roleMgr.getTawSystemSubRole(orgId).getSubRoleName();

		}
		// 若为用户则显示用户名称
		else if (StaticVariable.PRIV_ASSIGNTYPE_USER.equals(orgType)) {
			ITawSystemUserManager userMgr = (ITawSystemUserManager) ApplicationContextHolder
					.getInstance().getBean("itawSystemUserManager");
			return userMgr.getUserByuserid(orgId).getUsername();
		}
		return orgName;

	}

	/**
	 * 详细信息页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward detail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'detail' method");
		}
		ThreadForm threadForm = (ThreadForm) form;
		IThreadManager mgr = (IThreadManager) getBean("IthreadManager");
		String id = StaticMethod.null2String(threadForm.getId());
		if (id.equals("")) {
			id = StaticMethod.null2String(request.getParameter("id"));
		}
		// 根据主键取详细信息
		Thread thread = mgr.getThread(threadForm.getId());
		request.setAttribute("forumsId", thread.getForumsId());
		// 若无此贴子则转向失败页面，不记录查看记录历史
		boolean flag = false;
		boolean flag1 = false;
		if (thread == null || thread.getId() == null) {
			return mapping.findForward("fail");
		}
		String noValidity = StaticMethod.null2String(thread.getNoValidity());
		if (noValidity.equals("1")) {
			flag1 = true;

		} else {
			DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
			String validityDate = StaticMethod.null2String(thread
					.getValidityDate());
			String nowDate = StaticMethod.getCurrentDateTime();
			if (validityDate != null && !validityDate.equals("")) {
				Date date1 = f.parse(validityDate);
				Date date2 = f.parse(nowDate);
				flag = date1.after(date2);

			}

		}

		threadForm = (ThreadForm) convert(thread);
		request.setAttribute("status", threadForm.getStatus());
		request.setAttribute("threadForm", threadForm);

		// 仅审核通过，正式发布记入查看信息历史
		if (InfopubConstants.AUDIT_PASS.equals(thread.getStatus())
				|| InfopubConstants.NO_AUDIT.equals(thread.getStatus())
				|| InfopubConstants.ROTATION.equals(thread.getStatus())) {

			// 信息查看历史对象
			ThreadHistory history = new ThreadHistory();
			// 阅读者ip
			history.setIp(this.getUser(request).getRomteaddr());
			// 阅读时间
			history.setReadTime(new Date());
			// 回复人部门
			// ITawSystemUserManager
			// userMgr=(ITawSystemUserManager)getBean("itawSystemUserManager");
			// TawSystemUser
			// tawSystemUser=userMgr.getTawSystemUserByuserid(this.getUserId(request));
			// String userDept=tawSystemUser.getDeptname();
			// history.setUserDept(userDept);
			// //回复附件
			// String replyAccessories="";
			// history.setReplyAccessories(replyAccessories);
			// 阅读贴子
			history.setThreadId(id);
			// 阅读人id
			history.setUserId(this.getUserId(request));
			// 保存访问历史记录并新增访问次数
			if (flag || flag1)
				mgr.saveThreadHistory(thread, history);
		}
		IThreadPermimissionOrgManager orgMgr = (IThreadPermimissionOrgManager) getBean("IthreadPermimissionOrgManager");
		List pOrgs = orgMgr.getThreadPermissionOrgsByThreadId(id);
		IThreadAuditHistoryManager auditMgr = (IThreadAuditHistoryManager) getBean("IthreadAuditHistoryManager");
		ThreadAuditHistory Audit = auditMgr.getCurrentThreadAuditHistory(id);
		// 信息权限列表转换成json的字符串
		String jsonOrgs = threadPermissionOrgs2jsonStr(pOrgs);
		String jsonAudit = threadAuditHistoryAuditer2jsonStr(Audit);
		request.setAttribute("jsonOrgs", jsonOrgs);
		request.setAttribute("jsonAudit", jsonAudit);

		String pageIndexNameCount = new org.displaytag.util.ParamEncoder(
				InfopubConstants.THREADCOUNTHISTORY_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSizeCount = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndexCount = new Integer(
				GenericValidator.isBlankOrNull(request
						.getParameter(pageIndexNameCount)) ? 0
						: (Integer.parseInt(request
								.getParameter(pageIndexNameCount)) - 1));
		IThreadHistoryManager mgrHis = (IThreadHistoryManager) getBean("IthreadHistoryManager");
		Map mapCount = (Map) mgrHis.getThreadCountHistory(pageIndexCount,
				pageSizeCount, " where threadId='" + id + "'");
		List listCount = (List) mapCount.get("result");
		request.setAttribute(InfopubConstants.THREADCOUNTHISTORY_LIST,
				listCount);
		request.setAttribute("resultSizeCount", mapCount.get("total"));
		request.setAttribute("pageSizeCount", pageSizeCount);

		String pageIndexNameComments = new org.displaytag.util.ParamEncoder(
				InfopubConstants.THREADCOMMENTSHISTORY_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSizeComments = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndexComments = new Integer(
				GenericValidator.isBlankOrNull(request
						.getParameter(pageIndexNameComments)) ? 0
						: (Integer.parseInt(request
								.getParameter(pageIndexNameComments)) - 1));
		IThreadHistoryManager mgrComments = (IThreadHistoryManager) getBean("IthreadHistoryManager");
		Map mapComments = (Map) mgrComments.getThreadHistorys(
				pageIndexComments, pageSizeComments, " where threadId='" + id
						+ "'" + " and historyType='1'");
		List listComments = (List) mapComments.get("result");
		request.setAttribute(InfopubConstants.THREADCOMMENTSHISTORY_LIST,
				listComments);

		request.setAttribute("resultSizeComments", mapComments.get("total"));
		request.setAttribute("pageSizeComments", pageSizeComments);
		if (flag1) {
			return mapping.findForward("detail");
		}
		if (!flag) {
			return mapping.findForward("Vdetail");
		}
		return mapping.findForward("detail");
	}

	/**
	 * 新增或修改
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		if (log.isDebugEnabled()) {
			log.debug("Entering 'save' method");
		}
		IContentManager cMgr = (IContentManager) ApplicationContextHolder
				.getInstance().getBean("IContentManager");
		ITawSystemUserManager uMgr = (ITawSystemUserManager) ApplicationContextHolder
				.getInstance().getBean("itawSystemUserManager");
		SimpleDateFormat dateformat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Map infoMap = new HashMap();
		String url = "";
		String forumsId = (String) DictMgrLocator.getDictService().itemId2name(
				"dict-infopubInterFace" + "#" + "threadInterFace", "1");
		System.out.println("id1================" + forumsId);
		String forumsIdTwo = (String) DictMgrLocator.getDictService()
				.itemId2name("dict-infopubInterFace" + "#" + "threadInterFace",
						"2");
		System.out.println("id2=================" + forumsIdTwo);
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		ThreadForm threadForm = (ThreadForm) form;
		boolean isNew = ("".equals(threadForm.getId()) || threadForm.getId() == null);

		IThreadManager mgr = (IThreadManager) getBean("IthreadManager");
		Thread thread = (Thread) convert(threadForm);

		// add success messages
		// 若为新建
		if (isNew) {
			// 新建则加入创建时间
			thread.setCreateTime(new Date());
			// 最后编辑时间隔
			thread.setEditTime(new Date());
			// 设置发布人id
			thread.setCreaterId(this.getUserId(request));
			thread.setCreaterName(this.getUser(request).getUsername());
			// 设置非删除标记
			thread.setIsDel(Constants.NOT_DELETED_FLAG);
			// 初始化阅读数为0
			thread.setThreadCount(new Integer(0));

		} else {

			// 既没信息管理员权限又不是本人创建的信息
			if (!hasPermission(
					InfopubConstants.WORKBENCH_INFOPUB_PERMISSION_THREAD_MGR,
					request)
					&& !this.getUserId(request).equals(
							threadForm.getCreaterId())) {
				return mapping.findForward("nopriv");
			}

			// 最后编辑时间隔
			thread.setEditTime(new Date());
		}
		// 保存ThreadPermimissionOrg列表
		String createrDeptName = uMgr.getTawSystemUserByuserid(
				thread.getCreaterId()).getDeptname();
		infoMap.put("createrName", thread.getCreaterName());
		infoMap.put("createTime", dateformat.format(thread.getCreateTime()));
		infoMap.put("createrDeptName", createrDeptName);
		infoMap.put("title", thread.getTitle());
		infoMap.put("suffix", "请您审核。");
		String sendContent = cMgr.getSendContent(infoMap);
		List orgList = new ArrayList();
		MsgServiceImpl msgService = new MsgServiceImpl();
		// 未选发布范围，则取默认发布范围
		System.out.println("threadForm.getOrg() = " + threadForm.getOrg());
		System.out.println("threadForm.getAuditUser() = "
				+ threadForm.getAuditUser());
		if (null == threadForm.getOrg() || "".equals(threadForm.getOrg())) {
			orgList.add(new ThreadPermimissionOrg(getUser(request).getDeptid(),
					StaticVariable.PRIV_ASSIGNTYPE_DEPT, getUser(request)
							.getDeptname()));
			if (threadForm.getIsSubmitAudit().equals("0")) {// 保存草稿
				thread.setStatus(InfopubConstants.AUDIT_WITHOUT_SUBMIT);
				mgr.saveThread(thread, orgList);
				List audit = this.jsonOrg2Audit(threadForm.getAuditUser(),
						thread.getId(), thread.getStatus());
				for (int i = 0; i < audit.size(); i++) {
					ThreadAuditHistory threadAuditHistory = (ThreadAuditHistory) audit
							.get(i);
					mgr.saveThreadAuditHistoryDraft(threadAuditHistory);
				}
			} else if (threadForm.getIsSubmitAudit().equals("1")) {// 直接发布

				BulletinMgr bullMgr = (BulletinMgr) getBean(BulletinMgrLocator
						.getAttributes().getCallMethod());
				thread.setStatus(InfopubConstants.NO_AUDIT);
				InterfaceUtil interfaceUtil = new InterfaceUtil();
				String serialNo = interfaceUtil.getSerialNo();
				thread.setSerialNo(serialNo);
				mgr.saveThread(thread, orgList);
				/* 以下为调用华为公告接口 */

				if (thread.getForumsId().equals(forumsId)
						|| thread.getForumsId().equals(forumsIdTwo)) {

					if (thread.getForumsId().equals(forumsId)) {
						url = (String) DictMgrLocator.getDictService()
								.itemId2description(
										"dict-infopubInterFace" + "#"
												+ "threadUrl", "1");

					} else {
						url = (String) DictMgrLocator.getDictService()
								.itemId2description(
										"dict-infopubInterFace" + "#"
												+ "threadUrl", "2");
					}
					try {
						System.out.println("crm=================>!!!!!!!!!");
						String opPerson = sessionform.getUsername();
						String opCorp = sessionform.getDeptname();
						String opDepart = sessionform.getDeptname();
						System.out.println("crm:serialNo!!!!!!!!!" + serialNo);
						System.out
								.println("crm=================>end!!!!!!!!!"
										+ bullMgr
												.newBulletin(
														new Integer(031),
														new Integer(999),
														serialNo,
														BulletinMgrLocator
																.getAttributes()
																.getBulletinSerSupplier(),
														BulletinMgrLocator
																.getAttributes()
																.getBulletinSerCaller(),
														"",
														StaticMethod
																.getLocalString(),
														"",
														opPerson,
														opCorp,
														opDepart,
														"",
														StaticMethod
																.getLocalString(),
														interfaceUtil
																.getOpDetail(thread),
														url));
					} catch (Exception e) {
						e.printStackTrace();

					}
				}
				/* 以上为调用华为公告接口* 只是在本机测试 */

			} else {// 保存并提交审核
				mgr.saveThread(thread, orgList);
				List audit = this.jsonOrg2Audit(threadForm.getAuditUser(),
						thread.getId(), thread.getStatus());
				for (int i = 0; i < audit.size(); i++) {
					ThreadAuditHistory threadAuditHistory = (ThreadAuditHistory) audit
							.get(i);
					mgr.saveThreadAuditHistoryAudit(threadAuditHistory);
				}

				if (threadForm.getIsAuditSend() != null
						&& threadForm.getIsAuditSend().equals("1")) {
					String time = StaticMethod
							.getCurrentDateTime("yyyy-MM-dd hh:mm:ss");
					String serviceId = InfopubUtil.newInfopub;
					String dd = ((ThreadAuditHistory) audit.get(0)).getOrgId();
					msgService.sendMsg(serviceId, sendContent, thread.getId(),
							"1," + dd, time);

				}
			}
			return forwardList4forumId(mapping, threadForm);
		} else {
			// 选择发布范围
			// 需要判断权限
			// json串转为org列表
			orgList = jsonOrg2Orgs(threadForm.getOrg());
			/*
			 * // 若为修改不需要修改状态 if (isNew) { // 判断是否拥有发送范围无限制的权限，没有则验证有没有超出其发布范围
			 * if (!hasPermission(
			 * InfopubConstants.WORKBENCH_INFOPUB_PERMISSION_PUB_ALL, request)) {
			 * List depts = DeptMgrLocator.getTawSystemDeptManager()
			 * .getSubDepts(getUser(request).getDeptid()); //
			 * 判断是否超出其部门范围，不在范围则无权 if (!inRange(orgList, depts)) { //
			 * 超出发布范围（需要审核），但未提交审核，一般为草稿
			 * thread.setStatus(InfopubConstants.AUDIT_WITHOUT_SUBMIT);
			 * mgr.saveThread(thread, orgList); // 转向审核页面 ActionForward forward =
			 * mapping .findForward("forwardAuditHistory"); String path =
			 * forward.getPath() + "&threadId=" + thread.getId();// 加参数 return
			 * new ActionForward(path, false); } } // 设为审核通过，不需审核
			 * thread.setStatus(InfopubConstants.NO_AUDIT); }
			 */
			if (threadForm.getIsSubmitAudit().equals("0")) {
				//草稿
				thread.setStatus("2");
				mgr.saveThread(thread, orgList);
				System.out.println("threadForm.getAuditUser(): " + threadForm.getAuditUser());
				System.out.println("threadForm.getIsAudit()" + threadForm.getIsAudit());
				String auditUser = threadForm.getAuditUser();
				if(threadForm.getAuditUser() == null || threadForm.getAuditUser().equals("")){
					
				}else{
					List audit = this.jsonOrg2Audit(threadForm.getAuditUser(),
							thread.getId(), thread.getStatus());
					for (int i = 0; i < audit.size(); i++) {
						ThreadAuditHistory threadAuditHistory = (ThreadAuditHistory) audit
								.get(i);
						mgr.saveThreadAuditHistoryDraft(threadAuditHistory);
					}
				}
			} else if (threadForm.getIsSubmitAudit().equals("1")) {
				System.out
						.println("crm:newBulletin=================>!!!!!!!!!");

				InterfaceUtil interfaceUtil = new InterfaceUtil();
				String serialNo = interfaceUtil.getSerialNo();
				thread.setStatus(InfopubConstants.NO_AUDIT);
				thread.setSerialNo(serialNo);
				mgr.saveThread(thread, orgList);
				/* 以下为调用华为公告接口 */
				if (thread.getForumsId().equals(forumsId)
						|| thread.getForumsId().equals(forumsIdTwo)) {
					BulletinMgr bullMgr = null;
					if (thread.getForumsId().equals(forumsId)) {
						url = (String) DictMgrLocator.getDictService()
								.itemId2description(
										"dict-infopubInterFace" + "#"
												+ "threadUrl", "1");

					} else {
						url = (String) DictMgrLocator.getDictService()
								.itemId2description(
										"dict-infopubInterFace" + "#"
												+ "threadUrl", "2");
					}
					try {
						String opPerson = sessionform.getUsername();
						String opCorp = sessionform.getDeptname();
						String opDepart = sessionform.getDeptname();
						String serSupplier = BulletinMgrLocator.getAttributes()
								.getBulletinSerSupplier();
						String SerCaller = BulletinMgrLocator.getAttributes()
								.getBulletinSerCaller();
						System.out.println("serialNo:===" + serialNo);
						System.out
								.println("crm:newBulletin=================>end!!!!!!!!!"
										+ bullMgr.newBulletin(new Integer(031),
												new Integer(999), serialNo,
												serSupplier, SerCaller, "",
												StaticMethod.getLocalString(),
												"", opPerson, opCorp, opDepart,
												"", StaticMethod
														.getLocalString(),
												interfaceUtil
														.getOpDetail(thread),
												url));

					} catch (Exception e) {
						e.printStackTrace();

					}
				}
				/* 以上为调用华为公告接口* 只是在本机测试 */

			} else if (threadForm.getIsSubmitAudit().equals("2")) {
				mgr.saveThread(thread, orgList);
				List audit = this.jsonOrg2Audit(threadForm.getAuditUser(),
						thread.getId(), thread.getStatus());
				for (int i = 0; i < audit.size(); i++) {
					ThreadAuditHistory threadAuditHistory = (ThreadAuditHistory) audit
							.get(i);
					mgr.saveThreadAuditHistoryAudit(threadAuditHistory);
				}
				if (threadForm.getIsAuditSend() != null
						&& threadForm.getIsAuditSend().equals("1")) {
					String time = StaticMethod
							.getCurrentDateTime("yyyy-MM-dd hh:mm:ss");
					String serviceId = InfopubUtil.newInfopub;
					String dd = ((ThreadAuditHistory) audit.get(0)).getOrgId();
					msgService.sendMsg(serviceId, sendContent, thread.getId(),
							"1," + dd, time);
				}
			} else {
				thread.setStatus(threadForm.getStatus());
				mgr.saveThread(thread, orgList);

			}
		}

		return mapping.findForward("success");
		// return forwardList4forumId(mapping, threadForm);

	}

	/**
	 * 传阅&评阅
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward rotationRead(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'toAudit' method");
		}
		ThreadForm threadForm = (ThreadForm) form;
		String url="";
		String forumsId = (String) DictMgrLocator.getDictService().itemId2name(
				"dict-infopubInterFace" + "#" + "threadInterFace", "1");
		System.out.println("id1================" + forumsId);
		String forumsIdTwo = (String) DictMgrLocator.getDictService()
				.itemId2name("dict-infopubInterFace" + "#" + "threadInterFace",
						"2");
		System.out.println("id2=================" + forumsIdTwo);

		if (!StaticMethod.null2String(threadForm.getHistoryType()).equals("1")) {
			IThreadManager mgr = (IThreadManager) getBean("IthreadManager");
			Thread thread1 = mgr.getThread(threadForm.getId());

			List readerList = jsonOrg2Orgs(threadForm.getReader());
			Thread thread = new Thread();

			ConvertUtil.populateObject(thread, thread1);
			thread.setId("");
			thread.setThreadCount(Integer.valueOf("0"));
			thread.setStatus(InfopubConstants.ROTATION);
			mgr.saveThread(thread, readerList);
		} else {
			IThreadHistoryManager historyMgr = (IThreadHistoryManager) getBean("IthreadHistoryManager");
			ThreadHistory threadHistory = new ThreadHistory();
			threadHistory.setIp(this.getUser(request).getRomteaddr());
			// 阅读时间
			threadHistory.setReadTime(new Date());
			// 阅读贴子
			threadHistory.setThreadId(threadForm.getId());
			// 回复人部门
			ITawSystemUserManager userMgr = (ITawSystemUserManager) getBean("itawSystemUserManager");
			TawSystemUser tawSystemUser = userMgr.getTawSystemUserByuserid(this
					.getUserId(request));
			String userDept = tawSystemUser.getDeptname();
			threadHistory.setUserDept(userDept);
			// 阅读人id
			threadHistory.setUserId(this.getUserId(request));
			// 阅读类型（一般浏览还是评阅）
			threadHistory.setHistoryType(threadForm.getHistoryType());
			// 评阅内容
			threadHistory.setComments(threadForm.getComments());
			// 回复结果
			threadHistory.setReplyresult(threadForm.getReplyresult());
			// 附件
			threadHistory.setReplyAccessories(threadForm.getAccessories());
			// 增加评阅信息，但不增加阅读次数（评阅人已经浏览过信息）
			historyMgr.saveThreadHistory(threadHistory);

			try {
				IThreadManager msg = (IThreadManager) ApplicationContextHolder
						.getInstance().getBean("IthreadManager");
				Thread thread = new Thread();
				thread = msg.getThread(threadHistory.getThreadId());
				if (thread.getForumsId().equals(forumsId)
						|| thread.getForumsId().equals(forumsIdTwo)) {

					System.out
							.println("crm:confirmBulletin=================>!!!!!!!!!");
					BulletinMgr bullMgr=null;
					if (thread.getForumsId().equals(forumsId)) {
						url = (String) DictMgrLocator.getDictService()
								.itemId2description(
										"dict-infopubInterFace" + "#"
												+ "threadUrl", "1");

					} else {
						url = (String) DictMgrLocator.getDictService()
								.itemId2description(
										"dict-infopubInterFace" + "#"
												+ "threadUrl", "2");
					}
					InterfaceUtil interfaceUtil = new InterfaceUtil();
					System.out
							.println("crm:confirmBulletinend=================>!!!!!!!!!"
									+ bullMgr
											.confirmBulletin(
													new Integer(031),
													new Integer(999),
													thread.getSerialNo(),
													BulletinMgrLocator
															.getAttributes()
															.getBulletinSerSupplier(),
													BulletinMgrLocator
															.getAttributes()
															.getBulletinSerCaller(),
													StaticMethod
															.getLocalString(),
													"",
													InterfaceUtilVariable.BULLETION_CREATER_ID,
													"",
													"",
													"",
													"",
													"",
													interfaceUtil
															.getOpDetail(threadHistory),url));

				}
			} catch (Exception e) {
				e.printStackTrace();

			}
		}

		return mapping.findForward("success");
		// return forwardList4forumId(mapping, threadForm);
	}

	/**
	 * 判断要发布的部门范围是否在发布范围内
	 * 
	 * @param orgs
	 *            要发布的部门范围
	 * @param ranges
	 *            用户可发布的部门范围
	 * @return 可发布否
	 */
	private boolean inRange(List orgs, List ranges) {
		if (orgs == null || ranges == null) {
			return false;
		}
		for (Iterator it = orgs.iterator(); it.hasNext();) {
			ThreadPermimissionOrg org = (ThreadPermimissionOrg) it.next();
			if (!inRange(org, ranges)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断某一个发布部门是否在范围中
	 * 
	 * @param org
	 *            要发布的一个部门范围
	 * @param ranges
	 *            用户可发布的部门范围
	 * @return 某一部门是否在范围中
	 */
	private boolean inRange(ThreadPermimissionOrg org, List ranges) {
		// 若要发布的部门不在发布范围中则返回false
		for (Iterator it1 = ranges.iterator(); it1.hasNext();) {
			TawSystemDept dept = (TawSystemDept) it1.next();
			if (org.getOrgId() != null && dept.getDeptId() != null) {
				// 若选择的范围只要在可发布的范围内就继续验证下一个范围，否则超出发布范围
				if (org.getOrgId().equals(dept.getDeptId())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 从json中取发布组织列表
	 * 
	 * @param orgs
	 *            json串
	 * @return 部门列表
	 */
	private List jsonOrg2Orgs(String orgs) {
		JSONArray jsonOrgs = JSONArray.fromString(orgs);
		List orgList = new ArrayList();
		for (Iterator it = jsonOrgs.iterator(); it.hasNext();) {
			JSONObject org = (JSONObject) it.next();
			// 发布组织id
			String id = org.getString(UIConstants.JSON_ID);
			// 发布组织名称
			String name = org.getString(UIConstants.JSON_NAME);
			// 节点类型
			String nodeType = org.getString(UIConstants.JSON_NODETYPE);
			// 限制发布范围
			if (InfopubConstants.ORG_DEPT.equals(nodeType)
					|| StaticVariable.PRIV_ASSIGNTYPE_DEPT.equals(nodeType)) {
				// 写入orgList，供保存
				orgList.add(new ThreadPermimissionOrg(id,
						StaticVariable.PRIV_ASSIGNTYPE_DEPT, name));
			} else if (InfopubConstants.ORG_ROLE.equals(nodeType)
					|| StaticVariable.PRIV_ASSIGNTYPE_ROLE.equals(nodeType)) {
				orgList.add(new ThreadPermimissionOrg(id,
						StaticVariable.PRIV_ASSIGNTYPE_ROLE, name));
			} else if (InfopubConstants.ORG_USER.equals(nodeType)
					|| StaticVariable.PRIV_ASSIGNTYPE_USER.equals(nodeType)) {
				orgList.add(new ThreadPermimissionOrg(id,
						StaticVariable.PRIV_ASSIGNTYPE_USER, name));
			}
		}
		return orgList;
	}

	/**
	 * 从json中取审批组织
	 * 
	 * @param auditers
	 *            json串
	 * @param threadId
	 *            信息ID
	 * @return 审批组织列表
	 */
	private List jsonOrg2Audit(String auditers, final String threadId,
			String stauts) {
		JSONArray jsonAudit = JSONArray.fromString(auditers);
		List auditList = new ArrayList();
		for (Iterator it = jsonAudit.iterator(); it.hasNext();) {
			JSONObject audit = (JSONObject) it.next();
			// 审核组织id
			String orgId = audit.getString(UIConstants.JSON_ID);
			// 节点类型
			String nodeType = audit.getString(UIConstants.JSON_NODETYPE);
			//
			// 写入orgList，供保存
			ThreadAuditHistory threadAuditHistory = new ThreadAuditHistory();
			if (stauts == null
					|| stauts.equals(InfopubConstants.AUDIT_WITHOUT_SUBMIT)) {
				IThreadAuditHistoryManager auditMgr = (IThreadAuditHistoryManager) getBean("IthreadAuditHistoryManager");
				threadAuditHistory = auditMgr
						.getCurrentThreadAuditHistory(threadId);
			}
			if (InfopubConstants.ORG_USER.equals(nodeType)
					|| StaticVariable.PRIV_ASSIGNTYPE_USER.equals(nodeType)) {
				auditList.add(threadAuditHistory.JSON2ThreadAuditHistory(orgId,
						StaticVariable.PRIV_ASSIGNTYPE_USER, threadId,
						threadAuditHistory));
			} else if (InfopubConstants.ORG_DEPT.equals(nodeType)
					|| StaticVariable.PRIV_ASSIGNTYPE_DEPT.equals(nodeType)) {
				auditList.add(threadAuditHistory.JSON2ThreadAuditHistory(orgId,
						StaticVariable.PRIV_ASSIGNTYPE_DEPT, threadId,
						threadAuditHistory));
			} else if (InfopubConstants.ORG_ROLE.equals(nodeType)
					|| StaticVariable.PRIV_ASSIGNTYPE_ROLE.equals(nodeType)) {
				auditList.add(threadAuditHistory.JSON2ThreadAuditHistory(orgId,
						StaticVariable.PRIV_ASSIGNTYPE_ROLE, threadId,
						threadAuditHistory));
			}
		}
		return auditList;
	}

	/**
	 * 返回查询发布类型为部门的,发给当前部门所有父部门的sql;以及发布类型为用户的,发给当前用户的sql.(以后如果有发布信息给角色的，可以在这里添加相应的处理)
	 * 2009-04-07
	 * 
	 * @param deptIds
	 * @param userid
	 * @return 返回查询发布类型为部门的,发给当前部门所有父部门的sql;以及发布类型为用户的,发给当前用户的sql.(以后如果有发布信息给角色的，可以在这里添加相应的处理)
	 */
	private String getPublishRangeQueryStr(String deptId, String userid) {
		// 若无deptIds则代表查询全部
		if (deptId == null || "".equals(deptId)) {
			return "";
		}
		int length = deptId.length();
		String deptids = "('" + deptId + "')";
		String dept = "(";
		// 2009-04-08
		// 根据子部门deptId跟父部门的deptId代码有连贯性的特点，子部门deptId每向前截2位代表一级父部门，根据这个原则由子部门deptId得到全部父部门deptId
		// if(length>1)
		// for(int k=0;2*k+1<=length;k++){
		// dept += "'"+deptId.substring(0, 2*k+1)+"',";
		// }
		// else dept += "'"+deptId+"',";

		ITawSystemDeptManager mgr = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
		TawSystemDept tawSystemDept = null;
		while (length > 0 && !deptId.equals("-1")) {
			tawSystemDept = mgr.getDeptinfobydeptid(deptId, "0");
			dept += "'" + tawSystemDept.getDeptId() + "',";
			deptId = tawSystemDept.getParentDeptid();
		}
		if (dept.indexOf(",") > -1) {
			dept = dept.substring(0, dept.length() - 1) + ")";
		}
		return " and ((((threadPermissionOrg.orgId in "
		+ dept
		+ " and threadPermissionOrg.orgType='"
		+ StaticVariable.PRIV_ASSIGNTYPE_DEPT
		+ "' and threadPermissionOrg.isIncludeSubDept = '1'"
		+ ")"
		+ " or (threadPermissionOrg.orgId in "
		+ deptids
		+ " and threadPermissionOrg.orgType='"
		+ StaticVariable.PRIV_ASSIGNTYPE_DEPT
		+ "' and (threadPermissionOrg.isIncludeSubDept <> '1' or threadPermissionOrg.isIncludeSubDept is null)"
		+ ")" + "or(threadPermissionOrg.orgId ='" + userid
		+ "' and threadPermissionOrg.orgType='"
		+ StaticVariable.PRIV_ASSIGNTYPE_USER + "'))"
		+ " and thread.id=threadPermissionOrg.threadId)  or thread.createrId='"+userid+"')";
		// 2009-10-19  hanlu@boco.com.cn
		// 信息发布已发布信息人员无法查看自己已发布的信息 增加条件创建人为当前用户 or thread.createrId='"+userid+"')" 
		
	}

	/**
	 * 返回查询发布类型为部门的,发给当前部门所有父部门的sql;以及发布类型为用户的,发给当前用户的sql.(以后如果有发布信息给角色的，可以在这里添加相应的处理)
	 * 2009-04-08 注释
	 * 
	 * @param deptIds
	 * @param userid
	 * @return 返回查询发布类型为部门的,发给当前部门所有父部门的sql;以及发布类型为用户的,发给当前用户的sql.(以后如果有发布信息给角色的，可以在这里添加相应的处理)
	 */
	// 2009-04-08 注释
	// private String getPublishRangeQueryStr(String deptIds, String userid) {
	// // 若无deptIds则代表查询全部
	// if (deptIds == null || "".equals(deptIds)) {
	// return "";
	// }
	// return " and ((threadPermissionOrg.orgId in " + deptIds
	// + " and threadPermissionOrg.orgType='"
	// + StaticVariable.PRIV_ASSIGNTYPE_DEPT + "')"
	// + "or(threadPermissionOrg.orgId ='" + userid
	// + "' and threadPermissionOrg.orgType='"
	// + StaticVariable.PRIV_ASSIGNTYPE_USER + "'))"
	// + " and thread.id=threadPermissionOrg.threadId";
	// }
	/**
	 * 显示某版块下的信息信息列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 版块id

		String forumsId = StaticMethod.null2String(request
				.getParameter("forumsId"));

		String pageIndexName = new org.displaytag.util.ParamEncoder(
				InfopubConstants.THREAD_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		// 每页显示条数
		Integer pageSize = UtilMgrLocator.getEOMSAttributes().getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		// 信息管理
		IThreadManager mgr = (IThreadManager) getBean("IthreadManager");
		String deptStr = null;
		List parentDepts = null;
		// 若拥有信息管理员的权限，则可以查询所有部门（范围）
		if (this.hasPermission(
				InfopubConstants.WORKBENCH_INFOPUB_PERMISSION_THREAD_MGR,
				request)) {
			request.setAttribute("threadMgrPriv", "threadMgrPriv");
		} else {
			deptStr = getUser(request).getDeptid();// 2009-04-08
		}

		// --- 排序及导出 ---
		String order = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								InfopubConstants.THREAD_LIST)
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_ORDER)));
		String sort = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								InfopubConstants.THREAD_LIST)
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_SORT)));
		String whereStr = "";
		if (!order.equals("")) {
			if (order.equals("1")) {
				order = " asc";
			} else {
				order = " desc";
			}
		}
		String exportType = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								InfopubConstants.THREAD_LIST)
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_EXPORTTYPE)));
		if (!exportType.equals("")) {
			pageSize = new Integer(-1);
		}

		Map map = null;
		if (UIConstants.ROOT_NODE.equals(forumsId) || null == forumsId
				|| "".equals(forumsId.trim())) {
			whereStr = " and thread.isDel<>'"
					+ Constants.DELETED_FLAG
					+ "' "
					+ getPublishRangeQueryStr(deptStr, getUser(request)
							.getUserid());
			if (!sort.equals("")) {
				whereStr += " order by thread." + sort + order;
			} else {
				whereStr += " order by thread.threadTypeId ,thread.editTime desc";
			}
			map = (Map) mgr.getThreads(pageIndex, pageSize, whereStr);
		} else {
			String forumsarg = "";
			forumsarg = getForumssbyParentId(forumsarg, forumsId);
			String subforumsarg = forumsarg
					.substring(0, forumsarg.length() - 1);

			whereStr = " and thread.forumsId in("
					+ subforumsarg
					+ ") and thread.isDel<>'"
					+ Constants.DELETED_FLAG
					+ "' "
					+ getPublishRangeQueryStr(deptStr, getUser(request)
							.getUserid());
			if (!sort.equals("")) {
				whereStr += " order by thread." + sort + order;
			} else {
				whereStr += " order by thread.editTime desc";
			}
			map = (Map) mgr.getThreads(pageIndex, pageSize, whereStr);

		}
		List list = (List) map.get("result");

		request.setAttribute(InfopubConstants.THREAD_LIST, list);

		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		String defaultId = Infopublocator.InfopubUtilInstance()
				.getDefaultForums();
		if (forumsId.equals(defaultId)) {
			return mapping.findForward("netList");
		}

		return mapping.findForward("list");
	}
	
	/**
	 * 显示某版块下的信息信息列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listByOA(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 版块id
		String forumsId = StaticMethod.null2String(request
				.getParameter("forumsId"));
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				InfopubConstants.THREAD_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		// 每页显示条数
		Integer pageSize = UtilMgrLocator.getEOMSAttributes().getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		// 信息管理
		IThreadManager mgr = (IThreadManager) getBean("IthreadManager");
		String deptStr = null;
		List parentDepts = null;
		// 若拥有信息管理员的权限，则可以查询所有部门（范围）
		if (this.hasPermission(
				InfopubConstants.WORKBENCH_INFOPUB_PERMISSION_THREAD_MGR,
				request)) {
			request.setAttribute("threadMgrPriv", "threadMgrPriv");
		} else {
			deptStr = getUser(request).getDeptid();// 2009-04-08
		}

		// --- 排序及导出 ---
		String order = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								InfopubConstants.THREAD_LIST)
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_ORDER)));
		String sort = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								InfopubConstants.THREAD_LIST)
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_SORT)));
		String whereStr = "";
		if (!order.equals("")) {
			if (order.equals("1")) {
				order = " asc";
			} else {
				order = " desc";
			}
		}
		String exportType = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								InfopubConstants.THREAD_LIST)
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_EXPORTTYPE)));
		if (!exportType.equals("")) {
			pageSize = new Integer(-1);
		}

		Map map = null;
		/*
		if (UIConstants.ROOT_NODE.equals(forumsId) || null == forumsId
				|| "".equals(forumsId.trim())) {
			whereStr = " and thread.isDel<>'"
					+ Constants.DELETED_FLAG
					+ "' "
					+ getPublishRangeQueryStr(deptStr, getUser(request)
							.getUserid());
			if (!sort.equals("")) {
				whereStr += " order by thread." + sort + order;
			} else {
				whereStr += " order by thread.threadTypeId ,thread.editTime desc";
			}
			map = (Map) mgr.getThreads(pageIndex, pageSize, whereStr);
		} else {
			String forumsarg = "";
			forumsarg = getForumssbyParentId(forumsarg, forumsId);
			String subforumsarg = forumsarg
					.substring(0, forumsarg.length() - 1);

			whereStr = " and thread.forumsId in("
					+ subforumsarg
					+ ") and thread.isDel<>'"
					+ Constants.DELETED_FLAG
					+ "' "
					+ getPublishRangeQueryStr(deptStr, getUser(request)
							.getUserid());
			if (!sort.equals("")) {
				whereStr += " order by thread." + sort + order;
			} else {
				whereStr += " order by thread.editTime desc";
			}
			map = (Map) mgr.getThreads(pageIndex, pageSize, whereStr);

		}*/
		String forumsarg = "";
		forumsarg = getForumssbyParentId(forumsarg, forumsId);
		String subforumsarg = forumsarg
				.substring(0, forumsarg.length() - 1);
		whereStr = " and thread.forumsId in("
			+ subforumsarg
			+ ") and thread.isDel<>'"
			+ Constants.DELETED_FLAG
			+ "' ";
		map = (Map) mgr.getThreads(pageIndex, pageSize, whereStr);
		List list = (List) map.get("result");

		request.setAttribute(InfopubConstants.THREAD_LIST, list);

		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		String defaultId = Infopublocator.InfopubUtilInstance()
				.getDefaultForums();
		if (forumsId.equals(defaultId)) {
			return mapping.findForward("netList");
		}

		return mapping.findForward("oaList");
	}

	private String getForumssbyParentId(String forumsarg, String forumsId) {
		IForumsManager forumsmgr = (IForumsManager) getBean("IforumsManager");
		forumsarg += "'" + forumsId + "',";
		List forumslist = forumsmgr.getForumsByParentId(forumsId);
		for (int i = 0; i < forumslist.size(); i++) {
			Forums forum = (Forums) forumslist.get(i);
			forumsarg = getForumssbyParentId(forumsarg, forum.getId());
		}
		return forumsarg;
	}

	/**
	 * 显示某版块下的信息信息列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward listAllPortlet(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 信息管理
		IThreadManager mgr = (IThreadManager) getBean("IthreadManager");

		String deptStr = null;
		List parentDepts = null;
		// 若拥有信息管理员的权限，则可以查询所有部门（范围）
		if (this.hasPermission(
				InfopubConstants.WORKBENCH_INFOPUB_PERMISSION_THREAD_MGR,
				request)) {
			// parentDepts = DeptMgrLocator.getTawSystemDeptManager()
			// .getParentDepts(deptMgr.getRootDept().getDeptId());
			// deptStr = this.depts2str(parentDepts);
		} else {
			// parentDepts = DeptMgrLocator.getTawSystemDeptManager()
			// .getParentDepts(getUser(request).getDeptid());
			// deptStr = this.depts2str(parentDepts);
			deptStr = getUser(request).getDeptid();// 2009-04-08
		}

		Map map = null;

		// 若点击为根结点（所有版块）则显示所有信息,将版块条件去掉
		map = (Map) mgr.getThreads(new Integer(0),
				InfopubConstants.INDEX_RECORD_COUNT, " and thread.isDel<>'"
						+ Constants.DELETED_FLAG
						+ "' "
						+ getPublishRangeQueryStr(deptStr, getUser(request)
								.getUserid()));

		List list = (List) map.get("result");
		request.setAttribute(InfopubConstants.THREAD_LIST, list);
		return mapping.findForward("threadListAllPortlet");
	}

	/**
	 * 将部门列表转换为(deptId1,dpetId2,...)形式
	 * 
	 * @param depts
	 * @return
	 */
	private String depts2str(List depts) {
		String result = "(";
		if (depts != null) {
			for (Iterator it = depts.iterator(); it.hasNext();) {
				TawSystemDept dept = (TawSystemDept) it.next();
				result += ("'" + dept.getDeptId() + "',");
			}
		}
		if (result.indexOf(",") > -1) {
			result = result.substring(0, result.length() - 1);
		}
		return result + ")";
	}

	/**
	 * 未定义method=?参数都转向此方法
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return list(mapping, form, request, response);
	}

	/**
	 * 转向信息发布树页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward threadTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("threadTree");
	}

	/**
	 * 信息归类
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward sort(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		if (!hasPermission(
				InfopubConstants.WORKBENCH_INFOPUB_PERMISSION_THREAD_MGR,
				request)) {
			return mapping.findForward("nopriv");
		}

		ThreadForm threadForm = (ThreadForm) form;
		// 要分类的信息
		String ids[] = request.getParameterValues("ids");

		// 信息管理
		IThreadManager mgr = (IThreadManager) getBean("IthreadManager");
		// 未选择要移动的专题
		if (threadForm.getToForumsId() == null
				|| "".equals(threadForm.getToForumsId().trim())) {
			ActionMessages messages = new ActionMessages();
			messages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(
					"thread.tip.sort"));
			saveMessages(request, messages);
			return mapping.findForward("fail");

		}
		// 分类到哪个版块
		mgr.sort(ids, threadForm.getToForumsId());

		// return forwardList4forumId(mapping, threadForm);
		return mapping.findForward("success");
	}

	/**
	 * 删除所选信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delChoose(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// ThreadForm threadForm = (ThreadForm) form;
		// 要分类的信息
		String ids[] = request.getParameterValues("ids");
		// 未选中则失败
		if (ids == null) {
			return mapping.findForward("fail");
		}
		// 信息管理
		IThreadManager mgr = (IThreadManager) getBean("IthreadManager");
		// 若拥有信息管理权限或管理员直接删除
		if (hasPermission(
				InfopubConstants.WORKBENCH_INFOPUB_PERMISSION_THREAD_MGR,
				request)) {
			// 删除信息列表
			mgr.removeThread(ids);
		}
		// 否则判断信息是否由用户发起
		// TODO 要给出提示哪条信息可删，哪条不可删
		else {
			for (int i = 0; i < ids.length; i++) {
				Thread thread = mgr.getThread(ids[i]);
				// 该信息必须非审核通过且必须是当前登陆用户发布
				if (!InfopubConstants.AUDIT_PASS.equals(thread.getStatus())
						&& this.getUserId(request)
								.equals(thread.getCreaterId())) {
					mgr.removeThread(ids[i]);
				}
			}
		}
		// return forwardList4forumId(mapping, threadForm);
		return mapping.findForward("success");
	}

	/**
	 * 删除所选信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward list4audit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// 信息管理
		IThreadManager mgr = (IThreadManager) getBean("IthreadManager");

		String pageIndexName = new org.displaytag.util.ParamEncoder(
				InfopubConstants.THREAD_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		// 每页显示条数
		Integer pageSize = UtilMgrLocator.getEOMSAttributes().getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));

		// --- 排序及导出 ---
		String order = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								InfopubConstants.THREAD_LIST)
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_ORDER)));
		String sort = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								InfopubConstants.THREAD_LIST)
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_SORT)));
		String sortCondition = "";
		if (!order.equals("")) {
			if (order.equals("1")) {
				order = " asc";
			} else {
				order = " desc";
			}
		}
		String exportType = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								InfopubConstants.THREAD_LIST)
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_EXPORTTYPE)));
		if (!exportType.equals("")) {
			pageSize = new Integer(-1);
		}
		if (!sort.equals("")) {
			sortCondition = " order by thread.editTime desc";
		} else {
			sortCondition = " order by thread.editTime desc";
		}

		// 取信息草稿列表，需要审核
		Map map = (Map) mgr.getThreads4Draft(pageIndex, pageSize,
				getUserId(request), sortCondition);
		List list = (List) map.get("result");
		request.setAttribute(InfopubConstants.THREAD_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("threadList4Drafts");
	}

	/**
	 * 查询信息列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ThreadForm threadForm = (ThreadForm) form;
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				InfopubConstants.THREAD_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		// 每页显示条数
		Integer pageSize = UtilMgrLocator.getEOMSAttributes().getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		// 信息管理
		IThreadManager mgr = (IThreadManager) getBean("IthreadManager");
		String deptStr = null;
		List parentDepts = null;
		// 若拥有信息管理员的权限，则可以查询所有部门（范围）
		if (this.hasPermission(
				InfopubConstants.WORKBENCH_INFOPUB_PERMISSION_THREAD_MGR,
				request)) {
			request.setAttribute("threadMgrPriv", "threadMgrPriv");
		} else {
			deptStr = getUser(request).getDeptid();// 2009-04-08

		}

		// --- 排序及导出 ---
		String order = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								InfopubConstants.THREAD_LIST)
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_ORDER)));
		String sort = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								InfopubConstants.THREAD_LIST)
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_SORT)));
		String whereStr = "";
		if (!order.equals("")) {
			if (order.equals("1")) {
				order = " asc";
			} else {
				order = " desc";
			}
		}
		String exportType = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								InfopubConstants.THREAD_LIST)
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_EXPORTTYPE)));
		if (!exportType.equals("")) {
			pageSize = new Integer(-1);
		}

		// 2009-5-26 王思轩
		// 来源部门ID,对在信发布的查询模块中添加按“来源”查询，可以实现按照发布信息的部门来查询和统计公告信息。根据记录创建人所在部门进行查询
		String deptFrom = request.getParameter("deptId");
		String deptFromStr = "";
		if (deptFrom != null && !"".equals(deptFrom)) {
			deptFromStr = " and "
					+ deptFrom
					+ " in(select u.deptid from TawSystemUser u where u.userid = thread.createrId)";
		}
		whereStr += this.getPublishRangeQueryStr(deptStr, getUser(request)
				.getUserid())
				+ deptFromStr;
		if (!sort.equals("")) {
			whereStr += " order by thread." + sort + order;
		} else {
			whereStr += " order by thread.threadTypeId ,thread.editTime desc";
		}
		Map map = mgr.searchThreads(pageIndex, pageSize, threadForm, whereStr);
		List list = (List) map.get("result");
		request.setAttribute(InfopubConstants.THREAD_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);

		return mapping.findForward("threadSearchList");
	}

	public ActionForward search2(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ThreadForm threadForm = (ThreadForm) form;
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				InfopubConstants.THREAD_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		// 每页显示条数
		Integer pageSize = UtilMgrLocator.getEOMSAttributes().getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		// 信息管理
		IThreadManager mgr = (IThreadManager) getBean("IthreadManager");
		String deptStr = null;
		List parentDepts = null;
		// 若拥有信息管理员的权限，则可以查询所有部门（范围）
		if (this.hasPermission(
				InfopubConstants.WORKBENCH_INFOPUB_PERMISSION_THREAD_MGR,
				request)) {
			// parentDepts = DeptMgrLocator.getTawSystemDeptManager()
			// .getParentDepts(deptMgr.getRootDept().getDeptId());
			// deptStr = this.depts2str(parentDepts);
			// 版块管理
			// IForumsManager forumMgr = (IForumsManager)
			// getBean("IforumsManager");
			// List forums = forumMgr.getForumss(null);
			// 有权限
			request.setAttribute("threadMgrPriv", "threadMgrPriv");
		} else {
			// 2009-04-08 注释掉，想得到父部门deptId，只需根据代码规律；根据登录用户deptId，一步步去查父部门，效率低下。
			// parentDepts = DeptMgrLocator.getTawSystemDeptManager()
			// .getParentDepts(getUser(request).getDeptid());
			// deptStr = this.depts2str(parentDepts);
			deptStr = getUser(request).getDeptid();// 2009-04-08

		}

		// Map map = mgr.searchThreads(pageIndex, pageSize, threadForm,
		// this.getPublishRangeQueryStr(deptStr1,deptStr,
		// getUser(request).getUserid()));//2009-04-08

		// 2009-5-26 王思轩
		// 来源部门ID,对在信发布的查询模块中添加按“来源”查询，可以实现按照发布信息的部门来查询和统计公告信息。根据记录创建人所在部门进行查询
		String deptFrom = request.getParameter("deptId");
		String deptFromStr = "";
		if (deptFrom != null && !"".equals(deptFrom)) {
			deptFromStr = " and "
					+ deptFrom
					+ " in(select u.deptid from TawSystemUser u where u.userid = thread.createrId)";
		}
		// 将状态设置为审核通过或不需要审核
		threadForm.setStatus(InfopubConstants.AUDIT_PASS
				+ "' or thread.status='" + InfopubConstants.NO_AUDIT
				+ "' or thread.status='" + InfopubConstants.ROTATION);
		// 获取子模块的id
		/*
		 * forumsarg = ""; getForumssbyParentId(threadForm.getForumsId());
		 * String subforumsarg = forumsarg.substring(1, forumsarg.length() - 2);
		 * threadForm.setForumsId(subforumsarg);
		 */

		// --- 排序及导出 ---
		String order = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								InfopubConstants.THREAD_LIST)
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_ORDER)));
		String sort = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								InfopubConstants.THREAD_LIST)
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_SORT)));
		String whereStr = "";
		if (!order.equals("")) {
			if (order.equals("1")) {
				order = " asc";
			} else {
				order = " desc";
			}
		}
		String exportType = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								InfopubConstants.THREAD_LIST)
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_EXPORTTYPE)));
		if (!exportType.equals("")) {
			pageSize = new Integer(-1);
		}
		String forumsId = threadForm.getForumsId();
		if (UIConstants.ROOT_NODE.equals(forumsId) || null == forumsId
				|| "".equals(forumsId.trim())) {
			whereStr = " and thread.isDel<>'"
					+ Constants.DELETED_FLAG
					+ "' "
					+ getPublishRangeQueryStr(deptStr, getUser(request)
							.getUserid());
			if (!sort.equals("")) {
				whereStr += " order by thread." + sort + order;
			} else {
				whereStr += " order by thread.threadTypeId ,thread.editTime desc";
			}
		} else {
			String forumsarg = "";
			forumsarg = getForumssbyParentId(forumsarg, forumsId);
			String subforumsarg = forumsarg
					.substring(0, forumsarg.length() - 1);
			threadForm.setForumsId(subforumsarg);
			whereStr = " and thread.isDel<>'"
					+ Constants.DELETED_FLAG
					+ "' "
					+ getPublishRangeQueryStr(deptStr, getUser(request)
							.getUserid());
			if (!sort.equals("")) {
				whereStr += " order by thread." + sort + order;
			} else {
				whereStr += " order by thread.editTime desc";
			}
		}

		Map map = mgr.searchThreadsOrderby(pageIndex, pageSize, threadForm,
				this.getPublishRangeQueryStr(deptStr, getUser(request)
						.getUserid())
						+ deptFromStr, whereStr);
		List list = (List) map.get("result");

		// 加上时间段限制过滤(开始)
		/*
		 * List inTimeResult = new ArrayList(); for (int i = 0; i < list.size();
		 * i++) { Thread thread = (Thread) list.get(i);
		 * 
		 * DateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		 * GregorianCalendar cal = new GregorianCalendar(); cal.setTime(new
		 * Date()); cal.add(Calendar.DATE, 1 - StaticMethod.null2int(thread
		 * .getValidity())); Date dCurrentTimeAdd =
		 * f.parse(f.format(cal.getTime())); if
		 * (thread.getCreateTime().after(dCurrentTimeAdd)) {
		 * inTimeResult.add(thread); } }
		 */
		int resultSize = list.size();
		request.setAttribute(InfopubConstants.THREAD_LIST, list);

		request.setAttribute("resultSize", map.get("total"));
		// request.setAttribute("resultSize", new Integer(resultSize));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");
		// return mapping.findForward("threadSearchList");
	}

	/**
	 * 查询信息列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward initSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		return mapping.findForward("threadSearch");
	}

	public ActionForward getAtomLists(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String forumsId = StaticMethod.null2String(request
				.getParameter("forumsId"));
		int count = new Integer(StaticMethod.null2String(request
				.getParameter("count"))).intValue();
		String contentPath = request.getScheme() + "://"
				+ request.getLocalAddr() + ":" + request.getLocalPort()
				+ request.getContextPath();
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				InfopubConstants.THREAD_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		// 每页显示条数
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		// 信息管理
		IThreadManager mgr = (IThreadManager) getBean("IthreadManager");
		IForumsManager fMgr = (IForumsManager) getBean("IforumsManager");

		// 版块管理
		// IForumsManager forumMgr = (IForumsManager) getBean("IforumsManager");
		String deptStr = null;
		Thread eThread = null;
		List parentDepts = null;
		// 若拥有信息管理员的权限，则可以查询所有部门（范围）
		if (this.hasPermission(
				InfopubConstants.WORKBENCH_INFOPUB_PERMISSION_THREAD_MGR,
				request)) {
			request.setAttribute("threadMgrPriv", "threadMgrPriv");
		} else {
			// parentDepts = DeptMgrLocator.getTawSystemDeptManager()
			// .getParentDepts(getUser(request).getDeptid());
			// deptStr = this.depts2str(parentDepts);
			deptStr = getUser(request).getDeptid();// 2009-04-08
		}
		Map map = null;
		if (forumsId != null && !"".equals(forumsId)) {
			map = (Map) mgr.getThreads(pageIndex, pageSize,
					" and thread.forumsId='"
							+ forumsId
							+ "' and thread.isDel<>'"
							+ Constants.DELETED_FLAG
							+ "' "
							+ getPublishRangeQueryStr(deptStr, getUser(request)
									.getUserid()));
		} else {
			map = (Map) mgr.getThreads(pageIndex, pageSize,
					" and thread.isDel<>'"
							+ Constants.DELETED_FLAG
							+ "' "
							+ getPublishRangeQueryStr(deptStr, getUser(request)
									.getUserid()));
		}

		List list = (List) map.get("result");
		// 加上时间段限制过滤(开始)
		/*
		 * List inTimeResult = new ArrayList(); for (int i = 0; i < list.size();
		 * i++) { Thread thread = (Thread) list.get(i);
		 * 
		 * DateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		 * GregorianCalendar cal = new GregorianCalendar(); cal.setTime(new
		 * Date()); cal.add(Calendar.DATE, 1 - StaticMethod.null2int(thread
		 * .getValidity())); Date dCurrentTimeAdd =
		 * f.parse(f.format(cal.getTime()));
		 * 
		 * if (thread.getCreateTime().after(dCurrentTimeAdd)) {
		 * inTimeResult.add(thread); } }
		 */
		request.setAttribute(InfopubConstants.THREAD_LIST, list);
		// 加上时间段限制过滤(结束)
		String resultSize = "0";
		if (list.size() != 0) {
			resultSize = list.size() + "";
		}
		request.setAttribute("resultSize", new Integer(resultSize));
		request.setAttribute("pageSize", pageSize);

		// start

		// 创建ATOM源
		Factory factory = Abdera.getNewFactory();
		Feed feed = factory.newFeed();
		// 分页
		int listSize = list.size();
		listSize = (count <= listSize) ? count : listSize;
		for (int i = 0; i < listSize; i++) {
			eThread = (Thread) list.get(i);
			String status = StaticMethod
					.null2String(DictMgrLocator
							.getDictService()
							.itemId2name(
									"dict-workbench-infopub"
											+ com.boco.eoms.commons.system.dict.util.Constants.DICT_ID_SPLIT_CHAR
											+ "auditStatus",
									eThread.getStatus()).toString());
			String threadTypeName = StaticMethod
					.null2String(DictMgrLocator
							.getDictService()
							.itemId2name(
									"dict-workbench-infopub"
											+ com.boco.eoms.commons.system.dict.util.Constants.DICT_ID_SPLIT_CHAR
											+ "threadType",
									eThread.getThreadTypeId()).toString());
			Entry entry = feed.insertEntry();
			entry.setTitle(eThread.getTitle());
			entry.setContent(contentPath + "/workbench/infopub/thread.do?id="
					+ eThread.getId() + "&method=detail");
			entry.setSummary(eThread.getThreadCount().toString());
			entry.setPublished(eThread.getCreateTime());
			entry.setUpdated(eThread.getEditTime());
			entry.setLanguage(threadTypeName);
			entry.setText(StaticMethod.null2String(fMgr.getForums(
					eThread.getForumsId()).getTitle()));
			entry.setRights(status);
			Person person = entry.addAuthor("");
			person.setName(eThread.getCreaterName());
		}
		// 每页显示条数
		feed.setText(resultSize);
		feed.setTitle(contentPath + "/workbench/infopub/thread.do?method=list");
		OutputStream os = response.getOutputStream();
		PrintStream ps = new PrintStream(os);
		feed.getDocument().writeTo(ps);
		// end
		return null;
	}

	/**
	 * 跳转到审核未通过页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward list4Reject(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// 信息管理
		IThreadManager mgr = (IThreadManager) getBean("IthreadManager");

		String pageIndexName = new org.displaytag.util.ParamEncoder(
				InfopubConstants.THREAD_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		// 每页显示条数
		Integer pageSize = UtilMgrLocator.getEOMSAttributes().getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));

		// --- 排序及导出 ---
		String order = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								InfopubConstants.THREAD_LIST)
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_ORDER)));
		String sort = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								InfopubConstants.THREAD_LIST)
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_SORT)));
		String sortCondition = "";
		if (!order.equals("")) {
			if (order.equals("1")) {
				order = " asc";
			} else {
				order = " desc";
			}
		}
		String exportType = StaticMethod
				.null2String(request
						.getParameter(new org.displaytag.util.ParamEncoder(
								InfopubConstants.THREAD_LIST)
								.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_EXPORTTYPE)));
		if (!exportType.equals("")) {
			pageSize = new Integer(-1);
		}

		if (!sort.equals("")) {
			sortCondition = " order by thread." + sort + order;
		} else {
			sortCondition = " order by thread.threadTypeId ,thread.editTime desc";
		}
		// 取信息草稿列表，审核未通过的
		Map map = (Map) mgr.getThreads4Reject(pageIndex, pageSize,
				getUserId(request), sortCondition);
		List list = (List) map.get("result");
		request.setAttribute(InfopubConstants.THREAD_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("threadList4Reject");
	}

	public ActionForward netDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		if (log.isDebugEnabled()) {
			log.debug("Entering 'detail' method");
		}

		ThreadForm threadForm = (ThreadForm) form;
		IThreadManager mgr = (IThreadManager) getBean("IthreadManager");
		String id = StaticMethod.null2String(threadForm.getId());
		if (id.equals("")) {
			id = StaticMethod.null2String(request.getParameter("id"));
		}
		// 根据主键取详细信息
		Thread thread = mgr.getThread(threadForm.getId());
		// 若无此贴子则转向失败页面，不记录查看记录历史
		boolean flag = false;
		boolean flag1 = false;
		if (thread == null || thread.getId() == null) {
			return mapping.findForward("fail");
		}
		String noValidity = StaticMethod.null2String(thread.getNoValidity());
		if (noValidity.equals("1")) {
			flag1 = true;

		} else {
			DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
			String validityDate = StaticMethod.null2String(thread
					.getValidityDate());
			String nowDate = StaticMethod.getCurrentDateTime();
			Date date1 = f.parse(validityDate);
			Date date2 = f.parse(nowDate);
			flag = date1.after(date2);

		}

		threadForm = (ThreadForm) convert(thread);
		request.setAttribute("status", threadForm.getStatus());
		request.setAttribute("threadForm", threadForm);

		// 仅审核通过，正式发布记入查看信息历史
		if (InfopubConstants.AUDIT_PASS.equals(thread.getStatus())
				|| InfopubConstants.NO_AUDIT.equals(thread.getStatus())
				|| InfopubConstants.ROTATION.equals(thread.getStatus())) {
			// 信息查看历史对象
			ThreadHistory history = new ThreadHistory();
			// 阅读者ip
			history.setIp(this.getUser(request).getRomteaddr());
			// 阅读时间
			history.setReadTime(new Date());
			// 阅读贴子
			history.setThreadId(id);
			// 阅读人id
			history.setUserId(this.getUserId(request));
			// 保存访问历史记录并新增访问次数
			if (flag)
				mgr.saveThreadHistory(thread, history);
		}
		IThreadPermimissionOrgManager orgMgr = (IThreadPermimissionOrgManager) getBean("IthreadPermimissionOrgManager");
		List pOrgs = orgMgr.getThreadPermissionOrgsByThreadId(id);
		IThreadAuditHistoryManager auditMgr = (IThreadAuditHistoryManager) getBean("IthreadAuditHistoryManager");
		ThreadAuditHistory Audit = auditMgr.getCurrentThreadAuditHistory(id);
		// 信息权限列表转换成json的字符串
		String jsonOrgs = threadPermissionOrgs2jsonStr(pOrgs);
		String jsonAudit = threadAuditHistoryAuditer2jsonStr(Audit);
		request.setAttribute("jsonOrgs", jsonOrgs);
		request.setAttribute("jsonAudit", jsonAudit);

		String pageIndexNameCount = new org.displaytag.util.ParamEncoder(
				InfopubConstants.THREADCOUNTHISTORY_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSizeCount = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndexCount = new Integer(
				GenericValidator.isBlankOrNull(request
						.getParameter(pageIndexNameCount)) ? 0
						: (Integer.parseInt(request
								.getParameter(pageIndexNameCount)) - 1));
		IThreadHistoryManager mgrHis = (IThreadHistoryManager) getBean("IthreadHistoryManager");
		Map mapCount = (Map) mgrHis.getThreadCountHistory(pageIndexCount,
				pageSizeCount, " where threadId='" + id + "'");
		List listCount = (List) mapCount.get("result");
		request.setAttribute(InfopubConstants.THREADCOUNTHISTORY_LIST,
				listCount);
		request.setAttribute("resultSizeCount", mapCount.get("total"));
		request.setAttribute("pageSizeCount", pageSizeCount);

		String pageIndexNameComments = new org.displaytag.util.ParamEncoder(
				InfopubConstants.THREADCOMMENTSHISTORY_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSizeComments = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndexComments = new Integer(
				GenericValidator.isBlankOrNull(request
						.getParameter(pageIndexNameComments)) ? 0
						: (Integer.parseInt(request
								.getParameter(pageIndexNameComments)) - 1));
		IThreadHistoryManager mgrComments = (IThreadHistoryManager) getBean("IthreadHistoryManager");
		Map mapComments = (Map) mgrComments.getThreadHistorys(
				pageIndexComments, pageSizeComments, " where threadId='" + id
						+ "'" + " and historyType='1'");
		List listComments = (List) mapComments.get("result");
		request.setAttribute(InfopubConstants.THREADCOMMENTSHISTORY_LIST,
				listComments);
		request.setAttribute("resultSizeComments", mapComments.get("total"));
		request.setAttribute("pageSizeComments", pageSizeComments);
		if (flag1) {
			return mapping.findForward("detail");
		}
		if (!flag) {
			return mapping.findForward("Vdetail");
		}
		return mapping.findForward("netDetail");
	}

	public ActionForward auditUserList(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String node = StaticMethod.null2String(request.getParameter("node"),
				StaticVariable.ProvinceID + "");
		String selfFlag = StaticMethod.null2String(request
				.getParameter("noself"), "");
		TawSystemDeptBo deptbo = TawSystemDeptBo.getInstance();
		TawSystemUserRoleBo userrolebo = TawSystemUserRoleBo.getInstance();
		String template = StaticMethod.null2String(request.getParameter("tpl"),
				"tpl-user-xtree-fromdept");
		ArrayList userlist = new ArrayList();
		ArrayList deptlist = new ArrayList();
		try {
			deptlist = (ArrayList) deptbo.getNextLevecDepts(node, "0");
			if (selfFlag.equals("true")) {// 不包括自己的人员list
				TawSystemSessionForm sessionform = (TawSystemSessionForm) request
						.getSession().getAttribute("sessionform");
				userlist = (ArrayList) userrolebo.getUserBydeptidsNoSelf(node,
						sessionform.getUserid());
			} else {
				userlist = (ArrayList) userrolebo.getUserBydeptids(node);
			}

		} catch (Exception ex) {
			BocoLog.error(this, "生成部门用户树图时报错：" + ex);
		}
		request.setAttribute("deptlist", deptlist);
		request.setAttribute("userlist", userlist);
		return mapping.findForward(template);
	}

}
