/**
 * 
 */
package com.boco.eoms.partner.interfaces.dto;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * @author mooker
 * 
 */
public class ExcelStructureTable{
	private static Log log = LogFactory.getLog(ExcelStructureTable.class);
	private String className;
	private String classNameCN;
	private String classMQName;
	private Map<String, ExcelStructureFieldMap> fieldMap = new HashMap();
	private Map<String, ExcelStructureFieldMap> pfieldMap = new HashMap();

	public String getClassName() {
		return className;
	}

	public String getClassNameCN() {
		return classNameCN;
	}

	public Map<String, ExcelStructureFieldMap> getFieldMap() {
		return fieldMap;
	}
	
	public Map<String, ExcelStructureFieldMap> getPfieldMap() {
		return pfieldMap;
	}

	public void setClassNameCN(String classNameCN) {
		this.classNameCN = classNameCN;
	}

	public void setClassName(String className) {
		this.className = className;
	}
	
	public String getClassMQName() {
		return classMQName;
	}

	public void setClassMQName(String classMQName) {
		this.classMQName = classMQName;
	}
}
