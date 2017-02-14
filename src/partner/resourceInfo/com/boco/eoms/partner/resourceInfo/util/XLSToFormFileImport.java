package com.boco.eoms.partner.resourceInfo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.struts.upload.FormFile;
import org.apache.taglibs.bsf.expression;
import org.springframework.aop.ThrowsAdvice;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.commons.interfaceMonitoring.util.interfaceMonitorin;
import com.boco.eoms.deviceManagement.common.utils.CommonSqlHelper;
import com.boco.eoms.partner.baseinfo.util.PnrDeptAndUserConfigSetList;
import com.boco.eoms.partner.netresource.dao.IEomsDao;
import com.boco.eoms.partner.process.util.PnrProcessCach;
import com.boco.eoms.partner.process.util.XLSModel;

public abstract class XLSToFormFileImport {
	public ImportResult xlsFileValidate(FormFile formFile,HttpServletRequest request) throws Exception {
		PnrDeptAndUserConfigSetList setList = (PnrDeptAndUserConfigSetList) ApplicationContextHolder
		.getInstance().getBean("pnrDeptAndUserConfigSetList");
		String validateType = setList.getDwInfoValidateType();
		HSSFSheet sheet = this.fileCheck(formFile);
		XLSModel xlsModel = this.getXLSModel();
		int insertCount = 0;
		ImportResult result = new ImportResult();
		long t1=System.currentTimeMillis();
		this.loadStaticSource();
		long t2=System.currentTimeMillis();
		System.out.println("-------------------加载静态资源的时间为"+(t2-t1)+"ms---------------------");
		String usedTime = "";
		int addBatchRecodes=0;//oracle和informix对batch的容量各不相同
		if(CommonSqlHelper.isOracleDialect()){
			addBatchRecodes=2000;
		}else {
			addBatchRecodes=500;
		}
		if (getWay()) {
			try{
				long startTime=System.currentTimeMillis();
				for (int i = xlsModel.getAddStartRowNum(); i <= sheet.getLastRowNum(); i++) { // 获取工作表中每行数据,对其校验
					HSSFRow row = sheet.getRow(i);
					if (!this.blankRowCheck(row, xlsModel)) {
						if (this.doSaveRow2Data(row, request)) {
							insertCount++;
						}
					} else {
						break;
					}
				}
				long endTime = System.currentTimeMillis();
				long sec = (endTime - startTime) / 1000;
				long min = sec / 60;
				if (min > 0) {
					usedTime += Long.toString(min) + "分钟,";
				}
				usedTime += Long.toString(sec) + "秒";
				System.out.println(usedTime);
			} catch (SQLException e) {
				e.printStackTrace();
				result.setResultCode("503");
				String msg = result.getRestultMsg();
				if (!msg.equals("")) {
					msg += ",";
				}
				result.setRestultMsg(e.getMessage());
				throw new RuntimeException(result.getRestultMsg());
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
		} else {
			long startTime=System.currentTimeMillis();
			Connection conn = null;
			PreparedStatement ps = null;
			String sql = "";
			try {
				conn = ((IEomsDao) ApplicationContextHolder.getInstance().getBean("eomsDao")).getCon();
				sql = getBachSql();
				ps = conn.prepareStatement(sql);
				conn.setAutoCommit(false);
				for (int i = xlsModel.getAddStartRowNum(); i <= sheet	.getLastRowNum(); i++) { // 获取工作表中每行数据,对其校验
					HSSFRow row = sheet.getRow(i);
					if (!this.blankRowCheck(row, xlsModel)) {
						this.validateAndPrepareStatement(row, request, "add",ps, validateType);
						insertCount++;
						if ((insertCount% addBatchRecodes == 0)|| (i == sheet.getLastRowNum())) {
							ps.executeBatch();
							ps.clearBatch();
							conn.commit();
						}
					} else {
						break;
					}
				}
				conn.setAutoCommit(true);
			} catch (Exception e) {
				e.printStackTrace();
				if (conn != null) {
					try {
						conn.rollback();
					} catch (Exception cre) {
					}
				}
				result.setResultCode("503");
				String msg = result.getRestultMsg();
				if (!msg.equals("")) {
					msg += ",";
				}
				result.setRestultMsg(e.getMessage());
				throw new RuntimeException(result.getRestultMsg());
			} finally {
				if (ps != null) {
					try {
						ps.close();
					} catch (Exception pe) {
					}
				}
				if (conn != null) {
					try {
						conn.close();
					} catch (Exception ce) {
					}
				}
			}
			long endTime = System.currentTimeMillis();
			long sec = (endTime - startTime) / 1000;
			long min = sec/60;
			if (min > 0) {
				usedTime += Long.toString(min) + "分钟,";
			}
			usedTime += Long.toString(sec) + "秒";
			System.out.println("false ==="+usedTime);
		}
		result.setRestultMsg("本次导入耗时" + usedTime);
		result.setResultCode("200");
		result.setImportCount(insertCount);
		return result;
	}

	/**
	 * 文件检查
	 * 
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
	 * 
	 * @param formFile
	 * @return
	 * @throws Exception
	 */
	private HSSFSheet fileCheck(FormFile formFile) throws Exception {
		InputStream is = formFile.getInputStream();
		String fileName = formFile.getFileName();
		try {
			return check(fileName, is);
		} finally {
			is.close();
		}
	}

	private HSSFSheet check(String fileName, InputStream is) throws Exception {
		ImportResult result = new ImportResult();
		result.setRestultMsg("");
		// 检查是否为Excel文件
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
	 * 
	 * @param row
	 * @return
	 */
	private boolean blankRowCheck(HSSFRow row, XLSModel xlsModel) {
		if(row==null){
			return true;//如果该行是空行直接结束导入;
		}
		HSSFCell cell;
		int j = 0;
		for (int i = 0; i < xlsModel.getAddTotalCol(); i++) {
			cell = row.getCell(i + xlsModel.getAddStartCol());
			if (cell == null) {
				j++;
			} else if ("".equals(cell.toString())) {
				j++;
			}
		}
		if (j >= xlsModel.getAddTotalCol()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 上传数据的excel表格的规格，对addStartRowNum,addStartCol，addTotalCol进行赋值，其余数据随意赋值
	 * 增加数据开始行，开始列，总列数
	 * 
	 * @return
	 */
	public abstract XLSModel getXLSModel();

	/**
	 * 执行数据保存
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract boolean doSaveRow2Data(HSSFRow row,
			HttpServletRequest request) throws Exception;

	/**
	 * 执行前需要验证数据
	 * 
	 * @return
	 * @throws Exception
	public abstract boolean doValidate(HSSFRow row, HttpServletRequest request,
			String type) throws Exception;
	*/

	/**
	 * 执行前需要验证数据
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract String getBachSql() throws Exception;

	/**
	 * 插入数据库的方式选择器:为了兼容以前的模块，批处理方式只能执行一个插入方法，
	 * 比如人员和组织在插入pnr_user,pnr_dept后还要插入taw_system_user,taw_system_dept,此时采用批处理无法实现
	 * 1.使用的是doSaveRow2Data()方法来执行保存逻辑，需要返回false，此时必须实现doValidate(),doSaveRow2Data()方法;
	 * 2.使用的是executeBachSql()方法执行批处理方式，需要返回true，此时必须实现getBachSql(),doValidate(),getXLSModel()，addPsBach()方法
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract boolean getWay() throws Exception;

	/**
	 * 执行前需要验证数据
	 * 
	 * @return
	 * @throws Exception
	public abstract PreparedStatement addPsBach(PreparedStatement ps,HSSFRow row, HttpServletRequest request) throws Exception;
	 */

	/**
	 * 需要提前加载的静态资源,添加此接口是为了按需加载节约资源，资源自行指定
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract void loadStaticSource() throws Exception;

	/**
	 * 执行插入数据库
	 * 
	 * @return
	 * @throws Exception
	public int executeBachSql(FormFile formFile, HttpServletRequest request,
			HSSFSheet sheet, XLSModel xlsModel) throws Exception {
		int insertCount = 1;
		Connection conn = null;
		PreparedStatement ps = null;
		String sql = "";
		try {
			conn = ((IEomsDao) ApplicationContextHolder.getInstance().getBean(
					"eomsDao")).getCon();
			sql = getBachSql();
			ps = conn.prepareStatement(sql);
			conn.setAutoCommit(false);
			for (int i = xlsModel.getAddStartRowNum(); i <= sheet
					.getLastRowNum(); i++) {// 获取工作表中每行数据，插入数据库
				HSSFRow row = sheet.getRow(i);
				if (!this.blankRowCheck(row, xlsModel)) {
					//System.out.println("正在保存第" + (i + 1) + "行!");
					ps = addPsBach(ps, row, request);
					insertCount++;
				}
				if ((insertCount % 5000 == 0) || (i == sheet.getLastRowNum())) {
					ps.executeBatch();
					ps.clearBatch();
				}
			}
			conn.commit();
			conn.setAutoCommit(false);
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return insertCount;
	}
	 */
	/**
	 * 执行前需要验证数据
	 * 
	 * @return
	 * @throws Exception
	 */
	public abstract PreparedStatement validateAndPrepareStatement(HSSFRow row,HttpServletRequest request,
			String type, PreparedStatement ps,String validateType) throws Exception;

}
