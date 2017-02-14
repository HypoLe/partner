package com.boco.eoms.partner.net.webapp.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.validator.GenericValidator;
import org.apache.jcs.JCS;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import utils.PartnerPrivUtils;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.area.model.TawSystemArea;
import com.boco.eoms.commons.system.area.service.ITawSystemAreaManager;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserAndAreaMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerUserAndArea;
import com.boco.eoms.partner.baseinfo.util.PartnerCityByUser;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;
import com.boco.eoms.partner.net.mgr.IGridMgr;
import com.boco.eoms.partner.net.mgr.ISiteMgr;
import com.boco.eoms.partner.net.model.Gride;
import com.boco.eoms.partner.net.util.GridConstants;
import com.boco.eoms.partner.net.util.SiteConstants;
import com.boco.eoms.partner.net.webapp.form.GrideForm;

/**
 * <p>
 * Title:网格
 * </p>
 * <p>
 * Description:网格
 * </p>
 * <p>
 * Tue Nov 17 17:51:41 CST 2009
 * </p>
 * 
 * @moudle.getAuthor() benweiwei
 * @moudle.getVersion() 1.0
 * 
 */
public final class GridAction extends BaseAction {
	  
	//private static EomsJCS gridCache;
	//private static String gridCacheName;
 
	/**
	 * 未指定方法时默认调用的方法
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return search(mapping, form, request, response);
	}
 	
 	/**
	 * 新增网格
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		/**
    	 * 列出用户所属权限下的地市
    	 * 2010-4-28
    	 * wangjunfeng
    	 */
    	String userId = this.getUser(request).getUserid();
		
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		List city = new ArrayList();
		
		if(userId=="admin" || "admin".equals(userId)){//管理员登陆显示省下的所有地市
    		 city = PartnerCityByUser.getCityByProvince(province);
    	}else{//非管理员用户登陆，显示权限下的地市
    		PartnerUserAndAreaMgr partnerUserAndAreaMgr = (PartnerUserAndAreaMgr)ApplicationContextHolder.getInstance().getBean("partnerUserAndAreaMgr");
			List areasRight = partnerUserAndAreaMgr.listCountyOfPnrUserArea(userId);
			PartnerUserAndArea partnerUserAndArea = (PartnerUserAndArea)areasRight.get(0);
			String areas = partnerUserAndArea.getCityId();
	    	String [] areasTem = areas.split(",");
	    	StringBuffer areasBuffer = new StringBuffer();
	    	for(int i=0;i < areasTem.length ;i++){
	    		areasBuffer.append("'" );
	    		areasBuffer.append(areasTem[i] );
	    		areasBuffer.append("'" );
	    		areasBuffer.append("," );
	    	}
	    	String areasLast = areasBuffer.substring(0, areasBuffer.length()-1).toString();
    		// city = PartnerCityByUser.getCityByRight(province,areasLast); 
    	}
    	request.setAttribute("city", city);
    	
		return mapping.findForward("edit");
	}
	
	/**
	 * 修改网格
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IGridMgr gridMgr = (IGridMgr) getBean("iGridMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		
/*	   	// 改为2级联动，第一级为地市，第二级为县区，初始化的时候先显示第1级
		PnrServiceAreaIdList pnrMa =(PnrServiceAreaIdList) getBean("pnrServiceAreaIdList");
		String areaId = StaticMethod.nullObject2String(pnrMa.getRootAreaId());
		//地市  取4位 
		String len="4";
		List city = gridMgr.listCityOfArea(areaId,len);
		request.setAttribute("areaId", areaId);
		request.setAttribute("city", city);
*/	
		
/*	   	*//**
    	 * 加载当前用户所属地市
    	 *//*
    	//当前用户名
    	String userid = this.getUser(request).getUserid();
    	List city = PartnerCityByUser.getCityByUser(userid);
 */   	
/*    	*//**
    	 * 根据当前省ID，列出所有地市
    	 *//*
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
    	List city = PartnerCityByUser.getCityByProvince(province);    	
    	
    	request.setAttribute("city", city);
*/
		/**
    	 * 列出用户所属权限下的地市
    	 * 2010-4-28
    	 * wangjunfeng
    	 */
    	String userId = this.getUser(request).getUserid();
		PartnerUserAndAreaMgr partnerUserAndAreaMgr = (PartnerUserAndAreaMgr)ApplicationContextHolder
			.getInstance().getBean("partnerUserAndAreaMgr");
		
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		List city = new ArrayList();
//		if(userId=="admin" || "admin".equals(userId)){//管理员登陆显示省下的所有地市
    		 city = PartnerCityByUser.getCityByProvince(province);
//    	}else{//非管理员用户登陆，显示权限下的地市
    		 //city = PartnerCityByUser.getCityByRight(province,areasLast); 
//    	}
    	request.setAttribute("city", city);
    	
    	//得到合作伙伴列表
    	ITawSystemDeptManager mgr = (ITawSystemDeptManager)getBean("ItawSystemDeptManager");
    	List listPnrDept = mgr.getNextLevecDepts("102", "0");
    	request.setAttribute("listPnrDept", listPnrDept);
    	
    	Gride grid = gridMgr.getGrid(id);
		grid.setProvider(grid.getPartnerid()+"_"+grid.getBigpartnerid());
		GrideForm gridForm = (GrideForm) convert(grid);
		updateFormBean(mapping, request, gridForm);
		return mapping.findForward("edit");
	}
	/**
	 * 查看网格
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
		IGridMgr gridMgr = (IGridMgr) getBean("iGridMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		Gride grid = gridMgr.getGrid(id);
		GrideForm gridForm = (GrideForm) convert(grid);
		updateFormBean(mapping, request, gridForm);
		return mapping.findForward("detail");
	}
     
    /**
     * 查看网格 带基站列表
     */
    public ActionForward detailWithSite(ActionMapping mapping, ActionForm form,
    		HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    	IGridMgr gridMgr = (IGridMgr) getBean("iGridMgr");
    	String id = StaticMethod.null2String(request.getParameter("gridId"));
    	Gride grid = gridMgr.getGrid(id);
    	GrideForm gridForm = (GrideForm) convert(grid);
    	updateFormBean(mapping, request, gridForm);
    	
    	String pageIndexName = new org.displaytag.util.ParamEncoder(
				SiteConstants.SITE_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));

		String where = " where gridNumber='"+id+"'";
    	ISiteMgr siteMgr = (ISiteMgr) getBean("iSiteMgr");
		Map map = (Map) siteMgr.getSites(pageIndex, pageSize, where.toString());
		
		List list = (List) map.get("result");
		request.setAttribute(SiteConstants.SITE_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("flag", request.getAttribute("flag"));
		
    	return mapping.findForward("detail");
    }
	
	/**
	 * 保存网格
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
		IGridMgr gridMgr = (IGridMgr) getBean("iGridMgr");
		String userId = this.getUser(request).getUserid();
		GrideForm gridForm = (GrideForm) form;
		boolean isNew = (null == gridForm.getId() || "".equals(gridForm.getId()));
		Gride grid = (Gride) convert(gridForm);
		grid.setIsDel(Integer.valueOf(0));
		grid.setAddTime(new Date());
		grid.setAddUser(this.getUserId(request));
		gridMgr.saveGrid(grid);
		return mapping.findForward("search");
	}
	
	/**
	 * 删除网格
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward remove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
//		获取用户,设定删除时间和用户,伪删除
		String userId = this.getUser(request).getUserid();
		//物理删除
		IGridMgr gridMgr = (IGridMgr) getBean("iGridMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		Gride grid = gridMgr.getGrid(id);
		grid.setDelTime(StaticMethod.getLocalTime());
		grid.setDelUser(userId);
		grid.setIsDel(Integer.valueOf(1));
		gridMgr.saveGrid(grid);
		
		/*
		 * 删除网格时 更新缓存:网格个数-1
		 * wangjunfeng
		 * 2010-9-6
		 */
		String partnerId = grid.getPartnerid();
	   	JCS gridCacheOld = JCS.getInstance("GRID");
    	String gridNo = (String)gridCacheOld.get(partnerId); 
    	int gridNoNewFlag = Integer.valueOf(gridNo)- 1;
    	String gridNoNew = String.valueOf(gridNoNewFlag);
//    	gridCacheName = StaticMethod.getNodeName("SYSTEM.cache.gridCache");
//    	gridCache = EomsJCS.getInstance(this.gridCacheName);
//    	gridCache.put(partnerId, gridNoNew);
		
		
//		deliveryRequestConvertBO.getGrid("2", grid);//接口同步-删除
		return search(mapping, form, request, response);
	}
	
	/**
	 * 分页显示网格列表
	 * 
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
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				GridConstants.GRID_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
   	
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		List<TawSystemArea> region = PartnerCityByUser.getCityByProvince(province);
		request.setAttribute("region", region);
	    
		/*
		 * 列出用户所属权限下的Grid
		 * 2012-2-17
		 * chengyuanshu
		 */
		String priv = PartnerPrivUtils.getPrivSqlByArea(request);
		StringBuffer whereStr = new StringBuffer(" where isDel = '0' and (region "+priv+" or city "+priv+")");
    	IGridMgr gridMgr = (IGridMgr) getBean("iGridMgr");
		Map map = (Map) gridMgr.getGrids(pageIndex, pageSize, whereStr.toString());
		List list = (List) map.get("result");
		request.setAttribute(GridConstants.GRID_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");
	}
	/**
	 * 判断网格名称是否存在ajax
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward validationGridName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String gridName = request.getParameter("gridName");
		IGridMgr gridMgr = (IGridMgr) getBean("iGridMgr");

		List list = gridMgr.getGridsByGridName(gridName);
		
		// 设置返回页面的信息
		JSONArray json = new JSONArray();
		JSONObject jitem = new JSONObject();
		if(list.size()>0){
			jitem.put("message", false);
		} else {
			jitem.put("message", true);
		}
		json.put(jitem);
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(json.toString());
		return null;
	}
	/**
	 * 按条件显示网格列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchGridList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageIndexName = new org.displaytag.util.ParamEncoder(GridConstants.GRID_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		
    	
		IGridMgr gridMgr = (IGridMgr) getBean("iGridMgr");
		String gridName = StaticMethod.null2String(request.getParameter("gridName"));
		String region = StaticMethod.null2String(request.getParameter("region"));
		String county = StaticMethod.null2String(request.getParameter("city"));
		
		String priv = PartnerPrivUtils.getPrivSqlByArea(request);
		

	    StringBuffer whereStr = new StringBuffer(" where isDel = '0' and (region "+priv+" or city "+priv+")");
		String provider = StaticMethod.null2String(request.getParameter("provider"));
		if(!gridName.equals("")){
			whereStr.append(" and gridName like '%");
			whereStr.append(gridName);
			whereStr.append("%'");
		}
		
		if(!"".equals(region)){
			whereStr.append(" and region = '"+ region + "'") ;
			request.setAttribute("regionStr", region);
		}
		if(!county.equals("")){
			whereStr.append(" and city = '");
			whereStr.append(county);
			whereStr.append("'");
			request.setAttribute("cityStr", county);
		}
	
		Map map = (Map) gridMgr.getGrids(pageIndex, pageSize, whereStr.toString());
		List list = (List) map.get("result");
		
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		List<TawSystemArea> regionList = PartnerCityByUser.getCityByProvince(province);
		request.setAttribute("region", regionList);
		
		request.setAttribute(GridConstants.GRID_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");
	}		
	
	/**
	 * 页面二级联动，已知地市，返回对应县区list
	 * add by wangjunfeng
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changeCity(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String cityId = request.getParameter("city");
		String countyBuffer = PartnerCityByUser.getCountyByCity(cityId);
		
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(countyBuffer);
		response.getWriter().flush();
		
		return null;
	}
	/**
	 * 判断网格编号是否存在ajax
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward validationGridNumber(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String gridNumber = request.getParameter("gridNumber");
		IGridMgr gridMgr = (IGridMgr) getBean("iGridMgr");
		StringBuffer where = new StringBuffer();
		where.append(" where gridNumber = '");
		where.append(gridNumber);
		where.append("'");
		List list = gridMgr.getGridsByWhere(where.toString());
		
		// 设置返回页面的信息
		JSONArray json = new JSONArray();
		JSONObject jitem = new JSONObject();
		if(list!=null&&list.size()>0){
			jitem.put("message", false);
		} else {
			jitem.put("message", true);
		}
		json.put(jitem);
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(json.toString());
		return null;
	}
	
	/**
	 * 根据id批量删除
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward removes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IGridMgr gridMgr = (IGridMgr) getBean("iGridMgr");
		String[] ids = request.getParameterValues("ids");
/*		DeliveryRequestConvertBO deliveryRequestConvertBO = new DeliveryRequestConvertBO();
		String userId = this.getUser(request).getUserid();
		for(int i=0;i<ids.length;i++){
			
			Grid grid = gridMgr.getGrid(ids[i]);
			grid.setDelTime(StaticMethod.getLocalTime());
			grid.setDelUser(userId);
			grid.setIsDel(Integer.valueOf(1));
			gridMgr.saveGrid(grid);
			deliveryRequestConvertBO.getGrid("2", grid);//接口同步-删除
		}
*/		
		
		/*
		 * 批量删除网格时 更新缓存:网格个数-批量删除网格个数
		 * wangjunfeng
		 * 2010-9-6
		 */
	   	JCS gridCacheOld = JCS.getInstance("GRID");
//    	gridCacheName = StaticMethod.getNodeName("SYSTEM.cache.gridCache");
//    	gridCache = EomsJCS.getInstance(this.gridCacheName);
		String partnerId ="";
		for(int i=0;i<ids.length;i++){
			Gride grid = gridMgr.getGrid(ids[i]);
			partnerId = grid.getPartnerid();
	    	String gridNo = (String)gridCacheOld.get(partnerId); 
	    	int gridNoNewFlag = Integer.valueOf(gridNo)- 1;
	    	String gridNoNew = String.valueOf(gridNoNewFlag);
//	    	gridCache.put(partnerId, gridNoNew);
		}
//		gridMgr.removeGrids(ids);
		
		return search(mapping, form, request, response);
	}
	
	/**
	 * 基站列表记录（广西的基站=福建的网格）
	 * 基站代维巡检时基站选择列表
	 * add by  wangjunfeng
	 * 2010-10-9
	 * 分页显示网格信息管理列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchSite(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				SiteConstants.SITE_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));

		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		List city = new ArrayList();
    	city = PartnerCityByUser.getCityByProvince(province);
    	request.setAttribute("city", city);

		
		
		String partnerId = request.getParameter("partnerId");
	
		StringBuffer where = new StringBuffer();
		where.append("  where grid.isDel = '0' ");
		where.append("  and grid.partnerid = '"+ partnerId +"' ");
		
		String gridName = StaticMethod.null2String(request.getParameter("gridName"));
		if(!"".equals(gridName)){
			where.append(" and grid.gridName like'%");
			where.append(gridName);
			where.append("%'");
		}
		
		
		IGridMgr gridMgr = (IGridMgr) getBean("iGridMgr");
		Map map = (Map) gridMgr.getGrids(pageIndex, pageSize, where.toString());
		List list = (List) map.get("result");
		
		request.setAttribute(GridConstants.GRID_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("partnerId", partnerId);
		
		String flag = "mainFlag";
		request.setAttribute("flag", flag);
		
		return mapping.findForward("searchCyc");
	}
	
	
	
    /**
     * 根据基站id得到 基站名称   用于ajax调用
     * add by wangjunfeng
     * 2010-9-15
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward getCycSite(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String gridId = StaticMethod.null2String(request.getParameter("site"));
		IGridMgr gridMgr = (IGridMgr) getBean("iGridMgr");
			
		Gride grid = gridMgr.getGrid(gridId);
		GrideForm gridForm = (GrideForm) convert(grid);
		String gridName = gridForm.getGridName();
		
		
		JSONArray json = new JSONArray();
		JSONObject jitem = new JSONObject();
		jitem.put("siteId", gridId);
		jitem.put("siteName", gridName);
		json.put(jitem);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(json.toString());
	
		return null;
	}
	
    
	/**
	 * 抽查基站巡检记录（基站列表）
	 * add by  wangjunfeng
	 * 2010-9-15
	 * 分页显示站点信息管理列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchCyc(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		/*String pageIndexName = new org.displaytag.util.ParamEncoder(
				SiteConstants.SITE_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));

		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		List city = new ArrayList();
    	city = PartnerCityByUser.getCityByProvince(province);
    	request.setAttribute("city", city);
		
		String partnerId = request.getParameter("partnerId");
		
		java.util.Date currentDate = new java.util.Date();
		Date checkTime = currentDate;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timeStr = dateFormat.format(checkTime);
		String monthStr = timeStr.substring(5, 7);
		//2010-9-14 17:01:53
		
		StringBuffer where = new StringBuffer();
		where.append("  where grid.id = main.siteId ");
		where.append("  and main.markerIsok = '0' ");	//markerIsok:0未抽检
		where.append("  and main.isDraft = '1' ");  	//isDraft:1不是草稿
		where.append("  and to_char(main.checkTime,'MM') ='"+ monthStr +"' ");
		where.append("  and grid.isDel = '0' ");
		where.append("  and grid.partnerid = '"+ partnerId +"' ");
		
		String gridName = StaticMethod.null2String(request.getParameter("gridName"));
		if(!"".equals(gridName)){
			where.append(" and grid.gridName like'%");
			where.append(gridName);
			where.append("%'");
		}
		
		IGridMgr gridMgr = (IGridMgr) getBean("iGridMgr");
		Map map = (Map) gridMgr.getCycSite(pageIndex, pageSize, where.toString());
		List list = (List) map.get("result");
		List gridList = new ArrayList();
		Object[] obj =null;
		Grid grid =null;
		for(int i=0 ;i<list.size();i++){
			obj = (Object[])list.get(i);
			grid = (Grid)obj[0];
			gridList.add(grid);
		}
		
		request.setAttribute(GridConstants.GRID_LIST, gridList);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("partnerId", partnerId);
		
		return mapping.findForward("searchCyc");*/
		return null;
	}

	/**
	 * 页面二级联动，已知地市，县区 联动出合作伙伴
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changePartner(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONArray json = new JSONArray();
		JSONObject jitem = new JSONObject();
		//region地市，city县区
		String region = StaticMethod.null2String(request.getParameter("region"));
		String city = StaticMethod.null2String(request.getParameter("city"));

		// 列出选择当前地市的合作伙伴
		StringBuffer provider_list = new StringBuffer();
		provider_list.append("," + "");
		provider_list.append("," + "--请选择合作伙伴--");

		PartnerDeptMgr pnrDeptMgr = (PartnerDeptMgr) getBean("partnerDeptMgr");
		List provider = pnrDeptMgr.listProviderByRegionOrByCity(region,city);
	
		for (int i = 0; i < provider.size(); i++) {
			Object[] provider1 = (Object[])provider.get(i);
//		修改为新增字段后的设定	
			provider_list.append("," + provider1[1]);
			provider_list.append("," + provider1[0]);
		}
		String providerBuffer = provider_list.toString();
		
		providerBuffer = providerBuffer.substring(1, providerBuffer.length());
		
		jitem.put("pb", providerBuffer);
		json.put(jitem);
		
		response.setCharacterEncoding("utf-8");		
		response.getWriter().write(json.toString());

		return null;
	}
	
}