package com.boco.eoms.partner.contact.mgr;

import java.util.List;

import com.boco.eoms.partner.contact.model.ContactMain;
import com.boco.eoms.partner.contact.model.ContactTask;


/**
 * <p>
 * Title:联系函操作
 * </p>
 * <p>
 * Description:联系函操作
 * </p>
 * <p>
 * Aug 24, 2012 10:53:19 AM
 * </p>
 * 
 * @author LiHaolin
 * @version 1.0
 * 
 */
public interface ContactMgr {
	
	/**
	 * 取出联系函 by  id
	 * @param id
	 * @return
	 */
	public  ContactMain  findByMainId(String id);
	
	
	/**
	  * 保存草稿
	  * @param cMain
	  * @param cTask
	  */
	  public void saveDrafts(ContactMain cMain);
	  /**
	   * 送审
	   * @param cMain
	   * @param cTask
	   */
	  public void toAudit(ContactMain cMain,String linkId);

	  	/**
	  	 * 审批
	  	 * @param taskId
	  	 * @param auditContent
	  	 * @param auditResult
	  	 */
	 public void audit(String taskId,String linkId,String auditContent,String auditResult);
	 	/**
	  	 * 审批
	  	 * @param cMain
	  	 * @param linkId
	  	 */
	  public void publish(ContactMain cMain, String linkId) ;
	
	  /**
	   * 转发
	   * @param currentTaskId
	   * @param handleContent
	   * @param cTask
	   */
	  //public void forward(String currentTaskId ,String linkId,String handleContent,List<ContactTask> cTask);
	  public void forward(String currentTaskId ,String linkId,String handleContent,
				ContactMain cMain,String taskOwnerIds,String taskOwnerNames);
	  /**
	   * 终止任务
	   * @param taskId
	   * @param handleContent
	   */
	  public void endTask(String taskId,String handleContent);
	  /**
	   * 阅知
	   * @param taskId
	   * @param handleContent
	   */
	  public void finlishTask(String taskId,String handleContent);
	  
}
