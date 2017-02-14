package com.boco.eoms.commons.excelexporttag.action;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.boco.RW_Excel.excel.Workbook;
import com.boco.RW_Excel.excel.format.Alignment;
import com.boco.RW_Excel.excel.write.Label;
import com.boco.RW_Excel.excel.write.WritableCellFormat;
import com.boco.RW_Excel.excel.write.WritableFont;
import com.boco.RW_Excel.excel.write.WritableSheet;
import com.boco.RW_Excel.excel.write.WritableWorkbook;
import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.StaticMethod;
import com.boco.eoms.base.webapp.action.BaseAction;
import com.boco.eoms.commons.excelexporttag.model.ExcelRowItem;
import com.boco.eoms.commons.system.dict.service.ID2NameService;
import com.boco.eoms.deviceManagement.common.service.CommonGenericService;
import com.boco.eoms.deviceManagement.common.utils.CommonUtils;
import com.googlecode.genericdao.search.Search;
import com.googlecode.genericdao.search.SearchResult;
/**
 * <p>
 * Title: excel导出标签
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 *  2012-9-26 下午02:26:10
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */


public final class ExcelExportTagAction extends BaseAction {
	public ActionForward exportExcel(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String modelName = StaticMethod.null2String(request.getParameter("modelName")).trim();
		String serviceBeanId = StaticMethod.null2String(request.getParameter("serviceBeanId")).trim();
		String title = StaticMethod.null2String(request.getParameter("excelExportTitle")).trim();
		String tempTitle=title;
		System.out.println("---------------title="+title);
		if ("".equals(title)) {
			title = "temp";
		}
		String[] rowNames = request.getParameterValues("rowname");
		//1.解析页面数据 进行封装
		List<ExcelRowItem> itemList = new ArrayList<ExcelRowItem>();
		for (String  rowValue : rowNames) {
			ExcelRowItem item = new ExcelRowItem();
			String temp[] = rowValue.split("\\|");
			item.setName(temp[0].trim());
			item.setValue(temp[1].trim());
			if (temp.length>2) {
				item.setDictDaoName(temp[2]);
			}
			itemList.add(item);
		}
		//2.检查页面传过来的 字段值 在对应的model中是否存在。
		Class<?> model = Class.forName(modelName); 
		int rightItem = 0;
		for (ExcelRowItem item : itemList) {
			for (Field f : model.getDeclaredFields()) {
				  if(item.getValue().trim().equals(f.getName())){
					  rightItem++;
					  continue;
				  }
			}
		}
		if(itemList.size()!=rightItem){
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write("导出字段有误");
			return null;
		}
		
	
		
		String sheetName=title;			
		
		//初始化Excel文件
		OutputStream os = response.getOutputStream();// 取得输出流 
		
		//转换后乱码，先去掉
	if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0){
			title = new String(title.getBytes("UTF-8"), "ISO8859-1");//firefox浏览器
			
		}
		if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
			title = URLEncoder.encode(title, "UTF-8");// IE浏览器

		}
		
		response.reset();// 清空输出流 
		//设定输出文件头,该方法有两个参数，分别表示应答头的名字和值。
		response.setHeader("Content-disposition", "attachment;filename=\""+title+".xls\"");
		// 定义输出类型
		response.setContentType("application/msexcel");
		//response.setContentType("GB2312");
//		response.setHeader("Content-disposition", "attachment; filename=\""+URLEncoder.encode(title, "UTF-8")+".xls\"");
		WritableWorkbook wbook = Workbook.createWorkbook(os); // 建立excel文件 
		WritableSheet wsheet = wbook.createSheet(sheetName, 0); // sheet名称
		//标题列
		int i=0;
		int j=0;
		if(tempTitle.equals("运维人员信息")){
			j=1;
		}
		
		WritableFont font= new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD,false);
		WritableCellFormat cellFormat= new WritableCellFormat(font);  
		
		
		if(tempTitle.equals("运维人员信息")){
			WritableCellFormat cellFormat1= new WritableCellFormat(font);  
			cellFormat1.setAlignment(Alignment.CENTRE);//单元格内容居中
			Label e = new Label(0, 0, "人员信息导出模板（*为必填）");
			e.setCellFormat(cellFormat1);
			wsheet.addCell(e);
			wsheet.mergeCells(0, 0, 26, 0);
			
			Label e1 = new Label(27, 0, "说明：“工作岗位-兼职”可以填多个工作岗位，多个岗位已“,”分隔");
			e1.setCellFormat(cellFormat1);
			wsheet.addCell(e1);
			wsheet.mergeCells(27, 0, 29, 0);
		}
		
		
		for (ExcelRowItem item : itemList) {
			Label label = new  Label(i++,j,item.getName());
			label.setCellFormat(cellFormat);
			wsheet.addCell(label);
		//	wsheet.addCell(new Label(i++,0,item.getName()));
		}
		//取出数据
		@SuppressWarnings("rawtypes")
		CommonGenericService service = (CommonGenericService)getBean(serviceBeanId);
		SearchResult<?> searchResult;
		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(request, search);
		//设置每次导出多少条记录
		search.setFirstResult(0);
		//search.setMaxResults(service.count(search));
		String userId = getUser(request).getUserid();
		String deptId =  getUser(request).getDeptid();
		search=service.searchPrivFilter(search, userId, deptId,request);
		searchResult = service.searchAndCount(search);
		
		int m = 0;//改这里可以
		if(tempTitle.equals("运维人员信息")){
			m=1;
		}
		for (Object obj : searchResult.getResult()) {
			int n = 0;
			m++;
			for (ExcelRowItem item : itemList) {
				String methodName = this.getMethodName(item.getValue());
				Object objTempValue = obj.getClass().getMethod(methodName).invoke(obj);
				String objValue = "";
				if(objTempValue!=null)
					objValue = objTempValue.toString();
				//封装Excel主体数据
				String daoValue = StaticMethod.null2String(item.getDictDaoName());
				if("".endsWith(daoValue))
						wsheet.addCell(new Label(n++,m,objValue));
				else{
					ID2NameService s = (ID2NameService) ApplicationContextHolder.getInstance().getBean("ID2NameGetServiceCatch");
					String aa[]=objValue.split(",");
					if(aa.length>0){
						String bb="";
						for(int ii=0;ii<aa.length;ii++){
							if(!"".equals(bb)){
								bb+=";";
							}
							bb =bb+s.id2Name(aa[ii], item.getDictDaoName());
						}
						wsheet.addCell(new Label(n++,m,bb));
					}
					else
						wsheet.addCell(new Label(n++,m,s.id2Name(objValue, item.getDictDaoName())));
				}
			}
		}
		
		wbook.write(); // 写入文件 
		wbook.close(); 
		os.close(); // 关闭流
		
//		response.setContentType("text/html;charset=UTF-8");
//		response.getWriter().write("total count ---     ："+searchResult.getTotalCount());
		return null;
	}

	/**
	 * 取得get方法
	 * @param name
	 * @return
	 */
	private String getMethodName(String name){
		return "get"+(name.toCharArray()[0]+"").toUpperCase()+name.substring(1);
	}
	
	/**
	 * 用于线路工单的Excel导出
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward excelExportToProcess(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String modelName = StaticMethod.null2String(request.getParameter("modelName")).trim();
		String serviceBeanId = StaticMethod.null2String(request.getParameter("serviceBeanId")).trim();
		String title = StaticMethod.null2String(request.getParameter("excelExportTitle")).trim();
		String queryFlag = StaticMethod.null2String(request.getParameter("queryFlag")).trim();
		String processKey = StaticMethod.null2String(request.getParameter("processKey")).trim();
		String flag = StaticMethod.null2String(request.getParameter("flag")).trim();
		String tempTitle=title;
		System.out.println("---------------title="+title);
		if ("".equals(title)) {
			title = "temp";
		}
		String[] rowNames = request.getParameterValues("rowname");
		//1.解析页面数据 进行封装
		List<ExcelRowItem> itemList = new ArrayList<ExcelRowItem>();
		for (String  rowValue : rowNames) {
			ExcelRowItem item = new ExcelRowItem();
			String temp[] = rowValue.split("\\|");
			item.setName(temp[0].trim());
			item.setValue(temp[1].trim());
			if (temp.length>2) {
				item.setDictDaoName(temp[2]);
			}
			itemList.add(item);
		}
		//2.检查页面传过来的 字段值 在对应的model中是否存在。
		Class<?> model = Class.forName(modelName); 
		int rightItem = 0;
		for (ExcelRowItem item : itemList) {
			for (Field f : model.getDeclaredFields()) {
				  if(item.getValue().trim().equals(f.getName())){
					  rightItem++;
					  continue;
				  }
			}
		}
		if(itemList.size()!=rightItem){
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write("导出字段有误");
			return null;
		}
		
	
		
		String sheetName=title;			
		
		//初始化Excel文件
		OutputStream os = response.getOutputStream();// 取得输出流 
		
		//转换后乱码，先去掉
	if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0){
			title = new String(title.getBytes("UTF-8"), "ISO8859-1");//firefox浏览器
			
		}
		if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
			title = URLEncoder.encode(title, "UTF-8");// IE浏览器

		}
		
		response.reset();// 清空输出流 
		//设定输出文件头,该方法有两个参数，分别表示应答头的名字和值。
		response.setHeader("Content-disposition", "attachment;filename=\""+title+".xls\"");
		// 定义输出类型
		response.setContentType("application/msexcel");
		//response.setContentType("GB2312");
//		response.setHeader("Content-disposition", "attachment; filename=\""+URLEncoder.encode(title, "UTF-8")+".xls\"");
		WritableWorkbook wbook = Workbook.createWorkbook(os); // 建立excel文件 
		WritableSheet wsheet = wbook.createSheet(sheetName, 0); // sheet名称
		//标题列
		int i=0;
		int j=0;		
		WritableFont font= new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD,false);
		WritableCellFormat cellFormat= new WritableCellFormat(font);  		
		for (ExcelRowItem item : itemList) {
			Label label = new  Label(i++,j,item.getName());
			label.setCellFormat(cellFormat);
			wsheet.addCell(label);
		//	wsheet.addCell(new Label(i++,0,item.getName()));
		}
		//取出数据
		@SuppressWarnings("rawtypes")
		CommonGenericService service = (CommonGenericService)getBean(serviceBeanId);
		SearchResult<?> searchResult;
		Search search = new Search();
		search = CommonUtils.getSqlFromRequestMap(request, search);
		//设置每次导出多少条记录
		search.setFirstResult(0);
		//search.setMaxResults(service.count(search));
		String userId = getUser(request).getUserid();
		String deptId =  getUser(request).getDeptid();
//		search=service.searchPrivFilter(search, userId, deptId,request);
//		searchResult = service.searchAndCount(search);
		List result = service.excelExportToProcess(search, userId, deptId, queryFlag,processKey,flag,request);
		
		int m = 0;//改这里可以
		for (Object obj : result) {
			int n = 0;
			m++;
			for (ExcelRowItem item : itemList) {
				String methodName = this.getMethodName(item.getValue());
				Object objTempValue = obj.getClass().getMethod(methodName).invoke(obj);
				String returnType = obj.getClass().getMethod(methodName).getGenericReturnType().toString();//get方法的返回值类型
				System.out.println("-------------returnType="+returnType);
				String objValue = "";
				if(objTempValue!=null){
					if(returnType.equals("class java.util.Date")){//如果时间类型需要转换
//						Date date = null;
						/*try{
							SimpleDateFormat sdf1 = new SimpleDateFormat ("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
							date = sdf1.parse(objTempValue.toString());
						}catch(Exception ex){
							try{
								SimpleDateFormat sdf2 = new SimpleDateFormat ("EEE MMM dd Z yyyy", Locale.UK);
								date = sdf2.parse(objTempValue.toString());
							}catch(Exception ex2){								
							}
						}*/
//						 SimpleDateFormat  df   = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						 objValue = com.boco.eoms.base.util.StaticMethod.date2Str((Date)objTempValue);
					}else{
						objValue = objTempValue.toString();
					}
				}
					
				//封装Excel主体数据
				String daoValue = StaticMethod.null2String(item.getDictDaoName());
				if("".endsWith(daoValue))
						wsheet.addCell(new Label(n++,m,objValue));
				else{
					ID2NameService s = (ID2NameService) ApplicationContextHolder.getInstance().getBean("ID2NameGetServiceCatch");
					String aa[]=objValue.split(",");
					if(aa.length>0){
						String bb="";
						for(int ii=0;ii<aa.length;ii++){
							if(!"".equals(bb)){
								bb+=";";
							}
							bb =bb+s.id2Name(aa[ii], item.getDictDaoName());
						}
						wsheet.addCell(new Label(n++,m,bb));
					}
					else
						wsheet.addCell(new Label(n++,m,s.id2Name(objValue, item.getDictDaoName())));
				}
			}
		}
		
		wbook.write(); // 写入文件 
		wbook.close(); 
		os.close(); // 关闭流
		
//		response.setContentType("text/html;charset=UTF-8");
//		response.getWriter().write("total count ---     ："+searchResult.getTotalCount());
		return null;
	}
}
