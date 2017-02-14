package com.boco.eoms.partner.resourceInfo.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.system.user.service.ITawSystemUserManager;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcServiceImpl;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.personnel.util.PageData;
import com.boco.eoms.partner.personnel.util.SearchUtil;
import com.boco.eoms.partner.property.util.StatisticsResultExport;
import com.boco.eoms.partner.resourceInfo.form.OilEngineForm;
import com.boco.eoms.partner.resourceInfo.model.OilEngine;
import com.boco.eoms.partner.resourceInfo.service.OilEngineService;
import com.boco.eoms.partner.resourceInfo.switchcfg.PnrScSwitchConfig;
import com.boco.eoms.partner.resourceInfo.util.ImportResult;
import com.boco.eoms.partner.resourceInfo.util.ResourceInfoUtils;
import com.boco.eoms.partner.statistically.pojo.TdObjModel;
import com.boco.eoms.partner.statistically.utils.TableHelper;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

public class OilEngineAction extends BaseAction {
	/**
	 * 添加油机信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward goToAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PnrScSwitchConfig cfg = (PnrScSwitchConfig) this.getServlet().getServletContext()
		.getAttribute("pnrScSwitchConfig");
//		PnrScSwitchConfig cfg = (PnrScSwitchConfig) request.getSession()
//		.getAttribute("pnrScSwitchConfig");		
		if(cfg.isOpenScSwitch()){
			return mapping.findForward("goToAddSc");
		}else{
			return mapping.findForward("goToAdd");
		}
	}

	/**
	 * 保存和更新油机信息
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
		OilEngineService oilEngineService = (OilEngineService) getBean("oilEngineService");
		OilEngineForm oilEngineForm = (OilEngineForm) form;
		OilEngine oilEngine = new OilEngine();
		BeanUtils.copyProperties(oilEngine, oilEngineForm);
		String maintainComany = oilEngine.getMaintainCompany();
		if (!"".equals(maintainComany) && maintainComany.length() == 32) {
			PartnerDeptMgr deptMgr = (PartnerDeptMgr) getBean("partnerDeptMgr");
			PartnerDept dept = deptMgr.getPartnerDept(maintainComany);
			oilEngine.setMaintainCompany(StaticMethod.null2String(dept
					.getDeptMagId()));
		}
		try {
			oilEngine.setAddTime(CommonUtils.toEomsStandardDate(new Date(System
					.currentTimeMillis())));
			oilEngine.setDeleted("0");
			oilEngine.setVisible("0");
			oilEngineService.save(oilEngine);
			return mapping.findForward("successJump");
		} catch (Exception e) {
			return mapping.findForward("failJump");
		}
	}

	/**
	 * 删除油机信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("utf-8");
		Writer writer = response.getWriter();
		try {
			String id = StaticMethod.null2String(request.getParameter("id"));
			OilEngineService oilEngineService = (OilEngineService) getBean("oilEngineService");
			OilEngine oilEngine = oilEngineService.find(id);
			oilEngine.setDeleted("1");
			oilEngineService.save(oilEngine);
			writer.write(new Gson()
					.toJson(new ImmutableMap.Builder<String, String>().put(
							"success", "true").put("info", "删除成功").build()));
		} catch (Exception e) {
			writer.write(new Gson()
					.toJson(new ImmutableMap.Builder<String, String>().put(
							"success", "false").put("info", "删除出错").build()));
		} finally {
			writer.close();
			return null;
		}

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
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = StaticMethod.null2String(request.getParameter("id"));
		OilEngineService oilEngineService = (OilEngineService) getBean("oilEngineService");
		OilEngine oilEngine = oilEngineService.find(id);
		request.setAttribute("oilEngine", oilEngine);

		ITawSystemUserManager userManager = (ITawSystemUserManager) getBean("ItawSystemUserSaveManagerFlush");
//		PnrScSwitchConfig cfg = (PnrScSwitchConfig) request.getSession()
//		.getAttribute("pnrScSwitchConfig");		
		PnrScSwitchConfig cfg = (PnrScSwitchConfig) this.getServlet().getServletContext()
		.getAttribute("pnrScSwitchConfig");		
		if(cfg.isOpenScSwitch()){        
			String responseManName = userManager.getUserByuserid(oilEngine.getResponseMan()).getUsername();
			request.setAttribute("responseManName",responseManName);
			return mapping.findForward("editSc");
		}else{
			return mapping.findForward("edit");
		}		
	}

	/**
	 * 更新页面信息
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward update(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		OilEngineService oilEngineService = (OilEngineService) getBean("oilEngineService");
		String id = request.getParameter("id");
		OilEngine oilEngine = oilEngineService.find(id);
		BeanUtils.populate(oilEngine, request.getParameterMap());
		String maintainComany = oilEngine.getMaintainCompany();
		if (!"".equals(maintainComany) && maintainComany.length() > 20) {
			PartnerDeptMgr deptMgr = (PartnerDeptMgr) getBean("partnerDeptMgr");
			PartnerDept dept = deptMgr.getPartnerDept(maintainComany);
			oilEngine.setMaintainCompany(StaticMethod.null2String(dept
					.getDeptMagId()));
		}
		oilEngine.setAddTime(CommonUtils.toEomsStandardDate(new Date(System
				.currentTimeMillis())));
		oilEngineService.save(oilEngine);
		return mapping.findForward("forwardList");
	}

	/**
	 * 分页查找
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
		OilEngineService oilEngineService = (OilEngineService) getBean("oilEngineService");
		Search search = new Search();
		int firstResult = CommonUtils.getFirstResultOfDisplayTag(request,"oilEngineList");
		Integer pageSize = UtilMgrLocator.getEOMSAttributes().getPageSize();
		// 获取导出的状态如果不为空.说明点击了导出按钮
		String exportValue = request
				.getParameter(org.displaytag.tags.TableTagParameters.PARAMETER_EXPORTING);
		if (null != exportValue && !"".equals(exportValue)) {
			pageSize = new Integer(-1);
		}
		search.setFirstResult(firstResult * pageSize);
		search.setMaxResults(pageSize);
		search.addFilterEqual("deleted", "0");
		// 获取区域id作为删选条件
		TawSystemSessionForm sessionForm = this.getUser(request);
		String deptid = sessionForm.getDeptid();
		List<PartnerDept> list0 = new ArrayList<PartnerDept>();
		if (!"admin".equals(sessionForm.getUserid())) {
			PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) getBean("partnerDeptMgr");
			list0 = partnerDeptMgr.getPartnerDepts("and deptMagId='" + deptid
					+ "'");
			if (list0.size() != 0 && list0 != null) {// 不等于0表示是代维公司的
				search.addFilterILike("maintainCompany", deptid + "%");// 代维公司权限限定
			} else {
				ITawSystemDeptManager deptManager = (ITawSystemDeptManager) getBean("ItawSystemDeptManager");
				TawSystemDept dept = deptManager.getDeptinfobydeptid(deptid,
						"0");
				if (dept != null) {
					search.addFilterILike("area", dept.getAreaid() + "%");// 区域权限限定
				}
			}
		}
		//search.addFilterNotEqual("deleted", "1");// 1表示已经删除
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
		List list = searchResult.getResult();
		int resultSize = searchResult.getTotalCount();
		request.setAttribute("oilEngineList", list);
		request.setAttribute("total", resultSize);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("userid", sessionForm.getUserid());
		if (list0.size() != 0 && list0 != null) {
			return mapping.findForward("partnerList");
		}
		return mapping.findForward("list");
	}

	/**
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
		String id = request.getParameter("id");
		OilEngineService oilEngineService = (OilEngineService) getBean("oilEngineService");
		OilEngine oilEngine = oilEngineService.find(id);
		String oilEngineNumber = oilEngine.getOilEngineNumber();
		String whereStr = "from OilEngine where deleted='1' and oilEngineNumber = '" + oilEngineNumber + "'";
		List<OilEngine> list = oilEngineService.getOilEngineChangeList(whereStr); 
		request.setAttribute("oilEngine", oilEngine);
		request.setAttribute("oilEngineChangeList", list);
		request.setAttribute("total", list.size());
		request.setAttribute("pageSize", 15);
//		PnrScSwitchConfig cfg = (PnrScSwitchConfig) request.getSession()
//		.getAttribute("pnrScSwitchConfig");		
		PnrScSwitchConfig cfg = (PnrScSwitchConfig) this.getServlet().getServletContext()
		.getAttribute("pnrScSwitchConfig");		
		if(cfg.isOpenScSwitch()){
			return mapping.findForward("detailSc");
		}else{
			return mapping.findForward("detail");
		}
	}
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward detailChange(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		OilEngineService oilEngineService = (OilEngineService) getBean("oilEngineService");
		OilEngine oilEngine = oilEngineService.find(id);
		String oilEngineNumber = oilEngine.getOilEngineNumber();
		String whereStr = "from OilEngine where deleted='1' and oilEngineNumber = '" + oilEngineNumber + "'";
		List<OilEngine> list = oilEngineService.getOilEngineChangeList(whereStr); 
		request.setAttribute("oilEngine", oilEngine);
		request.setAttribute("oilEngineChangeList", list);
		request.setAttribute("total", list.size());
		request.setAttribute("pageSize", 15);
//		PnrScSwitchConfig cfg = (PnrScSwitchConfig) request.getSession()
//		.getAttribute("pnrScSwitchConfig");		
		PnrScSwitchConfig cfg = (PnrScSwitchConfig) this.getServlet().getServletContext()
		.getAttribute("pnrScSwitchConfig");		
		if(cfg.isOpenScSwitch()){
			return mapping.findForward("detailChange");
		}else{
			return mapping.findForward("detail");
		}
	}
	public void download(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String rootPath = request.getRealPath(File.separator+"partner"+ File.separator+"processExcelModel");
		String fileName = "增加油机.xls";
		File file = new File(rootPath + File.separator + fileName);

		// 读到流中
		InputStream inStream = new FileInputStream(file);// 文件的存放路径
		// 设置输出的格式
		response.reset();
		response.setContentType("application/x-msdownload;charset=GBK");
		response.setCharacterEncoding("GB2312");
		response.setHeader("Content-Disposition", "attachment; filename="
				+ new String(fileName.getBytes("gbk"), "iso8859-1"));
		// 循环取出流中的数据
		byte[] b = new byte[1024];
		int len = 0;
		while ((len = inStream.read(b)) > 0)
			response.getOutputStream().write(b, 0, len);
		inStream.close();
	}

	// excel导入功能
	public ActionForward importData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("utf-8");
		OilEngineForm uploadForm = (OilEngineForm) form;
		FormFile formFile = uploadForm.getImportFile();
		PrintWriter writer = response.getWriter();
		try {
			OilEngineService oilEngineService = (OilEngineService) getBean("oilEngineService");
			ImportResult result = oilEngineService.importFromFile(formFile);
			if (result.getResultCode().equals("200")) {
				writer.write(new Gson()
						.toJson(new ImmutableMap.Builder<String, String>().put(
								"success", "true").put("msg", "ok").put(
								"infor",
								"导入成功,共计导入" + result.getImportCount() + "条记录")
								.build()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			writer.write(new Gson()
					.toJson(new ImmutableMap.Builder<String, String>().put(
							"success", "false").put("msg", "failure").put(
							"infor", e.getMessage()).build()));
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
		return null;
	}

	/**
	 * 进入统计页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exceptionstatistics
	 */
	public ActionForward goToStatistics(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("goToStatisticsPage");
	}

	/**
	 * 数据统计 在统计页面中，统计条件和统计项目的id命名为表的列名称，name为实体名称;
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward statistics(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 带有字典标识的统计项目字段
		String rows[] = StaticMethod.nullObject2String(
				request.getParameter("statisticsItems"), "").split(";");
		// 无字典标识的统计项目字段和数据库的列名相同
		String checkedString = StaticMethod.nullObject2String(request
				.getParameter("checkedIds"), "");
		// 数值转化为字符串，作为sql的search条件
		String statisticsItems[] = checkedString.split(";");
		String searchStr = "";
		String group = "";
		for (int i = 0; i < rows.length; i++) {
			if (rows[i].contains("TypeLikedict")) {
				searchStr += statisticsItems[i] + " as " + rows[i];
			} else if (rows[i].contains("TypeLikeArea")) {
				searchStr += statisticsItems[i] + " as " + rows[i];
			} else if (rows[i].contains("TypeLikeUser")) {
				searchStr += statisticsItems[i] + " as " + rows[i];
			} else if (rows[i].contains("TypeLikeDept")) {
				searchStr += statisticsItems[i] + " as " + rows[i];
			} else {
				searchStr += statisticsItems[i];
			}
			group += statisticsItems[i];
			if (i != rows.length - 1) {
				searchStr += ",";
				group += ",";
			}
		}
		// 获取where条件值
		String area = StaticMethod.nullObject2String(request
				.getParameter("area_id"), "");
		String maintainCompany = StaticMethod.nullObject2String(request
				.getParameter("maintainCompany_id"), "");
		if (!"".equals(maintainCompany) && maintainCompany.length() > 20) {
			PartnerDeptMgr deptMgr = (PartnerDeptMgr) getBean("partnerDeptMgr");
			PartnerDept dept = deptMgr.getPartnerDept(maintainCompany);
			maintainCompany = StaticMethod.null2String(dept.getDeptMagId());
		}
		String exportFlag = StaticMethod.nullObject2String(request.getParameter("exportFlag"), "");
		String whereStr = " ";
		if (!"".equals(area)) {
			whereStr += " and area like '" + area + "'";
		}
		if (!"".equals(maintainCompany)) {
			whereStr += " and company like '" + maintainCompany + "'";
		}
		String statisticsSql = "select " + searchStr
				+ ",count(id) as count from pnr_oilEngineInfo where "
				+ "deleted <> 1 and visible <> 1 " + whereStr + "  group by "
				+ group + "  order by " + group;
		List<String> headList = new ArrayList<String>();
		for (int i = 0; i < rows.length; i++) {
			if ("areaTypeLikeArea".equals(rows[i])) {
				headList.add("所属区域");
			} else if ("companyTypeLikeDept".equals(rows[i])) {
				headList.add("代维公司");
			} else if ("oilEngine_number".equals(rows[i])) {
				headList.add("油机编号");
			} else if ("fule_typeTypeLikedict".equals(rows[i])) {
				headList.add("燃油种类");
			} else if ("typeTypeLikedict".equals(rows[i])) {
				headList.add("油机类型");
			} else if ("propertyTypeLikedict".equals(rows[i])) {
				headList.add("产权属性");
			} else if ("statusTypeLikedict".equals(rows[i])) {
				headList.add("油机状态");
			} else if ("power_rating".equals(rows[i])) {
				headList.add("额定功率");
			}
		}
		headList.add("总数");
		List<List<TdObjModel>> tempList = TableHelper.verticalGrowp(rows,statisticsSql,"/partner/resourceInfo/oilEngine.do?method=searchInto");
		request.setAttribute("tableList", tempList);
		request.setAttribute("areaStringLike", area);
		request.setAttribute("maintainCompanyStringLike", maintainCompany);
		request.setAttribute("headList", headList);
		request.setAttribute("checkedList", checkedString);
		if (!"".equals(exportFlag) && exportFlag.equals("2")) {
			// 执行导出
			String fileName = "油机信息统计";
			StatisticsResultExport.exportStatisticsResultToXLSFile(true,
					tempList, headList, fileName, response, request);
			return null;
		} else {
			// 跳转到统计页面
			request.setAttribute("hasSend", "ok");
			return mapping.findForward("goToStatisticsPage");
		}
	}

	/**
	 * 数据钻取+分页
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchInto(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		CommonSpringJdbcServiceImpl csjs = (CommonSpringJdbcServiceImpl) getBean("commonSpringJdbcService");
		request.setCharacterEncoding("utf-8");
		String whereStr = " ";
		String area = StaticMethod.nullObject2String(request.getParameter("areatypelikearea"));
		String maintainCompany = StaticMethod.nullObject2String(request.getParameter("companytypelikedept"));
		if (!"".equals(maintainCompany) && maintainCompany.length() > 20) {
			PartnerDeptMgr deptMgr = (PartnerDeptMgr) getBean("partnerDeptMgr");
			PartnerDept dept = deptMgr.getPartnerDept(maintainCompany);
			maintainCompany = StaticMethod.null2String(dept.getDeptMagId());
		}
		String fuleType = StaticMethod.nullObject2String(request
				.getParameter("fule_typetypelikedict"));
		String oilEngineType = StaticMethod.nullObject2String(request
				.getParameter("typetypelikedict"));
		String oilEngineProperty = StaticMethod.nullObject2String(request
				.getParameter("propertytypelikedict"));
		String oilEngineStatus = StaticMethod.nullObject2String(request
				.getParameter("statustypelikedict"));
		String powerRating = StaticMethod.nullObject2String(request
				.getParameter("power_rating"));
		if (!"".equals(area)) {
			whereStr += " and area='" + area + "'";
		}
		if (!"".equals(maintainCompany)) {
			whereStr += " and company='" + maintainCompany + "'";
		}
		if (!"".equals(fuleType)) {
			whereStr += " and fule_type='" + fuleType + "'";
		}
		if (!"".equals(oilEngineType)) {
			whereStr += " and type='" + oilEngineType + "'";
		}
		if (!"".equals(oilEngineProperty)) {
			whereStr += " and property='" + oilEngineProperty + "'";
		}
		if (!"".equals(powerRating)) {
			whereStr += " and power_rating='" + powerRating + "'";
		}
		if (!"".equals(oilEngineStatus)) {
			whereStr += " and status='" + oilEngineStatus + "'";
		}
		String sql = " select * from pnr_oilEngineInfo where deleted <> 1 and visible <> 1  "+ whereStr;
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				"oilEngineList")
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		String countSql = "select count(*) as count from ( " + sql + ")";
		// 用于数据钻取分页
		SearchUtil<OilEngine> search = new SearchUtil<OilEngine>(OilEngine.class, "oilEngine", sql, countSql);
		PageData<OilEngine> pageData = search.getDataList(pageIndex);
		request.setAttribute("pageSize", pageData.getPageSize());
		request.setAttribute("resultSize", pageData.getTotalCount());
		request.setAttribute("oilEngineList", pageData.getList());
		return mapping.findForward("statisticsList");
	}
	
}
