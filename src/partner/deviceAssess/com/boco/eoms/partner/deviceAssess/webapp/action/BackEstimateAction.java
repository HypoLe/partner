package com.boco.eoms.partner.deviceAssess.webapp.action;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.dict.model.TawSystemDictType;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.eva.util.DateTimeUtil;
import com.boco.eoms.partner.deviceAssess.mgr.ArithmeticMgr;
import com.boco.eoms.partner.deviceAssess.mgr.BackEstimateMgr;
import com.boco.eoms.partner.deviceAssess.model.BackEstimate;
import com.boco.eoms.partner.deviceAssess.util.BackEstimateConstants;
import com.boco.eoms.partner.deviceAssess.webapp.form.BackEstimateForm;

/**
 * <p>
 * Title:后评估指标体系
 * </p>
 * <p>
 * Description:后评估指标体系
 * </p>
 * <p>
 * Thu Oct 14 10:55:23 CST 2010
 * </p>
 *  
 * @moudle.getAuthor() benweiwei
 * @moudle.getVersion() 1.0
 * 
 */
public final class BackEstimateAction extends BaseAction {
 
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
	 * 新增后评估指标体系
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
	 * 修改后评估指标体系
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
		BackEstimateMgr backEstimateMgr = (BackEstimateMgr) getBean("backEstimateMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		BackEstimate backEstimate = backEstimateMgr.getBackEstimate(id);
		BackEstimateForm backEstimateForm = (BackEstimateForm) convert(backEstimate);
		updateFormBean(mapping, request, backEstimateForm);
		return mapping.findForward("edit");
	}
	
	/**
	 * 保存后评估指标体系
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
		BackEstimateMgr backEstimateMgr = (BackEstimateMgr) getBean("backEstimateMgr");
		BackEstimateForm backEstimateForm = (BackEstimateForm) form;
		boolean isNew = (null == backEstimateForm.getId() || "".equals(backEstimateForm.getId()));
		BackEstimate backEstimate = (BackEstimate) convert(backEstimateForm);
//		if (isNew) {
			backEstimateMgr.saveBackEstimate(backEstimate);
//		} else {
//			backEstimateMgr.saveBackEstimate(backEstimate);
//		}
		return mapping.findForward("success");
	}
	
	/**
	 * 删除后评估指标体系
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
		BackEstimateMgr backEstimateMgr = (BackEstimateMgr) getBean("backEstimateMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		backEstimateMgr.removeBackEstimate(id);
		return search(mapping, form, request, response);
	}
	
	/**
	 * 分页显示后评估指标体系列表
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
				BackEstimateConstants.BACKESTIMATE_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = Integer.valueOf(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		BackEstimateMgr backEstimateMgr = (BackEstimateMgr) getBean("backEstimateMgr");
		Map map = (Map) backEstimateMgr.getBackEstimates(pageIndex, pageSize, "");
		List list = (List) map.get("result");
		request.setAttribute(BackEstimateConstants.BACKESTIMATE_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");
	}

	/**
	 * 按条件显示后评估指标体系表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchBackEstimate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String factory = StaticMethod.null2String(request.getParameter("factory"));
		String speciality = StaticMethod.null2String(request.getParameter("speciality"));
		String equipmentType = StaticMethod.null2String(request.getParameter("equipmentType"));
		String year = StaticMethod.null2String(request.getParameter("year"));
		String quarter = StaticMethod.null2String(request.getParameter("quarter"));
//    	获得本季度的开始日期和结束日期 
//    	Calendar cal = Calendar.getInstance();
    	Date startDate = new Date();
    	Date endDate = 	new Date();
    	
    	if("".equals(quarter)){
        	startDate = DateTimeUtil.getFirstDayOfQuarter(Integer.valueOf(year),1);
        	endDate = DateTimeUtil.getFirstDayOfQuarter(Integer.valueOf(year)+1,1);    		
    	} else {
        	startDate = DateTimeUtil.getFirstDayOfQuarter(Integer.valueOf(year),Integer.valueOf(quarter));
        	endDate = DateTimeUtil.getFirstDayOfQuarter(Integer.valueOf(year),Integer.valueOf(quarter)+1);
    	}

    	if("4".equals(quarter)){
    		endDate = DateTimeUtil.getFirstDayOfQuarter(Integer.valueOf(year)+1,1);
    	}
    	endDate = new Date(StaticMethod.getMillis(endDate));
    	startDate = new Date(StaticMethod.getMillis(startDate));
    	BackEstimateMgr backEstimateMgr = (BackEstimateMgr) getBean("backEstimateMgr");
    	StringBuffer whereStr = new StringBuffer();
    	whereStr.append(" where 1 = 1 ");
    	if(!"".equals(factory)){
        	whereStr.append(" and factory = '");
        	whereStr.append(factory);
        	whereStr.append("'");
    	}
    	if(!"".equals(speciality)){
        	whereStr.append(" and speciality = '");
        	whereStr.append(speciality);
        	whereStr.append("'");
    	}
    	if(!"".equals(equipmentType)){
        	whereStr.append(" and equipmentType = '");
        	whereStr.append(equipmentType);
        	whereStr.append("'");
    	}
    	if(!"".equals(year)){
        	whereStr.append(" and year = '");
        	whereStr.append(year);
        	whereStr.append("'");
    	}
    	if(!"".equals(quarter)){
        	whereStr.append(" and quarter = '");
        	whereStr.append(quarter);
        	whereStr.append("'");
    	}
    	List backEstimatesList = backEstimateMgr.getBackEstimatesList(whereStr.toString());
    	
    	if(backEstimatesList.size()>0){
    		BackEstimate backEstimate = (BackEstimate)backEstimatesList.get(0);
    		BackEstimateForm backEstimateForm = (BackEstimateForm) convert(backEstimate);
    		request.setAttribute("backEstimateForm", backEstimateForm);
    		return mapping.findForward("detail");
    	} else {
    		BackEstimateForm backEstimateForm = new BackEstimateForm();
    		ArithmeticMgr arithmeticMgr = (ArithmeticMgr) getBean("arithmeticMgr");
    		
//    		获得设备故障率
    		double fixingFaultRate = arithmeticMgr.getFixingFaultRate(endDate, startDate, factory, speciality, equipmentType);
    		backEstimateForm.setFacilityFauTar(String.valueOf(fixingFaultRate));

//    		获得坏板率
    		double bad = arithmeticMgr.getBadBattenRate(endDate, startDate, factory, speciality, equipmentType);
    		backEstimateForm.setBadPlankTar(String.valueOf(bad));
    		
//    		重大故障次数
    		int bigFaultNum = arithmeticMgr.getBigFaultNum(endDate, startDate, factory, speciality, equipmentType);
    		backEstimateForm.setBigFaultTar(String.valueOf(bigFaultNum));
   		
//    		设备问题数
    		int facilityNum = arithmeticMgr.getFacilityNum(endDate, startDate, factory, speciality, equipmentType);
    		backEstimateForm.setFacilityQueTar(String.valueOf(facilityNum));
    		
//    		软件升级(含补丁)成功率
    		double softwareUpRate = arithmeticMgr.getSoftwareUpRate(endDate, startDate, factory, speciality, equipmentType);
    		backEstimateForm.setSoftwareSucTar(String.valueOf(softwareUpRate));
    		
//    		故障处理平均时长(小时)
    		double faultAvgTime = arithmeticMgr.getFaultAvgTime(endDate, startDate, factory, speciality, equipmentType);
    		backEstimateForm.setFaultAvgTar(String.valueOf(faultAvgTime));

//    		业务恢复平均时长(小时)
    		double resumeAvgTime = arithmeticMgr.getResumeAvgTime(endDate, startDate, factory, speciality, equipmentType);
    		backEstimateForm.setOperationRenewTar(String.valueOf(resumeAvgTime));
  
//    		板件返修平均时长(天)
    		double plankRepairAvgTime = arithmeticMgr.getPlankRepairAvgTime(endDate, startDate, factory, speciality, equipmentType);
    		backEstimateForm.setPlankRepairTar(String.valueOf(plankRepairAvgTime));

//    		咨询服务平均时长(天)
    		double referServeAvgTime = arithmeticMgr.getReferServeAvgTime(endDate, startDate, factory, speciality, equipmentType);
    		backEstimateForm.setReferServeTar(String.valueOf(referServeAvgTime));
 
//    		技术服务满意度
    		int artDegree = arithmeticMgr.getArtDegree(endDate, startDate, factory, speciality, equipmentType);
    		backEstimateForm.setArtServeTar(String.valueOf(artDegree));

//    		工程服务满意度
    		int projectSer = arithmeticMgr.getProjectSer(endDate, startDate, factory, speciality, equipmentType);
    		backEstimateForm.setProjectServeTar(String.valueOf(projectSer));
  
//    		培训服务满意度
    		int trainSer = arithmeticMgr.getTrainSer(endDate, startDate, factory, speciality, equipmentType);
    		backEstimateForm.setTrainServeTar(String.valueOf(trainSer));
    		
    		backEstimateForm.setSpecialServeTar("60");
    		
    		backEstimateForm.setFactory(factory);
    		backEstimateForm.setSpeciality(speciality);
    		backEstimateForm.setYear(year);
    		backEstimateForm.setQuarter(quarter);
    		request.setAttribute("backEstimateForm", backEstimateForm);
    		return mapping.findForward("edit");
    	}
	}	
	
	/**
	 * 进入查询后评估指标体系页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward searchForward(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
//    	获得当前年月，方便用户使用
    	Calendar cal = Calendar.getInstance();
    	int year = cal.get(Calendar.YEAR);
    	int month = cal.get(Calendar.MONTH)+1; 
    	String quarter = "";
    	if(month>0&&month<4){
    		quarter = "4";
    		year = year - 1 ;
    	} else if (month>3&&month<7){
    		quarter = "1";
    	} else if (month>6&&month<10){ 
    		quarter = "2";
    	} else if (month>9&&month<13){
    		quarter = "3";
    	}
		request.setAttribute("quarterValue", quarter);
		request.setAttribute("yearValue", year);
		return mapping.findForward("backEstimate");
	}	
	
	/**
	 * 专业 联动 设备类型
	 * author:benweiwei
	 * 2010-9-28
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changeFacility(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		JSONArray json = new JSONArray();
		JSONObject jitem = new JSONObject();
		String speciality = request.getParameter("speciality");
		ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");
		List facilityList = mgr.getDictSonsByDictid(speciality);

		
		StringBuffer c_list = new StringBuffer();
		c_list.append("," + "");
		c_list.append("," + "请选择设备类型");
		
		for (int i = 0; i < facilityList.size(); i++) {
			TawSystemDictType tsDict = (TawSystemDictType)facilityList.get(i);
			
			c_list.append("," + tsDict.getDictId());
			c_list.append("," + tsDict.getDictName());
		}
		String facility = c_list.toString();
		
		facility = facility.substring(1, facility.length());

		
//		存入网格list
		jitem.put("facility", facility);
		json.put(jitem);
		
		response.setCharacterEncoding("utf-8");		
		response.getWriter().write(json.toString());
		
		return null;
	}	
	/**
	 * 调用查看模板jsp页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward detailbBackModel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception { 
		return mapping.findForward("detailbBackModel");
	}
}