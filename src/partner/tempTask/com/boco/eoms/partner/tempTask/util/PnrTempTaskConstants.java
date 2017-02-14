package com.boco.eoms.partner.tempTask.util;

/**
 * <p>
 * Title:合作伙伴临时任务管理
 * </p>
 * <p>
 * Description:合作伙伴临时任务管理
 * </p>
 * <p>
 * Mon Apr 26 14:09:01 CST 2010
 * </p>
 * 
 * @author daizhigang
 * @version 1.0
 * 
 */
public class PnrTempTaskConstants {
	
	/**
	 * list key
	 */
	public final static String PNRAGREEMENTMAIN_LIST = "pnrTempTaskMainList";

	public final static String PNRAGREEMENTWORK_LIST = "pnrTempTaskWorkList";

	public final static String PNRAGREEMENTEVA_LIST = "pnrTempTaskEvaList";

	public final static String PNRAGREEMENTAUDIT_LIST = "unAuditList";

	/**
	 * 临时任务状态待审核
	 */
	public final static String AGREEMENT_STATE_UNAUDIT = "0";	
	/**
	 * 临时任务状态驳回
	 */
	public final static String AGREEMENT_STATE_REJECT = "1";	
	/**
	 * 临时任务状态有效
	 */
	public final static String AGREEMENT_STATE_PASS = "2";
}
