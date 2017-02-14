package com.boco.eoms.partner.property.webapp.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.axis.types.YearMonth;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.quartz.JobExecutionException;

import utils.PartnerPrivUtils;

import atg.taglib.json.util.JSONArray;
import atg.taglib.json.util.JSONException;
import atg.taglib.json.util.JSONObject;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.interfaceMonitoring.util.interfaceMonitorin;
import com.boco.eoms.commons.loging.BocoLog;
import com.boco.eoms.commons.system.area.model.TawSystemArea;
import com.boco.eoms.commons.system.area.service.ITawSystemAreaManager;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.common.utils.CommonConstants;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.partner.process.model.PartnerProcessLink;
import com.boco.eoms.partner.process.model.PartnerProcessMain;
import com.boco.eoms.partner.process.service.PartnerProcessLinkService;
import com.boco.eoms.partner.process.service.PartnerProcessMainService;
import com.boco.eoms.partner.process.util.PnrProcessHandler;
import com.boco.eoms.partner.property.model.PnrElectricityBills;
import com.boco.eoms.partner.property.model.PnrElectricityBillsCount;
import com.boco.eoms.partner.property.model.PnrPropertyAgreement;
import com.boco.eoms.partner.property.model.PnrRentBills;
import com.boco.eoms.partner.property.model.PnrRentBillsCount;
import com.boco.eoms.partner.property.service.IPnrElectricityBillsCountService;
import com.boco.eoms.partner.property.service.IPnrElectricityBillsService;
import com.boco.eoms.partner.property.service.IPnrPropertyAgreementService;
import com.boco.eoms.partner.property.service.IPnrRentBillsCountService;
import com.boco.eoms.partner.property.service.IPnrRentBillsService;
import com.boco.eoms.partner.property.service.impl.PnrElectricityBillsServiceImpl;
import com.boco.eoms.partner.property.util.PnrPropertyAgreementConstant;
import com.boco.eoms.partner.property.util.PnrPropertyAgreementHandler;
import com.boco.eoms.partner.property.util.PnrPropertyAgreementPayTaskScheduler;
import com.boco.eoms.partner.property.webapp.form.PnrPropertyAgreementForm;
import com.boco.eoms.partner.resourceInfo.util.ImportResult;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;


/**
 * 类说明：电费费用记录物业合同 Action类
 * 创建人： fengguangping
 * 创建时间：2012-08-27 16:57:47
 */
public class PnrPropertyAgreementAction extends BaseAction {
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("gotoAddPnrPropertyAgreementPage");
	}
	
	/**
	 * 
	 * 方法说明：跳转到PnrPropertyAgreement添加页面
	 * 作   者: fengguangping
	 * 创建时间: Aug 27, 2012-3:55:35 PM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward gotoAddPnrPropertyAgreementPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {  
//		PnrPropertyAgreementPayTaskScheduler taskScheduler=new PnrPropertyAgreementPayTaskScheduler();
//		taskScheduler.execute(null);
		String id = StaticMethod.null2String(request.getParameter("id"));
		if(!"".equals(id)) {
			IPnrPropertyAgreementService pnrPropertyAgreementService = (IPnrPropertyAgreementService)this.getBean("pnrPropertyAgreementService");
			PnrPropertyAgreement  pnrPropertyAgreement = pnrPropertyAgreementService.find(id);
			String remindObject = pnrPropertyAgreement.getRemindObject();
			PnrPropertyAgreementHandler handler=new PnrPropertyAgreementHandler();
			JSONArray sendUserAndRoles=handler.remindObject2Userids(remindObject);
			List<String> phonesList=handler.remindObject2phones(remindObject);
			request.setAttribute("sendUserAndRoles", sendUserAndRoles);
			request.setAttribute("phonesList",phonesList);
			request.setAttribute("pnrPropertyAgreement", pnrPropertyAgreement);
		}
		return mapping.findForward("gotoAddPnrPropertyAgreementPage");
	}
	
	/**
	 * 
	 * 方法说明：保存添加页面或修改页面的数据
	 * 作   者: fengguangping
	 * 创建时间: Aug 27, 2012-3:55:35 PM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward savePnrPropertyAgreement(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String id = StaticMethod.null2String(request.getParameter("id"));
		if(!"".equals(id)) {
			return this.editPnrPropertyAgreement(mapping, form, request, response);
		}
		IPnrPropertyAgreementService pnrPropertyAgreementService = (IPnrPropertyAgreementService)getBean("pnrPropertyAgreementService");
		IPnrElectricityBillsService pnrElectricityBillsService=(IPnrElectricityBillsService)getBean("pnrElectricityBillsService");
		IPnrRentBillsService pnrRentBillsService=(IPnrRentBillsService)getBean("pnrRentBillsService");
		PnrPropertyAgreementHandler handler=new PnrPropertyAgreementHandler();
		PnrPropertyAgreement pnrPropertyAgreement = new PnrPropertyAgreement();
		BeanUtils.populate(pnrPropertyAgreement, request.getParameterMap());
		String[] remindArray=request.getParameterValues("remindObject");
		String remindObjects=handler.remindObejct2UserIdAndPhones(remindArray);//解析提醒对象
		//String phones=handler.remindObject2Phones(remindObjects);
		pnrPropertyAgreement.setRemindObject(remindObjects);
		String districtId=StaticMethod.null2String(pnrPropertyAgreement.getDistirct());
		String provinceId="";
		String cityId="";
		if (districtId.length()==PartnerPrivUtils.AreaId_length_County) {
			cityId=districtId.substring(0, PartnerPrivUtils.AreaId_length_City);
			provinceId=districtId.substring(0,PartnerPrivUtils.AreaId_length_Province);
		}else if (districtId.length()==PartnerPrivUtils.AreaId_length_City) {
			cityId=districtId;
			provinceId=districtId.substring(0, PartnerPrivUtils.AreaId_length_Province);
		}else {
			cityId=districtId;
			provinceId=districtId;
		}
		pnrPropertyAgreement.setCity(cityId);
		pnrPropertyAgreement.setProvince(provinceId);
		//pnrPropertyAgreement.setAddTime(StaticMethod.getCurrentDateTime());
		//pnrPropertyAgreement.setAddUser(this.getUserId(request));
		pnrPropertyAgreement.setDeletedFlag("0");
		pnrPropertyAgreement.setAgreementStatus(PnrPropertyAgreementConstant.AGREEMENTEFFECTED);
		pnrPropertyAgreement.setExpireRemind("");
		pnrPropertyAgreement.setCreateTime(CommonUtils.toEomsStandardDate(new Date()));
		pnrPropertyAgreementService.save(pnrPropertyAgreement);
		List list=handler.producePayPlan(pnrPropertyAgreement);
		for (Object object : list) {
			if (object.getClass().getName().equals("com.boco.eoms.partner.property.model.PnrElectricityBills")) {
				pnrElectricityBillsService.save((PnrElectricityBills)object);
			}
			if (object.getClass().getName().equals("com.boco.eoms.partner.property.model.PnrRentBills")) {
				pnrRentBillsService.save((PnrRentBills)object);
			}
		}
		return mapping.findForward("success");
	}
	
	/**
	 * 
	 * 方法说明：保存添加页面或修改页面的数据
	 * 作   者: fengguangping
	 * 创建时间: Aug 27, 2012-3:55:35 PM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward editPnrPropertyAgreement(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		IPnrPropertyAgreementService pnrPropertyAgreementService = (IPnrPropertyAgreementService)this.getBean("pnrPropertyAgreementService");
		String id = StaticMethod.null2String(request.getParameter("id"));
		PnrPropertyAgreement pnrPropertyAgreement =pnrPropertyAgreementService.find(id);
		BeanUtils.populate(pnrPropertyAgreement, request.getParameterMap());
		//将合同设置为有效,如果合同由有效修改为无效，在轮询的时候会将合同的状态由有效转化为无效;
		pnrPropertyAgreement.setAgreementStatus(PnrPropertyAgreementConstant.AGREEMENTEFFECTED);
		PnrPropertyAgreementHandler handler=new PnrPropertyAgreementHandler();
		String[] remindArray=request.getParameterValues("remindObject");
		String remindObjects=handler.remindObejct2UserIdAndPhones(remindArray);//解析提醒对象
		pnrPropertyAgreement.setRemindObject(remindObjects);
		String districtId=StaticMethod.null2String(pnrPropertyAgreement.getDistirct());
		String provinceId="";
		String cityId="";
		if (districtId.length()==PartnerPrivUtils.AreaId_length_County) {
			cityId=districtId.substring(0, PartnerPrivUtils.AreaId_length_City);
			provinceId=districtId.substring(0,PartnerPrivUtils.AreaId_length_Province);
		}else if (districtId.length()==PartnerPrivUtils.AreaId_length_City) {
			cityId=districtId;
			provinceId=districtId.substring(0, PartnerPrivUtils.AreaId_length_Province);
		}else {
			cityId=districtId;
			provinceId=districtId;
		}
		pnrPropertyAgreement.setCity(cityId);
		pnrPropertyAgreement.setProvince(provinceId);
		pnrPropertyAgreementService.save(pnrPropertyAgreement);
		return mapping.findForward("success");
	}
	
	/**
	 * 
	 * 方法说明：跳转到PnrPropertyAgreement详情页面
	 * 作   者: fengguangping
	 * 创建时间: Aug 27, 2012-3:55:35 PM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward gotoPnrPropertyAgreementDetailPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		IPnrPropertyAgreementService pnrPropertyAgreementService = (IPnrPropertyAgreementService)this.getBean("pnrPropertyAgreementService");
		PnrPropertyAgreement pnrPropertyAgreement = new PnrPropertyAgreement();
		String id = StaticMethod.null2String(request.getParameter("id"));
		if(!"".equals(id)) {
			pnrPropertyAgreement = pnrPropertyAgreementService.find(id);
		}
		String remindObject=pnrPropertyAgreement.getRemindObject();
		String[] remindObjects=remindObject.split(";");
		String[] userids=remindObjects[0].substring("userIds:".length()).split(",");
		if (remindObjects.length>1) {
			String[] phones=remindObjects[1].substring("phones:".length()).split(",");
			request.setAttribute("phones", phones);
		}
		request.setAttribute("userids", userids);
		request.setAttribute("pnrPropertyAgreement", pnrPropertyAgreement);
		return mapping.findForward("gotoPnrPropertyAgreementDetailPage");
	}
	
	
	/**
	 * 
	 * 方法说明：跳转到PnrPropertyAgreement列表页面
	 * 作   者: fengguangping
	 * 创建时间: Aug 27, 2012-3:55:35 PM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward  gotoPnrPropertyAgreementListPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		IPnrPropertyAgreementService pnrPropertyAgreementService = (IPnrPropertyAgreementService)this.getBean("pnrPropertyAgreementService");
		Search search = new Search();
		String effet=StaticMethod.null2String(request.getParameter("effect"));
		TawSystemSessionForm sessionForm=this.getUser(request);
		String deptid=sessionForm.getDeptid();
		if (!"admin".equals(sessionForm.getUserid())) {
			ITawSystemDeptManager deptManager=(ITawSystemDeptManager)getBean("ItawSystemDeptManager");
			TawSystemDept dept=deptManager.getDeptinfobydeptid(deptid,"0");
			if (dept!=null) {
				search.addFilterILike("distirct", StaticMethod.null2String(dept.getAreaid())+"%");//区域权限限定
			}
		}
		boolean effectFlag=true;
		if (!"".equals(effet)&&effet.equals(PnrPropertyAgreementConstant.AGREEMENTUNEFFECTED)) {
			search.addFilterEqual("agreementStatus", PnrPropertyAgreementConstant.AGREEMENTUNEFFECTED);
			effectFlag=false;
		}else {
			search.addFilterNotEqual("deletedFlag", "1");
			search.addFilterEqual("agreementStatus", PnrPropertyAgreementConstant.AGREEMENTEFFECTED);
			effectFlag=true;
		}
		int firstResult = CommonUtils.getFirstResultOfDisplayTag(request, "pnrPropertyAgreementList");
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		search = CommonUtils.getSqlFromRequestMap(request, search);
		SearchResult<PnrPropertyAgreement> searchResult = pnrPropertyAgreementService.searchAndCount(search);
		List<PnrPropertyAgreement> pnrPropertyAgreementList = searchResult.getResult();
		request.setAttribute("effectFlag", effectFlag);
		request.setAttribute("pnrPropertyAgreementList",pnrPropertyAgreementList);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("size", searchResult.getTotalCount());
		return mapping.findForward("gotoPnrPropertyAgreementListPage");
	}
	
	/**
	 * 
	 * 方法说明：删除PnrPropertyAgreement
	 * 作   者: fengguangping
	 * 创建时间: Aug 27, 2012-3:55:35 PM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deletePnrPropertyAgreement(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		response.setCharacterEncoding("utf-8");
		try {
			IPnrPropertyAgreementService pnrPropertyAgreementService = (IPnrPropertyAgreementService)this.getBean("pnrPropertyAgreementService");
			String id = request.getParameter("id");
			PnrPropertyAgreement pnrPropertyAgreement = pnrPropertyAgreementService.find(id);
			pnrPropertyAgreement.setDeletedFlag("1");
			pnrPropertyAgreementService.save(pnrPropertyAgreement);
			response.getWriter().write(
					new Gson().toJson(new ImmutableMap.Builder<String, String>()
							.put("success", "true")
							.put("msg", "ok")
							.put("infor", "删除成功！").build()));
		} catch (RuntimeException e) {
			BocoLog.info(this, "删除出错！");
			e.printStackTrace();
			response.getWriter().write(
					new Gson().toJson(new ImmutableMap.Builder<String, String>()
							.put("success", "true")
							.put("msg", "ok")
							.put("infor", "删除出错！").build()));
		} finally {
			return null;
		}
	}
	
	/**
	 * 批量导入excel模板下载
	 *fengguangping
	 * Sep 4, 2012-10:49:38 AM
	 */
	public ActionForward download(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response)	throws Exception {
		String fileName=PnrPropertyAgreementConstant.AGREEMENTXLSFILENAME;
		String rootPath = request.getRealPath("/partner/pnrPropertyAgreement");
		File file = new File(rootPath + File.separator + fileName);
		InputStream inStream = new FileInputStream(file);// 文件的存放路径
		response.reset();
		response.setContentType("application/x-msdownload;charset=GBK");
		response.setCharacterEncoding("GB2312");
		response.setHeader("Content-Disposition", "attachment; filename="+ new String(fileName.getBytes("gbk"), "iso8859-1"));
		// 循环取出流中的数据
		byte[] b = new byte[1024];
		int len = 0;
		 OutputStream os=response.getOutputStream();
		while ((len = inStream.read(b)) > 0)  {
			os.write(b, 0, len);
		}
		os.flush();
		os.close();
		inStream.close();
		return null;
	}
	/**
	 * 导入excel文件
	 */
	public ActionForward importData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("utf-8");
		PrintWriter writer=null;
		try {
			writer =response.getWriter();
			PnrPropertyAgreementForm pnrPropertyAgreementForm=(PnrPropertyAgreementForm)form;
			FormFile formFile=pnrPropertyAgreementForm.getImportFile();
			IPnrPropertyAgreementService pnrPropertyAgreementService=(IPnrPropertyAgreementService)getBean("pnrPropertyAgreementService");
			ImportResult importResult=pnrPropertyAgreementService.importFromFile(formFile);
			writer.write(new Gson().toJson(new ImmutableMap.Builder<String, String>()
						.put("success", "true")
						.put("msg", "ok")
						.put("infor", "文件校验成功！本次共计导入"+importResult.getImportCount()+"条记录。").build()));
		}catch (Exception e) {
			e.printStackTrace();
			writer.write(new Gson().toJson(new ImmutableMap.Builder<String, String>()
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
	public ActionForward gotoAddBills(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception{
		String id=request.getParameter("id");
		IPnrPropertyAgreementService pnrPropertyAgreementService=(IPnrPropertyAgreementService)getBean("pnrPropertyAgreementService");
		PnrPropertyAgreement agreement=pnrPropertyAgreementService.find(id);
		String type=agreement.getAgreementType();
		if (type.equals("1250101")) {//合同类型为电费合同
			IPnrElectricityBillsService pnrElectricityBillsService = (IPnrElectricityBillsService)this.getBean("pnrElectricityBillsService");
			PnrElectricityBills pnrElectricityBills=new PnrElectricityBills();
			pnrElectricityBills.setRefId(id);
			pnrElectricityBills.setRelatedAgreementName(agreement.getAgreementName());
			pnrElectricityBills.setRelatedAgreementNo(agreement.getAgreementNo());
			pnrElectricityBills.setRelatedAgreementType(agreement.getAgreementType());
			pnrElectricityBills.setRelatedDistrict(agreement.getDistirct());
			pnrElectricityBills.setRelatedParta(StaticMethod.null2String(agreement.getParta()));
			pnrElectricityBills.setRelatedPartb(StaticMethod.null2String(agreement.getPartb()));
			pnrElectricityBills.setRelatedSite(StaticMethod.null2String(agreement.getSite()));
			pnrElectricityBills.setPayCircle("1250205");
			//关联上期读数。
			Search search=new Search();
			String agreementNo=pnrElectricityBills.getRelatedAgreementNo();
			search.addFilterEqual("relatedAgreementNo", agreementNo);
			search.addFilterEqual("payStatus", PnrPropertyAgreementConstant.HAVEPAID);
			search.addSort("settlementDate", true);
			SearchResult<PnrElectricityBills> searchResult=pnrElectricityBillsService.searchAndCount(search);
			List<PnrElectricityBills> list=searchResult.getResult();
			if (list!=null&list.size()>0) {
				double lastNum=list.get(0).getNowNum();//获取上期的读数。
				pnrElectricityBills.setLastNum(lastNum);
			}
			request.setAttribute("pnrElectricityBills", pnrElectricityBills);
			return mapping.findForward("goToAddElectricityBills");
		}
		if (type.equals("1250102")) {//合同类型为租赁合同
			PnrRentBills pnrRentBills=new PnrRentBills();
			pnrRentBills.setRefId(id);
			pnrRentBills.setRelatedAgreementName(agreement.getAgreementName());
			pnrRentBills.setRelatedAgreementNo(agreement.getAgreementNo());
			pnrRentBills.setRelatedAgreementType(agreement.getAgreementType());
			pnrRentBills.setRelatedDistrict(agreement.getDistirct());
			pnrRentBills.setRelatedParta(StaticMethod.null2String(agreement.getParta()));
			pnrRentBills.setRelatedPartb(StaticMethod.null2String(agreement.getPartb()));
			pnrRentBills.setRelatedSite(StaticMethod.null2String(agreement.getSite()));
			pnrRentBills.setPayCircle("1250205");
			request.setAttribute("pnrRentBills", pnrRentBills);
			return mapping.findForward("goToAddRentBills");
		}
		return null;
	}
}
