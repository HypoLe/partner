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
import com.boco.eoms.partner.serviceArea.mgr.IGridMgr;
import com.boco.eoms.partner.serviceArea.model.Grid;
import com.boco.eoms.partner.serviceArea.util.GridConstants;
import com.boco.eoms.partner.serviceArea.webapp.form.GridForm;

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
    	
/*    	// 改为2级联动，第一级为地市，第二级为县区，初始化的时候先显示第1级
		PnrServiceAreaIdList pnrMa =(PnrServiceAreaIdList) getBean("pnrServiceAreaIdList");
		String areaId = StaticMethod.nullObject2String(pnrMa.getRootAreaId());
		IGridMgr gridMgr = (IGridMgr) getBean("iGridMgr");
		//地市  取4位 
		String len="4";
		List city = gridMgr.listCityOfArea(areaId,len);
		request.setAttribute("areaId", areaId);
		request.setAttribute("city", city);
*/
    	
/*	   	注释：根绝当前用户列出地市，改为列出所有地市 2010-4-6 王峻峰
    	/**
    	 * 加载当前用户所属地市
    	 *//*
    	//当前用户名
    	String userid = this.getUser(request).getUserid();
    	List city = PartnerCityByUser.getCityByUser(userid);
*/    	
    	
    	/**
    	 * 根据当前省ID，列出所有地市
    	 */
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
    	List city = PartnerCityByUser.getCityByProvince(province);    	
    	
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
    	/**
    	 * 根据当前省ID，列出所有地市
    	 */
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
    	List city = PartnerCityByUser.getCityByProvince(province);    	
    	
    	request.setAttribute("city", city);

//    	bww 新增字段时的修改2
		Grid grid = gridMgr.getGrid(id);
		grid.setProvider(grid.getPartnerid()+"_"+grid.getBigpartnerid());
		GridForm gridForm = (GridForm) convert(grid);
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
		Grid grid = gridMgr.getGrid(id);
		GridForm gridForm = (GridForm) convert(grid);
		updateFormBean(mapping, request, gridForm);
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
		GridForm gridForm = (GridForm) form;
		boolean isNew = (null == gridForm.getId() || "".equals(gridForm.getId()));
		Grid grid = (Grid) convert(gridForm);
		grid.setIsDel(Integer.valueOf(0));
		String provider = grid.getProvider();
//		新增合作伙伴字段
		if(!"".equals(provider)){
			String[] providers =  provider.split("_");
			grid.setPartnerid(providers[0]);
			grid.setBigpartnerid(providers[1]);
			grid.setProvider(DictMgrLocator.getId2NameService().id2Name(providers[0], "partnerDeptDao"));
		}
//		DeliveryRequestConvertBO deliveryRequestConvertBO = new DeliveryRequestConvertBO();
		if (isNew) {
			grid.setAddTime(StaticMethod.getLocalTime());
			grid.setAddUser(userId);
			gridMgr.saveGrid(grid);
//			deliveryRequestConvertBO.getGrid("1", grid);//接口同步-新增
		} else {
			grid.setUpdateTime(StaticMethod.getLocalTime());
			grid.setUpdateUser(userId);
			gridMgr.saveGrid(grid);
//			deliveryRequestConvertBO.getGrid("3", grid);//接口同步-修改
			return mapping.findForward("success");
		}
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
		IGridMgr gridMgr = (IGridMgr) getBean("iGridMgr");
//		DeliveryRequestConvertBO deliveryRequestConvertBO = new DeliveryRequestConvertBO();
		String id = StaticMethod.null2String(request.getParameter("id"));
		Grid grid = gridMgr.getGrid(id);
		grid.setDelTime(StaticMethod.getLocalTime());
		grid.setDelUser(userId);
		grid.setIsDel(Integer.valueOf(1));
		gridMgr.saveGrid(grid);
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
   	
		/*
		 * 根据当前省ID，列出所有地市
		 */
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
    	//地市city
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
	    StringBuffer whereStr = new StringBuffer(" where isDel = '0'  ");
    	whereStr.append(" and city in (");
    	whereStr.append(countyLast);
    	whereStr.append(") ");
    	
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
		String gridId = request.getParameter("gridId");
		String gridName = request.getParameter("gridName");
		IGridMgr gridMgr = (IGridMgr) getBean("iGridMgr");
		Grid grid = gridMgr.getGrid(gridId);

		List list = gridMgr.getGridsByGridName(gridName);
		
		// 设置返回页面的信息
		JSONArray json = new JSONArray();
		JSONObject jitem = new JSONObject();
		if(list.size()>0){
			if(StaticMethod.null2String(grid.getGridName()).equals(gridName)){
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
	 * 按条件显示网格列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	/*public ActionForward searchGridList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				GridSatisfactionConstants.GRIDSATISFACTION_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		
		
		 * 根据当前省ID，列出所有地市
		 
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
    	List city = PartnerCityByUser.getCityByProvince(province);    	
    	request.setAttribute("city", city);
    	
    	
    	 * 用户权限控制中的所属县区
    	 * countyLast 是添加''后的县区ID，便于informix数据库select in
    	 * wangjunfeng
    	 
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
    	
    	
		IGridMgr gridMgr = (IGridMgr) getBean("iGridMgr");
		String gridName = StaticMethod.null2String(request.getParameter("gridName"));
		String region = StaticMethod.null2String(request.getParameter("region"));
		String county = StaticMethod.null2String(request.getParameter("city"));
		
		String provider = StaticMethod.null2String(request.getParameter("provider"));
		StringBuffer where = new StringBuffer();
		where.append(" where isDel = '0'");
		if(!gridName.equals("")){
			where.append(" and gridName like '%");
			where.append(gridName);
			where.append("%'");
		}
		if(!region.equals("")){
			where.append(" and region = '");
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
		if(!provider.equals("")){
//			修改后的查询
			String[] providers =  provider.split("_");
			where.append(" and partnerid = '");
			where.append(providers[0]);
			where.append("'");
		}
		if(!county.equals("")){
			where.append(" and city = '");
			where.append(county);
			where.append("'");
		}
		
	
		Map map = (Map) gridMgr.getGrids(pageIndex, pageSize, where.toString());
		List list = (List) map.get("result");
		request.setAttribute(GridConstants.GRID_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");
	}		
	*/
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
	
	
}