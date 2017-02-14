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
import com.boco.eoms.partner.personnel.util.fusionchart.service.BuildFusionChartXml;
import com.boco.eoms.partner.property.util.StatisticsResultExport;
import com.boco.eoms.partner.statistically.pojo.TdObjModel;
import com.boco.eoms.partner.statistically.utils.TableHelper;
import  com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.google.gson.JsonArray;

public class PnrElectricityBillsCountAction extends BaseAction {

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
		public ActionForward goToStatisticsPage(ActionMapping mapping, ActionForm form,	HttpServletRequest request, HttpServletResponse response) throws Exception {
			/**
	    	 * 根据当前省ID，列出所有地市,省份
	    	 */
	    	request.setAttribute("provinceList", StaticMethod.getUserRootProvinceAreaList());
	    	request.setAttribute("cityList", StaticMethod.getUserCityAreaList());
			return mapping.findForward("goToStatisticsPage");
		}
		public ActionForward statistics(ActionMapping mapping, ActionForm form,HttpServletRequest request, HttpServletResponse response)	throws Exception {
			/**
	    	 * 根据当前省ID，列出所有地市,省份
	    	 */
	    	request.setAttribute("provinceList", StaticMethod.getUserRootProvinceAreaList());
	    	request.setAttribute("cityList", StaticMethod.getUserCityAreaList());
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
					if(!group.equals("")){
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
			String city=StaticMethod.nullObject2String(request.getParameter("citySearch"),"");
			String district=StaticMethod.nullObject2String(request.getParameter("country"),"");
			String site=StaticMethod.nullObject2String(request.getParameter("siteStringLike"),"");
			String timeYear=StaticMethod.nullObject2String(request.getParameter("timeYearStringLike"),"");
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
			if(!"".equals(timeYear)){
				whereStr+=" and time_year='"+timeYear+"'";
			}
			
			String statisticsSql="select "+searchStr+" from pnr_electricity_bills_count  where " +"id  is not null "+whereStr+"  group by "+group+"  order by "+group;
			String sql="select "+searchStr+" from pnr_electricity_bills_count  where " +"id  is not null "+whereStr+"  group by "+group+"  order by "+group;
			StringBuffer items=new StringBuffer();
			List<String> headList = new ArrayList<String>();
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
				}else if ("january_bills".equals(checkedIds[i])) {
					headList.add("1月");
					items.append("1月份");
				}else if ("february_bills".equals(checkedIds[i])) {
					headList.add("2月");
					items.append("2月份");
				}else if ("march_bills".equals(checkedIds[i])) {
					headList.add("3月");
					items.append("3月份");
				}else if ("april_bills".equals(checkedIds[i])) {
					headList.add("4月");
					items.append("4月份");
				}else if ("may_bills".equals(checkedIds[i])) {
					headList.add("5月");
					items.append("5月份");
				}else if ("june_bills".equals(checkedIds[i])) {
					headList.add("6月");
					items.append("6月份");
				}else if ("july_bills".equals(checkedIds[i])) {
					headList.add("7月");
					items.append("7月份");
				}else if ("august_bills".equals(checkedIds[i])) {
					headList.add("8月");
					items.append("8月份");
				}else if ("september_bills".equals(checkedIds[i])) {
					headList.add("9月");
					items.append("9月份");
				}else if ("october_bills".equals(checkedIds[i])) {
					headList.add("10月");
					items.append("10月份");
				}else if ("november_bills".equals(checkedIds[i])) {
					headList.add("11月");
					items.append("11月份");
				}else if ("december_bills".equals(checkedIds[i])) {
					headList.add("12月");
					items.append("12月份");
				}
			}
			List<List<TdObjModel>> tempList = TableHelper.verticalGrowp(checkedIds,statisticsSql,"");
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
			request.setAttribute("timeYear",timeYear );
			if (!"".equals(exportFlag)&&exportFlag.equals("2")) {
				//执行导出
				String fileName="电费费用统计";
				StatisticsResultExport.exportStatisticsResultToXLSFile(true, tempList, headList, fileName, response,request);
				return null;
			}else {
				//跳转到统计页面
				request.setAttribute("hasSend", "ok");
				//当统计月份不为空时才出现统计图
				if (!"".equals(items.toString())&&tempList.size()>0) {
					String[] titles=items.toString().split("份");
					String[] colors=new String[]{"94B6D2","D6A91F","E79764","D57C7C","7EA900","000000","FF0000","FFFF00","00FF00","00FFFF","FF00FF","008080"};
					BuildFusionChartXml.setTitle(timeYear+"年电费费用统计图(单位:元)");
					BuildFusionChartXml.setNumberSuf("元");
					BuildFusionChartXml.setColors(colors);
					BuildFusionChartXml.setSpan(5);
					BuildFusionChartXml.setShowValues(0);
					String xml = BuildFusionChartXml.getSimpleBarXML(sql.toString(),titles);
					request.setAttribute("xml",xml );
					request.setAttribute("width",BuildFusionChartXml.getWidth() );
				}
				return mapping.findForward("goToStatisticsPage");
			}
		}
}
