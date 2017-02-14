package com.boco.activiti.partner.process.webapp.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.activiti.partner.process.model.RoomAssets;
import com.boco.activiti.partner.process.po.AssetQueryConditionModel;
import com.boco.activiti.partner.process.po.RoomAssetsModel;
import com.boco.activiti.partner.process.service.IRoomAssetsService;
import com.boco.eoms.partner.process.util.CommonUtils;
import com.boco.eoms.sheet.base.webapp.action.SheetAction;
import com.google.common.base.Strings;
import com.googlecode.genericdao.search.Search;

public class RoomAssetsAction extends SheetAction {

	/**
	 * 查询资产
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward queryAssets(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
//		request.getParameterValues(name)
		
		// 1.分页
		int pageSize = 50;// 默认每页显示50条
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "roomAssetsList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();

		// 2.查询条件
		// 资产编码
		String assetNumbers = request.getParameter("assetNumbers");
		// 资产名称
		String assetName = request.getParameter("assetName");
		// 已选择的资产id
		String assetIds = "";
		String tempAssetIds = request.getParameter("assetIds");
		if (tempAssetIds != null && !"".equals(tempAssetIds)) {
			String[] split = tempAssetIds.split(",");
			for (int i = 0; i < split.length; i++) {
				assetIds += "'" + split[i] + "',";
			}
			assetIds = assetIds.substring(0, assetIds.length() - 1);
		}

		AssetQueryConditionModel assetQueryConditionModel = new AssetQueryConditionModel();
		assetQueryConditionModel.setAssetName(assetName);
		assetQueryConditionModel.setAssetNumbers(assetNumbers);
		assetQueryConditionModel.setAssetIds(assetIds);

		IRoomAssetsService roomAssetsService = (IRoomAssetsService) getBean("roomAssetsService");

		// 工单管理-传输局工单-预检预修工单-待回复工单 集合
		List<RoomAssetsModel> roomAssetsList = null;
		int total = 0;
		// if (assetNumbers != null && !"".equals(assetNumbers))
		// {//资产编码不为空才能查询数据
		try {
			roomAssetsList = roomAssetsService.queryAssetsList(
					assetQueryConditionModel, firstResult, endResult, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}

//		if (roomAssetsList != null && roomAssetsList.size() > 0) {
			total = roomAssetsService.queryAssetsListCount(
					assetQueryConditionModel, firstResult, endResult, pageSize);
//		}
		// }

		request.setAttribute("roomAssetsList", roomAssetsList);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("total", total);
		request.setAttribute("assetNumbers", assetNumbers);
		request.setAttribute("assetName", assetName);
		request.setAttribute("assetIds", tempAssetIds);

		return mapping.findForward("openSelectRoomAssets");
	}

	/**
	 * 
	 * 根据选择的资产ID值,查询资产详细信息,显示到新增派发页面
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward selectAssetTodo(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String selectedAssetIds = request.getParameter("selectedAssetIds");
		String[] ids = selectedAssetIds.split(",");

		List<RoomAssets> roomAssetsList = new ArrayList<RoomAssets>();
		JSONArray json = new JSONArray();

		int count = 0;// 计数器

		IRoomAssetsService roomAssetsService = (IRoomAssetsService) getBean("roomAssetsService");
		// 获取每一个资产的信息
		for (String str : ids) {
			if (!"".equals(str)) {

				Search search = new Search();
				search.addFilterEqual("id", str);
				roomAssetsList = roomAssetsService.search(search);
				if (roomAssetsList != null && roomAssetsList.size() > 0) {
					JSONObject jo = new JSONObject();
					jo.put("id", roomAssetsList.get(0).getId());
					//资产标签号
					jo.put("assetNumbers", roomAssetsList.get(0).getAssetNumbers());
					//资产名称
					jo.put("assetName", roomAssetsList.get(0).getAssetName());
					//资产类别
					jo.put("assetsSort", roomAssetsList.get(0).getAssetsSort());
					//设备型号
					jo.put("modelVersion", roomAssetsList.get(0).getModelVersion());
					//资产启用日期
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					if(roomAssetsList.get(0).getAssetsStartTime()!=null && !"".equals(roomAssetsList.get(0).getAssetsStartTime())){
						try{
							jo.put("assetsStartTime",sdf.format(roomAssetsList.get(0).getAssetsStartTime()) );
							
						}catch(Exception e){
								e.printStackTrace();
						}
						
					}else{
						jo.put("assetsStartTime","无");
					}
					//资产使用月数（月）
					jo.put("assetsMonthNum", roomAssetsList.get(0).getAssetsMonthNum());
					//原值
					jo.put("originalValue", roomAssetsList.get(0).getOriginalValue());
					//累计折旧
					jo.put("accumulatedDepreciation", roomAssetsList.get(0).getAccumulatedDepreciation());
					//资产净值
					jo.put("assetsNet", roomAssetsList.get(0).getAssetsNet());
					//累计减值准备
					jo.put("assetsDevalue", roomAssetsList.get(0).getAssetsDevalue());

					json.put(jo);
				}
				count++;
			}
		}

		response.getWriter().write(json.toString());
		return null;
	}
}
