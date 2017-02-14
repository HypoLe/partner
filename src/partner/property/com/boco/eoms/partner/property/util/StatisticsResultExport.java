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

import com.boco.eoms.partner.statistically.pojo.TdObjModel;

public class StatisticsResultExport {
	/**
	 * 统计结果导出
	 * @param combin:true导出结果需要跨行,false导出结果不跨行
	 * @param tempList:导出的数据
	 * @param headList:标题列表
	 * @param fileName:导出文件名称;
	 * @author:fengguangping
	 * @param response:response
	 */
	public static void exportStatisticsResultToXLSFile(boolean combin,List<List<TdObjModel>> tempList,	List<String> headList,String fileName,
			HttpServletResponse response,HttpServletRequest request){
		response.setCharacterEncoding("utf-8");
		HSSFWorkbook wb=null;
		OutputStream os=null;
		try {
			wb=new HSSFWorkbook();
			HSSFSheet sheet=wb.createSheet("detail");
			sheet.setColumnWidth((short) 0, (short)5000);//设置列宽;//第一个参数代表列id(从0开始),第2个参数代表宽度值  参考 ："2012-08-10"的宽度为2500
			HSSFRow row0=sheet.createRow((short)0);
			HSSFCellStyle cellstyle=wb.createCellStyle();
			sheet.createFreezePane(0,1);//冻结窗口;
			cellstyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
			cellstyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
			cellstyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
			cellstyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
			cellstyle.setWrapText(true);//设置自动换行
			//添加标题；
			for (short i = 0; i < headList.size(); i++) {
				//createCell(wb, row0, i, headList.get(i));
				HSSFCell cell=row0.createCell(i);
				cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell.setCellValue(headList.get(i));
				HSSFFont font=wb.createFont();
				font.setFontHeightInPoints((short)12);
				HSSFCellStyle cellstyle1=wb.createCellStyle();
				cellstyle1.setFont(font);
				cellstyle1.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
				cellstyle1.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
				cellstyle1.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
				cellstyle1.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
				cellstyle1.setFillForegroundColor(HSSFColor.YELLOW.index);
				cellstyle1.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
				cell.setCellStyle(cellstyle1);
			}
			//添加统计内容
			for (short i =0; i <tempList.size(); i++) {
				List<TdObjModel> tdObjModelList=tempList.get(i);
				HSSFRow row=sheet.createRow(i+1);
				for (short j = 0; j < tdObjModelList.size(); j++) {
					TdObjModel tdObjModel=tdObjModelList.get(j);
					createCell(wb, row, j, tdObjModel.getName(),cellstyle);
				}
			}
			if (combin) {//导出需要跨行的结果
				//sheet.addMergedRegion(new Region((short)1,(short)1,(short)7,(short)1));
				//合并单元格处理,合并后的值是取自左上角的值
				short eachRowTotalCell=(short)headList.size();
				short r=0;//记录当前sheet的列号
				short c=0;//记录当前的行号
				for (short i =0; i <eachRowTotalCell; i++) {
					r=i;//记录当前sheet的列号
					for (short j=0; j < tempList.size();) {
						short rowSpan=1;//记录跨行数
						c=(short)(j+1);//记录当前的行号
						//获得当前行当前列的model
						TdObjModel model=tempList.get(j).get(i);
						while(!model.isShow()){//为不可见时表示有跨行
							c++;//行号自增1
							j++;//tempList向下移动
							rowSpan++;//rowSpan自增
							if (j>=tempList.size()) {
								break;
							}
							model=tempList.get(j).get(i);//取得下一个model检查是否有跨行；
						}
						if (rowSpan>1) {//表示有跨行，执行合并处理
							// 四个参数分别是：起始行，起始列，结束行，结束列
							sheet.addMergedRegion(new Region((short)(c-rowSpan),(short)r,(short)(c-1),(short)r));
							rowSpan=1;//将rowSpan置为原始值1；
						}
						j++;
					}
				}
			}
			if ("".equals(fileName)) {
				fileName="统计导出结果";
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
