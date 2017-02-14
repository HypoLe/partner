package com.boco.eoms.partner.netresource.action;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;

import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.system.dept.model.TawSystemDept;
import com.boco.eoms.commons.system.dept.service.ITawSystemDeptManager;
import com.boco.eoms.commons.system.session.form.TawSystemSessionForm;
import com.boco.eoms.deviceManagement.common.pojo.EomsSearch;
import com.boco.eoms.deviceManagement.common.utils.CommonConstants;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.boco.eoms.partner.baseinfo.util.PnrBaseAreaIdList;
import com.boco.eoms.partner.netresource.action.form.PnrbsbtResForm;
import com.boco.eoms.partner.netresource.service.AccessNetworkQuipmentMgr;
import com.boco.eoms.partner.netresource.service.AccessNetworkRoomMgr;
import com.boco.eoms.partner.netresource.service.BsBtApMgr;
import com.boco.eoms.partner.netresource.service.BsBtConfigMgr;
import com.boco.eoms.partner.netresource.service.BsBtQuipmentMgr;
import com.boco.eoms.partner.netresource.service.IEomsService;
import com.boco.eoms.partner.netresource.service.KeyCustomerRoomMgr;
import com.boco.eoms.partner.netresource.service.OutdoorCabinetMgr;
import com.boco.eoms.partner.netresource.service.RoomResourceMgr;
import com.boco.eoms.partner.netresource.service.TowerAntennasMgr;
import com.boco.eoms.partner.netresource.util.ImportResult;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.googlecode.genericdao.search.SearchResult;

/** 
 * Description:  
 * Copyright:   Copyright (c)2013 
 * Company:     BOCO
 * @author:     chenbing 
 * @version:    1.0 
 * Create at:   2013/7/1 
 */
public class PnrbsbtAction extends BaseAction {
	
	/**
	 * 用于设备更新 ，这是全表检索的
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward useSyn(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		String param = request.getParameter("param");
		
		if(param.equals("1")){
			//基站机房设备
			BsBtQuipmentMgr bsBtQuipmentService = (BsBtQuipmentMgr) getBean("bsBtQuipmentService");
			
			bsBtQuipmentService.synchronousBsBtQuitment();
			
		}else if(param.equals("2")){
			//接入网机房设备
			AccessNetworkQuipmentMgr anQuipmentService = (AccessNetworkQuipmentMgr) getBean("anQuipmentService");
			anQuipmentService.synchronousAnQuitment();
		}		
		response.getWriter().print("true");
		
		return null;
	}	
	/**
	 * 设备更新页面
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward useSynPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		
		
		
		return mapping.findForward("useSynPage");
	}	
	/**
	 * 
	 * 方法说明： 跳转查询页面 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward gotoListPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		String model = StaticMethod.null2String(request.getParameter("model"));
		Class klass = Class.forName(model);
		String klassName = model.substring(model.lastIndexOf(".")+1,model.length());
		String disParam = CommonUtils.lowerFirst(klassName)+"List";
		
		IEomsService eomsService = (IEomsService) this.getBean("eomsService");
		
		EomsSearch eomsSearch = new EomsSearch();
		eomsSearch.setSearchClass(klass);
		int firstResult = CommonUtils.getFirstResultOfDisplayTag(request, disParam);
		eomsSearch.setFirstResult(firstResult * CommonConstants.PAGE_SIZE);
		eomsSearch.setMaxResults(CommonConstants.PAGE_SIZE);
		eomsSearch = CommonUtils.getSqlFromRequestMap(request, eomsSearch);
		
//      初始化地市-站点查询
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
        String userId = sessionForm.getUserid();
        String deptId = sessionForm.getDeptid();
 	    ITawSystemDeptManager deptSys = (ITawSystemDeptManager)getBean("ItawSystemDeptManager");
 	    String deptIdString="";
 	    if(deptId.length()>=5)
 	    {
 	    	deptIdString=deptId.substring(0,5);
 	    }
 	    TawSystemDept list= deptSys.getDeptinfobydeptid(deptIdString,"0");
        String areaId ="";
        if(list !=null||userId.equals("admin"))
        {
        	areaId= list.getAreaid();
        	eomsSearch.addFilterEqual("city", areaId);
        }
        
//      初始化地市-end
		//search.addSortDesc("createTime");
		SearchResult searchResult = eomsService.searchAndCount(eomsSearch);
		List modelList = searchResult.getResult();
		
		request.setAttribute(disParam,modelList);
		request.setAttribute("model", model);
		request.setAttribute("pagesize", CommonConstants.PAGE_SIZE);
		request.setAttribute("size", searchResult.getTotalCount());
		
		//获取最近同步数据结果
		// Map<String,Object> dataSynchResult = this.getDataSynchInfo(klassName);
	 	//request.setAttribute("dataSynchResult", dataSynchResult);
		//设置是否开启手动Excel同步
		// Map<String,String> map = DataSynchConstant.getHandleSynchConfig();
		//String openHandleSynch = map.get("is-open");
		//request.setAttribute("openHandleSynch", openHandleSynch);
		
     	return mapping.findForward("goto"+klassName+"ListPage");
	}
	
	/**
	 * 
	 * 方法说明： 跳转列表页面-基站机房资源 
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward gotoListBsBtPage(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		//记录哪个人
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String userid = sessionForm.getUserid();
		request.setAttribute("userid", userid);
		
		return mapping.findForward("gotoSdBsBtResourceListPage");
	}
	
	 /**
     * 网络资源导入模板下载
     */
    public void downloadBsbt(ActionMapping mapping, ActionForm form,
                         HttpServletRequest request, HttpServletResponse response)
            throws Exception {
//				String rootPath = request.getRealPath("/partner/partnerRes");
        File file = new File(this.getServletContext().getRealPath(""),"partner"+File.separator+"partnerRes"+File.separator+"partnerBsbtTemplate.zip");
        String fileName = "资源模板.zip";
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
	 * 导入数据-新-基站机房资源 与该机房设备;
	 * 导入数据 接入网设备；
	 */
	public ActionForward importBsBtData(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception {
		response.setCharacterEncoding("utf-8");
		//得到当前省的Id
		PnrBaseAreaIdList pnrBaseAreaIdList = (PnrBaseAreaIdList)getBean("pnrBaseAreaIdList");
		String province = pnrBaseAreaIdList.getRootAreaId();
		//获得是哪个专业类型
		String specialty= request.getParameter("specialty");
		//记录哪个人
		TawSystemSessionForm sessionForm = (TawSystemSessionForm) request.getSession().getAttribute("sessionform");
		String userid = sessionForm.getUserid();
		//路径
		 String osPath=request.getSession().getServletContext().getRealPath("");
		PnrbsbtResForm pnrbsbtResForm = (PnrbsbtResForm)form;		
		FormFile formFile=pnrbsbtResForm.getImportFile();
		
		PrintWriter writer=null;

		ImportResult result =null;
		ImportResult result2 = null;
		ImportResult anrResult = null;
		ImportResult apResult = null;
		ImportResult ocResult = null;
		ImportResult rerResult = null;
		ImportResult towerResult = null;
		ImportResult anQuipmentResult = null;
		ImportResult keyCustomerRoomResult = null;
		
		//添加重点机房
		
		
		int flag =1;
		String errMsg="";
		
		String flagdiv = "";

		try {
			
			writer=response.getWriter();
			
			
			if(specialty.equals("1122501")){
				//基站机房
				BsBtConfigMgr bsBtConfigMgr = (BsBtConfigMgr) getBean("sdBsbtConfigService");
				result=bsBtConfigMgr.importBsBtFromFile(formFile, province,specialty,userid,osPath);
				flag=2;
				/*result2=bsBtConfigMgr.importBsBtEqFile(formFile, province,specialty,userid,osPath);
				flag=3;*/
			}else if(specialty.equals("1122505")){
				//接入网
				AccessNetworkRoomMgr anrMgr =(AccessNetworkRoomMgr)getBean("anrService");				
				anrResult=anrMgr.importAnrFile(formFile, province,specialty,userid,osPath);
				flag=4;
				/*anQuipmentResult=anrMgr.importAnrEqFile(formFile, province,specialty,userid,osPath);
				flag=8;*/
			}else if(specialty.equals("1122506")){
				
				//wlan
				BsBtApMgr apMgr = (BsBtApMgr)getBean("bsbtApService");		
				apResult=apMgr.importBsBtApFromFile(formFile, province,specialty,userid,osPath);
				flag=5;
			}else if(specialty.equals("1122509")){
				
				//添加重点客户机房
				KeyCustomerRoomMgr apMgr = (KeyCustomerRoomMgr)getBean("keyCustomerRoomService");		
				keyCustomerRoomResult=apMgr.importKeyCustomerRoomFromFile(formFile, province,specialty,userid,osPath);
				flag=10;
				
			}else if(specialty.equals("1122510")){
				//室外箱体
				OutdoorCabinetMgr ocMgr = (OutdoorCabinetMgr)getBean("outdoorCabinetService");		
				ocResult=ocMgr.importOCFromFile(formFile, province,specialty,userid,osPath);
				flag=6;
				
				
			}else if(specialty.equals("1122503")){
				//室分
				RoomResourceMgr rerMgr = (RoomResourceMgr)getBean("roomResourceService");		
				rerResult=rerMgr.importRerFromFile(formFile, province,specialty,userid,osPath);
				flag=7;
				
				
			}else if(specialty.equals("1122504")){
				//铁塔及天馈
				TowerAntennasMgr towerMgr = (TowerAntennasMgr)getBean("towerAntennasService");		
				towerResult=towerMgr.importTowerAntennasFromFile(formFile, province,specialty,userid,osPath);
				flag=9;
				
				
			}
			
			if(flag==1){
				
			}else if(flag==2){
				
				if (result.getResultCode().equals("200")) {
					String msg ="基站资源共计:成功导入"+(result.getImportCount()-result.getErrorCount())+"条；<br>"+
					"数据需要修改，未导入"+result.getErrorCount()+"条.";
					
					int error1 = result.getErrorCount();					
					if(error1>0){
						
						flagdiv = "1";
						
					}
																
					writer.write(
							new Gson().toJson(new ImmutableMap.Builder<String, String>()
									.put("success", "true")
									.put("msg", "ok")
									.put("infor", msg)
									.put("flagdiv",flagdiv)
									.build()));
					
				}
			}else if(flag==3){
				/*
				if (result.getResultCode().equals("200")&&result2.getResultCode().equals("200")) {
					String msg ="基站共计:成功导入"+(result.getImportCount()-result.getErrorCount())+"条；<br>"+
					"数据需要修改，未导入"+result.getErrorCount()+"条;<br>"
					+"网络设备,共计导入"+(result2.getImportCount()-result2.getErrorCount())+"条;<br>"
					+"数据需要修改，未导入"+result2.getErrorCount()+"条.";
					
					int error1 = result.getErrorCount();
					int error2 = result.getErrorCount();
					
					if(error1>0){
						
						flagdiv = "1";
						
					}
					if(error2>0){
						
						flagdiv = "2";
						
					}
					
					if(error2>0&&error1>0){
						
						flagdiv = "1,2";
						
					}
										
					writer.write(
							new Gson().toJson(new ImmutableMap.Builder<String, String>()
									.put("success", "true")
									.put("msg", "ok")
									.put("infor", msg)
									.put("flagdiv",flagdiv)
									.build()));
					
				}*/
				
			}else if(flag==4){
				if (anrResult.getResultCode().equals("200")) {
					String msg ="接入网资源共计:成功导入"+(anrResult.getImportCount()-anrResult.getErrorCount())+"条；<br>"+
					"数据需要修改，未导入"+anrResult.getErrorCount()+"条.";
					
					int error4 = anrResult.getErrorCount();					
					if(error4>0){
						
						flagdiv = "4";
						
					}
																
					writer.write(
							new Gson().toJson(new ImmutableMap.Builder<String, String>()
									.put("success", "true")
									.put("msg", "ok")
									.put("infor", msg)
									.put("flagdiv",flagdiv)
									.build()));
					
				}
			
			}else if(flag==5){
				if (apResult.getResultCode().equals("200")) {
					String msg ="WLAN资源共计:成功导入"+(apResult.getImportCount()-apResult.getErrorCount())+"条；<br>"+
					"数据需要修改，未导入"+apResult.getErrorCount()+"条.";
					
					int error5 = apResult.getErrorCount();					
					if(error5>0){
						
						flagdiv = "5";
						
					}
																
					writer.write(
							new Gson().toJson(new ImmutableMap.Builder<String, String>()
									.put("success", "true")
									.put("msg", "ok")
									.put("infor", msg)
									.put("flagdiv",flagdiv)
									.build()));
					
				}
				
			}else if(flag==10){ //添加重点客户机房
				if (keyCustomerRoomResult.getResultCode().equals("200")) {
					String msg ="重点客户机房资源共计:成功导入"+(keyCustomerRoomResult.getImportCount()-keyCustomerRoomResult.getErrorCount())+"条；<br>"+
					"数据需要修改，未导入"+keyCustomerRoomResult.getErrorCount()+"条.";
					
					int error5 = keyCustomerRoomResult.getErrorCount();					
					if(error5>0){
						
						flagdiv = "9";
						
					}
																
					writer.write(
							new Gson().toJson(new ImmutableMap.Builder<String, String>()
									.put("success", "true")
									.put("msg", "ok")
									.put("infor", msg)
									.put("flagdiv",flagdiv)
									.build()));
					
				}
				
			}else if(flag==6){
				if (ocResult.getResultCode().equals("200")) {
					String msg ="室外箱体共计:成功导入"+(ocResult.getImportCount()-ocResult.getErrorCount())+"条；<br>"+
					"数据需要修改，未导入"+ocResult.getErrorCount()+"条.";
					
					int error6 = ocResult.getErrorCount();					
					if(error6>0){
						
						flagdiv = "6";
						
					}
																
					writer.write(
							new Gson().toJson(new ImmutableMap.Builder<String, String>()
									.put("success", "true")
									.put("msg", "ok")
									.put("infor", msg)
									.put("flagdiv",flagdiv)
									.build()));
				}
				
			}else if(flag==7){
				
				if (rerResult.getResultCode().equals("200")) {
					String msg ="室分共计:成功导入"+(rerResult.getImportCount()-rerResult.getErrorCount())+"条；<br>"+
					"数据需要修改，未导入"+rerResult.getErrorCount()+"条.";

					int error7 = rerResult.getErrorCount();					
					if(error7>0){
						
						flagdiv = "7";
						
					}
																
					writer.write(
							new Gson().toJson(new ImmutableMap.Builder<String, String>()
									.put("success", "true")
									.put("msg", "ok")
									.put("infor", msg)
									.put("flagdiv",flagdiv)
									.build()));
				}
			}else if(flag==8){
				
				/*if (anQuipmentResult.getResultCode().equals("200")) {
					String msg ="接入网机房共计:成功导入"+(anrResult.getImportCount()-anrResult.getErrorCount())+"条；<br>"+
					"数据需要修改，未导入"+anrResult.getErrorCount()+"条;<br>"
					+"接入网设备,共计导入"+(anQuipmentResult.getImportCount()-anQuipmentResult.getErrorCount())+"条;<br>"
					+"数据需要修改，未导入"+anQuipmentResult.getErrorCount()+"条.";
					int error3 = anrResult.getErrorCount();
					int error4 = anQuipmentResult.getErrorCount();
					
					if(error3>0){
						
						flagdiv = "3";
						
					}
					if(error4>0){
						
						flagdiv = "4";
						
					}
					
					if(error3>0&&error4>0){
						
						flagdiv = "3,4";
						
					}
										
					writer.write(
							new Gson().toJson(new ImmutableMap.Builder<String, String>()
									.put("success", "true")
									.put("msg", "ok")
									.put("infor", msg)
									.put("flagdiv",flagdiv)
									.build()));
				}*/
			}else if(flag==9){
				
				if (towerResult.getResultCode().equals("200")) {
					String msg ="铁塔及天馈共计:成功导入"+(towerResult.getImportCount()-towerResult.getErrorCount())+"条；<br>"+
					"数据需要修改，未导入"+towerResult.getErrorCount()+"条.";

					int error8 = towerResult.getErrorCount();					
					if(error8>0){
						flagdiv = "8";
					}
					writer.write(
							new Gson().toJson(new ImmutableMap.Builder<String, String>()
									.put("success", "true")
									.put("msg", "ok")
									.put("infor", msg)
									.put("flagdiv",flagdiv)
									.build()));
				}
			}
	

		} catch (Exception e) {
			e.printStackTrace();
			
		 /*	if(flag==2){
				errMsg=errMsg+"已导入基站机房资源"+result.getImportCount()+"条;基站机房设备";
			}*/
			writer.write(
					new Gson().toJson(new ImmutableMap.Builder<String, String>()
							.put("success", "false")
							.put("msg", "failure")
							.put("infor", errMsg+e.getMessage()).build()));
			
		}finally{
			if(writer != null){
				writer.close();
			}
		}
		return null;
	}
	
	
}
