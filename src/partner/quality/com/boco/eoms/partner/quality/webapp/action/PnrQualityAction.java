package com.boco.eoms.partner.quality.webapp.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.commons.system.user.service.ITawSystemUserManager;
import com.boco.eoms.partner.quality.mgr.IPnrQualityAuditMgr;
import com.boco.eoms.partner.quality.mgr.IPnrQualityMainMgr;
import com.boco.eoms.partner.quality.model.PnrQualityAudit;
import com.boco.eoms.partner.quality.model.PnrQualityMain;
import com.boco.eoms.partner.serviceArea.util.SiteConstants;

public class PnrQualityAction extends BaseAction {
	/**
	 * 新增质量管理报告
	 * 
	 */
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");

		String userId = this.getUser(request).getUserid();

		ITawSystemUserManager userManager = (ITawSystemUserManager) getBean("ItawSystemUserSaveManagerFlush");

		TawSystemUser user = userManager.getUserByuserid(userId);
		String deptid = user.getDeptid();
		String telphone = user.getMobile();
		request.setAttribute("userId",userId);
		request.setAttribute("deptid",deptid);
		request.setAttribute("telphone",telphone);
		
		return mapping.findForward("add");
	}

	/**
	 * 保存质量管理报告
	 * 
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");

		String userId = this.getUser(request).getUserid();

		ITawSystemUserManager userManager = (ITawSystemUserManager) getBean("ItawSystemUserSaveManagerFlush");

		TawSystemUser user = userManager.getUserByuserid(userId);
		String deptid = user.getDeptid();
		String telphone = user.getMobile();
		request.setAttribute("userId",userId);
		request.setAttribute("deptid",deptid);
		request.setAttribute("telphone",telphone);
		String title = request.getParameter("title");
		String type = request.getParameter("type");
		String cycle = request.getParameter("cycle");
		String keyWord = request.getParameter("keyWord");
		String report = request.getParameter("report");
		String summary = request.getParameter("summary");
		String auditUser = request.getParameter("auditUser");
		String state = StaticMethod.null2String(request.getParameter("state"));
		boolean isDraft = false;
		if(state.equals("")){
			state = "1";
			isDraft = true;
		}
		IPnrQualityMainMgr pnrQualityMainMgr = (IPnrQualityMainMgr) getBean("pnrQualityMainMgr");
		PnrQualityMain pnrQualityMain = new PnrQualityMain();
		pnrQualityMain.setPublishUser(userId);
		pnrQualityMain.setPublishDept(deptid);
		pnrQualityMain.setPublishTel(telphone);
		pnrQualityMain.setTitle(title);
		pnrQualityMain.setType(type);
		pnrQualityMain.setCycle(cycle);
		pnrQualityMain.setKeyWord(keyWord);
		pnrQualityMain.setReport(report);
		pnrQualityMain.setSummary(summary);
		pnrQualityMain.setAuditUser(auditUser);
		pnrQualityMain.setState(state);
		pnrQualityMainMgr.savePnrQualityMain(pnrQualityMain);
		List list = pnrQualityMainMgr.getPnrQualityMains(" where title = '" + title + "'" );
		if(list.size()>0){
			pnrQualityMain = (PnrQualityMain) list.get(0);
		}
		request.setAttribute("pnrQualityMain",pnrQualityMain);
		if(isDraft){
			return mapping.findForward("edit");
		}else{
			return mapping.findForward("success");
		}
	}

	/**
	 * 审核质量管理报告
	 * 
	 */
	
	public ActionForward audit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");

		String userId = this.getUser(request).getUserid();

		ITawSystemUserManager userManager = (ITawSystemUserManager) getBean("ItawSystemUserSaveManagerFlush");

		TawSystemUser user = userManager.getUserByuserid(userId);
		String deptid = user.getDeptid();
		String telphone = user.getMobile();
		request.setAttribute("userId",userId);
		request.setAttribute("deptid",deptid);
		request.setAttribute("telphone",telphone);

		String mainId = request.getParameter("mainId");
		IPnrQualityMainMgr pnrQualityMainMgr = (IPnrQualityMainMgr) getBean("pnrQualityMainMgr");
		PnrQualityMain pnrQualityMain = pnrQualityMainMgr.getPnrQualityMain(mainId);
		pnrQualityMain.setState("4");
		pnrQualityMainMgr.savePnrQualityMain(pnrQualityMain);		

		String isOvertime = request.getParameter("isOvertime");
		String isQualified = request.getParameter("isQualified");
		String remark = request.getParameter("remark");
		String score = request.getParameter("score");
		IPnrQualityAuditMgr pnrQualityAuditMgr = (IPnrQualityAuditMgr) getBean("pnrQualityAuditMgr");
		PnrQualityAudit pnrQualityAudit = new PnrQualityAudit();
		pnrQualityAudit.setAuditDept(userId);
		pnrQualityAudit.setAuditMan(deptid);
		pnrQualityAudit.setAuditTel(telphone);
		pnrQualityAudit.setIsOvertime(isOvertime);
		pnrQualityAudit.setIsQualified(isQualified);
		pnrQualityAudit.setMainId(mainId);
		pnrQualityAudit.setRemark(remark);
		pnrQualityAudit.setScore(score);
		pnrQualityAuditMgr.savePnrQualityAudit(pnrQualityAudit);
		
		return mapping.findForward("success");
	}	
	/**
	 * 查询质量管理报告列表
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {	

		String pageIndexName = new org.displaytag.util.ParamEncoder(
				SiteConstants.SITE_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		String userId = this.getUser(request).getUserid();
		String state = request.getParameter("state");
		String whereStr = " where 1 = 1 ";
        
		boolean isAudit = false;
		if(state!=null&&state.equals("2")){
			whereStr += " and state = '" + state + "' and auditUser = '" + userId + "'";
			isAudit = true;
		}
		IPnrQualityMainMgr pnrQualityMainMgr = (IPnrQualityMainMgr) getBean("pnrQualityMainMgr");
		Map map = pnrQualityMainMgr.getPnrQualityMains(pageIndex, pageSize, whereStr);
		List list = (List) map.get("result");
		request.setAttribute("pnrQualityMainList", list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		
		if(isAudit){
			return mapping.findForward("auditList");
		}
		return mapping.findForward("list");
	}
	/**
	 * 编辑质量管理报告列表
	 */
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {	

		String id = request.getParameter("id");

		IPnrQualityMainMgr pnrQualityMainMgr = (IPnrQualityMainMgr) getBean("pnrQualityMainMgr");
        PnrQualityMain pnrQualityMain = pnrQualityMainMgr.getPnrQualityMain(id);
        request.setAttribute("pnrQualityMain",pnrQualityMain);
		return mapping.findForward("edit");
	}	

	/**
	 * 查看质量管理报告列表
	 */
	public ActionForward detail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");
		String userId = this.getUser(request).getUserid();

		ITawSystemUserManager userManager = (ITawSystemUserManager) getBean("ItawSystemUserSaveManagerFlush");
		String isAudit = StaticMethod.null2String(request.getParameter("isAudit"));
		TawSystemUser user = userManager.getUserByuserid(userId);
		String deptid = user.getDeptid();
		String telphone = user.getMobile();
		request.setAttribute("userId",userId);
		request.setAttribute("deptid",deptid);
		request.setAttribute("telphone",telphone);
		IPnrQualityMainMgr pnrQualityMainMgr = (IPnrQualityMainMgr) getBean("pnrQualityMainMgr");
        PnrQualityMain pnrQualityMain = pnrQualityMainMgr.getPnrQualityMain(id);
        request.setAttribute("pnrQualityMain",pnrQualityMain);
        request.setAttribute("type",pnrQualityMain.getType());
        if(isAudit.equals("1")){
    		return mapping.findForward("audit");
        }
		return mapping.findForward("detail");
	}		
	/**
	 * 提交质量管理报告 
	 */
	public ActionForward submit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = this.getUser(request).getUserid();

		ITawSystemUserManager userManager = (ITawSystemUserManager) getBean("ItawSystemUserSaveManagerFlush");

		TawSystemUser user = userManager.getUserByuserid(userId);
		String deptid = user.getDeptid();
		String telphone = user.getMobile();
		request.setAttribute("userId",userId);
		request.setAttribute("deptid",deptid);
		request.setAttribute("telphone",telphone);

		String title = request.getParameter("title");
		String type = request.getParameter("type");
		String cycle = request.getParameter("cycle");
		String keyWord = request.getParameter("keyWord");
		String report = request.getParameter("report");
		String summary = request.getParameter("summary");
		String auditUser = request.getParameter("auditUser");
		IPnrQualityMainMgr pnrQualityMainMgr = (IPnrQualityMainMgr) getBean("pnrQualityMainMgr");
		PnrQualityMain pnrQualityMain = new PnrQualityMain();
		pnrQualityMain.setPublishUser(userId);
		pnrQualityMain.setPublishDept(deptid);
		pnrQualityMain.setPublishTel(telphone);
		pnrQualityMain.setTitle(title);
		pnrQualityMain.setType(type);
		pnrQualityMain.setCycle(cycle);
		pnrQualityMain.setKeyWord(keyWord);
		pnrQualityMain.setReport(report);
		pnrQualityMain.setSummary(summary);
		pnrQualityMain.setAuditUser(auditUser);
		pnrQualityMain.setState("2");
		pnrQualityMainMgr.savePnrQualityMain(pnrQualityMain);		


		List list = pnrQualityMainMgr.getPnrQualityMains(" where title = " + title + " desc ");
		if(list.size()>0){
			pnrQualityMain = (PnrQualityMain) list.get(0);
		}		
		return mapping.findForward("success");
	}

	/**
	 * 删除质量管理报告 
	 */
	public ActionForward remove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");

		IPnrQualityMainMgr pnrQualityMainMgr = (IPnrQualityMainMgr) getBean("pnrQualityMainMgr");
        pnrQualityMainMgr.removePnrQualityMain(id);
 		
		return mapping.findForward("success");
	}	
	
}
