package com.boco.eoms.partner.deviceAssess.webapp.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.partner.deviceAssess.mgr.AllStatisticMgr;
import com.boco.eoms.partner.deviceAssess.util.MapObj;

public class AllStatisticAction extends BaseAction {

	public ActionForward search(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// FacilityQuantityMgr facilityQuantityMgr = (FacilityQuantityMgr)
		String special = request.getParameter("special");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String statisType = request.getParameter("statisType");
		String factoryDictId = "1121502";// 厂家字典值

		AllStatisticMgr allStatisticMgr = (AllStatisticMgr) ApplicationContextHolder
				.getInstance().getBean("allStatisticMgr");
		List<MapObj> list = allStatisticMgr.calculateList(special,
				factoryDictId, startTime, endTime);// 得到最终统计数据 list<MapObj>
		List<String> factoryList = allStatisticMgr
				.factorySearchList(factoryDictId);// 得到字典表里的厂商list
		List<String> finallyScore = allStatisticMgr.finallyScore(list,
				factoryList);// 得到最终得分List
		List<String> finallyRank = allStatisticMgr.finallyRank(finallyScore,
				factoryList);// 得到最终排名list
		if ("byQuarter".equals(statisType)) {
			Integer year = Integer.parseInt(startTime.substring(0, 4));
			Integer month = 0;
			String startTimeHistory="";
			String endTimeHistory="";
			if (!"-".equals(startTime.substring(7, 8))) {
				month = Integer.parseInt(startTime.substring(5, 6));
			} else {
				month = Integer.parseInt(startTime.substring(5, 7));
			}
			month = month - 3;
			if (month < 0) {
				year = year - 1;
				month = 10;
			}
			startTimeHistory = String.valueOf(year) + "-" + String.valueOf(month)
					+ "-1 00:00:00";
			if (month + 2 == 12 || month + 2 == 3) {
				endTimeHistory = String.valueOf(year) + "-"
						+ String.valueOf(month + 2) + "-31 23:59:59";
			} else {
				endTimeHistory = String.valueOf(year) + "-"
						+ String.valueOf(month + 2) + "-30 23:59:59";
			}
			List<MapObj> listHistory = allStatisticMgr.calculateList(special,
					factoryDictId, startTimeHistory, endTimeHistory);// 得到最终统计历史数据
														// list<MapObj>
			List<String> finallyScoreHistory = allStatisticMgr.finallyScore(
					listHistory, factoryList);// 得到最终得分List
			List<String> finallyRankHistory = allStatisticMgr.finallyRank(
					finallyScoreHistory, factoryList);// 得到最终排名list
			request.setAttribute("finallyRankHistory", finallyRankHistory);
			request.setAttribute("allStatisticListHistory", listHistory);
			request.setAttribute("finallyScoreHistory", finallyScoreHistory);
			request.setAttribute("startTimeHistory", startTimeHistory);
			request.setAttribute("endTimeHistory", endTimeHistory);
			request.setAttribute("ifSee", "ifSee");
			
		}
		request.setAttribute("finallyRank", finallyRank);
		request.setAttribute("finallyScore", finallyScore);
		request.setAttribute("factoryList", factoryList);
		request.setAttribute("allStatisticList", list);
		request.setAttribute("startTime", startTime);
		request.setAttribute("endTime", endTime);
		return mapping.findForward("allStatistic");
	}
}
