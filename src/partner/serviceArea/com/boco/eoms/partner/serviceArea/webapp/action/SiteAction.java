package com.boco.eoms.partner.serviceArea.webapp.action;

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
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserAndAreaMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerUserAndArea;
import com.boco.eoms.partner.baseinfo.util.PartnerCityByUser;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;
import com.boco.eoms.partner.serviceArea.mgr.ISiteMgr;
import com.boco.eoms.partner.serviceArea.mgr.ISitePapersMgr;
import com.boco.eoms.partner.serviceArea.model.Site;
import com.boco.eoms.partner.serviceArea.util.SiteConstants;
import com.boco.eoms.partner.serviceArea.webapp.form.SiteForm;

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
		 */
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
//    	用siteNo
//		String siteNo = StaticMethod.null2String(request.getParameter("siteNo"));
    	String idSite = StaticMethod.null2String(request.getParameter("idSite"));
		
    	ISiteMgr siteMgr = (ISiteMgr) getBean("iSiteMgr");
		if(!"".equals(idSite)){
	
			Site site = siteMgr.getSite(idSite);
			site.setProvider(site.getPartnerid()+"_"+site.getBigpartnerid());
			site.setGrid(site.getGridid());
			SiteForm siteForm = (SiteForm) convert(site);
			updateFormBean(mapping, request, siteForm);
		}
	
//	   	/**
//    	 * 加载当前用户所属地市
//    	 */
//    	//当前用户名
//    	String userid = this.getUser(request).getUserid();
//    	List city = PartnerCityByUser.getCityByUser(userid);
//    	request.setAttribute("city", city);
		
		/*
		 * 根据当前省ID，列出所有地市
		 */
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
    	List city = PartnerCityByUser.getCityByProvince(province);    	
    	request.setAttribute("city", city);

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
		ISiteMgr siteMgr = (ISiteMgr) getBean("iSiteMgr");
		ISitePapersMgr sitePapersMgr = (ISitePapersMgr) getBean("iSitePapersMgr");
		String userId = this.getUser(request).getUserid();
		SiteForm siteForm = (SiteForm) form;
		boolean isNew = (null == siteForm.getId() || "".equals(siteForm.getId()));
		Site site = (Site) convert(siteForm);
		site.setIsDel(Integer.valueOf(0));
		site.setUnconfig(Integer.valueOf(0));
		
		site.setGridid(site.getGrid());
		site.setGrid(DictMgrLocator.getId2NameService().id2Name(site.getGrid(),"gridDao"));
		String provider = site.getProvider();
//		新增合作伙伴字段
		if(!"".equals(provider)){
			String[] providers =  provider.split("_");
			site.setPartnerid(providers[0]);
			site.setBigpartnerid(providers[1]);
			site.setProvider(DictMgrLocator.getId2NameService().id2Name(providers[0], "partnerDeptDao"));
		}		
		if (isNew) {
			site.setAddTime(StaticMethod.getLocalTime());
			site.setAddUser(userId);
			siteMgr.saveSite(site);
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
			return mapping.findForward("success");
		}
		siteForm.setId(site.getId());
		request.setAttribute("siteName", siteForm.getSiteName());
		request.setAttribute("operType", "save");
		return edit(mapping, siteForm, request, response);
	}
	
	/**
	 * 删除站点信息管理
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
		ISiteMgr siteMgr = (ISiteMgr) getBean("iSiteMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		Site site = siteMgr.getSite(id);
		site.setDelTime(StaticMethod.getLocalTime());
		site.setDelUser(userId);
		site.setIsDel(Integer.valueOf(1));
		siteMgr.saveSite(site);
		
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
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				SiteConstants.SITE_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		
		
/*		// 改为2级联动，第一级为地市，第二级为县区，初始化的时候先显示第1级
		PnrServiceAreaIdList pnrMa =(PnrServiceAreaIdList) getBean("pnrServiceAreaIdList");
		String areaId = StaticMethod.nullObject2String(pnrMa.getRootAreaId());
		ISiteMgr siteMgr = (ISiteMgr) getBean("iSiteMgr");
		//地市  取4位 
		String len="4";
		List citys = siteMgr.listCityOfArea(areaId,len);
		request.setAttribute("areaId", areaId);
		request.setAttribute("city", citys);
*/
		
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
		 */
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
    	List city = PartnerCityByUser.getCityByProvince(province);    	
    	request.setAttribute("city", city);
    	
    	/*
    	 * 用户权限控制中的所属县区
    	 * countyLast 是添加''后的县区ID，便于informix数据库select in
    	 * wangjunfeng
    	 */
    	String userId = this.getUser(request).getUserid();
		PartnerUserAndAreaMgr partnerUserAndAreaMgr = (PartnerUserAndAreaMgr)ApplicationContextHolder
			.getInstance().getBean("partnerUserAndAreaMgr");
		List RightCounty = partnerUserAndAreaMgr.listCountyOfPnrUserArea(userId);
		PartnerUserAndArea partnerUserAndArea = (PartnerUserAndArea)RightCounty.get(0);
		String countys = partnerUserAndArea.getAreaId();
    	String [] countyTem = countys.split(",");
    	StringBuffer countyBuffer = new StringBuffer();
    	for(int i=0;i < countyTem.length ;i++){
    		countyBuffer.append("'" );
    		countyBuffer.append(countyTem[i] );
    		countyBuffer.append("'" );
    		countyBuffer.append("," );
    	}
    	String countyLast = countyBuffer.substring(0, countyBuffer.length()-1).toString();

		
		StringBuffer where = new StringBuffer();
		where.append(" where isDel = '0' ");
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
		String unconfig = StaticMethod.null2String(request.getParameter("unconfig"),"0");
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
		if(!"".equals(unconfig)){
			where.append(" and unconfig ='");
			where.append(unconfig);
			where.append("'");
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
		if(!region.equals("") && county.equals("")){
			where.append(" and city in (");
			where.append(countyLast);
			where.append(") ");
			
		}else{
			//不输入地市条件时，查询当前用户权限配置的所有县区
			where.append(" and city in (");
			where.append(countyLast);
			where.append(") ");
		}		

		if(!"".equals(county)){
			where.append(" and city ='");
			where.append(county);
			where.append("'");
		}		
		if(!"".equals(grid)){
			where.append(" and gridid ='");
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
		 */
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
    	List city = PartnerCityByUser.getCityByProvince(province);    	
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
		String siteId = request.getParameter("siteId");
		String siteNo = request.getParameter("siteNo");
		ISiteMgr siteMgr = (ISiteMgr) getBean("iSiteMgr");
		Site site = siteMgr.getSite(siteId);

		List list = siteMgr.getSitesBySiteNo(siteNo);
		
		// 设置返回页面的信息
		JSONArray json = new JSONArray();
		JSONObject jitem = new JSONObject();
		if(list.size()>0){
			if(StaticMethod.null2String(String.valueOf(site.getSiteNo())).equals(siteNo)){
				jitem.put("message", true);
			}else{
				jitem.put("message", false);
			}
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
			
			Site site = siteMgr.getSite(siteNo[i]);
			SiteForm siteForm = (SiteForm) convert(site);
			String siteName = siteForm.getSiteName();
			siteNames +=siteName+",";
		}
		
		String aa=siteNames.substring(0,siteNames.length()-1);
		
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(aa);
	
		return null;
	}
    

}