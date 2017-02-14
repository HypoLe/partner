package com.boco.eoms.netresource.line.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.struts.upload.FormFile;


public class LinesExcelImport {

	private int dataStartRow;
	private int dataStartCol;
	private int totalCol;
	
	/**
	 * 构造方法
	 * @param dataStartRow  实际数据开始行(从0计数)
	 * @param dataStartCol  实际数据开始列(从0计数)
	 * @param totalCol		有效数据总列数
	 */
	public LinesExcelImport(int dataStartRow,int dataStartCol,int totalCol) {
		this.dataStartRow = dataStartRow;
		this.dataStartCol = dataStartCol;
		this.totalCol = totalCol;
	}
	
	/**
	 * 数据导入方法，传入文件和保存数据的回调类（可以匿名传入）
	 * @param formFile
	 * @param callBack
	 * @return
	 * @throws Exception
	 */
	public LinesImportResult importFromFile(FormFile formFile,LinesDataSaveCallback callBack) throws Exception{
		HSSFSheet sheet = this.fileCheck(formFile);
		int count = 0;
		int point = 0;
		LinesImportResult result = new LinesImportResult();
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
			result.setRestultMsg("【导入错误】：错误行数是Excel第 "+msg + (point + 1)+" 行。");
			throw new Exception(result.getRestultMsg()+"【错误原因】："+e.getMessage());
		}
		
		result.setRestultMsg("导入成功！");
		result.setResultCode("200");
		result.setImportCount(count);
		return result;
	}
	/**
	 * 数据导入方法，传入文件和保存数据的回调类（可以匿名传入）
	 * @param formFile
	 * @param callBack
	 * @return
	 * @throws Exception
	 */
	public LinesImportResult importFromFile(File file,LinesDataSaveCallback callBack) throws Exception{
		int count = 0;
		int point = 0;
		LinesImportResult result = new LinesImportResult();
		try {
			FileInputStream fint;
			fint = new FileInputStream(file);
			POIFSFileSystem poiFileSystem = new POIFSFileSystem(fint);
			HSSFWorkbook workbook = new HSSFWorkbook(poiFileSystem);
			HSSFSheet sheet = workbook.getSheetAt(0);
			
			for (int i = dataStartRow; i <= sheet.getLastRowNum(); i++) { // 获取工作表中每行数据
				point = i;
				count++;
				HSSFRow row = sheet.getRow(i);
				if(!this.blankRowCheck(row)) {
					callBack.doSaveRow2Data(row);
				} else {
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
			result.setRestultMsg("【导入错误】：错误行数是Excel第 "+msg + (point + 1)+" 行。");
			throw new Exception(result.getRestultMsg()+"【错误原因】："+e.getMessage());
		}
		
		result.setRestultMsg("导入成功");
		result.setResultCode("200");
		result.setImportCount(count);
		return result;
	}
	
	/**
	 * 文件检查
	 * @param formFile
	 * @return
	 * @throws Exception
	 */
	private HSSFSheet fileCheck(FormFile formFile) throws Exception {
		LinesImportResult result = new LinesImportResult();
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
		HSSFSheet sheet = wb.getSheetAt(0);
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
