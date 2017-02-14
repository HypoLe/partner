package com.boco.eoms.partner.assess.AssWeight.webapp.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.util.UtilMgrLocator;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.area.model.TawSystemArea;
import com.boco.eoms.partner.assess.AssRight.util.AssRoleIdList;
import com.boco.eoms.partner.assess.AssTree.mgr.IAssTreeMgr;
import com.boco.eoms.partner.assess.AssTree.model.AssTree;
import com.boco.eoms.partner.assess.AssWeight.mgr.ICityWeightMgr;
import com.boco.eoms.partner.assess.AssWeight.model.CityWeight;
import com.boco.eoms.partner.assess.AssWeight.util.CityWeightConstants;
import com.boco.eoms.partner.baseinfo.util.PartnerCityByUser;

/**
 * <p>
 * Title:地市配置权重
 * </p>
 * <p>
 * Description:地市配置权重
 * </p>
 * <p>
 * Tue Feb 22 15:42:16 CST 2011
 * </p>
 * 
 * @moudle.getAuthor() benweiwei
 * @moudle.getVersion() 1.0
 * 
 */
public abstract class CityWeightAction extends BaseAction {
	
	protected String beenNameForCityWeightMgr = "";
	protected String beenNameForRoleIdList = "";
	protected String beenNameForTreeMgr = "";
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
	 * 新增地市配置权重
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
    	String nodeId = StaticMethod.null2String(request.getParameter("nodeId"));
//		得到当前地市	
		AssRoleIdList roleIdList = (AssRoleIdList)ApplicationContextHolder
		.getInstance().getBean(beenNameForRoleIdList);
		String rootAreaId = roleIdList.getRootAreaId();
    	List cityList = PartnerCityByUser.getCityByProvince(rootAreaId); 
    	request.setAttribute("cityList", cityList);
    	request.setAttribute("templateTreeId", nodeId);
		return mapping.findForward("edit");
	}
	
	/**
	 * 修改地市配置权重
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
		ICityWeightMgr cityWeightMgr = (ICityWeightMgr) getBean(beenNameForCityWeightMgr);
		IAssTreeMgr assTreeMgr = (IAssTreeMgr) getBean(beenNameForTreeMgr);
		String nodeId = StaticMethod.null2String(request.getParameter("nodeId"));
		AssTree assTree = assTreeMgr.getTreeNodeByNodeId(nodeId);
		
//		得到当前地市	
		AssRoleIdList roleIdList = (AssRoleIdList)ApplicationContextHolder
		.getInstance().getBean(beenNameForRoleIdList);
		String rootAreaId = roleIdList.getRootAreaId();
    	List cityList = PartnerCityByUser.getCityByProvince(rootAreaId); 
    	request.setAttribute("cityList", cityList);
    	List cityWeightList = cityWeightMgr.getCityWeights(" and templateTreeId = '"+assTree.getId()+"'");
    	Map map = new HashMap();
    	CityWeight cityWeight = null ;
    	String userId = this.getUser(request).getUserid();
    	
    	if(cityWeightList.size()>0){
    		for(int i = 0;cityWeightList.size()>i;i++){
    			cityWeight = (CityWeight)cityWeightList.get(i);
    			map.put(cityWeight.getCity()+"_weight", cityWeight.getWeight());
    			map.put(cityWeight.getCity()+"_id", cityWeight.getId());
    		}
    		request.setAttribute("createTime",cityWeight.getCreateTime());
    		request.setAttribute("creator",userId);
    		request.setAttribute("sumWeight","0");
    	}else{
    		request.setAttribute("createTime",StaticMethod.getLocalString());
    		request.setAttribute("creator",userId);
    		request.setAttribute("sumWeight","100");
    	}

    	request.setAttribute("map", map);
    	request.setAttribute("templateTreeId", assTree.getId());
    	request.setAttribute("deleted","0");
    	
		return mapping.findForward("edit");
	}
	
	/**
	 * 保存地市配置权重
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
		ICityWeightMgr cityWeightMgr = (ICityWeightMgr) getBean(beenNameForCityWeightMgr);
		CityWeight cityWeight = null;
//		CityWeight cityWeight1 = null;
		String creator = StaticMethod.null2String(request.getParameter("creator"));
		String createTime = StaticMethod.null2String(request.getParameter("createTime"));
		String templateTreeId = StaticMethod.null2String(request.getParameter("templateTreeId"));
		String deleted = StaticMethod.null2String(request.getParameter("deleted"));
		String userId = this.getUser(request).getUserid();
//		得到当前地市	
		AssRoleIdList roleIdList = (AssRoleIdList)ApplicationContextHolder
		.getInstance().getBean(beenNameForRoleIdList);
		String rootAreaId = roleIdList.getRootAreaId();
    	List cityList = PartnerCityByUser.getCityByProvince(rootAreaId); 
    	String city = "";
    	String weight = "";
    	String id = "";
    	for(int i = 0;cityList.size()>i;i++){
    		TawSystemArea tawSystemArea = (TawSystemArea)cityList.get(i);
    		city = tawSystemArea.getAreaid();
    		weight = StaticMethod.nullObject2String(request.getParameter(city+"_weight"),"0");
    		id = StaticMethod.nullObject2String(request.getParameter(city+"_id"),"");
    		if("".equals(id)){
        		cityWeight = new CityWeight();
        		cityWeight.setCreator(creator);
        		cityWeight.setCreateTime(createTime);
        		cityWeight.setTemplateTreeId(templateTreeId);
        		cityWeight.setDeleted(deleted);
        		cityWeight.setCity(city);
        		cityWeight.setWeight(Float.valueOf(weight));
        		cityWeightMgr.saveCityWeight(cityWeight);
    		}else{
//    			暂时只做修改不做伪删除
    			cityWeight = (CityWeight)cityWeightMgr.getCityWeight(id);
    			cityWeight.setCreateTime(StaticMethod.getLocalString());
    			cityWeight.setCreator(userId);
    			cityWeight.setWeight(Float.valueOf(weight));
    			cityWeightMgr.saveCityWeight(cityWeight);
//    			cityWeight = (CityWeight)cityWeightMgr.getCityWeight(id);
//    			cityWeight.setDeleted("1");
//    			cityWeightMgr.saveCityWeight(cityWeight);
//    			cityWeight1 = new CityWeight();
//        		cityWeight1.setCreator(creator);
//        		cityWeight1.setCreateTime(createTime);
//        		cityWeight1.setTemplateTreeId(templateTreeId);
//        		cityWeight1.setDeleted(deleted);
//        		cityWeight1.setCity(city);
//        		cityWeight1.setWeight(Float.valueOf(weight));
//        		cityWeightMgr.saveCityWeight(cityWeight);
    		}
    	}
		return mapping.findForward("success");
	}
	
	/**
	 * 删除地市配置权重
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
		ICityWeightMgr cityWeightMgr = (ICityWeightMgr) getBean(beenNameForCityWeightMgr);
		String id = StaticMethod.null2String(request.getParameter("id"));
		cityWeightMgr.removeCityWeight(id);
		return search(mapping, form, request, response);
	}
	
	/**
	 * 分页显示地市配置权重列表
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
				CityWeightConstants.CITYWEIGHT_LIST)
				.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE);
		final Integer pageSize = UtilMgrLocator.getEOMSAttributes()
				.getPageSize();
		final Integer pageIndex = Integer.valueOf(GenericValidator
				.isBlankOrNull(request.getParameter(pageIndexName)) ? 0
				: (Integer.parseInt(request.getParameter(pageIndexName)) - 1));
		ICityWeightMgr cityWeightMgr = (ICityWeightMgr) getBean(beenNameForCityWeightMgr);
		Map map = (Map) cityWeightMgr.getCityWeights(pageIndex, pageSize, "");
		List list = (List) map.get("result");
		request.setAttribute(CityWeightConstants.CITYWEIGHT_LIST, list);
		request.setAttribute("resultSize", map.get("total"));
		request.setAttribute("pageSize", pageSize);
		return mapping.findForward("list");
	}
	
}