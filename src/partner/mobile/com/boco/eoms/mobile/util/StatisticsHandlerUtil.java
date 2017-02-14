package com.boco.eoms.mobile.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.boco.eoms.partner.baseinfo.model.TdObjModel;
import com.google.gson.Gson;

public class StatisticsHandlerUtil {

	public static void handRequest(HttpServletRequest request,
			HttpServletResponse response) {
		setContentType(response);
		HashMap<String, Object> returnMap = new HashMap<String, Object>();
		List<List<TdObjModel>> tempList = (List<List<TdObjModel>>) request
				.getAttribute("tableList");
		List<String> headList = (List<String>) request.getAttribute("headList");
		String pageType = (String) request.getAttribute("pageType");
		String month = (String) request.getAttribute("month");
		String year = (String) request.getAttribute("year");
		returnMap.put("tempList", tempList);
		returnMap.put("headList", headList);
		returnMap.put("pageType", pageType);
		returnMap.put("month", month);
		returnMap.put("year", year);

		List<FeeStatistics> returnList = new ArrayList<FeeStatistics>();

		FeeStatistics feeStatisticsTemp;
		for (int i = 0; i < tempList.size(); i++) {
			feeStatisticsTemp = new FeeStatistics();
			List<TdObjModel> tempTdModelList = (List<TdObjModel>) tempList.get(i);
			int[] indexArray = new int[5];
			if("city".equals(pageType)){
				indexArray[0] = 1;indexArray[1] = 4;
			}else if("comp".equals(pageType)){
				indexArray[0] = 0;indexArray[1] = 3;
			}else if("major".equals(pageType)){
				indexArray[0] = 2;indexArray[1] = 4;
			}
			indexArray[2] = 3;
			indexArray[3] = 5;
			indexArray[4] = 6;
			feeStatisticsTemp.name = tempTdModelList.get(indexArray[0]).getName();
			feeStatisticsTemp.dataOne = (int)Float.parseFloat(tempTdModelList.get(indexArray[2]).getName());
			feeStatisticsTemp.dataTwo = (int)Float.parseFloat(tempTdModelList.get(indexArray[1]).getName());
			feeStatisticsTemp.dataThree = (int)Float.parseFloat(tempTdModelList.get(indexArray[3]).getName());
			feeStatisticsTemp.dataFour = (int)Float.parseFloat(tempTdModelList.get(indexArray[4]).getName());
			returnList.add(feeStatisticsTemp);
		}

		Gson gson = new Gson();
		String returnStr = gson.toJson(returnList);
		write(response, returnStr);
	}

	static void write(HttpServletResponse response, String s) {
		try {
			response.getWriter().write(s);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static void setContentType(HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/plain");
	}

	public Object getModel(String getType) {
		if ("FeeStatisticsMonth".equals(getType)) {
			return new FeeStatistics();
		}
		return null;
	}

	/**
	 * 月度考核代维费用之按地市统计
	 * 
	 * @author LEE
	 * 
	 */
	public static class FeeStatistics {
		public String id;// 统计ID

		public String name;// 统计名称:包换对代维公司的统计,费用统计或其它统计

		public int dataOne;
		public int dataTwo;
		public int dataThree;
		public int dataFour;
		public int dataFive;
		public int dataSix;

	}
}
