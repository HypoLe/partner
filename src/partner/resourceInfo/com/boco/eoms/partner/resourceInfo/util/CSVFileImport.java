package com.boco.eoms.partner.resourceInfo.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.struts.upload.FormFile;

import com.boco.eoms.partner.process.util.XLSModel;

public  abstract class CSVFileImport {
	public ImportResult csvFileValidate(FormFile formFile) throws Exception{
		CsvReader reader = new CsvReader(formFile.getInputStream(),',',Charset.forName("GBK"));
		XLSModel xlsModel=this.getXLSModel();
		int count = 0;
		ImportResult result = new ImportResult();
		try {
			reader.readHeaders();
			while(reader.readRecord())
				if (this.doSaveRow2Data(reader.getValues())) {
					count++;
				}
		} catch (Exception e) {
			e.printStackTrace();
			result.setResultCode("503");
			String msg = result.getRestultMsg();
			if (!msg.equals("")) {
				msg += ",";
			}
			result.setRestultMsg(e.getMessage());
			throw new RuntimeException(result.getRestultMsg());
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
	private HSSFSheet fileCheck(File file) throws Exception {
		FileInputStream fis = new FileInputStream(file);
		String fileName = file.getName();
		try {
			return check(fileName, fis);
		} finally {
			fis.close();
		}
	}
	
	/**
	 * 文件检查
	 * @param formFile
	 * @return
	 * @throws Exception
	 */
	private HSSFSheet fileCheck(FormFile formFile) throws Exception {
		InputStream is = formFile.getInputStream();
		String fileName = formFile.getFileName();
		try {
			return check(fileName, is);
		} finally{
			is.close();
		}
	}
	
	private HSSFSheet check(String fileName,InputStream is) throws Exception {
		ImportResult result = new ImportResult();
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
	private boolean blankRowCheck(HSSFRow row,XLSModel xlsModel) {
		HSSFCell cell;
		int j = 0;
		for(int i=0;i<xlsModel.getAddTotalCol();i++) {
			cell = row.getCell(i+xlsModel.getAddStartCol());
			if(cell == null) {
				j++;
			}else if("".equals(cell.toString())) {
				j++;
			}
		}
		if(j >= xlsModel.getAddTotalCol()) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 上传数据的excel表格的规格，对addStartRowNum,addStartCol，addTotalCol进行赋值，其余数据随意赋值
	 * 增加数据开始行，开始列，总列数
	 * @return
	 */
	public abstract  XLSModel getXLSModel();
	/**
	 * 执行数据保存，执行前需要验证数据
	 * @return
	 * @throws Exception
	 */
	public abstract  boolean doSaveRow2Data(String[] values) throws Exception;
}
