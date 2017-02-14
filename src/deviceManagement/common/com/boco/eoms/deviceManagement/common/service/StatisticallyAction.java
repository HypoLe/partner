package com.boco.eoms.deviceManagement.common.service;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.deviceManagement.common.pojo.TdObjModel;
import com.boco.eoms.deviceManagement.common.utils.StatisticsUntil;

public class StatisticallyAction extends BaseAction {

	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String rows[] = StaticMethod.nullObject2String(
				request.getParameter("deleteIds"), "").split(";");
		if (rows[0].equals("")) {
			rows = new String[] { "companyName" };
		}
		String search = "";
		for (int i = 0; i < rows.length; i++) {

			if (i == rows.length - 1) {
				search += rows[i];
			} else {
				search += rows[i] + ",";
			}

		}

		String compangyName = request.getParameter("companyName");
		String searchSql = "select " + search
				+ ", count(id) from company_information group by " + search
				+ " order by " + search;
		List headList = new ArrayList();
		for (int i = 0; i < rows.length; i++) {
			headList.add(rows[i]);
		}
		headList.add("count");

		List<List<TdObjModel>> tempList = StatisticsUntil.verticalGrowp(rows, searchSql,"/nop3/test.do?method=list");
//		List<List<TdObjModel>> tableList = new ArrayList<List<TdObjModel>>();
//		for (int i = tempList.size() - 1; i >= 0; i--) {
//			tableList.add(tempList.get(i));
//		}
		request.setAttribute("tableList", tempList);
		request.setAttribute("headList", headList);
		request.setAttribute("time", rows.length);

		return mapping.findForward("list");
	}
	/**
	 * @param
	 * 			 rows    the search  colums.
	 * 			 searchSqL	 the search Sql.
	 * 			 url	the amount wil be acquire .set to the tag <a>
	 * @return 	
	 * 			 List 	the table list to show.*/
	public List<List<TdObjModel>> verticalGrowp(String[] rows, String searchSql,String url) {
		String urlTemp=url;
		// 查询出结果集
		CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) getBean("commonSpringJdbcService");
		List<ListOrderedMap> resultList = jdbcService.queryForList(searchSql);
		// 构建一个MAP，放置合并的单元格。
		Map rowSpanMap = new HashMap();
		// 初始化计算器计算rowspan为多少
		Map<Integer, Integer> countRowMap = new HashMap<Integer, Integer>();
		for (int i = 0; i < rows.length; i++) {
			countRowMap.put(i, 1);
		}// 初始化 判断级别的避免父类不同子类相合并的情况
		Map<Integer, Integer> decideMap = new HashMap<Integer, Integer>();
		// 初始化重result里面取值的map
		Map<Integer, String> levelMap = new HashMap<Integer, String>();
		for (int j = 0; j < rows.length; j++) {
			levelMap.put(j, rows[j]);
		}
		levelMap.put(rows.length, "(count)");

		int levelLength = rows.length;
		// 循环查询数据库的结果集
		List<List<TdObjModel>> tableList = new ArrayList<List<TdObjModel>>();
		List<TdObjModel> trList;
		TdObjModel tdModel;
		HashMap<String, Integer> counttdMap = new HashMap<String, Integer>();
		//判断TD的显示，如果相同的显示一次则之后不会再显示了。
		HashMap<String, Boolean> counttdBoolMap = new HashMap<String, Boolean>();
		for (int i = 0; i < resultList.size(); i++) {
			ListOrderedMap childMap = resultList.get(i);
			trList = new ArrayList<TdObjModel>();
			String tdNames = "";
			//循环每一个行
			for (int j = 0; levelLength != 0 && j <= levelLength; j++) {
				String tdName = StaticMethod.nullObject2String(childMap
						.get(levelMap.get(j)));
				tdModel = new TdObjModel();
				tdModel.setName(tdName);
				
				
				if (!tdNames.equals("")) {
					tdNames = tdNames + "_";

				}
				tdNames = tdNames + tdName;
				//判断是否为最后的统计数字如果不是将进行比较合并
				if (j != levelLength) {
					int count = 1;
					if (counttdMap.containsKey(tdNames)) {
						count += counttdMap.get(tdNames);
					}
					counttdMap.put(tdNames, count);
					counttdBoolMap.put(tdNames, true);
					url+="&"+childMap.get(j)+"="+tdName;
				}else{
					tdModel.setUrl(url);
					url=urlTemp;
					tdModel.setShow(true);
				}
				trList.add(tdModel);
			}
			tableList.add(trList);
		}
		//构建最终显示的List
		for (List<TdObjModel> trListTemp : tableList) {
			String tdNames = "";
			int i = 0;
			for (TdObjModel tdObj : trListTemp) {
				
				if(i==levelLength)
					continue;
				
				String tdName = tdObj.getName();
				if (!tdNames.equals("")) {
					tdNames = tdNames + "_";
				}
				tdNames = tdNames + tdName;
				int rowspan = counttdMap.get(tdNames);
				tdObj.setRowspan(rowspan);
				if(counttdBoolMap.containsKey(tdNames)){
					counttdBoolMap.remove(tdNames);
					tdObj.setShow(true);
				}else{
					tdObj.setShow(false);
				}
				i+=1;
			}
		}

		return tableList;
	}
}
