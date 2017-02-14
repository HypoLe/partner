	/**
 * <p>
 * Title:excel导出标签 子标签
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 *  2012-9-20 下午02:28:56
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */

package com.boco.eoms.commons.excelexporttag.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.boco.eoms.commons.excelexporttag.model.ExcelRowItem;


public class ExcelExportRowToProcessTag extends TagSupport {
	private static final long serialVersionUID = -1075906831189475307L;

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
	 * 是否导出当前的字段  默认导出
	 */
	private boolean isExport = true;
	
	@Override
	public int doEndTag() throws JspException {
			ExcelExportToProcessTag topTag = (ExcelExportToProcessTag) TagSupport.findAncestorWithClass(this, ExcelExportToProcessTag.class);
			ExcelRowItem row = new ExcelRowItem();
			row.setName(name);
			row.setValue(value);
			row.setDictDaoName(dictDaoName);
			row.setExport(isExport);
			topTag.addParams(row);
		return EVAL_PAGE;
	}
	
	
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
