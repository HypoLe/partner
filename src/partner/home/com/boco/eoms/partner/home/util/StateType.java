	/**
 * <p>
 * Title:  公告管理  状态值 与中文意义的对应关系
 * </p>
 * <p>
 * Description:  公告管理  状态值 与中文意义的对应关系
 * </p>
 * <p>
 *  2012-8-27 上午11:46:50
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */

package com.boco.eoms.partner.home.util;

public class StateType {
   
	//******************** 公告信息 *********************************
	/**
	 * 作废 -1
	 */
	public static final int INFO_TYPE_INVALID = -1;
	/**
	 * 草稿 0
	 */
	public static final int INFO_TYPE_DRAFTS = 0;
	/**
	 * 审批中 1
	 */
	public static final int INFO_TYPE_AUDIT = 1;
	/**
	 * 发布中 2
	 */
	public static final int INFO_TYPE_RELEASE = 2;
	/**
	 * 被驳回 3
	 */
	public static final int INFO_TYPE_REJECT = 3;
	/**
	 * 阅知 4
	 */
	public static final int INFO_TYPE_READ = 4;
	
	/**
	 * 过期 5
	 */
	public static final int INFO_TYPE_EXPIRED = 5;
	
	
	//************************************  LINK TASK 操作类型***************************************************
	/**
	 * 作废 -1
	 */
	public static final int OPERATE_INVALID = -1;
	/**
	 * 存草稿 0
	 */
	public static final int OPERATE_DRAFTS = 0;
	/**
	 * 送审 1
	 */
	public static final int OPERATE_TO_AUDIT = 1;
//	/**
//	 * 下一审批 2  modify:下一审批也是送审
//	 */
	//public static final int LINK_NEXT_TOAUDIT = 2;
	/**审批*/
	public static final int OPERATE_AUDIT = 2;
	/**发布*/
	public static final int OPERATE_PUBLISH = 3;
//	/**
//	 * 通过 3
//	 */
//	public static final int LINK_PASS = 3;
//	/**
//	 * 驳回 4
//	 */
//	public static final int LINK_REJECT = 4;
	/**阅知*/
	public static final int OPERATE_READ = 4;
	
	//******************************操作结果*******************************************************
	public static final int OPERATERESULT_AUDITREJECT = 0;
	public static final int OPERATERESULT_AUDITPASS = 1;
	
	
   //	public static final int LINK_READ = 5;
//	/**
//	 * 审批结果 驳回 0
//	 */
//	public static final int LINK_RESULT_AUDIT_NOPASS = 0;
//	/**
//	 * 审批结果 通过 1
//	 */
//	public static final int LINK_RESULT_AUDIT_PASS = 1;
//	/**
//	 * 审批结果 通过 下一审批 2
//	 */
//	public static final int LINK_RESULT_AUDIT_PASS_NEXT = 2;
	
//**************************Task*********************************************
	/**
	 * 任务 未开始 -1
	 */
	public static final int TASK_NOSTART = -1;
	/**
	 * 任务 已结束 0
	 */
	public static final int TASK_END = 0;
	/**
	 * 任务 运行中 1
	 */
	public static final int TASK_RUNNING = 1;
//	/**
//	 * 任务 不能查看 -1
//	 */
//	public static final int TASK_CANNOT_READ = -1;
//	/**
//	 * 任务 阅知 0 
//	 */
//	public static final int TASK_READ = 0;
//	/**
//	 * 任务 审批 1
//	 */
//	public static final int TASK_AUDIT = 1;
//	/**
//	 * 任务 待查看 2
//	 */
//	public static final int TASK_TO_READ = 2;
	
}
