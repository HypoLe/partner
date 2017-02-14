package com.boco.eoms.partner.property.webapp.action;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.area.model.TawSystemArea;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.partner.baseinfo.util.PartnerCityByUser;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;
import com.boco.eoms.partner.property.util.StatisticsResultExport;
import com.boco.eoms.partner.statistically.pojo.TdObjModel;
import com.boco.eoms.partner.statistically.utils.TableHelper;

public class PnrRentBillsCountAction  extends BaseAction{
	
/**
 * 进入统计页面
 *fengguangping
 * Oct 9, 2012-11:06:46 AM
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
	public ActionForward goToStatisticsPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		/**
    	 * 根据当前省ID，列出所有地市,省份
    	 */
    	request.setAttribute("provinceList", StaticMethod.getUserRootProvinceAreaList());
    	request.setAttribute("cityList", StaticMethod.getUserCityAreaList());
		return mapping.findForward("goToStatisticsPage");
	}
	/**
	 * 数据统计
	 * 在统计页面中，统计条件和统计项目的id命名为表的列名称，name为实体名称;
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward statistics(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		/**
    	 * 根据当前省ID，列出所有地市,省份
    	 */
    	request.setAttribute("provinceList", StaticMethod.getUserRootProvinceAreaList());
    	request.setAttribute("cityList", StaticMethod.getUserCityAreaList());
		response.setCharacterEncoding("utf-8");
		//rowString 字段和数据库里的字段名字完全相同
		String checkedIdsStr=StaticMethod.null2String(request.getParameter("checkedIds"));
		//checkedString带有标识以及公式的字段
		String checkedValuesStr=StaticMethod.null2String(request.getParameter("statisticsItems"));
		String checkedIds[]=checkedIdsStr.split(";");
		//数值转化为字符串，作为sql的search条件
		String checkedValues[]=checkedValuesStr.split(";");
		String searchStr="";
		String group="";
		for (int i = 0; i < checkedIds.length; i++) {
			if(!checkedIds[i].contains("_bills")){
				searchStr+=checkedValues[i]+" as "+checkedIds[i];
				if (!group.equals("")) {
					group+=",";
				}
				group+=checkedValues[i];
			}else {
				searchStr+=checkedValues[i]+" as "+checkedIds[i];
			}
			if (i!=checkedIds.length-1) {
				searchStr+=",";
			}
		}
		//获取where条件值
		String province=StaticMethod.nullObject2String(request.getParameter("provinceSearch"),"");
		String city=StaticMethod.nullObject2String(request.getParameter("cityId"),"");
		String district=StaticMethod.nullObject2String(request.getParameter("country"),"");
		String timeYear=StaticMethod.nullObject2String(request.getParameter("timeYearStringLike"),"");
		String site=StaticMethod.nullObject2String(request.getParameter("siteStringLike"),"");
		String exportFlag=StaticMethod.nullObject2String(request.getParameter("exportFlag"),"");
		String whereStr=" ";
		if(!"".equals(province)){
			whereStr+=" and province='"+province+"'";
		}
		if (!"".equals(city)) {
			whereStr+=" and city='"+city+"'";
		}
		if(!"".equals(district)){
			whereStr+=" and district='"+district+"'";
		}
		if (!"".equals(site)) {
			whereStr+=" and site like '%"+site+"%'";
		}
//		if(!"".equals(timeYear)){
//			whereStr+=" and time_year like '%"+timeYear+"%'";
//		}
		
		String statisticsSql="select "+searchStr+" from pnr_rent_bills_count  where " +"id  is not null "+whereStr+"  group by "+group+"  order by "+group;
		List<String> headList = new ArrayList<String>();
		//[area, maintenanceCompany, maintenanceMajor, carName, carType, carProperty, carStatus]
		for (int i = 0; i < checkedIds.length; i++) {
			if ("time_year".equals(checkedIds[i])) {
				headList.add("年份");
			}else if ("provinceTypeLikeArea".equals(checkedIds[i])) {
				headList.add("省份");
			}else if ("cityTypeLikeArea".equals(checkedIds[i])) {
				headList.add("地市");
			}else if ("districtTypeLikeArea".equals(checkedIds[i])) {
				headList.add("区县");
			}else if ("site".equals(checkedIds[i])) {
				headList.add("物业点");
			}else if ("total_bills".equals(checkedIds[i])) {
				headList.add("应付金额");
			}else if ("paid_total_bills".equals(checkedIds[i])) {
				headList.add("已付金额");
			}else if ("unpaid_total_bills".equals(checkedIds[i])) {
				headList.add("未付金额");
			}
		}
		//headList.add("总数");
		List<List<TdObjModel>> tempList = TableHelper.verticalGrowp(checkedIds,statisticsSql,"/partner/property/rentCount.do?method=searchInto");
		//不需要数据钻取，去掉count字段；
		for (List<TdObjModel>  list : tempList) {
			list.remove(list.size()-1);
		}
		request.setAttribute("tableList", tempList);
		request.setAttribute("headList", headList);
		request.setAttribute("checkedList", checkedIdsStr);
		request.setAttribute("provinceSearch", province);
		request.setAttribute("cityId", city);
		request.setAttribute("country", district);
		request.setAttribute("siteStringLike", site);
		if (!"".equals(exportFlag)&&exportFlag.equals("2")) {
			//执行导出
			String fileName="租赁费用统计";
			StatisticsResultExport.exportStatisticsResultToXLSFile(true, tempList, headList, fileName, response,request);
			return null;
		}else {
			//跳转到统计页面
			request.setAttribute("hasSend", "ok");
			return mapping.findForward("goToStatisticsPage");
		}
	}
}
