package com.boco.eoms.partner.process.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.hssf.usermodel.HSSFRow;

import com.boco.eoms.partner.process.model.PartnerProcessMain;
import com.boco.eoms.partner.process.util.XLSModel;
import com.boco.eoms.partner.resourceInfo.util.ImportResult;
/**
 * 变更流程接口
 * @author fengguangping
 *
 */
public interface PnrProcessService {
	/**
	 * 校验删除操作时数据
	 * @param row
	 * @return
	 * @throws Exception
	 */
	boolean doDeleteXLSFileValidate(HSSFRow row) throws Exception;
	/**
	 * 校验增加操作时数据
	 * @param row
	 * @return
	 * @throws Exception
	 */
	boolean doSaveXLSFileValidate(HSSFRow row) throws Exception;
	/**
	 * 校验修改操作时数据
	 * @param row
	 * @return
	 * @throws Exception
	 */
	boolean doUpdateXLSFileValidate(HSSFRow row) throws Exception;
	
	/**
	 *执行增加归档操作
	 * @param row
	 * @return
	 * @throws Exception 
	 */
	boolean doSaveXLSFileData(HSSFRow row,HttpServletRequest request) throws Exception;
	/**
	 * 执行更新归档操作
	 * @param row
	 * @return
	 * @throws Exception 
	 */
	boolean doUpdateXLSFileData(HSSFRow row,HttpServletRequest request) throws Exception;
	/**
	 * 执行删除归档
	 * @param row
	 * @return
	 * @throws Exception 
	 */
	boolean doDeleteXLSFileData(HSSFRow row,HttpServletRequest request) throws Exception;
	
	/**
	 * 返回excel文件校验数据的规格;
	 */
	XLSModel getXLSModel();
	/**
	 * 当删除申请终止后恢复数据操作，如果没有恢复数据操作直接返回true
	 * @param row
	 * @return
	 * @throws Exception 
	 */
	boolean doRestoreDeleteXLSFileData(HSSFRow row, HttpServletRequest request);
	/**
	 * 当更新申请终止后恢复数据操作，如果没有恢复数据操作直接返回true
	 * @param row
	 * @return
	 * @throws Exception 
	 */
	boolean doRestoreUpdateXLSFileData(HSSFRow row, HttpServletRequest request);
	/**
	 * 当增加数据申请终止后恢复数据操作，如果没有恢复数据操作直接返回true
	 * @param row
	 * @return
	 * @throws Exception 
	 */
	boolean doRestoreSaveXLSFileData(HSSFRow row, HttpServletRequest request);
	/**
	 * 加载静态资源，提高校验效率
	 * @param row
	 * @return
	 * @throws Exception 
	 */
	boolean doLoadStaticSource();

}
