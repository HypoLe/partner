package com.boco.activiti.partner.process.webapp.action;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.activiti.partner.process.model.FaultStatModel;
import com.boco.activiti.partner.process.model.SchemeRate;
import com.boco.activiti.partner.process.model.SchemeRateRejectModel;
import com.boco.activiti.partner.process.po.ChildSceneReportsModel;
import com.boco.activiti.partner.process.service.IPnrTransferNewPrecheckService;
import com.boco.activiti.partner.process.service.ISchemeRateService;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.partner.process.util.CommonUtils;
import com.boco.eoms.sheet.base.webapp.action.SheetAction;
import com.google.common.base.Strings;

public class SchemeRateAction  extends SheetAction {
	
	public ActionForward schemeRateActionList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		String county = StaticMethod.nullObject2String(request.getParameter("county"));
		String startTime=StaticMethod.nullObject2String(request.getParameter("startTime"));
		String endTime=StaticMethod.nullObject2String(request.getParameter("endTime"));
		String type=StaticMethod.nullObject2String(request.getParameter("type"));
		DateFormat sFormat = new SimpleDateFormat("yyyy-MM");
		ISchemeRateService schemeRateService = (ISchemeRateService) getBean("schemeRateService");
		List<SchemeRate> schemeRatelist=null;
		String size="";
		if("1".equals(type)){
			Map<String,Object> map=schemeRateService.schemeRateList(county,startTime,endTime);
			 size=map.get("size").toString();
			 schemeRatelist=(List<SchemeRate>)map.get("schemeRateList");
		}else{
			 size="0";
		}
		//request.setAttribute("size", size);
		if("".equals(startTime) && "".equals(endTime)){
			Date day=new Date();
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM"); 
			SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd"); 
			startTime=df.format(day)+"-01";
			endTime=df2.format(day);
		}
		request.setAttribute("schemeRatelist", schemeRatelist);
		request.setAttribute("startTime", startTime);
		request.setAttribute("endTime", endTime);
		request.setAttribute("pageSize", size);
		request.setAttribute("total",Integer.parseInt(size));
		return mapping.findForward("schemeRateList");
	}
	public ActionForward schemeRateRejectList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		String city = StaticMethod.nullObject2String(request.getParameter("city"));
		String startTime=StaticMethod.nullObject2String(request.getParameter("startTime"));
		String endTime=StaticMethod.nullObject2String(request.getParameter("endTime"));
		String themeinterface=StaticMethod.nullObject2String(request.getParameter("themeinterface"));
		
		int pageSize = CommonUtils.PAGE_SIZE;
		String pageIndexString = CommonUtils.getPageIndexWithDisplaytag(
				request, "taskList");
		int firstResult = Strings.isNullOrEmpty(pageIndexString) ? 0 : Integer
				.valueOf(pageIndexString).intValue() - 1;
		int endResult = Strings.isNullOrEmpty(pageIndexString) ? 1 : Integer
				.valueOf(pageIndexString).intValue();
		
		ISchemeRateService schemeRateService = (ISchemeRateService) getBean("schemeRateService");
		List<SchemeRateRejectModel> schemeRateRejectModelList=schemeRateService.schemeRateRejectList(city, startTime,endTime, themeinterface, pageSize, firstResult, endResult);
		int total=schemeRateService.schemeRateRejectTotal(city, startTime,endTime, themeinterface);
		request.setAttribute("schemeRateRejectModelList", schemeRateRejectModelList);
		request.setAttribute("startTime", startTime);
		request.setAttribute("endTime", endTime);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("themeinterface", themeinterface);
		request.setAttribute("total",total);
		return mapping.findForward("schemeRateRejectList");
		
		
	}
	
	/**
	 * 	 子场景工单统计
	 	 * @author WANGJUN
	 	 * @title: childSceneReports
	 	 * @date Jul 21, 2016 9:30:07 AM
	 	 * @param mapping
	 	 * @param form
	 	 * @param request
	 	 * @param response
	 	 * @return
	 	 * @throws Exception ActionForward
	 */
	public ActionForward childSceneReports(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String returnView = "childSceneReports";
		String queryFlay = StaticMethod.null2String(request.getParameter("queryFlay"));
		List<ChildSceneReportsModel> taskList = null;
		int total = 0;
		if("1".equals(queryFlay)){
			//开始时间
			String sheetAcceptLimit = StaticMethod.null2String(request.getParameter("sheetAcceptLimit"));
			//结束时间
			String sheetCompleteLimit = StaticMethod.null2String(request.getParameter("sheetCompleteLimit"));
			//报表类型标识，是地市查询还是区县查询
			String flag = StaticMethod.null2String(request.getParameter("flag"));
			//地市的父ID
			String pId = StaticMethod.null2String(request.getParameter("pId"));
			
//			if("".equals(sheetAcceptLimit)){ //默认当前时间上个月 
//				 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
//				 Calendar cal = Calendar.getInstance();
//				 Date date = new Date();
//			     cal.setTime(date);
//			     cal.add(Calendar.MONTH, -1);
//			     sheetAcceptLimit = sdf.format(cal.getTime());
//			     System.out.println(sheetAcceptLimit);
//			}
//			
//			if("".equals(flag)){ //默认地市粒度查询
//				flag = "cityQuery";
//			}
//			
//			if("".equals(pId)){
//				pId = "28";
//			}
			ISchemeRateService schemeRateService = (ISchemeRateService) getBean("schemeRateService");
			taskList = schemeRateService.childSceneReports(sheetAcceptLimit,sheetCompleteLimit, pId,flag);
			total = taskList.size();
			
			request.setAttribute("sheetAcceptLimit", sheetAcceptLimit);
			request.setAttribute("sheetCompleteLimit", sheetCompleteLimit);
			request.setAttribute("pId", pId);
			//request.setAttribute("taskList", taskList);
			//request.setAttribute("total",total);
			
			if("countyQuery".equals(flag)){
				returnView = "childSceneReportsForCounty";
			}
		}
		
		request.setAttribute("taskList", taskList);
		request.setAttribute("total",total);
		request.setAttribute("queryFlay", queryFlay);
		return mapping.findForward(returnView);
	}
	
	/**
	 * 	 导出 子场景工单统计
	 	 * @author WANGJUN
	 	 * @title: exportChildSceneReports
	 	 * @date Jul 25, 2016 10:03:59 AM
	 	 * @param mapping
	 	 * @param form
	 	 * @param request
	 	 * @param response
	 	 * @return
	 	 * @throws Exception ActionForward
	 */
	public ActionForward exportChildSceneReports(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		//开始时间
		String sheetAcceptLimit = StaticMethod.null2String(request.getParameter("sheetAcceptLimit"));
		//结束时间
		String sheetCompleteLimit = StaticMethod.null2String(request.getParameter("sheetCompleteLimit"));
		//报表类型标识，是地市查询还是区县查询
		String flag = StaticMethod.null2String(request.getParameter("flag"));
		//地市的父ID
		String pId = StaticMethod.null2String(request.getParameter("pId"));
		
		ISchemeRateService schemeRateService = (ISchemeRateService) getBean("schemeRateService");
		HSSFWorkbook wb = schemeRateService.exportChildSceneReports(sheetAcceptLimit,sheetCompleteLimit,pId,flag);
	
		/*写入临时文件-----------------------------------------------------------------*/	
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
     		String nowDate=df.format(new Date());// new Date()为获取当前系统时间
     		String path=System.getProperty("catalina.home")+"/webapps"+request.getContextPath()+"/sceneExcelAccessory/temporary/";
//     		String path="D:/";
     		String fileName=nowDate+".xls";
     		OutputStream output = new FileOutputStream(path+fileName);
     		fileName = new String(fileName.getBytes("GBK"), "utf-8");  
            response.reset();  
            response.setHeader("Content-Disposition", "attachment;filename="  
                    + fileName);// 指定下载的文件名   
            response.setContentType("application/vnd.ms-excel");  
            response.setHeader("Pragma", "no-cache");  
            response.setHeader("Cache-Control", "no-cache");  
            response.setDateHeader("Expires", 0);  
            BufferedOutputStream bufferedOutPut = new BufferedOutputStream(output);
           
			
         try {   
	            bufferedOutPut.flush();  
	            wb.write(bufferedOutPut);  
	            bufferedOutPut.close();
	            /*下载文件-----------------------------------------------------------------------------*/
	            String downFilename="报表导出.xls";//要下载的文件名称
	            if("cityQuery".equals(flag)){//一次核验
	            	downFilename="地市导出.xls";
	    		}else if("countyQuery".equals(flag)){//二次抽检
	    			downFilename="区县导出.xls";
	    		}
	            
	            String filepath=path+fileName;//要下载的文件完整路径
			    response.setContentType("text/plain");
			    response.setHeader("Location",downFilename);
			    response.setHeader("Content-Disposition", "attachment; filename=" +new String(downFilename.getBytes("gb2312"),"iso8859-1")); 
			    response.setCharacterEncoding("utf-8"); 
			    OutputStream outputStream = response.getOutputStream();
			    InputStream inputStream = new FileInputStream(filepath);
			    byte[] buffer = new byte[1024];
			    int i = -1;
			    while ((i = inputStream.read(buffer)) != -1) {
			    	outputStream.write(buffer, 0, i);
			    }
		        outputStream.flush();
		        outputStream.close();
		        inputStream.close();

    	 } catch (IOException e) {  
	            e.printStackTrace();  
	            System.out.println("Output   is   closed ");  
    	 } finally {
    		 	File fileXls =new File(path+fileName);
    		 	if(fileXls.exists()){
    		 		fileXls.delete();
    		 	}
    	 }
         
		return null;

	}

}
