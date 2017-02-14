package com.boco.eoms.partner.res.webapp.action;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;
import com.boco.eoms.partner.inspect.mgr.IInspectPlanResMgr;
import com.boco.eoms.partner.res.mgr.IPnrResChangeHistoryMgr;
import com.boco.eoms.partner.res.mgr.PnrResConfigMgr;
import com.boco.eoms.partner.res.mgr.PnrResConfigStationMgr;
import com.boco.eoms.partner.res.mgr.PnrResFamilyBroadbandMgr;
import com.boco.eoms.partner.res.mgr.PnrResIronMgr;
import com.boco.eoms.partner.res.mgr.PnrResJiekeMgr;
import com.boco.eoms.partner.res.mgr.PnrResLineMgr;
import com.boco.eoms.partner.res.mgr.PnrResWlanMgr;
import com.boco.eoms.partner.res.model.PnrResChangeHistory;
import com.boco.eoms.partner.res.model.PnrResConfig;
import com.boco.eoms.partner.res.model.PnrResConfigStation;
import com.boco.eoms.partner.res.model.PnrResFamilyBroadband;
import com.boco.eoms.partner.res.model.PnrResIron;
import com.boco.eoms.partner.res.model.PnrResJieke;
import com.boco.eoms.partner.res.model.PnrResLine;
import com.boco.eoms.partner.res.model.PnrResWlan;
import com.boco.eoms.partner.res.util.ResourceConstants;
import com.boco.eoms.partner.res.util.excelimport.ImportResult;
import com.boco.eoms.partner.res.webapp.form.PnrAllResForm;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;

/** 
 * Description: 代维资源
 * Company:     BOCO 
 * @author:     Liaojiming 
 * @version:    1.0 
 */
public class PnrAllResAction extends BaseAction {

	/**
	 * 资源新增
	 */
	public ActionForward addSuccess(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PnrResConfigMgr pnrResConfiMgr = (PnrResConfigMgr) getBean("PnrResConfigMgr");
		
		PnrAllResForm pnrAllResForm = (PnrAllResForm)form;
		String city = request.getParameter("region");
		String country = request.getParameter("city");
		String inspectCycle = request.getParameter("inspectCycle");
		pnrAllResForm.getPnrResConfig().setCity(city);
		pnrAllResForm.getPnrResConfig().setCountry(country);
		PnrResConfig pnrResConfig = pnrAllResForm.getPnrResConfig();
		String subResId = "";
		if("1122501".equals(pnrResConfig.getSpecialty())){
			PnrResConfigStationMgr pnrResConfigStationMgr = (PnrResConfigStationMgr)getBean("PnrResConfigStationMgr");
			PnrResConfigStation pnrResConfigStation = pnrAllResForm.getPnrResConfigStation();
			pnrResConfig.setSubResTable("pnr_res_station");
			String str = request.getParameter("propertyType");
			for(int i=0;i<=ResourceConstants.propertyTypeType.size();i++){
				if(ResourceConstants.propertyTypeType.get(i).equals(str)){
					pnrResConfigStation.setPropertyType(i);
					break;
				}
			}
			pnrResConfigStationMgr.save(pnrResConfigStation);
			pnrResConfig.setSubResId(pnrResConfigStation.getId());
			subResId  = pnrResConfigStation.getId();
		}else if("1122502".equals(pnrResConfig.getSpecialty())){
			PnrResLineMgr PnrResLineMgr = (PnrResLineMgr)getBean("PnrResLineMgr");
			PnrResLine pnrResLine = pnrAllResForm.getPnrResLine();
			pnrResConfig.setSubResTable("pnr_res_line");
			pnrResConfig.setEndLatitude(pnrResLine.getEndLatitude());
			pnrResConfig.setEndLongitude(pnrResLine.getEndLongitude());
			PnrResLineMgr.save(pnrResLine);
			subResId = pnrResLine.getId();
		}else if("1122503".equals(pnrResConfig.getSpecialty())){
			PnrResConfigStationMgr pnrResConfigStationMgr = (PnrResConfigStationMgr)getBean("PnrResConfigStationMgr");
			PnrResConfigStation pnrResConfigStation = pnrAllResForm.getPnrResConfigStation();
			pnrResConfig.setSubResTable("pnr_res_station");
			String str = request.getParameter("propertyType");
			for(int i=0;i<=ResourceConstants.propertyTypeType.size();i++){
				if(ResourceConstants.propertyTypeType.get(i).equals(str)){
					pnrResConfigStation.setPropertyType(i);
					break;
				}
			}
			pnrResConfigStationMgr.save(pnrResConfigStation);
			pnrResConfig.setSubResId(pnrResConfigStation.getId());
			subResId  = pnrResConfigStation.getId();
//			PnrResRepeatersMgr pnrResRepeatersMgr = (PnrResRepeatersMgr) getBean("PnrResRepeatersMgr");
//			PnrResRepeaters pnrResRepeaters = pnrAllResForm.getPnrResRepeaters();
//			pnrResConfig.setSubResTable("pnr_res_repeaters");
//			String str = request.getParameter("propertyType2");
//			for(int i=1;i<=ResourceConstants.propertyTypeType.size();i++){
//				if(ResourceConstants.propertyTypeType.get(i).equals(str)){
//					pnrResRepeaters.setPropertyType(i);
//					break;
//				}
//			}
//			pnrResRepeatersMgr.save(pnrResRepeaters);
//			pnrResConfig.setSubResId(pnrResRepeaters.getId());
//			subResId = pnrResRepeaters.getId();
		}else if("1122504".equals(pnrResConfig.getSpecialty())){
			PnrResIronMgr PnrResIronMgr = (PnrResIronMgr) getBean("PnrResIronMgr");
			PnrResIron pnrResIron = pnrAllResForm.getPnrResIron();
			pnrResConfig.setSubResTable("pnr_res_iron");
			PnrResIronMgr.save(pnrResIron);
			subResId = pnrResIron.getId();
		}else if("1122505".equals(pnrResConfig.getSpecialty())){
			PnrResJiekeMgr PnrResJiekeMgr = (PnrResJiekeMgr)getBean("PnrResJiekeMgr");
			PnrResJieke pnrResJieke = pnrAllResForm.getPnrResJieke();
			pnrResConfig.setSubResTable("pnr_res_jieke");
			PnrResJiekeMgr.save(pnrResJieke);
			subResId = pnrResJieke.getId();
		}else if("1122506".equals(pnrResConfig.getSpecialty())){
			
			PnrResWlanMgr pnrResWlanMgr = (PnrResWlanMgr)getBean("PnrResWlanMgr");
			PnrResWlan pnrResWlan = pnrAllResForm.getPnrResWlan();
			pnrResConfig.setSubResTable("pnr_res_wlan");
			String str = request.getParameter("propertyType3");
			for(int i=1;i<=ResourceConstants.propertyTypeType.size();i++){
				if(ResourceConstants.propertyTypeType.get(i).equals(str)){
					pnrResWlan.setPropertyType(i);
					break;
				}
			}
			pnrResWlanMgr.save(pnrResWlan);
			pnrResConfig.setSubResId(pnrResWlan.getId());
			subResId = pnrResWlan.getId();
		}else if("1122507".equals(pnrResConfig.getSpecialty())){
			PnrResFamilyBroadbandMgr pnrResFamilyBroadbandMgr = (PnrResFamilyBroadbandMgr)getBean("PnrResFamilyBroadbandMgr");
			PnrResFamilyBroadband pnrResFamilyBroadband = pnrAllResForm.getPnrResFamilyBroadband();
			pnrResConfig.setSubResTable("pnr_res_family_broadband");
			pnrResFamilyBroadbandMgr.save(pnrResFamilyBroadband);
			subResId = pnrResFamilyBroadband.getId();
		}else if("1122511".equals(pnrResConfig.getSpecialty())){
			PnrResConfigStationMgr pnrResConfigStationMgr = (PnrResConfigStationMgr)getBean("PnrResConfigStationMgr");
			PnrResConfigStation pnrResConfigStation = pnrAllResForm.getPnrResConfigStation();
			pnrResConfig.setSubResTable("pnr_res_station");
			String str = request.getParameter("propertyType");
			for(int i=0;i<=ResourceConstants.propertyTypeType.size();i++){
				if(ResourceConstants.propertyTypeType.get(i).equals(str)){
					pnrResConfigStation.setPropertyType(i);
					break;
				}
			}
			pnrResConfigStationMgr.save(pnrResConfigStation);
			pnrResConfig.setSubResId(pnrResConfigStation.getId());
			subResId  = pnrResConfigStation.getId();
		}
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		pnrResConfig.setCreator(sessionForm.getUserid());
		pnrResConfig.setCreateTime(StaticMethod.getCurrentDateTime());
		pnrResConfig.setSubResId(subResId);
		pnrResConfig.setInspectCycle(inspectCycle);
		pnrResConfig.setTlInspectFlag("0");
		pnrResConfiMgr.save(pnrResConfig);
		ActionForward actionForward = new ActionForward();
		actionForward.setPath("/PnrResConfig.do?method=search");
		actionForward.setRedirect(true);
		return actionForward;
	}
	
	/**
	 * 资源信息的修改
	 */
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PnrResConfigMgr pnrResConfiMgr = (PnrResConfigMgr) getBean("PnrResConfigMgr");
		PnrAllResForm pnrAllResForm = (PnrAllResForm)form;
		PnrResConfig pnrResConfig = pnrAllResForm.getPnrResConfig();
		/**
		 * 资源修改日志--start
		 */
		String testName = request.getParameter("testName");
		String testLevel= request.getParameter("testLevel");
		PnrResChangeHistory  pnrResChangeHistory = new PnrResChangeHistory();
		boolean flag = false;
		int count = 1,sum = 0;
		if(!testName.equals(pnrResConfig.getResName())){
			//存修改后的名称
			pnrResChangeHistory.setNewName(pnrResConfig.getResName());
			//存修改前的名称
			pnrResChangeHistory.setOldName(testName);
			flag = true;
			count = 1;
			sum++;
		}
		if(!testLevel.equals(pnrResConfig.getResLevel())){
			//存修改前的级别
			pnrResChangeHistory.setOldLevel(testLevel);
			//存修改后的级别 
			pnrResChangeHistory.setNewLevel(pnrResConfig.getResLevel());
			flag = true;
			count=2;
			sum++;
		}
		if(flag){
			//存资源id 
			pnrResChangeHistory.setResId(pnrResConfig.getId());
			//存修改时间
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			pnrResChangeHistory.setChangeTime(sdf.parse(sdf.format(new Date())));
			//存操作人id
			TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
			pnrResChangeHistory.setChangePerson(sessionForm.getUserid());
			//存修改类型
			if(sum==1 && count==1){
			pnrResChangeHistory.setChangeState("1");
			}
			if(sum==1&&count==2){
				pnrResChangeHistory.setChangeState("2");	
			}
			if(sum==2){
				pnrResChangeHistory.setChangeState("3");	
			}
			IPnrResChangeHistoryMgr  pnrResChangeHistoryMgr = (IPnrResChangeHistoryMgr)getBean("PnrResChangeHistoryMgr");
			pnrResChangeHistoryMgr.save(pnrResChangeHistory);
			
		}
		/**
		 * 资源修改日志--end
		 */
		
		
		/**
		 * 资源周期改变，改变相应的原任务--start
		 */
			if(!testLevel.equals(pnrResConfig.getResLevel())&&!"week".equals(pnrResConfig.getResLevel())&&!"halfOfMonth".equals(pnrResConfig.getResLevel())){    //类别不相同，周期已改变，开始修改原任务
			//根据修改后的类别和当前修改时间，计算出新原任务的开始和结束时间	(目前不对周期为半月、周的进行改变)
				String[] newTime = getNewTimeByCycle(pnrResConfig.getInspectCycle());
				
			//根据资源的id，修改原任务周期和相应的时间
				IInspectPlanResMgr iInspectPlanResMgr = (IInspectPlanResMgr)getBean("inspectPlanResMgr");
				iInspectPlanResMgr.changePlanResCycle(pnrResConfig.getId(), newTime, pnrResConfig.getInspectCycle());
			}
		/**
		 * 资源周期改变，改变相应的原任务--end
		 */
		if("1122501".equals(pnrResConfig.getSpecialty())){
			PnrResConfigStationMgr pnrResConfigStationMgr = (PnrResConfigStationMgr)getBean("PnrResConfigStationMgr");
			PnrResConfigStation pnrResConfigStation = pnrAllResForm.getPnrResConfigStation();
			pnrResConfigStation.setId(pnrResConfig.getSubResId());
			String str = request.getParameter("propertyType");
			for(int i=0;i<=ResourceConstants.propertyTypeType.size();i++){
				if(ResourceConstants.propertyTypeType.get(i).equals(str)){
					pnrResConfigStation.setPropertyType(i);
					break;
				}
			}
			pnrResConfigStationMgr.save(pnrResConfigStation);
		}else if("1122502".equals(pnrResConfig.getSpecialty())){
			PnrResLineMgr PnrResLineMgr = (PnrResLineMgr)getBean("PnrResLineMgr");
			PnrResLine pnrResLine = pnrAllResForm.getPnrResLine();
			pnrResLine.setId(pnrResConfig.getSubResId());
			pnrResConfig.setEndLatitude(pnrResLine.getEndLatitude());
			pnrResConfig.setEndLongitude(pnrResLine.getEndLongitude());
			PnrResLineMgr.save(pnrResLine);
		}else if("1122503".equals(pnrResConfig.getSpecialty())){
			PnrResConfigStationMgr pnrResConfigStationMgr = (PnrResConfigStationMgr)getBean("PnrResConfigStationMgr");
			PnrResConfigStation pnrResConfigStation = pnrAllResForm.getPnrResConfigStation();
			pnrResConfigStation.setId(pnrResConfig.getSubResId());
			String str = request.getParameter("propertyType");
			for(int i=0;i<=ResourceConstants.propertyTypeType.size();i++){
				if(ResourceConstants.propertyTypeType.get(i).equals(str)){
					pnrResConfigStation.setPropertyType(i);
					break;
				}
			}
			pnrResConfigStationMgr.save(pnrResConfigStation);
//			PnrResRepeatersMgr pnrResRepeatersMgr = (PnrResRepeatersMgr) getBean("PnrResRepeatersMgr");
//			PnrResRepeaters pnrResRepeaters = pnrAllResForm.getPnrResRepeaters();
//			pnrResRepeaters.setId(pnrResConfig.getSubResId());
//			String str = request.getParameter("propertyType2");
//			for(int i=1;i<=ResourceConstants.propertyTypeType.size();i++){
//				if(ResourceConstants.propertyTypeType.get(i).equals(str)){
//					pnrResRepeaters.setPropertyType(i);
//					break;
//				}
//			}
//			pnrResRepeatersMgr.save(pnrResRepeaters);
		}else if("1122504".equals(pnrResConfig.getSpecialty())){
			PnrResIronMgr PnrResIronMgr = (PnrResIronMgr) getBean("PnrResIronMgr");
			PnrResIron pnrResIron = pnrAllResForm.getPnrResIron();
			pnrResIron.setId(pnrResConfig.getSubResId());
			PnrResIronMgr.save(pnrResIron);
		}else if("1122505".equals(pnrResConfig.getSpecialty())){
			PnrResJiekeMgr PnrResJiekeMgr = (PnrResJiekeMgr)getBean("PnrResJiekeMgr");
			PnrResJieke pnrResJieke = pnrAllResForm.getPnrResJieke();
			pnrResJieke.setId(pnrResConfig.getSubResId());
			PnrResJiekeMgr.save(pnrResJieke);
		}else if("1122506".equals(pnrResConfig.getSpecialty())){
			
			PnrResWlanMgr pnrResWlanMgr = (PnrResWlanMgr)getBean("PnrResWlanMgr");
			PnrResWlan pnrResWlan = pnrAllResForm.getPnrResWlan();
			pnrResWlan.setId(pnrResConfig.getSubResId());
			String str = request.getParameter("propertyType3");
			for(int i=1;i<=ResourceConstants.propertyTypeType.size();i++){
				if(ResourceConstants.propertyTypeType.get(i).equals(str)){
					pnrResWlan.setPropertyType(i);
					break;
				}
			}
			pnrResWlanMgr.save(pnrResWlan);
		}else if("1122507".equals(pnrResConfig.getSpecialty())){
			PnrResFamilyBroadbandMgr pnrResFamilyBroadbandMgr = (PnrResFamilyBroadbandMgr)getBean("PnrResFamilyBroadbandMgr");
			PnrResFamilyBroadband pnrResFamilyBroadband = pnrAllResForm.getPnrResFamilyBroadband();
			pnrResFamilyBroadband.setId(pnrResConfig.getSubResId());
			pnrResFamilyBroadbandMgr.save(pnrResFamilyBroadband);
		}
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		pnrResConfig.setCreator(sessionForm.getUserid());
		pnrResConfig.setCreateTime(StaticMethod.getCurrentDateTime());
		String city = request.getParameter("region");
		String country = request.getParameter("city");
		pnrAllResForm.getPnrResConfig().setCity(city);
		pnrAllResForm.getPnrResConfig().setCountry(country);
		pnrResConfig.setCity(city);
		pnrResConfig.setCountry(country);
		pnrResConfig.setTlInspectFlag("0");//非线路巡检状态 0
		pnrResConfiMgr.save(pnrResConfig);
		ActionForward actionForward = new ActionForward();
		actionForward.setPath("/PnrResConfig.do?method=search");
		actionForward.setRedirect(true);
		return actionForward;
	}
	
	/**
	 * 资源导入模板下载
	 */
	public void download(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
//				String rootPath = request.getRealPath("/partner/partnerRes");
				File file = new File(this.getServletContext().getRealPath(""),"partner"+File.separator+"partnerRes"+File.separator+"partnerResTemplate.zip");
				String fileName = "巡检资源模板.zip";
				// 读到流中
				InputStream inStream = new FileInputStream(file);// 文件的存放路径
				// 设置输出的格式
				response.reset();
				response.setContentType("application/x-msdownload;charset=GBK");
				response.setCharacterEncoding("GB2312");
				response.setHeader("Content-Disposition", "attachment; filename="
						+ new String(fileName.getBytes("gbk"), "iso8859-1"));
				// 循环取出流中的数据
				byte[] b = new byte[1024];
				int len = 0;
				while ((len = inStream.read(b)) > 0)
					response.getOutputStream().write(b, 0, len);
				inStream.close();
			}
   
	
	/**
	 * 导入数据
	 */
	public ActionForward importData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
			response.setCharacterEncoding("utf-8");
			//得到当前省的Id
			PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
			String province = pnrBaseAreaIdList.getRootAreaId();
			//获得是哪个专业类型
			String specialty = request.getParameter("specialty");
			TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
			String userid = sessionForm.getUserid();
			PnrAllResForm pnrAllResForm = (PnrAllResForm)form;
			FormFile formFile=pnrAllResForm.getImportFile();
			PrintWriter writer=null;
			try {
				PnrResConfigMgr pnrResConfiMgr = (PnrResConfigMgr) getBean("PnrResConfigMgr");
				writer=response.getWriter();
				ImportResult result=pnrResConfiMgr.importFromFile(formFile, province,specialty,userid);
				if (result.getResultCode().equals("200")) {
					writer.write(
						new Gson().toJson(new ImmutableMap.Builder<String, String>()
							.put("success", "true")
							.put("msg", "ok")
							.put("infor", "导入成功,共计导入"+result.getImportCount()+"条记录").build()));
				}
			} catch (Exception e) {
				e.printStackTrace();
				writer.write(
						new Gson().toJson(new ImmutableMap.Builder<String, String>()
								.put("success", "false")
								.put("msg", "failure")
								.put("infor", e.getMessage()).build()));
			}finally{
				if(writer != null){
					writer.close();
				}
			}
			return null;
		}
	
	
	/**
	 * Excel导出
	 */
	public void excelExport(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		PnrResConfigMgr pnrResConfiMgr = (PnrResConfigMgr) getBean("PnrResConfigMgr");
		response.setContentType("application/octet-stream");
		response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("巡检资源.xls", "utf-8"));
		//先得到是哪个专业类型的
		String specialty = request.getParameter("specialty");
		String city = request.getParameter("region1");
		String country = request.getParameter("city1");
		List<String> list = new ArrayList<String>();
		list.add(city);
		list.add(country);
	    //得到一个输出流
		OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
		pnrResConfiMgr.excelExport(specialty, list, toClient);
		toClient.flush();
	    toClient.close();
	}
	/**
	 * 根据周期计算出新原任务的开始和结束时间 
	 * @param cycle
	 * @return
	 */
	public String[] getNewTimeByCycle(String cycle){
			
		String[] str = new String[2];
		
		//获取当前时间
		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		SimpleDateFormat sdfAll = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
		String nowMonth = sdf.format(new Date());
		String newDay = sdfAll.format(new Date());
		String nowYear = sdfYear.format(new Date());
			//周期一年
			if("year".equals(cycle)){
				str[1]=nowYear+"-12-31 23:59:59";
			}
			//周期半年
			if("halfOfYear".equals(cycle)){
				if(Integer.parseInt(nowMonth)>=7){
					
					str[1]=nowYear+"-12-31 23:59:59";
				}else{
					str[1]=nowYear+"-6-30 23:59:59";
				}
			}
			//周期一个季度
			if("quarter".equals(cycle)){
				if(Integer.parseInt(nowMonth)<=3){
					str[1]=nowYear+"-3-31 23:59:59";
				}
				if(Integer.parseInt(nowMonth)>3&&Integer.parseInt(nowMonth)<=6){
					str[1]=nowYear+"-6-30 23:59:59";
				}
				if(Integer.parseInt(nowMonth)>6&&Integer.parseInt(nowMonth)<=9){
					str[1]=nowYear+"-9-30 23:59:59";
				}
				if(Integer.parseInt(nowMonth)>9&&Integer.parseInt(nowMonth)<=12){
					str[1]=nowYear+"-12-31 23:59:59";
				}
			}
			//周期两个月	
			if("doubleMonth".equals(cycle)){
				if(Integer.parseInt(nowMonth)<=2){
					boolean flag = false;
					if(Integer.parseInt(nowYear)%4==0 && Integer.parseInt(nowYear)%100!=0){
						flag = true;
					}
					if(Integer.parseInt(nowYear)%100==0 && Integer.parseInt(nowYear)%400==0){
						flag = true;
					}
					if(flag){
						str[1]=nowYear+"-2-29 23:59:59";
					}else{
						str[1]=nowYear+"-2-28 23:59:59";
					}
					
				}
				if(Integer.parseInt(nowMonth)>2&&Integer.parseInt(nowMonth)<=4){
					str[1]=nowYear+"-4-30 23:59:59";
				}
				if(Integer.parseInt(nowMonth)>4&&Integer.parseInt(nowMonth)<=6){
					str[1]=nowYear+"-6-30 23:59:59";
				}
				if(Integer.parseInt(nowMonth)>6&&Integer.parseInt(nowMonth)<=8){
					str[1]=nowYear+"-8-31 23:59:59";
				}
				if(Integer.parseInt(nowMonth)>8&&Integer.parseInt(nowMonth)<=10){
					str[1]=nowYear+"-10-31 23:59:59";
				}
				if(Integer.parseInt(nowMonth)>10&&Integer.parseInt(nowMonth)<=12){
					str[1]=nowYear+"-12-31 23:59:59";
				}
			}
			//周期一个月 	
			if("month".equals(cycle)){
				if("1".equals(nowMonth)||"3".equals(nowMonth)||"5".equals(nowMonth)||"7".equals(nowMonth)||"8".equals(nowMonth)||"10".equals(nowMonth)||"12".equals(nowMonth)){
					str[1]=nowYear+"-"+nowMonth+"-31 23:59:59";
				} else if("2".equals(nowMonth)){
					boolean flag = false;
					if(Integer.parseInt(nowYear)%4==0 && Integer.parseInt(nowYear)%100!=0){
						flag = true;
					}
					if(Integer.parseInt(nowYear)%100==0 && Integer.parseInt(nowYear)%400==0){
						flag = true;
					}
					if(flag){
						str[1]=nowYear+"-2-29 23:59:59";
					}else{
						str[1]=nowYear+"-2-28 23:59:59";
					}
				}else{
					str[1]=nowYear+"-"+nowMonth+"-30 23:59:59";
				}
			}
			str[0]=newDay;
		return str;
	}
}
