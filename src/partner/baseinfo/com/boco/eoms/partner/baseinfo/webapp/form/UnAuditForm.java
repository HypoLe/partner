package com.boco.eoms.partner.baseinfo.webapp.form;

import com.boco.eoms.base.webapp.form.BaseForm;

/**
 * <p>
 * Title:未审核信息
 * </p>
 * <p>
 * Description:未审核信息
 * </p>
 * <p>
 * 2010-7-15
 * </p>
 * 
 * @moudle.getAuthor() benweiwei
 * @moudle.getVersion() 1.0
 * 
 */
public class UnAuditForm extends BaseForm implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 所属模块
	 */
	private String module;	
	
	/**
	 * 访问链接
	 */
	private String url;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}