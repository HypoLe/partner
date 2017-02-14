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
import com.boco.eoms.partner.serviceArea.mgr.IPointMgr;
import com.boco.eoms.partner.serviceArea.model.Point;
import com.boco.eoms.partner.serviceArea.util.PointConstants;
import com.boco.eoms.partner.serviceArea.webapp.form.PointForm;

/**
 * <p>
 * Title:数据点
 * </p>
 * <p>
 * Description:服务资源配置 数据点
 * </p>
 * <p>
 * Mon Nov 23 11:36:10 CST 2009
 * </p>
 * 
 * @moudle.getAuthor() wangjunfeng
 * @moudle.getVersion() 1.0
 * 
 */
public final class PointAction extends BaseAction {
 
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
	 * 新增数据点
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
	 * 修改数据点
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
		IPointMgr pointMgr = (IPointMgr) getBean("iPointMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		
		/*
		 * 根据当前省ID，列出所有地市
		 */
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
    	List city = PartnerCityByUser.getCityByProvince(province);    	
    	request.setAttribute("city", city);
		
		Point point = pointMgr.getPoint(id);
		point.setProvider(point.getPartnerid()+"_"+point.getBigpartnerid());
		point.setGrid(point.getGridid());
		PointForm pointForm = (PointForm) convert(point);
		
		//修改人员姓名
    	String userIdModify= this.getUser(request).getUserid();
    	pointForm.setUserNameModify(userIdModify);
		
		//修改时间
		String timeModify =StaticMethod.getLocalString();
		pointForm.setTimeModify(timeModify);

		
		updateFormBean(mapping, request, pointForm);
		return mapping.findForward("edit");
	}

    
    
	/**
	 * 查看数据点详细信息
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
		IPointMgr pointMgr = (IPointMgr) getBean("iPointMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		Point point = pointMgr.getPoint(id);
		PointForm pointForm = (PointForm) convert(point);
		updateFormBean(mapping, request, pointForm);
		return mapping.findForward("detail");
	}

    
	/**
	 * 保存数据点
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
		IPointMgr pointMgr = (IPointMgr) getBean("iPointMgr");
		PointForm pointForm = (PointForm) form;
		boolean isNew = (null == pointForm.getId() || "".equals(pointForm.getId()));
		
		pointForm.setIsDel("0");
		
		if(isNew){
			//新增人员账号
	    	String userIdNew= this.getUser(request).getUserid();
	    	pointForm.setUserNameNew(userIdNew);

			//新增时间
			String timeNew =StaticMethod.getLocalString();
			pointForm.setTimeNew(timeNew);

		}
		
		Point point = (Point) convert(pointForm);
		point.setGridid(point.getGrid());
		point.setGrid(DictMgrLocator.getId2NameService().id2Name(point.getGrid(),"gridDao"));
		String provider = point.getProvider();
//		新增合作伙伴字段
		if(!"".equals(provider)){
			String[] providers =  provider.split("_");
			point.setPartnerid(providers[0]);
			point.setBigpartnerid(providers[1]);
			point.setProvider(DictMgrLocator.getId2NameService().id2Name(providers[0], "partnerDeptDao"));
		}
		if (isNew) {
			pointMgr.savePoint(point);
		} else {
			pointMgr.savePoint(point);
			return mapping.findForward("success");
		}
		
		//return search(mapping, pointForm, request, response);
		//避免增加数据后，刷新时还增加数据
		return mapping.findForward("search");

	}

	
	/**
	 * 伪删除数据点  如果删除 isDel字段设置为1
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
		IPointMgr pointMgr = (IPointMgr) getBean("iPointMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		
		Point point = pointMgr.getPoint(id);
		
		//删除人员姓名
    	String userIdDel= this.getUser(request).getUserid();
    	point.setUserNameDel(userIdDel);
		
		//删除时间
		Date timeDel =StaticMethod.getLocalTime();
		point.setTimeDel(timeDel);
		
		point.setIsDel(Integer.valueOf(1));
		pointMgr.savePoint(point);

		
		
		return search(mapping, form, request, response);
	}

	
	
	/**
	 * 删除数据点
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
		IPointMgr pointMgr = (IPointMgr) getBean("iPointMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		pointMgr.removePoint(id);
		return search(mapping, form, request, response);
	}
	
	/**
	 * 分页显示数据点列表
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
				PointConstants.POINT_LIST)
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
	    StringBuffer whereStr = new StringBuffer(" ");
    	whereStr.append(" and city in (");
    	whereStr.append(countyLast);
    	whereStr.append(") ");
    	
/*	    for(int i = 0;city.size()>i;i++){
	    	TawSystemArea tawSystemArea = (TawSystemArea)city.get(i);
	    	if(i==0){
	    		whereStr.append(" and (region = '");
	    	}else{
	    		whereStr.append(" or region = '");
	    	}
	    	whereStr.append(tawSystemArea.getAreaid());
	    	whereStr.append("' ");
	    	if(i==city.size()-1){
	    		whereStr.append(")");
	    	}
	    }
*/    	
    	
    	IPointMgr pointMgr = (IPointMgr) getBean("iPointMgr");
		Map map = (Map) pointMgr.getPoints(pageIndex, pageSize, whereStr.toString());
		List list = (List) map.get("result");
		request.setAttribute(PointConstants.POINT_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");
	}

	
	
	/**
	 * 按条件搜索 分页显示数据点列表
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
				PointConstants.POINT_LIST)
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
	    StringBuffer whereStr = new StringBuffer("   ");
    	whereStr.append(" and city in (");
    	whereStr.append(countyLast);
    	whereStr.append(") ");
    	
		
		PointForm pointForm = (PointForm) form;
		
		//数据点名称
		if(pointForm.getPointName()!=null &&!"".equals(pointForm.getPointName()) ){
			whereStr.append(" and point.pointName like '%");
			whereStr.append(pointForm.getPointName());
			whereStr.append("%' ");
		}

		//所属地市
/*		if(pointForm.getRegion()!=null &&!"".equals(pointForm.getRegion())){
			whereStr.append(" and point.region = '");
			whereStr.append(pointForm.getRegion());
			whereStr.append("' ");
		}else{
		    for(int i = 0;city.size()>i;i++){
		    	TawSystemArea tawSystemArea = (TawSystemArea)city.get(i);
		    	if(i==0){
		    		whereStr.append(" and ( region = '");
		    	}else{
		    		whereStr.append(" or region = '");
		    	}
		    	whereStr.append(tawSystemArea.getAreaid());
		    	whereStr.append("' ");
		    	if(i==city.size()-1){
		    		whereStr.append(")");
		    	}
		    }
		}
*/		
		//所属县区
		if(pointForm.getRegion()!=null && !"".equals(pointForm.getRegion())){
			whereStr.append(" and point.region = '");
			whereStr.append(pointForm.getRegion());
			whereStr.append("' ");
		}
		if(!pointForm.getRegion().equals("") && pointForm.getCity().equals("")){
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
		if(pointForm.getCity()!=null && !"".equals(pointForm.getCity())){
			whereStr.append(" and point.city = '");
			whereStr.append(pointForm.getCity());
			whereStr.append("' ");
		}

		//所属网格
		if(pointForm.getGrid()!=null &&!"".equals(pointForm.getGrid())){
			whereStr.append(" and point.gridid = '");
			whereStr.append(pointForm.getGrid());
			whereStr.append("' ");
		}

		//合作伙伴
		if(pointForm.getProvider()!=null &&!"".equals(pointForm.getProvider())){
//			修改后的查询
			String[] providers =  pointForm.getProvider().split("_");
			whereStr.append(" and point.partnerid = '");
			whereStr.append(providers[0]);
			whereStr.append("' ");
		}
		
/*		*//**
		 * 页面加载所在地市
		 *//*	
		// 改为2级联动，第一级为地市，第二级为县区，初始化的时候先显示第1级
		PnrServiceAreaIdList pnrMa =(PnrServiceAreaIdList) getBean("pnrServiceAreaIdList");
		String areaId = StaticMethod.nullObject2String(pnrMa.getRootAreaId());
		IPointMgr pointMgr = (IPointMgr) getBean("iPointMgr");
		//地市  取4位 
		String len="4";
		List city = pointMgr.listCityOfArea(areaId,len);
		request.setAttribute("areaId", areaId);
		request.setAttribute("city", city);
*/		
		IPointMgr pointMgr = (IPointMgr) getBean("iPointMgr");
		Map map = (Map) pointMgr.getPoints(pageIndex, pageSize, whereStr.toString());
		List list = (List) map.get("result");
		request.setAttribute(PointConstants.POINT_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");
	}


	/**
	 * 判断数据点名称是否重复
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward validationPointName(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String id = request.getParameter("id");
		String pointName = request.getParameter("pointName");
		IPointMgr pointMgr = (IPointMgr) getBean("iPointMgr");
		Point point = pointMgr.getPoint(id);
		
		List list = pointMgr.getPointName(pointName);
		
		// 设置返回页面的信息
		JSONArray json = new JSONArray();
		JSONObject jitem = new JSONObject();
		if(list.size()>0){
			if(StaticMethod.null2String(point.getPointName()).equals(pointName)){
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

		String cityId = request.getParameter("city");
		
		String countyBuffer = PartnerCityByUser.getCountyByCity(cityId);
		
		response.setContentType("text/html; charset=GBK");
		response.getWriter().print(countyBuffer);
		response.getWriter().flush();
		
		
		
		return null;
	}

	
	/**
	 * 分页显示数据点列表，支持Atom方式接入Portal
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
			PointMgr pointMgr = (PointMgr) getBean("iPointMgr");
			Map map = (Map) pointMgr.getPoints(pageIndex, pageSize, "");
			List list = (List) map.get("result");
			Point point = new Point();
			
			//创建ATOM源
			Factory factory = Abdera.getNewFactory();
			Feed feed = factory.newFeed();
			
			// 分页
			for (int i = 0; i < list.size(); i++) {
				point = (Point) list.get(i);
				
				// TODO 请按照下面的实例给entry赋值
				Entry entry = feed.insertEntry();
				entry.setTitle("<a href='" + request.getScheme() + "://"
						+ request.getServerName() + ":"
						+ request.getServerPort()
						+ request.getContextPath()
						+ "/point/points.do?method=edit&id="
						+ point.getId() + "' target='_blank'>"
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
	}
*/}