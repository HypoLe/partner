package com.boco.eoms.partner.serviceArea.webapp.action;

import java.util.Date;
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
import com.boco.eoms.partner.serviceArea.mgr.ILineMgr;
import com.boco.eoms.partner.serviceArea.model.Grid;
import com.boco.eoms.partner.serviceArea.model.Line;
import com.boco.eoms.partner.serviceArea.util.LineConstants;
import com.boco.eoms.partner.serviceArea.webapp.form.LineForm;

/**
 * <p>
 * Title:线路
 * </p>
 * <p>
 * Description:服务资源配置 线路
 * </p>
 * <p>
 * Fri Nov 13 10:10:56 CST 2009
 * </p>
 * 
 * @moudle.getAuthor() wangjunfeng
 * @moudle.getVersion() 1.0
 * 
 */
public final class LineAction extends BaseAction {
 
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
	 * 新增线路
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
		ILineMgr lineMgr = (ILineMgr) getBean("iLineMgr");
		//地市  取4位 
		String len="4";
		List city = lineMgr.listCityOfArea(areaId,len);
		request.setAttribute("areaId", areaId);
		request.setAttribute("city", city);
*/
    	
	   	/**
    	 * 加载当前用户所属地市
    	 */
    	//当前用户名
//    	String userid = this.getUser(request).getUserid();
//    	List city = PartnerCityByUser.getCityByUser(userid);
    	
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
	 * 修改线路
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
		ILineMgr lineMgr = (ILineMgr) getBean("iLineMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		
/*	   	// 改为2级联动，第一级为地市，第二级为县区，初始化的时候先显示第1级
		PnrServiceAreaIdList pnrMa =(PnrServiceAreaIdList) getBean("pnrServiceAreaIdList");
		String areaId = StaticMethod.nullObject2String(pnrMa.getRootAreaId());
		//地市  取4位 
		String len="4";
		List city = lineMgr.listCityOfArea(areaId,len);
		request.setAttribute("areaId", areaId);
		request.setAttribute("city", city);
*/

    	/**
    	 * 根据当前省ID，列出所有地市
    	 */
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
    	List city = PartnerCityByUser.getCityByProvince(province);    	
    	request.setAttribute("city", city);

		Line line = lineMgr.getLine(id);
		line.setProvider(line.getPartnerid()+"_"+line.getBigpartnerid());
		line.setGrid(line.getGridid());
		LineForm lineForm = (LineForm) convert(line);
		
		//修改人员姓名
    	String userIdModify= this.getUser(request).getUserid();
		lineForm.setUserNameModify(userIdModify);
		
		//修改时间
		String timeModify =StaticMethod.getLocalString();
		lineForm.setTimeModify(timeModify);

		updateFormBean(mapping, request, lineForm);
		return mapping.findForward("edit");
	}

    
    
	/**
	 * 查看线路详细信息
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
		ILineMgr lineMgr = (ILineMgr) getBean("iLineMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		Line line = lineMgr.getLine(id);
		LineForm lineForm = (LineForm) convert(line);
		updateFormBean(mapping, request, lineForm);
		return mapping.findForward("detail");
	}

    
    
	/**
	 * 保存线路
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
		ILineMgr lineMgr = (ILineMgr) getBean("iLineMgr");
		LineForm lineForm = (LineForm) form;

		boolean isNew = (null == lineForm.getId() || "".equals(lineForm.getId()));
		lineForm.setIsDel("0");

		if(isNew){
			//新增人员姓名
	    	String userIdNew= this.getUser(request).getUserid();
			lineForm.setUserNameNew(userIdNew);

			//新增时间
			String timeNew =StaticMethod.getLocalString();
			lineForm.setTimeNew(timeNew);
		}
		
		Line line = (Line) convert(lineForm);
		line.setGridid(line.getGrid());
		line.setGrid(DictMgrLocator.getId2NameService().id2Name(line.getGrid(),"gridDao"));
		String provider = line.getProvider();
//		新增合作伙伴字段
		if(!"".equals(provider)){
			String[] providers =  provider.split("_");
			line.setPartnerid(providers[0]);
			line.setBigpartnerid(providers[1]);
			line.setProvider(DictMgrLocator.getId2NameService().id2Name(providers[0], "partnerDeptDao"));
		}
		
		if(isNew){			
			//判断段落名称是否已经存在
			if(lineMgr.isUnique(line).booleanValue()){
				lineMgr.saveLine(line);
			}else{
				request.setAttribute("failure", "段落名称已经存在");
				//return mapping.findForward("edit");
				return edit(mapping, form, request, response);
			}
		}else{
			lineMgr.saveLine(line);
			return mapping.findForward("success");
		}	

		
		//return search(mapping, lineForm, request, response);
		//避免增加数据后，刷新时还增加数据
		return mapping.findForward("search");

	}
	

	
	/**
	 * 伪删除线路  如果删除 isDel字段设置为1
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward removeIsDel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		ILineMgr lineMgr = (ILineMgr) getBean("iLineMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		
		Line line = lineMgr.getLine(id);
		
		//删除人员姓名
    	String userIdDel= this.getUser(request).getUserid();
    	line.setUserNameDel(userIdDel);
		
		//删除时间
		Date timeDel =StaticMethod.getLocalTime();
		line.setTimeDel(timeDel);
		
		line.setIsDel(Integer.valueOf(1));
		lineMgr.saveLine(line);
		
		return search(mapping, form, request, response);
	}
	
	
	
	/**
	 * 删除线路
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
		
		ILineMgr lineMgr = (ILineMgr) getBean("iLineMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		lineMgr.removeLine(id);
		
		return search(mapping, form, request, response);
	}
	
	
	
	
	/**
	 * 分页显示线路列表
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
				LineConstants.LINE_LIST)
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
	    StringBuffer whereStr = new StringBuffer("  ");
    	whereStr.append(" and line.city in (");
    	whereStr.append(countyLast);
    	whereStr.append(") ");
     	
    	ILineMgr lineMgr = (ILineMgr) getBean("iLineMgr");
		Map map = (Map) lineMgr.getLines(pageIndex, pageSize, whereStr.toString());
		List list = (List) map.get("result");
		request.setAttribute(LineConstants.LINE_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");
	}

	
	
	/**
	 * 按查询条件 分页显示线路列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchX(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				LineConstants.LINE_LIST)
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
	    
//    	StringBuffer whereStr = new StringBuffer("  ");
//    	whereStr.append(" and line.city in (");
//    	whereStr.append(countyLast);
//    	whereStr.append(") ");
 		

		LineForm lineForm =(LineForm) form;
		String whereStr = " and 1=1 ";
		if(lineForm.getLineName()!=null && !"".equals(lineForm.getLineName())){
			whereStr+=" and line.lineName like '%" + lineForm.getLineName()+ "%'";
		}
		
/*		if(lineForm.getRegion()!=null && !"".equals(lineForm.getRegion())){
			whereStr+=" and line.region='"+ lineForm.getRegion() +"'";			
		}else{
		    for(int i = 0;city.size()>i;i++){
		    	TawSystemArea tawSystemArea = (TawSystemArea)city.get(i);
		    	if(i==0){
		    		whereStr+=" and ( line.region = '";
		    	}else{
		    		whereStr+=" or line.region = '";
		    	}
		    	whereStr+=tawSystemArea.getAreaid();
		    	whereStr+="' ";
		    	if(i==city.size()-1){
		    		whereStr+=")";
		    	}
		    }
		}*/
		
		if(lineForm.getRegion()!=null &&!lineForm.getRegion().equals("")){
			whereStr += " and region in ("+lineForm.getRegion()+")";
			
		}
		if(!lineForm.getRegion().equals("") && lineForm.getCity().equals("")){
			whereStr += " and city in ("+countyLast+")";
			
		}else{
			//不输入地市条件时，查询当前用户权限配置的所有县区
			whereStr += " and city in ("+countyLast+")";
		}		

		
		if(lineForm.getCity()!=null && !"".equals(lineForm.getCity())){
			whereStr+=" and line.city='"+lineForm.getCity()+"'";
		}
		if(lineForm.getGrid()!=null && !"".equals(lineForm.getGrid())){
			whereStr+=" and line.gridid='"+lineForm.getGrid()+"'";
		}
		if(lineForm.getProvider()!=null&& !"".equals(lineForm.getProvider())){
//			修改后的查询
			String[] providers =  lineForm.getProvider().split("_");
			whereStr+=" and line.partnerid='"+providers[0]+"'";
		}
		if(lineForm.getIsFullService()!=null && !"".equals(lineForm.getIsFullService())){
			whereStr+=" and line.isFullService='"+lineForm.getIsFullService()+"'";
		}
		if(lineForm.getLineLength()!=null && !"".equals(lineForm.getLineLength())){
			whereStr+=" and line.lineLength='"+lineForm.getLineLength()+"'";
		}
		if(lineForm.getGrade()!=null && !"".equals(lineForm.getGrade())){
			whereStr+=" and line.grade='"+lineForm.getGrade()+"'";
		}
		

		
		ILineMgr lineMgr = (ILineMgr) getBean("iLineMgr");
		Map map = (Map) lineMgr.getLines(pageIndex, pageSize, whereStr);
		List list = (List) map.get("result");
		request.setAttribute(LineConstants.LINE_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");
	}
	

	/**
	 * 判断线路名称是否重复（ajax）
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward validationLineName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String id = request.getParameter("id");
		String lineName = request.getParameter("lineName");
		ILineMgr lineMgr = (ILineMgr) getBean("iLineMgr");
		Line line = lineMgr.getLine(id);
		
		List list = lineMgr.getLineName(lineName);
		
		// 设置返回页面的信息
		JSONArray json = new JSONArray();
		JSONObject jitem = new JSONObject();
		if(list.size()>0){
			if(StaticMethod.null2String(line.getLineName()).equals(lineName)){
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
	 * 
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
		JSONArray json = new JSONArray();
		JSONObject jitem = new JSONObject();

		String cityId = request.getParameter("city");
    	
		String userid = this.getUser(request).getUserid();
//		String countyBuffer = PartnerCityByUser.getCountyByCity(cityId);	
		
		//根据所属地市、用户的县区权限 列出相应县区
		String countyBuffer = PartnerCityByUser.getCountyOfUserRight(userid,cityId);	
		
		
		StringBuffer grid_list = new StringBuffer();
		grid_list.append("," + "");
		grid_list.append("," + "--请选择所属网格--");
		String gridBuffer = grid_list.toString();
		gridBuffer = gridBuffer.substring(1, gridBuffer.length());
		
		// 列出选择当前地市的合作伙伴
		String region = StaticMethod.null2String(request.getParameter("region"));
		String city = StaticMethod.null2String(request.getParameter("city"));
		StringBuffer provider_list = new StringBuffer();
		provider_list.append("," + "");
		provider_list.append("," + "--请选择合作伙伴--");
		ILineMgr lineMgr = (ILineMgr) getBean("iLineMgr");
		List provider = lineMgr.listProviderOfCity(region,city);
		for (int i = 0; i < provider.size(); i++) {
			provider_list.append("," + provider.get(i));
			provider_list.append("," + provider.get(i));
		}
		String providerBuffer = provider_list.toString();
		providerBuffer = providerBuffer.substring(1, providerBuffer.length());

		
		jitem.put("cb", countyBuffer);
		jitem.put("pb", providerBuffer);
		jitem.put("gl", gridBuffer);
		json.put(jitem);
		
		response.setCharacterEncoding("utf-8");		
		
		response.getWriter().write(json.toString());

		
		
		return null;
	}
	
	
	/**
	 * 页面二级联动，已知地市，县区 联动出合作伙伴
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
		String region = StaticMethod.null2String(request.getParameter("region"));
		String city = StaticMethod.null2String(request.getParameter("city"));

		StringBuffer where = new StringBuffer();
		where.append(" where 1=1 "); 
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

		
		// 列出选择当前地市的合作伙伴
		StringBuffer provider_list = new StringBuffer();
		provider_list.append("," + "");
		provider_list.append("," + "--请选择合作伙伴--");

		ILineMgr lineMgr = (ILineMgr) getBean("iLineMgr");
		List provider = lineMgr.listProviderOfCity(region,city);
	
		for (int i = 0; i < provider.size(); i++) {
			Object[] provider1 = (Object[])provider.get(i);
//		修改为新增字段后的设定	
			provider_list.append("," + provider1[1]+"_"+provider1[2]);
			provider_list.append("," + provider1[0]);
		}
		String providerBuffer = provider_list.toString();
		
		providerBuffer = providerBuffer.substring(1, providerBuffer.length());
		
		//为了正常显示网格
		StringBuffer grid_list = new StringBuffer();
		grid_list.append("," + "");
		grid_list.append("," + "--请选择所属网格--");
		String gridBuffer = grid_list.toString();
		gridBuffer = gridBuffer.substring(1, gridBuffer.length());

		jitem.put("pb", providerBuffer);
		jitem.put("gl", gridBuffer);
		json.put(jitem);
		
		response.setCharacterEncoding("utf-8");		
		response.getWriter().write(json.toString());

		return null;
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
		
		String cityId = StaticMethod.null2String(request.getParameter("city"));
		String cityValue = StaticMethod.null2String(request.getParameter("cityValue"));
		String providerValue = StaticMethod.null2String(request.getParameter("providerValue"));

		StringBuffer where = new StringBuffer();
		where.append(" where isDel = '0' "); 
		if(!"".equals(cityValue)){
			where.append(" and city = '");
			where.append(cityValue);
			where.append("'");
		}
		if(!"".equals(cityId)){
			where.append(" and region = '");
			where.append(cityId);
			where.append("'");
		}
		if(!"".equals(providerValue)){
//			providerValue = java.net.URLDecoder.decode(providerValue , "UTF-8"); 
//			修改后的查询
			String[] providers =  providerValue.split("_");
			where.append(" and partnerid = '");
			where.append(providers[0]);
			where.append("'");

		}
		IGridMgr gridMgr = (IGridMgr) getBean("iGridMgr");
		List geidList = gridMgr.getGridsByWhere(where.toString());
		
		StringBuffer g_list = new StringBuffer();
		g_list.append("," + "");
		g_list.append("," + "--请选择所属网格--");
		
		for (int i = 0; i < geidList.size(); i++) {
			Grid grid = (Grid)geidList.get(i);
			
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
	 * 已知地市（不需要县区）， 联动出合作伙伴
	 * 网格维护中用
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changeToPartner(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONArray json = new JSONArray();
		JSONObject jitem = new JSONObject();
		//region地市
		String region = StaticMethod.null2String(request.getParameter("region"));

		StringBuffer where = new StringBuffer();
		where.append(" where 1=1 "); 
		if(!"".equals(region)){
			where.append(" and region = '");
			where.append(region);
			where.append("'");
		}

		
		// 列出选择当前地市的合作伙伴
		StringBuffer provider_list = new StringBuffer();
		provider_list.append("," + "");
		provider_list.append("," + "--请选择合作伙伴--");

		ILineMgr lineMgr = (ILineMgr) getBean("iLineMgr");
		List provider = lineMgr.listProviderByRegion(region);
	
		for (int i = 0; i < provider.size(); i++) {
			
			provider_list.append("," + provider.get(i));
			provider_list.append("," + provider.get(i));
		}
		String providerBuffer = provider_list.toString();
		
		providerBuffer = providerBuffer.substring(1, providerBuffer.length());
		
		//为了正常显示网格
		StringBuffer grid_list = new StringBuffer();
		grid_list.append("," + "");
		grid_list.append("," + "--请选择所属网格--");
		String gridBuffer = grid_list.toString();
		gridBuffer = gridBuffer.substring(1, gridBuffer.length());

		jitem.put("pb", providerBuffer);
		jitem.put("gl", gridBuffer);
		json.put(jitem);
		
		response.setCharacterEncoding("utf-8");		
		response.getWriter().write(json.toString());

		return null;
	}
	
	
	
}