package com.boco.eoms.sheet.base.service;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.boco.eoms.sheet.base.model.BaseLink;

/** 
 * Description: 提交流程引擎处理前的自己私有业务处理类 
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Oct 15, 2012 4:44:51 PM 
 */
public interface IPreEngineDataHandler {
	/**
	 * 各工单在PerformAdd之前对自己业务数据进行二次处理
	 * 可以对mainMap，linkmap，operate进行二次处理，均为面向地址调用，所以只需要直接修改这几个数据即可
	 */
	public void prePerformAddBusiMap(HttpServletRequest request,HashMap mainMap,HashMap linkMap,HashMap operate);
	/**
	 * 各工单对自己业务数据进行二次处理
	 * 可以对mainMap，linkmap，operate进行二次处理，均为面向地址调用，所以只需要直接修改这几个数据即可
	 */
	public void prePerformClaimBusiMap(HttpServletRequest request,HashMap mainMap,HashMap linkMap,HashMap operate);
	/**
	 * 各工单在PerformDeal之前对自己业务数据进行二次处理
	 * 可以对mainMap，linkmap，operate进行二次处理，均为面向地址调用，所以只需要直接修改这几个数据即可
	 */
	public void prePerformDealBusiMap(HttpServletRequest request,HashMap mainMap,HashMap linkMap,HashMap operate);
	
	/**
	 * 各工单对自己业务数据进行二次处理
	 * 可以对mainMap，linkmap，operate进行二次处理，均为面向地址调用，所以只需要直接修改这几个数据即可
	 */
	public void prePerformFroceHold(HttpServletRequest request,HashMap mainMap,HashMap linkMap,HashMap operate);
	
	
	/**
	 * 各工单封装不是main、link、task几个表的私有表的数据集合
	 * @return
	 */
	public List<Object> prePerformAddOtherDate(HttpServletRequest request,
			final HashMap mainMap,final List<BaseLink> linkList,final HashMap operate);
	/**
	 * 各工单封装不是main、link、task几个表的私有表的数据集合
	 * @return
	 */
	public List<Object> prePerformClaimOtherDate(HttpServletRequest request,
			final HashMap mainMap,final List<BaseLink> linkList,final HashMap operate);
	/**
	 * 各工单封装不是main、link、task几个表的私有表的数据集合
	 * @return
	 */
	public List<Object> prePerformDealOtherDate(HttpServletRequest request,
			final HashMap mainMap,final List<BaseLink> linkList,final HashMap operate);
}
