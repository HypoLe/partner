package com.boco.eoms.partner.property.util;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import com.boco.eoms.partner.statistically.pojo.TdObjModel;

public class ExportTableHelper {
	/**
	 * 导出统计后的结果，不合并；
	 *fengguangping
	 * Oct 11, 2012-11:09:31 AM
	 * @param tempList:统计数据
	 * @param headList：统计数据标题
	 * @param fileName：导出文件名称
	 * @param response
	 */
	public static void exportToXLSFileNoConbin(List<List<TdObjModel>> tempList,	List<String> headList,String fileName,HttpServletResponse response){
		response.setCharacterEncoding("utf-8");
		HSSFWorkbook wb=null;
		OutputStream os=null;
		try {
			os=response.getOutputStream();
			wb=new HSSFWorkbook();
			HSSFSheet sheet=wb.createSheet("detail");
			HSSFRow row0=sheet.createRow((short)0);
			//冻结窗口;
			sheet.createFreezePane(0,1);
			//添加标题；
			for (short i = 0; i < headList.size(); i++) {
				//createCell(wb, row0, i, headList.get(i));
				HSSFCell cell=row0.createCell(i);
				cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell.setCellValue(headList.get(i));
				HSSFCellStyle cellstyle=wb.createCellStyle();
				cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
				cellstyle.setFillBackgroundColor(HSSFColor.BLUE_GREY.index);
				cell.setCellStyle(cellstyle);
			}
			//添加统计内容
			for (short i =0; i <tempList.size(); i++) {
				List<TdObjModel> tdObjModelList=tempList.get(i);
				HSSFRow row=sheet.createRow(i+1);
				for (short j = 0; j < tdObjModelList.size(); j++) {
					TdObjModel tdObjModel=tdObjModelList.get(j);
					createCell(wb, row, j, tdObjModel.getName());
				}
			}
			//设定输出文件头,该方法有两个参数，分别表示应答头的名字和值。
			response.setHeader("Content-disposition", "attachment;filename="+new String(fileName.toString().trim().getBytes("utf-8"),"ISO-8859-1")+".xls");
			// 定义输出类型
			response.setContentType("application/msexcel");
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
	 * 导出统计后的结果，合并；
	 *fengguangping
	 * Oct 11, 2012-11:12:37 AM
	 * @param tempList
	 * @param headList
	 * @param fileName
	 * @param response
	 */
	public static void exportToXLSFileHasConbin(List<List<TdObjModel>> tempList,	List<String> headList,String fileName,HttpServletResponse response){
		//添加内容
//		for (short i =0; i <tempList.size(); i++) {
//			List<TdObjModel> tdObjModelList=tempList.get(i);
//			HSSFRow row=sheet.createRow(i+1);
//			if(tempList.size()<2){//如果只有一行直接把结果放入单元格中，
//				for (short j = 0; j < tdObjModelList.size(); j++) {
//					TdObjModel tdObjModel=tdObjModelList.get(j);
//					createCell(wb, row, j, tdObjModel.getName());
//				}
//			}else {//至少2行以上才存在跨行,跨行是从第二行开始，到rowspan-1行结束；
//				for (int k= 0; k < tdObjModelList.size(); k++) {
//					for (short j = 0; j < tdObjModelList.size(); j++) {
//						TdObjModel tdObjModel=tdObjModelList.get(j);
//						if(tdObjModel.isShow()){//当前model为true，
//							TdObjModel nextTdModel=tdObjModelList.get(j+1);//获取下一个model
//							if (nextTdModel!=null&&!nextTdModel.isShow()) {//下一个model的ishow为false时表示要跨行
//								int rowNumber=row.getRowNum();
//								int nextRowNumer=nextTdModel.getRowspan()+rowNumber-1;//被合并后的最后一行的行号
//								sheet.addMergedRegion(new Region((short)rowNumber,j,(short)nextRowNumer,j));
//							}
//							
//						}
//						createCell(wb, row, j, tdObjModel.getName());
//					}
//				}
//			}
//		}
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
	private static void createCell(HSSFWorkbook wb,HSSFRow row,short col,String val)	{
		HSSFCell cell=row.createCell(col);
		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell.setCellValue(val);
		HSSFCellStyle cellstyle=wb.createCellStyle();
		cellstyle.setAlignment(HSSFCellStyle.ALIGN_CENTER_SELECTION);
		cell.setCellStyle(cellstyle);
	}
}
