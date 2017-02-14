package com.boco.eoms.partner.property.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;

import com.boco.eoms.partner.inspect.webapp.form.InspectPlanResCountFromNew;
import com.boco.eoms.partner.statistically.pojo.TdObjModel;

public class CheckingStatisticsResultExport {
	/**
	 * 巡检统计结果导出
	 * @param countList:导出的数据
	 * @param specialtyList:大标题列表
	 * @param levelList:小标题列表
	 * @param fileName:导出文件名称;
	 * @author:wangyue
	 * @param response:response
	 */
	public static void exportStatisticsResultToXLSFile(String flag,List<InspectPlanResCountFromNew> resultList,List<String> specialtyList,List<String> levelList,String fileName,
			HttpServletResponse response,HttpServletRequest request){
		response.setCharacterEncoding("utf-8");
		HSSFWorkbook wb=null;
		OutputStream os=null;
		try {
			wb=new HSSFWorkbook();
			HSSFSheet sheet=wb.createSheet("detail");
			//设置列宽;//第一个参数代表列id(从0开始),第2个参数代表宽度值  参考 ："2012-08-10"的宽度为2500
			HSSFCellStyle cellstyle=wb.createCellStyle();
			sheet.createFreezePane(0,1);//冻结窗口;
			cellstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
			cellstyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
			cellstyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
			cellstyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
			cellstyle.setWrapText(true);//设置自动换行
			HSSFCellStyle cellstyle1=wb.createCellStyle();
			cellstyle1.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
			cellstyle1.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
			cellstyle1.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
			cellstyle1.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
			cellstyle1.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
			cellstyle1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellstyle1.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中   
			cellstyle1.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);//上下居中 
			//添加大标题；
			for(int i=0;i<3;i++){
				String msg = "";
					if(i==0){
						if("1".equals(flag)){
							msg = "地市";
						}else if("2".equals(flag)){
							msg = "区县";
						}else{
							msg = "负责人";
						}
						sheet.addMergedRegion(new Region(0,(short)0,2,(short)0));
						createCell(wb, sheet.createRow(0), (short)0, msg,cellstyle1);
						createCell(wb, sheet.createRow(1), (short)0, "",cellstyle1);
						createCell(wb, sheet.createRow(2), (short)0, "",cellstyle1);
						
					}else if(i==1){
						msg = "年";
						sheet.addMergedRegion(new Region(0,(short)1,2,(short)1));
						createCell(wb, sheet.createRow(0), (short)1, msg,cellstyle1);
						createCell(wb, sheet.createRow(1), (short)1, "",cellstyle1);
						createCell(wb, sheet.createRow(2), (short)1, "",cellstyle1);
						
					}else{
						msg = "月";
						sheet.addMergedRegion(new Region(0,(short)2,2,(short)2));
						createCell(wb, sheet.createRow(0), (short)2, msg,cellstyle1);
						createCell(wb, sheet.createRow(1), (short)2, "",cellstyle1);
						createCell(wb, sheet.createRow(2), (short)2, "",cellstyle1);
						
					}
				}
				
			
			for (short i = 0; i < specialtyList.size(); i++) {
					int count =0;
					if(i==0){//基站4*3=12 --11
						sheet.addMergedRegion(new Region(0,(short)3,0,(short)14));
						count = 3;
					}
					if(i==1){//接入网3*3=9---8
						sheet.addMergedRegion(new Region(0,(short)15,0,(short)23));
						count = 15;
					}
					if(i==2){//铁塔2*3=6---5
						sheet.addMergedRegion(new Region(0,(short)24,0,(short)29));
						count = 24;
					}
					if(i==3){//室分4*3=12----11
						sheet.addMergedRegion(new Region(0,(short)30,0,(short)41));
						count = 30;
					}
					if(i==4){//WLAN3*3=9---8
						sheet.addMergedRegion(new Region(0,(short)42,0,(short)50));
						count = 42;
				    }
					if(i==5){//重点机房4*3=12---12
						sheet.addMergedRegion(new Region(0,(short)51,0,(short)62));
						count = 51;
				    }
				
				HSSFCell cell=sheet.createRow((short)0).createCell((short)count);

				
				cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell.setCellValue(specialtyList.get(i));
				
				HSSFFont font=wb.createFont();
				font.setFontName("黑体");  
				font.setFontHeightInPoints((short)12);
				cellstyle1.setFont(font);
				cellstyle1.setFillForegroundColor(HSSFColor.BLUE.index);
				cell.setCellStyle(cellstyle1);
				
			}
			//添加小标题；
			for (short i = 0; i < levelList.size(); i++) {
				
				sheet.addMergedRegion(new Region(1,(short)(i*3+3),1,(short)(i*3+5)));
				HSSFCell cell=sheet.createRow((short)1).createCell((short)(i*3+3));
				cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell.setCellValue(levelList.get(i));
				
				HSSFFont font=wb.createFont();
				font.setFontName("黑体");  
				font.setFontHeightInPoints((short)12);
				cellstyle1.setFillForegroundColor(HSSFColor.BLUE_GREY.index);
				cell.setCellStyle(cellstyle1);
			}
			for(short i=0;i<60;i++){
				sheet.setColumnWidth((short)i, (short)4000);
				HSSFRow rowUp=sheet.createRow(2);
				String message = "";
				int j = i%3;
				
				if(j==0){
					message = "巡检总数";
				}else if(j==1){
					message = "月需巡检数";
				}else{
					message = "月已巡检数";
					
				}
				createCell(wb, rowUp, (short)(i+3), message,cellstyle1);
			}
			if(resultList.size()>0){
				for(int i=0;i<resultList.size();i++){
					createCell(wb, sheet.createRow(3+i), (short)0, resultList.get(i).getExportname(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)1, resultList.get(i).getYear(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)2, resultList.get(i).getMonth(),cellstyle);
					//基站
					createCell(wb, sheet.createRow(3+i), (short)3, resultList.get(i).getV_stage_a(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)4, resultList.get(i).getV_stage_d(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)5, resultList.get(i).getV_stage_w(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)6, resultList.get(i).getA_stage_a(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)7, resultList.get(i).getA_stage_d(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)8, resultList.get(i).getA_stage_w(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)9, resultList.get(i).getB_stage_a(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)10, resultList.get(i).getB_stage_d(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)11, resultList.get(i).getB_stage_w(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)12, resultList.get(i).getC_stage_a(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)13, resultList.get(i).getC_stage_d(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)14, resultList.get(i).getC_stage_w(),cellstyle);
					//接入网
					createCell(wb, sheet.createRow(3+i), (short)15, resultList.get(i).getA_accessNetwork_a(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)16, resultList.get(i).getA_accessNetwork_d(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)17, resultList.get(i).getA_accessNetwork_w(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)18, resultList.get(i).getB_accessNetwork_a(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)19, resultList.get(i).getB_accessNetwork_d(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)20, resultList.get(i).getB_accessNetwork_w(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)21, resultList.get(i).getC_accessNetwork_a(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)22, resultList.get(i).getC_accessNetwork_d(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)23, resultList.get(i).getC_accessNetwork_w(),cellstyle);
					//铁塔
					createCell(wb, sheet.createRow(3+i), (short)24, resultList.get(i).getJ_tower_a(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)25, resultList.get(i).getJ_tower_d(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)26, resultList.get(i).getJ_tower_w(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)27, resultList.get(i).getY_tower_a(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)28, resultList.get(i).getY_tower_d(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)29, resultList.get(i).getY_tower_w(),cellstyle);
					//室分
					createCell(wb, sheet.createRow(3+i), (short)30, resultList.get(i).getZ_distribution_a(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)31, resultList.get(i).getZ_distribution_d(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)32, resultList.get(i).getZ_distribution_w(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)33, resultList.get(i).getV_distribution_a(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)34, resultList.get(i).getV_distribution_d(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)35, resultList.get(i).getV_distribution_w(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)36, resultList.get(i).getA_distribution_a(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)37, resultList.get(i).getA_distribution_d(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)38, resultList.get(i).getA_distribution_w(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)39, resultList.get(i).getB_distribution_a(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)40, resultList.get(i).getB_distribution_d(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)41, resultList.get(i).getB_distribution_w(),cellstyle);
					//WLAN
					createCell(wb, sheet.createRow(3+i), (short)42, resultList.get(i).getA_wlan_a(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)43, resultList.get(i).getA_wlan_d(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)44, resultList.get(i).getA_wlan_w(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)45, resultList.get(i).getB_wlan_a(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)46, resultList.get(i).getB_wlan_d(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)47, resultList.get(i).getB_wlan_w(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)48, resultList.get(i).getC_wlan_a(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)49, resultList.get(i).getC_wlan_d(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)50, resultList.get(i).getC_wlan_w(),cellstyle);
					
					//重点机房
					createCell(wb, sheet.createRow(3+i), (short)51, resultList.get(i).getV_jifang_a(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)52, resultList.get(i).getV_jifang_d(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)53, resultList.get(i).getV_jifang_w(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)54, resultList.get(i).getA_jifang_a(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)55, resultList.get(i).getA_jifang_d(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)56, resultList.get(i).getA_jifang_w(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)57, resultList.get(i).getB_jifang_a(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)58, resultList.get(i).getB_jifang_d(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)59, resultList.get(i).getB_jifang_w(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)60, resultList.get(i).getC_jifang_a(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)61, resultList.get(i).getC_jifang_d(),cellstyle);
					createCell(wb, sheet.createRow(3+i), (short)62, resultList.get(i).getC_jifang_w(),cellstyle);
				}
			}
			if ("".equals(fileName)) {
				fileName="巡检统计导出结果";
			}
			if (request.getHeader("User-Agent").toLowerCase().indexOf("firefox") > 0){
				fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");//firefox浏览器
			}
			else if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0){
				fileName = URLEncoder.encode(fileName, "UTF-8");//IE浏览器
			}else {
				fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");//经过测试goole浏览器和firefox浏览器编码一样，这样避免出现goole乱码
			}
			//设定输出文件头,该方法有两个参数，分别表示应答头的名字和值。
			response.setHeader("Content-disposition", "attachment;filename="+fileName+".xls");
			// 定义输出类型
			response.setContentType("application/msexcel");
			//response.setContentType("application/octet-stream");
			
			os=response.getOutputStream();
			wb.write(os);
		} catch (UnsupportedEncodingException e) {
			new RuntimeException("文件名称编码错误!");
		} catch (IOException e) {
			new RuntimeException("文件获取发生错误，请重新导出!");
		}finally{
			try {
				os.flush();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 创建单元格，并将内容填入单元格中；
	 *fengguangping
	 * Oct 10, 2012-4:23:39 PM
	 * @param wb：HSSFWorkbook
	 * @param row：HSSFRow
	 * @param col：单元格列值
	 * @param val：单元格值
	 */
	private static void createCell(HSSFWorkbook wb,HSSFRow row,short col,String val,HSSFCellStyle cellStyle)	{
		HSSFCell cell=row.createCell(col);
		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue(val);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
		cell.setCellStyle(cellStyle);
	}
}
