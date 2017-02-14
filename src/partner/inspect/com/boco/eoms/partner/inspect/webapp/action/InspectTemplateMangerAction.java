package com.boco.eoms.partner.inspect.webapp.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.dict.model.TawSystemDictType;
import com.boco.eoms.commons.system.dict.service.ID2NameService;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.ui.util.UIConstants;
import com.boco.eoms.deviceManagement.common.utils.CommonConstants;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.mobile.util.MobileConstants;
import com.boco.eoms.partner.inspect.mgr.IInspectTemplateBigItemMgr;
import com.boco.eoms.partner.inspect.mgr.IInspectTemplateItemMgr;
import com.boco.eoms.partner.inspect.mgr.IInspectTemplateMgr;
import com.boco.eoms.partner.inspect.model.InspectTemplate;
import com.boco.eoms.partner.inspect.model.InspectTemplateBigItem;
import com.boco.eoms.partner.inspect.model.InspectTemplateItem;
import com.boco.eoms.partner.inspect.util.MapToBeanUtil;
import com.google.common.base.Strings;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

/**
 * 
 * Description: 巡检计划模版 Copyright: Copyright (c)2009 Company: boco
 * 
 * @author: lee
 * @version: 1.0 Create at: Jul 11, 2012 3:59:53 PM
 */
public class InspectTemplateMangerAction extends BaseAction {

	public IInspectTemplateMgr getTemplateBean() {
		return (IInspectTemplateMgr) getBean("inspectTemplateMgr");
	}
	
	public IInspectTemplateBigItemMgr getTemplateBigItemBean() {
		return (IInspectTemplateBigItemMgr) getBean("inspectTemplateBigItemMgr");
	}

	public IInspectTemplateItemMgr getTemplateItemBean() {
		return (IInspectTemplateItemMgr) getBean("inspectTemplateItemMgr");
	}

	/**
	 * 
	 * Description: 跳转到巡检模版显示列表页面,模版管理
	 * 
	 * @Title: goToAddTemplate
	 * @param
	 * @param mapping
	 * @param
	 * @param form
	 * @param
	 * @param request
	 * @param
	 * @param response
	 * @param
	 * @return
	 * @param
	 * @throws Exception
	 * @return ActionForward
	 * @throws
	 */
	public ActionForward goToTemplatesList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Search search = new Search();

		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(),
				search);
		search.addFilterEqual("status", 1);
		String pageIndexString = request.getParameter((new org.displaytag.util.ParamEncoder("inspectTemplateList").encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		search.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);

		SearchResult<InspectTemplate> result = getTemplateBean()
				.searchAndCount(search);
		List<InspectTemplate> inspectTemplateList = result.getResult();
		if (inspectTemplateList.isEmpty()) {
			request.setAttribute("resultSize", 0);
		} else {
			request.setAttribute("inspectTemplateList", inspectTemplateList);
			request.setAttribute("resultSize", result.getTotalCount());
		}

		request.setAttribute("pageSize", CommonConstants.PAGE_SIZE);
		return mapping.findForward("goToTemplatesList");
	}

	/**
	 * 
	 * Description: 跳转到模版新增页面
	 * 
	 * @Title: goToAddTemplate
	 * @param
	 * @param mapping
	 * @param
	 * @param form
	 * @param
	 * @param request
	 * @param
	 * @param response
	 * @param
	 * @return
	 * @param
	 * @throws Exception
	 * @return ActionForward
	 * @throws
	 */
	public ActionForward goToAddTemplate(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String id = StaticMethod.nullObject2String(request.getParameter("id"));
		if (null != id && !"".equals(id)) {
			Search search = new Search();
			search.addFilterEqual("id", id);
			List<InspectTemplate> inspectTemplate = getTemplateBean()
					.searchAndCount(search).getResult();
			request.setAttribute("inspectTemplate", inspectTemplate.get(0));
			request.setAttribute("type", "detail");
			return mapping.findForward("goToEditTemplate");
		}
		request.setAttribute("type", "new");
		request.setAttribute("edit", "edit");
		return mapping.findForward("goToEditTemplate");
	}

	/**
	 * 
	 * Description: 保存模版数据
	 * 
	 * @Title: addTemplate
	 * @param
	 * @param mapping
	 * @param
	 * @param form
	 * @param
	 * @param request
	 * @param
	 * @param response
	 * @param
	 * @throws Exception
	 * @return void
	 * @throws
	 */
	public ActionForward editTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");
		boolean successFlag = true;
		HashMap map = (HashMap) MapToBeanUtil.getParameterMap(request);
		InspectTemplate inspectTemplate = (InspectTemplate) MapToBeanUtil
				.setValueToObj(InspectTemplate.class, map);

		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		inspectTemplate.setAddTime(StaticMethod.getCurrentDateTime());
		inspectTemplate.setAddUser(getUserId(request));
		inspectTemplate.setDept(getUser(request).getDeptid());
		inspectTemplate.setDept(sessionForm.getDeptid());
		String id = request.getParameter("id");
		Integer templateItemCount = 0;   //模板项数目
		Search search = new Search();
		search.addFilterEqual("status", 1);
		if(!"".equals(id.trim())){
			inspectTemplate.setId(id);
			search.addFilterEqual("id",id);
		}
		
		IInspectTemplateMgr service = getTemplateBean();
		search.addFilterEqual("resType", inspectTemplate.getResType());
		SearchResult<InspectTemplate> searchResult = service.searchAndCount(search);
		if(null != searchResult && searchResult.getTotalCount()>0&&"".equals(id.trim())){
			request.setAttribute("msg", "该专业数据已添加");
			
			return mapping.findForward("failure");
//			response.getWriter().write("[{\"success\":\"false\",\"msg\":\"该专业数据已添加\"}]");
		}else if(null != searchResult && searchResult.getTotalCount()>0&&!"".equals(id.trim())){
			//id不为空，表示当前是修改模板，如果在当前资源类型和对应的id下没有查出数据，表示专业类型也修改（后来修改为专业类型不能修改）
			Search search1 = new Search();
			search1.addFilterEqual("status", 1);
			search1.addFilterEqual("resType", inspectTemplate.getResType());
			SearchResult<InspectTemplate> searchResult1 = service.searchAndCount(search1);
			templateItemCount = searchResult1.getResult().get(0).getItemNum();
//			if(null != searchResult1 && searchResult1.getTotalCount()>0){
//				request.setAttribute("msg", "该专业数据已添加,不能修改为该专业！");
//				
//				return mapping.findForward("failure");
//			}
		}
//		if(inspectTemplate.getResType())
		try {
			inspectTemplate.setStatus(1);
			inspectTemplate.setItemNum(templateItemCount);
			service.save(inspectTemplate);
		} catch (Exception e) {
			e.printStackTrace();
			successFlag = false;
		}
//		if (successFlag) {
//			response.getWriter().write(InspectPlanConstants.successStr);
//		} else {
//			response.getWriter().write(InspectPlanConstants.failureStr);
//		}
		
		Search search1 = new Search();

		search1 = CommonUtils.getSqlFromRequestMap(request.getParameterMap(),
				search1);
		search1.addFilterEqual("status", 1);
		String pageIndexString = request.getParameter((new org.displaytag.util.ParamEncoder("inspectTemplateList").encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		search1.setMaxResults(CommonConstants.PAGE_SIZE);
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer.valueOf(pageIndexString).intValue() - 1;
		search1.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);

		SearchResult<InspectTemplate> result = getTemplateBean()
				.searchAndCount(search1);
		List<InspectTemplate> inspectTemplateList = result.getResult();
		if (inspectTemplateList.isEmpty()) {
			request.setAttribute("resultSize", 0);
		} else {
			request.setAttribute("inspectTemplateList", inspectTemplateList);
			request.setAttribute("resultSize", result.getTotalCount());
		}

		request.setAttribute("pageSize", CommonConstants.PAGE_SIZE);
		
		return mapping.findForward("goToTemplatesList");
	}

	/**
	 * 删除模版
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public ActionForward deleteTemplate(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		boolean successFlag = true;
		String id = StaticMethod.nullObject2String(request.getParameter("id"));
		Search search;
		if (id != null && !"".equals(id)) {
			search = new Search();
			search.addFilterEqual("id", id);
			
			try {
//				getTemplateBean().remove(
//						getTemplateBean().searchAndCount(search).getResult()
//								.get(0));
				
				InspectTemplate inspectTemplate = getTemplateBean().find(id);
				inspectTemplate.setStatus(0);
				getTemplateBean().save(inspectTemplate);
				
				//当改变了模板的状态的时候，还得改变模板项的状态
//				Search search1 = new Search();
//				search1 = CommonUtils.getSqlFromRequestMap(request.getParameterMap(),
//						search1);
//				search1.addFilterEqual("templateId", id);
//				search1.addFilterEqual("status", 1);
//				SearchResult<InspectTemplateItem> result = getTemplateItemBean()
//				.searchAndCount(search1);
//				List<InspectTemplateItem> inspectTemplateItemlist = result.getResult();
//				for(int i=0;i<inspectTemplateItemlist.size();i++){
//					InspectTemplateItem inspectTemplateItem = inspectTemplateItemlist.get(i);
//					inspectTemplateItem.setStatus(0);
//					getTemplateItemBean().save(inspectTemplateItem);
//				}
				getTemplateItemBean().updateInspectTemplateItem(id);
				
				
			} catch (Exception e) {
				e.printStackTrace();
				successFlag = false;
			}
		}
		// MapToBeanUtil.setValueToObj(cls, map)
		if (successFlag) {
			//response.getWriter().write(InspectPlanConstants.successStr);
			return mapping.findForward("success");
		} else {
			//response.getWriter().write(InspectPlanConstants.failureStr);
			return mapping.findForward("fail");
		}
//		ActionForward actionForward = new ActionForward();
//		actionForward.setPath("/inspectTemplateManger.do?method=goToTemplatesList");
//		actionForward.setRedirect(true);
//		return actionForward;
	}

	// ----------------------------------模版项操作---------------------------------------------

	/**
	 * 
	 * Description: 保存模版项数据
	 * 
	 * @Title: addTemplate
	 * @param
	 * @param mapping
	 * @param
	 * @param form
	 * @param
	 * @param request
	 * @param
	 * @param response
	 * @param
	 * @throws Exception
	 * @return void
	 * @throws
	 */
	public ActionForward editTemplateItem(ActionMapping mapping, ActionForm form,
			 HttpServletRequest request, HttpServletResponse response)
	 throws Exception {
		//保存模板
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		Date date1 = new Date();
		SimpleDateFormat simpledate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String date = simpledate.format(date1);
		String templateId = StaticMethod.nullObject2String(request.getParameter("templateId"));
		//用来判断是不是网络设备
		String netDevice = StaticMethod.nullObject2String(request.getParameter("netDevice"));
		String minValues[] = request.getParameterValues("minValue");
		String maxValues[] = request.getParameterValues("maxValue");
		String bigitemNames[] = request.getParameterValues("bigitemName");
		String inspectItems[] = request.getParameterValues("inspectItem");
		String inspectContents[] = request.getParameterValues("inspectContent");
		String inputTypes[] = request.getParameterValues("inputType2");
		String dictIds[] = request.getParameterValues("dictId"); 
		String normalRanges[] = request.getParameterValues("normalRange");
		String defaultValues[] = request.getParameterValues("defaultValue");
		String cycles[] = request.getParameterValues("cycle");
		String deviceId[] = request.getParameterValues("deviceId");
		String pictureNums[] = request.getParameterValues("pictureNum");
		
		Map<String, Integer> numMap = new HashMap<String, Integer>();
		for(int i=0;i<bigitemNames.length;i++){
			if(numMap.containsKey(bigitemNames[i])){
				int number = numMap.get(bigitemNames[i]);
				numMap.put(bigitemNames[i], number+1);
			}else{
				numMap.put(bigitemNames[i], 1);
			}
		}
		//1.查询出当前模板
		InspectTemplate inspectTemplate = getTemplateBean().find(templateId);
		
		//2.查询原来的模板大项，返回已有的模板大项的信息
		Map<String, String> nowBigItem = new HashMap<String, String>();
		Map<String, String> bigItemMap = getTemplateBigItemBean().findTemplateBigItems(numMap, templateId);
		//把numMap里面的建放到一个List里面
		List<String> numList = new ArrayList<String>();
		Iterator<String> it = numMap.keySet().iterator();
		while(it.hasNext()){
			String itemName = it.next();
			numList.add(itemName);
		}
		
		for(int i=0;i<numMap.size();i++){
			if(bigItemMap.containsKey(numList.get(i))){  //此时数据库中已经有了这大项，只需跟新小项的数目
				String bigItemId = bigItemMap.get(numList.get(i));
				InspectTemplateBigItem inspectTemplateBigItem = getTemplateBigItemBean().find(bigItemId);
				int itemNum = inspectTemplateBigItem.getItemNum()+numMap.get(numList.get(i));
				inspectTemplateBigItem.setItemNum(itemNum);
				getTemplateBigItemBean().save(inspectTemplateBigItem);
				nowBigItem.put(numList.get(i), bigItemId);
			}else{
				InspectTemplateBigItem inspectTemplateBigItem = new InspectTemplateBigItem();
				inspectTemplateBigItem.setTemplateId(templateId);
				inspectTemplateBigItem.setName(numList.get(i));
				inspectTemplateBigItem.setStatus(1);
				inspectTemplateBigItem.setItemNum(numMap.get(numList.get(i)));
				getTemplateBigItemBean().save(inspectTemplateBigItem);
				nowBigItem.put(numList.get(i), inspectTemplateBigItem.getId());
			}
		}
		
		//3.保存模板小项
		int numType =0 ;
		int numDict = 0;
		int nummultiple = 0; 
		int testNum = 0;
		for(int i=0;i<bigitemNames.length;i++){
			InspectTemplateItem inspectTemplateItem = new InspectTemplateItem();
			inspectTemplateItem.setAddUser(sessionForm.getUserid());
			inspectTemplateItem.setAddTime(date);
			inspectTemplateItem.setBigitemName(bigitemNames[i]);
			inspectTemplateItem.setBigitemId(nowBigItem.get(bigitemNames[i]));
			inspectTemplateItem.setInspectItem(inspectItems[i]);
			inspectTemplateItem.setInspectContent(inspectContents[i]);
			inspectTemplateItem.setInputType(inputTypes[i]);
			inspectTemplateItem.setStatus(1);
			inspectTemplateItem.setDefaultValue(defaultValues[i]);
			inspectTemplateItem.setCycle(Integer.parseInt(cycles[i]));
			if(StringUtils.isEmpty(pictureNums[i])){
				inspectTemplateItem.setPictureFlag(0);
				inspectTemplateItem.setPictureNum(0);
			}else{
				Integer pictureNum = Integer.parseInt(pictureNums[i]);
				if(pictureNum == 0){
					inspectTemplateItem.setPictureFlag(0);
				}else{
					inspectTemplateItem.setPictureFlag(1);
				}
				inspectTemplateItem.setPictureNum(pictureNum);
			}
			
			if("1222401".equals(inputTypes[i])){
				inspectTemplateItem.setNormalRange("");
				inspectTemplateItem.setInputType("text");
				testNum++;
			}else if("1222402".equals(inputTypes[i])){//此时是数值
				inspectTemplateItem.setNormalRange(minValues[numType]+"|"+maxValues[numType]);
				inspectTemplateItem.setInputType("number");
				numType++;
			}else if("1222403".equals(inputTypes[i])){
				inspectTemplateItem.setDictId(dictIds[numDict]);
				inspectTemplateItem.setInputType("radio");
				inspectTemplateItem.setNormalRange(normalRanges[testNum]);
				testNum++;
				numDict++;
			}else if("1222406".equals(inputTypes[i])){ //自定义 2016-09-27添加
				inspectTemplateItem.setNormalRange("");
				inspectTemplateItem.setInputType("custom");
				testNum++;
			}else{//此时是多选
				inspectTemplateItem.setNormalRange("");
				inspectTemplateItem.setInputType("multiple");
				inspectTemplateItem.setDefaultValue(defaultValues[i].substring(0, defaultValues[i].length()-1));
				inspectTemplateItem.setDictId(dictIds[numDict]);
				numDict++;
				nummultiple ++;
			}
			inspectTemplateItem.setTemplateId(templateId);
			inspectTemplateItem.setResType(inspectTemplate.getResType());
			
			if("netDevice".equals(netDevice)){   //此时是网络设备
				inspectTemplateItem.setInspectMappingId(deviceId[i]);
				inspectTemplateItem.setDeviceInspectFlag(1);
			}else{
				inspectTemplateItem.setDeviceInspectFlag(0);
			}
			
			getTemplateItemBean().save(inspectTemplateItem);
		}
		
		//4.更新模板中模板子项的数目
		int itemNum = inspectTemplate.getItemNum()+bigitemNames.length;
		inspectTemplate.setItemNum(itemNum);
		getTemplateBean().save(inspectTemplate);
		
		//5.查询出当前模板的模板项
		
		ActionForward actionForward = new ActionForward();
		if("netDevice".equals(netDevice)){   //此时是网络设备
			actionForward.setPath("/inspectTemplateManger.do?method=goToTemplateItemsList&id="+templateId+"&device=device");
		}else{
			actionForward.setPath("/inspectTemplateManger.do?method=goToTemplateItemsList&id="+templateId);
		}
		actionForward.setRedirect(true);
		return actionForward;
	
	 }
	
	/**
	 * 跳转到模板项修改
	 * @param mapping
	 * @param form
	 */
	public ActionForward alterTemplateItem(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = StaticMethod.nullObject2String(request.getParameter("id"));
		String device = StaticMethod.nullObject2String(request.getParameter("device"));
		InspectTemplateItem inspectTemplateItem = getTemplateItemBean().find(id);
		if(!"".equals(device)){
			request.setAttribute("device", device);
//			String templateId = StaticMethod.nullObject2String(request
//					.getParameter("templateId"));
			IInspectTemplateMgr inspectTemplateMgr = getTemplateBean();
			InspectTemplate inspectTemplate = inspectTemplateMgr.find(inspectTemplateItem.getTemplateId());
			request.setAttribute("templateId", inspectTemplateItem.getTemplateId());
			request.setAttribute("specialty", inspectTemplate.getSpecialty());
			request.setAttribute("resType", inspectTemplate.getResType());
		}
		request.setAttribute("inspectTemplateItem", inspectTemplateItem);
		return mapping.findForward("alterTemplateItem");
	}
	
	public ActionForward saveTemplateItem(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String id = StaticMethod.nullObject2String(request.getParameter("id"));
		String bigitemName = StaticMethod.nullObject2String(request.getParameter("bigitemName"));
		String inspectItem = StaticMethod.nullObject2String(request.getParameter("inspectItem"));
		String inspectContent = StaticMethod.nullObject2String(request.getParameter("inspectContent"));
		String inputType = StaticMethod.nullObject2String(request.getParameter("inputType2"));
		String dictId = StaticMethod.nullObject2String(request.getParameter("dictId"));
		String normalRange = StaticMethod.nullObject2String(request.getParameter("normalRange"));
		String maxValue = StaticMethod.nullObject2String(request.getParameter("maxValue"));
		String minValue = StaticMethod.nullObject2String(request.getParameter("minValue"));
		String pictureNums = StaticMethod.nullObject2String(request.getParameter("pictureNum"));
		String defaultValue[] = request.getParameterValues("defaultValue");
		String cycle = StaticMethod.nullObject2String(request.getParameter("cycle"));
		
		InspectTemplateItem inspectTemplateItem = getTemplateItemBean().find(id);
		String templateId = inspectTemplateItem.getTemplateId();
		inspectTemplateItem.setInspectItem(inspectItem);
		inspectTemplateItem.setInspectContent(inspectContent);
		inspectTemplateItem.setDefaultValue(defaultValue[0]);
		inspectTemplateItem.setCycle(Integer.parseInt(cycle));
		if(StringUtils.isEmpty(pictureNums)){
			inspectTemplateItem.setPictureFlag(0);
			inspectTemplateItem.setPictureNum(0);
		}else{
			Integer pictureNum = Integer.parseInt(pictureNums);
			if(pictureNum == 0){
				inspectTemplateItem.setPictureFlag(0);
			}else{
				inspectTemplateItem.setPictureFlag(1);
			}
			inspectTemplateItem.setPictureNum(pictureNum);
		}
		
		if("1222401".equals(inputType)){
			inspectTemplateItem.setNormalRange("");
			inspectTemplateItem.setInputType("text");
			
		}else if("1222402".equals(inputType)){//此时是数值
			inspectTemplateItem.setNormalRange(minValue+"|"+maxValue);
			inspectTemplateItem.setInputType("number");
			
		}else if("1222403".equals(inputType)){
			inspectTemplateItem.setDictId(dictId);
			inspectTemplateItem.setInputType("radio");
			inspectTemplateItem.setNormalRange(normalRange);
			
		}else if("1222406".equals(inputType)){ //自定义 2016-09-27添加
			inspectTemplateItem.setNormalRange("");
			inspectTemplateItem.setInputType("custom");
		}else{//此时是多选
//			String str ="";
//			for(int i=0;i<defaultValue.length;i++){
//				str = str+defaultValue[i]+"|";
//			}
//			inspectTemplateItem.setNormalRange("");
//			inspectTemplateItem.setInputType("multiple");
//			inspectTemplateItem.setDefaultValue("");
//			inspectTemplateItem.setDictId(dictId);
			inspectTemplateItem.setNormalRange("");
			inspectTemplateItem.setInputType("multiple");
			String mudefault = "";
			for(int i=0;i<defaultValue.length;i++){
				mudefault = mudefault+"|"+defaultValue[i];
			}
			System.out.println(mudefault);
			inspectTemplateItem.setDefaultValue(mudefault.substring(1, mudefault.length()));
			inspectTemplateItem.setDictId(dictId);
			
		}
		
		if(bigitemName.equals(inspectTemplateItem.getBigitemName())){
			getTemplateItemBean().save(inspectTemplateItem);
			
		}else{//此时的模板大项名字改变了
			String templageBigId = inspectTemplateItem.getBigitemId();
			//1.修改原来模板大项下的模板数目
			InspectTemplateBigItem inspectTemplateBigItem = getTemplateBigItemBean().find(templageBigId);
			int itemNum  = inspectTemplateBigItem.getItemNum();
			if(itemNum>1){
				inspectTemplateBigItem.setItemNum(itemNum-1);
				getTemplateBigItemBean().save(inspectTemplateBigItem);
			}else{
				inspectTemplateBigItem.setItemNum(0);
				inspectTemplateBigItem.setStatus(0);
				getTemplateBigItemBean().save(inspectTemplateBigItem);
			}
			//2.保存修改后的模板大项
			Search search = new Search();
			search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(),
					search);
			search.addFilterEqual("name", bigitemName);
			search.addFilterEqual("templateId", templateId);
			SearchResult<InspectTemplateBigItem> result = getTemplateBigItemBean().searchAndCount(search);
			InspectTemplateBigItem inspectTemplateBigItem2 = null;
			if(result.getResult().size()>0){
				inspectTemplateBigItem2 = result.getResult().get(0);
				int itemNum2 = inspectTemplateBigItem2.getItemNum();
				inspectTemplateBigItem2.setItemNum(itemNum2+1);
				getTemplateBigItemBean().save(inspectTemplateBigItem2);
				inspectTemplateItem.setBigitemId(inspectTemplateBigItem2.getId());
				inspectTemplateItem.setBigitemName(bigitemName);
			}else{
				inspectTemplateBigItem2 = new InspectTemplateBigItem();
				inspectTemplateBigItem2.setName(bigitemName);
				inspectTemplateBigItem2.setStatus(1);
				inspectTemplateBigItem2.setItemNum(1);
				inspectTemplateBigItem2.setTemplateId(templateId);
				getTemplateBigItemBean().save(inspectTemplateBigItem2);
				//修改模板小项的模板大项的信息
				inspectTemplateItem.setBigitemName(bigitemName);
				inspectTemplateItem.setBigitemId(inspectTemplateBigItem2.getId());
			}
			//3.保存模板小项
			getTemplateItemBean().save(inspectTemplateItem);
		}
		
		ActionForward actionForward = new ActionForward();
		actionForward.setPath("/inspectTemplateManger.do?method=goToTemplateItemsList&id="+templateId);
		actionForward.setRedirect(true);
		
		return actionForward;
	}

	/**
	 * 删除模版项
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void deleteTemplateItem(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		boolean successFlag = true;
		String id = StaticMethod.nullObject2String(request.getParameter("id"));
		Search search;
		if (id != null && !"".equals(id)) {
			search = new Search();
			search.addFilterEqual("id", id);
			try {
				InspectTemplateItem inspectTemplateItem = getTemplateItemBean().find(id);
				inspectTemplateItem.setStatus(0);
				getTemplateItemBean().save(inspectTemplateItem);
				
				//修改模板中模板项的数目
				Integer templateItemCount = 0;
				InspectTemplate inspectTemplate = getTemplateBean().find(inspectTemplateItem.getTemplateId());
				if(inspectTemplate.getItemNum()>0){
					templateItemCount = inspectTemplate.getItemNum()-1;
					inspectTemplate.setItemNum(templateItemCount);
					getTemplateBean().save(inspectTemplate);
				}
				
				//修改模板大项中模板项的数目
				InspectTemplateBigItem inspectTemplateBigItem = getTemplateBigItemBean().find(inspectTemplateItem.getBigitemId());
				if(inspectTemplateBigItem != null){
					int templateBigItem = inspectTemplateBigItem.getItemNum();
					if(templateBigItem>0){
						inspectTemplateBigItem.setItemNum(templateBigItem-1);
					}else{
						inspectTemplateBigItem.setItemNum(0);
						inspectTemplateBigItem.setStatus(0);
					}
					getTemplateBigItemBean().save(inspectTemplateBigItem);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				successFlag = false;
			}
		}
		if (successFlag) {
			response.getWriter().write(MobileConstants.successStr);
		} else {
			response.getWriter().write(MobileConstants.failureStr);
		}
	}

	/**
	 * 
	 * Description: 跳转到巡检模版项显示列表页面,模版管理
	 * 
	 * @Title: goToAddTemplate
	 * @param
	 * @param mapping
	 * @param
	 * @param form
	 * @param
	 * @param request
	 * @param
	 * @param response
	 * @param
	 * @return
	 * @param
	 * @throws Exception
	 * @return ActionForward
	 * @throws
	 */
	public ActionForward goToTemplateItemsList(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String templateId = StaticMethod.nullObject2String(request.getParameter("id"));
		String device = StaticMethod.nullObject2String(request.getParameter("device"));
		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(request.getParameterMap(),search);
		search.addFilterEqual("status", 1);
		search.addFilterEqual("templateId", templateId);
//		search.addSortDesc("addTime");
    	search.addSortAsc("orderNo");
		if("device".equals(device)){
			search.addFilterEqual("deviceInspectFlag", 1);
		}else{
			search.addFilterNotEqual("deviceInspectFlag", 1);
		}
		SearchResult<InspectTemplateItem> result = getTemplateItemBean().searchAndCount(search);
		List<InspectTemplateItem> inspectTemplateItemList = result.getResult();
		request.setAttribute("resultSize", result.getTotalCount());
		request.setAttribute("inspectTemplateItemList",inspectTemplateItemList);
		request.setAttribute("pageSize", CommonConstants.PAGE_SIZE);
		request.setAttribute("templateId", templateId);
		//权限管理,找到当前模板是谁添加的
		IInspectTemplateMgr inspectTemplateMgr = getTemplateBean();
		InspectTemplate inspectTemplate = inspectTemplateMgr.find(templateId);
		request.setAttribute("inspectTemplate", inspectTemplate);
		request.setAttribute("addUser", inspectTemplate.getAddUser());
		if("device".equals(device)){
			return mapping.findForward("goToDeviceTemplateItemsList");
		}else{
			return mapping.findForward("goToTemplateItemsList");
		}
		
	}

	/**
	 * 
	 * Description: 跳转到模版项新增页面
	 * 
	 * @Title: goToAddTemplate
	 * @param
	 * @param mapping
	 * @param
	 * @param form
	 * @param
	 * @param request
	 * @param
	 * @param response
	 * @param
	 * @return
	 * @param
	 * @throws Exception
	 * @return ActionForward
	 * @throws
	 */
	public ActionForward goToAddTemplateItem(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 字典类型树初始值
		// inspectItem.getDictType() 10402
		// {name=是否清洁, id=10402}

		// if(inspectItem.getDictType()!=null&&!"".equals(inspectItem.getDictType())){
		// ID2NameService iD2NameService = (ID2NameService)
		// getBean("id2nameService");
		// JSONObject data = new JSONObject();
		// data.put("name", iD2NameService.id2Name(inspectItem.getDictType(),
		// "ItawSystemDictTypeDao"));
		// data.put("id", inspectItem.getDictType());
		// JSONArray root = new JSONArray();
		// root.put(data);
		// request.setAttribute("data", root);
		// }

		String id = StaticMethod.nullObject2String(request.getParameter("id"));
		String templateId = StaticMethod.nullObject2String(request
				.getParameter("templateId"));
		Search search2 = new Search();
		search2.addFilterEqual("templateId", templateId);
		List<InspectTemplateItem> list = getTemplateItemBean()
		.searchAndCount(search2).getResult();
		
		if (null != id && !"".equals(id)) {//如果ID不为空,则为编辑
			Search search = new Search();
			search.addFilterEqual("id", id);
			List<InspectTemplateItem> inspectTemplateItems = getTemplateItemBean()
					.searchAndCount(search).getResult();
			request.setAttribute("type", "detail");
			if (!inspectTemplateItems.isEmpty()) {
				request.setAttribute("inspectTemplateItem",
						inspectTemplateItems.get(0));
				if (inspectTemplateItems.get(0).getDictId() != null
						&& !"".equals(inspectTemplateItems.get(0).getDictId())) {
					ID2NameService iD2NameService = (ID2NameService) getBean("id2nameService");
					JSONObject data = new JSONObject();
					data.put("name", iD2NameService.id2Name(
							inspectTemplateItems.get(0).getDictId(),
							"ItawSystemDictTypeDao"));
					data.put("id", inspectTemplateItems.get(0).getDictId());
					JSONArray root = new JSONArray();
					root.put(data);
					request.setAttribute("data", root);
				}
			}else {

				JSONObject data = new JSONObject();
				data.put("name", "关联字典值");
				data.put("id", 0);
				JSONArray root = new JSONArray();
				root.put(data);
				request.setAttribute("data", root);

			}
			request.setAttribute("templateId", inspectTemplateItems.get(0)
					.getTemplateId());
			return mapping.findForward("goToAddTemplateItem");
		}

		request.setAttribute("type", "new");
		JSONObject data = new JSONObject();
		data.put("name", "关联字典值");
		data.put("id", 0);
		JSONArray root = new JSONArray();
		root.put(data);
		request.setAttribute("data", root);
		request.setAttribute("templateId", templateId);
		request.setAttribute("list", list);
		return mapping.findForward("goToAddTemplateItem");
	}

	/**
	 * 
	 * Description: 跳转到模版项新增页面
	 * 
	 * @Title: goToAddTemplate
	 * @param
	 * @param mapping
	 * @param
	 * @param form
	 * @param
	 * @param request
	 * @param
	 * @param response
	 * @param
	 * @return
	 * @param
	 * @throws Exception
	 * @return ActionForward
	 * @throws
	 */
	public ActionForward goToimportTemplateItem(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String templateId = request.getParameter("templateId");

		request.setAttribute("templateId", templateId);
		return mapping.findForward("goToimportTemplateItem");
	}

	/**
	 * 获取平台“组织管理-字典管理”菜单下定义的字典
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author zhangxb
	 */
	@SuppressWarnings("unchecked")
	public void getDict(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// 获取节点id
		String nodeId = request.getParameter("node");
		ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");
		// 取下级节点
		List list = mgr.getDictSonsByDictid(nodeId);
		// json根（树根节点）
		JSONArray root = new JSONArray();
		// 遍历子版块
		if (null != list) {
			for (Iterator nodeIter = list.iterator(); nodeIter.hasNext();) {
				TawSystemDictType tawSystemDictType = (TawSystemDictType) nodeIter
						.next();
				JSONObject item = new JSONObject();
				item.put("id", tawSystemDictType.getDictId());
				item.put("text", tawSystemDictType.getDictName());
				item.put("dictId", tawSystemDictType.getDictId());
				item.put("parentDictId", tawSystemDictType.getParentDictId());
				item.put(UIConstants.JSON_NODETYPE, "folder");
				// item.put("allowChild", true); // 设置右键菜单
				// item.put("allowEdit", true);
				// item.put("allowDelete", true);

				item.put("iconCls", "folder");
				item.put("qtip", tawSystemDictType.getDictName());
				// item.put("allowNewThread", true);
				// item.put("allowListThreads", true);
				// item.put("allowListUnreadThreads", true);
				// item.put(UIConstants.JSON_NODETYPE,
				// InfopubConstants.NODETYPE_INFOPUB_FORUMS);

				// 设置是否为叶子节点
				boolean leafFlag = false;
				Integer leaf = tawSystemDictType.getLeaf();
				if (leaf != null && leaf.intValue() == 1) {
					leafFlag = true;
				}
				item.put("leaf", leafFlag);

				// 设置叶子节点可以被选择(不可以选择叶子节点)
				if (!leafFlag) {
					item.put("checked", false);
				}
				root.put(item);
			}
		}
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().print(root.toString());
	}

	public ActionForward getDictValues(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String dict = request.getParameter("dict");
		ITawSystemDictTypeManager mgr = (ITawSystemDictTypeManager) getBean("ItawSystemDictTypeManager");
		List dictTypeList = mgr.getDictSonsByDictid(dict);
		JSONArray json = new JSONArray();
		for (int i = 0; i < dictTypeList.size(); i++) {
			JSONObject jitem = new JSONObject();
			TawSystemDictType dictType = (TawSystemDictType) dictTypeList
					.get(i);
			jitem.put("name", dictType.getDictName());
			jitem.put("dictId", dictType.getDictId());
			json.put(jitem);
		}
		response.setCharacterEncoding("utf-8");
		response.getWriter().write(json.toString());
		return null;
	}

	//xls 模板下载
	@SuppressWarnings("deprecation")
	public void download(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
				String rootPath = request.getRealPath("/partner/partnerRes");
				String fileName = "巡检资源模板.zip";
				File file = new File(rootPath + File.separator + fileName);

				// 读到流中
				InputStream inStream = new FileInputStream(file);// 文件的存放路径
				// 设置输出的格式
				response.reset();
				response.setContentType("application/x-msdownload;charset=GBK");
				response.setCharacterEncoding("GB2312");
				response.setHeader("Content-Disposition", "attachment; filename="
						+ new String(fileName.getBytes("gbk"), "iso8859-1"));
				// 循环取出流中的数据
				byte[] b = new byte[1024];
				int len = 0;
				while ((len = inStream.read(b)) > 0)
					response.getOutputStream().write(b, 0, len);
				inStream.close();
			}
	
	/* 跳转到网络资源模板新增 */
	public ActionForward goToAddNetResourceTemplateItem(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String templateId = StaticMethod.nullObject2String(request
				.getParameter("templateId"));
		IInspectTemplateMgr inspectTemplateMgr = getTemplateBean();
		InspectTemplate inspectTemplate = inspectTemplateMgr.find(templateId);
		request.setAttribute("templateId", templateId);
		request.setAttribute("specialty", inspectTemplate.getSpecialty());
		request.setAttribute("resType", inspectTemplate.getResType());
		return mapping.findForward("goToAddNetResourceTemplateItem");
	}
	
	
}
