package com.boco.eoms.partner.assess.AssFlow.webapp.action;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.common.util.MyBeanUtils;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.message.service.impl.MsgServiceImpl;
import com.boco.eoms.partner.assess.AssExecute.mgr.IAssKpiInstanceMgr;
import com.boco.eoms.partner.assess.AssFlow.mgr.IAssFlowMgr;
import com.boco.eoms.partner.assess.AssFlow.mgr.IAssOperateStepMgr;
import com.boco.eoms.partner.assess.AssFlow.model.AssFlow;
import com.boco.eoms.partner.assess.AssFlow.model.AssOperateStep;
import com.boco.eoms.partner.assess.AssFlow.webapp.form.AssOperateStepForm;
import com.boco.eoms.partner.assess.AssFlow.webapp.form.AssReportAndStepForm;
import com.boco.eoms.partner.assess.AssReport.mgr.IAssReportInfoMgr;
import com.boco.eoms.partner.assess.AssReport.model.AssReportInfo;
import com.boco.eoms.partner.assess.AssRight.util.AssRoleIdList;
import com.boco.eoms.partner.assess.util.AssConstants;

/**
 * <p>
 * Title:评估主表信息
 * </p>
 * <p>
 * Description:评估主表信息
 * </p>
 * <p>
 * Fri Sep 10 13:54:56 CST 2010
 * </p>
 * 
 * @moudle.getAuthor() 戴志刚
 * @moudle.getVersion() 1.0
 * 
 */
public class AssOperateStepAction extends BaseAction {
 
	protected String beenNameForFlowMgr = "";
	protected String beenNameForOperateStepMgr = "";
	protected String beenNameForReportInfoMgr = "";
	protected String beenNameForKpiInstanceMgr = "";
	protected String beenNameForRoleIdList = "";
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
	 * 新增评估主表信息
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
	 * 评估步骤详细信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    public ActionForward stepDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
    	
		IAssOperateStepMgr assOperateStepMgr = (IAssOperateStepMgr) getBean(beenNameForOperateStepMgr);
		IAssReportInfoMgr reportMgr = (IAssReportInfoMgr) getBean(beenNameForReportInfoMgr);
		String id = StaticMethod.null2String(request.getParameter("id"));
		AssOperateStep assOperateStep = assOperateStepMgr.getAssOperateStep(id);
		AssReportInfo report = reportMgr.getAssReportInfo(assOperateStep.getReportId());
		AssOperateStepForm assOperateStepForm = new AssOperateStepForm();
		MyBeanUtils.copyPropertiesFromDBToPage(assOperateStepForm,report);
		MyBeanUtils.copyPropertiesFromDBToPage(assOperateStepForm,assOperateStep);
		updateFormBean(mapping, request, assOperateStepForm);
		IAssFlowMgr flowMgr = (IAssFlowMgr) getBean(beenNameForFlowMgr);
		AssFlow assFlow = flowMgr.getAssFlowByXml(assOperateStep.getState());
		//根据配置中不同的PageType跳转到不同的页面
		if("editAssess".equals(assFlow.getPageType())){
			return editAssess(mapping, form, request, response,report,assOperateStep,assFlow);
		}else if("auditAssess".equals(assFlow.getPageType())){
			return auditAssess(mapping, form, request, response,report,assOperateStep,assFlow);
		}else if("confirmAssess".equals(assFlow.getPageType())){
			return confirmAssess(mapping, form, request, response,report,assOperateStep,assFlow);
		}else if("holdAssess".equals(assFlow.getPageType())){
			return holdAssess(mapping, form, request, response,report,assOperateStep,assFlow);
		}
		return mapping.findForward("stepDetail");
	}

	/**
	 * 驳回后地市代维管理员打分页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    public ActionForward editAssess(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			AssReportInfo report,AssOperateStep assOperateStep,
			AssFlow assFlow)
			throws Exception {
		IAssFlowMgr flowMgr = (IAssFlowMgr) getBean(beenNameForFlowMgr);
		AssRoleIdList roleIdList = (AssRoleIdList)getBean(beenNameForRoleIdList);
		Map map = flowMgr.pojo2Map(roleIdList);
		String operater = assFlow.getOperater();
		String roleId = StaticMethod.nullObject2String(map.get(operater));
		request.setAttribute("roleId",roleId);
		
    	AssOperateStepForm operateStepForm = new AssOperateStepForm();
    	MyBeanUtils.copyPropertiesFromDBToPage(operateStepForm,report);
    	MyBeanUtils.copyPropertiesFromDBToPage(operateStepForm,assOperateStep);
		request.setAttribute("reportAndStepForm",operateStepForm);
		IAssOperateStepMgr stepMgr = (IAssOperateStepMgr) getBean(beenNameForOperateStepMgr);
		request.setAttribute("viewFlag","edit");
		//得到地市代维管理员的打分
		List tableList = stepMgr.getKpiInstance(request, report.getId());
		request.setAttribute("tableList", tableList); // 详细列表数据
		//得到历史操作列表
		List operateSteps = stepMgr.getAssOperateSteps(report.getId());
		request.setAttribute("operateSteps",operateSteps);
		request.setAttribute("report", report);
		return mapping.findForward("editAssess");
	}	
	/**
	 * 评估审核页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    public ActionForward auditAssess(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			AssReportInfo report,AssOperateStep assOperateStep,
			AssFlow assFlow)
			throws Exception {
		IAssFlowMgr flowMgr = (IAssFlowMgr) getBean(beenNameForFlowMgr);
//    	String type = StaticMethod.nullObject2String(request.getParameter("type"));
		//得到可以驳回节点
		String rejectSteps = assFlow.getRejectCell();
		String steps[] = rejectSteps.split(",");
		List rejectList = new ArrayList();
		for(int i=0;i<steps.length;i++){
			AssFlow rejectCell = flowMgr.getAssFlowByXml(steps[i]);
			rejectList.add(rejectCell);
		}		
		
		AssRoleIdList roleIdList = (AssRoleIdList)getBean(beenNameForRoleIdList);
		
		Map map = flowMgr.pojo2Map(roleIdList);
		String operater = assFlow.getOperater();
		String roleId = StaticMethod.nullObject2String(map.get(operater));

		AssReportAndStepForm reportAndStepForm = (AssReportAndStepForm) convert(report);
		request.setAttribute("reportAndStepForm",reportAndStepForm);
		
		IAssKpiInstanceMgr kpiInstanceMgr = (IAssKpiInstanceMgr) getBean(beenNameForKpiInstanceMgr);
		request.setAttribute("viewFlag","show");
		//得到地市代维管理员的打分
//		String createOrg = String.valueOf(roleIdList.getReportExecuteRoleId());
		List tableListFirst = kpiInstanceMgr.getKpiInstancesByReport(report.getId());
		request.setAttribute("tableListFirst", tableListFirst); // 详细列表数据

		//得到历史操作列表
		IAssOperateStepMgr stepMgr = (IAssOperateStepMgr) getBean(beenNameForOperateStepMgr);
		List operateSteps = stepMgr.getAssOperateSteps(report.getId());
		request.setAttribute("operateSteps",operateSteps);
		request.setAttribute("roleId",roleId);
		request.setAttribute("rejectList", rejectList);
		request.setAttribute("report", report);
		return mapping.findForward("auditAss");
	}

    /**
	 * 确认页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    public ActionForward confirmAssess(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			AssReportInfo report,AssOperateStep assOperateStep,
			AssFlow assFlow)
			throws Exception {
		IAssFlowMgr flowMgr = (IAssFlowMgr) getBean(beenNameForFlowMgr);
		AssRoleIdList roleIdList = (AssRoleIdList)getBean(beenNameForRoleIdList);
		Map map = flowMgr.pojo2Map(roleIdList);
		String operater = assFlow.getOperater();
		String roleId = StaticMethod.nullObject2String(map.get(operater));
		request.setAttribute("roleId",roleId);
		
    	AssOperateStepForm operateStepForm = new AssOperateStepForm();
    	MyBeanUtils.copyPropertiesFromDBToPage(operateStepForm,report);
    	MyBeanUtils.copyPropertiesFromDBToPage(operateStepForm,assOperateStep);
		request.setAttribute("reportAndStepForm",operateStepForm);
		IAssOperateStepMgr stepMgr = (IAssOperateStepMgr) getBean(beenNameForOperateStepMgr);
		request.setAttribute("viewFlag","show");
		//得到可以驳回节点
		String rejectSteps = assFlow.getRejectCell();
		String steps[] = rejectSteps.split(",");
		List rejectList = new ArrayList();
		for(int i=0;i<steps.length;i++){
			AssFlow rejectCell = flowMgr.getAssFlowByXml(steps[i]);
			rejectList.add(rejectCell);
		}		
		request.setAttribute("rejectList", rejectList);
		//得到地市代维管理员的打分
		List tableList = stepMgr.getKpiInstance(request, report.getId());
		request.setAttribute("tableList", tableList); // 详细列表数据
		//得到历史操作列表
		List operateSteps = stepMgr.getAssOperateSteps(report.getId());
		request.setAttribute("operateSteps",operateSteps);
		request.setAttribute("report", report);
		return mapping.findForward("confirmAssess");
	}
	/**
	 * 归档页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    public ActionForward holdAssess(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response,
			AssReportInfo report,AssOperateStep assOperateStep,
			AssFlow assFlow)
			throws Exception {
		IAssFlowMgr flowMgr = (IAssFlowMgr) getBean(beenNameForFlowMgr);
//		AssRoleIdList roleIdList = (AssRoleIdList)getBean(beenNameForRoleIdList);
		IAssOperateStepMgr stepMgr = (IAssOperateStepMgr) getBean(beenNameForOperateStepMgr);
//		Map map = flowMgr.pojo2Map(roleIdList);
//		String operater = assFlow.getOperater();
//		String roleId = StaticMethod.nullObject2String(map.get(operater));

    	AssOperateStepForm operateStepForm = new AssOperateStepForm();
    	MyBeanUtils.copyPropertiesFromDBToPage(operateStepForm,report);
    	MyBeanUtils.copyPropertiesFromDBToPage(operateStepForm,assOperateStep);
		request.setAttribute("reportAndStepForm",operateStepForm);
		
		request.setAttribute("viewFlag","show");
		//得到可以驳回节点
		String rejectSteps = assFlow.getRejectCell();
		String steps[] = rejectSteps.split(",");
		List rejectList = new ArrayList();
		for(int i=0;i<steps.length;i++){
			AssFlow rejectCell = flowMgr.getAssFlowByXml(steps[i]);
			rejectList.add(rejectCell);
		}		
		request.setAttribute("rejectList", rejectList);
		//得到地市代维管理员的打分
		List tableList = stepMgr.getKpiInstance(request, report.getId());
		request.setAttribute("tableList", tableList); // 详细列表数据
		//得到历史操作列表
		List operateSteps = stepMgr.getAssOperateSteps(report.getId());
		request.setAttribute("operateSteps",operateSteps);
		request.setAttribute("report", report);
		request.setAttribute("holdFlag", "true");
		return mapping.findForward("holdAssess");
	}
	/**
	 * 保存评估主表信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward stepDone(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IAssFlowMgr flowMgr = (IAssFlowMgr) getBean(beenNameForFlowMgr);
		IAssOperateStepMgr assOperateStepMgr = (IAssOperateStepMgr) getBean(beenNameForOperateStepMgr);
//		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
//		.getSession().getAttribute("sessionform");
//		String userId = sessionform.getUserid();
//		String deptId = sessionform.getDeptid();
		AssOperateStepForm assOperateStepForm = (AssOperateStepForm) form;
		AssOperateStep assOperateStep = (AssOperateStep) convert(assOperateStepForm);
		AssFlow assFlow = flowMgr.getAssFlowByXml(assOperateStep.getState());
		String auditResult = StaticMethod.nullObject2String(request.getParameter("auditResult"));
		String reportId = StaticMethod.nullObject2String(request.getParameter("reportId"));
	    String nextState = assFlow.getGotoCell();
	    //如果是驳回读取页面选择的驳回节点
		if("1".equals(auditResult)){
			nextState = StaticMethod.nullObject2String(request.getParameter("nextStep"));
		}
		assOperateStepMgr.saveAssOperateStep(request, reportId, nextState);
		//归档操作时特殊处理，增加汇总记录
		 String holdFlag = StaticMethod.nullObject2String(request.getParameter("holdFlag"));
		if("true".equals(holdFlag)){
//			assOperateStepMgr.saveLastScoreForHold(assOperateStep.getReportId());
		}
		return mapping.findForward("showUndoList");
//		return undoList(mapping, assOperateStepForm, request, response);
	}
	

	/**
	 * 分页显示评估主表信息列表
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
				AssConstants.ASSESSOPERATESTEP_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = Integer.valueOf(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		IAssOperateStepMgr assOperateStepMgr = (IAssOperateStepMgr) getBean(beenNameForOperateStepMgr);
		Map map = (Map) assOperateStepMgr.getAssOperateSteps(pageIndex, pageSize, "");
		List list = (List) map.get("result");
		request.setAttribute(AssConstants.ASSESSOPERATESTEP_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");
	}
	
	/**
	 * 分页显示评估主表信息列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward undoList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
//		String deptId = sessionform.getDeptid();
		
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				AssConstants.ASSESSOPERATESTEP_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = Integer.valueOf(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		
		IAssOperateStepMgr assOperateStepMgr = (IAssOperateStepMgr) getBean(beenNameForOperateStepMgr);
		Map map = (Map) assOperateStepMgr.listUndoAss(userId,pageIndex,pageSize);
		List list = (List) map.get("result");
		//将查询结构中Main和Step的信息拷贝到AssOperateStepForm中
		List operateFormList = assOperateStepMgr.putReportAndStepListToForm(list);
		request.setAttribute(AssConstants.ASSESSOPERATESTEP_LIST, operateFormList);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("undoList");
	}
	
	/**
	 * 发送短信并返回验证码到页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward sendMessage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		
		MsgServiceImpl msgService = new MsgServiceImpl();
		String time = StaticMethod.getCurrentDateTime("yyyy-MM-dd hh:mm:ss");
		StaticMethod.getYYYYMMDD(time);
		
		AssRoleIdList roleIdList = (AssRoleIdList)ApplicationContextHolder
		.getInstance().getBean(beenNameForRoleIdList);
		String serverid = roleIdList.getServerId();
		Calendar c = Calendar.getInstance(); 

		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			sb.append(String.valueOf(random.nextInt(10)));
		}
		msgService.sendMsg(serverid, "尊敬的用户,你的验证码是："+sb.toString(), 
				String.valueOf(c.getTimeInMillis())+String.valueOf(random.nextInt(100)),"1,"+userId , time);
		
		// 设置返回页面的信息
		JSONArray json = new JSONArray();
		JSONObject jitem = new JSONObject();
		jitem.put("validateNum", sb.toString());
		json.put(jitem);
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(json.toString());
		return null;
	}		
	
}