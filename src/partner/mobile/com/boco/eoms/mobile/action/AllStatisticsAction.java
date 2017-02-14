package com.boco.eoms.mobile.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.common.util.StaticMethod;
import com.boco.eoms.deviceManagement.common.utils.CommonConstants;
import com.boco.eoms.mobile.model.AllStatistics;
import com.boco.eoms.mobile.service.AllStatisticsService;
import com.googlecode.genericdao.search.Search;

public class AllStatisticsAction extends BaseAction {

	/**
	 * 统计查询
	 * @throws IOException
	 */
	public void queryStatistics(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {
//		String statisticsName = StaticMethod.nullObject2String(request.getParameter("statisticsName"));
		String statisticsName = StaticMethod.nullObject2String(request.getParameter("statisticsName"));
		AllStatisticsService allStatisticsService  = (AllStatisticsService) this.getBean("allStatisticsServiceImpl");
		Search search = new Search();
		if(!"".equals(statisticsName)){
			search.addFilterLike("statisticsName", "%"+statisticsName+"%");
		}
		search.setMaxResults(CommonConstants.PAGE_SIZE);
		List<AllStatistics> searchResult = allStatisticsService.searchAndCount(search).getResult();
		if(null != searchResult && !searchResult.isEmpty()){
			JSONArray array = JSONArray.fromCollection(searchResult);
			System.out.println( array.toString() ); 
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/plain");
			try {
				response.getWriter().write(array.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}else{
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/plain");
			try {
				response.getWriter().write("".toString());
				return;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


}
