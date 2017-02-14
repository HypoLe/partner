package com.boco.eoms.partner.advance.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import utils.Nop3Constants;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcService;
import com.boco.eoms.partner.advance.model.AdvanceForm;
import com.boco.eoms.partner.advance.model.AdvanceModel;
import com.boco.eoms.partner.advance.service.AdvanceService;
import com.boco.eoms.partner.baseinfo.model.TawApparatus;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

/**
 * 
 * @author GOD.WU
 *
 */
public class AdvanceAction extends BaseAction {
	
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
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String deptid = sessionform.getDeptid();
		request.setAttribute("deptid", deptid);
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
		AdvanceForm   advanceForm = (AdvanceForm)form;
		AdvanceModel advance = new AdvanceModel();
		BeanUtils.copyProperties(advance, advanceForm);
		advance.setIsDeleted("0");
		advance.setType("0");
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String deptid = sessionform.getDeptid();
		advance.setCreatType(deptid);
		advance.setState("0");
		AdvanceService service = (AdvanceService)ApplicationContextHolder.getInstance().getBean("advanceService");
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
		AdvanceService service = (AdvanceService)ApplicationContextHolder.getInstance().getBean("advanceService");
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
		AdvanceService service = (AdvanceService)ApplicationContextHolder.getInstance().getBean("advanceService");
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
		AdvanceService service = (AdvanceService)ApplicationContextHolder.getInstance().getBean("advanceService");
    	String id= request.getParameter("id");
    	AdvanceModel advance = service.find(id);
    	request.setAttribute("advance", advance);
    	String type=request.getParameter("type");
    	request.setAttribute("type", type);
        return mapping.findForward("detail");
    }
	
	/**
	 * 处理过程
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward approve(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
		AdvanceService service = (AdvanceService)ApplicationContextHolder.getInstance().getBean("advanceService");
		String type=StaticMethod.null2String(request.getParameter("type"));
		if(type.equals("3")){
			String reRemark = StaticMethod.null2String(request.getParameter("reRemark"));
			String id = StaticMethod.null2String(request.getParameter("id"));
			AdvanceModel advance = service.find(id);
			advance.setBeginTime(advance.getBeginTime().substring(0, advance.getBeginTime().length()-2));
			advance.setEndTime(advance.getEndTime().substring(0, advance.getEndTime().length()-2));
			advance.setReRemark(reRemark);
			advance.setState("4");
			service.save(advance);
		}
		else{
		String checkIds[] = StaticMethod.nullObject2String(
				request.getParameter("dealIds"), "").split(";");
		String reason =request.getParameter("checkAllow");
		String operateType =request.getParameter("operateType");
		for (int i = 0; i < checkIds.length; i++) {
			String id = checkIds[i];
			AdvanceModel advance = service.find(id);
			advance.setBeginTime(advance.getBeginTime().substring(0, advance.getBeginTime().length()-2));
			advance.setEndTime(advance.getEndTime().substring(0, advance.getEndTime().length()-2));
			advance.setCheckAllow(reason);
			advance.setState(operateType);
			service.save(advance);
		}
		}
		
        return mapping.findForward("success");
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
		AdvanceService service = (AdvanceService)ApplicationContextHolder.getInstance().getBean("advanceService");
		String checkIds[] = StaticMethod.nullObject2String(
				request.getParameter("dealIds"), "").split(";");
		for (int i = 0; i < checkIds.length; i++) {
			String id = checkIds[i];
			AdvanceModel advance = service.find(id); 
			advance.setIsDeleted("1");
			service.save(advance);
		}
		return mapping.findForward("success");
	}
	
	
	/**
	 * 统计报表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward statistic(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
		CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");
		String search ="";
		Map paramMap = request.getParameterMap();
		if(paramMap.containsKey("dt0name")){
			search+= " and dt.name= '"+new String(((String[])paramMap.get("dt0name"))[0].toString().getBytes("ISO-8859-1"),"utf-8")+"'";
		}
		if(paramMap.containsKey("d0area_name")){
			search+= " and d.area_name='"+new String(((String[])paramMap.get("d0area_name"))[0].toString().getBytes("ISO-8859-1"),"utf-8")+"'";
		}
		if(paramMap.containsKey("d0name")){
			search+= " and d.name='"+new String(((String[])paramMap.get("d0name"))[0].toString().getBytes("ISO-8859-1"),"utf-8")+"'";
		}
		if(paramMap.containsKey("app0typetypelikedict")){
			search+= " and app.type='"+new String(((String[])paramMap.get("app0typetypelikedict"))[0].toString().getBytes("ISO-8859-1"),"utf-8")+"'";
			
		}
		
		
		String searchSql="";
		List<ListOrderedMap> resultList = jdbcService.queryForList(searchSql);
		List  list = new ArrayList();
		for (ListOrderedMap listOrderedMap : resultList) {
			TawApparatus  apparatus= new TawApparatus();
			apparatus.setId((String)listOrderedMap.get("id"));
			apparatus.setApparatusName((String)listOrderedMap.get("apparatusname"));
			apparatus.setFactory((String)listOrderedMap.get("factory"));
			apparatus.setType((String)listOrderedMap.get("type"));
			apparatus.setModel((String)listOrderedMap.get("model"));
			apparatus.setState((String)listOrderedMap.get("state"));
			apparatus.setDept_id((String)listOrderedMap.get("dept_id"));
			apparatus.setArea_id((String)listOrderedMap.get("area_id"));
			list.add(apparatus);
		}
		request.setAttribute("tawApparatusList", list);
		request.setAttribute("resultSize", list.size());
		return mapping.findForward("tawApparatusList");
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
		search.addFilterEqual("isDeleted", "0");
		AdvanceService service = (AdvanceService)ApplicationContextHolder.getInstance().getBean("advanceService");
		SearchResult result = service.searchAndCount(search);
		request.setAttribute("advanceList", result.getResult());
    	request.setAttribute("size", result.getTotalCount());
    	request.setAttribute("pagesize", Nop3Constants.PAGE_SIZE);
		return mapping.findForward("allList");
	}
}
