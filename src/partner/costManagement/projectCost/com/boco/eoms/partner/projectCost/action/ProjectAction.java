package com.boco.eoms.partner.projectCost.action;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import utils.Nop3Constants;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.partner.advance.service.AdvanceService;
import com.boco.eoms.partner.projectCost.model.ProjectCostForm;
import com.boco.eoms.partner.projectCost.model.ProjectCostModel;
import com.boco.eoms.partner.projectCost.service.ProjectCostService;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

/**
 * 
 * @author GOD.WU
 *
 */
public class ProjectAction extends BaseAction {
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward goToAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AdvanceService service = (AdvanceService)ApplicationContextHolder.getInstance().getBean("advanceService");
        List list = service.findAll();
        request.setAttribute("list", list);
		return mapping.findForward("goToAdd");
	}
	/**
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
		ProjectCostForm   advanceForm = (ProjectCostForm)form;
		ProjectCostModel advance = new ProjectCostModel();
		BeanUtils.copyProperties(advance, advanceForm);
		advance.setIsDelete("0");
		ProjectCostService service = (ProjectCostService)ApplicationContextHolder.getInstance().getBean("projectCostService");
		AdvanceService Aservice = (AdvanceService)ApplicationContextHolder.getInstance().getBean("advanceService");
		advance.setAdvanceName(Aservice.find(advance.getAdvanceId()).getAdvanceName());
		service.save(advance);
		
		return mapping.findForward("success");
	}
	

	/**
	 * 按照部门查询信息
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
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String deptid = sessionform.getDeptid();
		Search search = new Search();
		search.addFilterEqual("creatType", deptid);
		search.addFilterEqual("isDeleted", "0");
		ProjectCostService service = (ProjectCostService)ApplicationContextHolder.getInstance().getBean("projectCostService");
		SearchResult result = service.searchAndCount(search);
		request.setAttribute("advanceList", result.getResult());
    	request.setAttribute("size", result.getTotalCount());
    	request.setAttribute("pagesize", Nop3Constants.PAGE_SIZE);
		return mapping.findForward("list");
	}

	
	
	/**
	 * 审批查询信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchForApprove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String deptid = sessionform.getDeptid();
		Search search = new Search();
		search.addFilterEqual("approve", deptid);
		search.addFilterEqual("isDeleted", "0");
		search.addFilterEqual("state", "0");
		ProjectCostService service = (ProjectCostService)ApplicationContextHolder.getInstance().getBean("projectCostService");
		SearchResult result = service.searchAndCount(search);
		request.setAttribute("advanceList", result.getResult());
    	request.setAttribute("size", result.getTotalCount());
    	request.setAttribute("pagesize", Nop3Constants.PAGE_SIZE);
		return mapping.findForward("Searchlist");
	}
	
	/**
	 * 详细显示页面
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
		ProjectCostService service = (ProjectCostService)ApplicationContextHolder.getInstance().getBean("projectCostService");
    	String id= request.getParameter("id");
    	ProjectCostModel advance = service.find(id);
    	request.setAttribute("advance", advance);
    	String type=request.getParameter("type");
    	request.setAttribute("type", type);
        return mapping.findForward("detail");
    }
	
	 
	/**
	 * 删除
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
		ProjectCostService service = (ProjectCostService)ApplicationContextHolder.getInstance().getBean("projectCostService");
		String checkIds[] = StaticMethod.nullObject2String(
				request.getParameter("dealIds"), "").split(";");
		for (int i = 0; i < checkIds.length; i++) {
			String id = checkIds[i];
			ProjectCostModel advance = service.find(id); 
			advance.setIsDelete("1");
			service.save(advance);
		}
		return mapping.findForward("success");
	}
	
	
	

	/**
	 * 查询
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
		Search search = new Search();
		search.addFilterEqual("isDelete", "0");
		ProjectCostService service = (ProjectCostService)ApplicationContextHolder.getInstance().getBean("projectCostService");
		SearchResult result = service.searchAndCount(search);
		List list = result.getResult();
		request.setAttribute("projectCostList", result.getResult());
    	request.setAttribute("size", result.getTotalCount());
    	request.setAttribute("pagesize", Nop3Constants.PAGE_SIZE);
		return mapping.findForward("list");
	}
	
	public static void main(String[] args) {
		int a =(8>>3)*3*8;
//		System.out.println(Integer.toBinaryString(8));  
//		System.out.println(Integer.toBinaryString(8>>1));  
		System.out.println(4<<2);
//		System.out.println(8>>1);
	}
	
}
