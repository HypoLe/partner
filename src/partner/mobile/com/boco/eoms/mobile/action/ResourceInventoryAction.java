package com.boco.eoms.mobile.action;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.Clob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oracle.sql.CLOB;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;
import org.hibernate.Hibernate;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.lob.SerializableClob;
import org.joda.time.LocalDate;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.dict.exceptions.DictDAOException;
import com.boco.eoms.commons.system.dict.service.ITawSystemDictTypeManager;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.commons.system.user.service.ITawSystemUserManager;
import com.boco.eoms.deviceManagement.common.pojo.EomsSearch;
import com.boco.eoms.deviceManagement.common.utils.CommonConstants;
import com.boco.eoms.deviceManagement.common.utils.CommonSqlHelper;
import com.boco.eoms.mobile.form.InspectPlanMainForm;
import com.boco.eoms.mobile.service.InspectPlanMgr;
import com.boco.eoms.mobile.util.AndroidPropertiesUtils;
import com.boco.eoms.mobile.util.MobileCommonUtils;
import com.boco.eoms.mobile.util.MobileConstants;
import com.boco.eoms.mobile.util.PartnerUtil;
import com.boco.eoms.partner.baseinfo.mgr.PartnerDeptMgr;
import com.boco.eoms.partner.baseinfo.mgr.PartnerUserMgr;
import com.boco.eoms.partner.baseinfo.model.PartnerDept;
import com.boco.eoms.partner.baseinfo.model.PartnerUser;
import com.boco.eoms.partner.deviceInspect.model.PnrInspectMapping;
import com.boco.eoms.partner.deviceInspect.model.PnrInspectTaskLink;
import com.boco.eoms.partner.deviceInspect.service.PnrInspectMappingService;
import com.boco.eoms.partner.deviceInspect.service.PnrInspectTaskLinkService;
import com.boco.eoms.partner.inspect.dao.hibernate.PnrInspectTaskFileDaoHibernate;
import com.boco.eoms.partner.inspect.dao.jdbc.InspectPlanDaoJdbc;
import com.boco.eoms.partner.inspect.mgr.IErrorDistanceMgr;
import com.boco.eoms.partner.inspect.mgr.IInspectLineTrackMgr;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanItemMgr;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanMainMgr;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanResMgr;
import com.boco.eoms.partner.inspect.mgr.IInspectSpotcheckMgr;
import com.boco.eoms.partner.inspect.mgr.IPnrInspectTaskFileMgr;
import com.boco.eoms.partner.inspect.model.ErrorDistance;
import com.boco.eoms.partner.inspect.model.InspectPlanItem;
import com.boco.eoms.partner.inspect.model.InspectPlanMain;
import com.boco.eoms.partner.inspect.model.InspectPlanRes;
import com.boco.eoms.partner.inspect.model.PnrInspectTaskFile;
import com.boco.eoms.partner.inspect.model.SpotcheckItem;
import com.boco.eoms.partner.inspect.model.SpotcheckRes;
import com.boco.eoms.partner.inspect.model.SpotcheckTemplate;
import com.boco.eoms.partner.netresource.service.IEomsService;
import com.boco.eoms.partner.process.util.CommonUtils;
import com.boco.eoms.partner.res.dao.IPnrResConfigDaoJdbc;
import com.boco.eoms.partner.res.dao.jdbc.PnrResConfigDaoJdbc;
import com.boco.eoms.partner.res.model.PnrLocationCycle;
import com.google.common.collect.Maps;
import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;
public final class ResourceInventoryAction extends BaseAction {
	
	/**
	 * 返回类别、级别
	 * @param request
	 * @param response
	 */
	
	public void getTypeAndLevel(ActionMapping mapping, ActionForm form,HttpServletRequest request,
			HttpServletResponse response) {
		
		
		String[][] type = {{"请选择","资源清查"},{"","112251101"}};
		
		String[][][] level ={{{"请选择 "},{""}},{{"VIP","A类","B类","C类"},{"11225110101","11225110102","11225110103","11225110104"}}};
        Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("type", type);
		map.put("level",level);
		JSONObject jsonObject = JSONObject.fromObject(map);
	//	System.out.println("返回数组---------"+jsonObject.toString());
		MobileCommonUtils.responseWrite(response, jsonObject.toString(), "UTF-8");
		
	}
	/**
	 * 查询站点
	 * @param request
	 * @param response
	 */
	
	public void listInspectPlanMain(ActionMapping mapping, ActionForm form,HttpServletRequest request,
			HttpServletResponse response) {
		String userId = this.getUserId(request);
		String json = request.getParameter("json");
//		System.out.println(json);			
		JSONArray array = new JSONArray("["+json+"]");
		JSONObject obj = array.getJSONObject(0);
		String str = obj.get("queryCondition").toString();
		
		JSONArray array2 = new JSONArray(str);
		JSONObject queryJsonObj = array2.getJSONObject(0);
		String currentPage = queryJsonObj.get("currentPage")+"";
		IInspectPlanMainMgr inspectPlanMainMgr = (IInspectPlanMainMgr) ApplicationContextHolder.getInstance().getBean("inspectPlanMainMgr");
		ITawSystemUserManager systemUserManager = (ITawSystemUserManager) ApplicationContextHolder.getInstance().getBean("ItawSystemUserManagerFlush");
		Search search = new Search();	
		search.setFirstResult((Integer.parseInt(currentPage)-1) * CommonConstants.PAGE_SIZE10);
		//查询权限
		
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		PartnerUserMgr  partnerUserMgr = (PartnerUserMgr)getBean("partnerUserMgr");
		PartnerUser user = partnerUserMgr.getPartnerUserByUserId(userId);
		
		ITawSystemDeptManager mgr = (ITawSystemDeptManager)getBean("ItawSystemDeptManager");
		ITawSystemDictTypeManager dao = (ITawSystemDictTypeManager) ApplicationContextHolder.getInstance().getBean("ItawSystemDictTypeManager");
		
		PartnerDeptMgr  partnerDeptMgr;
		if(null != user){//不为空说明是代维公司人查看，默认为只能查看自己部门以及其下部门的，所以应该是deptMagId like
			String deptMagId = sessionForm.getDeptid();
			
			if(deptMagId.startsWith("201")&&deptMagId.length()<=7){
				partnerDeptMgr= (PartnerDeptMgr)getBean("partnerDeptMgr");
				
				TawSystemDept dept = mgr.getDeptinfobydeptid(sessionForm.getDeptid(), "0");
				String areaid = dept.getAreaid();
				List partnerList = partnerDeptMgr.getPartnerDepts(" and countyId like '"+areaid+"%'");
				List<String> deptMagIdList = new ArrayList<String>();
				for(int i=0;partnerList!=null && i<partnerList.size();i++){
					PartnerDept partnerDept = (PartnerDept)partnerList.get(i);					
					String deptMagIddw= StaticMethod.nullObject2String(partnerDept.getDeptMagId());
					if(!deptMagIddw.equals("")){
						deptMagIdList.add(deptMagIddw);
					}
				}
				
				if(deptMagIdList!=null && deptMagIdList.isEmpty()==false){
					search.addFilterIn("deptMagId", deptMagIdList);
				}else{//如果没有管理代维公司，则不允许看该界面
					MobileCommonUtils.responseWrite(response, "", "UTF-8");
				}
				
			}else{
				
				if(deptMagId.length()>8){
					search.addFilterLike("deptMagId", deptMagId.substring(0, deptMagId.length()-2)+ "%");
				}else{
					search.addFilterLike("deptMagId", deptMagId+ "%");
				}
			}
			
		}else if(!"admin".equals(userId)){//如果不是代维公司人员，则应该根据用户所属部门的所属地域来查看巡检任务，所以应该是根据areaId like
			
			TawSystemDept dept = mgr.getDeptinfobydeptid(sessionForm.getDeptid(), "0");
			String areaid = dept.getAreaid();
			partnerDeptMgr = (PartnerDeptMgr)getBean("partnerDeptMgr");
			List partnerList = partnerDeptMgr.getPartnerDepts(" and countyId like '"+areaid+"%'");
			List<String> deptMagIdList = new ArrayList<String>();
			
			for(int i=0;partnerList!=null && i<partnerList.size();i++){
				PartnerDept partnerDept = (PartnerDept)partnerList.get(i);
				
				String deptMagId = StaticMethod.nullObject2String(partnerDept.getDeptMagId());
				if(!deptMagId.equals("")){
					deptMagIdList.add(deptMagId);
				}
			}
			if(deptMagIdList!=null && deptMagIdList.isEmpty()==false){
				search.addFilterIn("deptMagId", deptMagIdList);
			}else{//如果没有管理代维公司，则不允许看该界面
				MobileCommonUtils.responseWrite(response, "", "UTF-8");
			}
		}
		String currentMonth = request.getParameter("currentMonth");
		if(StringUtils.isEmpty(currentMonth)){
			LocalDate date = new LocalDate();
			search.addFilterEqual("year", date.getYear());
			search.addFilterEqual("month", date.getMonthOfYear());
		}
		search.addFilterEqual("status", "1");
		search.addFilterEqual("approveStatus", "1");
//		search.setMaxResults(CommonConstants.PAGE_SIZE10);
		
		search.addSortDesc("createTime");
		SearchResult<InspectPlanMain> searchResult = inspectPlanMainMgr.searchAndCount(search);
		int count = searchResult.getTotalCount();
		List<InspectPlanMain> returnList = searchResult.getResult();
		InspectPlanMainForm tempFrom ;
		List<InspectPlanMainForm> returnFormList = new ArrayList<InspectPlanMainForm>();
		for(int i = 0;i<returnList.size();i++){
			tempFrom = new InspectPlanMainForm();
			try {
				BeanUtils.copyProperties(tempFrom, returnList.get(i));
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
			
			String deptMagName = "";
			String specialtyName = "";
			String creator = "";
			try {
				deptMagName = mgr.id2Name(tempFrom.getDeptMagId());
				specialtyName = dao.id2Name(tempFrom.getSpecialty());
				creator = systemUserManager.id2Name(tempFrom.getCreator());
			} catch (DictDAOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			tempFrom.setDeptMagName(deptMagName);
			tempFrom.setSpecialtyName(specialtyName);
			tempFrom.setCreator(creator);
			returnFormList.add(tempFrom);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("returnList", returnFormList);
		map.put("count",count);
		JSONObject jsonObject = JSONObject.fromObject(map);
		System.out.println(jsonObject.toString());
		MobileCommonUtils.responseWrite(response, jsonObject.toString(), "UTF-8");
		
	}
	/**
	 * 查询巡检资源	2--改
	 * 
	 * @param request
	 * @param response
	 */
	public void listInspectPlanRes(ActionMapping mapping, ActionForm form,HttpServletRequest request,
			HttpServletResponse response) {
		InspectPlanMgr mgr = (InspectPlanMgr) this.getBean("inspectPlanMgrImpl");
		try {
			mgr.listInspectPlanRes(request, response, this.getUserId(request), this.getUser(request).getDeptid());
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

    /**
     * 资源查询 - 无计划planMainid   巡检资源
     */


    public void listInspectBarRes(ActionMapping mapping, ActionForm form,HttpServletRequest request,
                                   HttpServletResponse response) {
        InspectPlanMgr mgr = (InspectPlanMgr) this.getBean("inspectPlanMgrImpl");
        try {
            mgr.listInspectBarRes(request, response, this.getUserId(request), this.getUser(request).getDeptid());
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * 资源查询 - 2013-08-30   config
     */
    
    public void listResource(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
    								HttpServletResponse response){
    	
    	InspectPlanMgr mgr = (InspectPlanMgr) this.getBean("inspectPlanMgrImpl");
    	try {
			mgr.listResource(request, response, this.getUserId(request), this.getUser(request).getDeptid());
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    public void updateResource(ActionMapping mapping, ActionForm form, HttpServletRequest request, 
			HttpServletResponse response){
    	//PnrResConfigDao resDao = (PnrResConfigDao) this.getBean("pnrResConfigDaoJdbc");
    	Object o = this.getBean("pnrResConfigDaoJdbc");
    	Object ob = this.getBean("inspectPlanDaoJdbc");
    	
    	IPnrResConfigDaoJdbc resDao = (IPnrResConfigDaoJdbc) o;
    	
    	InspectPlanDaoJdbc planDao = (InspectPlanDaoJdbc)ob;
		String res_longitude = request.getParameter("res_longitude"); // 经度
		String res_latitude = request.getParameter("res_latitude"); // 纬度
		String res_id = request.getParameter("res_id"); // 资源id
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String nowTime = sdf.format(new Date());
		Map<String, Object> returnMap = new HashMap<String, Object>();
		if("".equals(res_longitude)){
			returnMap.put("success", false);
			returnMap.put("reason", "noGps");
		}else{
			String sql = "UPDATE pnr_res_config SET res_latitude = "+res_latitude+",res_longitude = "+res_longitude+", creator = '"+this.getUserId(request)+"' WHERE id = '"+res_id+"'";
			String planSql = "update pnr_inspect_plan_res pr set pr.res_longitude = " +res_longitude+
					" , pr.res_latitude = " +res_latitude+",pr.creator='"+this.getUserId(request)+"'"+
					" where pr.res_cfg_id='"+res_id+"'" +
					" and pr.cycle_start_time<=to_date('"+nowTime+"','yyyy/mm/dd hh24:mi:ss')" +
					" and pr.cycle_end_time >=to_date('"+nowTime+"','yyyy/mm/dd hh24:mi:ss')" +
					" and pr.inspect_state = 0";
			int count = resDao.updateResource(sql);
					planDao.updateResource(planSql);
			if(count > 0){
				returnMap.put("success", true);
			}else{
				returnMap.put("success", false);
				returnMap.put("reason", "updateFailure");
			}
		}
		returnMap.put("type", "updateRes");
		JSONObject returnJsonObject = JSONObject.fromObject(returnMap);
		MobileCommonUtils.responseWrite(response, returnJsonObject.toString(),"UTF-8");
		
}
    
    



	/**
	 * 查询巡检项	3--改
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void listInspectPlanItem(ActionMapping mapping, ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		InspectPlanMgr mgr = (InspectPlanMgr) this.getBean("inspectPlanMgrImpl");
		mgr.listInspectPlanItem(request, response, this.getUserId(request));
	}
	
	/**
	 * 查询线路段
	 */
	public void getTransLineList(ActionMapping mapping, ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		InspectPlanMgr mgr = (InspectPlanMgr) this.getBean("inspectPlanMgrImpl");
		mgr.getTransLineList(request, response);
	}
	/**
	 * 查询光缆点
	 */
	public void getTransLinePointList(ActionMapping mapping, ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		InspectPlanMgr mgr = (InspectPlanMgr) this.getBean("inspectPlanMgrImpl");
		mgr.getTransLinePointList(request, response);
	}
	

	public void goToDeviceInspectList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException, Exception{

		PnrInspectTaskLinkService pitls = (PnrInspectTaskLinkService)getBean("pnrInspectTaskLinkService");
		String planResId  = StaticMethod.null2String(request.getParameter("planResId"));
		String getType  = StaticMethod.null2String(request.getParameter("getType"));
		String city  = StaticMethod.null2String(request.getParameter("city"));
		String country  = StaticMethod.null2String(request.getParameter("country"));
		String pageIndex  = StaticMethod.null2String(request.getParameter("pageIndex"));
		Search search = new Search();
//		search.setFirstResult((Integer.parseInt(pageIndex)-1) * CommonConstants.PAGE_SIZE);
//		search.setMaxResults(CommonConstants.PAGE_SIZE);
//		search = CommonUtils.getSqlFromRequestMap(request, search);
		search.addFilterEqual("planResId", planResId);
		
		SearchResult searchResult = pitls.searchAndCount(search);
		List<PnrInspectTaskLink> deviceInspectList = searchResult.getResult();
		//所做的是让手机显示 所属网络，所属设备---start
		List<PnrInspectTaskLink> deviceInspectFinal  = new ArrayList();		
		PnrInspectMappingService pnrInspectMappingService = (PnrInspectMappingService)getBean("pnrInspectMappingService");
		ITawSystemDictTypeManager sys = (ITawSystemDictTypeManager)getBean("ItawSystemDictTypeManager");

		int dsize =  deviceInspectList.size();
		String network ="";
		for (int i =0;i<dsize;i++){
			PnrInspectTaskLink pit=deviceInspectList.get(i);
			if(deviceInspectList.get(i).getInspectMappingId()!=null){
				PnrInspectMapping p=pnrInspectMappingService.find(deviceInspectList.get(i).getInspectMappingId());
				network=sys.id2Name(p.getNetresFieldValue());
			}
			pit.setNetWork(network);
			deviceInspectFinal.add(pit);
		}
		//所做的是让手机显示 所属网络，所属设备---end
		
		Map<String,Object> returnMap = new HashMap<String, Object>();
		returnMap.put("pagesize", CommonConstants.PAGE_SIZE);
		returnMap.put("size", searchResult.getTotalCount());
		returnMap.put("datas", deviceInspectFinal);
		if(!"".equals(city)&&!"".equals(country)||"line".equals(getType)){//如果是线路巡检使用则查出坐标上传时间间隔
			String carInterval="",walkInterval="";
			if(!"".equals(city)&&!"".equals(country)){
				IEomsService eomsService = (IEomsService) this.getBean("eomsService");
				EomsSearch eomsSearch = new EomsSearch();
				eomsSearch.setSearchClass(PnrLocationCycle.class);
				eomsSearch.addFilterEqual("city", city);
				eomsSearch.addFilterEqual("country", country);
				eomsSearch.addFilterEqual("executeType", "1123201");//先查询车巡的时间间隔
				SearchResult<PnrLocationCycle>  result = eomsService.searchAndCount(eomsSearch);
				List<PnrLocationCycle> lsit = result.getResult();
				if(!lsit.isEmpty()){
					String reportInterval = lsit.get(0).getReportInterval();
					String executeType = lsit.get(0).getExecuteType();
					carInterval = executeType+"|"+reportInterval;
				}
				
				eomsSearch = new EomsSearch();
				eomsSearch.setSearchClass(PnrLocationCycle.class);
				eomsSearch.addFilterEqual("city", city);
				eomsSearch.addFilterEqual("country", country);
				eomsSearch.addFilterEqual("executeType", "1123202");//查询出步巡的时间间隔
				result = eomsService.searchAndCount(eomsSearch);
				lsit = result.getResult();
				if(!lsit.isEmpty()){
					String reportInterval = lsit.get(0).getReportInterval();
					String executeType = lsit.get(0).getExecuteType();
					walkInterval = executeType+"|"+reportInterval;
				}
				if("".equals(carInterval)){
					carInterval = "1123201|10";//默认车巡10秒
				}
				if("".equals(walkInterval)){
					walkInterval = "1123202|30";//默认步巡30步
				}
			}else{
				carInterval = "1123201|10";//默认车巡10秒
				walkInterval = "1123202|30";//默认步巡30步
			}
			
			returnMap.put("interval", carInterval+"#"+walkInterval);
		}
		JSONArray returnArray = JSONArray.fromObject(returnMap);
		String returnStr = returnArray.toString();
		System.out.println("InspectPlanExecuteAction--409row------"+returnStr);
		MobileCommonUtils.responseWrite(response, returnStr, "UTF-8");
		return;
	}

	
	/**
	 *  资源查询- 设备查询 2013-09-03  config 
	 */
	public void goToDeviceResourceList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException, Exception{

		PnrInspectTaskLinkService pitls = (PnrInspectTaskLinkService)getBean("pnrInspectTaskLinkService");
//		String planResId  = StaticMethod.null2String(request.getParameter("planResId"));
		String inspectId = StaticMethod.null2String(request.getParameter("inspectId"));
		String getType  = StaticMethod.null2String(request.getParameter("getType"));
		String city  = StaticMethod.null2String(request.getParameter("city"));
		String country  = StaticMethod.null2String(request.getParameter("country"));
		String pageIndex  = StaticMethod.null2String(request.getParameter("pageIndex"));
		Search search = new Search();
//		search.addFilterEqual("planResId", planResId);
		search.addFilterEqual("inspectId", inspectId);
		SearchResult searchResult = pitls.searchAndCount(search);
		List<PnrInspectTaskLink> deviceInspectList = searchResult.getResult();
		//所做的是让手机显示 所属网络，所属设备---start
		List<PnrInspectTaskLink> deviceInspectFinal  = new ArrayList();		
		PnrInspectMappingService pnrInspectMappingService = (PnrInspectMappingService)getBean("pnrInspectMappingService");
		ITawSystemDictTypeManager sys = (ITawSystemDictTypeManager)getBean("ItawSystemDictTypeManager");

		int dsize =  deviceInspectList.size();
		String network ="";
		for (int i =0;i<dsize;i++){
			PnrInspectTaskLink pit=deviceInspectList.get(i);
			if(deviceInspectList.get(i).getInspectMappingId()!=null){
				PnrInspectMapping p=pnrInspectMappingService.find(deviceInspectList.get(i).getInspectMappingId());
				network=sys.id2Name(p.getNetresFieldValue());
			}
			pit.setNetWork(network);
			deviceInspectFinal.add(pit);
		}
		//所做的是让手机显示 所属网络，所属设备---end
		
		Map<String,Object> returnMap = new HashMap<String, Object>();
		returnMap.put("pagesize", CommonConstants.PAGE_SIZE);
		returnMap.put("size", searchResult.getTotalCount());
		returnMap.put("datas", deviceInspectFinal);
		if(!"".equals(city)&&!"".equals(country)||"line".equals(getType)){//如果是线路巡检使用则查出坐标上传时间间隔
			String carInterval="",walkInterval="";
			if(!"".equals(city)&&!"".equals(country)){
				IEomsService eomsService = (IEomsService) this.getBean("eomsService");
				EomsSearch eomsSearch = new EomsSearch();
				eomsSearch.setSearchClass(PnrLocationCycle.class);
				eomsSearch.addFilterEqual("city", city);
				eomsSearch.addFilterEqual("country", country);
				eomsSearch.addFilterEqual("executeType", "1123201");//先查询车巡的时间间隔
				SearchResult<PnrLocationCycle>  result = eomsService.searchAndCount(eomsSearch);
				List<PnrLocationCycle> lsit = result.getResult();
				if(!lsit.isEmpty()){
					String reportInterval = lsit.get(0).getReportInterval();
					String executeType = lsit.get(0).getExecuteType();
					carInterval = executeType+"|"+reportInterval;
				}
				
				eomsSearch = new EomsSearch();
				eomsSearch.setSearchClass(PnrLocationCycle.class);
				eomsSearch.addFilterEqual("city", city);
				eomsSearch.addFilterEqual("country", country);
				eomsSearch.addFilterEqual("executeType", "1123202");//查询出步巡的时间间隔
				result = eomsService.searchAndCount(eomsSearch);
				lsit = result.getResult();
				if(!lsit.isEmpty()){
					String reportInterval = lsit.get(0).getReportInterval();
					String executeType = lsit.get(0).getExecuteType();
					walkInterval = executeType+"|"+reportInterval;
				}
				if("".equals(carInterval)){
					carInterval = "1123201|10";//默认车巡10秒
				}
				if("".equals(walkInterval)){
					walkInterval = "1123202|30";//默认步巡30步
				}
			}else{
				carInterval = "1123201|10";//默认车巡10秒
				walkInterval = "1123202|30";//默认步巡30步
			}
			
			returnMap.put("interval", carInterval+"#"+walkInterval);
		}
		JSONArray returnArray = JSONArray.fromObject(returnMap);
		String returnStr = returnArray.toString();
		System.out.println("InspectPlanExecuteAction--499row------"+returnStr);
		MobileCommonUtils.responseWrite(response, returnStr, "UTF-8");
		return;
	}
	
	
	
	
	
	

    /**
     *  设备查询 - 设备条码核对
     */
    public void goToDeviceCheckInspectList(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response) throws IOException, Exception{

        PnrInspectTaskLinkService pitls = (PnrInspectTaskLinkService)getBean("pnrInspectTaskLinkService");
        String planResId  = StaticMethod.null2String(request.getParameter("planResId"));
        List<PnrInspectTaskLink> deviceInspectFinal  = new ArrayList<PnrInspectTaskLink>();
        SearchResult searchResult = new SearchResult();
        List<PnrInspectTaskLink> deviceInspectList = new ArrayList<PnrInspectTaskLink>();
        String codePrototype = request.getParameter("codePrototype");
        String[] barPrototype = codePrototype.split("\\|");        // 根据手机资源获取到的 设备 resBar
        String codeResult = request.getParameter("code");
        String[] barResult = codeResult.split("\\|");          // 现场扫描获取到的 设备resBar
        int index = barPrototype.length;
        int t = 0;
        int index2 = 0;
        String[] entire =  new String[barPrototype.length+barResult.length];
        for(int bp=0;bp< barPrototype.length; bp++){
           entire[bp] = barPrototype[bp];
        }
        String[] result2 = new String[barPrototype.length+barResult.length];
        String[] result3 = new String[barPrototype.length+barResult.length];
        for(int br=0;br<barResult.length;br++){
            if(!has(barPrototype,barResult[br])){
               entire[index] = barResult[br];
               result3[index2] = barResult[br];
               index++;
               index2++;
            }else{
                result2[t] = barResult[br];
                t++;
            }
        }
        String[] result = new String[index];     //   现场  +  数据   的 设备resBar
        for(int n=0;n<index;n++){
            result[n] = entire[n];
            // 根据每一个 条码去查询
         /*   Search search = new Search();
            search.addFilterEqual("resBar", result[n]);
            search.addFilterEqual("planResId", planResId);
            searchResult = pitls.searchAndCount(search);
            deviceInspectList = searchResult.getResult();
            if(deviceInspectList.isEmpty()|| null==deviceInspectList){
                PnrInspectTaskLink pit   = new PnrInspectTaskLink();
                pit.setBarSite(result[n]);
                pit.setBarCodeSite(planResId);
                pitls.save(pit);
                deviceInspectFinal.add(pit);
            } else{
                int dsize =  deviceInspectList.size();
                for (int j =0;j<dsize;j++){
                    PnrInspectTaskLink pit  = deviceInspectList.get(j);
              //     pit.setBarState(planResId+"3");
                    pitls.save(pit);
                    deviceInspectFinal.add(pit);
                }*/
            //   }

        }
        String[] poor = new String[entire.length-result.length];     //  现场 与 数据 共有的    设备resBar
        for(int n=0;n<entire.length-result.length;n++){
            poor[n] = result2[n];
            Search search = new Search();
            search.addFilterEqual("resBar", poor[n]);
            search.addFilterEqual("planResId", planResId);
            searchResult = pitls.searchAndCount(search);
            deviceInspectList = searchResult.getResult();
            int dsize =  deviceInspectList.size();
            for (int j =0;j<dsize;j++){
                PnrInspectTaskLink pit=deviceInspectList.get(j);
                pit.setBarState(planResId+"2");
                pit.setBarSite(planResId+"3");
                pitls.save(pit);
                deviceInspectFinal.add(pit);
            }

        }

        String[] uniqueSite = new String[index2];
        for(int i =0; i< index2 ; i++){
            uniqueSite[i] = result3[i];
        }


   /*     String[] uniqueSite = new String[barResult.length - poor.length];      // 现场独有 设备resBar
        int t2 =0;
        for(int br=0; br<barResult.length; br++){
            if(!has(poor ,barResult[br])){
                uniqueSite[t2] = barResult[br];
                t2++;
            }
        }
        for(int i=0;i <t2;i++){
            Search search = new Search();
           search.addFilterEqual("resBar", uniqueSite[i]);
          *//*  search.addFilterEqual("planResId", planResId);*//*

            searchResult = pitls.searchAndCount(search);
            deviceInspectList = searchResult.getResult();
            int dsize =  deviceInspectList.size();
            for (int j =0;j<dsize;j++){
                PnrInspectTaskLink pit=deviceInspectList.get(j);
                pit.setBarState(planResId+"0");
                pitls.save(pit);
                deviceInspectFinal.add(pit);
            }
        }*/

       String[] uniqueData =  new String[barPrototype.length - poor.length];       // 数据独有 设备resBar
       int t3 = 0;
       for(int bp=0;bp<barPrototype.length;bp++){
           if(!has(poor,entire[bp])){
               uniqueData[t3] = entire[bp];
               t3++;
           }
       }
       for(int i=0;i<t3;i++){
           Search search = new Search();
           search.addFilterEqual("resBar", uniqueData[i]);
           search.addFilterEqual("planResId", planResId);
           searchResult = pitls.searchAndCount(search);
           deviceInspectList = searchResult.getResult();
           int dsize =  deviceInspectList.size();
           for (int j =0;j<dsize;j++){
               PnrInspectTaskLink pit=deviceInspectList.get(j);
               pit.setBarState(planResId+"1");
               pit.setBarSite(planResId+"3");
               pitls.save(pit);
               deviceInspectFinal.add(pit);
           }
       }

    /*    String resBar = "";
        for(int k=0 ; k <barResult.length; k++){                          // 现场 resBar 循环查询数据库    开始
            resBar = barResult[k];
            Search search = new Search();
            search.addFilterEqual("resBar", resBar);
            search.addFilterEqual("planResId", planResId);
            searchResult = pitls.searchAndCount(search);
            deviceInspectList = searchResult.getResult();
            int dsize =  deviceInspectList.size();
            for (int j =0;j<dsize;j++){
                PnrInspectTaskLink pit=deviceInspectList.get(j);
                deviceInspectFinal.add(pit);
            }
        }        */                                                             // 结束

        Map<String,Object> returnMap = new HashMap<String, Object>();
        returnMap.put("pagesize", CommonConstants.PAGE_SIZE);
        returnMap.put("size", searchResult.getTotalCount());
        returnMap.put("barSite",Arrays.asList(uniqueSite) );
        returnMap.put("datas", deviceInspectFinal);
        JSONArray returnArray = JSONArray.fromObject(returnMap);
        String returnStr = returnArray.toString();
        System.out.println("InspectPlanExecuteAction--528row------"+returnStr);
        MobileCommonUtils.responseWrite(response, returnStr, "UTF-8");
        return;
    }

    
    /**
     * 条码核对- 数组比对
      */
    public static boolean has(String[] barPrototype, String barResult){
          for(int k = 0; k<barPrototype.length;k++ ){
              if(barResult.equals(barPrototype[k])){
                  barPrototype[k] ="";
                  return true;
              }
          }
        return false;
    }


    /**
     *  设备核对- 查询结果
     */
    public void goToDeviceCheckResultList(ActionMapping mapping, ActionForm form,
                                      HttpServletRequest request, HttpServletResponse response) throws IOException, Exception{

        PnrInspectTaskLinkService pitls = (PnrInspectTaskLinkService)getBean("pnrInspectTaskLinkService");
        String planResId  = StaticMethod.null2String(request.getParameter("planResId"));
        String  barState   =  StaticMethod.null2String(request.getParameter("barState"));
        String getType  = StaticMethod.null2String(request.getParameter("getType"));
        String city  = StaticMethod.null2String(request.getParameter("city"));
        String country  = StaticMethod.null2String(request.getParameter("country"));
        String pageIndex  = StaticMethod.null2String(request.getParameter("pageIndex"));
        Search search = new Search();

      /*  search.addFilterEqual("barSite",uniqueSite+","+planResId);*/


//		search.setFirstResult((Integer.parseInt(pageIndex)-1) * CommonConstants.PAGE_SIZE);
//		search.setMaxResults(CommonConstants.PAGE_SIZE);
//		search = CommonUtils.getSqlFromRequestMap(request, search);
      /*  if(null!= planResId && !"".equals(planResId)){
        search.addFilterEqual("planResId", planResId);*/
        if(barState.endsWith("3")){
            search.addFilterEqual("planResId", planResId);
            search.addFilterOr(Filter.equal("barState",planResId+1),Filter.equal("barState",planResId+2));
              //  search.addFilterEqual("barState", planResId+1);
        }
      /*  }else{*/
       /* if(barState.endsWith("0")){
            search.addFilterEqual("planResId", planResId);
            search.addFilterEqual("barCodeSite", planResId);
        }*/else{
            search.addFilterEqual("planResId", planResId);
            search.addFilterEqual("barState", barState);
        }
     /*   }*/
        SearchResult searchResult = pitls.searchAndCount(search);
        List<PnrInspectTaskLink> deviceInspectList = searchResult.getResult();
        //所做的是让手机显示 所属网络，所属设备---start
       /* List<PnrInspectTaskLink> deviceInspectFinal  = new ArrayList();
        PnrInspectMappingService pnrInspectMappingService = (PnrInspectMappingService)getBean("pnrInspectMappingService");
        ITawSystemDictTypeManager sys = (ITawSystemDictTypeManager)getBean("ItawSystemDictTypeManager");

        int dsize =  deviceInspectList.size();
        String network ="";
        for (int i =0;i<dsize;i++){
            PnrInspectTaskLink pit=deviceInspectList.get(i);
            if(deviceInspectList.get(i).getInspectMappingId()!=null){
                PnrInspectMapping p=pnrInspectMappingService.find(deviceInspectList.get(i).getInspectMappingId());
                network=sys.id2Name(p.getNetresFieldValue());
            }
            pit.setNetWork(network);
            deviceInspectFinal.add(pit);
        }   */
        //所做的是让手机显示 所属网络，所属设备---end

        Map<String,Object> returnMap = new HashMap<String, Object>();
        returnMap.put("pagesize", CommonConstants.PAGE_SIZE);
        returnMap.put("size", searchResult.getTotalCount());
        returnMap.put("datas", deviceInspectList);
        if(!"".equals(city)&&!"".equals(country)||"line".equals(getType)){//如果是线路巡检使用则查出坐标上传时间间隔
            String carInterval="",walkInterval="";
            if(!"".equals(city)&&!"".equals(country)){
                IEomsService eomsService = (IEomsService) this.getBean("eomsService");
                EomsSearch eomsSearch = new EomsSearch();
                eomsSearch.setSearchClass(PnrLocationCycle.class);
                eomsSearch.addFilterEqual("city", city);
                eomsSearch.addFilterEqual("country", country);
                eomsSearch.addFilterEqual("executeType", "1123201");//先查询车巡的时间间隔
                SearchResult<PnrLocationCycle>  result = eomsService.searchAndCount(eomsSearch);
                List<PnrLocationCycle> lsit = result.getResult();
                if(!lsit.isEmpty()){
                    String reportInterval = lsit.get(0).getReportInterval();
                    String executeType = lsit.get(0).getExecuteType();
                    carInterval = executeType+"|"+reportInterval;
                }

                eomsSearch = new EomsSearch();
                eomsSearch.setSearchClass(PnrLocationCycle.class);
                eomsSearch.addFilterEqual("city", city);
                eomsSearch.addFilterEqual("country", country);
                eomsSearch.addFilterEqual("executeType", "1123202");//查询出步巡的时间间隔
                result = eomsService.searchAndCount(eomsSearch);
                lsit = result.getResult();
                if(!lsit.isEmpty()){
                    String reportInterval = lsit.get(0).getReportInterval();
                    String executeType = lsit.get(0).getExecuteType();
                    walkInterval = executeType+"|"+reportInterval;
                }
                if("".equals(carInterval)){
                    carInterval = "1123201|10";//默认车巡10秒
                }
                if("".equals(walkInterval)){
                    walkInterval = "1123202|30";//默认步巡30步
                }
            }else{
                carInterval = "1123201|10";//默认车巡10秒
                walkInterval = "1123202|30";//默认步巡30步
            }

            returnMap.put("interval", carInterval+"#"+walkInterval);
        }
        JSONArray returnArray = JSONArray.fromObject(returnMap);
        String returnStr = returnArray.toString();
        System.out.println("InspectPlanExecuteAction--791row------"+returnStr);
        MobileCommonUtils.responseWrite(response, returnStr, "UTF-8");
        return;
    }


    
    
    /**
     *  资源查询-设备条码核对  config    2013-09-04
     */
    public void goToDeviceCheckResourceList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws IOException, Exception{

		PnrInspectTaskLinkService pitls = (PnrInspectTaskLinkService)getBean("pnrInspectTaskLinkService");
		String inspectId  = StaticMethod.null2String(request.getParameter("inspectId"));
		List<PnrInspectTaskLink> deviceInspectFinal  = new ArrayList<PnrInspectTaskLink>();
		SearchResult searchResult = new SearchResult();
		List<PnrInspectTaskLink> deviceInspectList = new ArrayList<PnrInspectTaskLink>();
		String codePrototype = request.getParameter("codePrototype");
		String[] barPrototype = codePrototype.split("\\|");        // 根据手机资源获取到的 设备 resBar
		String codeResult = request.getParameter("code");
		String[] barResult = codeResult.split("\\|");          // 现场扫描获取到的 设备resBar
		int index = barPrototype.length;
		int t = 0;
		int index2 = 0;
		String[] entire =  new String[barPrototype.length+barResult.length];
		for(int bp=0;bp< barPrototype.length; bp++){
			entire[bp] = barPrototype[bp];
		}
		String[] result2 = new String[barPrototype.length+barResult.length];
		String[] result3 = new String[barPrototype.length+barResult.length];
		for(int br=0;br<barResult.length;br++){
			if(!has(barPrototype,barResult[br])){
				entire[index] = barResult[br];
				result3[index2] = barResult[br];
				index++;
				index2++;
			}else{
				result2[t] = barResult[br];
				t++;
				}
		}
		String[] result = new String[index];     //   现场  +  数据   的 设备resBar
		for(int n=0;n<index;n++){
			result[n] = entire[n];
		}
		String[] poor = new String[entire.length-result.length];     //  现场 与 数据 共有的    设备resBar
		for(int n=0;n<entire.length-result.length;n++){
			poor[n] = result2[n];
			Search search = new Search();
			search.addFilterEqual("resBar", poor[n]);
			search.addFilterEqual("inspectId", inspectId);
			searchResult = pitls.searchAndCount(search);
			deviceInspectList = searchResult.getResult();
			int dsize =  deviceInspectList.size();
			for (int j =0;j<dsize;j++){
				PnrInspectTaskLink pit=deviceInspectList.get(j);
				pit.setBarState(inspectId+"2");
				pit.setBarSite(inspectId+"3");
				pitls.save(pit);
				deviceInspectFinal.add(pit);
			}
		
		}
		
		String[] uniqueSite = new String[index2];
		for(int i =0; i< index2 ; i++){
			uniqueSite[i] = result3[i];
		}
		
		String[] uniqueData =  new String[barPrototype.length - poor.length];       // 数据独有 设备resBar
		int t3 = 0;
		for(int bp=0;bp<barPrototype.length;bp++){
			if(!has(poor,entire[bp])){
				uniqueData[t3] = entire[bp];
				t3++;
			}
		}
		for(int i=0;i<t3;i++){
			Search search = new Search();
			search.addFilterEqual("resBar", uniqueData[i]);
			search.addFilterEqual("inspectId", inspectId);
			searchResult = pitls.searchAndCount(search);
			deviceInspectList = searchResult.getResult();
			int dsize =  deviceInspectList.size();
			for (int j =0;j<dsize;j++){
				PnrInspectTaskLink pit=deviceInspectList.get(j);
				pit.setBarState(inspectId+"1");
				pit.setBarSite(inspectId+"3");
				pitls.save(pit);
				deviceInspectFinal.add(pit);
			}
		}
		
		
		Map<String,Object> returnMap = new HashMap<String, Object>();
		returnMap.put("pagesize", CommonConstants.PAGE_SIZE);
		returnMap.put("size", searchResult.getTotalCount());
		returnMap.put("barSite",Arrays.asList(uniqueSite) );
		returnMap.put("datas", deviceInspectFinal);
		JSONArray returnArray = JSONArray.fromObject(returnMap);
		String returnStr = returnArray.toString();
		System.out.println("InspectPlanExecuteAction--895row------"+returnStr);
		MobileCommonUtils.responseWrite(response, returnStr, "UTF-8");
		return;
	}
    
    /**
     *    资源查询 - 设备核对- 查询结果   config  2013-09-04 
     */
    
    
    public void goToDeviceResourceResultList(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) throws IOException, Exception{

		PnrInspectTaskLinkService pitls = (PnrInspectTaskLinkService)getBean("pnrInspectTaskLinkService");
		String inspectId  = StaticMethod.null2String(request.getParameter("inspectId"));
		String  barState   =  StaticMethod.null2String(request.getParameter("barState"));
		String getType  = StaticMethod.null2String(request.getParameter("getType"));
		String city  = StaticMethod.null2String(request.getParameter("city"));
		String country  = StaticMethod.null2String(request.getParameter("country"));
		String pageIndex  = StaticMethod.null2String(request.getParameter("pageIndex"));
		Search search = new Search();
		if(barState.endsWith("3")){
			search.addFilterEqual("inspectId", inspectId);
			search.addFilterOr(Filter.equal("barState",inspectId+1),Filter.equal("barState",inspectId+2));
		}
		else{
			search.addFilterEqual("inspectId", inspectId);
			search.addFilterEqual("barState", barState);
		}
	
		SearchResult searchResult = pitls.searchAndCount(search);
		List<PnrInspectTaskLink> deviceInspectList = searchResult.getResult();
		
		Map<String,Object> returnMap = new HashMap<String, Object>();
		returnMap.put("pagesize", CommonConstants.PAGE_SIZE);
		returnMap.put("size", searchResult.getTotalCount());
		returnMap.put("datas", deviceInspectList);
		if(!"".equals(city)&&!"".equals(country)||"line".equals(getType)){//如果是线路巡检使用则查出坐标上传时间间隔
			String carInterval="",walkInterval="";
		if(!"".equals(city)&&!"".equals(country)){
			IEomsService eomsService = (IEomsService) this.getBean("eomsService");
			EomsSearch eomsSearch = new EomsSearch();
			eomsSearch.setSearchClass(PnrLocationCycle.class);
			eomsSearch.addFilterEqual("city", city);
			eomsSearch.addFilterEqual("country", country);
			eomsSearch.addFilterEqual("executeType", "1123201");//先查询车巡的时间间隔
			SearchResult<PnrLocationCycle>  result = eomsService.searchAndCount(eomsSearch);
			List<PnrLocationCycle> lsit = result.getResult();
			if(!lsit.isEmpty()){
				String reportInterval = lsit.get(0).getReportInterval();
				String executeType = lsit.get(0).getExecuteType();
				carInterval = executeType+"|"+reportInterval;
			}
			
			eomsSearch = new EomsSearch();
			eomsSearch.setSearchClass(PnrLocationCycle.class);
			eomsSearch.addFilterEqual("city", city);
			eomsSearch.addFilterEqual("country", country);
			eomsSearch.addFilterEqual("executeType", "1123202");//查询出步巡的时间间隔
			result = eomsService.searchAndCount(eomsSearch);
			lsit = result.getResult();
			if(!lsit.isEmpty()){
				String reportInterval = lsit.get(0).getReportInterval();
				String executeType = lsit.get(0).getExecuteType();
				walkInterval = executeType+"|"+reportInterval;
			}
			if("".equals(carInterval)){
				carInterval = "1123201|10";//默认车巡10秒
			}
			if("".equals(walkInterval)){
				walkInterval = "1123202|30";//默认步巡30步
			}
		}else{
			carInterval = "1123201|10";//默认车巡10秒
			walkInterval = "1123202|30";//默认步巡30步
		}
		
		returnMap.put("interval", carInterval+"#"+walkInterval);
		}
		JSONArray returnArray = JSONArray.fromObject(returnMap);
		String returnStr = returnArray.toString();
		System.out.println("InspectPlanExecuteAction--975row------"+returnStr);
		MobileCommonUtils.responseWrite(response, returnStr, "UTF-8");
		return;
	}

    
    
    
    
    /**
     * 获取某一设备的具体明细；
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @throws IOException
     * @throws Exception
     */
	/*public void goToDeviceDetail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException, Exception{
		
		ITawSystemDictTypeManager sys = (ITawSystemDictTypeManager)getBean("ItawSystemDictTypeManager");
		//设备表的名字
		String netresTableName  = StaticMethod.null2String(request.getParameter("netresTableName"));
		String netResId  = StaticMethod.null2String(request.getParameter("netResId"));
//		String netresTableName ="pnr_access_network_quipment";
//		String netResId="a08180804019231801401928ef550009";
		Search search = new Search();
		search.addFilterEqual("id", netResId);
		
		Map<String,Object> returnMap = new HashMap<String, Object>();
		returnMap.put("pagesize", CommonConstants.PAGE_SIZE);
		if(netresTableName.equals("pnr_bsbt_quipment")){
			BsBtQuipmentMgr bq = (BsBtQuipmentMgr)getBean("bsBtQuipmentService");
			
			SearchResult searchResult = bq.searchAndCount(search);
			List<BsBtQuipment> deviceList = searchResult.getResult();
			returnMap.put("size", searchResult.getTotalCount());
			if(deviceList.size()>0){		
				
				returnMap.put("name", deviceList.get(0).getDeviceNumber());
				returnMap.put("bsbtName", deviceList.get(0).getBsbtName());
				returnMap.put("network", sys.id2Name(deviceList.get(0).getNetwork()));
				returnMap.put("device",  sys.id2Name(deviceList.get(0).getDeviceSubclass()));
				
			}

		}else if(netresTableName.equals("pnr_access_network_quipment")){
			AccessNetworkQuipmentMgr aq = (AccessNetworkQuipmentMgr)getBean("anQuipmentService");

			SearchResult searchResult = aq.searchAndCount(search);
			List<AccessNetworkQuipment> deviceList = searchResult.getResult();
			if(deviceList.size()>0){		
				
				returnMap.put("name", deviceList.get(0).getAnqName());
				returnMap.put("bsbtName", deviceList.get(0).getAnrName());
				returnMap.put("network", sys.id2Name(deviceList.get(0).getNetwork()));
				returnMap.put("device",  sys.id2Name(deviceList.get(0).getDeviceType()));
				
			}
			

		}
		
		
		JSONArray returnArray = JSONArray.fromObject(returnMap);
		String returnStr = returnArray.toString();
		
		System.out.println("---查看某一设备"+returnStr);
		
		MobileCommonUtils.responseWrite(response, returnStr, "UTF-8");
		return;
	}*/
	/**
	 * 获取巡检信息主要是巡检是定义的误差距离和间隔时间
	 * 
	 * @param request
	 * @param response
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void getCheckInfo(ActionMapping mapping, ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String currentTime = StaticMethod.getCurrentDateTime();
		String returnJson="" ;
		if(!"".equals(StaticMethod.nullObject2String(request.getParameter("resType")))){
			IErrorDistanceMgr distanceMgr = (IErrorDistanceMgr) ApplicationContextHolder.getInstance().getBean("errorDistanceMgr");
			Search search = new Search();
			search.addFilterEqual("resource", request.getParameter("resType"));
			ErrorDistance errorDistance = distanceMgr.searchUnique(search);
			if(null != errorDistance){
				returnJson= "[{\"success\":\"true\"," +
						"\"sysTime\":\""+ currentTime + "\"" +
						",\"intervalTime\":\""+ errorDistance.getIntervalTime() + "\"" +
						",\"errorDistance\":\""+ errorDistance.getRule() + "\"" +
								"}]";
			}else{
				returnJson= "[{\"success\":\"true\"," +
				"\"sysTime\":\""+ currentTime + "\"" +
				",\"intervalTime\":\""+ 0 + "\"" +
				",\"errorDistance\":\""+ 0.0 + "\"" +
						"}]";
			}
		}
		MobileCommonUtils.responseWrite(response, returnJson, "UTF-8");
		return;
	}
	
	
	/**
	 * 保存站点数据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void saveCheckResult(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		IInspectPlanItemMgr inspectPlanItemMgr = (IInspectPlanItemMgr)getBean("inspectPlanItemMgrImpl");
			String itemResult = "";
				
			String temp = StaticMethod.nullObject2String(request.getParameter("json"));
			JSONArray tempArray = new JSONArray("["+temp+"]");
			JSONObject tempObj = tempArray.getJSONObject(0);
			String saveData = tempObj.get("saveData")+"";
			JSONArray array = new JSONArray(saveData);
			JSONObject object = array.getJSONObject(0);
			
			String id = object.get("inputId")+"";
			itemResult = object.get("value")+"";
			String exceptionContent = object.get("exceptionContent")+"";
			String exceptionFlag = object.get("exceptionFlag")+"";
			Search search = new Search();
			search.addFilterEqual("id", id);
			SearchResult<InspectPlanItem> srarchResult = inspectPlanItemMgr.searchAndCount(search);
			if(srarchResult.getResult().isEmpty()){
				MobileCommonUtils.responseWrite(response, MobileConstants.failureStr, "UTF-8");
				return;
			}
			InspectPlanItem item = srarchResult.getResult().get(0);
			
			
			item.setSaveTime(new Date());
			if("-1".equals(exceptionFlag)){
				item.setExceptionFlag(Integer.parseInt(exceptionFlag));
				item.setItemResult("");
				item.setExceptionContent("");
			}else{
				item.setItemResult(itemResult);
				if(!"".equals(exceptionContent)){
					item.setExceptionFlag(1);
				}
				item.setExceptionContent(exceptionContent);
			}
			inspectPlanItemMgr.save(item);
			//已完成的巡检数量
			search = new Search();
			search.addFilterNotEmpty("saveTime");
			search.addFilterAnd(
					Filter.equal("planResId", item.getPlanResId())
			);
			int hasCheckTotal = inspectPlanItemMgr.searchAndCount(search).getTotalCount();//已巡检数量
			search = new Search();
			search.addFilterEqual("planResId", item.getPlanResId());
			int total = inspectPlanItemMgr.searchAndCount(search).getTotalCount();//全部数量
			IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
			
			if(total == hasCheckTotal){//如果相等则更新巡检资源的状态为已巡检即 1
				search = new Search();
				search.addFilterEqual("id", item.getPlanResId());
				InspectPlanRes res = inspectPlanResMgr.get(item.getPlanResId());
				res.setItemDoneNum(hasCheckTotal);
				res.setInspectState(1);
				res.setInspectTime(new Date());
				inspectPlanResMgr.save(res);//更新巡检资源
				
				
				IInspectPlanMainMgr inspectPlanMainMgr = (IInspectPlanMainMgr)getBean("inspectPlanMainMgr");
				search = new Search();
				search.addFilterEqual("id", res.getPlanId());
				
				InspectPlanMain inspectPlanMain = inspectPlanMainMgr.search(search).get(0);
				
				inspectPlanMain.setResDoneNum(inspectPlanResMgr.queryHasDoneNum(res.getPlanId()).get("hasCheckDoneNum"));
				inspectPlanMainMgr.save(inspectPlanMain);//更新巡检计划的已完成数
			}else{
				InspectPlanRes res = inspectPlanResMgr.get(item.getPlanResId());
				res.setInspectState(0);
				res.setItemDoneNum(hasCheckTotal);
				res.setInspectTime(new Date());
				inspectPlanResMgr.save(res);
			}
			MobileCommonUtils.responseWrite(response, MobileConstants.successStr, "UTF-8");
	} 
	/**
	 * 批量保存	4--改
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 */
	public void saveAllCheckResult(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
			InspectPlanMgr mgr = (InspectPlanMgr) this.getBean("inspectPlanMgrImpl");
			String userId = this.getUserId(request);
			String returnStr = mgr.saveAllCheckResult(request, userId);
			if(!"".equals(returnStr)){
				MobileCommonUtils.responseWrite(response, returnStr, "UTF-8");
				return;
			}
	} 
	/*
	 * 保存图片单张
	 */
	@Deprecated
	public void saveOnePhotoBase64Old(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		InputStream inputStream = request.getInputStream();
		
		
		String fileString = PartnerUtil.inputStreamToString(inputStream);
//		System.out.println("fileString    "+fileString);
		
		String fileType = StaticMethod.nullObject2String(request.getParameter("fileType"));
		
		String picture_id = StaticMethod.nullObject2String(request.getParameter("picture_id"));
		if("".equals(picture_id)||"".equals(fileType)){
			MobileCommonUtils.responseWrite(response, MobileConstants.failureStr, "UTF-8");
			return;
		}
		String idType = StaticMethod.nullObject2String(request.getParameter("idType"));
		String photoType = StaticMethod.nullObject2String(request.getParameter("photoType"));
		
		IPnrInspectTaskFileMgr mgr = (IPnrInspectTaskFileMgr) ApplicationContextHolder.getInstance().getBean("pnrInspectTaskFileMgrImpl");
		
		File tempFile = File.createTempFile("temp", ".png");
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(tempFile.getPath())));
		PnrInspectTaskFile file = new PnrInspectTaskFile();
		FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
		fileOutputStream.write(fileString.getBytes());
		fileOutputStream.flush();
		fileOutputStream.close();
		file.setCreateTime(StaticMethod.getCurrentDateTime());
		file.setFileData(Hibernate.createClob(br, (int) tempFile.length()));
		file.setFile_id(picture_id);
		file.setIdType(idType);
		file.setFileType(fileType);
		file.setPhotoType(photoType);
		mgr.saveOrUpdateTaskFile(file, fileString);
		if(idType.equals("planItem")){
			IInspectPlanItemMgr inspectPlanItemMgr = (IInspectPlanItemMgr)getBean("inspectPlanItemMgrImpl");
			Search search = new Search();
			search.addFilterEqual("id", picture_id);
			InspectPlanItem inspectPlanItem = inspectPlanItemMgr.searchUnique(search);
			inspectPlanItem.setHasPicture(1);
			inspectPlanItemMgr.save(inspectPlanItem);
		}
		if(idType.equals("res")){
			
			IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
			Search search = new Search();
			search.addFilterEqual("id", picture_id);
			InspectPlanRes inspectPlanRes = inspectPlanResMgr.get(Long.parseLong(picture_id));
			if(null == inspectPlanRes){//如果为空,那么为测试数据
				MobileCommonUtils.responseWrite(response, MobileConstants.failureStr, "UTF-8");
				return;
			}
			inspectPlanRes.setHasPicture(1);
			
			
			IErrorDistanceMgr errorDistanceMgr = (IErrorDistanceMgr) this.getBean("errorDistanceMgr");
			Search errorSearch = new Search();
			errorSearch.addFilterEqual("resource", inspectPlanRes.getResType());
			ErrorDistance errorDistance = errorDistanceMgr.searchUnique(errorSearch);
			if(null != inspectPlanRes.getErrorDistance()&&null != errorDistance  && inspectPlanRes.getErrorDistance()>errorDistance.getRule()||null !=  inspectPlanRes.getSignTime()){
				inspectPlanRes.setSignStatus(3);//无坐标,但有照片
			}else if(null == inspectPlanRes.getSignTime()){
				inspectPlanRes.setSignStatus(2);
			}
			if(fileType.equals("0")){//0表示签到时拍的照片此时需要更新签到时间 
				inspectPlanRes.setSignTime(new Date());
			}
			if(fileType.equals("1")){//0表示签到时拍的照片此时需要更新签到时间 
				inspectPlanRes.setLastInspectTime(new Date());
			}
			
			
			inspectPlanResMgr.save(inspectPlanRes);
		}
		MobileCommonUtils.responseWrite(response, MobileConstants.successStr, "UTF-8");
		
	}
	
	
	/**
	 * 保存图片64 多张
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void saveMorePhotoBase64(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		Long startReceive = System.currentTimeMillis();
		request.setCharacterEncoding("utf-8");
		String picture_id = StaticMethod.nullObject2String(request.getParameter("picture_id"));
		String idType = StaticMethod.nullObject2String(request.getParameter("idType"));
		String fileType = StaticMethod.nullObject2String(request.getParameter("fileType"));
		InputStream inputStream = request.getInputStream();
		String fileString = PartnerUtil.inputStreamToString(inputStream);
//		System.out.println("fileString    "+fileString);
		Long receiveCost = System.currentTimeMillis() - startReceive;
		System.out.println("接收照片用时:    "+receiveCost);
		Long start = System.currentTimeMillis();
		
		IPnrInspectTaskFileMgr mgr = (IPnrInspectTaskFileMgr) ApplicationContextHolder.getInstance().getBean("pnrInspectTaskFileMgrImpl");
		if(null != fileString&& !"".equals(fileString)){
			JSONArray array = new JSONArray("["+fileString+"]");
			JSONObject obj = array.getJSONObject(0);
			String str = obj.get("data").toString();
			JSONArray data = new JSONArray(str);
			if(null != data && data.length()>0){
				PnrInspectTaskFile file;
				JSONObject dataObj;
				for(int i = 0;i<data.length();i++){
					file = new PnrInspectTaskFile();
						dataObj = data.getJSONObject(i);
						file.setFileType(fileType);
						file.setIdType(idType);
						file.setFile_id(picture_id);
						String fileData = dataObj.get("fileData").toString();
						
						File tempFile = File.createTempFile("temp", ".png");
						BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(tempFile.getPath())));
						
						
						FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
						fileOutputStream.write(fileData.getBytes());
						fileOutputStream.flush();
						fileOutputStream.close();
						file.setCreateTime(StaticMethod.getCurrentDateTime());
						file.setFileData(Hibernate.createClob(br, (int) tempFile.length()));
						
						mgr.saveOrUpdateTaskFile(file, dataObj.get("fileData").toString());
						
				}
			}
			if(idType.equals("res")){
				IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
				Search search = new Search();
				search.addFilterEqual("id", picture_id);
				InspectPlanRes inspectPlanRes = inspectPlanResMgr.get(Long.parseLong(picture_id));
				inspectPlanRes.setHasPicture(1);
				
				IErrorDistanceMgr errorDistanceMgr = (IErrorDistanceMgr) this.getBean("errorDistanceMgr");
				Search errorSearch = new Search();
				errorSearch.addFilterEqual("resource", inspectPlanRes.getResType());
				ErrorDistance errorDistance = errorDistanceMgr.searchUnique(errorSearch);
				if(inspectPlanRes.getErrorDistance()>errorDistance.getRule()||null !=  inspectPlanRes.getSignTime()){
					inspectPlanRes.setSignStatus(3);//无坐标,但有照片
				}else if(null == inspectPlanRes.getSignTime()){
					inspectPlanRes.setSignStatus(2);
				}
				inspectPlanResMgr.save(inspectPlanRes);
			}
			Long cost = System.currentTimeMillis()-start;
			System.out.println("用时:   "+cost);
			MobileCommonUtils.responseWrite(response, MobileConstants.successStr, "UTF-8");
		}
	}
	/**
	 * 保存坐标
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void saveLocation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		InspectPlanMgr mgr = (InspectPlanMgr) this.getBean("inspectPlanMgrImpl");
		mgr.saveLocation(request, response, this.getUserId(request));
	}
	/**
	 * 保存线路坐标
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void saveLineLocation(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response){
		InspectPlanMgr mgr = (InspectPlanMgr) this.getBean("inspectPlanMgrImpl");
		IInspectLineTrackMgr distanceMgr = (IInspectLineTrackMgr) this.getBean("lineTrackMgr");
		mgr.saveLineLocation(request, response, getUserId(request));
	}
	
	/**
	 * 查询该用户的任务信息	1---改
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void userTaskInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String userId = this.getUserId(request);
		String deptId = this.getUser(request).getDeptid();
		
		try {
			IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) ApplicationContextHolder.getInstance().getBean("inspectPlanResMgr");
			String whereStr  = " where planId is not null ";
			PartnerUserMgr partnerUserMgr = (PartnerUserMgr) ApplicationContextHolder.getInstance().getBean("partnerUserMgr");
			PartnerUser user = partnerUserMgr.getPartnerUserByUserId(userId);
			if (null != user) {// 不为空说明是代维公司人查看，默认为只能查看自己部门以及其下部门的，所以应该是executeDept
								// like
				if (deptId.startsWith("201") && deptId.length() <= 7) {
					ITawSystemDeptManager mgr = (ITawSystemDeptManager) ApplicationContextHolder.getInstance().getBean("ItawSystemDeptManager");

					TawSystemDept dept = mgr.getDeptinfobydeptid(deptId, "0");
					String areaid = dept.getAreaid();
					if (areaid != null && !areaid.trim().equals("")) {
						whereStr += "  and country like '" + areaid + "%'";
					} else {// 如果移动人员所在部门没有地域，则不允许查看
						MobileCommonUtils.responseWrite(response, "", "UTF-8");
						return;
					}
				}else {
					whereStr += "  and executeDept like '" + deptId + "%'";
					ITawSystemDeptManager mgr = (ITawSystemDeptManager) ApplicationContextHolder.getInstance().getBean("ItawSystemDeptManager");
					TawSystemDept dept = mgr.getDeptinfobydeptid(deptId, "0");
					String areaid = dept.getAreaid().trim();
					if(isRes2Person(areaid)){
						whereStr += " AND resCfgId IN "+ buildConfigsStr(userId,deptId);
					}
				}

			} else if (!"admin".equals(userId)) {// 如果不是代维公司人员，则应该根据用户所属部门的所属地域来查看巡检任务，所以应该是根据areaId
													// like

				ITawSystemDeptManager mgr = (ITawSystemDeptManager) ApplicationContextHolder
						.getInstance().getBean("ItawSystemDeptManager");

				TawSystemDept dept = mgr.getDeptinfobydeptid(deptId, "0");
				String areaid = dept.getAreaid();
				if (areaid != null && !areaid.trim().equals("")) {
					whereStr += "  and country like '" + areaid + "%'";
				} else {// 如果移动人员所在部门没有地域，则不允许查看
					MobileCommonUtils.responseWrite(response, "", "UTF-8");
					return;
				}
			}

		//whereStr += " and inspectState = 0 and planEndTime > "	+ CommonSqlHelper.getCurrentDateTimeFunc() + " ";
		whereStr += " and inspectState = 0 ";
		whereStr += " and specialty = '1122511'";//巡检资源专业为资源清查
		System.out.println("------巡检任务条数whereStr="+whereStr);
		int count = inspectPlanResMgr.getPlanResCountByHql(whereStr);
		int nocount = listnoPlanRes(request,response,userId,deptId);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("planNum",count);
	    map.put("noplanNum",nocount);//未设置计划的资源数
		
		map.put("showDeviceItem",AndroidPropertiesUtils.getValue("showDeviceItem"));
		map.put("startCheckType",AndroidPropertiesUtils.getValue("startCheckType"));
		map.put("supportReportHiddenTrouble",AndroidPropertiesUtils.getValue("supportReportHiddenTrouble"));
		map.put("supportTakeAutio",AndroidPropertiesUtils.getValue("supportTakeAutio"));
		JSONArray returnArray = JSONArray.fromObject(map);
		MobileCommonUtils.responseWrite(response, returnArray.toString(), "UTF-8");
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
	}
	/**
	 * 计算未设置计划的资源数
	 * @param request
	 * @param response
	 * @param userId
	 * @param deptId
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	private int listnoPlanRes(HttpServletRequest request,HttpServletResponse response, String userId, String deptId)
			throws ServletException, IOException {
	
			IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) ApplicationContextHolder
					.getInstance().getBean("inspectPlanResMgr");
			String planMainId = request.getParameter("planMainId");
			TawSystemSessionForm sessionForm = (TawSystemSessionForm) request
					.getSession().getAttribute("sessionform");

	
			String whereStr =" where planId is null";
				

			PartnerUserMgr partnerUserMgr = (PartnerUserMgr) ApplicationContextHolder
					.getInstance().getBean("partnerUserMgr");
			PartnerUser user = partnerUserMgr.getPartnerUserByUserId(userId);
			if(null != user){//不为空说明是代维公司人查看，默认为只能查看自己部门以及其下部门的，所以应该是executeDept like
				if(sessionForm.getDeptid().startsWith("201")&&sessionForm.getDeptid().length()<=7){
					
					ITawSystemDeptManager mgr = (ITawSystemDeptManager)ApplicationContextHolder.getInstance().getBean("ItawSystemDeptManager");
					
					TawSystemDept dept = mgr.getDeptinfobydeptid(sessionForm.getDeptid(), "0");
					String areaid = dept.getAreaid();
					if(areaid!=null && !areaid.trim().equals("")){
						whereStr += "  and country like '"+areaid+"%'"; 
					}else {//如果移动人员所在部门没有地域，则不允许查看
						MobileCommonUtils.responseWrite(response, "", "UTF-8");
						
						return 0;
					} 
					
				}else{
					
					whereStr += "  and executeDept like '"+sessionForm.getDeptid()+"%'"; 
				}
				
			}else if(!"admin".equals(userId)){//如果不是代维公司人员，则应该根据用户所属部门的所属地域来查看巡检任务，所以应该是根据areaId like
				
				ITawSystemDeptManager mgr = (ITawSystemDeptManager)ApplicationContextHolder.getInstance().getBean("ItawSystemDeptManager");
				
				TawSystemDept dept = mgr.getDeptinfobydeptid(sessionForm.getDeptid(), "0");
				String areaid = dept.getAreaid();
				if(areaid!=null && !areaid.trim().equals("")){
					whereStr += "  and country like '"+areaid+"%'"; 
				}else {//如果移动人员所在部门没有地域，则不允许查看
					MobileCommonUtils.responseWrite(response, "", "UTF-8");
					
					return 0;
				} 
			}			
						
			whereStr += " and inspectState = 0 and cycleEndTime >"+ 
			CommonSqlHelper.getCurrentDateTimeFunc() + " ";
			whereStr += " and specialty = '1122511' ";//巡检资源专业为资源清查

			System.out.println("------未例如计划资源条数whereStr="+whereStr);
			Map map = (Map) inspectPlanResMgr.findInspectPlanResList(0,	CommonConstants.PAGE_SIZE10, whereStr);

			List<InspectPlanRes> tempList = (List) map.get("result");
			
			return  Integer.parseInt(map.get("total").toString());
			
	}
	
	
	
	/**
	 * 查询抽检计划
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	
	public void findInspectPlanMainList(ActionMapping mapping, ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String planResId = StaticMethod.nullObject2String(request.getParameter("planResId"));
		String pageIndexString = StaticMethod.nullObject2String(request.getParameter("pageIndex"));
		request.getRequestDispatcher("/partner/inspect/inspectSpotcheckAction.do?method=findInspectPlanMainList&pageIndexString="+pageIndexString+"&device=mobile").forward(request, response);
	}
	/**
	 * 查询抽检资源
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void findInspectPlanResSpotcheckList(ActionMapping mapping, ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String planMainId = StaticMethod.nullObject2String(request.getParameter("planMainId"));
		String pageIndexString = StaticMethod.nullObject2String(request.getParameter("pageIndex"));
		String completeQuery = StaticMethod.nullObject2String(request.getParameter("completeQuery"));
		request.getRequestDispatcher("/partner/inspect/inspectSpotcheckAction.do?method=findInspectPlanResSpotcheckList&completeQuery="+completeQuery+"&pageIndexString="+pageIndexString+"&device=mobile&planId="+planMainId).forward(request, response);
	}
	/**
	 * 查询抽检项
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void findInspectPlanItemSpotcheckList(ActionMapping mapping, ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String inspectPlanResId = StaticMethod.nullObject2String(request.getParameter("planResId"));
		String pageIndexString = StaticMethod.nullObject2String(request.getParameter("pageIndex"));
		request.getRequestDispatcher("/partner/inspect/inspectSpotcheckAction.do?method=findInspectPlanItemSpotcheckList&pageIndexString="+pageIndexString+"&device=mobile&inspectPlanResId="+inspectPlanResId).forward(request, response);
	}
	/**
	 * 保存抽检项
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	public void saveInspectSpotcheck(ActionMapping mapping, ActionForm form,HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");	
		InputStream inputStream = request.getInputStream();
		String returnJson = PartnerUtil.inputStreamToString(inputStream);
		System.out.println("returnJson    "+returnJson);
		JSONArray array = new JSONArray("["+returnJson+"]");
		JSONObject obj;
		String id = "";
		String value = "";
		//key型如:score_2c9e9d89397004ba0139709938fd0060_873(score_抽检模板ID_巡检项ID)
		Map<String, String> tempMap = new HashMap<String, String>();
		for(int i = 0 ;i<array.length();i++){
			obj = array.getJSONObject(i);
			id = obj.getString("id");
			value = obj.getString("value");
			tempMap.put(id, value);
		}
		String planMainId = StaticMethod.nullObject2String(request.getParameter("planMainId"));
		String pageIndexString = StaticMethod.nullObject2String(request.getParameter("pageIndex"));
		
		IInspectSpotcheckMgr inspectSpotcheckMgr = (IInspectSpotcheckMgr)getBean("inspectSpotcheckMgr");
		String inspectPlanResId = request.getParameter("planResId");
		String planId = StaticMethod.nullObject2String(request.getParameter("planMainId"));
		String userId = this.getUserId(request);
		
		//先保存抽检资源并获取抽检资源ID
		SpotcheckRes spotcheckRes = new SpotcheckRes();
		spotcheckRes.setPlanId(planId);
		spotcheckRes.setPlanResId(inspectPlanResId);
		spotcheckRes.setSpotcheckUser(userId);
		spotcheckRes.setSpotcheckTime(new Date());
		inspectSpotcheckMgr.saveSpotcheckRes(spotcheckRes);
		String spotcheckResId = spotcheckRes.getId();
		
		Map<String,Float> spotTmpMap = Maps.newHashMap();//key:抽检模板ID，value:该模板下扣分后的剩余分
//		Map<String,Object[]> parameterMap = request.getParameterMap();//前台参数
		for(String key : tempMap.keySet()){
			//key型如:score_2c9e9d89397004ba0139709938fd0060_873(score_抽检模板ID_巡检项ID)
			if(key.startsWith("score")){//如果是扣分分数
				String[] keyArray = key.split("_");
				String spotTmpId = keyArray[1]; //抽检模板ID
				String planItemId = keyArray[2];//巡检项ID
				SpotcheckTemplate spotTmp = null;
				if(!spotTmpMap.containsKey(spotTmpId)){
					spotTmp = inspectSpotcheckMgr.getSpotcheckTemplate(spotTmpId);
					spotTmpMap.put(spotTmpId, spotTmp.getScore());
				}
				String scoreStr = tempMap.get(key);
				Float reduceScore = new Float(0); //扣分分数
				if(!StringUtils.isEmpty(scoreStr)){//如果填写了扣分分数
					reduceScore = Float.parseFloat(scoreStr);
					spotTmpMap.put(spotTmpId, spotTmpMap.get(spotTmpId)- reduceScore);
				}
				SpotcheckItem spotcheckItem = new SpotcheckItem();
				spotcheckItem.setPlanItemId(planItemId);
				spotcheckItem.setPlanResId(inspectPlanResId);
				spotcheckItem.setScore(reduceScore);
				spotcheckItem.setSpotcheckResId(spotcheckResId);
				inspectSpotcheckMgr.saveSpotcheckItem(spotcheckItem);
			}
		}
		Float remainTotalScore = 0f;//扣分后剩余总分数
		for(String key : spotTmpMap.keySet()){
			Float remainScore = spotTmpMap.get(key);
			if(remainScore < 0 ){
				remainScore = 0f;
			}
			remainTotalScore += remainScore;
		}
		//更新抽检资源得分
		spotcheckRes.setScore(remainTotalScore);
		inspectSpotcheckMgr.saveSpotcheckRes(spotcheckRes);
		MobileCommonUtils.responseWrite(response, MobileConstants.successStr, "UTF-8");
	}
	
	public void saveOnePhotoBase64(ActionMapping mapping, ActionForm form,HttpServletRequest request,HttpServletResponse response){
		DiskFileItemFactory difactory  = new DiskFileItemFactory();  
		difactory.setSizeThreshold(1024 * 1024);
        ServletFileUpload upload = new ServletFileUpload(difactory );  
        upload.setSizeMax(1024*3024);                       //设置上传文件的最大容量  
        try{
        	String fileType = "";
        	String file_id = "";
        	String idType = "";
    		String photoType = "";
    		String flag = "";
    		
    		  String fileName = StaticMethod
      		.getCurrentDateTime("yyyyMMddHHmmss")+StaticMethod.getRandomCharAndNumr(4);
      		
      		String tPath = "inspect"+ File.separatorChar + fileName.substring(0, 4) + File.separatorChar 
      		+ fileName.substring(4,6)+ File.separatorChar + fileName.substring(6, 8)+ File.separatorChar;
  		    tPath = tPath.replace("\\", "/");

//  		    String remotePhotoUrl = StaticVariable.IMG_INSPECT_PUBLIC_URL; //存放图片的共享目录  
  		    String remotePhotoUrl = CommonUtils.getImgInspectPublicUrl(); //存放图片的共享目录  

  		    String placePath = remotePhotoUrl+tPath;
  		    
              String nameStr = "";// 文件名（带路径）    
    		
            List<FileItem> items  = upload.parseRequest(request);  //取得表单全部数据 
            PnrInspectTaskFile file = new PnrInspectTaskFile();
            for(FileItem item:items){
                if(!item.isFormField()){
                	String comeFileName = item.getName();					
					nameStr = tPath+comeFileName;			
					InputStream in = null;  
					OutputStream out = null;  
						try {  
							    SmbFile remoteFile2 = new SmbFile(placePath);  
							    if(!remoteFile2.exists()){
							    	remoteFile2.mkdirs();
							    }
							    SmbFile remoteFile = new SmbFile(placePath+ item.getName());  
							    remoteFile.connect(); //尝试连接   
							    in = item.getInputStream();
						        out = new BufferedOutputStream(new SmbFileOutputStream(remoteFile));  
							 
							   byte[] buffer = new byte[4096];  
							   int len = 0; //读取长度   
							   while ((len = in.read(buffer, 0, buffer.length)) != -1) {  
								   out.write(buffer, 0, len);  
								   }  
							    out.flush(); //刷新缓冲的输出流   
	//							    System.out.println("--done-");
						} catch (Exception e) {  
						   String msg = "巡检上传-发生错误：" + e.getLocalizedMessage();  
						    System.out.println(msg); 
						    MobileCommonUtils.responseWrite(response,
									MobileConstants.failureStr, "UTF-8");
						
							return;
						}  
						finally {  
						  try {  
						      if(out != null) {  
							          out.close();  
							       }  
						        if(in != null) {  
						           in.close();  
						        }  
						  	}  catch (Exception e) {}  
						}  
                	
                }else{
                	
                	String name = item.getFieldName();
                	if("fileType".equals(name)){    //png
                		fileType = item.getString();
                	}
                	if("file_id".equals(name)){    //765406
                		file_id = item.getString();
                	}
                	if("idType".equals(name)){     //planItem
                		idType = item.getString();
                	}
                	if("photoType".equals(name)){
                		photoType = item.getString();
                	}
                	if("flag".equals(name)){
                		flag = item.getString();
                	}
                }
            }
            if("".equals(file_id)||"".equals(fileType)){
            	MobileCommonUtils.responseWrite(response, MobileConstants.failureStr, "UTF-8");    			
    			return;
    		}
            
            IPnrInspectTaskFileMgr mgr = (IPnrInspectTaskFileMgr) ApplicationContextHolder.getInstance().getBean("pnrInspectTaskFileMgrImpl");
            
            file.setCreateTime(StaticMethod.getCurrentDateTime());
    		file.setFile_id(file_id);
    		file.setIdType(idType);
    		file.setFileType(fileType);
    		file.setPhotoType(photoType);
		    file.setPath(nameStr);
    		
    		if(CommonSqlHelper.isOracleDialect()){
    			   PnrInspectTaskFileDaoHibernate dao = (PnrInspectTaskFileDaoHibernate) getBean("pnrInspectTaskFileDao");
				   Session session = dao.getHibernateTemplate().getSessionFactory().openSession();
				   Transaction tx = session.beginTransaction();
				   file.setFileData(null);
				   session.saveOrUpdate(file);
				   session.flush();
				 /*  session.refresh(file, LockMode.UPGRADE);
				   SerializableClob cb = (SerializableClob) file.getFileData();
				   Clob wrapClob = (Clob) cb.getWrappedClob();
				   if(wrapClob instanceof CLOB){
					    CLOB clob = (CLOB) wrapClob;
					    clob.putString(1, PartnerUtil.convertImageDataToBASE64(tempFile.getPath()));
				   }*/
				   tx.commit();
				   session.close();
    		}else if(CommonSqlHelper.isInformixDialect()){
//    			file.setFileData(Hibernate.createClob(br, (int) tempFile.length()));
//    			mgr.saveOrUpdateTaskFile(file, PartnerUtil.convertImageDataToBASE64(tempFile.getPath()));
//    			br.close();
    		}
    		
    		if(idType.equals("planItem")){
    			IInspectPlanItemMgr inspectPlanItemMgr = (IInspectPlanItemMgr)getBean("inspectPlanItemMgrImpl");
    			Search search = new Search();
    			search.addFilterEqual("id", file_id);
    			InspectPlanItem inspectPlanItem = inspectPlanItemMgr.searchUnique(search);
    			inspectPlanItem.setHasPicture(1);
    			inspectPlanItem.setPictureUploadNum(inspectPlanItem.getPictureUploadNum()+1);
    			if(inspectPlanItem.getPictureNum()<=inspectPlanItem.getPictureUploadNum()){//如果上传数量大于规定数量,则巡检项完成
    					inspectPlanItem.setPictureUploadFlag(1);
    			}
    			IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
    			InspectPlanRes inspectPlanRes = inspectPlanResMgr.get(inspectPlanItem.getPlanResId());
    			
    			/*search = new Search();
    			
    			照片上传--更改整个元任务的状态 ---2014-09-04 无意义！
    			search.addFilterEqual("planResId", inspectPlanItem.getPlanResId());
    			search.addFilterEqual("pictureUploadFlag", "0");
    			search.addFilterEqual("pictureFlag", "1");
    			int notComplete = inspectPlanItemMgr.searchAndCount(search).getTotalCount();
    			if(0 == notComplete){
    				inspectPlanRes.setInspectState(1);
    			}
    			inspectPlanResMgr.save(inspectPlanRes);*/
    			inspectPlanItemMgr.save(inspectPlanItem);
    			/*if(tempFile.exists()){
    				tempFile.delete();
    			}*/
    			//判断照片是否已经上传完成,如果上传完成就更新计划
    			search = new Search();
    			search.addFilterEqual("planResId", inspectPlanRes.getId());
    			search.addFilterEqual("pictureUploadFlag", "0");
    			search.addFilterEqual("pictureFlag", "1");
    			int notComplete = inspectPlanItemMgr.searchAndCount(search).getTotalCount();//判断照片是否已经全部上传成功,上
    			System.out.println("notComplete    "+notComplete);
    			if(0 == notComplete){
    				
    				IInspectPlanMainMgr inspectPlanMainMgr = (IInspectPlanMainMgr) ApplicationContextHolder.getInstance().getBean("inspectPlanMainMgr");
    				search = new Search();
    				search.addFilterEqual("id", inspectPlanRes.getPlanId());
    				InspectPlanMain inspectPlanMain = inspectPlanMainMgr.search(search).get(0);
    				int resExceptionNum = inspectPlanResMgr.getExceptionResCount(inspectPlanRes.getPlanId());
    				inspectPlanMain.setResExceptionNum(resExceptionNum);
    				inspectPlanMain.setResDoneNum(inspectPlanResMgr.queryHasDoneNum(inspectPlanRes.getPlanId()).get("hasCheckDoneNum"));
    				inspectPlanMainMgr.save(inspectPlanMain);
    				
    			}
    			
    			MobileCommonUtils.responseWrite(response, MobileConstants.successStr, "UTF-8");
    			return;
    		}
    		if(idType.equals("res")){
    			
    			IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
    			InspectPlanRes inspectPlanRes = inspectPlanResMgr.get(Long.parseLong(file_id));
    			if(null == inspectPlanRes){//如果为空,那么为测试数据
    				MobileCommonUtils.responseWrite(response, MobileConstants.failureStr, "UTF-8");
    				return;
    			}
    			inspectPlanRes.setHasPicture(1);
    			
    			
//    			IErrorDistanceMgr errorDistanceMgr = (IErrorDistanceMgr) this.getBean("errorDistanceMgr");
//    			Search errorSearch = new Search();
//    			errorSearch.addFilterEqual("resource", inspectPlanRes.getResType());
//    			ErrorDistance errorDistance = errorDistanceMgr.searchUnique(errorSearch);
//    			if(null != inspectPlanRes.getErrorDistance()&&null != errorDistance  && inspectPlanRes.getErrorDistance()>errorDistance.getRule()||null !=  inspectPlanRes.getSignTime()){
//    			if(null != inspectPlanRes.getErrorDistance()&&null != errorDistance  && inspectPlanRes.getErrorDistance()>errorDistance.getRule()||null !=  inspectPlanRes.getSignTime()){
//    				inspectPlanRes.setSignStatus(3);//无坐标,但有照片
//    			}else if(null == inspectPlanRes.getSignTime()){
//    				inspectPlanRes.setSignStatus(2);
//    			}else{
//    			}
    			inspectPlanRes.setSignStatus(3);
    			if("0".equals(flag)){//0表示签到时拍的照片此时需要更新签到时间 
    				inspectPlanRes.setSignTime(new Date());
    			}
    			if("1".equals(flag)){//0表示签退时拍的照片此时需要更新签退时间 
    				inspectPlanRes.setLastInspectTime(new Date());
    			}
    			/*if(tempFile.exists()){
    				tempFile.delete();
    			}*/
    			inspectPlanResMgr.save(inspectPlanRes);
    			MobileCommonUtils.responseWrite(response, MobileConstants.successStr, "UTF-8");
    			return;
    		}
        }catch (Exception e) {
        	e.printStackTrace();
        	MobileCommonUtils.responseWrite(response, MobileConstants.failureStr, "UTF-8");
			return;
		}
	}
	
	/*
	 * 保存音频
	 */
	public void saveAudioBase64(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		request.setCharacterEncoding("UTF-8");
		InputStream inputStream = request.getInputStream();
		
		
		String fileString = PartnerUtil.inputStreamToString(inputStream);
		String fileType = StaticMethod.nullObject2String(request.getParameter("fileType"));
		String audio_id = StaticMethod.nullObject2String(request.getParameter("audio_id"));
		if("".equals(audio_id)){
			MobileCommonUtils.responseWrite(response, MobileConstants.failureStr, "UTF-8");
			return;
		}
		String idType = StaticMethod.nullObject2String(request.getParameter("idType"));
		String photoType = StaticMethod.nullObject2String(request.getParameter("photoType"));
		
		IPnrInspectTaskFileMgr mgr = (IPnrInspectTaskFileMgr) ApplicationContextHolder.getInstance().getBean("pnrInspectTaskFileMgrImpl");
		
		File tempFile = File.createTempFile("temp", ".png");
		BufferedReader br = new BufferedReader(new InputStreamReader(
				new FileInputStream(tempFile.getPath())));
		PnrInspectTaskFile file = new PnrInspectTaskFile();
		FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
		fileOutputStream.write(fileString.getBytes());
		fileOutputStream.flush();
		fileOutputStream.close();
		file.setCreateTime(StaticMethod.getCurrentDateTime());
		file.setFileData(Hibernate.createClob(br, (int) tempFile.length()));
		file.setFile_id(audio_id);
		file.setIdType(idType);
		file.setFileType(fileType);
		file.setPhotoType(photoType);
		
		if(CommonSqlHelper.isOracleDialect()){
			   PnrInspectTaskFileDaoHibernate dao = (PnrInspectTaskFileDaoHibernate) getBean("pnrInspectTaskFileDao");
			   Session session = dao.getHibernateTemplate().getSessionFactory().openSession();
			   Transaction tx = session.beginTransaction();
			   file.setFileData(Hibernate.createClob("1"));
			   session.saveOrUpdate(file);
			   session.flush();
			   session.refresh(file, LockMode.UPGRADE);
			   SerializableClob cb = (SerializableClob) file.getFileData();
			   Clob wrapClob = (Clob) cb.getWrappedClob();
			   if(wrapClob instanceof CLOB){
				    CLOB clob = (CLOB) wrapClob;
				    clob.putString(1, PartnerUtil.convertImageDataToBASE64(tempFile.getPath()));
			   }
			   tx.commit();
			   session.close();
		}else if(CommonSqlHelper.isInformixDialect()){
			file.setFileData(Hibernate.createClob(br, (int) tempFile.length()));
			mgr.saveOrUpdateTaskFile(file, PartnerUtil.convertImageDataToBASE64(tempFile.getPath()));
			br.close();
		}
//		mgr.saveOrUpdateTaskFile(file, fileString);
		if(idType.equals("planItem")){
			IInspectPlanItemMgr inspectPlanItemMgr = (IInspectPlanItemMgr)getBean("inspectPlanItemMgrImpl");
			Search search = new Search();
			search.addFilterEqual("id", audio_id);
			InspectPlanItem inspectPlanItem = inspectPlanItemMgr.searchUnique(search);
			inspectPlanItem.setHasAudio(1);
			inspectPlanItemMgr.save(inspectPlanItem);
		}
		if(idType.equals("res")){
//			IInspectPlanResMgr inspectPlanResMgr = (IInspectPlanResMgr) getBean("inspectPlanResMgr");
//			Search search = new Search();
//			search.addFilterEqual("id", audio_id);
//			InspectPlanRes inspectPlanRes = inspectPlanResMgr.get(Long.parseLong(audio_id));
//			inspectPlanRes.setHasPicture(1);
//			
//			
//			IErrorDistanceMgr errorDistanceMgr = (IErrorDistanceMgr) this.getBean("errorDistanceMgr");
//			Search errorSearch = new Search();
//			errorSearch.addFilterEqual("resource", inspectPlanRes.getResType());
//			ErrorDistance errorDistance = errorDistanceMgr.searchUnique(errorSearch);
//			if(null != inspectPlanRes.getErrorDistance() && inspectPlanRes.getErrorDistance()>errorDistance.getRule()||null !=  inspectPlanRes.getSignTime()){
//				inspectPlanRes.setSignStatus(3);//无坐标,但有照片
//			}else if(null == inspectPlanRes.getSignTime()){
//				inspectPlanRes.setSignStatus(2);
//			}
//			
//			
//			
//			inspectPlanResMgr.save(inspectPlanRes);
		}
		MobileCommonUtils.responseWrite(response, MobileConstants.successStr, "UTF-8");
	}
	//------------------------下面是线路巡检部分-----------------
	/**
	 * 查询该用户的线路巡检信息
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void userLineTaskInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String userId = this.getUserId(request);
//		System.out.println(json);			
		
		IInspectPlanMainMgr inspectPlanMainMgr = (IInspectPlanMainMgr) ApplicationContextHolder.getInstance().getBean("inspectPlanMainMgr");
		ITawSystemUserManager systemUserManager = (ITawSystemUserManager) ApplicationContextHolder.getInstance().getBean("ItawSystemUserManagerFlush");
		Search search = new Search();	
		//查询权限
		
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		PartnerUserMgr  partnerUserMgr = (PartnerUserMgr)getBean("partnerUserMgr");
		PartnerUser user = partnerUserMgr.getPartnerUserByUserId(userId);
		
		
		if(null != user){//不为空说明是代维公司人查看，默认为只能查看自己部门以及其下部门的，所以应该是deptMagId like
			String deptMagId = sessionForm.getDeptid();
			if(deptMagId.length()>6){
				search.addFilterLike("deptMagId", deptMagId.substring(0, deptMagId.length()-2)+ "%");
			}else{
				search.addFilterLike("deptMagId", deptMagId+ "%");
			}
		}else if(!"admin".equals(userId)){//如果不是代维公司人员，则应该根据用户所属部门的所属地域来查看巡检任务，所以应该是根据areaId like
			
			ITawSystemDeptManager mgr = (ITawSystemDeptManager)getBean("ItawSystemDeptManager");
			TawSystemDept dept = mgr.getDeptinfobydeptid(sessionForm.getDeptid(), "0");
			String areaid = dept.getAreaid();
			PartnerDeptMgr  partnerDeptMgr = (PartnerDeptMgr)getBean("partnerDeptMgr");
			List partnerList = partnerDeptMgr.getPartnerDepts(" and countyId like '"+areaid+"%'");
			List<String> deptMagIdList = new ArrayList<String>();
			
			for(int i=0;partnerList!=null && i<partnerList.size();i++){
				PartnerDept partnerDept = (PartnerDept)partnerList.get(i);
				
				String deptMagId = StaticMethod.nullObject2String(partnerDept.getDeptMagId());
				if(!deptMagId.equals("")){
					deptMagIdList.add(deptMagId);
				}
			}
			if(deptMagIdList!=null && deptMagIdList.isEmpty()==false){
				search.addFilterIn("deptMagId", deptMagIdList);
			}else{//如果没有管理代维公司，则不允许看该界面
				MobileCommonUtils.responseWrite(response, "", "UTF-8");
			}
		}
		String currentMonth = request.getParameter("currentMonth");
		if(StringUtils.isEmpty(currentMonth)){
			LocalDate date = new LocalDate();
			search.addFilterEqual("year", date.getYear());
			search.addFilterEqual("month", date.getMonthOfYear());
		}
		search.addFilterEqual("status", "1");
		search.addFilterEqual("approveStatus", "1");
//		search.setMaxResults(CommonConstants.PAGE_SIZE10);
		
		search.addSortDesc("createTime");
		SearchResult<InspectPlanMain> searchResult = inspectPlanMainMgr.searchAndCount(search);
		int count = searchResult.getTotalCount();
		List<InspectPlanMain> returnList = searchResult.getResult();
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("planNum",count);
		map.put("showDeviceItem",AndroidPropertiesUtils.getValue("showDeviceItem"));
		map.put("startCheckType",AndroidPropertiesUtils.getValue("startCheckType"));
		map.put("supportReportHiddenTrouble",AndroidPropertiesUtils.getValue("supportReportHiddenTrouble"));
		map.put("supportTakeAutio",AndroidPropertiesUtils.getValue("supportTakeAutio"));
		JSONArray returnArray = JSONArray.fromObject(map);
		MobileCommonUtils.responseWrite(response, returnArray.toString(), "UTF-8");
	}
	@SuppressWarnings("unchecked")
	private boolean isRes2Person(String areaId){
		IPnrResConfigDaoJdbc pnrResConfigDaoJdbc = (PnrResConfigDaoJdbc) ApplicationContextHolder.getInstance().getBean("pnrResConfigDaoJdbc");
		ArrayList<Map<String,String>> list = (ArrayList<Map<String,String>>)pnrResConfigDaoJdbc.getResPersonFlag();
		Map trpf;
		for(int i=0;i<list.size();i++){
			trpf = list.get(i);
			if(areaId == null){
				return false;
			}else{
				if(areaId.contains((String)trpf.get("city_id"))){
					return true;
				}
			}
		}
		return false;
	}
	/*
	 * 根据用户登录名和部门Id查询出用户负责的资源配置Id
	 * 用于关联查询元任务(res_cfg_id关联字段)
	 */
	private String buildConfigsStr(String userId,String deptId){
		IPnrResConfigDaoJdbc pnrResConfigDaoJdbc = (PnrResConfigDaoJdbc) ApplicationContextHolder.getInstance().getBean("pnrResConfigDaoJdbc");
		ArrayList<Map<String,String>> list = (ArrayList<Map<String,String>>)pnrResConfigDaoJdbc.getPanResByUser(userId, deptId);
		String str = "";
		for(int i=0;i<list.size();i++){
			str += "'"+list.get(i).get("id") + "',";
		}
		String a = str.substring(0, str.length()-1);
		if(str.length()>0){
			a = str.substring(0, str.length()-1);
		}
		return "("+a+")";
	}
	
	
}
