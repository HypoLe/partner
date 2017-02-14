package com.boco.eoms.partner.personnel.util.fusionchart.bean;
/**
 * <p>
 * Title:Chart数据
 * </p>
 * <p>
 * Description: Chart数据
 * </p>
 * <p>
 * Jul 23, 2012 9:06:53 AM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
public class SetValue {

	private String value;
	/**
	 * 饼图 图注时使用
	 */
	private String name;
	public SetValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
