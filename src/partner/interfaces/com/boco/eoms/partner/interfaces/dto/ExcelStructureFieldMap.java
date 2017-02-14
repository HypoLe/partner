/**
 * 
 */
package com.boco.eoms.partner.interfaces.dto;

/**
 * @author mooker
 *
 */
public class ExcelStructureFieldMap {
	private String irmsFieldName;
	private String irmsFieldLabelCn;
	private String irmsFieldType;
	private String datanmsFieldName;
	private String datanmsFieldCheck;
	private String ref_field_value;
	private String ref_field;
	private String sub_sql;
	private String data_source;
	private String default_value;
	private String is_notnull;

	public ExcelStructureFieldMap() {
	}

	public String getIrmsFieldLabelCn() {
		return irmsFieldLabelCn;
	}

	public String getIrmsFieldName() {
		return irmsFieldName;
	}

	public String getIrmsFieldType() {
		return irmsFieldType;
	}

	public String getDatanmsFieldName() {
		return datanmsFieldName;
	}

	public String getDatanmsFieldCheck() {
		return datanmsFieldCheck;
	}

	public void setIrmsFieldLabelCn(String irmsFieldLabelCn) {
		this.irmsFieldLabelCn = irmsFieldLabelCn;
	}

	public void setIrmsFieldName(String irmsFieldName) {
		this.irmsFieldName = irmsFieldName;
	}

	public void setIrmsFieldType(String irmsFieldType) {
		this.irmsFieldType = irmsFieldType;
	}

	public void setDatanmsFieldName(String datanmsFieldName) {
		this.datanmsFieldName = datanmsFieldName;
	}

	public void setDatanmsFieldCheck(String datanmsFieldCheck) {
		this.datanmsFieldCheck = datanmsFieldCheck;
	}

	public String getRef_field_value() {
		return ref_field_value;
	}

	public void setRef_field_value(String ref_field_value) {
		this.ref_field_value = ref_field_value;
	}

	public String getRef_field() {
		return ref_field;
	}

	public void setRef_field(String ref_field) {
		this.ref_field = ref_field;
	}

	public String getSub_sql() {
		return sub_sql;
	}

	public void setSub_sql(String sub_sql) {
		this.sub_sql = sub_sql;
	}

	public String getData_source() {
		return data_source;
	}

	public void setData_source(String data_source) {
		this.data_source = data_source;
	}

	public String getDefault_value() {
		return default_value;
	}

	public void setDefault_value(String default_value) {
		this.default_value = default_value;
	}

	public String getIs_notnull() {
		return is_notnull;
	}

	public void setIs_notnull(String is_notnull) {
		this.is_notnull = is_notnull;
	}
}
