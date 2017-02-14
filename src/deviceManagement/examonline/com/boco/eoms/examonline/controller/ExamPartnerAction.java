package com.boco.eoms.examonline.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.acegisecurity.providers.encoding.Md5PasswordEncoder;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.model.PageModel;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.user.model.TawSystemUser;
import com.boco.eoms.commons.system.user.service.ITawSystemUserManager;
import com.boco.eoms.examonline.model.ExamConfig;
import com.boco.eoms.examonline.model.ExamResult;
import com.boco.eoms.examonline.service.ExamPartnerService;
import com.boco.eoms.examonline.service.ExamResultService;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.baseinfo.model.PartnerUser;

/** 
 * Description: 代维考试相关action 
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Feb 18, 2012 4:23:56 PM 
 */
public class ExamPartnerAction extends BaseAction {
	
	/**
	 * 查询代维考试
	 */
	public ActionForward findExamConfigList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		String assign = request.getParameter("assign");
		request.setAttribute("assign", assign);
		ExamPartnerService partnerService = (ExamPartnerService)ApplicationContextHolder.getInstance().getBean("examPartnerService");
		PartnerUserMgr partnerUserMgr = (PartnerUserMgr) getBean("partnerUserMgr");
		PageModel pm = null;
	    List<ExamConfig> l = null;//分页相关
    	String pageIndexStr = request.getParameter("pager.offset")==null ? "0" : request.getParameter("pager.offset");
        int pageIndex  = Integer.parseInt(pageIndexStr);
    	//定义每页显示数
    	int pageSize = 15;
    	ExamConfig config = new ExamConfig();
	    pm = partnerService.findExamConfigList(config, 2, pageIndex, pageSize);
	    l = pm.getDatas();
	    
	    //将身份证（考号）转为代维人员姓名
	    for (ExamConfig examConfig : l) {
	    	String personCardStr = examConfig.getTester();
			String[] personCardArr = personCardStr.split(";");
			String partnerUserStr = "";
			for(int j=0;j<personCardArr.length;j++){
				String personCardNo = personCardArr[j];
				PartnerUser pu = partnerUserMgr.getPartnerUserByPersonCardNo(personCardNo);
				if(pu != null){
					partnerUserStr += pu.getName()+";";
				}
			}
			examConfig.setTypesel_fk(partnerUserStr);//临时将此字段存放代维人员姓名用于页面显示
		}
	    request.setAttribute("list",l);
		request.setAttribute("resultSize",pm.getTotal());
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("examConfigList");
	}
	
	/**
	 * 指定考试合格者(生成系统用户号)/不合格者
	 */
	public ActionForward mark(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		String pass = request.getParameter("pass");
		String[] ids = request.getParameterValues("checkbox11");
		PartnerUserMgr partnerUserMgr = (PartnerUserMgr) getBean("partnerUserMgr");
		ExamResultService resultService = (ExamResultService)getBean("examResultService");
		ITawSystemUserManager mgr = (ITawSystemUserManager) getBean("ItawSystemUserManagerFlush");
		for(int i=0;i<ids.length;i++){
			String id = ids[i];
			ExamResult result = resultService.getExamResultById(id);
			//更改代维人员为上岗状态
			PartnerUser partnerUser = partnerUserMgr.getPartnerUserByPersonCardNo(result.getTester());
			if("true".equals(pass)){ //合格
				partnerUser.setPostState("112020401");//上岗
				partnerUserMgr.savePartnerUser(partnerUser);
				//更改考试结果为已指定合格状态
				result.setAssignType(1);
				//生成系统用户
				TawSystemUser sysUser = new TawSystemUser();
				sysUser.setUserid(partnerUser.getUserId());
				sysUser.setUsername(partnerUser.getName());
				sysUser.setDeptid(partnerUser.getDeptId());
				sysUser.setDeptname(partnerUser.getDeptName());
				sysUser.setSavetime(new Date());
				sysUser.setEnabled(true);
				sysUser.setAccountExpired(false);
				sysUser.setAccountLocked(false);
				sysUser.setCredentialsExpired(false);
				sysUser.setFailCount(0);
				sysUser.setDeleted("0");
				// md5加密
				sysUser.setPassword(new Md5PasswordEncoder().encodePassword(
						"123456", new String()));
				//将该用户放到缓存中
				/**
				 * 合作伙伴中没有作业计划，屏蔽之
				 * modify by 陈元蜀 2012-09-04 begin				 
				TawWorkplanCacheBean tawWorkplanCacheBean = (TawWorkplanCacheBean) ApplicationContextHolder
				.getInstance().getBean("TawWorkplanCacheBean");
				Map workplanMap = tawWorkplanCacheBean.getWorkplanUser(); 
				Map userMap = (Map)workplanMap.get("userMap");
				userMap.put(sysUser.getUserid(),sysUser.getUsername());
				* modify by 陈元蜀 2012-09-04 end
				 */
				mgr.saveTawSystemUser(sysUser, null);
				
			}else if("false".equals(pass)){ //不合格
				//更改考试结果为已指定不合格状态
				result.setAssignType(2);
				//不物理删除
//				partnerUserMgr.removePartnerUser(partnerUser.getId()); //删除代维人员信息
				partnerUser.setDeleted("1"); //把状态设置为删除
				partnerUserMgr.savePartnerUser(partnerUser);
			}
			resultService.saveOrUpdateExamResult(result);
			
		}
		
		return mapping.findForward("suc");
	}
	
	/**
	 * 公布合格者列表
	 */
	public ActionForward findQualifierList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		String issueId = request.getParameter("issueId");
		ExamResultService resultService = (ExamResultService)getBean("examResultService");
		PartnerUserMgr partnerUserMgr = (PartnerUserMgr) getBean("partnerUserMgr");
		PageModel pm = null;
	    List l = null;//分页相关
    	String pageIndexStr = request.getParameter("pager.offset")==null ? "0" : request.getParameter("pager.offset");
        int pageIndex  = Integer.parseInt(pageIndexStr);
    	//定义每页显示数
    	int pageSize = 15;
	    pm = resultService.findExamResultList(issueId, pageIndex, pageSize);
	    l = pm.getDatas();
	    for(int i=0;i<l.size();i++){
	    	ExamResult r = (ExamResult)l.get(i);
	    	if(r.getExamType()==2){
	    		String tester = r.getTester();
	    		PartnerUser pu = partnerUserMgr.getPartnerUserByPersonCardNo(tester);
	    		if(pu != null){
	    			r.setTesterName(pu.getName());
	    			r.setUserId(pu.getUserId());
	    		}
	    	}
	    }
	    request.setAttribute("issueId", issueId);
	    request.setAttribute("list",l);
		request.setAttribute("resultSize",pm.getTotal());
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("examResultList");
	}
	
	/**
	 * 查询代维公司需要参考人数
	 */
	public ActionForward getPartnerTesterCount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		String partnerDeptId = request.getParameter("partnerDeptId");
		PartnerUserMgr partnerUserMgr = (PartnerUserMgr) getBean("partnerUserMgr");
		PartnerDeptMgr partnerDeptMgr = (PartnerDeptMgr) ApplicationContextHolder.getInstance().getBean("partnerDeptMgr");
		PartnerDept dept = partnerDeptMgr.getPartnerDept(partnerDeptId);
		int count = partnerUserMgr.findAllPnrTesttersCount(dept.getDeptMagId());
		
		try {
			response.setContentType("text/html;charset=UTF-8");	
			response.getWriter().print(count);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
