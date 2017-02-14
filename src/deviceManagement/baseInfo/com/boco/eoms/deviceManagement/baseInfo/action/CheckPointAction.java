package com.boco.eoms.deviceManagement.baseInfo.action;


import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import bsh.util.Util;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UUIDHexGenerator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.loging.BocoLog;
import com.boco.eoms.deviceManagement.baseInfo.model.BaseStation;
import com.boco.eoms.deviceManagement.baseInfo.model.CPBaseStation;
import com.boco.eoms.deviceManagement.baseInfo.model.CheckPoint;
import com.boco.eoms.deviceManagement.baseInfo.model.CheckPointType;
import com.boco.eoms.deviceManagement.baseInfo.model.CheckPointUploadForm;
import com.boco.eoms.deviceManagement.baseInfo.model.CheckSegment;
import com.boco.eoms.deviceManagement.baseInfo.model.HandWell;
import com.boco.eoms.deviceManagement.baseInfo.model.LightJoinBox;
import com.boco.eoms.deviceManagement.baseInfo.model.Pole;
import com.boco.eoms.deviceManagement.baseInfo.service.BaseStationService;
import com.boco.eoms.deviceManagement.baseInfo.service.CheckPointService;
import com.boco.eoms.deviceManagement.baseInfo.service.CheckSegmentService;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcService;
import com.boco.eoms.deviceManagement.faultInfo.model.ImportResult;
import com.boco.eoms.deviceManagement.faultInfo.utils.CommonConstants;
import com.boco.eoms.deviceManagement.faultInfo.utils.CommonUtils;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

public final class CheckPointAction extends BaseAction {

	public CheckPointService getMainBean() {
		String source = CheckPointService.class.getSimpleName();
		return (CheckPointService) getBean(source.substring(0, 1)
				.toLowerCase().concat(source.substring(1)));
	}
	public CheckPointType getCheckPointType() {
		return (CheckPointType) getBean("cpType");
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
		//巡检段
		CheckSegmentService css = (CheckSegmentService) getBean("checkSegmentService");
		List<CheckSegment> csList = css.listAll();
		
		request.setAttribute("css", csList);
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
		String id = request.getParameter("id");
		CheckPointService cps = getMainBean();
		CheckPoint cp = cps.find(id);
		String type = this.convertCPType(cp.getType());
		BaseStationService basestationService = (BaseStationService) this.getBean("baseStationService");
		BaseStation baseStation = null;
		HandWell hw = null;
		LightJoinBox ljb = null;
		Pole pole = null;
		if("1190101".equals(type)) {
			Search search =  new Search();
			search.addFilterEqual("id", cp.getAgreementNO());
			List<BaseStation> list = basestationService.search(search);
			basestationService.search(search);
			if(list.size()!=0 && list != null){
				baseStation = list.get(0);
			}else baseStation =  new BaseStation();
		} 
		if("1190102".equals(type)) {
			hw = cps.findByType(cp.getId(), new HandWell());
		}
		if("1190103".equals(type)) {
			ljb = cps.findByType(cp.getId(), new LightJoinBox());
		}
		if("1190104".equals(type)) {
			pole = cps.findByType(cp.getId(), new Pole());
		}
		
		request.setAttribute("checkPoint", cp);
		request.setAttribute("baseStation", baseStation);
		request.setAttribute("handleWell", hw);
		request.setAttribute("lightJoinBox", ljb);
		request.setAttribute("pole", pole);
		return mapping.findForward("goToDetail");
	}

	// Go to record editing page.
	public ActionForward goToEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		CheckPointService cps = getMainBean();
		BaseStationService basestationService = (BaseStationService) this.getBean("baseStationService");
		
		CheckPoint cp = cps.find(id);
		String type = this.convertCPType(cp.getType());
//		String agreementNo = cp.getAgreementNO();
		BaseStation baseStation = null;
		HandWell hw = null;
		LightJoinBox ljb = null;
		Pole pole = null;
		if("1190101".equals(type)) {
			Search search =  new Search();
			search.addFilterEqual("id", cp.getAgreementNO());
			List<BaseStation> list = basestationService.search(search);
			basestationService.search(search);
			if(list.size()!=0 && list != null){
				baseStation = list.get(0);
			}else baseStation =  new BaseStation();
//			baseStation = (BaseStation) basestationService.search(search);
		} 
		if("1190102".equals(type)) {
			hw = cps.findByType(cp.getId(), new HandWell());
		}
		if("1190103".equals(type)) {
			ljb = cps.findByType(cp.getId(), new LightJoinBox());
		}
		if("1190104".equals(type)) {
			pole = cps.findByType(cp.getId(), new Pole());
		}
		
		//巡检段
		CheckSegmentService css = (CheckSegmentService) getBean("checkSegmentService");
		List<CheckSegment> csList = css.listAll();
		
		request.setAttribute("css", csList);
		
		request.setAttribute("checkPoint", cp);
		request.setAttribute("baseStation", baseStation);
		request.setAttribute("handleWell", hw);
		request.setAttribute("lightJoinBox", ljb);
		request.setAttribute("pole", pole);
		
		return mapping.findForward("goToEdit");
	}
	
	// Edit a new record.
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CheckPointService cps = getMainBean();
		
		CheckPoint cp = new CheckPoint();
		Map paramsMap = request.getParameterMap();
		BeanUtils.populate(cp, paramsMap);
		cp.setAddTime(CommonUtils.toEomsStandardDate(new Date()));
		
		String oldType = request.getParameter("oldType");
		String type = request.getParameter("type");
		String fixedType = this.convertCPType(type);
		CPBaseStation bs;
		HandWell hw;
		LightJoinBox ljb;
		Pole pole;
		
		if("1190101".equals(fixedType)) {
			bs = new CPBaseStation();
			BeanUtils.populate(bs, paramsMap);
			bs.setId(request.getParameter("baseStationId"));
			cps.save(cp,bs,type,oldType);
		} 
		if("1190102".equals(fixedType)) {
			hw = new HandWell();
			BeanUtils.populate(hw, paramsMap);
			hw.setId(request.getParameter("handleWellId"));
			cps.save(cp,hw,type,oldType);
		}
		if("1190103".equals(fixedType)) {
			ljb = new LightJoinBox();
			BeanUtils.populate(ljb, paramsMap);
			ljb.setId(request.getParameter("lightJoinBoxId"));
			cps.save(cp,ljb,type,oldType);
		}
		if("1190104".equals(fixedType)) {
			pole = new Pole();
			BeanUtils.populate(pole, paramsMap);
			pole.setId(request.getParameter("poleId"));
			cps.save(cp,pole,type,oldType);
		}
		
		return this.forwardlist(mapping);
	}

	// Delete a record.Afterward forward request.
	@SuppressWarnings("finally")
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("utf-8");
		try {
			CheckPointService cps = getMainBean();
			String id = request.getParameter("id");
			CheckPoint cp = cps.find(id);
			cps.delete(cp);


			response.getWriter().write(
					new Gson().toJson(new ImmutableMap.Builder<String, String>()
							.put("success", "true")
							.put("msg", "ok")
							.put("infor", "删除成功！").build()));
			
		} catch (RuntimeException e) {
			BocoLog.info(this, "删除出错！");
			e.printStackTrace();
			response.getWriter().write(
					new Gson().toJson(new ImmutableMap.Builder<String, String>()
							.put("success", "true")
							.put("msg", "ok")
							.put("infor", "删除出错！").build()));
		} finally {
			return null;
		}
	}

	// Delete all selected records.Afterward forward request. Notice the
	// difference with delete() method.
	@SuppressWarnings("finally")
	public ActionForward deleteAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String myDealingList[] = StaticMethod.nullObject2String(
				request.getParameter("ids"), "").split(";");
		// Remove all records.
		CheckPointService cps = getMainBean();
		try {
			cps.deleteAll(myDealingList);
			CommonUtils.printJsonSuccessMsg(response);
			
		} catch (RuntimeException e) {
			BocoLog.info(this, "删除出错！");
			e.printStackTrace();
			CommonUtils.printJsonSuccessMsg(response);
		} finally {
			return null;
		}
	}
	
	// Add a record.
	@SuppressWarnings("unchecked")
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CheckPointService cps = getMainBean();
		BaseStationService basestationService = (BaseStationService) this.getBean("baseStationService");
		CheckPoint cp = new CheckPoint();
		Map paramsMap = request.getParameterMap();
		BeanUtils.populate(cp, paramsMap);
		cp.setAddTime(CommonUtils.toEomsStandardDate(new Date()));
		
		String type = request.getParameter("type");
		String fixedType = this.convertCPType(type);
		CPBaseStation bs;
		HandWell hw;
		LightJoinBox ljb;
		Pole pole;
		if("1190101".equals(fixedType)) {
			bs = new CPBaseStation();
			BaseStation bestation = basestationService.find(((Object[])paramsMap.get("baseStationId"))[0].toString());
			cp.setId(UUIDHexGenerator.getInstance().getID());
			cp.setAgreementNO(((Object[])paramsMap.get("baseStationId"))[0].toString());
			basestationService.save(bestation);
			BeanUtils.populate(bs, paramsMap);
			cps.save(cp,bs,"1190101","1190101");
		} 
		if("1190102".equals(fixedType)) {
			hw = new HandWell();
			BeanUtils.populate(hw, paramsMap);
			cps.save(cp,hw,"1190102","1190102");
		}
		if("1190103".equals(fixedType)) {
			ljb = new LightJoinBox();
			BeanUtils.populate(ljb, paramsMap);
			cps.save(cp,ljb,"1190103","1190103");
		}
		if("1190104".equals(fixedType)) {
			pole = new Pole();
			BeanUtils.populate(pole, paramsMap);
			cps.save(cp,pole,"1190104","1190104");
		}
		
		return this.forwardlist(mapping);
	}

	
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(), search);
		
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"checkPointList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.addSortDesc("addTime");
		SearchResult<CheckPoint> searchResult = this.getMainBean()
				.searchAndCount(search);
		
		List<CheckPoint> checkPointList = searchResult.getResult();
		
		request.setAttribute("checkPointList",checkPointList);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("size", searchResult.getTotalCount());
		return mapping.findForward("list");
	}
	
	public ActionForward importCheckPoint(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		CheckPointUploadForm uploadForm = (CheckPointUploadForm) form;
		FormFile formFile = uploadForm.getImportFile();
		
		String cpType = request.getParameter("cpType");
		cpType = this.convertCPType(cpType);
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("cpType", cpType);
		
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
	
	
	private String convertCPType(String key) {
		CheckPointType cpType = this.getCheckPointType();
		Map<String,String> typeMap = cpType.getCpType();
		return typeMap.get(key).toString();
		
	}
}
