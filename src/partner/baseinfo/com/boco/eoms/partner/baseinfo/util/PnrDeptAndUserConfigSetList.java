package com.boco.eoms.partner.baseinfo.util;

/**  
 * @Title: PnrDeptAndUserConfigSet.java
 * @Package com.boco.eoms.partner.baseinfo.util
 * @Description: 用于资质管理各项配置设置
 * @author fengguangping fengguangping@boco.com.cn
 * @date Mar 25, 2013  9:41:49 AM  
 */
public class PnrDeptAndUserConfigSetList {
	/**
	 * 是否需要代维资质这个模块:"0"-不需要 "1"-需要
	 */
	private String qualificationConfig;
	/**
	 * 技能验证方式，有userid和身份证两种验证方式值为:userId和personCardNo
	 */
	private String dwInfoValidateType;
	
	/**
	 *代维组织使用的是否是新模版:"0"-旧模板 "1"-新模版
	 */
	private String partnerDeptExcelModel;
	
	
	public String getQualificationConfig() {
		return qualificationConfig;
	}
	public void setQualificationConfig(String qualificationConfig) {
		this.qualificationConfig = qualificationConfig;
	}
	public String getDwInfoValidateType() {
		return dwInfoValidateType;
	}
	public void setDwInfoValidateType(String dwInfoValidateType) {
		this.dwInfoValidateType = dwInfoValidateType;
	}
	public String getPartnerDeptExcelModel() {
		return partnerDeptExcelModel;
	}
	public void setPartnerDeptExcelModel(String partnerDeptExcelModel) {
		this.partnerDeptExcelModel = partnerDeptExcelModel;
	}
	
}
