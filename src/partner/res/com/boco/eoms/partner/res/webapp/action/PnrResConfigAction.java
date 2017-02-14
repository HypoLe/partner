package com.boco.eoms.partner.res.webapp.action;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import utils.PartnerPrivUtils;

import com.boco.eoms.base.model.PageModel;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.dept.service.bo.TawSystemDeptBo;
import com.boco.eoms.commons.system.dict.model.TawSystemDictType;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.system.user.dao.TawSystemUserDao;
import com.boco.eoms.deviceManagement.common.utils.CommonConstants;
import com.boco.eoms.deviceManagement.common.utils.CommonSqlHelper;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.mobile.util.MobileCommonUtils;
import com.boco.eoms.mobile.util.MobileConstants;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.baseinfo.util.PartnerCityByUser;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;
import com.boco.eoms.partner.deviceInspect.switchcfg.PnrDeviceInspectSwitchConfig;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanResMgr;
import com.boco.eoms.partner.inspect.util.InspectConstants;
import com.boco.eoms.partner.res.dao.PnrResConfigDao;
import com.boco.eoms.partner.res.mgr.IPnrResChangeHistoryMgr;
import com.boco.eoms.partner.res.mgr.IPnrResourceSuccessLogService;
import com.boco.eoms.partner.res.mgr.PnrResConfigMgr;
import com.boco.eoms.partner.res.mgr.PnrResConfigStationMgr;
import com.boco.eoms.partner.res.mgr.PnrResFamilyBroadbandMgr;
import com.boco.eoms.partner.res.mgr.PnrResIronMgr;
import com.boco.eoms.partner.res.mgr.PnrResJiekeMgr;
import com.boco.eoms.partner.res.mgr.PnrResLineMgr;
import com.boco.eoms.partner.res.mgr.PnrResRepeatersMgr;
import com.boco.eoms.partner.res.mgr.PnrResToWeekTimeMgr;
import com.boco.eoms.partner.res.mgr.PnrResWlanMgr;
import com.boco.eoms.partner.res.model.PnrResChangeHistory;
import com.boco.eoms.partner.res.model.PnrResConfig;
import com.boco.eoms.partner.res.model.PnrResConfigStation;
import com.boco.eoms.partner.res.model.PnrResFamilyBroadband;
import com.boco.eoms.partner.res.model.PnrResIron;
import com.boco.eoms.partner.res.model.PnrResJieke;
import com.boco.eoms.partner.res.model.PnrResLine;
import com.boco.eoms.partner.res.model.PnrResRepeaters;
import com.boco.eoms.partner.res.model.PnrResSetWeekTime;
import com.boco.eoms.partner.res.model.PnrResToWeekTime;
import com.boco.eoms.partner.res.model.PnrResWlan;
import com.boco.eoms.partner.res.model.PnrResourceSuccessLog;
import com.boco.eoms.partner.res.util.ResourceConstants;
import com.boco.eoms.partner.res.webapp.form.PnrResConfigForm;
import com.boco.eoms.sheet.base.util.SheetAttributes;
import com.google.common.base.Strings;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

/** 
 * Description:  
 * Copyright:   Copyright (c)2012 
 * Company:     BOCO
 * @author:     liaojiming 
 * @version:    1.0 
 * Create at:   2012/7/10 
 */
public class PnrResConfigAction extends BaseAction {
	/**
	 * 跳转到资源周期配置页面
	 *//*
	public ActionForward addToWeekTime(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
    	
    	//**  周期设置
    	//String seldelcar = request.getParameter("seldelcar");
		//request.setAttribute("seldelcar", seldelcar);
    	// 从数据库
	//	request.setAttribute("cycleMap", InspectConstants.cycleMap);
    	//**
		return mapping.findForward("addToWeekTime");
	}
	
	*//**
	 * 资源周期配置
	 *//*
	public ActionForward addToSuccess(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 获取操作类
		PnrResToWeekTimeMgr pnrResToWeekTimeMgr = (PnrResToWeekTimeMgr) getBean("PnrResToWeekTimeMgr");
		// 获取表单
		// PnrAllResForm pnrAllResForm = (PnrAllResForm)form;
		// 获取资源
		String resLevel = StaticMethod.null2String(request.getParameter("pnrResConfig.resLevel"));
		// 获取周期
		String weektime = StaticMethod.null2String(request.getParameter("weektime"));
		
		// session
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		//  从session获取人员的ID
		// pnrResConfig.setCreator(sessionForm.getUserid());
		//  设置当前时间
		// pnrResConfig.setCreateTime(StaticMethod.getCurrentDateTime());
		// 设置当前资源ID
		// pnrResConfig.setSubResId(subResId);
		// 保存对象（植入数据库）
		// pnrResConfiMgr.save(pnrResConfig);
		
		// 保存对象 
		PnrResToWeekTime pnr = new PnrResToWeekTime();
		pnr.setPnrID(resLevel);
		pnr.setWeekID(weektime);
		//1）先删除
		pnrResToWeekTimeMgr.removeByPnrId(pnr.getPnrID());
		//2）在保存
		pnrResToWeekTimeMgr.save(pnr);
		
		//开始跳转 页面
		
		ActionForward actionForward = new ActionForward();
		actionForward.setPath("/PnrResConfig.do?method=addToWeekTime");
		actionForward.setRedirect(true);
		return actionForward;
	}*/
	
	
	/**
	 * 跳转到资源新增页面 
	 */
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
    	/**
    	 * 根据当前省ID，列出所有地市
    	 */
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
    	List city = PartnerCityByUser.getCityByProvince(province);    	
    	request.setAttribute("city", city);
		return mapping.findForward("add");
	}
	
	/**
	 * 资源删除
	 */
	public ActionForward remove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PnrResConfigMgr pnrResConfiMgr = (PnrResConfigMgr) getBean("PnrResConfigMgr");
		String selcar = StaticMethod.null2String(request
				.getParameter("seldelcar"));
		String id = StaticMethod.null2String(request.getParameter("id"));
		try {
			if (!"".equals(selcar)) {
				String[] sel = selcar.split("\\|");
				for (int i = 0; i < sel.length; i++) {
					PnrResConfig pnrResConfig = pnrResConfiMgr.find(sel[i]);
					//记录删除信息
					PnrResChangeHistory  pnrResChangeHistory = new PnrResChangeHistory();
					pnrResChangeHistory.setResId(sel[i]);
					pnrResChangeHistory.setOldName(pnrResConfig.getResName());
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					pnrResChangeHistory.setChangeTime(sdf.parse(sdf.format(new Date())));
					pnrResChangeHistory.setChangeState("4");
					TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
					pnrResChangeHistory.setChangePerson(sessionForm.getUserid());
					//将删除信息存入数据库
					IPnrResChangeHistoryMgr  pnrResChangeHistoryMgr = (IPnrResChangeHistoryMgr)getBean("PnrResChangeHistoryMgr");
					pnrResChangeHistoryMgr.save(pnrResChangeHistory);
					
					//删除子表
					removeSubTable(pnrResConfig.getSubResTable(),pnrResConfig.getSubResId());
					pnrResConfiMgr.removeById(sel[i]);
				}
			} else if (!"".equals(id)) {
				PnrResConfig pnrResConfig = pnrResConfiMgr.find(id);
				removeSubTable(pnrResConfig.getSubResTable(),pnrResConfig.getSubResId());
				pnrResConfiMgr.removeByIds(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		ActionForward actionForward = new ActionForward();
		actionForward.setPath("/PnrResConfig.do?method=search");
		actionForward.setRedirect(true);
		return actionForward;
		
	}
	
	/**
	 * 资源查询
	 */
	@SuppressWarnings("unchecked")
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		/**
		 * 巡检资源周期以及代维组分配情况列表
		 */
    	PnrResConfigMgr pnrResConfiMgr = (PnrResConfigMgr) getBean("PnrResConfigMgr");
    	String whereCycleEmpty = CommonSqlHelper.formatEmpty("inspect_cycle");
    	List<Map> cycleList = pnrResConfiMgr.findUnAssignCycleAndExecuteObject(whereCycleEmpty);
    	String whereExecuteObjectEmpty = CommonSqlHelper.formatEmpty("execute_object");
    	List<Map> executeObjectList = pnrResConfiMgr.findUnAssignCycleAndExecuteObject(whereExecuteObjectEmpty);
    	List unAssignResList = new ArrayList(); //巡检资源周期以及代维组分配情况结果集
    	Map unAssignResMap;
		ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");
		//查询所有专业
		List<TawSystemDictType> dictTypeList = mgr.getDictSonsByDictid("11225");
		for (TawSystemDictType tawSystemDictType : dictTypeList) {
			String dictCode = tawSystemDictType.getDictCode();
			unAssignResMap = new HashMap();
			unAssignResMap.put("specialty", dictCode);//结果集首先放入专业
			//结果集根据专业放入对应的未分配周期的资源数目
			for(Map cycleMap : cycleList){
				if(dictCode.equals(cycleMap.get("specialty"))){
					unAssignResMap.put("cycleCount", cycleMap.get("count"));
					break;
				}
			}
			//结果集根据专业放入对应的未分配执行对象的资源数目
			for(Map executeObjectMap : executeObjectList){
				if(dictCode.equals(executeObjectMap.get("specialty"))){
					unAssignResMap.put("executeObjectCount", executeObjectMap.get("count"));
					break;
				}
			}
			unAssignResList.add(unAssignResMap);
		}
		request.setAttribute("unAssignResList",unAssignResList);
		
    	/**
    	 * 巡检资源查询
    	 */
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		String whereStr = "";
		PnrDeviceInspectSwitchConfig transLineswitch = (PnrDeviceInspectSwitchConfig) request.getSession().getServletContext().getAttribute("pnrInspect2SwitchConfig");
		
		if(transLineswitch.isOpenTransLineInspect()){   //开启线路巡检
			whereStr = " where specialty<>'1122502' ";
		}else{
			whereStr = " where 1=1 ";
		}
		if("HL".equals(((SheetAttributes) ApplicationContextHolder.getInstance().getBean("SheetAttributes")).getRegionId())){//黑龙江要求巡检线路维护划分需要加权限
    		HashMap<String, String> usermap = (HashMap<String, String>)PartnerPrivUtils.userIsPersonnel(request);
    		String flag = usermap.get("isPersonnel");
    		if(flag.equals("y")){
    			String areaId = "";
    			areaId = this.getUser(request).getAreaId();
    			if(null == areaId||"".equals(areaId)){
    				PartnerDeptMgr partnerDeptMgr=(PartnerDeptMgr)getBean("partnerDeptMgr");
    	    		List<PartnerDept> dept = partnerDeptMgr.getPartnerDepts("and deptMagId='"+getUser(request).getDeptid()+"'");
    	    		areaId = dept.get(0).getAreaId();
    			}
    			whereStr=whereStr+ "  and  city like '"+areaId+"%'";
    		}
    	}
    	List city = PartnerCityByUser.getCityByProvince(province);    	
    	request.setAttribute("city", city);
    	request.setAttribute("city1", city);

    	String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		PnrResConfigForm pnrResConfigForm = (PnrResConfigForm) form;
		if(!StringUtils.isEmpty(pnrResConfigForm.getResName())){
			whereStr = whereStr+" and resName like '%"+pnrResConfigForm.getResName()+"%'";
		}
		if(!StringUtils.isEmpty(pnrResConfigForm.getSpecialty())){
			whereStr = whereStr+" and specialty='"+pnrResConfigForm.getSpecialty()+"'";
		}
		if(!StringUtils.isEmpty(pnrResConfigForm.getResType())){
			whereStr = whereStr+" and resType='"+pnrResConfigForm.getResType()+"'";
		}
		if(!StringUtils.isEmpty(pnrResConfigForm.getResLevel())){
			whereStr = whereStr+" and resLevel='"+pnrResConfigForm.getResLevel()+"'";
		}
		if(!StringUtils.isEmpty(pnrResConfigForm.getCity())){
			whereStr = whereStr+" and country='"+pnrResConfigForm.getCity()+"'";
			pnrResConfigForm.setCountry(pnrResConfigForm.getCity());
		}
		if(!StringUtils.isEmpty(request.getParameter("region"))){
			whereStr = whereStr+" and city='"+request.getParameter("region")+"'";
			pnrResConfigForm.setCity(request.getParameter("region"));
		}
		
    	
    	TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
    	
    	ITawSystemDeptManager deptMgr = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
    	
//    	PartnerUserMgr partnerUserMgr = (PartnerUserMgr) getBean("partnerUserMgr");
//		List<PartnerUser> user = partnerUserMgr.getPartnerUsers(" and userId='"+sessionForm.getUserid()+"'");
		HashMap<String, String> usermap = (HashMap<String, String>)PartnerPrivUtils.userIsPersonnel(request);
		String flag = usermap.get("isPersonnel");
		if(flag.equals("y")){  //是代维人员
			request.setAttribute("isyd", "no");
			//控制地市权限
			PartnerDeptMgr partnerDeptMgr=(PartnerDeptMgr)getBean("partnerDeptMgr");
			List<PartnerDept> dept = partnerDeptMgr.getPartnerDepts("and deptMagId='"+sessionForm.getDeptid()+"'");
			whereStr = whereStr+" and country like '"+dept.get(0).getAreaId()+"%"+"'";
			whereStr = whereStr+" and executeDept like '"+sessionForm.getDeptid()+"%"+"'";

		}else{//此时是移动人员
			TawSystemDept dept = deptMgr.getTawSystemDept(sessionForm.getDeptpriid());
    		request.setAttribute("isyd", "yes");
    		request.setAttribute("dept", dept.getAreaid());
    		//控制地市权限
    		String aa=sessionForm.getDeptid();
    		TawSystemDeptBo deptbo = TawSystemDeptBo.getInstance();
    		TawSystemDept sept = deptbo.getDeptinfobydeptid(aa, "0");
			whereStr = whereStr+" and country like '"+sept.getAreaid()+"%"+"'";
		}
    	
		Map map = pnrResConfiMgr.getResources(firstResult*CommonConstants.PAGE_SIZE, CommonConstants.PAGE_SIZE, whereStr);
    	request.setAttribute("list",map.get("result"));
    	request.setAttribute("pnrResConfigForm", pnrResConfigForm);
		request.setAttribute("pageSize", CommonConstants.PAGE_SIZE);
		request.setAttribute("resultSize", map.get("total"));
		
		return mapping.findForward("list");
	}
	
	/**
	 * 用来跳到成功界面
	 */
	public ActionForward addSuccess(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("success");
	}
	
	/*
	 * 巡检资源详情
	 */
	@SuppressWarnings("unchecked")
	public ActionForward detial(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PnrResConfigMgr pnrResConfiMgr = (PnrResConfigMgr) getBean("PnrResConfigMgr");
		String id = StaticMethod.null2String(request.getParameter("seldelcar"));
		String gisType = StaticMethod.null2String(request.getParameter("gisType"));
		if(!"".equals(gisType)){
			request.setAttribute("gisType", gisType);	
		}
		PnrResConfig pnrResConfig = pnrResConfiMgr.find(id);
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
    	List city = PartnerCityByUser.getCityByProvince(province);    	
    	request.setAttribute("city", city);
		request.setAttribute("pnrResConfig", pnrResConfig);
		String subResTable = "";
		if(pnrResConfig ==null){
			request.setAttribute("existRes", false);
		}else{
			subResTable =  pnrResConfig.getSubResTable();
			request.setAttribute("existRes", true);
		}
		
		if("pnr_res_station".equals(subResTable)){//基站设备及配套
			PnrResConfigStationMgr pnrResConfigStationMgr = (PnrResConfigStationMgr)getBean("PnrResConfigStationMgr");
			PnrResConfigStation pnrResConfigStation = new PnrResConfigStation();
			if(!StringUtils.isEmpty(pnrResConfig.getSubResId())){
				pnrResConfigStation = pnrResConfigStationMgr.find(pnrResConfig.getSubResId());
			}
			request.setAttribute("pnrResConfigStation", pnrResConfigStation);
		}else if("pnr_res_repeaters".equals(subResTable)){//直放站
			PnrResRepeatersMgr pnrResRepeatersMgr = (PnrResRepeatersMgr) getBean("PnrResRepeatersMgr");
			PnrResRepeaters pnrResRepeaters = new PnrResRepeaters();
			if(!StringUtils.isEmpty(pnrResConfig.getSubResId())){
				pnrResRepeaters = pnrResRepeatersMgr.find(pnrResConfig.getSubResId());
			}
			request.setAttribute("pnrResRepeaters", pnrResRepeaters);
		}else if("pnr_res_iron".equals(subResTable)){//铁搭
			PnrResIronMgr PnrResIronMgr = (PnrResIronMgr) getBean("PnrResIronMgr");		
			PnrResIron pnrResIron = new PnrResIron();
			if(!StringUtils.isEmpty(pnrResConfig.getSubResId())){
				pnrResIron = PnrResIronMgr.find(pnrResConfig.getSubResId());
			}
			request.setAttribute("pnrResIron", pnrResIron);
		}else if("pnr_res_jieke".equals(subResTable)){//集客家客
			PnrResJiekeMgr PnrResJiekeMgr = (PnrResJiekeMgr)getBean("PnrResJiekeMgr");
			PnrResJieke pnrResJieke = new PnrResJieke();
			if(!StringUtils.isEmpty(pnrResConfig.getSubResId())){
				pnrResJieke = PnrResJiekeMgr.find(pnrResConfig.getSubResId());
			}
			request.setAttribute("pnrResJieke", pnrResJieke);
		}else if("pnr_res_line".equals(subResTable)){//传输线路
			PnrResLineMgr PnrResLineMgr = (PnrResLineMgr)getBean("PnrResLineMgr");
			PnrResLine pnrResLine = new PnrResLine();
			if(!StringUtils.isEmpty(pnrResConfig.getSubResId())){
				pnrResLine = PnrResLineMgr.find(pnrResConfig.getSubResId());
			}
			request.setAttribute("pnrResLine", pnrResLine);
		}else if("pnr_res_family_broadband".equals(subResTable)){//家庭宽带
			PnrResFamilyBroadbandMgr pnrResFamilyBroadbandMgr = (PnrResFamilyBroadbandMgr)getBean("PnrResFamilyBroadbandMgr");
			PnrResFamilyBroadband pnrResFamilyBroadband = new PnrResFamilyBroadband();
			if(!StringUtils.isEmpty(pnrResConfig.getSubResId())){
				pnrResFamilyBroadband = pnrResFamilyBroadbandMgr.find(pnrResConfig.getSubResId());
			}
			request.setAttribute("pnrResFamilyBroadband", pnrResFamilyBroadband);
		}else if("pnr_res_wlan".equals(subResTable)){//WLAN
			
			PnrResWlanMgr pnrResWlanMgr = (PnrResWlanMgr)getBean("PnrResWlanMgr");
			PnrResWlan pnrResWlan = new PnrResWlan();
			if(!StringUtils.isEmpty(pnrResConfig.getSubResId())){
				pnrResWlan = pnrResWlanMgr.find(pnrResConfig.getSubResId());
			}
			request.setAttribute("pnrResWlan", pnrResWlan);
		}
		
		return mapping.findForward("detial");
	}
	
	/*
	 * 重新编辑
	 * */
	@SuppressWarnings("unchecked")
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PnrResConfigMgr pnrResConfiMgr = (PnrResConfigMgr) getBean("PnrResConfigMgr");
		//id表示当前对象的Id
		String id = StaticMethod.null2String(request.getParameter("seldelcar"));
		PnrResConfig pnrResConfig = pnrResConfiMgr.find(id);
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
    	List city = PartnerCityByUser.getCityByProvince(province);    	
    	request.setAttribute("city", city);
		request.setAttribute("pnrResConfig", pnrResConfig);
		String subResTable =  pnrResConfig.getSubResTable();
		
		if("pnr_res_station".equals(subResTable)){//基站设备及配套
			PnrResConfigStationMgr pnrResConfigStationMgr = (PnrResConfigStationMgr)getBean("PnrResConfigStationMgr");
			PnrResConfigStation pnrResConfigStation = new PnrResConfigStation();
			if(!StringUtils.isEmpty(pnrResConfig.getSubResId())){
				pnrResConfigStation = pnrResConfigStationMgr.find(pnrResConfig.getSubResId());
			}
			request.setAttribute("pnrResConfigStation", pnrResConfigStation);
		}else if("pnr_res_repeaters".equals(subResTable)){//直放站
			PnrResRepeatersMgr pnrResRepeatersMgr = (PnrResRepeatersMgr) getBean("PnrResRepeatersMgr");
			PnrResRepeaters pnrResRepeaters = new PnrResRepeaters();
			if(!StringUtils.isEmpty(pnrResConfig.getSubResId())){
				pnrResRepeaters = pnrResRepeatersMgr.find(pnrResConfig.getSubResId());
			}
			request.setAttribute("pnrResRepeaters", pnrResRepeaters);
		}else if("pnr_res_iron".equals(subResTable)){//铁搭
			PnrResIronMgr PnrResIronMgr = (PnrResIronMgr) getBean("PnrResIronMgr");		
			PnrResIron pnrResIron = new PnrResIron();
			if(!StringUtils.isEmpty(pnrResConfig.getSubResId())){
				pnrResIron = PnrResIronMgr.find(pnrResConfig.getSubResId());
			}
			request.setAttribute("pnrResIron", pnrResIron);
		}else if("pnr_res_jieke".equals(subResTable)){//集客家客
			PnrResJiekeMgr PnrResJiekeMgr = (PnrResJiekeMgr)getBean("PnrResJiekeMgr");
			PnrResJieke pnrResJieke = new PnrResJieke();
			if(!StringUtils.isEmpty(pnrResConfig.getSubResId())){
				pnrResJieke = PnrResJiekeMgr.find(pnrResConfig.getSubResId());
			}
			request.setAttribute("pnrResJieke", pnrResJieke);
		}else if("pnr_res_line".equals(subResTable)){//传输线路
			PnrResLineMgr PnrResLineMgr = (PnrResLineMgr)getBean("PnrResLineMgr");
			PnrResLine pnrResLine = new PnrResLine();
			if(!StringUtils.isEmpty(pnrResConfig.getSubResId())){
				pnrResLine = PnrResLineMgr.find(pnrResConfig.getSubResId());
			}
			request.setAttribute("pnrResLine", pnrResLine);
		}else if("pnr_res_wlan".equals(subResTable)){//WLAN
			
			PnrResWlanMgr pnrResWlanMgr = (PnrResWlanMgr)getBean("PnrResWlanMgr");
			PnrResWlan pnrResWlan = new PnrResWlan();
			if(!StringUtils.isEmpty(pnrResConfig.getSubResId())){
				pnrResWlan = pnrResWlanMgr.find(pnrResConfig.getSubResId());
			}
			request.setAttribute("pnrResWlan", pnrResWlan);
		}else if("pnr_res_family_broadband".equals(subResTable)){//家庭宽带
			PnrResFamilyBroadbandMgr pnrResFamilyBroadbandMgr = (PnrResFamilyBroadbandMgr)getBean("PnrResFamilyBroadbandMgr");
			PnrResFamilyBroadband pnrResFamilyBroadband = new PnrResFamilyBroadband();
			if(!StringUtils.isEmpty(pnrResConfig.getSubResId())){
				pnrResFamilyBroadband = pnrResFamilyBroadbandMgr.find(pnrResConfig.getSubResId());
			}
			request.setAttribute("pnrResFamilyBroadband", pnrResFamilyBroadband);
		}
		
		return mapping.findForward("edit");
	}
	
	/**
	 * 增加一个维护人员与站点的关系
	 */
	public ActionForward assignCheckPer(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		String assign="";   //资源分配情况
		List city = PartnerCityByUser.getCityByProvince(province);    	
		request.setAttribute("city", city);
		PnrResConfigMgr pnrResConfiMgr = (PnrResConfigMgr) getBean("PnrResConfigMgr");
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		PnrResConfigForm pnrResConfigForm = (PnrResConfigForm) form;
		
		String resName = StaticMethod.nullObject2String(request.getParameter("resNameStringLike"));
		String specialty = StaticMethod.nullObject2String(request.getParameter("specialtyStringEqual"));
		String resType = StaticMethod.nullObject2String(request.getParameter("resTypeStringEqual"));
		String resLevel = StaticMethod.nullObject2String(request.getParameter("resLevelStringEqual"));
		String cityString = StaticMethod.nullObject2String(request.getParameter("cityStringEqual"));
		String countryString = StaticMethod.nullObject2String(request.getParameter("countryStringEqual"));
		//添加小组条件
		String executeObjectString = StaticMethod.nullObject2String(request.getParameter("executeObjectStringEqual"));
		
		pnrResConfigForm.setResName(resName);
		pnrResConfigForm.setSpecialty(specialty);
		pnrResConfigForm.setResType(resType);
		pnrResConfigForm.setCity(cityString);
		pnrResConfigForm.setCountry(countryString);
		pnrResConfigForm.setResLevel(resLevel);
		//添加小组条件
		pnrResConfigForm.setExecuteObject(executeObjectString);
		
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		
//		PartnerUserMgr partnerUserMgr = (PartnerUserMgr) getBean("partnerUserMgr");
//		List<PartnerUser> user = partnerUserMgr.getPartnerUsers(" and userId='"+sessionForm.getUserid()+"'");
		HashMap<String, String> map = (HashMap<String, String>)PartnerPrivUtils.userIsPersonnel(request);
		String flag = map.get("isPersonnel");
		
		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(), search);
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		PnrDeviceInspectSwitchConfig transLineswitch = (PnrDeviceInspectSwitchConfig) request.getSession().getServletContext().getAttribute("pnrInspect2SwitchConfig");
		
		if(transLineswitch.isOpenTransLineInspect()){
			search.addFilterNotEqual("specialty", "1122502");
		}
		
		assign = request.getParameter("assign");
		if("yes".equals(assign)){
// 			search.addFilterNotEmpty("executeObject");
			CommonSqlHelper.formatSearchNotEmpty(search, "executeObject");
			request.setAttribute("assign", "yes");  
		}else if("no".equals(assign)){
			search.addFilterEmpty("executeObject");
			request.setAttribute("assign", "no");
		}else{
			request.setAttribute("assign", "all");
		}
		if(flag.equals("y")){  //是代维人员
			PartnerDeptMgr partnerDeptMgr=(PartnerDeptMgr)getBean("partnerDeptMgr");
			List<PartnerDept> dept = partnerDeptMgr.getPartnerDepts("and deptMagId='"+sessionForm.getDeptid()+"'");
			search.addFilterLike("country", dept.get(0).getAreaId()+"%");
			search.addFilterLike("executeDept", sessionForm.getDeptid()+"%");
		}else if(!"admin".equals(sessionForm.getUserid())){//是移动人员
			String aa=sessionForm.getDeptid();
			TawSystemDeptBo deptbo = TawSystemDeptBo.getInstance();
			TawSystemDept sept = deptbo.getDeptinfobydeptid(aa, "0");
			search.addFilterLike("country",sept.getAreaid() +"%");//由于移动人员没有权限分配执行对象，当前不能查出数据
		}
		SearchResult<PnrResConfig> result = pnrResConfiMgr.searchAndCount(search);
		request.setAttribute("list",result.getResult());
		request.setAttribute("pnrResConfigForm", pnrResConfigForm);
		request.setAttribute("pageSize", CommonConstants.PAGE_SIZE);
		request.setAttribute("resultSize", result.getTotalCount());
		return mapping.findForward("assignCheckPer");
	}
	
	/**
	 * 查询资源分配情况
	 */
	public ActionForward assignCheck(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		String assign="";   //资源分配情况
    	List city = PartnerCityByUser.getCityByProvince(province);    	
    	request.setAttribute("city", city);
    	PnrResConfigMgr pnrResConfiMgr = (PnrResConfigMgr) getBean("PnrResConfigMgr");
    	String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		PnrResConfigForm pnrResConfigForm = (PnrResConfigForm) form;
		
		String resName = StaticMethod.nullObject2String(request.getParameter("resNameStringLike"));
		String specialty = StaticMethod.nullObject2String(request.getParameter("specialtyStringEqual"));
		String resType = StaticMethod.nullObject2String(request.getParameter("resTypeStringEqual"));
		String resLevel = StaticMethod.nullObject2String(request.getParameter("resLevelStringEqual"));
		String cityString = StaticMethod.nullObject2String(request.getParameter("cityStringEqual"));
		String countryString = StaticMethod.nullObject2String(request.getParameter("countryStringEqual"));
		//添加小组条件
		String executeObjectString = StaticMethod.nullObject2String(request.getParameter("executeObjectStringEqual"));
		
		pnrResConfigForm.setResName(resName);
		pnrResConfigForm.setSpecialty(specialty);
		pnrResConfigForm.setResType(resType);
		pnrResConfigForm.setCity(cityString);
		pnrResConfigForm.setCountry(countryString);
		pnrResConfigForm.setResLevel(resLevel);
		//添加小组条件
		pnrResConfigForm.setExecuteObject(executeObjectString);
		
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		
//		PartnerUserMgr partnerUserMgr = (PartnerUserMgr) getBean("partnerUserMgr");
//		List<PartnerUser> user = partnerUserMgr.getPartnerUsers(" and userId='"+sessionForm.getUserid()+"'");
		HashMap<String, String> map = (HashMap<String, String>)PartnerPrivUtils.userIsPersonnel(request);
		String flag = map.get("isPersonnel");
		
		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(), search);
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		PnrDeviceInspectSwitchConfig transLineswitch = (PnrDeviceInspectSwitchConfig) request.getSession().getServletContext().getAttribute("pnrInspect2SwitchConfig");
		
		if(transLineswitch.isOpenTransLineInspect()){
			search.addFilterNotEqual("specialty", "1122502");
		}
		
		assign = request.getParameter("assign");
		if("yes".equals(assign)){
// 			search.addFilterNotEmpty("executeObject");
			CommonSqlHelper.formatSearchNotEmpty(search, "executeObject");
			request.setAttribute("assign", "yes");  
		}else if("no".equals(assign)){
			search.addFilterEmpty("executeObject");
			request.setAttribute("assign", "no");
		}else{
			request.setAttribute("assign", "all");
		}
		if(flag.equals("y")){  //是代维人员
			PartnerDeptMgr partnerDeptMgr=(PartnerDeptMgr)getBean("partnerDeptMgr");
			List<PartnerDept> dept = partnerDeptMgr.getPartnerDepts("and deptMagId='"+sessionForm.getDeptid()+"'");
			search.addFilterLike("country", dept.get(0).getAreaId()+"%");
			search.addFilterLike("executeDept", sessionForm.getDeptid()+"%");
		}else{//是移动人员
			String aa=sessionForm.getDeptid();
			TawSystemDeptBo deptbo = TawSystemDeptBo.getInstance();
			TawSystemDept sept = deptbo.getDeptinfobydeptid(aa, "0");
			search.addFilterLike("country",sept.getAreaid() +"%");//由于移动人员没有权限分配执行对象，当前不能查出数据
		}
		SearchResult<PnrResConfig> result = pnrResConfiMgr.searchAndCount(search);
    	request.setAttribute("list",result.getResult());
    	request.setAttribute("pnrResConfigForm", pnrResConfigForm);
		request.setAttribute("pageSize", CommonConstants.PAGE_SIZE);
		request.setAttribute("resultSize", result.getTotalCount());
		return mapping.findForward("assign");
	}
	
	/**
	 * 按选择项分配执行对象是，打开子页面
	 */
	public ActionForward window(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String seldelcar = request.getParameter("seldelcar");
		request.setAttribute("seldelcar", seldelcar);
		return mapping.findForward("window");
	}
	/**
	 * 按选择项分配执行对象是，打开子页面---分配责任人
	 */
	public ActionForward assignCheckPerWindow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String seldelcar = request.getParameter("seldelcar");
		request.setAttribute("seldelcar", seldelcar);
		return mapping.findForward("assignCheckPerWindow");
	}
	
	/**
	 * 对选择的项，添加分配责任人
	 */
	public void addAssignCheckPer(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		PnrResConfigMgr pnrResConfigMgr = (PnrResConfigMgr) getBean("PnrResConfigMgr");
		PrintWriter pw =  response.getWriter();
		String selcar = StaticMethod.null2String(request
				.getParameter("seldelcar"));
		String prov = StaticMethod.null2String(request.getParameter("prov"));
				
		try {
			String[] sel = selcar.split("\\|");
			for (int i = 0; i < sel.length; i++) {
				PnrResConfig pnrResConfig = pnrResConfigMgr.find(sel[i]);
				pnrResConfig.setChargePerson(prov);
				pnrResConfigMgr.save(pnrResConfig);
			}
			pw.print("{'success': true, 'msg': '分配成功！'}");
		} catch (Exception e) {
			pw.print("{'faild': true, 'msg': '分配失败！'}");
		}
	}
	/**
	 * 对选择的项，添加执行对象
	 */
	public void addExecuteObject(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		PnrResConfigMgr pnrResConfigMgr = (PnrResConfigMgr) getBean("PnrResConfigMgr");
		PrintWriter pw =  response.getWriter();
		String selcar = StaticMethod.null2String(request
				.getParameter("seldelcar"));
		String prov = StaticMethod.null2String(request.getParameter("prov"));
		
		PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr)getBean("partnerDeptMgr");
		String deptMagId = partnerDeptMgr.getPartnerDept(prov).getDeptMagId();
		
		try {
			String[] sel = selcar.split("\\|");
			for (int i = 0; i < sel.length; i++) {
				PnrResConfig pnrResConfig = pnrResConfigMgr.find(sel[i]);
				pnrResConfig.setExecuteObject(prov);
				pnrResConfig.setExecuteDept(deptMagId);
				pnrResConfig.setExecuteType(ResourceConstants.EXECUTETYPE_DEPARTMENT+"");
				pnrResConfigMgr.save(pnrResConfig);
			}
			pw.print("{'success': true, 'msg': '分配成功！'}");
		} catch (Exception e) {
			pw.print("{'faild': true, 'msg': '分配失败！'}");
		}
	}
	
	/**
	 * 按查询条件添加执行对象
	 */
	public void addAllExecuteObject(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		PnrResConfigMgr pnrResConfigMgr = (PnrResConfigMgr) getBean("PnrResConfigMgr");
		String prov = StaticMethod.null2String(request.getParameter("prov"));
		String selcar = StaticMethod.null2String(request
				.getParameter("seldelcar"));
		PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr)getBean("partnerDeptMgr");
		String deptMagId = partnerDeptMgr.getPartnerDept(prov).getDeptMagId();
		pnrResConfigMgr.updateAllEntity(PnrResConfig.class,"", " update PnrResConfig set executeObject='"+prov+"' , executeDept='"+deptMagId+"' "+selcar+" and (executeObject='' or executeObject is null)");
		String seldelcar = request.getParameter("seldelcar");
		request.setAttribute("seldelcar", seldelcar);
		PrintWriter pw =  response.getWriter();
		try {
			pw.print("{'success': true, 'msg': '分配成功！'}");
		} catch (Exception e) {
			pw.print("{'faild': true, 'msg': '分配失败！'}");
		}
	}
	
	/**
	 * 跳转到按查询条件添加执行对象的界面
	 */
	public ActionForward conditionWindow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String seldelcar = request.getParameter("seldelcar");
		request.setAttribute("seldelcar", seldelcar);
		return mapping.findForward("conditionWindow");
	}
	
	/**
	 * 取消所选项的执行对象
	 */
	public ActionForward calcleExecuteObject(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		PnrResConfigForm pnrResConfigForm = (PnrResConfigForm) form;
		String province = pnrBaseAreaIdList.getRootAreaId();
    	List city = PartnerCityByUser.getCityByProvince(province); 
		PnrResConfigMgr pnrResConfigMgr = (PnrResConfigMgr) getBean("PnrResConfigMgr");
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		String selcar = StaticMethod.null2String(request
				.getParameter("seldelcar"));
		String[] sel = selcar.split("\\|");
		String transLine = "";
		for (int i = 0; i < sel.length&&!StringUtils.isEmpty(sel[0]); i++) {
			PnrResConfig pnrResConfig = pnrResConfigMgr.find(sel[i]);
			if(pnrResConfig.getSpecialty().equals("1122502")){
				transLine = "yes";
			}
			pnrResConfig.setExecuteObject("");
			pnrResConfig.setExecuteType("");
			pnrResConfig.setExecuteDept("");
			pnrResConfigMgr.save(pnrResConfig);
		}
		String whereStr ="";
		String assign = request.getParameter("assing2");
		if("yes".equals(transLine)){
			whereStr = " where specialty = '1122502'";
		}else{
			whereStr = " where specialty != '1122502'";
		}
//		String whereStr = " where 1=1 ";
		if(pnrResConfigForm.getResName()!="" && pnrResConfigForm.getResName()!=null){
			whereStr = whereStr+" and resName like '%"+pnrResConfigForm.getResName()+"%'";
		}
		if(pnrResConfigForm.getSpecialty() !="" && pnrResConfigForm.getSpecialty()!=null){
			whereStr = whereStr+" and specialty='"+pnrResConfigForm.getSpecialty()+"'";
		}
		if(pnrResConfigForm.getResType()!="" && pnrResConfigForm.getResType()!=null){
			whereStr = whereStr+" and resType='"+pnrResConfigForm.getResType()+"'";
		}
		if(pnrResConfigForm.getResLevel()!="" && pnrResConfigForm.getResLevel()!=null){
			whereStr = whereStr+" and resLevel='"+pnrResConfigForm.getResLevel()+"'";
		}
		if(pnrResConfigForm.getCity()!="" && pnrResConfigForm.getCity()!=null){
			whereStr = whereStr+" and country='"+pnrResConfigForm.getCity()+"'";
			pnrResConfigForm.setCountry(pnrResConfigForm.getCity());
		}
		if(request.getParameter("region")!="" && request.getParameter("region")!=null){
			whereStr = whereStr+" and city='"+request.getParameter("region")+"'";
			pnrResConfigForm.setCity(request.getParameter("region"));
		}
		if("all".equals(assign)){
			request.setAttribute("assign", "all"); 
		}else if("yes".equals(assign)){
			
//			whereStr = whereStr+" and (executeObject is not null) and (executeObject!='' ) ";
			whereStr = whereStr+" and "+CommonSqlHelper.formatNotEmpty("executeObject");
			request.setAttribute("assign", "yes");      //设置默认显示的是分配了的还是没有分配的 
		}
		Map map = pnrResConfigMgr.getResources(firstResult*CommonConstants.PAGE_SIZE, CommonConstants.PAGE_SIZE, whereStr);
    	request.setAttribute("list",map.get("result"));
    	request.setAttribute("pnrResConfigForm", pnrResConfigForm);
		request.setAttribute("pageSize", CommonConstants.PAGE_SIZE);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("city", city);
		return mapping.findForward("assign");
	}
	
	/**
	 * 取消所选项的站点责任人
	 */
	public ActionForward calcleChargePerson(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		PnrResConfigForm pnrResConfigForm = (PnrResConfigForm) form;
		String province = pnrBaseAreaIdList.getRootAreaId();
		List city = PartnerCityByUser.getCityByProvince(province); 
		PnrResConfigMgr pnrResConfigMgr = (PnrResConfigMgr) getBean("PnrResConfigMgr");
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		String selcar = StaticMethod.null2String(request
				.getParameter("seldelcar"));
		String[] sel = selcar.split("\\|");
		String transLine = "";
		for (int i = 0; i < sel.length&&!StringUtils.isEmpty(sel[0]); i++) {
			PnrResConfig pnrResConfig = pnrResConfigMgr.find(sel[i]);
			if(pnrResConfig.getSpecialty().equals("1122502")){
				transLine = "yes";
			}
			pnrResConfig.setChargePerson("");
			pnrResConfigMgr.save(pnrResConfig);
		}
		String whereStr ="";
		String assign = request.getParameter("assing2");
		if("yes".equals(transLine)){
			whereStr = " where specialty = '1122502'";
		}else{
			whereStr = " where specialty != '1122502'";
		}
//		String whereStr = " where 1=1 ";
		if(pnrResConfigForm.getResName()!="" && pnrResConfigForm.getResName()!=null){
			whereStr = whereStr+" and resName like '%"+pnrResConfigForm.getResName()+"%'";
		}
		if(pnrResConfigForm.getSpecialty() !="" && pnrResConfigForm.getSpecialty()!=null){
			whereStr = whereStr+" and specialty='"+pnrResConfigForm.getSpecialty()+"'";
		}
		if(pnrResConfigForm.getResType()!="" && pnrResConfigForm.getResType()!=null){
			whereStr = whereStr+" and resType='"+pnrResConfigForm.getResType()+"'";
		}
		if(pnrResConfigForm.getResLevel()!="" && pnrResConfigForm.getResLevel()!=null){
			whereStr = whereStr+" and resLevel='"+pnrResConfigForm.getResLevel()+"'";
		}
		if(pnrResConfigForm.getCity()!="" && pnrResConfigForm.getCity()!=null){
			whereStr = whereStr+" and country='"+pnrResConfigForm.getCity()+"'";
			pnrResConfigForm.setCountry(pnrResConfigForm.getCity());
		}
		if(request.getParameter("region")!="" && request.getParameter("region")!=null){
			whereStr = whereStr+" and city='"+request.getParameter("region")+"'";
			pnrResConfigForm.setCity(request.getParameter("region"));
		}
		if("all".equals(assign)){
			request.setAttribute("assign", "all"); 
		}else if("yes".equals(assign)){
			
//			whereStr = whereStr+" and (executeObject is not null) and (executeObject!='' ) ";
			whereStr = whereStr+" and "+CommonSqlHelper.formatNotEmpty("executeObject");
			request.setAttribute("assign", "yes");      //设置默认显示的是分配了的还是没有分配的 
		}
		Map map = pnrResConfigMgr.getResources(firstResult*CommonConstants.PAGE_SIZE, CommonConstants.PAGE_SIZE, whereStr);
		request.setAttribute("list",map.get("result"));
		request.setAttribute("pnrResConfigForm", pnrResConfigForm);
		request.setAttribute("pageSize", CommonConstants.PAGE_SIZE);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("city", city);
		return mapping.findForward("assignCheckPer");
	}
	
	/**
	 * 跳转到巡检周期的界面
	 */
	public ActionForward cycle(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
    	List city = PartnerCityByUser.getCityByProvince(province);    	
    	PnrResConfigMgr pnrResConfigMgr = (PnrResConfigMgr) getBean("PnrResConfigMgr");
    	String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		Map map = pnrResConfigMgr.getResources(firstResult*CommonConstants.PAGE_SIZE, CommonConstants.PAGE_SIZE, "");
    	request.setAttribute("list",map.get("result"));
		request.setAttribute("pageSize", CommonConstants.PAGE_SIZE);
		request.setAttribute("resultSize", map.get("total"));
    	request.setAttribute("city", city);
		return mapping.findForward("cycle");
	}
	
	/**
	 * 查询资源的周期分配情况
	 */
	public ActionForward cycleCheck(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		String assign="";   //资源分配情况
    	List city = PartnerCityByUser.getCityByProvince(province);    	
    	request.setAttribute("city", city);
    	TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
    	
    	
//    	PartnerUserMgr partnerUserMgr = (PartnerUserMgr) getBean("partnerUserMgr");
//		List<PartnerUser> user = partnerUserMgr.getPartnerUsers(" and userId='"+sessionForm.getUserid()+"'");
    	
    	
    	PnrResConfigMgr pnrResConfiMgr = (PnrResConfigMgr) getBean("PnrResConfigMgr");
    	String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		PnrResConfigForm pnrResConfigForm = (PnrResConfigForm) form;
		
		String resName = StaticMethod.nullObject2String(request.getParameter("resNameStringLike"));
		String specialty = StaticMethod.nullObject2String(request.getParameter("specialtyStringEqual"));
		String resType = StaticMethod.nullObject2String(request.getParameter("resTypeStringEqual"));
		String resLevel = StaticMethod.nullObject2String(request.getParameter("resLevelStringEqual"));
		String cityString = StaticMethod.nullObject2String(request.getParameter("cityStringEqual"));
		String countryString = StaticMethod.nullObject2String(request.getParameter("countryStringEqual"));
		pnrResConfigForm.setResName(resName);
		pnrResConfigForm.setSpecialty(specialty);
		pnrResConfigForm.setResType(resType);
		pnrResConfigForm.setCity(cityString);
		pnrResConfigForm.setCountry(countryString);
		pnrResConfigForm.setResLevel(resLevel);
		
		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(), search);
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		PnrDeviceInspectSwitchConfig transLineswitch = (PnrDeviceInspectSwitchConfig) request.getSession().getServletContext().getAttribute("pnrInspect2SwitchConfig");
		if(transLineswitch.isOpenTransLineInspect()){
			search.addFilterNotEqual("specialty", "1122502");
		}
		HashMap<String, String> map = (HashMap<String, String>)PartnerPrivUtils.userIsPersonnel(request);
		String flag = map.get("isPersonnel");
		
		assign = request.getParameter("assign");
		if("no".equals(assign)){
			search.addFilterEmpty("inspectCycle");
			request.setAttribute("assign", "no");  
		}else if("yes".equals(assign)){
//			search.addFilterNotEmpty("inspectCycle");
			CommonSqlHelper.formatSearchNotEmpty(search, "inspectCycle");
			request.setAttribute("assign", "yes");
		}else{
			request.setAttribute("assign", "all");
		}
		
		
		if("admin".equals(sessionForm.getUserid())){
			
		}else if(flag.equals("y")){ //此时是代维人员
			search.addFilterLike("country", "a");//由于代维人员没有权限分配周期，当前不能查出数据
		}else{//此时是移动人员
			ITawSystemDeptManager deptMgr = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
			TawSystemDept sysdept = deptMgr.getTawSystemDept(sessionForm.getDeptpriid());
			
			search.addFilterLike("country", sysdept.getAreaid()+"%");  
		}
		
		SearchResult<PnrResConfig> result = pnrResConfiMgr.searchAndCount(search);
		
    	request.setAttribute("list",result.getResult());
    	request.setAttribute("pnrResConfigForm", pnrResConfigForm);
		request.setAttribute("pageSize", CommonConstants.PAGE_SIZE);
		request.setAttribute("cycleMap", InspectConstants.cycleMap);
		request.setAttribute("resultSize", result.getTotalCount());
		return mapping.findForward("cycle");
	}
	/**
	 * 查询巡检周期分配情况
	 */
	public ActionForward cycleCheckCondition(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		ITawSystemDictTypeManager itawSystemDictTypeManager = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");
	
		String assign="all";   //资源情况
//		从页面获取查询信息
//		巡检专业
		String specialty = StaticMethod.nullObject2String(request.getParameter("specialtyStringEqual"));
		request.setAttribute("specialty", specialty);
		
//		资源级别
		String resType = StaticMethod.nullObject2String(request.getParameter("resTypeStringEqual"));
		request.setAttribute("resType", resType);
		
//		资源类别
		String resLevel = StaticMethod.nullObject2String(request.getParameter("resLevelStringEqual"));
		request.setAttribute("resLevel", resLevel);
		
		PnrResToWeekTimeMgr pnrResToWeekTimeMgr = (PnrResToWeekTimeMgr)getBean("PnrResToWeekTimeMgr");

		assign = request.getParameter("assign");
		//		接收数据
		List<PnrResSetWeekTime> s =null;
		
//		分页
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
	    int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		
	    StringBuffer sql = new StringBuffer();
	    
	    sql.append(" from TawSystemDictType s where s.dictId like ? and s.leaf = '1'");
		
	    if("no".equals(assign)){
	    	sql.append(" and not exists (select 1 from PnrResToWeekTime p where p.pnrID = s.dictId )");
			request.setAttribute("assign", "no"); 
			
		}else if("yes".equals(assign)){
			
	    	sql.append(" and  exists (select 1 from PnrResToWeekTime p where p.pnrID = s.dictId )");
			request.setAttribute("assign", "yes");
		}else{
			request.setAttribute("assign", "all");
		}
		
	    String[] string = new String[1];
		if(!"".equals(resLevel)){
			
			string[0]=resLevel+"%";	
		}else if(!"".equals(resType)){
			
			string[0]=resType+"%";	
		
		}else if(!"".equals(specialty)){	
			string[0]=specialty+"%";	

		}else{
			//默认查所有的
			string[0]="11225%";
		}
		PageModel p =itawSystemDictTypeManager.searchPaginated(sql.toString(),string, firstResult * CommonConstants.PAGE_SIZE, CommonConstants.PAGE_SIZE);
		s=pnrResToWeekTimeMgr.getTawSystemDictTypeByDictId(p.getDatas());
		
		// 11234 是周期的父级ID
		Map<String,String> map =pnrResToWeekTimeMgr.getTawSystemDictTypeByParentID("11234");
		
		request.setAttribute("list",s);
		request.setAttribute("pageSize", CommonConstants.PAGE_SIZE);
		request.setAttribute("cycleMap", map);
		request.setAttribute("resultSize", p.getTotal());
		return mapping.findForward("cycleCheckCondition");
	}
	
	/**
	 * 查询巡检周期分配情况
	 *//*
	public ActionForward cycleCheckConditionTest(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		String assign="all";   //资源情况
//		从页面获取查询信息
//		巡检专业
		String specialty = StaticMethod.nullObject2String(request.getParameter("specialtyStringEqual"));
		request.setAttribute("specialty", specialty);

//		资源级别
		String resType = StaticMethod.nullObject2String(request.getParameter("resTypeStringEqual"));
		request.setAttribute("resType", resType);

//		资源类别
		String resLevel = StaticMethod.nullObject2String(request.getParameter("resLevelStringEqual"));
		request.setAttribute("resLevel", resLevel);

		
		//resLevel ="112250102";
		//specialty ="112250102";
		// resType="1122501";
		PnrResToWeekTimeMgr pnrResToWeekTimeMgr = (PnrResToWeekTimeMgr)getBean("PnrResToWeekTimeMgr");
		// public  Map getDataList 分页可以借鉴
		assign = request.getParameter("assign");
	
//		接收数据
		List<PnrResSetWeekTime> s =null;
		if(!"".equals(resLevel)){
//			 先看最后一级 
			 s= pnrResToWeekTimeMgr.getTawSystemDictTypeByDictId(resLevel,3,assign);			
			
			
		}else if(!"".equals(resType)){
			s= pnrResToWeekTimeMgr.getTawSystemDictTypeByDictId(resType,2,assign);
			
			
		}else if(!"".equals(specialty)){
			s= pnrResToWeekTimeMgr.getTawSystemDictTypeByDictId(specialty,1,assign);
			
		}
//		分页
		 String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		 int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		
		
		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(), search);
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		
	//	HashMap<String, String> map = (HashMap<String, String>)PartnerPrivUtils.userIsPersonnel(request);
	//	Search search = new Search();
	//	search.a
		if("no".equals(assign)){
		//	search.addFilterEmpty("inspectCycle");
			request.setAttribute("assign", "no");  
		}else if("yes".equals(assign)){
		//	CommonSqlHelper.formatSearchNotEmpty(search, "inspectCycle");
			request.setAttribute("assign", "yes");
		}else{
			request.setAttribute("assign", "all");
		}
		
		
	// SearchResult<PnrResSetWeekTime> result = pnrResToWeekTimeMgr.searchAndCount(search);
		// 11234 是周期的父级ID
		Map<String,String> map =pnrResToWeekTimeMgr.getTawSystemDictTypeByParentID("11234");
		int resultSize=0;
		if(s!=null){
			resultSize = s.size();
		}
	//	request.setAttribute("list",result.getResult());
		request.setAttribute("list",s);
	///	request.setAttribute("pnrResConfigForm", pnrResConfigForm);
		request.setAttribute("pageSize", CommonConstants.PAGE_SIZE);
	//	request.setAttribute("cycleMap", InspectConstants.cycleMap);
		request.setAttribute("cycleMap", map);
	//	request.setAttribute("resultSize", result.getTotalCount());
		request.setAttribute("resultSize", resultSize);
		return mapping.findForward("cycleCheckCondition");
	}
	*/
	
	/**
	 * 资源的周期分配情况页面
	 */
	public ActionForward cycleAddWindow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
    	List city = PartnerCityByUser.getCityByProvince(province);    	
    	request.setAttribute("city", city);
		return mapping.findForward("cycle");
	}
	
	/**
	 * 跳转到周期分配界面
	 */
	public ActionForward cycleWindow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
    	String seldelcar = request.getParameter("seldelcar");
		request.setAttribute("seldelcar", seldelcar);
		request.setAttribute("cycleMap", InspectConstants.cycleMap);
		return mapping.findForward("cycleWindow");
	}
	
	/**
	 * 跳转到周期分配界面-巡检周期配置-chenbing
	 */
	public ActionForward cycleWindowWeekTime(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String seldelcar = request.getParameter("seldelcar");
		request.setAttribute("seldelcar", seldelcar);
		PnrResToWeekTimeMgr pnrResConfigMgr = (PnrResToWeekTimeMgr) getBean("PnrResToWeekTimeMgr");

		Map<String,String> map =pnrResConfigMgr.getTawSystemDictTypeByParentID("11234");
		request.setAttribute("cycleMap", map);
		return mapping.findForward("cycleWindow");
	}
	
	/**
	 * 保存和修改巡检周期
	 */
	public void updateCycle(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		PnrResConfigMgr pnrResConfigMgr = (PnrResConfigMgr) getBean("PnrResConfigMgr");
		String selcar = StaticMethod.null2String(request
				.getParameter("seldelcar"));
		String prov = StaticMethod.null2String(request.getParameter("prov"));
		try {
			if("all".equals(StaticMethod.nullObject2String(request.getParameter("type")))){
				
				
				String resName = StaticMethod.nullObject2String(request.getParameter("resNameStringLike"));
				String specialty = StaticMethod.nullObject2String(request.getParameter("specialtyStringEqual"));
				String resType = StaticMethod.nullObject2String(request.getParameter("resTypeStringEqual"));
				String resLevel = StaticMethod.nullObject2String(request.getParameter("resLevelStringEqual"));
				String cityString = StaticMethod.nullObject2String(request.getParameter("cityStringEqual"));
				String countryString = StaticMethod.nullObject2String(request.getParameter("countryStringEqual"));
				
//				pnrResConfigMgr.updateAllEntity(PnrResConfig.class,"", " update PnrResConfig set executeObject='"+prov+"' , executeDept='"+deptMagId+"' "+selcar+" and (executeObject='' or executeObject is null)");

				String updateSql = "update PnrResConfig set inspectCycle='"+prov+"'";
//				 where id='8ab03ec73b9dd815013b9dde800d0099'
				boolean addWhere = false;
				if(!"".equals(resName)){
					updateSql+="  where resName like '%"+resName+"%'   ";
					addWhere = true;
				}
				if(!"".equals(specialty)){
					if(!addWhere){
						updateSql+="  where specialty='"+specialty+"'   ";
						addWhere = true;
					}else{
						updateSql+="  and specialty='"+specialty+"'   ";
					}
				}
				if(!"".equals(resType)){
					if(!addWhere){
						updateSql+="  where resType='"+resType+"'   ";
						addWhere = true;
					}else{
						updateSql+="  and resType='"+resType+"'   ";
					}
				}
				if(!"".equals(resLevel)){
					if(!addWhere){
						updateSql+="  where resLevel='"+resLevel+"'   ";
						addWhere = true;
					}else{
						updateSql+="  and resLevel='"+resLevel+"'   ";
					}
				}
				if(!"".equals(cityString)){
					if(!addWhere){
						updateSql+="  where city='"+cityString+"'   ";
						addWhere = true;
					}else{
						updateSql+="  and city='"+cityString+"'   ";
					}
				}
				if(!"".equals(countryString)){
					if(!addWhere){
						updateSql+="  where country='"+countryString+"'   ";
						addWhere = true;
					}else{
						updateSql+="  and country='"+countryString+"'   ";
					}
				}
				if(!addWhere){//如果没有条件,则不允许更新周期
					response.getWriter().write(MobileConstants.failureStr);
					return;
				}
				updateSql+=" and (  inspectCycle ='' or inspectCycle is null   )";
				
//				pnrResConfigMgr.updateAllEntity(PnrResConfig.class,"", " update PnrResConfig set executeObject='"+prov+"' , executeDept='"+deptMagId+"' "+selcar+" and (executeObject='' or executeObject is null)");
				
				pnrResConfigMgr.updateAllEntity(PnrResConfig.class,""," "+ updateSql);
				String seldelcar = request.getParameter("seldelcar");
				request.setAttribute("seldelcar", seldelcar);
			}else{
				String[] sel = selcar.split("\\|");
				for (int i = 0; i < sel.length; i++) {
					PnrResConfig pnrResConfig = pnrResConfigMgr.find(sel[i]);
					pnrResConfig.setInspectCycle(prov);
					pnrResConfigMgr.save(pnrResConfig);
				}
			}
			response.getWriter().write(MobileConstants.successStr);
		} catch (Exception e) {
			response.getWriter().write(MobileConstants.successStr);
		}
	}
	/**
	 * 保存和修改巡检周期 --chenbing 2013-06-27
	 */
	public void updateCycleWeekTime(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		PnrResToWeekTimeMgr pnrResConfigMgr = (PnrResToWeekTimeMgr) getBean("PnrResToWeekTimeMgr");
//		不明白什么意思
		String selcar = StaticMethod.null2String(request
				.getParameter("seldelcar"));
		// 获取到的周期
		String prov = StaticMethod.null2String(request.getParameter("prov"));
		//在改动周期的同时 ，在资源存在的情况下同时更改资源的周期
		PnrResConfigDao pnrResConfigDao = (PnrResConfigDao) getBean("pnrResConfigDao");
		IInspectPlanResMgr inspectPlanResMgr =(IInspectPlanResMgr)getBean("inspectPlanResMgr");
		ITawSystemDictTypeManager tawSystemDictTypeManager = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");
		StringBuffer sql = new StringBuffer();		
		sql.append("update PnrResConfig p set p.inspectCycle=? where p.resLevel=?");
		String[] values= new String[2]; 
		
		try {
				String[] sel = selcar.split("\\|");
				PnrResToWeekTime pnrResConfig;
				for (int i = 0; i < sel.length; i++) {
					//找到对象 保存  sel 是选中的ID
					pnrResConfig= new PnrResToWeekTime();
					pnrResConfig.setWeekID(prov);
					pnrResConfig.setPnrID(sel[i]);
					
				//	PnrResConfig pnrResConfig = pnrResConfigMgr.find(sel[i]);
				//	pnrResConfig.setInspectCycle(prov);
					//1）先删除
					pnrResConfigMgr.removeByPnrId(sel[i]);
					//2）在保存
					pnrResConfigMgr.save(pnrResConfig);
					//保存完周期配置 开始更新资源
					TawSystemDictType t =tawSystemDictTypeManager.getDictByDictId(prov);
					values[0]=t.getDictCode();
					values[1]=sel[i];					
				    pnrResConfigDao.bulkUpdateByHql(sql.toString(), values);
				    //保存完周期配置 开始更新计划资源
				 /*   String hql = "update InspectPlanRes s set s.inspectCycle='"+t.getDictCode()+"'";
				    hql+=" where s.resLevel='"+sel[i]+"'";
				    inspectPlanResMgr.updateInspectPlanResByHql(hql);*/
				}
			
			response.getWriter().write(MobileConstants.successStr);
		} catch (Exception e) {
			response.getWriter().write(MobileConstants.failureStr);
		}
	}
	/**
	 * 保存和修改巡检周期 --chenbing 2013-06-27
	 */
	public void deleteCycleWeekTime(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		PnrResToWeekTimeMgr pnrResConfigMgr = (PnrResToWeekTimeMgr) getBean("PnrResToWeekTimeMgr");
//		不明白什么意思
		String selcar = StaticMethod.null2String(request
				.getParameter("seldelcar"));
		// 获取到的周期
		String prov = StaticMethod.null2String(request.getParameter("prov"));
		
		try {
			String[] sel = selcar.split("\\|");
			for (int i = 0; i < sel.length; i++) {				
			
				pnrResConfigMgr.removeByPnrId(sel[i]);
			
			}
			
			response.getWriter().write(MobileConstants.successStr);
		} catch (Exception e) {
			response.getWriter().write(MobileConstants.failureStr);
		}
	}
	
	
	/**
	 * 字表中删除对象
	 */
	public void removeSubTable(String tableName,String tableid){
		
		if("pnr_res_station".equals(tableName)){
			PnrResConfigStationMgr pnrResConfigStationMgr = (PnrResConfigStationMgr)getBean("PnrResConfigStationMgr");
			pnrResConfigStationMgr.removeById(tableid);
		}
		
		//铁搭
		if("pnr_res_iron".equals(tableName)){
			PnrResIronMgr PnrResIronMgr = (PnrResIronMgr) getBean("PnrResIronMgr");		
			PnrResIronMgr.removeById(tableid);
		}
		//集客家客
		if("pnr_res_jieke".equals(tableName)){
			PnrResJiekeMgr PnrResJiekeMgr = (PnrResJiekeMgr)getBean("PnrResJiekeMgr");
			PnrResJiekeMgr.removeById(tableid);
		}
		
		//传输线路
		if("pnr_res_line".equals(tableName)){
			PnrResLineMgr PnrResLineMgr = (PnrResLineMgr)getBean("PnrResLineMgr");
			PnrResLineMgr.removeById(tableid);
		}
		
		//直放站
		if("pnr_res_repeaters".equals(tableName)){
			PnrResRepeatersMgr pnrResRepeatersMgr = (PnrResRepeatersMgr) getBean("PnrResRepeatersMgr");
			pnrResRepeatersMgr.removeById(tableid);
		}
	}
	
	/**
	 * 取session中选项
	 */
	
	public ActionForward getRes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
	
		
		String resIds = (String) request.getSession().getAttribute("resIds");
		String resNames = (String) request.getSession().getAttribute("resNames");
		
		request.getSession().removeAttribute("resIds");
		request.getSession().removeAttribute("resNames");
    	MobileCommonUtils.responseWrite(response, "[{\"resValue\":\""+resIds+"\"},{\"resName\":\""+resNames+"\"}]","UTF-8");	    
		return null;
		
	}
	/**
	 * 将选项存入session中
	 */
	
	public ActionForward saveRes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		String resValue = StaticMethod.nullObject2String(request.getParameter("resValue"));
		String resName = StaticMethod.nullObject2String(request.getParameter("resName"));
		String flag = StaticMethod.nullObject2String(request.getParameter("flag"));
		
		String resIds = (String) request.getSession().getAttribute("resIds");
		String resNames = (String) request.getSession().getAttribute("resNames");
		String newNames=resNames;
		String newResIds =resIds;
		if(flag.equals("add")){
			if(resIds ==null || "".equals(resIds)){
				newResIds=resValue;
				newNames = resName;
			}else{
				//先清除
				newResIds+=","+resValue;
				newNames+=","+resName;
				request.getSession().removeAttribute("resIds");
				request.getSession().removeAttribute("resNames");
				
			}
						
		}else if(flag.equals("minus")){
					
			String[] resIdsString = resIds.split(",");
			int length = resIdsString.length;
			if(length==1){
				
				newResIds=resIds.replaceAll(resValue,"");
				newNames = resNames.replaceAll(resName,"");
			}else{
				
				   if(resIdsString[0].equals(resValue)){
					   
					   newResIds=resIds.replaceAll(resValue+",","");
					   newNames =resNames.replaceAll(resName+",","");
				   }else{
					   
					   //中间和尾巴
					   newResIds=	resIds.replaceAll(","+resValue,"");
					   newNames =resNames.replaceAll(","+resName,"");
				   }
				
			}
				
			
			request.getSession().removeAttribute("resIds");
			request.getSession().removeAttribute("resNames");
		}
		
		
		request.getSession().setAttribute("resIds", newResIds);
		request.getSession().setAttribute("resNames", newNames);
	//	MobileCommonUtils.responseWrite(response, "[{\"resValue\":\""+resIds+"\"},{\"resName\":\""+resNames+"\"}]","UTF-8");	    
		return null;
		
	}
	/**
	 * 资源查询(通用任务工单选择资源专用)
	 */
	public ActionForward searchResBySheet(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {		
//		多选站点--start
//		多选跟单选不是一个页面
		String sel =StaticMethod.nullObject2String(request.getParameter("sel"));
		String findForward = "resConfigSingleChooseList";
		
		if(sel.equals("multiple")){
			
			findForward="resConfigMultipleChooseList";
		/*	request.getSession().removeAttribute("resIds");
			request.getSession().removeAttribute("resNames");*/
		}
		
//		多选站点--end
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		String whereStr = " where 1=1 ";
    	List city = PartnerCityByUser.getCityByProvince(province);    	
    	request.setAttribute("city", city);
    	request.setAttribute("city1", city);
    	PnrResConfigMgr pnrResConfiMgr = (PnrResConfigMgr) getBean("PnrResConfigMgr");
    	String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		PnrResConfigForm pnrResConfigForm = (PnrResConfigForm) form;
		if(!StringUtils.isEmpty(pnrResConfigForm.getResName())){
			whereStr = whereStr+" and resName like '%"+pnrResConfigForm.getResName()+"%'";
		}
		if(!StringUtils.isEmpty(pnrResConfigForm.getSpecialty())){
			whereStr = whereStr+" and specialty='"+pnrResConfigForm.getSpecialty()+"'";
		}
		if(!StringUtils.isEmpty(pnrResConfigForm.getResType())){
			whereStr = whereStr+" and resType='"+pnrResConfigForm.getResType()+"'";
		}
		if(!StringUtils.isEmpty(pnrResConfigForm.getResLevel())){
			whereStr = whereStr+" and resLevel='"+pnrResConfigForm.getResLevel()+"'";
		}
		if(!StringUtils.isEmpty(pnrResConfigForm.getCity())){
			whereStr = whereStr+" and country='"+pnrResConfigForm.getCity()+"'";
			pnrResConfigForm.setCountry(pnrResConfigForm.getCity());
		}
		if(!StringUtils.isEmpty(request.getParameter("region"))){
			whereStr = whereStr+" and city='"+request.getParameter("region")+"'";
			pnrResConfigForm.setCity(request.getParameter("region"));
		}
		//部门小组id
		if(!StringUtils.isEmpty(request.getParameter("executeDept"))){
			whereStr = whereStr+" and executeDept like '"+request.getParameter("executeDept")+"%'";
		}
		
    	Map map = pnrResConfiMgr.getResources(firstResult*CommonConstants.PAGE_SIZE, CommonConstants.PAGE_SIZE, whereStr);
    	request.setAttribute("list",map.get("result"));
    	request.setAttribute("pnrResConfigForm", pnrResConfigForm);
		request.setAttribute("pageSize", CommonConstants.PAGE_SIZE);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("multiple", "multiple");
		return mapping.findForward(findForward);
	}
	/**
	 * 资源查询(油机专用，增加获取经纬度)
	 */
	public ActionForward searchResBySheetOil(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		String whereStr = " where 1=1 ";
    	List city = PartnerCityByUser.getCityByProvince(province);    	
    	request.setAttribute("city", city);
    	request.setAttribute("city1", city);
    	PnrResConfigMgr pnrResConfiMgr = (PnrResConfigMgr) getBean("PnrResConfigMgr");
    	String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		PnrResConfigForm pnrResConfigForm = (PnrResConfigForm) form;
		if(!StringUtils.isEmpty(pnrResConfigForm.getResName())){
			whereStr = whereStr+" and resName like '%"+pnrResConfigForm.getResName()+"%'";
		}
		if(!StringUtils.isEmpty(pnrResConfigForm.getSpecialty())){
			whereStr = whereStr+" and specialty='"+pnrResConfigForm.getSpecialty()+"'";
		}
		if(!StringUtils.isEmpty(pnrResConfigForm.getResType())){
			whereStr = whereStr+" and resType='"+pnrResConfigForm.getResType()+"'";
		}
		if(!StringUtils.isEmpty(pnrResConfigForm.getResLevel())){
			whereStr = whereStr+" and resLevel='"+pnrResConfigForm.getResLevel()+"'";
		}
		if(!StringUtils.isEmpty(pnrResConfigForm.getCity())){
			whereStr = whereStr+" and country='"+pnrResConfigForm.getCity()+"'";
			pnrResConfigForm.setCountry(pnrResConfigForm.getCity());
		}
		if(!StringUtils.isEmpty(request.getParameter("region"))){
			whereStr = whereStr+" and city='"+request.getParameter("region")+"'";
			pnrResConfigForm.setCity(request.getParameter("region"));
		}
		
    	Map map = pnrResConfiMgr.getResources(firstResult*CommonConstants.PAGE_SIZE, CommonConstants.PAGE_SIZE, whereStr);
    	request.setAttribute("list",map.get("result"));
    	request.setAttribute("pnrResConfigForm", pnrResConfigForm);
		request.setAttribute("pageSize", CommonConstants.PAGE_SIZE);
		request.setAttribute("resultSize", map.get("total"));
		
		return mapping.findForward("resConfigSingleChooseListOil");
	}	
	public ActionForward getChargePerson(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONArray json = new JSONArray();
		JSONObject jitem = new JSONObject();
        //获取资源 ID
		String resId = request.getParameter("resId");
    	PnrResConfigMgr pnrResConfiMgr = (PnrResConfigMgr) getBean("PnrResConfigMgr");
    	TawSystemUserDao tawSystemUserDao = (TawSystemUserDao) getBean("tawSystemUserDao");
    	PnrResConfig pnr = pnrResConfiMgr.find(resId);
    	String chargePerson="";
    	String personName="";
    	if(pnr !=null){
    		chargePerson = pnr.getChargePerson();
    		if(chargePerson.length()>0){
    			personName=tawSystemUserDao.id2Name(chargePerson);
    		}
    	}
		jitem.put("chargePerson", chargePerson);
		jitem.put("personName", personName);
		json.put(jitem);
		
		response.setCharacterEncoding("utf-8");		
		
		response.getWriter().write(json.toString());		
		
		return null;
	}
	
	/**
	 * 	 
	 	 * @author WangJun
	 	 * @title: openCollectResourceView
	 	 * @date Nov 24, 2015 10:43:09 AM
	 	 * @param mapping
	 	 * @param form
	 	 * @param request
	 	 * @param response
	 	 * @return
	 	 * @throws Exception ActionForward
	 */
	public ActionForward openCollectResourceView(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int pageSize = 20;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
//		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
//				.valueOf(pageIndexString).intValue();
		
		IPnrResourceSuccessLogService pnrResourceSuccessLogService = (IPnrResourceSuccessLogService) getBean("pnrResourceSuccessLogService");
		

		Search search = new Search();
		search.setMaxResults(pageSize);

		search.setFirstResult(firstResult * pageSize);
		search.addSortDesc("insertTime");
		SearchResult<PnrResourceSuccessLog> searchResult =pnrResourceSuccessLogService.searchAndCount(search);
		request.setAttribute("taskList", searchResult.getResult());
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", searchResult.getTotalCount());		
		return mapping.findForward("collectResourceView");
	}
	
	
	/**
	 *   从资源库同步机房数据到系统中
	 	 * @author WangJun
	 	 * @title: collectResourceInventoryData
	 	 * @date Nov 24, 2015 10:28:46 AM
	 	 * @param mapping
	 	 * @param form
	 	 * @param request
	 	 * @param response
	 	 * @return
	 	 * @throws Exception ActionForward
	 */
	public ActionForward collectResourceInventoryData(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String userId = sessionForm.getUserid();
		String limitedNumber = StaticMethod.null2String(request.getParameter("limitedNumber"));
		String cityIds = StaticMethod.null2String(request.getParameter("cityIds"));
		System.out.println("-------cityIds="+cityIds);
		String cityNames = java.net.URLDecoder.decode(StaticMethod.null2String(request.getParameter("cityNames")), "UTF-8");  
		
		System.out.println("-------cityNames="+cityNames);
		if("".equals(limitedNumber)){
			limitedNumber="10000";//如果没有值，默认采集10000条
		}
		PnrResConfigMgr pnrResConfiMgr = (PnrResConfigMgr) getBean("PnrResConfigMgr");
		Map<String,String> param =new HashMap<String,String>();
		param.put("userId", userId);
		param.put("limitedNumber", limitedNumber);
		param.put("cityIds", cityIds);
		param.put("cityNames", cityNames);
		
		//查询总条数，和限制条数进行比较
		Map<String, Object> totalNumberMap = pnrResConfiMgr.totalNumber(param);
		int totalNumber = (Integer)totalNumberMap.get("total");//资源总条数
		param.put("zyglTotalNumber",Integer.toString(totalNumber));
		JSONArray json = new JSONArray();
		JSONObject jitem = new JSONObject();
		int templimitedNumber=Integer.parseInt(limitedNumber);
		if(totalNumber > templimitedNumber){//如果资源总数大于限制条数
			jitem.put("resultMsg", "notCollecting");
			jitem.put("totalNumber", totalNumber);
		}else{
			Map<String, Object> resultMap = pnrResConfiMgr.collectResourceInventoryData(param);
			int total = (Integer)resultMap.get("total");
			jitem.put("resultMsg", "collecting");
			jitem.put("total", total);
		}
		json.put(jitem);
		
		//Thread.sleep(2000);// 睡眠100毫秒 
		
		response.setCharacterEncoding("utf-8");		
		
		response.getWriter().write(json.toString());
		
		return null;
	}
	
}
