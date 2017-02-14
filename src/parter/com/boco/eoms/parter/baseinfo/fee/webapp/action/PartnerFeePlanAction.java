/*package com.boco.eoms.parter.baseinfo.fee.webapp.action;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.ui.util.UIConstants;
import com.boco.eoms.parter.baseinfo.fee.mgr.PartnerFeeAuditMgr;
import com.boco.eoms.parter.baseinfo.fee.mgr.PartnerFeeInfoMgr;
import com.boco.eoms.parter.baseinfo.fee.mgr.PartnerFeePlanMgr;
import com.boco.eoms.parter.baseinfo.fee.model.PartnerFeeAudit;
import com.boco.eoms.parter.baseinfo.fee.model.PartnerFeePlan;
import com.boco.eoms.parter.baseinfo.fee.util.PartnerFeePlanConstants;
import com.boco.eoms.parter.baseinfo.fee.webapp.form.PartnerFeePlanForm;
import com.boco.eoms.partner.baseinfo.mgr.AreaDeptTreeMgr;
import com.boco.eoms.partner.baseinfo.model.AreaDeptTree;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;
import com.boco.eoms.partner.baseinfo.util.StatisticMethod;
import com.boco.eoms.partner.contract.contents.mgr.CtContentsMgr;
import com.boco.eoms.partner.contract.contents.util.CtContentsConstants;

*//**
 * <p>
 * Title:合作伙伴付款计划
 * </p>
 * <p>
 * Description:合作伙伴付款计划
 * </p>
 * <p>
 * Tue Sep 08 09:38:34 CST 2009
 * </p>
 * 
 * @moudle.getAuthor() lvweihua
 * @moudle.getVersion() 3.5
 * 
 *//*
public final class PartnerFeePlanAction extends BaseAction {
 
	*//**
	 * 未指定方法时默认调用的方法
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *//*
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return search(mapping, form, request, response);
	}
 	
 	*//**
	 * 新增合作伙伴付款计划
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *//*
    public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("edit");
	}
	
    *//**
	 * 查看付费计划详情
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *//*
    public ActionForward searchDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PartnerFeePlanMgr partnerFeePlanMgr = (PartnerFeePlanMgr) getBean("partnerFeePlanMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		String flag = StaticMethod.null2String(request.getParameter("flag"));
		PartnerFeePlan partnerFeePlan = partnerFeePlanMgr.getPartnerFeePlan(id);
		PartnerFeePlanForm partnerFeePlanForm = (PartnerFeePlanForm) convert(partnerFeePlan);
		updateFormBean(mapping, request, partnerFeePlanForm);
		if("draft".equals(flag)){
			return mapping.findForward("edit");
		}
		PartnerFeeAuditMgr partnerFeeAuditMgr = (PartnerFeeAuditMgr) getBean("partnerFeeAuditMgr");
		List auditList = partnerFeeAuditMgr.getPartnerFeeAudits(id, "plan");
		request.setAttribute("auditList", auditList);
		PartnerFeeInfoMgr partnerFeeInfoMgr = (PartnerFeeInfoMgr) getBean("partnerFeeInfoMgr");
		List payList = partnerFeeInfoMgr.getPartnerFeeInfoByPlanId(id);
		request.setAttribute("payList", payList);
		
		return mapping.findForward("detail");
	}
    
	*//**
	 * 修改合作伙伴付款计划
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *//*
    public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PartnerFeePlanMgr partnerFeePlanMgr = (PartnerFeePlanMgr) getBean("partnerFeePlanMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		PartnerFeePlan partnerFeePlan = partnerFeePlanMgr.getPartnerFeePlan(id);
		PartnerFeePlanForm partnerFeePlanForm = (PartnerFeePlanForm) convert(partnerFeePlan);
		updateFormBean(mapping, request, partnerFeePlanForm);
		return mapping.findForward("edit");
	}
	
	*//**
	 * 保存合作伙伴付款计划
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *//*
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PartnerFeePlanMgr partnerFeePlanMgr = (PartnerFeePlanMgr) getBean("partnerFeePlanMgr");
		PartnerFeeAuditMgr partnerFeeAuditMgr = (PartnerFeeAuditMgr) getBean("partnerFeeAuditMgr");
		PartnerFeePlanForm partnerFeePlanForm = (PartnerFeePlanForm) form;
		boolean isNew = (null == partnerFeePlanForm.getId() || "".equals(partnerFeePlanForm.getId()));
		PartnerFeePlan partnerFeePlan = (PartnerFeePlan) convert(partnerFeePlanForm);
		String toOrgId = StaticMethod.null2String(request.getParameter("toOrg"));
		String toOrgType = StaticMethod.null2String(request.getParameter("toOrgType"));
		if (isNew) {
			String user = this.getUser(request).getUserid();
			String dept = this.getUser(request).getDeptid();
			Date date = StaticMethod.getLocalTime();
			partnerFeePlan.setCreateUser(user);
			partnerFeePlan.setCreateDept(dept);
			partnerFeePlan.setCreateDate(date);
			partnerFeePlan.setPayStatus("0");
			//判断是否提交审核
			if(("").equals(toOrgId)){
				partnerFeePlan.setPayState(PartnerFeePlanConstants.PLAN_STATE_PASS);	
				partnerFeePlanMgr.savePartnerFeePlan(partnerFeePlan);
			}else{
				partnerFeePlan.setPayState(PartnerFeePlanConstants.PLAN_STATE_UNAUDIT);	
				partnerFeePlanMgr.savePartnerFeePlan(partnerFeePlan);
				partnerFeeAuditMgr.savePartnerFeePlanAudit(partnerFeePlan, toOrgId, toOrgType);
			}
		} else {
			partnerFeePlan.setPayStatus("0");
			//判断是否提交审核
			if(("").equals(toOrgId)){
				partnerFeePlan.setPayState(PartnerFeePlanConstants.PLAN_STATE_PASS);
				partnerFeePlanMgr.savePartnerFeePlan(partnerFeePlan);
			}else{
				partnerFeePlan.setPayState(PartnerFeePlanConstants.PLAN_STATE_UNAUDIT);
				partnerFeePlanMgr.savePartnerFeePlan(partnerFeePlan);
				partnerFeeAuditMgr.savePartnerFeePlanAudit(partnerFeePlan, toOrgId, toOrgType);
			}
		}
		return mapping.findForward("success");
	}
	
	*//**
	 * 保存有付款计划的付费信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	
	public ActionForward savePlanFee(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PartnerFeePlanMgr partnerFeePlanMgr = (PartnerFeePlanMgr) getBean("partnerFeePlanMgr");
		PartnerFeeInfoMgr partnerFeeInfoMgr = (PartnerFeeInfoMgr) getBean("partnerFeeInfoMgr");
		PartnerFeePlanForm partnerFeePlanForm = (PartnerFeePlanForm) form;
		boolean isNew = (null == partnerFeePlanForm.getId() || "".equals(partnerFeePlanForm.getId()));
		String id = StaticMethod.null2String(partnerFeePlanForm.getId());
		PartnerFeePlan partnerFeePlan = (PartnerFeePlan)partnerFeePlanMgr.getPartnerFeePlan(id);
		partnerFeePlan.setPayUnit(partnerFeePlanForm.getPayUnit());
		partnerFeePlan.setFactPayDate(new Date());
		partnerFeePlan.setFactPayFee(partnerFeePlanForm.getFactPayFee());
		partnerFeePlan.setPayWay(partnerFeePlanForm.getPayWay());
		partnerFeePlan.setPayUser(partnerFeePlanForm.getPayUser());
		partnerFeePlan.setPayStatus("1");
		partnerFeePlan.setRemark(StaticMethod.null2String(partnerFeePlanForm.getRemark()));
		if (isNew) {
			String user = this.getUser(request).getUserid();
			String dept = this.getUser(request).getDeptid();
			Date date = StaticMethod.getLocalTime();
			PartnerFeeInfo partnerFeeInfo = new PartnerFeeInfo();
			partnerFeeInfo = partnerFeeInfoMgr.getPartnerFeeInfoByPlanId(id);
			
			
			//收款单位
			partnerFeeInfo.setCollectUnit(StaticMethod.null2String(partnerFeePlan.getCollectUnit()));
			//付款单位
			partnerFeeInfo.setPayUnit(StaticMethod.null2String(partnerFeePlan.getPayUnit()));
			//合同编号
			partnerFeeInfo.setCompactNo(StaticMethod.null2String(partnerFeePlan.getCompactNo()));
			//对应的付款计划id
			partnerFeeInfo.setPlanId(StaticMethod.null2String(partnerFeePlan.getId()));
			partnerFeeInfo.setIsPlan("1");
			//付款时间
			partnerFeeInfo.setPayDate(partnerFeePlan.getFactPayDate());
			//付款费用
			partnerFeeInfo.setPayFee(partnerFeePlan.getFactPayFee());
			partnerFeeInfo.setCreateDate(date);
			partnerFeeInfo.setCreateDept(dept);
			partnerFeeInfo.setCreateUser(user);
			partnerFeeInfo.setCollectUnit(StaticMethod.null2String(partnerFeePlan.getCollectUnit()));
			partnerFeeInfo.setName(StaticMethod.null2String(partnerFeePlan.getName()));
			partnerFeeInfo.setRemark(StaticMethod.null2String(partnerFeePlan.getRemark()));
			partnerFeeInfo.setPayWay(StaticMethod.null2String(partnerFeePlan.getPayWay()));
			partnerFeeInfoMgr.savePartnerFeeInfo(partnerFeeInfo);
			partnerFeePlanMgr.savePartnerFeePlan(partnerFeePlan);
		} else {
			String user = this.getUser(request).getUserid();
			String dept = this.getUser(request).getDeptid();
			Date date = StaticMethod.getLocalTime();
			PartnerFeeInfo partnerFeeInfo = new PartnerFeeInfo();
			partnerFeeInfo = partnerFeeInfoMgr.getPartnerFeeInfoByPlanId(id);
			
			//收款单位
			partnerFeeInfo.setCollectUnit(StaticMethod.null2String(partnerFeePlan.getCollectUnit()));
			//付款单位
			partnerFeeInfo.setPayUnit(StaticMethod.null2String(partnerFeePlan.getPayUnit()));
			//合同编号
			partnerFeeInfo.setCompactNo(StaticMethod.null2String(partnerFeePlan.getCompactNo()));
			//对应的付款计划id
			partnerFeeInfo.setPlanId(StaticMethod.null2String(partnerFeePlan.getId()));
			partnerFeeInfo.setIsPlan("1");
			//付款时间
			partnerFeeInfo.setPayDate(partnerFeePlan.getFactPayDate());
			//付款费用
			partnerFeeInfo.setPayFee(partnerFeePlan.getFactPayFee());
			partnerFeeInfo.setCreateDate(date);
			partnerFeeInfo.setCreateDept(dept);
			partnerFeeInfo.setCreateUser(user);
			partnerFeeInfo.setCollectUnit(StaticMethod.null2String(partnerFeePlan.getCollectUnit()));
			partnerFeeInfo.setName(StaticMethod.null2String(partnerFeePlan.getName()));
			partnerFeeInfo.setRemark(StaticMethod.null2String(partnerFeePlan.getRemark()));
			partnerFeeInfo.setPayWay(StaticMethod.null2String(partnerFeePlan.getPayWay()));
			partnerFeeInfoMgr.savePartnerFeeInfo(partnerFeeInfo);
			partnerFeePlanMgr.savePartnerFeePlan(partnerFeePlan);
		}
		return mapping.findForward("success");
	}
	 *//*
	*//**
	 * 删除合作伙伴付款计划
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *//*
	public ActionForward remove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PartnerFeePlanMgr partnerFeePlanMgr = (PartnerFeePlanMgr) getBean("partnerFeePlanMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		partnerFeePlanMgr.removePartnerFeePlan(id);
		return mapping.findForward("success");
	}
	
	*//**
	 * 分页显示合作伙伴付款计划列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *//*
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				PartnerFeePlanConstants.PARTNERFEEPLAN_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		PartnerFeePlanMgr partnerFeePlanMgr = (PartnerFeePlanMgr) getBean("partnerFeePlanMgr");
		String whereStr = "where 1=1 ";
		whereStr +=" and partnerFeePlan.payState = '2'"; 
		Map map = (Map) partnerFeePlanMgr.getPartnerFeePlans(pageIndex, pageSize, whereStr);
		List list = (List) map.get("result");
		request.setAttribute(PartnerFeePlanConstants.PARTNERFEEPLAN_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");
	}
	*//**
	 * 分页显示被驳回的合作伙伴付款计划列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *//*
	public ActionForward getRejectFeePlanList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				PartnerFeePlanConstants.PARTNERFEEPLAN_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		PartnerFeePlanMgr partnerFeePlanMgr = (PartnerFeePlanMgr) getBean("partnerFeePlanMgr");
		Map map = (Map) partnerFeePlanMgr.getFeePlansByCreator(pageIndex, pageSize,userId,"", PartnerFeePlanConstants.PLAN_STATE_REJECT);
		List list = (List) map.get("result");
		request.setAttribute(PartnerFeePlanConstants.PARTNERFEEPLAN_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("flag", "draft");
		return mapping.findForward("list");
	}
	
	*//**
	 * 获得未付款付款计划树(第一层)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 *//*
	public void getNodesRadioTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		JSONArray jsonRoot = new JSONArray();
		PartnerFeePlanMgr partnerFeePlanMgr = (PartnerFeePlanMgr) getBean("partnerFeePlanMgr");
		List list = (List)partnerFeePlanMgr.getPartnerFeePlans();
		for (Iterator nodeIter = list.iterator(); nodeIter.hasNext();) {
			PartnerFeePlan partnerFeePlan =(PartnerFeePlan) nodeIter.next();
			JSONObject jitem = new JSONObject();
			jitem.put("id", partnerFeePlan.getId());
			// TODO 添加节点名称
			jitem.put("text",partnerFeePlan.getName());
			jitem.put(UIConstants.JSON_NODETYPE, "folder");
			//jitem.put("iconCls", "folder");
			jitem.put("qtip", partnerFeePlan.getName());
			
			// 设置是否为叶子节点
			boolean leafFlag = true;
		
			jitem.put("leaf", leafFlag);
			// TODO 设置鼠标悬浮提示
			//jitem.put("qtip", your tips here);
			jitem.put("checked", false);		
			jsonRoot.put(jitem);
		}
		//JSONUtil.print(response, jsonRoot.toString());		
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(jsonRoot.toString());
	}
	
	*//**
	 * 获得全部付款计划树(第一层)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 *//*
	public void getNodesRadioTree1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		JSONArray jsonRoot = new JSONArray();
		PartnerFeePlanMgr partnerFeePlanMgr = (PartnerFeePlanMgr) getBean("partnerFeePlanMgr");
		List list = (List)partnerFeePlanMgr.getPartnerFeePlans1();
		for (Iterator nodeIter = list.iterator(); nodeIter.hasNext();) {
			PartnerFeePlan partnerFeePlan =(PartnerFeePlan) nodeIter.next();
			JSONObject jitem = new JSONObject();
			jitem.put("id", partnerFeePlan.getId());
			// TODO 添加节点名称
			jitem.put("text",partnerFeePlan.getName());
			jitem.put(UIConstants.JSON_NODETYPE, "folder");
			//jitem.put("iconCls", "folder");
			jitem.put("qtip", partnerFeePlan.getName());
			
			// 设置是否为叶子节点
			boolean leafFlag = true;
		
			jitem.put("leaf", leafFlag);
			// TODO 设置鼠标悬浮提示
			//jitem.put("qtip", your tips here);
			jitem.put("checked", false);		
			jsonRoot.put(jitem);
		}
		//JSONUtil.print(response, jsonRoot.toString());		
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(jsonRoot.toString());
	}
	
	*//**
	 * 付款计划数统计
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *//*
	public ActionForward searchFeePlanStatistics(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				PartnerFeePlanConstants.PARTNERFEEPLAN_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		String startDate = StaticMethod.null2String(request.getParameter("startDate"));
		String endDate = StaticMethod.null2String(request.getParameter("endDate"));
		String plan = StaticMethod.null2String(request.getParameter("plan"));
		
		//默认统计最近一周的记录
		if("".equals(endDate)){
			Date endDateTime = new Date(System.currentTimeMillis());			
			java.util.Date startDateTime = StatisticMethod.countDate(endDateTime, -6);

			endDate = StatisticMethod.dateToString(endDateTime, "yyyy-MM-dd");
			startDate = StatisticMethod.dateToString(startDateTime, "yyyy-MM-dd");
		}
		PartnerFeePlanMgr partnerFeePlanMgr = (PartnerFeePlanMgr) getBean("partnerFeePlanMgr");
		List list = partnerFeePlanMgr.getFeePlantStatistics(plan,startDate, endDate);
		request.setAttribute(PartnerFeePlanConstants.PARTNERFEEPLAN_LIST, list);
		request.setAttribute("resultSize", new Integer(list.size()));
		request.setAttribute("pageSize", pageSize);

		request.setAttribute("startDate", startDate);
		request.setAttribute("endDate", endDate);
		request.setAttribute("plan", plan);
		return mapping.findForward("planStatistics");
	}
	*//**
	 * 获得单位树(第一层)
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 *//*
	public void getPartnerTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
		JSONArray jsonRoot = new JSONArray();
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList) getBean("pnrBaseAreaIdList");
		AreaDeptTreeMgr areaDeptTreeMgr =  (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");
		List partnerList = areaDeptTreeMgr.getNextLevelAreaDeptTrees(pnrBaseAreaIdList.getPartnerRootId());
		
		for (Iterator nodeIter = partnerList.iterator(); nodeIter.hasNext();) {
			AreaDeptTree areaDeptTree =(AreaDeptTree) nodeIter.next();
			JSONObject jitem = new JSONObject();
			jitem.put("id", areaDeptTree.getInterfaceHeadId());
			// TODO 添加节点名称
			jitem.put("text",areaDeptTree.getNodeName());
			jitem.put(UIConstants.JSON_NODETYPE, "folder");
			//jitem.put("iconCls", "folder");
			jitem.put("qtip", areaDeptTree.getNodeName());
			
			// 设置是否为叶子节点
			boolean leafFlag = true;
		
			jitem.put("leaf", leafFlag);
			// TODO 设置鼠标悬浮提示
			//jitem.put("qtip", your tips here);
			jitem.put("checked", false);		
			jsonRoot.put(jitem);
		}
		//JSONUtil.print(response, jsonRoot.toString());		
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(jsonRoot.toString());
	}
	
	*//**
	 * 根据分类去查找该 分类下的所用的合同列表 分页显示合同管理列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *//*
	public ActionForward queryContractDo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String partyAId = StaticMethod.nullObject2String(request.getParameter("partyAId"));
		String partyBId = StaticMethod.nullObject2String(request.getParameter("partyBId"));
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				CtContentsConstants.CTCONTENTS_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		String where = " where 1=1 ";
		if(!partyAId.equals("")){
			where = where+" and ctContents.partyAId = '"+partyAId+"'";
		}
		if(!partyBId.equals("")){
			where = where+" and ctContents.partyBId = '"+partyBId+"'";
		}
		Map map = new HashMap();
		try {
			CtContentsMgr ctContentsMgr = (CtContentsMgr) getBean("ctContentsMgr");
			map = (Map) ctContentsMgr.getCtContentss(pageIndex, pageSize, where);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.setAttribute(CtContentsConstants.CTCONTENTS_LIST, map.get("result"));
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);

		return mapping.findForward("queryContentsList");
	}
	
	*//**
	 * 分页显示合作伙伴付款计划列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *//*
	public ActionForward searchSelectFeePlan(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				PartnerFeePlanConstants.PARTNERFEEPLAN_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		PartnerFeePlanMgr partnerFeePlanMgr = (PartnerFeePlanMgr) getBean("partnerFeePlanMgr");
		Map map = (Map) partnerFeePlanMgr.getPartnerFeePlans(pageIndex, pageSize, "");
		List list = (List) map.get("result");
		request.setAttribute(PartnerFeePlanConstants.PARTNERFEEPLAN_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("selectFeePlanList");
	}
	
    *//**
	 * 得到待审核列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *//*
    public ActionForward getUnAuditPlanList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				PartnerFeePlanConstants.PARTNERFEEPLAN_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		PartnerFeeAuditMgr partnerFeeAuditMgr = (PartnerFeeAuditMgr) getBean("partnerFeeAuditMgr");
		Map map = (Map)partnerFeeAuditMgr.getPartnerFeeUnAudits(pageIndex, pageSize,userId, deptId, "plan");
		List unAuditList = (List) map.get("result");
		request.setAttribute("unAuditList", unAuditList);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("unAuditPlanList");
	}
    
    *//**
	 * 得到待审核列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *//*
    public ActionForward planAudit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		String id = StaticMethod.nullObject2String(request.getParameter("id"));
		String feeId = StaticMethod.nullObject2String(request.getParameter("feeId"));
		PartnerFeePlanMgr partnerFeePlanMgr = (PartnerFeePlanMgr) getBean("partnerFeePlanMgr");
		PartnerFeePlan partnerFeePlan = partnerFeePlanMgr.getPartnerFeePlan(feeId);
		request.setAttribute("id", id);
		request.setAttribute("partnerFeePlan", partnerFeePlan);
		return mapping.findForward("planAudit");
	}
    *//**
	 * 计划审核
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *//*
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
		PartnerFeeAuditMgr partnerFeeAuditMgr = (PartnerFeeAuditMgr) getBean("partnerFeeAuditMgr");
		PartnerFeeAudit partnerFeeAudit = partnerFeeAuditMgr.getPartnerFeeAudit(id);
		partnerFeeAudit.setAuditResult(auditResult);
		partnerFeeAudit.setOperateTime(new Date());
		partnerFeeAudit.setRemark(remark);
		partnerFeeAudit.setState(auditResult);
		partnerFeeAuditMgr.savePartnerFeeAudit(partnerFeeAudit);
		PartnerFeePlanMgr partnerFeePlanMgr = (PartnerFeePlanMgr) getBean("partnerFeePlanMgr");
		PartnerFeePlan partnerFeePlan = partnerFeePlanMgr.getPartnerFeePlan(partnerFeeAudit.getFeeId());
		partnerFeePlan.setPayState(auditResult);
		partnerFeePlanMgr.savePartnerFeePlan(partnerFeePlan);
		return mapping.findForward("success");
	}
    
}*/