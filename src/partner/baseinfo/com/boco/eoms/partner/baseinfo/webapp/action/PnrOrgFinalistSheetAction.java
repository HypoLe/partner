package com.boco.eoms.partner.baseinfo.webapp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.loging.BocoLog;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.dict.model.TawSystemDictType;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcServiceImpl;
import com.boco.eoms.deviceManagement.common.utils.CommonConstants;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.partner.baseinfo.mgr.IPnrOrgFinalistSheetMgr;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.baseinfo.model.PnrOrgFinalistSheet;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;


public class PnrOrgFinalistSheetAction extends BaseAction {
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("gotoAddPnrOrgFinalistSheet");
	}
	
	public ActionForward gotoPage(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)
		throws Exception {  
		String page = StaticMethod.null2String(request.getParameter("page"));
		IPnrOrgFinalistSheetMgr pofsMgr= (IPnrOrgFinalistSheetMgr)this.getBean("pnrOrgFinalistSheetMgr");
		ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");
		if("gotoAddPnrOrgFinalistSheet".equals(page)) {
			/*为修改页面设置数据*/
			PnrOrgFinalistSheet pofs = new PnrOrgFinalistSheet();
			String id = StaticMethod.null2String(request.getParameter("id"));
			if(!"".equals(id)) {
				pofs = pofsMgr.find(id);
				request.setAttribute("pnrOrgFinalistSheet", pofs);
				ArrayList specialtyList = mgr.getDictSonsByDictid("11225");
				ArrayList bigTypeArrayList = new ArrayList();
				if (pofs.getSpeciality()!= null
						&& !"".equals(pofs.getSpeciality())) {
					String[] bigType = pofs.getSpeciality().split(",");
					for (int i = 0; i < bigType.length; i++) {
						bigTypeArrayList.add(bigType[i]);
					}
					for (int i = 0; i < specialtyList.size(); i++) {
						TawSystemDictType tawSystemDictType = (TawSystemDictType) specialtyList.get(i);
						if (bigTypeArrayList.contains(tawSystemDictType.getDictId())) {
							tawSystemDictType.setDictRemark("isTrue");
						}
					}
				}
				request.setAttribute("specialtyList", specialtyList);
			}
			else{
			ArrayList specialtyList = mgr.getDictSonsByDictid("11225");
			request.setAttribute("specialtyList", specialtyList);
			}
			return mapping.findForward("gotoAddPnrOrgFinalistSheet");
		} else if("gotoPnrOrgFinalistSheetList".equals(page)) {
			return mapping.findForward("gotoPnrOrgFinalistSheetList");
		} else if("gotoPnrOrgFinalistSheetDetail".equals(page)) {
			PnrOrgFinalistSheet pofs = new PnrOrgFinalistSheet();
			String id = StaticMethod.null2String(request.getParameter("id"));
			if(!"".equals(id)) {
				pofs = pofsMgr.find(id);
				StringBuffer specialityStringBuffer=new StringBuffer();
				String[] specialityList = pofs.getSpeciality().split(",");
				for (int j = 0; j < specialityList.length; j++) {
					if (!"".equals(specialityStringBuffer.toString())) {
						specialityStringBuffer.append(",");
					}
					specialityStringBuffer.append(mgr.id2Name(specialityList[j]));
				}
				pofs.setSpeciality(specialityStringBuffer.toString());
			}
			request.setAttribute("pnrOrgFinalistSheet", pofs);
			return mapping.findForward("gotoPnrOrgFinalistSheetDetail");
		} else {
			return mapping.findForward("gotoAddPnrOrgFinalistSheet");
		}
	}
	public ActionForward addPnrOrgFinalistSheet(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {
		String id = StaticMethod.null2String(request.getParameter("id"));
		if(!"".equals(id)) {
			return this.editPnrOrgFinalistSheet(mapping, form, request, response);
		}
		IPnrOrgFinalistSheetMgr pofsMgr= (IPnrOrgFinalistSheetMgr)this.getBean("pnrOrgFinalistSheetMgr");
		PnrOrgFinalistSheet pofs = new PnrOrgFinalistSheet();
		BeanUtils.populate(pofs, request.getParameterMap());
		// 设置专业
		String[] bigType = request.getParameterValues("bigType");
		StringBuffer bigTypeString = new StringBuffer();
		for (int i = 0; i < bigType.length; i++) {
			bigTypeString.append(bigType[i]);
			bigTypeString.append(",");
		}
		pofs.setSpeciality(bigTypeString.toString());
		pofs.setAddTime(StaticMethod.getCurrentDateTime());
		pofs.setAddUser(this.getUserId(request));
		pofs.setIsdeleted("0");
		/*判断是否有效的逻辑 begin*/
		pofs.setIsEffected("0");//现在统一设置为有效
		/*判断是否有效的逻辑 end*/
		/*增加一个系统编码 begin*/
		CommonSpringJdbcServiceImpl jdbcService = (CommonSpringJdbcServiceImpl) ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");
		String sql = "SELECT COUNT(ID) as countNo from pnr_org_finalist_sheet ";
		List<ListOrderedMap> countList = jdbcService.queryForList(sql);
		Map map = new HashMap();
		map = countList.get(0);
		String organizationNo = "";
		int organization = Integer.parseInt(map.get("countNo").toString());
		String no = String.valueOf(organization + 1);
		if (no.length() == 1) {
			organizationNo = "RW-000" + no;
		} else if (no.length() == 2) {
			organizationNo = "RW-00" + no;
		} else if (no.length() == 3) {
			organizationNo = "RW-0" + no;
		}else if (no.length() == 4) {
			organizationNo = "RW-" + no;
		}
		pofs.setSysno(organizationNo);
		/*增加一个系统编码 end*/
		/*获取代维组织deptid begin*/
		String deptid=pofs.getOrgId();
		PartnerDeptMgr deptMgr=(PartnerDeptMgr)getBean("partnerDeptMgr");
		if (deptid.length()>14) {//如果是uuid，转为deptid
			PartnerDept dept=deptMgr.getPartnerDept(deptid);
			if (dept!=null) {
				pofs.setOrgDeptId(StaticMethod.null2String(dept.getDeptMagId()));
				pofs.setAreaId(StaticMethod.null2String(dept.getAreaId()));
			}
		}else {
			List<PartnerDept> list=deptMgr.getPartnerDeptsByHql("name='"+StaticMethod.null2String(pofs.getOrgName())+"'");
			if (list!=null&&list.size()>0) {
				PartnerDept dept=new PartnerDept();
				pofs.setAreaId(StaticMethod.null2String(dept.getAreaId()));
				pofs.setOrgDeptId(StaticMethod.null2String(dept.getDeptMagId()));
			}
		}
		/*获取代维组织deptid end*/
		pofsMgr.save(pofs);
		return mapping.findForward("success");
	}
	
	public ActionForward editPnrOrgFinalistSheet(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		IPnrOrgFinalistSheetMgr pofsMgr= (IPnrOrgFinalistSheetMgr)this.getBean("pnrOrgFinalistSheetMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		PnrOrgFinalistSheet pofs = new PnrOrgFinalistSheet();
		BeanUtils.populate(pofs, request.getParameterMap());
		// 设置专业
		String[] bigType = request.getParameterValues("bigType");
		StringBuffer bigTypeString = new StringBuffer();
		for (int i = 0; i < bigType.length; i++) {
			if (!"".equals(bigTypeString.toString())) {
				bigTypeString.append(",");
			}
			bigTypeString.append(bigType[i]);
		}
		pofs.setSpeciality(bigTypeString.toString());
		boolean flag = pofsMgr.save(pofs);
		return mapping.findForward("success");
	}
	
	/**
	 * 
	* @Title: pnrOrgFinalistSheetList 
	* @Description: 组织入围列表
	* @param 
	* @Time:Dec 24, 2012-4:22:23 PM
	* @Author:fengguangping
	* @return ActionForward    返回类型 
	* @throws
	 */
	public ActionForward pnrOrgFinalistSheetList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {
		IPnrOrgFinalistSheetMgr pofsMgr= (IPnrOrgFinalistSheetMgr)this.getBean("pnrOrgFinalistSheetMgr");
		ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");
		Search search = new Search();
		int firstResult = CommonUtils.getFirstResultOfDisplayTag(request, "pnrOrgFinalistSheetList");
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		
//		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(), search);
		// 设置专业
		String[] bigType = request.getParameterValues("bigType");
		String orgName =  StaticMethod.null2String(request.getParameter("orgName"));
		if(!"".equals(orgName)){
			search.addFilterILike("orgName", "%"+orgName+"%");
			request.setAttribute("orgNameSearch",orgName );
		}
		if(null!=bigType){
			for(int i=0;i<bigType.length;i++){
				search.addFilterILike("speciality", "%"+bigType[i]+"%");
			}
		}
		TawSystemSessionForm sessionForm=getUser(request);
		String deptid=sessionForm.getDeptid();
		String hasRightForAddAndDel="1";
		List<PartnerDept>  list0=new ArrayList<PartnerDept>();
		if (!"admin".equals(sessionForm.getUserid())) {
			PartnerDeptMgr partnerDeptMgr=(PartnerDeptMgr)getBean("partnerDeptMgr");
			list0=partnerDeptMgr.getPartnerDepts("and deptMagId='"+deptid+"'");
			if (list0.size()!=0&&list0!=null) {//不等于0表示是代维公司的
				search.addFilterILike("orgDeptId", deptid+"%");//代维公司权限限定
				hasRightForAddAndDel="0";
			}else {
				ITawSystemDeptManager deptManager=(ITawSystemDeptManager)getBean("ItawSystemDeptManager");
				TawSystemDept dept=deptManager.getDeptinfobydeptid(deptid,"0");
				if (dept!=null) {
					search.addFilterILike("areaId", dept.getAreaid()+"%");//区域权限限定
				}
			}
		}
		search.addFilterEqual("isdeleted", '0');
		search.addSortDesc("sysno");
		SearchResult<PnrOrgFinalistSheet> searchResult = pofsMgr.searchAndCount(search);
		List<PnrOrgFinalistSheet> pnrOrgFinalistSheetList = searchResult.getResult();
		for(int i=0;i<pnrOrgFinalistSheetList.size();i++){
			PnrOrgFinalistSheet  pofs=pnrOrgFinalistSheetList.get(i);
			StringBuffer specialityStringBuffer=new StringBuffer();
			String[] specialityList = pofs.getSpeciality().split(",");
			for (int j = 0; j < specialityList.length; j++) {
				if (!"".equals(specialityStringBuffer.toString())) {
					specialityStringBuffer.append(",");
				}
				specialityStringBuffer.append(mgr.id2Name(specialityList[j]));
			}
			pofs.setSpeciality(specialityStringBuffer.toString());
		}
		ArrayList specialtyList = mgr.getDictSonsByDictid("11225");
		request.setAttribute("specialtyList", specialtyList);
		request.setAttribute("pnrOrgFinalistSheetList",pnrOrgFinalistSheetList);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("size", searchResult.getTotalCount());
		request.setAttribute("hasRightForAddAndDel", hasRightForAddAndDel);
		return mapping.findForward("pnrOrgFinalistSheetList");
	}
	/**
	 * 
	* @Title: deletePnrOrgFinalistSheet 
	* @Description: 删除组织入围信息
	* @param 
	* @Time:Dec 24, 2012-4:06:58 PM
	* @Author:fengguangping
	* @return ActionForward    返回类型 
	* @throws
	 */
	public ActionForward deletePnrOrgFinalistSheet(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		response.setCharacterEncoding("utf-8");
		try {
			IPnrOrgFinalistSheetMgr pofsMgr= (IPnrOrgFinalistSheetMgr)this.getBean("pnrOrgFinalistSheetMgr");
			String id = request.getParameter("id");
			PnrOrgFinalistSheet pofs = pofsMgr.find(id);
			pofs.setIsdeleted("1");
			pofsMgr.save(pofs);
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
	
}
