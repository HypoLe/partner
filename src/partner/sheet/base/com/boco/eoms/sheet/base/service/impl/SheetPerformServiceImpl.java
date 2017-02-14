package com.boco.eoms.sheet.base.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import utils.PartnerPrivUtils;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.loging.BocoLog;
import com.boco.eoms.commons.system.dict.exceptions.DictServiceException;
import com.boco.eoms.commons.system.dict.model.DictItemXML;
import com.boco.eoms.commons.system.dict.service.ID2NameService;
import com.boco.eoms.commons.system.dict.util.DictMgrLocator;
import com.boco.eoms.commons.system.dict.util.Util;
import com.boco.eoms.commons.system.role.model.TawSystemRole;
import com.boco.eoms.commons.system.role.model.TawSystemSubRole;
import com.boco.eoms.commons.system.role.service.ITawSystemRoleManager;
import com.boco.eoms.commons.system.role.util.RoleManage;
import com.boco.eoms.commons.system.role.util.RoleMapSchema;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.commons.system.user.service.ITawSystemUserManager;
import com.boco.eoms.commons.ui.util.JSONUtil;
import com.boco.eoms.partner.deviceInspect.switchcfg.PnrDeviceInspectSwitchConfig;
import com.boco.eoms.partner.sheet.base.process.model.ClaimTaskData;
import com.boco.eoms.partner.sheet.base.process.model.FinishTaskData;
import com.boco.eoms.partner.sheet.base.process.model.TriggerProcessData;
import com.boco.eoms.sheet.base.dao.IPnrSheetQueryDao;
import com.boco.eoms.sheet.base.model.BaseLink;
import com.boco.eoms.sheet.base.model.BaseMain;
import com.boco.eoms.sheet.base.model.PnrSheetQuery;
import com.boco.eoms.sheet.base.service.IBusinessFlowService;
import com.boco.eoms.sheet.base.service.ILinkService;
import com.boco.eoms.sheet.base.service.IMainService;
import com.boco.eoms.sheet.base.service.IPreEngineDataHandler;
import com.boco.eoms.sheet.base.service.ISheetFacadeService;
import com.boco.eoms.sheet.base.service.ISheetInfoShowService;
import com.boco.eoms.sheet.base.service.ISheetPerformService;
import com.boco.eoms.sheet.base.service.ITaskService;
import com.boco.eoms.sheet.base.task.ITask;
import com.boco.eoms.sheet.base.util.Constants;
import com.boco.eoms.sheet.base.util.EomsInterpreter;
import com.boco.eoms.sheet.base.util.HibernateConfigurationHelper;
import com.boco.eoms.sheet.base.util.SheetBeanUtils;
import com.boco.eoms.sheet.base.util.SheetUtils;
import com.boco.eoms.sheet.base.util.UUIDHexGenerator;
import com.boco.eoms.sheet.base.util.flowdefine.xml.FlowDefine;
import com.boco.eoms.sheet.base.util.flowdefine.xml.FlowDefineExplain;
import com.boco.eoms.sheet.base.util.flowdefine.xml.ToPhaseId;
import com.boco.eoms.sheet.base.webapp.action.IBaseSheet;
import com.boco.eoms.sheet.dealtypeconfig.util.DealTypeConfigUtil;
import com.boco.eoms.sheet.limit.model.LevelLimit;
import com.boco.eoms.sheet.limit.model.StepLimit;
import com.boco.eoms.sheet.limit.service.ISheetDealLimitManager;
import com.boco.eoms.sheet.limit.util.SheetLimitUtil;
import com.boco.eoms.sheet.tool.relation.service.ITawSheetRelationManager;

/**
 * Description: Copyright: Copyright (c)2012 Company: BOCO
 * 
 * @author: Liuchang
 * @version: 1.0 Create at: Oct 11, 2012 4:17:21 PM
 */
@SuppressWarnings("unchecked")
public class SheetPerformServiceImpl implements ISheetPerformService {

	private ISheetFacadeService sheetFacadeService;

	public ISheetFacadeService getSheetFacadeService() {
		return sheetFacadeService;
	}

	public void setSheetFacadeService(ISheetFacadeService sheetFacadeService) {
		this.sheetFacadeService = sheetFacadeService;
	}

	public String getPageColumnName() {
		return Constants.pageColumnName;
	}

	public String getRoleConfigPath() {
		return sheetFacadeService.getRoleConfigPath();
	}

	protected IMainService getMainService() {
		return sheetFacadeService.getMainService();
	}

	protected ILinkService getLinkService() {
		return sheetFacadeService.getLinkService();
	}

	protected IBusinessFlowService getBusinessFlowService() {
		return sheetFacadeService.getBusinessFlowService();
	}

	protected IPreEngineDataHandler getPreEngineDataHandler() {
		return sheetFacadeService.getPreEngineDataHandler();
	}

	public ITaskService getTaskService() {
		return sheetFacadeService.getTaskService();
	}

	/**
	 * 解析流程配置文件
	 */
	public HashMap analyseFlowDefine(HttpServletRequest request,
			String sheetPageName) throws Exception {
		String status = ""; // 匹配结果返回值
		HashMap sessionMap = new HashMap();
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		sessionMap.put("userId", sessionform.getUserid());
		sessionMap.put("password", sessionform.getPassword());
		String taskName = StaticMethod.nullObject2String(request
				.getParameter(sheetPageName + "activeTemplateId"));
		String toDeptId = StaticMethod.nullObject2String(request
				.getParameter(sheetPageName + "toDeptId"));
		FlowDefineExplain explain = new FlowDefineExplain();

		if (taskName.equals("")) {
			explain.setFlowId(this.getMainService().getFlowTemplateName());
			explain.setCurrentPhaseId("receive");
		} else {
			explain.setFlowId(this.getMainService().getFlowTemplateName());
			explain.setCurrentPhaseId(taskName);
		}
		StringBuffer sb = new StringBuffer();
		StringBuffer sbFn = new StringBuffer();
		if (this.getRoleConfigPath() != null
				&& !this.getRoleConfigPath().equals("")) {
			List list = explain.explain(this.getRoleConfigPath());

			HashMap columnMap = RoleMapSchema.getInstance().getStyleIDsBySheet(
					this.getMainService().getFlowTemplateName());

			Hashtable hash = new Hashtable();
			hash.put("deptId", toDeptId);
			Set filterSet = columnMap.keySet();
			Iterator filterIt = filterSet.iterator();
			while (filterIt.hasNext()) {
				String hashName = StaticMethod.nullObject2String(filterIt
						.next());
				String name = StaticMethod.nullObject2String(columnMap
						.get(hashName));
				if (!name.equals("")) {
					String value = StaticMethod.nullObject2String(request
							.getParameter(sheetPageName + name));
					hash.put(hashName, value);
				}
			}

			for (int phaseCount = 0; list != null && phaseCount < list.size(); phaseCount++) {
				String dealPerformer = "";
				// String roleString = "";
				String roleId = "";

				ToPhaseId toPhaseId = (ToPhaseId) list.get(phaseCount);
				String role = toPhaseId.getRole();
				String bigRole = "";

				String condition = toPhaseId.getCondition();
				if (!condition.equals("")) {
					Hashtable conhash = EomsInterpreter
							.getParamNameFromCondition(condition);
					Enumeration eunm = conhash.keys();

					while (eunm.hasMoreElements()) {
						String key = (String) eunm.nextElement();
						String value = StaticMethod.nullObject2String(request
								.getParameter(sheetPageName + key));
						if (value.equals("")) {
							value = "null";
						}
						condition = condition.replaceAll(
								"\\$\\{" + key + "\\}", value);
					}
					boolean isPass = EomsInterpreter
							.getbooleanFromExpression(condition);
					System.out.println("condition===" + condition
							+ "isPass====" + isPass);
					if (isPass == false) {
						continue;
					}
				}
				sb.append(explain.getFlowDefine().getDescription()
						+ "工单即将流转到步骤:"
						+ explain.getFlowDefine()
								.getPhasesByPhaseId(toPhaseId.getId())
								.getName() + "\n");
				// 流程config文件中配置了roleid
				if (!role.equals("")) {
					String temp[] = role.split(",");
					for (int ii = 0; temp != null && ii < temp.length; ii++) {
						String temp2[] = temp[ii].split("@");
						dealPerformer = StaticMethod.nullObject2String(request
								.getParameter(sheetPageName + temp2[0]));
						bigRole = temp2[1];
						try {
							String moduleId = RoleMapSchema.getInstance()
									.getModelIdBySheet(
											this.getMainService()
													.getFlowTemplateName());
							ITawSystemRoleManager mgr = (ITawSystemRoleManager) ApplicationContextHolder
									.getInstance().getBean(
											"ItawSystemRoleManager");
							TawSystemRole trole = mgr.getTawSystemRole(
									new Integer(moduleId), bigRole);
							bigRole = String.valueOf(trole.getRoleId());
						} catch (Exception e) {

						}
						/**
						 * 当界面上选择了派发角色时，查询所选择的派发角色下的人员
						 */
						if (!dealPerformer.equals("")) {
							Map userNameMap = SheetUtils
									.getUserNameForSubRole(dealPerformer);
							String user = StaticMethod
									.nullObject2String(userNameMap.get("name"));
							String subRoleName = StaticMethod
									.nullObject2String(userNameMap
											.get("subRoleName"));
							sb.append("根据您的选择，工单即将派发给角色:" + subRoleName + "\n");
							sb.append("该角色对应用户为:" + user + "\n");
						}
						/**
						 * 根据规则匹配
						 */
						String Leader = "";
						List perform = RoleManage.getInstance().getSubRoles(
								bigRole, hash);

						Iterator it = filterSet.iterator();
						TawSystemSubRole subRole = SheetUtils
								.getMaxFilterSubRole(perform, it);

						if (subRole != null && subRole.getId() != null
								&& !subRole.getId().equals("")) {
							roleId = subRole.getId();
							Map userNameMap = SheetUtils
									.getUserNameForSubRole(subRole.getId());
							String user = StaticMethod
									.nullObject2String(userNameMap.get("name"));
							String subRoleName = StaticMethod
									.nullObject2String(userNameMap
											.get("subRoleName"));
							Leader = StaticMethod.nullObject2String(userNameMap
									.get("leaderId"));
							if (Leader.equals("")) {
								Leader = roleId;
							}
							sb.append("根据规则匹配，该步骤的执行角色为:" + subRoleName + "\n");
							sb.append("该角色对应用户为:" + user + "\n");
							sbFn.append("function(){");
							sbFn.append("try{" + "var "
									+ sheetPageName
									+ temp2[0]
									+ "s = document.getElementsByName('"
									+ sheetPageName
									+ temp2[0]
									+ "');"
									+ "var "
									+ sheetPageName
									+ temp2[0]
									+ "Types = document.getElementsByName('"
									+ sheetPageName
									+ temp2[0]
									+ "Type');"
									+ "var "
									+ sheetPageName
									+ temp2[0]
									+ "Leaders = document.getElementsByName('"
									+ sheetPageName
									+ temp2[0]
									+ "Leader');"
									+ "for(var i=0;i<dealPerformers.length;i++){"
									+ sheetPageName + temp2[0] + "s[i].value='"
									+ roleId + "';" + sheetPageName + temp2[0]
									+ "Types[i].value='subrole';"
									+ sheetPageName + temp2[0]
									+ "Leaders[i].value='" + Leader + "';"
									+ "}" + "}catch(e){}");
							sbFn.append("}");
							status = "1";
						}

						if (!dealPerformer.equals("")) {
							sb.append("工单将流转到您所选择的角色\n");
							sbFn.append("null");
							status = "1";
						}
						if (roleId.equals("") && dealPerformer.equals("")) {
							sb.append("根据您的选择，该步骤没有匹配出具体的角色\n请更改您的选择或者联系管理员配置相关角色\n");
							status = "2";
						}
					}
				} else {
					// 流程config文件中没有配置roleid,就直接提交，不显示任何提示框
					status = "0";
				}
			}

			if (list == null || 0 == list.size()) {
				status = "0";
			}
		} else {
			status = "0";
		}

		HashMap map = new HashMap();
		JSONObject o = new JSONObject();
		o.put("text", sb.toString());
		o.put("fn", sbFn.toString());
		System.out.println("o.toString()=" + o.toString());
		map.put("jsonObject", o);
		map.put("status", status);
		return map;
	}

	public void newPerformAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	}

	public void newPerformDeal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	}

	/**
	 * 整合用 非流程动作的处理：目前包括抄送、阶段回复、阶段通知
	 */
	public void newPerformNonFlow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BocoLog.info(this, "===优化======非流程动作===");
		// 获取页面输入信息
		String sheetKey = StaticMethod.nullObject2String(request
				.getParameter("mainId"));
		String taskId = StaticMethod.nullObject2String(request
				.getParameter("TKID"));
		Map parameterMap = request.getParameterMap();
		HashMap<String, Object> columnMap = new HashMap<String, Object>();

		BaseMain main = this.getMainService().getSingleMainPO(sheetKey);
		BaseLink link = this.getLinkService().getLinkObject().getClass()
				.newInstance();

		columnMap.put("main", main);
		columnMap.put("link", link);
		columnMap.put("operate", this.getPageColumnName());

		Map cloneMap = new HashMap();
		cloneMap.putAll(parameterMap);
		HashMap wpsMap = SheetUtils.actionMapToEngineMap(cloneMap, columnMap);
		dealFlowEngineMap(request, wpsMap);

		// 非流程动作：目前包括抄送、阶段回复、阶段通知中进行link和task的保存
		this.newSaveNonFlowData(request, taskId, wpsMap);

	}

	/**
	 * 非流程动作：目前包括抄送、阶段回复、阶段通知中进行link和task的保存
	 * 
	 * @param taskId
	 *            任务id
	 * @param dataMap
	 *            输入参数为Map类型，其中包括：Map类型的main、Map类型的link、Map类型的operate
	 * @return
	 * @throws Exception
	 */
	public void newSaveNonFlowData(HttpServletRequest request, String taskId,
			Map dataMap) throws Exception {

		HashMap linkMap = (HashMap) dataMap.get("link");
		HashMap operateMap = (HashMap) dataMap.get("operate");
		String operateType = StaticMethod.nullObject2String(linkMap
				.get("operateType"));
		String copyPerformer = StaticMethod.nullObject2String(request
				.getParameter("copyPerformer"));
		ITask task = null;
		BaseLink linkbean = null;
		String processTemplateName = this.getMainService()
				.getFlowTemplateName();
		Date nowDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(nowDate);
		// 得到main对象Start--通过link中的mainId得到main对象；当新增时还没有mainId，则从MainMap中取得main的数据
		BaseMain main = null;
		String mainId = StaticMethod.nullObject2String(linkMap.get("mainId"));

		if (dataMap.get("main") != null) {
			HashMap mainMap = (HashMap) dataMap.get("main");
			mainId = StaticMethod.nullObject2String(mainMap.get("id"));
			main = this.getMainService().getMainObject();
			main.setId(mainId);
			main.setSheetId(StaticMethod.nullObject2String(mainMap
					.get("sheetId")));
			main.setTitle(StaticMethod.nullObject2String(mainMap.get("title")));
			main.setPiid(StaticMethod.nullObject2String(mainMap.get("piid")));
			System.out.println(mainMap.get("sendTime").getClass().getName());
		} else {
			main = (BaseMain) this.getMainService().getSingleMainPO(mainId);
			BocoLog.warn(this, "数据对象中工单信息为空，请通知管理员");
		}

		// 得到main对象end
		if (Integer.parseInt(operateType) == Constants.ACTION_DRIVERFORWARD) {
			// 阶段通知
			if (!taskId.equals("")) {
				BocoLog.info(this, "===优化======已阅===");
				task = (ITask) this.getTaskService().getSinglePO(taskId);
				task.setTaskStatus(Constants.TASK_STATUS_FINISHED);
				this.getTaskService().addTask(task);
			} else {
				BocoLog.info(this, "===优化======阶段通知===");
				String dealPerformer = StaticMethod
						.nullObject2String(operateMap.get("dealPerformer"));
				String dealPerformerLeader = StaticMethod
						.nullObject2String(operateMap
								.get("dealPerformerLeader"));
				String dealPerformerType = StaticMethod
						.nullObject2String(operateMap.get("dealPerformerType"));

				String[] dealPerformers = dealPerformer.split(",");
				String[] dealPerformerLeaders = dealPerformerLeader.split(",");
				String[] dealPerformerTypes = dealPerformerType.split(",");

				for (int i = 0; i < dealPerformers.length; i++) {
					linkbean = (BaseLink) this.getLinkService().getLinkObject()
							.getClass().newInstance();
					SheetBeanUtils.populateEngineMap2Bean(linkbean, linkMap);
					linkbean.setId(UUIDHexGenerator.getInstance().getID());
					linkbean.setMainId(mainId);
					linkbean.setOperateType(new Integer(operateType));
					linkbean.setOperateTime(nowDate);
					linkbean.setToOrgRoleId(dealPerformers[i]);
					linkbean.setOperateDay(calendar.get(Calendar.DATE));
					linkbean.setOperateMonth(calendar.get(Calendar.MONTH) + 1);
					linkbean.setOperateYear(calendar.get(Calendar.YEAR));
					// 保存task数据
					task = (ITask) this.getTaskService().getTaskModelObject()
							.getClass().newInstance();
					task.setId(UUIDHexGenerator.getInstance().getID());
					task.setTaskName("advice");
					task.setTaskDisplayName("阶段通知");
					task.setOperateRoleId(dealPerformers[i]);
					task.setTaskOwner(dealPerformerLeaders[i]);
					task.setFlowName(processTemplateName);
					task.setOperateType(dealPerformerTypes[i]);
					task.setSendTime(main.getSendTime());
					newSaveTask(main, linkbean, task);
					linkbean.setAiid(task.getId());
					this.getLinkService().addLink(linkbean);
				}
			}
		} else if (Integer.parseInt(operateType) == Constants.ACTION_PHASE_BACKTOUP) {
			// 阶段回复
			task = this.getTaskService().getSinglePO(taskId);
			if (task.getTaskName().equals("reply")) {
				BocoLog.info(this, "===优化======已阅===");
				task = (ITask) this.getTaskService().getSinglePO(taskId);
				task.setTaskStatus(Constants.TASK_STATUS_FINISHED);
				this.getTaskService().addTask(task);
			} else {
				BocoLog.info(this, "===优化======阶段回复===");

				String dealPerformer = StaticMethod
						.nullObject2String(operateMap.get("dealPerformer"));
				String dealPerformerLeader = StaticMethod
						.nullObject2String(operateMap
								.get("dealPerformerLeader"));
				String dealPerformerType = StaticMethod
						.nullObject2String(operateMap.get("dealPerformerType"));

				String[] dealPerformers = dealPerformer.split(",");
				String[] dealPerformerLeaders = dealPerformerLeader.split(",");
				String[] dealPerformerTypes = dealPerformerType.split(",");

				for (int i = 0; i < dealPerformers.length; i++) {
					linkbean = (BaseLink) this.getLinkService().getLinkObject()
							.getClass().newInstance();
					SheetBeanUtils.populateEngineMap2Bean(linkbean, linkMap);
					ITask tmptask = (ITask) this.getTaskService().getSinglePO(
							taskId);
					linkbean.setActiveTemplateId(tmptask.getTaskName());
					linkbean.setId(UUIDHexGenerator.getInstance().getID());
					linkbean.setMainId(mainId);
					linkbean.setOperateType(new Integer(operateType));
					linkbean.setOperateTime(nowDate);
					linkbean.setToOrgRoleId(dealPerformers[i]);
					linkbean.setOperateDay(calendar.get(Calendar.DATE));
					linkbean.setOperateMonth(calendar.get(Calendar.MONTH) + 1);
					linkbean.setOperateYear(calendar.get(Calendar.YEAR));
					// 保存task数据
					task = (ITask) this.getTaskService().getTaskModelObject()
							.getClass().newInstance();
					task.setId(UUIDHexGenerator.getInstance().getID());
					task.setTaskName("reply");
					task.setTaskDisplayName("阶段回复");
					task.setOperateRoleId(dealPerformers[i]);
					task.setTaskOwner(dealPerformerLeaders[i]);
					task.setFlowName(processTemplateName);
					task.setOperateType(dealPerformerTypes[i]);
					task.setSendTime(main.getSendTime());
					newSaveTask(main, linkbean, task);
					linkbean.setAiid(task.getId());
					this.getLinkService().addLink(linkbean);
				}
			}
		} else if (copyPerformer.equals("")
				&& Integer.parseInt(operateType) == Constants.ACTION_READCOPY) {
			// 抄送确认
			BocoLog.info(this, "===优化======抄送确认===");
			linkbean = (BaseLink) this.getLinkService().getLinkObject()
					.getClass().newInstance();
			SheetBeanUtils.populateEngineMap2Bean(linkbean, linkMap);
			linkbean.setId(UUIDHexGenerator.getInstance().getID());
			linkbean.setOperateType(new Integer(Constants.ACTION_READCOPY));
			this.getLinkService().addLink(linkbean);
			ITask perTask = this.getTaskService().getTaskByPreLinkid(
					linkbean.getPreLinkId());
			perTask.setTaskStatus(Constants.TASK_STATUS_FINISHED);
			perTask.setCurrentLinkId(linkbean.getId());
			this.getTaskService().addTask(perTask);
		} else if (!copyPerformer.equals("")
				&& Integer.parseInt(operateType) != Constants.ACTION_DRAFT) {
			BocoLog.info(this, "===优化======抄送===");
			// 抄送
			String[] copyPerformers = copyPerformer.split(",");
			// 抄送多人
			String copyPerformerLeader = StaticMethod.nullObject2String(request
					.getParameter("copyPerformerLeader"));
			String copyPerformerType = StaticMethod.nullObject2String(request
					.getParameter("copyPerformerType"));
			String[] copyPerformerLeaders = copyPerformerLeader.split(",");
			String[] copyPerformerTypes = copyPerformerType.split(",");

			for (int i = 0; i < copyPerformers.length; i++) {
				linkbean = (BaseLink) this.getLinkService().getLinkObject()
						.getClass().newInstance();
				SheetBeanUtils.populateEngineMap2Bean(linkbean, linkMap);
				linkbean.setId(UUIDHexGenerator.getInstance().getID());
				linkbean.setOperateType(new Integer(
						Constants.ACTION_MAKECOPYFOR));
				linkbean.setOperateTime(nowDate);
				linkbean.setMainId(mainId);
				linkbean.setToOrgRoleId(copyPerformers[i]);
				linkbean.setOperateDay(calendar.get(Calendar.DATE));
				linkbean.setOperateMonth(calendar.get(Calendar.MONTH) + 1);
				linkbean.setOperateYear(calendar.get(Calendar.YEAR));
				// 保存task数据
				task = (ITask) this.getTaskService().getTaskModelObject()
						.getClass().newInstance();
				task.setTaskName("cc");
				task.setTaskDisplayName("抄送");
				task.setOperateRoleId(copyPerformers[i]);
				task.setTaskOwner(copyPerformerLeaders[i]);
				task.setFlowName(processTemplateName);
				task.setOperateType(copyPerformerTypes[i]);
				task.setSendTime(main.getSendTime());
				newSaveTask(main, linkbean, task);
				linkbean.setAiid(task.getId());
				this.getLinkService().addLink(linkbean);
				// 如果为抄送再抄送，则需要结束上条task
				if (Integer.parseInt(operateType) == Constants.ACTION_READCOPY) {
					ITask perTask = this.getTaskService().getTaskByPreLinkid(
							linkbean.getPreLinkId());
					perTask.setTaskStatus(Constants.TASK_STATUS_FINISHED);
					perTask.setCurrentLinkId(linkbean.getId());
					this.getTaskService().addTask(perTask);
				}
			}
		}
	}

	public void performBatchDeal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub

	}

	public void performCancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * 申明任务
	 */
	public void performClaim(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String beanName = mapping.getAttribute();
		if ("android".equals(StaticMethod.nullObject2String(request
				.getParameter("type")))) {
			beanName = StaticMethod.nullObject2String(request
					.getParameter("beanName"));
		}
		beanName = beanName + "InfoShow";
		ISheetInfoShowService baseSheet = (ISheetInfoShowService) ApplicationContextHolder
				.getInstance().getBean(beanName);
		String taskId = StaticMethod.nullObject2String(request
				.getParameter("TKID"));
		String sheetKey = StaticMethod.nullObject2String(request
				.getParameter("mainId"));
		Map parameterMap = request.getParameterMap();
		HashMap<String, Object> columnMap = new HashMap<String, Object>();

		BaseMain main = this.getMainService().getSingleMainPO(sheetKey);
		BaseLink link = this.getLinkService().getLinkObject().getClass()
				.newInstance();

		columnMap.put("main", main);
		columnMap.put("link", link);
		columnMap.put("operate", this.getPageColumnName());

		Map cloneMap = new HashMap();
		cloneMap.putAll(parameterMap);
		HashMap wpsMap = SheetUtils.actionMapToEngineMap(cloneMap, columnMap);
		dealFlowEngineMap(request, wpsMap);

		HashMap mainMap = (HashMap) wpsMap.get("main");
		HashMap linkMap = (HashMap) wpsMap.get("link");
		HashMap operateMap = (HashMap) wpsMap.get("operate");

		// 调用各工单实现处理数据的方法
		IPreEngineDataHandler preEngineDataHandler = sheetFacadeService
				.getPreEngineDataHandler();
		preEngineDataHandler.prePerformClaimBusiMap(request, mainMap, linkMap,
				operateMap);

		Map<String, Object> dataMap = this.getDataMap(main, mainMap, linkMap,
				operateMap);

		// 调用各工单处理other数据的方法，获取other数据
		List<Object> otherDataList = preEngineDataHandler
				.prePerformClaimOtherDate(request, mainMap,
						(List<BaseLink>) dataMap.get("linkObjList"), operateMap);
		String otherData = SheetBeanUtils.businessDataList2Xml(otherDataList);

		HashMap sessionMap = new HashMap();
		sessionMap.put("userId", "admin");
		sessionMap.put("password", "admin");

		ClaimTaskData claimTaskData = new ClaimTaskData();
		claimTaskData.setLinkData(dataMap.get("linkData").toString());
		claimTaskData.setLinkIds(dataMap.get("linkIds").toString());
		claimTaskData.setMainData(dataMap.get("mainData").toString());
		claimTaskData.setMainId(main.getId());
		claimTaskData.setOperateString(dataMap.get("operateString").toString());
		claimTaskData.setOtherData(otherData);

		// 更改task状态和TaskOwner
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		ITask task = this.getTaskService().getSinglePO(taskId);
		task.setId(taskId);
		task.setTaskStatus("8");
		task.setTaskOwner(sessionform.getUserid());
		this.getTaskService().addTask(task);
		// this.getSheetFacadeService().getBusinessFlowService().claimTask(taskId,
		// claimTaskData, sessionMap);

		request.setAttribute("task", task);
		request.setAttribute("taskStatus", "8");
		// 返回详细信息页面
		baseSheet.showDetailPage(mapping, form, request, response);

	}

	/**
	 * 工单处理
	 */
	public void performDeal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String nowUserId = sessionform.getUserid();
		String taskId = StaticMethod.nullObject2String(request
				.getParameter("TKID"));
		if ("".equals(taskId)) {
			taskId = StaticMethod.nullObject2String(request
					.getParameter("aiid"));
		}
		String operateType = StaticMethod.nullObject2String(request
				.getParameter("operateType"));
		System.out.println("operateType is -----------------------"
				+ operateType);

		String sheetKey = StaticMethod.nullObject2String(request
				.getParameter("mainId"));
		BaseMain main = this.getMainService().getSingleMainPO(sheetKey);
		BaseLink link = this.getLinkService().getLinkObject().getClass()
				.newInstance();

		HashMap<String, Object> columnMap = new HashMap<String, Object>();
		columnMap.put("main", main);
		columnMap.put("link", link);
		columnMap.put("operate", this.getPageColumnName());

		Map parameterMap = request.getParameterMap();
		Map cloneParameterMap = new HashMap();
		cloneParameterMap.putAll(parameterMap);

		HashMap wpsMap = SheetUtils.actionMapToEngineMap(cloneParameterMap,
				columnMap);
		dealFlowEngineMap(request, wpsMap); // 对处理人、抄送人以及审核人的解析

		HashMap mainMap = (HashMap) wpsMap.get("main");
		HashMap linkMap = (HashMap) wpsMap.get("link");
		HashMap operateMap = (HashMap) wpsMap.get("operate");

		// 调用各工单实现处理数据的方法
		IPreEngineDataHandler preEngineDataHandler = sheetFacadeService
				.getPreEngineDataHandler();
		preEngineDataHandler.prePerformDealBusiMap(request, mainMap, linkMap,
				operateMap);

		Map<String, Object> dataMap = this.getDataMap(main, mainMap, linkMap,
				operateMap);

		// ***
		String dealPerformerLeader = StaticMethod.nullObject2String(operateMap
				.get("dealPerformerLeader"));
		String taskName = StaticMethod.nullObject2String(operateMap
				.get("phaseId"));
		String activeName = mapping.getPath().substring(1);
		if (activeName.equals("androidSheetAction")) {
			activeName = request.getParameter("beanName");
		}
		Map<String, String> activeMap = this.getActiveMap(activeName
				.toLowerCase());

		ITask task = (ITask) this.getTaskService().getSinglePO(taskId);
		task.setTaskStatus(Constants.TASK_STATUS_FINISHED);
		this.getTaskService().addTask(task);

		String mainId = main.getId();
		SheetBeanUtils.populate(link, linkMap);
		link.setMainId(mainId);
		link.setOperateUserId(nowUserId);
		link.setOperateRoleId(nowUserId);

		ITask task2 = (ITask) this.getTaskService().getTaskModelObject()
				.getClass().newInstance();
		task2.setTaskDisplayName(activeMap.get(taskName));
		task2.setTaskName(taskName);
		task2.setOperateRoleId(nowUserId);
		if (!"".equals(dealPerformerLeader)) { // 当前派发对象不为空
			task2.setTaskOwner(dealPerformerLeader);
			link.setToOrgUserId(dealPerformerLeader);
			link.setToOrgRoleId(dealPerformerLeader);
		} else {
			if ("Over".equals(taskName)) { // 归档了
				SheetBeanUtils.populate(main, mainMap);
				main.setStatus(new Integer(1));
				this.getMainService().saveOrUpdateMain(main);
				task2.setTaskStatus(Constants.TASK_STATUS_FINISHED);
			} else {
				if (operateType.equals("96")) {
					link.setToOrgUserId(main.getSendUserId());
					link.setToOrgRoleId(main.getSendUserId());
					task2.setTaskOwner(main.getSendUserId());
				} else {
					if (operateType.equals("14")) {
						link.setToOrgUserId(main.getSendUserId());
						link.setToOrgRoleId(main.getSendUserId());
						task2.setTaskOwner(main.getSendUserId());
					} else {
						link.setToOrgUserId(task.getOperateRoleId());
						link.setToOrgRoleId(task.getOperateRoleId());
						task2.setTaskOwner(task.getOperateRoleId());
					}
				}
			}
		}

		this.getLinkService().addLink(link);
		newSaveTask(main, link, task2);
		// 工单公用处理other数据
		// Map otherMap = new HashMap();
		// otherMap.put("methodType", "deal");
		// this.dealOtherData(request,mainMap, operateMap,otherMap);
		//
		// //调用各工单处理other数据的方法，获取other数据
		// List<Object> otherDataList =
		// preEngineDataHandler.prePerformDealOtherDate(request, mainMap,
		// (List<BaseLink>)dataMap.get("linkObjList"), operateMap);
		// String otherData =
		// SheetBeanUtils.businessDataList2Xml(otherDataList);

		// HashMap sessionMap = new HashMap();
		// sessionMap.put("userId", "admin");
		// sessionMap.put("password", "admin");
		//
		// //判断是否是同组处理模式，若是的话，则先取消之前的claim操作
		// String
		// teamFlag=StaticMethod.nullObject2String(request.getParameter("teamFlag"));
		// if(teamFlag.equals("true")){
		// this.getSheetFacadeService().getBusinessFlowService().cancelClaimTask(taskId,
		// null,sessionMap);
		// }
		//
		// FinishTaskData finishTaskData = new FinishTaskData();
		// finishTaskData.setLinkData(dataMap.get("linkData").toString());
		// finishTaskData.setLinkIds(dataMap.get("linkIds").toString());
		// finishTaskData.setMainData(dataMap.get("mainData").toString());
		// finishTaskData.setMainId(main.getId());
		// finishTaskData.setOperateString(dataMap.get("operateString").toString());
		// finishTaskData.setOtherData(otherData);
		// this.getSheetFacadeService().getBusinessFlowService().finishTask(taskId,
		// finishTaskData, sessionMap);

		// 非流程动作：目前包括抄送、阶段回复、阶段通知中进行link和task的保存

		newSaveNonFlowData(request, taskId, wpsMap);

	}

	public void performClaimTaskAtom(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	}

	public void performCreateSubTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	}

	public void performDealAtom(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	}

	public void performDealReplyAccept(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	}

	public void performDealReplyReject(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	}

	/**
	 * 处理工单强制归档、作废等操作
	 */
	public void performFroceHold(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String piid = StaticMethod.nullObject2String(request
				.getParameter("piid"));
		String sheetKey = StaticMethod.nullObject2String(request
				.getParameter("sheetKey"));
		String processTemplateName = StaticMethod.nullObject2String(request
				.getParameter("processTemplateName"));
		String operateName = StaticMethod.nullObject2String(request
				.getParameter("operateName"));
		Map parameterMap = request.getParameterMap();
		HashMap<String, Object> columnMap = new HashMap<String, Object>();
		BaseMain main = this.getMainService().getSingleMainPO(sheetKey);
		BaseLink link = this.getLinkService().getLinkObject().getClass()
				.newInstance();
		String operateType = StaticMethod.nullObject2String(request
				.getParameter("operateType"));
		if (operateType.equals("-12")) {
			main.setStatus(-12);
		} else if (operateType.equals("-13")) {
			main.setStatus(-13);
		} else if (operateType.equals("-14")) {
			main.setStatus(-14);
		} else if (operateType.equals("-14")) {
			main.setStatus(-14);
		}
		columnMap.put("main", main);
		columnMap.put("link", link);
		columnMap.put("operate", this.getPageColumnName());
		System.out.println("=====request Map parentPhaseName:"
				+ StaticMethod.nullObject2String(
						request.getParameter("parentPhaseName"), ""));

		Map cloneMap = new HashMap();
		cloneMap.putAll(parameterMap);
		HashMap wpsMap = SheetUtils.actionMapToEngineMap(cloneMap, columnMap);

		dealFlowEngineMap(request, wpsMap);

		HashMap mainMap = (HashMap) wpsMap.get("main");
		HashMap linkMap = (HashMap) wpsMap.get("link");
		HashMap operateMap = (HashMap) wpsMap.get("operate");

		// 私有数据处理方法
		IPreEngineDataHandler preEngineDataHandler = sheetFacadeService
				.getPreEngineDataHandler();
		preEngineDataHandler.prePerformFroceHold(request, mainMap, linkMap,
				operateMap);

		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String nowUserId = sessionform.getUserid();
		String mainId = main.getId();
		SheetBeanUtils.populate(link, linkMap);
		link.setMainId(mainId);
		link.setOperateUserId(nowUserId);
		link.setOperateRoleId(nowUserId);
		link.setOperateType(main.getStatus());

		this.getLinkService().addLink(link);
		this.getMainService().saveOrUpdateMain(main);
		// Map<String,Object> dataMap = this.getDataMap(main, mainMap, linkMap,
		// operateMap);
		//
		// //新的合作伙伴方法
		// HashMap map = new HashMap();
		// map.put("linkData",dataMap.get("linkData").toString());
		// map.put("mainData",dataMap.get("mainData").toString());
		// map.put("mainId",main.getId());
		// String taskTableName =
		// HibernateConfigurationHelper.getTableName(this.getTaskService().getTaskModelObject().getClass());
		// map.put("taskTableName",taskTableName);
		//
		// HashMap<String,String> sessionMap = new HashMap<String,String>();
		// sessionMap.put("userId", "admin");
		// sessionMap.put("password", "admin");
		//
		// sheetFacadeService.getBusinessFlowService().triggerEvent(piid,
		// processTemplateName,
		// operateName, map, sessionMap);
	}

	public void performHide(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub

	}

	public void performInvokeReply(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub

	}

	public boolean performIsInvokeProcess(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String sheetKey = StaticMethod.nullObject2String(request
				.getParameter("sheetKey"));
		String taskName = StaticMethod.nullObject2String(request
				.getParameter("activeTemplateId"));
		String operateType = StaticMethod.nullObject2String(request
				.getParameter("operateType"));
		String tempUserId = "";
		ITask task = null;
		BocoLog.info(this, "operateType is:" + operateType);
		if (taskName.equals("")) {
			taskName = StaticMethod.nullObject2String(request
					.getParameter("taskName"));
		}
		String taskId = StaticMethod.nullObject2String(
				request.getParameter("taskId"), "");
		if (taskId != null && !taskId.equals("")) {
			task = (ITask) this.getTaskService().getSinglePO(taskId);
		}

		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		if (sessionform != null) {
			tempUserId = sessionform.getUserid();
		}
		// 查询工单互调表
		if (task != null) {
			ITawSheetRelationManager mgr = (ITawSheetRelationManager) ApplicationContextHolder
					.getInstance().getBean("ITawSheetRelationManager");
			List relationAllList = mgr.getSheetByProcessIdAndUserId(sheetKey,
					taskName, tempUserId, task.getProcessId());
			System.out.println("sheetKey=" + sheetKey + "==tempUserId="
					+ tempUserId);
			if (relationAllList != null && relationAllList.size() > 0
					&& !operateType.equals("111")) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public void performNonFlowAtom(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub

	}

	public void performPreCommit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String[] sheetPageName = request.getParameterValues("sheetPageName");
		String[] methodBeanId = request.getParameterValues("methodBeanId");
		String operateType = StaticMethod.nullObject2String(request
				.getParameterValues("operateType"));
		JSONObject jsonRoot = new JSONObject();
		JSONArray data = new JSONArray();
		String status = "";
		if (sheetPageName != null
				&& (!operateType.equals("4") || !operateType.equals("61"))) {
			for (int i = 0; i < sheetPageName.length; i++) {
				if (sheetPageName[i].equals("")) {
					HashMap map = analyseFlowDefine(request, sheetPageName[i]);
					status = StaticMethod.nullObject2String(map.get("status"));
					data.put(map.get("jsonObject"));
				} else {
					IBaseSheet basesheet = (IBaseSheet) ApplicationContextHolder
							.getInstance().getBean(methodBeanId[i]);
					HashMap map = basesheet.analyseFlowDefine(request,
							sheetPageName[i]);
					status = StaticMethod.nullObject2String(map.get("status"));
					data.put(map.get("jsonObject"));
				}
			}

			jsonRoot.put("data", data);
			jsonRoot.put("status", String.valueOf(status));
			JSONUtil.print(response, jsonRoot.toString());
		}

	}

	public void performProcessEvent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub

	}

	public void performQueryHide(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * 工单处理信息的保存
	 */
	public void performSaveDealInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String taskId = StaticMethod.nullObject2String(request
				.getParameter("TKID"));
		String linkId = "";
		String sheetKey = StaticMethod.nullObject2String(request
				.getParameter("mainId"));

		// 从页面上获取处理信息
		Map parameterMap = request.getParameterMap();
		HashMap<String, Object> columnMap = new HashMap<String, Object>();
		BaseMain main = this.getMainService().getSingleMainPO(sheetKey);
		BaseLink link = this.getLinkService().getLinkObject().getClass()
				.newInstance();
		columnMap.put("main", main);
		columnMap.put("link", link);
		columnMap.put("operate", this.getPageColumnName());
		System.out.println("=====request Map parentPhaseName:"
				+ StaticMethod.nullObject2String(
						request.getParameter("parentPhaseName"), ""));

		Map cloneMap = new HashMap();
		cloneMap.putAll(parameterMap);
		HashMap wpsMap = SheetUtils.actionMapToEngineMap(cloneMap, columnMap);

		// 保存处理信息，目前还有点问题，若流程互调时，如何保存被调流程的信息
		Iterator iterator = wpsMap.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();

			if (key.equalsIgnoreCase("main")) {
				HashMap mainHashMap = (HashMap) wpsMap.get(key);
				BaseMain mainModel = (BaseMain) this.getMainService()
						.getMainObject().getClass().newInstance();
				SheetBeanUtils.populate(mainModel, mainHashMap);
				mainModel.setStatus(new Integer(0));
				this.getMainService().addMain(mainModel);
			} else if (key.equalsIgnoreCase("link")) {
				HashMap linkHashMap = (HashMap) wpsMap.get(key);
				BaseLink linkModel = (BaseLink) this.getLinkService()
						.getLinkObject().getClass().newInstance();
				SheetBeanUtils.populate(linkModel, linkHashMap);
				linkModel.setMainId(null);
				linkId = this.getLinkService().addLink(linkModel);
			}
		}
		if (!linkId.equals("")) {
			ITask taskModel = this.getTaskService().getSinglePO(taskId);
			taskModel.setCurrentLinkId(linkId);
			this.getTaskService().addTask(taskModel);
		}
	}

	public void performTransferAtom(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub

	}

	public void performTransferWorkItem(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub

	}

	public void performUpdate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub

	}

	public void setParentTaskOperateWhenRejct(HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub

	}

	public void showPhaseReplyPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * 格式化为数据总集MAP，MAP的value中存的是XML数据
	 * 
	 * @param mainObj
	 * @param mainMap
	 * @param linkMap
	 * @param operateMap
	 * @return
	 * @throws Exception
	 */
	protected Map<String, Object> getDataMap(BaseMain mainObj, HashMap mainMap,
			HashMap linkMap, HashMap operateMap) throws Exception {
		SheetBeanUtils.populateEngineMap2Bean(mainObj, mainMap);
		String mainData = SheetBeanUtils.businessDataMap2Xml(mainObj);
		List<BaseLink> linkObjList = formatLinkMap(mainMap, linkMap, operateMap);
		String linkData = SheetBeanUtils.businessDataList2Xml(linkObjList);
		String linkIds = "";
		for (BaseLink baseLink : linkObjList) {
			if (!"".equals(linkIds)) {
				linkIds = linkIds + ",";
			}
			linkIds += baseLink.getId();
		}
		String operateString = SheetBeanUtils.OperateMap2Xml(operateMap);
		String taskTableName = HibernateConfigurationHelper.getTableName(this
				.getTaskService().getTaskModelObject().getClass());

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("mainData", mainData);
		dataMap.put("linkData", linkData);
		dataMap.put("linkIds", linkIds);
		dataMap.put("operateString", operateString);
		dataMap.put("taskTableName", taskTableName);
		dataMap.put("linkObjList", linkObjList);

		System.out.println("---mainData:" + mainData);
		System.out.println("---linkData:" + linkData);
		System.out.println("---linkIds:" + linkIds);
		System.out.println("---operateString:" + operateString);
		System.out.println("---taskTableName:" + taskTableName);
		System.out.println("---linkObjList:" + linkObjList);

		return dataMap;
	}

	/**
	 * 新增提交
	 */
	public void performAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		String nowUserId = sessionform.getUserid();
		Map parameterMap = request.getParameterMap();
		HashMap<String, Object> columnMap = new HashMap<String, Object>();

		BaseMain main = this.getMainService().getMainObject().getClass()
				.newInstance();
		BaseLink link = this.getLinkService().getLinkObject().getClass()
				.newInstance();
		ITask task = this.getTaskService().getTaskModelObject().getClass()
				.newInstance();
		columnMap.put("main", main);
		columnMap.put("link", link);
		columnMap.put("operate", this.getPageColumnName());

		Map cloneParameterMap = new HashMap();
		cloneParameterMap.putAll(parameterMap);

		HashMap wpsMap = SheetUtils.actionMapToEngineMap(cloneParameterMap,
				columnMap);

		String processTemplateName = StaticMethod.nullObject2String(request
				.getParameter("processTemplateName"));
		String operateName = StaticMethod.nullObject2String(request
				.getParameter("operateName"));
		dealFlowEngineMap(request, wpsMap); // 对处理人、抄送人以及审核人的解析

		HashMap mainMap = (HashMap) wpsMap.get("main");
		HashMap linkMap = (HashMap) wpsMap.get("link");
		HashMap operateMap = (HashMap) wpsMap.get("operate");

		// 调用各工单实现处理数据的方法
		IPreEngineDataHandler preEngineDataHandler = sheetFacadeService
				.getPreEngineDataHandler();
		preEngineDataHandler.prePerformAddBusiMap(request, mainMap, linkMap,
				operateMap);

		Map<String, Object> dataMap = this.getDataMap(main, mainMap, linkMap,
				operateMap);

		// 工单公用处理other数据
		Map otherMap = new HashMap();
		otherMap.put("methodType", "add");
		this.dealOtherData(request, mainMap, operateMap, otherMap);

		// 调用各工单处理other数据的方法，获取other数据
		List<Object> otherDataList = preEngineDataHandler
				.prePerformAddOtherDate(request, mainMap,
						(List<BaseLink>) dataMap.get("linkObjList"), operateMap);
		String otherData = SheetBeanUtils.businessDataList2Xml(otherDataList);

		String dealPerformerLeader = StaticMethod.nullObject2String(operateMap
				.get("dealPerformerLeader"));
		String taskName = StaticMethod.nullObject2String(operateMap
				.get("phaseId"));
		SheetBeanUtils.populate(main, mainMap);
		main.setStatus(new Integer(0));
		this.getMainService().addMain(main);
		String activeName = mapping.getPath().substring(1);
		Map<String, String> activeMap = this.getActiveMap(activeName);
		String mainId = main.getId();
		SheetBeanUtils.populate(link, linkMap);
		link.setMainId(mainId);
		task.setTaskDisplayName(activeMap.get(taskName));
		task.setTaskName(taskName);
		task.setOperateRoleId(nowUserId);

		if ("DraftTask".equals(taskName)) {
			// 草稿
			link.setToOrgUserId(main.getSendUserId());
			link.setToOrgRoleId(main.getSendUserId());
			task.setTaskOwner(main.getSendUserId());

		} else {
			link.setToOrgUserId(dealPerformerLeader);
			link.setToOrgRoleId(dealPerformerLeader);
			task.setTaskOwner(dealPerformerLeader);
		}
		this.getLinkService().addLink(link);

		newSaveTask(main, link, task);
		// link.setMainId(null);
		//
		// t.setCurrentLinkId(linkId);
		// this.getTaskService().addTask(taskModel);

		// //新的合作伙伴方法
		// TriggerProcessData triggerProcessData = new TriggerProcessData();
		// triggerProcessData.setLinkData(dataMap.get("linkData").toString());
		// triggerProcessData.setLinkIds(dataMap.get("linkIds").toString());
		// triggerProcessData.setMainData(dataMap.get("mainData").toString());
		// triggerProcessData.setMainId(main.getId());
		// triggerProcessData.setOperateString(dataMap.get("operateString").toString());
		// triggerProcessData.setOtherData(otherData);
		// triggerProcessData.setTaskTableName(dataMap.get("taskTableName").toString());
		//
		// HashMap sessionMap = new HashMap();
		// sessionMap.put("userId", "admin");
		// sessionMap.put("password", "admin");
		// getBusinessFlowService().triggerProcess(processTemplateName,operateName,triggerProcessData,sessionMap);

		// 非流程动作：目前包括抄送、阶段回复、阶段通知中进行link和task的保存
		this.newSaveNonFlowData(request, "", wpsMap);
	}

	public Map<String, String> getActiveMap(String name) {
		// FlowDefineExplain flowDefineExplain = new FlowDefineExplain(
		// this.getMainService().getFlowTemplateName(),
		// sheetFacadeService.getRoleConfigPath());
		// FlowDefine flowDefine = flowDefineExplain.getFlowDefine();
		Map<String, String> tempMap = new HashMap<String, String>();
		String dictName = "dict-sheet-" + name;

		List dictItems;
		try {
			dictItems = DictMgrLocator.getDictService().getDictItems(
					Util.constituteDictId(dictName, "activeTemplateId"));
			for (Iterator it = dictItems.iterator(); it.hasNext();) {
				DictItemXML dictItemXml = (DictItemXML) it.next();
				tempMap.put(dictItemXml.getItemId().toString(), dictItemXml
						.getItemName().toString());

			}
		} catch (DictServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return tempMap;
	}

	/**
	 * 对处理人、抄送人以及审核人的解析
	 */
	public void dealFlowEngineMap(HttpServletRequest request, Map wpsMap)
			throws Exception {
		// 设置后续处理对象：派给角色 或 派给人员
		String dealPerformerType = DealTypeConfigUtil.getDealTypeByHold(this
				.getMainService().getFlowTemplateName());
		HashMap operateMap = (HashMap) wpsMap.get("operate");
		String phaseId = (String) operateMap.get("phaseId");
		String tmpstr = phaseId.toLowerCase();
		String dealPerformer = StaticMethod.nullObject2String(operateMap
				.get("dealPerformer"));
		if (tmpstr.indexOf("hold") != -1 && dealPerformer.equals("")) {
			HashMap mainMap = (HashMap) wpsMap.get("main");
			ID2NameService service = (ID2NameService) ApplicationContextHolder
					.getInstance().getBean("ID2NameGetServiceCatch");
			String name = service.id2Name(StaticMethod
					.nullObject2String(operateMap.get("dealPerformerLeader")),
					"tawSystemUserDao");
			if (dealPerformerType.equals("0")) {// 派给人员
				if (name.equals("")) {
					operateMap.put("dealPerformer", StaticMethod
							.nullObject2String(mainMap.get("sendRoleId")));
					operateMap.put("dealPerformerLeader", StaticMethod
							.nullObject2String(mainMap.get("sendUserId")));
					operateMap.put("dealPerformerType", StaticMethod
							.nullObject2String(mainMap.get("sendOrgType")));
				}
			} else if (dealPerformerType.equals("1")) {// 派给角色
				if (!name.equals("")
						|| StaticMethod.nullObject2String(
								operateMap.get("dealPerformerLeader")).equals(
								"")) {
					operateMap.put("dealPerformer", StaticMethod
							.nullObject2String(mainMap.get("sendRoleId")));
					operateMap.put("dealPerformerLeader", StaticMethod
							.nullObject2String(mainMap.get("sendRoleId")));
					operateMap.put("dealPerformerType", StaticMethod
							.nullObject2String(mainMap.get("sendOrgType")));
				}
			}
		}
		wpsMap.put("operate", operateMap);

		// 驳回时设置环节时限
		String operateType = StaticMethod.nullObject2String(request
				.getParameter("operateType"));
		HashMap linkMap = (HashMap) wpsMap.get("link");
		HashMap mainMap = (HashMap) wpsMap.get("main");
		if (operateType.equals("4")) {
			Date nodeAcceptLimit = (Date) linkMap.get("nodeAcceptLimit");
			Date nodeCompleteLimit = (Date) linkMap.get("nodeCompleteLimit");
			if (nodeAcceptLimit == null && nodeCompleteLimit == null) {
				String flowTemplateName = this.getMainService()
						.getFlowTemplateName();
				String mainid = (String) mainMap.get("id");
				BaseMain main = this.getMainService().getSingleMainPO(mainid);
				HashMap conditionMap = SheetLimitUtil.getConditionByMapping(
						main, flowTemplateName);

				ISheetDealLimitManager sheetlimitmgr = (ISheetDealLimitManager) ApplicationContextHolder
						.getInstance().getBean("iSheetDealLimitManager");
				conditionMap.put("flowName", flowTemplateName);
				List sheetLimitList = sheetlimitmgr
						.getlevelLimitBySpecials(conditionMap);
				LevelLimit limit = null;
				String levelId = "";
				if (sheetLimitList != null && sheetLimitList.size() > 0) {
					limit = (LevelLimit) sheetLimitList.get(0);
					levelId = limit.getId();
					List stepList = sheetlimitmgr.getStepLimitByLevel(levelId);
					if (stepList != null && stepList.size() > 0) {
						for (int i = 0; i < stepList.size(); i++) {
							StepLimit tmpStepLimit = (StepLimit) stepList
									.get(i);
							String tmptaskName = tmpStepLimit.getTaskName();
							String tmpAcceptLimit = tmpStepLimit
									.getAcceptLimit();
							String tmpCompleteLimit = tmpStepLimit
									.getCompleteLimit();
							if (tmpAcceptLimit == null)
								tmpAcceptLimit = "0";
							if (tmpCompleteLimit == null)
								tmpCompleteLimit = "0";
							if (tmptaskName.equals(phaseId)) {
								Date currentDate = new Date();
								// 将工作时间和休息时间计算进来
								tmpAcceptLimit = ""
										+ SheetLimitUtil
												.getTimeLimitByConfig(
														currentDate,
														0,
														Integer.parseInt(tmpAcceptLimit),
														flowTemplateName);
								tmpCompleteLimit = ""
										+ SheetLimitUtil
												.getTimeLimitByConfig(
														currentDate,
														Integer.parseInt(tmpAcceptLimit),
														Integer.parseInt(tmpCompleteLimit),
														flowTemplateName);
								java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat(
										"yyyy-MM-dd HH:mm:ss");
								String date = dateFormat.format(currentDate);
								Date tempAcceptLimit = SheetUtils
										.stringToDate(StaticMethod.getDateForMinute(
												date,
												Integer.parseInt(tmpAcceptLimit)));
								Date tempCompleteLimit = SheetUtils
										.stringToDate(StaticMethod.getDateForMinute(
												date,
												Integer.parseInt(tmpCompleteLimit)));
								linkMap.put("nodeAcceptLimit", tempAcceptLimit);
								linkMap.put("nodeCompleteLimit",
										tempCompleteLimit);
								break;
							}
						}
					}
				}
			}
			// 新增时赋第一个环节的时限
		} else if (operateType.equals("0") || operateType.equals("3")
				|| operateType.equals("54")) {
			Date nodeAcceptLimit = (Date) linkMap.get("nodeAcceptLimit");
			Date nodeCompleteLimit = (Date) linkMap.get("nodeCompleteLimit");
			Date sheetAcceptLimit = (Date) mainMap.get("sheetAcceptLimit");
			Date sheetCompleteLimit = (Date) mainMap.get("sheetCompleteLimit");
			if (nodeAcceptLimit != null
					&& nodeCompleteLimit != null
					&& sheetAcceptLimit != null
					&& sheetCompleteLimit != null
					&& nodeAcceptLimit.getTime() == sheetAcceptLimit.getTime()
					&& nodeCompleteLimit.getTime() == sheetCompleteLimit
							.getTime()) {
				String flowTemplateName = this.getMainService()
						.getFlowTemplateName();
				BaseMain main = (BaseMain) this.getMainService()
						.getMainObject().getClass().newInstance();
				SheetBeanUtils.populate(main, mainMap);
				HashMap conditionMap = SheetLimitUtil.getConditionByMapping(
						main, flowTemplateName);
				ISheetDealLimitManager sheetlimitmgr = (ISheetDealLimitManager) ApplicationContextHolder
						.getInstance().getBean("iSheetDealLimitManager");
				conditionMap.put("flowName", flowTemplateName);
				List sheetLimitList = sheetlimitmgr
						.getlevelLimitBySpecials(conditionMap);
				LevelLimit limit = null;
				String levelId = "";
				if (sheetLimitList != null && sheetLimitList.size() > 0) {
					limit = (LevelLimit) sheetLimitList.get(0);
					levelId = limit.getId();
					List stepList = sheetlimitmgr.getStepLimitByLevel(levelId);
					if (stepList != null && stepList.size() > 0) {
						for (int i = 0; i < stepList.size(); i++) {
							StepLimit tmpStepLimit = (StepLimit) stepList
									.get(i);
							String tmptaskName = tmpStepLimit.getTaskName();
							String tmpAcceptLimit = tmpStepLimit
									.getAcceptLimit();
							String tmpCompleteLimit = tmpStepLimit
									.getCompleteLimit();
							if (tmpAcceptLimit == null)
								tmpAcceptLimit = "0";
							if (tmpCompleteLimit == null)
								tmpCompleteLimit = "0";
							if (tmptaskName.equals(phaseId)) {
								String localTime = StaticMethod
										.date2String((Date) mainMap
												.get("sendTime"));
								Date currentDate = (Date) mainMap
										.get("sendTime");
								// 将工作时间和休息时间计算进来
								tmpAcceptLimit = ""
										+ SheetLimitUtil
												.getTimeLimitByConfig(
														currentDate,
														0,
														Integer.parseInt(tmpAcceptLimit),
														flowTemplateName);
								tmpCompleteLimit = ""
										+ SheetLimitUtil
												.getTimeLimitByConfig(
														currentDate,
														Integer.parseInt(tmpAcceptLimit),
														Integer.parseInt(tmpCompleteLimit),
														flowTemplateName);
								Date tempAcceptLimit = SheetUtils
										.stringToDate(StaticMethod
												.getDateForMinute(
														localTime,
														Integer.parseInt(tmpAcceptLimit)));
								Date tempCompleteLimit = SheetUtils
										.stringToDate(StaticMethod
												.getDateForMinute(
														localTime,
														Integer.parseInt(tmpCompleteLimit)));
								linkMap.put("nodeAcceptLimit", tempAcceptLimit);
								linkMap.put("nodeCompleteLimit",
										tempCompleteLimit);
								break;
							}
						}
					}
				}
			}
		}
		wpsMap.put("link", linkMap);
	}

	/**
	 * 
	 * @param mainMap
	 * @param linkMap
	 * @param operate
	 * @return
	 * @throws Exception
	 */
	public List<BaseLink> formatLinkMap(final Map mainMap, final Map linkMap,
			final Map operate) throws Exception {
		String dealPerformer = StaticMethod.nullObject2String(operate
				.get("dealPerformer"));
		String dealPerformerType = StaticMethod.nullObject2String(operate
				.get("dealPerformerType"));
		String sheetKey = StaticMethod.nullObject2String(mainMap.get("id"));
		;

		String[] dealPerformers = dealPerformer.split(",");
		String[] dealPerformerTypes = dealPerformerType.split(",");
		HashMap map;

		if (dealPerformers != null && dealPerformers.length > 0) {
			List<BaseLink> linkList = new ArrayList<BaseLink>();
			for (int i = 0; i < dealPerformers.length; i++) {
				map = new HashMap();
				map.putAll(linkMap);
				map.put("mainId", sheetKey);
				map.put("toOrgRoleId", dealPerformers[i]);
				map.put("toOrgType", dealPerformerTypes[i]);
				map.put("id", UUIDHexGenerator.getInstance().getID());
				BaseLink link = this.getLinkService().getLinkObject()
						.getClass().newInstance();
				SheetBeanUtils.populateEngineMap2Bean(link, map);
				linkList.add(link);
			}
			return linkList;
		} else {
			throw new Exception("工单处理对象为空，请检查数据");
		}
	}

	/**
	 * 整合用 保存task数据
	 */
	public void newSaveTask(BaseMain main, BaseLink link, ITask task)
			throws Exception {
		Date nowDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(nowDate);

		if (task.getId() == null)
			task.setId(UUIDHexGenerator.getInstance().getID());
		if (task.getCreateTime() == null)
			task.setCreateTime(nowDate);
		if (task.getTaskStatus() == null)
			task.setTaskStatus(Constants.TASK_STATUS_READY);
		if (task.getProcessId() == null && main.getPiid() != null)
			task.setProcessId(main.getPiid());
		if (task.getSheetKey() == null)
			task.setSheetKey(main.getId());
		if (task.getSheetId() == null)
			task.setSheetId(main.getSheetId());
		if (task.getTitle() == null)
			task.setTitle(main.getTitle());
		if (task.getAcceptTimeLimit() == null)
			task.setAcceptTimeLimit(link.getNodeAcceptLimit());
		if (task.getCompleteTimeLimit() == null)
			task.setCompleteTimeLimit(link.getNodeCompleteLimit());
		if (task.getPreLinkId() == null)
			task.setPreLinkId(link.getId());
		if (task.getIfWaitForSubTask() == null)
			task.setIfWaitForSubTask("false");

		task.setCreateDay(calendar.get(Calendar.DATE));
		task.setCreateMonth(calendar.get(Calendar.MONTH) + 1);
		task.setCreateYear(calendar.get(Calendar.YEAR));

		this.getTaskService().addTask(task);
	}

	/**
	 * 处理工单权限查询
	 */
	public void dealSheetQuery(HttpServletRequest request, HashMap mainMap,
			HashMap operateMap, Map otherMap) {
		PnrDeviceInspectSwitchConfig config = (PnrDeviceInspectSwitchConfig) request
				.getSession().getServletContext()
				.getAttribute("pnrInspect2SwitchConfig");
		if (config.isOpenSheetQueryRights()) {// 判断是否开启工单分权限查询
			String mainTableName = HibernateConfigurationHelper
					.getTableName(this.getMainService().getMainObject()
							.getClass());
			String mainId = StaticMethod.nullObject2String(mainMap.get("id"));
			String dealPerformer = StaticMethod.nullObject2String(operateMap
					.get("dealPerformer"));
			String dealPerformerType = StaticMethod
					.nullObject2String(operateMap.get("dealPerformerType"));
			String deptId = "";

			if (StringUtils.isEmpty(dealPerformer)
					&& StringUtils.isEmpty(dealPerformerType)) {
				return;
			}
			if ("user".equalsIgnoreCase(dealPerformerType)) {// 派给人
				ITawSystemUserManager userMgr = (ITawSystemUserManager) ApplicationContextHolder
						.getInstance().getBean("itawSystemUserManager");
				TawSystemUser user = userMgr.getUserByuserid(dealPerformer);
				deptId = user.getDeptid();
			} else { // 派给部门
				deptId = dealPerformer;
			}

			String provincePnrDept = ""; // 省代维公司部门
			String cityPnrDept = ""; // 地市代维公司部门
			String countryPnrDept = ""; // 区县代维公司部门
			String groupPnrDept = ""; // 小组代维公司部门

			int level = PartnerPrivUtils.getPartnerDeptLevel(deptId);
			if (PartnerPrivUtils.notPartnerDept == level) { // 如果不是代维公司
				return;
			} else {// 如果是代维公司
				if (PartnerPrivUtils.Level_provinceDept == level) {
					provincePnrDept = deptId;
				} else if (PartnerPrivUtils.Level_city == level) {
					provincePnrDept = deptId.substring(0,
							PartnerPrivUtils.getProvinceDeptLength());
					cityPnrDept = deptId;
				} else if (PartnerPrivUtils.Level_County == level) {
					provincePnrDept = deptId.substring(0,
							PartnerPrivUtils.getProvinceDeptLength());
					cityPnrDept = deptId.substring(0,
							PartnerPrivUtils.getCityDeptLength());
					countryPnrDept = deptId;
				} else if (PartnerPrivUtils.Level_group == level) {
					provincePnrDept = deptId.substring(0,
							PartnerPrivUtils.getProvinceDeptLength());
					cityPnrDept = deptId.substring(0,
							PartnerPrivUtils.getCityDeptLength());
					countryPnrDept = deptId.substring(0,
							PartnerPrivUtils.getCountyDeptLength());
					groupPnrDept = deptId;
				}
			}

			IPnrSheetQueryDao pnrSheetQueryDao = (IPnrSheetQueryDao) ApplicationContextHolder
					.getInstance().getBean("pnrSheetQueryDao");
			PnrSheetQuery query = null;
			String methodType = StaticMethod.nullObject2String(otherMap
					.get("methodType"));
			if ("deal".equals(methodType)) { // 处理工单
				query = pnrSheetQueryDao
						.getPnrSheetQuery(mainTableName, mainId);
				if (query == null) { // 如果新增工单派给了移动，处理工单时query就会为空
					query = new PnrSheetQuery();
					query.setSheetType(mainTableName);
					query.setMainId(mainId);
				}
			} else { // 新增工单
				query = new PnrSheetQuery();
				query.setSheetType(mainTableName);
				query.setMainId(mainId);
			}
			query.setProvincePnrDept(provincePnrDept);
			query.setCityPnrDept(cityPnrDept);
			query.setCountryPnrDept(countryPnrDept);
			query.setGroupPnrDept(groupPnrDept);

			pnrSheetQueryDao.saveObject(query);
		}
	}

	/**
	 * 处理除了MAIN、LINK、TASK外的其他数据
	 */
	public void dealOtherData(HttpServletRequest request, HashMap mainMap,
			HashMap operateMap, Map otherMap) {
		// 处理工单权限查询
		this.dealSheetQuery(request, mainMap, operateMap, otherMap);
	}

}
