package com.boco.eoms.commons.mms.base.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class ExcelToPic {
	
	/**
	 * @param realPath 绝对路径（存放pic，excel）
	 * @param filename pic文件名称
	 * @param excelName excel名称
	 * @param tableHeadrowNum excel表头所在的行数
	 * @param tableHeadrowColor 绘制为pic表头的颜色
	 * 
	 * 必须按照规定输入excel （必须有表头）
	 * @return
	 */
	synchronized public String createRowsImage(String realPath,String filename,String excelName,int tableHeadrowNum,int imageWith ,Color tableHeadrowColor) {
		//add by lizhenyou  对Excel进行处理 ，由于绘图的算法我没有读懂 ，所以我暂时用处理excel的方式来做。 生成一个临时的excel，之后删除
		String headString = "报表";//"表头的内容：请填写";
		String newFileName = java.util.UUID.randomUUID().toString() + ".xls";
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			fis = new FileInputStream(realPath + excelName);
			POIFSFileSystem fs=new POIFSFileSystem(fis);
	        HSSFWorkbook wb = new HSSFWorkbook(fs);
	        HSSFSheet sheet = wb.getSheetAt(0);
	        HSSFRow row = sheet.getRow(tableHeadrowNum - 1);
	        headString = row.getCell(0).getRichStringCellValue().getString();
	        sheet.removeRow(row);
	        File f1 = new File(realPath + newFileName);
	        fos = new FileOutputStream(f1);
	        wb.write(fos);
		} catch (Exception e) {
			e.printStackTrace();
		}finally
		{
			if(fis != null)
			{
				 try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			if(fos != null)
			{
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		excelName = newFileName;
		//add end
		
		String file_name = "D:/app/excel/mms/image2.jpg";
		
		Workbook book = null;
		Sheet sheet = null;
		double rows = 0;
		double columns = 0;
		
		double paint_width = imageWith;//310; //画布宽度 175 550是7个字的宽度 
		if(imageWith == 0)
		{
			paint_width = 550;
		}

		double width = rows; //画布宽度,根据所有内容横坐标确定
		double height = columns; //画布高度,根据所有内容纵坐标确定

		double cell_heigth = 20; //单元格高度
		double cell_width = 35; //单元格宽度  根据画布宽度来计算

		double init_width_point = 0; //初始化点左侧横坐标
		double init_heigth_point = 0; //初始化点左侧纵坐标

		double line_size = 3; //单元格容纳字数 大致10pix 根据单元格计算

		double title_cells = 0; //抬头所占最大指标

		double lines = 0; //整个表单所占标准行数，用于计算画布大小
		double font_size = 13; //一个字所占像素
		String real_file_name = "";

		try {
			real_file_name = filename;// + ".gif";
			file_name = realPath + real_file_name;
			FileInputStream in = new FileInputStream(realPath
					+ excelName);
			book = Workbook.getWorkbook(in);
			sheet = book.getSheet(0);

			rows = sheet.getRows();
			columns = sheet.getColumns();
			cell_width = this.getLength(paint_width / columns); //paint_width/columns+5;
			line_size = this.getInt(cell_width / font_size);//cell_width/font_size;
			//lines=rows;
			for (int i = 0; i < rows; i++) {
				double temp_heigth = 0;
				temp_heigth = init_heigth_point + cell_heigth * title_cells
						+ (i) * cell_heigth;
				Cell[] c = sheet.getRow(i);
				int max_length = 0;

				for (int j = 0; j < c.length; j++) {

					if (!isNumeric(c[j].getContents())) {
						if (max_length < c[j].getContents().length()) {
							max_length = c[j].getContents().length();
						}
					} else {
						if (max_length < c[j].getContents().length() / 2) {
							max_length = this.getLength(c[j].getContents()
									.length() / 2);
						}
					}
				}
				title_cells = this.getLength(max_length / line_size);

				lines = lines + title_cells;
			}

			width = rows * cell_width;
			height = lines * cell_heigth;

			width = columns * cell_width;
			height = lines * cell_heigth;

			//File file = new File(file_name);  
			Font font = new Font("Serif", Font.PLAIN, 10);
			BufferedImage bi = new BufferedImage(this.getLength(width), this
					.getLength(height), BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = (Graphics2D) bi.getGraphics();
			g2.setBackground(Color.WHITE);
			g2.clearRect(0, 0, this.getLength(width), this.getLength(height));
			g2.setPaint(tableHeadrowColor);
	        g2.fillRect(0, 0, bi.getWidth(), 20);
			g2.setPaint(Color.blue);
			//画表格框架   
			double temp_heigth = 0;//实际单元格高度
			for (int i = 0; i < rows; i++) {

				Cell[] c = sheet.getRow(i);
				int max_length = 0;
				for (int j = 0; j < c.length; j++) {

					if (!isNumeric(c[j].getContents())) {
						if (max_length < c[j].getContents().length()) {
							max_length = c[j].getContents().length();
						}
					} else {
						if (max_length < c[j].getContents().length() / 2) {
							max_length = this.getLength(c[j].getContents()
									.length() / 2);
						}
					}
				}
				title_cells = this.getLength(max_length / line_size);
				temp_heigth = temp_heigth + init_heigth_point + cell_heigth
						* title_cells;

				for (int j = 0; j < c.length; j++) {
					if (i == tableHeadrowNum + 1) {
						//TODO
						//动态获取表头的行数
						//填充第一行 mod by lizhenyou 2009-8-6
						//g2.setColor(new Color(120, 120, 255));
//						g2.setColor(tableHeadrowColor);
//						g2.fillRect(this.getLength(init_width_point
//								+ cell_width * j), this
//								.getLength(init_heigth_point), this
//								.getLength(cell_width), this
//								.getLength(temp_heigth - cell_heigth*2));
						
						//第二行
//						g2.setColor(tableHeadrowColor);
//						g2.fillRect(this.getLength(init_width_point
//								+ cell_width * j), this
//								.getLength(init_heigth_point + cell_heigth), this
//								.getLength(cell_width), this
//								.getLength(temp_heigth - cell_heigth));

					} else {
						//给第一行填充颜色
//						if (j == 0) {
//         				   g2.setPaint(new Color(120, 120, 255));
//	            			   g2.fillRect(this.getLength(init_width_point+cell_width*j),this.getLength(init_heigth_point),
//	            					   this.getLength(cell_width * i),this.getLength(cell_heigth));
//
//						}
						
						//	            			   else{
						//	            				  
						//		            			   g2.setPaint(Color.WHITE);
						//		            			   g2.fillRect(init_width_point+cell_width*j,init_heigth_point,cell_width,temp_heigth);
						//
						//	            			   }
					}
					g2.setPaint(Color.BLACK);
					g2.drawRect(this.getLength(init_width_point
									+ cell_width * j), this
									.getLength(init_heigth_point), this
									.getLength(cell_width), this
									.getLength(temp_heigth));

				}
			}
			//画表格内容   
			double temp_cell_heigth = 0;
			for (int i = 0; i < rows; i++) {

				Cell[] c = sheet.getRow(i);

				int max_length = 0;
				for (int j = 0; j < c.length; j++) {

					if (!isNumeric(c[j].getContents())) {
						if (max_length < c[j].getContents().length()) {
							max_length = c[j].getContents().length();
						}
					} else {
						if (max_length < c[j].getContents().length() / 2) {
							max_length = this.getLength(c[j].getContents()
									.length() / 2);
						}
					}
				}
				title_cells = this.getLength(max_length / line_size);

				for (int j = 0; j < c.length; j++) {

					int begin_index = 0;
					int last_index = 0;
					double temp_line_size = 0;
					for (int k = 0; k < title_cells; k++) {//每个单元格计算每行写入的数值

						String content = "";
						if (isNumeric(c[j].getContents())) {
							temp_line_size = 2 * line_size;
						} else {
							temp_line_size = line_size;
						}
						begin_index = this.getLength(temp_line_size) * k;
						//last_index=temp_line_size*k+1;		         				
						if (c[j].getContents().length() > temp_line_size
								* (k + 1)) {
							last_index = this.getLength(temp_line_size)
									* (k + 1);

						} else {
							last_index = c[j].getContents().length();
						}

						if (begin_index > last_index) {
							content = content;
						} else {

							content = c[j].getContents().substring(begin_index,
									last_index);

						}
						g2.drawString(content, this.getLength(init_width_point
								+ cell_width * j + 3), this
								.getLength(init_heigth_point + temp_cell_heigth
										+ cell_heigth * (k) + 15));

					}

				}

				temp_cell_heigth = temp_cell_heigth + init_heigth_point
						+ cell_heigth * title_cells;

			}

			//ImageIO.write(bi, "jpg", file); 

			//add by lizhenyou
			BufferedImage bihead = new BufferedImage(this.getLength(bi.getWidth()), this.getLength(bi.getHeight()+20), BufferedImage.TYPE_INT_RGB);
			Graphics2D g2_bihead = (Graphics2D)bihead.getGraphics();
			g2_bihead.setBackground(new Color(120, 120, 255));//蓝色
			g2_bihead.clearRect(0, 0, this.getLength(bi.getWidth()), this.getLength(bi.getHeight()+20));
            g2_bihead.setPaint(Color.BLACK);
            
            //绘制表头
            g2_bihead.drawString(headString, (bi.getWidth() - headString.length()*10) /2, 15);
            
            //绘制表身
            g2_bihead.drawImage(bi, 0, 20, null);
//            g2.fillRect(this.getLength(init_width_point
//					+ cell_width * j), this
//					.getLength(init_heigth_point), this
//					.getLength(cell_width), this
//					.getLength(temp_heigth - cell_heigth*2));
            //add end
            
			GIFEncoder encode = new GIFEncoder(bihead);
			OutputStream output = new BufferedOutputStream(
					new FileOutputStream(file_name));
			encode.Write(output);
			output.close();
			
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			book.close();
		}
		
		//add by lizhenyou
		File f = new File(realPath + newFileName);
		if(f.isFile())
		{
			f.delete();
		}
		//add end

		return real_file_name;
	}

	
	
	
	
	 public String createColunmsImage(String realPath,String filename,String excelName){
		 String file_name="D:/app/excel/mms/image3.jpg";
		 
		 
		 Workbook book = null;         
		 Sheet sheet = null; 
		 double paint_width=175; //画布宽度
		 double rows=0;
		 double columns=0;
		 
		 double width = rows;    //画布宽度,根据所有内容横坐标确定
		 double height = columns;   //画布高度,根据所有内容纵坐标确定
	     
	      
		 double cell_heigth=15;   //单元格高度
		 double cell_width=50;    //单元格宽度 根据画布来计算
	     
		 double init_width_point=0;  //初始化点左侧横坐标
		 double init_heigth_point=0; //初始化点左侧纵坐标
	     
		 double line_size=3;         //单元格容纳字数
	     
		 double title_cells=0;       //抬头所占最大指标
	     
		 double lines=0;             //整个表单所占标准行数，用于计算画布大小
		 double font_size=14;
		 String real_name="";
	     try{
	    	 real_name=filename + ".gif";
			 file_name=realPath+real_name;	    		
	    	 FileInputStream in = new FileInputStream(realPath+excelName);       
	    	 book = Workbook.getWorkbook(in);             
	    	 sheet = book.getSheet(0); 
	    	
	    	 rows=sheet.getRows();
	    	 columns=sheet.getColumns();

//	    	 cell_width=this.getLength(paint_width/rows); //paint_width/columns+5;
//	    	 line_size=this.getInt(cell_width/font_size);//cell_width/font_size;
	    	 cell_width=line_size*font_size;
	    	 //lines=columns;
	         
	         for(int i=0;i<columns;i++){
	        	double temp_heigth=0;        	
	     		temp_heigth=init_heigth_point+cell_heigth*title_cells+(i)*cell_heigth;
	     		Cell [] c=sheet.getColumn(i);
	     		int max_length=0;
	     		for(int j=0;j<c.length;j++){
	     			if(!isNumeric(c[j].getContents())){
		             	if(max_length<c[j].getContents().length()){
		             		max_length=c[j].getContents().length();
		             	}
	     			}else{
		             	if(max_length<c[j].getContents().length()/2){
		             		max_length=this.getLength(c[j].getContents().length()/2);
		             	}
	     			}

	     		}
	     		
     			title_cells=this.getLength(max_length/line_size);
     			lines=lines+title_cells;  
	     		 
	         }
	     		

	         
	            width=rows*cell_width;
	            height=lines*cell_heigth; 
	            
	            
	           // File file = new File(file_name);  
	            Font font = new Font("TimesRoman", Font.PLAIN, 1);          
	            BufferedImage bi = new BufferedImage(this.getLength(width), this.getLength(height), BufferedImage.TYPE_INT_RGB); 
	            Graphics2D g2 = (Graphics2D)bi.getGraphics(); 
	            g2.setBackground(Color.WHITE);  
	            g2.clearRect(0, 0, this.getLength(width), this.getLength(height)); 
	            g2.setPaint(Color.blue);  
	       //画表格框架   
	            double temp_heigth=0;//实际单元格高度
	            for(int i=0;i<columns;i++){
	              	        	

	         		Cell [] c=sheet.getColumn(i);
	         		int max_length=0;
	         		for(int j=0;j<c.length;j++){

	         			if(!isNumeric(c[j].getContents())){
		                 	if(max_length<c[j].getContents().length()){
		                 		max_length=c[j].getContents().length();
		                 	}
	         			}else{
			             	if(max_length<c[j].getContents().length()/2){
			             		max_length=this.getLength(c[j].getContents().length()/2);
			             	}
		     			}
	         		}
	         		title_cells=this.getLength(max_length/line_size);
	         		temp_heigth=temp_heigth+init_heigth_point+cell_heigth*title_cells;       		
	         		for(int j=0;j<c.length;j++){
	            		   if(i==0){
	            			   g2.setPaint(Color.YELLOW);
	            			   g2.fillRect(this.getLength(init_width_point)+this.getLength(cell_width*j),this.getLength(init_heigth_point),this.getLength(cell_width),this.getLength(temp_heigth));

	            		   }else{
	            			   if(j==0){
//		            			   g2.setColor(Color.YELLOW);
//		            			   g2.fillRect(init_width_point+cell_width*j,init_heigth_point,cell_width,temp_heigth);
//	            				   g2.setPaint(Color.GREEN);
//		            			   g2.fillRect(init_width_point+cell_width*j,init_heigth_point,cell_width,temp_heigth);
	            			   }
//	            			   else{
//	            				  
//		            			   g2.setPaint(Color.WHITE);
//		            			   g2.fillRect(init_width_point+cell_width*j,init_heigth_point,cell_width,temp_heigth);
//
//	            			   }
	            		   }
                		   g2.setPaint(Color.BLACK);
                		   g2.drawRect(this.getLength(init_width_point+cell_width*j),this.getLength(init_heigth_point),this.getLength(cell_width),this.getLength(temp_heigth));
	         		}                                      	         		
	             }            
	            //画表格内容   
	           double temp_cell_heigth=0; 
	            for(int i=0;i<columns;i++){
             	        	

	         		Cell [] c=sheet.getColumn(i); 
	         		
	         		int max_length=0;
	         		for(int j=0;j<c.length;j++){
	         			
	         			if(!isNumeric(c[j].getContents())){
		                 	if(max_length<c[j].getContents().length()){
		                 		max_length=c[j].getContents().length();
		                 	}
	         			}else{
			             	if(max_length<c[j].getContents().length()/2){
			             		max_length=this.getLength(c[j].getContents().length()/2);
			             	}
		     			}
	     
	         		}
	         		System.out.println();
	         		//System.out.println(i+"   "+max_length+"==========="+line_size);
	         		title_cells=this.getLength(max_length/line_size);
	         		 
	         		for(int j=0;j<c.length;j++){
	      
	             		int begin_index=0;
	             		int last_index=0;
	             		double temp_line_size=0;
		         		for(int k=0;k<title_cells;k++){//每个单元格计算每行写入的数值

		         		        if(isNumeric(c[j].getContents())){
		         		        	temp_line_size=2*line_size;
		         		        }else{
		         		        	temp_line_size=line_size;
		         		        }
		         		       System.out.println("temp_line_size="+temp_line_size);
			         			begin_index=this.getLength(temp_line_size)*k;
			         			//last_index=temp_line_size*k+1;
			    	        	if(c[j].getContents().length()>temp_line_size*(k+1)){
			    	        		last_index=this.getLength(temp_line_size)*(k+1);
			    	        		
			    	        	}
			    	        	else{
			    	        		last_index=c[j].getContents().length();	        		
			    	        	}
		         	
	         		
		    	        	
		    	        	String content="";
		    	        	if(begin_index>last_index){
		    	        		content=content;
		    	        	}
		    	        	else{
		    	        		
		    	        			content=c[j].getContents().substring(begin_index,last_index);
		    	       
		    	        	}
		    	        	g2.drawString(content, this.getLength(init_width_point+cell_width*j+1),this.getLength(init_heigth_point+temp_cell_heigth+cell_heigth*(k)+12));	
		    	        	
		         		}
		         		

	         		}

	         		temp_cell_heigth=temp_cell_heigth+init_heigth_point+cell_heigth*title_cells; 
	         	
                    
	         		                                    	         		
	             }             
	            
	            //ImageIO.write(bi, "jpg", file); 
	            BufferedImage bihead = new BufferedImage(this.getLength(bi.getWidth()), this.getLength(bi.getHeight()+20), BufferedImage.TYPE_INT_RGB);
	            Graphics2D g2_bihead = (Graphics2D)bihead.getGraphics();
	            g2_bihead.setPaint(Color.RED);
	            g2_bihead.drawString("表头", 0, 5);
	            
	            g2_bihead.drawImage(bihead, 0, 0, null);
//	            g2_bihead.drawImage(bi, 0, 20, null);
	            
//	        	GIFEncoder encode = new GIFEncoder(bihead);
//	        	OutputStream output = new BufferedOutputStream(
//	        	    new FileOutputStream(file_name));
//	        	encode.Write(output); 
	           
	     	
	         } catch(Exception ex){
	    	ex.printStackTrace();
	    }	 finally{
	    	book.close();
	    }
		 
		 
		 return real_name;	 
	}
	
	public int getLength(double f) {
		int len = 0;
		len = (int) Math.ceil(new BigDecimal(f).setScale(2,
				BigDecimal.ROUND_HALF_UP).floatValue()); //paint_width/columns+5;

		return len;
	}

	public int getInt(double f) {
		int len = 0;
		len = (int) Math.floor(new BigDecimal(f).setScale(2,
				BigDecimal.ROUND_HALF_UP).floatValue()); //paint_width/columns+5;

		return len;
	}

	public boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("\\+?\\-?[0-9]+\\,?[0-9]*\\.?[0-9]*");

		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {

			return false;
		}

		return true;
	}
}
