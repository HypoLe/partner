package com.boco.eoms.partner.net.webapp.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.dict.util.DictMgrLocator;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserAndAreaMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.baseinfo.model.PartnerUserAndArea;
import com.boco.eoms.partner.baseinfo.util.PartnerCityByUser;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;
import com.boco.eoms.partner.net.mgr.IGridMgr;
import com.boco.eoms.partner.net.mgr.ISiteMgr;
import com.boco.eoms.partner.net.model.Gride;
import com.boco.eoms.partner.net.model.StationPoint;
import com.boco.eoms.partner.net.util.SiteConstants;
import com.boco.eoms.partner.net.webapp.form.StationPointForm;

/**
 * <p>
 * Title:站点信息管理
 * </p>
 * <p>
 * Description:站点信息管理
 * </p>
 * <p>
 * Tue Nov 17 17:51:41 CST 2009
 * </p>
 * 
 * @moudle.getAuthor() benweiwei
 * @moudle.getVersion() 1.0
 * 
 */
public final class SiteAction extends BaseAction {
//	private static EomsJCS siteCache;
//	private static String siteCacheName;
	
 
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
	 * 新增站点信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    public ActionForward newExpert(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
    	PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
    	List city = PartnerCityByUser.getCityByProvince(province);    	
    	request.setAttribute("city", city);
		return mapping.findForward("newExpert");
	}
 	
 	/**
	 * 新增站点信息管理
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
    	
     	
		return mapping.findForward("edit");
	}
	
    
    public ActionForward detailWithInspect(ActionMapping mapping, ActionForm form,
    		HttpServletRequest request, HttpServletResponse response)
    throws Exception { 
//    	IPnrInspectDeviceMgr iPnrInspectDeviceMgr = (IPnrInspectDeviceMgr) getBean("iPnrInspectDeviceMgr");
//    	String siteId = request.getParameter("siteId");
//    	ISiteMgr siteMgr = (ISiteMgr) getBean("iSiteMgr");
//		StationPoint site = siteMgr.getSite(siteId);
//		
//		request.setAttribute("siteForm", site);
//		
//		String pageIndexName = new org.displaytag.util.ParamEncoder(
//				PnrInspectDeviceConstants.PNRINSPECTDEVICE_LIST)
//				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
//		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
//				.getPageSize();
//		final Integer pageIndex = Integer.valueOf(GenericValidator 
//				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
//				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
//		       
//		String where = " where site='"+siteId+"'";
//		
//		Map map = (Map) iPnrInspectDeviceMgr.getPnrInspectDevices(pageIndex, pageSize, where);
//		List list = (List) map.get("result");
//		request.setAttribute(PnrInspectDeviceConstants.PNRINSPECTDEVICE_LIST, list);
//		request.setAttribute("resultSize", map.get("total"));
//		request.setAttribute("pageSize", pageSize);
//		
    	return mapping.findForward("detail");
    }
    
	/** 
	 * 修改站点信息管理
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
////    	用siteNo
////		String siteNo = StaticMethod.null2String(request.getParameter("siteNo"));
//    	String idSite = StaticMethod.null2String(request.getParameter("idSite"));
//		
//    	ISiteMgr siteMgr = (ISiteMgr) getBean("iSiteMgr");
//		if(!"".equals(idSite)){
//	
//			StationPoint site = siteMgr.getSite(idSite);
////			site.setProvider(site.getPartnerid()+"_"+site.getBigpartnerid());
//			site.setProvider(site.getPartnerid());
//			site.setGrid(site.getGridid());
//			StationPointForm siteForm = (StationPointForm) convert(site);
//			updateFormBean(mapping, request, siteForm);
//		}
//		
//		/**
//    	 * 列出用户所属权限下的地市
//    	 * 2010-4-28
//    	 * wangjunfeng
//    	 */
//    	String userId = this.getUser(request).getUserid();
//		
//		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
//		String province = pnrBaseAreaIdList.getRootAreaId();
//		List city = new ArrayList();
//		
//		if(userId=="admin" || "admin".equals(userId)){//管理员登陆显示省下的所有地市
//    		 city = PartnerCityByUser.getCityByProvince(province);
//    	}else{//非管理员用户登陆，显示权限下的地市
//    		PartnerUserAndAreaMgr partnerUserAndAreaMgr = (PartnerUserAndAreaMgr)ApplicationContextHolder.getInstance().getBean("partnerUserAndAreaMgr");
//			List areasRight = partnerUserAndAreaMgr.listCountyOfPnrUserArea(userId);
//			PartnerUserAndArea partnerUserAndArea = (PartnerUserAndArea)areasRight.get(0);
//			String areas = partnerUserAndArea.getCityId();
//	    	String [] areasTem = areas.split(",");
//	    	StringBuffer areasBuffer = new StringBuffer();
//	    	for(int i=0;i < areasTem.length ;i++){
//	    		areasBuffer.append("'" );
//	    		areasBuffer.append(areasTem[i] );
//	    		areasBuffer.append("'" );
//	    		areasBuffer.append("," );
//	    	}
//	    	String areasLast = areasBuffer.substring(0, areasBuffer.length()-1).toString();
//    		// city = PartnerCityByUser.getCityByRight(province,areasLast); 
//    	}
//    	request.setAttribute("city", city);
//    	
//    	//查询维护级别集合
//		IPnrInspectCycleMgr inspectCycleMgr = (IPnrInspectCycleMgr) getBean("iPnrInspectCycleMgr");
//		List cycleList = inspectCycleMgr.getCycles();
//		request.setAttribute("cycleList", cycleList);
//		
//		//站点类型字典列表
//		//ITawSystemDictTypeManager dictTypeManager = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");
//		//List DictItemXML = (List)dictTypeManager.getDictSonsByDictid("12102");
//		//request.setAttribute("DictItemXML", DictItemXML);
		
		return mapping.findForward("edit");
	}
	
	/**
	 * 保存站点信息管理
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
		String partnerId = StaticMethod.null2String(request.getParameter("partnerId"));
		ISiteMgr siteMgr = (ISiteMgr) getBean("iSiteMgr");
//		ISitePapersMgr sitePapersMgr = (ISitePapersMgr) getBean("iSitePapersMgr");
		String userId = this.getUser(request).getUserid();
		StationPointForm siteForm = (StationPointForm) form;
		boolean isNew = (null == siteForm.getId() || "".equals(siteForm.getId()));
		StationPoint site = (StationPoint) convert(siteForm);
		site.setIsDel(Integer.valueOf(0));
		site.setUnconfig(Integer.valueOf(0));
		
		//site.setGridid(site.getGrid());
		site.setPartnerid(partnerId); 
		site.setGrid(DictMgrLocator.getId2NameService().id2Name(site.getGridNumber(),"gridDao"));
		site.setSiteLevleName(site.getSiteLevle());
		
		if (isNew) {
			site.setAddTime(StaticMethod.getLocalTime());
			site.setAddUser(userId);
			siteMgr.saveSite(site);
			
			/*
			 * 新增基站时 更新缓存:基站个数+1
			 * wangjunfeng
			 * 2010-9-6
			 */
			/*String gridNumber = site.getGridNumber();
		   	JCS siteCacheOld = JCS.getInstance("site");
	    	String siteNo = (String)siteCacheOld.get(gridNumber); 
	    	//int siteNoNewFlag = Integer.valueOf(siteNo)+ 1;
	    	int siteNoNewFlag = StaticMethod.null2int(siteNo)+ 1;
	    	String siteNoNew = String.valueOf(siteNoNewFlag);*/
			
			
//	    	siteCacheName = StaticMethod.getNodeName("SYSTEM.cache.siteCache");
//	    	siteCache = EomsJCS.getInstance(this.siteCacheName);
//	    	siteCache.put(gridNumber, siteNoNew);
			/*String gridId = site.getGridid();
		   	JCS siteCacheOld = JCS.getInstance("site");
	    	String siteNo = (String)siteCacheOld.get(gridId); 
	    	//int siteNoNewFlag = Integer.valueOf(siteNo)+ 1;
	    	int siteNoNewFlag = StaticMethod.null2int(siteNo)+ 1;
	    	String siteNoNew = String.valueOf(siteNoNewFlag);
	    	siteCacheName = StaticMethod.getNodeName("SYSTEM.cache.siteCache");
	    	siteCache = EomsJCS.getInstance(this.siteCacheName);
	    	siteCache.put(gridId, siteNoNew);*/
			
		} else {
/*			Site site2 = siteMgr.getSite(site.getId());
			if(!site.getSiteNo().equals(site2.getSiteNo())){
				List list = sitePapersMgr.getSitePapersBySiteNo(site2.getSiteNo());
				for(int i=0;i<list.size();i++){
					SitePapers sitePapers = (SitePapers)list.get(i);
					//siteNo改为idSite
					sitePapers.setId(site.getId());
					sitePapersMgr.saveSitePapers(sitePapers);
				}
			}
*/			
			site.setUpdateTime(StaticMethod.getLocalTime());
			site.setUpdateUser(userId);
			siteMgr.saveSite(site);
//			return mapping.findForward("success");
		}
		siteForm.setId(site.getId());
		request.setAttribute("siteName", siteForm.getSiteName());
		request.setAttribute("operType", "save");
		//return edit(mapping, siteForm, request, response);
		return mapping.findForward("success");
	}
	
	/**
	 * 删除站点信息管理
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
/*		ISiteMgr siteMgr = (ISiteMgr) getBean("iSiteMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		Site site = siteMgr.getSite(id);
		site.setDelTime(StaticMethod.getLocalTime());
		site.setDelUser(userId);
		site.setIsDel(Integer.valueOf(1));
		siteMgr.saveSite(site);
*/		
		//物理删除
		ISiteMgr siteMgr = (ISiteMgr) getBean("iSiteMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		StationPoint site = siteMgr.getSite(id);
		site.setDelTime(StaticMethod.getLocalTime());
		site.setDelUser(userId);
		site.setIsDel(Integer.valueOf(1));
		siteMgr.saveSite(site);
		
		/*
		 * 删除基站时 更新缓存:基站个数-1
		 * wangjunfeng
		 * 2010-9-6
		 */
		/*String gridId = site.getGridid();
	   	JCS siteCacheOld = JCS.getInstance("site");
    	String siteNo = (String)siteCacheOld.get(gridId); 
    	int siteNoNewFlag = StaticMethod.null2int(siteNo)- 1;
    	String siteNoNew = String.valueOf(siteNoNewFlag);*/
		
		
//    	siteCacheName = StaticMethod.getNodeName("SYSTEM.cache.siteCache");
//    	siteCache = EomsJCS.getInstance(this.siteCacheName);
//    	siteCache.put(gridId, siteNoNew);
		
		
		return search(mapping, form, request, response);
	}
	
	/**
	 * 分页显示站点信息管理列表
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
//		String pageIndexName = new org.displaytag.util.ParamEncoder(
//				SiteConstants.SITE_LIST)
//				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
//		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
//				.getPageSize();
//		final Integer pageIndex = new Integer(GenericValidator
//				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
//				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
//		
//    	String userId = this.getUser(request).getUserid();
//		PartnerUserAndAreaMgr partnerUserAndAreaMgr = (PartnerUserAndAreaMgr)ApplicationContextHolder
//			.getInstance().getBean("partnerUserAndAreaMgr");
//		List areasRight = partnerUserAndAreaMgr.listCountyOfPnrUserArea(userId);
//		
//		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
//		String province = pnrBaseAreaIdList.getRootAreaId();
//		
//		List city = new ArrayList();
//    	city = PartnerCityByUser.getCityByProvince(province);
//    	request.setAttribute("city", city);
//		
//		StringBuffer where = new StringBuffer();
//		where.append(" where isDel = '0' ");
//		String siteName = StaticMethod.null2String(request.getParameter("siteName"));
//		String siteNo = StaticMethod.null2String(request.getParameter("siteNo"));
//		String siteType = StaticMethod.null2String(request.getParameter("siteType"));
//		String frequencyBand = StaticMethod.null2String(request.getParameter("frequencyBand"));
//		String coverType = StaticMethod.null2String(request.getParameter("coverType"));
//		String cellulType = StaticMethod.null2String(request.getParameter("cellulType"));
//		String siteLevle = StaticMethod.null2String(request.getParameter("siteLevle"));
//		String roomType = StaticMethod.null2String(request.getParameter("roomType"));
//		String region = StaticMethod.null2String(request.getParameter("region"));
//		String county = StaticMethod.null2String(request.getParameter("city"));
//		String specialtyType = StaticMethod.null2String(request.getParameter("specialtyType"));
//		String gridNumber = StaticMethod.null2String(request.getParameter("gridNumber"));
//		String maintainType = StaticMethod.null2String(request.getParameter("maintainType"));
//		String vendor = StaticMethod.null2String(request.getParameter("vendor"));
//		String partnername = StaticMethod.null2String(request.getParameter("partnername"));
//		String unconfig = StaticMethod.null2String(request.getParameter("unconfig"),"0");
//		if(!"".equals(partnername)){
//			where.append(" and partnername like'%");
//			where.append(partnername);
//			where.append("%'");
//		}
//		if(!"".equals(siteName)){
//			where.append(" and siteName like'%");
//			where.append(siteName);
//			where.append("%'");
//		}
//		if(!"".equals(unconfig)){
//			where.append(" and unconfig ='");
//			where.append(unconfig);
//			where.append("'");
//		}	
//		if(!"".equals(siteNo)){
//			where.append(" and siteNo ='");
//			where.append(siteNo);
//			where.append("'");
//		}		
//		if(!"".equals(siteType)){
//			where.append(" and siteType ='");
//			where.append(siteType);
//			where.append("'");
//		}		
//		if(!"".equals(frequencyBand)){
//			where.append(" and frequencyBand ='");
//			where.append(frequencyBand);
//			where.append("'");
//		}		
//		if(!"".equals(coverType)){
//			where.append(" and coverType ='");
//			where.append(coverType);
//			where.append("'");
//		}		
//		if(!"".equals(cellulType)){
//			where.append(" and cellulType ='");
//			where.append(cellulType);
//			where.append("'");
//		}		
//		if(!"".equals(siteLevle)){
//			where.append(" and siteLevle ='");
//			where.append(siteLevle);
//			where.append("'");
//		}		
//		if(!"".equals(roomType)){
//			where.append(" and roomType ='");
//			where.append(roomType);
//			where.append("'");
//		}
//		if(!"".equals(specialtyType)){
//			where.append(" and specialtyType ='");
//			where.append(specialtyType);
//			where.append("'");
//		}
//
//		if(!"".equals(region)){
//			where.append(" and region ='");
//			where.append(region);
//			where.append("'");
//		}
//		if(!"".equals(county)){
//			where.append(" and city ='");
//			where.append(county);
//			where.append("'");
//		}		
//		if(!"".equals(gridNumber)){
//			where.append(" and gridNumber ='");
//			where.append(gridNumber);
//			where.append("'");
//		}	
//		if(!"".equals(maintainType)){
//			where.append(" and maintainType ='");
//			where.append(maintainType);
//			where.append("'");
//		}		
//		if(!"".equals(vendor)){
//			where.append(" and vendor ='");
//			where.append(vendor);
//			where.append("'");
//		}		
//		
//		
//		ISiteMgr siteMgr = (ISiteMgr) getBean("iSiteMgr");
//		Map map = (Map) siteMgr.getSites(pageIndex, pageSize, where.toString());
//		List list = (List) map.get("result");
//		request.setAttribute(SiteConstants.SITE_LIST, list);
//		request.setAttribute("resultSize", map.get("total"));
//		request.setAttribute("pageSize", pageSize);
//    	
//		//查询维护级别集合
//		IPnrInspectCycleMgr inspectCycleMgr = (IPnrInspectCycleMgr) getBean("iPnrInspectCycleMgr");
//		List cycleList = inspectCycleMgr.getCycles();
//		request.setAttribute("cycleList", cycleList);
//		
//		//站点类型字典列表
//		//ITawSystemDictTypeManager dictTypeManager = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");
//		//List DictItemXML = (List)dictTypeManager.getDictSonsByDictid("12102");
//		//request.setAttribute("DictItemXML", DictItemXML);
//		
		return mapping.findForward("list");
	}
	

	/**
	 * 分页显示接口同步数据的 站点信息管理列表
	 * (即显示标志位unconfig为1的数据)
	 * 2010-02-04
	 * add by wangjunfeng
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchInterface(ActionMapping mapping, ActionForm form,
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
		
/*	   	*//**
    	 * 加载当前用户所属地市
    	 *//*
    	//当前用户名
    	String userid = this.getUser(request).getUserid();
    	List city = PartnerCityByUser.getCityByUser(userid);
    	request.setAttribute("city", city);
*/		
/*		
		 * 根据当前省ID，列出所有地市
		 
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
		
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		List city = new ArrayList();
		if(userId=="admin" || "admin".equals(userId)){//管理员登陆显示省下的所有地市
    		 city = PartnerCityByUser.getCityByProvince(province);
    	}else{//非管理员用户登陆，显示权限下的地市
//    		 city = PartnerCityByUser.getCityByRight(province,areasLast); 
    	}
    	request.setAttribute("city", city);

		
		StringBuffer where = new StringBuffer();
		where.append(" where isDel = '0' and unconfig='1' ");
		String siteName = StaticMethod.null2String(request.getParameter("siteName"));
		String siteNo = StaticMethod.null2String(request.getParameter("siteNo"));
		String siteType = StaticMethod.null2String(request.getParameter("siteType"));
		String frequencyBand = StaticMethod.null2String(request.getParameter("frequencyBand"));
		String coverType = StaticMethod.null2String(request.getParameter("coverType"));
		String cellulType = StaticMethod.null2String(request.getParameter("cellulType"));
		String siteLevle = StaticMethod.null2String(request.getParameter("siteLevle"));
		String roomType = StaticMethod.null2String(request.getParameter("roomType"));
		String region = StaticMethod.null2String(request.getParameter("region"));
		String county = StaticMethod.null2String(request.getParameter("city"));
		String grid = StaticMethod.null2String(request.getParameter("grid"));
		String maintainType = StaticMethod.null2String(request.getParameter("maintainType"));
		String vendor = StaticMethod.null2String(request.getParameter("vendor"));
		String provider = StaticMethod.null2String(request.getParameter("provider"));
		if(!"".equals(provider)){
//			修改后的查询
			String[] providers =  provider.split("_");
			where.append(" and partnerid ='");
			where.append(providers[0]);
			where.append("'");
		}	
		if(!"".equals(siteName)){
			where.append(" and siteName like'%");
			where.append(siteName);
			where.append("%'");
		}
		if(!"".equals(siteNo)){
			where.append(" and siteNo ='");
			where.append(siteNo);
			where.append("'");
		}		
		if(!"".equals(siteType)){
			where.append(" and siteType ='");
			where.append(siteType);
			where.append("'");
		}		
		if(!"".equals(frequencyBand)){
			where.append(" and frequencyBand ='");
			where.append(frequencyBand);
			where.append("'");
		}		
		if(!"".equals(coverType)){
			where.append(" and coverType ='");
			where.append(coverType);
			where.append("'");
		}		
		if(!"".equals(cellulType)){
			where.append(" and cellulType ='");
			where.append(cellulType);
			where.append("'");
		}		
		if(!"".equals(siteLevle)){
			where.append(" and siteLevle ='");
			where.append(siteLevle);
			where.append("'");
		}		
		if(!"".equals(roomType)){
			where.append(" and roomType ='");
			where.append(roomType);
			where.append("'");
		}		
		if(!"".equals(region)){
			where.append(" and region ='");
			where.append(region);
			where.append("'");
		}
				
		if(!"".equals(county)){
			where.append(" and city ='");
			where.append(county);
			where.append("'");
		}		
		if(!"".equals(grid)){
			where.append(" and grid ='");
			where.append(grid);
			where.append("'");
		}		
		if(!"".equals(maintainType)){
			where.append(" and maintainType ='");
			where.append(maintainType);
			where.append("'");
		}		
		if(!"".equals(vendor)){
			where.append(" and vendor ='");
			where.append(vendor);
			where.append("'");
		}		
		
		ISiteMgr siteMgr = (ISiteMgr) getBean("iSiteMgr");
		Map map = (Map) siteMgr.getSites(pageIndex, pageSize, where.toString());
		List list = (List) map.get("result");
		request.setAttribute(SiteConstants.SITE_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");
	}

	
	/**
	 * 判断基站站号是否存在ajax
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward validationSiteNo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String siteNo = request.getParameter("siteNo");
		ISiteMgr siteMgr = (ISiteMgr) getBean("iSiteMgr");

		List list = siteMgr.getSitesBySiteNo(siteNo);
		
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
	 * 抽查对象列表（基站）
	 * add by  wangjunfeng
	 * 
	 * 分页显示站点信息管理列表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchSample(ActionMapping mapping, ActionForm form,
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
	
		StringBuffer where = new StringBuffer();
		where.append(" where isDel = '0' ");
		where.append(" and unconfig = '0' ");
		String siteName = StaticMethod.null2String(request.getParameter("siteName"));
		String siteNo = StaticMethod.null2String(request.getParameter("siteNo"));

		if(!"".equals(siteName)){
			where.append(" and siteName like'%");
			where.append(siteName);
			where.append("%'");
		}
		if(!"".equals(siteNo)){
			where.append(" and siteNo ='");
			where.append(siteNo);
			where.append("'");
		}		

		ISiteMgr siteMgr = (ISiteMgr) getBean("iSiteMgr");
		Map map = (Map) siteMgr.getSites(pageIndex, pageSize, where.toString());
		List list = (List) map.get("result");
		request.setAttribute(SiteConstants.SITE_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("searchSample");
	}

	
	
    /**
     * 根据需求id得到 需求编号信息 分类 分类名信息 用于ajax调用
     * add by wangjunfeng
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward getSite(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String s = StaticMethod.null2String(request.getParameter("site"));
		ISiteMgr siteMgr = (ISiteMgr) getBean("iSiteMgr");
		String siteNames="";
		String [] siteNo = s.split(",");

		for(int i=0;i<siteNo.length;i++){
			
			StationPoint site = siteMgr.getSite(siteNo[i]);
			StationPointForm siteForm = (StationPointForm) convert(site);
			String siteName = siteForm.getSiteName();
			siteNames +=siteName+",";
		}
		
		String aa=siteNames.substring(0,siteNames.length()-1);
		
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(aa);
	
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
		ISiteMgr siteMgr = (ISiteMgr) getBean("iSiteMgr");
		String[] ids = request.getParameterValues("ids");
		siteMgr.removeSites(ids);

		/*
		 * 批量删除基站时 更新缓存:基站个数-批量删除基站个数
		 * wangjunfeng
		 * 2010-9-6
		 */
//	   	JCS siteCacheOld = JCS.getInstance("site");
//    	siteCacheName = StaticMethod.getNodeName("SYSTEM.cache.siteCache");
//    	siteCache = EomsJCS.getInstance(this.siteCacheName);
//		String gridId ="";
//		for(int i=0;i<ids.length;i++){
//			StationPoint site = siteMgr.getSite(ids[i]);
//			gridId = site.getGridid();
//	    	String siteNo = (String)siteCacheOld.get(gridId); 
//	    	int siteNoNewFlag = Integer.valueOf(siteNo)- 1;
//	    	String siteNoNew = String.valueOf(siteNoNewFlag);
//	    	siteCache.put(gridId, siteNoNew);
//		}
		return search(mapping, form, request, response);
	}
	
	
	/**
	 * 页面二级联动，已知地市，县区，合作伙伴返回对应的网格名称
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changeGrid(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONArray json = new JSONArray();
		JSONObject jitem = new JSONObject();
		
		String region = StaticMethod.null2String(request.getParameter("areaId"));
		String city = StaticMethod.null2String(request.getParameter("city"));

		StringBuffer where = new StringBuffer();
		where.append(" where isDel = '0' "); 
		if(!"".equals(region)){
			where.append(" and region = '");
			where.append(region);
			where.append("'");
		}
		if(!"".equals(city)){
			where.append(" and city = '");
			where.append(city);
			where.append("'");
		}
		IGridMgr gridMgr = (IGridMgr) getBean("iGridMgr");
		List geidList = gridMgr.getGridsByWhere(where.toString());
		
		StringBuffer g_list = new StringBuffer();
		g_list.append("," + "");
		g_list.append("," + "--请选择所属网格--");
		
		for (int i = 0; i < geidList.size(); i++) {
			Gride grid = (Gride)geidList.get(i);
			
			g_list.append("," + grid.getId());
			g_list.append("," + grid.getGridName());
		}
//		存入网格list
		jitem.put("gl", g_list.toString().substring(1));
		json.put(jitem);
		
		response.setCharacterEncoding("utf-8");		
		response.getWriter().write(json.toString());

		return null;
	}
	
	/**
	 * 页面二级联动 联动出合作伙伴
	 * 
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
		String gridId = StaticMethod.null2String(request.getParameter("gridId"));

		StringBuffer where = new StringBuffer();
		where.append(" where id='"+gridId+"'"); 
		
		IGridMgr gridMgr = (IGridMgr) getBean("iGridMgr");
		PartnerDeptMgr pnrDeptMgr = (PartnerDeptMgr) getBean("partnerDeptMgr");
		PartnerDept pd = null;
		List geidList = gridMgr.getGridsByWhere(where.toString());
		
		Gride gride = null;
		if(geidList != null && geidList.size()>0){
			gride = (Gride)geidList.get(0);
			pd = (PartnerDept)pnrDeptMgr.getPartnerDept(gride.getPartnerid());
			jitem.put("id", pd.getInterfaceHeadId());
			jitem.put("name", pd.getName());
			json.put(jitem);
		}
		
		response.setCharacterEncoding("utf-8");		
		response.getWriter().write(json.toString());

		return null;
	}
	
//	/**
//	 * 抽查基站巡检记录（基站列表）
//	 * add by  wangjunfeng
//	 * 2010-9-15
//	 * 分页显示站点信息管理列表
//	 * @param mapping
//	 * @param form
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws Exception
//	 */
//	public ActionForward searchCyc(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//		String pageIndexName = new org.displaytag.util.ParamEncoder(
//				SiteConstants.SITE_LIST)
//				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
//		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
//				.getPageSize();
//		final Integer pageIndex = new Integer(GenericValidator
//				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
//				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
//		
//		String partnerId = request.getParameter("partnerId");
//		
//		java.util.Date currentDate = new java.util.Date();
//		Date checkTime = currentDate;
//		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String timeStr = dateFormat.format(checkTime);
//		String monthStr = timeStr.substring(5, 7);
//		//2010-9-14 17:01:53
//		
//		StringBuffer where = new StringBuffer();
//		where.append("  where site.id = main.siteId ");
//		where.append("  and main.markerIsok = '0' ");
//		where.append("  and to_char(main.checkTime,'MM') ='"+ monthStr +"' ");
//		where.append("  and site.isDel = '0' ");
//		where.append("  and site.unconfig = '0' ");
//		where.append("  and site.partnerid = '"+ partnerId +"' ");
//		
//		String siteName = StaticMethod.null2String(request.getParameter("siteName"));
//		String siteNo = StaticMethod.null2String(request.getParameter("siteNo"));
//		if(!"".equals(siteName)){
//			where.append(" and siteName like'%");
//			where.append(siteName);
//			where.append("%'");
//		}
//		if(!"".equals(siteNo)){
//			where.append(" and siteNo ='");
//			where.append(siteNo);
//			where.append("'");
//		}		
//		
//		
//		ISiteMgr siteMgr = (ISiteMgr) getBean("iSiteMgr");
//		Map map = (Map) siteMgr.getCycSite(pageIndex, pageSize, where.toString());
//		List list = (List) map.get("result");
//		List siteList = new ArrayList();
//		Object[] obj =null;
//		Site site =null;
//		for(int i=0 ;i<list.size();i++){
//			obj = (Object[])list.get(i);
//			site = (Site)obj[0];
//			siteList.add(site);
//		}
//		
//		request.setAttribute(SiteConstants.SITE_LIST, siteList);
//		request.setAttribute("resultSize", map.get("total"));
//		request.setAttribute("pageSize", pageSize);
//		request.setAttribute("partnerId", partnerId);
//		
//		return mapping.findForward("searchCyc");
//	}
//
//    /**
//     * 根据基站id得到 基站名称   用于ajax调用
//     * add by wangjunfeng
//     * 2010-9-15
//     * @param mapping
//     * @param form
//     * @param request
//     * @param response
//     * @return
//     * @throws Exception
//     */
//    public ActionForward getCycSite(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//		
//		String siteId = StaticMethod.null2String(request.getParameter("site"));
//		ISiteMgr siteMgr = (ISiteMgr) getBean("iSiteMgr");
//			
//		Site site = siteMgr.getSite(siteId);
//		SiteForm siteForm = (SiteForm) convert(site);
//		String siteName = siteForm.getSiteName();
//		
//		
//		JSONArray json = new JSONArray();
//		JSONObject jitem = new JSONObject();
//		jitem.put("siteId", siteId);
//		jitem.put("siteName", siteName);
//		json.put(jitem);
//		response.setContentType("text/html;charset=UTF-8");
//		response.getWriter().write(json.toString());
//	
//		return null;
//	}
//	
//	/**
//	 * 基站列表记录
//	 * add by  benweiwei
//	 * 2010-9-15
//	 * 分页显示站点信息管理列表
//	 * @param mapping
//	 * @param form
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws Exception
//	 */
//	public ActionForward searchSite(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//		String pageIndexName = new org.displaytag.util.ParamEncoder(
//				SiteConstants.SITE_LIST)
//				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
//		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
//				.getPageSize();
//		final Integer pageIndex = new Integer(GenericValidator
//				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
//				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
//		
//		String partnerId = request.getParameter("partnerId");
//	
//		StringBuffer where = new StringBuffer();
//		where.append("  where site.isDel = '0' ");
//		where.append("  and site.unconfig = '0' ");
//		where.append("  and site.partnerid = '"+ partnerId +"' ");
//		
//		String siteName = StaticMethod.null2String(request.getParameter("siteName"));
//		String siteNo = StaticMethod.null2String(request.getParameter("siteNo"));
//		if(!"".equals(siteName)){
//			where.append(" and siteName like'%");
//			where.append(siteName);
//			where.append("%'");
//		}
//		if(!"".equals(siteNo)){
//			where.append(" and siteNo ='");
//			where.append(siteNo);
//			where.append("'");
//		}		
//		
//		
//		ISiteMgr siteMgr = (ISiteMgr) getBean("iSiteMgr");
//		Map map = (Map) siteMgr.getSites(pageIndex, pageSize, where.toString());
//		List list = (List) map.get("result");
//		
//		request.setAttribute(SiteConstants.SITE_LIST, list);
//		request.setAttribute("resultSize", map.get("total"));
//		request.setAttribute("pageSize", pageSize);
//		request.setAttribute("partnerId", partnerId);
//		
//		return mapping.findForward("searchCyc");
//	}
//	
}