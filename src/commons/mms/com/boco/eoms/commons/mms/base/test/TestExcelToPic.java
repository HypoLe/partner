package com.boco.eoms.commons.mms.base.test;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.boco.eoms.commons.mms.base.util.ExcelToPic;

public class TestExcelToPic {
	public static void main(String[] args)
	{
		ExcelToPic etp = new ExcelToPic();
		
		String realPath = "D:/";
		String filename = "test.gif";
		String excelName = "123.xls";//"2008-12-05-11-57-42402881ef1e0a6dfe011e0a71932e000d.xls";//"test.xls";//"test.xls";//"402881861f7e0ec9011f7e1bb5e3000720090216160239.xls";
			
		String newFileName = "temp.xls";
		
		int headStart = 3;
		//删除excel的第一行表头
		String headString = "表头的内容:请填写";
		
		
		//绘制excel为pic
		etp.createRowsImage(realPath, filename, excelName,headStart,350,Color.yellow);
		
		//		etp.createColunmsImage(realPath, filename, excelName);
		
	}
}
