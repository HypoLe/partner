package com.boco.eoms.mobile.action;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import com.boco.eoms.mobile.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.deviceManagement.busi.network.service.HiddenTroubleService;
import com.google.gson.Gson;

public final class HiddenTroubleAction extends BaseAction {
	public void saveHiddenTrouble(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		addByAndroid(mapping,form,request,response);
	}
	void hiddenTrouble_GetHandHiddenTroubleType(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		ITawSystemDictTypeManager _objDictManager = (ITawSystemDictTypeManager) ApplicationContextHolder
		.getInstance().getBean("ItawSystemDictTypeManager");
			List list = _objDictManager.getDictSonsByDictid("1200201");
	}
	/**
	 * 添加
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "finally" })
	public void addByAndroid(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String type = request.getParameter("type");
		String msg  = "";
		if("android".equals(type)){
			InputStream is = request.getInputStream();
			if(null != is){
				msg = PartnerUtil.inputStreamToString(is);
				System.out.println("msg   "+msg);
			}else {
				MobileCommonUtils.responseWrite(response, "", "UTF-8");
				return;
			}
			JSONArray array = JSONArray.fromString("["+msg+"]");//将字符串转换成json数组
			if(null == array ||array.isEmpty()){//如果空值 则返回""
				MobileCommonUtils.responseWrite(response, "", "UTF-8");
				return;
			}
			JSONObject tempObj = array.getJSONObject(0);//第0个为站点数据,包括站点数据和站点项数据
			String hiddentrouble = tempObj.getString("HiddenTrouble");
			if(null == hiddentrouble||"".equals(hiddentrouble)){//如果空值 则返回""
				MobileCommonUtils.responseWrite(response, "", "UTF-8");
				return;
			}
			
			JSONArray jsonarray = JSONArray.fromObject(hiddentrouble);
			for(int item_i=1;item_i<jsonarray.length();item_i++){
				JSONObject obj = jsonarray.getJSONObject(item_i);
				String id = (String) obj.get("id");
				String reportUser = (String) obj.get("reportUser");
				String reportTime = (String) obj.get("reportTime");
				String areaId = (String) obj.get("areaId");
				String hiddenTroubleType = (String) obj.get("hiddenTroubleType");
				String isImportant = (String) obj.get("isImportant");
				String majorType = (String) obj.get("majorType");
				String checkPoint = (String) obj.get("checkPoint");
				String longitude = (String) obj.get("longitude");
				String latitude = (String) obj.get("latitude");
				String checkUser = (String) obj.get("checkUser");
				String checkUserDept = (String) obj.get("checkUserDept");
				String phone = (String) obj.get("phone");
				String email = (String) obj.get("email");
				String processStatus = (String) obj.get("processStatus");
				String processUser = (String) obj.get("processUser");
				String handlTime = (String) obj.get("handlTime");
				String handleMsg = (String) obj.get("handleMsg");
				String remark = (String) obj.get("remark");
				
				try {
					String url = "/partner/inspect/inspectTasks.do?method=doCheckByMobile";
					request.setAttribute("id", id);
				} catch (Exception e) {
					throw e;
				}
				System.out.println(id);
			}
		}
		response.getOutputStream().write("success".getBytes("UTF-8"));
		/**
		try{
			Map<String,String> messageMap = new HashMap<String,String>();
			
			HiddenTroubleService htService = (HiddenTroubleService) this
			.getHiddenTroubleServiceBean();
			Map requestMap = request.getParameterMap();
			
			HiddenTrouble ht = new HiddenTrouble();
			BeanUtils.populate(ht, requestMap);
			
			TawSystemSessionForm sessionform = this.getUser(request);
			String userId = sessionform.getUserid();
			String reportTime = CommonUtils.toEomsStandardDate(new Date());
			
//			//设置巡检员和其代维公司
//			PartnerUserMgr pum = (PartnerUserMgr)ApplicationContextHolder.getInstance().getBean("partnerUserMgr");
//			List puList = pum.getPartnerUsers(" and userId='"+ht.getCheckUser()+"'");
//			
//			if(puList != null && puList.size()>0) {
//				PartnerUser pu = (PartnerUser)puList.get(0);
//				PartnerDeptMgr pdm = (PartnerDeptMgr)ApplicationContextHolder.getInstance().getBean("partnerDeptMgr");
//				PartnerDept pd = (PartnerDept)pdm.getPartnerDept(pu.getPartnerid());
//				if(pd != null) {
//					ht.setCheckUserDept(pd.getId());
//				}
//			} else {
//				messageMap.put("infor", "选择的人员不是代维人员，请选择代维人员！");
//				this.responseJSONMessage(response, messageMap, false);
//				return null;
//			}
			
			ht.setReportUser(userId);
			ht.setReportTime(reportTime);
			ht.setDeleted(HiddenTroubleConst.NOT_DELETE);
			ht.setProcessStatus(HiddenTroubleConst.NOT_PROCESS);
			
			htService.save(ht);
			
			messageMap.put("infor", "添加成功！");
			this.responseJSONMessage(response, messageMap, true);

		} catch (RuntimeException e) {
			Map<String,String> messageMap = new HashMap<String,String>();
			e.printStackTrace();
			messageMap.put("infor", "添加出错！");
			this.responseJSONMessage(response, messageMap, false);
		} finally {
//			return null;
		}
		*/
	}
	@SuppressWarnings({ "unused", "unchecked","static-access" })
	private void responseJSONMessage(HttpServletResponse response,Map<String,String> messageMap,boolean success)throws Exception {
		response.setCharacterEncoding("utf-8");
		if(success) {
			messageMap.put("success", "true");
			messageMap.put("msg", "ok");
		} else {
			messageMap.put("success", "false");
			messageMap.put("msg", "failed");
			
		}
		response.getWriter().write(
				new Gson()
						.toJson(messageMap));
	}
	public HiddenTroubleService getHiddenTroubleServiceBean() {
		String source = HiddenTroubleService.class.getSimpleName();
		return (HiddenTroubleService) getBean(source.substring(0, 1)
				.toLowerCase().concat(source.substring(1)));
	}
}
