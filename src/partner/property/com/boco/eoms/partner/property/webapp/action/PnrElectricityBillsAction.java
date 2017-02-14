package com.boco.eoms.partner.property.webapp.action;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import utils.PartnerPrivUtils;

import atg.taglib.json.util.JSONArray;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.loging.BocoLog;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.common.utils.CommonConstants;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.partner.property.model.PnrElectricityBills;
import com.boco.eoms.partner.property.model.PnrElectricityBillsCount;
import com.boco.eoms.partner.property.model.PnrPropertyAgreement;
import com.boco.eoms.partner.property.service.IPnrElectricityBillsCountService;
import com.boco.eoms.partner.property.service.IPnrElectricityBillsService;
import com.boco.eoms.partner.property.service.IPnrPropertyAgreementService;
import com.boco.eoms.partner.property.util.PnrPropertyAgreementConstant;
import com.boco.eoms.partner.property.util.PnrPropertyAgreementHandler;
import com.boco.eoms.partner.property.webapp.form.PnrElectricityBillsForm;
import com.boco.eoms.partner.resourceInfo.util.ImportResult;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;


/**
 * 类说明：物业合同管理-电费费用记录 Action类
 * 创建人： fengguangping
 * 创建时间：2012-08-27 16:57:46
 */
public class PnrElectricityBillsAction extends BaseAction {
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("gotoAddPnrElectricityBillsPage");
	}
	
	/**
	 * 
	 * 方法说明：跳转到PnrElectricityBills添加页面
	 * 作   者: fengguangping
	 * 创建时间: Aug 27, 2012-3:55:35 PM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward gotoAddPnrElectricityBillsPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {  
		//设置自己的初始化数据
		IPnrElectricityBillsService pnrElectricityBillsService = (IPnrElectricityBillsService)this.getBean("pnrElectricityBillsService");
		String id = StaticMethod.null2String(request.getParameter("id"));
		if(!"".equals(id)) {
			PnrElectricityBills pnrElectricityBills =pnrElectricityBillsService.find(id);
			//状态为已经支付的无需自动填入上期读数，只有未支付的点击修改才关联上期读数。
			if (!pnrElectricityBills.getPayStatus().equals(PnrPropertyAgreementConstant.HAVEPAID)) {
				Search search=new Search();
				String agreementNo=pnrElectricityBills.getRelatedAgreementNo();
				search.addFilterEqual("relatedAgreementNo", agreementNo);
				search.addFilterEqual("payStatus", PnrPropertyAgreementConstant.HAVEPAID);
				search.addSort("planPayDate", true);
				SearchResult<PnrElectricityBills> searchResult=pnrElectricityBillsService.searchAndCount(search);
				List<PnrElectricityBills> list=searchResult.getResult();
				if (list!=null&list.size()>0) {
					double lastNum=list.get(0).getNowNum();//获取上期的读数。
					pnrElectricityBills.setLastNum(lastNum);
				}
			}
			PnrPropertyAgreementHandler handler=new PnrPropertyAgreementHandler();
			String remindObject=pnrElectricityBills.getRemindObject();
			JSONArray sendUserAndRoles=handler.remindObject2Userids(remindObject);
			List<String> phonesList=handler.remindObject2phones(remindObject);
			request.setAttribute("sendUserAndRoles", sendUserAndRoles);
			request.setAttribute("phonesList",phonesList);
			request.setAttribute("pnrElectricityBills", pnrElectricityBills);
		}
		return mapping.findForward("gotoAddPnrElectricityBillsPage");
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
	public ActionForward savePnrElectricityBills(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String id = StaticMethod.null2String(request.getParameter("id"));
		if(!"".equals(id)) {
			return this.editPnrElectricityBills(mapping, form, request, response);
		}
		IPnrElectricityBillsService pnrElectricityBillsService = (IPnrElectricityBillsService)this.getBean("pnrElectricityBillsService");
		PnrElectricityBills pnrElectricityBills = new PnrElectricityBills();
		BeanUtils.populate(pnrElectricityBills, request.getParameterMap());
		PnrPropertyAgreementHandler handler=new PnrPropertyAgreementHandler();
		String[] remindArray=request.getParameterValues("remindObject");
		String remindObjects=handler.remindObejct2UserIdAndPhones(remindArray);//解析提醒对象
		pnrElectricityBills.setRemindObject(remindObjects);
		String districtId=StaticMethod.null2String(pnrElectricityBills.getRelatedDistrict());
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
		pnrElectricityBills.setCity(cityId);
		pnrElectricityBills.setProvince(provinceId);
		pnrElectricityBills.setPayStatus(PnrPropertyAgreementConstant.HAVEPAID);
		pnrElectricityBills.setCreateTime(CommonUtils.toEomsStandardDate(new Date()));
		pnrElectricityBillsService.save(pnrElectricityBills);
		handler.setPnrElectricityBillsValue2PnrElectricityBillsCount(pnrElectricityBills);
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
	public ActionForward editPnrElectricityBills(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		IPnrElectricityBillsService pnrElectricityBillsService = (IPnrElectricityBillsService)this.getBean("pnrElectricityBillsService");
		String id = StaticMethod.null2String(request.getParameter("id"));
		PnrElectricityBills pnrElectricityBills = pnrElectricityBillsService.find(id);
		BeanUtils.populate(pnrElectricityBills, request.getParameterMap());
		Date settlementTimeSectStart=pnrElectricityBills.getSettlementTimeSectStart();
		Date settlementTimeSectEnd=pnrElectricityBills.getSettlementTimeSectEnd();
		double totalMoney=pnrElectricityBills.getMustPayMoney();
		String payCircle=pnrElectricityBills.getPayCircle();
		if (!payCircle.equals("1250205")) {//支付周期不为其他时
			PnrPropertyAgreementHandler handler=new PnrPropertyAgreementHandler();
			int payCirCleInt=handler.payCircleStr2Int(payCircle);
		}
		PnrPropertyAgreementHandler handler=new PnrPropertyAgreementHandler();
		String[] remindArray=request.getParameterValues("remindObject");
		String remindObjects=handler.remindObejct2UserIdAndPhones(remindArray);//解析提醒对象
		pnrElectricityBills.setRemindObject(remindObjects);
		String districtId=StaticMethod.null2String(pnrElectricityBills.getRelatedDistrict());
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
		pnrElectricityBills.setCity(cityId);
		pnrElectricityBills.setProvince(provinceId);
		pnrElectricityBills.setPayStatus(PnrPropertyAgreementConstant.HAVEPAID);
		//将本条信息填入Count表中
		Search search=new Search();
		search.addFilterLike("relatedAgreementid", pnrElectricityBills.getRefId());
		//search.addFilterLike("timeYear",)
		handler.setPnrElectricityBillsValue2PnrElectricityBillsCount(pnrElectricityBills);
		pnrElectricityBillsService.save(pnrElectricityBills);
		handler.setPnrElectricityBillsValue2PnrElectricityBillsCount(pnrElectricityBills);
		return mapping.findForward("success");
	}
	
	/**
	 * 
	 * 方法说明：跳转到PnrElectricityBills详情页面
	 * 作   者: fengguangping
	 * 创建时间: Aug 27, 2012-3:55:35 PM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward gotoPnrElectricityBillsDetailPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		IPnrElectricityBillsService pnrElectricityBillsService = (IPnrElectricityBillsService)this.getBean("pnrElectricityBillsService");
		PnrElectricityBills pnrElectricityBills = new PnrElectricityBills();
		String id = StaticMethod.null2String(request.getParameter("id"));
		if(!"".equals(id)) {
			pnrElectricityBills = pnrElectricityBillsService.find(id);
		}
		String remindObject=pnrElectricityBills.getRemindObject();
		String[] remindObjects=remindObject.split(";");
		String[] userids=remindObjects[0].substring("userIds:".length()).split(",");
		if (remindObjects.length>1) {
			String[] phones=remindObjects[1].substring("phones:".length()).split(",");
			request.setAttribute("phones", phones);
		}
		request.setAttribute("userids", userids);
		request.setAttribute("pnrElectricityBills", pnrElectricityBills);
		return mapping.findForward("gotoPnrElectricityBillsDetailPage");
	}
	
	
	/**
	 * 
	 * 方法说明：跳转到PnrElectricityBills列表页面
	 * 作   者: fengguangping
	 * 创建时间: Aug 27, 2012-3:55:35 PM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward gotoPnrElectricityBillsListPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		IPnrElectricityBillsService pnrElectricityBillsService = (IPnrElectricityBillsService)this.getBean("pnrElectricityBillsService");
		Search search = new Search();
		String payStatus=request.getParameter("payStatus");
		TawSystemSessionForm sessionForm=this.getUser(request);
		String deptid=sessionForm.getDeptid();
		if (!"admin".equals(sessionForm.getUserid())) {
			ITawSystemDeptManager deptManager=(ITawSystemDeptManager)getBean("ItawSystemDeptManager");
			TawSystemDept dept=deptManager.getDeptinfobydeptid(deptid,"0");
			if (dept!=null) {
				search.addFilterILike("relatedDistrict", StaticMethod.null2String(dept.getAreaid())+"%");//区域权限限定
			}
		}
		search.addFilterEqual("payStatus", payStatus);
		int firstResult = CommonUtils.getFirstResultOfDisplayTag(request, "pnrElectricityBillsList");
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		search = CommonUtils.getSqlFromRequestMap(request, search);
		//search.addSortDesc("addTime");
		SearchResult<PnrElectricityBills> searchResult = pnrElectricityBillsService.searchAndCount(search);
		List<PnrElectricityBills> pnrElectricityBillsList = searchResult.getResult();
		request.setAttribute("pnrElectricityBillsList",pnrElectricityBillsList);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("size", searchResult.getTotalCount());
		return mapping.findForward("gotoPnrElectricityBillsListPage");
	}
	
	/**
	 * 
	 * 方法说明：删除PnrElectricityBills
	 * 作   者: fengguangping
	 * 创建时间: Aug 27, 2012-3:55:35 PM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deletePnrElectricityBills(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		response.setCharacterEncoding("utf-8");
		try {
			IPnrElectricityBillsService pnrElectricityBillsService = (IPnrElectricityBillsService)this.getBean("pnrElectricityBillsService");
			String id = request.getParameter("id");
			PnrElectricityBills pnrElectricityBills =pnrElectricityBillsService.find(id);
			pnrElectricityBillsService.remove(pnrElectricityBills);
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
		String fileName=PnrPropertyAgreementConstant.ELECTRICITYBILLXLSFILENAME;
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
			writer=response.getWriter();
			PnrElectricityBillsForm pnrElectricityBillsForm=(PnrElectricityBillsForm)form;
			FormFile formFile=pnrElectricityBillsForm.getImportFile();
			IPnrElectricityBillsService pnrElectricityBillsService=(IPnrElectricityBillsService)getBean("pnrElectricityBillsService");
			ImportResult importResult=pnrElectricityBillsService.importFromFile(formFile);
			writer.write(new Gson().toJson(new ImmutableMap.Builder<String, String>()
				.put("success", "true")
				.put("msg", "ok")
				.put("infor", "文件校验成功！本次共计导入"+importResult.getImportCount()+"条记录。").build()));
		}catch (Exception e) {
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
}
