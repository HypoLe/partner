package com.boco.eoms.partner.inspect.webapp.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.deviceManagement.common.utils.CommonConstants;
import com.boco.eoms.partner.inspect.mgr.IErrorDistanceMgr;
import com.boco.eoms.partner.inspect.model.ErrorDistance;
import com.boco.eoms.partner.inspect.util.MapToBeanUtil;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

/**
 * 经纬度进行误差匹配
 * @author LEE
 *
 */
public class ErrorDistanceAction  extends BaseAction{
	/**
	 *  跳转到配置页面
	 */
	public ActionForward goToAddPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		IErrorDistanceMgr distanceMgr = (IErrorDistanceMgr) this.getBean("errorDistanceMgr");
		Search search = new Search();
		search.addFilterEqual("delete", 0);
		SearchResult<ErrorDistance> searchResult = distanceMgr.searchAndCount(search);
		request.setAttribute("list", searchResult.getResult());
		request.setAttribute("size", searchResult.getTotalCount());
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		String showType = StaticMethod.nullObject2String(request.getParameter("showType"));
		if("".equals(showType)){
			showType = 1+"";
		}
		request.setAttribute("showType",showType );
		return mapping.findForward("errorDistanceMainForm");
	}
	/**
	 * 编辑配置
	 */
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		IErrorDistanceMgr distanceMgr = (IErrorDistanceMgr) this.getBean("errorDistanceMgr");
		
		String hiddenRuleId = StaticMethod.nullObject2String(request.getParameter("hiddenRuleId"));
		String hiddenResource = StaticMethod.nullObject2String(request.getParameter("hiddenResource"));
		String type = StaticMethod.nullObject2String(request.getParameter("type"));
		if("delete".equals(type)){
			String id = StaticMethod.nullObject2String(request.getParameter("id"));
			Search search = new Search();
			search.addFilterEqual("id", id);
			distanceMgr.remove(distanceMgr.searchUnique(search));
			return mapping.findForward("success");
		}
		
		Search search = new Search();
		if(!"".equals(hiddenResource)){
			search.addFilterEqual("resource", hiddenResource);
		}else{
			search.addFilterEqual("resource", StaticMethod.nullObject2String(request.getParameter("resource")));
		}
		SearchResult<ErrorDistance> searchResult = distanceMgr.searchAndCount(search);
		
		if(searchResult.getResult().isEmpty()){//如果为空,则新增,
			ErrorDistance errorDistance= new ErrorDistance();
			errorDistance.setAddUser(getUserId(request));
			errorDistance.setId(hiddenRuleId);
			errorDistance.setAddTime(StaticMethod.getCurrentDateTime());
			errorDistance.setRule(Integer.parseInt(""+StaticMethod.null2Long(request.getParameter("rule"))));
			errorDistance.setSpeciality(StaticMethod.nullObject2String(request.getParameter("speciality")));
			errorDistance.setResource(StaticMethod.nullObject2String(request.getParameter("resource")));
			errorDistance.setIntervalTime(StaticMethod.nullObject2int(request.getParameter("intervalTime")));
			distanceMgr.save(errorDistance);
		}else{
			ErrorDistance errorDistance= searchResult.getResult().get(0);
			errorDistance.setAddUser(getUserId(request));
//			errorDistance.setId(hiddenRuleId);
			errorDistance.setAddTime(StaticMethod.getCurrentDateTime());
			errorDistance.setIntervalTime(StaticMethod.nullObject2int(request.getParameter("intervalTime")));
			errorDistance.setRule(Integer.parseInt(""+StaticMethod.null2Long(request.getParameter("rule"))));
//			errorDistance.setSpeciality(StaticMethod.nullObject2String(request.getParameter("speciality")));
//			errorDistance.setResource(StaticMethod.nullObject2String(request.getParameter("resource")));
			distanceMgr.save(errorDistance);
		}
		
		
		
		
		
		return mapping.findForward("success");
	}
	/**
	 *  配置列表查看
	 */
	public ActionForward goToList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		IErrorDistanceMgr distanceMgr = (IErrorDistanceMgr) this.getBean("errorDistanceMgr");
		Search search = new Search();
		String showType = StaticMethod.nullObject2String(request.getParameter("showType"));
		if("".equals(showType)){
			showType = "1122501";
		}
		search.addFilterEqual("speciality", showType);
		search.addFilterEqual("delete", 0);
		SearchResult<ErrorDistance> searchResult = distanceMgr.searchAndCount(search);
		request.setAttribute("list", searchResult.getResult());
		request.setAttribute("size", searchResult.getTotalCount());
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		if("".equals(StaticMethod.nullObject2String(request.getParameter("showType")))){
			request.setAttribute("showType", 1122501);
		}else{
			request.setAttribute("showType",request.getParameter("showType"));
		}
		return mapping.findForward("errorDistanceList");
	}
}
