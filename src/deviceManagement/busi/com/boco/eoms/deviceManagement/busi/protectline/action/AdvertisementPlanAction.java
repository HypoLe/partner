package com.boco.eoms.deviceManagement.busi.protectline.action;

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
import com.boco.eoms.deviceManagement.busi.protectline.model.AdvertisementPlan;
import com.boco.eoms.deviceManagement.busi.protectline.service.AdvertisementPlanService;
import com.boco.eoms.deviceManagement.common.pojo.TdObjModel;
import com.boco.eoms.deviceManagement.faultInfo.utils.CommonConstants;
import com.boco.eoms.deviceManagement.faultInfo.utils.CommonUtils;
import com.google.common.base.Strings;
import com.googlecode.genericdao.search.SearchResult;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.commons.system.dict.service.ID2NameService;

import com.boco.eoms.deviceManagement.common.service.CommonSpringJdbcService;

public class AdvertisementPlanAction extends BaseAction {

	public ActionForward goToAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		return mapping.findForward("goToAdd");
	}

	public ActionForward goToEdit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AdvertisementPlanService advertisementPlanService = (AdvertisementPlanService) getBean("advertisementPlanService");
		String id = request.getParameter("id");
		request.setAttribute("advertisementPlan", advertisementPlanService
				.findById(id));
		return mapping.findForward("goToEdit");
	}

	public ActionForward add(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AdvertisementPlanService advertisementPlanService = (AdvertisementPlanService) getBean("advertisementPlanService");
		System.out.println("========reviewer======:"
				+ request.getParameter("reviewer"));
		System.out.println("==========city========:"
				+ request.getParameter("city") + "==");
		advertisementPlanService.add(getUserId(request), request
				.getParameter("reviewer"), request.getParameter("city"),
				request.getParameter("generalStone"), request
						.getParameter("detectStone"), request
						.getParameter("advertisement"), request
						.getParameter("remarks"));
		return mapping.findForward("success");
	}

	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AdvertisementPlanService advertisementPlanService = (AdvertisementPlanService) getBean("advertisementPlanService");
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"advertisementPlanList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		SearchResult<AdvertisementPlan> searchResult = advertisementPlanService
				.listAll(getUserId(request), request.getParameter("advertisementArea"), firstResult * CommonConstants.PAGE_SIZE,
						CommonConstants.PAGE_SIZE);
		request.setAttribute("advertisementPlanList", searchResult.getResult());
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("size", searchResult.getTotalCount());
		return mapping.findForward("list");
	}

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AdvertisementPlanService advertisementPlanService = (AdvertisementPlanService) getBean("advertisementPlanService");
		String id = request.getParameter("id");
		advertisementPlanService.delete(id);
		CommonUtils.printJsonSuccessMsg(response);
		return null;
	}

	public ActionForward sumbit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AdvertisementPlanService advertisementPlanService = (AdvertisementPlanService) getBean("advertisementPlanService");
		advertisementPlanService
				.sumbit(getUserId(request), request.getParameter("reviewer"),
						request.getParameter("advertisementArea"), request
								.getParameter("generalStone"), request
								.getParameter("detectStone"), request
								.getParameter("advertisement"), request
								.getParameter("remarks"));
		return mapping.findForward("success");
	}

	public ActionForward save(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AdvertisementPlanService advertisementPlanService = (AdvertisementPlanService) getBean("advertisementPlanService");
		advertisementPlanService
				.save(getUserId(request), request.getParameter("reviewer"),
						request.getParameter("advertisementArea"), request
								.getParameter("generalStone"), request
								.getParameter("detectStone"), request
								.getParameter("advertisement"), request
								.getParameter("remarks"));
		return mapping.findForward("success");
	}

	public ActionForward goToReview(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
//		AdvertisementPlanService advertisementPlanService = (AdvertisementPlanService) getBean("advertisementPlanService");
//		String id = request.getParameter("id");
//		request.setAttribute("advertisementPlan", advertisementPlanService
//				.findById(id));
		AdvertisementPlanService advertisementPlanService = (AdvertisementPlanService) getBean("advertisementPlanService");
		String id = request.getParameter("id");
		request.setAttribute("advertisementPlan", advertisementPlanService
				.findById(id));
		request.setAttribute("size", 15);
		request.setAttribute("operHis", advertisementPlanService.listRejectHis(id));
		return mapping.findForward("goToReview");

	}
	public ActionForward goToReview1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AdvertisementPlanService advertisementPlanService = (AdvertisementPlanService) getBean("advertisementPlanService");
		String id = request.getParameter("id");
		request.setAttribute("advertisementPlan", advertisementPlanService
				.findById(id));
		return mapping.findForward("goToReview1");

	}
	public ActionForward listReview(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AdvertisementPlanService advertisementPlanService = (AdvertisementPlanService) getBean("advertisementPlanService");
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"advertisementPlanList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		String city = request.getParameter("advertisementArea");
		SearchResult<AdvertisementPlan> searchResult = advertisementPlanService
				.listReview(getUserId(request), city, firstResult
						* CommonConstants.PAGE_SIZE, CommonConstants.PAGE_SIZE);
		request.setAttribute("advertisementPlanList", searchResult.getResult());
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("size", searchResult.getTotalCount());
		return mapping.findForward("listReview");
	}

	public ActionForward review(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AdvertisementPlanService advertisementPlanService = (AdvertisementPlanService) getBean("advertisementPlanService");
		advertisementPlanService.review(request.getParameter("id"), request
				.getParameter("suggestion"), request.getParameter("allow"));
		return mapping.findForward("success");
	}

	public ActionForward listReject(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AdvertisementPlanService advertisementPlanService = (AdvertisementPlanService) getBean("advertisementPlanService");
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"advertisementPlanList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		String city = request.getParameter("advertisementArea");
		SearchResult<AdvertisementPlan> searchResult = advertisementPlanService
				.listReject(getUserId(request), city, firstResult
						* CommonConstants.PAGE_SIZE, CommonConstants.PAGE_SIZE);
		request.setAttribute("advertisementPlanList", searchResult.getResult());
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("size", searchResult.getTotalCount());
		return mapping.findForward("listReject");
	}

	public ActionForward goToReSubmit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AdvertisementPlanService advertisementPlanService = (AdvertisementPlanService) getBean("advertisementPlanService");
		String id = request.getParameter("id");
		request.setAttribute("rejectHis", advertisementPlanService
				.listRejectHis(id));
		request.setAttribute("advertisementPlan", advertisementPlanService
				.findById(id));
		return mapping.findForward("goToResumbit");
	}
public ActionForward goToDetail(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws Exception {
	AdvertisementPlanService advertisementPlanService = (AdvertisementPlanService) getBean("advertisementPlanService");
	String id = request.getParameter("id");
	request.setAttribute("advertisementPlan", advertisementPlanService
			.findById(id));
//	request.setAttribute("operHis", advertisementPlanService
//			.listOperHis(id));
	request.setAttribute("size", 15);
	request.setAttribute("operHis", advertisementPlanService.listRejectHis(id));
	return mapping.findForward("goToDetail");
}
	public ActionForward resubmit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AdvertisementPlanService advertisementPlanService = (AdvertisementPlanService) getBean("advertisementPlanService");
		String id = request.getParameter("id");
		advertisementPlanService.resubmit(id, request.getParameter("reviewer"),
				request.getParameter("advertisementArea"), request
						.getParameter("generalStone"), request
						.getParameter("detectStone"), request
						.getParameter("advertisement"), request
						.getParameter("remarks"));
		return mapping.findForward("success");
	}

	public ActionForward listPass(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		AdvertisementPlanService advertisementPlanService = (AdvertisementPlanService) getBean("advertisementPlanService");
		String pageIndexString = request
				.getParameter((new org.displaytag.util.ParamEncoder(
						"advertisementPlanList")
						.encodeParameterName(org.displaytag.tags.TableTagParameters.PARAMETER_PAGE)));
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		String city = request.getParameter("advertisementArea");
		SearchResult<AdvertisementPlan> searchResult = advertisementPlanService
				.listPass(city, firstResult * CommonConstants.PAGE_SIZE,
						CommonConstants.PAGE_SIZE);
		request.setAttribute("advertisementPlanList", searchResult.getResult());
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("size", searchResult.getTotalCount());
		return mapping.findForward("listPass");
	}

	
	
	public ActionForward goToState(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	//	AdvertisementPlanService advertisementPlanService = (AdvertisementPlanService) getBean("advertisementPlanService");
	//	List list = advertisementPlanService.state();
	//	System.out.println(list);
		return mapping.findForward("goToState");		
	}

	
	public ActionForward state(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception { 
		String ss = request.getParameter("deleteIds");
		System.out.println("ssssssssssssssssss"+ss);
		//ss += "sum(generalStone);sum(detectStone);sum(generalStone)+sum(detectStone);sum(advertisement)";
		//String[] rows = StaticMethod.nullObject2String(ss, "").split(";");
		String colConvertCondiction[] = new String[]{"TypeLikeArea","","","","","","","","",""};
		String search = "";
		String group = "";
		
		search+="city";
		group+="city";
				
		// get the text to where condition.
		
		String searchSql = "select " + search
				+ ",sum(generalStone) sum1, sum(detectStone) sum2, sum(generalStone)+sum(detectStone) sum3, sum(advertisement) sum4 " 
				+ "from dm_advertisementplan_info where status=2"
				+ " group by " + group
				+ " order by " + group;
		System.out.println("SQL=================="+searchSql);
		
		// set table header title
		List<String> headList = new ArrayList<String>();
		//headList.add("序号");
		headList.add("所属地市");
		headList.add("计划增补普通标石数量");
		headList.add("计划增补侦测标石数量");
		headList.add("计划增补标石总量");
		headList.add("计划增补宣传牌数量");
		//headList.add("总数");

		List<List<TdObjModel>> tempList = this.verticalGrowp(colConvertCondiction,searchSql,"advertisementPlanAction.do?method=state");
		//System.out.println(list length+)
		
		request.setAttribute("tableList", tempList);
		request.setAttribute("headList", headList);
		request.setAttribute("time", 5);
		request.setAttribute("checkList", request.getParameter("checks"));
		//request.setAttribute("location", location);
		//request.setAttribute("projectName", projectName);

		return mapping.findForward("goToState");
	}
	

	private List<List<TdObjModel>> verticalGrowp(String[] colConvertCondiction,String searchSql,String url) {
		String urlTemp=url;
		// 查询出结果集
		CommonSpringJdbcService jdbcService = (CommonSpringJdbcService) ApplicationContextHolder.getInstance().getBean("commonSpringJdbcService");
		List<ListOrderedMap> resultList = jdbcService.queryForList(searchSql);
		
		// 查询结果列名结合
		List<String> colKeyList = null;
		Map<Integer, String> colKeyMap = new HashMap<Integer, String>();
		if(!resultList.isEmpty()) {
			colKeyList = resultList.get(0).keyList();
		} else {
			return null;
		}
		for (int j = 0; j < colKeyList.size(); j++) {
			colKeyMap.put(j, colKeyList.get(j));
		}
		
		int colKeySize = colKeyList.size();
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
			for (int j = 0;j < colKeySize; j++) {
				String tdName = StaticMethod.nullObject2String(childMap
						.get(colKeyMap.get(j)));
				tdModel = new TdObjModel();
				 if(colConvertCondiction[j].contains("TypeLikedict")){
					ID2NameService service = (ID2NameService) ApplicationContextHolder
					.getInstance().getBean("ID2NameGetServiceCatch");
					tdName =service.id2Name(tdName, "tawSystemDictTypeDao");
				}
				 if(colConvertCondiction[j].contains("TypeLikeArea")){
						ID2NameService service = (ID2NameService) ApplicationContextHolder
						.getInstance().getBean("ID2NameGetServiceCatch");
						tdName =service.id2Name(tdName, "tawSystemAreaDao");
					}
				 if(colConvertCondiction[j].contains("TypeLikeUser")){
						ID2NameService service = (ID2NameService) ApplicationContextHolder
						.getInstance().getBean("ID2NameGetServiceCatch");
						tdName =service.id2Name(tdName, "tawSystemUserDao");
					}
				tdModel.setName(tdName);
				
				if (!tdNames.equals("")) {
					tdNames = tdNames + "_";

				}
				tdNames = tdNames + tdName;
				//判断是否为最后的统计数字如果不是将进行比较合并
				if (j != colKeySize) {
					int count = 1;
					if (counttdMap.containsKey(tdNames)) {
						count += counttdMap.get(tdNames);
					}
					counttdMap.put(tdNames, count);
					counttdBoolMap.put(tdNames, true);
					 if(colConvertCondiction[j].contains("TypeLikedict")){
						 url+="&"+childMap.get(j)+"="+StaticMethod.nullObject2String(childMap
									.get(colKeyMap.get(j)));
						}
					 else if(colConvertCondiction[j].contains("TypeLikeArea")){
						 url+="&"+childMap.get(j)+"="+StaticMethod.nullObject2String(childMap
									.get(colKeyMap.get(j)));
						}
					 else if(colConvertCondiction[j].contains("TypeLikeUser")){
						 url+="&"+childMap.get(j)+"="+StaticMethod.nullObject2String(childMap
									.get(colKeyMap.get(j)));
						}
					 else{
					url+="&"+childMap.get(j)+"="+tdName;
					 }
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
				
				if(i==colKeySize)
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
