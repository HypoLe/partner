	/**
 * <p>
 * Title:  联系函 管理  状态值 与中文意义的对应关系
 * </p>
 * <p>
 * Description:  联系函 管理  状态值 与中文意义的对应关系
 * </p>
 * <p>
 *  2012-8-27 上午11:46:50
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */

package com.boco.eoms.partner.contact.util;

public class Type {
//**************************  联系函状态   *********************************************
	
	/**
	 *  被驳回  
	 *      只有发布人可以查看
	 */
	public static final int REJECT= -1;
	
	/**
	 * 草稿 
	 *      只有发布人可以查看
	 */
	public static final int DRAFT= 0;
	
	/**
	 * 审批中
	 *    审批人和发布人可见
	 */
	public static final int AUDITING = 1;
	
	/**
	 * 发布中
	 *  阅知后 也为 发布中状态          发布人，审批人，阅知人可见
	 */
	public static final int PUBLISHING = 2;
	
	/**
	 * 转发中
	 *   发布人，当前阅知人，审批人可见  ，下一阅知人或部门不可见
	 */
	public static final int FORWARD = 3;
	/**
	 * 终止
	 *    只在历史中所有人可见
	 */
	public static final int DEATH = -2;
	
//**************************Task 状态*********************************************
	
	/**
	 * 任务 未开始 -1
	 *     不能查看
	 */
	public static final int NOSTART = -1;
	/**
	 * 任务 已结束 0
	 */
	public static final int END = 0;
	/**
	 * 任务 运行中 1
	 * 任务前一节点，当前节点可见，下一节点 不可见
	 */
	public static final int RUNNING = 1;
	
	
	//*****************************  Task 所有者 类型 *******************************
	/**
	 * 阅知人--- 角色
	 * 该task关联 联系函处理人
	 */
	  public static final int USER = 1;
  	/**
	 * 阅知部门  -- 角色
	 * 该task关联 联系函处理部门
	 */
	public static final int DEPT = 2;
	/**
	 * 审批人  --- 角色
	 * 该task关联 联系函审批人
	 */
	  public static final int AUDIT = 3;
	  
	
	
}

  