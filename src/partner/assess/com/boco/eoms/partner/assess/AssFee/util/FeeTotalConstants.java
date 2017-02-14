package com.boco.eoms.partner.assess.AssFee.util;

import java.math.BigDecimal;

/**
 * <p>
 * Title:计算结果信息
 * </p>
 * <p>
 * Description:计算结果信息
 * </p>
 * <p>
 * Tue Nov 23 16:04:36 CST 2010
 * </p>
 * 
 * @author benweiwei
 * @version 1.0
 * 
 */
public class FeeTotalConstants {
	
	/**
	 * list key
	 */
	public final static String FEETOTAL_LIST = "feeTotalList";
	/**
	 * 浮点型四舍五入
	 */	
	public final static double getDouble2FourFive(double number){
		double i = number;
	    int   scale   =   2;//设置位数   
		int   roundingMode   =   4;//表示四舍五入，可以选择其他舍值方式，例如去尾，等等.  
		BigDecimal   bd   =   new   BigDecimal(i);  
		bd  = bd.setScale(scale,roundingMode); 
		return bd.doubleValue();
	}
}