package com.boco.eoms.partner.baseinfo.webapp.action;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.loging.BocoLog;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.common.pojo.EomsSearch;
import com.boco.eoms.deviceManagement.common.utils.CommonConstants;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.baseinfo.model.PnrStandardConfig;
import com.boco.eoms.partner.netresource.service.IEomsService;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.googlecode.genericdao.search.SearchResult;


/**
 * 类说明：代维资源配置模块 Action类
 * 创建人： fengguangping
 * 创建时间：2012-12-27 16:18:56
 */
public class PnrStandardConfigAction extends BaseAction {
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("gotoPnrStandardConfigAddPage");
	}
	
	/**
	 * 
	 * 方法说明：跳转到PnrStandardConfig添加页面
	 * 作   者: fengguangping
	 * 创建时间: Aug 27, 2012-3:55:35 PM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward gotoAddPnrStandardConfigPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {  
		return mapping.findForward("gotoAddPnrStandardConfigPage");
	}
	
	/**
	 * 
	 * 方法说明：跳转到PnrStandardConfig修改页面
	 * 作   者: fengguangping
	 * 创建时间: Aug 27, 2012-3:55:35 PM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward gotoEditPnrStandardConfigPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {  
		String id = StaticMethod.null2String(request.getParameter("id"));
		IEomsService  service=(IEomsService)getBean("eomsService");
		service.setPersistentClass(Class.forName("com.boco.eoms.partner.baseinfo.model.PnrStandardConfig"));
		PnrStandardConfig pnrStandardConfig =(PnrStandardConfig)service.find(id);
		request.setAttribute("pnrStandardConfig",pnrStandardConfig);
		return mapping.findForward("gotoEditPnrStandardConfigPage");
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
	public ActionForward savePnrStandardConfig(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		try {
			String id = StaticMethod.null2String(request.getParameter("id"));
			response.setCharacterEncoding("utf-8");
			if(!"".equals(id)) {
				return this.editPnrStandardConfig(mapping, form, request, response);
			}
			IEomsService  service=(IEomsService)getBean("eomsService");
			PnrStandardConfig pnrStandardConfig = new PnrStandardConfig();
			BeanUtils.populate(pnrStandardConfig, request.getParameterMap());
			pnrStandardConfig.setSaveTime(CommonUtils.toEomsStandardDate(new Date()));
			pnrStandardConfig.setIsdeleted("0");
			String areaId=StaticMethod.null2String(pnrStandardConfig.getAreaId());
			if (areaId.length()==2) {
				pnrStandardConfig.setProvinceId(areaId);
				pnrStandardConfig.setCityId(areaId);
				pnrStandardConfig.setCountryId(areaId);
			}else if (areaId.length()==4) {
				pnrStandardConfig.setProvinceId(areaId.substring(0, 2));
				pnrStandardConfig.setCityId(areaId);
				pnrStandardConfig.setCountryId(areaId);
			}else if (areaId.length()==6) {
				pnrStandardConfig.setProvinceId(areaId.substring(0, 2));
				pnrStandardConfig.setCityId(areaId.substring(0, 4));
				pnrStandardConfig.setCountryId(areaId);
			}
			int s=pnrStandardConfig.getStandardConfig();
			pnrStandardConfig.setAddUser(this.getUserId(request));
			PartnerDeptMgr partnerDeptMgr=(PartnerDeptMgr)ApplicationContextHolder.getInstance().getBean("partnerDeptMgr");
			service.save(pnrStandardConfig);
		} catch (org.springframework.dao.DataIntegrityViolationException e) {
			request.setAttribute("msg", "该专业已经配置了标准无法再配置");
			return mapping.findForward("fail");
		} catch (Exception e) {
			return mapping.findForward("fail");
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
	public ActionForward editPnrStandardConfig(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		IEomsService  service=(IEomsService)getBean("eomsService");
		service.setPersistentClass(Class.forName("com.boco.eoms.partner.baseinfo.model.PnrStandardConfig"));
		String id = StaticMethod.null2String(request.getParameter("id"));
		PnrStandardConfig pnrStandardConfig = new PnrStandardConfig();
		pnrStandardConfig = (PnrStandardConfig)service.find(id);
		BeanUtils.populate(pnrStandardConfig, request.getParameterMap());
		String areaId=StaticMethod.null2String(pnrStandardConfig.getAreaId());
		if (areaId.length()==2) {
			pnrStandardConfig.setProvinceId(areaId);
			pnrStandardConfig.setCityId(areaId);
			pnrStandardConfig.setCountryId(areaId);
		}else if (areaId.length()==4) {
			pnrStandardConfig.setProvinceId(areaId.substring(0, 2));
			pnrStandardConfig.setCityId(areaId);
			pnrStandardConfig.setCountryId(areaId);
		}else if (areaId.length()==6) {
			pnrStandardConfig.setProvinceId(areaId.substring(0, 2));
			pnrStandardConfig.setCityId(areaId.substring(0, 4));
			pnrStandardConfig.setCountryId(areaId);
		}
		int s=pnrStandardConfig.getStandardConfig();
		pnrStandardConfig.setAddUser(this.getUserId(request));
		boolean flag = service.save(pnrStandardConfig);
		return mapping.findForward("success");
	}
	
	/**
	 * 
	 * 方法说明：跳转到PnrStandardConfig详情页面
	 * 作   者: fengguangping
	 * 创建时间: Aug 27, 2012-3:55:35 PM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward gotoPnrStandardConfigDetailPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		IEomsService  service=(IEomsService)getBean("eomsService");
		service.setPersistentClass(Class.forName("com.boco.eoms.partner.baseinfo.model.PnrStandardConfig"));
		PnrStandardConfig pnrStandardConfig = new PnrStandardConfig();
		String id = StaticMethod.null2String(request.getParameter("id"));
		if(!"".equals(id)) {
			pnrStandardConfig = (PnrStandardConfig) service.find(id);
		}
		request.setAttribute("pnrStandardConfig", pnrStandardConfig);
		return mapping.findForward("gotoPnrStandardConfigDetailPage");
	}
	/**
	 * 
	 * 方法说明：跳转到PnrStandardConfig列表页面
	 * 作   者: fengguangping
	 * 创建时间: Aug 27, 2012-3:55:35 PM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward gotoPnrStandardConfigListPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		IEomsService  service=(IEomsService)getBean("eomsService");
		EomsSearch eomsSearch = new EomsSearch();
		eomsSearch.setSearchClass(Class.forName("com.boco.eoms.partner.baseinfo.model.PnrStandardConfig"));
		//service.setPersistentClass(Class.forName("PnrStandardConfig"));
		String hasRightForEditeAndDel="1";
		eomsSearch.addFilterEqual("isdeleted", "0");
		//获取区域id作为删选条件
		TawSystemSessionForm sessionForm=this.getUser(request);
		String deptid=sessionForm.getDeptid();
		List<PartnerDept>  list0=new ArrayList<PartnerDept>();
		if (!"admin".equals(sessionForm.getUserid())) {
			PartnerDeptMgr partnerDeptMgr=(PartnerDeptMgr)getBean("partnerDeptMgr");
			list0=partnerDeptMgr.getPartnerDepts("and deptMagId='"+deptid+"'");
			if (list0.size()!=0&&list0!=null) {//不等于0表示是代维公司的
//				eomsSearch.addFilterILike("companyMagId", deptid+"%");//代维公司权限限定
				hasRightForEditeAndDel="0";//代维公司人员没有编辑和修改的权利
			}
//			else {
//				ITawSystemDeptManager deptManager=(ITawSystemDeptManager)getBean("ItawSystemDeptManager");
//				TawSystemDept dept=deptManager.getDeptinfobydeptid(deptid,"0");
//				if (dept!=null) {
//					eomsSearch.addFilterILike("areaId", dept.getAreaid()+"%");//区域权限限定
//				}
//			}
		}
		int firstResult = CommonUtils.getFirstResultOfDisplayTag(request, "pnrStandardConfigList");
		eomsSearch = CommonUtils.getSqlFromRequestMap(request, eomsSearch);
		eomsSearch.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		eomsSearch.setMaxResults(CommonConstants.PAGE_SIZE);
		//search.addSortDesc("addTime");
		SearchResult<PnrStandardConfig> searchResult = service.searchAndCount(eomsSearch);
		List<PnrStandardConfig> pnrStandardConfigList = searchResult.getResult();
		request.setAttribute("pnrStandardConfigList",pnrStandardConfigList);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("size", searchResult.getTotalCount());
		request.setAttribute("hasRightForEditeAndDel", hasRightForEditeAndDel);
		return mapping.findForward("gotoPnrStandardConfigListPage");
	}
	
	/**
	 * 
	 * 方法说明：删除PnrStandardConfig
	 * 作   者: fengguangping
	 * 创建时间: Aug 27, 2012-3:55:35 PM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deletePnrStandardConfig(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		response.setCharacterEncoding("utf-8");
		try {
			IEomsService  service=(IEomsService)getBean("eomsService");
			service.setPersistentClass(Class.forName("com.boco.eoms.partner.baseinfo.model.PnrStandardConfig"));
			String id = request.getParameter("id");
			service.removeById(id);
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
