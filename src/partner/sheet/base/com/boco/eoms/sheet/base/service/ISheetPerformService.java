package com.boco.eoms.sheet.base.service;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/** 
 * Description: 工单操作
 * Copyright:   Copyright (c)2012
 * Company:     BOCO 
 * @author:     Liuchang 
 * @version:    1.0 
 * Create at:   Oct 11, 2012 2:29:36 PM 
 */
public interface ISheetPerformService{
	
    /**
     * 获取工单对象的operate对象的字段名称和类型，比如dealPerformer@java.lang.String
     */
    public String getPageColumnName();

//    /**
//     * 设置流程引擎所要使用的map
//     */
//    public abstract void setFlowEngineMap(HashMap flowEngineMap);
//
//    /**
//     * 获取流程引擎所要使用的map
//     */
//    public abstract HashMap getFlowEngineMap();
	
	 /**
     * 工单main新增提交方法
     */
    public abstract void performAdd(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception;
    
    /**
     * 更新main对象的提交
     */
    public abstract void performUpdate(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception;
    
    /**
     * 工单处理提交
     */
    public abstract void performDeal(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception;
    
    /**
     * main工单的撤销
     */
    public void performCancel(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception ;
    
	/**
     * 工单提交预处理
     */

    public void performPreCommit(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception ;
    
    /**
     * 解析流程配置文件
     */
    public HashMap analyseFlowDefine(HttpServletRequest request,String sheetPageName)
            throws Exception;
    
	/**
	 * 获取流程定义文件的路径 格式为classpath:config/business-config.xml
	 * @return roleConfigPath
	 */
	public abstract String getRoleConfigPath();
	
	/**
	 * 设置流程定义文件的路径 格式为classpath:config/business-config.xml
	 * @return roleConfigPath
	 */
//	public abstract void setRoleConfigPath(String roleConfigPath);
    
	/**
	 * 申明一个任务
	 */
	public void performClaim(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response  ) throws Exception;
	
	/**
	 * 创建一个子任务
	 */
	public void performCreateSubTask(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response  ) throws Exception;
	
	/**
	 * 执行流程的事件
	 */
	public void performProcessEvent(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response  ) throws Exception;
	
    /**
     * 工单移交 
     */
    public void performTransferWorkItem(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception;
    
    /**
	 * 工单处理信息的保存
	 */
	public void performSaveDealInfo(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response  ) throws Exception;
	

    
    /**
     * @author 张影
	 * 处理工单强制归档、作废
	 */
	public void performFroceHold(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response  ) throws Exception;
	
	/**
	 * 执行处理回复不通过
	 */
	public void performDealReplyReject(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception;
	
	/**
	 * 执行接口回调动作
	 */
	public void performInvokeReply(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception ;
	
	/**
	 * 新增提交
	 */
	public void newPerformAdd(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception ;
	
	/**
	 * LINK的提交
	 */
	public void newPerformDeal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception;
	
	/**
	 * 整合用   非流程动作的处理：目前包括抄送、阶段回复、阶段通知
	 */
	public void newPerformNonFlow(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception;
	
	/**
	 * 整合用   非流程动作：目前包括抄送、阶段回复、阶段通知中进行link和task的保存
	 * @param taskId 任务id
	 * @param dataMap 输入参数为Map类型，其中包括：Map类型的main、Map类型的link、Map类型的operate
	 * @return
	 * @throws Exception
	 */
	public void newSaveNonFlowData(HttpServletRequest request,String taskId,Map dataMap) throws Exception;
	/**
	 * 提交批量回复处理
	 */
	public void performBatchDeal(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	throws Exception;
	
	/**
	 * 工单隐藏
	 */
	public void performHide(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception;
	
	  /**
     * 申请任务(Atom系统)
     */
    public void performClaimTaskAtom(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception;      
    /**
     * 任务的处理(Atom系统)
     */
    public abstract void performDealAtom(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception;      
    
    /**
     * 移交(Atom系统)
     */
    public abstract void performTransferAtom(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception;    
    /**
     * 阶段回复(Atom系统)
     */
    public abstract void showPhaseReplyPage(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception;    
    /**
     * 阶段回复(Atom系统)
     */
    public abstract void performNonFlowAtom(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception;   
    
	/**
	 * 执行处理回复通过
	 */
	public void performDealReplyAccept(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception;
	
	/**
     * 是否调用了其他工单
     */
    public abstract boolean performIsInvokeProcess(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception;
    
	/**
	 * 工单隐藏查询提交
	 */
	public void performQueryHide(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception;
	
	/**
	 * 当驳回的时候查询上一条任务的执行者对象
	 *   从request中通过request.getAttribute(fOperateroleid/ftaskOwner/fOperateroleidType)
	 *   取得上一条任务的Operateroleid taskOwner OperateroleidType
	 * @param request
	 * @throws Exception
	 */
	public void setParentTaskOperateWhenRejct( HttpServletRequest request)
	throws Exception;
}
