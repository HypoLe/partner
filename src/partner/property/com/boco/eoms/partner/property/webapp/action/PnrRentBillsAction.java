package com.boco.eoms.partner.property.webapp.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
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
import com.boco.eoms.partner.property.model.PnrRentBills;
import com.boco.eoms.partner.property.service.IPnrRentBillsService;
import com.boco.eoms.partner.property.util.PnrPropertyAgreementConstant;
import com.boco.eoms.partner.property.util.PnrPropertyAgreementHandler;
import com.boco.eoms.partner.property.webapp.form.PnrRentBillsForm;
import com.boco.eoms.partner.resourceInfo.util.ImportResult;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;


/**
 * 类说明：物业合同管理-租赁费用 Action类
 * 创建人： fengguangping
 * 创建时间：2012-08-27 16:57:48
 */
public class PnrRentBillsAction extends BaseAction {
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("gotoAddPnrRentBillsPage");
	}
	
	/**
	 * 
	 * 方法说明：跳转到PnrRentBills添加页面
	 * 作   者: fengguangping
	 * 创建时间: Aug 27, 2012-3:55:35 PM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward gotoAddPnrRentBillsPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {  
		//设置自己的初始化数据
		IPnrRentBillsService pnrRentBillsService = (IPnrRentBillsService)this.getBean("pnrRentBillsService");
		String id = StaticMethod.null2String(request.getParameter("id"));
		if(!"".equals(id)) {
			PnrRentBills pnrRentBills=pnrRentBillsService.find(id);
			PnrPropertyAgreementHandler handler=new PnrPropertyAgreementHandler();
			String remindObject=pnrRentBills.getRemindObject();
			JSONArray sendUserAndRoles=handler.remindObject2Userids(remindObject);
			List<String> phonesList=handler.remindObject2phones(remindObject);
			request.setAttribute("sendUserAndRoles", sendUserAndRoles);
			request.setAttribute("phonesList",phonesList);
			request.setAttribute("pnrRentBills", pnrRentBills);
		}
		return mapping.findForward("gotoAddPnrRentBillsPage");
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
	public ActionForward savePnrRentBills(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String id = StaticMethod.null2String(request.getParameter("id"));
		if(!"".equals(id)) {
			return this.editPnrRentBills(mapping, form, request, response);
		}
		IPnrRentBillsService pnrRentBillsService = (IPnrRentBillsService)this.getBean("pnrRentBillsService");
		PnrRentBills pnrRentBills = new PnrRentBills();
		BeanUtils.populate(pnrRentBills, request.getParameterMap());
		//pnrRentBills.setAddTime(StaticMethod.getCurrentDateTime());
		//pnrRentBills.setAddUser(this.getUserId(request));
		PnrPropertyAgreementHandler handler=new PnrPropertyAgreementHandler();
		String[] remindArray=request.getParameterValues("remindObject");
		String remindObjects=handler.remindObejct2UserIdAndPhones(remindArray);//解析提醒对象
		pnrRentBills.setRemindObject(remindObjects);
		pnrRentBills.setPayStatus(PnrPropertyAgreementConstant.HAVEPAID);
		String districtId=StaticMethod.null2String(pnrRentBills.getRelatedDistrict());
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
		pnrRentBills.setCity(cityId);
		pnrRentBills.setProvince(provinceId);
		pnrRentBillsService.save(pnrRentBills);
		handler.setPnrRentBillsValue2PnrRentBillsCount(pnrRentBills);
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
	public ActionForward editPnrRentBills(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		IPnrRentBillsService pnrRentBillsService = (IPnrRentBillsService)this.getBean("pnrRentBillsService");
		String id = StaticMethod.null2String(request.getParameter("id"));
		PnrRentBills pnrRentBills = pnrRentBillsService.find(id);
		BeanUtils.populate(pnrRentBills, request.getParameterMap());
		PnrPropertyAgreementHandler handler=new PnrPropertyAgreementHandler();
		String[] remindArray=request.getParameterValues("remindObject");
		String remindObjects=handler.remindObejct2UserIdAndPhones(remindArray);//解析提醒对象
		pnrRentBills.setRemindObject(remindObjects);
		pnrRentBills.setPayStatus(PnrPropertyAgreementConstant.HAVEPAID);
		String districtId=StaticMethod.null2String(pnrRentBills.getRelatedDistrict());
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
		pnrRentBills.setCity(cityId);
		pnrRentBills.setProvince(provinceId);
		pnrRentBillsService.save(pnrRentBills);
		handler.setPnrRentBillsValue2PnrRentBillsCount(pnrRentBills);//将该条信息添加到count表中；
		return mapping.findForward("success");
	}
	
	/**
	 * 
	 * 方法说明：跳转到PnrRentBills详情页面
	 * 作   者: fengguangping
	 * 创建时间: Aug 27, 2012-3:55:35 PM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward gotoPnrRentBillsDetailPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		IPnrRentBillsService pnrRentBillsService = (IPnrRentBillsService)this.getBean("pnrRentBillsService");
		PnrRentBills pnrRentBills = new PnrRentBills();
		String id = StaticMethod.null2String(request.getParameter("id"));
		if(!"".equals(id)) {
			pnrRentBills = pnrRentBillsService.find(id);
		}
		String remindObject=pnrRentBills.getRemindObject();
		String[] remindObjects=remindObject.split(";");
		String[] userids=remindObjects[0].substring("userIds:".length()).split(",");
		if (remindObjects.length>1) {
			String[] phones=remindObjects[1].substring("phones:".length()).split(",");
			request.setAttribute("phones", phones);
		}
		request.setAttribute("userids", userids);
		request.setAttribute("pnrRentBills", pnrRentBills);
		return mapping.findForward("gotoPnrRentBillsDetailPage");
	}
	
	
	/**
	 * 
	 * 方法说明：跳转到PnrRentBills列表页面
	 * 作   者: fengguangping
	 * 创建时间: Aug 27, 2012-3:55:35 PM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward gotoPnrRentBillsListPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		IPnrRentBillsService pnrRentBillsService = (IPnrRentBillsService)this.getBean("pnrRentBillsService");
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
		int firstResult = CommonUtils.getFirstResultOfDisplayTag(request, "pnrRentBillsList");
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		search = CommonUtils.getSqlFromRequestMap(request, search);
		//search.addSortDesc("addTime");
		SearchResult<PnrRentBills> searchResult = pnrRentBillsService.searchAndCount(search);
		List<PnrRentBills> pnrRentBillsList = searchResult.getResult();
		request.setAttribute("pnrRentBillsList",pnrRentBillsList);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("size", searchResult.getTotalCount());
		return mapping.findForward("gotoPnrRentBillsListPage");
	}
	
	/**
	 * 
	 * 方法说明：删除PnrRentBills
	 * 作   者: fengguangping
	 * 创建时间: Aug 27, 2012-3:55:35 PM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deletePnrRentBills(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setCharacterEncoding("utf-8");
		try {
			IPnrRentBillsService pnrRentBillsService = (IPnrRentBillsService)this.getBean("pnrRentBillsService");
			String id = request.getParameter("id");
			PnrRentBills pnrRentBills = pnrRentBillsService.find(id);
			pnrRentBillsService.remove(pnrRentBills);
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
		String fileName=PnrPropertyAgreementConstant.RENTBILLXLSFILENAME;
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
			PnrRentBillsForm pnrRentBillsForm=(PnrRentBillsForm)form;
			FormFile formFile=pnrRentBillsForm.getImportFile();
			IPnrRentBillsService pnrRentBillsService=(IPnrRentBillsService)getBean("pnrRentBillsService");
			ImportResult importResult=pnrRentBillsService.importFromFile(formFile);
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
}
