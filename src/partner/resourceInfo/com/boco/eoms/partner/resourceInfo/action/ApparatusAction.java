package com.boco.eoms.partner.resourceInfo.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import utils.PartnerPrivUtils;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcServiceImpl;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.personnel.util.PageData;
import com.boco.eoms.partner.personnel.util.SearchUtil;
import com.boco.eoms.partner.property.util.StatisticsResultExport;
import com.boco.eoms.partner.resourceInfo.form.ApparatusForm;
import com.boco.eoms.partner.resourceInfo.model.Apparatus;
import com.boco.eoms.partner.resourceInfo.service.ApparatusService;
import com.boco.eoms.partner.resourceInfo.util.ImportResult;
import com.boco.eoms.partner.resourceInfo.util.ResourceInfoUtils;
import com.boco.eoms.partner.statistically.pojo.TdObjModel;
import com.boco.eoms.partner.statistically.utils.TableHelper;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;
public class ApparatusAction extends BaseAction{
	/**
	 * 仪器仪表增加
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
		Map<String, String> map=new HashMap<String, String>();
		map=PartnerPrivUtils.userIsPersonnel(request);
		TawSystemSessionForm sessionForm=this.getUser(request);
		String userid=sessionForm.getUserid();
		String areaNode="";
		if ("admin".equals(userid)) {
			areaNode=sessionForm.getRootAreaId();
		}else {
			areaNode=map.get("areaId");
		}
		request.setAttribute("areaNode", areaNode);
		return mapping.findForward("goToAdd");
	}
	/**
	 * 保存仪器仪表信息
	 *fengguangping
	 * Sep 20, 2012-3:58:08 PM
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
		Apparatus apparatus= new Apparatus();
		BeanUtils.populate(apparatus, request.getParameterMap());
		ApparatusService apparatusService=(ApparatusService)getBean("apparatusService");
		String maintainCompany=apparatus.getMaintenanceCompany();
		if (!"".equals(maintainCompany)&&maintainCompany.length()>20) {
			PartnerDeptMgr deptMgr=(PartnerDeptMgr)getBean("partnerDeptMgr");
			PartnerDept dept=deptMgr.getPartnerDept(maintainCompany);
			apparatus.setMaintenanceCompany(StaticMethod.null2String(dept.getDeptMagId()));
		}
		apparatus.setAddTime(CommonUtils.toEomsStandardDate(new Date()));
		apparatus.setDeleted("0");
		apparatus.setVisible("0");
		apparatusService.save(apparatus);
		return mapping.findForward("successJump");
	}
	/**
	 * 显示仪器仪表详情
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
			ApparatusService apparatusService=(ApparatusService)getBean("apparatusService");
			String id=request.getParameter("id");
			Apparatus apparatus=apparatusService.find(id);
			request.setAttribute("apparatus", apparatus);
			return mapping.findForward("detail");
	}
	/**
	 * 删除仪器仪表
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)	throws Exception {
		response.setCharacterEncoding("utf-8");
		Writer writer=response.getWriter();
		try {
			String id=request.getParameter("id");
			ApparatusService apparatusService=(ApparatusService)getBean("apparatusService");	
			Apparatus apparatus=apparatusService.find(id);
			apparatus.setDeleted("1");
			apparatusService.save(apparatus);
			writer.write(new Gson().toJson(new ImmutableMap.Builder<String, String>()
					.put("success", "true").put("info", "删除成功").build()));
		} catch (Exception e) {
			writer.write(new Gson().toJson(new ImmutableMap.Builder<String, String>()
					.put("success", "false").put("info", "删除出错").build()));
		}finally{
			writer.close();
			return null;
		}
	}
	/**
	 * 仪器仪表分页
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
		ApparatusService apparatusService=(ApparatusService)getBean("apparatusService");
		Search search=new Search();
		int firstResult=CommonUtils.getFirstResultOfDisplayTag(request, "apparatusList");
		Integer pageSize = UtilMgrLocator.getEOMSAttributes().getPageSize();
		//获取导出的状态如果不为空.说明点击了导出按钮 
		String exportValue = request.getParameter(org.displaytag.tags.TableTagParameters.PARAMETER_EXPORTING);
		if(null!=exportValue && !"".equals(exportValue)){
			pageSize = new Integer(-1);
		}
		search.setFirstResult(firstResult*pageSize);
		search.setMaxResults(pageSize);
		search.addFilterNotEqual("deleted", "1");
		//获取区域id作为删选条件
		TawSystemSessionForm sessionForm=this.getUser(request);
		String deptid=sessionForm.getDeptid();
		String interfaceHeadId="";
		List<PartnerDept>  list0=new ArrayList<PartnerDept>();
		if (!"admin".equals(sessionForm.getUserid())) {
			PartnerDeptMgr partnerDeptMgr=(PartnerDeptMgr)getBean("partnerDeptMgr");
			list0=partnerDeptMgr.getPartnerDepts("and deptMagId='"+deptid+"'");
			if (list0.size()!=0&&list0!=null) {//不等于0表示是代维公司的
				search.addFilterILike("maintenanceCompany", deptid+"%");//代维公司权限限定
				interfaceHeadId=list0.get(0).getInterfaceHeadId();
			}else {
				ITawSystemDeptManager deptManager=(ITawSystemDeptManager)getBean("ItawSystemDeptManager");
				TawSystemDept dept=deptManager.getDeptinfobydeptid(deptid,"0");
				if (dept!=null) {
					search.addFilterILike("area", dept.getAreaid()+"%");//区域权限限定
				}
			}
		}
		search.addFilterNotEqual("visible", "1");//1表示不可见，正在执行流程申请中
		search.addFilterNotEqual("deleted", "1");//1表示删除
		search=CommonUtils.getSqlFromRequestMap(request, search);
		String companyId=StaticMethod.null2String(request.getParameter("company_id"));
		String companyName=StaticMethod.null2String(request.getParameter("company_name"));
		String areaName=StaticMethod.null2String(request.getParameter("area_name"));
		String companyDeptId=ResourceInfoUtils.deptUUidToDeptId(companyId);
		search.addFilterILike("maintenanceCompany", companyDeptId+"%");
		request.setAttribute("companyName", companyName);
		request.setAttribute("areaName", areaName);
		request.setAttribute("companyId", companyDeptId);
		SearchResult<Apparatus> searchResult=apparatusService.searchAndCount(search);
		List list=searchResult.getResult();
		int resultSize=searchResult.getTotalCount();
		request.setAttribute("apparatusList", list);
		request.setAttribute("resultSize", resultSize);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("node", interfaceHeadId);
		request.setAttribute("userid", sessionForm.getUserid());
		if (list0.size()!=0&&list0!=null) {
			return mapping.findForward("partnerList");
		}
		return mapping.findForward("list");
	}
	/**
	 * 仪器仪表修改
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
			ApparatusService apparatusService=(ApparatusService)getBean("apparatusService");
			String id=StaticMethod.null2String(request.getParameter("id"));
			Apparatus apparatus=apparatusService.find(id);
			request.setAttribute("apparatus", apparatus);
			return mapping.findForward("edit");
	}
	/**
	 * 更新仪器仪表信息
	 *fengguangping
	 * Sep 20, 2012-4:02:21 PM
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
			ApparatusService apparatusService=(ApparatusService)getBean("apparatusService");
			String id=request.getParameter("id");
			Apparatus apparatus=apparatusService.find(id);
			BeanUtils.populate(apparatus, request.getParameterMap());
			String maintainCompany=apparatus.getMaintenanceCompany();
			if (!"".equals(maintainCompany)&&maintainCompany.length()>20) {
				PartnerDeptMgr deptMgr=(PartnerDeptMgr)getBean("partnerDeptMgr");
				PartnerDept dept=deptMgr.getPartnerDept(maintainCompany);
				apparatus.setMaintenanceCompany(StaticMethod.null2String(dept.getDeptMagId()));
			}
			apparatusService.save(apparatus);
			return mapping.findForward("forwardList");
	}
	/**
	 * 导入数据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward importData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("utf-8");
		ApparatusForm uploadForm=(ApparatusForm)form;
		FormFile formFile=uploadForm.getImportFile();
		PrintWriter writer=null;
		try {
			ApparatusService apparatusService=(ApparatusService)getBean("apparatusService");
			writer=response.getWriter();
			ImportResult result=apparatusService.importFromFile(formFile);
			if (result.getResultCode().equals("200")) {
				writer.write(
					new Gson().toJson(new ImmutableMap.Builder<String, String>()
						.put("success", "true")
						.put("msg", "ok")
						.put("infor", "导入成功,共计导入"+result.getImportCount()+"条记录").build()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			writer.write(
					new Gson().toJson(new ImmutableMap.Builder<String, String>()
							.put("success", "false")
							.put("msg", "failure")
							.put("infor", e.getMessage()).build()));
		}finally{
			if(writer != null){
				writer.close();
			}
		}
		return null;
	}
	/**
	 * 下载模板
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public void download(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String rootPath = request.getRealPath(File.separator+"partner"+ File.separator+"processExcelModel");
		String fileName = "增加仪器仪表.xls";
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
	/**
	 * 进入统计页面
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
	 * 数据统计
	 * 在统计页面中，统计条件和统计项目的id命名为表的列名称
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
		//带有字典标识的统计项目字段
		String rows[]=StaticMethod.nullObject2String(request.getParameter("statisticsItems"),"").split(";");
		//无字典标识的统计项目字段和数据库的列名相同
		String checkedString=StaticMethod.nullObject2String(request.getParameter("checkedIds"),"");
		//数值转化为字符串，作为sql的search条件
		String statisticsItems[]=checkedString.split(";");
		String searchStr="";
		String group="";
		for (int i = 0; i < rows.length; i++) {
			if(rows[i].contains("TypeLikedict")){
				searchStr+=statisticsItems[i]+" as "+rows[i];
			}else if(rows[i].contains("TypeLikeArea")) {
				searchStr+=statisticsItems[i]+" as "+rows[i];
			}else if(rows[i].contains("TypeLikeUser")) {
				searchStr+=statisticsItems[i]+" as "+rows[i];
			}else if(rows[i].contains("TypeLikeDept")) {
				searchStr+=statisticsItems[i]+" as "+rows[i];
			}else {
				searchStr+=statisticsItems[i];
			}
			group+=statisticsItems[i];
			if (i!=rows.length-1) {
				searchStr+=",";
				group+=",";
			}
		}
		//获取where条件值
		String area=StaticMethod.null2String(request.getParameter("area_id"));
		String maintainCompany=StaticMethod.null2String(request.getParameter("maintainCompany_id"));
		maintainCompany=ResourceInfoUtils.deptUUidToDeptId(maintainCompany);
		String maintenanceMajor=StaticMethod.null2String(request.getParameter("maintenanceMajor"));
		String exportFlag=StaticMethod.null2String(request.getParameter("exportFlag"));
		String whereStr=" ";
		if(!"".equals(area)){
			whereStr+=" and area like '"+area+"'";
		}
		if (!"".equals(maintainCompany)) {
			whereStr+=" and company like '"+maintainCompany+"'";
		}
		if (!"".equals(maintenanceMajor)) {
			whereStr+=" and major like '"+maintenanceMajor+"'";
		}
		String statisticsSql="select "+searchStr+",count(id) as count  from pnr_apparatusInfo where " +
				"deleted <>  "+" 1 and visible <>  "+"1"+whereStr+"  group by "+group+" order by "+group;
		List<String> headList = new ArrayList<String>();
		//[area, maintenanceCompany, maintenanceMajor, apparatusName, apparatusType, apparatusProperty, apparatusStatus]
		for (int i = 0; i < rows.length; i++) {
			if ("areaTypeLikeArea".equals(rows[i])) {
				headList.add("所属区域");
			}else if ("companyTypeLikeDept".equals(rows[i])) {
				headList.add("代维公司");
			}else if ("majorTypeLikedict".equals(rows[i])) {
				headList.add("维护专业");
			}else if ("nameTypeLikedict".equals(rows[i])) {
				headList.add("仪器名称");
			}else if ("type".equals(rows[i])) {
				headList.add("仪器类型");
			}else if ("propertyTypeLikedict".equals(rows[i])) {
				headList.add("产权属性");
			}else if ("statusTypeLikedict".equals(rows[i])) {
				headList.add("状态");
			}
		}
		headList.add("总数");
		List<List<TdObjModel>> tempList = TableHelper.verticalGrowp(rows,statisticsSql,"/partner/resourceInfo/apparatus.do?method=searchInto");
		request.setAttribute("areaStringLike", area);
		request.setAttribute("maintainCompanyStringLike", maintainCompany);
		request.setAttribute("major", maintenanceMajor);
		request.setAttribute("tableList", tempList);
		request.setAttribute("headList", headList);
		request.setAttribute("checkedList", checkedString);
		if (!"".equals(exportFlag)&&exportFlag.equals("2")) {
			//执行导出
			String fileName="仪器仪表统计";
			StatisticsResultExport.exportStatisticsResultToXLSFile(true, tempList, headList, fileName, response,request);
			return null;
		}else {
			//跳转到统计页面
			request.setAttribute("hasSend", "ok");
			return mapping.findForward("goToStatisticsPage");
		}
	}
	/**数据钻取
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
		CommonSpringJdbcServiceImpl csjs=(CommonSpringJdbcServiceImpl)getBean("commonSpringJdbcService");
		request.setCharacterEncoding("utf-8");
		String whereStr="";
		String area=StaticMethod.null2String(request.getParameter("areatypelikearea"));
		String maintenanceCompany=StaticMethod.null2String(request.getParameter("companytypelikedept"));
		maintenanceCompany=ResourceInfoUtils.deptUUidToDeptId(maintenanceCompany);
		String maintenanceMajor=StaticMethod.null2String(request.getParameter("majortypelikedict"));
		String apparatusName=StaticMethod.null2String(request.getParameter("nametypelikedict"));
		String apparatusType=StaticMethod.null2String(request.getParameter("type"));
		String apparatusProperty=StaticMethod.null2String(request.getParameter("propertytypelikedict"));
		String apparatusStatus=StaticMethod.null2String(request.getParameter("statustypelikedict"));
		if (!"".equals(area)) {
			whereStr+=" and area='"+area+"'";
		}
		if (!"".equals(maintenanceCompany)) {
			whereStr+=" and company='"+maintenanceCompany+"'";
		}
		if (!"".equals(maintenanceMajor)) {
			whereStr+=" and major='"+maintenanceMajor+"'";
		}
		if (!"".equals(apparatusName)) {
			whereStr+=" and name='"+apparatusName+"'";
		}
		if (!"".equals(apparatusType)) {
			whereStr+=" and type='"+apparatusType+"'";
		}
		if (!"".equals(apparatusProperty)) {
			whereStr +=" and property='"+apparatusProperty+"'";
		}if (!"".equals(apparatusStatus)) {
			whereStr+=" and status='"+apparatusStatus+"'";
		}
		String sql=" select * from pnr_apparatusInfo where deleted <> 1 and visible <> 1 "+whereStr;
		String pageIndexName = new org.displaytag.util.ParamEncoder("apparatusList").
		encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageIndex = new Integer(GenericValidator.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
		: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		String countSql="select count(*) as count from ( "+sql+")";
		SearchUtil<Apparatus> search = new SearchUtil<Apparatus>(Apparatus.class,"apparatus",sql,countSql);
		PageData<Apparatus> pageData = search.getDataList(pageIndex);
		request.setAttribute("pageSize", pageData.getPageSize());
		request.setAttribute("resultSize", pageData.getTotalCount());
		request.setAttribute("apparatusList", pageData.getList());
		return mapping.findForward("statisticsList");
	}
}
