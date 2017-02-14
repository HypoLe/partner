package com.boco.eoms.partner.res.util;

import com.boco.eoms.base.util.ApplicationContextHolder;
import com.boco.eoms.partner.netresource.service.IEomsService;
import com.boco.eoms.partner.res.model.PnrResConfig;
import com.googlecode.genericdao.search.Search;

/**
 * 线路巡检资源功能判断类
 * @author ZKQ
 *
 */
public class PRCERUtil {
	/**
	 * 将传输敷设点关联到光缆段
	 */
	public static final int TLP2TL = 1;
	/**
	 * 敷设点必到点设置
	 */
	public static final int TLP_MUST_ARRIVED_SET = 2;
	/**
	 * 将光缆段的起点和终点设置到敷设点中
	 */
	public static final int ADD_TLPOINT2TLP = 4;
	
	/**
	 * 判断线路巡检是否具有某种功能
	 * @param operation
	 * @param resCfgId
	 * @return
	 */
	public static boolean operationJudgment(int operation,String resCfgId) {
		IEomsService service = (IEomsService)ApplicationContextHolder.getInstance().getBean("eomsService");
		Search es = new Search();
		PnrResConfig cfg = (PnrResConfig)service.searchUnique(es);
		int dbOperation = cfg.getExecuteRecord();
		int result = operation & dbOperation;
		if(result>0) {
			return true;
		} else {
			return false;
		}
	}
	
	public static int addOperation(int currentOperationValue,int addOperationValue) {
		int operation = currentOperationValue | addOperationValue;
		return operation;
	}
	
	public static int removeOperation(int currentOperationValue,int removeOperationValue) {
		int operation = currentOperationValue ^ removeOperationValue;
		return operation;
	}
}
