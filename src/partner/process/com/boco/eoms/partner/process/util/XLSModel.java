package com.boco.eoms.partner.process.util;

import com.boco.eoms.commons.interfaceMonitoring.util.interfaceMonitorin;

public class XLSModel {
	/**
	 * 增加时数据开始行，从0行开始
	 */
	private int updateStartRowNum;
	/**
	 * 删除数据开始行，从0行开始
	 */
	private int deleteStartRowNum;
	/**
	 * 修改时数据开始行，从0行开始
	 */
	private int addStartRowNum;
	/**
	 * 增加时有效数据总列数
	 */
	private int addTotalCol;
	/**
	 * 增加时有效数据开始列，从0列开始
	 */
	private int addStartCol;
	/**
	 *更新时有效数据总列数
	 */
	private int updateTotalCol;
	/**
	 * 更新时有效数据开始列，从0列开始
	 */
	private int updateStartCol;
	/**
	 * 删除时有效数据总列数，
	 */
	private int deleteTotalCol;
	/**
	 * 删除有效数据开始列，从0列开始
	 */
	private int deleteStartCol;
	
	
	/**
	 * 构造器(增加数据开始行，开始列，总列数，删除数据开始行，开始列，总列数，修改数据开始行，开始列，总列数)
	 */
	public XLSModel(
			int addStartRowNum,int addStartCol,int addTotalCol, 
			int deleteStartRowNum,int deleteStartCol,  int deleteTotalCol,
			int updateStartRowNum, int updateStartCol,int updateTotalCol) {
		super();
		this.updateStartRowNum = updateStartRowNum;
		this.deleteStartRowNum = deleteStartRowNum;
		this.addStartRowNum = addStartRowNum;
		this.addTotalCol = addTotalCol;
		this.addStartCol = addStartCol;
		this.updateTotalCol = updateTotalCol;
		this.updateStartCol = updateStartCol;
		this.deleteTotalCol = deleteTotalCol;
		this.deleteStartCol = deleteStartCol;
	}
	
	public int getUpdateStartRowNum() {
		return updateStartRowNum;
	}

	public void setUpdateStartRowNum(int updateStartRowNum) {
		this.updateStartRowNum = updateStartRowNum;
	}

	public int getDeleteStartRowNum() {
		return deleteStartRowNum;
	}

	public void setDeleteStartRowNum(int deleteStartRowNum) {
		this.deleteStartRowNum = deleteStartRowNum;
	}

	public int getAddStartRowNum() {
		return addStartRowNum;
	}

	public void setAddStartRowNum(int addStartRowNum) {
		this.addStartRowNum = addStartRowNum;
	}
	
	public int getAddTotalCol() {
		return addTotalCol;
	}
	public void setAddTotalCol(int addTotalCol) {
		this.addTotalCol = addTotalCol;
	}
	public int getAddStartCol() {
		return addStartCol;
	}
	public void setAddStartCol(int addStartCol) {
		this.addStartCol = addStartCol;
	}
	public int getUpdateTotalCol() {
		return updateTotalCol;
	}
	public void setUpdateTotalCol(int updateTotalCol) {
		this.updateTotalCol = updateTotalCol;
	}
	public int getUpdateStartCol() {
		return updateStartCol;
	}
	public void setUpdateStartCol(int updateStartCol) {
		this.updateStartCol = updateStartCol;
	}
	public int getDeleteTotalCol() {
		return deleteTotalCol;
	}
	public void setDeleteTotalCol(int deleteTotalCol) {
		this.deleteTotalCol = deleteTotalCol;
	}
	public int getDeleteStartCol() {
		return deleteStartCol;
	}
	public void setDeleteStartCol(int deleteStartCol) {
		this.deleteStartCol = deleteStartCol;
	}
	
	
}
