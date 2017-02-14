package com.boco.eoms.partner.deviceAssess.webapp.action;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.joda.time.DateTime;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.loging.BocoLog;
import com.boco.eoms.deviceManagement.baseInfo.model.CheckPointUploadForm;
import com.boco.eoms.deviceManagement.faultInfo.model.ImportResult;
import com.boco.eoms.partner.deviceAssess.mgr.DeviceAssessContentMgr;
import com.boco.eoms.partner.deviceAssess.mgr.SoftApplyRecordMgr;
import com.boco.eoms.partner.deviceAssess.model.DeviceAssessApprove;
import com.boco.eoms.partner.deviceAssess.model.DeviceAssessContent;
import com.boco.eoms.partner.deviceAssess.model.SoftApplyRecord;
import com.boco.eoms.partner.deviceAssess.util.SearchUtil;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

/**
 * <p>
 * Title:软件申请问题记录
 * </p>
 * <p>
 * Description:软件申请问题记录
 * </p>
 * <p>
 * Sun Sep 26 09:32:29 CST 2011
 * </p>
 * 
 * @moudle.getAuthor() zhangkeqi
 * @moudle.getVersion() 1.0
 * 
 */
public final class SoftApplyRecordAction extends BaseAction {
 
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
		ActionForward af = new ActionForward("/softApplyRecord.do?method=search",true);
		return af;
	}
 	
	/**
	 * 新增软件申请问题记录
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
		request.setAttribute("PAGE_TYPE", "ADD_PAGE");
		return mapping.findForward("edit");
	}
	
 	/**
	 * 驳回再提交软件申请问题记录
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
    public ActionForward goToRebuteSubmit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
    	SoftApplyRecordMgr softApplyRecordMgr = (SoftApplyRecordMgr) getBean("softApplyRecordMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		SoftApplyRecord softApplyRecord = softApplyRecordMgr.getSoftApplyRecord(id);
		
    	//审批内容Mgr
		DeviceAssessContentMgr dacMgr = (DeviceAssessContentMgr)getBean("deviceAssessContentMgr");
		//该事件（数据）的审批列表
		List<DeviceAssessContent> dacList = dacMgr.findAssessContentList(softApplyRecord.getId());
		request.setAttribute("dacList", dacList);
		request.setAttribute("size", dacList.size());
		
		request.setAttribute("softApplyRecord", softApplyRecord);
    	request.setAttribute("PAGE_TYPE", "REBUTESUBMIT_PAGE");
		return mapping.findForward("edit");
	}
	
	/**
	 * 修改软件申请问题记录
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
		SoftApplyRecordMgr softApplyRecordMgr = (SoftApplyRecordMgr) getBean("softApplyRecordMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		SoftApplyRecord softApplyRecord = softApplyRecordMgr.getSoftApplyRecord(id);
		
		//审批内容Mgr
		DeviceAssessContentMgr dacMgr = (DeviceAssessContentMgr)getBean("deviceAssessContentMgr");
		//该事件（数据）的审批列表
		List<DeviceAssessContent> dacList = dacMgr.findAssessContentList(softApplyRecord.getId());
		request.setAttribute("dacList", dacList);
		request.setAttribute("size", dacList.size());
		
		request.setAttribute("softApplyRecord", softApplyRecord);
		request.setAttribute("PAGE_TYPE", "EDIT_PAGE");
		return mapping.findForward("edit");
	}
    /**
     * 查看软件申请问题记录
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ActionForward goToDetail(ActionMapping mapping, ActionForm form,
    		HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    	//审批内容Mgr
    	DeviceAssessContentMgr dacMgr = (DeviceAssessContentMgr)getBean("deviceAssessContentMgr");
    	SoftApplyRecordMgr softApplyRecordMgr = (SoftApplyRecordMgr) getBean("softApplyRecordMgr");
    	String id = StaticMethod.null2String(request.getParameter("id"));
    	SoftApplyRecord softApplyRecord = softApplyRecordMgr.getSoftApplyRecord(id);
    	
    	//该事件（数据）的审批列表
    	List<DeviceAssessContent> dacList = dacMgr.findAssessContentList(softApplyRecord.getId());
    	request.setAttribute("dacList", dacList);
		request.setAttribute("softApplyRecord", softApplyRecord);
    	request.setAttribute("size", dacList.size());
    	request.setAttribute("PAGE_TYPE", "DETAIL_TYPE");
    	
    	return mapping.findForward("goToDetail");
    }
	
	/**
	 * 保存软件申请问题记录
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
		SoftApplyRecordMgr softApplyRecordMgr = (SoftApplyRecordMgr) getBean("softApplyRecordMgr");
		SoftApplyRecord softApplyRecord = new SoftApplyRecord();
		
		Map paramsMap = request.getParameterMap();
		BeanUtils.populate(softApplyRecord, paramsMap);
		

		//设置审批信息(事件ID在保存了softApplyRecord生成主键后设置)
		DeviceAssessApprove daa = new DeviceAssessApprove();
		daa.setAssessType("1122103");//事件类型
		daa.setSheetNum("");//工单号
		daa.setName(softApplyRecord.getAffairName());//名称
		daa.setCommitTime(new DateTime(new Date()).toString("yyyy-MM-dd HH:mm:ss"));//提交时间
		daa.setApproveUser(request.getParameter("approvalUser"));//审批人
		daa.setClassName(SoftApplyRecord.class.getSimpleName());//实体类名
		daa.setModifyUrl("/partner/deviceAssess/softApplyRecord.do?method=edit");//修改URL链接
		daa.setDetailUrl("/partner/deviceAssess/softApplyRecord.do?method=goToDetail");//详细查看URL链接
		daa.setState(2);//审批状态（0驳回 1通过 2待审批）
		softApplyRecordMgr.saveDataAndApprove(softApplyRecord,daa);
			
		ActionForward af = new ActionForward("/softApplyRecord.do?method=search",true);
		return af;
	}
	
	/**
	 * 删除软件申请问题记录
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
		response.setCharacterEncoding("utf-8");
		try {
			SoftApplyRecordMgr softApplyRecordMgr = (SoftApplyRecordMgr) getBean("softApplyRecordMgr");
			String id = StaticMethod.null2String(request.getParameter("id"));
			softApplyRecordMgr.removeSoftApplyRecord(id);

			response.getWriter().write(
					new Gson()
							.toJson(new ImmutableMap.Builder<String, String>()
									.put("success", "true").put("msg", "ok")
									.put("infor", "删除成功！").build()));

		} catch (RuntimeException e) {
			BocoLog.info(this, "删除出错！");
			e.printStackTrace();
			response.getWriter().write(
					new Gson()
							.toJson(new ImmutableMap.Builder<String, String>()
									.put("success", "true").put("msg", "ok")
									.put("infor", "删除出错！").build()));
		} finally {
			return null;
		}
	}
	
	/**
	 * 分页显示软件申请问题记录列表
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
		String pageIndexName = new org.displaytag.util.ParamEncoder("softApplyRecordList")
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = Integer.valueOf(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		SoftApplyRecordMgr softApplyRecordMgr = (SoftApplyRecordMgr) getBean("softApplyRecordMgr");
		
		Map reqMap = request.getParameterMap();
		String whereStr = "";
		
		Map map;
		String stateStr = request.getParameter("state");
		if(stateStr != null && !"".equals(stateStr)) {
			String hql = "select sar from SoftApplyRecord sar,DeviceAssessApprove daa ";
			whereStr = SearchUtil.getSqlFromRequestMap(reqMap,"sar");
			hql += whereStr;
			if(whereStr != null && !"".equals(whereStr)) {
				hql += " and sar.id=daa.assessId order by sar.createTime";
			} else {
				hql += " where sar.id=daa.assessId order by sar.createTime";
			}
			map = (Map) softApplyRecordMgr.getSoftApplyRecordsWithHQL(pageIndex, pageSize, hql);
		} else {
			whereStr = SearchUtil.getSqlFromRequestMap(reqMap,"");
			map = (Map) softApplyRecordMgr.getSoftApplyRecords(pageIndex, pageSize, whereStr);
		}
		
		List list = (List) map.get("result");
		request.setAttribute("softApplyRecordList", list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");
	}
	
	/**
	 * 导入
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward importData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		CheckPointUploadForm uploadForm = (CheckPointUploadForm) form;
		FormFile formFile = uploadForm.getImportFile();
		
		String cpType = request.getParameter("cpType");
		
		Map<String,String> params = new HashMap<String,String>();
		params.put("cpType", cpType);
		
		PrintWriter writer = null;
		try{
//			writer = response.getWriter();
//			//ImportResult result = this.getMainBean().importFromFile(formFile.getInputStream(),formFile.getFileName(),params);
//			if(result.getResultCode().equals("200")) {
//				writer.write(
//						new Gson().toJson(new ImmutableMap.Builder<String, String>()
//								.put("success", "true")
//								.put("msg", "ok")
//								.put("infor", "'导入成功,共计导入"+result.getImportCount()+"条记录'").build()));
//			}
		}catch(Exception e){
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