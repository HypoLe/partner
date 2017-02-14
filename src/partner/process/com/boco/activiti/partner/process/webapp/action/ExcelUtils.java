package com.boco.activiti.partner.process.webapp.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.boco.activiti.partner.process.po.DownWorkOrderModel;
import com.boco.activiti.partner.process.po.SceneTableModel;
import com.boco.eoms.base.util.StaticMethod;

public class ExcelUtils {
		 private static Logger  logger = Logger.getLogger(ExcelUtils.class);
	 	 private HSSFWorkbook wb = null;  
	     private HSSFSheet sheet = null;  
	   
	     /** 
	      * @param wb 
	      * @param sheet  
	     * @return 
	      */  
	     public  ExcelUtils(HSSFWorkbook wb, HSSFSheet sheet) {  
	         // super();   
	         this.wb = wb;  
	         this.sheet = sheet;  
	     }

	     /** 
	      * 创建通用EXCEL头部 
	      *  
	      * @param headString 
	      *            头部显示的字符 
	      * @param colSum 
	      *            该报表的列数 
	      */  
	      @SuppressWarnings({ "deprecation", "unused" })  
	      public void createNormalHead(String headString, int colSum,int line) {  
	    	  	 HSSFRow row = sheet.createRow(line);  
	             // 设置第一行   
	             HSSFCell cell = row.createCell((short) 0);
	             // row.setHeight((short) 1000);   
	       
	             // 定义单元格为字符串类型   
	             cell.setCellType(HSSFCell.ENCODING_UTF_16);// 中文处理   
	             cell.setCellValue(new HSSFRichTextString(headString));  
	       
	             // 指定合并区域   
	             /** 
	              * public Region(int rowFrom, short colFrom, int rowTo, short colTo) 
	              */  
	             sheet.addMergedRegion(new Region(line, (short) 0, line, (short) colSum)); 
	            //参数1：行号 参数2：起始列号 参数3：行号 参数4：终止列号

	       
	             // 定义单元格格式，添加单元格表样式，并添加到工作簿   
	             HSSFCellStyle cellStyle = wb.createCellStyle();  
	             // 设置单元格水平对齐类型   
	             cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐   
	             cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐   
	             cellStyle.setWrapText(true);// 指定单元格自动换行  
	             cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); // 填充单元格  
	             cellStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
	             
	             




	             
	       
	             // 设置单元格字体   
	             HSSFFont font = wb.createFont();  
	             // font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   
	             // font.setFontName("宋体");   
	             // font.setFontHeight((short) 600);   
	             // cellStyle.setFont(font);   
	             cell.setCellStyle(cellStyle);  
	         }  
	      
	      
	      /** 
		      * 创建通用EXCEL标题
		      *  
		      * @param headString 
		      *            头部显示的字符 
		      * @param colSum 
		      *            该报表的列数 
		      */  
		      @SuppressWarnings({ "deprecation", "unused" })  
		      public void createTitle(List<SceneTableModel> list,int titleSize,int line,int sub) {
		    	     // 设置第二行   
		    	  	 HSSFRow row = sheet.createRow(line);
		    	  	 //设置列
		    	  	 for(int i=0;i<titleSize;i++){
		    	  		 HSSFCell cell = row.createCell((short) i);
			             // row.setHeight((short) 1000);   
			       
			             // 定义单元格为字符串类型   
			             cell.setCellType(HSSFCell.ENCODING_UTF_16);// 中文处理   
			             cell.setCellValue(new HSSFRichTextString(list.get(sub).getTableHeader()[i]));  
			      
			       
			             // 定义单元格格式，添加单元格表样式，并添加到工作簿   
			             HSSFCellStyle cellStyle = wb.createCellStyle();  
			             // 设置单元格水平对齐类型   
			             cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐   
			             cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐   
			             cellStyle.setWrapText(false);// 指定单元格自动换行  
			             cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); // 填充单元格  
			             cellStyle.setFillForegroundColor(HSSFColor.BLUE.index);
			             cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
			             cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
			             cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
			             cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框

			             // 设置单元格字体   
			             HSSFFont font = wb.createFont();  
			             // font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   
			             // font.setFontName("宋体");   
			             // font.setFontHeight((short) 600);   
			             // cellStyle.setFont(font);   
			             cell.setCellStyle(cellStyle);  
		    	  	 }
		            
		         }  
		      
		      /** 
			      * 创建通用EXCEL标题
			      *  
			      * @param headString 
			      *            头部显示的字符 
			      * @param colSum 
			      *            该报表的列数 
			      */  
			      @SuppressWarnings({ "deprecation", "unused" })  
			      public void createMaterialTitle(String[] arrs,int titleSize,int line) {  
			    	     // 设置第二行   
			    	  	 HSSFRow row = sheet.createRow(line);
			    	  	 //设置列
			    	  	 for(int i=0;i<titleSize;i++){
			    	  		 HSSFCell cell = row.createCell((short) i);
				             // row.setHeight((short) 1000);   
				       
				             // 定义单元格为字符串类型   
				             cell.setCellType(HSSFCell.ENCODING_UTF_16);// 中文处理   
				             cell.setCellValue(new HSSFRichTextString(arrs[i]));  
				      
				       
				             // 定义单元格格式，添加单元格表样式，并添加到工作簿   
				             HSSFCellStyle cellStyle = wb.createCellStyle();  
				             // 设置单元格水平对齐类型   
				             cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 指定单元格居中对齐   
				             cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 指定单元格垂直居中对齐   
				             cellStyle.setWrapText(false);// 指定单元格自动换行  
				             cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); // 填充单元格  
				             cellStyle.setFillForegroundColor(HSSFColor.BLUE.index);

				             // 设置单元格字体   
				             HSSFFont font = wb.createFont();  
				             // font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);   
				             // font.setFontName("宋体");   
				             // font.setFontHeight((short) 600);   
				             // cellStyle.setFont(font);   
				             cell.setCellStyle(cellStyle);  
			    	  	 }
			            
			         }  
	     
	     
	     public HSSFWorkbook getWb() {
			return wb;
		}

		public void setWb(HSSFWorkbook wb) {
			this.wb = wb;
		}

		public HSSFSheet getSheet() {
			return sheet;
		}

		public void setSheet(HSSFSheet sheet) {
			this.sheet = sheet;
		}  

	
}
