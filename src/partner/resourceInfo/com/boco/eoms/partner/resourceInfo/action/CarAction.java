package com.boco.eoms.partner.resourceInfo.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcServiceImpl;
import com.boco.eoms.deviceManagement.common.utils.CommonConstants;
import com.boco.eoms.deviceManagement.common.utils.CommonSqlHelper;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.baseinfo.model.PartnerUser;
import com.boco.eoms.partner.personnel.util.PageData;
import com.boco.eoms.partner.personnel.util.SearchUtil;
import com.boco.eoms.partner.process.util.PnrProcessCach;
import com.boco.eoms.partner.property.util.StatisticsResultExport;
import com.boco.eoms.partner.resourceInfo.form.CarForm;
import com.boco.eoms.partner.resourceInfo.model.Apparatus;
import com.boco.eoms.partner.resourceInfo.model.Car;
import com.boco.eoms.partner.resourceInfo.service.CarService;
import com.boco.eoms.partner.resourceInfo.util.ImportResult;
import com.boco.eoms.partner.resourceInfo.util.ResourceInfoUtils;
import com.boco.eoms.partner.statistically.pojo.TdObjModel;
import com.boco.eoms.partner.statistically.utils.TableHelper;
import com.boco.eoms.partner.taskManager.model.CarTask;
import com.boco.eoms.partner.taskManager.service.ICarTaskService;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;
public class CarAction extends BaseAction {
	/**
	 * 车辆增加
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
		CarService carService=(CarService)ApplicationContextHolder.getInstance().getBean("carService");
		List  list=carService.getDispatchCar("202");
		List list2=carService.getDispatchCar(request);
		return mapping.findForward("goToAdd");
	}
	/**
	 * 车辆保存
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		Car car=new Car();
		BeanUtils.populate(car, request.getParameterMap());
		String maintainCompany=car.getMaintainCompany();
		if (!"".equals(maintainCompany)&&maintainCompany.length()>20) {
			PartnerDeptMgr deptMgr=(PartnerDeptMgr)getBean("partnerDeptMgr");
			PartnerDept dept=deptMgr.getPartnerDept(maintainCompany);
			car.setMaintainCompany(StaticMethod.null2String(dept.getDeptMagId()));
		}
		String driverUserId=car.getDriver();
		PartnerUserMgr partnerUserMgr=(PartnerUserMgr)getBean("partnerUserMgr");
		PartnerUser user=partnerUserMgr.getPartnerUserByUserId(driverUserId);
		car.setDriver(StaticMethod.null2String(user.getId()));
		car.setAddTime(CommonUtils.toEomsStandardDate(new Date()));
		CarService carService=(CarService)getBean("carService");
		car.setDeleted("0");
		car.setVisible("0");
		car.setDispatchStatus("0");
		carService.save(car);
		String id=car.getId()+"";
		String carGPSNumber=car.getCarGPSNumber();
		String carNumber=car.getCarNumber();
		//刷新静态资源
		 PnrProcessCach.carnumAndGPSnumListCach.put(carNumber,id+","+carNumber);
		 PnrProcessCach.carnumAndGPSnumListCach.put(carGPSNumber,id+","+carGPSNumber);
		return mapping.findForward("successJump");
	}
	/**
	 * 显示car详情
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward detail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id=request.getParameter("id");
		CarService carService=(CarService)getBean("carService");
		Car car=carService.find(id);
		String carNumber=StaticMethod.null2String(car.getCarNumber());
		String applyId=StaticMethod.null2String(car.getApplyId());
		CarTask carTask=new CarTask();
		ICarTaskService carTaskService=(ICarTaskService)getBean("carTaskDaoService");
		if(!"".equals(carNumber)&&!"".equals(applyId)){
			carTask=carTaskService.getCarCurrentTask(carNumber, applyId);
		}
		request.setAttribute("car", car);
		request.setAttribute("carTask", carTask);
		return mapping.findForward("detail");
	}
	/**
	 * 删除car
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
			CarService carService=(CarService)getBean("carService");	
			Car car=carService.find(id);
			carService.remove(car);
			PnrProcessCach.carnumAndGPSnumListCach.remove(StaticMethod.null2String(car.getCarGPSNumber()));//清除缓存数据
			PnrProcessCach.carnumAndGPSnumListCach.remove(StaticMethod.null2String(car.getCarNumber()));//清除缓存数据
			writer.write(new Gson().toJson(new ImmutableMap.Builder<String, String>()
					.put("success", "true").put("info", "删除成功").build()));
		} catch (Exception e) {
			writer.write(new Gson().toJson(new ImmutableMap.Builder<String, String>()
					.put("success", "false").put("info", "删除出错").build()));
		}finally{
			//刷新静态资源
			if(writer != null){
				writer.close();
			}
			return null;
		}
	}
	/**
	 * car分页
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CarService carService=(CarService)getBean("carService");
		Search search=new Search();
		int firstResult=CommonUtils.getFirstResultOfDisplayTag(request, "carList");
		Integer pageSize = UtilMgrLocator.getEOMSAttributes().getPageSize();
		//获取导出的状态如果不为空.说明点击了导出按钮 
		String exportValue = request.getParameter(org.displaytag.tags.TableTagParameters.PARAMETER_EXPORTING);
		if(null!=exportValue && !"".equals(exportValue)){
			pageSize = new Integer(-1);
		}
		search.setFirstResult(firstResult*pageSize);
		search.setMaxResults(pageSize);
		//获取区域id作为删选条件
		TawSystemSessionForm sessionForm=this.getUser(request);
		String deptid=sessionForm.getDeptid();
		List<PartnerDept>  list0=new ArrayList<PartnerDept>();
		if (!"admin".equals(sessionForm.getUserid())) {
			PartnerDeptMgr partnerDeptMgr=(PartnerDeptMgr)getBean("partnerDeptMgr");
			list0=partnerDeptMgr.getPartnerDepts("and deptMagId='"+deptid+"'");
			if (list0.size()!=0&&list0!=null) {//不等于0表示是代维公司的
				search.addFilterILike("maintainCompany", deptid+"%");//代维公司权限限定
			}else {
				ITawSystemDeptManager deptManager=(ITawSystemDeptManager)getBean("ItawSystemDeptManager");
				TawSystemDept dept=deptManager.getDeptinfobydeptid(deptid,"0");
				if (dept!=null) {
					search.addFilterILike("area", dept.getAreaid()+"%");//区域权限限定
				}
			}
		}
		search.addSort("id", false);
		search.addFilterNotEqual("deleted", "1");//1表示不删除
		search.addFilterNotEqual("visible", "1");//1表示不可见，正在执行流程申请中
		search=CommonUtils.getSqlFromRequestMap(request, search);
		String companyId=StaticMethod.null2String(request.getParameter("company_id"));
		String companyName=StaticMethod.null2String(request.getParameter("company_name"));
		String areaName=StaticMethod.null2String(request.getParameter("area_name"));
		String companyDeptId=ResourceInfoUtils.deptUUidToDeptId(companyId);
		search.addFilterILike("maintainCompany", companyDeptId+"%");
		request.setAttribute("companyName", companyName);
		request.setAttribute("areaName", areaName);
		request.setAttribute("companyId", companyDeptId);
		SearchResult<Car> searchResult=carService.searchAndCount(search);
		List list=searchResult.getResult();
		int resultSize=searchResult.getTotalCount();
		request.setAttribute("carList", list);
		request.setAttribute("resultSize", resultSize);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("userid", sessionForm.getUserid());
		if (list0.size()!=0&&list0!=null) {
			return mapping.findForward("partnerList");
		}
		return mapping.findForward("list");
	}
	/**
	 * car修改
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		CarService carService=(CarService)getBean("carService");
		String id=StaticMethod.null2String(request.getParameter("id"));
		Car car=carService.find(id);
		request.setAttribute("car", car);
		return mapping.findForward("edit");
	}
	/**
	 * 更新车辆信息
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
			CarService carService=(CarService)getBean("carService");
			String id=request.getParameter("id");
			Car car=carService.find(id);
			String oldCarNumber=car.getCarNumber();
			String oldCarGPSNumber=car.getCarGPSNumber();
			BeanUtils.populate(car, request.getParameterMap());
			String maintainCompany=car.getMaintainCompany();
			if (!"".equals(maintainCompany)&&maintainCompany.length()>20) {
				PartnerDeptMgr deptMgr=(PartnerDeptMgr)getBean("partnerDeptMgr");
				PartnerDept dept=deptMgr.getPartnerDept(maintainCompany);
				car.setMaintainCompany(StaticMethod.null2String(dept.getDeptMagId()));
			}
			String newCarNumber=car.getCarNumber();
			String newCarGPSNumber=car.getCarGPSNumber();
			String driverUserId=car.getDriver();
			PartnerUserMgr partnerUserMgr=(PartnerUserMgr)getBean("partnerUserMgr");
			PartnerUser user=partnerUserMgr.getPartnerUserByUserId(driverUserId);
			car.setDriver(StaticMethod.null2String(user.getId()));
			carService.save(car);
			 //刷新静态资源
			 //如果修改后的车牌号未在静态缓存区域中时要删除静态缓存数据中的该车牌号，同时将新的车牌号添加到list中
			 if (!PnrProcessCach.carnumAndGPSnumListCach.containsKey(newCarNumber)) {
				 PnrProcessCach.carnumAndGPSnumListCach.remove(oldCarNumber);
				 PnrProcessCach.carnumAndGPSnumListCach.put(newCarNumber,id+","+newCarNumber);
			}
			 if (!PnrProcessCach.carnumAndGPSnumListCach.containsKey(newCarGPSNumber)) {
				 PnrProcessCach.carnumAndGPSnumListCach.remove(oldCarGPSNumber);
				 PnrProcessCach.carnumAndGPSnumListCach.put(newCarGPSNumber,id+","+newCarGPSNumber);
			 }
			return mapping.findForward("forwardList");
	}
	/**
	 * 
	 *fengguangping
	 * Nov 5, 2012-3:22:32 PM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void download(ActionMapping mapping, ActionForm form,
	HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String rootPath = request.getRealPath(File.separator+"partner"+ File.separator+"processExcelModel");
		String fileName = "增加车辆.xls";
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
	public ActionForward importData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			response.setCharacterEncoding("utf-8");
			CarForm uploadForm=(CarForm)form;
			FormFile formFile=uploadForm.getImportFile();
			PrintWriter writer=null;
			try {
				CarService carService=(CarService)getBean("carService");
				writer=response.getWriter();
				ImportResult result=carService.importFromFile(formFile);
				if (result.getResultCode().equals("200")) {
					writer.write(
						new Gson().toJson(new ImmutableMap.Builder<String, String>()
							.put("success", "true")
							.put("msg", "ok")
							.put("infor", "导入成功,共计导入"+result.getImportCount()+"条记录").build()));
				}
			} catch (Exception e) {
				//发生异常时重新加载静态资源
				PnrProcessCach.carnumAndGPSnumListCach.clear();
				PnrProcessCach.loadCarnumAndGPSnumList();
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
	 * 在统计页面中，统计条件和统计项目的id命名为表的列名称，name为实体名称;
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
		String area=StaticMethod.nullObject2String(request.getParameter("area_id"),"");
		String maintainCompany=StaticMethod.nullObject2String(request.getParameter("maintainCompany_id"),"");
		if (!"".equals(maintainCompany)&&maintainCompany.length()>20) {
			PartnerDeptMgr deptMgr=(PartnerDeptMgr)getBean("partnerDeptMgr");
			PartnerDept dept=deptMgr.getPartnerDept(maintainCompany);
			maintainCompany=StaticMethod.null2String(dept.getDeptMagId());
		}
		String exportFlag=StaticMethod.nullObject2String(request.getParameter("exportFlag"),"");
		String whereStr=" ";
		if(!"".equals(area)){
			whereStr+=" and area like '"+area+"'";
		}
		if (!"".equals(maintainCompany)) {
			whereStr+=" and company like '"+maintainCompany+"'";
		}
		String statisticsSql="select "+searchStr+",count(id) as count from  pnr_carInfo where " +"deleted <> 1 and visible <> 1 "+whereStr+"  group by "+group+"  order by "+group;
		List<String> headList = new ArrayList<String>();
		//[area, maintenanceCompany, maintenanceMajor, carName, carType, carProperty, carStatus]
		for (int i = 0; i < rows.length; i++) {
			if ("areaTypeLikeArea".equals(rows[i])) {
				headList.add("所属区域");
			}else if ("companyTypeLikeDept".equals(rows[i])) {
				headList.add("代维公司");
			}else if ("car_number".equals(rows[i])) {
				headList.add("车牌号");
			}else if ("car_type".equals(rows[i])) {
				headList.add("车型");
			}else if ("propertyTypeLikedict".equals(rows[i])) {
				headList.add("产权属性");
			}else if ("statusTypeLikedict".equals(rows[i])) {
				headList.add("车辆状态");
			}
		}
		headList.add("总数");
		List<List<TdObjModel>> tempList = TableHelper.verticalGrowp(rows,statisticsSql,"/partner/resourceInfo/car.do?method=searchInto");
		request.setAttribute("tableList", tempList);
		request.setAttribute("headList", headList);
		request.setAttribute("areaStringLike", area);
		request.setAttribute("maintainCompanyStringLike", maintainCompany);
		request.setAttribute("checkedList", checkedString);
		if (!"".equals(exportFlag)&&exportFlag.equals("2")) {
			//执行导出
			String fileName="车辆信息统计";
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
		String whereStr=" ";
		String area=StaticMethod.nullObject2String(request.getParameter("areatypelikearea"));
		String maintenanceCompany=StaticMethod.nullObject2String(request.getParameter("companytypelikedept"));
		if (!"".equals(maintenanceCompany)&&maintenanceCompany.length()>20) {
			PartnerDeptMgr deptMgr=(PartnerDeptMgr)getBean("partnerDeptMgr");
			PartnerDept dept=deptMgr.getPartnerDept(maintenanceCompany);
			String maintainCompany=StaticMethod.null2String(dept.getDeptMagId());
		}
		String carNumber=StaticMethod.nullObject2String(request.getParameter("car_number"));
		String carType=StaticMethod.nullObject2String(request.getParameter("car_type"));
		String carProperty=StaticMethod.nullObject2String(request.getParameter("propertytypelikedict"));
		String carStatus=StaticMethod.nullObject2String(request.getParameter("statustypelikedict"));
		if (!"".equals(area)) {
			whereStr+=" and area='"+area+"'";
		}
		if (!"".equals(maintenanceCompany)) {
			whereStr+=" and company='"+maintenanceCompany+"'";
		}
		if (!"".equals(carNumber)) {
			whereStr+=" and car_number='"+carNumber+"'";
		}
		if (!"".equals(carType)) {
			whereStr+=" and car_type='"+carType+"'";
		}
		if (!"".equals(carProperty)) {
			whereStr +=" and property='"+carProperty+"'";
		}
		String sql=" select * from  pnr_carInfo where deleted <> 1 and visible <> 1 "+whereStr;
		String pageIndexName = new org.displaytag.util.ParamEncoder("carList").
		encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageIndex = new Integer(GenericValidator.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
		: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		String countSql="select count(*) as count from ( "+sql+")";
		SearchUtil<Car> search = new SearchUtil<Car>(Car.class,"car",sql,countSql);
		PageData<Car> pageData = search.getDataList(pageIndex);
		request.setAttribute("pageSize", pageData.getPageSize());
		request.setAttribute("resultSize", pageData.getTotalCount());
		request.setAttribute("carList", pageData.getList());
		return mapping.findForward("statisticsList");
	}
	/**
	 * 验证车牌的唯一性
	 *fengguangping
	 * Sep 24, 2012-4:18:46 PM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void onlyValidate(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("utf-8");
		PrintWriter writer=null;
		try {
			CarService carService=(CarService)getBean("carService");
			String carNumber=request.getParameter("carNumber");
			String id=StaticMethod.null2String(request.getParameter("id"));
			writer=response.getWriter();
			List<Car> list=carService.findByCarNumber(carNumber,id);
			if (list!=null&list.size()>0) {
						writer.write(new Gson().toJson(new ImmutableMap.Builder<String, String>()
										.put("success", "true")
										.put("msg", "ok")
										.put("infor", "notOnly").build()));
						return;
			}
			writer.write(	new Gson().toJson(new ImmutableMap.Builder<String, String>()
							.put("success", "true")
							.put("msg", "ok")
							.put("infor", "only").build()));
		} catch (Exception e) {
			e.printStackTrace();
			writer.write(
					new Gson().toJson(new ImmutableMap.Builder<String, String>()
							.put("success", "false")
							.put("msg", "failure")
							.put("infor", e.getMessage()).build()));
		}finally{
			if(writer!=null){
				writer.close();
			}
		}
	}
	/**
	 * 验证驾驶人员是否是所选代维公司人员
	 *fengguangping
	 * Sep 24, 2012-4:18:46 PM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void dirverValidate(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) 
	throws Exception{
		response.setCharacterEncoding("utf-8");
		PrintWriter writer=null;
		try {
			String companyName=StaticMethod.null2String(request.getParameter("companyName"));
			String driverName=StaticMethod.null2String(request.getParameter("driverName"));
			writer=response.getWriter();
			PartnerUserMgr partnerUserMgr=(PartnerUserMgr)getBean("partnerUserMgr");
			List list=new ArrayList<PartnerUser>();
			String where=" and partnerUser.name='"+driverName+"' and partnerUser.deleted='0' and partnerUser.deptName='"+companyName+"'";
			list=partnerUserMgr.getPartnerUsers(where);
			if (list.size()>0) {
				writer.write(new Gson().toJson(new ImmutableMap.Builder<String, String>()
						.put("success", "true")
						.put("msg", "ok")
						.put("infor", "same").build()));
				return;
			}else {
				writer.write(	new Gson().toJson(new ImmutableMap.Builder<String, String>()
						.put("success", "true")
						.put("msg", "ok")
						.put("infor", "notsame").build()));
			}
		} catch (Exception e) {
			e.printStackTrace();
			writer.write(
					new Gson().toJson(new ImmutableMap.Builder<String, String>()
							.put("success", "false")
							.put("msg", "failure")
							.put("infor", e.getMessage()).build()));
		}finally{
			if(writer!=null){
				writer.close();
			}
		}
	}
	/**
	 * 验证GPSNumber的唯一性
	 *fengguangping
	 * Sep 24, 2012-4:18:46 PM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	public void onlyGPSNumberValidate(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("utf-8");
		PrintWriter writer=null;
		try {
			CarService carService=(CarService)getBean("carService");
			String carGPSNumber=request.getParameter("carGPSNumber");
			String id=StaticMethod.null2String(request.getParameter("id"));
			writer=response.getWriter();
			List<Car> list=carService.findByCarGPSNumber(carGPSNumber,id);
			if (list!=null&list.size()>0) {
				writer.write(new Gson().toJson(new ImmutableMap.Builder<String, String>()
						.put("success", "true")
						.put("msg", "ok")
						.put("infor", "notOnly").build()));
				return;
			}
			writer.write(	new Gson().toJson(new ImmutableMap.Builder<String, String>()
					.put("success", "true")
					.put("msg", "ok")
					.put("infor", "only").build()));
		} catch (Exception e) {
			e.printStackTrace();
			writer.write(
					new Gson().toJson(new ImmutableMap.Builder<String, String>()
							.put("success", "false")
							.put("msg", "failure")
							.put("infor", e.getMessage()).build()));
		}finally{
			if(writer!=null){
				writer.close();
			}
		}
	}
	/**
	 * 
	 *@Description:根据车牌号查询车辆历史任务
	 *@date May 15, 2013 5:28:27 PM
	 *@author Fengguangping fengguangping@boco.com.cn
	 *@param mapping
	 *@param form
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	public ActionForward getCarAllTaskList(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String carNumber=new String(request.getParameter("carNumber").getBytes("ISO-8859-1"),"UTF-8");
//		String carNumber= request.getParameter("carNumber");
//        carNumber = java.net.URLDecoder.decode(carNumber,"gbk");

        CommonSpringJdbcServiceImpl csjs=(CommonSpringJdbcServiceImpl)getBean("commonSpringJdbcService");
		//request.setCharacterEncoding("utf-8");
		String whereStr="";
		whereStr=CommonUtils.getSqlFromRequestMap(request, whereStr);
		String sql="select t1.APPLY_TIME,t1.APPLY_USER,t1.APPLY_USER_DEPT,t1.BACK_TIME,t1.APPROVE_TIME,t1.APPROVE_USER," +
				"t1.APPROVE_USER_DEPT,t2.CAR_NUM,t2.TASK_NAME,t2.TASK_TYPE" +
				" from pnr_car_approve t1,pnr_car_task t2  where t1.id=t2.car_approve_id  and t1.approve_statue='2' and t1.car_num='"+carNumber+"' "+whereStr;
		int pageIndex = CommonUtils.getFirstResultOfDisplayTag(request, "list");
		int pageSize = 15;
		int skip = pageIndex*pageSize;
		List<ListOrderedMap> list=new ArrayList<ListOrderedMap>();
		list=csjs.queryForList(CommonSqlHelper.formatPageSql(sql, skip, pageSize));
		int size=CommonSqlHelper.getResultSize(sql);
		request.setAttribute("list",list);
		request.setAttribute("pagesize",pageSize);
		request.setAttribute("size",size);
		request.setAttribute("carNumber",carNumber);
		return mapping.findForward("goToCarAllTaskListPage");
	}
	/**
	 * 
	 *@Description:车辆状态为使用中时查看车辆的使用人和任务
	 *@date May 15, 2013 5:28:27 PM
	 *@author Fengguangping fengguangping@boco.com.cn
	 *@param mapping
	 *@param form
	 *@param request
	 *@param response
	 *@return
	 *@throws Exception
	 */
	public ActionForward checkCarCurrentTask(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String carNumber=request.getParameter("carNumber");
		String  applyId=request.getParameter("applyId");
		ICarTaskService carTaskService=(ICarTaskService)getBean("carTaskDaoService");
		CarTask carTask=carTaskService.getCarCurrentTask(carNumber, applyId);
		if (carTask!=null) {
			request.setAttribute("carTask", carTask);
			request.setAttribute("carNumber", carNumber);
			return mapping.findForward("goToshowCurrent");
		}else {
			request.setAttribute("msg", carNumber+" 获取当前任务信息失败");
			return mapping.findForward("fail");
		}
	}
}
