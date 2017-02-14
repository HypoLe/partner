package com.boco.eoms.partner.serviceArea.webapp.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import utils.Nop3Constants;
import utils.Nop3Utils;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.loging.BocoLog;
import com.boco.eoms.commons.system.area.dao.TawSystemAreaDao;
import com.boco.eoms.commons.system.area.model.TawSystemArea;
import com.boco.eoms.commons.system.area.service.ITawSystemAreaManager;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.dict.service.Name2IDService;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.ui.util.JSONUtil;
import com.boco.eoms.deviceManagement.faultInfo.utils.CommonUtils;
import com.boco.eoms.partner.baseinfo.mgr.AreaDeptTreeMgr;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserAndAreaMgr;
import com.boco.eoms.partner.baseinfo.model.AreaDeptTree;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.baseinfo.model.PartnerUserAndArea;
import com.boco.eoms.partner.baseinfo.util.AreaDeptTreeConstants;
import com.boco.eoms.partner.baseinfo.util.PartnerCityByUser;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;
import com.boco.eoms.partner.baseinfo.util.RoleIdList;
import com.boco.eoms.partner.serviceArea.mgr.IResidentSiteMgr;
import com.boco.eoms.partner.serviceArea.model.ResidentSite;
import com.boco.eoms.partner.serviceArea.util.ResidentSiteConstants;
import com.boco.eoms.partner.serviceArea.webapp.form.ResidentSiteForm;
import com.google.common.base.Strings;

/**
 * <p>
 * Title:驻点信息管理
 * </p>
 * <p>
 * Description:驻点信息管理
 * </p>
 * <p>
 * Tue Nov 17 17:51:41 CST 2009
 * </p>
 * 
 * @moudle.getVersion() 1.0
 * 
 */
public final class ResidentSiteAction extends BaseAction {
 
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
	 * 新增驻点信息
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
	 * 修改驻点信息管理
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
    	String id = StaticMethod.null2String(request.getParameter("id"));
    	IResidentSiteMgr siteMgr = (IResidentSiteMgr) getBean("iResidentSiteMgr");
		if(!"".equals(id)){
			ResidentSite site = siteMgr.getResidentSite(id);
//			String partnerId = site.getPartnerId();
//			PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) getBean("partnerDeptMgr");
//			PartnerDept partnerDept = partnerDeptMgr.getPartnerDept(partnerId);
//			request.setAttribute("partnerNodeId", partnerDept.getTreeNodeId());
//			ID2NameService id2NameMgr = (ID2NameService) getBean("id2nameService");
//			String dutyManData = "{id:'"+site.getDutyMan()+"',name:'"+id2NameMgr.id2Name(site.getDutyMan(), "tawSystemUserDao")+"',nodeType:'user'}";
//			request.setAttribute("dutyManData", dutyManData);
			ResidentSiteForm siteForm = (ResidentSiteForm) convert(site);
			request.setAttribute("residentSiteForm", siteForm);
			request.setAttribute("site", site);
			request.setAttribute("type", request.getParameter("type"));
			return mapping.findForward("edit");
		}else {
			return null;
		}
	}
	
	/**
	 * 保存驻点信息管理
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
		IResidentSiteMgr siteMgr = (IResidentSiteMgr) getBean("iResidentSiteMgr");
		String userId = this.getUser(request).getUserid();
		ResidentSiteForm siteForm = (ResidentSiteForm) form;
		boolean isNew = (null == siteForm.getId() || "".equals(siteForm.getId()));
		ResidentSite site = (ResidentSite) convert(siteForm);
		site.setIsDel("0");
		site.setCity(request.getParameter("city"));
		site.setCountry(request.getParameter("country"));
		site.setMonitorCompany(request.getParameter("monitorCompany"));
		site.setDutyMan(request.getParameter("dutyMan"));
		
		if (isNew) {
			site.setCreateTime(CommonUtils.toEomsStandardDate(new Date()));
			site.setCreateUser(userId);
			siteMgr.saveResidentSite(site);
		}
		return this.search(mapping, form, request, response);
	}
	
	/**
	 * 删除驻点信息管理
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
		IResidentSiteMgr siteMgr = (IResidentSiteMgr) getBean("iResidentSiteMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		ResidentSite site = siteMgr.getResidentSite(id);
		site.setDelTime(StaticMethod.getLocalString());
		site.setDelUser(userId);
		site.setIsDel("1");
		siteMgr.saveResidentSite(site);
		
		return search(mapping, form, request, response);
	}
	
	/**
	 * 分页显示驻点信息管理列表
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
				ResidentSiteConstants.RESIDENTSITE_LIST)
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
    	List citys = PartnerCityByUser.getCityByProvince(province);    	
    	request.setAttribute("city", citys);
    	
    	/*
    	 * 用户权限控制中的所属县区
    	 * countyLast 是添加''后的县区ID，便于informix数据库select in
    	 * wangjunfeng
    	 */
    	String userId = this.getUser(request).getUserid();
//		PartnerUserAndAreaMgr partnerUserAndAreaMgr = (PartnerUserAndAreaMgr)ApplicationContextHolder
//			.getInstance().getBean("partnerUserAndAreaMgr");
//		List RightCounty = partnerUserAndAreaMgr.listCountyOfPnrUserArea(userId);
//		PartnerUserAndArea partnerUserAndArea = (PartnerUserAndArea)RightCounty.get(0);
//		
//		String countys = partnerUserAndArea.getAreaId();
//    	String [] countyTem = countys.split(",");
//    	StringBuffer countyBuffer = new StringBuffer();
//    	for(int i=0;i < countyTem.length ;i++){
//    		countyBuffer.append("'" );
//    		countyBuffer.append(countyTem[i] );
//    		countyBuffer.append("'" );
//    		countyBuffer.append("," );
//    	}
//    	String countyLast = countyBuffer.substring(0, countyBuffer.length()-1).toString();

		
		StringBuffer where = new StringBuffer();
		where.append(" where isDel = '0' ");
		String residentSiteName = StaticMethod.null2String(request.getParameter("residentSiteName"));
		String residentSiteNo = StaticMethod.null2String(request.getParameter("residentSiteNo"));
		String city = StaticMethod.null2String(request.getParameter("city"));
		String county = StaticMethod.null2String(request.getParameter("country"));
		if(!"".equals(residentSiteName)){
			where.append(" and residentSiteName like'%");
			where.append(residentSiteName);
			where.append("%'");
		}
		if(!"".equals(residentSiteNo)){
			where.append(" and county ='%");
			where.append(county);
			where.append("%'");
		}		
		if(!"".equals(city)){
			where.append(" and city ='%");
			where.append(city);
			where.append("%'");
		}

		
		IResidentSiteMgr siteMgr = (IResidentSiteMgr) getBean("iResidentSiteMgr");
		Map map = (Map) siteMgr.getResidentSites(pageIndex, pageSize, where.toString());
		List list = (List) map.get("result");
		request.setAttribute(ResidentSiteConstants.RESIDENTSITE_LIST, list);
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
		IResidentSiteMgr siteMgr = (IResidentSiteMgr) getBean("iResidentSiteMgr");
		ResidentSite site = siteMgr.getResidentSite(siteId);

		List list = siteMgr.getResidentSitesByResidentSiteNo(siteNo);
		
		// 设置返回页面的信息
		JSONArray json = new JSONArray();
		JSONObject jitem = new JSONObject();
		if(list.size()>0){
			if(StaticMethod.null2String(String.valueOf(site.getResidentSiteNo())).equals(siteNo)){
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
	 * 得到地域部门树树JSON数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String selectPartner(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String nodeId = StaticMethod.null2String(request.getParameter("node"));
		JSONArray jsonRoot = new JSONArray();
		AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");
		ITawSystemDeptManager iTawSystemDeptManager = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
		// 根据登录用户的权限取下级节点
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
				.getSession().getAttribute("sessionform");
		PartnerUserAndAreaMgr partnerUserAndAreaMgr = (PartnerUserAndAreaMgr) getBean("partnerUserAndAreaMgr");
		String operuserid = sessionform.getUserid();
		String operDeptId = sessionform.getDeptid();
		String deptTyep = operDeptId.substring(0, 1);
		PartnerUserAndArea object = partnerUserAndAreaMgr
				.getObjectByUserId(operuserid);
		// 2010-8-5 显示当前用户所属大合作伙伴下的所有合作伙伴
		PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) getBean("partnerDeptMgr");
		List partnerList = partnerDeptMgr.getPartnerDepts(" and deptMagId = "
				+ operDeptId);
		PartnerDept partnerDept = null;
		if (partnerList.size() > 0) {
			partnerDept = (PartnerDept) partnerList.get(0);
		}
		AreaDeptTree tree = areaDeptTreeMgr.getAreaDeptTreeByNodeId(nodeId);
		List list = new ArrayList();
		List list2 = new ArrayList();
		if (operuserid.equals("admin") || "1".equals(deptTyep)) {
			list2 = areaDeptTreeMgr.getNextLevelAreaDeptTrees(nodeId);
		} else if (object != null) {
			if (tree.getId() != null && tree.getNodeType().equals("province")
					&& !object.getAreaType().equals("province")) {
				// String[] areas = object.getAreaNames().split(",");
				partnerDept = (PartnerDept) partnerDeptMgr
						.getPartnerDept(operDeptId);
				String[] areas = object.getCityId().split(",");
				String area = "";
				for (int i = 0; i < areas.length; i++) {
					if (i > 0) {
						area += ",";
					}
					area += ("'" + areas[i] + "'");
				}
				list = areaDeptTreeMgr.getNextLevelAreaDeptTrees(nodeId, area);
				System.out.println(partnerDept.getName() + "1");
			} else {
				partnerDept = (PartnerDept) partnerDeptMgr
						.getPartnerDept(operDeptId);
				System.out.println(partnerDept.getName());
				list = areaDeptTreeMgr.getNextLevelAreaDeptTrees(nodeId,
						partnerDept.getAreaId());
			}
			if (nodeId.length() > 3 && "2".equals(deptTyep)) {
				AreaDeptTree areaDeptTree = null;
				if (partnerDept != null) {
					for (int i = 0; list.size() > i; i++) {
						areaDeptTree = (AreaDeptTree) list.get(i);
						if (partnerDept.getInterfaceHeadId().equals(
								areaDeptTree.getInterfaceHeadId())) {
							list2.add(areaDeptTree);
						}
					}
				}
			} else {
				list2 = list;
			}
		} else if (object == null) {
			list = areaDeptTreeMgr.getNextLevelAreaDeptTrees(nodeId);

			TawSystemDept tawSystemDept = (TawSystemDept) iTawSystemDeptManager
					.getDeptinfobydeptid(operDeptId, "0");

			if (nodeId.length() > 3 && "2".equals(deptTyep)&&operDeptId.equals(tawSystemDept.getDeptId())) {
				AreaDeptTree areaDeptTree = null;
				if (partnerDept != null) {
					for (int i = 0; list.size() > i; i++) {
						areaDeptTree = (AreaDeptTree) list.get(i);
						if (partnerDept.getInterfaceHeadId().equals(
								areaDeptTree.getInterfaceHeadId())&&areaDeptTree.getNodeName().equals(sessionform.getDeptname())
								) {
							list2.add(areaDeptTree);
						}
					}
				}
			} else {
				list2 = list;
			}
		}

		RoleIdList roleIdList = (RoleIdList) getBean("roleIdList");

		for (Iterator nodeIter = list2.iterator(); nodeIter.hasNext();) {
			AreaDeptTree areaDeptTree = (AreaDeptTree) nodeIter.next();
			JSONObject jitem = new JSONObject();
			jitem.put("id", areaDeptTree.getNodeId());
			// TODO 添加节点名称
			jitem.put("text", areaDeptTree.getNodeName());
			String clickUrl = "";
			// 设置右键菜单
			if (AreaDeptTreeConstants.NODE_TYPE_PROVINCE.equals(areaDeptTree.getNodeType())) {
				jitem.put("nodeType", AreaDeptTreeConstants.NODE_TYPE_PROVINCE);
				jitem.put("leaf", false);
			}
			if (AreaDeptTreeConstants.NODE_TYPE_AREA.equals(areaDeptTree.getNodeType())) {
				jitem.put("nodeType", AreaDeptTreeConstants.NODE_TYPE_AREA);
				jitem.put("leaf", false);
			}
			if (AreaDeptTreeConstants.NODE_TYPE_FACTORY.equals(areaDeptTree.getNodeType())) {
				jitem.put("nodeType", AreaDeptTreeConstants.NODE_TYPE_FACTORY);
				jitem.put("leaf", true);
			}
			jsonRoot.put(jitem);
		}
		JSONUtil.print(response, jsonRoot.toString());
		return null;
	}

	/**
	 * 得到合作伙伴对应的地市
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String getArea(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String nodeId = StaticMethod.null2String(request.getParameter("nodeId"));
		PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) getBean("partnerDeptMgr");
		AreaDeptTreeMgr areaDeptTreeMgr = (AreaDeptTreeMgr) getBean("areaDeptTreeMgr");
		if("".equals(nodeId)){
			JSONArray jsonRoot = new JSONArray();
			JSONObject jitem = new JSONObject();
			jitem.put("partnerId", "");
			jitem.put("partnerDeptId", "");
			jitem.put("areaId", "");
			// TODO 添加节点名称
			jitem.put("areaName", "");
			jsonRoot.put(jitem);
			JSONUtil.print(response, jsonRoot.toString());
			return null;
		}else{
    	AreaDeptTree factory = areaDeptTreeMgr.getAreaDeptTreeByNodeId(nodeId);//人力信息父节点——厂商（部门），利用这个对象得到厂商名，给字段所属公司赋值
		PartnerDept PartnerDept = partnerDeptMgr.getPartnerDept(factory.getPartnerid());
    	AreaDeptTree area = areaDeptTreeMgr.getAreaDeptTreeByNodeId(factory.getParentNodeId());
		JSONArray jsonRoot = new JSONArray();
		JSONObject jitem = new JSONObject();
		jitem.put("partnerId", factory.getPartnerid());
		jitem.put("partnerDeptId", PartnerDept.getDeptMagId());
		jitem.put("areaId", area.getAreaId());
		// TODO 添加节点名称
		jitem.put("areaName", area.getNodeName());
		//得到对应县区选项
		TawSystemArea tawSystemArea = null;
		ITawSystemAreaManager areaMgr = (ITawSystemAreaManager) getBean("ItawSystemAreaManager");
		List areaList = areaMgr.getSonAreaByAreaId(area.getAreaId());
		StringBuffer cityOption = new StringBuffer();
		cityOption.append(	",--请选择--" );
	    for(int i=0;i<areaList.size();i++){
	    	tawSystemArea = (TawSystemArea)areaList.get(i);
	    	cityOption.append(	"," +tawSystemArea.getAreaid() );
	    	cityOption.append(	"," +tawSystemArea.getAreaname() );   
	       }
		jitem.put("cityOption", cityOption.toString());
		
		jsonRoot.put(jitem);
		JSONUtil.print(response, jsonRoot.toString());
		return null;
		}
	}
	
	/**
	 * 导入excel
	 */
	public ActionForward importExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			TawSystemSessionForm sessionform = this.getUser(request);
			String userId = sessionform.getUserid();
			IResidentSiteMgr siteMgr = (IResidentSiteMgr) getBean("iResidentSiteMgr");
			Name2IDService name2IDService = Nop3Utils
			.getService(Name2IDService.class);
			// 获得附件信息
			String sheetAccessoriesValue = Strings.nullToEmpty((request
					.getParameter("sheetAccessories")));
			sheetAccessoriesValue = sheetAccessoriesValue.replace("'", "")
					.trim();
			// 处理导入的excel
			String msg = "";
			BocoLog.info(this, "Todoo 开始执行导入");
			String fileUrl = Nop3Utils.getPathValue(sheetAccessoriesValue);

			String city = "";
			String country = "";
			String residentSiteName = "";
			String monitorCompany = "";
			String address = "";
			String personNum = "";
			String carNum = "";
			String telNum = "";
			String mobileTelNum = "";
			String dutyMan = "";
			
			TawSystemAreaDao tawSystemAreaDao = Nop3Utils.getService(TawSystemAreaDao.class);
			PartnerDeptMgr partnerDeptMgr = Nop3Utils.getService(PartnerDeptMgr.class);
			List<TawSystemArea> areaNamesAndIds = tawSystemAreaDao.getAreas();
			Map<String,String> cityNamesAndIds = new HashMap<String,String>();
			Map<String,String> companyNamesAndIds = new HashMap<String,String>();
			for (TawSystemArea tawSystemArea : Nop3Utils
					.nullToEmpty(areaNamesAndIds)) {
				cityNamesAndIds.put(tawSystemArea.getAreaname(), tawSystemArea.getAreaid());
			}
			List<PartnerDept> monitorCompanyList = partnerDeptMgr.getPartnerDepts();
			for (PartnerDept partnerDept : Nop3Utils
					.nullToEmpty(monitorCompanyList)) {
				companyNamesAndIds.put(partnerDept.getName(), partnerDept.getId());
			}
			cityNamesAndIds = Collections.unmodifiableMap(cityNamesAndIds);
			companyNamesAndIds = Collections.unmodifiableMap(companyNamesAndIds);
			if (fileUrl != null) {
				// Read excel from server.
				File read = new File(fileUrl);
				InputStream is = new FileInputStream(read.getPath());
				jxl.Workbook wb = Workbook.getWorkbook(is);
				jxl.Sheet st = wb.getSheet(0);

				int rows = st.getRows();

				int successCount = 0;
				int failCount = 0;
				BocoLog.info(this, "Find rows of" + rows);
				for (int i = 1; i < rows; i++) {
					// Get real value.

					city = st.getCell(1, i).getContents();
					country = st.getCell(2, i).getContents();
					residentSiteName = st.getCell(3, i).getContents();
					monitorCompany = st.getCell(4, i).getContents();
					address = st.getCell(5, i).getContents();
					personNum = st.getCell(6, i).getContents();
					carNum = st.getCell(7, i).getContents();
					telNum = st.getCell(8, i).getContents();
					mobileTelNum = st.getCell(9, i).getContents();
					dutyMan = st.getCell(10, i).getContents();
					try{
						ResidentSite station = new ResidentSite();
						station.setCity(cityNamesAndIds.get(city));
						station.setAddress(address);
						station.setDutyMan(dutyMan);
						station.setCarNum(carNum);
						station.setCountry(cityNamesAndIds.get(country));
						station.setMobileTelNum(mobileTelNum);
						station.setMonitorCompany(companyNamesAndIds.get(monitorCompany));
						station.setPersonNum(personNum);
						station.setResidentSiteName(residentSiteName);
						station.setTelNum(telNum);
						station.setCreateTime(CommonUtils.toEomsStandardDate(new Date()));
						station.setCreateUser(userId);
						station.setIsDel("0");
						siteMgr.saveResidentSite(station);
					}catch(Exception e){
						failCount++;
						// When DateParseException occours, it can be
						// maintained
						// by user, so continue this loop is
						// necessary.Please do
						// not delete this continue;
						continue;
					}
				}// End Loop
				msg += "成功导入数据：" + successCount + "条";
			}
			request.setAttribute("msg", msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.search(mapping, form, request, response);
	}
	
}