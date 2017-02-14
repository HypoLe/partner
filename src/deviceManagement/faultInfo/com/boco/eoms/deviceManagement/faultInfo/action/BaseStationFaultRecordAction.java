package com.boco.eoms.deviceManagement.faultInfo.action;


import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.loging.BocoLog;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcService;
import com.boco.eoms.deviceManagement.faultInfo.model.BaseStationFaultRecord;
import com.boco.eoms.deviceManagement.faultInfo.model.BaseStationFaultRecordForm;
import com.boco.eoms.deviceManagement.faultInfo.model.ImportResult;
import com.boco.eoms.deviceManagement.faultInfo.service.BaseStationFaultRecordService;
import com.boco.eoms.deviceManagement.faultInfo.utils.CommonConstants;
import com.boco.eoms.deviceManagement.faultInfo.utils.CommonUtils;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

public final class BaseStationFaultRecordAction extends BaseAction {

	public BaseStationFaultRecordService getMainBean() {
		String source = BaseStationFaultRecordService.class.getSimpleName();
		return (BaseStationFaultRecordService) getBean(source.substring(0, 1)
				.toLowerCase().concat(source.substring(1)));
	}

	public CommonSpringJdbcService getJdbcBean() {
		String source = CommonSpringJdbcService.class.getSimpleName();
		return (CommonSpringJdbcService) getBean(source.substring(0, 1)
				.toLowerCase().concat(source.substring(1)));
	}

	public ActionForward forwardlist(ActionMapping mapping) {
		ActionForward forward = mapping.findForward("forwardlist");
		String path = forward.getPath();
		return new ActionForward(path, false);
	}

	public ActionForward goToAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("goToAdd");
	}

	// Go to templates importing operation
	public ActionForward goToImport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("goToImport");
	}

	// Go to the single detail page
	@SuppressWarnings("unchecked")
	public ActionForward goToDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id =  request.getParameter("id");
		BaseStationFaultRecord bsfr = this.getMainBean().find(id);
		
		request.setAttribute("faultInfo", bsfr);
		return mapping.findForward("goToDetail");
	}

	// Go to record editing page.
	public ActionForward goToEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id =  request.getParameter("id");
		BaseStationFaultRecord bsfr = this.getMainBean().find(id);
		
		request.setAttribute("faultInfo", bsfr);
		return mapping.findForward("goToEdit");
	}
	
	// Edit a new record.
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		BaseStationFaultRecordForm bsfrForm = (BaseStationFaultRecordForm) form;
		BaseStationFaultRecord bsfr = (BaseStationFaultRecord) convert(bsfrForm);
		
		this.getMainBean().save(bsfr);
		
		// 设置跳转页面
		BocoLog.info(this, "协议添加结束");
		return this.forwardlist(mapping);
	}

	// Delete a record.Afterward forward request.
	@SuppressWarnings("finally")
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		try {
			String id = request.getParameter("id");
			BaseStationFaultRecord bsfr = this.getMainBean().find(id);
			
			bsfr.setIsDeleted("1");
			bsfr.setDeleteTime(CommonUtils.toEomsStandardDate(new Date()));
			
			this.getMainBean().save(bsfr);
			
			CommonUtils.printJsonSuccessMsg(response);
			
		} catch (RuntimeException e) {
			BocoLog.info(this, "删除出错！");
			e.printStackTrace();
			CommonUtils.printJsonSuccessMsg(response);
		} finally {
			return null;
		}
	}

	// Delete all selected records.Afterward forward request. Notice the
	// difference with delete() method.
	public ActionForward deleteAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String myDealingList[] = StaticMethod.nullObject2String(
				request.getParameter("ids"), "").split(";");
		// Remove all records.
		for (String id : myDealingList) {
			getMainBean().removeById(id);
		}
		return forwardlist(mapping);
	}
	
	
	// Add a record.
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		BocoLog.info(this, "协议添加开始");

		// Get userId and deptId to add privileges searching.
		TawSystemSessionForm sessionform = this.getUser(request);
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		String phone = sessionform.getContactMobile();
		String roleId = sessionform.getRoleid();
		
		String reportTime = CommonUtils.toEomsStandardDate(new Date());
		
		BaseStationFaultRecordForm bsfrForm = (BaseStationFaultRecordForm) form;
		BaseStationFaultRecord bsfr = (BaseStationFaultRecord) convert(bsfrForm);
		
		bsfr.setReportPerson(userId);
		bsfr.setDeptId(deptId);
		bsfr.setRoleId(roleId);
		bsfr.setContactNumber(phone);
		bsfr.setReportTime(reportTime);
		bsfr.setIsDeleted("0");
		
		getMainBean().save(bsfr);

		// 设置跳转页面
		BocoLog.info(this, "协议添加结束");
		return this.forwardlist(mapping);
	}

	
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		Search search = new Search();
		
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(), search);
		// Get userId and deptId to add privileges searching.
		//TawSystemSessionForm sessionform = this.getUser(request);
		
		search.addFilterEqual("isDeleted", "0");
		
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"faultInfoList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.addSortDesc("reportTime");
		SearchResult<BaseStationFaultRecord> searchResult = this.getMainBean()
				.searchAndCount(search);
		
		List<BaseStationFaultRecord> faultInfoList = searchResult.getResult();
		
		request.setAttribute("faultInfoList",faultInfoList);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("bsfrList", searchResult.getResult());
		request.setAttribute("size", searchResult.getTotalCount());
		return mapping.findForward("list");
	}
	
	public ActionForward importRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		BaseStationFaultRecordForm uploadForm = (BaseStationFaultRecordForm) form;
		FormFile formFile = uploadForm.getImportFile();
		
		TawSystemSessionForm sessionform = this.getUser(request);
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		String phone = sessionform.getContactMobile();
		String roleId = sessionform.getRoleid();
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("userId", userId);
		params.put("deptId", deptId);
		params.put("phone", phone);
		params.put("roleId", roleId);
		
		PrintWriter writer = null;
		try{
			writer = response.getWriter();
			ImportResult result = this.getMainBean().importFromFile(formFile.getInputStream(),formFile.getFileName(),params);
			if(result.getResultCode().equals("200")) {
				writer.write(
						new Gson().toJson(new ImmutableMap.Builder<String, String>()
								.put("success", "true")
								.put("msg", "ok")
								.put("infor", "'导入成功,共计导入"+result.getImportCount()+"条记录'").build()));
			}
		}catch(Exception e){
			e.printStackTrace();
			writer.write(
					new Gson().toJson(new ImmutableMap.Builder<String, String>()
							.put("success", "false")
							.put("msg", "failure")
							.put("infor", e.getMessage()).build()));
		}finally{
			if(writer != null){
				writer.close();
			}
		}
		return null;
	}
	
}
