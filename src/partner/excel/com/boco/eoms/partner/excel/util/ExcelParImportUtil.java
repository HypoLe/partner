package com.boco.eoms.partner.excel.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.base.util.UUIDHexGenerator;
import com.boco.eoms.common.util.MyBeanUtils;
import com.boco.eoms.common.util.StaticMethod;
import com.boco.eoms.partner.excel.mgr.IParContentsExcelMgr;
import com.boco.eoms.partner.excel.mgr.IParExcelMgr;
import com.boco.eoms.partner.excel.model.IParExcelDeal;

import de.javawi.jstun.attribute.Username;


/**
 * <p>Title: Excel的导入功能</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * <p>Company: </p>
 * @author benweiwei
 * @version 1.0(2009-10-23)
 */
public class ExcelParImportUtil {

	private String modelName = null; // 所属模块的名称
	private String excelFilePath = null; // 上传后Excel保存的路径
//		将EXCEL文件上传到服务器，并返回上传文件保存的路径
	public File pushExcelFile(HttpServletRequest request, String userName,
			String deptName) {
		File newExcelFile = null;

		try {
			/*
			 * 上传项目只要足够小，就应该保留在内存里 较大的项目应该被写在硬盘的临时文件上。 非常大的上传请求应该避免。
			 * 限制项目在内存中所占的空间，限制最大的上传请求，并且设定临时文件的位置。
			 */

			request.setCharacterEncoding("utf-8");

			RequestContext requestContext = new ServletRequestContext(request);

			if (FileUpload.isMultipartContent(requestContext)) {

				// 附件上传后保存的路径
	
				String uploadPath =  ExcelParImportUtil.class.getResource("").toString();
				System.out.println("sPath==="+uploadPath);
				uploadPath = uploadPath.substring(5,uploadPath.indexOf("WEB-INF"))+"partnerUpload";
				System.out.println("configPath==="+uploadPath);
				
				
				File uploadDir = new File(uploadPath); // 文件缓冲区
				if (!uploadDir.exists()) {
					uploadDir.mkdir();
				}

				DiskFileItemFactory factory = new DiskFileItemFactory();
				factory.setSizeThreshold(1 * 1024 * 1024); // 设置缓冲区大小，这里是1M
				// (最大内存占用)
				factory.setRepository(uploadDir); // 设置临时目录

				ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
				servletFileUpload.setSizeMax(100 * 1024 * 1024); // 最大请求大小，这里是100M。如果为-1表示不限制大小

				// 解析请求，结果存于一个list中
				List items = servletFileUpload.parseRequest(request);
				Iterator iter = items.iterator();

				// 遍历这个list访问每个单独的文件项
				while (iter.hasNext()) {
					FileItem fileItem = (FileItem) iter.next();

					// 用isFormField()函数区分上传文件和常规类型域。
					if (!fileItem.isFormField()) {
						// 获得要上传的Excel文件名称，这个文件名包括路
						String fileItemName = fileItem.getName();
						if (fileItemName != null && !fileItemName.equals("")) {
							File excelFile = new File(fileItemName); // 要上传的文件对象
							// 要上传的Excel文件名称
							String fileName = excelFile.getName();
							fileName = fileName.substring(fileName.lastIndexOf("\\")+1, fileName.length());
							System.out.println("-----filepath------"+fileName);							
							// 附件的扩展名
							String expName = fileName.substring(fileName.lastIndexOf('.'));

							if (expName.toLowerCase().equals(".xls")) {
								// 附件上传后的文件名称
								String newFileName = StaticMethod.getCurrentDateTime("yyyyMMddHHmmss")
												+ "_" + userName + "_"+ deptName+ "_"+ fileName;

								excelFilePath = uploadPath + File.separator+ newFileName;

								// 上传后的文件对象
								newExcelFile = new File(excelFilePath);

								fileItem.write(newExcelFile); // 写入文件
							}
						}
					} else if (fileItem.isFormField()) {
						if (fileItem.getFieldName().equals("modelName")) {
							modelName = fileItem.getString();
						}
					}
				}
			}
		} catch (FileUploadException ex) {
			excelFilePath = null;
			ex.printStackTrace();
		} catch (UnsupportedEncodingException ex) {
			excelFilePath = null;
			ex.printStackTrace();
		} catch (Exception ex) {
			excelFilePath = null;
			ex.printStackTrace();
		}

		return newExcelFile;
	}
//	jdbc 拼接插入的sql语句
	public List sheet2sqlJDBC(HSSFSheet sheet, int beginRow, int endRow)throws Exception{

		ArrayList sheetList = new ArrayList();

		int numberOfRows = sheet.getPhysicalNumberOfRows();
//		System.out.println(" ********** 本次共有 " + numberOfRows + " 行需要导入 **********");

		// 判断这个 SHEET 中是否存在行数据
		if (numberOfRows > beginRow) {
			HSSFRow tableRow = sheet.getRow(0);// 表的名字
			HSSFRow colsRow = sheet.getRow(1); // 字段名称_行
			HSSFRow typeRow = sheet.getRow(2); // 字段类型_行
			HSSFRow sizeRow = sheet.getRow(3); // 字段大小_行
			HSSFRow nameRow = sheet.getRow(4); // 字段含义_行

			for (int i = beginRow; i < numberOfRows; i++) {
				String id = null;
				try {
					id = UUIDHexGenerator.getInstance().getID();
				} catch (Exception e) {
					e.printStackTrace();
				}
//				sql的拼接
				StringBuffer sql = new StringBuffer();
				StringBuffer sqlUp = new StringBuffer();
				StringBuffer sqlclo = new StringBuffer();
				StringBuffer sqlval = new StringBuffer();
				StringBuffer sqlwhere = new StringBuffer();
				sqlUp.append("update ");
				sql.append("insert into ");
				sqlwhere.append(" where id = '");
//				判断是否有表名
				String tableName = tableRow.getCell(1).getStringCellValue();
				if ("tableName".equals(tableRow.getCell(0).getStringCellValue())&&!"".equals(tableName)&&null!=tableName) {
					sql.append(tableName);
					sqlUp.append(tableName);
					sqlUp.append(" set ");
//					if("id".equals(tableRow.getCell(2).getStringCellValue())){
//						sqlclo.append(" (id,");
//						sqlval.append(" values('"+ id + "',");
//					}else{
						sqlclo.append(" (");
						sqlval.append(" values(");
//					}
				}else {
					break;
				}
				
				HSSFRow valueRow = sheet.getRow(i); // 读取一行数据

				int numberOfCells = valueRow.getPhysicalNumberOfCells();
//				System.out.println(" ===== 本次共有 " + numberOfCells + " 列需要导入 =====");

				for (short cellNum = 0; cellNum < numberOfCells; cellNum++) {
					HSSFCell colsCell = colsRow.getCell(cellNum); // 字段名称
					HSSFCell typeCell = typeRow.getCell(cellNum); // 字段类型
					HSSFCell sizeCell = sizeRow.getCell(cellNum); // 字段大小
					HSSFCell nameCell = nameRow.getCell(cellNum); // 字段含义

					HSSFCell valueCell = valueRow.getCell(cellNum); // 字段值
					sqlclo.append(colsCell.getStringCellValue());
					if (valueCell == null) {
						continue;
					}
					switch (valueCell.getCellType()) {
					case HSSFCell.CELL_TYPE_NUMERIC: // 数字 0
						if (typeCell.getStringCellValue().equals("integer")) {
							int value = (int) valueCell.getNumericCellValue();
							sqlUp.append(colsCell.getStringCellValue());
							sqlUp.append("=");
							sqlUp.append(value);
							sqlval.append(value);
						} else {
							sqlUp.append(colsCell.getStringCellValue());
							sqlUp.append("=");
							sqlUp.append(valueCell.getNumericCellValue());
							sqlval.append(valueCell.getNumericCellValue());
						}
						break;
					case HSSFCell.CELL_TYPE_STRING: // 字符 1
//						System.out.println(" 字段值="+ valueCell.getStringCellValue());
						if (typeCell.getStringCellValue().equals("integer")) {
							int value = Integer.parseInt(valueCell.getStringCellValue());
							sqlUp.append(colsCell.getStringCellValue());
							sqlUp.append("=");
							sqlUp.append(value);
							sqlval.append(value);
						} else {
							if (typeCell.getStringCellValue().equals("timestamp")||typeCell.getStringCellValue().equals("date")) {
								if ("org.hibernate.dialect.InformixDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())) {
									sqlUp.append(colsCell.getStringCellValue());
									sqlUp.append("=");
									sqlUp.append("'");
									sqlUp.append(valueCell.getStringCellValue());
									sqlUp.append("'");
									sqlval.append("'");
									sqlval.append(valueCell.getStringCellValue());
									sqlval.append("'");
								}
								else if("org.hibernate.dialect.OracleDialect".equals(ApplicationContextHolder.getInstance().getHQLDialect())){
									sqlUp.append(colsCell.getStringCellValue());
									sqlUp.append("=");
									sqlUp.append("to_date('"+ valueCell.getStringCellValue()+ "', 'yyyy-MM-dd HH24:mi:ss')");
									sqlval.append("to_date('"+ valueCell.getStringCellValue()+ "', 'yyyy-MM-dd HH24:mi:ss')");
								} 
							} else if(colsCell.getStringCellValue().trim().equals("id")){
								if (!"".equals(valueCell.getStringCellValue())&&null!=valueCell.getStringCellValue()) {
									sqlval.append("'");
									sqlval.append(valueCell.getStringCellValue());
									sqlval.append("'");
								}
								sqlwhere.append(valueCell.getStringCellValue());
							} else {
								sqlUp.append(colsCell.getStringCellValue());
								sqlUp.append("=");
								sqlUp.append("'");
								sqlUp.append(valueCell.getStringCellValue());
								sqlUp.append("'");
								sqlval.append("'");
								sqlval.append(valueCell.getStringCellValue());
								sqlval.append("'");
							}
						}
						break;
					case HSSFCell.CELL_TYPE_FORMULA: // 公式 2
//						System.out.println(" 字段值=" + valueCell.getCellFormula());
						sqlUp.append(colsCell.getStringCellValue());
						sqlUp.append("=");
						sqlUp.append("'");
						sqlUp.append(valueCell.getStringCellValue());
						sqlUp.append("'");
						sqlval.append("'");
						sqlval.append(String.valueOf(valueCell.getBooleanCellValue()));
						sqlval.append("'");
						break;
					case HSSFCell.CELL_TYPE_BLANK: // 空白 3
//						System.out.println(" 字段值="+ valueCell.getStringCellValue());
						if(colsCell.getStringCellValue().equals("id")){
							sqlval.append("'");
							sqlval.append(id);
							sqlval.append("'");
							sqlwhere.append(valueCell.getStringCellValue());
						}else{
							sqlUp.append(colsCell.getStringCellValue());
							sqlUp.append("=");
							sqlUp.append("'");
							sqlUp.append(valueCell.getStringCellValue());
							sqlUp.append("'");
							sqlval.append("'");
							sqlval.append("");
							sqlval.append("'");
						}	
						break;
					case HSSFCell.CELL_TYPE_BOOLEAN: // 布尔 4
//						System.out.println(" 字段值="+ valueCell.getBooleanCellValue());
						sqlUp.append(colsCell.getStringCellValue());
						sqlUp.append("=");
						sqlUp.append("'");
						sqlUp.append(valueCell.getStringCellValue());
						sqlUp.append("'");
						sqlval.append("'");
						sqlval.append(String.valueOf(valueCell.getBooleanCellValue()));
						sqlval.append("'");
						break;
					case HSSFCell.CELL_TYPE_ERROR: // 错误的数据 5
						break;
					default:
					}
					if (numberOfCells != cellNum + 1) {
						sqlclo.append(",");
						sqlval.append(",");
					}else{
						sqlclo.append(")");
						sqlval.append(")");
					}
					if (!colsCell.getStringCellValue().equals("id")) {
						sqlUp.append(",");
					}
				}
				if("idupdate".equals(tableRow.getCell(2).getStringCellValue())){
					sqlwhere.append("'");
					String sqlUps = sqlUp.toString();
					if(",".equals(sqlUps.substring(sqlUps.length()-1,sqlUps.length()))){
						sqlUps = sqlUps.trim().substring(0,sqlUps.lastIndexOf(","));
					}
					sheetList.add(sqlUps+sqlwhere.toString());
				}else{
					sheetList.add(sql.toString()+sqlclo.toString()+sqlval.toString());
//					System.out.println(sql.toString()+sqlclo.toString()+sqlval.toString());
				}
			}
		}
		return sheetList;
	}

//	jdbc 导入excel
	public List processExcelJDBC(File excelFile, int beginRow, int endRow ,IParExcelMgr ikmExcelMgr)
		throws Exception {
	
		ArrayList okList = new ArrayList();
		
		FileInputStream inputStream = new FileInputStream(excelFile.getAbsolutePath());
		
		// 创建要导入的Excel文件读入流
		POIFSFileSystem poiFileSystem = new POIFSFileSystem(inputStream);
		
		// 创建Excel工作薄对象
		HSSFWorkbook workBook = new HSSFWorkbook(poiFileSystem);
		
		List sheetList = null;
		for (int sheetNum = 0; sheetNum < 1; sheetNum++) {
			// 读取 EXCEL 文件中的一个 SHEET
			HSSFSheet sheet = workBook.getSheetAt(sheetNum);
		
			// 解析一个 Sheet， 将 Sheet 中的每一行都转换成一个 Map。
		
			sheetList = new ArrayList();
			try {
				sheetList = this.sheet2sqlJDBC(sheet, beginRow, endRow);
			} catch (Exception e) {
				e.printStackTrace();
				okList.add("模板错误");
			}
			if(sheetList.size()==0){
				okList.add("模板错误,请选择正确的模板");
			}else{
				for (int i = 0; i < sheetList.size(); i++) {
					try {
						String rowSql = (String) sheetList.get(i);
						ikmExcelMgr.insert(rowSql);					
					} catch (Exception ex) {
						okList.add("对不起你的第"+(beginRow+i+1)+"行出错了,请检查数据是否正确。");
						System.out.println("对不起你的第"+(beginRow+i+1)+"行出错了");
						ex.printStackTrace();
						break;
					}
				}				
			}	
		}
		return okList;
	}
	
//	将一个sheet转换成一个map (hibernate)
	private List sheet2map(HSSFSheet sheet, int beginRow, int endRow, String userName) throws Exception{
	
		ArrayList sheetList = new ArrayList();
		
		int numberOfRows = sheet.getPhysicalNumberOfRows();
		System.out.println(" ********** 本次共有 " + numberOfRows
				+ " 行需要导入 **********");
		
		// 判断这个 SHEET 中是否存在行数据
		if (numberOfRows > beginRow) {
			HSSFRow tableRow = sheet.getRow(0);//表的名字,系统填写的字段
			HSSFRow colsRow = sheet.getRow(1); // 字段名称_行
			HSSFRow typeRow = sheet.getRow(2); // 字段类型_行
			HSSFRow sizeRow = sheet.getRow(3); // 字段大小_行
			HSSFRow isDictRow = sheet.getRow(4);//是否是字典_行
			HSSFRow nameRow = sheet.getRow(5); // 字段含义_行

			for (int i = beginRow; i < numberOfRows; i++) {
				HSSFRow valueRow = sheet.getRow(i); // 读取一行数据
		
				int numberOfCells = valueRow.getPhysicalNumberOfCells();
//				System.out.println(" ===== 本次共有 " + numberOfCells + " 列需要导入 =====");
		
				HashMap rowMap = new HashMap();
				if(tableRow.getCell(0).getStringCellValue().trim().equals("system")){
					if(!tableRow.getCell(1).getStringCellValue().trim().equals("")&&!tableRow.getCell(2).getStringCellValue().trim().equals("")){
						rowMap.put(tableRow.getCell(1).getStringCellValue().trim(), userName);
						rowMap.put(tableRow.getCell(2).getStringCellValue().trim(), StaticMethod.getLocalString());
					}
					if(!tableRow.getCell(3).getStringCellValue().trim().equals("")){
						rowMap.put(tableRow.getCell(3).getStringCellValue().trim(), Integer.valueOf(0));
					}
					
					//unconfig=0
					if(!tableRow.getCell(4).getStringCellValue().trim().equals("")){
						rowMap.put(tableRow.getCell(4).getStringCellValue().trim(), Integer.valueOf(0));
					}

					
					
				}
				for (short cellNum = 0; cellNum < numberOfCells; cellNum++) {
					HSSFCell colsCell = colsRow.getCell(cellNum); // 字段名称
					HSSFCell typeCell = typeRow.getCell(cellNum); // 字段类型
					HSSFCell sizeCell = sizeRow.getCell(cellNum); // 字段大小
					HSSFCell isDictCell = isDictRow.getCell(cellNum);//是否字典
					HSSFCell nameCell = nameRow.getCell(cellNum); // 字段含义
					
					HSSFCell valueCell = valueRow.getCell(cellNum); // 字段值
		
					if (valueCell == null) {
						continue;
					}
//		
//					System.out.println(" 布尔 =========================================== ");
//					System.out.println(" 字段名称=" + colsCell.getStringCellValue());
//					System.out.println(" 字段类型=" + typeCell.getStringCellValue());
//					System.out.println(" 字段大小="+ sizeCell.getNumericCellValue());
//					System.out.println(" 字段含义=" + nameCell.getStringCellValue());

					switch (valueCell.getCellType()) {
					case HSSFCell.CELL_TYPE_NUMERIC: // 数字 0
//						System.out.println(" 数字 =========================================== ");
//						System.out.println(" 字段名称="+ colsCell.getStringCellValue());
//						System.out.println(" 字段类型="+ typeCell.getStringCellValue());
//						System.out.println(" 字段大小="+ sizeCell.getNumericCellValue());
//						System.out.println(" 字段含义="+ nameCell.getStringCellValue());
//						System.out.println(" 字段值="+ valueCell.getNumericCellValue());
		
						if (typeCell.getStringCellValue().equals("integer")) {
							int value = (int) valueCell.getNumericCellValue();
							rowMap.put(colsCell.getStringCellValue().trim(),String.valueOf(value));
						} else {
							rowMap.put(colsCell.getStringCellValue().trim(),
									String.valueOf(valueCell.getNumericCellValue()));
						}
						break;
					case HSSFCell.CELL_TYPE_STRING: // 字符 1
//						System.out.println(" 字段值="+ valueCell.getStringCellValue());
						if (typeCell.getStringCellValue().equals("integer")) {
							int value = Integer.parseInt(valueCell.getStringCellValue());
							rowMap.put(colsCell.getStringCellValue().trim(),String.valueOf(value));
						} else {
							String value = valueCell.getStringCellValue().trim();
							if(isDictCell.getStringCellValue().trim().equals("1")){
								value = value.substring(value.lastIndexOf("_")+1);
							}
							rowMap.put(colsCell.getStringCellValue().trim(),value);
						}
						break;
					case HSSFCell.CELL_TYPE_FORMULA: // 公式 2
//						System.out.println(" 字段值=" + valueCell.getCellFormula());
						rowMap.put(colsCell.getStringCellValue().trim(), String
								.valueOf(valueCell.getBooleanCellValue()));
		
						break;
					case HSSFCell.CELL_TYPE_BLANK: // 空白 3
						System.out.println(" 字段值="+ valueCell.getStringCellValue());
						rowMap.put(colsCell.getStringCellValue().trim(), "");
						break;
					case HSSFCell.CELL_TYPE_BOOLEAN: // 布尔 4
//						System.out.println(" 字段值="+ valueCell.getBooleanCellValue());
						rowMap.put(colsCell.getStringCellValue().trim(), String
								.valueOf(valueCell.getBooleanCellValue()));
						break;
					case HSSFCell.CELL_TYPE_ERROR: // 错误的数据 5
						break;
					default:
					}
				}
				// sheetList.add(tableRow.getCell(1).getStringCellValue().trim());
				sheetList.add(rowMap);
			}
		}
		
		return sheetList;
	}

//	导入excel (hibernate)
	public List processExcel(File excelFile, int beginRow, int endRow, IParContentsExcelMgr iContentsExcelMgr, String userName)throws Exception {

		ArrayList okList = new ArrayList();
		
		FileInputStream inputStream = new FileInputStream(excelFile
				.getAbsolutePath());
		
		// 创建要导入的Excel文件读入流
		POIFSFileSystem poiFileSystem = new POIFSFileSystem(inputStream);
		
		// 创建Excel工作薄对象
		HSSFWorkbook workBook = new HSSFWorkbook(poiFileSystem);

		List sheetList = null;
		for (int sheetNum = 0; sheetNum < 1; sheetNum++) {
			// 读取 EXCEL 文件中的一个 SHEET
			HSSFSheet sheet = workBook.getSheetAt(sheetNum);
			// 解析一个 Sheet， 将 Sheet 中的每一行都转换成一个 Map。
			sheetList = new ArrayList();
			try {
				sheetList = this.sheet2map(sheet, beginRow, endRow,userName);
			} catch (Exception e) {
				e.printStackTrace();
				okList.add("模板错误");
			}
			if(sheetList.size()==0){
				okList.add("模板错误,请选择正确的模板");
			}else{
				for (int i = 0; i < sheetList.size(); i++) {
					try {
						HashMap rowMap = (HashMap) sheetList.get(i);
						this.map2db(rowMap,iContentsExcelMgr);
					} catch (Exception ex) {
//						System.out.println("对不起你的第"+(beginRow+i+1)+"行出错了");
						if (ex.toString().indexOf("ORA-00001")!=-1) {
							okList.add("对不起你的第"+(beginRow+i+1)+"行出错了,请检查数据是否正确。(违反了唯一约束,请查看唯一字段)");
						}else if (ex.toString().indexOf("ORA-01400")!=-1){
							okList.add("对不起你的第"+(beginRow+i+1)+"行出错了,请检查数据是否正确。(一个不能为空的数据是空)");
						}else {
							okList.add("对不起你的第"+(beginRow+i+1)+"行出错了,请检查数据是否正确。");
						}
						ex.printStackTrace();
						continue;
					}
				}				
			}	
		}
		return okList;
	}

	public void map2db(HashMap rowMap,IParContentsExcelMgr iContentsExcelMgr) throws Exception {
		IParExcelDeal excelDeal = (IParExcelDeal) Class.forName(modelName).newInstance();
		MyBeanUtils.populate(excelDeal, rowMap);
		if("".equals(rowMap.get("id"))||null==rowMap.get("id")){
			iContentsExcelMgr.save(excelDeal);
		}else {
			iContentsExcelMgr.saveOrUpdate(excelDeal);
		}
	}
}
