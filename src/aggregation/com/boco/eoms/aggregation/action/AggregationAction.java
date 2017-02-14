package com.boco.eoms.aggregation.action;


import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import utils.Nop3Utils;

import com.boco.eoms.aggregation.form.AggregationForm;
import com.boco.eoms.aggregation.model.Aggregation;
import com.boco.eoms.aggregation.service.AggregationService;
import com.boco.eoms.aggregation.util.AggregationList;
import com.boco.eoms.aggregation.util.AggregationType;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.dict.model.TawSystemDictType;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcService;
import com.boco.eoms.deviceManagement.common.utils.CommonConstants;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerUser;
import com.boco.eoms.partner.baseinfo.webapp.form.PartnerUserForm;
import com.google.common.base.Strings;
import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;
/**
 * @author huhao
 * @系统功能聚合
 */
public final class AggregationAction extends BaseAction {

	public AggregationService getMainBean() {
		String source = AggregationService.class.getSimpleName();
		return (AggregationService) getBean(source.substring(0, 1)
				.toLowerCase().concat(source.substring(1)));
	}

	public CommonSpringJdbcService getJdbcBean() {
		String source = CommonSpringJdbcService.class.getSimpleName();
		return (CommonSpringJdbcService) getBean(source.substring(0, 1)
				.toLowerCase().concat(source.substring(1)));
	}

	public ActionForward goToAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = this.getUser(request);
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		Object token = request.getSession().getAttribute("Token");
		request.setAttribute("userID", userId);
		request.setAttribute("token", token);
		return mapping.findForward("goToAdd");
	}

	/**
	 * @author huhao
	 * @time 2012-3 查看详细页面
	 * @return
	 * @throws Exception
	 */
	public ActionForward goToDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		AggregationService aggregationService = (AggregationService) ApplicationContextHolder
				.getInstance().getBean("aggregationService");
		Aggregation aggregation = aggregationService.find(id);
		request.setAttribute("aggregationList", aggregation);
		return mapping.findForward("goToDetail");
	}

	/**
	 * @author huhao
	 * @time 2012-3 跳转修改信息页面
	 * @return
	 * @throws Exception
	 */
	public ActionForward goToEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = this.getUser(request);
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		String id = request.getParameter("id");
		Aggregation aggregation = this.getMainBean().find(id);
		String accessory = aggregation.getAccessory();
		if(accessory!=null&&!accessory.equals("")){
			String dbPath = "/partner/upUserPhotos/" + accessory;
			request.setAttribute("imgSrc", dbPath);
		}
		request.setAttribute("aggregationList", aggregation);
		request.setAttribute("userID", userId);
		request.setAttribute("goToEdit", true);
		return mapping.findForward("goToEdit");
	}

	/**
	 * @author huhao
	 * @time 2012-3 删除指定信息
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("finally")
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = this.getUser(request);
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		String id = request.getParameter("id");
		Aggregation ad = this.getMainBean().find(id);
		ad.setDeleted(AggregationType.DELETE_TRUE);
		this.getMainBean().save(ad);

//		ActionForward af = new ActionForward("/aggregation.do?method=list",
//				true);
//		return af;
		return mapping.findForward("aggregationList");

	}

	/**
	 * @author huhao
	 * @time 2012-3 删除所有所选信息
	 * @return
	 * @throws Exception
	 */
	public ActionForward deleteMyAll(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String myDealingList[] = StaticMethod.nullObject2String(
				request.getParameter("deleteIds"), "").split(";");
		TawSystemSessionForm sessionform = this.getUser(request);
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		for (String id : myDealingList) {
			Aggregation ad = getMainBean().find(id);
			ad.setDeleted(AggregationType.DELETE_TRUE);
			this.getMainBean().save(ad);
		}
//		ActionForward af = new ActionForward("/aggregation.do?method=list",
//				true);
//		return af;
		return mapping.findForward("aggregationList");
	}

	
	/**
	 * @author huaho
	 * @time 2012-3保存
	 * @return
	 * @throws Exception
	 */
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = request.getParameter("id");
		AggregationService aggregationService = (AggregationService) ApplicationContextHolder
				.getInstance().getBean("aggregationService");
		TawSystemSessionForm sessionform = this.getUser(request);
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		AggregationForm aggregationForm = (AggregationForm) form;
		Aggregation aggregation=new Aggregation();
		BeanUtils.copyProperties(aggregation, aggregationForm);
		if ("".equals(id) || id == null) {
			aggregation.setCreatUser(userId);
			aggregation.setCreatTime(Nop3Utils.toEomsStandardDate(new Date()));
		}
		String moduleUrl=aggregation.getModuleUrl();
		if("/".equals(moduleUrl.substring(0, 1))){
			aggregation.setModuleUrl(moduleUrl.substring(1, moduleUrl.length()));
		}
		aggregation.setDeleted(AggregationType.DELETE_FALSE);
		aggregationService.save(aggregation);
//		ActionForward af = new ActionForward("/aggregation.do?method=list",
//				true);
//		return af;
		return mapping.findForward("aggregationList");
		
	}

	/**
	 * @author huhao
	 * @time 2012-3 设置系统菜单聚合列表
	 * @return
	 */
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		String moduleNameStringLike = request
				.getParameter("moduleNameStringLike");
		String moduleUrlStringLike = request
				.getParameter("moduleUrlStringLike");
		if (null != moduleNameStringLike && !"".equals(moduleNameStringLike)) {
			request.setAttribute("moduleNameStringLike", moduleNameStringLike);
		}
		if (null != moduleUrlStringLike && !"".equals(moduleUrlStringLike)) {
			request.setAttribute("moduleUrlStringLike", moduleUrlStringLike);
		}
		Search search = new Search();
		Map searchMap = request.getParameterMap();
		Set keySet = searchMap.keySet();
		Iterator iterator = keySet.iterator();
		CommonUtils.getSqlFromRequestMap(searchMap, search);
		search.addFilterEqual("deleted", AggregationType.DELETE_FALSE);
		if (!"".equals(request.getParameter("creatTimeA"))
				&& null != request.getParameter("creatTimeA")) {
			search.addFilterGreaterThan("creatTime", request
					.getParameter("creatTimeA"));
		}
		if (!"".equals(request.getParameter("creatTimeB"))
				&& null != request.getParameter("creatTimeB")) {
			search.addFilterLessThan("creatTime", request
					.getParameter("creatTimeB"));
		}
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"aggregationList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.addSortDesc("creatTime");
		SearchResult<Aggregation> searchResult = this.getMainBean()
				.searchAndCount(search);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("aggregationList", searchResult.getResult());
		request.setAttribute("size", searchResult.getTotalCount());
		return mapping.findForward("list");
	}
	/**
	 * @author huhao
	 * @time 2012-3 系统菜单聚合展示
	 * @return
	 */
	public ActionForward showPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		Search search = new Search();
		search.addFilterEqual("deleted", AggregationType.DELETE_FALSE);
		SearchResult<Aggregation> searchResult = this.getMainBean()
				.searchAndCount(search);
		request.setAttribute("showPageList", searchResult.getResult());
		return mapping.findForward("showPage");
	}
	/**
	 * @author huhao
	 * @time 2012-3 设置个人管理列表(引用)
	 * @return
	 */
	public ActionForward userList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		CommonSpringJdbcService commonSpringJdbcService = (CommonSpringJdbcService) ApplicationContextHolder
				.getInstance().getBean("commonSpringJdbcService");
		TawSystemSessionForm sessionform = this.getUser(request);
		String userId = sessionform.getUserid();
		String sql = "select moduleID from portal_aggregationUser where creatUser="
				+ "'"
				+ userId
				+ "'"
				+ "and deleted="
				+ "'"
				+ AggregationType.DELETE_FALSE + "'";
		List<ListOrderedMap> resultList = commonSpringJdbcService
				.queryForList(sql);
		Search search = new Search();
		// Map searchMap = request.getParameterMap();
		// Set keySet = searchMap.keySet();
		// Iterator iterator = keySet.iterator();
		// CommonUtils.getSqlFromRequestMapWithHidden(searchMap,Aggregation.class,
		// search);
		for (int i = 0; i < resultList.size(); i++) {
			Map map = resultList.get(i);
			search.addFilterNotEqual("id", map.get("moduleID"));
		}
		search.addFilterEqual("deleted", AggregationType.DELETE_FALSE);
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"aggregationList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.addSortDesc("creatTime");
		SearchResult<Aggregation> searchResult = this.getMainBean()
				.searchAndCount(search);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("aggregationList", searchResult.getResult());
		request.setAttribute("size", searchResult.getTotalCount());
		return mapping.findForward("list");
	}

	
	

	

	

	
//	/**
//	 * 实现电子照片上传功能，保存在文件夹里
//	 * @param mapping
//	 * @param form
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws Exception
//	 */
//	public ActionForward savaPhoto(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response)
//			throws Exception {
//		AggregationForm aggregationForm = (AggregationForm)form;
//		FormFile file = aggregationForm.getAccessoryName();
//		if(file!=null){
//			System.out.println("file.size() = "+file.getFileSize());
//			response.setContentType("text/html; charset=GBK");
//			String timeTag = StaticMethod.getCurrentDateTime("yyyyMMddHHmmss");
//			String sysTemPaht = request.getRealPath("/"); // 取当前系统路径
//			String fileName = timeTag + file.getFileName().substring(file.getFileName().length()-4,file.getFileName().length());
//			String uploadPath = "/aggregation/upUserPhotos/";
//			String dbPath = uploadPath + fileName;
//			String path = sysTemPaht + uploadPath;
//			String filePath = sysTemPaht + dbPath;
//			File tempFile = new File(path);
//			if (!tempFile.exists()) {
//				tempFile.mkdir();
//			}
//			try {
//				InputStream stream = file.getInputStream(); // 把文件读入
//				OutputStream outputStream = new FileOutputStream(filePath); // 建立一个上传文件的输出流
//				int bytesRead = 0;
//				byte[] buffer = new byte[8192];
//				while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
//					outputStream.write(buffer, 0, bytesRead);
//				}
//				outputStream.close();
//				stream.close();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}	
//			request.setAttribute("imgSrc", dbPath);
//			request.setAttribute("isSaveFile", "1");
//			request.setAttribute("photo", new String(file.getFileName().getBytes("ISO-8859-1"),"UTF-8"));
//			request.setAttribute("accessory", fileName);
//			request.setAttribute("info", "附件最大为4MB");
//		}
//		else request.setAttribute("tooLargeInfo", "文件太大，无法上传！");
//		return mapping.findForward("uploadPhoto");
//	}
//	
//	/**
//	 * ajax实现删除电子照片
//	 */
//	public ActionForward delPhoto(ActionMapping mapping, ActionForm form,
//			HttpServletRequest request, HttpServletResponse response)
//			throws Exception{
//		String id = StaticMethod.null2String(request.getParameter("id"));
//		Aggregation aggregation = null;
//		String fileName = null;
//		if(!id.equals("")){
//			aggregation = this.getMainBean().find(id);
//		   fileName = aggregation.getAccessory();
//		   aggregation.setAccessory("");
//		   aggregation.setPhoto("");
//		   this.getMainBean().save(aggregation);
//		}
//		else{
//			fileName = request.getParameter("accessory") ;
//		}
//		String sysTemPaht = request.getRealPath("/"); // 取当前系统路径
//		String uploadPath = "/aggregation/upUserPhotos/";
//		String dbPath = uploadPath + fileName;
//		String filePath = sysTemPaht + dbPath;
//		File fileDel = new File(filePath);
//		if (fileDel.exists())
//			fileDel.delete();
//		String aaa = "delIsSuccess";
//		response.setContentType("text/html; charset=GBK");
//		response.getWriter().print(aaa);
//		response.getWriter().flush();
//		return null;
//	}

}
