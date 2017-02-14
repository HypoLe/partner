package com.boco.eoms.deviceManagement.fiber.action;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.faultInfo.utils.CommonConstants;
import com.boco.eoms.deviceManagement.faultInfo.utils.CommonUtils;
import com.boco.eoms.deviceManagement.fiber.model.FiberInformation;
import com.boco.eoms.deviceManagement.fiber.service.FiberInformationService;
import com.google.common.base.Strings;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

public class FiberInformationAction extends BaseAction{

	public FiberInformationService getMainBean() {
		String source = FiberInformationService.class.getSimpleName();
		return (FiberInformationService) getBean(source.substring(0, 1)
				.toLowerCase().concat(source.substring(1)));
	}

	public ActionForward goToAddorEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = StaticMethod.nullObject2String(request.getParameter("id"));
		String type = StaticMethod.nullObject2String(request.getParameter("type"));
		FiberInformation fiberInformation = new FiberInformation();
		if(!"".equals(id)){
			fiberInformation = this.getMainBean().find(id);
		}
		request.setAttribute("fiberInformation", fiberInformation);
		request.setAttribute("type", type);
		request.setAttribute("id", id);
		return mapping.findForward("addOrEdit");
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String dealIds = StaticMethod.nullObject2String(request.getParameter("dealIds"));
		if(!"".equals(dealIds)){
			for(String id:Arrays.asList(dealIds.split(";"))){
				FiberInformation fiberInformation = this.getMainBean().find(id);
				fiberInformation.setIsDel("1");
				this.getMainBean().save(fiberInformation);
			}
		}
		return this.list(mapping, form, request, response);
	}
	
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = this.getUser(request);
		String userId = sessionform.getUserid();
		String createTime = CommonUtils.toEomsStandardDate(new Date());
		String type = StaticMethod.nullObject2String(request.getParameter("type"));
		String id = StaticMethod.nullObject2String(request.getParameter("id"));
		FiberInformation fiberInformation = null;
		if("add".equals(type)){
			fiberInformation = new FiberInformation();
		}else if("edit".equals(type)&&!"".equals(id)){
			fiberInformation = this.getMainBean().find(id);
		}
		if(null!=fiberInformation){
			fiberInformation.setFiberType(request.getParameter("fiberType"));
			fiberInformation.setFiberLevel(request.getParameter("fiberLevel"));
			fiberInformation.setFiberStructure(request.getParameter("fiberStructure"));
			fiberInformation.setFiberNumber(request.getParameter("fiberNumber"));
			fiberInformation.setFiberCoreNumber(request.getParameter("fiberCoreNumber"));
			fiberInformation.setFiberCoreType(request.getParameter("fiberCoreType"));
			fiberInformation.setInstallTime(request.getParameter("installTime"));
			fiberInformation.setParagraphNumber(request.getParameter("paragraphNumber"));
			fiberInformation.setParagraph(request.getParameter("paragraph"));
			fiberInformation.setCreateUser(userId);
			fiberInformation.setCreateTime(createTime);
			fiberInformation.setIsDel("0");
			this.getMainBean().save(fiberInformation);
		}
		return this.list(mapping, form, request, response);
	}
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(), search);
		String fiberNumberMt = StaticMethod.nullObject2String(request.getParameter("fiberNumberMt"));
		String fiberNumberLt = StaticMethod.nullObject2String(request.getParameter("fiberNumberLt"));
		
		if(!"".equals(fiberNumberMt)){
			search.addFilterGreaterOrEqual("fiberNumber", fiberNumberMt);
		}
		if(!"".equals(fiberNumberLt)){
			search.addFilterLessOrEqual("fiberNumber", fiberNumberLt);
		}
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"fiberInformationList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.addSortDesc("createTime").addFilterEqual("isDel", "0");
		SearchResult<FiberInformation> searchResult = this.getMainBean()
				.searchAndCount(search);
		List<FiberInformation> fiberInformationList = searchResult.getResult();
		request.setAttribute("fiberInformationList",fiberInformationList);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("size", searchResult.getTotalCount());
		return mapping.findForward("list");
	}
}
