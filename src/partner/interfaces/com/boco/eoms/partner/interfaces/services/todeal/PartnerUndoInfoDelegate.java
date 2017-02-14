/*package com.boco.eoms.partner.interfaces.services.todeal;

import java.io.IOException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.commons.system.user.service.ITawSystemUserManager;
import com.boco.eoms.eva.mgr.IEvaKpiMgr;
import com.boco.eoms.eva.mgr.IEvaTaskAuditMgr;
import com.boco.eoms.eva.mgr.IEvaTemplateMgr;
import com.boco.eoms.eva.model.EvaTaskAudit;
import com.boco.eoms.eva.model.EvaTemplate;
import com.boco.eoms.eva.util.EvaConstants;
import com.boco.eoms.partner.agreement.mgr.IPnrAgreementAuditMgr;
import com.boco.eoms.partner.agreement.mgr.IPnrAgreementMainMgr;
import com.boco.eoms.partner.agreement.model.PnrAgreementAudit;
import com.boco.eoms.partner.agreement.model.PnrAgreementMain;
import com.boco.eoms.partner.eva.mgr.IPnrEvaAuditInfoMgr;
import com.boco.eoms.partner.feeInfo.mgr.IPnrFeeInfoAuditMgr;
import com.boco.eoms.partner.feeInfo.mgr.IPnrFeeInfoMainMgr;
import com.boco.eoms.partner.feeInfo.mgr.IPnrFeeInfoPayMgr;
import com.boco.eoms.partner.feeInfo.model.PnrFeeInfoAudit;
import com.boco.eoms.partner.feeInfo.model.PnrFeeInfoMain;
import com.boco.eoms.partner.feeInfo.model.PnrFeeInfoPlan;
import com.boco.eoms.partner.tempTask.mgr.IPnrTempTaskAuditMgr;
import com.boco.eoms.partner.tempTask.mgr.IPnrTempTaskMainMgr;
import com.boco.eoms.partner.tempTask.model.PnrTempTaskAudit;
import com.boco.eoms.partner.tempTask.model.PnrTempTaskMain;
import com.boco.eoms.partner.workplan.mgr.IPnrWorkplanAuditMgr;
import com.boco.eoms.partner.workplan.mgr.IPnrWorkplanMainMgr;
import com.boco.eoms.partner.workplan.model.PnrWorkplanAudit;
import com.boco.eoms.partner.workplan.model.PnrWorkplanMain;
import com.ultrapower.casp.client.LoginUtil;
import com.ultrapower.casp.client.config.CaspClientConfig;
import com.ultrapower.casp.common.datatran.data.ticket.TransferTicket;
import com.ultrapower.casp.common.datatran.data.user.UserInfo;

public class PartnerUndoInfoDelegate {
	static Logger logger = Logger.getLogger(PartnerUndoInfoDelegate.class);
	static String GET_TO_DEAL_NUM = "1";
	static String GET_TO_DEAL_INFO = "2";

	// 1、协议待审核列表 方法： agreeUnAuditList
	public static String AGREE_UN_AUDIT_TYPE = "61";

	// 2、考核未审核任务列表 方法：evaUnAuditList
	public static String EVA_UN_AUDIT_TYPE = "62";

	// 3、费用待审核列表 方法：feeUnAuditList
	public static String FEE_UN_AUDIT_TYTE = "63";

	// 4、临时工作待审核列表 方法：tempTaskUnAuditList
	public static String TEMP_TASK_UN_AUDIT_TYPE = "64";

	// 5、工作计划待审核列表 方法：workplanUnAuditList
	public static String WORKPLAN_UN_AUDIT_TYPE = "65";

	// 6、待付费列表 方法：feepayUndoList
	public static String FEEPAY_UN_DO_TYPE = "66";

	*//**
	 * Constructor of the object.
	 *//*

	public PartnerUndoInfoDelegate() {

	}

	public String service(String serviceType, HttpServletRequest request) {

		String appToken = request
				.getParameter(CaspClientConfig.TICKET_PARAM_NAME);// 获取门户传过来的appToken
		System.out.println("appToken===========" + appToken);

		try {
			TawSystemUser user = getRemoteUser(appToken);

			if (user != null) {
				if (GET_TO_DEAL_NUM.equalsIgnoreCase(serviceType)) {

					return createToDealNum(user);
				} else {

					return createDealInfo(request, user);

				}
			}
		} catch (Exception e) {

			e.printStackTrace();
		}

		return "";
	}

	public TawSystemUser getRemoteUser(String tokenStr)
			throws UnknownHostException {
		// 测试获得远程用户
		String remoteUser = "";
		TawSystemUser user = null;

		System.out.print("tokenStr:" + tokenStr);
		try {
			TransferTicket ticket = LoginUtil.getInstance().analysTicket(
					tokenStr);
			UserInfo userInfo = LoginUtil.getInstance().qryUserByTicket(ticket);
			System.out.println("userInfo.getRetCode() :"
					+ userInfo.getRetCode());
			remoteUser = userInfo.getAccountID();
			System.out.println("userId:" + remoteUser + "--"
					+ userInfo.toString());
			if (!"".equals(remoteUser)) {

				ITawSystemUserManager tawRmUserDAO = (ITawSystemUserManager) ApplicationContextHolder
						.getInstance().getBean("itawSystemUserManager");

				user = tawRmUserDAO.getUserByuserid(remoteUser);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return user;
	}

	*//**
	 * 
	 * @param request
	 * @param user
	 * @param basePath
	 * @return
	 * @throws IOException
	 *//*
	public String createDealInfo(HttpServletRequest request, TawSystemUser user)
			throws IOException {
		StringBuffer dealInfo = new StringBuffer(
				"<?xml version=\"1.0\" encoding=\"GB2312\" ?><root>");

		String taskType = request.getParameter("taskType");
		System.out.println("请求参数：" + request.getQueryString());
		Integer pageNum = Integer.parseInt(request.getParameter("pageNum")) - 1;
		Integer pageSize = Integer.parseInt(request.getParameter("pageSize"));
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + request.getContextPath()
				+ "/";

		String portalLoginUrl = basePath
				+ "portalLoginService.jsp?redirectUrl=";
		Map map = null;
		// <?xml version=”1.0” encoding=”GB2312” ?>
		// <root>
		// <pageCond> //分页条件
		// <allCount></allCount> //此用户此类工单的待办总数
		// </pageCond>
		// <workItemList> //待办列表
		// <workItem> //待办记录
		// <title></title> //待办标题
		// <starterName></starterName > //发起人名称
		// <arriveTime></arriveTime > //待办到达时间，格式//2009-06-22 20:26:41
		// <toDoUrl></ toDoUrl > //该工单的待办页面
		// <toReadUrl></toReadUrl > //该工单的只读页面
		// </workItem>
		// <workItem>
		// ……
		// <workItem>
		// ……
		// </ workItemList>
		// <returnCode></returnCode> //操作返回值：1成功，0失败
		// <returnInfo></returnInfo> //返回信息：返回值的附加信息
		// </root>

		// 1、协议待审核列表 方法： agreeUnAuditList
		if (PartnerUndoInfoDelegate.AGREE_UN_AUDIT_TYPE.equals(taskType)) {

			map = getAgreeUnAuditList(user, pageNum, pageSize);
			dealInfo.append("<pageCond><allCount>" + map.get("total")
					+ "</allCount></pageCond>");
			dealInfo.append("<workItemList>");

			List list = (List) map.get("result");

			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				PnrAgreementAudit pnrAgreementAudit = (PnrAgreementAudit) iterator
						.next();
				dealInfo.append("<workItem>   ");
				dealInfo.append("<title>"
						+ ((PnrAgreementMain) getObject(taskType,
								pnrAgreementAudit.getAgreementId()))
								.getAgreementName() + "</title>");
				dealInfo.append("<starterName>"
						+ ((PnrAgreementMain) getObject(taskType,
								pnrAgreementAudit.getAgreementId()))
								.getCreateUser() + "</starterName >");
				dealInfo.append("<arriveTime>"
						+ pnrAgreementAudit.getOperateTimeString()
						+ "</arriveTime >");
				String viewUrl = portalLoginUrl
						+ URLEncoder
								.encode(basePath
										+ "partner/agreement/pnrAgreementAudits.do?method=audit&agreementId="
										+ pnrAgreementAudit.getAgreementId()
										+ "&id=" + pnrAgreementAudit.getId()
										+ "&type="
										+ pnrAgreementAudit.getType());
				dealInfo.append("<toDoUrl>" + viewUrl + "</toDoUrl>");
				dealInfo.append("<toReadUrl>" + viewUrl + "</toReadUrl>");
				dealInfo.append("</workItem>   ");

			}

			dealInfo.append("</workItemList>");

		}
		// 2、考核未审核任务列表 方法：evaUnAuditList
		else if (PartnerUndoInfoDelegate.EVA_UN_AUDIT_TYPE.equals(taskType)) {
			map = getEvaUnAuditList(user, pageNum, pageSize);
			dealInfo.append("<pageCond><allCount>" + map.get("total")
					+ "</allCount></pageCond>");
			dealInfo.append("<workItemList>");

			List list = (List) map.get("result");

			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				EvaTaskAudit evaTaskAudit = (EvaTaskAudit) iterator.next();
				dealInfo.append("<workItem>   ");
				dealInfo.append("<title>" + evaTaskAudit.getTemplateName()
						+ "</title>");
				dealInfo.append("<starterName>" + evaTaskAudit.getAuditUser()
						+ "</starterName >");
				dealInfo.append("<arriveTime>" + evaTaskAudit.getAuditTime()
						+ "</arriveTime >");
				String viewUrl = portalLoginUrl
						+ URLEncoder
								.encode("eva/evaTaskAudit.do?method=auditDetail&id="
										+ evaTaskAudit.getId()
										+ "&taskId="
										+ evaTaskAudit.getTaskId()
										+ "&templateId="
										+ evaTaskAudit.getTemplateId()
										+ "&timeStr="
										+ evaTaskAudit.getAuditTimeStr()
										+ "&partner="
										+ evaTaskAudit.getPartner() + "");
				dealInfo.append("<toDoUrl>" + viewUrl + "</toDoUrl>");
				dealInfo.append("<toReadUrl>" + viewUrl + "</toReadUrl ");
				dealInfo.append("</workItem>   ");

			}

			dealInfo.append("</workItemList>");
		}

		// 3、费用待审核列表 方法：feeUnAuditList
		else if (PartnerUndoInfoDelegate.FEE_UN_AUDIT_TYTE.equals(taskType)) {
			map = getFeeUnAuditList(user, pageNum, pageSize);
			dealInfo.append("<pageCond><allCount>" + map.get("total")
					+ "</allCount></pageCond>");
			dealInfo.append("<workItemList>");

			List list = (List) map.get("result");

			for (Iterator iterator = list.iterator(); iterator.hasNext();) {

				PnrFeeInfoAudit pnrFeeInfoAudit = (PnrFeeInfoAudit) iterator
						.next();
				dealInfo.append("<workItem>   ");
				dealInfo.append("<title>"
						+ ((PnrFeeInfoMain) getObject(taskType, pnrFeeInfoAudit
								.getFeeId())).getFeeName() + "</title>");
				dealInfo.append("<starterName>"
						+ ((PnrFeeInfoMain) getObject(taskType, pnrFeeInfoAudit
								.getFeeId())).getCreateUser()
						+ "</starterName >");
				dealInfo.append("<arriveTime>"
						+ pnrFeeInfoAudit.getOperateTime() + "</arriveTime >");
				String viewUrl = portalLoginUrl
						+ URLEncoder
								.encode("partner/feeInfo/pnrFeeInfoMains.do?method=audit&"
										+ "feeId="
										+ pnrFeeInfoAudit.getFeeId()
										+ "&id=" + pnrFeeInfoAudit.getId());
				dealInfo.append("<toDoUrl>" + viewUrl + "</toDoUrl>");
				dealInfo.append("<toReadUrl>" + viewUrl + "</toReadUrl ");
				dealInfo.append("</workItem>   ");

			}

			dealInfo.append("</workItemList>");
		}
		// 4、临时工作待审核列表 方法：tempTaskUnAuditList
		else if (PartnerUndoInfoDelegate.TEMP_TASK_UN_AUDIT_TYPE
				.equals(taskType)) {
			map = getTempTaskUnAuditList(user, pageNum, pageSize);
			dealInfo.append("<pageCond><allCount>" + map.get("total")
					+ "</allCount></pageCond>");
			dealInfo.append("<workItemList>");

			List list = (List) map.get("result");

			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				PnrTempTaskAudit pnrTempTaskAudit = (PnrTempTaskAudit) iterator
						.next();
				dealInfo.append("<workItem>   ");
				dealInfo.append("<title>"
						+ ((PnrTempTaskMain) getObject(taskType,
								pnrTempTaskAudit.getTempTaskId()))
								.getTempTaskName() + "</title>");
				dealInfo.append("<starterName>"
						+ ((PnrTempTaskMain) getObject(taskType,
								pnrTempTaskAudit.getTempTaskId()))
								.getCreateUser() + "</starterName >");
				dealInfo.append("<arriveTime>"
						+ pnrTempTaskAudit.getOperateTime() + "</arriveTime >");
				String viewUrl = portalLoginUrl
						+ URLEncoder
								.encode("partner/tempTask/pnrTempTaskMains.do?method=detail"
										+ "&id=" + pnrTempTaskAudit.getId());
				dealInfo.append("<toDoUrl>" + viewUrl + "</toDoUrl>");
				dealInfo.append("<toReadUrl>" + viewUrl + "</toReadUrl ");
				dealInfo.append("</workItem>   ");

			}

			dealInfo.append("</workItemList>");
		}
		// 5、工作计划待审核列表 方法：workplanUnAuditList
		else if (PartnerUndoInfoDelegate.WORKPLAN_UN_AUDIT_TYPE
				.equals(taskType)) {
			map = getWorkplanUnAuditList(user, pageNum, pageSize);
			dealInfo.append("<pageCond><allCount>" + map.get("total")
					+ "</allCount></pageCond>");
			dealInfo.append("<workItemList>");

			List list = (List) map.get("result");

			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				PnrWorkplanAudit pnrWorkplanAudit = (PnrWorkplanAudit) iterator
						.next();
				dealInfo.append("<workItem>   ");
				dealInfo.append("<title>"
						+ ((PnrWorkplanMain) getObject(taskType,
								pnrWorkplanAudit.getWorkplanId()))
								.getWorkplanName() + "</title>");
				dealInfo.append("<starterName>"
						+ ((PnrWorkplanMain) getObject(taskType,
								pnrWorkplanAudit.getWorkplanId()))
								.getCreateUser() + "</starterName >");
				dealInfo.append("<arriveTime>"
						+ pnrWorkplanAudit.getOperateTime() + "</arriveTime >");
				String viewUrl = portalLoginUrl
						+ URLEncoder
								.encode("partner/workplan/pnrWorkplanMains.do?method=audit"
										+ "&workplanId="
										+ pnrWorkplanAudit.getWorkplanId()
										+ "&id=" + pnrWorkplanAudit.getId());
				dealInfo.append("<toDoUrl>" + viewUrl + "</toDoUrl>");
				dealInfo.append("<toReadUrl>" + viewUrl + "</toReadUrl ");
				dealInfo.append("</workItem>   ");

			}

			dealInfo.append("</workItemList>");
		}
		// 6、待付费列表 方法：feepayUndoList
		else if (PartnerUndoInfoDelegate.FEEPAY_UN_DO_TYPE.equals(taskType)) {
			map = getFeepayUndoList(user, pageNum, pageSize);
			dealInfo.append("<pageCond><allCount>" + map.get("total")
					+ "</allCount></pageCond>");
			dealInfo.append("<workItemList>");

			List list = (List) map.get("result");

			for (Iterator iterator = list.iterator(); iterator.hasNext();) {
				PnrFeeInfoPlan pnrFeeInfoPlan = (PnrFeeInfoPlan) iterator
						.next();
				dealInfo.append("<workItem>   ");
				dealInfo.append("<title>"
						+ ((PnrFeeInfoMain) getObject(taskType, pnrFeeInfoPlan
								.getFeeId())).getFeeName() + "</title>");
				dealInfo.append("<starterName>"
						+ ((PnrFeeInfoMain) getObject(taskType, pnrFeeInfoPlan
								.getFeeId())).getCreateUser()
						+ "</starterName >");
				dealInfo.append("<arriveTime>"
						+ pnrFeeInfoPlan.getPlanPayTime() + "</arriveTime >");
				String viewUrl = portalLoginUrl
						+ URLEncoder
								.encode("partner/feeInfo/pnrFeeInfoMains.do?method=detail"
										+ "&id=" + pnrFeeInfoPlan.getId());
				dealInfo.append("<toDoUrl>" + viewUrl + "</toDoUrl>");
				dealInfo.append("<toReadUrl>" + viewUrl + "</toReadUrl ");
				dealInfo.append("</workItem>   ");

			}

			dealInfo.append("</workItemList>");
		}

		dealInfo
				.append("<returnCode>1</returnCode><returnInfo>获得待办信息列表成功</returnInfo> </root>");
		return dealInfo.toString();

	}

	*//**
	 * 
	 * 
	 * 
	 * @param tokenStr
	 * @return
	 * @throws IOException
	 *//*
	public String createToDealNum(TawSystemUser user) throws Exception {

		StringBuffer toDealNums = new StringBuffer();

		// 1、协议待审核列表 方法： agreeUnAuditList
		toDealNums.append("<toDealNum>	<taskType>"
				+ PartnerUndoInfoDelegate.AGREE_UN_AUDIT_TYPE
				+ "</taskType><toDoNum>"
				+ getAgreeUnAuditList(user, 0, 1).get("total")
				+ "</toDoNum ></toDealNum>");

		// 2、考核未审核任务列表 方法：evaUnAuditList
		toDealNums.append("<toDealNum>	<taskType>"
				+ PartnerUndoInfoDelegate.EVA_UN_AUDIT_TYPE
				+ "</taskType><toDoNum>"
				+ getEvaUnAuditList(user, 0, 1).get("total")
				+ "</toDoNum ></toDealNum>");

		// 3、费用待审核列表 方法：feeUnAuditList
		toDealNums.append("<toDealNum>	<taskType>"
				+ PartnerUndoInfoDelegate.FEE_UN_AUDIT_TYTE
				+ "</taskType><toDoNum>"
				+ getFeeUnAuditList(user, 0, 1).get("total")
				+ "</toDoNum ></toDealNum>");

		// 4、临时工作待审核列表 方法：tempTaskUnAuditList
		toDealNums.append("<toDealNum>	<taskType>"
				+ PartnerUndoInfoDelegate.TEMP_TASK_UN_AUDIT_TYPE
				+ "</taskType><toDoNum>"
				+ getTempTaskUnAuditList(user, 0, 1).get("total")
				+ "</toDoNum ></toDealNum>");

		// 5、工作计划待审核列表 方法：workplanUnAuditList
		toDealNums.append("<toDealNum>	<taskType>"
				+ PartnerUndoInfoDelegate.WORKPLAN_UN_AUDIT_TYPE
				+ "</taskType><toDoNum>"
				+ getWorkplanUnAuditList(user, 0, 1).get("total")
				+ "</toDoNum ></toDealNum>");

		// 6、待付费列表 方法：feepayUndoList

		toDealNums.append("<toDealNum>	<taskType>"
				+ PartnerUndoInfoDelegate.FEEPAY_UN_DO_TYPE
				+ "</taskType><toDoNum>"
				+ getFeepayUndoList(user, 0, 1).get("total")
				+ "</toDoNum ></toDealNum>");

		String str = "<?xml version=\"1.0\" encoding=\"GB2312\"?><root><toDealNumList>"
				+ toDealNums.toString()
				+ " </toDealNumList><returnCode>1</returnCode>  <returnInfo>获得合作伙伴待办信息成功</returnInfo>  </root>";
		return str;
	}

	*//**
	 * 1 协议待审核列表
	 * 
	 * @param user
	 * @param pageNum
	 * @param pageSize
	 * @return
	 *//*
	public Map getAgreeUnAuditList(TawSystemUser user, Integer pageNum,
			Integer pageSize) {
		IPnrAgreementAuditMgr pnrAgreementAuditMgr = (IPnrAgreementAuditMgr) ApplicationContextHolder
				.getInstance().getBean("pnrAgreementAuditMgr");

		Map map = pnrAgreementAuditMgr.getPnrAgreementUnAudits(pageNum,
				pageSize, user.getUserid(), user.getDeptid(), "");

		// List unAuditList = (List) map.get("result");
		// request.setAttribute("unAuditList", unAuditList);
		// request.setAttribute("resultSize", map.get("total"));
		// request.setAttribute("pageSize", pageSize);
		// return mapping.findForward("agreeUnAuditList");
		return map;

	}

	*//**
	 * 
	 * 2、考核未审核任务列表
	 * 
	 * @param user
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws Exception
	 *//*
	public Map getEvaUnAuditList(TawSystemUser user, Integer pageNum,
			Integer pageSize) {

		IEvaTaskAuditMgr auditAuditMgr = (IEvaTaskAuditMgr) getBean("IevaTaskAuditMgr");
		String hSql = " where auditResult='" + EvaConstants.AUDIT_WAIT
				+ "' and  ((auditOrgType = 'user' and auditOrg = '"
				+ user.getUserid()
				+ "') or (auditOrgType = 'dept' and auditOrg = '"
				+ user.getDeptid() + "')) ";
		Map map = auditAuditMgr.getEvaTaskAuditByOrgType(pageNum, pageSize,
				hSql);
		List list = (List) map.get("result");
		// 查询出模板Id的模板名称，存在List集合中
		// 查村出指标Id对应的指标名称，存在List集合中
		IEvaTemplateMgr templateMgr = (IEvaTemplateMgr) getBean("IevaTemplateMgr");
		IEvaKpiMgr kpiMgr = (IEvaKpiMgr) getBean("IevaKpiMgr");
		Iterator iter = list.iterator();
		while (iter.hasNext()) {
			EvaTaskAudit taskAudit = (EvaTaskAudit) iter.next();
			if (!("".equals(taskAudit.getTemplateId()) || null == taskAudit
					.getTemplateId())) {
				EvaTemplate template = null;
				template = templateMgr.getTemplate(taskAudit.getTemplateId());
				taskAudit.setTemplateName(template.getTemplateName());
			}

		}
		// request.setAttribute(EvaConstants.TASK_AUDIT_LIST, list);
		// request.setAttribute("resultSize", map.get("total"));
		// request.setAttribute("pageSize", pageSize);
		// return mapping.findForward("evaUnAuditList");
		return map;
	}

	*//**
	 * 3 得到费用待审核列表
	 * 
	 * @param user
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws Exception
	 *//*
	public Map getFeeUnAuditList(TawSystemUser user, Integer pageNum,
			Integer pageSize) {

		IPnrFeeInfoAuditMgr pnrFeeInfoAuditMgr = (IPnrFeeInfoAuditMgr) getBean("pnrFeeInfoAuditMgr");
		Map map = (Map) pnrFeeInfoAuditMgr.getPnrFeeInfoUnAudits(pageNum,
				pageSize, user.getUserid(), user.getDeptid(), "");
		// List unAuditList = (List) map.get("result");
		// request.setAttribute("unAuditList", unAuditList);
		// request.setAttribute("resultSize", map.get("total"));
		// request.setAttribute("pageSize", pageSize);
		// return mapping.findForward("feeUnAuditList");
		return map;
	}

	*//**
	 * 
	 * 4、临时工作待审核列表 方法：tempTaskUnAuditList
	 * 
	 * @param user
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws Exception
	 *//*
	public Map getTempTaskUnAuditList(TawSystemUser user, Integer pageNum,
			Integer pageSize) {
		IPnrTempTaskAuditMgr pnrTempTaskAuditMgr = (IPnrTempTaskAuditMgr) getBean("pnrTempTaskAuditMgr");
		Map map = (Map) pnrTempTaskAuditMgr.getPnrTempTaskUnAudits(pageNum,
				pageSize, user.getUserid(), user.getDeptid());
		// List unAuditList = (List) map.get("result");
		// request.setAttribute("unAuditList", unAuditList);
		// request.setAttribute("resultSize", map.get("total"));
		// request.setAttribute("pageSize", pageSize);
		// return mapping.findForward("tempTaskUnAuditList");

		return map;
	}

	*//**
	 * 5 工作计划待审核列表 方法：workplanUnAuditList
	 * 
	 * @param user
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws Exception
	 *//*
	public Map getWorkplanUnAuditList(TawSystemUser user, Integer pageNum,
			Integer pageSize) {

		IPnrWorkplanAuditMgr pnrWorkplanAuditMgr = (IPnrWorkplanAuditMgr) getBean("pnrWorkplanAuditMgr");
		Map map = (Map) pnrWorkplanAuditMgr.getPnrWorkplanUnAudits(pageNum,
				pageSize, user.getUserid(), "1");
		List unAuditList = (List) map.get("result");
		// request.setAttribute("unAuditList", unAuditList);
		// request.setAttribute("resultSize", map.get("total"));
		// request.setAttribute("pageSize", pageSize);
		// return mapping.findForward("workplanUnAuditList");
		return map;
	}

	// 6、待付费列表 方法：feepayUndoList

	public Map getFeepayUndoList(TawSystemUser user, Integer pageNum,
			Integer pageSize) {

		IPnrFeeInfoMainMgr pnrFeeInfoMainMgr = (IPnrFeeInfoMainMgr) getBean("pnrFeeInfoMainMgr");
		IPnrFeeInfoPayMgr pnrFeeInfoPayMgr = (IPnrFeeInfoPayMgr) getBean("pnrFeeInfoPayMgr");
		// 得到待执行项
		Map map = (Map) pnrFeeInfoPayMgr.getPnrFeeInfoUndoForInterface(pageNum, pageSize,
				user.getUserid(), user.getDeptid());
		List list = (List) map.get("result");
		Map planNum = new HashMap();
		PnrFeeInfoPlan pnrFeeInfoPlan = new PnrFeeInfoPlan();
		for (int i = 0; i < list.size(); i++) {
			pnrFeeInfoPlan = (PnrFeeInfoPlan) list.get(i);
			planNum.put(pnrFeeInfoPlan.getFeeId(), String
					.valueOf(StaticMethod.nullObject2int(planNum
							.get(pnrFeeInfoPlan.getFeeId()), 0) + 1));
		}
		// request.setAttribute("planNum", planNum);
		// request.setAttribute(PnrFeeInfoConstants.PNRFEEINFOPLAN_LIST, list);
		// request.setAttribute("resultSize", map.get("total"));
		// request.setAttribute("pageSize", pageSize);
		// return mapping.findForward("feepayUndoList");
		return map;
	}

	public Object getBean(String beanName) {
		return ApplicationContextHolder.getInstance().getBean(beanName);
	}

	public Object getObject(String taskType, String objectId) {
		if (AGREE_UN_AUDIT_TYPE.equals(taskType)) {
			IPnrAgreementMainMgr iPnrAgreementMainMgr = (IPnrAgreementMainMgr) getBean("pnrAgreementMainMgr");
			return iPnrAgreementMainMgr.getPnrAgreementMain(objectId);
		} else if (EVA_UN_AUDIT_TYPE.equals(taskType)) {
			IPnrEvaAuditInfoMgr iPnrEvaAuditInfoMgr = (IPnrEvaAuditInfoMgr) getBean("pnrEvaAuditInfoMgr");
			return iPnrEvaAuditInfoMgr.getPnrEvaAuditInfo(objectId);
		} else if (FEE_UN_AUDIT_TYTE.equals(taskType)) {
			IPnrFeeInfoMainMgr iPnrFeeInfoMainMgr = (IPnrFeeInfoMainMgr) getBean("pnrFeeInfoMainMgr");
			return iPnrFeeInfoMainMgr.getPnrFeeInfoMain(objectId);
		} else if (TEMP_TASK_UN_AUDIT_TYPE.equals(taskType)) {
			IPnrTempTaskMainMgr iPnrTempTaskMainMgr = (IPnrTempTaskMainMgr) getBean("pnrTempTaskMainMgr");
			return iPnrTempTaskMainMgr.getPnrTempTaskMain(objectId);
		} else if (WORKPLAN_UN_AUDIT_TYPE.equals(taskType)) {
			IPnrWorkplanMainMgr iPnrWorkplanMainMgr = (IPnrWorkplanMainMgr) getBean("pnrWorkplanMainMgr");
			return iPnrWorkplanMainMgr.getPnrWorkplanMain(objectId);
		} else if (FEEPAY_UN_DO_TYPE.equals(taskType)) {
			IPnrFeeInfoMainMgr iPnrFeeInfoMainMgr = (IPnrFeeInfoMainMgr) getBean("pnrFeeInfoMainMgr");
			return iPnrFeeInfoMainMgr.getPnrFeeInfoMain(objectId);
		}

		return "";
	}
}
*/