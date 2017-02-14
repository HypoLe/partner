package com.boco.eoms.partner.record.webapp.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.commons.system.user.service.ITawSystemUserManager;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcService;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.baseinfo.util.PartnerCityByUser;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;
import com.boco.eoms.partner.property.util.StatisticsResultExport;
import com.boco.eoms.partner.record.mgr.IPnrRecordMgr;
import com.boco.eoms.partner.record.model.PnrRecord;
import com.boco.eoms.partner.serviceArea.mgr.ILineMgr;
import com.boco.eoms.partner.serviceArea.util.SiteConstants;
import com.boco.eoms.partner.statistically.pojo.TdObjModel;
import com.boco.eoms.partner.statistically.utils.TableHelper;

public class PnrRecordAction extends BaseAction {
	/**
	 * 新增质量管理报告
	 * 
	 */
	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		//根据省ID获取地市
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
    	request.setAttribute("city", PartnerCityByUser.getCityByProvince(province));
		
		
		String userId = this.getUser(request).getUserid();

		ITawSystemUserManager userManager = (ITawSystemUserManager) getBean("ItawSystemUserSaveManagerFlush");

		TawSystemUser user = userManager.getUserByuserid(userId);
		
		
		String deptid = user.getDeptid();
		String telphone = user.getMobile();
		request.setAttribute("userId",userId);
		request.setAttribute("deptid",deptid);
		request.setAttribute("telphone",telphone);
		
		return mapping.findForward("add");
	}

	/**
	 * 保存质量管理报告
	 * 
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");

		String userId = this.getUser(request).getUserid();

		ITawSystemUserManager userManager = (ITawSystemUserManager) getBean("ItawSystemUserSaveManagerFlush");

		TawSystemUser user = userManager.getUserByuserid(userId);
		String deptid = user.getDeptid();
		String telphone = user.getMobile();
		request.setAttribute("userId",userId);
		request.setAttribute("deptid",deptid);
		request.setAttribute("telphone",telphone);
		String title = request.getParameter("title");
		String city = request.getParameter("city");
		String country = request.getParameter("country");
		String site = request.getParameter("site");
		String summary = request.getParameter("summary");
		String keyword = request.getParameter("keyword");
		String specialty = request.getParameter("specialty");
		String attachment = request.getParameter("attachment");
		String recordType = request.getParameter("recordType");
//		String state = StaticMethod.null2String(request.getParameter("state"));
//		boolean isDraft = false;
//		if(state.equals("")){
//			state = "1";
//			isDraft = true;
//		}
		IPnrRecordMgr pnrRecordMgr = (IPnrRecordMgr) getBean("pnrRecordMgr");
		PnrRecord pnrRecord = new PnrRecord();
		pnrRecord.setTitle(title);
		pnrRecord.setCity(city);
		pnrRecord.setCountry(country);
		pnrRecord.setSite(site);
		pnrRecord.setSummary(summary);
		pnrRecord.setKeyword(keyword);
		pnrRecord.setAttachment(attachment);
		pnrRecord.setSpecialty(specialty);
		pnrRecord.setAddTime(CommonUtils.toEomsStandardDate(new Date(System
				.currentTimeMillis())));
		pnrRecord.setAddUser(userId);
		pnrRecord.setState("0");
		pnrRecord.setDownloadNum("0");
		pnrRecord.setRecordType(recordType);
		pnrRecordMgr.savePnrRecord(pnrRecord);
		List list = pnrRecordMgr.getPnrRecords(" where title = '" + title + "'" );
		if(list.size()>0){
			pnrRecord = (PnrRecord) list.get(0);
		}
			return mapping.findForward("success");
	}

	/**
	 * 审核质量管理报告
	 * 
	 */
	
	public ActionForward audit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");

		String userId = this.getUser(request).getUserid();

		ITawSystemUserManager userManager = (ITawSystemUserManager) getBean("ItawSystemUserSaveManagerFlush");

		TawSystemUser user = userManager.getUserByuserid(userId);
		String deptid = user.getDeptid();
		String telphone = user.getMobile();
		request.setAttribute("userId",userId);
		request.setAttribute("deptid",deptid);
		request.setAttribute("telphone",telphone);

		String mainId = request.getParameter("mainId");
		IPnrRecordMgr pnrRecordMgr = (IPnrRecordMgr) getBean("pnrRecordMgr");
		PnrRecord pnrRecord = pnrRecordMgr.getPnrRecord(mainId);
		pnrRecord.setState("4");
		pnrRecordMgr.savePnrRecord(pnrRecord);		

		
		return mapping.findForward("success");
	}	
	/**
	 * 查询质量管理报告列表
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {	
		//根据省ID获取地市
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
    	request.setAttribute("city", PartnerCityByUser.getCityByProvince(province));
		
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				SiteConstants.SITE_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		String userId = this.getUser(request).getUserid();
		String state = request.getParameter("state");
		String title = request.getParameter("title");
		String city = request.getParameter("city");
		String country = request.getParameter("country");
		String site = request.getParameter("site");		
		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		
		String whereStr = " where 1 = 1 ";
        
		if(title!=null&&!title.equals("")){
			whereStr += " and title = '" + title + "'";
		}
		if(city!=null&&!city.equals("")){
			whereStr += " and city = '" + city + "'";
		}
		if(country!=null&&!country.equals("")){
			whereStr += " and country = '" + country + "'";
		}
		if(site!=null&&!site.equals("")){
			whereStr += " and site = '" + site + "'";
		}		
		if(beginTime!=null&&!beginTime.equals("")){
			whereStr += " and addTime >= '" + beginTime + "'";
		}
		if(endTime!=null&&!endTime.equals("")){
			whereStr += " and addTime <= '" + endTime + "'";
		}
		
		boolean isEdit = false;
		if(state!=null&&state.equals("2")){
			isEdit = true;
		}
		IPnrRecordMgr pnrRecordMgr = (IPnrRecordMgr) getBean("pnrRecordMgr");
		Map map = pnrRecordMgr.getPnrRecords(pageIndex, pageSize, whereStr);
		List list = (List) map.get("result");
		request.setAttribute("pnrRecordList", list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		
		if(isEdit){
			return mapping.findForward("editList");
		}
		return mapping.findForward("list");
	}
	/**
	 * 编辑质量管理报告列表
	 */
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {	
		//根据省ID获取地市
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
    	request.setAttribute("city", PartnerCityByUser.getCityByProvince(province));
    	
		String id = request.getParameter("id");

		IPnrRecordMgr pnrRecordMgr = (IPnrRecordMgr) getBean("pnrRecordMgr");
        PnrRecord pnrRecord = pnrRecordMgr.getPnrRecord(id);
        request.setAttribute("pnrRecord",pnrRecord);
		return mapping.findForward("edit");
	}	

	/**
	 * 查看质量管理报告列表
	 */
	public ActionForward detail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");

		IPnrRecordMgr pnrRecordMgr = (IPnrRecordMgr) getBean("pnrRecordMgr");
        PnrRecord pnrRecord = pnrRecordMgr.getPnrRecord(id);
        request.setAttribute("pnrRecord",pnrRecord);
		return mapping.findForward("detail");
	}		
	/**
	 * 提交质量管理报告 
	 */
	public ActionForward submit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String userId = this.getUser(request).getUserid();

		ITawSystemUserManager userManager = (ITawSystemUserManager) getBean("ItawSystemUserSaveManagerFlush");

		TawSystemUser user = userManager.getUserByuserid(userId);
		String deptid = user.getDeptid();
		String telphone = user.getMobile();
		request.setAttribute("userId",userId);
		request.setAttribute("deptid",deptid);
		request.setAttribute("telphone",telphone);

		String title = request.getParameter("title");
		String type = request.getParameter("type");
		String cycle = request.getParameter("cycle");
		String keyWord = request.getParameter("keyWord");
		String report = request.getParameter("report");
		String summary = request.getParameter("summary");
		String auditUser = request.getParameter("auditUser");
		IPnrRecordMgr pnrRecordMgr = (IPnrRecordMgr) getBean("pnrRecordMgr");
		PnrRecord pnrRecord = new PnrRecord();
		pnrRecord.setState("2");
		pnrRecordMgr.savePnrRecord(pnrRecord);		


		List list = pnrRecordMgr.getPnrRecords(" where title = " + title + " desc ");
		if(list.size()>0){
			pnrRecord = (PnrRecord) list.get(0);
		}		
		return mapping.findForward("success");
	}

	/**
	 * 删除质量管理报告 
	 */
	public ActionForward remove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");

		IPnrRecordMgr pnrRecordMgr = (IPnrRecordMgr) getBean("pnrRecordMgr");
		pnrRecordMgr.removePnrRecord(id);
		return mapping.findForward("success");
	}	
	public ActionForward download(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String id = request.getParameter("id");

		IPnrRecordMgr pnrRecordMgr = (IPnrRecordMgr) getBean("pnrRecordMgr");
        PnrRecord pnrRecord = pnrRecordMgr.getPnrRecord(id);
        String fileName = pnrRecord.getAttachment().replace("'","");
        String path = "/partner/uploadfile/partner/uploadfile/partner/uploadfile/pnrcontact";
        String pathName = path + File.separator + fileName;
		InputStream inStream = new FileInputStream(new File(pathName));
		// 设置输出的格式
		response.reset();
		response.setContentType("application/x-msdownload;charset=GBK");
		response.setCharacterEncoding("GB2312");
		response.setHeader("Content-Disposition", "attachment; filename="+ new String((fileName).getBytes("gbk"), "iso8859-1"));
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
		Integer downloadNum = StaticMethod.getIntValue(pnrRecord.getDownloadNum())+1;
		pnrRecord.setDownloadNum(downloadNum.toString());
		pnrRecordMgr.savePnrRecord(pnrRecord);
		return null;        
        
	}
	/**
	 * 查询质量管理报告列表
	 */
	public ActionForward statistic(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {	

		return mapping.findForward("statistic");
	}
	/**
	 * 查询质量管理报告列表
	 */
	public ActionForward statisticSubmit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {	
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
		String joinStr = "";
		for (int i = 0; i < rows.length; i++) {
				searchStr += statisticsItems[i];
			group += statisticsItems[i];
			if (i != rows.length - 1) {
				searchStr += ",";
				group += ",";
			}
		}
		

		String beginTime = request.getParameter("beginTime");
		String endTime = request.getParameter("endTime");
		String whereStr = " where 1=1 ";
		if(beginTime!=null&&!beginTime.equals("")){
			whereStr += " and add_Time >= '" + beginTime + "'";
		}
		if(endTime!=null&&!endTime.equals("")){
			whereStr += " and add_Time <= '" + endTime + "'";
		}		
		CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");
		String searchSql = "";
		if (searchStr.equals("city,country")){
			searchSql = 
				"select aa.*,\n" +
				"       round(nvl(bbcount,0)*100/aa.aacount,2) updatenum ,\n" + 
				"       nvl(ccSum,0) donwloadnum,\n" + 
				"       nvl(ddCount,0) perfectnum from\n" + 
				" (select city,country,count(*)aaCount from pnr_res_config\n" + 
				" group by city,country ) aa\n" + 
				"left join (select city,country,count(*) bbCount from pnr_record " + whereStr +
				" group by city,country ) bb\n" + 
				"    on aa.city = bb.city\n" + 
				"   and aa.country = bb.country\n" + 
				"left join (select city,country,sum(download_num)ccSum from pnr_record group by city,country) cc\n" + 
				"    on aa.city = cc.city\n" + 
				"   and aa.country = cc.country\n" + 
				"left join (select city,country,count(*) ddCount from pnr_record group by city,country ) dd\n" + 
				"    on aa.city = dd.city\n" + 
				"   and aa.country = dd.country\n" + 
				" order by city,country";
			request.setAttribute("cityFlag",1);
			request.setAttribute("countryFlag",1);
		}else if(searchStr.equals("country")){
			searchSql = 
				"select aa.*,\n" +
				"       round(nvl(bbcount,0)*100/aa.aacount,2) updatenum ,\n" + 
				"       nvl(ccSum,0) donwloadnum,\n" + 
				"       nvl(ddCount,0) perfectnum from\n" + 
				" (select country,count(*)aaCount from pnr_res_config\n" + 
				" group by country ) aa\n" + 
				"left join (select country,count(*) bbCount from pnr_record " + whereStr +
				" group by country ) bb\n" + 
				"    on  aa.country = bb.country\n" + 
				"left join (select country,sum(download_num)ccSum from pnr_record group by country) cc\n" + 
				"    on aa.country = cc.country\n" + 
				"left join (select country,count(*) ddCount from pnr_record group by country ) dd\n" + 
				"    on aa.country = dd.country\n" + 
				" order by country";
			request.setAttribute("countryFlag",1);
		}else if(searchStr.equals("city")){
			searchSql = 
				"select aa.*,\n" +
				"       round(nvl(bbcount,0)*100/aa.aacount,2) updatenum ,\n" + 
				"       nvl(ccSum,0) donwloadnum,\n" + 
				"       nvl(ddCount,0) perfectnum from\n" + 
				" (select city,count(*)aaCount from pnr_res_config\n" + 
				" group by city ) aa\n" + 
				"left join (select city,count(*) bbCount from pnr_record " + whereStr +
				" group by city ) bb\n" + 
				"    on aa.city = bb.city\n" + 
				"left join (select city,sum(download_num)ccSum from pnr_record group by city) cc\n" + 
				"    on aa.city = cc.city\n" + 
				"left join (select city,count(*) ddCount from pnr_record group by city ) dd\n" + 
				"    on aa.city = dd.city\n" + 
				" order by city";		
			request.setAttribute("cityFlag",1);	
		}


		List<ListOrderedMap> resultList = jdbcService.queryForList(searchSql);
		request.setAttribute("resultList", resultList);
		request.setAttribute("resultSize", resultList.size());
		request.setAttribute("pageSize", resultList.size());
		return mapping.findForward("statisticsList");
		
	}	
	/**
	 * 页面二级联动，已知地市，县区 联动出合作伙伴
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changeSite(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JSONArray json = new JSONArray();
		JSONObject jitem = new JSONObject();
		String city = StaticMethod.null2String(request.getParameter("city"));
		String country = StaticMethod.null2String(request.getParameter("country"));

		StringBuffer where = new StringBuffer();
		where.append(" where 1=1 "); 
		if(!"".equals(city)){
			where.append(" and city = '");
			where.append(city);
			where.append("'");
		}
		if(!"".equals(country)){
			where.append(" and country = '");
			where.append(country);
			where.append("'");
		}

		
		// 列出选择当前地市的合作伙伴
		StringBuffer provider_list = new StringBuffer();
		provider_list.append("," + "");
		provider_list.append("," + "--请选择站点--");


		CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");
        String sql = " select res_name,id from pnr_res_config " + where ;
		List siteList = jdbcService.queryForList(sql);
	
		Map map = new HashMap();
		for (int i = 0; i < siteList.size();i++){
			map = (Map) siteList.get(i);
			provider_list.append("," + map.get("id"));
			provider_list.append("," + map.get("res_name"));			
		}
		String siteBuffer = provider_list.toString();
		
		siteBuffer = siteBuffer.substring(1, siteBuffer.length());
		

		jitem.put("sb", siteBuffer);
		json.put(jitem);
		
		response.setCharacterEncoding("utf-8");		
		response.getWriter().write(json.toString());

		return null;
	}	
	
}
