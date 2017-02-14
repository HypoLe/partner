package com.boco.eoms.partner.baseinfo.webapp.action;

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
import com.boco.eoms.commons.system.area.model.TawSystemArea;
import com.boco.eoms.commons.system.dict.util.DictMgrLocator;
import com.boco.eoms.partner.baseinfo.mgr.IStationMgr;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserAndAreaMgr;
import com.boco.eoms.partner.baseinfo.model.AreaDeptTree;
import com.boco.eoms.partner.baseinfo.model.PartnerUserAndArea;
import com.boco.eoms.partner.baseinfo.model.Station;
import com.boco.eoms.partner.baseinfo.util.PartnerCityByUser;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;
import com.boco.eoms.partner.baseinfo.util.StationConstants;
import com.boco.eoms.partner.baseinfo.webapp.form.StationForm;

/**
 * <p>
 * Title:驻点
 * </p>
 * <p>
 * Description:驻点
 * </p>
 * <p>
 * Fri Dec 18 09:31:48 CST 2009
 * </p>
 * 
 * @moudle.getAuthor() benweiwei
 * @moudle.getVersion() 1.0
 * 
 */
public final class StationAction extends BaseAction {
 
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
	 * 新增驻点
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
/*    	*//**
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
    	List citys = PartnerCityByUser.getCityByProvince(province);    	
    	request.setAttribute("city", citys);

    	
    	return mapping.findForward("edit");
	}
	
	/**
	 * 修改驻点
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
		IStationMgr stationMgr = (IStationMgr) getBean("iStationMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		Station station = stationMgr.getStation(id);
		station.setProvider(station.getPartnerid()+"_"+station.getBigpartnerid());
/*    	*//**
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
    	List citys = PartnerCityByUser.getCityByProvince(province);    	
    	request.setAttribute("city", citys);

		StationForm stationForm = (StationForm) convert(station);
		updateFormBean(mapping, request, stationForm);
		return mapping.findForward("edit");
	}
	
	/**
	 * 保存驻点
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
		IStationMgr stationMgr = (IStationMgr) getBean("iStationMgr");
		String userId = this.getUser(request).getUserid();
		StationForm stationForm = (StationForm) form;
		boolean isNew = (null == stationForm.getId() || "".equals(stationForm.getId()));
		Station station = (Station) convert(stationForm);
		station.setIsDel(Integer.valueOf(0));
		String provider = station.getProvider();
//		新增合作伙伴字段
		if(!"".equals(provider)){
			String[] providers =  provider.split("_");
			station.setPartnerid(providers[0]);
			station.setBigpartnerid(providers[1]);
			station.setProvider(DictMgrLocator.getId2NameService().id2Name(providers[0], "partnerDeptDao"));
		}		
		if (isNew) {
			station.setAddUser(userId);
			station.setAddTime(StaticMethod.getLocalTime());
			stationMgr.saveStation(station);
		} else {
			station.setUpdateTime(StaticMethod.getLocalTime());
			station.setUpdateUser(userId);
			stationMgr.saveStation(station);
			return mapping.findForward("success");
		}
		return mapping.findForward("search");
	}
	
	/**
	 * 删除驻点
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
		String userId = this.getUser(request).getUserid();
		IStationMgr stationMgr = (IStationMgr) getBean("iStationMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		Station station = stationMgr.getStation(id);
		station.setDelTime(StaticMethod.getLocalTime());
		station.setDelUser(userId);
		station.setIsDel(Integer.valueOf(1));
		stationMgr.saveStation(station);
		return mapping.findForward("success");
//		return mapping.findForward("search");
	}
	
	/**
	 * 分页显示驻点列表
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
				StationConstants.STATION_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
 /*   	*//**
    	 * 加载当前用户所属地市
    	 *//*
    	//当前用户名
    	String userid = this.getUser(request).getUserid();
    	List citys = PartnerCityByUser.getCityByUser(userid);
    	request.setAttribute("city", citys);
*/		
		/*
		 * 根据当前省ID，列出所有地市
		 */
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
    	List citys = PartnerCityByUser.getCityByProvince(province);    	
    	request.setAttribute("city", citys);

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

    	
		String stationName = StaticMethod.null2String(request.getParameter("stationName"));
		String region = StaticMethod.null2String(request.getParameter("region"));
		String serviceProfessional = StaticMethod.null2String(request.getParameter("serviceProfessional"));
		String city = StaticMethod.null2String(request.getParameter("city"));
		String provider = StaticMethod.null2String(request.getParameter("provider"));
		IStationMgr stationMgr = (IStationMgr) getBean("iStationMgr");
		StringBuffer where = new StringBuffer();
		where.append(" where isDel=0 ");
		if(!"".equals(stationName)){
			where.append(" and stationName like '%");
			where.append(stationName);
			where.append("%' ");	
		}
/*		if(!"".equals(region)){
			where.append(" and region = '");
			where.append(region);
			where.append("' ");
		}else{
		    for(int i = 0;citys.size()>i;i++){
		    	TawSystemArea tawSystemArea = (TawSystemArea)citys.get(i);
		    	if(i==0){
		    		where.append(" and ( region = '");
		    	}else{
		    		where.append(" or region = '");
		    	}
		    	where.append(tawSystemArea.getAreaid());
		    	where.append("' ");
		    	if(i==citys.size()-1){
		    		where.append(")");
		    	}
		    }
		}
*/
		
		if(!region.equals("") && city.equals("")){
			where.append(" and city in (");
			where.append(countyLast);
			where.append(") ");
			
		}else{
			//不输入地市条件时，查询当前用户权限配置的所有县区
			where.append(" and city in (");
			where.append(countyLast);
			where.append(") ");
		}		

		if(!"".equals(serviceProfessional)){
			where.append(" and serviceProfessional = '");
			where.append(serviceProfessional);
			where.append("' ");
		}
		if(!"".equals(city)){
			where.append(" and city = '");
			where.append(city);
			where.append("' ");
		}
		if(!"".equals(provider)){
//	修改后的查询
			String[] providers =  provider.split("_");	
			where.append(" and partnerid = '");
			where.append(providers[0]);
			where.append("' ");
		}
		Map map = (Map) stationMgr.getStations(pageIndex, pageSize, where.toString());
		List list = (List) map.get("result");
		request.setAttribute(StationConstants.STATION_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");
	}
	/**
	 * 判断驻点名称是否存在ajax
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward validationStation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String staId = request.getParameter("staId");
		String stationName = request.getParameter("stationName");
		String providerName = request.getParameter("providerName");
		IStationMgr stationMgr = (IStationMgr) getBean("iStationMgr");
		Station station = stationMgr.getStation(staId);
		StringBuffer where = new StringBuffer();
		where.append(" where stationName = '");
		where.append(stationName);
		where.append("' and provider = '");
		where.append(providerName);
		where.append("'");
		List list = stationMgr.getStations(where.toString());
		
		// 设置返回页面的信息
		JSONArray json = new JSONArray();
		JSONObject jitem = new JSONObject();
		if(list.size()>0){
			if(StaticMethod.null2String(station.getStationName()).equals(stationName)&&StaticMethod.null2String(station.getProvider()).equals(providerName)){
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
	 * 页面二级联动，已知地市，县区，合作伙伴返回对应的驻点名称
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changeStation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONArray json = new JSONArray();
		JSONObject jitem = new JSONObject();
		
		String areaId = StaticMethod.null2String(request.getParameter("areaId"));
		String deptId = StaticMethod.null2String(request.getParameter("deptId"));
		String city = StaticMethod.null2String(request.getParameter("city"));
		PartnerUserAndAreaMgr partnerUserAndAreaMgr = (PartnerUserAndAreaMgr)getBean("partnerUserAndAreaMgr");
		List areaList = partnerUserAndAreaMgr.listCityIdByCityNodeId(areaId);
		List deptIdList = partnerUserAndAreaMgr.listCityIdByCityNodeId(deptId);
		if(areaList.size()>0){
			AreaDeptTree areaDeptTree = (AreaDeptTree)areaList.get(0);
			areaId = areaDeptTree.getAreaId();
		}
		if(deptIdList.size()>0){
			AreaDeptTree areaDeptTree = (AreaDeptTree)deptIdList.get(0);
			deptId = areaDeptTree.getNodeName();
		}
		StringBuffer where = new StringBuffer();
		where.append(" where isDel = '0' "); 
		if(!"".equals(deptId)){
			where.append(" and provider = '");
			where.append(deptId);
			where.append("'");
		}
		if(!"".equals(areaId)){
			where.append(" and region = '");
			where.append(areaId);
			where.append("'");
		}
		if(!"".equals(city)){
			where.append(" and city = '");
			where.append(city);
			where.append("'");
		}
		IStationMgr stationMgr = (IStationMgr) getBean("iStationMgr");
		List statList = stationMgr.getStations(where.toString());
		
		StringBuffer g_list = new StringBuffer();
		g_list.append("," + "");
		g_list.append("," + "--请选择所属驻点--");
		
		for (int i = 0; i < statList.size(); i++) {
			Station station = (Station)statList.get(i);
			
			g_list.append("," + station.getStationName());
			g_list.append("," + station.getStationName());
		}
//		存入驻点list
		jitem.put("gl", g_list.toString().substring(1));
		json.put(jitem);
		
		response.setCharacterEncoding("utf-8");		
		response.getWriter().write(json.toString());

		return null;
	}

}