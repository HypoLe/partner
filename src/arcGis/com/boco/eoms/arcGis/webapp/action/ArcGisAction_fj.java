package com.boco.eoms.arcGis.webapp.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.joda.time.JodaTimePermission;

import com.boco.eoms.arcGis.util.ArcGisConstacts;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.dict.exceptions.DictDAOException;
import com.boco.eoms.commons.system.dict.exceptions.DictServiceException;
import com.boco.eoms.commons.system.dict.service.ID2NameService;
import com.boco.eoms.commons.system.dict.service.IDictService;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.commons.util.xml.XmlManage;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserAndAreaMgr;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.baseinfo.model.PartnerUser;
import com.boco.eoms.partner.baseinfo.model.PartnerUserAndArea;
import com.boco.eoms.partner.baseinfo.util.PartnerCityByUser;
import com.boco.eoms.partner.baseinfo.util.PartnerUserConstants;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;
import com.boco.eoms.partner.baseinfo.webapp.form.PartnerUserForm;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanApproveMgr;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanMainMgr;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanResMgr;
import com.boco.eoms.partner.inspect.mgr.IPnrInspectTrackMgr;
import com.boco.eoms.partner.inspect.model.InspectPlanApprove;
import com.boco.eoms.partner.inspect.model.InspectPlanMain;
import com.boco.eoms.partner.inspect.model.InspectPlanRes;
import com.boco.eoms.partner.inspect.model.PnrInspectTrack;
import com.boco.eoms.partner.inspect.util.InspectConstants;
import com.boco.eoms.partner.res.mgr.PnrResConfigMgr;
import com.boco.eoms.partner.res.mgr.PnrResConfigStationMgr;
import com.boco.eoms.partner.res.mgr.PnrResIronMgr;
import com.boco.eoms.partner.res.mgr.PnrResJiekeMgr;
import com.boco.eoms.partner.res.mgr.PnrResLineMgr;
import com.boco.eoms.partner.res.mgr.PnrResRepeatersMgr;
import com.boco.eoms.partner.res.model.PnrResConfig;
import com.boco.eoms.partner.res.model.PnrResConfigStation;
import com.boco.eoms.partner.res.model.PnrResIron;
import com.boco.eoms.partner.res.model.PnrResJieke;
import com.boco.eoms.partner.res.model.PnrResLine;
import com.boco.eoms.partner.res.model.PnrResRepeaters;
import com.boco.eoms.partner.res.webapp.form.PnrResConfigForm;
import com.boco.eoms.partner.resourceInfo.model.Car;
import com.boco.eoms.partner.resourceInfo.service.CarService;
import com.boco.eoms.partner.serviceArea.mgr.ISiteMgr;
import com.boco.eoms.partner.serviceArea.model.Grid;
import com.boco.eoms.partner.serviceArea.model.Site;
import com.boco.eoms.partner.serviceArea.util.PointConstants;
import com.boco.eoms.partner.serviceArea.util.SiteConstants;
import com.boco.eoms.commons.system.dict.util.Util;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcServiceImpl;
import com.boco.eoms.deviceManagement.common.utils.CommonConstants;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;
/**
 * 
* @ClassName: ArcGisAction
* @Description: TODO
* @author huhao thinking: 在构造json时
	 *         在数据量为100的情况下gson要比jackson直接生成string的方式要快，
	 *         但比jackson流方式差上8倍左右。随着数据量的增大在1000左右gson已经开始比jackson的string方式慢
	 *         ，比流方式相差在8-12倍之间。
	 *         所以显而易见jackson的流输出方式效率非常高，远远比直接输出string和gson的转换要快的多。
	 *         现在模块只显示15个标点，所以沿用Gson,当数据量进一步增大时，建议使用jackson
* @date 2012-11-23 上午11:23:04
 */

/**
 * 	在使用此action时请将public class ArcGisAction_fj extends BaseAction {
 * 改为public class ArcGisAction extends BaseAction {
 * thank you
 */
public class ArcGisAction_fj extends BaseAction {
	private static final String List = null;

/**
 * 跳转到ArcGis地图页面	
* @Title: goToArcGis
* @author huhao
* @Description: TODO
* @param @param mapping
* @param @param form
* @param @param request
* @param @param response
* @param @return    
* @return ActionForward    
* @throws
 */
	public ActionForward goToArcGis(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("goToArcGis");
	}
	
	
	/**
	 * 跳转到地图
	* @Title: goToArcGisMap
	* @author huhao
	* @Description: TODO
	* @param @param mapping
	* @param @param form
	* @param @param request
	* @param @param response
	* @param @return    
	* @return ActionForward    
	* @throws
	 */
	public ActionForward goToArcGisMap(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		return mapping.findForward("goToArcGisMap");
	}

	/**
	 * 基站查询
	* @Title: search
	* @author huhao
	* @Description: TODO
	* @param @param mapping
	* @param @param form
	* @param @param request
	* @param @param response
	* @param @return
	* @param @throws Exception    
	* @return ActionForward    
	* @throws
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
//		String type = StaticMethod.nullObject2String(request.getParameter("type"));
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList) getBean("pnrBaseAreaIdList");
		ID2NameService service = (ID2NameService) ApplicationContextHolder
		.getInstance().getBean("ID2NameGetServiceCatch");
		String province = pnrBaseAreaIdList.getRootAreaId();
		String whereStr = " where 1=1 ";
		List city = PartnerCityByUser.getCityByProvince(province);
		request.setAttribute("city", city);
		request.setAttribute("city1", city);
		PnrResConfigMgr pnrResConfiMgr = (PnrResConfigMgr) getBean("PnrResConfigMgr");
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "list");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		PnrResConfigForm pnrResConfigForm = new PnrResConfigForm();
		BeanUtils.populate(pnrResConfigForm, request.getParameterMap());
		if (!StringUtils.isEmpty(pnrResConfigForm.getResName())) {
			whereStr = whereStr + " and resName like '%"
					+ pnrResConfigForm.getResName() + "%'";
		}
//		String attr="";
//		if(!"".equals(type)&&"site".equals(type)){
//		whereStr = whereStr + " and specialty='1122501'";
//		attr="1122501";
//		}
//		if(!"".equals(type)&&"custom".equals(type)){
//			whereStr = whereStr + " and specialty='1122505'";
//			attr="1122505";
//			}
		
		 if(!StringUtils.isEmpty(pnrResConfigForm.getSpecialty())){
		 whereStr =
		 whereStr+" and specialty='"+pnrResConfigForm.getSpecialty()+"'";
		 }
		if (!StringUtils.isEmpty(pnrResConfigForm.getResType())) {
			whereStr = whereStr + " and resType='"
					+ pnrResConfigForm.getResType() + "'";
		}
		if (!StringUtils.isEmpty(pnrResConfigForm.getResLevel())) {
			whereStr = whereStr + " and resLevel='"
					+ pnrResConfigForm.getResLevel() + "'";
		}
		if (!StringUtils.isEmpty(pnrResConfigForm.getCity())) {
			whereStr = whereStr + " and country='" + pnrResConfigForm.getCity()
					+ "'";
			pnrResConfigForm.setCountry(pnrResConfigForm.getCity());
		}
		if (!StringUtils.isEmpty(request.getParameter("region"))) {
			whereStr = whereStr + " and city='"
					+ request.getParameter("region") + "'";
			pnrResConfigForm.setCity(request.getParameter("region"));
		}
		// 查询经纬度不为空的数据
		whereStr = whereStr + " and resLongitude is not null ";
		whereStr = whereStr + " and resLatitude is not null ";

		Map map = pnrResConfiMgr.getResources(firstResult
				* CommonConstants.PAGE_SIZE, CommonConstants.PAGE_SIZE,
				whereStr);
		List list = new ArrayList();
		list = (java.util.List) map.get("result");
		request.setAttribute("list", list);
		//对当前页面进行json封装
		List jsonList=new ArrayList();
		for(int j=0;j<list.size();j++){
			PnrResConfig pr=new PnrResConfig();
			Map jsonMap=new HashMap();
			pr=(PnrResConfig) list.get(j);
			String resUrl="/partner/res/PnrResConfig.do?method=detial&&seldelcar="+pr.getId();
			jsonMap.put("resUrl", resUrl);
			jsonMap.put("resName", pr.getResName());
			jsonMap.put("specialty", service.id2Name(pr.getSpecialty(), "tawSystemDictTypeDao"));
			jsonMap.put("resType", service.id2Name(pr.getResType(), "tawSystemDictTypeDao"));
			System.out.println("resType==="+service.id2Name(pr.getResType(), "tawSystemDictTypeDao"));
			jsonMap.put("city", service.id2Name(pr.getCity(), "tawSystemAreaDao"));
			jsonMap.put("country", service.id2Name(pr.getCountry(), "tawSystemAreaDao"));
			String street=StaticMethod.nullObject2String(pr.getStreet());
			jsonMap.put("street", street);
			String companyName=StaticMethod.nullObject2String(pr.getCompanyName());
			jsonMap.put("companyName",companyName);
			String contactName=StaticMethod.nullObject2String(pr.getContactName());
			jsonMap.put("contactName", contactName);
			String phone=StaticMethod.nullObject2String(pr.getPhone());
			if (!"".equals(phone)) {
				phone = ArcGisConstacts.replaceAll(phone, "\n",
						"<br/>");
			}else{
				phone="";
			}
			jsonMap.put("phone", phone);
			jsonMap.put("executeObject", service.id2Name(pr.getCountry(), "partnerDeptDao"));
			jsonMap.put("resLongitude",pr.getResLongitude());
			jsonMap.put("resLatitude",pr.getResLatitude());
			jsonMap.put("endLongitude",pr.getEndLongitude());
			jsonMap.put("endLatitude(",pr.getEndLatitude());
			//去除脏数据
			if("1122502".equals(pr.getSpecialty())&&!"0".equals(pr.getResLatitude())&&!"0".equals(pr.getResLongitude())&&!"0".equals(pr.getEndLongitude())&&!"0".equals(pr.getEndLatitude())){
				jsonList.add(jsonMap);
			}
			else if(!"1122502".equals(pr.getSpecialty())&&!"0".equals(pr.getResLatitude())&&!"0".equals(pr.getResLongitude())){
			jsonList.add(jsonMap);
			}
		}
		Gson gson=new Gson();
		String jsonString=gson.toJson(jsonList);
		System.out.println("jsonString==="+jsonString);
		request.setAttribute("jsonString", jsonString);
		request.setAttribute("pnrResConfigForm", pnrResConfigForm);
		request.setAttribute("pageSize", CommonConstants.PAGE_SIZE);
		request.setAttribute("resultSize", map.get("total"));
//		request.setAttribute("attr",attr);

		return mapping.findForward("list");
	}

	/**
	 * 地图渲染所选基站
	* @Title: siteSelected
	* @author huhao
	* @Description: TODO
	* @param @param mapping
	* @param @param form
	* @param @param request
	* @param @param response
	* @param @return    
	* @return ActionForward    
	* @throws
	 */
	public ActionForward siteSelected(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
	
		CommonSpringJdbcServiceImpl jdbcService = (CommonSpringJdbcServiceImpl) ApplicationContextHolder
				.getInstance().getBean("commonSpringJdbcService");
		ID2NameService service = (ID2NameService) ApplicationContextHolder
		.getInstance().getBean("ID2NameGetServiceCatch");
		String id = StaticMethod.nullObject2String(request.getParameter("id"));
		String type = StaticMethod.nullObject2String(request.getParameter("type"));
		String where="";
		if(!"".equals(type)){
			 where="res.specialty='"+type+"' and ";
		}
		if (!"".equals(id)) {
			String sql = "select res.id,res.res_name,dict.dictname as specialty,dict1.dictname as restype,res.EXECUTE_OBJECT,res.res_longitude,res.res_latitude,res.end_longitude,res.end_latitude,areacity.areaname as city,areacountry.areaname as country,res.street,res.company_name,res.contact_name,res.phone "
			+" from  pnr_res_config res,taw_system_dicttype dict,taw_system_area  areacity,taw_system_area  areacountry,taw_system_dicttype dict1 "
			+" where "
			+where
			+" res.specialty=dict.dictid and res.city=areacity.areaid and res.country=areacountry.areaid  and dict1.dictid=res.res_type "
			+" and res.res_longitude is not null and res.res_latitude is not null and res.id='" + id + "'";
			List<ListOrderedMap> list = jdbcService.queryForList(sql);
			try {
			response.setCharacterEncoding("utf-8");
			List<Map<String, String>> jsonList = new ArrayList<Map<String, String>>();
			Gson gson = new Gson();
			HashMap<String, String> jsonMap;
			StringBuffer grid_buffer = new StringBuffer();
			for (int i = 0; i < list.size(); i++) {
				jsonMap = new HashMap<String, String>();
				ListOrderedMap map = list.get(i);
				String resUrl="/partner/res/PnrResConfig.do?method=detial&&seldelcar="+map.get("id");
				String resName=StaticMethod.nullObject2String(map.get("res_name"));
				String specialty=StaticMethod.nullObject2String(map.get("specialty"));
				String resType=StaticMethod.nullObject2String(map.get("restype"));
				String resLongitude=StaticMethod.nullObject2String(map.get("res_longitude"));
				String resLatitude=StaticMethod.nullObject2String(map.get("res_latitude"));
				String endLongitude=StaticMethod.nullObject2String(map.get("end_longitude"));
				String endLatitude=StaticMethod.nullObject2String(map.get("end_latitude"));
				String city=StaticMethod.nullObject2String(map.get("city"));
				String country=StaticMethod.nullObject2String(map.get("country"));
				String street=StaticMethod.nullObject2String(map.get("street"));
				String companyName=StaticMethod.nullObject2String(map.get("company_name"));
				String contactName=StaticMethod.nullObject2String(map.get("contact_name"));
				String phone =StaticMethod.nullObject2String(map.get("phone"));
				String executeObject =StaticMethod.nullObject2String(map.get("EXECUTE_OBJECT"));
				if("".equals(executeObject)){
					executeObject="无";
				}
				else{
					String deptSql="select name from pnr_dept where id='"+executeObject+"'";
					List<ListOrderedMap> deptList = jdbcService.queryForList(deptSql);
						if(!deptList.isEmpty()){
							ListOrderedMap deptMap =deptList.get(0);
							 String deptName=StaticMethod.nullObject2String(deptMap.get("name"));
							if(!"".equals(deptName)){
								executeObject=deptName;
							}
							else{
								executeObject="无";
							}
						}
				}
				if (!"".equals(phone)) {
					phone = ArcGisConstacts.replaceAll(phone, "\n",
							"<br/>");
				}else{
					phone="";
				}
				jsonMap.put("resUrl", resUrl);
				jsonMap.put("resName", resName);
				jsonMap.put("specialty", specialty);
				jsonMap.put("resType", resType);
				jsonMap.put("city", city);
				jsonMap.put("country", country);
				jsonMap.put("street", street);
				jsonMap.put("companyName", companyName);
				jsonMap.put("contactName", contactName);
				jsonMap.put("phone", phone);
				jsonMap.put("resLongitude", resLongitude);
				jsonMap.put("resLatitude", resLatitude);
				jsonMap.put("endResLongitude", endLongitude);
				jsonMap.put("endResLatitude", endLatitude);
				//如果为脏数据则不装入jsonList,并给提示。
				if("传输线路".equals(specialty)){
					if(!"0".equals(resLongitude)&&!"0".equals(resLatitude)&&!"0".equals(endLongitude)&&!"0".equals(endLatitude)){
						jsonMap.put("type", "line");
						jsonList.add(jsonMap);
						}
					else{
						Map errorMap=new HashMap();
						errorMap.put("error", "定位坐标不正确！");
						jsonList.add(errorMap);
					}
					
					jsonMap.put("executeObject", executeObject);
					
				}
				else {
					
					if(!"0".equals(resLongitude)&&!"0".equals(resLatitude)){
						jsonMap.put("executeObject", executeObject);
						jsonList.add(jsonMap);
						}
					else{
						Map errorMap=new HashMap();
						errorMap.put("error", "定位坐标不正确！");
						jsonList.add(errorMap);
					}
					
				}
			}
			grid_buffer.append(gson.toJson(jsonList));
				response.getWriter().write(grid_buffer.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 巡检资源详情
	* @Title: detial
	* @author huhao
	* @Description: TODO
	* @param @param mapping
	* @param @param form
	* @param @param request
	* @param @param response
	* @param @return    
	* @return ActionForward    
	* @throws
	 */
	public ActionForward detial(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		PnrResConfigMgr pnrResConfiMgr = (PnrResConfigMgr) getBean("PnrResConfigMgr");
		ID2NameService service = (ID2NameService) ApplicationContextHolder
		.getInstance().getBean("ID2NameGetServiceCatch");
		CommonSpringJdbcServiceImpl jdbcService = (CommonSpringJdbcServiceImpl) ApplicationContextHolder
		.getInstance().getBean("commonSpringJdbcService");
		String id = StaticMethod.null2String(request.getParameter("seldelcar"));
		PnrResConfig pnrResConfig = pnrResConfiMgr.find(id);
//		IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
//		String whereStr=" where  res_cfg_id='"+id+"'";
//		Integer pageSize=15;
//		int pageIndex=0;
//		Map map = (Map) inspectPlanResMgr.findPlanResAssignList(pageIndex, pageSize, whereStr);
//		List list = (List) map.get("result");
		String subResTable =  pnrResConfig.getSubResTable();
		try {
		response.setCharacterEncoding("utf-8");
		Gson gson = new Gson();
		HashMap<String, String> jsonMap;
		List<Map<String, String>> jsonList = new ArrayList<Map<String, String>>();
		StringBuffer pnrResConfigStation_buffer = new StringBuffer();
		jsonMap = new HashMap<String, String>();
		String endResLongitude="";
		String endResLatitude="";
		//传输线路
		if("pnr_res_line".equals(subResTable)){
			PnrResLineMgr PnrResLineMgr = (PnrResLineMgr)getBean("PnrResLineMgr");
			if(!StringUtils.isEmpty(pnrResConfig.getSubResId())){
				 endResLongitude=StaticMethod.nullObject2String(pnrResConfig.getEndLongitude());
				 endResLatitude=StaticMethod.nullObject2String(pnrResConfig.getEndLatitude());
				jsonMap.put("endResLongitude",endResLongitude);
				jsonMap.put("endResLatitude",endResLatitude);
				jsonMap.put("type","line");
			}
		}
		String resName=StaticMethod.nullObject2String(pnrResConfig.getResName());
		String specialty=StaticMethod.nullObject2String(pnrResConfig.getSpecialty());
			   specialty= service.id2Name(specialty, "tawSystemDictTypeDao");
	    String resType=StaticMethod.nullObject2String(pnrResConfig.getResType());
	    	   resType= service.id2Name(resType, "tawSystemDictTypeDao");
		String resLongitude=StaticMethod.nullObject2String(pnrResConfig.getResLongitude());
		String resLatitude=StaticMethod.nullObject2String(pnrResConfig.getResLatitude());
		String city=StaticMethod.nullObject2String(pnrResConfig.getCity());
		 	   city=service.id2Name(city, "tawSystemAreaDao");
		String country=StaticMethod.nullObject2String(pnrResConfig.getCountry());
		 	   country=service.id2Name(country, "tawSystemAreaDao");
		String street=StaticMethod.nullObject2String(pnrResConfig.getStreet());
		String companyName=StaticMethod.nullObject2String(pnrResConfig.getCompanyName());
		String contactName=StaticMethod.nullObject2String(pnrResConfig.getContactName());
		String executeObject=StaticMethod.nullObject2String(pnrResConfig.getExecuteObject());
		if("".equals(executeObject)){
			executeObject="无";
		}
		else{
			String deptSql="select name from pnr_dept where id='"+executeObject+"'";
			List<ListOrderedMap> deptList = jdbcService.queryForList(deptSql);
				if(!deptList.isEmpty()){
					ListOrderedMap deptMap =deptList.get(0);
					 String deptName=StaticMethod.nullObject2String(deptMap.get("name"));
					if(!"".equals(deptName)){
						executeObject=deptName;
					}
					else{
						executeObject="无";
					}
				}
		}
		String phone =StaticMethod.nullObject2String(pnrResConfig.getPhone());
		
		if (!"".equals(phone)) {
			phone = ArcGisConstacts.replaceAll(phone, "\n",
					"<br/>");
		}else{
			phone="";
		}
		String resUrl="/partner/res/PnrResConfig.do?method=detial&&seldelcar="+pnrResConfig.getId();
		jsonMap.put("resUrl", resUrl);
		jsonMap.put("resName", resName);
		jsonMap.put("resLongitude",resLongitude);
		jsonMap.put("resLatitude",resLatitude);
		jsonMap.put("specialty",specialty);
		jsonMap.put("resType",resType);
		jsonMap.put("city",city);
		jsonMap.put("country", country);
		jsonMap.put("executeObject", executeObject);
		jsonMap.put("street",street);
		jsonMap.put("companyName",companyName);
		jsonMap.put("contactName",contactName);
		jsonMap.put("phone",phone);
		if(!"".equals(resLongitude)&&!"".equals(resLatitude)){
		jsonList.add(jsonMap);
		}
		else{
		Map errorMap=new HashMap();
		errorMap.put("error", "定位坐标不正确！");
		jsonList.add(errorMap);
		}
		pnrResConfigStation_buffer.append(gson.toJson(jsonList));
		
			response.getWriter().write(pnrResConfigStation_buffer.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
/**
 * 巡检轨迹
* @Title: gisTrack
* @author huhao
* @Description: TODO
* @param @param mapping
* @param @param form
* @param @param request
* @param @param response
* @param @return
* @param @throws IOException    
* @return ActionForward    
* @throws
 */
	public ActionForward gisTrack(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException{
		IPnrInspectTrackMgr inspectTrackMgr= (IPnrInspectTrackMgr) this.getBean("pnrInspectTrackMgrImpl");
		CommonSpringJdbcServiceImpl jdbcService = (CommonSpringJdbcServiceImpl) ApplicationContextHolder
		.getInstance().getBean("commonSpringJdbcService");
//		CommonSpringJdbcServiceImpl jdbcService = (CommonSpringJdbcServiceImpl) ApplicationContextHolder
//		.getInstance().getBean("commonSpringJdbcService");
		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(), search);
		String resCfgId = StaticMethod.null2String(request.getParameter("resCfgId"));
//		String sql="select config.* from pnr_inspect_plan_res res,PNR_RES_CONFIG config where res.res_cfg_id=config.id and res.id='"+resCfgId+"'";
//		List<ListOrderedMap> resList = jdbcService.queryForList(sql);
		search.addFilterEqual("planResId", resCfgId);
		search.addSort("signTime",true );
		SearchResult<PnrInspectTrack> searchResult = inspectTrackMgr.searchAndCount(search);
		List<PnrInspectTrack> list = searchResult.getResult();
		String sql1=" select "
					+" config.id as siteid , "
					+" config.res_name as siteres_name, "
					+" dict.dictname as sitespecialty, "
					+" config.res_longitude as siteres_longitude, "
					+" config.res_latitude as siteres_latitude, "
					+"config.end_longitude as siteend_longitude, "
					+"config.end_latitude as siteend_latitude, "	
					+" areacity.areaname as sitecity, "
					+" areacountry.areaname as sitecountry, "
					+" config.street as sitestreet, "
					+" config.company_name as sitecompany_name, "
					+" config.contact_name as contact_name, "
					+" config.phone as sitephone, "
					+" res.plan_start_time as siteplan_start_time, "
					+" res.plan_end_time as siteplan_end_time, "
					+" res.inspect_state as siteinspect_state, "
					+" dept.name as deptname"
					+" from pnr_inspect_plan_res res, "
					+" pnr_res_config config, "
					+" taw_system_dicttype dict, "
					+" taw_system_area  areacity, "
					+" taw_system_area  areacountry, "
					+" pnr_dept dept "
					+" where res.id='"+resCfgId+"' and res.res_cfg_id=config.id "
					+" and config.specialty=dict.dictid and config.city=areacity.areaid and config.country=areacountry.areaid and dept.id=res.execute_object ";
			List<ListOrderedMap> list1 = jdbcService.queryForList(sql1);
		try {
			response.setCharacterEncoding("utf-8");
			Gson gson = new Gson();
			HashMap<String, String> jsonMap;
			List<List<Map<String, String>>> allList = new ArrayList<List<Map<String, String>>>();
			List<Map<String, String>> jsonList = new ArrayList<Map<String, String>>();
			StringBuffer gisTrack_buffer = new StringBuffer();
				if(!list.isEmpty()){
					for(int i=0;i<list.size();i++){
						jsonMap = new HashMap<String, String>();
						PnrInspectTrack pit=new PnrInspectTrack();
						pit=list.get(i);
						String longitude=StaticMethod.null2String(pit.getSignLongitude());
						jsonMap.put("longitude",longitude );
						String latitude=StaticMethod.null2String(pit.getSignLatitude());
						jsonMap.put("latitude", latitude);
						jsonMap.put("signTime", StaticMethod.null2String(pit.getSignTime()));
						String signState=pit.getSignState();
						String status=pit.getStatus();
						if("1".equals(status)&&"0".equals(signState)){
							jsonMap.put("status","强制到站");
						}
						else if("0".equals(status)&&"0".equals(signState)){
							jsonMap.put("status","强制离站");
						}
						else if("1".equals(status)&&"1".equals(signState)){
							jsonMap.put("status","正常到站");
						}
						else if("0".equals(status)&&"1".equals(signState)){
							jsonMap.put("status","正常离站");
						}
						else if("2".equals(status)){
							jsonMap.put("status","巡检中");
						}
						else{
							jsonMap.put("status","无法获取到站信息");
						}
						if(!"".equals(longitude)&&!"".equals(latitude)){
						jsonList.add(jsonMap);
						}
					}
				}
				else{
					Map errorMap=new HashMap();
					errorMap.put("error", "无法获取巡检轨迹数据！");
					jsonList.add(errorMap);
				}
				HashMap<String, String> jsonMap1;
				List<Map<String, String>> jsonList1 = new ArrayList<Map<String, String>>();
				//装入资源数据
				if(!list1.isEmpty()&&list1.size()==1){
					jsonMap1 = new HashMap<String, String>();
					ListOrderedMap map = list1.get(0);
					String resName=StaticMethod.nullObject2String(map.get("siteres_name"));
					String specialty=StaticMethod.nullObject2String(map.get("sitespecialty"));
					String resLongitude=StaticMethod.nullObject2String(map.get("siteres_longitude"));
					String resLatitude=StaticMethod.nullObject2String(map.get("siteres_latitude"));
					String endLongitude=StaticMethod.nullObject2String(map.get("siteend_longitude"));
					String endLatitude=StaticMethod.nullObject2String(map.get("siteend_latitude"));
					String city=StaticMethod.nullObject2String(map.get("sitecity"));
					String country=StaticMethod.nullObject2String(map.get("sitecountry"));
					String street=StaticMethod.nullObject2String(map.get("sitestreet"));
					String companyName=StaticMethod.nullObject2String(map.get("sitecompany_name"));
					String contactName=StaticMethod.nullObject2String(map.get("sitecontact_name"));
					String phone =StaticMethod.nullObject2String(map.get("sitephone"));
					String planStartTime =StaticMethod.nullObject2String(map.get("siteplan_start_time"));
					String planEndTime =StaticMethod.nullObject2String(map.get("siteplan_end_time"));
					String inspectState =StaticMethod.nullObject2String(map.get("siteinspect_state"));
					String deptname =StaticMethod.nullObject2String(map.get("deptname"));
					if (!"".equals(phone)) {
						phone = ArcGisConstacts.replaceAll(phone, "\n",
								"<br/>");
					}else{
						phone="";
					}
					jsonMap1.put("resName", resName);
					jsonMap1.put("specialty", specialty);
					jsonMap1.put("city", city);
					jsonMap1.put("country", country);
					jsonMap1.put("street", street);
					jsonMap1.put("companyName", companyName);
					jsonMap1.put("contactName", contactName);
					jsonMap1.put("phone", phone);
					jsonMap1.put("resLongitude", resLongitude);
					jsonMap1.put("resLatitude", resLatitude);
					jsonMap1.put("endLongitude", endLongitude);
					jsonMap1.put("endLatitude", endLatitude);
					jsonMap1.put("planStartTime", planStartTime);
					jsonMap1.put("planEndTime", planEndTime);
					jsonMap1.put("deptname", deptname);
					if("0".equals(inspectState)){
						inspectState="已完成";
					}
					else if("0".equals(inspectState)){
						inspectState="未完成";
					}
					else{
						inspectState="无法获取数据";
					}
					jsonMap1.put("inspectState", inspectState);
					//如果为脏数据则不装入jsonList,并给提示。
					if(!"0".equals(resLongitude)&&!"0".equals(resLatitude)){
					jsonList1.add(jsonMap1);
					}
					else{
						Map errorMap=new HashMap();
						errorMap.put("error", "定位坐标不正确！");
						jsonList1.add(errorMap);
					}
				}
				allList.add(jsonList);
				allList.add(jsonList1);
				gisTrack_buffer.append(gson.toJson(allList));
				response.getWriter().write(gisTrack_buffer.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	
		


//		select dict.dictname,count(config.id) from pnr_res_config config,taw_system_dicttype dict where dict.dictid=config.specialty group by dict.dictname order by dict.dictname
//		select count(u.id) from partner_dwinfo info,pnr_user u where info.workerid=u.user_id and info.professional like'%1240201%' and info.isdelete='0' and u.deleted='0'
	}
	
	/**
	 * gis信息统计
	* @Title: gisInformation
	* @author huhao
	* @Description: TODO
	* @param @param mapping
	* @param @param form
	* @param @param request
	* @param @param response
	* @param @return    
	* @return ActionForward    
	* @throws
	 */
	public ActionForward gisInformation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
	
		CommonSpringJdbcServiceImpl jdbcService = (CommonSpringJdbcServiceImpl) ApplicationContextHolder
				.getInstance().getBean("commonSpringJdbcService");
		String resSql="select dict.dictname,config.specialty,count(config.id) as count from pnr_res_config config,taw_system_dicttype dict where dict.dictid=config.specialty group by dict.dictname,config.specialty order by dict.dictname,config.specialty";
		String userSql="select count(u.id) as count from partner_dwinfo info,pnr_user u where info.workerid=u.user_id and info.professional like'%1122501%' and info.isdelete='0' and u.deleted='0'";
//		String userSql2="select count(u.id) as count from partner_dwinfo info,pnr_user u where info.workerid=u.user_id and info.professional like'%1122502%' and info.isdelete='0' and u.deleted='0'";
		String userSql3="select count(u.id) as count from partner_dwinfo info,pnr_user u where info.workerid=u.user_id and info.professional like'%1122505%' and info.isdelete='0' and u.deleted='0'";
		String userSql4="select count(u.id) as count from partner_dwinfo info,pnr_user u where info.workerid=u.user_id and info.professional like'%1122504%' and info.isdelete='0' and u.deleted='0'";
		String userSql5="select count(u.id) as count from partner_dwinfo info,pnr_user u where info.workerid=u.user_id and info.professional like'%1122503%' and info.isdelete='0' and u.deleted='0'";
//		String oilSql="select areaid,areaname,nvl(num,0) as count from taw_system_area aaa left join( "
//					+" select count(*) as num,newarea from ( "
//					+" select *,substr(xxx.area,1,4) as newarea from pnr_oilengineinfo xxx  where length(area)>3 "
//					+" ) group by newarea ) bbb on aaa.areaid=bbb.newarea where length(areaid)=4 ";
//		String carSql="select areaname,nvl(num,0) as count,areaid from taw_system_area aaa left join( "
//					+" select count(*) as num,newarea from ( "
//					+" select *,substr(xxx.area,1,4) as newarea from pnr_carinfo xxx  where length(area)>3 "
//					+" ) group by newarea ) bbb on aaa.areaid=bbb.newarea where length(areaid)=4 ";
		
		//福建的地市ID为5位，取地市粒度统计的时候改为取areaid前5位
		String oilSql="select areaid,areaname,nvl(num,0) as count from taw_system_area aaa left join( "
			+" select count(*) as num,newarea from ( "
			+" select *,substr(xxx.area,1,5) as newarea from pnr_oilengineinfo xxx  where length(area)>3 "
			+" ) group by newarea ) bbb on aaa.areaid=bbb.newarea where length(areaid)=5 ";
        String carSql="select areaname,nvl(num,0) as count,areaid from taw_system_area aaa left join( "
			+" select count(*) as num,newarea from ( "
			+" select *,substr(xxx.area,1,5) as newarea from pnr_carinfo xxx  where length(area)>3 "
			+" ) group by newarea ) bbb on aaa.areaid=bbb.newarea where length(areaid)=5 ";
		
		List<ListOrderedMap> resList = jdbcService.queryForList(resSql);
		List<String> specialtyList=new ArrayList<String>();
		List<String> specialtyDictList=new ArrayList<String>();
		List<String> countSpecialtyList=new ArrayList<String>();
		List<String> countUserList=new ArrayList<String>();
		List<String> oilAreaList=new ArrayList<String>();
		List<String> oilCountList=new ArrayList<String>();
		List<String> carAreaList=new ArrayList<String>();
		List<String> carCountList=new ArrayList<String>();
		Map map;
		/**
		 * 代维资源数据装载
		 */
		for(int i=0;i<resList.size();i++){
			map=new HashMap();
			map=resList.get(i);
			specialtyList.add(map.get("dictname").toString());
			specialtyDictList.add(map.get("specialty").toString());
			String count=StaticMethod.nullObject2String(map.get("count"));
			if("".endsWith(count)){
				count="0";
			}
			countSpecialtyList.add(count);
		}
		/**
		 * 代维人员数据装载
		 */
		//基站设备及配套
		List<ListOrderedMap> userList = jdbcService.queryForList(userSql);
		String s=StaticMethod.nullObject2String(userList.get(0).get("count"));
		if("".endsWith(s)){
			s="0";
		}
		
		// 传输路线  
//		List<ListOrderedMap> userList2 = jdbcService.queryForList(userSql2);
//		String s2=StaticMethod.nullObject2String(userList2.get(0).get("count"));
//		if("".endsWith(s2)){
//			s2="0";
//		}
		
		//集客家客 
		List<ListOrderedMap> userList3 = jdbcService.queryForList(userSql3);
		String s3=StaticMethod.nullObject2String(userList3.get(0).get("count"));
		if("".endsWith(s3)){
			s3="0";
		}
		
		//铁塔及天馈
		List<ListOrderedMap> userList4 = jdbcService.queryForList(userSql4);
		String s4=StaticMethod.nullObject2String(userList4.get(0).get("count"));
		if("".endsWith(s4)){
			s4="0";
		}
		
		//直放站室分及WLAN
		List<ListOrderedMap> userList5 = jdbcService.queryForList(userSql5);
		String s5=StaticMethod.nullObject2String(userList5.get(0).get("count"));
		if("".endsWith(s5)){
			s5="0";
		}
//		countUserList.add(s2);
		countUserList.add(s);
		countUserList.add(s3);
		countUserList.add(s4);
		countUserList.add(s5);
		/**
		 * 代维车辆数据装载
		 */
		List<ListOrderedMap> carList = jdbcService.queryForList(carSql);
		for(int j=0;j<carList.size();j++){
			map=new HashMap();
			map=carList.get(j);
			carAreaList.add(map.get("areaname").toString());
			carCountList.add(map.get("count").toString());
		}
		/**
		 * 代维油机数据装载
		 */
		List<ListOrderedMap> oilList = jdbcService.queryForList(oilSql);
		for(int j=0;j<oilList.size();j++){
			map=new HashMap();
			map=oilList.get(j);
			oilAreaList.add(map.get("areaname").toString());
			oilCountList.add(map.get("count").toString());
		}
		request.setAttribute("specialtyList",specialtyList);
		request.setAttribute("carList",carList);
		request.setAttribute("oilList",oilList);
		request.setAttribute("specialtyDictList",specialtyDictList);
		request.setAttribute("countSpecialtyList",countSpecialtyList);
		request.setAttribute("countUserList",countUserList);
		request.setAttribute("carAreaList",oilAreaList);
		request.setAttribute("carCountList",oilCountList);
		request.setAttribute("oilAreaList",oilAreaList);
		request.setAttribute("oilCountList",oilCountList);
		

		return mapping.findForward("gisInformation");
		
//		 dictname     (count)    
//		 -----------  ---------- 
//		 传输线路         2476       
//		 基站设备及配套      39568      
//		 集客家客         8          
//		 铁塔及天馈        3          
//		 直放站室分及WLAN   2 
// [{dictname=传输线路, (count)=2476}, {dictname=基站设备及配套, (count)=39568}, {dictname=集客家客, (count)=8}, {dictname=铁塔及天馈, (count)=3}, {dictname=直放站室分及WLAN, (count)=2}]
			
	}
	
	/**
	 * 定位查看计划下的巡检资源
	* @Title: getInspectPlanMainDetailForArcGis
	* @author huhao
	* @Description: TODO
	* @param @param mapping
	* @param @param form
	* @param @param request
	* @param @param response
	* @param @return    
	* @return ActionForward    
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public ActionForward getInspectPlanMainDetailForArcGis(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		try {
		IInspectPlanMainMgr inspectPlanMainMgr = (IInspectPlanMainMgr)getBean("inspectPlanMainMgr");
		IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
		ID2NameService service = (ID2NameService) ApplicationContextHolder
		.getInstance().getBean("ID2NameGetServiceCatch");
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String id = StaticMethod.null2String(request.getParameter("id"));
		String planResId = StaticMethod.null2String(request.getParameter("planResId"));
		String resName = StaticMethod.null2String(request.getParameter("resName"));
		String inspectCycle = StaticMethod.null2String(request.getParameter("inspectCycle"));
		String executeObject = StaticMethod.null2String(request.getParameter("executeObject"));
		String inspectState = StaticMethod.null2String(request.getParameter("inspectState"));
		if(!"".equals(id)){
		InspectPlanMain planMain = inspectPlanMainMgr.find(id);
		request.setAttribute("planMain", planMain);
		}
		
		final Integer pageSize = CommonConstants.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		final int pageIndex = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		//String whereStr =" where 1=1 and planStartTime<='"+new DateTime().toString("yyyy-MM-dd HH:mm:ss")+"' and planEndTime>='"+new DateTime().toString("yyyy-MM-dd HH:mm:ss")+"'";
		String whereStr =" where 1=1 ";
		if("admin".equals(sessionForm.getUserid())&&!"".equals(id)){
			whereStr += " and planId='"+id+"'";
		}else if(sessionForm.getDeptid().length()<=7&&!"".equals(id)){
			whereStr += "  and planId='"+id+"' and executeDept like '"+sessionForm.getDeptid()+"%'";
		}else{
			if(!"".equals(id)){
			whereStr += "  and planId='"+id+"' and execute_dept='"+sessionForm.getDeptid()+"'";
			}
		}
		String userId = getUserId(request);
		PartnerUserMgr  partnerUserMgr = (PartnerUserMgr)getBean("partnerUserMgr");
		PartnerUser user = partnerUserMgr.getPartnerUserByUserId(userId);
		
		if(null != user){
			if(!"1".equals(user.getIsmanager())){
					String partnerid  = user.getPartnerid();
					whereStr+="  and  execute_object='"+partnerid+"'";
				}
		}
		if(!StringUtils.isEmpty(resName)){
			whereStr+="  and  resName like '%"+resName+"%'";
		}
		if(!StringUtils.isEmpty(inspectCycle)){
			whereStr+="  and  inspectCycle='"+inspectCycle+"'";
		}
		if(!StringUtils.isEmpty(executeObject)){
			whereStr+="  and  executeObject='"+executeObject+"'";
		}
		if(!StringUtils.isEmpty(inspectState)){
			whereStr+="  and  inspectState='"+inspectState+"'";
		}
		if(!"".equals(planResId)){
			whereStr+="  and  id='"+planResId+"'";
		}
		whereStr+=" and res_Longitude is not null and res_Latitude is not null ";
		Map map = (Map) inspectPlanResMgr.findInspectPlanResList(pageIndex, pageSize, whereStr);
		List list = (List) map.get("result");
		request.setAttribute("list", list);
		//对当前页面进行json封装
		List jsonList=new ArrayList();
		response.setCharacterEncoding("utf-8");
		for(int j=0;j<list.size();j++){
			InspectPlanRes pr=new InspectPlanRes();
			Map jsonMap=new HashMap();
			pr=(InspectPlanRes) list.get(j);
			jsonMap.put("resName", pr.getResName());
			String specialty= service.id2Name(pr.getSpecialty(), "tawSystemDictTypeDao");
			jsonMap.put("specialty", specialty);
			jsonMap.put("city", service.id2Name(pr.getCity(), "tawSystemAreaDao"));
			jsonMap.put("country", service.id2Name(pr.getCountry(), "tawSystemAreaDao"));
			jsonMap.put("resType", service.id2Name(pr.getResType(), "tawSystemDictTypeDao"));
			
			String planStartTime=StaticMethod.nullObject2String(pr.getPlanStartTime());
			jsonMap.put("planStartTime", planStartTime);
			String planEndTime=StaticMethod.nullObject2String(pr.getPlanEndTime());
			jsonMap.put("planEndTime",planEndTime);
			 executeObject=StaticMethod.nullObject2String(pr.getExecuteObject());
			jsonMap.put("executeObject",  service.id2Name(executeObject, "partnerDeptDao"));
			 inspectState=StaticMethod.nullObject2String(pr.getInspectState());
			//巡检状态(0待完成 1已完成 2超时未关联未完成 3超时已关联未完成)
				if ("1".equals(inspectState)) {
					inspectState="已完成";
				}else if("0".equals(inspectState)){
					inspectState="未完成";
				}else if("2".equals(inspectState)){
					inspectState="超时未关联未完成";
				}else if("3".equals(inspectState)){
					inspectState="超时已关联未完成";
				}
				else{
					inspectState="无法获取数据";
				}
				
				jsonMap.put("inspectState", inspectState);
				jsonMap.put("resLongitude",pr.getResLongitude());
				jsonMap.put("resLatitude",pr.getResLatitude());
				jsonMap.put("endLongitude",pr.getEndLongitude());
				jsonMap.put("endLatitude",pr.getEndLatitude());
				String signTime =StaticMethod.nullObject2String(pr.getSignTime());
				if("".equals(signTime)){
					signTime="未签到";
				}
				jsonMap.put("signTime",signTime);
				String inspectTime =StaticMethod.nullObject2String(pr.getInspectTime());
				if("".equals(inspectTime)){
					inspectTime="未签到";
				}
				jsonMap.put("inspectTime",inspectTime);
			jsonMap.put("inspectState", inspectState);
			jsonMap.put("resLongitude",pr.getResLongitude());
			jsonMap.put("resLatitude",pr.getResLatitude());
			jsonMap.put("endLongitude",pr.getEndLongitude());
			jsonMap.put("endLatitude(",pr.getEndLatitude());
			//去除脏数据
			if("1122502".equals(pr.getSpecialty())&&!"0".equals(pr.getResLatitude())&&!"0".equals(pr.getResLongitude())&&!"0".equals(pr.getEndLongitude())&&!"0".equals(pr.getEndLatitude())){
				jsonList.add(jsonMap);
			}
			else if(!"1122502".equals(pr.getSpecialty())&&!"0".equals(pr.getResLatitude())&&!"0".equals(pr.getResLongitude())){
			jsonList.add(jsonMap);
			}
		}
		StringBuffer json=new StringBuffer();
		Gson gson=new Gson();
		json.append(gson.toJson(jsonList));
		
			response.getWriter().write(json.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	public ActionForward getCountryCountDetailForArcGis(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		CommonSpringJdbcServiceImpl jdbcService = (CommonSpringJdbcServiceImpl) ApplicationContextHolder
		.getInstance().getBean("commonSpringJdbcService");
		
		String type = StaticMethod.nullObject2String(request.getParameter("type"));
		List<Object> list = new ArrayList<Object>();
		
		if("res".equals(type)){
			String city = StaticMethod.nullObject2String(request.getParameter("iscity"));
			String special = StaticMethod.nullObject2String(request.getParameter("special"));
			if("city".equals(city)){
				String ressql = "select a.areaid,a.areaname,nvl(b.count,0) as count from taw_system_area a left join" +
						" (select count(*) as count,city from pnr_res_config where specialty='"+special+"' group by city) b on a.areaid=b.city where length(a.areaid)=4";
				list= jdbcService.queryForList(ressql);
				request.setAttribute("list", list);
				request.setAttribute("type", "cityres");
				request.setAttribute("special", special);
			}else{   //此时是现实city地市下区县的资源情况
				String ressql = "select a.areaid,a.areaname,nvl(b.count,0) as count from taw_system_area a left join " +
						"(select count(*) as count,country from pnr_res_config where city='"+city+"' and specialty='"+special+"' group by country) b " +
						"on a.areaid = b.country where a.areaid like '"+city+"%' and a.areaid !='"+city+"'";
				list= jdbcService.queryForList(ressql);
				request.setAttribute("list", list);
				request.setAttribute("type", "countryres");
				request.setAttribute("special", special);
			}
		}else if("user".equals(type)){
			String city = StaticMethod.nullObject2String(request.getParameter("iscity"));
			String special = StaticMethod.nullObject2String(request.getParameter("special"));
			if("city".equals(city)){
//				String usersql ="select c.areaid,c.areaname,nvl(yyy.count,0) as count from taw_system_area c left join (select count(*) as count,xxx.area from " +
//						"(select substr(a.area_id,1,4) as area from pnr_user a join partner_dwinfo b on a.user_id=b.workerid" +
//						" where b.professional like'%"+special+"%')xxx group by xxx.area)yyy on c.areaid=yyy.area where length(c.areaid)=4";
				//福建判断地市id为areaid前5位，此处改为前5位
				String usersql ="select c.areaid,c.areaname,nvl(yyy.count,0) as count from taw_system_area c left join (select count(*) as count,xxx.area from " +
				"(select substr(a.area_id,1,5) as area from pnr_user a join partner_dwinfo b on a.user_id=b.workerid" +
				" where b.professional like'%"+special+"%')xxx group by xxx.area)yyy on c.areaid=yyy.area where length(c.areaid)=5";
				list= jdbcService.queryForList(usersql);
				request.setAttribute("list", list);
				request.setAttribute("type", "cityuser");
				request.setAttribute("special", special);
			}else{
				String usersql="select c.areaid,c.areaname,nvl(xxx.count,0) as count from taw_system_area c left join " +
						"(select a.area_id,count(*) as count from pnr_user a join partner_dwinfo b on a.user_id=b.workerid where" +
						" b.professional like'%"+special+"%' and a.area_id like'"+city+"%' group by a.area_id)xxx on c.areaid=xxx.area_id where " +
						"c.areaid like'"+city+"%' and c.areaid!='"+city+"'";
				list= jdbcService.queryForList(usersql);
				request.setAttribute("list", list);
				request.setAttribute("type", "countryuser");
				request.setAttribute("special", special);
			}
		}else if("car".equals(type)){
			String city = StaticMethod.nullObject2String(request.getParameter("iscity"));
			String carsql = "select a.areaid,a.areaname,nvl(b.count,0) as count from taw_system_area a left join  " +
					"(select count(*) as count,area from pnr_carinfo  where area like '"+city+"%' and area!='"+city+"' group by area )b on" +
					" a.areaid = b.area where a.areaid like '"+city+"%' and a.areaid !='"+city+"'";
			list= jdbcService.queryForList(carsql);
			request.setAttribute("list", list);
			request.setAttribute("type", "countrycal");
		}else if("oil".equals(type)){
			String city = StaticMethod.nullObject2String(request.getParameter("iscity"));
			String oilsql ="select a.areaid,a.areaname,nvl(b.count,0) as count from taw_system_area a left join " +
					" (select count(*) as count,area from pnr_oilengineinfo  where area like '"+city+"%' and area!='"+city+"' group by area )b on" +
					" a.areaid = b.area where a.areaid like '"+city+"%' and a.areaid !='"+city+"'";
			list= jdbcService.queryForList(oilsql);
			request.setAttribute("list", list);
			request.setAttribute("type", "countryoil");
		}
		return mapping.findForward("countryCountDetailForArcGis");
	}
	
	/**
	 * 车辆列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward carList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CarService carService=(CarService)getBean("carService");
		Search search=new Search();
		int firstResult=CommonUtils.getFirstResultOfDisplayTag(request, "carList");
		Integer pageSize = UtilMgrLocator.getEOMSAttributes().getPageSize();
		search.setFirstResult(firstResult*pageSize);
		search.setMaxResults(pageSize);
		//获取区域id作为删选条件
//		TawSystemSessionForm sessionForm=this.getUser(request);
//		String deptid=sessionForm.getDeptid();
//		List<PartnerDept>  list0=new ArrayList<PartnerDept>();
//		if (!"admin".equals(sessionForm.getUserid())) {
//			PartnerDeptMgr partnerDeptMgr=(PartnerDeptMgr)getBean("partnerDeptMgr");
//			list0=partnerDeptMgr.getPartnerDepts("and deptMagId='"+deptid+"'");
//			if (list0.size()!=0&&list0!=null) {//不等于0表示是代维公司的
//				search.addFilterILike("maintainCompany", deptid+"%");//代维公司权限限定
//			}else {
//				ITawSystemDeptManager deptManager=(ITawSystemDeptManager)getBean("ItawSystemDeptManager");
//				TawSystemDept dept=deptManager.getDeptinfobydeptid(deptid,"0");
//				if (dept!=null) {
//					search.addFilterILike("area", dept.getAreaid()+"%");//区域权限限定
//				}
//			}
//		}
		search.addFilterNotEqual("deleted", "1");//1表示不删除
		search.addFilterNotEqual("visible", "1");//1表示不可见，正在执行流程申请中
		search=CommonUtils.getSqlFromRequestMap(request, search);
		SearchResult<Car> searchResult=carService.searchAndCount(search);
		List list=searchResult.getResult();
		int resultSize=searchResult.getTotalCount();
		request.setAttribute("carList", list);
		request.setAttribute("resultSize", resultSize);
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("carList");
	}
	
	
	/**
	 * 车辆详情
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward carDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id=StaticMethod.null2String(request.getParameter("id"));
		CarService carService=(CarService)getBean("carService");
		ID2NameService service = (ID2NameService) ApplicationContextHolder.getInstance().getBean("ID2NameGetServiceCatch");
		Car car=carService.find(id);
		try {
			response.setCharacterEncoding("utf-8");
			Gson gson = new Gson();
			HashMap<String, String> jsonMap;
			List<Map<String, String>> jsonList = new ArrayList<Map<String, String>>();
			StringBuffer gisTrack_buffer = new StringBuffer();
			jsonMap=new HashMap();
			String carUrl="/partner/resourceInfo/car.do?method=detail&&id="+car.getId();
			jsonMap.put("carUrl",carUrl);
			jsonMap.put("carNumber",car.getCarNumber());
			jsonMap.put("carType",car.getCarType());
			jsonMap.put("company",service.id2Name(car.getMaintainCompany(), "tawSystemDeptDao"));
			jsonMap.put("area",service.id2Name(car.getArea(), "tawSystemAreaDao"));
			jsonMap.put("driver",service.id2Name(car.getDriver(), "partnerUserDao"));
			jsonMap.put("driverContact",car.getDriverContact());
			jsonMap.put("longitude",car.getLongitude());
			jsonMap.put("latitude",car.getLatitude());
//			if(!"".equals((StaticMethod.null2String(car.getLongitude())))&&!"".equals((StaticMethod.null2String(car.getLatitude())))){
			jsonList.add(jsonMap);
//			}
				gisTrack_buffer.append(gson.toJson(jsonList));
				response.getWriter().write(gisTrack_buffer.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	}
	
	
	
	/**
	 * 人员列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward userList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				PartnerUserConstants.PARTNERUSER_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		PartnerUserMgr partnerUserMgr = (PartnerUserMgr) getBean("partnerUserMgr");
		//状态不为未上岗并且必须为审批通过
		String whereStr = " where approveStatus=1 ";
		PartnerUser user = partnerUserMgr.getPartnerUserByUserId(sessionForm.getUserid());
		
		if(null != user){//不为空说明是代维公司人查看
			whereStr += " and deptId like '"+user.getDeptId()+"%'";
		}else if(!"admin".equals(sessionForm.getUserid())){//移动公司代维
			ITawSystemDeptManager mgr = (ITawSystemDeptManager)getBean("ItawSystemDeptManager");

			TawSystemDept dept = mgr.getDeptinfobydeptid(sessionForm.getDeptid(), "0");
			String areaid = dept.getAreaid();
			if(areaid!=null && !areaid.trim().equals("")){
			  whereStr += "  and areaId like '"+areaid+"%'"; 
			}else{
				 whereStr += "  and 1=0";
			}

		}
		//组装查询条件
		if(request.getParameter("nameSearch")!=null&&!request.getParameter("nameSearch").equals("")){
			whereStr += " and name like '%"+request.getParameter("nameSearch")+"%'";
			request.setAttribute("nameSearch", request.getParameter("nameSearch"));
		}
		//------------------
		if(request.getParameter("phoneSearch")!=null&&!request.getParameter("phoneSearch").equals("")){
			whereStr += " and phone like '%"+request.getParameter("phoneSearch")+"%'";
			request.setAttribute("phoneSearch", request.getParameter("phoneSearch"));
		}
		if(request.getParameter("emailSearch")!=null&&!request.getParameter("emailSearch").equals("")){
			whereStr += " and email like '%"+request.getParameter("emailSearch")+"%'";
			request.setAttribute("emailSearch", request.getParameter("emailSearch"));
		}
		if(request.getParameter("prov")!=null&&!request.getParameter("prov").equals("")&&!"请选择".equals(request.getParameter("prov"))){
			whereStr += " and partnerid ='"+request.getParameter("prov")+"'";
			request.setAttribute("prov", request.getParameter("prov"));
		}
		Map map = (Map) partnerUserMgr.getPartnerUsers(pageIndex, pageSize, whereStr);
		List list = (List) map.get("result");
		request.setAttribute(PartnerUserConstants.PARTNERUSER_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("userList");
	}
	
	/**
	 * 人员查看页面
	 * 
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
		PartnerUserMgr partnerUserMgr = (PartnerUserMgr) getBean("partnerUserMgr");
		ID2NameService service = (ID2NameService) ApplicationContextHolder.getInstance().getBean("ID2NameGetServiceCatch");
		String id = StaticMethod.nullObject2String((request.getParameter("id")));
		PartnerUser partnerUser = partnerUserMgr.getPartnerUser(id);
		try {
			response.setCharacterEncoding("utf-8");
			Gson gson = new Gson();
			HashMap<String, String> jsonMap;
			List<Map<String, String>> jsonList = new ArrayList<Map<String, String>>();
			StringBuffer gisTrack_buffer = new StringBuffer();
			jsonMap=new HashMap();
			String userUrl="/partner/baseinfo/resumes.do?method=newExpert&&searchInto=Y&&id="+partnerUser.getId();
			jsonMap.put("userUrl",userUrl);
			jsonMap.put("userName",partnerUser.getName());
			jsonMap.put("userNo",partnerUser.getUserNo());
			jsonMap.put("sex",service.id2Name(partnerUser.getSex(), "tawSystemDictTypeDao"));
			jsonMap.put("area",service.id2Name(partnerUser.getAreaId(), "tawSystemAreaDao"));
			jsonMap.put("licenseNo",partnerUser.getLicenseNo());
			jsonMap.put("dept",service.id2Name(partnerUser.getDeptId(), "tawSystemDeptDao"));
			jsonMap.put("phone",partnerUser.getPhone());
			jsonMap.put("email",partnerUser.getEmail());
			jsonMap.put("longitude",partnerUser.getLongtitude());
			jsonMap.put("latitude",partnerUser.getLatitude());
//			if(!"".equals((StaticMethod.null2String(partnerUser.getLongtitude())))&&!"".equals((StaticMethod.null2String(partnerUser.getLatitude())))){
				jsonList.add(jsonMap);
//			}
				gisTrack_buffer.append(gson.toJson(jsonList));
				response.getWriter().write(gisTrack_buffer.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	}
}
