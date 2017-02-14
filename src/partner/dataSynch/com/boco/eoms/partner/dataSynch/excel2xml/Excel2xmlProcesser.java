package com.boco.eoms.partner.dataSynch.excel2xml;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import com.boco.eoms.partner.dataSynch.util.CommonUtil;
import com.boco.eoms.partner.dataSynch.util.DataSynchConstant;
import com.boco.eoms.partner.dataSynch.util.Excel2XMLUtil;

/**
 * 
 * Description: <p>
 *				数据同步Excel转化成xml
 *				</p>
 * Copyright:   Copyright (c)2012
 * Company:     BOCO
 * @author:     zhangkeqi
 * @version:    1.0
 * Create at:   Dec 5, 2012-9:52:33 PM
 */
public class Excel2xmlProcesser {
	private String excel2xmlDir;
	private String generateTime;
	private String resName;
	private String baseFileName;
	

	public String getBaseFileName() {
		return baseFileName;
	}

	public void setBaseFileName(String baseFileName) {
		this.baseFileName = baseFileName;
	}

	public String getResName() {
		return resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}

	public String getGenerateTime() {
		return generateTime;
	}

	public void setGenerateTime(String generateTime) {
		this.generateTime = generateTime;
	}

	public String getExcel2xmlDir() {
		return excel2xmlDir;
	}

	public void setExcel2xmlDir(String excel2xmlDir) {
		this.excel2xmlDir = excel2xmlDir;
	}

	/**
	 * Excel转xml处理
	 * @param excelFilePath
	 * @return
	 */
	public void excelFile2XmlFile(String excelFilePath) {
		File file = new File(excelFilePath);
//		if(!file.exists()) {
//			return null;
//		}
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			POIFSFileSystem poiFileSystem = new POIFSFileSystem(fis);
			HSSFWorkbook wb = new HSSFWorkbook(poiFileSystem);
			
			int sheetNum = wb.getNumberOfSheets();
			HSSFSheet sheet = null;
			sheet = wb.getSheetAt(0);
			
			int rowCount = sheet.getPhysicalNumberOfRows();
			
			HSSFRow row = null;
			writeIntoXML(sheet);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 将Excel的sheet生成XML数据
	 * @param sheet
	 */
	public void writeIntoXML(HSSFSheet sheet) {
		String time = new Date().getTime()+"";
		this.setGenerateTime(time);
//		String basePath = DataSynchConstant.getHandleSynchConfig().get("zip-path").toString()+File.separator+time;
		String basePath = this.getExcel2xmlDir()+File.separator+time;
		File baseDir = new File(basePath);
		if(!baseDir.exists()) {
			baseDir.mkdirs();
		}
		
		int rowCount = sheet.getPhysicalNumberOfRows();
		int beginRowNum = Integer.parseInt(DataSynchConstant.getHandleSynchConfig().get("excel-begin-row").toString());
		
		int totalCount = rowCount-beginRowNum;
		int pageSize = Integer.parseInt(DataSynchConstant.getHandleSynchConfig().get("page-file-size").toString());
		int page = totalCount / pageSize +1;
		
		//设置基础文件名
		this.setBaseFileName("PNR_"+resName+"_"+time);
		
		for(int i=1;i<=page;i++) {
			int beginIndex = pageSize*(i-1)+beginRowNum;
			int endIndex = ((pageSize*i)>totalCount?totalCount:(pageSize*i)) + beginRowNum;
			String resName = this.getResName();
			generateXMLData(sheet,beginIndex,endIndex,baseDir,time,resName,i);
		}
	}
	
	/**
	 * 序号生成工方法
	 * @param i
	 * @return
	 */
	public static String getPreIndex(int i) {
		String index = "";
		if(i<10) {
			index = "00"+i;
		} else if(i<100) {
			index = "0"+i;
		} else {
			index = i +"";
		}
		return index;
	}
	
	/**
	 * CHK文件内容添加方法
	 * @param chkFile
	 * @param line
	 */
	public static void addCHKFileRecorde(File chkFile,String line) {
		try {
			BufferedWriter br = new BufferedWriter(new FileWriter(chkFile,true));
			br.write(line);
			br.write("\r\n");
			br.flush();
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 分页xml生成数据
	 * @param sheet excel 表单
	 * @param begin 开始行号
	 * @param end   结束行号
	 * @param time  时间戳
	 * @param resName 资源名称
	 * @param index   文件序号
	 */
	public void generateXMLData(HSSFSheet sheet,int begin,int end,File baseDir,String time,String resName,int index) {
		Map<String,Map> map = Excel2XMLUtil.excelMappingMap.get(resName);
		HSSFCell cell;
		HSSFRow row;
		FileWriter fw = null;
//		BufferedWriter bw = null;
		ZipOutputStream zos = null;
//		OutputStreamWriter osw = null;
		try {
			String dataFileNamePrefix = "PNR_"+resName+"_"+time+getPreIndex(index);
			
			File zipFile = new File(baseDir,dataFileNamePrefix+".zip");
			
			zos = new ZipOutputStream(new FileOutputStream(zipFile));
			zos.setEncoding("UTF-8");
			ZipEntry ze = new ZipEntry(dataFileNamePrefix+".xml");
			zos.setEncoding("UTF-8");
			zos.putNextEntry(ze);
//			osw = new OutputStreamWriter(zos);
//			bw = new BufferedWriter(osw);
			
			zos.write("<?xml version='1.0' encoding='utf-8'?>".getBytes("UTF-8"));
			zos.write("\r\n".getBytes("UTF-8"));
			zos.write("<resource-data>".getBytes("UTF-8"));
			zos.write("\r\n".getBytes("UTF-8"));
			zos.write(("	<notifyId>"+resName+"</notifyId>").getBytes("UTF-8"));
			zos.write("\r\n".getBytes("UTF-8"));
			for(int i=begin;i<end;i++) {
				row = sheet.getRow(i);
				
				zos.write("	<class>".getBytes("UTF-8"));
				zos.write("\r\n".getBytes("UTF-8"));
				zos.write("		<updateType>3</updateType>".getBytes("UTF-8"));
				zos.write("\r\n".getBytes("UTF-8"));
				zos.write(("		<resourceKind>"+resName+"</resourceKind>").getBytes("UTF-8"));
				zos.write("\r\n".getBytes("UTF-8"));
				zos.write(("		<resourceId>"+CommonUtil.generateUUID()+"</resourceId>").getBytes("UTF-8"));
				zos.write("\r\n".getBytes("UTF-8"));
				zos.write("		<attributes>".getBytes("UTF-8"));
				zos.write("\r\n".getBytes("UTF-8"));
				
				zos.flush();
//				osw.flush();
//				bw.flush();
				for(int j=0;j<map.keySet().size();j++) {
					String name = map.get(j+"").get("name").toString();
					int num = Integer.parseInt(map.get(j+"").get("excelCellNum").toString());
					System.out.println("i="+i+";j="+j+";num="+num);
					String value = row.getCell(num).getRichStringCellValue().getString();
					zos.write(("			<attribute name=\""+name+"\" value=\""+value+"\" />").getBytes("UTF-8"));
					zos.write("\r\n".getBytes("UTF-8"));
				}
				zos.flush();
//				osw.flush();
//				bw.flush();	
				
				zos.write("		</attributes>".getBytes("UTF-8"));
				zos.write("\r\n".getBytes("UTF-8"));
				zos.write("	</class>".getBytes("UTF-8"));
				zos.write("\r\n".getBytes("UTF-8"));
			}
			zos.write("</resource-data>".getBytes("UTF-8"));
			zos.flush();
//			osw.flush();
//			bw.flush();
			
			zos.closeEntry();
			
			//添加CHKFile的内容 begin
			String chkFileName = "PNR_"+resName+"_"+time+".CHK";
			File chkFile = new File(baseDir,chkFileName);
			String count = (end - begin) + "";
			String line = dataFileNamePrefix+".zip" + "        " + ze.getSize() + "        " + count + "        " + CommonUtil.generateUUID();
			addCHKFileRecorde(chkFile,line);
			//添加CHKFile的内容 end
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
//				bw.close();
//				osw.close();
				zos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	

	public static void main(String[]sss) throws Exception {
		Excel2XMLUtil.initMap();
		Excel2xmlProcesser processer = new Excel2xmlProcesser();
		Map map = DataSynchConstant.getHandleSynchConfig();
		String filePath = "E:\\workspace_xa\\resource\\陕西数据\\直放站\\直放站120121129113337.xls";
		processer.excelFile2XmlFile(filePath);
	}
}
