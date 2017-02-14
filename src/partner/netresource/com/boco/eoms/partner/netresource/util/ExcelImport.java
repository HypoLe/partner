package com.boco.eoms.partner.netresource.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.struts.upload.FormFile;

public class ExcelImport {

	private int dataStartRow;
	private int dataStartCol;
	private int totalCol;
	
	/**
	 * 构造方法
	 * @param dataStartRow  实际数据开始行(从0计数)
	 * @param dataStartCol  实际数据开始列(从0计数)
	 * @param totalCol		有效数据总列数
	 */
	public ExcelImport(int dataStartRow,int dataStartCol,int totalCol) {
		this.dataStartRow = dataStartRow;
		this.dataStartCol = dataStartCol;
		this.totalCol = totalCol;
	}
	
	/**
	 * 数据导入方法，传入文件和保存数据的回调类（可以匿名传入）--设备
	 * @param formFile
	 * @param callBack
	 * @param flag 第几个sheet
	 * @return
	 * @throws Exception
	 */
	public ImportResult importFromFile(FormFile formFile,int flag,DataSaveCallback callBack) throws Exception{
		HSSFSheet sheet = this.fileCheck(formFile,flag);
		int count = 0;
		int point = 0;
		ImportResult result = new ImportResult();
		try {
			for (int i = dataStartRow; i <= sheet.getLastRowNum(); i++) { // 获取工作表中每行数据
				point = i;
				count++;
				HSSFRow row = sheet.getRow(i);
				if(!this.blankRowCheck(row)) {
					callBack.doSaveRow2Data(row);
				} else {
					count--;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.setResultCode("503");
			String msg = result.getRestultMsg();
			if (!msg.equals("")) {
				msg += ",";
			}
//			result.setRestultMsg("导入错误:错误行数是:"+msg + (point + 1));
			throw new Exception("导入错误:错误行数是:" +msg + (point + 1)+e.getMessage());
		}
		
		result.setRestultMsg("导入成功");
		result.setResultCode("200");
		result.setImportCount(count);
		return result;
	}
	public List<String> getExcelNameList(FormFile formFile,int flag,int index)throws Exception{
		HSSFSheet sheet = this.fileCheck(formFile,flag);
		//存放Excel表格中每条记录的机房名称
		List<String> nameArray = new ArrayList<String>();
		try{
			for (int i = dataStartRow; i <= sheet.getLastRowNum(); i++) { // 获取工作表中每行数据
				HSSFRow row = sheet.getRow(i);
				if(!this.blankRowCheck(row)) {
					nameArray.add(row.getCell(index).toString());
				} 
			}
			}catch(Exception e){
			}
		return nameArray;
	}
	/**
	 * 判断excel表格中是否有机房名称相同的数据
	 * @param formFile
	 * @param nowName
	 * @param flag 第几个sheet
	 * @return true:存在相同机房名称的数据;false 不存在
	 * @throws Exception
	 */
	public boolean isHaveSameName(String nowName,List<String> nameList)throws Exception{
		int count =0;
		try{
			if(nameList!=null && nameList.size()>1){
				for(String s :nameList){
					if(s.equals(nowName)){
						count++;
					}
				}
				if(count>=2){
					return  true;
				}else{
					return false;
				}
			}else{
				return false;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	/**
	 * 文件检查---设备
	 * @param formFile
	 * @return
	 * @throws Exception
	 */
	private HSSFSheet fileCheck(FormFile formFile,int flag) throws Exception {
		ImportResult result = new ImportResult();
		String fileName = formFile.getFileName();
		InputStream is = formFile.getInputStream();
		result.setRestultMsg("");
		
		//检查是否为Excel文件
		if (!fileName.endsWith(".xls")) { 
			result.setRestultMsg(result.getRestultMsg() + "导入文件非法");
			result.setResultCode("500");
			throw new Exception("导入错误:" + result.getRestultMsg());
		}
		
		// 检查能否获取工作薄
		HSSFWorkbook wb = null;
		try {
			wb = new HSSFWorkbook(new POIFSFileSystem(is));
		} catch (Exception e) {
			result.setResultCode("500");
			result.setRestultMsg(result.getRestultMsg() + "导入文件非法");
		}
		if (wb == null) {
			result.setRestultMsg("不能获取工作薄");
			result.setResultCode("501");
			throw new Exception("导入错误:" + result.getRestultMsg());
		}
		
		// 检查能否获取工作表
		int i =wb.getNumberOfSheets();
		HSSFSheet sheet =null;
		if(flag<=i){
			sheet = wb.getSheetAt(flag-1);			
		}
		
		
		if (sheet == null) { 
			result.setRestultMsg("不能获取工作表");
			result.setResultCode("502");
			throw new Exception("导入错误:" + result.getRestultMsg());
		} else {
			return sheet;
		}
	}
		
	/**
	 * 空行检测，遇到空行则结束导入，即结束标志
	 * @param row
	 * @return
	 */
	private boolean blankRowCheck(HSSFRow row) {
		HSSFCell cell;
		int j = 0;
		for(int i=0;i<totalCol;i++) {
			cell = row.getCell(i+dataStartCol);
			if(cell == null) {
				j++;
			}else if("".equals(cell.toString())) {
				j++;
			}
		}
		if(j >= totalCol) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 将数据写到excel中；
	 * @param  outfilepath 输出的路径
	 * @param string[] List 存放string[]集合
	 * @int  sheet 第几个sheet(目前最多第5个（含）)
	 * @String[] names 存放表头的数组
	 * String sheetName   sheet的名字
	 * int rowNum 第几行开始写数据
	 * 
	 */
	
	public  void errorObjtoExcel(String outfilepath,List<Object> cellList,int sheet,String[] names,String sheetName,int rowNum){
		
		
		// 创建Excel文档
		HSSFWorkbook hwb = new HSSFWorkbook();
		
		// 获取总行数 
		int CountColumnNum = cellList.size();
	
		// sheet 对应一个工作页
		
		if(sheet==1){
			
		}else if(sheet==2){			
			HSSFSheet sheet1 =hwb.createSheet();
		}else if(sheet==3){			
			HSSFSheet sheet1 =hwb.createSheet();
			HSSFSheet sheet2 =hwb.createSheet();
		}else if(sheet==4){			
			HSSFSheet sheet1 =hwb.createSheet();
			HSSFSheet sheet2 =hwb.createSheet();
			HSSFSheet sheet3 =hwb.createSheet();
		}else if(sheet==5){			
			HSSFSheet sheet1 =hwb.createSheet();
			HSSFSheet sheet2 =hwb.createSheet();
			HSSFSheet sheet3 =hwb.createSheet();
			HSSFSheet sheet4 =hwb.createSheet();
		}
		
		HSSFSheet useSheet =hwb.createSheet(sheetName);
		HSSFSheet sheet0 =hwb.createSheet();//防止读不到文件
		HSSFRow firstrow = useSheet.createRow(0); //下标为0的行开始
		
		//获取表头的数量
		int namesLength = names.length;
		HSSFCell[] firstcell = new HSSFCell[namesLength];

		//设置表头
		for(int j =0 ; j< namesLength; j++){
			
			firstcell[j] = firstrow.createCell((short)j);
			firstcell[j].setCellValue(new HSSFRichTextString(names[j]));
		}
		//开始插入数据
		for(int i=0;i<CountColumnNum;i++){
			//创建一行
			HSSFRow row = useSheet.createRow(i + (rowNum-1));
			// 得到要插入的每一条记录
			String[] cell = (String[])cellList.get(i);
		
			for(int colu=0; colu<namesLength; colu++){
				//在一行内循环
				HSSFCell xm1 = row.createCell((short) (colu));				
				xm1.setCellValue(new HSSFRichTextString(cell[colu]));
			}
		}
		// 创建文件输出流，准备输出电子表格
		OutputStream out = null;
		try {
			out = new FileOutputStream(outfilepath);		
			hwb.write(out);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
			    out =null;
				e.printStackTrace();
			
			}
		}
		
	//	System.out.println("ExcelImport.java:242数据库导出成功");		
		
	}
	
	
	/**
	 * 将数据写到excel中（新）
	 * @param outfilepath	输出的路径
	 * @param cellList	存放string[]集合
	 * @param sheet	第几个sheet(目前最多第5个（含）)
	 * @param headers	存放表头
	 * @param sheetName	sheet的名字
	 * @param rowNum	第几行开始写数据
	 * @param totalColumns	总列数
	 */
	public  void errorObjtoExcelNew(String outfilepath,List<Object> cellList,int sheet,Map<Integer,String[]> headers,String sheetName,int rowNum,int totalColumns){
		
		
		// 创建Excel文档
		HSSFWorkbook hwb = new HSSFWorkbook();
		
		HSSFCellStyle cellStyle=hwb.createCellStyle();     
		cellStyle.setWrapText(true);     
		
		// 获取总行数 
		int CountColumnNum = cellList.size();
	
		// sheet 对应一个工作页
		
		if(sheet==1){
			
		}else if(sheet==2){			
			HSSFSheet sheet1 =hwb.createSheet();
		}else if(sheet==3){			
			HSSFSheet sheet1 =hwb.createSheet();
			HSSFSheet sheet2 =hwb.createSheet();
		}else if(sheet==4){			
			HSSFSheet sheet1 =hwb.createSheet();
			HSSFSheet sheet2 =hwb.createSheet();
			HSSFSheet sheet3 =hwb.createSheet();
		}else if(sheet==5){			
			HSSFSheet sheet1 =hwb.createSheet();
			HSSFSheet sheet2 =hwb.createSheet();
			HSSFSheet sheet3 =hwb.createSheet();
			HSSFSheet sheet4 =hwb.createSheet();
		}
		
		HSSFSheet useSheet =hwb.createSheet(sheetName);
		HSSFSheet sheet0 =hwb.createSheet();//防止读不到文件
		
		
		 Iterator<Map.Entry<Integer,String[]>> it = headers.entrySet().iterator();
		  while (it.hasNext()) {
		   Map.Entry<Integer,String[]> entry = it.next();
		   HSSFRow firstrow = useSheet.createRow(entry.getKey().intValue()); 
		   String[] names = entry.getValue();
		 //获取表头的数量
			int namesLength = names.length;
			HSSFCell[] firstcell = new HSSFCell[namesLength];
			//设置表头
			for(int j =0 ; j< namesLength; j++){				
				firstcell[j] = firstrow.createCell((short)j);
				firstcell[j].setCellStyle(cellStyle);
				firstcell[j].setCellValue(new HSSFRichTextString(names[j]));
			}
		  }
		
		
		
		
		
		//开始插入数据
		for(int i=0;i<CountColumnNum;i++){
			//创建一行
			HSSFRow row = useSheet.createRow(i + (rowNum-1));
			// 得到要插入的每一条记录
			String[] cell = (String[])cellList.get(i);
		
			for(int colu=0; colu<totalColumns; colu++){
				//在一行内循环
				HSSFCell xm1 = row.createCell((short) (colu));				
				xm1.setCellValue(new HSSFRichTextString(cell[colu]));
			}
		}
		// 创建文件输出流，准备输出电子表格
		OutputStream out = null;
		try {
			out = new FileOutputStream(outfilepath);		
			hwb.write(out);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			
			try {
				out.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
			    out =null;
				e.printStackTrace();
			
			}
		}
		
	//	System.out.println("ExcelImport.java:242数据库导出成功");		
		
	}
	
	
	
	
	/**
     * 根据HSSFCell类型设置数据
     * 解决手机号，电话号以科学计数法方式获取的问题
     * @param cell
     * @return
     */
	public static String getStringCellValue(HSSFCell cell) {
        String strCell = "";

        if (cell == null) {
            return "";
        }
      
        switch (cell.getCellType()) {
        case HSSFCell.CELL_TYPE_STRING:
            strCell = cell.getRichStringCellValue().toString();
            break;
        case HSSFCell.CELL_TYPE_NUMERIC:
        	DecimalFormat df = new DecimalFormat("#");
            strCell = df.format(cell.getNumericCellValue());
            break;
        case HSSFCell.CELL_TYPE_BOOLEAN:
            strCell = String.valueOf(cell.getBooleanCellValue());
            break;
        case HSSFCell.CELL_TYPE_BLANK:
            strCell = "";
            break;
        default:
            strCell = "";
            break;
        }
        if (strCell.equals("") || strCell == null) {
        	strCell= "";
        }
        return strCell;
    }
	/**
	 *  统一保存错误的文件路径
	 * @return
	 */
	public static String outfilePath(String osPath,String name){
		String symbol=File.separator;		
		String path=osPath+symbol+"errorfile"+symbol;
		if(name.endsWith(".xls")){
			path=path+name;
		}else{
			path=path+name+".xls";
		}
		return path;
	}
	
	public int getDataStartRow() {
		return dataStartRow;
	}

	public void setDataStartRow(int dataStartRow) {
		this.dataStartRow = dataStartRow;
	}

	public int getDataStartCol() {
		return dataStartCol;
	}

	public void setDataStartCol(int dataStartCol) {
		this.dataStartCol = dataStartCol;
	}

	public int getTotalCol() {
		return totalCol;
	}

	public void setTotalCol(int totalCol) {
		this.totalCol = totalCol;
	}
}
