package com.boco.eoms.partner.baseinfo.webapp.action;

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
import com.boco.eoms.partner.baseinfo.mgr.IVehicleMgr;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserAndAreaMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerUserAndArea;
import com.boco.eoms.partner.baseinfo.model.Vehicle;
import com.boco.eoms.partner.baseinfo.util.PartnerCityByUser;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;
import com.boco.eoms.partner.baseinfo.util.VehicleConstants;
import com.boco.eoms.partner.baseinfo.webapp.form.VehicleForm;

/**
 * <p>
 * Title:车辆配置
 * </p>
 * <p>
 * Description:资源配置管理 车辆配置
 * </p>
 * <p>
 * Mon Dec 07 19:17:45 CST 2009
 * </p>
 * 
 * @moudle.getAuthor() wangjunfeng
 * @moudle.getVersion() 1.0
 * 
 */
public final class VehicleAction extends BaseAction {
 
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
	 * 新增车辆配置
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
	 * 修改车辆配置
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
		IVehicleMgr vehicleMgr = (IVehicleMgr) getBean("iVehicleMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		
 /*   	*//**
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

		Vehicle vehicle = vehicleMgr.getVehicle(id);
		vehicle.setProvider(vehicle.getPartnerid()+"_"+vehicle.getBigpartnerid());
		VehicleForm vehicleForm = (VehicleForm) convert(vehicle);
		updateFormBean(mapping, request, vehicleForm);
		return mapping.findForward("edit");
	}
	
    
    
    
	/**
	 * 查看车辆配置
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
		IVehicleMgr vehicleMgr = (IVehicleMgr) getBean("iVehicleMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		Vehicle vehicle = vehicleMgr.getVehicle(id);
		VehicleForm vehicleForm = (VehicleForm) convert(vehicle);
		updateFormBean(mapping, request, vehicleForm);
		return mapping.findForward("detail");
	}
    
	/**
	 * 保存车辆配置
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
		IVehicleMgr vehicleMgr = (IVehicleMgr) getBean("iVehicleMgr");
		VehicleForm vehicleForm = (VehicleForm) form;
		boolean isNew = (null == vehicleForm.getId() || "".equals(vehicleForm.getId()));
		vehicleForm.setIsDel("0");
		
		Vehicle vehicle = (Vehicle) convert(vehicleForm);

		/**
		 * 判断车辆配置是否已经存在（所属县区、所属公司、维护专业、用途）
		 */
		//所属县区
		String city = vehicle.getCity();
		//所属公司
		String provider = vehicle.getProvider();
		//维护专业
		String serviceProfession = vehicle.getServiceProfession();
		//用途
		String use = vehicle.getUse();
		StringBuffer whereStr = new StringBuffer(" where 1=1 ");
		whereStr.append(" and city = '");
		whereStr.append(city);
		whereStr.append("' ");
		whereStr.append(" and provider = '");
		whereStr.append(provider);
		whereStr.append("' ");
		whereStr.append(" and serviceProfession = '");
		whereStr.append(serviceProfession);
		whereStr.append("' ");
		whereStr.append(" and use = '");
		whereStr.append(use);
		whereStr.append("' ");
		List isUnique = vehicleMgr.isUnique(whereStr.toString());
		
//		新增合作伙伴字段
		if(!"".equals(provider)){
			String[] providers =  provider.split("_");
			vehicle.setPartnerid(providers[0]);
			vehicle.setBigpartnerid(providers[1]);
			vehicle.setProvider(DictMgrLocator.getId2NameService().id2Name(providers[0], "partnerDeptDao"));
		}		
		if (isNew) {
			
			if(isUnique.size()>0){
				request.setAttribute("failure", "对不起，此条件下的车辆配置已经存在（所属县区、所属公司、维护专业、用途）");
				return edit(mapping, form, request, response);
			}else{
				vehicleMgr.saveVehicle(vehicle);
			}
			
		} else {
			
			
			if(isUnique.size()>0){
				//修改、编辑的时候配置重复的时候提示
				Vehicle vehicleModify =(Vehicle)isUnique.get(0);
				if(vehicleModify.getId().equals(vehicle.getId())){
					vehicleMgr.saveVehicle(vehicle);
				}else{
					request.setAttribute("failure", "对不起，此条件下的车辆配置已经存在（所属县区、所属公司、维护专业、用途）");
					return edit(mapping, form, request, response);
				}
				
			}else{
				//修改后的数据之前没有，直接保存
				vehicleMgr.saveVehicle(vehicle);
			}
			return mapping.findForward("success");
		}
		
		//return search(mapping, vehicleForm, request, response);
		//避免增加数据后，刷新时还增加数据
		return mapping.findForward("search");
		
	}
	
	/**
	 * 删除车辆配置
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
		IVehicleMgr vehicleMgr = (IVehicleMgr) getBean("iVehicleMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		vehicleMgr.removeVehicle(id);
		return mapping.findForward("success");
//		return search(mapping, form, request, response);
	}

	
	/**
	 * 假删除车辆配置（页面删除，数据库保留，假删除后isdel为1）
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
		IVehicleMgr vehicleMgr = (IVehicleMgr) getBean("iVehicleMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		
		Vehicle vehicle = vehicleMgr.getVehicle(id);
		//删除人员姓名
    	String userIdDel= this.getUser(request).getUserid();
    	vehicle.setUserNameDel(userIdDel);
		
		//删除时间
		Date timeDel =StaticMethod.getLocalTime();
		vehicle.setTimeDel(timeDel);
		
		vehicle.setIsDel(Integer.valueOf(1));
		vehicleMgr.saveVehicle(vehicle);
		return mapping.findForward("success");
//		return search(mapping, form, request, response);
	}

	/**
	 * 分页显示车辆配置列表
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
				VehicleConstants.VEHICLE_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
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


		StringBuffer whereStr = new StringBuffer(" where isdel=0 ");	    

/*	    for(int i = 0;city.size()>i;i++){
	    	TawSystemArea tawSystemArea = (TawSystemArea)city.get(i);
	    	if(i==0){
	    		whereStr.append(" and ( vehicle.region = '");
	    	}else{
	    		whereStr.append(" or vehicle.region = '");
	    	}
	    	whereStr.append(tawSystemArea.getAreaid());
	    	whereStr.append("' ");
	    	if(i==city.size()-1){
	    		whereStr.append(")");
	    	}
	    }

*/
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
    	whereStr.append(" and city in (");
    	whereStr.append(countyLast);
    	whereStr.append(") ");

	    
	    
	    IVehicleMgr vehicleMgr = (IVehicleMgr) getBean("iVehicleMgr");
		Map map = (Map) vehicleMgr.getVehicles(pageIndex, pageSize, whereStr.toString());
		List list = (List) map.get("result");
		request.setAttribute(VehicleConstants.VEHICLE_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");
	}

	
	/**
	 * 分页显示车辆配置列表
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
				VehicleConstants.VEHICLE_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		
		
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
    	
	    	
	    /**
	     * 搜索条件
	     * 
	     */
			VehicleForm vehicleForm = (VehicleForm) form;
			StringBuffer whereStr = new StringBuffer(" where isdel=0 ");
			//所属地市
/*			if(vehicleForm.getRegion()!=null && !"".equals(vehicleForm.getRegion())){
				whereStr.append(" and vehicle.region = '");
				whereStr.append(vehicleForm.getRegion());
				whereStr.append("' ");				
			}else{
			    for(int i = 0;city.size()>i;i++){
			    	TawSystemArea tawSystemArea = (TawSystemArea)city.get(i);
			    	if(i==0){
			    		whereStr.append(" and ( vehicle.region = '");
			    	}else{
			    		whereStr.append(" or vehicle.region = '");
			    	}
			    	whereStr.append(tawSystemArea.getAreaid());
			    	whereStr.append("' ");
			    	if(i==city.size()-1){
			    		whereStr.append(")");
			    	}
			    }
			}
*/			
			if(!vehicleForm.getRegion().equals("") && vehicleForm.getCity().equals("")){
				whereStr.append(" and city in (");
				whereStr.append(countyLast);
				whereStr.append(") ");
				
			}else{
				//不输入地市条件时，查询当前用户权限配置的所有县区
				whereStr.append(" and city in (");
				whereStr.append(countyLast);
				whereStr.append(") ");
			}		
		
			
			//所属县区
			if(vehicleForm.getCity()!=null && !"".equals(vehicleForm.getCity())){
				whereStr.append(" and vehicle.city = '");
				whereStr.append(vehicleForm.getCity());
				whereStr.append("' ");
			}
			//所属公司
			if(vehicleForm.getProvider()!=null && !"".equals(vehicleForm.getProvider())){
//				修改后的查询
				String[] providers =  vehicleForm.getProvider().split("_");					
				whereStr.append(" and vehicle.partnerid = '");
				whereStr.append(providers[0]);
				whereStr.append("' ");
			}
			//维护专业
			if(vehicleForm.getServiceProfession()!=null && !"".equals(vehicleForm.getServiceProfession())){
				whereStr.append(" and vehicle.serviceProfession = '");
				whereStr.append(vehicleForm.getServiceProfession());
				whereStr.append("' ");
			}
		
			
		IVehicleMgr vehicleMgr = (IVehicleMgr) getBean("iVehicleMgr");	
		Map map = (Map) vehicleMgr.getVehicles(pageIndex, pageSize, whereStr.toString());
		List list = (List) map.get("result");
		request.setAttribute(VehicleConstants.VEHICLE_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");
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

		String countyBuffer= PartnerCityByUser.getCountyByCity(cityId);
		String providerBuffer =PartnerCityByUser.getProviderByCity(cityId);
		
		jitem.put("cb", countyBuffer);
		jitem.put("pb", providerBuffer);
		//jitem.put("gl", gridBuffer);
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
	public ActionForward changeStation(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JSONArray json = new JSONArray();
		JSONObject jitem = new JSONObject();
		String cityId = request.getParameter("city");

		String countyBuffer= PartnerCityByUser.getCountyByCity(cityId);
		String providerBuffer =PartnerCityByUser.getProviderByCity(cityId);
		
		jitem.put("cb", countyBuffer);
		jitem.put("pb", providerBuffer);
		//jitem.put("gl", gridBuffer);
		json.put(jitem);
		
		response.setCharacterEncoding("utf-8");		
		response.getWriter().write(json.toString());

		return null;
	}
	

	/**
	 * 分页显示车辆配置列表，支持Atom方式接入Portal
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
/*	public ActionForward search4Atom(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			// --------------用于分页，得到当前页号-------------
			final Integer pageIndex = new Integer(request
					.getParameter("pageIndex"));
			final Integer pageSize = new Integer(request
					.getParameter("pageSize"));
			IVehicleMgr vehicleMgr = (IVehicleMgr) getBean("iVehicleMgr");
			Map map = (Map) vehicleMgr.getVehicles(pageIndex, pageSize, "");
			List list = (List) map.get("result");
			Vehicle vehicle = new Vehicle();
			
			//创建ATOM源
			Factory factory = Abdera.getNewFactory();
			Feed feed = factory.newFeed();
			
			// 分页
			for (int i = 0; i < list.size(); i++) {
				vehicle = (Vehicle) list.get(i);
				
				// TODO 请按照下面的实例给entry赋值
				Entry entry = feed.insertEntry();
				entry.setTitle("<a href='" + request.getScheme() + "://"
						+ request.getServerName() + ":"
						+ request.getServerPort()
						+ request.getContextPath()
						+ "/vehicle/vehicles.do?method=edit&id="
						+ vehicle.getId() + "' target='_blank'>"
						+ display name for list + "</a>");
				entry.setSummary(summary);
				entry.setContent(content);
				entry.setLanguage(language);
				entry.setText(text);
				entry.setRights(tights);
				
				// 以下三项需要传入Date类型或String类型（yyyy-MM-dd）的参数
				entry.setUpdated(new java.util.Date());
				entry.setPublished(new java.util.Date());
				entry.setEdited(new java.util.Date());
				
				// 为person的name属性赋值，entry.addAuthor可以随意赋值
				Person person = entry.addAuthor(userId);
				person.setName(userName);
			}
			
			// 每页显示条数
			feed.setText(map.get("total").toString());
		    OutputStream os = response.getOutputStream();
		    PrintStream ps = new PrintStream(os);
		    feed.getDocument().writeTo(ps);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}*/
}