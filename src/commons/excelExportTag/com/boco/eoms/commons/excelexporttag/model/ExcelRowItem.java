	/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 *  2012-9-21 下午03:16:40
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */

package com.boco.eoms.commons.excelexporttag.model;

@SuppressWarnings("serial")
public class ExcelRowItem implements java.io.Serializable {
	/**
	 * 字段中文名（页面显示）
	 */
	private String name;
	/**
	 * 字段名 （对应model）
	 */
	private String value;
	/**
	 * 进行字典转换的dao名
	 */
	private String dictDaoName;
	/**
	 * 是否导出当前的字段
	 */
	private boolean isExport;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDictDaoName() {
		return dictDaoName;
	}
	public void setDictDaoName(String dictDaoName) {
		this.dictDaoName = dictDaoName;
	}
	public boolean isExport() {
		return isExport;
	}
	public void setExport(boolean isExport) {
		this.isExport = isExport;
	}
	
	
	
}
