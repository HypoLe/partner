package com.boco.eoms.partner.inspect.webapp.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.mobile.util.MobileCommonUtils;
import com.boco.eoms.partner.inspect.mgr.IInspectLineTrackMgr;
import com.boco.eoms.partner.inspect.mgr.impl.InspectLineTrackMgrImpl;
import com.boco.eoms.partner.inspect.model.InspectLineTrack;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;

/**
 * 根据线路段查询出符合条件的巡检点
 * @author LEE
 *
 */
public class InspectLineTrackAction  extends BaseAction{
	public void queryLinePointBySeg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		String segId = StaticMethod.nullObject2String(request.getParameter("segId"));
		String lineId = StaticMethod.nullObject2String(request.getParameter("lineId"));
		String planId = StaticMethod.nullObject2String(request.getParameter("planId"));
		IInspectLineTrackMgr distanceMgr = (IInspectLineTrackMgr) this.getBean("lineTrackMgr");
		Search search = new Search();
		if("".equals(segId)&&"".equals(lineId)&&"".equals(planId)){
			MobileCommonUtils.responseWrite(response, "", "UTF-8");
			return;
		}
		if(!"".equals(segId)){
			search.addFilterEqual("segId", segId);
		}
		if(!"".equals(lineId)){
			search.addFilterEqual("lineId", lineId);
		}
		
		if(!"".equals(planId)){
			search.addFilterEqual("planId", planId);
		}
		search.addSortAsc("signTime");
		SearchResult<InspectLineTrack> searchResult = distanceMgr.searchAndCount(search);
		int rsultSize = searchResult.getTotalCount();
		List<InspectLineTrack> returnList = searchResult.getResult();
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("rsultSize", rsultSize);
		returnMap.put("list", returnList);
		MobileCommonUtils.responseWrite(response, MobileCommonUtils.toJson(returnMap), "UTF-8");
	}
}
