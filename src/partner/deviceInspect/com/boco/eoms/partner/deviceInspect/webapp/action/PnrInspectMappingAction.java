package com.boco.eoms.partner.deviceInspect.webapp.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.deviceManagement.common.utils.CommonConstants;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.partner.deviceInspect.model.PnrInspectLink;
import com.boco.eoms.partner.deviceInspect.model.PnrInspectMapping;
import com.boco.eoms.partner.deviceInspect.mp.NetResourceMapping;
import com.boco.eoms.partner.deviceInspect.service.PnrInspectLinkService;
import com.boco.eoms.partner.deviceInspect.service.PnrInspectMappingService;
import com.boco.eoms.partner.deviceInspect.util.SpecialtyMappingXMLUtil;
import com.boco.eoms.partner.deviceInspect.util.TableNameMappingNetres;
import com.boco.eoms.partner.res.mgr.PnrResConfigMgr;
import com.boco.eoms.partner.res.model.PnrResConfig;
import com.google.common.base.Strings;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

public class PnrInspectMappingAction extends BaseAction {

	/**
	 * 跳转到代维资源配置
	 */
	public ActionForward gotoPartnerDeviceDeploy(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		SpecialtyMappingXMLUtil util = new SpecialtyMappingXMLUtil();
		Map<String, List<NetResourceMapping>> map = util.loadMapping();
		Map<String, String> keyMap = util.loadSpecial();
		request.setAttribute("map", map);
		JSONArray json = JSONArray.fromObject(map);
		request.setAttribute("keyMap", keyMap);
		request.setAttribute("json", json);
		return mapping.findForward("partnerDeviceDeploy");
	}
	
	/**
	 * 代维资源配置保存
	 */
	public ActionForward savePartnerDeviceDeploy(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		PnrInspectMappingService pnrInspectMappingMgr = (PnrInspectMappingService) getBean("pnrInspectMappingService");
		
		String specialty = StaticMethod.nullObject2String(request.getParameter("specialty"));
		String inspectType = StaticMethod.nullObject2String(request.getParameter("inspectType"));
		String deviceSpecialty[] = request.getParameterValues("deviceSpecialty");
		String deviceType[] = request.getParameterValues("deviceType");
		String netresTableName[] = request.getParameterValues("netresTableName");
		String netresFieldName[] = request.getParameterValues("netresFieldName");
		String netresFieldValue[] = request.getParameterValues("netresFieldValue");
		String deviceTypeName[] = request.getParameterValues("deviceTypeName");
		String deviceSpecialtyName[] = request.getParameterValues("deviceSpecialtyName");
		
		for(int i=0;i<deviceSpecialty.length;i++){
			PnrInspectMapping inspectMapping = new PnrInspectMapping();
			inspectMapping.setSpecialty(specialty);
			inspectMapping.setDeviceType(deviceType[i]);
			inspectMapping.setDeviceTypeName(deviceTypeName[i]);
			inspectMapping.setDeviceSpecialtyName(deviceSpecialtyName[i]);
			inspectMapping.setInspectType(inspectType);
			inspectMapping.setNetresFieldName(netresFieldName[i]);
			inspectMapping.setNetresFieldValue(netresFieldValue[i]);
			inspectMapping.setNetresTableName(netresTableName[i]);
			inspectMapping.setDeviceSpecialty(deviceSpecialty[i]);
			pnrInspectMappingMgr.save(inspectMapping);
		}
		
		
		return mapping.findForward("success");
	}
	
	
	/**
	 * 跳转到代维资源配置
	 */
	public ActionForward gotoSelectDevice(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		PnrInspectMappingService pnrInspectMappingMgr = (PnrInspectMappingService) getBean("pnrInspectMappingService");
		
		SpecialtyMappingXMLUtil util = new SpecialtyMappingXMLUtil();
		Map<String, List<NetResourceMapping>> map = util.loadMapping();
		Map<String, String> keyMap = util.loadSpecial();
		request.setAttribute("map", map);
		JSONArray json = JSONArray.fromObject(map);
		request.setAttribute("keyMap", keyMap);
		request.setAttribute("json", json);
		String specialty = StaticMethod.nullObject2String(request.getParameter("specialty"));
		String resType = StaticMethod.nullObject2String(request.getParameter("resType"));
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(), search);
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		search.addFilterEqual("specialty", specialty);
		search.addFilterEqual("inspectType", resType);
		SearchResult<PnrInspectMapping> result = pnrInspectMappingMgr.searchAndCount(search);
		request.setAttribute("list",result.getResult());
		request.setAttribute("pageSize", CommonConstants.PAGE_SIZE);
		request.setAttribute("resultSize", result.getTotalCount());
		request.setAttribute("specialty", specialty);
		request.setAttribute("inspectType", resType);
		return mapping.findForward("gotoSelectDevice");
	}
	
	
	/**
	 * 代维资源配置list
	 */
	public ActionForward gotoInspectDeviceMapplist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		PnrInspectMappingService pnrInspectMappingMgr = (PnrInspectMappingService) getBean("pnrInspectMappingService");
		SpecialtyMappingXMLUtil util = new SpecialtyMappingXMLUtil();
		Map<String, List<NetResourceMapping>> map = util.loadMapping();
		Map<String, String> keyMap = util.loadSpecial();
		request.setAttribute("map", map);
		JSONArray json = JSONArray.fromObject(map);
		request.setAttribute("keyMap", keyMap);
		request.setAttribute("json", json);
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(), search);
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		SearchResult<PnrInspectMapping> result = pnrInspectMappingMgr.searchAndCount(search);
		request.setAttribute("list",result.getResult());
		request.setAttribute("pageSize", CommonConstants.PAGE_SIZE);
		request.setAttribute("resultSize", result.getTotalCount());
		return mapping.findForward("gotoInspectDeviceMapplist");
	}
	
	/**
	 * 删除代维资源配置
	 */
	public ActionForward deleteInspectDeviceMapplist(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		PnrInspectMappingService pnrInspectMappingMgr = (PnrInspectMappingService) getBean("pnrInspectMappingService");
		SpecialtyMappingXMLUtil util = new SpecialtyMappingXMLUtil();
		Map<String, List<NetResourceMapping>> map = util.loadMapping();
		Map<String, String> keyMap = util.loadSpecial();
		request.setAttribute("map", map);
		JSONArray json = JSONArray.fromObject(map);
		request.setAttribute("keyMap", keyMap);
		request.setAttribute("json", json);
		String id = StaticMethod.nullObject2String(request.getParameter("id"));
		String ids = StaticMethod.nullObject2String(request.getParameter("ids"));
		if(!"".equals(id)){
			pnrInspectMappingMgr.removeById(id);
		}else if(!"".equals(ids)){
			String idsarr [] = ids.split("\\|");
			pnrInspectMappingMgr.removeByIds(idsarr);
		}
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(), search);
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		SearchResult<PnrInspectMapping> result = pnrInspectMappingMgr.searchAndCount(search);
		request.setAttribute("list",result.getResult());
		request.setAttribute("pageSize", CommonConstants.PAGE_SIZE);
		request.setAttribute("resultSize", result.getTotalCount());
		return mapping.findForward("gotoInspectDeviceMapplist");
	}
	
	/**
	 * 删除代维资源配置
	 */
	public ActionForward gotoeditInspectDeviceMapping(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		PnrInspectMappingService pnrInspectMappingMgr = (PnrInspectMappingService) getBean("pnrInspectMappingService");
		SpecialtyMappingXMLUtil util = new SpecialtyMappingXMLUtil();
		Map<String, List<NetResourceMapping>> map = util.loadMapping();
		Map<String, String> keyMap = util.loadSpecial();
		request.setAttribute("map", map);
		JSONArray json = JSONArray.fromObject(map);
		request.setAttribute("keyMap", keyMap);
		request.setAttribute("json", json);
		String id = StaticMethod.nullObject2String(request.getParameter("id"));
		PnrInspectMapping pnrInspectMapping = pnrInspectMappingMgr.find(id);
		request.setAttribute("pnrInspectMapping", pnrInspectMapping);
		return mapping.findForward("editInspectDeviceMapping");
	}
	
	/**
	 * 删除代维资源配置
	 */
	public ActionForward editInspectDeviceMapping(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		PnrInspectMappingService pnrInspectMappingMgr = (PnrInspectMappingService) getBean("pnrInspectMappingService");
		
		String id = StaticMethod.nullObject2String(request.getParameter("id"));
		String specialty = StaticMethod.nullObject2String(request.getParameter("specialty"));
		String inspectType = StaticMethod.nullObject2String(request.getParameter("inspectType"));
		String deviceSpecialty = StaticMethod.nullObject2String(request.getParameter("deviceSpecialty"));
		String deviceType = StaticMethod.nullObject2String(request.getParameter("deviceType"));
		String netresTableName = StaticMethod.nullObject2String(request.getParameter("netresTableName"));
		String netresFieldName = StaticMethod.nullObject2String(request.getParameter("netresFieldName"));
		String netresFieldValue = StaticMethod.nullObject2String(request.getParameter("netresFieldValue"));
		String deviceSpecialtyName = StaticMethod.nullObject2String(request.getParameter("deviceSpecialtyName"));
		String deviceTypeName = StaticMethod.nullObject2String(request.getParameter("deviceTypeName"));
		PnrInspectMapping pnrInspectMapping = new PnrInspectMapping();
		pnrInspectMapping.setId(id);
		pnrInspectMapping.setSpecialty(specialty);
		pnrInspectMapping.setInspectType(inspectType);
		pnrInspectMapping.setDeviceSpecialty(deviceSpecialty);
		pnrInspectMapping.setDeviceType(deviceType);
		pnrInspectMapping.setNetresFieldName(netresFieldName);
		pnrInspectMapping.setNetresFieldValue(netresFieldValue);
		pnrInspectMapping.setNetresTableName(netresTableName);
		pnrInspectMapping.setDeviceSpecialtyName(deviceSpecialtyName);
		pnrInspectMapping.setDeviceTypeName(deviceTypeName);
		pnrInspectMappingMgr.save(pnrInspectMapping);
		SpecialtyMappingXMLUtil util = new SpecialtyMappingXMLUtil();
		Map<String, List<NetResourceMapping>> map = util.loadMapping();
		Map<String, String> keyMap = util.loadSpecial();
		request.setAttribute("map", map);
		JSONArray json = JSONArray.fromObject(map);
		request.setAttribute("keyMap", keyMap);
		request.setAttribute("json", json);
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(), search);
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		SearchResult<PnrInspectMapping> result = pnrInspectMappingMgr.searchAndCount(search);
		request.setAttribute("list",result.getResult());
		request.setAttribute("pageSize", CommonConstants.PAGE_SIZE);
		request.setAttribute("resultSize", result.getTotalCount());
		return mapping.findForward("gotoInspectDeviceMapplist");
	}
	
	/**
	 * 代维资源已关联网络资源的列表
	 * */
	public ActionForward findRelationNetResourceList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		PnrInspectLinkService pnrInspectlinkMgr = (PnrInspectLinkService) getBean("pnrInspectLinkService");
		PnrResConfigMgr pnrResConfiMgr = (PnrResConfigMgr) getBean("PnrResConfigMgr");
		String id = StaticMethod.nullObject2String(request.getParameter("id"));
		PnrResConfig pnrResConfig = pnrResConfiMgr.find(id);
		String specialty = StaticMethod.nullObject2String(request.getParameter("specialty"));
		String resType = StaticMethod.nullObject2String(request.getParameter("resType"));
		
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(), search);
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		search.addFilterEqual("inspectId", id);
		SearchResult<PnrInspectLink> result = pnrInspectlinkMgr.searchAndCount(search);
		
		request.setAttribute("pageSize", CommonConstants.PAGE_SIZE);
		request.setAttribute("list", result.getResult());
		request.setAttribute("size", result.getTotalCount());
		
		request.setAttribute("id", id);
		request.setAttribute("specialty", specialty);
		request.setAttribute("resType", resType);
		request.setAttribute("pnrResConfig", pnrResConfig);
		return mapping.findForward("relationNetResourceList");
	}
	
	/**
	 * 代维资源可以关联的网络资源
	 * */
	public ActionForward waitRelationInspectDeviceList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		PnrInspectMappingService pnrInspectMappingMgr = (PnrInspectMappingService) getBean("pnrInspectMappingService");
		String resid = StaticMethod.nullObject2String(request.getParameter("resid"));
		String mappingid = StaticMethod.nullObject2String(request.getParameter("mappingid"));
		String tableName = StaticMethod.nullObject2String(request.getParameter("tableName"));
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(request,"list");
		final int pageIndex = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		//1.先判断是否要根据某个字段来筛选网络资源
		PnrInspectMapping pnrInspectMapping = pnrInspectMappingMgr.find(mappingid);
		System.out.println("tableName="+tableName);
		String filedName = "";
		String whereStr = "";
		TableNameMappingNetres table = new TableNameMappingNetres();
		Map<String, String> map = table.loadMapping();
		filedName = map.get(tableName);
		//whereStr += " where (inspect_id ='' or inspect_id is null)";
		if((!StringUtils.isEmpty(pnrInspectMapping.getNetresFieldName())) || "N/A".equals(pnrInspectMapping.getNetresFieldName())){
			//whereStr += " and "+pnrInspectMapping.getNetresFieldName()+" ='"+pnrInspectMapping.getNetresFieldValue()+"'";
			whereStr +=" where "+ pnrInspectMapping.getNetresFieldName()+" ='"+pnrInspectMapping.getNetresFieldValue()+"' and INSPECT_ID is null";
		}
		List<Map<String, Object>> list = pnrInspectMappingMgr.getAllDevice(pageIndex*CommonConstants.PAGE_SIZE, CommonConstants.PAGE_SIZE,tableName, filedName, whereStr);
		request.setAttribute("list", list.get(1));
		request.setAttribute("resultSize", list.get(0));
		request.setAttribute("pageSize", CommonConstants.PAGE_SIZE);
		request.setAttribute("deviceSpecialty", pnrInspectMapping.getDeviceSpecialtyName());
		request.setAttribute("pnrInspectMapping", pnrInspectMapping);
		request.setAttribute("resid", resid);
		return mapping.findForward("waitRelationInspectDeviceList");
	}
	/**
	 * 代维资源可以关联的网络资源
	 * */
	public ActionForward waitRelationInspectDeviceTypeList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		PnrInspectMappingService pnrInspectMappingMgr = (PnrInspectMappingService) getBean("pnrInspectMappingService");
		String id = StaticMethod.nullObject2String(request.getParameter("id"));
		String specialty = StaticMethod.nullObject2String(request.getParameter("specialty"));
		String resType = StaticMethod.nullObject2String(request.getParameter("resType"));
		//1.获得所有设备类型
		String whereStr = " where specialty='"+specialty+"' and inspect_type='"+resType+"'";
		List<Map<String, String>> list = pnrInspectMappingMgr.getAllDeviceTypeName(whereStr);
		
		request.setAttribute("deviceTypeList", list);
		request.setAttribute("id", id);
		request.setAttribute("specialty", specialty);
		request.setAttribute("resType", resType);
		return mapping.findForward("waitRelationInspectDeviceTypeList");
	}
	/**
	 * 代维资源关联网络资源
	 * */
	public ActionForward relationInspectDevice(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		PnrInspectLinkService pnrInspectlinkMgr = (PnrInspectLinkService) getBean("pnrInspectLinkService");
		PnrInspectMappingService pnrInspectMappingMgr = (PnrInspectMappingService) getBean("pnrInspectMappingService");
		PnrResConfigMgr pnrResConfiMgr = (PnrResConfigMgr) getBean("PnrResConfigMgr");
		String id = StaticMethod.null2String(request.getParameter("seldelcar"));
		String resid = request.getParameter("res");
		String mappingId = request.getParameter("mappingId");
		PnrInspectMapping pnrInspectMapping = pnrInspectMappingMgr.find(mappingId);
		PnrResConfig pnrResConfig = pnrResConfiMgr.find(resid);
		String[] ids = id.split("\\|");
		System.out.println(id);
		String idStr = "";
		for(int i=0;i<ids.length;i++){
			PnrInspectLink pnrInspectLink = new PnrInspectLink();
			pnrInspectLink.setInspectId(resid);
			pnrInspectLink.setInspectMappingId(mappingId);
			String[] netres = ids[i].split("~");
			pnrInspectLink.setNetResId(netres[0]);
			pnrInspectLink.setDeviceSpecialtyName(pnrInspectMapping.getDeviceSpecialtyName());
			pnrInspectLink.setDeviceTypeName(pnrInspectMapping.getDeviceTypeName());
			pnrInspectLink.setNetresTableName(pnrInspectMapping.getNetresTableName());
			pnrInspectLink.setNetresName(netres[1]);
			pnrInspectLink.setNetresFieldName(netres[2].trim());
			pnrInspectLink.setNetresFieldValue(netres[3].trim());
			idStr += "'"+netres[0]+"',";
			pnrInspectlinkMgr.save(pnrInspectLink);
		}
		//跟新当前网络资源已经关联到代维资源
		idStr = idStr.substring(0, idStr.length()-1);
		pnrInspectMappingMgr.updateNetres(pnrInspectMapping.getNetresTableName(), resid, pnrResConfig.getResName(), idStr);
		ActionForward actionForward = new ActionForward();
		actionForward.setPath("/inspectMapping.do?method=waitRelationInspectDeviceList&resid="+resid+"&mappingid="+mappingId+"&tableName="+pnrInspectMapping.getNetresTableName());
		actionForward.setRedirect(true);
		return actionForward;
	}
	
	/**
	 * 代维资源可以关联的网络资源
	 * */
	public ActionForward calncleRelationInspectDevice(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		
		PnrInspectMappingService pnrInspectMappingMgr = (PnrInspectMappingService) getBean("pnrInspectMappingService");
		String id = StaticMethod.null2String(request.getParameter("seldelcar"));
		String[] ids = id.split("\\|");
		System.out.println(id);
		String resid = StaticMethod.nullObject2String(request.getParameter("id"));
		String specialty = StaticMethod.nullObject2String(request.getParameter("specialty"));
		String resType = StaticMethod.nullObject2String(request.getParameter("resType"));
		Map<String, String> map = new HashMap<String, String>();
		String idStr = "";
		for(int i = 0;i<ids.length;i++){
			String[] deviceMessage = ids[i].split(",");
			idStr += "'"+deviceMessage[0]+"',";
			map.put(deviceMessage[1], deviceMessage[2]);
		}
		idStr = idStr.substring(0,idStr.length()-1);
		pnrInspectMappingMgr.cancleRelationInspectDevice(map, idStr);
		ActionForward actionForward = new ActionForward();
		actionForward.setPath("/inspectMapping.do?method=findRelationNetResourceList&id="+resid+"&specialty="+specialty+"&resType="+resType);
		actionForward.setRedirect(true);
		return actionForward;
	}
}
