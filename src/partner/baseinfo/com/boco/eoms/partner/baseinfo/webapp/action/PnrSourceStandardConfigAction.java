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
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.common.utils.CommonConstants;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.partner.baseinfo.mgr.IPnrSourceStandardConfigService;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.baseinfo.model.PnrSourceStandardConfig;
import com.boco.eoms.partner.baseinfo.util.PnrResConfigAmountTaskScheduler;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;


/**
 * 类说明：代维资源配置模块 Action类
 * 创建人： fengguangping
 * 创建时间：2012-12-27 16:18:56
 */
public class PnrSourceStandardConfigAction extends BaseAction {
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("gotoPnrSourceStandardConfigAddPage");
	}
	
	/**
	 * 
	 * 方法说明：跳转到PnrSourceStandardConfig添加页面
	 * 作   者: fengguangping
	 * 创建时间: Aug 27, 2012-3:55:35 PM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward gotoAddPnrSourceStandardConfigPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {  
		//设置自己的初始化数据
		return mapping.findForward("gotoAddPnrSourceStandardConfigPage");
	}
	
	/**
	 * 
	 * 方法说明：跳转到PnrSourceStandardConfig修改页面
	 * 作   者: fengguangping
	 * 创建时间: Aug 27, 2012-3:55:35 PM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward gotoEditPnrSourceStandardConfigPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {  
		String id = StaticMethod.null2String(request.getParameter("id"));
		IPnrSourceStandardConfigService pnrSourceStandardConfigService = (IPnrSourceStandardConfigService)this.getBean("pnrSourceStandardConfigService");
		PnrSourceStandardConfig pnrSourceStandardConfig = pnrSourceStandardConfigService.find(id);
		request.setAttribute("pnrSourceStandardConfig",pnrSourceStandardConfig);
		return mapping.findForward("gotoEditPnrSourceStandardConfigPage");
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
	public ActionForward savePnrSourceStandardConfig(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String id = StaticMethod.null2String(request.getParameter("id"));
		if(!"".equals(id)) {
			return this.editPnrSourceStandardConfig(mapping, form, request, response);
		}
		IPnrSourceStandardConfigService pnrSourceStandardConfigService = (IPnrSourceStandardConfigService)this.getBean("pnrSourceStandardConfigService");
		PnrSourceStandardConfig pnrSourceStandardConfig = new PnrSourceStandardConfig();
		BeanUtils.populate(pnrSourceStandardConfig, request.getParameterMap());
		pnrSourceStandardConfig.setSaveTime(CommonUtils.toEomsStandardDate(new Date()));
		pnrSourceStandardConfig.setIsdeleted("0");
		String areaId=pnrSourceStandardConfig.getAreaId();
		String time=pnrSourceStandardConfig.getConfigTime();
		if (areaId.length()==2) {
			pnrSourceStandardConfig.setProvinceId(areaId);
			pnrSourceStandardConfig.setCityId(areaId);
			pnrSourceStandardConfig.setCountryId(areaId);
		}else if (areaId.length()==4) {
			pnrSourceStandardConfig.setProvinceId(areaId.substring(0, 2));
			pnrSourceStandardConfig.setCityId(areaId);
			pnrSourceStandardConfig.setCountryId(areaId);
		}else if (areaId.length()==6) {
			pnrSourceStandardConfig.setProvinceId(areaId.substring(0, 2));
			pnrSourceStandardConfig.setCityId(areaId.substring(0, 4));
			pnrSourceStandardConfig.setCountryId(areaId);
		}
		int s=pnrSourceStandardConfig.getStandardConfig();
		int actual=pnrSourceStandardConfig.getActualConfig();
		pnrSourceStandardConfig.setConfigRate((actual*1.0)/s*100);
		pnrSourceStandardConfig.setAddTimeY(Integer.parseInt(time.substring(0,4)));
		pnrSourceStandardConfig.setAddTimeM(Integer.parseInt(time.substring(5,7)));
		pnrSourceStandardConfig.setAddTimeD(Integer.parseInt(time.substring(8,10)));
		pnrSourceStandardConfig.setAddUser(this.getUserId(request));
		PartnerDeptMgr partnerDeptMgr=(PartnerDeptMgr)ApplicationContextHolder.getInstance().getBean("partnerDeptMgr");
		PartnerDept partnerDept=partnerDeptMgr.getPartnerDept(pnrSourceStandardConfig.getCompanyId());
		pnrSourceStandardConfig.setCompanyMagId(partnerDept.getDeptMagId());
		pnrSourceStandardConfigService.save(pnrSourceStandardConfig);
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
	public ActionForward editPnrSourceStandardConfig(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		IPnrSourceStandardConfigService pnrSourceStandardConfigService = (IPnrSourceStandardConfigService)this.getBean("pnrSourceStandardConfigService");
		String id = StaticMethod.null2String(request.getParameter("id"));
		PnrSourceStandardConfig pnrSourceStandardConfig = new PnrSourceStandardConfig();
		pnrSourceStandardConfig = pnrSourceStandardConfigService.find(id);
		BeanUtils.populate(pnrSourceStandardConfig, request.getParameterMap());
		String areaId=pnrSourceStandardConfig.getAreaId();
		String time=pnrSourceStandardConfig.getConfigTime();
		if (areaId.length()==2) {
			pnrSourceStandardConfig.setProvinceId(areaId);
			pnrSourceStandardConfig.setCityId(areaId);
			pnrSourceStandardConfig.setCountryId(areaId);
		}else if (areaId.length()==4) {
			pnrSourceStandardConfig.setProvinceId(areaId.substring(0, 2));
			pnrSourceStandardConfig.setCityId(areaId);
			pnrSourceStandardConfig.setCountryId(areaId);
		}else if (areaId.length()==6) {
			pnrSourceStandardConfig.setProvinceId(areaId.substring(0, 2));
			pnrSourceStandardConfig.setCityId(areaId.substring(0, 4));
			pnrSourceStandardConfig.setCountryId(areaId);
		}
		int s=pnrSourceStandardConfig.getStandardConfig();
		int actual=pnrSourceStandardConfig.getActualConfig();
		pnrSourceStandardConfig.setConfigRate((actual*1.0)/s*100);
		pnrSourceStandardConfig.setAddTimeY(Integer.parseInt(time.substring(0,4)));
		pnrSourceStandardConfig.setAddTimeM(Integer.parseInt(time.substring(5,7)));
		pnrSourceStandardConfig.setAddTimeD(Integer.parseInt(time.substring(8,10)));
		pnrSourceStandardConfig.setAddUser(this.getUserId(request));
		PartnerDeptMgr partnerDeptMgr=(PartnerDeptMgr)ApplicationContextHolder.getInstance().getBean("partnerDeptMgr");
		String companyId=pnrSourceStandardConfig.getCompanyId();
		PartnerDept partnerDept=partnerDeptMgr.getPartnerDept(companyId);
		pnrSourceStandardConfig.setCompanyMagId(partnerDept.getDeptMagId());
		boolean flag = pnrSourceStandardConfigService.save(pnrSourceStandardConfig);
		return mapping.findForward("success");
	}
	
	/**
	 * 
	 * 方法说明：跳转到PnrSourceStandardConfig详情页面
	 * 作   者: fengguangping
	 * 创建时间: Aug 27, 2012-3:55:35 PM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward gotoPnrSourceStandardConfigDetailPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		IPnrSourceStandardConfigService pnrSourceStandardConfigService = (IPnrSourceStandardConfigService)this.getBean("pnrSourceStandardConfigService");
		PnrSourceStandardConfig pnrSourceStandardConfig = new PnrSourceStandardConfig();
		String id = StaticMethod.null2String(request.getParameter("id"));
		if(!"".equals(id)) {
			pnrSourceStandardConfig = pnrSourceStandardConfigService.find(id);
		}
		request.setAttribute("pnrSourceStandardConfig", pnrSourceStandardConfig);
		return mapping.findForward("gotoPnrSourceStandardConfigDetailPage");
	}
	/**
	 * 
	 * 方法说明：跳转到PnrSourceStandardConfig列表页面
	 * 作   者: fengguangping
	 * 创建时间: Aug 27, 2012-3:55:35 PM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward gotoPnrSourceStandardConfigListPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
//		PnrResConfigAmountTaskScheduler t=new PnrResConfigAmountTaskScheduler();
//		t.execute(null);
		IPnrSourceStandardConfigService pnrSourceStandardConfigService = (IPnrSourceStandardConfigService)this.getBean("pnrSourceStandardConfigService");
		Search search = new Search();
		String areaName=StaticMethod.null2String(request.getParameter("areaName"));
		String hasRightForEditeAndDel="1";
		search.addFilterEqual("isdeleted", "0");
		//获取区域id作为删选条件
		TawSystemSessionForm sessionForm=this.getUser(request);
		String deptid=sessionForm.getDeptid();
		List<PartnerDept>  list0=new ArrayList<PartnerDept>();
		if (!"admin".equals(sessionForm.getUserid())) {
			PartnerDeptMgr partnerDeptMgr=(PartnerDeptMgr)getBean("partnerDeptMgr");
			list0=partnerDeptMgr.getPartnerDepts("and deptMagId='"+deptid+"'");
			if (list0.size()!=0&&list0!=null) {//不等于0表示是代维公司的
				search.addFilterILike("companyMagId", deptid+"%");//代维公司权限限定
				hasRightForEditeAndDel="0";//代维公司人员没有编辑和修改的权利
			}else {
				ITawSystemDeptManager deptManager=(ITawSystemDeptManager)getBean("ItawSystemDeptManager");
				TawSystemDept dept=deptManager.getDeptinfobydeptid(deptid,"0");
				if (dept!=null) {
					search.addFilterILike("areaId", dept.getAreaid()+"%");//区域权限限定
				}
			}
		}
		int firstResult = CommonUtils.getFirstResultOfDisplayTag(request, "pnrSourceStandardConfigList");
		
		search = CommonUtils.getSqlFromRequestMap(request, search);
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		//search.addSortDesc("addTime");
		SearchResult<PnrSourceStandardConfig> searchResult = pnrSourceStandardConfigService.searchAndCount(search);
		List<PnrSourceStandardConfig> pnrSourceStandardConfigList = searchResult.getResult();
		request.setAttribute("pnrSourceStandardConfigList",pnrSourceStandardConfigList);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("size", searchResult.getTotalCount());
		request.setAttribute("areaName", areaName);
		request.setAttribute("hasRightForEditeAndDel", hasRightForEditeAndDel);
		return mapping.findForward("gotoPnrSourceStandardConfigListPage");
	}
	
	/**
	 * 
	 * 方法说明：删除PnrSourceStandardConfig
	 * 作   者: fengguangping
	 * 创建时间: Aug 27, 2012-3:55:35 PM
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deletePnrSourceStandardConfig(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		response.setCharacterEncoding("utf-8");
		try {
			IPnrSourceStandardConfigService pnrSourceStandardConfigService = (IPnrSourceStandardConfigService)this.getBean("pnrSourceStandardConfigService");
			String id = request.getParameter("id");
			PnrSourceStandardConfig pnrSourceStandardConfig = new PnrSourceStandardConfig();
			pnrSourceStandardConfig = pnrSourceStandardConfigService.find(id);
			pnrSourceStandardConfig.setIsdeleted("1");
			pnrSourceStandardConfig.setAddUser(this.getUserId(request));
			pnrSourceStandardConfigService.save(pnrSourceStandardConfig);
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
