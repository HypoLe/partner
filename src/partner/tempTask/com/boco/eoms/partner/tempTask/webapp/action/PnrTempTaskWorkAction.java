package com.boco.eoms.partner.tempTask.webapp.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.eva.util.DateTimeUtil;
import com.boco.eoms.partner.tempTask.mgr.IPnrTempTaskExeMgr;
import com.boco.eoms.partner.tempTask.mgr.IPnrTempTaskMainMgr;
import com.boco.eoms.partner.tempTask.mgr.IPnrTempTaskWorkMgr;
import com.boco.eoms.partner.tempTask.model.PnrTempTaskExe;
import com.boco.eoms.partner.tempTask.model.PnrTempTaskMain;
import com.boco.eoms.partner.tempTask.model.PnrTempTaskWork;
import com.boco.eoms.partner.tempTask.util.PnrTempTaskConstants;
import com.boco.eoms.partner.tempTask.webapp.form.PnrTempTaskExeForm;
import com.boco.eoms.partner.tempTask.webapp.form.PnrTempTaskWorkForm;

/**
 * <p>
 * Title:合作伙伴临时任务管理
 * </p>
 * <p>
 * Description:合作伙伴临时任务管理主表信息
 * </p>
 * <p>
 * Mon Apr 26 14:09:01 CST 2010
 * </p>
 * 
 * @moudle.getAuthor() daizhigang
 * @moudle.getVersion() 1.0
 * 
 */
public final class PnrTempTaskWorkAction extends BaseAction {
 
	/**
	 * 未指定方法时默认调用的方法
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward unspecified(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return search(mapping, form, request, response);
	}
 	
 	/**
	 * 新增合作伙伴临时任务管理
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		return mapping.findForward("edit");
	}
	
	/**
	 * 修改合作伙伴临时任务管理
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IPnrTempTaskWorkMgr pnrTempTaskWorkMgr = (IPnrTempTaskWorkMgr) getBean("pnrTempTaskWorkMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		PnrTempTaskWork pnrTempTaskWork = pnrTempTaskWorkMgr.getPnrTempTaskWork(id);
		PnrTempTaskWorkForm pnrTempTaskWorkForm = (PnrTempTaskWorkForm) convert(pnrTempTaskWork);
		updateFormBean(mapping, request, pnrTempTaskWorkForm);
		return mapping.findForward("edit");
	}
	
	/**
	 * 保存合作伙伴临时任务管理
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IPnrTempTaskWorkMgr pnrTempTaskWorkMgr = (IPnrTempTaskWorkMgr) getBean("pnrTempTaskWorkMgr");
		PnrTempTaskWorkForm pnrTempTaskWorkForm = (PnrTempTaskWorkForm) form;
		boolean isNew = (null == pnrTempTaskWorkForm.getId() || "".equals(pnrTempTaskWorkForm.getId()));
		PnrTempTaskWork pnrTempTaskWork = (PnrTempTaskWork) convert(pnrTempTaskWorkForm);
		pnrTempTaskWork.setWarnTime(StaticMethod.nullObject2String(pnrTempTaskWork.getStartTime()));
		if (isNew) {
			pnrTempTaskWorkMgr.savePnrTempTaskWork(pnrTempTaskWork);
		} else {
			pnrTempTaskWorkMgr.savePnrTempTaskWork(pnrTempTaskWork);
		}
		return search(mapping, pnrTempTaskWorkForm, request, response);
	}
	
	/**
	 * 删除合作伙伴临时任务管理
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward remove(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IPnrTempTaskWorkMgr pnrTempTaskWorkMgr = (IPnrTempTaskWorkMgr) getBean("pnrTempTaskWorkMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		pnrTempTaskWorkMgr.removePnrTempTaskWork(id);
		return search(mapping, form, request, response);
	}
	
	/**
	 * 分页显示合作伙伴临时任务管理列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				PnrTempTaskConstants.PNRAGREEMENTWORK_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		IPnrTempTaskWorkMgr pnrTempTaskWorkMgr = (IPnrTempTaskWorkMgr) getBean("pnrTempTaskWorkMgr");
		Map map = (Map) pnrTempTaskWorkMgr.getPnrTempTaskWorks(pageIndex, pageSize, "");
		List list = (List) map.get("result");
		request.setAttribute(PnrTempTaskConstants.PNRAGREEMENTWORK_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");
	}
	
	/**
	 * 分页显示合作伙伴临时任务管理列表，支持Atom方式接入Portal
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception

	public ActionForward search4Atom(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			// --------------用于分页，得到当前页号-------------
			final Integer pageIndex = new Integer(request
					.getParameter("pageIndex"));
			final Integer pageSize = new Integer(request
					.getParameter("pageSize"));
			IPnrTempTaskWorkMgr pnrTempTaskWorkMgr = (IPnrTempTaskWorkMgr) getBean("pnrTempTaskWorkMgr");
			Map map = (Map) pnrTempTaskWorkMgr.getPnrTempTaskWorks(pageIndex, pageSize, "");
			List list = (List) map.get("result");
			PnrTempTaskWork pnrTempTaskWork = new PnrTempTaskWork();
			
			//创建ATOM源
			Factory factory = Abdera.getNewFactory();
			Feed feed = factory.newFeed();
			
			// 分页
			for (int i = 0; i < list.size(); i++) {
				pnrTempTaskWork = (PnrTempTaskWork) list.get(i);
				
				// TODO 请按照下面的实例给entry赋值
				Entry entry = feed.insertEntry();
				entry.setTitle("<a href='" + request.getScheme() + "://"
						+ request.getServerName() + ":"
						+ request.getServerPort()
						+ request.getContextPath()
						+ "/pnrTempTaskWork/pnrTempTaskWorks.do?method=edit&id="
						+ pnrTempTaskWork.getId() + "' target='_blank'>"
						+ display name for list + "</a>");
				entry.setSummary(summary);
				entry.setContent(content);
				entry.setLanguage(language);
				entry.setText(text);
				entry.setRights(tights);
				
				// 以下三项需要传入Date类型或String类型（yyyy-MM-dd）的参数
				entry.setUpdated(new java.util.Date());
				entry.setPublished(new java.util.Date());
				entry.setEdited(new java.util.Date());
				
				// 为person的name属性赋值，entry.addAuthor可以随意赋值
				Person person = entry.addAuthor(userId);
				person.setName(userName);
			}
			
			// 每页显示条数
			feed.setText(map.get("total").toString());
		    OutputStream os = response.getOutputStream();
		    PrintStream ps = new PrintStream(os);
		    feed.getDocument().writeTo(ps);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
		 */
	/**
	 * 分页显示合作伙伴临时任务待执行列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward undoList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TawSystemSessionForm sessionform = (TawSystemSessionForm) request
		.getSession().getAttribute("sessionform");
		String userId = sessionform.getUserid();
		String deptId = sessionform.getDeptid();
		String pageIndexName = new org.displaytag.util.ParamEncoder(
				PnrTempTaskConstants.PNRAGREEMENTWORK_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = new Integer(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		IPnrTempTaskMainMgr pnrTempTaskMainMgr = (IPnrTempTaskMainMgr) getBean("pnrTempTaskMainMgr");
		IPnrTempTaskExeMgr pnrTempTaskExeMgr = (IPnrTempTaskExeMgr) getBean("pnrTempTaskExeMgr");
		//得到待执行项
		Map map = (Map) pnrTempTaskExeMgr.getPnrTempTaskUndo(pageIndex, pageSize,userId,deptId);
		List list = (List) map.get("result");
		Map workNum = new HashMap();
		PnrTempTaskWork pnrTempTaskWork = new PnrTempTaskWork();
		for(int i=0;i<list.size();i++){
			pnrTempTaskWork=(PnrTempTaskWork)list.get(i);
			workNum.put(pnrTempTaskWork.getTempTaskId(), String.valueOf(StaticMethod.nullObject2int(workNum.get(pnrTempTaskWork.getTempTaskId()), 0)+1));
		}
		request.setAttribute("workNum", workNum);
		request.setAttribute(PnrTempTaskConstants.PNRAGREEMENTWORK_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("undoList");
	}
	/**
	 * 显示合作伙伴工作执行页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward workExecute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IPnrTempTaskMainMgr pnrTempTaskMainMgr = (IPnrTempTaskMainMgr) getBean("pnrTempTaskMainMgr");
		IPnrTempTaskWorkMgr pnrTempTaskWorkMgr = (IPnrTempTaskWorkMgr) getBean("pnrTempTaskWorkMgr");
		IPnrTempTaskExeMgr pnrTempTaskExeMgr = (IPnrTempTaskExeMgr) getBean("pnrTempTaskExeMgr");
		String workId =  StaticMethod.nullObject2String(request.getParameter("workId"));
		PnrTempTaskWork pnrTempTaskWork = pnrTempTaskWorkMgr.getPnrTempTaskWork(workId);
		PnrTempTaskMain pnrTempTaskMain = pnrTempTaskMainMgr.getPnrTempTaskMain(pnrTempTaskWork.getTempTaskId());
		PnrTempTaskExeForm pnrTempTaskExeForm = new PnrTempTaskExeForm();
		pnrTempTaskExeForm.setTempTaskId(pnrTempTaskWork.getTempTaskId());
		pnrTempTaskExeForm.setWorkId(pnrTempTaskWork.getId());
		request.setAttribute("pnrTempTaskMain", pnrTempTaskMain);
		request.setAttribute("pnrTempTaskWork", pnrTempTaskWork);
		request.setAttribute("pnrTempTaskExeForm", pnrTempTaskExeForm);
		return mapping.findForward("workExecute");
	}
	/**
	 * 保存执行数据
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward saveExecute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IPnrTempTaskWorkMgr pnrTempTaskWorkMgr = (IPnrTempTaskWorkMgr) getBean("pnrTempTaskWorkMgr");
		IPnrTempTaskExeMgr pnrTempTaskExeMgr = (IPnrTempTaskExeMgr) getBean("pnrTempTaskExeMgr");
		// 读取：当前操作用户信息
		TawSystemSessionForm sessionform = this.getUser(request);
		String operateDeptId = sessionform.getDeptid();
		String operateUserId = sessionform.getUserid();	

		String exeContent =  StaticMethod.nullObject2String(request.getParameter("exeContent"));
		String workId =  StaticMethod.nullObject2String(request.getParameter("workId"));
		String tempTaskId =  StaticMethod.nullObject2String(request.getParameter("tempTaskId"));

		PnrTempTaskExe pnrTempTaskExe = new PnrTempTaskExe();
		pnrTempTaskExe.setExeContent(exeContent);
		pnrTempTaskExe.setWorkId(workId);
		pnrTempTaskExe.setTempTaskId(tempTaskId);
		pnrTempTaskExe.setExeStartTime(StaticMethod.getTimestamp());
		pnrTempTaskExeMgr.savePnrTempTaskExe(pnrTempTaskExe);
		PnrTempTaskWork pnrTempTaskWork = pnrTempTaskWorkMgr.getPnrTempTaskWork(pnrTempTaskExe.getWorkId());
		pnrTempTaskWork.setLastExecuteTime(pnrTempTaskExe.getExeStartTime());
		pnrTempTaskWork.setWorkFlag("1");
		pnrTempTaskWorkMgr.savePnrTempTaskWork(pnrTempTaskWork);
		return mapping.findForward("flushParentPage");
	}
	/**
	 * 取消本周期的提醒
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward workWarnCancel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		IPnrTempTaskWorkMgr pnrTempTaskWorkMgr = (IPnrTempTaskWorkMgr) getBean("pnrTempTaskWorkMgr");
		String workId =  StaticMethod.nullObject2String(request.getParameter("workId"));
		PnrTempTaskWork pnrTempTaskWork = pnrTempTaskWorkMgr.getPnrTempTaskWork(workId);
		String localTime = StaticMethod.getLocalString();
		String nextWarnTime = DateTimeUtil.getTimesByCycle(pnrTempTaskWork.getCycle(),StaticMethod.getTimestampString(pnrTempTaskWork.getStartTime()),1);
		while(StaticMethod.getTimeDistance(nextWarnTime,localTime)>0){
			nextWarnTime = DateTimeUtil.getTimesByCycle(pnrTempTaskWork.getCycle(),nextWarnTime,1);
		}
		pnrTempTaskWork.setWarnTime(nextWarnTime);
		pnrTempTaskWorkMgr.savePnrTempTaskWork(pnrTempTaskWork);
		return null;
	}
}