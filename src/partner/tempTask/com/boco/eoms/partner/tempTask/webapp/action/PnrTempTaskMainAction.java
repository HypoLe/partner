package com.boco.eoms.partner.tempTask.webapp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.dict.service.ID2NameService;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.commons.system.user.service.ITawSystemUserManager;
import com.boco.eoms.commons.system.user.service.IUserMgr;
import com.boco.eoms.eva.mgr.IEvaKpiMgr;
import com.boco.eoms.eva.mgr.IEvaKpiTempMgr;
import com.boco.eoms.eva.mgr.IEvaTemplateMgr;
import com.boco.eoms.eva.mgr.IEvaTreeMgr;
import com.boco.eoms.eva.model.EvaKpi;
import com.boco.eoms.eva.model.EvaTemplate;
import com.boco.eoms.eva.model.EvaTree;
import com.boco.eoms.eva.util.DateTimeUtil;
import com.boco.eoms.eva.util.EvaConstants;
import com.boco.eoms.partner.tempTask.mgr.IPnrTempTaskAuditMgr;
import com.boco.eoms.partner.tempTask.mgr.IPnrTempTaskExeMgr;
import com.boco.eoms.partner.tempTask.mgr.IPnrTempTaskMainMgr;
import com.boco.eoms.partner.tempTask.mgr.IPnrTempTaskWorkMgr;
import com.boco.eoms.partner.tempTask.model.PnrTempTaskAudit;
import com.boco.eoms.partner.tempTask.model.PnrTempTaskExe;
import com.boco.eoms.partner.tempTask.model.PnrTempTaskMain;
import com.boco.eoms.partner.tempTask.model.PnrTempTaskWork;
import com.boco.eoms.partner.tempTask.util.PnrTempTaskConstants;
import com.boco.eoms.partner.tempTask.webapp.form.PnrTempTaskMainForm;

/**
 * <p>
 * Title:合作伙伴临时任务管理
 * </p>
 * <p>
 * Description:合作伙伴临时任务管理主表信息
 * </p>
 * <p>
 * Mon Apr 26 14:09:01 CST 2010
 * </p>
 * 
 * @moudle.getAuthor() daizhigang
 * @moudle.getVersion() 1.0
 * 
 */
public final class PnrTempTaskMainAction extends BaseAction {
 
	/**
	 * 未指定方法时默认调用的方法
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
		return search(mapping, form, request, response);
	}
 	
 	/**
	 * 新增合作伙伴临时任务管理
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("edit");
	}
	
	/**
	 * 修改合作伙伴临时任务管理
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
 /*   public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IPnrTempTaskMainMgr pnrTempTaskMainMgr = (IPnrTempTaskMainMgr) getBean("pnrTempTaskMainMgr");
		IPnrTempTaskWorkMgr pnrTempTaskWorkMgr = (IPnrTempTaskWorkMgr) getBean("pnrTempTaskWorkMgr");
		IPnrAgreementWorkMgr pnrAgreementWorkMgr = (IPnrAgreementWorkMgr) getBean("pnrAgreementWorkMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		String agreementId = StaticMethod.null2String(request.getParameter("agreementId"));
		String agreementNO = StaticMethod.null2String(request.getParameter("agreementNO"));
		PnrTempTaskMain pnrTempTaskMain = pnrTempTaskMainMgr.getPnrTempTaskMain(id);
		List workList = new ArrayList();
		PnrTempTaskMainForm pnrTempTaskMainForm = (PnrTempTaskMainForm) convert(pnrTempTaskMain);
		if(!"".equals(agreementId)){
//			pnrTempTaskMainForm.setAgreementId(agreementId);
//			pnrTempTaskMainForm.setAgreementNO(agreementNO);
			List agreementWorkList = pnrAgreementWorkMgr.getPnrAgreementWorks(agreementId);
			PnrAgreementWork pnrAgreementWork = null;
			for(int i=0;i<agreementWorkList.size();i++){
				pnrAgreementWork = (PnrAgreementWork)agreementWorkList.get(i);
				PnrTempTaskWork pnrTempTaskWork = new PnrTempTaskWork();
				pnrTempTaskWork.setStartTime(pnrAgreementWork.getStartTime());
				pnrTempTaskWork.setEndTime(pnrAgreementWork.getEndTime());
				pnrTempTaskWork.setWorkContent(pnrAgreementWork.getWorkContent());
				workList.add(pnrTempTaskWork);
			}
		}else{
			workList = pnrTempTaskWorkMgr.getPnrTempTaskWorks(id);
		}
		updateFormBean(mapping, request, pnrTempTaskMainForm);
		request.setAttribute("workList", workList);
		if(workList!=null){
			request.setAttribute("workListSize", workList.size());
		}
		return mapping.findForward("edit");
	}
*/
	/**
	 * 查看合作伙伴临时任务管理
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
    	TawSystemSessionForm sessionform = this.getUser(request);
    	String paruserId =StaticMethod.null2String(sessionform.getUserid());
    	String dept = StaticMethod.null2String(sessionform.getDeptid());
		IPnrTempTaskMainMgr pnrTempTaskMainMgr = (IPnrTempTaskMainMgr) getBean("pnrTempTaskMainMgr");
		IPnrTempTaskWorkMgr pnrTempTaskWorkMgr = (IPnrTempTaskWorkMgr) getBean("pnrTempTaskWorkMgr");
		IPnrTempTaskAuditMgr pnrTempTaskAuditMgr = (IPnrTempTaskAuditMgr) getBean("pnrTempTaskAuditMgr");
//		benweiwei 2010-5-8
		IPnrTempTaskExeMgr pnrTempTaskExeMgr = (IPnrTempTaskExeMgr) getBean("pnrTempTaskExeMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		PnrTempTaskMain pnrTempTaskMain = pnrTempTaskMainMgr.getPnrTempTaskMain(id);
		List workList = pnrTempTaskWorkMgr.getPnrTempTaskWorks(id);
		List auditList = pnrTempTaskAuditMgr.getPnrTempTaskAudits(id);
		List exeList = pnrTempTaskExeMgr.getPnrTempTaskExes(id);
		PnrTempTaskMainForm pnrTempTaskMainForm = (PnrTempTaskMainForm) convert(pnrTempTaskMain);
		updateFormBean(mapping, request, pnrTempTaskMainForm);
		ArrayList workExeList = new ArrayList();
		Map map = new HashMap();
		for(int i = 0 ; i<workList.size();i++){
			PnrTempTaskWork pnrTempTaskWork = (PnrTempTaskWork)workList.get(i);
			if("1".equals(pnrTempTaskWork.getWorkFlag())){
				workExeList.add(pnrTempTaskWork);
				map.put(pnrTempTaskWork.getId(), pnrTempTaskWork);
			}
		}
		for(int i = 0 ; i<exeList.size();i++){
			PnrTempTaskExe pnrTempTaskExe = (PnrTempTaskExe)exeList.get(i);
			if(map.containsKey(pnrTempTaskExe.getWorkId())){
				map.put(pnrTempTaskExe.getWorkId(), pnrTempTaskExe.getExeContent());
			}
		}
		if("user".equals(pnrTempTaskMainForm.getToEvaOrgType())){
//			暂时考核只到部门
			String userid = pnrTempTaskMainForm.getToEvaOrgId();
			ITawSystemUserManager mgrflush = (ITawSystemUserManager) getBean("ItawSystemUserManagerFlush");
			TawSystemUser tawSystemUser =  mgrflush.getUserByuserid(userid);
			if(dept.equals(tawSystemUser.getDeptid())){
				request.setAttribute("result", true);
			}else{
				request.setAttribute("result", false);
			}
			request.setAttribute("evaDeptId", tawSystemUser.getDeptid());
		} else {
			if(dept.equals(pnrTempTaskMainForm.getToEvaOrgId())){
				request.setAttribute("result", true);
			}else{
				request.setAttribute("result", false);
			}
			request.setAttribute("evaDeptId", pnrTempTaskMainForm.getToEvaOrgId());
		}
		
		if("user".equals(pnrTempTaskMainForm.getToOrgType())){
			String userid = pnrTempTaskMainForm.getToOrgId();
			ITawSystemUserManager mgrflush = (ITawSystemUserManager) getBean("ItawSystemUserManagerFlush");
			TawSystemUser tawSystemUser =  mgrflush.getUserByuserid(userid);
			request.setAttribute("parDeptId", tawSystemUser.getDeptid());
		} else {
			String pardeptid = pnrTempTaskMainForm.getToOrgId();
			request.setAttribute("parDeptId", pardeptid);
		}		
		ID2NameService mgr = (ID2NameService) getBean("id2nameService");
		String name = mgr.id2Name(pnrTempTaskMainForm.getEvaTemplateId(),"evaTemplateDao");
		request.setAttribute("unName", name);
		request.setAttribute("workExeList", workExeList);
		request.setAttribute("workExeListsize", workExeList.size());
		request.setAttribute("exeCont", map);
		request.setAttribute("workList", workList);
		request.setAttribute("auditList", auditList);
		
		return mapping.findForward("detail");
	}
	/**
	 * 保存合作伙伴临时任务管理
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
		IPnrTempTaskMainMgr pnrTempTaskMainMgr = (IPnrTempTaskMainMgr) getBean("pnrTempTaskMainMgr");
		IPnrTempTaskWorkMgr pnrTempTaskWorkMgr = (IPnrTempTaskWorkMgr) getBean("pnrTempTaskWorkMgr");
		IPnrTempTaskAuditMgr pnrTempTaskAuditMgr = (IPnrTempTaskAuditMgr) getBean("pnrTempTaskAuditMgr");
		// 读取：当前操作用户信息
		TawSystemSessionForm sessionform = this.getUser(request);
		String operateDeptId = sessionform.getDeptid();
		String operateUserId = sessionform.getUserid();	
		
		PnrTempTaskMainForm pnrTempTaskMainForm = (PnrTempTaskMainForm) form;
		String toOrgId = StaticMethod.null2String(request.getParameter("toOrg"));
		String toOrgType = StaticMethod.null2String(request.getParameter("toOrgType"));
		String toAuditOrgId = StaticMethod.null2String(request.getParameter("toAuditOrg"));
		String toAuditOrgType = StaticMethod.null2String(request.getParameter("toAuditOrgType"));
		String toEvaOrgId = StaticMethod.null2String(request.getParameter("toEvaOrg"));
		String toEvaOrgType = StaticMethod.null2String(request.getParameter("toEvaOrgType"));

		String[] workId = pnrTempTaskMainForm.getWorkId();
//		String[] workStartTime =  pnrTempTaskMainForm.getWorkStartTime();
//		String[] workEndTime =  pnrTempTaskMainForm.getWorkEndTime();
		String[] workContent =  pnrTempTaskMainForm.getWorkContent();
		String[] workStandard =  pnrTempTaskMainForm.getWorkStandard();
		String[] workEvaContent = pnrTempTaskMainForm.getWorkEvaContent();
		String[] workEvaWeight = pnrTempTaskMainForm.getWorkEvaWeight();
		String[] workEvaName = pnrTempTaskMainForm.getWorkEvaName();
		String[] workEvaStartTime = pnrTempTaskMainForm.getWorkEvaStartTime();
		String[] workEvaEndTime = pnrTempTaskMainForm.getWorkEvaEndTime();
		String[] workEvaCycle = pnrTempTaskMainForm.getWorkEvaCycle();		
		String[] workCycle = pnrTempTaskMainForm.getWorkCycle();
		String[] taskUrl = pnrTempTaskMainForm.getTaskUrl();
		String[] workType = pnrTempTaskMainForm.getWorkType();
		String[] toOrgUser = pnrTempTaskMainForm.getToOrgUser();
		String[] toOrgUserName = pnrTempTaskMainForm.getToOrgUserName();
		String[] algorithmType = pnrTempTaskMainForm.getAlgorithmType();
//		String[] evaStandard =  pnrTempTaskMainForm.getEvaStandard();
		
		boolean isNew = (null == pnrTempTaskMainForm.getId() || "".equals(pnrTempTaskMainForm.getId()));
		if(pnrTempTaskMainForm.getCreateTime()==null||"".equals(pnrTempTaskMainForm.getCreateTime())){
			pnrTempTaskMainForm.setCreateTime(StaticMethod.getLocalString());
		}
		pnrTempTaskMainForm.setStartTime(pnrTempTaskMainForm.getStartTime()+" 00:00:00");
		pnrTempTaskMainForm.setEndTime(pnrTempTaskMainForm.getEndTime()+" 00:00:00");
		PnrTempTaskMain pnrTempTaskMain = (PnrTempTaskMain) convert(pnrTempTaskMainForm);
		if (isNew) {
			
			// 获得最大合同值,生成合同编号
			List pnrTempTaskMainList = pnrTempTaskMainMgr.getPnrTempTaskMains();
			Integer maxNo = 0;
			PnrTempTaskMain pnrTempTaskMainAsSize = null;
			if(pnrTempTaskMainList!=null&&pnrTempTaskMainList.size()>0){
				pnrTempTaskMainAsSize = (PnrTempTaskMain)pnrTempTaskMainList.get(0);
				String tempTaskNO = pnrTempTaskMainAsSize.getTempTaskNO();
				tempTaskNO = tempTaskNO.substring(tempTaskNO.length()-3);
				Pattern pattern = Pattern.compile("[0-9]*");
				Matcher isNum = pattern.matcher(tempTaskNO);
				if(isNum.matches()){
					maxNo = Integer.parseInt(tempTaskNO)+1;
				}
			}
			String tempTaskNO= pnrTempTaskMainMgr.creatNumber("LSRW", String.valueOf(maxNo));
			pnrTempTaskMain.setTempTaskNO(tempTaskNO);
			pnrTempTaskMain.setCreateUser(operateUserId);
			pnrTempTaskMain.setCreateDept(operateDeptId);
			pnrTempTaskMain.setToOrgId(toOrgId);
			pnrTempTaskMain.setToOrgType(toOrgType);
			pnrTempTaskMain.setToEvaOrgId(toEvaOrgId);
			pnrTempTaskMain.setToEvaOrgType(toEvaOrgType);
			pnrTempTaskMain.setState(PnrTempTaskConstants.AGREEMENT_STATE_UNAUDIT);
			pnrTempTaskMainMgr.savePnrTempTaskMain(pnrTempTaskMain);
			//得到相关的协议记录,更新临时任务编号
//			IPnrAgreementMainMgr pnrAgreementMainMgr = (IPnrAgreementMainMgr) getBean("pnrAgreementMainMgr");
//			PnrAgreementMain pnrAgreementMain = pnrAgreementMainMgr.getPnrAgreementMain(pnrTempTaskMain.getAgreementId());
//			pnrAgreementMain.setTempTaskId(pnrTempTaskMain.getId());
//			pnrAgreementMain.setTempTaskNO(pnrTempTaskMain.getTempTaskNO());
//			pnrAgreementMainMgr.savePnrAgreementMain(pnrAgreementMain);
			pnrTempTaskAuditMgr.savePnrAgreeMainAudit(pnrTempTaskMain, toAuditOrgId, toAuditOrgType);
			for(int i=0;workId!=null&&i<workId.length;i++){
				PnrTempTaskWork pnrTempTaskWork = new PnrTempTaskWork();
				pnrTempTaskWork.setTempTaskId(pnrTempTaskMain.getId());
				pnrTempTaskWork.setStartTime(StaticMethod.getTimestamp(workEvaStartTime[i]+" 00:00:00"));
				pnrTempTaskWork.setEndTime(StaticMethod.getTimestamp(workEvaEndTime[i]+" 00:00:00"));
				pnrTempTaskWork.setWorkContent(workContent[i]);
				pnrTempTaskWork.setWorkStandard(workStandard[i]);
				pnrTempTaskWork.setWorkEvaContent(workEvaContent[i]);
				pnrTempTaskWork.setWorkEvaName(workEvaName[i]);
				pnrTempTaskWork.setWorkEvaWeight(workEvaWeight[i]);
				pnrTempTaskWork.setWorkEvaCycle(workEvaCycle[i]);
				pnrTempTaskWork.setWorkEvaStartTime(StaticMethod.getTimestamp(workEvaStartTime[i]+" 00:00:00"));
				pnrTempTaskWork.setWorkEvaEndTime(StaticMethod.getTimestamp(workEvaEndTime[i]+" 00:00:00"));
//				pnrTempTaskWork.setEvaStandard(evaStandard[i]);
				pnrTempTaskWork.setCycle(workCycle[i]);
				pnrTempTaskWork.setWorkFlag("0");
				pnrTempTaskWork.setTaskUrl("");
				pnrTempTaskWork.setWorkType(workType[i]);
				pnrTempTaskWork.setWarnTime(workEvaStartTime[i]);
				pnrTempTaskWork.setAlgorithmType(algorithmType[i]);
				pnrTempTaskWork.setToOrgUser(toOrgUser[i]);
				pnrTempTaskWork.setToOrgUserName(toOrgUserName[i]);
				pnrTempTaskWorkMgr.savePnrTempTaskWork(pnrTempTaskWork);
			}
		} else {
			pnrTempTaskMain.setState(PnrTempTaskConstants.AGREEMENT_STATE_UNAUDIT);
			pnrTempTaskMain.setToOrgId(toOrgId);
			pnrTempTaskMain.setToOrgType(toOrgType);
			pnrTempTaskMain.setToEvaOrgId(toEvaOrgId);
			pnrTempTaskMain.setToEvaOrgType(toEvaOrgType);
			pnrTempTaskMainMgr.savePnrTempTaskMain(pnrTempTaskMain);
			pnrTempTaskAuditMgr.savePnrAgreeMainAudit(pnrTempTaskMain, toAuditOrgId, toAuditOrgType);
			List workList = pnrTempTaskWorkMgr.getPnrTempTaskWorks(pnrTempTaskMain.getId());
			for(int i=0;i<workList.size();i++){
				PnrTempTaskWork pnrTempTaskWork = (PnrTempTaskWork)workList.get(i);
				pnrTempTaskWorkMgr.removePnrTempTaskWork(pnrTempTaskWork.getId());
			}
			for(int i=0;workId!=null&&i<workId.length;i++){
				PnrTempTaskWork pnrTempTaskWork = new PnrTempTaskWork();
				pnrTempTaskWork.setTempTaskId(pnrTempTaskMain.getId());
				pnrTempTaskWork.setStartTime(StaticMethod.getTimestamp(workEvaStartTime[i]+" 00:00:00"));
				pnrTempTaskWork.setEndTime(StaticMethod.getTimestamp(workEvaEndTime[i]+" 00:00:00"));
				pnrTempTaskWork.setWorkEvaContent(workEvaContent[i]);
				pnrTempTaskWork.setWorkEvaName(workEvaName[i]);
				pnrTempTaskWork.setWorkEvaWeight(workEvaWeight[i]);
				pnrTempTaskWork.setWorkContent(workContent[i]);
				pnrTempTaskWork.setWorkStandard(workStandard[i]);
				pnrTempTaskWork.setWorkEvaCycle(workEvaCycle[i]);
				pnrTempTaskWork.setWorkEvaStartTime(StaticMethod.getTimestamp(workEvaStartTime[i]+" 00:00:00"));
				pnrTempTaskWork.setWorkEvaEndTime(StaticMethod.getTimestamp(workEvaEndTime[i]+" 00:00:00"));				
//				pnrTempTaskWork.setEvaStandard(evaStandard[i]);
				pnrTempTaskWork.setCycle(workCycle[i]);
				pnrTempTaskWork.setWorkFlag("0");
				pnrTempTaskWork.setTaskUrl("");
				pnrTempTaskWork.setWorkType(workType[i]);
				pnrTempTaskWork.setWarnTime(workEvaStartTime[i]);
				pnrTempTaskWork.setAlgorithmType(algorithmType[i]);
				pnrTempTaskWork.setToOrgUser(toOrgUser[i]);
				pnrTempTaskWork.setToOrgUserName(toOrgUserName[i]);
				pnrTempTaskWorkMgr.savePnrTempTaskWork(pnrTempTaskWork);
			}
		}
		return mapping.findForward("refreshParent");
	}
	
	/**
	 * 删除合作伙伴临时任务管理
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward remove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IPnrTempTaskMainMgr pnrTempTaskMainMgr = (IPnrTempTaskMainMgr) getBean("pnrTempTaskMainMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		pnrTempTaskMainMgr.removePnrTempTaskMain(id);
		return search(mapping, form, request, response);
	}
	
	/**
	 * 分页显示合作伙伴临时任务管理列表
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
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				PnrTempTaskConstants.PNRAGREEMENTMAIN_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		IPnrTempTaskMainMgr pnrTempTaskMainMgr = (IPnrTempTaskMainMgr) getBean("pnrTempTaskMainMgr");
		String state =  StaticMethod.nullObject2String(request.getParameter("state"));
		String whereStr = " where 1=1 ";
		if(!"".equals(state)){
			whereStr += " and pnrTempTaskMain.state='"+state+"'";
		}
		Map map = (Map) pnrTempTaskMainMgr.getPnrTempTaskMains(pageIndex, pageSize, whereStr);
		List list = (List) map.get("result");
		request.setAttribute(PnrTempTaskConstants.PNRAGREEMENTMAIN_LIST, list);
		request.setAttribute("state", state);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");
	}
	
	/**
	 * 分页显示合作伙伴临时任务管理列表，支持Atom方式接入Portal
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception

	public ActionForward search4Atom(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			// --------------用于分页，得到当前页号-------------
			final Integer pageIndex = new Integer(request
					.getParameter("pageIndex"));
			final Integer pageSize = new Integer(request
					.getParameter("pageSize"));
			IPnrTempTaskMainMgr pnrTempTaskMainMgr = (IPnrTempTaskMainMgr) getBean("pnrTempTaskMainMgr");
			Map map = (Map) pnrTempTaskMainMgr.getPnrTempTaskMains(pageIndex, pageSize, "");
			List list = (List) map.get("result");
			PnrTempTaskMain pnrTempTaskMain = new PnrTempTaskMain();
			
			//创建ATOM源
			Factory factory = Abdera.getNewFactory();
			Feed feed = factory.newFeed();
			
			// 分页
			for (int i = 0; i < list.size(); i++) {
				pnrTempTaskMain = (PnrTempTaskMain) list.get(i);
				
				// TODO 请按照下面的实例给entry赋值
				Entry entry = feed.insertEntry();
				entry.setTitle("<a href='" + request.getScheme() + "://"
						+ request.getServerName() + ":"
						+ request.getServerPort()
						+ request.getContextPath()
						+ "/pnrTempTaskMain/pnrTempTaskMains.do?method=edit&id="
						+ pnrTempTaskMain.getId() + "' target='_blank'>"
						+ display name for list + "</a>");
				entry.setSummary(summary);
				entry.setContent(content);
				entry.setLanguage(language);
				entry.setText(text);
				entry.setRights(tights);
				
				// 以下三项需要传入Date类型或String类型（yyyy-MM-dd）的参数
				entry.setUpdated(new java.util.Date());
				entry.setPublished(new java.util.Date());
				entry.setEdited(new java.util.Date());
				
				// 为person的name属性赋值，entry.addAuthor可以随意赋值
				Person person = entry.addAuthor(userId);
				person.setName(userName);
			}
			
			// 每页显示条数
			feed.setText(map.get("total").toString());
		    OutputStream os = response.getOutputStream();
		    PrintStream ps = new PrintStream(os);
		    feed.getDocument().writeTo(ps);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
		 */
	

    /**
	 * 得到待审核列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    public ActionForward audit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = StaticMethod.nullObject2String(request.getParameter("id"));
		String tempTaskId = StaticMethod.nullObject2String(request.getParameter("tempTaskId"));
		IPnrTempTaskMainMgr pnrTempTaskMainMgr = (IPnrTempTaskMainMgr) getBean("pnrTempTaskMainMgr");
		IPnrTempTaskWorkMgr pnrTempTaskWorkMgr = (IPnrTempTaskWorkMgr) getBean("pnrTempTaskWorkMgr");
		PnrTempTaskMain pnrTempTaskMain = pnrTempTaskMainMgr.getPnrTempTaskMain(tempTaskId);
		List workList = pnrTempTaskWorkMgr.getPnrTempTaskWorks(tempTaskId);
		PnrTempTaskMainForm pnrTempTaskMainForm = (PnrTempTaskMainForm) convert(pnrTempTaskMain);
		updateFormBean(mapping, request, pnrTempTaskMainForm);
		request.setAttribute("workList", workList);
		request.setAttribute("id", id);
		return mapping.findForward("audit");
	}
    /**
	 * 计划审核(通过审核部增加考核信息)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
//    public ActionForward auditDo(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
//		.getSession().getAttribute("sessionform");
//		String userId = sessionform.getUserid();
//		String deptId = sessionform.getDeptid();
//		String id = StaticMethod.nullObject2String(request.getParameter("id"));
//		String auditResult = StaticMethod.nullObject2String(request.getParameter("auditResult"));
//		String remark = StaticMethod.nullObject2String(request.getParameter("remark"));
//		IPnrTempTaskAuditMgr pnrTempTaskAuditMgr = (IPnrTempTaskAuditMgr) getBean("pnrTempTaskAuditMgr");
//		PnrTempTaskAudit pnrTempTaskAudit = pnrTempTaskAuditMgr.getPnrTempTaskAudit(id);
//		pnrTempTaskAudit.setAuditResult(auditResult);
//		pnrTempTaskAudit.setOperateTime(StaticMethod.getLocalTime());
//		pnrTempTaskAudit.setRemark(remark);
//		pnrTempTaskAudit.setState("2");
//		pnrTempTaskAuditMgr.savePnrTempTaskAudit(pnrTempTaskAudit);
//		IPnrTempTaskMainMgr pnrTempTaskMainMgr = (IPnrTempTaskMainMgr) getBean("pnrTempTaskMainMgr");
//		PnrTempTaskMain pnrTempTaskMain = pnrTempTaskMainMgr.getPnrTempTaskMain(pnrTempTaskAudit.getTempTaskId());
//		pnrTempTaskMain.setState(auditResult);
//		pnrTempTaskMainMgr.savePnrTempTaskMain(pnrTempTaskMain);
//		return mapping.findForward("refreshParent");
//	}
    /**
	 * 计划审核（通过审核直接激活模板）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    public ActionForward auditDo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		String id = StaticMethod.nullObject2String(request.getParameter("id"));
		String auditResult = StaticMethod.nullObject2String(request.getParameter("auditResult"));
		String remark = StaticMethod.nullObject2String(request.getParameter("remark"));
		IPnrTempTaskAuditMgr pnrTempTaskAuditMgr = (IPnrTempTaskAuditMgr) getBean("pnrTempTaskAuditMgr");
		PnrTempTaskAudit pnrTempTaskAudit = pnrTempTaskAuditMgr.getPnrTempTaskAudit(id);
		pnrTempTaskAudit.setAuditResult(auditResult);
		pnrTempTaskAudit.setOperateTime(StaticMethod.getLocalTime());
		pnrTempTaskAudit.setRemark(remark);
		pnrTempTaskAudit.setState("2");
		pnrTempTaskAuditMgr.savePnrTempTaskAudit(pnrTempTaskAudit);
		IPnrTempTaskMainMgr pnrTempTaskMainMgr = (IPnrTempTaskMainMgr) getBean("pnrTempTaskMainMgr");
		PnrTempTaskMain pnrTempTaskMain = pnrTempTaskMainMgr.getPnrTempTaskMain(pnrTempTaskAudit.getTempTaskId());
		pnrTempTaskMain.setState(auditResult);
		if("2".equals(auditResult)){
//			新增考核
			EvaTemplate template = new EvaTemplate();
			template.setCreator(sessionform.getUserid());
			template.setCreateTime(StaticMethod.getCurrentDateTime());
			template.setStartTime(DateTimeUtil.timestamp2String(pnrTempTaskMain.getStartTime()));
			template.setEndTime(DateTimeUtil.timestamp2String(pnrTempTaskMain.getEndTime()));
			template.setCreatorOrgId(sessionform.getDeptid());
			template.setOrgType(EvaConstants.ORG_DEPT); // orgType暂时作为预留字段，全部设置为dept
			template.setActivated(EvaConstants.TEMPLATE_ACTIVATED);
			template.setDeleted(EvaConstants.UNDELETED);
			template.setProfessional("");
			template.setAgreementId(pnrTempTaskMain.getId());
			template.setContractId("");
			template.setTemplateName(pnrTempTaskMain.getTempTaskName()+"-模板");
			template.setRemark("");
			template.setAgrwor("tempTask");
			String userid = pnrTempTaskMain.getToOrgId();
			ITawSystemUserManager mgrflush = (ITawSystemUserManager) getBean("ItawSystemUserManagerFlush");
			TawSystemUser tawSystemUser =  mgrflush.getUserByuserid(userid);
			template.setPartnerDept(tawSystemUser.getDeptid());
			template.setPartnerDeptName(tawSystemUser.getDeptname());
			IUserMgr userMgr = (IUserMgr) getBean("UserMgrImpl");
			String userAid = pnrTempTaskMain.getToEvaOrgId();
			TawSystemUser tawSystemUserA =  mgrflush.getUserByuserid(userAid);
			template.setOrgId(tawSystemUserA.getDeptid());
			String[] ids = tawSystemUserA.getDeptid().split(",");
			
//			此处取指标周期最小值作为模板的周期
			IEvaKpiTempMgr evaKpiTempMgr = (IEvaKpiTempMgr) getBean("IevaKpiTempMgr");
			int order = 4;
			String[] orderValue = {"month","quarter","halfYear","year","times"};
			Map map = new HashMap();
			map.put("month", 0);
			map.put("quarter", 1);
			map.put("halfYear", 2);
			map.put("year", 3);
			map.put("times", 4);
			String cycleValue ="";
			IPnrTempTaskWorkMgr pnrTempTaskWorkMgr = (IPnrTempTaskWorkMgr) getBean("pnrTempTaskWorkMgr");
			List pnrTempTaskList = pnrTempTaskWorkMgr.getPnrTempTaskWorks(pnrTempTaskMain.getId());
			PnrTempTaskWork pnrTempTaskWork = null;
			for(int i=0;pnrTempTaskList.size()>i;i++){
				pnrTempTaskWork = (PnrTempTaskWork)pnrTempTaskList.get(i);
				cycleValue = (String)pnrTempTaskWork.getWorkEvaCycle();
				if((Integer)map.get(cycleValue)<order){
					order = (Integer)map.get(cycleValue);
				} 
			}
			
			String cycle = orderValue[order];
			template.setCycle(cycle);
			
			IEvaTreeMgr treeMgr = (IEvaTreeMgr) getBean("IevaTreeMgr");
			
			EvaTree node = treeMgr.getNodeByRemark(tawSystemUser.getDeptid());
			String parentNodeId = node.getNodeId();
			// 所属地市
			IEvaTemplateMgr templateMgr = (IEvaTemplateMgr) getBean("IevaTemplateMgr");			
			templateMgr.saveTemplateWithNodeAndTask(template, parentNodeId, ids);
			//更新协议信息
			pnrTempTaskMain.setEvaTemplateId(template.getId());
			
			EvaTree templateTree =  treeMgr.getTreeNode(template.getBelongNode());
			
			IEvaKpiMgr kpiMgr = (IEvaKpiMgr) getBean("IevaKpiMgr");
			for (int i = 0; i < pnrTempTaskList.size(); i++) {
				pnrTempTaskWork = (PnrTempTaskWork)pnrTempTaskList.get(i);
				EvaKpi ek = new EvaKpi();
				ek.setKpiName(pnrTempTaskWork.getWorkEvaName());
				ek.setWeight(Float.valueOf(pnrTempTaskWork.getWorkEvaWeight()));
				ek.setAlgorithm(pnrTempTaskWork.getWorkEvaContent());
				ek.setRemark("");
//				此处为新增字段---考核来源  
				ek.setEvaSource(pnrTempTaskWork.getWorkType());
				ek.setCreator(pnrTempTaskMain.getToEvaOrgId());
				ek.setDeleted(EvaConstants.UNDELETED);
				ek.setCreateTime(StaticMethod.getCurrentDateTime());
				ek.setEvaEndTime(pnrTempTaskWork.getWorkEvaEndTimeStr());
				ek.setEvaStartTime(pnrTempTaskWork.getWorkEvaStartTimeStr());
				ek.setCycle(pnrTempTaskWork.getWorkEvaCycle());
				ek.setAlgorithmType(pnrTempTaskWork.getAlgorithmType());
				ek.setToOrgUser(pnrTempTaskWork.getToOrgUser());
				kpiMgr.saveKpiAndNode(ek,templateTree.getNodeId(),null);
			}
			templateMgr.activeTemplate(template.getId(), templateTree.getNodeId());
		}
		pnrTempTaskMainMgr.savePnrTempTaskMain(pnrTempTaskMain);
		return mapping.findForward("refreshParent");
	}

    /**
	 * 得到待审核列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    public ActionForward getUnAuditList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				PnrTempTaskConstants.PNRAGREEMENTAUDIT_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		IPnrTempTaskAuditMgr pnrTempTaskAuditMgr = (IPnrTempTaskAuditMgr) getBean("pnrTempTaskAuditMgr");
		Map map = (Map)pnrTempTaskAuditMgr.getPnrTempTaskUnAudits(pageIndex, pageSize,userId, deptId);
		List unAuditList = (List) map.get("result");
		request.setAttribute("unAuditList", unAuditList);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("unAuditList");
	}


    /**
	 * 增加工作项
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    public ActionForward createWork(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ID2NameService mgr = (ID2NameService) getBean("id2nameService");
		String tableId = StaticMethod.nullObject2String(request.getParameter("tableId"));
		String tableMax = StaticMethod.nullObject2String(request.getParameter("tableMax"));
		String allWeight = StaticMethod.nullObject2String(request.getParameter("allWeight"));
		String toOrgId = StaticMethod.nullObject2String(request.getParameter("toOrgId"));
		String toOrg[] = toOrgId.split(",");
		String orgData = "";
		for(int i=0 ;!toOrgId.equals("")&&i<toOrg.length;i++){
			if(i!=0){
				orgData = orgData+",";
			}
			orgData = orgData+"{id:'"+toOrg[i]+"',name:'"+mgr.id2Name(toOrg[i], "tawSystemUserDao")+"',nodeType:'user'}";
		}
		if(!"".equals(orgData)){
			orgData = "["+orgData+"]";
		}
		request.setAttribute("tableId", tableId);
		request.setAttribute("tableMax", tableMax);
		request.setAttribute("allWeight", allWeight);
		request.setAttribute("orgData",orgData);
		return mapping.findForward("createWork");
	}
    
}