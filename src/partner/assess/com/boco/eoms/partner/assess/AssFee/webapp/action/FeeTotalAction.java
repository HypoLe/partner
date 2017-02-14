package com.boco.eoms.partner.assess.AssFee.webapp.action;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.area.model.TawSystemArea;
import com.boco.eoms.partner.assess.AssFee.mgr.IFeeDetailMgr;
import com.boco.eoms.partner.assess.AssFee.mgr.IFeeTotalMgr;
import com.boco.eoms.partner.assess.AssFee.mgr.IFeeTreeMgr;
import com.boco.eoms.partner.assess.AssFee.model.FeeDetail;
import com.boco.eoms.partner.assess.AssFee.model.FeeTotal;
import com.boco.eoms.partner.assess.AssFee.model.FeeTree;
import com.boco.eoms.partner.assess.AssFee.util.FeeTotalConstants;
import com.boco.eoms.partner.assess.AssFee.webapp.form.FeeDetailForm;
import com.boco.eoms.partner.assess.AssFee.webapp.form.FeeTotalForm;
import com.boco.eoms.partner.assess.AssRight.util.AssRoleIdList;
import com.boco.eoms.partner.baseinfo.util.PartnerCityByUser;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;

/**
 * <p>
 * Title:计算结果信息
 * </p>
 * <p>
 * Description:计算结果信息
 * </p>
 * <p> 
 * Tue Nov 23 16:04:36 CST 2010
 * </p>
 * 
 * @moudle.getAuthor() benweiwei
 * @moudle.getVersion() 1.0
 * 
 */
public final class FeeTotalAction extends BaseAction {
 
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
	 * 跳转到查询页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
    	Calendar cal = Calendar.getInstance();
    	int year = cal.get(Calendar.YEAR);
    	FeeTotalForm feeTotalForm = (FeeTotalForm) form;
    	feeTotalForm.setYear(String.valueOf(year));	
    	updateFormBean(mapping, request, feeTotalForm);
		return mapping.findForward("toSearch");
	} 	
	
 	/**
	 * 新增计算结果信息
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
//    	获得当前年月,方便用户使用
    	Calendar cal = Calendar.getInstance();
    	int year = cal.get(Calendar.YEAR)+1;
    	FeeTotalForm feeTotalForm = (FeeTotalForm) form;
    	feeTotalForm.setYear(String.valueOf(year));
//		得到当前地市	
		AssRoleIdList roleIdList = (AssRoleIdList)ApplicationContextHolder
		.getInstance().getBean("lineAssRoleIdList");
		String rootAreaId = roleIdList.getRootAreaId();
//		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
//		String province = pnrBaseAreaIdList.getRootAreaId();
    	List city = PartnerCityByUser.getCityByProvince(rootAreaId);   
    	
    	List showCityList = new ArrayList();
    	
		IFeeTotalMgr feeTotalMgr = (IFeeTotalMgr) getBean("feeTotalMgr");
		List feeTotaList = feeTotalMgr.getFeeTotalsList(" where year = '"+year+"' order by areaId");
		FeeTotal feeTotal = null;
		if(feeTotaList.size()>0){
			for(int i = 0 ;city.size()>i;i++ ){
				TawSystemArea tawSystemArea =(TawSystemArea)city.get(i);  
				for(int j = 0 ;feeTotaList.size()>j;j++ ){
					feeTotal = (FeeTotal)feeTotaList.get(j);
					if(tawSystemArea.getAreaid().equals(feeTotal.getAreaId())){
						break;
					}
					if(feeTotaList.size()-1==j){
						showCityList.add(tawSystemArea);
					}
				}
			}
		}else{
			showCityList = city;
		}
    	request.setAttribute("city", showCityList);   
//    	request.setAttribute("city", city); 
    	IFeeTreeMgr feeTreeMgr = (IFeeTreeMgr) getBean("feeTreeMgr");
    	List feeTreeList = (List)feeTreeMgr.getChildNodes("1");
    	List feeDetailList = new ArrayList();
    	FeeTree feeTree = null;
    	FeeDetailForm feeDetailForm = null;
    	for(int i = 0;feeTreeList.size()>i;i++ ){
    		feeTree = (FeeTree)feeTreeList.get(i);
    		feeDetailForm = new FeeDetailForm();
    		feeDetailForm.setNodeId(feeTree.getNodeId());
    		feeDetailForm.setType(feeTree.getNodeType());
    		feeDetailForm.setUnit(feeTree.getUnit());
    		feeDetailForm.setPrice(String.valueOf(feeTree.getPrice()));
    		feeDetailForm.setNumber("");
    		feeDetailForm.setParentNodeId(feeTree.getParentNodeId());
    		feeDetailList.add(feeDetailForm);
    	}
    	request.setAttribute("feeDetailList", feeDetailList);
    	
    	updateFormBean(mapping, request, feeTotalForm);
		return mapping.findForward("edit");
	}
	
	/**
	 * 修改计算结果信息
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
		IFeeTotalMgr feeTotalMgr = (IFeeTotalMgr) getBean("feeTotalMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		FeeTotal feeTotal = feeTotalMgr.getFeeTotal(id);
		IFeeDetailMgr feeDetailMgr = (IFeeDetailMgr) getBean("feeDetailMgr");
		List feeDetaiList  = feeDetailMgr.getFeeDetailList(" where resultId = '"+feeTotal.getId()+"' order by nodeId");
		List feeDetailList = new ArrayList();
		FeeDetail feeDetail = null;
		FeeDetailForm feeDetailForm = null;
		for(int i = 0 ; feeDetaiList.size()>i;i++){
			feeDetail = (FeeDetail)feeDetaiList.get(i);
			feeDetailForm = new FeeDetailForm();
    		feeDetailForm.setNodeId(feeDetail.getNodeId());
    		feeDetailForm.setType(feeDetail.getType());
    		feeDetailForm.setUnit(feeDetail.getUnit());
    		feeDetailForm.setPrice(String.valueOf(feeDetail.getPrice()));
    		feeDetailForm.setNumber(String.valueOf(feeDetail.getNumber()));
    		feeDetailForm.setParentNodeId(feeDetail.getNodeId().substring(0,feeDetail.getNodeId().length()-2));
    		feeDetailForm.setTotal(String.valueOf(feeDetail.getTotal()));
    		feeDetailList.add(feeDetailForm);
		}
		request.setAttribute("feeDetailList", feeDetailList);
		FeeTotalForm feeTotalForm = (FeeTotalForm) convert(feeTotal);
		DecimalFormat df = new DecimalFormat("0.00");
		feeTotalForm.setTotalMoney(df.format(feeTotal.getTotalMoney()));
		feeTotalForm.setTotalreaMoney(df.format(feeTotal.getTotalreaMoney()));
		updateFormBean(mapping, request, feeTotalForm);
		return mapping.findForward("edit");
	}
	
	/**
	 * 保存计算结果信息
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
		IFeeTotalMgr feeTotalMgr = (IFeeTotalMgr) getBean("feeTotalMgr");
		FeeTotalForm feeTotalForm = (FeeTotalForm) form;
		boolean isNew = (null == feeTotalForm.getId() || "".equals(feeTotalForm.getId()));
		FeeTotal feeTotal = (FeeTotal) convert(feeTotalForm);
		if (isNew) {
//			保存
			feeTotal.setMonthMoney(FeeTotalConstants.getDouble2FourFive(feeTotal.getTotalMoney()/12.0f));
			feeTotal.setPointMoney(FeeTotalConstants.getDouble2FourFive(feeTotal.getMonthMoney()/200.0f));
			feeTotal.setQuarterMoney(FeeTotalConstants.getDouble2FourFive(feeTotal.getTotalMoney()/4.0f));
			feeTotal.setDeleted("0");
			feeTotalMgr.saveFeeTotal(feeTotal);
//			保存光缆线路付费信息
	    	IFeeTreeMgr feeTreeMgr = (IFeeTreeMgr) getBean("feeTreeMgr");
	    	List feeTreeList = (List)feeTreeMgr.getChildNodes("1");
	    	IFeeDetailMgr feeDetailMgr = (IFeeDetailMgr) getBean("feeDetailMgr");
	    	Map lineLongMap = new HashMap();
	    	FeeDetail feeDetail = null; 
	    	FeeTree feeTree = null;
	    	String number = "";
	    	String price = "";
	    	String total = "";
	    	String unit = "";
	    	String lineLongStr = "";
	    	for(int i = 0;feeTreeList.size()>i;i++ ){
	    		feeTree = (FeeTree)feeTreeList.get(i);
	    		if("lineName".equals(feeTree.getNodeType())){
					number = StaticMethod.null2String(request
							.getParameter("number_"+feeTree.getNodeId()));
					price = StaticMethod.null2String(request
							.getParameter("price_"+feeTree.getNodeId()));
					total = StaticMethod.null2String(request
							.getParameter("total_"+feeTree.getNodeId()));
					unit = StaticMethod.null2String(request
							.getParameter("unit_"+feeTree.getNodeId()));
					lineLongStr = StaticMethod.null2String(String.valueOf(lineLongMap.get(feeTree.getParentNodeId())));
					if("".equals(lineLongStr)||"null".equals(lineLongStr)){
						lineLongMap.put(feeTree.getParentNodeId(), number);
					}else{
						lineLongMap.put(feeTree.getParentNodeId(), String.valueOf(Double.parseDouble(lineLongStr)+Double.parseDouble(number)));
					}
					feeDetail = new FeeDetail();
					feeDetail.setAreaId(feeTotal.getAreaId());
					feeDetail.setDeleted("0");
					feeDetail.setNumber(Double.parseDouble(number));
					feeDetail.setPrice(Double.parseDouble(price));
					feeDetail.setNodeId(feeTree.getNodeId());
					feeDetail.setTotal(Double.parseDouble(total));
					feeDetail.setType(feeTree.getNodeType());
					feeDetail.setUnit(unit);
					feeDetail.setYear(feeTotal.getYear());
					feeDetail.setResultId(feeTotal.getId());
					feeDetailMgr.saveFeeDetail(feeDetail);
	    		}
	    	}
	    	for(int i = 0;feeTreeList.size()>i;i++ ){
	    		feeTree = (FeeTree)feeTreeList.get(i);
	    		if("sort".equals(feeTree.getNodeType())){
					number = StaticMethod.null2String(request
							.getParameter("number_"+feeTree.getNodeId()));
					price = StaticMethod.null2String(request
							.getParameter("price_"+feeTree.getNodeId()));
					total = StaticMethod.null2String(request
							.getParameter("total_"+feeTree.getNodeId()));
					unit = StaticMethod.null2String(request
							.getParameter("unit_"+feeTree.getNodeId()));
					feeDetail = new FeeDetail();
					feeDetail.setAreaId(feeTotal.getAreaId());
					feeDetail.setDeleted("0");
					feeDetail.setNodeId(feeTree.getNodeId());
					feeDetail.setTotal(Double.parseDouble(String.valueOf(lineLongMap.get(feeTree.getNodeId()))));
					feeDetail.setType(feeTree.getNodeType());
					feeDetail.setUnit(unit);
					feeDetail.setYear(feeTotal.getYear());
					feeDetail.setResultId(feeTotal.getId());
					feeDetailMgr.saveFeeDetail(feeDetail);
	    		}
	    	}	    	
		} else {
//			保存
			feeTotal.setMonthMoney(FeeTotalConstants.getDouble2FourFive(feeTotal.getTotalMoney()/12.0f));
			feeTotal.setPointMoney(FeeTotalConstants.getDouble2FourFive(feeTotal.getMonthMoney()/200.0f));
			feeTotal.setQuarterMoney(FeeTotalConstants.getDouble2FourFive(feeTotal.getTotalMoney()/4.0f));
			feeTotal.setDeleted("0");			
			feeTotalMgr.saveFeeTotal(feeTotal);
			
			IFeeDetailMgr feeDetailMgr = (IFeeDetailMgr) getBean("feeDetailMgr");
			List feeDetaiList  = feeDetailMgr.getFeeDetailList(" where resultId = '"+feeTotal.getId()+"' order by nodeId");	
	    	Map lineLongMap = new HashMap();
	    	FeeDetail feeDetail = null; 
	    	String number = "";
	    	String price = "";
	    	String total = "";
	    	String unit = "";
	    	String lineLongStr = "";
	    	String parentNodeId = "";
	    	for(int i = 0;feeDetaiList.size()>i;i++ ){
	    		feeDetail = (FeeDetail)feeDetaiList.get(i);
	    		if("lineName".equals(feeDetail.getType())){
					number = StaticMethod.null2String(request
							.getParameter("number_"+feeDetail.getNodeId()));
					price = StaticMethod.null2String(request
							.getParameter("price_"+feeDetail.getNodeId()));
					total = StaticMethod.null2String(request
							.getParameter("total_"+feeDetail.getNodeId()));
					unit = StaticMethod.null2String(request
							.getParameter("unit_"+feeDetail.getNodeId()));
					parentNodeId = feeDetail.getNodeId().substring(0, feeDetail.getNodeId().length()-2);
					lineLongStr = StaticMethod.null2String(String.valueOf(lineLongMap.get(feeDetail.getNodeId().substring(0, feeDetail.getNodeId().length()-2))));
					if("".equals(lineLongStr)||"null".equals(lineLongStr)){
						lineLongMap.put(parentNodeId, number);
					}else{
						lineLongMap.put(parentNodeId, String.valueOf(Double.parseDouble(lineLongStr)+Double.parseDouble(number)));
					}
					feeDetail.setAreaId(feeTotal.getAreaId());
					feeDetail.setDeleted("0");
					feeDetail.setNumber(Double.parseDouble(number));
					feeDetail.setPrice(Double.parseDouble(price));
					feeDetail.setNodeId(feeDetail.getNodeId());
					feeDetail.setTotal(Double.parseDouble(total));
					feeDetail.setType(feeDetail.getType());
					feeDetail.setUnit(unit);
					feeDetail.setYear(feeTotal.getYear());
					feeDetail.setResultId(feeTotal.getId());
					feeDetailMgr.saveFeeDetail(feeDetail);
	    		}
	    	}
	    	for(int i = 0;feeDetaiList.size()>i;i++ ){
	    		feeDetail = (FeeDetail)feeDetaiList.get(i);
	    		if("sort".equals(feeDetail.getType())){
					number = StaticMethod.null2String(request
							.getParameter("number_"+feeDetail.getNodeId()));
					price = StaticMethod.null2String(request
							.getParameter("price_"+feeDetail.getNodeId()));
					total = StaticMethod.null2String(request
							.getParameter("total_"+feeDetail.getNodeId()));
					unit = StaticMethod.null2String(request
							.getParameter("unit_"+feeDetail.getNodeId()));
					feeDetail.setAreaId(feeTotal.getAreaId());
					feeDetail.setDeleted("0");
					feeDetail.setNodeId(feeDetail.getNodeId());
					feeDetail.setTotal(Double.parseDouble(String.valueOf(lineLongMap.get(feeDetail.getNodeId()))));
					feeDetail.setType(feeDetail.getType());
					feeDetail.setUnit(unit);
					feeDetail.setYear(feeTotal.getYear());
					feeDetail.setResultId(feeTotal.getId());
					feeDetailMgr.saveFeeDetail(feeDetail);
	    		}
	    	}		    	
		}
		return mapping.findForward("success");
	}
	
	/**
	 * 删除计算结果信息
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
		IFeeTotalMgr feeTotalMgr = (IFeeTotalMgr) getBean("feeTotalMgr");
		String id = StaticMethod.null2String(request.getParameter("id"));
		feeTotalMgr.removeFeeTotal(id);
		return search(mapping, form, request, response);
	}
	
	/**
	 * 显示计算结果信息列表
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
		
		String year = StaticMethod.null2String(request.getParameter("year"));	
		
		FeeTotal feeTotal = null;
		Map feeDetailMap = new HashMap();
		
		IFeeTotalMgr feeTotalMgr = (IFeeTotalMgr) getBean("feeTotalMgr");
		List feeTotaList = feeTotalMgr.getFeeTotalsList(" where year = '"+year+"' order by areaId");
		
		IFeeDetailMgr feeDetailMgr = (IFeeDetailMgr) getBean("feeDetailMgr");
		List feeDetaiList = null;
		for(int i =0 ; feeTotaList.size()>i;i++){
			feeTotal = (FeeTotal)feeTotaList.get(i);
			feeDetaiList = feeDetailMgr.getFeeDetailList(" where resultId = '"+feeTotal.getId()+"' order by nodeId");
			feeDetailMap.put(feeTotal.getId(), feeDetaiList);
		}
		request.setAttribute("feeDetaiList", feeDetaiList);
		request.setAttribute("feeTotaList", feeTotaList);
		request.setAttribute("feeDetailMap", feeDetailMap);
		request.setAttribute("year", year);
		return mapping.findForward("list");
	}

	/**
	 * 根据年份选出未配置维护服务量的地市
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changeCity(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		JSONArray json = new JSONArray();
		JSONObject jitem = new JSONObject();

		String year = request.getParameter("year");
    	
//		得到当前地市	
		AssRoleIdList roleIdList = (AssRoleIdList)ApplicationContextHolder
		.getInstance().getBean("lineAssRoleIdList");
		String rootAreaId = roleIdList.getRootAreaId();
//		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
//		String province = pnrBaseAreaIdList.getRootAreaId();
    	List city = PartnerCityByUser.getCityByProvince(rootAreaId); 		
    	
    	List showCityList = new ArrayList();
    	
		IFeeTotalMgr feeTotalMgr = (IFeeTotalMgr) getBean("feeTotalMgr");
		List feeTotaList = feeTotalMgr.getFeeTotalsList(" where year = '"+year+"' order by areaId");
		FeeTotal feeTotal = null;
		if(feeTotaList.size()>0){
			for(int i = 0 ;city.size()>i;i++ ){
				TawSystemArea tawSystemArea =(TawSystemArea)city.get(i);
				for(int j = 0 ;feeTotaList.size()>j;j++ ){
					feeTotal = (FeeTotal)feeTotaList.get(j);
					if(tawSystemArea.getAreaid().equals(feeTotal.getAreaId())){
						break;
					}
					if(feeTotaList.size()-1==j){
						showCityList.add(tawSystemArea);
					}
				}
			}
		}else{
			showCityList = city;
		}

		StringBuffer area_list = new StringBuffer();
		area_list.append("," + "");
		area_list.append("," + "--请选择地市--");
		for(int i = 0; i < showCityList.size(); i++){
			TawSystemArea tawSystemArea =(TawSystemArea)showCityList.get(i);
			area_list.append("," + tawSystemArea.getAreaid());
			area_list.append("," + tawSystemArea.getAreaname());
		}
		String areaBuffer = area_list.toString();
		areaBuffer = areaBuffer.substring(1, areaBuffer.length());
		
		jitem.put("areaId", areaBuffer);
		json.put(jitem);
		response.setCharacterEncoding("utf-8");		
		response.getWriter().write(json.toString());

		return null;
	}
}