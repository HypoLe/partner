package com.boco.eoms.arcGis.webapp.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import sun.misc.BASE64Decoder;
import utils.PartnerPrivUtils;

import com.boco.eoms.arcGis.model.GisOrder;
import com.boco.eoms.arcGis.model.Trajectory;
import com.boco.eoms.arcGis.util.ArcGisConstacts;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.dict.service.ID2NameService;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcServiceImpl;
import com.boco.eoms.deviceManagement.common.utils.CommonConstants;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerUser;
import com.boco.eoms.partner.baseinfo.util.PartnerCityByUser;
import com.boco.eoms.partner.baseinfo.util.PartnerUserConstants;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;
import com.boco.eoms.partner.inspect.mgr.IInspectLineTrackMgr;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanMainMgr;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanResMgr;
import com.boco.eoms.partner.inspect.mgr.IPnrInspectTrackMgr;
import com.boco.eoms.partner.inspect.model.InspectLineTrack;
import com.boco.eoms.partner.inspect.model.InspectPlanMain;
import com.boco.eoms.partner.inspect.model.InspectPlanRes;
import com.boco.eoms.partner.inspect.model.PnrInspectTrack;
import com.boco.eoms.partner.res.mgr.PnrResConfigMgr;
import com.boco.eoms.partner.res.model.PnrResConfig;
import com.boco.eoms.partner.res.webapp.form.PnrResConfigForm;
import com.boco.eoms.partner.resourceInfo.model.Car;
import com.boco.eoms.partner.resourceInfo.model.OilEngine;
import com.boco.eoms.partner.resourceInfo.service.CarService;
import com.boco.eoms.partner.resourceInfo.service.OilEngineService;
import com.boco.eoms.partner.resourceInfo.util.ResourceInfoUtils;
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
public class ArcGisAction extends BaseAction {
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
//		String beanName=ResultIntoModel.getBeanName("com.boco.eoms.partner.baseinfo.model.PartnerUser");
//		List<String> list=ResultIntoModel.getBeanPropertyList("com.boco.eoms.partner.baseinfo.model.PartnerUser");
//		String aaa=ResultIntoModel.getBeanFilesList("com.boco.eoms.partner.baseinfo.model.PartnerUser");
//		Class lass = null;
//		try {
//			lass = Class.forName("com.boco.eoms.partner.baseinfo.model.PartnerUser");
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		String columNames=ResultIntoModel.getColumnNames(lass);
//		String columName=ResultIntoModel.getColumnNameByProperName(lass, "deptId");
//		String end="";
		return mapping.findForward("goToArcGis");
	}
	
	
	/**
	 * 跳转到地图
	* @Title: goToArcGisMap
	* @author huhao
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
		String type = request.getParameter("type");
		
		
		
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String areaid=com.boco.eoms.partner.process.util.CommonUtils.getAreaIdByDeptId(sessionForm.getDeptid());
//		areaid="281101";
		String px="117.0249670000";
		String py="36.6827850000";//2819	济南
		if(areaid.length()>4){
			String cityId = areaid.substring(0, 4);
			if("2811".equals(cityId)){//2811	威海 37.5193220000,122.1282450000
				px="122.1282450000";
			    py="37.5193220000";
			}else if("2813".equals(cityId)){//2813	滨州 37.3883870000,117.9792000000
				px="117.9792000000";
			    py="37.3883870000";
			}else if("2823".equals(cityId)){//2823	东营 37.4399900000,118.6810460000
				px="118.6810460000";
			    py="37.4399900000";
				
			}else if("2816".equals(cityId)){//2816	临沂 35.1105310000,118.3629900000
				px="118.3629900000";
			    py="35.1105310000";
				
			}else if("2827".equals(cityId)){//2827	泰安 36.2059050000,117.0948930000
				px="117.0948930000";
			    py="36.2059050000";
				
			}else if("2820".equals(cityId)){//2820	青岛  36.0723580000,120.3894450000
				px="120.3894450000";
			    py="36.0723580000";
				
			}else if("2821".equals(cityId)){//2821	淄博  36.8191820000,118.0612540000
				px="118.0612540000";
			    py="36.8191820000";
				
			}else if("2822".equals(cityId)){//2822	枣庄 34.8165690000,117.3285130000
				px="117.3285130000";
			    py="34.8165690000";
				
			}else if("2824".equals(cityId)){//2824	烟台 37.4698680000,121.4544250000
				px="121.4544250000";
			    py="37.4698680000";
				
			}else if("2825".equals(cityId)){// 2825	潍坊 36.7132120000,119.1681380000
				px="119.1681380000";
			    py="36.7132120000";
				
			}else if("2826".equals(cityId)){//2826	济宁 35.4202690000,116.5938520000
				px="116.5938520000";
			    py="35.4202690000";
			    			
			}else if("2814".equals(cityId)){//2814	德州 37.4413130000,116.3658250000
				px="116.3658250000";
			    py="37.4413130000";
				
			}else if("2812".equals(cityId)){//2812	日照 35.4227980000,119.5336060000
				px="119.5336060000";
			    py="35.4227980000";
			    
			}else if("2817".equals(cityId)){// 2817	菏泽 35.2394350000,115.4876960000
				px="115.4876960000";
			    py="35.2394350000";
				
			}else if("2815".equals(cityId)){//2815	聊城 36.4626810000,115.9920770000
				px="115.9920770000";
			    py="36.4626810000";
				
			}else if("2818".equals(cityId)){//2818	莱芜 36.2193570000,117.6832210000
				px="117.6832210000";
			    py="36.2193570000";
				
			}
		}
		request.setAttribute("centerlng",px);
		request.setAttribute("centerlat",py);
		if(type==null || "".equals(type)){
			return mapping.findForward("goToArcGisMap");
		}else{
			return mapping.findForward("goToArcGisMap2");
		}
		
	}
	/**
	 * 跳转到地图-新版
	 * @Title: toSeleArcGis
	 * @param @param mapping
	 * @param @param form
	 * @param @param request
	 * @param @param response
	 * @param @return    
	 * @return ActionForward    
	 * @throws
	 */
	public ActionForward toSeleArcGis(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		 TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
 	    ITawSystemDeptManager deptSys = (ITawSystemDeptManager)ApplicationContextHolder.getInstance().getBean("ItawSystemDeptManager");
 	    String city = "28";
 	    String deptId = sessionForm.getDeptid();
	    TawSystemDept dt= deptSys.getDeptinfobydeptid(deptId,"0");
	    
	    if(dt!=null){
	    	city = dt.getAreaid();
	    	if(city.length()>4){
	    		city = city.substring(0,4);
	    	}
	    }
//	    System.out.println("------city--------:"+city);
		request.setAttribute("city",city);
		return mapping.findForward("toSeleArcGis");
	}

	/**
	 * 基站查询
	* @Title: search
	* @author huhao
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
		String pagesize = StaticMethod.nullObject2String(request.getParameter("pagesize"));
		
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList) getBean("pnrBaseAreaIdList");
		ID2NameService service = (ID2NameService) ApplicationContextHolder
									.getInstance().getBean("ID2NameGetServiceCatch");
		String province = pnrBaseAreaIdList.getRootAreaId();
		String whereStr = " where specialty<>'1122502' ";
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
		//控制地市权限
	    TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
    	ITawSystemDeptManager deptMgr = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
		TawSystemDept dept = deptMgr.getTawSystemDept(sessionForm.getDeptpriid());
		String areaId = dept.getAreaid();
		if(areaId!=null&&areaId.length()>4){
			areaId = areaId.substring(0,4);			
		}
		whereStr = whereStr+" and city like '"+areaId+"%"+"'";
		//备用
//		select * from taw_system_area aa where aa.areaid =(
//				select a.parentareaid from taw_system_area a 
//				where a.areaid =
//				(select areaid from taw_system_dept d 
//				where d.deptid = (select u.deptid from taw_system_user u where u.userid ='miaofu1'))
//				)
		//控制地市权限 -end
		Map map = null;
		if(null != pagesize && ""!=pagesize){
			map = pnrResConfiMgr.getResources(firstResult
					* Integer.parseInt(pagesize) , Integer.parseInt(pagesize),
					whereStr);
		}else{
			map = pnrResConfiMgr.getResources(firstResult
					* CommonConstants.PAGE_SIZE, CommonConstants.PAGE_SIZE,
					whereStr);
		}

		
		List<PnrResConfig> list = new ArrayList<PnrResConfig>();
		list = (List<PnrResConfig>) map.get("result");
		request.setAttribute("list", list);
		//对当前页面进行json封装
		List<Map<String,String> > jsonList=new ArrayList<Map<String,String> >();
		for(int j=0;j<list.size();j++){
			PnrResConfig pr=new PnrResConfig();
			Map<String,String> jsonMap=new HashMap<String,String> ();
			pr=(PnrResConfig) list.get(j);
			String resUrl="/partner/partner/res/PnrResConfig.do?method=detial&gisType=gisType&seldelcar="+pr.getId();
			jsonMap.put("resUrl", resUrl);
			jsonMap.put("resName", pr.getResName());
			jsonMap.put("specialty", service.id2Name(pr.getSpecialty(), "ItawSystemDictTypeDao"));
			//jsonMap.put("resType", service.id2Name(pr.getResType(), "tawSystemDictTypeDao")+"-"+service.id2Name(pr.getResLevel(), "tawSystemDictTypeDao"));
			jsonMap.put("resType", service.id2Name(pr.getResType(), "ItawSystemDictTypeDao"));
			jsonMap.put("resLevel",service.id2Name(pr.getResLevel(), "ItawSystemDictTypeDao"));
			jsonMap.put("resLevelId",pr.getResLevel());

			jsonMap.put("city", service.id2Name(pr.getCity(), "tawSystemAreaDao"));
			jsonMap.put("country", service.id2Name(pr.getCountry(), "tawSystemAreaDao"));
			
			jsonMap.put("resLongitude",pr.getResLongitude());
			jsonMap.put("resLatitude",pr.getResLatitude());
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
		request.setAttribute("jsonString", jsonString);

		//com.boco.eoms.common.log.BocoLog.info("ArcGisAction-search", 383, "jsonString:"+jsonString);
		request.setAttribute("pnrResConfigForm", pnrResConfigForm);
		if(null != pagesize && ""!=pagesize){
			request.setAttribute("pageSize", pagesize);
		}else{
			request.setAttribute("pageSize", CommonConstants.PAGE_SIZE);
		}
		
		
		request.setAttribute("resultSize", map.get("total"));

		return mapping.findForward("list");
	}

	/**
	 * @throws Exception 
	 * 地图渲染所选基站
	* @Title: siteSelected
	* @author huhao
	* @param @param mapping
	* @param @param form
	* @param @param request
	* @param @return    
	* @return ActionForward    
	* @throws
	 */
	public ActionForward siteSelected(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		CommonSpringJdbcServiceImpl jdbcService = (CommonSpringJdbcServiceImpl) ApplicationContextHolder
				.getInstance().getBean("commonSpringJdbcService");
		@SuppressWarnings("unused")
		ID2NameService service = (ID2NameService) ApplicationContextHolder
		.getInstance().getBean("ID2NameGetServiceCatch");
		String id = StaticMethod.nullObject2String(request.getParameter("id"));
		String type = StaticMethod.nullObject2String(request.getParameter("type"));
		String where="";
		if(!"".equals(type)){
			 where="res.specialty='"+type+"' and ";
		}
		if (!"".equals(id)) {
			String sql = "select res.id,res.res_name,dict.dictname as specialty,dict2.dictid resLevelId,dict1.dictname as restype,dict2.dictname as resLevel,res.EXECUTE_OBJECT,res.res_longitude,res.res_latitude,res.end_longitude,res.end_latitude,areacity.areaname as city,areacountry.areaname as country,res.street,res.company_name,res.contact_name,res.phone "
			+" from  pnr_res_config res,taw_system_dicttype dict,taw_system_area  areacity,taw_system_area  areacountry,taw_system_dicttype dict1,taw_system_dicttype dict2 "
			+" where "
			+where
			+" res.specialty=dict.dictid and res.city=areacity.areaid and res.country=areacountry.areaid  and dict1.dictid=res.res_type and dict2.dictid = res.res_level "
			+" and res.res_longitude is not null and res.res_latitude is not null and res.id='" + id + "'";
			@SuppressWarnings("unchecked")
			List<ListOrderedMap> list = jdbcService.queryForList(sql);
			com.boco.eoms.common.log.BocoLog.info("transient-ArcGisAction-siteSelected:sql：",432,sql);
			try {
			response.setCharacterEncoding("utf-8");
			List<Map<String, String>> jsonList = new ArrayList<Map<String, String>>();
			Gson gson = new Gson();
			HashMap<String, String> jsonMap;
			StringBuffer grid_buffer = new StringBuffer();
			for (int i = 0; i < list.size(); i++) {
				jsonMap = new HashMap<String, String>();
				ListOrderedMap map = list.get(i);
				String resUrl="/partner/res/PnrResConfig.do?method=detial&gisType=gisType&seldelcar="+map.get("id");
				String resName=StaticMethod.nullObject2String(map.get("res_name"));
				String specialty=StaticMethod.nullObject2String(map.get("specialty"));
//				//如果是线路则跳转
//				if("1122502".equals(specialty)){
//					request.setAttribute("seldelcar", map.get("id"));
//					 gotoTransLinePointList( mapping,  form,
//							 request,  response);
//				}
				
				String resType=StaticMethod.nullObject2String(map.get("restype"));
				String resLevel=StaticMethod.nullObject2String(map.get("resLevel"));
				String resLevelId=StaticMethod.nullObject2String(map.get("resLevelId"));
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
				String executeObjectId=executeObject;
				if("".equals(executeObject)){
					executeObject="无";
				}
				else{
					String deptSql="select name from pnr_dept where id='"+executeObject+"'";
					@SuppressWarnings("unchecked")
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
				jsonMap.put("resLevel", resLevel);
				jsonMap.put("resLevelId", resLevelId);
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
						Map<String,String> errorMap=new HashMap<String,String>();
						errorMap.put("error", "定位坐标不正确！");
						jsonList.add(errorMap);
					}
					
					jsonMap.put("executeObject", executeObject);
					String userConfigUrl="/partner/arcGis/arcGis.do?method=getDistanceForUser&&executeObjectId="+executeObjectId;
					jsonMap.put("userConfigUrl",userConfigUrl);
					
				}
				else {
					
					if(!"0".equals(resLongitude)&&!"0".equals(resLatitude)){
						jsonMap.put("executeObject", executeObject);
						String userConfigUrl="/partner/arcGis/arcGis.do?method=getDistanceForUser&&executeObjectId="+executeObjectId;
						jsonMap.put("userConfigUrl",userConfigUrl);
						jsonList.add(jsonMap);
						}
					else{
						Map<String,String> errorMap=new HashMap<String,String>();
						errorMap.put("error", "定位坐标不正确！");
						jsonList.add(errorMap);
					}
					
				}
			}
			grid_buffer.append(gson.toJson(jsonList));
				response.getWriter().write(grid_buffer.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * @throws Exception 
	 * 巡检资源详情
	* @Title: detial
	* @author huhao
	* @param @param mapping
	* @param @param form
	* @param @param request
	* @param @param response
	* @param @return    
	* @return ActionForward    
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public ActionForward detial(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
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
//		String subResTable =  pnrResConfig.getSubResTable();
		String typeSpecialty=pnrResConfig.getSpecialty();
		
		try {
		response.setCharacterEncoding("utf-8");
		Gson gson = new Gson();
		HashMap<String, String> jsonMap;
		List<Map<String, String>> jsonList = new ArrayList<Map<String, String>>();
		StringBuffer pnrResConfigStation_buffer = new StringBuffer();
		jsonMap = new HashMap<String, String>();
//		String endResLongitude="";
//		String endResLatitude="";
//		//传输线路
//		if("pnr_res_line".equals(subResTable)){
//			PnrResLineMgr PnrResLineMgr = (PnrResLineMgr)getBean("PnrResLineMgr");
//			if(!StringUtils.isEmpty(pnrResConfig.getSubResId())){
//				 endResLongitude=StaticMethod.nullObject2String(pnrResConfig.getEndLongitude());
//				 endResLatitude=StaticMethod.nullObject2String(pnrResConfig.getEndLatitude());
//				jsonMap.put("endResLongitude",endResLongitude);
//				jsonMap.put("endResLatitude",endResLatitude);
//				jsonMap.put("type","line");
//			}
//		}
		if("1122502".equals(typeSpecialty)){
			 gotoTransLinePointList( mapping,  form,
					 request,  response);
		}else{
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
		String executeObjectId=executeObject;
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
		String resUrl="/partner/res/PnrResConfig.do?method=detial&gisType=gisType&seldelcar="+pnrResConfig.getId();
		jsonMap.put("resUrl", resUrl);
		jsonMap.put("resName", resName);
		jsonMap.put("resLongitude",resLongitude);
		jsonMap.put("resLatitude",resLatitude);
		jsonMap.put("specialty",specialty);
		jsonMap.put("resType",resType);
		jsonMap.put("city",city);
		jsonMap.put("country", country);
		jsonMap.put("executeObject", executeObject);
		String userConfigUrl="/partner/arcGis/arcGis.do?method=getDistanceForUser&&executeObjectId="+executeObjectId;
		jsonMap.put("userConfigUrl",userConfigUrl);
		jsonMap.put("street",street);
		jsonMap.put("companyName",companyName);
		jsonMap.put("contactName",contactName);
		jsonMap.put("phone",phone);
		if(!"".equals(resLongitude)&&!"".equals(resLatitude)){
		jsonList.add(jsonMap);
		}
		else{
		Map<String,String> errorMap=new HashMap<String,String>();
		errorMap.put("error", "定位坐标不正确！");
		jsonList.add(errorMap);
		}
		pnrResConfigStation_buffer.append(gson.toJson(jsonList));
		
			response.getWriter().write(pnrResConfigStation_buffer.toString());
		}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
/**
 * 巡检轨迹
* @Title: gisTrack
* @author huhao
* @param @param mapping
* @param @param form
* @param @param request
* @param @param response
* @param @return
* @param @throws IOException    
* @return ActionForward    
* @throws
 */
	@SuppressWarnings("unchecked")
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
					Map<String,String> errorMap=new HashMap<String,String>();
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
						Map<String,String> errorMap=new HashMap<String,String>();
						errorMap.put("error", "定位坐标不正确！");
						jsonList1.add(errorMap);
					}
				}
				allList.add(jsonList);
				allList.add(jsonList1);
				gisTrack_buffer.append(gson.toJson(allList));
				response.getWriter().write(gisTrack_buffer.toString());
			} catch (IOException e) {
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
	* @param @param mapping
	* @param @param form
	* @param @param request
	* @param @param response
	* @param @return    
	* @return ActionForward    
	* @throws
	 */
	@SuppressWarnings("unchecked")
	public ActionForward gisInformation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
	
		CommonSpringJdbcServiceImpl jdbcService = (CommonSpringJdbcServiceImpl) ApplicationContextHolder
				.getInstance().getBean("commonSpringJdbcService");
		String resSql="select dict.dictname,config.specialty,count(config.id) as count from pnr_res_config config,taw_system_dicttype dict where dict.dictid=config.specialty group by dict.dictname,config.specialty order by dict.dictname,config.specialty";
//		String userSql="select count(u.id) as count from partner_dwinfo info,pnr_user u where info.workerid=u.user_id and info.professional like'%1122501%' and info.isdelete='0' and u.deleted='0'";
//		String userSql2="select count(u.id) as count from partner_dwinfo info,pnr_user u where info.workerid=u.user_id and info.professional like'%1122502%' and info.isdelete='0' and u.deleted='0'";
//		String userSql3="select count(u.id) as count from partner_dwinfo info,pnr_user u where info.workerid=u.user_id and info.professional like'%1122505%' and info.isdelete='0' and u.deleted='0'";
//		String userSql4="select count(u.id) as count from partner_dwinfo info,pnr_user u where info.workerid=u.user_id and info.professional like'%1122504%' and info.isdelete='0' and u.deleted='0'";
//		String userSql5="select count(u.id) as count from partner_dwinfo info,pnr_user u where info.workerid=u.user_id and info.professional like'%1122503%' and info.isdelete='0' and u.deleted='0'";
		String oilSql="select areaid,areaname,nvl(num,0) as count from taw_system_area aaa left join( "
					+" select count(*) as num,newarea from ( "
					+" select xxx.*,substr(xxx.area,1,4) as newarea from pnr_oilengineinfo xxx  where length(area)>3 AND deleted='0'"
					+" ) group by newarea ) bbb on aaa.areaid=bbb.newarea where length(areaid)=4 order by areaid";
		String carSql="select areaname,nvl(num,0) as count,areaid from taw_system_area aaa left join( "
					+" select count(*) as num,newarea from ( "
					+" select xxx.*,substr(xxx.area,1,4) as newarea from pnr_carinfo xxx  where length(area)>3 AND deleted='0'"
					+" ) group by newarea ) bbb on aaa.areaid=bbb.newarea where length(areaid)=4 order by areaid";
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
			map=new HashMap<String,String>();
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
		for (int s=0;s<specialtyDictList.size();s++){
			String searchSpecialty=specialtyDictList.get(s);
			String userSpecialtySql="select count(u.id) as count from partner_dwinfo info,pnr_user u where info.workerid=u.user_id and info.professional like'%"+searchSpecialty+"%' and info.isdelete='0' and u.deleted='0'";
			List<ListOrderedMap> userList = jdbcService.queryForList(userSpecialtySql);
			String userSpecialtyCount=StaticMethod.nullObject2String(userList.get(0).get("count"));
			if("".endsWith(userSpecialtyCount)){
				userSpecialtyCount="0";
			}
			countUserList.add(userSpecialtyCount);
		}
		
		//基站设备及配套
//		List<ListOrderedMap> userList = jdbcService.queryForList(userSql);
//		String s=StaticMethod.nullObject2String(userList.get(0).get("count"));
//		if("".endsWith(s)){
//			s="0";
//		}
		
		// 传输路线  
//		List<ListOrderedMap> userList2 = jdbcService.queryForList(userSql2);
//		String s2=StaticMethod.nullObject2String(userList2.get(0).get("count"));
//		if("".endsWith(s2)){
//			s2="0";
//		}
		
		//集客家客 
//		List<ListOrderedMap> userList3 = jdbcService.queryForList(userSql3);
//		String s3=StaticMethod.nullObject2String(userList3.get(0).get("count"));
//		if("".endsWith(s3)){
//			s3="0";
//		}
//		
//		//铁塔及天馈
//		List<ListOrderedMap> userList4 = jdbcService.queryForList(userSql4);
//		String s4=StaticMethod.nullObject2String(userList4.get(0).get("count"));
//		if("".endsWith(s4)){
//			s4="0";
//		}
//		
//		//直放站室分及WLAN
//		List<ListOrderedMap> userList5 = jdbcService.queryForList(userSql5);
//		String s5=StaticMethod.nullObject2String(userList5.get(0).get("count"));
//		if("".endsWith(s5)){
//			s5="0";
//		}
////		countUserList.add(s2);
////		countUserList.add(s);
//		countUserList.add(s3);
//		countUserList.add(s4);
//		countUserList.add(s5);
		
		/**
		 * 代维车辆数据装载
		 */
		List<ListOrderedMap> carList = jdbcService.queryForList(carSql);
		for(int j=0;j<carList.size();j++){
			map=new HashMap<String,String>();
			map=carList.get(j);
			carAreaList.add(map.get("areaname").toString());
			carCountList.add(map.get("count").toString());
		}
		/**
		 * 代维油机数据装载
		 */
		List<ListOrderedMap> oilList = jdbcService.queryForList(oilSql);
		for(int j=0;j<oilList.size();j++){
			map=new HashMap<String,String>();
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
		request.setAttribute("carCountList",carAreaList);
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
	 * @throws Exception 
	 * 定位查看计划下的巡检资源
	* @Title: getInspectPlanMainDetailForArcGis
	* @author huhao
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
			HttpServletRequest request, HttpServletResponse response) throws Exception{
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
//		whereStr+=" and res_Longitude is not null and res_Latitude is not null ";
		@SuppressWarnings("rawtypes")
		Map map = (Map) inspectPlanResMgr.findInspectPlanResList(pageIndex, pageSize, whereStr);
		List<InspectPlanRes> list = (List<InspectPlanRes>) map.get("result");
		request.setAttribute("list", list);
		//如果位传输线路，则过滤
		InspectPlanRes plan=(InspectPlanRes) list.get(0);
		String typeSpecialty=StaticMethod.nullObject2String(plan.getSpecialty());
		String resCfgId=StaticMethod.nullObject2String(plan.getResCfgId());
		if(typeSpecialty.equals("1122502")){
			request.setAttribute("resCfgId", resCfgId);
			gotoTransLinePointList( mapping,  form,
					 request,  response);
		}
		else{
		//对当前页面进行json封装
		List<Map<String,Object>> jsonList=new ArrayList<Map<String,Object>>();
		response.setCharacterEncoding("utf-8");
		for(int j=0;j<list.size();j++){
			InspectPlanRes pr=new InspectPlanRes();
			Map<String,Object> jsonMap=new HashMap<String,Object>();
			pr=(InspectPlanRes) list.get(j);
			String resUrl="/partner/res/PnrResConfig.do?method=detial&gisType=gisType&seldelcar="+pr.getResCfgId();
			jsonMap.put("resUrl", resUrl);
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
			String userConfigUrl="/partner/arcGis/arcGis.do?method=getDistanceForUser&&executeObjectId="+pr.getExecuteObject();
			jsonMap.put("userConfigUrl",userConfigUrl);
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
		}
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
		
		return null;
	}
	/**
	 * 代维人员数钻取
	 * @author 廖继明
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
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
				String usersql ="select c.areaid,c.areaname,nvl(yyy.count,0) as count from taw_system_area c left join (select count(*) as count,xxx.area from " +
						"(SELECT SUBSTR(A.AREA_ID,1,4) AS AREA , A.NAME FROM  PNR_USER A , PARTNER_DWINFO B "+
						" where b.professional like'%"+special+"%' AND A.USER_ID=B.WORKERID AND  B.ISDELETE='0' AND A.DELETED='0')xxx group by xxx.area)yyy on c.areaid=yyy.area where length(c.areaid)=4";
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
					"(select count(*) as count,area from pnr_carinfo  where area like '"+city+"%' and area!='"+city+"' and  deleted='0' group by area )b on" +
					" a.areaid = b.area where a.areaid like '"+city+"%' and a.areaid !='"+city+"'";
			list= jdbcService.queryForList(carsql);
			request.setAttribute("list", list);
			request.setAttribute("type", "countrycal");
		}else if("oil".equals(type)){
			String city = StaticMethod.nullObject2String(request.getParameter("iscity"));
			String oilsql ="select a.areaid,a.areaname,nvl(b.count,0) as count from taw_system_area a left join " +
					" (select count(*) as count,area from pnr_oilengineinfo  where area like '"+city+"%' and area!='"+city+"' and deleted='0' group by area )b on" +
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
		//获取改变后的页面数量
		String pagesize = StaticMethod.nullObject2String(request.getParameter("pagesize"));
		CarService carService=(CarService)getBean("carService");
		ID2NameService service = (ID2NameService) ApplicationContextHolder.getInstance().getBean("ID2NameGetServiceCatch");
		int firstResult=CommonUtils.getFirstResultOfDisplayTag(request, "carList");
		Integer pageSize = UtilMgrLocator.getEOMSAttributes().getPageSize();
		//拼接where 条件
		String whereStr = " where 1=1 and deleted<>'1' and visible<>'1' ";
		String companyId=StaticMethod.null2String(request.getParameter("company_id"));
		String companyName=StaticMethod.null2String(request.getParameter("company_name"));
		if(!"".equals(companyId)){
		String companyDeptId=ResourceInfoUtils.deptUUidToDeptId(companyId);
		request.setAttribute("companyDeptId", companyId);
		request.setAttribute("companyName", companyName);
		}
		Date curDate = new Date();
    	SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		String beginTime = sdf.format(curDate);
		whereStr += "and car.carNumber in (select distinct (h.carNum) from Trajectory h " +
	        " where trunc(h.insertTime) = to_date('"+beginTime+"', 'yyyy-mm-dd')) " ;
		Map map = null;
		try{
			if(null != pagesize && "" != pagesize){
				map = carService.getCars(firstResult, Integer.valueOf(pagesize), whereStr);
			}else{
				map = carService.getCars(firstResult, pageSize, whereStr);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		
		}
		List<Car> listc = (List<Car>) map.get("result");
		request.setAttribute("carList", listc);
		request.setAttribute("resultSize", map.get("total"));
		
		if(null != pagesize && ""!=pagesize){
			request.setAttribute("pageSize", pagesize);
		}else{
			request.setAttribute("pageSize", pageSize);
		}
	
		HashMap<String, String> jsonMap;
		List<Map<String, String>> jsonList = new ArrayList<Map<String, String>>();
		for(int i=0;i<listc.size();i++){
			jsonMap=new HashMap<String,String>();
			Car car=(Car) listc.get(i);
			String carUrl="/partner/resourceInfo/car.do?method=detail&&id="+car.getId();
			jsonMap.put("carUrl",carUrl);
			jsonMap.put("carNumber",car.getCarNumber());
			jsonMap.put("carType",car.getCarType());
			jsonMap.put("company",service.id2Name(car.getMaintainCompany(), "tawSystemDeptDao"));
			jsonMap.put("carStatus",service.id2Name(car.getCarStatus(), "tawSystemDictTypeDao"));
			jsonMap.put("area",service.id2Name(car.getArea(), "tawSystemAreaDao"));
			jsonMap.put("driver",service.id2Name(car.getDriver(), "partnerUserDao"));
			jsonMap.put("driverContact",car.getDriverContact());
			String carDipacherUrl="/partner/resourceInfo/car.do?method=checkCarCurrentTask&&carNumber="+car.getCarNumber()+"&&applyId="+car.getApplyId();
			jsonMap.put("carDipacherUrl",carDipacherUrl);
			jsonMap.put("dispatchStatus",car.getDispatchStatus());
			String dispatch="未知";
			if("1".equals(car.getDispatchStatus())){
				dispatch="空闲";
			}else if("2".equals(car.getDispatchStatus())){
				dispatch="已预订";
			}else if("3".equals(car.getDispatchStatus())){
				dispatch="使用中";
			}
			String carDispatchUrl="/partner/taskManager/carApprove.do?method=toAddCarApplyFrom&&carNumber="+car.getCarNumber();
			jsonMap.put("carDispatchUrl",carDispatchUrl);
			jsonMap.put("dispatch",dispatch);
			jsonMap.put("longitude",car.getLongitude());
			jsonMap.put("latitude",car.getLatitude());
			if(!"".equals((StaticMethod.null2String(car.getLongitude())))&&!"".equals((StaticMethod.null2String(car.getLatitude())))){
				jsonList.add(jsonMap);
			}
		}
		Gson gson=new Gson();
		String jsonString=gson.toJson(jsonList);
		request.setAttribute("jsonString", jsonString);
		//System.out.println(jsonString);
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
			jsonMap=new HashMap<String,String>();
			String carUrl="/partner/resourceInfo/car.do?method=detail&&id="+car.getId();
			jsonMap.put("carUrl",carUrl);
			jsonMap.put("carNumber",car.getCarNumber());
			jsonMap.put("carType",car.getCarType());
			jsonMap.put("company",service.id2Name(car.getMaintainCompany(), "tawSystemDeptDao"));
			jsonMap.put("carStatus",service.id2Name(car.getCarStatus(), "tawSystemDictTypeDao"));
			jsonMap.put("area",service.id2Name(car.getArea(), "tawSystemAreaDao"));
			jsonMap.put("driver",service.id2Name(car.getDriver(), "partnerUserDao"));
			jsonMap.put("driverContact",car.getDriverContact());
			String carDipacherUrl="/partner/resourceInfo/car.do?method=checkCarCurrentTask&&carNumber="+car.getCarNumber()+"&&applyId="+car.getApplyId();
			jsonMap.put("carDipacherUrl",carDipacherUrl);
			jsonMap.put("dispatchStatus",car.getDispatchStatus());
			String dispatch="未知";
			if("1".equals(car.getDispatchStatus())){
				dispatch="空闲";
			}else if("2".equals(car.getDispatchStatus())){
				dispatch="已预订";
			}else if("3".equals(car.getDispatchStatus())){
				dispatch="使用中";
			}
			String carDispatchUrl="/partner/taskManager/carApprove.do?method=toAddCarApplyFrom&&carNumber="+car.getCarNumber();
			jsonMap.put("carDispatchUrl",carDispatchUrl);
			jsonMap.put("dispatch",dispatch);
			jsonMap.put("longitude",car.getLongitude());
			jsonMap.put("latitude",car.getLatitude());
//			if(!"".equals((StaticMethod.null2String(car.getLongitude())))&&!"".equals((StaticMethod.null2String(car.getLatitude())))){
			jsonList.add(jsonMap);
//			}
				gisTrack_buffer.append(gson.toJson(jsonList));
				response.getWriter().write(gisTrack_buffer.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		return null;
	}
	
	
    /**
     * 查看车辆今日轨迹
     * add by chenruoke
     */
    public ActionForward getCarTrajectory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
		CommonSpringJdbcServiceImpl jdbcService = (CommonSpringJdbcServiceImpl) ApplicationContextHolder
														.getInstance().getBean("commonSpringJdbcService");
    	List<Trajectory> trajectorylist = new ArrayList<Trajectory>();
    	Gson gson = new Gson();
    	List<Map<String, String>> jsonList = new ArrayList<Map<String, String>>();
		StringBuffer gisTrack_buffer = new StringBuffer();
    	SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		Date curDate = new Date();
		String carnum = StaticMethod.nullObject2String((request.getParameter("carnum")));
		if(carnum!=null){
			carnum=new String(carnum.getBytes("ISO8859_1"),"UTF-8");
		}
		String beginTime = sdf.format(curDate)+" 00:00:00";
    	//获取轨迹信息
		String sql = "select trajectory.id, trajectory.inserttime,trajectory.x,trajectory.y from GIS_TRAJECTORY trajectory " +
				 " where trajectory.carnum ='"+carnum+"' "+
				 " and trajectory.inserttime >= to_date('"+beginTime+"','yyyy-mm-dd hh24:mi:ss') " +
				 " order by trajectory.inserttime asc ";
		try{
			List<Object> list = jdbcService.queryForList(sql);
			if(list.size()>0){
				for(int i=0;i<list.size();i++){
					HashMap<String, String> jsonMap = new HashMap<String,String>();
					response.setCharacterEncoding("utf-8");
					Trajectory trajectory = new Trajectory();
					ListOrderedMap listo =  (ListOrderedMap) list.get(i);
					trajectory.setId((String)listo.get("id"));
					trajectory.setX((String) listo.get("x"));
					trajectory.setY((String) listo.get("y"));
					trajectory.setInsertTime((Date) listo.get("insertTime"));
					trajectorylist.add(trajectory);
					jsonMap.put("sendTime",listo.get("insertTime").toString());
					jsonMap.put("longitude",(String) listo.get("x")); //经度
					jsonMap.put("latitude",(String) listo.get("y"));  //纬度
						if(!"".equals((StaticMethod.null2String((String) listo.get("x"))))&&!"".equals((StaticMethod.null2String((String) listo.get("y"))))){
							jsonList.add(jsonMap);
						}
					}
				}
			String jsonString=gson.toJson(jsonList);
			request.setAttribute("size", trajectorylist.size());
			request.setAttribute("jsonString", jsonString);
			request.setAttribute("trajectorylist", trajectorylist);
			request.setAttribute("resultSize", trajectorylist.size());
			request.setAttribute("pageSize",2000);
		}catch(Exception e){
		  	e.printStackTrace();
		}
    	return  mapping.findForward("carTrajectoryList");
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
		//获取改变后的页面数量
		String pagesize = StaticMethod.nullObject2String(request.getParameter("pagesize"));
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
		String whereStr = "  and approveStatus=1 ";
		PartnerUser user = partnerUserMgr.getPartnerUserByUserId(sessionForm.getUserid());
		ID2NameService service = (ID2NameService) ApplicationContextHolder.getInstance().getBean("ID2NameGetServiceCatch");
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
			whereStr += " and mobilePhone like '%"+request.getParameter("phoneSearch")+"%'";
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
		//Map map = (Map) partnerUserMgr.getPartnerUsers(pageIndex, pageSize, t);
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		Date curDate = new Date();
		String today = sdf.format(curDate);
		Map map = null;
		try{
			if(null != pagesize && "" != pagesize){
				map = (Map) partnerUserMgr.getPartnerUsersForGis(pageIndex, Integer.valueOf(pagesize), whereStr, today);
			}else{
				map = (Map) partnerUserMgr.getPartnerUsersForGis(pageIndex, pageSize, whereStr, today);
			}
			
		}catch (Exception e){
			e.printStackTrace();
		}
		List<PartnerUser> list = (List<PartnerUser>) map.get("result");
		request.setAttribute(PartnerUserConstants.PARTNERUSER_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		if(null != pagesize && "" != pagesize){
			request.setAttribute("pageSize", pagesize);
		}else{
			request.setAttribute("pageSize", pageSize);
		}
		
		
		Gson gson = new Gson();
		HashMap<String, String> jsonMap;
		List<Map<String, String>> jsonList = new ArrayList<Map<String, String>>();
		try {
			for(int i=0;i<list.size();i++){
				PartnerUser partnerUser=(PartnerUser) list.get(i);
				jsonMap=new HashMap<String,String>();
				String userUrl="/partner/baseinfo/resumes.do?method=newExpert&&searchInto=Y&&id="+partnerUser.getId();
				jsonMap.put("userUrl",userUrl);
				jsonMap.put("userName",StaticMethod.null2String(partnerUser.getName()));
				jsonMap.put("userNo",StaticMethod.null2String(partnerUser.getUserNo()));
				jsonMap.put("sex",StaticMethod.null2String(service.id2Name(partnerUser.getSex(), "tawSystemDictTypeDao")));
				jsonMap.put("area",StaticMethod.null2String(service.id2Name(partnerUser.getAreaId(), "tawSystemAreaDao")));
				jsonMap.put("licenseNo",StaticMethod.null2String(partnerUser.getLicenseNo()));
				jsonMap.put("dept",StaticMethod.null2String(service.id2Name(partnerUser.getDeptId(), "tawSystemDeptDao")));
				jsonMap.put("phone",StaticMethod.null2String(partnerUser.getPhone()));
				jsonMap.put("mobilePhone",StaticMethod.null2String(partnerUser.getMobilePhone()));
				jsonMap.put("email",StaticMethod.null2String(partnerUser.getEmail()));
				jsonMap.put("longitude",partnerUser.getLongtitude());
				jsonMap.put("latitude",partnerUser.getLatitude());
				if(!"".equals((StaticMethod.null2String(partnerUser.getLongtitude())))&&!"".equals((StaticMethod.null2String(partnerUser.getLatitude())))){
					jsonList.add(jsonMap);
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
			String jsonString=gson.toJson(jsonList);
			request.setAttribute("jsonString", jsonString);
			//System.out.println(jsonString);
			return mapping.findForward("userList");
	}
	
	/**
	 * 人员查看页面
	 * @author pointatyou
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
			Gson gson = new Gson();
			StringBuffer gisTrack_buffer = new StringBuffer();
			List<Map<String, String>> jsonList = new ArrayList<Map<String, String>>();
			response.setCharacterEncoding("utf-8");
			HashMap<String, String> jsonMap;
			jsonMap=new HashMap<String,String>();
			String userUrl="/partner/baseinfo/resumes.do?method=newExpert&&searchInto=Y&&id="+partnerUser.getId();
			jsonMap.put("userUrl",userUrl);
			jsonMap.put("userName",StaticMethod.null2String(partnerUser.getName()));
			jsonMap.put("userNo",StaticMethod.null2String(partnerUser.getUserNo()));
			jsonMap.put("sex",StaticMethod.null2String(service.id2Name(partnerUser.getSex(), "tawSystemDictTypeDao")));
			jsonMap.put("area",StaticMethod.null2String(service.id2Name(partnerUser.getAreaId(), "tawSystemAreaDao")));
			jsonMap.put("licenseNo",StaticMethod.null2String(partnerUser.getLicenseNo()));
			jsonMap.put("dept",StaticMethod.null2String(service.id2Name(partnerUser.getDeptId(), "tawSystemDeptDao")));
			jsonMap.put("phone",StaticMethod.null2String(partnerUser.getPhone()));
			jsonMap.put("mobilePhone",StaticMethod.null2String(partnerUser.getMobilePhone()));
			jsonMap.put("email",StaticMethod.null2String(partnerUser.getEmail()));
			jsonMap.put("longitude",partnerUser.getLongtitude());
			jsonMap.put("latitude",partnerUser.getLatitude());
	//		if(!"".equals((StaticMethod.null2String(partnerUser.getLongtitude())))&&!"".equals((StaticMethod.null2String(partnerUser.getLatitude())))){
				jsonList.add(jsonMap);
	//		}
				gisTrack_buffer.append(gson.toJson(jsonList));
				response.getWriter().write(gisTrack_buffer.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		return null;
	}
    
    
    /**
     * 查看人员今日轨迹
     * add by chenruoke
     */
    public ActionForward getUserTrajectory(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
    	
		CommonSpringJdbcServiceImpl jdbcService = (CommonSpringJdbcServiceImpl) ApplicationContextHolder
														.getInstance().getBean("commonSpringJdbcService");
    	List<Trajectory> trajectorylist = new ArrayList<Trajectory>();
    	Gson gson = new Gson();
    	List<Map<String, String>> jsonList = new ArrayList<Map<String, String>>();
		StringBuffer gisTrack_buffer = new StringBuffer();
    	SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		Date curDate = new Date();
		String phoneNum = StaticMethod.nullObject2String((request.getParameter("userPhone")));
		//String beginTime = "2014-12-17 00:00:00";
		//String phoneNumt = "15567563184";
		String beginTime = sdf.format(curDate)+" 00:00:00";
    	//获取轨迹信息
		String sql = "select trajectory.id, trajectory.inserttime,trajectory.x,trajectory.y from GIS_TRAJECTORY trajectory " +
				 " where trajectory.phonenum ='"+phoneNum+"' "+
				 " and trajectory.inserttime >= to_date('"+beginTime+"','yyyy-mm-dd hh24:mi:ss') " +
				 " order by trajectory.inserttime asc ";
		try{
			List<Object> list = jdbcService.queryForList(sql);
			if(list.size()>0){
				for(int i=0;i<list.size();i++){
					HashMap<String, String> jsonMap = new HashMap<String,String>();
					response.setCharacterEncoding("utf-8");
					Trajectory trajectory = new Trajectory();
					ListOrderedMap listo =  (ListOrderedMap) list.get(i);
					trajectory.setId((String)listo.get("id"));
					trajectory.setX((String) listo.get("x"));
					trajectory.setY((String) listo.get("y"));
					trajectory.setInsertTime((Date) listo.get("insertTime"));
					trajectorylist.add(trajectory);
					jsonMap.put("sendTime",listo.get("insertTime").toString());
					jsonMap.put("longitude",(String) listo.get("x")); //经度
					jsonMap.put("latitude",(String) listo.get("y"));  //纬度
						if(!"".equals((StaticMethod.null2String((String) listo.get("x"))))&&!"".equals((StaticMethod.null2String((String) listo.get("y"))))){
							jsonList.add(jsonMap);
						}
					}
				}
			String jsonString=gson.toJson(jsonList);
			request.setAttribute("size", trajectorylist.size());
			System.out.println(trajectorylist.size());
			request.setAttribute("jsonString", jsonString);
			request.setAttribute("trajectorylist", trajectorylist);
			request.setAttribute("resultSize", trajectorylist.size());
			request.setAttribute("pageSize",2000);
		}catch(Exception e){
		  	e.printStackTrace();
		}
    	return  mapping.findForward("userTrajectoryList");
    }
    
    
    /**
     * 查看人员轨迹点详情
     */
    public ActionForward userlocusdetail (ActionMapping mapping, ActionForm form,
							HttpServletRequest request, HttpServletResponse response) throws Exception {
    	CommonSpringJdbcServiceImpl jdbcService = (CommonSpringJdbcServiceImpl) ApplicationContextHolder
															.getInstance().getBean("commonSpringJdbcService");
    	//获取轨迹点ID
    	String id = StaticMethod.nullObject2String(request.getParameter("planResId"));
    	//根据轨迹ID获取对象
    	String sql = "select g.inserttime,g.x,g.y  from GIS_TRAJECTORY g where g.id = '"+id+"'";
    	List<Object> list = jdbcService.queryForList(sql);
    	ListOrderedMap listo =  (ListOrderedMap) list.get(0);
    	StringBuffer gisTrack_buffer = new StringBuffer();
		//对当前页面进行json封装
    	try {
	    	response.setCharacterEncoding("utf-8");
			List<Map<String, String>> jsonList = new ArrayList<Map<String, String>>();
			HashMap<String, String> jsonMap=new HashMap<String,String>();
			jsonMap.put("sendTime", listo.get("insertTime").toString());
			jsonMap.put("longitude",(String) listo.get("x"));
			jsonMap.put("latitude",(String) listo.get("y"));
			if(!"".equals((StaticMethod.nullObject2String((String) listo.get("x"))))&&!"".equals((StaticMethod.nullObject2String((String) listo.get("y"))))){
				jsonList.add(jsonMap);
			}
			Gson gson = new Gson();
//			String jsonString=gson.toJson(jsonList);
//			System.out.println("jsonString==="+jsonString);
//			request.setAttribute("jsonString", jsonString);
			gisTrack_buffer.append(gson.toJson(jsonList));
			response.getWriter().write(gisTrack_buffer.toString());
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return null;
    }
    
    
    
    
    
    
    
    /**
     * 跳转到gis工单页面
     */
    
    public ActionForward gotoGisOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception  {
    	request.setAttribute("orderId", request.getParameter("orderId").toString());
    	return mapping.findForward("gotoGisOrder");
    }
    
    /**
     * Gis工单
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    
	public ActionForward gisOrder(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CommonSpringJdbcServiceImpl jdbcService = (CommonSpringJdbcServiceImpl) ApplicationContextHolder
														.getInstance().getBean("commonSpringJdbcService");
		List<GisOrder> gisOrderlist = new ArrayList<GisOrder>();
		Gson gson = new Gson();
    	List<Map<String, String>> jsonList = new ArrayList<Map<String, String>>();
    	
		String orderId = request.getParameter("orderId");
		/*String tempPath = System.getProperty("user.dir");
		String projectPath = "";
		String filename = "";
		if(null!=tempPath && !"".equals(tempPath) ){
			projectPath = tempPath.substring(0,tempPath.length()-3);
		}*/
		if(null == orderId && "".equals(orderId)){
			return null;
		}else{
			try{
				//String sql = "select ID,X,Y,USERNAME,PHONE,IMAGE_PATH,IMAGE_FILE,TYPE from TEST_GIS_ORDER  where 1=1 and ORDERID = '"+orderId+"' ";
				
				String sql ="select p.longitude,p.latitude, p.path,'255000000' type "+
						" from pnr_android_photo p "+
						" left join pnr_act_precheck_photo_ship pk on p.id = pk.photo_id	where pk.processinstance_id ='"+orderId+"' "+
						"	union  all "+
						" select to_char(w.longitude),to_char(w.latitude),w.path ,decode(w.id_type,'transferSpotcheck','000000255','255255000') from pnr_android_workorder_photo  w where w.picture_id='"+orderId+"' ";
				System.out.println("transient-ArcGisAction-gisOrder:sql:"+sql);
				List<Object> list = jdbcService.queryForList(sql);
				if(list.size()>0){
					for(int i=0;i<list.size();i++){
						HashMap<String, String> jsonMap = new HashMap<String,String>();
						GisOrder gisOrderModel = new GisOrder();
						ListOrderedMap listo =  (ListOrderedMap) list.get(i);
						gisOrderModel.setX(listo.get("longitude").toString());
						gisOrderModel.setY(listo.get("latitude").toString());					
						
						gisOrderModel.setIMAGE_FILE_NAME(listo.get("path")+"");						
						gisOrderlist.add(gisOrderModel);

						jsonMap.put("url",listo.get("path")+""); //
						
						jsonMap.put("longitude",(String) listo.get("longitude")); //经度
						jsonMap.put("latitude",(String) listo.get("latitude"));  //纬度
						
						jsonMap.put("type",(String) listo.get("type")); //判断是事前事中还是事后的值
						if(!"".equals((StaticMethod.null2String((String) listo.get("longitude"))))&&!"".equals((StaticMethod.null2String((String) listo.get("latitude"))))){
							jsonList.add(jsonMap);
						}
					}
					String jsonString=gson.toJson(jsonList);
					request.setAttribute("size", gisOrderlist.size());
					request.setAttribute("jsonString", jsonString);
					request.setAttribute("gisOrderlist", gisOrderlist);
					request.setAttribute("resultSize",gisOrderlist.size());
					request.setAttribute("pageSize",2000);
					
				}else{
					request.setAttribute("jsonString", gson.toJson(jsonList));
					request.setAttribute("size", "0");
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}
		return mapping.findForward("gisOrderList");
	}
    
	
	//点击图片非全屏显示
	public ActionForward showGisImage (ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception  { 
		String imagename= request.getParameter("imagename");
		String tempPath = System.getProperty("user.dir");
		String projectPath = "";
		if(null!=tempPath && !"".equals(tempPath) ){
			projectPath = tempPath.substring(0,tempPath.length()-3);
		}
	    String patch = projectPath+"webapps\\partner\\arcGis\\tempImage\\"+imagename;
		FileInputStream is = new FileInputStream(patch);  
		int i = is.available(); // 得到文件大小  
		byte data[] = new byte[i];  
		is.read(data); // 读数据  
		is.close();  
		response.setContentType("image/*"); // 设置返回的文件类型  
		OutputStream toClient = response.getOutputStream(); // 得到向客户端输出二进制数据的对象  
		toClient.write(data); // 输出数据  
		toClient.close();  
		            
		return null;
	}  
	
	
	/**
	 * 点击某个GIS工单点查看详情
	 */
	public ActionForward gisOrderDetail (ActionMapping mapping, ActionForm form,
						HttpServletRequest request, HttpServletResponse response) throws Exception  { 
		String filename= request.getParameter("filename");
		String username= request.getParameter("username");
		String ResLevelId= request.getParameter("ResLevelId");
		String x= request.getParameter("x");
		String y= request.getParameter("y");
		StringBuffer gisTrack_buffer = new StringBuffer();
		List<Map<String, String>> jsonList = new ArrayList<Map<String, String>>();
		Gson gson = new Gson();
		HashMap<String, String> jsonMap = new HashMap<String,String>();
		try{
			if(null != filename){
				jsonMap.put("username", username);
				jsonMap.put("longitude",x); //经度
				jsonMap.put("latitude",y);  //纬度
				jsonMap.put("filename", filename);
				jsonMap.put("ResLevelId", ResLevelId);
				jsonMap.put("path", filename);

				if(!"".equals((StaticMethod.null2String(x)))&&!"".equals((StaticMethod.null2String(y)))){
					jsonList.add(jsonMap);
				}
			}
			gisTrack_buffer.append(gson.toJson(jsonList));
			response.getWriter().write(gisTrack_buffer.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		
			return null;
	}
	
	
	
	  /**
	   * 查看以二进制流存在数据库中的图片
	   * @param mapping
	   * @param form
	   * @param request
	   * @param response
	   * @return
	   * @throws Exception
	   */
	    public ActionForward showPicture(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	    														HttpServletResponse response)  throws Exception  {
	      CommonSpringJdbcServiceImpl jdbcService = (CommonSpringJdbcServiceImpl) ApplicationContextHolder
																	.getInstance().getBean("commonSpringJdbcService");
	      String Id = request.getParameter("Id");
	      if(null == Id && "".equals(Id)){
				return null;
	      }
	      String sql = "select IMAGE_FILE from TEST_GIS_ORDER  where 1=1 and ID = '"+Id+"' ";
	      List<Object> list = jdbcService.queryForList(sql);
	      ListOrderedMap listo =  (ListOrderedMap) list.get(0);
	      byte[] photoimg = null;
	      try{
				// 对base64数据进行解码 生成 字节数组，不能直接用Base64.decode（）；进行解密
				photoimg = new BASE64Decoder().decodeBuffer(listo.get("IMAGE_FILE").toString());
				for (int i = 0; i < photoimg.length; ++i) {
					if (photoimg[i] < 0) {
						// 调整异常数据
						photoimg[i] += 256;
					}
				}
			
	      }catch (Exception e){
	    	  e.printStackTrace();
	      }
	   
	      if (photoimg != null) {
	        File tempFile = File.createTempFile("tmp", ".png");
//	        System.out.println("--tempFilePath-" + tempFile.getPath());
	        try {
	          response.setHeader("Pragma", "no-cache");
	          response.setHeader("Cache-Control", "no-cache");
	          response.setDateHeader("Expires", 0L);
	          response.setContentType("image/jpeg");

	          FileOutputStream fos = new FileOutputStream(tempFile);
	          fos.write(photoimg);
	          fos.flush();
	          fos.close();
	          HashMap<String, String> jsonMap = new HashMap<String,String>();
	          jsonMap.put("imagepatch",tempFile.toString());
	          Gson gson = new Gson();
	          String jsonString=gson.toJson(jsonMap);
	          request.setAttribute("jsonString", jsonString);
	          
	          FileInputStream fis = new FileInputStream(tempFile);
	          BASE64Decoder base64 = new BASE64Decoder();
	          base64.decodeBuffer(fis, response.getOutputStream());
	        }
	        finally
	        {
	          tempFile.deleteOnExit();
	        }
	      }

	      return null;
	    } 
    
   
    
    
    
    /**
	 * 油机列表
	 * @author pointatyou
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward oilSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OilEngineService oilEngineService = (OilEngineService) getBean("oilEngineService");
		Search search = new Search();
		int firstResult = CommonUtils.getFirstResultOfDisplayTag(request,"oilEngineList");
		ID2NameService service = (ID2NameService) ApplicationContextHolder.getInstance().getBean("ID2NameGetServiceCatch");
		String id = StaticMethod.nullObject2String((request.getParameter("id")));
		Integer pageSize = UtilMgrLocator.getEOMSAttributes().getPageSize();
		// 获取导出的状态如果不为空.说明点击了导出按钮
//		String exportValue = request
//				.getParameter(org.displaytag.tags.TableTagParameters.PARAMETER_EXPORTING);
//		if (null != exportValue && !"".equals(exportValue)) {
//			pageSize = new Integer(-1);
//		}
		search.setFirstResult(firstResult * pageSize);
		search.setMaxResults(pageSize);
		search.addFilterEqual("deleted", "0");
		// 获取区域id作为删选条件
		TawSystemSessionForm sessionForm = this.getUser(request);
//		String deptid = sessionForm.getDeptid();
		search.addFilterNotEqual("visible", "1");// 1表示不可见，正在执行流程申请中
		search = CommonUtils.getSqlFromRequestMap(request, search);
		String companyId=StaticMethod.null2String(request.getParameter("company_id"));
		String companyName=StaticMethod.null2String(request.getParameter("company_name"));
		String areaName=StaticMethod.null2String(request.getParameter("area_name"));
		String companyDeptId=ResourceInfoUtils.deptUUidToDeptId(companyId);
		search.addFilterILike("maintainCompany", companyDeptId+"%");
		request.setAttribute("companyName", companyName);
		request.setAttribute("areaName", areaName);
		request.setAttribute("companyId", companyId);
		SearchResult<OilEngine> searchResult = oilEngineService.searchAndCount(search);
		List<OilEngine> list = searchResult.getResult();
		
		Gson gson = new Gson();
		HashMap<String, String> jsonMap;
		List<Map<String, String>> jsonList = new ArrayList<Map<String, String>>();
		for(int i=0;i<list.size();i++){
			OilEngine oil=new OilEngine();
			oil=list.get(i);
			jsonMap=new HashMap<String,String>();
			String oilUrl="/partner/resourceInfo/oilEngine.do?method=detail&&id="+oil.getId();
			jsonMap.put("oilUrl",oilUrl);
			String oilEditUrl="/partner/arcGis/arcGis.do?method=oilEdit&&id="+oil.getId();
			jsonMap.put("oilEditUrl", oilEditUrl);
			jsonMap.put("oilEngineNumber",StaticMethod.nullObject2String(oil.getOilEngineNumber()));
			jsonMap.put("area",StaticMethod.null2String(service.id2Name(oil.getArea(), "tawSystemAreaDao")));
			jsonMap.put("maintainCompany",StaticMethod.null2String(service.id2Name(oil.getMaintainCompany(), "tawSystemDeptDao")));
			jsonMap.put("oilEngineModel",StaticMethod.null2String(oil.getOilEngineModel()));
			jsonMap.put("powerRating",StaticMethod.null2String(oil.getPowerRating()));
			jsonMap.put("standardFuelConsumption",StaticMethod.null2String(oil.getStandardFuelConsumption()));
			jsonMap.put("fuleType",StaticMethod.null2String(service.id2Name(oil.getFuleType(), "tawSystemDictTypeDao")));
			jsonMap.put("oilEngineType",StaticMethod.null2String(service.id2Name(oil.getOilEngineType(), "tawSystemDictTypeDao")));
			jsonMap.put("oilEngineProperty",StaticMethod.null2String(service.id2Name(oil.getOilEngineProperty() ,"tawSystemDictTypeDao")));
			jsonMap.put("oilEngineStatus",StaticMethod.null2String(service.id2Name(oil.getOilEngineStatus() ,"tawSystemDictTypeDao")));
			jsonMap.put("longitude",oil.getLongitude());
			jsonMap.put("latitude",oil.getLatitude());
			if(!"".equals((StaticMethod.null2String(oil.getLongitude())))&&!"".equals((StaticMethod.null2String(oil.getLatitude())))){
				jsonList.add(jsonMap);
			}
			
		}
		String jsonString=gson.toJson(jsonList);
		
		request.setAttribute("jsonString", jsonString);
		int resultSize = searchResult.getTotalCount();
		request.setAttribute("oilEngineList", list);
		request.setAttribute("total", resultSize);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("userid", sessionForm.getUserid());
		
	
		return mapping.findForward("oilList");
	}
	
	
	/**
	 * 油机详情
	 * @author pointatyou
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward oilDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = StaticMethod.nullObject2String(request.getParameter("id"));
		OilEngineService oilEngineService = (OilEngineService) getBean("oilEngineService");
		ID2NameService service = (ID2NameService) ApplicationContextHolder.getInstance().getBean("ID2NameGetServiceCatch");
		OilEngine oil = oilEngineService.find(id);
		try {
			response.setCharacterEncoding("utf-8");
			StringBuffer gisTrack_buffer = new StringBuffer();
		Gson gson = new Gson();
		HashMap<String, String> jsonMap;
		List<Map<String, String>> jsonList = new ArrayList<Map<String, String>>();
			jsonMap=new HashMap<String,String>();
			String oilUrl="/partner/resourceInfo/oilEngine.do?method=detail&&id="+oil.getId();
			jsonMap.put("oilUrl",oilUrl);
			String oilEditUrl="/partner/arcGis/arcGis.do?method=oilEdit&&id="+oil.getId();
			jsonMap.put("oilEditUrl", oilEditUrl);
			jsonMap.put("oilEngineNumber",StaticMethod.nullObject2String(oil.getOilEngineNumber()));
			jsonMap.put("area",StaticMethod.null2String(service.id2Name(oil.getArea(), "tawSystemAreaDao")));
			jsonMap.put("maintainCompany",StaticMethod.null2String(service.id2Name(oil.getMaintainCompany(), "tawSystemDeptDao")));
			jsonMap.put("oilEngineModel",StaticMethod.null2String(oil.getOilEngineModel()));
			jsonMap.put("powerRating",StaticMethod.null2String(oil.getPowerRating()));
			jsonMap.put("standardFuelConsumption",StaticMethod.null2String(oil.getStandardFuelConsumption()));
			jsonMap.put("fuleType",StaticMethod.null2String(service.id2Name(oil.getFuleType(), "tawSystemDictTypeDao")));
			jsonMap.put("oilEngineType",StaticMethod.null2String(service.id2Name(oil.getOilEngineType(), "tawSystemDictTypeDao")));
			jsonMap.put("oilEngineProperty",StaticMethod.null2String(service.id2Name(oil.getOilEngineProperty() ,"tawSystemDictTypeDao")));
			jsonMap.put("oilEngineStatus",StaticMethod.null2String(service.id2Name(oil.getOilEngineStatus() ,"tawSystemDictTypeDao")));
			jsonMap.put("longitude",oil.getLongitude());
			jsonMap.put("latitude",oil.getLatitude());
			jsonList.add(jsonMap);
			gisTrack_buffer.append(gson.toJson(jsonList));
			response.getWriter().write(gisTrack_buffer.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		return null;
	}
	
	
	/**
	 * 修改油机信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward oilEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = StaticMethod.null2String(request.getParameter("id"));
//		String longtitude = StaticMethod.null2String(request.getParameter("longtitude"));
//		String latitude = StaticMethod.null2String(request.getParameter("latitude"));
		OilEngineService oilEngineService = (OilEngineService) getBean("oilEngineService");
		OilEngine oilEngine = oilEngineService.find(id);
		request.setAttribute("oilEngine", oilEngine);
//		try {
//			response.setCharacterEncoding("utf-8");
//			StringBuffer gisTrack_buffer = new StringBuffer();
//		Gson gson = new Gson();
//		HashMap<String, String> jsonMap=new HashMap();
//		jsonMap.put("type", "success");
//		gisTrack_buffer.append(gson.toJson(jsonMap));
//		response.getWriter().write(gisTrack_buffer.toString());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		return mapping.findForward("oilEdit");
	}
	
	/**
	 * 巡检线路光缆段查询
	 * @author pointatyou
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
    	/**
		 * add by lee
		 */
		if(com.boco.eoms.mobile.util.MobileCommonUtils.isAndroidRequest(request)){//如果手机访问,则直接到出pageIndex
			pageIndexString = StaticMethod.nullObject2int(request.getParameter("pageIndex"))+"";
		}
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		PnrResConfig pnrResConfigForm = new PnrResConfig();
		String resName = StaticMethod.null2String(request.getParameter("resName"));
		String region = StaticMethod.null2String(request.getParameter("city"));
		String country = StaticMethod.null2String(request.getParameter("country"));
		pnrResConfigForm.setCity(region);
		pnrResConfigForm.setCountry(country);
		pnrResConfigForm.setResName(resName);
		
		if(!StringUtils.isEmpty(pnrResConfigForm.getResName())){
			whereStr = whereStr+" and resName like '%"+pnrResConfigForm.getResName()+"%'";
		}
		if(!StringUtils.isEmpty(pnrResConfigForm.getCity())){
			whereStr = whereStr+" and city='"+pnrResConfigForm.getCity()+"'";
		}
		if(!StringUtils.isEmpty(request.getParameter("region"))){
			whereStr = whereStr+" and city='"+request.getParameter("region")+"'";
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
	 * 巡检线路光缆段中点查询
	 * @author pointatyou
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward gotoTransLinePointList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		try {
		String id;
		String forwardType = "";
		 id=StaticMethod.null2String(request.getParameter("id"));
		 if("".equals(id)){
		 id=StaticMethod.null2String(request.getParameter("seldelcar"));
		 forwardType="传输线路";
		 }
		 if("".equals(id)){
			 id=StaticMethod.nullObject2String(request.getAttribute("resCfgId"));
			 forwardType="传输线路"; 
			 
		 }
		String sql="select dd.tl_pa_lo as dd_tl_pa_lo,dd.tl_pa_la as dd_tl_pa_la,dd.tl_pz_lo as dd_tl_pz_lo,"
				+" dd.tl_pz_la as dd_tl_pz_la,dd.tl_pa_name as dd_tl_pa_name,dd.tl_pz_name as dd_tl_pz_name,point.tlp_pa_lo as point_tlp_pa_lo,point.tlp_pa_la  as point_tlp_pa_la,point.tlp_pa_name as point_tlp_pa_name,dd.res_name as dd_res_name"
				+" from pnr_trans_line_point point,pnr_res_config  dd  " 
				+" where  point.tlp_wire=dd.tl_wire and point.tlp_seg=dd.tl_seg and dd.tl_inspect_flag = 1 and point.tlp_source='0' and dd.id='"+id+"'";
		CommonSpringJdbcServiceImpl jdbcService = (CommonSpringJdbcServiceImpl) ApplicationContextHolder
				.getInstance().getBean("commonSpringJdbcService");
		ID2NameService service = (ID2NameService) ApplicationContextHolder
		.getInstance().getBean("ID2NameGetServiceCatch");
			List<ListOrderedMap> list = jdbcService.queryForList(sql);
			response.setCharacterEncoding("utf-8");
			List<Map<String, String>> jsonList = new ArrayList<Map<String, String>>();
			Gson gson = new Gson();
			HashMap<String, String> jsonMap;
			StringBuffer grid_buffer = new StringBuffer();
			if(!list.isEmpty()){
				Map<String,String> jsonMap1 = new HashMap<String, String>();
				ListOrderedMap map1 = list.get(0);
				String ddTlPaLo=StaticMethod.nullObject2String(map1.get("dd_tl_pa_lo"));
				String ddTlPaLa=StaticMethod.nullObject2String(map1.get("dd_tl_pa_la"));
				String ddTlPzLo=StaticMethod.nullObject2String(map1.get("dd_tl_pz_lo"));
				String ddTlPzLa=StaticMethod.nullObject2String(map1.get("dd_tl_pz_la"));
				String ddTlPaName=StaticMethod.nullObject2String(map1.get("dd_tl_pa_name"));
				String ddTlPzName=StaticMethod.nullObject2String(map1.get("dd_tl_pz_name"));
				String dd_res_name=StaticMethod.nullObject2String(map1.get("dd_res_name"));
				String resAType;
				String resBType;
				if(ddTlPaName.contains("接头盒")&&ddTlPaName!=""){
					resAType="接头盒";
				}
				else{
					resAType="站点";
				}
				if(ddTlPzName.contains("接头盒")&&ddTlPzName!=""){
					resBType="接头盒";
				}
				else{
					resBType="站点";
				}
				jsonMap1.put("ddTlPaLo", ddTlPaLo);
				jsonMap1.put("ddTlPaLa", ddTlPaLa);
				jsonMap1.put("ddTlPzLo", ddTlPzLo);
				jsonMap1.put("ddTlPzLa", ddTlPzLa);
				jsonMap1.put("ddTlPaName", ddTlPaName);
				jsonMap1.put("ddTlPzName", ddTlPzName);
				jsonMap1.put("dd_res_name", dd_res_name);
				jsonMap1.put("resAType", resAType);
				jsonMap1.put("resBType", resBType);
				jsonMap1.put("specialty",forwardType);
				
				if(ddTlPaLo!=""&&ddTlPaLa!=""&&ddTlPzLo!=""&&ddTlPzLa!=""){
					jsonList.add(jsonMap1);
				}
			for (int i = 0; i < list.size(); i++) {
				jsonMap = new HashMap<String, String>();
				ListOrderedMap map = list.get(i);
				String tlpPaLo=StaticMethod.nullObject2String(map.get("point_tlp_pa_lo"));
				String tlpPaLa=StaticMethod.nullObject2String(map.get("point_tlp_pa_la"));
				String point_tlp_pa_name=StaticMethod.nullObject2String(map.get("point_tlp_pa_name"));
				String type;
				if(point_tlp_pa_name.contains("标石")&&point_tlp_pa_name!=""){
					type="标石";
				}
				else if(point_tlp_pa_name.contains("井")&&point_tlp_pa_name!=""){
					type="井";
				}
				else{
					type="标石";
				}
				jsonMap.put("tlpPaLo", tlpPaLo);
				jsonMap.put("tlpPaLa", tlpPaLa);
				jsonMap.put("point_tlp_pa_name", point_tlp_pa_name);
				jsonMap.put("type", type);
				if(tlpPaLo!=""&&tlpPaLa!=""){
					jsonList.add(jsonMap);
				}
				
			}
			}
				
			grid_buffer.append(gson.toJson(jsonList));
				response.getWriter().write(grid_buffer.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		return null;
	}
	
	/**
	 * 传输线路巡检轨迹，模拟方法
	 * @author pointatyou
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
//	public ActionForward gotoTransLineLocus(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response)
//	throws Exception {
//		
//		String id=StaticMethod.null2String(request.getParameter("id"));
//		String sql="select dd.tl_pa_lo as dd_tl_pa_lo,dd.tl_pa_la as dd_tl_pa_la,dd.tl_pz_lo as dd_tl_pz_lo,"
//				+" dd.tl_pz_la as dd_tl_pz_la,dd.tl_pa_name as dd_tl_pa_name,dd.tl_pz_name as dd_tl_pz_name,point.tlp_pa_lo as point_tlp_pa_lo,point.tlp_pa_la  as point_tlp_pa_la,point.tlp_pa_name as point_tlp_pa_name,dd.res_name as dd_res_name"
//				+" from pnr_trans_line_point point,pnr_res_config  dd  " 
//				+" where  point.tlp_wire=dd.tl_wire and point.tlp_seg=dd.tl_seg and dd.tl_inspect_flag = 1 and point.tlp_source='0' and dd.id='402880ef3e12b757013e12bc6f1e0287'";
//		CommonSpringJdbcServiceImpl jdbcService = (CommonSpringJdbcServiceImpl) ApplicationContextHolder
//				.getInstance().getBean("commonSpringJdbcService");
//		ID2NameService service = (ID2NameService) ApplicationContextHolder
//		.getInstance().getBean("ID2NameGetServiceCatch");
//			List<ListOrderedMap> list = jdbcService.queryForList(sql);
//			try {
//			response.setCharacterEncoding("utf-8");
//			List<Map<String, String>> jsonList = new ArrayList<Map<String, String>>();
//			Gson gson = new Gson();
//			HashMap<String, String> jsonMap;
//			StringBuffer grid_buffer = new StringBuffer();
//			if(!list.isEmpty()){
//				Map jsonMap1 = new HashMap<String, String>();
//				ListOrderedMap map1 = list.get(0);
//				String ddTlPaLo=StaticMethod.nullObject2String(map1.get("dd_tl_pa_lo"));
//				String ddTlPaLa=StaticMethod.nullObject2String(map1.get("dd_tl_pa_la"));
//				String ddTlPzLo=StaticMethod.nullObject2String(map1.get("dd_tl_pz_lo"));
//				String ddTlPzLa=StaticMethod.nullObject2String(map1.get("dd_tl_pz_la"));
//				String ddTlPaName=StaticMethod.nullObject2String(map1.get("dd_tl_pa_name"));
//				String ddTlPzName=StaticMethod.nullObject2String(map1.get("dd_tl_pz_name"));
//				String dd_res_name=StaticMethod.nullObject2String(map1.get("dd_res_name"));
//				String resAType;
//				String resBType;
//				if(ddTlPaName.contains("接头盒")&&ddTlPaName!=""){
//					resAType="接头盒";
//				}
//				else{
//					resAType="站点";
//				}
//				if(ddTlPzName.contains("接头盒")&&ddTlPzName!=""){
//					resBType="接头盒";
//				}
//				else{
//					resBType="站点";
//				}
//				jsonMap1.put("ddTlPaLo", ddTlPaLo);
//				jsonMap1.put("ddTlPaLa", ddTlPaLa);
//				jsonMap1.put("ddTlPzLo", ddTlPzLo);
//				jsonMap1.put("ddTlPzLa", ddTlPzLa);
//				jsonMap1.put("ddTlPaName", ddTlPaName);
//				jsonMap1.put("ddTlPzName", ddTlPzName);
//				jsonMap1.put("dd_res_name", dd_res_name);
//				jsonMap1.put("resAType", resAType);
//				jsonMap1.put("resBType", resBType);
//				if(ddTlPaLo!=""&&ddTlPaLa!=""&&ddTlPzLo!=""&&ddTlPzLa!=""){
//					jsonList.add(jsonMap1);
//				}
//			for (int i = 0; i < list.size(); i++) {
//				jsonMap = new HashMap<String, String>();
//				ListOrderedMap map = list.get(i);
//				String tlpPaLo=StaticMethod.nullObject2String(map.get("point_tlp_pa_lo"));
//				String tlpPaLa=StaticMethod.nullObject2String(map.get("point_tlp_pa_la"));
//				String point_tlp_pa_name=StaticMethod.nullObject2String(map.get("point_tlp_pa_name"));
//				String type;
//				if(point_tlp_pa_name.contains("标石")&&point_tlp_pa_name!=""){
//					type="标石";
//				}
//				else if(point_tlp_pa_name.contains("井")&&point_tlp_pa_name!=""){
//					type="井";
//				}
//				else{
//					type="标石";
//				}
//				jsonMap.put("tlpPaLo", tlpPaLo);
//				jsonMap.put("tlpPaLa", tlpPaLa);
//				jsonMap.put("point_tlp_pa_name", point_tlp_pa_name);
//				jsonMap.put("type", type);
//				if(tlpPaLo!=""&&tlpPaLa!=""){
//					jsonList.add(jsonMap);
//				}
//				
//			}
//			}
//				
//			grid_buffer.append(gson.toJson(jsonList));
//				response.getWriter().write(grid_buffer.toString());
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		
//		return null;
//	}
	
	/**
	 * 传输线路巡检轨迹
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public ActionForward queryLinePointBySeg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		String segId = StaticMethod.nullObject2String(request.getParameter("segId"));
		String lineId = StaticMethod.nullObject2String(request.getParameter("lineId"));
		String planId = StaticMethod.nullObject2String(request.getParameter("planId"));
		String planResId = StaticMethod.null2String(request.getParameter("planResId"));
		IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
		IInspectLineTrackMgr distanceMgr = (IInspectLineTrackMgr)getBean("lineTrackMgr");
		String whereStr =" where 1=1 ";
		if(!"".equals(planResId)){
			whereStr+="  and  id='"+planResId+"'";
		}
		whereStr+=" and res_Longitude is not null and res_Latitude is not null ";
		Map map = (Map) inspectPlanResMgr.findInspectPlanResList(0, 15, whereStr);
		List<InspectPlanRes> list = (List<InspectPlanRes>) map.get("result");
		if(!list.isEmpty()){
//		InspectPlanRes plan=(InspectPlanRes) list.get(0);
//		String resCfgId=StaticMethod.nullObject2String(plan.getResCfgId());
		Search search = new Search();
		if(!"".equals(segId)){
			search.addFilterEqual("segId", segId);
		}
		if(!"".equals(lineId)){
			search.addFilterEqual("lineId", lineId);
		}
		
		if(!"".equals(planId)){
			search.addFilterEqual("planId", planId);
		}
		
		SearchResult<InspectLineTrack> searchResult = distanceMgr.searchAndCount(search);
//		int rsultSize = searchResult.getTotalCount();
		List<InspectLineTrack> returnList = searchResult.getResult();
		response.setCharacterEncoding("utf-8");
		List<Map<String, String>> jsonList = new ArrayList<Map<String, String>>();
		Gson gson = new Gson();
		HashMap<String, String> jsonMap;
		StringBuffer grid_buffer = new StringBuffer();
			if(!returnList.isEmpty()){
				for(int i=0;i<returnList.size();i++){
					jsonMap=new HashMap<String,String>();
					InspectLineTrack islt=(InspectLineTrack)returnList.get(i);
					jsonMap.put("tlpPaLo",StaticMethod.null2String(islt.getLongitude()));
					jsonMap.put("tlpPaLa",StaticMethod.null2String(islt.getLatitude()));
					if(!"".equals(StaticMethod.null2String(islt.getLongitude()))&&!"".equals(StaticMethod.null2String(islt.getLatitude()))){
					jsonList.add(jsonMap);
					}
				}
				
			}
			
			grid_buffer.append(gson.toJson(jsonList));
			try {
			response.getWriter().write(grid_buffer.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 按距离筛选人员
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getDistanceForUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String executeObjectId=request.getParameter("executeObjectId");
		String siteLongtitude=request.getParameter("reslongtitude");
		String siteLatitude=request.getParameter("resLatitude");
		CommonSpringJdbcServiceImpl jdbcService = (CommonSpringJdbcServiceImpl) ApplicationContextHolder
				.getInstance().getBean("commonSpringJdbcService");
//		ID2NameService service = (ID2NameService) ApplicationContextHolder
//				.getInstance().getBean("ID2NameGetServiceCatch");
		String sql="select u.name,u.user_id,u.mobile_phone,u.updatetime,u.latitude,u.longtitude,u.dept_id from  pnr_dept dept , pnr_user u "+
					" where u.dept_id=dept.dept_mag_id  "+
				    " AND u.deleted = '0'"+
				    " AND dept.deleted = '0'"+
				    " and dept.id='"+executeObjectId+"'";
//		" and dept.id='8ab034363b9c8d50013b9c95e20c0013'";
//		 			" and u.user_id='yangbing2'"; 
			List<ListOrderedMap> userList = jdbcService.queryForList(sql);
			List<Map<String,Object>> returnList=new ArrayList<Map<String,Object>>();
			Map<String,Object> returnMap;
			for (int i = 0; i < userList.size(); i++) {
				ListOrderedMap userMap = userList.get(i);
				returnMap=new HashMap<String,Object>();
				String deptid=StaticMethod.nullObject2String(userMap.get("dept_id"));
				String longtitude=StaticMethod.nullObject2String(userMap.get("longtitude"));
				String latitude=StaticMethod.nullObject2String(userMap.get("latitude"));
				String updatetime=StaticMethod.nullObject2String(userMap.get("updatetime"));
				String phone=StaticMethod.nullObject2String(userMap.get("mobile_phone"));
				String userid=StaticMethod.nullObject2String(userMap.get("user_id"));
				String name=StaticMethod.nullObject2String(userMap.get("name"));
				siteLongtitude=ArcGisConstacts.formatObject(siteLongtitude,6);
				siteLatitude=ArcGisConstacts.formatObject(siteLatitude,6);
				longtitude=ArcGisConstacts.formatObject(longtitude,6);
				latitude=ArcGisConstacts.formatObject(latitude,6);
				String length;
				if(!siteLongtitude.equals("")&&!siteLatitude.endsWith("")&&!longtitude.endsWith("")&&!latitude.endsWith("")){
				int userLength=ArcGisConstacts.GetDistance(Double.valueOf(siteLongtitude),Double.valueOf(siteLatitude),Double.valueOf(longtitude),Double.valueOf(latitude));
				length=String.valueOf(userLength);
				}
				else{
					length="无坐标定位";
				}
				returnMap.put("deptid", deptid);
				returnMap.put("name", name);
				returnMap.put("userid", userid);
				returnMap.put("phone", phone);
				returnMap.put("updatetime", updatetime);
				returnMap.put("length", length);
				returnList.add(returnMap);
			}
			request.setAttribute("returnList",returnList);
			request.setAttribute("resultSize",returnList.size());
			return mapping.findForward("getDistanceForUser");
	}
	/**
	 * 获取地图打点信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward getData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String returnJson ="[{\"image\":\"https://distilleryimage11.instagram.com/231895caaf2211e19dc71231380fe523_6.jpg\"," +
				"\"caption\":\"another crappy day at work...\"," +
				"\"link\":\"https://instagr.am/p/Lfz0O-Io5_/\"," +
				"\"full_name\":\"gino beltran\"," +
				"\"lat\":\"33.540744\"," +
				"\"lng\":\"-117.782432\"" +
				"}]";
		
		String pagesize = StaticMethod.nullObject2String(request.getParameter("pagesize"));

		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList) getBean("pnrBaseAreaIdList");
		ID2NameService service = (ID2NameService) ApplicationContextHolder
									.getInstance().getBean("ID2NameGetServiceCatch");
		String province = pnrBaseAreaIdList.getRootAreaId();
		String whereStr = " where specialty<>'1122502' ";
		List city = PartnerCityByUser.getCityByProvince(province);
		request.setAttribute("city", city);
		request.setAttribute("city1", city);
		PnrResConfigMgr pnrResConfiMgr = (PnrResConfigMgr) getBean("PnrResConfigMgr");
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "list");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		PnrResConfigForm pnrResConfigForm = new PnrResConfigForm();
		try {
			BeanUtils.populate(pnrResConfigForm, request.getParameterMap());
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		//控制地市权限
	    TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
    	ITawSystemDeptManager deptMgr = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
		TawSystemDept dept = deptMgr.getTawSystemDept(sessionForm.getDeptpriid());
		String areaId = dept.getAreaid();
		if(areaId!=null&&areaId.length()>4){
			areaId = areaId.substring(0,4);			
		}
		whereStr = whereStr+" and city like '"+areaId+"%"+"'";
		//备用
//		select * from taw_system_area aa where aa.areaid =(
//				select a.parentareaid from taw_system_area a 
//				where a.areaid =
//				(select areaid from taw_system_dept d 
//				where d.deptid = (select u.deptid from taw_system_user u where u.userid ='miaofu1'))
//				)
		//控制地市权限 -end
		Map map = null;
		if(null != pagesize && ""!=pagesize){
			map = pnrResConfiMgr.getResources(firstResult
					* Integer.parseInt(pagesize) , Integer.parseInt(pagesize),
					whereStr);
		}else{
			map = pnrResConfiMgr.getResources(firstResult
					* CommonConstants.PAGE_SIZE, CommonConstants.PAGE_SIZE,
					whereStr);
		}

		
		List<PnrResConfig> list = new ArrayList<PnrResConfig>();
		list = (List<PnrResConfig>) map.get("result");
		request.setAttribute("list", list);
		//对当前页面进行json封装
		List<Map<String,String> > jsonList=new ArrayList<Map<String,String> >();
		for(int j=0;j<list.size();j++){
			PnrResConfig pr=new PnrResConfig();
			Map<String,String> jsonMap=new HashMap<String,String> ();
			pr=(PnrResConfig) list.get(j);
			String resUrl="/partner/partner/res/PnrResConfig.do?method=detial&gisType=gisType&seldelcar="+pr.getId();
			jsonMap.put("resUrl", resUrl);
			jsonMap.put("resName", pr.getResName());
			jsonMap.put("specialty", service.id2Name(pr.getSpecialty(), "ItawSystemDictTypeDao"));
			//jsonMap.put("resType", service.id2Name(pr.getResType(), "tawSystemDictTypeDao")+"-"+service.id2Name(pr.getResLevel(), "tawSystemDictTypeDao"));
			jsonMap.put("resType", service.id2Name(pr.getResType(), "ItawSystemDictTypeDao"));
			jsonMap.put("resLevel",service.id2Name(pr.getResLevel(), "ItawSystemDictTypeDao"));

			jsonMap.put("city", service.id2Name(pr.getCity(), "tawSystemAreaDao"));
			jsonMap.put("country", service.id2Name(pr.getCountry(), "tawSystemAreaDao"));
			
			jsonMap.put("resLongitude",pr.getResLongitude());
			jsonMap.put("resLatitude",pr.getResLatitude());
			//去除脏数据
			if("1122502".equals(pr.getSpecialty())&&!"0".equals(pr.getResLatitude())&&!"0".equals(pr.getResLongitude())&&!"0".equals(pr.getEndLongitude())&&!"0".equals(pr.getEndLatitude())){
				jsonList.add(jsonMap);
			}
			else if(!"1122502".equals(pr.getSpecialty())&&!"0".equals(pr.getResLatitude())&&!"0".equals(pr.getResLongitude())){
				jsonList.add(jsonMap);
			}
		}
		Gson gson=new Gson();
		String dataJson=gson.toJson(jsonList);
		
		PrintWriter out = response.getWriter();
		out.print(dataJson);
	
	    
		return null;

	}
	
}
