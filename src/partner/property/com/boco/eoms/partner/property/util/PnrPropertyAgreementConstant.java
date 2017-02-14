package com.boco.eoms.partner.property.util;

public class PnrPropertyAgreementConstant {
	/**无需支付(指计划还未开始，现在无需支付)*/
	public static final String UNPAID="0";
	/**已支付*/
	public static final String HAVEPAID="1";
	/**待支付*/
	public static final String WAITPAID="2";
	/**逾期未支付*/
	public static final String HAVEEXPIREDUNPAID="3";
	/**合同有效*/
	public static final String AGREEMENTEFFECTED="4";
	/**合同无效*/
	public static final String AGREEMENTUNEFFECTED="5";
	/**合同到期提醒*/
	public static final String AGREEMENTREMIND="6";
	/**合同未到期提醒*/
	public static final String AGREEMENTNOREMIND="7";
	/**批量导入合同excel模板名称*/
	public static final String AGREEMENTXLSFILENAME="物业合同导入模板.xls";
	/**批量导入电费支付记录excel模板名称*/
	public static final String ELECTRICITYBILLXLSFILENAME="电费支付记录导入模板.xls";
	/**批量导入租赁支付记录excel模板名称*/
	public static final String RENTBILLXLSFILENAME="租赁费用支付记录导入模板.xls";
	
}
