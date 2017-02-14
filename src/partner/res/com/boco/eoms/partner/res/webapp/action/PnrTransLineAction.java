package com.boco.eoms.partner.res.webapp.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import utils.PartnerPrivUtils;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.common.util.StaticMethod;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.common.pojo.EomsSearch;
import com.boco.eoms.deviceManagement.common.utils.CommonConstants;
import com.boco.eoms.deviceManagement.common.utils.CommonSqlHelper;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.mobile.util.MobileConstants;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.baseinfo.util.PartnerCityByUser;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;
import com.boco.eoms.partner.dataSynch.util.DataSynchConstant;
import com.boco.eoms.partner.inspect.util.InspectConstants;
import com.boco.eoms.partner.netresource.service.IEomsService;
import com.boco.eoms.partner.res.mgr.IPnrTransLineMgr;
import com.boco.eoms.partner.res.mgr.PnrResConfigMgr;
import com.boco.eoms.partner.res.model.PnrArrivedRate;
import com.boco.eoms.partner.res.model.PnrLocationCycle;
import com.boco.eoms.partner.res.model.PnrResConfig;
import com.boco.eoms.partner.res.model.PnrTransLinePoint;
import com.boco.eoms.partner.res.util.PRCERUtil;
import com.boco.eoms.partner.res.webapp.form.PnrAllResForm;
import com.boco.eoms.partner.resourceInfo.util.ImportResult;
import com.boco.eoms.sheet.base.util.SheetAttributes;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.googlecode.genericdao.search.SearchResult;


/** 
 * Description:  
 * Copyright:   Copyright (c)2012 
 * Company:     BOCO
 * @author:     zhangkeqi 
 * @version:    1.0 
 * Create at:   2013/4/15 
 */
public class PnrTransLineAction extends BaseAction {

	/**
	 * 跳转到资源新增页面 
	 */
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("add");
	}
	
	
	/**
	 * 导入光缆段
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward importTransLine(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		response.setCharacterEncoding("utf-8");
		PnrAllResForm uploadForm=(PnrAllResForm)form;
		FormFile formFile=uploadForm.getImportFile();
		PrintWriter writer=null;
		try {
			IPnrTransLineMgr pnrTransLineMgr=(IPnrTransLineMgr)getBean("pnrTransLineMgrImpl");
			writer=response.getWriter();
			ImportResult result=pnrTransLineMgr.importTLFromFile(formFile);
			if (result.getResultCode().equals("200")) {
				writer.write(
					new Gson().toJson(new ImmutableMap.Builder<String, String>()
						.put("success", "true")
						.put("msg", "ok")
						.put("infor", "导入成功,共计导入"+result.getImportCount()+"条记录").build()));
			}
		} catch (Exception e) {
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
	/**
	 * 导入光缆敷设点
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward importTransLinePoint(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		response.setCharacterEncoding("utf-8");
		PnrAllResForm uploadForm=(PnrAllResForm)form;
		FormFile formFile=uploadForm.getImportFile();
		PrintWriter writer=null;
		try {
			IPnrTransLineMgr pnrTransLineMgr=(IPnrTransLineMgr)getBean("pnrTransLineMgrImpl");
			writer=response.getWriter();
			ImportResult result=pnrTransLineMgr.importTLPFromFile(formFile);
			if (result.getResultCode().equals("200")) {
				writer.write(
						new Gson().toJson(new ImmutableMap.Builder<String, String>()
								.put("success", "true")
								.put("msg", "ok")
								.put("infor", "导入成功,共计导入"+result.getImportCount()+"条记录").build()));
			}
		} catch (Exception e) {
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
	
	/**
	 * 导入光缆敷设点
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward goToPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String page = StaticMethod.null2String(request.getParameter("page"));
		if("goToAddArrivedRate".equals(page)) {
			setCity(request);
			return mapping.findForward("goToAddArrivedRate");
		} else if ("goToArrivedRateList".equals(page)) {
			return mapping.findForward("goToArrivedRateList");
		} else if ("goToAddLocationCycle".equals(page)) {
			setCity(request);
			return mapping.findForward("goToAddLocationCycle");
		} else if ("goToLocationCycleList".equals(page)) {
			return mapping.findForward("goToLocationCycleList");
		} else {
		}
		return null;
	}
	
	public ActionForward goToLocationCycleList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		IEomsService eomsService = (IEomsService) this.getBean("eomsService");
		EomsSearch eomsSearch = new EomsSearch();
		eomsSearch.setSearchClass(PnrLocationCycle.class);
		int firstResult = CommonUtils.getFirstResultOfDisplayTag(request, "pnrLocationCycleList");
		eomsSearch.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		eomsSearch.setMaxResults(CommonConstants.PAGE_SIZE);
		SearchResult searchResult = eomsService.searchAndCount(eomsSearch);
		List modelList = searchResult.getResult();
		
		request.setAttribute("pnrLocationCycleList",modelList);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("size", searchResult.getTotalCount());
		return mapping.findForward("goToLocationCycleList");
	}
	
	public ActionForward goToArrivedRateList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		IEomsService eomsService = (IEomsService) this.getBean("eomsService");
		EomsSearch eomsSearch = new EomsSearch();
		eomsSearch.setSearchClass(PnrArrivedRate.class);
		int firstResult = CommonUtils.getFirstResultOfDisplayTag(request, "pnrArrivedRateList");
		eomsSearch.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		eomsSearch.setMaxResults(CommonConstants.PAGE_SIZE);
		SearchResult searchResult = eomsService.searchAndCount(eomsSearch);
		List modelList = searchResult.getResult();
		
		request.setAttribute("pnrArrivedRateList",modelList);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("size", searchResult.getTotalCount());
		return mapping.findForward("goToArrivedRateList");
	}
	
	public ActionForward addArrivedRate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		String city = StaticMethod.null2String(request.getParameter("region"));
		String country = StaticMethod.null2String(request.getParameter("city"));
		String arrivedRate = StaticMethod.null2String(request.getParameter("arrivedRate"));
		
		IPnrTransLineMgr pnrTransLineMgr=(IPnrTransLineMgr)getBean("pnrTransLineMgrImpl");
		PnrArrivedRate par = new PnrArrivedRate();
		par.setCity(city);
		par.setCountry(country);
		par.setArrivedRate(arrivedRate);
		
		pnrTransLineMgr.setPersistentClass(PnrArrivedRate.class);
		pnrTransLineMgr.save(par);
		return mapping.findForward("goToArrivedRateList");
	}
	
	public ActionForward addLocationCycle(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String city = StaticMethod.null2String(request.getParameter("region"));
		String country = StaticMethod.null2String(request.getParameter("city"));
		String executeType = StaticMethod.null2String(request.getParameter("executeType"));
		String reportInterval = StaticMethod.null2String(request.getParameter("reportInterval"));
		
		IPnrTransLineMgr pnrTransLineMgr=(IPnrTransLineMgr)getBean("pnrTransLineMgrImpl");
		PnrLocationCycle plc = new PnrLocationCycle();
		plc.setCity(city);
		plc.setCountry(country);
		plc.setExecuteType(executeType);
		plc.setReportInterval(reportInterval);
		
		pnrTransLineMgr.setPersistentClass(PnrArrivedRate.class);
		pnrTransLineMgr.save(plc);
		
		return mapping.findForward("goToLocationCycleList");
	}
	
	public void setCity(HttpServletRequest request){
		/**
    	 * 根据当前省ID，列出所有地市
    	 */
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
    	List city = PartnerCityByUser.getCityByProvince(province);    	
    	request.setAttribute("city", city);
	}
	
	
	/**
	 * 线路段列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward gotoTransLineList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		String whereStr = " where tlInspectFlag = 1 ";
    	List city = PartnerCityByUser.getCityByProvince(province);    	
    	request.setAttribute("city", city);
    	request.setAttribute("city1", city);
    	PnrResConfigMgr pnrResConfiMgr = (PnrResConfigMgr) getBean("PnrResConfigMgr");
    	String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		PnrResConfig pnrResConfigForm = new PnrResConfig();
		String resName = StaticMethod.null2String(request.getParameter("resName"));
		String region = StaticMethod.null2String(request.getParameter("city"));
		String country = StaticMethod.null2String(request.getParameter("country"));
		String tlDis = StaticMethod.null2String(request.getParameter("tlDis"));
		String tlWire = StaticMethod.null2String(request.getParameter("tlWire"));
		String tlSeg = StaticMethod.null2String(request.getParameter("tlSeg"));
		pnrResConfigForm.setCity(region);
		pnrResConfigForm.setCountry(country);
		pnrResConfigForm.setResName(resName);
		pnrResConfigForm.setTlDis(tlDis);
		pnrResConfigForm.setTlWire(tlWire);
		pnrResConfigForm.setTlSeg(tlSeg);
		
		if(!StringUtils.isEmpty(pnrResConfigForm.getResName())){
			whereStr = whereStr+" and resName like '%"+pnrResConfigForm.getResName()+"%'";
		}
		if(!StringUtils.isEmpty(pnrResConfigForm.getCity())){
			whereStr = whereStr+" and city='"+pnrResConfigForm.getCity()+"'";
		}
		if(!StringUtils.isEmpty(request.getParameter("country"))){
			whereStr = whereStr+" and country='"+request.getParameter("country")+"'";
		}
		if(!StringUtils.isEmpty(request.getParameter("tlDis"))){
			whereStr = whereStr+" and tlDis like '%"+tlDis+"%'";
		}
		if(!StringUtils.isEmpty(request.getParameter("tlWire"))){
			whereStr = whereStr+" and tlWire like '%"+tlWire+"%'";
		}
		if(!StringUtils.isEmpty(request.getParameter("tlSeg"))){
			whereStr = whereStr+" and tlSeg like '%"+tlSeg+"%'";
		}
		
    	Map map = pnrResConfiMgr.getResources(firstResult*CommonConstants.PAGE_SIZE, CommonConstants.PAGE_SIZE, whereStr);
    	
    	TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
    	
    	ITawSystemDeptManager deptMgr = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
    	
		HashMap<String, String> usermap = (HashMap<String, String>)PartnerPrivUtils.userIsPersonnel(request);
		String flag = usermap.get("isPersonnel");
		if(flag.equals("y")){  //是代维人员
			request.setAttribute("isyd", "no");
		}else{//此时是移动人员
			TawSystemDept dept = deptMgr.getTawSystemDept(sessionForm.getDeptpriid());
    		request.setAttribute("isyd", "yes");
    		request.setAttribute("dept", dept.getAreaid());
		}
		
    	request.setAttribute("list",map.get("result"));
    	request.setAttribute("pnrResConfigForm", pnrResConfigForm);
		request.setAttribute("pageSize", CommonConstants.PAGE_SIZE);
		request.setAttribute("resultSize", map.get("total"));
		
		return mapping.findForward("transLineList");
	}
	
	/**
	 * 敷设点列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ActionForward gotoTransLinePointList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		PnrResConfigMgr pnrResConfigMgr = (PnrResConfigMgr) getBean("PnrResConfigMgr");
		String province = pnrBaseAreaIdList.getRootAreaId();
		String id = StaticMethod.null2String(request.getParameter("id"));
		String cityStr = StaticMethod.null2String(request.getParameter("city"));
		String country = StaticMethod.null2String(request.getParameter("country"));
		String tlpPAName = StaticMethod.null2String(request.getParameter("tlpPAName"));
		String tlpPZName = StaticMethod.null2String(request.getParameter("tlpPZName"));
		String tlDis = StaticMethod.null2String(request.getParameter("tlDis"));
		String tlpWire = StaticMethod.null2String(request.getParameter("tlpWire"));
		String tlpSeg = StaticMethod.null2String(request.getParameter("tlpSeg"));
		PnrResConfig res = pnrResConfigMgr.find(id);
		List city = PartnerCityByUser.getCityByProvince(province);    	
		Class klass = PnrTransLinePoint.class;
		EomsSearch eomsSearch = new EomsSearch();
		eomsSearch.setSearchClass(klass);
		IEomsService eomsService = (IEomsService) this.getBean("eomsService");
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		eomsSearch.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		eomsSearch.setMaxResults(CommonConstants.PAGE_SIZE);
		eomsSearch = CommonUtils.getSqlFromRequestMap(request, eomsSearch);
		eomsSearch.addFilterEqual("tlpWire", res.getTlWire());
		eomsSearch.addFilterEqual("tlpSeg", res.getTlSeg());
		if(!StringUtils.isEmpty(cityStr)){
			eomsSearch.addFilterEqual("city", cityStr);
		}
		if(!StringUtils.isEmpty(country)){
			eomsSearch.addFilterEqual("country", country);
		}
		if(!StringUtils.isEmpty(tlpPAName)){
			eomsSearch.addFilterLike("tlpPAName", "%"+tlpPAName+"%");
		}
		if(!StringUtils.isEmpty(tlpPZName)){
			eomsSearch.addFilterLike("tlpPZName", "%"+tlpPZName+"%");
		}
		if(!StringUtils.isEmpty(tlDis)){
			eomsSearch.addFilterLike("tlpDis", "%"+tlDis+"%");
		}
		if(!StringUtils.isEmpty(tlDis)){
			eomsSearch.addFilterLike("tlpWire", "%"+tlpWire+"%");
		}
		if(!StringUtils.isEmpty(tlpSeg)){
			eomsSearch.addFilterLike("tlpSeg", "%"+tlpSeg+"%");
		}
		PnrTransLinePoint pnrTransLinePoint = new PnrTransLinePoint();
		pnrTransLinePoint.setCity(cityStr);
		pnrTransLinePoint.setCountry(country);
		pnrTransLinePoint.setTlpPAName(tlpPAName);
		pnrTransLinePoint.setTlpPZName(tlpPZName);
		pnrTransLinePoint.setTlpDis(tlDis);
		pnrTransLinePoint.setTlpWire(tlpWire);
		pnrTransLinePoint.setTlpSeg(tlpSeg);
		
		int currentOperationValue = res.getExecuteRecord();
		int relation = currentOperationValue & PRCERUtil.TLP2TL;
		
		SearchResult searchResult = eomsService.searchAndCount(eomsSearch);
		List modelList = searchResult.getResult();
		request.setAttribute("list",modelList);
		request.setAttribute("pageSize", CommonConstants.PAGE_SIZE);
		request.setAttribute("size", searchResult.getTotalCount());
		request.setAttribute("city", city);
		request.setAttribute("resid", id);
		request.setAttribute("res", res); 
		request.setAttribute("relation", relation);
		request.setAttribute("pnrTransLinePoint", pnrTransLinePoint); 
		return mapping.findForward("transLinePointList");
	}
	
	/**
	 * 关联线路段
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void relationTransLine(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			IPnrTransLineMgr pnrTransLineMgr=(IPnrTransLineMgr)getBean("pnrTransLineMgrImpl");
			PnrResConfigMgr pnrResConfigMgr = (PnrResConfigMgr) getBean("PnrResConfigMgr");
			String id = StaticMethod.null2String(request.getParameter("id"));
			String tlWire = StaticMethod.null2String(request.getParameter("tlWire"));
			String tlSeg = StaticMethod.null2String(request.getParameter("tlSeg"));
			pnrTransLineMgr.relationTransLine(id,tlWire,tlSeg);
			PnrResConfig pnrResConfig = pnrResConfigMgr.find(id);
			int currentOperationValue = pnrResConfig.getExecuteRecord();
			pnrTransLineMgr.addTL2TPL(id);    //把起点和终点添加到线路段里面
			int addOperationValue = PRCERUtil.addOperation(currentOperationValue, PRCERUtil.TLP2TL);
			addOperationValue = PRCERUtil.addOperation(addOperationValue, PRCERUtil.ADD_TLPOINT2TLP);
			pnrResConfig.setExecuteRecord(addOperationValue);  //更新资源的操作功能
			pnrResConfigMgr.save(pnrResConfig);
			response.getWriter().write(MobileConstants.successStr);
		} catch (Exception e) {
			response.getWriter().write(MobileConstants.failureStr);
		}
		
	}
	
	/**
	 * 跳转到到点率设置页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward gotoTransPointRate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		IPnrTransLineMgr pnrTransLineMgr=(IPnrTransLineMgr)getBean("pnrTransLineMgrImpl");
		Map<String, List> map=null;
		((SheetAttributes) ApplicationContextHolder.getInstance().getBean("SheetAttributes")).getRegionId();
		if("HL".equals(((SheetAttributes) ApplicationContextHolder.getInstance().getBean("SheetAttributes")).getRegionId())){
			map = pnrTransLineMgr.getTransPointRate(this.getUser(request).getAreaId());//黑龙江要求根据地域来选择到点率
		}else{
			map = pnrTransLineMgr.getTransPointRate();
		}
		
		List list1 = map.get("list1");
		List<PnrArrivedRate> list2 = map.get("list2");
//		rateCountryId2Name
		if("HL".equals(((SheetAttributes) ApplicationContextHolder.getInstance().getBean("SheetAttributes")).getRegionId())){//黑龙江要求根据地域来选择到点率
			String tempContry;
			for(PnrArrivedRate rate: list2){
				tempContry =pnrTransLineMgr.rateCountryId2Name(rate.getCountry());
				rate.setCountry("".equals(tempContry)?"":tempContry+"-");
			}
		}
		
		request.setAttribute("list1", list1);
		request.setAttribute("list2", list2);
		request.setAttribute("all", StaticMethod.null2String(request.getParameter("all")));
		request.setAttribute("id", StaticMethod.null2String(request.getParameter("id")));
		return mapping.findForward("transLinePointRate");
		
	}
	
	
	/**
	 * 设置到点率
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void updateTransPointRate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			PnrResConfigMgr pnrResConfiMgr = (PnrResConfigMgr) getBean("PnrResConfigMgr");
			String id = StaticMethod.null2String(request.getParameter("id"));
			String rate = StaticMethod.null2String(request.getParameter("rate"));
			String rateStrp[] = rate.split(",");
			PnrResConfig pnrResConfig =  pnrResConfiMgr.find(id);
			pnrResConfig.setArrivedRateId(rateStrp[0]);
			pnrResConfig.setTlArrivedRate(rateStrp[1]);
			pnrResConfiMgr.save(pnrResConfig);
			response.getWriter().write(MobileConstants.successStr);
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write(MobileConstants.failureStr);
		}
		
	}
	
	/**
	 * 设置必到点
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void setMustArrive(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		try {
			IPnrTransLineMgr pnrTransLineMgr=(IPnrTransLineMgr)getBean("pnrTransLineMgrImpl");
			String ids = StaticMethod.null2String(request.getParameter("ids"));
			String cancle = StaticMethod.null2String(request.getParameter("cancle"));
			ids = ids.substring(0, ids.length()-1);
			if("yes".equals(cancle)){
				pnrTransLineMgr.setMustArrive(ids,"1");
			}else if("no".equals(cancle)){
				pnrTransLineMgr.setMustArrive(ids,"0");
			}
			response.getWriter().write(MobileConstants.successStr);
		} catch (Exception e) {
			response.getWriter().write(MobileConstants.failureStr);
		}
		
	}
	
	/**
	 * 批量设置到点率
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void updateAllTransPointRate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		try {
			IPnrTransLineMgr pnrTransLineMgr=(IPnrTransLineMgr)getBean("pnrTransLineMgrImpl");
			String ids = StaticMethod.null2String(request.getParameter("ids"));
			String rate = StaticMethod.null2String(request.getParameter("rate"));
			String cycle = StaticMethod.null2String(request.getParameter("cycle"));
//			String inspectMethod = StaticMethod.null2String(request.getParameter("inspectMethod"));
			String rateStrp[] = rate.split(",");
//			String cycleStr[] = cycle.split(",");
			ids = ids.substring(0, ids.length()-1);
			pnrTransLineMgr.updateAllTransPointRate(ids, rateStrp[0],rateStrp[1]);
			response.getWriter().write(MobileConstants.successStr);
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write(MobileConstants.failureStr);
		}
		
	}
	
	/**
	 * 跳转到代维小组分配页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward assignCheck(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		String whereStr = " where tlInspectFlag = 1 ";
    	List city = PartnerCityByUser.getCityByProvince(province);    	
    	request.setAttribute("city", city);
    	request.setAttribute("city1", city);
    	PnrResConfigMgr pnrResConfiMgr = (PnrResConfigMgr) getBean("PnrResConfigMgr");
    	String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		PnrResConfig pnrResConfigForm = new PnrResConfig();
		String resName = StaticMethod.null2String(request.getParameter("resName"));
		String region = StaticMethod.null2String(request.getParameter("city"));
		String country = StaticMethod.null2String(request.getParameter("country"));
		String tlWire = StaticMethod.null2String(request.getParameter("tlWire"));
		String tlSeg = StaticMethod.null2String(request.getParameter("tlSeg"));
		String assign = StaticMethod.null2String(request.getParameter("assign"));
		String tlDis = StaticMethod.null2String(request.getParameter("tlDis"));
		pnrResConfigForm.setCity(region);
		pnrResConfigForm.setCountry(country);
		pnrResConfigForm.setResName(resName);
		pnrResConfigForm.setTlWire(tlWire);
		pnrResConfigForm.setTlSeg(tlSeg);
		pnrResConfigForm.setTlDis(tlDis);
		
		if("yes".equals(assign)){
			whereStr += " and "+CommonSqlHelper.formatNotEmpty("executeObject");
			request.setAttribute("assign", "yes");  
		}else if("no".equals(assign)){
			whereStr += " and "+CommonSqlHelper.formatEmpty("executeObject");
			request.setAttribute("assign", "no");
		}else{
			request.setAttribute("assign", "all");
		}
		if(!StringUtils.isEmpty(pnrResConfigForm.getResName())){
			whereStr = whereStr+" and resName like '%"+pnrResConfigForm.getResName()+"%'";
		}
		if(!StringUtils.isEmpty(pnrResConfigForm.getCity())){
			whereStr = whereStr+" and city='"+pnrResConfigForm.getCity()+"'";
		}
		if(!StringUtils.isEmpty(country)){
			whereStr = whereStr+" and country='"+country+"'";
		}
		if(!StringUtils.isEmpty(tlWire)){
			whereStr = whereStr+" and tlWire like '%"+tlWire+"%'";
		}
		if(!StringUtils.isEmpty(tlSeg)){
			whereStr = whereStr+" and tlSeg like '%"+tlSeg+"%'";
		}
		if(!StringUtils.isEmpty(tlDis)){
			whereStr = whereStr+" and tlDis like '%"+tlDis+"%'";
		}
    	
    	TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		HashMap<String, String> usermap = (HashMap<String, String>)PartnerPrivUtils.userIsPersonnel(request);
		String flag = usermap.get("isPersonnel");
		if(flag.equals("y")){  //是代维人员
			PartnerDeptMgr partnerDeptMgr=(PartnerDeptMgr)getBean("partnerDeptMgr");
			List<PartnerDept> dept = partnerDeptMgr.getPartnerDepts("and deptMagId='"+sessionForm.getDeptid()+"'");
			whereStr += " and country like '"+dept.get(0).getAreaId()+"%' ";
		}else if(!"admin".equals(sessionForm.getUserid())){//是移动人员
			whereStr += " and country = 'a' ";
		}
		Map map = pnrResConfiMgr.getResources(firstResult*CommonConstants.PAGE_SIZE, CommonConstants.PAGE_SIZE, whereStr);
    	request.setAttribute("list",map.get("result"));
    	request.setAttribute("pnrResConfigForm", pnrResConfigForm);
		request.setAttribute("pageSize", CommonConstants.PAGE_SIZE);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("cycleMap", InspectConstants.cycleMap);
		return mapping.findForward("transLineExecuteObject");
	}
	/**
	 * 跳转到周期分配页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward gotoCycle(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		String whereStr = " where tlInspectFlag = 1 ";
		List city = PartnerCityByUser.getCityByProvince(province);    	
		request.setAttribute("city", city);
		request.setAttribute("city1", city);
		PnrResConfigMgr pnrResConfiMgr = (PnrResConfigMgr) getBean("PnrResConfigMgr");
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		PnrResConfig pnrResConfigForm = new PnrResConfig();
		String resName = StaticMethod.null2String(request.getParameter("resName"));
		String region = StaticMethod.null2String(request.getParameter("city"));
		String country = StaticMethod.null2String(request.getParameter("country"));
		String assign = StaticMethod.null2String(request.getParameter("assign"));
		String tlWire = StaticMethod.null2String(request.getParameter("tlWire"));
		String tlSeg = StaticMethod.null2String(request.getParameter("tlSeg"));
		String tlDis = StaticMethod.null2String(request.getParameter("tlDis"));
		pnrResConfigForm.setCity(region);
		pnrResConfigForm.setCountry(country);
		pnrResConfigForm.setResName(resName);
		pnrResConfigForm.setTlWire(tlWire);
		pnrResConfigForm.setTlSeg(tlSeg);
		pnrResConfigForm.setTlDis(tlDis);
		
		if(!StringUtils.isEmpty(pnrResConfigForm.getResName())){
			whereStr = whereStr+" and resName like '%"+pnrResConfigForm.getResName()+"%'";
		}
		if(!StringUtils.isEmpty(pnrResConfigForm.getCity())){
			whereStr = whereStr+" and city='"+pnrResConfigForm.getCity()+"'";
		}
		if(!StringUtils.isEmpty(request.getParameter("country"))){
			whereStr = whereStr+" and country='"+request.getParameter("country")+"'";
		}
		if(!StringUtils.isEmpty(tlWire)){
			whereStr = whereStr+" and tlWire like '%"+tlWire+"%'";
		}
		if(!StringUtils.isEmpty(tlSeg)){
			whereStr = whereStr+" and tlSeg like '%"+tlSeg+"%'";
		}
		if(!StringUtils.isEmpty(tlSeg)){
			whereStr = whereStr+" and tlDis like '%"+tlDis+"%'";
		}
		if("yes".equals(assign)){
			whereStr += " and "+CommonSqlHelper.formatNotEmpty("inspectCycle");
			request.setAttribute("assign", "yes");  
		}else if("no".equals(assign)){
			whereStr += " and "+CommonSqlHelper.formatEmpty("inspectCycle");
			request.setAttribute("assign", "no");
		}else{
			request.setAttribute("assign", "all");
		}
		
		Map map = pnrResConfiMgr.getResources(firstResult*CommonConstants.PAGE_SIZE, CommonConstants.PAGE_SIZE, whereStr);
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		HashMap<String, String> usermap = (HashMap<String, String>)PartnerPrivUtils.userIsPersonnel(request);
		String flag = usermap.get("isPersonnel");
		if("admin".equals(sessionForm.getUserid())){
			
		}else if(flag.equals("y")){ //此时是代维人员
			whereStr += " and country = 'a' ";//由于代维人员没有权限分配周期，当前不能查出数据
		}else{//此时是移动人员
			ITawSystemDeptManager deptMgr = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
			TawSystemDept sysdept = deptMgr.getTawSystemDept(sessionForm.getDeptpriid());
			whereStr += " and country like "+sysdept.getAreaid()+"%";
		}
		
		request.setAttribute("list",map.get("result"));
		request.setAttribute("pnrResConfigForm", pnrResConfigForm);
		request.setAttribute("pageSize", CommonConstants.PAGE_SIZE);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("cycleMap", InspectConstants.cycleMap);
		return mapping.findForward("transLineCycle");
	}
	
	public void calcleExecuteObject(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		try {
			PnrResConfigMgr pnrResConfigMgr = (PnrResConfigMgr) getBean("PnrResConfigMgr");
			String ids = StaticMethod.null2String(request
					.getParameter("ids"));
			String[] sel = ids.split("\\|");
			for (int i = 0; i < sel.length&&!StringUtils.isEmpty(sel[0]); i++) {
				PnrResConfig pnrResConfig = pnrResConfigMgr.find(sel[i]);
				pnrResConfig.setExecuteObject("");
				pnrResConfig.setExecuteType("");
				pnrResConfig.setExecuteDept("");
				pnrResConfigMgr.save(pnrResConfig);
			}
			response.getWriter().write(MobileConstants.successStr);
		} catch (Exception e) {
			e.printStackTrace();
			response.getWriter().write(MobileConstants.failureStr);
		}
		
	}
	
	public void download(ActionMapping mapping, ActionForm form,
	HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String type = request.getParameter("type");
		String rootPath = request.getRealPath("/partner/partnerRes/transLine");
		String fileName = "光缆段导入模板.csv";
		if("tl".equals(type)) {
			fileName = "光缆段导入模板.csv";
		} else if("tlp".equals(type)) {
			fileName = "光缆敷设点导入模板.csv";
		}
		File file = new File(rootPath + File.separator + fileName);

		// 读到流中
		InputStream inStream = new FileInputStream(file);// 文件的存放路径
		// 设置输出的格式
		response.reset();
		response.setContentType("application/x-msdownload;charset=GBK");
		response.setCharacterEncoding("GB2312");
		response.setHeader("Content-Disposition", "attachment; filename="
				+ new String(fileName.getBytes("gbk"), "iso8859-1"));
		// 循环取出流中的数据
		byte[] b = new byte[1024];
		int len = 0;
		while ((len = inStream.read(b)) > 0)
			response.getOutputStream().write(b, 0, len);
		inStream.close();
	}
	
	//----------------------add by lee--------------------------------
	public void listLineSegment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
	}
	public void listLinePoint(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
	}
	public void saveLocation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
	}
	
}
